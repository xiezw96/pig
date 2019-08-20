package com.pig4cloud.pig.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class PasswordEncoder {

	public static String encode(String rawPass, String salt, int hashIterations) {
		String result = null;
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			// 加密后的字符串
			BASE64Encoder base = new BASE64Encoder();
			result = base.encode(md.digest(mergePasswordAndSalt(rawPass, salt).getBytes("utf-8")));
			if (hashIterations > 0)
				result = encode(result, salt, hashIterations - 1);
		} catch (Exception ex) {
		}
		return result;
	}

	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	private static String mergePasswordAndSalt(String password, String salt) {
		if (password == null) {
			password = "";
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}
	
	public static String convertSha1(String inStr) {
		try {  
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
            crypt.reset();  
            crypt.update(inStr.getBytes("UTF-8"));  
            return bytesToHexString(crypt.digest());  
        } catch (NoSuchAlgorithmException e) {  
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {  
        	e.printStackTrace();
        }
		return inStr;
	}
	
	/**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
