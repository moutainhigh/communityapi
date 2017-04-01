package net.okdi.core.httpclient;

public class RawHttpHeader {

    private String contentType;

    private String tokenField;

    private String tokenStr;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTokenField() {
        return tokenField;
    }

    public void setTokenField(String tokenField) {
        this.tokenField = tokenField;
    }

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
    }
}
