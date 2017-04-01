package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;

public interface BasNetInfoMapper extends BaseDao{
	/**
	 * 
	 * @Description: 查询网络信息(未删除 且 审核通过的)
	 * @author 翟士贺
	 * @date 2014-10-20上午9:11:24
	 * @return BasNetInfo
	 */
	public List<BasNetInfo> queryNetInfo();
	/**
	 * 
	 * @Description: 查询网络信息(所有)
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public List<BasNetInfo> queryNetInfoAll();
	/**
	 * 
	 * @Description: 修改网络信息
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public int updateNetInfo(Map<String,Object> map);
	/**
	 * 
	 * @Method 添加 bas_netinfo表
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-13 下午3:20:24
	 * @param map
	 */
	public void insertBasNetInfo(BasNetInfo basNetInfo);
	/**
	 * 
	 * @Method 修改网络信息的启用/停用 状态
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-13 下午5:13:53
	 * @param netId
	 * @param status
	 * @return
	 */
	public int makeBaseNetInfo(@Param("netId")long netId,@Param("deleteMark")Short deleteMark);
	/**
	 * 
	 * @Method 判断网络名称是否存在
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-14 上午9:48:44
	 * @param netName
	 * @return
	 */
	public Long ifHasNetInfo(@Param("netName")String netName);
	/**
	 * 
	 * @Description: 查询网络信息详情
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public List<BasNetInfo> queryNetInfoById(@Param("netId")Long netId);
	/**
	 * 
	 * @Description: 查询网络名称是否存在
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public Integer queryNetName(@Param("netName")String netName);

	/**
	 * 
	 * @Description: 更新快递网络的合作伙伴
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public Integer updatePartners(@Param("netId")Long netId,@Param("isPartners")Short isPartners);
	/**
	 * 
	 * @Description: 查询快递公司是否授权
	 * @param netId
	 * @returnBasNetInfo
	 */
	BasNetInfo queryNetAuthByNetId(Long netId);

}