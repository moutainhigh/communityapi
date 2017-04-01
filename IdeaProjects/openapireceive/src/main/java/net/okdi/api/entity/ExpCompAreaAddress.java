package net.okdi.api.entity;

import java.util.Date;

public class ExpCompAreaAddress{
    private Long netId;
    private Long addressId;
    private Long compId;
    private Date createdTime;

    public Long getNetId() {
        return netId;
    }

    public void setNetId(Long netId) {
        this.netId = netId;
    }
    
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }
    
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}