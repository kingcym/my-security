package com.cym.security.browser.utils.httpclient;

import com.alibaba.fastjson.JSON;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Chandler Qian
 * @version V1.0
 * @Description:
 * @date 2017/5/24 0024
 */
public class SmsClientSend {
	/*
	* @param url
	* ：必填--发送连接地址URL——http://sms.kingtto.com:9999/sms.aspx
	* @param userid
	* ：必填--用户ID，为数字
	* @param account
	* ：必填--用户帐号
	* @param password
	* ：必填--用户密码
	* @param mobile
	* ：必填--发送的手机号码，多个可以用逗号隔比如>130xxxxxxxx,131xxxxxxxx
	* @param content
	* ：必填--实际发送内容，
	* @param action
	* ：选填--访问的事件，默认为send
	* @param sendType
	* ：选填--发送方式，默认为POST
	* @param codingType
	* ：选填--发送内容编码方式，默认为UTF-8
	* @param backEncodType
	* ：选填--返回内容编码方式，默认为UTF-8
	* @return 返回发送之后收到的信息
	*/
	public static Object sendSms(String url, String userid, String account,
								 String password, String mobile, String content, String action,
								 String sendType, String codingType, String backEncodType) {
		try {
			if (codingType == null || codingType.equals("")) {
				codingType = "UTF-8";
			}
			HashMap<String, String> map = new HashMap<>();
			map.put("action","send");
			map.put("userid",userid);
			map.put("account",URLEncoder.encode(account, codingType));
			map.put("password",URLEncoder.encode(password, codingType));
			map.put("content",URLEncoder.encode(content, codingType));
			map.put("mobile",mobile);
			String s = JSON.toJSONString(map);
			return HttpClientUtils.doPostJson(url,s);
		} catch (Exception e) {
			e.printStackTrace();
			return "未发送，编码异常";
		}
	}




	public static void main(String[] args) {
		try{
			String url="http://sms.kingtto.com:9999/sms.aspx";
			String userid="4653";
			String account="zjly";
			String password="zjly123456";
			String mobile="18322694214";
			String sign="【陶朱街道安监站】";
			String  content="你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好" +
					"你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好" +
					"你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好" +
					"你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好" +
					"你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好";
//			String sa = SMSUtils.send("4653", "zjly123456", "zjly", "15267142067",URLEncoder.encode(content,"utf8"));
			Object sa = SmsClientSend.sendSms(url,userid, account, password, mobile,content+sign,"","","","");
			System.out.println("sa="+sa);
		}catch (Exception e){

		}

	}

}
