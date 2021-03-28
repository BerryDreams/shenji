package com.shenji.audit.dao;

import com.shenji.audit.model.Report;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/23 10:39
 */
public interface ReportMapper {

    void insertOne(Report report);

    Report selectOne(Long reportId);
}
