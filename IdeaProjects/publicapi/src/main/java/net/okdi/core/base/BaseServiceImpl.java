package net.okdi.core.base;




/**
 * @ClassName: BaseService.java
 * @description: 
 * @author wxd
 * @date 2013-9-3
 * @version: 1.0.0
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	public abstract BaseDao getBaseDao();

	public void removeById(Long id) throws Exception {
		getBaseDao().delete(id);
	}

	public void removeBatch(Long... id) throws Exception {
		getBaseDao().deleteBatch(id);
	}

	public void save(T entity) throws Exception {
		getBaseDao().insert(entity);
	}

	public void update(T entity) throws Exception {
		getBaseDao().update(entity);
	}


}
