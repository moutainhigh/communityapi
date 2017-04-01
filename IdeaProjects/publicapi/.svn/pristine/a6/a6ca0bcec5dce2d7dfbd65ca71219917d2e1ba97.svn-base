package net.okdi.httpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import net.okdi.core.util.PubMethod;

/**
 * @description: copy from okdiweb
 * @author feng.wang
 * @date 2014-9-5
 * @version: 1.0.0
 * @param <E>
 */
public abstract class AbstractHttpClient<E> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);
	
	private static Integer connectionTimeout = 60*1000;
	private static Integer requestTimeout = 60*1000;

	/**
	 * httpClient POST请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param data
	 *            post上送的参数;
	 * @return
	 */
	public String Post(String url, Map<String, Object> map) {
		logger.info("url => {} :: 参数 => {}", url, map);
		String result = null;
		HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) );
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout); // 连接超时
		// 设置
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(this.buildPostParams(map));
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, requestTimeout); // post请求超时
		// 设置
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				logger.error("url => {} 请求错误, 状态码 => {}", url, statusCode);
				return null;
			} else {
				result = new String(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			e.printStackTrace();
			postMethod.abort();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			postMethod.abort();
			return null;
		} finally {
			postMethod.releaseConnection();
			((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();  
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		logger.info("httpClient返回值  => {}", result);
		return result;
	}

	/**
	 * httpClient GET请求方法
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String Get(String url, Map<String, String> map) {
		logger.info(url);
		logger.info(JSON.toJSONString(map));
		String result = null;
		HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) );
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout); // 连接超时
		// 设置
		GetMethod postMethod = new GetMethod(buildGetParams(url, map));
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, requestTimeout); // post请求超时
		// 设置
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return null;
			} else {
				result = new String(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			postMethod.abort();
			return null;
		} catch (IOException e) {
			postMethod.abort();
			return null;
		} finally {
			postMethod.releaseConnection();
			((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();  
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return result;
	}

	/**
	 * 组建GET调用方式的参数
	 * 
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private NameValuePair[] buildPostParams(Map<String, Object> map) {
		if(PubMethod.isEmpty(map)){
			map = new HashMap<String, Object>();
		}
		NameValuePair[] data = new NameValuePair[map.size()];
		Iterator<String> it = map.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key) == null ? "" : map.get(key) + "";
			data[i] = new NameValuePair(key, value);
			i++;
		}
		return data;
	}

	/**
	 * 组建GET调用方式的参数
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private String buildGetParams(String url, Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = map.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key) == null ? "" : map.get(key) + "";
			PubMethod.concat(sb, key + "=" + value, "&");
		}
		String paramStr = url + "?" + sb.toString();
		System.out.println(paramStr);
		return paramStr;
	}
	
}
