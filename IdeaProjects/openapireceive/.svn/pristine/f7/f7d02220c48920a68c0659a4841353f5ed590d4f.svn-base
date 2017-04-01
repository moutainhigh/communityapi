package net.okdi.core.common.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobListener extends JobListenerSupport{

	private static final Logger logger = LoggerFactory.getLogger(JobListener.class);
	
	@Override
	public String getName() {
		return JobListener.class.getName();
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		try {
			Date nextFireTime = context.getTrigger().getNextFireTime();
			logger.info("nextFireTime: {}", nextFireTime);
			if (nextFireTime == null) {
				Scheduler scheduler = context.getScheduler();
				JobKey jobKey = context.getJobDetail().getKey();
				scheduler.pauseTrigger(context.getTrigger().getKey());
				boolean isUnschedule = scheduler.unscheduleJob(context.getTrigger().getKey());
				boolean isDeleted = scheduler.deleteJob(jobKey);
				logger.info("scheduler({}) 删除job({})", isUnschedule + "." + isDeleted + ". " + 
						scheduler.getSchedulerInstanceId(), jobKey);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("删除job失败..");
		}
	}
}





