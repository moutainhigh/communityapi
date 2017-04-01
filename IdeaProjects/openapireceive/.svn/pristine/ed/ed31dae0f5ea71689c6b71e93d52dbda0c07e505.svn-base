package net.okdi.apiV4.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 包裹表主表
 */
@Document(collection="parParceladdress")
public class ParParceladdress {
	private Long uid; //片键
	private String sendName; //发件人姓名
	private Long sendAddressId; //发件乡镇ID
	private String sendAddress; //发件详细地址  整个完整地址
	private String sendMobile; //发件人手机
	private String sendPhone; //发件人电话
	private String sendZipcode; //发件地址邮编
	private Long sendCasUserId; //发件人CASID
	private Long sendCustomerId; //发件客户ID
	private Long sendContactId; //发件联系人ID
	//--------因国通而加-----新增------------------------------
	private String sendProv ;//发件人省份
	private String sendCity ;//发件人市县(区)
	private String sendDetailed  ;//发件人详细地址（只有地址后部）
		
	private String addresseeProv ;//收件人省份
	private String addresseeCity ;//收件人市县(区)
	private String addresseeDetailed  ;//收件人详细地址（只有地址后部）
	//--------------------------------------------------
	private String addresseeName; //收件人姓名
	private Long addresseeAddressId; //收件乡镇ID
	private String addresseeAddress; //收件详细地址   整个完整地址
	private String addresseeMobile; //收件人手机  ---片键--2016年5月26日10:12:28
	private String addresseePhone; //收件人电话
	private String addresseeZipcode; //收件地址邮编
	private Long addresseeCasUserId; //收件人CASID
	private Long addresseeCustomerId; //收件客户ID
	private Long addresseeContactId; //收件联系人ID
	private Long agencySiteId; //包裹代收点ID
	private BigDecimal sendLongitude; //发件人经度
	private BigDecimal sendLatitude; //发件人纬度
	private BigDecimal addresseeLongitude; //收件人经度
	private BigDecimal addresseeLatitude; //收件人纬度
	private Date createTime;//地址创建时间
	//-------2017-2-15--------------------------------------------------//
	private String expWaybillNum; //包裹运单号 加索引
	private Long compId; //所属站点
	private Long netId; //所属网络
	private String netName; //网络名称
	/**
	 * 揽收码（必填）好递平台揽散件时自动生成的，规则:日+三位流水号+memberId(好递平台ID) 如:1600188888888888
	 * 国通所需揽收码  fetchcode = code + takeMemberId
	 */
	private String  code;   //单独揽收码  日+三位流水号 16001在页面展示
	private Long takeMemberId; //第一次 包裹揽收人ID不等于物权所有人(下单人)
	
	private String fetchcode; //国通样式的揽收码    code+netId+memberId(拼接) 
	
	@DateTimeFormat
	private Date pickupTime;//取件时间
	
	private Long  precontrollermemberid; //物权原有人id
	private String  controllerName; //物权所有人 发货后 "干线运输"
	private Long  controllerMemberId; //物权所有人id
    private String controllerNetName; //物权网络名称
    private Long controllerNetId; //物权网络id
    private Long controllerCompId; //物权站点id 代收点id
    private String controllerCompName; //物权站点名称
//  注释掉的这个字段的描述是老板下一版本要求的状态，到时候再改的时候看情况吧 (￣∇￣)
//	private Short controllerTaskFlag;//0:未从客户取件-(待取件);1:已从客户(代收点)取件-(待发货); 2:分拨中心取件-(待发货)；10：站点或代收点从分拨中心取件，或代收点从站点取件(待派件);11：已派件(标记签收或扫码签收) 12:派件异常
    private Short controllerTaskFlag;//0:待取件; 1:已取件(未发货); 2:已发货; 10:待派件; 11:已派件; 12:派件异常;

	private List<ContentHis> content;   //包裹标记备注
	private Short packageflag;   //0未标记备注  1已标记备注（大头钉）
	
	//2017-1-4新增
	private String marker;//大头笔  好比站点区号 每个快递公司不同
	private String bourn;//目的地  省市区

    private String shipmentRemark; // 发运备注 1:已发运 0或空表示未发运
    private Date shipmentTime; // 发运时间
    //-----------2017-3-10----------------------------------------- 
    /**
     *（揽散件）已下单方式上传国通成功： 1、失败：0
     */
    private Short uploadTF;
    /**
     * 揽收任务执行人id
     */
    private Long receiveTaskUserId;
    /**
     * 揽收任务执行人编码
     */
    private String  receiveTaskUserCode;  
    /**
     * 电商平台
     */
    private String  originPlatform;   
    /**
     * 包裹重量
     */
    private String pacelWeight;
    /**
     * 物品（包裹）类型：包裹，文件，手机等
     */
    private String parcelType;   
    /**
     * 航空标记（H航空，N普通）
     */
    private String hkSign;  
    /**
     * 邮包id
     */
    private Long postbagId;  
    /**
     * 服务类型
     */
    private String serviceName;   
    /**
     * 包裹当前所属车辆车号
     */
    private String addressCarNumber;
    /**
     * 收件地址所在网点id
     */
    private Long addressNetId;
    /**
     * 收件地址所在网点名称
     */
    private String addressNetName;
    /**
     * 收件地址所在网点代码
     */
    private String addressNetCode;
    /**
     * 收件地址所在网点城市
     */
    private String addressNetCity;
    /**
     * 收件地址所属快递员ID
     */
    private Long addressExpMid;
    /**
     * 收件地址所属快递员姓名
     */
    private String addressExpMname;
    /**
     * 收件地址所属快递员 编号
     */
    private String addressExpNumber;
    /**
     * 收件人所在地的站点编码（必填）
     */
    private String addressOrgCode;
    /**
     * 收件人所在地的快递员编码（必填）
     */
    private String addressUserCode;
    /**
     * 寄件人所在地的站点编码（必填）
     */
    private String sendOrgCode;
    /**
     * 寄件人所在地的快递员编码（必填）
     */
    private String sendUserCode;
	 /**
	  * 物流订单号（给快递公司）好递任务订单号
	  */
    private String txlogisticid;
    /**
     * 快递公司订单ID(国通)
     */
    private String tid;
    
    /**
     * 包裹出处标识(国通)
     */
    private String parFlag;//包裹标识:1:国通-推送,2:国通-查询
    
	public Short getUploadTF() {
		return uploadTF;
	}

	public void setUploadTF(Short uploadTF) {
		this.uploadTF = uploadTF;
	}

	public String getSendDetailed() {
		return sendDetailed;
	}

	public void setSendDetailed(String sendDetailed) {
		this.sendDetailed = sendDetailed;
	}

	public String getAddresseeDetailed() {
		return addresseeDetailed;
	}

	public void setAddresseeDetailed(String addresseeDetailed) {
		this.addresseeDetailed = addresseeDetailed;
	}

    
	public String getParFlag() {
		return parFlag;
	}

	public void setParFlag(String parFlag) {
		this.parFlag = parFlag;
	}

	public String getTxlogisticid() {
		return txlogisticid;
	}

	public void setTxlogisticid(String txlogisticid) {
		this.txlogisticid = txlogisticid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getFetchcode() {
		return fetchcode;
	}

	public void setFetchcode(String fetchcode) {
		this.fetchcode = fetchcode;
	}

    

	public String getSendOrgCode() {
		return sendOrgCode;
	}

	public void setSendOrgCode(String sendOrgCode) {
		this.sendOrgCode = sendOrgCode;
	}

	public String getSendUserCode() {
		return sendUserCode;
	}

	public void setSendUserCode(String sendUserCode) {
		this.sendUserCode = sendUserCode;
	}

	public String getAddressOrgCode() {
		return addressOrgCode;
	}

	public void setAddressOrgCode(String addressOrgCode) {
		this.addressOrgCode = addressOrgCode;
	}

	public String getAddressUserCode() {
		return addressUserCode;
	}

	public void setAddressUserCode(String addressUserCode) {
		this.addressUserCode = addressUserCode;
	}

	
	public String getHkSign() {
		return hkSign;
	}

	public void setHkSign(String hkSign) {
		this.hkSign = hkSign;
	}

	public String getOriginPlatform() {
		return originPlatform;
	}

	public void setOriginPlatform(String originPlatform) {
		this.originPlatform = originPlatform;
	}

	public Long getReceiveTaskUserId() {
		return receiveTaskUserId;
	}

	public void setReceiveTaskUserId(Long receiveTaskUserId) {
		this.receiveTaskUserId = receiveTaskUserId;
	}

	public String getReceiveTaskUserCode() {
		return receiveTaskUserCode;
	}

	public void setReceiveTaskUserCode(String receiveTaskUserCode) {
		this.receiveTaskUserCode = receiveTaskUserCode;
	}

	public String getSendProv() {
		return sendProv;
	}

	public void setSendProv(String sendProv) {
		this.sendProv = sendProv;
	}

	public String getSendCity() {
		return sendCity;
	}

	public void setSendCity(String sendCity) {
		this.sendCity = sendCity;
	}

	public String getAddresseeProv() {
		return addresseeProv;
	}

	public void setAddresseeProv(String addresseeProv) {
		this.addresseeProv = addresseeProv;
	}

	public String getAddresseeCity() {
		return addresseeCity;
	}

	public void setAddresseeCity(String addresseeCity) {
		this.addresseeCity = addresseeCity;
	}

	public String getPacelWeight() {
		return pacelWeight;
	}

	public void setPacelWeight(String pacelWeight) {
		this.pacelWeight = pacelWeight;
	}

	public String getParcelType() {
		return parcelType;
	}

	public void setParcelType(String parcelType) {
		this.parcelType = parcelType;
	}

	public Long getPostbagId() {
		return postbagId;
	}

	public void setPostbagId(Long postbagId) {
		this.postbagId = postbagId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAddressCarNumber() {
		return addressCarNumber;
	}

	public void setAddressCarNumber(String addressCarNumber) {
		this.addressCarNumber = addressCarNumber;
	}

	public Long getAddressNetId() {
		return addressNetId;
	}

	public void setAddressNetId(Long addressNetId) {
		this.addressNetId = addressNetId;
	}

	public String getAddressNetName() {
		return addressNetName;
	}

	public void setAddressNetName(String addressNetName) {
		this.addressNetName = addressNetName;
	}

	public String getAddressNetCode() {
		return addressNetCode;
	}

	public void setAddressNetCode(String addressNetCode) {
		this.addressNetCode = addressNetCode;
	}

	public String getAddressNetCity() {
		return addressNetCity;
	}

	public void setAddressNetCity(String addressNetCity) {
		this.addressNetCity = addressNetCity;
	}

	public Long getAddressExpMid() {
		return addressExpMid;
	}

	public void setAddressExpMid(Long addressExpMid) {
		this.addressExpMid = addressExpMid;
	}

	public String getAddressExpMname() {
		return addressExpMname;
	}

	public void setAddressExpMname(String addressExpMname) {
		this.addressExpMname = addressExpMname;
	}

	public String getAddressExpNumber() {
		return addressExpNumber;
	}

	public void setAddressExpNumber(String addressExpNumber) {
		this.addressExpNumber = addressExpNumber;
	}

	public Long getPrecontrollermemberid() {
		return precontrollermemberid;
	}

	public void setPrecontrollermemberid(Long precontrollermemberid) {
		this.precontrollermemberid = precontrollermemberid;
	}

	public Short getPackageflag() {
		return packageflag;
	}

	public void setPackageflag(Short packageflag) {
		this.packageflag = packageflag;
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

    public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public Long getControllerMemberId() {
		return controllerMemberId;
	}
	public void setControllerMemberId(Long controllerMemberId) {
		this.controllerMemberId = controllerMemberId;
	}
	public Date getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}
	public List<ContentHis> getContent() {
		return content;
	}
	public void setContent(List<ContentHis> content) {
		this.content = content;
	}
	public String getExpWaybillNum() {
		return expWaybillNum;
	}
	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
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
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Long getTakeMemberId() {
		return takeMemberId;
	}
	public void setTakeMemberId(Long takeMemberId) {
		this.takeMemberId = takeMemberId;
	}
	
	
	public Short getControllerTaskFlag() {
		return controllerTaskFlag;
	}
	public void setControllerTaskFlag(Short controllerTaskFlag) {
		this.controllerTaskFlag = controllerTaskFlag;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getBourn() {
		return bourn;
	}
	public void setBourn(String bourn) {
		this.bourn = bourn;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName == null ? null : sendName.trim();
	}
	public Long getSendAddressId() {
		return sendAddressId;
	}
	public void setSendAddressId(Long sendAddressId) {
		this.sendAddressId = sendAddressId;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress == null ? null : sendAddress.trim();
	}
	public String getSendMobile() {
		return sendMobile;
	}
	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile == null ? null : sendMobile.trim();
	}
	public String getSendPhone() {
		return sendPhone;
	}
	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone == null ? null : sendPhone.trim();
	}
	public String getSendZipcode() {
		return sendZipcode;
	}
	public void setSendZipcode(String sendZipcode) {
		this.sendZipcode = sendZipcode == null ? null : sendZipcode.trim();
	}
	public Long getSendCasUserId() {
		return sendCasUserId;
	}
	public void setSendCasUserId(Long sendCasUserId) {
		this.sendCasUserId = sendCasUserId;
	}
	public Long getSendCustomerId() {
		return sendCustomerId;
	}
	public void setSendCustomerId(Long sendCustomerId) {
		this.sendCustomerId = sendCustomerId;
	}
	public Long getSendContactId() {
		return sendContactId;
	}
	public void setSendContactId(Long sendContactId) {
		this.sendContactId = sendContactId;
	}
	public String getAddresseeName() {
		return addresseeName;
	}
	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName == null ? null : addresseeName
				.trim();
	}
	public Long getAddresseeAddressId() {
		return addresseeAddressId;
	}
	public void setAddresseeAddressId(Long addresseeAddressId) {
		this.addresseeAddressId = addresseeAddressId;
	}
	public String getAddresseeAddress() {
		return addresseeAddress;
	}
	public void setAddresseeAddress(String addresseeAddress) {
		this.addresseeAddress = addresseeAddress == null ? null
				: addresseeAddress;
	}
	public String getAddresseeMobile() {
		return addresseeMobile;
	}
	public void setAddresseeMobile(String addresseeMobile) {
		this.addresseeMobile = addresseeMobile == null ? null : addresseeMobile
				.trim();
	}
	public String getAddresseePhone() {
		return addresseePhone;
	}
	public void setAddresseePhone(String addresseePhone) {
		this.addresseePhone = addresseePhone == null ? null : addresseePhone
				.trim();
	}
	public String getAddresseeZipcode() {
		return addresseeZipcode;
	}
	public void setAddresseeZipcode(String addresseeZipcode) {
		this.addresseeZipcode = addresseeZipcode == null ? null
				: addresseeZipcode.trim();
	}
	public Long getAddresseeCasUserId() {
		return addresseeCasUserId;
	}
	public void setAddresseeCasUserId(Long addresseeCasUserId) {
		this.addresseeCasUserId = addresseeCasUserId;
	}
	public Long getAddresseeCustomerId() {
		return addresseeCustomerId;
	}
	public void setAddresseeCustomerId(Long addresseeCustomerId) {
		this.addresseeCustomerId = addresseeCustomerId;
	}
	public Long getAddresseeContactId() {
		return addresseeContactId;
	}
	public void setAddresseeContactId(Long addresseeContactId) {
		this.addresseeContactId = addresseeContactId;
	}
	public Long getAgencySiteId() {
		return agencySiteId;
	}

	public void setAgencySiteId(Long agencySiteId) {
		this.agencySiteId = agencySiteId;
	}
	public BigDecimal getSendLongitude() {
		return sendLongitude;
	}
	public void setSendLongitude(BigDecimal sendLongitude) {
		this.sendLongitude = sendLongitude;
	}
	public BigDecimal getSendLatitude() {
		return sendLatitude;
	}
	public void setSendLatitude(BigDecimal sendLatitude) {
		this.sendLatitude = sendLatitude;
	}
	public BigDecimal getAddresseeLongitude() {
		return addresseeLongitude;
	}
	public void setAddresseeLongitude(BigDecimal addresseeLongitude) {
		this.addresseeLongitude = addresseeLongitude;
	}
	public BigDecimal getAddresseeLatitude() {
		return addresseeLatitude;
	}
	public void setAddresseeLatitude(BigDecimal addresseeLatitude) {
		this.addresseeLatitude = addresseeLatitude;
	}
	
    public void addContent(String contentStr, String name) {
        if (content == null) {
            content = new ArrayList<>();
        }
        ContentHis contentHis = new ContentHis(contentStr, name, System.currentTimeMillis());
        content.add(contentHis);
    }

    public String getControllerNetName() {
        return controllerNetName;
    }

    public void setControllerNetName(String controllerNetName) {
        this.controllerNetName = controllerNetName;
    }

    public Long getControllerNetId() {
        return controllerNetId;
    }

    public void setControllerNetId(Long controllerNetId) {
        this.controllerNetId = controllerNetId;
    }

    public Long getControllerCompId() {
        return controllerCompId;
    }

    public void setControllerCompId(Long controllerCompId) {
        this.controllerCompId = controllerCompId;
    }

    public String getControllerCompName() {
        return controllerCompName;
    }

    public void setControllerCompName(String controllerCompName) {
        this.controllerCompName = controllerCompName;
    }
}