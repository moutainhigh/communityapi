package net.okdi.apiV4.controller;

import com.alibaba.fastjson.JSON;
import net.okdi.apiV4.service.SmsTimedServiceV1;
import net.okdi.core.util.PubMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/smsTimed/v1")
public class SmsTimedControllerV1 {
	
	private @Autowired
    SmsTimedServiceV1 smsTimedServiceV1;
	Logger logger = Logger.getLogger(SmsTimedControllerV1.class);
	/**
	 * @author erlong.pei
	 * @api {GET} /smsTimed/v1/updateNumOrPhone 修改单个联系人的手机号或编号
	 * @apiVersion 0.4.0
	 * @apiDescription 延时发送功能，修改单个联系人的手机号或编号
	 * @apiGroup 延时发送V1
	 * @apiParam {Long} id	联系人id
	 * @apiParam {String} number 编号	|| 当不需要传此参数时传 ""
	 * @apiParam {String} phone 接收人号码	|| 当不需要传此参数时传 ""
	 * @apiParam {Integer} flag 标识  || 0:修改编号 1:修改手机号 2:都修改
	 * 
	 * @apiSuccess {String} rc 001-正常响应   | 501-联系人id不存在  | 502-系统异常，修改失败
	 * 
	 * @apiSampleRequest /smsTimed/v1/updateNumOrPhone
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	* @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "501",		--响应码 501
				"err": "联系人id不存在",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "502",		--响应码 502
				"err": "系统异常，修改失败",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/updateNumOrPhone", method = {RequestMethod.POST, RequestMethod.GET})
	public String updateSmsTimedInfo(Long id, String number, String phone, Integer flag){
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("", "当前id不能为空");
		}
		if (PubMethod.isEmpty(flag)) {
			return paramsFailure("", "标识不能为空");
		}
		switch (flag) {
		case 0:	//0:修改编号 
			if (PubMethod.isEmpty(number)) {
				return paramsFailure("", "编号不能为空");
			}
			break;
		case 1: // 1:修改手机号
			if (PubMethod.isEmpty(phone)) {
				return paramsFailure("", "手机号不能为空");
			}
			break;
		case 2: //2:都修改
			if (PubMethod.isEmpty(number)) {
				return paramsFailure("", "编号不能为空");
			}
			if (PubMethod.isEmpty(phone)) {
				return paramsFailure("", "手机号不能为空");
			}
			break;
		default:
				return paramsFailure("", "flag值不合法");
		}
		return smsTimedServiceV1.updateSmsTimedInfo(id, number, phone, flag);
	}
	
	/**
	 * @author erlong.pei
	 * @api {GET} /smsTimed/v1/deleteSmsTimedInfo 删除单个联系人
	 * @apiVersion 0.4.0
	 * @apiDescription 延时发送功能，删除单个联系人
	 * @apiGroup 延时发送V1
	 * @apiParam {Long} id	接收人id
	 * 
	 * @apiSuccess {String} rc 001-正常响应  | 503-系统异常，删除失败
	 * 
	 * @apiSampleRequest /smsTimed/v1/deleteSmsTimedInfo
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	* @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "503",		--响应码 503
				"err": "系统异常，删除失败",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSmsTimedInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteSmsTimedInfo(Long id){
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("", "当前id不能为空");
		}
		return smsTimedServiceV1.deleteSmsTimedInfo(id);
	}
	
	/**
	 * @author erlong.pei
	 * @api {GET} /smsTimed/v1/sendSmsTimed 立即发送
	 * @apiVersion 0.4.0
	 * @apiDescription 延时发送功能，立即发送
	 * @apiGroup 延时发送V1
	 * @apiParam {Long} id	组id
	 * 
	 * @apiSuccess {String} rc 001-正常响应  | 504-延时发送分组中无联系人 | 505-延时发送, 发送不成功,提示err错误
	 * 
	 * @apiSampleRequest /smsTimed/v1/sendSmsTimed
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	* @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "504",		--响应码 504
				"err": "延时发送分组中无联系人",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/sendSmsTimed", method = {RequestMethod.POST, RequestMethod.GET})
	public String sendSmsTimed(Long id){
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("", "当前id不能为空");
		}
		return smsTimedServiceV1.sendSmsTimed(id);
	}
	
	protected String paramsFailure(String errSubcode,String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", 0);
		map.put("errSubcode",errSubcode);
		map.put("message",message);
		return JSON.toJSONString(map);
	}
	/**
	 * @api {GET} /smsTimed/v1/timeDelaySendSms 保存延时发送
	 * @apiVersion 0.4.0
	 * @apiDescription 保存延时发送
	 * @apiGroup 延时发送V1
	 * @apiParam {String} groupName 分组名字
	 * @apiParam {Long} warnTime 提醒时间(注意是Long类型)
	 * @apiParam {Long} sendTime 发送时间(注意是Long类型)
	 * @apiParam {String} flag 短信类型    1.仅短信  2.电话优先 3.仅电话 
	 * @apiParam {String} content 发送内容
	 * @apiParam {Long} memberId memberId
	 * @apiParam {String} memberPhone 收派员手机号
	 * @apiParam {String} accountId 收派员钱包id
	 * @apiParam {String} phoneAndNum json字符串，格式如下：{"items":[{"parNum":"123","mobilePhone":"123"},{"parNum":"345","mobilePhone":"345"},{"parNum":"567","mobilePhone":"567"}]} parNum 编号，mobilePhone 手机号
	 * @apiParam {Integer} flag 标识  || 0:修改编号 1:修改手机号 2:都修改
	 * @apiParam {String} type 类型(1：提醒，2：直接发送通知)
	 * @apiSuccess {String} data groupId
	 * @apiSampleRequest /smsTimed/v1/timeDelaySendSms
	 * @apiSuccessExample Success-Response: 正常响应
	 * {"data":"222745463808000","success":true}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value="/timeDelaySendSms", method={RequestMethod.GET,RequestMethod.POST})
	public String timeDelaySendSms(String groupName, Long warnTime,Long sendTime,Short flag,String content,
			Long memberId,String memberPhone,String accountId,String phoneAndNum,String type){
		logger.info("保存延时发送timeDelaySendSms===groupName: "+groupName+" ,warnTime: "+warnTime+" ,sendTime: "+sendTime+" ,flag: "+flag+ " ,content: "+content+" ,memberId: "+memberId+" ,memberPhone: "+memberPhone
				+",accountId: "+accountId+" ,phoneAndNum: "+phoneAndNum+" ,type: "+type);
			return smsTimedServiceV1.timeDelaySendSms( groupName,  warnTime, sendTime, flag, content,
			 memberId, memberPhone, accountId, phoneAndNum,type);
	}

	/**
	 * @api {GET} /smsTimed/v1/aliTimeDelaySendSms 保存延时发送-新版
	 * @apiVersion 0.4.0
	 * @apiDescription 保存延时发送(包含群发通知和扫码通知)
	 * @apiGroup 延时发送V1
	 * @apiParam {String} groupName 分组名字
	 * @apiParam {Long} warnTime 提醒时间(注意是Long类型)
	 * @apiParam {Long} sendTime 发送时间(注意是Long类型)
	 * @apiParam {String} flag 短信类型 1.群发通知(仅短信) 2.电话优先 5:群发通知(微信优先) 91:阿里大于(仅短信) 95:阿里大于(微信优先)
	 * @apiParam {String} content 发送内容
	 * @apiParam {String} templateId 模版ID
	 * @apiParam {Long} memberId memberId
	 * @apiParam {String} memberPhone 收派员手机号
	 * @apiParam {String} accountId 收派员钱包id
	 * @apiParam {String} phoneAndNum json字符串，格式如下：{"items":[{"parNum":"123","mobilePhone":"123","receiverName":"张三","netName":"中通","netId":"123","parId":"123","wayBillNum":"123123123"},{"parNum":"345","mobilePhone":"345","receiverName":"张三","netName":"中通","netId":"123",,"parId":"123""wayBillNum":"123123123"}]} parNum 编号，mobilePhone 手机号
	 * @apiParam {String} aliParams   json字符串 {"items":[{"parNum":"123","mobilePhone":"123","netName":"中通","netId":"123","parId":"123","wayBillNum":"123123123"},{"parNum":"456","mobilePhone":"456","netName":"中通","netId":"456","parId":"123","wayBillNum":"456456456"}]}
	 * @apiParam {Integer} flag 标识  || 0:修改编号 1:修改手机号 2:都修改
	 * @apiParam {String} type 类型(1：提醒，2：直接发送通知)
	 * @apiParam {String} pickupAddr 取件地址
	 * @apiParam {String} version APP版本号
	 * @apiParam {String} system 操作系统 安卓 传 Android, 苹果 传 IOS(不能为空)
	 * @apiSuccess {String} smsGroupId 仅短信GroupId
	 * @apiSuccess {String} aliGroupId 扫码发短信GroupId
	 * @apiSampleRequest /smsTimed/v1/timeDelaySendSms
	 * @apiSuccessExample Success-Response: 正常响应
	 * {"data":"{"smsGroupId":"123123123", "aliGroupId":"123123123123"}","success":true}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value="/aliTimeDelaySendSms", method={RequestMethod.GET,RequestMethod.POST})
	public String aliTimeDelyaSendSms(String groupName, Long warnTime, Long sendTime,
									  Short flag, String content, String templateId, Long memberId, String memberPhone,
									  String accountId, String phoneAndNum, String aliParams, String type, String pickupAddr, String version, String system){
		logger.info("保存延时发送新版timeDelaySendSms===groupName: "+groupName+" ,warnTime: "+warnTime+" ,sendTime: "+sendTime+" ,flag: "+flag+ " ,content: "+content+" ,memberId: "+memberId+" ,memberPhone: "+memberPhone
				+",accountId: "+accountId+" ,phoneAndNum: "+phoneAndNum+", aliParams" + aliParams + " ,type: "+type+"templateId: "+templateId + "pickupAddr: " + pickupAddr);
		return smsTimedServiceV1.aliTimeDelaySendSms(groupName, warnTime, sendTime, flag, content, templateId, memberId,
				memberPhone, accountId, phoneAndNum, aliParams, type, pickupAddr, version, system);
	}


	/**
	 * @api {GET} /smsTimed/v1/timeDelaySendSmsList 延时发送列表
	 * @apiVersion 0.4.0
	 * @apiDescription 保存延时发送列表
	 * @apiGroup 延时发送V1
	 * @apiParam {Long} memberId memberId
	 * @apiSuccess {String} groupName 分组名称
	 * @apiSuccess {Long} createTime 创建时间
	 * @apiSuccess {Long} warnTime 提醒时间
	 * @apiSuccess {String} id 分组id
	 * @apiSuccess {String} groupName 分组名称
	 * @apiSuccess {Long} sendCount 分组里面发送的手机号数
	 * @apiSuccess {Long} sendTime 发送时间
	 * @apiSuccess {String} type 类型(1：提醒，2：直接发送通知)
	 * @apiSuccess {Integer} count 短信条数
	 * @apiSuccess {Long} flag 短信类型 1.群发通知(仅短信) 2.电话优先 5:群发通知(微信优先) 91:阿里大于(仅短信) 95:阿里大于(微信优先)
	 * @apiSampleRequest /smsTimed/v1/timeDelaySendSmsList
	 * @apiSuccessExample Success-Response: 正常响应
	 * {
		    "data":[
		        {
		            "accountId":"106020113741787136",
		            "content":"客户您好,请到健德门环星大厦来取快递",
		            "count":1,
		            "createTime":1467957098552,
		            "flag":1,
		            "groupName":"151134006484992",
		            "id":222724584562688,
		            "isRemove":0,
		            "isSend":0,
		            "memberId":2051403749645167,
		            "memberPhone":"13910850043",
		            "sendTime":1467957098295,
		            "updateTime":1467957098552,
		            "warnTime":1467957098295,
		            "sendCount":3,
		            "type":"1"
		        }
		    ],
		    "success":true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value="/timeDelaySendSmsList", method={RequestMethod.GET,RequestMethod.POST})
	public String timeDelaySendSmsList(Long memberId){
			return smsTimedServiceV1.timeDelaySendSmsList(memberId);
	}
	/**
	 * @api {GET} /smsTimed/v1/delaySendSmsDetial 延时发送详情
	 * @apiVersion 0.4.0
	 * @apiDescription 延时发送详情
	 * @apiGroup 延时发送V1
	 * @apiParam {Long} groupId groupId分组的Id
	 * @apiSuccess {String} content 发送内容
	 * @apiSuccess {Long} flag 短信类型 1.群发通知(仅短信) 2.电话优先 5:群发通知(微信优先) 91:阿里大于(仅短信) 95:阿里大于(微信优先)
	 * @apiSuccess {Long} number 编号
	 * @apiSuccess {String} receiverPhone 接收人手机号
	 * @apiSuccess {String} receiverName 接收人姓名
	 * @apiSuccess {Integer} smsCount 短信条数
	 * @apiSuccess {Integer} phoneCount 发送手机号条数
	 * @apiSampleRequest /smsTimed/v1/DelaySendSmsDetial
	 * @apiSuccessExample Success-Response: 正常响应
	 * {
    "data":{
        "SmsTimedInfoList":[
            {
                "createTime":1468215036466,
                "id":223265519755265,
                "isRemove":0,
                "isSendId":"223265230749696",
                "number":"12",
                "receiverPhone":"13521225785",
                "timedTaskId":223265519755264,
				"netName":"百世汇通",
				"wayBillNum":"123123123"，
				"receiverName"":"张三"
            }
        ],
        "content":"客户您好,请到健德门环星大厦来取快递",
        "flag":1,
        "phoneCount":2,
        "smsCount":1
    },
    "success":true
}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value="/delaySendSmsDetial", method={RequestMethod.GET,RequestMethod.POST})
	public String DelaySendSmsDetial(Long groupId){
			return smsTimedServiceV1.DelaySendSmsDetial(groupId);
	}
	/**
	 * @api {GET} /smsTimed/v1/updateSmsWarnTime 修改提醒时间
	 * @apiVersion 0.4.0
	 * @apiDescription 修改提醒时间
	 * @apiGroup 延时发送V1
	 * @apiParam {Long} groupId groupId分组的Id
	 * @apiParam {Long} warnTime 提醒时间
	 * @apiParam {String} type 类型（1:提醒时间，2：发送时间）
	 * @apiSampleRequest /smsTimed/v1/updateSmsWarnTime
	 * @apiSuccessExample Success-Response: 正常响应
	 * {
	    "data":"true"
	    "success":true
	}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value="/updateSmsWarnTime", method={RequestMethod.GET,RequestMethod.POST})
	public String updateSmsWarnTime(Long groupId,Long warnTime,String type){
			return smsTimedServiceV1.updateSmsWarnTime(groupId,warnTime,type);
	}
	
	/**
	 * @author erlong.pei
	 * @api {GET} /smsTimed/v1/deleteSmsList 批量删除延时发送列表
	 * @apiVersion 0.4.0
	 * @apiDescription 批量删除延时发送列表
	 * @apiGroup 延时发送V1
	 * @apiParam {String} ids	延时发送列表的id  单个id的时候直接传id，多个id时使用 - 符号拼接 例: 123-123-123-123
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /smsTimed/v1/deleteSmsList
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSmsList", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteSmsList(String ids){
		if (PubMethod.isEmpty(ids)) {
			return paramsFailure("", "需要删除的ids不能为空");
		}
		return smsTimedServiceV1.deleteSmsList(ids);
	}
}