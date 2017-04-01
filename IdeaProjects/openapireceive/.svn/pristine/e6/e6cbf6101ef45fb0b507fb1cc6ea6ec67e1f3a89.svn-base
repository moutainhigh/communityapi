package net.okdi.apiV4.entity;

import net.okdi.core.util.PubMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ParParceladdressVO {
    private Long uid; //

    private String expWaybillNum; //包裹运单号
    private String netName; //网络名称
    private String sendName; //发件人姓名
    private String sendPhone; //发件人手机
    private String sendAddress; //发件详细地址

    private String addresseeName; //收件人姓名
    private String addresseePhone; //收件人电话
    private String addresseeAddress; //收件详细地址

    private List<ContentHis> content;// 标记备注内容   如：时间   人   内容

    private Integer parNumber; //包裹数量 固定为1

    private String shipmentRemark; // 发运备注 1:已发运 0或空表示未发运
    private Date shipmentTime; // 发运时间

    private String code;

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setExpWaybillNum(String expWaybillNum) {
        if (PubMethod.isEmpty(expWaybillNum)) {
            expWaybillNum = "";
        }
        this.expWaybillNum = expWaybillNum;
    }

    public void setNetName(String netName) {
        if (PubMethod.isEmpty(netName)) {
            netName = "";
        }
        this.netName = netName;
    }

    public void setSendName(String sendName) {
        if (PubMethod.isEmpty(sendName)) {
            sendName = "";
        }
        this.sendName = sendName;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        if (PubMethod.isEmpty(sendPhone)) {
            sendPhone = "";
        }
        this.sendPhone = sendPhone;
    }

    public void setSendAddress(String sendAddress) {
        if (PubMethod.isEmpty(sendAddress)) {
            sendAddress = "";
        }
        this.sendAddress = sendAddress;
    }

    public void setAddresseeName(String addresseeName) {
        if (PubMethod.isEmpty(addresseeName)) {
            addresseeName = "";
        }
        this.addresseeName = addresseeName;
    }

    public void setAddresseePhone(String addresseePhone) {
        if (PubMethod.isEmpty(addresseePhone)) {
            addresseePhone = "";
        }
        this.addresseePhone = addresseePhone;
    }

    public void setAddresseeAddress(String addresseeAddress) {
        if (!PubMethod.isEmpty(addresseeAddress) && addresseeAddress.contains("undefined")) {
            addresseeAddress = addresseeAddress.replaceAll("undefined", "");
        }
        if (PubMethod.isEmpty(addresseeAddress)) {
            addresseeAddress = "";
        }
        this.addresseeAddress = addresseeAddress;
    }

    public void setContent(List<ContentHis> content) {
        if (PubMethod.isEmpty(content)) {
            content = new ArrayList<>();
        }
        this.content = content;
    }

    public void setParNumber(Integer parNumber) {
        this.parNumber = parNumber;
    }

    public Long getUid() {
        return uid;
    }

    public String getExpWaybillNum() {
        return expWaybillNum;
    }

    public String getNetName() {
        return netName;
    }

    public String getSendName() {
        return sendName;
    }


    public String getSendAddress() {
        return sendAddress;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public String getAddresseePhone() {
        return addresseePhone;
    }

    public String getAddresseeAddress() {
        return addresseeAddress;
    }

    public List<ContentHis> getContent() {
        return content;
    }

    public Integer getParNumber() {
        return parNumber;
    }

    public String getShipmentRemark() {
        return shipmentRemark;
    }

    public void setShipmentRemark(String shipmentRemark) {
        if (PubMethod.isEmpty(shipmentRemark)) {
            shipmentRemark = "";
        }
        this.shipmentRemark = shipmentRemark;
    }

    public Date getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(Date shipmentTime) {
        if (PubMethod.isEmpty(shipmentTime)) {
            shipmentTime = new Date();
        }
        this.shipmentTime = shipmentTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (PubMethod.isEmpty(code)) {
            code = "";
        }
        this.code = code;
    }
}