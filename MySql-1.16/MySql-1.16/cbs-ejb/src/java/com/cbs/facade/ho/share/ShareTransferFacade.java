/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.share;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.DlAcctOpenReg;
import com.cbs.dto.memberRefMappingPojo;
import com.cbs.dto.report.AreaWiseSharePojo;
import com.cbs.dto.report.MemberReferenceMappingPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.FdDdsAccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AccGenInfo;
import com.cbs.utils.CbsUtil;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "ShareTransferFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ShareTransferFacade implements ShareTransferFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdTwo = new SimpleDateFormat("yyyy-MM-dd");
    @EJB
    private AccountOpeningFacadeRemote acOpenFacadeRemote;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    FdDdsAccountOpeningFacadeRemote fdDdsAccountOpeningFacadeRemote;
    @EJB
    CommonReportMethodsRemote commom;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPostFacade;
    @EJB
    AdvancesInformationTrackingRemote advanceRemote;

    public List transferorShareHolderName(String folioNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select cc.custname From share_holder sh, cbs_customer_master_detail cc  Where sh.regFolioNo='" + folioNo + "' and cc.customerid = sh.custid");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List gettransfereeCertNo() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select ifnull(max(cast(certificateno as unsigned)),0) + 1 From certificate_share");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getTableData(String folioNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select s.ShareCertNo as certNo,s.ShareNo as shareNo,ifnull(s.dt,'') as issuedt,ifnull(c.issuedBy,'') as issuedBy,"
                    + "c.AdviceNo as AdviceNo,c.PoNo as PoNo,c.PaymentDt as PaymentDt,c.CertIssueDt as CertIssueDt,c.remark as remark,Status= case c.status "
                    + "When 'A' then 'ACTIVE' When 'C' then 'INACTIVE'Else 'OTHERS' End From share_capital_issue s left join certificate_share c on "
                    + "s.shareCertNo=c.certificateno Where s.foliono='" + folioNo + "'and c.status='A'Order By s.shareCertNo,s.shareNo asc");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getShareNum(String folioNo, double certNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select shareno from share_capital_issue where foliono='" + folioNo + "' and sharecertno= " + certNo + " and sharecertno in (select certificateno from certificate_share where status='A')");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List checkAuthorization(double certNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select * from certificate_share where certificateno=" + certNo + " and auth='N'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String shareTransferSaveUpdation(String oldFolioNo, int oldCertNo, int lStart, int lEnd, String newFolioNo, int trfCert,
            int txtTrfCertNo, String maskDt, String userName, String dt, String cmbTranCertNo, String transferorName, String TransfereeName, int noOfshareToBeTransferred, String remarks, String lPartial) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = null;
            double newCertNo;
            int generateCertNo = 0;
            String msg = "";
            if (lPartial.equalsIgnoreCase("false")) {
                if (trfCert == 0) {
                    newCertNo = oldCertNo;
                } else {
                    newCertNo = trfCert;
                }
            } else {
                newCertNo = trfCert;
                generateCertNo = txtTrfCertNo;
                msg = "Generated Certificate No For Transferee :- " + newCertNo;
            }
            Query ShareCapitalissuehis = em.createNativeQuery("insert into share_capital_issue_his(ShareNo,FolioNo,ShareCertNo,IssueDt,IssuedBy,fromdt,todt,LastUpdateTime,LastUpdateBy,Auth,AuthBy) select shareNo,FolioNo,ShareCertNo, IssueDt,IssuedBy,dt,'" + maskDt + "',now(),'" + userName + "', 'N',null from share_capital_issue where foliono='" + oldFolioNo + "' and  sharecertno=" + oldCertNo + " and shareno  between " + lStart + " and " + lEnd + "");
            Integer insertinfo = ShareCapitalissuehis.executeUpdate();
            if (insertinfo <= 0) {
                ut.rollback();
                result = "Data not saved,some problem in Transaction";
                return result;
            }
            Query sharecapitalissue = em.createNativeQuery("update share_capital_issue set foliono='" + newFolioNo + "',sharecertno=" + newCertNo + " ,lastupdatetime='" + dt + "', lastupdateby='" + userName + "',dt='" + maskDt + "' where foliono='" + oldFolioNo + "' and sharecertno=" + oldCertNo + " and shareno between " + lStart + " and " + lEnd + "");
            Integer updateparameterInfo = sharecapitalissue.executeUpdate();
            if (updateparameterInfo <= 0) {
                ut.rollback();
                result = "Data not saved,some problem in Transaction";
                return result;
            }
            if (cmbTranCertNo.equalsIgnoreCase("New Certificate") || lPartial.equalsIgnoreCase("false")) {

                Query ShareCapitalIssuehis = em.createNativeQuery("insert into share_capital_issue_his (ShareNo,FolioNo,ShareCertNo,IssueDt,IssuedBy,fromdt,todt,LastUpdateTime,LastUpdateBy,Auth,AuthBy) select shareNo,FolioNo,ShareCertNo,IssueDt,IssuedBy,dt,'" + maskDt + "',now(),'" + userName + "','N',null from share_capital_issue where foliono='" + oldFolioNo + "' and sharecertno=" + oldCertNo + "");
                Integer insertInfo = ShareCapitalIssuehis.executeUpdate();

            }
            if (generateCertNo != 0) {
                msg = msg + "Generated Certificate No for Transferor:- " + generateCertNo;
            }
            Query sharecapital = em.createNativeQuery("Update share_capital_issue set shareCertNo=" + generateCertNo + ",lastupdatetime='" + dt + "', lastupdateby='" + userName + "' where foliono='" + oldFolioNo + "' and sharecertno=" + oldCertNo + "");
            Integer capital = sharecapital.executeUpdate();
            if (cmbTranCertNo.equalsIgnoreCase("New Certificate") || lPartial.equalsIgnoreCase("false")) {
                Query certificateShare = em.createNativeQuery("Update certificate_share set status='C',lastupdateby='" + userName + "',lastupdatedt='" + maskDt + "' where certificateno=" + oldCertNo + " and status='A'");
                Integer certificate = certificateShare.executeUpdate();
                if (certificate <= 0) {
                    ut.rollback();
                    result = "Data not saved,some problem in Transaction";
                    return result;
                }
            }
            //Query certShare = em.createNativeQuery("if not exists (select certificateno from certificate_share where certificateno=" + newCertNo + ") insert into certificate_share(certificateno,issuedt,issuedby,Auth,AuthBy)  values(" + newCertNo + ",'" + maskDt + "', '" + userName + "','N','')");
            List certList = em.createNativeQuery("select certificateno from certificate_share where certificateno=" + newCertNo + "").getResultList();
            if (certList.isEmpty()) {
                Query certShare = em.createNativeQuery("insert into certificate_share(certificateno,issuedt,issuedby,Auth,AuthBy)  values(" + newCertNo + ",'" + maskDt + "', '" + userName + "','N','')");
                Integer share = certShare.executeUpdate();
                if (share <= 0) {
                    ut.rollback();
                    result = "Data not saved,some problem in Transaction";
                    return result;
                }
            }

            if (cmbTranCertNo.equalsIgnoreCase("New Certificate") && lPartial.equalsIgnoreCase("true")) {
                //Query certShare1 = em.createNativeQuery("if not exists (select certificateno from certificate_share where certificateno=" + generateCertNo + ") Insert into certificate_share(certificateno,issuedt,issuedby,Auth,AuthBY) Values(" + generateCertNo + ",'" + maskDt + "','" + userName + "','N','')");
                List certShareList = em.createNativeQuery("select certificateno from certificate_share where certificateno=" + generateCertNo + "").getResultList();
                if (certShareList.isEmpty()) {
                    Query certShare1 = em.createNativeQuery("Insert into certificate_share(certificateno,issuedt,issuedby,Auth,AuthBY) Values(" + generateCertNo + ",'" + maskDt + "','" + userName + "','N','')");
                    Integer share1 = certShare1.executeUpdate();
                    if (share1 <= 0) {
                        ut.rollback();
                        result = "Data not saved,some problem in Transaction";
                        return result;
                    }
                }
            }
            Query shareTransfer = em.createNativeQuery("insert into share_transfer (trfDt,certNo,trfFno,trfeFno,trfrName,trfeName,minshare,maxshare,noofshare,EnterBy,Auth,AuthBy,remark) values('" + maskDt + "'," + oldCertNo + ",'" + oldFolioNo + "','" + newFolioNo + "','" + transferorName + "','" + TransfereeName + "'," + lStart + "," + lEnd + ", " + noOfshareToBeTransferred + ",'" + userName + "','N','','" + remarks + "' )");
            Integer transfer = shareTransfer.executeUpdate();
            if (transfer <= 0) {
                ut.rollback();
                result = "Data not saved,some problem in Transaction";
                return result;
            }

            result = "Data Saved Successfully" + msg;
            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    /**
     * ***************************** share Issue *****************
     */
    public List noOfShare() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select ifnull(max(cast(shareNo as unsigned)),0) + 1 From share_capital_issue");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String saveShareIssueAction(long shareIssueFrom, int noOfshareIssue, String user, String issueDt, String dt, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            long finishshare = shareIssueFrom + noOfshareIssue - 1;
            long txnNo = 1l;
            List shareList = em.createNativeQuery("select max(txnNo) from share_capital_issue").getResultList();
            if (shareList.size() > 0) {
                Vector vec = (Vector) shareList.get(0);
                if (vec.get(0) != null) {
                    txnNo = Long.parseLong(vec.get(0).toString()) + 1;
                }
            }
            shareList = em.createNativeQuery("select * from share_capital_issue where shareno between " + shareIssueFrom + " and " + finishshare + " order by shareno ").getResultList();
            if (shareList.size() > 0) {
                ut.rollback();
                return "Share Has Already been Issued";
            } else {
                while (noOfshareIssue > 0) {
                    String folioNum = orgnBrCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001";
                    int saveData = em.createNativeQuery("insert into share_capital_issue (ShareNo,IssuedBy,LastUpdateBy,dt,issuedt,FolioNo,txnNo)"
                            + " values(" + shareIssueFrom + ",'" + user + "','" + user + "','" + dt + "','" + issueDt + "','" + folioNum + "'," + txnNo + ")").executeUpdate();

                    shareIssueFrom = shareIssueFrom + 1;
                    noOfshareIssue = noOfshareIssue - 1;
                    if (saveData <= 0) {
                        throw new ApplicationException("Record Not Saved");
                    }
                }
                ut.commit();
                return "Record has been successfully saved";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    /**
     * ********************************** End ************************
     */
    public List folioDetail(String folioNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select sh.Regfoliono,cc.custname,cc.fathername,sh.sector,sh.city,sh.area from share_holder sh, cbs_customer_master_detail cc  where sh.regfoliono= '" + folioNo + "' and cc.customerid = sh.custid ");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String saveUpdateAction(String area, String folioNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer updateparameter = em.createNativeQuery("UPDATE share_holder SET AREA='" + area + "' WHERE REGFOLIONO='" + folioNo + "'").executeUpdate();
            if (updateparameter <= 0) {
                ut.rollback();
                return "Data Not Updated";
            } else {
                ut.commit();
                return "true";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveDesignation(String designation, String name, String refID, String enteredBy, String enteredDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertParameter = em.createNativeQuery("insert into member_Designation_mapping(DesignationCode,PersonRefNo,PersonName,EnterBy,EnteredDate) values('" + designation + "',"
                    + "'" + refID + "','" + name + "','" + enteredBy + "','" + ymd.format(dmy.parse(enteredDt)) + "')").executeUpdate();
            if (insertParameter <= 0) {
                ut.rollback();
                return "Data Not Saved";
            } else {
                ut.commit();
                return "true";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                return "Data already exists";
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveReferedByDetail(String folioNo, String referBy, String designation, String enteredBy, String enteredDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertParameter = em.createNativeQuery("insert into member_reference_mapping(MembershipNo,ReferBy,DesignationCode,EnterBy,EnteredDate)"
                    + "values('" + folioNo + "','" + referBy + "','" + designation + "','" + enteredBy + "','" + enteredDt + "')").executeUpdate();
            if (insertParameter <= 0) {
                ut.rollback();
                return "Data Not Saved";
            } else {
                ut.commit();
                return "true";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                return "Data already exists";
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List getMembershipRecordIndividually(String memberShipNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select MembershipNo,DesignationCode,ReferBy from member_reference_mapping "
                    + "where MembershipNo='" + memberShipNo + "' and "
                    + "TxnId=(select max(TxnId) from member_reference_mapping where MembershipNo ='" + memberShipNo + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<MemberReferenceMappingPojo> getReferenceDetailMembershipWise(String memberShipNo) throws ApplicationException {
        List<MemberReferenceMappingPojo> resultList = new ArrayList();
        try {
            List result = em.createNativeQuery("select re.ORDER_BY, re.DesignationCode, re.UnderDes, re.PersonRefNo, re.ReferBy, "
                    + "mem.DesignationCode, mem.ReferBy, mem.MembershipNo, mem.memName, mem.memAdd, mem.memMobNo from  "
                    + "(select aa.DesignationCode, aa.ReferBy, aa.MembershipNo, aa.memName,concat(aa.MailAddressLine1, "
                    + "aa.MailAddressLine2, aa.MailVillage, aa.MailBlock,if(ifnull(bb.mailCity,'')='','',concat(trim(bb.mailCity),' ')) ,"
                    + "if(ifnull(cc.mailState,'')='','',concat(trim(cc.mailState),' ')),aa.MailPostalCode) as memAdd, aa.memMobNo  from "
                    + "(select c.DesignationCode, c.ReferBy, c.MembershipNo, concat(trim(d.CustFullName), ' [',c.MembershipNo,']') as memName,"
                    + "if(ifnull(d.MailAddressLine1,'')='','',concat(trim(d.MailAddressLine1),' ')) as MailAddressLine1,"
                    + "if(ifnull(d.MailAddressLine2,'')='','',concat(trim(d.MailAddressLine2),' ')) as MailAddressLine2,"
                    + "if(ifnull(d.MailVillage,'')='','',concat(trim(d.MailVillage),' ')) as MailVillage,if(ifnull(d.MailBlock,'')='','',"
                    + "concat(trim(d.MailBlock),' ')) as MailBlock,if(ifnull(d.mailCityCode,'')='','',d.mailCityCode) as mailCityCode,"
                    + "if(ifnull(d.MailStateCode,'')='','',d.MailStateCode) as MailStateCode,if(ifnull(d.MailPostalCode,'')='','',"
                    + "d.MailPostalCode) as MailPostalCode,ifnull(d.mobilenumber, ifnull(d.TelexNumber, ifnull(d.MailPhoneNumber, "
                    + "ifnull(d.MailTelexNumber, ifnull(d.MailFaxNumber, ''))))) as memMobNo from member_reference_mapping c, "
                    + "cbs_customer_master_detail d, share_holder e where d.customerid = e.custId  and e.Regfoliono = '" + memberShipNo + "' "
                    + "and e.Regfoliono = c.MembershipNo) aa  left join (select ref_code, ref_desc as mailCity from cbs_ref_rec_type "
                    + "where ref_rec_no = 001 ) bb on bb.REF_CODE = aa.mailCityCode left join (select ref_code, ref_desc as MailState "
                    + "from cbs_ref_rec_type where ref_rec_no = 002 ) cc on cc.ref_code = aa.MailStateCode ) mem,(select a.ORDER_BY,"
                    + " b.DesignationCode, a.REF_DESC as UnderDes, b.PersonRefNo, concat( '[',b.PersonRefNo,'] ', trim(b.PersonName)) as ReferBy "
                    + "from cbs_ref_rec_type a, member_Designation_mapping b  where a.REF_CODE = b.DesignationCode  and a.ref_rec_no = '416' "
                    + "order by a.ORDER_BY, b.PersonRefNo) re  where mem.DesignationCode = re.DesignationCode and mem.ReferBy = re.PersonRefNo  "
                    + "order by ORDER_BY, re.PersonRefNo").getResultList();

            if (result.isEmpty()) {
                throw new ApplicationException("There is no data.");
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                MemberReferenceMappingPojo pojo = new MemberReferenceMappingPojo();
                String desgntn = vtr.get(2).toString();
                String referedBy = vtr.get(4).toString();
                String name = vtr.get(8).toString();
                String addresss = vtr.get(9).toString();
                String contactNo = vtr.get(10).toString();
                String membershipNo = vtr.get(7).toString();

                pojo.setDesignation(desgntn);
                pojo.setReferBy(referedBy);
                pojo.setPersonName(name);
                pojo.setAddress(addresss);
                pojo.setContactNo(contactNo);
                pojo.setMembershipNo(membershipNo);
                resultList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public List<MemberReferenceMappingPojo> getReferenceDetails(String designation, String referBy, String dt) throws ApplicationException {

        List<MemberReferenceMappingPojo> resultList = new ArrayList();
        try {// ut.begin();
            String subFilterQuery = "";
            if (!designation.equalsIgnoreCase("A") && !referBy.equalsIgnoreCase("A")) {
                subFilterQuery = " and re.DesignationCode  = '" + designation + "' and re.PersonRefNo = '" + referBy + "' ";
            } else if (!designation.equalsIgnoreCase("A") && referBy.equalsIgnoreCase("A")) {
                subFilterQuery = " and re.DesignationCode  = '" + designation + "' ";
            }
            String query = "select re.ORDER_BY, re.DesignationCode, re.UnderDes, re.PersonRefNo, re.ReferBy, "
                    + "mem.DesignationCode, mem.ReferBy, mem.MembershipNo, mem.memName, mem.memAdd, mem.memMobNo from  "
                    + "(select aa.DesignationCode, aa.ReferBy, aa.MembershipNo, aa.memName,  "
                    + "concat(aa.MailAddressLine1, aa.MailAddressLine2, aa.MailVillage, aa.MailBlock,  "
                    + "if(ifnull(bb.mailCity,'')='','',concat(trim(bb.mailCity),' ')) ,  "
                    + "if(ifnull(cc.mailState,'')='','',concat(trim(cc.mailState),' ')),  "
                    + "aa.MailPostalCode) as memAdd, aa.memMobNo  from  "
                    + "(select c.DesignationCode, c.ReferBy, c.MembershipNo, trim(d.CustFullName) as memName,  "
                    + "if(ifnull(d.MailAddressLine1,'')='','',concat(trim(d.MailAddressLine1),' ')) as MailAddressLine1,  "
                    + "if(ifnull(d.MailAddressLine2,'')='','',concat(trim(d.MailAddressLine2),' ')) as MailAddressLine2, "
                    + "if(ifnull(d.MailVillage,'')='','',concat(trim(d.MailVillage),' ')) as MailVillage, "
                    + "if(ifnull(d.MailBlock,'')='','',concat(trim(d.MailBlock),' ')) as MailBlock,  "
                    + "if(ifnull(d.mailCityCode,'')='','',d.mailCityCode) as mailCityCode,  "
                    + "if(ifnull(d.MailStateCode,'')='','',d.MailStateCode) as MailStateCode,  "
                    + "if(ifnull(d.MailPostalCode,'')='','',d.MailPostalCode) as MailPostalCode,  "
                    + "ifnull(d.mobilenumber, ifnull(d.TelexNumber, ifnull(d.MailPhoneNumber, ifnull(d.MailTelexNumber, ifnull(d.MailFaxNumber, ''))))) as memMobNo "
                    + "from (select ma.MembershipNo, ma.ReferBy, ma.DesignationCode from "
                    + "(select MembershipNo, ReferBy, DesignationCode, EnterBy, EnteredDate, TxnId from member_reference_mapping where EnteredDate<='" + dt + "' ) ma,"
                    + "(select MembershipNo, max(Txnid) as Txnid from member_reference_mapping where EnteredDate<='" + dt + "' group by MembershipNo)mb "
                    + "where ma.MembershipNo = mb.MembershipNo and ma.TxnId = mb.TxnId ) c, cbs_customer_master_detail d, share_holder e  "
                    + "where d.customerid = e.custId  "
                    + "and e.Regfoliono = c.MembershipNo and d.SuspensionFlg<>'S') aa  "
                    + "left join  "
                    + "(select ref_code, ref_desc as mailCity from cbs_ref_rec_type where ref_rec_no = 001 ) bb on bb.REF_CODE = aa.mailCityCode  "
                    + "left join  "
                    + "(select ref_code, ref_desc as MailState from cbs_ref_rec_type where ref_rec_no = 002 ) cc on cc.ref_code = aa.MailStateCode ) mem, "
                    + "(select a.ORDER_BY, b.DesignationCode, a.REF_DESC as UnderDes, b.PersonRefNo, concat( '[',b.PersonRefNo,'] ', trim(b.PersonName)) as ReferBy "
                    + "from cbs_ref_rec_type a, member_Designation_mapping b  "
                    + "where a.REF_CODE = b.DesignationCode  "
                    + "and a.ref_rec_no = '416' order by a.ORDER_BY, b.PersonRefNo) re  "
                    + "where mem.DesignationCode = re.DesignationCode and mem.ReferBy = re.PersonRefNo  " + subFilterQuery
                    + "order by ORDER_BY, cast(re.PersonRefNo as decimal)";
            List result = em.createNativeQuery(query).getResultList();
            System.out.println(query);
            if (result.isEmpty()) {
                throw new ApplicationException("data does not exits.");
            } else {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    MemberReferenceMappingPojo pojo = new MemberReferenceMappingPojo();
                    String desgntn = vtr.get(2).toString();
                    String referedBy = vtr.get(4).toString();
                    String name = vtr.get(8).toString();
                    String addresss = vtr.get(9).toString();
                    String contactNo = vtr.get(10).toString();
                    String membershipNo = vtr.get(7).toString();

                    pojo.setDesignation(desgntn);
                    pojo.setReferBy(referedBy);
                    pojo.setPersonName(name);
                    pojo.setAddress(addresss);
                    pojo.setContactNo(contactNo);
                    pojo.setMembershipNo(membershipNo);
                    resultList.add(pojo);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    /**
     * ****************************** CategoryForm *************************
     */
    public List categoryDetail() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select category from share_holder_category");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List CategoryfolioDetail(String folioNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select cc.CustNAME,'',cc.fathername,sh.houseno,sh.sector,sh.city,cc.perAddressLine1,sh.category from share_holder sh, cbs_customer_master_detail cc where sh.regfoliono= '" + folioNo + "' and cc.customerid = sh.custid ");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String CategorySaveUpdateAction(String mode, String str2, String str1) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String message = "";
            if (mode.equalsIgnoreCase("Save")) {
                Integer updateshareholder = em.createNativeQuery("UPDATE share_holder SET CATEGORY='" + str2 + "' WHERE REGFOLIONO='" + str1 + "'").executeUpdate();
                if (updateshareholder <= 0) {
                    ut.rollback();
                    message = "Data Not Saved";
                } else {
                    ut.commit();
                    message = "true";
                }
            } else if (mode.equalsIgnoreCase("Update")) {
                Integer updateshareholder = em.createNativeQuery("UPDATE share_holder SET CATEGORY='" + str2 + "' WHERE REGFOLIONO='" + str1 + "'").executeUpdate();
                if (updateshareholder <= 0) {
                    ut.rollback();
                    message = "Data Not Updated";
                } else {
                    ut.commit();
                    message = "true";
                }
            }
            return message;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String deleteCategoryMaintenanceAction(String folioNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer reconbalan = em.createNativeQuery("UPDATE share_holder SET CATEGORY='' WHERE REGFOLIONO='" + folioNo + "'").executeUpdate();
            if (reconbalan <= 0) {
                ut.rollback();
                return "Data not deleted";
            } else {
                ut.commit();
                return "Category deleted for the Folio No. : " + folioNo;
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    /**
     * ****************************** End ************************
     */
    /**
     * ****************************** CategoryMaintenanceFormBean
     * *************************
     */
    public List CategoryMaintenanceDetail() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT CATEGORY FROM share_holder_category");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String saveCategoryMaintenanceAction(String category, String enterby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List shareList = em.createNativeQuery("select * from share_holder_category where category = '" + category + "'").getResultList();
            if (shareList.size() > 0) {
                ut.rollback();
                return "This category already exist in database,please fill another category";
            }
            Integer reconbalan = em.createNativeQuery("insert into share_holder_category(category,dt,enterby)values('" + category + "',now(),'" + enterby + "')").executeUpdate();
            if (reconbalan <= 0) {
                ut.rollback();
                return "Data not saved";
            } else {
                ut.commit();
                return "Record Successfully Saved";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String CategoryMaintenanceDeleteAction(String category) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer reconbalan = em.createNativeQuery("DELETE FROM share_holder_category WHERE CATEGORY='" + category + "'").executeUpdate();
            if (reconbalan <= 0) {
                ut.rollback();
                return "Data not deleted";
            } else {
                ut.commit();
                return "Record Successfully Deleted";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

//    public void CategoryMaintenanceOnloadAction() throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            List selectQuery = em.createNativeQuery("SELECT NAME FROM SYSCOLUMNS WHERE ID IN (SELECT ID FROM SYSOBJECTS WHERE NAME LIKE 'SHARE_HOLDER') AND NAME LIKE 'CATEGORY'").getResultList();
//            if (selectQuery.size() <= 0) {
//                Query altershareholder = em.createNativeQuery("ALTER TABLE SHARE_HOLDER ADD CATEGORY VARCHAR(50)");
//                Integer var = altershareholder.executeUpdate();
//            }
//            List selectQuery1 = em.createNativeQuery("SELECT NAME FROM SYSCOLUMNS WHERE ID IN (SELECT ID FROM SYSOBJECTS WHERE NAME LIKE 'SHARE_HOLDER_HIS') AND NAME LIKE 'CATEGORY'").getResultList();
//            if (selectQuery1.size() <= 0) {
//                Query altershareholder1 = em.createNativeQuery("ALTER TABLE SHARE_HOLDER_HIS ADD CATEGORY VARCHAR(50)");
//                Integer var1 = altershareholder1.executeUpdate();
//            }
//            List selectQuery2 = em.createNativeQuery("select * from sysobjects where id = object_id(N'[dbo].[SHARE_HOLDER_CATEGORY]') and OBJECTPROPERTY(id, N'IsUserTable') = 1").getResultList();
//            if (selectQuery2.size() <= 0) {
//                Query altershareholder2 = em.createNativeQuery("CREATE TABLE [dbo].[SHARE_HOLDER_CATEGORY] ([SNO] [int] IDENTITY (1, 1) NOT NULL ,[CATEGORY] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,[DT] [datetime] NULL ,[ENTERBY] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL) ON [PRIMARY]");
//                Integer var2 = altershareholder2.executeUpdate();
//            }
//            ut.commit();
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(e);
//            } catch (IllegalStateException ex) {
//                throw new ApplicationException(ex);
//            } catch (SecurityException ex) {
//                throw new ApplicationException(ex);
//            } catch (SystemException ex) {
//                throw new ApplicationException(ex);
//            }
//        }
//    }
    /**
     * ****************************** End ************************
     */
    /**
     * ****************************** Share Value ************************
     */
    public String saveShareValue(double shareValue, String effectiveDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List shareList = em.createNativeQuery("select * from share_value where Effectivedt='" + effectiveDt + "'").getResultList();
            if (shareList.size() > 0) {
                ut.rollback();
                return "Please fill another effective date this date already exist in database";
            } else {
                Integer reconbalan = em.createNativeQuery("insert into share_value(Effectivedt,shareAmt)values('" + effectiveDt + "'," + shareValue + ")").executeUpdate();
                if (reconbalan <= 0) {
                    ut.rollback();
                    return "Data not saved";
                } else {
                    ut.commit();
                    return "Record Saved Successfully";
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    /**
     * ****************************** End ************************
     */
    public List getAlphaCode() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select alphacode from branchmaster where AlphaCode not in ('IDBI','UCOB','UCOG','ICICI','CSGL')order by alphacode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getOperatingMode() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select code,description  from codebook where groupcode = 4 and code in (1,2,5,9)").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getPurposeList() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select acctdesc from accounttypemaster where AcctNature in('CA','TL','DL','FD','MD') and ifnull(ProductCode,'')<> 'SS' order by AcctNature, AcctCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getAcctType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select distinct (acctcode) from accounttypemaster where AcctNature in('CA','SB','DL','TL') and ifnull(ProductCode,'')<> 'SS'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List categoryList() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("SELECT DISTINCT CATEGORY FROM share_holder_category ORDER BY CATEGORY").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getshareAmount(String dt) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select ifnull(shareamt,0) from share_value where effectivedt=(select max(effectivedt) from share_value where effectivedt<='" + dt + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getUnAuthAccountList() throws ApplicationException {
        try {
            return em.createNativeQuery("select regfoliono from share_holder where authflag='N'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List editDetail(String folioNo) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select regfoliono from share_holder_his where regfoliono='" + folioNo + "'").getResultList();
            if (resultlist.size() > 0) {
                resultlist = em.createNativeQuery("select sh.title,cc.custname,'',DATE_FORMAT(cc.DateOfBirth,'%Y%m%d'),cc.fathername,sh.Purpose,sh.relatedACNo,sh.CATEGORY,"
                        + "sh.buss_desig,sh.firmName, sh.regdate, sh.witname1,sh.witadd1,sh.witname2,sh.witadd2,sh.nomname,sh.nomadd,sh.nomage,sh.nomrelation,sh.comments,sh.operationmode,sh.JtId1,sh.jtname1,"
                        + "sh.JtId2,sh.jtname2,sh.lastupdateby,sh.Jdate,sh.salary,sh.gradePay,sh.area,sh.BeneficiaryAccNo,sh.BeneficiaryName,sh.IFSCCode,sh.BankName,cc.pan_girnumber,NomDob,AlphaCode from share_holder sh, cbs_customer_master_detail cc  where sh.regfoliono='" + folioNo + "' and cc.customerid = sh.custid").getResultList();
            } else {
                resultlist = em.createNativeQuery("select sh.title,cc.custname,'',DATE_FORMAT(cc.DateOfBirth,'%Y%m%d'),cc.fathername,sh.Purpose,sh.relatedACNo,sh.CATEGORY,"
                        + "sh.buss_desig,sh.firmName,sh.regdate,sh.witname1,sh.witadd1,sh.witname2,sh.witadd2,sh.nomname,sh.nomadd,sh.nomage,sh.nomrelation,sh.comments,sh.operationmode,sh.JtId1,sh.jtname1,"
                        + "sh.JtId2,sh.jtname2,sh.Enterby,sh.Jdate,sh.salary,sh.gradePay,sh.area,sh.BeneficiaryAccNo,sh.BeneficiaryName,sh.IFSCCode,sh.BankName,cc.pan_girnumber,NomDob,AlphaCode from share_holder sh, cbs_customer_master_detail cc  where sh.regfoliono='" + folioNo + "'and cc.customerid = sh.custid").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public String saveAccountOpen(List<AccGenInfo> acOpenList, String mode, String custId, String folioNo, String relacno, String registerDt, String wtFName,
            String wtFAdd, String wtSName, String wtSAdd, String nomineeName, String nomineeAdd, String nomineeAge, String nomineeRelation,
            String remark, String operationMode, String jtId1, String jtId2, String userName, String purpose, String businessDesig,
            String firmHolderName, String category, String date, String orgnBrCode, String jtName1, String jtName2, String jDt,
            String Sal, String gPay, String area, String benefAcNo, String benefName, String ifscCode, String bnkName, String panNo,
            String nomineeDob, String occupation, String docName, String docDetail, String introAcNo, String actCateg, String branch) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (mode.equalsIgnoreCase("1")) {
                if (nomineeDob.equalsIgnoreCase("19000101")) {
                    nomineeDob = "";
                } else {
                    nomineeDob = dmy.format(ymd.parse(nomineeDob));
                }
                List folioList = em.createNativeQuery("select max(substring(regfoliono,5,6)) from share_holder").getResultList();
                int num = 1;
                if (folioList.size() > 0) {
                    Vector descVect = (Vector) folioList.get(0);
                    if (descVect.get(0) != null) {
                        num = Integer.parseInt(descVect.get(0).toString()) + 1;
                    }
                }
                folioNo = orgnBrCode + CbsAcCodeConstant.SF_AC.getAcctCode() + CbsUtil.lPadding(6, num) + "01";
                Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO) values ('" + folioNo + "','" + folioNo + "')");
                insertMapping.executeUpdate();

                List foliocheckList = em.createNativeQuery("select * from share_holder where regfoliono='" + folioNo + "'").getResultList();
                if (foliocheckList.size() > 0) {
                    throw new ApplicationException("Problem in generating Folio Number.");
                }
                String custinfo = acOpenFacadeRemote.custIdInformation(custId);
//                if (custinfo.equals("This Customer ID Does Not Exists")) {
//                    throw new ApplicationException(custinfo);
//                }
                String[] custDetails = custinfo.split(":");
                String title = custDetails[0].trim(),
                        custName = custDetails[1].trim(),
                        prAddress = custDetails[2].trim(),
                        crAddress = custDetails[3].trim(),
                        fatherName = custDetails[4].trim(),
                        phoneNo = custDetails[5].trim(),
                        introCustId = custDetails[8].trim(),
                        primaryBrCode = custDetails[10].trim();
                panNo = custDetails[6].trim().equalsIgnoreCase("") ? panNo : custDetails[6].trim();
                Date dob = dmy.parse(custDetails[7].trim());
                List alphaCodeList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + Integer.parseInt(custDetails[10].trim())).getResultList();
                if (alphaCodeList.size() <= 0) {
                    throw new ApplicationException("Alpha Code does not exist.");
                }
                Vector vec = (Vector) alphaCodeList.get(0);
                String alphaCode = vec.get(0).toString();
                Integer detail = em.createNativeQuery("Insert into share_holder(regdate,regfoliono,witname1,witadd1,witname2,witadd2,nomname,"
                        + "nomadd,nomage,nomrelation,comments,operationmode,JtId1,JtId2,Enterby,Purpose,relatedACNo,buss_desig,"
                        + "firmName,authflag,CATEGORY,custid,title,fname,lname,dob,fathername,phone,permadd,alphacode, jtname1,jtname2,Jdate,Salary,gradePay,area,BeneficiaryAccNo,BeneficiaryName,IFSCCode,BankName,NomDob) "
                        + "values('" + registerDt + "','" + folioNo + "','" + wtFName + "','" + wtFAdd + "','" + wtSName + "','" + wtSAdd + "','"
                        + nomineeName + "','" + nomineeAdd + "','" + nomineeAge + "','" + nomineeRelation + "','" + remark + "','"
                        + operationMode + "','" + jtId1 + "','" + jtId2 + "','" + userName + "','" + purpose + "','" + relacno + "','"
                        + businessDesig + "','" + firmHolderName + "','N','" + category + "','" + custId + "','" + custDetails[0].trim() + "','"
                        + custDetails[1].trim() + "','','" + ymd.format(dob) + "','" + custDetails[4].trim() + "','" + custDetails[5].trim() + "','"
                        + custDetails[2].trim() + "','" + alphaCode + "','" + jtName1 + "','" + jtName2 + "','" + jDt + "'," + Float.parseFloat(Sal)
                        + "," + Float.parseFloat(gPay) + ",'" + area + "','" + benefAcNo + "','" + benefName + "','" + ifscCode + "','" + bnkName + "','" + nomineeDob + "')").executeUpdate();
                if (detail <= 0) {
                    throw new ApplicationException("Problem in saving Share Holder details");
                }
                ut.commit();
                return "Generated Customer Account No is -->  " + folioNo;
            } else if (mode.equalsIgnoreCase("2")) {
                String nomDob = "";
                if (nomineeDob.equalsIgnoreCase("19000101") || nomineeDob.equalsIgnoreCase("")) {
                    nomDob = "";
                } else {
                    nomDob = dmy.format(ymd.parse(nomineeDob));
                }

                Integer detail = em.createNativeQuery("INSERT INTO share_holder_his (Regfoliono,Fname,Lname,Dob,Fathername,Houseno,Sector,City,Phone,"
                        + "Permadd,Witname1,Witadd1,Witname2,Witadd2,Nomname,Nomadd,Nomage,Nomrelation,zipcode,Comments,reg_folio,Operationmode,JtName1,"
                        + "JtName2,Enterby,Trantime,Regdate,lastupdatedt,lastupdateby,ShareType,applicationNo,purpose,relatedACNo,buss_desig,firmName,"
                        + "age,AlphaCode,authflag,AuthBy,area,CATEGORY,TITLE,custId,JtId1,JtId2,Jdate,Salary,gradePay,BeneficiaryAccNo,BeneficiaryName,IFSCCode,BankName,NomDob) "
                        + "select * from share_holder where regfoliono='" + folioNo + "'").executeUpdate();
                if (detail <= 0) {
                    throw new ApplicationException("Problem in saving Share Holder details");
                }
                Integer certificate = em.createNativeQuery("update share_holder set witname1='" + wtFName + "', witadd1='" + wtFAdd + "', witname2='" + wtSName
                        + "', witadd2='" + wtSAdd + "',nomname='" + nomineeName + "',nomadd='" + nomineeAdd + "', nomage= '" + nomineeAge + "', nomrelation='"
                        + nomineeRelation + "',CATEGORY='" + category + "', comments='" + remark + "', operationmode='" + operationMode + "', JtId1='" + jtId1
                        + "',JtId2='" + jtId2 + "', lastupdatedt='" + date + "',lastupdateby='" + userName + "', purpose='" + purpose + "', relatedAcNo='"
                        + relacno + "', buss_desig='" + businessDesig + "', area='" + area + "',firmname='" + firmHolderName + "' ,authflag='N',Jdate='" + jDt
                        + "',Salary=" + Float.parseFloat(Sal) + ",gradePay=" + Float.parseFloat(gPay) + ",BeneficiaryAccNo='" + benefAcNo + "',BeneficiaryName='"
                        + benefName + "',IFSCCode='" + ifscCode + "',BankName='" + bnkName + "',NomDob= '" + nomDob + "',AlphaCode = '"+branch+"',JtName1='" + jtName1 + "',JtName2='" + jtName2 + "' where regfoliono='" + folioNo + "'").executeUpdate();

                String custinfo = acOpenFacadeRemote.custIdInformation(custId);
                if (custinfo.equals("This Customer ID Does Not Exists")) {
                    throw new ApplicationException(custinfo);
                }
                String[] custDetails = custinfo.split(":");
                String title = custDetails[0].trim(),
                        custName = custDetails[1].trim(),
                        prAddress = custDetails[2].trim(),
                        crAddress = custDetails[3].trim(),
                        fatherName = custDetails[4].trim(),
                        phoneNo = custDetails[5].trim(),
                        introCustId = custDetails[8].trim(),
                        primaryBrCode = custDetails[10].trim();
                panNo = custDetails[6].trim().equalsIgnoreCase("") ? panNo : custDetails[6].trim();
                Date dob = dmy.parse(custDetails[7].trim());
                for (int i = 0; i < acOpenList.size(); i++) {
                    String brnCode = commom.getBrncodeByAlphacode(acOpenList.get(i).getBranch());
                    // AccGenInfo obj = new AccGenInfo();
                    acOpenList.get(i).getAcNo();
                    if (acOpenList.get(i).getAcNo() == null || acOpenList.get(i).getAcNo().equalsIgnoreCase("")) {

                        if (acOpenList.get(i).getActNature().equalsIgnoreCase(CbsConstant.CURRENT_AC) || acOpenList.get(i).getActNature().equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            Integer loanPeriodMm = 0; //It will be calculated from Scheme and Memeber DOB (which is less)
                            Float limit = 0f;
                            Float roi = 0f;
                            String intOpt, calMethod, interestAppFreq1, interestAppFreq2 = null, intCode;
                            List schemeValueList = em.createNativeQuery("select l.INT_BASE_METHOD,l.INT_PRODUCT_METHOD,l.COMPOUNDING_FREQ,t.INTEREST_TABLE_CODE, l.LOAN_PERIOD_MAX_MONTHS, l.LOAN_AMOUNT_MAX"
                                    + " from cbs_scheme_loan_scheme_details l,cbs_scheme_tod_reference_details t where l.SCHEME_CODE =t.SCHEME_CODE and l.SCHEME_CODE='" + acOpenList.get(i).getSchemeCode() + "' ").getResultList();
                            if (schemeValueList.isEmpty()) {
                                ut.rollback();
                                return "Data doesnt't exist for -->  " + acOpenList.get(i).getSchemeCode();
                            } else {
                                Vector ele = (Vector) schemeValueList.get(0);
                                calMethod = ele.get(0).toString();
                                interestAppFreq1 = ele.get(2).toString();
                                if (ele.get(2).toString().equalsIgnoreCase("0") || ele.get(2).toString().equalsIgnoreCase("")
                                        || ele.get(2).toString().length() == 0 || ele.get(2).toString() == null) {
                                    interestAppFreq1 = "S";
                                    interestAppFreq2 = ele.get(2).toString();
                                } else {
                                    interestAppFreq2 = ele.get(2).toString();
                                    interestAppFreq1 = "C";
                                }
                                intCode = ele.get(3).toString();
                                loanPeriodMm = Integer.parseInt(ele.get(4).toString());
                                limit = acOpenList.get(i).getPriority().equalsIgnoreCase("P") ? Float.parseFloat(ele.get(5).toString()) : 0;
                                roi = Float.parseFloat(acOpenFacadeRemote.GetROIAcOpen(intCode, acOpenList.get(i).getPriority().equalsIgnoreCase("P") ? Double.parseDouble(ele.get(5).toString()) : 0d, loanPeriodMm, registerDt));
                            }

                            String result = ftsPostFacade.saveTLAcOpenDetailWithOutTranMgmt(custId, acOpenList.get(i).getActNature(), "false", "--Select--", "", acOpenList.get(i).getActType(),
                                    brnCode, "01", registerDt, title, custName, fatherName, prAddress,
                                    crAddress, phoneNo, panNo, ymd.format(dob), Integer.parseInt(occupation), "", "",
                                    Integer.parseInt(operationMode), jtName1, jtName2, "", "", nomineeName, nomineeRelation,
                                    nomineeAdd, nomineeDob, introAcNo, limit, roi,
                                    registerDt, "C", "0", Integer.parseInt(docName),
                                    docDetail, "", userName, acOpenList.get(i).getSchemeCode(), Integer.parseInt("0"), Float.parseFloat("0"),
                                    Float.parseFloat("0"), "Fl", calMethod, "END", interestAppFreq1, "L", interestAppFreq2, "S", intCode,
                                    "0", loanPeriodMm, Integer.parseInt("0"), jtId1, jtId2, "", "", actCateg, (folioNo.length() == 12 ? folioNo.substring(4, 10) : ""), "", 0);
//
                            if (result == null) {
                                ut.rollback();
                                return "DATA NOT SAVED , SOME PROBLEM OCCURED !!!";
                            } else {
                                if (!(result.contains("FOR CUST ID"))) {
                                    ut.rollback();
                                    return result;
                                } else if (result.contains("!")) {
                                    ut.rollback();
                                    return result;
                                }
                            }
                        } else if (acOpenList.get(i).getActNature().equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            Integer loanPeriodMm = 0; //It will be calculated from Scheme and Memeber DOB (which is less)
                            Float limit = 0f;
                            Float roi = 0f;
                            String intOpt, calMethod, interestAppFreq1, interestAppFreq2 = null, intCode;
                            List<DlAcctOpenReg> dlAcctOpen = new ArrayList<DlAcctOpenReg>();
                            List schemeValueList = em.createNativeQuery("select l.INT_BASE_METHOD,l.INT_PRODUCT_METHOD,l.COMPOUNDING_FREQ,t.INTEREST_TABLE_CODE, l.LOAN_PERIOD_MAX_MONTHS, l.LOAN_AMOUNT_MAX"
                                    + " from cbs_scheme_loan_scheme_details l,cbs_scheme_tod_reference_details t where l.SCHEME_CODE =t.SCHEME_CODE and l.SCHEME_CODE='" + acOpenList.get(i).getSchemeCode() + "' ").getResultList();
                            if (schemeValueList.isEmpty()) {
                                ut.rollback();
                                return "Data doesnt't exist for -->  " + acOpenList.get(i).getSchemeCode();
                            } else {
                                Vector ele = (Vector) schemeValueList.get(0);
                                calMethod = ele.get(0).toString();
                                interestAppFreq1 = ele.get(2).toString();
                                if (ele.get(2).toString().equalsIgnoreCase("0") || ele.get(2).toString().equalsIgnoreCase("")
                                        || ele.get(2).toString().length() == 0 || ele.get(2).toString() == null) {
                                    interestAppFreq1 = "S";
                                } else {
                                    interestAppFreq2 = ele.get(2).toString();
                                    interestAppFreq1 = "C";
                                }
                                intCode = ele.get(3).toString();
                                loanPeriodMm = Integer.parseInt(ele.get(4).toString());
                                limit = acOpenList.get(i).getPriority().equalsIgnoreCase("P") ? Float.parseFloat(ele.get(5).toString()) : 0;
                                roi = Float.parseFloat(acOpenFacadeRemote.GetROIAcOpen(intCode, acOpenList.get(i).getPriority().equalsIgnoreCase("P") ? Double.parseDouble(ele.get(5).toString()) : 0d, loanPeriodMm, registerDt));
                            }
                            String resultList = ftsPostFacade.saveDlAcctOpenRegisterWithOutTranMgmt(acOpenList.get(i).getActType(),
                                    occupation, operationMode, brnCode, "01",
                                    limit, roi, title, custName, crAddress, prAddress, phoneNo,
                                    userName, panNo, "", nomineeAge, jtName1, jtName2, introAcNo, category,
                                    custId, acOpenList.get(i).getSchemeCode(), intCode, Integer.parseInt("0"), Float.parseFloat("0"),
                                    Float.parseFloat("0"), "Fl", "S", calMethod, "END", "L",
                                    interestAppFreq2, Integer.parseInt("0"), interestAppFreq1, loanPeriodMm,
                                    Integer.parseInt("0"), dlAcctOpen, ymd.format(dob), jtId1, jtId2, actCateg, "MEMBERSHIP", (folioNo.length() == 12 ? folioNo.substring(4, 10) : ""), "");
                            if (resultList.contains("Customer Verification")) {
                                ut.rollback();
                                return "Customer Verification is not completed.";
                            } else if (resultList.substring(0, 5).equalsIgnoreCase("false")) {
                                ut.rollback();
                                return "DATA IS NOT SAVED";
                            }
                        } else if (acOpenList.get(i).getActNature().equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            Integer loanPeriodMm = 0; //It will be calculated from Scheme and Memeber DOB (which is less)
                            Float limit = 0f;
                            Float roi = 0f;
                            String intOpt, calMethod, interestAppFreq1, interestAppFreq2 = null, intCode;
                            List<DlAcctOpenReg> dlAcctOpen = new ArrayList<DlAcctOpenReg>();
                            List schemeValueList = em.createNativeQuery("select l.INT_BASE_METHOD,l.INT_PRODUCT_METHOD,l.COMPOUNDING_FREQ,t.INTEREST_TABLE_CODE, l.LOAN_PERIOD_MAX_MONTHS, l.LOAN_AMOUNT_MAX"
                                    + " from cbs_scheme_loan_scheme_details l,cbs_scheme_tod_reference_details t where l.SCHEME_CODE =t.SCHEME_CODE and l.SCHEME_CODE='" + acOpenList.get(i).getSchemeCode() + "' ").getResultList();
                            if (schemeValueList.isEmpty()) {
                                ut.rollback();
                                return "Data doesnt't exist for -->  " + acOpenList.get(i).getSchemeCode();
                            } else {
                                Vector ele = (Vector) schemeValueList.get(0);
                                calMethod = ele.get(0).toString();
                                interestAppFreq1 = ele.get(2).toString();
                                if (ele.get(2).toString().equalsIgnoreCase("0") || ele.get(2).toString().equalsIgnoreCase("")
                                        || ele.get(2).toString().length() == 0 || ele.get(2).toString() == null) {
                                    interestAppFreq1 = "S";
                                } else {
                                    interestAppFreq2 = ele.get(2).toString();
                                    interestAppFreq1 = "C";
                                }
                                intCode = ele.get(3).toString();
                                loanPeriodMm = Integer.parseInt(ele.get(4).toString());
                                limit = acOpenList.get(i).getPriority().equalsIgnoreCase("P") ? Float.parseFloat(ele.get(5).toString()) : 0;
                                roi = Float.parseFloat(acOpenFacadeRemote.GetROIAcOpen(intCode, acOpenList.get(i).getPriority().equalsIgnoreCase("P") ? Double.parseDouble(ele.get(5).toString()) : 0d, loanPeriodMm, registerDt));
                            }
                            String save = ftsPostFacade.saveAccountOpenSbRdWithOutTranMgmt("", custId, acOpenList.get(i).getActType(),
                                    title, custName, crAddress, prAddress, phoneNo, ymd.format(dob), Integer.parseInt(occupation),
                                    Integer.parseInt(operationMode), panNo, "", "", "01", registerDt, userName, fatherName,
                                    introAcNo, jtId1, jtId2, brnCode, nomineeName, nomineeRelation, "", "", Integer.parseInt("0"),
                                    Float.parseFloat("0.0"), roi, Integer.parseInt(docName), docDetail, nomineeAdd, nomineeDob, jtId1,
                                    jtId2, "", "", acOpenList.get(i).getSchemeCode(), intCode, actCateg, (folioNo.length() == 12 ? folioNo.substring(4, 10) : ""), "", 0);
                            if (save.equalsIgnoreCase("Account does not Open")) {
                                ut.rollback();
                                return save;
                            }
                        }
//                    else if (acOpenList.get(i).getActNature().equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                        String resultList = fdDdsAccountOpeningFacadeRemote.saveAccountOpenDDS("", custId, acOpenList.get(i).getActType(), title, custName, craddress, praddress, phoneNo,
//                                ymd.format(dob), Integer.parseInt(occupation), Integer.parseInt(operationMode), "", panNo,
//                                "", "", "01", ymd.format(registerDt), userName, fatherName, introAcNo, jtName1,
//                                jtName2, "", "", orgnBrCode, nomineeName, nomineeRelation, Integer.parseInt(docName), docDetail, Float.parseFloat(day), Float.parseFloat(amount), Float.parseFloat(roi),
//                                nomineeName, nomineeAdd, nomineeAdd, "",
//                                ymd.format(nomineeDob), nomineeAge, jtId1, jtId2, "", "");
//                        if (resultList.contains("Customer Verification")) {
//                            ut.rollback();
//                            return "Customer Verification is not completed.";
//                        } else if (!resultList.substring(0, 4).equalsIgnoreCase("true")) {
//                            ut.rollback();
//                            return "DATA IS NOT SAVED";
//                        }
//                    }
                    }
                }
                if (certificate > 0) {
                    ut.commit();
                    return "Account Details successfully updated.";
                } else {
                    throw new ApplicationException("Problem in updating Share Holder details");
                }
            } else {
                Integer certificate = em.createNativeQuery("update share_holder set authflag='Y', authby='" + userName + "'  where regfoliono='" + folioNo + "'").executeUpdate();
                if (certificate > 0) {
                    ut.commit();
                    return "Account Details successfully verified.";
                } else {
                    throw new ApplicationException("Problem in verifing Share Holder details");
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                e.printStackTrace();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String getLevelId(String userId, String brCode) throws ApplicationException {
        try {
            List alphaCodeList = em.createNativeQuery("select levelid from securityinfo where brncode='" + brCode + "' and userid='" + userId + "'").getResultList();
            if (alphaCodeList.size() <= 0) {
                throw new ApplicationException("User Id does not exist.");
            }
            Vector vec = (Vector) alphaCodeList.get(0);
            return vec.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List areaList() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no = '019'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List<AreaWiseSharePojo> getDefaulterShareData(String status, String area, String date) throws ApplicationException {
        List<AreaWiseSharePojo> dataList = new ArrayList<AreaWiseSharePojo>();
        List result, result1, result2, result3, result4, result5 = null;
        try {
            if (area.equalsIgnoreCase("ALL")) {
                result = em.createNativeQuery("select distinct sh.custid,si.foliono,cmd.custname,cmd.fathername,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1  "
                        + "from share_holder sh,cbs_customer_master_detail cmd,share_capital_issue si where sh.custId = cmd.customerid and "
                        + "sh.Regfoliono =si.foliono and si.sharecertno in (select certificateNo from  certificate_share where (paymentdt is null or paymentdt >='" + date + "'))").getResultList();
            } else {
                result = em.createNativeQuery("select distinct sh.custid,si.foliono,cmd.custname,cmd.fathername,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1  "
                        + "from share_holder sh,cbs_customer_master_detail cmd,share_capital_issue si where sh.area = '" + area + "' and sh.custId = cmd.customerid and "
                        + "sh.Regfoliono =si.foliono and si.sharecertno in (select certificateNo from  certificate_share where (paymentdt is null or paymentdt >='" + date + "'))").getResultList();
            }

            if (status.equalsIgnoreCase("D")) {
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector ele = (Vector) result.get(i);
                        String cId = ele.get(0).toString();
                        int cnt = 0;
                        result1 = em.createNativeQuery("select c.acno from customerid c, accountmaster a where c.custid = '" + cId + "' and substring(c.acno,3,2) in "
                                + " (SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and CrDbFlag ='D') and c.acno = a.acno and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + date + "')").getResultList();

                        if (!result1.isEmpty()) {
                            for (int j = 0; j < result1.size(); j++) {
                                Vector ele1 = (Vector) result1.get(j);
                                String acno = ele1.get(j).toString();
                                String presentStatus = interBranchFacade.fnGetLoanStatus(acno, date);
                                if (presentStatus.equalsIgnoreCase("DOU") || presentStatus.equalsIgnoreCase("SUB") || presentStatus.equalsIgnoreCase("LOS")) {
                                    if (cnt == 0) {
                                        AreaWiseSharePojo pojo = new AreaWiseSharePojo();
                                        pojo.setFolioNo(ele.get(1).toString());
                                        pojo.setCustName(ele.get(2).toString());
                                        if (ele.get(3) == null || ele.get(3).toString().equalsIgnoreCase("")) {
                                            pojo.setFatherName("");
                                        } else {
                                            pojo.setFatherName(ele.get(3).toString());
                                        }

                                        String dob = ele.get(4).toString().substring(8, 10) + "/" + ele.get(4).toString().substring(5, 7) + "/" + ele.get(4).toString().substring(0, 4);

                                        if (dob == null || dob.equalsIgnoreCase("") || dob.equalsIgnoreCase("01/01/1900")) {
                                            pojo.setDob("");
                                        } else {
                                            pojo.setDob(dob);
                                        }
                                        pojo.setPermAdd(ele.get(5).toString());
                                        pojo.setCorresspndAdd(ele.get(6).toString());
                                        pojo.setCustId(cId);
                                        dataList.add(pojo);


                                        /*      Code Comment Because CustId Save With Account No *****/
                                        /* result2 = em.createNativeQuery("select ifnull(gar_acno,'') from loan_guarantordetails where acno in (select "
                                         + " c.acno from customerid c, accountmaster a where c.custid = '"+ cId +"' and substring(c.acno,3,2) in (SELECT "
                                         + " ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN ('TL','DL','CA') and CrDbFlag ='D') and c.acno = a.acno and (a.closingdate is null or a.closingdate = '' or a.closingdate > '"+ date +"'))").getResultList();
                                         if (!result2.isEmpty()) {
                                         for (int k = 0; k < result2.size(); k++) {
                                         Vector ele2 = (Vector) result2.get(k);
                                         String gAcno = ele2.get(k).toString();
                                         if(!gAcno.equalsIgnoreCase("")){
                                         result3 = em.createNativeQuery("select distinct sh.regfoliono,cmd.custname,"
                                         + " cmd.fathername,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1 "
                                         + " from share_holder sh,cbs_customer_master_detail cmd,customerid ci "
                                         + " where sh.custId = cmd.customerid and ci.custid = sh.custid "
                                         + " and ci.acno = '"+ gAcno +"'").getResultList();
                                         if (!result3.isEmpty()) {
                                         for (int l = 0; l < result3.size(); l++) {
                                         Vector ele3 = (Vector) result3.get(l);
                                         AreaWiseSharePojo pojo1 = new AreaWiseSharePojo();
                                         pojo1.setFolioNo(ele3.get(0).toString());
                                         pojo1.setCustName(ele3.get(1).toString());
                                         if (ele3.get(3) == null || ele3.get(3).toString().equalsIgnoreCase("")) {
                                         pojo1.setFatherName("");
                                         } else {
                                         pojo1.setFatherName(ele3.get(3).toString());
                                         }
                                        
                                         String dob1 = ele3.get(4).toString().substring(8, 10) + "/" + ele3.get(4).toString().substring(5, 7) + "/" + ele3.get(4).toString().substring(0, 4);
                                        
                                         if (dob1 == null || dob1.equalsIgnoreCase("") || dob1.equalsIgnoreCase("01/01/1900")) {
                                         pojo1.setDob("");
                                         } else {
                                         pojo1.setDob(dob);
                                         }
                                         pojo1.setPermAdd(ele3.get(5).toString());
                                         pojo1.setCorresspndAdd(ele3.get(6).toString());
                                         pojo1.setCustId(cId);
                                         dataList.add(pojo1);
                                         }
                                         }
                                         }
                                         }
                                         }*/

                                        result4 = em.createNativeQuery("select ifnull(gar_custid,'') from loan_guarantordetails where acno in (select "
                                                + " acno from customerid where custid = '" + cId + "' and substring(acno,3,2) in (SELECT "
                                                + " ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN ('TL','DL','CA') and CrDbFlag ='D'))").getResultList();
                                        if (!result4.isEmpty()) {
                                            for (int m = 0; m < result4.size(); m++) {
                                                Vector ele4 = (Vector) result4.get(m);
                                                String gCId = ele4.get(m).toString();
                                                if (!gCId.equalsIgnoreCase("")) {
                                                    result5 = em.createNativeQuery("select distinct sh.regfoliono,cmd.custname,"
                                                            + " cmd.fathername,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1 "
                                                            + " from share_holder sh,cbs_customer_master_detail cmd "
                                                            + " where sh.custId = cmd.customerid and sh.custid = '" + gCId + "'").getResultList();
                                                    if (!result5.isEmpty()) {
                                                        for (int n = 0; n < result5.size(); n++) {
                                                            Vector ele5 = (Vector) result5.get(n);
                                                            AreaWiseSharePojo pojo2 = new AreaWiseSharePojo();
                                                            pojo2.setFolioNo(ele5.get(0).toString());
                                                            pojo2.setCustName(ele5.get(1).toString());
                                                            if (ele5.get(3) == null || ele5.get(3).toString().equalsIgnoreCase("")) {
                                                                pojo2.setFatherName("");
                                                            } else {
                                                                pojo2.setFatherName(ele5.get(3).toString());
                                                            }

                                                            String dob2 = ele5.get(4).toString().substring(8, 10) + "/" + ele5.get(4).toString().substring(5, 7) + "/" + ele5.get(4).toString().substring(0, 4);

                                                            if (dob2 == null || dob2.equalsIgnoreCase("") || dob2.equalsIgnoreCase("01/01/1900")) {
                                                                pojo2.setDob("");
                                                            } else {
                                                                pojo2.setDob(dob);
                                                            }
                                                            pojo2.setPermAdd(ele5.get(5).toString());
                                                            pojo2.setCorresspndAdd(ele5.get(6).toString());
                                                            pojo2.setCustId(cId);
                                                            dataList.add(pojo2);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        cnt = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector ele = (Vector) result.get(i);
                        String cId = ele.get(0).toString();
                        int cnt = 0;
                        String flg = "N";
                        result1 = em.createNativeQuery("select c.acno from customerid c, accountmaster a where c.custid = '" + cId + "' and substring(c.acno,3,2) in "
                                + " (SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and CrDbFlag ='D') and c.acno = a.acno and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + date + "')").getResultList();

                        if (!result1.isEmpty()) {
                            for (int j = 0; j < result1.size(); j++) {
                                Vector ele1 = (Vector) result1.get(j);
                                String acno = ele1.get(j).toString();
                                String presentStatus = interBranchFacade.fnGetLoanStatus(acno, date);
                                cnt = cnt + 1;
                                if (presentStatus.equalsIgnoreCase("DOU") || presentStatus.equalsIgnoreCase("SUB") || presentStatus.equalsIgnoreCase("LOS")) {
                                    flg = "Y";
                                }
                                if (flg.equalsIgnoreCase("N") && cnt == result1.size()) {
                                    AreaWiseSharePojo pojo = new AreaWiseSharePojo();
                                    pojo.setFolioNo(ele.get(1).toString());
                                    pojo.setCustName(ele.get(2).toString());
                                    if (ele.get(3) == null || ele.get(3).toString().equalsIgnoreCase("")) {
                                        pojo.setFatherName("");
                                    } else {
                                        pojo.setFatherName(ele.get(3).toString());
                                    }

                                    String dob = ele.get(4).toString().substring(8, 10) + "/" + ele.get(4).toString().substring(5, 7) + "/" + ele.get(4).toString().substring(0, 4);

                                    if (dob == null || dob.equalsIgnoreCase("") || dob.equalsIgnoreCase("01/01/1900")) {
                                        pojo.setDob("");
                                    } else {
                                        pojo.setDob(dob);
                                    }
                                    pojo.setPermAdd(ele.get(5).toString());
                                    pojo.setCorresspndAdd(ele.get(6).toString());
                                    pojo.setCustId(cId);
                                    dataList.add(pojo);
                                }
                            }
                        } else {
                            AreaWiseSharePojo pojo = new AreaWiseSharePojo();
                            pojo.setFolioNo(ele.get(1).toString());
                            pojo.setCustName(ele.get(2).toString());
                            if (ele.get(3) == null || ele.get(3).toString().equalsIgnoreCase("")) {
                                pojo.setFatherName("");
                            } else {
                                pojo.setFatherName(ele.get(3).toString());
                            }

                            String dob = ele.get(4).toString().substring(8, 10) + "/" + ele.get(4).toString().substring(5, 7) + "/" + ele.get(4).toString().substring(0, 4);

                            if (dob == null || dob.equalsIgnoreCase("") || dob.equalsIgnoreCase("01/01/1900")) {
                                pojo.setDob("");
                            } else {
                                pojo.setDob(dob);
                            }
                            pojo.setPermAdd(ele.get(5).toString());
                            pojo.setCorresspndAdd(ele.get(6).toString());
                            pojo.setCustId(cId);
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public String getCustName(String acNo) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select custname from accountmaster where acno = '" + acNo + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account Name does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getBankNameList() throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_rec_no = '212'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List checkAcno(String brnCode, String acctType) throws ApplicationException {
        try {
            String custNo = acOpenFacadeRemote.cbsCustId(acctType, brnCode);
            String actulCustNo = CbsUtil.lPadding(6, Integer.parseInt(custNo));
            String Tempacno = brnCode + acctType.toUpperCase() + actulCustNo + "01";
            return em.createNativeQuery("select * from cbs_acno_mapping where new_ac_no = '" + Tempacno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getFolioAcnoInfo(String folioNo, String function) throws ApplicationException {
        try {
            if (function.equalsIgnoreCase("2")) {
                return em.createNativeQuery("select a.acno,cl.SCHEME_CODE,ifnull(sum(odlimit),0) from accountmaster a, customerid cu, cbs_loan_acc_mast_sec cl where a.acno in (select acno from cbs_loan_acc_mast_sec where scheme_code in (select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag = 'Y')) and a.acno = cu.acno and a.acno = cl.acno and cu.custid in(select custId from share_holder where Regfoliono = '" + folioNo + "') and accstatus<>9 group by a.acno").getResultList();
            } else {
                return em.createNativeQuery("select a.acno,cl.SCHEME_CODE,ifnull(sum(odlimit),0) from accountmaster a, customerid cu, cbs_loan_acc_mast_sec cl where a.acno in (select acno from cbs_loan_acc_mast_sec where scheme_code in (select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag = 'Y')) and a.acno = cu.acno and a.acno = cl.acno and cu.custid in ('" + folioNo + "') and accstatus<>9 group by a.acno").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getcustIdByFolioNo(String folioNo) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select custId from share_holder where Regfoliono = '" + folioNo + "'").getResultList();
            if (result.size() > 0) {
                Vector element = (Vector) result.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Cust Id does not exist this folio No.");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String isRefAlreadyUsed(String desgination, String refNo) throws ApplicationException {
        String msg = "";
        try {
            List list = em.createNativeQuery("select PersonRefNo from member_Designation_mapping where DesignationCode='" + desgination + "' and PersonRefNo='" + refNo + "'").getResultList();
            if (!list.isEmpty()) {
                msg = "This ref No is already used.";
            } else {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public List getMaxMemberRefNo(String designation) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(max(cast(PersonRefNo as decimal))+1,1) as refID from member_Designation_mapping WHERE DesignationCode='" + designation + "' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String deleteMappingDesignation(String designationCode, String personrefId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int i = em.createNativeQuery("UPDATE member_Designation_mapping SET opFlag ='D' WHERE DesignationCode='" + designationCode + "'"
                    + " and PersonRefNo='" + personrefId + "'").executeUpdate();
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public List<memberRefMappingPojo> getAllDetailOfDesignationBases(String designation, String Date) throws ApplicationException {
        List<memberRefMappingPojo> list = new ArrayList<>();
        try {
//         List list1= em.createNativeQuery("select DesignationCode,PersonRefNo,PersonName,EnteredDate from member_Designation_mapping where "
//                   + "DesignationCode='"+designation+"' order by cast(PersonRefNo as decimal)").getResultList();


            List list1 = em.createNativeQuery("select a.*, cast(a.PersonRefNo as decimal) from "
                    + "(select * from member_Designation_mapping order by cast(PersonRefNo as decimal)) a,"
                    + "(select PersonRefNo, max(txnid) as TxnId from member_Designation_mapping where  "
                    + "EnteredDate <='20181012' group by PersonRefNo) b where  a.PersonRefNo = b.PersonRefNo and"
                    + " a.TxnId = b.TxnId and a.DesignationCode = '" + designation + "' and ifnull(a.opFlag,'')<>'D'").getResultList();

            if (!list1.isEmpty()) {
                for (int i = 0; i < list1.size(); i++) {
                    Vector vector = (Vector) list1.get(i);
                    memberRefMappingPojo pojo = new memberRefMappingPojo();
                    List desinnationPerson = advanceRemote.refRecDesc(vector.get(0).toString());
                    Vector ve = (Vector) desinnationPerson.get(0);
                    pojo.setDegination(ve.get(0).toString());
                    pojo.setRefid(vector.get(1).toString());
                    pojo.setName(vector.get(2).toString());
                    pojo.setDate(dmy.format(ymdTwo.parse(vector.get(5).toString())));

                    list.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public String saveBasicIncrementAmtAction(String area, String applicableDt, String basicIncrement, String userName) throws ApplicationException {
        int updateQuery;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertQuery = em.createNativeQuery("INSERT INTO basicsalincrement (Area, ApplicableDt, BasicIncrementAmt, enter_by, trantime) "
                    + "VALUES ('" + area + "', '" + applicableDt + "', " + basicIncrement + ", '" + userName + "', now())").executeUpdate();
            if (insertQuery <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }
            Integer detail = 0;

            if (area.equalsIgnoreCase("All")) {
                detail = em.createNativeQuery("INSERT INTO share_holder_his (Regfoliono,Fname,Lname,Dob,Fathername,Houseno,Sector,City,Phone,"
                        + "Permadd,Witname1,Witadd1,Witname2,Witadd2,Nomname,Nomadd,Nomage,Nomrelation,zipcode,Comments,reg_folio,Operationmode,JtName1,"
                        + "JtName2,Enterby,Trantime,Regdate,lastupdatedt,lastupdateby,ShareType,applicationNo,purpose,relatedACNo,buss_desig,firmName,"
                        + "age,AlphaCode,authflag,AuthBy,area,CATEGORY,TITLE,custId,JtId1,JtId2,Jdate,Salary,gradePay,BeneficiaryAccNo,BeneficiaryName,IFSCCode,BankName,NomDob) "
                        + "select * from share_holder").executeUpdate();
            } else {
                detail = em.createNativeQuery("INSERT INTO share_holder_his (Regfoliono,Fname,Lname,Dob,Fathername,Houseno,Sector,City,Phone,"
                        + "Permadd,Witname1,Witadd1,Witname2,Witadd2,Nomname,Nomadd,Nomage,Nomrelation,zipcode,Comments,reg_folio,Operationmode,JtName1,"
                        + "JtName2,Enterby,Trantime,Regdate,lastupdatedt,lastupdateby,ShareType,applicationNo,purpose,relatedACNo,buss_desig,firmName,"
                        + "age,AlphaCode,authflag,AuthBy,area,CATEGORY,TITLE,custId,JtId1,JtId2,Jdate,Salary,gradePay,BeneficiaryAccNo,BeneficiaryName,IFSCCode,BankName,NomDob) "
                        + "select * from share_holder where area = '" + area + "'").executeUpdate();
            }

            if (detail <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in saving Share Holder details");
            }
            if (area.equalsIgnoreCase("All")) {
                updateQuery = em.createNativeQuery("update share_holder set gradepay = (Salary*" + basicIncrement + ")/100,lastupdatedt= curdate(),lastupdateby = '" + userName + "'  ").executeUpdate();
            } else {
                updateQuery = em.createNativeQuery("update share_holder set gradepay = (Salary*" + basicIncrement + ")/100,lastupdatedt= curdate(),lastupdateby = '" + userName + "' where area = '" + area + "'").executeUpdate();
            }
            if (updateQuery <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Updation");
            }
            ut.commit();
            return "Data has been Updated successfully !";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }
}
