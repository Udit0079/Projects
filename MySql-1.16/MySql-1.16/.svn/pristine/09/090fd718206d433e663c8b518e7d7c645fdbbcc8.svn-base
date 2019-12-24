/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.CardFillingReportPojo;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author aditya
 *
 */
public class CardReportingFile extends BaseBean {

    private String branchcode;
    private List<SelectItem> branchcodeList;
    private String fromDate;
    private String toDate;
    private String errorMsg;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private MiscMgmtFacadeS1Remote miscmgmt;
    List<CardFillingReportPojo> resultList;

    public CardReportingFile() {
        try {
            fromDate = ymdFormat.format(new Date());
            toDate = ymdFormat.format(new Date());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscmgmt = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchcodeList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchcodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void refresh() {
        this.branchcode = "0";
         fromDate = ymdFormat.format(new Date());
         toDate = ymdFormat.format(new Date());
        this.setErrorMsg("");
    }

    public String exitAction() {
        return "case1";
    }

    public void printExcelReport() {
        try {
            resultList = new ArrayList();
            resultList = miscmgmt.getCadFillingReportDetail(this.branchcode, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)));
            String report = "Card Reporting File";
            FastReportBuilder fastReportBuilder = genrateDaynamicReport();
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(resultList), fastReportBuilder, "Card Reporting File");

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn cardnumber = ReportBean.getColumn("cardnumber", String.class, "Card Number", 100);
            AbstractColumn cardId = ReportBean.getColumn("cardId", String.class, "Card ID", 100);
            AbstractColumn savingno = ReportBean.getColumn("savingacno", String.class, "Saving Account number", 100);
            AbstractColumn currentAcno = ReportBean.getColumn("currentacno", String.class, "Current Account number", 100);
            AbstractColumn ccAcno = ReportBean.getColumn("ccacno", String.class, "CC Account number", 100);
            AbstractColumn embossedName = ReportBean.getColumn("embossedname", String.class, "Embossed Name", 150);
            AbstractColumn lastName = ReportBean.getColumn("lastName", String.class, "Last Name", 100);
            AbstractColumn firstName = ReportBean.getColumn("firstName", String.class, "First Name", 100);
            AbstractColumn middleName = ReportBean.getColumn("middleName", String.class, "Middle Name", 100);
            AbstractColumn addressline1 = ReportBean.getColumn("addressLine1", String.class, "Address Line 1", 200);
            AbstractColumn addressline2 = ReportBean.getColumn("addressLine2", String.class, "Address Line 2", 200);
            AbstractColumn addressline3 = ReportBean.getColumn("addressLine3", String.class, "Address Line 3", 200);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "City", 100);
            AbstractColumn state = ReportBean.getColumn("state", String.class, "State", 80);
            AbstractColumn pincode = ReportBean.getColumn("pincode", String.class, "Pincode", 60);
            AbstractColumn acholdermobileno = ReportBean.getColumn("acholdermobileno", String.class, "Account holder Mobile Number", 100);
            AbstractColumn branchid = ReportBean.getColumn("branchid", String.class, "Branch Id", 60);
            AbstractColumn branchname = ReportBean.getColumn("branchname", String.class, "Branch Name", 150);
            AbstractColumn smsalert = ReportBean.getColumn("smsalert", String.class, "Want to receive SMS transaction alerts ? Y/N", 100);
            AbstractColumn dob = ReportBean.getColumn("dateOfBirth", String.class, "Date of Birth", 100);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN Number", 80);
            AbstractColumn tan = ReportBean.getColumn("tan", String.class, "TAN Number", 80);
            AbstractColumn aadharno = ReportBean.getColumn("aadharno", String.class, "Aadhar Number", 80);

            fastReport.addColumn(cardnumber);
            width = width + cardnumber.getWidth();

            fastReport.addColumn(cardId);
            width = width + cardId.getWidth();

            fastReport.addColumn(savingno);
            width = width + savingno.getWidth();

            fastReport.addColumn(currentAcno);
            width = width + currentAcno.getWidth();

            fastReport.addColumn(ccAcno);
            width = width + ccAcno.getWidth();

            fastReport.addColumn(embossedName);
            width = width + embossedName.getWidth();

            fastReport.addColumn(lastName);
            width = width + lastName.getWidth();

            fastReport.addColumn(firstName);
            width = width + firstName.getWidth();

            fastReport.addColumn(middleName);
            width = width + middleName.getWidth();

            fastReport.addColumn(addressline1);
            width = width + addressline1.getWidth();

            fastReport.addColumn(addressline2);
            width = width + addressline2.getWidth();

            fastReport.addColumn(addressline3);
            width = width + addressline3.getWidth();

            fastReport.addColumn(city);
            width = width + city.getWidth();

            fastReport.addColumn(state);
            width = width + state.getWidth();

            fastReport.addColumn(pincode);
            width = width + pincode.getWidth();

            fastReport.addColumn(acholdermobileno);
            width = width + acholdermobileno.getWidth();

            fastReport.addColumn(branchid);
            width = width + branchid.getWidth();

            fastReport.addColumn(branchname);
            width = width + branchname.getWidth();

            fastReport.addColumn(smsalert);
            width = width + smsalert.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(tan);
            width = width + tan.getWidth();

            fastReport.addColumn(aadharno);
            width = width + aadharno.getWidth();


            Page page = new Page(1042, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Card Reporting File");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public List<SelectItem> getBranchcodeList() {
        return branchcodeList;
    }

    public void setBranchcodeList(List<SelectItem> branchcodeList) {
        this.branchcodeList = branchcodeList;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
