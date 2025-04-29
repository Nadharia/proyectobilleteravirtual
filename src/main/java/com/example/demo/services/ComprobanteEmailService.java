package com.example.demo.services;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteEmailService {
 private final JavaMailSender mailSender;

public ComprobanteEmailService(JavaMailSender mailSender) {
	
	this.mailSender = mailSender;
}

public void recibirDatos(String para,String origen,Double monto) {
	String mensajecuerpo="Has enviado una transferencia a "+para+"por el monto de "+monto+" "+LocalDateTime.now();
	String asunto="Comprobante de transferencia";
	enviarCorreo(para,asunto, mensajecuerpo, origen);
}
 
public void enviarCorreo(String para,String asunto,String cuerpo,String origen) {
	SimpleMailMessage mensaje=new SimpleMailMessage();
	mensaje.setTo(para);
	mensaje.setSubject(asunto);
	mensaje.setText(cuerpo);
	mensaje.setFrom(origen);
	mailSender.send(mensaje);
}
 
}
