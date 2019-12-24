/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.TopDepositorBorrowerPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.TopBorrowerGroupIDPoJo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class TopDepositorsAndBorrower extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String bnkBranch;
    private List<SelectItem> bnkBranchList;
    private String optionType;
    private List<SelectItem> optionTypeList;
    private String frDt;
    private String toDt;
    private Date date = new Date();
    private boolean frDtDisable;
    private Integer nos;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote commonBeanRemote;
    private MiscReportFacadeRemote remoteFacade;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private FtsPostingMgmtFacadeRemote fts;
    List<TopDepositorBorrowerPojo> reportList = new ArrayList<TopDepositorBorrowerPojo>();
    List<TopDepositorBorrowerPojo> resultList = new ArrayList<TopDepositorBorrowerPojo>();
    private String borrowerType;
    public String disableTypePanel;
    private List<SelectItem> borrowerTypeList;
    private BigDecimal from;
    private BigDecimal to;
    private boolean fromToDisable;

    public BigDecimal getFrom() {
        return from;
    }

    public void setFrom(BigDecimal from) {
        this.from = from;
    }

    public BigDecimal getTo() {
        return to;
    }

    public void setTo(BigDecimal to) {
        this.to = to;
    }

    public String getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
    }

    public String getDisableTypePanel() {
        return disableTypePanel;
    }

    public void setDisableTypePanel(String disableTypePanel) {
        this.disableTypePanel = disableTypePanel;
    }

    public List<SelectItem> getBorrowerTypeList() {
        return borrowerTypeList;
    }

    public void setBorrowerTypeList(List<SelectItem> borrowerTypeList) {
        this.borrowerTypeList = borrowerTypeList;
    }

    public String getBnkBranch() {
        return bnkBranch;
    }

    public void setBnkBranch(String bnkBranch) {
        this.bnkBranch = bnkBranch;
    }

    public List<SelectItem> getBnkBranchList() {
        return bnkBranchList;
    }

    public void setBnkBranchList(List<SelectItem> bnkBranchList) {
        this.bnkBranchList = bnkBranchList;
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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNos() {
        return nos;
    }

    public void setNos(Integer nos) {
        this.nos = nos;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public List<SelectItem> getOptionTypeList() {
        return optionTypeList;
    }

    public void setOptionTypeList(List<SelectItem> optionTypeList) {
        this.optionTypeList = optionTypeList;
    }

    public boolean isFrDtDisable() {
        return frDtDisable;
    }

    public void setFrDtDisable(boolean frDtDisable) {
        this.frDtDisable = frDtDisable;
    }

    public boolean isFromToDisable() {
        return fromToDisable;
    }

    public void setFromToDisable(boolean fromToDisable) {
        this.fromToDisable = fromToDisable;
    }

    public TopDepositorsAndBorrower() {
        try {
            frDt = dmy.format(date);
            toDt = dmy.format(date);
            from = new BigDecimal(0.0);
            to = new BigDecimal(0.0);

            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            branchCodeList = new ArrayList<SelectItem>();
            List list = RemoteCode.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchCodeList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }

            optionTypeList = new ArrayList<SelectItem>();
            optionTypeList.add(new SelectItem("select", "--Select--"));
            optionTypeList.add(new SelectItem("Sanction Wise", "Sanction Wise"));
            optionTypeList.add(new SelectItem("Borrower Wise Balance", "Balance Wise Borrower"));
            optionTypeList.add(new SelectItem("Borrower", "Borrowed Amount"));
            optionTypeList.add(new SelectItem("Npa Wise Balance", "Npa Wise Balance"));
            optionTypeList.add(new SelectItem("Depositor", "Depositor"));
            optionTypeList.add(new SelectItem("Deposit Wise Balance", "Deposit Wise Balance"));
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("Customer Id", "Customer Id"));
            repTypeList.add(new SelectItem("Account No", "Account No"));
            repTypeList.add(new SelectItem("Npa", "Npa Acno"));
            borrowerTypeList = new ArrayList<>();
            borrowerTypeList.add(new SelectItem("select", "--Select--"));
            borrowerTypeList.add(new SelectItem("1", "Individual"));
            borrowerTypeList.add(new SelectItem("2", "Group"));
            this.disableTypePanel = "none";

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getDisableOption() {
        if (optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) {
            frDtDisable = true;
        } else {
            frDtDisable = false;
        }
        if (optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Borrower") || optionType.contains("Sanction")) {
            this.disableTypePanel = "";
            borrowerTypeList = new ArrayList<>();
            borrowerTypeList.add(new SelectItem("select", "--Select--"));
            borrowerTypeList.add(new SelectItem("1", "Individual"));
            borrowerTypeList.add(new SelectItem("2", "Group"));
        } else {
            this.disableTypePanel = "none";
        }

        if (optionType.contains("Borrower") || optionType.contains("Sanction")) {
            this.fromToDisable = false;
        } else {
            this.fromToDisable = true;
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public void viewReport() {
        String branchName = "";
        String address = "";
        String report = "", hType = "", hType1 = "", h1Type = "", h2Type = "", h3Type = "", h4Type = "", pOverDue = "";
        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dmy.parse(frDt));
//            int fDate = calendar.get(Calendar.MONTH) + 1;
//            calendar.setTime(dmy.parse(toDt));
//            int tDate = calendar.get(Calendar.MONTH) + 1;
//            if (fDate != tDate) {
//                setMessage("To Month Should Be Same as From Month");
//                return;
//            }

            if (nos == null || nos == 0) {
                setMessage("Please fill the Number !");
                return;
            }

            if (optionType.equalsIgnoreCase("select")) {
                setMessage("Please Select Option Type !");
                return;
            }

            if (repType.equalsIgnoreCase("Npa")) {
                if (!optionType.equalsIgnoreCase("Npa Wise Balance")) {
                    setMessage("Please Select Option Type only Npa Wise Bal !");
                    return;
                }
            }
            if (!(optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise"))) {
                if (frDt == null || frDt.equalsIgnoreCase("")) {
                    setMessage("Please fill the from date!");
                    return;
                }
                if (!Validator.validateDate(frDt)) {
                    setMessage("Please select Proper from date!");
                    return;
                }
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill the To date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper To date!");
                return;
            }

            if (!(optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise"))) {

                if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                    setMessage("Please Form date should be less than To date !");
                    return;
                }
            }
            if (optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Borrower")) {
                if (borrowerType.equals(null) || borrowerType.equalsIgnoreCase("select")) {
                    setMessage("Please select The Borrower Type");
                    return;
                }
            }
//            if(borrowerType.equalsIgnoreCase("select") && (optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Borrower"))) {
//                setMessage("Please select The Borrower Type");
//                return;
//            }

            List brnAddrList = new ArrayList();
            if (getBranchCode().equalsIgnoreCase("0A")) {
                brnAddrList = commonBeanRemote.getBranchNameandAddress("90");
            } else {
                brnAddrList = commonBeanRemote.getBranchNameandAddress(getBranchCode());
            }

            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            if (repType.equalsIgnoreCase("Customer Id")) {
                h1Type = "Customer Id";
                h2Type = "Name of Borrower's";
                h3Type = "Address";
                h4Type = "Pan/Form 60";
                pOverDue = "Over Due Amount";
                if (optionType.equalsIgnoreCase("Borrower")) {
                    report = "Top Borrowed Report";
                    hType1 = " Sanction Amount";
                    hType = "Borrowed Amount";
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    report = "Top Borrower Balance Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    report = "Top Sanction Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    report = "Top Depositors Report ";
                    hType = "Deposit Amount";
                } else {
                    report = "Top Balance Report ";
                    hType = " Balance";
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                report = "Top Npa Report ";
                hType = "Balance";
                h2Type = "Customer Name";
                h3Type = "Present Status";
                h4Type = "Npa date";
            } else {
                h1Type = "Account No.";
                h2Type = "Name of Borrower's";
                h3Type = "Address";
                h4Type = "Pan/Form 60";
                if (optionType.equalsIgnoreCase("Borrower")) {
                    hType = "Borrowed Amount";
                    report = "Top Borrowed Report between " + frDt + " - " + toDt;
                    hType1 = " Sanction Amount";
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    report = "Top Borrower Balance Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    report = "Top Depositors Report ";
                    hType = "Deposit Amount";
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    report = "Top Sanction Report ";
                    hType1 = " Outstanding Amount";
                    hType = " Sanction Amount";
                } else {
                    report = "Top Balance Report ";
                    hType = "Balance";
                }
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            if (optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) {
                fillParams.put("pReportDate", this.toDt);
            } else {
                fillParams.put("pReportDate", this.frDt + " to " + toDt);
            }

            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pTopNo", this.nos);

            if (repType.equalsIgnoreCase("Customer Id")) {
                fillParams.put("pH1Type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
                if (optionType.equalsIgnoreCase("Borrower")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    optionType = "Borrower";
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    optionType = "Borrower Wise Balance";
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else {
                    optionType = optionType.equalsIgnoreCase("Depositor") ? "Depositor" : "Deposit Wise Balance";
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                fillParams.put("pTopType", this.optionType);
                fillParams.put("pHtype", hType);
                fillParams.put("pH1type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
            } else {
                fillParams.put("pH1Type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
                if (optionType.equalsIgnoreCase("Borrower")) {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                } else {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                }
            }

            String frdate = ymd.format(dmy.parse(frDt));
            String todate = ymd.format(dmy.parse(toDt));

            reportList = remoteFacade.getTopDepositorBorrower(branchCode, optionType, frdate, todate, nos, repType, bnkBranch, borrowerType, from.doubleValue(), to.doubleValue());
            if (reportList.isEmpty()) {
                setMessage("Data does not exist");
                return;
            }
            if (optionType.equalsIgnoreCase("Borrower") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise") || optionType.equalsIgnoreCase("Npa Wise Balance")) {
                if (repType.equalsIgnoreCase("Customer Id") && (optionType.equalsIgnoreCase("Borrower") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) && borrowerType.equalsIgnoreCase("2")) {
                    ComparatorChain chObj = new ComparatorChain();
                    chObj.addComparator(new TopBorrowerGroupIDPoJo());
                    Collections.sort(reportList, chObj);
                    new ReportBean().jasperReport("TopBorrowers_Groupwise", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Top Depositors and Borrowers");
                } else {
                    new ReportBean().jasperReport("TopBorrowers", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Top Depositors and Borrowers");
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            } else {
                new ReportBean().jasperReport("TopDepositors", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Top Depositors and Borrowers");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
//            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void printExcelReport() {
        String branchName = "";
        String address = "";
        String report = "", hType = "", hType1 = "", h1Type = "", h2Type = "", h3Type = "", h4Type = "", pOverDue = "";
        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dmy.parse(frDt));
//            int fDate = calendar.get(Calendar.MONTH) + 1;
//            calendar.setTime(dmy.parse(toDt));
//            int tDate = calendar.get(Calendar.MONTH) + 1;
//            if (fDate != tDate) {
//                setMessage("To Month Should Be Same as From Month");
//                return;
//            }

            if (nos == null || nos == 0) {
                setMessage("Please fill the Number !");
                return;
            }

            if (optionType.equalsIgnoreCase("select")) {
                setMessage("Please Select Option Type !");
                return;
            }

            if (repType.equalsIgnoreCase("Npa")) {
                if (!optionType.equalsIgnoreCase("Npa Wise Balance")) {
                    setMessage("Please Select Option Type only Npa Wise Bal !");
                    return;
                }
            }
            if (!(optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise"))) {
                if (frDt == null || frDt.equalsIgnoreCase("")) {
                    setMessage("Please fill the from date!");
                    return;
                }
                if (!Validator.validateDate(frDt)) {
                    setMessage("Please select Proper from date!");
                    return;
                }
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill the To date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper To date!");
                return;
            }

            if (!(optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise"))) {

                if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                    setMessage("Please Form date should be less than To date !");
                    return;
                }
            }
            if (optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Borrower")) {
                if (borrowerType.equals(null) || borrowerType.equalsIgnoreCase("select")) {
                    setMessage("Please select The Borrower Type");
                    return;
                }
            }
//            if(borrowerType.equalsIgnoreCase("select") && (optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Borrower"))) {
//                setMessage("Please select The Borrower Type");
//                return;
//            }

            List brnAddrList = new ArrayList();
            if (getBranchCode().equalsIgnoreCase("0A")) {
                brnAddrList = commonBeanRemote.getBranchNameandAddress("90");
            } else {
                brnAddrList = commonBeanRemote.getBranchNameandAddress(getBranchCode());
            }

            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            if (repType.equalsIgnoreCase("Customer Id")) {
                h1Type = "Customer Id";
                h2Type = "Name of Borrower's";
                h3Type = "Address";
                h4Type = "Pan/Form 60";
                pOverDue = "Over Due Amount";
                if (optionType.equalsIgnoreCase("Borrower")) {
                    report = "Top Borrowed Report";
                    hType1 = " Sanction Amount";
                    hType = "Borrowed Amount";
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    report = "Top Borrower Balance Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    report = "Top Sanction Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else {
                    report = "Top Balance Report ";
                    hType = " Balance";
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                report = "Top Npa Report ";
                hType = "Balance";
                h2Type = "Customer Name";
                h3Type = "Present Status";
                h4Type = "Npa date";
            } else {
                h1Type = "Account No.";
                h2Type = "Name of Borrower's";
                h3Type = "Address";
                h4Type = "Pan/Form 60";
                if (optionType.equalsIgnoreCase("Borrower")) {
                    hType = "Borrowed Amount";
                    report = "Top Borrowed Report";
                    hType1 = " Sanction Amount";
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    report = "Top Borrower Balance Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    report = "Top Depositors Report ";
                    hType = "Deposit Amount";
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    report = "Top Sanction Report ";
                    hType1 = " Outstanding Amount";
                    hType = " Sanction Amount";
                } else {
                    report = "Top Balance Report ";
                    hType = "Balance";
                }
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            if (optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) {
                fillParams.put("pReportDate", this.toDt);
            } else {
                fillParams.put("pReportDate", this.frDt + " to " + toDt);
            }

            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pTopNo", this.nos);

            if (repType.equalsIgnoreCase("Customer Id")) {
                fillParams.put("pH1Type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
                if (optionType.equalsIgnoreCase("Borrower")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    optionType = "Borrower";
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    optionType = "Borrower Wise Balance";
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else {
                    optionType = "Deposit Wise Balance";
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                fillParams.put("pTopType", this.optionType);
                fillParams.put("pHtype", hType);
                fillParams.put("pH1type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
            } else {
                fillParams.put("pH1Type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
                if (optionType.equalsIgnoreCase("Borrower")) {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                } else {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                }
            }

            String frdate = ymd.format(dmy.parse(frDt));
            String todate = ymd.format(dmy.parse(toDt));

            reportList = remoteFacade.getTopDepositorBorrower(branchCode, optionType, frdate, todate, nos, repType, bnkBranch, borrowerType, from.doubleValue(), to.doubleValue());

            if (reportList.isEmpty()) {
                setMessage("Data does not exist");
                return;
            }
            if (optionType.equalsIgnoreCase("Borrower") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise") || optionType.equalsIgnoreCase("Npa Wise Balance")) {
                if (repType.equalsIgnoreCase("Customer Id") && (optionType.equalsIgnoreCase("Borrower") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) && borrowerType.equalsIgnoreCase("2")) {
                    ComparatorChain chObj = new ComparatorChain();
                    chObj.addComparator(new TopBorrowerGroupIDPoJo());
                    Collections.sort(reportList, chObj);
                    new ReportBean().dynamicExcelJasper("TopBorrowers_Groupwise_Excel", "excel",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Top Depositors and Borrowers");
                } else {
                    new ReportBean().dynamicExcelJasper("TopBorrowers_Excel", "excel",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Top Depositors and Borrowers");
                }
//                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            } else {
                new ReportBean().dynamicExcelJasper("TopDepositors_Excel", "excel",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Top Depositors and Borrowers");
//                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        String branchName = "";
        String address = "";
        String report = "", hType = "", hType1 = "", h1Type = "", h2Type = "", h3Type = "", h4Type = "", pOverDue = "";
        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dmy.parse(frDt));
//            int fDate = calendar.get(Calendar.MONTH) + 1;
//            calendar.setTime(dmy.parse(toDt));
//            int tDate = calendar.get(Calendar.MONTH) + 1;
//            if (fDate != tDate) {
//                setMessage("To Month Should Be Same as From Month");
//                return;
//            }

            if (nos == null || nos == 0) {
                setMessage("Please fill the Number !");
                return;
            }

            if (optionType.equalsIgnoreCase("select")) {
                setMessage("Please Select Option Type !");
                return;
            }

            if (repType.equalsIgnoreCase("Npa")) {
                if (!optionType.equalsIgnoreCase("Npa Wise Balance")) {
                    setMessage("Please Select Option Type only Npa Wise Bal !");
                    return;
                }
            }
            if (!(optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise"))) {
                if (frDt == null || frDt.equalsIgnoreCase("")) {
                    setMessage("Please fill the from date!");
                    return;
                }
                if (!Validator.validateDate(frDt)) {
                    setMessage("Please select Proper from date!");
                    return;
                }
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill the To date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper To date!");
                return;
            }

            if (!(optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise"))) {

                if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                    setMessage("Please Form date should be less than To date !");
                    return;
                }
            }
            if (borrowerType.equalsIgnoreCase("select") && (optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Borrower"))) {
                setMessage("Please select The Borrower Type");
                return;
            }

            List brnAddrList = new ArrayList();
            if (getBranchCode().equalsIgnoreCase("0A")) {
                brnAddrList = commonBeanRemote.getBranchNameandAddress("90");
            } else {
                brnAddrList = commonBeanRemote.getBranchNameandAddress(getBranchCode());
            }

            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            Map fillParams = new HashMap();
            String pdfDt = "";
            if (optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) {
                fillParams.put("pReportDate", this.toDt);
                pdfDt = ymd.format(dmy.parse(toDt));
            } else {
                fillParams.put("pReportDate", this.frDt + " to " + toDt);
                pdfDt = ymd.format(dmy.parse(frDt)) + " to " + ymd.format(dmy.parse(toDt));
            }

            if (repType.equalsIgnoreCase("Customer Id")) {
                h1Type = "Customer Id";
                h2Type = "Name of Borrower's";
                h3Type = "Address";
                h4Type = "Pan/Form 60";
                pOverDue = "Over Due Amount";
                if (optionType.equalsIgnoreCase("Borrower")) {
                    report = "Top Borrowed Report";
                    hType1 = " Sanction Amount";
                    hType = "Borrowed Amount";
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    report = "Top Borrower Balance Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    report = "Top Sanction Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else {
                    report = "Top Balance Report ";
                    hType = " Balance";
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                report = "Top Npa Report ";
                hType = "Balance";
                h2Type = "Customer Name";
                h3Type = "Present Status";
                h4Type = "Npa date";
            } else {
                h1Type = "Account No.";
                h2Type = "Name of Borrower's";
                h3Type = "Address";
                h4Type = "Pan/Form 60";
                if (optionType.equalsIgnoreCase("Borrower")) {
                    hType = "Borrowed Amount";
                    report = "Top Borrowed Report";
                    hType1 = " Sanction Amount";
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    report = "Top Borrower Balance Report ";
                    hType1 = " Sanction Amount";
                    hType = " Outstanding Amount";
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    report = "Top Depositors Report ";
                    hType = "Deposit Amount";
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    report = "Top Sanction Report ";
                    hType1 = " Outstanding Amount";
                    hType = " Sanction Amount";
                } else {
                    report = "Top Balance Report ";
                    hType = "Balance";
                }
            }
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            if (optionType.equalsIgnoreCase("Deposit Wise Balance") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) {
                fillParams.put("pReportDate", this.toDt);
            } else {
                fillParams.put("pReportDate", this.frDt + " to " + toDt);
            }

            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pTopNo", this.nos);

            if (repType.equalsIgnoreCase("Customer Id")) {
                fillParams.put("pH1Type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
                if (optionType.equalsIgnoreCase("Borrower")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    optionType = "Borrower";
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Borrower Wise Balance")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    optionType = "Borrower Wise Balance";
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Sanction Wise")) {
                    String reportType = "";
                    if (borrowerType.equalsIgnoreCase("1")) {
                        reportType = " Individual ";
                    } else if (borrowerType.equalsIgnoreCase("2")) {
                        reportType = " Group ";
                    }
                    fillParams.put("pTopType", reportType + optionType + "- Amount Between " + from + " and " + to);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else {
                    optionType = "Deposit Wise Balance";
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                fillParams.put("pTopType", this.optionType);
                fillParams.put("pHtype", hType);
                fillParams.put("pH1type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
            } else {
                fillParams.put("pH1Type", h1Type);
                fillParams.put("pH2type", h2Type);
                fillParams.put("pH3type", h3Type);
                fillParams.put("pH4type", h4Type);
                if (optionType.equalsIgnoreCase("Borrower")) {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                    fillParams.put("pOverDue", pOverDue);
                } else if (optionType.equalsIgnoreCase("Depositor")) {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                } else {
                    fillParams.put("pTopType", this.optionType);
                    fillParams.put("pHtype", hType);
                    fillParams.put("pHtype1", hType1);
                }
            }

            String frdate = ymd.format(dmy.parse(frDt));
            String todate = ymd.format(dmy.parse(toDt));

            reportList = remoteFacade.getTopDepositorBorrower(branchCode, optionType, frdate, todate, nos, repType, bnkBranch, borrowerType, from.doubleValue(), to.doubleValue());
            if (reportList.isEmpty()) {
                setMessage("Data does not exist");
                return;
            }
            if (optionType.equalsIgnoreCase("Borrower") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise") || optionType.equalsIgnoreCase("Npa Wise Balance")) {
                if (repType.equalsIgnoreCase("Customer Id") && (optionType.equalsIgnoreCase("Borrower") || optionType.equalsIgnoreCase("Borrower Wise Balance") || optionType.equalsIgnoreCase("Sanction Wise")) && borrowerType.equalsIgnoreCase("2")) {
                    ComparatorChain chObj = new ComparatorChain();
                    chObj.addComparator(new TopBorrowerGroupIDPoJo());
                    Collections.sort(reportList, chObj);
                    new ReportBean().openPdf(report + "_" + pdfDt, "TopBorrowers_Groupwise", new JRBeanCollectionDataSource(reportList), fillParams);
                } else {
                    new ReportBean().openPdf(report + "_" + pdfDt, "TopBorrowers", new JRBeanCollectionDataSource(reportList), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                new ReportBean().openPdf(report + "_" + pdfDt, "TopDepositors", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
        this.setOptionType("select");
        this.disableTypePanel = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}

class MyComparator implements Comparator<Object> {

    Map map;

    public MyComparator(Map map) {
        this.map = map;
    }

    @Override
    public int compare(Object o1, Object o2) {

        return ((BigDecimal) map.get(o2)).compareTo((BigDecimal) map.get(o1));

    }
}