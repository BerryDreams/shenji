package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.dao.UserMapper;
import com.shenji.audit.model.User;
import com.shenji.audit.service.UserService;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("登录获取token")
    public RespBean login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        if(!StringUtils.isNotEmpty(username) || !StringUtils.isNotEmpty(password)) {
            return RespBean.error("404", "用户名或密码错误");
        }
        User user = userService.selectByUsername(username);
        if(user == null || !password.equals(user.getPassword())) {
            return RespBean.error("404", "用户名或密码错误");
        }
        return RespBean.ok(JwtUtil.createToken(user.getId()));
    }

    @PostMapping("/reg")
    @ApiOperation("注册")
    public RespBean reg(String username, String password, String name, Integer post) {
        if(!StringUtils.isNotEmpty(username) || !StringUtils.isNotEmpty(password)) {
            return RespBean.error("404", "用户名或密码错误");
        }
        userService.addOne(username, password, name, post);
        return RespBean.ok();
    }

    @PostMapping("/sign")
    @ApiOperation("更新用户签名")
    public RespBean setSign(@RequestHeader("token")String token, @RequestParam("photo") MultipartFile photo) {
        Long userId = JwtUtil.decodeToken(token);
        userService.addSign(userId, photo);
        return RespBean.ok();
    }

    @GetMapping("/sign")
    @ApiOperation("获取用户签名信息")
    public RespBean getSign(@RequestHeader("token")String token) {
        Long userId = JwtUtil.decodeToken(token);
        return RespBean.ok(userService.getSign(userId));
    }

    @PostMapping("/refresh")
    @ApiOperation("刷新token")
    public RespBean refresh(@RequestHeader("token")String token) {
        String newToken = JwtUtil.refreshToken(token);
        return RespBean.ok(newToken);
    }

    @GetMapping("/user")
    @ApiOperation("获取用户信息")
    public RespBean getUser(@RequestHeader("token")String token) {
        Long userId = JwtUtil.decodeToken(token);
        return RespBean.ok(userService.selectOne(userId));
    }


}
