package com.app.dao;

import org.springframework.stereotype.Repository;

@Repository
public class SqlDao extends BaseDao{

	@SuppressWarnings("unchecked")
	public void generateUUID() throws Exception{
		em.createNativeQuery("CREATE EXTENSION IF NOT EXISTS uuid-ossp");
	}
}
