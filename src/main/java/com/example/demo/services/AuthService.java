package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.http.HttpHeaders;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.Exceptions.AuthException;
import com.example.demo.dtos.RequestLogin;
import com.example.demo.dtos.RequestRegister;
import com.example.demo.dtos.ResponseAuth;
import com.example.demo.entitys.Cuenta;
import com.example.demo.entitys.Rol;
import com.example.demo.entitys.Usuario;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.TokenBlacklistService;


@Service

public class AuthService {
	
	
	private  UsuarioRepository usuarioRepository;
	private  CuentaRepository cuentaRepository;
	private TokenBlacklistService tokenBlacklistService;
	
	private  PasswordEncoder encoder;
	
	private  JwtUtil jwtUtil;
	
	 
	
	public AuthService(UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository,
			TokenBlacklistService tokenBlacklistService, PasswordEncoder encoder, JwtUtil jwtUtil) {
		
		this.usuarioRepository = usuarioRepository;
		this.cuentaRepository = cuentaRepository;
		this.tokenBlacklistService = tokenBlacklistService;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
	}

	public ResponseAuth register(RequestRegister request) {
		 if (request.getPassword() == null || request.getPassword().isEmpty()) {
		        throw new AuthException("La contraseña es nula");
		    }
		if (usuarioRepository.findByUsuario(request.getUsuario()).isPresent()) {
			throw new AuthException("El usuario ya está registrado");
		}
		Usuario requestUser=new Usuario();
		requestUser.setEmail(request.getEmail());
		requestUser.setUsuario(request.getUsuario());
		requestUser.setPassword(encoder.encode(request.getPassword()));
		requestUser.setRol(Rol.ADMIN);
		usuarioRepository.save(requestUser);
		
		Cuenta cuenta=new Cuenta();
		cuenta.setAlias(request.getUsuario());
		cuenta.setUsuario(requestUser);
		cuentaRepository.save(cuenta);
		
		
		String token = jwtUtil.generateToken(request.getUsuario(), request.getRol().name());
		return new ResponseAuth(token);
		
	}
	
	public ResponseAuth login(RequestLogin request) {
		
	Optional<Usuario> userOptional = usuarioRepository.findByUsuario(request.getUsuario());
	
	if (userOptional.isEmpty()) {
		throw new AuthException("El usuario no existe");
	}
			
		Usuario user=userOptional.get();
		
	if (!encoder.matches(request.getPassword(),user.getPassword())) {
		throw new AuthException("La contraseña es incorrecta");
	}
		
	String token=jwtUtil.generateToken(user.getUsuario(),Rol.ADMIN.name());
		
	return new ResponseAuth(token);
	
	
		
	}
	
	
	 
	    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            return ResponseEntity.badRequest().body("Token inválido");
	        }

	        String token = authHeader.substring(7);
	        LocalDateTime expiracion = jwtUtil.obtenerExpiracion(token);

	        tokenBlacklistService.agregarToken(token, expiracion);
	        return ResponseEntity.ok("Logout exitoso");
	    }

}
