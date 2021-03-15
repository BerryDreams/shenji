package com.shenji.audit.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 8:33
 */
public enum RespBeanType {

    OK(10200,"成功");

    private final Integer status;
    private final String msg;

    RespBeanType(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
