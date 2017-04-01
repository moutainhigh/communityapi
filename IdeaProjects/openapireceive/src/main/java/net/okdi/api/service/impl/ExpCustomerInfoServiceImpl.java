package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.dao.ExpCustomerContactsInfoMapper;
import net.okdi.api.dao.ExpCustomerInfoMapper;
import net.okdi.api.dao.ExpCustomerMemberRelationMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.ExpCustomerContactsInfo;
import net.okdi.api.entity.ExpCustomerInfo;
import net.okdi.api.entity.ExpCustomerMemberRelation;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.PriceGroupService;
import net.okdi.api.vo.VO_ExpCustomerInfo;
import net.okdi.api.vo.VO_JustCustomerIdName;
import net.okdi.apiV2.dao.CollectionMapper;
import net.okdi.apiV4.dao.ParTaskInfoMapperV4;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.entity.ParTaskOrderRecord;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.AbstractHttpClient;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ExpCustomerInfoServiceImpl extends AbstractHttpClient implements ExpCustomerInfoService {

	@Autowired
	ExpCustomerInfoMapper expCustomerInfoMapper;
	@Autowired
	EhcacheService ehcacheService;
	@Autowired
	PriceGroupService  priceGroupService ;
	@Autowired
	ExpCustomerMemberRelationMapper expCustomerMemberRelationMapper;

	@Autowired
	ExpCustomerContactsInfoMapper expCustomerContactsInfoMapper;
	
	@Autowired
	ParcelInfoService parcelInfoService;
	
	@Autowired
	private CollectionMapper collectionMapper;

	@Autowired
	NoticeHttpClient noticeHttpClient;
	
	@Autowired
	ParTaskInfoMapperV4 parTaskInfoMapperV4;
	
	@Value("${pay_url}")
	private String payUrl; //新版财务url
	
	@Value("${ucenter.url}")
	private String ucenterUrl; 
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private ConstPool constPool;
	
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;
	
	@Value("${pay.code.time}")
	private String payCodeTime;
	
	Logger logger = Logger.getLogger(this.getClass()); 

	/**
	 * @Method: queryCustomers 
	 * @param compId
	 * @param customerName
	 * @param customerType
	 * @param expMemberId
	 * @param page
	 * @return 
	 * @see net.okdi.api.service.ExpCustomerInfoService#queryCustomers(java.lang.Long, java.lang.String, java.lang.Short, java.lang.Long, net.okdi.core.common.page.Page)
	 */
	@Override
	public Map<String, Object> queryCustomers(Long compId, String customerName, Short customerType, String customerPhone,Long expMemberId,
			Page page) {

		String cacheParam = "";
		Map<String, Object> params = new HashMap<String, Object>();
		cacheParam += "compId=" + compId;
		cacheParam += "customerName=" + customerName;
		cacheParam += "customerType=" + customerType;
		cacheParam += "customerPhone=" + customerPhone;
		cacheParam += "currentPage=" + page.getCurrentPage() + "pageSize=" + page.getPageSize();
		cacheParam += "expMemberId=" + expMemberId;
		String cacheCount = cacheParam + "Count";

		params.put("compId", compId);
		params.put("customerName", customerName);
		params.put("customerType", customerType);
		params.put("customerPhone", customerPhone);
		params.put("expMemberId", expMemberId);
		params.put("page", page);

		Integer count = 0;
		page.setTotal(count);

		//存储取得的所有Id
		List listCustomersId = new ArrayList();
		//
		List<ExpCustomerInfo> listCustomers = new ArrayList<ExpCustomerInfo>();

		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) { //缓存不为空
			listCustomersId = ehcacheService.get("customerInfoCache", cacheParam, List.class);
			String s = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			count = ehcacheService.get("customerInfoCache", cacheCount, Integer.class);
		} else {
			count = expCustomerInfoMapper.selectCount(params);
			if (count != 0)
				listCustomersId = expCustomerInfoMapper.selectCustomers(params);
			page.setTotal(count);
			addCache(compId, cacheCount, count);
			addCache(compId, cacheParam, listCustomersId);
		}

		for (int i = 0; i < listCustomersId.size(); i++) {
			ExpCustomerInfo eci = getByCustomerId(compId, Long.valueOf(listCustomersId.get(i).toString()));
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

	@Override
	public List<ExpCustomerInfo> queryCustomersJDW(Long compId, String quertCondition) {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(2000);
//		String customerPhone = quertCondition;
		String customerName = quertCondition;
		Map<String,Object> phoneMap =queryCustomers(compId, customerName,null, null,null,page);
		Page phonePage = (Page)phoneMap.get("page");
		
		Page page2 = new Page();
		page2.setCurrentPage(1);
		page2.setPageSize(2000);
		Map<String,Object> nameMap =queryCustomers(compId, null,null, customerName,null,page2);
		Page namePage = (Page)nameMap.get("page");
		List<ExpCustomerInfo> contactsList = getSumFromCustomerList(phonePage.getItems(),namePage.getItems());
		return contactsList;
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

	@Override
	public List<VO_JustCustomerIdName> getCustomerIdName(Long compId) {
		Page page = new Page();
		page.setPageSize(2000);
		page.setCurrentPage(1);
		Page p = (Page) queryCustomers(compId, null, null,null, null, page).get("page");
		List<ExpCustomerInfo> list = p.getItems();
		List<VO_JustCustomerIdName> lvo = new ArrayList<VO_JustCustomerIdName>();
		for (int i = 0; i < list.size(); i++) {
			VO_JustCustomerIdName vo = new VO_JustCustomerIdName();
			vo.setCustomerName(list.get(i).getCustomerName());
			vo.setId(list.get(i).getId());
			lvo.add(vo);
		}
		return lvo;
	}

	@Override
	public String valContactsExits(Long compId, String customerName) {
		List<ExpCustomerInfo> lsExp = selectByContactName(compId,customerName);
		if (lsExp.size() == 0)
			return "false";
		return "true";
	}

	
	public List<ExpCustomerInfo> selectByContactName(Long compId, String customerName){
		
		Map m = new HashMap();
		m.put("compId", compId);
		m.put("customerName", customerName);

		String cacheParam = "";
		cacheParam += "compId=" + compId;
		cacheParam += "customerName=" + customerName;
		
		List<ExpCustomerInfo> lsExpInfo = null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) {
			String str = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			lsExpInfo = jsonArrayToBean(str, ExpCustomerInfo.class);
		} else {
			lsExpInfo = expCustomerInfoMapper.selectByContactName(m);
			addCache(compId, cacheParam, lsExpInfo);
		}
		return lsExpInfo;
	}
	
	@Override
	public List<ExpCustomerInfo> queryByErpIdandCompId(Long compId,Long erpId){
		String cacheParam ="compId="+compId+"erpId"+erpId;
		List<ExpCustomerInfo> listExp= null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) { //缓存不为空
			String str = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			listExp=jsonArrayToBean(str, ExpCustomerInfo.class);
		} else {
			Map map = new HashMap();
			map.put("compId", compId);
			map.put("erpId", erpId);
			listExp = expCustomerInfoMapper.queryByErpIdandCompId(map);
			addCache(compId, cacheParam, listExp);
		}
		return listExp;
	}
	@Override
	public ExpCustomerContactsInfo getContactsByCustomerId(Long customerId) {
		return null;
	}
	
	@Override
	public String getContactsCount(Long compId, Long customerId) {
		Map m = new HashMap();
		m.put("compId", compId);
		m.put("customerId", customerId);

		String cacheParam = "";
		cacheParam += "compId=" + compId;
		cacheParam += "customerId=" + customerId;
		cacheParam += "Count";

		Integer count = 0;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) {
			count = ehcacheService.get("customerInfoCache", cacheParam, Integer.class);
		} else {
			count = expCustomerContactsInfoMapper.getContactsCount(m);
			//增加两次缓存
			addCache(compId, cacheParam, count);
		}

		return String.valueOf(count);
	}

	@Override
	public ExpCustomerInfo getByCustomerId(Long memberId, Long customerId) {
		String cacheParam = "";
		cacheParam += "customerId=" + customerId;
		ExpCustomerInfo eci = null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) { //缓存不为空
			eci = ehcacheService.get("customerInfoCache", cacheParam, ExpCustomerInfo.class);
			//voExpCustom = ehcacheService.get("customerInfoCache", cacheParam, VO_ExpCustomerInfo.class);
		} else {
			eci = expCustomerInfoMapper.selectByKey(customerId);
			//ehcacheService.put("customerInfoCache", cacheParam, eci);
//			addCache(compId, cacheParam, eci);
		}
		return eci;
	}

	@Override
	public VO_ExpCustomerInfo getAllByCustomerId(Long compId, Long customerId) {
		ExpCustomerInfo eci = getByCustomerId(compId, customerId);
		VO_ExpCustomerInfo vo_ExpCustomerInfo = new VO_ExpCustomerInfo(eci);
		vo_ExpCustomerInfo.setListContacts(queryContactsByCustomerId(compId, customerId));
		List<ExpCustomerMemberRelation> lsExpMember = queryExpMembers(compId, customerId);
		vo_ExpCustomerInfo.setListExpMembers(lsExpMember);
		if(!PubMethod.isEmpty(eci.getDiscountGroupId())){
			vo_ExpCustomerInfo.setGroupName(priceGroupService.getPriceGroupById(eci.getDiscountGroupId()).getGroupName());
		}
		return vo_ExpCustomerInfo;
	}

	@Override
	public void clearDiscountGroupIdByCompId(Long compId,Long discountGroupId){
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("discountGroupId", discountGroupId);
		expCustomerInfoMapper.clearDiscountGroupIdByCompId(map);
		removeCompIdCache(compId);
	}
	
	@Override
	public String insertCustomer(Long compId, String customerName, Short customerType, String customerPhone,Long erpCustomerId,
			Long townId, String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs, Long userId,String parcelId) {
		Long customerId = IdWorker.getIdWorker().nextId();

		ExpCustomerInfo eci = new ExpCustomerInfo();
		eci.setId(customerId);
		eci.setCompId(compId);
		eci.setCustomerName(customerName);
		eci.setCustomerNameSpell(PiyinUtil.cn2py(customerName));
		eci.setCustomerType(customerType);
		eci.setCustomerPhone(customerPhone);
		eci.setErpCustomerId(erpCustomerId);
		eci.setTownId(townId);
		eci.setTownName(townName);
		eci.setDetailedAddress(detailedAddress);
		if (!PubMethod.isEmpty(discountGroupId))
			eci.setDiscountGroupId(Long.valueOf(discountGroupId));
		eci.setAgencyFee(agencyFee);
		
		eci.setSettleType(settleType);
		eci.setCreateTime(new Date());
		eci.setCasMemberId(userId);
		expCustomerInfoMapper.insert(eci);

		//[{"expmemberId":"111","sort":1}，{"expmemberId":"222","sort":2}]  
		insertExpList(compId, customerId, expMemberIds);

		//contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
		insertContactsList(compId, customerId, contactMsgs);
		//contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
		
		//Modify lxw  2015.3.24   修改包裹地址客户信息Modify
		if(!PubMethod.isEmpty(parcelId)){
			parcelInfoService.editAddresseeInfo(customerId, customerName, customerPhone, townName+" "+detailedAddress, userId,parcelId);
		}
	
		removeCompIdCache(compId);
		return String.valueOf(customerId);
	}

	@Override
	public String updateCustomer(Long customerId, Long compId, String customerName, Short customerType,
			String customerPhone, Long townId, String townName,String detailedAddress, Long discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs) {
		
		Boolean isSelf = false;
		List<ExpCustomerInfo>  ls = selectByContactName(compId, customerName);
		for(ExpCustomerInfo exp:ls){
			if(customerId.equals(exp.getId())){
				if(!customerName.equals(exp.getCustomerName()))
					isSelf = true;
			}
		}
		if(isSelf){
			throw new ServiceException("客户姓名在改网点下有重复");
		}
		
		ExpCustomerInfo eci = new ExpCustomerInfo();
		eci.setId(customerId);
		eci.setCompId(compId);
		eci.setCustomerName(customerName);
		eci.setCustomerNameSpell(PiyinUtil.cn2py(customerName));
		eci.setCustomerType(customerType);
		eci.setCustomerPhone(customerPhone);
		eci.setTownId(townId);
		eci.setTownName(townName);
		eci.setDetailedAddress(detailedAddress);
		eci.setDiscountGroupId(discountGroupId);
		eci.setAgencyFee(agencyFee);
		eci.setSettleType(settleType);
		expCustomerInfoMapper.updateByPrimaryKey(eci);

		ExpCustomerMemberRelation expR = new ExpCustomerMemberRelation();
		expR.setCompId(compId);
		expR.setCustomerId(customerId);
		expCustomerMemberRelationMapper.deleteByPrimaryKey(expR);

		expCustomerContactsInfoMapper.deleteByCustomerId(customerId);

		//[{"expmemberId":"111","expMemberName":"收派员1","sort":1}，{"expmemberId":"222","expMemberName":"收派员2","sort":2}]  
		insertExpList(compId, customerId, expMemberIds);

		//contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
		insertContactsList(compId, customerId, contactMsgs);

		removeCompIdCache(compId);

		return "true";
	}

	@Override
	public void insertExpList(Long compId, Long customerId, String expMemberIds) {
		if(PubMethod.isEmpty(expMemberIds))
			return ;
		JSONArray expmemberIdJA = JSONArray.parseArray(expMemberIds);
		List<ExpCustomerMemberRelation> lsExp = new ArrayList<ExpCustomerMemberRelation>();
		for (int i = 0; i < expmemberIdJA.size(); i++) {
			if (!PubMethod.isEmpty(expmemberIdJA.getJSONObject(i).getString("expmemberId"))) {
				ExpCustomerMemberRelation ecmr = new ExpCustomerMemberRelation();
				ecmr.setCompId(compId);
				ecmr.setCustomerId(customerId);
				ecmr.setExpMemberId(expmemberIdJA.getJSONObject(i).getLong("expmemberId"));
				ecmr.setExpMemberName(expmemberIdJA.getJSONObject(i).getString("expMemberName"));
				ecmr.setSort(expmemberIdJA.getJSONObject(i).getShort("sort"));
				lsExp.add(ecmr);
			}
			//expCustomerMemberRelationMapper.insert(ecmr);
		}
		if (lsExp.size() > 0) {
			expCustomerMemberRelationMapper.insertExpList(lsExp);
			removeCompIdCache(compId);
		}

	}

	public void insertContactsList(Long compId, Long customerId, String contactMsgs) {
		if(contactMsgs == null)
			return;
		JSONArray contactMsgsJA = JSONArray.parseArray(contactMsgs);
		Map validMap = new HashMap<String, String>();
		List<ExpCustomerContactsInfo> lsContacts = new ArrayList<ExpCustomerContactsInfo>();
		for (int i = 0; i < contactMsgsJA.size(); i++) {
			if (!(PubMethod.isEmpty(contactMsgsJA.getJSONObject(i).getString("phone")) && PubMethod
					.isEmpty(contactMsgsJA.getJSONObject(i).getString("name")))) {
				ExpCustomerContactsInfo expCustomerContactsInfo = new ExpCustomerContactsInfo();
				expCustomerContactsInfo.setId(IdWorker.getIdWorker().nextId());
				String phone = contactMsgsJA.getJSONObject(i).getString("phone");
				String contactsName = contactMsgsJA.getJSONObject(i).getString("name");
				expCustomerContactsInfo.setContactsName(contactsName);
				expCustomerContactsInfo.setContactsPhone(phone);
				if (validMap.containsKey(phone)) {
					throw new ServiceException("电话号码有重复");
				}
				expCustomerContactsInfo.setCustomerId(customerId);
				expCustomerContactsInfo.setContactsNameSpell(PiyinUtil.cn2py(contactsName));
				lsContacts.add(expCustomerContactsInfo);
			}
			//expCustomerContactsInfoMapper.insert(expCustomerContactsInfo);
		}
		if (lsContacts.size() > 0) {
			expCustomerContactsInfoMapper.insertContactsList(lsContacts);
			removeCompIdCache(compId);
		}
	}

	@Override
	public String deleteCustomer(Long customerId, Long compId) {
		expCustomerInfoMapper.deleteByPrimaryKey(customerId);

		ExpCustomerMemberRelation expR = new ExpCustomerMemberRelation();
		expR.setCompId(compId);
		expR.setCustomerId(customerId);
		expCustomerMemberRelationMapper.deleteByPrimaryKey(expR);

		expCustomerContactsInfoMapper.deleteByPrimaryKey(customerId);

		String cacheParam = "";
		cacheParam += "customerId=" + customerId;
		//ehcacheService.remove("customerInfoCache", cacheParam);
		removeCompIdCache(compId);
		return "true";
	}

	//查询客户下的联系人
	@Override
	public List<ExpCustomerContactsInfo> queryContactsByCustomerId(Long compId, Long customerId) {

		String cacheParam = "";
		cacheParam += "contactscustomerId=" + customerId;

		List<ExpCustomerContactsInfo> listContacts = null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) {
			String result = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			listContacts = jsonArrayToBean(result, ExpCustomerContactsInfo.class);
		} else {
			listContacts = expCustomerContactsInfoMapper.selectContactsByCustomerId(customerId);
			//ehcacheService.put("customerInfoCache", cacheParam, listContacts);
			addCache(compId, cacheParam, listContacts);
		}
		return listContacts;
	}

	@Override
	public Boolean valPhoneExist(Long compId, Long customerId, String phone) {
		List<ExpCustomerContactsInfo> list = queryContactsByCustomerId(compId, customerId);
		for (ExpCustomerContactsInfo info : list) {
			if (phone.trim().equals(info.getContactsPhone().trim())) {
				return true;
			}
		}
		return false;
	}

	//查询客户的取件员
	@Override
	public List<ExpCustomerMemberRelation> queryExpMembers(Long compId, Long customerId) {
		String cacheParam = "";
		cacheParam += "expMembersCustomerId=" + customerId;
		List<ExpCustomerMemberRelation> listExpMembersContacts = null;
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) {
			String result = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			listExpMembersContacts = jsonArrayToBean(result, ExpCustomerMemberRelation.class);
		} else {
			listExpMembersContacts = expCustomerMemberRelationMapper.selectByCustomerId(customerId);
			//ehcacheService.put("customerInfoCache", cacheParam, listExpMembersContacts);
			addCache(compId, cacheParam, listExpMembersContacts);
		}
		return listExpMembersContacts;
	}

	//单独查询联系人
	@Override
	public Map<String, Object> queryContacts(Long compId, String customerName, String contactsName,
			String contactsPhone, Page page) {

		String cacheParam = "";
		Map<String, Object> params = new HashMap<String, Object>();
		cacheParam += "compId=" + compId;
		cacheParam += "customerName=" + customerName;
		cacheParam += "contactsName=" + contactsName;
		cacheParam += "currentPage=" + page.getCurrentPage() + "pageSize=" + page.getPageSize();
		cacheParam += "contactsPhone=" + contactsPhone;

		params.put("compId", compId);
		params.put("customerName", customerName);
		params.put("contactsName", contactsName);
		params.put("contactsPhone", contactsPhone);
		params.put("page", page);

		Integer count = 0;
		List<ExpCustomerContactsInfo> listContacts = new ArrayList<ExpCustomerContactsInfo>();
		if (ehcacheService.getByKey("customerInfoCache", cacheParam)) {
			count = ehcacheService.get("customerInfoCache", cacheParam + "Count", Integer.class);
			String str = ehcacheService.get("customerInfoCache", cacheParam, String.class);
			listContacts = jsonArrayToBean(str, ExpCustomerContactsInfo.class);

		} else {
			count = expCustomerContactsInfoMapper.selectCount(params);
			if (count != 0)
				listContacts = expCustomerContactsInfoMapper.selectCustomers(params);

			addCache(compId, cacheParam, listContacts);
			addCache(compId, cacheParam + "Count", count);

		}

		page.setTotal(count);
		page.setItems(listContacts);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("page", page);
		return resultmap;
	}

	/**
	 * 增加联系人
	 */
	@Override
	public String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone,Long memberId) {
		if (!PubMethod.isEmpty(customerId)) {
			
			List<ExpCustomerContactsInfo> lsContacts = queryContactsByCustomerId(compId,customerId);
			//检测此手机号在此客户下是否存在
			ExpCustomerContactsInfo contactsPhoneExits = null;
			for(ExpCustomerContactsInfo exp :lsContacts){
				if(contactsPhone.equals(exp.getContactsPhone()))
					contactsPhoneExits = exp;
			}
			if(contactsPhoneExits == null){//此手机号在此客户下不存在
				//检测这个客户下面是否超过3个联系人
				if(lsContacts.size()>=3)
					throw new ServiceException("该客户下联系人已满");
				
				ExpCustomerContactsInfo expCustomerContactsInfo = new ExpCustomerContactsInfo();
				expCustomerContactsInfo.setId(IdWorker.getIdWorker().nextId());
				expCustomerContactsInfo.setContactsName(contactsName);
				expCustomerContactsInfo.setContactsNameSpell(PiyinUtil.cn2py(contactsName));
				expCustomerContactsInfo.setContactsPhone(contactsPhone);
				expCustomerContactsInfo.setCustomerId(customerId);
				expCustomerContactsInfoMapper.insert(expCustomerContactsInfo);
			}else{//此手机号在此客户下存在
				ExpCustomerContactsInfo expCustomerContactsInfo = new ExpCustomerContactsInfo();
				expCustomerContactsInfo.setId(contactsPhoneExits.getId());
				expCustomerContactsInfo.setContactsName(contactsName);
				expCustomerContactsInfo.setContactsNameSpell(PiyinUtil.cn2py(contactsName));
				expCustomerContactsInfo.setContactsPhone(contactsPhone);
				expCustomerContactsInfo.setCustomerId(customerId);
				expCustomerContactsInfoMapper.updateByPrimaryKey(expCustomerContactsInfo);
			}
		} else {
			noCustomerInsertContacts(compId,contactsName,contactsPhone,memberId);
		}
		//ehcacheService.remove("customerInfoCache", compId + "Contacts");
		removeCompIdCache(compId);
		//ehcacheService.put("customerInfoCache", String.valueOf(compId));
		return "true";
	}

	//处理添加联系人时，没有客户id的情况
	public void noCustomerInsertContacts(Long compId, String contactsName, String contactsPhone,Long memberId){
		List<ExpCustomerInfo> result = selectByContactName(compId, contactsName);
		if(result.size()>0){//联系人名称 存在
			throw new ServiceException("客户名称已经存在");
			// 0:电商客户,1:企业客户,2:零散客户
//			ExpCustomerInfo customer = result.get(0);
			
			//下面的逻辑有点儿复杂，删除掉
//			if(!Short.valueOf("2").equals(customer.getCustomerType())){//判定此客户类型 不是 零散客户
//				throw new ServiceException("客户名称已经存在");
//			}else{//零散客户
//				List<ExpCustomerContactsInfo> lsContactsByCustomerId = queryContactsByCustomerId(compId, customer.getId());
//				
//				ExpCustomerContactsInfo existContactsInfo = valPhoneInCustomerContacts(lsContactsByCustomerId,contactsPhone);
//				if(existContactsInfo == null){//此客户下不存在此联系人-【手机号】
//					if(lsContactsByCustomerId.size()>=3){//检测这个客户下面超过3个联系人
//						throw new ServiceException("该客户下联系人已满");
//					}else{//检测这个客户下面不超过3个联系人	新增此联系人信息 并且把此联系人下挂到此客户下面
//						 addContacts(compId,customer.getId(), contactsName, contactsPhone,memberId);
//					}
//				}else{//此客户下存在此联系人：【手机号】      更新此联系人信息
//					updateContacts(compId, existContactsInfo.getId(), contactsName, contactsPhone) ;
//				}
//			}
		}else{//联系人名称 不存在
			//新增此客户信息【零散客户】
			//客户信息赋值。客户名称=联系人姓名、客户电话=联系人手机、客户类型=零散客户 
			String contactMsgs = "[{\"name\":\""+contactsName+"\",\"phone\":\""+contactsPhone+"\"}]";
			insertCustomer(compId, contactsName, Short.valueOf("2"), contactsPhone,null,
					null, null, null, null, null,
					Short.valueOf("3"), "[]",contactMsgs, memberId,null);
			//新增此联系人信息 ,并且把此联系人下挂到此客户下
		}
		removeCompIdCache(compId);
	}

	
	@Override
	public List<ExpCustomerContactsInfo> queryContactsJDW(Long compId, String quertCondition) {
		Page pName = new Page();
		pName.setCurrentPage(1);
		pName.setPageSize(2000);
		Map<String,Object> mObj =queryContacts(compId,null,quertCondition,null,pName);
		Page pageName = (Page)mObj.get("page");
		
		Page pPhone = new Page();
		pPhone.setCurrentPage(1);
		pPhone.setPageSize(2000);
		Map<String,Object> mObj2= queryContacts(compId,null,null,quertCondition,pPhone);
		Page pagePhone = (Page)mObj2.get("page");
		pPhone.getItems();
		List<ExpCustomerContactsInfo> result = getSumFromContactsList(pageName.getItems(),pagePhone.getItems());
		for(ExpCustomerContactsInfo info:result){
			info.setIsRegist(noticeHttpClient.ifReg(info.getContactsPhone()));
		}
		return result;
	}

	List<ExpCustomerContactsInfo> getSumFromContactsList(List<ExpCustomerContactsInfo> list1, List<ExpCustomerContactsInfo> list2){
		
		List<ExpCustomerContactsInfo> resultList = new ArrayList<ExpCustomerContactsInfo>();
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
	
	// * <dt><span class="strong">方法描述:</span></dt><dd>验证该手机在客户联系人下是否存在</dd>
	//true 存在，false 不存在
	private ExpCustomerContactsInfo valPhoneInCustomerContacts(List<ExpCustomerContactsInfo> lsContactsByCustomerId,
			String contactsPhone) {
		for(ExpCustomerContactsInfo exp:lsContactsByCustomerId){
			if(exp.getContactsPhone().equals(contactsPhone)){
				return exp;
			}
		}
		return null;
	}

	/**
	 * 修改联系人
	 */
	@Override
	public String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone) {
		ExpCustomerContactsInfo expCustomerContactsInfo = new ExpCustomerContactsInfo();
		expCustomerContactsInfo.setId(contactId);
		expCustomerContactsInfo.setContactsName(contactsName);
		expCustomerContactsInfo.setContactsNameSpell(PiyinUtil.cn2py(contactsName));
		expCustomerContactsInfo.setContactsPhone(contactsPhone);
		expCustomerContactsInfoMapper.updateByPrimaryKey(expCustomerContactsInfo);
		//ehcacheService.remove("customerInfoCache", compId + "Contacts");
		removeCompIdCache(compId);
		return "true";
	}

	@Override
	public void deleteByMemberId(Long compId, Long memberId) {
		removeCompIdCache(compId);
		expCustomerMemberRelationMapper.deleteByMemberId(memberId);
	}

	@Override
	public String deleteContacts(Long compId, Long contactId) {
		expCustomerContactsInfoMapper.deleteByPrimaryKey(contactId);
		//ehcacheService.remove("customerInfoCache", compId + "Contacts");
		removeCompIdCache(compId);
		return "true";
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

	private void addCache(Object compId, String strName, Object cacheContent) {
		List<String> lsCacheList = ehcacheService.get("customerInfoCache", compId.toString(), List.class);
		if (lsCacheList == null)
			lsCacheList = new ArrayList<String>();
		lsCacheList.add(strName);

		ehcacheService.put("customerInfoCache", strName, cacheContent);
		ehcacheService.put("customerInfoCache", compId.toString(), lsCacheList);

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

	@Override
	public String insertCustomerV4(Long compId, String customerName,Byte gender,
			Short customerType, String customerPhone, String townName,
			String compName, String iphoneTwo, String iphoneThree,
			String addressTwo, String addressThree, String expMemberIds,Long memberId) {
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
		expCustomerInfoMapper.insertV4(eci);

		//[{"expmemberId":"111","sort":1}，{"expmemberId":"222","sort":2}]  
		insertExpList(compId, customerId, expMemberIds);

		//contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
//		insertContactsList(compId, customerId, contactMsgs);
		
		//Modify lxw  2015.3.24   修改包裹地址客户信息Modify
//		if(!PubMethod.isEmpty(parcelId)){
//			parcelInfoService.editAddresseeInfo(customerId, customerName, customerPhone, townName+" "+detailedAddress, userId,parcelId);
//		}
	
		removeCompIdCache(memberId);
		return String.valueOf(customerId);
	}

	@Override
	public String deleteCustomerV4(Long customerId,Long compId,Long memberId) {
		expCustomerInfoMapper.deleteByPrimaryKey(customerId);
		removeCompIdCache(compId);
		removeCompIdCache(memberId);
		return String.valueOf(customerId);
	}

	@Override
	public List<ExpCustomerInfo> selectCustomer(Long compId,Long memberId) {
		List<ExpCustomerInfo> list=expCustomerInfoMapper.selectCustomer(memberId);
		return list;
	}

	@Override
	public String editCustomer(Long customerId, Long compId,
			String customerName, Short customerType, String customerPhone,Byte gender,
			String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds,Long memberId) {
		ExpCustomerInfo eci = new ExpCustomerInfo();
		eci.setId(customerId);
		eci.setCompId(compId);
		eci.setCustomerName(customerName);
		eci.setCustomerNameSpell(PiyinUtil.cn2py(customerName));
		eci.setCustomerType(customerType);
		eci.setCustomerPhone(customerPhone);
		eci.setTownName(townName);
		eci.setDetailedAddress(townName);//townName代表详细地址
		eci.setAddressThress(addressThree);
		eci.setAddressTwo(addressTwo);
		eci.setIphoneThree(iphoneThree);
		eci.setGender(gender);
		eci.setIphoneTwo(iphoneTwo);
		eci.setCompAddress(compName);
		eci.setMemberId(memberId);
		expCustomerInfoMapper.editCustomer(eci);
		removeCompIdCache(compId);
		removeCompIdCache(memberId);
		return String.valueOf(customerId);
	}

	@Override
	public String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("customerType", customerType);
		//添加标签
		String[] str=addCustomerIds.split(",");
		for(String customerId:str){
			map.put("customerId", customerId);
			expCustomerInfoMapper.addCustomerType(map);
		}
		//删除标签
		String[] strd=delCustomerIds.split(",");
		for(String customerId:strd){
			map.put("customerId", customerId);
			expCustomerInfoMapper.deleteCustomerType(map);
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
			Map<String, Object> map1=new HashMap<String, Object>();
			map1.put("memberId", memberId);
			map1.put("phone", phone);
			List<ExpCustomerInfo> list=expCustomerInfoMapper.selectCustomersByMobile(map1);
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
			expCustomerInfoMapper.insertV4(eci);
			removeCompIdCache(memberId);
		}
		
		return "1";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> saomaCreate(String tradeNum, Double tradeTotalAmount) {
		//取件任务id查出来memberId（par_task_info)，根据memberId查出账户id（okdiuser表）
//		Map<String, Object> mapList=parTaskInfoMapperV4.queryTakeTaskDetailByTaskId(Long.parseLong(tradeNum));
		
		
		if(PubMethod.isEmpty(tradeNum)){
			logger.info("扫码支付--任务号不可为空");
			throw new ServiceException("任务号不可为空");
		}
		if(PubMethod.isEmpty(tradeTotalAmount)){
			logger.info("扫码支付--金额有可为空");
			throw new ServiceException("金额有可为空");
		}
		
		Long orderNum=Long.parseLong(tradeNum);
		
		Query query=new Query();
		query.addCriteria(Criteria.where("uid").is(Long.parseLong(tradeNum)));
		ParTaskInfo parTaskInfo=mongoTemplate.findOne(query, ParTaskInfo.class);
		
		Long actorMemberId= parTaskInfo.getActorMemberId();//取件员id
		Long customberId= parTaskInfo.getCustomerId();//客户id
		
		logger.info("扫码支付--付款任务Id:"+tradeNum+"  收款方memberId:"+actorMemberId+"  付款方memberId:"+customberId);
		/*if(PubMethod.isEmpty(customberId)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("appid","wxd11b57b3d88a4c21");
			map.put("channelsCode","weixinpay");
			map.put("code_url","weixin://wxpay/bizpayurl?pr=cSGldwS");
			map.put("headUrl","http://cas.okdit.net/nfs_data/mob/head/211507086671872.jpg");
			map.put("name","测试名！！");
			map.put("noncestr","pe7vlqqa21i5r1a630wq6dx0k5wvhxjb");
			map.put("package","Sign=WXPay");
			map.put("partnerid","1248131301");
			map.put("prepayId","wx20160512153242a8a1fc298c0868951047");
			map.put("sign","020D970C9C3C9D14A6B19223F89A69E6");
			map.put("tid","106204620622147584");
			map.put("timestamp","1463038361");
			map.put("compName","测试数据！！");
			return map;
		}*/
		
		//判断是否结束此单 或者是否支付过
		if(parTaskInfo.getTaskIsEnd()==1){
			logger.info("扫码支付--此任务已结束");
			throw new ServiceException("此任务已结束");
		}
		if(parTaskInfo.getPayStatus()==21||parTaskInfo.getPayStatus()==20){
			logger.info("扫码支付--此任务已微信支付过");
			throw new ServiceException("此任务微信已支付");
		}
		
		String url = ucenterUrl;
		//String url = payUrl;
		String methodName="/user/queryAccountId";
		Map<String, String> map1 = new HashMap<String, String>();
		//获取收款方财务账号
		map1.put("memberId",String.valueOf(actorMemberId) );
		String resultActorMemberId = this.Post(url+methodName, map1);
		logger.info("=====================================resultActorMemberId: "+resultActorMemberId);
		String payNum2 = parTaskInfo.getPayNum();
		System.out.println("======================= payNum2: "+payNum2);
		BigDecimal amount = parTaskInfo.getAmount();
		System.out.println("======================= amount: "+amount);
		System.out.println("=============================tradeTotalAmount: "+tradeTotalAmount);
		
		int i=0;
		//*********************如果paynum 不为空，判断 输入的金额 与 取件任务中的   amount金额是否相同，如果不相同，要先调用 支付系统的 订单取消接口
	     if(!PubMethod.isEmpty(parTaskInfo.getPayNum()) && !PubMethod.isEmpty(parTaskInfo.getAmount()) && !String.valueOf(tradeTotalAmount).equals(parTaskInfo.getAmount().toString())){
	    	 logger.info("===============parTaskInfo.getPayNum(): "+ parTaskInfo.getPayNum());
		    Map<String, String> cancelMap=new HashMap<String, String>();
		    cancelMap.put("tid", parTaskInfo.getPayNum());
		    cancelMap.put("accountId", resultActorMemberId);
		    logger.info("=============开始调用 ==ws/trade/cancel: ");
		    String cancelMethodName="ws/trade/cancel";//http://pay.okdit.net/ws/trade/cancel
		    //http://pay.okdit.net/okdipay/
		    String cancelResult=this.Post(payUrl+cancelMethodName, cancelMap);
		    logger.info("=============调用返回请求 ==cancelResult: "+cancelResult);
//		    String success=String.valueOf(JSON.parseObject(cancelResult).get("success"));

		    orderNum=IdWorker.getIdWorker().nextId();//取消重新生成orderNum
		    logger.info("-=================================如果paynum 不为空，判断 输入的金额 与 取件任务中的   amount金额是否相同，如果不相同，要先调用 支付系统的 订单取消接口");
		  
		  i=1;
	     }else if(PubMethod.isEmpty(parTaskInfo.getPayNum())){
	    	 i=1;
	     }
		
		//获取付款方财务账号
		String resultCustomberId = "";
		if(!PubMethod.isEmpty(customberId)){
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("memberId",String.valueOf(customberId) );
			resultCustomberId = this.Post(url+methodName, map2);
		}
		
		logger.info("扫码支付--resultActorMemberId:"+resultActorMemberId+"resultCustomberId"+resultCustomberId);
		String nameMethod="/user/queryNameByMemberId";
		String name=this.Post(url+nameMethod, map1);
		
		Long compId=basEmployeeRelationMapper.findCompIdByMemberId(actorMemberId);
		logger.info("扫码支付--收派员公司id"+compId);
		BasCompInfo compName=basCompInfoMapper.findById(compId);
		logger.info("扫码支付--收派员公司compName"+compName);
		
//****************如果前面进行了取消订单操作，那么orderNum重新生成
		
		logger.info("扫码支付--任务id"+tradeNum+"       生成的orderNum："+orderNum);
		System.out.println("任务id"+tradeNum+"       生成的orderNum："+orderNum);
		//如果金额相同，获取之前的交易号
		if(!PubMethod.isEmpty(parTaskInfo.getOrderNum())&&!PubMethod.isEmpty(parTaskInfo.getAmount()) && String.valueOf(tradeTotalAmount).equals(parTaskInfo.getAmount().toString())){
			orderNum=parTaskInfo.getOrderNum();
		}

		Update update=new Update();
		update.set("orderNum", orderNum);
		mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
		//调用财务系统
		String payUrl1 = payUrl;
		String methodName2="ws/trade/saomaCreate";
		Map<String, String> payMap = new HashMap<String, String>();
		payMap.put("tradeNum",String.valueOf(orderNum));
		payMap.put("tradeTotalAmount",String.valueOf(tradeTotalAmount) );
		payMap.put("inAccountId",resultActorMemberId );
		payMap.put("outAccountId",resultCustomberId);
		payMap.put("title","扫码支付");
		payMap.put("remark","扫码支付订单");
		payMap.put("channelsCode","weixinpay");
		payMap.put("platformId","100002");
		payMap.put("outerTradeType","200001");
		payMap.put("activeTime",payCodeTime);
		String result = this.Post(payUrl1+methodName2, payMap);
		
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("操作异常！");
		}
		String success=String.valueOf(JSON.parseObject(result).get("success"));
		if(!"true".equals(success)){
			String errorMsg=String.valueOf(JSON.parseObject(result).get("error_msg"));
			String errorCode=String.valueOf(JSON.parseObject(result).get("error_code"));
			throw new ServiceException(errorCode,errorMsg);
		}
		
		Map<String,Object> list = (Map<String, Object>) JSON.parseObject(result).get("data");
		
		String payNum=String.valueOf(list.get("tid"));
		logger.info("扫码支付--任务id"+tradeNum+"       payNum："+payNum);
//*******************保存金额 
		Update update1=new Update();
		update1.set("payNum", payNum);
		update1.set("amount", tradeTotalAmount);
		
		
		
		// 记录
		if(i==1||(!PubMethod.isEmpty(parTaskInfo.getPayNum())&&!payNum.equals(parTaskInfo.getPayNum()))){
			ParTaskOrderRecord record=new ParTaskOrderRecord();
			record.setAmount(BigDecimal.valueOf(tradeTotalAmount));
			record.setCreateTime(new Date());
			record.setOrderNum(orderNum);
			record.setPayNum(payNum);
			record.setTaskId(tradeNum);
			mongoTemplate.save(record);
		}
		mongoTemplate.updateFirst(query, update1, ParTaskInfo.class);
		
		list.put("headUrl", constPool.getHeadImgPath()+actorMemberId+".jpg");
		list.put("name", name);
		list.put("compName",compName.getCompName());
		return list;
	}

	@Override
	public Object parseResult(String info) {
		return null;
	}
	
}
