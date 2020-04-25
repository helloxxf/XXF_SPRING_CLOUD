package com.xxf.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @Description 基于springsession获取用户工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Slf4j
public class SessionUtils {

    /**
     * 功能描述: 清除session
     *
     * @param
     * @return
     * @auther kanst
     * @date 2019/5/20 11:41
     */
    public static void clearSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        /*参数为false时，防止生成session*/
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 获取当前session
     *
     * @return
     */
    public static HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        /*参数为false时，防止生成session*/
        HttpSession session = request.getSession(false);
        return session;
    }

    public static HttpSession createSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        /*参数为false时，防止生成session*/
        HttpSession session = request.getSession();
        return session;
    }
}
