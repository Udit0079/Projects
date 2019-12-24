    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho;

import com.cbs.exception.ApplicationException;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "HoExtractEntryBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class HoExtractEntryBean implements HoExtractEntryRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public String save(String originBranch, String respondingBranch, String date, String originDate, String respondDate, String instno, Float amount, Integer ty, String enterBy, String authBy, String details, String entryType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("insert into ho_extract (OriginBranch,RespondBranch,Dt,OriginDt,RespondDt,InstrumentType,Instno,Amount,Ty,"
                    + "Trantime,EnterBy,Auth,AuthBy,LastUpdateBy,LastUpdateDt,ReconFlag,Detail,Entry_Type) values ('" + originBranch + "','" + respondingBranch + "','" + date
                    + "','" + originDate + "','" + respondDate + "',0,'" + instno + "'," + amount + "," + ty + ",now(),'" + enterBy + "','y','" + authBy + "',null,null,'N','" + details
                    + "','" + entryType + "')");
            Integer rowAffected = q.executeUpdate();
            if (rowAffected > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
