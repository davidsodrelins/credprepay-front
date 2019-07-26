package com.credprepay.apirest.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import com.credprepay.apirest.models.Cartao;


public class CartaoUtil {

	public static boolean ValidaCCV (Cartao cartao, Long codigoCvv) {

		final Integer bin = Integer.parseInt(cartao.getNumero().substring(0, 6 ));	
		String numCartao = cartao.getNumero();
		Integer dv = Integer.parseInt(cartao.getNumero().substring(12, 13));
		Integer sb = somatorio(bin);
		Integer modSb = sb%10;
		Integer divSb = sb/10;
		Integer checkSum = ((((divSb*modSb)+dv))*sb);
		Integer mesValidade = Integer.parseInt(cartao.getValidade().substring(0, 2));
		Integer anoValidade = Integer.parseInt(cartao.getValidade().substring(3, 5));
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

		final Integer bin = 199108;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		System.out.println("Teste no Cadastro - CVV DO CARTAO GERADO " + cartaoReport.getCvv());
		//		System.out.println("CVV Calculado " + cvv);
		//		
		//		System.out.println(CartaoUtil.ValidaCCV(cartao, Long.parseLong(cartaoReport.getCvv()))?"CVV Válido":"CVV Inválido");
		return cartaoReport;	
	}




	public static String GerarSenha () {
		return  GeradorRandomico(4, false, true);
	}


	public static boolean ValidaCartao (Cartao cartao) {
		return true;
	}

	public static boolean ValidaData (Cartao cartao) {
		return true;
	}



	public static String GeradorRandomico(int tamanho, boolean letras, boolean numeros) {
		String generatedString = RandomStringUtils.random(tamanho, letras, numeros);
		return generatedString;
	}
}
