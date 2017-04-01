package net.okdi.mob.controller;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.SendMsgToCustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 语音文件上传接口
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/soundUpload")
public class SendMsgToCustomerController extends BaseController {
	
	@Autowired
	SendMsgToCustomerService sendMsgToCustomerService;
	
	/**
	 * @api {post} /soundUpload/upSound 发送语音短信
	 * @apiPermission user
	 * @apiDescription  发送声音消息给客户 kai.yang
	 * @apiparam {String} phone 客户的手机号，订单号，编号,这三个字段用"—"拼接成一个整体，每个整体用"|"分隔开
	 * @apiparam {String} compId站点Id
	 * @apiparam {String} memberPhone 当前收派员的手机号
	 * @apiparam {Long} memberId 当前收派员的memberId
	 * @apiparam {MultipartFile[]} myfiles 传递过来的音频文件
	 * @apiparam {Short} isWaybillNum 是否插入订单号，0为插入，1为不插入
	 * @apiparam {Short} isIdentifier 是否插入编号，0为插入，1为不插入
	 * @apiGroup 免费短信
	 * @apiSampleRequest /soundUpload/upSound
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("upSound")
    public String upLoad(String phone,String compId,String memberPhone,Long memberId, @RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
    		Short isWaybillNum,Short isIdentifier){
    	try {
    		String str = sendMsgToCustomerService.smsAndUpSound(phone,compId,memberPhone,memberId, myfiles,isWaybillNum,isIdentifier);
    		return str;
		} catch (Exception e) {
            return jsonFailure(e);
		}
    }
	/**
	 * @api {post} /soundUpload/sendVoiceSms 发送语音短信新
	 * @apiPermission user
	 * @apiDescription  发送声音消息给客户 kai.yang
	 * @apiparam {String} phone 客户的手机号，订单号，编号,这三个字段用"—"拼接成一个整体，每个整体用"|"分隔开
	 * @apiparam {String} compId站点Id
	 * @apiparam {String} memberPhone 当前收派员的手机号
	 * @apiparam {Long} memberId 当前收派员的memberId
	 * @apiparam {MultipartFile[]} myfiles 传递过来的音频文件
	 * @apiparam {Short} isWaybillNum 是否插入订单号，0为插入，1为不插入
	 * @apiparam {Short} isIdentifier 是否插入编号，0为插入，1为不插入
	 * @apiGroup 免费短信
	 * @apiSampleRequest /soundUpload/sendVoiceSms
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("sendVoiceSms")
    public String sendVoiceSms(String phone,String compId,String memberPhone,Long memberId, @RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
    		Short isWaybillNum,Short isIdentifier){
    	try {
    		String str = sendMsgToCustomerService.sendVoiceSms(phone,compId,memberPhone,memberId, myfiles,isWaybillNum,isIdentifier);
    		return str;
		} catch (Exception e) {
            return jsonFailure(e);
		}
    }
	/**
	 * 删除几天前的语音信息，目前支持删除7天以上的
	 * @param day 需要删除几天前的数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delSound")
	public String del(Integer day){
		if(!PubMethod.isEmpty(day)&& day < 7 ){
			return paramsFailure("001","输入天数请大于7");
		}
		try {
			String str = sendMsgToCustomerService.delSound(day);
			return jsonSuccess(str);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}