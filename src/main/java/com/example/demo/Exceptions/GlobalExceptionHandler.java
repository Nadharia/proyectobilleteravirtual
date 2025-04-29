package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dtos.TransferenciaResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(TransferenciaException.class)
    public ResponseEntity<TransferenciaResponse> handleTransferenciaException(TransferenciaException ex) {
        TransferenciaResponse response = new TransferenciaResponse(null, null, null, null);
        response.setMensaje(ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
	
	
	

}
