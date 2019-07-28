package com.credprepay.apirest.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="transacao")


/*
A classe Transacao tem por objetivo criar um modelo que será avaliado quando o usuário
submeter uma nova compra, nela estão os dados enviados pelo usuário no momento da solicitação
da compra. Essas transações são gravas no banco de dados. */
public class Transacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long id;
	
	@NotBlank(message="{estabelecimento.not.blank}")
	private String estabelecimento;
	
	@NotBlank(message="{valor.compra.not.blank}")
	private double valorCompra;
	
	@NotBlank(message="{cvv.do.cartao.not.blank}")
	private String cvv;
	
	@NotBlank(message="{validade.do.cartao.not.blank}")
	private String validade;
	
	@NotBlank(message="{numero.do.cartao.not.blank}")
	private String numeroCartao;
	
	@NotBlank(message="{senha.do.cartao.not.blank}")
	private String senha;
	
	private boolean autorizado;
	
	private String status="";

	public Transacao() {
	}


	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if(this.status=="") {
			this.status = status;
		}else
			this.status += " # " +status;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}	

}
