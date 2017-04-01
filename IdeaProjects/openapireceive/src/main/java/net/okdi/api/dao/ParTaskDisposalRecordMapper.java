package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParTaskDisposalRecord;
import net.okdi.api.vo.TaskVo;
import net.okdi.core.base.BaseDao;


public interface ParTaskDisposalRecordMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(TaskVo record);

    int insertSelective(TaskVo record);

    ParTaskDisposalRecord selectByPrimaryKey(Long id);
    
    /**
     * 
     * @Method: queryByTaskIdAndDisposalObject 
     * @Description: 查询任务记录
     * @param param
     * @return
     * @author xpf
     * @date 2014-10-29 下午5:17:27
     * @since jdk1.6
     */
    ParTaskDisposalRecord queryByTaskIdAndDisposalObject(Map<String, Object> param);

    int updateByPrimaryKeySelective(ParTaskDisposalRecord record);

    int updateByPrimaryKey(ParTaskDisposalRecord record);
    
    /**
     * 
     * @Method: updateCancelTaskByTaskId 
     * @Description: 更新任务记录表，取消任务
     * @param record
     * @return
     * @author xpf
     * @date 2014-10-30 上午11:07:12
     * @since jdk1.6
     */
    int updateCancelTaskByTaskId(ParTaskDisposalRecord record);
    
    /**
     * 
     * @Method: updateByTaskIdSelective 
     * @Description: 通过任务id更新
     * @param record
     * @return
     * @author xpf
     * @date 2014-10-28 下午2:18:14
     * @since jdk1.6
     */
    int updateByTaskIdSelective(ParTaskDisposalRecord record);
    
    /**
     * 
     * @Method: queryTaskByExp 
     * @Description: 分页查询取件任务
     * @param map
     * @return
     * @author xpf
     * @date 2014-10-29 下午5:15:23
     * @since jdk1.6
     */
    List<Map<String, Object>> queryTaskByExp(Map<String,Object> map);
    
    /**
     * 
     * @Method: queryCancelTaskByExp 
     * @Description: 分页查询取消任务 需要关联log表
     * @param map
     * @return
     * @author xpf
     * @date 2014-10-30 上午10:27:40
     * @since jdk1.6
     */
    List<Map<String, Object>> queryCancelTaskByExp(Map<String,Object> map);
    
    /**
     * 
     * @Method: queryTaskByExpCount 
     * @Description: 统计分页查询任务数量
     * @param map
     * @return
     * @author xpf
     * @date 2014-10-29 下午5:16:08
     * @since jdk1.6
     */
    int queryTaskByExpCount(Map<String,Object> map);
    
    /**
     * 
     * @Method: queryTaskByMember 
     * @Description: 收派员查询取件任务 不分页
     * @param memberId
     * @return
     * @author xpf
     * @date 2014-10-29 下午5:15:46
     * @since jdk1.6
     */
    List<Long> queryTaskByMember(Map<String, Object> param);
    
    /**
     * 
     * @Method: queryTaskAvailableByTaskId 
     * @Description: 查询未处理和已分配的任务记录
     * @param taskId
     * @return
     * @author xpf
     * @date 2014-10-29 下午8:36:00
     * @since jdk1.6
     */
    List<ParTaskDisposalRecord> queryTaskAvailableByTaskId(Long taskId);
    
    
    /** 
     * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
     * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午05:42:55</dd>
     * @param taskId
     * @return
     * @since v1.0
    */
    List<ParTaskDisposalRecord> queryDistributeTaskByTaskId(Long taskId);
    
    /**
     * 
     * @Method: queryTaskByMemberIdOrCompId 
     * @Description: 查询其他营业分部或收派员任务信息 重新分配任务使用
     * @param map
     * @return
     * @author xpf
     * @date 2014-11-1 下午6:31:07
     * @since jdk1.6
     */
    List<ParTaskDisposalRecord> queryTaskByMemberIdOrCompId(Map<String,Object> map);
    
    /**
     * 
     * @Method: queryTaskUnFinished 
     * @Description: 查询营业分部或收派员是否有未完成任务
     * @param map
     * @return
     * @author xpf
     * @date 2014-11-6 上午10:11:26
     * @since jdk1.6
     */
    List<ParTaskDisposalRecord> queryTaskUnFinished(Map<String,Object> map);
    
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>查询营业分部上级是否有站点分配任务</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-25 下午1:54:27</dd>
     * @param taskId
     * @return
     * @since v1.0
     */
    List<ParTaskDisposalRecord> queryCompTaskById(Long taskId);
    
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>查询分配给收派员的任务记录</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午11:41:49</dd>
     * @param param
     * @return
     * @since v1.0
     */
    ParTaskDisposalRecord queryTaskToMember(Map<String, Object> param);

    public Map queryIdByTaskId(Long taskId);

	int queryTaskCount(Map<String, Object> map);

	List<Map<String, Object>> queryTaskByCriteria(Map<String, Object> map);
}