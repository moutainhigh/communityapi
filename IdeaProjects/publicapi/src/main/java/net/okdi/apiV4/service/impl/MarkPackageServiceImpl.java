package net.okdi.apiV4.service.impl;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.MarkPackageService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarkPackageServiceImpl implements MarkPackageService {


    private @Autowired OpenApiHttpClient openApiHttpClient;

    @Override
    public String getPhoneByOrderNo(Long memberId, String wayBill) {
        Map<String, String> param = new HashMap<>();
        param.put("memberId", memberId + "");
        param.put("waybill", wayBill);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/phone/", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }

    @Override
    public String findExcepList(Long compId, Long memberId, Integer page) {
        Map<String, Object> param = new HashMap<>();
        param.put("compid", compId);
        param.put("mid", memberId);
        param.put("page", page);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/list/", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }

    @Override
    public String searchByPhoneOrOrderNo(Long compId, Long memberId, String orderNoOrPhone, Integer type) {
        Map<String, Object> param = new HashMap<>();
        param.put("compid", compId);
        param.put("mid", memberId);
        param.put("orderNoOrPhone", orderNoOrPhone);
        param.put("type", type);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/search/", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }

    @Override
    public String addMark(Long netId, Long compId, String wayBill, String phone, String content,
                          Long memberId, String source, Long parId, String addrName, String addr) {

        Map<String, Object> param = new HashMap<>();
        param.put("netid", netId);
        param.put("compid", compId);
        param.put("waybill", wayBill);
        param.put("phone", phone);
        param.put("content", content);
        param.put("mid", memberId);
        param.put("source", source);
        param.put("parid", parId);
        param.put("addrname", addrName);
        param.put("addr", addr);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/add/", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }

    @Override
    public String addMarkAndParcel(Long netId, Long compId, String wayBill, String phone, String content, Long memberId, Long parId, String addrName, String addr) {
        Map<String, Object> param = new HashMap<>();
        param.put("netid", netId);
        param.put("compid", compId);
        param.put("waybill", wayBill);
        param.put("phone", phone);
        param.put("content", content);
        param.put("mid", memberId);
        param.put("parid", parId);
        param.put("addrname", addrName);
        param.put("addr", addr);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/addpar/", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }

    @Override
    public String findMarkContent(Long netId) {
        Map<String, Object> param = new HashMap<>();
        param.put("netId", netId);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/content/", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }

    @Override
    public String getParcelMarkHistoryByWaybill(String wayBill) {
        Map<String, Object> param = new HashMap<>();
        param.put("waybill", wayBill);
        String resp = openApiHttpClient.doPassSendStrParcel("mark/content/his", param);
        if (PubMethod.isEmpty(resp)) {
            throw new ServiceException("请求失败");
        }
        return resp;
    }


}
