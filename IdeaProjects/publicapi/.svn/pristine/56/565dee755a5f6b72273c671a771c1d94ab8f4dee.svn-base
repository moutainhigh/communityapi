package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.MessageInfoService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公告信息
 * @author jingguoqiang
 * @version V1.0
 */
@Controller
@RequestMapping("/messageInfo")
public class MessageInfoController extends BaseController {
	
	@Autowired 
	MessageInfoService messageInfoService;

	/**
	 * 查询最新公告时间
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMessageTime", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMessageTime() {
		try {
			return jsonSuccess(messageInfoService.queryMessageTime());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /messageInfo/queryInfoInWechat 获取该手机号的微信信息
	 * @apiVersion 0.2.0
	 * @apiDescription 获取该手机号的微信信息
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} mobilePhone 手机号
	 * @apiParam {String} firstMsgId firstMsgId唯一id
	 * @apiSampleRequest /messageInfo/queryInfoInWechat
	 * @apiSuccess {String} nickName 	微信昵称
	 * @apiSuccess {String} mobilePhone 	手机号
	 * @apiSuccess {String} headImage 	微信头像
	 * @apiSuccess {String} headImg 	快递员注册上传的头像
	 * @apiSuccess {String} weChatAddress 	微信地址信息
	 * @apiSuccess {String} ifRegisted 	是否是好递用户 0否1是
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "nickName": "水电费"
		        "mobilePhone": "所发生的分"
		        "headImage": "水电费水电费"
		        "weChatAddress": "水电费水电费"
		        "ifRegisted": "0"
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInfoInWechat", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryInfoInWechat(String firstMsgId, String mobilePhone) {
		try {
			return messageInfoService.queryInfoInWechat(firstMsgId, mobilePhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}