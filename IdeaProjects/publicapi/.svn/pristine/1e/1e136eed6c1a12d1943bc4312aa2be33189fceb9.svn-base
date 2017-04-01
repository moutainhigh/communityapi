package net.okdi.apiV4.service;

public interface MarkPackageService {


    String getPhoneByOrderNo(Long memberId, String wayBill);

    String findExcepList(Long compId, Long memberId, Integer page);

    String searchByPhoneOrOrderNo(Long compId, Long memberId, String orderNoOrPhone, Integer type);

    String addMark(Long netId, Long compId, String wayBill, String phone, String content,
                   Long memberId, String source, Long parId, String addrName, String addr);

    String findMarkContent(Long netId);

    String getParcelMarkHistoryByWaybill(String wayBill);

    String addMarkAndParcel(Long netId, Long compId, String wayBill, String phone, String content, Long memberId, Long parId, String addrName, String addr);
}
