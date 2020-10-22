package com.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.app.helper.SessionHelper;
import com.app.model.Notification;

@Repository
public class NotificationDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception {
		// TODO Auto-generated method stub
		em.persist(entity);
		
	}

	@Override
	public <T> void edit(T entity) throws Exception {
		// TODO Auto-generated method stub
		em.merge(entity);
		
	}

	@Override
	public <T> void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		em.remove(entity);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getNotifVericator(String id){
		
		StringBuilder sb = new StringBuilder("with g as(\r\n" + 
				"select tn.title,pro.description ,tl.id as pro_id,\r\n" + 
				"case when tpl.id is null then lpp2.id else tpl.id end pt_id,\r\n" + 
				"case when lpp.id is null then tpp.id else lpp.id end lpp,\r\n" + 
				"case when tl.id is not null then 'Proggress' else case when p3.id is not null then 'Petugas' else 'Done' end end not_type,\r\n" + 
				"case when p.id is null then case when p2.id is null then p3.id else p2.id end else p.id end p_id,\r\n" + 
				"case when ru1.\"name\" is not null then 'ROLE_PETUGAS' else 'ROLE_VERIFICATOR' end roles,tn.id,tn.is_read\r\n" + 
				"from tb_notification tn \r\n" + 
				"left join tb_laporan tl on tn.laporan_id = tl.id \r\n" + 
				"left join tb_person_lpp tpl on tpl.id = tn.person_lpp_id\r\n" + 
				"left join tb_progressing  pro on pro.id = tl.progress_id \r\n" + 
				"left join tb_lpp lpp on lpp.id = tpl.lpp_id\r\n" + 
				"left join tb_person p on tpl.person_id = p.id\r\n" + 
				"left join tb_person_lpp lpp2 on lpp2.id = tl.person_lpp_id\r\n" + 
				"left join tb_lpp tpp on tpp.id = lpp2.lpp_id\r\n" + 
				"left join tb_person p2 on p2.id = lpp2.person_id\r\n" + 
				"left join tb_person p3 on p3.id = tn.person_id \r\n" + 
				"left join tb_users us1 on us1.person_id = p3.id\r\n" + 
				"left join tb_role_user ru1 on ru1.id = us1.role_user_id)\r\n" + 
				"select * from g\r\n" + 
				"where g.roles = :roles and g.is_read is not true\r\n");
		String role = SessionHelper.getUser().getRoleUser().getName();
		if(role.equals("ROLE_PETUGAS")) {
			sb.append(" and g.p_id =:id");
		}
		
		Query que =em.createNativeQuery(sb.toString()).setParameter("roles", id); 
		if(role.equals("ROLE_PETUGAS")) {
			que.setParameter("id", SessionHelper.getPerson().getId());
		}
		System.out.println("ini role"+id);
		List<Object[]> data = que.getResultList();
		List<Object> dta = new ArrayList<Object>();
		
		for(Object[] o:data) {
			HashMap<String, Object> f = new HashMap<String, Object>();
			
			f.put("title",o[0]);
			f.put("description",o[1]);
			f.put("proggId",o[2]);
			f.put("personLppId",o[3]);
			f.put("type",o[5]);
			f.put("personId",o[6]);
			f.put("id",o[8]);
			dta.add(f);
		}
		
		return dta;
	}
	
	@SuppressWarnings("unchecked")
	public Notification getById(String id) {
		
		List<Notification> not = em.createQuery("From Notification where id=:id")
								.setParameter("id", id)
								.getResultList();
		
		return not.isEmpty() ? null : not.get(0);
							
	}

}
