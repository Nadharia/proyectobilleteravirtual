package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entitys.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia,Long> {
	
}
