package com.example.demo.dtos;

import com.example.demo.entitys.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRegister {
	
	private String email;
	private String usuario;
	private String password;
	private Rol rol;

}
