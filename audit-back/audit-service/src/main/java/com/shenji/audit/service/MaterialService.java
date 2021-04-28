package com.shenji.audit.service;

import com.shenji.audit.common.FileData;
import com.shenji.audit.model.MaterialLog;

import java.util.List;

/**
 * 材料服务类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 12:45
 */
public interface MaterialService {


    void uploadMaterial(Long userId, Long affairId, String name, String remark, List<FileData> fileList);

    List<MaterialLog> getMaterialList(Long userId, Long affairId);

    List<String> getMaterialFolder(Long userId, Long materialId);

    FileData getMaterial(Long userId, Long materialId, String filename);

    void delMaterial(Long userId, Long materialId);

    void delFile(Long userId, Long material, String filename);
}
