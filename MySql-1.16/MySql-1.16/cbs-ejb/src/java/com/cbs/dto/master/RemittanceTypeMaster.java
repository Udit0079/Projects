package com.cbs.dto.master;

import java.math.BigDecimal;

public class RemittanceTypeMaster {

    private int sNo;
    private String instCode;
    private int slabCode;
    private BigDecimal amtFrom;
    private BigDecimal amtTo;
    private String effDt;
    private String flag;
    private long txtleaves;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public long getTxtleaves() {
        return txtleaves;
    }

    public void setTxtleaves(long txtleaves) {
        this.txtleaves = txtleaves;
    }

    public RemittanceTypeMaster() {
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public int getSlabCode() {
        return slabCode;
    }

    public void setSlabCode(int slabCode) {
        this.slabCode = slabCode;
    }

    public BigDecimal getAmtFrom() {
        return amtFrom;
    }

    public void setAmtFrom(BigDecimal amtFrom) {
        this.amtFrom = amtFrom;
    }

    public BigDecimal getAmtTo() {
        return amtTo;
    }

    public void setAmtTo(BigDecimal amtTo) {
        this.amtTo = amtTo;
    }

    public String getEffDt() {
        return effDt;
    }

    public void setEffDt(String effDt) {
        this.effDt = effDt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
