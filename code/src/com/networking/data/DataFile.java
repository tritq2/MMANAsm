package com.networking.data;

import java.io.Serializable;
import java.security.Key;

import com.networking.tags.Tags;

@SuppressWarnings("serial")
public class DataFile implements Serializable{

	private String openTags = com.networking.tags.Tags.FILE_OPEN_TAG;
	private String closeTags = com.networking.tags.Tags.FILE_END_TAG;
	public byte[] data;
	
	public String encryptedkey = "";
	public String iv = "";

	public DataFile(int size) {
		data = new byte[size];
	}
	
	public DataFile() {
		data = new byte[Tags.MAX_MSG_SIZE];
	}
	
	public DataFile(byte[] d, String k, String Iv){
		data = d;
		encryptedkey = k;
		iv = Iv;
	}
}

