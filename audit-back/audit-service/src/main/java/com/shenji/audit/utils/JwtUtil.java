package com.shenji.audit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.shenji.audit.model.User;

/**
 * JWT
 *
 * @author lwt
 * @version 1.0
 * @date 2021/4/17 20:45
 */
public class JwtUtil {

    private static final Integer tokenExpireTime = 10; //token过期时间，单位分钟
    private static final String secretKey = "shenji_chen_tao_yu_da"; // 固定的服务器私钥


    public static String createToken(Long userId) {
        User user = new User();
        user.setId(userId);
        return createToken(user);
    }

    public static String createToken(User user) {
        // id + JWT私钥加密
        String secret = secretKey + user.getId();
        // 过期时间，单位：毫秒
        long expireTime = System.currentTimeMillis() + tokenExpireTime*60*1000L;
        // 将 userId + currentTime 保存到 token 里面; 以 secret 作为 token 的密钥
        return JWT.create().withAudience(user.getId() +";;"+ expireTime)
                .sign(Algorithm.HMAC256(secret));
    }

    public static Long decodeToken(String token) {
        // 判null
        if (token == null) {
            throw new RuntimeException("Error: token is null in decodeToken ");
        }
        // 获取 token 中的 user id
        String content;
        try {
            content = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401 (jwt decode error)");
        }
        return Long.parseLong(content.split(";;")[0]);
    }

    // 返回newToken(需更新，并且更新了)     返回token(不需更新，返回原token)
    public static String refreshToken(String token) {
        //根据有效期检查是否需要更新Token
        if (token != null){
            String content;
            try {
                content = JWT.decode(token).getAudience().get(0);

            } catch (JWTDecodeException j) {
                throw new RuntimeException("401 (jwt decode error)");
            }

            long expireTime = Long.parseLong(content.split(";;")[1]);
            long currentTime = System.currentTimeMillis();
            long userIdToken = Long.parseLong(content.split(";;")[0]);

            if (currentTime > expireTime){
                // 已失效
                String secret = secretKey + userIdToken;
                long newExpireTime = System.currentTimeMillis() + tokenExpireTime*60*1000L;
                return JWT.create().withAudience(userIdToken +";;"+ expireTime)
                        .sign(Algorithm.HMAC256(secret));
            }
        }else {
            throw new RuntimeException("Error: token is null in refreshToken ");
        }
        return token;
    }
}
