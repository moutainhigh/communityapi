package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="parParcelconnection")
public class ParParcelconnection {
    private Long uid;
    private Long parId;//包裹id   
    private Long netId;//交付人网络id    
    private Long compId;//交付人站点id  
    private Long expMemberId;//交付人id       
    private Long taskId;//任务id
    /**
     * 交付人动作标示  1:揽收(寄件) 2:派件 3:发货(寄件) 4:转订单(订单模块) 5:取消订单 6:转代收(派件) 7:接收(派件)
     */  
    private Short cosignFlag;
    
    private Short signResult;// 交付人的包裹标示    3：代收点签收  14 ：本人签收，15 他人签收，16：系统签收  17：转代收（转包裹等着）
    private Short expMemberSuccessFlag;// 交付人动作标志    0成功；1失败  
    @DateTimeFormat
    private Date createTime;//交付时间 
    private String mobilePhone; //收件人手机号 必填项-片键
    //-------------2017-2-15--------------------//
    private String expWaybillNum; //包裹运单号
    private String code; //取件标号 (揽收码)
    private String netName;//交付网络名称
    private String deliveryName;//交付人姓名
    private String deliveryAddress;//交付地址  
    private String deliveryUnits ;//交付单位
    private String deliveryMobile;//交付电话
    
    private Long recMemberId;//接受人id  
    private String recName;//接受人姓名
    private String recMobile;//接受人电话
    private String recUnits;//接受人单位
    private String recAddress;//接受人地址
    private Long recCompId;//接受人站点id  
    private Long recNetId;//接受人网络id
    private String recnetName;//接受人网络名称
    
    /**
     * 接受人动作标示
     * 1:揽收(寄件) 2:派件(转代收点 或 客户签收) 4:接单 5:取消订单 7:签收 8:收取派件 9: 从站点提货(派件) 10: 从分拨中心提货(派件) 11: 从分拨中心发货
     */
    private Short recCosignFlag;
    //private Short recMemberSuccessFlag;// 接受人动作标志    0成功；1失败
    //---------2017-3-10-------------------------
    /**
     * 邮包id
     */
    private Long packetsId;
    
    /**
     * 交付司机车号
     */
    private String deliveryCarNumber;
    
    /**
     * 接收司机车号
     */
    private String recCarNumber;
    
    /**
     * 上传快递公司成功   false：失败， true：成功
     */
    private Boolean NetFlag;
    
    /**
     * 上传电商成功e-commerce   false：失败， true：成功
     */
    private Boolean ecFlag;
       
    /**
     * 设备号（IMEI号）
     */
    private String terminalId;
    /**
     * 客户端（apk）版本号
     */
    private String versionId;
    
    
    public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public Long getPacketsId() {
		return packetsId;
	}

	public void setPacketsId(Long packetsId) {
		this.packetsId = packetsId;
	}

	public String getDeliveryCarNumber() {
		return deliveryCarNumber;
	}

	public void setDeliveryCarNumber(String deliveryCarNumber) {
		this.deliveryCarNumber = deliveryCarNumber;
	}

	public String getRecCarNumber() {
		return recCarNumber;
	}

	public void setRecCarNumber(String recCarNumber) {
		this.recCarNumber = recCarNumber;
	}


	public Boolean getNetFlag() {
		return NetFlag;
	}

	public void setNetFlag(Boolean netFlag) {
		NetFlag = netFlag;
	}

	public Boolean getEcFlag() {
		return ecFlag;
	}

	public void setEcFlag(Boolean ecFlag) {
		this.ecFlag = ecFlag;
	}

	public String getExpWaybillNum() {
		return expWaybillNum;
	}

	public Short getSignResult() {
		return signResult;
	}

	public void setSignResult(Short signResult) {
		this.signResult = signResult;
	}

	public String getRecName() {
		return recName;
	}


	public void setRecName(String recName) {
		this.recName = recName;
	}


	public String getRecMobile() {
		return recMobile;
	}


	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}


	public String getRecUnits() {
		return recUnits;
	}


	public void setRecUnits(String recUnits) {
		this.recUnits = recUnits;
	}


	public String getRecAddress() {
		return recAddress;
	}


	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}


	public String getDeliveryName() {
		return deliveryName;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}


	public String getDeliveryUnits() {
		return deliveryUnits;
	}


	public void setDeliveryUnits(String deliveryUnits) {
		this.deliveryUnits = deliveryUnits;
	}


	public String getDeliveryMobile() {
		return deliveryMobile;
	}


	public void setDeliveryMobile(String deliveryMobile) {
		this.deliveryMobile = deliveryMobile;
	}


	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
	}


	public String getNetName() {
		return netName;
	}


	public void setNetName(String netName) {
		this.netName = netName;
	}


	public String getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}


	public Long getRecMemberId() {
		return recMemberId;
	}


	public void setRecMemberId(Long recMemberId) {
		this.recMemberId = recMemberId;
	}


	public Long getRecCompId() {
		return recCompId;
	}


	public void setRecCompId(Long recCompId) {
		this.recCompId = recCompId;
	}


	public Long getRecNetId() {
		return recNetId;
	}


	public void setRecNetId(Long recNetId) {
		this.recNetId = recNetId;
	}


	public String getRecnetName() {
		return recnetName;
	}


	public void setRecnetName(String recnetName) {
		this.recnetName = recnetName;
	}


	public Short getRecCosignFlag() {
		return recCosignFlag;
	}


	public void setRecCosignFlag(Short recCosignFlag) {
		this.recCosignFlag = recCosignFlag;
	}


//	public Short getRecMemberSuccessFlag() {
//		return recMemberSuccessFlag;
//	}
//
//
//	public void setRecMemberSuccessFlag(Short recMemberSuccessFlag) {
//		this.recMemberSuccessFlag = recMemberSuccessFlag;
//	}


	
    
    public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public Long getParId() {
        return parId;
    }

    
    public void setParId(Long parId) {
        this.parId = parId;
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

    
    public Long getExpMemberId() {
        return expMemberId;
    }

    
    public void setExpMemberId(Long expMemberId) {
        this.expMemberId = expMemberId;
    }

    
    public Long getTaskId() {
        return taskId;
    }

    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    
    public Short getCosignFlag() {
        return cosignFlag;
    }

    
    public void setCosignFlag(Short cosignFlag) {
        this.cosignFlag = cosignFlag;
    }

    
    public Short getExpMemberSuccessFlag() {
        return expMemberSuccessFlag;
    }

    
    public void setExpMemberSuccessFlag(Short expMemberSuccessFlag) {
        this.expMemberSuccessFlag = expMemberSuccessFlag;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}