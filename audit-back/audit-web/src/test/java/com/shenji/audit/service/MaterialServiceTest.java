package com.shenji.audit.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/4/20 14:37
 */
@SpringBootTest
@MapperScan(basePackages = "com.shenji.audit.dao")
public class MaterialServiceTest {
}
