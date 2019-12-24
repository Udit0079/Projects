/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.share;

import com.cbs.constant.CbsAcCodeConstant;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import com.cbs.exception.ApplicationException;
import java.util.Vector;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "ShareAuthorizationFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ShareAuthorizationFacade implements ShareAuthorizationFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List gridDetail() throws ApplicationException {
        List acList = new ArrayList();
        try {
            //acList = em.createNativeQuery("select distinct ifnull(s.fname,'') + ' ' + ifnull(s.lname,'') name, d.acno,d.txnid,d.cramt,d.dramt,d.trantime,d.enterby,d.auth,d.DETAILS  from share_holder s left join dividend_recon d on d.acno=s.REGFOLIONO where  d.auth='N' order by d.trantime desc").getResultList();
            acList = em.createNativeQuery("select distinct cc.custname,d.acno,d.txnid,d.cramt,d.dramt,d.trantime,d.enterby,d.auth,d.details  from  share_holder s left join cbs_customer_master_detail cc on cc.customerid = s.custid left join dividend_recon d on d.acno=s.regfoliono where  d.auth='N' order by d.trantime desc").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acList;
    }

    public String authorizeAction(List list, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            if ((list == null) || (list.isEmpty()) || enterBy == null) {
                ut.rollback();
                return "Please Double Click On The Auth Column To Authorize Entry.";
            }
            for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7, i = 8; a < list.size(); a = a + 9, b = b + 9, c = c + 9, d = d + 9, e = e + 9, f = f + 9, g = g + 9, h = h + 9, i = i + 9) {
                String txnid = list.get(c).toString();
                Integer acctCloseHis = em.createNativeQuery("Update dividend_recon Set AuthBy='" + enterBy + "' , Auth='Y'  Where txnid='" + txnid + "'").executeUpdate();
                if (acctCloseHis <= 0) {
                    ut.rollback();
                    return "DATA NOT updated";
                }
            }
            ut.commit();
            return "Authorization Successful";
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

    public String deleteDividendAction(String txnId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer reconbalan = em.createNativeQuery("Delete from dividend_recon where txnid='" + txnId + "'").executeUpdate();
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

    /****************************  ShareCertificateIssueAuthorizationBean ***************************/
    public List shareIssuegridDetail() throws ApplicationException {
        List acList = new ArrayList();
        try {
            List accList = em.createNativeQuery("Select * From certificate_share Where (ifnull(Auth,'N')='N' or ifnull(Auth,'N')='')").getResultList();
            if (accList.size() > 0) {
                acList = em.createNativeQuery("select distinct sh.regfoliono,cc.custname,cs.certificateno,cs.issuedt,cs.issuedby,cs.auth from certificate_share cs,share_holder sh,"
                        + "share_capital_issue si,cbs_customer_master_detail cc where sh.regfoliono=si.foliono and si.sharecertno=cs.certificateno and cc.customerid = sh.custid "
                        + "and (ifnull(cs.auth,'N')='N' or ifnull(cs.auth,'N')='') order by sh.regfoliono,cs.issuedt").getResultList();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acList;
    }

    public String shareCertificateAuthorization(int certNo, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            List tmpseries = em.createNativeQuery("Select * From certificate_share Where CertificateNo=" + certNo + " and (ifnull(Auth,'N')='N' or Auth='')").getResultList();
            if (tmpseries.size() > 0) {
                Integer acctCloseHis = em.createNativeQuery("Update certificate_share Set AuthBy='" + enterBy + "' , Auth='Y'  Where CertificateNo=" 
                        + certNo + " and (ifnull(Auth,'N')='N' or Auth='') ").executeUpdate();

                Integer Certificate = em.createNativeQuery("Update share_capital_issue Set AuthBy='" + enterBy + "' , Auth='Y'  Where shareCertNo=" 
                        + certNo + " and (ifnull(Auth,'N')='N' or Auth='')").executeUpdate();
                if (acctCloseHis > 0 || Certificate > 0) {
                    result = "Authorization Successful";
                    List result1 = em.createNativeQuery("select shareno from share_capital_issue  Where sharecertno='" + certNo + "' order by shareno").getResultList();
                    if (!result1.isEmpty()) {
                        Vector vtr = (Vector) result1.get(0);
                        Vector vtr1 = (Vector) result1.get(result1.size() - 1);
                        result = result + " for cetificate no  " + certNo + " and share no from  " + vtr.get(0).toString() + " to " + vtr1.get(0).toString();
                    }
                } else {
                    throw new ApplicationException("Problem in saving data.");
                }
            }
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

    /*************************End ****************************/
    /********************************* share issue authorization *********************/
    public List getPendingShares(String orgBrCode) throws ApplicationException{
        List acList = new ArrayList();
        try {
            String acno = orgBrCode + CbsAcCodeConstant.SF_AC.getAcctCode()+"00000001";
            acList = em.createNativeQuery("select distinct txnNo from share_capital_issue where foliono = '"+acno+"' and auth='N'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acList;
    }
    
    public List shareIssuedDetails(long txnNo, String issueDt) throws ApplicationException {
        List acList = new ArrayList();
        try {
            acList = em.createNativeQuery("select min(shareno), max(shareno),issuedby, DATE_FORMAT(issuedt,'%d/%m/%Y'), auth from share_capital_issue "
                    + "where txnNo='" + txnNo + "' and auth='N' and dt='"+issueDt+"' group by issuedby, issuedt, auth").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acList;
    }

    public String shareIssueAuthorizeAction(long shareNoFrom, long shareNoTo, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            Integer rs = em.createNativeQuery("Update share_capital_issue Set AuthBy='" + enterBy + "' , Auth='Y', LastUpdateBy ='" + enterBy + "', "
                    + "lastupdatetime = now() Where shareno between " + shareNoFrom + " and "+ shareNoTo + " and Auth='N'").executeUpdate();
            if(rs<=0){
                throw new ApplicationException("Problem in Share issue Authorization");
            }
            ut.commit();
            return "True";

        } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (    IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    
    @Override
    public String deleteUnAuthShares(long shareNoFrom, long shareNoTo) throws ApplicationException{
         UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer rs = em.createNativeQuery("delete from share_capital_issue Where shareno between " + shareNoFrom + " and "+ shareNoTo + " and Auth='N'").executeUpdate();
            if(rs<=0){
                throw new ApplicationException("Problem in Share deletion");
            }
            ut.commit();
            return "True";
        } catch (ApplicationException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }
    /************************** End *********************/
    /******************************** share transfer *************************/
    public List shareTransferGridDetail() throws ApplicationException {
        List acList = new ArrayList();
        try {
            acList = em.createNativeQuery("Select * From share_transfer Where (ifnull(Auth,'N')='N' or ifnull(Auth,'N')='') Order by trfDt").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acList;
    }

    public String shareTransferAuthorizeAction(List list, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            if ((list == null) || (list.isEmpty()) || enterBy == null) {
                ut.rollback();
                result = "Please Double Click On The Auth Column To Authorize Entry.";
                return result;
            }

            for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7, i = 8, j = 9, k = 10; a < list.size(); a = a + 11, b = b + 11, c = c + 11, d = d + 11, e = e + 11, f = f + 11, g = g + 11, h = h + 11, i = i + 11, j = j + 11, k = k + 11) {
                String certNo = list.get(b).toString();
                List tmpseries = em.createNativeQuery("Select * From share_transfer Where CertNo=" + Integer.parseInt(certNo) + " and (ifnull(Auth,'N')='N' or ifnull(Auth,'N')='')").getResultList();
                if (tmpseries.size() > 0) {
                    Integer acctCloseHis = em.createNativeQuery("Update share_transfer Set AuthBy='" + enterBy + "' , Auth='Y' Where CertNo=" + Integer.parseInt(certNo) + " and (ifnull(Auth,'N')='N' or ifnull(Auth,'N')='')").executeUpdate();
                    if (acctCloseHis <= 0) {
                        ut.rollback();
                        result = "Authorization not completed";
                        return result;
                    } else {
                        result = "Authorization Successful";
                        // return result;
                    }
                }
            }
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
    /*********************************** End ******************************/
}
