package net.okdi.apiV4.service;

import java.util.Map;

public interface SubmittedSignService {

	String queryAuthStatus(Long memberId, Long netId);

	String quOrUpParcelToControlMemberId(Long memberId, String billJson);

	String subSignBatch(Long memberId, Long compId, String billJson, String flag, Long netId);

	String querySignInfoCount(Long memberId, String dateYM, Long compId);

	String queryNoticeInfoCount(Long memberId, String dateYM, Long compId);

	String querySignInfoRecord(Long memberId, String dateYMD, Long compId);

	String queryUploadFailParcelInfo(Long memberId);

	String querySignRecordDetail(Long uid, Long memberId);

	String updateSignBill(String newBill , Long memberId, Long parId, String newPhone);

}
