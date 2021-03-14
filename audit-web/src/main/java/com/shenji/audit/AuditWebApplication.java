package com.shenji.audit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.shenji.audit.dao")
public class AuditWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditWebApplication.class, args);
    }

}
