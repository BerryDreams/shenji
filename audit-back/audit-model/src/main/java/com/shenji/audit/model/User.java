package com.shenji.audit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class User {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;        //用户id
    private String username;   //用户名
    @JsonIgnore
    private String password;   //用户密码
    private String name;       //用户姓名
    private Integer post;       //用户职务
}
