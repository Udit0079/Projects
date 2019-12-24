/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareHoldersPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ShareMemberExpire extends BaseBean {
    
    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private int retireAge;
    private int retirePeriod;
    private String asOndate;
    private Date dt = new Date();
    private CommonReportMethodsRemote common = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private ShareReportFacadeRemote srfRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    List<ShareHoldersPojo> resultList = new ArrayList<ShareHoldersPojo>();
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    
    public ShareMemberExpire() {
        
        try {
            setAsOndate(dmyFormat.format(dt));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            srfRemote = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            branchList = new ArrayList<SelectItem>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
            setRetireAge(ftsRemote.getCodeForReportName("SR-CITIZEN-AGE"));
            
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void PrintViwe() {
        setMessage("");
        String branchName = "";
        String address = "";
        try {
            String report = "Member to be Retire Report";
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", asOndate);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pPrintedBy", this.getUserName());
            resultList = srfRemote.getRetireShareMemberData(branch, retireAge, retirePeriod, ymdFormat.format(dmyFormat.parse(asOndate)));
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            new ReportBean().jasperReport("RetireShareMember", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", report);
            
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void pdfDownLoad() {
        setMessage("");
        String branchName = "";
        String address = "";
        try {
            String report = "Member to be Retire Report";
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", asOndate);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pPrintedBy", this.getUserName());
            resultList = srfRemote.getRetireShareMemberData(branch, retireAge, retirePeriod, ymdFormat.format(dmyFormat.parse(asOndate)));
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            new ReportBean().openPdf("Member to be Retire Report", "RetireShareMember", new JRBeanCollectionDataSource(resultList), fillParams);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", report);
            
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void refresh() {
        setMessage("");
        //setRetireAge(0);
        setAsOndate(dmyFormat.format(dt));
        setRetirePeriod(0);
    }
    
    public String closeAction() {
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
    
    public int getRetireAge() {
        return retireAge;
    }
    
    public void setRetireAge(int retireAge) {
        this.retireAge = retireAge;
    }
    
    public int getRetirePeriod() {
        return retirePeriod;
    }
    
    public void setRetirePeriod(int retirePeriod) {
        this.retirePeriod = retirePeriod;
    }
    
    public String getAsOndate() {
        return asOndate;
    }
    
    public void setAsOndate(String asOndate) {
        this.asOndate = asOndate;
    }
    
    public Date getDt() {
        return dt;
    }
    
    public void setDt(Date dt) {
        this.dt = dt;
    }
}
