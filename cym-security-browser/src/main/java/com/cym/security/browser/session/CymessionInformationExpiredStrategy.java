package com.cym.security.browser.session;

import org.apache.log4j.Logger;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/5 16:37
 */
public class CymessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        logger.info("===============账号被其它地方登陆=================");
        response.getWriter().write("账号被其它地方登陆");
    }
}
