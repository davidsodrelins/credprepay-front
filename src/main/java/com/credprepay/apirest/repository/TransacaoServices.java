package com.credprepay.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credprepay.apirest.models.Transacao;

public interface TransacaoServices extends JpaRepository<Transacao, Long> {
	//busca transaca por id;
	Transacao findById(long id);

}
