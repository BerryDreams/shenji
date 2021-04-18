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

    private Long id;                    //事务id
    private String name;                //事务名
    private String remark;              //备注
    private Integer kind;               //事务类型
    private Integer state;              //审批状态

    private Date startTime;             //创建时间
    private Date endTime;               //结束时间

    private Long promoterId;            //事务发起人id
    private Integer approverPost;            //当前审批人职务
}