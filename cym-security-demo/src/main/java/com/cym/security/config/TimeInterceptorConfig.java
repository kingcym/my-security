package com.cym.security.config;

import com.cym.security.interceptor.TimeInterceptor;
import com.cym.security.interceptor.TimeTwoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 15:27
 */
//@Configuration
public class TimeInterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private TimeInterceptor timeInterceptor;

    @Autowired
    private TimeTwoInterceptor timeTwoInterceptor;


    /**
     * 添加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        InterceptorRegistration timeTwo = registry.addInterceptor(timeTwoInterceptor);
        InterceptorRegistration time = registry.addInterceptor(timeInterceptor);
        //配置拦截路径
        timeTwo.addPathPatterns("/*");
        time.addPathPatterns("/*");
        //忽略拦截路径
        timeTwo.excludePathPatterns("/do");
        time.excludePathPatterns("/user");
    }
}
