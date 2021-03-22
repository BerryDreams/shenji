package com.shenji.audit.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 16:33
 */
@AllArgsConstructor
@Getter
public enum AffairStateType {

    COLLECTING_MATERIAL(10, "收集资料中"),
    WRITTING(11, "编写审计报告中"),
    APPROVING(12, "审批中"),
    END(12, "审批结束");


    private final Integer state;
    private final String msg;
}
