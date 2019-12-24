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
import com.cbs.pojo.GoiIntReportPojo;
import com.cbs.pojo.SortSecControlNo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Administrator
 */
public class SecAccrIntPost extends BaseBean  {
    private String message;
    private String secTpDd;
    private List<SelectItem> secTypeList;
    private boolean disTillDt;
    private String tilDate;
    private String controlNo;
    private BigDecimal txtReceivedAmount;
    private boolean disRecAmt;
    private BigDecimal txtAmount;
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
    private List<GoiIntReportPojo> tableList;
    private GoiIntReportPojo currentItem;
    private boolean disUpdate;
    private String btnHide;
    private boolean calcFlag;
    private boolean disPost;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private TdReceiptManagementFacadeRemote tdobj = null;
    private final String jndiName = "TdReceiptManagementFacade";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private InvestmentReportMgmtFacadeRemote remObj = null;
    private final String jndiHomeNameRep = "InvestmentReportMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdslash = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.00");
    private String viewID="/pages/master/sm/test.jsp";
    private String drHeadDesc;
    private String crHeadDesc;

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    
    public boolean isDisTillDt() {
        return disTillDt;
    }

    public void setDisTillDt(boolean disTillDt) {
        this.disTillDt = disTillDt;
    }

    public String getTilDate() {
        return tilDate;
    }

    public void setTilDate(String tilDate) {
        this.tilDate = tilDate;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public BigDecimal getTxtReceivedAmount() {
        return txtReceivedAmount;
    }

    public void setTxtReceivedAmount(BigDecimal txtReceivedAmount) {
        this.txtReceivedAmount = txtReceivedAmount;
    }

    public boolean isDisRecAmt() {
        return disRecAmt;
    }

    public void setDisRecAmt(boolean disRecAmt) {
        this.disRecAmt = disRecAmt;
    }

    public BigDecimal getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(BigDecimal txtAmount) {
        this.txtAmount = txtAmount;
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

    public GoiIntReportPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(GoiIntReportPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isDisUpdate() {
        return disUpdate;
    }

    public void setDisUpdate(boolean disUpdate) {
        this.disUpdate = disUpdate;
    }

    public String getBtnHide() {
        return btnHide;
    }

    public void setBtnHide(String btnHide) {
        this.btnHide = btnHide;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public boolean isDisPost() {
        return disPost;
    }

    public void setDisPost(boolean disPost) {
        this.disPost = disPost;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
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

    public List<GoiIntReportPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<GoiIntReportPojo> tableList) {
        this.tableList = tableList;
    }
    
    public SecAccrIntPost() {
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            tdobj = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            remObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameRep);
            tableList = new ArrayList<GoiIntReportPojo>();
            secTypeList = new ArrayList<SelectItem>();
            loadSecurityType();            
            this.setTilDate(getTodayDate());
            disUpdate = true;
            disPost = true;
            disRecAmt = true;            
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
    
    public void onBlurTillDate() {
        this.setMessage("");
        if (this.tilDate == null || this.tilDate.equals("") || this.tilDate.length() < 10) {
            this.setMessage("Please fill a proper till date !");
            return;
        }

        boolean var = new Validator().validateDate_dd_mm_yyyy(this.tilDate);
        if (var == false) {
            this.setMessage("Please fill a proper till date !");
            return;
        }        
        getInterestData();        
    }
    
    public void getInterestData() {
        calcFlag = true;
        this.setMessage("");
        Double totAmt = 0.0;
        tableList = new ArrayList<GoiIntReportPojo>();        
        try {
            List<Object[]> resultList = remObj.getAllInvestmentMasterSecurityMasterAccrPost(this.secTpDd, dmy.parse(this.tilDate));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    String intopt = "S";
                    Object[] obj = resultList.get(i);
                    InvestmentMaster im = (InvestmentMaster) obj[0];
                    InvestmentGoidates isam = (InvestmentGoidates) obj[1];
                    GoiIntReportPojo pojo = new GoiIntReportPojo();
//                    String fddt1 = getFdDt1(ism.getIntPayableFirstDate(), ism.getIntPayableSecondDate(), dmy.format(im.getTransdt()));
                    String fddt1 = getFdDt1(isam.getPurchasedt(), isam.getLastintpaiddt(), isam.getLastIntPaidDtAccr());  
                    String matDt = dmy.format(im.getMatdt());
                    //long period = CbsUtil.dayDiff(dmy.parse(fddt1), dmy.parse(this.tilDate)) + 1;
                    
                    // Old Code Commented Start 
//                    
//                    long period;
////                    if(period > 180){
////                        period = 180;
////                    }else{
//                        int mon = CbsUtil.monthDiff(dmy.parse(fddt1), dmy.parse(this.tilDate));
//                        int d1 = Integer.parseInt(fddt1.substring(0,2));
//                        int d2 = Integer.parseInt(this.tilDate.substring(0,2));
//                        if(d1 > d2){
//                            String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), (mon -1));
//                            period = ((mon -1) * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), dmy.parse(this.tilDate)));
//                        }else if(d1 == d2){
//                            period = (mon * 30);
//                        }else{
//                            String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), mon);
//                            period = (mon * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), dmy.parse(this.tilDate)));
//                        }
//                    //}
//                    if((period > 30) && (period < 35) ){
//                        period = 30;
//                    }
//                    if((period > 90) && (period < 95) ){
//                        period = 90;
//                    }
//                    if((period > 180) && (period < 185) ){
//                        period = 180;
//                    }
                    // Old Code Commented End 
                    
                    // New Code Start 
                    int period = 0;
                    
                    String ddFr = fddt1.substring(0, 2);
                    String monFr = fddt1.substring(3, 5);
                    
                    if((Integer.parseInt(ddFr) < 30)){
                        if(!((Integer.parseInt(monFr))==2 && (Integer.parseInt(ddFr)== 28 || Integer.parseInt(ddFr)== 29 ))){
                       //   period = 30 - Integer.parseInt(ddFr)+1 ; changed due to khattri  
                            period = 30 - Integer.parseInt(ddFr) ;
                        }
                    }
            
                    Date lastDtFrmDate = CbsUtil.getLastDateOfMonth(dmy.parse(fddt1));
                    String dta = CbsUtil.monthAdd(ymd.format(lastDtFrmDate),1);
                    while(ymd.parse(dta).compareTo(dmy.parse(this.tilDate))< 0){
                        period = period + 30;
                        lastDtFrmDate = CbsUtil.getLastDateOfMonth(ymd.parse(dta));
                        dta = CbsUtil.monthAdd(ymd.format(lastDtFrmDate),1);
                    }
            
                    if(dmy.parse(this.tilDate).compareTo(dmy.parse(matDt))< 0){
                        period = period + (int) CbsUtil.dayDiff(lastDtFrmDate, dmy.parse(this.tilDate));
                    }else{
                        period = period + (int) CbsUtil.dayDiff(lastDtFrmDate, dmy.parse(matDt)) - 1;
                    }
                    
                    // New Code End
                    
                    String dayPr = period + "Days";
                    String val = tdobj.orgFDInterestGoi(intopt, (float) im.getRoi(), ymd.format(dmy.parse(fddt1)), ymd.format(dmy.parse(this.tilDate)), im.getFacevalue(), dayPr, this.getOrgnBrCode());

                    pojo.setCtrlNo(im.getInvestmentMasterPK().getControlno());
                    pojo.setParticulars(im.getSecdesc());
                    pojo.setBookVal(formatter.format(im.getBookvalue()));
                    pojo.setSettleDt(dmy.format(im.getTransdt()));
                    pojo.setIpDate(fddt1);
                    pojo.setFaceVal(formatter.format(im.getFacevalue()));
                    pojo.setRoi(im.getRoi());
                    pojo.setPeriod(period);
                    //pojo.setIntAmt(formatter.format(Double.parseDouble(val) - isam.getAmtIntAccr()));
                    pojo.setIntAmt(formatter.format(Double.parseDouble(val)));
                    pojo.setYtm(im.getYtm());

                    tableList.add(pojo);
                    //totAmt = totAmt + (Double.parseDouble(val) - isam.getAmtIntAccr());
                    totAmt = totAmt + (Double.parseDouble(val));
                }
            } else {
                this.setMessage("Data does not exist !");
                return;
            }
                
            InvestmentCallHead entity = remoteObj.getInvestCallHeadByCode("14");
            if (entity != null) {
                this.setDrAcno(entity.getCrGlhead());
                this.setDrHeadDesc(acDescVal(this.getDrAcno()));
                this.setDrAmt(formatter.format(totAmt));
                this.setCrAcno(entity.getIntGlhead());
                this.setCrHeadDesc(acDescVal(this.getCrAcno()));
                this.setCrAmt(formatter.format(totAmt));
            } else {
                this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
            }
            disTillDt = true;            
        } catch (ApplicationException ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        }        
    }
    
    public int getIndex(List<GoiIntReportPojo> list, String ctrlNo) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCtrlNo()==(Integer.parseInt(ctrlNo))) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem != null) {
                this.setControlNo(Integer.toString(currentItem.getCtrlNo()));
                this.setTxtReceivedAmount(new BigDecimal (currentItem.getIntAmt()));
                this.setTxtAmount(new BigDecimal (currentItem.getIntAmt()));
                disRecAmt = false;
                disUpdate = false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void updateAction(){
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (this.txtReceivedAmount == null || this.txtReceivedAmount.toString().equalsIgnoreCase("")) {
                this.setMessage("Please fill amount field.");
                return;
            }

            Matcher amountTxnCheck = p.matcher(this.txtReceivedAmount.toString());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid amount.");
                return;
            }

            if (this.txtReceivedAmount.toString().contains(".")) {
                if (this.txtReceivedAmount.toString().indexOf(".") != this.txtReceivedAmount.toString().lastIndexOf(".")) {
                    this.setMessage("Invalid amount.Please fill the amount correctly.");
                    return;
                } else if (this.txtReceivedAmount.toString().substring(txtReceivedAmount.toString().indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please fill the amount upto two decimal places.");
                    return;
                }
            }
            InvestmentCallHead entity = remoteObj.getInvestCallHeadByCode("14");
            //String AcnoFdr = remoteObj.getGLByCtrlNo(Integer.parseInt(this.getControlNo()));
            
            if (this.txtReceivedAmount.compareTo(this.txtAmount)== -1) {
                BigDecimal totAmt = this.txtReceivedAmount.subtract(this.txtAmount);
                if (entity != null) {
                    this.setDrAcno(entity.getCrGlhead());
                    this.setDrAmt(formatter.format(new BigDecimal (this.getDrAmt()).add(totAmt)));
                    this.setCrAcno(entity.getIntGlhead());
                    this.setCrAmt(formatter.format(new BigDecimal (this.crAmt).add(totAmt)));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 14.");
                }
            } else {
                BigDecimal totAmt = this.txtAmount.subtract(this.txtReceivedAmount);
                if (entity != null) {
                    this.setDrAcno(entity.getIntGlhead());
                    this.setDrAmt(formatter.format(new BigDecimal (this.getDrAmt()).subtract(totAmt)));
                    this.setCrAcno(entity.getCrGlhead());
                    this.setCrAmt(formatter.format(new BigDecimal (this.crAmt).subtract(totAmt)));
                } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 6.");
                }
            }
            
            int index = getIndex(tableList, this.getControlNo());
            
            GoiIntReportPojo tempObj = tableList.get(index);
            tableList.remove(index);
            tempObj.setIntAmt(this.getTxtReceivedAmount().toString());
            tableList.add(tempObj);
            
            this.setControlNo("");
            this.setTxtReceivedAmount(new BigDecimal("0"));
            this.setTxtAmount(new BigDecimal("0"));
            disUpdate = true;  
            
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortSecControlNo());
            Collections.sort(tableList, chObj);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void print() {
        calcFlag = true;
        this.setMessage("");
        try {
            if (tableList.isEmpty()) {
                resetForm();
                this.setMessage("Data does not exist !");
                calcFlag = false;
            } else {
                List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
                if (bankDetailsList.size() > 0) {
                    String reportName = "Interest Received Post Gov. Sec";
                    String jrxmlName = "goiIntReport";
                    String bankName = bankDetailsList.get(0).toString();
                    String bankAddress = bankDetailsList.get(1).toString();
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", reportName);
                    fillParams.put("pReportDate", this.getTilDate());
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", bankAddress);        

                    new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(tableList), fillParams, reportName);
                    this.setViewID("/report/ReportPagePopUp.jsp"); 
                }
                disPost = false;
                disUpdate = true;
                btnHide = "none";
            }
        } catch (ApplicationException ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            calcFlag = false;
            this.setMessage(ex.getMessage());
        }
    }  
    
    public void postAction() {
        this.setMessage("");
        try {
            
            String msg = remoteObj.postSecAccrInt(tableList, Double.parseDouble(this.getDrAmt()), dmy.parse(this.tilDate), getUserName(), getOrgnBrCode());
            if (msg.equals("true")) {
                this.setMessage("Interest Posted Successfully !");
                resetSave();
                return;
            }
       } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void resetForm() {
        this.setMessage("");
        this.setTilDate(getTodayDate());
        this.setControlNo("");
        this.setTxtReceivedAmount(new BigDecimal("0"));
        this.setDrAcno("");
        this.setDrAmt("");
        this.setCrAcno("");
        this.setCrAmt("");
        this.setCrHeadDesc("");
        this.setDrHeadDesc("");
        tableList = new ArrayList<GoiIntReportPojo>();        
        disTillDt = false;
        disRecAmt = true;
        btnHide = "";
        disUpdate = true;
        disPost = true;
    }
    
    public void resetSave() {
        this.setTilDate(getTodayDate());
        this.setControlNo("");
        this.setTxtReceivedAmount(new BigDecimal("0"));
        this.setDrAcno("");
        this.setDrAmt("");
        this.setCrAcno("");
        this.setCrAmt("");
        tableList = new ArrayList<GoiIntReportPojo>();        
        disTillDt = false;
        disRecAmt = true;
        btnHide = "";
        disUpdate = true;
        disPost = true;
    }
    
    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
    
    public String acDescVal(String acno) {
        String acName = "";
        try {
            Gltable entityList1 = remoteObj.getGltableByAcno(acno);
            if (entityList1 != null) {
                acName = entityList1.getAcName().toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return acName;
    }

//public String getFdDt1(String dt1, String dt2, String trnDt) {
//        String fddt1 = "";
//        String fdt1 = dt1 + "/" + this.tilDate.substring(6);
//        String sdt1 = dt2 + "/" + this.tilDate.substring(6);        
//        try{
//            if(dmy.parse(fdt1).compareTo(dmy.parse(sdt1))<0){
//                fdt1 = fdt1;
//                sdt1 = sdt1;       
//            }else{
//                fdt1 = sdt1;
//                sdt1 = fdt1;
//            }
//            if(dmy.parse(this.tilDate).compareTo(dmy.parse(fdt1))<0){
//                String year = Integer.toString(Integer.parseInt(this.tilDate.substring(6)) - 1);
//                fdt1 = dt1 + "/" + year;
//                sdt1 = dt2 + "/" + year;
//                if(dmy.parse(this.tilDate).compareTo(dmy.parse(sdt1))<0){
//                    if(dmy.parse(trnDt).compareTo(dmy.parse(fdt1))<0){
//                        fddt1 = fdt1;
//                    }else{
//                        fddt1 = trnDt;
//                    }                    
//                }else{
//                    if(dmy.parse(trnDt).compareTo(dmy.parse(sdt1))<0){
//                        fddt1 = sdt1;
//                    }else{
//                        fddt1 = trnDt;
//                    }
//                }                
//            }else{
//                if(dmy.parse(this.tilDate).compareTo(dmy.parse(sdt1))>0){
//                    fddt1 = sdt1;
//                }else{
//                    fddt1 = fdt1;
//                }
//            }
//        } catch (Exception ex) {
//            this.setMessage(ex.getMessage());
//        }
//        return fddt1;
//    }
    public String getFdDt1(java.util.Date dt1, java.util.Date dt2, java.util.Date dt3) {
        String fddt1 = "";
        if((dt1.compareTo(dt2)==0) && (dt2.compareTo(dt3)==0)){
            fddt1 = dmy.format(dt1);
        }else{
            if(dt2.compareTo(dt3) < 0){
                fddt1 = CbsUtil.dateAdd(dmy.format(dt3), 1);
            }else{
                fddt1 = CbsUtil.dateAdd(dmy.format(dt2), 1);
            }
        }
        return fddt1;
    }

}
