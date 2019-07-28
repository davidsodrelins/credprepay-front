package com.exceptions;

/*
 * Classe criada para pegar todas as excessões que ocorressem e armazenar no banco de dados.
 * Essa funcionalidade não foi implementada.
 */

public class CredPrePayException extends RuntimeException {
	
	private static final long serialVersionUID = -1402677565107062800L;

	public CredPrePayException(String mensagem) {
		super(mensagem);
	}

	public CredPrePayException(String mensagem, Throwable erro) {
		super(mensagem, erro);
	}
}
