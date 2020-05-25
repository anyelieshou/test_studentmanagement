package com.springboottest.interceptor;

import cn.hutool.json.JSONObject;
import com.springboottest.constant.Constants;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author LJS
 * @data 2020/5/14 18:22
 */
public class HelloInterceptor implements HandlerInterceptor{
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler) throws java.lang.Exception {
        String url = request.getRequestURL().toString();
        System.out.println("即将访问： " + url + "handler=" + handler.getClass());
        if (handler instanceof HandlerMethod) {
            //获取ContextPath
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int port = request.getServerPort();
            String contextPath = request.getContextPath();
            String ctxPath;
            if (port == 80) {
                ctxPath = scheme + "://" + serverName + contextPath;
            } else {
                ctxPath = scheme + "://" + serverName + ":" + port + contextPath;
            }
            request.setAttribute("ctxPath", ctxPath);
            if (url.endsWith("/index")||url.endsWith("/user")||url.endsWith("/personal")||url.endsWith("/updatePass")||url.endsWith("/passView")) {
                JSONObject currentUser = (JSONObject) request.getSession().getAttribute(Constants.CURRNT_USER_KEY);
                if (currentUser == null) {
                    response.sendRedirect(ctxPath + "/user/login");
                    return false;
                }
            }
        }
            return true;
    }

    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler, @org.springframework.lang.Nullable org.springframework.web.servlet.ModelAndView modelAndView) throws java.lang.Exception {

    }

    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object handler, @org.springframework.lang.Nullable java.lang.Exception ex) throws java.lang.Exception {
    }

}
