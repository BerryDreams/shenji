package com.shenji.audit.service.impl;

import com.shenji.audit.common.FileData;
import com.shenji.audit.dao.*;
import com.shenji.audit.model.*;
import com.shenji.audit.common.CustomException;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.type.AffairType;
import com.shenji.audit.type.MinioType;
import com.shenji.audit.utils.IDKeyUtil;
import com.shenji.audit.utils.MinioUtil;
import com.shenji.audit.utils.ToPdf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public List<Affair> toApprove(Long userId) {
        User user = userMapper.selectOne(userId);
        return affairMapper.getAffairByApproverPost(user.getPost());
    }

    @Override
    public List<ApprovalLog> getApproval(Long userId, Long affairId) {
        return approvalMapper.getApprovalLogByAffair(affairId);
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
    public void approve(Long userId, Long affairId, Boolean isPass, String msg, String ip, List<FileData> files) {

        Affair affair = affairMapper.getAffairById(affairId);
        User user = userMapper.selectOne(userId);

        //判断是否可以审批
        if(!affair.getState().equals(AffairType.STATE_APPROVING)) {
            log.error("目前不可审批");
            throw new CustomException("目前不可审批");
        }

        if(!affair.getApproverPost().equals(user.getPost())) {
            log.error("此人不可审批");
            throw new CustomException("此人不可审批");
        }

        try {
            for(FileData file : files) {
                minioUtil.uploadFile(MinioType.sourcePrefix(affairId) + file.getName(), file.getData());
            }
            toPdf.genReport(MinioType.sourcePrefix(affairId));
        }catch (CustomException e) {
            log.error(e.getMsg());
        }

        ApprovalLog approvalLog = new ApprovalLog();
        approvalLog.setId(IDKeyUtil.generateId());
        approvalLog.setAffairId(affairId);
        approvalLog.setIsPass(isPass);
        approvalLog.setMsg(msg);
        approvalLog.setAuthorId(userId);
        approvalLog.setCreateTime(new Date());
        approvalLog.setIp(ip);
        approvalLog.setIsEditSource(files != null && files.size() != 0);

        approvalMapper.insertOne(approvalLog);

        if(isPass) {
            if(affair.getApproverPost() < 3) {
                affairMapper.updateState(AffairType.STATE_APPROVING, affairId);
                affairMapper.nextApprover(affairId);
            }else {
                affairMapper.updateState(AffairType.STATE_END, affairId);
            }
        }else {
            affairMapper.updateState(AffairType.STATE_APPROVE_FAILED, affairId);
        }

    }

}
