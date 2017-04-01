package net.okdi.apiV4.service;

public interface SmsTemplateService {

	String querySmsTemplateContent(Long memberId);

	String saveSmsTemplateContent(Long memberId, String content, String name, Long phone);

	String updateSmsTemplateContent(Long id, Long memberId, String content);

	String deleteSmsTemplateContent(Long id);
	
	String killToken(String token);

	String dealTemplate(Long memberId);

	String dealTemplatePhone();

	String isBelongBlackAndWrite(String world);

}
