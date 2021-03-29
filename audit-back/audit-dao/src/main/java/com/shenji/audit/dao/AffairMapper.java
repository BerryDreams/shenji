package com.shenji.audit.dao;

import com.shenji.audit.model.Affair;

import java.util.List;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 20:03
 */
public interface AffairMapper {

    void insertOne(Affair affair);

    Affair getAffairById(Long affairId);

    List<Affair> getAffairByPromoterId(Long promoterId);

    List<Affair> getAffairByApproverId(Long approverId);

    List<Affair> selectAll();

    void updateState(Integer state, Long affairId);

    void nextApprover();
}
