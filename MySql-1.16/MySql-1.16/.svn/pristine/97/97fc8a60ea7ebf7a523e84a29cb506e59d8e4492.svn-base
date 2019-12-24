/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author Deepshikha
 */
@Stateless(mappedName = "ExpenditureLimitFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ExpenditureLimitFacade implements ExpenditureLimitFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public List loadBranchNames() {
        List selectBranchNames = em.createNativeQuery("select bm.brncode,bm.alphacode from branchmaster bm,bnkadd bn where bm.alphacode = bn.alphacode ").getResultList();
        return selectBranchNames;
         }

    public String saveData(String userDate, String branchName, String amount) throws ApplicationException {
        String result = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            javax.persistence.Query insert = em.createNativeQuery("insert into expanddtl values('" + userDate + "','" + amount + "','" + branchName + "')");
            int insertResult = insert.executeUpdate();
            if (insertResult <= 0) {
                ut.rollback();
                return result = "Error!! Data not been saved.";
            } else {
                ut.commit();
                return result = "Data has been saved successfully";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    public double getLimitDetails(String branchName) {
        double limit = 0d;
        try {
            Vector vtr;
            
            List l = em.createNativeQuery("select amount from expanddtl where brncode='" + branchName+ "'").getResultList();
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                limit = vtr.get(0) != null ? Double.parseDouble(vtr.get(0).toString()) : 0d;
            }
        } catch (Exception e) {
        }
        return limit;
    }

    public String updateData(String userDate, String brncode, String amount) throws ApplicationException {
       String result1 = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            javax.persistence.Query q = em.createNativeQuery("insert into expenditurelimit_history(DATE,brncode,amount) select Date,brncode,amount from expanddtl");
            int qresult = q.executeUpdate();
            if (qresult <= 0) {
                ut.rollback();
                return result1 = "Limit is saved!";
            }
            Query m = em.createNativeQuery("update expanddtl set amount='" + amount + "' where brncode='" + brncode+ "'");
            int mresult = m.executeUpdate();
            if (mresult <= 0) {
                ut.rollback();
                return result1 = "Limit is not updated!";
            } else {
                ut.commit();
                return result1 = "Limit is Updated Successfuly!";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
