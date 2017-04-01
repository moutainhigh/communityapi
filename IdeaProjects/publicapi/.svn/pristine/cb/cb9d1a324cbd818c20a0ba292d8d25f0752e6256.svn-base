package net.okdi.apiV4.controller;

import net.okdi.apiV3.service.SmallBellService;
import net.okdi.apiV4.service.WrongPriceService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("wrongPriceController")
@RequestMapping("/wrongPrice")
public class WrongPriceController extends BaseController{
	
	Logger logger = Logger.getLogger(WrongPriceController.class);
	@Autowired
	WrongPriceService wrongPriceService;

	@Autowired
	SmallBellService bellService;
	
	/**
	 * @author jiong.zheng
	 * @api {post} /wrongPrice/query 发送失败查询接口
	 * @apiVersion 0.2.0
	 * @apiDescription 发送失败查询接口
	 * @apiGroup 问题件
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {Integer} pageNo 页码
	 * @apiParam {Integer} pageSize 每页显示条数
	 * @apiParam {String} flag :传空就行
	 * @apiSampleRequest /wrongPrice/query
	 * @apiSuccess {String} receiverPhone   接收电话
	 * @apiSuccess {String} sendContent   发送的内容
	 * @apiSuccess {Date} updateTime  根据发送结果更新
	 * @apiSuccess {Short} sendResult 回执状态    0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败
	 * @apiSuccess {String} msgId  发送的msgId(直接返回值)
	 * @apiSuccess {Long} memberId 快递员id
	 * @apiSuccess {String} memberPhone  快递员电话
	 * @apiSuccess {Short} flag 短信类型 1：是群发短信 2:电话不通补发短信 3.群呼电话 9.淘宝单
	 * @apiSuccess {String} sendStatus 发送状态  ：      0.发送成功  repeat.重复 reject.用户退订   -99.发送失败失败  1.呼叫成功 3.呼叫失败 
	 * @apiSuccess {Short} replyStatus 回复状态       0.未回复  1.客户回复 2.回复已读
	 * @apiSuccess {Short} isRead 回复状态       0.未读 1.已读 
	 * @apiSuccess {Boolean} isWeiXin 是否绑定微信       （true：是，false：不是）
	 * @apiSuccess {String} number 编号
	 * @apiSuccess {String} netId 网络id
	 * @apiSuccess {String} netName 普通单这是空值; 淘宝单这是网络名称
	 * @apiSuccess {String} waybill 运单号
	 * @apiSuccess {String} name 姓名
	 * @apiSuccessExample Success-Response:
	    {"data":
	    {"pageNo":1,"pageSize":20,"smsList":
	    	[{"flag":1,
	    	"id":"56f21196e4b0c37ed145fb78",
	    	"memberId":1531386262800009,
	    	"memberPhone":"18611788996",
	    	"msgId":"201603231146301870",
	    	"firstMsgId":"201603231146301870",
	    	"receiverPhone":"18611788996",
	    	"replyStatus":1,
	    	"sendContent":"【好递爱购猫】客户你好，您的快件将在10分钟内派送中，请准备收件~ 可回，屏蔽短信回1",
	    	"sendResult":1,
	    	"isRead":0,
	    	"sendStatus":"0",
	    	"updateTime":"2016-03-23 11:46:34",
	    	"waybill":"111111",
	    	"name":"张三"}]},
	    "success":true}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("query")
	public String query(Long memberId, Integer pageSize,Integer pageNo, String flag) {
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
			return wrongPriceService.query(memberId, pageSize, pageNo, flag);
		} catch (Exception e) {
			return jsonFailure(e);
		}

	}
	/**
	 * @author yangkai
	 * @api {post} /wrongPrice/queryCount 首页数量查询接口
	 * @apiVersion 0.2.0
	 * @apiDescription 发送失败查询接口
	 * @apiGroup 问题件
	 * @apiParam {Long} memberId 用户ID
	 * @apiSampleRequest /wrongPrice/query
	 * @apiSuccessExample Success-Response:
	 * {}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("queryCount")
	public String queryCount(Long memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		try {
			return jsonSuccess(wrongPriceService.queryWrong(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /wrongPrice/aliResend 淘宝单重发接口
	 * @apiVersion 0.2.0
	 * @apiDescription 淘宝单重发接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {String} msgIds msgId集合 用"-"拼起来
	 * @apiSampleRequest /smallBell/aliResend
	 * @apiSuccessExample Success-Response:
	    {"data"":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("aliResend")
	public String aliResend(Long memberId, String msgIds) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(msgIds)) {
			return paramsFailure("002", "msgIds不能为空");
		}
		try {
			return wrongPriceService.aliResend(memberId, msgIds);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	
	/**
	 * @author jiong.zheng
	 * @api {post} /wrongPrice/wrongResend 问题件重发接口
	 * @apiVersion 0.2.0
	 * @apiDescription 问题件重发接口
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {String} msgIds 普通msgId集合 用"-"拼起来
	 * @apiParam {String} aliMsgIds 淘宝单msgId集合 用"-"拼起来
	 * @apiSampleRequest /wrongPrice/wrongResend
	 * @apiSuccessExample Success-Response:
	    {"data"":"发送成功","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("wrongResend")
	public String wrongResend(Long memberId, String msgIds, String aliMsgIds) {
		logger.info("问题件重发接口********memberId: "+memberId+" ,普通单msgIds: "+msgIds+" ,淘宝单aliMsgIds: "+aliMsgIds);
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(msgIds) && PubMethod.isEmpty(aliMsgIds)) {
			return paramsFailure("002", "普通单或者菜鸟单不能同时为空");
		}
		try {
			String result="";
			String aliResult="";
			if(!PubMethod.isEmpty(msgIds)){
				result = bellService.resend(memberId, msgIds);
			}
			if(!PubMethod.isEmpty(aliMsgIds)){
				aliResult = wrongPriceService.aliResend(memberId, aliMsgIds);
			}
			System.out.println("普通问题件发送返回的结果result:"+result+" ,淘宝单发送返回的结果aliResult: "+aliResult);
			return jsonSuccess("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
}
