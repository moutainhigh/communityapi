package net.okdi.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.api.dao.VisitDsAppDownLogMapper;
import net.okdi.api.dao.VisitShortUrlDsLogMapper;
import net.okdi.api.entity.VisitDsAppDownLog;
import net.okdi.api.entity.VisitShortUrlDsLog;
import net.okdi.api.service.StatisDSService;

@Service("statisDSService")
public class StatisDSServiceImpl implements StatisDSService{

	@Autowired
	VisitShortUrlDsLogMapper visitShortUrlDsLogMapper;
	@Autowired
	VisitDsAppDownLogMapper visitDsAppDownLogMapper;
	
	@Override
	public void addDsVisitCount(String shortkey, String time, String shopId) {
		List<VisitShortUrlDsLog>  ls = visitShortUrlDsLogMapper.selectByShortKey(shortkey);
		if(ls.size()>0){
			VisitShortUrlDsLog record = ls.get(0);
			record.setVisitCount(record.getVisitCount()+1);
			record.setUpdateTime(new Date());
			visitShortUrlDsLogMapper.updateByPrimaryKey(record);
		}else{
			VisitShortUrlDsLog record = new VisitShortUrlDsLog();
			record.setCreateTime(new Date());
			try{
				Date d = new Date(Long.valueOf(time));
				record.setTime(d);
			}catch (Exception e) {
			}
			record.setShortUrl(shortkey);
			record.setId(IdWorker.getIdWorker().nextId());
			record.setShopId(shopId);
			record.setVisitCount(1);
			visitShortUrlDsLogMapper.insert(record );
		}
	}

	@Override
	public void addDsDownCount(String shortkey, String productType, String userAgent, String appType) {
		VisitDsAppDownLog vl = new VisitDsAppDownLog();
		vl.setId(IdWorker.getIdWorker().nextId());
		vl.setShortUrl(shortkey);
		//1.接单王 2.个人端 3.网站 4.站点 5其它',
		vl.setProductType(Short.valueOf(productType));
		vl.setCreateTime(new Date());
		vl.setAppType(Short.valueOf(appType));
		vl.setUserAgent(userAgent);
		visitDsAppDownLogMapper.insert(vl);
	}
}
