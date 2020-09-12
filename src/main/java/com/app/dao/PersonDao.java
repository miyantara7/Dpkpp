package com.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Person;
import com.app.pojo.PojoPerson;

@Repository
public class PersonDao extends BaseDao implements BaseMasterDao {

	@SuppressWarnings("unchecked")
	public Person getPersonByNip(String nip)throws Exception{
		List<Person> listUser = em.createQuery("FROM Person where nip = :nip")
				.setParameter("nip", nip)
				.getResultList();
		
		return !listUser.isEmpty() ? listUser.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public Person getPersonById(String id)throws Exception{
		List<Person> listUser = em.createQuery("FROM Person where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !listUser.isEmpty() ? listUser.get(0) : null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getPojoPersonById(String id){
		
		List<Object[]> listUser = em.createNativeQuery(
				bBuilder("select tu.id userId,tu.username ,tp.id,tp.nip,tp.\"name\" personName,tp.gender " +
				",tp.type_file ,\r\n" + 
				"tp.file_name , " +
				"(case when tu2.name is null then '-' else tu2.name end) as unit," + 
				"(case when tu3.\"name\" is null then '-' else tu3.\"name\" end) as unit_head,\r\n" + 
				"(case when tp2.\"name\" is null then '-' else tp2.\"name\" end) as positions "+
				"from tb_users tu\r\n" + 
				"join tb_person tp on tu.person_id = tp.id \r\n" + 
				"left join tb_unit_position tup on tp.unit_position_id = tup.id \r\n" + 
				"left join tb_unit tu2 on tup.unit_id = tu2.id \r\n" + 
				"left join tb_unit tu3 on tu3.id = tu2.unit_id \r\n" + 
				"left join tb_position tp2 on tup.position_id = tp2.id \r\n" + 
				"where tp.id = :id "))
				.setParameter("id", id)
				.getResultList();

		return !listUser.isEmpty() ? listUser : null;
	}
	
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
		em.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public List<?> getAllPersonByPaging(int page,int limit)throws Exception{
		String sql = bBuilder("select tp.id ,tp.nip , tp.\"name\" ,tu.\"name\" unit,tp2.\"name\" positions "+
				"from tb_person tp \r\n" + 
				"left join tb_unit_position tup on tp.unit_position_id = tup.id\r\n" + 
				"left join tb_unit tu on tup.unit_id = tu.id \r\n" + 
				"left join tb_position tp2 on tup.position_id = tp2.id");
		
		List<Object[]> list = em.createNativeQuery(sql)
				.setFirstResult((page-1) * limit)
				.setMaxResults(limit)
				.getResultList();
		
		return !list.isEmpty() ? bMapperList(list, PojoPerson.class, "id","nip","name","unit","position") : list;
	}
	
	public Integer getCountPersonByPaging(int page,int limit)throws Exception{
		String sql = bBuilder("Select count (*) FROM ("+
				"select tp.id ,tp.nip , tp.\"name\" ,tu.\"name\" unit,tp2.\"name\" positions "+
				"from tb_person tp \r\n" + 
				"join tb_unit_position tup on tp.unit_position_id = tup.id\r\n" + 
				"join tb_unit tu on tup.unit_id = tu.id \r\n" + 
				"join tb_position tp2 on tup.position_id = tp2.id )"+
				"as person");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql)
				.setFirstResult((page-1) * limit)
				.setMaxResults(limit).getSingleResult();
		
		return value.intValue();
	}
	
	
	public String getQueryForSearch(String inquiry) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("from tb_person tp \r\n" + 
				"left join tb_unit_position tup on tp.unit_position_id = tup.id\r\n" + 
				"left join tb_unit tu on tup.unit_id = tu.id \r\n" + 
				"left join tb_position tp2 on tup.position_id = tp2.id ) as p " +
				"WHERE 1=1 ");
		
		  if (inquiry != null && !inquiry.isEmpty()) {
			   sb.append(" AND POSITION(LOWER('").append(inquiry)
			   .append("') in LOWER(CONCAT(").append("p.id,p.nip,p.name,p.unit,p.positions")
			     .append("))) > 0");
			  }
		  
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getAllPersonBySearch(int page,int limit,String inquiry)throws Exception{
		String sql = bBuilder("Select p.id,p.nip,p.name,p.unit,p.positions "
				+ "FROM (select tp.id ,tp.nip , tp.name ,tu.name unit,tp2.name positions " );	  
		List<Object[]> list = em.createNativeQuery(sql + getQueryForSearch(inquiry))
				.setFirstResult((page-1) * limit)
				.setMaxResults(limit)
				.getResultList();
		
		return !list.isEmpty() ? bMapperList(list, PojoPerson.class, "id","nip","name","unit","position") : list;
	}
	
	public Integer getCountPersonBySearch(int page,int limit,String inquiry)throws Exception{
		String sql = bBuilder("Select count (*) "
				+ "FROM (select tp.id ,tp.nip , tp.name ,tu.name unit,tp2.name positions " );
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryForSearch(inquiry))
				.setFirstResult((page-1) * limit)
				.setMaxResults(limit).getSingleResult();
		
		return value.intValue();
	}
	
}
