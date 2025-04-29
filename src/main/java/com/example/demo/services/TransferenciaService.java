package com.example.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.demo.Exceptions.TransferenciaException;
import com.example.demo.dtos.ListaMovimientosDTO;
import com.example.demo.dtos.MovimientoUsuarioDTO;
import com.example.demo.dtos.OrigenTransferenciaDTO;
import com.example.demo.dtos.TransferenciaResponse;
import com.example.demo.entitys.Cuenta;
import com.example.demo.entitys.Movimiento;
import com.example.demo.entitys.Transferencia;

import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.MovimientoRepository;
import com.example.demo.repository.TransferenciaRepository;
import com.example.demo.services.interfaces.ITransferenciaService;

@Service
public class TransferenciaService implements ITransferenciaService {
	private TransferenciaRepository transferenciaRepository;
	private CuentaRepository cuentaRepository;
	private MovimientoRepository movimientoRepository;
	private ComprobanteEmailService comprobanteEmailService;

	public TransferenciaService(ComprobanteEmailService comprobanteEmailService,
			TransferenciaRepository transferenciaRepository, CuentaRepository cuentaRepository,
			MovimientoRepository movimientoRepository) {

		this.transferenciaRepository = transferenciaRepository;
		this.cuentaRepository = cuentaRepository;
		this.movimientoRepository = movimientoRepository;
		this.comprobanteEmailService = comprobanteEmailService;
	}

	public TransferenciaResponse realizarTransferencia(OrigenTransferenciaDTO oTransferenciaDTO) {
		if (oTransferenciaDTO == null) {
			throw new TransferenciaException("El DTO de transferencia es nulo");
		}

		Optional<Cuenta> cuentaDestinoOptional = cuentaRepository
				.findByAlias(oTransferenciaDTO.getDestino().getAlias());
		Optional<Cuenta> cuentaOrigenOptional = cuentaRepository.findByAlias(oTransferenciaDTO.getOrigen().getAlias());

		if (cuentaDestinoOptional.isPresent()) {
			Cuenta cuentaDestino = cuentaDestinoOptional.get();
			Cuenta cuentaOrigen = cuentaOrigenOptional.get();
			if (oTransferenciaDTO.getOrigen().getSaldo() > oTransferenciaDTO.getMonto()) {
				Double monto = oTransferenciaDTO.getMonto();

				cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
				cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

				cuentaRepository.save(cuentaDestino);
				cuentaRepository.save(cuentaOrigen);

				TransferenciaResponse comprobante = new TransferenciaResponse("APROBADO", monto, LocalDateTime.now(),
						cuentaDestino);
				Transferencia transferencia = new Transferencia(monto, LocalDate.now(), cuentaOrigen, cuentaDestino);

				transferenciaRepository.save(transferencia);
				comprobanteEmailService.recibirDatos(cuentaDestino.getUsuario().getEmail(),
						cuentaOrigen.getUsuario().getEmail(), monto);
				return comprobante;

			} else {
				throw new TransferenciaException("el monto no suficiente");
			}

		} else {
			throw new TransferenciaException("no existe la cuenta de destino,revise el alias");
		}

	}

	public ListaMovimientosDTO obtenerMovimientos(MovimientoUsuarioDTO moDto) {
		Optional<Cuenta> cuenta = cuentaRepository.findByAlias(moDto.getAlias());

		if (cuenta.isEmpty()) {
			throw new TransferenciaException("La cuenta con el alias " + cuenta.get().getAlias() + " no existe");
		}

		Cuenta cuentaMovimiento = cuenta.get();

		List<Movimiento> movimientos = movimientoRepository.findByCuenta_id(cuentaMovimiento.getId());
		int cantidad = movimientoRepository.countByCuenta_Id(cuentaMovimiento.getId());

		if (movimientos.isEmpty()) {
			throw new TransferenciaException("No tienes movimientos en tu cuenta");
		}
		return new ListaMovimientosDTO(cantidad, movimientos);
	}

}
