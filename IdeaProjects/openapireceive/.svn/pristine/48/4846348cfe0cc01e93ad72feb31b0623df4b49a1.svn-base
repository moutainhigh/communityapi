package net.okdi.apiV1.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV1.entity.AnnounceMessageInfo;
import net.okdi.apiV1.entity.PushMessageInfo;
import net.okdi.apiV1.entity.PushMessageInfoItem;
import net.okdi.apiV1.service.PushMessageService;
import net.okdi.core.common.handlemessage.MessageSenderRabbit;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class PushMessageServiceImpl implements PushMessageService {

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MessageSenderRabbit messageSenderRabbit;
	@Override
	public void savePushMessageInfo(String title, String message,
			String extraParam, List arrPhones, String pushWay,String platform,Short useType ) {
		//放队列
		//Map<String,Object> map = new HashMap<String,Object>();
		//String result = title+"-"+message+"-"+extraParam+"-"+pushWay+"-"+arrPhones;
		//保存发送记录表
		List<PushMessageInfo> list = new ArrayList<PushMessageInfo>();
		long id = IdWorker.getIdWorker().nextId();//主键id
		Date date = new Date();//创建时间
		PushMessageInfo messageInfo = new PushMessageInfo();
		messageInfo.setId(id);
		messageInfo.setContent(message);
		messageInfo.setCreateTime(date);
		messageInfo.setExtraParam(Short.valueOf(extraParam));
		messageInfo.setModifyTime(null);
		messageInfo.setOpenNum(0);
		messageInfo.setPushWay(Short.valueOf(pushWay));
		messageInfo.setReciveNum(0);
		messageInfo.setSendNum(arrPhones.size());
		messageInfo.setSendTime(date);
		messageInfo.setTitle(title);
		messageInfo.setPlatform(platform);
		messageInfo.setUseType(useType);
		//list.add(messageInfo);
		mongoTemplate.insert(messageInfo);
		
		this.messageSenderRabbit.setPushRecordKey("pushRecordKey");
		for (int i = 0; i < arrPhones.size(); i++) {
			String result = id+"-"+arrPhones.get(i);
			this.messageSenderRabbit.sendDataToQueue(result);
		}
	}

	@Override
	public String receiveSendTypeValue(Long id, String sendPhone, Short flag) {
		// TODO Auto-generated method stub
		
		Query query = new Query();
		Update update = new Update();
		if(!PubMethod.isEmpty(id)){
			query.addCriteria(Criteria.where("batchId").is(id));
			if(!PubMethod.isEmpty(sendPhone)){
				query.addCriteria(Criteria.where("pushPhone").is(sendPhone));
			}
		}
		List<PushMessageInfoItem> listMap = mongoTemplate.find(query, PushMessageInfoItem.class);
		if(!PubMethod.isEmpty(listMap.get(0))){
			PushMessageInfoItem infoItem = listMap.get(0);
			Short reciveType = infoItem.getReciveType();//接收类型
			Short openType = infoItem.getOpenType();//打开类型
			if(!reciveType.equals(1)){//未接收过
				//1.修改记录详细表中的记录
				update.set("flag", flag);
				mongoTemplate.updateFirst(query, update, PushMessageInfoItem.class);
				//mongoTemplate.updateMulti(query, update, PushMessageInfoItem.class);
				//2.修改记录表中的记录
				Query query1 = new Query();
				Update update1 = new Update();
				query1.addCriteria(Criteria.where("_id").is(id));
				List<PushMessageInfo> messageInfo = mongoTemplate.find(query1, PushMessageInfo.class);
				update1.set("reciveNum", messageInfo.get(0).getReciveNum()+1);
				mongoTemplate.updateFirst(query1, update1, PushMessageInfo.class);
			}
		}
		
		return null;
	}

	@Override
	public Map<String, Object> queryPushMessageListInfo(String startTime,
			String endTime, String title, String content, Integer currentPage,
			Integer pageSize) {
		// TODO Auto-generated method stub
		
		try {
			Query query = new Query();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//更新时间
			if(!PubMethod.isEmpty(startTime) && PubMethod.isEmpty(endTime)){
				query.addCriteria(Criteria.where("sendTime").gte(dateFormat.parse(startTime)));
			}
			if(!PubMethod.isEmpty(endTime) && PubMethod.isEmpty(startTime)){
				query.addCriteria(Criteria.where("sendTime").lte(dateFormat.parse(startTime)));
			}
			if(!PubMethod.isEmpty(startTime)&&!PubMethod.isEmpty(endTime)){
				query.addCriteria(Criteria.where("sendTime").gte(dateFormat.parse(startTime)).lte(dateFormat.parse(endTime)));
			}
			//标题和文本需用正则查询
			if(!PubMethod.isEmpty(title)){
				query.addCriteria(Criteria.where("title").regex(".*?"+title+".*"));
			}
			if(!PubMethod.isEmpty(content)){
				query.addCriteria(Criteria.where("content").regex(".*?"+content+".*"));
			}
			//查询总数量
			Long total = mongoTemplate.count(query, PushMessageInfo.class);
			//分页
			query.skip((currentPage-1)*pageSize);//分页
			query.limit(pageSize);
			query.with(new Sort(Direction.DESC,"sendTime"));//根据发送时间排序
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取数据
			List<PushMessageInfo> listMessage = mongoTemplate.find(query, PushMessageInfo.class);
			for(PushMessageInfo messageInfo : listMessage){
				Query query2 = new Query();
				long id = messageInfo.getId();
				query2.addCriteria(Criteria.where("batchId").is(id));
				long count = mongoTemplate.count(query2, PushMessageInfoItem.class);
				messageInfo.setSendNum(count);
			}
			resultMap.put("listMessage", listMessage);
			resultMap.put("total", total);
			return resultMap;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Map<String, Object> queryAnnounceMessageListInfo(String startTime,
			String endTime, String title, String content, Integer currentPage,
			Integer pageSize) {
		// TODO Auto-generated method stub
		
		try {
			Query query = new Query();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//更新时间
			if(!PubMethod.isEmpty(startTime) && PubMethod.isEmpty(endTime)){
				query.addCriteria(Criteria.where("createTime").gte(dateFormat.parse(startTime)));
			}
			if(!PubMethod.isEmpty(endTime) && PubMethod.isEmpty(startTime)){
				query.addCriteria(Criteria.where("createTime").lte(dateFormat.parse(startTime)));
			}
			if(!PubMethod.isEmpty(startTime)&&!PubMethod.isEmpty(endTime)){
				query.addCriteria(Criteria.where("createTime").gte(dateFormat.parse(startTime)).lte(dateFormat.parse(endTime)));
			}
			//标题和文本需用正则查询
			if(!PubMethod.isEmpty(title)){
				query.addCriteria(Criteria.where("title").regex(".*?"+title+".*"));
			}
			if(!PubMethod.isEmpty(content)){
				query.addCriteria(Criteria.where("content").regex(".*?"+content+".*"));
			}
			//查询总数量
			Long total = mongoTemplate.count(query, AnnounceMessageInfo.class);
			//分页
			query.skip((currentPage-1)*pageSize);//分页
			query.limit(pageSize);
			query.with(new Sort(Direction.DESC,"createTime"));//根据发送时间排序
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取数据
			List<AnnounceMessageInfo> listMessage = mongoTemplate.find(query, AnnounceMessageInfo.class);
			
			resultMap.put("listMessage", listMessage);
			resultMap.put("total", total);
			return resultMap;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PushMessageInfoItem> queryPushPhoneAllById(String id) {
		// TODO Auto-generated method stub
		Long lid = Long.valueOf(id);
		Query query = new Query();
		query.addCriteria(Criteria.where("batchId").is(lid));
		List<PushMessageInfoItem> listMessage = mongoTemplate.find(query, PushMessageInfoItem.class);
		
		return listMessage;
	}
	@Override
	public void saveAnnounceMessageInfo(String announceType, String title,
			String creator, String content, String pushWay) {
		long id = IdWorker.getIdWorker().nextId();//主键id
		Date date = new Date();//创建时间
		AnnounceMessageInfo messageInfo = new AnnounceMessageInfo();
		messageInfo.setId(id);
		messageInfo.setContent(content);
		messageInfo.setCreateTime(date);
		messageInfo.setModifiedTime(date);
		messageInfo.setPushWay(Short.valueOf(pushWay));
		messageInfo.setTitle(title);
		messageInfo.setAnnounceType(Short.valueOf(announceType));
		messageInfo.setContent(content);
		messageInfo.setCreator(creator);
		messageInfo.setStatus((short)0);
		//list.add(messageInfo);
		mongoTemplate.insert(messageInfo);
		
	}
	@Override
	public void updateAnnounceStatus(Long id){
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("status", (short)1);//更改公告状态
		update.set("modifiedTime", new Date());//更改修改时间
		mongoTemplate.updateFirst(query, update, AnnounceMessageInfo.class);
	}
	
}
