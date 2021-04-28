package com.shenji.audit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using=ToStringSerializer.class)
    private Long affairId;         //所属的事务
    private Date createTime;       //审批时间
    private Boolean isPass;        //是否通过
    private Boolean isEditSource;  //是否修改附件
    @JsonSerialize(using=ToStringSerializer.class)
    private Long authorId;         //审批人id
    @JsonIgnore
    private String ip;             //审批人ip
    private String msg;            //审批结论
}
