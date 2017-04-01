package net.okdi.api.service;

import java.util.List;

import net.okdi.api.entity.ParLogisticTrace;
import net.okdi.core.base.BaseService;
import net.okdi.core.common.page.Page;
/**
 * 
 * @ClassName ParLogisticTraceService
 * @Description 查件记录API业务逻辑实现
 * @author dong.zhang
 * @date 2014-10-27
 * @since jdk1.6
 */
public interface ParLogisticTraceService extends BaseService<ParLogisticTrace>{

	public int deleteByIds(String ids);

	public List<ParLogisticTrace> list(Long casMemberId, String traceStatus);

	public List<ParLogisticTrace> listByPage(Page page, Long casMemberId, String traceStatus);

	public void saveOrUpdate(String param, Long casMemberId, String sendNoticeFlag);
	
}
