/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentAmortizationDetails;
import com.cbs.entity.ho.investment.InvestmentAmortizationDetailsPK;
import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.AmortDates;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class AmortCalculation extends BaseBean {

    private String message;
    private String ctrl;
    private String option;
    private String amortization;
    private boolean visiblePost;
    private List<SelectItem> ctrlList;
    private List<SelectItem> optionList;
    private List<SelectItem> amortizationList;
    private List<AmortDates> amortList;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.##");
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean calcFlag;
    private String secTpDd;
    private List<SelectItem> secTypeList;

    public boolean isVisiblePost() {
        return visiblePost;
    }

    public void setVisiblePost(boolean visiblePost) {
        this.visiblePost = visiblePost;
    }

    public String getAmortization() {
        return amortization;
    }

    public void setAmortization(String amortization) {
        this.amortization = amortization;
    }

    public List<SelectItem> getAmortizationList() {
        return amortizationList;
    }

    public void setAmortizationList(List<SelectItem> amortizationList) {
        this.amortizationList = amortizationList;
    }

    public String getCtrl() {
        return ctrl;
    }
    
    public void setCtrl(String ctrl) {
        this.ctrl = ctrl;
    }

    public List<SelectItem> getCtrlList() {
        return ctrlList;
    }

    public void setCtrlList(List<SelectItem> ctrlList) {
        this.ctrlList = ctrlList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getSecTpDd() {
        return secTpDd;
    }

    public void setSecTpDd(String secTpDd) {
        this.secTpDd = secTpDd;
    }

    public List<SelectItem> getSecTypeList() {
        return secTypeList;
    }

    public void setSecTypeList(List<SelectItem> secTypeList) {
        this.secTypeList = secTypeList;
    }
    
    public AmortCalculation() {
        try {
            ctrlList = new ArrayList<SelectItem>();
            optionList = new ArrayList<SelectItem>();
            secTypeList = new ArrayList<SelectItem>();
            amortizationList = new ArrayList<SelectItem>();
            amortList = new ArrayList<AmortDates>();
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            resetForm();
            setOtherList();
            loadSecurityType();
            this.setVisiblePost(false);
            this.setMessage("Please select control no !");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void loadSecurityType() {
        try {
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = remoteObj.getGlRange("G");
            for (int i = 0; i < resultList.size(); i++) {
                GlDescRange entity = resultList.get(i);
                secTypeList.add(new SelectItem(entity.getGlname()));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void secTypeLostFocus() {
        getControlNoData(this.getSecTpDd());              
    }  

    public void getControlNoData(String type) {
        if(!secTpDd.equalsIgnoreCase("TREASURY BILLS")){
            ctrlList = new ArrayList<SelectItem>();
            ctrlList.add(new SelectItem("--Select--"));        
            try {
                List<InvestmentGoidates> entityList = remoteObj.getControllNoForAmortCalc("Active",type.trim());
                if (!entityList.isEmpty()) {
                    for (InvestmentGoidates entity : entityList) {
                        ctrlList.add(new SelectItem(entity.getCtrlno()));
                    }
                } else {
                    this.setMessage("There is no control no !");
                    return;
                }
            } catch (ApplicationException ex) {
                this.setMessage(ex.getMessage());
            } catch (Exception ex) {
                this.setMessage(ex.getMessage());
            }
        } else{
            ctrlList = new ArrayList<SelectItem>();
            ctrlList.add(new SelectItem(""));
        }      
    }

    public void setOtherList() {
        optionList.add(new SelectItem("F","Financial Year"));
        optionList.add(new SelectItem("C","Calendar Year"));
        amortizationList.add(new SelectItem("Quarterly"));
        amortizationList.add(new SelectItem("Yearly"));
        amortizationList.add(new SelectItem("Half Yearly"));
    }

    public void onBlurCtrlNo() {
        this.setMessage("");
        if (this.ctrl.equals("--Select--")) {
            this.setMessage("Please select control no !");
            return;
        }
    }

    public void calculation() {
        calcFlag = true;
        this.setMessage("");
        int serialNo = 1, controllNo = 0;
        String purchaseDt = "", matDt = "", dtF = "", dtH = "", mode = "", pDtFst = "";
        double faceVal = 0.0, bookVal = 0.0, totaldays = 0, aa = 0;
        try {
            amortList = new ArrayList<AmortDates>();
            if (this.ctrl.equals("--Select--")) {
                this.setMessage("Please select control no !");
                return;
            }
            Object[] obj = remoteObj.getCtrlNoDetailsAmortcalc(Integer.parseInt(this.ctrl));
            if (obj.length > 0) {
                InvestmentGoidates investmentGoidatesObj = (InvestmentGoidates) obj[0];
                if (investmentGoidatesObj != null) {
                    if (investmentGoidatesObj.getCtrlno() == null || investmentGoidatesObj.getCtrlno().toString().equals("")) {
                        controllNo = 0;
                    } else {
                        controllNo = investmentGoidatesObj.getCtrlno();
                    }
                    if (investmentGoidatesObj.getPurchasedt() == null || investmentGoidatesObj.getPurchasedt().toString().equals("")) {
                        purchaseDt = "";
                    } else {
                        purchaseDt = ymd.format(investmentGoidatesObj.getPurchasedt());
                    }
                    if (investmentGoidatesObj.getMatdt() == null || investmentGoidatesObj.getMatdt().toString().equals("")) {
                        matDt = "";
                    } else {
                        matDt = ymd.format(investmentGoidatesObj.getMatdt());
                    }
                }
                InvestmentMaster investmentMasterObj = (InvestmentMaster) obj[1];
                if (investmentMasterObj != null) {
                    faceVal = investmentMasterObj.getFacevalue();
                    bookVal = investmentMasterObj.getBookvalue();
                }
            }

            pDtFst = purchaseDt;

            int doubleCompare = Double.compare(faceVal, bookVal);
            if (doubleCompare > 0) {
                this.setMessage("Book value is less than face value !");
                calcFlag = false;
                return;
            }

            if (this.amortization.equalsIgnoreCase("Yearly")) {
                if (this.option.equalsIgnoreCase("F")) {
                    dtF = purchaseDt.substring(0, 4) + "0331";
                    dtH = purchaseDt.substring(0, 4) + "0331";
                    if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                        dtF = Integer.toString(Integer.parseInt(purchaseDt.substring(0, 4))+1) + "0331";
                    }
                    if(ymd.parse(dtH).compareTo(ymd.parse(purchaseDt))<0){
                        dtH = Integer.toString(Integer.parseInt(purchaseDt.substring(0, 4))+1) + "0331";
                    }
                } else if (this.option.equalsIgnoreCase("C")) {
                    dtF = purchaseDt.substring(0, 4) + "1231";
                    dtH = purchaseDt.substring(0, 4) + "1231";
                }
            } else if (this.amortization.equalsIgnoreCase("Half Yearly")) {
                if (this.option.equalsIgnoreCase("F")) {
                    dtF = purchaseDt.substring(0, 4) + "0930";
                    if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                        dtF = purchaseDt.substring(0, 4) + "0331";
                    }                    
                } else if (this.option.equalsIgnoreCase("C")) {
                    dtF = purchaseDt.substring(0, 4) + "0630";
                    if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                        dtF = purchaseDt.substring(0, 4) + "1231";
                    }                    
                }
            } else if (this.amortization.equalsIgnoreCase("Quarterly")) {
                if (this.option.equalsIgnoreCase("F")) {
                    dtF = purchaseDt.substring(0, 4) + "0331";
                    if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                        dtF = purchaseDt.substring(0, 4) + "0630";
                        if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                            dtF = purchaseDt.substring(0, 4) + "0930";
                            if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                                dtF = purchaseDt.substring(0, 4) + "1231";
                            }
                        }
                    }                    
                } else if (this.option.equalsIgnoreCase("C")) {
                    dtF = purchaseDt.substring(0, 4) + "0331";
                    if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                        dtF = purchaseDt.substring(0, 4) + "0630";
                        if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                            dtF = purchaseDt.substring(0, 4) + "0930";
                            if(ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))<0){
                                dtF = purchaseDt.substring(0, 4) + "1231";
                            }
                        }
                    }                    
                }
            }

            if (this.amortization.equalsIgnoreCase("Yearly")) {
                if (this.option.equalsIgnoreCase("F")) {
                    mode = "FY";
                    if (ymd.parse(dtF).compareTo(ymd.parse(purchaseDt)) > 0) {
                        AmortDates tableObj = new AmortDates();
                        tableObj.setSlno(serialNo);
                        tableObj.setCtrlno(controllNo);
                        tableObj.setDateS(purchaseDt);
                        tableObj.setDateE(dtF);
                        tableObj.setMode("FY");
                        amortList.add(tableObj);
                    }

                    serialNo = serialNo + 1;
                    while ((ymd.parse(purchaseDt).compareTo(ymd.parse(matDt))) <= 0) {
                        if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) < 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(dtF);
                            tableObj.setMode("FY");
                            amortList.add(tableObj);
                        } else {
                            if ((ymd.parse(CbsUtil.yearAdd(dtF, 1)).compareTo(ymd.parse(matDt))) < 0) {                                
                                AmortDates tableObj = new AmortDates();
                                tableObj.setSlno(serialNo);
                                tableObj.setCtrlno(controllNo);
                                tableObj.setDateS(dtF);
                                tableObj.setDateE(CbsUtil.monthLast(dtF, 12));
                                tableObj.setMode("FY");
                                amortList.add(tableObj);
                            } else {
                                AmortDates tableObj = new AmortDates();
                                tableObj.setSlno(serialNo);
                                tableObj.setCtrlno(controllNo);
                                tableObj.setDateS(dtF);
                                tableObj.setDateE(matDt);
                                tableObj.setMode("FY");
                                amortList.add(tableObj);
                            }
                        }
                        serialNo = serialNo + 1;
                        dtF = CbsUtil.monthLast(dtF, 12);
                        purchaseDt = dtF;
                    }
                }
            }

            if (this.amortization.equalsIgnoreCase("Yearly")) {
                if (this.option.equalsIgnoreCase("C")) {
                    mode = "CY";
                    while ((ymd.parse(purchaseDt).compareTo(ymd.parse(matDt))) <= 0) {
                        if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) < 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(dtF);
                            tableObj.setDateE(purchaseDt);
                            tableObj.setMode("CY");
                            amortList.add(tableObj);
                        } else if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) > 0 && (ymd.parse(dtF).compareTo(ymd.parse(matDt)) < 0)) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(dtF);
                            tableObj.setMode("CY");
                            amortList.add(tableObj);
                        }
                        if ((ymd.parse(dtF).compareTo(ymd.parse(matDt))) > 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(matDt);
                            tableObj.setMode("CY");
                            amortList.add(tableObj);
                        }
                        serialNo = serialNo + 1;
                        purchaseDt = dtF;
                        dtF = CbsUtil.monthLast(dtF, 12);
                    }
                }
            }

            if (this.amortization.equalsIgnoreCase("Half Yearly")) {
                if (this.option.equalsIgnoreCase("F")) {
                    mode = "FH";
                    while ((ymd.parse(purchaseDt).compareTo(ymd.parse(matDt))) <= 0) {
                        if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) < 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(dtF);
                            tableObj.setDateE(purchaseDt);
                            tableObj.setMode("FH");
                            amortList.add(tableObj);
                        } else if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) > 0 && (ymd.parse(dtF).compareTo(ymd.parse(matDt)) < 0)) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(dtF);
                            tableObj.setMode("FH");
                            amortList.add(tableObj);
                        }
                        if ((ymd.parse(dtF).compareTo(ymd.parse(matDt))) > 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(matDt);
                            tableObj.setMode("FH");
                            amortList.add(tableObj);
                        }
                        serialNo = serialNo + 1;
                        purchaseDt = dtF;
                        dtF = CbsUtil.monthLast(dtF, 6);
                        if (dtF.substring(4).equals("0330")) {
                            dtF = CbsUtil.dateAdd(dtF, 1);
                        }
                    }
                }
            }

            if (this.amortization.equalsIgnoreCase("Half Yearly")) {
                if (this.option.equalsIgnoreCase("C")) {
                    mode = "CH";
                    while ((ymd.parse(purchaseDt).compareTo(ymd.parse(matDt))) <= 0) {
                        if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) < 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(dtF);
                            tableObj.setDateE(purchaseDt);
                            tableObj.setMode("CH");
                            amortList.add(tableObj);
                        } else if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) > 0 && (ymd.parse(dtF).compareTo(ymd.parse(matDt)) < 0)) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(dtF);
                            tableObj.setMode("CH");
                            amortList.add(tableObj);
                        }
                        if ((ymd.parse(dtF).compareTo(ymd.parse(matDt))) > 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(matDt);
                            tableObj.setMode("CH");
                            amortList.add(tableObj);
                        }
                        serialNo = serialNo + 1;
                        purchaseDt = dtF;
                        dtF = CbsUtil.monthLast(dtF, 6);
                        if (dtF.substring(4).equals("1230")) {
                            dtF = CbsUtil.dateAdd(dtF, 1);
                        }
                    }
                }
            }
            if (this.amortization.equalsIgnoreCase("Quarterly")) {
                if (this.option.equalsIgnoreCase("F")) {
                    mode = "FQ";
                    while ((ymd.parse(purchaseDt).compareTo(ymd.parse(matDt))) <= 0) {
                        if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) < 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(dtF);
                            tableObj.setDateE(purchaseDt);
                            tableObj.setMode("FQ");
                            amortList.add(tableObj);
                        } else if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) > 0 && (ymd.parse(dtF).compareTo(ymd.parse(matDt)) < 0)) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(dtF);
                            tableObj.setMode("FQ");
                            amortList.add(tableObj);
                        }
                        if ((ymd.parse(dtF).compareTo(ymd.parse(matDt))) > 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(matDt);
                            tableObj.setMode("FQ");
                            amortList.add(tableObj);
                        }
                        serialNo = serialNo + 1;
                        purchaseDt = dtF;
                        dtF = CbsUtil.monthLast(dtF, 3);
                    }
                }
            }

            if (this.amortization.equalsIgnoreCase("Quarterly")) {
                if (this.option.equalsIgnoreCase("C")) {
                    mode = "CQ";
                    while ((ymd.parse(purchaseDt).compareTo(ymd.parse(matDt))) <= 0) {
                        if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) < 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(dtF);
                            tableObj.setDateE(purchaseDt);
                            tableObj.setMode("CQ");
                            amortList.add(tableObj);
                        } else if ((ymd.parse(dtF).compareTo(ymd.parse(purchaseDt))) > 0 && (ymd.parse(dtF).compareTo(ymd.parse(matDt)) < 0)) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(dtF);
                            tableObj.setMode("CQ");
                            amortList.add(tableObj);
                        }
                        if ((ymd.parse(dtF).compareTo(ymd.parse(matDt))) > 0) {
                            AmortDates tableObj = new AmortDates();
                            tableObj.setSlno(serialNo);
                            tableObj.setCtrlno(controllNo);
                            tableObj.setDateS(purchaseDt);
                            tableObj.setDateE(matDt);
                            tableObj.setMode("CQ");
                            amortList.add(tableObj);
                        }
                        serialNo = serialNo + 1;
                        purchaseDt = dtF;
                        dtF = CbsUtil.monthLast(dtF, 3);
                    }
                }
            }

            if (!amortList.isEmpty()) {
                for (int i = 0; i < amortList.size(); i++) {
                    AmortDates tableObj = amortList.get(i);
                    String amortDateE = tableObj.getDateE();
                    String amortDateS = tableObj.getDateS();
                    double amortDays = amortDays(amortDateE, amortDateS, this.option);
                    tableObj.setDays(amortDays);

                    totaldays = totaldays + tableObj.getDays();
//                    aa = ((bookVal - faceVal) * tableObj.getDays()) / totaldays;
//                    tableObj.setAmount(aa);
                }
            }

            if (!amortList.isEmpty()) {
                for (int i = 0; i < amortList.size(); i++) {
                    AmortDates tableObj = amortList.get(i);
//                    String amortDateE = tableObj.getDateE();
//                    String amortDateS = tableObj.getDateS();
//                    double amortDays = amortDays(amortDateE, amortDateS, this.option);
//                    tableObj.setDays(amortDays);//
//                    totaldays = totaldays + tableObj.getDays();

                    aa = ((bookVal - faceVal) * tableObj.getDays()) / totaldays;
                    tableObj.setAmount(Math.round(aa));
                }
            }
            /****CallReport method will have to make and call****/
            if (amortizationListForReportAndPost(amortList, bookVal, faceVal).equalsIgnoreCase("true")) {
                String branchName = "";
                String address = "";

                String report = "Amortization Calculation";
                List brNameAdd = new ArrayList();
                brNameAdd = commonRemote.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }

                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pReportDt", dmy.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBranchName", branchName);
                fillParams.put("pAddress", address);
                fillParams.put("pFaceVal", faceVal);
                fillParams.put("pBookVal", bookVal);
                fillParams.put("pAmortCalc", faceVal - bookVal);
                fillParams.put("pDtFst", pDtFst);

                new ReportBean().jasperReport("AmortCalcReport", "text/html",
                        new JRBeanCollectionDataSource(amortList), fillParams, "Amortization Calculation");
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (ApplicationException ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        }
    }

    public String amortizationListForReportAndPost(List<AmortDates> reportAndPostList, double bookVal, double faceVal) {
        double totalSum = 0, totalSumOne = 0;
        String msg = "";
        try {
            if (reportAndPostList.isEmpty()) {
                this.setMessage("Data does not exist for post !");
                this.visiblePost = true;
                msg = "";
            } else {
                Collections.sort(reportAndPostList);
                Collections.reverse(reportAndPostList);
                for (int i = 0; i < reportAndPostList.size(); i++) {
                    AmortDates tableObj = reportAndPostList.get(i);
                    totalSum = totalSum + tableObj.getAmount();
                }
                totalSumOne = (bookVal - faceVal) - totalSum;
                if (totalSumOne != 0 || totalSumOne != 0.0) {
                    AmortDates tableObj = reportAndPostList.get(0);
                    double maxSlNoAmt = tableObj.getAmount();
                    String updateAmt = formatter.format(maxSlNoAmt + totalSumOne);
                    tableObj.setAmount(Double.parseDouble(updateAmt));
                    this.visiblePost = false;
                }
                msg = "true";
                this.visiblePost = false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return msg;
    }

    public double amortDays(String amortDateT, String amortDateF, String flag) {
        double days = 0, fullMonth, part1;
        String dateBegin = "", dateEnd = "";
        try {
            Date amortdateT = ymd.parse(amortDateT);
            Date amortdateF = ymd.parse(amortDateF);
             CbsUtil.dateAdd(amortDateT,1);
            String dmyAmortDateT = dmy.format(amortdateT);
            String dmyAmortDateF = dmy.format(amortdateF);

            if (this.amortization.equalsIgnoreCase("Half Yearly")) {
                if (flag.equalsIgnoreCase("F")) {
                    int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                    if (monDiff == 6) {
                        long dayDiff = CbsUtil.dayDiff(amortdateF, amortdateT);
                        return days = dayDiff;
                       
                    }

                    if (!dmyAmortDateF.substring(0, 5).equals("30/09") && !dmyAmortDateF.substring(0, 5).equals("31/03")) {
                        int mDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                        if (mDiff < 12) {
                            dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
                            int compare = amortdateF.compareTo(dmy.parse(dateBegin));
                            if (compare != 0) {
                                dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
                                fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
                                part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
                                days = (fullMonth * 30) + part1;
                                  
                            } else {
                                fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
                               
                            }
                        }
                    }

                    if (!dmyAmortDateT.substring(0, 5).equals("30/09") && !dmyAmortDateT.substring(0, 5).equals("31/03")) {
                        if (monDiff < 12) {
                            dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
                            dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
                            fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
                            part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
                            days = (fullMonth * 30) + part1;
                            
                        }
                    }
                }

                if (flag.equalsIgnoreCase("C")) {
                    int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                    if (monDiff == 6) {
                        long dayDiff = CbsUtil.dayDiff(amortdateF, amortdateT);
                       return  days = dayDiff;
                          
                        
                    }

                    if (!dmyAmortDateF.substring(0, 5).equals("30/06") && !dmyAmortDateF.substring(0, 5).equals("31/12")) {
                        int mDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                        if (mDiff < 12) {
                            dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
                            int compare = amortdateF.compareTo(dmy.parse(dateBegin));
                            if (compare != 0) {
                                dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
                                fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
                                part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
                                days = (fullMonth * 30) + part1;
                              
                            } else {
                                fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
                                days = (fullMonth * 30);
                                
                            }
                        }
                    }
                    if (!dmyAmortDateT.substring(0, 5).equals("30/06") && !dmyAmortDateT.substring(0, 5).equals("31/12")) {
                        int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
                        if (moDiff < 12) {
                            dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
                            dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
                            fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
                            part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
                            days = (fullMonth * 30) + part1;
                              
                        }
                    }
                }
            } else if (this.amortization.equalsIgnoreCase("Quarterly")) {
                if (flag.equalsIgnoreCase("F")) {
                    int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                    if (monDiff == 3) {
                        long dayDiff = CbsUtil.dayDiff(amortdateF, amortdateT);
                        return dayDiff;
                        
                    }

                    if (!dmyAmortDateF.substring(0, 5).equals("30/06") && !dmyAmortDateF.substring(0, 5).equals("30/09") && !dmyAmortDateF.substring(0, 5).equals("31/12") && !dmyAmortDateF.substring(0, 5).equals("31/03")) {
                        int mDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                        if (mDiff < 12) {
                            dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
                            int compare = amortdateF.compareTo(dmy.parse(dateBegin));
                            if (compare > 0) {
                                days = CbsUtil.dayDiff(amortdateF, amortdateT);  
                               
                                
                                
                            } else if (compare < 0) {
                                dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
                                fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
                                part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
                                days = (fullMonth * 30) + part1;
                               
                            } else {
                                fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
                                days = (fullMonth * 30);
                                
                            }
                        }
                    }

                    if (!dmyAmortDateT.substring(0, 5).equals("30/06") && !dmyAmortDateT.substring(0, 5).equals("30/09") && !dmyAmortDateT.substring(0, 5).equals("31/12") && !dmyAmortDateT.substring(0, 5).equals("31/03")) {
                        if (monDiff < 12) {
                            dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
                            dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
                            fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
                            part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
                            days = (fullMonth * 30) + part1;
                           
                        }
                    }
                }

                if (flag.equalsIgnoreCase("C")) {
                    int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                    if (monDiff == 3) {
                        long dayDiff = CbsUtil.dayDiff(amortdateF, amortdateT);
                         return days = dayDiff;
                        
                    }

                    if (!dmyAmortDateF.substring(0, 5).equals("31/03") && !dmyAmortDateF.substring(0, 5).equals("30/06") && !dmyAmortDateF.substring(0, 5).equals("30/09") && !dmyAmortDateF.substring(0, 5).equals("31/12")) {
                        int mDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                        if (mDiff < 12) {
                            dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
                            int compare = amortdateF.compareTo(dmy.parse(dateBegin));
                            if (compare != 0) {
                                dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
                                fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
                                part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
                                days = (fullMonth * 30) + part1;
                                
                            } else {
                                fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
                                days = (fullMonth * 30);
                               
                            }
                        }
                    }
                    if (!dmyAmortDateT.substring(0, 5).equals("31/03") && !dmyAmortDateT.substring(0, 5).equals("30/06") && !dmyAmortDateT.substring(0, 5).equals("30/09") && !dmyAmortDateT.substring(0, 5).equals("31/12")) {
                        int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
                        if (moDiff < 12) {
                            dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
                            dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
                            fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
                            part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
                            days = (fullMonth * 30) + part1;
                         
                        }
                    }
                }
            } else {
                if (flag.equalsIgnoreCase("F")) {
                    int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                    if (monDiff == 12) {
                        long dayDiff = CbsUtil.dayDiff(amortdateF, amortdateT);
                        return  days = dayDiff;
                       
                        
                    }
                    if (!dmyAmortDateF.substring(0, 5).equals("31/03")) {
                        if (monDiff < 12) {
                            dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
                            int compare = amortdateF.compareTo(dmy.parse(dateBegin));
                            if (compare != 0) {
                                dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
                                fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
                                part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
                                days = (fullMonth * 30) + part1;
                            
                            } else {
                                fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
                                days = (fullMonth * 30);
                              
                            }
                        }
                    }
                    if (!dmyAmortDateT.substring(0, 5).equals("31/03")) {
                        int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
                        if (moDiff < 12) {
                            dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
                            dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
                            fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
                            part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
                            days = (fullMonth * 30) + part1;
                           
                        }
                    }
                }

                if (flag.equalsIgnoreCase("C")) {
                    int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
                    if (monDiff == 12) {
                        long dayDiff = CbsUtil.dayDiff(amortdateF, amortdateT);
                         return days = dayDiff;
                       
                    }
                    if (!dmyAmortDateF.substring(0, 5).equals("31/12")) {
                        if (monDiff < 12) {
                            dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
                            int compare = amortdateF.compareTo(dmy.parse(dateBegin));
                            if (compare != 0) {
                                dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
                                fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
                                part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
                                days = (fullMonth * 30) + part1;
                               
                            } else {
                                fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
                                days = (fullMonth * 30);
                              
                            }
                        }
                    }
                    if (!dmyAmortDateT.substring(0, 5).equals("31/12")) {
                        int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
                        if (moDiff < 12) {
                            dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
                            dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
                            fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
                            part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
                            days = (fullMonth * 30) + part1;
                            
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return days;
    }

    public void post() {
        this.setMessage("");
        try {
            Collections.reverse(amortList);
            for (int i = 0; i < amortList.size(); i++) {
                AmortDates tableObj = amortList.get(i);
                InvestmentAmortizationDetails amortTableObj = new InvestmentAmortizationDetails();

                InvestmentAmortizationDetailsPK invstAmortPK = new InvestmentAmortizationDetailsPK(tableObj.getCtrlno(), tableObj.getSlno());

                amortTableObj.setInvestmentAmortizationDetailsPK(invstAmortPK);
                amortTableObj.setYears(ymd.parse(tableObj.getDateE()));
                amortTableObj.setDays(new BigDecimal(tableObj.getDays()).toBigInteger());
                amortTableObj.setAmortAmt(new BigDecimal(formatter.format(tableObj.getAmount())));
                amortTableObj.setStatus("A");
                amortTableObj.setEnterBy(getUserName());
                amortTableObj.setTranTime(new Date());
                amortTableObj.setMode(tableObj.getMode());

                remoteObj.saveInvestmentAmortizationDetails(amortTableObj);
            }
            resetForm();
            this.setMessage("Data has been posted successfully !");
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setMessage("");
        this.visiblePost = true;
        this.setOption("F");
        ctrlList = new ArrayList<SelectItem>();        
        this.setCtrl("--Select--");
        this.setMessage("Please select Security Type !");
        amortList = new ArrayList<AmortDates>();
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}