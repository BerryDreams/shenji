package com.shenji.audit.model;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/23 11:06
 */
@Data
public class ApprovalLog {

    private Long id;
    private Long affairId;         //所属的事务
    private Date createTime;       //审批时间
    private Boolean isPass;        //是否通过
    private Long authorId;         //审批人id
    private String ip;             //审批人ip
    private String msg;            //审批结论
}
