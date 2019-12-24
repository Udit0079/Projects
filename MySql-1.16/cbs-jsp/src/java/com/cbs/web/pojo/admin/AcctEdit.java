/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class AcctEdit implements Serializable{

    String docdetail;
    String code;
    String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDocdetail() {
        return docdetail;
    }

    public void setDocdetail(String docdetail) {
        this.docdetail = docdetail;
    }

    public AcctEdit(String docdetail, String code, String desc) {
        this.docdetail = docdetail;
        this.code = code;
        this.desc = desc;
    }
}
