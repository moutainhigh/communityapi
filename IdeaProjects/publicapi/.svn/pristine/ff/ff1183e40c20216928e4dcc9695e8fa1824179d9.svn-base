package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.SubmittedSignService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

@Service
public class SubmittedSignServiceImpl implements SubmittedSignService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Override
	public String queryAuthStatus(Long memberId, Long netId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("netId",netId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/authStatus", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.queryAuthStatus.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String quOrUpParcelToControlMemberId(Long memberId, String billJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("billJson",billJson);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/quOrUpParcelToControlMemberId", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.quOrUpParcelToControlMemberId.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String subSignBatch(Long memberId, Long compId, String billJson,String flag, Long netId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("compId",compId);
		map.put("billJson",billJson);
		map.put("flag",flag);
		map.put("netId",netId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/subSignBatch", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.subSignBatch.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String querySignInfoCount(Long memberId, String dateYM, Long compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("dateYM",dateYM);
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/querySignInfoCount", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.querySignInfoCount.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String queryNoticeInfoCount(Long memberId, String dateYM, Long compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("dateYM",dateYM);
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/queryNoticeInfoCount", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.queryNoticeInfoCount.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String querySignInfoRecord(Long memberId, String dateYMD, Long compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("dateYMD",dateYMD);
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/querySignInfoRecord", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.querySignInfoRecord.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String queryUploadFailParcelInfo(Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/queryUploadFailParcelInfo", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.queryUploadFailParcelInfo.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String querySignRecordDetail(Long uid, Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid",uid);
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/querySignRecordDetail", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.queryUploadFailParcelInfo.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String updateSignBill(String newBill , Long memberId, Long parId, String newPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newPhone",newPhone);
		map.put("newBill",newBill);
		map.put("memberId",memberId);
		map.put("parId",parId);
		String response = openApiHttpClient.doPassSendStrParcel("submittedSign/auth/updateSignBill", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SubmittedSignServiceImpl.queryUploadFailParcelInfo.001","数据请求异常");
		}
		return response;
	}

}
