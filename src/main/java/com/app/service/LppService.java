package com.app.service;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.app.pojo.PojoLpp;

@Service
public class LppService extends BaseService {

	@Autowired
	private LppDao lppDao;
	
	@Autowired
	private FileService fileService;
	
	@Value("${path.report.file}")
    private String path_file;
	
	@Autowired
	private PersonLppService personLppService;
	
	@Autowired
	private ProgressingService progressingService;
	
	@Autowired
	private LaporanService laporanService;
	
	public void add(MultipartFile file,String pojoLpps) throws Exception{	
		try {
			PojoLpp pojoLpp = new PojoLpp();
			pojoLpp = super.readValue(pojoLpps, PojoLpp.class);
			Lpp lpp = pojoLpp.getLpp();
			lpp.setTypeFile(file.getContentType());
			lpp.setFileName(file.getOriginalFilename());
			lpp.setCreatedBy(SessionHelper.getPerson().getName());
			lpp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
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
				personLppService.add(personLpp);
				
				List<Progressing> listProgress = progressingService.getAll();
				for(Progressing progress : listProgress) {
					Laporan laporan = new Laporan();
					laporan.setPersonLpp(personLpp);
					laporan.setProgressing(progress);
					laporanService.add(laporan);
				}
			}
		}

	}
	
}
