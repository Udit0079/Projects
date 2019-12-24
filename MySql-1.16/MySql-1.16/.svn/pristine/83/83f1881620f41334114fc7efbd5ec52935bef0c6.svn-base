/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.DtlRegisterPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class AfsHtmModification extends BaseBean {

    private String ctrl;
    private String marking;
    private String secType;
    private String markingType;
    private List<SelectItem> ctrlList;
    private List<SelectItem> secTypeList;
    private List<SelectItem> markingTestList;
    private String message;
    private Date dt = new Date();
    
    private String bookValue;
    InvestmentMgmtFacadeRemote remoteObject;
    private HoReportFacadeRemote hoRep = null;
    private final String hoRepJndiHomeName = "HoReportFacade";
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String invRepjndiHomeName = "InvestmentReportMgmtFacade";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private String mark;
    private String sellarName;
    private String matdt;
    private String faceValue;

    public AfsHtmModification() {

        try {
            ctrlList = new ArrayList<SelectItem>();
            markingTestList = new ArrayList<SelectItem>();
            secTypeList = new ArrayList<SelectItem>();
            hoRep = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(hoRepJndiHomeName);
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(invRepjndiHomeName);
            remoteObject = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup("InvestmentMgmtFacade");
            secTypeList.add(new SelectItem("S", "--select--"));
            secTypeList.add(new SelectItem("GOVERNMENT SECURITIES", "GOVERNMENT SECURITIES"));
            setOtherList();
            refreshForm();
            this.setMessage("Please select security type !");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void secTypeMethod() {
        this.setMessage("");
        getControlNoData(this.getSecType());
    }

    public void getControlNoData(String type) {
        ctrlList = new ArrayList<SelectItem>();
        ctrlList.add(new SelectItem("--Select--"));
        try {
            List entityList = remoteObject.getControlNo(type.trim());
            if (!entityList.isEmpty()) {
                for (int i = 0; i < entityList.size(); i++) {
                    Vector ele = (Vector) entityList.get(i);
                    ctrlList.add(new SelectItem(ele.get(0).toString()));
                }
            } else {
                this.setMessage("There is no control no !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setmark() {
        setMessage("");
        try {
            List list = remoteObject.getMarkingStatus(this.ctrl, this.secType);
            Vector ele = (Vector) list.get(0);
            this.mark = ele.get(0).toString();
            this.bookValue = ele.get(1).toString();
            if (mark.equalsIgnoreCase("HM")) {
                this.marking = "Held For Maturity";
            } else if (mark.equalsIgnoreCase("HT")) {
                this.marking = "Held for Trading";
            } else if (mark.equalsIgnoreCase("AF")) {
                this.marking = "Available for Sale";
            }
             double amortamt = remoteObj.getAmortAmt(Integer.parseInt(this.ctrl), ymd.format(new Date()));
            this.bookValue = formatter.format(Double.valueOf(ele.get(1).toString())-amortamt);
            this.faceValue = ele.get(2).toString();
            this.sellarName = ele.get(5).toString();
            this.matdt = ele.get(4).toString();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private void setOtherList() {
        markingTestList = new ArrayList<SelectItem>();
        markingTestList.add(new SelectItem("S", "-- Select --"));
        markingTestList.add(new SelectItem("HM", "Held For Maturity"));
        markingTestList.add(new SelectItem("HT", "Held for Trading"));
        markingTestList.add(new SelectItem("AF", "Available for Sale"));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCtrl() {
        return ctrl;
    }

    public void setCtrl(String ctrl) {
        this.ctrl = ctrl;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }

    public List<SelectItem> getCtrlList() {
        return ctrlList;
    }

    public void setCtrlList(List<SelectItem> ctrlList) {
        this.ctrlList = ctrlList;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<SelectItem> getSecTypeList() {
        return secTypeList;
    }

    public void setSecTypeList(List<SelectItem> secTypeList) {
        this.secTypeList = secTypeList;
    }

    public String getMarkingType() {
        return markingType;
    }

    public void setMarkingType(String markingType) {
        this.markingType = markingType;
    }

    public List<SelectItem> getMarkingTestList() {
        return markingTestList;
    }

    public void setMarkingTestList(List<SelectItem> markingTestList) {
        this.markingTestList = markingTestList;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getBookValue() {
        return bookValue;
    }

    public void setBookValue(String bookValue) {
        this.bookValue = bookValue;
    }

    public String getSellarName() {
        return sellarName;
    }

    public void setSellarName(String sellarName) {
        this.sellarName = sellarName;
    }

    public String getMatdt() {
        return matdt;
    }

    public void setMatdt(String matdt) {
        this.matdt = matdt;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public void changeBtnAction() {
        setMessage("");

        try {
            if (this.secType.equalsIgnoreCase("S")) {
                this.setMessage("Please select Security Type !");
                return;
            }
            if (this.ctrl.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Control No !");
                return;
            }
            if (this.markingType.equalsIgnoreCase("S")) {
                this.setMessage("Please select Marking Type !");
                return;
            }
            if (mark.equalsIgnoreCase(markingType)) {
                this.setMessage("Marked and Marking type can't be same !");
                return;
            }
            if (mark.equalsIgnoreCase("AF") && markingType.equalsIgnoreCase("HT")) {
                double ndtl = 0, hmTot = 0;
                List<DtlRegisterPojo> secNdtl = hoRep.getDtlForInvestment("90", ymd.format(dt), "R");
                DtlRegisterPojo classPojo = null;
                for (int i = 0; i < secNdtl.size(); i++) {
                    classPojo = secNdtl.get(i);
                    List<RbiSossPojo> ndtlList = classPojo.getNdtlList();
                    for (int j = 0; j < ndtlList.size(); j++) {
                        ndtl = ndtl + ndtlList.get(j).getAmt().doubleValue();
                    }
                }
                List<Object[]> secList = remoteObj.getAllInvestmentMasterSecurityMaster(this.secType, dt);
                for (int k = 0; k < secList.size(); k++) {
                    Object[] obj = secList.get(k);
                    InvestmentMaster im = (InvestmentMaster) obj[0];
                    if (im.getMarking().equalsIgnoreCase("HM")) {
                        hmTot = hmTot + im.getBookvalue();
                    }
                }
                if ((hmTot + Double.parseDouble(this.getBookValue())) > ((ndtl * 25.0) / 100.0)) {
                    setMessage("Investment in government security under HTM category can't exceed 25% of ndtl");
                    return;
                }
            }
            String result = remoteObject.changeMark(secType, ctrl, mark, markingType, getUserName());
            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Data has been updated successfully");
            }
             refreshPage();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        this.message = "";
        this.setSecType("S");
        this.setCtrl("--Select--");
        this.sellarName = "";
        this.matdt = "";
        this.bookValue = "";
        this.faceValue = "";
        this.marking = "";
        this.setMarkingType("S");
    }
    public void refreshPage() {
        this.setSecType("S");
        this.setCtrl("--Select--");
        this.sellarName = "";
        this.matdt = "";
        this.bookValue = "";
        this.faceValue = "";
        this.marking = "";
        this.setMarkingType("S");
    }
}
