package com.example.demo.entitys;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Double saldo=0.0;
@Column(name="alias")
private String alias;

@OneToOne
@JoinColumn(name="usuario_id")
@JsonManagedReference
private Usuario usuario;

@OneToMany(mappedBy = "cuenta",cascade=CascadeType.ALL)
private List<Movimiento> movimientos;
}
