package com.jwt.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jwt.entity.UserEntity;
import com.jwt.repository.UserRepository;

@Repository
public class UserDao {
	@Autowired
	private UserRepository authRepository;
	
	public UserEntity save(UserEntity entity) {
		
		return authRepository.save(entity);
	}
	public Optional<UserEntity> findByUserEmail(String email) {
		return authRepository.findByEmail(email);
	}
	
	public Optional<UserEntity> findById(Long id) {
		return authRepository.findById(id);
	}
	
	public Optional<UserEntity> findByUserMobileNo(String mobileNo) {
		return authRepository.findByMobileNo(mobileNo);
	}
//	public Optional<UserEntity> findByUserMobileNoAndEmail(String mobileNo,String email) {
//		return authRepository.findByMobileNoAndEmail(mobileNo,email);
//	}
}
