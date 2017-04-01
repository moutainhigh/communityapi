package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.WeiChatPubNoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("weiChatPubNoController")
@RequestMapping("weiChatPubNo")
public class WeiChatPubNoController extends BaseController{
	
	@Autowired
	private WeiChatPubNoService weiChatPubNoService;
	
	/**
	 * @author erlong.pei
	 * @api {get} /weiChatPubNo/queryWeichatKey 根据memberId查询微信二维码url
	 * @apiVersion 0.2.0
	 * @apiDescription 根据memberid查询微信二维码url
	 * @apiGroup 微信公共号
	 * @apiParam {String} memberId 邀请人的memberId
	 * @apiSampleRequest /weiChatPubNo/queryWeichatKey
	 * @apiSuccess {String} url  微信二维码url
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "url": "http://weixin.qq.com/q/Lzocd1nlCCdxOSMJLhJb"
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
	@RequestMapping(value = "/queryWeichatKey", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryWeichatKey(String memberId){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryWeichatKey.001", "memberId不能为空");
			}
			return weiChatPubNoService.findWeichatkeyBymemberId(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
}
