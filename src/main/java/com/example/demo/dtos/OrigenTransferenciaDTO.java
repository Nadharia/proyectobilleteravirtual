	package com.example.demo.dtos;
	
	import java.time.LocalDateTime;
	
	import com.example.demo.entitys.Cuenta;

	import lombok.Getter;
	import lombok.Setter;

	@Getter
	@Setter
	public class OrigenTransferenciaDTO {
		private Long id;
		private Double monto;
		private LocalDateTime fecha;
		private Cuenta origen;
		private Cuenta destino;
		
	}
