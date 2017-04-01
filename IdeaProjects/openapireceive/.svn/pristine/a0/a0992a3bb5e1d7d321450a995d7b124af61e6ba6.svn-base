package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.ParLogisticTraceMapper;
import net.okdi.api.entity.ParLogisticTrace;
import net.okdi.api.service.ParLogisticTraceService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.base.QueryService;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.ClientStatuUtil;
import net.okdi.core.util.DateUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;
@Service
public class ParLogisticTraceServiceImpl extends BaseServiceImpl<ParLogisticTrace> implements ParLogisticTraceService{

	@Autowired
	private ParLogisticTraceMapper parLogisticTraceMapper;
	@Autowired
	private QueryService queryService;
	@Override
	public BaseDao getBaseDao() {
		return parLogisticTraceMapper;
	}

	@Override
	public String parseResult(String info) {
		return null;
	}

	@Override
	public int deleteByIds(String ids) {
		String[] arr=ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		List list=new ArrayList();	
		for(int i=0;i<arr.length;i++){
			list.add(arr[i]);
		}
		map.put("ids", list);
		return parLogisticTraceMapper.deleteByIds(map);
	}

	@Override
	public List<ParLogisticTrace> list(Long casMemberId, String traceStatus) {
		Map<String, Object> paras=new HashMap<String, Object>();
		paras.put("casMemberId", casMemberId);
		paras.put("traceStatus", traceStatus);
		List<ParLogisticTrace> list=new ArrayList<ParLogisticTrace>();
		list=parLogisticTraceMapper.findByMap1(paras);
		return list;
	}

	@Override
	public List listByPage(Page page, Long casMemberId, String traceStatus) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("casMemberId", casMemberId);
		paras.put("traceStatus", traceStatus);
		List list = queryService.findPage("net.okdi.api.dao.ParLogisticTraceMapper.getLogisticTracesByChannelId", page, paras);
		return list;
	}
	/*params : 5717095188,yuantong,2014-10-31 11:48:19,北京市海淀区花园桥公司 已签收 */
	/*插入或更新前的拼接*/
	@Override
	public void saveOrUpdate(String param, Long casMemberId, String sendNoticeFlag) {
		String [] parLogisticTraces = param.split("\\@");
		List<ParLogisticTrace>traceList = new ArrayList<ParLogisticTrace>();
	for(String parLogisticTrace :parLogisticTraces ){
		String [] params = parLogisticTrace.split(",");
		ParLogisticTrace plt = new ParLogisticTrace();
		plt.setCasMemberId(casMemberId);
		plt.setExpWaybillNum(params[0]);
		plt.setClientNetNum(params[1]);
		plt.setModifyTime(DateUtil.stringToDate(params[2]));
		if(params.length>4){
			plt.setClientTraceStatu(ClientStatuUtil.getStatu(params[3]+params[4]));
			plt.setTraceStatus(ClientStatuUtil.getStatu(params[3]+params[4]));
		plt.setTraceDetail(params[3]+params[4]);
		}else{
			plt.setClientTraceStatu(ClientStatuUtil.getStatu(params[3]));
			plt.setTraceStatus(ClientStatuUtil.getStatu(params[3]));
			plt.setTraceDetail(params[3]);
		}
		plt.setNetId(01l);
		plt.setSendNoticeFlag(sendNoticeFlag);
		traceList.add(plt);
	}
	saveOrUpdateLogistTraces(traceList);
	}
	/*真正的插入或更新*/
	public void saveOrUpdateLogistTraces(List<ParLogisticTrace> traceList) {
		List <ParLogisticTrace> list=null;
		for(ParLogisticTrace plt : traceList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("casMemberId", plt.getCasMemberId().toString());
			map.put("expWayBillNum",plt.getExpWaybillNum());
			list=this.parLogisticTraceMapper.getLogisticTrace(map);
			if(list!=null&&list.size()>0){
				ParLogisticTrace parLogisticTrace =  list.get(0);
			    plt.setId(parLogisticTrace.getId());
				this.parLogisticTraceMapper.updateByPrimaryKeySelective(plt);
			}else{
				plt.setId(IdWorker.getIdWorker().nextId());
				this.parLogisticTraceMapper.insert(plt);
			}
		}
	}
}
