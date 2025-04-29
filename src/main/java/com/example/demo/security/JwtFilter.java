package com.example.demo.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entitys.Usuario;
import com.example.demo.repository.UsuarioRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final TokenBlacklistService blacklistService;
    
    

    public JwtFilter(JwtUtil jwtUtil, UsuarioRepository usuarioRepository, TokenBlacklistService blacklistService) {
		
		this.jwtUtil = jwtUtil;
		this.usuarioRepository = usuarioRepository;
		this.blacklistService = blacklistService;
	}



	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String token = authHeader.substring(7);
        final String username = jwtUtil.obtenerUsuario(token);
        
        if(blacklistService.estaEnBlacklist(token)) {
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	return;
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Usuario> userOptional = usuarioRepository.findByUsuario(username);
            
            if (userOptional.isPresent() && jwtUtil.verificarValidez(token, userOptional.get().getUsuario())) {
                Usuario user = userOptional.get();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol().name()))
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
