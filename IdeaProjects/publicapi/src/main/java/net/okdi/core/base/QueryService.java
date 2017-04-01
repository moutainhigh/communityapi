package net.okdi.core.base;

import java.util.List;
import java.util.Map;

import net.okdi.core.common.page.Page;

import org.springframework.dao.DataAccessException;

/**
 * 所有查询为主的Service接口的基础接口
 * 
 * @ClassName: BaseQueryService
 * @description:
 * @author: tiger
 * @date: 2014-9-19 下午10:06:14
 * @version: 1.0.0
 */
public interface QueryService {

	/**
	 * 根据查询条件，查询数据总数
	 * 
	 * @title: findCount
	 * @description:
	 * @author: tiger
	 * @date: 2014-9-19
	 * @param sqlId
	 * @param paras
	 * @return
	 * @throws DataAccessException
	 * 
	 */
	public int findCount(String sqlId, Map<String, Object> paras) throws DataAccessException;

	/**
	 * 根据条件，返回查询实体
	 * 
	 * @title: findOne
	 * @description:
	 * @author: tiger
	 * @date: 2014-9-19
	 * @param sqlId
	 * @param paras
	 * @return
	 * @throws DataAccessException
	 * @throws NullPointerException
	 * 
	 */
	public <T> T findOne(String sqlId, Map<String, Object> paras) throws DataAccessException, NullPointerException;
	
	/**
	 * 根据主键查询实体
	 * @param sqlId
	 * @param pk
	 * @return
	 * @throws DataAccessException
	 * @throws NullPointerException
	 */
	public <T> T findOneByPk(String sqlId, Object pk) throws DataAccessException, NullPointerException;

	/**
	 * 根据查询条件返回List<Entity>或List<Map<String,Object>>。根据SQL语句，将查询结果存入Map<column,
	 * value>。
	 * 
	 * @param sqlId
	 *            mybatis配置文件中select元素的ID
	 * @param paras
	 *            HashMap格式的参数
	 * @return List<Object>
	 * @throws DataAccessException
	 * @throws NullPointerException
	 */
	public <T> List<T> findList(String sqlId, Map<String, Object> paras) throws DataAccessException, NullPointerException;

	/**
	 * 分页查询。默认读取数据总数的sqlId为:sqlId_Count(sqlId加下划线"_"加"Count"组成)
	 * 
	 * @title: page
	 * @description:
	 * @author: tiger
	 * @date: 2014-9-19
	 * @param sqlId
	 * @param paras
	 * @return
	 * @throws DataAccessException
	 * @throws NullPointerException
	 * 
	 */
	public <T> List<T> findPage(String sqlId, Page page, Map<String, Object> paras) throws DataAccessException, NullPointerException;

}
