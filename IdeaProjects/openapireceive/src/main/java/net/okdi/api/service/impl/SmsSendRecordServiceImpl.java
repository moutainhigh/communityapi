package net.okdi.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.SmsSendRecordMapper;
import net.okdi.api.entity.SmsSendRecord;
import net.okdi.api.service.SmsSendRecordService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.page.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

/**
 * 
 * @Description: 网络信息
 * @author 翟士贺
 * @date 2014-10-18下午3:13:31
 */
@Service
public class SmsSendRecordServiceImpl extends BaseServiceImpl<SmsSendRecord> implements SmsSendRecordService {
	@Autowired
	private SmsSendRecordMapper smsSendRecordMapper;
	@Autowired
	private RedisService redisService;
	
	@Override
	public BaseDao getBaseDao() {
		return smsSendRecordMapper;
	}
	@Override
	public Page querySmsSendRecord(Long memberId,int pageNo,int pageSize,Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(date);
		//Page page = PageUtil.getPageData(pageNo, pageSize, this.smsSendRecordMapper.querySmsSendRecord(memberId,time));
		Page page = PageUtil.getPageData(pageNo, pageSize, redisService.lpop("smsRecordCache_"+memberId+"_"+time,SmsSendRecord.class));
//		 if(pageNo>page.getPageCount()){
//			 page.setCurrentPage(pageNo);
//			 page.setPageSize(pageSize);
//			 page.setItems(null);
//		 }
		return page;
	}
	@Override
	public void saveSmsSendRecordBatch(String content,Long memberId,String memberMobile,Long compId){
		this.redisService.remove("addWrongNumber",memberId.toString());
		List<SmsSendRecord> smsSendRecordList = new ArrayList<SmsSendRecord>();
		for(String temp:content.split("\\^")){
			smsSendRecordList.add(this.setSmsSendRecord(temp.split("[|]")[1], temp.split("[|]")[0], memberId, memberMobile, compId));
		}
		this.smsSendRecordMapper.saveSmsSendRecordBatch(smsSendRecordList);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(date);
		for(SmsSendRecord smsSendRecord : smsSendRecordList){
			smsSendRecord.setCreatedTime(date);
			redisService.lpush("smsRecordCache_"+memberId+"_"+time, smsSendRecord);
		}
	}
	private SmsSendRecord setSmsSendRecord(String smsContent,String receiverMobile,Long memberId,String memberMobile,Long compId){
		SmsSendRecord smsSendRecord = new SmsSendRecord();
		smsSendRecord.setSmsId(IdWorker.getIdWorker().nextId());
		smsSendRecord.setSmsContent(smsContent);
		smsSendRecord.setReceiverMobile(receiverMobile);
		smsSendRecord.setMemberId(memberId);
		smsSendRecord.setMemberMobile(memberMobile);
		smsSendRecord.setCompId(compId);
		return smsSendRecord;
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
}
