package net.okdi.apiV4.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.InviteService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 包名: net.okdi.apiV4.service.impl
 * 创建人 : Elong
 * 时间: 2017/1/21 下午4:34
 */
@Service
public class InviteServiceImpl extends AbstractHttpClient<String> implements InviteService {
    @Autowired
    private ConstPool constPool;

    @Override
    public String collQRCode(String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("memberSrc", "3");
        params.put("productType", "3");
        String result = Post(constPool.getPromoUrl() + "lgrg/returnNewTypeUrl", params);
        JSONObject jsonObject = JSON.parseObject(result);
        if (!jsonObject.getBoolean("success")) {
            throw new ServiceException("请求失败,系统异常!");
        }
        String intiveUrl;
        try {
            intiveUrl = jsonObject.getJSONObject("data").getString("intiveUrl");
        } catch (Exception e) {
            throw new ServiceException("请求失败,系统异常!");
        }
        return intiveUrl;
    }

}
