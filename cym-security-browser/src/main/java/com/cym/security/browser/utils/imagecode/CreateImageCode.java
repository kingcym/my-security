package com.cym.security.browser.utils.imagecode;

import com.cym.security.browser.dto.ImageCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/30 15:22
 */
public class CreateImageCode {
    private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    // 图片的宽度。
    private int width;
    // 图片的高度。
    private int height;
    // 验证码字符个数
    private int codeCount;
    // 验证码干扰线数
    private int lineCount;
    //过期时间(秒)
    private  int expire;


    public CreateImageCode(int expire) {
        this();
        this.expire = expire;
    }

    public CreateImageCode() {
        this(160,40,6,75,60);
    }

    public CreateImageCode(char[] codeSequence) {
        this();
        this.codeSequence = codeSequence;
    }

    public CreateImageCode(int width, int height, int codeCount, int lineCount,int expire) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.expire = expire;
    }


    public ImageCode createImageCode() {

        // 验证码
        String code = null;
        // 验证码图片Buffer
        BufferedImage buffImg = null;

        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);//每个字符的宽度
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体
        ImgFontByte imgFont = new ImgFontByte();
        Font font = imgFont.getFont(fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            //    String strRand =String.valueOf(getRandomChar());
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));

            //设置字体旋转角度
            int degree = new Random().nextInt() % 30;
            //顺时针旋转
            g.rotate(degree * Math.PI / 180, (i + 1) * x, 20);
            //写入Graphics2D
            g.drawString(strRand, (i + 1) * x, codeY);
            //逆时针旋转
            g.rotate(-(degree * Math.PI / 180), (i + 1) * x, 20);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        //将四位数字的验证码
        code = randomCode.toString();

        return new ImageCode(buffImg, code, expire);
    }

}
