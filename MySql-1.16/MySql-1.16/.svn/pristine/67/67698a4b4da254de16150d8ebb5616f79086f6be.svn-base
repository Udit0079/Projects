/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.other.TxnDetailsBean;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.sms.service.PropertyContainer;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "CtsManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CtsManagementFacade implements CtsManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPost43CBSRemote;
    @EJB
    private CommonReportMethodsRemote commonReportRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnRemote;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat nf = new DecimalFormat("#.##");
    Date date = new Date();

    /*Start of CTS Transaction*/
    public List getBranch() {
        List list = em.createNativeQuery("SELECT ALPHACODE,BRNCODE FROM branchmaster WHERE ALPHACODE NOT IN('CELL')").getResultList();
        return list;
    }

    public String getOrgnBranch(String alphaCode) {
        List list = em.createNativeQuery("SELECT  BRNCODE FROM branchmaster where ALPHACODE='" + alphaCode + "'").getResultList();
        Vector v = (Vector) list.get(0);
        return v.get(0).toString();
    }

    public List getAcctCode() {
        List list = em.createNativeQuery("SELECT  ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN('SB','PO','CA')").getResultList();
        return list;
    }

    public String fnABBGetCtsDetails(String curDt, String branchAlphaCode, Integer scheduleNo) throws ApplicationException {
        List detailsList = new ArrayList();
        String PRDATE, CHQ_NO, PR_BANKCODE, PR_BANKNAME, DBANKCODE, CTS_BRNCODE, IMG_CODE, BRNCODE, DT, MICR, CODE, CODENO, RBIREF_NO, destBrnCode;
        double AMOUNT;
        try {
            destBrnCode = commonReportRemote.getBrncodeByAlphacode(branchAlphaCode);

            if (ftsPost43CBSRemote.getCodeForReportName("CTS-SPONSOR") == 1) {
                String sno, dt, pbankcode, dbankcode, clgdt, amount, chqno, rbirefno, tc, other, baseacno, cts_branchcode, details, enterby, branchcode, schedule_no, original_zip_name;

                List dataList = em.createNativeQuery("SELECT sno, dt, pbankcode, dbankcode, date_format(clgdt,'%Y%m%d') as clgdate, amount, chqno, "
                        + "rbirefno, tc, other, baseacno, cts_branchcode, details, enterby, branchcode,schedule_no, original_zip_name "
                        + "FROM cts_upload_txt_cell where dt='" + curDt + "' and schedule_no = " + scheduleNo + " and rbirefno not in"
                        + "(select rbirefno from cts_clg_in_entry where dt='" + curDt + "'  and schedule_no= 1) order by chqno").getResultList();
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector v2 = (Vector) dataList.get(i);

                        sno = v2.get(0).toString();
                        dt = v2.get(1).toString();
                        pbankcode = v2.get(2).toString();
                        dbankcode = v2.get(3).toString();
                        clgdt = v2.get(4).toString();
                        amount = v2.get(5).toString();
                        chqno = v2.get(6).toString();
                        rbirefno = v2.get(7).toString();
                        tc = v2.get(8).toString();
                        other = v2.get(9).toString();
                        baseacno = v2.get(10).toString();
                        cts_branchcode = v2.get(11).toString();
                        details = v2.get(12).toString();
                        enterby = v2.get(13).toString();
                        branchcode = v2.get(14).toString();
                        schedule_no = v2.get(15).toString();
                        original_zip_name = v2.get(16).toString();

                        MICR = pbankcode.substring(0, 3);
                        CODE = pbankcode.substring(3, 6);
                        CODENO = pbankcode.substring(6);

                        List list3 = em.createNativeQuery("select bankname from clg_bankdirectory where micr = '" + MICR + "' and "
                                + "code = '" + CODE + "' and codeno = '" + CODENO + "'").getResultList();
                        if (list3.size() <= 0) {
                            detailsList = new ArrayList();
                            detailsList.add("Bankname does not exists for code " + MICR + " " + CODE + " " + CODENO);
                            return detailsList.toString();
                        }

                        Vector v3 = (Vector) list3.get(0);
                        PR_BANKNAME = v3.get(0).toString();

                        detailsList.add(clgdt);
                        detailsList.add(dt);
                        detailsList.add(amount);
                        detailsList.add(chqno);

                        detailsList.add(pbankcode);
                        detailsList.add(details);
                        detailsList.add(dbankcode);

                        detailsList.add(cts_branchcode);
                        detailsList.add(rbirefno);
                        detailsList.add(rbirefno);

//                        detailsList.add(PRDATE);
//                        detailsList.add(DT);
//                        detailsList.add(AMOUNT);
//                        detailsList.add(CHQ_NO);
//                        detailsList.add(PR_BANKCODE);
//                        detailsList.add(PR_BANKNAME);
//                        detailsList.add(DBANKCODE);
//                        detailsList.add(CTS_BRNCODE);
//                        detailsList.add(IMG_CODE);
//                        detailsList.add(RBIREF_NO);
                    }
                }
            } else {
                List dataList = em.createNativeQuery("select date_format(a.prdate,'%Y%m%d') as prdate, "
                        + "date_format(b.clgdt,'%y%m%d') as clgdate,b.amount,b.chqno,b.pbankcode,b.dbankcode,b.branchcode,"
                        + "concat('P',substring(date_format(a.prdate,'%Y%m%d'),7,2),substring(date_format(a.prdate,'%Y%m%d'),5,2), "
                        + "substring(date_format(a.prdate,'%Y%m%d'),1,4),rtrim(a.remarks),substring(a.truncatingrtno,7,3)) "
                        + "as imagename,b.rbirefno as uniqueno from cts_upload_pxf_cell a,cts_upload_txt_cell b"
                        + " where b.dt='" + curDt + "' and a.dt='" + curDt + "' and b.schedule_no =" + scheduleNo + " and "
                        + "a.schedule_no =" + scheduleNo + " and a.serialno=b.chqno and "
                        + "b.branchcode=" + Integer.parseInt(destBrnCode) + " and a.branchcode=" + Integer.parseInt(destBrnCode) + " and substring(a.itemseqno,5,10)=b.rbirefno "
                        + "and b.rbirefno not in(select rbirefno from cts_clg_in_entry where dt='" + curDt + "' and "
                        + "dest_branch='" + destBrnCode + "' and schedule_no=" + scheduleNo + ") order by b.chqno").getResultList();

                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector v2 = (Vector) dataList.get(i);
                        PRDATE = v2.get(0).toString();
                        DT = v2.get(1).toString();
                        AMOUNT = Double.parseDouble(v2.get(2).toString());
                        CHQ_NO = v2.get(3).toString();
                        PR_BANKCODE = v2.get(4).toString();
                        DBANKCODE = v2.get(5).toString();
                        CTS_BRNCODE = v2.get(6).toString();
                        IMG_CODE = v2.get(7).toString();
                        RBIREF_NO = v2.get(8).toString();

                        MICR = PR_BANKCODE.substring(0, 3);
                        CODE = PR_BANKCODE.substring(3, 6);
                        CODENO = PR_BANKCODE.substring(6);

                        List list3 = em.createNativeQuery("select bankname from clg_bankdirectory where micr = '" + MICR + "' and "
                                + "code = '" + CODE + "' and codeno = '" + CODENO + "'").getResultList();
                        if (list3.size() <= 0) {
                            detailsList = new ArrayList();
                            detailsList.add("Bankname does not exists for code " + MICR + " " + CODE + " " + CODENO);
                            return detailsList.toString();
                        }

                        Vector v3 = (Vector) list3.get(0);
                        PR_BANKNAME = v3.get(0).toString();
                        detailsList.add(PRDATE);
                        detailsList.add(DT);
                        detailsList.add(AMOUNT);
                        detailsList.add(CHQ_NO);
                        detailsList.add(PR_BANKCODE);
                        detailsList.add(PR_BANKNAME);
                        detailsList.add(DBANKCODE);
                        detailsList.add(CTS_BRNCODE);
                        detailsList.add(IMG_CODE);
                        detailsList.add(RBIREF_NO);
                    }
                }

            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return detailsList.toString();
    }

    @Override
    public List<TxnDetailsBean> fnABBGetCtsDetails1(String curDt, String branchAlphaCode,
            Integer scheduleNo) throws ApplicationException {
        List<TxnDetailsBean> resultList = new ArrayList<>();
        try {
            String branchCode = commonReportRemote.getBrncodeByAlphacode(branchAlphaCode);
            List datalist = em.createNativeQuery("select c.txn_id,date_format(c.inst_dt,'%d/%m/%Y'),c.inst_amt,ifnull(c.inst_no,''),"
                    + "ifnull(c.prbankcode,''),ifnull(c.img_code,''),c.em_flag,ifnull(c.schedule_no,0),ifnull(c.favor_of,'') from "
                    + "cts_clg_in_entry c where c.orgn_branch='" + branchCode + "' and c.status in(1,5) and c.dt='" + curDt + "' and "
                    + "c.schedule_no=" + scheduleNo + " and c.acno='' order by c.inst_no").getResultList();
            if (datalist.isEmpty()) {
                throw new ApplicationException("There is no data for this schedule no.");
            }
            for (int i = 0; i < datalist.size(); i++) {
                TxnDetailsBean obj = new TxnDetailsBean();
                Vector v2 = (Vector) datalist.get(i);

                obj.setTxnId(v2.get(0).toString().trim());
                obj.setInstDate(v2.get(1).toString().trim());
                obj.setAmount(new BigDecimal(v2.get(2).toString().trim()));
                obj.setChqNo(v2.get(3).toString().trim());
                obj.setPrBankCode(v2.get(4).toString().trim());
                obj.setImage(v2.get(5).toString().trim());
                obj.setRbiReferenceNo(v2.get(5).toString().trim());
                obj.setEmflag(Integer.parseInt(v2.get(6).toString().trim()));
                obj.setScheduleNo(Integer.parseInt(v2.get(7).toString().trim()));
                obj.setInFavourOf(v2.get(8).toString().trim());
                obj.setSrNo(i + 1);

                List micrList = em.createNativeQuery("select b.bankname,b.branch from clg_bankdirectory b "
                        + "where b.micr = '" + obj.getPrBankCode().substring(0, 3) + "' and "
                        + "b.code = '" + obj.getPrBankCode().substring(3, 6) + "' and "
                        + "b.codeno = '" + obj.getPrBankCode().substring(6) + "'").getResultList();
                if (micrList.isEmpty()) {
                    throw new ApplicationException("Please define BankName for MICR " + obj.getPrBankCode());
                }

                Vector ele = (Vector) micrList.get(0);
                obj.setPrBankName(ele.get(0).toString().trim());
                obj.setPrBankAddress(ele.get(1).toString().trim());

                resultList.add(obj);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    public List getMicrDetails(String bankCode1, String bankCode2, String bankCode3) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("SELECT BANKNAME,BRANCH FROM clg_bankdirectory WHERE MICR='" + bankCode1 + "' AND CODE='" + bankCode2 + "' AND CODENO='" + bankCode3 + "'").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }
    /*
     *
     */

    public String getClngDetails(String accNo) throws ApplicationException {
        String ACNAME = null, JTNAME = null, CLEARBAL = "", UNCLEARBAL, OPER_MODE = null, DEST_BRNID, COMP_FLAG, MSGACCSTATUS, acctNature;
        String msg = new String();
        int ty = 1, OPER_MODE_CODE;
        List dtlList = new ArrayList();

        List list1 = new ArrayList();
        try {
            MSGACCSTATUS = ftsPost43CBSRemote.ftsAcnoValidate(accNo, ty, "");
            if (!MSGACCSTATUS.equalsIgnoreCase("true")) {
                return msg = MSGACCSTATUS;
            }
            DEST_BRNID = ftsPost43CBSRemote.getAccountCode(accNo);
            String acCode = ftsPost43CBSRemote.getAccountCode(accNo);
            List acctNat = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acCode + "'").getResultList();
            Vector vecAcctNat = (Vector) acctNat.get(0);
            acctNature = vecAcctNat.get(0).toString();
            if (acctNature.equals("CA") || acctNature.equals("SB")) {
                list1 = em.createNativeQuery("SELECT CUSTNAME,OPERMODE,ifnull(JTNAME1,'') FROM accountmaster WHERE ACNO = '" + accNo + "'").getResultList();
                if (!list1.isEmpty()) {
                    Vector v1 = (Vector) list1.get(0);
                    ACNAME = v1.get(0).toString();
                    OPER_MODE_CODE = Integer.parseInt(v1.get(1).toString());
                    List list2 = em.createNativeQuery("SELECT DESCRIPTION FROM codebook WHERE GROUPCODE=4 AND CODE=" + OPER_MODE_CODE + "").getResultList();
                    Vector v2 = (Vector) list2.get(0);
                    OPER_MODE = v2.get(0).toString();
                    JTNAME = v1.get(2).toString();
                }
            } else if (acctNature.equals("PO")) {
                dtlList.add("TRUE");
                return msg = dtlList.toString();
            } else {
                return msg = "ACCOUNT NO DOES NOT EXIST";
            }

            if (acctNature.equalsIgnoreCase("SB")) {
                List list3 = em.createNativeQuery("SELECT ROUND(ifnull(BALANCE,0),2) FROM reconbalan WHERE ACNO = '" + accNo + "'").getResultList();
                if (!list3.isEmpty()) {
                    Vector v3 = (Vector) list3.get(0);
                    CLEARBAL = v3.get(0).toString();
                }
            } else if (acctNature.equalsIgnoreCase("CA")) {
                List list4 = em.createNativeQuery("SELECT ROUND(ifnull(BALANCE,0),2) FROM ca_reconbalan WHERE ACNO = '" + accNo + "'").getResultList();
                if (!list4.isEmpty()) {
                    Vector v4 = (Vector) list4.get(0);
                    CLEARBAL = v4.get(0).toString();
                }
            } else {
                CLEARBAL = "0";
            }

            List list5 = em.createNativeQuery("SELECT ROUND(ifnull(TxnInstAmt,0),2) FROM clg_ow_shadowbal WHERE ACNO = '" + accNo + "'").getResultList();
            if (!list5.isEmpty()) {
                Vector v5 = (Vector) list5.get(0);
                UNCLEARBAL = v5.get(0).toString();
            } else {
                UNCLEARBAL = "0";
            }

            dtlList.add(ACNAME);
            dtlList.add(JTNAME);
            dtlList.add(CLEARBAL);
            dtlList.add(UNCLEARBAL);
            dtlList.add(OPER_MODE);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg = dtlList.toString();
    }

    public String getSeqNo(String instNo, String instAmt, String instDate, String aplhaCode) throws ApplicationException {

        try {
            List list1 = em.createNativeQuery("SELECT SEQNO,STATUS,INFAVOUROF,CUSTNAME FROM bill_po WHERE INSTNO='" + instNo + "' AND AMOUNT='" + instAmt + "' AND date_format(VALIDATIONDT,'%Y%m%d')='" + instDate + "' AND PAYABLEAT='" + aplhaCode + "'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("EITHER THERE IS NO SEQ. NO OR INCORRECT ENTRIES");
            }
            Vector v1 = (Vector) list1.get(0);
            String seqNo = v1.get(0).toString();
            String instStatus = v1.get(1).toString();
            String inFavourOf = v1.get(2).toString();
            String custName = v1.get(3).toString();
//                dtlList.add(SEQ_NO);
//                dtlList.add(INFAVOUR_OF);
//                dtlList.add(CUST_NAME);
            if (instStatus.equalsIgnoreCase("PAID")) {
                throw new ApplicationException("THIS SEQUENCE NO. HAS BEEN ALREADY PAID.");
            }
            if (instStatus.equalsIgnoreCase("CANCELLED")) {
                throw new ApplicationException("THIS SEQUENCE NO. HAS BEEN CANCELLED.");
            }
            return seqNo + "~`" + inFavourOf + "~`" + custName;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        
    }

    public String saveTxnDetails(String TXN_ID, String BATCH_NO, String ACNO, String INST_NO, String INST_AMT, String INST_DT,
            String FAVOR_OF, String BANK_NAME, String BANK_ADD, String ENTER_BY, String SEQ_NO, String ORGN_BRANCH,
            String DEST_BRANCH, String DT, String imgcode, String custname, String oper_mode, String prbankcode,
            String rbirefno, String userdetails, Integer scheduleNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        String msg = new String();
        String INST_DT1, REMARKS, AUTH_BY, PAY_BY, SCREEN_FLAG, EM_FLAG, SECOND_AUTH_BY, auth, CIRCLE_TYPE,
                SUBSTATUS, STATUS, DETAILS;

        int VCH_NO = 0, REASON_FOR_CANCEL = 0, IY = 0;
        double seqNo = 0;
        try {
            ut.begin();
            if (TXN_ID == null) {
                ut.rollback();
                return msg = "TXN_ID PASSED NULL OR BLANK";

            }
            if (BATCH_NO == null) {
                ut.rollback();
                return msg = "BATCH_NO PASSED NULL OR BLANK";
            }
            if (ACNO == null) {
                ut.rollback();
                return msg = "ACNO PASSED NULL OR BLANK";
            }
            if (INST_NO == null) {
                ut.rollback();
                return msg = "INST_NO PASSED NULL OR BLANK";
            }
            if (INST_AMT == null) {
                ut.rollback();
                return msg = "INST_AMT PASSED NULL OR BLANK";
            }
            if (INST_DT == null) {
                ut.rollback();
                return msg = "INST_DT PASSED NULL OR BLANK";
            }
            if (BANK_NAME == null) {
                ut.rollback();
                return msg = "BANK_NAME PASSED NULL OR BLANK";
            }
            if (BANK_ADD == null) {
                ut.rollback();
                return msg = "BANK_ADD PASSED NULL OR BLANK";
            }
            if (ORGN_BRANCH == null) {
                ut.rollback();
                return msg = "ORGN_BRANCH PASSED NULL OR BLANK";
            }
            if (DEST_BRANCH == null) {
                ut.rollback();
                return msg = "DEST_BRANCH PASSED NULL OR BLANK";
            }
            if (scheduleNo == null || scheduleNo == 0) {
                ut.rollback();
                return msg = "scheduleNo can not be null or blank.";
            }
            if (userdetails == null) {
                userdetails = "";
            }
            if (FAVOR_OF == null) {
                FAVOR_OF = userdetails;
            } else {
                FAVOR_OF = userdetails + " " + FAVOR_OF;
            }

            REMARKS = "";
            AUTH_BY = "";
            if (ACNO.substring(2, 4).equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                seqNo = Double.parseDouble(SEQ_NO);
            }
            PAY_BY = "1";
            SCREEN_FLAG = "C";
            EM_FLAG = "B";
            SECOND_AUTH_BY = "CTS_USER";
            auth = "";
            CIRCLE_TYPE = "A";
            SUBSTATUS = "U";

            if (getClngDetails(ACNO).contains("[")) {
                STATUS = "1";
            } else {
                STATUS = "3";
            }
            if (getClngDetails(ACNO).contains("[")) {
                DETAILS = "CLEARED";
            } else {
                DETAILS = getClngDetails(ACNO);
            }
            if (ORGN_BRANCH.length() == 1) {
                ORGN_BRANCH = "0" + ORGN_BRANCH;
            }
            if (DEST_BRANCH.length() == 1) {
                DEST_BRANCH = "0" + DEST_BRANCH;
            }

            List list1 = em.createNativeQuery("select acno,inst_no,inst_dt from cts_clg_in_entry "
                    + "where acno='" + ACNO + "' and rbirefno='" + rbirefno + "' and "
                    + "status in(1,3)").getResultList();
            if (!list1.isEmpty()) {
                ut.rollback();
                return msg = "Entry For This Cheque Has Been Already Made";

            } else {
                List list2 = new ArrayList();
                if (ftsPost43CBSRemote.getCodeForReportName("CTS-SPONSOR") == 1) {
                    list2 = em.createNativeQuery("select ifnull(max(vch_no),0)+1 from cts_clg_in_entry where "
                            + "ORGN_BRANCH='" + ORGN_BRANCH + "' and dt='" + DT + "' and "
                            + "schedule_no=" + scheduleNo + "").getResultList();
                } else {
                    list2 = em.createNativeQuery("select ifnull(max(vch_no),0)+1 from cts_clg_in_entry where "
                            + "dest_branch='" + DEST_BRANCH + "' and dt='" + DT + "' and "
                            + "schedule_no=" + scheduleNo + "").getResultList();
                }

                Vector v2 = (Vector) list2.get(0);
                VCH_NO = Integer.parseInt(v2.get(0).toString());

                INST_DT1 = INST_DT;
                Query insert = em.createNativeQuery("insert into cts_clg_in_entry(TXN_ID,BATCH_NO,ACNO,INST_NO,INST_AMT,INST_DT,FAVOR_OF,BANK_NAME,"
                        + "BANK_ADD,REMARKS,REASON_FOR_CANCEL,ENTER_BY,AUTH_BY,STATUS,SEQ_NO,ORGN_BRANCH,DEST_BRANCH,DT,TRAN_TIME,PAY_BY,IY,"
                        + "SCREEN_FLAG,DETAILS,EM_FLAG,VCH_NO,SECOND_AUTH_BY,AUTH,CIRCLE_TYPE,SUBSTATUS,IMG_CODE,CUSTNAME,OPER_MODE,USERDETAILS,"
                        + "PRBANKCODE,rbirefno,SCHEDULE_NO,doc_type) "
                        + "values( '" + TXN_ID + "',  " + new BigDecimal(BATCH_NO).longValue() + ",  '" + ACNO + "',  "
                        + "'" + INST_NO + "',  '" + INST_AMT + "',  '" + INST_DT1 + "',  '" + FAVOR_OF + "',"
                        + "'" + BANK_NAME + "',  '" + BANK_ADD + "',  '" + REMARKS + "',  " + REASON_FOR_CANCEL + ",  "
                        + "'" + ENTER_BY + "',  '" + AUTH_BY + "',  '" + STATUS + "'," + seqNo + ",  '" + ORGN_BRANCH + "',  "
                        + "'" + DEST_BRANCH + "',  '" + DT + "',current_timestamp,'" + PAY_BY + "',  '" + IY + "',  "
                        + "'" + SCREEN_FLAG + "',  '" + DETAILS + "','" + EM_FLAG + "',  '" + VCH_NO + "',"
                        + "'" + SECOND_AUTH_BY + "',  '" + auth + "',  '" + CIRCLE_TYPE + "',  '" + SUBSTATUS + "',  "
                        + "'" + imgcode + "',  '" + custname + "','" + oper_mode + "',  'ENTERED',  '" + prbankcode + "',  "
                        + "'" + rbirefno + "'," + scheduleNo + ",'')");
                int insertResult = insert.executeUpdate();
                if (insertResult <= 0) {
                    ut.rollback();
                    return msg = "Problem in data insertion.";
                }
            }
            ut.commit();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg = "TRUE" + VCH_NO;
    }

    @Override
    public String npciSaveClgInwardDetail(String txnId, String accNo, String instNo, BigDecimal instAmount,
            String instDate, String details, String inFavourOf, String bankName, String bankAddress, String userName,
            String seqNo, String custName, String opMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = em.createNativeQuery("insert into cts_clg_in_entry_history (txn_id, batch_no, acno, inst_no, "
                    + "inst_amt, inst_dt, favor_of, bank_name, bank_add, remarks, reason_for_cancel, enter_by, auth_by, status, "
                    + "seq_no, orgn_branch, dest_branch, dt, tran_time, pay_by, iy, screen_flag, details, em_flag, vch_no, "
                    + "second_auth_by, auth, circle_type, substatus, img_code, custname, oper_mode, userdetails, prbankcode, "
                    + "rbirefno, schedule_no, fh_creation_date, fh_file_id, fh_settlement_date, item_payor_bank_routno, "
                    + "item_seq_no, item_trans_code, item_san, binary_data_file_name, binary_img_file_name, fh_vno, "
                    + "fh_test_file_indicator, item_prment_date, item_cycleno, addenda_bofdroutno, addenda_bofdbusdate, "
                    + "addenda_depositoracct, addenda_ifsc, modified_flag,doc_type) "
                    + "select txn_id, batch_no, acno, inst_no, inst_amt, inst_dt, favor_of, bank_name, bank_add, remarks, "
                    + "reason_for_cancel, enter_by, auth_by, status, seq_no, orgn_branch, dest_branch, dt, tran_time, pay_by, "
                    + "iy, screen_flag, details, em_flag, vch_no, second_auth_by, auth, circle_type, substatus, img_code, "
                    + "custname, oper_mode, userdetails, prbankcode, rbirefno, schedule_no, fh_creation_date, fh_file_id, "
                    + "fh_settlement_date, item_payor_bank_routno, item_seq_no, item_trans_code, item_san, binary_data_file_name, "
                    + "binary_img_file_name, fh_vno, fh_test_file_indicator, item_prment_date, item_cycleno, addenda_bofdroutno, "
                    + "addenda_bofdbusdate, addenda_depositoracct, addenda_ifsc, modified_flag,doc_type from cts_clg_in_entry where "
                    + "txn_id='" + txnId + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In History Maintenence.");
            }

            n = em.createNativeQuery("update cts_clg_in_entry set acno='" + accNo + "',inst_no='" + instNo + "',"
                    + "inst_amt=" + instAmount + ",inst_dt='" + ymd.format(dmyOne.parse(instDate)) + "',details='" + details + "',"
                    + "favor_of='" + inFavourOf + "',bank_name='" + bankName + "',bank_add='" + bankAddress + "',"
                    + "second_auth_by='" + userName + "',seq_no =" + Double.parseDouble(seqNo) + ","
                    + "dest_branch='" + accNo.substring(0, 2).trim() + "',modified_flag='Y',custname='" + custName + "',"
                    + "oper_mode='" + opMode + "',tran_time=now(),status=1 where txn_id ='" + txnId + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in data modification in clearing entry.");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String getBranchCtsFolder(String orgnCode) throws ApplicationException {
        String folderName = null;
        try {
            List folderNameList = em.createNativeQuery("select branchcode from bnkadd where alphacode=(select alphacode from branchmaster where brncode=cast('" + orgnCode + "' as unsigned))").getResultList();
            Vector element = (Vector) folderNameList.get(0);
            folderName = element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return folderName;
    }

    public String dataVerification(int branchCode, int scheduleNo) throws ApplicationException {
        String msg = "";
        try {
            String curDt = ymd.format(date);

            List countList = em.createNativeQuery("select count(*) from cts_upload_txt_cell "
                    + "where dt='" + curDt + "' and branchcode=" + branchCode + " and "
                    + "schedule_no=" + scheduleNo + "").getResultList();
            Vector element = (Vector) countList.get(0);
            int txtCount = Integer.parseInt(element.get(0).toString());

            countList = em.createNativeQuery("select count(*) from cts_upload_pxf_cell "
                    + "where dt='" + curDt + "' and branchcode=" + branchCode + " and "
                    + "schedule_no=" + scheduleNo + "").getResultList();
            element = (Vector) countList.get(0);
            int pxfCount = Integer.parseInt(element.get(0).toString());

            if (txtCount != pxfCount) {
                return msg = "There is wrong data in zip file.";
            }

            countList = em.createNativeQuery("select count(*) from cts_upload_pxf_cell a,cts_upload_txt_cell b "
                    + "where a.dt=b.dt and b.dt='" + curDt + "' and a.branchcode=b.branchcode and "
                    + "b.branchcode=" + branchCode + " and a.schedule_no=b.schedule_no and "
                    + "b.schedule_no=" + scheduleNo + " and a.serialno=b.chqno and "
                    + "substring(a.itemseqno,5,10)=b.rbirefno").getResultList();
            element = (Vector) countList.get(0);
            int txnCount = Integer.parseInt(element.get(0).toString());

            if (txtCount != txnCount) {
                return msg = "You will get some less data for posting due to some wrong data coming in zip file.";
            }
            msg = "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public int getDestBrnCode(String alphaCode) throws ApplicationException {
        int brnCode = 500;
        try {
            List brnCodeList = em.createNativeQuery("select brncode from branchmaster where alphacode='" + alphaCode + "'").getResultList();
            Vector element = (Vector) brnCodeList.get(0);
            brnCode = Integer.parseInt(element.get(0).toString());

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brnCode;
    }

    public String getBranchAplhaCode(int brnCode) throws ApplicationException {
        String alphaCode = " ";
        try {
            List alphaCodeList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + brnCode + "").getResultList();
            Vector element = (Vector) alphaCodeList.get(0);
            alphaCode = element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return alphaCode;
    }

    public String chequeValidate(String acno, String chqNo) throws ApplicationException {
        try {
            if (acno.equalsIgnoreCase("")) {
                return "A/c No can not be blank";
            } else if (chqNo.equalsIgnoreCase("")) {
                return "Cheque No can not be blank";
            }
            String nature = ftsPost43CBSRemote.getAccountNature(acno);
            if (nature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                return "true";
            }
            String tableName = "chbook_sb";
            if (nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                tableName = "chbook_ca";
            }
            List chequeStatusList = em.createNativeQuery("select statusflag from " + tableName + " where acno='" + acno + "' and chqno = cast('"
                    + chqNo + "' as decimal)").getResultList();
            if (chequeStatusList.size() > 0) {
                Vector chqElement = (Vector) chequeStatusList.get(0);
                String status = chqElement.get(0).toString();
                if (status.equalsIgnoreCase("F")) {
                    return "true";
                } else if (status.equalsIgnoreCase("S")) {
                    return "Cheque " + chqNo + " has been marked for stop payement";
                } else if (status.equalsIgnoreCase("U")) {
                    return "Cheque " + chqNo + " has already been used";
                }
            } else {
                return "Cheque " + chqNo + " does not exists for A/c no:- " + acno;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "ture";
    }
    /*End of CTS Transaction*/

    /*Start of CTS Authorization*/
    public String getBranchName(int originBrCode) throws ApplicationException {
        String branchName = null;
        try {
            List list = em.createNativeQuery("SELECT BRANCHNAME FROM branchmaster WHERE BRNCODE=" + originBrCode + "").getResultList();
            Vector element = (Vector) list.get(0);
            branchName = element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return branchName;
    }

    public List getReason() throws ApplicationException {
        List reason = null;
        try {
//            reason = em.createNativeQuery("SELECT DISTINCT(DESCRIPTION) FROM codebook WHERE GROUPCODE=13").getResultList();
            reason = em.createNativeQuery("select code,ifnull(description,'') from codebook where groupcode=13").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return reason;
    }

    public List getGridDetails(Integer branchCode) throws ApplicationException {
        List tableDetails = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDt = sdf.format(date);
        try {
            tableDetails = em.createNativeQuery("SELECT A.BATCH_NO,A.VCH_NO,A.ACNO,A.INST_AMT,A.INST_NO,date_format(A.INST_DT,'%d/%m/%Y'),A.PRBANKCODE,A.STATUS,A.OPER_MODE,UPPER(A.ENTER_BY), "
                    + " UPPER(A.DETAILS),UPPER(A.FAVOR_OF),UPPER(A.BANK_NAME), UPPER(A.BANK_ADD),A.TXN_ID,A.ORGN_BRANCH,A.DEST_BRANCH,A.SEQ_NO,UPPER(A.AUTH_BY), "
                    + " A.PAY_BY,1,UPPER(A.SCREEN_FLAG),UPPER(A.SECOND_AUTH_BY),'A',A.REASON_FOR_CANCEL,A.TRAN_TIME,date_format(A.DT,'%Y%m%d'),1,A.SUBSTATUS,1, "
                    + " A.IMG_CODE, A.CUSTNAME, ifnull(cm.mailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                    + " FROM cts_clg_in_entry A left join customerid ci on A.acno = ci.acno "
                    + " left join cbs_customer_master_detail cm on ci.CustId = cast(cm.customerid as unsigned) "
                    + " left join  branchmaster br on  br.brncode= " + branchCode
                    + " WHERE  date_format(A.DT,'%Y%m%d') = '" + currentDt + "' AND A.STATUS IN ('1','3') "
                    + " AND CAST(A.DEST_BRANCH AS unsigned)=" + branchCode + " AND SUBSTATUS<>'L' AND SCHEDULE_NO=0 ORDER BY A.VCH_NO").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableDetails;
    }

    @Override
    public String chequesAuthorization(String acno, String enterBy, String orgnCode, String destCode,
            String details, String secondAuthBy, String authBy, String instNo, String screenFlag,
            String payee, String bankname, String bankAddress, Integer status, Float seqNo, Integer payBy,
            Integer reasonForCancel, Integer vchNo, Integer batchNo, String txnId, double instAmt, String dt,
            String instDt, Integer tranType, String subStatus, Integer requestType, String circleType,
            Integer ty, String remarks, String userDetails, String exccedFlag, int ctsSponsor,
            String otherReasonText, String custSate, String branchState) throws ApplicationException {
        String msg = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            if (status == 2 || status == 4 || status == 5) {
                if (status == 2 || status == 4) {
                    List userAuthLimit = em.createNativeQuery("SELECT COALESCE(CLGDEBIT,0.0) FROM securityinfo WHERE USERID='" + authBy + "' AND BRNCODE='" + orgnCode + "'").getResultList();
                    if (userAuthLimit.size() > 0) {
                        for (int t = 0; t < userAuthLimit.size(); t++) {
                            Vector element = (Vector) userAuthLimit.get(t);
                            double userLimit = Double.parseDouble(element.get(0).toString());
                            if (userLimit == 0.0) {
                                return msg = "Your passing limit is less than withdrawal amount. You can not authorize this transaction";
                            } else if (instAmt > userLimit) {
                                return msg = "Your passing limit is less than withdrawal amount. You can not authorize this transaction";
                            }
                        }
                    } else {
                        return msg = "Your passing limit is less than withdrawal amount. You can not authorize this transaction";
                    }
                }

                List list = em.createNativeQuery("SELECT SUBSTATUS,status FROM cts_clg_in_entry WHERE TXN_ID = '" + txnId + "' AND ACNO = '" + acno + "'").getResultList();
                if (list.size() > 0) {
                    Vector element = (Vector) list.get(0);
                    String sub = element.get(0).toString();
                    int dbStatus = Integer.parseInt(element.get(1).toString());
                    if (status == 5 && (dbStatus == 2 || dbStatus == 4)) {
                        return "This entry is already Passed or Return.";
                    }
                    if ((status == 2 || status == 4) && !(dbStatus == 1 || dbStatus == 3)) {
                        return "This particular entry is already Passed or Return.";
                    }
                    if (sub.equals("L")) {
                        return msg = "This Entry is Under Processing, Authorization Aborted";
                    } else {
                        ut.begin();

                        Query q1 = em.createNativeQuery("UPDATE cts_clg_in_entry SET SUBSTATUS='L' WHERE TXN_ID = '" + txnId + "' AND ACNO = '" + acno + "'");
                        int no = q1.executeUpdate();
                        if (no <= 0) {
                            ut.rollback();
                            return msg = "Updation problem in CTS";
                        }

                        String msgFromSDMaker = "";
                        if (orgnCode.equalsIgnoreCase(destCode)) {
                            msgFromSDMaker = sdMakerClg(acno, enterBy, orgnCode, destCode, details, secondAuthBy, authBy,
                                    instNo, screenFlag, payee, bankname, bankAddress, status, seqNo, payBy, reasonForCancel,
                                    vchNo, batchNo, txnId, instAmt, dt, instDt, tranType, subStatus, requestType, circleType,
                                    ty, remarks, userDetails, ctsSponsor, otherReasonText, custSate, branchState);
                        } else {
                            msgFromSDMaker = remoteSdMakerClg(acno, enterBy, orgnCode, destCode, details, secondAuthBy,
                                    authBy, instNo, screenFlag, payee, bankname, bankAddress, status, seqNo, payBy,
                                    reasonForCancel, vchNo, batchNo, txnId, instAmt, dt, instDt, tranType, subStatus,
                                    requestType, circleType, ty, remarks, userDetails, ctsSponsor, otherReasonText, custSate, branchState);
                        }

                        if (msgFromSDMaker.equalsIgnoreCase("PASSED")) {
                            msg = "The cheque has been passed successfuly";
                        } else if (msgFromSDMaker.equalsIgnoreCase("RETURNED")) {
                            msg = "The cheque has been returned successfuly";
                        } else if (msgFromSDMaker.equalsIgnoreCase("DELETED")) {
                            msg = "The cheque has been deleted successfuly";
                        } else if (msgFromSDMaker.equalsIgnoreCase("PENDING")) {
                            msg = "Charges Entered in PendingCharges due to Insufficient Fund!";
                        } else {
                            msg = msgFromSDMaker;
                            ut.begin();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                            String curDate = sdf.format(date);
                            Query q2 = em.createNativeQuery("update cts_clg_in_entry set status = 3,auth_by='" + authBy + "',auth='Y',substatus='U',userdetails='HOLD',details='" + msgFromSDMaker + "' where txn_id = '" + txnId + "' and date_format(dt,'%Y%m%d') = '" + curDate + "'");
                            q2.executeUpdate();
                        }
                        ut.commit();
                        //Sending Sms In case of manual inward/cts authorization.
                        String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                                == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                        if (msgFromSDMaker.equalsIgnoreCase("PASSED")) {
                            try {
                                if (!ftsPost43CBSRemote.getAccountNature(acno).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                                    TransferSmsRequestTo trfSmsRequestTo = new TransferSmsRequestTo();
                                    trfSmsRequestTo.setMsgType("T");
                                    if (templateType.equalsIgnoreCase("indr")) {
                                        String details1 = "";
                                        if (payee.length() <= 30) {
                                            details1 = payee;
                                        } else if (payee.length() > 30) {
                                            details1 = payee.substring(1, 30);
                                        }
                                        trfSmsRequestTo.setDetails(details1);
                                        trfSmsRequestTo.setTemplate(SmsType.CLEARING_WITHDRAWAL_INDR);
                                    } else {
                                        trfSmsRequestTo.setTemplate(SmsType.CLEARING_WITHDRAWAL);
                                    }
                                    trfSmsRequestTo.setAcno(acno);
                                    trfSmsRequestTo.setTranType(1);
                                    trfSmsRequestTo.setTy(1);
                                    trfSmsRequestTo.setAmount(instAmt);
                                    trfSmsRequestTo.setDate(dmyOne.format(ymd.parse(dt)));
                                    trfSmsRequestTo.setFirstCheque(instNo);

                                    smsFacade.sendSms(trfSmsRequestTo);

                                    //CC-OD Exceed SMS
                                    if (exccedFlag.equalsIgnoreCase("exceed")) {
                                        txnRemote.sendCCODExceedSms(acno, instAmt);
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::" + instAmt);
                            }
                        } else if (msgFromSDMaker.equalsIgnoreCase("RETURNED")) {
                            try {
                                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                                tSmsRequestTo.setMsgType("PAT");
//                                tSmsRequestTo.setTemplate(SmsType.CHQ_RETURN_CLEARING_DEBIT);
                                tSmsRequestTo.setTemplate(SmsType.IW_CLG_CHQ_RETURN);
                                tSmsRequestTo.setAcno(acno);
                                tSmsRequestTo.setAmount(instAmt);
                                tSmsRequestTo.setDate(dmyOne.format(ymd.parse(dt)));
                                tSmsRequestTo.setPromoMobile(smsFacade.getSubscriberDetails(acno).getMobileNo());
                                tSmsRequestTo.setFirstCheque(instNo);
                                tSmsRequestTo.setBalFlag("Y");

                                List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                                String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                                tSmsRequestTo.setBankName(templateBankName);

                                smsFacade.sendSms(tSmsRequestTo);
                            } catch (Exception ex) {
                                System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::" + instAmt);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String sdMakerClg(String acno, String enterBy, String orgnCode, String destCode, String details, String secondAuthBy,
            String authBy, String instNo, String screenFlag, String payee, String bankname, String bankAddress, Integer status,
            Float seqNo, Integer payBy, Integer reasonForCancel, Integer vchNo, Integer batchNo, String txnId, double instAmt,
            String dt, String instDt, Integer tranType, String subStatus, Integer requestType, String circleType, Integer ty,
            String remarks, String userDetails, int ctsSponsor, String otherReasonText, String custSate, String branchState)
            throws ApplicationException {
        String msg = null, mainDetails = null;
        double totalChgToBeDed = 0d, serviceCharge = 0d;
        Map<String, Double> map = new HashMap<String, Double>();
        UserTransaction ut = context.getUserTransaction();
        try {
            if (requestType <= 0 || requestType > 4) {
                return msg = "Invalid RequestType";
            }
            if (!((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals(""))) { //For khatri to stop the checking in case of other bank
                if (acno.length() != 12 || acno.equals("")) {
                    return msg = "Invalid AccountNumber";
                }
            }

            List list = em.createNativeQuery("Select CircleType From parameterinfo_clg where "
                    + "CircleType = '" + circleType + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                String cirType = element.get(0).toString();
                if (!circleType.equalsIgnoreCase(cirType)) {
                    return msg = "Invalid CircleType";
                }
            }
            if (batchNo == null) {
                return msg = "Invalid SessionId";
            }
            if (enterBy.equals("")) {
                return msg = "Invalid User Defined";
            }
            if (authBy.equals("")) {
                return msg = "Invalid Authby Defined";
            }
            if (!((screenFlag.trim().equalsIgnoreCase("T")) || (screenFlag.trim().equalsIgnoreCase("C")))) {
                return msg = "Invalid ScreenFlag";
            }
            if (!((status == 2) || (status == 3) || (status == 4) || (status == 5))) {
                return msg = "Invalid Status";
            }
            if (vchNo <= 0) {
                return msg = "Invalid Voucher Number";
            }
            if (instAmt <= 0) {
                return msg = "Invalid Amount";
            }
            if (payBy != 1) {
                return msg = "Invalid PayBy(1 For Cheque)";
            }
            if (tranType != 1) {
                return msg = "Invalid TranType";
            }
            if (!((ty == 0) || (ty == 1))) {
                return msg = "Invalid Transaction Type";
            }

            List listOrgnAplhaCode = em.createNativeQuery("Select Alphacode from branchmaster where "
                    + "BrnCode = cast('" + orgnCode + "' as unsigned)").getResultList();
            Vector element2 = (Vector) listOrgnAplhaCode.get(0);
            String orgnAlphaCode = element2.get(0).toString();
            if (orgnAlphaCode.equalsIgnoreCase("")) {
                return msg = "Invalid Originating BranchId";
            }

            if (!((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals(""))) {
                List listDestAplhaCode = em.createNativeQuery("Select Alphacode from branchmaster where "
                        + "BrnCode = cast('" + destCode + "' as unsigned)").getResultList();
                Vector element3 = (Vector) listDestAplhaCode.get(0);
                String destAlphaCode = element3.get(0).toString();
                if (destAlphaCode.equalsIgnoreCase("")) {
                    return msg = "Invalid Destination BranchId";
                }
            }

            if (txnId.equals("")) {
                return msg = "Invalid TranId";
            }
            if (instNo.length() > 10) {
                return msg = "Invalid Instrument Number";
            }
            if (dt.equals("")) {
                return msg = "Invalid Date";
            }
            if (instDt.equals("")) {
                return msg = "Invalid Instrument Date";
            }

            instNo = fnLpading(instNo, 10, "0");
            String clgAdjAcno = "", glAcnoToBeCr = "", clgCellAcno = "";
            List listGLAcnoToBeUsed = em.createNativeQuery("select GlInClgRet,GlInClgRetChg,InRetChg,GlInClg from parameterinfo_clg "
                    + "Where CircleType='" + circleType + "'").getResultList();
            Vector element1 = (Vector) listGLAcnoToBeUsed.get(0);
            String clgAdjAcnoHead = element1.get(0).toString();
            if (clgAdjAcnoHead.length() < 6) {
                int padLength = 6 - clgAdjAcnoHead.length();
                for (int i = 0; i < padLength; i++) {
                    clgAdjAcnoHead = "0" + clgAdjAcnoHead;
                }
                clgAdjAcno = destCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgAdjAcnoHead + "01";
            } else {
                clgAdjAcno = destCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgAdjAcnoHead + "01";
            }

            String clgCellAcnoHead = element1.get(3).toString();
            if (clgCellAcnoHead.length() < 6) {
                int padLength = 6 - clgCellAcnoHead.length();
                for (int i = 0; i < padLength; i++) {
                    clgCellAcnoHead = "0" + clgCellAcnoHead;
                }
            }
            clgCellAcno = destCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgCellAcnoHead + "01";

            double chgToBeDed = 0;
            if (!((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals(""))) {
                if (!acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    List selectRetChg = ftsPost43CBSRemote.getChequeReturnCharge("CHEQUE-RETURN-CHG", "IW-CHEQUE", instAmt, ftsPost43CBSRemote.getAccountCode(acno));
                    //List selectRetChg = ftsPost43CBSRemote.getCheqRetChgAndHead("IW Cheque Return Charges", ftsPost43CBSRemote.getAccountCode(acno));
                    if (selectRetChg.isEmpty()) {
                        ut.rollback();
                        return msg = "Please fill IW Cheque Return Charges.";
                    }
                    Vector vecRetChg = (Vector) selectRetChg.get(0);
                    chgToBeDed = Double.parseDouble(vecRetChg.get(1).toString());
                    glAcnoToBeCr = destCode + vecRetChg.get(0).toString() + "01";
                }
            }

            List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname "
                    + "like 'STAXMODULE_ACTIVE'").getResultList();
            Vector element4 = (Vector) listCode.get(0);
            Integer code = Integer.parseInt(element4.get(0).toString());
            if (code == 1) {
                totalChgToBeDed = chgToBeDed;
                if (custSate.equalsIgnoreCase(branchState)) {
                    map = ibRemote.getTaxComponent(chgToBeDed, dt);
                } else {
                    map = ibRemote.getIgstTaxComponent(chgToBeDed, dt);
                }
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    serviceCharge = serviceCharge + Double.parseDouble(entry.getValue().toString());
                }
                totalChgToBeDed = totalChgToBeDed + serviceCharge;
            } else {
                totalChgToBeDed = chgToBeDed;
            }

            List chequeStatusList = em.createNativeQuery("SELECT STATUS FROM cts_clg_in_entry WHERE TXN_ID='" + txnId + "' AND "
                    + "ACNO='" + acno + "'").getResultList();
            Vector element6 = (Vector) chequeStatusList.get(0);
            Integer chequeStatus = Integer.parseInt(element6.get(0).toString());
            if (chequeStatus == 2) {
                return msg = "This Cheque have been already Passed.";
            } else if (chequeStatus == 4) {
                return msg = "This Cheque have been already Returned.";
            } else if (chequeStatus == 5) {
                return msg = "This Cheque have been already Deleted.";
            }

            instDt = instDt.substring(6) + instDt.substring(3, 5) + instDt.substring(0, 2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDate = sdf.format(date);

            if (status == 5) {
                Query insertCheque = em.createNativeQuery("insert into cts_clg_in_entry_history (txn_id, batch_no, acno, inst_no, "
                        + "inst_amt, inst_dt, favor_of, bank_name, bank_add, remarks, reason_for_cancel, enter_by, auth_by, status, "
                        + "seq_no, orgn_branch, dest_branch, dt, tran_time, pay_by, iy, screen_flag, details, em_flag, vch_no, "
                        + "second_auth_by, auth, circle_type, substatus, img_code, custname, oper_mode, userdetails, prbankcode, "
                        + "rbirefno, schedule_no, fh_creation_date, fh_file_id, fh_settlement_date, item_payor_bank_routno, "
                        + "item_seq_no, item_trans_code, item_san, binary_data_file_name, binary_img_file_name, fh_vno, "
                        + "fh_test_file_indicator, item_prment_date, item_cycleno, addenda_bofdroutno, addenda_bofdbusdate, "
                        + "addenda_depositoracct, addenda_ifsc, modified_flag,doc_type) "
                        + "select txn_id, batch_no, acno, inst_no, inst_amt, inst_dt, favor_of, bank_name, bank_add, remarks, "
                        + "reason_for_cancel, enter_by, auth_by, status, seq_no, orgn_branch, dest_branch, dt, tran_time, pay_by, "
                        + "iy, screen_flag, details, em_flag, vch_no, second_auth_by, auth, circle_type, substatus, img_code, "
                        + "custname, oper_mode, userdetails, prbankcode, rbirefno, schedule_no, fh_creation_date, fh_file_id, "
                        + "fh_settlement_date, item_payor_bank_routno, item_seq_no, item_trans_code, item_san, binary_data_file_name, "
                        + "binary_img_file_name, fh_vno, fh_test_file_indicator, item_prment_date, item_cycleno, addenda_bofdroutno, "
                        + "addenda_bofdbusdate, addenda_depositoracct, addenda_ifsc, modified_flag,doc_type from cts_clg_in_entry where "
                        + "txn_id='" + txnId + "'");
                int insno = insertCheque.executeUpdate();
                if (insno <= 0) {
                    ut.rollback();
                    return msg = "Problem in insertion";
                }

                if (ctsSponsor == 3) { //For Ramgariya
                    Query updateCheque = em.createNativeQuery("update cts_clg_in_entry set status = 5,auth_by='" + authBy + "',"
                            + "auth='Y',substatus='U',userdetails='DELETED',acno='' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                    int no = updateCheque.executeUpdate();
                    if (no <= 0) {
                        ut.rollback();
                        return msg = "Problem in updation";
                    }
                } else {
                    Query updateCheque = em.createNativeQuery("update cts_clg_in_entry set status = 5,auth_by='" + authBy + "',"
                            + "auth='Y',substatus='U',userdetails='DELETED' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                    int no = updateCheque.executeUpdate();
                    if (no <= 0) {
                        ut.rollback();
                        return msg = "Problem in updation";
                    }

                    Query deleteCheque = em.createNativeQuery("delete from cts_clg_in_entry where txn_id='" + txnId + "'");
                    int delno = deleteCheque.executeUpdate();
                    if (delno <= 0) {
                        ut.rollback();
                        return msg = "Problem in deletion";
                    }
                }
                return msg = "DELETED";
            }

            String dateValMsg = ftsPost43CBSRemote.ftsDateValidate(dt, destCode);
            if (!dateValMsg.equalsIgnoreCase("true")) {
                ut.rollback();
                Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 3 where txn_id = '" + txnId + "' and "
                        + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                q1.executeUpdate();
                return msg = dateValMsg;
            }

            if (status == 2) {
                if (ftsPost43CBSRemote.isAccountNPA(acno)) {
                    ut.rollback();
                    return "This is a NPA Account";
                }
                mainDetails = "CTS " + payee;
                String ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(acno, 1, 1, instAmt, dt, dt, enterBy, orgnCode, destCode, 0,
                        mainDetails, 0.0f, 0.0f, 0, "CTS", "Y", authBy, "A", payBy, instNo, instDt, "TDA",
                        Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "N", "", "", "");
                if (ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                    if (ctsSponsor == 2 || ctsSponsor == 3) {
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgCellAcno, 1, 0, instAmt, dt, dt, enterBy, orgnCode,
                                destCode, 0, mainDetails, 0f, 0.0f, 0, "CTS", "Y", authBy, "A",
                                3, "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A",
                                0.0f, "N", "", "", "");
                        if (ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                            Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 2,auth_by='" + authBy + "',auth='Y',"
                                    + "substatus='U',userdetails='PASSED' where txn_id = '" + txnId + "' and "
                                    + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                            q1.executeUpdate();
                            ftsPost43CBSRemote.lastTxnDateUpdation(acno);
                            return "PASSED";
                        } else {
                            ut.rollback();
                            return ftsMsg;
                        }
                    } else {
                        Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 2,auth_by='" + authBy + "',auth='Y',"
                                + "substatus='U',userdetails='PASSED' where txn_id = '" + txnId + "' and "
                                + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                        q1.executeUpdate();
                        ftsPost43CBSRemote.lastTxnDateUpdation(acno);
                        return "PASSED";
                    }
                } else {
                    ut.rollback();
                    return ftsMsg;
                }
            }

            if (status == 4) {
                if ((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals("") && reasonNotAppForCharge(userDetails).equals("false")) {
                    ut.rollback();
                    return msg = "This reason is not appropriate to return.";
                }
                Float trsno = ftsPost43CBSRemote.getTrsNo();
                if (reasonNotAppForCharge(userDetails).equalsIgnoreCase("true")
                        || acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    mainDetails = "Chq Returned. ";
                    String ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgAdjAcno, 1, 1, instAmt, dt, dt, enterBy, orgnCode,
                            destCode, 111, mainDetails, 0.0f, 0.0f, 0, "CTS", "Y", authBy, "A", 3, instNo, instDt, "TDA",
                            Float.parseFloat(vchNo.toString()), "", "", screenFlag, "", 0.0f, "N", "", "", "");
                    if (!ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
                        return msg = ftsMsg;
                    } else {
                        Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 4,auth_by='" + authBy + "',"
                                + "auth='Y',substatus='U',userdetails='" + userDetails + "',details='" + otherReasonText + "' where txn_id = '" + txnId + "' and "
                                + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                        q1.executeUpdate();
                        return msg = "RETURNED";
                    }
                } else {
                    mainDetails = "Chq Returned. ";
                    String ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgAdjAcno, 1, 1, instAmt, dt, dt, enterBy, orgnCode,
                            destCode, 111, mainDetails, 0.0f, 0.0f, 0, "CTS", "Y", authBy, "A", 3, instNo, instDt, "TDA",
                            Float.parseFloat(vchNo.toString()), "", "", screenFlag, "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        ut.rollback();
                        return msg = ftsMsg;
                    }

                    //Pending Balance Checking.
                    ftsMsg = ftsPost43CBSRemote.checkBalance(acno, totalChgToBeDed, authBy);
                    if (ftsMsg.equalsIgnoreCase("Balance Exceeds")) {
                        mainDetails = "Insufficient Fund ";
                        Query q1 = em.createNativeQuery("Insert into pendingcharges(Acno,Dt,Amount,Details,InstNo,TY,"
                                + "TranType,RecNo,Trsno,EnterBy,Auth, AuthBy,UpdateDt,UpdateBy,charges,recover,trandesc) "
                                + "Values('" + acno + "','" + dt + "'," + chgToBeDed + ",'" + mainDetails + "',"
                                + "'" + instNo + "',1,2,0," + trsno + ",'" + enterBy + "','Y','" + authBy + "',"
                                + "'" + dt + "','" + enterBy + "',Null,'N',111)");

                        q1.executeUpdate();

                        Query q2 = em.createNativeQuery("update cts_clg_in_entry set status = 4,auth_by='" + authBy + "',"
                                + "auth='Y',substatus='U',userdetails='" + userDetails + "',details='" + otherReasonText + "' where txn_id = '" + txnId + "' and "
                                + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                        q2.executeUpdate();
                        return msg = "PENDING";
                    }
                    //Charge deduction in party.
                    mainDetails = "Chq. Return Charges";
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(acno, 2, 1, chgToBeDed, dt, dt, authBy, orgnCode,
                            destCode, 111, mainDetails, trsno, 0.0f, 0, "CTS", "Y", "System", "A", 3, instNo, instDt, "TDA",
                            Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "N", "", "", "S");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        ut.rollback();
                        return msg = ftsMsg;
                    }

                    mainDetails = "Chq Returned Charges ";
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(glAcnoToBeCr, 2, 0, chgToBeDed, dt, dt, authBy,
                            orgnCode, destCode, 111, mainDetails, trsno, 0.0f, 0, "CTS", "Y", "System", "A", 3, instNo,
                            instDt, "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        ut.rollback();
                        return msg = ftsMsg;
                    }
                    //Service tax gl entry
                    if (code == 1) {
                        //Service tax deduction in party.
                        mainDetails = "GST on Chq. Return Charge. ";
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(acno, 2, 1, serviceCharge, dt, dt, authBy, orgnCode,
                                destCode, 71, mainDetails, trsno, 0.0f, 0, "CTS", "Y", "System", "A", 3, instNo, instDt,
                                "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "N", "", "", "S");
                        if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }

                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = destCode + keyArray[1];
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            mainDetails = description.trim().toUpperCase() + " ON I/W CHQ RTN CHGS.";
                            ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(taxHead, 2, 0, taxAmount, dt, dt, authBy, orgnCode,
                                    destCode, 71, mainDetails, trsno, 0.0f, 0, "CTS", "Y", "System", "A", 3, instNo, instDt,
                                    "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "N", "", "", "");
                            if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                                ut.rollback();
                                return msg = ftsMsg;
                            }
                        }
                    }
                    Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 4,auth_by='" + authBy + "',"
                            + "auth='Y',substatus='U',userdetails='" + userDetails + "',details='" + otherReasonText + "' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                    q1.executeUpdate();
                    return msg = "RETURNED";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String remoteSdMakerClg(String acno, String enterBy, String orgnCode, String destCode, String details,
            String secondAuthBy, String authBy, String instNo, String screenFlag, String payee, String bankname,
            String bankAddress, Integer status, Float seqNo, Integer payBy, Integer reasonForCancel, Integer vchNo,
            Integer batchNo, String txnId, double instAmt, String dt, String instDt, Integer tranType, String subStatus,
            Integer requestType, String circleType, Integer ty, String remarks, String userDetails, int ctsSponsor,
            String otherReasonText, String custSate, String branchState)
            throws ApplicationException {

        String msg = null, mainDetails = null;
        double totalChgToBeDed = 0d;
        double sTax = 0.0, serviceCharge = 0d;
        Map<String, Double> map = new HashMap<String, Double>();
        UserTransaction ut = context.getUserTransaction();
        try {
            if (requestType <= 0 || requestType > 4) {
                return msg = "Invalid RequestType";
            }
            if (!((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals(""))) { //For khatri to stop the checking in case of other bank
                if (acno.length() != 12 || acno.equals("")) {
                    return msg = "Invalid AccountNumber";
                }
            }

            List list = em.createNativeQuery("Select CircleType From parameterinfo_clg where "
                    + "CircleType = '" + circleType + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                String cirType = element.get(0).toString();
                if (!circleType.equalsIgnoreCase(cirType)) {
                    return msg = "Invalid CircleType";
                }
            }
            if (batchNo == null) {
                return msg = "Invalid SessionId";
            }
            if (enterBy.equals("")) {
                return msg = "Invalid User Defined";
            }
            if (authBy.equals("")) {
                return msg = "Invalid Authby Defined";
            }
            if (!((screenFlag.trim().equalsIgnoreCase("T")) || (screenFlag.trim().equalsIgnoreCase("C")))) {
                return msg = "Invalid ScreenFlag";
            }
            if (!((status == 2) || (status == 3) || (status == 4) || (status == 5))) {
                return msg = "Invalid Status";
            }
            if (vchNo <= 0) {
                return msg = "Invalid Voucher Number";
            }
            if (instAmt <= 0) {
                return msg = "Invalid Amount";
            }
            if (payBy != 1) {
                return msg = "Invalid PayBy(1 For Cheque)";
            }
            if (tranType != 1) {
                return msg = "Invalid TranType";
            }
            if (!((ty == 0) || (ty == 1))) {
                return msg = "Invalid Transaction Type";
            }

            List listOrgnAplhaCode = em.createNativeQuery("Select Alphacode from branchmaster where "
                    + "BrnCode = cast('" + orgnCode + "' as unsigned)").getResultList();
            Vector element2 = (Vector) listOrgnAplhaCode.get(0);
            String orgnAlphaCode = element2.get(0).toString();
            if (orgnAlphaCode.equalsIgnoreCase("")) {
                return msg = "Invalid Originating BranchId";
            }

            if (!((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals(""))) {
                List listDestAplhaCode = em.createNativeQuery("Select Alphacode from branchmaster where "
                        + "BrnCode = cast('" + destCode + "' as unsigned)").getResultList();
                Vector element3 = (Vector) listDestAplhaCode.get(0);
                String destAlphaCode = element3.get(0).toString();
                if (destAlphaCode.equalsIgnoreCase("")) {
                    return msg = "Invalid Destination BranchId";
                }
            }

            if (txnId.equals("")) {
                return msg = "Invalid TranId";
            }
            if (instNo.length() > 10) {
                return msg = "Invalid Instrument Number";
            }
            if (dt.equals("")) {
                return msg = "Invalid Date";
            }
            if (instDt.equals("")) {
                return msg = "Invalid Instrument Date";
            }

            instNo = fnLpading(instNo, 10, "0");
            String clgAdjAcno = "", glAcnoToBeCr = "", clgCellAcno = "";
            List listGLAcnoToBeUsed = em.createNativeQuery("select GlInClgRet,GlInClgRetChg,InRetChg,GlInClg from "
                    + "parameterinfo_clg Where CircleType='" + circleType + "'").getResultList();
            if (listGLAcnoToBeUsed == null || listGLAcnoToBeUsed.isEmpty()) {
                return msg = "Please fill data in ParameterInfo_Clg.";
            }

            Vector element1 = (Vector) listGLAcnoToBeUsed.get(0);
            String clgAdjAcnoHead = element1.get(0).toString();
            if (clgAdjAcnoHead.length() < 6) {
                int padLength = 6 - clgAdjAcnoHead.length();
                for (int i = 0; i < padLength; i++) {
                    clgAdjAcnoHead = "0" + clgAdjAcnoHead;
                }
            }
            clgAdjAcno = orgnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgAdjAcnoHead + "01";

            double chgToBeDed = 0;
            if (!((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals(""))) {
                if (!acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    List selectRetChg = ftsPost43CBSRemote.getChequeReturnCharge("CHEQUE-RETURN-CHG", "IW-CHEQUE", instAmt, ftsPost43CBSRemote.getAccountCode(acno));
                    //List selectRetChg = ftsPost43CBSRemote.getCheqRetChgAndHead("IW Cheque Return Charges", ftsPost43CBSRemote.getAccountCode(acno));
                    if (selectRetChg.isEmpty()) {
                        ut.rollback();
                        return msg = "Please fill IW Cheque Return Charges.";
                    }

                    Vector vecRetChg = (Vector) selectRetChg.get(0);
                    chgToBeDed = Double.parseDouble(vecRetChg.get(1).toString());
                    glAcnoToBeCr = orgnCode + vecRetChg.get(0).toString() + "01";
                }
            }
            String clgCellAcnoHead = element1.get(3).toString();
            if (clgCellAcnoHead.length() < 6) {
                int padLength = 6 - clgCellAcnoHead.length();
                for (int i = 0; i < padLength; i++) {
                    clgCellAcnoHead = "0" + clgCellAcnoHead;
                }
            }
            clgCellAcno = orgnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgCellAcnoHead + "01";

            List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname "
                    + "like 'STAXMODULE_ACTIVE'").getResultList();
            Vector element4 = (Vector) listCode.get(0);
            Integer code = Integer.parseInt(element4.get(0).toString());
            if (code == 1) {
                totalChgToBeDed = chgToBeDed;

                if (custSate.equalsIgnoreCase(branchState)) {
                    map = ibRemote.getTaxComponent(chgToBeDed, dt);
                } else {
                    map = ibRemote.getIgstTaxComponent(chgToBeDed, dt);
                }
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    serviceCharge = serviceCharge + Double.parseDouble(entry.getValue().toString());
                }
                totalChgToBeDed = totalChgToBeDed + serviceCharge;
            } else {
                totalChgToBeDed = chgToBeDed;
            }

            List chequeStatusList = em.createNativeQuery("SELECT STATUS FROM cts_clg_in_entry "
                    + "WHERE TXN_ID='" + txnId + "' AND ACNO='" + acno + "'").getResultList();
            Vector element6 = (Vector) chequeStatusList.get(0);
            Integer chequeStatus = Integer.parseInt(element6.get(0).toString());
            if (chequeStatus == 2) {
                return msg = "This Cheque have been already Passed.";
            } else if (chequeStatus == 4) {
                return msg = "This Cheque have been already Returned.";
            } else if (chequeStatus == 5) {
                return msg = "This Cheque have been already Deleted.";
            }

            instDt = instDt.substring(6) + instDt.substring(3, 5) + instDt.substring(0, 2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDate = sdf.format(date);

            if (status == 5) {
                Query insertCheque = em.createNativeQuery("insert into cts_clg_in_entry_history (txn_id, batch_no, acno, inst_no, "
                        + "inst_amt, inst_dt, favor_of, bank_name, bank_add, remarks, reason_for_cancel, enter_by, auth_by, status, "
                        + "seq_no, orgn_branch, dest_branch, dt, tran_time, pay_by, iy, screen_flag, details, em_flag, vch_no, "
                        + "second_auth_by, auth, circle_type, substatus, img_code, custname, oper_mode, userdetails, prbankcode, "
                        + "rbirefno, schedule_no, fh_creation_date, fh_file_id, fh_settlement_date, item_payor_bank_routno, "
                        + "item_seq_no, item_trans_code, item_san, binary_data_file_name, binary_img_file_name, fh_vno, "
                        + "fh_test_file_indicator, item_prment_date, item_cycleno, addenda_bofdroutno, addenda_bofdbusdate, "
                        + "addenda_depositoracct, addenda_ifsc, modified_flag,doc_type) "
                        + "select txn_id, batch_no, acno, inst_no, inst_amt, inst_dt, favor_of, bank_name, bank_add, remarks, "
                        + "reason_for_cancel, enter_by, auth_by, status, seq_no, orgn_branch, dest_branch, dt, tran_time, pay_by, "
                        + "iy, screen_flag, details, em_flag, vch_no, second_auth_by, auth, circle_type, substatus, img_code, "
                        + "custname, oper_mode, userdetails, prbankcode, rbirefno, schedule_no, fh_creation_date, fh_file_id, "
                        + "fh_settlement_date, item_payor_bank_routno, item_seq_no, item_trans_code, item_san, binary_data_file_name, "
                        + "binary_img_file_name, fh_vno, fh_test_file_indicator, item_prment_date, item_cycleno, addenda_bofdroutno, "
                        + "addenda_bofdbusdate, addenda_depositoracct, addenda_ifsc, modified_flag,doc_type from cts_clg_in_entry where "
                        + "txn_id='" + txnId + "'");
                int insno = insertCheque.executeUpdate();
                if (insno <= 0) {
                    ut.rollback();
                    return msg = "Problem in insertion";
                }

                if (ctsSponsor == 3) { //For Ramgariya
                    Query updateCheque = em.createNativeQuery("update cts_clg_in_entry set status = 5,auth_by='" + authBy + "',"
                            + "auth='Y',substatus='U',userdetails='DELETED',acno='' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                    int no = updateCheque.executeUpdate();
                    if (no <= 0) {
                        ut.rollback();
                        return msg = "Problem in updation";
                    }
                } else {
                    Query updateCheque = em.createNativeQuery("update cts_clg_in_entry set status = 5,auth_by='" + authBy + "',"
                            + "auth='Y',substatus='U',userdetails='DELETED' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                    int no = updateCheque.executeUpdate();
                    if (no <= 0) {
                        ut.rollback();
                        return msg = "Problem in updation";
                    }

                    Query deleteCheque = em.createNativeQuery("delete from cts_clg_in_entry where txn_id='" + txnId + "'");
                    int delno = deleteCheque.executeUpdate();
                    if (delno <= 0) {
                        ut.rollback();
                        return msg = "Problem in deletion";
                    }
                }
                return msg = "DELETED";
            }

            String dateValMsg = ftsPost43CBSRemote.ftsDateValidate(dt, orgnCode);
            if (!dateValMsg.equalsIgnoreCase("true")) {
                ut.rollback();
                Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 3 where txn_id = '" + txnId + "' and "
                        + "date_format(curdate(),'%Y%m%d') = '" + curDate + "'");
                q1.executeUpdate();
                return msg = dateValMsg;
            }

            List isoList = em.createNativeQuery("select acno from abb_parameter_info where "
                    + "purpose = 'intersole account'").getResultList();
            if (isoList.isEmpty()) {
                ut.rollback();
                return msg = "There is no entry in abb parameter info for Intersole Account.";
            }
            Vector isoEle = (Vector) isoList.get(0);
            String isoAccount = isoEle.get(0).toString();
            if (isoAccount.equals("") || isoAccount.length() < 10) {
                ut.rollback();
                return msg = "There is no entry in abb parameter info for Intersole Account in proper state.";
            }

            if (status == 2) {
                if (ftsPost43CBSRemote.isAccountNPA(acno)) {
                    ut.rollback();
                    return "This is a NPA Account";
                }
                mainDetails = "Iw-Clg " + payee;
                String ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(acno, 1, 1, instAmt, dt, dt, enterBy, orgnCode, destCode, 0,
                        mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", authBy, "A", payBy, instNo,
                        instDt, "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "S", "", "", "");
                if (ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(destCode + isoAccount, 1, 0, instAmt, dt, dt, enterBy,
                            orgnCode, destCode, 0, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y",
                            authBy, "A", 3, "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A",
                            0.0f, "S", "", "", "");
                    if (ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgCellAcno, 1, 0, instAmt, dt, dt, enterBy, orgnCode,
                                orgnCode, 0, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", authBy, "A",
                                3, "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A",
                                0.0f, "C", "", "", "");
                        if (ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                            ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(orgnCode + isoAccount, 1, 1, instAmt, dt, dt,
                                    enterBy, orgnCode, destCode, 0, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(),
                                    0, "INWRD", "Y", authBy, "A", 3, "", "", "TDA", Float.parseFloat(vchNo.toString()),
                                    "I", "C", screenFlag, "A", 0.0f, "C", "", "", "");
                            if (ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                                Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 2,"
                                        + "auth_by='" + authBy + "',auth='Y',substatus='U',userdetails='PASSED' "
                                        + "where txn_id = '" + txnId + "' and date_format(dt,'%Y%m%d') = '" + curDate + "'");
                                q1.executeUpdate();
                                //Deaf updation
                                ftsPost43CBSRemote.lastTxnDateUpdation(acno);
                                //Deaf updation end here
                                return msg = "PASSED";
                            }
                        }
                    }
                }
                ut.rollback();
                return msg = ftsMsg;
            }

            if (status == 4) {
                if ((ctsSponsor == 2 || ctsSponsor == 3) && acno.trim().equals("") && reasonNotAppForCharge(userDetails).equals("false")) {
                    ut.rollback();
                    return msg = "This reason is not appropriate to return.";
                }

                Float trsno = ftsPost43CBSRemote.getTrsNo();
                if (reasonNotAppForCharge(userDetails).equalsIgnoreCase("true")
                        || acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    mainDetails = "Chq Returned. ";
                    String ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgAdjAcno, 1, 1, instAmt, dt, dt, enterBy, orgnCode,
                            orgnCode, 111, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", authBy, "A", 3,
                            instNo, instDt, "TDA", Float.parseFloat(vchNo.toString()), "", "", screenFlag, "", 0.0f, "N", "", "", "");
                    if (!ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
                        return msg = ftsMsg;
                    } else {
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgCellAcno, 1, 0, instAmt, dt, dt, enterBy, orgnCode,
                                orgnCode, 111, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", authBy,
                                "A", 3, instNo, instDt, "TDA", Float.parseFloat(vchNo.toString()), "", "", screenFlag, "",
                                0.0f, "N", "", "", "");
                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }
                        Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 4,auth_by='" + authBy + "',"
                                + "auth='Y',substatus='U',userdetails='" + userDetails + "',details='" + otherReasonText + "' where txn_id = '" + txnId + "' and "
                                + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                        q1.executeUpdate();
                        return msg = "RETURNED";
                    }
                } else {
                    mainDetails = "Chq Returned. ";
                    String ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgAdjAcno, 1, 1, instAmt, dt, dt, enterBy, orgnCode,
                            orgnCode, 111, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", authBy, "A", 3,
                            instNo, instDt, "TDA", Float.parseFloat(vchNo.toString()), "", "", screenFlag, "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        ut.rollback();
                        return msg = ftsMsg;
                    }

                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgCellAcno, 1, 0, instAmt, dt, dt, enterBy, orgnCode,
                            orgnCode, 111, mainDetails, 0f, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", authBy, "A", 3,
                            instNo, instDt, "TDA", Float.parseFloat(vchNo.toString()), "", "", screenFlag, "", 0.0f, "N", "", "", "");
                    if (!ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
                        return msg = ftsMsg;
                    }

                    ftsMsg = ftsPost43CBSRemote.checkBalance(acno, totalChgToBeDed, authBy);
                    if (ftsMsg.toUpperCase().contains("BALANCE EXCEEDS")) {
                        mainDetails = "Insufficient Fund ";
                        Query q1 = em.createNativeQuery("Insert into pendingcharges(Acno,Dt,Amount,Details,InstNo,TY,TranType,"
                                + "RecNo,Trsno,EnterBy,Auth, AuthBy,UpdateDt,UpdateBy,charges,recover,trandesc) "
                                + "Values('" + acno + "','" + dt + "'," + chgToBeDed + ",'" + mainDetails + "',"
                                + "'" + instNo + "',1,2,0," + trsno + ",'" + enterBy + "','Y','" + authBy + "',"
                                + "'" + dt + "','" + enterBy + "',Null,'N',111)");

                        q1.executeUpdate();

                        Query q2 = em.createNativeQuery("update cts_clg_in_entry set status = 4,auth_by='" + authBy + "',"
                                + "auth='Y',substatus='U',userdetails='" + userDetails + "',details='" + otherReasonText + "' where txn_id = '" + txnId + "' and "
                                + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                        q2.executeUpdate();
                        return msg = "PENDING";
                    } else {
                        //Charges in party a/c
                        mainDetails = "Chq. Return Charges";
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(acno, 2, 1, chgToBeDed, dt, dt, authBy,
                                orgnCode, destCode, 111, mainDetails, trsno, 0.0f, 0, "INWRD", "Y", "System", "A", 3, instNo,
                                instDt, "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "S", "", "", "S");
                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }

                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(destCode + isoAccount, 2, 0, chgToBeDed, dt, dt,
                                authBy, orgnCode, destCode, 111, mainDetails, trsno, ftsPost43CBSRemote.getRecNo(), 0,
                                "INWRD", "Y", "System", "A", 3, "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C",
                                screenFlag, "A", 0.0f, "S", "", "", "");
                        if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }
                        //Charge in Gl Head
                        mainDetails = "Chq Returned. ";
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(glAcnoToBeCr, 2, 0, chgToBeDed, dt, dt, authBy,
                                orgnCode, orgnCode, 111, mainDetails, trsno, ftsPost43CBSRemote.getRecNo(), 0, "INWRD",
                                "Y", "System", "A", 3, instNo, instDt, "TDA", Float.parseFloat(vchNo.toString()), "I", "C",
                                screenFlag, "A", 0.0f, "C", "", "", "");
                        if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }

                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(orgnCode + isoAccount, 2, 1, chgToBeDed, dt, dt,
                                authBy, orgnCode, destCode, 111, mainDetails, trsno, ftsPost43CBSRemote.getRecNo(),
                                0, "INWRD", "Y", "System", "A", 3, "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C",
                                screenFlag, "A", 0.0f, "C", "", "", "");
                        if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }
                    }

                    if (code == 1) {
                        //Service charge in party a/c
                        mainDetails = "GST on Chq. Return Charge";
                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(acno, 2, 1, serviceCharge, dt, dt, authBy,
                                orgnCode, destCode, 71, mainDetails, trsno, 0.0f, 0, "INWRD", "Y", "System", "A", 3, instNo,
                                instDt, "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f, "S", "", "", "S");
                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }

                        ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(destCode + isoAccount, 2, 0, serviceCharge, dt, dt,
                                authBy, orgnCode, destCode, 71, mainDetails, trsno, ftsPost43CBSRemote.getRecNo(), 0,
                                "INWRD", "Y", "System", "A", 3, "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C",
                                screenFlag, "A", 0.0f, "S", "", "", "");
                        if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                            ut.rollback();
                            return msg = ftsMsg;
                        }

                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = orgnCode + keyArray[1];
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            mainDetails = description.trim().toUpperCase() + " ON I/W CHQ RTN CHGS.";
                            ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(taxHead, 2, 0,
                                    taxAmount, dt, dt, authBy, orgnCode, orgnCode, 71,
                                    mainDetails, trsno, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", "System", "A", 3,
                                    instNo, instDt, "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A",
                                    0.0f, "C", "", "", "");
                            if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                                ut.rollback();
                                return msg = ftsMsg;
                            }

                            ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(orgnCode + isoAccount, 2, 1,
                                    taxAmount, dt, dt, authBy, orgnCode, destCode, 71,
                                    mainDetails, trsno, ftsPost43CBSRemote.getRecNo(), 0, "INWRD", "Y", "System", "A", 3,
                                    "", "", "TDA", Float.parseFloat(vchNo.toString()), "I", "C", screenFlag, "A", 0.0f,
                                    "C", "", "", "");
                            if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                                ut.rollback();
                                return msg = ftsMsg;
                            }

                        }
                    }

                    Query q1 = em.createNativeQuery("update cts_clg_in_entry set status = 4,auth_by='" + authBy + "',"
                            + "auth='Y',substatus='U',userdetails='" + userDetails + "',details='" + otherReasonText + "' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + curDate + "'");
                    q1.executeUpdate();
                    return msg = "RETURNED";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String fnLpading(String padVar, Integer padLength, String padChar) throws ApplicationException {
        try {
            while ((padVar.length()) < padLength) {
                padVar = padChar + padVar;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return padVar;
    }

    public String getTotalChequeDetail(String orgnCode) throws ApplicationException {
        String msg = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDate = sdf.format(date);
            List totalChqList = em.createNativeQuery("select count(*) from cts_clg_in_entry where "
                    + "date_format(dt,'%Y%m%d') ='" + curDate + "' and dest_branch ='" + orgnCode + "' and "
                    + "status in(1,2,3,4) and schedule_no=0").getResultList();
            Vector element = (Vector) totalChqList.get(0);
            Integer totalChq = Integer.parseInt(element.get(0).toString());

            if (totalChq == 0) {
                return msg = "false";
            }

            List totalPassList = em.createNativeQuery("select count(*) from cts_clg_in_entry where "
                    + "date_format(dt,'%Y%m%d') ='" + curDate + "' and dest_branch ='" + orgnCode + "' and "
                    + "status in(2) and schedule_no=0").getResultList();
            Vector element1 = (Vector) totalPassList.get(0);
            Integer totalPassChq = Integer.parseInt(element1.get(0).toString());

            List totalReturnList = em.createNativeQuery("select count(*) from cts_clg_in_entry where "
                    + "date_format(dt,'%Y%m%d') ='" + curDate + "' and dest_branch ='" + orgnCode + "' and "
                    + "status in(4) and schedule_no=0").getResultList();
            Vector element2 = (Vector) totalReturnList.get(0);
            Integer totalReturnChq = Integer.parseInt(element2.get(0).toString());

            if (totalChq == (totalPassChq + totalReturnChq)) {
                List compList = em.createNativeQuery("select count(*) from recon_clg_d where "
                        + "date_format(dt,'%Y%m%d') ='" + curDate + "' and dest_brnid ='" + orgnCode + "' and "
                        + "details='IW CLG Completion' and trandesc=65").getResultList();
                Vector compElement = (Vector) compList.get(0);
                Integer compStatus = Integer.parseInt(compElement.get(0).toString());
                if (compStatus <= 0) {
                    msg = "true";
                } else {
                    return msg = "You have completed your Inward";
                }
            } else {
                msg = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String completion(String orgnCode, String enterBy) throws ApplicationException {
        String msg = null, mainDetails = "", ftsMsg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDate = sdf.format(date);

            String clgGlPassToBeCr = "", glGlRetToBeCr = "", circleType = "";
            List circleTypeList = em.createNativeQuery("select distinct(circle_type) from cts_clg_in_entry "
                    + "where dest_branch='" + orgnCode + "'").getResultList();
            if (circleTypeList.size() > 0) {
                for (int t = 0; t < circleTypeList.size(); t++) {
                    Vector element = (Vector) circleTypeList.get(t);
                    circleType = element.get(0).toString();
                }
            } else {
                return msg = "There is problem in completion";
            }
            List listGLAcnoToBeUsed = em.createNativeQuery("select GlInClg,GlInClgRet from parameterinfo_clg "
                    + "Where CircleType='" + circleType + "'").getResultList();
            Vector element = (Vector) listGLAcnoToBeUsed.get(0);
            String clgGlPassToBeCrHead = element.get(0).toString();
            if (clgGlPassToBeCrHead.length() < 6) {
                int padLength = 6 - clgGlPassToBeCrHead.length();
                for (int i = 0; i < padLength; i++) {
                    clgGlPassToBeCrHead = "0" + clgGlPassToBeCrHead;
                }
                clgGlPassToBeCr = orgnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgGlPassToBeCrHead + "01";
            } else {
                clgGlPassToBeCr = orgnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgGlPassToBeCrHead + "01";
            }

            String glGlRetToBeCrHead = element.get(1).toString();
            if (glGlRetToBeCrHead.length() < 6) {
                int padLength = 6 - glGlRetToBeCrHead.length();
                for (int i = 0; i < padLength; i++) {
                    glGlRetToBeCrHead = "0" + glGlRetToBeCrHead;
                }
                glGlRetToBeCr = orgnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glGlRetToBeCrHead + "01";
            } else {
                glGlRetToBeCr = orgnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glGlRetToBeCrHead + "01";
            }

            List passAmtList = em.createNativeQuery("select ifnull(sum(inst_amt),0) from cts_clg_in_entry "
                    + "where dt='" + curDate + "' and dest_branch='" + orgnCode + "' and status=2 and "
                    + "schedule_no=0").getResultList();
            Vector element1 = (Vector) passAmtList.get(0);
            double totalPassAmtToBeCr = Double.parseDouble(element1.get(0).toString());

            List retAmtList = em.createNativeQuery("select ifnull(sum(inst_amt),0) from cts_clg_in_entry "
                    + "where dt='" + curDate + "' and dest_branch='" + orgnCode + "' and status=4 and "
                    + "schedule_no=0").getResultList();
            Vector element2 = (Vector) retAmtList.get(0);
            double totalRetAmtToBeCr = Double.parseDouble(element2.get(0).toString());

            ut.begin();

            String bankCode = getBankCode();
            if (bankCode.equals("")) {
                throw new ApplicationException("Please define bank code in MB SMS SENDER BANK DETAIL.");
            }

            mainDetails = "IW CLG Completion";
            if (bankCode.equalsIgnoreCase("ccbl")) {
                if (totalPassAmtToBeCr > 0) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgGlPassToBeCr, 1, 0, totalPassAmtToBeCr, curDate,
                            curDate, enterBy, orgnCode, orgnCode, 65, mainDetails, 0.0f, 0.0f, 0, "Inw", "Y", "SYSTEM",
                            "A", 3, "", "", "TDA", 0.0f, "", "", "C", "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        throw new ApplicationException(ftsMsg);
                    }
                }

                if (totalRetAmtToBeCr > 0) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(glGlRetToBeCr, 1, 0, totalRetAmtToBeCr, curDate,
                            curDate, enterBy, orgnCode, orgnCode, 65, mainDetails, 0.0f, 0.0f, 0, "Inw", "Y", "SYSTEM",
                            "A", 3, "", "", "TDA", 0.0f, "", "", "C", "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        throw new ApplicationException(ftsMsg);
                    }
                }
            } else {
                double totalpassAndReturnAmount = totalPassAmtToBeCr + totalRetAmtToBeCr;
                if (totalpassAndReturnAmount > 0) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgGlPassToBeCr, 1, 0, totalpassAndReturnAmount, curDate,
                            curDate, enterBy, orgnCode, orgnCode, 65, mainDetails, 0.0f, 0.0f, 0, "Inw", "Y", "SYSTEM",
                            "A", 3, "", "", "TDA", 0.0f, "", "", "C", "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        throw new ApplicationException(ftsMsg);
                    }
                }
            }

            msg = "true";
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }

    public List getImageDetails(String imageCode, String orgCode) throws ApplicationException {
        List imgDetails = null;
        try {
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDt = sdf.format(dt);
            imgDetails = em.createNativeQuery("select acno,inst_no,inst_amt,date_format(inst_dt,'%Y%m%d'),coalesce(favor_of,'') as favor_of,bank_name,enter_by,auth_by,status,coalesce(seq_no,0.0) as seq_no,orgn_branch,dest_branch,date_format(dt,'%Y%m%d'),details,auth,substatus,img_code,custname,oper_mode,userdetails,prbankcode,rbirefno from cts_clg_in_entry where img_code='" + imageCode + "' and dt='" + curDt + "' and dest_branch='" + orgCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return imgDetails;
    }

    public String makeImageBackup(String imageType, String imageCode, String encyImgFile, String acno,
            String instNo, Float instAmt, String instDt, String favourOf, String bankName, String enterBy,
            String authBy, int status, Float seqNo, String orgBranch, String destBranch, String clgDt,
            String details, String auth, String substatus, String custname, String opermode, String userdetails,
            String prbankcode, String rbirefno, Integer scheduleNo) throws ApplicationException {
        String msg = "true";
        UserTransaction ut = context.getUserTransaction();
        try {
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDt = sdf.format(dt);
            ut.begin();
            List imgDetail = em.createNativeQuery("select acno from CBS_BACKUP.DBO.cts_clg_image_bkp where dt='" + curDt + "' "
                    + "and dest_branch='" + destBranch + "' and schedule_no = " + scheduleNo + " and acno='" + acno + "' and "
                    + "inst_no='" + instNo + "'").getResultList();
            if (imgDetail.size() <= 0) {
                if (imageType.equalsIgnoreCase("GF")) {
                    Query insertGFQuery = em.createNativeQuery("insert into CBS_BACKUP.DBO.cts_clg_image_bkp(acno,inst_no,"
                            + "inst_amt,inst_dt,favor_of,bank_name,enter_by,auth_by,status,seq_no,orgn_branch,dest_branch,dt,"
                            + "details,auth,substatus,img_code,custname,oper_mode,userdetails,prbankcode,rbirefno,gf_image,"
                            + "jpg_image,png_image,schedule_no) values('" + acno + "','" + instNo + "'," + instAmt + ",'" + instDt + "',"
                            + "'" + favourOf + "','" + bankName + "','" + enterBy + "','" + authBy + "'," + status + ","
                            + "" + seqNo + ",'" + orgBranch + "','" + destBranch + "','" + clgDt + "','" + details + "',"
                            + "'" + auth + "','" + substatus + "','" + imageCode + "','" + custname + "','" + opermode + "',"
                            + "'" + userdetails + "','" + prbankcode + "','" + rbirefno + "','" + encyImgFile + "','','',"
                            + "" + scheduleNo + ")");
                    int insertGFNo = insertGFQuery.executeUpdate();
                    if (insertGFNo <= 0) {
                        ut.rollback();
                        return msg = "Insertion problem in image backup for GF image";
                    }
                } else if (imageType.equalsIgnoreCase("jpg")) {
                    Query insertJPGQuery = em.createNativeQuery("insert into CBS_BACKUP.DBO.cts_clg_image_bkp(acno,inst_no,"
                            + "inst_amt,inst_dt,favor_of,bank_name,enter_by,auth_by,status,seq_no,orgn_branch,dest_branch,dt,"
                            + "details,auth,substatus,img_code,custname,oper_mode,userdetails,prbankcode,rbirefno,gf_image,"
                            + "jpg_image,png_image,schedule_no) values('" + acno + "','" + instNo + "'," + instAmt + ",'" + instDt + "',"
                            + "'" + favourOf + "','" + bankName + "','" + enterBy + "','" + authBy + "'," + status + ","
                            + "" + seqNo + ",'" + orgBranch + "','" + destBranch + "','" + clgDt + "','" + details + "',"
                            + "'" + auth + "','" + substatus + "','" + imageCode + "','" + custname + "','" + opermode + "',"
                            + "'" + userdetails + "','" + prbankcode + "','" + rbirefno + "','','" + encyImgFile + "','',"
                            + "" + scheduleNo + ")");
                    int insertJPGNo = insertJPGQuery.executeUpdate();
                    if (insertJPGNo <= 0) {
                        ut.rollback();
                        return msg = "Insertion problem in image backup for JPG image";
                    }
                } else if (imageType.equalsIgnoreCase("png")) {
                    Query insertPNGQuery = em.createNativeQuery("insert into CBS_BACKUP.DBO.cts_clg_image_bkp(acno,inst_no,"
                            + "inst_amt,inst_dt,favor_of,bank_name,enter_by,auth_by,status,seq_no,orgn_branch,dest_branch,"
                            + "dt,details,auth,substatus,img_code,custname,oper_mode,userdetails,prbankcode,rbirefno,gf_image,"
                            + "jpg_image,png_image,schedule_no) values('" + acno + "','" + instNo + "'," + instAmt + ",'" + instDt + "',"
                            + "'" + favourOf + "','" + bankName + "','" + enterBy + "','" + authBy + "'," + status + ","
                            + "" + seqNo + ",'" + orgBranch + "','" + destBranch + "','" + clgDt + "','" + details + "',"
                            + "'" + auth + "','" + substatus + "','" + imageCode + "','" + custname + "','" + opermode + "',"
                            + "'" + userdetails + "','" + prbankcode + "','" + rbirefno + "','','','" + encyImgFile + "',"
                            + "" + scheduleNo + ")");
                    int insertPNGNo = insertPNGQuery.executeUpdate();
                    if (insertPNGNo <= 0) {
                        ut.rollback();
                        return msg = "Insertion problem in image backup for PNG image";
                    }
                }
            } else {
                if (imageType.equalsIgnoreCase("GF")) {
                    Query updateGFQuery = em.createNativeQuery("update CBS_BACKUP.DBO.cts_clg_image_bkp set "
                            + "gf_image='" + encyImgFile + "' where dt='" + curDt + "' and dest_branch='" + destBranch + "' and "
                            + "acno='" + acno + "' and inst_no='" + instNo + "' and schedule_no=" + scheduleNo + "");
                    int updateGFNo = updateGFQuery.executeUpdate();
                    if (updateGFNo <= 0) {
                        ut.rollback();
                        return msg = "Updation problem in image backup for GF image";
                    }
                } else if (imageType.equalsIgnoreCase("jpg")) {
                    Query updateJPGQuery = em.createNativeQuery("update CBS_BACKUP.DBO.cts_clg_image_bkp set "
                            + "jpg_image='" + encyImgFile + "' where dt='" + curDt + "' and dest_branch='" + destBranch + "' "
                            + "and acno='" + acno + "' and inst_no='" + instNo + "' and schedule_no=" + scheduleNo + "");
                    int updateJPGNo = updateJPGQuery.executeUpdate();
                    if (updateJPGNo <= 0) {
                        ut.rollback();
                        return msg = "Updation problem in image backup for JPG image";
                    }
                } else if (imageType.equalsIgnoreCase("png")) {
                    Query updatePNGQuery = em.createNativeQuery("update CBS_BACKUP.DBO.cts_clg_image_bkp set "
                            + "png_image='" + encyImgFile + "' where dt='" + curDt + "' and dest_branch='" + destBranch + "' "
                            + "and acno='" + acno + "' and inst_no='" + instNo + "' and schedule_no=" + scheduleNo + "");
                    int updatePNGNo = updatePNGQuery.executeUpdate();
                    if (updatePNGNo <= 0) {
                        ut.rollback();
                        return msg = "Updation problem in image backup for PNG image";
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    @Override
    public String uploadDataInCell(String txtFilePath, String pxfFilePath,
            String extractFolderName, String zipFileName, Integer scheduleNo, String userName) throws ApplicationException {
        String msg = null;
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            String cbsBrCode = extractFolderName.substring(extractFolderName.indexOf("_") + 1);
            // Data insertion of .Txt file
            String dt = ymd.format(date);
            ut.begin();
            if (ftsPost43CBSRemote.getCodeForReportName("CTS-SPONSOR") == 1) {
                try {
                    fstream = new FileInputStream(txtFilePath);
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        String result = strLine;
                        String values[] = result.split("\\|");
                        String pbMicr = values[0];  // PBANKCODE           1
                        String chequeNo = values[1]; // CHQNO              2
                        String branchMicr = values[2]; // DBANKCODE        3
                        String sanNo = values[3];  // BASEACNO             4
                        String txnCode = values[4]; // TC                  5
                        String amt = values[5];  // AMOUNT                 6
                        String acName = values[6]; // DETAILS              8
                        String busDt = values[7];  // CLGDT                9
                        String dd = busDt.substring(0, 2);
                        String mm = busDt.substring(2, 4);
                        String yyyy = busDt.substring(4, 8);
                        String uniqueNo = values[8]; // RBIREFNO          10
                        Query insertTxtCell = em.createNativeQuery("insert into cts_upload_txt_cell(dt, pbankcode, dbankcode, "
                                + "clgdt,amount, chqno, rbirefno, tc,other, baseacno,cts_branchcode,details,enterby,"
                                + "branchcode,schedule_no,original_zip_name) "
                                + "values('" + dt + "','" + pbMicr + "',"
                                + "'" + branchMicr + "','" + yyyy + mm + dd + "',"
                                + "" + Double.parseDouble(amt) + ","
                                + "'" + chequeNo + "','" + uniqueNo + "',"
                                + "'" + txnCode + "','',"
                                + "'" + sanNo + "',0,'" + acName + "','" + userName + "',"
                                + "" + Integer.parseInt(cbsBrCode) + "," + scheduleNo + ",'" + zipFileName + "')");
                        int no = insertTxtCell.executeUpdate();
                        if (no <= 0) {
                            throw new ApplicationException("Insertion problem in TXT-CELL");
                        }
                    }
                } catch (Exception e) {
                    try {
                        ut.rollback();
                        throw new ApplicationException(e.getMessage());
                    } catch (Exception ex) {
                        throw new ApplicationException(ex.getMessage());
                    }
                } finally {
                    try {
                        fstream.close();
                        in.close();
                        br.close();
                    } catch (Exception ex) {
                        throw new ApplicationException("Current date Txt File not found" + ex.getMessage());
                    }
                }
            } else { //CCBL CASE
                try {
                    fstream = new FileInputStream(txtFilePath);
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        if (strLine.indexOf(" ") < 0) {
                            String clgDt = (strLine.substring(22, 26) + strLine.substring(20, 22) + strLine.substring(18, 20));
                            Query insertTxtCell = em.createNativeQuery("insert into cts_upload_txt_cell(dt, pbankcode, dbankcode, "
                                    + "clgdt,amount, chqno, rbirefno, tc,other, baseacno,cts_branchcode,details,enterby,"
                                    + "branchcode,schedule_no,original_zip_name) "
                                    + "values('" + dt + "','" + strLine.substring(0, 9) + "',"
                                    + "'" + strLine.substring(9, 18) + "','" + clgDt + "',"
                                    + "" + Double.parseDouble(strLine.substring(26, 39)) / 100 + ","
                                    + "'" + strLine.substring(39, 45) + "','" + strLine.substring(45, 55) + "',"
                                    + "'" + strLine.substring(55, 57) + "','" + strLine.substring(57, 61) + "',"
                                    + "'" + strLine.substring(62) + "',0,'','" + userName + "',"
                                    + "" + Integer.parseInt(cbsBrCode) + "," + scheduleNo + ",'" + zipFileName + "')");
                            int no = insertTxtCell.executeUpdate();
                            if (no <= 0) {
                                throw new ApplicationException("Insertion problem in TXT-CELL");
                            }
                        }
                    }
                } catch (Exception e) {
                    try {
                        ut.rollback();
                        throw new ApplicationException(e.getMessage());
                    } catch (Exception ex) {
                        throw new ApplicationException(ex.getMessage());
                    }
                } finally {
                    try {
                        fstream.close();
                        in.close();
                        br.close();
                    } catch (Exception ex) {
                        throw new ApplicationException("Current date Txt File not found" + ex.getMessage());
                    }
                }
                // Data insertion of .Pxf file
                try {
                    fstream = new FileInputStream(pxfFilePath);
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        String[] pxfArr = strLine.split("\\|");
                        String itemSeqNo = pxfArr[0].trim();
                        Double amount = Double.parseDouble(pxfArr[2]) / 100;
                        String chqNo = pxfArr[4].trim();
                        String prDt = pxfArr[7].trim();
                        String turncatingRtNo = pxfArr[1].trim();
                        String remarks = pxfArr[19].trim();
                        String session = pxfArr[18].trim().length() < 2 ? "0" + pxfArr[18].trim() : pxfArr[18].trim();

                        String insertQuery = "insert into cts_upload_pxf_cell(dt,itemseqno,amt,serialno,prdate,truncatingrtno,"
                                + "remarks,enterby,details,branchcode,schedule_no,original_zip_name,session) values('" + dt + "',"
                                + "'" + itemSeqNo + "'," + amount + ",'" + chqNo + "','" + ymd.format(ymdOne.parse(prDt)) + "',"
                                + "'" + turncatingRtNo + "','" + remarks + "','" + userName + "','',"
                                + "" + Integer.parseInt(cbsBrCode) + "," + scheduleNo + ",'" + zipFileName + "','" + session + "')";

                        int result = em.createNativeQuery(insertQuery).executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In PXF Insertion.");
                        }
                    }
                } catch (Exception e) {
                    try {
                        ut.rollback();
                        throw new ApplicationException(e.getMessage());
                    } catch (Exception ex) {
                        throw new ApplicationException(ex.getMessage());
                    }
                } finally {
                    try {
                        fstream.close();
                        in.close();
                        br.close();
                    } catch (Exception ex) {
                        throw new ApplicationException("Current date Pxt File not found");
                    }
                }
            }
            msg = "true";
            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public List getUnAuthorizedInstrument(String todayDate, String branchCode) throws ApplicationException {
        try {
            if (branchCode.equalsIgnoreCase("90")) {
                return em.createNativeQuery("select vch_no,acno,inst_no,inst_dt,inst_amt,favor_of,bank_name,bank_add,prbankcode,custname "
                        + "from cts_clg_in_entry where dt='" + todayDate + "' and orgn_branch='"
                        + branchCode + "' and status in(1,3) order by vch_no").getResultList();
            } else {
                return em.createNativeQuery("select vch_no,acno,inst_no,inst_dt,inst_amt,favor_of,bank_name,bank_add,prbankcode,custname "
                        + "from cts_clg_in_entry where dt='" + todayDate + "' and dest_branch='"
                        + branchCode + "' and status in(1,3) order by vch_no").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveManualInward(String TXN_ID, String BATCH_NO, String ACNO, String INST_NO, String INST_AMT, String INST_DT, String FAVOR_OF,
            String BANK_NAME, String BANK_ADD, String ENTER_BY, String SEQ_NO, String ORGN_BRANCH, String DEST_BRANCH, String DT,
            String imgcode, String custname,
            String oper_mode, String prbankcode, String rbirefno, String userdetails) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        String msg = new String();
        String INST_DT1, REMARKS, AUTH_BY, PAY_BY, SCREEN_FLAG, EM_FLAG, SECOND_AUTH_BY, auth, CIRCLE_TYPE,
                SUBSTATUS, STATUS, DETAILS;

        int VCH_NO = 0, REASON_FOR_CANCEL = 0, IY = 0;
        try {
            ut.begin();
            if (TXN_ID == null) {
                ut.rollback();
                return msg = "TXN_ID PASSED NULL OR BLANK";

            }
            if (BATCH_NO == null) {
                ut.rollback();
                return msg = "BATCH_NO PASSED NULL OR BLANK";
            }
            if (ACNO == null) {
                ut.rollback();
                return msg = "ACNO PASSED NULL OR BLANK";
            }
            if (INST_NO == null) {
                ut.rollback();
                return msg = "INST_NO PASSED NULL OR BLANK";
            }
            if (INST_AMT == null) {
                ut.rollback();
                return msg = "INST_AMT PASSED NULL OR BLANK";
            }
            if (INST_DT == null) {
                ut.rollback();
                return msg = "INST_DT PASSED NULL OR BLANK";
            }
            if (BANK_NAME == null) {
                ut.rollback();
                return msg = "BANK_NAME PASSED NULL OR BLANK";
            }
            if (BANK_ADD == null) {
                ut.rollback();
                return msg = "BANK_ADD PASSED NULL OR BLANK";
            }
            if (ORGN_BRANCH == null) {
                ut.rollback();
                return msg = "ORGN_BRANCH PASSED NULL OR BLANK";
            }
            if (DEST_BRANCH == null) {
                ut.rollback();
                return msg = "DEST_BRANCH PASSED NULL OR BLANK";
            }
            if (userdetails == null) {
                userdetails = "";
            }
            if (FAVOR_OF == null) {
                FAVOR_OF = userdetails;
            } else {
                FAVOR_OF = userdetails + " " + FAVOR_OF;
            }

            REMARKS = "";
            AUTH_BY = "";
//            if (!ACNO.substring(2, 4).equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
//                SEQ_NO = "";
//            }
            PAY_BY = "1";
            SCREEN_FLAG = "C";
            EM_FLAG = "B";
            SECOND_AUTH_BY = "CTS_USER";
            auth = "";
            CIRCLE_TYPE = "A";
            SUBSTATUS = "U";

            if (getClngDetails(ACNO).contains("[")) {
                STATUS = "1";
            } else {
                STATUS = "3";
            }
            if (getClngDetails(ACNO).contains("[")) {
                DETAILS = "CLEARED";
            } else {
                DETAILS = getClngDetails(ACNO);
            }
            if (ORGN_BRANCH.length() == 1) {
                ORGN_BRANCH = "0" + ORGN_BRANCH;
            }
            if (DEST_BRANCH.length() == 1) {
                DEST_BRANCH = "0" + DEST_BRANCH;
            }

            List list2 = em.createNativeQuery("SELECT ifnull(MAX(VCH_NO),0)+1 FROM cts_clg_in_entry "
                    + "WHERE DEST_BRANCH='" + DEST_BRANCH + "' AND DT='" + DT + "'").getResultList();
            Vector v2 = (Vector) list2.get(0);
            VCH_NO = Integer.parseInt(v2.get(0).toString());
            INST_DT1 = INST_DT;
            Query insert = em.createNativeQuery("insert into cts_clg_in_entry(TXN_ID,BATCH_NO,ACNO,INST_NO,INST_AMT,INST_DT,FAVOR_OF,BANK_NAME,"
                    + "BANK_ADD,REMARKS,REASON_FOR_CANCEL,ENTER_BY,AUTH_BY,STATUS,SEQ_NO,ORGN_BRANCH,DEST_BRANCH,DT,TRAN_TIME,PAY_BY,IY,"
                    + "SCREEN_FLAG,DETAILS,EM_FLAG,VCH_NO,SECOND_AUTH_BY,AUTH,CIRCLE_TYPE,SUBSTATUS,IMG_CODE,CUSTNAME,OPER_MODE,USERDETAILS,"
                    + "PRBANKCODE,rbirefno,SCHEDULE_NO,doc_type) VALUES( '" + TXN_ID + "',  "
                    + "" + new BigDecimal(BATCH_NO).longValue() + ",  '" + ACNO + "',  '" + INST_NO + "',  "
                    + "'" + INST_AMT + "',  '" + INST_DT1 + "',  '" + FAVOR_OF + "','" + BANK_NAME + "',  "
                    + "'" + BANK_ADD + "',  '" + REMARKS + "',  '" + REASON_FOR_CANCEL + "',  '" + ENTER_BY + "',  "
                    + "'" + AUTH_BY + "',  '" + STATUS + "'," + SEQ_NO + ",  '" + ORGN_BRANCH + "',  '" + DEST_BRANCH + "',  "
                    + "'" + DT + "',CURRENT_TIMESTAMP,'" + PAY_BY + "',  '" + IY + "',  '" + SCREEN_FLAG + "',  "
                    + "'" + DETAILS + "','" + EM_FLAG + "',  '" + VCH_NO + "','" + SECOND_AUTH_BY + "',  "
                    + "'" + auth + "',  '" + CIRCLE_TYPE + "',  '" + SUBSTATUS + "',  '" + imgcode + "',  "
                    + "'" + custname + "','" + oper_mode + "',  'ENTERED',  '" + prbankcode + "',  "
                    + "'" + rbirefno + "',0,'')");
            int insertResult = insert.executeUpdate();
            if (insertResult <= 0) {
                ut.rollback();
                return msg = "Problem in data insertion.";
            }
            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg = "TRUE" + VCH_NO;
    }

    public String getModuleActiveCode(String moduleName) throws ApplicationException {
        String code = "";
        try {
            List codeList = em.createNativeQuery("select code from parameterinfo_report where reportname='CTS'").getResultList();
            if (!codeList.isEmpty()) {
                Vector element = (Vector) codeList.get(0);
                code = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return code;
    }

    public String getBankCode() throws ApplicationException {
        String bankCode = "";
        try {
            List bankList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            if (!bankList.isEmpty()) {
                Vector element = (Vector) bankList.get(0);
                bankCode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return bankCode;
    }

    public String reasonNotAppForCharge(String description) throws ApplicationException {
        try {
//            List reasonList = em.createNativeQuery("select description from codebook where groupcode=13 and flag='N' and description = '" + description + "'").getResultList();
            List reasonList = em.createNativeQuery("select description from codebook where groupcode=13 and flag='N' and code = " + description + "").getResultList();
            if (!reasonList.isEmpty()) {
                return "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "false";
    }

    public String valBillHead(String acNo, String instNo, String instAmt, String instDate, String aplhaCode) throws ApplicationException {
        try {
            List instNatList = em.createNativeQuery("select instNature from billtypemaster where glhead = '" + acNo.substring(2, 10) + "'").getResultList();
            if (!instNatList.isEmpty()) {
                Vector element = (Vector) instNatList.get(0);
                String insNat = element.get(0).toString();
                if (insNat.equalsIgnoreCase("PO")) {
                    List list1 = em.createNativeQuery("SELECT SEQNO FROM bill_po WHERE INSTNO='" + instNo + "' AND AMOUNT='" + instAmt + "' AND DATE_FORMAT(VALIDATIONDT,'%Y%m%d')='" + instDate + "' "
                            + " AND PAYABLEAT='" + aplhaCode + "' AND ACNO = '" + acNo + "' AND STATUS ='ISSUED'").getResultList();
                    if (!list1.isEmpty()) {
                        return "true";
                    }
                } else if (insNat.equalsIgnoreCase("DD")) {
                    List list1 = em.createNativeQuery("SELECT SEQNO FROM bill_dd WHERE INSTNO='" + instNo + "' AND AMOUNT='" + instAmt + "' AND DATE_FORMAT(ORIGINDT,'%Y%m%d')='" + instDate + "' "
                            + " AND PAYABLEAT='" + aplhaCode + "' AND ACNO = '" + acNo + "' AND STATUS ='ISSUED'").getResultList();
                    if (!list1.isEmpty()) {
                        return "true";
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "false";
    }

    @Override
    public List getGridDetailsCts(Integer branchCode, Integer scheduleNo, int ctsSponsor,
            String emFlag, int status) throws ApplicationException {
        List tableDetails = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDt = sdf.format(date);
        String branch = "", addQuery = "", fetchStatus = "1,3"; //For Entered,Hold both
        if (ctsSponsor == 1) { //AMMCO
            branch = "and cast(a.orgn_branch as unsigned)";
        } else if (ctsSponsor == 2 || ctsSponsor == 3) { //Khatrri
            String brAlphaCode = commonReportRemote.getAlphacodeByBrncode(String.valueOf(branchCode));
            if (brAlphaCode.equalsIgnoreCase("HO")) {
                branch = "and cast(a.orgn_branch as unsigned)";
            } else {
                branch = "and cast(a.dest_branch as unsigned)";
            }
            fetchStatus = (status == 9) ? fetchStatus : String.valueOf(status);
        } else { //CCBL
            branch = "and cast(a.dest_branch as unsigned)";
        }
        if (ctsSponsor == 2 || ctsSponsor == 3) {
            addQuery = " and em_flag='" + emFlag + "' ";
        }
        try {
            tableDetails = em.createNativeQuery("select a.batch_no,a.vch_no,a.acno,a.inst_amt,a.inst_no, "
                    + " date_format(a.inst_dt,'%d/%m/%Y'),a.prbankcode,a.status,a.oper_mode,upper(a.enter_by), "
                    + " upper(a.details),upper(a.favor_of),upper(a.bank_name), upper(a.bank_add),a.txn_id,a.orgn_branch, "
                    + " a.dest_branch,a.seq_no,upper(a.auth_by),a.pay_by,1,upper(a.screen_flag),upper(a.second_auth_by),"
                    + " 'a',a.reason_for_cancel,a.tran_time,date_format(a.dt,'%Y%m%d'),1,a.substatus,1,a.img_code, "
                    + " ifnull(cm.mailStateCode,'') as stateCode, ifnull(br.State,'') as brState,ifnull(a.doc_type,'') as docType from "
                    + " cts_clg_in_entry a left join customerid ci on a.acno = ci.acno "
                    + " left join cbs_customer_master_detail cm on ci.CustId = cast(cm.customerid as unsigned) "
                    + " left join  branchmaster br on  br.brncode=" + branchCode
                    + " where  date_format(a.dt,'%Y%m%d') = '" + currentDt + "' and "
                    + " a.status in (" + fetchStatus + ") " + branch + " =" + branchCode + "  and substatus<>'L' AND "
                    + " a.schedule_no = " + scheduleNo + "" + addQuery + " order by a.vch_no").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableDetails;
    }

    public Integer getScheduleNo(String curDt, Integer branchCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(max(schedule_no),0)+1 from cts_upload_txt_cell "
                    + "where dt='" + curDt + "' and branchcode=" + branchCode + "").getResultList();
            Vector element = (Vector) list.get(0);
            return Integer.parseInt(element.get(0).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public boolean isZipFileAlreadyUploaded(String zipFileName, Integer branchCode, String curDt) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select * from cts_upload_txt_cell where dt='" + curDt + "' and "
                    + "branchcode=" + branchCode + " and original_zip_name = '" + zipFileName + "'").getResultList();
            if (list == null || list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getScheduleNoList(String alphacode, String date) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct(schedule_no) from cts_upload_txt_cell where "
                    + "branchcode in(select brncode from branchmaster where alphacode='" + alphacode + "') and "
                    + "dt='" + date + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("There is no schedule no for this branch.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getScheduleNoList1(String alphacode, String date) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct(schedule_no) from cts_clg_in_entry where"
                    + " ORGN_BRANCH In(select brncode from branchmaster where alphacode='" + alphacode + "')and dt='" + date + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("There is no schedule no for this branch.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getTransactionScheduleNos(String date, int brnCode, String clearingType, int ctsSponsor) throws ApplicationException {
        try {
            String branch = "";
            if (ctsSponsor == 1) { //AMMCO
                branch = "orgn_branch";
            } else if (ctsSponsor == 2 || ctsSponsor == 3) { //Khatrri,Ramgariya
                String brAlphaCode = commonReportRemote.getAlphacodeByBrncode(String.valueOf(brnCode));
                if (brAlphaCode.equalsIgnoreCase("HO")) {
                    branch = "orgn_branch";
                } else {
                    branch = "dest_branch";
                }
            } else { //CCBL
                branch = "dest_branch";
            }

            List list = null;
            if (ctsSponsor == 2 || ctsSponsor == 3) {
                list = em.createNativeQuery("select distinct(schedule_no) from cts_clg_in_entry where dt='" + date + "' and "
                        + "cast(" + branch + " as unsigned) = " + brnCode + " and schedule_no <> 0 and em_flag='" + clearingType + "'").getResultList();
            } else {
                list = em.createNativeQuery("select distinct(schedule_no) from cts_clg_in_entry where dt='" + date + "' and "
                        + "cast(" + branch + " as unsigned) = " + brnCode + " and schedule_no <> 0").getResultList();
            }
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("There is no schedule no for this branch.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getTotalChequeDetailCts(int brnCode, int scheduleNo) throws ApplicationException {
        String msg = null;
        try {
            String curDate = ymd.format(date);
            List totalChqList = em.createNativeQuery("select count(*) from cts_clg_in_entry where "
                    + "date_format(dt,'%Y%m%d')='" + curDate + "' and cast(dest_branch as unsigned) ='" + brnCode + "' and "
                    + "status in(1,2,3,4) and schedule_no=" + scheduleNo + "").getResultList();
            Vector element = (Vector) totalChqList.get(0);
            Integer totalChq = Integer.parseInt(element.get(0).toString());

            if (totalChq == 0) {
                return msg = "false";
            }

            List totalPassList = em.createNativeQuery("select count(*) from cts_clg_in_entry where "
                    + "date_format(dt,'%Y%m%d')='" + curDate + "' and cast(dest_branch as unsigned) ='" + brnCode + "' and "
                    + "status in(2) and schedule_no=" + scheduleNo + "").getResultList();
            Vector element1 = (Vector) totalPassList.get(0);
            Integer totalPassChq = Integer.parseInt(element1.get(0).toString());

            List totalReturnList = em.createNativeQuery("select count(*) from cts_clg_in_entry where "
                    + "date_format(dt,'%Y%m%d')='" + curDate + "' and cast(dest_branch as unsigned) ='" + brnCode + "' and "
                    + "status in(4) and schedule_no=" + scheduleNo + "").getResultList();
            Vector element2 = (Vector) totalReturnList.get(0);
            Integer totalReturnChq = Integer.parseInt(element2.get(0).toString());

            if (totalChq == (totalPassChq + totalReturnChq)) {
                List compList = em.createNativeQuery("select count(*) from recon_clg_d where "
                        + "date_format(dt,'%Y%m%d')='" + curDate + "' and cast(dest_brnid as unsigned) =" + brnCode + " and "
                        + "details='CTS Completion' and trandesc=65 and tran_id=" + scheduleNo + "").getResultList();
                Vector compElement = (Vector) compList.get(0);
                Integer compStatus = Integer.parseInt(compElement.get(0).toString());
                if (compStatus <= 0) {
                    msg = "true";
                } else {
                    return msg = "You have completed your CTS";
                }
            } else {
                msg = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String completionCts(Integer brnCode, Integer scheduleNo, String userName) throws ApplicationException {
        String msg = null, mainDetails = "", ftsMsg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            String curDate = ymd.format(date);
            String clgGlPassToBeCr = "", glGlRetToBeCr = "";

            String branchCode = brnCode.toString().length() == 1 ? "0" + brnCode.toString() : brnCode.toString();

            List listGLAcnoToBeUsed = em.createNativeQuery("select glinclg,glinclgret from parameterinfo_clg "
                    + "where circletype='A'").getResultList();
            if (listGLAcnoToBeUsed == null || listGLAcnoToBeUsed.isEmpty()) {
                return "Please define circle type A in ParameterInfo_Clg.";
            }
            Vector element = (Vector) listGLAcnoToBeUsed.get(0);
            String clgGlPassToBeCrHead = element.get(0).toString();
            if (clgGlPassToBeCrHead.length() < 6) {
                int padLength = 6 - clgGlPassToBeCrHead.length();
                for (int i = 0; i < padLength; i++) {
                    clgGlPassToBeCrHead = "0" + clgGlPassToBeCrHead;
                }
                clgGlPassToBeCr = branchCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgGlPassToBeCrHead + "01";
            } else {
                clgGlPassToBeCr = branchCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgGlPassToBeCrHead + "01";
            }

            String glGlRetToBeCrHead = element.get(1).toString();
            if (glGlRetToBeCrHead.length() < 6) {
                int padLength = 6 - glGlRetToBeCrHead.length();
                for (int i = 0; i < padLength; i++) {
                    glGlRetToBeCrHead = "0" + glGlRetToBeCrHead;
                }
                glGlRetToBeCr = branchCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glGlRetToBeCrHead + "01";
            } else {
                glGlRetToBeCr = branchCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glGlRetToBeCrHead + "01";
            }

            List passAmtList = em.createNativeQuery("select ifnull(sum(inst_amt),0) from cts_clg_in_entry "
                    + "where dt='" + curDate + "' and dest_branch='" + branchCode + "' and "
                    + "schedule_no = " + scheduleNo + " and status=2").getResultList();
            Vector element1 = (Vector) passAmtList.get(0);
            double totalPassAmtToBeCr = Double.parseDouble(element1.get(0).toString());

            List retAmtList = em.createNativeQuery("select ifnull(sum(inst_amt),0) from cts_clg_in_entry "
                    + "where dt='" + curDate + "' and dest_branch='" + branchCode + "' and "
                    + "schedule_no = " + scheduleNo + " and status=4").getResultList();
            Vector element2 = (Vector) retAmtList.get(0);
            double totalRetAmtToBeCr = Double.parseDouble(element2.get(0).toString());

            ut.begin();

            String bankCode = getBankCode();
            if (bankCode == null || bankCode.equals("")) {
                throw new ApplicationException("Please define bank code in MB SMS SENDER BANK DETAIL.");
            }

            mainDetails = "CTS Completion";
            if (bankCode.equalsIgnoreCase("ccbl")) {
                if (totalPassAmtToBeCr > 0) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgGlPassToBeCr, 1, 0, totalPassAmtToBeCr, curDate,
                            curDate, userName, branchCode, branchCode, 65, mainDetails, 0.0f, 0.0f, scheduleNo, "CTS", "Y",
                            "SYSTEM", "A", 3, "", "", "TDA", 0.0f, "", "", "C", "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        throw new ApplicationException(ftsMsg);
                    }
                }

                if (totalRetAmtToBeCr > 0) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(glGlRetToBeCr, 1, 0, totalRetAmtToBeCr, curDate, curDate,
                            userName, branchCode, branchCode, 65, mainDetails, 0.0f, 0.0f, scheduleNo, "CTS", "Y", "SYSTEM",
                            "A", 3, "", "", "TDA", 0.0f, "", "", "C", "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        throw new ApplicationException(ftsMsg);
                    }
                }
            } else {
                double totalpassAndReturnAmount = totalPassAmtToBeCr + totalRetAmtToBeCr;
                if (totalpassAndReturnAmount > 0) {
                    ftsMsg = ftsPost43CBSRemote.ftsPosting43CBS(clgGlPassToBeCr, 1, 0, totalpassAndReturnAmount, curDate,
                            curDate, userName, branchCode, branchCode, 65, mainDetails, 0.0f, 0.0f, scheduleNo, "CTS", "Y",
                            "SYSTEM", "A", 3, "", "", "TDA", 0.0f, "", "", "C", "", 0.0f, "N", "", "", "");
                    if (!(ftsMsg.substring(0, 4).equalsIgnoreCase("true"))) {
                        throw new ApplicationException(ftsMsg);
                    }
                }
            }

            msg = "true";
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }

    public List getImageDetailsCts(String imageCode, Integer brnCode, Integer scheduleNo) throws ApplicationException {
        List imgDetails = null;
        try {
            String branchCode = brnCode.toString().length() == 1 ? "0" + brnCode.toString() : brnCode.toString();
            String curDt = ymd.format(date);
            imgDetails = em.createNativeQuery("select acno,inst_no,inst_amt,date_format(inst_dt,'%Y%m%d'),"
                    + "coalesce(favor_of,'') as favor_of,bank_name,enter_by,auth_by,status,coalesce(seq_no,0.0) as seq_no,"
                    + "orgn_branch,dest_branch,date_format(dt,'%Y%m%d'),details,auth,substatus,img_code,custname,"
                    + "oper_mode,userdetails,prbankcode,rbirefno,schedule_no from cts_clg_in_entry where "
                    + "img_code='" + imageCode + "' and dt='" + curDt + "' and "
                    + "dest_branch='" + branchCode + "' and schedule_no = " + scheduleNo + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return imgDetails;
    }

    public List<String> getIwAmountOnCurrentDt(String orgnCode) throws ApplicationException {
        List<String> dataList = new ArrayList<String>();
        try {
            List list = em.createNativeQuery("select ifnull(sum(inst_amt),0) from cts_clg_in_entry "
                    + "where dt='" + ymd.format(date) + "' and dest_branch='" + orgnCode + "' and status=2 and "
                    + "schedule_no=0").getResultList();
            Vector ele = (Vector) list.get(0);
            dataList.add(nf.format(Double.parseDouble(ele.get(0).toString())));

            list = em.createNativeQuery("select ifnull(sum(inst_amt),0) from cts_clg_in_entry "
                    + "where dt='" + ymd.format(date) + "' and dest_branch='" + orgnCode + "' and status=4 and "
                    + "schedule_no=0").getResultList();
            ele = (Vector) list.get(0);
            dataList.add(nf.format(Double.parseDouble(ele.get(0).toString())));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public String generateCtsReturnFile(String branch, String fileDt, String dirLocation,
            String useName, String orgnBrCode, String todayDt) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List returnList = new ArrayList();
            int ctsReturnFileCode = ftsPost43CBSRemote.getCodeForReportName("CTS-RETURN-FILE"); //(AMMCO-1,CCBL-2)
            if (ctsReturnFileCode == 1) {
                //It is ammco specific
                String query = "select ifnull(c.inst_no,'') as chq_no,c.inst_amt,ifnull(c.rbirefno,'') as UniqueNo,"
                        + "ifnull(c.userdetails,'') as reasonCode,ifnull(c.details,'') as reasonDescription from "
                        + "cts_clg_in_entry c where c.dt='" + ymd.format(dmyOne.parse(fileDt)) + "' and c.status=4";
                if (!branch.equalsIgnoreCase("ALL")) {
                    query = query + " and c.orgn_branch='" + branch + "'";
                }
                returnList = em.createNativeQuery(query).getResultList();
            } else if (ctsReturnFileCode == 0 || ctsReturnFileCode == 2) {
                //It is CCBL And Others Case
                String branchQuery = "";
                if (!branch.equalsIgnoreCase("ALL")) {
                    branchQuery = " and c.dest_branch='" + branch + "'";
                }

//                String query = "select ifnull(c.inst_no,'') as chq_no,c.inst_amt,ifnull(c.rbirefno,'') as rbi_ref_no,"
//                        + "ifnull(c.userdetails,'') as reasonCode,date_format(u.prdate,'%Y%m%d') as presentmentDate,"
//                        + "ifnull(u.remarks,'') as instrumentId,ifnull(u.truncatingrtno,'') as payorBankRouteNo,"
//                        + "ifnull(c.details,'') as reasonDescription,ifnull(session,'') as session from cts_clg_in_entry c,"
//                        + "cts_upload_pxf_cell u where c.dt = u.dt and c.inst_no = u.serialno and cast(c.dest_branch as "
//                        + "unsigned) = u.branchcode and c.dt='" + ymd.format(dmyOne.parse(fileDt)) + "' and "
//                        + "c.status=4 " + branchQuery + " group by rbi_ref_no";
                String query = "select ifnull(c.inst_no,'') as chq_no,c.inst_amt,ifnull(c.rbirefno,'') as rbi_ref_no,"
                        + "ifnull(c.userdetails,'') as reasonCode,date_format(u.prdate,'%Y%m%d') as presentmentDate,"
                        + "ifnull(u.remarks,'') as instrumentId,ifnull(u.truncatingrtno,'') as payorBankRouteNo,"
                        + "ifnull(c.details,'') as reasonDescription,ifnull(session,'') as session from cts_clg_in_entry c,"
                        + "cts_upload_pxf_cell u where c.dt = u.dt and c.inst_no = u.serialno and cast(c.dest_branch as "
                        + "unsigned) = u.branchcode and substring(u.itemseqno,5)=c.rbirefno and "
                        + "c.dt='" + ymd.format(dmyOne.parse(fileDt)) + "' and c.status=4 " + branchQuery + " group by rbi_ref_no";

                returnList = em.createNativeQuery(query).getResultList();
            }
            if (returnList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the return file.");
            }
            //Branch MICR Extraction
            String micr = "";
            List list;
            if (ctsReturnFileCode == 0 || ctsReturnFileCode == 1) {
                list = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                        + "bank_code,ifnull(b.branchcode,'') as brach_code from bnkadd b,branchmaster m "
                        + "where b.alphacode=m.alphacode and m.brncode=" + Integer.parseInt(branch) + "").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define MICR code for branch.");
                }
                Vector ele = (Vector) list.get(0);
                String cityCode = ele.get(0).toString().trim();
                String bankCode = ele.get(1).toString().trim();
                String branchCode = ele.get(2).toString().trim();
                if (cityCode.equals("") || bankCode.equals("") || branchCode.equals("")
                        || cityCode.length() != 3 || bankCode.length() != 3) {
                    throw new ApplicationException("Please define proper MICR code.");
                }
                micr = cityCode + bankCode + ParseFileUtil.addTrailingZeros(branchCode, 3);
            } else if (ctsReturnFileCode == 2) {
                list = em.createNativeQuery("select ifnull(code,'') as routingCode from cbs_parameterinfo "
                        + "where name in('HDFC-CTS-ROUTING-CODE')").getResultList();
                if (list.isEmpty()) {
                    throw new Exception("Please define HDFC-CTS-ROUTING-CODE in structure of cbs parameterinfo.");
                }
                Vector ele = (Vector) list.get(0);
                micr = ele.get(0).toString().trim(); //Filename routing code
                if (micr.equals("") || micr.length() != 9) {
                    throw new Exception("Please define 9 digit routing code as HDFC-CTS-ROUTING-CODE in structure of cbs parameterinfo.");
                }
            }

            String presentmentDate = "", session = "";
            if (ctsReturnFileCode != 1) { //CCBL And Others
                Vector ele = (Vector) returnList.get(0);
                presentmentDate = ele.get(4).toString().trim();
                session = ele.get(8).toString().trim();
            }

            //Extracting seqno
            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files where "
                    + "file_gen_date='" + ymd.format(dmyOne.parse(fileDt)) + "' and file_gen_type='CTS'").getResultList();
            Vector ele = (Vector) list.get(0);
            String fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();  //Make to 2 digit.
            }

            String fileName = "";
            if (ctsReturnFileCode == 1) { //AMMCO
                fileName = micr.trim() + "_" + ymdOne.format(dmyOne.parse(fileDt)) + "_01_"
                        + ParseFileUtil.addTrailingZeros(fileNo, 2) + ".txt";
            } else if (ctsReturnFileCode == 2) { //CCBL
                fileName = micr.trim() + "_" + ymdOne.format(dmyOne.parse(todayDt)) + "_" + session + "_"
                        + ParseFileUtil.addTrailingZeros(fileNo, 2) + ".RET";
            } else { //Others
                fileName = micr.trim() + "_" + ymdOne.format(dmyOne.parse(todayDt)) + "_01_"
                        + ParseFileUtil.addTrailingZeros(fileNo, 2) + ".RET";
            }

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ","
                    + "'" + ymd.format(dmyOne.parse(fileDt)) + "','" + fileName + "','" + useName + "',now(),"
                    + "'" + orgnBrCode + "','CTS')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in file seq maintenance.");
            }

            FileWriter fw = new FileWriter(dirLocation + "/" + fileName);

            for (int i = 0; i < returnList.size(); i++) {
                ele = (Vector) returnList.get(i);
                String instNo = ele.get(0).toString().trim();
                BigDecimal instAmt = new BigDecimal(nf.format(Double.parseDouble(ele.get(1).toString().trim())));
                String strAmt = instAmt.toString();
                BigDecimal fileAmt = null;
                if (strAmt.contains(".")) {
                    int decimalIndex = strAmt.indexOf(".");
                    String valAfterDecimal = strAmt.substring(decimalIndex + 1).trim();
                    if (valAfterDecimal.length() == 1) {
                        fileAmt = new BigDecimal(strAmt.substring(0, decimalIndex) + strAmt.substring(decimalIndex + 1) + "0");
                    } else {
                        fileAmt = new BigDecimal(strAmt.substring(0, decimalIndex) + strAmt.substring(decimalIndex + 1));
                    }
                } else {
                    if (ctsReturnFileCode == 1) { //AMMCO
                        fileAmt = new BigDecimal(strAmt);
                    } else { //CCBL And Others
                        fileAmt = new BigDecimal(strAmt).multiply(new BigDecimal(100));
                    }
                }
                String rbiRefNo = ele.get(2).toString().trim();
                String reasonCode = ele.get(3).toString().trim().length() < 2 ? "0" + ele.get(3).toString().trim() : ele.get(3).toString().trim();
                String reasonDesc = "", presentDt = "", intSeqNo = "", payorBankRouteNo = "", sessionNo = "";
                if (ctsReturnFileCode != 1) {
                    presentDt = ele.get(4).toString().trim();
                    intSeqNo = ele.get(5).toString().trim();
                    payorBankRouteNo = ele.get(6).toString().trim();
                    reasonDesc = ele.get(7).toString().trim(); //There will value only in case of 88 - Other
                    sessionNo = ele.get(8).toString().trim();
                } else {
                    reasonDesc = ele.get(4).toString().trim(); //There will value only in case of 88 - Other
                }
                if (ctsReturnFileCode == 1) {
                    if (instNo.equals("") || instAmt.compareTo(new BigDecimal("0")) == 0
                            || rbiRefNo.equals("") || reasonCode.equals("")) {
                        throw new ApplicationException("Please check instrument data. Mandatory fields are not found.");
                    }
                } else {
                    if (payorBankRouteNo.equalsIgnoreCase("") || instNo.equals("") || instAmt.compareTo(new BigDecimal("0")) == 0 || rbiRefNo.equals("")
                            || presentDt == null || presentDt.equals("") || reasonCode.equals("") || intSeqNo.equals("")) {
                        throw new ApplicationException("Please check instrument data. Mandatory fields are not found.");
                    }
                }

                if (reasonCode.equals("88")) { //For Other Reason
                    reasonDesc = reasonDesc.replaceAll("[\\W_]", " ").trim();
                    reasonDesc = reasonDesc.length() > 25 ? reasonDesc.substring(0, 25) : reasonDesc;
                    reasonCode = reasonCode + "-" + reasonDesc;
                }
                String singleEntry = "";
                if (ctsReturnFileCode == 1) {
                    singleEntry = instNo + "|" + fileAmt + "|" + rbiRefNo + "|" + reasonCode + "\n";
                } else if (ctsReturnFileCode == 2) {
                    singleEntry = payorBankRouteNo + "|" + instNo + "|" + fileAmt + "|"
                            + ymdOne.format(ymd.parse(presentDt)) + "|" + reasonCode + "|" + intSeqNo + "\r\n";
                } else {
                    singleEntry = micr.trim() + "|" + instNo + "|" + fileAmt + "|"
                            + ymdOne.format(ymd.parse(presentDt)) + "|" + reasonCode + "|" + intSeqNo + "\n";
                }
                fw.write(singleEntry);
            }
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true";
    }

    public List<NpciFileDto> showCtsReturnFiles(String branch, String fileGenDt) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + fileGenDt + "' and "
                    + "file_gen_type='CTS' and file_gen_brncode='" + branch + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                Vector ele = (Vector) list.get(i);

                dto.setFileNo(new BigInteger(ele.get(0).toString()));
                dto.setFileGenDt(ele.get(1).toString());
                dto.setFileName(ele.get(2).toString());
                dto.setFileGenBy(ele.get(3).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String odBalStopUpdation(String txnId, String authBy, String returnMsg) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select status from cts_clg_in_entry where txn_id = '" + txnId + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                int dbStatus = Integer.parseInt(ele.get(0).toString().trim());
                if (!(dbStatus == 2 || dbStatus == 4)) {
                    int n = em.createNativeQuery("update cts_clg_in_entry set status = 3,auth_by='" + authBy + "',auth='Y',"
                            + "substatus='U',userdetails='HOLD',details='" + returnMsg + "' where txn_id = '" + txnId + "' and "
                            + "date_format(dt,'%Y%m%d') = '" + ymd.format(new Date()) + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in inward clearing updation.");
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String getGlHeadName(String acno) throws ApplicationException {
        String name = "";
        try {
            List list = em.createNativeQuery("select acname from gltable where acno='" + acno + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                name = ele.get(0).toString().trim();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return name;
    }
}
