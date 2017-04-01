/**  
 * @Project: openapi
 * @Title: SendParcelController.java
 * @Package net.okdi.api.controller
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-7 下午02:41:18
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;

import net.okdi.api.service.SendParcelService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/sendParcel")
public class SendParcelController extends BaseController{

	@Autowired
	private SendParcelService sendParcelService;
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>发快递获得默认联系人</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-26 上午09:36:08</dd>
     * @param memberId 操作人ID
     * @return {"success":true}
     * @since v1.0
     */
	@Deprecated
	@RequestMapping("/queryDefulatAddress")
	@ResponseBody
	public String queryDefulatAddress(Long memberId){
		try {
			return sendParcelService.quertDefulatAddress(memberId+"");	
		} catch (RuntimeException re) {
            return this.jsonFailure(re);
		}
		
	}

}
