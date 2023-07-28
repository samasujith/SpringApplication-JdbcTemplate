package com.sujith.project.interceptor;

import jakarta.servlet.http.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.servlet.*;

@Component
public class ApiInterceptor implements HandlerInterceptor {
    double preTime = 0;
    double postTime = 0;
    Logger log = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        preTime = System.currentTimeMillis();
        log.info("preHandle method is invoked {} {} {}", request.getRequestURI(), request.getMethod(), preTime);

        return HandlerInterceptor.super.preHandle(request, response, handler);

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        postTime = System.currentTimeMillis();
        log.info("postHandle method is invoked {} {} {}", request.getRequestURI(), request.getMethod(), postTime);


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
            log.info("afterCompletion  method is invoked {} {} {}", request.getRequestURI(), request.getMethod(), ex.getMessage());
        } else if (postTime > 0) {
            log.info("after completion is invoked Time took is {} seconds.", (postTime - preTime) / 1000);
        }
    }
} //round(postTime-preTime,3)
