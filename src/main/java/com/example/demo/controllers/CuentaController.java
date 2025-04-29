package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ListaMovimientosDTO;
import com.example.demo.dtos.ModificarAliasDTO;
import com.example.demo.dtos.MovimientoUsuarioDTO;
import com.example.demo.dtos.OrigenTransferenciaDTO;
import com.example.demo.dtos.TransferenciaResponse;
import com.example.demo.entitys.Cuenta;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.services.CuentaService;
import com.example.demo.services.TransferenciaService;

@RestController
@RequestMapping
public class CuentaController {

	@Autowired
	private CuentaRepository cuentaRepository;

	@Autowired
	private TransferenciaService transferenciaService;
	
	@Autowired
	private CuentaService cuentaService;

	@GetMapping("/dashboard/{id}")
	public ResponseEntity<Cuenta> obtenerDatosCuenta(@PathVariable Long id) {
		return cuentaRepository.findById(id).map(cuenta -> ResponseEntity.ok(cuenta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/realizartransferencia")
	public TransferenciaResponse realizarTransferencia(OrigenTransferenciaDTO origenTransferenciaDTO) {
		return transferenciaService.realizarTransferencia(origenTransferenciaDTO);
	}

	@GetMapping("/obtenermovimientos")
	public ListaMovimientosDTO obtenerMovimientos(MovimientoUsuarioDTO mDto) {
		return transferenciaService.obtenerMovimientos(mDto);
	}
	
	@PostMapping("/modificaralias")
	public ResponseEntity<String> modificaralias(ModificarAliasDTO modificarAliasDTO){
		return cuentaService.modificarAlias(modificarAliasDTO);
	}
}
