package com.cym.security.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 20:43
 */

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/file")
public class FileControler {
    private Logger logger = Logger.getLogger(this.getClass());
    private final String localUrl = "E:\\gitHub\\自定义代码\\cai-security\\cym-security-demo\\src\\main\\resources\\files";

    @PostMapping
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("=========originalFilename==============" + originalFilename);
        //获取文件后缀名
        String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //单位byte
        System.out.println(file.getSize());
        File newFile = new File(localUrl, System.currentTimeMillis() + prefix);
        //保存文件
        file.transferTo(newFile);
        return newFile.getAbsolutePath();
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        //（）里面东西jdk会自动关闭
        try (FileInputStream inputStream = new FileInputStream(new File(localUrl, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition","attachment;filename=text.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
