package com.example.demo.entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {
	
	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Double monto;
	private LocalDateTime fecha;
	
	
	@OneToOne
	@JoinColumn(name="origen_id")
	private Cuenta origen;
	
	@OneToOne
	@JoinColumn(name="destino_id")
	private Cuenta destino;
	
	public Transferencia(Double monto2, LocalDate now, Cuenta cuentaOrigen, Cuenta cuentaDestino) {
		// TODO Auto-generated constructor stub
	}

}
