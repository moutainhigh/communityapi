package net.okdi.apiV3.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV3.dao.QueryAddressInfoMapper;
import net.okdi.apiV3.entity.WechatAddress;
import net.okdi.apiV3.service.QueryAddressInfoService;

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
public class QueryAddressInfoServiceImpl implements QueryAddressInfoService {

	@Autowired
	private QueryAddressInfoMapper queryAddressInfoMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Map<String, Object>> queryAddressInfo(Long parentId) {
		
		List<Map<String, Object>> dataList = queryAddressInfoMapper.queryAddressInfoByParentId(parentId);

		return dataList;
	}


	@Override
	public List<WechatAddress> getWechatAddressInfo(Long memberId,Short flag) {
		Criteria criteria = where("memberId").is(memberId).and("flag").is(flag);
		
		Query query = Query.query(criteria);

		query.with(new Sort(Direction.DESC, "createTime"));
		List<WechatAddress> list = mongoTemplate.find(query,WechatAddress.class);

		return list;
	}

	@Override
	public Map<String, String> addWechatAddressInfo(Long memberId,
			String senderName, String senderPhone, Long addresseeTownId,
			String addresseeTownName, String addresseeAddress, Short defaultMark,Short flag) {
		Map<String, String> map = new HashMap<String, String>();
		WechatAddress wechatAddress = new WechatAddress();
		long id = IdWorker.getIdWorker().nextId();
		wechatAddress.setId(id);
		wechatAddress.setCreateTime(new Date());
		wechatAddress.setMemberId(memberId);
		wechatAddress.setSenderName(senderName);
		wechatAddress.setSenderPhone(senderPhone);
		wechatAddress.setAddresseeTownId(addresseeTownId);
		wechatAddress.setAddresseeTownName(addresseeTownName);
		wechatAddress.setAddresseeAddress(addresseeAddress);
		this.modifyDefault(defaultMark,memberId,flag);
		wechatAddress.setDefaultMark(defaultMark);
		wechatAddress.setFlag(flag);
		mongoTemplate.insert(wechatAddress);

		return map;
	}


	@Override
	public Map<String, String> deleteWechatAddressInfo(Long memberId, Long addressId) {
		Map<String, String> map = new HashMap<String, String>();
		Criteria criteria=where("memberId").is(memberId).and("id").is(addressId);
		Query query=Query.query(criteria);
		mongoTemplate.remove(query, WechatAddress.class);
		return map;
	}
	
	@Override
	public List<WechatAddress> queryWechatDefaultAddress(Long memberId,Short flag) {
		Criteria criteria = where("memberId").is(memberId).and("defaultMark").is(0).and("flag").is(flag);//0代表默认地址
		
		Query query = Query.query(criteria);

//		query.with(new Sort(Direction.DESC, "createTime"));
		WechatAddress defaultAdd = mongoTemplate.findOne(query, WechatAddress.class);
		List<WechatAddress> list=new ArrayList<>();
		list.add(defaultAdd);
		return list;
	}

	@Override
	public Map<String, String> editWechatAddressInfo(Long memberId,Long addressId,String senderName, String senderPhone, Long addresseeTownId,
			String addresseeTownName, String addresseeAddress, Short defaultMark ,Short flag) {
		Map<String, String> map = new HashMap<String, String>();
		Criteria criteria = where("memberId").is(memberId).and("id").is(addressId);
		Query query = Query.query(criteria);
		Update update=new Update();
		
		update.set("senderName", senderName);
		update.set("senderPhone", senderPhone);
		update.set("addresseeTownId", addresseeTownId);
		update.set("addresseeTownName", addresseeTownName);
		update.set("addresseeAddress", addresseeAddress);
		this.modifyDefault(defaultMark,memberId,flag);
		update.set("defaultMark", defaultMark);
		update.set("flag", flag);
		mongoTemplate.upsert(query, update, WechatAddress.class);
		return map;
	}
	@Override
	public void modefyDefaultAddress(Long memberId,Long addressId, Short flag) {
		Criteria criteria = where("memberId").is(memberId).and("id").is(addressId);
		Query query = Query.query(criteria);
		Update update=new Update();
		Short defaultMark=0;
		this.modifyDefault(defaultMark,memberId,flag);
		update.set("defaultMark", defaultMark);
		mongoTemplate.upsert(query, update, WechatAddress.class);
	}

	public void modifyDefault(Short defaultMark,Long memberId ,Short flag){
		if(defaultMark==0){//将其他地址设置为非默认
			Criteria criteria = where("memberId").is(memberId).and("defaultMark").is((short)0).and("flag").is(flag);//0代表默认地址
			Query query = Query.query(criteria);
			Update update = new Update();
			update.set("defaultMark", (short) 1);
			mongoTemplate.updateMulti(query, update, WechatAddress.class);
		}
	}
}
