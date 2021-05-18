package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.dao.FileLogMapper;
import com.shenji.audit.model.FileLog;
import com.shenji.audit.service.FileService;
import com.shenji.audit.utils.IDKeyUtil;
import com.shenji.audit.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 文件服务
 *
 * @author misxr
 * @version 1.0
 * @date 2021/5/18 10:03
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final String MATERIAL = "audit-material";
    private final String SIGNATURE = "audit-signature";
    private final String REPORT = "audit-report";
    private final String TEMPLATE = "audit-template";

    @Autowired
    private MinioUtil minioUtil;

    @Resource
    private FileLogMapper fileLogMapper;

    @Override
    public void insertMaterial(Long materialId, MultipartFile[] files) {
        try {
            for(MultipartFile file : files) {
                String prefix = materialId + "/";
                Long fileLogId = IDKeyUtil.generateId();
                FileLog fileLog = new FileLog(fileLogId, new Date(), "material", MATERIAL, prefix, file.getOriginalFilename(), file.getSize(), materialId);
                fileLogMapper.insertOne(fileLog);
                minioUtil.uploadFile(MATERIAL, prefix + file.getOriginalFilename(), file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertReport(Long affairId, MultipartFile[] files) {
        try {
            for(MultipartFile file : files) {
                String prefix = affairId + "/source/";
                Long fileLogId = IDKeyUtil.generateId();
                FileLog fileLog = new FileLog(fileLogId, new Date(), "report", REPORT, prefix, file.getOriginalFilename(), file.getSize(), affairId);
                fileLogMapper.insertOne(fileLog);
                minioUtil.uploadFile(REPORT, prefix + file.getOriginalFilename(), file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertSignature(Long userId, MultipartFile file) {
        try {
            String prefix = userId + "/";
            Long fileLogId = IDKeyUtil.generateId();
            FileLog fileLog = new FileLog(fileLogId, new Date(), "signature", SIGNATURE, prefix, file.getOriginalFilename(), file.getSize(), userId);
            fileLogMapper.insertOne(fileLog);
            minioUtil.uploadFile(SIGNATURE, prefix + file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertTemplate(Long templateId, MultipartFile file) {
        try {
            String prefix = templateId + "/";
            Long fileLogId = IDKeyUtil.generateId();
            FileLog fileLog = new FileLog(fileLogId, new Date(), "template", TEMPLATE, prefix, file.getOriginalFilename(), file.getSize(), templateId);
            fileLogMapper.insertOne(fileLog);
            minioUtil.uploadFile(TEMPLATE, prefix + file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertOne(FileLog fileLog) {
        fileLogMapper.insertOne(fileLog);
    }

    @Override
    public List<FileLog> listMaterialFiles(Long materialId) {
        return fileLogMapper.listItems(materialId, "material");
    }

    @Override
    public List<FileLog> ListReportFiles(Long affairId) {
        return fileLogMapper.listItems(affairId, "report");
    }

    @Override
    public FileLog getSignature(Long userId) throws CustomException {
        List<FileLog> fileLogs = fileLogMapper.listItems(userId, "signature");
        if(fileLogs.size() == 1) {
            return fileLogs.get(0);
        }
        throw new CustomException("500", "获取文件列表错误");
    }

    @Override
    public FileLog getTemplate(Long templateId) throws CustomException {
        List<FileLog> fileLogs = fileLogMapper.listItems(templateId, "template");
        if(fileLogs.size() == 1) {
            return fileLogs.get(0);
        }
        throw new CustomException("500", "获取文件列表错误");
    }

    @Override
    public FileLog getPdf(Long affairId) {
        List<FileLog> fileLogs = fileLogMapper.listItems(affairId, "pdf");
        if(fileLogs.size() == 1) {
            return fileLogs.get(0);
        }
        throw new CustomException("500", "获取文件列表错误");
    }

    @Override
    public FileLog getFileLog(Long fileLogId) throws CustomException {
        return fileLogMapper.selectOne(fileLogId);
    }

    @Override
    public void delFile(Long fileLogId) {
        FileLog fileLog = fileLogMapper.selectOne(fileLogId);
        minioUtil.delFile(fileLog.getBucketName(), fileLog.getPrefix() + fileLog.getFilename());
        fileLogMapper.delOne(fileLogId);
    }

    @Override
    public void updateFile(Long fileLogId, MultipartFile file) {
        try{
            FileLog fileLog = fileLogMapper.selectOne(fileLogId);
            fileLogMapper.updateOne(fileLogId, file.getOriginalFilename(), file.getSize(), new Date());
            minioUtil.uploadFile(fileLog.getBucketName(), fileLog.getPrefix() + file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getFileData(Long fileLogId) {
        FileLog fileLog = fileLogMapper.selectOne(fileLogId);
        return minioUtil.getFileData(fileLog.getBucketName(), fileLog.getPrefix() + fileLog.getFilename());
    }
}
