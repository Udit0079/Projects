/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.TokenBookPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class TokenBook extends BaseBean {

    private String message;
    private String tokenPaidBy;
    private List<SelectItem> tokenPaidByList;
    private String asOnDt;
    private String type;
    private List<SelectItem> typeList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private LedgerReportFacadeRemote ledgerReport;
    List<TokenBookPojo> reportList = new ArrayList<TokenBookPojo>();

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
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

    public String getTokenPaidBy() {
        return tokenPaidBy;
    }

    public void setTokenPaidBy(String tokenPaidBy) {
        this.tokenPaidBy = tokenPaidBy;
    }

    public List<SelectItem> getTokenPaidByList() {
        return tokenPaidByList;
    }

    public void setTokenPaidByList(List<SelectItem> tokenPaidByList) {
        this.tokenPaidByList = tokenPaidByList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public TokenBook() {
        try {
            setAsOnDt(dmy.format(date));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ledgerReport = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("Select", "--Select--"));
            typeList.add(new SelectItem("0", "Receipt"));
            typeList.add(new SelectItem("1", "Payment"));
            
            tokenPaidByList = new ArrayList();
            tokenPaidByList.add(new SelectItem("ALL", "ALL"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getEnterByData() {
        try {
            String asOnDate = ymd.format(dmy.parse(asOnDt));
            List userId = common.getTokenPaidBy(getOrgnBrCode(), asOnDate, type);
            tokenPaidByList = new ArrayList();
            tokenPaidByList.add(new SelectItem("ALL", "ALL"));
            for (Object element : userId) {
                Vector vector = (Vector) element;
                tokenPaidByList.add(new SelectItem(vector.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        this.setMessage("");
        String report = "Token Book Report";
        String tPaid = "Token Paid By";
        String tReceipt = "Token Receipt By";

        String branchName = "", address = "";
        try {
            if (asOnDt == null || asOnDt.equalsIgnoreCase("")) {
                setMessage("Please fill As On Date!!!");
                return;
            }
            if (!Validator.validateDate(asOnDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (type.equalsIgnoreCase("Select")) {
                setMessage("Please Select Type!!!");
                return;
            }
            List bnkAddList = new ArrayList();
            bnkAddList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!bnkAddList.isEmpty()) {
                branchName = (String) bnkAddList.get(0);
                address = (String) bnkAddList.get(1);
            }
            Map fillParams = new HashMap();
            if (type.equalsIgnoreCase("0")) {
                fillParams.put("pTPaidReceiv", tReceipt);
            } else {
                fillParams.put("pTPaidReceiv", tPaid);
            }
            fillParams.put("pTPaidRcvVal", this.tokenPaidBy);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDate", this.asOnDt);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", getUserName());
            String asOnDate = ymd.format(dmy.parse(asOnDt));

            reportList = ledgerReport.getTokenBookData(tokenPaidBy, asOnDate, type, getOrgnBrCode());
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }
            if (type.equalsIgnoreCase("0")) {
                new ReportBean().jasperReport("TokenBook", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Token Book Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().jasperReport("TokenBook", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Token Book Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        setAsOnDt(dmy.format(date));
        setType("Select");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
