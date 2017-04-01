package net.okdi.apiV3.controller;

import net.okdi.api.service.TakeTaskService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/take")
public class TakeController extends BaseController {

	@Autowired
	TakeTaskService takeTaskService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根绝取件任务id查询取件任务详情(不分页)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>yangkai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:22:45</dd>
	 * @param memberId 收派员id
	 * @param taskId
	 * @return {"data":{"resultlist":[{"appointDesc":"","contactAddress":"北京-海淀区 田村路","contactMobile":"13800138000","contactName":"张三","createTime":1415774210000,"memberId":3111394703898098,"taskId":14161141757117440}]},"success":true}
	 *appointDesc 取件备注   contactAddress详细地址 contactMobile联系人手机  contactName联系人姓名  memberId处理人id taskid任务ID createTime创建时间
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInfo", method = {RequestMethod.GET , RequestMethod.POST })
	public String queryInfo(Long taskId) {		
		//查询收派员未处理任务
		try{
			return jsonSuccess(takeTaskService.queryTakeById(taskId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
}