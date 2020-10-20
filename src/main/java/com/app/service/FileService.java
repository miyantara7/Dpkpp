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
	
	public void addFileReport(MultipartFile file,Lpp lpp) throws Exception{
		try {
			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();
			Files.copy(is, Paths.get(path_file + lpp.getId() + "_" + fileName), StandardCopyOption.REPLACE_EXISTING);
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
				System.out.println(file.toString());
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
						file = new File(photoNotFound);
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
	
	public void edit(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			File fileDel = new File(path + laporan.getId() + "_" + laporan.getFileNameDepan());
			if (fileDel.delete()) {
				InputStream is = file.getInputStream();
				Files.copy(is, Paths.get(path + laporan.getId() + "_" + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void add(String path,Laporan laporan,MultipartFile file) throws Exception{
		try {
			InputStream is = file.getInputStream();
			Files.copy(is, Paths.get(path + laporan.getId() + "_" + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			System.out.println(path + laporan.getId() + "_" + file.getOriginalFilename());
		} catch (Exception e) {
			throw e;
		}
	}
}
