package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.SubmittedSignService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/submittedSign")
public class SubmittedSignController extends BaseController {

	Logger LOGGER= Logger.getLogger(SubmittedSignController.class);
	@Autowired
	SubmittedSignService submittedSignService;
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/authStatus 报签收-用户是否授权(暂时不用)
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-用户是否授权
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 站点id
	 * @apiParam {Long} netId 网络id
	 * @apiSampleRequest /submittedSign/authStatus
	 * @apiSuccess {String} bill  运单号
	 * @apiSuccess {String} parId  包裹id
	 * @apiSuccessExample Success-Response:
	  {"data":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/authStatus", method={RequestMethod.POST, RequestMethod.GET})
	public String queryAuthStatus(Long memberId, Long netId){
		String result = submittedSignService.queryAuthStatus(memberId, netId);
		return result;
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/quOrUpParcelToControlMemberId 报签收-查询该运单号是否在待派包裹中存在
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-查询该运单号是否在待派包裹中存在
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 站点id
	 * @apiParam {String} billJson 格式: {"netName":"国通","bill":"11111111111"}
	 * @apiSampleRequest /submittedSign/quOrUpParcelToControlMemberId
	 * @apiSuccess {String} bill  运单号
	 * @apiSuccess {String} parId  包裹id
	 * @apiSuccess {String} phone  手机号
	 * @apiSuccess {String} flag  001:不取其他字段如(bill,parId,phone),001代表该运单已成功报签收,不能录入进去;002:不取其他字段如(bill,parId,phone),该运单在待派包裹中也不存在,
	 * 如果此时修改该运单号还需在调用本接口,根据返回状态就是flag=003或者是有parId,来是否调取更改 updateSignBill; 003:是该运单在待派包裹中存在,如果有修改手机号或者运单号则需调用updateSignBill
	 * @apiSuccessExample Success-Response:
	  {"data":{"flag":"001"},"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/quOrUpParcelToControlMemberId", method={RequestMethod.POST, RequestMethod.GET})
	public String quOrUpParcelToControlMemberId(Long memberId, String billJson){
		if(PubMethod.isEmpty(billJson)){
			return paramsFailure("001", "billJson 不能为空");
		}
		String result = submittedSignService.quOrUpParcelToControlMemberId(memberId, billJson);
		return result;
	}
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/subSignBatch 报签收-报签收,重新上传
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-报签收,重新上传
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} netName 网络名称
	 * @apiParam {Long} compId 快递员或者代售点compId
	 * @apiParam {String} billJson 格式: [{"netName":"国通","terminalId":"0000000","versionId":"5.0.4","bill":"11111","parId":"22222222","netId":"111111","recPhone":"15810885287"},{"netName":"国通","terminalId":"0000000","versionId":"5.0.4","bill":"11111","parId":"22222222","netId":"111111","recPhone":"15810885287"}]
	 * @apiParam {String} flag 点击报签收 传 1,点击重新上传 传空
	 * @apiParam {Long} netId 网络id
	 * @apiSampleRequest /submittedSign/subSignBatch
	 * @apiSuccess {String} succCount  成功数量
	 * @apiSuccess {String} failCount  失败数量
	 * @apiSuccess {String} parId  包裹id
	 * @apiSuccess {String} controllerNetName  网络名称
	 * @apiSuccess {String} bill  运单号
	 * @apiSuccess {String} netId  网络id
	 * @apiSuccess {String} compId 快递员或者代售点compId
	 * @apiSuccess {String} totalCount 包裹总数多少个
	 * @apiSuccess {String} msg  失败原因
	 * @apiSuccessExample Success-Response:
	  {
	    "data": {
	        "failCount": 2,
	        "listMap": [
	            {
	                "addresseeName": "",
	                "addresseePhone": "",
	                "bill": "333333333333",
	                "controllerNetName": "",
	                "msg": "1条添加失败！",
	                "parId": "",
	                "netId":11111,
	                "compId":111111
	            },
	            {
	                "addresseeName": "",
	                "addresseePhone": "",
	                "bill": "4444444444",
	                "controllerNetName": "",
	                "msg": "1条添加失败！",
	                "parId": "",
	                "netId":11111,
	                "compId":11111
	            }
	        ],
	        "succCount": 0
	    },
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/subSignBatch", method={RequestMethod.POST, RequestMethod.GET})
	public String subSignBatch(Long memberId, Long compId, String billJson, String flag, Long netId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("002", "compId 不能为空");
		}
		if(PubMethod.isEmpty(billJson)){
			return paramsFailure("003", "billJson 不能为空");
		}
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("004", "netId 不能为空");
		}
		String result = submittedSignService.subSignBatch(memberId, compId, billJson, flag, netId);
		return result;
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/querySignInfoCount 报签收- 报签收报表
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-报签收报表
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 快递员memberId 如果查询本站点下所有的,则memberId传空,其他情况都传
	 * @apiParam {String} dateYM 日期格式yyyyMM:201703
	 * @apiParam {String} compId 公司id(站点id)
	 * @apiSampleRequest /submittedSign/querySignInfoCount
	 * @apiSuccess {String} noticeNumber  每月的数量
	 * @apiSuccess {String} dateYMD  每天的日期格式是 yyyyMMdd
	 * @apiSuccess {String} dateYm  每月的总数量 yyyyMM
	 * @apiSuccess {String} parNumber  每天的包裹数量 
	 * @apiSuccessExample Success-Response:
	  {"data":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/querySignInfoCount", method={RequestMethod.POST, RequestMethod.GET})
	public String querySignInfoCount(Long memberId, String dateYM, Long compId){
		
		String result = submittedSignService.querySignInfoCount(memberId, dateYM, compId);
		return result;
	}
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/queryNoticeInfoCount 报签收- 通知报表
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-通知报表
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 快递员memberId, 
	 * @apiParam {String} dateYM 日期格式yyyyMM:201703
	 * @apiParam {String} compId 公司id(站点id) 如果查询本站点下所有的,则compId不为空;如果查询单个人的compId为空
	 * @apiSampleRequest /submittedSign/queryNoticeInfoCount
	 * @apiSuccess {String} noticeNumber  每月的数量
	 * @apiSuccess {String} dateYm  每月的总数量 yyyyMM
	 * @apiSuccess {String} dateYMD  每天的日期格式是 yyyyMMdd
	 * @apiSuccess {String} voiceNumber  每天的语音数量 
	 * @apiSuccess {String} smsNumber  每天的短信数量 
	 * @apiSuccess {String} weiChatNumber  每天的微信数量 
	 * @apiSuccessExample Success-Response:
	  {"data":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/queryNoticeInfoCount", method={RequestMethod.POST, RequestMethod.GET})
	public String queryNoticeInfoCount(Long memberId, String dateYM, Long compId){
		
		String result = submittedSignService.queryNoticeInfoCount(memberId, dateYM, compId);
		return result;
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/querySignInfoRecord 报签收- 报表-报签收记录
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-报表-报签收记录
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 快递员memberId(查询本站全部的时候,memberId是传空的)
	 * @apiParam {String} dateYMD 日期格式yyyyMMdd:20170322
	 * @apiParam {String} compId 公司id(站点id)
	 * @apiSampleRequest /submittedSign/querySignInfoRecord
	 * @apiSuccess {String} expWaybillNum  运单号
	 * @apiSuccess {String} recnetName  接收人网络名称
	 * @apiSuccess {String} recMobile  接收人手机号
	 * @apiSuccess {String} submitSignMsg 失败原因
	 * @apiSuccess {Date} createTime  创建日期
	 * @apiSuccess {Long} uid  唯一uid,在报签收记录详情中传入uid
	 * @apiSuccess {Long} expMemberId  唯返回的快递员memberId,在报签收记录详情中传入memberId
	 * @apiSuccessExample Success-Response:
	  {
	    "data": {
	        "list": [
	            {
	                "compId": 1536,
	                "cosignFlag": 12,
	                "createTime": 1490018374072,
	                "dateYMD": "",
	                "expMemberId": 212192318504960,
	                "expWaybillNum": "4444444444",
	                "mobilePhone": "4444444444",
	                "netId": 1536,
	                "parId": 22222222,
	                "recnetName": "国通",
	                "signFlag": 2,
	                "signResult": "",
	                "submitSignMsg": "1条添加失败！",
	                "uid": 268990431952896,
	                "recMobile":15810885287
	            },
	            {
	                "compId": 1536,
	                "cosignFlag": 12,
	                "createTime": 1490018374072,
	                "dateYMD": "",
	                "expMemberId": 212192318504960,
	                "expWaybillNum": "4444444444",
	                "mobilePhone": "4444444444",
	                "netId": 1536,
	                "parId": 22222222,
	                "recnetName": "国通",
	                "signFlag": 2,
	                "signResult": "",
	                "submitSignMsg": "1条添加失败！",
	                "uid": 268990431952896,
	                "recMobile":15810885287
	            }
	        ]
	    },
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/querySignInfoRecord", method={RequestMethod.POST, RequestMethod.GET})
	public String querySignInfoRecord(Long memberId, String dateYMD, Long compId){
		
		String result = submittedSignService.querySignInfoRecord(memberId, dateYMD, compId);
		return result;
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/queryUploadFailParcelInfo 报签收- 报签收失败列表
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-报签收失败列表
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiSampleRequest /submittedSign/queryUploadFailParcelInfo
	 * @apiSuccess {String} expWaybillNum  运单号
	 * @apiSuccess {String} recnetName  接收人网络名称
	 * @apiSuccess {String} submitSignMsg  失败原因
	 * @apiSuccess {Date} createTime  创建日期
	 * @apiSuccess {Long} compId  接收人属于哪个站点id
	 * @apiSuccess {Long} parId  包裹id
	 * @apiSuccessExample Success-Response:
	  {
	    "data": {
	        "list": [
	            {
	                "compId": 1536,
	                "cosignFlag": 12,
	                "createTime": 1490018374072,
	                "dateYMD": "",
	                "expMemberId": 212192318504960,
	                "expWaybillNum": "4444444444",
	                "mobilePhone": "4444444444",
	                "netId": 1536,
	                "parId": 22222222,
	                "recnetName": "国通",
	                "signFlag": 2,
	                "signResult": "",
	                "submitSignMsg": "1条添加失败！",
	                "uid": 268990431952896
	            },
	            {
	                "compId": 1536,
	                "cosignFlag": 12,
	                "createTime": 1490018374072,
	                "dateYMD": "",
	                "expMemberId": 212192318504960,
	                "expWaybillNum": "4444444444",
	                "mobilePhone": "4444444444",
	                "netId": 1536,
	                "parId": 22222222,
	                "recnetName": "国通",
	                "signFlag": 2,
	                "signResult": "",
	                "submitSignMsg": "1条添加失败！",
	                "uid": 268990431952896
	            }
	        ]
	    },
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/queryUploadFailParcelInfo", method={RequestMethod.POST, RequestMethod.GET})
	public String queryUploadFailParcelInfo(Long memberId){
		
		String result = submittedSignService.queryUploadFailParcelInfo(memberId);
		return result;
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/querySignRecordDetail 报签收-查询报签收记录详情
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-查询报签收记录详情
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} uid 主id
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiSampleRequest /submittedSign/querySignRecordDetail
	 * @apiSuccess {String} expWaybillNum  运单号
	 * @apiSuccess {String} recnetName  接收人网络名称
	 * @apiSuccess {String} recName  接收人姓名
	 * @apiSuccess {String} recMobile  接收人手机号
	 * @apiSuccess {String} signResult 签收类型
	 * @apiSuccess {String} recUnits 接收单位
	 * @apiSuccess {String} recAddress 接收地址
	 * @apiSuccess {String} submitSignMsg  失败原因
	 * @apiSuccess {Date} createTime  接收时间
	 * @apiSuccessExample Success-Response:
	  {
	    "data": {
	        "code": "",
	        "compId": 1536,
	        "cosignFlag": 12,
	        "createTime": 1490018374072,
	        "dateYMD": "",
	        "deliveryAddress": "",
	        "deliveryCarNumber": "",
	        "deliveryMobile": "",
	        "deliveryName": "",
	        "deliveryUnits": "",
	        "ecFlag": false,
	        "expMemberId": 212192318504960,
	        "expMemberSuccessFlag": "",
	        "expWaybillNum": "4444444444",
	        "mobilePhone": "4444444444",
	        "netFlag": false,
	        "netId": 1536,
	        "netName": "",
	        "packetsId": "",
	        "parId": 22222222,
	        "recAddress": "",
	        "recCarNumber": "",
	        "recCompId": "",
	        "recCosignFlag": "",
	        "recMemberId": "",
	        "recMobile": "",
	        "recName": "",
	        "recNetId": "",
	        "recUnits": "",
	        "recnetName": "国通",
	        "signFlag": 2,
	        "signResult": "",
	        "submitSignMsg": "1条添加失败！",
	        "taskId": "",
	        "terminalId": "",
	        "uid": 268990431952896,
	        "versionId": ""
	    },
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/querySignRecordDetail", method={RequestMethod.POST, RequestMethod.GET})
	public String querySignRecordDetail(Long uid, Long memberId){
		
		String result = submittedSignService.querySignRecordDetail(uid, memberId);
		return result;
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /submittedSign/updateSignBill 报签收-更改运单号(有包裹id -- parId)
	 * @apiVersion 5.0.4
	 * @apiDescription 报签收-更改运单号(有包裹id -- parId)
	 * @apiGroup 新版-好递-国通V4
	 * @apiParam {Long} newBill 新运单号
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {String} newPhone 新手机号
	 * @apiParam {String} parId 包裹id
	 * @apiSampleRequest /submittedSign/updateSignBill
	 * @apiSuccess {String} date 1:更改成功, 0:更改失败
	 * @apiSuccessExample Success-Response:
	  {
	    "data": "1",
	    "success": true
	  }
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/updateSignBill", method={RequestMethod.POST, RequestMethod.GET})
	public String querySignRecordDetail(String newBill , Long memberId, Long parId, String newPhone){
		if(PubMethod.isEmpty(newBill)){
			return paramsFailure("001", "newBill 不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空");
		}
		if(PubMethod.isEmpty(parId)){
			return paramsFailure("002", "parId 不能为空");
		}
		String result = submittedSignService.updateSignBill(newBill, memberId, parId, newPhone);
		return result;
	}
}
