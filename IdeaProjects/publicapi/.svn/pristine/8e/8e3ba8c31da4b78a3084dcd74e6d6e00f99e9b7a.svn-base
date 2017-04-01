package net.okdi.mob.controller;

import javax.xml.ws.Action;

import net.okdi.core.base.BaseController;
import net.okdi.mob.service.BroadcastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("couriserContrloller")
public class CouriserController extends BaseController{
	
	@Autowired
	private BroadcastService broadcastService;
	@RequestMapping("getAllOnLineMember")
	@ResponseBody
	public String getAllOnLineMember(){
    try {
    	return broadcastService.getAllOnLineMember();		
	} catch (Exception e) {
        return jsonFailure(e);
	}
	}

}
