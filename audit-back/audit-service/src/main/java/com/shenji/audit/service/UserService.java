package com.shenji.audit.service;

import com.shenji.audit.model.FileLog;
import com.shenji.audit.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User selectOne(Long userId);

    User selectByUsername(String username);

    void addOne(String username, String password, String name, Integer post);

    void addSign(Long userId, MultipartFile file);

    FileLog getSign(Long userId);
}
