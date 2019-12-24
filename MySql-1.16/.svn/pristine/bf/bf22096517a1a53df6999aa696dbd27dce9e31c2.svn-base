/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.deaf;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DeafMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.deaf.DeafForm1Pojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Form1 extends BaseBean {

    private String message;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private String branch;
    private List<SelectItem> branchList;
    private String repType;
    private List<SelectItem> repTypeList;
    private String name1;
    private String designation1;
    private String name2;
    private String designation2;
    private String auditorName;
    private String auditorAdd;
    private String place;
    private String display;
    private DeafMgmtFacadeRemote deafRemote;
    private CommonReportMethodsRemote common = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date curDt = new Date();
    List<DeafForm1Pojo> repoprtList = new ArrayList<DeafForm1Pojo>();

    public Form1() {
        branchList = new ArrayList<SelectItem>();
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            deafRemote = (DeafMgmtFacadeRemote) ServiceLocator.getInstance().lookup("DeafMgmtFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

//            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
//            for (int i = 0; i < list.size(); i++) {
//                Vector vtr = (Vector) list.get(i);
//                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
//            }
//            

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            //Set all months
            monthList = new ArrayList<SelectItem>();
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }

            repTypeList = new ArrayList<SelectItem>();
            // repTypeList.add(new SelectItem("Form I", "Form I"));
            repTypeList.add(new SelectItem("Form II", "Form I & II"));
            repTypeList.add(new SelectItem("Form III", "Form III"));
            repTypeList.add(new SelectItem("Form IV", "Form IV"));
            repTypeList.add(new SelectItem("Form V", "Form V"));
            repTypeList.add(new SelectItem("Form VIII", "Form VIII"));
            btnRefreshAction();
            this.display = "none";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void chenageOperation() {
        if (repType.equalsIgnoreCase("Form III")) {
            this.display = "";
        } else {
            this.display = "none";
        }
    }

    public void btnPdfAction() {
        setMessage("");
        try {
            if (validateField(this.month, this.year).equals("true")) {

                String generatedDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
                        Integer.parseInt(this.year)));
                if (!new Validator().validateDate_dd_mm_yyyy(generatedDt)) {
                    this.setMessage("Please fill proper month and year.");
                    return;
                }

                //date for Opening balance at the beginning of the month 
                String prevEndDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month) - 1, Integer.parseInt(this.year)));
                if (month.equalsIgnoreCase("1")) {
                    prevEndDt = CbsUtil.dateAdd(ymd.format(dmy.parse(prevEndDt)), 1);
                    prevEndDt = dmy.format(ymd.parse(prevEndDt));
                }

                String genMM = generatedDt.substring(3, 5);
                String genYYYY = generatedDt.substring(6, 10);
                String genFirstDt = genYYYY + genMM + "01";

                String reportName = "Deaf Account Detail Report";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDt", generatedDt);
                fillParams.put("pReportName", reportName);
                fillParams.put("pMonth", common.getMonthName(Integer.parseInt(this.month)));

                fillParams.put("pYear", this.year);
                fillParams.put("pName1", name1);
                fillParams.put("pDesig1", designation1);
                fillParams.put("pName2", name2);
                fillParams.put("pDesig2", designation2);
                fillParams.put("pAuditorName", auditorName);
                fillParams.put("pAuditorAdd", auditorAdd);
                fillParams.put("pPlace", place);
                fillParams.put("pDate", place);
                fillParams.put("pPhoneNo", deafRemote.setTelephoneNo(branch));
                fillParams.put("pDeafCodeRbi", common.getCodeFromCbsParameterInfo("DEAF-CODE-ALLOTTED-RBI"));

                repoprtList = deafRemote.getForm1Data(this.branch, genFirstDt, ymd.format(dmy.parse(generatedDt)), repType, ymd.format(dmy.parse(prevEndDt)));
                if (repoprtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                
                if(this.branch.equalsIgnoreCase("0A")){
                   this.branch = getOrgnBrCode();
                }

                if (repType.equalsIgnoreCase("Form I")) {
                    new ReportBean().openPdf("Form I_"+common.getAlphacodeByBrncode(branch)+"_" + ymd.format(dmy.parse(getTodayDate())), "Form1_Deaf_AccountDetail", new JRBeanCollectionDataSource(repoprtList), fillParams);
                } else if (repType.equalsIgnoreCase("Form II")) {
                    new ReportBean().openPdf("Form II_"+common.getAlphacodeByBrncode(branch)+"_" + ymd.format(dmy.parse(getTodayDate())), "FormII_UnclaimedDeposit", new JRBeanCollectionDataSource(repoprtList), fillParams);
                } else if (repType.equalsIgnoreCase("Form III")) {
                    new ReportBean().openPdf("Form III_"+common.getAlphacodeByBrncode(branch)+"_" + ymd.format(dmy.parse(getTodayDate())), "FormIII_ClaimFund", new JRBeanCollectionDataSource(repoprtList), fillParams);
                } else if (repType.equalsIgnoreCase("Form IV")) {
                    new ReportBean().openPdf("Form IV_"+common.getAlphacodeByBrncode(branch)+"_" + ymd.format(dmy.parse(getTodayDate())), "FormIV_ClaimFund", new JRBeanCollectionDataSource(repoprtList), fillParams);
                } else if (repType.equalsIgnoreCase("Form V")) {
                    new ReportBean().openPdf("Form V_"+common.getAlphacodeByBrncode(branch)+"_" + ymd.format(dmy.parse(getTodayDate())), "FormV_DeafAccount", new JRBeanCollectionDataSource(repoprtList), fillParams);
                } else if (repType.equalsIgnoreCase("Form VIII")) {
                    new ReportBean().openPdf("Form VIII_"+common.getAlphacodeByBrncode(branch)+"_" + ymd.format(dmy.parse(getTodayDate())), "FormVIII_UnclaimedDeposit", new JRBeanCollectionDataSource(repoprtList), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", reportName);
            } else {
                this.setMessage(validateField(this.month, this.year));
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateField(String month, String year) {
        String result = "true";
        Pattern p = Pattern.compile("[0-9]*");
        try {
//            //Month validation.
//            if (month == null || month.equals("")) {
//                return result = "Month can not be blank.";
//            }
//            Matcher m = p.matcher(month);
//            if (m.matches() != true) {
//                return result = "Please fill proper Month.";
//            }
//            if (Integer.parseInt(month) <= 0 || Integer.parseInt(month) > 12) {
//                return result = "Month should be between 1 to 12.";
//            }
//            //Year validation.
//            if (year == null || year.equals("")) {
//                return result = "Year can not be blank.";
//            }
//            m = p.matcher(year);
//            if (m.matches() != true) {
//                return result = "Please fill proper Year.";
//            }

            if (month == null || month.equals("0")) {
                return result = "Please select month !";
            }
            if (year == null || year.equals("")) {
                return result = "Year can not be blank.";
            }

            Matcher m = p.matcher(year);
            if (m.matches() != true) {
                return result = "Please fill proper Year.";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setMonth("0");
        this.setYear("");
        this.setName1("");
        this.setName2("");
        this.setDesignation1("");
        this.setDesignation2("");
        this.setAuditorName("");
        this.setAuditorAdd("");
        this.setPlace("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter.
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDesignation1() {
        return designation1;
    }

    public void setDesignation1(String designation1) {
        this.designation1 = designation1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getDesignation2() {
        return designation2;
    }

    public void setDesignation2(String designation2) {
        this.designation2 = designation2;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getAuditorAdd() {
        return auditorAdd;
    }

    public void setAuditorAdd(String auditorAdd) {
        this.auditorAdd = auditorAdd;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
