package net.okdi.api.dao;

import java.util.List;
import net.okdi.api.entity.NoticeCenter;
import net.okdi.api.entity.NoticeCenterExample;
import org.apache.ibatis.annotations.Param;

public interface NoticeCenterMapper {
    int countByExample(NoticeCenterExample example);

    int deleteByExample(NoticeCenterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NoticeCenter record);

    int insertSelective(NoticeCenter record);

    List<NoticeCenter> selectByExample(NoticeCenterExample example);

    NoticeCenter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NoticeCenter record, @Param("example") NoticeCenterExample example);

    int updateByExample(@Param("record") NoticeCenter record, @Param("example") NoticeCenterExample example);

    int updateByPrimaryKeySelective(NoticeCenter record);

    int updateByPrimaryKey(NoticeCenter record);
}