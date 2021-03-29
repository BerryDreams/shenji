package com.shenji.audit.service.impl;

import com.shenji.audit.common.FileData;
import com.shenji.audit.dao.*;
import com.shenji.audit.model.*;
import com.shenji.audit.common.CustomException;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.service.MinioService;
import com.shenji.audit.type.*;
import com.shenji.audit.utils.IDKeyUtil;
import com.shenji.audit.utils.ToPdf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private MinioService minioService;

    /**
     *
     * @param userId 发起人
     * @param name 名称
     * @param remark 详情
     * @param kind 类型
     */
    @Override
    public void startAffair(Long userId, String name, String remark, Integer kind) {

        Affair affair = new Affair();
        affair.setId(IDKeyUtil.generateId());
        affair.setPromoterId(userId);
        affair.setApproverId(2L);

        affair.setKind(kind);
        affair.setState(AffairType.STATE_READY);
        affair.setName(name);
        affair.setRemark(remark);

        affair.setStartTime(new Date());
        affair.setEndTime(null);


        affairMapper.insertOne(affair);
    }

    @Override
    public List<Affair> getAllAffair(Long userId) {
        return affairMapper.selectAll();
    }

    @Override
    public List<Affair> getMyAffair(Long userId) {
        return affairMapper.getAffairByPromoterId(userId);
    }

    @Override
    public List<Affair> getMyApproval(Long userId) {
        return affairMapper.getAffairByApproverId(userId);
    }

    @Override
    public void approve(Long userId, Long affairId, Boolean isPass, String msg, String ip, List<FileData> files) {

        //判断是否可以审批
        if(!affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_APPROVING)) {
            log.error("目前不可审批");
            throw new CustomException("目前不可审批");
        }

        try {
            minioService.putSomeDocument(affairId, MinioType.SOURCE_REPORT, files);
            minioService.putDocument(affairId, MinioType.PDF_REPORT, ToPdf.genReport(files));
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
            affairMapper.updateState(AffairType.STATE_END, affairId);
        }else {
            affairMapper.updateState(AffairType.STATE_APPROVE_FAILED, affairId);
            affairMapper.nextApprover();
        }

    }

}
