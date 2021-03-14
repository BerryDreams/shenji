package com.shenji.audit;

import com.shenji.audit.dao.UserMapper;
import com.shenji.audit.model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@MapperScan(basePackages = "com.shenji.audit.dao")
class AuditWebApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> userList = userMapper.selectAll();
    }

}
