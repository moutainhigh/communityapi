package net.okdi.logistics;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class IpClient {
	//ReadProperties rp = new ReadProperties("./resources/taobao_proxy.properties");
	ReadProperties rp = new ReadProperties(getClass().getResource("/taobao_proxy.properties").getFile());
	private String PROXYSERVE_URL = rp.getKeyValue("proxy_url");//"http://121.199.38.28/ip?tid=836011700406345&num=500&filter=on";// area=%E5%8C%97%E4%BA%AC

	private final static int CONN_TIMEOUT = 50000;

	private Logger logger = Logger.getLogger(IpClient.class);
	public String getResult() {

		String result = null;

		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时
																								// 设置
		GetMethod getMethod = new GetMethod(PROXYSERVE_URL);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1090); // 获取数据超时设置
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				System.out.println("Method failed: " + getMethod.getStatusLine());
				return null;
			} else {
				result = new String(getMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!" + e.getCause());
			return null;
		} catch (IOException e) {
			System.out.println("url:" + PROXYSERVE_URL);
			return null;
		} finally {
			getMethod.releaseConnection();
		}

		return result;
	}

	public synchronized void updateIpQueue() {
		String result = getResult();
		int count = 1;
		while (result == null||result.split(":").length<2) {
			logger.error("使用代理 第 " + count + " 次没有ip返回");
			result = getResult();
			count++;
			if (count >= 3)
				return;
		}
		String[] ips = splitIps(result);
		for (int i = 0; i < ips.length; i++) {
			TimerQueueInfo.ipQueue.offer(ips[i]);
		}
	}

	public String[] splitIps(String ips) {
		String[] ip = ips.split("\r\n");
		return ip;
	}

	public static void main(String[] args) {
		IpClient a = new IpClient();
		String ss = a.getResult();
		System.out.println(ss);
	}

	// 参数转UTF-8
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

}
