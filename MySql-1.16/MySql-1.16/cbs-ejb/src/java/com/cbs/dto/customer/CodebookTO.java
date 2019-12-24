/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CodebookTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CodebookPKTO codebookPKTO;
    private String description;

    public CodebookPKTO getCodebookPKTO() {
        return codebookPKTO;
    }

    public void setCodebookPKTO(CodebookPKTO codebookPKTO) {
        this.codebookPKTO = codebookPKTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
