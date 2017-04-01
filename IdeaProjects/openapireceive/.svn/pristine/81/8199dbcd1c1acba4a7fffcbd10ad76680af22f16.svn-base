package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpExceedareaAddress;
import net.okdi.core.base.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public interface ExpExceedareaAddressMapper extends BaseDao {
	public List<ExpExceedareaAddress> queryExceedareaAddress(Long compId);

	public void deleteExceedareaAddress(Long compId);

	public void saveBatch(List<ExpExceedareaAddress> list);
	public List<Map> getExceedAddress(Map<String, Object> paras);
	/**
	 * 
	 * @Description: 根据公司ID获取公司不派送范围
	 * @author feng.wang
	 * @date 2014-11-3下午13:40:17
	 * @param compId
	 *            公司ID
	 */
	public List getExceedareaList(Long compId);
}