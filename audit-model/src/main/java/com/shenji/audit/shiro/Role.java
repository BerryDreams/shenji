package com.shenji.audit.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/16 7:30
 */

@Data
@AllArgsConstructor
public class Role {

    private Integer id;
    private String name;

    private Set<Permission> permissions;
}
