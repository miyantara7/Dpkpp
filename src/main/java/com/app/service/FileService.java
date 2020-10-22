package com.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Laporan;
import com.app.model.Lpp;
import com.app.model.Person;

@Service
public class FileService {

	@Value("${path.report.file}")
    private String path_file;
	
	@Value("${upload.path}")
    private String path_upload;
	
	@Value("${photo.not.found}")
    private String photoNotFound;
	
	@Value("${path.photo}")
    private String path_foto;
	
	@Value("${upload.path}")
    private String path_default;
		
	public void editFotoPerson(Person person,MultipartFile file) throws Exception{
		try {
			deleteFotoPerson(person,file);
			addFotoPerson(file,person);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deleteFotoPerson(Person person,MultipartFile file) throws Exception{
		try {
			if (file != null) {
				File fileDel = new File(path_foto + "/" + person.getId() + "_" + person.getFileName());
				if (fileDel.delete()) {
					System.out.println(person.getId() + "_" + person.getFileName() + " has deleted !");
				}
			}
		} catch (Exception e) {
			throw new Exception("Cannot delete file !");
		}
	}
	
	public void addFotoPerson(MultipartFile file,Person person) throws Exception{
		try {
			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();
			Files.copy(is, Paths.get(path_foto + person.getId() + "_" + fileName), StandardCopyOption.REPLACE_EXISTING);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to store file %f "+file.getName(), e);
		}
	}
	
	public void addFileReport(MultipartFile file,Lpp lpp) throws Exception{
		try {
			Files.copy(file.getInputStream(), Paths.get(path_file + lpp.getId() + "_" + file.getOriginalFilename())
					, StandardCopyOption.REPLACE_EXISTING);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to store file %f "+file.getName(), e);
		}
	}
	
	public LinkedHashMap<String, Object> getFotoLaporan(String path,String id,String fileName,String typeFile) throws Exception{
		LinkedHashMap<String, Object> foto = new LinkedHashMap<String, Object>();
		try {			
			String filePath = id + "_" + fileName;
			File file;	
				file = new File(path + filePath);
				if (file.exists()) {
					try {				
						foto.put("foto", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file)));
						foto.put("type", FilenameUtils.getExtension(file.toString()));
						foto.put("fileName", FilenameUtils.getName(file.toString()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					try {
						file = new File(path_upload + photoNotFound);
						foto.put("foto", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file)));
						foto.put("type", FilenameUtils.getExtension(file.toString()));
						foto.put("fileName", FilenameUtils.getBaseName(file.toString()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return foto;
	}
	
	public Person getFotoPerson(Person person) throws Exception{
		try {			
			String photo;
			File file;
			file = new File(path_foto + person.getId() + "_" + person.getFileName());
			if (file.exists()) {
				try {
					photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
					person.setPhoto(photo);
					person.setFileName(person.getFileName());
					person.setTypeFile(person.getTypeFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					file = new File(path_default + photoNotFound);
					photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
					person.setPhoto(photo);
					person.setFileName(file.getName());
					person.setTypeFile("image/"+FilenameUtils.getExtension(file.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return person;
	}
	
	public void editDepan(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			deleteDepan(path,laporan);
			add(path, laporan, file);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editSamping(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			deleteSamping(path,laporan);
			add(path, laporan, file);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editDalam(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			deleteDalam(path,laporan);
			add(path, laporan, file);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editBelakang(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			deleteBelakang(path,laporan);
			add(path, laporan, file);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	public void deleteDepan(String path,Laporan laporan) throws Exception{
		try {
			File file = new File(path + laporan.getId() + "_" + laporan.getFileNameDepan());
			if (file.delete()) {
				System.out.println(laporan.getId() + "_" + laporan.getFileNameDepan()+" has deleted !");
			}
		} catch (Exception e) {
			throw new Exception("Cannot delete file !");
		}
	}
	
	public void deleteSamping(String path,Laporan laporan) throws Exception{
		try {
			File file = new File(path + laporan.getId() + "_" + laporan.getFileNameSamping());
			if (file.delete()) {
				System.out.println(laporan.getId() + "_" + laporan.getFileNameSamping()+" has deleted !");
			}
		} catch (Exception e) {
			throw new Exception("Cannot delete file !");
		}
	}
	
	public void deleteDalam(String path,Laporan laporan) throws Exception{
		try {
			File file = new File(path + laporan.getId() + "_" + laporan.getFileNameDalam());
			if (file.delete()) {
				System.out.println(laporan.getId() + "_" + laporan.getFileNameDalam()+" has deleted !");
			}
		} catch (Exception e) {
			throw new Exception("Cannot delete file !");
		}
	}
	
	public void deleteBelakang(String path,Laporan laporan) throws Exception{
		try {
			File file = new File(path + laporan.getId() + "_" + laporan.getFileNameBelakang());
			if (file.delete()) {
				System.out.println(laporan.getId() + "_" + laporan.getFileNameBelakang() +" has deleted !");
			}
		} catch (Exception e) {
			throw new Exception("Cannot delete file !");
		}
	}
	
	public void add(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			InputStream is = file.getInputStream();
			Files.copy(is, Paths.get(path + laporan.getId() + "_" + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw e;
		}
	}
}
