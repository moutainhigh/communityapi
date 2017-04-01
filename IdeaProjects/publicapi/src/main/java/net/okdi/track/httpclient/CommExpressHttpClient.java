package net.okdi.track.httpclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chuanshi.chai
 *
 */
public class CommExpressHttpClient extends AbstractHttpClient{

	@Autowired
	private ConstPool constPool; 
	
	//private static String constPool.getUrl()=null;
	
//	public void setUrl(String url){
//		this.constPool.getUrl()=url;
//	}
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
		
		String url =constPool.getCommExpressApiUrl() + methodName;
		String response = Post(url,map);
		
		if(response==null) return null;
		//Object e=JSON.parseObject(response);
		Map result = JSONObject.parseObject(response);
		return result;
	}	
	public String  getPhoneCity(String phoneNum){
		String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
		Map<String, String> map = new HashMap<String, String>();
		map.put("tel", phoneNum);
		String result = Post(url, map);
		String city = JSON.parseObject(result.replace("__GetZoneResult_ = ","")).getString("province");
		return city;
	}
	/**
	 * 改方法解析传过来的字符串
	 * @param methodName
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public List<Map> doPassSendArr(String methodName,Map map) throws ServiceException {
		
		String url = this.constPool.getCommExpressApiUrl() +methodName;
		String response = Post(url,map);
		List list = new ArrayList();
		if(response==null) return list;
		JSONArray jsa = JSONArray.parseArray(response);
		for(int i = 0 ;i<jsa.size();i++){
			Map m = (Map) jsa.get(i);
			list.add(m);
		}
		return list;
	}	
	/**
	 * 改方法解析传过来的字符串
	 * @param methodName
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public List<String> doPassList(String methodName,Map map) throws ServiceException {
		
		String url = this.constPool.getCommExpressApiUrl() +methodName;
		String response = Post(url,map);
		List list = new ArrayList();
		if(response==null) return list;
		String[] finaStr = response.replace("[", "").replace("]", "").split(",");
		return Arrays.asList(finaStr);
	}	
	
	public String doExpBack(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getExpBack();
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通CommExpressAPI服务器异常");
		return response;
	}
	
	public String doPassSendStr(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getCommExpressApiUrl() +methodName;
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通CommExpressAPI服务器异常");
		return response;
	}
	
	public List<Map> json2list(String response){
		List list = new ArrayList();
		JSONArray jsa = JSONArray.parseArray(response);
		for(int i = 0 ;i<jsa.size();i++){
			Map m = (Map) jsa.get(i);
			list.add(m);
		}
		return list;
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