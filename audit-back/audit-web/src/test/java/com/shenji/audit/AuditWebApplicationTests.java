package com.shenji.audit;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
@MapperScan(basePackages = "com.shenji.audit.dao")
class AuditWebApplicationTests {

//    @Test
//    void testWordToPdf() throws Exception {
//        File file = new File("C:\\Users\\misxr\\Desktop\\audit\\审计处新一代物联化信息管理系统建设方案.docx");
//        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\misxr\\Desktop\\audit\\out.pdf"));
//        FileInputStream is = new FileInputStream(file);
//        Word2Pdf.convertDocxToPdf(is, fos);
//    }
}
