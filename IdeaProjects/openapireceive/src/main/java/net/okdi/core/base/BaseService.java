package net.okdi.core.base;


/**
 * @ClassName: BaseService.java
 * @description: TODO
 * @author wxd
 * @date 2013-9-3
 * @version: 1.0.0
 */
public interface BaseService<T> {

	public void save(T entity) throws Exception;

	public void update(T entity) throws Exception;

	public void removeById(Long id) throws Exception;

	public void removeBatch(Long... id) throws Exception;

}
