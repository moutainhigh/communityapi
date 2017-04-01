package net.okdi.apiV1.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasCompimage;
import net.okdi.core.base.BaseDao;

public interface BasCompimageMapperV1 extends BaseDao{
	
	public List<BasCompimage> queryCompimage(Long compId);
	/**jingguoqiang
	 * */
	  public void saveCompImageBatch(List<BasCompimage> compimageList);
	  
	  public Map<String,Object> queryCompPic(@Param("compId")Long compId);
	
	  public List<Map<String, Object>> queryImageByMemberId(@Param("memberId")String memberId);
	  
	  public void deleteImageByCompId(@Param("compId")Long compId);

}