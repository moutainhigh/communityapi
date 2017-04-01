/**  
 * @Project: openapi
 * @Title: SendParcelServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-7 下午02:37:05
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import com.alibaba.fastjson.JSON;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.service.SendParcelService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SendParcelServiceImpl
 * @Description TODO
 * @author mengnan.zhang
 * @date 2014-11-7
 * @since jdk1.6
 */
@Service
public class SendParcelServiceImpl implements SendParcelService{
	@Autowired
    private MemberInfoMapper memberInfoMapper;
	@Autowired
	private EhcacheService ehcacheService;
	/**
	 * @Method: quertDefulatAddress 
	 * @Description: TODO
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.SendParcelService#quertDefulatAddress(java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public String quertDefulatAddress(String memberId) {
		String result = ehcacheService.get("memberDefulatAddress", ""+memberId, String.class);
		if(!PubMethod.isEmpty(result)){return result;}
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map = memberInfoMapper.quertDefulatAddress(map);
		Map <String,Object>data = new HashMap<String,Object>();
		data.put("success", true);
		data.put("data",map);
		ehcacheService.put("memberDefulatAddress", ""+memberId, JSON.toJSONString(data));
//		ehcacheService.remove("memberDefulatAddress", memberId);
		return JSON.toJSONString(data);
	}

}
