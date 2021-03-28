package com.shenji.audit.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 8:33
 */
@AllArgsConstructor
@Getter
public enum RespType {

    OK("00200","成功"),

    USER_INPUT_ERROR("00400", "用户输入错误");



    private final String status;
    private final String msg;
}
