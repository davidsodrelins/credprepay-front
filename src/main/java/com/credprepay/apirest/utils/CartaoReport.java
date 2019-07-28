package com.credprepay.apirest.utils;

import com.credprepay.apirest.models.Cartao;

public class CartaoReport extends Cartao {

	
	/**
	 * Esta classe é responsável por retornar os dados do cartão gerado, herda os atributos e os
	 * metodos da classe Cartao, incluindo a senha e o cvv que não são armazenados no banco de dados.
	 */
	private static final long serialVersionUID = 1L;
	private String cvv;	
	
	public CartaoReport() {
		super();
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	

}
