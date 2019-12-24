/*
 *  Document   : ODDPLimitEnhancement
 Created on : 8 Apr, 2011, 3:34:04 PM
 Author     : Zeeshan Waris
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.loan;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class ODDPLimitEnhancement extends BaseBean {

    LoanGenralFacadeRemote loanGenralFacade;
    FtsPostingMgmtFacadeRemote fts;
    private String message;
    private String accountNo;
    private String oldaccountNo;
    private String name;
    private String accountNoOption;
    private String roiOld;
    private String penalInterestOld;
    private String sanctionLimitOld;
    private String maximumlimitOld;
    private String adhocLimitOld;
    private String adhocRoiOld;
    private Date adhocApplicableDateOld;
    private Date adhoctillDateOld;
    private String subsidyAmtOld;
    private Date subsidyExpiryDateOld;
    private String interestOptionOld;
    private String roiNew;
    private String penalInterestNew;
    private String sanctionLimitNew;
    private String maximumlimitNew;
    private String adhocLimitNew;
    private String adhocRoiNew;
    private Date adhocApplicableDateNew;
    private Date adhoctillDateNew;
    private String subsidyAmtNew;
    private Date subsidyExpiryDateNew;
    private String interestOptionNew;
    private List<SelectItem> ddInterestOptionOld;
    private boolean roiOldDisable;
    private boolean roiNewDisable;
    private boolean penalInterestNewDisable;
    private boolean adhocLimitNewDisable;
    private boolean adhocRoiNewDisable;
    private boolean adhocApplicableDateNewDisable;
    private boolean adhoctillDateNewDisable;
    private boolean subsidyAmtNewDisable;
    private boolean subsidyExpiryDateNewDisable;
    private boolean interestOptionNewDisable;
    private String acNature;
    private String roi;
    private String penalRoi;
    private String sanctionLimit;
    private String odLimit;
    private String adhocLimit;
    private String adhocroi;
    private String adhocapplicabledt;
    private String adhocexpiry;
    private String maxlimit;
    private String subsidyamt;
    private String subsidyexpdt;
    private String oldSanctionedLomitChange;
    private String newSanctionedLomitChange, acNoLen;
    private boolean saveDisable;
    Validator validator;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public Date getAdhocApplicableDateOld() {
        return adhocApplicableDateOld;
    }

    public void setAdhocApplicableDateOld(Date adhocApplicableDateOld) {
        this.adhocApplicableDateOld = adhocApplicableDateOld;
    }

    public Date getAdhocApplicableDateNew() {
        return adhocApplicableDateNew;
    }

    public void setAdhocApplicableDateNew(Date adhocApplicableDateNew) {
        this.adhocApplicableDateNew = adhocApplicableDateNew;
    }

    public Date getAdhoctillDateNew() {
        return adhoctillDateNew;
    }

    public void setAdhoctillDateNew(Date adhoctillDateNew) {
        this.adhoctillDateNew = adhoctillDateNew;
    }

    public Date getAdhoctillDateOld() {
        return adhoctillDateOld;
    }

    public void setAdhoctillDateOld(Date adhoctillDateOld) {
        this.adhoctillDateOld = adhoctillDateOld;
    }

    public Date getSubsidyExpiryDateNew() {
        return subsidyExpiryDateNew;
    }

    public void setSubsidyExpiryDateNew(Date subsidyExpiryDateNew) {
        this.subsidyExpiryDateNew = subsidyExpiryDateNew;
    }

    public Date getSubsidyExpiryDateOld() {
        return subsidyExpiryDateOld;
    }

    public void setSubsidyExpiryDateOld(Date subsidyExpiryDateOld) {
        this.subsidyExpiryDateOld = subsidyExpiryDateOld;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getNewSanctionedLomitChange() {
        return newSanctionedLomitChange;
    }

    public void setNewSanctionedLomitChange(String newSanctionedLomitChange) {
        this.newSanctionedLomitChange = newSanctionedLomitChange;
    }

    public String getOldSanctionedLomitChange() {
        return oldSanctionedLomitChange;
    }

    public void setOldSanctionedLomitChange(String oldSanctionedLomitChange) {
        this.oldSanctionedLomitChange = oldSanctionedLomitChange;
    }

    public boolean isAdhocApplicableDateNewDisable() {
        return adhocApplicableDateNewDisable;
    }

    public void setAdhocApplicableDateNewDisable(boolean adhocApplicableDateNewDisable) {
        this.adhocApplicableDateNewDisable = adhocApplicableDateNewDisable;
    }

    public boolean isAdhocLimitNewDisable() {
        return adhocLimitNewDisable;
    }

    public void setAdhocLimitNewDisable(boolean adhocLimitNewDisable) {
        this.adhocLimitNewDisable = adhocLimitNewDisable;
    }

    public boolean isAdhocRoiNewDisable() {
        return adhocRoiNewDisable;
    }

    public void setAdhocRoiNewDisable(boolean adhocRoiNewDisable) {
        this.adhocRoiNewDisable = adhocRoiNewDisable;
    }

    public boolean isAdhoctillDateNewDisable() {
        return adhoctillDateNewDisable;
    }

    public void setAdhoctillDateNewDisable(boolean adhoctillDateNewDisable) {
        this.adhoctillDateNewDisable = adhoctillDateNewDisable;
    }

    public boolean isInterestOptionNewDisable() {
        return interestOptionNewDisable;
    }

    public void setInterestOptionNewDisable(boolean interestOptionNewDisable) {
        this.interestOptionNewDisable = interestOptionNewDisable;
    }

    public boolean isPenalInterestNewDisable() {
        return penalInterestNewDisable;
    }

    public void setPenalInterestNewDisable(boolean penalInterestNewDisable) {
        this.penalInterestNewDisable = penalInterestNewDisable;
    }

    public boolean isRoiNewDisable() {
        return roiNewDisable;
    }

    public void setRoiNewDisable(boolean roiNewDisable) {
        this.roiNewDisable = roiNewDisable;
    }

    public boolean isRoiOldDisable() {
        return roiOldDisable;
    }

    public void setRoiOldDisable(boolean roiOldDisable) {
        this.roiOldDisable = roiOldDisable;
    }

    public boolean isSubsidyAmtNewDisable() {
        return subsidyAmtNewDisable;
    }

    public void setSubsidyAmtNewDisable(boolean subsidyAmtNewDisable) {
        this.subsidyAmtNewDisable = subsidyAmtNewDisable;
    }

    public boolean isSubsidyExpiryDateNewDisable() {
        return subsidyExpiryDateNewDisable;
    }

    public void setSubsidyExpiryDateNewDisable(boolean subsidyExpiryDateNewDisable) {
        this.subsidyExpiryDateNewDisable = subsidyExpiryDateNewDisable;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNoOption() {
        return accountNoOption;
    }

    public void setAccountNoOption(String accountNoOption) {
        this.accountNoOption = accountNoOption;
    }

    public String getAdhocLimitNew() {
        return adhocLimitNew;
    }

    public void setAdhocLimitNew(String adhocLimitNew) {
        this.adhocLimitNew = adhocLimitNew;
    }

    public String getAdhocLimitOld() {
        return adhocLimitOld;
    }

    public void setAdhocLimitOld(String adhocLimitOld) {
        this.adhocLimitOld = adhocLimitOld;
    }

    public String getAdhocRoiNew() {
        return adhocRoiNew;
    }

    public void setAdhocRoiNew(String adhocRoiNew) {
        this.adhocRoiNew = adhocRoiNew;
    }

    public String getAdhocRoiOld() {
        return adhocRoiOld;
    }

    public void setAdhocRoiOld(String adhocRoiOld) {
        this.adhocRoiOld = adhocRoiOld;
    }

    public List<SelectItem> getDdInterestOptionOld() {
        return ddInterestOptionOld;
    }

    public void setDdInterestOptionOld(List<SelectItem> ddInterestOptionOld) {
        this.ddInterestOptionOld = ddInterestOptionOld;
    }

    public String getInterestOptionNew() {
        return interestOptionNew;
    }

    public void setInterestOptionNew(String interestOptionNew) {
        this.interestOptionNew = interestOptionNew;
    }

    public String getInterestOptionOld() {
        return interestOptionOld;
    }

    public void setInterestOptionOld(String interestOptionOld) {
        this.interestOptionOld = interestOptionOld;
    }

    public String getMaximumlimitNew() {
        return maximumlimitNew;
    }

    public void setMaximumlimitNew(String maximumlimitNew) {
        this.maximumlimitNew = maximumlimitNew;
    }

    public String getMaximumlimitOld() {
        return maximumlimitOld;
    }

    public void setMaximumlimitOld(String maximumlimitOld) {
        this.maximumlimitOld = maximumlimitOld;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPenalInterestNew() {
        return penalInterestNew;
    }

    public void setPenalInterestNew(String penalInterestNew) {
        this.penalInterestNew = penalInterestNew;
    }

    public String getPenalInterestOld() {
        return penalInterestOld;
    }

    public void setPenalInterestOld(String penalInterestOld) {
        this.penalInterestOld = penalInterestOld;
    }

    public String getRoiNew() {
        return roiNew;
    }

    public void setRoiNew(String roiNew) {
        this.roiNew = roiNew;
    }

    public String getRoiOld() {
        return roiOld;
    }

    public void setRoiOld(String roiOld) {
        this.roiOld = roiOld;
    }

    public String getSanctionLimitNew() {
        return sanctionLimitNew;
    }

    public void setSanctionLimitNew(String sanctionLimitNew) {
        this.sanctionLimitNew = sanctionLimitNew;
    }

    public String getSanctionLimitOld() {
        return sanctionLimitOld;
    }

    public void setSanctionLimitOld(String sanctionLimitOld) {
        this.sanctionLimitOld = sanctionLimitOld;
    }

    public String getSubsidyAmtNew() {
        return subsidyAmtNew;
    }

    public void setSubsidyAmtNew(String subsidyAmtNew) {
        this.subsidyAmtNew = subsidyAmtNew;
    }

    public String getSubsidyAmtOld() {
        return subsidyAmtOld;
    }

    public void setSubsidyAmtOld(String subsidyAmtOld) {
        this.subsidyAmtOld = subsidyAmtOld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldaccountNo() {
        return oldaccountNo;
    }

    public void setOldaccountNo(String oldaccountNo) {
        this.oldaccountNo = oldaccountNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    Date date = new Date();

    public ODDPLimitEnhancement() {
        try {
            setUserName(getUserName());
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            this.setMessage("");
            setNewSanctionedLomitChange("Sanction Limit(Rs.)");
            setOldSanctionedLomitChange("Sanction Limit(Rs.)");
            ddInterestOptionOld = new ArrayList<SelectItem>();
            ddInterestOptionOld.add(new SelectItem("0", "--SELECT--"));
            ddInterestOptionOld.add(new SelectItem("M", "Monthly"));
            ddInterestOptionOld.add(new SelectItem("Q", "Quarterly"));
            ddInterestOptionOld.add(new SelectItem("H", "Half yearly"));

            setSaveDisable(true);
            validator = new Validator();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getDetails() {
        try {
            this.setMessage("");
            List resultLt = loanGenralFacade.detailRoi(accountNo, getOrgnBrCode());
            if (resultLt.isEmpty()) {
                throw new ApplicationException("No Record Found");
            }
            Vector ele = (Vector) resultLt.get(0);
            if (ele.get(1).toString().equalsIgnoreCase("")) {
                setName("");
            } else {
                setName(ele.get(1).toString());
            }
            if (ele.get(2).toString().equalsIgnoreCase("")) {
                roi = "";
            } else {
                roi = ele.get(2).toString();
            }
            if (ele.get(3).toString().equalsIgnoreCase("")) {
                penalRoi = "";
            } else {
                penalRoi = ele.get(3).toString();
            }

            float roiValue = Float.parseFloat(roi);
            float penaltyTmp = Float.parseFloat(penalRoi);
            penaltyTmp = penaltyTmp * 12;
            penaltyTmp = penaltyTmp - roiValue;

            if (ele.get(4).toString().equalsIgnoreCase("")) {
                sanctionLimit = "";
            } else {
                sanctionLimit = formatter.format(Double.parseDouble(ele.get(4).toString()));
            }

            if (ele.get(5).toString().equalsIgnoreCase("")) {
                odLimit = "";
            } else {
                odLimit = formatter.format(Double.parseDouble(ele.get(5).toString()));
            }
            if (ele.get(7).toString().equalsIgnoreCase("")) {
                adhocLimit = "";
            } else {
                adhocLimit = formatter.format(Double.parseDouble(ele.get(7).toString()));
            }

            if (ele.get(8).toString().equalsIgnoreCase("")) {
                adhocroi = "";
            } else {
                adhocroi = ele.get(8).toString();
            }

            if (ele.get(9).toString().equalsIgnoreCase("")) {
                adhocapplicabledt = "";
            } else {
                adhocapplicabledt = ele.get(9).toString();
            }
            if (ele.get(10).toString().equalsIgnoreCase("")) {
                adhocexpiry = "";
            } else {
                adhocexpiry = ele.get(10).toString();
            }
            if (ele.get(11).toString().equalsIgnoreCase("")) {
                maxlimit = "";
            } else {
                maxlimit = formatter.format(Double.parseDouble(ele.get(11).toString()));
            }
            if (ele.get(12).toString().equalsIgnoreCase("")) {
                subsidyamt = "";
            } else {
                subsidyamt = formatter.format(Double.parseDouble(ele.get(12).toString()));
            }

            if (ele.get(13).toString().equalsIgnoreCase("")) {
                subsidyexpdt = "";
            } else {
                subsidyexpdt = ele.get(13).toString();
            }
            if (ele.get(14).toString().equalsIgnoreCase("")) {
                setInterestOptionOld("0");
                setInterestOptionNew("0");
            } else {
                setInterestOptionOld(ele.get(14).toString());
                setInterestOptionNew(ele.get(14).toString());
            }
            setRoiOld(roi);
            setRoiNew(roi);

            setPenalInterestOld(Float.toString(penaltyTmp));
            setPenalInterestNew(Float.toString(penaltyTmp));

            setMaximumlimitOld(maxlimit);
            setMaximumlimitNew(maxlimit);

            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                setSanctionLimitOld(odLimit);
                setSanctionLimitNew(odLimit);
                setAdhocRoiOld(adhocroi);
                setAdhocRoiNew(adhocroi);
                setAdhocLimitNew(adhocLimit);
                setAdhocLimitOld(adhocLimit);
                try {
                    if (!adhocapplicabledt.equalsIgnoreCase("")) {
                        setAdhocApplicableDateOld(sdf.parse(adhocapplicabledt));
                        setAdhocApplicableDateNew(sdf.parse(adhocapplicabledt));
                    }
                    if (!adhocexpiry.equalsIgnoreCase("")) {
                        setAdhoctillDateOld(sdf.parse(adhocexpiry));
                        setAdhoctillDateNew(sdf.parse(adhocexpiry));
                    }
                    if (!subsidyexpdt.equalsIgnoreCase("")) {
                        setSubsidyExpiryDateOld(sdf.parse(subsidyexpdt));
                        setSubsidyExpiryDateNew(sdf.parse(subsidyexpdt));
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ODDPLimitEnhancement.class.getName()).log(Level.SEVERE, null, ex);
                }
                setSubsidyAmtOld(subsidyamt);
                setSubsidyAmtNew(subsidyamt);
            } else {
                setSanctionLimitOld(sanctionLimit);
                setSanctionLimitNew(sanctionLimit);
                setAdhocRoiOld("");
                setAdhocRoiNew("");
                setAdhocLimitNew("");
                setAdhocLimitOld("");

                setAdhoctillDateOld(null);
                setAdhoctillDateNew(null);
                if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    setSubsidyAmtOld(subsidyamt);
                    setSubsidyAmtNew(subsidyamt);
                    try {
                        if (!subsidyexpdt.equalsIgnoreCase("")) {
                            setSubsidyExpiryDateOld(sdf.parse(subsidyexpdt));
                            setSubsidyExpiryDateNew(sdf.parse(subsidyexpdt));
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ODDPLimitEnhancement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (!name.equalsIgnoreCase("")) {
                saveDisable = false;
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            reset();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            reset();
        }
    }

    public void accountnoValue() {
        try {
            if (oldaccountNo.equalsIgnoreCase("") || oldaccountNo == null) {
                throw new ApplicationException("Please Fill Account Number");
            }

            if (!validator.validateStringAllNoSpace(oldaccountNo)) {
                throw new ApplicationException("Please Enter valid Account No:");
            }
            //if (oldaccountNo.length() < 12) {
            if (!this.oldaccountNo.equalsIgnoreCase("") && ((this.oldaccountNo.length() != 12) && (this.oldaccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                throw new ApplicationException("Please Enter valid Account No:");
            }

            accountNo = fts.getNewAccountNumber(oldaccountNo);
            acNature = fts.getAccountNature(accountNo);
            if (!(acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.SS_AC))) {
                throw new ApplicationException("Please insert account no. of TL, CA, DL, SS nature only !!");
            }
            accountNoOption = fts.getAccountCode(accountNo);
            if (acNature.equalsIgnoreCase("CA")) {
                if (accountNoOption.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                    setNewSanctionedLomitChange("OD Limit(Rs.)");
                    setOldSanctionedLomitChange("OD Limit(Rs.)");
                    setSubsidyExpiryDateNewDisable(true);
                    setSubsidyAmtNewDisable(true);
                    setAdhocLimitNewDisable(false);
                    setAdhocRoiNewDisable(false);
                    setAdhocApplicableDateNewDisable(false);
                    setAdhoctillDateNewDisable(false);
                } else if (accountNoOption.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())) {
                    setNewSanctionedLomitChange("Drawing Power(Rs.)");
                    setOldSanctionedLomitChange("Drawing Power(Rs.)");
                    setAdhocLimitNewDisable(false);
                    setAdhocRoiNewDisable(false);
                    setAdhocApplicableDateNewDisable(false);
                    setAdhoctillDateNewDisable(false);
                    setSubsidyAmtNewDisable(false);
                    setSubsidyExpiryDateNewDisable(false);
                } else if (accountNoOption.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                    setNewSanctionedLomitChange("OD Limit(Rs.)");
                    setOldSanctionedLomitChange("OD Limit(Rs.)");
                    setAdhocLimitNewDisable(false);
                    setAdhocRoiNewDisable(false);
                    setAdhocApplicableDateNewDisable(false);
                    setAdhoctillDateNewDisable(false);
                    setSubsidyAmtNewDisable(false);
                    setSubsidyExpiryDateNewDisable(false);
                } else {
                    setNewSanctionedLomitChange("OD Limit(Rs.)");
                    setOldSanctionedLomitChange("OD Limit(Rs.)");
                    setAdhocLimitNewDisable(false);
                    setAdhocRoiNewDisable(false);
                    setAdhocApplicableDateNewDisable(false);
                    setAdhoctillDateNewDisable(false);
                    setSubsidyAmtNewDisable(false);
                    setSubsidyExpiryDateNewDisable(false);
                }
            }
            if (acNature.equalsIgnoreCase("TL")) {
                setNewSanctionedLomitChange("Sanction Limit(Rs.)");
                setOldSanctionedLomitChange("Sanction Limit(Rs.)");
                setAdhocLimitNewDisable(true);
                setAdhocRoiNewDisable(true);
                setAdhocApplicableDateNewDisable(true);
                setAdhoctillDateNewDisable(true);
                setSubsidyAmtNewDisable(false);
                setSubsidyExpiryDateNewDisable(false);
            }
            if (acNature.equalsIgnoreCase("DL")) {
                setNewSanctionedLomitChange("Sanction Limit(Rs.)");
                setOldSanctionedLomitChange("Sanction Limit(Rs.)");
                setAdhocLimitNewDisable(true);
                setAdhocRoiNewDisable(true);
                setAdhocApplicableDateNewDisable(true);
                setAdhoctillDateNewDisable(true);
                setSubsidyAmtNewDisable(true);
                setSubsidyExpiryDateNewDisable(true);
            }
            getDetails();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void saveBtnAction() throws ParseException {
        this.setMessage("");
        if (onblurChecking().equalsIgnoreCase("false")) {
            return;
        }

        float roi = (float) 0.0;
        if (roiOld == null) {

            roi = (float) 0.0;
        } else {
            roi = Float.parseFloat(roiOld);
        }

        float newroi = (float) 0.0;
        if (roiNew == null) {
            newroi = (float) 0.0;
        } else {
            newroi = Float.parseFloat(roiNew);
        }

        float penalRoi = (float) 0.0;
        if ((penalInterestNew == null) || (penalInterestNew.equalsIgnoreCase(""))) {
            penalRoi = newroi / 12;
        } else {
            penalRoi = Float.parseFloat(penalInterestNew);
            penalRoi = (penalRoi + newroi) / 12;
        }

        float penalty = (float) 0.0;
        if (penalInterestOld == null) {
            penalty = (float) 0.0;
        } else {
            penalty = Float.parseFloat(penalInterestOld);
        }

        float sancLimitOld = (float) 0.0;
        if (sanctionLimitOld == null) {
            sancLimitOld = (float) 0.0;
        } else {
            sancLimitOld = Float.parseFloat(sanctionLimitOld);
        }

        float sancLimitNew = (float) 0.0;
        if (sanctionLimitNew == null) {
            sancLimitNew = (float) 0.0;
        } else {
            sancLimitNew = Float.parseFloat(sanctionLimitNew);
        }

        float maxlimit = 0f;
        if (maximumlimitOld == null) {
            maxlimit = (float) 0.0;
        } else {
            maxlimit = Float.parseFloat(maximumlimitOld);
        }

//        float maxlimitNew = (float) 0.0;
//        if (maximumlimitNew == null) {
//            maxlimitNew = (float) 0.0;
//        } else {
//            maxlimitNew = Float.parseFloat(maximumlimitNew);
//        }
        float adhocLimitold = (float) 0.0;
        if ((adhocLimitOld == null) || (adhocLimitOld.equalsIgnoreCase(""))) {
            adhocLimitold = (float) 0.0;
        } else {
            adhocLimitold = Float.parseFloat(adhocLimitOld);
        }

        float adhocLimitNew1 = (float) 0.0;
        if ((adhocLimitNew == null) || (adhocLimitNew.equalsIgnoreCase(""))) {
            adhocLimitNew1 = (float) 0.0;
        } else {
            adhocLimitNew1 = Float.parseFloat(adhocLimitNew);
        }

        float adhocRoiold = (float) 0.0;
        if ((adhocRoiOld == null) || (adhocRoiOld.equalsIgnoreCase(""))) {
            adhocRoiold = (float) 0.0;
        } else {
            adhocRoiold = Float.parseFloat(adhocRoiOld);
        }

        float adhocRoiNew1 = (float) 0.0;
        if ((adhocRoiNew == null) || (adhocRoiNew.equalsIgnoreCase(""))) {
            adhocRoiNew1 = (float) 0.0;
        } else {
            if (adhocLimitNew1 > 0) {
                adhocRoiNew1 = Float.parseFloat(adhocRoiNew);
                if (adhocRoiNew1 > newroi) {
                    setMessage("Adhoc Roi cann't greater than ROI of this account.");
                    return;
                }
            }
        }

        String adhocAppDtold = "";
        if (adhocApplicableDateOld == null) {
            adhocAppDtold = "19000101";
        } else {
            adhocAppDtold = ymd.format(adhocApplicableDateOld);
        }

        String adhocAppDtNew = "";
        if (adhocApplicableDateNewDisable == true) {
            adhocAppDtNew = "19000101";
        } else {
            if (adhocApplicableDateNew == null) {
                adhocAppDtNew = "19000101";
            } else {
                adhocAppDtNew = ymd.format(adhocApplicableDateNew);
            }
        }

        String adhocTillDtold = "";
        if (adhoctillDateOld == null) {
            adhocTillDtold = "19000101";
        } else {
            adhocTillDtold = ymd.format(adhoctillDateOld);
        }

        String adhocTillDtNew = "";
        if (adhoctillDateNew == null) {
            adhocTillDtNew = "19000101";
        } else {
            adhocTillDtNew = ymd.format(adhoctillDateNew);
        }

        float subsidyAmt = (float) 0.0;
        if ((subsidyAmtOld == null) || (subsidyAmtOld.equalsIgnoreCase(""))) {
            subsidyAmt = (float) 0.0;
        } else {
            subsidyAmt = Float.parseFloat(subsidyAmtOld);
        }

        float subsidyAmtNew1 = (float) 0.0;
        if ((subsidyAmtNew == null) || (subsidyAmtNew.equalsIgnoreCase(""))) {
            subsidyAmtNew1 = (float) 0.0;
        } else {
            subsidyAmtNew1 = Float.parseFloat(subsidyAmtNew);
        }

        String subsidyExpDtOld = "";
        if (subsidyExpiryDateOld == null) {
            subsidyExpDtOld = "";
        } else {
            subsidyExpDtOld = sdf.format(subsidyExpiryDateOld);
        }

        String subsidyExpDtNew = "";
        if (subsidyExpiryDateNewDisable == true) {
            subsidyExpDtNew = "19000101";
        } else {
            if((subsidyAmtNew.equalsIgnoreCase(""))&& (subsidyExpiryDateNew != null)) {
                setMessage("Please fill Subsidy Amount");
                return;
            } else if((Float.parseFloat(subsidyAmtNew)!=0) && (subsidyExpiryDateNew == null) ) {
                setMessage("Please fill Subsidy Expiry Date");
                return;
            } else {
                if (subsidyExpiryDateNew == null) {
                    subsidyExpDtNew = "19000101";
                } else {
                    subsidyExpDtNew = ymd.format(subsidyExpiryDateNew);
                }
            }
        }
        if (interestOptionNew.equalsIgnoreCase("0")) {
            setMessage("Please Select Interest Option");
            return;
        }
        try {
            String result = loanGenralFacade.odEnhancementSave(accountNo, accountNoOption, roi, newroi, penalRoi, penalty, sancLimitOld,
                    sancLimitNew, maxlimit, sancLimitNew, adhocLimitold, adhocLimitNew1, adhocRoiold, adhocRoiNew1, adhocAppDtold, adhocAppDtNew,
                    adhocTillDtold, adhocTillDtNew, subsidyAmt, subsidyAmtNew1, subsidyExpDtOld, subsidyExpDtNew, interestOptionNew, getUserName(),
                    ymd.format(sdf.parse(getTodayDate())));
            setMessage(result);
            reset();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void clear() {
        try {
            setNewSanctionedLomitChange("Sanction Limit(Rs.)");
            setOldSanctionedLomitChange("Sanction Limit(Rs.)");
            this.setMessage("");
            setAccountNo("");
            setOldaccountNo("");
            setName("");
            setAccountNoOption("");
            setRoiOld("");
            setPenalInterestOld("");
            setSanctionLimitOld("");
            setMaximumlimitOld("");
            setAdhocLimitOld("");
            setAdhocRoiOld("");
            setAdhocApplicableDateOld(null);
            setAdhoctillDateOld(null);
            setSubsidyAmtOld("");

            setSubsidyExpiryDateOld(null);
            setInterestOptionOld("0");
            setRoiNew("");
            setPenalInterestNew("");
            setSanctionLimitNew("");
            setMaximumlimitNew("");
            setAdhocLimitNew("");
            setAdhocRoiNew("");

            setAdhocApplicableDateNew(null);
            setAdhoctillDateNew(null);
            setSubsidyAmtNew("");

            setSubsidyExpiryDateNew(null);
            setInterestOptionNew("0");
            saveDisable = true;
            setAccountNoOption("--Select--");
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void reset() {
        try {
            setAccountNo("");
            setOldaccountNo("");
            setName("");
            setRoiOld("");
            setPenalInterestOld("");
            setSanctionLimitOld("");
            setMaximumlimitOld("");
            setAdhocLimitOld("");
            setAdhocRoiOld("");
            setAdhocApplicableDateOld(null);
            setAdhoctillDateOld(null);
            setSubsidyAmtOld("");
            setSubsidyExpiryDateOld(null);
            setInterestOptionOld("0");
            setRoiNew("");
            setPenalInterestNew("");
            setSanctionLimitNew("");
            setMaximumlimitNew("");
            setAdhocLimitNew("");
            setAdhocRoiNew("");
            setAdhocApplicableDateNew(null);
            setAdhoctillDateNew(null);
            setSubsidyAmtNew("");
            setSubsidyExpiryDateNew(null);
            setInterestOptionNew("0");
            saveDisable = true;
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public String onblurChecking() {
        try {

            if (oldaccountNo.equalsIgnoreCase("") || oldaccountNo == null) {
                setMessage("Please Fill Account Number");
                return "false";
            }

            if (!validator.validateStringAllNoSpace(oldaccountNo)) {
                this.setMessage("Please Enter valid Account No:");
                return "false";
            }
            //if (oldaccountNo.length() < 12) {
            if (!this.oldaccountNo.equalsIgnoreCase("") && ((this.oldaccountNo.length() != 12) && (this.oldaccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please Enter valid Account No:");
                return "false";
            }
            if (adhocRoiNew != null) {
                if (!adhocRoiNew.equalsIgnoreCase("")) {
                    if (!adhocRoiNew.matches("[0-9.]*")) {
                        this.setMessage("Please Enter Numeric Value in Adhoc ROI(% p.a.)");
                        return "false";
                    }
                }
            }
            if (subsidyAmtNew != null) {
                if (!subsidyAmtNew.equalsIgnoreCase("")) {
                    if (!subsidyAmtNew.matches("[0-9.]*")) {
                        this.setMessage("Please Enter Numeric Value in Subsidy Amount(Rs.)");
                        return "false";
                    }
                }
            }
            maximumlimitNew = sanctionLimitNew;
            if (sanctionLimitNew != null && !sanctionLimitNew.equalsIgnoreCase("")) {

                   // if (maximumlimitNew != null) {
                //   if (!maximumlimitNew.equalsIgnoreCase("")) {
                if (!sanctionLimitNew.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in OD Limit(Rs.) / Sanction Limit(Rs.) / Drawing Power(Rs.)");
                    return "false";
                }
                if (!maximumlimitNew.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Maximum Limit(Rs.)");
                    return "false";
                }
//                            else {
//                                if (Float.parseFloat(sanctionLimitNew) > Float.parseFloat(maximumlimitNew)) {
//                                    if ((accountNoOption.equalsIgnoreCase(CbsAcCodeConstant.DEMAND_LOAN.getAcctCode()) || accountNoOption.equalsIgnoreCase(CbsAcCodeConstant.TERM_LOAN.getAcctCode()))) {
//                                        this.setMessage("Sanctioned Limit Should be Less than or equal to Max. Limit");
//                                        return "false";
//                                    } else {
//                                        this.setMessage("DP Exceeds Maximum Limit");
//                                        return "false";
//                                    }
//                                }
//                            }
                //  }
                //  }
            }

            if (adhocLimitNew != null) {
                if (!adhocLimitNew.equalsIgnoreCase("")) {
                    if (!adhocLimitNew.matches("[0-9.]*")) {
                        this.setMessage("Please Enter Numeric Value in Adhoc Limit(Rs.) (Over Max Limit)");
                        return "false";
                    } else {
                        if (Float.parseFloat(adhocLimitNew) > 0) {
                            if (adhoctillDateNew != null) {
                                if (adhoctillDateNew.before(sdf.parse(getTodayDate()))) {
                                    this.setMessage("Adhoc Till Date Should Not Be Less Then Todays Date.");
                                    return "false";
                                }
                            }
                            if (adhocApplicableDateNew != null) {
                                if (adhocApplicableDateNew.before(sdf.parse(getTodayDate()))) {
                                    this.setMessage("Adhoc Applicable Date Should Not Be Less Then Todays Date.");
                                    return "false";
                                }
                            }
                            if (adhoctillDateNew != null) {
                                if (adhocApplicableDateNew != null) {
                                    if (adhoctillDateNew.before(adhocApplicableDateNew)) {
                                        this.setMessage("Adhoc Applicable Date Should Be Less Adhoc Till Date.");
                                        return "false";
                                    }
                                }
                            }
                        } else {
                            if (Float.parseFloat(adhocLimitNew) < 0) {
                                if (Float.parseFloat(adhocRoiNew) < 0) {
                                    this.setMessage("Adhoc ROI not possible for Zero Adhoc Limit.");
                                    return "false";
                                }
                                if (!adhoctillDateNew.equals("")) {
                                    this.setMessage("Adhoc Till Date not possible for Zero Adhoc Limit.");
                                    return "false";
                                }

                                if (!adhocApplicableDateNew.equals("")) {
                                    this.setMessage("Adhoc Applicable Date not possible if Adhoc Limit is Zero.");
                                    return "false";
                                }

                            }

                        }
                    }
                }
            }
            if (subsidyAmtNew != null) {
                if (!subsidyAmtNew.equalsIgnoreCase("")) {
                    if (maximumlimitNew != null) {
                        if (!maximumlimitNew.equalsIgnoreCase("")) {
                            if (!subsidyAmtNew.matches("[0-9.]*")) {
                                this.setMessage("Please Enter Numeric Value in Subsidy Amount(Rs.)");
                                return "false";
                            }
                            if (!maximumlimitNew.matches("[0-9.]*")) {
                                this.setMessage("Please Enter Numeric Value in Maximum Limit(Rs.)");
                                return "false";
                            } else {
                                if (Float.parseFloat(subsidyAmtNew) > Float.parseFloat(maximumlimitNew)) {
                                    this.setMessage("Subsidy Amount Should be Less than Max. Limit.");
                                    return "false";
                                }
                            }
                        }
                    }
                }
            }

            if (sanctionLimitNew != null) {
                if (!sanctionLimitNew.equalsIgnoreCase("")) {
                    if (subsidyAmtNew != null) {
                        if (!subsidyAmtNew.equalsIgnoreCase("")) {
                            if (!sanctionLimitNew.matches("[0-9.]*")) {
                                this.setMessage("Please Enter Numeric Value in OD Limit(Rs.) / Sanction Limit(Rs.) / Drawing Power(Rs.)");
                                return "false";
                            }
                            if (!subsidyAmtNew.matches("[0-9.]*")) {
                                this.setMessage("Please Enter Numeric Value in Subsidy Amount(Rs.)");
                                return "false";
                            } else {
                                if (Float.parseFloat(subsidyAmtNew) > Float.parseFloat(sanctionLimitNew)) {
                                    this.setMessage("Subsidy Amount Should be Less than Sanctioned Limit.");
                                    return "false";
                                }
                            }
                        }
                    }
                }
            }
            if (subsidyExpiryDateNew != null) {
                if (!subsidyExpiryDateNew.equals(subsidyExpiryDateOld)) {
                    if (ymd.parse(ymd.format(date)).after(subsidyExpiryDateNew)) {
                        this.setMessage("Sorry Expiry Date Cannot Be Less Than Today's Date.");
                        return "false";
                    }
                }
            }
            return "true";
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
        return "true";
    }

    public String exitForm() {
        clear();
        return "case1";
    }
}
