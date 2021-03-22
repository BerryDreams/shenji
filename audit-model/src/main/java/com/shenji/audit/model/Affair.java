package com.shenji.audit.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 事务实体类
 *
 * @author LWT
 * @date 2021/3/14
 */

@Data
public class Affair {
    private Integer id;                 //事务id
    private String remark;              //备注详情
    private String kind;                //事务类型（小事务无收集资料状态）
    private Integer state;               //事务状态（收集资料中，编写草稿中，审批中，审批结束）

    private Date startTime;           //创建时间
    private Date endTime;              //结束时间

    private Integer approverId;      //事务当前审批人id
    private Integer promoters;       //事务发起人id
}