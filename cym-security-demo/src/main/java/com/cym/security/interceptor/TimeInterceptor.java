package com.cym.security.interceptor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 15:19
 */

/**
 * 需把该拦截器放到InterceptorRegistry中
 * 具体实现到{@link com.cym.security.config.TimeInterceptorConfig}
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 进入controler之前执行此方法
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("==============preHandle====================");
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    /**
     * 退出controler之后执行此方法（controler没有异常）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("==============postHandle====================" + o);
    }

    /**
     * 退出controler之后执行此方法（无论是否异常）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("==============afterCompletion====================");

    }
}
