package VNPaySubsystem;

import java.security.SecureRandom;

public class Config {

	public static String vnp_SecureHash = String.valueOf('1');

	public static String vnp_ver = String.valueOf('1');

	public static String tnm_code = String.valueOf('1');

	public static String vnp_PayUrl = String.valueOf("vnpay.com");

	public static String vnp_ReturnUrl = String.valueOf("return_url_vnpay.com");

	public static String vnp_Tmn = String.valueOf('1');

	public static String getIp() {
		return "123.123.123";
	}

	public static String getRandNum(int num) throws IllegalArgumentException{
		if(num <= 0) throw new IllegalArgumentException();
		byte[] bytes = new byte[num];
		new SecureRandom().nextBytes(bytes);
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			int index = Math.abs(b % chars.length);
			sb.append(chars[index]);
		}
		return sb.toString();
	}

}
