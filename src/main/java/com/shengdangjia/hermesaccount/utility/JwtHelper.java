package com.shengdangjia.hermesaccount.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

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

    /**
     * 验证access token
     * @param token 令牌
     * @return JWT数据
     */
    public static JwtState decodeAccessToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC384(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth")
                    .withSubject("access token")
                    .acceptExpiresAt(1)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            var dt = jwt.getExpiresAt();
            LocalDateTime ldt = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            JwtState state = new JwtState();
            state.success = true;
            state.isExpire = false;
            state.uid = jwt.getClaim("uid").asString();;
            state.subject = jwt.getClaim("sub").asString();

            return state;
        } catch (TokenExpiredException e) {
            JwtState state = new JwtState();
            state.success = false;
            state.isExpire = true;
            state.errorMessage = "access token 已超时";
            return state;
        } catch (JWTVerificationException e) {
            JwtState state = new JwtState();
            state.success = false;
            state.isExpire = false;
            state.errorMessage = "access token 认证失败";
            return state;
        }
    }
}
