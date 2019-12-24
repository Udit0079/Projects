/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeDocumentDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeDocumentDetailsPKTO cbsSchemeDocumentDetailsPKTO;
    private String documentDescription;
    private String mandatoryDocument;
    private String delFlag;
//    Added by dinesh to handle Save And Update in either both: s:To save,u:To update,g:To get
    private String saveUpdateFlag;

    public CbsSchemeDocumentDetailsPKTO getCbsSchemeDocumentDetailsPKTO() {
        return cbsSchemeDocumentDetailsPKTO;
    }

    public void setCbsSchemeDocumentDetailsPKTO(CbsSchemeDocumentDetailsPKTO cbsSchemeDocumentDetailsPKTO) {
        this.cbsSchemeDocumentDetailsPKTO = cbsSchemeDocumentDetailsPKTO;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getMandatoryDocument() {
        return mandatoryDocument;
    }

    public void setMandatoryDocument(String mandatoryDocument) {
        this.mandatoryDocument = mandatoryDocument;
    }

    public String getSaveUpdateFlag() {
        return saveUpdateFlag;
    }

    public void setSaveUpdateFlag(String saveUpdateFlag) {
        this.saveUpdateFlag = saveUpdateFlag;
    }
}
