package cryptography;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class Convert {
	private Convert(){};
	
	public static byte[] Key2Bytes(Key key){
		return key.getEncoded();
	}
	
	public static byte[] Iv2Bytes(IvParameterSpec Iv){
		return Iv.getIV();
	}
	
	public static Key Bytes2Key(byte[] bytes, String algorithm){
		return new SecretKeySpec(bytes, 0, bytes.length, algorithm); 
	}
	
	public static IvParameterSpec Bytes2Iv(byte[] bytes){
		return new IvParameterSpec(bytes);
	}
	
	public static String Key2String(Key key){
		return  Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static Key String2Key(String str, String algorithm, boolean isPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] b_str = Base64.getDecoder().decode(str);
		if (algorithm != "RSA")
			return Convert.Bytes2Key(b_str, algorithm);
		
		if (isPublicKey){
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b_str);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			return pubKey;
		}
		else {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b_str);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		}
	}
	
}
