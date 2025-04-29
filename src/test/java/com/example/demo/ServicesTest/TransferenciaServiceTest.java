package com.example.demo.ServicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dtos.OrigenTransferenciaDTO;
import com.example.demo.dtos.TransferenciaResponse;
import com.example.demo.entitys.Cuenta;
import com.example.demo.entitys.Transferencia;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.TransferenciaRepository;
import com.example.demo.services.TransferenciaService;

@ExtendWith(MockitoExtension.class)  
public class TransferenciaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;  

    @Mock
    private TransferenciaRepository transferenciaRepository; 

   
    private TransferenciaService transferenciaService;

    @Test
    void realizarTransferencia_CasoExitoso() {
        
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setAlias("cuenta-origen");
        cuentaOrigen.setSaldo(1000.00);
        
        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setAlias("cuenta-destino");
        cuentaDestino.setSaldo(500.00);
        
        
        OrigenTransferenciaDTO dto = new OrigenTransferenciaDTO();
        dto.setMonto(300.0);
        dto.setOrigen(cuentaOrigen);
        dto.setDestino(cuentaDestino);
        
        
        when(cuentaRepository.findByAlias("cuenta-origen")).thenReturn(Optional.of(cuentaOrigen));
        when(cuentaRepository.findByAlias("cuenta-destino")).thenReturn(Optional.of(cuentaDestino));
        
        
        transferenciaService = new TransferenciaService(null, transferenciaRepository, cuentaRepository, null);
        
        
        TransferenciaResponse response = transferenciaService.realizarTransferencia(dto);
        
        
        assertEquals("APROBADO", response.getMensaje());
        assertEquals(300, response.getMonto());
        assertEquals("cuenta-destino", response.getDestino().getAlias());
        assertNotNull(response.getFecha());
        
       
        verify(cuentaRepository, times(1)).save(cuentaOrigen);
        verify(cuentaRepository, times(1)).save(cuentaDestino);
        verify(transferenciaRepository, times(1)).save(any(Transferencia.class));
    }

}
