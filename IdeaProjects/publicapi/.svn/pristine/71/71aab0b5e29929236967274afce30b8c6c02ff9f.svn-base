package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author feng.wang
 * @version V1.0
 */
public class CompInfoHttpClient extends AbstractHttpClient {

	//@Value("${api.exp}")
	//private String url;
	
	@Autowired
	private ConstPool constPool; 

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据地址的经纬度获取网络下的站点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-17 下午03:27:54</dd>
	 * @param longitude(double) 地址的经度，必填
	 * @param latitude(double) 地址的纬度，必填
	 * @param netId(Long) 网络ID，非必填
	 * @return
	 * @since v1.0
	*/
	public String getExpSitesToEc(double longitude, double latitude, Long netId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("longitude", String.valueOf(longitude));
		params.put("latitude", String.valueOf(latitude));
		params.put("netId", netId == null ? "" : netId.toString());
		String methodName = "compInfo/getExpSites";
		String result = Post(constPool.getOpenApiUrl() + methodName, params);
		return result;
	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据网络ID和取件省份ID获取网络报价（取件身份指的是收派员取件地址）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-17 下午03:28:01</dd>
	 * @param netId(Long) 网络ID，必填
	 * @param provinceId(Long) 取件省份ID，必填
	 * @return
	 * @since v1.0
	*/
	public String getNetQuote(Long netId, Long provinceId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("netId", netId == null ? "" : netId.toString());
		params.put("provinceId", provinceId == null ? "" : provinceId.toString());
		String methodName = "netInfo/getNetPrice";
		String result = Post(constPool.getOpenApiUrl() + methodName, params);
		return result;
	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取公司派送范围和不派送范围</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-17 下午03:28:05</dd>
	 * @param compId(Long) 公司ID，必填
	 * @return
	 * @since v1.0
	*/
	public String getExpSendRange(Long compId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("compId", compId == null ? "" : compId.toString());
		String methodName = "compInfo/getExpSendRange";
		String result = Post(constPool.getOpenApiUrl() + methodName, params);
		return result;
	}
	public String getCoverExceedAddress(Long netId,Long addressId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("netId", netId == null ? "" : netId.toString());
		params.put("addressId", addressId == null ? "" : addressId.toString());
		String methodName = "address/getCoverExceedAddress";
		String result = Post(constPool.getOpenApiUrl() + methodName, params);
		return result;
	}

	public String queryMemberInfoForFhw(String memberPhone){
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("memberPhone", String.valueOf(memberPhone));
			String methodName = "memberInfo/queryMemberInfoForFhw";
			String result = Post(constPool.getOpenApiUrl() + methodName, params);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
