package com.app.dao;

public interface BaseMasterDao {

	abstract <T> void save(T entity) throws Exception;
	abstract <T> void edit(T entity)throws Exception;
	abstract <T> void delete(T entity)throws Exception;
}
