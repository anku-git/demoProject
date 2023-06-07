package com.jwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jwt.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
	
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByMobileNo(String mobileNo);
    Optional<UserEntity> findByMobileNoAndEmail(String mobileNo,String email);
}
