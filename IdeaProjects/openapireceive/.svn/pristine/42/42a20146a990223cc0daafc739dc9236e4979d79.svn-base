package net.okdi.core.sms;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

/**
 * 从连接池里面获取HttpClient
 * @author feng.wang
 * @version V1.0
*/
public class PoolingHttpClient {

	/** 超时时间 **/
	private static Integer timeout = 5000;

	/** httpClient连接池 **/
	PoolingHttpClientConnectionManager poolConnManager = null;

	/** 单例模式 **/
	private static PoolingHttpClient instance = new PoolingHttpClient();

	/** 初始化连接池 **/
	private PoolingHttpClient() {

		try {
			// 声明对https连接支持
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			// 初始化连接管理器
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// 连接池最大生成连接数200
			poolConnManager.setMaxTotal(200);
			// 默认设置route最大连接数为20
			poolConnManager.setDefaultMaxPerRoute(20);
			// 指定专门的route，设置最大连接数为80
			// HttpHost localhost = new HttpHost("locahost", 80);
			// cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}

	}

	public static PoolingHttpClient getInstance() {
		return instance;
	}

	// 获取连接
	public CloseableHttpClient getHttpClient() {
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout).setSocketTimeout(timeout).build();
		// 获取httpClient
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
				.setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}

}
