package cryptography;
import java.security.InvalidAlgorithmParameterException;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.networking.tags.enCode;

import cryptography.Convert;
public class DES {
	private Cipher desCipher;
	private KeyGenerator keygenerator;
	private IvParameterSpec iv ;
	private final String INSTANCE_CYPHER = "DES/CBC/PKCS5Padding";
	 
	public DES() throws NoSuchAlgorithmException, NoSuchPaddingException{
		keygenerator = KeyGenerator.getInstance("DES");
		desCipher = Cipher.getInstance(INSTANCE_CYPHER);
	}
	
	public Key generateKey(){
		    return keygenerator.generateKey();
	}
	
	
	public IvParameterSpec getIv() {
		return iv;
	}

	public void setIv(IvParameterSpec Iv) {
		this.iv = Iv;
	}

	public byte[] encrypt(byte[] plaintext, Key key) throws NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,BadPaddingException{
		 desCipher.init(Cipher.ENCRYPT_MODE, key);
		 setIv(Convert.Bytes2Iv(desCipher.getIV()));
		 return desCipher.doFinal(plaintext);
	}
	
	public byte[] decrypt(byte[] ciphertext, Key key, IvParameterSpec Iv) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		desCipher.init(Cipher.DECRYPT_MODE, key, Iv);
		return desCipher.doFinal(ciphertext);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		
		/*DES des = new DES();
		Key key = des.generateKey();
		String plain = "haha";
		byte[] cipher = des.encrypt("haha".getBytes(), key);
		byte[] p = des.decrypt(cipher, key, des.getIv());
		System.out.println("plain: " + new String(p));
		*/
		
		DES des = new DES();
		String key_str = "12345678";
		Key key = Convert.Bytes2Key(key_str.getBytes(), "DES");
		//Key key = des.generateKey();
		String plaintext = "I'm vuthede";
		byte[] key_byte = Convert.Key2Bytes(key);
		byte[] cbyte = des.encrypt(plaintext.getBytes(), key);
		IvParameterSpec iv = des.getIv();
		
		
		
		String ctext = new String(des.decrypt(cbyte, Convert.Bytes2Key(key_byte, "DES"),iv));
		System.out.println(ctext);
		
		
	}
}





