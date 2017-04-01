package net.okdi.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParParcelconnection;

import org.apache.ibatis.annotations.Param;

public interface ParParcelconnectionMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(ParParcelconnection record);

    
    int insertSelective(ParParcelconnection record);

    
    ParParcelconnection selectByPrimaryKey(Long id);

    
    int updateByPrimaryKeySelective(ParParcelconnection record);

   
    int updateByPrimaryKey(ParParcelconnection record);
    
    public List<Map<String,Object>> queryTask(@Param("netId")Long netId,@Param("compId")Long compId,@Param("memberId")Long memberId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("page")Integer page,@Param("pageSize")Integer pageSize);
    
    public List<Map<String,Object>>queryTaskDetail(@Param("memberId")Long memberId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("page")Integer page,@Param("pageSize")Integer pageSize);

    public Long queryTaskCount(@Param("netId")Long netId,@Param("compId")Long compId,@Param("memberId")Long memberId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("page")Integer page);
    
    public Long queryTaskDetailCount(@Param("memberId")Long memberId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("page")Integer page);
    
    public List<ParParcelconnection> queryParcelConnectionByParId(Long parId);
    
    public int deleteByExpMemberId(Long memberId);
}