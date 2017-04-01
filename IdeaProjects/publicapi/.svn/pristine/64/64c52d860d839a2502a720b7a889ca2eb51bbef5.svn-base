package net.okdi.logistics;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.AbstractHttpClient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QueryKuaiDiClient extends AbstractHttpClient{
	private String KUAIDI100_URL = "http://www.kuaidi100.com/query?";
	private int sumQuery = 0;// 使用查询最大上限

	Logger logger = Logger.getLogger(QueryKuaiDiClient.class);

	private String usedIps = "";

	public String getResult(Map map) {
		sumQuery++;
		if (sumQuery > 10) {
			return null;
		}
		if (PubMethod.isEmpty(map)) {
			return null;
		}
		if (PubMethod.isEmpty(map.get("type")) || PubMethod.isEmpty(map.get("postid"))) {
			return null;
		}
		String ip = "";
		int port = 8080;
		if (TimerQueueInfo.ipQueue.getSize() < 5) {
			IpClient ic = new IpClient();
			ic.updateIpQueue();
		}
		String ips = null;
		ips = TimerQueueInfo.ipQueue.poll();
		if (PubMethod.isEmpty(ips)) {
			return null;
		}
		String[] ipArry = ips.split(":");
		ip = ipArry[0];
		usedIps += ip + " >> ";
		port = Integer.parseInt(ipArry[1]);
		String result = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);

		// 连接超时 设置
		// ip = "118.181.64.141";
		// port = 8080;
		ProxyHost proxyHost = new ProxyHost(ip, port);

		httpClient.getHostConfiguration().setProxyHost(proxyHost);
		System.out.println("type:   " + map.get("type"));
		System.out.println("postid:   " +map.get("postid"));
		GetMethod getMethod = new GetMethod(KUAIDI100_URL + "type=" + map.get("type").toString() + "&postid="
				+ map.get("postid").toString());
		//System.out.println(KUAIDI100_URL + "type=" + map.get("type").toString() + "&postid="
		//		+ map.get("postid").toString());
		getMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
		getMethod.getParams().setParameter("http.protocol.cookie-policy", "netscape");
		getMethod.getParams().setParameter("http.protocol.cookie-policy", "rfc2109");
		getMethod.getParams().setParameter("http.protocol.cookie-policy", "ignoreCookies");
		getMethod.getParams().setParameter("http.protocol.cookie-policy", "default");
		
		// post请求超时 设置
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		try {
			//System.out.println("beginning dealing...... "+i+" 次");
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				//System.out.println("如果请求返回的不是成功，返回");
				logger.error("查询请求Hppt连接，返回的不是成功，继续递归，返回代码：" + statusCode);
				return getResult(map);
			} else {
				String respStr = getMethod.getResponseBodyAsString();
				if (respStr == null) {
					//System.out.println("请求结果result为空的不是成功，返回");
					return getResult(map);
				}
				result = new String(respStr);
				logger.info("......... 快递100返回消息 ，可以看看为啥会出错，消息结果   -->"+respStr);
				JSONObject jsonObj = JSONArray.parseObject(result);

				if (jsonObj.get("status") == null) {
					logger.error("status 状态为空跳转");
					return getResult(map);
				}
				String status = jsonObj.get("status").toString();
				if ("201".equals(status) || "400".equals(status) || "403".equals(status) || "200".equals(status)) {
					return result;
				}else{
					logger.error("访问快递100返回的是其他状态,status:" + jsonObj.get("status") + "。本次查询结果：" + result);
				}
			}
		} catch (HttpException e) {
			logger.error("Http连接错误，连接次数第 " + sumQuery + " 次 ** " + e.getMessage());
			return getResult(map);
		} catch (IOException e) {
			logger.error("IO异常，连接次数第 " + sumQuery + " 次 ** " + e.getMessage());
			return getResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(">>>>其他连接错误，连接次数第 " + sumQuery + " 次" + " 异常信息：" + e.getMessage());
			// System.out.println(">>>>其他连接错误，连接次数第 "+i+" 次");
		} finally {
			getMethod.releaseConnection();
		}
		logger.debug("查找到合适ip所迭代次数： " + sumQuery + "， 使用IPs: " + usedIps + " .. " + result + "  map.get(\"type\"):"
				+ map.get("type") + " " + map.get("postid"));
		// System.out.println("返回结果(迭代次数： "+i+" )。。。+使用IPs: "+usedIps+" 。。。。。。"+result+"  map.get(\"type\"):"+map.get("type")+" "+map.get("postid"));
		return result;
	}


	public static void main(String[] args) {
		//for (int i = 0; i < 10; i++) {
			QueryKuaiDiClient a = new QueryKuaiDiClient();
			HashMap<String, String> parameter = new HashMap<String, String>();
			parameter.put("type", "yuantong");// yunda yuantong
			parameter.put("postid", "8921958671");// 1000405647333 9356359685
			parameter.put("type", "huitongkuaidi");// yunda yuantong
			parameter.put("postid", "210688108436");// 1000405647333 9356359685
			System.out.println(a.getResult(parameter));
		//}
	}
}
