package net.okdi.taobaoApi.service;

public interface TaobaoApiService {

	String queryPhoneStatus(Long companyId, String mailNo);

	String queryCompanyList();

}
