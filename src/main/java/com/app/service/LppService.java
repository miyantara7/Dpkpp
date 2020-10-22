package com.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.LppDao;
import com.app.helper.Constants;
import com.app.helper.SessionHelper;
import com.app.model.Laporan;
import com.app.model.Lpp;
import com.app.model.Notification;
import com.app.model.Person;
import com.app.model.PersonLpp;
import com.app.model.Progressing;
import com.app.pojo.PojoDetailLppAdmin;
import com.app.pojo.PojoLaporan;
import com.app.pojo.PojoLaporanAdmin;
import com.app.pojo.PojoLpp;
import com.app.pojo.PojoLppPersonDetailAdmin;
import com.app.pojo.PojoPagination;
import com.app.pojo.PojoProgressLpp;

@Service
@Transactional
public class LppService extends BaseService {

	@Autowired
	private LppDao lppDao;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private NotificationService notService;

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

	@Autowired
	private PersonService personService;

	public Lpp getById(String id) throws Exception {
		Lpp lpp = lppDao.getById(id);
		if (lpp != null) {
			return lpp;
		} else {
			throw new Exception("LPP not exist !");
		}
	}
	
	public void valIdExist(Lpp lpp) throws Exception{
		if(lppDao.getById(lpp.getId()) == null){
			throw new Exception("LPP not exist !");
		}
	}

	@Transactional
	public void add(MultipartFile file,String pojoLpps) throws Exception {
		try {
			PojoLpp pojoLpp = new PojoLpp();
			pojoLpp = super.readValue(pojoLpps, PojoLpp.class);
			Lpp lpp = pojoLpp.getLpp();
			lpp.setCreatedBy(SessionHelper.getPerson().getName());
			lpp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			lpp.setPerson(SessionHelper.getPerson());
			lppDao.save(lpp);
			if(file != null) {
				fileService.addFileReport(file, lpp);
			}
			addDetail(pojoLpp);
		} catch (IOException e) {
			throw e;
		}
	}

	@Transactional
	public void addDetail(PojoLpp pojoLpp) throws Exception {
		Lpp lpp = pojoLpp.getLpp();
		if (pojoLpp.getListPerson() != null || !pojoLpp.getListPerson().isEmpty()) {
			for (Person person : pojoLpp.getListPerson()) {
				personService.valIdExist(person);
				PersonLpp personLpp = new PersonLpp();
				personLpp.setLpp(lpp);
				personLpp.setPerson(person);
				personLpp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				personLpp.setStartDate(lpp.getStartDate());
				personLppService.add(personLpp);
				Notification not = new Notification();
				not.setPerson(personLpp.getPerson());
				not.setTitle(Constants.NEW_LPP_TITLE+pojoLpp.getLpp().getName());
				notService.save(not);

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

	public PojoPagination getLppByPersonId(java.sql.Date start,java.sql.Date end) throws Exception {
		PojoPagination pojoLkh = new PojoPagination();
		pojoLkh.setData(lppDao.getLppByPersonId(SessionHelper.getPerson().getId(), null,start,end));
		pojoLkh.setCount(lppDao.getCountLppByPersonId(SessionHelper.getPerson().getId(), null,start,end));
		return pojoLkh;
	}
	
	
	public PojoPagination getLppByPersonIdAdmin(String id,java.sql.Date start,java.sql.Date end) throws Exception {
		PojoPagination pojoLkh = new PojoPagination();
		pojoLkh.setData(lppDao.getLppByPersonId(id, null,start,end));
		pojoLkh.setCount(lppDao.getCountLppByPersonId(SessionHelper.getPerson().getId(), null,start,end));
		return pojoLkh;
	}
	
	public PojoPagination getLppByVerificator() throws Exception {
		PojoPagination pojoLkh = new PojoPagination();
		pojoLkh.setData(lppDao.getLppByVerificator(null));
		pojoLkh.setCount(lppDao.getCountLppByVerificator(null));
		return pojoLkh;
	}

	public PojoProgressLpp getProgressLppById(String id) throws Exception {
		return lppDao.getProgressLppById(id);
	}

	public PojoLaporan getDetailsLaporanById(String id) throws Exception {
		List<Object[]> result = lppDao.getLaporanLppById(id);
		PojoLaporan pojoLaporan = new PojoLaporan();
		for (Object[] o : result) {
			pojoLaporan.setId((String) o[0]);
			pojoLaporan.setProggress((String)o[14]);
			pojoLaporan.setProjek((String)o[13]);
			pojoLaporan.setName((String) o[1]);
			pojoLaporan.setUploadDate((String) o[2]);
			pojoLaporan.setVerificationDate((String) o[3]);
			pojoLaporan.setDec((String) o[4]);
			pojoLaporan.setFotoDepan(
					fileService.getFotoLaporan(path_foto_depan, (String) o[0], (String) o[5], (String) o[6]));
			pojoLaporan.setFotoBelakang(
					fileService.getFotoLaporan(path_foto_belakang, (String) o[0], (String) o[11], (String) o[12]));
			pojoLaporan.setFotoSamping(
					fileService.getFotoLaporan(path_foto_samping, (String) o[0], (String) o[7], (String) o[8]));
			pojoLaporan.setFotoDalam(
					fileService.getFotoLaporan(path_foto_dalam, (String) o[0], (String) o[9], (String) o[10]));

		}
		return pojoLaporan;
	}

	public void verificationPersonLpp(String id) throws Exception {
		try {
			List<Object[]> result = laporanService.getStatusLaporanByPersonLppId(id);
			for (Object[] o : result) {

				if ((Boolean) o[2] == false) {
					throw new Exception(((BigDecimal) o[1]).toString() + " % Progress has not been verified !");
				}

				PersonLpp personLpp = personLppService.getById(id);
				personLpp.setVerificationDate(new Timestamp(System.currentTimeMillis()));
				personLpp.setStatus(true);
				personLppService.update(personLpp);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void verificationProgress(String id) throws Exception {
		try {
			Laporan laporan = laporanService.getById(id);
			if (laporan != null) {
				laporan.setVerificationDate(new Timestamp(System.currentTimeMillis()));
				laporan.setStatus(true);
				laporanService.update(laporan);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public PojoPagination getLppbyAdmin(int page, int limit,String inq) throws Exception {
		PojoPagination pj = new PojoPagination();
		pj.setData(lppDao.getLppAdmin(page, limit,inq));
		pj.setCount(lppDao.getCountLppByAdmin(inq));
		return pj;
	}

	public PojoDetailLppAdmin getAdminDetail(String id) {
		PojoDetailLppAdmin pj = new PojoDetailLppAdmin();
		List<Object> ps = new ArrayList<Object>();
		for (Object[] o : lppDao.getDetailLppAdmin(id)) {
			pj.setId((String)o[0]);
			pj.setCode((String) o[1]);
			pj.setName((String) o[2]);
			pj.setDescription((String) o[3]);
			HashMap<String, Object> data = new HashMap<String, Object>();
		if(o[4] !=null) {
			data.put("id", o[0]);
			data.put("name", o[4]);
			data.put("startDate", o[5]);
			data.put("verikasiDate", o[6]);
			data.put("endDate", o[7]);
			data.put("status", o[8]);
			ps.add(data);
		}

		}
		pj.setListPerson(ps);
		return pj;
	}

	@Transactional
	public void uploadFotoLaporan(String id, MultipartFile depan, MultipartFile samping, MultipartFile dalam,
			MultipartFile belakang,String desc) throws Exception {
		try {
			Laporan laporan = laporanService.getById(id);

			if (depan != null) {
				if (laporan.getFileNameDepan() == null && laporan.getTypeFileDepan() == null) {
					fileService.add(path_foto_depan, laporan, depan);
				} else {
					fileService.editDepan(path_foto_depan, laporan, depan);
				}
				laporan.setFileNameDepan(depan.getOriginalFilename());
				laporan.setTypeFileDepan(depan.getContentType());
			}

			if (samping != null) {
				if (laporan.getFileNameSamping() == null && laporan.getTypeFileSamping() == null) {
					fileService.add(path_foto_samping, laporan, samping);
				} else {
					fileService.editSamping(path_foto_samping, laporan, samping);
				}
				laporan.setFileNameSamping(samping.getOriginalFilename());
				laporan.setTypeFileSamping(samping.getContentType());
			}

			if (dalam != null) {
				if (laporan.getFileNameDalam() == null && laporan.getTypeFileDalam() == null) {
					fileService.add(path_foto_dalam, laporan, dalam);
				} else {
					fileService.editDalam(path_foto_dalam, laporan, dalam);
				}
				laporan.setFileNameDalam(dalam.getOriginalFilename());
				laporan.setTypeFileDalam(dalam.getContentType());
			}

			if (belakang != null) {
				if (laporan.getFileNameBelakang() == null && laporan.getTypeFileBelakang() == null) {
					fileService.add(path_foto_belakang, laporan, belakang);
				} else {
					fileService.editBelakang(path_foto_belakang, laporan, belakang);
				}
				laporan.setFileNameBelakang(belakang.getOriginalFilename());
				laporan.setTypeFileBelakang(belakang.getContentType());
			}
			laporan.setDesc(desc);
			laporan.setUploadDate(new Timestamp(System.currentTimeMillis()));
			laporanService.update(laporan);
			Notification not = new Notification();
			not.setTitle(laporan.getPersonLpp().getPerson().getName()+Constants.LPP_UPLOAD);
			not.setLaporan(laporan);
			notService.save(not);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	

	@Transactional
	public void updateLaporanIsDone(String id) throws Exception {
		PersonLpp personLpp = personLppService.getById(id);
		personLpp.setEndDate(new Timestamp(System.currentTimeMillis()));
		personLppService.updatePersonLpp(personLpp);
		Notification not = new Notification();
		not.setTitle(personLpp.getPerson().getName()+Constants.LPP_DONE);
		not.setPersonLpp(personLpp);
		notService.save(not);
	}
	
	
	
	public PojoLppPersonDetailAdmin getPersonLppDetailAdmin(String id) {
		PojoLppPersonDetailAdmin pj = new PojoLppPersonDetailAdmin();
		List<Object> ps = new ArrayList<Object>();
		for (Object[] o : lppDao.getDetailLppPersonbyAdmin(id)) {
			pj.setPetugas((String)o[4]);
			pj.setStartDate((Date)o[5]);
			pj.setVerikasiDate((Date)o[6]);
			pj.setEndDate((Date)o[7]);
			pj.setProjek((String)o[2]);
			pj.setDescription((String)o[3]);
			pj.setStatus((Boolean)o[8]);
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", o[13]);
			data.put("uploadDate", o[9]);
			data.put("verikasiDate", o[10]);
			data.put("status", o[11]);
			data.put("proggress", o[12]);

			ps.add(data);

		}
		
		pj.setListProggres(ps);	
		return pj;
	}
	
	public PojoLaporanAdmin getDetailsLaporanByIdAdminDtl(String id) throws Exception{
		List<Object[]> result = lppDao.getLaporanLppById(id);
		PojoLaporanAdmin pojoLaporan = new PojoLaporanAdmin();
		for (Object[] o : result) {
			pojoLaporan.setId((String)o[0]);
			pojoLaporan.setName((String)o[1]);
			pojoLaporan.setUploadDate((String)o[2]);
			pojoLaporan.setVerificationDate((String)o[3]);
			pojoLaporan.setDec((String)o[4]);
			pojoLaporan.setProyek((String)o[13]);
		}
		return pojoLaporan;
	}
	
	public PojoLaporanAdmin getDetailsLaporanUser(String id) throws Exception{
		List<Object[]> result = lppDao.getLaporanLppById(id);
		PojoLaporanAdmin pojoLaporan = new PojoLaporanAdmin();
		List<Object> listFoto = new ArrayList<Object>();
		for (Object[] o : result) {
			pojoLaporan.setId((String)o[0]);
			pojoLaporan.setName((String)o[1]);
			pojoLaporan.setUploadDate((String)o[2]);
			pojoLaporan.setVerificationDate((String)o[3]);
			pojoLaporan.setDec((String)o[4]);
			pojoLaporan.setProyek((String)o[13]);
			listFoto.add(fileService.getFotoLaporan(path_foto_depan, (String)o[0],(String)o[5],(String)o[6]));
			listFoto.add(fileService.getFotoLaporan(path_foto_samping, (String)o[0],(String)o[7],(String)o[8]));
			listFoto.add(fileService.getFotoLaporan(path_foto_dalam, (String)o[0],(String)o[9],(String)o[10]));
			listFoto.add(fileService.getFotoLaporan(path_foto_belakang, (String)o[0],(String)o[11],(String)o[12]));
			pojoLaporan.setListFoto(listFoto);
		}
		return pojoLaporan;
	}
	
	public void deleteLpp(String id) throws Exception{
		try {
			List<PersonLpp> listPersonLpp = personLppService.getByBk(id);
			if (!listPersonLpp.isEmpty() || listPersonLpp != null) {
				for (PersonLpp personLpp : listPersonLpp) {				
					deleteLaporan(personLpp.getId());
					personLppService.delete(personLpp);
				}
			}
			delete(getById(id));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deleteLaporan(String personLppId) throws Exception{
		try {
			List<Laporan> listLaporan = laporanService.getListByBk(personLppId);
			if (!listLaporan.isEmpty() || listLaporan != null) {
				for (Laporan laporan : listLaporan) {
					fileService.deleteDepan(path_foto_depan, laporan);
					fileService.deleteSamping(path_foto_samping, laporan);
					fileService.deleteDalam(path_foto_dalam, laporan);
					fileService.deleteBelakang(path_foto_belakang, laporan);
					laporanService.delete(laporan);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void delete(Lpp lpp) throws Exception,RollbackException	 {	
		try {
			valIdExist(lpp);

			lppDao.delete(lpp);
		} catch (Exception e) {
			throw e;
		}
	}

	public void addPersonLpp(String id,PojoLpp pojoLpp) throws Exception{
		try {
			Lpp lpp = getById(id);
			lpp.setStartDate(pojoLpp.getLpp().getStartDate());
			pojoLpp.setLpp(lpp);
			addDetail(pojoLpp);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editLpp(Lpp lpp) throws Exception {
		try {
			if(lpp.getId() == null) {
				throw new Exception("id cannot be null");
			}
			Lpp old = getById(lpp.getId());
			old.setDesc(lpp.getDesc());
			old.setLocation(lpp.getLocation());
			old.setName(lpp.getName());
			lppDao.edit(old);
		} catch (Exception e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void editPersonLpp(String id,Person person) throws Exception{
		try {
			PersonLpp personLpp = personLppService.getById(id);
			personLpp.setPerson(person);
			personLppService.update(personLpp);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deletePersonLpp(String id) throws Exception{
		try {
			PersonLpp personLpp = personLppService.getById(id);
			deleteLaporan(personLpp.getId());
			personLppService.delete(personLpp);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Lpp> getByPersonId(String id) throws Exception{
		return lppDao.getByPersonId(id);
	}
	
}
