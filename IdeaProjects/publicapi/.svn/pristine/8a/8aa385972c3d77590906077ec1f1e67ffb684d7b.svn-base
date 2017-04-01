package net.okdi.mob.common.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import net.okdi.core.util.ApplicationConstant;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储.
 * 
 * 方法: void getKey(String strKey)从strKey的字条生成一个Key
 * 
 * String getEncString(String strMing)对strMing进行加密,返回String密文 String
 * getDesString(String strMi)对strMin进行解密,返回String明文
 * 
 * byte[] getEncCode(byte[] byteS)byte[]型的加密 byte[] getDesCode(byte[]
 * byteD)byte[]型的解密
 */

public class DesEncrypt {
	Key key;

	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 */
	public void getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
	        //_generator.init(new SecureRandom(strKey.getBytes()));//linux環境下會每次會不同，windows不會
	        //SecureRandom 實現嘗試完全隨機化生成器本身的內部狀態，除非調用方在調用 getInstance 方法之後又調用了 setSeed 方法：
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	        secureRandom.setSeed(strKey.getBytes());
	        _generator.init(secureRandom);
			
			this.key = _generator.generateKey();
			_generator = null;
			//另一种方式
			//KeySpec dks = new DESKeySpec(strKey.getBytes());
			//SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//this.key = keyFactory.generateSecret(dks);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 */
	public String getEncString(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = this.getEncCode(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String getDesString(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		if(strMi!=null && !strMi.equals(""))
		{
			try {
				byteMi = base64De.decodeBuffer(strMi);
				byteMing = this.getDesCode(byteMi);
				strMing = new String(byteMing, "UTF8");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				base64De = null;
				byteMing = null;
				byteMi = null;
			}
	
		}
				return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
//			cipher = Cipher.getInstance("PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
//			cipher = Cipher.getInstance("PKCS5Padding");
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;

	}
	
    /**
     * 转换密码
     * 创 建 人:  文超
     * 创建时间:  2011-8-16 上午09:55:13  
     * @param loginPwd  明文或密文
     * @param type  ENC 加密，将明文loginPwd进行加密变成密文；非ENC 解密，由密文loginPwd进行解密成明文
     * @return
     * @see [类、类#方法、类#成员]
     */
	public String convertPwd(String loginPwd,String type){
		DesEncrypt des = new DesEncrypt();// 实例化一个对像
		String key=ApplicationConstant.PASSWORD_KEY;			
		des.getKey(key);
		if(type.equals("ENC")){
			loginPwd = des.getEncString(loginPwd);//加密
		}else{
			loginPwd = des.getDesString(loginPwd);//解密				
		}
		return loginPwd;			
	}

	public static void main(String[] args) {

		DesEncrypt des = new DesEncrypt();// 实例化一个对像
//		des.getKey("MYKEY11");// 生成密匙
//		System.out.println("key=MYKEY11");
//		String strEnc = des.getEncString("CHINA");// 加密字符串,返回String的密文
//		System.out.println("密文=" + strEnc);
//
//		String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
//		System.out.println("明文=" + strDes);
		
		String t = des.convertPwd("123456", "ENC");
		System.out.println(t);
		String t1 = des.convertPwd("gHqTKSE5FXw=", "ABC");
		System.out.println(t1);
		
		
	}
	
}
