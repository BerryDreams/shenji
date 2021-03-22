package com.shenji.audit.model;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 16:04
 */
@Data
public class Material {

    private Integer id;
    private String name;           //材料名称
    private Integer affairId;      //所属的事务
    private Date createTime;      //创建时间
    private Integer author;       //上传用户id
    private String contentUrl;    //存储地址
}
