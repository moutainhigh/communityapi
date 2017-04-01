package net.okdi.apiV3.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV3.entity.WaybillSearchInfo;
import net.okdi.apiV3.service.WaybillSearchInfoService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;


@Service
public class WaybillSearchInfoServiceImpl implements WaybillSearchInfoService {
 private int pageSize=20;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public List<WaybillSearchInfo> waybillSearchInfo(Long memberId,Integer currentPage,String netName,String waybillNo) {
		Criteria criteria = where("memberId").is(memberId);
		if(!PubMethod.isEmpty(netName)){
			criteria.regex(netName);
		}
		if(!PubMethod.isEmpty(waybillNo)){
			criteria.regex(waybillNo);
		}
		Query query = Query.query(criteria);
		query.skip((currentPage - 1) * pageSize);
		query.limit(pageSize);
		query.with(new Sort(Direction.DESC, "modifyTime"));
		List<WaybillSearchInfo> list = mongoTemplate.find(query, WaybillSearchInfo.class);
		
		Calendar cale = Calendar.getInstance();
		int currMonth = cale.get(Calendar.MONTH);
		
		for (WaybillSearchInfo wayBill : list) {
			Date createTime = wayBill.getCreateTime();
			cale.setTime(createTime);
			int month = cale.get(Calendar.MONTH);
			wayBill.setMonth(month+1);
			wayBill.setIsCurrMonth(currMonth == month ? 1 : 0);
		}
		return list;
	}

	@Override
	public Map<String, String> insertWaybillInfo(String waybillNo,String netId,String netName,Long memberId) {
		Map<String, String> map=new HashMap<String, String>();
		WaybillSearchInfo waybillSearchInfo=new WaybillSearchInfo();
		Criteria criteria = where("memberId").is(memberId).and("waybillNo").is(waybillNo).and("netId").is(netId);
		Query query = Query.query(criteria);
		WaybillSearchInfo searchInfo = mongoTemplate.findOne(query, WaybillSearchInfo.class);
		if(PubMethod.isEmpty(searchInfo)){
			waybillSearchInfo.setId(IdWorker.getIdWorker().nextId());
			Date date = new Date();
			waybillSearchInfo.setCreateTime(date);
			waybillSearchInfo.setModifyTime(date);
			waybillSearchInfo.setMemberId(memberId);
			waybillSearchInfo.setNetId(netId);
			waybillSearchInfo.setNetName(netName);
//		waybillSearchInfo.setNetCode(netCode);
			waybillSearchInfo.setMemberId(memberId);
			waybillSearchInfo.setWaybillNo(waybillNo); 
			mongoTemplate.insert(waybillSearchInfo);
		}else {
			Update update = new Update();
			update.set("modifyTime", new Date());
			mongoTemplate.updateFirst(query, update, WaybillSearchInfo.class);
		}
			
		return map;
	}



	
}


