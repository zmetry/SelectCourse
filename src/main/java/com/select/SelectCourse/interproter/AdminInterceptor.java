package com.select.SelectCourse.interproter;

import com.select.SelectCourse.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<User> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();
    private static boolean flag;
    private static User user;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进入admin权限拦截器内部");
        if (USER_INFO_THREAD_LOCAL.get() != null){
            user = USER_INFO_THREAD_LOCAL.get();
        }else{
            user = (User) request.getSession().getAttribute("user");
            USER_INFO_THREAD_LOCAL.set(user);
        }

        if (user.getPower() != 1){
            flag = false;
            log.warn("由于权限不够，被拦截器拦截");
        }
        else {
            flag = true;
            log.info("进行放行");
        }

        return flag;
    }

    @PreDestroy
    public void deleteThreadLocal(){
        USER_INFO_THREAD_LOCAL.remove();
    }

}
