/**  
 * @Project: openapi
 * @Title: CountNunController.java
 * @Package net.okdi.api.controller
 * @author amssy
 * @date 2015-4-28 下午6:20:07
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.TaskHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("")
public class CountNumController extends BaseController {

	@Autowired
	private TaskHttpClient taskHttpClient;

	/**
	 *  @api {get} /countUserNumber 统计用户数
	 *  @apiPermission user
	 *  @apiDescription 根据账户号，系统外部ID，校验密码是否正确 （密码为加密后的）
	 *  @apiGroup ACCOUNT 创建账号业务
	 *  @apiSampleRequest /countUserNumber
	 *  @apiParam  {String} countType  1：统计总数 2：当前月注册总数 3：当前月每日注册总数
	 *  @apiSuccess {String} result true
	 *  @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"RESULT":"success","DATA":{"check":"ok"}}
	 *  @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"RESULT":"error","DATA":{"accountCard":"账户号不能为空<\/a>"}}
	 * @apiVersion 0.1.0
	 */
	@RequestMapping("/countUserNumber")
	@ResponseBody
    public String countUserNumber(Integer countType){
		if(PubMethod.isEmpty(countType)){
			return JSON.toJSONString("countType is not null");
		}
		if(countType!=1&&countType!=2&&countType!=3){
			return JSON.toJSONString("countType is error");
		}
    	try {
    		return taskHttpClient.countUserNumber(countType);
		} catch (Exception e) {
			return jsonFailure(e);
		}
    }
	
}