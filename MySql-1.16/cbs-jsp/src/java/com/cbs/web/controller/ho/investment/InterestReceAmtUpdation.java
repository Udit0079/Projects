/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFrdDatesAndDetailsHistory;
import com.cbs.entity.master.Gltable;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.pojo.InvestmentFDRInterestPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class InterestReceAmtUpdation extends BaseBean {

    private String message;
    private String controlNo;
    private BigDecimal txtReceivedAmount;
    private boolean processVisible;
    private InvestmentFDRInterestPojo currentItem;
    private List<InvestmentFDRInterestPojo> tableList;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.00");
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
    private BigDecimal txtAmount;
    private String drHeadName;
    private String crHeadName;
    private String interestOption;
    private List<SelectItem> interestOptionList;
    private boolean disIntOpt;

    public InvestmentFDRInterestPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InvestmentFDRInterestPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isProcessVisible() {
        return processVisible;
    }

    public void setProcessVisible(boolean processVisible) {
        this.processVisible = processVisible;
    }

    public List<InvestmentFDRInterestPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<InvestmentFDRInterestPojo> tableList) {
        this.tableList = tableList;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }  

    public BigDecimal getTxtReceivedAmount() {
        return txtReceivedAmount;
    }

    public void setTxtReceivedAmount(BigDecimal txtReceivedAmount) {
        this.txtReceivedAmount = txtReceivedAmount;
    }
    
    public String getCrAcno() {
        return crAcno;
    }

    public void setCrAcno(String crAcno) {
        this.crAcno = crAcno;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getDrAcno() {
        return drAcno;
    }

    public void setDrAcno(String drAcno) {
        this.drAcno = drAcno;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }   

    public BigDecimal getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(BigDecimal txtAmount) {
        this.txtAmount = txtAmount;
    }
    
    public String getDrHeadName() {
        return drHeadName;
    }

    public void setDrHeadName(String drHeadName) {
        this.drHeadName = drHeadName;
    }

    public String getCrHeadName() {
        return crHeadName;
    }

    public void setCrHeadName(String crHeadName) {
        this.crHeadName = crHeadName;
    }

    public String getInterestOption() {
        return interestOption;
    }

    public void setInterestOption(String interestOption) {
        this.interestOption = interestOption;
    }

    public List<SelectItem> getInterestOptionList() {
        return interestOptionList;
    }

    public void setInterestOptionList(List<SelectItem> interestOptionList) {
        this.interestOptionList = interestOptionList;
    }

    public boolean isDisIntOpt() {
        return disIntOpt;
    }

    public void setDisIntOpt(boolean disIntOpt) {
        this.disIntOpt = disIntOpt;
    }
    
    /** Creates a new instance of InterestReceAmtUpdation */
    public InterestReceAmtUpdation() {
        try {
            tableList = new ArrayList<InvestmentFDRInterestPojo>();
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            resetForm();
            interestOptionList = new ArrayList<SelectItem>();
            interestOptionList.add(new SelectItem("C","C"));
            interestOptionList.add(new SelectItem("S","S"));
            //getDataToUpdate();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getDataToUpdate() {
        this.setMessage("");
        tableList = new ArrayList<InvestmentFDRInterestPojo>();
        try {
            List<Object[]> dataList = remoteObj.getInvestmentDetailAndDatesForIntRecPostFdr("ACTIVE", dmy.parse(getTodayDate()), interestOption);
            if (!dataList.isEmpty()) {
                this.setMessage("Please select a row to update.");
                for (int i = 0; i < dataList.size(); i++) {
                    InvestmentFDRInterestPojo pojo = new InvestmentFDRInterestPojo();
                    Object[] dataArray = dataList.get(i);

                    pojo.setCtrlNo(Integer.parseInt(dataArray[0].toString()));
                    pojo.setLastIntPaidDt(dataArray[1] == null ? "" : dmy.format(dataArray[1]));
                    pojo.setIntOpt(dataArray[2] == null ? "" : dataArray[2].toString());
                    pojo.setTotAmtIntRec(new BigDecimal(formatter.format(dataArray[3])));
                    pojo.setPurchaseDt(dmy.format(dataArray[4]));
                    pojo.setMatDt(dmy.format(dataArray[5]));
                    pojo.setFaceValue(new BigDecimal(formatter.format(dataArray[6])));
                    pojo.setRoi(Double.parseDouble(dataArray[7].toString()));
                    pojo.setSellerName(dataArray[8].toString());
                    pojo.setSecDesc(dataArray[9].toString());
                    pojo.setBookValue(new BigDecimal(formatter.format(dataArray[10])));
                    pojo.setFdrNo(dataArray[11] == null ? "" : dataArray[11].toString());
                    pojo.setCrHead("");
                    pojo.setDrHead("");
                    pojo.setEnterBy(dataArray[12].toString());
                    pojo.setAuthBy(dataArray[13] == null ? "" : dataArray[13].toString());
                    pojo.setTrantime((java.util.Date) dataArray[14]);
                    pojo.setAmtIntRec(new BigDecimal(formatter.format(dataArray[15])));

                    tableList.add(pojo);
                }
                disIntOpt = true;
            } else {
                this.setMessage("There is no data to update.");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void amountProcess() {
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (this.txtReceivedAmount == null || this.txtReceivedAmount.toString().equalsIgnoreCase("")) {
                this.setMessage("Please fill amount field.");
                return;
            }

            Matcher amountTxnCheck = p.matcher(this.txtReceivedAmount.toString());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid amount.");
                return;
            }

            if (this.txtReceivedAmount.toString().contains(".")) {
                if (this.txtReceivedAmount.toString().indexOf(".") != this.txtReceivedAmount.toString().lastIndexOf(".")) {
                    this.setMessage("Invalid amount.Please fill the amount correctly.");
                    return;
                } else if (this.txtReceivedAmount.toString().substring(txtReceivedAmount.toString().indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please fill the amount upto two decimal places.");
                    return;
                }
            }
            InvestmentCallHead entity = remoteObj.getInvestCallHeadByCode("6");
            //String AcnoFdr = remoteObj.getGLByCtrlNo(Integer.parseInt(this.getControlNo()));
            
            if (this.txtReceivedAmount.compareTo(this.txtAmount)==1) {
                BigDecimal totAmt = this.txtReceivedAmount.subtract(this.txtAmount);
                if (entity != null) {
                    Gltable entityG;
                    this.setDrAcno(entity.getIntGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getIntGlhead());
                    if (entityG != null) {
                        this.setDrHeadName(entityG.getAcName());
                    }
                    this.setDrAmt(formatter.format(totAmt));
                    this.setCrAcno(entity.getDrGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getDrGlhead());
                    if (entityG != null) {
                        this.setCrHeadName(entityG.getAcName());
                    }
                    this.setCrAmt(formatter.format(totAmt));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
                }
            } else {
                BigDecimal totAmt = this.txtAmount.subtract(this.txtReceivedAmount);
                if (entity != null) {
                    Gltable entityG;
                    this.setDrAcno(entity.getDrGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getDrGlhead());
                    if (entityG != null) {
                        this.setDrHeadName(entityG.getAcName());
                    }
                    this.setDrAmt(formatter.format(totAmt));
                    this.setCrAcno(entity.getIntGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getIntGlhead());
                    if (entityG != null) {
                        this.setCrHeadName(entityG.getAcName());
                    }
                    this.setCrAmt(formatter.format(totAmt));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
                }
            }
            this.setProcessVisible(false);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem != null) {                
                this.setControlNo(currentItem.getCtrlNo().toString());
                this.setTxtReceivedAmount(new BigDecimal (currentItem.getAmtIntRec().toString()));
                this.setTxtAmount(new BigDecimal (currentItem.getAmtIntRec().toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        amountProcess();
        Pattern p = Pattern.compile("((|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.txtReceivedAmount == null || this.txtReceivedAmount.toString().equalsIgnoreCase("")) {
            this.setMessage("Please fill amount field.");
            return;
        }

        Matcher amountTxnCheck = p.matcher(this.txtReceivedAmount.toString());
        if (!amountTxnCheck.matches()) {
            this.setMessage("Invalid amount.");
            return;
        }

        if (this.txtReceivedAmount.toString().contains(".")) {
            if (this.txtReceivedAmount.toString().indexOf(".") != this.txtReceivedAmount.toString().lastIndexOf(".")) {
                this.setMessage("Invalid amount.Please fill the amount correctly.");
                return;
            } else if (this.txtReceivedAmount.toString().substring(txtReceivedAmount.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please fill the amount upto two decimal places.");
                return;
            }
        }
        try {
            if (currentItem != null) {
                if (currentItem.getCtrlNo() != Integer.parseInt(this.controlNo)) {
                    this.setMessage("Only selected row can be updated by you.");
                    return;
                } else {
                    InvestmentFrdDatesAndDetailsHistory historyObj = new InvestmentFrdDatesAndDetailsHistory();
                    historyObj.setCtrlNo(currentItem.getCtrlNo().longValue());
                    historyObj.setLastIntPaidDt(dmy.parse(currentItem.getLastIntPaidDt()));
                    historyObj.setIntOpt(currentItem.getIntOpt());

                    historyObj.setTotAmtIntRec(currentItem.getTotAmtIntRec());
                    historyObj.setPurchaseDt(dmy.parse(currentItem.getPurchaseDt()));
                    historyObj.setMatDt(dmy.parse(currentItem.getMatDt()));
                    historyObj.setFaceValue(currentItem.getFaceValue());

                    historyObj.setRoi(currentItem.getRoi());
                    historyObj.setSellerName(currentItem.getSellerName());
                    historyObj.setSecDesc(currentItem.getSecDesc());
                    historyObj.setBookValue(currentItem.getBookValue());

                    historyObj.setFdrNo(currentItem.getFdrNo());
                    historyObj.setEnterBy(currentItem.getEnterBy());
                    historyObj.setAuthBy(currentItem.getAuthBy());
                    historyObj.setCreatedTranTime(currentItem.getTrantime());
                    historyObj.setUpdateBy(getUserName());
                    historyObj.setUpdateTranTime(new Date());                    
                    
                    
                    InvestmentFdrDates obj = remoteObj.getInvestmentFdrDatesByCtrlNo(currentItem.getCtrlNo());
                    BigDecimal newAmt = new BigDecimal("0");
                    if (this.txtReceivedAmount.compareTo(this.txtAmount)==1) {
                        newAmt = this.txtReceivedAmount.subtract(this.txtAmount);
                        obj.setAmtIntTrec(this.txtReceivedAmount.doubleValue());
                        obj.setTotAmtIntRec(obj.getTotAmtIntRec() + newAmt.doubleValue());
                    } else {
                        newAmt = this.txtAmount.subtract(this.txtReceivedAmount);
                        obj.setAmtIntTrec(this.txtReceivedAmount.doubleValue());
                        obj.setTotAmtIntRec(obj.getTotAmtIntRec() - newAmt.doubleValue());
                    }

                    String result = remoteObj.updateInvestmentInterestAmount(historyObj, obj,this.getDrAcno(),this.getDrAmt(), this.getCrAcno(), this.getCrAmt(),this.getUserName(),this.getOrgnBrCode(),this.txtReceivedAmount,this.txtAmount);
                    if (result.equalsIgnoreCase("true")) {
                        resetForm();
                        tableList = new ArrayList<InvestmentFDRInterestPojo>();
                        getDataToUpdate();
                        this.setMessage("Data has been updated successfully !");
                    } else {
                        this.setMessage(result);
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        if (!tableList.isEmpty()) {
            this.setMessage("Please select a row to update.");
        } else {
            this.setMessage("There is no data to update");
        }
        this.setControlNo("");
        this.setTxtReceivedAmount(new BigDecimal("0"));
        this.setTxtAmount(new BigDecimal("0"));
        this.setProcessVisible(true);
        this.setDrAcno("");
        this.setCrAcno("");
        this.setCrAmt("");
        this.setDrAmt("");
        this.setDrHeadName("");
        this.setCrHeadName("");
        disIntOpt = false;
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}