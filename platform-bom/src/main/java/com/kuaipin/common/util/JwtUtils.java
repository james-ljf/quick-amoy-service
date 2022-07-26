package com.kuaipin.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * 生成Token和校验Token的工具类
 * @author lijf
 */
public class JwtUtils {

    private JwtUtils(){}

    /**
     * token 过期时间
     */
    private static final long TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24L;

    /**
     * 签发ID
     */
    public static final String ISSUE_ID = "admin";

    /**
     * 加密/解密 密钥
     */
    private static final String JWT_SECRET = "HS256/MD5";

    /**
     * 创建JWT
     */
    public static String createJwt(Map<String, Object> claims, Long time) {
        // 指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(ISSUE_ID)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, JWT_SECRET);

        if (time >= 0) {
            long expMillis = nowMillis + time;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * 验证Token，如果返回null说明token无效或过期
     *
     * @param token token令牌
     * @return  Claims
     */
    public static Claims verifyJwt(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成token
     *
     * @param data 载荷
     * @return  String
     */
    public static String generateToken(Map<String, Object> data) {
        return createJwt(data, TOKEN_EXPIRED_TIME);
    }

}
