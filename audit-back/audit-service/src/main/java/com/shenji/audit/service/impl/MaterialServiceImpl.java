package com.shenji.audit.service.impl;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import com.shenji.audit.dao.AffairMapper;
import com.shenji.audit.dao.MaterialMapper;
import com.shenji.audit.model.MaterialLog;
import com.shenji.audit.service.MaterialService;
import com.shenji.audit.type.AffairType;
import com.shenji.audit.type.MinioType;
import com.shenji.audit.utils.IDKeyUtil;
import com.shenji.audit.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private MinioUtil minioUtil;

    @Override
    public void uploadMaterial(Long userId, Long affairId, String name, String remark, List<FileData> fileList){

        //判断是否可以上传
        if(!affairMapper.getAffairById(affairId).getState().equals(AffairType.STATE_READY)) {
            log.error("目前不可上传资料");
            throw new CustomException("目前不可上传资料");
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
            for(FileData file : fileList) {
                minioUtil.uploadFile(MinioType.materialPrefix(affairId, materialLogId) + file.getName(), file.getData());
            }
        }catch (CustomException e) {
            log.error(e.getMsg());
        }

    }

    @Override
    public List<MaterialLog> getMaterialList(Long userId, Long affairId) {
        return materialMapper.getMaterialByAffairId(affairId);
    }

    @Override
    public List<String> getMaterialFolder(Long userId, Long materialId) {
        MaterialLog materialLog = materialMapper.selectOne(materialId);
        return minioUtil.listFile(MinioType.materialPrefix(materialLog.getAffairId(), materialId));
    }

    @Override
    public FileData getMaterial(Long userId, Long materialId, String filename) {
        MaterialLog materialLog = materialMapper.selectOne(materialId);
        return minioUtil.getFileData(MinioType.materialPrefix(materialLog.getAffairId(), materialId) + filename);
    }

    @Override
    public void delMaterial(Long userId, Long materialId) {
        MaterialLog materialLog = materialMapper.selectOne(materialId);
        minioUtil.delFolder(MinioType.materialPrefix(materialLog.getAffairId(), materialId));
    }

    @Override
    public void delFile(Long userId, Long materialId, String filename) {
        MaterialLog materialLog = materialMapper.selectOne(materialId);
        minioUtil.delFile(MinioType.materialPrefix(materialLog.getAffairId(), materialId) + filename);
    }
}
