package net.okdi.api.vo;

import java.io.Serializable;


public class VO_BasNetInfo implements Serializable,Comparable<VO_BasNetInfo>{
    private Long netId;

    private String netName;

    private String firstLetter;

    private String code;
    
    private String telephone;
    
    private String url;

	public String getNetNum() {
		return netNum;
	}

	public void setNetNum(String netNum) {
		this.netNum = netNum;
	}

	private Short isPartners;
	
	private String netNum;
	
	
    public Short getIsPartners() {
		return isPartners;
	}

	public void setIsPartners(Short isPartners) {
		this.isPartners = isPartners;
	}

	public Long getNetId() {
        return netId;
    }

    public void setNetId(Long netId) {
        this.netId = netId;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName == null ? null : netName.trim();
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter == null ? null : firstLetter.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    
    
    public String getTelephone() {
		return telephone==null?"":telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUrl() {
		return url==null?"":url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
    public int compareTo(VO_BasNetInfo o) {
        return this.getFirstLetter().compareTo(o.getFirstLetter());
    }
}