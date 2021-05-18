package com.shenji.audit.dao;

import com.shenji.audit.model.User;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 19:06
 */
public interface UserMapper {

    User selectOne(Long id);

    User selectByUsername(String username);

    void addOne(User user);
}
