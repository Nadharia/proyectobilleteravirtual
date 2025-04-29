package com.example.demo.dtos;

import java.util.List;

import com.example.demo.entitys.Movimiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaMovimientosDTO {
	private int cantidad;
private List<Movimiento> movimientos;
}
