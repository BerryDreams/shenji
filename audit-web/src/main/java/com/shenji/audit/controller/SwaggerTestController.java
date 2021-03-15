package com.shenji.audit.controller;

import com.shenji.audit.result.RespBean;
import com.shenji.audit.result.RespBeanType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 8:23
 */
@Api("接口测试接口")
@RestController
@RequestMapping("/api")
public class SwaggerTestController {

    @GetMapping("/swagger/test")
    @ApiOperation("swagger测试")
    public RespBean testSwagger(@ApiParam(name = "input", value="hello的值")@RequestParam(value = "input") String hello) {
        return RespBean.build(hello, RespBeanType.OK);
    }
}
