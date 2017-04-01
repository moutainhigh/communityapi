package net.okdi.api.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.okdi.api.service.TakeTaskService;
import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.common.quartz.GenericJob;
import net.okdi.core.util.PubMethod;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

public class TakeTaskJob extends GenericJob {

	private static final Logger logger = LoggerFactory.getLogger(TakeTaskJob.class);

    @SuppressWarnings("unused")
	private static final String reason = "快递员太忙了，没时间取件，联系其他快递员试试";
    
	@Override
	public void process(JobExecutionContext context, WebApplicationContext ctx) throws JobExecutionException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("抢单定义任务开始:: @ :: {}", df.format(new Date()));
		
		JobDataMap dataMap = context.getTrigger().getJobDataMap();
		long taskId = dataMap.getLong("taskId");
		
		TakeTaskService takeTaskService = ctx.getBean(TakeTaskService.class);
		TaskRemindService taskRemindService = ctx.getBean(TaskRemindService.class);
		
		Map<String, Object> task = takeTaskService.queryTakeById(taskId);
		if (task == null) {
			logger.info("抢单任务不存在::{}::定时任务结束", taskId);
			return;
		}
		String source = String.valueOf(task.get("taskSource"));
		if ("0".equals(source) || "4".equals(source) || "6".equals(source)) {
			logger.info("倒计时结束没有被抢::{}::更新状态为取消", source);
			//actorMemberId 查询任务中的 actorMemberId 是否有执行人员的actorMemberId,有就不执行定时,没有就取消
			Object object = task.get("actorMemberId");
			if(PubMethod.isEmpty(object)){
				takeTaskService.updateTaskStatus(taskId, 3);
				taskRemindService.removeById(taskId, null);
				logger.info("无人抢单,删除发送的附近快递员抢单中的记录taskId:"+taskId);
				return;
			}
			
		}
		logger.info("不处理::{}::{}", taskId, source);
		logger.info("抢单定义任务结束:: @ :: {}", df.format(new Date()));
	}
    
	/*@Override
	public void process(JobExecutionContext context, WebApplicationContext ctx) throws JobExecutionException {
		logger.info("取件任务定义任务开始::{}", new Date());
		
		ParTaskInfoMapper parTaskInfoMapper = ctx.getBean(ParTaskInfoMapper.class);
		
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		long taskId = jobDataMap.getLong("taskId");
		logger.info("taskId::{}", taskId);
		
		ParTaskInfo task = parTaskInfoMapper.selectByPrimaryKey(taskId);
		Byte status = task.getTaskStatus();
		if (!"0".equals(String.valueOf(status))) {
			logger.info("taskId::{}的任务10分钟后已经处理::{}, 定时任务结束", taskId, status);
			return;
		}
		logger.info("taskId::{}的任务10分钟后没有处理::{}, 设置状态为取消", taskId, status);
		
		ParTaskInfo record = new ParTaskInfo();
		record.setTaskId(taskId);
		record.setTaskStatus(new Byte("3"));
		parTaskInfoMapper.updateByPrimaryKeySelective(record);
		
		long fromCompId = jobDataMap.getLong("fromCompId");
		long actorMemberId = jobDataMap.getLong("actorMemberId");
		TakeTaskService takeTaskService = ctx.getBean(TakeTaskService.class);
		takeTaskService.removeTaskIgomoCache(fromCompId, actorMemberId);

		String openId = jobDataMap.getString("openId");

        WechatMpService wechatMpService = ctx.getBean(WechatMpService.class);
		ExpressUserService expressUserService = ctx.getBean(ExpressUserService.class);
		HashMap<String, Object> expressUser =  expressUserService.queryRealNameAuditInfo(String.valueOf(actorMemberId));
		String courierName = expressUser.get("memberName").toString();
		String phone = expressUser.get("memberPhone").toString();

		logger.info("给用户({})发送模板消息", openId);
        wechatMpService.offlineSend(openId, courierName, phone, 0, reason);

		logger.info("取件任务定义任务结束::{}", new Date());
	}*/

}







