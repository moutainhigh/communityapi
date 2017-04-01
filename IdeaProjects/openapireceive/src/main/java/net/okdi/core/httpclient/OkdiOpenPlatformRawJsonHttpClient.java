package net.okdi.core.httpclient;

import org.springframework.beans.factory.annotation.Value;

public class OkdiOpenPlatformRawJsonHttpClient extends AbstractRawHttpClient {

    private @Value("${openPlatformToken}") String token;

    @Override
    protected RawHttpHeader doGetHeader() {
        RawHttpHeader header = new RawHttpHeader();
        header.setContentType("application/json;charset=utf-8");
        header.setTokenField("token");
        header.setTokenStr(token);
        return header;
    }
}
