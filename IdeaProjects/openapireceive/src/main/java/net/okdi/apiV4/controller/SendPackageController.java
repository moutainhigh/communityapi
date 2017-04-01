package net.okdi.apiV4.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import net.okdi.apiV4.service.SendPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 派件controller
 * @Project Name:springmvc 
 * @Package net.okdi.apiV4.controller  
 * @Title: SendPackageController.java 
 * @ClassName: SendPackageController <br/> 
 * @date: 2016-4-14 下午6:18:44 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/sendPackage")
public class SendPackageController extends BaseController {

	@Autowired
	private SendPackageService sendPackageService;//注入service
	
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.queryParcelToBeTakenList 
	 * @Description: TODO(查询待提包裹列表) 
	 * @param @param memberId
	 * @param @param currentPage 当前页码
	 * @param @param pageSize	每页显示数量
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-16
	 * @auth zhaohu
	 */
	/**
	 {
    "data": {
        "currentPage": 1,
        "hasFirst": false,
        "hasLast": false,
        "hasNext": false,
        "hasPre": false,
        "items": [
            {
                "addresseeMobile": "18888888888",
                "createTime": "2016-04-15 15:43",
                "expWaybillNum": "6947751402018",
                "parId": 207518370676736
            },
            {
                "addresseeMobile": "15366685555",
                "createTime": "2016-04-14 19:46",
                "expWaybillNum": "4770584120062",
                "parId": 207367778385920
            }
        ],
        "offset": 0,
        "pageCount": 1,
        "pageSize": 10,
        "rows": [],
        "total": 2
    },
    "success": true
}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryParcelToBeTakenList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryParcelToBeTakenList(Long memberId,Integer currentPage,Integer pageSize){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelToBeTakenList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelToBeTakenList.002", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelToBeTakenList.003", "pageSize不能为空!");
		}
		try {
			Page page = this.sendPackageService.queryParcelToBeTakenList(memberId,currentPage,pageSize);
			return jsonSuccess(page);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.queryParcelDetail 
	 * @Description: TODO(待提包裹信息详情) 
	 * @param @param parId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-16
	 * @auth zhaohu
	 */
	/**
	 {
    "data": {
        "addressee_mobile": "18888888888",
        "addressee_name": "臧天朔",
        "cod_amount": 1,
        "exp_waybill_num": "6947751402018",
        "freight": 1,
        "net_name": "中通快递",
        "parId": 207518370676736
    },
    "success": true
}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryParcelDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryParcelDetail(String parId){
		if(PubMethod.isEmpty(parId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelDetail.001", "parId不能为空");
		}
		try {
			HashMap<String, Object> resultMap = this.sendPackageService.queryParcelDetail(parId);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.changeSendPerson 
	 * @Description: TODO(待提包裹转单-批量) 
	 * @param @param memberId 转给谁
	 * @param @param parIds 包裹ids
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-18
	 * @auth zhaohu
	 */
	/**
	 {
    "data": {
        "isPickUp": "0"
    },
    "success": true
}
	 */
	@ResponseBody
	@RequestMapping(value = "/changeSendPerson", method = { RequestMethod.POST, RequestMethod.GET })
	public String changeSendPerson(String newMemberId,String oldMemberId,String parIds){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeSendPerson.001", "parIds不能为空");
		}
		if(PubMethod.isEmpty(newMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeSendPerson.002", "newMemberId不能为空");
		}
		if(PubMethod.isEmpty(oldMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeSendPerson.003", "oldMemberId不能为空");
		}
		try {
			this.sendPackageService.changeSendPerson(newMemberId,oldMemberId,parIds);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.createSendTaskOrUpdate 
	 * @Description: TODO(提货-批量) 
	 * @param @param parIds 包裹id，逗号隔开
	 * @param @param memberId 提货人id
	 * @param @param memberPhone 提货人手机号
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-20
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/pickUpParcel", method = { RequestMethod.POST, RequestMethod.GET })
	public String pickUpParcel(String parIds,Long memberId,String memberPhone){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.pickUpParcel.001", "parIds不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.pickUpParcel.002", "memberId不能为空");
		}
		try {
			this.sendPackageService.pickUpParcel(parIds,memberId,memberPhone);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.querySendTaskList 
	 * @Description: TODO(查询待派任务列表) 
	 * @param @param memberId 快递员id
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-20
	 * @auth zhaohu
	 */
	/**
	{
    "data": {
        "currentPage": 1,
        "hasFirst": false,
        "hasLast": false,
        "hasNext": false,
        "hasPre": false,
        "items": [
            {
                "address": "浙江台州市温岭市石桥头镇",
                "callBackStatus": "",
                "mobile": "13552751530",
                "parcelId": 143912834768896,
                "parcelNum": "",
                "sendSmsStatus": "",
                "sendSmsType": "",
                "taskId": 143912836866048
            }
        ],
        "offset": 0,
        "pageCount": 1,
        "pageSize": 10,
        "rows": [],
        "total": 4
    },
    "success": true
}
}
	 */
	@ResponseBody
	@RequestMapping(value = "/querySendTaskList", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendTaskList(Long memberId,Integer currentPage,Integer pageSize,String mobilePhone){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendTaskList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendTaskList.002", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendTaskList.003", "pageSize不能为空");
		}
		try {
			Page page = this.sendPackageService.querySendTaskList(memberId,currentPage,pageSize,mobilePhone);
			return jsonSuccess(page);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 查询附近5公里代收点
	 * @param longitude
	 * @param latitude
	 * @param agentType	'代收点类型: 1 社区小店  2 公司前台  3 收发室  4 小区物业',
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-20
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNearCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNearCompInfos(Double longitude,Double latitude,Short agentType){
		if(PubMethod.isEmpty(longitude)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearCompInfos.01", "longitude不能为空");
		}
		if(PubMethod.isEmpty(latitude)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearCompInfos.01", "latitude不能为空");
		}
		if(PubMethod.isEmpty(agentType)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearCompInfos.01", "agentType不能为空");
		}
		try {
			return jsonSuccess(this.sendPackageService.queryNearCompInfo( longitude, latitude,agentType));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	/**
	 * 
	 * @Description: 转代收--添加一个代收点
	 * @param compName
	 * @param compMobile
	 * @param actorMemberId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-20
	 */
	@ResponseBody
	@RequestMapping(value = "/saveNearComp", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveNearComp(String compName,String compMobile,Long actorMemberId){
		
		try {
			this.sendPackageService.saveNearComp(compName, compMobile,actorMemberId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
		/**
		 * 
		 * @Description: 转代收--根据代收点名称和手机号判断代收点是否存在
		 * @param compName
		 * @param compMobile
		 * @param actorMemberId
		 * @return String
		 * @throws
		 * @author jianxin.ma
		 * @date 2016-4-20
		 */
		@ResponseBody
		@RequestMapping(value = "/ifCompExist", method = { RequestMethod.POST, RequestMethod.GET})
		public String ifCompExist(String compName,String compMobile){
			if(PubMethod.isEmpty(compName)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.ifCompExist.01", "compName不能为空");
			}
			if(PubMethod.isEmpty(compMobile)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.ifCompExist.02", "compMobile不能为空");
			}
			try {
				return jsonSuccess(this.sendPackageService.ifCompExist(compName, compMobile));
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.updateParcelInfo 
	 * @Description: TODO(编辑派件包裹信息) 
	 * @param @param parId
	 * @param @param netId
	 * @param @param expNum
	 * @param @param mobile
	 * @param @param address
	 * @param @param codAmount 应收货款
	 * @param @param freight 应收运费
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-20
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/updateParcelInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateParcelInfo(String parId,String netId,String expNum,String mobile,String address,String codAmount,String freight,String name){
		if(PubMethod.isEmpty(parId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelInfo.001", "parId不能为空");
		}
		if(PubMethod.isEmpty(mobile)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelInfo.002", "mobile不能为空");
		}
		try {
			this.sendPackageService.updateParcelInfo(parId, netId, expNum, mobile, address, codAmount, freight, name);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.normalSignParcel 
	 * @Description: TODO(派件正常签收) 
	 * @param @param taskId
	 * @param @param parcelId
	 * @param @param totalCodAmount
	 * @param @param totalFreight
	 * @param @param memberId
	 * @param @param mobile
	 * @param @param name
	 * @param @param cityId
	 * @param @param cityName
	 * @param @param address
	 * @param @param sex
	 * @param @param signType 1.签收并保存客户信息 2.签收
	 * @param @param signFlag 正常签收-传空值；有派无签-传1
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-26
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/normalSignParcel", method = { RequestMethod.POST, RequestMethod.GET })
	public String normalSignParcel(Long taskId,String parcelId,Double totalCodAmount,Double totalFreight,Long memberId,
			String mobile,String name,String address,String sex,String signType,String signFlag){
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.001", "taskId不能为空");
		}
		if(PubMethod.isEmpty(parcelId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.002", "parcelId不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.003", "memberId不能为空");
		}
		if(PubMethod.isEmpty(signType)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.004", "signType不能为空");
		}
		try {
			this.sendPackageService.normalSignParcel(taskId, parcelId, totalCodAmount, totalFreight, memberId, mobile, name, address, sex,signType,signFlag);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.normalSignParcelBatch 
	 * @Description: TODO(派件正常签收--批量) 
	 * @param @param taskIds
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-25
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/normalSignParcelBatch", method = { RequestMethod.POST, RequestMethod.GET })
	public String normalSignParcelBatch(String taskIds,String signType){
		if(PubMethod.isEmpty(taskIds)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcelBatch.001", "taskIds不能为空");
		}
		try {
			this.sendPackageService.normalSignParcelBatch(taskIds,signType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.exceptionSignParcel 
	 * @Description: TODO(派件异常签收) 
	 * @param @param taskId
	 * @param @param parcelId
	 * @param @param memberId
	 * @param @param exceptionType
	 * @param @param textValue
	 * @param @param compId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-25
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/exceptionSignParcel", method = { RequestMethod.POST, RequestMethod.GET })
	public String exceptionSignParcel(String taskId,String parcelId,String memberId,String exceptionType,String textValue,String compId){
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.001", "taskId不能为空");
		}
		if(PubMethod.isEmpty(parcelId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.002", "parcelId不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.003", "memberId不能为空");
		}
		if(PubMethod.isEmpty(exceptionType)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.004", "exceptionType不能为空");
		}
		try {
			this.sendPackageService.exceptionSignParcel( taskId, parcelId, memberId, exceptionType, textValue, compId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 派件--转代收(批量)
	 * @param newMemberId
	 * @param oldMemberId
	 * @param parIds
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-22
	 */
	@ResponseBody
	@RequestMapping(value = "/changeAccept", method = { RequestMethod.POST, RequestMethod.GET })
	public String changeAccept(Long newMemberId,Long newCompId,Long newNetId,Long oldMemberId,String parIds,Short flag){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept.001", "parIds不能为空");
		}
		if(PubMethod.isEmpty(oldMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept.003", "oldMemberId不能为空");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept.003", "flag不能为空");
		}
		try {
			this.sendPackageService.changeAccept(newMemberId, newCompId, newNetId, oldMemberId, parIds,flag);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
     * <dt><span class="strong">方法描述:</span></dt><dd>添加派件</dd>
     * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2016-4-19</dd>
     * @param coopCompId        '任务受理方站点',
     * @param coopNetId         '任务受理方网络',
     * @param actorMemberId     '执行人员ID 取件员id',
     * @param actorPhone        '执行人电话',
     * @param contactName       '联系人姓名',
     * @param contactMobile     '联系人手机',
     * @param contactAddressId  '联系人地址id',
     * @param contactAddress    '详细地址',
     * @param createUserId      '创建人ID',
     * <dt><span class="strong">异常:</span></dt>
     * <dd>SendTaskController.createSendTask.coopCompId - 操作人ID不能为空</dd>
     * <dd>SendTaskController.createSendTask.coopNetId - 操作人ID不能为空</dd>
     * <dd>SendTaskController.createSendTask.coopCompId - 操作人ID不能为空</dd>
     * @return
     * @since v1.0
     */
    @ResponseBody
    @RequestMapping(value="/createSendTask",method = { RequestMethod.POST, RequestMethod.GET })
	public String createSendTask(String actorPhone,String expWaybillNum, Long compId, Long netId, String addresseeName, 
			String addresseeMobile,Long addresseeAddressId,String cityName,String addresseeAddress, BigDecimal freight,BigDecimal codAmount, Long memberId) {

		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("SendTaskController.createSendTask.memberId","memberId不能为空");
		}
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("SendTaskController.createSendTask.addresseeMobile","收件人电话不能为空");
		}
		try {

		String result = sendPackageService.addSendTask(1, compId, netId,
					1,-1, 11, 0, memberId,
					actorPhone, addresseeName, addresseeMobile, 
					addresseeAddressId, addresseeAddress,
					 new Date(), 0, new Date(),expWaybillNum,freight,codAmount,cityName);
		   return result;
		} catch (RuntimeException e) {
		   return jsonFailure(e);
		}
		 
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.querySendRecordList 
	 * @Description: TODO(派件记录列表) 
	 * @param @param memberId
	 * @param @param signDate
	 * @param @param currentPage
	 * @param @param pageSize
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-25
	 * @auth zhaohu
	 */
    @ResponseBody
	@RequestMapping(value = "/querySendRecordList", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendRecordList(Long memberId,String signDate){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecordList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(signDate)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecordList.003", "signDate不能为空");
		}
		try {
			HashMap<String, Object> resultMap = this.sendPackageService.querySendRecordList(memberId,signDate);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.isRegisterByPhone 
     * @Description: TODO(判断该手机号是否注册-0否1是) 
     * @param @param memberPhone
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-4-28
     * @auth zhaohu
     */
    @ResponseBody
	@RequestMapping(value = "/isRegisterByPhone", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRegisterByPhone(String memberPhone){
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.isRegisterByPhone.001", "memberPhone不能为空");
		}
		try {
			HashMap<String, Object> resultMap = this.sendPackageService.isRegisterByPhone(memberPhone);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.querySendCountAll 
     * @Description: TODO( 查询待派数量和待提数量 ) 
     * @param @param memberId
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-4-29
     * @auth zhaohu
     */
    @ResponseBody
   	@RequestMapping(value = "/querySendCountAll", method = { RequestMethod.POST, RequestMethod.GET })
   	public String querySendCountAll(String memberId){
   		if(PubMethod.isEmpty(memberId)){
   			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendCountAll.001", "memberId不能为空");
   		}
   		try {
   			HashMap<String, Object> resultMap = this.sendPackageService.querySendCountAll(memberId);
   			return jsonSuccess(resultMap);
   		} catch (Exception e) {
   			e.printStackTrace();
   			return jsonFailure(e);
   		}
   	}
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.leaveOffice 
     * @Description: TODO( 快递员离职 ) 
     * @param @param memberId
     * @param @param compId
     * @param @param memberName
     * @param @param memberPhone
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-5-6
     * @auth zhaohu
     */
    @ResponseBody
   	@RequestMapping(value = "/leaveOffice", method = { RequestMethod.POST, RequestMethod.GET })
   	public String leaveOffice(Long memberId, Long compId,String memberName, String memberPhone){
   		if(PubMethod.isEmpty(memberId)){
   			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.leaveOffice.001", "memberId不能为空");
   		}
   		try {
   			HashMap<String, Object> resultMap = this.sendPackageService.leaveOffice(memberId,compId,memberName,memberPhone);
   			return jsonSuccess(resultMap);
   		} catch (Exception e) {
   			e.printStackTrace();
   			return jsonFailure(e);
   		}
   	}
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.leaveOffice 
     * @Description: TODO( 根据sendTaskId 更改状态 ) 
     * @param @param sendTaskId
     * @param @param sendSmsType 群发通知类型：2.电话优先 3.仅电话 1.仅短信
     * @param @param sendSmsStatus 群发通知状态  0.发送成功  1.呼叫成功  2.重复  3.呼叫失败  4.发送失败   5.用户退订 6.拦截
     * @param @param callBackStatus 群发通知回执状态 0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败 6.短信转通道
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-5-6
     * @auth zhaohu
     */
    @ResponseBody
    @RequestMapping(value = "/updateParcelStatusBySendTaskId", method = { RequestMethod.POST, RequestMethod.GET })
    public String updateParcelStatusBySendTaskId(Long sendTaskId, Short sendSmsStatus,Short callBackStatus,Short sendSmsType){
    	if(PubMethod.isEmpty(sendTaskId)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelStatusBySendTaskId.001", "sendTaskId不能为空");
    	}
    	if(PubMethod.isEmpty(sendSmsStatus)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelStatusBySendTaskId.002", "sendSmsStatus不能为空");
    	}
    	if(PubMethod.isEmpty(callBackStatus)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelStatusBySendTaskId.003", "callBackStatus不能为空");
    	}
    	if(PubMethod.isEmpty(sendSmsType)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelStatusBySendTaskId.004", "sendSmsType不能为空");
    	}
    	try {
    		String result = this.sendPackageService.updateParcelStatusBySendTaskId(sendTaskId,sendSmsStatus,callBackStatus,sendSmsType);
    		return jsonSuccess(result);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.updateIsSendMsgFlag 
     * @Description: TODO(根据taskid修改是否发短信状态为1,0否1是) 
     * @param @param sendTaskId
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-5-21
     * @auth zhaohu
     */
    @ResponseBody
    @RequestMapping(value = "/updateIsSendMsgFlag", method = { RequestMethod.POST, RequestMethod.GET })
    public String updateIsSendMsgFlag(String sendTaskId){
    	if(PubMethod.isEmpty(sendTaskId)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateIsSendMsgFlag.001", "sendTaskId不能为空");
    	}
    	try {
    		this.sendPackageService.updateIsSendMsgFlag(sendTaskId);
    		return jsonSuccess();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.updateNumByParId 
     * @Description: TODO( 根据包裹parid修改编号 ) 
     * @param @param jsonData 
     * {"items":[{"parId":"123","parNum":"123","phone": "123"},{"parId":"456","parNum":"456","phone": "456"}]}
	     {
		    "items": [
			        {
			            "parId": "123",
			            "parNum": "123",
			            "phone": "123"
			        },
			        {
			            "parId": "456",
			            "parNum": "456",
			            "phone": "456"
			        }
			    ]
		 }
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-5-23
     * @auth zhaohu
     */
    @ResponseBody
    @RequestMapping(value = "/updateNumByParId", method = { RequestMethod.POST, RequestMethod.GET })
    public String updateNumByParId(String jsonData){
    	if(PubMethod.isEmpty(jsonData)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateNumByParId.001", "jsonData不能为空");
    	}
    	try {
    		this.sendPackageService.updateNumByParId(jsonData);
    		return jsonSuccess();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }
    /**
     * @MethodName: net.okdi.apiV4.controller.SendPackageController.java.saveParcels 
     * @Description: TODO(群发通知--批量保存电话号码为包裹信息) 
     * @param @param memberId
     * @param @param phoneData
     * @param @return   
     * @return String  返回值类型
     * @throws 
     * @date 2016-5-26
     * @auth zhaohu
     */
    @ResponseBody
    @RequestMapping(value = "/saveParcels", method = { RequestMethod.POST, RequestMethod.GET })
    public String saveParcels(String memberId,String phone, String phoneData){
    	if(PubMethod.isEmpty(memberId)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcels.001", "memberId不能为空");
    	}
    	if(PubMethod.isEmpty(phoneData)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcels.002", "phoneData不能为空");
    	}
    	if(PubMethod.isEmpty(phone)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcels.003", "phone不能为空");
    	}
    	try {
    		this.sendPackageService.saveParcels(memberId,phone,phoneData);
    		return jsonSuccess();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }
    
} 
