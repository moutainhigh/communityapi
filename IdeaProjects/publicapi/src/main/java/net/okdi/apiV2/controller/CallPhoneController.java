package net.okdi.apiV2.controller;

import net.okdi.apiV2.service.CallPhoneService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/callPhone")
public class CallPhoneController extends BaseController {

	@Autowired
	private CallPhoneService callPhoneService;
	/**
	 * @api {post} /callPhone/callPhoneByPhone 拨打电话--郑炯(已不用)
	 * @apiVersion 0.3.0
	 * @apiDescription 拨打电话-郑炯
	 * @apiGroup ACCOUNT 拨打电话
	 * @apiParam {String} caller 主叫电话
	 * @apiParam {String} callee 被叫电话
	 * @apiSampleRequest /callPhone/callPhoneByPhone
	 * @apiSuccess {String} result 返回值:0 代表成功, 1:代表失败, description:代表失败原因
	 * @apiSuccessExample Success-Response:
	  {
	    "result": "0",
	    "uniqueId": "ebfa05b642043cb9ffb56ec5b3b51d1c",
	    "clid": "01089177643",
	    "description": "提交成功"
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
	@RequestMapping(value="/callPhoneByPhone",method={RequestMethod.POST,RequestMethod.GET})
	public String callPhoneByPhone(String caller, String callee){
		
		String result = callPhoneService.callPhoneByPhone(caller, callee);
		return result;
	}
	
	/**
	 * 外呼回调接口(已不用)
	 * @param id 
	 * @param falg 20——TTS合成失败；21——外呼未接通；22——外呼接通未转接；23——转接未接通；24——成功
	 * @param duration 通话时长(单位/秒)
	 * @param answerTime 接听开始时间(单位/linux 毫秒)
	 * @param endTime 接听挂断时间(单位/linux 毫秒)
	 * @return 001:成功, 002:失败, 003:id 不能为空
	 */
	
	@ResponseBody
	@RequestMapping(value="/backPhoneToValue",method={RequestMethod.POST,RequestMethod.GET})
	public String backPhoneToValue(String id, Short flag, String duration, String answerTime, String endTime){
		String result = callPhoneService.backPhoneToValue(id, flag, duration, answerTime, endTime);
		return result;
	}
	
	/**
	 * @api {post} /callPhone/queryPhoneRecordList 查询通话记录列表--郑炯(已不用)
	 * @apiVersion 0.3.0
	 * @apiDescription 拨打电话-郑炯
	 * @apiGroup ACCOUNT 拨打电话
	 * @apiParam {String} caller 主叫电话
	 * @apiSampleRequest /callPhone/queryPhoneRecordList
	 * @apiSuccess {String} answerTime 接听时间
	 * @apiSuccess {String} caller 主叫电话
	 * @apiSuccess {String} callee 被叫电话
	 * @apiSuccess {String} createTime 创建时间
	 * @apiSuccess {String} duration 通话时长
	 * @apiSuccess {String} endTime 挂段时间
	 * @apiSuccess {Short} flag 通话状态 20——TTS合成失败；21——外呼未接通；22——外呼接通未转接；23——转接未接通；24——成功
	 * @apiSuccess {String} id 主键id
	 * @apiSuccessExample Success-Response:
		  {
		    "data": [
		        {
		            "answerTime": "",
		            "callee": "18511586957",
		            "caller": "15810885211",
		            "createTime": 1451285699145,
		            "duration": "00:00",
		            "endTime": 1451285732000,
		            "flag": 21,
		            "id": 187762125094912
		        },
		        {
		            "answerTime": "",
		            "callee": "18511586957",
		            "caller": "15810885211",
		            "createTime": 1451285702021,
		            "duration": "00:00",
		            "endTime": 1451285725000,
		            "flag": 21,
		            "id": 187762131386369
		        }
		    ],
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
	@RequestMapping(value="/queryPhoneRecordList",method={RequestMethod.POST,RequestMethod.GET})
	public String queryPhoneRecordList(String caller){
		if(PubMethod.isEmpty(caller)){
			return "machinePhone is not null";
		}
		String result = callPhoneService.queryPhoneRecordList(caller);
		return result;
	}
	
}
