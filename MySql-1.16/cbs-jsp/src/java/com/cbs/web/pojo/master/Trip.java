/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.master;

/**
 *
 * @author sipl
 */
public class Trip {
    
    private String desc;
    private String code;
    public String glCode;
    public String glDes;

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
  
    public String getGlCode() {
        return glCode;
    }


    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }


    public String getGlDes() {
        return glDes;
    }


    public void setGlDes(String glDes) {
        this.glDes = glDes;
    }
	
}
