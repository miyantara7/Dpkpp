package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.BaseModel;
import com.app.model.Laporan;
import com.app.model.Lpp;

@Repository
public class LaporanDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception {
		em.persist(entity);
		
	}

	@Override
	public <T> void edit(T entity) throws Exception {
		em.merge(entity);
	}

	@Override
	public <T> void delete(T entity) throws Exception {
		em.remove(entity);	
	}
	
	@SuppressWarnings("unchecked")
	public Laporan getById(String id) throws Exception{
		List<Laporan> results = em.createQuery("FROM Laporan where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Laporan> getListByBk(String id) throws Exception{
		List<Laporan> results = em.createQuery("FROM Laporan where personLpp.id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getStatusLaporanByPersonLppId(String id) throws Exception{
		List<Object[]> results = em.createNativeQuery("select tl.id,\r\n"
				+ "tp.progress\\:\\:numeric as prog,tl.status from \r\n"
				+ "tb_person_lpp tpl \r\n"
				+ "join tb_laporan tl on tpl.id = tl.person_lpp_id \r\n"
				+ "join tb_progressing tp on tl.progress_id = tp.id\r\n"
				+ "where tpl.id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return results;
	}
}
