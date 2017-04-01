package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.ParSmsAuditMapper;
import net.okdi.api.dao.VisitShortUrlLogMapper;
import net.okdi.api.entity.VisitShortUrlLog;
import net.okdi.api.service.VisitShortUrlLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;


@Service
public class VisitShortUrlLogServiceImpl implements VisitShortUrlLogService {

	@Autowired
	VisitShortUrlLogMapper visitShortUrlLogMapper;

	@Autowired
	ParSmsAuditMapper parSmsAuditMapper;
	
	@Override
	public void addVisitCount(String shortUrl, String sendLon, String sendLat, String sendMob) {
		List<VisitShortUrlLog> lsShortUrlKey = visitShortUrlLogMapper.queryByShortUrlKey(shortUrl);
		if (lsShortUrlKey == null || lsShortUrlKey.size() < 1) {
			VisitShortUrlLog record = new VisitShortUrlLog();
			record.setId(IdWorker.getIdWorker().nextId());
			record.setShortUrl(shortUrl);
			record.setDownCount(0);
			record.setVisitCount(1);
			try{
				record.setSendLon(BigDecimal.valueOf(Double.valueOf(sendLon)));
				record.setSendLat(BigDecimal.valueOf(Double.valueOf(sendLat)));
			}catch(Exception e){
			}
			record.setSendMob(sendMob);
			record.setCreateTime(new Date());
			visitShortUrlLogMapper.insert(record);
		}else{
			VisitShortUrlLog record = new VisitShortUrlLog();
			record.setId(lsShortUrlKey.get(0).getId());
			record.setVisitCount(lsShortUrlKey.get(0).getVisitCount()+1);
			record.setUpdateTime(new Date());
			visitShortUrlLogMapper.updateVisitDownCount(record);
		}
	}

	@Override
	public void addDownCount(String shortUrl) {
		List<VisitShortUrlLog> lsShortUrlKey = visitShortUrlLogMapper.queryByShortUrlKey(shortUrl);
		if (lsShortUrlKey == null || lsShortUrlKey.size() < 1) {
			return;
		}else{
			VisitShortUrlLog record = new VisitShortUrlLog();
			record.setId(lsShortUrlKey.get(0).getId());
			record.setDownCount(lsShortUrlKey.get(0).getDownCount()+1);
			record.setUpdateTime(new Date());
			visitShortUrlLogMapper.updateVisitDownCount(record);
		}
	}

	
	@Override
	public Long findExpCount() {
		return parSmsAuditMapper.findExpCount();
	}

	@Override
	public Long findSmsCount() {
		return parSmsAuditMapper.findSmsCount();
	}
	
	/**
	 * 查找今天使用微信发送的短信的数量
	 * @Method: findTodayWxSmsCount 
	 */
	@Override
	public Long findTodayWxSmsCount(Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Date now = new Date();
		map.put("now", now);
		map.put("memberId", memberId);
		return parSmsAuditMapper.findTodayWxSmsCount(map);
	}

	
}
