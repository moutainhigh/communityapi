package net.okdi.core.base;

import java.util.Calendar;

import net.okdi.core.sms.AbstractHttpClient;


/**
 * @ClassName: BaseService.java
 * @description: TODO
 * @author wxd
 * @date 2013-9-3
 * @version: 1.0.0
 */
public abstract class BaseServiceImpl<T> extends AbstractHttpClient<String> implements BaseService<T> {

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
