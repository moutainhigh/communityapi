package net.okdi.core.util.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("constPool")
public class ConstPool {

	@Value("${passPortUrl}")
	private String passPortUrl;

	@Value("${openApiUrl}")
	private String openApiUrl;
	
	@Value("${weiChatApiUrl}")
	private String weiChatApiUrl;
	
	@Value("${openApiParcelUrl}")
	private String openApiParcelUrl;
	
	@Value("${expBack}")
	private String expBack;
	
	@Value("${commExpressApiUrl}")
	private String commExpressApiUrl;
	
	@Value("${isThisPhoneLoginUrl}")
	private String isThisPhoneLoginUrl;
	
	@Value("${sms_service_http_url}")
	private String smsServiceHttpUrl;
	@Value("${smsService_http_url}")
	private String smsHttpUrl;
	@Value("${sms_service_http_url_Personal}")
	private String smsServiceHttpUrlPersonal;
	@Value("${sms_service_http_url_Express}")
	private String smsServiceHttpUrlExpress;
	@Value("${register_http_url}")
	private String registerHttpUrl;
	@Value("${financial_http_url}")
	private String financial;
	@Value("${o2oApiUrl}")
	private String o2oApiUrl;

	@Value("${login_auxiliary_queue}")
	private String loginAuxiliaryQueue;
	@Value("${login_auxiliary_key}")
	private String loginAuxiliaryKey;
	
	
	
	@Value("${net.pic.save.path}")
	private String netSavePath;
	
	@Value("${net.pic.url}")
	private String netPicUrl;
	
	@Value("${express.card}")
	private String cardUrl;//帖子图片url
	
	@Value("${opencustomerUrl}")
	private String opencustomerUrl;
	
	@Value("${callApiUrl}")
	private String callCourierOpenApiUrl;
	
	@Value("${express.community.savepath}")
	private String communitySavePath;
	
	@Value("${express.communityapi.url}")
	private String communityApiUrl;
	
	@Value("${im.url}")
	private String imUrl;

	@Value("${promo_url}")
    private String promoUrl;

	public String getNetSavePath() {
		return netSavePath;
	}
	
	public String getNetPicUrl() {
		return netPicUrl;
	}
	
	
	public String getLoginAuxiliaryQueue() {
		return loginAuxiliaryQueue;
	}
	public void setLoginAuxiliaryQueue(String loginAuxiliaryQueue) {
		this.loginAuxiliaryQueue = loginAuxiliaryQueue;
	}
	public String getLoginAuxiliaryKey() {
		return loginAuxiliaryKey;
	}
	public void setLoginAuxiliaryKey(String loginAuxiliaryKey) {
		this.loginAuxiliaryKey = loginAuxiliaryKey;
	}
	public String getSmsServiceHttpUrlPersonal() {
		return smsServiceHttpUrlPersonal;
	}
	public String getSmsServiceHttpUrlExpress() {
		return smsServiceHttpUrlExpress;
	}
	/**以下是图片地址
	 * contact.mob.picture.path=http://125.35.15.71:8080/images/contact
	#正面照
	front.id.mob.picture.path=http://125.35.15.71:8080/images/front
	#反面照
	back.id.mob.picture.path=http://125.35.15.71:8080/images/back
	#手持身份证
	inHand.id.mob.picture.path=http://125.35.15.71:8080/images/inHand
	#接单王头像地址
	head.express.mob.picture.path=http://125.35.15.71:8080/images/head
	 */
	@Value("${contact.mob.picture.path}")
	private String contact;
	@Value("${me.id.mob.picture.path}")
	private String Me;
	@Value("${front.id.mob.picture.path}")
	private String front;
	@Value("${back.id.mob.picture.path}")
	private String back;
	@Value("${inHand.id.mob.picture.path}")
	private String inHand;
	@Value("${head.express.mob.picture.path}")
	private String head;
	@Value("${net.express.mob.picture.path}")
	private String net;
	@Value("${picture.readPath}")
	private String readPath;
	@Value("${picture.savePath}")
	private String savePath;
	@Value("${photoPar.express.mob.pictrue.path}")
	private String photoPar;
	@Value("${payUrl}")
	private String payUrl;
	@Value("${pay_url}")
	private String newPayUrl;
	@Value("${sound.exp.path}")
	private String sound;
	
	@Value("${bank.picture.readPath}")
	private String bankImgReadPath;
	@Value("${picture.bank.path}")
	private String bankPicUrl;
	/**
	 * 以下是发送短信内容的相关地址
	 * 推广服务器地址（地图展示，加下载链接)
	 * okdi_http_url=http://www.okdit.net/parcelLocation.jsp
	 * 短链接服务器地址
	 * shortlink_http_url=http://shorturl.okdit.net/getShortUrl
	 * 长连接服务器地址
	 * longlink_http_url=http\://shorturl.okdit.net/u/
	 */
	@Value("${okdi_http_url}")
	private String okdiHttpUrl;
	@Value("${shortlink_http_url}")
	private String shortlinkHttpUrl;
	@Value("${longlink_http_url}")
	private String longlinkHttpUrl;
	@Value("${rabbitmq.message.routing.key}")
	private String routingKey;
	@Value("${ebusiness.Url}")
	private String ebusinessUrl;
	@Value("${rabbitmq.Housekeeper.routing.key}")
	private String rabbitmqHousekeeperRoutingKey;
	
	@Value("${sound_play_url}")
	private String soundPlayUrl;
	@Value("${wallte_url}")
	private String walletUrl;
	
	@Value("${customerUrl}")
	private String customerUrl;
	@Value("${openApiTakeUrl}")
	private String openApiTakeUrl;
	@Value("${relNameCert_url}")
	private String relNameCertUrl;
	
	@Value("${sms_start_time}")
	private String smsStartTime;
	
	@Value("${sms_end_time}")
	private String smsEndTime;
	@Value("${sms_cause}")
	private String smsCause;
	
	@Value("${customer_phone}")
	private String customerPhone;
	
	@Value("${customer_qq}")
	private String customerQq;
	
	
	

	public String getCustomerPhone() {
		return customerPhone;
	}

	public String getCustomerQq() {
		return customerQq;
	}

	public String getSmsCause() {
		return smsCause;
	}

	public void setSmsCause(String smsCause) {
		this.smsCause = smsCause;
	}

	public String getSmsStartTime() {
		return smsStartTime;
	}

	public void setSmsStartTime(String smsStartTime) {
		this.smsStartTime = smsStartTime;
	}

	public String getSmsEndTime() {
		return smsEndTime;
	}

	public void setSmsEndTime(String smsEndTime) {
		this.smsEndTime = smsEndTime;
	}

	public String getRelNameCertUrl() {
		return relNameCertUrl;
	}

	public String getOpenApiTakeUrl() {
		return openApiTakeUrl;
	}

	public String getWalletUrl() {
		return walletUrl;
	}
	public void setWalletUrl(String walletUrl) {
		this.walletUrl = walletUrl;
	}
	public String getRabbitmqHousekeeperRoutingKey() {
		return rabbitmqHousekeeperRoutingKey;
	}
	public String getEbusinessUrl() {
		return ebusinessUrl;
	}
	public void setEbusinessUrl(String ebusinessUrl) {
		this.ebusinessUrl = ebusinessUrl;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	public String getExpBack() {
		return expBack;
	}
	public void setExpBack(String expBack) {
		this.expBack = expBack;
	}
	public String getPassPortUrl() {
		return passPortUrl;
	}
	public String getOpenApiUrl() {
		return openApiUrl;
	}
	public String getIsThisPhoneLoginUrl() {
		return isThisPhoneLoginUrl;
	}
	public String getContact() {
		return contact;
	}
	public String getFront() {
		return front;
	}
	public String getBack() {
		return back;
	}
	public String getInHand() {
		return inHand;
	}
	public String getHead() {
		return head;
	}
	public String getNet() {
		return net;
	}
	public String getReadPath() {
		return readPath;
	}
	public String getSavePath() {
		return savePath;
	}
	public String getPhotoPar() {
		return photoPar;
	}
	public String getOkdiHttpUrl() {
		return okdiHttpUrl;
	}
	public String getShortlinkHttpUrl() {
		return shortlinkHttpUrl;
	}
	public String getLonglinkHttpUrl() {
		return longlinkHttpUrl;
	}
	public String getSmsServiceHttpUrl() {
		return smsServiceHttpUrl;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getSound() {
		return sound;
	}
	public String getSoundPlayUrl() {
		return soundPlayUrl;
	}
	public String getRegisterHttpUrl() {
		return registerHttpUrl;
	}
	public void setRegisterHttpUrl(String registerHttpUrl) {
		this.registerHttpUrl = registerHttpUrl;
	}
	public String getCustomerUrl() {
		return customerUrl;
	}
	public String getSmsHttpUrl() {
		return smsHttpUrl;
	}
	public String getFinancial() {
		return financial;
	}
	public String getO2oApiUrl() {
		return o2oApiUrl;
	}
	public String getMe() {
		return Me;
	}
	public void setMe(String me) {
		Me = me;
	}
	public String getCommExpressApiUrl() {
		return commExpressApiUrl;
	}
	public void setCommExpressApiUrl(String commExpressApiUrl) {
		this.commExpressApiUrl = commExpressApiUrl;
	}

	public String getBankImgReadPath() {
		return bankImgReadPath;
	}

	public void setBankImgReadPath(String bankImgReadPath) {
		this.bankImgReadPath = bankImgReadPath;
	}

	public String getBankPicUrl() {
		return bankPicUrl;
	}

	public void setBankPicUrl(String bankPicUrl) {
		this.bankPicUrl = bankPicUrl;
	}

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public String getNewPayUrl() {
		return newPayUrl;
	}

	public void setNewPayUrl(String newPayUrl) {
		this.newPayUrl = newPayUrl;
	}

	public String getOpenApiParcelUrl() {
		return openApiParcelUrl;
	}

	public void setOpenApiParcelUrl(String openApiParcelUrl) {
		this.openApiParcelUrl = openApiParcelUrl;
	}

	public String getOpencustomerUrl() {
		return opencustomerUrl;
	}

	public void setOpencustomerUrl(String opencustomerUrl) {
		this.opencustomerUrl = opencustomerUrl;
	}

	public String getCallCourierOpenApiUrl() {
		return callCourierOpenApiUrl;
	}

	public void setCallCourierOpenApiUrl(String callCourierOpenApiUrl) {
		this.callCourierOpenApiUrl = callCourierOpenApiUrl;
	}

	public String getCommunitySavePath() {
		return communitySavePath;
	}

	public void setCommunitySavePath(String communitySavePath) {
		this.communitySavePath = communitySavePath;
	}

	public String getCommunityApiUrl() {
		return communityApiUrl;
	}

	public void setCommunityApiUrl(String communityApiUrl) {
		this.communityApiUrl = communityApiUrl;
	}

	public String getImUrl() {
		return imUrl;
	}

	public void setImUrl(String imUrl) {
		this.imUrl = imUrl;
	}

	public String getWeiChatApiUrl() {
		return weiChatApiUrl;
	}

	public void setWeiChatApiUrl(String weiChatApiUrl) {
		this.weiChatApiUrl = weiChatApiUrl;
	}

    public String getPromoUrl() {
        return promoUrl;
    }

    public void setPromoUrl(String promoUrl) {
        this.promoUrl = promoUrl;
    }
}
