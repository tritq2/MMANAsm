package cryptography;

import java.security.Key;

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
}
