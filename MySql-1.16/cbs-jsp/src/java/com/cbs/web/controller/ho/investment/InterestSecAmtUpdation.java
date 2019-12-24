/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentFrdDatesAndDetailsHistory;
import com.cbs.entity.ho.investment.InvestmentGoidates;
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

/**
 *
 * @author sipl
 */
public class InterestSecAmtUpdation extends BaseBean {
    private String message;
    private String controlNo;
    private BigDecimal txtReceivedAmount;
    private boolean processVisible;
    private boolean txtVisible;
    private InvestmentFDRInterestPojo currentItem;
    private List<InvestmentFDRInterestPojo> tableList;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.##");
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
    private BigDecimal txtAmount;
    private String flag;
    private String drHeadName;
    private String crHeadName;

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
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

    public InvestmentFDRInterestPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InvestmentFDRInterestPojo currentItem) {
        this.currentItem = currentItem;
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

    public BigDecimal getTxtReceivedAmount() {
        return txtReceivedAmount;
    }

    public void setTxtReceivedAmount(BigDecimal txtReceivedAmount) {
        this.txtReceivedAmount = txtReceivedAmount;
    }

    public BigDecimal getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(BigDecimal txtAmount) {
        this.txtAmount = txtAmount;
    }
    
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public boolean isTxtVisible() {
        return txtVisible;
    }

    public void setTxtVisible(boolean txtVisible) {
        this.txtVisible = txtVisible;
    }
    
    
    /** Creates a new instance of InterestReceAmtUpdation */
    public InterestSecAmtUpdation() {
        try {
            tableList = new ArrayList<InvestmentFDRInterestPojo>();
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            resetForm();
            getDataToUpdate();
            this.setTxtVisible(true);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getDataToUpdate() {
        this.setMessage("");
        try {
            List<Object[]> dataList = remoteObj.getInvestmentDetailAndDatesForIntRecPostSec("ACTIVE", dmy.parse(getTodayDate()));
            if (!dataList.isEmpty()) {
                this.setMessage("Please select a row to update.");
                for (int i = 0; i < dataList.size(); i++) {
                    InvestmentFDRInterestPojo pojo = new InvestmentFDRInterestPojo();
                    Object[] dataArray = dataList.get(i);

                    pojo.setCtrlNo(Integer.parseInt(dataArray[0].toString()));
                    pojo.setLastIntPaidDt(dataArray[1] == null ? "" : dmy.format(dataArray[1]));
                    pojo.setTotAmtIntRec(new BigDecimal(formatter.format(dataArray[2])));
                    pojo.setPurchaseDt(dmy.format(dataArray[3]));
                    pojo.setMatDt(dmy.format(dataArray[4]));
                    pojo.setFaceValue(new BigDecimal(formatter.format(dataArray[5])));
                    pojo.setRoi(Double.parseDouble(dataArray[6].toString()));
                    pojo.setSellerName(dataArray[7].toString());
                    pojo.setSecDesc(dataArray[8].toString());
                    pojo.setBookValue(new BigDecimal(formatter.format(dataArray[9])));
                    pojo.setEnterBy(dataArray[10] == null ? "" : dataArray[10].toString());
                    pojo.setAuthBy(dataArray[11] == null ? "" : dataArray[11].toString());
                    pojo.setTrantime(new Date());
                    
                    tableList.add(pojo);
                }
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
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

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
            InvestmentCallHead entity = remoteObj.getInvestCallHeadByCode("14");

            if (this.txtReceivedAmount.compareTo(this.txtAmount)==1) {
                BigDecimal totAmt = this.txtReceivedAmount.subtract(this.txtAmount);
                if (entity != null) {
                    Gltable entityG;
                    this.setDrAcno(entity.getCrGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getCrGlhead());
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
                    this.setMessage("Data is not found in Investment Call Head Table for Code 14.");
                }
                this.setFlag("D");
            } else {
                Gltable entityG;
                BigDecimal totAmt = this.txtAmount.subtract(this.txtReceivedAmount);
                if (entity != null) {
                    this.setDrAcno(entity.getIntGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getIntGlhead());
                    if (entityG != null) {
                        this.setDrHeadName(entityG.getAcName());
                    }
                    this.setDrAmt(formatter.format(totAmt));
                    this.setCrAcno(entity.getCrGlhead());
                    entityG = remoteObj.getGltableByAcno(entity.getCrGlhead());
                    if (entityG != null) {
                        this.setCrHeadName(entityG.getAcName());
                    }
                    this.setCrAmt(formatter.format(totAmt));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 14.");
                }
                this.setFlag("C");
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem != null) {
                this.setProcessVisible(false);
                this.setTxtVisible(false);
                this.setControlNo(currentItem.getCtrlNo().toString());
                this.setTxtReceivedAmount(new BigDecimal (currentItem.getTotAmtIntRec().toString()));
                this.setTxtAmount(new BigDecimal (currentItem.getTotAmtIntRec().toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        amountProcess();
        try {
            if (currentItem != null) {
                if (currentItem.getCtrlNo() != Integer.parseInt(this.controlNo)) {
                    this.setMessage("Only selected row can be updated by you.");
                    return;
                } else {
                    InvestmentFrdDatesAndDetailsHistory historyObj = new InvestmentFrdDatesAndDetailsHistory();
                    historyObj.setCtrlNo(currentItem.getCtrlNo().longValue());
                    historyObj.setLastIntPaidDt(dmy.parse(currentItem.getLastIntPaidDt()));
                    
                    historyObj.setTotAmtIntRec(currentItem.getTotAmtIntRec());
                    historyObj.setPurchaseDt(dmy.parse(currentItem.getPurchaseDt()));
                    historyObj.setMatDt(dmy.parse(currentItem.getMatDt()));
                    historyObj.setFaceValue(currentItem.getFaceValue());

                    historyObj.setRoi(currentItem.getRoi());
                    historyObj.setSellerName(currentItem.getSellerName());
                    historyObj.setSecDesc(currentItem.getSecDesc());
                    historyObj.setBookValue(currentItem.getBookValue());

                    historyObj.setEnterBy(currentItem.getEnterBy());
                    historyObj.setAuthBy(currentItem.getAuthBy());
                    historyObj.setCreatedTranTime(currentItem.getTrantime());
                    historyObj.setUpdateBy(getUserName());
                    historyObj.setUpdateTranTime(new Date());

                    InvestmentGoidates obj = remoteObj.getInvestmentGoidates(currentItem.getCtrlNo());
                    BigDecimal newAmt = new BigDecimal("0");
                    if (this.txtReceivedAmount.compareTo(this.txtAmount)==1) {
                        newAmt = this.txtReceivedAmount.subtract(this.txtAmount);
                        obj.setAmtIntAccr(this.txtReceivedAmount.doubleValue());
                        obj.setTotAmtIntAccr(obj.getTotAmtIntAccr() + newAmt.doubleValue());
                    } else {
                        newAmt = this.txtAmount.subtract(this.txtReceivedAmount);
                        obj.setAmtIntAccr(this.txtReceivedAmount.doubleValue());
                        obj.setTotAmtIntAccr(obj.getTotAmtIntAccr() - newAmt.doubleValue());
                    }
                    String result = remoteObj.updateInvestmentSecInterestAmount(historyObj, obj,this.getDrAcno(),this.getDrAmt(), this.getCrAcno(), this.getCrAmt(),this.getUserName(),this.getOrgnBrCode(),this.getFlag());
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
        this.setDrAcno("");
        this.setDrHeadName("");
        this.setCrAcno("");
        this.setCrHeadName("");
        this.setTxtAmount(new BigDecimal("0"));
        this.setDrAmt("");
        this.setCrAmt("");
        this.setProcessVisible(true);
        this.setTxtVisible(true);
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