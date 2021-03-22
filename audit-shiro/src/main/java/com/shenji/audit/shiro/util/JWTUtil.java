package com.shenji.audit.shiro.util;

import com.shenji.audit.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/19 16:24
 */
@Slf4j
@NoArgsConstructor
public class JWTUtil {

    /**
     * 私钥
     */
    private static final String SECRET_KEY = "fweijvoivhuhrgergergregergerg";

    /**
     * 过期时间 毫秒,设置默认两小时过期
     */
    private static final long EXPIRATION_TIME = 3600000L * 2;

    /**
     * 生成令牌
     * @param username 用户名
     * @return 令牌
     */
    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT,username);
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return username;
    }



    /**
     * 判断令牌是否过期
     * @param token 令牌
     * @return 是否过期
     * @throws Exception 所有异常
     */
    public static Boolean isTokenExpired(String token) throws  Exception{
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            throw new Exception("签名过期或者token不正确");
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }



    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis()+ EXPIRATION_TIME);
        HashMap<String, Object> header = new HashMap<>(1);
        header.put(Header.TYPE, Header.JWT_TYPE);
        return Jwts.builder().setHeader(header).setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) throws Exception {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new Exception("token解析失败");
        }
        return claims;
    }
}
