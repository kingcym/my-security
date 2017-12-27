package com.cym.security.config;

import com.cym.security.filter.TimeFilter;
import com.cym.security.filter.TimeTwoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 11:10
 */
@Configuration
public class TimeFilerConfig {
    /**
     * 注册自定义TimeFilter的bean
     */
    @Bean(name = "timeFilter")
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Filter timeFilter = new TimeFilter();
        filterRegistrationBean.setFilter(timeFilter);
        //设置拦截路径
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        //如果多个Filter,指定Filter的顺序,数字越小越先开始
        filterRegistrationBean.setOrder(10);
        return filterRegistrationBean;
    }


    /**
     * 注册自定义timeTwoFilter的bean
     */
    @Bean(name = "timeTwoFilter")
    public FilterRegistrationBean timeTwoFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Filter timeFilter = new TimeTwoFilter();
        filterRegistrationBean.setFilter(timeFilter);
        //设置拦截路径
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        //如果多个Filter,指定Filter的顺序
        filterRegistrationBean.setOrder(5);
        return filterRegistrationBean;
    }




}
