package net.okdi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.api.service.ShopMemberPushSetService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller
@RequestMapping("/shopMemberPushSet")
public class ShopMemberPushSetController extends BaseController {

	@Autowired
	ShopMemberPushSetService shopMemberPushSetService;
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置(修改抢单模式)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>caina.cun</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-23 下午14:36:00</dd>
	 * @param memberId	收派员ID
	 * @param pushStatus	0开启 1关闭
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pushStatus", method = { RequestMethod.POST,RequestMethod.GET })
	public String pushStatus(Long memberId, Short pushStatus){
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure("openapi.shopMemberPushSet.pushStatus","memberId不能为空");
			}
		try {
			this.shopMemberPushSetService.pushStatus(memberId, pushStatus);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>caina.cun</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-23 下午14:36:00</dd>
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryShopMemberPushSetById", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryShopMemberPushSetById(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("openapi.shopMemberPushSet.queryShopMemberPushSetById","memberId不能为空");
		}
		try {
			return jsonSuccess(this.shopMemberPushSetService.queryShopMemberPushSetById(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
