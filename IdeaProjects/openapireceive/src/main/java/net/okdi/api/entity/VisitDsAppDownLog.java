package net.okdi.api.entity;

import java.util.Date;

public class VisitDsAppDownLog {
    private Long id;

    private String shortUrl;
    
    private Short productType;
    
    private String userAgent;

    private Short appType;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getProductType() {
        return productType;
    }

    public void setProductType(Short productType) {
        this.productType = productType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Short getAppType() {
        return appType;
    }

    public void setAppType(Short appType) {
        this.appType = appType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
    
}