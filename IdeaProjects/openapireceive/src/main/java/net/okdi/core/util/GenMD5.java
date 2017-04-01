package net.okdi.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * MD5密码加密
 */
public class GenMD5 {

	/**
	 * 密码加密
	 */
	public static final String generateMd5(String pwd) {
		MessageDigest messageDigest = getMessageDigest();
		byte[] digest;
		try {
			digest = messageDigest.digest(pwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}
		return new String(Hex.encodeHex(digest));
	}

	/**
	 * 密码+混淆码加密
	 */
	public static final String generateMd5(String pwd, String salt) {
		String saltedPass = mergePasswordAndSalt(pwd, salt, false);
		MessageDigest messageDigest = getMessageDigest();
		byte[] digest;
		try {
			digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}
		return new String(Hex.encodeHex(digest));
	}

	private static final MessageDigest getMessageDigest() {
		String algorithm = "MD5";
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm ["
					+ algorithm + "]");
		}
	}

	private static final String mergePasswordAndSalt(String password,
			Object salt, boolean strict) {
		if (password == null) {
			password = "";
		}
		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1)
					|| (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException(
						"Cannot use { or } in salt.toString()");
			}
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	public static void main(String[] args) {
		// ea5b82a9ce9b0e67cfa3ebac77722674
		System.out.println(generateMd5("123456"));
	}
}
