package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.dao.*;
import com.shenji.audit.model.*;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.common.AffairType;
import com.shenji.audit.service.FileService;
import com.shenji.audit.utils.IDKeyUtil;
import com.shenji.audit.utils.MinioUtil;
import com.shenji.audit.utils.SignUtil;
import com.shenji.audit.utils.ToPdf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 事务服务类实现
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 15:30
 */
@Service
@Transactional
@Slf4j
public class AffairServiceImpl implements AffairService {

    @Resource
    private AffairMapper affairMapper;

    @Resource
    private ApprovalMapper approvalMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private ToPdf toPdf;

    @Autowired
    private SignUtil signUtil;

    @Autowired
    private FileService fileService;

    /**
     *
     * @param userId 发起人
     * @param name 名称
     * @param remark 详情
     * @param kind 类型
     */
    @Override
    public Long startAffair(Long userId, String name, String remark, Integer kind) {

        User user = userMapper.selectOne(userId);

        Affair affair = new Affair();
        affair.setId(IDKeyUtil.generateId());
        affair.setPromoterId(userId);
        affair.setApproverPost(user.getPost() + 1);

        affair.setKind(kind);

        //这个地方需要划分
        affair.setState(AffairType.STATE_READY);
        affair.setName(name);
        affair.setRemark(remark);

        affair.setStartTime(new Date());
        affair.setEndTime(null);

        affairMapper.insertOne(affair);

        return affair.getId();
    }


    @Override
    public Affair getAffair(Long userId, Long affairId) {
        return affairMapper.getAffairById(affairId);
    }

    @Override
    public List<Affair> getHistory(Long userId) {
        return affairMapper.getHistory();
    }

    /**
     * 获取全部事务
     * @param userId 用户id
     * @return 事务列表
     */
    @Override
    public List<Affair> getAllAffair(Long userId) {
        return affairMapper.selectAll();
    }

    /**
     * 获取我发起的事务
     * @param userId 用户id
     * @return 事务列表
     */
    @Override
    public List<Affair> getMyAffair(Long userId) {
        return affairMapper.getAffairByPromoterId(userId);
    }

    /**
     * 获取我审批过的事务
     * @param userId 用户id
     * @return 事务列表
     */
    @Override
    public List<Affair> getMyApproval(Long userId) {
        return approvalMapper.getAffairByUserId(userId);
    }

    /**
     * 获取待我审批的事务
     * @param userId 用户id
     * @return 事务列表
     */
    @Override
    public List<Affair> getToApprove(Long userId) {
        User user = userMapper.selectOne(userId);
        return affairMapper.getAffairByApproverPost(user.getPost());
    }

    /**
     * 获取审批记录
     * @param userId 用户id
     * @param affairId 事务id
     * @return
     */
    @Override
    public List<ApprovalLog> getApproval(Long userId, Long affairId) {
        return approvalMapper.getApprovalLogByAffair(affairId);
    }

    @Override
    public void postSource(Long userId, Long affairId, MultipartFile[] files) {

        Affair affair = affairMapper.getAffairById(affairId);
        //判断是否可以上传
        if(!affair.getState().equals(AffairType.STATE_READY) &&
                !affair.getState().equals(AffairType.STATE_APPROVE_FAILED)) {
            log.error("目前不可上传报告草稿");
            throw new CustomException("目前不可上传报告草稿");
        }

        if(!affair.getPromoterId().equals(userId)) {
            log.error("此人不可上传报告草稿");
            throw new CustomException("此人不可上传报告草稿");
        }

        try {
            fileService.insertReport(affairId, files);
            Long pdfLogId = IDKeyUtil.generateId();
            FileLog fileLog = new FileLog(pdfLogId, new Date(), "pdf",
                    "audit-report", affairId + "/pdf/", "报告.pdf", 0L, affairId);
            Boolean ok = toPdf.genReport(affairId + "/source/");
            if(ok) {
                fileService.insertOne(fileLog);
                byte[] bytes = fileService.getFileData(pdfLogId);
                byte[] file = signUtil.signPdf(new ByteArrayInputStream(bytes), affairId, userId, "创建报告", new Date(), null);
                minioUtil.uploadFile("audit-report", affairId + "/pdf/"+ "报告.pdf", file);
            }else {
                log.error("转化生成pdf失败");
                throw new CustomException("转化生成pdf失败");
            }
        }catch (Exception e) {
            log.error("生成报告错误");
            throw new CustomException("生成报告错误");
        }

        affairMapper.updateState(AffairType.STATE_APPROVING, affairId);
    }

    @Override
    public List<FileLog> getSourceList(Long userId, Long affairId) {
        return fileService.ListReportFiles(affairId);
    }

    @Override
    public FileLog getPdf(Long userId, Long affairId) {
        return fileService.getPdf(affairId);
    }

    /**
     * 审批
     * @param userId 用户id
     * @param affairId 事务id
     * @param isPass 是否通过
     * @param msg 消息
     * @param ip ip地址
     * @param files 文件
     */
    @Override
    public void approve(Long userId, Long affairId, Boolean isPass, String msg, String ip, MultipartFile[] files) {

        Affair affair = affairMapper.getAffairById(affairId);
        User user = userMapper.selectOne(userId);
        Date nowTime = new Date();

        //判断是否可以审批
        if(!affair.getState().equals(AffairType.STATE_APPROVING)) {
            log.error("目前不可审批");
            throw new CustomException("目前不可审批");
        }

        if(!affair.getApproverPost().equals(user.getPost())) {
            log.error("此人不可审批");
            throw new CustomException("此人不可审批");
        }

        FileLog fileLog;
        if(files == null || files.length == 0) {
            fileLog = fileService.getPdf(affairId);
            try {
                byte[] bytes = fileService.getFileData(fileLog.getId());
                FileLog photoData = fileService.getSignature(userId);
                byte[] photo = fileService.getFileData(photoData.getId());
                byte[] file = signUtil.signPdf(new ByteArrayInputStream(bytes), affairId, userId,
                        isPass ? "审批通过" : "审批失败", nowTime, photo);
                minioUtil.uploadFile("audit-report", affairId + "/pdf/"+ "报告.pdf", file);
            }catch (Exception e) {
                log.error("审批签名错误");
                throw new CustomException("审批签名错误");
            }
        }else {
            Long pdfLogId = IDKeyUtil.generateId();
            fileLog = new FileLog(pdfLogId, new Date(), "pdf", "audit-report",
                    affairId + "/pdf/", "报告.pdf", 0L, affairId);
            try {
                fileService.insertReport(affairId, files);
                Boolean ok = toPdf.genReport(affairId + "/source/");
                if(ok) {
                    fileService.insertOne(fileLog);
                    byte[] bytes = fileService.getFileData(pdfLogId);
                    FileLog photoData = fileService.getSignature(userId);
                    byte[] photo = fileService.getFileData(photoData.getId());
                    byte[] file = signUtil.signPdf(new ByteArrayInputStream(bytes), affairId, userId, isPass ? "审批通过" : "审批失败", nowTime, photo);
                    minioUtil.uploadFile("audit-report", affairId + "/pdf/"+ "报告.pdf", file);
                }else {
                    log.error("转化为pdf失败");
                    throw new CustomException("转化生成pdf失败");
                }
            }catch (Exception e) {
                log.error("审批签名错误");
                throw new CustomException("审批签名错误");
            }
        }



        ApprovalLog approvalLog = new ApprovalLog();
        approvalLog.setId(IDKeyUtil.generateId());
        approvalLog.setAffairId(affairId);
        approvalLog.setIsPass(isPass);
        approvalLog.setMsg(msg);
        approvalLog.setAuthorId(userId);
        approvalLog.setCreateTime(new Date());
        approvalLog.setIp(ip);
        approvalLog.setIsEditSource(files != null && files.length != 0);

        approvalMapper.insertOne(approvalLog);

        if(isPass) {
            if(affair.getApproverPost() < 3) {
                affairMapper.updateState(AffairType.STATE_APPROVING, affairId);
                affairMapper.nextApprover(affairId);
            }else {
                affairMapper.updateState(AffairType.STATE_END, affairId);
                affairMapper.updateEndTime(affairId, nowTime);
            }
        }else {
            affairMapper.updateState(AffairType.STATE_APPROVE_FAILED, affairId);
        }

    }


}
