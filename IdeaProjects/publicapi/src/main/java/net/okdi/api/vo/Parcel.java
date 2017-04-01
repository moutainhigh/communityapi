package net.okdi.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="Parcel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Parcel")
public class Parcel {
	
	
	@XmlElement(nillable=true)
	private String sendName;//发件人姓名
	@XmlElement(nillable=true)
	private Long sendCityId;//发件市id
	@XmlElement(nillable=true)
	private String sendAddress;//发件详细地址
	@XmlElement(nillable=true)
	private String sendMobile;//发件电话
	@XmlElement(nillable=true)
	private String recName;//收件姓名
	@XmlElement(nillable=true)
	private Long recCityId;//收件市id
	@XmlElement(nillable=true)
	private String recAddress;//收件详细地址
	@XmlElement(nillable=true)
	private String recMobile;//收件电话
	@XmlElement(nillable=true)
	private String shopName; //店铺名称
	@XmlElement(nillable=true)
	private Long netId;//网络id
	@XmlElement(nillable=true)
	private String netNum;//网络简码
	@XmlElement(nillable=true)
	private String waybillNum;//运单号
	@XmlElement(nillable=true)
	private Long orderNum;//订单号
	@XmlElement(nillable=true)
	private Double sendlongitude;
	@XmlElement(nillable=true)
	private Double sendlatitude;
	@XmlElement(nillable=true)
	private Double reclongitude;
	@XmlElement(nillable=true)
	private Double reclatitude;
	// Constructors

	/** default constructor */
	public Parcel() {
	}

	// Property accessors

	public String getSendName() {
		return this.sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendAddress() {
		return this.sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getSendMobile() {
		return this.sendMobile;
	}

	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile;
	}



	public String getRecName() {
		return this.recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRecAddress() {
		return this.recAddress;
	}

	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}

	public String getRecMobile() {
		return this.recMobile;
	}

	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}


	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getNetId() {
		return netId;
	}

	public void setNetId(Long netId) {
		this.netId = netId;
	}

	public String getNetNum() {
		return netNum;
	}

	public void setNetNum(String netNum) {
		this.netNum = netNum;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	

	public Long getSendCityId() {
		return sendCityId;
	}

	public void setSendCityId(Long sendCityId) {
		this.sendCityId = sendCityId;
	}

	public Long getRecCityId() {
		return recCityId;
	}

	public void setRecCityId(Long recCityId) {
		this.recCityId = recCityId;
	}

	public Double getSendlongitude() {
		return sendlongitude;
	}

	public void setSendlongitude(Double sendlongitude) {
		this.sendlongitude = sendlongitude;
	}

	public Double getSendlatitude() {
		return sendlatitude;
	}

	public void setSendlatitude(Double sendlatitude) {
		this.sendlatitude = sendlatitude;
	}

	public Double getReclongitude() {
		return reclongitude;
	}

	public void setReclongitude(Double reclongitude) {
		this.reclongitude = reclongitude;
	}

	public Double getReclatitude() {
		return reclatitude;
	}

	public void setReclatitude(Double reclatitude) {
		this.reclatitude = reclatitude;
	}
	
}
