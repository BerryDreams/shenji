package com.shenji.audit.model;

import com.shenji.audit.shiro.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Set;

/**
 * 用户实体类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/12 11:22
 */
@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private Set<Role> roles;
}
