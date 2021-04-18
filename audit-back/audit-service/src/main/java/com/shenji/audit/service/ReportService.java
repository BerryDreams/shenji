package com.shenji.audit.service;

import com.shenji.audit.common.FileData;

import java.util.List;

/**
 * 报告服务接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 17:10
 */
public interface ReportService {

    void postSource(Long userId, Long affairId, List<FileData> fileList);

    FileData getPDF(Long userId, Long affairId);

    List<String> getSourceList(Long userId, Long affairId);

    FileData getSource(Long userId, Long affairId, String filename);
}
