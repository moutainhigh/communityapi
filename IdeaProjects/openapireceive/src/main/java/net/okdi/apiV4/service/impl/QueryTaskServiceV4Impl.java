package net.okdi.apiV4.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.okdi.apiV4.dao.QueryTaskMapperV4;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.service.QueryTaskServiceV4;
import net.okdi.apiV4.vo.Vo_ParTaskInfo;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
@Service
public class QueryTaskServiceV4Impl implements QueryTaskServiceV4 {

	@Autowired
	private QueryTaskMapperV4 takeTaskMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private EhcacheService ehcacheService; //缓存
	
	public static final Log logger = LogFactory.getLog(QueryTaskServiceV4Impl.class);

	//运营平台查询取件列表
	public Page queryTask(Byte taskSource, String netName,Byte taskStatus, String contactAddress, String contactMobile,
			String actorPhone, Date startTime, Date endTime, Integer currentPage, Integer pageSize) {
		//根据netName查询netId
		Long netId =null;
		if(!PubMethod.isEmpty(netName)){
			netId = takeTaskMapper.getNetIdByName(netName);
		}
		Criteria criteria =null;
		if(!PubMethod.isEmpty(taskSource)) {
			criteria=Criteria.where("taskSource").is(taskSource);
		} else {
			criteria = Criteria.where("taskSource").ne((byte)-1);
		}
		if(!PubMethod.isEmpty(netId)) {
			criteria =criteria.and("coopNetId").is(netId);
		} 
		if(!PubMethod.isEmpty(taskStatus)) {
			criteria =criteria.and("taskStatus").is(taskStatus);
		} else {
			criteria =criteria.and("taskStatus").ne((byte)11);
		}
		if(!PubMethod.isEmpty(contactAddress)) {
			criteria =criteria.and("contactAddress").regex(contactAddress);
		} 
		if(!PubMethod.isEmpty(contactMobile)) {
			criteria =criteria.and("contactMobile").is(contactMobile);
		} 
		if(!PubMethod.isEmpty(actorPhone)) {
			criteria =criteria.and("actorPhone").is(actorPhone);
		} 
		if(startTime != null && !"".equals(startTime)) {
			criteria =criteria.and("createTime").gte(startTime);
//			criteria.and("createTime").gte(DateUtil.getZeroTimeOfDay(startTime));
		} 
		if(endTime != null && !"".equals(endTime)) {
			criteria =criteria.and("createTime").lte(endTime);
//			criteria.and("createTime").lte(DateUtil.getZeroTimeOfDay(endTime));
		} 
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		//计算页数
		Query query = Query.query(criteria);
		//统计页数，返回结果值
		int totalCount = (int) mongoTemplate.count(query, ParTaskInfo.class);
			Query limit = query.skip(page.getOffset()).limit(page.getPageSize());
			/*select 
			mi.member_name,bn.net_name,bc.comp_name,
			
			from
			par_task_info pti,member_info mi,bas_netinfo bn,bas_compinfo bc
			where 
			pti.actor_member_id=mi.member_id and pti.coop_net_id=bn.net_id and pti.coop_comp_id=bc.comp_id 

			limit #{page.offset},#{page.pageSize}*/
//			resultlist = parTaskDisposalRecordMapper.queryTaskByCriteria(map);
			
			//查询结果
		List<ParTaskInfo> resultlist = mongoTemplate.find(limit, ParTaskInfo.class);
		List<Vo_ParTaskInfo> dataList = new ArrayList<Vo_ParTaskInfo>();
		for (ParTaskInfo parTaskInfo : resultlist) {
			Long actorMemberId = parTaskInfo.getActorMemberId();
			String memberName = takeTaskMapper.getMemberName(actorMemberId);
			Long coopNetId = parTaskInfo.getCoopNetId();
			String netName1 = takeTaskMapper.getNetName(coopNetId);
			Long coopCompId = parTaskInfo.getCoopCompId();
			String compName = takeTaskMapper.getCompName(coopCompId);
			//拼装数据
			Vo_ParTaskInfo taskInfo = new Vo_ParTaskInfo();
			taskInfo.setMemberName(memberName);
			taskInfo.setNetName(netName1);
			taskInfo.setCompName(compName);
			taskInfo.setActorPhone(parTaskInfo.getActorPhone());
			taskInfo.setContactAddress(parTaskInfo.getContactAddress());
			taskInfo.setContactMobile(parTaskInfo.getContactMobile());
			taskInfo.setContactName(parTaskInfo.getContactName());
			taskInfo.setAppointDesc(parTaskInfo.getAppointDesc());
			taskInfo.setAppointTime(parTaskInfo.getAppointTime());
			taskInfo.setParEstimateCount(parTaskInfo.getParEstimateCount());
			taskInfo.setTaskRemark(parTaskInfo.getTaskRemark());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date createTime2 = parTaskInfo.getCreateTime();
			String createTime="";
			if(!PubMethod.isEmpty(createTime2)){
				createTime = format.format(createTime2);
			}
			taskInfo.setCreateTime(createTime);
			Date modifyTime2 = parTaskInfo.getModifyTime();
			String modifyTime="";
			if(!PubMethod.isEmpty(modifyTime2)){
				modifyTime = format.format(modifyTime2);
			}
			taskInfo.setModifyTime(modifyTime);
			taskInfo.setParEstimateWeight(parTaskInfo.getParEstimateWeight());
			taskInfo.setTaskId(parTaskInfo.getUid());
			taskInfo.setTaskSource(parTaskInfo.getTaskSource());
			taskInfo.setTaskStatus(parTaskInfo.getTaskStatus());
			dataList.add(taskInfo);
		}
		
		int pageNum = 0;
		if(totalCount % page.getPageSize() > 0) {
			pageNum = (totalCount/page.getPageSize()) + 1;
		}
		page.setItems(dataList);
		page.setPageCount(pageNum);
		page.setTotal(totalCount);
		return page;
	}

	@Override
	public void deleteWechatSendRecords(String taskIds) {
		String[] ids = taskIds.split(",");
		for (String taskId : ids) {
			Query query = Query.query(Criteria.where("uid").is(Long.valueOf(taskId)));
			mongoTemplate.upsert(query, new Update().set("isRemove", (byte)1), ParTaskInfo.class);
		}
		ehcacheService.removeAll("TakeTaskCacheQueryList");
		logger.info("微信清除发件记录成功taskIds"+taskIds);
	}

}
