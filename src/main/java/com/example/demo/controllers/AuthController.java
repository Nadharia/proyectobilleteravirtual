package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.RequestLogin;
import com.example.demo.dtos.RequestRegister;
import com.example.demo.dtos.ResponseAuth;
import com.example.demo.services.AuthService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
		return authService.logout(authHeader);
	}

	@PostMapping("/register")
	public String name(@RequestBody RequestRegister request) {

		authService.register(request);
		String respuesta200 = "Usuario Registrado con exito";
		return respuesta200;
	}

	@PostMapping("/login")
	public ResponseAuth login(@RequestBody RequestLogin request) {
		return authService.login(request);
	}

}
