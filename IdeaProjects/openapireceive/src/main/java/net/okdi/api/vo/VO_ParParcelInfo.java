package net.okdi.api.vo;

import net.okdi.api.entity.ParParcelinfo;

public class VO_ParParcelInfo extends ParParcelinfo {

	private String compName;//公司名称
	
	private String netName; //网络名称
	
	private String serviceName; //服务产品名称

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
	
	
	
}
