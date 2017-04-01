package net.okdi.apiV5.service;

public interface NewWrongPriceService {

	String aliResend(Long memberId, String msgIds, String version, String system, String accountId,String memberPhone);


	String resend(Long memberId, String msgIds, String version, String system, String accountId,String memberPhone);
}
