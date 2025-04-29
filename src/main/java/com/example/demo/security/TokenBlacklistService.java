package com.example.demo.security;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.TokenInvalido;
import com.example.demo.repository.TokenInvalidoRepository;

@Service
public class TokenBlacklistService {
	private final TokenInvalidoRepository repository;

	public TokenBlacklistService(TokenInvalidoRepository repository) {
		
		this.repository = repository;
	}
	
	
	public void agregarToken(String token,LocalDateTime fechaExpiracion) {
		TokenInvalido tokenInvalido=new TokenInvalido();
		tokenInvalido.setToken(token);
		tokenInvalido.setFechaExpiracion(fechaExpiracion);
		repository.save(tokenInvalido);
	}
	
	public boolean estaEnBlacklist(String token) {
        return repository.existsByToken(token);
    }

    @Scheduled(cron = "0 0 * * * *") 
    public void limpiarExpirados() {
        repository.deleteAll(repository.findAll().stream()
            .filter(t -> t.getFechaExpiracion().isBefore(LocalDateTime.now()))
            .toList());
    }
	
	

}
