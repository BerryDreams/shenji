package com.shenji.audit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 16:04
 */
@Data
public class MaterialLog {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String name;            //材料名称
    private String remark;          //备注
    @JsonSerialize(using=ToStringSerializer.class)
    private Long affairId;          //所属的事务
    private Date createTime;        //创建时间
    @JsonSerialize(using=ToStringSerializer.class)
    private Long authorId;          //上传用户id
}
