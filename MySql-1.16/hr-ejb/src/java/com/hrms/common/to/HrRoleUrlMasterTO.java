/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class HrRoleUrlMasterTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idNo;
    private Integer roleName;
    private String displayName;
    private String url;
    private String moduleName;
    private Integer txnId;
    private Integer parentcode;
    private Integer subparentcode;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIdNo() {
        return idNo;
    }

    public void setIdNo(Integer idNo) {
        this.idNo = idNo;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getParentcode() {
        return parentcode;
    }

    public void setParentcode(Integer parentcode) {
        this.parentcode = parentcode;
    }

    public Integer getRoleName() {
        return roleName;
    }

    public void setRoleName(Integer roleName) {
        this.roleName = roleName;
    }

    public Integer getSubparentcode() {
        return subparentcode;
    }

    public void setSubparentcode(Integer subparentcode) {
        this.subparentcode = subparentcode;
    }

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "CbsRoleUrlMasterTO[ idNo=" + idNo + " ]";
    }
}
