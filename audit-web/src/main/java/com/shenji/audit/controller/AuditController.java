package com.shenji.audit.controller;

import com.shenji.audit.result.RespBean;
import com.shenji.audit.type.RespType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审计接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/20 14:55
 */
@Api("审计接口")
@RestController
@RequestMapping("/api")
public class AuditController {

    @GetMapping("/template/download")
    @ApiOperation("shiro测试")
    public RespBean downloadTemplate() {

        return RespBean.build("hello", RespType.OK);
    }
}
