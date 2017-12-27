package com.cym.security.interceptor;

import com.alibaba.fastjson.JSON;
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
public class TimeTwoInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     *  //在请求处理之前进行调用（Controller方法调用之前)
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("==============preHandle2====================");
        logger.info("请求接口:{" + httpServletRequest.getRequestURL() + "}" + "请求参数:" + JSON.toJSON(httpServletRequest.getParameterMap()));

        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("==============postHandle2====================" + o);
    }

    /**
     * //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("==============afterCompletion2====================");

    }
}
