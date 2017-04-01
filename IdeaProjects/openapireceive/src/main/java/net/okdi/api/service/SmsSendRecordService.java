package net.okdi.api.service;

import java.util.Date;

import net.okdi.api.entity.SmsSendRecord;
import net.okdi.core.base.BaseService;
import net.okdi.core.common.page.Page;


public interface SmsSendRecordService extends BaseService<SmsSendRecord> {
	 public Page querySmsSendRecord(Long memberId,int pageNo,int pageSize,Date date);

	public void saveSmsSendRecordBatch(String content,Long memberId,String memberMobile,Long compId);
}
