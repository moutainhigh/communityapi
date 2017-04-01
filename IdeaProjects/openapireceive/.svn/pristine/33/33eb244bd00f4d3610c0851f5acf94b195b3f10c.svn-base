package net.okdi.apiV4.service;

import java.util.Date;

import net.okdi.core.common.page.Page;

public interface QueryTaskServiceV4 {

	public Page queryTask(Byte taskSource, String netName,Byte taskStatus, String contactAddress, String contactMobile,
			String actorPhone,Date startTime, Date endTime, Integer currentPage, Integer pageSize);

	public void deleteWechatSendRecords(String taskIds);
}
