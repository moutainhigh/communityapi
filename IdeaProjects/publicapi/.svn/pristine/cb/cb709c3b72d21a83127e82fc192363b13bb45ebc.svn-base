package net.okdi.core.passport;



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
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;



public class ShortLinkHttpClient {
	
	
    /**连接超时时间*/
    private final static int CONN_TIMEOUT=50000;
    /**请求超时时间*/
    private final static int REQUEST_TIMEOUT=50000;
    
    private final static String RETURN_FOMATE="json";//支持json、xml
    
    static HttpClient httpClient = new HttpClient();
    
    /**
     * 请求服务器入口
     * 创 建 人:  XX
     * 创建时间:  2012-11-7 下午03:39:55  
     * @param methodName 方法名，如：短信发送接口 smsGateway/doSmsSend
     * @param map  上送输入参数集合
     * @return
     * @see [类、类#方法、类#成员]
     */
	public String doPost(String url,Map<String,String> map){
		
		System.out.println(url+"===================="+url);
		System.out.println(map+"===================="+map);
		
		String result =null;
	   
	    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);  //连接超时 设置
		PostMethod postMethod = new PostMethod(url); 
		
        postMethod.setRequestBody(this.getPostParams(map));
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,REQUEST_TIMEOUT); //post请求超时 设置
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
            int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK){//如果请求返回的不是成功，则进行处理
				return new String(postMethod.getResponseBodyAsString()) ;
			}else{
			    result = new String(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) { 
				System.out.println("Please check your provided http address!"); 
				e.printStackTrace(); 
				return e.getMessage();
		} catch (IOException e) {
			   e.printStackTrace(); 
			   return e.getMessage();
		}finally{			
			postMethod.releaseConnection();
			((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
			
		}
		return result;
	}
	
	 private NameValuePair[] getPostParams(Map<String,String> map){
		    map.put("_type", RETURN_FOMATE);
	    	NameValuePair[] data =new NameValuePair[map.size()];
			Iterator<String> it=map.keySet().iterator();
			int i=0;
			while(it.hasNext()){
				String key = it.next();
				String value =map.get(key);
				data[i]=new NameValuePair(key,value);
				i++;
			}
			return data;
	    }
	 
	 
	 public static void main(String[] args){
		 ShortLinkHttpClient httpClient = new ShortLinkHttpClient();
		 Map<String,String> map = new HashMap<String,String>();
		 String str = httpClient.doPost("http://shorturl.okdit.net/getShortUrl",map);
		 System.out.println(str);
	 }
}
