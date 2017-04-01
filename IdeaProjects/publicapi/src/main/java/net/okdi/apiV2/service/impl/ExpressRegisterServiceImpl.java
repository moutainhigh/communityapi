package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV2.service.ExpressRegisterService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressRegisterServiceImpl implements ExpressRegisterService {

	public static final Log logger = LogFactory.getLog(ExpressRegisterServiceImpl.class);
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	

	/**
	 * @MethodName: net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.java.registerCourier 
	 * @Description: 注册收派员或者后勤角色
	 * @param memberId  快递员membrId,ucenter自动生成,不能null 
	 *        memberName
	 * @param netId      不能null
	 * @param compId     可null 
	 * @param compName   不能null
	 * @param applicationRoleType  申请角色类型  0 收派员 -1 后勤  不能null
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-3
	 * @auth guoqiang.jing
	 */
	
	
	public String registerCourier(Long memberId,String memberName,Long netId,String compId,String compTypeNum,String compName,String applicationRoleType,String employeeNum,String externalAccount){
		
		
		Map<String, String> map = new HashMap<String, String>();
			
			map.put("memberId",String.valueOf(memberId));
			map.put("memberName",memberName);
			map.put("netId",String.valueOf(netId));
			map.put("compId",String.valueOf(compId));
			map.put("compTypeNum",compTypeNum);
			map.put("compName",compName);
			map.put("employeeNum",employeeNum);
			map.put("applicationRoleType",String.valueOf(applicationRoleType));
			map.put("externalAccount",externalAccount);
			String result = null;
			try {
				result = openApiHttpClient.doPassSendStr("expressRegister/registerCourier", map);
			} catch (Exception e) {
				return PubMethod.sysErrorUS();
			}
			return result;
		
	}

	/**
	 * 获取快递员的身份和快递认证状态
	 */
	@Override
	public String queryAuditStatus(String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStr("expressRegister/queryAuditStatus/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryAuditStatus.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 获取站点信息列表-按首字母排序
	 */
	@Override
	public String queryCompInfoByFirstLetter(String netId, String addressName,String latitude,String longitude) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("netId",netId);
		map.put("addressName",addressName);
		map.put("latitude",latitude);
		map.put("longitude",longitude);
		String response = openApiHttpClient.doPassSendStr("expressRegister/queryCompInfoByFirstLetter/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryCompInfoByFirstLetter.001","数据请求异常");
		}
		return response;
	}
	
	/**
	 * 获取站点信息列表-按首字母排序
	 */
	@Override
	public String queryCompInfoByFirstLetter2(String netId, String addressName,String latitude,String longitude,String compName,Integer compType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("netId",netId);
		map.put("addressName",addressName);
		map.put("latitude",latitude);
		map.put("longitude",longitude);
		map.put("compName",compName);
		map.put("compType",compType);
		String response = openApiHttpClient.doPassSendStr("expressRegister/queryCompInfoByFirstLetter2/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryCompInfoByFirstLetter2.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色
	 */
	@Override
	public String isRelationByLeader(String compId, String compNumType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);
		map.put("compNumType",compNumType);
		String response = openApiHttpClient.doPassSendStr("expressRegister/isRelationByLeader/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.isRelationByLeader.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 站长领用网络抓取的站点
	 */
	@Override
	public String submitUseCompInfo(Long webCompId, String compName,String belongToNetId, String county, String member_id,
			String addressId,String roleType,String memberName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("webCompId",String.valueOf(webCompId));
		map.put("compName",compName);
		map.put("belongToNetId",belongToNetId);
		map.put("county",county);
		map.put("member_id",member_id);
		map.put("addressId",addressId);
		map.put("roleType",roleType);
		map.put("memberName",memberName);
		String response = openApiHttpClient.doPassSendStr("expressRegister/submitUseCompInfo/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.submitUseCompInfo.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 查询在同一个快递网络下是否有重名的站点
	 */
	@Override
	public String isRepeatCompInfoName(String netId, String compName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("netId",netId);
		map.put("compName",compName);
		String response = openApiHttpClient.doPassSendStr("expressRegister/isRepeatCompInfoName/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.isRepeatCompInfoName.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 站长创建新站点/营业分部
	 */
	@Override
	public String saveNewCompInfo(Long memberId, Long netId,String compTypeNum, String compName, String compTelephone,
			String county, String addressId,String descriptionMsg,String roleType,String memberName,String province,String city,String compNum,String longitude,String latitude) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId",String.valueOf(memberId));
		map.put("netId",String.valueOf(netId));
		map.put("compTypeNum",compTypeNum);
		map.put("compName",compName);
		map.put("compTelephone",compTelephone);
		map.put("county",county);
		map.put("addressId",addressId);
		map.put("descriptionMsg",descriptionMsg);
		map.put("roleType",roleType);
		map.put("memberName",memberName);
		map.put("province",province);
		map.put("city",city);
		map.put("compNum",compNum);
		map.put("longitude",longitude);
		map.put("latitude",latitude);
		String response = openApiHttpClient.doPassSendStr("expressRegister/saveNewCompInfo/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.saveNewCompInfo.001","数据请求异常");
		}
		return response;
	}
	
	
	
	public String queryRepeatOrder(String orderArr,String netId,String phone){
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("orderArr",orderArr);
		map.put("netId",String.valueOf(netId));
		map.put("phone",phone);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("expressRegister/queryRepeatOrder", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	/**
	 * 站长离职
	 */
	@Override
	public String leaveOfficeByLeader(Long memberId, Long compId,String memberName, String memberPhone) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("memberId",String.valueOf(memberId));
		map.put("compId",String.valueOf(compId));
		map.put("memberName",memberName);
		map.put("memberPhone",memberPhone);
		String response = openApiHttpClient.doPassSendStr("expressRegister/leaveOfficeByLeader/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.leaveOfficeByLeader.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 查询此站点下的收派员和后勤人员数量，不包括站长角色
	 */
	@Override
	public String queryCountInCompInfo(String compId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStr("expressRegister/queryCountInCompInfo/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryCountInCompInfo.001","数据请求异常");
		}
		return response;
	}
	
	
    public String queryGSInfo(Long memberId,int roleId) {
		
	   Map<String, String> map = new HashMap<String, String>();
		
		map.put("memberId",String.valueOf(memberId));
		map.put("roleId",String.valueOf(roleId));
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("expressRegister/queryGSInfo", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
    /**
     * 查询此代收点下的店员数量，不包括店长角色V3
     */
    @Override
	public String queryCountInShop(String compId) {
    	Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStr("expressRegister/queryCountInShop/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryCountInShop.001","数据请求异常");
		}
		return response;
	}
    /**
     * 判断该站点的站点信息是否已认证
     */
	@Override
	public String isIdentifyCompInfo(String compTypeNum, String compId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compTypeNum",compTypeNum);
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStr("expressRegister/isIdentifyCompInfo/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.isIdentifyCompInfo.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 收派员/后勤创建或领取未认证站点并提交站点认证信息--收派员/后勤角色使用V3
	 */
	@Override
	public String saveCompInfoByCourier(String compId, String memberId, String netId,
			String compTypeNum, String compName, String compTelephone,
			String county, String addressId, String roleType,
			String memberName, String responsible, String responsibleTelephone,
			String responsibleNum, String licenseNum, String holdImg,
			String reverseImg,String province,String city) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);
		map.put("memberId",memberId);
		map.put("netId",netId);
		map.put("compTypeNum",compTypeNum);
		map.put("compName",compName);
		map.put("compTelephone",compTelephone);
		map.put("county",county);
		map.put("addressId",addressId);
		map.put("roleType",roleType);
		map.put("memberName",memberName);
		map.put("responsible",responsible);
		map.put("responsibleTelephone",responsibleTelephone);
		map.put("responsibleNum",responsibleNum);
		map.put("licenseNum",licenseNum);
		map.put("holdImg",holdImg);
		map.put("reverseImg",reverseImg);
		map.put("province",province);
		map.put("city",city);
		String response = openApiHttpClient.doPassSendStr("expressRegister/saveCompInfoByCourier/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.saveCompInfoByCourier.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 提交站点认证信息V3--站长/收派员/后勤通用
	 */
	@Override
	public String saveCompVerifyInfoV3(String loginCompId, String responsible,
			String responsibleTelephone, String responsibleNum, String holdImg,
			String reverseImg, String memberId, String licenseNum) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginCompId",loginCompId);
		map.put("memberId",memberId);
		map.put("responsible",responsible);
		map.put("responsibleTelephone",responsibleTelephone);
		map.put("responsibleNum",responsibleNum);
		map.put("licenseNum",licenseNum);
		map.put("holdImg",holdImg);
		map.put("reverseImg",reverseImg);
		String response = openApiHttpClient.doPassSendStr("expressRegister/saveCompVerifyInfoV3/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.saveCompVerifyInfoV3.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 判断选择的加盟站点/网络抓取站点/营业分部是否被领用V3--站长角色
	 */
	@Override
	public String isRelationByLeaderV3(String compId, String compNumType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);
		map.put("compNumType",compNumType);
		String response = openApiHttpClient.doPassSendStr("expressRegister/isRelationByLeaderV3/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.isRelationByLeaderV3.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 站长领用网络抓取的站点V3--站长角色
	 */
	@Override
	public String submitUseCompInfoV3(Long webCompId, String compName,
			String belongToNetId, String county, String member_id,
			String addressId, String roleType, String memberName,
			String compTypeNum,String province,String city) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("webCompId",String.valueOf(webCompId));
		map.put("compName",compName);
		map.put("belongToNetId",belongToNetId);
		map.put("county",county);
		map.put("member_id",member_id);
		map.put("addressId",addressId);
		map.put("roleType",roleType);
		map.put("memberName",memberName);
		map.put("compTypeNum",compTypeNum);
		map.put("province",province);
		map.put("city",city);
		String response = openApiHttpClient.doPassSendStr("expressRegister/submitUseCompInfoV3/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.submitUseCompInfoV3.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 查看站点认证信息V3
	 */
	@Override
	public String showSiteInfoV3(String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStr("expressRegister/showSiteInfoV3/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.showSiteInfoV3.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 处理旧数据程序
	 */
	@Override
	public String updateProvinceNotNullCityNull(Long pageSize) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageSize", pageSize+"");
		String response = openApiHttpClient.doPassSendStr("expressRegister/updateProvinceNotNullCityNull/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.updateProvinceNotNullCityNull.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 处理旧数据程序
	 */
	@Override
	public String joinDefaultOldData() {
		Map<String, String> map = new HashMap<String, String>();
		String response = openApiHttpClient.doPassSendStr("expressRegister/joinDefaultOldData/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.joinDefaultOldData.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 处理旧数据程序
	 */
	@Override
	public String updateAddressIdIsNull() {
		Map<String, String> map = new HashMap<String, String>();
		String response = openApiHttpClient.doPassSendStr("expressRegister/updateAddressIdIsNull/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.updateAddressIdIsNull.001","数据请求异常");
		}
		return response;
	}
	
}
