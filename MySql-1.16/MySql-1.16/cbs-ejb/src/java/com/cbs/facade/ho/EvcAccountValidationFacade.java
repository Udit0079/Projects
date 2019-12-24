/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho;

import com.cbs.exception.ApplicationException;
import com.cbs.utils.CbsUtil;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EvcAccountValidationFacade implements EvcAccountValidationFacadeRemote {

    @PersistenceContext
    private EntityManager em;

    @Resource
    EJBContext context;

    @Override
    public String logEvcAccountValidationReq(String evcUniqueReqId, String evcJsonReq) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            
            List dupList = em.createNativeQuery("select unique_req_id from cbs_evc_account_validate where unique_req_id='"+ evcUniqueReqId+"'").getResultList();
            if(!dupList.isEmpty()) return "EF-WS-SEC-ERR-10004";
            ut.begin();
            Query q = em.createNativeQuery("INSERT INTO cbs_evc_account_validate (unique_req_id, evc_req, evc_res, req_time) "
                    + "VALUES ('" + evcUniqueReqId + "', '" + evcJsonReq + "', '', now())");
            Integer rowAffected = q.executeUpdate();
            if (rowAffected < 0) {
                throw new ApplicationException("Problem in request data logging.");
            }
            ut.commit();
            return "True";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }
    }
    
    
    @Override
    public String updateEvcAccountValidationRes(String evcUniqueReqId, String evcJsonRes) throws ApplicationException{
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("update cbs_evc_account_validate set evc_res = '"+ evcJsonRes + "', res_time = now() "
                    + " where unique_req_id = '" + evcUniqueReqId +"'");
            Integer rowAffected = q.executeUpdate();
            if (rowAffected < 0) {
                throw new ApplicationException( "Problem in response data updation.");
            }
            ut.commit();
            return "True";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }
    }
    
    
    @Override
    public List evcAccountValidate(String pan, String acno, String ifsc) throws ApplicationException{
        try {
            List ifscList = em.createNativeQuery("select brncode from branchmaster where ifsc_code='"+ ifsc+"'").getResultList();
            
            if(ifscList.isEmpty()) throw new ApplicationException("IFSC");
            String brnCode = "";
            if(ifscList.size() == 1){
                Vector ifscVec = (Vector) ifscList.get(0);
                brnCode = CbsUtil.lPadding(2,Integer.parseInt(ifscVec.get(0).toString()));
            }else{
                brnCode = acno.substring(0, 2);
            }
            List rsList = em.createNativeQuery("select new_ac_no from cbs_acno_mapping where new_ac_no='" + acno + "' or old_ac_no ='" + acno + "'").getResultList();    
            if(rsList.isEmpty()) throw new ApplicationException("ACCOUNT");
            
            rsList = em.createNativeQuery("select customerid,custfullname,mobilenumber,mail_email, am.AccStatus from cbs_customer_master_detail cb, customerid ci, "
                    + "accountmaster am  where cast(customerid as unsigned)  = ci.custid and am.acno = ci.acno and pan_girnumber ='" + pan + "' and ci.acno='" 
                    + acno + "' and substring(am.acno,1,2) = '" + brnCode +"'").getResultList();
            
            if(rsList.isEmpty()) {
                rsList = em.createNativeQuery("select customerid,custfullname,mobilenumber,mail_email,am.AccStatus from cbs_customer_master_detail cb, accountmaster am " 
                        +" where (customerid = am.custid1 || customerid = am.custid2 || customerid = am.custid3 || customerid = am.custid4) "
                        + "and pan_girnumber ='" + pan + "' and am.acno='"+ acno +"' and am.curbrcode = '" + brnCode + "'").getResultList();
                if(rsList.isEmpty()) throw new ApplicationException("PAN");
            }
            return rsList;
        } catch (ApplicationException | NumberFormatException e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
