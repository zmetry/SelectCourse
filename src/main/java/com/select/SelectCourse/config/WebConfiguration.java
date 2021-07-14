package com.select.SelectCourse.config;

import com.select.SelectCourse.interproter.AdminInterceptor;
import com.select.SelectCourse.interproter.LoginInterceptor;
import com.select.SelectCourse.interproter.TeacherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index/**", "/login/**", "/css/**"
                        , "/html/**", "/js/**", "/image/**", "/registerPage/**"
                        , "/register/**","/text");

        //权限拦截器
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**");

        registry.addInterceptor(new TeacherInterceptor())
                .addPathPatterns("/teacher/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/js/");

        registry.addResourceHandler("/image/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX +"/static/image/");

    }
}
