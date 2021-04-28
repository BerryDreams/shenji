package com.shenji.audit.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/4/20 14:36
 */

@SpringBootTest
@MapperScan(basePackages = "com.shenji.audit.dao")
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    void postSource() {
        reportService.postSource(1L, 544423666015006720L, new ArrayList<>());
    }
}
