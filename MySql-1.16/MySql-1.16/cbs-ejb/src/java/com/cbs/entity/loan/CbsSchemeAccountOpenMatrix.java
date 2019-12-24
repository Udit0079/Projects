/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_account_open_matrix")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findAll", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findBySchemeCode", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findBySchemeType", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByMatrixDescription", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.matrixDescription = :matrixDescription"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByValidInvalidFlag", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.validInvalidFlag = :validInvalidFlag"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustStatus", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custStatus = :custStatus"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustSector", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custSector = :custSector"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustSubSector", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custSubSector = :custSubSector"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustTypeCode", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custTypeCode = :custTypeCode"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustConstitution", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custConstitution = :custConstitution"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCusTempId", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.cusTempId = :cusTempId"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustOtherBank", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custOtherBank = :custOtherBank"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByModeOfOperation", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.modeOfOperation = :modeOfOperation"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByGuaranteeCover", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.guaranteeCover = :guaranteeCover"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByNatureOfAdvance", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.natureOfAdvance = :natureOfAdvance"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByTypeOfAdvance", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.typeOfAdvance = :typeOfAdvance"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByModeOfAdvance", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.modeOfAdvance = :modeOfAdvance"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByPurposeOfAdvance", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.purposeOfAdvance = :purposeOfAdvance"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByCustMinorFlag", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.custMinorFlag = :custMinorFlag"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByChequeAllowedFlag", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.chequeAllowedFlag = :chequeAllowedFlag"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByAccountTurnDetail", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.accountTurnDetail = :accountTurnDetail"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByAccountOwnership", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.accountOwnership = :accountOwnership"),
    @NamedQuery(name = "CbsSchemeAccountOpenMatrix.findByDelFlag", query = "SELECT c FROM CbsSchemeAccountOpenMatrix c WHERE c.delFlag = :delFlag")})
public class CbsSchemeAccountOpenMatrix extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 80)
    @Column(name = "MATRIX_DESCRIPTION")
    private String matrixDescription;
    @Size(max = 1)
    @Column(name = "VALID_INVALID_FLAG")
    private String validInvalidFlag;
    @Size(max = 6)
    @Column(name = "CUST_STATUS")
    private String custStatus;
    @Size(max = 6)
    @Column(name = "CUST_SECTOR")
    private String custSector;
    @Size(max = 6)
    @Column(name = "CUST_SUB_SECTOR")
    private String custSubSector;
    @Size(max = 6)
    @Column(name = "CUST_TYPE_CODE")
    private String custTypeCode;
    @Size(max = 6)
    @Column(name = "CUST_CONSTITUTION")
    private String custConstitution;
    @Size(max = 10)
    @Column(name = "CUS_TEMP_ID")
    private String cusTempId;
    @Size(max = 6)
    @Column(name = "CUST_OTHER_BANK")
    private String custOtherBank;
    @Size(max = 6)
    @Column(name = "MODE_OF_OPERATION")
    private String modeOfOperation;
    @Size(max = 6)
    @Column(name = "GUARANTEE_COVER")
    private String guaranteeCover;
    @Size(max = 6)
    @Column(name = "NATURE_OF_ADVANCE")
    private String natureOfAdvance;
    @Size(max = 6)
    @Column(name = "TYPE_OF_ADVANCE")
    private String typeOfAdvance;
    @Size(max = 6)
    @Column(name = "MODE_OF_ADVANCE")
    private String modeOfAdvance;
    @Size(max = 6)
    @Column(name = "PURPOSE_OF_ADVANCE")
    private String purposeOfAdvance;
    @Size(max = 1)
    @Column(name = "CUST_MINOR_FLAG")
    private String custMinorFlag;
    @Size(max = 1)
    @Column(name = "CHEQUE_ALLOWED_FLAG")
    private String chequeAllowedFlag;
    @Size(max = 1)
    @Column(name = "ACCOUNT_TURN_DETAIL")
    private String accountTurnDetail;
    @Size(max = 1)
    @Column(name = "ACCOUNT_OWNERSHIP")
    private String accountOwnership;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeAccountOpenMatrix() {
    }

    public CbsSchemeAccountOpenMatrix(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getMatrixDescription() {
        return matrixDescription;
    }

    public void setMatrixDescription(String matrixDescription) {
        this.matrixDescription = matrixDescription;
    }

    public String getValidInvalidFlag() {
        return validInvalidFlag;
    }

    public void setValidInvalidFlag(String validInvalidFlag) {
        this.validInvalidFlag = validInvalidFlag;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public String getCustSector() {
        return custSector;
    }

    public void setCustSector(String custSector) {
        this.custSector = custSector;
    }

    public String getCustSubSector() {
        return custSubSector;
    }

    public void setCustSubSector(String custSubSector) {
        this.custSubSector = custSubSector;
    }

    public String getCustTypeCode() {
        return custTypeCode;
    }

    public void setCustTypeCode(String custTypeCode) {
        this.custTypeCode = custTypeCode;
    }

    public String getCustConstitution() {
        return custConstitution;
    }

    public void setCustConstitution(String custConstitution) {
        this.custConstitution = custConstitution;
    }

    public String getCusTempId() {
        return cusTempId;
    }

    public void setCusTempId(String cusTempId) {
        this.cusTempId = cusTempId;
    }

    public String getCustOtherBank() {
        return custOtherBank;
    }

    public void setCustOtherBank(String custOtherBank) {
        this.custOtherBank = custOtherBank;
    }

    public String getModeOfOperation() {
        return modeOfOperation;
    }

    public void setModeOfOperation(String modeOfOperation) {
        this.modeOfOperation = modeOfOperation;
    }

    public String getGuaranteeCover() {
        return guaranteeCover;
    }

    public void setGuaranteeCover(String guaranteeCover) {
        this.guaranteeCover = guaranteeCover;
    }

    public String getNatureOfAdvance() {
        return natureOfAdvance;
    }

    public void setNatureOfAdvance(String natureOfAdvance) {
        this.natureOfAdvance = natureOfAdvance;
    }

    public String getTypeOfAdvance() {
        return typeOfAdvance;
    }

    public void setTypeOfAdvance(String typeOfAdvance) {
        this.typeOfAdvance = typeOfAdvance;
    }

    public String getModeOfAdvance() {
        return modeOfAdvance;
    }

    public void setModeOfAdvance(String modeOfAdvance) {
        this.modeOfAdvance = modeOfAdvance;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getCustMinorFlag() {
        return custMinorFlag;
    }

    public void setCustMinorFlag(String custMinorFlag) {
        this.custMinorFlag = custMinorFlag;
    }

    public String getChequeAllowedFlag() {
        return chequeAllowedFlag;
    }

    public void setChequeAllowedFlag(String chequeAllowedFlag) {
        this.chequeAllowedFlag = chequeAllowedFlag;
    }

    public String getAccountTurnDetail() {
        return accountTurnDetail;
    }

    public void setAccountTurnDetail(String accountTurnDetail) {
        this.accountTurnDetail = accountTurnDetail;
    }

    public String getAccountOwnership() {
        return accountOwnership;
    }

    public void setAccountOwnership(String accountOwnership) {
        this.accountOwnership = accountOwnership;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeAccountOpenMatrix)) {
            return false;
        }
        CbsSchemeAccountOpenMatrix other = (CbsSchemeAccountOpenMatrix) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeAccountOpenMatrix[ schemeCode=" + schemeCode + " ]";
    }
    
}
