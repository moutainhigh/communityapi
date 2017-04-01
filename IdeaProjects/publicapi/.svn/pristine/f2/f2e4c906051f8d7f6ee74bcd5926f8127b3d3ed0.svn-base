package net.okdi.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName VO_CompInfo
 * @Description TODO
 * @author feng.wang
 * @date 2014-11-4
 * @since jdk1.6
 */
public class VO_CompInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @since jdk1.6
	 */
	private static final long serialVersionUID = 1L;
	/** 网络ID */
	private String netId;
	/** 网络名称 */
	private String netName;
	/** 网络电话 */
	private String netTelephone;
	/** 公司ID */
	private String compId;
	/** 公司名称 */
	private String compName;
	/** 公司地址 */
	private String compAddress;
	/** 公司电话 */
	private String compTelephone;
	/** 公司经理电话 */
	private String compManagerPhone;

	/** 站点距离 */
	private BigDecimal distance;
	/** 公司合作标识 1:合作，0：非合作 */
	private String cooperationLogo;

	public VO_CompInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId == null ? "" : netId;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName == null ? "" : netName;
	}

	public String getNetTelephone() {
		return netTelephone;
	}

	public void setNetTelephone(String netTelephone) {
		this.netTelephone = netTelephone == null ? "" : netTelephone;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId == null ? "" : compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName == null ? "" : compName;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress == null ? "" : compAddress.replace("-", "").replace("|", "");
	}

	public String getCompTelephone() {
		return compTelephone;
	}

	public void setCompTelephone(String compTelephone) {
		this.compTelephone = compTelephone == null ? "" : compTelephone;
	}

	public String getCompManagerPhone() {
		return compManagerPhone;
	}

	public void setCompManagerPhone(String compManagerPhone) {
		this.compManagerPhone = compManagerPhone == null ? "" : compManagerPhone;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getCooperationLogo() {
		return cooperationLogo;
	}

	public void setCooperationLogo(String cooperationLogo) {
		this.cooperationLogo = cooperationLogo;
	}

	@Override
	public boolean equals(Object obj) {
		VO_CompInfo a = (VO_CompInfo) obj;
		if (this.getCompId().equals(a.getCompId()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return this.compId.hashCode();
	}

}
