package com.shenji.audit;

import com.shenji.audit.model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@MapperScan(basePackages = "com.shenji.audit.dao")
class AuditWebApplicationTests {

}
