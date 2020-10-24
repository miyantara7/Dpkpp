package com.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.app.model.Absent;
import com.app.model.Notification;
import com.app.model.Person;
import com.app.pojo.PojoAbsent;
import com.app.pojo.PojoAbsentPerson;

@Repository
public class AbsentDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) {
		em.persist(entity);
	}

	@Override
	public <T> void edit(T entity) {
		em.merge(entity);
	}

	@Override
	public <T> void delete(T entity) {
		em.remove(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Absent getById(String id) throws Exception{
		List<Absent> results = em.createQuery("FROM Absent where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	public String getQueryForAbsent(String inquiry) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select person.id,person.nip,person.name, " )
		.append("(case when absents.date_in\\:\\:text is null then null else absents.date_in\\:\\:text end) as date_in," ) 
		.append("(case when absents.date_out\\:\\:text is null then null else absents.date_out\\:\\:text end) as date_out," ) 
		.append("(case when absents.location_absen_in is null then '-' else absents.location_absen_in end) as location_in," ) 
		.append("(case when absents.location_absen_out is null then '-' else absents.location_absen_out end) as location_out," ) 
		.append("(case when absents.status is null then '-' else absents.status end) as status " ) 
		.append("from "  )
		.append("	(select tp.id , tp.nip , tp.name ") 
		.append("	from tb_person tp ) as person ")
		.append("left join "  )
		.append("	(select tp.id , tp.nip , tp.\"name\",ta.date_in,ta.date_out,ta.status, "  )
		.append("	ta.location_absen_in ,ta.location_absen_out " )
		.append("	from tb_person tp "  )
		.append("	join tb_absent ta on tp.id = ta.person_id "  )
		.append("	where ta.date_in\\:\\:date = current_date  " )
		.append("   or ta.date_out\\:\\:date = current_date ) as absents "  )
		.append("on person.id = absents.id ) as absen ")
		.append("WHERE 1=1 ");
		
		
		  if (inquiry != null && !inquiry.isEmpty()) {
			   sb.append(" AND POSITION(LOWER('").append(inquiry)
			   .append("') in LOWER(CONCAT(").append("absen.id,absen.nip,absen.name,absen.date_in,absen.date_out,"
			   		+ "absen.location_in,absen.location_out,absen.status ")
			     .append("))) > 0");
			  }
		
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getAbsentByPaging(int page, int limit,String inquiry) throws Exception {	
		String sql = bBuilder("Select absen.id,absen.nip,absen.name,absen.date_in,absen.date_out,"
				+ "absen.location_in,absen.location_out,absen.status "
				+ "FROM ( ");
		
		List<Object[]> list = em.createNativeQuery(sql+getQueryForAbsent(inquiry))
				.setFirstResult((page-1)*limit)
				.setMaxResults(limit)
				.getResultList();
		
		return !list.isEmpty() ? bMapperList(list, PojoAbsent.class, "id","nip","nama","dateIn","dateOut","locationIn","locationOut","status") : list;
	}
	
	public Integer getCountAbsentByPaging(String inquiry) throws Exception {	
		String sql = bBuilder("Select count(*) "
				+ "FROM ( ");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryForAbsent(inquiry))
				.getSingleResult();
		
		return value.intValue();
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Integer> getUserAbsentById(String id) throws Exception {	
		String sql = bBuilder("with tes as (\r\n" + 
				"	select t.id,t.name,count(ta.lov_absent_id ) total,ta.person_id from tb_lov_absent t " + 
				"	left join tb_absent ta on t.id = ta.lov_absent_id\r\n" + 
				"	where ta.person_id = :id \r\n" + 
				"	group by t.id,t.name,ta.person_id\r\n" + 
				")\r\n" + 
				"select t.name,\r\n" + 
				"case when (tes.total) is null then 0 else tes.total end from tes\r\n" + 
				"right join tb_lov_absent t on tes.id = t.id");
		
		List<Object[]> list = em.createNativeQuery(sql).setParameter("id", id).getResultList();
		LinkedHashMap<String, Integer> result  = new LinkedHashMap<>();
		for(Object[] o : list) {		
			result.put((String)o[0], ((BigInteger)o[1]).intValue());	
		}
		return !list.isEmpty() ? result : null;
	}
	
	@SuppressWarnings("unchecked")
	public PojoAbsentPerson checkAbsentByPersonId(String id) throws Exception {	
		String sql = bBuilder("select ta.id,tp.nip,tp.name,ta.date_in\\:\\:text ,ta.date_out\\:\\:text,ta.status from tb_absent ta\r\n" + 
				"join tb_person tp on tp.id = ta.person_id \r\n" + 
				"where ta.date_in\\:\\:date = current_date and ta.person_id = :id");
		
		List<Object[]> list = em.createNativeQuery(sql).setParameter("id", id).getResultList();

		return !list.isEmpty() ? bMapperList(list, PojoAbsentPerson.class, "id","nip","nama","dateIn","dateOut","status").get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public Absent getAbsentById(String id) throws Exception {	

		List<Absent> list = em.createQuery("FROM Absent where id = :id")
				.setParameter("id", id)
				.getResultList();

		return !list.isEmpty() ? list.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public Absent getAbsentByPersonId(String id) throws Exception {	

		List<Absent> list = em.createQuery(bBuilder("from Absent where person.id = :id and DATE(dateIn) = current_date"))
				.setParameter("id", id)
				.getResultList();

		return !list.isEmpty() ? list.get(0) : new Absent();
	}
	
	public Integer getCountAbsentPerson(String id,String inquiry,Date periodBegin,Date periodEnd) throws Exception {	
		String sql = bBuilder("select count(*) from ( ");
		
		Query query = em.createNativeQuery(sql +getQueryHistoriAbsent(inquiry, periodBegin, periodEnd))
				.setParameter("id",id);
		
		BigInteger value = (BigInteger) setParamAbsentHistory(query, periodBegin, periodEnd).getSingleResult();
		
		return value.intValue();
	}
	
	
	public String getQueryHistoriAbsent(String inquiry,Date periodBegin,Date periodEnd) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select ta.id,(ta.date_in + interval '7 hours') as date_in,(ta.date_out + interval '7 hours') as date_out,ta.location_absen_in,ta.location_absen_out,")
				.append("ta.status,ta.langtitude_absen_in,ta.langtitude_absen_out,ta.longtitude_absen_in,")
				.append("ta.longtitude_absen_out,ta.file_name_absen_in ,ta.file_name_absen_out,")
				.append("ps.name,ps.nip,lova.name as names ")
				.append("from tb_absent ta join tb_person ps on ta.person_id = ps.id left join tb_lov_absent lova on ta.lov_absent_id = lova.id ")
				.append("where ta.person_id=:id ) as a ")
				.append("WHERE 1=1 ");
		
		
		if(periodBegin != null && periodEnd == null) {
			sb.append(" AND a.date_in\\:\\:date >= :periodBegin or a.date_out\\:\\:date >= :periodBegin");
		}else if(periodEnd != null && periodBegin == null) {
			sb.append(" AND a.date_out\\:\\:date <= :periodEnd or a.date_in\\:\\:date <= :periodEnd");
		}else if(periodBegin != null && periodEnd != null){
			sb.append(" AND a.date_in\\:\\:date >= :periodBegin  ");
			sb.append(" AND a.date_out\\:\\:date <= :periodEnd ");
		}
		
		
		  if (inquiry != null && !inquiry.isEmpty()) {
			   sb.append(" AND POSITION(LOWER('").append(inquiry)
			   .append("') in LOWER(CONCAT(").append("a.id,a.date_in,a.date_out,a.location_absen_in,a.location_absen_out,"
			   		+ "a.status,a.langtitude_absen_in,a.langtitude_absen_out,a.longtitude_absen_in,a.longtitude_absen_out,"
			   		+ "a.name,a.nip,a.names")
			     .append("))) > 0");
			  }
		  
		  return sb.toString();
	}
	
	public Query setParamAbsentHistory(Query query,Date periodBegin,Date periodEnd) throws Exception{
		if(periodBegin != null && periodEnd == null) {
			query.setParameter("periodBegin",periodBegin);
		}else if(periodEnd != null && periodBegin == null) {
			query.setParameter("periodEnd", periodEnd);
		}else if(periodBegin != null && periodEnd != null ){
			query.setParameter("periodBegin", periodBegin);
			query.setParameter("periodEnd", periodEnd);
		}		
		
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getHistoriAbsentUSer(String id,int page,int limit,String inquiry,Date periodBegin,Date periodEnd) throws Exception{
		String sb = bBuilder("SELECT a.id,a.date_in\\:\\:text,a.date_out\\:\\:text,a.location_absen_in,a.location_absen_out,"
				,"a.status,a.langtitude_absen_in,a.langtitude_absen_out,a.longtitude_absen_in,a.longtitude_absen_out,"
				,"a.file_name_absen_in ,a.file_name_absen_out,a.name,a.nip,a.names FROM ( ");

		Query query = em.createNativeQuery(sb + getQueryHistoriAbsent(inquiry, periodBegin, periodEnd))
				.setParameter("id",id);
		List<Object[]> data = setParamAbsentHistory(query, periodBegin, periodEnd)
				.setFirstResult((page-1)*limit)
				.setMaxResults(limit).getResultList();
		
		return data;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Absent> getByPersonId(String id) {
		
		List<Absent> list = em.createQuery("From Absent where person.id=:id")
								.setParameter("id", id)
								.getResultList();
		
		return list;
							
	}
}
