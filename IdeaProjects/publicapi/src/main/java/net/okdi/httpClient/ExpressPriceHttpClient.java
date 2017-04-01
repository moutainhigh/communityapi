/**  
 * @Project: public_api
 * @Title: TaskHttpClient.java
 * @Package net.okdi.httpClient
 * @author amssy
 * @date 2014-11-17 下午5:23:26
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
 */
package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;

public class ExpressPriceHttpClient extends AbstractHttpClient {

	@Autowired
	private ConstPool constPool; 

	public String getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("netId",String.valueOf(netId));
			map.put("sendAddressId",String.valueOf(sendAddressId));
			map.put("receiveAddressId",String.valueOf(receiveAddressId));
			map.put("weight",String.valueOf(weight));
			String url = constPool.getOpenApiUrl() + "expressPrice/getExpressPrice";
			String response = Post(url, map);
			if(PubMethod.isEmpty(response)){
				throw new ServiceException("publicapi.ExpressPriceHttpClient.getExpressPrice.001","数据请求异常");
			}
			return response;
	}
	public String getExpressTotalPrice(Long netId,Long sendAddressId,String weightAndreceiveAddressIds) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("netId",String.valueOf(netId));
		map.put("sendAddressId",String.valueOf(sendAddressId));
		map.put("weightAndreceiveAddressIds",weightAndreceiveAddressIds);
		String url = constPool.getOpenApiUrl() + "expressPrice/getExpressTotalPrice";
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressPriceHttpClient.getExpressTotalPrice.001","数据请求异常");
		}
		return response;
	}
	
	public String expImpData(Map<String,String> JSONData,String memthod) {
		String url = constPool.getEbusinessUrl() + memthod;
		String response = Post(url, JSONData);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.expImpDataHttpClint.001","请求ebusiness数据异常");
		}
		return response;
	}
}
