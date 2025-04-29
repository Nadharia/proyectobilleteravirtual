package com.example.demo.services.interfaces;

import com.example.demo.dtos.ListaMovimientosDTO;
import com.example.demo.dtos.MovimientoUsuarioDTO;
import com.example.demo.dtos.OrigenTransferenciaDTO;
import com.example.demo.dtos.TransferenciaResponse;

public interface ITransferenciaService {
public TransferenciaResponse realizarTransferencia(OrigenTransferenciaDTO origenTransferenciaDTO);
public ListaMovimientosDTO obtenerMovimientos(MovimientoUsuarioDTO movimientoUsuarioDTO);
}
