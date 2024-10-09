package org.example.serletdemo;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

/**
 * ClassName: demoListener
 * Package: org.example.serletdemo
 * Description:
 *
 * @Author: Alexios
 * @Create: 2024/10/7 - 19:19
 * @Version: v1.0
 */
@WebListener
public class demoListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        //请求时间
        long startTime = (long)request.getAttribute("startTime");
        //记录请求完成的时间作为结束时间
        long endTime = System.currentTimeMillis();
        //计算请求处理时间
        long processingTime = endTime - startTime;
        //获取客户端IP地址
        String clientIP = request.getRemoteAddr();
        //获取请求方法
        String requestMethod = request.getMethod();
        //获取请求URI
        String requestURI = request.getRequestURI();
        //获取查询字符串
        String queryString = request.getQueryString();
        //获取User-Agent
        String userAgent = request.getHeader("User-Agent");
        //打印日志
        System.out.printf("Request Time: %s, Client IP: %s, Method: %s, URI: %s, "
                        + "Query String: %s, User-Agent: %s, Processing Time: %d ms",
                new java.util.Date(endTime), clientIP, requestMethod, requestURI, queryString, userAgent, processingTime);
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        //记录开始时间
        long startTime = System.currentTimeMillis();
        //存储到request中
        request.setAttribute("startTime", startTime);
    }
}
