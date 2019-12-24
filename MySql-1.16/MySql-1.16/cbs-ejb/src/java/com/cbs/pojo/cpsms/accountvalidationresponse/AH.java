package com.cbs.pojo.cpsms.accountvalidationresponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author root
 */
public class AH {
    private String name;
    private String sName;
    private String gender;
    private String uid;
    private String mobile;
    private String add1;
    private String add2;
    private String district;
    private String state;
    private String pinCode; 
    private String pan;
    private String tan;
    private String acno;
    private String EntityCode;

    public String getName() {
        return name;
    }
    @XmlAttribute(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getsName() {
        return sName;
    }
    @XmlAttribute(name = "ShortName")
    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getGender() {
        return gender;
    }
    @XmlAttribute(name = "Gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }
    @XmlAttribute(name = "UID")
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }
    @XmlAttribute(name = "Mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAdd1() {
        return add1;
    }
    @XmlAttribute(name = "AddressLine1")
    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }
    @XmlAttribute(name = "AddressLine2")
    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getDistrict() {
        return district;
    }
    @XmlAttribute(name = "District")
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }
    @XmlAttribute(name = "State")        
    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }
    @XmlAttribute(name = "PinCode")
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPan() {
        return pan;
    }
    @XmlAttribute(name = "PAN")
    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTan() {
        return tan;
    }

    @XmlAttribute(name = "TAN")
    public void setTan(String tan) {
        this.tan = tan;
    }
    
    public String getAcno() {
        return acno;
    }
    @XmlAttribute(name = "AccountNumber")
    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getEntityCode() {
        return EntityCode;
    }
    @XmlAttribute(name = "EntityCode")
    public void setEntityCode(String EntityCode) {
        this.EntityCode = EntityCode;
    }
    
}
