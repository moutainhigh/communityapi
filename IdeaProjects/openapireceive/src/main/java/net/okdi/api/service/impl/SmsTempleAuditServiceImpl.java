package net.okdi.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.api.dao.SmsTempleAuditMapper;
import net.okdi.api.entity.ExpPriceGroup;
import net.okdi.api.entity.SmsTempleAudit;
import net.okdi.api.service.SmsTempleAuditService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;

@Service("smsTempleAuditService")
public class SmsTempleAuditServiceImpl implements SmsTempleAuditService{

	@Autowired
	SmsTempleAuditMapper smsTempleAuditMapper;
	
	@Autowired
	EhcacheService ehcacheService;
	
	SensitivewordFilter sf = new SensitivewordFilter();

	@Override
	public String upLoadSmsTemple(Long memberId, Long compId, String compName, String memberPhone, String realName,String templeContent) {
		
		//包含审核通过的关键词
		boolean containKeyWord = sf.isContaintSensitiveWord(templeContent, 1);
		
		SmsTempleAudit sta = new SmsTempleAudit();
		sta.setId(IdWorker.getIdWorker().nextId());
		
		sta.setMemberId(memberId);
		sta.setCompId(compId);
		sta.setCompName(compName);
		sta.setMemberPhone(memberPhone);
		sta.setRealName(realName);
		sta.setTempleContent(templeContent);
		
		sta.setAuditCount(Short.valueOf("0"));//审核次数默认为0
		if(containKeyWord){//包含审核通过的关键词
			sta.setAuditStatus(Short.valueOf("1"));//包含审核通过的关键字 1:审核通过
		}else{
			sta.setAuditStatus(Short.valueOf("0"));//默认审核状态 0:待审核
		}
		sta.setCreateTime(new Date());
		sta.setDeleteFlag(Short.valueOf("0"));//默认不删除
		smsTempleAuditMapper.insert(sta);
		ehcacheService.remove("smsTempleAuditCache", memberId.toString());
		SmsTempleAudit smsTempleAudit = this.findSmsTempleById(memberId);
		ehcacheService.put("smsTempleAuditCache", memberId.toString(), smsTempleAudit);
		return "true";
	}

	@Override
	public List<SmsTempleAudit> findSmsTemple(Long memberId) {
		String str = this.ehcacheService.get("smsTempleAuditCache", String.valueOf(memberId), String.class);
		if(!PubMethod.isEmpty(str)){
			return JSON.parseArray(str, SmsTempleAudit.class);
		}
		List list= smsTempleAuditMapper.findSmsTemple(memberId);
		ehcacheService.put("smsTempleAuditCache", String.valueOf(memberId), list);
		return list;
	}

	@Override
	public String editSmsTemple(Long id, String smsContent) {
		
		boolean containKeyWord = sf.isContaintSensitiveWord(smsContent, 1);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		map.put("templeContent",smsContent);
		if(containKeyWord){
			map.put("auditStatus","1");
		}else{
			map.put("auditStatus","0");
		}
		smsTempleAuditMapper.editSmsTemple(map);
		return "true";
	}

	
	@Override
	public String deleteSmsTemple(Long id) {
		smsTempleAuditMapper.deleteByPrimaryKey(id);//逻辑删除
		ehcacheService.remove("smsTempleAuditCache", id.toString());
		return "true";
	}

	@Override
	public SmsTempleAudit findSmsTempleById(Long id) {
		SmsTempleAudit smsTempleAudit = ehcacheService.get("smsTempleAuditCache", id.toString(), SmsTempleAudit.class);
		if(PubMethod.isEmpty(smsTempleAudit)){
			smsTempleAudit=this.smsTempleAuditMapper.selectByPrimaryKey(id);
			this.ehcacheService.put("smsTempleAuditCache", String.valueOf(id), smsTempleAudit);
		}
		return smsTempleAudit;
	}

	@Override
	public String auditSmsTemple(Long id, Short status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("auditStatus", status);
		smsTempleAuditMapper.auditSmsTemple(map);
		return "true";
	}

	@Override
	public Page findSmsTemplePT(String phone, String realName, String compName, String auditCount, Short auditStatus, String startTime,String endTime,Integer currentPage, Integer pageSize) {
		Page p = new Page();
		if(PubMethod.isEmpty(currentPage)){
			currentPage = 1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize = 10;
		}
		p.setCurrentPage(currentPage);
		p.setPageSize(pageSize);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", phone);
		map.put("realName", realName);
		map.put("compName", compName);
		map.put("auditCount", auditCount);
		map.put("auditStatus", auditStatus);
		map.put("startTime", getDate(startTime));
		map.put("endTime", getDate(endTime));
		map.put("page",p);
		List<SmsTempleAudit> lsPt = smsTempleAuditMapper.findSmsTemplePT(map);
		p.setItems(lsPt);
		return p;
	}

	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date getDate(String time){
		try{
			Date d = sdf.parse(time);
			return d;
		}catch (Exception e) {
			return null;
		}
		
	}
	
}
