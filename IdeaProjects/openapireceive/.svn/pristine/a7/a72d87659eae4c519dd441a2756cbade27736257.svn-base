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

import net.okdi.api.service.CountNumService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

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
	private CountNumService countNumService;
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>统计站点、收派员、好递超市、好递生活注册数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-02 上午11:23:50</dd>
	 * @param countType 1：统计总数 2：当前月注册总数 3：当前月每日注册总数
	 * @return
	 * @since v1.0
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
    		return JSON.toJSONString(countNumService.getRegisterNum(countType));	
		} catch (Exception e) {
			return null;
		}
    }
	
}