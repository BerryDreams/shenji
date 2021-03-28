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

    private Long id;                 //报告id
    private String name;             //报告名称
    private Long affairId;           //相关事务
}