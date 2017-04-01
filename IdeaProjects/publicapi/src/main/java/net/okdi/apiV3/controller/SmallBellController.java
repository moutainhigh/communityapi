package net.okdi.apiV3.controller;

import java.math.BigDecimal;

import net.okdi.apiV3.service.SmallBellService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("smallBellController")
@RequestMapping("/smallBell")
public class SmallBellController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SmallBellController.class);

    @Autowired
	SmallBellService smallBellService;

	/**
	 * @author yangkai
	 * @api {post} /smallBell/query 铃铛查询接口
	 * @apiVersion 0.2.0
	 * @apiDescription 铃铛查询接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {Integer} pageNo 页码(不分页了,不用传)
	 * @apiParam {Integer} pageSize 每页显示条数(不分页了,不用传)
	 * 
	 * @apiSampleRequest /smallBell/query
	 * @apiSuccess {String} receiverPhone   接收电话
	 * @apiSuccess {String} sendContent   发送的内容
	 * @apiSuccess {Date} updateTime  根据发送结果更新
	 * @apiSuccess {Short} sendResult 回执状态    0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败
	 * @apiSuccess {String} msgId  发送的msgId(直接返回值)
	 * @apiSuccess {Long} memberId 快递员id
	 * @apiSuccess {String} memberPhone  快递员电话
	 * @apiSuccess {Short} flag 短信类型 1：是群发短信 2:电话不通补发短信 3.群呼电话
	 * @apiSuccess {String} sendStatus 发送状态  ：      0.发送成功  repeat.重复 reject.用户退订   -99.发送失败失败  1.呼叫成功 3.呼叫失败 
	 * @apiSuccess {Short} replyStatus 回复状态       0.未回复  1.未读 2.已读
	 * @apiSuccess {Short} isRead 是否已读       0.未读 1.已读
	 * @apiSuccess {String} headImage 客户头像(用户微信头像,没有则为空)
	 * @apiSuccess {Long} customerId 客户Id(没有为空)
	 * @apiSuccess {String} nickName 客户昵称(用户微信昵称,没有则为空)
	 * @apiSuccessExample Success-Response:
	   {
	    "data": {
	        "pageNo": 1,
	        "pageSize": 20,
	        "smsList": [
	            {
	                "createTime": "2016-10-21 14:51:19",
	                "customerId": 203725182648320,
	                "firstMsgId": "241757455695873",
	                "flag": 1,
	                "headImage": "http://wx.qlogo.cn/mmopen/Q3auHgzwzM6nnDLoKMPeh1IYRTkTUuFKEwxnLGl3tQkIHfUKufWyafgsWmGUvfttQhzsdloZcGRdJ4s2EBOl3w/0",
	                "id": "5809bae8e4b07fe181223f73",
	                "isRead": 1,
	                "isWeiXin": false,
	                "memberId": 179145206538240,
	                "memberPhone": "15011232453",
	                "msgId": "D4753416102114511900",
	                "nickName": "裴二龙",
	                "number": "",
	                "receiverPhone": "13581652368",
	                "replyStatus": 2,
	                "sendContent": "【好递】客户你好,10分钟后上门取件,如果不在家请提前通知。短信可回",
	                "sendResult": 1,
	                "sendStatus": "0",
	                "taskId": "",
	                "updateTime": "2016-10-21 14:51:23"
	            }
	        ]
	    },
	    "success": true
	}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/query", method={RequestMethod.POST, RequestMethod.GET})
	public String query(Long memberId, Integer pageSize,Integer pageNo) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(pageSize)) {
			pageSize = 20;
		}
		if (PubMethod.isEmpty(pageNo)) {
			pageNo = 1;
		}
		try {
			return smallBellService.query(memberId, pageSize, pageNo);
		} catch (Exception e) {
			return jsonFailure(e);
		}

	}
	/**
	 * @author yangkai
	 * @api {post} /smallBell/queryTaskRob 抢单查询接口
	 * @apiVersion 0.2.0
	 * @apiDescription 抢单查询接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {Integer} pageNo 页码
	 * @apiParam {Integer} pageSize 每页显示条数
	 * @apiSampleRequest /smallBell/queryTaskRob
	 * @apiSuccess {String} receiverPhone   接收电话
	 * @apiSuccess {String} receiverName   寄件人姓名
	 * @apiSuccess {String} sendContent   发送的内容
	 * @apiSuccess {Date} updateTime  根据发送结果更新
	 * @apiSuccess {Short} sendResult 回执状态    0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败
	 * @apiSuccess {String} msgId  发送的msgId(直接返回值)
	 * @apiSuccess {Long} memberId 快递员id
	 * @apiSuccess {String} memberPhone  快递员电话
	 * @apiSuccess {Short} flag 短信类型 1：是群发短信 2:电话不通补发短信 3.群呼电话
	 * @apiSuccess {String} sendStatus 发送状态  ：      0.发送成功  repeat.重复 reject.用户退订   -99.发送失败失败  1.呼叫成功 3.呼叫失败 
	 * @apiSuccess {Short} replyStatus 回复状态       0.未回复  1.未读 2.已读
	 * @apiSuccess {Short} isRead 是否已读       0.未读 1.已读
	 * @apiSuccess {Integer} taskSource 任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端,5:好递接单王,6:微信端
	 * @apiSuccess {Integer} taskFlag 0：正常,1：抢单
	 * @apiSuccessExample Success-Response:
	    {
	"data": {
		"pageNo": 1,
		"pageSize": 20,
		"smsList": [
		{
			"flag": 5,
			"id": 213500672917505,
			"isRead": 0,
			"memberId": 179145206538240,
			"memberPhone": "15011232453",
			"msgId": "213500670820352",
			"receiverPhone": "18655556620",
			"replyStatus": 0,
			"sendContent": "北京市海淀区环星大厦a座18655556620",
			"sendResult": "",
			"sendStatus": "",
			"taskId": 213500670820352,
			"updateTime": "2016-05-18 16:06:35",
			"taskSource":6,
			"taskFlag":1
		}]
	},
	"success": true
}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("queryTaskRob")
	public String queryTaskRob(Long memberId, Integer pageSize,Integer pageNo) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(pageSize)) {
			pageSize = 20;
		}
		if (PubMethod.isEmpty(pageNo)) {
			pageNo = 1;
		}
		try {
			return smallBellService.queryTaskRob(memberId, pageSize, pageNo);
		} catch (Exception e) {
			return jsonFailure(e);
		}
		
	}

    /**
     * @author yangkai
     * @api {post} /smallBell/deleteTaskRob 抢单删除接口
     * @apiVersion 0.2.0
     * @apiDescription 抢单删除接口
     * @apiGroup 说明
     * @apiParam {Long} id 单条数据的id
     * @apiSampleRequest /smallBell/deleteTaskRob
     * @apiSuccess {String} msg   成功信息

     * @apiSuccessExample Success-Response:
    {
    "data": {
        "msg": "删除成功"
    },
    "success": true
    }

     * @apiErrorExample Error-Response:
     *    {"errCode":0,
     *    "errSubcode":"001",
     *    "message":"memberId不能为空",
     *    "success":false}
     */
    @ResponseBody
    @RequestMapping("deleteTaskRob")
    public String deleteTaskRob(Long id) {
        if (PubMethod.isEmpty(id)) {
            return paramsFailure("001", "id不能为空");
        }
        try {
            return smallBellService.deleteTaskRob(id);
        } catch (Exception e) {
            return jsonFailure(e);
        }

    }

	/**
	 * @author yangkai
	 * @api {post} /smallBell/resend 铃铛重发接口
	 * @apiVersion 0.2.0
	 * @apiDescription 铃铛重发接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {String} msgIds msgId集合 用"-"拼起来
	 * @apiSampleRequest /smallBell/resend
	 * @apiSuccessExample Success-Response:
	    {"data"":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("resend")
	public String resend(Long memberId, String msgIds) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(msgIds)) {
			return paramsFailure("002", "msgIds不能为空");
		}
		try {
			return smallBellService.resend(memberId, msgIds);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	/**
	 * @author yangkai
	 * @api {post} /smallBell/delete 铃铛删除接口
	 * @apiVersion 0.2.0
	 * @apiDescription 铃铛删除接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {String} msgId 要删除的msgId
	 * @apiSampleRequest /smallBell/delete
	 * @apiSuccessExample Success-Response:
	    {"data"":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String delete(Long memberId, String msgId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(msgId)) {
			return paramsFailure("002", "msgId不能为空");
		}
		try {
			return jsonSuccess(smallBellService.delete(memberId, msgId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	
	/**
	 * @author yangkai
	 * @api {post} /smallBell/taskInfo 取件任务详情接口
	 * @apiVersion 0.2.0
	 * @apiDescription 取件任务详情接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {String} msgId 短信Id
	 * @apiParam {String} taskId 取件任务Id
	 * @apiParam {Integer} pageNo 页码
	 * @apiParam {Integre} pageSize 每页显示条数
	 * @apiSampleRequest /smallBell/taskInfo
	 * @apiSuccessExample Success-Response:
	    {"data":{"uid":210045927473152,"taskType":0,"coopCompId":175857817952256,"coopNetId":1503,
	    "parEstimateCount":1,"appointDesc":"iphone5se","taskStatus":0,"taskIsEnd":0,"taskSource":6,
	    "actorMemberId":203332845355008,"actorPhone":"18766669998","contactName":"郑炯",
	    "contactMobile":"13521283934","contactAddress":"北京海淀区","createTime":1461911444101,
	    "contactAddrLongitude":"0","contactAddrLatitude":"0","id":210045927473152,
	    "taskId":210045927473152,"thirdid":"osyqutwiVplVQPu27e-kDzbtJO8g"},
	    "success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("taskInfo")
	public String taskInfo(String memberId, String msgId ,String taskId,Integer pageNo,Integer pageSize) {
		if (PubMethod.isEmpty(memberId)) {	
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(taskId)) {
			return paramsFailure("002", "taskId不能为空");
		}
		if (PubMethod.isEmpty(pageNo)) {
			pageNo=1;
		}
		if (PubMethod.isEmpty(pageSize)) {
			pageSize=20;
		}
		try {
			String taskList= smallBellService.taskInfo(memberId,taskId);
			LOGGER.info("抢单详情 >> " + taskList);
			return taskList;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}

	
	/**
	 * @author yangkai
	 * @api {post} /smallBell/createTaskIgomo 创建取件任务
	 * @apiVersion 0.2.0
	 * @apiDescription 创建取件任务
	 * @apiGroup 说明
	 * @apiParam {Long} fromCompId 任务受理方站点
	 * @apiParam {Long} coopNetId 任务受理方网络
	 * @apiParam {String} appointDesc 取件备注
	 * @apiParam {Long} actorMemberId 执行人员
	 * @apiParam {String} contactName 发件人姓名
	 * @apiParam {String} contactMobile 发件人手机
	 * @apiParam {String} contactAddress 发件人详细地址
	 * @apiParam {BigDecimal} contactAddrLongitude 发件人地址的经度信息
	 * @apiParam {BigDecimal} contactAddrLatitude 发件人地址的纬度信息
	 * @apiParam {String} actorPhone 收派员电话
	 * @apiParam {String} openId 第三方ID（微信ID)
	 * @apiParam {Byte} taskSource 来源 3电商 6微信
	 * @apiParam {Long} memberId 创建人id
	 * @apiParam {String} parcelStr 
	 * @apiParam {Byte} taskFlag 
	 * @apiParam {Integer} howFast 
	 * @apiParam {Byte} parEstimateCount 
	 * @apiParam {Long} assignNetId 
	 * @apiSampleRequest /smallBell/createTaskIgomo
	 * @apiSuccessExample Success-Response:
	    {"data"":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/createTaskIgomo", method = { RequestMethod.GET,RequestMethod.POST })
	public String createTaskIgomo(Long fromCompId, Long coopNetId,String appointDesc,Long actorMemberId,String contactName,String contactMobile
			,String contactAddress,BigDecimal contactAddrLongitude,BigDecimal contactAddrLatitude,String actorPhone,String openId,Byte taskSource,
			Long memberId,String parcelStr,Byte taskFlag,Integer howFast,Byte parEstimateCount,Long assignNetId
			) {
		try{
			return smallBellService.createTaskIgomo(fromCompId,coopNetId,appointDesc,actorMemberId,contactName,contactMobile,
					contactAddress,contactAddrLongitude,contactAddrLatitude,actorPhone,openId,taskSource,memberId,parcelStr,
					taskFlag,howFast,parEstimateCount,assignNetId);
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * @author yangkai
	 * @api {post} /smallBell/queryCount 铃铛消息总数查询接口
	 * @apiVersion 0.2.0
	 * @apiDescription 铃铛消息总数查询接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiSampleRequest /smallBell/queryCount
	 * @apiSuccess {String} data  消息数量
	 * @apiSuccessExample Success-Response:
	   {"data":2,"success":true}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCount", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryCount(Long memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		try {
			return jsonSuccess(smallBellService.queryCount(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}

	}
	/**
	 * @author yangkai
	 * @api {post} /smallBell/queryRobCount 抢单消息总数查询接口
	 * @apiVersion 0.2.0
	 * @apiDescription 抢单消息总数查询接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiSampleRequest /smallBell/queryRobCount
	 * @apiSuccess {String} data  消息数量
	 * @apiSuccessExample Success-Response:
	   {"data":2,"success":true}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRobCount", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryRobCount(Long memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		try {
			return jsonSuccess(smallBellService.queryRobCount(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
		
	}
	
	/**
	 * @author panke.sun
	 * @api {post} /smallBell/deleteMessage 铃铛消息删除
	 * @apiVersion 0.2.0
	 * @apiDescription 铃铛消息删除
	 * @apiGroup 说明
	 * @apiParam {String} memberId 用户ID
	 * @apiParam {String} msgId 短信msgId
	 * @apiSampleRequest /smallBell/deleteMessage
	 * @apiSuccess {String} data  1:删除成功,0:删除失败
	 * @apiSuccessExample Success-Response:
	   {"data":1,"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMessage", method = { RequestMethod.GET,RequestMethod.POST })
	public String deleteMessage(String msgId,String memberId) {
		if (PubMethod.isEmpty(msgId)) {
			return paramsFailure("001", "msgId不能为空");
		}
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("002", "memberId不能为空");
		}
		try {
			return smallBellService.deleteMessage(msgId,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
		
	}
	
	
	
}
