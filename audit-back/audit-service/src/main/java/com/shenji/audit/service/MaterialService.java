package com.shenji.audit.service;

import com.shenji.audit.model.FileLog;
import com.shenji.audit.model.MaterialLog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 材料服务类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 12:45
 */
public interface MaterialService {


    void uploadMaterial(Long userId, Long affairId, String name, String remark, MultipartFile[] fileList);

    List<MaterialLog> getMaterialList(Long userId, Long affairId);

    List<FileLog> getMaterialFolder(Long userId, Long materialId);

    void delMaterial(Long userId, Long materialId);
}
