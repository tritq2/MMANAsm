package test;

import java.awt.EventQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.network.client.ContractApp;

public class testthread {
	public static int rsa = 0;
	
	public testthread(){
		rsa = 5;
	}
	
	public static void main(String args[]) {
		testthread a = new testthread();
		System.out.println(testthread.rsa);
	}

}
