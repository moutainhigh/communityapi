package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.core.base.BaseDao;

/**
 * 
 * @Description: 网点信息
 * @author 翟士贺
 * @date 2014-10-18下午3:15:58
 */
public interface BasCompInfoMapper extends BaseDao {
	/**
	 * 
	 * @Description: 获取提示网点信息
	 * @author 翟士贺
	 * @date 2014-10-18下午3:16:12
	 * @param paras
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getPromptComp(Map<String, Object> paras);
	public List<Map<String, Object>> getPromptCompForMobile(Map<String, Object> paras);
	/**
	 * 
	 * @Description: 获取同网络下重名网点信息
	 * @author 翟士贺
	 * @date 2014-10-18下午3:16:12
	 * @param paras
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getSameCompName(Map<String, Object> paras);
	public List<Map<String, Object>> getSameCompNameForMobile(Map<String, Object> paras);

	/**
	 * @Description: 获取可用网点信息
	 * @author 翟士贺
	 * @date 2014-10-20上午10:36:43
	 * @param paras
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getUseCompInfo(Map<String, Object> paras);

	/**
	 * 
	 * @Description: 更新领用关系
	 * @author 翟士贺
	 * @date 2014-10-20下午2:16:41
	 * @param paras
	 */
	public void updateRelationCompId(Map<String, Object> paras);

	/**
	 * 
	 * @Description: 更新网点审核状态
	 * @author 翟士贺
	 * @date 2014-10-20下午7:34:42
	 * @param paras
	 */
	public void updateCompStatus(Map<String, Object> paras);

	/**
	 * 
	 * @Description: 更新网点审核状态
	 * @author 翟士贺
	 * @date 2014-10-20下午7:34:42
	 * @param compId
	 *            网点Id
	 * @return String
	 */
	public String queryCompAuditDesc(Long compId);

	/**
	 * 
	 * @Description: 查询所有公司信息
	 * @author feng.wang
	 * @date 2014-10-30下午17:34:42
	 * @return
	 */
	public List<BasCompInfo> getCompList();
	
	public int getTotalNum();
	public int getTotalNum2(@Param("netId") String netId,
			@Param("compType") Short compType, @Param("compName") String compName,
			@Param("memberPhone") String memberPhone,@Param("createType") Short createType, 
			 @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime,@Param("status") Short status,@Param("provinceId")String provinceId);
	
	public int getCurrentMonthNum();
	
	public List<Map<String, Object>> getMonthOfDaysNum();
	

	public Integer countCompNum(Map<String, Object> paras);
	
	public List<Map<String, Object>> countCompNumList(Map<String, Object> paras);
	
	public List<Map<String, Object>> queryAllCompInfo(@Param("pageNum") Integer pageNum,
			@Param("pageSize") Integer pageSize,@Param("netId") String netId,
			@Param("compType") Short compType, @Param("compName") String compName,
			@Param("memberPhone") String memberPhone,@Param("createType") Short createType, 
			 @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime,@Param("status") Short status,@Param("provinceId")String provinceId);
	
	public List<Map<String, Object>> expBasCompInfoList(@Param("netId") String netId,
			@Param("compType") Short compType, @Param("compName") String compName,
			@Param("memberPhone") String memberPhone,@Param("createType") Short createType, 
			 @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime,@Param("status") Short status,@Param("provinceId")String provinceId);
	
	public Map<String, Object> getNum();
	
	public int getTotalNum(@Param("netId") String netId,
			@Param("compType") Short compType, @Param("compName") String compName,
			@Param("memberPhone") String memberPhone,@Param("createType") Short createType, 
			 @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime,@Param("status") Short status);
	public Map<String, Object> queryMemberByCompId(Long compId);
	public BasCompInfo isVirtualuser(Long memberId);
}