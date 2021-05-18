package com.shenji.audit.service.impl;

import com.shenji.audit.dao.UserMapper;
import com.shenji.audit.model.FileLog;
import com.shenji.audit.model.User;
import com.shenji.audit.service.FileService;
import com.shenji.audit.service.UserService;
import com.shenji.audit.utils.IDKeyUtil;
import com.shenji.audit.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/5/10 22:26
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Override
    public User selectOne(Long userId) {
        return userMapper.selectOne(userId);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public void addOne(String username, String password, String name, Integer post) {
        User user = new User();
        user.setId(IDKeyUtil.generateId());
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPost(post);
        userMapper.addOne(user);
    }

    @Override
    public void addSign(Long userId, MultipartFile file) {
        fileService.insertSignature(userId, file);
    }

    @Override
    public FileLog getSign(Long userId) {
        return fileService.getSignature(userId);
    }
}
