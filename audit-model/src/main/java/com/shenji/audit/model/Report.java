package com.shenji.audit.model;

import java.util.List;

/**
 * 报告实体类
 *
 * @author LWT
 * @date 2021/3/14
 */

//@Data
public class Report {
    private Integer id;                 //报告id
    private List<Template> templates;   //相关模板
    private List<User> owners;          //报告拥有者
    private String content;             //报告内容
    private String information;         //报告相关信息
}