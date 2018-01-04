package com.cym.security.browser.controller.validateCode;

import com.cym.security.browser.dto.ImageCode;
import com.cym.security.browser.utils.imagecode.CreateImageCode;
import com.cym.security.browser.utils.imagecode.ImgFontByte;
import org.apache.log4j.Logger;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/29 14:58
 */
@RestController
public class ValidateController {
    private Logger logger = Logger.getLogger(this.getClass());


    public static final String IMAGECODEKEY = "image_code";

    //操作session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        CreateImageCode code = new CreateImageCode();
        //生成验证码
        ImageCode imageCode = code.createImageCode();
        logger.info("========生成验证码==========="+imageCode.getCode());
        //存入session
        sessionStrategy.setAttribute(new ServletWebRequest(request),IMAGECODEKEY,imageCode);
        //写入响应输出流
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }


}
