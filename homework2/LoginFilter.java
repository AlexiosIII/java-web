package org.example.serletdemo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: LoginFilter
 * Package: org.example.serletdemo
 * Description:
 *
 * @Author: Alexios
 * @Create: 2024/9/26 - 14:45
 * @Version: v1.0
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    //排除列表
    private static final List<String> STATIC_EXTENSIONS = Arrays.asList(
            "/login","/register","/public"
    );
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    public boolean checkExtensions(String requestURI){
        return STATIC_EXTENSIONS.stream().anyMatch(requestURI::endsWith);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求的URL
        String requestURI = request.getRequestURI().toLowerCase();
        //如果登录的路径为排除列表之内,直接放行
        boolean isStaticResource = checkExtensions(requestURI);
        if(isStaticResource) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 如果不含有已登录的属性,重定向到/login路径
        String name = (String) request.getSession().getAttribute("name");
        System.out.println(Objects.isNull(name));
        if(Objects.isNull(name)){
            response.sendRedirect("login");
            return;
        }
        //如果登录就进行放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
