package com.shenji.audit.result;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 8:33
 */
public enum StatusType {

    OK("00200","成功"),

    USER_INPUT_ERROR("00400", "用户输入错误");



    private final String status;
    private final String msg;

    StatusType(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
