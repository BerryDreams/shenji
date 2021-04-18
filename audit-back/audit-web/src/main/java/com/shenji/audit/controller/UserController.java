package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.User;
import com.shenji.audit.type.RespType;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/4/17 11:57
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户相关")
public class UserController {

    @PostMapping("/login")
    @ApiOperation("登录")
    public RespBean login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        return RespBean.build(RespType.OK);
    }
}
