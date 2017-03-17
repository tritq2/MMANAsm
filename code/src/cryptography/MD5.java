package cryptography;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {
	
	public static byte[] getchecksumOfFile(String path)
			throws NoSuchAlgorithmException, IOException {
	
		MessageDigest md = MessageDigest.getInstance("MD5");
		InputStream is = new  FileInputStream(path);
		md.reset();
		byte[] bytes = new byte[1024];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		return md.digest();
	
	}
	/*
	public static byte[] getchecksumOfFile(String path) throws NoSuchAlgorithmException, IOException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		try (InputStream is = new FileInputStream(path);
		     DigestInputStream dis = new DigestInputStream(is, md)) 
		{
		  
		}
		return md.digest();
	}
	*/
}
