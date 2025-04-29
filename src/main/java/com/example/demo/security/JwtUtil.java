package com.example.demo.security;



import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;



@Component
public class JwtUtil {

	
	private final Key keySecret = Keys.hmacShaKeyFor("clave-super-secreta-de-256-bits123456789".getBytes());
	
	public String generateToken(String usuario,String rol) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("rol", rol);
		
		
		 String jwt=Jwts.builder()
				.subject(usuario)
				.signWith(keySecret)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60*60*10))		
				.claims(claims)
				.compact();
		 	return jwt;
	}
	
	public Claims extraerClaims(String token) {
		return Jwts.parser()
		.verifyWith((SecretKey) keySecret)
		.build()
		.parseSignedClaims(token)
		.getPayload();
				
	}
	
	
	public String obtenerUsuario(String token) {
		return extraerClaims(token).getSubject();
	}
	
	public String obtenerRol(String token) {
		return (String)	extraerClaims(token).get("rol");
	}
	
	public boolean verificarValidez(String token,String usuario) {
		return usuario.equals(obtenerUsuario(token)) && !tokenExpirado(token);

	}
	
	private boolean tokenExpirado(String token) {
		return extraerClaims(token).getExpiration().before(new Date());
	}
	
	public LocalDateTime obtenerExpiracion(String token) {
        Date expirationDate = extraerClaims(token).getExpiration();
        return expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}
