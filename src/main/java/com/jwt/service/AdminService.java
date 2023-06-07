//package com.jwt.service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.jwt.dao.AdminDao;
//import com.jwt.dto.AdminDto;
//import com.jwt.entity.AdminEntity;
//import com.jwt.entity.Role;
//import com.jwt.repository.RoleRepository;
//import com.jwt.translator.AdminTranslator;
//
//@Service
//public class AdminService {
//	@Autowired
//	private AdminDao adminDao;
//	@Autowired
//	private AdminTranslator adminTranslator;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private RoleRepository roleRepository;
//	
//	public String saveAdmin(AdminDto adminDto) {
//		AdminEntity entity= adminTranslator.translateRequestToEntity(adminDto);
//		Role role = roleRepository.findByName("ADMIN");
//		Set<Role> roleSet = new HashSet<>();
//		roleSet.add(role);
//		entity.setRole(roleSet);
//		entity.setPassword(passwordEncoder.encode( adminDto.getPassword()));
//		  adminDao.save(entity);
//		return "success";
//	}
//
//}
