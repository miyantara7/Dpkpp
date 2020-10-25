package com.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Lpp;
import com.app.model.Person;
import com.app.pojo.PojoPerson;

@Repository
public class PersonDao extends BaseDao implements BaseMasterDao {

	
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
	public Person getPersonByNip(String nip)throws Exception{
		List<Person> listUser = em.createQuery("FROM Person where nip = :nip")
				.setParameter("nip", nip)
				.getResultList();
		
		return !listUser.isEmpty() ? listUser.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public Person getById(String id) throws Exception{
		List<Person> results = em.createQuery("FROM Person where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	public String getQueryForSearch(String inquiry) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select tp.id ,tp.nip , tp.name,tp.gender,ru.description as role "+
				"from tb_person tp join tb_users us on us.person_id = tp.id"
				+ " join tb_role_user ru on ru.id = us.role_user_id ) as p " +
				"WHERE 1=1 ");
		
		  if (inquiry != null && !inquiry.isEmpty()) {
			   sb.append(" AND POSITION(LOWER('").append(inquiry)
			   .append("') in LOWER(CONCAT(").append("p.id,p.nip,p.name")
			     .append("))) > 0");
			  }
		  
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getAllPersonByPaging(int page,int limit,String inquiry)throws Exception{
		String sql = bBuilder("Select p.id,p.nip,p.name,p.gender,p.role "
				+ "FROM ( " );	  
		List<Object[]> list = em.createNativeQuery(sql + getQueryForSearch(inquiry))
				.setFirstResult((page-1) * limit)
				.setMaxResults(limit)
				.getResultList();
		List<Object> listPerson = new ArrayList<>();
		for(Object[] o : list) {
			HashMap<String, Object> person = new HashMap<>();
			person.put("id", (String)o[0]);
			person.put("nip", (String)o[1]);
			person.put("name", (String)o[2]);
			person.put("gender", (String)o[3]);
			person.put("role",(String)o[4]);
			listPerson.add(person);
		}
		return listPerson;
	}
	
	public Integer getCountPersonByPaging(String inquiry)throws Exception{
		String sql = bBuilder("Select count (*) "
				+ "FROM ( " );
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryForSearch(inquiry)).getSingleResult();
		
		return value.intValue();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object> getPetugas(){
		List<Object> data = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("select tp.id,tp.nip,tp.\"name\" from tb_person tp \r\n" + 
				"join tb_users us on us.person_id = tp.id\r\n" + 
				"join tb_role_user rl on rl.id = us.role_user_id \r\n" + 
				"where rl.\"name\" = 'ROLE_PENGAWAS'");
		List<Object[]> ls = em.createNativeQuery(sb.toString()).getResultList();
		
		for(Object[] o:ls) {
			HashMap<String, Object> s = new HashMap<String, Object>();
			s.put("id",o[0]);
			s.put("nip",o[1]);
			s.put("nama",o[2]);
			data.add(s);
		}
		
		
		
		return data;
	}
	
}
