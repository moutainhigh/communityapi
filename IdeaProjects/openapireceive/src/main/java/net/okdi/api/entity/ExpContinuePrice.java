package net.okdi.api.entity;

import java.math.BigDecimal;

public class ExpContinuePrice {
    private Long id;

    private Long firstPriceId;

    private BigDecimal continueWeight;

    private BigDecimal continueFreight;

    private BigDecimal weightMin;

    private BigDecimal weightMax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstPriceId() {
        return firstPriceId;
    }

    public void setFirstPriceId(Long firstPriceId) {
        this.firstPriceId = firstPriceId;
    }

    public BigDecimal getContinueWeight() {
        return continueWeight;
    }

    public void setContinueWeight(BigDecimal continueWeight) {
        this.continueWeight = continueWeight;
    }

    public BigDecimal getContinueFreight() {
        return continueFreight;
    }

    public void setContinueFreight(BigDecimal continueFreight) {
        this.continueFreight = continueFreight;
    }

    public BigDecimal getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(BigDecimal weightMin) {
        this.weightMin = weightMin;
    }

    public BigDecimal getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(BigDecimal weightMax) {
        this.weightMax = weightMax;
    }
}