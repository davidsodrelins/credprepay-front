package com.credprepay.apirest.utils;

import com.credprepay.apirest.models.Cartao;
import com.credprepay.apirest.models.Transacao;

public class TransacaoUtil {
	
	
	//Checagem do saldo do cartão
	public static boolean SaldoSuficiente(Transacao transacao, Cartao cartao) {
		if ((cartao.getSaldo()) >= (transacao.getValorCompra())) {
			return true;
		}else {
			return false;
		}
	}
	
	//Gravação da transação com sucesso ou com os erros possíveis
	public static Transacao autorizarTransacao (Transacao transacao, Cartao cartao){

		boolean cvvValido = CartaoUtil.ValidaCCV(transacao);
		boolean dataValida = CartaoUtil.validaData(transacao, cartao);
		boolean saldoSuficiente = TransacaoUtil.SaldoSuficiente(transacao, cartao);
		boolean senhaValida = CartaoUtil.validaSenha(transacao, cartao);

		if (cvvValido && dataValida && saldoSuficiente && senhaValida) {
			transacao.setAutorizado(true);
			cartao.atualizarSaldo(transacao.getValorCompra());
			transacao.setStatus("COD 00 - Transação realizada com sucesso seu novo saldo é:" + cartao.getSaldo());
		}else{
			if(!cvvValido) {
				transacao.setStatus("COD 01 - Código CVV inválido.");
			}
			if(!dataValida) {
				transacao.setStatus("COD 02 - Data inválida seu cartão expirado.");
			}
			if(!saldoSuficiente){
				transacao.setStatus("COD 03 - Saldo Insuficiente");
			}
			if(!senhaValida) {
				transacao.setStatus("COD 04 - Senha inválida");
			}
		}		
		return transacao ;
	}
}