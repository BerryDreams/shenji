package com.shenji.audit.shiro.service;

import com.shenji.audit.dao.UserMapper;
import com.shenji.audit.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 21:53
 */
@Service
public class ShiroService {

    @Resource
    private UserMapper userMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
