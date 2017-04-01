package net.okdi.core.util.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("constPool")
public class ConstPool {
	@Value("${validateCenterUrl}")
	private String validateCenterUrl;
	@Value("${registerCenterUrl}")
	private String registerCenterUrl;
	@Value("${userCenterUrl}")
	private String userCenterUrl;
	@Value("${expCenterUrl}")
	private String expCenterUrl;
	@Value("${updCenterUrl}")
	private String updCenterUrl;	
	@Value("${cas.logout}")
	private String logoutUrl;
	@Value("${headImgPath}")
	private String headImgPath;
	@Value("${frontImgPath}")
	private String frontImgPath;
	@Value("${inHandImgPath}")
	private String inHandImgPath;
	@Value("${callBackFhwUrl}")
	private String callBackFhwPath;
	@Value("${promo.content.url}")
	private String promoContentUrl;
	@Value("${ucenter.url}")
	private String ucenterUrl;
	@Value("${callBackErp}")
	private String erpUrl;
	@Value("${sms_zt_url}")
	private String smsZtUrl;

	@Value("${o2opub.url}")
	private String o2opubUrl;
	
	@Value("${customerUrl}")
	
	private String customerUrl;
	@Value("${net.pic.save.path}")
	private String netSavePath;
	
	@Value("${net.pic.url}")
	private String netPicUrl;
	
	@Value("${picture.readPath}")
	private String readPath;
	
	@Value("${opencall}")
	private String opencall;
	
	@Value("${express.card}")
	private String expressCardUrl;
	
	@Value("${opencustomerUrl}")
	private String opencustomerUrl;
	@Value("${openApiUrl}")
	private String openApiUrl;
	@Value("${express.communityapi.url}")
	private String communityapiUrl;
	
	@Value("${openApiParcelUrl}")
	private String openApiParcelUrl;
	
	/**
	 * 国通快递接口 - 待取包裹查询接口
	 */
	@Value("${openListParcelUrl}")
	private String openListParcelUrl;
	/**
	 * 国通快递接口 - 快递单号查询包裹接口
	 */
	@Value("${openGetParcelUrl}")
	private String openGetParcelUrl;
	/**
	 * 国通快递接口 - 包裹上传接口
	 */
	@Value("${openUploadParcelUrl}")
	private String openUploadParcelUrl;
	/**
	 * 国通快递接口 - 包裹揽收接口
	 */
	@Value("${openReceivedParcelUrl}")
	private String openReceivedParcelUrl;
	/**
	 * 国通快递接口 - 包裹派件接口
	 */
	@Value("${openDeliveryParcelUrl}")
	private String openDeliveryParcelUrl;
	/**
	 * 国通快递接口 - 包裹签收接口
	 */
	@Value("${openSignParcelUrl}")
	private String openSignParcelUrl;
	/**
	 * 国通快递接口 - 包裹标记接口
	 */
	@Value("${openMarkParcelUrl}")
	private String openMarkParcelUrl;
	
	
	public String getOpenListParcelUrl() {
		return openListParcelUrl;
	}
	public void setOpenListParcelUrl(String openListParcelUrl) {
		this.openListParcelUrl = openListParcelUrl;
	}
	public String getOpenGetParcelUrl() {
		return openGetParcelUrl;
	}
	public void setOpenGetParcelUrl(String openGetParcelUrl) {
		this.openGetParcelUrl = openGetParcelUrl;
	}
	public String getOpenUploadParcelUrl() {
		return openUploadParcelUrl;
	}
	public void setOpenUploadParcelUrl(String openUploadParcelUrl) {
		this.openUploadParcelUrl = openUploadParcelUrl;
	}
	public String getOpenReceivedParcelUrl() {
		return openReceivedParcelUrl;
	}
	public void setOpenReceivedParcelUrl(String openReceivedParcelUrl) {
		this.openReceivedParcelUrl = openReceivedParcelUrl;
	}
	public String getOpenDeliveryParcelUrl() {
		return openDeliveryParcelUrl;
	}
	public void setOpenDeliveryParcelUrl(String openDeliveryParcelUrl) {
		this.openDeliveryParcelUrl = openDeliveryParcelUrl;
	}
	public String getOpenSignParcelUrl() {
		return openSignParcelUrl;
	}
	public void setOpenSignParcelUrl(String openSignParcelUrl) {
		this.openSignParcelUrl = openSignParcelUrl;
	}
	public String getOpenMarkParcelUrl() {
		return openMarkParcelUrl;
	}
	public void setOpenMarkParcelUrl(String openMarkParcelUrl) {
		this.openMarkParcelUrl = openMarkParcelUrl;
	}
	public void setFrontImgPath(String frontImgPath) {
		this.frontImgPath = frontImgPath;
	}
	public void setInHandImgPath(String inHandImgPath) {
		this.inHandImgPath = inHandImgPath;
	}
	public void setPromoContentUrl(String promoContentUrl) {
		this.promoContentUrl = promoContentUrl;
	}
	public void setCustomerUrl(String customerUrl) {
		this.customerUrl = customerUrl;
	}
	public void setNetSavePath(String netSavePath) {
		this.netSavePath = netSavePath;
	}
	public void setNetPicUrl(String netPicUrl) {
		this.netPicUrl = netPicUrl;
	}
	public void setOpencall(String opencall) {
		this.opencall = opencall;
	}
	public void setCommunityapiUrl(String communityapiUrl) {
		this.communityapiUrl = communityapiUrl;
	}
	public String getOpenApiParcelUrl() {
		return openApiParcelUrl;
	}
	public void setOpenApiParcelUrl(String openApiParcelUrl) {
		this.openApiParcelUrl = openApiParcelUrl;
	}
	public String getCommunityapiUrl() {
		return communityapiUrl;
	}
	public String getOpencustomerUrl() {
		return opencustomerUrl;
	}
	public void setOpencustomerUrl(String opencustomerUrl) {
		this.opencustomerUrl = opencustomerUrl;
	}
	public String getOpencall() {
		return opencall;
	}
	public String getNetSavePath() {
		return netSavePath;
	}
	public String getNetPicUrl() {
		return netPicUrl;
	} 
	public String getCustomerUrl() {
		return customerUrl;
	}
	public String getCallBackFhwPath() {
		return callBackFhwPath;
	}

	public void setCallBackFhwPath(String callBackFhwPath) {
		this.callBackFhwPath = callBackFhwPath;
	}

	public String getHeadImgPath() {
		return headImgPath;
	}

	public void setHeadImgPath(String headImgPath) {
		this.headImgPath = headImgPath;
	}

	public String getUserCenterUrl() {
		return userCenterUrl;
	}

	public void setUserCenterUrl(String userCenterUrl) {
		this.userCenterUrl = userCenterUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getValidateCenterUrl() {
		return validateCenterUrl;
	}

	public void setValidateCenterUrl(String validateCenterUrl) {
		this.validateCenterUrl = validateCenterUrl;
	}

	public String getRegisterCenterUrl() {
		return registerCenterUrl;
	}

	public void setRegisterCenterUrl(String registerCenterUrl) {
		this.registerCenterUrl = registerCenterUrl;
	}

	public String getExpCenterUrl() {
		return expCenterUrl;
	}

	public void setExpCenterUrl(String expCenterUrl) {
		this.expCenterUrl = expCenterUrl;
	}

	public String getUpdCenterUrl() {
		return updCenterUrl;
	}

	public void setUpdCenterUrl(String updCenterUrl) {
		this.updCenterUrl = updCenterUrl;
	}

	public String getPromoContentUrl() {
		return promoContentUrl;
	}

	public String getUcenterUrl() {
		return ucenterUrl;
	}

	public void setUcenterUrl(String ucenterUrl) {
		this.ucenterUrl = ucenterUrl;
	}

	public String getErpUrl() {
		return erpUrl;
	}

	public void setErpUrl(String erpUrl) {
		this.erpUrl = erpUrl;
	}

	public String getO2opubUrl() {
		return o2opubUrl;
	}

	public void setO2opubUrl(String o2opubUrl) {
		this.o2opubUrl = o2opubUrl;
	}
	public String getFrontImgPath() {
		return frontImgPath;
	}
	public String getInHandImgPath() {
		return inHandImgPath;
	}
	public String getSmsZtUrl() {
		return smsZtUrl;
	}
	public void setSmsZtUrl(String smsZtUrl) {
		this.smsZtUrl = smsZtUrl;
	}
	public String getReadPath() {
		return readPath;
	}
	public void setReadPath(String readPath) {
		this.readPath = readPath;
	}
	public String getExpressCardUrl() {
		return expressCardUrl;
	}
	public void setExpressCardUrl(String expressCardUrl) {
		this.expressCardUrl = expressCardUrl;
	}
	public String getOpenApiUrl() {
		return openApiUrl;
	}
	public void setOpenApiUrl(String openApiUrl) {
		this.openApiUrl = openApiUrl;
	}
	
}
