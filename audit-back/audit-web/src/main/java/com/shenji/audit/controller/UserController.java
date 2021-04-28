package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.dao.UserMapper;
import com.shenji.audit.model.User;
import com.shenji.audit.type.RespType;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private UserMapper userMapper;

    @PostMapping("/login")
    @ApiOperation("登录获取token")
    public RespBean login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        if(!StringUtils.isNotEmpty(username) || !StringUtils.isNotEmpty(password)) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        User user = userMapper.selectByUsername(username);
        if(user == null || !password.equals(user.getPassword())) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        return RespBean.build(JwtUtil.createToken(user.getId()), RespType.OK);
    }

    @PostMapping("/refresh")
    @ApiOperation("刷新token")
    public RespBean refresh(@RequestHeader("token")String token) {
        String newToken = JwtUtil.refreshToken(token);
        return RespBean.build(newToken, RespType.OK);
    }

    @GetMapping("/user")
    @ApiOperation("获取用户信息")
    public RespBean getUser(@RequestHeader("token")String token) {
        Long userId = JwtUtil.decodeToken(token);
        return RespBean.build(userMapper.selectOne(userId), RespType.OK);
    }
}
