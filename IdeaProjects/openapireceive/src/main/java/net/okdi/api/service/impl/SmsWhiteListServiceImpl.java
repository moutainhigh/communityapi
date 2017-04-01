package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.dao.SmsWhiteListMapper;
import net.okdi.api.entity.SmsWhiteList;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.SmsWhiteListService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

@Service("smsWhiteListService")
public class SmsWhiteListServiceImpl implements SmsWhiteListService{

	@Autowired
	SmsWhiteListMapper smsWhiteListMapper;
	@Autowired
	MemberInfoService memberInfoService;
	@Autowired
	EhcacheService ehcacheService;
	
	@Override
	public Boolean isInWhiteList(Long memberId) {
		SmsWhiteList smsWhite = smsWhiteListMapper.selectByPrimaryKey(memberId);
		if(!PubMethod.isEmpty(smsWhite)&&Short.valueOf("1").equals(smsWhite.getStatus())){
			return true;
		}
		return false;
	}

	@Override
	public String addInWhiteList(String memberPhone) {
		Long memberId = memberInfoService.getMemberId(memberPhone);
		SmsWhiteList smsWhite = smsWhiteListMapper.selectByPrimaryKey(memberId);
		if(!PubMethod.isEmpty(smsWhite)){
			return "已经加入白名单";
		}
		
		SmsWhiteList whiteList = new SmsWhiteList();
		whiteList.setMemberId(memberId);
		whiteList.setMemberPhone(memberPhone);
		whiteList.setCreateTime(new Date());
		whiteList.setStatus(Short.valueOf("1"));
		smsWhiteListMapper.insert(whiteList);
		
		smsWhite = ehcacheService.get("SmsWhiteListCache", memberId.toString(), SmsWhiteList.class);
		if(!PubMethod.isEmpty(smsWhite)){
			ehcacheService.put("SmsWhiteListCache", String.valueOf(memberId), whiteList);
		}
		return "true";
	}

	@Override
	public String deleteFromWhiteList(Long memberId) {
		smsWhiteListMapper.deleteByPrimaryKey(memberId);
		ehcacheService.remove("SmsWhiteListCache", String.valueOf(memberId));
		return "true";
	}

}
