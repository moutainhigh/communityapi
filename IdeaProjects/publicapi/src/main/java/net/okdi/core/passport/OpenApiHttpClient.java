package net.okdi.core.passport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OpenApiHttpClient extends AbstractHttpClient{

	@Autowired
	private ConstPool constPool; 
	
	private static final Logger logger = LoggerFactory.getLogger(OpenApiHttpClient.class);
	
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
		
		String url =constPool.getOpenApiUrl() + methodName;
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
		
		String url = this.constPool.getOpenApiUrl() +methodName;
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
		
		String url = this.constPool.getOpenApiUrl() +methodName;
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
			throw new ServiceException("0",  "连通OpenAPI服务器异常");
		return response;
	}
	
	public String doPassSendStr(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getOpenApiUrl() +methodName;
//		String url = "http://localhost:8080/openapi/"+methodName;
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通OpenAPI服务器异常");
		return response;
	}
	public String doPassSendUcenter(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getPassPortUrl() +methodName;
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通Ucenter服务器异常");
		return response;
	}
	
	public String doPassSendWeichat(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getWeiChatApiUrl() +methodName;
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通weichat-push服务器异常");
		return response;
	}
	
	public String doPassSendStrToCallCourier(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getCallCourierOpenApiUrl() + methodName;
		String response = Post(url, map);
		if (response == null) {
			logger.error("连通CallAPI服务器异常 :: 服务地址 :: " + constPool.getCallCourierOpenApiUrl());
			throw new ServiceException("0", "连通CallOpenAPI服务器异常");
		}
		return response;
	}
	public String doPassSendStrToCommunity(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getCommunityApiUrl() + methodName;
		String response = Post(url, map);
		if (response == null) {
			logger.error("连通CommunityAPI服务器异常 :: 服务地址 :: " + constPool.getCommunityApiUrl());
			throw new ServiceException("0", "连通CommunityAPI服务器异常");
		}
		return response;
	}
	
	
	public String doPassSendStrParcel(String methodName,Map map) throws ServiceException {
		//String url = "http://localhost:8080/openapi-takeSend/" +methodName;
		String url = this.constPool.getOpenApiParcelUrl() +methodName;
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通OpenParcelAPI服务器异常");
		return response;
	}
	public String doPassTakeStrParcel(String methodName,Map map) throws ServiceException {
//		String url = "http://localhost:8081/receive/" +methodName;
		String url = this.constPool.getOpenApiTakeUrl() +methodName;
		logger.info("查询的url:"+url+",参数是map="+map);
		String response = Post(url,map);
		logger.info("返回的结果是:"+response);
		if(response==null)
			throw new ServiceException("0",  "连通OpenParcelAPI服务器异常");
		return response;
	}
	//调用运营平台的接口,查询电商件的包裹
	public String ParcelFromGT(String methodName,Map map) throws ServiceException {
		String url = constPool.getOpenApiTakeUrl()+methodName;
		logger.info("查询的url:"+url+",参数是map="+map);
		String response = Post(url,map);
		logger.info("返回的结果是:"+response);
		if(response==null)
			throw new ServiceException("0",  "连通OpenParcelAPI服务器异常");
		return response;
	}
	
	public String doOpencustomer(String methodName,Map map) throws ServiceException {
		String url = this.constPool.getOpencustomerUrl() +methodName;
		String response = Post(url,map);
		if(response==null)
			throw new ServiceException("0",  "连通opencustomer服务器异常");
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
		OpenApiHttpClient pht = new OpenApiHttpClient();
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

	/**首页查询外快
	 * @param memberId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getExtraMoney(Long memberId){
		if (memberId==null) {
			return "0";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String url = this.constPool.getCommunityApiUrl()+ "attention/getExtraMoney";
		//String url="http://localhost:8081/communityapi/attention/getExtraMoney";
		String result=Post(url, map);
		JSONObject object = JSON.parseObject(result);
		if ("false".equals(String.valueOf(object.get("success")))) {
			return "0";
		}
		String count = String.valueOf(object.get("data"));
		return count;
		
	}

}