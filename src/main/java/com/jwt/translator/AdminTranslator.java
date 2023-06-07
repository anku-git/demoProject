//package com.jwt.translator;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.jwt.dto.AdminDto;
//import com.jwt.entity.AdminEntity;
//@Component
//public class AdminTranslator {
//	@Autowired
//	private ModelMapper mapper;
//	
//	public AdminEntity translateRequestToEntity(AdminDto request) {
//		return mapper.map(request,AdminEntity.class);
//	}
//
//}
