/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author root
 */
@Embeddable
public class HrMstProgramPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "PROG_CODE")
    private String progCode;

    public HrMstProgramPK() {
    }

    public HrMstProgramPK(int compCode, String progCode) {
        this.compCode = compCode;
        this.progCode = progCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (progCode != null ? progCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstProgramPK)) {
            return false;
        }
        HrMstProgramPK other = (HrMstProgramPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.progCode == null && other.progCode != null) || (this.progCode != null && !this.progCode.equals(other.progCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstProgramPK[compCode=" + compCode + ", progCode=" + progCode + "]";
    }

}
