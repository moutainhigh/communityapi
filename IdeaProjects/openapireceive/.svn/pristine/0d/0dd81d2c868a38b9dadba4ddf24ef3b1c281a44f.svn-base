/**  
 * @Project: openapi
 * @Title: SendTaskController.java
 * @Package net.okdi.api.controller
 * @author amssy
 * @date 2014-12-9 下午07:10:43
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
 */
package net.okdi.api.controller;

import java.util.Date;

import net.okdi.api.service.SendTaskService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/SendTaskController")
public class SendTaskController extends BaseController {
	@Autowired
	SendTaskService sendTaskService;
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>扫描提货</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 上午11:49:33</dd>
     * @param taskType          '任务类型 0:取件 1:派件 2:自取件 3：销售等非快递',
     * @param coopCompId        '任务受理方站点',
     * @param coopNetId         '任务受理方网络',
     * @param parEstimateCount  '包裹预估数',
     * @param parEstimateWeight '包裹预估重量',
     * @param appointTime       '预约取件时间',
     * @param appointDesc       '预约描述',
     * @param actorMemberId     '执行人员ID 取件员id',
     * @param actorPhone        '执行人电话',
     * @param contactName       '联系人姓名',
     * @param contactMobile     '联系人手机',
     * @param contactTel        '联系人电话',
     * @param contactAddressId  '联系人地址id',
     * @param contactAddress    '详细地址',
     * @param customerId        '客户id/发件人id',
     * @param contactCasUserId  '联系人CAS_ID',
     * @param contactCompId     '联系人公司ID',
     * @param createUserId      '创建人ID',
     * @param taskFlag          '任务标志 0：正常,1：抢单',
     * @param contactAddrLongitude '联系人地址的经度信息',
     * @param contactAddrLatitude  '联系人地址的纬度信息',
     * <dt><span class="strong">异常:</span></dt>
     * <dd>SendTaskController.createSendTask.coopCompId - 操作人ID不能为空</dd>
     * <dd>SendTaskController.createSendTask.coopNetId - 操作人ID不能为空</dd>
     * <dd>SendTaskController.createSendTask.coopCompId - 操作人ID不能为空</dd>
     * @return
     * @since v1.0
     */
	@RequestMapping("createSendTask")
    @ResponseBody
	public String createSendTask(Long parcelId,Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc,Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Long createUserId,
			Integer taskFlag, Double contactAddrLongitude,
			Double contactAddrLatitude) {
		if(PubMethod.isEmpty(coopCompId)){
			return paramsFailure("SendTaskController.createSendTask.coopCompId","任务受理方站点不能为空");
		}
		if(PubMethod.isEmpty(coopNetId)){
			return paramsFailure("SendTaskController.createSendTask.coopNetId","任务受理方网络不能为空");
		}
		if(PubMethod.isEmpty(actorMemberId)){
			return paramsFailure("SendTaskController.createSendTask.actorMemberId","取件员id不能为空");
		}
		if(PubMethod.isEmpty(actorPhone)){
			return paramsFailure("SendTaskController.createSendTask.actorPhone","取件员电话不能为空");
		}
		if(PubMethod.isEmpty(parcelId)){
			return paramsFailure("SendTaskController.createSendTask.parcelId","包裹ID不能为空");
		}
		try {
		String result = sendTaskService.addSendTask(parcelId,1, coopCompId, coopNetId,
					parEstimateCount, parEstimateWeight, appointTime, appointDesc,
					-1, 11, 0, null, actorMemberId,
					actorPhone, contactName, contactMobile, contactTel,
					contactAddressId, contactAddress, customerId, contactCasUserId,
					contactCompId, new Date(), createUserId, 0, new Date(),
					contactAddrLongitude, contactAddrLatitude);
		   return result;
		} catch (RuntimeException e) {
		   return jsonFailure(e);
		}
		 
	}

	/**
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * @param parcelId
	 * @param memberId
	 * @param memberPhone
	 * @return
	 */
	@RequestMapping("createSendTaskBatch")
    @ResponseBody
	public String createSendTaskBatch(String parcelId,Long memberId,String memberPhone){
		try {
			if(PubMethod.isEmpty(parcelId)){
				return paramsFailure("openapi.SendTaskController.createSendTaskBatch.001", "包裹Ids不能为空");
			}
		String result= sendTaskService.addSendTaskBatch(parcelId, memberId, memberPhone);
		return result;
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param parcelId
	 * @param memberId
	 * @param memberPhone
	 * @return
	 */
	@RequestMapping("createSendTaskOrUpdate")
    @ResponseBody
	public String createSendTaskOrUpdate(String parcelId,Long memberId,String memberPhone){ 
		try {
			if(PubMethod.isEmpty(parcelId)){
				return paramsFailure("openapi.SendTaskController.createSendTaskBatch.001", "包裹Ids不能为空");
			}
		String result= sendTaskService.createSendTaskOrUpdate(parcelId, memberId, memberPhone);
		return result;
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>批量转单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午04:11:19</dd>
	 * @param parcelIds 包裹ID串 ，隔开
	 * @param memberId 转给的收派员ID
	 * @param oldMemberId 
	 * @return
	 * @since v1.0
	 */
	@RequestMapping("changSendPerson")
    @ResponseBody
	public String changSendPerson(String parcelIds, Long memberId,Long oldMemberId) {
		try {
			String result = sendTaskService.changSendPerson(parcelIds, memberId,oldMemberId);	
		    return result;
		} catch (RuntimeException e) {
           return jsonFailure(e);
		}
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断包裹是否已经被提货</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 下午04:52:55</dd>
	 * @param expWayBillNum 运单号
	 * @param netId 包裹所属网络
	 * @return {"data":{"hasPickUp" //是否已经提货:true 已经被提货},"success":true}
	 * @since v1.0
	 */
	@RequestMapping("ifHasPickUp")
    @ResponseBody
	public String ifHasPickUp(String expWayBillNum,Long netId) {
		try {
			System.out.println("============扫描派件：ifHasPickUp:"+expWayBillNum+"----"+netId);
			String result = sendTaskService.ifHasPickUp( expWayBillNum, netId);	
			return result;
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
		
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>派件任务列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 上午10:32:46</dd>
	 * @param memberId 收派员ID
	 * @return
	 * @since v1.0
	 */
	@RequestMapping("querySendTaskList")
    @ResponseBody
	public String querySendTaskList(Long memberId) {
		try {
			String result = sendTaskService.querySendTaskList(memberId);	
			return result;
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 下午01:19:23</dd>
	 * @param taskId 任务ID
	 * @param parcelIds 包裹Id串
	 * @param totalCodAmount 代收货款金额
	 * @param totalFreight 实收运费
	 * @param type   类型 0取件 1派件
	 * @param memberId 创建人
	 * @return
	 * @since v1.0
	 */
	@RequestMapping("finishTask")
    @ResponseBody
	public String finishTask(Long taskId,String parcelIds,Double totalCodAmount,Double totalFreight,Long memberId,String expWaybillNum) {
		try {
			String result = sendTaskService.finishTask(taskId, parcelIds, totalCodAmount, totalFreight,(short) 1, memberId,expWaybillNum);	
			return result;
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>派件任务取消</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 下午01:19:23</dd>
	 * @param taskId 任务ID
	 * @param parcelId 包裹Id
	 * @param memberId 收派员Id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping("cancelSendTask")
    @ResponseBody
	public String cancelSendTask(Long taskId, Long memberId, Long parcelId , Long Id ,String cancelType,Long compId,String compName,String textValue) {
		try {
			return sendTaskService.cancelSendTask(taskId, memberId, parcelId, Id, cancelType, compId, compName,textValue);
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
	}
	
	
	@RequestMapping("ifHasPickUpByParcelIdAndMemberId")
    @ResponseBody
	public String ifHasPickUpByParcelIdAndMemberId(String parcelid,Long memberId) {
		try {
			if(sendTaskService.ifHasPickUpByParcelIdAndMemberId(parcelid, memberId)) {
				return jsonFailure();	
			}else{
				return jsonSuccess();
			}
		} catch (RuntimeException e) {
			return jsonFailure(e);
		}
	}
	
}
