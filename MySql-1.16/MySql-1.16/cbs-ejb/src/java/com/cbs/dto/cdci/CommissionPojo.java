package com.cbs.dto.cdci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommissionPojo", propOrder = {
    "amountFrom", "amountTo", "commCharge", "surCharge", "minCharge", "commFlag"})
public class CommissionPojo {

    private Double amountFrom, amountTo, commCharge, surCharge, minCharge;
    private Character commFlag;

    public Double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Double amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Double amountTo) {
        this.amountTo = amountTo;
    }

    public Double getCommCharge() {
        return commCharge;
    }

    public void setCommCharge(Double commCharge) {
        this.commCharge = commCharge;
    }

    public Character getCommFlag() {
        return commFlag;
    }

    public void setCommFlag(Character commFlag) {
        this.commFlag = commFlag;
    }

    public Double getMinCharge() {
        return minCharge;
    }

    public void setMinCharge(Double minCharge) {
        this.minCharge = minCharge;
    }

    public Double getSurCharge() {
        return surCharge;
    }

    public void setSurCharge(Double surCharge) {
        this.surCharge = surCharge;
    }
}
