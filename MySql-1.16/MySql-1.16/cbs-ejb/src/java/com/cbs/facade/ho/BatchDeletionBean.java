package com.cbs.facade.ho;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author Navneet Goyal
 */
@Stateless(mappedName = "BatchDeletionBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class BatchDeletionBean implements BatchDeletionRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public List getBatchNoForDeletion() throws ApplicationException {
        List resultList = new ArrayList();
        try {
            Query q1 = em.createNativeQuery("select distinct a.trsno, a.dt from gl_recon a , gltable b where b.acno = a.acno  and (a.auth is null or a.auth='N') and a.trantype in (2,8) order by a.trsno");
            resultList = q1.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

//AcTab(0, 0) = "Recon"
//AcTab(1, 0) = "CA_Recon"
//AcTab(2, 0) = "Loan_Recon"
//AcTab(3, 0) = "RDRecon"
//AcTab(4, 0) = "DDSTransaction"
    public List getBatchData(int batchNo, String tempBd) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            Query selectFromRecon = em.createNativeQuery("select a.acno, b.custname, a.ty,a.dt, a.dramt,a.cramt,a.trsno,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby, a.recno, 'Recon' from recon a, accountmaster b where (a.auth is null or a.auth='N') and a.trantype in (2,8) and b.acno = a.acno and a.dt = '" + tempBd + "' and a.trsno = " + batchNo);
            resultList.addAll(selectFromRecon.getResultList());

            Query selectFromCA_Recon = em.createNativeQuery("select a.acno, b.custname, a.ty,a.dt, a.dramt,a.cramt,a.trsno,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby, a.recno, 'ca_recon' from ca_recon a, accountmaster b where (a.auth is null or a.auth='N') and a.trantype in (2,8) and b.acno = a.acno and a.dt = '" + tempBd + "' and a.trsno = " + batchNo);
            resultList.addAll(selectFromCA_Recon.getResultList());

            Query selectFromLoan_Recon = em.createNativeQuery("select a.acno, b.custname, a.ty,a.dt, a.dramt,a.cramt,a.trsno,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby, a.recno, 'Loan_Recon' from loan_recon a, accountmaster b where (a.auth is null or a.auth='N') and a.trantype in (2,8) and b.acno = a.acno and a.dt = '" + tempBd + "' and a.trsno = " + batchNo);
            resultList.addAll(selectFromLoan_Recon.getResultList());

            Query selectFromRDRecon = em.createNativeQuery("select a.acno, b.custname, a.ty,a.dt, a.dramt,a.cramt,a.trsno,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby, a.recno, 'RDRecon' from rdrecon a, accountmaster b where (a.auth is null or a.auth='N') and a.trantype in (2,8) and b.acno = a.acno and a.dt = '" + tempBd + "' and a.trsno = " + batchNo);
            resultList.addAll(selectFromRDRecon.getResultList());

            Query selectFromDDSTransaction = em.createNativeQuery("select a.acno, b.custname, a.ty,a.dt, a.dramt,a.cramt,a.trsno,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby,a.recno , 'DDSTransaction' from ddstransaction a, accountmaster b where (a.auth is null or a.auth='N') and a.trantype in (2,8) and b.acno = a.acno and a.dt = '" + tempBd + "' and a.trsno = " + batchNo);
            resultList.addAll(selectFromDDSTransaction.getResultList());

            Query selectFromGL_RECON_GLTABLE = em.createNativeQuery("select a.acno,b.acname 'Custname' ,a.ty,a.dt,a.dramt,a.cramt,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby,a.recno,'GL_Recon' 'TableName' from gl_recon a , gltable b where b.acno = a.acno  and (a.auth is null or a.auth='N') and a.trantype in (2, 8)and a.dt = '" + tempBd + "' and a.trsno = " + batchNo);
            resultList.addAll(selectFromGL_RECON_GLTABLE.getResultList());

            Query selectFromTD_RECON_TD_AccountMaster = em.createNativeQuery("select a.acno,b.Custname ,a.ty,a.dt,a.dramt,a.cramt,a.details,a.iy,a.payby,a.instno,a.trandesc,a.enterby,a.recno,intflag,'TD_Recon' 'TableName' from td_recon a , td_accountmaster b where b.acno = a.acno and a.dt = '" + tempBd + "' and (a.auth is null or a.auth='N') and a.trantype in (2,8) and a.trsno = " + batchNo + " and a.closeflag is null AND (((voucherno is null or voucherno=0)  AND intflag not in ('I','T')) OR (voucherno is not null AND intflag in ('T') AND trandesc=10))");
            resultList.addAll(selectFromTD_RECON_TD_AccountMaster.getResultList());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public String getBalanceForAcNature(String accountNo) throws ApplicationException {
        List result = new ArrayList();
        String balance = "0";
        try {
            result = em.createNativeQuery("select ACCTNATURE FROM accounttypemaster WHERE ACCTCODE=SUBSTRING('" + accountNo + "',3,2)").getResultList();
            Vector resultV = (Vector) result.get(0);
            String acCode = resultV.get(0).toString();
            if (acCode.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                result = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM ca_reconbalan WHERE ACNO ='" + accountNo + "'").getResultList();
                if (result.isEmpty()) {
                    balance = "0";
                } else {
                    Vector ele = (Vector) result.get(0);
                    balance = ele.get(0).toString();
                }
            } else if (acCode.equalsIgnoreCase(CbsConstant.TD_AC) || acCode.equalsIgnoreCase(CbsConstant.MS_AC)) {
                result = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM td_reconbalan WHERE ACNO ='" + accountNo + "'").getResultList();
                if (result.isEmpty()) {
                    balance = "0";
                } else {
                    Vector ele = (Vector) result.get(0);
                    balance = ele.get(0).toString();
                }
            } else {
                result = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM reconbalan WHERE ACNO ='" + accountNo + "'").getResultList();
                if (result.isEmpty()) {
                    balance = "0";
                } else {
                    Vector ele = (Vector) result.get(0);
                    balance = ele.get(0).toString();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return balance;
    }

    /**
     * *********************Method for HO GLB Insert**************************
     */
    public String hoGlbInsert(String array[], String userName) throws ApplicationException {
        String msg = "";
        Double liaAmt = 0.0d;
        Double assAmt = 0.0d;
        String orgnId = "";
        String date1 = "";
        try {
            for (int i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, p = 7; p < array.length; i = i + 8, j = j + 8, k = k + 8, l = l + 8, m = m + 8, n = n + 8, o = o + 8, p = p + 8) {
                if (array[l].equalsIgnoreCase("L")) {
                    liaAmt = liaAmt + Double.parseDouble(array[j]);
                } else if (array[l].equalsIgnoreCase("A")) {
                    assAmt = assAmt + Double.parseDouble(array[j]);
                }
                if (!array[m].equals("")) {
                    orgnId = array[m].substring(0, 2);
                }
                date1 = array[n];
            }
            NumberFormat formatter = new DecimalFormat("#0.00");
            double comp = 0.1d;
            System.out.println("Liability   " + liaAmt);
            System.out.println("Asset   " + assAmt);
            UserTransaction ut = context.getUserTransaction();
            if (Math.abs(liaAmt - assAmt) < comp || formatter.format(liaAmt).equalsIgnoreCase(formatter.format(assAmt))) {
                List resultList = em.createNativeQuery("SELECT ALPHACODE FROM cbs_branchmaster WHERE BRNCODE=CAST('" + orgnId + "' AS unsigned)").getResultList();
                String alphaCode = "";
                if (!resultList.isEmpty()) {
                    Vector vec = (Vector) resultList.get(0);
                    alphaCode = vec.get(0).toString();
                }
                ut.begin();
                em.createNativeQuery("INSERT INTO glb_recon1_his(org_brnid,acno,dt,dramt,cramt,ty,trantype,trandesc,trsno,instno,payby,iy,tokenno,details,trantime,Auth,enterby,authby,UpDateby)SELECT org_brnid,acno,dt,dramt,cramt,ty,trantype,trandesc,trsno,instno,payby,iy,tokenno,details,TranTime,Auth,enterby,authby,'" + userName + "' FROM glb_recon WHERE org_brnid='" + alphaCode + "' AND dt='" + date1 + "'").executeUpdate();
                em.createNativeQuery("DELETE from glb_recon WHERE org_brnid= '" + alphaCode + "' AND dt='" + date1 + "'").executeUpdate();
                for (int i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, p = 7; p < array.length; i = i + 8, j = j + 8, k = k + 8, l = l + 8, m = m + 8, n = n + 8, o = o + 8, p = p + 8) {
                    String glbFlatFileInsert = glbFlatFileInsert(array[m], array[n], Integer.parseInt(array[o]), Double.parseDouble(array[j]), array[p], userName);
                    if (glbFlatFileInsert.equalsIgnoreCase("false")) {
                        ut.rollback();
                        return msg = "Data was not inserted !";
                    }
                }
                ut.commit();
                return msg = "Data has been inserted successfully!";

            } else {
                msg = "Balance not matched so, data was not inserted";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private String glbFlatFileInsert(String accno, String date, int ty, double amount, String enterBy, String authBy) throws ApplicationException {
        String acno = accno.substring(0, 2) + "GL" + accno.substring(4, 12);
        String brncode = acno.substring(0, 2);
        List alphaList = em.createNativeQuery("SELECT ALPHACODE FROM cbs_branchmaster WHERE BRNCODE=CAST('" + brncode + "' AS unsigned)").getResultList();
        String orgnAlphaCode = "";
        if (!alphaList.isEmpty()) {
            Vector vec = (Vector) alphaList.get(0);
            orgnAlphaCode = vec.get(0).toString();
        }
        boolean fflag = false;
//        List maxRecnoList = em.createNativeQuery("SELECT ifnull(MAX(recno),1) FROM reconvmast").getResultList();
//        Vector vect = (Vector) maxRecnoList.get(0);
//        float maxRecno = Float.parseFloat(vect.get(0).toString());

//        List maxTrsnoList = em.createNativeQuery("SELECT ifnull(MAX(trsno),1) FROM reconvmast_trans").getResultList();
//        Vector vect1 = (Vector) maxTrsnoList.get(0);
//        float maxTrsno = Float.parseFloat(vect1.get(0).toString());

        List resultList1 = em.createNativeQuery("SELECT org_brnid,dt,trsno FROM glb_recon").getResultList();
        float transNum = 0.0f;
        if (!resultList1.isEmpty()) {
            fflag = true;
            List resultList2 = em.createNativeQuery("select ifnull(MAX(trsno),0) FROM glb_recon").getResultList();
            if (!resultList2.isEmpty()) {
                Vector vecct = (Vector) resultList2.get(0);
                transNum = Float.parseFloat(vecct.get(0).toString());
            }
        }
        boolean flag7 = false;
        String acno1 = "99" + acno.substring(2, 10) + "01";
        double drAmt = 0.0;
        double crAmt = 0.0;
        int tranType = 5;
        float trsno = 0.0f;
        if (ty == 1) {
            drAmt = amount;
            crAmt = 0.0;
        } else if (ty == 0) {
            crAmt = amount;
            drAmt = 0.0;
        }
        if (fflag == false) {
            List maxTrsnoList = em.createNativeQuery("SELECT ifnull(MAX(trsno),1) FROM reconvmast_trans").getResultList();
            Vector vect1 = (Vector) maxTrsnoList.get(0);
            float maxTrsno = Float.parseFloat(vect1.get(0).toString());

            trsno = maxTrsno + 1;
        } else {
            trsno = transNum + 1;
        }
        if ((crAmt == 0.0) && (drAmt == 0.0)) {
            flag7 = true;
        }
        try {
            int executeUpdate = em.createNativeQuery("INSERT INTO glb_recon(org_brnid,acno,dt,ty,dramt,cramt,trantype,"
                    + "trandesc,trsno,enterby,auth) VALUES('" + orgnAlphaCode + "','" + acno1 + "','" + date + "'," + ty + ","
                    + "" + drAmt + "," + crAmt + "," + tranType + ",0," + trsno + ",'" + enterBy + "','Y')").executeUpdate();
            if (executeUpdate <= 0) {
                return "false";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "true";
    }
}
