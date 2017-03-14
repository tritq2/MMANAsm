package cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class algorithm {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		
		Convert.String2Key("z2Ä8E¶ñ‰", "DES", true);
		//String k = new String(rsa.)
		/*
		RSA rsa = new RSA();
		KeyPair kp = rsa.generateKey();
		Key publickey  = kp.getPublic();
		Key privatekey = kp.getPrivate();
		String privatekey_str = Convert.Key2String(privatekey);
		
		String text = "This key key of AES";
		byte[] c = rsa.encrypt(text.getBytes(), publickey);
		
		
		
		
		byte[] p = rsa.decrypt(c, Convert.String2Key(privatekey_str, "RSA", false));
		
		String plain  = new String(p);
		System.out.println("After decrypting: " + plain);
		
		
		
		*/
		//String key = "This is key!";
		/*
		String key_str = "1234567890123456";
		String ptext  = "vuthede";
		
		AES des = new AES();
		
		//Coonvert 8 byte to Key
		Key key = Convert.Bytes2Key(key_str.getBytes(), "AES");
		
		//Encrypt
		byte[] c_byte = des.encrypt(ptext.getBytes(), key);
		
		//Get Initialize vector;
		IvParameterSpec iv =des.getIv();
		
		//decrypt
		byte[] plaintext_byte = des.decrypt(c_byte, key, iv);
		System.out.println("Sau khi giai ma: " + new String(plaintext_byte));
		
	*/
		
	/*	
		//key to byte[]
		Key key = KeyGenerator.getInstance("AES").generateKey();
		byte[] encodedKey = key.getEncoded();
		FileOutputStream write = new FileOutputStream(new File("E:/key.txt"));
		write.write(encodedKey);
		write.close();
		
		
		
		
		
		//byte[] to key
		FileInputStream r = new FileInputStream(new File("E:/key.txt"));
		int sizeFile = (int) r.getChannel().size();
		byte[]  decodedKey = new byte[sizeFile];
		r.read(decodedKey);
		Key originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
		if (key.equals(originalKey))
			System.out.println("equal");
		*/
		
		//Read and Write any file into byte and vice versa
		/*
		final int BUFFER_SIZE = 1024 * 1024; // this is actually bytes
		FileInputStream fis = new FileInputStream(new File("E:/a.wmv"));
		byte[] buffer = new byte[BUFFER_SIZE];
		int read = 0;
		int i= 0;
		FileOutputStream fos = new FileOutputStream("E:/b.wmv");
		while ((read = fis.read(buffer)) > 0) {
			i++;
			System.out.println("chunk i-th: " + i );
			fos.write(buffer);
		}
		fis.close();
		fos.close();
		*/
	}
}
