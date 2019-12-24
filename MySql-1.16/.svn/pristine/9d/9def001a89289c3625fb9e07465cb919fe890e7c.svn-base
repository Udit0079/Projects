/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Embeddable
public class CodebookPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupCode")
    private short groupCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Code")
    private short code;

    public CodebookPK() {
    }

    public CodebookPK(short groupCode, short code) {
        this.groupCode = groupCode;
        this.code = code;
    }

    public short getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(short groupCode) {
        this.groupCode = groupCode;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupCode;
        hash += (int) code;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodebookPK)) {
            return false;
        }
        CodebookPK other = (CodebookPK) object;
        if (this.groupCode != other.groupCode) {
            return false;
        }
        if (this.code != other.code) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.CodebookPK[ groupCode=" + groupCode + ", code=" + code + " ]";
    }
    
}
