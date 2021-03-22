package com.shenji.audit.controller;

import com.alibaba.druid.util.StringUtils;
import com.shenji.audit.model.User;
import com.shenji.audit.result.RespBean;
import com.shenji.audit.type.RespType;
import com.shenji.audit.shiro.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.*;

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
public class TestController {

    @GetMapping("/swagger/test")
    @ApiOperation("swagger测试")
    public RespBean testSwagger(@ApiParam(name = "input", value="hello的值")@RequestParam(value = "input") String hello) {
        return RespBean.build(hello, RespType.OK);
    }

    @GetMapping("/shiro/login")
    @ApiOperation("shiro测试")
    public RespBean testShiroLogin(@ApiParam(name = "username&password", value="hello的值")User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUsername(),
                user.getPassword()
        );
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
        }catch (Exception e) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        return RespBean.build(JWTUtil.generateToken(user.getUsername()), RespType.OK);
    }
}
