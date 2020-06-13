package com.shengdangjia.hermesaccount.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT 工具类
 */
public class JwtHelper {
    private static String key = "the shengdangjia hermes project";

    /**
     * 创建 id token
     * <p>
     * 签发人: auth
     * 主题: id token
     * 超时：1小时
     * 自定义: uid - 用户ID
     *
     * @param uid 用户GUID
     * @return token
     */
    public static String createIdJWT(String uid) {
        Algorithm algorithm = Algorithm.HMAC384(key);

        var ldt = LocalDateTime.now().plusHours(1);
        var dt = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        String token = JWT.create()
                .withIssuer("auth")
                .withExpiresAt(dt)
                .withSubject("id token")
                .withClaim("uid", uid)
                .sign(algorithm);

        return token;
    }
}
