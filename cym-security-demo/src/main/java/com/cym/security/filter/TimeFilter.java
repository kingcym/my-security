package com.cym.security.filter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 10:49
 */
/**
 * 过滤器
 * 若没有@Component注解，则需要定义FilterRegistrationBean
 * 把自定义的Filter,放入其中.
 * {@link com.cym.security.config.TimeFilerConfig}
 */
@Component
@WebFilter(filterName="过滤器名称", urlPatterns = "/*") //过滤地址
public class TimeFilter implements Filter {
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 容器启动时初始化init
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("==============TimeFilter初始化====================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("==============进入doFilter====================");
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("==============退出doFilter====================");
        System.out.println("");
        System.out.println("");

    }

    /**
     * 容器销毁后执行
     */
    @Override
    public void destroy() {
        logger.info("==============TimeFilter销毁====================");
    }
}
