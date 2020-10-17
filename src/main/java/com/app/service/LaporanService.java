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
	
	public void add(Laporan laporan) throws Exception{	
		try {
			laporanDao.save(laporan);
		} catch (IOException e) {
			throw e;
		}
	}
}
