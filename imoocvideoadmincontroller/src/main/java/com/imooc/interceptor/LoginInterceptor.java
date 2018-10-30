package com.imooc.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptor implements HandlerInterceptor{

    private List<String> unCheckUrls = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestUrl = request.getRequestURI();
        requestUrl = requestUrl.replace(request.getContextPath(), "");
        //判断是否针对匿名路径需要拦截, 如果包含, 则表示匿名路径, 需要拦截, 否则通过拦截器
        if (unCheckUrls.contains(requestUrl)){
            //包含公开url, 直接跳过
            return true;
        }
        String method = request.getMethod();
        String a = request.getRequestURI();
        //如果没有登录直接跳到登录页面
        if (null == request.getSession().getAttribute("sessionUser")){
            response.sendRedirect( request.getContextPath() + "/users/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }

    public List<String> getUnCheckUrls() {
        return unCheckUrls;
    }

    public void setUnCheckUrls(List<String> unCheckUrls) {
        this.unCheckUrls = unCheckUrls;
    }
}
