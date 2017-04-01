package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.ParLogisticSearchMapper;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.ParLogisticSearch;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParLogisticSearchService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.base.QueryService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ParLogisticSearchServiceImpl extends BaseServiceImpl<ParLogisticSearch> implements
		ParLogisticSearchService {
	@Autowired
	private ParLogisticSearchMapper parLogisticSearchMapper;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private QueryService queryService;
	@Autowired
	private BasNetInfoMapper netInfoMapper;
	@Autowired
	private EhcacheService ehcacheService;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public BaseDao getBaseDao() {
		return parLogisticSearchMapper;
	}

	@Override
	public int saveSend(Long channelId, Long netId, String expWaybillNum, String traceStatus, String traceDetail,
			Long appointId, String recMobile, String systemMark, String channelNo, String expType) {
		
		removeCompIdCache(channelId);
		
		ParLogisticSearch pl = new ParLogisticSearch();
		pl.setId(IdWorker.getIdWorker().nextId());
		pl.setChannelId(channelId);
		pl.setNetId(netId);
		pl.setNetCode(this.findCode(netId));
		pl.setExpWaybillNum(expWaybillNum);
		pl.setTraceStatus(traceStatus);
		pl.setTraceDetail(traceDetail);
		pl.setAppointId(appointId);
		pl.setRecMobile(recMobile);
		//如果不是指定电商管家1，则指定为个人端
		if(!"1".equals(systemMark))
			systemMark = "0";
		pl.setSystemMark(systemMark);
		pl.setChannelNo(channelNo);
		pl.setExpType(expType);
		pl.setModifiedTime(new Date());
		pl.setCreatedTime(new Date());
		return parLogisticSearchMapper.insert(pl);
	}

	@Override
	public int decideGoods(Long channelId, String expWaybillNum, Long netId) {
		String cacheKey = "channelId"+channelId+"expWaybillNum"+expWaybillNum+"netId"+netId;
		String str = getCache(cacheKey);
		int i = 0;
		if(str != null){
			i = ehcacheService.get("parlogisticSearchCache", cacheKey, Integer.class);
			return i;
		}
		if (channelId == null || expWaybillNum == null) {
			i = 0;
		} else {
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("netId", netId);
			paras.put("channelId", channelId);
			paras.put("expWaybillNum", expWaybillNum);
			i = parLogisticSearchMapper.decideGoods(paras);
			addCache(channelId, cacheKey, i);
		}
		return i;
	}

	@Override
	public String findCode(Long netId) {
		BasNetInfo basNetInfo = this.ehcacheService.get("netCache", String.valueOf(netId), BasNetInfo.class);
		//return parLogisticSearchMapper.findCodeByNetId(netId);
		if(basNetInfo==null)
			return "EMS";
		return basNetInfo.getCode();
	}

	@Override
	public String parseResult(String info) {
		return null;
	}

	@Override
	public List<ParLogisticSearch> list(Long channelId, String expType) {
		
		String str = getCache(channelId+expType);
		List<ParLogisticSearch> alist = null;
		
		if(str == null){
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("channelId", channelId);
			paras.put("expType", expType);
			alist = parLogisticSearchMapper.findList(paras);
			//根据netId获取netName
			for (ParLogisticSearch parLogisticSearch : alist) {
				BasNetInfo netInfo = this.ehcacheService.get("netCache", parLogisticSearch.getNetId().toString(),
						BasNetInfo.class);
				if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
					netInfo = this.netInfoMapper.findById(parLogisticSearch.getNetId());
				}
				String netName = netInfo.getNetName();
				parLogisticSearch.setNetName(netName);
			}
			addCache(channelId,expType,alist);
		}else{
			alist = jsonArrayToBean(str,ParLogisticSearch.class);
		}
		return alist;
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
	
	private void removeCompIdCache(Long channelId) {
		if (ehcacheService.getByKey("parlogisticSearchCache", String.valueOf(channelId))) {
			String str = ehcacheService.get("parlogisticSearchCache", String.valueOf(channelId), String.class);
			List ls = ehcacheService.get("parlogisticSearchCache", String.valueOf(channelId), List.class);
			for (int i = 0; i < ls.size(); i++) {
				ehcacheService.remove("parlogisticSearchCache", ls.get(i).toString());
			}
			ehcacheService.remove("parlogisticSearchCache", String.valueOf(channelId));
		}
	}

	private void addCache(Object channelId, String cachekey, Object cacheContent) {
		List<String> lsCacheList = ehcacheService.get("parlogisticSearchCache", channelId.toString(), List.class);
		if (lsCacheList == null)
			lsCacheList = new ArrayList<String>();
		lsCacheList.add(cachekey);

		ehcacheService.put("parlogisticSearchCache", cachekey, cacheContent);
		ehcacheService.put("parlogisticSearchCache", channelId.toString(), lsCacheList);

	}
	
	private void removeAllCache(){
		ehcacheService.removeAll("parlogisticSearchCache");
	}

	private String getCache(String cachekey) {
		return ehcacheService.get("parlogisticSearchCache", cachekey, String.class);
	}

	@Override
	public void deleteById(Long id, Long channelId, Long netId, String expType) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("id", id);
		paras.put("channelId", channelId);
		paras.put("netId", netId);
		paras.put("expType", expType);
		parLogisticSearchMapper.deleteById(paras);
	}

	@Override
	public List<ParLogisticSearch> listByPage(Page page, Long channelId, String expType) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("channelId", channelId);
		paras.put("expType", expType);
		List list = queryService.findPage("net.okdi.api.dao.ParLogisticSearchMapper.getLogisticSearchByChannelId",
				page, paras);
		return list;
	}

	@Override
	public void update(Long id, String expWaybillNum, String traceStatus, String traceDetail) {
		removeAllCache();
		ParLogisticSearch pl = new ParLogisticSearch();
		pl.setId(id);
		pl.setExpWaybillNum(expWaybillNum);
		pl.setTraceStatus(traceStatus);
		pl.setTraceDetail(traceDetail);
		pl.setModifiedTime(new Date());
		this.parLogisticSearchMapper.updateByPrimaryKeySelective(pl);
	}

	@Override
	public List<Long> findIdListPerFourHour() {
		return parLogisticSearchMapper.findIdListPerFourHour();
	}

	@Override
	public List<ParLogisticSearch> findParLogistic(String ids) {
		String[] arr = ids.split(",");
		List idsList = Arrays.asList(arr);
		return parLogisticSearchMapper.findParLogistic(idsList);
	}

	@Override
	public void batchUpdate(String json) {
		removeAllCache();
		//"traceStatus";
		//"traceDetail";
		List<ParLogisticSearch> listPar = JSONArray.parseArray(json, ParLogisticSearch.class);
		if (listPar == null || listPar.size() == 0)
			return;
		//List<ParLogisticSearch> listPar = null;
		parLogisticSearchMapper.batchUpdate(listPar);
	}

	@Override
	public void insertOrUpdateByMemberIdNetBill(String memberId, String netId, String expWaybillNum, String recMobile,
			String traceStatus, String traceDetail) {
		removeCompIdCache(Long.valueOf(memberId));
		ParLogisticSearch par = new ParLogisticSearch();
		par.setChannelId(Long.valueOf(memberId));
		par.setNetId(Long.valueOf(netId));
		par.setExpWaybillNum(expWaybillNum);
		//查询使用list的原因是防止抛出下面异常：如果不小心数据库中有多条相似的数据，那么查询会报异常
		List<ParLogisticSearch> parSearchResult = parLogisticSearchMapper.findByMemberIdNetBill(par);
		
		//不论查询结果如何，都要设置推送标记，和电商标记
		par.setPushMark(Short.valueOf("1"));
		//通过该渠道的系统标记都为电商管家
		par.setSystemMark("1");
		Date d = new Date();
		if(parSearchResult.size()>0){//存在则更新
			parSearchResult.get(0).setTraceDetail(traceDetail);
			parSearchResult.get(0).setTraceStatus(traceStatus);
			//parResult.get(0).setRecMobile(recMobile);
			parSearchResult.get(0).setModifiedTime(d);
			parLogisticSearchMapper.updateByMemberNetBill(par);
		}else{//不存在则新插入记录
			par.setId(IdWorker.getIdWorker().nextId());
			par.setNetCode(findCode(Long.valueOf(netId)));
			par.setChannelNo("02");
			par.setTraceDetail(traceDetail);
			par.setTraceStatus(traceStatus);
			par.setRecMobile(recMobile);
			par.setExpType("1");
			par.setCreatedTime(d);
			par.setModifiedTime(d);
			parLogisticSearchMapper.insert(par);
		}
		
	}

	@Override
	public List<ParLogisticSearch> findUnPushed() {
		List<ParLogisticSearch> lsPar = parLogisticSearchMapper.findUnPushed();
		return lsPar;
	}

	@Override
	public void updatePushData(String ids) {
		if(ids==null){
			return;
		}
		removeAllCache();
		List<Map> lsm = new ArrayList<Map>();
		String[] idArray = ids.split(",");
		Date d = new Date();
		for(int i = 0 ;i<idArray.length;i++){
			if(!PubMethod.isEmpty(idArray[i])){
				Map m = new HashMap();
				m.put("id",idArray[i]);
				m.put("now", d);
				lsm.add(m);
			}
		}
		parLogisticSearchMapper.updatePushData(lsm);
	}
	
}
