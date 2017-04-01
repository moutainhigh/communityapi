package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.DispatchParMapper;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.DicAddressService;
import net.okdi.api.service.DispatchParService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.vo.VO_DispatchPar;
import net.okdi.api.vo.VO_DispatchSingleParInfo;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DateUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service("dispatchParService")
public class DispatchParServiceImpl implements DispatchParService {

	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private DicAddressService dicAddressService;

	@Autowired
	private DispatchParMapper dispatchParMapper;

	@Autowired
	private ParcelInfoService parcelInfoService;
	/**
	 * @Method: findPars 
	 * @param actualSendMember	派送员
	 * @param addresseeName		收件人姓名
	 * @param addresseePhone	收件人电话
	 * @param startDate			开始时间：20111111
	 * @param endDate			结束时间：20121111
	 * @param expWaybillNum		快递单号
	 * @param currentPage		当前页
	 * @param pageSize			页面大小
	 * @param tackingStatus		派送状态   1--待提，2--在派，3--已签收，4--异常：客户拒收
	 * @return 
	 * @see net.okdi.api.service.DispatchParService#findPars(java.lang.Long, java.lang.String, java.lang.String, java.lang.Short, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page findPars(Long compId, Long actualSendMember, String addresseeName, String addresseeMobile,
			String startDate, String endDate, String expWaybillNum, Integer currentPage, Integer pageSize,
			Short tackingStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		map.put("actualSendMember", actualSendMember);
		map.put("addresseeName", addresseeName);
		map.put("addresseeMobile", addresseeMobile);
		//map.put("parcelStatus",flag);
		if (startDate != null && !"".equals(startDate)) {
			map.put("startDate", DateUtil.getZeroTimeOfDay(startDate));
		}
		if (endDate != null && !"".equals(endDate)) {
			map.put("endDate", DateUtil.getEndTimeOfDay(endDate));
		}
		map.put("expWaybillNum", expWaybillNum);
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		map.put("page", page);

		switch (tackingStatus) {
		case 0://派送状态       所有 			不根据tackingStatus这个条件查询
			map.put("tackingStatus", "");
			break;
		case 1://派送状态    0--待提 		tackingStatus 为null
			map.put("tackingStatus", "");
			map.put("statusTacking", "1111");//这里做一个逻辑控制，待提的只能是tackingStatus为空的
			break;
		case 2://派送状态    1--在派 		tackingStatus 为0
			map.put("tackingStatus", "0");
			break;
		case 3://派送状态	2--已签收	tackingStatus 为1
			map.put("tackingStatus", "1");
			break;
		//case 4://为异常情况，需要先查询任务相关的信息
			//map.put("tackingStatus", "0");
			//map.put("parcelStatus", "12");
			//break;
		default:
			throw new RuntimeException("查询标记不是 1--待提，2--在派，3--已签收：客户拒收");
		}

		removeAllEmptyMap(map);

		String countKey = compId + "_actualSendMember" + actualSendMember + "_addresseeName" + addresseeName + "_addresseeMobile" + addresseeMobile + "_tackingStatus"
				+ tackingStatus + "_startDate" + startDate + "_endDate" + endDate + "_expWaybillNum" + expWaybillNum + "_statusTacking" 
				+map.get("statusTacking")
				+"_count";
		Integer count = ehcacheService.get("expressserviceCache", String.valueOf(countKey), Integer.class);
		if (count == null) {
			count = dispatchParMapper.findCount(map);
			putCache(countKey, count);
		}
		page.setTotal(count);

		String listKey = compId + "_actualSendMember" + actualSendMember + "_addresseeName" + addresseeName + "_addresseeMobile" + addresseeMobile + "_tackingStatus"
				+ tackingStatus + "_startDate" + startDate + "_endDate" + endDate + "_expWaybillNum" + expWaybillNum + "_statusTacking" 
				+map.get("statusTacking")
				+ "_" + currentPage + "_"
				+ pageSize + "_list";

		List<VO_DispatchPar> listdispatches = getListFromCache(listKey, VO_DispatchPar.class);
		if (listdispatches == null) {
			listdispatches = dispatchParMapper.findDispatches(map);
			for (VO_DispatchPar p : listdispatches) {
				try {
					if(p.getActualSendMember()!=null){
						MemberInfo memberInfo = memberInfoService.getMemberInfoById(Long.valueOf(p.getActualSendMember()));
						p.setMemberName(memberInfo.getMemberName());
					}
				} catch (Exception e) {
				}

				Short s = p.getTackingStatus();
				
				if (s == null) {
					p.setTackingStatus(Short.valueOf("1"));
				} else if (Short.valueOf("0").equals(s) ) {
					p.setTackingStatus(Short.valueOf("2"));
				} else if (Short.valueOf("1").equals(s)) {
					p.setTackingStatus(Short.valueOf("3"));
				}
			}
			putCache(listKey, listdispatches);
		}
		page.setItems(listdispatches);
		return page;
	}

	@Override
	public VO_DispatchSingleParInfo findParById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		VO_DispatchSingleParInfo par = ehcacheService.get("expressserviceCache", String.valueOf(id),
				VO_DispatchSingleParInfo.class);
		if (par == null) {
			par = dispatchParMapper.findParById(map);
			try {
				Long addrSendId = par.getAddresseeAddressId();
				if (addrSendId != null) {
					Map<String, Object> objReceive = dicAddressService.getObjectByPrimaryKey(addrSendId);
					par.setAddresseeAddressInfo(objReceive.get("addressName").toString());
				}
				Long addrReceiveId = par.getSendAddressId();
				if (addrReceiveId != null) {
					Map<String, Object> objSender = dicAddressService.getObjectByPrimaryKey(addrReceiveId);
					par.setSendAddressInfo(objSender.get("addressName").toString());
				}
			} catch (Exception e) {
			}
			putCache(String.valueOf(id), par);
		}
		return par;
	}

	@Override
	public void addPar(Long createUserId, String actualSendMember, Long compId, Long netId, String expWaybillNum,
			BigDecimal codAmount, Long addresseeAddressId, String addresseeAddress, String addresseeMobile,
			String addresseeName, Long sendAddressId, String sendAddress, String sendMobile, String sendName,
			Short freightPaymentMethod, BigDecimal freight, BigDecimal packingCharges, BigDecimal chareWeightForsender,
			BigDecimal insureAmount, BigDecimal pricePremium, String parcelRemark) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("netId", netId);
		map.put("expWaybillNum", expWaybillNum);
		List<VO_DispatchPar> lsdisps = dispatchParMapper.findParByExpWaybill(map);
		if (lsdisps !=null && lsdisps.size() > 0) {
			throw new ServiceException("同一快递网络的派送快递单号不能重复");
		}
		
		Long idWorkerId = IdWorker.getIdWorker().nextId();
		map.put("id", idWorkerId);

		map.put("actualSendMember", actualSendMember);
		map.put("expWaybillNum", expWaybillNum);

		map.put("addresseeAddressId", addresseeAddressId);
		map.put("codAmount", codAmount);
		map.put("addresseeAddressId", addresseeAddressId);
		map.put("addresseeAddress", addresseeAddress);
		map.put("addresseeMobile", addresseeMobile);
		map.put("addresseeName", addresseeName);
		map.put("sendAddressId", sendAddressId);
		map.put("sendAddress", sendAddress);
		map.put("sendMobile", sendMobile);
		map.put("sendName", sendName);

		map.put("freightPaymentMethod", freightPaymentMethod);
		map.put("freight", freight);
		map.put("packingCharges", packingCharges);
		map.put("chareWeightForsender", chareWeightForsender);
		map.put("insureAmount", insureAmount);
		map.put("pricePremium", pricePremium);
		map.put("parcelRemark", parcelRemark);

		//create_user_id,comp_id,create_time,parcel_status,net_id
		map.put("createUserId", createUserId);
		map.put("compId", compId);
		map.put("createTime", new Date());
		map.put("parcelStatus", "10");//包裹状态默认置10
		map.put("netId", netId);

		removeAllEmptyMap(map);

		dispatchParMapper.insertPar(map);
		dispatchParMapper.insertParAddress(map);
		removeCache(idWorkerId,Long.valueOf(actualSendMember));
	}

	@Override
	public void updatePar(Long id, Long netId, String actualSendMember, String expWaybillNum, BigDecimal codAmount,
			Long addresseeAddressId, String addresseeAddress, String addresseeMobile, String addresseeName,
			Long sendAddressId, String sendAddress, String sendMobile, String sendName, Short freightPaymentMethod,
			BigDecimal freight, BigDecimal packingCharges, BigDecimal chareWeightForsender, BigDecimal insureAmount,
			BigDecimal pricePremium, String parcelRemark) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);

		//修改快递单号为同网络中已存在的派送单号，点击保存按钮

		VO_DispatchSingleParInfo singlePar = findParById(id);
		
		map.put("netId", netId);
		map.put("expWaybillNum", expWaybillNum);
		List<VO_DispatchPar> lsdisps = dispatchParMapper.findParByExpWaybill(map);
		if(lsdisps!=null ){
			for(VO_DispatchPar vo :lsdisps){
				if(expWaybillNum.equals(vo.getExpWaybillNum())&&!singlePar.getId().equals(vo.getId())){
					throw new ServiceException("同一快递网络的派送快递单号不能重复");
				}
			}
		}
		
		map.put("actualSendMember", actualSendMember);
		map.put("expWaybillNum", expWaybillNum);
		
		map.put("addresseeAddressId", addresseeAddressId);
		map.put("codAmount", codAmount);
		map.put("addresseeAddressId", addresseeAddressId);
		map.put("addresseeAddress", addresseeAddress);
		map.put("addresseeMobile", addresseeMobile);
		map.put("addresseeName", addresseeName);
		map.put("sendAddressId", sendAddressId);
		map.put("sendAddress", sendAddress);
		map.put("sendMobile", sendMobile);
		map.put("sendName", sendName);

		map.put("freightPaymentMethod", freightPaymentMethod);
		map.put("freight", freight);
		map.put("packingCharges", packingCharges);
		map.put("chareWeightForsender", chareWeightForsender);
		map.put("insureAmount", insureAmount);
		map.put("pricePremium", pricePremium);
		map.put("parcelRemark", parcelRemark);

		removeAllEmptyMap(map);

		dispatchParMapper.updatePar(map);
		dispatchParMapper.updateParAddress(map);
		
		try{
			removeCache(id,Long.valueOf(actualSendMember));
		}catch (Exception e) {
		}
		try {
			removeCache(id,Long.valueOf(singlePar.getActualSendMember()));
		} catch (Exception e) {
		}
	}

	@Override
	public void removeTaskCache() {
		//这里不再使用任务条件查询,但是作为开放接口，已经实现，所以不再删除
	}

	private void putCache(String key, Object value) {
		this.ehcacheService.put("expressserviceCache", key, value);
	}

	private <T> List<T> getListFromCache(String key, Class<T> className) {
		try {
			String result = ehcacheService.get("expressserviceCache", key, String.class);
			JSONArray jsa = JSONArray.parseArray(result);
			List<T> list = new ArrayList<T>();
			for (int i = 0; i < jsa.size(); i++) {
				T jso = JSONObject.parseObject(jsa.getString(i), className);
				list.add(jso);
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void clearDispatchCache() {
		this.ehcacheService.removeAll("expressserviceCache");//takeParcelIdsCacheByMemberId
	}
	
	public void removeCache(Long parcelId , Long memberId) {
		this.ehcacheService.removeAll("expressserviceCache");//takeParcelIdsCacheByMemberId
		parcelInfoService.removeParcelCache(parcelId, memberId);
		ehcacheService.remove("sendTaskCache", memberId+"");
	}

	private void removeAllEmptyMap(Map<String, Object> map) {
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (PubMethod.isEmpty(map.get(key)) || "".equals(map.get(key))) {
				map.put(key, null);
			}
		}
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", "bbb");
		map.put("bbb", "");
		map.put("12", "11");
		//removeAllEmptyMap(map);
		System.out.println(map.get("bbb"));
	}

}
