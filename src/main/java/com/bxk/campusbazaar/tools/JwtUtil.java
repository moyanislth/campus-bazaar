package com.bxk.campusbazaar.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bxk.campusbazaar.pojo.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${prop.jwt.secret}")
    private String secret;  // 改为非静态

    @Value("${prop.jwt.expire}")
    private Long expire;    // 改为非静态

    /**
     * 生成用户token,设置token超时时间
     */
    public String createToken(User user) {

        Date expireDate = new Date(System.currentTimeMillis() + expire);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HMAC256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map) // 添加头部
                //可以将基本信息放到claims中
                .withClaim("id", user.getId())
                .withClaim("userName", user.getUsername())
                .withClaim("role", Integer.valueOf(user.getRole()))
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 校验token并解析token
     */
    public Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        try {
            //解码token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("token解码异常");
            //解码异常则抛出异常
            return null;
        }
        return jwt.getClaims();
    }

}
