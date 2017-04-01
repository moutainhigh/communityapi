package net.okdi.api.dao;

import java.util.List;

import net.okdi.api.entity.BasCompimage;
import net.okdi.core.base.BaseDao;
/**
 * 
 * @ClassName BasCompimageMapper
 * @Description 网点图片信息
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
public interface BasCompimageMapper extends BaseDao{
	/**
	 * 
	 * @Method: deleteByCompId 
	 * @Description: 根据网点ID删除图片信息
	 * @param compId 网点ID
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:44:59
	 * @since jdk1.6
	 */
   public void deleteByCompId(Long compId);
   /**
    * 
    * @Method: saveCompImageBatch 
    * @Description: 批量保存图片信息
    * @param compimageList 图片信息
    * @author ShiHe.Zhai
    * @date 2014-11-11 上午10:45:37
    * @since jdk1.6
    */
   public void saveCompImageBatch(List<BasCompimage> compimageList);
   /**
    * 
    * @Method: queryCompimage 
    * @Description: 查询网点图片信息
    * @param compId 网点ID
    * @return List<BasCompimage>
    * @author ShiHe.Zhai
    * @date 2014-11-11 上午10:48:19
    * @since jdk1.6
    */
   public List<BasCompimage> queryCompimage(Long compId);
}