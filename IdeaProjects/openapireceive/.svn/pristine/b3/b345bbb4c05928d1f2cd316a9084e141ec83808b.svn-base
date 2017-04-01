package net.okdi.apiV1.controller;

import java.util.Map;

import net.okdi.apiV1.service.IAttributionService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/attribution")
public class AttributionController extends BaseController {

	@Autowired
	private IAttributionService iattributionService;

	@ResponseBody
	@RequestMapping(value = "/findShopowner", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String findShopowner(String memberId,String roleId) {
		
		try {
			return jsonSuccess(this.iattributionService.findShopowner(memberId,roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}
