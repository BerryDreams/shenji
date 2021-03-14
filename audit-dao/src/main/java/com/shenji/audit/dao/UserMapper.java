package com.shenji.audit.dao;

import com.shenji.audit.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/13 13:18
 */
public interface UserMapper {
    List<User> selectAll();
}
