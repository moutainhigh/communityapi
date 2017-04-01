
package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;

public class ExpressSiteHttpClient extends AbstractHttpClient {

	@Autowired
	private ConstPool constPool; 

	public String saveCompInfo(Long memberId, Long netId, String compTypeNum,
			String compName, String compTelephone, Long addressId,
			String address) {
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("memberId",String.valueOf(memberId));
			map.put("netId",String.valueOf(netId));
			map.put("compTypeNum",compTypeNum);
			map.put("compName",compName);
			map.put("compTelephone",compTelephone);
			map.put("addressId",String.valueOf(addressId));
			map.put("address",address);
		//	String url = constPool.getOpenApiUrl() + "expressSite/saveCompInfo";
			String url = constPool.getOpenApiUrl() +"expressSite/saveCompInfo";
			
			String response = Post(url, map);
			if(PubMethod.isEmpty(response)){
				throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
			}
			return response;
	}
	
	
	/**
	 * 登录网点ID 查询网点详细信息(认证信息运营平台用)
	 */
	public String queryCompVerifyInfo(Long loginCompId) {
	
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("loginCompId",String.valueOf(loginCompId));//登录网点ID
		
		String url = constPool.getOpenApiUrl() + "expressSite/queryCompVerifyInfo";
		
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
		}
		return response;
	}
	
	/**
	 * 第四个接口：快递员通过手机号查询站长入住状态
	 */
	public String queryJoinState(String member_phone) {
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_phone",member_phone);//手机号13810362693
		
		String url = constPool.getOpenApiUrl() + "expressSite/queryJoinState";
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
		}
		return response;
	}
	
	/**
	 * 第五个接口：快递员申请入站
	 */
	public String applyJoin(String member_id,String audit_comp,String application_role_type,String audit_item) {
	
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("member_id",member_id);//id 3435341
		map.put("audit_comp",audit_comp);  //5980
		map.put("application_role_type",application_role_type);
		map.put("audit_item",audit_item);
		
		String url = constPool.getOpenApiUrl() + "expressSite/applyJoin";
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 第6个接口：
	//通过以下方式邀请店长入住, 微信, 短信
	 */
	public String invite(String telephone) {
	
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("telephone",telephone);//手机号18810070246
		
		String url = constPool.getOpenApiUrl() + "expressSite/invite";
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
		}
		return response;
	}
/*---------------------------------站长的操作------------------------------------------*/	
	/**
	 * 第7个接口：
	//提交网点认证信息xxx
	 */
	public String submitCompVerifyInfo(String loginCompId,String responsible,String responsibleTelephone,String responsibleNum,String frontImg,String businessLicenseImg,String shopImg) {
	
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("loginCompId",loginCompId);//登录网点ID 3435104
		
		map.put("responsible",responsible);
		map.put("responsibleTelephone",responsibleTelephone);
		map.put("responsibleNum",responsibleNum);
		
		map.put("frontImg",frontImg);
		map.put("businessLicenseImg",businessLicenseImg);
		map.put("shopImg",shopImg);
		
		String url = constPool.getOpenApiUrl() + "expressSite/submitCompVerifyInfo";
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
		}
		return response;
	}
	/*---------------------------------运营平台的操作------------------------------------------*/	
	/**
	 * 第8个接口：
	//审核站点
	 */
	public String auditSite(String compId,String compStatus) {
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);//登录网点ID 3435104
		map.put("compStatus",compStatus); //1
		
		String url = constPool.getOpenApiUrl() + "expressSite/auditSite";
		String response = Post(url, map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.ExpressSiteHttpClient.saveCompInfo.001","数据请求异常");
		}
		return response;
	}

}
