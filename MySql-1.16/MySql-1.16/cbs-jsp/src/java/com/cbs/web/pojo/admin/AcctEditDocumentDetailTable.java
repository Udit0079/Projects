/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class AcctEditDocumentDetailTable implements Serializable{
    private String docudetails;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocudetails() {
        return docudetails;
    }

    public void setDocudetails(String docudetails) {
        this.docudetails = docudetails;
    }


}
