package com.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

	@Value("${upload.path}")
	private String storage;

	@Value("${path.photo}")
	private String photo;

	@Value("${path.report}")
	private String report;

	@Value("${path.absen}")
	private String absen;

	@Value("${path.absen.masuk}")
	private String masuk;

	@Value("${path.absen.keluar}")
	private String keluar;
	
	@Value("${path.target.photo}")
	private String src;
	
	@Value("${path.report.file}")
	private String file;
	
	@Value("${path.report.photo.depan}")
	private String foto_depan;
	
	@Value("${path.report.photo.samping}")
	private String foto_samping;
	
	@Value("${path.report.photo.dalam}")
	private String foto_dalam;
	
	@Value("${path.report.photo.belakang}")
	private String foto_belakang;
	
	@Bean
	public void createFolderStorage() {
		File files = null;
		files = new File(storage);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("create storage success !");
			}
		}

		files = new File(photo);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("create folder photo success !");
			}
		}

		files = new File(report);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("create folder report success !");		
			}
		}
		
		files = new File(file);
		if (files.mkdirs()) {
			System.out.println("create folder report file success !");
		}
		files = new File(foto_depan);
		if (files.mkdirs()) {
			System.out.println("create folder report foto_depan success !");
		}
		files = new File(foto_samping);
		if (files.mkdirs()) {
			System.out.println("create folder report foto_samping success !");
		}
		files = new File(foto_dalam);
		if (files.mkdirs()) {
			System.out.println("create folder report foto_dalam success !");
		}
		files = new File(foto_belakang);
		if (files.mkdirs()) {
			System.out.println("create folder report foto_belakang success !");
		}

		files = new File(absen);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("create folder absen success !");
			}
		}

		files = new File(masuk);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("create folder absen masuk success !");
			}
		}

		files = new File(keluar);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("create folder absen keluar success !");
			}
		}

		files = new File(photo + "default.png");
		if (!files.exists()) {
			try {
				File file = new File("src/default.png");
				InputStream is = new FileInputStream(file);
				Files.copy(is, Paths.get(src + "default.png"), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("create image success !");
			} catch (IOException e) {
			}
		}

	}

}