package com.cym.security.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/12 23:40
 */
public class CymOauth2Exception extends DefaultWebResponseExceptionTranslator {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        try {
            if (e instanceof ValidateCodeException){
                return handleOAuth2Exceptions((ValidateCodeException) e);
            } else {
                return super.translate(e);
            }

        }catch (Exception exception){
            return super.translate(e);
        }
    }


    private ResponseEntity handleOAuth2Exceptions(ValidateCodeException e) throws IOException {

        int status = e.getCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",status);
        map.put("message",e.getMessage());
        ResponseEntity response = new ResponseEntity(map, headers,
                HttpStatus.OK);

        return response;

    }
}
