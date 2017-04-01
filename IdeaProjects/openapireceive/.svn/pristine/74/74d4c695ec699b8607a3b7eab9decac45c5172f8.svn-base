package net.okdi.api.dao;

import java.util.List;
import net.okdi.api.entity.RobBroadcastInfo;
import net.okdi.api.entity.RobBroadcastInfoExample;
import org.apache.ibatis.annotations.Param;

public interface RobBroadcastInfoMapper {
    int countByExample(RobBroadcastInfoExample example);

    int deleteByExample(RobBroadcastInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobBroadcastInfo record);

    int insertSelective(RobBroadcastInfo record);

    List<RobBroadcastInfo> selectByExample(RobBroadcastInfoExample example);

    RobBroadcastInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobBroadcastInfo record, @Param("example") RobBroadcastInfoExample example);

    int updateByExample(@Param("record") RobBroadcastInfo record, @Param("example") RobBroadcastInfoExample example);

    int updateByPrimaryKeySelective(RobBroadcastInfo record);

    int updateByPrimaryKey(RobBroadcastInfo record);

	void updateRobStatus(String broadcastStatusYcs);

	List<RobBroadcastInfo> queryAllRobBroadcastInfo();

    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>登录人id查询广播列表id</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:13:01</dd>
     * @param memberId
     * @return
     * @since v1.0
     */
    List<Long> selectByLoginMemberId(Long memberId);

	public void updateStatus(Long id);
	
	public void updateStatusCancelBroadcast(List list);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商运营数据直接完成广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 下午4:17:51</dd>
	 * @param id
	 * @since v1.0
	 */
	public void updateStatus2(Long id);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商运营数据完成报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-23 下午4:42:43</dd>
	 * @param qId
	 * @since v1.0
	 */
	public void finishQuotation(@Param("qId")Long qId);
	
	public Long getBroadcastIdByTaskId(Long taskId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据广播Id和选中人员查找选中收派员报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-6-1 下午6:47:31</dd>
	 * @param broadcastId
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public Long getQuotationIdByBroadcastIdAndMemberId(@Param("broadcastId")Long broadcastId,@Param("memberId")Long memberId);
	
}