package com.xxf.common.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "xssFilter")
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper((HttpServletRequest)request);
        chain.doFilter(xssHttpServletRequestWrapper, response);
    }

    @Override
    public void destroy() {}
}