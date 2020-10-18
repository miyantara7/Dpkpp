package com.app.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.LaporanDao;
import com.app.model.Laporan;

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
}
