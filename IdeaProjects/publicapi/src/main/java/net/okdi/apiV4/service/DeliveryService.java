package net.okdi.apiV4.service;

/**
 * 包名: net.okdi.apiV4.service
 * 创建人 : Elong
 * 时间: 05/02/2017 5:36 PM
 * 描述 :
 */
public interface DeliveryService {

    //String queryDeliveryList(String memberId, String date, String phone, String expWaybillNum, String all, Integer currentPage, Integer pageSize);
    public String queryDeliveryList(String memberId, Long netId, Long compId);
    public String queryNotPrintList(String memberId, Long netId, Long compId);
    public String confirmDelivery(String uids, String deliveryAddress);
	public String queryDeliveryDetail(String uid);
	public String getParListByQRCode(String uids);
	public String confirmTakeDelivery(String memberId, String uids, String deliveryAddress, String index);

}
