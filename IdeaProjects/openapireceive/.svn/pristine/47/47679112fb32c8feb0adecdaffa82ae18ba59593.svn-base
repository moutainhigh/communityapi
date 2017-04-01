package net.okdi.core.common.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public abstract class GenericJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		process(context, getContext());
	}
	
	/**
	 * 获取Web应用上下文
	 * @Method: getContext
	 * @return
	 */
	private WebApplicationContext getContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
	public abstract void process(JobExecutionContext context, WebApplicationContext ctx) throws JobExecutionException;
}
