package com.cbs.dto;

/**
 *
 * @author Navneet Goyal
 */
public class DividendTable implements Comparable {

    private Integer nos, sno;
    private String disbDate,
            details,
            folioNo,
            issueDt,
            paymentdt,
            paid,
            fYear,
            auth,
            authBy,
            status,
            brcode,
            purpose,
            relatedAcno,
            address,
            name,
            beneAcno,
            bankName,
            beneName,
            ifscCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Double divamt,
            amt;
    private Long certNo;

    public String getPaymentdt() {
        return paymentdt;
    }

    public void setPaymentdt(String paymentdt) {
        this.paymentdt = paymentdt;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBrcode() {
        return brcode;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
    }

    public Long getCertNo() {
        return certNo;
    }

    public void setCertNo(Long certNo) {
        this.certNo = certNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDisbDate() {
        return disbDate;
    }

    public void setDisbDate(String disbDate) {
        this.disbDate = disbDate;
    }

    public Double getDivamt() {
        return divamt;
    }

    public void setDivamt(Double divamt) {
        this.divamt = divamt;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNos() {
        return nos;
    }

    public void setNos(Integer nos) {
        this.nos = nos;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRelatedAcno() {
        return relatedAcno;
    }

    public void setRelatedAcno(String relatedAcno) {
        this.relatedAcno = relatedAcno;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeneAcno() {
        return beneAcno;
    }

    public void setBeneAcno(String beneAcno) {
        this.beneAcno = beneAcno;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    
    public int compareTo(Object object) {
        return folioNo.compareTo(((DividendTable) object).folioNo);
    }
}
