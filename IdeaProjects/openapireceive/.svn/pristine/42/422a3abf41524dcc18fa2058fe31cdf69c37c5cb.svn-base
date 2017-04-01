package net.okdi.core.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 该方法只提供单用户简单邮件发送。包括text格式内容与HTML格式内容。
 * 
 * 不提供发送附件，抄送功能
 * 
 * @date 2014-09-04
 * @author xiaodong.wang
 * @version 0.1
 * 
 */
public class SimpleMail {

	@Autowired
	private JavaMailSender javaMailSender = null;
	private String mailFrom = null;
	private String encode = null;
	@Autowired
	private TaskExecutor taskExecutor;// 注入Spring封装的异步执行器

	/**
	 * 同步发送普通text格式邮件
	 * 
	 * @param subject
	 *            邮件标题
	 * @param text
	 *            邮件正文
	 * @param sendTo
	 *            接收地址
	 */
	public void sendText(String subject, String text, String sendTo) {
		SimpleMailMessage msg = new SimpleMailMessage();// SimpleMailMessage只能用来发送text文本
		msg.setFrom(mailFrom);
		msg.setTo(sendTo);// 接受者
		msg.setSubject(subject);// 主题
		msg.setText(text);// 正文内容
		try {
			javaMailSender.send(msg);// 发送邮件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 异步发送普通text格式邮件
	 * 
	 * @param subject
	 * @param text
	 * @param sendTo
	 */
	public void sendTextAsync(final String subject, final String text, final String sendTo) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendText(subject, text, sendTo);// 发送邮件
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * 同步发送HTML格式内容邮件
	 * 
	 * @param subject
	 * @param html
	 * @param sendTo
	 */
	public void sendHtml(String subject, String html, String sendTo) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, encode);
			messageHelper.setTo(sendTo);// 接受者
			messageHelper.setFrom(mailFrom);// 发送者
			messageHelper.setSubject(subject);// 主题
			// 邮件内容，注意加参数true
			messageHelper.setText(html, true);
			javaMailSender.send(mailMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 异步发送HTML格式邮件
	 * 
	 * @param subject
	 * @param html
	 * @param sendTo
	 */
	public void sendHtmlAsync(final String subject, final String html, final String sendTo) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendHtml(subject, html, sendTo);// 发送邮件
				} catch (Exception e) {
				}
			}
		});
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

}
