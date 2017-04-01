package net.okdi.core.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @ClassName: BaseDao.java
 * @description: 
 * @author wxd
 * @date 2013-9-3
 * @version: 1.0.0
 */
public interface BaseDao {
	/**
	 * @title: 插入
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @param entity
	 * @return 0 or 1
	 * @throws DataAccessException
	 */
	public <T> int insert(T entity) throws DataAccessException;

	/**
	 * @title: 删除
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @param id
	 * @return 0 or 1
	 * @throws DataAccessException
	 */
	public <T> int delete(Long id) throws DataAccessException;

	/**
	 * @title:delete batch
	 * @author: wxd
	 * @date:2013-9-6
	 * @param <T>
	 * @param id
	 * @return batch size
	 * @throws DataAccessException
	 */
	public <T> int deleteBatch(Long[] id) throws DataAccessException;

	/**
	 * @title: 修改
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @param entity
	 * @return 0 or 1
	 * @throws DataAccessException
	 */
	public <T> int update(T entity) throws DataAccessException;

	/**
	 * @title: 根据主键查询实体
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @param id
	 * @return entity
	 * @throws DataAccessException
	 */
	public <T> T findById(Long id) throws DataAccessException;
	
	/**
	 * @title: 根据MAP查询实体
	 * @author: feng.wang
	 * @date:2013-9-10
	 * @param <T>
	 * @param Map
	 * @return entity
	 * @throws DataAccessException
	 */
	public <T> T findByMap(Map<String, Object> paras) throws DataAccessException;

	/**
	 * @title: 查询所有数据
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> findAll() throws DataAccessException;

	/**
	 * @title: 分页查询
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @param paras
	 *            ,key值包括：page分页信息,vo/map查询条件。其中paras必须包含page。查询条件可以是vo也可以是Map<
	 *            String,Object>
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> findPage(Map<String, Object> paras) throws DataAccessException;

	/**
	 * @title:根据查询条件，查询记录总数
	 * @author: wxd
	 * @date:2013-9-3
	 * @param <T>
	 * @param paras
	 * @return 记录总数
	 * @throws DataAccessException
	 */
	public int findCount(Map<String, Object> paras) throws DataAccessException;

}
