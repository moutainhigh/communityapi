package net.okdi.apiV4.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
@Document(collection="parParcelinfo")
public class ParParcelinfo {
	private Long uid;
	private String expWaybillNum; //包裹运单号
	private Short senderType; //发件人类型 1:发货商家,2:好递个人
	private Long senderCompId; //发货方商家ID
	private Long senderUserId; //发货方客户ID
	private String ordOfSellerId; //发货商订单号
	private BigDecimal totalGoodsAmount; //商品货款合计
	private BigDecimal insureAmount; //保价金额
	private BigDecimal tipAmount; //抢单发快递的小费
	private BigDecimal chareWeightForsender; //计费重量（销售）
	private BigDecimal chareWeightFortransit; //计费重量（采购）
	private BigDecimal parcelVolume; //包裹初始体积
	private Integer goodsNum; //产品个数
	private String parcelRemark; //包裹备注
	private Short goodsPaymentMethod; //货款支付方式 0:不代收付货款,1:上门代收付(COD)
	private BigDecimal codAmount; //代收货款金额
	private Short codIsRecovery; //代收货款是否收回 0：代收货款未收回 1：代收货款已收回
	private BigDecimal actualCodAmount; //代收货款实际收到的货款金额
	private Short freightPaymentMethod; //应收运费支付方式 0：发件方现结,1：发件方月节,2：收件方到付
	private BigDecimal freight; //应收运费
	private Short freightPaymentStatus; //费用结算状态 1：yes 运费已收 0:no 运费未收
	private Short paymentMethod; //费用付款方式 0：现金,1：POS机
	private Short goodsPaymentStatus; //货款与发件人结算状态  1：yes 货款已结给发件人 0:no 货款未付给发件人
	@DateTimeFormat
	private Date freightPaymentTime; //费用支付时间 费用支付时间；取件时以收派员完成取件时间，派件时以派件签收时间；
	private Long serviceId; //快递公司服务产品ID 快递产品ID，结算用
	private Short tackingStatus; //包裹当前状态 0:在途,未签收 1:已签收
	private Short signResult; //签收结果 0：未签收/拒签 1：正常签收-个人签2：派而不签-派（正常签收，他人签收）3：转代收-转（正常签收）16 系统签收
	private String signMember; //签收人
	@DateTimeFormat
	private Date signTime; //签收时间
	private BigDecimal signGoodsTotal; //签收金额
	private Long createUserId; //包裹创建人
	private Long compId; //所属站点
	private Long netId; //所属网络
	@DateTimeFormat
	private Date createTime; //包裹录入时间
	private String signImgUrl; //上传的图片路径
	private String parcelType; //包裹类型，1：包裹，2：文件，3手机等
	private String parcelEndMark; //包裹结束标志 0：未结束 1：结束
	private Long takeTaskId; //取件任务ID
	private Long sendTaskId; //派件任务ID  
	private Long actualTakeMember; //实际取件人ID
	private Long actualSendMember; //实际派件人ID
	private Long receiptId; //付款收据ID
	private Short printFlag; //打印标记 0：未打印，1：已打印
	private Short noFly; //禁航件 1：yes 禁航 0:no 非禁航
	private BigDecimal packingCharges; //包装费
	private BigDecimal pricePremium; //保价费
	private String goodsDesc; //产品描述
	private Short parcelStatus;//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'12:派件异常
	private String disposalDesc;
	@DateTimeFormat
	private Date exceptionTime;
	@DateTimeFormat
	private Date pickupTime;//取件时间
	
	//------------2016年4月20日17:04:39--by hu.zhao--用于群发通知生产派件任务------//
	private String errorMessage;//异常信息
	private String parcelNum;//包裹编号
	private Short sendSmsType;//群发通知类型：2.电话优先 3.仅电话 1.仅短信
	private Short sendSmsStatus;//群发通知状态  0.发送成功  1.呼叫成功  2.重复  3.呼叫失败  4.发送失败   5.用户退订 6.拦截
	private Short callBackStatus;//群发通知回执状态 0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败 6.短信转通道
	private Short replyStatus;  //回复状态      0.未回复  1.客户回复  2.回复已读
	//------------2016年4月20日17:04:39--by hu.zhao---//
	
	//--------------------2016年5月16日---用于代收点自添加包裹微信支付用-----------------//
	private Long  cpTakeTaskId;//代收点自添加包裹生成的任务ID
	private Short cpReceiptStatus ;//代收点自添加包裹，代收点的收款状态; 0 未付   1 已付
	
	//---------------------------------------------------------------------------//
	//------------2016年5月18日10:05:48--by hu.zhao--用于派件列表重派标识------//
	private Short isAgainSend;//是否是重派的标识，空值为正常待派，1为重派待派
	//------------2016年5月21日15:33:23--by hu.zhao-------//
	private Short isSendMsg;//是否发送过短信，0否1是
	
	private String mobilePhone; //手机号--片键--2016年5月26日10:11:29
	
	private Short isRepeat; //0.不重复  1.重复
	//----------------2017年1月11日-----------------------------------//
	private String  code;   //取件码
	
	private String comment;//  标记备注    如：易碎 ， 易爆

    private String shipmentRemark; // 发运备注 1:已发运 0或空表示未发运 
    private Date shipmentTime; // 发运时间
			
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Short getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(Short isRepeat) {
		this.isRepeat = isRepeat;
	}

	public Short getIsAgainSend() {
		return isAgainSend;
	}

	public void setIsAgainSend(Short isAgainSend) {
		this.isAgainSend = isAgainSend;
	}

	public Long getCpTakeTaskId() {
		return cpTakeTaskId;
	}

	public void setCpTakeTaskId(Long cpTakeTaskId) {
		this.cpTakeTaskId = cpTakeTaskId;
	}

	public Short getCpReceiptStatus() {
		return cpReceiptStatus;
	}

	public void setCpReceiptStatus(Short cpReceiptStatus) {
		this.cpReceiptStatus = cpReceiptStatus;
	}

	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getParcelNum() {
		return parcelNum;
	}


	public void setParcelNum(String parcelNum) {
		this.parcelNum = parcelNum;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public Date getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public String getDisposalDesc() {
		return disposalDesc;
	}

	public void setDisposalDesc(String disposalDesc) {
		this.disposalDesc = disposalDesc;
	}


	public String getExpWaybillNum() {
		return expWaybillNum;
	}

	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum == null ? null : expWaybillNum
				.trim();
	}

	public Short getSenderType() {
		return senderType;
	}

	public void setSenderType(Short senderType) {
		this.senderType = senderType;
	}
	public Long getSenderCompId() {
		return senderCompId;
	}

	public void setSenderCompId(Long senderCompId) {
		this.senderCompId = senderCompId;
	}

	public Long getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(Long senderUserId) {
		this.senderUserId = senderUserId;
	}

	public String getOrdOfSellerId() {
		return ordOfSellerId;
	}

	public void setOrdOfSellerId(String ordOfSellerId) {
		this.ordOfSellerId = ordOfSellerId == null ? null : ordOfSellerId
				.trim();
	}
	public BigDecimal getTotalGoodsAmount() {
		return totalGoodsAmount;
	}

	public void setTotalGoodsAmount(BigDecimal totalGoodsAmount) {
		this.totalGoodsAmount = totalGoodsAmount;
	}

	public BigDecimal getInsureAmount() {
		return insureAmount;
	}

	public void setInsureAmount(BigDecimal insureAmount) {
		this.insureAmount = insureAmount;
	}

	public BigDecimal getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(BigDecimal tipAmount) {
		this.tipAmount = tipAmount;
	}

	public BigDecimal getChareWeightForsender() {
		return chareWeightForsender;
	}

	public void setChareWeightForsender(BigDecimal chareWeightForsender) {
		this.chareWeightForsender = chareWeightForsender;
	}

	public BigDecimal getChareWeightFortransit() {
		return chareWeightFortransit;
	}

	public void setChareWeightFortransit(BigDecimal chareWeightFortransit) {
		this.chareWeightFortransit = chareWeightFortransit;
	}

	public BigDecimal getParcelVolume() {
		return parcelVolume;
	}

	public void setParcelVolume(BigDecimal parcelVolume) {
		this.parcelVolume = parcelVolume;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getParcelRemark() {
		return parcelRemark;
	}
	public void setParcelRemark(String parcelRemark) {
		this.parcelRemark = parcelRemark == null ? null : parcelRemark.trim();
	}
	public Short getGoodsPaymentMethod() {
		return goodsPaymentMethod;
	}
	public void setGoodsPaymentMethod(Short goodsPaymentMethod) {
		this.goodsPaymentMethod = goodsPaymentMethod;
	}
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	public Short getCodIsRecovery() {
		return codIsRecovery;
	}
	public void setCodIsRecovery(Short codIsRecovery) {
		this.codIsRecovery = codIsRecovery;
	}
	public BigDecimal getActualCodAmount() {
		return actualCodAmount;
	}
	public void setActualCodAmount(BigDecimal actualCodAmount) {
		this.actualCodAmount = actualCodAmount;
	}
	public Short getFreightPaymentMethod() {
		return freightPaymentMethod;
	}
	public void setFreightPaymentMethod(Short freightPaymentMethod) {
		this.freightPaymentMethod = freightPaymentMethod;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Short getFreightPaymentStatus() {
		return freightPaymentStatus;
	}
	public void setFreightPaymentStatus(Short freightPaymentStatus) {
		this.freightPaymentStatus = freightPaymentStatus;
	}
	public Short getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Short paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Short getGoodsPaymentStatus() {
		return goodsPaymentStatus;
	}
	public void setGoodsPaymentStatus(Short goodsPaymentStatus) {
		this.goodsPaymentStatus = goodsPaymentStatus;
	}
	public Date getFreightPaymentTime() {
		return freightPaymentTime;
	}
	public void setFreightPaymentTime(Date freightPaymentTime) {
		this.freightPaymentTime = freightPaymentTime;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Short getTackingStatus() {
		return tackingStatus;
	}
	public void setTackingStatus(Short tackingStatus) {
		this.tackingStatus = tackingStatus;
	}
	public Short getSignResult() {
		return signResult;
	}
	public void setSignResult(Short signResult) {
		this.signResult = signResult;
	}
	public String getSignMember() {
		return signMember;
	}
	public void setSignMember(String signMember) {
		this.signMember = signMember == null ? null : signMember.trim();
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public BigDecimal getSignGoodsTotal() {
		return signGoodsTotal;
	}
	public void setSignGoodsTotal(BigDecimal signGoodsTotal) {
		this.signGoodsTotal = signGoodsTotal;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getCompId() {
		return compId;
	}
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	public Long getNetId() {
		return netId;
	}
	public void setNetId(Long netId) {
		this.netId = netId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSignImgUrl() {
		return signImgUrl;
	}
	public void setSignImgUrl(String signImgUrl) {
		this.signImgUrl = signImgUrl == null ? null : signImgUrl.trim();
	}
	public String getParcelType() {
		return parcelType;
	}
	public void setParcelType(String parcelType) {
		this.parcelType = parcelType == null ? null : parcelType.trim();
	}
	public String getParcelEndMark() {
		return parcelEndMark;
	}
	public void setParcelEndMark(String parcelEndMark) {
		this.parcelEndMark = parcelEndMark == null ? null : parcelEndMark
				.trim();
	}
	public Long getTakeTaskId() {
		return takeTaskId;
	}
	public void setTakeTaskId(Long takeTaskId) {
		this.takeTaskId = takeTaskId;
	}
	public Long getSendTaskId() {
		return sendTaskId;
	}
	public void setSendTaskId(Long sendTaskId) {
		this.sendTaskId = sendTaskId;
	}
	public Long getActualTakeMember() {
		return actualTakeMember;
	}
	public void setActualTakeMember(Long actualTakeMember) {
		this.actualTakeMember = actualTakeMember;
	}
	public Long getActualSendMember() {
		return actualSendMember;
	}
	public void setActualSendMember(Long actualSendMember) {
		this.actualSendMember = actualSendMember;
	}
	public Long getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}
	public Short getPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(Short printFlag) {
		this.printFlag = printFlag;
	}
	public Short getNoFly() {
		return noFly;
	}
	public void setNoFly(Short noFly) {
		this.noFly = noFly;
	}
	public BigDecimal getPackingCharges() {
		return packingCharges;
	}
	public void setPackingCharges(BigDecimal packingCharges) {
		this.packingCharges = packingCharges;
	}
	public BigDecimal getPricePremium() {
		return pricePremium;
	}
	public void setPricePremium(BigDecimal pricePremium) {
		this.pricePremium = pricePremium;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
	}

	public Short getParcelStatus() {
		return parcelStatus;
	}

	public void setParcelStatus(Short parcelStatus) {
		this.parcelStatus = parcelStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Short getSendSmsType() {
		return sendSmsType;
	}

	public void setSendSmsType(Short sendSmsType) {
		this.sendSmsType = sendSmsType;
	}

	public Short getSendSmsStatus() {
		return sendSmsStatus;
	}

	public void setSendSmsStatus(Short sendSmsStatus) {
		this.sendSmsStatus = sendSmsStatus;
	}

	public Short getCallBackStatus() {
		return callBackStatus;
	}

	public void setCallBackStatus(Short callBackStatus) {
		this.callBackStatus = callBackStatus;
	}

	public Short getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Short replyStatus) {
		this.replyStatus = replyStatus;
	}

	public Short getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(Short isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

    public String getShipmentRemark() {
        return shipmentRemark;
    }

    public void setShipmentRemark(String shipmentRemark) {
        this.shipmentRemark = shipmentRemark;
    }

    public Date getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(Date shipmentTime) {
        this.shipmentTime = shipmentTime;
    }
}