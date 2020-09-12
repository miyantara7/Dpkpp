package com.app.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.pojo.PojoUnitPositionSelector;

@Repository
public class UnitPositionDao extends BaseDao implements BaseMasterDao {

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
	public List<Object> getUnitPositionByBk(PojoUnitPositionSelector unitPosition) throws Exception{	
		String sql = bBuilder("select tup.id ,concat(tu.\"name\" ,' - ', tp.\"name\") " +
				"from tb_unit tu \r\n" + 
				"join tb_unit_position tup on tu.id = tup.unit_id \r\n" + 
				"join tb_position tp on tp.id = tup.position_id \r\n" + 
				"where tu.id = :unit and tp.id = :position");
		
		List<Object[]> list = em.createNativeQuery(sql)
				.setParameter("unit", unitPosition.getUnitId())
				.setParameter("position", unitPosition.getPositionId())
				.getResultList();
		
		List<Object> listUnitPosition = new ArrayList<>();
		
		for(Object[] o : list) {
			LinkedHashMap<String, Object> unitPositions = new LinkedHashMap<>();
			unitPositions.put("key", (String)o[0]);
			unitPositions.put("value", (String)o[1]);
			listUnitPosition.add(unitPositions);
		}
		return !list.isEmpty() ? listUnitPosition : null;
	}
}
