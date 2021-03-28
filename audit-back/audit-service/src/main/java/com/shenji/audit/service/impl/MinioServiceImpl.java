package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import com.shenji.audit.service.MinioService;
import com.shenji.audit.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 资料管理服务
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 9:14
 */
@Service
@Transactional
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Override
    public void putDocument(Long id, String type, List<FileData> fileList) {
        for(FileData file : fileList) {
            String innerPath = getInnerPath(type, id, file.getName());
            InputStream is = new ByteArrayInputStream(file.getData());
            MinioUtil.uploadFile(is, innerPath, file.getName());
        }
    }

    @Override
    public FileData getDocument(Long id, String type, String filename) {
        FileData fileData = new FileData();
        try {
            InputStream is = MinioUtil.getFile(getInnerPath(type, id, filename));
            fileData.setName(filename);
            fileData.setData(is.readAllBytes());
        }catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        return fileData;
    }

    @Override
    public List<String> getDocumentList(Long id, String type) {
        List<FileData> fileDataList = new ArrayList<>();
        String prefix = getPrefix(type, id);

        return MinioUtil.listFile(prefix);
    }

    @Override
    public void removeDocument(Long id, String type) {

        String prefix = getPrefix(type, id);

        List<String> fileNameList = MinioUtil.listFile(prefix);
        MinioUtil.delFile(fileNameList);
    }

    private String getPrefix(String kind, Long id) {
        return kind + "/" + id.toString() + "/";
    }

    private String getInnerPath(String kind, Long id, String filename) {
        return kind + "/" + id.toString() + "/" + filename;
    }
}
