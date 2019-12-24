/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.dto.ChBookAuthorization;
import com.cbs.dto.NpciFileDto;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.ChbookDetailPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface OtherAuthorizationManagementFacadeRemote {

    public List<String> returnAuthStatus(String tempBd, String orgBrnCode) throws ApplicationException;

    public List selectFromBankDays(String orgBrnCode);

    public List ibcObcOnLoad(String billType, String brCode) throws ApplicationException;

    public List setPartyName(String billType, String acno) throws ApplicationException;

    public String ibcObcEntryDelete(String user, String bills, String bilType, String acno, String instno,
            double instamount, String instdt, String enterby, String auth, String bankname,
            String bankaddr, String coll_bankname, String coll_bankaddr, String brCode) throws ApplicationException;

    public String ibcObcAuth(String BRCODE, String user, String enterby, String bills, String bilType,
            String acno, String instno, double instamount, String Year, String instrDt) throws ApplicationException;

    public List chBkTable(String orgnBrCode, String todayDate, String authType) throws ApplicationException;

    public String authCheckIssue(ChBookAuthorization item, String userName) throws ApplicationException;

    public List grid(String BranchCode) throws ApplicationException;

    public List getSelectedValue(String instcode, int sno, String BranchCode) throws ApplicationException;

    public String authDeleteData(String InstCode, int SlabCode, float AmtFrom, float AmtTo,
            String IssueDt, int numfrom, int numto, int Leaves, String printlot, String IssueBy,
            String option, String authUser, int sno, String BranchCode) throws ApplicationException;

    public List gridLoad(String brCode, String curDt) throws ApplicationException;

    public String authorizeAction(ChbookDetailPojo obj, String enterBy, String brcode) throws ApplicationException;

    public List getUnauthorizedAcctNo(String orgBrnCode, String imgType) throws ApplicationException;

    public List getDetailsOfScannedPerson(String accountNo) throws ApplicationException;

    public String signAuthorization(String accountNo, String serialNo, String userName) throws ApplicationException;

    public String getSignatureDetails(String acno);
    
    public String getMergSignatureDetails(String acno);

    public String deleteUnauthSign(String newAccNo, String deletedBy) throws ApplicationException;
    
    public String validateBothAccountNo(String oldAccNo , String newAccNo) throws ApplicationException;
    
    public String generateChBookPrintingFile(String instType, String fileName, String dt, String orgBrCode ,String userName) throws ApplicationException;
    
    public List<NpciFileDto> showGeneratedFiles(String instType,String fileName, String dt, String brCode) throws ApplicationException;
    
    public List<String> getGeneratedFilesName(String instType, String dt, String brCode) throws ApplicationException;
    
    public List getCustomerDetails(String custId, String imgCode)throws ApplicationException;
    
    public String ckycImgAuthorization(String accountNo, String serialNo, String imgCode, String userName) throws ApplicationException;
    
    public List getCustImgeDetails(String imgType, String custId)throws ApplicationException;
    
    public String deleteUnauthCkycImg(String newAccNo, String deletedBy, String imgCode) throws ApplicationException;
}
