package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpAreaElectronicFence;
import net.okdi.core.base.BaseService;
/**
 * 
 * @Description: 片区
 * @author 翟士贺
 * @date 2014-10-18下午3:11:38
 */
public interface AreaElectronicFenceService extends BaseService<ExpAreaElectronicFence> {
	/**
	 * 
	 * @Method: queryAreaFence 
	 * @Description: 查询网点片区围栏
	 * @param compId 网点ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @return List<Map<String,Object>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:16:08
	 * @since jdk1.6
	 */
	public List queryAreaFence(Long compId,String compTypeNum);
	/**
	 * 
	 * @Method: addAreaFence 
	 * @Description: 添加/更新片区围栏
	 * @param areaFenceId 片区围栏ID
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitudeStr 经度串
	 * @param latitudeStr 纬度串
	 * @return ExpAreaElectronicFence
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:17:00
	 * @since jdk1.6
	 */
	public ExpAreaElectronicFence addAreaFence(Long areaFenceId,Long netId,Long compId,String longitudeStr,String latitudeStr);
	/**
	 * 
	 * @Method: updateAreaMember 
	 * @Description: 更新片区收派员/营业分部（分配片区）
	 * @param areaFenceId 片区围栏ID
	 * @param memberId 收派员/营业分部ID
	 * @param compTypeNum 类型 1050营业分部 0收派员
	 * @param labelLongitude 标注经度
	 * @param labelLatitude 标注纬度
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:17:53
	 * @since jdk1.6
	 */
	public void updateAreaMember(Long areaFenceId,Long compId,Long memberId,String compTypeNum,BigDecimal labelLongitude,
			BigDecimal labelLatitude);
	/**
	 * 
	 * @Method: deleteAreaFence 
	 * @Description: 删除片区围栏
	 * @param areaFenceId 片区围栏ID
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:19:56
	 * @since jdk1.6
	 */
	public void deleteAreaFence(Long areaFenceId,Long compId);
	/**
	 * 
	 * @Method: updateAreaBranch 
	 * @Description: 更新片区营业分部（解除关系）
	 * @param compId 站点ID
	 * @param branchCompId 营业分部ID
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:20:17
	 * @since jdk1.6
	 */
	public void updateAreaBranch(Long compId,Long branchCompId);
	public void removeAreaMember(Long compId,Long MemberId);
}
