/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.CurrencyInfoTable;
import com.cbs.dto.customer.CBSCustCurrencyInfoTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author ANKIT VERMA
 */
public class CurrencyInfo {

    CustomerDetail customerDetail;
    String currencyCodeForCurrInformation;
    String withoutHoldTaxLevel;
    String withoutHoldTax100;
    String withoutHoldTaxLim;
    String tdsOperativeAccIdEdit;
    String custIntRateCredit;
    String custIntrateDebit;
    private int currentRowCurr;
    Date prefIntTillDate;
    private List<CurrencyInfoTable> listCurrInfoTab;
    List<SelectItem> currencyCodeForCurrInfoOption;
    private CurrencyInfoTable currInfoTab = new CurrencyInfoTable();
    private int selectCountCurr;
    private int count1Curr;
    private int count2Curr;
    boolean flag5;
    CustomerMasterDelegate masterDelegate;
    List<CurrencyInfoTable> listCurrInfoTab2 = new ArrayList<CurrencyInfoTable>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /** Creates a new instance of CurrencyInfo */
    public CurrencyInfo() {
        initializeForm();
    }

    public List<CurrencyInfoTable> getListCurrInfoTab2() {
        return listCurrInfoTab2;
    }

    public void setListCurrInfoTab2(List<CurrencyInfoTable> listCurrInfoTab2) {
        this.listCurrInfoTab2 = listCurrInfoTab2;
    }

    public int getCount1Curr() {
        return count1Curr;
    }

    public void setCount1Curr(int count1Curr) {
        this.count1Curr = count1Curr;
    }

    public int getCount2Curr() {
        return count2Curr;
    }

    public void setCount2Curr(int count2Curr) {
        this.count2Curr = count2Curr;
    }

    public boolean isFlag5() {
        return flag5;
    }

    public void setFlag5(boolean flag5) {
        this.flag5 = flag5;
    }

    public int getSelectCountCurr() {
        return selectCountCurr;
    }

    public void setSelectCountCurr(int selectCountCurr) {
        this.selectCountCurr = selectCountCurr;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public List<CurrencyInfoTable> getListCurrInfoTab() {
        return listCurrInfoTab;
    }

    public void setListCurrInfoTab(List<CurrencyInfoTable> listCurrInfoTab) {
        this.listCurrInfoTab = listCurrInfoTab;
    }

    public CurrencyInfoTable getCurrInfoTab() {
        return currInfoTab;
    }

    public void setCurrInfoTab(CurrencyInfoTable currInfoTab) {
        this.currInfoTab = currInfoTab;
    }

    public String getCurrencyCodeForCurrInformation() {
        return currencyCodeForCurrInformation;
    }

    public void setCurrencyCodeForCurrInformation(String currencyCodeForCurrInformation) {
        this.currencyCodeForCurrInformation = currencyCodeForCurrInformation;
    }

   

    public List<SelectItem> getCurrencyCodeForCurrInfoOption() {
        return currencyCodeForCurrInfoOption;
    }

    public void setCurrencyCodeForCurrInfoOption(List<SelectItem> currencyCodeForCurrInfoOption) {
        this.currencyCodeForCurrInfoOption = currencyCodeForCurrInfoOption;
    }

    public int getCurrentRowCurr() {
        return currentRowCurr;
    }

    public void setCurrentRowCurr(int currentRowCurr) {
        this.currentRowCurr = currentRowCurr;
    }

    public String getCustIntRateCredit() {
        return custIntRateCredit;
    }

    public void setCustIntRateCredit(String custIntRateCredit) {
        this.custIntRateCredit = custIntRateCredit;
    }

    public String getCustIntrateDebit() {
        return custIntrateDebit;
    }

    public void setCustIntrateDebit(String custIntrateDebit) {
        this.custIntrateDebit = custIntrateDebit;
    }

    public Date getPrefIntTillDate() {
        return prefIntTillDate;
    }

    public void setPrefIntTillDate(Date prefIntTillDate) {
        this.prefIntTillDate = prefIntTillDate;
    }

    public String getTdsOperativeAccIdEdit() {
        return tdsOperativeAccIdEdit;
    }

    public void setTdsOperativeAccIdEdit(String tdsOperativeAccIdEdit) {
        this.tdsOperativeAccIdEdit = tdsOperativeAccIdEdit;
    }

    public String getWithoutHoldTax100() {
        return withoutHoldTax100;
    }

    public void setWithoutHoldTax100(String withoutHoldTax100) {
        this.withoutHoldTax100 = withoutHoldTax100;
    }

    public String getWithoutHoldTaxLevel() {
        return withoutHoldTaxLevel;
    }

    public void setWithoutHoldTaxLevel(String withoutHoldTaxLevel) {
        this.withoutHoldTaxLevel = withoutHoldTaxLevel;
    }

    public String getWithoutHoldTaxLim() {
        return withoutHoldTaxLim;
    }

    public void setWithoutHoldTaxLim(String withoutHoldTaxLim) {
        this.withoutHoldTaxLim = withoutHoldTaxLim;
    }

    public void fetchCurrentRow(ActionEvent event) {

        String currCode = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currCode"));
        currentRowCurr = currentRowCurr + 1;
        currentRowCurr = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (CurrencyInfoTable item : listCurrInfoTab) {
            if (item.getCurrencyCode().equalsIgnoreCase(currCode)) {
                currInfoTab = item;
                break;
            }
        }
    }

    public void selectCurrency() {
        try {
            getCustomerDetail().setErrorMsg("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            selectCountCurr = 1;
            if (currencyCodeForCurrInformation!=null&&!(this.currencyCodeForCurrInformation.equalsIgnoreCase("0") && currInfoTab.getCurrencyCode() != null)) {
                if (!this.currencyCodeForCurrInformation.equalsIgnoreCase("")) {
                    if (this.currencyCodeForCurrInformation.equalsIgnoreCase(currInfoTab.getCurrencyCode())) {
                        count2Curr = count1Curr;
                        count1Curr = count1Curr + 1;
                        if (count2Curr != count1Curr) {
                            loadCurrencyGrid();
                        }
                    } else {
                        count1Curr = 0;
                    }
                }
            }
            this.setCurrencyCodeForCurrInformation(currInfoTab.getCurrencyCode());
            this.setWithoutHoldTax100(currInfoTab.getWithHoldTaxPer());
            this.setWithoutHoldTaxLevel(currInfoTab.getWithHoldingTaxLevel());
            this.setWithoutHoldTaxLim(currInfoTab.getWithHoldTaxLim());
            this.setTdsOperativeAccIdEdit(currInfoTab.getTdsOperativeAccIdEdit());
            this.setCustIntRateCredit(currInfoTab.getCustIntRateCredit());
            this.setCustIntrateDebit(currInfoTab.getCustIntRateDebit());
            this.setPrefIntTillDate(sdf.parse(currInfoTab.getPreferInterestTillDate()));
            listCurrInfoTab.remove(currentRowCurr);
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void loadCurrencyGrid() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custIntRateDebitCheck = p.matcher(this.custIntrateDebit);
            if (this.currencyCodeForCurrInformation.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Currency Code.");
                return;
            }
            if (!custIntRateDebitCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Customer Interest Rate Debit.");
                return;
            } else if (this.custIntrateDebit.contains(".")) {
                if (this.custIntrateDebit.substring(this.custIntrateDebit.indexOf(".") + 1).length() > 2) {
                    getCustomerDetail().setErrorMsg("Please Fill The Customer Interest Rate Debit Upto Two Decimal Places.");
                    getCustomerDetail().setFlagForCreditRate(1);
                    return;
                } else {
                    getCustomerDetail().setErrorMsg("");
                }
            } else {
                getCustomerDetail().setErrorMsg("");
            }
            List<CurrencyInfoTable> listCurr = listCurrInfoTab;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String preferDate;
            if (getCustomerDetail().getFunction().equalsIgnoreCase("a") || getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
                if (selectCountCurr == 0) {
                    for (int i = 0; i < listCurr.size(); i++) {
                        String currCode = listCurr.get(i).getCurrencyCode();
                        if (this.currencyCodeForCurrInformation.equalsIgnoreCase(currCode)) {
                            getCustomerDetail().setErrorMsg("Entry Already Exists In The Table");
                            return;
                        }
                    }
                }
            }
            getCustomerDetail().setErrorMsg("");
            CurrencyInfoTable currTab = new CurrencyInfoTable();
            currTab.setCurrencyCode(this.currencyCodeForCurrInformation);
            currTab.setCustIntRateCredit(this.custIntRateCredit);
            currTab.setCustIntRateDebit(this.custIntrateDebit);
            currTab.setTdsOperativeAccIdEdit(this.tdsOperativeAccIdEdit);
            currTab.setWithHoldTaxLim(this.withoutHoldTaxLim);
            currTab.setWithHoldTaxPer(this.withoutHoldTax100);
            currTab.setWithHoldingTaxLevel(this.withoutHoldTaxLevel);
            if (this.prefIntTillDate == null) {
                preferDate = "01/01/1900";
            } else {
                preferDate = sdf.format(prefIntTillDate);
            }
            currTab.setPreferInterestTillDate(preferDate);
            if (getCustomerDetail().getFunction().equalsIgnoreCase("a")) {
                currTab.setSaveUpdateFlag("Save");
                this.setCurrencyCodeForCurrInformation("0");
                this.setCustIntRateCredit("0.0");
                this.setCustIntrateDebit("0.0");
                this.setTdsOperativeAccIdEdit("");
                this.setWithoutHoldTax100("0.0");
                this.setWithoutHoldTaxLevel("");
                this.setWithoutHoldTaxLim("0.0");
                this.setPrefIntTillDate(date);
            }
            listCurrInfoTab.add(currTab);

            this.setFlag5(false);
            if (getCustomerDetail().getFunction().equalsIgnoreCase("m")) {

                if (selectCountCurr != 1) {
                    for (int i = 0; i < listCurr.size(); i++) {
                        String currencyCod = listCurr.get(i).getCurrencyCode();
                        if (!currencyCod.equalsIgnoreCase(this.currencyCodeForCurrInformation)) {
                            currTab.setSaveUpdateFlag("Save");
                            listCurrInfoTab2.add(currTab);
                            this.setCurrencyCodeForCurrInformation("0");
                            this.setCustIntRateCredit("0.0");
                            this.setCustIntrateDebit("0.0");
                            this.setTdsOperativeAccIdEdit("");
                            this.setWithoutHoldTax100("0.0");
                            this.setWithoutHoldTaxLevel("");
                            this.setWithoutHoldTaxLim("0.0");
                            this.setPrefIntTillDate(date);
                            return;
                        }
                    }
                }
                if (currInfoTab.getCurrencyCode() != null) {
                    if (currInfoTab.getCurrencyCode().equalsIgnoreCase(this.currencyCodeForCurrInformation)) {
                        if (!currInfoTab.getCustIntRateCredit().equalsIgnoreCase(this.custIntRateCredit) || !currInfoTab.getCustIntRateDebit().equalsIgnoreCase(this.custIntrateDebit) || !currInfoTab.getTdsOperativeAccIdEdit().equalsIgnoreCase(this.tdsOperativeAccIdEdit) || !currInfoTab.getWithHoldTaxLim().equalsIgnoreCase(this.withoutHoldTaxLim) || !currInfoTab.getWithHoldTaxPer().equalsIgnoreCase(this.withoutHoldTax100) || !currInfoTab.getWithHoldingTaxLevel().equalsIgnoreCase(this.withoutHoldTaxLevel) || !currInfoTab.getPreferInterestTillDate().equalsIgnoreCase(sdf.format(this.prefIntTillDate))) {
                            currTab.setSaveUpdateFlag("Update");
                            listCurrInfoTab2.add(currTab);
                            this.setCurrencyCodeForCurrInformation("0");
                            this.setCustIntRateCredit("0.0");
                            this.setCustIntrateDebit("0.0");
                            this.setTdsOperativeAccIdEdit("");
                            this.setWithoutHoldTax100("0.0");
                            this.setWithoutHoldTaxLevel("");
                            this.setWithoutHoldTaxLim("0.0");
                            this.setPrefIntTillDate(date);
                        }
                        selectCountCurr = 0;
                    }
                } else {
                    currTab.setSaveUpdateFlag("Save");
                    listCurrInfoTab2.add(currTab);
                    this.setCurrencyCodeForCurrInformation("0");
                    this.setCustIntRateCredit("0.0");
                    this.setCustIntrateDebit("0.0");
                    this.setTdsOperativeAccIdEdit("");
                    this.setWithoutHoldTax100("0.0");
                    this.setWithoutHoldTaxLevel("");
                    this.setWithoutHoldTaxLim("0.0");
                    this.setPrefIntTillDate(date);
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }

    }

    public void deleteGridCurr() {
        listCurrInfoTab.remove(currInfoTab);
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            listCurrInfoTab = new ArrayList<CurrencyInfoTable>();
            currencyCodeForCurrInfoOption = new ArrayList<SelectItem>();
            currencyCodeForCurrInfoOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> currencyCodeOptionList = masterDelegate.getFunctionValues("176");
            for (CbsRefRecTypeTO recTypeTO : currencyCodeOptionList) {
                currencyCodeForCurrInfoOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String onblurWithholdingTaxPer() {
        if (withoutHoldTax100 != null) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tfiNameCheck = p.matcher(this.withoutHoldTax100);
            if (this.withoutHoldTax100.contains("%")) {
                getCustomerDetail().setErrorMsg("% Mark is not excepted.");
                return "% Mark is not excepted.";
            } else if (!tfiNameCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Withholding Tax%.");
                return "Please Enter Numeric Value in Withholding Tax%.";
            } else if (this.withoutHoldTax100.contains(".")) {
                if (this.withoutHoldTax100.substring(this.withoutHoldTax100.indexOf(".") + 1).length() > 2) {
                    getCustomerDetail().setErrorMsg("Please Fill The Withholding Tax% Upto Two Decimal Places.");
                    return "Please Fill The Withholding Tax% Upto Two Decimal Places.";
                } else {
                    getCustomerDetail().setErrorMsg("");
                    return "true";
                }
            } else {
                getCustomerDetail().setErrorMsg("");
                return "true";
            }
        }
        return "true";
    }

    public String onblurWithholdTaxLimit() {
        if (withoutHoldTaxLim != null) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher withoutHoldTaxLimCheck = p.matcher(this.withoutHoldTaxLim);
            if (!withoutHoldTaxLimCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Withhold Tax Limit.");
                return "Please Enter Numeric Value in Withhold Tax Limit.";
            } else if (this.withoutHoldTaxLim.contains(".")) {
                if (this.withoutHoldTaxLim.substring(this.withoutHoldTaxLim.indexOf(".") + 1).length() > 2) {
                    getCustomerDetail().setErrorMsg("Please Fill The Withhold Tax Limit Upto Two Decimal Places.");
                    return "Please Fill The Withhold Tax Limit Upto Two Decimal Places.";
                } else {
                    getCustomerDetail().setErrorMsg("");
                    return "true";
                }
            } else {
                getCustomerDetail().setErrorMsg("");
                return "true";
            }
        }
        return "true";
    }

    public void selectFieldValues() {
        try {
            if(listCurrInfoTab!=null)
            {
                listCurrInfoTab.clear();
            }
            List<CBSCustCurrencyInfoTO> currencyTOs = masterDelegate.getCurrencyInfoByCustId(getCustomerDetail().getCustId());
            if (!currencyTOs.isEmpty()) {
                for (CBSCustCurrencyInfoTO currencyTO : currencyTOs) {
                    currInfoTab = new CurrencyInfoTable();
                    currInfoTab.setCurrencyCode(currencyTO.getcBSCustCurrencyInfoPKTO().getCurrencyCodeType());
                    currInfoTab.setCustIntRateCredit(currencyTO.getCustinterestRateCredit().toString());
                    currInfoTab.setCustIntRateDebit(currencyTO.getCustInterestRateDebit().toString());
                    if (currencyTO.getPreferentialInterestTillDate() == null) {
                        currInfoTab.setPreferInterestTillDate(getCustomerDetail().getTodayDate());
                    } else {
                        currInfoTab.setPreferInterestTillDate(sdf.format(currencyTO.getPreferentialInterestTillDate()));
                    }

                    currInfoTab.setTdsOperativeAccIdEdit(currencyTO.gettDSOperativeAccountID());
                    currInfoTab.setWithHoldTaxLim(currencyTO.getWithHoldingTaxLimit().toString());
                    currInfoTab.setWithHoldTaxPer(currencyTO.getWithHoldingTax().toString());
                    currInfoTab.setWithHoldingTaxLevel(currencyTO.getWithHoldingTaxLevel());
                    listCurrInfoTab.add(currInfoTab);
                }

            }

         } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }

    }

    public void refreshForm() {
        try {
            this.setWithoutHoldTax100("0.0");
            this.setCurrencyCodeForCurrInformation("0");
            this.setWithoutHoldTaxLevel("");
            this.setWithoutHoldTaxLim("0.0");
            this.setPrefIntTillDate(sdf.parse(getCustomerDetail().getTodayDate()));
            this.setTdsOperativeAccIdEdit("");
            this.setCustIntRateCredit("0.0");
            this.setCustIntrateDebit("0.0");
            if (listCurrInfoTab != null) {
                listCurrInfoTab.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String validation() {
        String s44 = onblurWithholdingTaxPer();
        if (!s44.equalsIgnoreCase("true")) {
            return s44;
        }

        String s45 = onblurWithholdTaxLimit();
        if (!s45.equalsIgnoreCase("true")) {
            return s45;
        }
        return "ok";
    }
}
