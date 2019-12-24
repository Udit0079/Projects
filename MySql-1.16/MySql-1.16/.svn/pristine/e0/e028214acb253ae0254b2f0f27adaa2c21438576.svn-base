/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ib;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.cdci.CustomerIdPojo;
import com.cbs.dto.ib.IbRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.cdci.IBSWSFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.inventory.ChequeMaintinanceRegisterFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

@Stateless(mappedName = "IbRequestMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class IbRequestMgmtFacade implements IbRequestMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    IBSWSFacadeRemote ibswsRemote;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    ChequeMaintinanceRegisterFacadeRemote cmrFacade;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public List<IbRequestTo> getIbRequestForBranch(String reqType, String reqStatus,
            String brCode, String todayDt) throws ApplicationException {
        List<IbRequestTo> requestList = new ArrayList<IbRequestTo>();
        try {
            List list = em.createNativeQuery("select acno,request_no,request_type,date_format(ib_request_dt,'%d/%m/%Y') as "
                    + "ib_request_dt,date_format(cbs_request_dt,'%d/%m/%Y') as cbs_request_dt,request_status,"
                    + "request_break_no,invt_class,invt_type,ifnull(reschedule_date,''),reschedule_reason,reschedule_by,"
                    + "charge_status from ib_request where request_type='" + reqType + "' and "
                    + "request_status='" + reqStatus + "' and substring(acno,1,2)='" + brCode + "' and "
                    + "cbs_request_dt='" + todayDt + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                IbRequestTo to = new IbRequestTo();
                Vector ele = (Vector) list.get(i);

                to.setAcno(ele.get(0).toString().trim());
                to.setRequestNo(Long.parseLong(ele.get(1).toString().trim()));
                to.setRequestType(ele.get(2).toString().trim());
                to.setIbRequestDt(ele.get(3).toString().trim());
                to.setCbsRequestDt(ele.get(4).toString().trim());
                to.setRequestStatus(ele.get(5).toString().trim());
                to.setRequestBreakNo(Long.parseLong(ele.get(6).toString().trim()));
                to.setInvtClass(ele.get(7).toString().trim());
                to.setInvtType(ele.get(8).toString().trim());
                String rescheduledDt = ele.get(9).toString().trim();
                to.setRescheduleDt(rescheduledDt.equals("") ? "" : dmy.format(yyyymmdd.parse(rescheduledDt)));
                to.setRescheduleReason(ele.get(10).toString().trim());
                to.setRescheduleBy(ele.get(11).toString().trim());
                to.setChargeStatus(ele.get(12).toString().trim());

                requestList.add(to);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return requestList;
    }

    @Override
    public String processChequeBookIssue(String acno, long chqFrom, long chqTo, String chqSeries, long noOfLeaves, String invtClass, String invtType,
            String userName, String todayDt, String orgnCode, long requestNo, long requestBrNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<>();
        try {
            ut.begin();
            String san = "";
            long originalChqFrom = chqFrom;
            List sanList = em.createNativeQuery("SELECT SAN FROM chbookmaster WHERE acno='" + acno + "'").getResultList();
            if (sanList.isEmpty()) {
                sanList = em.createNativeQuery("SELECT max(cast(ifnull(SAN,0) as unsigned))+1 FROM chbookmaster").getResultList();
            }
            Vector eleNat = (Vector) sanList.get(0);
            san = CbsUtil.lPadding(6, Integer.parseInt(eleNat.get(0).toString()));

            int n = em.createNativeQuery("insert into chbookmaster(acno,chbookno,chnofrom,chnoto,remarks,issuedby,issuedt,leafs,auth,authby,chargeflag,atpar,"
                    + "chbooktype,san) values('" + acno + "','" + chqSeries + "'," + chqFrom + "," + chqTo + ",'','" + userName + "','" + todayDt + "',"
                    + noOfLeaves + ",'Y','System','Y','N','bearer','" + san + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in cheque book issue. Error Code-001");
            }

            n = em.createNativeQuery("update chbookmaster_stock set issuedt='" + todayDt + "',"
                    + "status='U' where chnofrom=" + chqFrom + " and chnoto=" + chqTo + " and chqsrno='" + chqSeries + "' and "
                    + "brncode = '" + orgnCode + "' and invt_class='" + invtClass + "' and "
                    + "invt_type='" + invtType + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in cheque book issue. Error Code-002");
            }

            n = em.createNativeQuery("update accountmaster set chequebook=1 where acno='" + acno + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in cheque book issue. Error Code-003");
            }

            CustomerIdPojo pojo = ibswsRemote.getDetailOfAccountNumber(acno);
            String chqBookTable = "";
            if (pojo.getActNature().equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                chqBookTable = "chbook_sb";
            } else if (pojo.getActNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                chqBookTable = "chbook_ca";
            }
            long noOfChq = (chqTo - chqFrom) + 1;
            while (chqFrom <= chqTo) {
                n = em.createNativeQuery("insert into " + chqBookTable + "(acno,chqno,statusflag,issuedt,authby,chqsrno) "
                        + "values('" + acno + "','" + chqFrom + "','F','" + todayDt + "',"
                        + "'" + userName + "','" + chqSeries + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in cheque book issue. Error Code-004");
                }
                chqFrom = chqFrom + 1;
            }

            if (ftsRemote.getCodeForReportName("CHEQUE-BOOK-PRINTING-FILE") == 1) {

                List txnCodeList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_rec_no='015' and ref_desc='" + pojo.getActNature() + "'").getResultList();

                if (txnCodeList.isEmpty()) {
                    throw new ApplicationException("Please define the Transaction Code");
                }

                Vector txnVect = (Vector) txnCodeList.get(0);
                String txnCode = txnVect.get(0).toString();


                if (ftsRemote.getCodeForReportName("CSN-OR-SAN") == 1) {
                    san = chqSeries;
                }

                if (!(noOfLeaves == 15 || noOfLeaves == 30 || noOfLeaves == 45)) {
                    throw new ApplicationException("Cheque Book size must be 15,30,45");
                }

                List srNoList = em.createNativeQuery("select ifnull(max(srNo),0)+1 from chbook_file_details").getResultList();
                Vector srNoVect = (Vector) srNoList.get(0);
                long srNo = Long.parseLong(srNoVect.get(0).toString());

                String query = "INSERT INTO chbook_file_details (acno, san, txn_code, at_par, chbook_type, book_size, start_chq_no, end_chq_no, "
                        + "entery_by, enter_date, file_gen_by, file_name,srNo) VALUES ('" + pojo.getAcNo() + "', '" + san + "', '" + txnCode
                        + "', 'N', 'bearer'," + noOfLeaves + ", '" + originalChqFrom + "', '" + chqTo + "', '"
                        + userName + "',now() , '',''," + srNo + ")";

                int rs = em.createNativeQuery(query).executeUpdate();

                if (rs <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
            }

            //Charge handling
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='IB-CHEQUE-BOOK-CHG'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define applied account type for chequebook charges in internet banking.");
            }
            Vector ele = (Vector) list.get(0);
            String[] splittedValue = ele.get(0).toString().trim().split(",");
            String appliedAccountType = "";
            for (int i = 0; i < splittedValue.length; i++) {
                if (appliedAccountType.equals("")) {
                    appliedAccountType = splittedValue[i];
                } else {
                    appliedAccountType = appliedAccountType + "," + splittedValue[i];
                }
            }
            if (appliedAccountType.trim().toUpperCase().contains(pojo.getAccountType().trim().toUpperCase())) {
                list = em.createNativeQuery("select ifnull(charges,0),glheadmisc from parameterinfo_miscincome "
                        + "where acctcode='" + pojo.getAcType() + "' and purpose='cheque book charges'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define charge head for cheque book. Error Code-005");
                }
                ele = (Vector) list.get(0);
                float tmpCharges = Float.parseFloat(ele.get(0).toString().trim());
                String glHead = pojo.getBrCode() + ele.get(1).toString().trim() + "01";

                String custState = "", branchState = "";
                List stateCodeList = em.createNativeQuery("select ci.acno, ifnull(cm.MailStateCode,'') as stateCode, "
                        + " ifnull(br.State,'') as brState from "
                        + " customerid ci, cbs_customer_master_detail cm, branchmaster br where "
                        + " ci.CustId = cast(cm.customerid as unsigned) "
                        + " and br.BrnCode=cast(substring(ci.Acno,1,2) as unsigned) and ci.acno = '" + acno + "'").getResultList();
                if (!stateCodeList.isEmpty()) {
                    Vector stateCodeVect = (Vector) stateCodeList.get(0);
                    custState = stateCodeVect.get(0).toString();
                    branchState = stateCodeVect.get(1).toString();
                }
                int sTaxCode = ftsRemote.getCodeForReportName("STAXMODULE_ACTIVE");
                double commAmt = tmpCharges * noOfChq;
                float tmpTrsno = ftsRemote.getTrsNo();
                if (tmpCharges > 0) {
                    String msg = ftsRemote.insertRecons(pojo.getActNature(), acno, 1, commAmt, todayDt, todayDt,
                            2, "Cheque Book Charges", userName, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                            userName, 8, 3, "", null, 0f, "", "", 1, "", 0f, "", "", pojo.getBrCode(),
                            pojo.getBrCode(), 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg + " Error Code-006");
                    }
                    msg = ftsRemote.updateBalance(pojo.getActNature(), acno, 0, commAmt, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg + " Error Code-007");
                    }
                    msg = ftsRemote.insertRecons("PO", glHead, 0, commAmt, todayDt, todayDt,
                            2, "Cheque Book Charges for:" + acno, userName, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                            userName, 8, 3, "", null, 0f, "", "", 1, "", 0f, "", "", pojo.getBrCode(),
                            pojo.getBrCode(), 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg + " Error Code-008");
                    }
                    msg = ftsRemote.updateBalance("PO", glHead, commAmt, 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg + " Error Code-009");
                    }
                    if (sTaxCode == 1) {
//                    double ttlTax = cmrFacade.findTax(commAmt, todayDt);
                        String mainDetails = null;
                        double sTax = 0d;
                        if (branchState.equalsIgnoreCase(branchState)) {
                            map = ibRemote.getTaxComponent(commAmt, todayDt);
                        } else {
                            map = ibRemote.getIgstTaxComponent(commAmt, todayDt);
                        }
                        Set<Map.Entry<String, Double>> set = map.entrySet();
                        Iterator<Map.Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = it.next();
                            sTax = sTax + Double.parseDouble(entry.getValue().toString());
                        }

                        msg = ftsRemote.insertRecons(pojo.getActNature(), acno, 1, sTax, todayDt, todayDt,
                                2, "GST For Cheque Book Charge", userName, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                                userName, 8, 3, "", null, 0f, "", "", 1, "", 0f, "", "", pojo.getBrCode(),
                                pojo.getBrCode(), 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg + " Error Code-010");
                        }
                        msg = ftsRemote.updateBalance(pojo.getActNature(), acno, 0, sTax, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg + " Error Code-011");
                        }

                        Set<Map.Entry<String, Double>> set1 = map.entrySet();
                        Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                        while (it1.hasNext()) {
                            Map.Entry entry = it1.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = pojo.getBrCode() + keyArray[1];
                            mainDetails = description.toUpperCase() + " for Cheque Book for. " + acno;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

//                      List<String> taxDetail = ftsRemote.getTaxDetail(todayDt);
//                      if (taxDetail.isEmpty()) {
//                          throw new ApplicationException("Please define proper tax master. Error Code-011");
//                      }
//                      String glAcno = pojo.getBrCode() + taxDetail.get(3) + "01";
                            msg = ftsRemote.insertRecons("PO", taxHead, 0, taxAmount, todayDt, todayDt,
                                    2, mainDetails, userName, tmpTrsno, null, ftsRemote.getRecNo(), "Y",
                                    userName, 8, 3, "", null, 0f, "", "", 1, "", 0f, "", "", pojo.getBrCode(),
                                    pojo.getBrCode(), 0, "", "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg + " Error Code-012");
                            }
                            msg = ftsRemote.updateBalance("PO", taxHead, taxAmount, 0, "Y", "Y");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg + " Error Code-013");
                            }
                        }
                    }
                }
            }
            //Status updation in request table.
            n = em.createNativeQuery("update ib_request set request_status='APPROVED',charge_status='Y',"
                    + "process_by='" + userName + "',process_dt=current_timestamp where "
                    + "request_no=" + requestNo + " and request_break_no=" + requestBrNo + "").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in cheque book issue. Error Code-014");
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

    public String reScheduleProcess(String brBusinessDt, String reScheduleReason,
            String reScheduleBy, long requestNo, long requestBrNo, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select reschedule_by from ib_request where "
                    + "request_no=" + requestNo + " and request_break_no=" + requestBrNo + "").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("This request no does not exist.");
            }
            Vector ele = (Vector) list.get(0);
            String dbRescheduleBy = ele.get(0).toString().trim();
            if (!dbRescheduleBy.equals("")) {
                throw new ApplicationException("This request no has been already rescheduled by " + dbRescheduleBy);
            }

            list = em.createNativeQuery("select min(date) from bankdays where "
                    + "brncode='" + orgnBrCode + "' and dayendflag='Y' and "
                    + "dayendflag1='N' and daybeginflag='N'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("System is unable find next business date for branch.");
            }
            ele = (Vector) list.get(0);
            String brNextBusinessDt = ele.get(0).toString().trim();

            int n = em.createNativeQuery("update ib_request set cbs_request_dt='" + brNextBusinessDt + "',"
                    + "reschedule_date='" + brBusinessDt + "',reschedule_time=current_timestamp,"
                    + "reschedule_reason='" + reScheduleReason + "',reschedule_by='" + reScheduleBy + "' where "
                    + "request_no=" + requestNo + " and request_break_no=" + requestBrNo + "").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in reschedule process for request no:" + requestNo);
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
}
