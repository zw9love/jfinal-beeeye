package util;

import java.security.MessageDigest;

public class MD5Util {
	// 生成MD5
	public static String encrypt(String message) {
		String md5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); // 创建一个md5算法对象
			byte[] messageByte = message.getBytes("UTF-8");
			byte[] md5Byte = md.digest(messageByte); // 获得MD5字节数组,16*8=128位
			md5 = bytesToHex(md5Byte); // 转换为16进制字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypt(md5);
	}

	// 加密后解密
	public static String decrypt(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	// 二进制转十六进制
	public static String bytesToHex(byte[] bytes) {
		StringBuffer hexStr = new StringBuffer();
		int num;
		for (int i = 0; i < bytes.length; i++) {
			num = bytes[i];
			if (num < 0) {
				num += 256;
			}
			if (num < 16) {
				hexStr.append("0");
			}
			hexStr.append(Integer.toHexString(num));
		}
		return hexStr.toString().toUpperCase();
	}
}
