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
public class GstReportPojo {

    private String nameofreceipient;
    private String gstinUin;
    private String stateName;
    private String txnid;
    private String pos;
    //Invoice Details
    private String no;
    private int sNo;
    private String date;
    private BigDecimal invoiceValue;
    private String hsnSac;
    private String goodsServicedescription;
    private BigDecimal taxableValue;
    //End of Invoice Details
    // Quantity
    private String qty;
    private String unit;
    //End of Quantitys
    private double igstRate;
    private double igstAmt;
    private double cgstRate;
    private double cgstAmt;
    private double sgstRate;
    private double sgstAmt;
    private double cessRate;
    private double cessAmt;
    private String supplyAttractsReverseCharge;
    private String taxonassessment;
    private String assessmentOrderDetailsNo;
    private String assessmentOrderDetailsDt;
    private String nameofecommerceoperator;
    private String gSTINofecommerceOperator;
    //Shipping Bill  
    private String exportType;
    private String shNo;
    private String shDate;
    private String shPortCode;
    private String receipientCategory;
    private String itemType;
    private String acno;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }
    
    public String getNameofreceipient() {
        return nameofreceipient;
    }

    public void setNameofreceipient(String nameofreceipient) {
        this.nameofreceipient = nameofreceipient;
    }

    public String getGstinUin() {
        return gstinUin;
    }

    public void setGstinUin(String gstinUin) {
        this.gstinUin = gstinUin;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(BigDecimal invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public String getHsnSac() {
        return hsnSac;
    }

    public void setHsnSac(String hsnSac) {
        this.hsnSac = hsnSac;
    }

    public String getGoodsServicedescription() {
        return goodsServicedescription;
    }

    public void setGoodsServicedescription(String goodsServicedescription) {
        this.goodsServicedescription = goodsServicedescription;
    }

    public BigDecimal getTaxableValue() {
        return taxableValue;
    }

    public void setTaxableValue(BigDecimal taxableValue) {
        this.taxableValue = taxableValue;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getIgstRate() {
        return igstRate;
    }

    public void setIgstRate(double igstRate) {
        this.igstRate = igstRate;
    }

    public double getIgstAmt() {
        return igstAmt;
    }

    public void setIgstAmt(double igstAmt) {
        this.igstAmt = igstAmt;
    }

    public double getCgstRate() {
        return cgstRate;
    }

    public void setCgstRate(double cgstRate) {
        this.cgstRate = cgstRate;
    }

    public double getCgstAmt() {
        return cgstAmt;
    }

    public void setCgstAmt(double cgstAmt) {
        this.cgstAmt = cgstAmt;
    }

    public double getSgstRate() {
        return sgstRate;
    }

    public void setSgstRate(double sgstRate) {
        this.sgstRate = sgstRate;
    }

    public double getSgstAmt() {
        return sgstAmt;
    }

    public void setSgstAmt(double sgstAmt) {
        this.sgstAmt = sgstAmt;
    }

    public double getCessRate() {
        return cessRate;
    }

    public void setCessRate(double cessRate) {
        this.cessRate = cessRate;
    }

    public double getCessAmt() {
        return cessAmt;
    }

    public void setCessAmt(double cessAmt) {
        this.cessAmt = cessAmt;
    }

    public String getSupplyAttractsReverseCharge() {
        return supplyAttractsReverseCharge;
    }

    public void setSupplyAttractsReverseCharge(String supplyAttractsReverseCharge) {
        this.supplyAttractsReverseCharge = supplyAttractsReverseCharge;
    }

    public String getTaxonassessment() {
        return taxonassessment;
    }

    public void setTaxonassessment(String taxonassessment) {
        this.taxonassessment = taxonassessment;
    }

    public String getAssessmentOrderDetailsNo() {
        return assessmentOrderDetailsNo;
    }

    public void setAssessmentOrderDetailsNo(String assessmentOrderDetailsNo) {
        this.assessmentOrderDetailsNo = assessmentOrderDetailsNo;
    }

    public String getAssessmentOrderDetailsDt() {
        return assessmentOrderDetailsDt;
    }

    public void setAssessmentOrderDetailsDt(String assessmentOrderDetailsDt) {
        this.assessmentOrderDetailsDt = assessmentOrderDetailsDt;
    }

    public String getNameofecommerceoperator() {
        return nameofecommerceoperator;
    }

    public void setNameofecommerceoperator(String nameofecommerceoperator) {
        this.nameofecommerceoperator = nameofecommerceoperator;
    }

    public String getgSTINofecommerceOperator() {
        return gSTINofecommerceOperator;
    }

    public void setgSTINofecommerceOperator(String gSTINofecommerceOperator) {
        this.gSTINofecommerceOperator = gSTINofecommerceOperator;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getShNo() {
        return shNo;
    }

    public void setShNo(String shNo) {
        this.shNo = shNo;
    }

    public String getShDate() {
        return shDate;
    }

    public void setShDate(String shDate) {
        this.shDate = shDate;
    }

    public String getShPortCode() {
        return shPortCode;
    }

    public void setShPortCode(String shPortCode) {
        this.shPortCode = shPortCode;
    }

    public String getReceipientCategory() {
        return receipientCategory;
    }

    public void setReceipientCategory(String receipientCategory) {
        this.receipientCategory = receipientCategory;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }
}
