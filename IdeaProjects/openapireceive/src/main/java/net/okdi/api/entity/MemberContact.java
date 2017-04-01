package net.okdi.api.entity;

import java.util.Date;
/**
 * @ClassName MemberContact
 * @Description 联系人基本信息
 * @author mengnan.zhang
 * @date 2014-10-28
 * @since jdk1.6
 */
public class MemberContact {
	
    //主键ID
    private Long id;
    //创建人memberid
    private Long createUserId;
    //联系人姓名
    private String contactName;
    //性别
    private Short gender;
    //联系人默认电话
    private String contactPhone;
    //联系人默认地址ID
    private Long contactAddressId;
    //联系人默认地址（文子）省市区
    private String contactDetaileDisplay;
    //联系人默认详细地址
    private String contactDetailedAddress;
    //数据获取方式   0-默认，页面维护写入
	//        1-Excel导入
	//        2-手机导入
	//        3-发快递时获取
    private Short dataMode;
    //姓名拼音缩写
    private String nameAbbr;
    //创建时间
    private Date createTime;
    //生日
    private Date birthday;
    //备注
    private String remark;
    //联系人在通行证中的ID
    private Long casMemberId;
    //客户ERP主键ID
    private Long erpCustomerId;
   
    public MemberContact(){}
    public MemberContact(Long id, Long createUserId, String contactName,
			Short gender, String contactPhone, Long contactAddressId,
			String contactDetaileDisplay, String contactDetailedAddress,
			Short dataMode, String nameAbbr, Date createTime, Date birthday,
			String remark, Long casMemberId, Long erpCustomerId) {
		super();
		this.id = id;
		this.createUserId = createUserId;
		this.contactName = contactName;
		this.gender = gender;
		this.contactPhone = contactPhone;
		this.contactAddressId = contactAddressId;
		this.contactDetaileDisplay = contactDetaileDisplay;
		this.contactDetailedAddress = contactDetailedAddress;
		this.dataMode = dataMode;
		this.nameAbbr = nameAbbr;
		this.createTime = createTime;
		this.birthday = birthday;
		this.remark = remark;
		this.casMemberId = casMemberId;
		this.erpCustomerId = erpCustomerId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Long getContactAddressId() {
        return contactAddressId;
    }

    public void setContactAddressId(Long contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

  
    public String getContactDetaileDisplay() {
        return contactDetaileDisplay;
    }

    public void setContactDetaileDisplay(String contactDetaileDisplay) {
        this.contactDetaileDisplay = contactDetaileDisplay;
    }

    public String getContactDetailedAddress() {
        return contactDetailedAddress;
    }

    public void setContactDetailedAddress(String contactDetailedAddress) {
        this.contactDetailedAddress = contactDetailedAddress;
    }

    public Short getDataMode() {
        return dataMode;
    }

    public void setDataMode(Short dataMode) {
        this.dataMode = dataMode;
    }

    public String getNameAbbr() {
        return nameAbbr;
    }

    public void setNameAbbr(String nameAbbr) {
        this.nameAbbr = nameAbbr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCasMemberId() {
        return casMemberId;
    }

    public void setCasMemberId(Long casMemberId) {
        this.casMemberId = casMemberId;
    }

    public Long getErpCustomerId() {
        return erpCustomerId;
    }

    public void setErpCustomerId(Long erpCustomerId) {
        this.erpCustomerId = erpCustomerId;
    }
}