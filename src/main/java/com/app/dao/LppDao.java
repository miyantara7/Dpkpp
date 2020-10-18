package com.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Lpp;
import com.app.pojo.PojoLppPerson;
import com.app.pojo.PojoPersonLkh;
import com.app.pojo.PojoProgressLpp;

@Repository
public class LppDao extends BaseDao implements BaseMasterDao{

	@Override
	public <T> void save(T entity) throws Exception {
		em.persist(entity);
	}

	@Override
	public <T> void edit(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public Lpp getById(String id) throws Exception{
		List<Lpp> results = em.createQuery("FROM Lpp where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	public String getQueryLppByPersonId(String inquiry) {
		StringBuilder sb = new StringBuilder();
		sb.append("select tpl.id as personLppId,tl.id as laporanId,tl.\"name\",tl.description,\r\n"
				+ "tpl.start_date\\:\\:text,tpl.end_date\\:\\:text,tpl.status \r\n"
				+ "from \r\n"
				+ "tb_lpp tl\r\n"
				+ "join tb_person_lpp tpl on tl.id = tpl.lpp_id \r\n"
				+ "join tb_person tp on tpl.person_id = tp.id\r\n"
				+ "where tp.id = :personId ) as lpp ");
		sb.append("WHERE 1=1 ");
		
		if (inquiry != null && !inquiry.isEmpty()) {
			sb.append(" AND POSITION(LOWER('").append(inquiry).append("') in LOWER(CONCAT(")
					.append("lpp.personLppId,lpp.laporanId,lpp.name,lpp.description,lpp.start_date,lpp.end_date,lpp.status")
					.append("))) > 0");
		}

		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public List<PojoLppPerson> getLppByPersonId(String personId,String inquiry) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT lpp.personLppId,lpp.laporanId,lpp.name,lpp.description,"
				+"lpp.start_date,lpp.end_date,lpp.status "
				+"From ( ");
		List<Object[]> results = em.createNativeQuery(sb.toString() + getQueryLppByPersonId(inquiry))
				.setParameter("personId", personId)
				.getResultList();
		
		return !results.isEmpty() ? bMapperList(results, PojoLppPerson.class, "id","laporanId",
				"name","desc","startDate","endDate","status") : null;
	}
	
	public Integer getCountLppByPersonId(String personId,String inquiry) throws Exception {	
		String sql = bBuilder("Select count(*) FROM ( ");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryLppByPersonId(inquiry))
				.setParameter("personId", personId)
				.getSingleResult();
		
		return value.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public PojoProgressLpp getProgressLppById(String id) throws Exception{
		List<Object[]> results = em.createNativeQuery(bBuilder("select tpl.id , tp.\"name\" ,\r\n"
				+ "(case when tpl.start_date\\:\\:text is null then '-' else tpl.start_date\\:\\:text end) ,\r\n"
				+ "(case when tpl.end_date\\:\\:text is null then '-' else tpl.end_date\\:\\:text end),\r\n"
				+ "(case when tl2.verification_date\\:\\:text is null then '-' else tl2.verification_date\\:\\:text end),\r\n"
				+ "tl2.id as progressId,tp2.progress,tl2.status\r\n"
				+ "from \r\n"
				+ "tb_lpp tl\r\n"
				+ "join tb_person_lpp tpl on tl.id = tpl.lpp_id \r\n"
				+ "join tb_person tp on tpl.person_id = tp.id\r\n"
				+ "join tb_laporan tl2 on tl2.person_lpp_id = tpl.id \r\n"
				+ "join tb_progressing tp2 on tl2.progress_id = tp2.id\r\n"
				+ "where tpl.id = :id"))
				.setParameter("id", id)
				.getResultList();

		PojoProgressLpp progressLpp = new PojoProgressLpp();
		List<Object> listProgress = new ArrayList<>();
		for(Object[] o : results) {
			progressLpp.setId((String)o[0]);
			progressLpp.setName((String)o[1]);
			progressLpp.setStartDate((String)o[2]);
			progressLpp.setEndDate((String)o[3]);
			progressLpp.setVerificationDate((String)o[4]);
			LinkedHashMap<String, Object> progress = new LinkedHashMap<>();
			progress.put("id", (String)o[5]);
			progress.put("progress", (String)o[6]);
			progress.put("status", (Boolean)o[7]);
			listProgress.add(progress);
			progressLpp.setListProgress(listProgress);
		}
		return progressLpp;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getLaporanLppById(String id) throws Exception{
		List<Object[]> results = em.createNativeQuery(bBuilder("select tl2.id, tp.\"name\" ,\r\n"
				+ "(case when tl2.upload_date\\:\\:date\\:\\:text is null then '-' else tl2.upload_date\\:\\:date\\:\\:text end),\r\n"
				+ "(case when tl2.verification_date\\:\\:date\\:\\:text is null then '-' else tl2.verification_date\\:\\:date\\:\\:text end),\r\n"
				+ "tl2.description,tl2.file_name_depan,tl2.type_file_depan,tl2.file_name_samping,tl2.type_file_samping,\r\n"
				+ "tl2.file_name_dalam,tl2.type_file_dalam,tl2.file_name_belakang,tl2.type_file_belakang \r\n"
				+ "from \r\n"
				+ "tb_lpp tl\r\n"
				+ "join tb_person_lpp tpl on tl.id = tpl.lpp_id \r\n"
				+ "join tb_person tp on tpl.person_id = tp.id\r\n"
				+ "join tb_laporan tl2 on tl2.person_lpp_id = tpl.id \r\n"
				+ "join tb_progressing tp2 on tl2.progress_id = tp2.id\r\n"
				+ "where tl2.id = :id"))
				.setParameter("id", id)
				.getResultList();

		return results;
	}
}
