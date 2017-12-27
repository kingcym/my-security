package com.cym.security.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
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
//@Component
public class TimeFilter implements Filter {
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("==============TimeFilter初始化====================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("==============进入doFilter====================");
        long l = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("==============退出doFilter====================耗时：" + (System.currentTimeMillis() - l));
    }

    @Override
    public void destroy() {
        logger.info("==============TimeFilter销毁====================");
    }
}
