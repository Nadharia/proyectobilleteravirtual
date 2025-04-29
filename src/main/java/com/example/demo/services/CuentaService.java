package com.example.demo.services;

import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.AliasYaExistenteException;
import com.example.demo.Exceptions.ModificarAliasException;
import com.example.demo.dtos.ModificarAliasDTO;
import com.example.demo.entitys.Cuenta;
import com.example.demo.repository.CuentaRepository;

@Service
public class CuentaService {

	
	private CuentaRepository cuentaRepository;
	
	public CuentaService(CuentaRepository cuentaRepository) {
		
		this.cuentaRepository = cuentaRepository;
	}
	
	
public ResponseEntity<String> modificarAlias(ModificarAliasDTO modificarAliasDTO){
	Optional<Cuenta> cuenta=cuentaRepository.findByAlias(modificarAliasDTO.getAliasviejo());
	Optional<Cuenta> cuentanuevo=cuentaRepository.findByAlias(modificarAliasDTO.getAliasnuevo());	
	
	
	
	if (cuenta.isEmpty()) {
		throw new ModificarAliasException("Alias no encontrado");
	}
	
	if (cuentanuevo.isPresent()) {
		throw new AliasYaExistenteException("El alias que quieres usar ya esta en uso");
	}
	Cuenta cuentaPresente=cuenta.get();
	cuentaPresente.setAlias(modificarAliasDTO.getAliasnuevo());
	cuentaRepository.save(cuentaPresente);
	return ResponseEntity.ok("Alias cambiado");



	
}

}
