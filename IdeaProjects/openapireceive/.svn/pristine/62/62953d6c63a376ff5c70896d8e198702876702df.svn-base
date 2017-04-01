package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendTaskService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.apiV2.service.ExpressRegisterService;
import net.okdi.apiV2.vo.VO_BasCompInfo;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 快递员注册controller
 * @Project Name:springmvc 
 * @Package net.okdi.apiV2.controller  
 * @Title: ExpressRegisterController.java 
 * @ClassName: ExpressRegisterController <br/> 
 * @date: 2015-12-2 下午3:05:22 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/expressRegister")
public class ExpressRegisterController extends BaseController{
	
	@Autowired
	private ExpressRegisterService expressRegisterService;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	SendTaskService sendTaskService;
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.queryAuditStatus 
	 * @Description: TODO(获取快递员的身份和快递认证状态) 
	 * @param @param memberId 快递员id
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-2
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAuditStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAuditStatus(String memberId){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryAuditStatus.001", "memberId不能为空");
			}
			HashMap<String, Object> resultMap = expressRegisterService.queryAuditStatus(memberId);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.queryCompInfoByFirstLetter 
	 * @Description: TODO(获取站点信息列表-按首字母排序) 
	 * @param @param netId	快递网络id
	 * @param @param addressName 二级地址名称
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-2
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByFirstLetter", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByFirstLetter(String netId,String addressName){
		try {
			if (PubMethod.isEmpty(addressName)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCompInfoByFirstLetter.001", "addressName不能为空");
			}
			Map<String, List<VO_BasCompInfo>> resultMap = expressRegisterService.queryCompInfoByFirstLetter(netId,addressName);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.isRelationByLeader 
	 * @Description: TODO(判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色) 
	 * @param @param compId	所选站点id
	 * @param @param compNumType	站点类型：1003网络抓取 1006已被领取新建的站点 1050营业分部
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-3
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/isRelationByLeader", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRelationByLeader(String compId,String compNumType){
		try {
			HashMap<String, Object> resultMap = expressRegisterService.isRelationByLeader(compId,compNumType);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.isRelationByLeader 
	 * @Description: TODO(判断选择的加盟站点/网络抓取站点/营业分部是否被领用V3--站长角色) 
	 * @param @param compId	所选站点id
	 * @param @param compNumType	站点类型：1003网络抓取 1006已被领取新建的站点 1050营业分部
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-3
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/isRelationByLeaderV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRelationByLeaderV3(String compId,String compNumType){
		try {
			HashMap<String, Object> resultMap = expressRegisterService.isRelationByLeaderV3(compId,compNumType);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.submitUseCompInfo 
	 * @Description: TODO(站长领用网络抓取的站点) 
	 * @param @param webCompId
	 * @param @param compName
	 * @param @param belongToNetId
	 * @param @param county
	 * @param @param member_id
	 * @param @param roleType
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-3
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/submitUseCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitUseCompInfo(Long webCompId, String compName, String belongToNetId, String county,String member_id,String addressId,String roleType,String memberName){
		try {
			if (PubMethod.isEmpty(webCompId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfo.001", "webCompId不能为空");
			}
			if (PubMethod.isEmpty(member_id)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfo.002", "member_id不能为空");
			}
			if (PubMethod.isEmpty(belongToNetId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfo.003", "belongToNetId不能为空");
			}
			if (PubMethod.isEmpty(addressId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfo.004", "addressId不能为空");
			}
			if (PubMethod.isEmpty(roleType)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfo.005", "roleType不能为空");
			}
			HashMap<String, Object> resultMap = expressRegisterService.submitUseCompInfo(webCompId,compName,belongToNetId,county,member_id,addressId,roleType,memberName);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.isRepeatCompInfoName 
	 * @Description: TODO(查询在同一个快递网络下是否有重名的站点) 
	 * @param @param netId
	 * @param @param compName
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/isRepeatCompInfoName", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRepeatCompInfoName(String netId,String compName){
		try {
			if (PubMethod.isEmpty(netId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRepeatCompInfoName.001", "netId不能为空");
			}
			if (PubMethod.isEmpty(compName)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRepeatCompInfoName.002", "compName不能为空");
			}
			HashMap<String, Object> resultMap = expressRegisterService.isRepeatCompInfoName(netId,compName);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.saveNewCompInfo 
	 * @Description: TODO(站长创建新站点/营业分部) 
	 * @param @param memberId
	 * @param @param netId
	 * @param @param compTypeNum
	 * @param @param compName
	 * @param @param compTelephone
	 * @param @param county
	 * @param @param descriptionMsg 创建营业分部时，前面选择的站点名称
	 * @param @param roleType 
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/saveNewCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveNewCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,String county,String addressId,String descriptionMsg,String roleType,String memberName){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.001", "memberId不能为空");
			}
			if (PubMethod.isEmpty(netId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.002", "netId不能为空");
			}
			if (PubMethod.isEmpty(compTypeNum)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.003", "compTypeNum不能为空");
			}
			if (PubMethod.isEmpty(addressId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.004", "addressId不能为空");
			}
			if (PubMethod.isEmpty(roleType)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.005", "roleType不能为空");
			}
			HashMap<String, Object> resultMap = expressRegisterService.saveNewCompInfo(memberId,netId,compTypeNum,compName,compTelephone,county,addressId,descriptionMsg,roleType,memberName);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.java.registerCourier 
	 * @Description: 注册收派员或者后勤角色
	 * @param memberId  快递员membrId,ucenter自动生成,不能null 
	 * @param memberName  快递员实名 不能null
	 * @param netId      不能null
	 * @param compId     可null 
	 * @param compTypeNum  公司分类代码，可null  1003:网络抓取,1006:加盟公司,1008:加盟公司站点,1030:快递代理点 1040快递代收点认领或者创建，1050:营业分部'
	 * @param compName   不能null
	 * @param applicationRoleType  申请角色类型  0 收派员 -1 后勤  不能null
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-3
	 * @auth guoqiang.jing
	 */
	@ResponseBody
	@RequestMapping(value = "/registerCourier", method = { RequestMethod.POST, RequestMethod.GET })
	public String registerCourier(Long memberId,String memberName,Long netId,String compId,String compTypeNum,String compName,String applicationRoleType){
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("net.okdi.apiV2.controller.ExpressRegisterController.java.registerCourier","memberId参数异常");
		}
		if(PubMethod.isEmpty(netId)){
			throw new ServiceException("net.okdi.apiV2.controller.ExpressRegisterController.java.registerCourier","netId参数异常");
		}
		if(PubMethod.isEmpty(compName)){
			throw new ServiceException("net.okdi.apiV2.controller.ExpressRegisterController.java.registerCourier","compName参数异常");
		}
		if(PubMethod.isEmpty(applicationRoleType)){
			throw new ServiceException("net.okdi.apiV2.controller.ExpressRegisterController.java.registerCourier","applicationRoleType参数异常");
		}
		
		try {
			HashMap<String, Object> resultMap = expressRegisterService.registerCourier(memberId,memberName, netId, compId,compTypeNum, compName, applicationRoleType);
		    return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		    return jsonFailure();
		}
	}
	
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryRepeatOrder 
	 * @Description: 查询单号是否被重复认证
	 * @param orderArr   订单数据
	 * @param netId      网络id
	 * @param phone      本人电话，除本人外是否被其他人认证过
	 * @return   
	 * @return String  返回值类型
	 *      {"data":["0015479822","0015479822"], "success":true}
	 * @throws 
	 * @date 2015-12-4
	 * @auth guoqiang.jing
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRepeatOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRepeatOrder(String orderArr,Long netId,String phone){
		if (PubMethod.isEmpty(netId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressRegisterController.queryRepeatOrder.001", "netId不能为空");
		}
		if (PubMethod.isEmpty(phone)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressRegisterController.queryRepeatOrder.002", "phone不能为空");
		}
		try {
			List list = expressRegisterService.queryRepeatOrder(orderArr,String.valueOf(netId),phone);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.queryCountInCompInfo 
	 * @Description: TODO( 查询此站点下的收派员和后勤人员数量，不包括站长角色) 
	 * @param @param compId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCountInCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCountInCompInfo(String compId){
		try {
			if (PubMethod.isEmpty(compId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCountInCompInfo.001", "compId不能为空");
			}
			return jsonSuccess(this.expressRegisterService.queryCountInCompInfo(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.queryCountInShop 
	 * @Description: TODO( 查询此代收点下的店员数量，不包括店长角色V3) 
	 * @param @param compId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-30
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCountInShop", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCountInShop(String compId){
		try {
			if (PubMethod.isEmpty(compId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCountInShop.001", "compId不能为空");
			}
			return jsonSuccess(this.expressRegisterService.queryCountInShop(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.leaveOfficeByLeader 
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
	@ResponseBody
	@RequestMapping(value = "/leaveOfficeByLeader", method = { RequestMethod.POST, RequestMethod.GET })
	public String leaveOfficeByLeader(Long memberId, Long compId, String memberName, String memberPhone){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.leaveOfficeByLeader.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.leaveOfficeByLeader.002", "compId不能为空");
		}
		if (PubMethod.isEmpty(memberPhone)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.leaveOfficeByLeader.003", "memberPhone不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		ehcacheService.removeAll("employeeCache");
		
		int isFinish = this.takeTaskService.queryTaskUnFinished(null, memberId);
		if (isFinish == 0) {
			List<Map<String,Object>> list = this.parcelInfoService.queryParcelListBySendMemberId(memberId,1,10);
			if(!PubMethod.isEmpty(list)){
				map.put("isFinish",0);
				map.put("isSendFinish", 1);
				map.put("isTaskFinish", 1);
				return jsonSuccess(map);
			}
			String a  = this.sendTaskService.querySendTaskList(memberId);
			JSONObject obj = JSON.parseObject(a);
			JSONObject jbj = obj.getJSONObject("data");
			if(jbj.getJSONArray("resultlist").size()>0){
				map.put("isFinish",0);
				map.put("isSendFinish", 0);
				map.put("isTaskFinish", 1);
				return jsonSuccess(map);
			}
			this.expressRegisterService.leaveOfficeByLeader(memberId, compId, memberName, memberPhone);
			map.put("isSendFinish", 0);
			map.put("isTaskFinish", 0);
			map.put("isFinish", isFinish);
			return jsonSuccess(map);
		} else {
			map.put("isFinish", isFinish);
			return jsonSuccess(map);
		}
	}
	
	
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.queryGSInfo 
	 * @Description: TODO(查询归属详情) 
	 * @param memberId
	 * @param roleId
	 * @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-7
	 * @auth guoqiang.jing
	 */
	@ResponseBody
	@RequestMapping(value = "/queryGSInfo", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryGSInfo(Long memberId,int roleId) {
		
		try {
			return jsonSuccess(expressRegisterService.queryGSInfo(memberId,roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.isIdentifyCompInfo 
	 * @Description: TODO(判断该站点是否已认证---站长/收派员/后勤角色使用V3) 
	 * @param @param compTypeNum	站点类型：1003网络抓取站点 1006加盟站点 1050营业分部
	 * @param @param compId	站点id
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-28
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/isIdentifyCompInfo", method = { RequestMethod.POST,RequestMethod.GET })
	public String isIdentifyCompInfo(String compTypeNum,String compId) {
		try {
			HashMap<String, Object> resultMap = expressRegisterService.isIdentifyCompInfo(compTypeNum,compId);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.saveCompInfoByCourier 
	 * @Description: TODO(收派员/后勤创建或领取未认证站点并提交站点认证信息----收派员/后勤角色使用V3) 
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
	 * @param @param descriptionMsg
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-12-30
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompInfoByCourier", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompInfoByCourier(Long compId,Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,String county,String addressId,String roleType,String memberName,
			String responsible, String responsibleTelephone, String responsibleNum, String licenseNum,
			String holdImg, String reverseImg){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(netId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.002", "netId不能为空");
		}
		if (PubMethod.isEmpty(addressId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.004", "addressId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.005", "roleType不能为空");
		}
		if (PubMethod.isEmpty(compName)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.006", "compName不能为空");
		}
		if (PubMethod.isEmpty(county)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.008", "county不能为空");
		}
		if (PubMethod.isEmpty(memberName)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.009", "memberName不能为空");
		}
		if (PubMethod.isEmpty(holdImg)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.010", "holdImg不能为空");
		}
		if (PubMethod.isEmpty(reverseImg)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.011", "reverseImg不能为空");
		}
		try {
			HashMap<String, Object> resultMap = this.expressRegisterService.saveCompInfoByCourier(compId,memberId,netId,compTypeNum,compName,compTelephone,county,addressId,roleType,memberName,
					responsible,responsibleTelephone,responsibleNum,licenseNum,holdImg,reverseImg);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.saveCompVerifyInfoV3 
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
	@ResponseBody
	@RequestMapping(value = "/saveCompVerifyInfoV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompVerifyInfoV3(Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
			String holdImg, String reverseImg,Long memberId,String licenseNum) {
		try {
			return jsonSuccess(this.expressRegisterService.saveCompVerifyInfoV3(loginCompId, responsible, responsibleTelephone, responsibleNum,holdImg, reverseImg,memberId, licenseNum));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.submitUseCompInfoV3 
	 * @Description: TODO(站长领用网络抓取的站点V3--站长角色使用) 
	 * @param @param webCompId
	 * @param @param compName
	 * @param @param belongToNetId
	 * @param @param county
	 * @param @param member_id
	 * @param @param roleType
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016年1月5日17:58:38
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/submitUseCompInfoV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitUseCompInfoV3(Long webCompId, String compName, String belongToNetId, String county,String member_id,String addressId,String roleType,String memberName,String compTypeNum){
		try {
			if (PubMethod.isEmpty(webCompId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.001", "webCompId不能为空");
			}
			if (PubMethod.isEmpty(member_id)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.002", "member_id不能为空");
			}
			if (PubMethod.isEmpty(belongToNetId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.003", "belongToNetId不能为空");
			}
			if (PubMethod.isEmpty(addressId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.004", "addressId不能为空");
			}
			if (PubMethod.isEmpty(roleType)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.005", "roleType不能为空");
			}
			HashMap<String, Object> resultMap = expressRegisterService.submitUseCompInfoV3(webCompId,compName,belongToNetId,county,member_id,addressId,roleType,memberName,compTypeNum);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.showSiteInfoV3 
	 * @Description: TODO(查看站点认证信息V3) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-1-9
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/showSiteInfoV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String showSiteInfoV3(String memberId){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.showSiteInfoV3.001", "memberId不能为空");
			}
			HashMap<String, Object> resultMap = expressRegisterService.showSiteInfoV3(memberId);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.sendSmsByRecharge 
	 * @Description: TODO(充值通讯费赠送奖励推送) 
	 * @param @param money
	 * @param @param mobile   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-3-28
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/sendSmsByRecharge", method = { RequestMethod.POST, RequestMethod.GET })
	public void sendSmsByRecharge(Double money,Long memberId){
		expressRegisterService.sendSmsByRecharge(money,memberId);
	}
	
	
	/**
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.getAccountIdByMemberId(String)
	 * @Description: TODO(根据memberId查询accountId) 
	 * @param @param memberId
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-5
	 * @auth zhengjiong
	 */
	@ResponseBody
	@RequestMapping(value = "/getAccountIdByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String getAccountIdByMemberId(String memberId){
		
		String accountId = expressRegisterService.getAccountIdByMemberId(memberId);
		return accountId;
	}
	
	
}
