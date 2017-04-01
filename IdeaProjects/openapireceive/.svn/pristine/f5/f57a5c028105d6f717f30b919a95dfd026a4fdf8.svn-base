package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.RobQuotationInfo;
import net.okdi.api.entity.RobQuotationInfoExample;

import org.apache.ibatis.annotations.Param;

public interface RobQuotationInfoMapper {
    int countByExample(RobQuotationInfoExample example);

    int deleteByExample(RobQuotationInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobQuotationInfo record);

    int insertSelective(RobQuotationInfo record);

    List<RobQuotationInfo> selectByExample(RobQuotationInfoExample example);

    RobQuotationInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobQuotationInfo record, @Param("example") RobQuotationInfoExample example);

    int updateByExample(@Param("record") RobQuotationInfo record, @Param("example") RobQuotationInfoExample example);

    int updateByPrimaryKeySelective(RobQuotationInfo record);

    int updateByPrimaryKey(RobQuotationInfo record);
    
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>广播id查询抢单列表</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午2:21:01</dd>
     * @param robId
     * @return
     * @since v1.0
     */
    List<Long> selectByRobId(Long robId);
    
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>抢单列表更新已读状态</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午3:25:52</dd>
     * @param map
     * @return
     * @since v1.0
     */
    int updateReadStatusByPrimaryKey(Map<String,Object> map);

	public List<RobQuotationInfo> queryRobQuotationInfoByCompId(Long compId);

	public List<RobQuotationInfo> queryRobQuotationInfoByMemberId(Long memberId);
	
	public void addbatchRobQuotationInfo(List<RobQuotationInfo> list);

	void robExpress(RobQuotationInfo robQuotationInfo);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询是否有新报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-3 下午5:11:52</dd>
	 * @param robId
	 * @return
	 * @since v1.0
	 */
	List<Long> selectByNewQuotate(Long robId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据广播ID查询所有对该广播报价的报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 下午5:37:33</dd>
	 * @param broadcastId
	 * @return
	 * @since v1.0
	 */
	List<Map<String,Object>> getRobQuotationInfoByBroadcastId(@Param("broadcastId")Long broadcastId);
}