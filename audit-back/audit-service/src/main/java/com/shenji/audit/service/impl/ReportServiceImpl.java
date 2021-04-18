package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import com.shenji.audit.dao.AffairMapper;
import com.shenji.audit.dao.ApprovalMapper;
import com.shenji.audit.dao.MaterialMapper;
import com.shenji.audit.service.ReportService;
import com.shenji.audit.type.AffairType;
import com.shenji.audit.type.MinioType;
import com.shenji.audit.utils.MinioUtil;
import com.shenji.audit.utils.ToPdf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
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


    @Override
    public void postSource(Long userId, Long affairId, List<FileData> fileList) {

        //判断是否可以上传
        if(!affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_READY) &&
                !affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_APPROVE_FAILED)) {
            log.error("目前不可上传报告草稿");
            throw new CustomException("目前不可上传报告草稿");
        }

        try {
            for(FileData file : fileList) {
                MinioUtil.uploadFile(MinioType.sourcePrefix(affairId) + file.getName(), file.getData());
            }
            ToPdf.genReport(MinioType.pdfPrefix(affairId) + "报告.pdf", fileList);
        }catch (CustomException e) {
            log.error(e.getMsg());
        }

        //上传草稿
        affairMapper.updateState(AffairType.STATE_APPROVING, affairId);
    }

    @Override
    public FileData getPDF(Long userId, Long affairId) {
        return MinioUtil.getFileData(MinioType.pdfPrefix(affairId) + "报告.pdf");
    }

    @Override
    public List<String> getSourceList(Long userId, Long affairId) {
        return MinioUtil.listFile(MinioType.sourcePrefix(affairId));
    }

    @Override
    public FileData getSource(Long userId, Long affairId, String filename) {
        return MinioUtil.getFileData(MinioType.sourcePrefix(affairId) + filename);
    }
}
