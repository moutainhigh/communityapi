package net.okdi.apiExt.service;

public interface ExtCompInfoService {

	public String queryCompInfoByAddressAndRange(String sendAddress, String range);

	public String queryMemberInfoByCompId(String compId);

	public String queryBatchCompInfoByAddressAndRange(String sendAddress, String range);

	public String queryBatchCompInfoByAddress(String sendAddress, String compId);

}
