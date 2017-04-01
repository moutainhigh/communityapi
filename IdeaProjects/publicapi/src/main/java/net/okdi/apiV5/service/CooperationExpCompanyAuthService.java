package net.okdi.apiV5.service;

public interface CooperationExpCompanyAuthService {


    String getAuthStatusByMemberId(Long memberId, Long netId);

    String addAuth(Long memberId, Long netId, String orgCode, String userCode, String telNum, String password);
}
