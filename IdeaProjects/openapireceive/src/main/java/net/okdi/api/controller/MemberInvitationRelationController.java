package net.okdi.api.controller;

import net.okdi.api.service.MemberInvitationService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/memberInvitationRelation")
public class MemberInvitationRelationController extends BaseController {
	@Autowired
	private MemberInvitationService memberInvitationService;
	
	@ResponseBody
	@RequestMapping(value = "/createRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String createRelation(Long memberId,String phone,String shopId) {
		try {
			this.memberInvitationService.createRelation(memberId, phone, shopId);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

}
