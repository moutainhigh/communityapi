package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.TaskRecordService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author jingguoqiang
 * @desc  取派件和通知数量查询类
 */
@Controller
@RequestMapping("/taskRecord")
public class TaskRecordController extends BaseController {

	@Autowired
	private TaskRecordService tcaskRecordService;
    
	
	/**
	 * @api {post} /taskRecord/queryCount 今天取派件和通知数量--静国强
	 * @apiVersion 0.3.0
	 * @apiDescription 今天取派件和通知数量--静国强
	 * @apiGroup 新版-注册
	 * @apiParam {String} memberId 
	 * @apiSampleRequest /taskRecord/queryCount
	 * @apiSuccess {String} data： noticeCount：通知数量 receiveCount：取件数量  sendCount：派件数量
	 * @apiSuccessExample Success-Response:
	    {
         "data": {
           "noticeCount": "0",
          "receiveCount": "0",
          "sendCount": "0"
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
	@RequestMapping(value = "/queryCount", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryCount(String memberId) {
		
		try {
			String receiveCount = this.queryReceiveCount(memberId);
			String sendCount = this.querySendCount(memberId);
			String noticeCount = this.queryNoticeCount(memberId);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("receiveCount", receiveCount);
			map.put("sendCount", sendCount);
			map.put("noticeCount", noticeCount);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	

	
	@ResponseBody
	@RequestMapping(value = "/queryReceiveCount", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryReceiveCount(String memberId) {
		
		try {
			String count = tcaskRecordService.queryReceiveCount(memberId);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	

	
	@ResponseBody
	@RequestMapping(value = "/querySendCount", method = { RequestMethod.POST,RequestMethod.GET })
	public String querySendCount(String memberId) {
		
		try {
			String count = tcaskRecordService.querySendCount(memberId);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/queryNoticeCount", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryNoticeCount(String memberId) {
		try {
			String count = tcaskRecordService.queryNoticeCount(memberId);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	
}
