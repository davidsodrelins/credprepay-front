package com.credprepay.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credprepay.apirest.models.Cartao;

public interface CartaoServices extends JpaRepository<Cartao, Long> {
	//busca cartão por ID
	Cartao findById(long id);
	
	//busca cartão por numero
	boolean existsByNumero(String numero);
	
	//busca cartão por numero
	Cartao findByNumero(String numero);

}
