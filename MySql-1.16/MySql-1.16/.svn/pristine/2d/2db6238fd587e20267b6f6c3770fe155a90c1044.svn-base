/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.ho.DemandRecoveryDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DemandRecoveryDetail extends BaseBean {

    private String branchcode;
    private List<SelectItem> branchcodeList;
    private String acnature;
    private List<SelectItem> acnatureList;
    private String actype;
    private List<SelectItem> actypeList;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private boolean recoveryCheck = false;
    private CommonReportMethodsRemote common;
    private String errMessage;
    private List tempList;
    private Vector tempVector;
    private String fromDate;
    private String toDate;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private OtherLoanReportFacadeRemote RemoteLocal;
    private List<DemandRecoveryDetailPojo> list;

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public List<SelectItem> getBranchcodeList() {
        return branchcodeList;
    }

    public void setBranchcodeList(List<SelectItem> branchcodeList) {
        this.branchcodeList = branchcodeList;
    }

    public String getAcnature() {
        return acnature;
    }

    public void setAcnature(String acnature) {
        this.acnature = acnature;
    }

    public List<SelectItem> getAcnatureList() {
        return acnatureList;
    }

    public void setAcnatureList(List<SelectItem> acnatureList) {
        this.acnatureList = acnatureList;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public List<SelectItem> getActypeList() {
        return actypeList;
    }

    public void setActypeList(List<SelectItem> actypeList) {
        this.actypeList = actypeList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isRecoveryCheck() {
        return recoveryCheck;
    }

    public void setRecoveryCheck(boolean recoveryCheck) {
        this.recoveryCheck = recoveryCheck;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public DemandRecoveryDetail() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteLocal = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchcodeList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchcodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acnatureList = new ArrayList();
            actypeList = new ArrayList();
            tempList = common.getAcctNatureOnlyDB();
            if (!tempList.isEmpty()) {
             
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    if (tempVector.get(0).toString().equalsIgnoreCase("TL")) {
                        acnatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                    }
                }
            }
            tempList = common.getAcctTypeAsAcNatureOnlyDB(actype);
            if (!tempList.isEmpty()) {
                actypeList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    actypeList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
            monthList = new ArrayList<SelectItem>();
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }


        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void blurAcctNature() {
        if (actypeList != null) {
            actypeList.clear();
        }
        Vector vtr = null;
        try {
            List result = null;
            result = common.getAcctTypeAsAcNatureOnlyDB(acnature);
            actypeList.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                actypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
            }
        } catch (Exception e) {
            setErrMessage(e.getMessage());
        }
    }

   public void onCheckRecovery(){
       if(this.isRecoveryCheck()== false){
           this.recoveryCheck=true;
       }else{
           this.recoveryCheck= false;
       }
   }
    
    public void pdfDownLoad() {
        try {
            if (this.month.equalsIgnoreCase("") || this.month.equalsIgnoreCase("0")) {
                this.setErrMessage("Please fill month. !!");
                return;
            }
            if (this.year == null || this.year.equalsIgnoreCase("")) {
                this.setErrMessage("Please fill Year !!");
                return;
            }

            toDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            fromDate = "01" + toDate.substring(2);
            list = new ArrayList();
            list = RemoteLocal.getDemandRecoveryDetail(branchcode, acnature, actype, ymd.format(dmy.parse(fromDate)), ymd.format(dmy.parse(toDate)));
            if (!list.isEmpty()) {
                List bnkList;
                if (branchcode.equalsIgnoreCase("0A")) {
                    bnkList = common.getBranchNameandAddress("90");
                } else {
                    bnkList = common.getBranchNameandAddress(getBranchcode());
                }
                String bankName = "", bankAdd = "";
                if (!bnkList.isEmpty()) {
                    bankName = bnkList.get(0).toString();
                    bankAdd = bnkList.get(1).toString();
                }
                Map fillParams = new HashMap();
                String report = "Demand Recovery Detail Report";
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pRecoveryCheck",this.recoveryCheck);
                fillParams.put("pReportName", report);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAdd);
                fillParams.put("pPrintedDate", this.getTodayDate());
                fillParams.put("pReportDate", "For the period " + fromDate + " to " + toDate + " ");
                new ReportBean().openPdf("Demand Recovery Detail Report", "Demand Recovery Detail Report", new JRBeanCollectionDataSource(list), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "Demand Recovery Detail Report");

            } else {
                this.setErrMessage("Data does's not exist !!");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void refreshPage() {
        this.branchcode = "0";
        this.acnature = "0";
        this.actype = "0";
        this.month = "0";
        this.year = "";
        this.recoveryCheck = false;

    }
}
