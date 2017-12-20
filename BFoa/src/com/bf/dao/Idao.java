package com.bf.dao;

import java.io.Serializable;

import org.jbpm.JbpmContext;

public interface Idao {
	/**
	 * @param obj
	 */
	public void save(Object obj);

	/**
    */
	public <T> void remove(Class<T> entityClass, Serializable id);

	/**
	 * @param obj
	 */
	public void remove(Object obj);

	/**
	 * @param obj
	 */
	public void update(Object obj);


	public void saveOrUpdate(Object entity);

	public <T> void deleteByLogic(Class<T> entity, Serializable[] ids,
			String id, String flag);
	
	
	
	//得到JBPM中的JbpmContext
	public JbpmContext getContext();
}
