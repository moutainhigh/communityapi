package net.okdi.core.util;

import java.util.UUID;
/**
 * 邮件验证码生成工具类
 * @author xiaodong.wang
 * @date 2014-09-11
 * @version 1.0
 */
public class EmailVerifyCode {
	public static String getCode(){
		return GenMD5.generateMd5(UUID.randomUUID().toString());
	}
	public static void main(String[] args){
		System.out.println(getCode());
	}
}
