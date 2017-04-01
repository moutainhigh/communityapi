package net.okdi.api.vo;

import java.math.BigDecimal;
import java.util.Date;

import net.okdi.api.entity.ParParceladdress;

public class VO_ParcelInfoAndAddressInfo extends ParParceladdress{
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
	private Date freightPaymentTime; //费用支付时间 费用支付时间；取件时以收派员完成取件时间，派件时以派件签收时间；
	private Long serviceId; //快递公司服务产品ID 快递产品ID，结算用
	private Short tackingStatus; //包裹当前状态 0:在途,未签收 1:已签收
	private Short signResult; //签收结果 0：未签收/拒签 1：签收
	private String signMember; //签收人
	private Date signTime; //签收时间
	private BigDecimal signGoodsTotal; //签收金额
	private Long createUserId; //包裹创建人
	private Long compId; //所属站点
	private Long netId; //所属网络
	private Date createTime; //包裹录入时间
	private String signImgUrl; //上传的图片路径
	private String parcelType; //包裹类型，1：包裹，2：文件
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
	private Short parcelStatus;//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'

	public String getExpWaybillNum() {
		return expWaybillNum;
	}
	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
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
		this.ordOfSellerId = ordOfSellerId;
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
		this.parcelRemark = parcelRemark;
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
		this.signMember = signMember;
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
		this.signImgUrl = signImgUrl;
	}
	public String getParcelType() {
		return parcelType;
	}
	public void setParcelType(String parcelType) {
		this.parcelType = parcelType;
	}
	public String getParcelEndMark() {
		return parcelEndMark;
	}
	public void setParcelEndMark(String parcelEndMark) {
		this.parcelEndMark = parcelEndMark;
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
		this.goodsDesc = goodsDesc;
	}
	public Short getParcelStatus() {
		return parcelStatus;
	}
	public void setParcelStatus(Short parcelStatus) {
		this.parcelStatus = parcelStatus;
	}

	
	
	
}
