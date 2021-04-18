package com.shenji.audit.dao;

import com.shenji.audit.model.MaterialLog;

import java.util.List;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/23 10:07
 */
public interface MaterialMapper {

    void insertOne(MaterialLog materialLog);

    MaterialLog selectOne(Long materialId);

    List<MaterialLog> getMaterialByAffairId(Long affairId);
}
