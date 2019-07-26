package com.credprepay.apirest.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public abstract class Security {

	public static String crypto(String senha) throws GeneralSecurityException, UnsupportedEncodingException {

		String senhaex = senha;

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(senhaex.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();
		
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		
		String senhahex = hexString.toString();

		return senhahex;

	}

	public static boolean validaToken(String senha, String hash) throws UnsupportedEncodingException, GeneralSecurityException {
		return crypto(senha).equals(hash)?true:false;
	}
		
	
	
	
}
