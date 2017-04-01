package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.ParTaskInfo;
import net.okdi.api.vo.TaskVo;
import net.okdi.core.base.BaseDao;


public interface ParTaskInfoMapper extends BaseDao {
    int deleteByPrimaryKey(Long taskId);

    Long insert(TaskVo record);

    int insertSelective(TaskVo record);

    ParTaskInfo selectByPrimaryKey(Long taskId);

    int updateByPrimaryKeySelective(ParTaskInfo record);

    int updateByPrimaryKey(ParTaskInfo record);
    
    List<Map<String,Object>> queryTaskByCreateUserId(Map<String,Object> params);
    
    List<Map<String,Object>> queryWechatTaskByMemberId(Map<String,Object> params);
    
    int queryTaskCountByCreateUserId(Map<String, Object> param);
    
    int queryWeTaskCountByMemberId(Map<String, Object> param);
    
    int updateParTaskInfoBySelective(ParTaskInfo record);
    int updateTakeTask(ParTaskInfo record);
    
    void insertExpressTask(Map<String,Object> param);
    
    String querySystemExistMember(Long memberPhone);
    
    /**
     * 查询取件记录列表
     * @param memberId 收派员ID
     * @param date 查询取件记录日期
     * @return
     */
    List<Map<String,Object>> takeTaskRecordList(@Param("memberId")Long memberId,@Param("date")String date);
    /**
     * 根据任务ID查询任务详情
     * @param taskId
     * @return
     */
    Map<String,Object> queryTakeTaskById(@Param("taskId")Long taskId);

    /**
     * 根据任务ID查询任务详情
     * @param taskId
     * @return
     */
    Map<String,Object> queryTakeById(@Param("taskId")Long taskId);
    /**
     * 根据任务ID查询任务下挂载包裹简单信息
     * @param taskId
     * @return
     */
    List<Map<String,Object>> queryParcelsByTaskId(@Param("taskId")Long taskId);
    
    int updateStatusByTaskId(@Param("taskId")Long taskId,@Param("taskStatus")Byte taskStatus);
    
	String getMemberName(Long actorMemberId);

	String getNetName(Long coopNetId);
    
}