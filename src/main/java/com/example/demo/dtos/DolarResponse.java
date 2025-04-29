package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DolarResponse {
private String casa;
private String moneda;
private String nombre;
private double compra;
private double venta;
private String fechaActualizacion;
}
