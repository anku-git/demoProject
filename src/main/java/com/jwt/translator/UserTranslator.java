package com.jwt.translator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jwt.dto.AuthRequest;
import com.jwt.dto.UserDto;
import com.jwt.dto.response.UserResponse;
import com.jwt.entity.UserEntity;

@Component
public class UserTranslator {
	@Autowired
	private ModelMapper mapper;
	
	public UserEntity translaterequestToEntity(UserDto request) {
		return mapper.map(request,UserEntity.class);
	}
	public UserResponse translateEntityToResponse(UserEntity entity) {
		return mapper.map(entity,UserResponse.class);
	}

}
