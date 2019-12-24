/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.DueDateDiaryPojo;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DueDateDiary extends BaseBean {

    private String message;
    private String selectStatus;
    private String frDt;
    private String toDt;
    private String lblFrDate = "none";
    private String txtFrDate = "none";
    private String lblToDate = "none";
    private String txtToDate = "none";
    private String frDtVal;
    private List<SelectItem> selectStatusList;
    private List<DueDateDiaryPojo> reportList;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdHms = new SimpleDateFormat("yyyyMMdd hh:MM:ss");
    
    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getLblFrDate() {
        return lblFrDate;
    }

    public void setLblFrDate(String lblFrDate) {
        this.lblFrDate = lblFrDate;
    }

    public String getLblToDate() {
        return lblToDate;
    }

    public void setLblToDate(String lblToDate) {
        this.lblToDate = lblToDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(String selectStatus) {
        this.selectStatus = selectStatus;
    }

    public List<SelectItem> getSelectStatusList() {
        return selectStatusList;
    }

    public void setSelectStatusList(List<SelectItem> selectStatusList) {
        this.selectStatusList = selectStatusList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getTxtFrDate() {
        return txtFrDate;
    }

    public void setTxtFrDate(String txtFrDate) {
        this.txtFrDate = txtFrDate;
    }

    public String getTxtToDate() {
        return txtToDate;
    }

    public void setTxtToDate(String txtToDate) {
        this.txtToDate = txtToDate;
    }

    public String getFrDtVal() {
        return frDtVal;
    }

    public void setFrDtVal(String frDtVal) {
        this.frDtVal = frDtVal;
    }

    public DueDateDiary() {
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            setStatusList();
            resetAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setStatusList() {
        selectStatusList = new ArrayList<SelectItem>();
        selectStatusList.add(new SelectItem("0", "ACTIVE"));
        selectStatusList.add(new SelectItem("1", "CLOSED"));
        selectStatusList.add(new SelectItem("2", "RENEWED"));
        selectStatusList.add(new SelectItem("3", "OBTAINED"));
    }

    public void onBlurStatus() {
        this.setMessage("");
        if (this.selectStatus.equals("0")) {
            this.lblFrDate = "";
            this.txtFrDate = "";
            this.lblToDate = "none";
            this.txtToDate = "none";
            this.setFrDtVal("As On Date");
            this.setFrDt("");
            this.setToDt("");
        } else if (this.selectStatus.equals("2")) {
            this.lblFrDate = "none";
            this.txtFrDate = "none";
            this.lblToDate = "none";
            this.txtToDate = "none";
            this.setFrDtVal("As On Date");
            this.setFrDt("");
            this.setToDt("");
        } else if (this.selectStatus.equals("1")) {
            this.setFrDtVal("From Date");
            this.lblFrDate = "";
            this.txtFrDate = "";
            this.lblToDate = "";
            this.txtToDate = "";
            this.setFrDt("");
            this.setToDt("");
        } else if (this.selectStatus.equals("3")) {
            this.setFrDtVal("From Date");
            this.lblFrDate = "";
            this.txtFrDate = "";
            this.lblToDate = "";
            this.txtToDate = "";
            this.setFrDt("");
            this.setToDt("");
        }

    }

    public void onBlurFrDt() {
        this.setMessage("");
        if (this.frDt == null || this.frDt.equals("") || this.frDt.length() < 10) {
            this.setMessage("Please fill from date !");
            return;
        }

        boolean result = new Validator().validateDate_dd_mm_yyyy(this.frDt);
        if (result == false) {
            this.setMessage("Please fill proper from date !");
            return;
        }
    }

    public void onBlurToDt() {
        this.setMessage("");
        try {
            if (this.toDt == null || this.toDt.equals("") || this.toDt.length() < 10) {
                this.setMessage("Please fill from date !");
                return;
            }

            boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
            if (result == false) {
                this.setMessage("Please fill proper from date !");
                return;
            }

            if (dmy.parse(this.frDt).compareTo(dmy.parse(this.toDt)) > 0) {
                this.setMessage("From date should be less than To date !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void printAction() {
        this.setMessage("");
        reportList = new ArrayList<DueDateDiaryPojo>();
        if (this.selectStatus.equals("1")) {
            if (this.frDt == null || this.frDt.equals("") || this.frDt.length() < 10) {
                this.setMessage("Please fill from date !");
                return;
            }
            if (this.toDt == null || this.toDt.equals("") || this.toDt.length() < 10) {
                this.setMessage("Please fill from date !");
                return;
            }
        }
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            String repName = "Investment FDR Report";
            if (this.selectStatus.equals("0")) {
                List<Object[]> dataList = remoteObj.getDueDateReportActive1(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(this.frDt)),1)));
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        DueDateDiaryPojo pojo = new DueDateDiaryPojo();
                        Object[] array = dataList.get(i);
                        InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                        InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                        pojo.setSno(i);
                        pojo.setCtrlNo(dateEntity.getCtrlNo());
                        pojo.setPurDt(dmy.format(dateEntity.getPurchaseDt()));
                        pojo.setMatDt(dmy.format(dateEntity.getMatDt()));
                        pojo.setFdrNo(detailEntity.getFdrNo());
                        pojo.setFaceValue(formatter.format(detailEntity.getFaceValue()));
                        pojo.setBookValue(formatter.format(detailEntity.getBookValue()));
                        pojo.setRoi(detailEntity.getRoi());
                        pojo.setSellarName(detailEntity.getSellerName());
                        pojo.setIntOpt(detailEntity.getIntOpt());
                        pojo.setBranch(detailEntity.getBranch());
                        //pojo.setTotalRec(formatter.format(dateEntity.getTotAmtIntRec()));
                        pojo.setTotalRec(Double.toString(remoteObj.getReceviableAmt(Integer.parseInt(dateEntity.getCtrlNo().toString()), ymd.format(dmy.parse(this.frDt)))));
                        pojo.setOldCtrlNo(0);
                        pojo.setCloseDt(dmy.format(new Date()));
                        pojo.setPrd(dateEntity.getDays().toString() + "D " + dateEntity.getMonths().toString() + "M " + dateEntity.getYears().toString() + "Y");

                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
            } else if (this.selectStatus.equals("1")) {
                List<Object[]> dataList = remoteObj.getDueDateReportClose(dmy.parse(this.frDt), dmy.parse(this.toDt));
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        DueDateDiaryPojo pojo = new DueDateDiaryPojo();
                        Object[] array = dataList.get(i);
                        InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                        InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                        pojo.setSno(i);
                        pojo.setCtrlNo(dateEntity.getCtrlNo());
                        pojo.setPurDt(dmy.format(dateEntity.getPurchaseDt()));
                        pojo.setMatDt(dmy.format(dateEntity.getMatDt()));
                        pojo.setFdrNo(detailEntity.getFdrNo());
                        pojo.setFaceValue(formatter.format(detailEntity.getFaceValue()));
                        pojo.setBookValue(formatter.format(detailEntity.getBookValue()));
                        pojo.setRoi(detailEntity.getRoi());
                        pojo.setSellarName(detailEntity.getSellerName());
                        pojo.setIntOpt(detailEntity.getIntOpt());
                        pojo.setBranch(detailEntity.getBranch());
                        //pojo.setTotalRec(formatter.format(dateEntity.getTotAmtIntRec()));
                        pojo.setTotalRec(Double.toString(remoteObj.getReceviableAmt(Integer.parseInt(dateEntity.getCtrlNo().toString()), ymd.format(dmy.parse(this.toDt)))));
                        pojo.setOldCtrlNo(detailEntity.getCtrlNo());
                        pojo.setCloseDt(dmy.format(new Date()));
                        pojo.setPrd(dateEntity.getDays().toString() + "D " + dateEntity.getMonths().toString() + "M " + dateEntity.getYears().toString() + "Y");

                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
            } else if (this.selectStatus.equals("2")) {
                List<Object[]> dataList = remoteObj.getDueDateReportRenewed();
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        DueDateDiaryPojo pojo = new DueDateDiaryPojo();
                        Object[] array = dataList.get(i);
                        InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                        InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                        pojo.setSno(i);
                        pojo.setCtrlNo(dateEntity.getCtrlNo());
                        pojo.setPurDt(dmy.format(dateEntity.getPurchaseDt()));
                        pojo.setMatDt(dmy.format(dateEntity.getMatDt()));
                        pojo.setFdrNo(detailEntity.getFdrNo());
                        pojo.setFaceValue(formatter.format(detailEntity.getFaceValue()));
                        pojo.setBookValue(formatter.format(detailEntity.getBookValue()));
                        pojo.setRoi(detailEntity.getRoi());
                        pojo.setSellarName(detailEntity.getSellerName());
                        pojo.setIntOpt(detailEntity.getIntOpt());
                        pojo.setBranch(detailEntity.getBranch());
                        //pojo.setTotalRec(formatter.format(dateEntity.getTotAmtIntRec()));
                        pojo.setTotalRec(Double.toString(remoteObj.getReceviableAmt(Integer.parseInt(dateEntity.getCtrlNo().toString()), ymd.format(new Date()))));
                        pojo.setOldCtrlNo(detailEntity.getCtrlNo());
                        if (dateEntity.getClosedDt() == null || dateEntity.getClosedDt().toString().equalsIgnoreCase("")) {
                            pojo.setCloseDt("");
                        } else {
                            pojo.setCloseDt(dmy.format(dateEntity.getClosedDt()));
                        }
                        pojo.setPrd(dateEntity.getDays().toString() + "D " + dateEntity.getMonths().toString() + "M " + dateEntity.getYears().toString() + "Y");

                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
            } else if (this.selectStatus.equals("3")) {
                List<Object[]> dataList = remoteObj.getDueDateReportObtained(dmy.parse(this.frDt), dmy.parse(this.toDt));
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        DueDateDiaryPojo pojo = new DueDateDiaryPojo();
                        Object[] array = dataList.get(i);
                        InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                        InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                        pojo.setSno(i);
                        pojo.setCtrlNo(dateEntity.getCtrlNo());
                        pojo.setPurDt(dmy.format(dateEntity.getPurchaseDt()));
                        pojo.setMatDt(dmy.format(dateEntity.getMatDt()));
                        pojo.setFdrNo(detailEntity.getFdrNo());
                        pojo.setFaceValue(formatter.format(detailEntity.getFaceValue()));
                        pojo.setBookValue(formatter.format(detailEntity.getBookValue()));
                        pojo.setRoi(detailEntity.getRoi());
                        pojo.setSellarName(detailEntity.getSellerName());
                        pojo.setIntOpt(detailEntity.getIntOpt());
                        pojo.setBranch(detailEntity.getBranch());
                        pojo.setTotalRec(formatter.format(dateEntity.getTotAmtIntRec()));
                        pojo.setTotalRec(Double.toString(remoteObj.getReceviableAmt(Integer.parseInt(dateEntity.getCtrlNo().toString()), ymd.format(dmy.parse(this.toDt)))));
                        pojo.setOldCtrlNo(detailEntity.getCtrlNo());
                        pojo.setCloseDt(dmy.format(new Date()));
                        pojo.setPrd(dateEntity.getDays().toString() + "D " + dateEntity.getMonths().toString() + "M " + dateEntity.getYears().toString() + "Y");

                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
            }
            String repDate = "";
            if (this.selectStatus.equals("2") || this.selectStatus.equals("0")) {
                repDate = this.frDt;
            } else {
                repDate = "From " + this.frDt + " To " + this.toDt;
            }
            Map fillParams = new HashMap();
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", brList.get(0));
            fillParams.put("pBranchAddress", brList.get(1));
            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", repDate);
            new ReportBean().jasperReport("dueDateActiveReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetAction() {
        this.setMessage("");
        this.setSelectStatus("ACTIVE");
        this.lblFrDate = "none";
        this.txtFrDate = "none";
        this.lblToDate = "none";
        this.txtToDate = "none";
        this.setFrDt("");
        this.setToDt("");
    }

    public String exitAction() {
        try {
            resetAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}