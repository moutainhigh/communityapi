package net.okdi.mob.service;

import org.springframework.web.multipart.MultipartFile;

public interface SendMsgToCustomerService {
	String smsAndUpSound(String phone,String compId,String memberPhone,Long memberId, MultipartFile[] myfiles,Short isWaybillNum,Short isIdentifier);
	String sendVoiceSms(String phone,String compId,String memberPhone,Long memberId, MultipartFile[] myfiles,Short isWaybillNum,Short isIdentifier);
	String delSound(Integer day);
}
