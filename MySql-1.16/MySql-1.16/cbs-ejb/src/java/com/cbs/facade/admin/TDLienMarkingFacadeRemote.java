/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.facade.admin;

import com.cbs.dto.AcTransferAuthPojo;
import com.cbs.exception.ApplicationException;
import java.io.File;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Admin
 */
@Remote
public interface TDLienMarkingFacadeRemote {
    List acctTypeCombo()throws ApplicationException;
    
    String dlAcOpen()throws ApplicationException;
    
    String auth(String userName, String brCode)throws ApplicationException;
    
    List customerDetail(String acno)throws ApplicationException;
    
    List gridDetailLoad(String acno, String acType)throws ApplicationException;
    
    String tdLienPresentAmount(String acno, Float voucherno, Float prinamount)throws ApplicationException;
    
    String saveLienMarkingDetail(Float ReceiptNo, Float VchNo, String Actype, String AcNO, String LAcNO, String chkLien, String AUTH, String enteredby, String remark, String Loan_Lien_Call, String tmpSecType, String DLAccOpen_Lien, String BillLcBg_Lien, String brnCode)throws ApplicationException;

    public java.lang.String saveSingleImg(File fileImg, java.lang.String Enterby) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveImages(String[] children, java.lang.String dirName, java.lang.String Enterby) throws com.cbs.exception.ApplicationException;

    public java.util.List getAlphacodes() throws com.cbs.exception.ApplicationException;

    public java.lang.String transferAccount(java.lang.String acno, java.lang.String originBranch, java.lang.String respondingBranch, java.lang.String enterBy) throws com.cbs.exception.ApplicationException;

    public java.lang.String getAlphacodeByBranchCode(java.lang.String brncode) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.AcTransferAuthPojo> getUnAuthTrfList(java.lang.String brncode) throws com.cbs.exception.ApplicationException;

    public java.lang.String checkAcNoForTransfer(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public String authorizeAcTransfer(AcTransferAuthPojo acTrfList, String format, String userName, String orgnBrCode)throws ApplicationException;
    
    public List getDocumentType(String custId,String classification)throws ApplicationException;
    
    public String saveCkycImage(String custId, String name, String Enterby) throws ApplicationException;
    
    public String checkPrimaryBrCode(String custId, String brCode) throws ApplicationException;
}
