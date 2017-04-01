package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpCompAreaAddress;
import net.okdi.core.base.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public interface ExpCompAreaAddressMapper extends BaseDao {
	public List<ExpCompAreaAddress> queryCompAreaAddress(Long compId);

	public void deleteCompAreaAddress(Long compId);

	public void saveBatch(List<ExpCompAreaAddress> list);
	public List<Map> getCoverAddress(Map<String, Object> paras);

	/**
	 * 
	 * @Description: 公司信息获取公司派送范围
	 * @author feng.wang
	 * @date 2014-11-3下午13:23:17
	 * @param compId
	 *            公司ID
	 */
	public List getCompareaList(Long compId);
	
	/**
	 * @Method: getExceedareaList
	 * @Description: 根据经纬度和查询方圆5公里站点信息
	 * @param latitude
	 *            地址纬度
	 * @param longitude
	 *            地址经度
	 * @return
	 * @since jdk1.6
	 */
	List getNearSite(Map map);
}