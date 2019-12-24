/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.sms;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.SmsManagementDelegate;
import com.cbs.web.pojo.sms.AcSenderMsgPojo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class AccountSmsDetail extends BaseBean {
    private String errMsg;
    private String detailType;
    private String acno, acNoLen;
    private String frDt;
    private String toDt;
    private List<SelectItem> detailTypeList;
    private List<AcSenderMsgPojo> gridDetail;
    private CommonReportMethodsRemote commonReport;
    private MessageDetailBeanRemote msgDetailRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private Validator validator;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    
    public AccountSmsDetail(){
        detailTypeList = new ArrayList<SelectItem>();
        try{
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            msgDetailRemote = (MessageDetailBeanRemote) ServiceLocator.getInstance().lookup("MessageDetailBean");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            initData();
            refresh();
        }catch(Exception ex){
            this.setErrMsg(ex.getMessage());
        }
    }
    
   public void initData(){
       try{
           detailTypeList.add(new SelectItem("0","--Select--"));
           detailTypeList.add(new SelectItem("DL","Delievered"));
           detailTypeList.add(new SelectItem("UL", "Undelievered"));
       }catch(Exception ex){
           this.setErrMsg(ex.getMessage());
       }
   } 
   
   public void deatilTypeAction(){
       if (this.detailType == null || this.detailType.equals("--Select--")) {
                this.setErrMsg("Please Select detail Type.");
                return;
            }
   }
   
   public void accountAction(){
       this.setErrMsg("");
       try{
        if (this.acno == null || this.acno.equalsIgnoreCase("")) {
                this.setErrMsg("Account number should not be blank.");
                return;
            }
            //if (this.acno.length() < 12) {
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrMsg("Please fill 12 digits valid acoount number.");
                return;
            }
            if (!validator.validateStringAll(this.acno)) {
                this.setErrMsg("Please fill 12 digits valid account number.");
                return;
            }
            String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphacode.equalsIgnoreCase("HO")) {
                if (!(ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode()))) {
                    this.setErrMsg("Please fill your branch account number.");
                    return;
                }
            }
            String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMsg(result);
                return;
            }
            SmsManagementDelegate delegate = new SmsManagementDelegate();
            result = delegate.checkSubscriberDetails(acno);
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMsg("This account number is not registered for sms.");
                return;
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
   }
   
   public void btnHtmlAction(){
         gridDetail = new ArrayList<AcSenderMsgPojo>();
       try{
           if(vaidateField()){
               gridDetail= msgDetailRemote.getAccountSmsDetail(this.frDt, this.toDt, this.acno, this.detailType);
                 if (gridDetail.isEmpty()) {
                    this.setErrMsg("There is no data found");
                    } else {
                    this.setErrMsg("The Message Detail in the grid. Please Check .");
                }
           }
       }catch(Exception ex){
           this.setErrMsg(ex.getMessage());
       }
   }
   
   public boolean vaidateField(){
       try{
       if (this.detailType == null || this.detailType.equals("--Select--")) {
                this.setErrMsg("Please Select Report Type.");
                return false;
            }
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrMsg("Please fill 12 digits valid acoount number.");
                    return false;
                }
                String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphacode.equalsIgnoreCase("HO")) {
                    if (!(ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode()))) {
                        this.setErrMsg("Please fill your branch account number.");
                        return false;
                    }
                }
                String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMsg(result);
                    return false;
                }
                SmsManagementDelegate delegate = new SmsManagementDelegate();
                result = delegate.checkSubscriberDetails(acno);
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMsg("This account number is not registered for sms.");
                    return false;
                }
                 if (this.frDt == null || this.frDt.equals("")) {
                    this.setErrMsg("Please Fill From Date.");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.frDt)) {
                    this.setErrMsg("Please Fill Proper From Date.");
                    return false;
                }
                if (this.toDt == null || this.toDt.equals("")) {
                    this.setErrMsg("Please Fill To Date.");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.toDt)) {
                    this.setErrMsg("Please Fill Proper To Date.");
                    return false;
                }
            
       }catch(Exception ex){
           this.setErrMsg(ex.getMessage());
       }
       
       
       return true;
   }
   
   public void refresh(){
       this.setErrMsg("");
       this.setFrDt(getTodayDate());
       this.setToDt(getTodayDate());
       this.setDetailType("0");
       this.setAcno("");
       gridDetail = new ArrayList<AcSenderMsgPojo>();
       this.setGridDetail(null);
    }
    
     public String exit() {
        refresh();
        return "case1";
    }
    
    
    
    
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

     public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<SelectItem> getDetailTypeList() {
        return detailTypeList;
    }

    public void setDetailTypeList(List<SelectItem> detailTypeList) {
        this.detailTypeList = detailTypeList;
    }

    public CommonReportMethodsRemote getCommonReport() {
        return commonReport;
    }

    public void setCommonReport(CommonReportMethodsRemote commonReport) {
        this.commonReport = commonReport;
    }

    public FtsPostingMgmtFacadeRemote getFtsRemote() {
        return ftsRemote;
    }

    public void setFtsRemote(FtsPostingMgmtFacadeRemote ftsRemote) {
        this.ftsRemote = ftsRemote;
    }

    public MessageDetailBeanRemote getMsgDetailRemote() {
        return msgDetailRemote;
    }

    public void setMsgDetailRemote(MessageDetailBeanRemote msgDetailRemote) {
        this.msgDetailRemote = msgDetailRemote;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public List<AcSenderMsgPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AcSenderMsgPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }
  }
