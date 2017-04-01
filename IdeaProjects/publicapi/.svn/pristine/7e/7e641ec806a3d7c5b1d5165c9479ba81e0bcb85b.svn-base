package net.okdi.schedule.controller;

import net.okdi.schedule.PushSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sche")
public class TestSchedule {

	@Autowired
	PushSchedule pushSchedule;
	
	@ResponseBody
	@RequestMapping(value = "/pushTimer", method = { RequestMethod.POST })
	public String pushTimer(){
		pushSchedule.startPush();
		return "success";
	}
}
