package net.okdi.core.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.okdi.core.util.PubMethod;

/**
 * 微信公众号
 * @ClassName: WechatMpHttpClient
 * @Description: TODO
 * @author hang.yu
 * @date 2016年3月24日 下午2:26:01
 * @version V1.0
 */
public class WechatMpHttpClient extends AbstractHttpClient<String> {
	
	private static Logger logger = Logger.getLogger(WechatMpHttpClient.class);
	
	private @Value("${mp.wechat.taketask}") String takeTask;
	
	/**
	 * 获取微信公众号ACCESS_TOKEN
	 * @Method: token
	 * @param appId		: 第三方用户唯一凭证(公众号开发者中心获得)
	 * @param appSecret	: 第三方用户唯一凭证密钥，即appsecret(公众号开发者中心获得)
	 * @return
	 */
	public String token(String appId, String appSecret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String, String> param = new HashMap<>();
		param.put("grant_type", "client_credential");
		param.put("appid", appId);
		param.put("secret", appSecret);
		return Get(url, param);
	}
	
	public void getTempleate(String token) {
		String url1 = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + token;
		Map<String, String> map = new HashMap<>();
		map.put("industry_id1", "1");
		map.put("industry_id2", "13");
		String post = Post(url1, map);
		System.out.println("post: " + post);
	}
	
	public JSONObject sendTempleteMessage(String token, String openId, String templateId, String dumpUrl, String site,
			String courierName, String phone, Integer status, String reason) {
		
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
		Map<String, Object> map = new HashMap<>();
		map.put("touser", openId);
		map.put("template_id", templateId);
		map.put("url", "");
		
		JSONObject firstObj = new JSONObject();
		firstObj.put("value", PubMethod.isEmpty(site) ? "" : "站点: " + site);
		firstObj.put("color", "#173177");
		
		JSONObject keyword1 = new JSONObject();
		keyword1.put("value", courierName);
		keyword1.put("color", "#000000");
		
		JSONObject keyword2 = new JSONObject();
		keyword2.put("value", phone);
		keyword2.put("color", "#0000be");
		
		JSONObject remark = new JSONObject();
		StringBuffer sb = new StringBuffer("\r\n");
		switch (status) {
		case 1:
			sb.append(PubMethod.isEmpty(takeTask) ? "" : takeTask);
			break;
		case 0:
			reason = PubMethod.isEmpty(reason) ? "" : reason;
			sb.append(reason);
			break;
		}
		remark.put("value", sb.toString());
		remark.put("color", "#000000");
		
		JSONObject json = new JSONObject();
		json.put("first", firstObj);
		json.put("keyword1", keyword1);
		json.put("keyword2", keyword2);
		json.put("remark", remark);
		map.put("data", json);
		
		String jsonStr = JSON.toJSONString(map);
		
		logger.info("发送模板消息POST参数: " + jsonStr);

		return httpRequest(url, "POST", jsonStr);
	}
	
	
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return info;
	}
	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 */
	public JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			//TrustManager[] tm = { new x509t};

			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, null, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String str = null;
			while ((str = br.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			br.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return JSON.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String  getJsapiTicket(String accessToken){
		Map<String,String> map=new HashMap<String, String>();
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		map.put("access_token", accessToken);
		map.put("type", "jsapi");
		String result = Post(url,map);
		return result;
	}
}









