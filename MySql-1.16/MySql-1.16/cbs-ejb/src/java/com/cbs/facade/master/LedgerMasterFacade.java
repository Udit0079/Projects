package com.cbs.facade.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "LedgerMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LedgerMasterFacade implements LedgerMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;

    public List getGlTable(String fromCode, String toCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select DISTINCT substring(AcNo,3,10),AcName from gltable where substring(acno,5,6) >= RIGHT(concat('00000','" + fromCode + "'),6) and substring(acno,5,6) <= RIGHT(concat('00000','" + toCode + "'),6) order by 1").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String cbsSaveGeneralLedger(String acno, String acnoName, String acType, String BrnCode, String Status) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = "System error occured";
            String st = "";
            List resultlist = em.createNativeQuery("select substring(acno,3,10) from gltable where acno='" + acno + "'").getResultList();
            if (resultlist.size() > 0) {
                ut.rollback();
                return "GL Head Already Exists";
            } else {
                List brnList = em.createNativeQuery("Select brncode from branchmaster").getResultList();
                for (int i = 0; i < brnList.size(); i++) {
                    Vector brnVector = (Vector) brnList.get(i);
                    String brnCd = brnVector.get(0).toString();
                    String glNo = brnCd + acno;
                    Integer insertMasterList = em.createNativeQuery("insert into gltable(acno,acname,postflag,msgflag) values('" + glNo + "','" + acnoName + "',10,99)").executeUpdate();
                    if (insertMasterList <= 0) {
                        ut.rollback();
                        msg = "Data has not saved in gltable";
                        return msg;
                    }
                }
            }
            ut.commit();
            msg = "Data Saved Successfully";
            return msg;
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

    public String cbsUpdateGeneralLedger(String glHead, String BrnCode, String Status) throws ApplicationException {
        String msg = "System error occured";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (BrnCode.equalsIgnoreCase("A")) {
                Integer updateResult = em.createNativeQuery("update gltable set STATUS = '" + Status + "' "
                        + "where substring(acno,3,10) = '" + glHead + "'").executeUpdate();
                if (updateResult <= 0) {
                    ut.rollback();
                    return msg = "Updation failed due to system error.";
                }
            } else {
                int length = BrnCode.length();
                int addedZero = 2 - length;
                for (int j = 0; j < addedZero; j++) {
                    BrnCode = "0" + BrnCode;
                }
                String glAcno = BrnCode + glHead;
                Integer updateResult = em.createNativeQuery("update gltable set STATUS = '" + Status + "' "
                        + "where acno = '" + glAcno + "'").executeUpdate();
                if (updateResult <= 0) {
                    ut.rollback();
                    return msg = "Updation failed due to system error.";
                }
            }
            ut.commit();
            return msg = "Data updated successfully.";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getGlDesc() throws ApplicationException {
        try {
            return em.createNativeQuery("select Code,GlName from gl_desc_range order by Code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBranch() throws ApplicationException {
        try {
            return em.createNativeQuery("select branchName,brncode from branchmaster order by brncode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getRange(int pCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select fromNo,ToNo from gl_desc_range where code = " + pCode + "").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getGlDescData() throws ApplicationException {
        try {
            return em.createNativeQuery("Select Code,Glname,FromNo,ToNo From gl_desc_range order by Code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String SaveData(String glDesc, int fromNo, int toNo, String Enterby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkFrlist = em.createNativeQuery("SELECT FROMNO,TONO FROM gl_desc_range WHERE " + fromNo + " BETWEEN FROMNO AND TONO").getResultList();
            if (!chkFrlist.isEmpty()) {
                ut.rollback();
                return "G.L. From Range Already Defined";
            } else {
                List chkTolist = em.createNativeQuery("SELECT FROMNO,TONO FROM gl_desc_range WHERE " + toNo + " BETWEEN FROMNO AND TONO").getResultList();
                if (!chkTolist.isEmpty()) {
                    ut.rollback();
                    return "G.L. To Range Already Defined";
                } else {
                    List resultlist = em.createNativeQuery("select ifnull(max(code),0) from gl_desc_range").getResultList();
                    Vector secLst = (Vector) resultlist.get(0);
                    int mCode = Integer.parseInt(secLst.get(0).toString()) + 1;
                    Integer upadteMasterList = em.createNativeQuery("insert into gl_desc_range(Code,Glname,FROMNO,TONO,RecordCreaterID,CreationTime,TS_CNT) values(" + mCode + ",'" + glDesc + "'," + fromNo + "," + toNo + ",'" + Enterby + "',CURRENT_TIMESTAMP,0)").executeUpdate();
                    if (upadteMasterList <= 0) {
                        ut.rollback();
                        return "Problem In Insertion";
                    } else {
                        ut.commit();
                        return "Data Saved Successfully";
                    }
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

    public String updateData(int mCode, String glDesc, int fromNo, int toNo, String UpdateBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List resultlist = em.createNativeQuery("select * from glTable where cast(substring(acno,5,6) as int) between " + fromNo + " and " + toNo + "").getResultList();
            if (resultlist.isEmpty()) {
                int chCode = 0;
                int chCodeT = 0;
                List chkFrlist = em.createNativeQuery("SELECT CODE,FROMNO,TONO FROM gl_desc_range WHERE " + fromNo + " BETWEEN FROMNO AND TONO").getResultList();
                if (!chkFrlist.isEmpty()) {
                    Vector codeLst = (Vector) chkFrlist.get(0);
                    chCode = Integer.parseInt(codeLst.get(0).toString());
                }
                if ((!chkFrlist.isEmpty()) && (mCode != chCode)) {
                    ut.rollback();
                    return "G.L. From Range Already Defined";
                } else {
                    List chkTolist = em.createNativeQuery("SELECT CODE,FROMNO,TONO FROM gl_desc_range WHERE " + toNo + " BETWEEN FROMNO AND TONO").getResultList();
                    if (!chkTolist.isEmpty()) {
                        Vector codeTLst = (Vector) chkTolist.get(0);
                        chCodeT = Integer.parseInt(codeTLst.get(0).toString());
                    }
                    if ((!chkTolist.isEmpty()) && (mCode != chCodeT)) {
                        ut.rollback();
                        return "G.L. To Range Already Defined";
                    } else {
                        List countlist = em.createNativeQuery("select TS_CNT from gl_desc_range where Code =" + mCode + "").getResultList();
                        Vector secLst = (Vector) countlist.get(0);
                        int count = Integer.parseInt(secLst.get(0).toString()) + 1;

                        Integer upadteMasterList = em.createNativeQuery("update gl_desc_range set glname ='" + glDesc + "',FROMNO =" + fromNo + ",TONO=" + toNo + ",LastChangeUserid='" + UpdateBy + "',LastChangeTime=CURRENT_TIMESTAMP, TS_CNT =" + count + " where code = " + mCode + "").executeUpdate();
                        if (upadteMasterList <= 0) {
                            ut.rollback();
                            return "Problem In Updation";
                        } else {
                            ut.commit();
                            return "Data Update Successfully";
                        }
                    }
                }
            } else {
                ut.rollback();
                return "Gl Head Already Open On this Range";
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

    public List loadGrdTransaction(String fromAcno, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,date_format(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,date_format(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,date_format(expirydt,'%d/%m/%Y') AS expirydt from standins_transmaster where SUBSTRING(FROMACNO,1,2)='" + brCode + "' and fromacno='" + fromAcno + "' order by InsTrNo,sno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List loadGrdTransaction1(String fromAcno, float instNo, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,date_format(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,date_format(entrydate,'%d/%m/%Y') AS entrydate,chargeflag,date_format(expirydt,'%d/%m/%Y') AS expirydt from standins_transmaster where SUBSTRING(FROMACNO,1,2)='" + brCode + "' and fromacno='" + fromAcno + "'and instrno=" + instNo + " order by InsTrNo,sno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List loadGrdGeneralData(String fromAcno, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno,sno,instrno,date_format(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,date_format(entrydate,'%d/%m/%Y') AS entrydate from standins_generalmaster where SUBSTRING(acno,1,2)='" + brCode + "' and acno='" + fromAcno + "' order by InsTrNo,sno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List loadGrdGeneralData1(String fromAcno, float instNo, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno,sno,instrno,date_format(effdate,'%d/%m/%Y') AS effdate,status,remarks,enterby,date_format(entrydate,'%d/%m/%Y') AS entrydate from standins_generalmaster where SUBSTRING(acno,1,2)='" + brCode + "' and acno='" + fromAcno + "' and instrno=" + instNo + " order by InsTrNo,sno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteTransactionData(String fromAcno, float instrNo, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List status = em.createNativeQuery("select status from standins_transmaster where SUBSTRING(FROMACNO,1,2)='" + brCode + "' and fromacno='" + fromAcno + "' and instrno='" + instrNo + "'").getResultList();
            List statusTrans = em.createNativeQuery("select status from standins_transexecuted where SUBSTRING(FROMACNO,1,2)='" + brCode + "' and fromacno='" + fromAcno + "' and instrno='" + instrNo + "'").getResultList();
            String delFlag = "False";
            if (!statusTrans.isEmpty()) {
                ut.rollback();
                return "Can't Delete,Already Executed Instructions";
            } else {
                if (!status.isEmpty()) {
                    Vector statuss = (Vector) status.get(0);
                    String statusins = statuss.get(0).toString();
                    if (!statusins.equals("UNEXECUTED")) {
                        ut.rollback();
                        delFlag = "False";
                        return "Can't Delete,Already Executed Instructions";
                    } else {
                        delFlag = "True";
                    }
                }
            }
            if (delFlag.equals("True")) {
                Query updateQuery = em.createNativeQuery("Delete From standins_transmaster where SUBSTRING(FROMACNO,1,2)='" + brCode + "' and fromacno='" + fromAcno + "' and instrno='" + instrNo + "'");
                int var = updateQuery.executeUpdate();
                ut.commit();
                return "Data Deleted Successfully All Instructions For -> " + fromAcno + "   Having Instruction No. " + instrNo;
            } else {
                ut.rollback();
                return "Data could not be deleted";
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

    public String deleteGeneralTableData(String acno, float instrNo, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List status = em.createNativeQuery("select status from standins_generalmaster where SUBSTRING(acno,1,2)='" + brCode + "' and acno='" + acno + "' and instrno='" + instrNo + "'").getResultList();
            String delFlag = "False";
            if (!status.isEmpty()) {
                Vector statuss = (Vector) status.get(0);
                String statusins = statuss.get(0).toString();
                if (!statusins.equals("UNEXECUTED")) {
                    ut.rollback();
                    delFlag = "False";
                    return "Can't Delete,Already Executed Instructions";
                } else {
                    delFlag = "True";
                }
            }
            if (delFlag.equals("True")) {
                Query updateQuery = em.createNativeQuery("Delete From standins_generalmaster where SUBSTRING(acno,1,2)='" + brCode + "' and acno='" + acno + "' and instrno='" + instrNo + "'");
                int var = updateQuery.executeUpdate();
                ut.commit();
                return "Data Deleted Successfully All Instructions For -> " + acno + "   Having Instruction No. " + instrNo;
            } else {
                ut.rollback();
                return "Data could not be deleted";
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

    public String acnoLostFocus(String acno, String instType) throws ApplicationException {
        try {
            List status = null;
            String custname = null;
            String nature = fTSPosting43CBSBean.getAccountNature(acno);
            if (instType.equals("GENERAL")) {
                if ((nature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (nature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    status = em.createNativeQuery("select custname,accstatus from td_accountmaster where  acno='" + acno + "'").getResultList();
                } else {
                    status = em.createNativeQuery("select custname,accstatus from accountmaster where  acno='" + acno + "'").getResultList();
                }
                if (status.isEmpty()) {
                    return "Account Does Not Exist";
                } else {
                    Vector statuss = (Vector) status.get(0);
                    custname = statuss.get(0).toString();
                    String accstatus = statuss.get(1).toString();
                    if (accstatus.equals("9")) {
                        return "This Account Is Closed";
                    }
                    if (accstatus.equals("2")) {
                        return "This Account Is Inoperative";
                    }
                }
            }
            if (instType.equals("TRANSACTION")) {
                if ((nature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (nature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    status = em.createNativeQuery("select custname,accstatus from td_accountmaster where  acno='" + acno + "'").getResultList();
                } else {
                    status = em.createNativeQuery("select custname,accstatus from accountmaster where  acno='" + acno + "'").getResultList();
                }
                if (status.isEmpty()) {
                    return "Account Does Not Exist";
                } else {
                    Vector statuss = (Vector) status.get(0);
                    custname = statuss.get(0).toString();
                    String accstatus = statuss.get(1).toString();
                    if (accstatus.equals("9")) {
                        return "This Account Is Closed";
                    }
                    if (accstatus.equals("2")) {
                        return "This Account Is Inoperative";
                    }
                }
            }
            return custname;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String acctoLostFocus(String fromAcno, String toAcno, String instType) throws ApplicationException {
        try {
            String postFlegs = "";
            List status;
            String custname = null;
            String accstatus = null;
            if (instType.equals("TRANSACTION")) {
                if (toAcno == null) {
                    return "Account Does Not Exist";
                }
                if (fromAcno.equalsIgnoreCase(toAcno)) {
                    return "Instruction Cannot Be Given To same Account";
                }

                String nature = fTSPosting43CBSBean.getAccountNature(toAcno);
                String acCode = fTSPosting43CBSBean.getAccountCode(toAcno);
                if ((nature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (nature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    status = em.createNativeQuery("select custname,accstatus from td_accountmaster where  acno='" + toAcno + "'").getResultList();
                    if (status.isEmpty()) {
                        return "Account Does Not Exist";
                    } else {
                        Vector statuss = (Vector) status.get(0);
                        custname = statuss.get(0).toString();
                        accstatus = statuss.get(1).toString();
                    }
                } else if (acCode.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    List postFlag = em.createNativeQuery("select postflag,AcNo,AcName,MSGFLAG from gltable where  acno='" + toAcno + "'").getResultList();
                    if (postFlag.isEmpty()) {
                        return "Account Does Not Exist";
                    } else {
                        Vector postFlags = (Vector) postFlag.get(0);
                        String postFlgs = postFlags.get(0).toString();
                        custname = postFlags.get(2).toString();
                        if (postFlgs == null) {
                            postFlegs = "0";
                        } else {
                            postFlegs = postFlgs;
                        }
                    }
                } else {
                    status = em.createNativeQuery("select custname,accstatus from accountmaster where  acno='" + toAcno + "'").getResultList();
                    if (status.isEmpty()) {
                        return "Account Does Not Exist";
                    } else {
                        Vector statuss = (Vector) status.get(0);
                        custname = statuss.get(0).toString();
                        accstatus = statuss.get(1).toString();
                        if (accstatus.equals("9")) {
                            return "This Account Is Closed";
                        }
                        if (accstatus.equals("2")) {
                            return "This Account Is Inoperative";
                        }
                    }
                }
            }
            return custname;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveData(String insType, String effPeriod, String remarks, float amount, String fromAcno, String toAcno, String periodicity, String effectiveDate, int validityDays, String userName, String deductCharges, String brCode, String sNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String period2 = "1";
            int var2 = 0;
            int var3 = 0;
            int var4 = 0;
            String rightEffDate = null;
            String fullExpiryDate = null;
            float spflag = Float.parseFloat(effPeriod);
            float sp = Math.round(spflag);
            float instrNos = 0;
            String saveMessage = "Instruction Authorized successfully";
              
            List chkCode = em.createNativeQuery("SELECT * from standins_transmaster_auth where from_acno ='"+ fromAcno +"' "
                    + " and sno = '"+ sNo +"' and auth ='N'").getResultList();
            if(chkCode.isEmpty()){
                ut.rollback();
                return ": " + "Entry Not Exist May Be Deleted Or Authorized By Another User ";
            }
            
            Query updateResult = em.createNativeQuery("update standins_transmaster_auth set auth = 'Y',authby = '" + userName + "' "
                        + "where sno = '" + sNo + "'");
            var4 = updateResult.executeUpdate();
            
            String frNat = fTSPosting43CBSBean.getAccountNature(fromAcno);
            
            if(frNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || frNat.equalsIgnoreCase(CbsConstant.MS_AC)){
                List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from td_accountmaster where acno = '"+ fromAcno +"'").getResultList();
                Vector eVal = (Vector) fVal.get(0);
                int fInt = Integer.parseInt(eVal.get(0).toString());
                if(fInt <= 0){
                    ut.rollback();
                    return "Opening Date of " + fromAcno + " is Greater Than Effective Date";
                }
            } else {
                List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from accountmaster where acno = '"+ fromAcno +"'").getResultList();
                Vector eVal = (Vector) fVal.get(0);
                int fInt = Integer.parseInt(eVal.get(0).toString());
                if(fInt <= 0){
                    ut.rollback();
                    return "Opening Date of " + fromAcno + " is Greater Than Effective Date";
                }
            }
            
            if (insType.equals("TRANSACTION")) {
                String toNat = fTSPosting43CBSBean.getAccountNature(toAcno);
                if(toNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || toNat.equalsIgnoreCase(CbsConstant.MS_AC)){
                    List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from td_accountmaster where acno = '"+ toAcno +"'").getResultList();
                    Vector eVal = (Vector) fVal.get(0);
                    int fInt = Integer.parseInt(eVal.get(0).toString());
                    if(fInt <= 0){
                        ut.rollback();
                        return "Opening Date of " + toAcno + " is Greater Than Effective Date";
                    }
                }else{
                    List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from accountmaster where acno = '"+ toAcno +"'").getResultList();
                    Vector eVal = (Vector) fVal.get(0);
                    int fInt = Integer.parseInt(eVal.get(0).toString());
                    if(fInt <= 0){
                        ut.rollback();
                        return "Opening Date of " + toAcno + " is Greater Than Effective Date";
                    }
                }
                
                if (effPeriod == null) {
                    ut.rollback();
                    return "Please Enter The Effective Period";
                }
                if (remarks == null) {
                    ut.rollback();
                    return "Please Enter The remarks";
                }
                if (amount <= 0) {
                    ut.rollback();
                    return "Please Enter The large amount";
                }
                if (fromAcno == null) {
                    ut.rollback();
                    return "Please Enter The from Account No";
                }
                if (toAcno == null) {
                    ut.rollback();
                    return "Please Enter The To Account No";
                }
                if (periodicity.equals("MONTHLY")) {
                    periodicity = "1";
                } else if (periodicity.equals("QUARTERLY")) {
                    periodicity = "3";
                } else if (periodicity.equals("HALF-YEARLY")) {
                    periodicity = "6";
                } else if (periodicity.equals("YEARLY")) {
                    periodicity = "12";
                }
                period2 = periodicity;
                int period = Integer.parseInt(period2);
                List instrNo = em.createNativeQuery("select ifnull(max(instrno),0) from standins_transmaster where SUBSTRING(FROMACNO,1,2)='" + brCode + "'").getResultList();
                float a;
                if (instrNo.isEmpty()) {
                    a = 0;
                } else {
                    Vector inst = (Vector) instrNo.get(0);
                    String insts = inst.get(0).toString();
                    float instrN = Float.parseFloat(insts);
                    a = instrN;
                }
                List instrNo1 = em.createNativeQuery("select ifnull(max(instrno),0) from standins_transpending where SUBSTRING(FROMACNO,1,2)='" + brCode + "'").getResultList();
                float b;
                if (instrNo1.isEmpty()) {
                    b = 0;
                } else {
                    Vector inst1 = (Vector) instrNo1.get(0);
                    String insts1 = inst1.get(0).toString();
                    float instrN1 = Float.parseFloat(insts1);
                    b = instrN1;
                }
                List instrNo2 = em.createNativeQuery("select ifnull(max(instrno),0) from standins_transexecuted where SUBSTRING(FROMACNO,1,2)='" + brCode + "'").getResultList();
                float c;
                if (instrNo2.isEmpty()) {
                    c = 0;
                } else {
                    Vector inst2 = (Vector) instrNo2.get(0);
                    String insts2 = inst2.get(0).toString();
                    float instrN2 = Float.parseFloat(insts2);
                    c = instrN2;
                }
                List instrNo3 = em.createNativeQuery("select ifnull(max(instrno),0) from standins_transcancel where SUBSTRING(FROMACNO,1,2)='" + brCode + "'").getResultList();
                float d;
                if (instrNo3.isEmpty()) {
                    d = 0;
                } else {
                    Vector inst3 = (Vector) instrNo3.get(0);
                    String insts3 = inst3.get(0).toString();
                    float instrN3 = Float.parseFloat(insts3);
                    d = instrN3;
                }
                if (a >= b && a >= c && a >= d) {
                    instrNos = a + 1;
                } else if (b >= c && b >= d) {
                    instrNos = b + 1;
                } else if (c >= d) {
                    instrNos = c + 1;
                } else {
                    instrNos = d + 1;
                }
                int sno = 0;
                for (int z = 0; z < sp; z++) {
                    sno++;
                    List expDate = em.createNativeQuery("select DATE_FORMAT(DATE_ADD('" + effectiveDate + "', INTERVAL " + validityDays + " DAY),'%Y%m%d')").getResultList();
                    Vector expDates = (Vector) expDate.get(0);
                    fullExpiryDate = expDates.get(0).toString();
                    rightEffDate = effectiveDate;
                    List effDate = em.createNativeQuery("select DATE_FORMAT(DATE_ADD('" + rightEffDate + "', INTERVAL " + period + " MONTH),'%Y%m%d')").getResultList();
                    Vector effDates = (Vector) effDate.get(0);
                    effectiveDate = effDates.get(0).toString();
                    List lastUpdate = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
                    Vector ltUpdate = (Vector) lastUpdate.get(0);
                    String currentDate = ltUpdate.get(0).toString();
                    Query insertQuery2 = em.createNativeQuery("insert into standins_transmaster(instrNo,sNo,fromAcno,toAcno,amount,effDate,status,remarks,enterBy,entryDate,chargeFlag,expiryDt,tranTime)"
                            + "values (" + instrNos + "," + sno + "," + "'" + fromAcno + "'" + "," + "'" + toAcno + "'" + "," + amount + "," + "'" + rightEffDate + "'" + "," + "'" + "UNEXECUTED" + "'" + "," + "'" + remarks + "'" + "," + "'" + userName + "'" + "," + "'" + currentDate + "'" + "," + "'" + deductCharges + "'" + "," + "'" + fullExpiryDate + "'" + "," + "'" + currentDate + "'" + ")");
                    var2 = insertQuery2.executeUpdate();
                }
            } else if (insType.equals("GENERAL")) {
                if (effPeriod.equals("")) {
                    ut.rollback();
                    return "Please Enter The Effective Period";
                }
                if (fromAcno.equals("")) {
                    ut.rollback();
                    return "Please Enter The from Account No";
                }
                if (periodicity.equals("MONTHLY")) {
                    periodicity = "1";
                } else if (periodicity.equals("QUARTERLY")) {
                    periodicity = "3";
                } else if (periodicity.equals("HALF-YEARLY")) {
                    periodicity = "6";
                } else if (periodicity.equals("YEARLY")) {
                    periodicity = "12";
                }
                period2 = periodicity;
                int period = Integer.parseInt(period2);
                List instrNos1 = em.createNativeQuery("select (ifnull(max(instrno),0)+1) from standins_generalmaster where SUBSTRING(ACNO,1,2)='" + brCode + "'").getResultList();
                Vector instrNumber = (Vector) instrNos1.get(0);
                String instrNumbers = instrNumber.get(0).toString();
                instrNos = Float.parseFloat(instrNumbers);
                List seqNo = em.createNativeQuery("select (coalesce(max(sno),0)) from standins_generalmaster where acno='" + fromAcno + "' and SUBSTRING(ACNO,1,2)='" + brCode + "'").getResultList();
                Vector sequNo = (Vector) seqNo.get(0);
                String seqNos = sequNo.get(0).toString();
                int sNumber = Integer.parseInt(seqNos);
                for (int i = 0; i < sp; i++) {
                    sNumber++;
                    rightEffDate = effectiveDate;
                    List effDate = em.createNativeQuery("select DATE_FORMAT(DATE_ADD('" + rightEffDate + "', INTERVAL " + period + " MONTH),'%Y%m%d')").getResultList();
                    Vector effDates = (Vector) effDate.get(0);
                    effectiveDate = effDates.get(0).toString();
                    List lastUpdate = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
                    Vector ltUpdate = (Vector) lastUpdate.get(0);
                    String currentDate = ltUpdate.get(0).toString();
                    Query insertQuery3 = em.createNativeQuery("insert into standins_generalmaster(instrNo,sNo,acno,effDate,status,remarks,entryDate,enterBy,tranTime)"
                            + "values (" + instrNos + "," + sNumber + "," + "'" + fromAcno + "'" + "," + "'" + rightEffDate + "'" + "," + "'" + "UNEXECUTED" + "'" + "," + "'" + remarks + "'" + "," + "'" + currentDate + "'" + "," + "'" + userName + "'" + "," + "'" + currentDate + "'" + ")");
                    var3 = insertQuery3.executeUpdate();
                }
            }
            
            if ((var2 > 0) || (var3 > 0) || (var4 > 0)) {
                ut.commit();
                return instrNos + ": " + saveMessage + " And Instruction No. is " + instrNos;
            } else {
                ut.rollback();
                return "Instruction could not be Saved";
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

    public List getAllDataFromGlDescRange() throws ApplicationException {
        return em.createNativeQuery("select glname,fromno,tono from gl_desc_range order by code").getResultList();
    }

    public String createGlRange(String glname, int fromNo, int toNo, String username) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select glname from gl_desc_range where glname='" + glname + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("This gl type is already exists.");
            }
            list = em.createNativeQuery("select ifnull(max(code),0)+1 from gl_desc_range").getResultList();
            Vector element = (Vector) list.get(0);
            int inq = em.createNativeQuery("INSERT INTO gl_desc_range(Code,Glname,FROMNO,TONO,RecordCreaterID,CreationTime,TS_CNT) VALUES(" + Integer.parseInt(element.get(0).toString()) + ",'" + glname + "'," + fromNo + "," + toNo + ",'" + username + "',current_timestamp,0)").executeUpdate();
            if (inq <= 0) {
                throw new ApplicationException("Problem in creating new gl range.");
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }
    
    public String saveUnAuthSIData(String insType,String fromAcno,String effectiveDate,String effPeriod,String toAcno,String periodicity,int validityDays,float amount,String remarks,String deductCharges,String userName,String brCode, String debitAcTrname, String debitAcGenname, String creditAcTrnName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkUnAuthTable = em.createNativeQuery("select auth from standins_transmaster_auth where from_acno = '"+ fromAcno +"' "
                    + " and to_acno ='"+ toAcno +"'").getResultList();
            if(!chkUnAuthTable.isEmpty()){
                Vector chkElement = (Vector) chkUnAuthTable.get(0);
                String chkAuth = chkElement.get(0).toString();
                if(chkAuth.equalsIgnoreCase("N")){
//                    throw new ApplicationException("Authorize entry for from acno " + fromAcno + " And To Acno " + toAcno + " Exist.");
//                }else{
                    throw new ApplicationException("UnAuthorize entry for from acno " + fromAcno + " And To Acno " + toAcno + " Exist.");
                }                
            }
            
            if(insType.equalsIgnoreCase("TRANSACTION")){
                List chkSTranTable = em.createNativeQuery("select * from standins_transmaster where fromAcno = '"+ fromAcno +"' "
                    + " and toAcno ='"+ toAcno +"'").getResultList();
                if(!chkSTranTable.isEmpty()){
                    throw new ApplicationException("Entry for from acno " + fromAcno + " And To Acno " + toAcno + " Already Created");
                }
                
                String toNat = fTSPosting43CBSBean.getAccountNature(toAcno);
                
                if(toNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || toNat.equalsIgnoreCase(CbsConstant.MS_AC)){
                    List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from td_accountmaster where acno = '"+ toAcno +"'").getResultList();
                    Vector eVal = (Vector) fVal.get(0);
                    int fInt = Integer.parseInt(eVal.get(0).toString());
                    if(fInt <= 0){
                        throw new ApplicationException("Opening Date of " + toAcno + " is Greater Than Effective Date");
                    }
                }else{
                    List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from accountmaster where acno = '"+ toAcno +"'").getResultList();
                    Vector eVal = (Vector) fVal.get(0);
                    int fInt = Integer.parseInt(eVal.get(0).toString());
                    if(fInt <= 0){                        
                        throw new ApplicationException("Opening Date of " + toAcno + " is Greater Than Effective Date");
                    }
                }
            }
            
            String frNat = fTSPosting43CBSBean.getAccountNature(fromAcno); 
            if(frNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || frNat.equalsIgnoreCase(CbsConstant.MS_AC)){
                List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from td_accountmaster where acno = '"+ fromAcno +"'").getResultList();
                Vector eVal = (Vector) fVal.get(0);
                int fInt = Integer.parseInt(eVal.get(0).toString());
                if(fInt <= 0){
                    throw new ApplicationException("Opening Date of " + fromAcno + " is Greater Than Effective Date");
                }
            } else {
                List fVal = em.createNativeQuery("SELECT Datediff('" + effectiveDate + "',openingdt) from accountmaster where acno = '"+ fromAcno +"'").getResultList();
                Vector eVal = (Vector) fVal.get(0);
                int fInt = Integer.parseInt(eVal.get(0).toString());
                if(fInt <= 0){
                    throw new ApplicationException("Opening Date of " + fromAcno + " is Greater Than Effective Date");
                }
            }
            
            List list = em.createNativeQuery("select ifnull(max(sno),0)+1 from standins_transmaster_auth").getResultList();
            Vector element = (Vector) list.get(0);
            int inq = em.createNativeQuery("INSERT INTO standins_transmaster_auth(sno,instruction_type,from_acno,eff_date,eff_period,to_acno,"
                    + "periodicity,validity_days,amount,remarks,charge_flag,enterby,auth,org_brnid,"
                    + "debitactrname,debitacgenname,creditactrname) VALUES(" + Integer.parseInt(element.get(0).toString()) + ",'" + insType + "',"
                    + "'" + fromAcno + "','" + effectiveDate + "','" + effPeriod + "','" + toAcno + "','"+ periodicity +"',"
                    + ""+ validityDays +","+ amount +",'"+ remarks +"','"+ deductCharges +"','" + userName + "',"
                    + "'N','"+ brCode +"','"+ debitAcTrname+"','"+ debitAcGenname +"','"+ creditAcTrnName +"')").executeUpdate();
            if (inq <= 0) {
                throw new ApplicationException("Problem in creating new gl range.");
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }
    
    public String deleteUnAuthSIData(String insType,String brCode, String sNo) throws ApplicationException{
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            
            List chkCode = em.createNativeQuery("SELECT * from standins_transmaster_auth where instruction_type ='"+ insType +"' "
                    + " and sno = '"+ sNo +"' and auth ='N'").getResultList();
            if(chkCode.isEmpty()){
                ut.rollback();
                return "Entry Not Exist May Be Deleted Or Authorized By Another User ";
            }
            
            int inq = em.createNativeQuery("delete from standins_transmaster_auth where sno = '"+ sNo +"' "
                    + " and instruction_type = '"+ insType +"' and org_brnid ='"+ brCode +"'").executeUpdate();
            if (inq <= 0) {
                throw new ApplicationException("Problem in Deleteion.");
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }
    
    public List getUnAuthSI(String InstType, String orgnBrCode) throws ApplicationException {
        try {
            String query = "select distinct sno,from_acno,to_acno from standins_transmaster_auth where auth='N' and instruction_type = '"+ InstType +"' and org_brnid = '" + orgnBrCode + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public List getUnAuthSILst(String sno, String orgnBrCode) throws ApplicationException{
        try {
            String query = "select sno,instruction_type,from_acno,date_format(eff_date,'%d/%m/%Y'),eff_period,to_acno,periodicity,validity_days,"
                    + " amount,remarks,charge_flag,enterby,auth,authby,org_brnid,DebitAcTrName,DebitAcGenName,CreditAcTrName "
                    + " from standins_transmaster_auth where sno='"+ sno +"' and org_brnid = '" + orgnBrCode + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no detail For this");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
