package net.okdi.apiV4.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV2.dao.CollectionMapper;
import net.okdi.apiV4.entity.ExpCustomerInfo;
import net.okdi.apiV4.service.CustomerInfoNewTwoService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;
@Service
public class CustomerInfoNewTwoServiceImpl implements CustomerInfoNewTwoService{
	public static final Log logger = LogFactory.getLog(CustomerInfoNewTwoServiceImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;//mongo
	@Autowired
	EhcacheService ehcacheService;
	@Autowired
	private CollectionMapper collectionMapper;
	//删除联系人
	@Override
	public String deleteCustomerV4(Long customerId, Long compId, Long memberId) {
		this.mongoTemplate.remove(Query.query(Criteria.where("_id").is(customerId)), ExpCustomerInfo.class);
		removeCompIdCache(compId);
		removeCompIdCache(memberId);
		return String.valueOf(customerId);
	}
	private void removeCompIdCache(Object compId) {
		if (ehcacheService.getByKey("customerInfoCache", String.valueOf(compId))) {
			String str = ehcacheService.get("customerInfoCache", String.valueOf(compId), String.class);
			List ls = ehcacheService.get("customerInfoCache", String.valueOf(compId), List.class);
			for (int i = 0; i < ls.size(); i++) {
				ehcacheService.remove("customerInfoCache", ls.get(i).toString());
			}
			ehcacheService.remove("customerInfoCache", String.valueOf(compId));
		}
	}
	//选择联系人
	@Override
	public List<ExpCustomerInfo> selectCustomer(
			Long compId, Long memberId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("customerType").is(null));
		List<ExpCustomerInfo> list = this.mongoTemplate.find(query, ExpCustomerInfo.class);
		return list;
	}
	//添加联系人
	@Override
	public String insertCustomerV4(Long compId, String customerName,
			Byte gender, Short customerType, String customerPhone,
			String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, Long memberId) {
		Long customerId = IdWorker.getIdWorker().nextId();

		ExpCustomerInfo eci = new ExpCustomerInfo();
		eci.setId(customerId);
		eci.setCompId(compId);
		eci.setCustomerName(customerName);
		eci.setGender(gender);
		eci.setCustomerNameSpell(PiyinUtil.cn2py(customerName));
		eci.setCustomerType(customerType);
		eci.setCustomerPhone(customerPhone);
		eci.setTownName(townName);
		eci.setDetailedAddress(townName);//townName代表详细地址
		eci.setAddressThress(addressThree);
		eci.setAddressTwo(addressTwo);
		eci.setIphoneThree(iphoneThree);
		eci.setIphoneTwo(iphoneTwo);
		eci.setCompAddress(compName);
		eci.setCreateTime(new Date());
		eci.setMemberId(memberId);
		mongoTemplate.insert(eci);
		removeCompIdCache(memberId);
		return String.valueOf(customerId);
	}

	//编辑联系人
	@Override
	public String editCustomer(Long customerId, Long compId,
			String customerName, Short customerType, String customerPhone,
			Byte gender, String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, Long memberId) {
		Update update = new Update();
		update.set("compId", compId);
		update.set("customerName", customerName);
		update.set("customerNameSpell", PiyinUtil.cn2py(customerName));
		update.set("customerType", customerType);
		update.set("customerPhone", customerPhone);
		update.set("townName", townName);
		update.set("detailedAddress", townName);//townName代表详细地址
		update.set("addressThress", addressThree);
		update.set("addressTwo", addressTwo);
		update.set("iphoneThree", iphoneThree);
		update.set("gender", gender);
		update.set("iphoneTwo", iphoneTwo);
		update.set("compAddress", compName);
		update.set("memberId", memberId);
		this.mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(customerId)), update, ExpCustomerInfo.class);
		removeCompIdCache(compId);
		removeCompIdCache(memberId);
		return String.valueOf(customerId);
	}
	@Override
	public String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId) {
		//添加标签
		String[] str=addCustomerIds.split(",");
		if(!PubMethod.isEmpty(str)){
			Update update = new Update();
			update.set("customerType", Short.valueOf(customerType));
			for(String customerId:str){
				this.mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(Long.valueOf(customerId))), update, ExpCustomerInfo.class);
			}
		}
		//删除标签
		String[] strd=delCustomerIds.split(",");
		if(!PubMethod.isEmpty(strd)){
			Update update1 = new Update();
			update1.set("customerType", "");
			for(String customerId:strd){
				this.mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(Long.valueOf(customerId))), update1, ExpCustomerInfo.class);
			}
		}
		removeCompIdCache(compId);
		removeCompIdCache(memberId);
		return "1";
	}
	@Override
	public String addContactsInfo(Long compId, String mobileData,Long memberId) {
		List jsonArray = JSON.parseObject(mobileData).getJSONArray("data");
		for (Object object : jsonArray) {
			Map<String, String> map = (Map<String, String>) object;
			String phone=map.get("mobile");
			List<ExpCustomerInfo> list = mongoTemplate.find(Query.query(Criteria.where("memberId").is(memberId).and("customerPhone").is(phone)), ExpCustomerInfo.class);
			if(list.size()>0){
				continue;
			}
			Long customerId = IdWorker.getIdWorker().nextId();
			ExpCustomerInfo eci = new ExpCustomerInfo();
			eci.setId(customerId);
			eci.setCompId(compId);
			eci.setCustomerName(map.get("name"));
			eci.setCustomerNameSpell(PiyinUtil.cn2py(map.get("name")));
			eci.setCustomerPhone(map.get("mobile"));
			eci.setCreateTime(new Date());
			eci.setMemberId(memberId);
			mongoTemplate.insert(eci);
			removeCompIdCache(memberId);
		}
		
		return "1";
	}
	@Override
	public List<ExpCustomerInfo> queryCustomersJDW(Long memberId,
			String quertCondition) {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(2000);
		String customerName = quertCondition;
		Map<String,Object> phoneMap =queryCustomers(memberId, customerName,null, null,null,page);
		Page phonePage = (Page)phoneMap.get("page");
		
		Page page2 = new Page();
		page2.setCurrentPage(1);
		page2.setPageSize(2000);
		Map<String,Object> nameMap =queryCustomers(memberId, null,null, customerName,null,page2);
		Page namePage = (Page)nameMap.get("page");
		List<ExpCustomerInfo> contactsList = getSumFromCustomerList(phonePage.getItems(),namePage.getItems());
		return contactsList;
	}

	private Map<String, Object> queryCustomers(Long memberId, String customerName, Short customerType, String customerPhone,Long expMemberId,
			Page page) {
		String cacheParam = "";
		Map<String, Object> params = new HashMap<String, Object>();
		cacheParam += "memberId=" + memberId;
		cacheParam += "customerName=" + customerName;
		cacheParam += "customerType=" + customerType;
		cacheParam += "customerPhone=" + customerPhone;
		cacheParam += "currentPage=" + page.getCurrentPage() + "pageSize=" + page.getPageSize();
		cacheParam += "expMemberId=" + expMemberId;
		String cacheCount = cacheParam + "Count";

		params.put("memberId", memberId);
		params.put("customerName", customerName);
		params.put("customerType", customerType);
		params.put("customerPhone", customerPhone);
		params.put("expMemberId", expMemberId);
		params.put("page", page);

		Integer count = 0;
		page.setTotal(count);

		//存储取得的所有Id
		List<ExpCustomerInfo> listCustomersId = new ArrayList<ExpCustomerInfo>();
		//
		List<ExpCustomerInfo> listCustomers = new ArrayList<ExpCustomerInfo>();

		/*if (ehcacheService.getByKey("customerInfoCache", cacheParam)) { //缓存不为空
			logger.info("人员管理走缓存了。。。。。。。。。。。。。。。。。。");
			listCustomersId =(List<ExpCustomerInfo>)ehcacheService.get("customerInfoCache", cacheParam, List.class);
			String s = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			count = ehcacheService.get("customerInfoCache", cacheCount, Integer.class);
		} else {*/
			//count = expCustomerInfoNewMapper.selectCount(params);
			Query query=new Query();
			Criteria criteria = new Criteria();
			Criteria[] criterialist = new Criteria[3];
			Criteria criteria0 = new Criteria();
			if(!PubMethod.isEmpty(customerName)){
				criteria0.andOperator(Criteria.where("memberId").is(memberId).and("customerName").is(customerName));
			}else{
				criteria0.andOperator(Criteria.where("memberId").is(memberId));
			}
			criterialist[0]=criteria0;
			
			Criteria criteria1 = new Criteria();
			if(!PubMethod.isEmpty(customerType)){
				criteria1.andOperator(Criteria.where("memberId").is(memberId).and("customerType").is(customerType));
			}else{
				criteria1.andOperator(Criteria.where("memberId").is(memberId));
			}
			criterialist[1]=criteria1;
			
			Criteria criteria2 = new Criteria();
			if(!PubMethod.isEmpty(customerPhone)){
				criteria2.andOperator(Criteria.where("memberId").is(memberId).and("customerPhone").is(customerPhone));
			}else{
				criteria2.andOperator(Criteria.where("memberId").is(memberId));
			}
			criterialist[2]=criteria2;
			criteria.orOperator(criterialist);
			query.addCriteria(criteria);
			query.fields().include("id");
			List<Long> list=new ArrayList<Long>();
			listCustomersId=mongoTemplate.find(query, ExpCustomerInfo.class);
			/*for(ExpCustomerInfo info:listCustomersId){
				list.add(info.getId());
			}*/
//			if (count != 0)
//				listCustomersId = expCustomerInfoNewMapper.selectCustomers(params);
			page.setTotal(listCustomersId.size());
			addCache(memberId, cacheCount, listCustomersId.size());
			addCache(memberId, cacheParam, listCustomersId);
//		}

		for (int i = 0; i < listCustomersId.size(); i++) {
			ExpCustomerInfo eci = getByCustomerId(memberId, listCustomersId.get(i).getId());
			String phone = eci.getCustomerPhone();
			HashMap<String,Object> memMap = collectionMapper.queryMemberInfo(phone);
			if(PubMethod.isEmpty(memMap)){
				eci.setIsOkdit((byte) 0);
			}else {
				eci.setIsOkdit((byte) 1);
			}
			listCustomers.add(eci);
		}

		page.setItems(listCustomers);
		if(count==null){
			logger.error("这里count为空，回头看看为什么:"+count);
			count = 0;
		}
		page.setTotal(count);

		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("page", page);
		return resultmap;
	}
	
	
	public ExpCustomerInfo getByCustomerId(Long memberId, Long customerId) {
		String cacheParam = "";
		cacheParam += "customerId=" + customerId;
		ExpCustomerInfo eci = null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) { //缓存不为空
			eci = ehcacheService.get("customerInfoCache", cacheParam, ExpCustomerInfo.class);
		} else {
			Query queryeci=new Query();
			queryeci.addCriteria(Criteria.where("_id").is(customerId));
			eci=mongoTemplate.findOne(queryeci, ExpCustomerInfo.class);
			
		}
		return eci;
	}
	private List<ExpCustomerInfo> getSumFromCustomerList(List<ExpCustomerInfo> list1, List<ExpCustomerInfo> list2) {
		List<ExpCustomerInfo> resultList = new ArrayList<ExpCustomerInfo>();
		
		for(int i = 0 ; i < list1.size() ; i++){
			int j = 0;
			for(; j < list2.size() ; j++){
				if(list1.get(i).getId().equals(list2.get(j).getId())){
					break;
				}
			}
			if(j==list2.size()){
				resultList.add(list1.get(i));
			}
		}
		list2.addAll(resultList);
		
		return list2;
	}
	private void addCache(Object compId, String strName, Object cacheContent) {
		List<String> lsCacheList = ehcacheService.get("customerInfoCache", compId.toString(), List.class);
		if (lsCacheList == null)
			lsCacheList = new ArrayList<String>();
		lsCacheList.add(strName);

		ehcacheService.put("customerInfoCache", strName, cacheContent);
		ehcacheService.put("customerInfoCache", compId.toString(), lsCacheList);

	}
	@Override
	public String valContactsExits(Long memberId, String customerName) {
		List<ExpCustomerInfo> lsExp = selectContactNameByMemberId(memberId,customerName);
		if (lsExp.size() == 0)
			return "false";
		return "true";
	}

	
	private List<ExpCustomerInfo> selectContactNameByMemberId(Long memberId,
			String customerName) {
		Map m = new HashMap();
		m.put("memberId", memberId);
		m.put("customerName", customerName);

		String cacheParam = "";
		cacheParam += "memberId=" + memberId;
		cacheParam += "customerName=" + customerName;
		
		List<ExpCustomerInfo> lsExpInfo = null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) {
			String str = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			lsExpInfo = jsonArrayToBean(str, ExpCustomerInfo.class);
		} else {
			lsExpInfo = mongoTemplate.find(Query.query(Criteria.where("memberId").is(memberId).and("customerName").is(customerName)), ExpCustomerInfo.class);
			addCache(memberId, cacheParam, lsExpInfo);
		}
		return lsExpInfo;
	}
	private <T> List<T> jsonArrayToBean(String str, Class<T> t) {
		List<T> lt = new ArrayList<T>();
		if(PubMethod.isEmpty(str))
			return lt;
		JSONArray ja = JSONArray.parseArray(str);
		for (int i = 0; i < ja.size(); i++) {
			T my = JSONObject.parseObject(ja.getString(i), t);
			lt.add(my);
		}
		return lt;
	}
}
