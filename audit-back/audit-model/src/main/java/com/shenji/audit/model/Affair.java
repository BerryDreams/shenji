package com.shenji.audit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @JsonSerialize(using=ToStringSerializer.class)
    private Long id;                    //事务id
    private String name;                //事务名
    private String remark;              //备注
    private Integer kind;               //事务类型
    private Integer state;              //审批状态

    private Date startTime;             //创建时间
    private Date endTime;               //结束时间

    @JsonSerialize(using=ToStringSerializer.class)
    private Long promoterId;            //事务发起人id
    private Integer approverPost;            //当前审批人职务
}