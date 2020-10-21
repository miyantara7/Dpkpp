package com.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.UserDaoHibernate;
import com.app.helper.Constants;
import com.app.helper.SessionHelper;
import com.app.model.Person;
import com.app.model.RegisterUser;
import com.app.model.User;


@Service
public class UserService extends BaseService {

	@Autowired
	private UserDaoHibernate userDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Value("${path.photo}")
    private String pathPhoto;
	
	@Value("${upload.path}")
    private String path;
	
	@Value("${photo.not.found}")
    private String photoNotFound;
	
	@Autowired
	private UserDaoHibernate userDaoHibernate;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RoleUserService roleUserService;
	
	public void edit(User user) throws Exception{
		try {
			userDao.edit(user);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public User findById() {
		User user =  userDao.getUserById(SessionHelper.getUserId());
		Person person = user.getPerson();
		String filePath = person.getId() + "_" + person.getFileName();
		String photo;
		File file;
		file = new File(pathPhoto + filePath);
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
				file = new File(pathPhoto + photoNotFound);
				photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				person.setPhoto(photo);
				person.setFileName(file.getName());
				person.setTypeFile("image/"+FilenameUtils.getExtension(file.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return user;
		
	}
	
	
	public void updatePassword(HashMap<String, String> user) throws Exception {
		
		User userOld = findByIdUser();
		if(!bcryptEncoder.matches(user.get("oldPassword"), userOld.getPassword())) {
			throw new Exception("Password did'nt Match");
		}
		else {
			userOld.setPassword(bcryptEncoder.encode(user.get("newPassword")));
			edit(userOld);
		}
		
	}
	
	public void save(String user,MultipartFile file) throws Exception {
		User newUser = new User();
		   
		RegisterUser regisUser = super.readValue(user, RegisterUser.class);
		newUser.setUsername(regisUser.getUsername());
		newUser.setPassword(bcryptEncoder.encode(regisUser.getPassword()));
		newUser.setPerson(regisUser.getPerson());
		newUser.setRoleUser(regisUser.getRoleUser());
		File files = new File(path);
		String fileName="";
		InputStream is=null;
		if (!Files.exists(Paths.get(path))) {
	        if (!files.mkdirs()) {
	            System.out.println("Failed to create directory!");
	        }
		}
		try {
			if(userDaoHibernate.getUserByUsername(newUser.getUsername())==null) {

				if (file != null) {
					fileName = file.getOriginalFilename();
					is = file.getInputStream();
					Person person = newUser.getPerson();
					person.setTypeFile(file.getContentType());
					person.setFileName(fileName);
					newUser.setPerson(person);
				}

				personService.save(newUser.getPerson());
				userDaoHibernate.save(newUser);	
		
		        if(file != null) {
		            Files.copy(is, Paths.get(pathPhoto + newUser.getPerson().getId()+"_"+fileName),
		                    StandardCopyOption.REPLACE_EXISTING);
		        }						
			}else {
				throw new Exception("Username is exist !") ;
			}
		} catch (Exception e) {
			throw e;
		}	
	}
	
	public void setActiveByTokenExpire(String username) throws Exception{
		try {
			User user = userDaoHibernate.getUserByUsername(username);
			user.setActiveMobile(false);
			user.setActiveWeb(false);
			edit(user);
		} catch (Exception e) {
			throw e;
		}		
	}
	
	
	
	public User findByIdUser() {
		User user =  userDao.getUserById(SessionHelper.getUserId());		
		return user;
		
	}
	
	
}
