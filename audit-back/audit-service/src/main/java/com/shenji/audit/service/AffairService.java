package com.shenji.audit.service;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.Affair;
import com.shenji.audit.type.RespType;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
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

    List<Affair> getMyAffair(Long userId);

    List<Affair> getMyApproval(Long userId);

    void approve(Long userId, Long affairId, Boolean isPass, String msg, String ip, List<FileData> files);
}
