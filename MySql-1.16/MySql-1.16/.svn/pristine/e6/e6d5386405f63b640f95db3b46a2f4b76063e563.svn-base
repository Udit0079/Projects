/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.other.AutoMandateTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.CustomerDetailsReportFacadeRemote;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class MandateReverification extends BaseBean {
    private String errMessage;
    private String mmsMode;
    private String mmsType;
    private String umrn;
    private String acno;
    private String newAcno;
    private String acNoLen;
    private String name;
    private String category;
    private String chiUmrn;
    //add by rahul
    private String opMode;
    //end
    private String status;
    private String reason;
    private boolean reasonDisable=true;
    private AutoMandateTo currentItem;
    private List<SelectItem> mmsModeList;
    private List<SelectItem> mmsTypeList;
    private List<SelectItem> seqNoList;
    private List<SelectItem> statusList;
    private List<SelectItem> reasonList;
    private List<AutoMandateTo> gridDetail;
    private NpciMandateFacadeRemote npciMandateRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote;
    private CustomerDetailsReportFacadeRemote remoteObject;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    Map map;

    public MandateReverification() {
        try{
           npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
           commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
           ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
           txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("TxnAuthorizationManagementFacade");
           remoteObject = (CustomerDetailsReportFacadeRemote) ServiceLocator.getInstance().lookup("CustomerDetailsReportFacade");
           this.setAcNoLen(getAcNoLength());
           initData();
       }catch(Exception ex){
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        mmsModeList = new ArrayList<>();
        //mmsTypeList = new ArrayList<SelectItem>();
        seqNoList = new ArrayList<>();
        statusList = new ArrayList<>();
        reasonList = new ArrayList<>();
        try {

            mmsModeList.add(new SelectItem("0", "--Select--"));
            mmsModeList.add(new SelectItem("N", "NEW"));
            mmsModeList.add(new SelectItem("L", "LEGACY"));
            mmsModeList.add(new SelectItem("E", "E-SIGN"));

            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("A", "ACCEPT"));
            statusList.add(new SelectItem("R", "REJECT"));

            setMap(); //For MMS Category
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }
    
     public void modeTypeOption() {
        mmsTypeList = new ArrayList<>();
        mmsTypeList.add(new SelectItem("0", "--Select--"));
        if (mmsMode.equalsIgnoreCase("N")) {
            mmsTypeList.add(new SelectItem("CREATE"));
            mmsTypeList.add(new SelectItem("AMEND"));
        } else if (mmsMode.equalsIgnoreCase("L")) {
            mmsTypeList.add(new SelectItem("CREATE"));
            mmsTypeList.add(new SelectItem("AMEND"));
        } else {
            mmsTypeList.add(new SelectItem("CREATE"));
            //mmsTypeList.add(new SelectItem("AMEND"));
        }
    }
    
    
    public void setMap() {
        try {
            map = new HashMap();
            List list = commonReport.getRefRecList("326");
            if (list.isEmpty()) {
                this.setErrMessage("Please fill MMS category.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                map.put(ele.get(0).toString().trim(), ele.get(1).toString().trim());
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }
    
    public void getgridDetails(){
        this.setErrMessage("");
        reasonList = new ArrayList<>();
        reasonList.add(new SelectItem("0", "--Select--"));
        try{
           String refRecNo = "";
            if (this.mmsType.equalsIgnoreCase("CREATE")) {
                refRecNo = "320";
            } else if (this.mmsType.equalsIgnoreCase("AMEND")) {
                refRecNo = "321";
            }
            List list = commonReport.getRefRecList(refRecNo);
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                reasonList.add(new SelectItem(ele.get(0).toString().trim(), ele.get(1).toString().trim()));
            }  
            
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setErrMessage("Please select mandate type.");
                return;
            }
             gridDetail = new ArrayList<>();
              List list1 = npciMandateRemote.getMandateReverifyDetails(ymd.format(dmy.parse(getTodayDate())),
                     this.mmsType, getOrgnBrCode(), this.mmsMode,this.chiUmrn);
             if (list1.isEmpty()) {
                this.setErrMessage("There is no data.");
                return;
            }
             for (int i = 0; i < list1.size(); i++) {
                Vector ele = (Vector) list1.get(i);
                AutoMandateTo obj = new AutoMandateTo();

                obj.setMsgId(ele.get(0).toString().trim());
                obj.setCreDtTm(ele.get(1).toString().trim());
                obj.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
                obj.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
                obj.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());
                obj.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
                obj.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
                obj.setMndtId(ele.get(7).toString().trim());
                obj.setMndtReqId(ele.get(8).toString().trim());

                obj.setTpSvcLvlPrtry(ele.get(9).toString().trim());
                obj.setTpLclInstrmPrtry(ele.get(10).toString().trim());
                obj.setOcrncsSeqTp(ele.get(11).toString().trim());
                obj.setOcrncsFrqcy(ele.get(12).toString().trim());
                obj.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
                obj.setOcrncsFnlColltnDt(ele.get(14).toString().trim());
                obj.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
                obj.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));

                obj.setCdtrNm(ele.get(17).toString().trim());
                obj.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
                obj.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());
                obj.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
                obj.setDbtrNm(ele.get(21).toString().trim());
                obj.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
                obj.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
                obj.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());
                obj.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
                obj.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
                obj.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());

                obj.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
                obj.setDbtrAcctTpPrtry(ele.get(29).toString().trim());
                obj.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
                obj.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
                obj.setMandateType(ele.get(32).toString().trim());
                String mandateStatusValue = ele.get(33).toString().trim();
                if (mandateStatusValue.equalsIgnoreCase("V")) {
                    obj.setMandateStatus("Verified");
                } else if (mandateStatusValue.equalsIgnoreCase("F")) {
                    obj.setMandateStatus("File Generated");
                } else {
                    obj.setMandateStatus("");
                }
                String acceptValue = ele.get(34).toString().trim();
                if (acceptValue.equalsIgnoreCase("A")) {
                    obj.setAccept("Accept");
                } else if (acceptValue.equalsIgnoreCase("R")) {
                    obj.setAccept("Reject");
                } else {
                    obj.setAccept("");
                }
                obj.setRejectCode(ele.get(35).toString().trim());
                obj.setImageName(ele.get(36).toString().trim());
                obj.setVerifyBy(ele.get(37).toString().trim());

                gridDetail.add(obj);
            }
         }catch(Exception ex){
            this.setErrMessage(ex.getMessage());
        }
    }
    
    public void makeReasonEnable(){
        try{
            this.setErrMessage("");
            List accptList = npciMandateRemote.getMandateAcceptStatus(currentItem.getMndtId(),currentItem.getMandateType(),this.mmsMode);
            Vector vec = (Vector) accptList.get(0);
            String statusMark = vec.get(0).toString();
            if(statusMark.equalsIgnoreCase("A")){
            if(statusMark.equals(this.status)){
                this.setErrMessage("This mandate is already accept .Please change the Status .");
                return;
            }
            }else{
                if(statusMark.equals(this.status)){
                    this.setErrMessage("This mandate is already rejected .Please change the status.");
                    return;
                }
            }
             this.setReasonDisable(true);
            if (this.status.equalsIgnoreCase("R")) {
                this.setReasonDisable(false);
            }
         }catch(Exception ex){
            this.setErrMessage(ex.getMessage());
        }
    }
    
    
    public void setTableDataToForm() {
        try {
            this.setErrMessage("");
            this.setUmrn("");
            this.setAcno("");
            this.setNewAcno("");
            this.setName("");
            this.setCategory("");
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table.");
                return;
            }
            this.setUmrn(currentItem.getMndtId());
            this.setCategory(map.get(currentItem.getTpSvcLvlPrtry().trim()).toString());
            this.setAcno(currentItem.getDbtrAcctIdOthrId());
            
           
            //Setting signature
            if (mmsMode.equalsIgnoreCase("N")) {
                System.out.println("New MMS Signature A/c-->" + this.acno);
                String cbsAccountNo = this.acno;
                try {
                    cbsAccountNo = ftsRemote.getNewAccountNumber(this.acno);
                } catch (Exception ex) {
                    throw new Exception("New Account Not Found For Acno-->" + this.acno);
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }
    
    
     public void retrieveAcnoDetail() {
        try {
            this.setName("");
            this.setNewAcno("");
            List getacopMode;

            if (this.acno == null || this.acno.equals("")) {
                this.setErrMessage("A/c No can not be blank.");
                return;
            }
            if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                this.setErrMessage("Please fill proper Account Number.");
                return;
            }
            this.newAcno = ftsRemote.getNewAccountNumber(acno);
            String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphacode.equalsIgnoreCase("HO")) {
                if (!ftsRemote.getCurrentBrnCode(this.newAcno).equalsIgnoreCase(getOrgnBrCode())) {
                    this.setErrMessage("Please fill self branch A/c Number.");
                    return;
                }
            }
            String result = ftsRemote.ftsAcnoValidate(this.newAcno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMessage(result);
                return;
            }
            this.setName(ftsRemote.getCustName(this.newAcno));
           
            //add by rahul
            getacopMode = commonReport.getAcopMode(this.newAcno);
            Vector opmode = (Vector) getacopMode.get(0);
            opMode = opmode.get(0).toString();
            this.setOpMode(commonReport.selectForOperationMode(this.opMode));
            //end

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }
    
    
    public void processAction(){
        try{
            if(this.mmsType.equalsIgnoreCase("")|| this.mmsType.equalsIgnoreCase("0") || this.mmsType==null){
                this.setErrMessage("Please select the mandate type.");
                return;
            }
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table.");
                return;
            }
            if (this.status == null || this.status.equals("0") || this.status.equals("")) {
                this.setErrMessage("Please select status.");
                return;
            }
            if (this.status.equalsIgnoreCase("R") && (this.reason == null || this.reason.equals("0") || this.reason.equals(""))) {
                this.setErrMessage("In case of reject, reason is mandatory.");
                return;
            }
            if (this.status.equalsIgnoreCase("A")) {
                this.reason = "";
            }
            if (mmsMode.equalsIgnoreCase("N")) {
                this.acno = "";
            } else if (mmsMode.equalsIgnoreCase("L") || mmsMode.equalsIgnoreCase("E")) {
                //In case of CCBL Legacy Verification is stopped from branches.
                int code = ftsRemote.getCodeForReportName("LEG-MANDATE-VERIFY-MODE");
                String alphaCode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphaCode.equalsIgnoreCase("HO") && code == 0) {
                    this.setErrMessage("Legacy verification can not be process from branches.");
                    return;
                }

                if (status.equalsIgnoreCase("A")) {
                    if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                        this.setErrMessage("Please fill proper Account Number.");
                        return;
                    }
                    if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                        this.setErrMessage("There should be actual a/c no.");
                        return;
                    }
                    String result = ftsRemote.ftsAcnoValidate(this.newAcno, 1, "");
                    if (!result.equalsIgnoreCase("true")) {
                        this.setErrMessage(result);
                        return;
                    }
                }
            }
            List accptList = npciMandateRemote.getMandateAcceptStatus(currentItem.getMndtId(),currentItem.getMandateType(),this.mmsMode);
            Vector vec = (Vector) accptList.get(0);
            String statusMark = vec.get(0).toString();
            if(statusMark.equalsIgnoreCase("A")){
            if(statusMark.equals(this.status)){
                this.setErrMessage("This mandate is already accept .Please change the Status .");
                return;
            }
            }else{
                if(statusMark.equals(this.status)){
                    this.setErrMessage("This mandate is already rejected .Please change the status.");
                    return;
                }
            }
           String msg = npciMandateRemote.reVerifymandate(currentItem, this.status, this.reason,
                    ymd.format(dmy.parse(getTodayDate())), this.mmsType,getUserName(),this.mmsMode, this.newAcno);
            if (!msg.equalsIgnoreCase("true")) {
                this.setErrMessage(msg);
                return;
            }
            this.setUmrn("");
            this.setCategory("");
            this.setAcno("");
            this.setNewAcno("");
            this.setName("");
            this.chiUmrn="";
            this.setErrMessage("Entry has been verified successfully.");
            
        }catch(Exception ex){
            this.setErrMessage(ex.getMessage());
        }
    }
    
     public void refreshForm() {
        this.setErrMessage("");
        this.setMmsType("0");
        seqNoList = new ArrayList<SelectItem>();
        this.setUmrn("");
        this.setCategory("");
        this.setAcno("");
        this.setName("");
        this.setChiUmrn("");
        this.setStatus("0");
        this.setOpMode("");
        reasonList = new ArrayList<SelectItem>();
        this.gridDetail = new ArrayList<AutoMandateTo>();
        this.currentItem = null;
        this.newAcno = "";
        this.setMmsMode("0");
        
        
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
     
     
    
    
    
    
    
    
    
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getMmsMode() {
        return mmsMode;
    }

    public void setMmsMode(String mmsMode) {
        this.mmsMode = mmsMode;
    }

    public String getMmsType() {
        return mmsType;
    }

    public void setMmsType(String mmsType) {
        this.mmsType = mmsType;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AutoMandateTo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AutoMandateTo currentItem) {
        this.currentItem = currentItem;
    }

    public List<SelectItem> getMmsModeList() {
        return mmsModeList;
    }

    public void setMmsModeList(List<SelectItem> mmsModeList) {
        this.mmsModeList = mmsModeList;
    }

    public List<SelectItem> getMmsTypeList() {
        return mmsTypeList;
    }

    public void setMmsTypeList(List<SelectItem> mmsTypeList) {
        this.mmsTypeList = mmsTypeList;
    }

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public List<SelectItem> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<SelectItem> reasonList) {
        this.reasonList = reasonList;
    }

    public List<AutoMandateTo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AutoMandateTo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NpciMandateFacadeRemote getNpciMandateRemote() {
        return npciMandateRemote;
    }

    public void setNpciMandateRemote(NpciMandateFacadeRemote npciMandateRemote) {
        this.npciMandateRemote = npciMandateRemote;
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

    public TxnAuthorizationManagementFacadeRemote getTxnAuthRemote() {
        return txnAuthRemote;
    }

    public void setTxnAuthRemote(TxnAuthorizationManagementFacadeRemote txnAuthRemote) {
        this.txnAuthRemote = txnAuthRemote;
    }

    public CustomerDetailsReportFacadeRemote getRemoteObject() {
        return remoteObject;
    }

    public void setRemoteObject(CustomerDetailsReportFacadeRemote remoteObject) {
        this.remoteObject = remoteObject;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isReasonDisable() {
        return reasonDisable;
    }

    public void setReasonDisable(boolean reasonDisable) {
        this.reasonDisable = reasonDisable;
    }

    public String getChiUmrn() {
        return chiUmrn;
    }

    public void setChiUmrn(String chiUmrn) {
        this.chiUmrn = chiUmrn;
    }
    
}
