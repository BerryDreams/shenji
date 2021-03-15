package com.shenji.audit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.shenji.audit.dao")
public class AuditWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditWebApplication.class, args);
    }

}
