package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.Usuario;
import com.example.demo.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/usuarios")
	public List<Usuario> obtenerTodosLosUsuarios(){
		return usuarioRepository.findAll();
	}
}
