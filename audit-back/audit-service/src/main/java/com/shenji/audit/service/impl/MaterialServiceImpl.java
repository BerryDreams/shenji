package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.dao.AffairMapper;
import com.shenji.audit.dao.MaterialMapper;
import com.shenji.audit.model.FileLog;
import com.shenji.audit.model.MaterialLog;
import com.shenji.audit.service.FileService;
import com.shenji.audit.service.MaterialService;
import com.shenji.audit.common.AffairType;
import com.shenji.audit.utils.IDKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 实现资料服务类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 12:46
 */
@Service
@Transactional
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    @Resource
    private AffairMapper affairMapper;

    @Resource
    private MaterialMapper materialMapper;

    @Autowired
    private FileService fileService;

    @Override
    public void uploadMaterial(Long userId, Long affairId, String name, String remark, MultipartFile[] files){

        //判断是否可以上传
        if(!affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_READY)) {
            log.error("目前不可上传资料");
        }

        Long materialLogId = IDKeyUtil.generateId();


        //插入材料
        MaterialLog materialLog = new MaterialLog();
        materialLog.setId(materialLogId);
        materialLog.setAffairId(affairId);
        materialLog.setCreateTime(new Date());
        materialLog.setAuthorId(userId);
        materialLog.setName(name);
        materialLog.setRemark(remark);
        materialMapper.insertOne(materialLog);

        try {
            fileService.insertMaterial(materialLogId, files);
        }catch (CustomException e) {
            log.error(e.getMsg());
            throw e;
        }
    }

    @Override
    public List<MaterialLog> getMaterialList(Long userId, Long affairId) {
        return materialMapper.getMaterialByAffairId(affairId);
    }

    @Override
    public List<FileLog> getMaterialFolder(Long userId, Long materialId) {
        return fileService.listMaterialFiles(materialId);
    }

    @Override
    public void delMaterial(Long userId, Long materialId) {
        MaterialLog materialLog = materialMapper.selectOne(materialId);
        List<FileLog> fileLogList = fileService.listMaterialFiles(materialId);
        for(FileLog fileLog : fileLogList) {
            fileService.delFile(fileLog.getId());
        }
    }
}
