package net.okdi.core.mail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.okdi.test.BaseTest;

public class SimpleMailTest extends BaseTest {
	@Autowired
	private SimpleMail simpleMail;
	@Test
	public void testText(){
		simpleMail.sendText("这是一封测试邮件", "测试内容", "1251129264@qq.com");
	}
	@Test
	public void testHtml(){
		simpleMail.sendHtml("这是一封测试邮件", "<html><body><a href='#'>测试邮件</a></body></html>", "1251129264@qq.com");
	}
	@Test
	public void testAsyncText(){
		simpleMail.sendTextAsync("这是一封测试邮件", "测试内容", "1251129264@qq.com");
	}
	@Test
	public void testAsyncHtml(){
		simpleMail.sendHtmlAsync("这是一封测试邮件", "测试内容", "1251129264@qq.com");
	}
}
