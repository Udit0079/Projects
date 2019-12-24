package com.cbs.web.controller.report.other;

import com.cbs.dto.report.AccountReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Nishant Srivastava
 */
public class BranchWiseSmt extends BaseBean {

    private boolean disabled;
    private String focusId;
    private String calFromDate;
    private String caltoDate;
    private String codeBr;
    private String acNo, acNoLen;
    private String tranType;
    private List<SelectItem> tranTypeList;
    List<AccountReportPojo> repDataList = new ArrayList<AccountReportPojo>();
    Date dt = new Date();
    private String message;
    private String orderBy;
    private List<SelectItem> orderByList;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private String adviceBrCode;
    private List<SelectItem> adviceBrCodeList;
    private TdReceiptManagementFacadeRemote remoteFacade;

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

    public List<SelectItem> getAdviceBrCodeList() {
        return adviceBrCodeList;
    }

    public void setAdviceBrCodeList(List<SelectItem> adviceBrCodeList) {
        this.adviceBrCodeList = adviceBrCodeList;
    }

    public String getAdviceBrCode() {
        return adviceBrCode;
    }

    public void setAdviceBrCode(String adviceBrCode) {
        this.adviceBrCode = adviceBrCode;
    }
    
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeBr() {
        return codeBr;
    }

    public void setCodeBr(String codeBr) {
        this.codeBr = codeBr;
    }
    
    
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public BranchWiseSmt() {
        try {
            this.setCalFromDate(getTodayDate());
            this.setCaltoDate(getTodayDate());
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            remoteFacade = (TdReceiptManagementFacadeRemote)ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            
            this.setAcNoLen(getAcNoLength());
            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("A", "Advice Branch Code"));
            orderByList.add(new SelectItem("D", "Date Wise"));
            
            tranTypeList = new ArrayList<SelectItem>();
            tranTypeList.add(new SelectItem("ALL","ALL"));
            tranTypeList.add(new SelectItem("AT","Auto"));
            tranTypeList.add(new SelectItem("ML","Manual"));
            
            List brncode = remoteFacade.getBranchCodeList(this.getOrgnBrCode());
            adviceBrCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    adviceBrCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }    
            setMessage("");
            setFocusId("txtAccountNo");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateGlHead() {
        try {
            setMessage("");
            if (this.codeBr.equalsIgnoreCase("")) {
                setMessage("Please fill 12 digit Gl head");
                return;
            }
            //if (this.codeBr.length() != 12) {
            if (!this.codeBr.equalsIgnoreCase("") && ((this.codeBr.length() != 12) && (this.codeBr.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please fill proper Gl head");
                return;
            }
            setAcNo(ftsRemote.getNewAccountNumber(codeBr));
            String alphaCode = common.getAlphacodeByBrncode(getAcNo().substring(0,2));
            if(alphaCode.equalsIgnoreCase("HO")){
                setDisabled(false);
                setFocusId("branch");
            }else{
                setAdviceBrCode("0A");
                setDisabled(true);
                setFocusId("ddOrderBy");
            }
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        }
    }

    public void PrintViwe() {
        setMessage("");
        try {
            if (this.codeBr.equalsIgnoreCase("")) {
                setMessage("Please fill proper Gl head");
                return;
            }
            //if (this.codeBr.length() != 12) {
            if (!this.codeBr.equalsIgnoreCase("") && ((this.codeBr.length() != 12) && (this.codeBr.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please fill proper Gl head");
                return;
            }
            if (this.calFromDate == null) {
                setMessage("Please Fill From Date.");
                return;
            }
            if (this.caltoDate == null) {
                setMessage("Please Fill To Date.");
                return;
            }
            if (ymdFormat.parse(this.calFromDate).after(ymdFormat.parse(this.caltoDate))) {
                setMessage("From Date is less than To Date");
                return;
            }
            if (ymdFormat.parse(this.caltoDate).after(ymdFormat.parse(getTodayDate()))) {
                setMessage("To Date is less than Current Date");
                return;
            }
            Map fillParams = new HashMap();

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String bankName = "";
            String bankAddress = "";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            String report = "Branch Wise Account Statement";
            fillParams.put("pReportFrDate", this.calFromDate);
            fillParams.put("pReportToDate", this.caltoDate);
            fillParams.put("SysDate", ymdFormat.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);
            fillParams.put("repType", "");

            String dd = this.calFromDate.substring(0, 2);
            String mm = this.calFromDate.substring(3, 5);
            String yy = this.calFromDate.substring(6, 10);
            String frmDt = yy + mm + dd;
            String dd1 = this.caltoDate.substring(0, 2);
            String mm1 = this.caltoDate.substring(3, 5);
            String yy1 = this.caltoDate.substring(6, 10);
            String toDt = yy1 + mm1 + dd1;

            repDataList = otherFacade.getPrintAction(acNo, frmDt, toDt, orderBy, adviceBrCode,tranType);
            new ReportBean().jasperReport("BranchReport", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "Branch Wise Statement");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewPdf() {
        setMessage("");
        try {
            if (this.codeBr.equalsIgnoreCase("")) {
                setMessage("Please fill proper Gl head");
                return;
            }
            //if (this.codeBr.length() != 12) {
            if (!this.codeBr.equalsIgnoreCase("") && ((this.codeBr.length() != 12) && (this.codeBr.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please fill proper Gl head");
                return;
            }
            if (this.calFromDate == null) {
                setMessage("Please Fill From Date.");
                return;
            }
            if (this.caltoDate == null) {
                setMessage("Please Fill To Date.");
                return;
            }
            if (ymdFormat.parse(this.calFromDate).after(ymdFormat.parse(this.caltoDate))) {
                setMessage("From Date is less than To Date");
                return;
            }
            if (ymdFormat.parse(this.caltoDate).after(ymdFormat.parse(getTodayDate()))) {
                setMessage("To Date is less than Current Date");
                return;
            }
            Map fillParams = new HashMap();

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String bankName = "";
            String bankAddress = "";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            String report = "Branch Wise Account Statement";
            fillParams.put("pReportFrDate", this.calFromDate);
            fillParams.put("pReportToDate", this.caltoDate);
            fillParams.put("SysDate", ymdFormat.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);
            fillParams.put("repType", "");

            String dd = this.calFromDate.substring(0, 2);
            String mm = this.calFromDate.substring(3, 5);
            String yy = this.calFromDate.substring(6, 10);
            String frmDt = yy + mm + dd;
            String dd1 = this.caltoDate.substring(0, 2);
            String mm1 = this.caltoDate.substring(3, 5);
            String yy1 = this.caltoDate.substring(6, 10);
            String toDt = yy1 + mm1 + dd1;

            repDataList = otherFacade.getPrintAction(acNo, frmDt, toDt, orderBy,adviceBrCode,tranType);

            new ReportBean().downloadPdf("BranchReport_" + ymd.format(ymdFormat.parse(caltoDate)), "BranchReport", new JRBeanCollectionDataSource(repDataList), fillParams);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        setCodeBr("");
        setAcNo("");
        setCalFromDate(getTodayDate());
        setCaltoDate(getTodayDate());
        setMessage("");
    }

    public String closeAction() {
        return "case1";
    }
}