package net.okdi.api.service.impl;


import net.okdi.api.dao.BasClientPushJurisdictionMapper;
import net.okdi.api.entity.BasClientPushJurisdiction;
import net.okdi.api.service.BasClientPushJurisdictionService;
import net.okdi.core.common.ehcache.EhcacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("basClientPushJurisdictionService")
public class BasClientPushJurisdictionServiceImpl implements BasClientPushJurisdictionService{

	@Autowired
	BasClientPushJurisdictionMapper basClientPushJurisdictionMapper;

	@Autowired
	private EhcacheService ehcacheService;
	
	/**
	 * @Method: findByBasClientPushJurisdiction 
	 * @Description: TODO
	 * @param memberId
	 * @return 
	 * @see net.okdi.push.service.BasClientPushJurisdictionService#findByBasClientPushJurisdiction(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public BasClientPushJurisdiction findByBasClientPushJurisdiction(Long memberId) {
		return basClientPushJurisdictionMapper.query(memberId);
	}

	/**
	 * @Method: updateBasClientPushJurisdiction 
	 * @Description: 数据存在则更新，否则则插入
	 * @param basClientPushJurisdiction 
	 * @see net.okdi.push.service.BasClientPushJurisdictionService#updateBasClientPushJurisdiction(net.okdi.push.entity.BasClientPushJurisdiction) 
	 * @since jdk1.6
	*/
	@Override
	public void saveOrupdateBasClientPushJurisdiction(BasClientPushJurisdiction basClientPushJurisdiction) {
		ehcacheService.remove("mobPhoneCache","login_ext_"+"01"+'_'+basClientPushJurisdiction.getMemberId());
		ehcacheService.remove("mobPhoneCache","login_ext_"+"02"+'_'+basClientPushJurisdiction.getMemberId());
		BasClientPushJurisdiction bas = basClientPushJurisdictionMapper.query(basClientPushJurisdiction.getMemberId());
		if(bas==null){
			basClientPushJurisdictionMapper.insert(basClientPushJurisdiction);
		}else{
			basClientPushJurisdictionMapper.update(basClientPushJurisdiction);
		}
	}
	
	
}
