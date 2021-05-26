package br.com.leorocha.meudoglindo.util;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SenhaUtil   {

	public static String criptografarSHA2(String texto) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(texto.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			String senhaCriptgrafada = hexString.toString();
			return senhaCriptgrafada;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String criptografarMD5(String texto) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(texto.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			return hash.toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
