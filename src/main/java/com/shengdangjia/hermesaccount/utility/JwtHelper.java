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
     * 验证id token
     * @param token 令牌
     * @return JWT数据
     */
    public static boolean decodeAccessToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC384(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth")
                    .withSubject("id token")
                    .acceptExpiresAt(1)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            var dt = jwt.getExpiresAt();
            LocalDateTime ldt = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            String uid = jwt.getClaim("uid").asString();

//            JwtState state = new JwtState();
//            state.setSuccess(true);
//            state.setExpire(false);
//            state.setExpireTime(ldt);
//            state.setUid(uid);

            return true;
        } catch (TokenExpiredException e) {
//            JwtState state = new JwtState();
//            state.setSuccess(false);
//            state.setExpire(true);
//            return state;
            return false;
        } catch (JWTVerificationException e) {
//            JwtState state = new JwtState();
//            state.setSuccess(false);
//            state.setExpire(false);
//            return state;
            return  false;
        }
    }
}
