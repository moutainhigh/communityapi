package net.okdi.api.controller;

import net.okdi.api.service.SmsTempleAuditService;
import net.okdi.core.base.BaseController;
import static net.okdi.core.util.PubMethod.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("smsTempleAudit")
public class SmsTempleAuditController extends BaseController{
	@Autowired
	SmsTempleAuditService smsTempleAuditService; 
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>上传短信模板</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:06:24</dd>
	 * @param memberId		收派员Id
	 * @param compId		站点id
	 * @param compName		站点名称
	 * @param memberPhone	收派员手机号码
	 * @param realName		真实姓名
	 * @param templeContent 模板内容
	 * @return		
	 */
	@ResponseBody
	@RequestMapping("/upLoadSmsTemple")
	public String upLoadSmsTemple(Long memberId, Long compId, String compName, String memberPhone, String realName,String templeContent) {
		if (isEmpty(memberId)||isEmpty(memberPhone)||isEmpty(templeContent)) {
			return paramsFailure("SmsTempleAuditController.upLoadSmsTemple.001", "memberId,memberPhone,templeContent为必填项");
		}
		try {
			return jsonSuccess(this.smsTempleAuditService.upLoadSmsTemple(memberId, compId, compName, memberPhone, realName,templeContent));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:07:52</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findSmsTemple")
	public String findSmsTemple(Long memberId) {
		if (isEmpty(memberId)) {
			return paramsFailure("SmsTempleAuditController.findSmsTemple.001", "memberId为必填项");
		}
		try {
			return jsonSuccess(this.smsTempleAuditService.findSmsTemple(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>编辑短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:15:08</dd>
	 * @param id			短信模板id
	 * @param smsContent	短信模板内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editSmsTemple")
	public String editSmsTemple(Long id, String smsContent) {
		if (isEmpty(id)||isEmpty(smsContent)) {
			return paramsFailure("SmsTempleAuditController.editSmsTemple.001", "id,smsContent为必填项");
		}
		try {
			return jsonSuccess(this.smsTempleAuditService.editSmsTemple(id, smsContent));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:26:12</dd>
	 * @param id			短信模板id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteSmsTemple")
	public String deleteSmsTemple(Long id) {
		if (isEmpty(id)) {
			return paramsFailure("SmsTempleAuditController.deleteSmsTemple.001", "id为必填项");
		}
		try {
			return jsonSuccess(this.smsTempleAuditService.deleteSmsTemple(id));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询短信模板内容</dd>
	 * @param id		短信模板id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findSmsTempleById")
	public String findSmsTempleById(Long id) {
		if (isEmpty(id)) {
			return paramsFailure("SmsTempleAuditController.findSmsTempleById.001", "id为必填项");
		}
		try {
			return jsonSuccess(this.smsTempleAuditService.findSmsTempleById(id));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd><strong>运营平台</strong>审核短信模板</dd>
	 * @param id			短信模板id
	 * @param status		审核状态 0:待审核 1:通过 2:驳回
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/auditSmsTemple")
	public String auditSmsTemple(Long id, Short status) {
		if (isEmpty(id)||isEmpty(status)) {
			return paramsFailure("SmsTempleAuditController.auditSmsTemple.001", "id,status为必填项");
		}
		try {
			return jsonSuccess(this.smsTempleAuditService.auditSmsTemple(id, status));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd><strong>运营平台</strong>查询短信模板</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:35:37</dd>
	 * @param phone			手机号
	 * @param realName		真实姓名(模糊查询)
	 * @param compName		站点姓名(模糊查询)
	 * @param auditCount	认证次数
	 * @param auditStatus	认证的状态
	 * @param startTime		开始时间 格式：2015-07-01 11:11:11
	 * @param endTime		结束时间 格式：2015-07-03 11:11:11
	 * @param currentPage	当前页
	 * @param pageSize		页面大小
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findSmsTemplePT")
	public String findSmsTemplePT(String phone, String realName, String compName, String auditCount, Short auditStatus, String startTime,String endTime,Integer currentPage, Integer pageSize) {
		try {
			return jsonSuccess(this.smsTempleAuditService.findSmsTemplePT(phone, realName, compName, auditCount, auditStatus,startTime,endTime,currentPage,pageSize));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
}
