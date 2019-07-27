package com.credprepay.apirest.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import com.credprepay.apirest.models.Cartao;
import com.credprepay.apirest.models.Transacao;


public class CartaoUtil {

	public static boolean ValidaCCV (Transacao transacao) {
		Long codigoCvv = Long.parseLong(transacao.getCvv());

		Integer bin = Integer.parseInt(transacao.getNumeroCartao().substring(0, 6 ));	

		String numCartao = transacao.getNumeroCartao();
		Integer dv = Integer.parseInt(transacao.getNumeroCartao().substring(12, 13));
		Integer sb = somatorio(bin);
		Integer modSb = sb%10;
		Integer divSb = sb/10;
		Integer checkSum = ((((divSb*modSb)+dv))*sb);
		Integer mesValidade = Integer.parseInt(transacao.getValidade().substring(0, 2));
		Integer anoValidade = Integer.parseInt(transacao.getValidade().substring(3, 5));
		Long cvv = ((somatorio(Long.parseLong(numCartao)) * anoValidade * mesValidade * checkSum) / ((anoValidade *	mesValidade * mesValidade)))/10 ;

		return (Long.compare(cvv,codigoCvv)==0)?true:false;
	}

	public static Integer somatorio (Integer numero) {
		Integer valor = 0;
		while(numero>0) { 
			valor += (numero % 10);
			numero /= 10;
		}
		return valor;
	}

	public static Long somatorio (Long numero) {
		Long valor = 0L;
		while(numero>0L) {
			valor += (numero % 10L);
			numero /= 10L;
		}
		return valor;
	}

	public static Integer produtoDigitos (Integer numero) {
		Integer valor = 0;
		while(numero>0) {
			valor *= (numero % 10);
			numero /= 10;
		}
		return valor;
	}

	public static CartaoReport EmitirCartao (Cartao cartao){

		CartaoReport cartaoReport = new CartaoReport();

		Integer bin = 199108;
		Integer dv = Integer.parseInt(GeradorRandomico(1, false, true));
		Integer sb = somatorio(bin);
		Integer modSb = sb%10;
		Integer divSb = sb/10;
		Integer checkSum = ((((divSb*modSb)+dv))*sb);
		Integer conta = Integer.parseInt(GeradorRandomico(6, false, true));
		LocalDate data = LocalDate.now();
		Integer mesValidade = Integer.parseInt(data.format(DateTimeFormatter.ofPattern("MM")));
		Integer anoValidade = Integer.parseInt(data.format(DateTimeFormatter.ofPattern("yy")))+2;
		String numCartao = bin.toString()+conta.toString()+dv.toString()+checkSum.toString();
		String senha = GerarSenha();


		Long cvv = ((somatorio(Long.parseLong(numCartao)) * anoValidade * mesValidade * checkSum) / ((anoValidade *	mesValidade * mesValidade)))/10 ;

		cartaoReport.setTitular(cartao.getTitular());
		cartaoReport.setNumero(numCartao);
		cartaoReport.setCvv(cvv.toString());

		if (mesValidade < 10) {
			cartaoReport.setValidade("0" + mesValidade+ "/" + anoValidade);
		}else {
			cartaoReport.setValidade(mesValidade+"/"+anoValidade);
		}		
		cartaoReport.setSenha(senha);
		cartaoReport.setSaldo(cartao.getSaldo());
		cartaoReport.setId(cartao.getId());


		cartao.setNumero(numCartao);

		if(mesValidade < 10) {
			cartao.setValidade("0" + mesValidade+ "/" + anoValidade);
		}else {
			cartao.setValidade(mesValidade+"/"+anoValidade);
		}

		try {
			cartao.setSenha(Security.crypto(senha));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		return cartaoReport;	
	}

	public static String GerarSenha () {
		return  GeradorRandomico(4, false, true);
	}

	public static String GeradorRandomico(int tamanho, boolean letras, boolean numeros) {
		String generatedString = RandomStringUtils.random(tamanho, letras, numeros);
		return generatedString;
	}

	public static boolean validaData(Transacao transacao, Cartao cartao) {

		LocalDate data = LocalDate.now();
		Integer mesAtual = Integer.parseInt(data.format(DateTimeFormatter.ofPattern("MM")));
		Integer anoAtual = Integer.parseInt(data.format(DateTimeFormatter.ofPattern("yy")));

		Integer mesValidade = Integer.parseInt(transacao.getValidade().substring(0, 2));
		Integer anoValidade = Integer.parseInt(transacao.getValidade().substring(3, 5));

		String ValidadeCartao = cartao.getValidade();
		String ValidadeTransacao = transacao.getValidade();

		return (ValidadeCartao.equals(ValidadeTransacao) && (anoAtual<=anoValidade) && (mesAtual<=mesValidade))?true:false;
	}
	
	public static boolean validaSenha(Transacao transacao, Cartao busca){
		String hash = busca.getSenha();
		String senha = transacao.getSenha();
		
		try {
			senha = Security.crypto(senha);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		return hash.equals(senha)?true:false;
	}


}
