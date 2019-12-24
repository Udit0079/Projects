/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.FdrIntPostPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class FdInterestPosting extends BaseBean {

    private String message;
    private List<SelectItem> repTypeList;
    private InvestmentMgmtFacadeRemote obj = null;
    private final String jndiName = "InvestmentMgmtFacade";
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private TdReceiptManagementFacadeRemote tdobj = null;
    private final String tdJndiName = "TdReceiptManagementFacade";
    private String repType;
    private String frmDt;
    private String toDt;
    private List<SelectItem> ctrlNoList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    private String ctrlNoDd;
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
    private String remark;
    private boolean visiblePost;
    private boolean accacnovisibility;
    private String drHeadDesc;
    private String crHeadDesc;
    private String accrHeadDesc;
    private String accrAmt;
    private String accrAcno;
    private double pCrAmt;
    NumberFormat formatter = new DecimalFormat("#.00");
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean calcFlag;
    private String repDesc;
    private List<SelectItem> repDescList;
    List<FdrIntPostPojo> reportList ;
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private MiscReportFacadeRemote beanRemote = null;

    public FdInterestPosting() {
        this.setMessage("");
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            tdobj = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(tdJndiName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            getReportTypeList();
            getControlNoList();
            this.setVisiblePost(true);
            this.accacnovisibility = true;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurValAction() {
        double pDrTot = 0;
        if(pCrAmt > Double.parseDouble(this.getCrAmt())){
            pDrTot = Double.valueOf(this.getDrAmt()) - (pCrAmt - Double.parseDouble(this.getCrAmt()));
        }else if(pCrAmt < Double.parseDouble(this.getCrAmt())){
            pDrTot = Double.valueOf(this.getDrAmt()) + (Double.parseDouble(this.getCrAmt()) - pCrAmt);
        }else{
            pDrTot = Double.valueOf(this.getDrAmt());
        }
        this.setDrAmt(String.valueOf(formatter.format(pDrTot)));
    }
    
    public void getNewDrAcNo() {
             this.setMessage("");
        try {
                if (this.accrAcno.equalsIgnoreCase("") || this.accrAcno == null || this.accrAcno.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Debit Account No.");
                return;
            }

            if (this.accrAcno.equalsIgnoreCase("") || ((this.accrAcno.length() != 12) )) {
                this.setMessage("Please Fill Proper Debit Account Number.");
                return;
            } 
          accrHeadDesc =  acDescVal(accrAcno);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    

    public void calTxn() {

        try {
            calcFlag = true;
            accacnovisibility=false;
            
            reportList = new ArrayList<FdrIntPostPojo>();
            this.setMessage("");
            if (this.repType.equalsIgnoreCase("--Select--")) {
               this.setMessage("Please select Security type !!");
               calcFlag = false;
               return;
             }
            if (this.ctrlNoDd.equalsIgnoreCase("--SELECT--")) {
               this.setMessage("Please Select Control!!");
               calcFlag = false;
              return;
            }
            
            if(dmy.parse(this.frmDt).after(dmy.parse(this.toDt))){
            this.setMessage("Invalid From date And Todate");
            calcFlag=false;
            return;
            }
            
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            String maxDate = this.getToDt();
            String totAmt;
            Double postedAccr = 0.0;
            List<InvestmentFdrDates> obj1 = obj.onblurCntrlNumber(Integer.parseInt(this.getCtrlNoDd()));
            List<InvestmentFdrDetails> obj2 = obj.controlNumberDeatils(Integer.parseInt(this.getCtrlNoDd()));
            if (obj1.size() > 0 && obj2.size() > 0) {
                InvestmentFdrDates   entity1 = (InvestmentFdrDates)   obj1.get(0);
                InvestmentFdrDetails entity2 = (InvestmentFdrDetails) obj2.get(0);
              
                String intOpt = obj1.get(0).getIntOpt();
                if (dmy.parse(maxDate).before(entity1.getLastIntPaidDt()) && dmy.parse(maxDate).equals(entity1.getLastIntPaidDt())) {
                    setMessage("Invalid To Date!!!");
                    calcFlag = false;
                    return;
                }                
                
                int dd = (int) CbsUtil.dayDiff(dmy.parse(this.getFrmDt()), dmy.parse(maxDate));
                int yr = 0, mm = 0, day =0;
                
                String dayspr = yr + "Years" + mm + "Months" + dd + "Days";                
                
                if(intOpt.equalsIgnoreCase("C")){
                totAmt = tdobj.orgFDInterest(intOpt, (float) entity2.getRoi(), ymd.format(dmy.parse(this.getFrmDt())), ymd.format(dmy.parse(maxDate)), entity2.getFaceValue()+entity1.getAmtIntTrec(), dayspr, this.getOrgnBrCode()); 
                }else{
                totAmt = tdobj.orgFDInterest(intOpt, (float) entity2.getRoi(), ymd.format(dmy.parse(this.getFrmDt())), ymd.format(dmy.parse(maxDate)), entity2.getFaceValue(), dayspr, this.getOrgnBrCode());
                }
                postedAccr = entity1.getTotAmtIntRec();
                this.setDrAmt(totAmt);
                if (isVisiblePost()) {
                    visiblePost = false;
                }

                long dDif = CbsUtil.dayDiff(dmy.parse(this.getFrmDt()), dmy.parse(maxDate));
                FdrIntPostPojo pojo = new FdrIntPostPojo();
                pojo.setControlNo(entity1.getCtrlNo());
                pojo.setFaceValue(entity2.getFaceValue());                
                pojo.setLastIntPaidDate(ymd.format(dmy.parse(this.getFrmDt())));
                pojo.setMaturityValue(Double.valueOf(entity2.getBookValue()));
                pojo.setRoi(entity2.getRoi());
                pojo.setInterestAmt(Double.valueOf(getDrAmt()));
                pojo.setInterestoption(intOpt);
                pojo.setPeriod(String.valueOf(dDif));
                pojo.setClearingdiff((Double.valueOf(totAmt) - postedAccr));
                reportList.add(pojo);
               
            } else {
                this.setMessage("Data does not exist !");
                calcFlag = false;
                return;
            }

            InvestmentCallHead entity = obj.getInvestCallHeadByCode("6");
            if (entity != null) {
                pCrAmt = (Double.valueOf(totAmt) - postedAccr);
                this.setDrAcno(entity.getIntGlhead());
                this.setDrHeadDesc(acDescVal(entity.getIntGlhead()));
                this.setDrAmt(formatter.format(Double.valueOf(totAmt)));
                this.setCrAcno(entity.getDrGlhead());
                this.setCrHeadDesc(acDescVal(entity.getDrGlhead()));
                this.setCrAmt(formatter.format(pCrAmt));
                this.setAccrAmt(postedAccr.toString());
            } else {
                this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
            }

            Map fillParams = new HashMap();
            String repName = "FDR Interest Report";
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", brList.get(0));
            fillParams.put("pBranchAddress", brList.get(1));
            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", dmy.format(dmy.parse(maxDate)));
            new ReportBean().jasperReport("FDRInterestReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            this.setViewID("/report/ReportPagePopUp.jsp");

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void postTxn() {
        this.setMessage("");
        try {
            List<InvestmentFdrDates> obj1 = obj.onblurCntrlNumber(Integer.parseInt(this.getCtrlNoDd()));
             String intOpt = obj1.get(0).getIntOpt();
            InvestmentFdrDates entity1 = (InvestmentFdrDates) obj1.get(0);
            String msg = obj.postIntRecievedRecFdr(reportList, entity1.getCtrlNo(), this.getFrmDt(), this.getToDt(), getUserName(), getOrgnBrCode(), this.remark, this.drAcno, this.drAmt, this.crAcno, this.crAmt, this.accrAcno, this.accrAmt,intOpt);
            if (msg.subSequence(0, 4).equals("true")) {
                String trNo = msg.substring(4);
                this.ClearAll();
                this.setMessage("Interest Posted Successfully ! And Batch No is " + trNo);
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String acDescVal(String acno) {
        String acName = "";
        try {
            Gltable entityList1 = obj.getGltableByAcno(acno);
            if (entityList1 != null) {
                acName = entityList1.getAcName().toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return acName;
    }

    public void ClearAll() {
        this.setMessage("");
        this.setRepType("--Select--");       
        this.setCrAcno("");
        this.setCrAmt("");
        this.setDrAcno("");
        this.setDrAmt("");
        this.setVisiblePost(true);
        this.setCrHeadDesc("");
        this.setAccrHeadDesc("");
        this.setDrHeadDesc("");
        this.setAccrAcno("");
        this.setAccrAmt("");
        this.setFrmDt("");
        this.setToDt("");
        repDescList = new ArrayList<SelectItem>();
        ctrlNoList = new ArrayList<SelectItem>();
    }

    public String exitButton() {
        ClearAll();
        this.setMessage("");
        return "case1";
    }

    public void getReportTypeList() {
        try {
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("--Select--"));
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = obj.getGlRange("F");
            for (int i = 0; i < resultList.size(); i++) {
                GlDescRange entity = resultList.get(i);
                repTypeList.add(new SelectItem(entity.getGlname()));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getControlNoList() {
        try {
            ctrlNoList = new ArrayList<SelectItem>();
            ctrlNoList.add(new SelectItem("--SELECT--"));
            List<InvestmentFdrDetails> controlList = new ArrayList();
            controlList = obj.getControlList("ACTIVE");
            for (int i = 0; i < controlList.size(); i++) {
                ctrlNoList.add(new SelectItem(controlList.get(i)));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onCntlNoLostFocus() {
        if (!(this.getCtrlNoDd().equalsIgnoreCase("")) && !(this.getCtrlNoDd() == null)) {
            setMessage("");
            try {
                List<InvestmentFdrDates> obj1 = obj.onblurCntrlNumber(Integer.parseInt(this.getCtrlNoDd()));
                List<InvestmentFdrDetails> obj2 = obj.controlNumberDeatils(Integer.parseInt(this.getCtrlNoDd()));
                if (obj1.size() > 0 && obj2.size() > 0) {
                    InvestmentFdrDates investmentFdrDatesObj = (InvestmentFdrDates) obj1.get(0);
                    if (investmentFdrDatesObj.getLastIntPaidDt().compareTo(investmentFdrDatesObj.getPurchaseDt()) == 0) {
                        String newDate = CbsUtil.dateAdd(ymd.format(investmentFdrDatesObj.getPurchaseDt()), 0);
                        this.setFrmDt(newDate.substring(6) + "/" + newDate.substring(4, 6) + "/" + newDate.substring(0, 4));
                        this.setToDt(dmy.format(dmy.parse(getTodayDate())));
                    } else {
                        String newDate = CbsUtil.dateAdd(ymd.format(investmentFdrDatesObj.getIntPdt()), 1);
                        this.setFrmDt(newDate.substring(6) + "/" + newDate.substring(4, 6) + "/" + newDate.substring(0, 4));
                        if(investmentFdrDatesObj.getMatDt().compareTo(new Date()) > 0){
                            this.setToDt(dmy.format(dmy.parse(getTodayDate())));
                        }else{
                            this.setToDt(dmy.format(investmentFdrDatesObj.getMatDt()));
                        }
                    }
                }

            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        } else {
            this.setMessage("Select Control No!!! ");
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
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

    public List<SelectItem> getCtrlNoList() {
        return ctrlNoList;
    }

    public void setCtrlNoList(List<SelectItem> ctrlNoList) {
        this.ctrlNoList = ctrlNoList;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getCtrlNoDd() {
        return ctrlNoDd;
    }

    public void setCtrlNoDd(String ctrlNoDd) {
        this.ctrlNoDd = ctrlNoDd;
    }

    public String getDrAcno() {
        return drAcno;
    }

    public void setDrAcno(String drAcno) {
        this.drAcno = drAcno;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }

    public String getCrAcno() {
        return crAcno;
    }

    public void setCrAcno(String crAcno) {
        this.crAcno = crAcno;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isVisiblePost() {
        return visiblePost;
    }

    public void setVisiblePost(boolean visiblePost) {
        this.visiblePost = visiblePost;
    }

    public String getDrHeadDesc() {
        return drHeadDesc;
    }

    public void setDrHeadDesc(String drHeadDesc) {
        this.drHeadDesc = drHeadDesc;
    }

    public String getCrHeadDesc() {
        return crHeadDesc;
    }

    public void setCrHeadDesc(String crHeadDesc) {
        this.crHeadDesc = crHeadDesc;
    }

    public String getAccrHeadDesc() {
        return accrHeadDesc;
    }

    public void setAccrHeadDesc(String accrHeadDesc) {
        this.accrHeadDesc = accrHeadDesc;
    }

    public String getAccrAmt() {
        return accrAmt;
    }

    public void setAccrAmt(String accrAmt) {
        this.accrAmt = accrAmt;
    }

    public String getAccrAcno() {
        return accrAcno;
    }

    public void setAccrAcno(String accrAcno) {
        this.accrAcno = accrAcno;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public String getRepDesc() {
        return repDesc;
    }

    public void setRepDesc(String repDesc) {
        this.repDesc = repDesc;
    }

    public List<SelectItem> getRepDescList() {
        return repDescList;
    }

    public void setRepDescList(List<SelectItem> repDescList) {
        this.repDescList = repDescList;
    }

    public String getViewID() {
        return viewID;
    }

    public boolean getAccacnovisibility() {
        return accacnovisibility;
    }

    public void setAccacnovisibility(boolean accacnovisibility) {
        this.accacnovisibility = accacnovisibility;
    }
    
    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public double getpCrAmt() {
        return pCrAmt;
    }

    public void setpCrAmt(double pCrAmt) {
        this.pCrAmt = pCrAmt;
    }
    
}
