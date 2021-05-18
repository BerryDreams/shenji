package com.shenji.audit.service;

import com.shenji.audit.model.Affair;
import com.shenji.audit.model.ApprovalLog;
import com.shenji.audit.model.FileLog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 项目流程事务
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 15:29
 */
public interface AffairService {

    /**
     *
     * @param userId 发起人
     * @param name 名称
     * @param kind 类型
     * @param remark 详情
     */
    Long startAffair(Long userId, String name, String remark, Integer kind);

    Affair getAffair(Long userId, Long affairId);

    List<Affair> getHistory(Long userId);

    List<Affair> getAllAffair(Long userId);

    List<Affair> getMyAffair(Long userId);

    List<Affair> getMyApproval(Long userId);

    List<Affair> getToApprove(Long userId);

    List<ApprovalLog> getApproval(Long userId, Long affairId);

    void postSource(Long userId, Long affairId, MultipartFile[] files);

    List<FileLog> getSourceList(Long userId, Long affairId);

    FileLog getPdf(Long userId, Long affairId);

    void approve(Long userId, Long affairId, Boolean isPass, String msg, String ip, MultipartFile[] files);
}
