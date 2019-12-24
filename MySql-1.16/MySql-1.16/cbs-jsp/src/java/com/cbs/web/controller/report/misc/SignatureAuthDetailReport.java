/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AccountEditHistoryPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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

/**
 *
 * @author Admin
 */
public class SignatureAuthDetailReport extends BaseBean {
    
    private String msg;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String selectAcType;
    private List<SelectItem> selectAcTypeList;
    private String optType;
    private List<SelectItem> optTypeLst;
    private String txtFromAcno;
    private String txtToAcno;
    private String lblFromAc;
    private String lblToAc;
    private String frmDt;
    private String toDt;
    boolean disableFrAc;
    boolean disableToAc;
    boolean disableFrDt;
    boolean disableToDt;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote miscFacde;
    String agentCode;
    String displayAgCode;
    String focusId;
    List<SelectItem> agentCodeList = new ArrayList<SelectItem>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAcnoNature() {
        return acnoNature;
    }

    public void setAcnoNature(String acnoNature) {
        this.acnoNature = acnoNature;
    }

    public List<SelectItem> getAcnoNatureList() {
        return acnoNatureList;
    }

    public void setAcnoNatureList(List<SelectItem> acnoNatureList) {
        this.acnoNatureList = acnoNatureList;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public List<SelectItem> getOptTypeLst() {
        return optTypeLst;
    }

    public void setOptTypeLst(List<SelectItem> optTypeLst) {
        this.optTypeLst = optTypeLst;
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

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }    

    public boolean isDisableFrAc() {
        return disableFrAc;
    }

    public void setDisableFrAc(boolean disableFrAc) {
        this.disableFrAc = disableFrAc;
    }

    public boolean isDisableToAc() {
        return disableToAc;
    }

    public void setDisableToAc(boolean disableToAc) {
        this.disableToAc = disableToAc;
    }

    public boolean isDisableFrDt() {
        return disableFrDt;
    }

    public void setDisableFrDt(boolean disableFrDt) {
        this.disableFrDt = disableFrDt;
    }

    public boolean isDisableToDt() {
        return disableToDt;
    }

    public void setDisableToDt(boolean disableToDt) {
        this.disableToDt = disableToDt;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getDisplayAgCode() {
        return displayAgCode;
    }

    public void setDisplayAgCode(String displayAgCode) {
        this.displayAgCode = displayAgCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
    
    public SignatureAuthDetailReport() {
        try {
            miscFacde = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            getacNature();
            optTypeLst = new ArrayList<SelectItem>();
            optTypeLst.add(new SelectItem("A", "A/C RANGE"));
            optTypeLst.add(new SelectItem("D", "DATE RANGE"));
            
            this.setAgentCode("0");
            agentCodeList = new ArrayList<SelectItem>();
            agentCodeList.add(new SelectItem("0", "All"));
            this.setDisplayAgCode("none");
            focusId = "txtFromAcno";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
    
    public void getacNature() {
        try {
            acnoNatureList = new ArrayList<SelectItem>();
            List acNtureList = common.getAllNatureList();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acnoNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            selectAcTypeList = new ArrayList<SelectItem>();
            List acTypeList = common.getAccType(acnoNature);
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    selectAcTypeList.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }
    
    public void ddAcTypeProcessValueChange() {
        try {
            List agCodeList = new ArrayList();
            String acNature = common.getAcctNature(this.selectAcType);
            if (acNature.equals(CbsConstant.DEPOSIT_SC)) {
                agCodeList = common.getAgentCode(getOrgnBrCode());
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
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }

    }
    
    public void changeOnOption(){
        try{
            if(optType.equalsIgnoreCase("A")){
                disableFrAc = false;
                disableToAc = false;
                disableFrDt = true;
                disableToDt = true;
                setFrmDt("");
                setToDt("");
                setTxtFromAcno("");
                setTxtToAcno("");
                setLblFromAc("");
                setLblToAc("");
                focusId = "txtFromAcno";
            }else{
                disableFrAc = true;
                disableToAc = true;
                disableFrDt = false;
                disableToDt = false;
                setFrmDt("");
                setToDt("");
                setTxtFromAcno("");
                setTxtToAcno("");
                setLblFromAc("");
                setLblToAc("");
                focusId = "calfrm";
            }
            if (acnoNature.equals(CbsConstant.DEPOSIT_SC)) {
                focusId = "ddAgent";
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }
    
    public void txtToAcnoProcessValueChange() {
        try {
            if (txtFromAcno == null || txtFromAcno.equals("")) {
                setMsg("Please Enter From Account No.");
                return;
            } else {
                if (!txtFromAcno.matches("[0-9]*")) {
                    setMsg("Please Enter Valid From Account No.");
                    return;
                }
            }
            if (txtToAcno == null || txtToAcno.equals("")) {
                setMsg("Please Enter To Account No.");
                return;
            } else {
                if (!txtToAcno.matches("[0-9]*")) {
                    setMsg("Please Enter Valid To Account No.");
                    return;
                }
            }
            
            if(Integer.parseInt(txtFromAcno) > Integer.parseInt(txtToAcno)){
                setMsg("From A/c no. cant't be greater than to a/c no.");
                return;
            }
            
            String agCode = "01";
            String acNature = common.getAcctNature(this.selectAcType);
            if (acNature.equals(CbsConstant.DEPOSIT_SC) && !this.getAgentCode().equals("0")) {
                agCode = this.getAgentCode();
            }
            this.setLblFromAc(getOrgnBrCode() + this.selectAcType + CbsUtil.lPadding(6, Integer.parseInt(this.txtFromAcno)) + agCode);
            this.setLblToAc(getOrgnBrCode() + this.selectAcType + CbsUtil.lPadding(6, Integer.parseInt(this.txtToAcno)) + agCode);
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }
    
    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        this.setMsg("");
        selectAcTypeList = new ArrayList<SelectItem>();
        this.setOptType("A");
        disableFrAc = false;
        disableToAc = false;
        disableFrDt = true;
        disableToDt = true;
        setFrmDt("");
        setToDt("");
        setTxtFromAcno("");
        setTxtToAcno("");
        setLblFromAc("");
        setLblToAc("");
        this.setDisplayAgCode("none");
        agentCodeList = new ArrayList<SelectItem>();
    }
    
    public void viewReport() {
        try {
            String fDt = "01/01/1900";
            String tDt = "01/01/1900";
            setMsg("");
            if (acnoNature == null || acnoNature.equals("0")) {
                setMsg("Please Select A/c Nature.");
                return;
            }
            
            if (selectAcType == null || selectAcType.equals("0")) {
                setMsg("Please Select Account Type.");
                return;
            }
        
            if (optType == null || optType.equals("0")) {
                setMsg("Please Select Option.");
                return;
            }
        
            if(optType.equalsIgnoreCase("A")){
                if (txtFromAcno == null || txtFromAcno.equals("") || lblFromAc.equalsIgnoreCase("")) {
                    setMsg("Please Enter From Account No.");
                    return;
                } else {
                    if (!txtFromAcno.matches("[0-9]*")) {
                        setMsg("Please Enter Valid From Account No.");
                        return;
                    }
                }
                
                if (txtToAcno == null || txtToAcno.equals("") || lblToAc.equalsIgnoreCase("")) {
                    setMsg("Please Enter To Account No.");
                    return;
                } else {
                    if (!txtToAcno.matches("[0-9]*")) {
                        setMsg("Please Enter Valid To Account No.");
                        return;
                    }
                }
                
                if(Integer.parseInt(txtFromAcno) > Integer.parseInt(txtToAcno)){
                    setMsg("From A/c no. cant't be greater than to a/c no.");
                    return;
                }
            }
            
            if(optType.equalsIgnoreCase("D")){
                fDt = frmDt;
                tDt = toDt;
                if (this.frmDt == null || this.toDt == null
                        || this.frmDt.equals("") || this.toDt.equals("")) {
                    this.setMsg("Please fill proper From Date and To Date.");
                    return;
                }
                
                Validator validator = new Validator();
                if (!validator.validateDate_dd_mm_yyyy(this.frmDt)) {
                    this.setMsg("Please fill proper From Date.");
                    return;
                }
                
                if (!validator.validateDate_dd_mm_yyyy(this.toDt)) {
                    this.setMsg("Please fill proper To Date.");
                    return;
                }
                if (dmy.parse(this.frmDt).compareTo(dmy.parse(this.toDt)) > 0) {
                    this.setMsg("From Date can not be greater than To Date.");
                    return;
                }
            }
            
            String rMessage ="";
            if(optType.equalsIgnoreCase("A")){
                rMessage = "From A/c         : " + lblFromAc + " to " + lblToAc;
            }else{
                rMessage = "From Date        : " + frmDt + " to " + toDt;
            }
            
            String rpName = "SIGNATURE AUTH DETAIL REPORT";
            String acTpe = "A/c Type         : " + this.selectAcType;
            String agentDetails = "";
            if ((agentCode == null) || agentCode.equalsIgnoreCase("0")) {
                agentDetails = "";
            } else {
                try {
                    agentDetails = common.getAgentName(agentCode,getOrgnBrCode());
                } catch (ApplicationException ex) {
                    this.setMsg(ex.getMessage());
                }
                agentDetails = "Agent Name: " + agentDetails + "(" + agentCode + ")";
                acTpe = acTpe + ",\n" + agentDetails;
            }
            
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            
            Map fillParams = new HashMap();
            fillParams.put("pAcctType", acTpe);
            fillParams.put("pReportName", rpName);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pMessage", rMessage);
            fillParams.put("pbankName", brNameAndAddList.get(0).toString());
            fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());
            
            List<AccountEditHistoryPojo> reportList = miscFacde.getSignatureAuthDetail(acnoNature, selectAcType, optType, lblFromAc,lblToAc,
                    ymd.format(dmy.parse(fDt)), ymd.format(dmy.parse(tDt)),getOrgnBrCode());
            
            if (!reportList.isEmpty()) {
                new ReportBean().jasperReport("SignatureAuthDetail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "SIGNATURE AUTH DETAIL REPORT");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", rpName);
            } else{
                this.setMsg("Data Does Not Exist");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }
}
