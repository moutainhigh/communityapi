package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="serviceTypeInfo")
public class ServiceTypeInfo {
	
	private Long id;
	private Long netId;
	private String netName;
	//编号
	private String serviceNum;
	//服务名称
	private String serviceName;
	//外部代码
	private String outCode;	

	private Date createTime;
	private Date updateTime;
	//0：启用  1：停用
	private Integer flag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		this.netName = netName;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
