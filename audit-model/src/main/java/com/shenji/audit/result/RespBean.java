package com.shenji.audit.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    private String status;
    @ApiModelProperty(value = "返回结果描述")
    private String msg;
    @ApiModelProperty(value = "返回结果内容")
    private Object content;

    private RespBean() {
    }

    private RespBean(String status, String msg, Object content) {
        this.status = status;
        this.msg = msg;
        this.content = content;
    }

    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean build(StatusType statusType) {
        return new RespBean(statusType.getStatus(), statusType.getMsg(), null);
    }

    public static RespBean build(Object obj, StatusType statusType) {
        return new RespBean(statusType.getStatus(), statusType.getMsg(), obj);
    }

    public static RespBean build(CustomException customException) {
        return new RespBean(customException.getStatus(), customException.getMsg(), null);
    }
}
