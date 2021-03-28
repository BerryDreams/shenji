package com.shenji.audit.model;

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

    private Long id;
    private String name;            //材料名称
    private String remark;          //备注
    private Long affairId;          //所属的事务
    private Date createTime;        //创建时间
    private Long authorId;          //上传用户id
}
