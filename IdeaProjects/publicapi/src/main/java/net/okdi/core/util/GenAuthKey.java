package net.okdi.core.util;

import java.util.UUID;

/**
 * Auth
 */
public class GenAuthKey {

	/**
	 * 生成公钥
	 */
	public static final String getPublicKey(String memberId) {
		DesEncrypt desEncrypt = new DesEncrypt();
		return desEncrypt.convertPwd(memberId, "ENC");
	}

	/**
	 * 生成私钥
	 */
	public static final String getPrivateKey() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
