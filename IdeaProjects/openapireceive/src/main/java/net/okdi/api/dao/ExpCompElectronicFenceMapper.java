package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.core.base.BaseDao;

import org.springframework.stereotype.Repository;

/**
 * @ClassName ExpCompElectronicFenceMapper
 * @Description 公司取派覆盖区域电子围栏信息
 * @author feng.wang
 * @date 2014-10-29
 * @since jdk1.6
 */
@Repository
public interface ExpCompElectronicFenceMapper extends BaseDao {
	/**
	 * 
	 * @Method: queryCompFence 
	 * @Description: 查询网点取派范围
	 * @param compId 网点ID
	 * @return List<ExpCompElectronicFence>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:09:45
	 * @since jdk1.6
	 */
	public List<ExpCompElectronicFence> queryCompFence(Long compId);
	public List<ExpCompElectronicFence> getListByLonLat(Map<String, Object> map);


	public void saveBatch(List<ExpCompElectronicFence> list);

	/**
	 * @Method: getExpCompElectronicLaction
	 * @Description: 根据公司ID获取电子围栏信息
	 * @param compId
	 *            公司ID
	 * @return
	 * @since jdk1.6
	 */
	public List<ExpCompElectronicFence> getExpCompElectronicLaction(Long compId);
}