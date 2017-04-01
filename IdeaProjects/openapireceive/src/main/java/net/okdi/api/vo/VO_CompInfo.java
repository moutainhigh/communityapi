package net.okdi.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class VO_CompInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @since jdk1.6
	 */
	private static final long serialVersionUID = 1L;
	/** 网络ID */
	private Long netId;
	/** 公司ID */
	private Long compId;
	/** 公司合作标识 1:合作，0：非合作 */
	private String cooperationLogo;
	/** 网络名称 */
	private String netName;
	/** 网络联系座机 */
	private String netTel;
	/** 公司名称 */
	private String compName;
	/** 公司地址 */
	private String compAddress;
	/** 公司地址ID */
	private Long compAddressId;
	/** 站点距离 */
	private BigDecimal distance;
	/** 公司经度 */
	private BigDecimal longitude;
	/** 公司纬度 */
	private BigDecimal latitude;

	/** 公司所在网络LOGO地址 */
	private String imageUrl;

	/** 公司负责人 */
	private String responsible;

	/** 公司负责人电话 */
	private String responsibleTelephone;

	/** 公司所在省份ID */
	private Long provinceId;

	/** 公司座机号 */
	private String compTelephone;

	/** 公司手机号 */
	private String compMobile;

	/** 公司电话拼接 */
	private String compTel;// 格式化公司电话

	public VO_CompInfo() {
		super();
	}

	public Long getNetId() {
		return netId;
	}

	public void setNetId(Long netId) {
		this.netId = netId;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public String getCooperationLogo() {
		return cooperationLogo;
	}

	public void setCooperationLogo(String cooperationLogo) {
		this.cooperationLogo = cooperationLogo;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getNetTel() {
		return netTel;
	}

	public void setNetTel(String netTel) {
		this.netTel = netTel;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress == null ? "" : compAddress.replace("-", "").replace("|", "");
	}

	public Long getCompAddressId() {
		return compAddressId;
	}

	public void setCompAddressId(Long compAddressId) {
		this.compAddressId = compAddressId;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getResponsible() {
		return responsible == null ? "" : responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getResponsibleTelephone() {
		return responsibleTelephone == null ? "" : responsibleTelephone;
	}

	public void setResponsibleTelephone(String responsibleTelephone) {
		this.responsibleTelephone = responsibleTelephone;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getCompTelephone() {
		return compTelephone == null ? "" : compTelephone;
	}

	public void setCompTelephone(String compTelephone) {
		this.compTelephone = compTelephone;
	}

	public String getCompMobile() {
		return compMobile == null ? "" : compMobile;
	}

	public void setCompMobile(String compMobile) {
		this.compMobile = compMobile;
	}

	public String getCompTel() {
		StringBuilder str = new StringBuilder();
		if (compTelephone != null && !"".equals(compTelephone)) {
			str.append(compTelephone);
		}
		if (compMobile != null && !"".equals(compMobile)) {
			str.append(","+compMobile);
		}
		return str.toString();
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
