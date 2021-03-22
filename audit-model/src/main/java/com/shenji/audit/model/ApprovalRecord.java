package com.shenji.audit.model;

import lombok.Data;

import java.util.Date;

/**
 * 签名记录
 *
 * @author LWT
 * @date 2021/3/14
 */

@Data
public class ApprovalRecord {
    private Integer id;
    private Boolean ok;             //是否审批通过
    private Date date;              //审批时间
    private String ip;              //审批人ip地址
    private Integer authorId;       //审批人id
    private Integer affairId;       //审批的事务
    private String message;         //审批信息，给予的建议
}