/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.txn.XferDetailsItem;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Page bean that corresponds to a similarly named JSP page. This class
 * contains component definitions (and initialization code) for all components
 * that you have defined on this page, as well as lifecycle methods and event
 * handlers where you may add behavior to respond to incoming events.</p>
 *
 * @version XferAuthorization.java
 * @version Created on May 30, 2010, 5:41:23 PM
 * @author Administrator
 */
public class XferAuthorization {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    //private InterestCodeMasterRemote InterestCodeMasterRemote;
    private String orgnBrCode;
    private HttpServletRequest req;
    private String todayDate;
    private String userName;
    private String trsNo;
    private String message;
    private String errorMsg;
    private List<XferDetailsItem> xferDetailsItemList;
    private List<XferDetailsItem> selectedItemList;
//    String msg;
    String accNo;
    String imageData;
    int currentRow;
    XferDetailsItem currentItem;
    boolean openPopUp;
    BigDecimal crAmount;
    BigDecimal drAmount;
    String crdrFlag = "none";
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    BigDecimal openBalance;
    private BigDecimal presentBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    String accountNo;
    String accountName;
    String opMode;
    String openDate;
    String jtName;
    String accInstruction;
    private final String jndiHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiRemoteName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanRemote = null;
    private final String jndiFtsName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private String acViewJtName;
    private String acViewNominee;
    private String adNo;
    private String adBrName;
    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String dpLimit;
    private PostalDetailFacadeRemote postalRemote = null;

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
    }

    public String getAdBrName() {
        return adBrName;
    }

    public void setAdBrName(String adBrName) {
        this.adBrName = adBrName;
    }

    public String getAdNo() {
        return adNo;
    }

    public void setAdNo(String adNo) {
        this.adNo = adNo;
    }

    public String getAcViewJtName() {
        return acViewJtName;
    }

    public void setAcViewJtName(String acViewJtName) {
        this.acViewJtName = acViewJtName;
    }

    public String getAcViewNominee() {
        return acViewNominee;
    }

    public void setAcViewNominee(String acViewNominee) {
        this.acViewNominee = acViewNominee;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<XferDetailsItem> getSelectedItemList() {
        return selectedItemList;
    }

    public void setSelectedItemList(List<XferDetailsItem> selectedItemList) {
        this.selectedItemList = selectedItemList;
    }

    public String getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(String trsNo) {
        this.trsNo = trsNo;
    }

    public List<XferDetailsItem> getXferDetailsItemList() {
        return xferDetailsItemList;
    }

    public void setXferDetailsItemList(List<XferDetailsItem> xferDetailsItemList) {
        this.xferDetailsItemList = xferDetailsItemList;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public XferDetailsItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(XferDetailsItem currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isOpenPopUp() {
        return openPopUp;
    }

    public void setOpenPopUp(boolean openPopUp) {
        this.openPopUp = openPopUp;
    }

    public BigDecimal getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(BigDecimal crAmount) {
        this.crAmount = crAmount;
    }

    public String getCrdrFlag() {
        return crdrFlag;
    }

    public void setCrdrFlag(String crdrFlag) {
        this.crdrFlag = crdrFlag;
    }

    public BigDecimal getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(BigDecimal drAmount) {
        this.drAmount = drAmount;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getPresentBalance() {
        return presentBalance;
    }

    public void setPresentBalance(BigDecimal presentBalance) {
        this.presentBalance = presentBalance;
    }

    public TxnDetailBean getTxnDetailGrid() {
        return txnDetailGrid;
    }

    public void setTxnDetailGrid(TxnDetailBean txnDetailGrid) {
        this.txnDetailGrid = txnDetailGrid;
    }

    public List<TxnDetailBean> getTxnDetailList() {
        return txnDetailList;
    }

    public void setTxnDetailList(List<TxnDetailBean> txnDetailList) {
        this.txnDetailList = txnDetailList;
    }

    public TxnDetailBean getTxnDetailUnAuthGrid() {
        return txnDetailUnAuthGrid;
    }

    public void setTxnDetailUnAuthGrid(TxnDetailBean txnDetailUnAuthGrid) {
        this.txnDetailUnAuthGrid = txnDetailUnAuthGrid;
    }

    public List<TxnDetailBean> getTxnDetailUnAuthList() {
        return txnDetailUnAuthList;
    }

    public void setTxnDetailUnAuthList(List<TxnDetailBean> txnDetailUnAuthList) {
        this.txnDetailUnAuthList = txnDetailUnAuthList;
    }

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustPanNo() {
        return custPanNo;
    }

    public void setCustPanNo(String custPanNo) {
        this.custPanNo = custPanNo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    // </editor-fold>
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public XferAuthorization() {
        selectedItemList = new ArrayList<XferDetailsItem>();
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            setUserName(req.getRemoteUser());
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiRemoteName);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsName);
            postalRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");

            this.setAcViewJtName("");
            this.setAcViewNominee("");
            getUnAuthXferDetails();
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }


    }

    public void getUnAuthXferDetails() {
        try {
            xferDetailsItemList = new ArrayList<XferDetailsItem>();
            List resultList = new ArrayList();
            resultList = txnAuthRemote.getUnAuthXferDetails(orgnBrCode);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(i);
                    XferDetailsItem xferDetailsItem = new XferDetailsItem();
                    if (element.elementAt(0) != null) {
                        xferDetailsItem.setAcNo(element.elementAt(0).toString());
                    }
                    if (element.elementAt(1) != null) {
                        xferDetailsItem.setCustName(element.elementAt(1).toString());
                    }
                    if (element.elementAt(2) != null) {
                        xferDetailsItem.setTranType(element.elementAt(2).toString());
                    }
                    if (element.elementAt(3) != null) {
                        xferDetailsItem.setInstNo(element.elementAt(3).toString());
                    }

                    if (element.elementAt(4) != null) {
                        xferDetailsItem.setCrAmt(Double.parseDouble(element.elementAt(4).toString()));
                    }
                    if (element.elementAt(5) != null) {
                        xferDetailsItem.setAuth(element.elementAt(5).toString());
                    }
                    if (element.elementAt(6) != null) {
                        xferDetailsItem.setEnterBy(element.elementAt(6).toString());
                    }
                    if (element.elementAt(7) != null) {
                        xferDetailsItem.setRecNo(Float.parseFloat(element.elementAt(7).toString()));
                    }
                    if (element.elementAt(8) != null) {
                        xferDetailsItem.setTy(element.elementAt(8).toString());
                    }

                    if (element.elementAt(9) != null) {
                        xferDetailsItem.setDrAmt(Double.parseDouble(element.elementAt(9).toString()));
                    }
                    if (element.elementAt(10) != null) {
                        xferDetailsItem.setTrsNo(Float.parseFloat(element.elementAt(10).toString()));
                    }
                    if (element.elementAt(11) != null) {
                        xferDetailsItem.setBalance(Double.parseDouble(element.elementAt(11).toString()));
                    }
                    if (element.elementAt(12) != null) {
                        xferDetailsItem.setPayBy(element.elementAt(12).toString());
                    }

                    if (element.elementAt(13) != null) {
                        xferDetailsItem.setDetails(element.elementAt(13).toString());
                        int index = element.elementAt(13).toString().indexOf("~`");
                        if (index > -1) {
                            xferDetailsItem.setViewDetails(element.elementAt(13).toString().substring(0, index));
                        } else {
                            xferDetailsItem.setViewDetails(element.elementAt(13).toString());
                        }
                    }
                    if (element.elementAt(14) != null) {
                        xferDetailsItem.setClgReason(element.elementAt(14).toString());
                    }
                    if (element.elementAt(15) != null) {
                        xferDetailsItem.setIy(element.elementAt(15).toString());
                    }
                    if (element.elementAt(16) != null) {
                        xferDetailsItem.setSubtokenno(element.elementAt(16).toString());
                    }
                    if (element.elementAt(17) != null) {
                        xferDetailsItem.setCheckBy(element.elementAt(17).toString());
                    }
                    if (element.elementAt(18) != null) {
                        xferDetailsItem.setInstDt(element.elementAt(18).toString());
                    }
                    if (element.elementAt(19) != null) {
                        xferDetailsItem.setValueDt(element.elementAt(19).toString());
                    }
                    if (element.elementAt(20) != null) {
                        xferDetailsItem.setAdviceNo(element.elementAt(20).toString());
                    }
                    if (element.elementAt(21) != null) {
                        xferDetailsItem.setAdviceBrCode(element.elementAt(21).toString());
                    }
                    xferDetailsItem.setRelatedTo(txnAuthRemote.getTranDescription(Integer.parseInt(element.elementAt(14).toString())));
                    
                    xferDetailsItemList.add(xferDetailsItem);
                }
            } else {
                this.setErrorMsg("Transfer Voucher does not pending for authorization.");
            }

        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void setSelectedBatch() {
        try {
            if (this.trsNo.equals("") || this.trsNo == null) {
                this.setErrorMsg("Please enter a batch number for select that batch for authorization.");
                this.setCrdrFlag("none");
                this.setCrAmount(new BigDecimal("0"));
                this.setDrAmount(new BigDecimal("0"));
                return;
            }
            if (!this.trsNo.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                this.setErrorMsg("Please enter numeric value in Batch No.");
                this.setCrdrFlag("none");
                this.setCrAmount(new BigDecimal("0"));
                this.setDrAmount(new BigDecimal("0"));
                return;
            }
            if (checkDrCrBalance() == false) {
                this.setErrorMsg("Batch No. " + this.getTrsNo() + " is incomplete");
                this.setCrdrFlag("none");
                this.setCrAmount(new BigDecimal("0"));
                this.setDrAmount(new BigDecimal("0"));
                return;
            }
            this.setErrorMsg("");
            selectedItemList = new ArrayList<XferDetailsItem>();
            for (XferDetailsItem xferDetailsItem : xferDetailsItemList) {
                if (xferDetailsItem.getTrsNo() == Float.parseFloat(trsNo)) {
                    selectedItemList.add(xferDetailsItem);
                }
            }
            if (selectedItemList.isEmpty()) {
                this.setCrdrFlag("none");
                this.setCrAmount(new BigDecimal("0"));
                this.setDrAmount(new BigDecimal("0"));
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public boolean checkDrCrBalance() {
        try {
            NumberFormat formatter = new DecimalFormat("#.##");
            BigDecimal crAmt = new BigDecimal("0");
            BigDecimal drAmt = new BigDecimal("0");
            selectedItemList = new ArrayList<XferDetailsItem>();
            for (XferDetailsItem xferDetailsItem : xferDetailsItemList) {
                if (xferDetailsItem.getTrsNo() == Float.parseFloat(trsNo)) {
                    selectedItemList.add(xferDetailsItem);
                }
            }
            if (!selectedItemList.isEmpty()) {
                for (int i = 0; i < selectedItemList.size(); i++) {
                    String ty = selectedItemList.get(i).getTy();
                    if (ty.equals("0")) {
                        crAmt = crAmt.add(new BigDecimal(selectedItemList.get(i).getCrAmt()));
                    } else if (ty.equals("1")) {
                        drAmt = drAmt.add(new BigDecimal(selectedItemList.get(i).getDrAmt()));
                    }
                }
                //if (crAmt.compareTo(drAmt) != 0) {
                if (!formatter.format(Double.parseDouble(crAmt.toString())).toString().equals(formatter.format(Double.parseDouble(drAmt.toString())).toString())) {
                    selectedItemList = new ArrayList<XferDetailsItem>();
                    this.setCrdrFlag("none");
                    this.setCrAmount(new BigDecimal("0"));
                    this.setDrAmount(new BigDecimal("0"));
                    return false;
                } else {
                    this.setCrdrFlag("");
                    this.setCrAmount(crAmt);
                    this.setDrAmount(drAmt);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return true;
    }

    public void authorizeXferDetail() {
        if (selectedItemList.size() > 0) {
            try {
                NumberFormat formatter = new DecimalFormat("#.##");
                if (this.getDrAmount().compareTo(new BigDecimal("0")) != 0 && !formatter.format(Double.parseDouble(getCrAmount().toString())).toString().equals(formatter.format(Double.parseDouble(getDrAmount().toString())).toString())) {
                    //if (this.getDrAmount().compareTo(new BigDecimal("0")) != 0 && this.getDrAmount().compareTo(this.getCrAmount()) != 0) {
                    this.setErrorMsg("Batch No. " + this.getTrsNo() + " is incomplete");
                    this.setCrdrFlag("none");
                    this.setCrAmount(new BigDecimal("0"));
                    this.setDrAmount(new BigDecimal("0"));
                    return;
                }
                String dt = ymd.format(dmy.parse(getTodayDate()));
                Float trSNo = selectedItemList.get(0).getTrsNo();
                String enterByPage = getUserName();
                System.out.println("dt:" + dt + ", trSNo:" + trSNo + ", enterByPage:" + enterByPage);
                //Postal Checking
               // String result = postalRemote.checkNreFlagInBatch(trSNo);
               // if (result.equalsIgnoreCase("true")) {
               //     result = postalRemote.processTrfRecovery(trSNo, 0f, orgnBrCode, ymd.format(dmy.parse(todayDate)), 2, userName);
                //} else {
                   String result = txnAuthRemote.cbsAuthTransfer(dt, trSNo, enterByPage, orgnBrCode);
                //}
                System.out.println("result" + result);
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    if (!result.substring(4).contains("->")) {
                        this.setMessage("Authorizaton Over");
                    } else if (result.substring(4).contains("->")) {
                        this.setMessage("Authorizaton Over. " + result.substring(6));
                    }
                    if (selectedItemList.size() > 0) {
                        selectedItemList.clear();
                    }
                    this.setTrsNo("");
                    this.setCrAmount(new BigDecimal("0"));
                    this.setDrAmount(new BigDecimal("0"));
                } else {
                    this.setErrorMsg(result);
                }
                getUnAuthXferDetails();
            } catch (ApplicationException e) {
                this.setErrorMsg(e.getMessage());
            } catch (Exception e) {
                this.setErrorMsg(e.getMessage());
            }
        } else {
            this.setErrorMsg("Please select at least one batch for authorization");
        }
    }

    public void refresh() {
        if (selectedItemList.size() > 0) {
            selectedItemList.clear();
        }
        getUnAuthXferDetails();
        this.setTrsNo("");
        this.setErrorMsg("");
        this.setMessage("");
        this.setCrdrFlag("none");
        this.setCrAmount(new BigDecimal("0"));
        this.setDrAmount(new BigDecimal("0"));
        this.setAccountNo("");
        this.setOpenBalance(new BigDecimal("0"));
        this.setPresentBalance(new BigDecimal("0"));
        this.setAcViewJtName("");
        this.setAcViewNominee("");
    }

    public String btnExit_action() {
        return "case1";
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }

    public void signatureDetails() {
        try {
            if (currentItem.getTy().equals("0")) {
//                imageData = null;
//                accNo = null;
//                openPopUp = false;
                String acctnature = ftsRemote.getAccountNature(currentItem.getAcNo());
                if (acctnature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    openPopUp = true;
                    getSignatureDetail();
                } else {
                    imageData = null;
                    accNo = null;
                    openPopUp = false;
                }
            }
            if (currentItem.getTy().equals("1")) {
                openPopUp = true;
                getSignatureDetail();
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getSignatureDetail() {
        try {
            this.imageData = null;
            int Toper;
            String signature;
            accNo = currentItem.getAcNo();
            System.out.println("accNo    " + accNo);
            accountName = currentItem.getCustName();
            List list = txnAuthRemote.getDataForSignature(accNo);
            System.out.println("list    " + list);
            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                jtName = vec.get(0).toString() + " " + vec.get(1).toString() + " " + vec.get(2).toString() + " " + vec.get(3).toString();
                opMode = vec.get(5).toString();
                Toper = Integer.parseInt(opMode);
                if (Toper == 1) {
                    if ((Toper == 1 && !accNo.equalsIgnoreCase("CA")) && (Toper == 1 && !accNo.equalsIgnoreCase("CC"))) {
                        opMode = "Self";
                    } else {
                        opMode = "Self";
                    }
                } else {
                    List list2 = txnAuthRemote.selectForOperationMode(opMode);
                    Vector v2 = (Vector) list2.get(0);
                    this.setOpMode(v2.get(0).toString());

                }

                accInstruction = vec.get(4).toString();
                String accOpenDate = vec.get(6).toString().substring(6) + "/" + vec.get(6).toString().substring(4, 6) + "/" + vec.get(6).toString().substring(0, 4);
                openDate = accOpenDate;
                this.setDpLimit(vec.get(8).toString());
                List<String> custIdList = txnRemote.getCustIdByAccount(accNo);
                if (!custIdList.isEmpty()) {
                    this.setCustId(custIdList.get(0));
                    this.setAnnualTurnover(custIdList.get(1));
                    this.setRiskCategorization(custIdList.get(2));
                    this.setCustPanNo(custIdList.get(3));
                    this.setProfession(custIdList.get(4));
                } else {
                    this.setCustId("");
                    this.setAnnualTurnover("");
                    this.setRiskCategorization("");
                    this.setCustPanNo("");
                    this.setProfession("");
                }
            }
            signature = txnAuthRemote.getSignatureDetails(accNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(accNo);
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
//                this.setMsg("");
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (FileNotFoundException e) {
            this.setErrorMsg("");
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String acno = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));

        for (XferDetailsItem item : selectedItemList) {
            if (item.getAcNo().equalsIgnoreCase(acno)) {
                currentItem = item;
                break;
            }
        }
    }

    public void acctViewAuthUnAuth() {
        // String array[] = null;
        // String array1[] = null;
        // int i, j, k, l, m, n, o, p, q;

        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();
            accountNo = currentItem.getAcNo();
            System.out.println("Account No IS " + accountNo);
            if (this.accountNo == null) {
                this.setErrorMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            if (this.accountNo.equals("")) {
                this.setErrorMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            this.setMessage("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar now = Calendar.getInstance();
            now.roll(Calendar.MONTH, false);
            String oneMonthBeforeDate = sdf.format(now.getTime());


            String openBal = txnRemote.getOpeningBalF4(accountNo, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {

                this.setOpenBalance(new BigDecimal(openBal));
            }

            String prsntBal = txnRemote.getOpeningBalF4(accountNo, todayDate);
            if (!prsntBal.equals("FALSE")) {

                this.setPresentBalance(new BigDecimal(prsntBal));
            }

            //   BigDecimal netAmount = this.getPresentBalance();

            List sigList = txnAuthRemote.getDataForSignature(accountNo);
            if (!sigList.isEmpty()) {
                Vector sigVector = (Vector) sigList.get(0);
                this.setAcViewJtName(sigVector.get(0).toString() + " " + sigVector.get(1).toString() + " " + sigVector.get(2).toString() + " " + sigVector.get(3).toString());
                this.setAcViewNominee(sigVector.get(7).toString());
            }

            txnDetailList = txnRemote.accViewAuth(accountNo, todayDate, orgnBrCode);
            txnDetailUnAuthList = txnRemote.accViewUnauth(accountNo, todayDate, orgnBrCode);
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void viewAdviceDetails() {
        try {
            this.setAdNo(currentItem.getAdviceNo());
            List bankResultList = beanRemote.bankAddress(currentItem.getAdviceBrCode());
            if (bankResultList.size() > 0) {
                for (int i = 0; i < bankResultList.size(); i++) {
                    Vector ele = (Vector) bankResultList.get(i);
                    this.setAdBrName(CbsUtil.toProperCase((ele.get(1).toString() + " " + ele.get(2).toString()).toLowerCase()));
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }
}
