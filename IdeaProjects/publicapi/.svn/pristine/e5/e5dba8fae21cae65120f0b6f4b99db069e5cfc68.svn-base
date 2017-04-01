/**  
 * @Project: openapi
 * @Title: BaiDuClient.java
 * @Package net.okdi.core.sms
 * @author amssy
 * @date 2015-2-2 下午04:47:34
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.mob.service.BaiDuClient;

import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class BaiDuClientImpl extends AbstractHttpClient implements BaiDuClient{
    @Value("${BD_ADDRESS_URL}")
	private String URL ;
    @Value("${BD_ADDREESS_RESULTTYPE}")
	private String RESULTTYPE ;
    @Value("${BD_ADDREESS_KEY}")
	private String KEY ;
  
    @Override
	public Double[] getResult(String addressName){
		String addresses = this.fixAddress(addressName);
    	Map<String,String> map = new HashMap<String,String>();
		map.put("output", RESULTTYPE);
		try {
			map.put("address", URLEncoder.encode(addresses,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("ak", KEY);
		String result = this.Get(URL,map);
		Double [] location = {0.0,0.0};
        JSONObject obj = JSON.parseObject(result);
        if(obj == null){
        	return location;
        }
        JSONObject obj2;
        if(obj.getInteger("status")==0&&(obj2=obj.getJSONObject("result"))!=null){
        	location[0] = obj2.getJSONObject("location").getDouble("lng");
        	location[1] = obj2.getJSONObject("location").getDouble("lat");
        }
        else {
        	baiduException(obj.getInteger("status"));
		}
		return location;
	}

    public String fixAddress(String address){
    	StringBuffer sb = new StringBuffer("");
    	String[]addresses = address.split("\\|");
    	if (addresses!=null) {
    		addresses[0] = addresses[0].replaceAll("-","");
    		sb.append(addresses[0]);
    		if (addresses.length>1) {
            sb.append(addresses[1]);
			}
    	}
    return sb.toString();
    }
    
    public void baiduException (Integer flag){
    	
		if (1==flag) {
			throw new ServiceException("服务器内部错误");
		}
		if (2==flag) {
			throw new ServiceException("请求参数非法");
		}
		if (3==flag) {
			throw new ServiceException("权限校验失败");
		}
		if (4==flag) {
			throw new ServiceException("配额校验失败");
		}
		if (5==flag) {
			throw new ServiceException("ak不存在或者非法");
		}
		if (101==flag) {
			throw new ServiceException("服务禁用");
		}
		if (102==flag) {
			throw new ServiceException("不通过白名单或者安全码不对");
		}
		if (flag>200&&flag<300) {
			throw new ServiceException("无权限");
		}
		if (flag>300) {
			throw new ServiceException("配额错误");
		}
		
				
		}
    }


