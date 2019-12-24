/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

public class AccountMandate extends BaseBean {

    private List<SelectItem> txnFileTypeList;
    private String txnFileType;
    private String errMessage;
    private String function;
    private String modifyAcno;
    private List<SelectItem> modifyCritList;
    private String modifyCrit;
    private String proprietary;
    private List<SelectItem> proprietaryList;
    private List<SelectItem> categoryList;
    private String category;
    private String amtType;
    private List<SelectItem> amtTypeList;
    private String amt;
    private String drAcNo;
    private String drAcHolderName;
    private String drBankName;
    private List<SelectItem> finTranCodeTypeList;
    private String drFinTranCode;
    private String drFinTranCodeType;
    private String drAcType;
    private List<SelectItem> drAcTypeList;
    private String drUtilityCode;
    private String drMob;
    private String drEmail;
    private String crAcType;
    private List<SelectItem> crAcTypeList;
    private String crAcNo;
    private String crAcHolderName;
    private String crBankName;
    private String crFinTranCode;
    private String crFinTranCodeType;
    private String crUtilityCode;
    private String crMob;
    private String crEmail;
    private String frequency;
    private List<SelectItem> frequencyList;
    private String period;
    private List<SelectItem> periodList;
    private List<SelectItem> sequenceTypeList;
    private String sequenceType;
    private String ref1;
    private String ref2;
    private UploadedFile uploadedFile;
    private UploadedFile uploadedFile1;
    private String frDt;
    private String toDt;
    private String btnValue = "CREATE";
    private String confirmAlert;
    private String displayOnCreate = "none";
    private String displayImageUploadField;
    private String crHeader;
    private String drHeader;
    private String statusType;
    private List<SelectItem> statusTypeList;
    private boolean disableToDate;
    private boolean disableOnCradit;
    private boolean disableOnDebit;
    private CbsMandateDetailPojo currentItem;
    private List<CbsMandateDetailPojo> gridDetail;
    private List<SelectItem> functionList;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    private Validator valid = new Validator();
    NumberFormat formatter = new DecimalFormat("#.##");

    public AccountMandate() {
        try {
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            initData();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            List txnType = commonReport.getRefRecList("366");
            for (int i = 0; i < txnType.size(); i++) {
                Vector ele = (Vector) txnType.get(i);
                functionList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            amtTypeList = new ArrayList<SelectItem>();
            amtTypeList.add(new SelectItem("0", "--Select--"));
            amtTypeList.add(new SelectItem("F", "FIXED INSTALLMENT"));
            amtTypeList.add(new SelectItem("M", "MAX LIMIT"));

            frequencyList = new ArrayList<SelectItem>();
            frequencyList.add(new SelectItem("0", "--Select--"));
            List freqList = commonReport.getRefRecList("365");
            for (int i = 0; i < freqList.size(); i++) {
                Vector ele = (Vector) freqList.get(i);
                frequencyList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            periodList = new ArrayList<SelectItem>();
            periodList.add(new SelectItem("0", "--Select--"));
            periodList.add(new SelectItem("B", "BETWEEN PERIOD"));
            periodList.add(new SelectItem("C", "UNTILL CANCELATION"));

            sequenceTypeList = new ArrayList<SelectItem>();
            sequenceTypeList.add(new SelectItem("0", "--Select--"));
            List list = commonReport.getRefRecList("369");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                sequenceTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            crAcTypeList = new ArrayList<SelectItem>();
            drAcTypeList = new ArrayList<SelectItem>();
            crAcTypeList.add(new SelectItem("0", "--Select--"));
            drAcTypeList.add(new SelectItem("0", "--Select--"));
            list = commonReport.getRefRecList("368");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                crAcTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                drAcTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            modifyCritList = new ArrayList<SelectItem>();
            proprietaryList = new ArrayList<SelectItem>();
            modifyCritList.add(new SelectItem("0", "--Select--"));
            proprietaryList.add(new SelectItem("0", "--Select--"));
            list = commonReport.getRefRecList("367");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                modifyCritList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                proprietaryList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            categoryList = new ArrayList<SelectItem>();
            categoryList.add(new SelectItem("0", "--Select--"));
            list = commonReport.getRefRecList("326");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                categoryList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            txnFileTypeList = new ArrayList<SelectItem>();
            txnFileTypeList.add(new SelectItem("0", "--Select--"));
            txnFileTypeList.add(new SelectItem("ACH", "ACH"));
            txnFileTypeList.add(new SelectItem("ECS", "ECS"));

            finTranCodeTypeList = new ArrayList<SelectItem>();
            finTranCodeTypeList.add(new SelectItem("0", "--Select--"));
            finTranCodeTypeList.add(new SelectItem("IFSC", "IFSC"));
            finTranCodeTypeList.add(new SelectItem("MICR", "MICR"));
            finTranCodeTypeList.add(new SelectItem("IIN", "IIN"));

            statusTypeList = new ArrayList<SelectItem>();
            statusTypeList.add(new SelectItem("0", "--Select--"));
            statusTypeList.add(new SelectItem("N", "ACTIVE"));
            statusTypeList.add(new SelectItem("C", "CLOSED"));

            displayImageUploadField = "none";

            this.setCrHeader("Creditor Detail");
            this.setDrHeader("Debitor Detail");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void onblurTxnFileType() {
        try {
            if (txnFileType.equalsIgnoreCase("ECS")) {
                displayImageUploadField = "none";
                finTranCodeTypeList = new ArrayList<SelectItem>();
                finTranCodeTypeList.add(new SelectItem("0", "--Select--"));
                finTranCodeTypeList.add(new SelectItem("MICR", "MICR"));

                functionList = new ArrayList<SelectItem>();
                functionList.add(new SelectItem("0", "--Select--"));
                functionList.add(new SelectItem("CREATE", "CREATE"));
                functionList.add(new SelectItem("EDIT", "EDIT"));
            } else {
                displayImageUploadField = "";
                finTranCodeTypeList = new ArrayList<SelectItem>();
                finTranCodeTypeList.add(new SelectItem("0", "--Select--"));
                finTranCodeTypeList.add(new SelectItem("IFSC", "IFSC"));
                finTranCodeTypeList.add(new SelectItem("MICR", "MICR"));
                finTranCodeTypeList.add(new SelectItem("IIN", "IIN"));

                functionList = new ArrayList<SelectItem>();
                functionList.add(new SelectItem("0", "--Select--"));
                List txnType = commonReport.getRefRecList("366");
                for (int i = 0; i < txnType.size(); i++) {
                    Vector ele = (Vector) txnType.get(i);
                    functionList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (ApplicationException ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void onblurTxnType() {
        if (!(this.function.equalsIgnoreCase("CREATE") || this.function.equalsIgnoreCase("0"))) {
            this.displayOnCreate = "";
        } else {
            this.displayOnCreate = "none";
        }
        if (!this.function.equalsIgnoreCase("0")) {
            this.btnValue = this.function;
        } else {
            this.btnValue = "CREATE";
        }
    }

    public void onblurProprietary() {
        if (this.proprietary.equalsIgnoreCase("CREDIT")) {
            disableOnCradit = false;
            disableOnDebit = true;
            this.setCrHeader("Creditor Detail-->Destination Bank");
            this.setDrHeader("Debitor Detail-->Own Bank");
        } else if (this.proprietary.equalsIgnoreCase("DEBIT")) {
            disableOnCradit = true;
            disableOnDebit = false;
            this.setCrHeader("Creditor Detail-->Own Bank");
            this.setDrHeader("Debitor Detail-->Destination Bank");
        }
    }

    public void onblurPeriodType() {
        if (period.equalsIgnoreCase("C")) {
            disableToDate = true;
            toDt = "";
        } else {
            disableToDate = false;
        }
    }

    public void onblurAcNo() {
        try {
            String[] acDetail;
            this.errMessage = "";
            drAcHolderName = "";
            drBankName = "";
            drFinTranCodeType = "";
            drFinTranCode = "";

            crAcHolderName = "";
            crBankName = "";
            crFinTranCodeType = "";
            crFinTranCode = "";

            if (this.proprietary.equalsIgnoreCase("CREDIT") && !drAcNo.equalsIgnoreCase("")) {
                if (drAcType == null || drAcType.equalsIgnoreCase("0")) {
                    this.errMessage = "Please Select Debitor A/C Type.";
                    return;
                }
                String validFlag = commonReport.validateAccountType(drAcType, drAcNo.substring(2, 4));
                String brnCode = commonReport.getBrncodeByAcno(drAcNo);
                if (validFlag.equalsIgnoreCase("false")) {
                    this.errMessage = "Debitor A/C Type not match with filled A/C no. type.";
                    return;
                }
                if (!(getOrgnBrCode().equals("90") || brnCode.equalsIgnoreCase(getOrgnBrCode()))) {
                    this.errMessage = "Please fill own branch A/C No.";
                    return;
                }
                acDetail = otherNpciRemote.getAcHolderDetail(drAcNo);
                drAcHolderName = acDetail[0];
                drBankName = acDetail[1];
                if (txnFileType.equalsIgnoreCase("ACH")) {
                    drFinTranCodeType = "IFSC";
                    drFinTranCode = acDetail[2];
                } else if (txnFileType.equalsIgnoreCase("ECS")) {
                    drFinTranCodeType = "MICR";
                    drFinTranCode = acDetail[4]; // for micr
                }
            } else if (this.proprietary.equalsIgnoreCase("DEBIT") && !crAcNo.equalsIgnoreCase("")) {
                if (crAcType == null || crAcType.equalsIgnoreCase("0")) {
                    this.errMessage = "Please Select Creditor A/C Type.";
                    return;
                }
                String validFlag = commonReport.validateAccountType(crAcType, crAcNo.substring(2, 4));
                String brnCode = commonReport.getBrncodeByAcno(crAcNo);
                if (validFlag.equalsIgnoreCase("false")) {
                    this.errMessage = "Creditor A/C Type not match with filled A/C no. type.";
                    return;
                }
                if (!(getOrgnBrCode().equals("90") || brnCode.equalsIgnoreCase(getOrgnBrCode()))) {
                    this.errMessage = "Please fill own branch A/C No.";
                    return;
                }
                acDetail = otherNpciRemote.getAcHolderDetail(crAcNo);
                crAcHolderName = acDetail[0];
                crBankName = acDetail[1];
                if (txnFileType.equalsIgnoreCase("ACH")) {
                    crFinTranCodeType = "IFSC";
                    crFinTranCode = acDetail[2];
                } else if (txnFileType.equalsIgnoreCase("ECS")) {
                    crFinTranCodeType = "MICR";
                    crFinTranCode = acDetail[4]; // for micr
                }
            }
        } catch (ApplicationException ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getDataToModify() {
        try {
            if (this.txnFileType == null || this.txnFileType.equals("")) {
                this.setErrMessage("Please select Txn File Type.");
                return;
            }
            if (this.function == null || this.function.equals("")) {
                this.setErrMessage("Please select function.");
                return;
            }
            if ((this.function.equals("EDIT") || this.function.equals("AMEND")
                    || this.function.equals("CANCEL")) && validateAccount(this.modifyAcno)) {
                gridDetail = otherNpciRemote.retrieveMandateDataNew(txnFileType, function, modifyCrit, modifyAcno, getOrgnBrCode());
                if (gridDetail.isEmpty()) {
                    this.setErrMessage("There is no data.");
                    return;
                }
                this.setErrMessage("Please select a row from table to modify.");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        try {
            if (currentItem == null) {
                this.setErrMessage("Please select row from table.");
                return;
            }
            this.proprietary = currentItem.getProprietary();
            this.category = currentItem.getCategory();
            this.amtType = currentItem.getAmountFlag();
            this.amt = currentItem.getAmount();
            this.drAcNo = currentItem.getDebtorAcno();
            this.drAcHolderName = currentItem.getDebtorName();
            this.drBankName = currentItem.getDebtorBankName();
            this.drAcType = currentItem.getDebtorAcType();
            this.drFinTranCodeType = currentItem.getDebtorFinCodeType();
            this.drFinTranCode = currentItem.getDebtorIFSC();
            this.drUtilityCode = currentItem.getDebtorUtilityCode();
            this.drMob = currentItem.getDebtorMobile();
            this.drEmail = currentItem.getDebtorEmail();
            this.crAcType = currentItem.getCreditorAcType();
            this.crAcNo = currentItem.getCreditorAcno();
            this.crAcHolderName = currentItem.getCreditorName();
            this.crBankName = currentItem.getCreditorBankName();
            this.crFinTranCodeType = currentItem.getCreditorFinCodeType();
            this.crFinTranCode = currentItem.getCreditorIFSC();
            this.crUtilityCode = currentItem.getCreditorUtilityCode();
            this.crMob = currentItem.getCreditorMobile();
            this.crEmail = currentItem.getCreditorEmail();
            this.frequency = currentItem.getFrequency();
            this.period = currentItem.getPeriodType();
            this.sequenceType = currentItem.getSequenceType();
            this.statusType = currentItem.getFlag();
            this.ref1 = currentItem.getRef1();
            this.ref2 = currentItem.getRef2();
            this.frDt = currentItem.getFromDate();
            this.toDt = currentItem.getToDate();
            if (this.proprietary.equalsIgnoreCase("CREDIT")) {
                disableOnCradit = false;
                disableOnDebit = true;
            } else if (this.proprietary.equalsIgnoreCase("DEBIT")) {
                disableOnCradit = true;
                disableOnDebit = false;
            }
            if (period.equalsIgnoreCase("C")) {
                disableToDate = true;
            } else {
                disableToDate = false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void populateAlertMessage() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setErrMessage("Please select function.");
                return;
            }
            if (this.function.equals("CREATE")) {
                this.setConfirmAlert("Are you sure to create this entry ?");
            } else if (this.function.equals("AMEND")) {
                this.setConfirmAlert("Are you sure to amend this entry ?");
            } else if (this.function.equals("EDIT")) {
                this.setConfirmAlert("Are you sure to edit this entry ?");
            } else if (this.function.equals("CANCEL")) {
                this.setConfirmAlert("Are you sure to cancel this entry ?");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            if (validateField() && (validateAccount(this.drAcNo) || validateAccount(this.crAcNo))) {
                String result = "";
                CbsMandateDetailPojo data = new CbsMandateDetailPojo();
                data.setTxnFileType(this.txnFileType);
                data.setTransType(this.function);
                data.setProprietary(this.proprietary);
                data.setCategory(this.category);
                data.setAmountFlag(this.amtType);
                data.setAmount(this.amt);
                data.setFrequency(this.frequency);
                data.setSequenceType(this.sequenceType);
                data.setPeriodType(this.period);
                data.setFromDate(ymd.format(dmy.parse(this.frDt)));
                data.setToDate(toDt.equalsIgnoreCase("") ? "" : ymd.format(dmy.parse(this.toDt)));
                data.setCreditorAcno(this.crAcNo);
                data.setCreditorName(this.crAcHolderName);
                data.setCreditorAcType(this.crAcType);
                data.setCreditorBankName(this.crBankName);
                data.setCreditorFinCodeType(this.crFinTranCodeType);
                data.setCreditorIFSC(this.crFinTranCode);
                data.setCreditorMobile(this.crMob);
                data.setCreditorEmail(this.crEmail);
                data.setCreditorUtilityCode(this.crUtilityCode);
                data.setDebtorAcno(this.drAcNo);
                data.setDebtorName(this.drAcHolderName);
                data.setDebtorAcType(this.drAcType);
                data.setDebtorBankName(this.drBankName);
                data.setDebtorFinCodeType(this.drFinTranCodeType);
                data.setDebtorIFSC(this.drFinTranCode);
                data.setDebtorMobile(this.drMob);
                data.setDebtorEmail(this.drEmail);
                data.setDebtorUtilityCode(this.drUtilityCode == null ? "" : this.drUtilityCode);
//                data.setFlag(this.statusType);
                data.setRef1(this.ref1);
                data.setRef2(this.ref2);
                data.setMandateReceivingBranch(getOrgnBrCode());
                data.setMandateReceivedBy(getUserName());
                data.setMandateReceivedDate(getTodayDate());
                if (this.function.equals("CREATE")) {
                    data.setFlag("N");
                    String bankUmrn = "";
                    if (this.txnFileType.equalsIgnoreCase("ACH")) {
                        Long umrn = otherNpciRemote.getUmrn(txnFileType);
                        String um = String.format("%013d", umrn);
                        bankUmrn = "CRMN" + um.toString();
                        data.setcBSUmrn(bankUmrn);
                    } else {
                        Long umrn = otherNpciRemote.getUmrn(txnFileType);
                        String um = String.format("%07d", umrn);
                        bankUmrn = "REF" + um.toString();
                        data.setcBSUmrn(bankUmrn);
                    }
                    data.setcHIUmrn("");
                    //**** Create or Modify ACH/ECS input data
                    result = otherNpciRemote.mandateProcess(data);
                    //-------------------------

                    if (txnFileType.equalsIgnoreCase("ACH")) {
                        if (uploadedFile.getContentType().equals("image/jpeg") || uploadedFile.getContentType().equals("image/jpg")) {
                            errMessage = uploadImage(uploadedFile, bankUmrn + ".jpg");
                        }
                        if (uploadedFile1.getContentType().equals("image/tiff") || uploadedFile1.getContentType().equals("image/tif")) {
                            errMessage = uploadImage(uploadedFile1, bankUmrn + ".tiff");
                        }
                    }
                    if (!result.equalsIgnoreCase("true")) {
                        this.setErrMessage(result);
                        return;
                    }
                    refreshForm();
                    this.setErrMessage("Entry has been added successfully for UMRN " + bankUmrn);
                } else if ((!(this.function.equals("")) && !(this.function.equals("CREATE")))) {
                    if (currentItem == null) {
                        this.setErrMessage("Please select a row from table to modify.");
                        return;
                    }
                    if (txnFileType.equalsIgnoreCase("ACH")) {
                        /* Upload file */
                        if ((uploadedFile != null) && ((uploadedFile.getContentType().equals("image/jpeg") || uploadedFile.getContentType().equals("image/jpg")))) {
                            errMessage = uploadImage(uploadedFile, currentItem.getcBSUmrn() + ".jpg");
                        }
                        if ((uploadedFile1 != null) && ((uploadedFile1.getContentType().equals("image/tiff") || uploadedFile1.getContentType().equals("image/tif")))) {
                            errMessage = uploadImage(uploadedFile1, currentItem.getcBSUmrn() + ".tiff");
                        }
                    }
                    data.setTransTypePrevious(currentItem.getTransType());
                    data.setUpdateBy(getUserName());
                    data.setUpdateDate(getTodayDate());
                    // data.setFlag(currentItem.getFlag());
                    if ((txnFileType.equalsIgnoreCase("ECS") || txnFileType.equalsIgnoreCase("ACH"))
                            && function.equalsIgnoreCase("EDIT")) {
                        data.setFlag(statusType);
                    } else {
                        data.setFlag("N");
                    }

                    data.setUpdateMode(currentItem.getUpdateMode());
                    data.setcBSUmrn(currentItem.getcBSUmrn());
                    result = otherNpciRemote.mandateProcess(data);
                    if (!result.equalsIgnoreCase("true")) {
                        this.setErrMessage(result);
                        return;
                    }
                    String a = this.function, b = currentItem.getcBSUmrn();
                    refreshForm();
                    this.setErrMessage("Entry has been " + a.toLowerCase() + "ed" + " successfully for UMRN " + b);
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String uploadImage(UploadedFile uploadedFile, String fileName) {
        String msg = "";
        /* Upload file */
        if (!(uploadedFile == null || uploadedFile.equals(""))) {
            long fileSize = 200000; // size in byte
            String uploadedFileType = uploadedFile.getContentType();
            if (uploadedFile.getSize() > fileSize) {
                msg = "File size should be less than equal 200kb !";
            }
            if (uploadedFileType.equals("image/jpeg") || uploadedFileType.equals("image/jpg") || uploadedFileType.equals("image/tiff") || uploadedFileType.equals("image/tif")) {
                try {
//                    File dir = null;
//                    String osName = System.getProperty("os.name");
//                    String file_name = "";
//                    if (osName.equals("Linux")) {
//                        dir = new File(File.separatorChar + "opt" + File.separatorChar + "mms" + File.separatorChar + "mandate-images" + File.separatorChar);
//                    } else {
//                        dir = new File("C:" + File.separatorChar + "opt" + File.separatorChar + "mms" + File.separatorChar + "mandate-images" + File.separatorChar);
//                    }

                    File dir = new File(File.separatorChar + "opt" + File.separatorChar + "mms" + File.separatorChar + "mandate-images" + File.separatorChar);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file1 = new File(dir.getCanonicalPath() + File.separatorChar + fileName);
                    /**
                     * *Writing zip file into file system**
                     */
                    byte[] b = uploadedFile.getBytes();
                    FileOutputStream fos = new FileOutputStream(file1);
                    fos.write(b);
                    fos.close();
                    /**
                     * *End here**
                     */
                    msg = fileName + " has been uploaded successfully.";
                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.setErrMessage(ex.getMessage());
                }
            } else {
                msg = "File must be jpg, jpeg, png or gif type !";
            }
        }
        return msg;
    }

    public boolean validateAccount(String valAcno) {
        try {
            if (valAcno == null || valAcno.equals("")) {
                this.setErrMessage("There should be proper a/c number.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateField() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setErrMessage("Please select function.");
                return false;
            }
            if (this.proprietary == null || this.proprietary.equals("0")) {
                this.setErrMessage("Please select proprietary.");
                return false;
            }
            if (this.category == null || this.category.equals("0")) {
                this.setErrMessage("Please select category.");
                return false;
            }
            if (this.amtType == null || this.amtType.equals("0")) {
                this.setErrMessage("Please select amount type.");
                return false;
            }
            if (validateAmount() == false) {
                return false;
            }
            if (this.frequency == null || this.frequency.equals("0")) {
                this.setErrMessage("Please select frequency.");
                return false;
            }
            if (this.sequenceType == null || this.sequenceType.equals("0")) {
                this.setErrMessage("Please select sequence type.");
                return false;
            }
            if (this.period == null || this.period.equals("0")) {
                this.setErrMessage("Please select period.");
                return false;
            }
            if ((this.frDt == null || this.frDt.equals(""))) {
                if (!new Validator().validateDate_dd_mm_yyyy(this.frDt)) {
                    this.setErrMessage("Please fill proper from date.");
                    return false;
                }
            }
            if (period.equalsIgnoreCase("B")) {
                if ((this.toDt == null || this.toDt.equals(""))) {
                    if (!new Validator().validateDate_dd_mm_yyyy(this.toDt)) {
                        this.setErrMessage("Please fill proper to date.");
                        return false;
                    }
                }
                if (!(this.frDt == null || this.frDt.equals(""))
                        && !(this.toDt == null || this.toDt.equals(""))) {
                    if (dmy.parse(this.frDt).compareTo(dmy.parse(this.toDt)) > 0) {
                        this.setErrMessage("From date can not be greater than to date.");
                        return false;
                    }
                }
            }
            if ((this.statusType == null || this.statusType.equals("0"))) {
                this.setErrMessage("Please select status.");
                return false;
            }
            if ((this.ref1 == null || this.ref1.equals(""))) {
                this.setErrMessage("Please fill refrence 1.");
                return false;
            }
            if ((this.ref2 == null || this.ref2.equals(""))) {
                this.setErrMessage("Please fill refrence 2.");
                return false;
            }
            if ((this.crAcType == null || this.crAcType.equals("0"))) {
                this.setErrMessage("Please select creditor a/c type.");
                return false;
            }
            if ((this.crAcNo == null || this.crAcNo.equals(""))) {
                this.setErrMessage("Please fill creditor a/c no.");
                return false;
            }

            if (this.txnFileType.equalsIgnoreCase("ECS") && this.proprietary.equalsIgnoreCase("CREDIT")) {
                if (!(this.crAcNo == null || this.crAcNo.equals(""))) {
                    if (crAcNo.length() > 15) {
                        this.setErrMessage("Creditor a/c no. must not greater than 15 digit.!");
                        return false;
                    }
                }
            }


            if ((this.crAcHolderName == null || this.crAcHolderName.equals(""))) {
                this.setErrMessage("Please fill creditor name.");
                return false;
            }

            if ((this.crBankName == null || this.crBankName.equals(""))) {
                this.setErrMessage("Please fill creditor bank name.");
                return false;
            }

            if (crFinTranCodeType.equalsIgnoreCase("IFSC")) {
                if ((this.crFinTranCode == null || this.crFinTranCode.equals(""))) {
                    this.setErrMessage("Please fill creditor IFSC");
                    return false;
                }
                if (!(this.crFinTranCode == null || this.crFinTranCode.equals(""))) {
                    if (this.crFinTranCode.length() != 11) {
                        this.setErrMessage("Length of creditor Bank IFSC should be of 9 digit.");
                        return false;
                    }
                    if (ParseFileUtil.isValidIfsc(this.crFinTranCode) == false) {
                        this.setErrMessage("Only alphabet and numeric digits are allowed for creditor Bank  IFSC.");
                        return false;
                    }
                }
            } else if (crFinTranCodeType.equalsIgnoreCase("MICR")) {
                if ((this.crFinTranCode == null || this.crFinTranCode.equals(""))) {
                    this.setErrMessage("Please fill creditor MICR");
                    return false;
                }
                if (!(this.crFinTranCode == null || this.crFinTranCode.equals(""))) {
                    if (this.crFinTranCode.length() != 9) {
                        this.setErrMessage("Length of creditor Bank MICR should be of 9 digit.");
                        return false;
                    }
                    if (ParseFileUtil.isNumeric(this.crFinTranCode) == false) {
                        this.setErrMessage("Only numeric digits are allowed for creditor Bank  MICR.");
                        return false;
                    }
                }
            } else if (crFinTranCodeType.equalsIgnoreCase("IIN")) {
                if ((this.crFinTranCode == null || this.crFinTranCode.equals(""))) {
                    this.setErrMessage("Please fill creditor IIN");
                    return false;
                }
                if (!(this.crFinTranCode == null || this.crFinTranCode.equals(""))) {
                    if (this.crFinTranCode.length() != 6) {
                        this.setErrMessage("Length of creditor Bank IIN should be of 6 digit.");
                        return false;
                    }
                    if (ParseFileUtil.isNumeric(this.crFinTranCode) == false) {
                        this.setErrMessage("Only alphabet and numeric digits are allowed for creditor Bank  IIN.");
                        return false;
                    }
                }
            }


            if (this.txnFileType.equalsIgnoreCase("ACH") && this.proprietary.equalsIgnoreCase("CREDIT") && (this.crUtilityCode == null || this.crUtilityCode.equals(""))) {
                this.setErrMessage("Please fill creditor utility code ");
                return false;
            }

            if (this.txnFileType.equalsIgnoreCase("ACH")) {
                if (!(!(this.crMob == null || this.crMob.equals("")) || (!(this.crEmail == null || this.crEmail.equals(""))))) {
                    this.setErrMessage("Please fill creditor Mobile Or Email. ");
                    return false;
                }
            }

            if (!(this.crMob.equalsIgnoreCase("") || this.crMob == null)) {
                if (!valid.validateMobile(this.crMob)) {
                    this.setErrMessage("Please Enter Valid creditor Mobile Number");
                }
            }
            if (!(this.crEmail.equalsIgnoreCase("") || this.crEmail == null)) {
                if (!valid.validateEmail(this.crEmail)) {
                    this.setErrMessage("Please Enter Valid creditor Email Id");
                    return false;
                }
            }

            if ((this.drAcType == null || this.drAcType.equals("0"))) {
                this.setErrMessage("Please select debtor a/c type.");
                return false;
            }
            if ((this.drAcNo == null || this.drAcNo.equals(""))) {
                this.setErrMessage("Please fill debtor a/c no.");
                return false;
            }

            if (this.txnFileType.equalsIgnoreCase("ECS") && this.proprietary.equalsIgnoreCase("DEBIT")) {
                if (!(this.drAcNo == null || this.drAcNo.equals(""))) {
                    if (drAcNo.length() > 15) {
                        this.setErrMessage("Debtor a/c no. must not greater than 15 digit.!");
                        return false;
                    }
                }
            }

            if ((this.drAcHolderName == null || this.drAcHolderName.equals(""))) {
                this.setErrMessage("Please fill debtor name.");
                return false;
            }

            if ((this.drBankName == null || this.drBankName.equals(""))) {
                this.setErrMessage("Please fill debtor bank name.");
                return false;
            }

            if (drFinTranCodeType.equalsIgnoreCase("IFSC")) {
                if ((this.drFinTranCode == null || this.drFinTranCode.equals(""))) {
                    this.setErrMessage("Please fill debtor IFSC");
                    return false;
                }
                if (!(this.drFinTranCode == null || this.drFinTranCode.equals(""))) {
                    if (this.drFinTranCode.length() != 11) {
                        this.setErrMessage("Length of debtor Bank IFSC should be of 11 digit.");
                        return false;
                    }
                    if (ParseFileUtil.isValidIfsc(this.drFinTranCode) == false) {
                        this.setErrMessage("Only alphabet and numeric digits are allowed for debtor Bank  IFSC.");
                        return false;
                    }
                }
            } else if (drFinTranCodeType.equalsIgnoreCase("MICR")) {
                if ((this.drFinTranCode == null || this.drFinTranCode.equals(""))) {
                    this.setErrMessage("Please fill debtor MICR");
                    return false;
                }
                if (!(this.drFinTranCode == null || this.drFinTranCode.equals(""))) {
                    if (this.drFinTranCode.length() != 9) {
                        this.setErrMessage("Length of debtor Bank MICR should be of 9 digit.");
                        return false;
                    }
                    if (ParseFileUtil.isNumeric(this.drFinTranCode) == false) {
                        this.setErrMessage("Only numeric digits are allowed for debtor Bank  MICR.");
                        return false;
                    }
                }
            } else if (drFinTranCodeType.equalsIgnoreCase("IIN")) {
                if ((this.drFinTranCode == null || this.drFinTranCode.equals(""))) {
                    this.setErrMessage("Please fill debtor IIN");
                    return false;
                }
                if (!(this.drFinTranCode == null || this.drFinTranCode.equals(""))) {
                    if (this.drFinTranCode.length() != 6) {
                        this.setErrMessage("Length of debtor Bank IIN should be of 6 digit.");
                        return false;
                    }
                    if (ParseFileUtil.isNumeric(this.drFinTranCode) == false) {
                        this.setErrMessage("Only alphabet and numeric digits are allowed for debtor Bank  IIN.");
                        return false;
                    }
                }
            }
            //Change on 09/10/2017
//            if (this.txnFileType.equalsIgnoreCase("ACH") && this.proprietary.equalsIgnoreCase("DEBIT") && (this.drUtilityCode == null || this.drUtilityCode.equals(""))) {
//                this.setErrMessage("Please fill debtor utility code ");
//                return false;
//            }
            if (this.txnFileType.equalsIgnoreCase("ACH")) {
                if (!(!(this.drMob == null || this.drMob.equals("")) || (!(this.drEmail == null || this.drEmail.equals(""))))) {
                    this.setErrMessage("Please fill debtor Mobile Or Email. ");
                    return false;
                }
            }
            if (!(this.drMob.equalsIgnoreCase("") || this.drMob == null)) {
                if (!valid.validateMobile(this.drMob)) {
                    this.setErrMessage("Please Enter Valid Debitor Mobile Number");
                }
            }
            if (!(this.drEmail.equalsIgnoreCase("") || this.drEmail == null)) {
                if (!valid.validateEmail(this.drEmail)) {
                    this.setErrMessage("Please Enter Valid Debitor Email Id");
                    return false;
                }
            }
            if (txnFileType.equalsIgnoreCase("ACH")) {
                if (this.function.equalsIgnoreCase("CREATE")) {
                    if ((uploadedFile == null) || (uploadedFile1 == null)) {
                        this.setErrMessage("Please Select Image");
                        return false;
                    }
                    if ((uploadedFile != null) && (!(uploadedFile.getContentType().equals("image/jpeg") || uploadedFile.getContentType().equals("image/jpg")))) {
                        this.setErrMessage("Please Select JPEG Image");
                        return false;
                    }
                    if ((uploadedFile1 != null) && (!(uploadedFile1.getContentType().equals("image/tiff") || uploadedFile1.getContentType().equals("image/tif")))) {
                        this.setErrMessage("Please Select TIFF Image");
                        return false;
                    }
                }
                if ((uploadedFile != null) && (!(uploadedFile.getContentType().equals("image/jpeg") || uploadedFile.getContentType().equals("image/jpg")))) {
                    this.setErrMessage("Please Select JPEG Image");
                    return false;
                }
                if ((uploadedFile1 != null) && (!(uploadedFile1.getContentType().equals("image/tiff") || uploadedFile1.getContentType().equals("image/tif")))) {
                    this.setErrMessage("Please Select TIFF Image");
                    return false;
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            Matcher m = p.matcher(this.amt);
            if (this.amt == null || this.amt.equals("") || this.amt.equals("0")) {
                this.setErrMessage("Please fill proper amount.");
                return false;
            } else if (m.matches() != true) {
                this.setErrMessage("Please fill proper amount.");
                return false;
            } else if (this.amt.equalsIgnoreCase("0") || this.amt.equalsIgnoreCase("0.0")) {
                this.setErrMessage("Amount can not be zero.");
                return false;
            } else if (new BigDecimal(this.amt).compareTo(new BigDecimal("0")) == -1) {
                this.setErrMessage("Amount can not be negative.");
                return false;
            }
            if (this.amt.contains(".")) {
                if (this.amt.indexOf(".") != this.amt.lastIndexOf(".")) {
                    this.setErrMessage("Invalid amount. Please fill the amount correctly.");
                    return false;
                } else if (this.amt.substring(amt.indexOf(".") + 1).length() > 2) {
                    this.setErrMessage("Please fill the amount upto two decimal places.");
                    return false;
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void refreshFieldOnFunction() {
        this.setErrMessage("");
        this.modifyAcno = "";
        this.modifyCrit = "0";
        this.proprietary = "0";
        this.category = "0";
        this.amtType = "0";
        this.amt = "";
        this.drAcNo = "";
        this.drAcHolderName = "";
        this.drBankName = "";
        this.drFinTranCodeType = "0";
        this.drFinTranCode = "";
        this.drAcType = "0";
        this.drUtilityCode = "";
        this.drMob = "";
        this.drEmail = "";
        this.crAcType = "0";
        this.crAcNo = "";
        this.crAcHolderName = "";
        this.crBankName = "";
        this.crFinTranCodeType = "0";
        this.crFinTranCode = "";
        this.crUtilityCode = "";
        this.crMob = "";
        this.crEmail = "";
        this.frequency = "0";
        this.period = "0";
        this.sequenceType = "0";
        this.statusType = "0";
        this.ref1 = "";
        this.ref2 = "";
        this.frDt = "";
        this.toDt = "";
        this.setCurrentItem(new CbsMandateDetailPojo());
//        this.gridDetail = new ArrayList<CbsMandateDetailPojo>();
        this.uploadedFile = null;
        this.uploadedFile1 = null;

    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setFunction("0");
        this.modifyAcno = "";
        this.modifyCrit = "0";
        this.proprietary = "0";
        this.category = "0";
        this.amtType = "0";
        this.amt = "";
        this.drAcNo = "";
        this.drAcHolderName = "";
        this.drBankName = "";
        this.drFinTranCodeType = "0";
        this.drFinTranCode = "";
        this.drAcType = "0";
        this.drUtilityCode = "";
        this.drMob = "";
        this.drEmail = "";
        this.crAcType = "0";
        this.crAcNo = "";
        this.crAcHolderName = "";
        this.crBankName = "";
        this.crFinTranCodeType = "0";
        this.crFinTranCode = "";
        this.crUtilityCode = "";
        this.crMob = "";
        this.crEmail = "";
        this.frequency = "0";
        this.period = "0";
        this.sequenceType = "0";
        this.statusType = "0";
        this.ref1 = "";
        this.ref2 = "";
        this.frDt = "";
        this.toDt = "";
        this.setCurrentItem(new CbsMandateDetailPojo());
        this.gridDetail = new ArrayList<CbsMandateDetailPojo>();
        this.setBtnValue("CREATE");
        this.setConfirmAlert("");
        this.uploadedFile = null;
        this.uploadedFile1 = null;
        this.displayOnCreate = "none";
        this.disableToDate = false;
        this.disableOnCradit = false;
        this.disableOnDebit = false;
        this.setCrHeader("Creditor Detail");
        this.setDrHeader("Debitor Detail");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getModifyAcno() {
        return modifyAcno;
    }

    public void setModifyAcno(String modifyAcno) {
        this.modifyAcno = modifyAcno;
    }

    public List<SelectItem> getModifyCritList() {
        return modifyCritList;
    }

    public void setModifyCritList(List<SelectItem> modifyCritList) {
        this.modifyCritList = modifyCritList;
    }

    public String getModifyCrit() {
        return modifyCrit;
    }

    public void setModifyCrit(String modifyCrit) {
        this.modifyCrit = modifyCrit;
    }

    public String getProprietary() {
        return proprietary;
    }

    public void setProprietary(String proprietary) {
        this.proprietary = proprietary;
    }

    public List<SelectItem> getProprietaryList() {
        return proprietaryList;
    }

    public void setProprietaryList(List<SelectItem> proprietaryList) {
        this.proprietaryList = proprietaryList;
    }

    public List<SelectItem> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public List<SelectItem> getAmtTypeList() {
        return amtTypeList;
    }

    public void setAmtTypeList(List<SelectItem> amtTypeList) {
        this.amtTypeList = amtTypeList;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDrAcNo() {
        return drAcNo;
    }

    public void setDrAcNo(String drAcNo) {
        this.drAcNo = drAcNo;
    }

    public String getDrAcHolderName() {
        return drAcHolderName;
    }

    public void setDrAcHolderName(String drAcHolderName) {
        this.drAcHolderName = drAcHolderName;
    }

    public String getDrBankName() {
        return drBankName;
    }

    public void setDrBankName(String drBankName) {
        this.drBankName = drBankName;
    }

    public String getDrAcType() {
        return drAcType;
    }

    public void setDrAcType(String drAcType) {
        this.drAcType = drAcType;
    }

    public List<SelectItem> getDrAcTypeList() {
        return drAcTypeList;
    }

    public void setDrAcTypeList(List<SelectItem> drAcTypeList) {
        this.drAcTypeList = drAcTypeList;
    }

    public String getDrUtilityCode() {
        return drUtilityCode;
    }

    public void setDrUtilityCode(String drUtilityCode) {
        this.drUtilityCode = drUtilityCode;
    }

    public String getDrMob() {
        return drMob;
    }

    public void setDrMob(String drMob) {
        this.drMob = drMob;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getCrAcType() {
        return crAcType;
    }

    public void setCrAcType(String crAcType) {
        this.crAcType = crAcType;
    }

    public List<SelectItem> getCrAcTypeList() {
        return crAcTypeList;
    }

    public void setCrAcTypeList(List<SelectItem> crAcTypeList) {
        this.crAcTypeList = crAcTypeList;
    }

    public String getCrAcNo() {
        return crAcNo;
    }

    public void setCrAcNo(String crAcNo) {
        this.crAcNo = crAcNo;
    }

    public String getCrAcHolderName() {
        return crAcHolderName;
    }

    public void setCrAcHolderName(String crAcHolderName) {
        this.crAcHolderName = crAcHolderName;
    }

    public String getCrBankName() {
        return crBankName;
    }

    public void setCrBankName(String crBankName) {
        this.crBankName = crBankName;
    }

    public String getCrUtilityCode() {
        return crUtilityCode;
    }

    public void setCrUtilityCode(String crUtilityCode) {
        this.crUtilityCode = crUtilityCode;
    }

    public String getCrMob() {
        return crMob;
    }

    public void setCrMob(String crMob) {
        this.crMob = crMob;
    }

    public String getCrEmail() {
        return crEmail;
    }

    public void setCrEmail(String crEmail) {
        this.crEmail = crEmail;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<SelectItem> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<SelectItem> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<SelectItem> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<SelectItem> periodList) {
        this.periodList = periodList;
    }

    public List<SelectItem> getSequenceTypeList() {
        return sequenceTypeList;
    }

    public void setSequenceTypeList(List<SelectItem> sequenceTypeList) {
        this.sequenceTypeList = sequenceTypeList;
    }

    public String getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile1() {
        return uploadedFile1;
    }

    public void setUploadedFile1(UploadedFile uploadedFile1) {
        this.uploadedFile1 = uploadedFile1;
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

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getConfirmAlert() {
        return confirmAlert;
    }

    public void setConfirmAlert(String confirmAlert) {
        this.confirmAlert = confirmAlert;
    }

    public CbsMandateDetailPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CbsMandateDetailPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<CbsMandateDetailPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CbsMandateDetailPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public SimpleDateFormat getMdy() {
        return mdy;
    }

    public void setMdy(SimpleDateFormat mdy) {
        this.mdy = mdy;
    }

    public String getDisplayOnCreate() {
        return displayOnCreate;
    }

    public void setDisplayOnCreate(String displayOnCreate) {
        this.displayOnCreate = displayOnCreate;
    }

    public boolean isDisableToDate() {
        return disableToDate;
    }

    public void setDisableToDate(boolean disableToDate) {
        this.disableToDate = disableToDate;
    }

    public boolean isDisableOnCradit() {
        return disableOnCradit;
    }

    public void setDisableOnCradit(boolean disableOnCradit) {
        this.disableOnCradit = disableOnCradit;
    }

    public boolean isDisableOnDebit() {
        return disableOnDebit;
    }

    public void setDisableOnDebit(boolean disableOnDebit) {
        this.disableOnDebit = disableOnDebit;
    }

    public List<SelectItem> getTxnFileTypeList() {
        return txnFileTypeList;
    }

    public void setTxnFileTypeList(List<SelectItem> txnFileTypeList) {
        this.txnFileTypeList = txnFileTypeList;
    }

    public String getTxnFileType() {
        return txnFileType;
    }

    public void setTxnFileType(String txnFileType) {
        this.txnFileType = txnFileType;
    }

    public String getDisplayImageUploadField() {
        return displayImageUploadField;
    }

    public void setDisplayImageUploadField(String displayImageUploadField) {
        this.displayImageUploadField = displayImageUploadField;
    }

    public List<SelectItem> getFinTranCodeTypeList() {
        return finTranCodeTypeList;
    }

    public void setFinTranCodeTypeList(List<SelectItem> finTranCodeTypeList) {
        this.finTranCodeTypeList = finTranCodeTypeList;
    }

    public String getDrFinTranCodeType() {
        return drFinTranCodeType;
    }

    public void setDrFinTranCodeType(String drFinTranCodeType) {
        this.drFinTranCodeType = drFinTranCodeType;
    }

    public String getCrFinTranCodeType() {
        return crFinTranCodeType;
    }

    public void setCrFinTranCodeType(String crFinTranCodeType) {
        this.crFinTranCodeType = crFinTranCodeType;
    }

    public String getDrFinTranCode() {
        return drFinTranCode;
    }

    public void setDrFinTranCode(String drFinTranCode) {
        this.drFinTranCode = drFinTranCode;
    }

    public String getCrFinTranCode() {
        return crFinTranCode;
    }

    public void setCrFinTranCode(String crFinTranCode) {
        this.crFinTranCode = crFinTranCode;
    }

    public String getCrHeader() {
        return crHeader;
    }

    public void setCrHeader(String crHeader) {
        this.crHeader = crHeader;
    }

    public String getDrHeader() {
        return drHeader;
    }

    public void setDrHeader(String drHeader) {
        this.drHeader = drHeader;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public List<SelectItem> getStatusTypeList() {
        return statusTypeList;
    }

    public void setStatusTypeList(List<SelectItem> statusTypeList) {
        this.statusTypeList = statusTypeList;
    }
}
