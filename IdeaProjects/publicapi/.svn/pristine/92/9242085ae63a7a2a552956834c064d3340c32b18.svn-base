package net.okdi.mob.controller;

import net.okdi.core.base.BaseController;
import net.okdi.mob.service.PushService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("pushMsg")
public class PushController extends BaseController{
    @Autowired
	private PushService pushService;
	
    @RequestMapping("initBadge")
    @ResponseBody
	public String initBadge(String deviceToken){
    	try {
    		return	pushService.initBadge(deviceToken);	
		} catch (Exception e) {
            return jsonFailure(e);
		}
	}
}
