package net.okdi.core.common.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobExecutor {

	private ScheduledBuilder scheBuilder = ScheduledBuilder.newSchedule();
	private static final Logger logger = LoggerFactory.getLogger(JobExecutor.class);
	
	public JobExecutor() {
	}
	
	public void execute(Class<? extends GenericJob> job) {
		scheBuilder.scheduleJob(job);
	}
	
	public void execute(Class<? extends GenericJob> job, Date startTime) {
		scheBuilder.scheduleJob(job, startTime);
	}
	
	public void execute(Class<? extends GenericJob> job, Date startTime, Map<String, Object> dataMap) {
		scheBuilder.scheduleJob(job, startTime, dataMap);
	}
	
	public void executeOnlyOnce(Class<? extends GenericJob> job, Date startTime, Map<String, Object> dataMap) {
		scheBuilder.scheduleJobForOnce(job, startTime, dataMap);
	}
	
	/**
	 * 删除定时任务
	 * @param jobName	: 任务名称, 即订单Id
	 */
	public void deleteJob(Class<? extends GenericJob> job, String jobName) {
		scheBuilder.deleteJob(job, jobName);
	}
	
	public void executeOnStartup(final List<GenericJob> jobList) {
		new Thread(){
			public void run() {
				scheBuilder.scheduleJob(jobList);
				logger.info("定时任务已注册完毕..");
			};
		}.start();
	}
}








