package cryptography;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public class AES {
	private Cipher aesCipher;
	private KeyGenerator keygenerator;
	private byte[] iv ;
	private final String INSTANCE_CYPHER = "AES/CBC/PKCS5Padding";

	AES() throws NoSuchAlgorithmException, NoSuchPaddingException{
		keygenerator = KeyGenerator.getInstance("AES");
		aesCipher = Cipher.getInstance(INSTANCE_CYPHER);
		
	}

	public Key generateKey() {
		return keygenerator.generateKey();
	}

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public byte[] encrypt(byte[] plaintext, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		aesCipher.init(Cipher.ENCRYPT_MODE, key);
		setIv(aesCipher.getIV());
		return aesCipher.doFinal(plaintext);
	}

	public byte[] decrypt(byte[] ciphertext, Key key, byte[] iv )
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
	    IvParameterSpec dps = new IvParameterSpec(iv);
		aesCipher.init(Cipher.DECRYPT_MODE, key, dps);
		return aesCipher.doFinal(ciphertext);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		AES aes = new AES();
		Key key = aes.generateKey();
		String plain = "haha";
		byte[] cipher = aes.encrypt("haha".getBytes(), key);
		byte[] p = aes.decrypt(cipher, key, aes.getIv());
		System.out.println("plain: " + new String(p));
	}

}
