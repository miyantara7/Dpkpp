package com.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.LppDao;
import com.app.helper.SessionHelper;
import com.app.model.Laporan;
import com.app.model.Lpp;
import com.app.model.Person;
import com.app.model.PersonLpp;
import com.app.model.Progressing;
import com.app.pojo.PojoLaporan;
import com.app.pojo.PojoLpp;
import com.app.pojo.PojoPagination;
import com.app.pojo.PojoProgressLpp;

@Service
public class LppService extends BaseService {

	@Autowired
	private LppDao lppDao;
	
	@Autowired
	private FileService fileService;
	
	@Value("${path.report.file}")
    private String path_file;
	
	@Value("${path.report.photo.depan}")
    private String path_foto_depan;
	
	@Value("${path.report.photo.samping}")
    private String path_foto_samping;
	
	@Value("${path.report.photo.dalam}")
    private String path_foto_dalam;
	
	@Value("${path.report.photo.belakang}")
    private String path_foto_belakang;
	
	@Autowired
	private PersonLppService personLppService;
	
	@Autowired
	private ProgressingService progressingService;
	
	@Autowired
	private LaporanService laporanService;
	
	public Lpp getById(String id) throws Exception{
		Lpp lpp = lppDao.getById(id);
		if(lpp != null) {
			return lpp;
		}else {
			throw new Exception("LPP not exist !");
		}
	}
	
	public void add(MultipartFile file,String pojoLpps) throws Exception{	
		try {
			PojoLpp pojoLpp = new PojoLpp();
			pojoLpp = super.readValue(pojoLpps, PojoLpp.class);
			Lpp lpp = pojoLpp.getLpp();
			lpp.setTypeFile(file.getContentType());
			lpp.setFileName(file.getOriginalFilename());
			lpp.setCreatedBy(SessionHelper.getPerson().getName());
			lpp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			lpp.setPerson(SessionHelper.getPerson());
			lppDao.save(lpp);
			fileService.addFileReport(file, lpp);
			addDetail(pojoLpp);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void addDetail(PojoLpp pojoLpp) throws Exception{
		Lpp lpp = pojoLpp.getLpp();
		if(pojoLpp.getListPerson()!=null ||!pojoLpp.getListPerson().isEmpty()) {
			for (Person person : pojoLpp.getListPerson()) {
				PersonLpp personLpp = new PersonLpp();
				personLpp.setLpp(lpp);
				personLpp.setPerson(person);
				personLpp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				personLpp.setStartDate(lpp.getStartDate());
				personLppService.add(personLpp);
				
				List<Progressing> listProgress = progressingService.getAll();
				if (!listProgress.isEmpty()) {
					for (Progressing progress : listProgress) {
						Laporan laporan = new Laporan();
						laporan.setPersonLpp(personLpp);
						laporan.setProgressing(progress);
						laporanService.add(laporan);
					}
				} else {
					throw new Exception("Table Progressing is empty !");
				}
			}
		}

	}
	
	public PojoPagination getLppByPersonId() throws Exception{
		PojoPagination pojoLkh = new PojoPagination();
		pojoLkh.setData(lppDao.getLppByPersonId(SessionHelper.getPerson().getId(),null));
		pojoLkh.setCount(lppDao.getCountLppByPersonId(SessionHelper.getPerson().getId(),null));
		return pojoLkh;
	}
	
	public PojoProgressLpp getProgressLppById(String id) throws Exception{
		return lppDao.getProgressLppById(id);
	}
	
	public PojoLaporan getDetailsLaporanById(String id) throws Exception{
		List<Object[]> result = lppDao.getLaporanLppById(id);
		PojoLaporan pojoLaporan = new PojoLaporan();
		List<Object> listFoto = new ArrayList<Object>();
		for (Object[] o : result) {
			pojoLaporan.setId((String)o[0]);
			pojoLaporan.setName((String)o[1]);
			pojoLaporan.setUploadDate((String)o[2]);
			pojoLaporan.setVerificationDate((String)o[3]);
			pojoLaporan.setDec((String)o[4]);
			LinkedHashMap<String, Object> fotoDepan = new LinkedHashMap<String, Object>();
			fotoDepan.put("fotoDepan", fileService.getFotoLaporan(path_foto_depan, (String)o[0],(String)o[5],(String)o[6]));
			LinkedHashMap<String, Object> fotoSamping = new LinkedHashMap<String, Object>();
			fotoSamping.put("fotoSamping", fileService.getFotoLaporan(path_foto_samping, (String)o[0],(String)o[7],(String)o[8]));
			LinkedHashMap<String, Object> fotoDalam = new LinkedHashMap<String, Object>();
			fotoDalam.put("fotoDalam", fileService.getFotoLaporan(path_foto_dalam, (String)o[0],(String)o[9],(String)o[10]));
			LinkedHashMap<String, Object> fotoBelakang = new LinkedHashMap<String, Object>();
			fotoBelakang.put("fotoBelakang", fileService.getFotoLaporan(path_foto_belakang, (String)o[0],(String)o[11],(String)o[12]));
			listFoto.add(fotoDepan);
			listFoto.add(fotoSamping);
			listFoto.add(fotoDalam);
			listFoto.add(fotoBelakang);
			pojoLaporan.setListFoto(listFoto);
		}
		return pojoLaporan;
	}
	
	public void addVerification(String id) throws Exception{
		
	}
	
	public void uploadFotoLaporan(String id,MultipartFile depan,MultipartFile samping,
			MultipartFile dalam,MultipartFile belakang) throws Exception{
		Laporan laporan = laporanService.getById(id);
		
		if(depan != null) {
			if (laporan.getFileNameDepan().equals("") || laporan.getFileNameDepan() == null) {
				System.out.println("masuk");
				fileService.add(path_foto_depan, laporan, depan);	
			}else {
				fileService.edit(path_foto_depan, laporan, depan);
			}
			laporan.setFileNameDepan(depan.getOriginalFilename());
			laporan.setTypeFileDepan(depan.getContentType());
		}
		
		if(samping != null) {
			if (laporan.getFileNameSamping().equals("") || laporan.getFileNameSamping() == null) {
				fileService.add(path_foto_samping, laporan, samping);
			}else {
				fileService.edit(path_foto_samping, laporan, samping);
			}
			laporan.setFileNameSamping(samping.getOriginalFilename());
			laporan.setTypeFileSamping(samping.getContentType());
		}
		
		if(dalam != null) {
			if (laporan.getFileNameDalam().equals("") || laporan.getFileNameDalam() == null) {
				fileService.add(path_foto_dalam, laporan, dalam);
			}else {
				fileService.edit(path_foto_dalam, laporan, dalam);
			}
			laporan.setFileNameDalam(dalam.getOriginalFilename());
			laporan.setTypeFileDalam(dalam.getContentType());
		}
		
		if(belakang != null) {
			if (laporan.getFileNameBelakang().equals("") || laporan.getFileNameBelakang() == null) {
				fileService.add(path_foto_belakang, laporan, belakang);
			}else {
				fileService.edit(path_foto_belakang, laporan, belakang);
			}
			laporan.setFileNameBelakang(belakang.getOriginalFilename());
			laporan.setTypeFileBelakang(belakang.getContentType());
		}
		
		laporan.setUploadDate(new Timestamp(System.currentTimeMillis()));
		laporanService.update(laporan);
	}	
	
	public void updateLaporanIsDone(String id) throws Exception{
		PersonLpp personLpp = personLppService.getById(id);
		personLpp.setEndDate(new Timestamp(System.currentTimeMillis()));
		personLppService.update(personLpp);
	}
	
}
