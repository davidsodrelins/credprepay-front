package com.credprepay.apirest.utils;

import com.credprepay.apirest.models.Cartao;

public class CartaoReport extends Cartao {

	
	/**
	 * 
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
