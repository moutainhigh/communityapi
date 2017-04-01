package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.dao.BasEmployeeAuditMapper;
import net.okdi.apiV4.service.CompIdAndNetIdService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

@Service
public class CompIdAndNetIdServiceImpl implements CompIdAndNetIdService{

	@Autowired
	BasEmployeeAuditMapper basEmployeeAuditMapper;
	@Autowired
	RedisService redisService;
	@Override
	public Map queryComIdAndNetIdByMemberId(Long memberId) {
		Map map=redisService.get("ComIdAndNetId", memberId+"", Map.class);
		if(PubMethod.isEmpty(map)){
			map=new HashMap();
			map=basEmployeeAuditMapper.queryComIdAndNetIdByMemberId(memberId);
			if(PubMethod.isEmpty(map)){
				throw new ServiceException("net.okdi.apiV4.service.impl.CompIdAndNetIdServiceImpl.queryComIdAndNetIdByMemberId.001", "快递员没有compId和netId"); 
			}else{
				if(PubMethod.isEmpty(map.get("comId"))){
					throw new ServiceException("net.okdi.apiV4.service.impl.CompIdAndNetIdServiceImpl.queryComIdAndNetIdByMemberId.002", "快递员没有compId"); 					
				}
				if(PubMethod.isEmpty(map.get("netId"))){
					throw new ServiceException("net.okdi.apiV4.service.impl.CompIdAndNetIdServiceImpl.queryComIdAndNetIdByMemberId.003", "快递员没有netId"); 					
				}
				redisService.put("ComIdAndNetId", memberId+"", map);
			}
		}
		return map;
	}

}
