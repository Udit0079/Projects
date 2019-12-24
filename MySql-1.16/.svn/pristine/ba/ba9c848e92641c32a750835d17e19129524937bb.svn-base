/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
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
@Stateless(mappedName = "HoCircularDetails")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class HoCircularDetails implements HoCircularDetailsRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public String saveData(String cirNo, String cirDetails) throws ApplicationException {
        String result = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select max(sno)+1 from hocirculardetails ").getResultList();
            if (!list.isEmpty()) {
                Vector element = (Vector) list.get(0);
                int serialNo = Integer.parseInt(element.get(0).toString());
                Query insert = em.createNativeQuery("insert into hocirculardetails(sno,DATE,details) values('" + serialNo + "','" + cirNo + "','" + cirDetails + "')");
                int insertResult = insert.executeUpdate();
                if (insertResult <= 0) {
                    ut.rollback();
                    result = "Error!! Data not been saved."; 
                }else {
                ut.commit();
                 result = "Data has been saved.";
            }
            }            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
            } catch (Exception e1) {
            }
            throw new ApplicationException(e);
        }
        return result;
    }

    public List selectRecord() throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select sno,date,details from hocirculardetails").getResultList();
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
