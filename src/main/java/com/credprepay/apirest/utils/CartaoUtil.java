package com.credprepay.apirest.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import com.credprepay.apirest.models.Cartao;
import com.credprepay.apirest.models.Transacao;

/*
 * Todas as rotinas de validação e geração de dados dos cartões são realziadas nesta classe.
 */
public class CartaoUtil {

	//Validação do CVV
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

	//realização de somatório de todos os dígitos de um número Integer
	public static Integer somatorio (Integer numero) {
		Integer valor = 0;
		while(numero>0) { 
			valor += (numero % 10);
			numero /= 10;
		}
		return valor;
	}

	//realização de somatório de todos os dígitos de um número Long, foi criado especialmente
	//para somar os numeros do cartão de crédito
	public static Long somatorio (Long numero) {
		Long valor = 0L;
		while(numero>0L) {
			valor += (numero % 10L);
			numero /= 10L;
		}
		return valor;
	}


	//Método responsável pela emissão do cartão.
	//o bin é 199108, pode ser alterado direto na função para gerar novos números
	//O numero do cartão é dividido da seguinte forma:
	//      BIN[000000]uma conta gerada[000000]dígito verificador gerado[0]checksum[000]
	//os calculos realziados são para geração de números de cartões válidos, todos os numeros 
	//gerados é gerado através do cálculo realizado neste método
	public static CartaoReport EmitirCartao (Cartao cartao){

		CartaoReport cartaoReport = new CartaoReport();
		
		Integer bin = 199108; //bin fixo
		Integer dv = Integer.parseInt(GeradorRandomico(1, false, true)); //digito verificadr gerado randomicamente
		Integer sb = somatorio(bin); //sb é a soma dos numeros do bin para criar uma constante
		Integer modSb = sb%10; //resto da divisão da constante por 10
		Integer divSb = sb/10; //divisão da constante por 10
		
		//o checksum é gerado a partir do cálculo abaixo:
		//  (((divSb*modSb)+dv)*sb) 
		//este resultado gera uma sequencia de 3 dígitos que formam os tres ultimos digitos do cartao
		Integer checkSum = ((((divSb*modSb)+dv))*sb); 
		//o numero da conta é uma sequencia de 6 dítigos gerada randoicamente
		Integer conta = Integer.parseInt(GeradorRandomico(6, false, true));
		
		//geração do mês e ano para gravação no cartão
		LocalDate data = LocalDate.now();
		Integer mesValidade = Integer.parseInt(data.format(DateTimeFormatter.ofPattern("MM")));
		Integer anoValidade = Integer.parseInt(data.format(DateTimeFormatter.ofPattern("yy")))+2;
		
		//criação do numero do cartão
		String numCartao = bin.toString()+conta.toString()+dv.toString()+checkSum.toString();
		
		//criação de uma senha de 4 dígitos
		String senha = GerarSenha();

		//geração do cvv do cartão também seguinto um cálculo matenática envolvendo os dados 
		// do cartão
		Long cvv = ((somatorio(Long.parseLong(numCartao)) * anoValidade * mesValidade * checkSum) / ((anoValidade *	mesValidade * mesValidade)))/10 ;

		//passagem dos valores para o objeto da classe CartaoReport para ser enviada ao front
		cartaoReport.setTitular(cartao.getTitular());
		cartaoReport.setNumero(numCartao);
		cartaoReport.setCvv(cvv.toString());

		//ajuste dos caracteres de data
		if (mesValidade < 10) {
			cartaoReport.setValidade("0" + mesValidade+ "/" + anoValidade);
		}else {
			cartaoReport.setValidade(mesValidade+"/"+anoValidade);
		}		
		
		
		cartaoReport.setSenha(senha);
		cartaoReport.setSaldo(cartao.getSaldo());
		cartaoReport.setId(cartao.getId());
		
		//numero atribuido ao cartão que será salvo na base de dados
		cartao.setNumero(numCartao);

		if(mesValidade < 10) {
			cartao.setValidade("0" + mesValidade+ "/" + anoValidade);
		}else {
			cartao.setValidade(mesValidade+"/"+anoValidade);
		}

		//geração do hash SHA-2 para criptogradar a senha e salvar na base de dados
		try {
			cartao.setSenha(Security.crypto(senha));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		return cartaoReport;	
	}

	//método gerador de senha
	public static String GerarSenha () {
		return  GeradorRandomico(4, false, true);
	}

	//método gerador randomico configurável
	public static String GeradorRandomico(int tamanho, boolean letras, boolean numeros) {
		String generatedString = RandomStringUtils.random(tamanho, letras, numeros);
		return generatedString;
	}

	//validação da data de validade do cartão
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
	
	//validação da senha informada na transação de compra
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
