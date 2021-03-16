package com.shenji.audit.shiro.service;

import com.shenji.audit.dao.UserMapper;
import com.shenji.audit.model.User;
import com.shenji.audit.shiro.Permission;
import com.shenji.audit.shiro.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        Permission permissions1 = new Permission(1, "query");
        Permission permissions2 = new Permission(2, "add");
        Set<Permission> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        Role role = new Role(1, "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = new User(1, "wsl", "123456", "lwt", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user.getUsername(), user);

        Set<Permission> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role(2, "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User(2, "zhangsan", "123456", "czg", roleSet1);
        map.put(user1.getUsername(), user1);
        return map.get(username);
    }
}
