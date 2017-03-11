package cryptography;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public class DES {
	private Cipher desCipher;
	KeyGenerator keygenerator;
	private byte[] iv ;
	private final String INSTANCE_CYPHER = "DES/CBC/PKCS5Padding";
	 
	DES() throws NoSuchAlgorithmException, NoSuchPaddingException{
		keygenerator = KeyGenerator.getInstance("DES");
		desCipher = Cipher.getInstance(INSTANCE_CYPHER);
	}
	
	public Key generateKey(){
		    return keygenerator.generateKey();
	}
	
	
	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public byte[] encrypt(byte[] plaintext, Key key) throws NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,BadPaddingException{
		 desCipher.init(Cipher.ENCRYPT_MODE, key);
		 setIv(desCipher.getIV());
		 return desCipher.doFinal(plaintext);
	}
	
	public byte[] decrypt(byte[] ciphertext, Key key, byte[] iv) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		IvParameterSpec dps = new IvParameterSpec(iv);
		desCipher.init(Cipher.DECRYPT_MODE, key, dps);
		return desCipher.doFinal(ciphertext);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		
		DES des = new DES();
		Key key = des.generateKey();
		String plain = "haha";
		byte[] cipher = des.encrypt("haha".getBytes(), key);
		byte[] p = des.decrypt(cipher, key, des.getIv());
		System.out.println("plain: " + new String(p));
		
		
	}
}
