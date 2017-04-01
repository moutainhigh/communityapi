package net.okdi.core.common.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
public class ScheduleJobInitListener implements ApplicationListener<ContextClosedEvent>, ApplicationContextAware{

	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobInitListener.class);
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		logger.info("扫描定时任务开始: {}", new Date());

		List<GenericJob> jobs = new ArrayList<>();
		Map<String, GenericJob> beans = ctx.getBeansOfType(GenericJob.class);
		for (Iterator<String> iter = beans.keySet().iterator(); iter.hasNext();) {
			GenericJob job = beans.get(iter.next());
			if (job.getClass().getAnnotation(CronExpression.class) == null) {
				logger.info("{} 未设置cron表达式, 定时任务不执行.", job.getClass().getName());
				continue;
			}
			jobs.add(job);
		}
		logger.info("扫描结束, 定时任务共计{}个. {}", jobs.size(), new Date());
		new JobExecutor().executeOnStartup(jobs);		
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		Scheduler scheduler = null;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			try {
				logger.info("scheduler: {} 关闭异常.", scheduler.getSchedulerInstanceId());
			} catch (SchedulerException e1) {
				e1.printStackTrace();
			}
		}
	}
}
















