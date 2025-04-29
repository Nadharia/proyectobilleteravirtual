package com.example.demo.entitys;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="usuario",unique=true,nullable =false)
	private String usuario;
	
	@Column(name="email",unique=true,nullable =false)
	private String email;
	
	
	@Column(name="password",nullable =false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@OneToOne(mappedBy = "usuario",cascade=CascadeType.ALL)
	@JsonBackReference
	private Cuenta cuenta;
}
