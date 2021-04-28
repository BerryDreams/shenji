package com.shenji.audit.dao;

import com.shenji.audit.model.Affair;
import com.shenji.audit.model.ApprovalLog;

import java.util.List;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 20:28
 */
public interface ApprovalMapper {

    void insertOne(ApprovalLog approvalLog);

    List<Affair> getAffairByUserId(Long userId);

    List<ApprovalLog> getApprovalLogByAffair(Long affairId);
}
