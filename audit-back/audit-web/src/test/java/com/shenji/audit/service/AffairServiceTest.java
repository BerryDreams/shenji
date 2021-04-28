package com.shenji.audit.service;

import com.shenji.audit.type.AffairType;
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
public class AffairServiceTest {

    @Autowired
    private AffairService affairService;

    @Test
    void startOne() {
        affairService.startAffair(1L, "测试事务", "这真是测试事务", AffairType.KIND_FOLLOW_UP_AUDIT);
    }

    @Test
    void getAffair() {
        System.out.println(affairService.getAllAffair(1L));
    }

    @Test
    void getMyAffair() {
        System.out.println(affairService.getMyAffair(1L));
    }

    @Test
    void getToApprove() {
        System.out.println(affairService.toApprove(2L));
    }

    @Test
    void getMyApproval() {
        System.out.println(affairService.getMyApproval(2L));
    }

    @Test
    void approve() {
        affairService.approve(4L, 544423666015006720L, true, "干得漂亮", "192。168.0.1", new ArrayList<>());
    }
}
