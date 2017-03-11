package cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class algorithm {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		//String key = "This is key!";
		
		//key to byte[]
		Key key = KeyGenerator.getInstance("AES").generateKey();
		byte[] encodedKey = key.getEncoded();
		
		FileOutputStream write = new FileOutputStream(new File("E:/key.txt"));
		write.write(encodedKey);
		
		FileInputStream r = new FileInputStream(new File("E:/key.txt"));
		int read = 0;
		int i = 0;
		final int BUFFER_SIZE = 16 * 16;
		byte[] buffer = new byte[BUFFER_SIZE];
		byte[] key1;
		while (r.read() != -1 ) {
			
		}
	
		
		//byte[] to key
		byte[] decodedKey = encodedKey;
		Key originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
		if (key.equals(originalKey))
			System.out.println("equal");
		
		
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
