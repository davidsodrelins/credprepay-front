package com.credprepay.apirest.resources;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.credprepay.apirest.models.Cartao;
import com.credprepay.apirest.repository.CartaoServices;
import com.credprepay.apirest.utils.CartaoReport;
import com.credprepay.apirest.utils.CartaoUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping (value="/apicredprepay")

@Api(value="API Rest Cartão Crédito Pré-pago")

@CrossOrigin(origins="*")

public class CartaoResource {

	@Autowired
	CartaoServices cartaoRepository;

	@GetMapping("/cartoes")
	@ApiOperation(value="Retorna a lista de cartões")
	public List<Cartao> listaCartoes(){
		return cartaoRepository.findAll();
	}



	@GetMapping("/cartao/{id}")
	@ApiOperation(value="Retorna um cartão")
	public Cartao buscar(@PathVariable(value="id") long id) throws JsonParseException, JsonMappingException, IOException, CredentialNotFoundException {
		Cartao cartao = cartaoRepository.findById(id);

		if (cartao == null) {
			throw new CredentialNotFoundException("Cartão não cadastrado");
		}
		return cartao;
	}
	

	@PostMapping("/cartao")
	@ApiOperation(value="Gera e salva um Cartãos")

	public CartaoReport salvaCartao(@RequestBody  Cartao cartao ) throws CredentialNotFoundException {

		CartaoReport credPrePay  = CartaoUtil.EmitirCartao(cartao);
		
		if(cartaoRepository.save(cartao)==null) {
			throw new  CredentialNotFoundException("Erro durante o cadastro");
		}else {
			return credPrePay;
		}
	}




	@DeleteMapping("/cartao")
	@ApiOperation(value="Deleta um cartãos")
	public void deletaCartao(@RequestBody Cartao cartao) {
		cartaoRepository.delete(cartao);
	}

	@PutMapping("/cartao")
	@ApiOperation(value="Atualiza um cartão")
	public Cartao editaCartao(@RequestBody Cartao cartao) {
		return cartaoRepository.save(cartao);
	}

}
