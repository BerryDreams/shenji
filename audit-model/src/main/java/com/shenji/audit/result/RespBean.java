package com.shenji.audit.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 8:27
 */
@Data
@ApiModel(value = "返回结果")
public class RespBean {

    @ApiModelProperty(value = "返回类型编号")
    private Integer status;
    @ApiModelProperty(value = "返回结果描述")
    private String msg;
    @ApiModelProperty(value = "返回结果内容")
    private Object content;

    private RespBean() {
    }

    private RespBean(Integer status, String msg, Object content) {
        this.status = status;
        this.msg = msg;
        this.content = content;
    }

    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean build(RespBeanType respBeanType) {
        return new RespBean(respBeanType.getStatus(), respBeanType.getMsg(), null);
    }

    public static RespBean build(Object obj, RespBeanType respBeanType) {
        return new RespBean(respBeanType.getStatus(), respBeanType.getMsg(), obj);
    }
}
