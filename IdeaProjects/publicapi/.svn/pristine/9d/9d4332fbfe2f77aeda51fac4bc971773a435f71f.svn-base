package net.okdi.apiV2.service;


public interface ExpressRegisterService {
	 
	
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
   String registerCourier(Long memberId,String memberName,Long netId,String compId,String compTypeNum,String compName,String applicationRoleType,String employeeNum,String externalAccount);
   
   /**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryRepeatOrder 
	 * @Description: 查询单号是否被重复认证
	 * @param orderArr   订单数据
	 * @param netId      网络id
	 * @param phone      本人电话，除本人外是否被其他人认证过
	 * @throws 
	 * @date 2015-12-4
	 * @auth guoqiang.jing
	 */
   
   public String queryRepeatOrder(String orderArr,String netId,String phone);
   	/**
   	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryAuditStatus 
   	 * @Description: TODO(获取快递员的身份和快递认证状态) 
   	 * @param @param memberId
   	 * @param @return   
   	 * @return String  返回值类型
   	 * @throws 
   	 * @date 2015-12-4
   	 * @auth zhaohu
   	 */
	String queryAuditStatus(String memberId);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryCompInfoByFirstLetter 
	 * @Description: TODO(获取站点信息列表-按首字母排序) 
	 * @param @param netId
	 * @param @param addressName
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	String queryCompInfoByFirstLetter(String netId, String addressName,String latitude,String longitude);
	public String queryCompInfoByFirstLetter2(String netId, String addressName,String latitude,String longitude,String compName,Integer compType);
	
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isRelationByLeader 
	 * @Description: TODO(判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色) 
	 * @param @param compId
	 * @param @param compNumType
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	String isRelationByLeader(String compId, String compNumType);
	/**
	 * @param roleType 
	 * @param memberName 
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.submitUseCompInfo 
	 * @Description: TODO(站长领用网络抓取的站点) 
	 * @param @param webCompId
	 * @param @param compName
	 * @param @param belongToNetId
	 * @param @param county
	 * @param @param member_id
	 * @param @param addressId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	String submitUseCompInfo(Long webCompId, String compName,String belongToNetId, String county, String member_id,String addressId, String roleType, String memberName);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isRepeatCompInfoName 
	 * @Description: TODO(查询在同一个快递网络下是否有重名的站点) 
	 * @param @param netId
	 * @param @param compName
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	String isRepeatCompInfoName(String netId, String compName);
	/**
	 * @param descriptionMsg 
	 * @param roleType 
	 * @param memberName 
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.saveNewCompInfo 
	 * @Description: TODO(站长创建新站点/营业分部V2) 
	 * @param @param memberId
	 * @param @param netId
	 * @param @param compTypeNum
	 * @param @param compName
	 * @param @param compTelephone
	 * @param @param county
	 * @param @param addressId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	String saveNewCompInfo(Long memberId, Long netId, String compTypeNum,String compName, String compTelephone, String county,String addressId, String descriptionMsg, String roleType, String memberName,String province,String city,String compNum,String longitude,String latitude);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.leaveOfficeByLeader 
	 * @Description: TODO(站长离职) 
	 * @param @param memberId
	 * @param @param compId
	 * @param @param memberName
	 * @param @param memberPhone
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	String leaveOfficeByLeader(Long memberId, Long compId, String memberName,String memberPhone);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryCountInCompInfo 
	 * @Description: TODO(查询此站点下的收派员和后勤人员数量，不包括站长角色) 
	 * @param @param compId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	String queryCountInCompInfo(String compId);
	
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryGSInfo 
	 * @Description: TODO(查询归属详情) 
	 * @param memberId
	 * @param roleId
	 * @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-7
	 * @auth guoqiang.jing
	 */
	public String queryGSInfo(Long memberId,int roleId) ;
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.isIdentifyCompInfo 
	 * @Description: TODO(判断该站点的站点信息是否已认证) 
	 * @param @param compTypeNum
	 * @param @param compId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-28
	 * @auth zhaohu
	 */
	String isIdentifyCompInfo(String compTypeNum, String compId);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.queryCountInShop 
	 * @Description: TODO(查询此代收点下的店员数量，不包括店长角色V3) 
	 * @param @param compId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-31
	 * @auth zhaohu
	 */
	String queryCountInShop(String compId);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.saveCompInfoByCourier 
	 * @Description: TODO(收派员/后勤创建或领取未认证站点并提交站点认证信息--收派员/后勤角色使用V3) 
	 * @param @param compId
	 * @param @param memberId
	 * @param @param netId
	 * @param @param compTypeNum
	 * @param @param compName
	 * @param @param compTelephone
	 * @param @param county
	 * @param @param addressId
	 * @param @param roleType
	 * @param @param memberName
	 * @param @param responsible
	 * @param @param responsibleTelephone
	 * @param @param responsibleNum
	 * @param @param licenseNum
	 * @param @param holdImg
	 * @param @param reverseImg
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-31
	 * @auth zhaohu
	 */
	String saveCompInfoByCourier(String compId, String memberId, String netId,
			String compTypeNum, String compName, String compTelephone,
			String county, String addressId, String roleType,
			String memberName, String responsible, String responsibleTelephone,
			String responsibleNum, String licenseNum, String holdImg,
			String reverseImg,String province,String city);
	/**
	 * @MethodName: net.okdi.apiV2.service.ExpressRegisterService.java.saveCompVerifyInfoV3 
	 * @Description: TODO(提交站点认证信息V3--站长/收派员/后勤通用) 
	 * @param @param loginCompId
	 * @param @param responsible
	 * @param @param responsibleTelephone
	 * @param @param responsibleNum
	 * @param @param holdImg
	 * @param @param reverseImg
	 * @param @param memberId
	 * @param @param licenseNum
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-31
	 * @auth zhaohu
	 */
	String saveCompVerifyInfoV3(String loginCompId, String responsible,
			String responsibleTelephone, String responsibleNum, String holdImg,
			String reverseImg, String memberId, String licenseNum);

	String isRelationByLeaderV3(String compId, String compNumType);

	String submitUseCompInfoV3(Long webCompId, String compName,
			String belongToNetId, String county, String member_id,
			String addressId, String roleType, String memberName,
			String compTypeNum,String province,String city);

	String showSiteInfoV3(String memberId);

	String updateProvinceNotNullCityNull(Long pageSize);

	String joinDefaultOldData();

	String updateAddressIdIsNull();
}
