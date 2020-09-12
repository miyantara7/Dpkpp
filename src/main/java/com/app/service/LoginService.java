package com.app.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.config.JwtTokenUtil;
import com.app.dao.LoginDao;
import com.app.dao.UserDao;
import com.app.dao.UserDaoHibernate;
import com.app.helper.Constants;
import com.app.helper.SessionHelper;
import com.app.model.Login;
import com.app.model.LoginResponse;
import com.app.model.Person;
import com.app.model.User;
import com.app.model.UserActivity;
import com.app.model.Users;

@Service
public class LoginService extends BaseService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserDaoHibernate userDaoHibernate;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private LoginDao loginDao;

	@Autowired
	private PersonService personService;
	
	@Value("${path.photo}")
    private String path;
	
	@Value("${photo.not.found}")
    private String photoNotFound;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public Users loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		try {	
			String filePath = user.getPerson().getId() + "_" + user.getPerson().getFileName();
			String photo;
			File file;	
				file = new File(path + filePath);
				if (file.exists()) {
					try {
						photo = Base64.getEncoder()
								.encodeToString(FileUtils.readFileToByteArray(file));
						Person person = user.getPerson();
						person.setPhoto(photo);
						person.setFileName(user.getPerson().getFileName());
						person.setTypeFile(user.getPerson().getTypeFile());
						user.setPerson(person);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					try {
						file = new File(path + photoNotFound);
						photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
						Person person = user.getPerson();
						person.setPhoto(photo);
						person.setFileName(file.getName());
						person.setTypeFile("image/"+FilenameUtils.getExtension(file.toString()));
						user.setPerson(person);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new com.app.model.Users(user,user.getId(),user.getUsername(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRoleUser().getName())));
	}
	
	public void checkUserLogin(Login login) throws Exception{
		String username = login.getUsername();
		User user = userDaoHibernate.getUserByUsername(username);
		try {
			if (user.isActiveMobile() == true) {
				throw new Exception("User has been login !");
			} 
			user.setActiveMobile(true);
			userDaoHibernate.edit(user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void insertUserActivityMobile(User user,String type,boolean is_active) throws Exception{
		try {
			user.setActiveMobile(is_active);
			insertUserActivity(user, type,Constants.MOBILE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void insertUserActivityWeb(User user,String type,boolean is_active) throws Exception{
		try {
			user.setActiveWeb(is_active);
			insertUserActivity(user, type,Constants.WEB);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void insertUserActivity(User user,String type,String device) throws Exception{

		try {
			userService.edit(user);	
			UserActivity userActivity = new UserActivity();
			userActivity.setDate(new java.sql.Timestamp(new Date().getTime()));
			userActivity.setType(type);
			userActivity.setUser(user);
			userActivity.setDevice(device);
			userActivityService.save(userActivity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public LoginResponse loginWeb(Login authenticationRequest) throws Exception{
		
		final Users userDetails = (Users) loadUserByUsername(authenticationRequest.getUsername());

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		insertUserActivityWeb(userDetails.getUser(),Constants.LOGIN,true);
		
		return new LoginResponse(token,userDetails.getUser());
	}
	
	
	public Object loginMobile(Login authenticationRequest) throws Exception{
		
		final Users userDetails = (Users) loadUserByUsername(authenticationRequest.getUsername());
		
		checkUserLogin(authenticationRequest);
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		insertUserActivityMobile(userDetails.getUser(),Constants.LOGIN,true);
		
		return new LoginResponse(token,userDetails.getUser());	
	}
	
	public void logOutMobile() throws Exception{
		String username = SessionHelper.getUser().getUsername();
		User user = userDaoHibernate.getUserByUsername(username);
		try {
			if (user == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}

			if (user.isActiveMobile() != true) {
				throw new Exception("Your not login !");	
			}
			insertUserActivityMobile(user,Constants.LOGOUT,false);
			user.setActiveMobile(false);
			userDaoHibernate.edit(user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void authenticate(String username, String password) throws Exception {		
		try {
			User user = userDao.findByUsername(username);
			
			if(!bcryptEncoder.matches(password, user.getPassword())){
				throw new Exception("Username or password incorrect !");
			}
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
	
	public void logOutWeb() throws Exception{
		String userName = SessionHelper.getUser().getUsername();
		User user = userDaoHibernate.getUserByUsername(userName);
		try {
			if (user == null) {
				throw new UsernameNotFoundException("User not found with username: " + userName);
			}
			insertUserActivityWeb(user,Constants.LOGOUT,false);
			user.setActiveMobile(false);
			userDaoHibernate.edit(user);
		} catch (Exception e) {
			throw e;
		}
	}
}