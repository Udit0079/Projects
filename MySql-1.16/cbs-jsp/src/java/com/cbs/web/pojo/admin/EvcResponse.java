/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.admin;


/**
 *
 * @author root
 */
public class EvcResponse {
    
    private String uniqueRequestId;
    
    private String name;
    
    private String panVerFlag;
    
    private String mobileVerFlag;
    
    private String emailVerFlag;
    
    private String responseCode;
    
//    public EvcResponse(String uniqueId, String name, String panFlag, String mobileFlag, String emailFlag){
//        uniqueRequestId = uniqueId;
//        this.name = name;
//        panVerFlag  = panFlag;
//        mobileVerFlag = mobileFlag;
//        emailVerFlag = emailFlag;
//    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPanVerFlag() {
        return panVerFlag;
    }

    public void setPanVerFlag(String panVerFlag) {
        this.panVerFlag = panVerFlag;
    }

    public String getMobileVerFlag() {
        return mobileVerFlag;
    }

    public void setMobileVerFlag(String mobileVerFlag) {
        this.mobileVerFlag = mobileVerFlag;
    }

    public String getEmailVerFlag() {
        return emailVerFlag;
    }

    public void setEmailVerFlag(String emailVerFlag) {
        this.emailVerFlag = emailVerFlag;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    
    
}
