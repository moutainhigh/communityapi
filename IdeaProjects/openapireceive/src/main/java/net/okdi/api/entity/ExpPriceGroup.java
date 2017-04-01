package net.okdi.api.entity;

import java.math.BigDecimal;

public class ExpPriceGroup {
    private Long id;

    private String groupName;

    private BigDecimal discountPercentage;

    private Long compId;

    private Long netId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
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
}