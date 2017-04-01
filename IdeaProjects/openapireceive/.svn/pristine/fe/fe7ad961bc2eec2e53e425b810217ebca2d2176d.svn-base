package net.okdi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.okdi.api.service.SensitiveWordService;
import net.okdi.core.base.BaseController;


/**
 * 敏感词方法
 * 2015 08 20
 * @author kai.yang
 * @version V1.0
 * 去数据库里查询所有的敏感词,只查一次
 */
@Controller
@RequestMapping("/sensitiveWord")
public class SensitiveWordController extends BaseController{

	@Autowired
	private SensitiveWordService sensitiveWordService;

	//查询黑名单库
	@ResponseBody
	@RequestMapping(value = "/queryBlackList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryBlackList(){
		try {
			return JSON.toJSONString(this.sensitiveWordService.queryBlackList());
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	//查询白名单库
	@ResponseBody
	@RequestMapping(value = "/queryWhiteList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryWhiteList(){
		try {
			return JSON.toJSONString(this.sensitiveWordService.queryWhiteList());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	//添加关键字
	@ResponseBody
	@RequestMapping(value = "/insertSensitiveWord", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertSensitiveWord(String sensitiveWord,String type){
		try {
			return JSON.toJSONString(this.sensitiveWordService.insertSensitiveWord(sensitiveWord,type));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	//删除关键字
	@ResponseBody
	@RequestMapping(value = "/deleteSensitiveWord", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteSensitiveWord(String sensitiveWord,String type){
		try {
			return JSON.toJSONString(this.sensitiveWordService.deleteSensitiveWord(sensitiveWord,type));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	//模糊查询关键字
	@ResponseBody
	@RequestMapping(value = "/likeSensitiveWord", method = { RequestMethod.POST, RequestMethod.GET })
	public String likeSensitiveWord(String sensitiveWord,String type){
		try {
			return JSON.toJSONString(this.sensitiveWordService.likeSensitiveWord(sensitiveWord,type));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	//记录错误次数 错一次+1
	@ResponseBody
	@RequestMapping(value = "/addWrongNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public String addWrongNumber(Long memberId,String phone,String sendContent){
		try {
			return jsonSuccess(this.sensitiveWordService.addWrongNumber(memberId,phone,sendContent));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 查询错误次数，判断是否可以发短信
	 * @param memberId 用户id
	 * @param phone 发送号码
	 * @param sendContent 发送内容
	 */
	@ResponseBody
	@RequestMapping(value = "/queryWrongNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryWrongNumber(Long memberId){
		try {
			return jsonSuccess(this.sensitiveWordService.queryWrongNumber(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	//发送成功，重置错误次数
	@ResponseBody
	@RequestMapping(value = "/removeWrongNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public void removeWrongNumber(String memberId){
		try {
			this.sensitiveWordService.removeWrongNumber(memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
