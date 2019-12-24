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
import com.cbs.dto.report.FormNo15gPart1Pojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class Form15G15HDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String frDt;
    private String toDt;
    private String finYear;
    private String newFinYear;
    private Date date = new Date();
    private String repType;
    private List<SelectItem> repTypeList;
    TdReceiptManagementFacadeRemote RemoteCode;
    private MiscReportFacadeRemote remoteFacade;
    private List<FormNo15gPart1Pojo> reportList = new ArrayList<FormNo15gPart1Pojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public Form15G15HDetail() {
        try {
            frDt = dmy.format(date);
            toDt = dmy.format(date);
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("Form 15H Basic", "Form 15H Basic"));
            repTypeList.add(new SelectItem("Form 15H Income", "Form 15H Income"));
            repTypeList.add(new SelectItem("Form 15G Basic", "Form 15G Basic"));
            repTypeList.add(new SelectItem("Form 15G Income", "Form 15G Income"));
            
            repTypeList.add(new SelectItem("Society & Exemption Basic", "Society & Exemption Basic"));
            repTypeList.add(new SelectItem("Society & Exemption Income", "Society & Exemption Income"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getNewfYear() {
        this.newFinYear = finYear;
    }

    public FastReportBuilder excelPrint() {
        setMessage("");
        FastReportBuilder fastReport = new FastReportBuilder();
        try {

//            if (finYear == null || finYear.equalsIgnoreCase("")) {
//                setMessage("Please fill proper 4 digits financial year !");
//                return null;
//            }
            String h1 = "", h2 = "";
            if (repType.contains("Form 15G")) {
                h1 = "Total No. of Form No. 15G filed";
                h2 = "Aggregate amount of  income for which Form No.15G filed";
            } else {
                h1 = "Total No. of Form No. 15H filed";
                h2 = "Aggregate amount of  income for which Form No.15H filed";
            }

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return null;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return null;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("from date should be less than current date !");
                return null;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return null;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return null;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("to date should be less than current date !");
                return null;
            }
            if (repType.equalsIgnoreCase("S")) {
                setMessage("Please select report type !");
                return null;
            }

            if (finYear == null || finYear.equalsIgnoreCase("")) {
                setMessage("Please fill proper 4 digits financial year !");
                return null;
            }

            reportList = remoteFacade.getForm15g15hData(branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), repType, finYear);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits !");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn uniNo = ReportBean.getColumn("uniNo", String.class, "Unique Identification No.", 170);
            AbstractColumn nameAssessee = ReportBean.getColumn("nameAssessee", String.class, "Name of the Assessee", 150);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN of the Assessee", 80);
            AbstractColumn status = ReportBean.getColumn("status", String.class, "Status", 70);
            AbstractColumn rStatus = ReportBean.getColumn("rStatus", String.class, "Residential Status", 150);
            AbstractColumn previousYear = ReportBean.getColumn("previousYear", String.class, "Previous year(P.Y) (for which declaration is being made)", 70);
            AbstractColumn flatDoorBlock = ReportBean.getColumn("flatDoorBlock", String.class, "Flat/Door/Block No.", 100);
            AbstractColumn roadStreetLane = ReportBean.getColumn("roadStreetLane", String.class, "Road/Street/Lane", 100);
            AbstractColumn premises = ReportBean.getColumn("premises", String.class, "Name of  premises", 120);
            AbstractColumn areaLocality = ReportBean.getColumn("areaLocality", String.class, "Area/ Locality", 100);
            AbstractColumn twonCityDist = ReportBean.getColumn("twonCityDist", String.class, "Town/City/ District", 100);
            AbstractColumn state = ReportBean.getColumn("state", String.class, "State", 80);
            AbstractColumn pinNo = ReportBean.getColumn("pinNo", String.class, "PIN", 60);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email", 100);
            AbstractColumn stdCode = ReportBean.getColumn("stdCode", String.class, "STD Code", 60);
            AbstractColumn telePhoneNo = ReportBean.getColumn("telePhoneNo", String.class, "Telephone No", 80);
            AbstractColumn mobileNo = ReportBean.getColumn("mobileNo", String.class, "Mobile No.", 80);
            AbstractColumn a15Yes = ReportBean.getColumn("a15Yes", String.class, "Whether assessed  to tax under the  Income-tax Act,1961", 60);
            AbstractColumn b15LatestAssYear = ReportBean.getColumn("b15LatestAssYear", String.class, "If yes, latest assessment year for which assessed", 60);
            AbstractColumn estimatedIncome1 = ReportBean.getColumn("estimatedIncome1", String.class, "Estimated income  for which  this declaration is made", 80);
            AbstractColumn estimatedTotalIncome1 = ReportBean.getColumn("estimatedTotalIncome1", String.class, "Estimated total income of the P.Y.  in which Estimated income for which  this declaration is made to be included.", 80);
            AbstractColumn totalNoOf15g = ReportBean.getColumn("totalNoOf15g", String.class, h1, 60);
            AbstractColumn aggregateAmt1 = ReportBean.getColumn("aggregateAmt1", String.class, h2, 80);
            AbstractColumn declarationDt = ReportBean.getColumn("declarationDt", String.class, "Date on which  Declaration is received  (DD/MM/YYYY)", 70);
            AbstractColumn paidAmt1 = ReportBean.getColumn("paidAmt1", String.class, "Amount of income paid", 80);
            AbstractColumn paidDt = ReportBean.getColumn("paidDt", String.class, "Date on which the  income has been  paid/credited (DD/MM/YYYY)", 70);
            AbstractColumn recordType = ReportBean.getColumn("recordType", String.class, "Record Type", 70);
            AbstractColumn dateOfbirth = ReportBean.getColumn("dateOfbirth", String.class, "Date of Birth (DD/MM/YYYY)", 70);
            AbstractColumn idNoOfRelevant = ReportBean.getColumn("idNoOfRelevant", String.class, "Identification number of relevant investment/ account, etc.", 80);
            AbstractColumn natureOfIncome = ReportBean.getColumn("natureOfIncome", String.class, "Nature of income", 130);
            AbstractColumn sectionUnder = ReportBean.getColumn("sectionUnder", String.class, "Section under which tax is deductible", 60);
            AbstractColumn incomeAmt1 = ReportBean.getColumn("incomeAmt1", String.class, "Amount of income", 80);

            if (repType.equalsIgnoreCase("Form 15G Basic") || repType.equalsIgnoreCase("Society & Exemption Basic")) {
                fastReport.addColumn(uniNo);
                width = width + uniNo.getWidth();

                fastReport.addColumn(nameAssessee);
                width = width + nameAssessee.getWidth();

                fastReport.addColumn(panNo);
                width = width + panNo.getWidth();

                fastReport.addColumn(status);
                width = width + status.getWidth();

                fastReport.addColumn(rStatus);
                width = width + rStatus.getWidth();

                fastReport.addColumn(previousYear);
                width = width + previousYear.getWidth();

                fastReport.addColumn(flatDoorBlock);
                width = width + flatDoorBlock.getWidth();

                fastReport.addColumn(roadStreetLane);
                width = width + roadStreetLane.getWidth();

                fastReport.addColumn(premises);
                width = width + premises.getWidth();

                fastReport.addColumn(areaLocality);
                width = width + areaLocality.getWidth();

                fastReport.addColumn(twonCityDist);
                width = width + twonCityDist.getWidth();

                fastReport.addColumn(state);
                width = width + state.getWidth();

                fastReport.addColumn(pinNo);
                width = width + pinNo.getWidth();

                fastReport.addColumn(email);
                width = width + email.getWidth();

                fastReport.addColumn(stdCode);
                width = width + stdCode.getWidth();

                fastReport.addColumn(telePhoneNo);
                width = width + telePhoneNo.getWidth();

                fastReport.addColumn(mobileNo);
                width = width + mobileNo.getWidth();

                fastReport.addColumn(a15Yes);
                width = width + a15Yes.getWidth();

                fastReport.addColumn(b15LatestAssYear);
                width = width + b15LatestAssYear.getWidth();

                fastReport.addColumn(estimatedIncome1);
                estimatedIncome1.setStyle(style);
                width = width + estimatedIncome1.getWidth();

                fastReport.addColumn(estimatedTotalIncome1);
                estimatedTotalIncome1.setStyle(style);
                width = width + estimatedTotalIncome1.getWidth();

                fastReport.addColumn(totalNoOf15g);
                width = width + totalNoOf15g.getWidth();

                fastReport.addColumn(aggregateAmt1);
                aggregateAmt1.setStyle(style);
                width = width + aggregateAmt1.getWidth();

                fastReport.addColumn(declarationDt);
                width = width + declarationDt.getWidth();

                fastReport.addColumn(paidAmt1);
                paidAmt1.setStyle(style);
                width = width + paidAmt1.getWidth();

                fastReport.addColumn(paidDt);
                width = width + paidDt.getWidth();

                fastReport.addColumn(recordType);
                width = width + recordType.getWidth();

            } else if (repType.equalsIgnoreCase("Form 15G Income")
                    || repType.equalsIgnoreCase("Form 15H Income") 
                    || repType.equalsIgnoreCase("Society & Exemption Income")) {

                fastReport.addColumn(uniNo);
                width = width + uniNo.getWidth();

                fastReport.addColumn(idNoOfRelevant);
                width = width + idNoOfRelevant.getWidth();

                fastReport.addColumn(natureOfIncome);
                width = width + natureOfIncome.getWidth();

                fastReport.addColumn(sectionUnder);
                width = width + sectionUnder.getWidth();

                fastReport.addColumn(incomeAmt1);
                width = width + incomeAmt1.getWidth();
            } else if (repType.equalsIgnoreCase("Form 15H Basic")) {

                fastReport.addColumn(uniNo);
                width = width + uniNo.getWidth();

                fastReport.addColumn(nameAssessee);
                width = width + nameAssessee.getWidth();

                fastReport.addColumn(panNo);
                width = width + panNo.getWidth();

                fastReport.addColumn(dateOfbirth);
                width = width + dateOfbirth.getWidth();

                fastReport.addColumn(previousYear);
                width = width + previousYear.getWidth();

                fastReport.addColumn(flatDoorBlock);
                width = width + flatDoorBlock.getWidth();


                fastReport.addColumn(roadStreetLane);
                width = width + roadStreetLane.getWidth();

                fastReport.addColumn(premises);
                width = width + premises.getWidth();

                fastReport.addColumn(areaLocality);
                width = width + areaLocality.getWidth();

                fastReport.addColumn(twonCityDist);
                width = width + twonCityDist.getWidth();

                fastReport.addColumn(state);
                width = width + state.getWidth();

                fastReport.addColumn(pinNo);
                width = width + pinNo.getWidth();

                fastReport.addColumn(email);
                width = width + email.getWidth();

                fastReport.addColumn(stdCode);
                width = width + stdCode.getWidth();

                fastReport.addColumn(telePhoneNo);
                width = width + telePhoneNo.getWidth();

                fastReport.addColumn(mobileNo);
                width = width + mobileNo.getWidth();

                fastReport.addColumn(a15Yes);
                width = width + a15Yes.getWidth();

                fastReport.addColumn(b15LatestAssYear);
                width = width + b15LatestAssYear.getWidth();

                fastReport.addColumn(estimatedIncome1);
                estimatedIncome1.setStyle(style);
                width = width + estimatedIncome1.getWidth();

                fastReport.addColumn(estimatedTotalIncome1);
                estimatedTotalIncome1.setStyle(style);
                width = width + estimatedTotalIncome1.getWidth();

                fastReport.addColumn(totalNoOf15g);
                width = width + totalNoOf15g.getWidth();

                fastReport.addColumn(aggregateAmt1);
                aggregateAmt1.setStyle(style);
                width = width + aggregateAmt1.getWidth();

                fastReport.addColumn(declarationDt);
                width = width + declarationDt.getWidth();

                fastReport.addColumn(paidAmt1);
                paidAmt1.setStyle(style);
                width = width + paidAmt1.getWidth();

                fastReport.addColumn(paidDt);
                width = width + paidDt.getWidth();

                fastReport.addColumn(recordType);
                width = width + recordType.getWidth();

            }
            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle(repType + " Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReport, repType + " Report_" + ymd.format(dmy.parse(frDt)) + "  to  " + ymd.format(dmy.parse(toDt)));



        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public FastReportBuilder csvPrint() {
        setMessage("");
        FastReportBuilder fastReport = new FastReportBuilder();
        try {

            String h1 = "", h2 = "";

//            if (finYear == null || finYear.equalsIgnoreCase("")) {
//                setMessage("Please fill proper 4 digits financial year !");
//                return null;
//            }

            if (repType.contains("Form 15G")) {
                h1 = "Total No. of Form No. 15G filed";
                h2 = "Aggregate amount of  income for which Form No.15G filed";
            } else {
                h1 = "Total No. of Form No. 15H filed";
                h2 = "Aggregate amount of  income for which Form No.15H filed";
            }

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return null;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return null;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("from date should be less than current date !");
                return null;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return null;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return null;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("to date should be less than current date !");
                return null;
            }
            if (repType.equalsIgnoreCase("S")) {
                setMessage("Please select report type !");
                return null;
            }
            if (finYear == null || finYear.equalsIgnoreCase("")) {
                setMessage("Please fill proper 4 digits financial year !");
                return null;
            }

            reportList = remoteFacade.getForm15g15hData(branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), repType, finYear);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits !");
            }

            for (int i = 0; i < reportList.size(); i++) {
                FormNo15gPart1Pojo obj = reportList.get(i);
                System.out.println(obj.getAggregateAmt1());
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn uniNo = ReportBean.getColumn("uniNo", String.class, "Unique Identification No.", 170);
            AbstractColumn nameAssessee = ReportBean.getColumn("nameAssessee", String.class, "Name of the Assessee", 150);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN of the Assessee", 80);
            AbstractColumn status = ReportBean.getColumn("status", String.class, "Status", 70);
            AbstractColumn rStatus = ReportBean.getColumn("rStatus", String.class, "Residential Status", 150);
            AbstractColumn previousYear = ReportBean.getColumn("previousYear", String.class, "Previous year(P.Y) (for which declaration is being made)", 70);
            AbstractColumn flatDoorBlock = ReportBean.getColumn("flatDoorBlock", String.class, "Flat/Door/Block No.", 100);
            AbstractColumn roadStreetLane = ReportBean.getColumn("roadStreetLane", String.class, "Road/Street/Lane", 100);
            AbstractColumn premises = ReportBean.getColumn("premises", String.class, "Name of  premises", 120);
            AbstractColumn areaLocality = ReportBean.getColumn("areaLocality", String.class, "Area/ Locality", 100);
            AbstractColumn twonCityDist = ReportBean.getColumn("twonCityDist", String.class, "Town/City/ District", 100);
            AbstractColumn state = ReportBean.getColumn("state", String.class, "State", 80);
            AbstractColumn pinNo = ReportBean.getColumn("pinNo", String.class, "PIN", 60);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email", 100);
            AbstractColumn stdCode = ReportBean.getColumn("stdCode", String.class, "STD Code", 60);
            AbstractColumn telePhoneNo = ReportBean.getColumn("telePhoneNo", String.class, "Telephone No", 80);
            AbstractColumn mobileNo = ReportBean.getColumn("mobileNo", String.class, "Mobile No.", 80);
            AbstractColumn a15Yes = ReportBean.getColumn("a15Yes", String.class, "Whether assessed  to tax under the  Income-tax Act,1961", 60);
            AbstractColumn b15LatestAssYear = ReportBean.getColumn("b15LatestAssYear", String.class, "If yes, latest assessment year for which assessed", 60);
            AbstractColumn estimatedIncome1 = ReportBean.getColumn("estimatedIncome1", String.class, "Estimated income  for which  this declaration is made", 80);
            AbstractColumn estimatedTotalIncome1 = ReportBean.getColumn("estimatedTotalIncome1", String.class, "Estimated total income of the P.Y.  in which Estimated income for which  this declaration is made to be included.", 80);
            AbstractColumn totalNoOf15g = ReportBean.getColumn("totalNoOf15g", String.class, h1, 60);
            AbstractColumn aggregateAmt1 = ReportBean.getColumn("aggregateAmt1", String.class, h2, 80);
            AbstractColumn declarationDt = ReportBean.getColumn("declarationDt", String.class, "Date on which  Declaration is received  (DD/MM/YYYY)", 70);
            AbstractColumn paidAmt1 = ReportBean.getColumn("paidAmt1", String.class, "Amount of income paid", 80);
            AbstractColumn paidDt = ReportBean.getColumn("paidDt", String.class, "Date on which the  income has been  paid/credited (DD/MM/YYYY)", 70);
            AbstractColumn recordType = ReportBean.getColumn("recordType", String.class, "Record Type", 70);
            AbstractColumn dateOfbirth = ReportBean.getColumn("dateOfbirth", String.class, "Date of Birth (DD/MM/YYYY)", 70);
            AbstractColumn idNoOfRelevant = ReportBean.getColumn("idNoOfRelevant", String.class, "Identification number of relevant investment/ account, etc.", 80);
            AbstractColumn natureOfIncome = ReportBean.getColumn("natureOfIncome", String.class, "Nature of income", 130);
            AbstractColumn sectionUnder = ReportBean.getColumn("sectionUnder", String.class, "Section under which tax is deductible", 60);
            AbstractColumn incomeAmt1 = ReportBean.getColumn("incomeAmt1", String.class, "Amount of income", 80);

            if (repType.equalsIgnoreCase("Form 15G Basic")) {
                fastReport.addColumn(uniNo);
                width = width + uniNo.getWidth();

                fastReport.addColumn(nameAssessee);
                width = width + nameAssessee.getWidth();

                fastReport.addColumn(panNo);
                width = width + panNo.getWidth();

                fastReport.addColumn(status);
                width = width + status.getWidth();

                fastReport.addColumn(rStatus);
                width = width + rStatus.getWidth();

                fastReport.addColumn(previousYear);
                width = width + previousYear.getWidth();

                fastReport.addColumn(flatDoorBlock);
                width = width + flatDoorBlock.getWidth();

                fastReport.addColumn(roadStreetLane);
                width = width + roadStreetLane.getWidth();

                fastReport.addColumn(premises);
                width = width + premises.getWidth();

                fastReport.addColumn(areaLocality);
                width = width + areaLocality.getWidth();

                fastReport.addColumn(twonCityDist);
                width = width + twonCityDist.getWidth();

                fastReport.addColumn(state);
                width = width + state.getWidth();

                fastReport.addColumn(pinNo);
                width = width + pinNo.getWidth();

                fastReport.addColumn(email);
                width = width + email.getWidth();

                fastReport.addColumn(stdCode);
                width = width + stdCode.getWidth();

                fastReport.addColumn(telePhoneNo);
                width = width + telePhoneNo.getWidth();

                fastReport.addColumn(mobileNo);
                width = width + mobileNo.getWidth();

                fastReport.addColumn(a15Yes);
                width = width + a15Yes.getWidth();

                fastReport.addColumn(b15LatestAssYear);
                width = width + b15LatestAssYear.getWidth();

                fastReport.addColumn(estimatedIncome1);
                estimatedIncome1.setStyle(style);
                width = width + estimatedIncome1.getWidth();

                fastReport.addColumn(estimatedTotalIncome1);
                estimatedTotalIncome1.setStyle(style);
                width = width + estimatedTotalIncome1.getWidth();

                fastReport.addColumn(totalNoOf15g);
                width = width + totalNoOf15g.getWidth();

                fastReport.addColumn(aggregateAmt1);
                aggregateAmt1.setStyle(style);
                width = width + aggregateAmt1.getWidth();

                fastReport.addColumn(declarationDt);
                width = width + declarationDt.getWidth();

                fastReport.addColumn(paidAmt1);
                paidAmt1.setStyle(style);
                width = width + paidAmt1.getWidth();

                fastReport.addColumn(paidDt);
                width = width + paidDt.getWidth();

                fastReport.addColumn(recordType);
                width = width + recordType.getWidth();

            } else if (repType.equalsIgnoreCase("Form 15G Income")
                    || repType.equalsIgnoreCase("Form 15H Income")) {

                fastReport.addColumn(uniNo);
                width = width + uniNo.getWidth();

                fastReport.addColumn(idNoOfRelevant);
                width = width + idNoOfRelevant.getWidth();

                fastReport.addColumn(natureOfIncome);
                width = width + natureOfIncome.getWidth();

                fastReport.addColumn(sectionUnder);
                width = width + sectionUnder.getWidth();

                fastReport.addColumn(incomeAmt1);
                width = width + incomeAmt1.getWidth();
            } else if (repType.equalsIgnoreCase("Form 15H Basic")) {

                fastReport.addColumn(uniNo);
                width = width + uniNo.getWidth();

                fastReport.addColumn(nameAssessee);
                width = width + nameAssessee.getWidth();

                fastReport.addColumn(panNo);
                width = width + panNo.getWidth();

                fastReport.addColumn(dateOfbirth);
                width = width + dateOfbirth.getWidth();

                fastReport.addColumn(previousYear);
                width = width + previousYear.getWidth();

                fastReport.addColumn(flatDoorBlock);
                width = width + flatDoorBlock.getWidth();


                fastReport.addColumn(roadStreetLane);
                width = width + roadStreetLane.getWidth();

                fastReport.addColumn(premises);
                width = width + premises.getWidth();

                fastReport.addColumn(areaLocality);
                width = width + areaLocality.getWidth();

                fastReport.addColumn(twonCityDist);
                width = width + twonCityDist.getWidth();

                fastReport.addColumn(state);
                width = width + state.getWidth();

                fastReport.addColumn(pinNo);
                width = width + pinNo.getWidth();

                fastReport.addColumn(email);
                width = width + email.getWidth();

                fastReport.addColumn(stdCode);
                width = width + stdCode.getWidth();

                fastReport.addColumn(telePhoneNo);
                width = width + telePhoneNo.getWidth();

                fastReport.addColumn(mobileNo);
                width = width + mobileNo.getWidth();

                fastReport.addColumn(a15Yes);
                width = width + a15Yes.getWidth();

                fastReport.addColumn(b15LatestAssYear);
                width = width + b15LatestAssYear.getWidth();

                fastReport.addColumn(estimatedIncome1);
                estimatedIncome1.setStyle(style);
                width = width + estimatedIncome1.getWidth();

                fastReport.addColumn(estimatedTotalIncome1);
                estimatedTotalIncome1.setStyle(style);
                width = width + estimatedTotalIncome1.getWidth();

                fastReport.addColumn(totalNoOf15g);
                width = width + totalNoOf15g.getWidth();

                fastReport.addColumn(aggregateAmt1);
                aggregateAmt1.setStyle(style);
                width = width + aggregateAmt1.getWidth();

                fastReport.addColumn(declarationDt);
                width = width + declarationDt.getWidth();

                fastReport.addColumn(paidAmt1);
                paidAmt1.setStyle(style);
                width = width + paidAmt1.getWidth();

                fastReport.addColumn(paidDt);
                width = width + paidDt.getWidth();

                fastReport.addColumn(recordType);
                width = width + recordType.getWidth();

            }
            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            //fastReport.setTitle(repType + " Report");

            new ReportBean().dynamicJasperCSV(new JRBeanCollectionDataSource(reportList), fastReport, repType + " Report_" + ymd.format(dmy.parse(frDt)) + "  to  " + ymd.format(dmy.parse(toDt)));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void refreshPage() {
        setMessage("");
        finYear = "";
        newFinYear = "";
        frDt = dmy.format(date);
        toDt = dmy.format(date);
        repType = "S";
    }

    public String exitPage() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getNewFinYear() {
        return newFinYear;
    }

    public void setNewFinYear(String newFinYear) {
        this.newFinYear = newFinYear;
    }
}
