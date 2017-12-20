package com.bf.base;

import java.io.Serializable;
import java.util.Collection;

import org.jbpm.JbpmContext;

/**
 * ͳһ����ɾ��
 * @author Administrator
 * 
 */
public interface BaseDao {
	/**
	 * @param obj
	 */
	public void save(Object obj);
	/**
	 * @param obj
	 */
	public void save(Object[] objs);
	/**
	 */
	public <T> void save(Collection<T> objs);
	/**
	 */
	public <T> void remove(Class<T> entityClass,Serializable id);
	/**
	 */
	public void remove(Object[] ids);
	/**
	 */
	public <T> void remove(Collection<T> objects);
	/**
	 */
	public void update(Object obj);
	/**
	 */
	public void update(Object[] objs);
	/**
	 */
	public <T> void update(Collection<T> objs);
	/**
	 */
	public void saveOrUpdate(Object obj);
	/**
	 */
	public void saveOrUpdate(Object[] objs);
	/**
	 */
	public <T> void saveOrUpdate(Collection<T> objs); 
	//逻辑删除
	public <T> void deleteByLogic(Class<T> entity,Serializable[] ids,String id,String flag);
	
	//得到JbpmContext
	public JbpmContext getContext() ;
	
	
	
}
