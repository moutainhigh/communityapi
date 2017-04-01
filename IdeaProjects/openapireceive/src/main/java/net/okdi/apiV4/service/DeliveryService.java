package net.okdi.apiV4.service;

public interface DeliveryService {

    String queryDeliveryList(String memberId, Long netId, Long compId);
    String queryNotPrintList(String memberId, Long netId, Long compId);

    String queryDeliveryDetail(String uid);

    String confirmDelivery(String uids, String deliveryAddress);

    String getParListByQRCode(String uids);

    String confirmTakeDelivery(String memberId, String uids, String deliveryAddress, String index);
}
