package net.okdi.api.dao;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpAreaElectronicFence;
import net.okdi.core.base.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public interface ExpAreaElectronicFenceMapper  extends BaseDao {
	/**
	 * 
	 * @Method: queryMemberAreaFence 
	 * @Description: 查询网点下未指派或指派给收派员的片区
	 * @param paras
	 * @return List<Map<String,Object>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:13:36
	 * @since jdk1.6
	 */
	public List<Map<String,Object>> queryMemberAreaFence(Map<String, Object> paras);
	/**
	 * 
	 * @Method: queryBranchAreaFence 
	 * @Description: 查询站点下指派给营业分部的片区
	 * @param paras
	 * @return List<Map<String,Object>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:14:07
	 * @since jdk1.6
	 */
	public List<Map<String,Object>> queryBranchAreaFence(Map<String, Object> paras);
	/**
	 * 
	 * @Method: updateAreaBranch 
	 * @Description: 更新片区营业分部（解除关系）
	 * @param paras
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:15:01
	 * @since jdk1.6
	 */
	public void updateAreaBranch(Map<String, Object> paras);
	public void updateAreaMember(Map<String, Object> paras);
	public void removeAreaMember(Map<String, Object> paras);
	
	/**
	 * 
	 * @Method: queryMemberFence 
	 * @Description: 经纬度查询收派员
	 * @param paras
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午3:49:56
	 * @since jdk1.6
	 */
	List<ExpAreaElectronicFence> queryMemberFence(Map<String, Object> paras);
	
	/**
	 * 
	 * @Method: queryCompFence 
	 * @Description: 经纬度查询营业分部
	 * @param paras
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午3:49:41
	 * @since jdk1.6
	 */
	List<ExpAreaElectronicFence> queryCompFence(Map<String, Object> paras);
}