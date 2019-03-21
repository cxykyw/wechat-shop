package com.kyw.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kyw.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity extends BaseEntity {

	@JsonProperty(value = "userName")
	private String userName;
	
	@JsonProperty(value = "password")
	private String password;
	
	@JsonProperty(value = "phone")
	private String phone;
	
	@JsonProperty(value = "email")
	private String email;
	
	@JsonProperty(value = "openid")
	private String openId;
}
