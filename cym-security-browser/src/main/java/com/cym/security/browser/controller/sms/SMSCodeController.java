package com.cym.security.browser.controller.sms;

import com.cym.security.browser.dto.SmsCode;
import com.cym.security.browser.service.sms.SmsCodeSenderService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/31 0:41
 */
@RestController
public class SMSCodeController {
    private Logger logger = Logger.getLogger(this.getClass());
    //操作session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    public static final String MOBILECODEKEY = "mobile_code";


    @Autowired
    private SmsCodeSenderService smsCodeSenderService;

    @GetMapping("/code/sms")
    public void createCode(HttpServletRequest request, String mobile) throws IOException {
        SmsCode smsCode = createMobileCode();
        smsCode.setMobile(mobile);
        logger.info("=====手机号：" + mobile + "==========生产手机验证码：=================" + smsCode.getCode());
        //存入session
        sessionStrategy.setAttribute(new ServletWebRequest(request), MOBILECODEKEY, smsCode);
        //发送短信验证码
        smsCodeSenderService.sendSmsCode(smsCode);
    }

    private SmsCode createMobileCode() {
        // 生成随机数
        Random random = new Random();
        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < 6; i++) {
            int strRand = random.nextInt(9);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        return new SmsCode(randomCode.toString(),600);
    }


}
