package com.credprepay.apirest.resources;

/*
 * Nesta classe está todas as rotas da API. 
 * 
 * Consultas e Emissao de Cartões e Transações de Compra;
 */




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
import org.springframework.web.bind.annotation.RestController;
import com.credprepay.apirest.models.Cartao;
import com.credprepay.apirest.models.Transacao;
import com.credprepay.apirest.repository.CartaoServices;
import com.credprepay.apirest.repository.TransacaoServices;
import com.credprepay.apirest.utils.CartaoReport;
import com.credprepay.apirest.utils.CartaoUtil;
import com.credprepay.apirest.utils.TransacaoUtil;
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
	@Autowired
	TransacaoServices transacaoRepository;

	//Retorna uma lista de cartoes cadastrados
	@GetMapping("/cartoes")
	@ApiOperation(value="Retornar a lista de cartões")
	public List<Cartao> listaCartoes(){
		return cartaoRepository.findAll();
	}

	//Retrona um determinado cartão a partir do id
	@GetMapping("/cartao/{id}")
	@ApiOperation(value="Retornar um cartão")
	public Cartao buscar(@PathVariable(value="id") long id) throws JsonParseException, JsonMappingException, IOException, CredentialNotFoundException {
		Cartao cartao = cartaoRepository.findById(id);
		if (cartao == null) {
			throw new CredentialNotFoundException("Cartão não cadastrado");
		}
		return cartao;
	}
	
	//Retrona um determinado cartão a partir do numero do cartão
	@GetMapping("/cartao/{numero}")
	@ApiOperation(value="Retornar um cartão a partir do numero indicado")
	public Cartao buscar(@PathVariable(value="numero") String numero) throws JsonParseException, JsonMappingException, IOException, CredentialNotFoundException {

		Cartao cartao = cartaoRepository.findByNumero(numero);
		if (cartao == null) {
			throw new CredentialNotFoundException("Cartão não cadastrado");
		}
		return cartao;
	}

	@PostMapping("/cartao")
	@ApiOperation(value="Gerar e salvar um Cartãos")

	public CartaoReport salvaCartao(@RequestBody  Cartao cartao ) throws CredentialNotFoundException {

		CartaoReport credPrePay  = CartaoUtil.EmitirCartao(cartao);

		if(cartaoRepository.save(cartao)==null) {
			throw new  CredentialNotFoundException("Erro durante o cadastro");
		}else {
			return credPrePay;
		}
	}

	//remove um cartão (deve ser passado o objeto a ser removido)
	@DeleteMapping("/cartao")
	@ApiOperation(value="Deleta um cartãos")
	public void deletaCartao(@RequestBody Cartao cartao) {
		cartaoRepository.delete(cartao);
	}
	
	//edita um cartão (deve ser passado o objeto a ser editado)
	@PutMapping("/cartao")
	@ApiOperation(value="Atualiza um cartão")
	public Cartao editaCartao(@RequestBody Cartao cartao) {
		return cartaoRepository.save(cartao);
	}

	//Retorna todas as transações salvas
	@GetMapping("/transacoes")
	@ApiOperation(value="Retornar a lista de autorizações")

	public List<Transacao> listaTransacoes(){
		return transacaoRepository.findAll();
	}

	//busca autorização a partir de um id
	@GetMapping("/transacao/{id}")
	@ApiOperation(value="Retornar uma transação")
	public Transacao buscarTransaco(@PathVariable(value="id") long id) throws JsonParseException, JsonMappingException, IOException, CredentialNotFoundException {
		Transacao compra = transacaoRepository.findById(id);
		if (compra == null) {
			throw new CredentialNotFoundException("Cartão não cadastrado");
		}
		return compra;
	}

	//Autoriza ou não uma transação de compra
	@PostMapping("/transacao")
	@ApiOperation(value="Autorização de Compra")
	public Transacao transacaoCartao(@RequestBody  Transacao transacao ){

		Cartao buscaCartao = cartaoRepository.findByNumero(transacao.getNumeroCartao());
		
		//caso o cartão não exista, não faz sentido ir fazer as outras validações.
		if(!cartaoRepository.existsByNumero(transacao.getNumeroCartao())) {
			transacao.setStatus("COD 99 - Este cartão não existe");
			return transacao;
		} else {
			if(TransacaoUtil.autorizarTransacao(transacao, buscaCartao)!=null) {
				cartaoRepository.save(buscaCartao);
				transacaoRepository.save(transacao);
				return transacao;
			} else {
				return transacao;
			}
		}
	}

}
