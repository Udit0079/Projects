/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.MsgPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface BankProcessManagementFacadeRemote {

    public List getPath() throws ApplicationException;

    public String execProc(int code) throws ApplicationException;

    String getInitialInfo(String todaydate) throws ApplicationException;
    
    public boolean isHolidayDate(String date) throws ApplicationException;
    
    public String maxWorkingDate(String date) throws ApplicationException;

    List<MsgPojo> dayBeginProcess(String todaydate, String userName) throws ApplicationException;

    String dayEndProcess(String todaydate, String userName, String orgnCode,
            String directory, String ckycrTempImageLocation, String ctsArchievalImgLocation) throws ApplicationException;

    public String createFile(String date, String brCode, String enterBy) throws ApplicationException;

    public List getAccDetail(String acNo) throws ApplicationException;

    public String valAccDetail(String acNo) throws ApplicationException;

    public String canPassBook(String acNo, String Reason) throws ApplicationException;

    public String passBookIss(String acNo) throws ApplicationException;

    public List getAccFstPrnDtl(String acNo) throws ApplicationException;

    public String valAccFstPrnDtl(String acNo) throws ApplicationException;

    public List getIssuedDetail(String acNo) throws ApplicationException;

    public List getPassBookValues() throws ApplicationException;

    public List setPassBookValues(String accNo) throws ApplicationException;

    public List selectParameterValue(String accNo) throws ApplicationException;

    public List selectFromPassbookRecon(String accNo) throws ApplicationException;

    public List checkPrint2(String accNo) throws ApplicationException;

    public String selectAcnoCramtDramtDt(String accNo, String tableName, String dateFrom, double recmax) throws ApplicationException;

    public List selectFromPassbookPrt(int prow, String accNo) throws ApplicationException;

    public List selectFromPassbookValuesCA(String accNo) throws ApplicationException;

    public List selectFromPassbookValues(String accNo) throws ApplicationException;

    public List selectFromPassbookHis(String accNo) throws ApplicationException;

    public List selectFromPassRecon(String accNo) throws ApplicationException;

    public List selectFromCustmasterAccMasterParameterInfo(String accNo) throws ApplicationException;

    public List selectDescription6(String occupation) throws ApplicationException;

    public List selectDescription4(String OPERMODE) throws ApplicationException;

    public List selectFromTable(String accNo, String issueDate, String tableName) throws ApplicationException;

    public List getDetailsOnLoad1() throws ApplicationException;

    public List getDetailsOnLoad2() throws ApplicationException;

    public String updatePassbookRecon(String accNo) throws ApplicationException;

    public String deleteFromPassbookPrint(String accNo) throws ApplicationException;

    public List selectForPassUpdate(int prow, String accNo) throws ApplicationException;

    public String updatepassbookRecon2(String balance, long totalPrint, String accNo) throws ApplicationException;

    public String deleteInsertPassbookrecon(String tableName, String accNo, double maxrecno, String maxdt1) throws ApplicationException;

    public String insertIntoPassbookRecon(String accNo, String bal, String lnum, String recNo, String issueDate, String passBookNo, String sNo, String Tempbd) throws ApplicationException;

    public List selectWidthPassbookValues(String accNo) throws ApplicationException;

    public List getMaxTxnDate(String accNo);

    public List getRemainingDataToPrint(String accNo);

    public String yearEndProcess() throws ApplicationException;

    public List getFinYear() throws ApplicationException;

    public String getIfscCodeByBrnCode(int brncode) throws ApplicationException;

    public String getAcctDescByAcctCode(String acctcode) throws ApplicationException;

    public String getoldAccountNumber(String newAcno) throws ApplicationException;

    public String getPassCPABalValues() throws ApplicationException;

    public String insertFortnightBal(String enterBy, String dt, String toDt, String brnCode) throws ApplicationException;

    public String insertFortnightBalAsPerGLB(String enterBy, String dt, String toDt, String brnCode) throws ApplicationException;

    public String entryAsOnDateBalanceList(String brCode, String date) throws ApplicationException;

    public String entryBetweenDateBalanceList(String brCode, String fromdate, String Todate) throws ApplicationException;

    public String cashInHandEntryBetweenDateBalanceList(String brCode, String fromdate, String Todate) throws ApplicationException;

    public List getAccountDetails(String acctNature, String acno) throws ApplicationException;
    
    public String getBankGstinByBrnCode(int brncode) throws ApplicationException;

    public List getAtmTransactionDataForMarking(String date) throws ApplicationException;

    public String holidayMarkingProcess(List resultList,String user,String date,String frDate) throws ApplicationException;

    public String holidayUnmarkingProcess(String user, String date) throws ApplicationException;
}
