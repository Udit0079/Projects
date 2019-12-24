/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.DueDatePojo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DueReport extends BaseBean {

    private String message;
    private String reportType;
    private String matDescOption;
    private String fromDt;
    private String toDt;
    private String fieldDisplay = "none";
    private String frmInputDisplay = "none";
    private List<SelectItem> reportTypeList;
    private List<SelectItem> matDescOptionList;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private InvestmentMgmtFacadeRemote obj = null;
    private final String jndiName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public String getFieldDisplay() {
        return fieldDisplay;
    }

    public void setFieldDisplay(String fieldDisplay) {
        this.fieldDisplay = fieldDisplay;
    }

    public String getFrmInputDisplay() {
        return frmInputDisplay;
    }

    public void setFrmInputDisplay(String frmInputDisplay) {
        this.frmInputDisplay = frmInputDisplay;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getMatDescOption() {
        return matDescOption;
    }

    public void setMatDescOption(String matDescOption) {
        this.matDescOption = matDescOption;
    }

    public List<SelectItem> getMatDescOptionList() {
        return matDescOptionList;
    }

    public void setMatDescOptionList(List<SelectItem> matDescOptionList) {
        this.matDescOptionList = matDescOptionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public DueReport() {
        reportTypeList = new ArrayList<SelectItem>();
        matDescOptionList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            fillReportTypeList();
            fillMatDescList();
            resetAction();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillMatDescList() {
        matDescOptionList.add(new SelectItem("--Select--"));
        matDescOptionList.add(new SelectItem("Between"));
        matDescOptionList.add(new SelectItem("Till"));
    }

    public void fillReportTypeList() {
        reportTypeList.add(new SelectItem("--Select--"));
        reportTypeList.add(new SelectItem("All"));
        try {
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = obj.getGlRange("G");
            for (int i = 0; i < resultList.size(); i++) {
                GlDescRange entity = resultList.get(i);
                reportTypeList.add(new SelectItem(entity.getGlname()));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurReportType() {
        this.setMessage("");
        if (this.reportType.equals("--Select--")) {
            this.setMessage("Please select report type !");
            return;
        }
    }

    public void onBlurMatDate() {
        this.setMessage("");
        if (this.matDescOption.equals("--Select--")) {
            this.setMessage("Please select maturity date !");
            return;
        }
        if (this.matDescOption.equalsIgnoreCase("Between")) {
            fieldDisplay = "";
            frmInputDisplay = "";
        }
        if (this.matDescOption.equalsIgnoreCase("Till")) {
            fieldDisplay = "none";
            frmInputDisplay = "";
        }
    }

    public void onBlurFromDt() {
        this.setMessage("");
        if (this.fromDt.length() < 10) {
            this.setMessage("please fill correct from date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.fromDt) == false) {
            this.setMessage("please fill correct from date !");
            return;
        }
    }

    public void onBlurToDt() {
        this.setMessage("");
        if (this.toDt.length() < 10) {
            this.setMessage("please fill correct to date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
            this.setMessage("please fill correct to date !");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        List<DueDatePojo> reportList = new ArrayList<DueDatePojo>();
        List<InvestmentMaster> resultList = new ArrayList<InvestmentMaster>();
        String repName = "";
        try {
            if (validate()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List brList = common.getBranchNameandAddress(getOrgnBrCode());
                if (this.matDescOption.equalsIgnoreCase("Between")) {
                   if(this.getReportType().equalsIgnoreCase("All")) {
                       repName = "All Investment Due Between Range Report";
                       resultList = remoteObj.getAllDueReportBetweenRange("TERM DEPOSIT", dmy.parse(this.getFromDt()), dmy.parse(this.getToDt()));                    
                   }else{
                       repName = "Investment Due Between Range Report";
                       resultList = remoteObj.getDueReportBetweenRange(this.getReportType(), dmy.parse(this.getFromDt()), dmy.parse(this.getToDt()));                    
                   }
                   
                   if (!resultList.isEmpty()) {
                        for (InvestmentMaster entity : resultList) {
                            DueDatePojo pojo = new DueDatePojo();
                            
                            pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                            pojo.setSecDtl(entity.getSecdesc());
                            pojo.setSellarName(entity.getSellername());
                            pojo.setFaceValue(entity.getFacevalue());
                            pojo.setDueDt(dmy.format(entity.getMatdt()));
                            pojo.setRoi(entity.getRoi());
                            pojo.setMatValue(entity.getBookvalue());
                            
                            reportList.add(pojo);
                        }                        
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                } else if (this.matDescOption.equalsIgnoreCase("Till")) {
                    if(this.getReportType().equalsIgnoreCase("All")) {
                        repName = "All Investment Due Till Report";
                        resultList = remoteObj.getAllDueReportTillDate("TERM DEPOSIT", dmy.parse(this.getFromDt()));
                    }else{
                        repName = "Investment Due Till Report";
                        resultList = remoteObj.getDueReportTillDate(this.getReportType(), dmy.parse(this.getFromDt()));                    
                    }
                    
                    if (!resultList.isEmpty()) {
                        for (InvestmentMaster entity : resultList) {
                            DueDatePojo pojo = new DueDatePojo();
                            
                            pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                            pojo.setSecDtl(entity.getSecdesc());
                            pojo.setSellarName(entity.getSellername());
                            pojo.setFaceValue(entity.getFacevalue());
                            pojo.setDueDt(dmy.format(entity.getMatdt()));
                            pojo.setRoi(entity.getRoi());
                            pojo.setMatValue(entity.getBookvalue());
                            
                            reportList.add(pojo);
                        }                        
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                }
                
                String repDate ="";
                
                if(this.matDescOption.equalsIgnoreCase("Between")){
                    repDate = "From " + this.getFromDt() + " To " + this.getToDt();
                }else{
                    repDate = "Till " + this.getFromDt();
                }                
                
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", brList.get(0));
                fillParams.put("pBranchAddress", brList.get(1));
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate",repDate);
                
                new ReportBean().jasperReport("dueBetweenRangeReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "DUE REPORT");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validate() {
        this.setMessage("");
        if (this.reportType.equals("--Select--")) {
            this.setMessage("Please select report type !");
            return false;
        }
        if (this.matDescOption.equals("--Select--")) {
            this.setMessage("Please select maturity date !");
            return false;
        }
        if (this.matDescOption.equalsIgnoreCase("Between")) {
            if (this.fromDt.length() < 10) {
                this.setMessage("please fill correct from date !");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.fromDt) == false) {
                this.setMessage("please fill correct from date !");
                return false;
            }
            if (this.toDt.length() < 10) {
                this.setMessage("please fill correct to date !");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
                this.setMessage("please fill correct to date !");
                return false;
            }
        }
        if (this.matDescOption.equalsIgnoreCase("Till")) {
            if (this.fromDt.length() < 10) {
                this.setMessage("please fill correct from date !");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.fromDt) == false) {
                this.setMessage("please fill correct from date !");
                return false;
            }
        }
        return true;
    }

    public void resetAction() {
        this.setMessage("");
        fieldDisplay = "none";
        frmInputDisplay = "none";
        this.setMatDescOption("--Select--");
        this.setReportType("--Select--");
        this.setFromDt("");
        this.setToDt("");
        this.setMessage("Please select report type !");
    }

    public String exitAction() {
        try {
            resetAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}