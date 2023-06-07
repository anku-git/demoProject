//package com.jwt.entity;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import lombok.Data;
//@Data
//@Entity
//public class AdminEntity {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	private String name;
//	private String password;
//	private String email;
//	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
//	@JoinTable(name="admin_roles",joinColumns =@JoinColumn (name="admin_id"),inverseJoinColumns =@JoinColumn(name="role_id"))
//	
//	private Set<Role> role=new HashSet<>();
//	
//
//}
