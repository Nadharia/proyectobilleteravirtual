package com.example.demo.dtos;

import java.time.LocalDateTime;

import com.example.demo.entitys.Cuenta;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransferenciaResponse {
	
	private String mensaje;
	private Double monto;
	private LocalDateTime fecha;
	private Cuenta destino;

	
	public TransferenciaResponse(String string, Double monto2, LocalDateTime now, Cuenta cuentaDestino) {
		// TODO Auto-generated constructor stub
	}


	
}
