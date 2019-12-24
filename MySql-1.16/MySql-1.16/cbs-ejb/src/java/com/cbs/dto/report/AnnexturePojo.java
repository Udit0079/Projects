/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class AnnexturePojo {
    
    private Integer gNo;
    private String sGNo;
    private Integer ssGNo;
    private String acCode;
    private String formula;
    private Integer f_gNo;
    private String f_sGNo;
    private Integer f_ssGNo;
    private String acName;
    private BigDecimal amount;
    private Integer date;

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getgNo() {
        return gNo;
    }

    public void setgNo(Integer gNo) {
        this.gNo = gNo;
    }

    public String getF_sGNo() {
        return f_sGNo;
    }

    public void setF_sGNo(String f_sGNo) {
        this.f_sGNo = f_sGNo;
    }

    public Integer getF_ssGNo() {
        return f_ssGNo;
    }

    public void setF_ssGNo(Integer f_ssGNo) {
        this.f_ssGNo = f_ssGNo;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getF_gNo() {
        return f_gNo;
    }

    public void setF_gNo(Integer f_gNo) {
        this.f_gNo = f_gNo;
    }

    public String getsGNo() {
        return sGNo;
    }

    public void setsGNo(String sGNo) {
        this.sGNo = sGNo;
    }

    public Integer getSsGNo() {
        return ssGNo;
    }

    public void setSsGNo(Integer ssGNo) {
        this.ssGNo = ssGNo;
    }
    
}
