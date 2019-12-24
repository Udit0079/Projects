/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.CustomerDetailsReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.List;

public class JointHolderBean extends BaseBean {

    private String jtMsg;
    private String primaryAccount;
    private String primaryName;
    private String jtName1;
    private String dob1;
    private String panNo1;
    private String fatherName1;
    private String occupation1;
    private String address1;
    private String jtName2;
    private String dob2;
    private String panNo2;
    private String fatherName2;
    private String occupation2;
    private String address2;
    private String jtName3;
    private String dob3;
    private String panNo3;
    private String fatherName3;
    private String occupation3;
    private String address3;
    private String jtName4;
    private String dob4;
    private String panNo4;
    private String fatherName4;
    private String occupation4;
    private String address4;
    private String nomineeName;
    private String nomineeAddress;
    private String nomineeRelation;
    private String proprietorName1;
    private String proprietorName2;
    private String proprietorName3;
    private String proprietorName4;
    private CustomerDetailsReportFacadeRemote remoteObject;
    private FtsPostingMgmtFacadeRemote ftsRemote;
   

    public JointHolderBean() {
        try {
            remoteObject = (CustomerDetailsReportFacadeRemote) ServiceLocator.getInstance().lookup("CustomerDetailsReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
        } catch (Exception ex) {
            this.setJtMsg(ex.getMessage());
        }
    }

    //Account Joint Detail-Here always there will be new account number.
    public void jtDetails(String accountNo) {
        resetjtHHolderDetails();
        try {
            System.out.println("Joint Detail A/c Is-->" + accountNo);
            List proprietorNameList = remoteObject.proprietorDetails(accountNo);
            if(!proprietorNameList.isEmpty()){
                setProprietorName1(proprietorNameList.get(0).toString());
                setProprietorName2(proprietorNameList.get(1).toString());
                setProprietorName3(proprietorNameList.get(2).toString());
                setProprietorName4(proprietorNameList.get(3).toString());
            }
            
            List resultList = remoteObject.jtDetails(accountNo, getOrgnBrCode());
            if (resultList.isEmpty()) {
                this.setJtMsg("There is no joint detail for account:-" + accountNo);
                return;
            }
            this.primaryAccount = accountNo;
            this.primaryName = ftsRemote.getCustName(accountNo);
            if (resultList.get(0).toString().equalsIgnoreCase("")) {
                setJtMsg("Sorry Nominee Details Are Not Present");
            } else {
                setNomineeName(resultList.get(0).toString());
                setNomineeAddress(resultList.get(1).toString());
                setNomineeRelation(resultList.get(2).toString());
            }
            if (resultList.size() == 3 || resultList.get(3).toString().equalsIgnoreCase("")) {
                this.setJtMsg("Sorry Joint Holder Details Are Not Present");
                return;
            }
            if (resultList.size() > 3) {
                setJtName1(resultList.get(3).toString());
                setDob1(resultList.get(4).toString());
                setAddress1(resultList.get(5).toString());
                setFatherName1(resultList.get(6).toString());
                setPanNo1(resultList.get(7).toString());
                setOccupation1(resultList.get(8).toString());
            }
            if (resultList.size() > 8) {
                setJtName2(resultList.get(9).toString());
                setDob2(resultList.get(10).toString());
                setAddress2(resultList.get(11).toString());
                setFatherName2(resultList.get(12).toString());
                setPanNo2(resultList.get(13).toString());
                setOccupation2(resultList.get(14).toString());
            }
            if (resultList.size() > 14) {
                setJtName3(resultList.get(15).toString());
                setDob3(resultList.get(16).toString());
                setAddress3(resultList.get(17).toString());
                setFatherName3(resultList.get(1).toString());
                setPanNo3(resultList.get(19).toString());
                setOccupation3(resultList.get(20).toString());
            }
            if (resultList.size() > 20) {
                setJtName4(resultList.get(21).toString());
                setDob4(resultList.get(22).toString());
                setAddress4(resultList.get(23).toString());
                setFatherName4(resultList.get(24).toString());
                setPanNo4(resultList.get(25).toString());
                setOccupation4(resultList.get(26).toString());
            }
        } catch (Exception ex) {
            this.setJtMsg(ex.getMessage());
        }
    }

    public void resetjtHHolderDetails() {
        this.setJtMsg("");
        this.setPrimaryAccount("");
        this.setPrimaryName("");
        this.setJtName1("");
        this.setDob1("");
        this.setAddress1("");
        this.setFatherName1("");
        this.setPanNo1("");
        this.setOccupation1("");
        this.setJtName2("");
        this.setDob2("");
        this.setAddress2("");
        this.setFatherName2("");
        this.setPanNo2("");
        this.setOccupation2("");
        this.setJtName3("");
        this.setDob3("");
        this.setAddress3("");
        this.setFatherName3("");
        this.setPanNo3("");
        this.setOccupation3("");
        this.setJtName4("");
        this.setDob4("");
        this.setAddress4("");
        this.setFatherName4("");
        this.setPanNo4("");
        this.setOccupation4("");
        this.setNomineeName("");
        this.setNomineeAddress("");
        this.setNomineeRelation("");
        this.setProprietorName1("");
        this.setProprietorName2("");
        this.setProprietorName3("");
        this.setProprietorName4("");
    }

    //Getter And Setter
    public String getJtMsg() {
        return jtMsg;
    }

    public void setJtMsg(String jtMsg) {
        this.jtMsg = jtMsg;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getDob1() {
        return dob1;
    }

    public void setDob1(String dob1) {
        this.dob1 = dob1;
    }

    public String getPanNo1() {
        return panNo1;
    }

    public void setPanNo1(String panNo1) {
        this.panNo1 = panNo1;
    }

    public String getFatherName1() {
        return fatherName1;
    }

    public void setFatherName1(String fatherName1) {
        this.fatherName1 = fatherName1;
    }

    public String getOccupation1() {
        return occupation1;
    }

    public void setOccupation1(String occupation1) {
        this.occupation1 = occupation1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getDob2() {
        return dob2;
    }

    public void setDob2(String dob2) {
        this.dob2 = dob2;
    }

    public String getPanNo2() {
        return panNo2;
    }

    public void setPanNo2(String panNo2) {
        this.panNo2 = panNo2;
    }

    public String getFatherName2() {
        return fatherName2;
    }

    public void setFatherName2(String fatherName2) {
        this.fatherName2 = fatherName2;
    }

    public String getOccupation2() {
        return occupation2;
    }

    public void setOccupation2(String occupation2) {
        this.occupation2 = occupation2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getDob3() {
        return dob3;
    }

    public void setDob3(String dob3) {
        this.dob3 = dob3;
    }

    public String getPanNo3() {
        return panNo3;
    }

    public void setPanNo3(String panNo3) {
        this.panNo3 = panNo3;
    }

    public String getFatherName3() {
        return fatherName3;
    }

    public void setFatherName3(String fatherName3) {
        this.fatherName3 = fatherName3;
    }

    public String getOccupation3() {
        return occupation3;
    }

    public void setOccupation3(String occupation3) {
        this.occupation3 = occupation3;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getDob4() {
        return dob4;
    }

    public void setDob4(String dob4) {
        this.dob4 = dob4;
    }

    public String getPanNo4() {
        return panNo4;
    }

    public void setPanNo4(String panNo4) {
        this.panNo4 = panNo4;
    }

    public String getFatherName4() {
        return fatherName4;
    }

    public void setFatherName4(String fatherName4) {
        this.fatherName4 = fatherName4;
    }

    public String getOccupation4() {
        return occupation4;
    }

    public void setOccupation4(String occupation4) {
        this.occupation4 = occupation4;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(String primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getProprietorName1() {
        return proprietorName1;
    }

    public void setProprietorName1(String proprietorName1) {
        this.proprietorName1 = proprietorName1;
    }

    public String getProprietorName2() {
        return proprietorName2;
    }

    public void setProprietorName2(String proprietorName2) {
        this.proprietorName2 = proprietorName2;
    }

    public String getProprietorName3() {
        return proprietorName3;
    }

    public void setProprietorName3(String proprietorName3) {
        this.proprietorName3 = proprietorName3;
    }

    public String getProprietorName4() {
        return proprietorName4;
    }

    public void setProprietorName4(String proprietorName4) {
        this.proprietorName4 = proprietorName4;
    }
    
    
}
