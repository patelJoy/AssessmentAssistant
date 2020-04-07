package com.vgec.util;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static void main(String[] args) {
		System.out.println(Md5.getMd5("helloworld"));
	}
    public static String getMd5(String input) 
    { 
        try {
            MessageDigest md = MessageDigest.getInstance("Md5"); 
            byte[] messageDigest = md.digest(input.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
} 