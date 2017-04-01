package net.okdi.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.dao.ShopMemberPushSetMapper;
import net.okdi.api.entity.ShopMemberPushSet;
import net.okdi.api.service.ShopMemberPushSetService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

@Service
public class ShopMemberPushSetServiceImpl implements ShopMemberPushSetService {

	@Autowired
	ShopMemberPushSetMapper shopMemberPushSetMapper;
	
	@Autowired
	EhcacheService ehcacheService;
	
	/**
	 * 设置(修改抢单模式)
	 */
	@Override
	public void pushStatus(Long memberId, Short pushStatus) {
		ShopMemberPushSet shopMemberPushSet =  shopMemberPushSetMapper.selectByPrimaryKey(memberId);
		ehcacheService.remove("shopMemberPushSetCache", memberId.toString());
		if(PubMethod.isEmpty(shopMemberPushSet)){
			shopMemberPushSet = new ShopMemberPushSet();
			shopMemberPushSet.setMemberId(memberId);
			shopMemberPushSet.setPushStatus(pushStatus);
			shopMemberPushSetMapper.insert(shopMemberPushSet);
		}else{
			shopMemberPushSet.setPushStatus(pushStatus);
			shopMemberPushSetMapper.updateByPrimaryKey(shopMemberPushSet);
		}
	}

	/**
	 * 查询
	 */
	@Override
	public short queryShopMemberPushSetById(Long memberId) {
		ShopMemberPushSet shopMemberPushSet = this.ehcacheService.get("shopMemberPushSetCache", String.valueOf(memberId), ShopMemberPushSet.class);
		if(PubMethod.isEmpty(shopMemberPushSet)){
			shopMemberPushSet =  shopMemberPushSetMapper.selectByPrimaryKey(memberId);
			ehcacheService.put("shopMemberPushSetCache", String.valueOf(memberId), shopMemberPushSet);
		}
		if(PubMethod.isEmpty(shopMemberPushSet)){
			return (short)0;
		}else{
			return shopMemberPushSet.getPushStatus();
		}
	}
}
