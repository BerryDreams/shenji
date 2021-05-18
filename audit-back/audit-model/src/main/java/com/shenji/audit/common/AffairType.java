package com.shenji.audit.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 16:33
 */
public class AffairType {

    /**
     * 事务状态state
     */
    public static final Integer STATE_READY = 10;                  //准备好
    public static final Integer STATE_APPROVING = 11;              //审批中
    public static final Integer STATE_APPROVE_FAILED = 12;         //审批失败
    public static final Integer STATE_END = 13;                    //审批结束


    /**
     * 事务类型kind
     */
    public static final Integer KIND_REIMBURSEMENT_AUDIT = 20;    //报销审计
    public static final Integer KIND_FOLLOW_UP_AUDIT = 21;        //跟踪审计

}
