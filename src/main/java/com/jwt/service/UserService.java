package com.jwt.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.dao.UserDao;
import com.jwt.dto.AdminDto;
import com.jwt.dto.ForgetPasswordDto;
import com.jwt.dto.UserDto;
import com.jwt.dto.response.UserResponse;
import com.jwt.entity.Role;
import com.jwt.entity.UserEntity;
import com.jwt.helper.MailHelper;
import com.jwt.helper.OtpHelper;
import com.jwt.repository.RoleRepository;
import com.jwt.translator.UserTranslator;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserTranslator userTranslator;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MailHelper mailHelper;
	@Autowired
	private OtpHelper otpHelper;

	public String saveUser(UserDto userDto) {
		UserEntity entity = userTranslator.translaterequestToEntity(userDto);
		Role role = roleRepository.findByName("ROLE_USER");
		List<Role> roleSet = new ArrayList<>();
		roleSet.add(role);
		entity.setRoles(roleSet);
		entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDao.save(entity);
		// mailHelper.sendMail(userDto.getEmail(),userDto.getName(),
		// userDto.getPassword());
		// otpHelper.sendOtpToUser(userDto.getMobileNo());
		return "success";
	}

	public String saveAdmin(UserDto userDto) {
		UserEntity entity = userTranslator.translaterequestToEntity(userDto);
		Role role = roleRepository.findByName("ROLE_ADMIN");
		List<Role> roleSet = new ArrayList<>();
		roleSet.add(role);
		entity.setRoles(roleSet);
		entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		//mailHelper.sendMail(userDto.getEmail(), userDto.getName(), userDto.getPassword());
		userDao.save(entity);
		return "success";
	}

	public UserResponse getById(Long id) {

		UserResponse response = userTranslator.translateEntityToResponse(userDao.findById(id).orElseThrow());
		return response;
	}

	public String forgetPassword(ForgetPasswordDto forgetPasswordDto) {
		UserEntity entity=userDao.findByUserMobileNo(forgetPasswordDto.getMobileNo()).orElseThrow();
		if(entity!=null) {
		 String otp=otpHelper.sendOtpToUser(forgetPasswordDto.getMobileNo());

		 entity.setOtp(otp);
		 userDao.save(entity);
		}
		return "otp send successFully";	
	}
	public String verifyUserOtp(ForgetPasswordDto forgetPasswordDto) {
		Optional<UserEntity> entity=userDao.findByUserMobileNo(forgetPasswordDto.getMobileNo());
		if(entity.isPresent() && forgetPasswordDto.getOtp().equals(entity.get().getOtp())) 
		{
			mailHelper.sendMail(entity.get().getEmail(), entity.get().getName());
		}
		return "user verify successFully";
			
		
	}

}
