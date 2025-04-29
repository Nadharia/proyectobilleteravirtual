package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.mockito.Mockito.any;

import com.example.demo.dtos.RequestRegister;
import com.example.demo.dtos.ResponseAuth;
import com.example.demo.entitys.Rol;
import com.example.demo.entitys.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.AuthService;

public class TestAuthService {
	@Mock
	private UsuarioRepository usuarioRepository;
	
	 @Mock
	 private BCryptPasswordEncoder encoder;

	 @Mock
	 private JwtUtil jwtUtil;

	 @InjectMocks
	 private AuthService authService;
	 
	 public TestAuthService() {
	        MockitoAnnotations.openMocks(this); // Necesario para inicializar los mocks
	    }
	 
	 @Test
	 void register_DeberiaRegistrarYDevolverToken() {
		 RequestRegister request=new RequestRegister();
		 
		 request.setUsuario("juan123");
	        request.setPassword("1234");
	        request.setEmail("juan@example.com");
	        request.setRol(Rol.ADMIN);
	        
	        
	       when(usuarioRepository.findByUsuario("juan123")).thenReturn(Optional.empty());
	       when(encoder.encode("1234")).thenReturn("hashed_1234");
	       when(jwtUtil.generateToken("juan123","ADMIN")).thenReturn("mocked_token");
	       
	       
	       ResponseAuth response=authService.register(request);
	       
	       assertEquals("mocked_token", response.getToken());
	        verify(usuarioRepository).save(any(Usuario.class));
	       
	       
	 }

}
