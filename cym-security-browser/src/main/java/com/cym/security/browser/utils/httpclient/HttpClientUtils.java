package com.cym.security.browser.utils.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 1:11
 */
public class HttpClientUtils {
    public static JSONObject doGetJson(String url) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            //创建HttpClients对象
            client = HttpClients.createDefault();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);
            // 执行请求
            response = client.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                //转成String
                String result =  entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
                //String转json
                jsonObject = JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (client != null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }



    public static JSONObject doPostJson(String url,String content) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            //创建HttpClients对象
            client = HttpClients.createDefault();
            // 创建http post请求
            HttpPost httpPost = new HttpPost(url);
            StringEntity s = new StringEntity(content);
            s.setContentEncoding("UTF-8");
            httpPost.setEntity(s);
            // 执行请求
            response = client.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                //转成String
                String result =  entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
                //String转json
                jsonObject = JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (client != null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
