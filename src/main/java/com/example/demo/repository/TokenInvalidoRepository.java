package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entitys.TokenInvalido;

public interface TokenInvalidoRepository extends JpaRepository<TokenInvalido, Long> {
	boolean existsByToken(String token);
}
