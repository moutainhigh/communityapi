package net.okdi.api.dao;

import java.util.List;
import net.okdi.api.entity.RobParcelRelation;
import net.okdi.api.entity.RobParcelRelationExample;
import org.apache.ibatis.annotations.Param;

public interface RobParcelRelationMapper {
    int countByExample(RobParcelRelationExample example);

    int deleteByExample(RobParcelRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobParcelRelation record);

    int insertSelective(RobParcelRelation record);

    List<RobParcelRelation> selectByExample(RobParcelRelationExample example);

    RobParcelRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobParcelRelation record, @Param("example") RobParcelRelationExample example);

    int updateByExample(@Param("record") RobParcelRelation record, @Param("example") RobParcelRelationExample example);

    int updateByPrimaryKeySelective(RobParcelRelation record);

    int updateByPrimaryKey(RobParcelRelation record);

    List<RobParcelRelation> queryRobParcelRelationByRobId(Long robId);

 public void addBroadcastParcelRelation(List<RobParcelRelation>list);
 
 	/**
 	 * 
 	 * <dt><span class="strong">方法描述:</span></dt><dd>广播id删除广播包裹关系数据</dd>
 	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
 	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午11:48:09</dd>
 	 * @param parcelId
 	 * @return
 	 * @since v1.0
 	 */
 	int deleteByRobId(Long robId);
 	
 	/**
 	 * 
 	 * <dt><span class="strong">方法描述:</span></dt><dd>包裹id删除广播包裹关系数据</dd>
 	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
 	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-31 上午11:20:24</dd>
 	 * @param parcelId
 	 * @return
 	 * @since v1.0
 	 */
 	int deleteByParcelId(Long parcelId);
}