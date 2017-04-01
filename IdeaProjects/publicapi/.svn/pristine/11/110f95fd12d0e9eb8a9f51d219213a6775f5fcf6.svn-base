package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.ProblemPackageService;
import net.okdi.core.passport.OpenApiHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProblemPackageServiceImpl implements ProblemPackageService {
	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	public static final Logger logger=LoggerFactory.getLogger(ProblemPackageServiceImpl.class);
	@Override
	public String queryProblemPackageList(Long actualSendMember) {
		Map<String,Object> parms = new HashMap<>();
		parms.put("actualSendMember", actualSendMember);
		String methodName="problemPackage/queryProblemPackageList";
		return openApiHttpClient.doPassSendStrParcel(methodName, parms);
 }


	/**
	 * 问题件重派
	 */
	@Override
	public String probPackAssignAgain(String parIds,Long memberId,String memberPhone) {
		Map<String,Object> map = new HashMap<>();
		map.put("parIds", parIds);
		map.put("memberId", memberId);
		map.put("memberPhone", memberPhone);
		String methodName="problemPackage/probPackAssignAgain";
		return openApiHttpClient.doPassSendStrParcel(methodName, map);
	}


	@Override
	public String probPackBackComp(String parIds) {
		Map<String,Object> map = new HashMap<>();
		map.put("parIds", parIds);
		String methodName="problemPackage/probPackBackComp";
		return openApiHttpClient.doPassSendStrParcel(methodName, map);
	}
	
}
