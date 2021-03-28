package com.shenji.audit.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 9:19
 */

@SpringBootTest
@MapperScan(basePackages = "com.shenji.audit.dao")
public class MinioServiceTest {

    @Autowired
    private MinioService minioService;

    @Test
    void saveMaterialTest() {
    }
}
