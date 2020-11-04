package com.example.westwoodhomes;

import java.math.BigInteger;
import java.security.MessageDigest;

public class md5 {
    public static  byte[] encryptMD5(byte[] data) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    public static String encryptPass(String password){
        byte[] md5Input = password.getBytes();
        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, encryptMD5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String md5Str = md5Data.toString();
        return md5Str;
    }


}