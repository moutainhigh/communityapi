package net.okdi.core.passport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chuanshi.chai
 *
 */
public class PassportHttpClient extends AbstractHttpClient{

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
		
		String url =constPool.getPassPortUrl() + methodName;
	//	String url = "http://localhost:8080/ucenter/"+methodName;
		String response = Post(url,map);
		
		if(response==null) return null;
		//Object e=JSON.parseObject(response);
		Map result = JSONObject.parseObject(response);
		return result;
	}	
	
	/**
	 * 改方法解析传过来的字符串
	 * @param methodName
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public List<Map> doPassSendArr(String methodName,Map map) throws ServiceException {
		
		String url = this.constPool.getPassPortUrl() +methodName;
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
	
	
	public String doPassSendStr(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getPassPortUrl() +methodName;
		String response = Post(url,map);
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
	
	public static void main(String[] args){
		PassportHttpClient pht = new PassportHttpClient();
		Map m = new HashMap<String,String>();
		//m.put("mobile","13261658330");
		//m.put("mobile","12345678901");
		//Map ss = pht.doPassSendObj("validate",m);
		//System.out.println(ss.get("registered"));
		
		DesEncrypt des = new DesEncrypt();
		
		Map map = new HashMap<String,String>();
		map.put("moblie", "15836254715");
		//map.put("password", "1234561");
		//map.put("source", "2");
		//Map ss = pht.doPassSendObj("user/mobLogin",map);
		Map ss = pht.doPassSendObj("service/sendSmsCode",map);
		//map.put("memberId", "13143686534925312");
		//String ss = pht.doPassSendStr("getMemberMsg",map);
		System.out.println(ss);
		
		//String t1 = des.convertPwd(ss, "ABC");
		//System.out.println(t1);
		
	}

}
