/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.master;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemePopUpFormsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String formId;
    private String formName;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
