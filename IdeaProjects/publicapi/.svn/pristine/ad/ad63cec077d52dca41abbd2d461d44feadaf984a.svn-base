package net.okdi.api.service.impl;


import net.okdi.api.exception.ServiceException;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.ParcelInfoHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParcelInfoServiceImpl implements ParcelInfoService{
	
	@Autowired
	private ParcelInfoHttpClient parcelInfoHttpClient;
	/**
	 * 扫描单号后判断是否为系统中已存在的包裹，如果已存在则返回包裹ID
	 * @Method: queryParcelInfoByExpWayBillNumAndNetId 
	 * @param netId 网络id
	 * @param expWayBillNum  运单号
	 * @return {"parcelId":118025415647232,"success":true} 存在包裹    {"success":false}  不存在包裹 
	 * @see net.okdi.api.service.ParcelInfoService#queryParcelInfoByExpWayBillNumAndNetId(java.lang.Long, java.lang.String)
	 */
	@Override
	public String queryParcelInfoByExpWayBillNumAndNetId(Long netId, String expWayBillNum) {
		if(PubMethod.isEmpty(netId)){
			throw new ServiceException("publicapi.ParcelInfoServiceImpl.queryParcelInfoByExpWayBillNumAndNetId.001", "网络netId不能为空 ");
		}
		if(PubMethod.isEmpty(expWayBillNum)){
			throw new ServiceException("publicapi.ParcelInfoServiceImpl.queryParcelInfoByExpWayBillNumAndNetId.002", "运单号expWayBillNum不能为空 ");
		}
		return parcelInfoHttpClient.queryParcelInfoByExpWayBillNumAndNetId(netId, expWayBillNum);
	}

}
