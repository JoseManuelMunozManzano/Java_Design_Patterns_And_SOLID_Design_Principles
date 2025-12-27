package com.jmunoz.sec01.segregation.good.service;

import com.jmunoz.sec01.segregation.good.entity.Entity;

//common interface to be implemented by all persistence services.
public interface PersistenceService<T extends Entity> {

	public void save(T entity);
	
	public void delete(T entity);
	
	public T findById(Long id);
	
}
