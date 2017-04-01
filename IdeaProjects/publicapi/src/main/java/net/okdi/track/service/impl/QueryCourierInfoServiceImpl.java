package net.okdi.track.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV1.service.impl.ExpressUserServiceImpl;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.track.httpclient.CommExpressHttpClient;
import net.okdi.track.service.QueryCourierInfoService;

@Service
public class QueryCourierInfoServiceImpl implements QueryCourierInfoService {
	
	@Autowired
	private CommExpressHttpClient commExpressHttpClient;
	
	Logger log = Logger.getLogger(ExpressUserServiceImpl.class);
	
	/**
	 * 根据快递网络代码和快递单号获取物流信息
	 */
	@Override
	public String queryCourierInformation(String billCode, String code) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billCode",billCode);
		map.put("code",code);
		String response = commExpressHttpClient.doPassSendStr("queryCourie/queryCourierInformation/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.track.service.impl.QueryCourierInfoServiceImpl.queryCourierInformation.001","数据请求异常");
		}
		return response;
	}

}
