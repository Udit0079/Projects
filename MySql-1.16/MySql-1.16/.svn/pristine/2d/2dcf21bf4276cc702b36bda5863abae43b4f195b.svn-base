/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.IMPSMMIDGenerationPojo;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class IMPSMMIDGeneration extends BaseBean {

    private String accountNo;
    private String function;
    private String errorMsg;
    private String acNoLen;
    private String customerid;
    private String name;
    private String panno;
    private String adharno;
    private String mobileno;
    private String mode;
    private String btnvalue;
    private String msgvalue;
    private String validationdata;
    private String request_beneficaryName;
    private String beneficaryAccountno;
    private String beneficaryIFSC;
    private String beneficaryMobileno;
    private String beneficaryMmid;
    private double tranAmount = 0d;
    private String panelDisplay1 = "none";
    private String panelDisplay = "none";
    private String panelDisplay2 = "none";
    private String panelDisplay3 = "none";
    private String panelDisplay6 = "none";
    private String accountDisplay = "none";
    private boolean disableaccountno = true;
    private boolean disableProcessBtn;
    private String panelDisplay4 = "none";
    private String panelDisplay5 = "none";
    private String panelDisplay7 = "none";
    private String remark;
    private String dir;
    private String newAcNo;
    private String chargeType;
    private String btnAlertMessage;
    private String chargeAmount;
    private boolean fieldDisableFlag;
    CustomerMasterDelegate masterDelegate;
    IMPSMMIDGenerationPojo tempCurrentItem;
    private List<SelectItem> chargeTypeList;
    private List<SelectItem> functionList;
    private List<SelectItem> modeList;
    private List<IMPSMMIDGenerationPojo> gridDetail;
    private IMPSMMIDGenerationPojo p2ATempCurrentItem;
    private IMPSMMIDGenerationPojo p2PTempCurrentItem;
    private List<IMPSMMIDGenerationPojo> p2AGridDetail;
    private List<IMPSMMIDGenerationPojo> p2PGridDetail;
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private FtsPostingMgmtFacadeRemote ftsremote;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    NumberFormat formatter = new DecimalFormat("#0.00");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public IMPSMMIDGeneration() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsremote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            this.setAcNoLen(getAcNoLength());
            this.fieldDisableFlag = false;
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("G", "Generate MMID"));
            functionList.add(new SelectItem("C", "Cancel MMID"));
            functionList.add(new SelectItem("FP2A", "Fund Transfer P2A"));
            functionList.add(new SelectItem("FP2P", "Fund Transfer P2P"));
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void optionsdisplay() throws ApplicationException {
        setErrorMsg("");
        try {
            this.disableProcessBtn = false;
            if (function.equalsIgnoreCase("G") && mode.equalsIgnoreCase("A")) {
                this.panelDisplay = "";
                this.panelDisplay4 = "";
                this.panelDisplay1 = "none";
                this.panelDisplay2 = "none";
                this.panelDisplay3 = "none";
                this.panelDisplay6 = "none";
                this.panelDisplay7 = "none";
                this.disableaccountno = false;
                this.panelDisplay5 = "none";
                this.setBtnvalue("Generate");
            }
            if (function.equalsIgnoreCase("G") || function.equalsIgnoreCase("C")) {
                if (this.mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D")) {
                    this.panelDisplay2 = "";
                    this.panelDisplay = "none";
                    this.panelDisplay4 = "none";
                    this.panelDisplay1 = "none";
                    this.panelDisplay3 = "none";
                    this.panelDisplay5 = "none";
                    this.panelDisplay6 = "none";
                    this.panelDisplay7 = "none";
                    this.disableaccountno = true;
                    this.disableProcessBtn = true;
                    this.setAccountNo("");
                    gridDetail = new ArrayList<>();
                    List datalist = remoteInterface.getImpsGridDetails(this.newAcNo, ymd.format(dmy.parse(getTodayDate())), mode, function, getOrgnBrCode());
                    if (datalist.isEmpty()) {
                        setErrorMsg("Data does not exists");
                    }
                    for (int i = 0; i < datalist.size(); i++) {
                        Vector vtr = (Vector) datalist.get(i);
                        IMPSMMIDGenerationPojo pojo = new IMPSMMIDGenerationPojo();
                        pojo.setName(vtr.get(0).toString());
                        pojo.setAccountNo(vtr.get(1).toString());
                        pojo.setMobileno(vtr.get(2).toString());
                        pojo.setStancode(vtr.get(6).toString());
                        gridDetail.add(pojo);
                    }
                    if (mode.equalsIgnoreCase("V")) {
                        this.setMsgvalue("Are you sure to verfiy");
                    }
                    if (mode.equalsIgnoreCase("D")) {
                        this.setMsgvalue("Are you sure for delete");
                    }
                }
            }
            if (function.equalsIgnoreCase("FP2A") && (this.mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                this.panelDisplay2 = "none";
                this.panelDisplay = "none";
                this.panelDisplay4 = "none";
                this.panelDisplay1 = "none";
                this.panelDisplay3 = "none";
                this.panelDisplay7 = "none";
                this.panelDisplay6 = "none";
                this.panelDisplay5 = "";
                this.disableaccountno = true;
                this.disableProcessBtn = true;
                this.setAccountNo("");
                p2AGridDetail = new ArrayList<>();
                List datalist = remoteInterface.getImpsGridDetails(this.newAcNo, ymd.format(dmy.parse(getTodayDate())), mode, function, getOrgnBrCode());
                if (datalist.isEmpty()) {
                    setErrorMsg("Data does not exists");
                }
                for (int i = 0; i < datalist.size(); i++) {
                    Vector vtr = (Vector) datalist.get(i);
                    IMPSMMIDGenerationPojo pojo = new IMPSMMIDGenerationPojo();
                    pojo.setName(vtr.get(0).toString());
                    pojo.setAccountNo(vtr.get(1).toString());
                    pojo.setMobileno(vtr.get(2).toString());
                    pojo.setStancode(vtr.get(6).toString());
                    pojo.setBeneAccNo(vtr.get(7).toString());
                    pojo.setBeneIFSC(vtr.get(8).toString());
                    pojo.setTranAmount(vtr.get(9).toString());
                    p2AGridDetail.add(pojo);
                }
                if (mode.equalsIgnoreCase("V")) {
                    this.setMsgvalue("Are you sure to verfiy");
                }
                if (mode.equalsIgnoreCase("D")) {
                    this.setMsgvalue("Are you sure for delete");
                }
            }

            if (function.equalsIgnoreCase("FP2P") && (this.mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                this.panelDisplay2 = "none";
                this.panelDisplay = "none";
                this.panelDisplay4 = "none";
                this.panelDisplay1 = "none";
                this.panelDisplay3 = "none";
                this.panelDisplay7 = "";
                this.panelDisplay6 = "none";
                this.panelDisplay5 = "none";
                this.disableaccountno = true;
                this.disableProcessBtn = true;
                this.setAccountNo("");
                p2PGridDetail = new ArrayList<>();
                List datalist = remoteInterface.getImpsGridDetails(this.newAcNo, ymd.format(dmy.parse(getTodayDate())), mode, function, getOrgnBrCode());
                if (datalist.isEmpty()) {
                    setErrorMsg("Data does not exists");
                }
                for (int i = 0; i < datalist.size(); i++) {
                    Vector vtr = (Vector) datalist.get(i);
                    IMPSMMIDGenerationPojo pojo = new IMPSMMIDGenerationPojo();
                    pojo.setName(vtr.get(0).toString());
                    pojo.setAccountNo(vtr.get(1).toString());
                    pojo.setMobileno(vtr.get(2).toString());
                    pojo.setStancode(vtr.get(6).toString());
                    pojo.setBeneMobileNo(vtr.get(8).toString());
                    pojo.setBeneMMID(vtr.get(9).toString());
                    pojo.setTranAmount(vtr.get(11).toString());
                    p2PGridDetail.add(pojo);
                }
                if (mode.equalsIgnoreCase("V")) {
                    this.setMsgvalue("Are you sure to verfiy");
                }
                if (mode.equalsIgnoreCase("D")) {
                    this.setMsgvalue("Are you sure for delete");
                }
            }

            if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("A")) {
                this.disableaccountno = false;
                this.setAccountNo("");
                this.setNewAcNo("");
                setBtnvalue("Cancel");
                this.panelDisplay2 = "";
                this.panelDisplay = "none";
                this.panelDisplay1 = "none";
                this.panelDisplay3 = "none";
                this.panelDisplay4 = "none";
                this.panelDisplay6 = "none";
                this.panelDisplay5 = "none";
                this.panelDisplay7 = "none";
                this.disableProcessBtn = true;
                this.gridDetail = new ArrayList();
                this.setMsgvalue("Are you sure for cancle the request");
            }
            if (function.equalsIgnoreCase("FP2A") && mode.equalsIgnoreCase("A")) {
                this.panelDisplay = "none";
                this.panelDisplay1 = "";
                this.panelDisplay3 = "";
                this.panelDisplay4 = "";
                this.panelDisplay2 = "none";
                this.panelDisplay5 = "none";
                this.panelDisplay6 = "none";
                this.panelDisplay7 = "none";
                this.disableaccountno = false;
                setBtnvalue("P2A Fund Transfer");
            }
            if (function.equalsIgnoreCase("FP2P") && mode.equalsIgnoreCase("A")) {
                this.panelDisplay = "none";
                this.panelDisplay1 = "none";
                this.panelDisplay3 = "";
                this.panelDisplay4 = "";
                this.panelDisplay2 = "none";
                this.panelDisplay5 = "none";
                this.panelDisplay6 = "";
                this.panelDisplay7 = "none";
                this.disableaccountno = false;
                setBtnvalue("P2P Fund Transfer");
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void details() {
        setErrorMsg("");
        setName("");
        setMobileno("");
        setNewAcNo("");
        setAdharno("");
        setCustomerid("");
        setPanno("");
        setBeneficaryAccountno("");
        setBeneficaryMobileno("");
        setBeneficaryMmid("");
        setBeneficaryIFSC("");
        setRequest_beneficaryName("");
        chargeTypeList = new ArrayList<>();
        setRemark("");
        setChargeAmount("");
        this.setTranAmount(.00);
        try {
            if (this.function == null || this.function.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select proper Function.");
                return;
            }
            if (this.mode == null || this.mode.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select proper Mode.");
                return;
            }
            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")
                    || ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMsg("Please fill proper Account No.");
                return;
            }
            if (!this.accountNo.matches("[0-9]+")) {
                this.setErrorMsg("Please fill numeric Account No.");
                return;
            }
            this.newAcNo = ftsPostRemote.getNewAccountNumber(this.accountNo);
            if ((function.equalsIgnoreCase("G") || function.equalsIgnoreCase("FP2A") || function.equalsIgnoreCase("FP2P")) && mode.equalsIgnoreCase("A")) {
                List result = remoteInterface.getverfiedCustomerDetails(newAcNo, getOrgnBrCode());
                if (result.isEmpty()) {
                    this.setErrorMsg("This account no is not valid to register.");
                    this.newAcNo = "";
                    return;
                }
                List dataList = remoteInterface.getCustomerDetails(this.newAcNo);
                if (dataList.isEmpty()) {
                    this.setErrorMsg("Data does not exits.");
                    return;
                }
                if (function.equalsIgnoreCase("G")) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector vtr = (Vector) dataList.get(i);
                        this.setCustomerid(vtr.get(0).toString());
                        this.setName(vtr.get(1).toString());
                        this.setPanno(vtr.get(2).toString());
                        this.setAdharno(vtr.get(3).toString());
                        this.setMobileno(vtr.get(4).toString());
                    }
                } else if (function.equalsIgnoreCase("FP2A")) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector vtr = (Vector) dataList.get(i);
                        this.setName(vtr.get(1).toString());
                        this.setMobileno(vtr.get(4).toString());
                    }
                    chargeTypeList = new ArrayList<>();
                    List<CbsRefRecTypeTO> chargeToList = masterDelegate.getFunctionValues("455");
                    if (!chargeToList.isEmpty()) {
                        for (CbsRefRecTypeTO recTypeTO : chargeToList) {
                            chargeTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                        }
                    }
                } else if (function.equalsIgnoreCase("FP2P")) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector vtr = (Vector) dataList.get(i);
                        this.setName(vtr.get(1).toString());
                        this.setMobileno(vtr.get(4).toString());
                    }
                    chargeTypeList = new ArrayList<>();
                    List<CbsRefRecTypeTO> chargeToList = masterDelegate.getFunctionValues("455");
                    if (!chargeToList.isEmpty()) {
                        for (CbsRefRecTypeTO recTypeTO : chargeToList) {
                            chargeTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                        }
                    }
                }
            } else if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("A")) {
                gridDetail = new ArrayList<>();
                List datalist = remoteInterface.getImpsGridDetails(this.accountNo, ymd.format(dmy.parse(getTodayDate())), mode, function, getOrgnBrCode());
                if (datalist.isEmpty()) {
                    setErrorMsg("Data does not exists");
                }
                for (int i = 0; i < datalist.size(); i++) {
                    Vector vtr = (Vector) datalist.get(i);
                    IMPSMMIDGenerationPojo pojo = new IMPSMMIDGenerationPojo();
                    pojo.setName(vtr.get(0).toString());
                    pojo.setAccountNo(vtr.get(1).toString());
                    pojo.setMobileno(vtr.get(2).toString());
                    pojo.setStancode(vtr.get(6).toString());
                    gridDetail.add(pojo);
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void buttonClick() {
        setErrorMsg("");
        try {
            if (this.function == null || this.function.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select proper Function.");
                return;
            }
            if (this.mode == null || this.mode.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select proper Mode.");
                return;
            }
            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")
                    || ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMsg("Please fill proper Account No.");
                return;
            }
            if (!this.accountNo.matches("[0-9]+")) {
                this.setErrorMsg("Please fill numeric Account No.");
                return;
            }
            String instiutionId = ftsremote.getCodeFromCbsParameterInfo("IMPS-OW-INST-ID");
            if (function.equalsIgnoreCase("G") && mode.equalsIgnoreCase("A")) {
                validationdata = accountNo.trim().concat(panno.trim());
                if (this.mobileno == null || this.mobileno.trim().equalsIgnoreCase("") || this.mobileno.trim().length() != 10) {
                    this.setErrorMsg("Please fill proper Mobile No.");
                    return;
                }
                String result = remoteInterface.getImpsGenertaionCancleAdddata(accountNo, name, mobileno,
                        ymd.format(dmy.parse(getTodayDate())), validationdata, instiutionId, this.request_beneficaryName,
                        this.beneficaryAccountno, this.beneficaryIFSC, beneficaryMobileno, beneficaryMmid, this.tranAmount, this.remark, getUserName(), mode,
                        function, "0");
                if (!result.equalsIgnoreCase("true")) {
                    setErrorMsg(result);
                    return;
                }
                generateMmidRefresh();
                setErrorMsg("Request has been generated.");

            } else if (function.equalsIgnoreCase("FP2A") && mode.equalsIgnoreCase("A")) {
                if (this.mobileno == null || this.mobileno.trim().equalsIgnoreCase("") || this.mobileno.trim().length() != 10) {
                    this.setErrorMsg("Please fill proper Mobile No.");
                    return;
                }
                if (this.request_beneficaryName == null || this.request_beneficaryName.trim().equalsIgnoreCase("")) {
                    this.setErrorMsg("Please fill proper Beneficiary Name.");
                    return;
                }
                if (checkvalidBeneficaryAccountno()) {
                    if (checkValidIfscCode()) {
                        if (checkvalidTranAmount()) {
                            if (this.remark == null || this.remark.trim().equalsIgnoreCase("")) {
                                this.setErrorMsg("Please fill Remarks.");
                                return;
                            }
                            double totalAmount = this.tranAmount + Double.parseDouble(this.chargeAmount);
                            String actNature = ftsPostRemote.getAccountNature(this.newAcNo.trim());
                            String chkBalResult = ftsPostRemote.chkBal(this.newAcNo.trim(), 1, 1, actNature);
                            if (!chkBalResult.equalsIgnoreCase("true")) {
                                if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    String chkBalance = ftsPostRemote.checkBalForOdLimit(this.newAcNo.trim(), totalAmount, getUserName());
                                    if (!chkBalance.equalsIgnoreCase("1")) {
                                        this.setErrorMsg("Balance Exceeds for A/c No-->" + this.newAcNo.trim());
                                        return;
                                    }
                                } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                    String balResult = ftsPostRemote.checkBalance(this.newAcNo.trim(), totalAmount, getUserName());
                                    if (!balResult.equalsIgnoreCase("true")) {
                                        this.setErrorMsg("Balance Exceeds for A/c No-->" + this.newAcNo.trim());
                                        return;
                                    }
                                }
                            }
                            String result = remoteInterface.getImpsGenertaionCancleAdddata(newAcNo, name, mobileno,
                                    ymd.format(dmy.parse(getTodayDate())), validationdata, instiutionId, this.request_beneficaryName,
                                    this.beneficaryAccountno, beneficaryIFSC, beneficaryMobileno, beneficaryMobileno, this.tranAmount, this.remark, getUserName(), mode,
                                    function, this.chargeAmount);
                            if (!result.equalsIgnoreCase("true")) {
                                setErrorMsg(result);
                                return;
                            }
                            P2AfundTrfFieldRefresh();
                            setErrorMsg("P2A Fund Transfer Request has been generated.");
                        }
                    }
                }
            } else if (function.equalsIgnoreCase("FP2P") && mode.equalsIgnoreCase("A")) {
                if (this.mobileno == null || this.mobileno.trim().equalsIgnoreCase("") || this.mobileno.trim().length() != 10) {
                    this.setErrorMsg("Please fill proper Mobile No.");
                    return;
                }
                if (validateBenefMobileno()) {
                    if (validatebeneMMID()) {
                        if (this.remark == null || this.remark.trim().equalsIgnoreCase("")) {
                            this.setErrorMsg("Please fill Remarks.");
                            return;
                        }
                    }
                }
                double totalAmount = this.tranAmount + Double.parseDouble(this.chargeAmount);
                String actNature = ftsPostRemote.getAccountNature(this.newAcNo.trim());
                String chkBalResult = ftsPostRemote.chkBal(this.newAcNo.trim(), 1, 1, actNature);
                if (!chkBalResult.equalsIgnoreCase("true")) {
                    if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        String chkBalance = ftsPostRemote.checkBalForOdLimit(this.newAcNo.trim(), totalAmount, getUserName());
                        if (!chkBalance.equalsIgnoreCase("1")) {
                            this.setErrorMsg("Balance Exceeds for A/c No-->" + this.newAcNo.trim());
                            return;
                        }
                    } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        String balResult = ftsPostRemote.checkBalance(this.newAcNo.trim(), totalAmount, getUserName());
                        if (!balResult.equalsIgnoreCase("true")) {
                            this.setErrorMsg("Balance Exceeds for A/c No-->" + this.newAcNo.trim());
                            return;
                        }
                    }
                }
                String result = remoteInterface.getImpsGenertaionCancleAdddata(newAcNo, name, mobileno,
                        ymd.format(dmy.parse(getTodayDate())), validationdata, instiutionId, this.request_beneficaryName,
                        this.beneficaryAccountno, beneficaryIFSC, this.beneficaryMobileno, this.beneficaryMmid, this.tranAmount, this.remark, getUserName(), mode,
                        function, "0");
                if (!result.equalsIgnoreCase("true")) {
                    setErrorMsg(result);
                    return;
                }
                P2PfundTrfFieldRefresh();
                setErrorMsg("P2P Fund Transfer Request has been generated.");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void verifyUpdate() {
        String details = "";
        setErrorMsg("");
        try {
            if (this.function == null || this.function.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select proper Function.");
                return;
            }
            if (this.mode == null || this.mode.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select proper Mode.");
                return;
            }
            if (function.equalsIgnoreCase("FP2A") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                if (p2ATempCurrentItem == null) {
                    this.setErrorMsg("Please select row from table.");
                    return;
                }
                String result = remoteInterface.getVerifyDataOnVerifyDelete(p2ATempCurrentItem.getAccountNo(), getOrgnBrCode(),
                        function, mode, getUserName(), ymd.format(dmy.parse(getTodayDate())), p2ATempCurrentItem.getStancode());
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMsg(result);
                    return;
                }

                details = remoteInterface.getGenerateCancleProcess(p2ATempCurrentItem.getAccountNo(),
                        ymd.format(dmy.parse(getTodayDate())), getOrgnBrCode(), function, mode,
                        p2ATempCurrentItem.getStancode(), getUserName());
//                if (!details.equalsIgnoreCase("true")) {
//                    this.setErrorMsg(details);
//                    return;
//                }
//                this.setErrorMsg(mode.equalsIgnoreCase("D") ? "Request has been deleted" : "Request has been completed.");
                this.setErrorMsg(details);
                p2AGridDetail.remove(p2ATempCurrentItem);
            }
            if (function.equalsIgnoreCase("FP2P") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                if (p2PTempCurrentItem == null) {
                    this.setErrorMsg("Please select row from table.");
                    return;
                }
                String result = remoteInterface.getVerifyDataOnVerifyDelete(p2PTempCurrentItem.getAccountNo(), getOrgnBrCode(),
                        function, mode, getUserName(), ymd.format(dmy.parse(getTodayDate())), p2PTempCurrentItem.getStancode());
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMsg(result);
                    return;
                }

                details = remoteInterface.getGenerateCancleProcess(p2PTempCurrentItem.getAccountNo(),
                        ymd.format(dmy.parse(getTodayDate())), getOrgnBrCode(), function, mode,
                        p2PTempCurrentItem.getStancode(), getUserName());
//                if (!details.equalsIgnoreCase("true")) {
//                    this.setErrorMsg(details);
//                    return;
//                }
//                this.setErrorMsg(mode.equalsIgnoreCase("D") ? "Request has been deleted" : "Request has been completed.");
                this.setErrorMsg(details);
                p2PGridDetail.remove(p2PTempCurrentItem);
            }

            if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("A")) {
                if (tempCurrentItem == null) {
                    this.setErrorMsg("Please select row from table.");
                    return;
                }
                if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")
                        || ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMsg("Please fill proper Account No.");
                    return;
                }
                if (!this.accountNo.matches("[0-9]+")) {
                    this.setErrorMsg("Please fill numeric Account No.");
                    return;
                }
                String instiutionId = ftsremote.getCodeFromCbsParameterInfo("IMPS-OW-INST-ID");
                String result = remoteInterface.getImpsGenertaionCancleAdddata(this.accountNo, tempCurrentItem.getName(), tempCurrentItem.getMobileno(),
                        ymd.format(dmy.parse(getTodayDate())), validationdata, instiutionId, this.request_beneficaryName,
                        this.beneficaryAccountno, beneficaryIFSC, beneficaryMobileno, beneficaryMmid, this.tranAmount, this.remark, getUserName(), mode, function, "0");
                if (!result.equalsIgnoreCase("true")) {
                    setErrorMsg(result);
                    return;
                }
                this.setFunction("S");
                this.setMode("0");
                this.setAccountNo("");
                this.setNewAcNo("");
                gridDetail.remove(tempCurrentItem);
                setErrorMsg("Cancle MMID Request has been generated.");
            }
            if (function.equalsIgnoreCase("G") || function.equalsIgnoreCase("C")) {
                if (tempCurrentItem == null) {
                    this.setErrorMsg("Please select row from table.");
                    return;
                }
                if (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D")) {
                    String result = remoteInterface.getVerifyDataOnVerifyDelete(tempCurrentItem.getAccountNo(), getOrgnBrCode(),
                            function, mode, getUserName(), ymd.format(dmy.parse(getTodayDate())), tempCurrentItem.getStancode());
                    if (!result.equalsIgnoreCase("true")) {
                        this.setErrorMsg(result);
                        return;
                    }
                }

                details = remoteInterface.getGenerateCancleProcess(tempCurrentItem.getAccountNo(), ymd.format(dmy.parse(getTodayDate())),
                        getOrgnBrCode(), function, mode, tempCurrentItem.getStancode(), getUserName());
                if (!details.equalsIgnoreCase("true")) {
                    this.setErrorMsg(details);
                    return;
                }
                this.setErrorMsg(mode.equalsIgnoreCase("D") ? "Request has been deleted" : "Request has been completed.");
                gridDetail.remove(tempCurrentItem);
            }

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void validateIfscCode() {
        this.setErrorMsg("");
        checkValidIfscCode();
    }

    public boolean checkValidIfscCode() {
        try {
            if (!ParseFileUtil.isValidIfsc(this.beneficaryIFSC)) {
                this.setErrorMsg("Please fill proper Beneficiary IFSC");
                return false;
            }
//            if (!ParseFileUtil.isNumeric(this.beneficaryIFSC.substring(4, 11))) {
//                this.setErrorMsg("Please fill proper Beneficiary IFSC");
//                return false;
//            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
            return false;
        }
        return true;
    }

    public void validateBeneficiaryname() {
        this.setErrorMsg("");
        if ((this.request_beneficaryName == null) || (this.request_beneficaryName.equalsIgnoreCase(""))
                || this.request_beneficaryName.length() == 0) {
            this.setErrorMsg("Please fill proper Beneficiary Name.");
        }
    }

    public void validateBeneficaryAccountno() {
        this.setErrorMsg("");
        checkvalidBeneficaryAccountno();
    }

    public boolean validateBenefMobileno() {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        if ((this.beneficaryMobileno == null || this.beneficaryMobileno.equalsIgnoreCase("")) || this.beneficaryMobileno.length() != 10) {
            this.setErrorMsg("Please fill proper Beneficiary Mobile Number.");
            return false;
        }
        Matcher mobileNo = p.matcher(this.beneficaryMobileno);
        if (!mobileNo.matches()) {
            this.setErrorMsg("Please fill a valid Beneficiary Mobile Number.");
            return false;
        }

        return true;

    }

    public void validateBeneficaryMobileno() {
        this.setErrorMsg("");
        validateBenefMobileno();
    }

    public void validatebeneficaryMMID() {
        this.setErrorMsg("");
        validatebeneMMID();
    }

    public boolean validatebeneMMID() {
        if (this.beneficaryMmid == null || this.beneficaryMmid.equalsIgnoreCase("") || this.beneficaryMmid.length() != 7) {
            this.setErrorMsg("Please fill proper Beneficiary MMID.");
            return false;
        }
        if (!this.beneficaryMmid.matches("[0-9]+")) {
            this.setErrorMsg("Please fill valid Beneficiary MMID. ");
            return false;
        }
        if (beneficaryMmid.substring(0, 4).contains("0")) {
            this.setErrorMsg("Please fill valid Beneficiary MMID.");
            return false;
        }
        return true;
    }

    public boolean checkvalidBeneficaryAccountno() {
        Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
        if (this.beneficaryAccountno == null || this.beneficaryAccountno.equalsIgnoreCase("") || this.beneficaryAccountno.length() == 0) {
            this.setErrorMsg("Please fill proper Beneficiary Account Number.");
            return false;
        }
        Matcher acNoCheck = p3.matcher(this.beneficaryAccountno);
        if (!acNoCheck.matches()) {
            this.setErrorMsg("Please fill a valid Beneficiary Account Number.");
            return false;
        }
        return true;
    }

    public void validateRemark() {
        this.setErrorMsg("");
        if (remark.equalsIgnoreCase("") || remark.equalsIgnoreCase(null) || remark.length() == 0) {
            this.setErrorMsg("Please fill proper Remark.");
        }
    }

    public String getChargeApplyDueToTransaction(String chargeType, double transactionAmount, String accountType) {
        String charge = "0";
        try {
            List chargeApplyList = remoteInterface.getChargeApplyOnCustomer(this.chargeType, transactionAmount,
                    ftsPostRemote.getAccountCode(this.newAcNo), getOrgnBrCode());
            if (!chargeApplyList.isEmpty()) {
                Vector element = (Vector) chargeApplyList.get(0);
                String chargeFlag = element.get(0).toString();
                Double amtOrPerc = Double.parseDouble(element.get(1).toString());
                if (chargeFlag.equalsIgnoreCase("P")) {
                    amtOrPerc = (transactionAmount * amtOrPerc) / 100;
                }
                charge = formatter.format(amtOrPerc);
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return charge;
    }

    public void validateTranAmount() {
        this.setErrorMsg("");
        this.setChargeAmount("");
        checkvalidTranAmount();
    }

    public boolean checkvalidTranAmount() {
        try {
            if (this.tranAmount == 0) {
                this.setErrorMsg("Please fill Txn. Amount");
                return false;
            }
            Pattern p = Pattern.compile("[0-9]+([,.][0-9]{1,2})?");
            Matcher amountCheck = p.matcher(String.valueOf(this.tranAmount));
            if (!amountCheck.matches()) {
                this.setErrorMsg("Amount should be with two decimal point.");
            }
            DecimalFormat df = new DecimalFormat("#.00");
            this.setTranAmount(new BigDecimal(df.format(tranAmount)).doubleValue());
            if (!this.chargeType.equalsIgnoreCase("0NA")) {
                String charge = getChargeApplyDueToTransaction(this.chargeType, this.getTranAmount(),
                        ftsPostRemote.getAccountCode(this.newAcNo));
                this.setChargeAmount(df.format(Double.parseDouble(charge)));
            } else {
                this.setChargeAmount("0");
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
            return false;
        }
        return true;
    }

    public void showButtonAlertMessage() {
        this.setErrorMsg("");
        this.setBtnAlertMessage("");
        try {
            if (this.function == null || this.function.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select function.");
                this.setBtnAlertMessage("Please select function.");
                return;
            }
            if (this.mode == null || this.mode.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select mode.");
                this.setBtnAlertMessage("Please select mode.");
                return;
            }
            if (this.function.equalsIgnoreCase("G")) {
                if (this.mode.equalsIgnoreCase("A")) {
                    this.setBtnAlertMessage("Are you sure to add the MMID generation request.");
                } else if (this.mode.equalsIgnoreCase("D")) {
                    this.setBtnAlertMessage("Are you sure to delete the MMID generation request.");
                } else if (this.mode.equalsIgnoreCase("V")) {
                    this.setBtnAlertMessage("Are you sure to verify the MMID generation request.");
                }
            } else if (this.function.equalsIgnoreCase("C")) {
                if (this.mode.equalsIgnoreCase("A")) {
                    this.setBtnAlertMessage("Are you sure to add the MMID cancel request.");
                } else if (this.mode.equalsIgnoreCase("V")) {
                    this.setBtnAlertMessage("Are you sure to verify the MMID cancel request.");
                }
            } else if (this.function.equalsIgnoreCase("FP2A")) {
                this.setBtnAlertMessage("Are you sure to transfer the fund.");
                if (this.mode.equalsIgnoreCase("A")) {
                    this.setBtnAlertMessage("Are you sure to add the P2A fund transfer request.");
                } else if (this.mode.equalsIgnoreCase("D")) {
                    this.setBtnAlertMessage("Are you sure to delete the P2A fund transfer request.");
                } else if (this.mode.equalsIgnoreCase("V")) {
                    this.setBtnAlertMessage("Are you sure to verify the P2A fund transfer request.");
                }
            } else if (this.function.equalsIgnoreCase("FP2P")) {
                this.setBtnAlertMessage("Are you sure to transfer the fund.");
                if (this.mode.equalsIgnoreCase("A")) {
                    this.setBtnAlertMessage("Are you sure to add the  P2P fund transfer request.");
                } else if (this.mode.equalsIgnoreCase("D")) {
                    this.setBtnAlertMessage("Are you sure to delete the P2P fund transfer request.");
                } else if (this.mode.equalsIgnoreCase("V")) {
                    this.setBtnAlertMessage("Are you sure to verify the P2P fund transfer request.");
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    private void P2AfundTrfFieldRefresh() {
        this.setErrorMsg("");
        this.setFunction("S");
        this.setMode("0");
        this.setAccountNo("");
        this.setNewAcNo("");
        this.setName("");
        this.setMobileno("");
        this.setRequest_beneficaryName("");
        this.setBeneficaryAccountno("");
        this.setBeneficaryIFSC("");
        this.setChargeAmount("");
        this.setTranAmount(.00);
        this.setRemark("");
        this.fieldDisableFlag = false;
    }

    private void P2PfundTrfFieldRefresh() {
        this.setErrorMsg("");
        this.setFunction("S");
        this.setMode("0");
        this.setAccountNo("");
        this.setNewAcNo("");
        this.setName("");
        this.setMobileno("");
        this.setBeneficaryMobileno("");
        this.setBeneficaryMmid("");
        this.setChargeAmount("");
        this.setTranAmount(.00);
        this.setRemark("");
        this.fieldDisableFlag = false;
    }

    private void generateMmidRefresh() {
        this.setErrorMsg("");
        this.setFunction("S");
        this.setMode("0");
        this.setAccountNo("");
        this.setNewAcNo("");
        this.setCustomerid("");
        this.setName("");
        this.setMobileno("");
        this.setAdharno("");
        this.setPanno("");
        this.setDisableaccountno(false);

    }

    public void optionsMode() {
        setErrorMsg("");
        setName("");
        setMobileno("");
        setNewAcNo("");
        setAdharno("");
        setCustomerid("");
        setPanno("");
        setAccountNo("");
        this.setDisableaccountno(true);
        setMode("0");
        panelDisplay();
        modeList = new ArrayList<>();
        setBtnvalue("");
        modeList.add(new SelectItem("0", "--Select--"));
        if (function.equalsIgnoreCase("G")) {
            setBtnvalue("");
            modeList.add(new SelectItem("A", "Add"));
            modeList.add(new SelectItem("V", "Verify"));
            modeList.add(new SelectItem("D", "Delete"));
        } else if (function.equalsIgnoreCase("C")) {
            setBtnvalue("");
            modeList.add(new SelectItem("A", "Add"));
            modeList.add(new SelectItem("V", "Verify"));
        } else if (function.equalsIgnoreCase("FP2A")) {
            setBtnvalue("");
            modeList.add(new SelectItem("A", "Add"));
            modeList.add(new SelectItem("V", "Verify"));
            modeList.add(new SelectItem("D", "Delete"));
        } else if (function.equalsIgnoreCase("FP2P")) {
            setBtnvalue("");
            modeList.add(new SelectItem("A", "Add"));
            modeList.add(new SelectItem("V", "Verify"));
            modeList.add(new SelectItem("D", "Delete"));
        }
    }

    public void refresh() {
        P2AfundTrfFieldRefresh();
        P2PfundTrfFieldRefresh();
        generateMmidRefresh();
        panelDisplay();
        gridDetail = new ArrayList<>();
        p2AGridDetail = new ArrayList<>();
        p2PGridDetail = new ArrayList<>();
        p2ATempCurrentItem = null;
        p2PTempCurrentItem = null;
        disableProcessBtn = false;
        this.setDisableaccountno(true);
        setBtnvalue("");
//        this.setErrorMsg("");
//        this.setFunction("S");
//        this.setMode("0");
//        this.setAccountNo("");
//        this.setDisableaccountno(true);
        // this.setMobileno("");       
        //  this.setChargeAmount("");
        // this.setName("");
        //  this.setBeneficaryAccountno("");
        // this.setBeneficaryIFSC("");
        //this.setBeneficaryName("");
        //this.setRemark("");
        //this.setTranAmount(0);  
        // this.setNewAcNo(""); 
        //this.fieldDisableFlag = false;
    }

    public void panelDisplay() {
        this.setPanelDisplay("none");
        this.setPanelDisplay1("none");
        this.setPanelDisplay2("none");
        this.setPanelDisplay3("none");
        this.setPanelDisplay4("none");
        this.setPanelDisplay6("none");
        this.setPanelDisplay7("none");
    }

    public String exitForm() {
        return "case1";
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBtnvalue() {
        return btnvalue;
    }

    public void setBtnvalue(String btnvalue) {
        this.btnvalue = btnvalue;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getAdharno() {
        return adharno;
    }

    public void setAdharno(String adharno) {
        this.adharno = adharno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public IMPSMMIDGenerationPojo getTempCurrentItem() {
        return tempCurrentItem;
    }

    public void setTempCurrentItem(IMPSMMIDGenerationPojo tempCurrentItem) {
        this.tempCurrentItem = tempCurrentItem;
    }

    public List<IMPSMMIDGenerationPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<IMPSMMIDGenerationPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getMsgvalue() {
        return msgvalue;
    }

    public void setMsgvalue(String msgvalue) {
        this.msgvalue = msgvalue;
    }

    public String getRequest_beneficaryName() {
        return request_beneficaryName;
    }

    public void setRequest_beneficaryName(String request_beneficaryName) {
        this.request_beneficaryName = request_beneficaryName;
    }

    public String getBeneficaryAccountno() {
        return beneficaryAccountno;
    }

    public void setBeneficaryAccountno(String beneficaryAccountno) {
        this.beneficaryAccountno = beneficaryAccountno;
    }

    public String getBeneficaryIFSC() {
        return beneficaryIFSC;
    }

    public void setBeneficaryIFSC(String beneficaryIFSC) {
        this.beneficaryIFSC = beneficaryIFSC;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPanelDisplay1() {
        return panelDisplay1;
    }

    public void setPanelDisplay1(String panelDisplay1) {
        this.panelDisplay1 = panelDisplay1;
    }

    public String getPanelDisplay() {
        return panelDisplay;
    }

    public void setPanelDisplay(String panelDisplay) {
        this.panelDisplay = panelDisplay;
    }

    public String getPanelDisplay2() {
        return panelDisplay2;
    }

    public void setPanelDisplay2(String panelDisplay2) {
        this.panelDisplay2 = panelDisplay2;
    }

    public String getPanelDisplay3() {
        return panelDisplay3;
    }

    public void setPanelDisplay3(String panelDisplay3) {
        this.panelDisplay3 = panelDisplay3;
    }

    public String getPanelDisplay4() {
        return panelDisplay4;
    }

    public void setPanelDisplay4(String panelDisplay4) {
        this.panelDisplay4 = panelDisplay4;
    }

    public boolean isDisableaccountno() {
        return disableaccountno;
    }

    public void setDisableaccountno(boolean disableaccountno) {
        this.disableaccountno = disableaccountno;
    }

    public String getAccountDisplay() {
        return accountDisplay;
    }

    public void setAccountDisplay(String accountDisplay) {
        this.accountDisplay = accountDisplay;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public List<SelectItem> getChargeTypeList() {
        return chargeTypeList;
    }

    public void setChargeTypeList(List<SelectItem> chargeTypeList) {
        this.chargeTypeList = chargeTypeList;
    }

    public String getBtnAlertMessage() {
        return btnAlertMessage;
    }

    public void setBtnAlertMessage(String btnAlertMessage) {
        this.btnAlertMessage = btnAlertMessage;
    }

    public String getPanelDisplay5() {
        return panelDisplay5;
    }

    public void setPanelDisplay5(String panelDisplay5) {
        this.panelDisplay5 = panelDisplay5;
    }

    public IMPSMMIDGenerationPojo getP2ATempCurrentItem() {
        return p2ATempCurrentItem;
    }

    public void setP2ATempCurrentItem(IMPSMMIDGenerationPojo p2ATempCurrentItem) {
        this.p2ATempCurrentItem = p2ATempCurrentItem;
    }

    public List<IMPSMMIDGenerationPojo> getP2AGridDetail() {
        return p2AGridDetail;
    }

    public void setP2AGridDetail(List<IMPSMMIDGenerationPojo> p2AGridDetail) {
        this.p2AGridDetail = p2AGridDetail;
    }

    public boolean isDisableProcessBtn() {
        return disableProcessBtn;
    }

    public void setDisableProcessBtn(boolean disableProcessBtn) {
        this.disableProcessBtn = disableProcessBtn;
    }

    public boolean isFieldDisableFlag() {
        return fieldDisableFlag;
    }

    public void setFieldDisableFlag(boolean fieldDisableFlag) {
        this.fieldDisableFlag = fieldDisableFlag;
    }

    public String getBeneficaryMobileno() {
        return beneficaryMobileno;
    }

    public void setBeneficaryMobileno(String beneficaryMobileno) {
        this.beneficaryMobileno = beneficaryMobileno;
    }

    public String getBeneficaryMmid() {
        return beneficaryMmid;
    }

    public void setBeneficaryMmid(String beneficaryMmid) {
        this.beneficaryMmid = beneficaryMmid;
    }

    public List<IMPSMMIDGenerationPojo> getP2PGridDetail() {
        return p2PGridDetail;
    }

    public void setP2PGridDetail(List<IMPSMMIDGenerationPojo> p2PGridDetail) {
        this.p2PGridDetail = p2PGridDetail;
    }

    public String getPanelDisplay6() {
        return panelDisplay6;
    }

    public void setPanelDisplay6(String panelDisplay6) {
        this.panelDisplay6 = panelDisplay6;
    }

    public IMPSMMIDGenerationPojo getP2PTempCurrentItem() {
        return p2PTempCurrentItem;
    }

    public void setP2PTempCurrentItem(IMPSMMIDGenerationPojo p2PTempCurrentItem) {
        this.p2PTempCurrentItem = p2PTempCurrentItem;
    }

    public String getPanelDisplay7() {
        return panelDisplay7;
    }

    public void setPanelDisplay7(String panelDisplay7) {
        this.panelDisplay7 = panelDisplay7;
    }
}