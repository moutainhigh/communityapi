package net.okdi.core.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.okdi.core.util.PubMethod;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

/**
 * @author feng.wang
 * @version V2.0
 */
public abstract class AbstractHttpClient<E> {
	
	@Autowired
	private CloseableHttpClient httpClient;

	private static Logger logger = Logger.getLogger(AbstractHttpClient.class);

	/**
	 * post请求
	 * 
	 * @param url请求的地址
	 * @param map参数map
	 * @return 结果
	 */
	public String Post(String url, Map<String, String> map) {
		logger.info("------openapi---Post请求地址------" + url);
		logger.info("------openapi---Post请求参数------" + JSON.toJSONString(map));
		String returnStr = null;
		// 参数检测
		if (url == null || "".equals(url)) {
			return returnStr;
		}
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {

			// 设置post参数对
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (map != null && map.size() > 0) {
				logger.info("------openapi---Post请求参数------" + JSON.toJSONString(map));
				for (String key : map.keySet()) {
					nvps.add(new BasicNameValuePair(key,map.get(key) == null ? "" : String.valueOf(map.get(key))));
				}
			}
			// 设置编码，如果包含中文，一定要进行设置，否则按照系统默认字符集进行转码会出现乱码
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			// 请求数据
			response = httpClient.execute(httpPost);
					
			// 获取响应状态码
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String result = entity != null ? EntityUtils.toString(entity,
						"UTF-8") : null;
				logger.info("------openapi---Post请求结果------" + result);
				return result;
			} else {
				logger.info("------openapi---Post请求结果------ null 状态码 => " + status + " 调用地址 => " + url);
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if(response!=null){
					response.close();
					EntityUtils.consume(response.getEntity());
				}
				//httpclient必须releaseconnection，但不是abort。
				//因为releaseconnection是归还连接到连接池，而abort是直接抛弃这个连接，而且占用连接池的数目
				httpPost.releaseConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 相应返回值
		return returnStr;
	}

	/**
	 * get请求
	 * 
	 * @param url请求的地址
	 * @param map参数map
	 * @return 结果
	 */
	public String Get(String url, Map<String, String> map) {
		logger.info("------openapi---Get请求地址------" + url);
		logger.info("------openapi---Get请求参数------" + JSON.toJSONString(map));
		String returnStr = null;
		// 参数检测
		if (url == null || "".equals(url)) {
			return returnStr;
		}
		// 组建GET调用方式的参数
		url = this.buildGetParams(url, map);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			// 请求数据
			response =httpClient.execute(httpGet);
			// 获取响应状态码
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String result = entity != null ? EntityUtils.toString(entity,
						"UTF-8") : null;
				logger.info("------openapi---Get请求结果------" + result);
				return result;
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if(response!=null){
					response.close();
					EntityUtils.consume(response.getEntity());
				}
				//httpclient必须releaseconnection，但不是abort。
				//因为releaseconnection是归还连接到连接池，而abort是直接抛弃这个连接，而且占用连接池的数目
				httpGet.releaseConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 相应返回值
		return returnStr;
	}

	/**
	 * 组建GET调用方式的参数 s
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private String buildGetParams(String url, Map<String, String> map) {
		if (map == null || map.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key) == null ? "" : String.valueOf(map
					.get(key));
			PubMethod.concat(sb, key + "=" + value, "&");
		}
		String paramStr = url + "?" + sb.toString();
		logger.info("------openapi---Get请求组装后的URL------" + paramStr);
		return paramStr;
	}

	public abstract E parseResult(String info);

	public String paramsToStr(Object obj) {
		String result = String.valueOf(obj);
		if (result == null || result == "null") {
			return "";
		}
		return result;
	}
}
