package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.Map;

import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.api.entity.ExpCompFenceCenter;
import net.okdi.core.base.BaseService;
/**
 * 
 * @ClassName CompElectronicFenceService
 * @Description 取派范围
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
public interface CompElectronicFenceService extends BaseService<ExpCompElectronicFence> {
	/**
	 * 
	 * @Method: queryCompFence 
	 * @Description: 查询网点取派范围
	 * @param compId 网点ID
	 * @return Map<String,Object>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午8:59:16
	 * @since jdk1.6
	 */
	public Map<String,Object> queryCompFence(Long compId);
	/**
	 * 
	 * @Method: addCompFence 
	 * @Description: 添加/更新网点取派范围
	 * @param compFenceId 取派范围ID
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitudeStr 经度串
	 * @param latitudeStr 纬度串
	 * @return ExpCompElectronicFence
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午8:59:55
	 * @since jdk1.6
	 */
	public ExpCompElectronicFence addCompFence(Long compFenceId,Long netId,Long compId,String longitudeStr,String latitudeStr);
	/**
	 * 
	 * @Method: addCompFenceCenter 
	 * @Description: 添加/更新围栏初始化数据
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitude 中心点经度
	 * @param latitude 中心点纬度
	 * @param mapLevel 地图放大级别
	 * @return ExpCompFenceCenter
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:01:47
	 * @since jdk1.6
	 */
	public ExpCompFenceCenter addCompFenceCenter(Long netId,Long compId,BigDecimal longitude,BigDecimal latitude,Byte mapLevel);
	/**
	 * 
	 * @Method: deleteCompFence 
	 * @Description: 删除取派范围
	 * @param compFenceId 取派范围ID
	 * @param compId 网点ID
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:03:01
	 * @since jdk1.6
	 */
	public void deleteCompFence(Long compFenceId,Long compId);
}
