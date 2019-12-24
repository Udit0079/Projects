package com.cbs.facade.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.CustIdDetaiPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
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
import com.cbs.dto.GlHeadSearchPojo;
import com.cbs.pojo.AadharNonAadharPoJo;
import com.cbs.pojo.ChbookDetailPojo;
import java.util.Comparator;

/**
 *
 * @author root
 */
@Stateless(mappedName = "AcctEnqueryFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AcctEnqueryFacade implements AcctEnqueryFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;

    public List tableAccountWise(String accountNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select txninstno,txninstamt,date_format(txnDate,'%d/%m/%Y') from clg_ow_2day where acno='" + accountNo + "' and reasonforcancel=0 union select txninstno,txninstamt,date_format(txnDate,'%d/%m/%Y') "
                    + "from clg_ow_shadowbal where acno='" + accountNo + "' and reasonforcancel=0 union select txninstno,txninstamt,date_format(txnDate,'%d/%m/%Y') from clg_localchq where acno='" + accountNo + "' and reasonforcancel=0 and txnstatus='E'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String acNature(String acNo) throws ApplicationException {
        String message = "";
        try {
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode=(select accttype from accountmaster where acno='" + acNo + "')").getResultList();
            if (acNatList.isEmpty() || acNatList.size() < 0) {
                message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT";
                return message;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return message;
    }

    public List ddStatus() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster order by acctCode");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List branchList() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select brncode, alphaCode from branchmaster ");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List CustomerDetail(String acctNum, String acctType, String agCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            String brnCode = facadeRemote.getCurrentBrnCode(acctNum);
            String acNat = facadeRemote.getAcNatureByCode(acctType);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Query selectQuery = em.createNativeQuery("select a.jtname1,a.jtname2,a.jtname3,a.jtname4,a.custname,c.craddress,c.phoneno,c.fathername from "
                        + "td_accountmaster a ,td_customermaster c where a.accttype=c.actype and substring(a.acno,5,6)=c.custno "
                        + "and c.actype='" + acctType + "' and c.agcode='" + agCode + "' and a.acno='" + acctNum + "' and a.CurBrCode='" + brnCode + "' "
                        + "and c.brncode=a.CurBrCode");
                tableResult = selectQuery.getResultList();
            } else {
                Query selectQuery = em.createNativeQuery("select a.jtname1,a.jtname2,a.jtname3,a.jtname4,a.custname,c.craddress,c.phoneno,c.fathername "
                        + "from accountmaster a,customermaster c where a.accttype=c.actype and substring(a.acno,5,6)=c.custno "
                        + "and c.actype='" + acctType + "' and c.agcode='" + agCode + "' and a.acno='" + acctNum + "' and a.CurBrCode='" + brnCode + "' "
                        + "and c.brncode=a.CurBrCode");
                tableResult = selectQuery.getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String AccountStatus(String acNo) throws ApplicationException {
        try {

            String brnCode = facadeRemote.getAccountCode(acNo);
            String result = null;
            List acctType = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = (select accttype from accountmaster where acno='" + acNo + "')").getResultList();
            Vector eleType = (Vector) acctType.get(0);
            String acctNature = eleType.get(0).toString();
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List tmpseries = em.createNativeQuery("select acno from td_accountmaster where acno='" + acNo + "'  and accstatus=9").getResultList();
                if (tmpseries.size() > 0) {
                    result = "Account has been Closed";
                }
            } else {
                List tmpseries = em.createNativeQuery("select acno from accountmaster where acno='" + acNo + "'  and accstatus=9").getResultList();
                if (tmpseries.size() > 0) {
                    result = "Account has been Closed";
                }
            }

            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List tmpseries = em.createNativeQuery("select acno from td_accountmaster where acno= '" + acNo + "' ").getResultList();
                if (tmpseries.size() <= 0) {
                    result = "Account No. Does Not Exist";
                }
            } else {
                List tmpseries = em.createNativeQuery("select acno from accountmaster where acno='" + acNo + "' ").getResultList();
                if (tmpseries.size() <= 0) {
                    result = "Account No. Does Not Exist";
                }

            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Double AccountWiseBalanceCheck(String acNo) throws ApplicationException {
        try {
            Double Balance;
            List acctType = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = (select accttype from accountmaster where acno='" + acNo + "')").getResultList();
            Vector eleType = (Vector) acctType.get(0);
            String acctNature = eleType.get(0).toString();
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List tmpseries = em.createNativeQuery("select Balance from td_reconbalan where acno = '" + acNo + "' ").getResultList();
                if (tmpseries.size() <= 0) {
                    Balance = 0d;
                } else {
                    Vector elebal = (Vector) tmpseries.get(0);
                    Balance = Double.parseDouble(elebal.get(0).toString());
                }
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List tmpseries = em.createNativeQuery("select Balance from ca_reconbalan where acno = '" + acNo + "'").getResultList();
                if (tmpseries.size() <= 0) {
                    Balance = 0d;
                } else {
                    Vector elebal = (Vector) tmpseries.get(0);
                    Balance = Double.parseDouble(elebal.get(0).toString());
                }
            } else {
                List tmpseries = em.createNativeQuery("select Balance from reconbalan where acno='" + acNo + "' ").getResultList();
                if (tmpseries.size() <= 0) {
                    Balance = 0d;
                } else {
                    Vector elebal = (Vector) tmpseries.get(0);
                    Balance = Double.parseDouble(elebal.get(0).toString());
                }
            }
            return Balance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List nameWiseEnquery(String enterBy, String status, String acctType, String nameType, String custName, String brnCode, String branch) throws ApplicationException {

        try {
            String result = null;
            List custenq = null;
            String msg = "";
            if (branch.equalsIgnoreCase("all")) {
                if (nameType.equalsIgnoreCase("First")) {
                    custName = custName + '%';
                    List tmpseries = em.createNativeQuery("select levelId from securityinfo where userID='" + enterBy + "' and levelId in (1,2) AND brncode = '" + brnCode + "'").getResultList();
                    if (tmpseries.size() <= 0) {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno "
                                        + "and td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode "
                                        + "and AcctType=actype and actype in (select acctcode from accounttypemaster where acctNature in ('FD','MS')) "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and actype in "
                                        + "(select acctcode from accounttypemaster where acctNature not in ('FD','MS')) And substring(acno,11,2)=agcode "
                                        + "and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }

                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno "
                                        + "and td_accountmaster.accStatus=9 and td_customermaster.brncode = td_accountmaster.CurBrCode "
                                        + "and AcctType=actype and actype in (select acctcode from accounttypemaster where acctNature in ('FD','MS')) "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and actype in "
                                        + "(select acctcode from accounttypemaster where acctNature not in ('FD','MS')) And substring(acno,11,2)=agcode "
                                        + "and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }

                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno "
                                        + "and td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode "
                                        + "and AcctType=actype and accttype='" + acctType + "' and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and accttype ='" + acctType + "' "
                                        + "And substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 "
                                        + "and td_customermaster.brncode = td_accountmaster.CurBrCode and AcctType=actype and accttype='" + acctType + "' "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and accttype ='" + acctType + "' "
                                        + "And substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }


                        }

                    } else {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername From td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and AcctType=actype and substring(td_accountmaster.acno,11,2)=agcode "
                                        + "and td_accountmaster.custname like '" + custName + "' Union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9  and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "' ").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername From td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 "
                                        + "and td_customermaster.brncode = td_accountmaster.CurBrCode and AcctType=actype and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "Union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,"
                                        + "phoneno,fathername from accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno "
                                        + "and accountmaster.accStatus=9  and customermaster.brncode = accountmaster.CurBrCode "
                                        + "and AcctType=actype and substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "' ").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }

                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername From td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9  and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and AcctType=actype and substring(td_accountmaster.acno,11,2)=agcode "
                                        + "and td_accountmaster.custname like '" + custName + "' Union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster "
                                        + "where accttype='" + acctType + "' and substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus<>9 and customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and "
                                        + "substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername From td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9  and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and AcctType=actype and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_accountmaster.custname like '" + custName + "' Union select accountmaster.acno,accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where accttype='" + acctType + "' and "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and AcctType=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        }

                    }

                }

                if (nameType.equalsIgnoreCase("Last")) {
                    custName = '%' + custName;
                    List tmpseries = em.createNativeQuery("select levelId from securityinfo where userID='" + enterBy + "' and levelId in (1,2)").getResultList();
                    if (tmpseries.size() <= 0) {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and acctType=actype and acType in "
                                        + "(select acctcode from accounttypemaster where acctNature in ('FD','MS')) and substring(td_accountmaster.acno,11,2)="
                                        + "agcode and td_customermaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,"
                                        + "phoneno,fathername from accountmaster,customermaster where substring(accountmaster.acno,5,6)="
                                        + "customermaster.custno and accStatus<>9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "accttype=actype and actype in (select acctcode from accounttypemaster where acctNature not in ('FD','MS')) "
                                        + "and substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and acctType=actype and "
                                        + "acType in (select acctcode from accounttypemaster where acctNature in ('FD','MS')) "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union "
                                        + "select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername from accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno "
                                        + "and accStatus=9 and customermaster.brncode = accountmaster.CurBrCode and accttype=actype and "
                                        + "actype in (select acctcode from accountTypeMaster where acctNature not in ('FD','MS')) and "
                                        + "substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and acctType=actype and "
                                        + "acctType ='" + acctType + "' and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster "
                                        + "where substring(accountmaster.acno,5,6)=customermaster.custno and accStatus<>9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and accttype=actype and acctType ='" + acctType + "' and "
                                        + "substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno and "
                                        + "td_accountmaster.accStatus=9 and td_customermaster.brncode = td_accountmaster.CurBrCode and acctType=actype and "
                                        + "acctType ='" + acctType + "' and substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union "
                                        + "select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,"
                                        + "customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accStatus=9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and accttype=actype and acctType ='" + acctType + "' and substring(acno,11,2)=agcode "
                                        + "and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        }

                    } else {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno and "
                                        + "td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode and  accttype=actype and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union select accountmaster.acno,"
                                        + "accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and  accttype=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno and "
                                        + "td_accountmaster.accStatus=9 and td_customermaster.brncode = td_accountmaster.CurBrCode and accttype=actype and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union select accountmaster.acno,"
                                        + "accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 and customermaster.brncode = accountmaster.CurBrCode "
                                        + "and accttype=actype and substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }

                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername from td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and accttype=actype and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where accttype='" + acctType + "' and "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and accttype=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername from td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and accttype=actype and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where accttype='" + acctType + "' and "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and accttype=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        }
                    }
                }
            } else {
                /**
                 * ************** BRANCH WISE ****************
                 */
                if (nameType.equalsIgnoreCase("First")) {
                    custName = custName + '%';
                    List tmpseries = em.createNativeQuery("select levelId from securityinfo where userID='" + enterBy + "' and levelId in (1,2) AND brncode = '" + brnCode + "'").getResultList();
                    if (tmpseries.size() <= 0) {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno "
                                        + "and td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and AcctType=actype and actype in (select acctcode from accounttypemaster where acctNature in ('FD','MS')) "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and actype in "
                                        + "(select acctcode from accounttypemaster where acctNature not in ('FD','MS')) And substring(acno,11,2)=agcode "
                                        + "and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }

                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno "
                                        + "and td_accountmaster.accStatus=9 and td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and AcctType=actype and actype in (select acctcode from accounttypemaster where acctNature in ('FD','MS')) "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and actype in "
                                        + "(select acctcode from accounttypemaster where acctNature not in ('FD','MS')) And substring(acno,11,2)=agcode "
                                        + "and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }

                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno "
                                        + "and td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and AcctType=actype and accttype='" + acctType + "' and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus<>9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned)  and AcctType=actype and accttype ='" + acctType + "' "
                                        + "And substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 "
                                        + "and td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and accttype='" + acctType + "' "
                                        + "and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "From accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and accountmaster.accStatus=9 "
                                        + "and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and accttype ='" + acctType + "' "
                                        + "And substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }


                        }

                    } else {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,"
                                        + "phoneno,fathername From td_accountmaster,td_customermaster where substring(td_accountmaster.acno,5,6)=td_customermaster.custno and "
                                        + "td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_accountmaster.custname like '" + custName + "' Union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus<>9  and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and AcctType=actype and substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "' ").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername From td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 "
                                        + "and td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' "
                                        + "Union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,"
                                        + "phoneno,fathername from accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno "
                                        + "and accountmaster.accStatus=9  and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and AcctType=actype and substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "' ").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }

                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername From td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9  and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) and "
                                        + "AcctType=actype and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' Union "
                                        + "select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from "
                                        + "accountmaster,customermaster where accttype='" + acctType + "' and substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus<>9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,"
                                        + "fathername From td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9  and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned)  and "
                                        + "AcctType=actype and substring(td_accountmaster.acno,11,2)=agcode and td_accountmaster.custname like '" + custName + "' Union "
                                        + "select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from "
                                        + "accountmaster,customermaster where accttype='" + acctType + "' and substring(accountmaster.acno,5,6)=customermaster.custno "
                                        + "and accountmaster.accStatus=9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and AcctType=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        }

                    }

                }

                if (nameType.equalsIgnoreCase("Last")) {
                    custName = '%' + custName;
                    List tmpseries = em.createNativeQuery("select levelId from securityinfo where userID='" + enterBy + "' and levelId in (1,2)").getResultList();
                    if (tmpseries.size() <= 0) {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,"
                                        + "jtname2,jtname3,jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and td_customermaster.brncode = cast('" + branch + "' as unsigned) and acctType=actype and acType in "
                                        + "(select acctcode from accounttypemaster where acctNature in ('FD','MS')) and substring(td_accountmaster.acno,11,2)="
                                        + "agcode and td_customermaster.custname like '" + custName + "' "
                                        + "union select accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,"
                                        + "phoneno,fathername from accountmaster,customermaster where substring(accountmaster.acno,5,6)="
                                        + "customermaster.custno and accStatus<>9 and customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) and "
                                        + "accttype=actype and actype in (select acctcode from accounttypemaster where acctNature not in ('FD','MS')) "
                                        + "and substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and acctType=actype and "
                                        + "acType in (select acctcode from accounttypemaster where acctNature in ('FD','MS')) and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union select "
                                        + "accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername "
                                        + "from accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accStatus=9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and actype "
                                        + "in (select acctcode from accounttypemaster where acctNature not in ('FD','MS')) and "
                                        + "substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and acctType=actype and"
                                        + " acctType ='" + acctType + "' and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accStatus<>9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and accttype=actype and acctType ='" + acctType + "' and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and acctType=actype and "
                                        + "acctType ='" + acctType + "' and substring(td_accountmaster.acno,11,2)=agcode and "
                                        + "td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where "
                                        + "substring(accountmaster.acno,5,6)=customermaster.custno and accStatus=9 and "
                                        + "customermaster.brncode = accountmaster.CurBrCode and customermaster.brncode = cast('" + branch + "' as unsigned) "
                                        + "and accttype=actype and acctType ='" + acctType + "' and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        }

                    } else {
                        if (acctType.equalsIgnoreCase("All")) {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus<>9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and  accttype=actype and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union select "
                                        + "accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from "
                                        + "accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus<>9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and  accttype=actype and "
                                        + "substring(acno,11,2)=agcode and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and "
                                        + "substring(td_accountmaster.acno,11,2)=agcode and td_customermaster.custname like '" + custName + "' union select "
                                        + "accountmaster.acno,accountmaster.custname,craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from "
                                        + "accountmaster,customermaster where substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus=9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }

                        } else {
                            if (status.equalsIgnoreCase("Active")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where "
                                        + "accttype='" + acctType + "' and substring(td_accountmaster.acno,5,6)=td_customermaster.custno and "
                                        + "td_accountmaster.accStatus<>9 and td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and substring(td_accountmaster.acno,11,2)=agcode "
                                        + "and td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster "
                                        + "where accttype='" + acctType + "' and substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus<>9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and substring(acno,11,2)=agcode "
                                        + "and accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            } else if (status.equalsIgnoreCase("Close")) {
                                custenq = em.createNativeQuery("select td_accountmaster.acno,td_accountmaster.custname,craddress,jtname1,jtname2,jtname3,"
                                        + "jtname4,phoneno,fathername from td_accountmaster,td_customermaster where accttype='" + acctType + "' and "
                                        + "substring(td_accountmaster.acno,5,6)=td_customermaster.custno and td_accountmaster.accStatus=9 and "
                                        + "td_customermaster.brncode = td_accountmaster.CurBrCode and "
                                        + "td_customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and substring(td_accountmaster.acno,11,2)=agcode "
                                        + "and td_customermaster.custname like '" + custName + "' union select accountmaster.acno,accountmaster.custname,"
                                        + "craddress,jtname1,jtname2,jtname3,jtname4,phoneno,fathername from accountmaster,customermaster where "
                                        + "accttype='" + acctType + "' and substring(accountmaster.acno,5,6)=customermaster.custno and "
                                        + "accountmaster.accStatus=9 and customermaster.brncode = accountmaster.CurBrCode and "
                                        + "customermaster.brncode = cast('" + branch + "' as unsigned) and accttype=actype and substring(acno,11,2)=agcode and "
                                        + "accountmaster.custname like '" + custName + "'").getResultList();
                                if (custenq.size() <= 0) {
                                    return custenq;
                                }
                            }
                        }
                    }
                }
            }
            return custenq;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List nameWiseEnqueryJointName(String accountNo) throws ApplicationException {
        try {
            List joint = null;
            String accountType = facadeRemote.getAccountCode(accountNo);
            String agCode = accountNo.substring(10, 12);
            List acctType = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = (select accttype from accountmaster where acno='" + accountNo + "')").getResultList();
            Vector eleType = (Vector) acctType.get(0);
            String acctNature = eleType.get(0).toString();

            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                joint = em.createNativeQuery(" select acno,td_accountmaster.custname,JtName1,JtName2,JtName3,JtName4 from td_accountmaster,td_customermaster where substring(acno,5,6)=td_customermaster.custno and substring(acno,3,2)='" + accountType + "' and substring(acno,11,2)='" + agCode + "' and acno='" + accountNo + "'").getResultList();
                if (joint.size() <= 0) {
                    return joint;
                }
            } else {
                if (!acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    joint = em.createNativeQuery("select acno,accountmaster.custname,JtName1,JtName2,JtName3,JtName4 from accountmaster,customermaster where substring(acno,5,6)=customermaster.custno and substring(acno,3,2)='" + accountType + "'  and acno='" + accountNo + "'").getResultList();
                    if (joint.size() <= 0) {
                        return joint;
                    }
                } else {
                    joint = em.createNativeQuery("select acno,accountmaster.custname,JtName1,JtName2,JtName3,JtName4 from accountmaster,customermaster where substring(acno,5,6)=customermaster.custno and substring(acno,3,2)='" + accountType + "' and substring(acno,11,2)='" + agCode + "' and acno='" + accountNo + "'").getResultList();
                    if (joint.size() <= 0) {
                        return joint;
                    }
                }
            }
            return joint;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List nameWiseOutwordClearing(String accountNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select txninstno,txninstamt,date_format(txnDate,'%d/%m/%Y') from clg_ow_2day where acno='" + accountNo + "' and reasonforcancel=0 union select txninstno,txninstamt,date_format(txnDate,'%d/%m/%Y') from clg_ow_shadowbal where acno='" + accountNo + "' and reasonforcancel=0 union select txninstno,txninstamt,date_format(txnDate,'%d/%m/%Y') from clg_localchq where acno='" + accountNo + "' and reasonforcancel=0 and txnstatus='E'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    /**
     * ***********************************RdEnquiryBean
     * ******************************
     */
    public String roiSearch(Float period, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List dayList = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY, '" + date + "', DATE_FORMAT(DATE_ADD('" + date + "', INTERVAL " + period + " MONTH),'%Y%m%d'))").getResultList();
            Vector dayLst = (Vector) dayList.get(0);
            String daysList = dayLst.get(0).toString();
            int noOfDay = Integer.parseInt(daysList);
            List roiList = em.createNativeQuery("select Interest_Rate,applicable_date from td_slab Where FromDays <= " + noOfDay + " And ToDays >= " + noOfDay + " and applicable_Date =(select max(Applicable_Date) from td_slab where applicable_date <='" + date + "')").getResultList();
            if (roiList.isEmpty()) {
                ut.rollback();
                return "No Interest Rate Exists.";
            } else {
                Vector roiLst = (Vector) roiList.get(0);
                String roiSrcLst = roiLst.get(0).toString();
                ut.commit();
                return roiSrcLst;
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

    public String rdRoiCal(Float roi, Float amt, Float period) throws ApplicationException {

        try {
            NumberFormat nft = new DecimalFormat("0.00");
            double ip = 4;
            if ((roi == null) || (amt == null) || (period == null)) {
                return "Data could not be null.";
            }
            if ((roi.isNaN() == true) || (amt.isNaN() == true) || (period.isNaN() == true)) {
                return "Amount, Period, ROI should  be Numeric. ";
            }
            double i = roi / ip;
            double c = ip / 12;
            double F = (Math.pow((1 + i / 100), c)) - 1;
            double a1 = ((Math.pow((1 + F), period)) - 1) / F;
            double a2 = a1 * (1 + F);
            double totalAmt = (a2 * amt);
            double interest = (totalAmt - (amt * period));

            return "Total amount is : " + nft.format(CbsUtil.round(totalAmt, 0)) + "  and Toatal interest is : "
                    + nft.format(CbsUtil.round(interest, 0));

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ****************** End *********************
     */
    /**
     * *********************** Customer Search *******************
     */
    public List getData1(String paramValue, String status) throws ApplicationException {
        try {
            List list = null;
            if (paramValue.length() == 12) {
//                String qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                        + "FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CI.ACNO='" + paramValue + "' "
//                        + "union all "
//                        + "SELECT customerid,Regfoliono,custname,DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
//                        + "MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND "
//                        + "sh.Regfoliono='" + paramValue + "' ";

                String statusCondQueryAcc = "";
                String statusCondQueryShare = "";
                if (status.equalsIgnoreCase("active")) {
                    statusCondQueryAcc = " and acm.AccStatus<>9  ";
                    statusCondQueryShare = " status ='A' ";
                } else if (status.equalsIgnoreCase("close")) {
                    statusCondQueryAcc = " and acm.AccStatus=9 ";
                    statusCondQueryShare = " status ='C' ";
                } else if (status.equalsIgnoreCase("all")) {
                    statusCondQueryAcc = "  ";
                    statusCondQueryShare = " status in ('C','A') ";
                }
                String qry = "SELECT customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster as acm,codebook cb WHERE CI.CUSTID=CCMD.customerid and acm.ACNo=CI.Acno " + statusCondQueryAcc
                        + " AND cb.groupcode='3' and cb.code= acm.accstatus AND acm.ACNO='" + paramValue + "' "
                        + " union "
                        + " SELECT customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster as acm,codebook cb  WHERE CI.CUSTID=CCMD.customerid and acm.ACNo=CI.Acno " + statusCondQueryAcc
                        + " AND cb.groupcode='3' and cb.code= acm.accstatus AND acm.ACNO='" + paramValue + "' "
                        + " union "
                        + " SELECT customerid,Regfoliono,custname,DateOfBirth, CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName ,CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
                        + " MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                        + " FROM share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc, certificate_share cs  where "
                        + " sharecertno = certificateno and  " + statusCondQueryShare
                        + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and sh.Regfoliono='" + paramValue + "'";
                Query q1 = em.createNativeQuery(qry);
                list = q1.getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getData2(String paramValue, String status) throws ApplicationException {
        try {
            List list = null;
            if (!paramValue.equals("")) {
                String qry = "";
                String statusCondQueryAcc = "";
                String statusCondQueryShare = "";
                if (status.equalsIgnoreCase("active")) {
                    statusCondQueryAcc = "  acm.AccStatus<>9 and ";
                    statusCondQueryShare = " status ='A' ";
                } else if (status.equalsIgnoreCase("close")) {
                    statusCondQueryAcc = " acm.AccStatus=9 and ";
                    statusCondQueryShare = " status ='C' ";
                } else if (status.equalsIgnoreCase("all")) {
                    statusCondQueryAcc = "  ";
                    statusCondQueryShare = " status in ('C','A') ";
                }
//                String qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,middle_name,last_name from "
//                        + "(SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                        + "FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CCMD.CUSTNAME LIKE '%" + paramValue + "%' "
//                        + "union all "
//                        + "SELECT customerid,regfoliono as acno,custname,DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                        + "FROM share_holder AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CCMD.CUSTNAME LIKE '%" + paramValue + "%' "
//                        + "union all "
//                        + "select customerid,'',ifnull(custname,''),DateOfBirth,ifnull(fathername,''),ifnull(mothername,''),ifnull(mobilenumber,''),ifnull(PAN_GIRNumber,''),"
//                        + "ifnull(PerAddressLine1,''),ifnull(MailAddressLine1,''),ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name from cbs_customer_master_detail where CUSTNAME LIKE '%" + paramValue + "%' "
//                        + "and customerid not in (select custid from customerid)) as a "
//                        + "order by custname";

                //String qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1 "
                //       + "FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CCMD.CUSTNAME LIKE '%" + paramValue + "%' order by custname";
                qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
                        + " MailAddressLine1,middle_name,last_name,status from "
                        + " (SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                        + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm,codebook cb WHERE " + statusCondQueryAcc
                        + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' and cb.code= acm.accstatus AND "
                        + " CCMD.CustFullName LIKE '%" + paramValue + "%'"
                        + " union "
                        + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                        + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm,codebook cb WHERE " + statusCondQueryAcc
                        + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND  cb.groupcode='3' AND cb.code= acm.accstatus AND "
                        + " CCMD.CustFullName LIKE '%" + paramValue + "%'"
                        + " union "
                        + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,"
                        + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                        + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc , certificate_share cs where "
                        + " sharecertno = certificateno and  " + statusCondQueryShare
                        + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and  cc.CustFullName LIKE '%" + paramValue + "%' group by cc.customerid,sh.Regfoliono"
                        + " union  "
                        + " select customerid,'',ifnull(custname,''),DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,ifnull(mobilenumber,''),"
                        + " ifnull(PAN_GIRNumber,''),"
                        + " ifnull(PerAddressLine1,''),ifnull(MailAddressLine1,''),ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,'' as status"
                        + " from cbs_customer_master_detail where CustFullName LIKE '%" + paramValue + "%' "
                        + " and customerid not in (select custid from customerid)) as a "
                        + " order by custname";

                Query q1 = em.createNativeQuery(qry);
                list = q1.getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    
    @Override
    public List getData5(String custnameValue, String fatherValue, String status) throws ApplicationException {
               try {
            List list = null;
            if (!custnameValue.equals("")) {
                String qry = "";
                String statusCondQueryAcc = "";
                String statusCondQueryShare = "";
                if (status.equalsIgnoreCase("active")) {
                    statusCondQueryAcc = "  acm.AccStatus<>9 and ";
                    statusCondQueryShare = " status ='A' ";
                } else if (status.equalsIgnoreCase("close")) {
                    statusCondQueryAcc = " acm.AccStatus=9 and ";
                    statusCondQueryShare = " status ='C' ";
                } else if (status.equalsIgnoreCase("all")) {
                    statusCondQueryAcc = "  ";
                    statusCondQueryShare = " status in ('C','A') ";
                }
                qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
                        + " MailAddressLine1,middle_name,last_name,status from "
                        + " (SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                        + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm,codebook cb WHERE " + statusCondQueryAcc
                        + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' and cb.code= acm.accstatus AND "
                        + " CCMD.CustFullName LIKE '%" + custnameValue + "%' and CCMD.fathername LIKE '%" + fatherValue + "%' "
                        + " union "
                        + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                        + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm,codebook cb WHERE " + statusCondQueryAcc
                        + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND  cb.groupcode='3' AND cb.code= acm.accstatus AND "
                        + " CCMD.CustFullName LIKE '%" + custnameValue + "%' and CCMD.fathername LIKE '%" + fatherValue + "%'"
                        + " union "
                        + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,"
                        + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                        + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                        + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc , certificate_share cs where "
                        + " sharecertno = certificateno and  " + statusCondQueryShare
                        + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and  cc.CustFullName LIKE '%" + custnameValue + "%' and cc.fathername LIKE '%" + fatherValue + "%' group by cc.customerid,sh.Regfoliono"
                        + " union  "
                        + " select customerid,'',ifnull(custname,''),DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                        + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,ifnull(mobilenumber,''),"
                        + " ifnull(PAN_GIRNumber,''),"
                        + " ifnull(PerAddressLine1,''),ifnull(MailAddressLine1,''),ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,'' as status"
                        + " from cbs_customer_master_detail where CustFullName LIKE '%" + custnameValue + "%' and fathername LIKE '%" + fatherValue + "%'"
                        + " and customerid not in (select custid from customerid)) as a "
                        + " order by custname";

                Query q1 = em.createNativeQuery(qry);
                list = q1.getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }


    public List getData3(String paramValue, String option, String status) throws ApplicationException {
        try {
            List list = null;
            if (!paramValue.equals("")) {
                String qry = "";
                String statusCondQueryAcc = "";
                String statusCondQueryShare = "";
                if (status.equalsIgnoreCase("active")) {
                    statusCondQueryAcc = "  acm.AccStatus<>9 and ";
                    statusCondQueryShare = " status ='A' ";
                } else if (status.equalsIgnoreCase("close")) {
                    statusCondQueryAcc = " acm.AccStatus=9 and ";
                    statusCondQueryShare = " status ='C' ";
                } else if (status.equalsIgnoreCase("all")) {
                    statusCondQueryAcc = "  ";
                    statusCondQueryShare = " status in ('C','A') ";
                }

                if (option.equals("id")) {
//                    qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                            + "FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CI.CUSTID='" + paramValue + "'"
//                            + "union all "
//                            + "SELECT customerid,Regfoliono,custname,DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
//                            + "MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND "
//                            + "sh.custid='" + paramValue + "' ";
                    qry = "SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,mothername,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus AND "
                            + " CCMD.customerid in(" + paramValue + ")"
                            + " union "
                            + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,mothername,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus and "
                            + " CCMD.customerid in(" + paramValue + ")"
                            + " union "
                            + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,mothername,mobilenumber,"
                            + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,"
                            + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                            + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc, certificate_share cs where "
                            + " sharecertno =certificateno AND " + statusCondQueryShare
                            + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and cc.customerid in(" + paramValue + ") group by cc.customerid,sh.Regfoliono";

                } else if (option.equals("AADHAAR")) {
//                    qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                            + "FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND "
//                            + "(CCMD.AADHAAR_NO like '%" + paramValue + "%' or (CCMD.legal_document = 'E' and CCMD.IdentityNo like '%" + paramValue + "%')) "
//                            + "union all "
//                            + "SELECT customerid,Regfoliono,custname,DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
//                            + "MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND "
//                            + "(CCMD.AADHAAR_NO like '%" + paramValue + "%' or (CCMD.legal_document = 'E' and CCMD.IdentityNo like '%" + paramValue + "%')) ";
                    String qryForCustId = "select customerid from cbs_cust_identity_details  where (IdentificationType='E' and IdentityNo like '%" + paramValue + "%')"
                            + " union all"
                            + " select customerid from cbs_customer_master_detail  where AADHAAR_NO like '%" + paramValue + "%' or (legal_document = 'E' and IdentityNo like '%" + paramValue + "%') ";

                    qry = "SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                            + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus AND CCMD.customerid in(" + qryForCustId + ")"
                            + " union "
                            + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                            + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm ,codebook cb WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid and cb.groupcode='3' AND cb.code= acm.accstatus AND CCMD.customerid in(" + qryForCustId + ")"
                            + " union "
                            + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,"
                            + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,"
                            + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,"
                            + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                            + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc,certificate_share cs where "
                            + " sharecertno =certificateno AND " + statusCondQueryShare
                            + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and cc.customerid in(" + qryForCustId + ") group by cc.customerid,sh.Regfoliono";

                } else if (option.equals("pan")) {

//                    qry = "SELECT customerid,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                            + "FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CCMD.PAN_GIRNumber='" + paramValue + "'"
//                            + "union all "
//                            + "SELECT customerid,Regfoliono,custname,DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,"
//                            + "MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND "
//                            + "CCMD.PAN_GIRNumber='" + paramValue + "' ";
                    String qryForCustId = "select customerid from cbs_cust_identity_details  where (IdentificationType='C' and IdentityNo like '%" + paramValue + "%')"
                            + " union all"
                            + " select customerid from cbs_customer_master_detail  where PAN_GIRNumber like '%" + paramValue + "%' or (legal_document = 'C' and IdentityNo like '%" + paramValue + "%') ";

                    qry = "SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                            + "CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus AND CCMD.customerid in(" + qryForCustId + ")"
                            + " union "
                            + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,"
                            + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm ,codebook cb WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid and cb.groupcode='3' AND cb.code= acm.accstatus AND CCMD.customerid in(" + qryForCustId + ")"
                            + " union "
                            + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,"
                            + " CONCAT (mothername,' ',MotherMiddleName,' ',MotherLastName) as motherName,mobilenumber,"
                            + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,"
                            + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                            + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc,certificate_share cs where "
                            + " sharecertno =certificateno AND " + statusCondQueryShare
                            + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and cc.customerid in(" + qryForCustId + ") group by cc.customerid,sh.Regfoliono";

                } else if (option.equalsIgnoreCase("TokenNo")) {

                    qry = "SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,mothername,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus AND "
                            + " CCMD.employeeNo in('" + paramValue + "')"
                            + " union "
                            + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,mothername,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus and "
                            + " CCMD.employeeNo in('" + paramValue + "')"
                            + " union "
                            + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,mothername,mobilenumber,"
                            + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,"
                            + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                            + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc, certificate_share cs where "
                            + " sharecertno =certificateno AND " + statusCondQueryShare
                            + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and cc.employeeNo in('" + paramValue + "') group by cc.customerid,sh.Regfoliono";

                } else if (option.equalsIgnoreCase("MobileNo")) {
                    qry = "SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,mothername,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus AND "
                            + " CCMD.mobilenumber in(" + paramValue + ")"
                            + " union "
                            + " SELECT CCMD.customerid,CI.acno,CCMD.custname,DateOfBirth,CONCAT (fathername,' ',FatherMiddleName,' ',FatherLastName) as fatherName,mothername,mobilenumber,PAN_GIRNumber,"
                            + " PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,"
                            + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD,td_accountmaster AS acm ,codebook cb  WHERE " + statusCondQueryAcc
                            + " acm.acno=CI.acno AND CI.CUSTID=CCMD.customerid AND CI.CUSTID=CCMD.customerid AND cb.groupcode='3' AND cb.code= acm.accstatus and "
                            + " CCMD.mobilenumber in(" + paramValue + ")"
                            + " union "
                            + " SELECT cc.customerid,sh.Regfoliono as acno,cc.custname,DateOfBirth,CONCAT (cc.fathername,' ',cc.FatherMiddleName,' ',cc.FatherLastName) as fatherName,mothername,mobilenumber,"
                            + " PAN_GIRNumber,PerAddressLine1,MailAddressLine1,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,"
                            + " IF(cs.status='C' ,'CLOSED',if( cs.status='A','ACTIVE','')) as status"
                            + " from share_capital_issue sc,share_holder sh,cbs_customer_master_detail cc, certificate_share cs where "
                            + " sharecertno =certificateno AND " + statusCondQueryShare
                            + " and cc.customerid = sh.custid and sh.regfoliono = sc.foliono  and cc.mobilenumber in(" + paramValue + ") group by cc.customerid,sh.Regfoliono";
                }
                Query q1 = em.createNativeQuery(qry);
                list = q1.getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getData4(String paramValue, String status) throws ApplicationException {
        try {
            List list = null;
            if (!paramValue.equals("")) {
                String statusCondQueryAcc = "";
                if (status.equalsIgnoreCase("active")) {
                    statusCondQueryAcc = " and A.AccStatus<>9  ";
                } else if (status.equalsIgnoreCase("close")) {
                    statusCondQueryAcc = " AND A.AccStatus=9 ";
                } else if (status.equalsIgnoreCase("all")) {
                    statusCondQueryAcc = "  ";
                }
                String qry = "SELECT A.ACNO,A.CustName,C.FATHERNAME,DATE_FORMAT(DATE_FORMAT(IFNULL(C.DOB,'19000101'),'%Y-%m-%d'),'%d/%m/%Y'),C.craddress,A.JTNAME1,A.JTNAME2,A.JTNAME3,A.JTNAME4,IFNULL(A.CUSTID1,''),IFNULL(A.CUSTID2,''),IFNULL(A.CUSTID3,''),IFNULL(A.CUSTID4,'') ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM td_accountmaster A, td_customermaster C,codebook cb WHERE cb.groupcode='3' and cb.code= A.accstatus AND A.ACNO IN ((SELECT ACNO FROM td_accountmaster WHERE (JTNAME1 LIKE '%" + paramValue + "%' OR JTNAME2 LIKE '%" + paramValue + "%' OR JTNAME3 LIKE '%" + paramValue + "%' OR JTNAME4 LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM td_accountmaster WHERE CUSTID1 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM td_accountmaster WHERE CUSTID2 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM td_accountmaster WHERE CUSTID3 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM td_accountmaster WHERE CUSTID4 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%'))) "
                        + " AND C.CUSTNO = SUBSTRING(A.ACNO,5,6) AND C.ACTYPE = A.ACCTTYPE AND A.CURBRCODE = C.BRNCODE AND C.AGCODE = SUBSTRING(A.ACNO,11,2 ) " + statusCondQueryAcc + " UNION "
                        + " SELECT A.ACNO,A.CustName,C.FATHERNAME,DATE_FORMAT(DATE_FORMAT(IFNULL(C.DOB,'19000101'),'%Y-%m-%d'),'%d/%m/%Y'),C.craddress,A.JTNAME1,A.JTNAME2,A.JTNAME3,A.JTNAME4,IFNULL(A.CUSTID1,''),IFNULL(A.CUSTID2,''),IFNULL(A.CUSTID3,''),IFNULL(A.CUSTID4,'') ,"
                        + " if(cb.Description='OPERATIVE','ACTIVE',cb.Description) as status"
                        + " FROM accountmaster A, customermaster C,codebook cb WHERE cb.groupcode='3' and cb.code= A.accstatus AND  A.ACNO IN ((SELECT ACNO FROM accountmaster WHERE (JTNAME1 LIKE '%" + paramValue + "%' OR JTNAME2 LIKE '%" + paramValue + "%' OR JTNAME3 LIKE '%" + paramValue + "%' OR JTNAME4 LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM accountmaster WHERE CUSTID1 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM accountmaster WHERE CUSTID2 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM accountmaster WHERE CUSTID3 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%') UNION "
                        + " SELECT ACNO FROM accountmaster WHERE CUSTID4 IN (SELECT CUSTOMERID FROM cbs_customer_master_detail WHERE CustFullName LIKE '%" + paramValue + "%'))) "
                        + " AND C.CUSTNO = SUBSTRING(A.ACNO,5,6) AND C.ACTYPE = A.ACCTTYPE AND A.CURBRCODE = C.BRNCODE AND C.AGCODE = SUBSTRING(A.ACNO,11,2 ) " + statusCondQueryAcc + " ORDER BY 1";
                Query q1 = em.createNativeQuery(qry);
                list = q1.getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ****************************** End *************************
     */
    /**
     * ************************** Instrument Search **************
     */
    public List accType() throws ApplicationException {
        List varlist = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('ca','sb')");
            varlist = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return varlist;
    }

    public String searchData(String acType, String chqno, String brCode) throws ApplicationException {
        String Msg = "";
        String acno = "";
        String name = "";
        String chqSrNo = "";
        try {
            String acNat = facadeRemote.getAcNatureByCode(acType);

            List resList1 = new ArrayList();
            if ((acNat.equalsIgnoreCase(CbsConstant.SAVING_AC))) {
                resList1 = em.createNativeQuery("select c.acno,a.custname,ifnull(c.statusflag,''),ifnull(c.chqsrno,'') from chbook_sb c,accountmaster a "
                        + "where chqno ='" + chqno + "' AND  a.accttype='" + acType + "' and a.CurBrCode='" + brCode + "'  and a.acno=c.acno ").getResultList();
            } else if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                resList1 = em.createNativeQuery("select c.acno,a.custname,ifnull(c.statusflag,''),ifnull(c.chqsrno,'') from chbook_ca c,accountmaster a "
                        + "where chqno ='" + chqno + "' AND  a.accttype='" + acType + "' and a.CurBrCode='" + brCode + "'  and a.acno=c.acno ").getResultList();
            }
            if (resList1.isEmpty()) {
                return "Instrument No. does not Exist";
            }
            for (int i = 0; i < resList1.size(); i++) {
                Vector recLst1 = (Vector) resList1.get(i);
                acno = recLst1.get(0).toString();
                name = recLst1.get(1).toString();

                String rs = recLst1.get(2).toString();
                if (recLst1.get(3).toString().equalsIgnoreCase("")) {
                    chqSrNo = "N/A";
                } else {
                    chqSrNo = recLst1.get(3).toString();
                }
                if (rs.equalsIgnoreCase("F")) {
                    Msg = "UNUSED";
                } else if (rs.equals("U")) {
                    Msg = "ALREADY USED";
                } else if (rs.equals("S")) {
                    Msg = "STOP PAYMENT";
                }
            }
            return Msg + ":" + acno + ":" + name + ":" + chqSrNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getGlDetails(String optionSearch, String glCode, String glName) throws ApplicationException {
        List<GlHeadSearchPojo> dataList = new ArrayList<GlHeadSearchPojo>();
        List result = new ArrayList();
        try {
            if (optionSearch.equalsIgnoreCase("1")) {
                result = em.createNativeQuery("select * from gltable where acno like '%" + glCode + "%'").getResultList();
            } else {
                result = em.createNativeQuery("select * from gltable where AcName like '%" + glName + "%' order by AcName").getResultList();
            }
            int sNo = 0;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    sNo = sNo + 1;
                    Vector ele = (Vector) result.get(i);
                    GlHeadSearchPojo pojo = new GlHeadSearchPojo();
                    pojo.setGlAcNo(ele.get(0).toString());
                    pojo.setGlName(ele.get(1).toString());
                    pojo.setGlPostfg(ele.get(2).toString());
                    pojo.setGlMesgfg(ele.get(3).toString());
                    pojo.setSqNo(sNo);
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    /**
     * **************************** End ************************
     */
    public List getIdDetails(String custId) throws ApplicationException {
        List<CustIdDetaiPojo> dataList = new ArrayList<CustIdDetaiPojo>();
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select cd.acno,cc.CustFullName,concat_ws(' ', nullif(cc.fathername,''),nullif((concat_ws(' ', nullif(cc.FatherMiddleName,'') ,nullif(cc.FatherLastName,''))),'')),date_format(ifnull(cc.DateOfBirth,'19000101'),'%d/%m/%Y'),"
                    + " cc.PAN_GIRNumber,cc.MailAddressLine1,cc.MailAddressLine2,cc.MailVillage,cc.MailBlock,cc.MailCityCode,cc.MailStateCode,"
                    + " cc.MailPostalCode,cc.MailCountryCode from cbs_customer_master_detail cc, customerid cd "
                    + " where cc.customerid = cd.custid and cc.customerid = '" + custId + "' "
                    + "union all "
                    + "select cd.Regfoliono,cc.CustFullName,concat_ws(' ', nullif(cc.fathername,''),nullif((concat_ws(' ', nullif(cc.FatherMiddleName,'') ,nullif(cc.FatherLastName,''))),'')),date_format(ifnull(cc.DateOfBirth,'19000101'),'%d/%m/%Y'),"
                    + " cc.PAN_GIRNumber,cc.MailAddressLine1,cc.MailAddressLine2,cc.MailVillage,cc.MailBlock,cc.MailCityCode,cc.MailStateCode,"
                    + " cc.MailPostalCode,cc.MailCountryCode from cbs_customer_master_detail cc, share_holder cd "
                    + " where cc.customerid = cd.custid and cc.customerid = '" + custId + "'").getResultList();
            if (result.isEmpty()) {
                result = em.createNativeQuery("select 'N/A',cc.CustFullName,concat_ws(' ', nullif(cc.fathername,''),nullif((concat_ws(' ', nullif(cc.FatherMiddleName,'') ,nullif(cc.FatherLastName,''))),'')),date_format(ifnull(cc.DateOfBirth,'19000101'),'%d/%m/%Y'),"
                        + " cc.PAN_GIRNumber,cc.MailAddressLine1,cc.MailAddressLine2,cc.MailVillage,cc.MailBlock,cc.MailCityCode,cc.MailStateCode,"
                        + " cc.MailPostalCode,cc.MailCountryCode from cbs_customer_master_detail cc"
                        + " where cc.customerid = '" + custId + "' ").getResultList();
            }
            int sNo = 0;
            // if (!result.isEmpty()) {
            for (int i = 0; i < result.size(); i++) {
                sNo = sNo + 1;
                Vector ele = (Vector) result.get(i);
                CustIdDetaiPojo pojo = new CustIdDetaiPojo();
                pojo.setsNo(sNo);
                pojo.setAcno(ele.get(0).toString());
                pojo.setCustname(ele.get(1).toString());
                pojo.setFname(ele.get(2) != null ? ele.get(2).toString() : "");
                pojo.setDob(ele.get(3) != null ? ele.get(3).toString() : "");
                pojo.setPanno(ele.get(4) != null ? ele.get(4).toString() : "");

                String city = "";
                if ((ele.get(9) == null) || (ele.get(9).toString().equalsIgnoreCase(""))) {
                } else {
                    List result1 = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '001' and ref_code = '" + ele.get(9).toString() + "' ").getResultList();
                    if (!result1.isEmpty()) {
                        Vector ele1 = (Vector) result1.get(0);
                        city = ele1.get(0).toString();
                    }
                }

                String state = "";
                if ((ele.get(10) == null) || (ele.get(10).toString().equalsIgnoreCase(""))) {
                } else {
                    List result2 = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '002' and ref_code = '" + ele.get(10).toString() + "' ").getResultList();
                    if (!result2.isEmpty()) {
                        Vector ele1 = (Vector) result2.get(0);
                        state = ele1.get(0).toString();
                    }
                }

                String con = "";
                if ((ele.get(12) == null) || (ele.get(12).toString().equalsIgnoreCase(""))) {
                } else {
                    List result2 = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '003' and ref_code = '" + ele.get(12).toString() + "' ").getResultList();
                    if (!result2.isEmpty()) {
                        Vector ele1 = (Vector) result2.get(0);
                        con = ele1.get(0).toString();
                    }
                }
                pojo.setCraddress(ele.get(5) != null ? ele.get(5).toString() : "" + " " + ele.get(6) != null ? ele.get(6).toString() : "" + " " + ele.get(7) != null ? ele.get(7).toString() : "" + " " + ele.get(8) != null ? ele.get(8).toString() : "" + " " + city + " " + state + " " + con);

                dataList.add(pojo);
            }
            //}
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<ChbookDetailPojo> getAllChqDetails(String acno) throws ApplicationException {
        String msg = "";
        try {
            List<ChbookDetailPojo> chLst = new ArrayList<ChbookDetailPojo>();
            List acctType = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = (select accttype from accountmaster where acno='" + acno + "')").getResultList();
            Vector eleType = (Vector) acctType.get(0);
            String acNat = eleType.get(0).toString();

            List resList1 = new ArrayList();
            if ((acNat.equalsIgnoreCase(CbsConstant.SAVING_AC))) {
                resList1 = em.createNativeQuery("select c.acno,a.custname,ifnull(c.statusflag,''),ifnull(c.chqsrno,''),cast(c.chqno as unsigned) from chbook_sb c,accountmaster a "
                        + " where a.acno='" + acno + "' and a.acno=c.acno order by c.statusflag,4,5").getResultList();
            } else if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                resList1 = em.createNativeQuery("select c.acno,a.custname,ifnull(c.statusflag,''),ifnull(c.chqsrno,''),cast(c.chqno as unsigned) from chbook_ca c,accountmaster a where "
                        + " a.acno='" + acno + "' and a.acno=c.acno order by c.statusflag,4,5").getResultList();
            }
            int m = 0;
            if (!resList1.isEmpty()) {
                for (int i = 0; i < resList1.size(); i++) {
                    Vector recLst1 = (Vector) resList1.get(i);
                    m = m + 1;
                    ChbookDetailPojo pojo = new ChbookDetailPojo();
                    pojo.setAcctNo(recLst1.get(0).toString());
                    pojo.setCustName(recLst1.get(1).toString());
                    String rs = recLst1.get(2).toString();
                    if (recLst1.get(3).toString().equalsIgnoreCase("")) {
                        pojo.setChqBookNo("N/A");
                    } else {
                        pojo.setChqBookNo(recLst1.get(3).toString());
                    }
                    if (rs.equalsIgnoreCase("F")) {
                        msg = "FRESH";
                    } else if (rs.equals("U")) {
                        msg = "USED";
                    } else if (rs.equals("S")) {
                        msg = "STOP PAYMENT";
                    }
                    pojo.setFavoring(msg);
                    pojo.setStatus(m);
                    pojo.setChqNo(recLst1.get(4).toString());
                    chLst.add(pojo);
                }
            }
            return chLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<AadharNonAadharPoJo> getAadharNonAadharDetail(String paramValue, String option) throws ApplicationException {
        try {
            List<AadharNonAadharPoJo> finalList = new ArrayList<AadharNonAadharPoJo>();
            if (!paramValue.equals("")) {
                String qry = "";
                String brnList = "";
                String brnList1 = "";
                if (paramValue.equalsIgnoreCase("0A")) {
                    brnList = "";
                } else {
                    brnList = " substring(acno,1,2) =  '" + paramValue + "' and ";
                    brnList1 = " substring(Regfoliono,1,2) =  '" + paramValue + "' and ";
                }
                List list = new ArrayList();
                if (option.equalsIgnoreCase("A")) {
                    qry = " SELECT customerid,AADHAAR_NO ,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,ifnull(PerAddressLine1,''),ifnull(peraddressline2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,''),ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),middle_name,last_name ,primaryAc from ("
                            + "SELECT customerid,CCMD.AADHAAR_NO ,acno,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc "
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND  " + brnList + "  SuspensionFlg   <> 'Y' AND  "
                            + " (CCMD.AADHAAR_NO like '%%' and CCMD.AADHAAR_NO <>'' )  group by CI.CustId "
                            + " union all "
                            + " SELECT customerid,CCMD.AADHAAR_NO,Regfoliono,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,"
                            + " MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND " + brnList1 + "  SuspensionFlg   <> 'Y' AND "
                            + " (CCMD.AADHAAR_NO like '%%' and CCMD.AADHAAR_NO <>'') "
                            + " union  "
                            + " SELECT customerid,CCMD.IdentityNo as AADHAAR_NO,acno,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc "
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND " + brnList + "  SuspensionFlg   <> 'Y' AND  "
                            + " (CCMD.legal_document = 'E' and CCMD.IdentityNo like '%%' and CCMD.IdentityNo<>'')  group by CI.CustId "
                            + " union all "
                            + " SELECT customerid,CCMD.IdentityNo as AADHAAR_NO,Regfoliono,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,"
                            + " MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND " + brnList1 + "  SuspensionFlg   <> 'Y' AND  "
                            + " (CCMD.legal_document = 'E' and CCMD.IdentityNo like '%%' and CCMD.IdentityNo<>'')) f order by cast(f.customerid  as unsigned),f.acno ";
                } else if (option.equalsIgnoreCase("N")) {
                    qry = "  SELECT customerid,AADHAAR_NO ,acno,custname,DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,ifnull(PerAddressLine1,''),ifnull(peraddressline2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,''),ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),middle_name, last_name ,primaryAc from ( "
                            + "SELECT customerid,CCMD.AADHAAR_NO,acno,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,ifnull(AADHAAR_LPG_ACNO,'')  as primaryAc "
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND " + brnList + " SuspensionFlg   <> 'Y' AND "
                            + " (/*CCMD.AADHAAR_NO like '' and */CCMD.AADHAAR_NO ='' )  group by CI.CustId "
                            + " union all "
                            + " SELECT customerid,CCMD.AADHAAR_NO,Regfoliono,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,"
                            + " MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND " + brnList1 + " SuspensionFlg   <> 'Y' AND "
                            + " (/*CCMD.AADHAAR_NO like '%%' and*/ CCMD.AADHAAR_NO ='') "
                            + " union  "
                            + " SELECT customerid,CCMD.IdentityNo as AADHAAR_NO,acno,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name ,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc "
                            + " FROM customerid AS CI,cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND " + brnList + " SuspensionFlg   <> 'Y' AND  "
                            + " (CCMD.legal_document = 'E' and /*CCMD.IdentityNo like '%%' and*/ CCMD.IdentityNo='')  group by CI.CustId "
                            + " union all "
                            + " SELECT customerid,CCMD.IdentityNo as AADHAAR_NO,Regfoliono,custname,ifnull(DateOfBirth,'1900-01-01 00:00:00') as DateOfBirth,CCMD.fathername,mothername,mobilenumber,PAN_GIRNumber,PerAddressLine1,peraddressline2,PerVillage,PerBlock,PerStateCode,PerPostalCode,"
                            + " MailAddressLine1,MailAddressLine2,MailVillage,MailBlock,MailStateCode,MailPostalCode,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,ifnull(AADHAAR_LPG_ACNO,'') as primaryAc FROM share_holder sh ,cbs_customer_master_detail AS CCMD WHERE sh.custId=CCMD.customerid AND " + brnList1 + " SuspensionFlg   <> 'Y' AND "
                            + " (CCMD.legal_document = 'E' and /*CCMD.IdentityNo like '%%' and*/ CCMD.IdentityNo='') ) f order by cast(f.customerid  as unsigned),f.acno  ";
                }
                Query q1 = em.createNativeQuery(qry);
                list = q1.getResultList();
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        AadharNonAadharPoJo pojo = new AadharNonAadharPoJo();
                        Vector vect = (Vector) list.get(i);
                        pojo.setCustId(vect.get(0).toString());
                        pojo.setAadharNo(vect.get(1).toString());
                        pojo.setAcNo(vect.get(2).toString());
                        pojo.setCustName(vect.get(3).toString());
                        System.out.println("Acno " + vect.get(2).toString());
                        pojo.setDob(ymd.parse(vect.get(4).toString()));
                        pojo.setFatherName(vect.get(5).toString());
                        pojo.setMotherName(vect.get(6).toString());
                        pojo.setMobileNo(vect.get(7).toString());
                        pojo.setPanNo(vect.get(8).toString());
                        String presentAddd = vect.get(9).toString().concat(vect.get(10).toString().concat(vect.get(11).toString().concat(vect.get(12).toString().concat(vect.get(13).toString().concat(vect.get(14).toString())))));
                        pojo.setPreAdd(presentAddd);
                        String mailAdd = vect.get(15).toString().concat(vect.get(16).toString()).concat(vect.get(17).toString()).concat(vect.get(18).toString()).concat(vect.get(19).toString()).concat(vect.get(20).toString());
                        pojo.setMailAdd(mailAdd);
                        pojo.setPrimaryAcNo(vect.get(23).toString());
                        finalList.add(pojo);
                    }
                }
            }
//            Collections.sort(finalList, new SortByCustId());
            return finalList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getOriginalIdMergeId(String custId) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from cbs_id_merge_auth where mergId in('" + custId + "') and auth <> 'D'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public class SortByCustId implements Comparator<AadharNonAadharPoJo> {

        public int compare(AadharNonAadharPoJo o1, AadharNonAadharPoJo o2) {
            return o1.getCustId().compareTo(o2.getCustId());
        }
    }

    public List ddsInterestCal(Double roi, Double amt, Float period) throws ApplicationException {
        List retLst = new ArrayList();
        try {
            NumberFormat nft = new DecimalFormat("0.00");
            double p = amt;
            double r = roi;
            double totProd = 0;
            double fnlProd = 0;
            float t = period / 12;
            int h = (int) (365 * t);
            int l = h;
            while (h > 0) {
                totProd = totProd + p;
                fnlProd = fnlProd + totProd;
                h = h - 1;
            }
            double interest = (fnlProd * r) / 36500;
            retLst.add(nft.format(CbsUtil.round(amt * l, 0)));
            retLst.add(nft.format(CbsUtil.round((amt * l) + interest, 0)));
            String intAmt = nft.format(CbsUtil.round(interest, 0));
            retLst.add(intAmt);
            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
