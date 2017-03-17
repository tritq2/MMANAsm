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
	private IvParameterSpec iv;
	private final String INSTANCE_CYPHER = "AES/CBC/PKCS5Padding";
	

	public AES() throws NoSuchAlgorithmException, NoSuchPaddingException {
		keygenerator = KeyGenerator.getInstance("AES");
		aesCipher = Cipher.getInstance(INSTANCE_CYPHER);

	}

	

	public Key generateKey() {
		return keygenerator.generateKey();
	}

	public IvParameterSpec getIv() {
		return iv;
	}

	public void setIv(IvParameterSpec Iv) {
		this.iv = Iv;
	}

	public byte[] encrypt(byte[] plaintext, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		aesCipher.init(Cipher.ENCRYPT_MODE, key);

		setIv(Convert.Bytes2Iv(aesCipher.getIV()));

		return aesCipher.doFinal(plaintext);
	}

	public byte[] decrypt(byte[] ciphertext, Key key, IvParameterSpec Iv) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		aesCipher.init(Cipher.DECRYPT_MODE, key, Iv);
		return aesCipher.doFinal(ciphertext);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		AES aes = new AES();
		Key key = aes.generateKey();
		String plain = "haha";
		byte[] cipher = aes.encrypt("haha".getBytes(), key);
		byte[] p = aes.decrypt(cipher, key, aes.getIv());
		System.out.println("plain: " + new String(p));
	}

}
