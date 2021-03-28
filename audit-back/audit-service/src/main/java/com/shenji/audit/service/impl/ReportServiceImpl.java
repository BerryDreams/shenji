package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import com.shenji.audit.dao.AffairMapper;
import com.shenji.audit.dao.ApprovalMapper;
import com.shenji.audit.dao.MaterialMapper;
import com.shenji.audit.dao.ReportMapper;
import com.shenji.audit.model.Report;
import com.shenji.audit.service.MinioService;
import com.shenji.audit.service.ReportService;
import com.shenji.audit.type.AffairType;
import com.shenji.audit.type.MinioType;
import com.shenji.audit.utils.IDKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 报告服务类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 17:10
 */
@Service
@Transactional
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Resource
    private AffairMapper affairMapper;

    @Resource
    private ApprovalMapper approvalMapper;

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private MinioService minioService;


    @Override
    public void postSource(Long userId, Long affairId, String name, List<FileData> fileList) {

        //判断是否可以上传
        if(!affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_READY) &&
                !affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_APPROVE_FAILED)) {
            log.error("目前不可上传报告草稿");
            throw new CustomException("目前不可上传报告草稿");
        }

        Long reportId = IDKeyUtil.generateId();

        try {
            minioService.putDocument(reportId, MinioType.SOURCE_REPORT, fileList);
            minioService.putDocument(reportId, MinioType.PDF_REPORT, fileList);
        }catch (CustomException e) {
            log.error(e.getMsg());
        }

        //上传草稿
        Report report = new Report();
        report.setId(reportId);
        report.setAffairId(affairId);
        report.setName(name);
        reportMapper.insertOne(report);

        affairMapper.updateState(AffairType.STATE_APPROVING, affairId);

    }

    @Override
    public List<String> getPDFList(Long userId, Long reportId) {
        return minioService.getDocumentList(reportId, MinioType.PDF_REPORT);
    }

    @Override
    public FileData getPDF(Long userId, Long reportId, String filename) {
        return minioService.getDocument(reportId, MinioType.PDF_REPORT, filename);
    }

    @Override
    public List<String> getSourceList(Long userId, Long reportId) {
        return minioService.getDocumentList(reportId, MinioType.SOURCE_REPORT);
    }

    @Override
    public FileData getSource(Long userId, Long reportId, String filename) {
        return minioService.getDocument(reportId, MinioType.SOURCE_REPORT, filename);
    }
}
