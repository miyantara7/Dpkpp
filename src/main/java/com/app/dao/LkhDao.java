package com.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Lkh;
import com.app.pojo.PojoLkh;
import com.app.pojo.PojoPersonLkh;

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
	
//	public String getQueryListLkh(String inquiry) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select tl.id,tl.description,tp.nip,tp.\"name\",tu.\"name\" units,tp2.\"name\" positions,tl.status " ) 
//				"from tb_lkh tl " ) 
//				"join tb_person tp ON tl.person_id = tp.id" ) 
//				"join tb_unit_position tup on tp.unit_position_id = tup.id " ) 
//				"join tb_unit tu on tup.unit_id = tu.id " ) 
//				"join tb_position tp2 on tp2.id = tup.position_id " ) 
//				"where tu.unit_id = (" ) 
//				"select tup2.unit_id from tb_unit_position tup2 " ) 
//				"join tb_person tp3 on tup2.id = tp3.unit_position_id " ) 
//				"where tp3.id = :personId) and " ) 
//				"(select tp5.\"level\" from tb_unit_position tup3" ) 
//				"join tb_person tp4 on tup3.id = tp4.unit_position_id " ) 
//				"join tb_position tp5 on tup3.position_id = tp5.id " ) 
//				"where tp4.id = :personId) >= tp2.level ) as l " )
//				"WHERE 1=1 ");
//		
//		  if (inquiry != null && !inquiry.isEmpty()) {
//			   sb.append(" AND POSITION(LOWER('").append(inquiry)
//			   .append("') in LOWER(CONCAT(").append("l.id,l.description,l.nip,l.name,l.units,l.positions,l.status")
//			     .append("))) > 0");
//			  }
//		
//		
//		return sb.toString();
//	}
	
	public String getQueryListLkh(String inquiry) {
		StringBuilder sb = new StringBuilder();
		sb.append("with tl as (select tl.id,tl.description,tp.nip,tp.\"name\" pName,tu.\"name\" uName,");
		sb.append("tp2.\"name\" lName,tl.status,tu.unit_id,tp2.\"level\" ");
		sb.append("from tb_lkh tl ");
		sb.append("join tb_person tp ON tl.person_id = tp.id ");
		sb.append("join tb_unit_position tup on tp.unit_position_id = tup.id ");
		sb.append("join tb_unit tu on tup.unit_id = tu.id ");
		sb.append("join tb_position tp2 on tp2.id = tup.position_id ");
		sb.append("),");
		sb.append("tp as(");
		sb.append("select tup2.unit_id,tp4.\"level\" from tb_unit_position tup2 ");
		sb.append("join tb_person tp3 on tup2.id = tp3.unit_position_id ");
		sb.append("join tb_position tp4 on tup2.position_id = tp4.id ");
		sb.append("where tp3.id = :personId) ");
		sb.append("select tl.id,tl.description,tl.nip,tl.pName ,tl.uName ,tl.lName ,tl.status ");
		sb.append("from tl,tp ");
		sb.append("where tl.unit_id = tp.unit_id and tp.level >= tl.\"level\" ) as l ");
		sb.append("WHERE 1=1 ");

		if (inquiry != null && !inquiry.isEmpty()) {
			sb.append(" AND POSITION(LOWER('").append(inquiry).append("') in LOWER(CONCAT(")
					.append("l.id,l.description,l.nip,l.pName,l.uName,l.lName,l.status").append("))) > 0");
		}

		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoPersonLkh> getLkhByService(String personId,String inquiry,int page,int limit) throws Exception{
		String sql = bBuilder("SELECT l.id,l.description,l.nip,l.pName,l.uName,l.lName,l.status FROM ( ");
		List<Object[]> results = em.createNativeQuery(sql + getQueryListLkh(inquiry))
				.setParameter("personId", personId)
				.setFirstResult((page-1)*limit)
				.setMaxResults(limit)
				.getResultList();
		
		return bMapperList(results,PojoPersonLkh.class, "id","desc","nip","name","unit","position","status");
	}
	
	public Integer getCountLkhByService(String personId,String inquiry) throws Exception {	
		String sql = bBuilder("Select count(*) FROM ( ");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryListLkh(inquiry))
				.setParameter("personId", personId)
				.getSingleResult();
		
		return value.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoPersonLkh> getLkhByServiceMobile(String personId,String inquiry) throws Exception{
		String sql = bBuilder("SELECT l.id,l.description,l.nip,l.pName,l.uName,l.lName,l.status FROM ( ");
		List<Object[]> results = em.createNativeQuery(sql + getQueryListLkh(inquiry))
				.setParameter("personId", personId)
				.getResultList();
		
		return bMapperList(results,PojoPersonLkh.class, "id","desc","nip","name","unit","position","status");
	}
	
	@SuppressWarnings("unchecked")
	public Lkh getById(String id) {
		List<Lkh> list = em.createQuery("From Lkh where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !list.isEmpty() ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public PojoLkh getLkhById(String id) throws Exception{
		String sql = bBuilder("SELECT id,end_date\\:\\:text,val_date\\:\\:text,file_name,type_file,hasil,laporan from tb_lkh ",
				"Where id = :id ");
		List<Object[]> results = em.createNativeQuery(sql)
				.setParameter("id", id)
				.getResultList();
		
		return bMapperList(results,PojoLkh.class, "id","endDate","valDate","fileName","typeFile","hasil","lapooran").get(0);
	}
	
	public String getQueryListLkhPerson(String inquiry) {
		StringBuilder sb = new StringBuilder();
		sb.append("select tl.id,tl.description,tp.nip,tp.\"name\" pName,tu.\"name\" uName,");
		sb.append("tp2.\"name\" lName,tl.status ");
		sb.append("from tb_lkh tl ");
		sb.append("join tb_person tp ON tl.person_id = tp.id ");
		sb.append("join tb_unit_position tup on tp.unit_position_id = tup.id ");
		sb.append("join tb_unit tu on tup.unit_id = tu.id ");
		sb.append("join tb_position tp2 on tp2.id = tup.position_id ");
		sb.append("WHERE tp.id = :personId ) as l ");
		sb.append("WHERE 1=1 ");

		if (inquiry != null && !inquiry.isEmpty()) {
			sb.append(" AND POSITION(LOWER('").append(inquiry).append("') in LOWER(CONCAT(")
					.append("l.id,l.description,l.nip,l.pName,l.uName,l.lName,l.status").append("))) > 0");
		}

		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoPersonLkh> getLkhPersonById(String personId,String inquiry) throws Exception{
		String sql = bBuilder("SELECT l.id,l.description,l.nip,l.pName,l.uName,l.lName,l.status FROM ( ");
		List<Object[]> results = em.createNativeQuery(sql + getQueryListLkhPerson(inquiry))
				.setParameter("personId", personId)
				.getResultList();
		
		return bMapperList(results,PojoPersonLkh.class, "id","desc","nip","name","unit","position","status");
	}
	
	public Integer getCountLkhPersonById(String personId,String inquiry) throws Exception {	
		String sql = bBuilder("Select count(*) FROM ( ");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryListLkhPerson(inquiry))
				.setParameter("personId", personId)
				.getSingleResult();
		
		return value.intValue();
	}
}
