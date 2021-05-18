package com.shenji.audit.service;

import com.shenji.audit.model.FileLog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件服务接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/5/18 10:03
 */
public interface FileService {

    void insertMaterial(Long materialId, MultipartFile[] files);

    void insertSignature(Long userId, MultipartFile file);

    void insertTemplate(Long templateId, MultipartFile file);

    void insertReport(Long affairId, MultipartFile[] files);

    void insertOne(FileLog fileLog);

    List<FileLog> listMaterialFiles(Long materialId);

    List<FileLog> ListReportFiles(Long affairId);

    FileLog getSignature(Long userId);

    FileLog getTemplate(Long templateId);

    FileLog getPdf(Long affairId);

    FileLog getFileLog(Long fileLogId);

    void delFile(Long fileLogId);

    void updateFile(Long fileLogId, MultipartFile file);

    byte[] getFileData(Long fileLogId);
}
