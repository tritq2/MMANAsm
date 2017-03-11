package cryptography;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;

public class RSA {
	private Cipher rsaCipher;
	KeyPairGenerator keypairgenerator;
	private final String INSTANCE_CYPHER = "RSA/ECB/PKCS1Padding";
	
	RSA() throws NoSuchAlgorithmException, NoSuchPaddingException{
		keypairgenerator = KeyPairGenerator.getInstance("RSA");
		rsaCipher = Cipher.getInstance(INSTANCE_CYPHER);
	}
	
	public KeyPair generateKey(){
		// keypairgenerator.initialize(2048);
	     return keypairgenerator.generateKeyPair();
}
	
	public  byte[] encrypt(byte[] data, Key publicKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	      rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
	      return rsaCipher.doFinal(data);
	}

	public  byte[] decrypt(byte[] data, Key privateKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
	      rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
	      return rsaCipher.doFinal(data); 
	}
	
	public static void main (String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		RSA rsa = new RSA();
	    KeyPair keys = rsa.generateKey();
	    Key publicKey = keys.getPublic();
	    Key privateKey = keys.getPrivate();
	    String data = "abcdefghijklmnop";
	    

	    System.out.println("Plaintext: " + data);
	    byte[] ciphertext = rsa.encrypt(data.getBytes(), publicKey);
	    System.out.println("Ciphertext: " + ciphertext);
	    byte[] plaintext = rsa.decrypt(ciphertext, privateKey);
	    System.out.println("Decrypted text " + new String(plaintext));
	}
	
	
}
