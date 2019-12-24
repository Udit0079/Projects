/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@XmlRootElement(name = "MSG")
@XmlAccessorType(XmlAccessType.FIELD)
public class N06 {

    @XmlElement(name = "FTransaction_Reference_Number")
    private String FTransaction_Reference_Number;
    @XmlElement(name = "FAmount")
    private String FAmount;
    @XmlElement(name = "FValue_Date")
    private String FValue_Date;
    @XmlElement(name = "FSending_branchs_IFSC")
    private String FSending_branchs_IFSC;
    @XmlElement(name = "FSending_Customer_Account_Type")
    private String FSending_Customer_Account_Type;
    @XmlElement(name = "FSending_Customer_Account_Number")
    private String FSending_Customer_Account_Number;
    @XmlElement(name = "FSending_Customer_Account_Name")
    private String FSending_Customer_Account_Name;
    @XmlElement(name = "FSending_Customer_Mobile_No_Email_Id")
    private String FSending_Customer_Mobile_No_Email_Id;
    @XmlElement(name = "FSending_Customer_Mobile_No_Email_Id_")
    private String FSending_Customer_Mobile_No_Email_Id_;
    @XmlElement(name = "FOriginator_of_Remittance")
    private String FOriginator_of_Remittance;
    @XmlElement(name = "FBeneficiary_branchs_IFSC")
    private String FBeneficiary_branchs_IFSC;
    @XmlElement(name = "FBeneficiary_Customer_Account_Type")
    private String FBeneficiary_Customer_Account_Type;
    @XmlElement(name = "FBeneficiary_Customer_Account_Number")
    private String FBeneficiary_Customer_Account_Number;
    @XmlElement(name = "FBeneficiary_Customer_Account_Name")
    private String FBeneficiary_Customer_Account_Name;
    @XmlElement(name = "FBeneficiary_Customer_Address")
    private String FBeneficiary_Customer_Address;
    @XmlElement(name = "FSender_to_Receiver_Information")
    private String FSender_to_Receiver_Information;

    public String getFTransaction_Reference_Number() {
        return FTransaction_Reference_Number;
    }

    public void setFTransaction_Reference_Number(String FTransaction_Reference_Number) {
        this.FTransaction_Reference_Number = FTransaction_Reference_Number;
    }

    public String getFAmount() {
        return FAmount;
    }

    public void setFAmount(String FAmount) {
        this.FAmount = FAmount;
    }

    public String getFValue_Date() {
        return FValue_Date;
    }

    public void setFValue_Date(String FValue_Date) {
        this.FValue_Date = FValue_Date;
    }

    public String getFSending_branchs_IFSC() {
        return FSending_branchs_IFSC;
    }

    public void setFSending_branchs_IFSC(String FSending_branchs_IFSC) {
        this.FSending_branchs_IFSC = FSending_branchs_IFSC;
    }

    public String getFSending_Customer_Account_Type() {
        return FSending_Customer_Account_Type;
    }

    public void setFSending_Customer_Account_Type(String FSending_Customer_Account_Type) {
        this.FSending_Customer_Account_Type = FSending_Customer_Account_Type;
    }

    public String getFSending_Customer_Account_Number() {
        return FSending_Customer_Account_Number;
    }

    public void setFSending_Customer_Account_Number(String FSending_Customer_Account_Number) {
        this.FSending_Customer_Account_Number = FSending_Customer_Account_Number;
    }

    public String getFSending_Customer_Account_Name() {
        return FSending_Customer_Account_Name;
    }

    public void setFSending_Customer_Account_Name(String FSending_Customer_Account_Name) {
        this.FSending_Customer_Account_Name = FSending_Customer_Account_Name;
    }

    public String getFSending_Customer_Mobile_No_Email_Id() {
        return FSending_Customer_Mobile_No_Email_Id;
    }

    public void setFSending_Customer_Mobile_No_Email_Id(String FSending_Customer_Mobile_No_Email_Id) {
        this.FSending_Customer_Mobile_No_Email_Id = FSending_Customer_Mobile_No_Email_Id;
    }

    public String getFSending_Customer_Mobile_No_Email_Id_() {
        return FSending_Customer_Mobile_No_Email_Id_;
    }

    public void setFSending_Customer_Mobile_No_Email_Id_(String FSending_Customer_Mobile_No_Email_Id_) {
        this.FSending_Customer_Mobile_No_Email_Id_ = FSending_Customer_Mobile_No_Email_Id_;
    }

    public String getFOriginator_of_Remittance() {
        return FOriginator_of_Remittance;
    }

    public void setFOriginator_of_Remittance(String FOriginator_of_Remittance) {
        this.FOriginator_of_Remittance = FOriginator_of_Remittance;
    }

    public String getFBeneficiary_branchs_IFSC() {
        return FBeneficiary_branchs_IFSC;
    }

    public void setFBeneficiary_branchs_IFSC(String FBeneficiary_branchs_IFSC) {
        this.FBeneficiary_branchs_IFSC = FBeneficiary_branchs_IFSC;
    }

    public String getFBeneficiary_Customer_Account_Type() {
        return FBeneficiary_Customer_Account_Type;
    }

    public void setFBeneficiary_Customer_Account_Type(String FBeneficiary_Customer_Account_Type) {
        this.FBeneficiary_Customer_Account_Type = FBeneficiary_Customer_Account_Type;
    }

    public String getFBeneficiary_Customer_Account_Number() {
        return FBeneficiary_Customer_Account_Number;
    }

    public void setFBeneficiary_Customer_Account_Number(String FBeneficiary_Customer_Account_Number) {
        this.FBeneficiary_Customer_Account_Number = FBeneficiary_Customer_Account_Number;
    }

    public String getFBeneficiary_Customer_Account_Name() {
        return FBeneficiary_Customer_Account_Name;
    }

    public void setFBeneficiary_Customer_Account_Name(String FBeneficiary_Customer_Account_Name) {
        this.FBeneficiary_Customer_Account_Name = FBeneficiary_Customer_Account_Name;
    }

    public String getFBeneficiary_Customer_Address() {
        return FBeneficiary_Customer_Address;
    }

    public void setFBeneficiary_Customer_Address(String FBeneficiary_Customer_Address) {
        this.FBeneficiary_Customer_Address = FBeneficiary_Customer_Address;
    }

    public String getFSender_to_Receiver_Information() {
        return FSender_to_Receiver_Information;
    }

    public void setFSender_to_Receiver_Information(String FSender_to_Receiver_Information) {
        this.FSender_to_Receiver_Information = FSender_to_Receiver_Information;
    }
}
