package com.shengdangjia.hermesaccount.interceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shengdangjia.hermesaccount.utility.JwtHelper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * access id 拦截器
 */
public class AccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var token = request.getHeader("Authorization");

        if (token == null) {
            returnJson(response, "do not find access token");
            return false;
        }

        var jwtState = JwtHelper.decodeAccessToken(token);

        System.out.println("interceptor: " + token);

        if (jwtState.success) {
            return true;
        } else {
            returnJson(response, jwtState.errorMessage);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void returnJson(HttpServletResponse response, String message) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            writer = response.getWriter();

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("errorCode", 2);
            map.put("message", message);
            map.put("result", null);

            Gson gson = new GsonBuilder().serializeNulls().create();
            var result = gson.toJson(map);

            writer.print(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
