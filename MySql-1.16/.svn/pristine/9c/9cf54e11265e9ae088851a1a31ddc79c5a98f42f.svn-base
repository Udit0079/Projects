/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.AssetsTable;
import com.cbs.dto.LoanTable;
import com.cbs.dto.customer.CodebookTO;
import com.cbs.dto.loan.CbsKycAssetsTO;
import com.cbs.dto.loan.CbsKycDetailsTO;
import com.cbs.dto.loan.CbsKycLoanTO;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public class KycDetails {

    CustomerDetail customerDetail;
    CustomerMasterDelegate masterDelegate;
    String educationDetails;
    List<SelectItem> educationDetailsList;
    String creditCardDetails;
    List<SelectItem> creditCardDetailsList;
    String relationWithDirector;
    List<SelectItem> relationWithDirectorList;
    String relativeInBank;
    List<SelectItem> relativeInBankList;
    String abroadDetails;
    List<SelectItem> abroadDetailsList;
    String assetsType;
    List<SelectItem> assetsTypeList;
    String assetsOwnership;
    List<SelectItem> assetsOwnershipList;
    String assetValue;
    List<SelectItem> assetValueList;
    String existLoans;
    List<SelectItem> existLoansList;
    String medicalInsurance;
    List<SelectItem> medicalInsuranceList;
    boolean spouse;
    boolean parents;
    boolean children;
    String male1;
    String male2;
    String male3;
    String male4;
    String male5;
    String totalMale;
    String female1;
    String female2;
    String female3;
    String female4;
    String female5;
    String totalFemale;
    String name1;
    String name2;
    String name3;
    String address1;
    String address2;
    String address3;
    String abroadTime;
    String childrenFlag = "none";
    String assetFlag = "none";
    String noOfChildren;
    private List<LoanTable> listLoanTable;
    List<AssetsTable> listAssetsTable1 = new ArrayList<AssetsTable>();
    private AssetsTable currentItemAssetTavle = new AssetsTable();
    private List<AssetsTable> listAssetsTable;
    AssetsTable assetsTable;
    int selectCountAssetsTable = 0;
    private int currentRowAssetTable;
    int count2AssetTable = 0;
    int count1AssetTable = 0;
    String sNoAssets;
    String sNoLoan;
    LoanTable loanTable;
    int selectCountLoanTable = 0;
    List<LoanTable> listLoanTable1 = new ArrayList<LoanTable>();
    private LoanTable currentItemLoanTable = new LoanTable();
    private int currentRowLoanTable;
    int count2LoanTable = 0;
    int count1LoanTable = 0;
    BigDecimal loanAmount;
    String existLoanAmtFlag;
    String btnAdd2Flag;

    /** Creates a new instance of KycDetails */
    public KycDetails() {
        initializeForm();
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public String getBtnAdd2Flag() {
        return btnAdd2Flag;
    }

    public void setBtnAdd2Flag(String btnAdd2Flag) {
        this.btnAdd2Flag = btnAdd2Flag;
    }

    public String getExistLoanAmtFlag() {
        return existLoanAmtFlag;
    }

    public void setExistLoanAmtFlag(String existLoanAmtFlag) {
        this.existLoanAmtFlag = existLoanAmtFlag;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getCount1AssetTable() {
        return count1AssetTable;
    }

    public void setCount1AssetTable(int count1AssetTable) {
        this.count1AssetTable = count1AssetTable;
    }

    public int getCount1LoanTable() {
        return count1LoanTable;
    }

    public void setCount1LoanTable(int count1LoanTable) {
        this.count1LoanTable = count1LoanTable;
    }

    public int getCount2AssetTable() {
        return count2AssetTable;
    }

    public void setCount2AssetTable(int count2AssetTable) {
        this.count2AssetTable = count2AssetTable;
    }

    public int getCount2LoanTable() {
        return count2LoanTable;
    }

    public void setCount2LoanTable(int count2LoanTable) {
        this.count2LoanTable = count2LoanTable;
    }

    public LoanTable getCurrentItemLoanTable() {
        return currentItemLoanTable;
    }

    public void setCurrentItemLoanTable(LoanTable currentItemLoanTable) {
        this.currentItemLoanTable = currentItemLoanTable;
    }

    public int getCurrentRowAssetTable() {
        return currentRowAssetTable;
    }

    public void setCurrentRowAssetTable(int currentRowAssetTable) {
        this.currentRowAssetTable = currentRowAssetTable;
    }

    public int getCurrentRowLoanTable() {
        return currentRowLoanTable;
    }

    public void setCurrentRowLoanTable(int currentRowLoanTable) {
        this.currentRowLoanTable = currentRowLoanTable;
    }

    public List<LoanTable> getListLoanTable1() {
        return listLoanTable1;
    }

    public void setListLoanTable1(List<LoanTable> listLoanTable1) {
        this.listLoanTable1 = listLoanTable1;
    }

    public LoanTable getLoanTable() {
        return loanTable;
    }

    public void setLoanTable(LoanTable loanTable) {
        this.loanTable = loanTable;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public String getsNoAssets() {
        return sNoAssets;
    }

    public void setsNoAssets(String sNoAssets) {
        this.sNoAssets = sNoAssets;
    }

    public String getsNoLoan() {
        return sNoLoan;
    }

    public void setsNoLoan(String sNoLoan) {
        this.sNoLoan = sNoLoan;
    }

    public int getSelectCountLoanTable() {
        return selectCountLoanTable;
    }

    public void setSelectCountLoanTable(int selectCountLoanTable) {
        this.selectCountLoanTable = selectCountLoanTable;
    }

    public String getAssetFlag() {
        return assetFlag;
    }

    public void setAssetFlag(String assetFlag) {
        this.assetFlag = assetFlag;
    }

    public AssetsTable getAssetsTable() {
        return assetsTable;
    }

    public void setAssetsTable(AssetsTable assetsTable) {
        this.assetsTable = assetsTable;
    }

    public AssetsTable getCurrentItemAssetTavle() {
        return currentItemAssetTavle;
    }

    public void setCurrentItemAssetTavle(AssetsTable currentItemAssetTavle) {
        this.currentItemAssetTavle = currentItemAssetTavle;
    }

    public List<AssetsTable> getListAssetsTable() {
        return listAssetsTable;
    }

    public void setListAssetsTable(List<AssetsTable> listAssetsTable) {
        this.listAssetsTable = listAssetsTable;
    }

    public List<AssetsTable> getListAssetsTable1() {
        return listAssetsTable1;
    }

    public void setListAssetsTable1(List<AssetsTable> listAssetsTable1) {
        this.listAssetsTable1 = listAssetsTable1;
    }

    public String getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(String noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public int getSelectCountAssetsTable() {
        return selectCountAssetsTable;
    }

    public void setSelectCountAssetsTable(int selectCountAssetsTable) {
        this.selectCountAssetsTable = selectCountAssetsTable;
    }

    public String getChildrenFlag() {
        return childrenFlag;
    }

    public void setChildrenFlag(String childrenFlag) {
        this.childrenFlag = childrenFlag;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getAbroadDetails() {
        return abroadDetails;
    }

    public void setAbroadDetails(String abroadDetails) {
        this.abroadDetails = abroadDetails;
    }

    public List<SelectItem> getAbroadDetailsList() {
        return abroadDetailsList;
    }

    public void setAbroadDetailsList(List<SelectItem> abroadDetailsList) {
        this.abroadDetailsList = abroadDetailsList;
    }

    public String getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(String assetValue) {
        this.assetValue = assetValue;
    }

    public List<SelectItem> getAssetValueList() {
        return assetValueList;
    }

    public void setAssetValueList(List<SelectItem> assetValueList) {
        this.assetValueList = assetValueList;
    }

    public String getAssetsOwnership() {
        return assetsOwnership;
    }

    public void setAssetsOwnership(String assetsOwnership) {
        this.assetsOwnership = assetsOwnership;
    }

    public List<SelectItem> getAssetsOwnershipList() {
        return assetsOwnershipList;
    }

    public void setAssetsOwnershipList(List<SelectItem> assetsOwnershipList) {
        this.assetsOwnershipList = assetsOwnershipList;
    }

    public String getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(String assetsType) {
        this.assetsType = assetsType;
    }

    public List<SelectItem> getAssetsTypeList() {
        return assetsTypeList;
    }

    public void setAssetsTypeList(List<SelectItem> assetsTypeList) {
        this.assetsTypeList = assetsTypeList;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    public List<SelectItem> getCreditCardDetailsList() {
        return creditCardDetailsList;
    }

    public void setCreditCardDetailsList(List<SelectItem> creditCardDetailsList) {
        this.creditCardDetailsList = creditCardDetailsList;
    }

    public String getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(String educationDetails) {
        this.educationDetails = educationDetails;
    }

    public List<SelectItem> getEducationDetailsList() {
        return educationDetailsList;
    }

    public void setEducationDetailsList(List<SelectItem> educationDetailsList) {
        this.educationDetailsList = educationDetailsList;
    }

    public String getExistLoans() {
        return existLoans;
    }

    public void setExistLoans(String existLoans) {
        this.existLoans = existLoans;
    }

    public List<SelectItem> getExistLoansList() {
        return existLoansList;
    }

    public void setExistLoansList(List<SelectItem> existLoansList) {
        this.existLoansList = existLoansList;
    }

    public String getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(String medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public List<SelectItem> getMedicalInsuranceList() {
        return medicalInsuranceList;
    }

    public void setMedicalInsuranceList(List<SelectItem> medicalInsuranceList) {
        this.medicalInsuranceList = medicalInsuranceList;
    }

    public String getRelationWithDirector() {
        return relationWithDirector;
    }

    public void setRelationWithDirector(String relationWithDirector) {
        this.relationWithDirector = relationWithDirector;
    }

    public List<SelectItem> getRelationWithDirectorList() {
        return relationWithDirectorList;
    }

    public void setRelationWithDirectorList(List<SelectItem> relationWithDirectorList) {
        this.relationWithDirectorList = relationWithDirectorList;
    }

    public String getRelativeInBank() {
        return relativeInBank;
    }

    public void setRelativeInBank(String relativeInBank) {
        this.relativeInBank = relativeInBank;
    }

    public List<SelectItem> getRelativeInBankList() {
        return relativeInBankList;
    }

    public void setRelativeInBankList(List<SelectItem> relativeInBankList) {
        this.relativeInBankList = relativeInBankList;
    }

    public String getAbroadTime() {
        return abroadTime;
    }

    public void setAbroadTime(String abroadTime) {
        this.abroadTime = abroadTime;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public String getFemale1() {
        return female1;
    }

    public void setFemale1(String female1) {
        this.female1 = female1;
    }

    public String getFemale2() {
        return female2;
    }

    public void setFemale2(String female2) {
        this.female2 = female2;
    }

    public String getFemale3() {
        return female3;
    }

    public void setFemale3(String female3) {
        this.female3 = female3;
    }

    public String getFemale4() {
        return female4;
    }

    public void setFemale4(String female4) {
        this.female4 = female4;
    }

    public String getFemale5() {
        return female5;
    }

    public void setFemale5(String female5) {
        this.female5 = female5;
    }

    public String getMale1() {
        return male1;
    }

    public void setMale1(String male1) {
        this.male1 = male1;
    }

    public String getMale2() {
        return male2;
    }

    public void setMale2(String male2) {
        this.male2 = male2;
    }

    public String getMale3() {
        return male3;
    }

    public void setMale3(String male3) {
        this.male3 = male3;
    }

    public String getMale4() {
        return male4;
    }

    public void setMale4(String male4) {
        this.male4 = male4;
    }

    public String getMale5() {
        return male5;
    }

    public void setMale5(String male5) {
        this.male5 = male5;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public boolean isParents() {
        return parents;
    }

    public void setParents(boolean parents) {
        this.parents = parents;
    }

    public boolean isSpouse() {
        return spouse;
    }

    public void setSpouse(boolean spouse) {
        this.spouse = spouse;
    }

    public String getTotalFemale() {
        return totalFemale;
    }

    public void setTotalFemale(String totalFemale) {
        this.totalFemale = totalFemale;
    }

    public String getTotalMale() {
        return totalMale;
    }

    public void setTotalMale(String totalMale) {
        this.totalMale = totalMale;
    }

    public List<LoanTable> getListLoanTable() {
        return listLoanTable;
    }

    public void setListLoanTable(List<LoanTable> listLoanTable) {
        this.listLoanTable = listLoanTable;
    }

    public void onclickChildren() {
        try {
            if (this.children == true) {
                childrenFlag = "";
            } else if (this.children == false) {
                childrenFlag = "none";
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurMale() {
        try {
            if (maleValidation() == false) {
                getCustomerDetail().setErrorMsg("Invalid Entry In No. Of Males");
                return;
            }

            getCustomerDetail().setErrorMsg("");
            int m1, m2, m3, m4, m5;
            if (this.male1.equals("") || this.male1 == null) {
                m1 = 0;
            } else {
                m1 = Integer.parseInt(this.male1);
            }
            if (this.male2.equals("") || this.male2 == null) {
                m2 = 0;
            } else {
                m2 = Integer.parseInt(this.male2);
            }
            if (this.male3.equals("") || this.male3 == null) {
                m3 = 0;
            } else {
                m3 = Integer.parseInt(this.male3);
            }
            if (this.male4.equals("") || this.male4 == null) {
                m4 = 0;
            } else {
                m4 = Integer.parseInt(this.male4);
            }
            if (this.male5.equals("") || this.male5 == null) {
                m5 = 0;
            } else {
                m5 = Integer.parseInt(this.male5);
            }
            this.setTotalMale(String.valueOf(m1 + m2 + m3 + m4 + m5));
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }

    }

    public void onblurFemale() {
        try {
            if (femaleValidation() == false) {
                getCustomerDetail().setErrorMsg("Invalid Entry In No. Of Females");
                return;
            }
            getCustomerDetail().setErrorMsg("");
            int f1, f2, f3, f4, f5;
            if (this.female1.equals("") || this.female1 == null) {
                f1 = 0;
            } else {
                f1 = Integer.parseInt(this.female1);
            }
            if (this.female2.equals("") || this.female2 == null) {
                f2 = 0;
            } else {
                f2 = Integer.parseInt(this.female2);
            }
            if (this.female3.equals("") || this.female3 == null) {
                f3 = 0;
            } else {
                f3 = Integer.parseInt(this.female3);
            }
            if (this.female4.equals("") || this.female4 == null) {
                f4 = 0;
            } else {
                f4 = Integer.parseInt(this.female4);
            }
            if (this.female5.equals("") || this.female5 == null) {
                f5 = 0;
            } else {
                f5 = Integer.parseInt(this.female5);
            }
            this.setTotalFemale(String.valueOf(f1 + f2 + f3 + f4 + f5));
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }

    }

    public boolean maleValidation() {
        if (!this.male1.equals("")) {
            if (!this.male1.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.male1.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.male1) < 0) {
                return false;
            }
        }
        if (!this.male2.equals("")) {
            if (!this.male2.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.male2.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.male2) < 0) {
                return false;
            }
        }
        if (!this.male3.equals("")) {
            if (!this.male3.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.male3.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.male3) < 0) {
                return false;
            }
        }
        if (!this.male4.equals("")) {
            if (!this.male4.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.male4.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.male4) < 0) {
                return false;
            }
        }
        if (!this.male5.equals("")) {
            if (!this.male5.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.male5.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.male5) < 0) {
                return false;
            }

        }


        return true;
    }

    public boolean femaleValidation() {

        if (!this.female1.equals("")) {
            if (!this.female1.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.female1.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.female1) < 0) {
                return false;
            }
        }

        if (!this.female2.equals("")) {
            if (!this.female2.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.female2.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.female2) < 0) {
                return false;
            }
        }

        if (!this.female3.equals("")) {
            if (!this.female3.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.female3.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.female3) < 0) {
                return false;
            }
        }

        if (!this.female4.equals("")) {
            if (!this.female4.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.female4.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.female4) < 0) {
                return false;
            }
        }

        if (!this.female5.equals("")) {
            if (!this.female5.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                return false;
            }
            if (this.female5.contains(".")) {
                return false;
            }
            if (Integer.parseInt(this.female5) < 0) {
                return false;
            }
        }
        return true;
    }

    public void onblurAssetsType() {
        try {
            assetFlag = "";
            List list1 = new ArrayList();
            List list2 = new ArrayList();
            if (!this.assetsType.equals("0")) {
                getCustomerDetail().setErrorMsg("");
                List<CodebookTO> assetOwnership = masterDelegate.getAssetOwnership(assetsType);
                assetsOwnershipList = new ArrayList<SelectItem>();
                assetsOwnershipList.add(new SelectItem("0", "--Select--"));
                for (CodebookTO codeBookTo : assetOwnership) {
                    if (codeBookTo.getCodebookPKTO().getCode() != 0) {
                        assetsOwnershipList.add(new SelectItem(codeBookTo.getDescription(), codeBookTo.getDescription()));
                    }
                }

                List<CodebookTO> getAssetValue = masterDelegate.getAssetOwnership("94");
                assetValueList = new ArrayList<SelectItem>();
                assetValueList.add(new SelectItem("0", "--Select--"));
                for (CodebookTO codeBookTo : getAssetValue) {
                    if (codeBookTo.getCodebookPKTO().getCode() >= 1 && codeBookTo.getCodebookPKTO().getCode() <= 4) {
                        assetValueList.add(new SelectItem(codeBookTo.getDescription()));
                    }
                }

            } else {
                this.setAssetValue("0");
                this.setAssetsOwnership("0");
                assetFlag = "none";
                getCustomerDetail().setErrorMsg("Please Select Assets Type");
                return;
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void clickOnAdd1() {
        try {
            if (this.assetsType.equals("0") || this.assetValue.equals("0") || this.assetsOwnership.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Fill Correct Entries To Add");
                return;
            } else {
                List<AssetsTable> listRpi = listAssetsTable;
                assetsTable = new AssetsTable();
                if (getCustomerDetail().getFunction().equalsIgnoreCase("a") || getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
                    if (selectCountAssetsTable == 0) {
                        for (int i = 0; i < listRpi.size(); i++) {
                            String rpiCustomerId = listRpi.get(i).getAssets();
                            if (this.assetsOwnership.equalsIgnoreCase(rpiCustomerId)) {
                                getCustomerDetail().setErrorMsg("Entry Already Exists In The Assets Table");
                                return;
                            }
                        }
                    }
                }
                getCustomerDetail().setErrorMsg("");
                AssetsTable rpiTab = new AssetsTable();
                List<CodebookTO> getAssetValue = masterDelegate.getAssetOwnership(assetsType);
                int var = 0;
                for (CodebookTO codeBookTo : getAssetValue) {
                    if (var == 0) {
                        rpiTab.setAstType(codeBookTo.getDescription());
                    } else {
                        break;
                    }
                    var++;
                }
                rpiTab.setAssets(this.assetsOwnership);
                rpiTab.setAstValue(this.assetValue);
                String description="";
                List<CodebookTO> assetOwnership = masterDelegate.getAssetOwnership(assetsType);
                if(!assetOwnership.isEmpty())
                {
                    description=assetOwnership.get(0).getDescription();
                }
                    if (getCustomerDetail().getFunction().equalsIgnoreCase("a")) {
                    rpiTab.setSaveUpdateCounter("Save");
                    this.setAssetValue("0");
                    this.setAssetsOwnership("0");
                    this.setAssetsType("0");

                }
                listAssetsTable.add(rpiTab);
                if (getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
                    if (selectCountAssetsTable != 1) {
                        for (int i = 0; i < listRpi.size(); i++) {
                            String rpiCusId = listRpi.get(i).getAssets();
                            if (!rpiCusId.equalsIgnoreCase(this.assetsOwnership)) {
                                rpiTab.setSaveUpdateCounter("Save");
                                listAssetsTable1.add(rpiTab);
                                this.setAssetValue("0");
                                this.setAssetsOwnership("0");
                                this.setAssetsType("0");
                                return;
                            }
                        }
                    }
                    if (currentItemAssetTavle.getAssets() != null) {
                        if (!currentItemAssetTavle.getAstValue().equalsIgnoreCase(this.assetValue) || !currentItemAssetTavle.getAssets().equalsIgnoreCase(this.assetsOwnership) || !currentItemAssetTavle.getAstType().equalsIgnoreCase(description)) {
                            rpiTab.setSaveUpdateCounter("Update");
                            rpiTab.setsNo1(sNoAssets);
                            listAssetsTable1.add(rpiTab);
                            this.setAssetValue("0");
                            this.setAssetsOwnership("0");
                            this.setAssetsType("0");
                        }
                        selectCountAssetsTable = 0;
                    } else {
                        rpiTab.setSaveUpdateCounter("Save");
                        listAssetsTable1.add(rpiTab);
                        this.setAssetValue("0");
                        this.setAssetsOwnership("0");
                        this.setAssetsType("0");

                    }
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow3(ActionEvent event) {

        String custAssets = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("custAssets"));
        currentRowAssetTable = currentRowAssetTable + 1;
        currentRowAssetTable = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (AssetsTable item : listAssetsTable) {
            if (item.getAssets().equalsIgnoreCase(custAssets)) {
                currentItemAssetTavle = item;
                break;
            }
        }
    }

    public void selectAssets() {
        try {
            getCustomerDetail().setErrorMsg("");
            assetFlag = "";
            selectCountAssetsTable = 1;
            if (this.assetsOwnership.equalsIgnoreCase(currentItemAssetTavle.getAssets())) {
                count2AssetTable = count1AssetTable;
                count1AssetTable = count1AssetTable + 1;
                if (count2AssetTable != count1AssetTable) {
                    clickOnAdd1();
                }
            } else {
                count1AssetTable = 0;
            }
            sNoAssets = currentItemAssetTavle.getsNo1();
            CodebookTO assetsByDescription = masterDelegate.getAssetsByDescription(currentItemAssetTavle.getAstType());
            setAssetsType(String.valueOf(assetsByDescription.getCodebookPKTO().getGroupCode()));
            assetsOwnershipList = new ArrayList<SelectItem>();
            assetsOwnershipList.add(new SelectItem(currentItemAssetTavle.getAssets(), currentItemAssetTavle.getAssets()));
            assetValueList = new ArrayList<SelectItem>();
            assetValueList.add(new SelectItem(currentItemAssetTavle.getAstValue(), currentItemAssetTavle.getAstValue()));
            listAssetsTable.remove(currentItemAssetTavle);
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void deleteGridAssets() {
        listAssetsTable.remove(currentItemAssetTavle);
    }

    public void deleteGridLoans() {
        listLoanTable.remove(currentItemLoanTable);
    }

    public void selectLoans() {
        try {
            selectCountLoanTable = 1;
            if (this.existLoans.equalsIgnoreCase(currentItemLoanTable.getLoanType())) {
                count2LoanTable = count1LoanTable;
                count1LoanTable = count1LoanTable + 1;
                if (count2LoanTable != count1LoanTable) {
                    clickOnAdd2();
                }
            } else {
                count1LoanTable = 0;
            }

            sNoLoan = currentItemLoanTable.getsNo2();
            this.setExistLoans(currentItemLoanTable.getLoanType());
            this.setLoanAmount(new BigDecimal(currentItemLoanTable.getLoanValue()));
            existLoanAmtFlag = "false";
            btnAdd2Flag = "false";
            listLoanTable.remove(currentItemLoanTable);
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void clickOnAdd2() {
        try {

            if (this.existLoans.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Fill Correct Entries Of Loan To Add");
                return;
            }
            if (!this.existLoans.equals("0") && (this.loanAmount.toString().equals("0") || this.loanAmount.toString().equals(""))) {
                getCustomerDetail().setErrorMsg("Please Fill Loan Amount Field");
                return;
            }
            if (!this.loanAmount.toString().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                getCustomerDetail().setErrorMsg("Only Numeric Value Is Allowed In Loan Amount");
                return;
            }
            List<LoanTable> listRpi = listLoanTable;
            loanTable = new LoanTable();
            if (getCustomerDetail().getFunction().equalsIgnoreCase("a") || getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
                if (selectCountLoanTable == 0) {
                    for (int i = 0; i < listRpi.size(); i++) {
                        String rpiCustomerId = listRpi.get(i).getLoanType();
                        if (this.existLoans.equalsIgnoreCase(rpiCustomerId)) {
                            getCustomerDetail().setErrorMsg("Entry Already Exists In The Loan Table");
                            return;
                        }
                    }
                }
            }
            getCustomerDetail().setErrorMsg("");
            LoanTable rpiTab = new LoanTable();

            rpiTab.setLoanType(this.existLoans);
            rpiTab.setLoanValue(this.loanAmount.toString());


            if (getCustomerDetail().getFunction().equalsIgnoreCase("a")) {
                rpiTab.setSaveUpdateCounter("Save");
                this.setExistLoans("0");
                this.setLoanAmount(new BigDecimal("0"));
            }
            listLoanTable.add(rpiTab);
            if (getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
                if (selectCountLoanTable != 1) {
                    for (int i = 0; i < listRpi.size(); i++) {
                        String rpiCusId = listRpi.get(i).getLoanType();
                        if (!rpiCusId.equalsIgnoreCase(this.existLoans)) {
                            rpiTab.setSaveUpdateCounter("Save");
                            listLoanTable1.add(rpiTab);
                            this.setExistLoans("0");
                            this.setLoanAmount(new BigDecimal("0"));
                            return;
                        }
                    }
                }
                if (currentItemLoanTable.getLoanType() != null) {
                    if (!currentItemLoanTable.getLoanType().equalsIgnoreCase(this.existLoans) || !currentItemLoanTable.getLoanValue().equalsIgnoreCase(this.loanAmount.toString())) {
                        rpiTab.setSaveUpdateCounter("Update");
                        rpiTab.setsNo2(sNoLoan);
                        listLoanTable1.add(rpiTab);
                        this.setExistLoans("0");
                        this.setLoanAmount(new BigDecimal("0"));
                    }
                    selectCountLoanTable = 0;
                } else {
                    rpiTab.setSaveUpdateCounter("Save");
                    listLoanTable1.add(rpiTab);
                    this.setExistLoans("0");
                    this.setLoanAmount(new BigDecimal("0"));
                }
            }

        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow4(ActionEvent event) {

        String custLoans = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("custLoans"));
        currentRowLoanTable = currentRowLoanTable + 1;
        currentRowLoanTable = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (LoanTable item : listLoanTable) {
            if (item.getLoanType().equalsIgnoreCase(custLoans)) {
                currentItemLoanTable = item;
                break;
            }
        }
    }

    public void onblurExistingLoans() {
        try {
            if (this.existLoans.equals("0")) {
                existLoanAmtFlag = "true";
                btnAdd2Flag = "true";
                this.setLoanAmount(new BigDecimal("0"));
            } else if (this.existLoans.equalsIgnoreCase("NONE")) {
                existLoanAmtFlag = "true";
                btnAdd2Flag = "false";
                this.setLoanAmount(new BigDecimal("0"));
            } else {
                existLoanAmtFlag = "false";
                btnAdd2Flag = "false";
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            listAssetsTable = new ArrayList<AssetsTable>();
            listLoanTable = new ArrayList<LoanTable>();
            assetsTypeList = new ArrayList<SelectItem>();
            assetsTypeList.add(new SelectItem("0", "--Select--"));
            List<CodebookTO> assetTypeList = masterDelegate.getAssetType();
            for (CodebookTO codeBookTo : assetTypeList) {
                if (codeBookTo.getCodebookPKTO().getCode() == 0) {
                    assetsTypeList.add(new SelectItem(codeBookTo.getCodebookPKTO().getGroupCode(), codeBookTo.getDescription()));
                }
            }
            existLoansList = new ArrayList<SelectItem>();
            existLoansList.add(new SelectItem("0", "--Select--"));
            List<CodebookTO> existLoanList = masterDelegate.getAssetOwnership("91");
            String description = "";
            for (CodebookTO codeBookTo : existLoanList) {
                if (codeBookTo.getCodebookPKTO().getCode() == 0) {
                    description = "NONE";
                } else {
                    description = codeBookTo.getDescription();
                }
                existLoansList.add(new SelectItem(description));

            }
            educationDetailsList = new ArrayList<SelectItem>();
            educationDetailsList.add(new SelectItem("0", "--Select--"));
            educationDetailsList.add(new SelectItem("UP TO HSC", "UP TO HSC"));
            educationDetailsList.add(new SelectItem("GRADUATE", "GRADUATE"));
            educationDetailsList.add(new SelectItem("POST-GRADUATE", "POST-GRADUATE"));
            educationDetailsList.add(new SelectItem("PROFESSIONAL", "PROFESSIONAL"));
            educationDetailsList.add(new SelectItem("OTHERS", "OTHERS"));

            relativeInBankList = new ArrayList<SelectItem>();
            relativeInBankList.add(new SelectItem("0", "--Select--"));
            relativeInBankList.add(new SelectItem("Y", "Yes"));
            relativeInBankList.add(new SelectItem("N", "No"));

            relationWithDirectorList = new ArrayList<SelectItem>();
            relationWithDirectorList.add(new SelectItem("0", "--Select--"));
            relationWithDirectorList.add(new SelectItem("Y", "Yes"));
            relationWithDirectorList.add(new SelectItem("N", "No"));

            creditCardDetailsList = new ArrayList<SelectItem>();
            creditCardDetailsList.add(new SelectItem("0", "--Select--"));
            creditCardDetailsList.add(new SelectItem("Y", "Yes"));
            creditCardDetailsList.add(new SelectItem("N", "No"));

            medicalInsuranceList = new ArrayList<SelectItem>();
            medicalInsuranceList.add(new SelectItem("0", "--Select--"));
            medicalInsuranceList.add(new SelectItem("Y", "Yes"));
            medicalInsuranceList.add(new SelectItem("N", "No"));

            assetsOwnershipList = new ArrayList<SelectItem>();
            assetsOwnershipList.add(new SelectItem("0", "--Select--"));
            assetValueList = new ArrayList<SelectItem>();
            assetValueList.add(new SelectItem("0", "--Select--"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectFieldValues() {
        try {
            CbsKycDetailsTO kycDetailsTO = masterDelegate.getKycDetailsByCustId(getCustomerDetail().getCustId());
            if (kycDetailsTO != null) {
                if (kycDetailsTO.getEduDetails() == null || kycDetailsTO.getEduDetails().equalsIgnoreCase("")) {
                    this.setEducationDetails("0");
                } else {
                    this.setEducationDetails(kycDetailsTO.getEduDetails());
                }

                if (kycDetailsTO.getDependents() == null || kycDetailsTO.getDependents().equalsIgnoreCase("")) {
                } else {
                    if (!kycDetailsTO.getDependents().contains("~")) {
                        if (kycDetailsTO.getDependents().equalsIgnoreCase("NONE")) {
                            this.setSpouse(false);
                            this.setParents(false);
                            this.setChildren(false);

                        } else if (kycDetailsTO.getDependents().equalsIgnoreCase("SPOUSE")) {
                            this.setSpouse(true);
                            this.setParents(false);
                            this.setChildren(false);
                        } else if (kycDetailsTO.getDependents().equalsIgnoreCase("PARENTS")) {
                            this.setSpouse(false);
                            this.setParents(true);
                            this.setChildren(false);

                        } else if (kycDetailsTO.getDependents().equalsIgnoreCase("CHILDREN")) {
                            this.setSpouse(false);
                            this.setParents(false);
                            this.setChildren(true);
                        }
                    } else if (kycDetailsTO.getDependents().contains("~")) {
                        String array[] = kycDetailsTO.getDependents().split("~");
                        if (array.length == 2) {
                            if (array[0].equalsIgnoreCase("SPOUSE") && array[1].equalsIgnoreCase("PARENTS")) {
                                this.setSpouse(true);
                                this.setParents(true);
                                this.setChildren(false);
                            } else if (array[0].equalsIgnoreCase("SPOUSE") && array[1].equalsIgnoreCase("CHILDREN")) {
                                this.setSpouse(true);
                                this.setParents(false);
                                this.setChildren(true);
                            }
                        } else if (array.length == 3) {
                            this.setSpouse(true);
                            this.setParents(true);
                            this.setChildren(true);
                        }
                    }

                }
                if (kycDetailsTO.getNoChild() == null || kycDetailsTO.getNoChild().toString().equalsIgnoreCase("0")) {
                    this.setChildrenFlag("none");
                } else {
                    this.setChildrenFlag("");
                    this.setNoOfChildren(kycDetailsTO.getNoChild().toString());
                }
                if (kycDetailsTO.getNoMales10() == null) {
                    this.setMale1("");
                } else {
                    this.setMale1(kycDetailsTO.getNoMales10().toString());
                }
                if (kycDetailsTO.getNoMales20() == null) {
                    this.setMale2("");
                } else {
                    this.setMale2(kycDetailsTO.getNoMales20().toString());
                }
                if (kycDetailsTO.getNoMales45() == null) {
                    this.setMale3("");
                } else {
                    this.setMale3(kycDetailsTO.getNoMales45().toString());
                }
                if (kycDetailsTO.getNoMales60() == null) {
                    this.setMale4("");
                } else {
                    this.setMale4(kycDetailsTO.getNoMales60().toString());
                }
                if (kycDetailsTO.getNoMalesAbove61() == null) {
                    this.setMale5("");
                } else {
                    this.setMale5(kycDetailsTO.getNoMalesAbove61().toString());
                }
                if (kycDetailsTO.getNoFem10() == null) {
                    this.setFemale1("");
                } else {
                    this.setFemale1(kycDetailsTO.getNoFem10().toString());
                }
                if (kycDetailsTO.getNoFem20() == null) {
                    this.setFemale2("");
                } else {
                    this.setFemale2(kycDetailsTO.getNoFem20().toString());
                }
                if (kycDetailsTO.getNoFem45() == null) {
                    this.setFemale3("");
                } else {
                    this.setFemale3(kycDetailsTO.getNoFem45().toString());
                }
                if (kycDetailsTO.getNoFem60() == null) {
                    this.setFemale4("");
                } else {
                    this.setFemale4(kycDetailsTO.getNoFem60().toString());
                }
                if (kycDetailsTO.getNoFemAbove61() == null) {
                    this.setFemale5("");
                } else {
                    this.setFemale5(kycDetailsTO.getNoFemAbove61().toString());
                }
                if (kycDetailsTO.getAbRelName1() == null || kycDetailsTO.getAbRelName1().equalsIgnoreCase("")) {
                    this.setName1("");
                } else {
                    this.setName1(kycDetailsTO.getAbRelName1());
                }
                if (kycDetailsTO.getAbRelName2() == null || kycDetailsTO.getAbRelName2().equalsIgnoreCase("")) {
                    this.setName2("");
                } else {
                    this.setName2(kycDetailsTO.getAbRelName2());
                }
                if (kycDetailsTO.getAbRelName3() == null || kycDetailsTO.getAbRelName3().equalsIgnoreCase("")) {
                    this.setName3("");
                } else {
                    this.setName3(kycDetailsTO.getAbRelName3());
                }
                if (kycDetailsTO.getAbRelAdd1() == null || kycDetailsTO.getAbRelAdd1().equalsIgnoreCase("")) {
                    this.setAddress1("");
                } else {
                    this.setAddress1(kycDetailsTO.getAbRelAdd1());
                }

                if (kycDetailsTO.getAbRelAdd2() == null || kycDetailsTO.getAbRelAdd2().equalsIgnoreCase("")) {
                    this.setAddress2("");
                } else {
                    this.setAddress2(kycDetailsTO.getAbRelAdd2());
                }
                if (kycDetailsTO.getAbRelAdd3() == null || kycDetailsTO.getAbRelAdd3().equalsIgnoreCase("")) {
                    this.setAddress3("");
                } else {
                    this.setAddress3(kycDetailsTO.getAbRelAdd3());
                }
                if (kycDetailsTO.getAbVisited() == null) {
                    this.setAbroadTime("");
                } else {
                    this.setAbroadTime(kycDetailsTO.getAbVisited().toString());
                }
                if (kycDetailsTO.getBankRel() == null) {
                    this.setRelativeInBank("");
                } else {
                    this.setRelativeInBank(kycDetailsTO.getBankRel().toString());
                }
                if (kycDetailsTO.getDirRel() == null) {
                    this.setRelationWithDirector("");
                } else {
                    this.setRelationWithDirector(kycDetailsTO.getDirRel().toString());
                }
                if (kycDetailsTO.getCreditcard() == null) {
                    this.setCreditCardDetails("");
                } else {
                    this.setCreditCardDetails(kycDetailsTO.getCreditcard().toString());
                }
                if (kycDetailsTO.getMedinsurence() == null || kycDetailsTO.getMedinsurence().toString().equalsIgnoreCase("")) {
                    this.setMedicalInsurance("");
                } else {
                    this.setMedicalInsurance(kycDetailsTO.getMedinsurence().toString());
                }

            }
            if(listAssetsTable!=null)
            {
                listAssetsTable.clear();
            }
            List<CbsKycAssetsTO> kycAssetsTos = masterDelegate.getKycAssetsByCustId(getCustomerDetail().getCustId());
            if (!kycAssetsTos.isEmpty()) {
                for (CbsKycAssetsTO kycAssetsTO : kycAssetsTos) {
                    assetsTable = new AssetsTable();
                    assetsTable.setAstType(kycAssetsTO.getAssetstype());
                    assetsTable.setAssets(kycAssetsTO.getAssets());
                    assetsTable.setAstValue(kycAssetsTO.getAssetsvalue());
                    assetsTable.setsNo1(String.valueOf(kycAssetsTO.getCbsKycAssetsPKTO().getTxnId()));
                    listAssetsTable.add(assetsTable);
                }
            }
            if(listLoanTable!=null)
            {
                listLoanTable.clear();
            }
            List<CbsKycLoanTO> kycLoanTos = masterDelegate.getKycLoanByCustId(getCustomerDetail().getCustId());
            if (!kycLoanTos.isEmpty()) {
                for (CbsKycLoanTO kycLoanTo : kycLoanTos) {
                    loanTable = new LoanTable();
                    loanTable.setLoanType(kycLoanTo.getLoantype());
                    loanTable.setLoanValue(kycLoanTo.getLoanamt());
                    loanTable.setsNo2(String.valueOf(kycLoanTo.getCbsKycLoanPKTO().getTxnId()));
                    listLoanTable.add(loanTable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }

    }

    public void refreshForm() {
        
        this.setEducationDetails("0");
        this.setSpouse(false);
        this.setParents(false);
        this.setChildren(false);
        this.setChildrenFlag("none");
        this.setNoOfChildren("");
        this.setMale1("");
        this.setMale2("");
        this.setMale3("");
        this.setMale4("");
        this.setMale5("");
        this.setTotalMale("");
        this.setFemale1("");
        this.setFemale2("");
        this.setFemale3("");
        this.setFemale4("");
        this.setFemale5("");
        this.setTotalFemale("");
        this.setName1("");
        this.setName2("");
        this.setName3("");
        this.setAddress1("");
        this.setAddress2("");
        this.setAddress3("");
        this.setAbroadTime("");
        this.setRelativeInBank("0");
        this.setRelationWithDirector("0");
        this.setCreditCardDetails("0");
        this.setMedicalInsurance("0");
         this.setAssetsType("0");
        this.setAssetsOwnership("0");
        this.setAssetValue("0");
        if(listAssetsTable!=null)
        {
            listAssetsTable.clear();
               this.setExistLoans("0");
        this.setLoanAmount(new BigDecimal("0"));
        this.setExistLoanAmtFlag("false");
        this.setBtnAdd2Flag("false");
        if(listLoanTable!=null)
        {
            listLoanTable.clear();
        }
        
        }
        
    }
}
