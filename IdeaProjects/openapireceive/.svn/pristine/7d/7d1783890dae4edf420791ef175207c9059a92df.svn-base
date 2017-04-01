package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParTaskProcess;
import net.okdi.api.vo.TaskVo;
import net.okdi.core.base.BaseDao;


public interface ParTaskProcessMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(TaskVo record);

    int insertSelective(TaskVo record);

    ParTaskProcess selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ParTaskProcess record);

    int updateByPrimaryKey(ParTaskProcess record);
    
    /**
     * 
     * @Method: selectByTaskId 
     * @Description: 查询任务流转信息
     * @param taskId
     * @return
     * @author xpf
     * @date 2014-10-29 下午5:43:18
     * @since jdk1.6
     */
    List<Map<String, Object>> selectByTaskId(Long taskId);
    
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>查询任务取消原因</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-24 下午5:16:44</dd>
     * @param taskId
     * @return
     * @since v1.0
     */
    List<ParTaskProcess> selectMaxCancelTaskByTaskId(Long taskId);
}