package com.example.demo.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entitys.Cuenta;


public interface CuentaRepository extends JpaRepository<Cuenta, Long>{
	Optional<Cuenta> findByAlias(String alias);

}
