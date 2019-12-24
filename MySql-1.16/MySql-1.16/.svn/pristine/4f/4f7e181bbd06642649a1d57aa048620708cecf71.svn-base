/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.FinalBalanceReportPojo;
import com.cbs.dto.report.SortedByFinalBalance;
import com.cbs.dto.report.SortedFinalBalanceReport;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class FinalBalanceReport {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String type;
    private List<SelectItem> typeList;
    String orgnBrCode;
    HttpServletRequest req;
    String message;
    String stxtDate;
    String stxtUser;
    String ddStatus;
    private String exCounter;
    private String displayExCtr;
    private List<SelectItem> exCounterList;
    List<SelectItem> ddStatusList = new ArrayList<SelectItem>();
    String ddAcType;
    List<SelectItem> ddAcTypeList = new ArrayList<SelectItem>();
    String txtFromAcno;
    String txtToAcno;
    String lblFromAc;
    String lblToAc;
    String lblAcType;
    Date calDate;
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
    List<FinalBalanceReportPojo> finalBalanceDataList = new ArrayList<FinalBalanceReportPojo>();

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

    public String getDisplayAgCode() {
        return displayAgCode;
    }

    public void setDisplayAgCode(String displayAgCode) {
        this.displayAgCode = displayAgCode;
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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getDdAcType() {
        return ddAcType;
    }

    public void setDdAcType(String ddAcType) {
        this.ddAcType = ddAcType;
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

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getStxtDate() {
        return stxtDate;
    }

    public void setStxtDate(String stxtDate) {
        this.stxtDate = stxtDate;
    }

    public String getStxtUser() {
        return stxtUser;
    }

    public void setStxtUser(String stxtUser) {
        this.stxtUser = stxtUser;
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

    public String getDdAcTypeDisableFlag() {
        return ddAcTypeDisableFlag;
    }

    public void setDdAcTypeDisableFlag(String ddAcTypeDisableFlag) {
        this.ddAcTypeDisableFlag = ddAcTypeDisableFlag;
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

    public String getExCounter() {
        return exCounter;
    }

    public void setExCounter(String exCounter) {
        this.exCounter = exCounter;
    }

    public String getDisplayExCtr() {
        return displayExCtr;
    }

    public void setDisplayExCtr(String displayExCtr) {
        this.displayExCtr = displayExCtr;
    }

    public List<SelectItem> getExCounterList() {
        return exCounterList;
    }

    public void setExCounterList(List<SelectItem> exCounterList) {
        this.exCounterList = exCounterList;
    }
    

    public FinalBalanceReport() {
        req = getRequest();
        try {
            acTypeMap = new HashMap<String, String>();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setStxtUser(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setStxtDate(sdf.format(date));
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

            List acCodeList = commonBeanRemote.getAccTypeExcludePO();
            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("0", "--Select--"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                ddAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
                acTypeMap.put(vector.get(0).toString(), vector.get(1).toString());
            }
            this.setAgentCode("0");
            agentCodeList = new ArrayList<SelectItem>();
            agentCodeList.add(new SelectItem("0", "All"));
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "--Select--"));
            typeList.add(new SelectItem("1", "ALL"));
            typeList.add(new SelectItem("2", "Individual"));
            typeList.add(new SelectItem("3", "Society"));
            exCounterList = new ArrayList<>();
            exCounterList.add(new SelectItem("N", "No"));
            exCounterList.add(new SelectItem("Y", "Yes"));
            setExCounter("N");
            setDisplayExCtr("none");
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
    
    public void checkExtCounter(){
        try {
            setMessage("");
            if(!branchCode.equals("0A") && commonBeanRemote.isExCounterExit(branchCode)){
                setDisplayExCtr("");
            }
            else{
                setDisplayExCtr("none");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
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
                    agentCodeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
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

    public void btnHtmlAction() {
        setMessage("");
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
        if (txtFromAcno.equals("")) {
            setMessage("Please Enter From Account No.");
            return;
        }
        if (txtToAcno.equals("")) {
            setMessage("Please Enter To Account No.");
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
        if (calDate == null) {
            setMessage("Please Enter As On Date.");
            return;
        }

        if (calDate.after(new Date())) {
            setMessage("As On Date less than current date !");
            return;
        }
        if (type == null || type.equalsIgnoreCase("0")) {
            setMessage("Please Select Report Type!!!");
            return;
        }

        int valfromAc = Integer.valueOf(this.txtFromAcno);
        int valtoAc = Integer.valueOf(this.txtToAcno);

        Date date = this.calDate;
        String date2 = sdf1.format(date);

        String user = this.stxtUser;
        String rpName = "FINAL BALANCE REPORT";
        String acTpe = "A/c Type: " + this.ddAcType + " (" + this.getLblAcType() + ")" + ",\n" + "Status: " + this.ddStatus;
        String agentDetails = "";
        if (agentCode.equalsIgnoreCase("0")) {
            agentDetails = "";
        } else {
            try {
                agentDetails = commonBeanRemote.getAgentName(agentCode, branchCode);
            } catch (ApplicationException ex) {
                this.setMessage(ex.getMessage());
            }
            agentDetails = "Agent Name: " + agentDetails + "(" + agentCode + ")";
            acTpe = acTpe + ",\n" + agentDetails;
        }
        Map fillParams = new HashMap();
        try {
            fillParams.put("pAcctType", acTpe);
            fillParams.put("pReportName", rpName);
            fillParams.put("pPrintedDate", date2);
            fillParams.put("pPrintedBy", user);
            fillParams.put("pMessage", "From A/c:" + valfromAc + " to:" + valtoAc);

            finalBalanceDataList = glBeanRemote.getFinalBalanceReport(ddStatus, ddAcType, txtFromAcno, txtToAcno, ymd.format(calDate), branchCode, agentCode, type,this.exCounter);
            if(finalBalanceDataList.isEmpty()){
                this.setMessage("data does not exits.");
            }
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByFinalBalance());
            chObj.addComparator(new SortedFinalBalanceReport());
            Collections.sort(finalBalanceDataList, chObj);

            new ReportBean().jasperReport("final_balance_report", "text/html",
                    new JRBeanCollectionDataSource(finalBalanceDataList), fillParams, "Final Balance Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", rpName);

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
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
        if (txtFromAcno.equals("")) {
            setMessage("Please Enter From Account No.");
            return;
        }
        if (txtToAcno.equals("")) {
            setMessage("Please Enter To Account No.");
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
        if (calDate == null) {
            setMessage("Please Enter As On Date.");
            return;
        }

        if (calDate.after(new Date())) {
            setMessage("As On Date less than current date");
            return;
        }

        if (type == null || type.equalsIgnoreCase("0")) {
            setMessage("Please Select Report Type!!!");
            return;
        }
        int valfromAc = Integer.valueOf(this.txtFromAcno);
        int valtoAc = Integer.valueOf(this.txtToAcno);

        Date date = this.calDate;
        String date2 = sdf1.format(date);

        String user = this.stxtUser;
        String rpName = "FINAL BALANCE REPORT";
        String acTpe = "A/c Type: " + this.ddAcType + ", Status: " + this.ddStatus;
        String agentDetails = "";
        if (agentCode.equalsIgnoreCase("0")) {
            agentDetails = "";
        } else {
            try {
                agentDetails = commonBeanRemote.getAgentName(agentCode, branchCode);
            } catch (ApplicationException ex) {
                this.setMessage(ex.getMessage());
            }
            agentDetails = "Agent Name: " + agentDetails + "(" + agentCode + ")";
            acTpe = acTpe + ",\n" + agentDetails;
        }
        Map fillParams = new HashMap();
        try {
            fillParams.put("pAcctType", acTpe);
            fillParams.put("pReportName", rpName);
            fillParams.put("pPrintedDate", date2);
            fillParams.put("pPrintedBy", user);
            fillParams.put("pMessage", "From A/c:" + valfromAc + " to:" + valtoAc);

            finalBalanceDataList = glBeanRemote.getFinalBalanceReport(ddStatus, ddAcType, txtFromAcno, txtToAcno, ymd.format(calDate), branchCode, agentCode, type,this.exCounter);
            if(finalBalanceDataList.isEmpty()){
                this.setMessage("data does not exits.");
            }
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByFinalBalance());
            chObj.addComparator(new SortedFinalBalanceReport());
            Collections.sort(finalBalanceDataList, chObj);

            new ReportBean().openPdf("final_balance_report_", "final_balance_report", new JRBeanCollectionDataSource(finalBalanceDataList), fillParams);
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
        this.setCalDate(null);
        this.setDdAcType("0");
        this.setDdStatus("0");
        this.setLblAcType("");
        this.setTxtFromAcno("");
        this.setTxtToAcno("");
        this.setLblFromAc("");
        this.setLblToAc("");
        this.setDisplayExCtr("none");
    }
}
