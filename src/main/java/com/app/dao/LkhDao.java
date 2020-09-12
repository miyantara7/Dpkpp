package com.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Lkh;
import com.app.pojo.PojoLkh;

@Repository
public class LkhDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception{
		em.persist(entity);	
	}

	@Override
	public <T> void edit(T entity) throws Exception{
		em.merge(entity);		
	}

	@Override
	public <T> void delete(T entity) throws Exception{
		// TODO Auto-generated method stub
		
	}
	
	public String getQueryListLkh(String inquiry) {
		StringBuilder sb = new StringBuilder();
		sb.append("select tl.id,tl.description,tp.nip,tp.\"name\",tu.\"name\" units,tp2.\"name\" positions,tl.status \r\n" + 
				"from tb_lkh tl \r\n" + 
				"join tb_person tp ON tl.person_id = tp.id\r\n" + 
				"join tb_unit_position tup on tp.unit_position_id = tup.id \r\n" + 
				"join tb_unit tu on tup.unit_id = tu.id \r\n" + 
				"join tb_position tp2 on tp2.id = tup.position_id \r\n" + 
				"where tu.unit_id = (\r\n" + 
				"select tup2.unit_id from tb_unit_position tup2 \r\n" + 
				"join tb_person tp3 on tup2.id = tp3.unit_position_id \r\n" + 
				"where tp3.id = :personId) and \r\n" + 
				"(select tp5.\"level\" from tb_unit_position tup3\r\n" + 
				"join tb_person tp4 on tup3.id = tp4.unit_position_id \r\n" + 
				"join tb_position tp5 on tup3.position_id = tp5.id \r\n" + 
				"where tp4.id = :personId) >= tp2.level ) as l " +
				"WHERE 1=1 ");
		
		  if (inquiry != null && !inquiry.isEmpty()) {
			   sb.append(" AND POSITION(LOWER('").append(inquiry)
			   .append("') in LOWER(CONCAT(").append("l.id,l.description,l.nip,l.name,l.units,l.positions,l.status")
			     .append("))) > 0");
			  }
		
		
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public List<PojoLkh> getLkhByService(String personId,String inquiry,int page,int limit) throws Exception{
		String sql = bBuilder("SELECT l.id,l.description,l.nip,l.name,l.units,l.positions,l.status FROM ( ");
		List<Object[]> results = em.createNativeQuery(sql + getQueryListLkh(inquiry))
				.setParameter("personId", personId)
				.setFirstResult((page-1)*limit)
				.setMaxResults(limit)
				.getResultList();
		
		return bMapperList(results,PojoLkh.class, "id","desc","nip","name","unit","position","status");
	}
	
	public Integer getCountLkhByService(String personId,String inquiry) throws Exception {	
		String sql = bBuilder("Select count(*) FROM ( ");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryListLkh(inquiry))
				.setParameter("personId", personId)
				.getSingleResult();
		
		return value.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public Lkh getById(String id) {
		List<Lkh> list = em.createQuery("From Lkh where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !list.isEmpty() ? list.get(0) : null;
	}

}
