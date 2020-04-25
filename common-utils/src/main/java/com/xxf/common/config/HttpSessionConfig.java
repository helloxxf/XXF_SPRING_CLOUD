package com.xxf.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;


/**
 * @Description
 * @Date Created in 2020/3/19 19:57
 * @User xxf
 */
@Slf4j
@Configuration
public class HttpSessionConfig {
    //设置token接收方式 接收名为token的header获取已有session
    //无该bean则默认通过cookie传递sessionId
    //请求无token则新建session，且返回时携带token header

//    @Bean
//    CookieHttpSessionIdResolver getCookieHttpSessionStrategy() {
//        //return new HeaderHttpSessionIdResolver("pass-token");
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        cookieSerializer.setCookieName("PASS-SESSIONID");//sessionId名称
//
//
//        CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
//        cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
//        //cookieHttpSessionIdResolver.expireSession();
//
//        return cookieHttpSessionIdResolver;
//    }

    @Bean
    HeaderHttpSessionIdResolver getHeaderHttpSessionStrategy() {
        return new HeaderHttpSessionIdResolver("token");
    }
}
