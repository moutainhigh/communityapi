package net.okdi.core.common.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledBuilder {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledBuilder.class);
	
	private static final String RANDOMCHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	protected ScheduledBuilder(){
	}
	
	public static ScheduledBuilder newSchedule() {
		return new ScheduledBuilder();
	}
	
	/**
	 * 初始化JobDetail对象
	 * @param job
	 * @return
	 */
	private JobDetail initJobDetail(Class<? extends GenericJob> job) {
		return initJobDetail(job, null);
	}
	
	/**
	 * 初始化JobDetail对象
	 * @param job
	 * @return
	 */
	private JobDetail initJobDetail(Class<? extends GenericJob> job, String jobName) {
		if (null == jobName || "".equals(jobName) || "null".equals(jobName)) {
			jobName = randomString();
		}
		String jobPkgName = job.getPackage().getName();
		JobDetail jobDetail = JobBuilder.newJob(job)
				.withIdentity(job.getSimpleName() + "." + jobName, jobPkgName)
				.build();
		return jobDetail;
	}
	
	/**
	 * 初始化CronTriggerBuilder对象
	 * @param jobDetail
	 * @return
	 */
	private TriggerBuilder<CronTrigger> initTrigger(JobDetail jobDetail) {
		CronExpression cron = jobDetail.getJobClass().getAnnotation(CronExpression.class);
		TriggerBuilder<CronTrigger> tb = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(cron.value()))
				.forJob(jobDetail);
		return tb;
	}
	
	/**
	 * 初始化SimpleTrigger
	 * @param jobDetail
	 * @param repeatCount
	 * @return
	 */
	private TriggerBuilder<SimpleTrigger> initSimpleTrigger(JobDetail jobDetail, int repeatCount) {
		SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule();
		if (repeatCount == -1) {
			schedBuilder.repeatForever();
		} else {
			schedBuilder.withRepeatCount(repeatCount);
		}
		TriggerBuilder<SimpleTrigger> tb = TriggerBuilder.newTrigger()
				.withSchedule(schedBuilder)
				.withIdentity(jobDetail.getKey().getName());
		return tb;
	}
	
	/**
	 * 执行给定的定时任务
	 * 
	 * @param job
	 *            : 定时任务类对象
	 * @return
	 */
	public Scheduler scheduleJob(Class<? extends GenericJob> job) {
		return scheduleJob(job, new Date(), new HashMap<String, Object>());
	}
	
	/**
	 * 在指定的时间执行定时任务
	 * 
	 * @param job
	 *            : 定时任务类对象
	 * @param startTime
	 *            : 执行时间
	 * @return
	 */
	public Scheduler scheduleJob(Class<? extends GenericJob> job, Date startTime) {
		return scheduleJob(job, startTime, new HashMap<String, Object>());
	}
	
	/**
	 * 在指定的时间执行定时任务, 并将参数放到JobDataMap中
	 * @param job
	 *            : 定时任务类对象
	 * @param startTime
	 *            : 执行时间
	 * @param dataMap
	 * @return
	 */
	public Scheduler scheduleJob(Class<? extends GenericJob> job, Date startTime, Map<String, Object> dataMap) {
		Scheduler scheduler = null;
		if (null == startTime) {
			startTime = new Date();
		}
		if (null == dataMap) {
			dataMap = new HashMap<>();
		}
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			JobDetail jobDetail = initJobDetail(job);
			TriggerBuilder<CronTrigger> tb = initTrigger(jobDetail);
			
			CronTrigger trigger = tb.startAt(startTime).build();
			
			//填充参数值
			JobDataMap jdm = trigger.getJobDataMap();
			for (Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				jdm.put(key, dataMap.get(key));
			}
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("init failed, {}: {}", job.getClass(), e.getMessage());
		}
		return scheduler;
	}
	
	public void scheduleJob(List<GenericJob> jobs) {
		for (GenericJob job : jobs) {
			scheduleJob(job.getClass());
		}
	}
	
	/**
	 * 在指定的时间执行定时任务, 只一次
	 * @param job
	 * @param startTime
	 * @param dataMap
	 * @return
	 */
	public Scheduler scheduleJobForOnce(Class<? extends GenericJob> job, Date startTime, Map<String, Object> dataMap) {
		return scheduleJobForRepeatCount(job, startTime, dataMap, 0);
	}
	
	public Scheduler scheduleJobForRepeatCount(Class<? extends GenericJob> job, Date startTime,
											   Map<String, Object> dataMap, int repeatCount) {
		Scheduler scheduler = null;
		if (null == startTime) {
			startTime = new Date();
		}
		if (null == dataMap) {
			dataMap = new HashMap<>();
		}
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			//JobDetail jobDetail = JobBuilder.newJob(job).build();
			Object jobNameInMap = dataMap.get("jobSimpleName");
			String jobName = jobNameInMap == null ? randomString() : String.valueOf(jobNameInMap);
			JobDetail jobDetail = initJobDetail(job, jobName);
			TriggerBuilder<SimpleTrigger> triggerBuilder = initSimpleTrigger(jobDetail, repeatCount);
			triggerBuilder.startAt(startTime);
			
			JobDataMap jdm = new JobDataMap();
			for (Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				jdm.put(key, dataMap.get(key));
			}
			SimpleTrigger simpleTrigger = triggerBuilder.usingJobData(jdm).build();
			scheduler.getListenerManager().addJobListener(new JobListener());
			scheduler.scheduleJob(jobDetail, simpleTrigger);
			logger.info("定时任务{}注册成功..", jobDetail.getKey());
		}catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("定时任务注册失败, {}: {}", job, e.getMessage());
		}
		return scheduler;
	}
	
	/**
	 * 删除定时任务
	 * @param jobName	: 定时任务名称, 即订单id
	 * @return
	 */
	public boolean deleteJob(Class<? extends GenericJob> job, String jobName) {
		boolean flag = false;
		JobKey jobKey = null;
		if (job == null) {
			return false;
		}
		if (null == jobName || "".equals(jobName) || "null".equals(jobName)) {
			jobName = randomString();
		}
		String jobPkgName = job.getPackage().getName();
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			String name = job.getSimpleName() + "." + jobName;
			jobKey = JobKey.jobKey(name, jobPkgName);
			TriggerKey triggerKey = TriggerKey.triggerKey(name);
			scheduler.pauseTrigger(triggerKey);
			scheduler.unscheduleJob(triggerKey);
			System.out.println("手动删除, job和触发器, " + jobKey + ";" + triggerKey);
			flag = scheduler.deleteJob(jobKey);
			System.out.println("存在：" + scheduler.checkExists(jobKey));
			if (scheduler.checkExists(jobKey)) {
				logger.info("删除定时任务scheduler({})删除job({})", scheduler.getSchedulerInstanceId(), jobKey);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("删除任务失败, {}:{}", jobKey, e.getMessage());
		}
		return flag;
	}
	
	private String randomString() {
		synchronized (ScheduledBuilder.class) {
			int length = RANDOMCHAR.length();
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 8; i++) {
				int nextInt = random.nextInt(length);
				sb.append(RANDOMCHAR.charAt(nextInt));
			}
			return sb.toString();
		}
	}
}

















