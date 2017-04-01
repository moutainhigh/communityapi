/**  
 * @Project: openapi
 * @Title: ParcelSupplementServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2015-1-28 下午7:55:16
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.ParParceladdressMapper;
import net.okdi.api.dao.ParParcelinfoMapper;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.ExpCustomerInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelconnection;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.ParcelSupplementService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DateUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class ParcelSupplementServiceImpl implements ParcelSupplementService {
	
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private ParParcelinfoMapper parParcelinfoMapper;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
    private BasNetInfoMapper basNetInfoMapper;
	@Autowired
	private ExpCustomerInfoService expCustomerInfoService;
	@Autowired
	private ParParceladdressMapper parParceladdressMapper;

	@Override
	public Page queryParcelList(String createTime, String expWaybillNum, Long memberId, String customerName, 
			String senderPhone, Long parcelStatus, Long compId, Long departId, Page page) throws ServiceException {
		if(PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.ParcelSupplementServiceImpl.queryParcelList.001", "当前站点不存在");
		}
		//cache参数拼接
		String cacheParam = "";
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		//参数
		Map<String,Object> map = new HashMap<String, Object>();
		if(!PubMethod.isEmpty(createTime)) {
			map.put("createTimeStart", DateUtil.getZeroTimeOfDay(createTime));
			cacheParam += "createTimeStart="+createTime;
		} else {
			map.put("createTimeStart", null);
			cacheParam += "createTimeStart=";
		}
		if(!PubMethod.isEmpty(createTime)) {
			map.put("createTimeEnd", DateUtil.getEndTimeOfDay(createTime));
			cacheParam += "createTimeEnd="+createTime;
		} else {
			map.put("createTimeEnd", null);
			cacheParam += "createTimeEnd=";
		}
		if(!PubMethod.isEmpty(expWaybillNum)) {
			map.put("expWaybillNum", expWaybillNum);
		} else {
			map.put("expWaybillNum", "");
		}
		cacheParam += "expWaybillNum="+expWaybillNum;
		if(!PubMethod.isEmpty(memberId)) {
			map.put("memberId", memberId);
		} else {
			map.put("memberId", "");
		}
		cacheParam += "memberId="+memberId;
		if(!PubMethod.isEmpty(customerName)) {
			map.put("customerName", customerName);
		} else {
			map.put("customerName", "");
		}
		cacheParam += "customerName="+customerName;
		if(!PubMethod.isEmpty(senderPhone)) {
			map.put("senderPhone", senderPhone);
		} else {
			map.put("senderPhone", "");
		}
		cacheParam += "senderPhone="+senderPhone;
		if(!PubMethod.isEmpty(parcelStatus)) {
			map.put("parcelStatus", parcelStatus);
		} else {
			map.put("parcelStatus", "");
		}
		cacheParam += "parcelStatus="+parcelStatus;
		map.put("page", page);
		cacheParam += "currentPage="+page.getCurrentPage()+"pageSize="+page.getPageSize();
		map.put("compId", compId);
		cacheParam += "compId="+compId;
		String cacheCount = cacheParam + "Count";
		//计算页数
		int pageNum = 0;
		//统计数量
		int totalCount = queryParcelListCount(map, cacheCount, compId);
		//查询包裹列表
		List<Map> parcelIdList = queryParcelIdList(map, cacheParam, compId);
		if(!PubMethod.isEmpty(parcelIdList)) {
			ParParcelinfo parcelInfo = null;
			ParParceladdress parceladdr = null;
			ExpCustomerInfo expcustomerinfo = null;
			for(Map parcel : parcelIdList) {
				if(parcel != null && parcel.get("parcelId") != null && !"".equals(parcel.get("parcelId").toString())) {
					//包裹信息
					parcelInfo = parcelInfoService.getParcelInfoById(Long.parseLong(parcel.get("parcelId").toString()));
					//包裹地址
					parceladdr = parcelInfoService.getParParceladdressById(Long.parseLong(parcel.get("parcelId").toString()));
					if(parcel.get("customerId") != null && !"".equals(parcel.get("customerId").toString())) {
						//查询客户信息
						expcustomerinfo = expCustomerInfoService.getByCustomerId(compId, Long.parseLong(parcel.get("customerId").toString()));
					} else {
						expcustomerinfo = null;
					}
					//组装数据
					resultList.add(assembleData(parcelInfo, parceladdr, expcustomerinfo));
				}
			}
		}
		if(totalCount % page.getPageSize() > 0) {
			pageNum = (totalCount/page.getPageSize()) + 1;
		} else {
			pageNum = (totalCount/page.getPageSize());
		}
		page.setItems(resultList);
		page.setPageCount(pageNum);
		page.setTotal(totalCount);
		return page;
	}
	
	private Map<String,Object> assembleData(ParParcelinfo parcelInfo, ParParceladdress parceladdr, ExpCustomerInfo expcustomerinfo) throws ServiceException {
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo memberinfo = null;
		BasNetInfo netInfo = null;
		if(!PubMethod.isEmpty(parcelInfo)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("id", parcelInfo.getId());
			map.put("expWaybillNum", parcelInfo.getExpWaybillNum());
			map.put("takeMemberId", parcelInfo.getActualTakeMember());
			memberinfo = memberInfoService.getMemberInfoById(parcelInfo.getActualTakeMember());
			if(!PubMethod.isEmpty(memberinfo)) {
				map.put("takeMemberName", memberinfo.getMemberName());
			} else {
				map.put("takeMemberName", "");
			}
			if(!PubMethod.isEmpty(parcelInfo.getCreateTime())) {
				map.put("createTime", sdf.format(parcelInfo.getCreateTime()));
			} else {
				map.put("createTime", "");
			}
			map.put("chareWeightForsender", parcelInfo.getChareWeightForsender());
			//map.put("goodNum", parcelInfo.getGoodsNum());
			map.put("signResult", parcelInfo.getParcelStatus());
			map.put("netId", parcelInfo.getNetId());
			netInfo = ehcacheService.get("netCache", String.valueOf(parcelInfo.getNetId()), BasNetInfo.class);
			if(PubMethod.isEmpty(netInfo)){
				netInfo = basNetInfoMapper.findById(parcelInfo.getNetId());
			}
			if(!PubMethod.isEmpty(netInfo)) {
				map.put("netName", netInfo.getNetName());
				map.put("netCode", netInfo.getCode());
			} else {
				map.put("netCode", "");
				map.put("netName", "");
			}
			map.put("freightPaymentMethod", parcelInfo.getFreightPaymentMethod());
			map.put("signGoodsTotal", parcelInfo.getFreight());
			map.put("actualCodAmount", parcelInfo.getCodAmount());
			map.put("insureAmount",parcelInfo.getInsureAmount());
			map.put("pricePremium", parcelInfo.getPricePremium());
			map.put("packingCharges", parcelInfo.getPackingCharges());
			map.put("parcelRemark", parcelInfo.getParcelRemark());
			map.put("noFly", parcelInfo.getNoFly());
		} else {
			map.put("id", "");
			map.put("expWaybillNum", "");
			map.put("takeMemberName", "");
			map.put("takeMemberId", "");
			map.put("createTime", "");
			map.put("chareWeightForsender", "");
			map.put("signResult", "");
			map.put("netId", "");
			map.put("netCode", "");
			map.put("goodNum", "");
			map.put("freightPaymentMethod", "");
			map.put("signGoodsTotal", "");
			map.put("actualCodAmount", "");
			map.put("pricePremium", "");
			map.put("packingCharges", "");
			map.put("parcelRemark", "");
			map.put("noFly", "");
			map.put("insureAmount","");
		}
		if(!PubMethod.isEmpty(parceladdr)) {
			map.put("sendName", parceladdr.getSendName());
			map.put("sendMobile", parceladdr.getSendMobile());
			map.put("sendAddressId", parceladdr.getSendAddressId());
			map.put("sendAddress", parceladdr.getSendAddress());
			map.put("addresseeName", parceladdr.getAddresseeName());
			map.put("addresseeAddressId",parceladdr.getAddresseeAddressId());
			map.put("addresseeAddress",parceladdr.getAddresseeAddress());
			map.put("addresseeMobile",parceladdr.getAddresseeMobile());
			map.put("customerId", parceladdr.getSendCustomerId());
		} else {
			map.put("sendName", "");
			map.put("sendMobile", "");
			map.put("sendAddressId", "");
			map.put("sendAddress", "");
			map.put("addresseeName", "");
			map.put("addresseeAddressId","");
			map.put("addresseeAddress","");
			map.put("addresseeMobile","");
			map.put("customerId", "");
		}
		if(!PubMethod.isEmpty(expcustomerinfo)) {
			map.put("customerName", expcustomerinfo.getCustomerName());
			map.put("customerType", expcustomerinfo.getCustomerType());
		} else {
			map.put("customerName", "");
			map.put("customerType", "");
		}
		return map;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹id结果集</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 下午1:36:41</dd>
	 * @param map
	 * @param cacheParam
	 * @param compId
	 * @return
	 * @since v1.0
	 */
	private List<Map> queryParcelIdList(Map<String,Object> map, String cacheParam, Long compId) throws ServiceException {
		//id对应list
		List<Map> resultList = null;
		String cachelist = ehcacheService.get("takeParcelIdsCacheByMemberId", cacheParam, String.class);
		if(!PubMethod.isEmpty(cachelist)) {
			resultList = JSON.parseArray(cachelist, Map.class);
		} else {
			//查询数据库
			resultList = parParcelinfoMapper.queryParcelInfoList(map);
			ehcacheService.put("takeParcelIdsCacheByMemberId", cacheParam, resultList);
			addCacheList("takeParcelIdsCacheByMemberId", compId, cacheParam);
		}
		return resultList;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>统计包裹列表总数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 下午1:37:06</dd>
	 * @param map
	 * @param cacheCount
	 * @param compId
	 * @return
	 * @since v1.0
	 */
	private int queryParcelListCount(Map<String,Object> map, String cacheCount, Long compId) throws ServiceException {
		int totalCount = 0;
		String strCount = ehcacheService.get("takeParcelIdsCacheByMemberId", cacheCount, String.class);
		Integer count = new Integer(0);
		if(!PubMethod.isEmpty(strCount)) {
			count = Integer.parseInt(strCount);
			if(!PubMethod.isEmpty(count)) {
				totalCount = count.intValue();
			}
		} else {
			//查询数据库
			totalCount = parParcelinfoMapper.queryParcelInfoListCount(map);
			ehcacheService.put("takeParcelIdsCacheByMemberId", cacheCount, totalCount);
			addCacheList("takeParcelIdsCacheByMemberId", compId, cacheCount);
		}
		return totalCount;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询条件加入缓存</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 下午1:25:34</dd>
	 * @param cacheName 缓存名称
	 * @param compId 站点id
	 * @param addKey 条件
	 * @since v1.0
	 */
	private void addCacheList(String cacheName, Long compId, String addKey) throws ServiceException {
		List<String> compQueryList = null;
		compQueryList = ehcacheService.get(cacheName, String.valueOf(compId), ArrayList.class);
		if(PubMethod.isEmpty(compQueryList)) {
			compQueryList = new ArrayList<String>();
		}
		if(!compQueryList.contains(addKey)) {
			compQueryList.add(addKey);
		}
		//存入缓存
		ehcacheService.put(cacheName, String.valueOf(compId), compQueryList);
	}

	@Override
	public Map<String, Object> queryParcelDetail(Long compId, Long parcelId, String type) throws ServiceException {
		if(PubMethod.isEmpty(parcelId)) {
			throw new ServiceException("openapi.ParcelSupplementServiceImpl.queryParcelDetail.001", "请选择包裹查看详情");
		}
		Map<String, Object> resultMap = null;
		//包裹信息
		ParParcelinfo parcelInfo = parcelInfoService.getParcelInfoById(parcelId);
		//包裹地址
		ParParceladdress parceladdr = parcelInfoService.getParParceladdressById(parcelId);
		ExpCustomerInfo expcustomerinfo = null;
		if(!PubMethod.isEmpty(parceladdr) && "comp".equals(type)) {
			//查询客户信息
			expcustomerinfo = expCustomerInfoService.getByCustomerId(compId, parceladdr.getSendCustomerId());
		}
		resultMap = assembleData(parcelInfo, parceladdr, expcustomerinfo);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> addOrUpdateParcelInfo(Long id,Long sendCustomerId, String expWaybillNum, Long compId, 
			Long netId, String addresseeName, String addresseeMobile, Long addresseeAddressId, String addresseeAddress, 
			String sendName, String sendMobile, Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, 
			BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges, Short freightPaymentMethod, 
			String parcelRemark, Long createUserId, Long actualTakeMember, BigDecimal actualCodAmount, Short noFly, BigDecimal signGoodsTotal) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Long idReturn = null;
		ParParcelinfo parcelInfo = new ParParcelinfo();
		ParParceladdress parceladdress = new ParParceladdress();
		
		parcelInfo.setExpWaybillNum(expWaybillNum);
		parcelInfo.setCompId(compId);
		parcelInfo.setNetId(netId);
		parcelInfo.setChareWeightForsender(chareWeightForsender);
		parcelInfo.setInsureAmount(insureAmount);
		parcelInfo.setPricePremium(pricePremium);
		parcelInfo.setPackingCharges(packingCharges);
		parcelInfo.setFreightPaymentMethod(freightPaymentMethod);
		parcelInfo.setParcelRemark(parcelRemark);
		parcelInfo.setCreateUserId(createUserId);
		parcelInfo.setActualTakeMember(actualTakeMember);
		parcelInfo.setCodAmount(actualCodAmount);
		parcelInfo.setNoFly(noFly);
		parcelInfo.setFreight(signGoodsTotal);
		parceladdress.setSendCustomerId(sendCustomerId);
		parceladdress.setAddresseeName(addresseeName);
		parceladdress.setAddresseeMobile(addresseeMobile);
		parceladdress.setAddresseeAddressId(addresseeAddressId);
		parceladdress.setAddresseeAddress(addresseeAddress);
		parceladdress.setSendName(sendName);
		parceladdress.setSendMobile(sendMobile);
		parceladdress.setSendAddressId(sendAddressId);
		parceladdress.setSendAddress(sendAddress);
		
		//取派包裹记录
		ParParcelconnection connect = new ParParcelconnection();
		connect.setCompId(parcelInfo.getCompId());
		connect.setNetId(parcelInfo.getNetId());
		connect.setExpMemberId(parcelInfo.getActualTakeMember());
		connect.setTaskId(parcelInfo.getTakeTaskId());
		connect.setCreateTime(new Date());
		connect.setCosignFlag((short) 1);
		connect.setExpMemberSuccessFlag((short) 1);
		
		//查询包裹id是否存在
		Long tempParcelId = parcelInfoService.queryParcelInfoByExpWayBillNumAndNetId(netId, expWaybillNum);
		if(PubMethod.isEmpty(id)){
			//最后需返回
			idReturn = IdWorker.getIdWorker().nextId();
			parcelInfo.setId(idReturn);  //包裹id
			parceladdress.setId(idReturn);  //包裹地址id
			parcelInfo.setCreateTime(new Date());
			//parcelInfo.setSignResult((short) 0); //未签收状态
			parcelInfo.setParcelStatus((short) 1);
			if(!PubMethod.isEmpty(tempParcelId)) {
				throw new ServiceException("openapi.ParcelSupplementServiceImpl.addOrUpdateParcelInfo.001", "运单号已存在");
			}
			//进行包裹信息和包裹地址信息的插入操作
			parParcelinfoMapper.insertParcel(parcelInfo);
			parParceladdressMapper.insertParceladdress(parceladdress);
			//添加取派件包裹记录
			connect.setId(IdWorker.getIdWorker().nextId());
			connect.setParId(idReturn);
			parcelInfoService.addParcelConnection(connect);
		}else{
			idReturn = id;
			parcelInfo.setId(id);  //包裹id
			parceladdress.setId(id); //包裹地址id
			if(!PubMethod.isEmpty(tempParcelId) && !tempParcelId.equals(id)) {
				throw new ServiceException("openapi.ParcelSupplementServiceImpl.addOrUpdateParcelInfo.001", "运单号已存在");
			}
			//进行包裹信息和包裹地址信息的更新操作
			parParcelinfoMapper.updateParcelSelective(parcelInfo);
			parParceladdressMapper.updateParceladdressSelective(parceladdress);
			//包裹id查询结果集
			List<ParParcelconnection> parcelList = parcelInfoService.queryConnectionListByParId(id);
			if(!PubMethod.isEmpty(parcelList)) {
				for(ParParcelconnection parcel : parcelList) {
					if(parcel.getExpMemberId() != null && !parcel.getExpMemberId().equals(parcelInfo.getActualTakeMember()) && parcel.getCosignFlag() == 1l) {
						//删除原来的收派员记录
						parcelInfoService.removeParcelConnection(parcel.getId());
						//添加新的收派员记录
						connect.setId(IdWorker.getIdWorker().nextId());
						connect.setParId(id);
						parcelInfoService.addParcelConnection(connect);
					}
				}
			} else {
				//添加新的收派员记录
				connect.setId(IdWorker.getIdWorker().nextId());
				connect.setParId(id);
				parcelInfoService.addParcelConnection(connect);
			}
			//更新后查询对象
			parcelInfo = parParcelinfoMapper.findById(idReturn);
			parceladdress = parParceladdressMapper.findParceladdress(idReturn);
		}
		//放置包裹信息和包裹地址信息到缓存
		ehcacheService.put("parcelInfoCache", String.valueOf(parcelInfo.getId()), parcelInfo);
		ehcacheService.put("parcelAddressCache", String.valueOf(parcelInfo.getId()), parceladdress);
		this.ehcacheService.put("parcelIdCacheByExpWayBillNumAndNetId",String.valueOf(parcelInfo.getExpWaybillNum() + parcelInfo.getNetId()), parcelInfo.getId());
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(createUserId));
		this.ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(createUserId));
		Long receiptId =parParcelinfoMapper.queryTaskByReceipt(parcelInfo.getId());
 		if(!PubMethod.isEmpty(createUserId) && !PubMethod.isEmpty(receiptId)){
 			ehcacheService.remove("queryTakeByWaybillNum", String.valueOf(createUserId)+String.valueOf(receiptId));
 		}
		//清空查询列表缓存
		cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(compId));
		return map;
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>清除缓存结果集</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 上午10:10:41</dd>
	 * @param cacheName
	 * @param key
	 * @throws ServiceException
	 * @since v1.0
	 */
	private void cleanQueryInfo(String cacheName, String key) throws ServiceException {
		String queryresult = ehcacheService.get(cacheName, key, String.class);
		List<String> querylist = null;
		if(!PubMethod.isEmpty(queryresult)) {
			querylist = JSON.parseArray(queryresult, String.class);
		}
		if(!PubMethod.isEmpty(querylist)) {
			ehcacheService.remove(cacheName, querylist);
		}
	}
}