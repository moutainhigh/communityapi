package net.okdi.core.passport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.okdi.core.exception.ServiceException;
import net.okdi.httpClient.AbstractHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chuanshi.chai
 *
 */
public class IsThisPhoneLoginHttpClient extends AbstractHttpClient{

	private String basUrl=null; 
	
	//private static String constPool.getUrl()=null;
	
	public void setUrl(String url){
		this.basUrl = url;
	}
	/*passport_service_http_url参数配置systemConfig.properties文件中*/
	//String url=constPool.getPassPortUrl();
	//private String url="http://cas.okdit.net/service/";
	/**
	 * @param methodName 需要调用方法的名字
	 * @param map 传入的参数
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> doPassSendObj(String methodName,Map map) throws ServiceException {
		
		String url = basUrl + methodName;
		System.out.println("IsthisPhoneURL:"+url);
		String response = Post(url,map);
		if(response==null) return null;
		Map result = JSONObject.parseObject(response);
		return result;
	}	
	
	public String doSendStr(String methodName,Map map) throws ServiceException {
		String url = basUrl +methodName;
		String response = Post(url,map);
		return response;
	}
	
	
	public String getJsonStr(String response){
		List list = new ArrayList();
		JSONArray jsa = JSONArray.parseArray(response);
		if(jsa.size()<0){
			throw new ServiceException("在通行证中找不到相应的数据");
		}
		return jsa.get(0).toString();
	}
	

}
