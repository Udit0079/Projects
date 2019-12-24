/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class strCommonPanMobilePhoneEmailPojo {
    
    String commonType;
    String commonNo;
    String acNo;
    Integer noOfTrans;
    BigDecimal valueOfTran;

    public String getCommonType() {
        return commonType;
    }

    public void setCommonType(String commonType) {
        this.commonType = commonType;
    }

    public String getCommonNo() {
        return commonNo;
    }

    public void setCommonNo(String commonNo) {
        this.commonNo = commonNo;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public Integer getNoOfTrans() {
        return noOfTrans;
    }

    public void setNoOfTrans(Integer noOfTrans) {
        this.noOfTrans = noOfTrans;
    }

    public BigDecimal getValueOfTran() {
        return valueOfTran;
    }

    public void setValueOfTran(BigDecimal valueOfTran) {
        this.valueOfTran = valueOfTran;
    }
}
