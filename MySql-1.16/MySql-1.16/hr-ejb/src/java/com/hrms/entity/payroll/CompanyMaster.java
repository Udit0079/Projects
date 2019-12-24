/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "company_master")
@NamedQueries({
    @NamedQuery(name = "CompanyMaster.findCompanyMasterEntityByCompanyCode", query = "SELECT c FROM CompanyMaster c WHERE c.companyCode = :companyCode"),
    @NamedQuery(name = "CompanyMaster.findCompanyNamebycmpnycode", query = "SELECT c.companyName FROM CompanyMaster c where c.companyCode = :companyCode"),
    @NamedQuery(name = "CompanyMaster.findAll", query = "SELECT c FROM CompanyMaster c"),
    @NamedQuery(name = "CompanyMaster.findByCompanyName", query = "SELECT c FROM CompanyMaster c WHERE c.companyName = :companyName"),
    @NamedQuery(name = "CompanyMaster.findByMailingName", query = "SELECT c FROM CompanyMaster c WHERE c.mailingName = :mailingName"),
    @NamedQuery(name = "CompanyMaster.findByAddress", query = "SELECT c FROM CompanyMaster c WHERE c.address = :address"),
    @NamedQuery(name = "CompanyMaster.findByState", query = "SELECT c FROM CompanyMaster c WHERE c.state = :state"),
    @NamedQuery(name = "CompanyMaster.findByCity", query = "SELECT c FROM CompanyMaster c WHERE c.city = :city"),
    @NamedQuery(name = "CompanyMaster.findByPin", query = "SELECT c FROM CompanyMaster c WHERE c.pin = :pin"),
    @NamedQuery(name = "CompanyMaster.findByCountryCode", query = "SELECT c FROM CompanyMaster c WHERE c.countryCode = :countryCode"),
    @NamedQuery(name = "CompanyMaster.findByParentCompany", query = "SELECT c FROM CompanyMaster c WHERE c.parentCompany = :parentCompany"),
    @NamedQuery(name = "CompanyMaster.findByParentCompCode", query = "SELECT c FROM CompanyMaster c WHERE c.parentCompCode = :parentCompCode"),
    @NamedQuery(name = "CompanyMaster.findByDefaultCompany", query = "SELECT c FROM CompanyMaster c WHERE c.defaultCompany = :defaultCompany"),
    @NamedQuery(name = "CompanyMaster.findByDefCompCode", query = "SELECT c FROM CompanyMaster c WHERE c.defCompCode = :defCompCode"),
    @NamedQuery(name = "CompanyMaster.findByActiveFlag", query = "SELECT c FROM CompanyMaster c WHERE c.activeFlag = :activeFlag"),
    @NamedQuery(name = "CompanyMaster.findByIncomeTaxNo", query = "SELECT c FROM CompanyMaster c WHERE c.incomeTaxNo = :incomeTaxNo"),
    @NamedQuery(name = "CompanyMaster.findByLstNo", query = "SELECT c FROM CompanyMaster c WHERE c.lstNo = :lstNo"),
    @NamedQuery(name = "CompanyMaster.findByCstno", query = "SELECT c FROM CompanyMaster c WHERE c.cstno = :cstno"),
    @NamedQuery(name = "CompanyMaster.findByIstNo", query = "SELECT c FROM CompanyMaster c WHERE c.istNo = :istNo"),
    @NamedQuery(name = "CompanyMaster.findByServiceTax", query = "SELECT c FROM CompanyMaster c WHERE c.serviceTax = :serviceTax"),
    @NamedQuery(name = "CompanyMaster.findByCompanyReg", query = "SELECT c FROM CompanyMaster c WHERE c.companyReg = :companyReg"),
    @NamedQuery(name = "CompanyMaster.findByApplyVat", query = "SELECT c FROM CompanyMaster c WHERE c.applyVat = :applyVat"),
    @NamedQuery(name = "CompanyMaster.findByVatTinNo", query = "SELECT c FROM CompanyMaster c WHERE c.vatTinNo = :vatTinNo"),
    @NamedQuery(name = "CompanyMaster.findByFinYearFrom", query = "SELECT c FROM CompanyMaster c WHERE c.finYearFrom = :finYearFrom"),
    @NamedQuery(name = "CompanyMaster.findByBooksBeginingFrom", query = "SELECT c FROM CompanyMaster c WHERE c.booksBeginingFrom = :booksBeginingFrom"),
    @NamedQuery(name = "CompanyMaster.findByBaseCurrrency", query = "SELECT c FROM CompanyMaster c WHERE c.baseCurrrency = :baseCurrrency"),
    @NamedQuery(name = "CompanyMaster.findByBaseCurrencyNotation", query = "SELECT c FROM CompanyMaster c WHERE c.baseCurrencyNotation = :baseCurrencyNotation"),
    @NamedQuery(name = "CompanyMaster.findByFloatingPoints", query = "SELECT c FROM CompanyMaster c WHERE c.floatingPoints = :floatingPoints"),
    @NamedQuery(name = "CompanyMaster.findByAuthFlag", query = "SELECT c FROM CompanyMaster c WHERE c.authFlag = :authFlag"),
    @NamedQuery(name = "CompanyMaster.findByAuthStatus", query = "SELECT c FROM CompanyMaster c WHERE c.authStatus = :authStatus"),
    @NamedQuery(name = "CompanyMaster.findByAuthBy", query = "SELECT c FROM CompanyMaster c WHERE c.authBy = :authBy"),
    @NamedQuery(name = "CompanyMaster.findByTranTime", query = "SELECT c FROM CompanyMaster c WHERE c.tranTime = :tranTime"),
    @NamedQuery(name = "CompanyMaster.findByEffectDt", query = "SELECT c FROM CompanyMaster c WHERE c.effectDt = :effectDt"),
    @NamedQuery(name = "CompanyMaster.findByEnteredBy", query = "SELECT c FROM CompanyMaster c WHERE c.enteredBy = :enteredBy")})
public class CompanyMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "COMPANY_NAME", nullable = false, length = 100)
    private String companyName;
    @Id
    @Basic(optional = false)
    @Column(name = "COMPANY_CODE", nullable = false)
    private Integer companyCode;
    @Column(name = "MAILING_NAME", length = 100)
    private String mailingName;
    @Column(name = "ADDRESS", length = 200)
    private String address;
    @Column(name = "STATE", length = 100)
    private String state;
    @Column(name = "CITY", length = 100)
    private String city;
    @Column(name = "PIN")
    private Integer pin;
    @Basic(optional = false)
    @Column(name = "COUNTRY_CODE", nullable = false)
    private int countryCode;
    @Column(name = "PARENT_COMPANY", length = 100)
    private String parentCompany;
    @Column(name = "PARENT_COMP_CODE")
    private Integer parentCompCode;
    @Column(name = "DEFAULT_COMPANY", length = 100)
    private String defaultCompany;
    @Column(name = "DEF_COMP_CODE")
    private Integer defCompCode;
    @Basic(optional = false)
    @Column(name = "ACTIVE_FLAG", nullable = false)
    private char activeFlag;
    @Basic(optional = false)
    @Column(name = "INCOME_TAX_NO", nullable = false, length = 50)
    private String incomeTaxNo;
    @Column(name = "LST_NO", length = 50)
    private String lstNo;
    @Column(name = "CSTNO", length = 50)
    private String cstno;
    @Column(name = "IST_NO", length = 50)
    private String istNo;
    @Column(name = "SERVICE_TAX", length = 20)
    private String serviceTax;
    @Column(name = "COMPANY_REG", length = 20)
    private String companyReg;
    @Basic(optional = false)
    @Column(name = "APPLY_VAT", nullable = false, length = 1)
    private String applyVat;
    @Column(name = "VAT_TIN_NO", length = 50)
    private String vatTinNo;
    @Basic(optional = false)
    @Column(name = "FIN_YEAR_FROM", nullable = false, length = 10)
    private String finYearFrom;
    @Basic(optional = false)
    @Column(name = "BOOKS_BEGINING_FROM", nullable = false, length = 10)
    private String booksBeginingFrom;
    @Basic(optional = false)
    @Column(name = "BASE_CURRRENCY", nullable = false, length = 20)
    private String baseCurrrency;
    @Basic(optional = false)
    @Column(name = "BASE_CURRENCY_NOTATION", nullable = false, length = 5)
    private String baseCurrencyNotation;
    @Basic(optional = false)
    @Column(name = "FLOATING_POINTS", nullable = false)
    private int floatingPoints;
    @Basic(optional = false)
    @Column(name = "AUTH_FLAG", nullable = false)
    private char authFlag;
    @Column(name = "AUTH_STATUS")
    private Character authStatus;
    @Column(name = "AUTH_BY", length = 100)
    private String authBy;
    @Basic(optional = false)
    @Column(name = "TRAN_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Basic(optional = false)
    @Column(name = "EFFECT_DT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectDt;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY", nullable = false, length = 100)
    private String enteredBy;

    public CompanyMaster() {
    }

    public CompanyMaster(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public CompanyMaster(Integer companyCode, String companyName, int countryCode, char activeFlag, String incomeTaxNo, String applyVat, String finYearFrom, String booksBeginingFrom, String baseCurrrency, String baseCurrencyNotation, int floatingPoints, char authFlag, Date tranTime, Date effectDt, String enteredBy) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.countryCode = countryCode;
        this.activeFlag = activeFlag;
        this.incomeTaxNo = incomeTaxNo;
        this.applyVat = applyVat;
        this.finYearFrom = finYearFrom;
        this.booksBeginingFrom = booksBeginingFrom;
        this.baseCurrrency = baseCurrrency;
        this.baseCurrencyNotation = baseCurrencyNotation;
        this.floatingPoints = floatingPoints;
        this.authFlag = authFlag;
        this.tranTime = tranTime;
        this.effectDt = effectDt;
        this.enteredBy = enteredBy;
    }

    public char getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(char activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyVat() {
        return applyVat;
    }

    public void setApplyVat(String applyVat) {
        this.applyVat = applyVat;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public char getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(char authFlag) {
        this.authFlag = authFlag;
    }

    public Character getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Character authStatus) {
        this.authStatus = authStatus;
    }

    public String getBaseCurrencyNotation() {
        return baseCurrencyNotation;
    }

    public void setBaseCurrencyNotation(String baseCurrencyNotation) {
        this.baseCurrencyNotation = baseCurrencyNotation;
    }

    public String getBaseCurrrency() {
        return baseCurrrency;
    }

    public void setBaseCurrrency(String baseCurrrency) {
        this.baseCurrrency = baseCurrrency;
    }

    public String getBooksBeginingFrom() {
        return booksBeginingFrom;
    }

    public void setBooksBeginingFrom(String booksBeginingFrom) {
        this.booksBeginingFrom = booksBeginingFrom;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyReg() {
        return companyReg;
    }

    public void setCompanyReg(String companyReg) {
        this.companyReg = companyReg;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getCstno() {
        return cstno;
    }

    public void setCstno(String cstno) {
        this.cstno = cstno;
    }

    public Integer getDefCompCode() {
        return defCompCode;
    }

    public void setDefCompCode(Integer defCompCode) {
        this.defCompCode = defCompCode;
    }

    public String getDefaultCompany() {
        return defaultCompany;
    }

    public void setDefaultCompany(String defaultCompany) {
        this.defaultCompany = defaultCompany;
    }

    public Date getEffectDt() {
        return effectDt;
    }

    public void setEffectDt(Date effectDt) {
        this.effectDt = effectDt;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFinYearFrom() {
        return finYearFrom;
    }

    public void setFinYearFrom(String finYearFrom) {
        this.finYearFrom = finYearFrom;
    }

    public int getFloatingPoints() {
        return floatingPoints;
    }

    public void setFloatingPoints(int floatingPoints) {
        this.floatingPoints = floatingPoints;
    }

    public String getIncomeTaxNo() {
        return incomeTaxNo;
    }

    public void setIncomeTaxNo(String incomeTaxNo) {
        this.incomeTaxNo = incomeTaxNo;
    }

    public String getIstNo() {
        return istNo;
    }

    public void setIstNo(String istNo) {
        this.istNo = istNo;
    }

    public String getLstNo() {
        return lstNo;
    }

    public void setLstNo(String lstNo) {
        this.lstNo = lstNo;
    }

    public String getMailingName() {
        return mailingName;
    }

    public void setMailingName(String mailingName) {
        this.mailingName = mailingName;
    }

    public Integer getParentCompCode() {
        return parentCompCode;
    }

    public void setParentCompCode(Integer parentCompCode) {
        this.parentCompCode = parentCompCode;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getVatTinNo() {
        return vatTinNo;
    }

    public void setVatTinNo(String vatTinNo) {
        this.vatTinNo = vatTinNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyCode != null ? companyCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyMaster)) {
            return false;
        }
        CompanyMaster other = (CompanyMaster) object;
        if ((this.companyCode == null && other.companyCode != null) || (this.companyCode != null && !this.companyCode.equals(other.companyCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.CompanyMaster[companyCode=" + companyCode + "]";
    }
}
