/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.dds;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AgentCollectionPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author saurabhsipl
 */
public class AgentCollection extends BaseBean{
    private String branchCode;
    private List<SelectItem> branchCodeList;
    String orgnBrCode;
    HttpServletRequest req;
    String message;
    String date;
    String user;
    String ddStatus;
    List<SelectItem> ddStatusList = new ArrayList<SelectItem>();
    String ddAcType;
    List<SelectItem> ddAcTypeList = new ArrayList<SelectItem>();
    String txtFromAcno;
    String txtToAcno;
    String lblFromAc;
    String lblToAc;
    String lblAcType;
    Date toDate;
    Date fromDate;
    String agentCode;
    Map<String, String> acTypeMap;
    List<SelectItem> agentCodeList = new ArrayList<SelectItem>();
    String ddAcTypeDisableFlag;
    String displayAgCode;
    private final String jndiHomeName = "GLReportFacade";
    private GLReportFacadeRemote glBeanRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonBeanRemote = null;
    private DDSReportFacadeRemote hofacade;
    List<AgentCollectionPojo> finalBalanceDataList = new ArrayList<AgentCollectionPojo>();
    
    public AgentCollection() {
        req = getRequest();
        try {
            acTypeMap = new HashMap<String, String>();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUser(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setDate(sdf.format(date));
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ddStatusList = new ArrayList<SelectItem>();
            ddStatusList.add(new SelectItem("0", "--Select--"));
            ddStatusList.add(new SelectItem("ALL", "ALL"));
            ddStatusList.add(new SelectItem("OPERATIVE", "OPERATIVE"));
            ddStatusList.add(new SelectItem("INOPERATIVE", "INOPERATIVE"));
            ddStatusList.add(new SelectItem("PROTESTEDBILL", "PROTESTED BILL"));
            ddStatusList.add(new SelectItem("SUITFILED", "SUIT FILED"));

            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            hofacade = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            List brncode = new ArrayList();
            brncode = commonBeanRemote.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            List acCodeList = commonBeanRemote.getAcctTypeForDs();
            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("0", "--Select--"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                ddAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
                acTypeMap.put(vector.get(0).toString(), "["+vector.get(0).toString()+"] " + vector.get(1).toString());
            }
            this.setAgentCode("0");
            agentCodeList = new ArrayList<SelectItem>();
            agentCodeList.add(new SelectItem("0", "All"));
            this.setDisplayAgCode("none");
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
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
    
    
    public void ddStatusProcessValueChange() {
        if ((this.ddStatus.equals("PROTESTEDBILL"))) {
            this.setDdAcTypeDisableFlag("true");
            this.setLblAcType("PROTESTED BILL");
        } else if ((this.ddStatus.equals("SUITFILED"))) {
            this.setDdAcTypeDisableFlag("true");
            this.setLblAcType("SUIT FILED");
        } else {
            this.setDdAcTypeDisableFlag("false");
        }
    }

    public void txtToAcnoProcessValueChange() {
        try {
            if (ddStatus == null || ddStatus.equals("0")) {
                setMessage("Please Select Status.");
                return;
            }
            if (ddAcType == null || ddAcType.equals("0")) {
                setMessage("Please Select Account Type.");
                return;
            }
            if (txtFromAcno == null || txtFromAcno.equals("")) {
                setMessage("Please Enter From Account No.");
                return;
            } else {
                if (!txtFromAcno.matches("[0-9]*")) {
                    setMessage("Please Enter Valid From Account No.");
                    return;
                }
            }
            if (txtToAcno == null || txtToAcno.equals("")) {
                setMessage("Please Enter To Account No.");
                return;
            } else {
                if (!txtToAcno.matches("[0-9]*")) {
                    setMessage("Please Enter Valid To Account No.");
                    return;
                }
            }
            String agCode = "01";
            String acNature = commonBeanRemote.getAcctNature(this.ddAcType);
            if (acNature.equals(CbsConstant.DEPOSIT_SC) && !this.getAgentCode().equals("0")) {
                agCode = this.getAgentCode();
            }
            this.setLblFromAc(orgnBrCode + this.ddAcType + CbsUtil.lPadding(6, Integer.parseInt(this.txtFromAcno)) + agCode);
            this.setLblToAc(orgnBrCode + this.ddAcType + CbsUtil.lPadding(6, Integer.parseInt(this.txtToAcno)) + agCode);
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        }
    }

    public void ddAcTypeProcessValueChange() {
        try {
            List agCodeList = new ArrayList();
            String acNature = commonBeanRemote.getAcctNature(this.ddAcType);
            this.setLblAcType(acTypeMap.get(this.getDdAcType()));
            if (acNature.equals(CbsConstant.DEPOSIT_SC)) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    agCodeList = hofacade.getAgentCodeHo();
                } else {
                    agCodeList = commonBeanRemote.getAgentCode(branchCode);
                }
                //  List agCodeList = commonBeanRemote.getAgentCode(orgnBrCode);
                agentCodeList = new ArrayList<SelectItem>();
                agentCodeList.add(new SelectItem("0", "All"));
                for (Object element : agCodeList) {
                    Vector vector = (Vector) element;
                    agentCodeList.add(new SelectItem(vector.get(0).toString(), "("+vector.get(0).toString()+")"+vector.get(1).toString()));
                }
                this.setDisplayAgCode("block");
            } else {
                this.setDisplayAgCode("none");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

     public void pdfDownLoad() {
        setMessage("");
        String branchName = "", address = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        if (ddStatus == null || ddStatus.equals("0")) {
            setMessage("Please Select Status.");
            return;
        }
        if (ddAcType == null || ddAcType.equals("0")) {
            setMessage("Please Select Account Type.");
            return;
        }       
        if (txtFromAcno == null || txtFromAcno.equals("")) {
            setMessage("Please Enter From Account No.");
            return;
        } else {
            if (!txtFromAcno.matches("[0-9]*")) {
                setMessage("Please Enter Valid From Account No.");
                return;
            }
        }
        if (txtToAcno == null || txtToAcno.equals("")) {
            setMessage("Please Enter To Account No.");
            return;
        } else {
            if (!txtToAcno.matches("[0-9]*")) {
                setMessage("Please Enter Valid To Account No.");
                return;
            }
        }
        if (toDate == null) {
            setMessage("Please Enter To Date.");
            return;
        }
        if(fromDate == null){
            setMessage("Please Enter From Date.");
        }

        int valfromAc = Integer.valueOf(this.txtFromAcno);
        int valtoAc = Integer.valueOf(this.txtToAcno);

        Date date = this.toDate;
        String date2 = sdf1.format(date);

        String user = this.user;
        String rpName = "Agent Wise Collection Sheet";
        String acTpe = "A/c Type: " + this.ddAcType + ", Status: " + this.ddStatus;
        String agentDetails = "";               
        Map fillParams = new HashMap();
        try {
            if (agentCode.equalsIgnoreCase("0")) {
                agentDetails = "";
            } else {            
                agentDetails = commonBeanRemote.getAgentName(agentCode,branchCode);            
                agentDetails = "Agent Name: " + agentDetails + "(" + agentCode + ")";
                acTpe = acTpe + ",\n" + agentDetails;
            } 
            List brNameAdd = new ArrayList();
            brNameAdd = commonBeanRemote.getBranchNameandAddress(getBranchCode().equalsIgnoreCase("0A")?"90":branchCode);
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAdd", address);
            fillParams.put("pAcctType", acTpe);
            fillParams.put("pReportName", rpName);
            fillParams.put("pPrintedDate", sdf1.format(this.fromDate).concat(" To ").concat(date2));
            fillParams.put("pPrintedBy", user);
            fillParams.put("pMessage", "From A/c:" + valfromAc + " to:" + valtoAc);

            finalBalanceDataList = glBeanRemote.getAgentCollectionReport(ddStatus, ddAcType, txtFromAcno, txtToAcno, ymd.format(fromDate), ymd.format(toDate), branchCode, agentCode);
            if(finalBalanceDataList.isEmpty()){
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().openPdf("Agent_Wise_Collection_Sheet"+agentCode , "Agent_Collection_sheet", new JRBeanCollectionDataSource(finalBalanceDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", rpName);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        this.setMessage("");
        this.setToDate(null);
        this.setFromDate(null);
        this.setDdAcType("0");
        this.setDdStatus("0");
        this.setLblAcType("");
        this.setTxtFromAcno("");
        this.setTxtToAcno("");
        this.setLblFromAc("");
        this.setLblToAc("");
    }

    public Map<String, String> getAcTypeMap() {
        return acTypeMap;
    }

    public void setAcTypeMap(Map<String, String> acTypeMap) {
        this.acTypeMap = acTypeMap;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    } 

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDdAcType() {
        return ddAcType;
    }

    public void setDdAcType(String ddAcType) {
        this.ddAcType = ddAcType;
    }

    public String getDdAcTypeDisableFlag() {
        return ddAcTypeDisableFlag;
    }

    public void setDdAcTypeDisableFlag(String ddAcTypeDisableFlag) {
        this.ddAcTypeDisableFlag = ddAcTypeDisableFlag;
    }

    public List<SelectItem> getDdAcTypeList() {
        return ddAcTypeList;
    }

    public void setDdAcTypeList(List<SelectItem> ddAcTypeList) {
        this.ddAcTypeList = ddAcTypeList;
    }

    public String getDdStatus() {
        return ddStatus;
    }

    public void setDdStatus(String ddStatus) {
        this.ddStatus = ddStatus;
    }

    public List<SelectItem> getDdStatusList() {
        return ddStatusList;
    }

    public void setDdStatusList(List<SelectItem> ddStatusList) {
        this.ddStatusList = ddStatusList;
    }

    public String getDisplayAgCode() {
        return displayAgCode;
    }

    public void setDisplayAgCode(String displayAgCode) {
        this.displayAgCode = displayAgCode;
    }

    public String getLblAcType() {
        return lblAcType;
    }

    public void setLblAcType(String lblAcType) {
        this.lblAcType = lblAcType;
    }

    public String getLblFromAc() {
        return lblFromAc;
    }

    public void setLblFromAc(String lblFromAc) {
        this.lblFromAc = lblFromAc;
    }

    public String getLblToAc() {
        return lblToAc;
    }

    public void setLblToAc(String lblToAc) {
        this.lblToAc = lblToAc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTxtFromAcno() {
        return txtFromAcno;
    }

    public void setTxtFromAcno(String txtFromAcno) {
        this.txtFromAcno = txtFromAcno;
    }

    public String getTxtToAcno() {
        return txtToAcno;
    }

    public void setTxtToAcno(String txtToAcno) {
        this.txtToAcno = txtToAcno;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }   
        
}
