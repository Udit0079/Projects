/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.dto.npci.cts.InstrumentsForTheSameVoucherGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.ChqGridDetails;
import com.cbs.pojo.OutwardCtsFileGrid;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface OutwardClearingManagementFacadeRemote {

    public String getCurrentDate(String brCode) throws ApplicationException;

    public String getEntryOpenDate(String emFlag, String brCode) throws ApplicationException;

    public String saveDataRegisterMaintenance(String emFlag, String entryDt, String postingDt,
            String clearingDt, String user, String brCode) throws ApplicationException;

    public String otherButton(String emFlag, String brCode) throws ApplicationException;

    public String yesButtonRegisterClose(String emFlag, String dtRegisterClose, String user,
            String brCode) throws ApplicationException;

    public String changeButtonRegisterMaintenance(String emFlag, String othClRegisterDt,
            String TabOther, String DtNewPosting, String DtOldClearing, String DtNewClearing,
            String DtOldPosting, String brCode) throws ApplicationException;

    public String onChangeOfOtherDateCalander(String emFlag, String brCode, String otherCalender)
            throws ApplicationException;

    public String onChangeOfOtherDateCalander1(String emFlag, String brCode, String otherCalender)
            throws ApplicationException;

    public List loadBankNames();

    public List loadAlphaCode(String orgBrnCode);

    public String instrDate(String orgBrnCode);

    public List getRegisterdate(String circleType, String orgBrnCode);

    public String cbsOutClearRegAccInfo(String accNo, String orgBrnCode, String acNature) throws ApplicationException;

    public List getUnverifyInstrument(String orgBrnCode, String tmpFlag, String regisDate);

    public String owInstDateValidate(String intrDate, String regisDate)
            throws ApplicationException;

    public List instrUpdtDelRegBankDetail(String bankCode1, String bankCode2, String bankCode3);

    public List loadBranchNames(String banknames);

    public List generateVoucherNo(String empFlag, String instrDate, String orgBrnCode);

    public String cbsOwChqDeposit(List<InstrumentsForTheSameVoucherGrid> arraylist, String acNo, String EnterBy, String emflag,
            String txndate, String obcflag, String TerminalId, String tot, String brCode)
            throws ApplicationException;

    public List getLockInfo(String emFlag, String entryDate, String orgBrnCode);

    public List getDescription(String emFlag);

    public List getDetailsOnPopup(String instrNo, String accNo, String emFlag, String vtot, String tableName);

    public String cbsOwEntryUpdate(String instrDate, String flag, String accNo, String txnAmt,
            String txnNo, String txnNoOld, String bnkAddr, String bnkName, String vt,
            String oldInstrDate, String oldAccNo, String brCode, String micrCode1Popup, String micrCode2Popup, String micrCode3Popup) throws ApplicationException;

    public String cbsInstrumentDateCheckPopup(String instrDate, String accNo, String oldInstrDate)
            throws ApplicationException;

    public String cbsOwEntryDelete(String EMPFLAG, String ACNO, String INSTNO, String VTOT,
            String AUTHBY, String brCode) throws ApplicationException;

    public List getDuplicateChqInfo(String accNo, String instrNo, String regisDate, String micr1, String micr2, String micr3, String EMPFLAG, String orgnbrcode);

    public String dateDiff(String acBrCode, String accNo, String orgnBrCode) throws ApplicationException;

    public String accStatus(String accNo, String orgnCode) throws ApplicationException;

    public List regDateCombo(String brCode, String emFlag) throws ApplicationException;

    public List terminalCombo(String brCode) throws ApplicationException;

    public List usersCombo(String brCode, String emFlag, String regDt) throws ApplicationException;

    public List loadInstrumentDetails(String brCode, String emFlag, String regDt, String user,
            String terminal, String status, String by, boolean userFlag, boolean terminalFlag,
            boolean statusFlag) throws ApplicationException;

    public List statusDetail(String brCode, String emFlag, String regDt) throws ApplicationException;

    public String verificationOfInstrument(String brCode, String emFlag, String vtot,
            String instNo, String authBy) throws ApplicationException;

    public String unverificationOfInstrument(String brCode, String emFlag, String vtot,
            String instNo) throws ApplicationException;

    public List glTableInstLoad(String brCode, String emFlag, String regDt, String user,
            String terminal, String status, String by, boolean userFlag, boolean terminalFlag,
            boolean statusFlag) throws ApplicationException;

    public List loadFDInstrumentDetails(String brCode, String emFlag, String regDt, String user,
            String terminal, String status, String by, boolean userFlag, boolean terminalFlag,
            boolean statusFlag) throws ApplicationException;

    public List loadReasonForReturnInBean() throws ApplicationException;

    public List loadPendingDate(String emFlag, String brCode) throws ApplicationException;

    public List loadCkqGridInBean(String emFlag, String pendingdate, String brCode) throws ApplicationException;

    public List getDetailsFromChqGrid1(String accNo, String instrNo, String brCode);

    public List getDetailsFromChqGrid2(String accNo, String instrNo, String brCode);

    public String clickOnClearButtonInBean(String brnCode);

    public List checkStatus(String entryDate, String emFlag, String brCode) throws ApplicationException;

    public String coverOfClearButton(String emFlag, String txnDate, String authBy, String accNo,
            String amount, String instrNo, String orgBrnCode, String auth1, String name) throws ApplicationException;

    public String returnClickResult(String selectclearModeOption, String acountNo, String instrDate,
            String instrNo, double instrAmt, String userName, String orgBrnCode,
            String reasonForReturnOption, String loadPendingDateListOption, String enterBy) throws ApplicationException;

    public String coverOfAllClearButton(String emFlag, String txnDate, String authBy, String orgBrnCode,
            String auth1, List<ChqGridDetails> chqGridDetails) throws ApplicationException;

    public String instrDateCheck(String intrDate, String accNo) throws ApplicationException;

    public String checkClgDt(String emFlag, String registerDts1, String brCode) throws ApplicationException;

    public String getCityCodeAsMiccr(String brCode) throws ApplicationException;

    // Added by Manish kumar
    public List getBranchList() throws ApplicationException;

    public String generateOutwardCtsFile(String path, String branchCode, String txnDate, String generatedByBrnCode, String fileGeneratedBy) throws ApplicationException;

    public List<OutwardCtsFileGrid> showOutwardCtsFile(String branchCode, String generatedDate, String generatedBy) throws ApplicationException;

    public String generateOutwardTxtFile(String path, String branchCode, String txnDate, String generatedByBrnCode,
            String fileGeneratedBy, String emFlag) throws ApplicationException;
    
    public String generateOutwardCtsFileKcbl(String path, String branchCode, String txnDate, String generatedByBrnCode, String fileGeneratedBy) throws ApplicationException;

    public String generateclringOutwardTxtFile(String path, String branchCode, String txnDate, String genrateByBrnCode, String generatedBy, String emFlag) throws ApplicationException;
}
