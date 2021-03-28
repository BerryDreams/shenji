package com.shenji.audit.service;

import com.shenji.audit.common.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 资料文件服务类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 9:14
 */
public interface MinioService {

    void putDocument(Long id, String type, List<FileData> fileList);

    FileData getDocument(Long id, String type, String filename);

    List<String> getDocumentList(Long id, String type);

    void removeDocument(Long id, String type);
}
