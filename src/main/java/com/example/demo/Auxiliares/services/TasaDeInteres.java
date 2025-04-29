package com.example.demo.Auxiliares.services;

import java.math.BigDecimal;

import java.util.List;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entitys.Cuenta;
import com.example.demo.repository.CuentaRepository;

import jakarta.transaction.Transactional;

@Component
public class TasaDeInteres {

	private CuentaRepository cuentaRepository;
	private static final BigDecimal tasaInteres = new BigDecimal("0.03");
	
	
	public TasaDeInteres(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }
	
	@Transactional
	@Scheduled(cron = "0 0 10 * * *", zone = "America/Argentina/Buenos_Aires")
	public void implementacionInteres() {
		List<Cuenta> cuentas=cuentaRepository.findAll();
		
		for (Cuenta cuenta:cuentas) {
			BigDecimal saldo = BigDecimal.valueOf(cuenta.getSaldo()); 
			if (saldo.compareTo(BigDecimal.ZERO)>0) {
				BigDecimal interes=saldo.multiply(tasaInteres);
				cuenta.setSaldo(saldo.add(interes).doubleValue());
				cuentaRepository.save(cuenta);
			}
			
		}
		
		
		
	}
}
