package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.LaporanDao;
import com.app.model.Laporan;
import com.app.model.PersonLpp;

@Service
public class LaporanService {

	@Autowired
	private LaporanDao laporanDao;
	
	public Laporan getById(String id) throws Exception{
		Laporan laporan = laporanDao.getById(id);
		if(laporan != null){
			return laporan;
		}else {
			throw new Exception("Laporan not exist !");
		}
	}
	
	public List<Laporan> getListByBk(String id) throws Exception{
		return laporanDao.getListByBk(id);
	}
	
	public void valIdExist(Laporan laporan) throws Exception{
		if(laporanDao.getById(laporan.getId()) == null){
			throw new Exception("Laporan not exist !");
		}
	}

	public void add(Laporan laporan) throws Exception{	
		try {
			laporanDao.save(laporan);
		} catch (IOException e) {
			throw e;
		}
	}
	
	public void update(Laporan laporan) throws Exception{
		try {
			valIdExist(laporan);
			
			laporanDao.edit(laporan);
		} catch (IOException e) {
			throw e;
		}
	}
	
	public void delete(Laporan laporan) throws Exception{
		try {
			valIdExist(laporan);
			
			laporanDao.delete(laporan);
		} catch (IOException e) {
			throw e;
		}
	}
	
	public List<Object[]> getStatusLaporanByPersonLppId(String id) throws Exception{
		return laporanDao.getStatusLaporanByPersonLppId(id);
	}
}
