package net.okdi.apiV4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.apiV2.dao.BasEmployeeAuditMapperV2;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.service.ExpCustomerInfoNewService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/receivePackage")
public class ReceivePackageController extends BaseController{

	private static Logger logger = Logger.getLogger(ReceivePackageController.class);
	@Autowired
	private ReceivePackageService packageService;
	
	@Autowired
	private ExpCustomerInfoNewService customerInfoService;
	@Autowired
	private BasEmployeeAuditMapperV2 basEmployeeAuditMapperV2;
	
	
	@ResponseBody
	@RequestMapping(value = "/test", method = { RequestMethod.POST, RequestMethod.GET })
	public String test(){
		return jsonSuccess(null);
	}

	
	//查询待取包裹列表(任务)
	@ResponseBody
	@RequestMapping(value = "/queryTakePackList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackList(String memberId, String currentPage, String pageSize){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("002", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("003", "pageSize 不能为空!!!");
		}
		Page result = packageService.queryTakePackList(Long.valueOf(memberId), Integer.valueOf(currentPage), Integer.valueOf(pageSize));
		return jsonSuccess(result);
	}

	// 查询取件订单列表===new
	@ResponseBody
	@RequestMapping(value = "/queryNewTakePackList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNewTakePackList(String memberId, String currentPage, String pageSize){
		logger.info("进入取件项目中=====查询待取包裹列表:memberid:"+memberId);
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("002", "currentPage 不能为空!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("003", "pageSize 不能为空!!");
		}
		Page result = packageService.queryNewTakePackList(Long.valueOf(memberId), Integer.valueOf(currentPage), Integer.valueOf(pageSize));
		logger.info("进入取件项目中查询待取包裹===返回的结果是:"+result);
		return jsonSuccess(result);
	}
	
	//查询包裹交寄订单(代收点)
	@ResponseBody
	@RequestMapping(value = "/queryTakeConsigOrderList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeConsigOrderList(String memberId, String netId, String currentPage, String pageSize){
		
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("001", "netId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("003", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("004", "pageSize 不能为空!!!");
		}
		Page page = packageService.queryTakeConsigOrderList(memberId, netId, Integer.valueOf(currentPage), Integer.valueOf(pageSize));
		return jsonSuccess(page);
	}
	
	
	//点击查看待取任务详情
	@ResponseBody
	@RequestMapping(value = "/queryTakeTaskDetailByTaskId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeTaskDetailByTaskId(String memberId, String taskId){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("002", "taskId 不能为空!!!");
		}
		Map<String,Object> map = packageService.queryTakeTaskDetailByTaskId(Long.valueOf(memberId), Long.valueOf(taskId));
		return jsonSuccess(map);
	}
	
	//从任务确认取件
	@ResponseBody
	@RequestMapping(value = "/takeSendPackage", method = { RequestMethod.POST, RequestMethod.GET })
	public String takeSendPackage(String taskId, String memberId, String packageNum, String freightMoney, String sign){
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(freightMoney)){
			return paramsFailure("003", "freightMoney 不能为空!!!");
		}
		if(PubMethod.isEmpty(freightMoney)){
			freightMoney = "0";
		}
		String result = packageService.takeSendPackage(taskId, memberId, Integer.valueOf(packageNum), Double.valueOf(freightMoney), sign);
		return jsonSuccess(result);
	}
	//代收点或者收派员自取件
	@ResponseBody
	@RequestMapping(value = "/takeSendPackageByMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String takeSendPackageByMember(String memberId, String sendName, String sendPhone, String sendAddress, String netId, 
			String packageNum, String freightMoney, String packageJson, String flag){
		logger.info("紧紧传过来的memberId："+memberId+",sendName:"+sendName+",sendPhone:"+sendPhone+",sendAddress:"+sendAddress+",netId:"+netId+
				",packageNum:"+packageNum+",freightMoney:"+freightMoney+",packageJson:"+packageJson+",flag:"+flag);
		System.out.println("=======走的取件新方法＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		/*if(PubMethod.isEmpty(freightMoney)){
			return paramsFailure("002", "freightMoney 不能为空!!!");
		}*/
		if(PubMethod.isEmpty(packageJson)){
			return paramsFailure("003", "packageJson 不能为空!!!");
		}
		if(PubMethod.isEmpty(freightMoney)){
			freightMoney = "0";
		}
		/*if(PubMethod.isEmpty(flag)){
			return paramsFailure("004", "flag 不能为空!!!");
		}*/
		//自取件
		Map<String,Object> map = packageService.takeSendPackageByMember(memberId, sendName, sendPhone, sendAddress, netId, 
				packageNum, Double.valueOf(freightMoney), packageJson, flag);
		if("1".equals(flag)){//微信支付的走流程
			/*if(map.get("data").equals("002")){
				map = new HashMap<String,Object>();
				map.put("data", "002");
				return jsonSuccess(map);
			}*/
			String taskId = map.get("taskId")+"";
			//调取微信支付的
			//Map<String, Object> saomaCreate = customerInfoService.saomaCreate(taskId+"", Double.valueOf(freightMoney));
			String result = packageService.recivesaomaCreate(taskId+"", Double.valueOf(freightMoney));
			System.out.println("=================takeSendPackageByMemberAdd Controller 层====================== 返回微信扫码的二维码 recivesaomaCreate "+result);
			//return jsonSuccess(saomaCreate);
			return result;
		}
		return jsonSuccess(map);
	}
	//查询已取包裹列表
	@ResponseBody
	@RequestMapping(value = "/queryHasTakeList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryHasTakeList(String memberId, String roleId, String netName, String currentPage, String pageSize){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("002", "roleId 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("003", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("004", "pageSize 不能为空!!!");
		}
		/*if(PubMethod.isEmpty(date)){
			return paramsFailure("005", "date 不能为空!!!");
		}*/
		String date="2017-01-17";
		Page page = packageService.queryHasTakeList(memberId, roleId, netName, Integer.valueOf(currentPage), Integer.valueOf(pageSize), date);
		return jsonSuccess(page);
	}
	
	//查询已取包裹详情
	@ResponseBody
	@RequestMapping(value = "/queryHasTakePackDetailByParcelId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryHasTakePackDetailByParcelId(String parcelId){
		
		if(PubMethod.isEmpty(parcelId)){
			return paramsFailure("001", "parcelId 不能为空!!!");
		}
		Map<String,Object> map = packageService.queryHasTakePackDetailByParcelId(parcelId);
		return jsonSuccess(map);
	}
	
	//查询该站点下的其他收派员
	@ResponseBody
	@RequestMapping(value = "/querySiteMemberByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySiteMemberByMemberId(String memberId){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		Map<String,Object> result = packageService.querySiteMemberByMemberId(memberId);
		return jsonSuccess(result);
	}
	/**
	 * 2017- 1-15
	 * @param memberId
	 * @return  查询该站点下的所有收派员
	 */
	  
		@ResponseBody
		@RequestMapping(value = "/queryCompMemberByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
		public String queryCompMemberByMemberId(String memberId){
			
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("001", "memberId 不能为空!!!");
			}
			Map<String,Object> result = packageService.queryCompMemberByMemberId(memberId);
			
			return jsonSuccess(result);
		}
	
	//转单-- 只是把任务转给另外一个收派员
	@ResponseBody
	@RequestMapping(value = "/turnTakePackageToMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String turnTakePackageToMember(String taskId, String memberId, String toMemberId, String toMemberPhone){
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(toMemberId)){
			return paramsFailure("003", "toMemberId 不能为空!!!");
		}
		String result = packageService.turnTakePackageToMember(taskId, memberId, toMemberId, toMemberPhone);
		return jsonSuccess(result);
	}
	
	
	//收派员交寄给站点
	@ResponseBody
	@RequestMapping(value = "/consignationToSiteByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String consignationToSiteByMemberId(String parcelIds){
		
		if(PubMethod.isEmpty(parcelIds)){
			return paramsFailure("002", "parcelIds 不能为空!!!");
		}
		String result = packageService.consignationToSiteByMemberId(parcelIds);
		return jsonSuccess(result);
	}
	
	//代收点查询附近5公里范围内的收派员
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryNearMemberByTude", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNearMemberByTude(String memberId, String netId){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		List<Map> result = packageService.queryNearMemberByTude(memberId,netId);
		return jsonSuccess(result);
	}
	//获取 代收点交寄的信息，代收点名称，电话     ，还有快件信息  (交寄时二维码扫描调用     就是一个页面 ) 2016-12-26
	@ResponseBody
	@RequestMapping(value = "/consignationInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String consignationInfo(String memberId,String parcelIds){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(parcelIds)){
			return paramsFailure("002", "parcelIds 不能为空!!!");
		}
		Map<String, Object> consignationInfo = packageService.consignationInfo(memberId,parcelIds);
		return jsonSuccess(consignationInfo);
	}
	
	//代收点交寄给收派员 代收点的memberId, 收派员的toMemberId, 包裹的parcelIds
	@ResponseBody
	@RequestMapping(value = "/consignationToMemberByParcelIds", method = { RequestMethod.POST, RequestMethod.GET })
	public String consignationToMemberBy(String parcelIds, String toMemberId){
		
		if(PubMethod.isEmpty(parcelIds)){
			return paramsFailure("001", "parcelIds 不能为空!!!");
		}
		if(PubMethod.isEmpty(toMemberId)){
			return paramsFailure("002", "toMemberId 不能为空!!!");
		}
		String result = packageService.consignationToMemberByParcelIds(parcelIds, toMemberId);
		return jsonSuccess(result);
	}
	
	
	//没有快递员,新添加快递员
	@ResponseBody
	@RequestMapping(value = "/saveMemberInfoToNewMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveMemberInfoToNewMemberInfo(String parcelIds, String newMemberName, String compName, String newMemberPhone){
		
		if(PubMethod.isEmpty(parcelIds)){
			return paramsFailure("001", "parcelIds 不能为空!!!");
		}
		if(PubMethod.isEmpty(newMemberName)){
			return paramsFailure("002", "newMemberName 不能为空!!!");
		}
		if(PubMethod.isEmpty(compName)){
			return paramsFailure("003", "compName 不能为空!!!");
		}
		if(PubMethod.isEmpty(newMemberPhone)){
			return paramsFailure("004", "newMemberPhone 不能为空!!!");
		}
		String result = packageService.saveMemberInfoToNewMemberInfo(parcelIds, newMemberName, compName, newMemberPhone);
		return jsonSuccess(result);
	}
	
	//代收点叫快递
	@ResponseBody
	@RequestMapping(value = "/calledCourierToMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String calledCourierToMember(String taskId, String memberId, String parcelIds, String flag){
		
		if(PubMethod.isEmpty(parcelIds)){
			return paramsFailure("001", "parcelIds 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("003", "flag 不能为空!!!");
		}
		String result = packageService.calledCourierToMember(taskId, memberId, parcelIds, flag);
		return jsonSuccess(result);
	}
	
	//收派员点击确认(接单)
	@ResponseBody
	@RequestMapping(value = "/memberConfirmOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String memberConfirmOrder(String taskId, String memberId, String phone){
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		String result = packageService.memberConfirmOrder(taskId, memberId, phone);
		if("001".equals(result)){
			//抢单成功
			return paramsFailure("001", "抢单成功!!!");
		}else if("003".equals(result)){
			//任务取消
			return paramsFailure("003", "该任务取消!!!");
		}else if("004".equals(result)){
			//已经被其他收派员抢到
			return paramsFailure("004", "该订单已被抢!!!");
		}
		return jsonSuccess(result);
	}
	
	//代收点点击确认交寄或者取消交寄 flag 0:取消, 1:确认 memberId:代收点的memberId
	@ResponseBody
	@RequestMapping(value = "/collectPointsConfirmSend", method = { RequestMethod.POST, RequestMethod.GET })
	public String collectPointsConfirmSend(String taskId, String memberId, String flag){
		
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("003", "flag 不能为空!!!");
		}
		String result = packageService.collectPointsConfirmSend(taskId, memberId, flag);
		return jsonSuccess(result);
	}
	
	//取派记录
	@ResponseBody
	@RequestMapping(value = "/queryTakeRecordList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeRecordList(String memberId, String date, String phone){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		List<Map<String,Object>> result = packageService.queryTakeRecordList(memberId, date, phone);
		
		return jsonSuccess(result);
	}
	//取件记录 查询  2016-12-21  增加角色，手机号，运单号查询  
	@ResponseBody
	@RequestMapping(value = "/queryTakeRecordLists", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeRecordLists(String memberId, String date, String phone ,String expWaybillNum,String parcelEndMark,String all,Integer currentPage,Integer pageSize){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(date)){
			return paramsFailure("001", "date 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("002", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("003", "pageSize 不能为空!!!");
		}
		Map<String,Object> result = packageService.queryTakeRecordLists(memberId, date, phone,expWaybillNum,parcelEndMark,all, currentPage, pageSize);
		
		return jsonSuccess(result);
	}
		  //揽收记录 查询  2017-1-4  增加角色，手机号，运单号查询   取消交寄流程    不分角色
		@ResponseBody
		@RequestMapping(value = "/queryTakeRecordListk", method = { RequestMethod.POST, RequestMethod.GET })
		public String queryTakeRecordListk(String memberId, String date, String phone ,String expWaybillNum,String all,Integer currentPage,Integer pageSize, Integer index){
			
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("001", "memberId 不能为空!!!");
			}
			if(PubMethod.isEmpty(date)){
				return paramsFailure("001", "date 不能为空!!!");
			}
			if(PubMethod.isEmpty(currentPage)){
				return paramsFailure("002", "currentPage 不能为空!!!");
			}
			if(PubMethod.isEmpty(pageSize)){
				return paramsFailure("003", "pageSize 不能为空!!!");
			}
			Map<String,Object> result = packageService.queryTakeRecordListk(memberId, date, phone,expWaybillNum,all, currentPage, pageSize, index);
			
			return jsonSuccess(result);
		}
		
	
	 //揽收记录 查询  2017-1-4  增加角色，手机号，运单号查询   取消交寄流程    不分角色
	@ResponseBody
	@RequestMapping(value = "/queryRecRecordList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRecRecordList(String memberId, String date, String phone ,String expWaybillNum,String compId,Integer currentPage,Integer pageSize){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(date)){
			return paramsFailure("001", "date 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("002", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("003", "pageSize 不能为空!!!");
		}
		Map<String,Object> result = packageService.queryRecRecordList(memberId, date, phone,expWaybillNum,compId, currentPage, pageSize);
		
		return jsonSuccess(result);
	}
	
	//揽收记录详情 2017-2-18
	@ResponseBody
	@RequestMapping(value = "/queryTakeRecordDetailed", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeRecordDetailed(String memberId, Long uid){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(uid)){
			return paramsFailure("002", "uid 不能为空!!!");
		}			
			Map<String,Object> result = packageService.queryTakeRecordDetailed(memberId, uid);		
		return jsonSuccess(result);
	}				
	//揽收报表    查询  2016-12-28  queryTakeforms
	@ResponseBody
	@RequestMapping(value = "/queryTakeforms", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeforms(String memberId, String date, String all){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(date)){
			return paramsFailure("001", "date 不能为空!!!");
		}
		Map<String,Object> result = packageService.queryTakeforms(memberId, date,all);		
		return jsonSuccess(result);
	}	
	//查询取件包裹列表详情(从任务过来的) 收派员的memberId
	@ResponseBody
	@RequestMapping(value = "/queryTakePackageList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackageList(String taskId, String memberId){
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		List<Map<String,Object>> result = packageService.queryTakePackageList(taskId, memberId);
		return jsonSuccess(result);
	}
	
	//删除包裹根据parcelId
	@ResponseBody
	@RequestMapping(value = "/deleteParcelByParcelId", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteParcelByParcelId(String taskId, String parcelId){
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(parcelId)){
			return paramsFailure("002", "parcelId 不能为空!!!");
		}
		String result = packageService.deleteParcelByParcelId(taskId, parcelId);
		return jsonSuccess(result);
	}
	
	//删除任务
	@ResponseBody
	@RequestMapping(value = "/deleteTaskByTaskId", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteTaskByTaskId(String taskId){
		
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		String result = packageService.deleteTaskByTaskId(taskId);
		return jsonSuccess(result);
	}
		
	//查询待取包裹列表(任务)
	@ResponseBody
	@RequestMapping(value = "/queryTakePackCount", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackCount(Long memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNum", 0);
		map.put("parcelNum", 0);
		try {
			Long orderOrParcelNum=0l;
			Long parcelNum = 0l;
			BasEmployeeAudit gsAuditInfo = basEmployeeAuditMapperV2.queryGsAuditInfo(memberId.toString());
			if(!PubMethod.isEmpty(gsAuditInfo)){
				//代收点角色 请求代收点数量接口
				if("2".equals(gsAuditInfo.getApplicationRoleType().toString()) || "3".equals(gsAuditInfo.getApplicationRoleType().toString())){
					orderOrParcelNum = packageService.queryTakePackCountDSD(memberId, gsAuditInfo.getAuditComp());//查询代收点的已取包裹数量
					parcelNum = packageService.queryConsigOrderNum(memberId,gsAuditInfo.getAuditComp());//查询交寄订单数量数量
					map.put("orderNum", orderOrParcelNum);
					map.put("parcelNum", parcelNum);
					//站点角色 请求站点数量接口
				}else if ("0".equals(gsAuditInfo.getApplicationRoleType().toString()) || "-1".equals(gsAuditInfo.getApplicationRoleType().toString()) || "1".equals(gsAuditInfo.getApplicationRoleType().toString())) {
					orderOrParcelNum = packageService.queryTakePackCount(memberId);//查询取件订单的数量
					parcelNum = packageService.queryHasParcelNum(memberId);//查询已取包裹的数量
					map.put("orderNum", orderOrParcelNum);
					map.put("parcelNum", parcelNum);
				}
				
			}
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	//查询待取包裹列表(任务)
	@ResponseBody
	@RequestMapping(value = "/sendPush", method = {RequestMethod.POST, RequestMethod.GET })
	public void sendPush(String memberId, String phone) {
		packageService.sendPush(memberId, phone);
	}
	
	//微信调取接口修改微信支付状态
	@ResponseBody
	@RequestMapping(value = "/updateWeChatPayStatus", method = {RequestMethod.POST, RequestMethod.GET })
	public String updateWeChatPayStatus(String payNum, String payStatus) {
		
		if (PubMethod.isEmpty(payNum)) {
			return paramsFailure("001", "orderNum 不能为空!!!");
		}
		if (PubMethod.isEmpty(payStatus)) {
			return paramsFailure("002", "parStatus 不能为空!!!");
		}
		String result = packageService.updateWeChatPayStatus(payNum, Short.valueOf(payStatus));
		return jsonSuccess(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getTaskIdByOrderNum", method = {RequestMethod.POST, RequestMethod.GET })
	public String getTaskIdByOrderNum(String orderNum){
		
		if (PubMethod.isEmpty(orderNum)) {
			return paramsFailure("001", "orderNum 不能为空!!!");
		}
		ParTaskInfo result = packageService.getTaskIdByOrderNum(orderNum);
		return jsonSuccess(result);
	}
	
	//自添加取件生成任务的,返回微信支付的二维码
	@ResponseBody
	@RequestMapping(value = "/takeSendPackageByMemberAdd", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeSendPackageByMemberAdd(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson, String sign){
		
		Map<String,Object> map = packageService.takeSendPackageByMemberAdd(memberId, sendName,sendPhone, sendAddress, netId,
				packageNum, freightMoney, packageJson, sign);
		if(map.get("data").equals("002")){
			map = new HashMap<String,Object>();
			map.put("data", "002");
			return jsonSuccess(map);
		}
		String taskId = map.get("taskId")+"";
		//调取微信支付的
		Map<String, Object> saomaCreate = customerInfoService.saomaCreate(taskId+"", freightMoney);
		System.out.println("=================takeSendPackageByMemberAdd Controller 层====================== 返回微信支付的 saomaCreate "+saomaCreate);
		return jsonSuccess(saomaCreate);
	}
	
	//微信调取接口查询微信支付状态
	@ResponseBody
	@RequestMapping(value = "/findPayStatusByPayNum", method = {RequestMethod.POST, RequestMethod.GET })
	public String findPayStatusByPayNum(String payNum) {
		
		if (PubMethod.isEmpty(payNum)) {
			return paramsFailure("001", "orderNum 不能为空!!!");
		}
		String result = packageService.findPayStatusByPayNum(payNum);
		return result;
	}
	
	
	//取件记录详情
	@ResponseBody
	@RequestMapping(value = "/queryRecordDetailByTaskId", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryRecordDetailByTaskId(String taskId){
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!");
		}
		Map<String,Object> map = packageService.queryRecordDetailByTaskId(taskId);
		return jsonSuccess(map);
	}
	
	//自添加取件(收派员或者代收点)---新取件, 不是现金或者微信支付的   (揽收)
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackageByMember", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, String packageJson){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendName)){
			return paramsFailure("002", "sendName 不能为空!!!");
		}
		if(PubMethod.isEmpty(packageJson)){
			logger.info("这个memberId: "+memberId+" 传过来的packageJson: "+packageJson);
			//return paramsFailure("003", "packageJson 不能为空!!!");
		}
		if(PubMethod.isEmpty(packageNum)){
			packageNum = "0";
		}
		if(PubMethod.isEmpty(sendPhone)){
			return paramsFailure("004", "sendPhone 不能为空!!!");
		}
		String result = packageService.takeReceivePackageByMember(memberId, sendName, sendPhone, sendAddress, netId, packageNum, packageJson);
		return jsonSuccess(result);
	}
	
	//揽收之前运单号查询      订单下包裹是否存在  
	@ResponseBody
	@RequestMapping(value = "/queryPackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackage(String memberId, String expWaybillNum, String netId,String netName,String sendMobile,String code){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}				
		Map<String, Object> result = packageService.queryPackage(memberId,sendMobile,netId,netName,expWaybillNum,code);
		
		return jsonSuccess(result);
	}
	//揽收之前手机号查询      订单下包裹是否存在   
	@ResponseBody
	@RequestMapping(value = "/queryPackageMobile", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackageMobile(String memberId,String netId,String netName,String sendMobile){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}	
		if(PubMethod.isEmpty(sendMobile)){
			return paramsFailure("002", "sendMobile 不能为空!!!");
		}	
		Map<String, Object> result = packageService.queryPackageMobile(memberId,sendMobile,netId,netName);			
		return jsonSuccess(result);
	}
	
	
	//自添加取件(收派员或者代收点)---新取件  (揽收  扫码取件)   2017-2-15
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackageByMembers", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByMembers(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendPhone)){
			return paramsFailure("003", "sendPhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendAddress)){
			return paramsFailure("004", "sendAddress 不能为空!!!");
		}
		if(PubMethod.isEmpty(addresseePhone)){
			return paramsFailure("006", "addresseePhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(addresseeAddress)){
			return paramsFailure("007", "addresseeAddress 不能为空!!!");
		}
		String result = packageService.takeReceivePackageByMembers(memberId, sendName, sendPhone,  sendAddress, deliveryAddress,
				 netId,  addresseeName,  addresseeAddress, addresseePhone, netName, roleId,
				 expWaybillNum , code, comment, parcelId, compId,pacelWeight,parcelType,serviceName);
		
		return jsonSuccess(result);
	}
   //  2017-1-13    揽收   待派 标记备注   先走公司   后走其他
	@ResponseBody
    @RequestMapping("/content")
	public String markContent(Long netId,Integer signType) {
	     if (PubMethod.isEmpty(netId)) {
	         return paramsFailure("001", "invalid params");
	      }
	     if (PubMethod.isEmpty(signType)) {
	         return paramsFailure("002", "invalid params");
	      }
	      try {
	           return jsonSuccess(packageService.findMarkComment(netId, signType));
	      } catch (Exception e) {
	           e.printStackTrace();
	           return jsonFailure();
	       }
	 }
	/**
	 * 服务类型
	 * @param netId
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/serviceType")
	public String serviceType(Long netId) {
	     if (PubMethod.isEmpty(netId)) {
	         return paramsFailure("001", "invalid params");
	      }
	    
	      try {
	           return jsonSuccess(packageService.serviceType(netId));
	      } catch (Exception e) {
	           e.printStackTrace();
	           return jsonFailure();
	       }
	 }
	
	/**
	 * @param taskId
	 * @param memberId
	 * @param packageNum
	 * @param sign
	 * @param packageJson
	 * @return
	 */
	//取件订单添加新的包裹, 收派员
	@ResponseBody
	@RequestMapping(value = "/addParcelToTaskId", method = {RequestMethod.POST, RequestMethod.GET })
	public String addParcelToTaskId(String taskId, String memberId, int packageNum, String sign, String packageJson){
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(packageNum)){
			return paramsFailure("003", "packageNum 不能为空!!!");
		}
		if(PubMethod.isEmpty(packageJson)){
			logger.info("取件订单中该用户没有添加新的包裹...packageJson: "+packageJson);
			//return paramsFailure("004", "packageJson 不能为空!!!");
		}
		String result = packageService.addParcelToTaskId(taskId, memberId, packageNum, sign, packageJson);
		return jsonSuccess(result);
	}
	//查询订单详情---new
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackageDetailInfo", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageDetailInfo(String taskId, Long memberId){
		logger.info("进入取件项目中查询订单详情:参数是:taskId"+taskId+",memberId:"+memberId);
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("001", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		Map<String,Object> result = packageService.takeReceivePackageDetailInfo(taskId, memberId);
		logger.info("进入取件项目中查询订单详情:返回的结果是:"+result);
		return jsonSuccess(result);
	}
	
	
	//手机端传过来的唯一code(弃用)
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackageByOnlyCode", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByOnlyCode(String parcelIds, Long memberId){
		if(PubMethod.isEmpty(parcelIds)){
			return paramsFailure("001", "parcelIds 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		String result = packageService.takeReceivePackageByOnlyCode(parcelIds, memberId);
		return jsonSuccess(result);
	}
	
		/**查询取件记录
		 * @param date
		 * @param memberId
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/queryTakePackageCount", method = {RequestMethod.POST, RequestMethod.GET })
		public String queryTakePackageCount(String date, Long memberId){
//			if(PubMethod.isEmpty(date)){
//				return paramsFailure("001", "parcelIds 不能为空!!!");
//			}
//			if(PubMethod.isEmpty(memberId)){
//				return paramsFailure("002", "memberId 不能为空!!!");
//			}
			logger.info("进入receriveapi查找,date:"+date+",memberId:"+memberId);
			Long result = packageService.queryTakePackageCount(date, memberId);
			System.out.println("==============="+result);
			return jsonSuccess(result);
		}

	/** 新 -- 添加取件订单
	 * t添加新取件订单
	 * @param memberId
	 * @param senderName
	 * @param senderPhone
	 * @param addressDetail
	 * @param packNum
	 * @param tagContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addNewReceiveOrder", method = {RequestMethod.POST, RequestMethod.GET })
    public String addNewReceiveOrder(String memberId, String senderName, String senderPhone, String addressDetail, Integer packNum, String tagContent){
        if(PubMethod.isEmpty(memberId)){
            return paramsFailure("001", "memberId 不能为空!!!");
        }
        if(PubMethod.isEmpty(senderPhone)){
            return paramsFailure("002", "senderPhone 不能为空!!!");
        }
        if(PubMethod.isEmpty(addressDetail)){
            return paramsFailure("003", "addressDetail 不能为空!!!");
        }
        return jsonSuccess(packageService.addNewReceiveOrder(memberId, senderName, senderPhone, addressDetail, packNum, tagContent));
    }

	/**
	 * 新 -- 标记
	 * @param memberId
	 * @param taskId
	 * @param tagContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tagOrder", method = {RequestMethod.POST, RequestMethod.GET })
	public String tagOrder(String memberId, Long taskId, String tagContent){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("002", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(tagContent)){
			return paramsFailure("003", "tagContent 不能为空!!!");
		}
		return packageService.tagOrder(memberId, taskId, tagContent);
	}


    /**
     * 取消订单-----new
     * @param memberId 快递员memberId
     * @param taskId 任务id
     * @param ancelReason 取消原因
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/operationOrderStatus", method = {RequestMethod.POST, RequestMethod.GET })
	public String operationOrderStatus(String memberId, Long taskId, String ancelReason){

	    if(PubMethod.isEmpty(memberId)){
	        return paramsFailure("001" , " memberId 不能为空!!!");
        }
        if(PubMethod.isEmpty(taskId)){
	        return paramsFailure("002" , " taskId 不能为空!!!");
        }
        String result = packageService.operationOrderStatus(memberId, taskId, ancelReason);
		return jsonSuccess(result);
	}
	
	/**
     * 新 -- 结束订单 
     * @param memberId 快递员memberId
     * @param taskId 任务id
     * @param parcelIds 包裹id
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/finishOrder", method = {RequestMethod.POST, RequestMethod.GET })
	public String finishOrder(String memberId, Long taskId, String parcelIds, String sendName,String deliveryAddress,
			String sendPhone, String sendAddress, String netId,String roleId,
			String addresseeName, String addresseePhone,String addresseeAddress, 
			String expWaybillNum,String marker,String bourn,String code,String comment,String netName){

	    if(PubMethod.isEmpty(memberId)){
	        return paramsFailure("001" , " memberId 不能为空!!!");
        }
        if(PubMethod.isEmpty(taskId)){
	        return paramsFailure("002" , " taskId 不能为空!!!");
        }
        String result = packageService.finishOrder(memberId, taskId, parcelIds, sendName,deliveryAddress,
				 sendPhone,  sendAddress, netId,addresseeName,  addresseePhone, addresseeAddress, 
				expWaybillNum,marker, bourn, code, comment,netName,roleId);
		return jsonSuccess(result);
	}
    /**
     * 确认发运
     * @param uids 包裹id "123123-123123"
     */
    @ResponseBody
    @RequestMapping(value = "/confirmDelivery", method = { RequestMethod.POST, RequestMethod.GET })
    public String confirmDelivery(String uids){
        logger.info("确认发运confirmDelivery()>>uids: " + uids);
        if(PubMethod.isEmpty(uids)){
            return paramsFailure("001", "uids 不能为空!!!");
        }
        try {
            packageService.confirmDelivery(uids);
        } catch (Exception e) {
            return jsonFailure(e);
        }
        return jsonSuccess();
    }
    
    /**checkParcelDetail订单中查询包裹详情
     * @param parcelId
     * @param contactAddress
     * @param netId
     * @param netName
     * @param contactMobile
     * @param contactName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkParcelDetail", method = { RequestMethod.POST, RequestMethod.GET })
    public String checkParcelDetail(Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName){
        logger.info("订单中查询包裹详情:parcelId: " + parcelId+",contactAddress:"+contactAddress+",netId:"+netId+",netName:"+netName+",contactMobile:"+contactMobile+",contactName:"+contactName);
        if(PubMethod.isEmpty(parcelId)){
            return paramsFailure("001", "parcelId 不能为空!!!");
        }
        try {
        	Map<String, Object> result = packageService.checkParcelDetail(parcelId, contactAddress, netId, netName, contactMobile, contactName);
            logger.info("查询订单中包裹详情返回的结果:"+result);
        	return jsonSuccess(result);
        } catch (Exception e) {
            return jsonFailure(e);
        }
    }
    /**订单中,保存订单中包裹详情的信息
     * @param mark//标记的备注
     * @param parcelId//包裹id
     * @param contactAddress 发件人地址
     * @param netId 网络id
     * @param netName 网络名称
     * @param contactMobile 发件人手机号
     * @param contactName 发件人姓名
     * @param expWaybillNum 运单号
     * @param code 取件标号
     * @param addresseeName 收件人的姓名
     * @param addresseeAddress 收件人地址
     * @param addresseePhone 收件人的手机
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveParcelInfo", method = { RequestMethod.POST, RequestMethod.GET })
    public String saveParcelInfo(String mark,Long parcelId,String contactAddress,Long netId,String netName,
    		String contactMobile,String contactName,String expWaybillNum,String code,String addresseeName,
    		String addresseeAddress,String addresseePhone){
        logger.info("订单中查询包裹详情:parcelId: " + parcelId+",contactAddress:"+contactAddress+",netId:"+netId+",netName:"+netName+",contactMobile:"+contactMobile+",contactName:"+contactName+",expWaybillNum:"+expWaybillNum+",code:"+code+",addresseeName:"+addresseeName+",addresseeAddress:"+addresseeAddress+",addresseePhone:"+addresseePhone);
        if(PubMethod.isEmpty(parcelId)){
            return paramsFailure("001", "parcelId 不能为空!!!");
        }
        try {
        	packageService.saveParcelInfo(mark, parcelId, contactAddress, netId, netName, contactMobile, contactName, expWaybillNum, code, addresseeName, addresseeAddress, addresseePhone);
        	logger.info("订单中保存包裹详细信息返回的结果:"+001);
        	return jsonSuccess("001");//保存成功
        } catch (Exception e) {
        	logger.info("订单中保存包裹详细信息返回的结果:"+e);
            return jsonFailure(e);//保存失败
        }
        
    }
    
    /**确认取件
     * @param parcelId   1111-1111
     * @param memberId
     * @param taskId
     * @param flag flag=1时，说明是订单列表直接签收
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/confirmTakeParcel", method = { RequestMethod.POST, RequestMethod.GET })
    public String confirmTakeParcel(String uids,Long memberId,String taskId,String flag){
        logger.info("订单中查询包裹详情:parcelId: " + uids);
    	String result=packageService.confirmTakeParcel(uids,memberId,taskId,flag);
    	logger.info("订单中保存包裹详细信息返回的结果:"+result);
    	return result;
        
    }
    /**运单号唯一验证
     * @param expWaybillNum   运单号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkPidIsUnique", method = { RequestMethod.POST, RequestMethod.GET })
    public String checkPidIsUnique(String expWaybillNum){
    	logger.info("订单中查询运单号是否唯一:expWaybillNum: " + expWaybillNum);
    	String result=packageService.checkPidIsUnique(expWaybillNum);
    	logger.info("订单中查询运单号是否唯一返回的结果:"+result);
    	return result;
    	
    }
}
