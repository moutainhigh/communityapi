package net.okdi.apiV4.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.okdi.apiV4.entity.TaskRemind;
import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.tools.Tracer.Logger;

@Service
public class TaskRemindServiceImpl extends BaseServiceImpl  implements TaskRemindService {
	@Resource
	protected MongoTemplate mongoTemplate;
    @Autowired
	private ConstPool constPool;
	@Override
	public List<Map> queryTaskRemind(Long memberId,Integer pageNo,Integer pageSize) throws ParseException{
	List<Map> list=new ArrayList<Map>();
	/*		if(pageNo==1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c=Calendar.getInstance();
			Date today=c.getTime();
			c.add(Calendar.DAY_OF_MONTH,-1);
			Date last=c.getTime();
			String endTime=sdf.format(today);
			String startTime=sdf.format(last);
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
			Query query = new Query();
			query.addCriteria(Criteria.where("createTime").gte(startDate)
					.lte(endDate).and("memberId").is(memberId).and("isRemove").is(0));
			query.with(new Sort(Direction.DESC,"updateTime"));//修改排序字段，为最后更改时间
			List<TaskRemind> fs = mongoTemplate.find(query, TaskRemind.class);
			for(TaskRemind tr:fs){
				Map map = new HashMap();
				map.put("flag", 5);
				map.put("id", tr.getUid());
				map.put("memberId", tr.getMemberId());
				map.put("memberPhone", tr.getMemberPhone());
				map.put("msgId", tr.getTaskId()+"");
				map.put("receiverPhone", tr.getSenderPhone());
				map.put("replyStatus", 0);
				map.put("isRead", tr.getIsRead());
				map.put("sendContent", tr.getSenderAddress()+tr.getSenderPhone());
				map.put("sendResult", "");
				map.put("sendStatus", "");
				map.put("taskId", tr.getTaskId());
				map.put("updateTime", sdf.format(tr.getUpdateTime()));
				list.add(map);
			}
		}*/
		Map map=new HashMap();
		map.put("memberId",memberId+"");
		map.put("pageNo",pageNo+"");
		map.put("pageSize",pageSize+"");
		String url = constPool.getSmsZtUrl() + "smallBell/query";
		String result = Post(url, map);
		if (!PubMethod.isEmpty(result)) {//result为空说明短信服务器有问题
			JSONObject fromObject = JSONObject.parseObject(result);
			String data = fromObject.getString("data");
			if (!PubMethod.isEmpty(data)) {//data为空说明查询结果为空，就不用接下来的操作了
				JSONObject fromObject1 = JSONObject.parseObject(data);
				JSONArray jsonArray = fromObject1.getJSONArray("smsList");
				List<Map> list1 = JSON.parseArray(jsonArray.toString(),
						Map.class);
				for (Map mp : list1) {
					list.add(mp);
				}
			}
		}
		return list;
	}
	@Override
	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void removeMsgLoger(Long msgId, Long memberId) {
		Criteria criteria = new Criteria("taskId").is(Long.valueOf(msgId)).and("memberId").is(memberId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("isRemove", (short)1); //把状态改为删除
		mongoTemplate.updateFirst(query, update, TaskRemind.class);	
	}
	
	@Override
	public void removeById(Long msgId, Long memberId){
		System.out.println("进入removeById方法，msgId="+msgId+",memberId="+memberId);
		Criteria criteria = null;
		if(!PubMethod.isEmpty(memberId)){			
			criteria = new Criteria("taskId").is(Long.valueOf(msgId)).and("memberId").ne(memberId);
		}else{
			criteria = new Criteria("taskId").is(Long.valueOf(msgId));
		}
		Query query = new Query(criteria);
		List<TaskRemind> list = this.mongoTemplate.find(query, TaskRemind.class);
		if(list!=null && list.size()>0){
			for(TaskRemind remind:list){
				Update update = new Update();
				update.set("isRemove", (short)1); //把状态改为删除
//				mongoTemplate.updateMulti(query, update, TaskRemind.class);	
				mongoTemplate.updateFirst(Query.query(Criteria.where("uid").is(remind.getUid())), update, TaskRemind.class);
			}
		}
		System.out.println("结束removeById方法，TaskRemind状态删除完毕!");
	}
	@Override
	public String unRead(String msgId,Long memberId) {
		Criteria criteria = new Criteria("taskId").is(Long.valueOf(msgId)).and("memberId").is(memberId).and("isRead").is(0);
//		Criteria criteria = new Criteria("taskId").is(Long.valueOf(msgId)).and("isRead").is(0);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("isRead", 1); //把状态改为已读
		mongoTemplate.updateFirst(query, update, TaskRemind.class);	
		return "true";
	}

	
	@Override
	public Long unReadCount(Long memberId) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c=Calendar.getInstance();
		Date today=c.getTime();
		c.add(Calendar.DAY_OF_MONTH,-1);
		Date last=c.getTime();
		String endTime=sdf.format(today);
		String startTime=sdf.format(last);
		Date startDate = sdf.parse(startTime);
		Date endDate = sdf.parse(endTime);
		Query query = new Query();
		query.addCriteria(Criteria.where("createTime").gte(startDate)
				.lte(endDate).and("memberId").is(memberId).and("isRemove").is(0).and("isRead").is(0));
		query.with(new Sort(Direction.DESC,"updateTime"));//修改排序字段，为最后更改时间
		Long account = mongoTemplate.count(query, "taskRemind");
		return account;
	}
	@Override
	public List<Map> queryTaskRob(Long memberId, Integer pageNo,Integer pageSize) throws ParseException {
		List<Map> list=new ArrayList<Map>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c=Calendar.getInstance();
			Date today=c.getTime();
			c.add(Calendar.DAY_OF_MONTH,-1);
			Date last=c.getTime();
			String endTime=format.format(today);
			String startTime=format.format(last);
			Date startDate = format.parse(startTime);
			Date endDate = format.parse(endTime);
			Query query = new Query();
			query.addCriteria(Criteria.where("createTime").gte(startDate)
					.lte(endDate).and("memberId").is(memberId).and("isRemove").is(0));
			query.with(new Sort(Direction.DESC,"updateTime"));//修改排序字段，为最后更改时间
			//获取总条数  
//	        long totalCount = this.mongoTemplate.count(query, TaskRemind.class);  
	        //总页数  
//	        int totalPage = (int) (totalCount/pageSize);  
	        int skip = (pageNo-1)*pageSize;  
//	        Pagination<T> page = new Pagination<T>(pageNo, totalPage, (int)totalCount);  
	        query.skip(skip);// skip相当于从那条记录开始  
	        query.limit(pageSize);// 从skip开始,取多少条记录  
			
			List<TaskRemind> fs = mongoTemplate.find(query, TaskRemind.class);
			for(TaskRemind tr:fs){
				Map map = new HashMap();
				map.put("flag", 5);
				map.put("id", tr.getUid());
				map.put("memberId", tr.getMemberId());
				map.put("memberPhone", tr.getMemberPhone());
				map.put("msgId", tr.getTaskId()+"");
				map.put("firstMsgId", tr.getTaskId()+"");
				map.put("receiverPhone", tr.getSenderPhone());
				map.put("replyStatus", 0);
				map.put("isRead", tr.getIsRead());
				map.put("sendContent", tr.getSenderAddress()+tr.getSenderPhone());
				map.put("sendResult", "");
				map.put("sendStatus", "");
				map.put("taskId", tr.getTaskId());
				map.put("updateTime", format.format(tr.getUpdateTime()));
				list.add(map);
			}
		return list;
	}

}
