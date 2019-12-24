/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.utils.CbsUtil;
import com.cbs.web.controller.ReportBean;
import com.cbs.pojo.GoiIntReportPojo;
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
 * @author sipl
 */
public class SecurityIntPost extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;    
    private List<SelectItem> monthList;    
    private boolean visiblePost;
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
    private String ctrlNoDd;
    private String frmDt;
    private String toDt;
    private String remark;
    private List<SelectItem> ctrlNoList;
    private InvestmentMgmtFacadeRemote obj = null;
    private final String jndiName = "InvestmentMgmtFacade";
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private TdReceiptManagementFacadeRemote tdobj = null;
    private final String tdJndiName = "TdReceiptManagementFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    List<GoiIntReportPojo> reportList = new ArrayList<GoiIntReportPojo>();
    NumberFormat formatter = new DecimalFormat("#.00");  
    private String viewID="/pages/master/sm/test.jsp";
    private boolean calcFlag;
    private String drHeadDesc;
    private String crHeadDesc;
    private String accrHeadDesc;
    private String accrAmt;
    private String accrAcno;
    private String repDesc;
    private List<SelectItem> repDescList;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public boolean isVisiblePost() {
        return visiblePost;
    }

    public void setVisiblePost(boolean visiblePost) {
        this.visiblePost = visiblePost;
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

    public String getCtrlNoDd() {
        return ctrlNoDd;
    }

    public void setCtrlNoDd(String ctrlNoDd) {
        this.ctrlNoDd = ctrlNoDd;
    }

    public List<SelectItem> getCtrlNoList() {
        return ctrlNoList;
    }

    public void setCtrlNoList(List<SelectItem> ctrlNoList) {
        this.ctrlNoList = ctrlNoList;
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
    
    public SecurityIntPost() {
        this.setMessage("");
        repTypeList = new ArrayList<SelectItem>();        
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            tdobj = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(tdJndiName);
            getReportTypeList();
            ClearAll();
            this.setVisiblePost(true);
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getReportTypeList() {
        try {
            repTypeList.add(new SelectItem("--Select--"));
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = obj.getGlRange("G");
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
    
    public void ClearAll() {
        this.setMessage("");
        this.setRepType("--Select--");
        this.setRepDesc("");
        this.setCrAcno("");
        this.setCrAmt("");
        this.setDrAcno("");
        this.setDrAmt("");
        this.setVisiblePost(true);
        this.setCrHeadDesc("");
        this.setAccrHeadDesc("");
        this.setAccrAcno("");
        this.setAccrAmt("");
        repDescList = new ArrayList<SelectItem>();
        ctrlNoList = new ArrayList<SelectItem>();
    }
    
    public void onBlurValAction() {
        this.setDrAmt(String.valueOf(formatter.format(Double.parseDouble(this.getCrAmt()) + Double.parseDouble(this.getAccrAmt()))));        
    }
    
    public void calTxn() {
        calcFlag = true;
        reportList = new ArrayList<GoiIntReportPojo>();
        this.setMessage("");
        if (this.repType.equals("--Select--")) {
            this.setMessage("Please select Security type !");
            calcFlag = false;
            return;
        }
        //validateTillDate();
        /***Calculate the max days ****/
        try {            
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            String maxDt = this.getToDt();            
            Object[] obj1 = obj.blurOnControllNo(Integer.parseInt(this.getCtrlNoDd()), this.getRepType());
            Double totAmt = 0.0,postedAccr=0.0;
            if (obj1.length > 0) {            
                InvestmentMaster investmentMasterObj = (InvestmentMaster) obj1[1];
                InvestmentGoidates investmentGoidatesObj = (InvestmentGoidates) obj1[0];
                    String intopt = "S";
                    if (investmentMasterObj != null) {
                        GoiIntReportPojo pojo = new GoiIntReportPojo();
                        String fddt1 = this.getFrmDt();
                        //long period = CbsUtil.dayDiff(dmy.parse(fddt1), dmy.parse(maxDt));
                        long period;
//                        if(period > 180){
//                            period = 180;
//                        }else{
                            int mon = CbsUtil.monthDiff(dmy.parse(fddt1), dmy.parse(maxDt));
                            int d1 = Integer.parseInt(fddt1.substring(0,2));
                            int d2 = Integer.parseInt(maxDt.substring(0,2));
                            if(d1 > d2){
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), (mon -1));
                                period = ((mon -1) * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), dmy.parse(maxDt)));
                            }else if(d1 == d2){
                                period = (mon * 30);
                            }else{
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), mon);
                                period = (mon * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), dmy.parse(maxDt)));
                            }
                        //}
                        String dayPr = period + "Days";
                        String val = tdobj.orgFDInterestGoi(intopt, (float) investmentMasterObj.getRoi(), ymd.format(dmy.parse(fddt1)), ymd.format(dmy.parse(maxDt)), investmentMasterObj.getFacevalue(), dayPr, this.getOrgnBrCode());
                        postedAccr = investmentGoidatesObj.getTotAmtIntAccr();
                        
                        pojo.setCtrlNo(investmentMasterObj.getInvestmentMasterPK().getControlno());
                        pojo.setParticulars(investmentMasterObj.getSecdesc());
                        pojo.setBookVal(formatter.format(investmentMasterObj.getBookvalue()));
                        pojo.setSettleDt(dmy.format(investmentMasterObj.getSettledt()));
                        pojo.setIpDate(fddt1);
                        pojo.setFaceVal(formatter.format(investmentMasterObj.getFacevalue()));
                        pojo.setRoi(investmentMasterObj.getRoi());
                        pojo.setPeriod(period);
                        pojo.setIntAmt(formatter.format(Double.parseDouble(val) - postedAccr));
                        pojo.setYtm(investmentMasterObj.getYtm());

                        reportList.add(pojo);
                        totAmt = totAmt + (Double.parseDouble(val) - postedAccr);
                    }
                this.setVisiblePost(false);
            } else {
                this.setMessage("Data does not exist !");
                calcFlag = false;
                return;
            }

            InvestmentCallHead entity = obj.getInvestCallHeadByCode("14");
            if (entity != null) {
                this.setDrAcno(entity.getDrGlhead());
                this.setDrHeadDesc(acDescVal(entity.getDrGlhead()));
                //this.setDrAmt(formatter.format(totAmt));
                this.setDrAmt(formatter.format(postedAccr + totAmt));
                this.setCrAcno(entity.getIntGlhead());
                this.setCrHeadDesc(acDescVal(entity.getIntGlhead()));
                this.setCrAmt(formatter.format(totAmt));
                this.setAccrAcno(entity.getCrGlhead());
                this.setAccrHeadDesc(acDescVal(entity.getCrGlhead()));
                this.setAccrAmt(postedAccr.toString());
            } else {
                this.setMessage("Data is not found in Investment Call Head Table for Code 6.");                
            }

            Map fillParams = new HashMap();
            String repName = "GOI Interest Report";
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", brList.get(0));
            fillParams.put("pBranchAddress", brList.get(1));
            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", dmy.format(dmy.parse(maxDt)));

            new ReportBean().jasperReport("goiIntReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            this.setViewID("/report/ReportPagePopUp.jsp"); 
        } catch (ApplicationException ex) {
            calcFlag = false;
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        }
    }
    
    public void postTxn() {
        this.setMessage("");
        try {
            if(dmy.parse(this.getToDt()).compareTo(dmy.parse(dmy.format(dt)))>0){
                this.setMessage("To Date Can't Be Greater Current Date");
                return;
            }
            String msg = obj.postIntRecSec(reportList, dmy.parse(this.getToDt()),this.crAcno,this.crAmt, this.drAcno, this.drAmt, getUserName(), getOrgnBrCode(),this.remark,this.getAccrAcno(), this.getAccrAmt());
            if (msg.subSequence(0,4).equals("true")) {
                String trNo = msg.substring(4);
                this.setMessage("Interest Posted Successfully ! And Batch No is " + trNo);
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public String exitButton() {
        ClearAll();
        this.setMessage("");
        return "case1";
    } 
    
    public void optTypeLostFocus() {
        try {
            repDescList = new ArrayList<SelectItem>();
            repDescList.add(new SelectItem(""));
            List<String> entityList = obj.getSecurityDesc(this.repType);
            if (!entityList.isEmpty()) {
                for (String entity : entityList) {
                    repDescList.add(new SelectItem(entity));
                }
            } else {
                this.setMessage("There is no Security Description !");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void optDescLostFocus() {
        try {
            ctrlNoList = new ArrayList<SelectItem>();
            List<InvestmentGoidates> entityList = obj.getControllNoSecWise(this.repType, this.getRepDesc());
            if (!entityList.isEmpty()) {
                for (InvestmentGoidates entity : entityList) {
                    ctrlNoList.add(new SelectItem(entity.getCtrlno()));
                }
            } else {
                this.setMessage("There is no control no !");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
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
    
    public void ctrlNoLostFocus() {
        String ipDt1="",ipDt2="";
        this.setMessage("");
        if((this.getCtrlNoDd()==null) || (this.getCtrlNoDd().equalsIgnoreCase(""))){
            this.setMessage("Please Select Control No");
            return;
        }
        try {             
            Object[] obj1 = obj.blurOnControllNo(Integer.parseInt(this.getCtrlNoDd()), this.repType);
            if (obj1.length > 0) {
                InvestmentGoidates investmentGoidatesObj = (InvestmentGoidates) obj1[0];
                
                if(investmentGoidatesObj.getPurchasedt().compareTo(investmentGoidatesObj.getLastintpaiddt())==0){
                    ipDt1 = dmy.format(investmentGoidatesObj.getPurchasedt());
                    ipDt2 = ymd.format(dmy.parse(dmy.format(investmentGoidatesObj.getIntpdt2())));
                } else if(investmentGoidatesObj.getPurchasedt().compareTo(investmentGoidatesObj.getLastintpaiddt())> 0){
                    ipDt1 = dmy.format(investmentGoidatesObj.getLastintpaiddt());
                    ipDt2 = ymd.format(dmy.parse(dmy.format(investmentGoidatesObj.getIntpdt1())));
                } else{
                    ipDt1 = dmy.format(investmentGoidatesObj.getLastintpaiddt());
                    ipDt2 = CbsUtil.monthAdd(ymd.format(dmy.parse(ipDt1)), 6);
                }
                this.setFrmDt(ipDt1);
                this.setToDt(ipDt2.substring(6)+"/"+ipDt2.substring(4,6)+"/"+ipDt2.substring(0,4));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}