/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeGeneralSchemeParameterMasterTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
public class PM {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for pm.jsp file
    private String schemeDesc;
    private String nativeSchemeDesc;
    private String rptTransFlag;
    private List<SelectItem> ddrptTransFlag;
    private String schemeSupId;
    private String stmtFrqType;
    private List<SelectItem> ddstmtFrqType;
    private String stmtFrqNum;
    private List<SelectItem> ddstmtFrqNum;
    private String stmtFrqDay;
    private List<SelectItem> ddstmtFrqDay;
    private String stmtFrqStartDt;
    private String stmtFrqNP;
    private List<SelectItem> ddstmtFrqNP;
    private String schemeCrDrInd;
    private List<SelectItem> ddschemeCrDrInd;
    private String turnoverDetailFlag;
    private List<SelectItem> ddturnoverDetailFlag;
    private String sysgenAcctNoFlag;
    private List<SelectItem> ddsysgenAcctNoFlag;
    private String turnoverFreqType;
    private String turnoverFreqNo;
    private String turnoverFreqDay;
    private String turnoverFreqDate;
    private String turnoverFreqNp;
    private String acctPrefix;
    private String nxtNoTblCode;
    private String fcnrSchemeFlg;
    private String acctCloserAcrossSols;
    private String schemeFlag;
    private String opt;
    private String fdCrAccountPlaceHolder;
    private String commitCalMethd;
    private String fdDrAccountPlaceHolder;
    private BigDecimal minCommitUtln;
    private String staffSchmeflg;
    private String dormantChargeMonth;
    private String dormantChargeDay;
    private String nrSchemeFlg;
    private String inactiveChrgmnth;
    private String inactiveChrgDay;
    private String newAcctDur;
    private String productGroup;
    private String minPostWorkClass;
    private String linkTranSale;
    private String minAcctClosurePeriodMonth;
    private String minAcctClosurePeriodDay;
    private String acctMaintPeriod;
    private String dfltClgTransCode;
    private String stdfltClgTransCode;
    private String dfltInsttype;
    private String trnRefNo;
    private String pegIntForAcctFlg;
    private String modificationPegFlg;
    private String pegRevCust;
    private String acctPartionAllowed;
    private String autoRenewal;
    private String pdGlSubHead;
    private String stmtMsg;
    private String nativeStmtMsg;
    private String cretedBy;
    private String createDt;
    private String lastUpdateBy;
    private String lastUpdateDt;
    private String totalModification;
    private String liabilityExceed;
    private String stliabilityExceed;
    private String insufficientBal;
    private String stinsufficientBal;
    private String acctNameChng;
    private String stacctNameChng;
    private String staleInstrment;
    private String ststaleInstrment;
    private String acctFrozenExcep;
    private String stacctFrozenExcep;
    private String custIdMismatchDr;
    private String stcustIdMismatchDr;
    private String custIdMismatchcr;
    private String stcustIdMismatchcr;
    private String backDatedTrn;
    private String stbackDatedTrn;
    private String valueDateTrn;
    private String stvalueDateTrn;
    private String cashTrn;
    private String stcashTrn;
    private String acctOpenMatrix;
    private String stacctOpenMatrix;
    private String clrTrnExcp;
    private String stclrTrnExcp;
    private String transferTrn;
    private String sttransferTrn;
    private String sanctionedLmt;
    private String stsanctionedLmt;
    private String sanctionedExceed;
    private String stsanctionedExceed;
    private String cusrDrWithoutChq;
    private String stcusrDrWithoutChq;
    private String refferedAcctClosure;
    private String strefferedAcctClosure;
    private String userTodGrntExcp;
    private String stuserTodGrntExcp;
    private String autoTodGrntExcp;
    private String stAutoTodGrntExcp;
    private String acctInDrBal;
    private String stacctInDrBal;
    private String acctInCrBal;
    private String stacctInCrBal;
    private String overrideDfltChq;
    private String stoverrideDfltChq;
    private String invalidReportCode;
    private String stinvalidReportCode;
    private String mamoPadExist;
    private String stmamoPadExist;
    private String dfltIntParmExcp;
    private String stdfltIntParmExcp;
    private String acctBalBelowmin;
    private String stacctBalBelowmin;
    private String minBalPenalChrg;
    private String stminBalPenalChrg;
    private String intForPastDue;
    private String stintForPastDue;
    private String drTranToPast;
    private String stdrTranToPast;
    private List<SelectItem> ddturnoverFreqType;
    private List<SelectItem> ddturnoverFreqNo;
    private List<SelectItem> ddturnoverFreqDay;
    private List<SelectItem> ddturnoverFreqNp;
    private List<SelectItem> ddfcnrSchemeFlg;
    private List<SelectItem> ddacctCloserAcrossSols;
    private List<SelectItem> ddschemeFlag;
    private List<SelectItem> ddopt;
    private List<SelectItem> ddcommitCalMethd;
    private List<SelectItem> ddstaffSchmeflg;
    private List<SelectItem> ddnrSchemeFlg;
    private List<SelectItem> ddproductGroup;
    private List<SelectItem> ddminPostWorkClass;
    private List<SelectItem> ddlinkTranSale;
    private List<SelectItem> ddacctMaintPeriod;
    private List<SelectItem> dddfltInsttype;
    private List<SelectItem> ddtrnRefNo;
    private List<SelectItem> ddpegIntForAcctFlg;
    private List<SelectItem> ddmodificationPegFlg;
    private List<SelectItem> ddpegRevCust;
    private List<SelectItem> ddacctPartionAllowed;
    private List<SelectItem> ddAutoRenewal;
    private String stClsPendingProxy;
    private boolean pmFlag;

    public List<SelectItem> getDdAutoRenewal() {
        return ddAutoRenewal;
    }

    public void setDdAutoRenewal(List<SelectItem> ddAutoRenewal) {
        this.ddAutoRenewal = ddAutoRenewal;
    }

    public List<SelectItem> getDdacctCloserAcrossSols() {
        return ddacctCloserAcrossSols;
    }

    public void setDdacctCloserAcrossSols(List<SelectItem> ddacctCloserAcrossSols) {
        this.ddacctCloserAcrossSols = ddacctCloserAcrossSols;
    }

    public List<SelectItem> getDdacctMaintPeriod() {
        return ddacctMaintPeriod;
    }

    public void setDdacctMaintPeriod(List<SelectItem> ddacctMaintPeriod) {
        this.ddacctMaintPeriod = ddacctMaintPeriod;
    }

    public List<SelectItem> getDdacctPartionAllowed() {
        return ddacctPartionAllowed;
    }

    public void setDdacctPartionAllowed(List<SelectItem> ddacctPartionAllowed) {
        this.ddacctPartionAllowed = ddacctPartionAllowed;
    }

    public List<SelectItem> getDdcommitCalMethd() {
        return ddcommitCalMethd;
    }

    public void setDdcommitCalMethd(List<SelectItem> ddcommitCalMethd) {
        this.ddcommitCalMethd = ddcommitCalMethd;
    }

    public List<SelectItem> getDddfltInsttype() {
        return dddfltInsttype;
    }

    public void setDddfltInsttype(List<SelectItem> dddfltInsttype) {
        this.dddfltInsttype = dddfltInsttype;
    }

    public List<SelectItem> getDdfcnrSchemeFlg() {
        return ddfcnrSchemeFlg;
    }

    public void setDdfcnrSchemeFlg(List<SelectItem> ddfcnrSchemeFlg) {
        this.ddfcnrSchemeFlg = ddfcnrSchemeFlg;
    }

    public List<SelectItem> getDdlinkTranSale() {
        return ddlinkTranSale;
    }

    public void setDdlinkTranSale(List<SelectItem> ddlinkTranSale) {
        this.ddlinkTranSale = ddlinkTranSale;
    }

    public List<SelectItem> getDdminPostWorkClass() {
        return ddminPostWorkClass;
    }

    public void setDdminPostWorkClass(List<SelectItem> ddminPostWorkClass) {
        this.ddminPostWorkClass = ddminPostWorkClass;
    }

    public List<SelectItem> getDdmodificationPegFlg() {
        return ddmodificationPegFlg;
    }

    public void setDdmodificationPegFlg(List<SelectItem> ddmodificationPegFlg) {
        this.ddmodificationPegFlg = ddmodificationPegFlg;
    }

    public List<SelectItem> getDdnrSchemeFlg() {
        return ddnrSchemeFlg;
    }

    public void setDdnrSchemeFlg(List<SelectItem> ddnrSchemeFlg) {
        this.ddnrSchemeFlg = ddnrSchemeFlg;
    }

    public List<SelectItem> getDdopt() {
        return ddopt;
    }

    public void setDdopt(List<SelectItem> ddopt) {
        this.ddopt = ddopt;
    }

    public List<SelectItem> getDdpegIntForAcctFlg() {
        return ddpegIntForAcctFlg;
    }

    public void setDdpegIntForAcctFlg(List<SelectItem> ddpegIntForAcctFlg) {
        this.ddpegIntForAcctFlg = ddpegIntForAcctFlg;
    }

    public List<SelectItem> getDdpegRevCust() {
        return ddpegRevCust;
    }

    public void setDdpegRevCust(List<SelectItem> ddpegRevCust) {
        this.ddpegRevCust = ddpegRevCust;
    }

    public List<SelectItem> getDdproductGroup() {
        return ddproductGroup;
    }

    public void setDdproductGroup(List<SelectItem> ddproductGroup) {
        this.ddproductGroup = ddproductGroup;
    }

    public List<SelectItem> getDdschemeFlag() {
        return ddschemeFlag;
    }

    public void setDdschemeFlag(List<SelectItem> ddschemeFlag) {
        this.ddschemeFlag = ddschemeFlag;
    }

    public List<SelectItem> getDdstaffSchmeflg() {
        return ddstaffSchmeflg;
    }

    public void setDdstaffSchmeflg(List<SelectItem> ddstaffSchmeflg) {
        this.ddstaffSchmeflg = ddstaffSchmeflg;
    }

    public List<SelectItem> getDdtrnRefNo() {
        return ddtrnRefNo;
    }

    public void setDdtrnRefNo(List<SelectItem> ddtrnRefNo) {
        this.ddtrnRefNo = ddtrnRefNo;
    }

    public List<SelectItem> getDdturnoverFreqDay() {
        return ddturnoverFreqDay;
    }

    public void setDdturnoverFreqDay(List<SelectItem> ddturnoverFreqDay) {
        this.ddturnoverFreqDay = ddturnoverFreqDay;
    }

    public List<SelectItem> getDdturnoverFreqNo() {
        return ddturnoverFreqNo;
    }

    public void setDdturnoverFreqNo(List<SelectItem> ddturnoverFreqNo) {
        this.ddturnoverFreqNo = ddturnoverFreqNo;
    }

    public List<SelectItem> getDdturnoverFreqNp() {
        return ddturnoverFreqNp;
    }

    public void setDdturnoverFreqNp(List<SelectItem> ddturnoverFreqNp) {
        this.ddturnoverFreqNp = ddturnoverFreqNp;
    }

    public List<SelectItem> getDdturnoverFreqType() {
        return ddturnoverFreqType;
    }

    public void setDdturnoverFreqType(List<SelectItem> ddturnoverFreqType) {
        this.ddturnoverFreqType = ddturnoverFreqType;
    }

    public String getStClsPendingProxy() {
        return stClsPendingProxy;
    }

    public void setStClsPendingProxy(String stClsPendingProxy) {
        this.stClsPendingProxy = stClsPendingProxy;
    }

    public boolean isPmFlag() {
        return pmFlag;
    }

    public void setPmFlag(boolean pmFlag) {
        this.pmFlag = pmFlag;
    }

    public String getAcctBalBelowmin() {
        return acctBalBelowmin;
    }

    public void setAcctBalBelowmin(String acctBalBelowmin) {
        this.acctBalBelowmin = acctBalBelowmin;
    }

    public String getAcctCloserAcrossSols() {
        return acctCloserAcrossSols;
    }

    public void setAcctCloserAcrossSols(String acctCloserAcrossSols) {
        this.acctCloserAcrossSols = acctCloserAcrossSols;
    }

    public String getAcctFrozenExcep() {
        return acctFrozenExcep;
    }

    public void setAcctFrozenExcep(String acctFrozenExcep) {
        this.acctFrozenExcep = acctFrozenExcep;
    }

    public String getAcctInCrBal() {
        return acctInCrBal;
    }

    public void setAcctInCrBal(String acctInCrBal) {
        this.acctInCrBal = acctInCrBal;
    }

    public String getAcctInDrBal() {
        return acctInDrBal;
    }

    public void setAcctInDrBal(String acctInDrBal) {
        this.acctInDrBal = acctInDrBal;
    }

    public String getAcctMaintPeriod() {
        return acctMaintPeriod;
    }

    public void setAcctMaintPeriod(String acctMaintPeriod) {
        this.acctMaintPeriod = acctMaintPeriod;
    }

    public String getAcctNameChng() {
        return acctNameChng;
    }

    public void setAcctNameChng(String acctNameChng) {
        this.acctNameChng = acctNameChng;
    }

    public String getAcctOpenMatrix() {
        return acctOpenMatrix;
    }

    public void setAcctOpenMatrix(String acctOpenMatrix) {
        this.acctOpenMatrix = acctOpenMatrix;
    }

    public String getAcctPartionAllowed() {
        return acctPartionAllowed;
    }

    public void setAcctPartionAllowed(String acctPartionAllowed) {
        this.acctPartionAllowed = acctPartionAllowed;
    }

    public String getAcctPrefix() {
        return acctPrefix;
    }

    public void setAcctPrefix(String acctPrefix) {
        this.acctPrefix = acctPrefix;
    }

    public String getAutoRenewal() {
        return autoRenewal;
    }

    public void setAutoRenewal(String autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    public String getAutoTodGrntExcp() {
        return autoTodGrntExcp;
    }

    public void setAutoTodGrntExcp(String autoTodGrntExcp) {
        this.autoTodGrntExcp = autoTodGrntExcp;
    }

    public String getBackDatedTrn() {
        return backDatedTrn;
    }

    public void setBackDatedTrn(String backDatedTrn) {
        this.backDatedTrn = backDatedTrn;
    }

    public String getCashTrn() {
        return cashTrn;
    }

    public void setCashTrn(String cashTrn) {
        this.cashTrn = cashTrn;
    }

    public String getClrTrnExcp() {
        return clrTrnExcp;
    }

    public void setClrTrnExcp(String clrTrnExcp) {
        this.clrTrnExcp = clrTrnExcp;
    }

    public String getCommitCalMethd() {
        return commitCalMethd;
    }

    public void setCommitCalMethd(String commitCalMethd) {
        this.commitCalMethd = commitCalMethd;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getCretedBy() {
        return cretedBy;
    }

    public void setCretedBy(String cretedBy) {
        this.cretedBy = cretedBy;
    }

    public String getCusrDrWithoutChq() {
        return cusrDrWithoutChq;
    }

    public void setCusrDrWithoutChq(String cusrDrWithoutChq) {
        this.cusrDrWithoutChq = cusrDrWithoutChq;
    }

    public String getCustIdMismatchDr() {
        return custIdMismatchDr;
    }

    public void setCustIdMismatchDr(String custIdMismatchDr) {
        this.custIdMismatchDr = custIdMismatchDr;
    }

    public String getCustIdMismatchcr() {
        return custIdMismatchcr;
    }

    public void setCustIdMismatchcr(String custIdMismatchcr) {
        this.custIdMismatchcr = custIdMismatchcr;
    }

    public List<SelectItem> getDdrptTransFlag() {
        return ddrptTransFlag;
    }

    public void setDdrptTransFlag(List<SelectItem> ddrptTransFlag) {
        this.ddrptTransFlag = ddrptTransFlag;
    }

    public List<SelectItem> getDdschemeCrDrInd() {
        return ddschemeCrDrInd;
    }

    public void setDdschemeCrDrInd(List<SelectItem> ddschemeCrDrInd) {
        this.ddschemeCrDrInd = ddschemeCrDrInd;
    }

    public List<SelectItem> getDdstmtFrqDay() {
        return ddstmtFrqDay;
    }

    public void setDdstmtFrqDay(List<SelectItem> ddstmtFrqDay) {
        this.ddstmtFrqDay = ddstmtFrqDay;
    }

    public List<SelectItem> getDdstmtFrqNP() {
        return ddstmtFrqNP;
    }

    public void setDdstmtFrqNP(List<SelectItem> ddstmtFrqNP) {
        this.ddstmtFrqNP = ddstmtFrqNP;
    }

    public List<SelectItem> getDdstmtFrqNum() {
        return ddstmtFrqNum;
    }

    public void setDdstmtFrqNum(List<SelectItem> ddstmtFrqNum) {
        this.ddstmtFrqNum = ddstmtFrqNum;
    }

    public List<SelectItem> getDdstmtFrqType() {
        return ddstmtFrqType;
    }

    public void setDdstmtFrqType(List<SelectItem> ddstmtFrqType) {
        this.ddstmtFrqType = ddstmtFrqType;
    }

    public List<SelectItem> getDdsysgenAcctNoFlag() {
        return ddsysgenAcctNoFlag;
    }

    public void setDdsysgenAcctNoFlag(List<SelectItem> ddsysgenAcctNoFlag) {
        this.ddsysgenAcctNoFlag = ddsysgenAcctNoFlag;
    }

    public List<SelectItem> getDdturnoverDetailFlag() {
        return ddturnoverDetailFlag;
    }

    public void setDdturnoverDetailFlag(List<SelectItem> ddturnoverDetailFlag) {
        this.ddturnoverDetailFlag = ddturnoverDetailFlag;
    }

    public String getDfltClgTransCode() {
        return dfltClgTransCode;
    }

    public void setDfltClgTransCode(String dfltClgTransCode) {
        this.dfltClgTransCode = dfltClgTransCode;
    }

    public String getDfltInsttype() {
        return dfltInsttype;
    }

    public void setDfltInsttype(String dfltInsttype) {
        this.dfltInsttype = dfltInsttype;
    }

    public String getDfltIntParmExcp() {
        return dfltIntParmExcp;
    }

    public void setDfltIntParmExcp(String dfltIntParmExcp) {
        this.dfltIntParmExcp = dfltIntParmExcp;
    }

    public String getDormantChargeDay() {
        return dormantChargeDay;
    }

    public void setDormantChargeDay(String dormantChargeDay) {
        this.dormantChargeDay = dormantChargeDay;
    }

    public String getDormantChargeMonth() {
        return dormantChargeMonth;
    }

    public void setDormantChargeMonth(String dormantChargeMonth) {
        this.dormantChargeMonth = dormantChargeMonth;
    }

    public String getDrTranToPast() {
        return drTranToPast;
    }

    public void setDrTranToPast(String drTranToPast) {
        this.drTranToPast = drTranToPast;
    }

    public String getFcnrSchemeFlg() {
        return fcnrSchemeFlg;
    }

    public void setFcnrSchemeFlg(String fcnrSchemeFlg) {
        this.fcnrSchemeFlg = fcnrSchemeFlg;
    }

    public String getFdCrAccountPlaceHolder() {
        return fdCrAccountPlaceHolder;
    }

    public void setFdCrAccountPlaceHolder(String fdCrAccountPlaceHolder) {
        this.fdCrAccountPlaceHolder = fdCrAccountPlaceHolder;
    }

    public String getFdDrAccountPlaceHolder() {
        return fdDrAccountPlaceHolder;
    }

    public void setFdDrAccountPlaceHolder(String fdDrAccountPlaceHolder) {
        this.fdDrAccountPlaceHolder = fdDrAccountPlaceHolder;
    }

    public String getInactiveChrgDay() {
        return inactiveChrgDay;
    }

    public void setInactiveChrgDay(String inactiveChrgDay) {
        this.inactiveChrgDay = inactiveChrgDay;
    }

    public String getInactiveChrgmnth() {
        return inactiveChrgmnth;
    }

    public void setInactiveChrgmnth(String inactiveChrgmnth) {
        this.inactiveChrgmnth = inactiveChrgmnth;
    }

    public String getInsufficientBal() {
        return insufficientBal;
    }

    public void setInsufficientBal(String insufficientBal) {
        this.insufficientBal = insufficientBal;
    }

    public String getIntForPastDue() {
        return intForPastDue;
    }

    public void setIntForPastDue(String intForPastDue) {
        this.intForPastDue = intForPastDue;
    }

    public String getInvalidReportCode() {
        return invalidReportCode;
    }

    public void setInvalidReportCode(String invalidReportCode) {
        this.invalidReportCode = invalidReportCode;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getLiabilityExceed() {
        return liabilityExceed;
    }

    public void setLiabilityExceed(String liabilityExceed) {
        this.liabilityExceed = liabilityExceed;
    }

    public String getLinkTranSale() {
        return linkTranSale;
    }

    public void setLinkTranSale(String linkTranSale) {
        this.linkTranSale = linkTranSale;
    }

    public String getMamoPadExist() {
        return mamoPadExist;
    }

    public void setMamoPadExist(String mamoPadExist) {
        this.mamoPadExist = mamoPadExist;
    }

    public String getMinAcctClosurePeriodDay() {
        return minAcctClosurePeriodDay;
    }

    public void setMinAcctClosurePeriodDay(String minAcctClosurePeriodDay) {
        this.minAcctClosurePeriodDay = minAcctClosurePeriodDay;
    }

    public String getMinAcctClosurePeriodMonth() {
        return minAcctClosurePeriodMonth;
    }

    public void setMinAcctClosurePeriodMonth(String minAcctClosurePeriodMonth) {
        this.minAcctClosurePeriodMonth = minAcctClosurePeriodMonth;
    }

    public String getMinBalPenalChrg() {
        return minBalPenalChrg;
    }

    public void setMinBalPenalChrg(String minBalPenalChrg) {
        this.minBalPenalChrg = minBalPenalChrg;
    }

    public BigDecimal getMinCommitUtln() {
        return minCommitUtln;
    }

    public void setMinCommitUtln(BigDecimal minCommitUtln) {
        this.minCommitUtln = minCommitUtln;
    }

    public String getMinPostWorkClass() {
        return minPostWorkClass;
    }

    public void setMinPostWorkClass(String minPostWorkClass) {
        this.minPostWorkClass = minPostWorkClass;
    }

    public String getModificationPegFlg() {
        return modificationPegFlg;
    }

    public void setModificationPegFlg(String modificationPegFlg) {
        this.modificationPegFlg = modificationPegFlg;
    }

    public String getNativeSchemeDesc() {
        return nativeSchemeDesc;
    }

    public void setNativeSchemeDesc(String nativeSchemeDesc) {
        this.nativeSchemeDesc = nativeSchemeDesc;
    }

    public String getNativeStmtMsg() {
        return nativeStmtMsg;
    }

    public void setNativeStmtMsg(String nativeStmtMsg) {
        this.nativeStmtMsg = nativeStmtMsg;
    }

    public String getNewAcctDur() {
        return newAcctDur;
    }

    public void setNewAcctDur(String newAcctDur) {
        this.newAcctDur = newAcctDur;
    }

    public String getNrSchemeFlg() {
        return nrSchemeFlg;
    }

    public void setNrSchemeFlg(String nrSchemeFlg) {
        this.nrSchemeFlg = nrSchemeFlg;
    }

    public String getNxtNoTblCode() {
        return nxtNoTblCode;
    }

    public void setNxtNoTblCode(String nxtNoTblCode) {
        this.nxtNoTblCode = nxtNoTblCode;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getOverrideDfltChq() {
        return overrideDfltChq;
    }

    public void setOverrideDfltChq(String overrideDfltChq) {
        this.overrideDfltChq = overrideDfltChq;
    }

    public String getPdGlSubHead() {
        return pdGlSubHead;
    }

    public void setPdGlSubHead(String pdGlSubHead) {
        this.pdGlSubHead = pdGlSubHead;
    }

    public String getPegIntForAcctFlg() {
        return pegIntForAcctFlg;
    }

    public void setPegIntForAcctFlg(String pegIntForAcctFlg) {
        this.pegIntForAcctFlg = pegIntForAcctFlg;
    }

    public String getPegRevCust() {
        return pegRevCust;
    }

    public void setPegRevCust(String pegRevCust) {
        this.pegRevCust = pegRevCust;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getRefferedAcctClosure() {
        return refferedAcctClosure;
    }

    public void setRefferedAcctClosure(String refferedAcctClosure) {
        this.refferedAcctClosure = refferedAcctClosure;
    }

    public String getRptTransFlag() {
        return rptTransFlag;
    }

    public void setRptTransFlag(String rptTransFlag) {
        this.rptTransFlag = rptTransFlag;
    }

    public String getSanctionedExceed() {
        return sanctionedExceed;
    }

    public void setSanctionedExceed(String sanctionedExceed) {
        this.sanctionedExceed = sanctionedExceed;
    }

    public String getSanctionedLmt() {
        return sanctionedLmt;
    }

    public void setSanctionedLmt(String sanctionedLmt) {
        this.sanctionedLmt = sanctionedLmt;
    }

    public String getSchemeCrDrInd() {
        return schemeCrDrInd;
    }

    public void setSchemeCrDrInd(String schemeCrDrInd) {
        this.schemeCrDrInd = schemeCrDrInd;
    }

    public String getSchemeDesc() {
        return schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }

    public String getSchemeFlag() {
        return schemeFlag;
    }

    public void setSchemeFlag(String schemeFlag) {
        this.schemeFlag = schemeFlag;
    }

    public String getSchemeSupId() {
        return schemeSupId;
    }

    public void setSchemeSupId(String schemeSupId) {
        this.schemeSupId = schemeSupId;
    }

    public String getStAutoTodGrntExcp() {
        return stAutoTodGrntExcp;
    }

    public void setStAutoTodGrntExcp(String stAutoTodGrntExcp) {
        this.stAutoTodGrntExcp = stAutoTodGrntExcp;
    }

    public String getStacctBalBelowmin() {
        return stacctBalBelowmin;
    }

    public void setStacctBalBelowmin(String stacctBalBelowmin) {
        this.stacctBalBelowmin = stacctBalBelowmin;
    }

    public String getStacctFrozenExcep() {
        return stacctFrozenExcep;
    }

    public void setStacctFrozenExcep(String stacctFrozenExcep) {
        this.stacctFrozenExcep = stacctFrozenExcep;
    }

    public String getStacctInCrBal() {
        return stacctInCrBal;
    }

    public void setStacctInCrBal(String stacctInCrBal) {
        this.stacctInCrBal = stacctInCrBal;
    }

    public String getStacctInDrBal() {
        return stacctInDrBal;
    }

    public void setStacctInDrBal(String stacctInDrBal) {
        this.stacctInDrBal = stacctInDrBal;
    }

    public String getStacctNameChng() {
        return stacctNameChng;
    }

    public void setStacctNameChng(String stacctNameChng) {
        this.stacctNameChng = stacctNameChng;
    }

    public String getStacctOpenMatrix() {
        return stacctOpenMatrix;
    }

    public void setStacctOpenMatrix(String stacctOpenMatrix) {
        this.stacctOpenMatrix = stacctOpenMatrix;
    }

    public String getStaffSchmeflg() {
        return staffSchmeflg;
    }

    public void setStaffSchmeflg(String staffSchmeflg) {
        this.staffSchmeflg = staffSchmeflg;
    }

    public String getStaleInstrment() {
        return staleInstrment;
    }

    public void setStaleInstrment(String staleInstrment) {
        this.staleInstrment = staleInstrment;
    }

    public String getStbackDatedTrn() {
        return stbackDatedTrn;
    }

    public void setStbackDatedTrn(String stbackDatedTrn) {
        this.stbackDatedTrn = stbackDatedTrn;
    }

    public String getStcashTrn() {
        return stcashTrn;
    }

    public void setStcashTrn(String stcashTrn) {
        this.stcashTrn = stcashTrn;
    }

    public String getStclrTrnExcp() {
        return stclrTrnExcp;
    }

    public void setStclrTrnExcp(String stclrTrnExcp) {
        this.stclrTrnExcp = stclrTrnExcp;
    }

    public String getStcusrDrWithoutChq() {
        return stcusrDrWithoutChq;
    }

    public void setStcusrDrWithoutChq(String stcusrDrWithoutChq) {
        this.stcusrDrWithoutChq = stcusrDrWithoutChq;
    }

    public String getStcustIdMismatchDr() {
        return stcustIdMismatchDr;
    }

    public void setStcustIdMismatchDr(String stcustIdMismatchDr) {
        this.stcustIdMismatchDr = stcustIdMismatchDr;
    }

    public String getStcustIdMismatchcr() {
        return stcustIdMismatchcr;
    }

    public void setStcustIdMismatchcr(String stcustIdMismatchcr) {
        this.stcustIdMismatchcr = stcustIdMismatchcr;
    }

    public String getStdfltClgTransCode() {
        return stdfltClgTransCode;
    }

    public void setStdfltClgTransCode(String stdfltClgTransCode) {
        this.stdfltClgTransCode = stdfltClgTransCode;
    }

    public String getStdfltIntParmExcp() {
        return stdfltIntParmExcp;
    }

    public void setStdfltIntParmExcp(String stdfltIntParmExcp) {
        this.stdfltIntParmExcp = stdfltIntParmExcp;
    }

    public String getStdrTranToPast() {
        return stdrTranToPast;
    }

    public void setStdrTranToPast(String stdrTranToPast) {
        this.stdrTranToPast = stdrTranToPast;
    }

    public String getStinsufficientBal() {
        return stinsufficientBal;
    }

    public void setStinsufficientBal(String stinsufficientBal) {
        this.stinsufficientBal = stinsufficientBal;
    }

    public String getStintForPastDue() {
        return stintForPastDue;
    }

    public void setStintForPastDue(String stintForPastDue) {
        this.stintForPastDue = stintForPastDue;
    }

    public String getStinvalidReportCode() {
        return stinvalidReportCode;
    }

    public void setStinvalidReportCode(String stinvalidReportCode) {
        this.stinvalidReportCode = stinvalidReportCode;
    }

    public String getStliabilityExceed() {
        return stliabilityExceed;
    }

    public void setStliabilityExceed(String stliabilityExceed) {
        this.stliabilityExceed = stliabilityExceed;
    }

    public String getStmamoPadExist() {
        return stmamoPadExist;
    }

    public void setStmamoPadExist(String stmamoPadExist) {
        this.stmamoPadExist = stmamoPadExist;
    }

    public String getStminBalPenalChrg() {
        return stminBalPenalChrg;
    }

    public void setStminBalPenalChrg(String stminBalPenalChrg) {
        this.stminBalPenalChrg = stminBalPenalChrg;
    }

    public String getStmtFrqDay() {
        return stmtFrqDay;
    }

    public void setStmtFrqDay(String stmtFrqDay) {
        this.stmtFrqDay = stmtFrqDay;
    }

    public String getStmtFrqNP() {
        return stmtFrqNP;
    }

    public void setStmtFrqNP(String stmtFrqNP) {
        this.stmtFrqNP = stmtFrqNP;
    }

    public String getStmtFrqNum() {
        return stmtFrqNum;
    }

    public void setStmtFrqNum(String stmtFrqNum) {
        this.stmtFrqNum = stmtFrqNum;
    }

    public String getStmtFrqStartDt() {
        return stmtFrqStartDt;
    }

    public void setStmtFrqStartDt(String stmtFrqStartDt) {
        this.stmtFrqStartDt = stmtFrqStartDt;
    }

    public String getStmtFrqType() {
        return stmtFrqType;
    }

    public void setStmtFrqType(String stmtFrqType) {
        this.stmtFrqType = stmtFrqType;
    }

    public String getStmtMsg() {
        return stmtMsg;
    }

    public void setStmtMsg(String stmtMsg) {
        this.stmtMsg = stmtMsg;
    }

    public String getStoverrideDfltChq() {
        return stoverrideDfltChq;
    }

    public void setStoverrideDfltChq(String stoverrideDfltChq) {
        this.stoverrideDfltChq = stoverrideDfltChq;
    }

    public String getStrefferedAcctClosure() {
        return strefferedAcctClosure;
    }

    public void setStrefferedAcctClosure(String strefferedAcctClosure) {
        this.strefferedAcctClosure = strefferedAcctClosure;
    }

    public String getStsanctionedExceed() {
        return stsanctionedExceed;
    }

    public void setStsanctionedExceed(String stsanctionedExceed) {
        this.stsanctionedExceed = stsanctionedExceed;
    }

    public String getStsanctionedLmt() {
        return stsanctionedLmt;
    }

    public void setStsanctionedLmt(String stsanctionedLmt) {
        this.stsanctionedLmt = stsanctionedLmt;
    }

    public String getStstaleInstrment() {
        return ststaleInstrment;
    }

    public void setStstaleInstrment(String ststaleInstrment) {
        this.ststaleInstrment = ststaleInstrment;
    }

    public String getSttransferTrn() {
        return sttransferTrn;
    }

    public void setSttransferTrn(String sttransferTrn) {
        this.sttransferTrn = sttransferTrn;
    }

    public String getStuserTodGrntExcp() {
        return stuserTodGrntExcp;
    }

    public void setStuserTodGrntExcp(String stuserTodGrntExcp) {
        this.stuserTodGrntExcp = stuserTodGrntExcp;
    }

    public String getStvalueDateTrn() {
        return stvalueDateTrn;
    }

    public void setStvalueDateTrn(String stvalueDateTrn) {
        this.stvalueDateTrn = stvalueDateTrn;
    }

    public String getSysgenAcctNoFlag() {
        return sysgenAcctNoFlag;
    }

    public void setSysgenAcctNoFlag(String sysgenAcctNoFlag) {
        this.sysgenAcctNoFlag = sysgenAcctNoFlag;
    }

    public String getTotalModification() {
        return totalModification;
    }

    public void setTotalModification(String totalModification) {
        this.totalModification = totalModification;
    }

    public String getTransferTrn() {
        return transferTrn;
    }

    public void setTransferTrn(String transferTrn) {
        this.transferTrn = transferTrn;
    }

    public String getTrnRefNo() {
        return trnRefNo;
    }

    public void setTrnRefNo(String trnRefNo) {
        this.trnRefNo = trnRefNo;
    }

    public String getTurnoverDetailFlag() {
        return turnoverDetailFlag;
    }

    public void setTurnoverDetailFlag(String turnoverDetailFlag) {
        this.turnoverDetailFlag = turnoverDetailFlag;
    }

    public String getTurnoverFreqDate() {
        return turnoverFreqDate;
    }

    public void setTurnoverFreqDate(String turnoverFreqDate) {
        this.turnoverFreqDate = turnoverFreqDate;
    }

    public String getTurnoverFreqDay() {
        return turnoverFreqDay;
    }

    public void setTurnoverFreqDay(String turnoverFreqDay) {
        this.turnoverFreqDay = turnoverFreqDay;
    }

    public String getTurnoverFreqNo() {
        return turnoverFreqNo;
    }

    public void setTurnoverFreqNo(String turnoverFreqNo) {
        this.turnoverFreqNo = turnoverFreqNo;
    }

    public String getTurnoverFreqNp() {
        return turnoverFreqNp;
    }

    public void setTurnoverFreqNp(String turnoverFreqNp) {
        this.turnoverFreqNp = turnoverFreqNp;
    }

    public String getTurnoverFreqType() {
        return turnoverFreqType;
    }

    public void setTurnoverFreqType(String turnoverFreqType) {
        this.turnoverFreqType = turnoverFreqType;
    }

    public String getUserTodGrntExcp() {
        return userTodGrntExcp;
    }

    public void setUserTodGrntExcp(String userTodGrntExcp) {
        this.userTodGrntExcp = userTodGrntExcp;
    }

    public String getValueDateTrn() {
        return valueDateTrn;
    }

    public void setValueDateTrn(String valueDateTrn) {
        this.valueDateTrn = valueDateTrn;
    }

    /** Creates a new instance of PM */
    public PM() {

        ddrptTransFlag = new ArrayList<SelectItem>();
        ddrptTransFlag.add(new SelectItem("0", ""));
        ddrptTransFlag.add(new SelectItem("Y", "Yes"));
        ddrptTransFlag.add(new SelectItem("N", "No"));

        ddstmtFrqType = new ArrayList<SelectItem>();
        ddstmtFrqType.add(new SelectItem("0", ""));
        ddstmtFrqType.add(new SelectItem("D", "Daily"));
        ddstmtFrqType.add(new SelectItem("W", "Weekly"));
        ddstmtFrqType.add(new SelectItem("M", "Monthly"));
        ddstmtFrqType.add(new SelectItem("Q", "Quarterly"));
        ddstmtFrqType.add(new SelectItem("H", "HalfYearly"));
        ddstmtFrqType.add(new SelectItem("Y", "Yearly"));

        ddstmtFrqNum = new ArrayList<SelectItem>();
        ddstmtFrqNum.add(new SelectItem("0", ""));
        ddstmtFrqNum.add(new SelectItem("1", "First Week"));
        ddstmtFrqNum.add(new SelectItem("2", "Second week"));
        ddstmtFrqNum.add(new SelectItem("3", "Third week"));
        ddstmtFrqNum.add(new SelectItem("4", "Forth week"));
        ddstmtFrqNum.add(new SelectItem("M", "Middle Week"));
        ddstmtFrqNum.add(new SelectItem("L", "Last week"));

        ddstmtFrqDay = new ArrayList<SelectItem>();
        ddstmtFrqDay.add(new SelectItem("0", ""));
        ddstmtFrqDay.add(new SelectItem("1", "Sun"));
        ddstmtFrqDay.add(new SelectItem("2", "Mon"));
        ddstmtFrqDay.add(new SelectItem("3", "Tue"));
        ddstmtFrqDay.add(new SelectItem("4", "Wed"));
        ddstmtFrqDay.add(new SelectItem("5", "Thu"));
        ddstmtFrqDay.add(new SelectItem("6", "Fri"));
        ddstmtFrqDay.add(new SelectItem("7", "Sat"));

        ddstmtFrqNP = new ArrayList<SelectItem>();
        ddstmtFrqNP.add(new SelectItem("0", ""));
        ddstmtFrqNP.add(new SelectItem("N", "Next"));
        ddstmtFrqNP.add(new SelectItem("P", "Previous"));
        ddstmtFrqNP.add(new SelectItem("S", "Skip"));

        ddschemeCrDrInd = new ArrayList<SelectItem>();
        ddschemeCrDrInd.add(new SelectItem("0", ""));
        ddschemeCrDrInd.add(new SelectItem("C", "Credit"));
        ddschemeCrDrInd.add(new SelectItem("D", "Debit"));

        ddturnoverDetailFlag = new ArrayList<SelectItem>();
        ddturnoverDetailFlag.add(new SelectItem("0", ""));
        ddturnoverDetailFlag.add(new SelectItem("Y", "Yes"));
        ddturnoverDetailFlag.add(new SelectItem("N", "No"));

        ddsysgenAcctNoFlag = new ArrayList<SelectItem>();
        ddsysgenAcctNoFlag.add(new SelectItem("0", ""));
        ddsysgenAcctNoFlag.add(new SelectItem("Y", "Yes"));
        ddsysgenAcctNoFlag.add(new SelectItem("N", "No"));


        ddturnoverFreqType = new ArrayList<SelectItem>();
        ddturnoverFreqType.add(new SelectItem("0", ""));
        ddturnoverFreqType.add(new SelectItem("D", "Daily"));
        ddturnoverFreqType.add(new SelectItem("W", "Weekly"));
        ddturnoverFreqType.add(new SelectItem("M", "Monthly"));
        ddturnoverFreqType.add(new SelectItem("Q", "Quarterly"));
        ddturnoverFreqType.add(new SelectItem("H", "HalfYearly"));
        ddturnoverFreqType.add(new SelectItem("Y", "Yearly"));

        ddturnoverFreqNo = new ArrayList<SelectItem>();
        ddturnoverFreqNo.add(new SelectItem("0", ""));
        ddturnoverFreqNo.add(new SelectItem("1", "First Week"));
        ddturnoverFreqNo.add(new SelectItem("2", "Second week"));
        ddturnoverFreqNo.add(new SelectItem("3", "Third week"));
        ddturnoverFreqNo.add(new SelectItem("4", "Forth week"));
        ddturnoverFreqNo.add(new SelectItem("M", "Middle Week"));
        ddturnoverFreqNo.add(new SelectItem("L", "Last week"));

        ddturnoverFreqDay = new ArrayList<SelectItem>();
        ddturnoverFreqDay.add(new SelectItem("0", ""));
        ddturnoverFreqDay.add(new SelectItem("1", "Sun"));
        ddturnoverFreqDay.add(new SelectItem("2", "Mon"));
        ddturnoverFreqDay.add(new SelectItem("3", "Tue"));
        ddturnoverFreqDay.add(new SelectItem("4", "Wed"));
        ddturnoverFreqDay.add(new SelectItem("5", "Thu"));
        ddturnoverFreqDay.add(new SelectItem("6", "Fri"));
        ddturnoverFreqDay.add(new SelectItem("7", "Sat"));

        ddturnoverFreqNp = new ArrayList<SelectItem>();
        ddturnoverFreqNp.add(new SelectItem("0", ""));
        ddturnoverFreqNp.add(new SelectItem("N", "Next"));
        ddturnoverFreqNp.add(new SelectItem("P", "Previous"));
        ddturnoverFreqNp.add(new SelectItem("S", "Skip"));

        ddfcnrSchemeFlg = new ArrayList<SelectItem>();
        ddfcnrSchemeFlg.add(new SelectItem("0", ""));
        ddfcnrSchemeFlg.add(new SelectItem("Y", "Yes"));
        ddfcnrSchemeFlg.add(new SelectItem("N", "No"));

        ddacctCloserAcrossSols = new ArrayList<SelectItem>();
        ddacctCloserAcrossSols.add(new SelectItem("0", ""));
        ddacctCloserAcrossSols.add(new SelectItem("Y", "Yes"));
        ddacctCloserAcrossSols.add(new SelectItem("N", "No"));

        ddschemeFlag = new ArrayList<SelectItem>();
        ddschemeFlag.add(new SelectItem("0", ""));
        ddschemeFlag.add(new SelectItem("Y", "Yes"));
        ddschemeFlag.add(new SelectItem("N", "No"));

        ddopt = new ArrayList<SelectItem>();
        ddopt.add(new SelectItem("0", ""));
        ddopt.add(new SelectItem("I", "Inquire"));
        ddopt.add(new SelectItem("A", "Add"));
        ddopt.add(new SelectItem("M", "Modify"));
        ddopt.add(new SelectItem("D", "Delete"));

        ddcommitCalMethd = new ArrayList<SelectItem>();
        ddcommitCalMethd.add(new SelectItem("0", ""));
        ddcommitCalMethd.add(new SelectItem("L", "On Outstanding"));
        ddcommitCalMethd.add(new SelectItem("S", "On Sanction Limit"));
        ddcommitCalMethd.add(new SelectItem("D", "Daily debit Balance"));
        ddcommitCalMethd.add(new SelectItem("W", "Weighted debit Average"));        

        ddstaffSchmeflg = new ArrayList<SelectItem>();
        ddstaffSchmeflg.add(new SelectItem("0", ""));
        ddstaffSchmeflg.add(new SelectItem("Y", "Yes"));
        ddstaffSchmeflg.add(new SelectItem("N", "No"));

        ddnrSchemeFlg = new ArrayList<SelectItem>();
        ddnrSchemeFlg.add(new SelectItem("0", ""));
        ddnrSchemeFlg.add(new SelectItem("Y", "Yes"));
        ddnrSchemeFlg.add(new SelectItem("N", "No"));

        ddproductGroup = new ArrayList<SelectItem>();
        ddproductGroup.add(new SelectItem("0", ""));
        ddproductGroup.add(new SelectItem("SB", "Saving"));

        ddminPostWorkClass = new ArrayList<SelectItem>();
        ddminPostWorkClass.add(new SelectItem("0", ""));
        ddminPostWorkClass.add(new SelectItem("CONPH", "Confirmation Over Phone"));
        ddminPostWorkClass.add(new SelectItem("CONWI", "Confirmation Over Wire"));

        ddlinkTranSale = new ArrayList<SelectItem>();
        ddlinkTranSale.add(new SelectItem("0", ""));
        ddlinkTranSale.add(new SelectItem("Y", "Yes"));
        ddlinkTranSale.add(new SelectItem("N", "No"));

        ddacctMaintPeriod = new ArrayList<SelectItem>();
        ddacctMaintPeriod.add(new SelectItem("0", ""));
        ddacctMaintPeriod.add(new SelectItem("M", "Monthly"));
        ddacctMaintPeriod.add(new SelectItem("Q", "Quarterly"));
        ddacctMaintPeriod.add(new SelectItem("H", "HalfYearly"));
        ddacctMaintPeriod.add(new SelectItem("Y", "Yearly"));

        dddfltInsttype = new ArrayList<SelectItem>();
        dddfltInsttype.add(new SelectItem("0", ""));
        dddfltInsttype.add(new SelectItem("SHQ", "SHQ"));
        dddfltInsttype.add(new SelectItem("TPO", "TPO"));
        dddfltInsttype.add(new SelectItem("PO", "PO"));
        dddfltInsttype.add(new SelectItem("DD", "DD"));

        ddtrnRefNo = new ArrayList<SelectItem>();
        ddtrnRefNo.add(new SelectItem("0", ""));
        ddtrnRefNo.add(new SelectItem("Y", "Yes"));
        ddtrnRefNo.add(new SelectItem("N", "No"));

        ddpegIntForAcctFlg = new ArrayList<SelectItem>();
        ddpegIntForAcctFlg.add(new SelectItem("0", ""));
        ddpegIntForAcctFlg.add(new SelectItem("Y", "Yes"));
        ddpegIntForAcctFlg.add(new SelectItem("N", "No"));

        ddmodificationPegFlg = new ArrayList<SelectItem>();
        ddmodificationPegFlg.add(new SelectItem("0", ""));
        ddmodificationPegFlg.add(new SelectItem("Y", "Yes"));
        ddmodificationPegFlg.add(new SelectItem("N", "No"));

        ddpegRevCust = new ArrayList<SelectItem>();
        ddpegRevCust.add(new SelectItem("0", ""));
        ddpegRevCust.add(new SelectItem("Y", "Yes"));
        ddpegRevCust.add(new SelectItem("N", "No"));

        ddacctPartionAllowed = new ArrayList<SelectItem>();
        ddacctPartionAllowed.add(new SelectItem("0", ""));
        ddacctPartionAllowed.add(new SelectItem("Y", "Yes"));
        ddacctPartionAllowed.add(new SelectItem("N", "No"));

        ddAutoRenewal = new ArrayList<SelectItem>();
        ddAutoRenewal.add(new SelectItem("0", ""));
        ddAutoRenewal.add(new SelectItem("Y", "Yes"));
        ddAutoRenewal.add(new SelectItem("N", "No"));

    }

    public void descLiabilityExceed() {
        System.out.println("First exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.liabilityExceed == null || this.liabilityExceed.equalsIgnoreCase("") || liabilityExceed.length() == 0) {
                this.setStliabilityExceed("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.liabilityExceed);
                this.setStliabilityExceed(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStliabilityExceed("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descInsufficientBal() {
        System.out.println("Second exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.insufficientBal == null || this.insufficientBal.equalsIgnoreCase("") || insufficientBal.length() == 0) {
                this.setStinsufficientBal("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.insufficientBal);
                this.setStinsufficientBal(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStinsufficientBal("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAcctNameChng() {
        System.out.println("Third exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.acctNameChng == null || this.acctNameChng.equalsIgnoreCase("") || acctNameChng.length() == 0) {
                this.setStacctNameChng("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctNameChng);
                this.setStacctNameChng(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStacctNameChng("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descStaleInstrment() {
        System.out.println("4th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.staleInstrment == null || this.staleInstrment.equalsIgnoreCase("") || staleInstrment.length() == 0) {
                this.setStstaleInstrment("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.staleInstrment);
                this.setStstaleInstrment(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStstaleInstrment("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAcctFrozenExcep() {
        System.out.println("5th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.acctFrozenExcep == null || this.acctFrozenExcep.equalsIgnoreCase("") || acctFrozenExcep.length() == 0) {
                this.setStacctFrozenExcep("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctFrozenExcep);
                this.setStacctFrozenExcep(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStacctFrozenExcep("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCustIdMismatchDr() {
        System.out.println("6th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.custIdMismatchDr == null || this.custIdMismatchDr.equalsIgnoreCase("") || custIdMismatchDr.length() == 0) {
                this.setStcustIdMismatchDr("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.custIdMismatchDr);
                this.setStcustIdMismatchDr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStcustIdMismatchDr("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCustIdMismatchcr() {
        System.out.println("7th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.custIdMismatchcr == null || this.custIdMismatchcr.equalsIgnoreCase("") || custIdMismatchcr.length() == 0) {
                this.setStcustIdMismatchcr("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.custIdMismatchcr);
                this.setStcustIdMismatchcr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStcustIdMismatchcr("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descBackDatedTrn() {
        System.out.println("8th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.backDatedTrn == null || this.backDatedTrn.equalsIgnoreCase("") || backDatedTrn.length() == 0) {
                this.setStbackDatedTrn("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.backDatedTrn);
                this.setStbackDatedTrn(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStbackDatedTrn("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descValueDateTrn() {
        System.out.println("9th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.valueDateTrn == null || this.valueDateTrn.equalsIgnoreCase("") || valueDateTrn.length() == 0) {
                this.setStvalueDateTrn("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.valueDateTrn);
                this.setStvalueDateTrn(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStvalueDateTrn("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCashTrn() {
        System.out.println("10th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.cashTrn == null || this.cashTrn.equalsIgnoreCase("") || cashTrn.length() == 0) {
                this.setStcashTrn("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cashTrn);
                this.setStcashTrn(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStcashTrn("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAcctOpenMatrix() {
        System.out.println("11th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.acctOpenMatrix == null || this.acctOpenMatrix.equalsIgnoreCase("") || acctOpenMatrix.length() == 0) {
                this.setStacctOpenMatrix("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctOpenMatrix);
                this.setStacctOpenMatrix(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStacctOpenMatrix("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClrTrnExcp() {
        System.out.println("12th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.clrTrnExcp == null || this.clrTrnExcp.equalsIgnoreCase("") || clrTrnExcp.length() == 0) {
                this.setStclrTrnExcp("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clrTrnExcp);
                this.setStclrTrnExcp(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStclrTrnExcp("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descTransferTrn() {
        System.out.println("13th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.transferTrn == null || this.transferTrn.equalsIgnoreCase("") || transferTrn.length() == 0) {
                this.setSttransferTrn("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.transferTrn);
                this.setSttransferTrn(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setSttransferTrn("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descSanctionedLmt() {
        System.out.println("14th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.sanctionedLmt == null || this.sanctionedLmt.equalsIgnoreCase("") || sanctionedLmt.length() == 0) {
                this.setStsanctionedLmt("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.sanctionedLmt);
                this.setStsanctionedLmt(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStsanctionedLmt("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descSanctionedExceed() {
        System.out.println("15th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.sanctionedExceed == null || this.sanctionedExceed.equalsIgnoreCase("") || sanctionedExceed.length() == 0) {
                this.setStsanctionedExceed("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.sanctionedExceed);
                this.setStsanctionedExceed(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStsanctionedExceed("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCusrDrWithoutChq() {
        System.out.println("16th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.cusrDrWithoutChq == null || this.cusrDrWithoutChq.equalsIgnoreCase("") || cusrDrWithoutChq.length() == 0) {
                this.setStcusrDrWithoutChq("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cusrDrWithoutChq);
                this.setStcusrDrWithoutChq(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStcusrDrWithoutChq("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descRefferedAcctClosure() {
        System.out.println("17th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.refferedAcctClosure == null || this.refferedAcctClosure.equalsIgnoreCase("") || refferedAcctClosure.length() == 0) {
                this.setStrefferedAcctClosure("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.refferedAcctClosure);
                this.setStrefferedAcctClosure(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStrefferedAcctClosure("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descUserTodGrntExcp() {
        System.out.println("18th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.userTodGrntExcp == null || this.userTodGrntExcp.equalsIgnoreCase("") || userTodGrntExcp.length() == 0) {
                this.setStuserTodGrntExcp("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.userTodGrntExcp);
                this.setStuserTodGrntExcp(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStuserTodGrntExcp("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAutoTodGrntExcp() {
        System.out.println("19th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.autoTodGrntExcp == null || this.autoTodGrntExcp.equalsIgnoreCase("") || autoTodGrntExcp.length() == 0) {
                this.setStAutoTodGrntExcp("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.autoTodGrntExcp);
                this.setStAutoTodGrntExcp(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStAutoTodGrntExcp("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAcctInDrBal() {
        System.out.println("20th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.acctInDrBal == null || this.acctInDrBal.equalsIgnoreCase("") || acctInDrBal.length() == 0) {
                this.setStacctInDrBal("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctInDrBal);
                this.setStacctInDrBal(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStacctInDrBal("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAcctInCrBal() {
        System.out.println("21st exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.acctInCrBal == null || this.acctInCrBal.equalsIgnoreCase("") || acctInCrBal.length() == 0) {
                this.setStacctInCrBal("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctInCrBal);
                this.setStacctInCrBal(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStacctInCrBal("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descOverrideDfltChq() {
        System.out.println("22nd exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.overrideDfltChq == null || this.overrideDfltChq.equalsIgnoreCase("") || overrideDfltChq.length() == 0) {
                this.setStoverrideDfltChq("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.overrideDfltChq);
                this.setStoverrideDfltChq(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStoverrideDfltChq("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descInvalidReportCode() {
        System.out.println("23rd exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.invalidReportCode == null || this.invalidReportCode.equalsIgnoreCase("") || invalidReportCode.length() == 0) {
                this.setStinvalidReportCode("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.invalidReportCode);
                this.setStinvalidReportCode(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStinvalidReportCode("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descMamoPadExist() {
        System.out.println("24th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.mamoPadExist == null || this.mamoPadExist.equalsIgnoreCase("") || mamoPadExist.length() == 0) {
                this.setStmamoPadExist("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.mamoPadExist);
                this.setStmamoPadExist(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStmamoPadExist("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDfltIntParmExcp() {
        System.out.println("25th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.dfltIntParmExcp == null || this.dfltIntParmExcp.equalsIgnoreCase("") || dfltIntParmExcp.length() == 0) {
                this.setStdfltIntParmExcp("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.dfltIntParmExcp);
                this.setStdfltIntParmExcp(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStdfltIntParmExcp("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descAcctBalBelowmin() {
        System.out.println("26th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.acctBalBelowmin == null || this.acctBalBelowmin.equalsIgnoreCase("") || acctBalBelowmin.length() == 0) {
                this.setStacctBalBelowmin("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.acctBalBelowmin);
                this.setStacctBalBelowmin(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStacctBalBelowmin("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descMinBalPenalChrg() {
        System.out.println("27th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.minBalPenalChrg == null || this.minBalPenalChrg.equalsIgnoreCase("") || minBalPenalChrg.length() == 0) {
                this.setStminBalPenalChrg("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.minBalPenalChrg);
                this.setStminBalPenalChrg(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStminBalPenalChrg("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descIntForPastDue() {
        System.out.println("28th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.intForPastDue == null || this.intForPastDue.equalsIgnoreCase("") || intForPastDue.length() == 0) {
                this.setStintForPastDue("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.intForPastDue);
                this.setStintForPastDue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStintForPastDue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDrTranToPast() {
        System.out.println("29th exception code onblur method.");
        this.schemeMaster.setMessage("");
        try {
            if (this.drTranToPast == null || this.drTranToPast.equalsIgnoreCase("") || drTranToPast.length() == 0) {
                this.setStdrTranToPast("Please Fill The Right Code.");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.drTranToPast);
                this.setStdrTranToPast(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.setStdrTranToPast("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectPmDetails() {
        this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeGeneralSchemeParameterMasterTO singleTO = schemeMasterManagementDelegate.getParameterMasterDetails(schemeMaster.getSchemeCode());
            if (singleTO != null) {
                if (singleTO.getSchemeDescription() == null || singleTO.getSchemeDescription().equalsIgnoreCase("")) {
                    this.setSchemeDesc("");
                } else {
                    this.setSchemeDesc(singleTO.getSchemeDescription());
                }
                if (singleTO.getNativeSchemeDescription() == null || singleTO.getNativeSchemeDescription().equalsIgnoreCase("")) {
                    this.setNativeSchemeDesc("");
                } else {
                    this.setNativeSchemeDesc(singleTO.getNativeSchemeDescription());
                }
                if (singleTO.getRptTranFlag() == null || singleTO.getRptTranFlag().equalsIgnoreCase("")) {
                    this.setRptTransFlag("");
                } else {
                    this.setRptTransFlag(singleTO.getRptTranFlag());
                }
                if (singleTO.getSchemeSupervisorId() == null || singleTO.getSchemeSupervisorId().equalsIgnoreCase("")) {
                    this.setSchemeSupId("");
                } else {
                    this.setSchemeSupId(singleTO.getSchemeSupervisorId());
                }
                if (singleTO.getStmtFreqType() == null || singleTO.getStmtFreqType().equalsIgnoreCase("")) {
                    this.setStmtFrqType("");
                } else {
                    this.setStmtFrqType(singleTO.getStmtFreqType());
                }
                if (singleTO.getStmtFreqWeekNumber() == null || singleTO.getStmtFreqWeekNumber().equalsIgnoreCase("")) {
                    this.setStmtFrqNum("");
                } else {
                    this.setStmtFrqNum(singleTO.getStmtFreqWeekNumber());
                }
                if (singleTO.getStmtFreqWeekDay() == null || singleTO.getStmtFreqWeekDay().equalsIgnoreCase("")) {
                    this.setStmtFrqDay("");
                } else {
                    this.setStmtFrqDay(singleTO.getStmtFreqWeekDay());
                }
                if (singleTO.getStmtFreqStartDate() == null || singleTO.getStmtFreqStartDate().equalsIgnoreCase("")) {
                    this.setStmtFrqStartDt("");
                } else {
                    this.setStmtFrqStartDt(singleTO.getStmtFreqStartDate());
                }
                if (singleTO.getStmtFreqNp() == null || singleTO.getStmtFreqNp().equalsIgnoreCase("")) {
                    this.setStmtFrqNP("");
                } else {
                    this.setStmtFrqNP(singleTO.getStmtFreqNp());
                }
                if (singleTO.getSchemeCrDrInd() == null || singleTO.getSchemeCrDrInd().equalsIgnoreCase("")) {
                    this.setSchemeCrDrInd("");
                } else {
                    this.setSchemeCrDrInd(singleTO.getSchemeCrDrInd());
                }
                if (singleTO.getTurnOverDetailFlag() == null || singleTO.getTurnOverDetailFlag().equalsIgnoreCase("")) {
                    this.setTurnoverDetailFlag("");
                } else {
                    this.setTurnoverDetailFlag(singleTO.getTurnOverDetailFlag());
                }
                if (singleTO.getSysGenAccNoFlag() == null) {
                    this.setSysgenAcctNoFlag("");
                } else {
                    this.setSysgenAcctNoFlag(singleTO.getSysGenAccNoFlag());
                }
                if (singleTO.getTurnOverFreqType() == null) {
                    this.setTurnoverFreqType("");
                } else {
                    this.setTurnoverFreqType(singleTO.getTurnOverFreqType());
                }
                if (singleTO.getTurnOverFreqWeekNumber() == null) {
                    this.setTurnoverFreqNo("");
                } else {
                    this.setTurnoverFreqNo(singleTO.getTurnOverFreqWeekNumber());
                }
                if (singleTO.getTurnOverFreqWeekDay() == null || singleTO.getTurnOverFreqWeekDay().equalsIgnoreCase("")) {
                    this.setTurnoverFreqDay("");
                } else {
                    this.setTurnoverFreqDay(singleTO.getTurnOverFreqWeekDay());
                }
                if (singleTO.getTurnOverFreqStartDate() == null || singleTO.getTurnOverFreqStartDate().equalsIgnoreCase("")) {
                    this.setTurnoverFreqDate("");
                } else {
                    this.setTurnoverFreqDate(singleTO.getTurnOverFreqStartDate());
                }
                if (singleTO.getTurnOverFreqNp() == null) {
                    this.setTurnoverFreqNp("");
                } else {
                    this.setTurnoverFreqNp(singleTO.getTurnOverFreqNp());
                }
                if (singleTO.getAcctPrefix() == null || singleTO.getAcctPrefix().equalsIgnoreCase("")) {
                    this.setAcctPrefix("");
                } else {
                    this.setAcctPrefix(singleTO.getAcctPrefix());
                }
                if (singleTO.getNxtNbrTblCode() == null || singleTO.getNxtNbrTblCode().equalsIgnoreCase("")) {
                    this.setNxtNoTblCode("");
                } else {
                    this.setNxtNoTblCode(singleTO.getNxtNbrTblCode());
                }
                if (singleTO.getFcnrSchemeFlag() == null || singleTO.getFcnrSchemeFlag().equalsIgnoreCase("")) {
                    this.setFcnrSchemeFlg("");
                } else {
                    this.setFcnrSchemeFlg(singleTO.getFcnrSchemeFlag());
                }
                if (singleTO.getAcctClosuerAcrossSolsAlwdFlag() == null || singleTO.getAcctClosuerAcrossSolsAlwdFlag().equalsIgnoreCase("")) {
                    this.setAcctCloserAcrossSols("");
                } else {
                    this.setAcctCloserAcrossSols(singleTO.getAcctClosuerAcrossSolsAlwdFlag());
                }
                if (singleTO.getEefcSchemeFlag() == null || singleTO.getEefcSchemeFlag().equalsIgnoreCase("")) {
                    this.setSchemeFlag("");
                } else {
                    this.setSchemeFlag(singleTO.getEefcSchemeFlag());
                }
                if (singleTO.getStatusOption() == null || singleTO.getStatusOption().equalsIgnoreCase("")) {
                    this.setOpt("");
                } else {
                    this.setOpt(singleTO.getStatusOption());
                }
                if (singleTO.getFdCrAcctPlaceHolder() == null || singleTO.getFdCrAcctPlaceHolder().equalsIgnoreCase("")) {
                    this.setFdCrAccountPlaceHolder("");
                } else {
                    this.setFdCrAccountPlaceHolder(singleTO.getFdCrAcctPlaceHolder());
                }
                if (singleTO.getCommitCalculationMethod() == null || singleTO.getCommitCalculationMethod().equalsIgnoreCase("")) {
                    this.setCommitCalMethd("");
                } else {
                    this.setCommitCalMethd(singleTO.getCommitCalculationMethod());
                }
                if (singleTO.getFdDrAcctPlaceHolder() == null || singleTO.getFdDrAcctPlaceHolder().equalsIgnoreCase("")) {
                    this.setFdDrAccountPlaceHolder("");
                } else {
                    this.setFdDrAccountPlaceHolder(singleTO.getFdDrAcctPlaceHolder());
                }
                if (singleTO.getMinCommitUtilisation() == null) {
                    this.setMinCommitUtln(new BigDecimal(0.0d));
                } else {
                    this.setMinCommitUtln(singleTO.getMinCommitUtilisation());
                }
                if (singleTO.getStaffSchemeFlag() == null || singleTO.getStaffSchemeFlag().equalsIgnoreCase("")) {
                    this.setStaffSchmeflg("");
                } else {
                    this.setStaffSchmeflg(singleTO.getStaffSchemeFlag());
                }
                if (singleTO.getDormantChargePeriodMonths() == null || singleTO.getDormantChargePeriodMonths().equalsIgnoreCase("")) {
                    this.setDormantChargeMonth("");
                } else {
                    this.setDormantChargeMonth(singleTO.getDormantChargePeriodMonths());
                }
                if (singleTO.getDormantChargePeriodDays() == null || singleTO.getDormantChargePeriodDays().equalsIgnoreCase("")) {
                    this.setDormantChargeDay("");
                } else {
                    this.setDormantChargeDay(singleTO.getDormantChargePeriodDays());
                }
                if (singleTO.getNreSchemeFlag() == null || singleTO.getNreSchemeFlag().equalsIgnoreCase("")) {
                    this.setNrSchemeFlg("");
                } else {
                    this.setNrSchemeFlg(singleTO.getNreSchemeFlag());
                }
                if (singleTO.getInactiveChargePeriodMonths() == null || singleTO.getInactiveChargePeriodMonths().equalsIgnoreCase("")) {
                    this.setInactiveChrgmnth("");
                } else {
                    this.setInactiveChrgmnth(singleTO.getInactiveChargePeriodMonths());
                }
                if (singleTO.getInactiveChargePeriodDays() == null || singleTO.getInactiveChargePeriodDays().equalsIgnoreCase("")) {
                    this.setInactiveChrgDay("");
                } else {
                    this.setInactiveChrgDay(singleTO.getInactiveChargePeriodDays());
                }
                if (singleTO.getNewAccountDuration() == null || singleTO.getNewAccountDuration().equalsIgnoreCase("")) {
                    this.setNewAcctDur("");
                } else {
                    this.setNewAcctDur(singleTO.getNewAccountDuration());
                }
                if (singleTO.getProductGroup() == null || singleTO.getProductGroup().equalsIgnoreCase("")) {
                    this.setProductGroup("");
                } else {
                    this.setProductGroup(singleTO.getProductGroup());
                }
                if (singleTO.getMinimumPostWorkClass() == null || singleTO.getMinimumPostWorkClass().equalsIgnoreCase("")) {
                    this.setMinPostWorkClass("");
                } else {
                    this.setMinPostWorkClass(singleTO.getMinimumPostWorkClass());
                }
                if (singleTO.getLinkTranToPurchaseSale() == null || singleTO.getLinkTranToPurchaseSale().equalsIgnoreCase("")) {
                    this.setLinkTranSale("");
                } else {
                    this.setLinkTranSale(singleTO.getLinkTranToPurchaseSale());
                }
                if (singleTO.getMinimumAccountClosurePeriodMonths() == null || singleTO.getMinimumAccountClosurePeriodMonths().equalsIgnoreCase("")) {
                    this.setMinAcctClosurePeriodMonth("");
                } else {
                    this.setMinAcctClosurePeriodMonth(singleTO.getMinimumAccountClosurePeriodMonths());
                }
                if (singleTO.getMinimumAccountClosurePeriodDays() == null || singleTO.getMinimumAccountClosurePeriodDays().equalsIgnoreCase("")) {
                    this.setMinAcctClosurePeriodDay("");
                } else {
                    this.setMinAcctClosurePeriodDay(singleTO.getMinimumAccountClosurePeriodDays());
                }
                if (singleTO.getAccountMaintenancePeriod() == null || singleTO.getAccountMaintenancePeriod().equalsIgnoreCase("")) {
                    this.setAcctMaintPeriod("");
                } else {
                    this.setAcctMaintPeriod(singleTO.getAccountMaintenancePeriod());
                }
                if (singleTO.getDfltClgTranCode() == null || singleTO.getDfltClgTranCode().equalsIgnoreCase("")) {
                    this.setDfltClgTransCode("");
                } else {
                    this.setDfltClgTransCode(singleTO.getDfltClgTranCode());
                }
                if (singleTO.getDfltInstrumentType() == null || singleTO.getDfltInstrumentType().equalsIgnoreCase("")) {
                    this.setDfltInsttype("");
                } else {
                    this.setDfltInsttype(singleTO.getDfltInstrumentType());
                }
                if (singleTO.getTransactionReferenceNumberFlag() == null || singleTO.getTransactionReferenceNumberFlag().equalsIgnoreCase("")) {
                    this.setTrnRefNo("");
                } else {
                    this.setTrnRefNo(singleTO.getTransactionReferenceNumberFlag());
                }
                if (singleTO.getPegInterestForAccountFlag() == null || singleTO.getPegInterestForAccountFlag().equalsIgnoreCase("")) {
                    this.setPegIntForAcctFlg("");
                } else {
                    this.setPegIntForAcctFlg(singleTO.getPegInterestForAccountFlag());
                }
                if (singleTO.getModificationofPegAllowedFlag() == null || singleTO.getModificationofPegAllowedFlag().equalsIgnoreCase("")) {
                    this.setModificationPegFlg("");
                } else {
                    this.setModificationPegFlg(singleTO.getModificationofPegAllowedFlag());
                }
                if (singleTO.getPegRevCustPrefFromCustMastFlag() == null || singleTO.getPegRevCustPrefFromCustMastFlag().equalsIgnoreCase("")) {
                    this.setPegRevCust("");
                } else {
                    this.setPegRevCust(singleTO.getPegRevCustPrefFromCustMastFlag());
                }
                if (singleTO.getAcctPartitionAllowed() == null || singleTO.getAcctPartitionAllowed().equalsIgnoreCase("")) {
                    this.setAcctPartionAllowed("");
                } else {
                    this.setAcctPartionAllowed(singleTO.getAcctPartitionAllowed());
                }
                if (singleTO.getAutoRenewalPeriodFlag() == null || singleTO.getAutoRenewalPeriodFlag().equalsIgnoreCase("")) {
                    this.setAutoRenewal("");
                } else {
                    this.setAutoRenewal(singleTO.getAutoRenewalPeriodFlag());
                }
                if (singleTO.getPdGlSubheadCode() == null || singleTO.getPdGlSubheadCode().equalsIgnoreCase("")) {
                    this.setPdGlSubHead("");
                } else {
                    this.setPdGlSubHead(singleTO.getPdGlSubheadCode());
                }
                if (singleTO.getStmtMessage() == null || singleTO.getStmtMessage().equalsIgnoreCase("")) {
                    this.setStmtMsg("");
                } else {
                    this.setStmtMsg(singleTO.getStmtMessage());
                }
                if (singleTO.getNativeStmtMessage() == null || singleTO.getNativeStmtMessage().equalsIgnoreCase("")) {
                    this.setNativeStmtMsg("");
                } else {
                    this.setNativeStmtMsg(singleTO.getNativeStmtMessage());
                }
                if (singleTO.getCreatedByUserId() == null || singleTO.getCreatedByUserId().equalsIgnoreCase("")) {
                    this.setCretedBy("");
                } else {
                    this.setCretedBy(singleTO.getCreatedByUserId());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (singleTO.getCreationDate() == null) {
                    this.setCreateDt("");
                } else {
                    //String tempCreationDt= sdf.format(singleTO.getCreationDate());
                    this.setCreateDt(sdf.format(singleTO.getCreationDate()));
                }
                if (singleTO.getLastUpdatedByUserId() == null || singleTO.getLastUpdatedByUserId().equalsIgnoreCase("")) {
                    this.setLastUpdateBy("");
                } else {
                    this.setLastUpdateBy(singleTO.getLastUpdatedByUserId());
                }
                if (singleTO.getLastUpdatedDate() == null) {
                    this.setLastUpdateDt("");
                } else {
                    this.setLastUpdateDt(sdf.format(singleTO.getLastUpdatedDate()));
                }
                if (singleTO.getTotalModifications() == null) {
                    this.setTotalModification("0");
                } else {
                    this.setTotalModification(singleTO.getTotalModifications().toString());
                }
                if (singleTO.getLiabilityExceedsGroupLimitExcep() == null || singleTO.getLiabilityExceedsGroupLimitExcep().equalsIgnoreCase("")) {
                    this.setLiabilityExceed("");
                } else {
                    this.setLiabilityExceed(singleTO.getLiabilityExceedsGroupLimitExcep());
                    descLiabilityExceed();
                }
                if (singleTO.getInsufficientAvailableBalanceExcep() == null || singleTO.getInsufficientAvailableBalanceExcep().equalsIgnoreCase("")) {
                    this.setInsufficientBal("");
                } else {
                    this.setInsufficientBal(singleTO.getInsufficientAvailableBalanceExcep());
                    descInsufficientBal();
                }
                if (singleTO.getAccountNameChangeExcep() == null || singleTO.getAccountNameChangeExcep().equalsIgnoreCase("")) {
                    this.setAcctNameChng("");
                } else {
                    this.setAcctNameChng(singleTO.getAccountNameChangeExcep());
                    descAcctNameChng();
                }
                if (singleTO.getStaleInstrumentExcep() == null || singleTO.getStaleInstrumentExcep().equalsIgnoreCase("")) {
                    this.setStaleInstrment("");
                } else {
                    this.setStaleInstrment(singleTO.getStaleInstrumentExcep());
                    descStaleInstrment();
                }
                if (singleTO.getAccountFrozenExcep() == null || singleTO.getAccountFrozenExcep().equalsIgnoreCase("")) {
                    this.setAcctFrozenExcep("");
                } else {
                    this.setAcctFrozenExcep(singleTO.getAccountFrozenExcep());
                    descAcctFrozenExcep();
                }
                if (singleTO.getCustomerIdMismatchDr() == null || singleTO.getCustomerIdMismatchDr().equalsIgnoreCase("")) {
                    this.setCustIdMismatchDr("");
                } else {
                    this.setCustIdMismatchDr(singleTO.getCustomerIdMismatchDr());
                }
                if (singleTO.getCustomerIdMismatchCr() == null || singleTO.getCustomerIdMismatchCr().equalsIgnoreCase("")) {
                    this.setCustIdMismatchcr("");
                } else {
                    this.setCustIdMismatchcr(singleTO.getCustomerIdMismatchCr());
                }
                if (singleTO.getBackDatedTransactionExcep() == null || singleTO.getBackDatedTransactionExcep().equalsIgnoreCase("")) {
                    this.setBackDatedTrn("");
                } else {
                    this.setBackDatedTrn(singleTO.getBackDatedTransactionExcep());
                }
                if (singleTO.getValueDateTransactionExcep() == null || singleTO.getValueDateTransactionExcep().equalsIgnoreCase("")) {
                    this.setValueDateTrn("");
                } else {
                    this.setValueDateTrn(singleTO.getValueDateTransactionExcep());
                }
                if (singleTO.getCashTransactionExcep() == null || singleTO.getCashTransactionExcep().equalsIgnoreCase("")) {
                    this.setCashTrn("");
                } else {
                    this.setCashTrn(singleTO.getCashTransactionExcep());
                }
                if (singleTO.getAccountOpenMatrixExcep() == null || singleTO.getAccountOpenMatrixExcep().equalsIgnoreCase("")) {
                    this.setAcctOpenMatrix("");
                } else {
                    this.setAcctOpenMatrix(singleTO.getAccountOpenMatrixExcep());
                }
                if (singleTO.getClearingTransactionExcep() == null || singleTO.getClearingTransactionExcep().equalsIgnoreCase("")) {
                    this.setClrTrnExcp("");
                } else {
                    this.setClrTrnExcp(singleTO.getClearingTransactionExcep());
                }
                if (singleTO.getTransferTransactionExcep() == null || singleTO.getTransferTransactionExcep().equalsIgnoreCase("")) {
                    this.setTransferTrn("");
                } else {
                    this.setTransferTrn(singleTO.getTransferTransactionExcep());
                }
                if (singleTO.getSanctionedLimitExpiredExcep() == null || singleTO.getSanctionedLimitExpiredExcep().equalsIgnoreCase("")) {
                    this.setSanctionedLmt("");
                } else {
                    this.setSanctionedLmt(singleTO.getSanctionedLimitExpiredExcep());
                }
                if (singleTO.getSanctionedExceedsLimitExcep() == null || singleTO.getSanctionedExceedsLimitExcep().equalsIgnoreCase("")) {
                    this.setSanctionedExceed("");
                } else {
                    this.setSanctionedExceed(singleTO.getSanctionedExceedsLimitExcep());
                }
                if (singleTO.getCustDrWithoutCheque() == null || singleTO.getCustDrWithoutCheque().equalsIgnoreCase("")) {
                    this.setCusrDrWithoutChq("");
                } else {
                    this.setCusrDrWithoutChq(singleTO.getCustDrWithoutCheque());
                }
                if (singleTO.getRefferedAccountClosure() == null || singleTO.getRefferedAccountClosure().equalsIgnoreCase("")) {
                    this.setRefferedAcctClosure("");
                } else {
                    this.setRefferedAcctClosure(singleTO.getRefferedAccountClosure());
                }
                if (singleTO.getUserTodGrantException() == null || singleTO.getUserTodGrantException().equalsIgnoreCase("")) {
                    this.setUserTodGrntExcp("");
                } else {
                    this.setUserTodGrntExcp(singleTO.getUserTodGrantException());
                }
                if (singleTO.getAutoTodGrantException() == null || singleTO.getAutoTodGrantException().equalsIgnoreCase("")) {
                    this.setAutoTodGrntExcp("");
                } else {
                    this.setAutoTodGrntExcp(singleTO.getAutoTodGrantException());
                }
                if (singleTO.getAccountInDrBalanceExp() == null || singleTO.getAccountInDrBalanceExp().equalsIgnoreCase("")) {
                    this.setAcctInDrBal("");
                } else {
                    this.setAcctInDrBal(singleTO.getAccountInDrBalanceExp());
                }
                if (singleTO.getAccountInCrBalanceExp() == null || singleTO.getAccountInCrBalanceExp().equalsIgnoreCase("")) {
                    this.setAcctInCrBal("");
                } else {
                    this.setAcctInCrBal(singleTO.getAccountInCrBalanceExp());
                }
                if (singleTO.getOverrideDfLtCheque() == null || singleTO.getOverrideDfLtCheque().equalsIgnoreCase("")) {
                    this.setOverrideDfltChq("");
                } else {
                    this.setOverrideDfltChq(singleTO.getOverrideDfLtCheque());
                }
                if (singleTO.getInvalidReportCodeExcep() == null || singleTO.getInvalidReportCodeExcep().equalsIgnoreCase("")) {
                    this.setInvalidReportCode("");
                } else {
                    this.setInvalidReportCode(singleTO.getInvalidReportCodeExcep());
                }
                if (singleTO.getMemoPadExists() == null || singleTO.getMemoPadExists().equalsIgnoreCase("")) {
                    this.setMamoPadExist("");
                } else {
                    this.setMamoPadExist(singleTO.getMemoPadExists());
                }
                if (singleTO.getDfltIntParmChngException() == null || singleTO.getDfltIntParmChngException().equalsIgnoreCase("")) {
                    this.setDfltIntParmExcp("");
                } else {
                    this.setDfltIntParmExcp(singleTO.getDfltIntParmChngException());
                }
                if (singleTO.getAcctBalBelowTheReqMin() == null || singleTO.getAcctBalBelowTheReqMin().equalsIgnoreCase("")) {
                    this.setAcctBalBelowmin("");
                } else {
                    this.setAcctBalBelowmin(singleTO.getAcctBalBelowTheReqMin());
                }
                if (singleTO.getMinBalPenalChrgNotCalc() == null || singleTO.getMinBalPenalChrgNotCalc().equalsIgnoreCase("")) {
                    this.setMinBalPenalChrg("");
                } else {
                    this.setMinBalPenalChrg(singleTO.getMinBalPenalChrgNotCalc());
                }
                if (singleTO.getIntForPastDueNotUpToDate() == null || singleTO.getIntForPastDueNotUpToDate().equalsIgnoreCase("")) {
                    this.setIntForPastDue("");
                } else {
                    this.setIntForPastDue(singleTO.getIntForPastDueNotUpToDate());
                }
                if (singleTO.getDrTranToPastDueAsset() == null || singleTO.getDrTranToPastDueAsset().equalsIgnoreCase("")) {
                    this.setDrTranToPast("");
                } else {
                    this.setDrTranToPast(singleTO.getDrTranToPastDueAsset());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshPmForm() {
        try {
            this.setSchemeDesc("");
            this.setNativeSchemeDesc("");
            this.setRptTransFlag("");
            this.setSchemeSupId("");
            this.setStmtFrqType("");
            this.setStmtFrqType("");
            this.setStmtFrqNum("");
            this.setStmtFrqDay("");
            this.setStmtFrqStartDt("");
            this.setStmtFrqNP("");
            this.setSchemeCrDrInd("");
            this.setTurnoverDetailFlag("");
            this.setSysgenAcctNoFlag("");
            this.setTurnoverFreqType("");
            this.setTurnoverFreqNo("");
            this.setTurnoverFreqDay("");
            this.setTurnoverFreqDate("");
            this.setTurnoverFreqNp("");
            this.setAcctPrefix("");
            this.setNxtNoTblCode("");
            this.setFcnrSchemeFlg("");
            this.setAcctCloserAcrossSols("");
            this.setSchemeFlag("");
            this.setOpt("");
            this.setFdCrAccountPlaceHolder("");
            this.setCommitCalMethd("");
            this.setFdDrAccountPlaceHolder("");
            this.setMinCommitUtln(new BigDecimal(0.0d));
            this.setStaffSchmeflg("");
            this.setDormantChargeMonth("");
            this.setDormantChargeDay("");
            this.setNrSchemeFlg("");
            this.setInactiveChrgmnth("");
            this.setInactiveChrgDay("");
            this.setNewAcctDur("");
            this.setProductGroup("");
            this.setMinPostWorkClass("");
            this.setLinkTranSale("");
            this.setMinAcctClosurePeriodMonth("");
            this.setMinAcctClosurePeriodDay("");
            this.setAcctMaintPeriod("");
            this.setDfltClgTransCode("");
            this.setDfltInsttype("");
            this.setTrnRefNo("");
            this.setPegIntForAcctFlg("");
            this.setModificationPegFlg("");
            this.setPegRevCust("");
            this.setAcctPartionAllowed("");
            this.setAutoRenewal("");
            this.setPdGlSubHead("");
            this.setStmtMsg("");
            this.setNativeStmtMsg("");
            this.setCretedBy("");
            this.setCreateDt("");
            this.setLastUpdateBy("");
            this.setLastUpdateDt("");
            this.setTotalModification("0");
            this.setLiabilityExceed("");
            this.setInsufficientBal("");
            this.setAcctNameChng("");
            this.setStaleInstrment("");
            this.setAcctFrozenExcep("");
            this.setCustIdMismatchDr("");
            this.setCustIdMismatchcr("");
            this.setBackDatedTrn("");
            this.setValueDateTrn("");
            this.setCashTrn("");
            this.setAcctOpenMatrix("");
            this.setClrTrnExcp("");
            this.setTransferTrn("");
            this.setSanctionedLmt("");
            this.setSanctionedExceed("");
            this.setCusrDrWithoutChq("");
            this.setRefferedAcctClosure("");
            this.setUserTodGrntExcp("");
            this.setAutoTodGrntExcp("");
            this.setAcctInDrBal("");
            this.setAcctInCrBal("");
            this.setOverrideDfltChq("");
            this.setInvalidReportCode("");
            this.setMamoPadExist("");
            this.setDfltIntParmExcp("");
            this.setAcctBalBelowmin("");
            this.setMinBalPenalChrg("");
            this.setIntForPastDue("");
            this.setDrTranToPast("");

            //Clear OutputText Fields

            this.setStliabilityExceed("");
            this.setStinsufficientBal("");

            this.setStacctNameChng("");

            this.setStstaleInstrment("");

            this.setStacctFrozenExcep("");

        } catch (Exception ex) {
            System.out.println("Exception occured in Refresh method.");
        }
    }

    public void enablePmForm() {
        this.pmFlag = false;
    }

    public void disablePmForm() {
        this.pmFlag = true;
    }
}
