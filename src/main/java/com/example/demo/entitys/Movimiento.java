package com.example.demo.entitys;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {
@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
private Long id;

private Double monto;
private String descripcion;
private LocalDateTime fecha;

@Enumerated(EnumType.STRING)
private TipoMovimiento tipo;


@ManyToOne
@JoinColumn(name="cuenta_id")
private Cuenta cuenta;

}
