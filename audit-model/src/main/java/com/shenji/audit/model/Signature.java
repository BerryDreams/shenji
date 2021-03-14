package com.shenji.audit.model;

import java.util.Date;

/**
 * 电子签名实体类
 *
 * @author LWT
 * @date 2021/3/14
 */

//@Data
public class Signature {
    private Integer owner_id;           //电子签名拥有者id
    private Date date;                  //签名时间戳
    private Affair related_affair;      //相关事务
}