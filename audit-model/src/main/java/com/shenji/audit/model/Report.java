package com.shenji.audit.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 报告实体类
 *
 * @author LWT
 * @date 2021/3/14
 */

@Data
public class Report {
    private String id;                 //报告id
    private String contentUrl;          //报告内容存放地址
    private Integer affairId;           //相关事务
}