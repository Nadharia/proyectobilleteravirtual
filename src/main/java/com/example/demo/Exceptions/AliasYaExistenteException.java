package com.example.demo.Exceptions;

public class AliasYaExistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	 public AliasYaExistenteException(String message) {
		 super(message);
	 }
}
