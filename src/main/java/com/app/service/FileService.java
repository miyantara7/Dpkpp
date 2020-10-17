package com.app.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Lpp;

@Service
public class FileService {

	@Value("${path.report.file}")
    private String path_file;
	
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
}
