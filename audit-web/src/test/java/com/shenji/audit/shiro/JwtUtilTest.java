package com.shenji.audit.shiro;

import com.shenji.audit.model.User;
import com.shenji.audit.shiro.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/20 8:07
 */

@SpringBootTest
public class JwtUtilTest {

//    @Autowired
//    private JWTUtil jwtUtil;

    @Test
    void genToken() {

    }

    @Test
    void genUser() {
        String token = JWTUtil.generateToken("chenzhenguo");
        String username = JWTUtil.getUsernameFromToken(token);
        System.out.println(username);
    }

}
