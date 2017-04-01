package net.okdi.apiV2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV2.vo.VO_BasCompInfo;

public interface ExpressRegisterService {
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryAuditStatus 
	 * @Description: TODO(获取快递员的身份和快递认证状态) 
	 * @param @param memberId
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2015-12-2
	 * @auth zhaohu
	 */
	HashMap<String, Object> queryAuditStatus(String memberId);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryCompInfoByFirstLetter 
	 * @Description: TODO(获取站点信息列表-按首字母排序) 
	 * @param @param netId
	 * @param @param addressName
	 * @param @return   
	 * @return Map<String,List<VO_BasCompInfo>>  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	Map<String, List<VO_BasCompInfo>> queryCompInfoByFirstLetter(String netId,String addressName);
    
	
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
	HashMap<String, Object> registerCourier(Long memberId,String memberName,Long netId,String compId,String compTypeNum,String compName,String applicationRoleType);
   /**
    * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isRelationByLeader 
    * @Description: TODO(判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色) 
    * @param @param compId
    * @param @param compNumType
    * @param @return   
    * @return HashMap<String,Object>  返回值类型
    * @throws 
    * @date 2015-12-5
    * @auth zhaohu
    */
	HashMap<String, Object> isRelationByLeader(String compId, String compNumType);
	
	public List queryRepeatOrder(String orderArr,String netId,String phone);
	/**
	 * @param memberName 
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.submitUseCompInfo 
	 * @Description: TODO(站长领用网络抓取的站点) 
	 * @param @param webCompId
	 * @param @param compName
	 * @param @param belongToNetId
	 * @param @param county
	 * @param @param member_id
	 * @param @param addressId
	 * @param @param roleType
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	HashMap<String, Object> submitUseCompInfo(Long webCompId,String compName, String belongToNetId, String county,String member_id, String addressId, String roleType, String memberName);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.initExpressAudit 
	 * @Description: TODO(初始化身份和快递认证信息) 
	 * @param @param memberId
	 * @param @param compId
	 * @param @param roleType   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	public void initExpressAudit(String memberId,String compId,String roleType);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isRepeatCompInfoName 
	 * @Description: TODO(查询在同一个快递网络下是否有重名的站点) 
	 * @param @param netId
	 * @param @param compName
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	HashMap<String, Object> isRepeatCompInfoName(String netId, String compName);
	/**
	 * @param memberName 
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.saveNewCompInfo 
	 * @Description: TODO(站长创建新站点/营业分部) 
	 * @param @param memberId
	 * @param @param netId
	 * @param @param compTypeNum
	 * @param @param compName
	 * @param @param compTelephone
	 * @param @param county
	 * @param @param addressId
	 * @param @param descriptionMsg
	 * @param @param roleType
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	HashMap<String, Object> saveNewCompInfo(Long memberId, Long netId,String compTypeNum, String compName, String compTelephone,String county,String addressId, String descriptionMsg, String roleType, String memberName);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryCountInCompInfo 
	 * @Description: TODO( 查询此站点下的收派员和后勤人员数量，不包括站长角色) 
	 * @param @param compId
	 * @param @return   
	 * @return int  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	int queryCountInCompInfo(String compId);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.leaveOfficeByLeader 
	 * @Description: TODO(站长离职) 
	 * @param @param memberId
	 * @param @param compId
	 * @param @param memberName
	 * @param @param memberPhone   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	void leaveOfficeByLeader(Long memberId, Long compId, String memberName,
			String memberPhone);
    
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryGSInfo 
	 * @Description: TODO(查询归属信息) 
	 * @param memberId
	 * @param roleId
	 * @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth guoqiang.jing
	 */
	public Map queryGSInfo(Long memberId,int roleId) ;
	int queryCountInShop(String compId);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isIdentifyCompInfo 
	 * @Description: TODO(判断该站点的站点信息是否已认证) 
	 * @param @param compTypeNum
	 * @param @param compId
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2015-12-28
	 * @auth zhaohu
	 */
	HashMap<String, Object> isIdentifyCompInfo(String compTypeNum, String compId);
	/**
	 * @param reverseImg 
	 * @param holdImg 
	 * @param licenseNum 
	 * @param responsibleNum 
	 * @param responsibleTelephone 
	 * @param responsible 
	 * @param descriptionMsg 
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.saveCompInfoByCourier 
	 * @Description: TODO(收派员/后勤创建站点或领取站点并提交站点认证信息----收派员/后勤角色使用V3) 
	 * @param @param webCompId
	 * @param @param memberId
	 * @param @param netId
	 * @param @param compTypeNum
	 * @param @param compName
	 * @param @param compTelephone
	 * @param @param county
	 * @param @param addressId
	 * @param @param roleType
	 * @param @param memberName
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2015-12-30
	 * @auth zhaohu
	 */
	HashMap<String, Object> saveCompInfoByCourier(Long compId,
			Long memberId, Long netId, String compTypeNum, String compName,
			String compTelephone, String county, String addressId,
			String roleType, String memberName, String responsible, String responsibleTelephone, String responsibleNum, String licenseNum, String holdImg, String reverseImg);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.saveCompVerifyInfoV3 
	 * @Description: TODO(提交站点认证信息V3) 
	 * @param @param loginCompId
	 * @param @param responsible
	 * @param @param responsibleTelephone
	 * @param @param responsibleNum
	 * @param @param holdImg
	 * @param @param reverseImg
	 * @param @param memberId
	 * @param @param licenseNum   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-31
	 * @auth zhaohu
	 */
	HashMap<String, Object> saveCompVerifyInfoV3(Long loginCompId, String responsible,
			String responsibleTelephone, String responsibleNum, String holdImg,
			String reverseImg, Long memberId, String licenseNum);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.submitUseCompInfoV3 
	 * @Description: TODO(站长领用网络抓取的站点V3--站长角色使用) 
	 * @param @param webCompId
	 * @param @param compName
	 * @param @param belongToNetId
	 * @param @param county
	 * @param @param member_id
	 * @param @param addressId
	 * @param @param roleType
	 * @param @param memberName
	 * @param @param compTypeNum
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2016-1-5
	 * @auth zhaohu
	 */
	HashMap<String, Object> submitUseCompInfoV3(Long webCompId,
			String compName, String belongToNetId, String county,
			String member_id, String addressId, String roleType,
			String memberName, String compTypeNum);
	/**
    * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isRelationByLeader 
    * @Description: TODO(判断选择的加盟站点/网络抓取站点/营业分部是否被领用v3--站长角色) 
    * @param @param compId
    * @param @param compNumType
    * @param @return   
    * @return HashMap<String,Object>  返回值类型
    * @throws 
    * @date 2015-12-5
    * @auth zhaohu
    */
	HashMap<String, Object> isRelationByLeaderV3(String compId,
			String compNumType);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.showSiteInfoV3 
	 * @Description: TODO(查看站点认证信息V3) 
	 * @param @param memberId
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2016-1-9
	 * @auth zhaohu
	 */
	HashMap<String, Object> showSiteInfoV3(String memberId);
	void sendSmsByRecharge(Double money, Long memberId);
	String getAccountIdByMemberId(String memberId);
}
