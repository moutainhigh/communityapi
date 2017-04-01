package net.okdi.taobaoApi.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.taobaoApi.service.TaobaoApiService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaobaoApiServiceImpl extends AbstractHttpClient implements TaobaoApiService {

	@Autowired
	private ConstPool constPool;
	public static final Log logger = LogFactory.getLog(TaobaoApiServiceImpl.class);
	
	@Override
	public String queryPhoneStatus(Long companyId, String mailNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId",companyId);
		map.put("mailNo",mailNo);
		String response = this.Post(constPool.getSmsHttpUrl()+"taobaoApi/queryPhoneStatus", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("public--net.okdi.taobaoApi.service.impl.TaobaoApiServiceImpl.queryPhoneStatus.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String queryCompanyList() {
		Map<String, Object> map = new HashMap<String, Object>();
		String response = this.Post(constPool.getSmsHttpUrl()+"taobaoApi/queryCompanyList", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("public--net.okdi.taobaoApi.service.impl.TaobaoApiServiceImpl.queryCompanyList.001","数据请求异常");
		}
		return response;
	}
	
	
	

}
