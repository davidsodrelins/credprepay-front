package com.exceptions;



public class CredPrePayException extends RuntimeException {
	
	private static final long serialVersionUID = -1402677565107062800L;

	public CredPrePayException(String mensagem) {
		super(mensagem);
	}

	public CredPrePayException(String mensagem, Throwable erro) {
		super(mensagem, erro);
	}
}
