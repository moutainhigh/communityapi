package net.okdi.apiV4.controller;

import java.math.BigDecimal;

import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/assignPackage")
public class AssignPackageController extends BaseController {

	@Autowired
	private AssignPackageService assignPackageService;

	public static final Log logger = LogFactory.getLog(AssignPackageController.class);
	/**
	 * 
	 * @Description: 根据站点id查询站点人员列表
	 * @param compId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-19
	 */
	@ResponseBody
	@RequestMapping(value = "/querySiteMemberListV2", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySiteMemberList(Long compId,String memberId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.querySiteMemberList", "compId不能为空！");
		}
		try {
			
			return jsonSuccess(assignPackageService.queryEmployeeByCompId(compId,memberId));
	
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 保存包裹信息 (包裹id为空进行保存操作，否则进行更新操作)
	 * <dt><span class="strong">方法描述:</span></dt><dd>分派包裹保存包裹信息到par_parcelinfo</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-4-16 下午3:29:33</dd>
	 * @param id 包裹id
	 * @param sendTaskId 派件任务id
	 * @param expWaybillNum 运单号
	 * @param compId 公司id
	 * @param netId  网络id
	 * @param addresseeName 收件人姓名
	 * @param addresseeMobile 收件人手机号码
	 * @param freight 包裹应收运费
	 * @param codAmount 代收货款金额
	 * @param createUserId 创建人id
	 * @param actualSendMember 派件人id
	 * @return 
		 {"success": true, "data":1}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveParcelInfo", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String saveParcelInfo(String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile,Long addresseeAddressId,String cityName,
			String addresseeAddress, BigDecimal freight,BigDecimal codAmount,Long createUserId, Long actualSendMember){
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.saveParcelInfo", "addresseeMobile不能为空！");
		}
		try {
			return jsonSuccess(
					this.assignPackageService.saveParcelInfo(expWaybillNum, compId, netId, addresseeName, addresseeMobile,addresseeAddressId,cityName,
							addresseeAddress,freight, codAmount, createUserId, actualSendMember)
					);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 发送短信生成包裹信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>保存包裹信息到par_parcelinfo</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>yangkai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-4-25 下午4:26:33</dd>
	 * @param sendTaskId 派件任务id
	 * @param createUserId 创建人id
	 * @param actualSendMember 实际派件人id
	 * @param sendSmsType 群发通知类型：1.电话优先 2.仅电话 3.仅短信
	 * @param sendSmsStatus 群发通知回执状态
	 * @param addresseeMobile 收件人手机号
	 * @param sendMobile 发件人手机号
	 * @param parcelNum 包裹编号
	 * @param isNum 是否插入编号 0 不插入编号 1插入编号
	 * @return 
		 {"success": true, "data":1}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createParcelInfo", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String createParcelInfo(Long sendTaskId, Long createUserId,Long actualSendMember,
			Short sendSmsType,Short sendSmsStatus,Short callBackStatus,String addresseeMobile,String sendMobile,String parcelNum,Short replyStatus,
			Short isNum){
		if(PubMethod.isEmpty(sendTaskId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "sendTaskId派件任务Id不能为空！");
		}
		if(PubMethod.isEmpty(createUserId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "createUserId创建人Id不能为空！");
		}
		if(PubMethod.isEmpty(actualSendMember)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "actualSendMember实际派件人Id不能为空！");
		}
		if(PubMethod.isEmpty(sendSmsType)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "sendSmsType群发通知类型不能为空！");
		}
		if(PubMethod.isEmpty(sendSmsStatus)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "sendSmsStatus群发通知状态不能为空！");
		}
		if(PubMethod.isEmpty(callBackStatus)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "callBackStatus群发通知回执状态不能为空！");
		}
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "addresseeMobile收件人手机不能为空！");
		}
		if(PubMethod.isEmpty(sendMobile)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.createParcelInfo", "sendMobile发件人手机不能为空！");
		}
		if(PubMethod.isEmpty(replyStatus)){
			replyStatus=0;
		}
		if(PubMethod.isEmpty(isNum)){
			isNum=0;
		}
		try {
				this.assignPackageService.createParcelInfo(sendTaskId,createUserId,actualSendMember,sendSmsType,sendSmsStatus,
						callBackStatus,addresseeMobile,sendMobile,parcelNum,replyStatus,isNum);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 发送短信生成包裹信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>保存包裹信息到par_parcelinfo</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>yangkai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-4-25 下午4:26:33</dd>
	 * @param sendTaskId 派件任务id
	 * @param createUserId 创建人id
	 * @param actualSendMember 实际派件人id
	 * @param sendSmsType 群发通知类型：1.电话优先 2.仅电话 3.仅短信
	 * @param sendSmsStatus 群发通知回执状态
	 * @param addresseeMobile 收件人手机号
	 * @param sendMobile 发件人手机号
	 * @return 
		 {"success": true, "data":1}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateParcelInfo", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String updateParcelInfo(Long sendTaskId,Short callBackStatus){
		logger.info("更新包裹状态,包裹的派件id:"+sendTaskId+",修改的状态为:"+callBackStatus);
		if(PubMethod.isEmpty(sendTaskId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.updateParcelInfo", "sendTaskId派件任务Id不能为空！");
		}
		if(PubMethod.isEmpty(callBackStatus)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.updateParcelInfo", "callBackStatus群发通知回执状态不能为空！");
		}
		try {
			this.assignPackageService.updateParcelInfo(sendTaskId,callBackStatus);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 判断手机号今天是否添加过包裹
	 * @param addresseeMobile
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-27
	 */
	@ResponseBody
	@RequestMapping(value = "/parcelIsExist", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String parcelIsExist(String addresseeMobile,Long compId){
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.parcelIsExist", "addresseeMobile不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.parcelIsExist", "compId不能为空！");
		}
		try {
			return jsonSuccess(this.assignPackageService.parcelIsExist(addresseeMobile,compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * 更新包裹状态为客户回复
	 * <dt><span class="strong">方法描述:</span></dt><dd>保存包裹信息到par_parcelinfo</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>yangkai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-4-25 下午4:26:33</dd>
	 * @param sendTaskId 派件任务id
	 * @param createUserId 创建人id
	 * @param actualSendMember 实际派件人id
	 * @param sendSmsType 群发通知类型：1.电话优先 2.仅电话 3.仅短信
	 * @param sendSmsStatus 群发通知回执状态
	 * @param addresseeMobile 收件人手机号
	 * @param sendMobile 发件人手机号
	 * @return 
		 {"success": true, "data":1}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateParcelReply", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String updateParcelReply(Long sendTaskId,Short replyStatus){
		if(PubMethod.isEmpty(sendTaskId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.updateParcelReply", "sendTaskId派件任务Id不能为空！");
		}
		if(PubMethod.isEmpty(replyStatus)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.updateParcelReply", "replyStatus群发通知回执状态不能为空！");
		}
		try {
			this.assignPackageService.updateParcelReply(sendTaskId,replyStatus);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @MethodName: net.okdi.apiV4.controller.AssignPackageController.java.deleteSendTaskByTaskId 
	 * @Description: TODO(删除派件任务和相对应的包裹信息--update为空) 
	 * @param @param sendTaskId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-5-11
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSendTaskByTaskId", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String deleteSendTaskByTaskId(Long sendTaskId){
		if(PubMethod.isEmpty(sendTaskId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.deleteSendTaskByTaskId", "sendTaskId派件任务Id不能为空！");
		}
		try {
			this.assignPackageService.deleteSendTaskByTaskId(sendTaskId);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	
	
	
}
