package com.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.LkhDao;
import com.app.helper.SessionHelper;
import com.app.model.Lkh;
import com.app.pojo.PojoPagination;

@Service
public class LkhService extends BaseService  {

	@Autowired
	private LkhDao lkhDao;
	
	@Value("${path.report}")
    private String path;
	
	public void add(MultipartFile file,String lkhs) throws Exception{
		Lkh lkh = new Lkh();
		lkh = super.readValue(lkhs, Lkh.class);
		lkh.setTypeFile(file.getContentType());
		lkh.setFileName(file.getOriginalFilename());
		lkh.setStatus(false);
		lkh.setPerson(SessionHelper.getPerson());
		lkhDao.save(lkh);
		
		try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            Files.copy(is, Paths.get(path + lkh.getId()+"_"+fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
        	e.printStackTrace();
            String msg = String.format("Failed to store file %f", file.getName());

            throw new Exception(msg, e);
        }
	}
	
	public PojoPagination getListLkh(int page,int limit) throws Exception{
		PojoPagination pojoLkh = new PojoPagination();
		pojoLkh.setData(lkhDao.getLkhByService(SessionHelper.getPerson().getId(),null,page,limit));
		pojoLkh.setCount(lkhDao.getCountLkhByService(SessionHelper.getPerson().getId(),null));
		return pojoLkh;
	}
	
	public PojoPagination getListLkhBySearch(String inquiry,int page,int limit) throws Exception{
		PojoPagination pojoLkh = new PojoPagination();
		pojoLkh.setData(lkhDao.getLkhByService(SessionHelper.getPerson().getId(),inquiry,page,limit));
		pojoLkh.setCount(lkhDao.getCountLkhByService(SessionHelper.getPerson().getId(),inquiry));
		return pojoLkh;
	}
	
	public void edit(MultipartFile file,String lkhs) throws Exception{
		Lkh lkh = new Lkh();
		lkh = super.readValue(lkhs, Lkh.class);
		Lkh templkh = lkhDao.getById(lkh.getId());	
		try {
			if (templkh != null) {
				String nameFile = templkh.getFileName();
				templkh.setTypeFile(file.getContentType());
				templkh.setFileName(file.getOriginalFilename());
				templkh.setStatus(true);
				templkh.setPerson(SessionHelper.getPerson());
				lkhDao.edit(templkh);

				File fileDel = new File(path + "/" + templkh.getId() + "_" + nameFile);
				if (fileDel.delete()) {
					InputStream is = file.getInputStream();
					Files.copy(is, Paths.get(path + templkh.getId() + "_" + file.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
				} 
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
