package com.shenji.audit.service;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import com.shenji.audit.model.Affair;
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
    void startAffair(Long userId, String name, String remark, Integer kind);

    List<Affair> getAllAffair(Long userId);
}
