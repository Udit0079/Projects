package com.cbs.web.pojo.dds;

public class DDSDenominationGrid implements Comparable {

    private Integer sno, denoNo;
    public Double denoValue, denoAmount;

    public Double getDenoAmount() {
        return denoAmount;
    }

    public void setDenoAmount(Double denoAmount) {
        this.denoAmount = denoAmount;
    }

    public Integer getDenoNo() {
        return denoNo;
    }

    public void setDenoNo(Integer denoNo) {
        this.denoNo = denoNo;
    }

    public Double getDenoValue() {
        return denoValue;
    }

    public void setDenoValue(Double denoValue) {
        this.denoValue = denoValue;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public int compareTo(Object object) {
        return denoValue.compareTo(((DDSDenominationGrid) object).denoValue);
    }
}
