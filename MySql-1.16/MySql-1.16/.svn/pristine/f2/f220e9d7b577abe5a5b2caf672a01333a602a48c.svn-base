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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class Archieving extends BaseBean {

    private String message;
    private String mmsMode;
    private String mmsType;
    private String fileUpDt;
    private String seqNo;
    private String imageName;
    private String function;
    private List<SelectItem> functionList;
    private String acNo;
    private String acNoLen;
    private String newAcno;
    private String oldAcno;
    private NpciMandateFacadeRemote npciMandateRemote = null;
    private CommonReportMethodsRemote reportRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote;
    private CustomerDetailsReportFacadeRemote remoteObject;
    private AutoMandateTo currentItem;
    private List<AutoMandateTo> gridDetail;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private List<AutoMandateTo> gridDetailFinal;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AutoMandateTo> getGridDetailFinal() {
        return gridDetailFinal;
    }

    public void setGridDetailFinal(List<AutoMandateTo> gridDetailFinal) {
        this.gridDetailFinal = gridDetailFinal;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public AutoMandateTo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AutoMandateTo currentItem) {
        this.currentItem = currentItem;
    }

    public List<AutoMandateTo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AutoMandateTo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NpciMandateFacadeRemote getNpciFacade() {
        return npciMandateRemote;
    }

    public void setNpciMandateRemote(NpciMandateFacadeRemote npciFacade) {
        this.npciMandateRemote = npciFacade;
    }

    public CommonReportMethodsRemote getReportRemote() {
        return reportRemote;
    }

    public void setReportRemote(CommonReportMethodsRemote reportRemote) {
        this.reportRemote = reportRemote;
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

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFileUpDt() {
        return fileUpDt;
    }

    public void setFileUpDt(String fileUpDt) {
        this.fileUpDt = fileUpDt;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Archieving() {
        try {
            npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("TxnAuthorizationManagementFacade");
            remoteObject = (CustomerDetailsReportFacadeRemote) ServiceLocator.getInstance().lookup("CustomerDetailsReportFacade");
            this.setAcNoLen(getAcNoLength());
            getList();
            refreshForm();
            gridDetail = new ArrayList<>();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getList() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("IM", "Inward Mms"));
            //  functionList.add(new SelectItem("IL","Inward Legacy"));
            //  functionList.add(new SelectItem("CT","CTS"));
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void gridload() {
        try {
            this.setMessage("");
            this.setNewAcno("");
            this.setOldAcno("");
            gridDetail = new ArrayList<>();
            if (function == null || function.equalsIgnoreCase("S")) {
                setMessage("Please select mode");
                return;
            }
            if (this.acNo == null || this.acNo.equals("")) {
                this.setMessage("A/c No can not be blank.");
                return;
            }
            if (!(this.acNo.length() == 12 || (this.acNo.length() == Integer.parseInt(getAcNoLen())))) {
                this.setMessage("Please fill proper Account Number.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher mt = p.matcher(acNo);
            if (!mt.matches()) {
                setMessage("please fill the numeric value of Ac No.");
                return;
            }
            this.newAcno = ftsRemote.getNewAccountNumber(acNo);
            this.oldAcno = ftsRemote.getOldAccountNumber(acNo);

            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO") && !getOrgnBrCode().equalsIgnoreCase(reportRemote.getBrncodeByAcno(newAcno))) {
                this.setMessage("Please fill your branch account no.");
                return;
            }

            gridDetail = npciMandateRemote.getArchievingDetails(this.oldAcno, this.newAcno, this.function);
            if (gridDetail.isEmpty()) {
                this.setMessage("There is no data to show the detail");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getWhiteImage() {
        try {
            if (currentItem == null) {
                this.setMessage("please select the row from the table");
                return;
            }
            this.imageName = currentItem.getImageName() + "_front.png";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void getGrayImage() {
        try {
            if (currentItem == null) {
                this.setMessage("please select the row from the table");
                return;
            }
            this.imageName = currentItem.getImageName() + "_detailfront.jpg";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem == null) {
                this.setMessage("please select the row from the table");
                return;
            }
            this.imageName = currentItem.getImageName() + "_detailfront.jpg";
            this.setSeqNo(currentItem.getFileNo());
            this.setMmsType(currentItem.getMandateType());
            this.setFileUpDt(currentItem.getUploadDate());
            this.setMmsMode(currentItem.getMandateMode());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        try {
            this.setMessage("");
            this.setAcNo("");
            this.setNewAcno("");
            this.setOldAcno("");
            this.setFunction("--Select--");
            this.setFileUpDt(getTodayDate());
            this.gridDetail = new ArrayList<AutoMandateTo>();
            this.currentItem = null;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
