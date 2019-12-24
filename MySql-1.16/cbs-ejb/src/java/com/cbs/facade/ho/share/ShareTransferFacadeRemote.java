/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.share;

import com.cbs.dto.memberRefMappingPojo;
import com.cbs.dto.report.AreaWiseSharePojo;
import com.cbs.dto.report.MemberReferenceMappingPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AccGenInfo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface ShareTransferFacadeRemote {

    public List transferorShareHolderName(String folioNo) throws ApplicationException;

    public List gettransfereeCertNo() throws ApplicationException;

    public List getTableData(String folioNo) throws ApplicationException;

    public List getShareNum(String folioNo, double certNo) throws ApplicationException;

    public List checkAuthorization(double certNo) throws ApplicationException;

    public String shareTransferSaveUpdation(String oldFolioNo, int oldCertNo, int lStart, int lEnd, String newFolioNo, int trfCert,
            int txtTrfCertNo, String maskDt, String userName, String dt, String cmbTranCertNo, String transferorName, String TransfereeName, int noOfshareToBeTransferred, String remarks, String lPartial) throws ApplicationException;

    public List noOfShare() throws ApplicationException;

    public String saveShareIssueAction(long shareIssueFrom, int noOfshareIssue, String user, String issueDt, String dt, String orgnBrCode) throws ApplicationException;

    public List folioDetail(String folioNo) throws ApplicationException;
    
    public String saveUpdateAction(String area, String folioNo) throws ApplicationException;
    
    public String saveDesignation(String designation,String name,String refID,String enteredBy, String enteredDt) throws ApplicationException;
    
    public String saveReferedByDetail(String folioNo,String referBy,String designation,String enteredBy,String enteredDt) throws ApplicationException;
    
     public List<MemberReferenceMappingPojo> getReferenceDetails(String designation,String referBy, String dt)throws ApplicationException;

    //public void onloadAction() throws ApplicationException;
    public List categoryDetail() throws ApplicationException;

    public List CategoryfolioDetail(String folioNo) throws ApplicationException;

    public String CategorySaveUpdateAction(String mode, String str2, String str1) throws ApplicationException;

    public String deleteCategoryMaintenanceAction(String folioNo) throws ApplicationException;

    //public void CategoryOnloadAction() throws ApplicationException;
    public List CategoryMaintenanceDetail() throws ApplicationException;

    public String saveCategoryMaintenanceAction(String category, String enterby) throws ApplicationException;

    public String CategoryMaintenanceDeleteAction(String category) throws ApplicationException;

    //public void CategoryMaintenanceOnloadAction() throws ApplicationException;
    public List getAlphaCode() throws ApplicationException;

    public List getOperatingMode() throws ApplicationException;

    public List getPurposeList() throws ApplicationException;

    public List getAcctType() throws ApplicationException;

    public List categoryList() throws ApplicationException;

    public List getshareAmount(String dt) throws ApplicationException;

    public List editDetail(String folioNo) throws ApplicationException;

    public String saveShareValue(double shareValue, String effectiveDt) throws ApplicationException;

    public String getLevelId(String userId, String brCode) throws ApplicationException;

    public List getUnAuthAccountList() throws ApplicationException;

    public String saveAccountOpen(List <AccGenInfo> acOpenList,String mode, String custId, String folioNo, String dividentPayable, String registerDt,
            String wtFName, String wtFAddr, String wtSName, String wtSAdds, String nomineeName, String nomineeAdd, String nomineeAge,
            String nomineeRelation, String remark, String operationMode, String jtId1, String jtId2, String userName, String purpose,
            String businessDesig, String firmHolderName, String category, String date, String orgnBrCode, String jtName1, String jtName2, String jDt, 
            String Sal, String gPay, String area, String benefAcNo, String benefName, String ifscCode, String bnkName,
            String panNo, String nomineeDob, String occupation, String docName, String docDetail, String introAcNo, String actCateg,String alphaCode) throws ApplicationException;

    public List areaList() throws ApplicationException;

    public List<AreaWiseSharePojo> getDefaulterShareData(String status, String area, String date) throws ApplicationException;

    public String getCustName(String acNo) throws ApplicationException;
    
    public List getBankNameList() throws ApplicationException;
      
    public List checkAcno(String brnCode,String acctType) throws ApplicationException;
    
    public List getFolioAcnoInfo(String folioNo, String function) throws ApplicationException;	
    
    public String getcustIdByFolioNo(String folioNo) throws ApplicationException ; 
    
    public String saveBasicIncrementAmtAction(String area,String applicableDt,String basicIncrement,String userName) throws ApplicationException;

    public List getMaxMemberRefNo(String designation) throws ApplicationException;

    public String isRefAlreadyUsed(String desgination, String refNo) throws ApplicationException;

    public List getAllDetailOfDesignationBases(String designation,String Date) throws ApplicationException;

    public List<MemberReferenceMappingPojo> getReferenceDetailMembershipWise(String memberShipNo) throws ApplicationException;

    public List getMembershipRecordIndividually(String memberShipNo) throws ApplicationException;

    public String deleteMappingDesignation(String designationCode, String personrefId) throws ApplicationException;
    
}