package com.credprepay.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credprepay.apirest.models.Cartao;

public interface CartaoServices extends JpaRepository<Cartao, Long> {
	
	Cartao findById(long id);
	boolean existsByNumero(String numero);
	Cartao findByNumero(String numero);

}
