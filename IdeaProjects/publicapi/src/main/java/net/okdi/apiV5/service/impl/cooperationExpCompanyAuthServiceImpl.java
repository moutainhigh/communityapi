package net.okdi.apiV5.service.impl;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV5.service.CooperationExpCompanyAuthService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class cooperationExpCompanyAuthServiceImpl implements CooperationExpCompanyAuthService {

    private @Autowired ConstPool constPool;

    private @Autowired OpenApiHttpClient openApiHttpClient;

    @Override
    public String getAuthStatusByMemberId(Long memberId, Long netId) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("netId", netId);
        String response = openApiHttpClient.doPassSendStrParcel("coopcomp/auth/status/", map);
        if(PubMethod.isEmpty(response)){
            throw new ServiceException("001","数据请求异常");
        }
        return response;
    }

    @Override
    public String addAuth(Long memberId, Long netId, String orgCode, String userCode, String telNum, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("netId", netId);
        map.put("orgCode", orgCode);
        map.put("userCode", userCode);
        map.put("telNum", telNum);
        map.put("password", password);
        String response = openApiHttpClient.doPassSendStrParcel("coopcomp/auth/gt/add/", map);
        if(PubMethod.isEmpty(response)){
            throw new ServiceException("001","数据请求异常");
        }
        return response;
    }
}
