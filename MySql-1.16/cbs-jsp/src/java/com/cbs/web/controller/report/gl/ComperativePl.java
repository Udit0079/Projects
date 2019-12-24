/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.ProfitAndLossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.SecondaryGlReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.other.ComperativePlJrxmlPojo;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ComperativePl extends BaseBean {

    private String errMessage;
    private String branch;
    private String dateOne;
    private String dateTwo;
    private String option;
    private List<SelectItem> branchList;
    private List<SelectItem> optionList;
    private CommonReportMethodsRemote commonRemote;
    private SecondaryGlReportFacadeRemote remote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public ComperativePl() {
        try {
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remote = (SecondaryGlReportFacadeRemote) ServiceLocator.getInstance().lookup("SecondaryGlReportFacade");
            refresh();
            initData();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            branchList = new ArrayList<SelectItem>();
            List list = commonRemote.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrMessage("Please fill Branch Master.");
                return;
            }
            String alphaCode = commonRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("ALL"));
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                branchList.add(new SelectItem(ele.get(1).toString().length() < 2 ? "0" + ele.get(1).toString()
                        : ele.get(1).toString(), ele.get(0).toString()));
            }
            this.setDateOne(getTodayDate());
            this.setDateTwo(getTodayDate());

            optionList = new ArrayList<SelectItem>();
            optionList.add(new SelectItem("0", "Before Year End"));
            optionList.add(new SelectItem("1", "After Year End"));
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setErrMessage("");
        try {
            if (validateField()) {
                List<ProfitAndLossPojo> dataList = remote.getComperativePLDetails(this.branch, ymd.format(dmy.parse(dateOne)),
                        ymd.format(dmy.parse(dateTwo)), this.option);
                if (dataList.isEmpty()) {
                    this.setErrMessage("There is no data to print.");
                    return;
                }
                String reportName = "Comperative Profit Loss";
                Map fillParams = new HashMap();
                List brNameAndAddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchName", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pPrintedDate", getTodayDate());
                fillParams.put("firstDt", dateOne);
                fillParams.put("secondDt", dateTwo);

                List<ComperativePlJrxmlPojo> jrxmlList = new ArrayList<ComperativePlJrxmlPojo>();     //Actual List To Print
                ComperativePlJrxmlPojo jrxmlPojo = new ComperativePlJrxmlPojo();
                //Creating separate income & expenditure list.
                List<ProfitAndLossPojo> incomeList = new ArrayList<ProfitAndLossPojo>();
                List<ProfitAndLossPojo> expenseList = new ArrayList<ProfitAndLossPojo>();

//                for (int i = 0; i < dataList.size(); i++) {
//                    ProfitAndLossPojo pojo = dataList.get(i);
//                    System.out.println("Head Name is-->" + pojo.getAcName() + ":::Type is-->" + pojo.getType() + ":::First Amount is-->"
//                            + pojo.getAmount() + ":::Second Amount is-->" + pojo.getAmountOne() + "\n");
//                }

                for (int i = 0; i < dataList.size(); i++) {
                    ProfitAndLossPojo pojo = dataList.get(i);

                    if (!(pojo.getAmount().compareTo(new BigDecimal(0)) == 0
                            && pojo.getAmountOne().compareTo(new BigDecimal(0)) == 0)) {
                        if (pojo.getType().equalsIgnoreCase("I")) {
                            incomeList.add(pojo);
                        } else if (pojo.getType().equalsIgnoreCase("E")) {
                            expenseList.add(pojo);
                        }
                    }
                }

                jrxmlPojo.setIncome(new JRBeanCollectionDataSource(incomeList));
                jrxmlPojo.setExpense(new JRBeanCollectionDataSource(expenseList));
                jrxmlList.add(jrxmlPojo);
                /*Report Printing*/
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().jasperReport("comperativePl", "text/html",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams, reportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setErrMessage("");
        try {
            if (validateField()) {
                List<ProfitAndLossPojo> dataList = remote.getComperativePLDetails(this.branch, ymd.format(dmy.parse(dateOne)),
                        ymd.format(dmy.parse(dateTwo)), this.option);
                if (dataList.isEmpty()) {
                    this.setErrMessage("There is no data to print.");
                    return;
                }
                String reportName = "Comperative Profit Loss";
                Map fillParams = new HashMap();
                List brNameAndAddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchName", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pPrintedDate", getTodayDate());
                fillParams.put("firstDt", dateOne);
                fillParams.put("secondDt", dateTwo);

                List<ComperativePlJrxmlPojo> jrxmlList = new ArrayList<ComperativePlJrxmlPojo>();     //Actual List To Print
                ComperativePlJrxmlPojo jrxmlPojo = new ComperativePlJrxmlPojo();
                //Creating separate income & expenditure list.
                List<ProfitAndLossPojo> incomeList = new ArrayList<ProfitAndLossPojo>();
                List<ProfitAndLossPojo> expenseList = new ArrayList<ProfitAndLossPojo>();

//                for (int i = 0; i < dataList.size(); i++) {
//                    ProfitAndLossPojo pojo = dataList.get(i);
//                    System.out.println("Head Name is-->" + pojo.getAcName() + ":::Type is-->" + pojo.getType() + ":::First Amount is-->"
//                            + pojo.getAmount() + ":::Second Amount is-->" + pojo.getAmountOne() + "\n");
//                }

                for (int i = 0; i < dataList.size(); i++) {
                    ProfitAndLossPojo pojo = dataList.get(i);

                    if (!(pojo.getAmount().compareTo(new BigDecimal(0)) == 0
                            && pojo.getAmountOne().compareTo(new BigDecimal(0)) == 0)) {
                        if (pojo.getType().equalsIgnoreCase("I")) {
                            incomeList.add(pojo);
                        } else if (pojo.getType().equalsIgnoreCase("E")) {
                            expenseList.add(pojo);
                        }
                    }
                }

                jrxmlPojo.setIncome(new JRBeanCollectionDataSource(incomeList));
                jrxmlPojo.setExpense(new JRBeanCollectionDataSource(expenseList));
                jrxmlList.add(jrxmlPojo);
                /*Report Printing*/
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().openPdf(reportName, "comperativePl", new JRBeanCollectionDataSource(jrxmlList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", reportName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.branch == null || this.branch.equals("")) {
                this.setErrMessage("Please select Branch.");
                return false;
            }
            if (this.dateOne == null || this.dateTwo == null
                    || this.dateOne.equals("") || this.dateTwo.equals("")) {
                this.setErrMessage("Please fill proper Date1 and Date2.");
                return false;
            }
            Validator validator = new Validator();
            if (!validator.validateDate_dd_mm_yyyy(this.dateOne)) {
                this.setErrMessage("Please fill proper Date1.");
                return false;
            }
            if (!validator.validateDate_dd_mm_yyyy(this.dateTwo)) {
                this.setErrMessage("Please fill proper Date2.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
        return true;
    }

    public void refresh() {
        this.setErrMessage("");
        this.setDateOne(getTodayDate());
        this.setDateTwo(getTodayDate());
        this.setOption("0");
    }

    public String btnExitAction() {
        refresh();
        return "case1";
    }
    //Getter & Setter

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDateOne() {
        return dateOne;
    }

    public void setDateOne(String dateOne) {
        this.dateOne = dateOne;
    }

    public String getDateTwo() {
        return dateTwo;
    }

    public void setDateTwo(String dateTwo) {
        this.dateTwo = dateTwo;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }
}
