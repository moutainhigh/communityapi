package net.okdi.schedule;

import net.okdi.mob.service.SendMsgToCustomerService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DelSchedule {
	
	@Autowired
	SendMsgToCustomerService sendMsgToCustomerService;
	
	Logger logger = Logger.getLogger(DelSchedule.class);
	
	private Integer day = 0;
	
	public void del(){
		logger.debug("要被删掉了，呜呜呜~");
		sendMsgToCustomerService.delSound(day);
	}
}
