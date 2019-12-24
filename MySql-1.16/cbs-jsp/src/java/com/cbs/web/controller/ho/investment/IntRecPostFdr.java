/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.InvestmentFDRInterestPojo;
import com.cbs.pojo.SortByControlNo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.IntRecPostFdrPojo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class IntRecPostFdr extends BaseBean {

    private String message;
    private boolean disTillDt;
    private String tilDate;
    private String controlNo;
    private BigDecimal txtReceivedAmount;
    private boolean disRecAmt;
    private BigDecimal txtAmount;
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
    private List<InvestmentFDRInterestPojo> tableList;
    private InvestmentFDRInterestPojo currentItem;
    private boolean disUpdate;
    private String btnHide;
    private boolean calcFlag;
    private boolean disPost;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private TdReceiptManagementFacadeRemote obj = null;
    private final String jndiName = "TdReceiptManagementFacade";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.00");
    private String viewID = "/pages/master/sm/test.jsp";
    private String drHeadDesc;
    private String crHeadDesc;
    private String interestOption;
    private List<SelectItem> interestOptionList;
    private boolean disIntOpt;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisTillDt() {
        return disTillDt;
    }

    public void setDisTillDt(boolean disTillDt) {
        this.disTillDt = disTillDt;
    }

    public String getTilDate() {
        return tilDate;
    }

    public void setTilDate(String tilDate) {
        this.tilDate = tilDate;
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

    public boolean isDisRecAmt() {
        return disRecAmt;
    }

    public void setDisRecAmt(boolean disRecAmt) {
        this.disRecAmt = disRecAmt;
    }

    public BigDecimal getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(BigDecimal txtAmount) {
        this.txtAmount = txtAmount;
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

    public List<InvestmentFDRInterestPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<InvestmentFDRInterestPojo> tableList) {
        this.tableList = tableList;
    }

    public InvestmentFDRInterestPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InvestmentFDRInterestPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isDisUpdate() {
        return disUpdate;
    }

    public void setDisUpdate(boolean disUpdate) {
        this.disUpdate = disUpdate;
    }

    public String getBtnHide() {
        return btnHide;
    }

    public void setBtnHide(String btnHide) {
        this.btnHide = btnHide;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public boolean isDisPost() {
        return disPost;
    }

    public void setDisPost(boolean disPost) {
        this.disPost = disPost;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getDrHeadDesc() {
        return drHeadDesc;
    }

    public void setDrHeadDesc(String drHeadDesc) {
        this.drHeadDesc = drHeadDesc;
    }

    public String getCrHeadDesc() {
        return crHeadDesc;
    }

    public void setCrHeadDesc(String crHeadDesc) {
        this.crHeadDesc = crHeadDesc;
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

    public IntRecPostFdr() {
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            tableList = new ArrayList<InvestmentFDRInterestPojo>();
            List<IntRecPostFdrPojo> reportList = new ArrayList<IntRecPostFdrPojo>();
            this.setTilDate(getTodayDate());
            disUpdate = true;
            disPost = true;
            disRecAmt = true;
            interestOptionList = new ArrayList<SelectItem>();
            interestOptionList.add(new SelectItem("C", "C"));
            interestOptionList.add(new SelectItem("S", "S"));
            interestOptionList.add(new SelectItem("Q", "Q"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onBlurTillDate() {
        this.setMessage("");
        if (this.tilDate == null || this.tilDate.equals("") || this.tilDate.length() < 10) {
            this.setMessage("Please fill a proper till date !");
            return;
        }

        boolean var = new Validator().validateDate_dd_mm_yyyy(this.tilDate);
        if (var == false) {
            this.setMessage("Please fill a proper till date !");
            return;
        }
        getInterestData();
    }

    public void getInterestData() {
        calcFlag = true;
        this.setMessage("");
        tableList = new ArrayList<InvestmentFDRInterestPojo>();
        try {
            List<Object[]> dataList = remoteObj.getInvestmentDetailAndDatesForIntRecPostFdr("ACTIVE", dmy.parse(this.tilDate), interestOption);
            if (dataList.isEmpty()) {
                resetForm();
                this.setMessage("Data does not exist !");
                calcFlag = false;
            } else {
                Double totAmt = 0.0, totfaceAmt = 0.0, dblInterest = 0.0;
                String fromDt = "", purchaseDt = "", gPeriod = "", intPDate = "";
                long dayDiff = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    Object[] object = dataList.get(i);
                    if (((java.util.Date) object[1]).compareTo((java.util.Date) object[5]) < 0) {
                        
                        dayDiff = CbsUtil.dayDiff((java.util.Date) object[1], dmy.parse(this.tilDate));
                        fromDt = ymd.format((java.util.Date) object[1]);
                        purchaseDt = ymd.format((java.util.Date) object[4]);
                        if(object[17] != null){
                            intPDate = ymd.format((java.util.Date) object[17]);
                            if (ymd.parse(intPDate).compareTo(ymd.parse(fromDt)) > 0) {
                                fromDt = intPDate;
                            }
                        }                        

                        if (ymd.parse(fromDt).compareTo(ymd.parse(purchaseDt)) <= 0) {
                            fromDt = CbsUtil.dateAdd(purchaseDt, -1);
                            dayDiff = CbsUtil.dayDiff(ymd.parse(purchaseDt), dmy.parse(this.tilDate)) + 1;
                        }

                        if (object[2].toString().equals("C")) {
                            if (dayDiff > 0) {
                                totfaceAmt = (Double) object[6] + (Double) object[3] + (Double) object[16];
                                gPeriod = dayDiff + "Days";
                                dblInterest = Double.parseDouble(obj.orgFDInterest(object[2].toString(), Float.parseFloat(object[7].toString()), fromDt, ymd.format(dmy.parse(this.tilDate)), totfaceAmt, gPeriod, this.getOrgnBrCode()));
                            }
                        } else {
                            if (dayDiff > 0) {
                                gPeriod = dayDiff + "Days";
                                dblInterest = Double.parseDouble(obj.orgFDInterest(object[2].toString(), Float.parseFloat(object[7].toString()), fromDt, ymd.format(dmy.parse(this.tilDate)), (Double) object[6], gPeriod, this.getOrgnBrCode()));
                            }
                        }
                        if ((Double.compare((Double) object[10], (Double) object[6]) == 0)) {
                            dblInterest = dblInterest;
                        } else {
                            if ((dblInterest + (Double) object[3]) > ((Double) object[10] - (Double) object[6])) {
                                dblInterest = (Double) object[10] - (Double) object[6] - (Double) object[3];
                            }
                        }


                        /**
                         * Creation of report list
                         */
                        InvestmentFDRInterestPojo pojo = new InvestmentFDRInterestPojo();
                        pojo.setLastIntPaidDt(dmy.format(object[1]));
                        pojo.setCtrlNo(Integer.parseInt(object[0].toString()));
                        pojo.setIntOpt(object[2] == null ? "" : object[2].toString());
                        pojo.setPurchaseDt(dmy.format(object[4]));
                        pojo.setMatDt(dmy.format(object[5]));
                        pojo.setFaceValue(new BigDecimal(formatter.format(object[6])));
                        pojo.setRoi(Double.parseDouble(object[7].toString()));
                        pojo.setSellerName(object[8].toString());
                        pojo.setSecDesc(object[9].toString());
                        pojo.setBookValue(new BigDecimal(formatter.format(object[10])));
                        pojo.setFdrNo(object[11] == null ? "" : object[11].toString());
                        pojo.setCrHead("");
                        pojo.setDrHead("");
                        pojo.setEnterBy(object[12].toString());
                        pojo.setAuthBy(object[13] == null ? "" : object[13].toString());
                        pojo.setTrantime((java.util.Date) object[14]);
                        pojo.setAmtIntRec(new BigDecimal(formatter.format(dblInterest)));

                        tableList.add(pojo);
                        totAmt = totAmt + dblInterest;
                    }
                }

                InvestmentCallHead entity = remoteObj.getInvestCallHeadByCode("6");
                if (entity != null) {
                    this.setDrAcno(entity.getIntGlhead());
                    this.setDrHeadDesc(acDescVal(this.getDrAcno()));
                    this.setDrAmt(formatter.format(totAmt));
                    this.setCrAcno(entity.getDrGlhead());
                    this.setCrHeadDesc(acDescVal(this.getCrAcno()));
                    this.setCrAmt(formatter.format(totAmt));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
                }
                disTillDt = true;
                disIntOpt = true;
            }
        } catch (ApplicationException ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem != null) {
                this.setControlNo(currentItem.getCtrlNo().toString());
                this.setTxtReceivedAmount(new BigDecimal(formatter.format(currentItem.getAmtIntRec())));
                this.setTxtAmount(new BigDecimal(currentItem.getAmtIntRec().toString()));
                disRecAmt = false;
                disUpdate = false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void updateAction() {
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
            InvestmentCallHead entity = remoteObj.getInvestCallHeadByCode("6");
            //String AcnoFdr = remoteObj.getGLByCtrlNo(Integer.parseInt(this.getControlNo()));

            if (this.txtReceivedAmount.compareTo(this.txtAmount) == -1) {
                BigDecimal totAmt = this.txtReceivedAmount.subtract(this.txtAmount);
                if (entity != null) {
                    this.setDrAcno(entity.getIntGlhead());
                    this.setDrAmt(formatter.format(new BigDecimal(this.getDrAmt()).add(totAmt)));
                    this.setCrAcno(entity.getDrGlhead());
                    this.setCrAmt(formatter.format(new BigDecimal(this.crAmt).add(totAmt)));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
                }
            } else {
                BigDecimal totAmt = this.txtAmount.subtract(this.txtReceivedAmount);
                if (entity != null) {
                    this.setDrAcno(entity.getDrGlhead());
                    this.setDrAmt(formatter.format(new BigDecimal(this.getDrAmt()).subtract(totAmt)));
                    this.setCrAcno(entity.getIntGlhead());
                    this.setCrAmt(formatter.format(new BigDecimal(this.crAmt).subtract(totAmt)));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
                }
            }

            int index = getIndex(tableList, this.getControlNo());

            InvestmentFDRInterestPojo tempObj = tableList.get(index);
            tableList.remove(index);
            tempObj.setAmtIntRec(this.getTxtReceivedAmount());
            tableList.add(tempObj);

            this.setControlNo("");
            this.setTxtReceivedAmount(new BigDecimal("0"));
            this.setTxtAmount(new BigDecimal("0"));
            disUpdate = true;

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortByControlNo());
            Collections.sort(tableList, chObj);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public int getIndex(List<InvestmentFDRInterestPojo> list, String ctrlNo) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCtrlNo() == (Integer.parseInt(ctrlNo))) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void print() {
        calcFlag = true;
        this.setMessage("");
        try {
            if (tableList.isEmpty()) {
                resetForm();
                this.setMessage("Data does not exist !");
                calcFlag = false;
            } else {
                String fromDt = "", purchaseDt = "";
                long dayDiff = 0;
                List<IntRecPostFdrPojo> reportList = new ArrayList<IntRecPostFdrPojo>();
                for (int i = 0; i < tableList.size(); i++) {
                    String AcnoFdr = remoteObj.getGLByCtrlNo(Integer.parseInt(tableList.get(i).getCtrlNo().toString()));
                    dayDiff = CbsUtil.dayDiff((java.util.Date) dmy.parse(tableList.get(i).getLastIntPaidDt()), dmy.parse(this.tilDate));
                    fromDt = ymd.format((java.util.Date) dmy.parse(tableList.get(i).getLastIntPaidDt()));
                    purchaseDt = ymd.format((java.util.Date) dmy.parse(tableList.get(i).getPurchaseDt()));
                    if (ymd.parse(fromDt).compareTo(ymd.parse(purchaseDt)) <= 0) {
                        fromDt = CbsUtil.dateAdd(purchaseDt, -1);
                        dayDiff = CbsUtil.dayDiff(ymd.parse(purchaseDt), dmy.parse(this.tilDate)) + 1;
                    }
                    /**
                     * Creation of report list
                     */
                    IntRecPostFdrPojo reportPojo = new IntRecPostFdrPojo();
                    reportPojo.setSno(i + 1);
                    reportPojo.setCtrlNo(Integer.parseInt(tableList.get(i).getCtrlNo().toString()));
                    reportPojo.setBankAcNo(AcnoFdr);
                    reportPojo.setFdrNo(tableList.get(i).getFdrNo().toString());
                    reportPojo.setFaValue(formatter.format(tableList.get(i).getFaceValue()));
                    reportPojo.setBookvalue(formatter.format(tableList.get(i).getBookValue()));
                    reportPojo.setRoi(Float.parseFloat(tableList.get(i).getRoi().toString()));
                    reportPojo.setSellerName(tableList.get(i).getSellerName().toString());
                    reportPojo.setIntopt(tableList.get(i).getIntOpt().toString());
                    reportPojo.setDays(Integer.parseInt(String.valueOf(dayDiff)));
                    reportPojo.setRecAmt(formatter.format(tableList.get(i).getAmtIntRec()));
                    reportPojo.setPurDt(tableList.get(i).getPurchaseDt());
                    reportPojo.setMatdt(tableList.get(i).getMatDt());

                    reportList.add(reportPojo);
                }

                List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
                if (bankDetailsList.size() > 0) {
                    String reportName = "Interest Received Post FDR";
                    String jrxmlName = "intRecPostFdr";
                    String bankName = bankDetailsList.get(0).toString();
                    String bankAddress = bankDetailsList.get(1).toString();
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", reportName);
                    fillParams.put("pReportDate", this.getTilDate());
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAddress);

                    new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                }
                disPost = false;
                disUpdate = true;
                btnHide = "none";
            }
        } catch (ApplicationException ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        }
    }

    public void postAction() {
        this.setMessage("");
        try {
            String msg = remoteObj.postIntRecFdr(tableList, Double.parseDouble(this.getDrAmt()), dmy.parse(this.tilDate), getUserName(), getOrgnBrCode(), this.interestOption);
            if (msg.equals("true")) {
                this.setMessage("Interest Posted Successfully !");
                resetSave();
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setMessage("");
        this.setTilDate(getTodayDate());
        this.setControlNo("");
        this.setTxtReceivedAmount(new BigDecimal("0"));
        this.setDrAcno("");
        this.setDrAmt("");
        this.setCrAcno("");
        this.setCrAmt("");
        this.setCrHeadDesc("");
        this.setDrHeadDesc("");
        tableList = new ArrayList<InvestmentFDRInterestPojo>();
        List<IntRecPostFdrPojo> reportList = new ArrayList<IntRecPostFdrPojo>();
        disTillDt = false;
        disRecAmt = true;
        btnHide = "";
        disUpdate = true;
        disPost = true;
        disIntOpt = false;
    }

    public void resetSave() {
        this.setTilDate(getTodayDate());
        this.setControlNo("");
        this.setTxtReceivedAmount(new BigDecimal("0"));
        this.setDrAcno("");
        this.setDrAmt("");
        this.setCrAcno("");
        this.setCrAmt("");
        tableList = new ArrayList<InvestmentFDRInterestPojo>();
        List<IntRecPostFdrPojo> reportList = new ArrayList<IntRecPostFdrPojo>();
        disTillDt = false;
        disRecAmt = true;
        btnHide = "";
        disUpdate = true;
        disPost = true;
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

    public String acDescVal(String acno) {
        String acName = "";
        try {
            Gltable entityList1 = remoteObj.getGltableByAcno(acno);
            if (entityList1 != null) {
                acName = entityList1.getAcName().toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return acName;
    }
}