package com.shengdangjia.hermesaccount.config;

import com.shengdangjia.hermesaccount.interceptor.AccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AccessConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AccessInterceptor());
        registration.addPathPatterns("/account/**");
        registration.excludePathPatterns("/**/*.html");
    }
}
