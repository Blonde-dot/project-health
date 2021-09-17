package com.blond.interceptor;

import com.blond.pojo.Member;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-16 16:33
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final String[] NO_FILTER_PATH={"/css/","/fonts/","/fonts/","/img/","/plugins/","/regist.html","/"};
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        for (String s : NO_FILTER_PATH) {
            if(s.equals(requestURI)){
                return true;
            }
        }
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("User");
        if(member != null){
            return true;
        }
        response.sendRedirect("/regist.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
