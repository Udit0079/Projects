/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.dto.report.OwClgPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface OtherTransactionManagementFacadeRemote {

    public String getSignatureDetails(String acno);

    public List getReasonForCancel() throws ApplicationException;

    public List getBankNameTableData(String registerCboDate, String brCode, String hOpt) throws ApplicationException;

    public List getClearingOption();

    public String loadDate(String emFlag, String brCode) throws ApplicationException;

    public List getBankName() throws ApplicationException;

    public String registerCboDateLostFocus(
            String emFlag, String registerCboDate, String registerCboDate1, String brCode) throws ApplicationException;

    public List getTableData(String emFlag, String registerCboDate, String registerCboDate1, String brCode) throws ApplicationException;

    public String status() throws ApplicationException;

    public String gridbankDblClick(String emFlags, String registerCboDate, String gridValue, String brCode, String hOpt)
            throws ApplicationException;

    public String cmbBankNameValueChangeClearance(String emFlags, String registerCboDate, String bankName, String brCode, String hOpt)
            throws ApplicationException;

    public String cboReasonKeyDown(String acno, String instNo, float seqNo, String bankName, String instDt, String brCode)
            throws ApplicationException;

    public String getTableDataReasonForReturn(String emFlag, String acno, String instNo, String instDate, String bankName,
            String cboReason, float seqNo, float instAmount, int txtYear, String brCode, int vtot)
            throws ApplicationException;

    public String grdTxnDblClick(
            String grdTxnInstNo, String grdTxnInstDt, String grdtxnAcno, int grdTxnVtot, float grdTxnAmount, float txtSeqNo, String brCode)
            throws ApplicationException;

    public List getTxnTableData(String emFlag, String registerCboDate, String brCode) throws ApplicationException;

    public String clgOutward3day(String AuthBy, String txnDate, String emFlag, String brCode) throws ApplicationException;

    public String maskEdBox1KeyDown(String txnInstNo, String txnInstDate, String emFlag, String cboDate, String Tmpac, String brCode)
            throws ApplicationException;

    public List getCurrentTableData(String emFlag, String registerCboDate, String txnInstNo, String txnInstDt, String txnInstAmt, String brCode)
            throws ApplicationException;

    public List getClearingMode();

    public String clearingModeLostFocus(String emFlag, String brCode) throws ApplicationException;

    public String checkPostingDt(String emFlag, String registerDts1, String brCode) throws ApplicationException;

    public String clgOutward2day(String AuthBy, String txnDate, String emFlag, String brCode) throws ApplicationException;

    public String insertTransfer(String acctNature, String acno, int ty, Double amt, String dt, int TranType, String details, String enterby,
            float trsNo, String trantime, float recNo, String auth, String authby, int trandesc, int payby, String instrNo, float TokenNo,
            String tokenPaidBy, String SubTokenNo, int iy, String tdacno, Float voucherno, String intflag, String closeflag, String org_brnid,
            String dest_brnid, int tran_id, String term_id, String brCode, String valueDt) throws ApplicationException;

    public String reasonNotAppForCharge(int code) throws ApplicationException;

    public List<String> loadRegisterDate(String clgMode, String brCode) throws Exception;

    public List<OwClgPojo> getOwNpciReturnData(String txnDt, String clgMode, String brCode, String status) throws Exception;
}
