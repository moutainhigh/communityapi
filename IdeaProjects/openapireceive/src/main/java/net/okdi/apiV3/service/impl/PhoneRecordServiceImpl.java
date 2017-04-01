package net.okdi.apiV3.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV3.entity.PhoneRecord;
import net.okdi.apiV3.service.PhoneRecordService;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


/**
 * @author jingguoqiang
 * @desc  
 */
@Service
public class PhoneRecordServiceImpl implements  PhoneRecordService{
	Logger logger = Logger.getLogger(PhoneRecordServiceImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/** 
	 * 
	* @Description: 插入通话记录  
	* @author: jingguoqiang
	* @date 2016-4-7 上午10:10:27 
	*/ 
	@Override
	public void savePhoneRecord(String sendPhone,String receivePhone,String flag) {
		PhoneRecord phoneRecord = new PhoneRecord();
			
				phoneRecord.setSendPhone(sendPhone);
				phoneRecord.setReceivePhone(receivePhone);
				phoneRecord.setStartTime(new Date());
				phoneRecord.setFlag(flag);
			  mongoTemplate.insert(phoneRecord, "PhoneRecord");
			
	}


	@Override
	public Map queryPhoneRecord(String sendPhone,String receivePhone, Integer currentPage,Integer pageSize) {
		Query query = new Query();
		Map map = new HashMap();
		  query.addCriteria(Criteria.where("sendPhone").is(sendPhone));
		if(!PubMethod.isEmpty(receivePhone)){
			query.addCriteria(Criteria.where("receivePhone").is(receivePhone));
		}
		
		
		
	
		query.skip((currentPage-1)*pageSize);//分页
		query.limit(pageSize);
		//进行排序
		query.with(new Sort(Direction.DESC,"startTime"));
		Long len = mongoTemplate.count(query, PhoneRecord.class);
		
		List<PhoneRecord> phoneRecordList = mongoTemplate.find(query,PhoneRecord.class);//
		
		map.put("totalCount", len);
		map.put("list", phoneRecordList);
		logger.info("---查询条件:receivePhone="+receivePhone+",返回结果数量:"+len+",结果记录集合:"+phoneRecordList.toString());
		return map;
	}


	@Override
	public void deletePhoneRecord(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query, PhoneRecord.class);
	}

	

	

	
}


