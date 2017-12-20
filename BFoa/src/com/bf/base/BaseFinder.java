package com.bf.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.bf.common.PageView;

/**
 * @author Administrator
 * 
 */
public interface BaseFinder<T> {
	/**
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public T findById(Class<T> entityClass, Serializable id);

	/**
	 * @param entityClass
	 * @param xql
	 * @return
	 */
	public List<T> findAll(Class<T> entityClass, String xql);

	/**
	 * @param entityClass
	 * @param xql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */

	public PageView<T> findByPage(Class<T> entityClass, String xql, int pageNo,
			int pageSize);

	/**
	 * @param entityClass
	 * @param xql
	 * @param key
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */

	public PageView<T> findByPage(Class<T> entityClass, String xql, Object key,
			int pageNo, int pageSize);

	/**
	 * @param entityClass
	 * @param xql
	 * @param keys
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageView<T> findByPage(Class<T> entityClass, String xql,
			Object[] keys, int pageNo, int pageSize);

	// 按条件查询对象
	public T findByKey(Class<T> entityClass, String xql, Object[] keys);

	// 按条件查询集合
	public List<T> findAllKey(Class<T> entityClass, String xql, Object[] keys);
	
	public List<T> findAllByCollection(Class<T> entityClass,String xql,Collection cn);

}
