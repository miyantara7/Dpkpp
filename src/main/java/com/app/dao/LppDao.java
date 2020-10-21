package com.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Lpp;
import com.app.pojo.PojoLppPerson;
import com.app.pojo.PojoLppPetugas;
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
		em.remove(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Lpp getById(String id) throws Exception{
		List<Lpp> results = em.createQuery("FROM Lpp where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	
	@SuppressWarnings("unchecked")
	public void deleteById(String id) throws Exception {
		List<Lpp> results = em.createQuery("FROM Lpp where id = :id")
				.setParameter("id", id)
				.getResultList();

		if (!results.isEmpty()) {
			delete(results.get(0));
		}
	}
	
	public String getQueryGetLppAdmin() {
		StringBuilder sb = new StringBuilder();
		sb.append("select tl.id,tl.code,tl.name from tb_lpp tl");
		
		return sb.toString();
		
	}
	
	public String getQueryGetLppAdminDetail() {
		StringBuilder sb = new StringBuilder();
		sb.append("select tp.id,tl.code,tl.\"name\" as na,tl.description,p.\"name\" ,tp.start_date ,tp.verification_date ,tp.end_date ,tp.status from tb_lpp tl\r\n" + 
				"join tb_person_lpp tp on tp.lpp_id = tl.id \r\n" + 
				"join tb_person p on p.id = tp.person_id \r\n" + 
				"where tl.id =:id");
		
		return sb.toString();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getLppAdmin(int page,int limit){
		List<Object> list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder().append(getQueryGetLppAdmin());
		List<Object[]> lt = em.createNativeQuery(sb.toString()).setFirstResult((page-1)*limit)
				.setMaxResults(limit).getResultList();
		for(Object[] o:lt) {
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id",o[0]);
			data.put("code",o[1]);
			data.put("name",o[2]);
			list.add(data);
		}
		return list;
		
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
	
	public Integer getCountLppByAdmin() throws Exception {	
		StringBuilder querySb = new StringBuilder();
		querySb.append("select count(*) from (");
		querySb.append(getQueryGetLppAdmin());
		querySb.append(")");
		querySb.append("as emp");
		
		BigInteger value = (BigInteger) em.createNativeQuery(querySb.toString())
				.getSingleResult();
		
		return value.intValue();
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
	public List<Object[]> getDetailLppPersonbyAdmin(String id){
		List<Object[]> og = new ArrayList<Object[]>();
		StringBuilder sb = new StringBuilder();
		sb.append("select tl.id as tt_id,tl.code,tl.\"name\" as ts_name,\r\n" + 
				"tl.description as tp_des,p.\"name\" ,tp.start_date ,\r\n" + 
				"tp.verification_date ,tp.end_date ,tp.status,\r\n" + 
				"la.upload_date as pg_up,la.verification_date as pg_ver,la.status as la_status ,pro.description,la.id \r\n" + 
				"from tb_lpp tl\r\n" + 
				"join tb_person_lpp tp on tp.lpp_id = tl.id \r\n" + 
				"join tb_person p on p.id = tp.person_id\r\n" + 
				"join tb_laporan la on la.person_lpp_id = tp.id\r\n" + 
				"join tb_progressing pro on pro.id = la.progress_id \r\n" + 
				"where tp.id =:id\r\n" + 
				"order by pro.code");
		og = em.createNativeQuery(sb.toString()).setParameter("id", id).getResultList();
		return og;
	}
	
	@SuppressWarnings("unchecked")
	public PojoProgressLpp getProgressLppById(String id) throws Exception{
		List<Object[]> results = em.createNativeQuery(bBuilder("select tpl.id , tp.\"name\" ,\r\n"
				+ "(case when tpl.start_date\\:\\:text is null then '-' else tpl.start_date\\:\\:text end) ,\r\n"
				+ "(case when tpl.end_date\\:\\:text is null then '-' else tpl.end_date\\:\\:text end),\r\n"
				+ "(case when tpl.verification_date\\:\\:text is null then '-' else tpl.verification_date\\:\\:text end),\r\n"
				+ "tl2.id as progressId,tp2.progress,tl2.status,tl.name as name_tl\r\n"
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
			progressLpp.setLaporan((String)o[8]);
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
	public List<Object[]> getDetailLppAdmin(String id){
		StringBuilder sb = new StringBuilder(getQueryGetLppAdminDetail());
		List<Object[]> data = em.createNativeQuery(sb.toString()).setParameter("id", id).getResultList();
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getLaporanLppById(String id) throws Exception{
		List<Object[]> results = em.createNativeQuery(bBuilder("select tl2.id, tp.\"name\" ,\r\n"
				+ "(case when tl2.upload_date\\:\\:date\\:\\:text is null then null else tl2.upload_date\\:\\:date\\:\\:text end),\r\n"
				+ "(case when tl2.verification_date\\:\\:date\\:\\:text is null then null else tl2.verification_date\\:\\:date\\:\\:text end),\r\n"
				+ "tl2.description,tl2.file_name_depan,tl2.type_file_depan,tl2.file_name_samping,tl2.type_file_samping,\r\n"
				+ "tl2.file_name_dalam,tl2.type_file_dalam,tl2.file_name_belakang,tl2.type_file_belakang,tl.name as name_pro,tp2.description as desc_y \r\n"
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
	
	
	public String getQueryLppByVerificator(String inquiry) {
		StringBuilder sb = new StringBuilder();
		sb.append("select tpl.id as personLppId,tl.id as laporanId,tl.\"name\",tp.\"name\" as petugas,tl.description,\r\n"
				+ "tpl.start_date\\:\\:text,tpl.end_date\\:\\:text,tpl.status \r\n"
				+ "from \r\n"
				+ "tb_lpp tl\r\n"
				+ "join tb_person_lpp tpl on tl.id = tpl.lpp_id \r\n"
				+ "join tb_person tp on tpl.person_id = tp.id ) as lpp ");
		sb.append("WHERE 1=1 ");
		
		if (inquiry != null && !inquiry.isEmpty()) {
			sb.append(" AND POSITION(LOWER('").append(inquiry).append("') in LOWER(CONCAT(")
					.append("lpp.personLppId,lpp.laporanId,lpp.name,lpp.petugas,lpp.description,"
							+ "lpp.start_date,lpp.end_date,lpp.status")
					.append("))) > 0");
		}

		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoLppPetugas> getLppByVerificator(String inquiry) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT lpp.personLppId,lpp.laporanId,lpp.name,lpp.petugas,lpp.description,"
				+"lpp.start_date,lpp.end_date,lpp.status "
				+"From ( ");
		List<Object[]> results = em.createNativeQuery(sb.toString() + getQueryLppByVerificator(inquiry))
				.getResultList();
		
		return !results.isEmpty() ? bMapperList(results, PojoLppPetugas.class, "id","laporanId",
				"nameLpp","petugas","desc","startDate","endDate","status") : null;
	}
	public Integer getCountLppByVerificator(String inquiry) throws Exception {	
		String sql = bBuilder("Select count(*) FROM ( ");
		
		BigInteger value = (BigInteger) em.createNativeQuery(sql+getQueryLppByVerificator(inquiry))
				.getSingleResult();
		
		return value.intValue();
	}
}
