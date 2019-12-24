/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.ChBookAuthorization;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.MbSubscriberTabTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsBulkPostingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.inventory.ChequeMaintinanceRegisterFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.ChbookDetailPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author root
 */
@Stateless(mappedName = "OtherAuthorizationManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OtherAuthorizationManagementFacade implements OtherAuthorizationManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsMethods;
    @EJB
    ChequeMaintinanceRegisterFacadeRemote cmrFacade;
    @EJB
    FtsBulkPostingFacadeRemote ftsBulk;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private TransactionManagementFacadeRemote txnAuth;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMddhhmmss");

    public List<String> returnAuthStatus(String tempBd, String orgBrnCode) throws ApplicationException {
        String str1 = "";
        String str2 = "";

        List<String> list = new ArrayList<String>();
        String dt1 = tempBd.substring(0, 10) + " " + "00:00";
        String dt2 = tempBd.substring(0, 10) + " " + "23:59";
        String date = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);

        List list2 = em.createNativeQuery("select acno from tokentable_credit where  dt='" + date + "' and (auth ='N' or auth is null) and org_brnid='"
                + orgBrnCode + "'").getResultList();
        if (!list2.isEmpty()) {
            list.add("Authorization is not completed for cash credit transactions");
        }
        List list3 = em.createNativeQuery("select acno from tokentable_debit where auth='N' and dt='" + date + "' and org_brnid='" + orgBrnCode + "'").getResultList();
        if (!list3.isEmpty()) {
            list.add("Authorization is not completed for cash debit transactions");
        }

        List list4 = em.createNativeQuery("select acno from recon_cash_d where auth='N' and dt='" + date + "' AND substring(ACNO,3,10) NOT IN "
                + "(SELECT DISTINCT ACNO FROM abb_parameter_info WHERE  PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999) and org_brnid='"
                + orgBrnCode + "'").getResultList();
        if (!list4.isEmpty()) {
            list.add("Authorization is not completed for cash transactions");
        }

        List list5 = em.createNativeQuery("select acno from recon_clg_d where auth='N' and dt='" + date + "' and org_brnid='" + orgBrnCode + "'").getResultList();
        if (!list5.isEmpty()) {
            list.add("Authorization is not completed for clearing transactions");
        }

        List list6 = em.createNativeQuery("select tokenno,subtokenno,acno,custname,dramt,enterby,Authby,recno from recon_cash_d where (tokenpaidby is null OR tokenpaidby='') "
                + " and auth = 'Y' and ty=1 and trantype=0 AND DT='" + date + "' AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info "
                + "WHERE PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999) and org_brnid='" + orgBrnCode + "'").getResultList();
        if (!list6.isEmpty()) {
            list.add("Token paid is not completed.");
        }

        List list7 = em.createNativeQuery("select enterby from dds_auth_d ds,accountmaster am where tokenpaid= 'N' AND DT='" + date + "' and ds.ACNo = "
                + "am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list7.isEmpty()) {
            list.add("Cash deposit receiving is not completed for DDS. (In DDS Module)");
        }

        List list8 = em.createNativeQuery("select enterby from dds_auth_d ds,accountmaster am where tokenpaid= 'Y' AND DT='" + date + "' and ds.ACNo = "
                + "am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list8.isEmpty()) {
            list.add(" Cash deposit authorization is not completed for DDS. (In DDS Module)");
        }

        List list9 = em.createNativeQuery("select acno from recon_trf_d where auth='N' and dt='" + date + "' and org_brnid='" + orgBrnCode + "' and trandesc<>77").getResultList();
        if (!list9.isEmpty()) {
            list.add("Authorization is not completed for transfer transactions");
        }

        List list10 = em.createNativeQuery("select rd.acno from recon_ext_d rd, accountmaster am where auth='N' and dt='" + date + "' and rd.ACNo = "
                + "am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list10.isEmpty()) {
            list.add("Authorization is not completed for extension counter transactions");
        }

//        List list11 = em.createNativeQuery("select tdr.acno,voucherno,auth,tdr.authby,enterby from td_renewal_auth tdr,td_accountmaster tda where "
//                + "auth='N' and date_format(trantime,'%Y%m%d')='" + date + "' and tdr.ACNo = tda.ACNo and tda.CurBrCode='" + orgBrnCode + "'").getResultList();
//        if (!list11.isEmpty()) {
//            list.add(" TD renewal authorization till pending");
//        }
        List list11 = em.createNativeQuery("select tdr.acno from td_renewal_auth tdr,td_accountmaster tda where "
                + "auth='N' and date_format(trantime,'%Y%m%d')='" + date + "' and tdr.ACNo = tda.ACNo and tda.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list11.isEmpty()) {
            list.add(" TD renewal authorization till pending");
        }

        List list12 = em.createNativeQuery("select * from chbookmaster ch,accountmaster am where  issuedt='" + date + "' and (auth ='N' or auth is null) "
                + "and ch.ACNo = am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list12.isEmpty()) {
            list.add("Authorization is not completed for cheque book transactions");
        }

        List list13 = em.createNativeQuery("select auth from bill_po where (auth ='N' or auth is null) and dt='" + date + "' and substring(acno,1,2)='"
                + orgBrnCode + "'").getResultList();
        if (!list13.isEmpty()) {
            list.add("Authorization is not completed for PO");
        }

        List list14 = em.createNativeQuery("select auth from bill_dd where (auth ='N' or auth is null) and dt='" + date + "' and substring(acno,1,2)='"
                + orgBrnCode + "'").getResultList();
        if (!list14.isEmpty()) {
            list.add("Authorization is not completed for DD");
        }

        List list15 = em.createNativeQuery("select auth from bill_tpo where (auth ='N' or auth is null) and dt='" + date + "' and substring(acno,1,2)='"
                + orgBrnCode + "'").getResultList();
        if (!list15.isEmpty()) {
            list.add("Authorization is not completed for TPO");
        }

        List list16 = em.createNativeQuery("select acno from accountmaster where (Authby Is Null or authby='') and accstatus<>9 and CurBrCode='"
                + orgBrnCode + "'").getResultList();
        if (!list16.isEmpty()) {
            list.add("Account authorization is not completed ");
        }

        List list17 = em.createNativeQuery("select acno from td_accountmaster where (Authby Is Null or authby='') and accstatus<>9 and CurBrCode='"
                + orgBrnCode + "'").getResultList();
        if (!list17.isEmpty()) {
            list.add("Account authorization for term deposit is not completed ");
        }

        List list18 = em.createNativeQuery("select acno,auth,authBy,enteredBy from acedithistory where authby is null or authby ='' and "
                + "substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
        if (!list18.isEmpty()) {
            list.add("Authorization of edit accounts is not completed ");
        }

        List list19 = em.createNativeQuery("select lo.acno,lo.authBy,enterBy from loan_oldinterest lo,accountmaster am where lo.authby is null and "
                + "lo.ACNo = am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list19.isEmpty()) {
            list.add("Authorization for ROI and Limits is not completed");
        }

        List list20 = em.createNativeQuery("select acno from accountstatus where auth='N' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
        if (!list20.isEmpty()) {
            list.add("Authorization for accountstatus is not completed");
        }

        List list21 = em.createNativeQuery("Select status From chbookdetail ch,accountmaster am Where ch.authBy is null And Status in(1,9) and ch.ACNo = "
                + "am.ACNo and am.CurBrCode='" + orgBrnCode + "' and enterydate='" + date + "'").getResultList();
        if (!list21.isEmpty()) {
            list.add("Pending authorization for CHQ stoppayment to operative marking");
        }

        List list22 = em.createNativeQuery("select tdv.acno from td_vouchmst_duplicate tdv,td_accountmaster td where tdv.auth = 'N'and tdv.authby is "
                + "null and tdv.ACNo = td.ACNo and td.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list22.isEmpty()) {
            list.add("Pending authorization for TD duplicate receipt");
        }

        List list23 = em.createNativeQuery("select auth from bill_obcbooking bo,accountmaster am where (auth='N' or auth is null) and billtype "
                + "in('DD','PO','CHQ') and bo.ACNo = am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list23.isEmpty()) {
            list.add("Pending authorization for local bill purchase");
        }

        List list24 = em.createNativeQuery("Select bill.authby From bill_bpdgulc_master bill,accountmaster am Where bill.AuthBy is Null and bill.ACNo = am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list24.isEmpty()) {
            list.add("Pending authorization for LC BG limit ");
        }

        List list25 = em.createNativeQuery("select billtype,bill.acno,instno,instamount,instdt,enterby,auth,bankname,bankaddr,coll_bankname, "
                + "coll_bankaddr from bill_obcbooking bill,accountmaster am where (auth='N' or auth is null) and bill.ACNo = am.ACNo and am.CurBrCode='"
                + orgBrnCode + "'").getResultList();
        if (!list25.isEmpty()) {
            list.add("Pending authorization for OBC");
        }

        List list26 = em.createNativeQuery("select billtype,bill.acno,instno,instamount,instdt,enterby,auth,bankname,bankaddr from bill_ibcbooking bill,"
                + "accountmaster am where (auth='N' or auth is null) and bill.ACNo = am.ACNo and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list26.isEmpty()) {
            list.add("Pending authorization for IBC");
        }

        List list27 = em.createNativeQuery("Select auth from bill_lc bill,accountmaster am Where (auth='N' or auth is null) and bill.ACNo = am.ACNo "
                + "and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list27.isEmpty()) {
            list.add("Pending authorization for letter of credit");
        }

        List list28 = em.createNativeQuery("Select auth from bill_bg bill,accountmaster am Where (auth='N' or auth is null) and bill.ACNo = am.ACNo "
                + "and am.CurBrCode='" + orgBrnCode + "'").getResultList();
        if (!list28.isEmpty()) {
            list.add("Pending authorization for bank guarantee");
        }

        List list29 = em.createNativeQuery("Select auth from lockeracmaster lo Where (auth='N' or auth is null) and lo.brncode='" + orgBrnCode + "'").getResultList();
        if (!list29.isEmpty()) {
            list.add("Pending authorization for lockers issued");
        }

        List list30 = em.createNativeQuery("select auth from bill_ad where (auth ='N' or auth is null) and dt='" + date + "' and substring(acno,1,2)='"
                + orgBrnCode + "'").getResultList();
        if (!list30.isEmpty()) {
            list.add("Authorization is not completed for AD");
        }

        List list31 = em.createNativeQuery("select auth from td_receiptmaster where (auth ='N' or auth is null) and dt='" + date + "' and brncode='"
                + orgBrnCode + "'").getResultList();
        if (!list31.isEmpty()) {
            list.add("Authorization is not completed for TD Receipt Issue.");
        }

        List list32 = em.createNativeQuery("select auth from bill_hoothers where (auth ='N' or auth is null) and dt='" + date + "' and "
                + "substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
        if (!list32.isEmpty()) {
            list.add("Authorization is not completed for BC or BP");
        }

        List list33 = em.createNativeQuery("Select clg.acno From clg_in_entry clg,accountmaster am where clg.ACNo = am.ACNo and am.CurBrCode='"
                + orgBrnCode + "'").getResultList();
        if (!list33.isEmpty()) {
            list.add("Please check inward clearing entries");
        }

        List list34 = em.createNativeQuery("Select newAcno from cbs_cust_image_detail where upper(auth)='N' AND substring(newacno,1,2)='"
                + orgBrnCode + "'").getResultList();
        if (!list34.isEmpty()) {
            list.add("Scaning authorization is not completed");
        }

        List list35 = em.createNativeQuery("select acno,closedby from acctclose_his where auth<>'Y' and substring(acno,1,2)='"
                + orgBrnCode + "'").getResultList();
        if (!list35.isEmpty()) {
            list.add("Account closing authorization is not completed");
        }

        List list36 = em.createNativeQuery("Select Distinct EmFlag from clg_in_history clg1,accountmaster am where TxnDate='" + date + "' and clg1.Acno = "
                + "am.ACNo and am.CurBrCode = '" + orgBrnCode + "' Union Select Distinct EmFlag from clg_in_returned clg2,accountmaster am where "
                + "TxnDate between '" + dt1 + "' and '" + dt2 + "' and clg2.Acno = am.ACNo and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
        if (!list36.isEmpty()) {
            for (int j = 0; j < list36.size(); j++) {
                Vector v36 = (Vector) list36.get(j);
                List list37 = em.createNativeQuery("select Details from gl_recon where details like 'Inward Clg Amt Dt ' + '" + date + "' +  ' for Circle "
                        + "Type ' + '" + v36.get(0).toString() + "' and org_brnid='" + orgBrnCode + "'").getResultList();
                if (list37.isEmpty()) {
                    list.add("Inward clearing completion pending for circle type " + v36.get(0).toString());
                }
            }
        }

        List list37 = em.createNativeQuery("select emflag from clg_ow_register where upper(status) in ('OPEN') AND ENTRYDATE='" + date + "' and brncode='" + orgBrnCode + "'").getResultList();
        if (!list37.isEmpty()) {
            for (int k = 0; k < list37.size(); k++) {
                Vector v37 = (Vector) list37.get(k);
                list.add("Current date O/W clearing register for circle Type " + v37.get(0).toString() + " is still OPEN.");
            }
        }

        List list38 = em.createNativeQuery("select emflag from clg_ow_register where upper(status) not in ('POSTED','CLEARED','UNCLEARED','HELD','OPEN') "
                + "AND PostingDATE='" + date + "' and brncode='" + orgBrnCode + "'").getResultList();
        if (!list38.isEmpty()) {
            for (int l = 0; l < list38.size(); l++) {
                Vector v38 = (Vector) list38.get(l);
                list.add("O/W clearing register for circle Type " + v38.get(0).toString() + " is still not Posted.");
            }
        }

        List list39 = em.createNativeQuery("select emflag from clg_ow_register where upper(status) not in ('CLEARED','OPEN') AND clearingDATE='" + date + "' and brncode='" + orgBrnCode + "'").getResultList();
        if (!list39.isEmpty()) {
            for (int m = 0; m < list39.size(); m++) {
                Vector v39 = (Vector) list39.get(m);
                list.add("O/W clearing register for circle Type " + v39.get(0).toString() + " is still not CLEARED.");
            }
        }
        List list40 = em.createNativeQuery("select AcctCode,Case Substring(LoanAuth,1,1)  When '9' then 1 else 0 end Reserved , Case "
                + "Substring(LoanAuth,2,1)  When '1' then 1 else 0 end Guarantor ,Case Substring(LoanAuth,3,1)  When '1' then 1 else 0 end EMI ,"
                + "Case Substring(LoanAuth,4,1)  When '1' then 1 else 0 end LoanMIS ,Case Substring(LoanAuth,5,1)  When '1' then 1 else 0 end LegalDoc ,"
                + "Case Substring(LoanAuth,6,1)  When '1' then 1 else 0 end LoanShare ,Case Substring(LoanAuth,7,1)  When '1' then 1 else 0 end Security ,"
                + "Case Substring(LoanAuth,8,1)  When '1' then 1 else 0 end Disb , Case Substring(LoanAuth,9,1)  When '1' then 1 else 0 end Insurance , "
                + "Case Substring(LoanAuth,10,1) When '1' then 1 else 0 end Company , Case Substring(LoanAuth,11,1) When '1' then 1 else 0 end Employment ,"
                + " Case Substring(LoanAuth,12,1) When '1' then 1 else 0 end Extra From accounttypemaster where AcctNature in ('TL','DL','CA') and AcctCode "
                + "not in ('CA')").getResultList();
        if (!list40.isEmpty()) {
            for (int n = 0; n < list40.size(); n++) {
                Vector v40 = (Vector) list40.get(n);
                if (Integer.parseInt(v40.get(4).toString()) == 1) {
                    if (str1.equals("")) {
                        str1 = v40.get(0).toString();
                    } else {
                        str1 = str1 + "," + v40.get(0).toString();
                    }
                }
                if (Integer.parseInt(v40.get(7).toString()) == 1) {
                    if (str2.equals("")) {
                        str2 = v40.get(0).toString();
                    } else {
                        str2 = str2 + "," + v40.get(0).toString();
                    }
                }
            }

            if (!str1.equalsIgnoreCase("")) {
                List list41 = em.createNativeQuery("select Distinct Substring(lm.ACC_NO,3,2) from cbs_loan_borrower_details lm,accountmaster am Where Auth <> 'Y' and "
                        + "substring(lm.ACC_NO,3,2) in(" + str1 + ") and lm.ACC_NO = am.ACNo and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                if (!list41.isEmpty()) {
                    String acNos = "";
                    for (int o = 0; o < list41.size(); o++) {
                        Vector v41 = (Vector) list41.get(o);
                        acNos = acNos + v41.get(0).toString() + " ";
                    }
                    list.add("Authorization pending for loan MIS details. " + acNos);
                }
            }

            if (!str2.equalsIgnoreCase("")) {
                List list42 = em.createNativeQuery("select Distinct Substring(ls.Acno,3,2) from loansecurity ls,accountmaster am Where Auth <> 'Y' and "
                        + "substring(ls.acno,3,2) in(" + str2 + ") and ls.AcNo = am.ACNo and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                if (!list42.isEmpty()) {
                    String acNos = "";
                    for (int p = 0; p < list42.size(); p++) {
                        Vector v42 = (Vector) list42.get(p);
                        acNos = acNos + v42.get(0).toString() + " ";
                    }
                    list.add("Authorization pending for loan security. ");
                }
            }
        }

        List list43 = em.createNativeQuery("select customerid from cbs_customer_master_detail where auth='N' and primarybrcode='"
                + orgBrnCode + "'").getResultList();
        if (!list43.isEmpty()) {
            list.add("Customer Verification is not completed. ");
        }
        List list50 = em.createNativeQuery("select acno from td_payment_auth where auth='N' and substring(acno,1,2)='" + orgBrnCode + "' "
                + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.TD_AC + "'))").getResultList();
        if (!list50.isEmpty()) {
            list.add("Term Deposit Verification is not completed. ");
        }
        List list50_1 = em.createNativeQuery("select acno from td_payment_auth where auth='N' and substring(acno,1,2)='" + orgBrnCode + "' "
                + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))").getResultList();
        if (!list50_1.isEmpty()) {
            list.add("Recurring Deposit Verification is not completed. ");
        }
        List list52 = em.createNativeQuery("select acno from npa_recon where auth='N' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
        if (!list52.isEmpty()) {
            list.add("NPA Transaction Verification is not completed.");
        }
        List list51 = em.createNativeQuery("select acno from td_vouchmst_auth where (auth='N' or auth='') and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
        if (!list51.isEmpty()) {
            list.add("New Receipt Creation Verification is not completed. ");
        }
        List list44 = em.createNativeQuery("select alphacode from branchmaster where brncode =" + Integer.parseInt(orgBrnCode)).getResultList();
        if (list44.isEmpty()) {
            throw new ApplicationException("Problem in getting Alpha Code of this branch");
        }

        Vector vect = (Vector) list44.get(0);
        String alphaCode = vect.get(0).toString();
        if (alphaCode.equals("HO")) {
            List list45 = em.createNativeQuery("select regfoliono from share_holder where authflag='N'").getResultList();
            if (!list45.isEmpty()) {
                list.add("Share Account open Verification is pending.");
            }
            String folioNo = orgBrnCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001";
            List list46 = em.createNativeQuery("select shareno from share_capital_issue where auth='N' and foliono ='" + folioNo + "'").getResultList();
            if (!list46.isEmpty()) {
                list.add("Share Issue Authorization is pending.");
            }

            List list47 = em.createNativeQuery("select certificateno from certificate_share where auth='N' and  status='A'").getResultList();
            if (!list47.isEmpty()) {
                list.add("Certificate Issue Authorization is pending.");
            }
        }
        List list48 = em.createNativeQuery("select * from neft_ow_details where dt='" + date + "' and (auth ='N' or auth is null) and orgbrnid='"
                + orgBrnCode + "' and status='P'").getResultList();
        if (!list48.isEmpty()) {
            list.add("Authorization is not completed for Outward Transaction.");
        }
        list48 = em.createNativeQuery("select * from bill_lost where branchcode='" + orgBrnCode + "' and auth = 'N'").getResultList();
        if (!list48.isEmpty()) {
            list.add("Authorization is not completed for Pay Order/DD.");
        }
        list48 = em.createNativeQuery("select * from mb_subscriber_tab where substring(acno,1,2)='" + orgBrnCode + "' and auth='N'").getResultList();
        if (!list48.isEmpty()) {
            list.add("Authorization is not completed for SMS Module.");
        }

//        List list49 = em.createNativeQuery("select * from cts_clg_in_entry A WHERE date_format(A.DT,'%Y%m%d') = '" + date + "' AND A.STATUS IN ('1','3') "
//                + "AND CAST(A.DEST_BRANCH AS unsigned)=" + orgBrnCode + " AND SUBSTATUS<>'L'").getResultList();
//        if (!list49.isEmpty()) {
//            list.add("Authorization is not completed for CTS Inward Clearing.");
//        }
        List list49 = null;
        int ctsSponsor = ftsMethods.getCodeForReportName("CTS-SPONSOR");
        if (ctsSponsor == 2 || ctsSponsor == 3) {
            list49 = em.createNativeQuery("select * from cts_clg_in_entry A WHERE date_format(A.DT,'%Y%m%d') = '" + date + "' AND A.STATUS IN ('1','3') "
                    + "AND CAST(A.ORGN_BRANCH AS unsigned)=" + orgBrnCode + " AND SUBSTATUS<>'L'").getResultList();
        } else {
            list49 = em.createNativeQuery("select * from cts_clg_in_entry A WHERE date_format(A.DT,'%Y%m%d') = '" + date + "' AND A.STATUS IN ('1','3') "
                    + "AND CAST(A.DEST_BRANCH AS unsigned)=" + orgBrnCode + " AND SUBSTATUS<>'L'").getResultList();
        }
        if (!list49.isEmpty()) {
            list.add("Authorization is not completed for CTS Inward Clearing.");
        }

        list49 = em.createNativeQuery("select count(*) from cts_clg_in_entry where date_format(dt,'%Y%m%d') ='" + date + "' and dest_branch ='" + orgBrnCode + "' and "
                + " status in(1,2,3,4) and schedule_no=0").getResultList();
        if (!list49.isEmpty()) {
            Vector element = (Vector) list49.get(0);
            Integer totalChq = Integer.parseInt(element.get(0).toString());

            if (totalChq > 0) {
                list49 = em.createNativeQuery("select count(*) from recon_clg_d where date_format(dt,'%Y%m%d') ='" + date + "' and dest_brnid ='" + orgBrnCode + "' and "
                        + " details='IW CLG Completion' and trandesc=65").getResultList();
                if (list49.isEmpty()) {
                    list.add("Completion is Pending for CTS Inward Clearing.");
                }
            }
        }

        List list54 = em.createNativeQuery("select * from cbs_id_merge_auth WHERE auth = 'N' AND orgn_br_code = '" + orgBrnCode + "'").getResultList();
        if (!list54.isEmpty()) {
            list.add("Authorization is not completed for Id Merging");
        }

        list54 = em.createNativeQuery("select acno from pm_scheme_reg_details where auth = 'N' and "
                + "txn_br_code = '" + orgBrnCode + "'").getResultList();
        if (!list54.isEmpty()) {
            list.add("Authorization is not completed for Social Security Schemes.");
        }

        list54 = em.createNativeQuery("select acno from prizm_card_master where auth = 'N' and "
                + "substring(acno,1,2) = '" + orgBrnCode + "'").getResultList();
        if (!list54.isEmpty()) {
            list.add("Authorization is not completed for ATM registration.");
        }

        List list55 = em.createNativeQuery("select from_acno from standins_transmaster_auth where auth = 'N' and "
                + "org_brnid = '" + orgBrnCode + "'").getResultList();
        if (!list55.isEmpty()) {
            list.add("Authorization is not completed for Standing Instruction.");
        }
        //Addition for internet banking.
        list55 = em.createNativeQuery("select acno from ib_request where request_status='NEW' "
                + "and substring(acno,1,2)='" + orgBrnCode + "' and cbs_request_dt='" + date + "'").getResultList();
        if (!list55.isEmpty()) {
            list.add("Authorization is not completed for Internet Banking Request.");
        }

        List list56 = em.createNativeQuery("select auth from tds_docdetail where (auth Is Null or auth='N')and orgBrnid = '" + orgBrnCode + "'").getResultList();
        if (!list56.isEmpty()) {
            list.add("Authorization is not completed for Tds documents.");
        }
        List list57 = em.createNativeQuery("select auth from money_exchange_details where (auth Is Null or auth='N')and brCode = '" + orgBrnCode + "' and entrydate='" + date + "'").getResultList();
        if (!list57.isEmpty()) {
            list.add("Authorization is not completed for Money Exchange.");
        }
        String recMsg = ftsMethods.receiptNotCreated(date, orgBrnCode);
        if (!recMsg.equalsIgnoreCase("true")) {
            list.add(recMsg);
        }
        //------------
//        List list58 = em.createNativeQuery("select Header_MessageId from cpsms_batch_detail where Entry_date = '" + date + "' and Cbs_Status in ('01','06')").getResultList();
//        if (!list58.isEmpty()) {
//            list.add("Authorization is pending for CPSMS batch.");
//        }

        List list58 = em.createNativeQuery("select cb.Header_MessageId from cpsms_batch_detail cb ,cpsms_detail cd where cb.Entry_date = cd.Entry_date"
                + " and cb.CPSMS_Batch_No=cd.CPSMS_Batch_No and cb.Header_MessageId=cd.Header_MessageId "
                + " and cd.Account_Type='DR' and SUBSTRING(cd.C6021_Agency_Bank_Acno, 1, 2)='" + orgBrnCode + "'"
                + " and cb.Entry_date <= '" + date + "'and cb.Cbs_Status in ('01','06')").getResultList();
        if (!list58.isEmpty()) {
            list.add("Authorization is pending for CPSMS batch.");
        }

        List list59 = em.createNativeQuery("select * from investment_fdr_close_renew_auth where (auth Is Null or auth='N')and org_brnid = '" + orgBrnCode + "'").getResultList();
        if (!list59.isEmpty()) {
            list.add("Authorization is pending for FDR Closing.");
        }

        list58 = em.createNativeQuery("select acno from atm_card_master where substring(acno,1,2)='" + orgBrnCode + "' and "
                + "verify='N' and date_format(lastUpdateDate,'%Y%m%d')='" + date + "'").getResultList();
        if (!list58.isEmpty()) {
            list.add("Authorization is pending for ATM Card Verification.");
        }
        list58 = em.createNativeQuery("select Instcode,sno from chbookmaster_amtwise where  brncode = '" + orgBrnCode + "' AND auth = 'N' and  Dt='" + date + "' ORDER BY sno").getResultList();
        if (!list58.isEmpty()) {
            list.add("Authorization is pending for PO/DD/AD Book .");
        }

        List list60 = em.createNativeQuery("select * from cheque_purchase where auth = 'N' and substring(AccountNo,1,2)='" + orgBrnCode + "'").getResultList();
        if (!list60.isEmpty()) {
            list.add("Authorization is pending for Cheque Purchase .");
        }
        List list61 = em.createNativeQuery("select acno from recon_trf_d where auth='N' and org_brnid='" + orgBrnCode + "' and trandesc=77").getResultList();
        if (!list61.isEmpty()) {
            list.add("Authorization is not completed for back date transfer transactions");
        }

        List locSurAuth = em.createNativeQuery("select cast(cabno as unsigned),lockertype,lockerno,acno,enterBy from locker_surrender_auth where "
                + " brncode = '" + orgBrnCode + "' and auth ='N'").getResultList();
        if (!locSurAuth.isEmpty()) {
            list.add("Authorization is pending for Locker Surrender");
        }
        //imps
        List list53 = em.createNativeQuery("select Remitter_Acc_No from cbs_imps_ow_request where substring(Remitter_Acc_No,1,2)='" + orgBrnCode + "' and Request_Status='L' and cast(dt as date)='" + date + "'").getResultList();
        if (!list53.isEmpty()) {
            list.add("Authorization is pending for imps request");
        }
//        List list65 =em.createNativeQuery("select MndtId from mms_upload_xml_detail where Upload_Date='"+date+"' and Mandate_Status='' and Accept=''").getResultList();
//            if(!list65.isEmpty()){
//                list.add("Mms mandate verification is pending");
//            }
        List bgList = em.createNativeQuery("select * from cbs_bank_guarantee_details where auth = 'N' and action  in ('I','V','R','C') and date_format(EntryDt,'%Y%m%d') = '" + date + "' and brncode='" + orgBrnCode + "'").getResultList();
        if (!bgList.isEmpty()) {
            list.add("Authorization is pending for Bank Guarantee");
        }

        //------------
        return list;
    }

    public List selectFromBankDays(String orgBrnCode) {
        List list = em.createNativeQuery("SELECT cast(date as datetime) FROM bankdays WHERE DayEndFlag='N' AND brncode = '" + orgBrnCode + "'").getResultList();
        return list;
    }
    /*
     * End of Authorization Check
     */

    /*
     * Start of Signature Authorization
     */
    public List getUnauthorizedAcctNo(String orgBrnCode, String imgType) {
        List listForUnAuthoAcc = new ArrayList();
        if (imgType.equals("S")) {
            listForUnAuthoAcc = em.createNativeQuery("select NewAcNo,SrNo,EnterBy,image,'Signature' from cbs_cust_image_detail where auth='N' and substring(NewAcNo,1,2)='"
                    + orgBrnCode + "'").getResultList();
        } else {
            listForUnAuthoAcc = em.createNativeQuery("select NewAcNo,SrNo,EnterBy,substring(image,8,2),REF_DESC from cbs_cust_image_detail ci, cbs_customer_master_detail cm,"
                    + "cbs_ref_rec_type cr where cm.customerid =ci.newacno and ci.auth='N' and cm.PrimaryBrCode = '" + orgBrnCode + "' and cr.REF_REC_NO ='362' "
                    + "and REF_CODE = substring(image,8,2)").getResultList();
        }
        return listForUnAuthoAcc;
    }

    public List getDetailsOfScannedPerson(String accountNo) throws ApplicationException {
        try {
            if (!accountNo.substring(0, 4).equals(CbsConstant.SHARE_AC)) {
                String acNat = ftsMethods.getAccountNature(accountNo);
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    return em.createNativeQuery("select a.custname,a.OpeningDt,a.IntroAccno,ifnull(a.Instruction,'') as Instruction,b.enterby from "
                            + "td_accountmaster a,cbs_cust_image_detail b where a.acno=b.NewAcNo and a.acno='" + accountNo + "'").getResultList();
                } else {
                    return em.createNativeQuery("select a.custname,a.OpeningDt,a.IntroAccno,ifnull(a.Instruction,'') as Instruction,b.enterby from "
                            + "accountmaster a,cbs_cust_image_detail b where a.acno=b.NewAcNo and a.acno='" + accountNo + "'").getResultList();
                }
            } else {
                return em.createNativeQuery("select concat(a.fname,' ',a.lname) ,a.regdate,'','' as Instruction,b.enterby from "
                        + "share_holder a,cbs_cust_image_detail b where a.regfoliono=b.NewAcNo and a.regfoliono='" + accountNo + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCustomerDetails(String custId, String imgCode) throws ApplicationException {
        imgCode = "client_" + imgCode;
        return em.createNativeQuery("select custfullname,b.enterby from cbs_customer_master_detail a,cbs_cust_image_detail b   where newacno = customerid and "
                + "customerid='" + custId + "' and b.image='" + imgCode + "'").getResultList();
    }

    @Override
    public String signAuthorization(String accountNo, String serialNo, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query updateAuth = em.createNativeQuery("update cbs_cust_image_detail set Auth='Y',AuthBy='" + userName + "' where NewAcNo='" + accountNo + "' and SrNo=" + Integer.parseInt(serialNo) + "");
            int updateResult = updateAuth.executeUpdate();
            if (updateResult <= 0) {
                throw new ApplicationException("Authorization Failed.");
            }
            ut.commit();
            return "true";

        } catch (NotSupportedException | SystemException | NumberFormatException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
            throw new ApplicationException(e);
        }
    }

    public String ckycImgAuthorization(String accountNo, String serialNo, String imgCode, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            imgCode = "client_" + imgCode;
            Query updateAuth = em.createNativeQuery("update cbs_cust_image_detail set Auth='Y',AuthBy='" + userName + "' where NewAcNo='" + accountNo
                    + "' and SrNo=" + Integer.parseInt(serialNo) + " and image = '" + imgCode + "'");
            int updateResult = updateAuth.executeUpdate();
            if (updateResult <= 0) {
                throw new ApplicationException("Authorization Failed.");
            }
            ut.commit();
            return "true";

        } catch (NotSupportedException | SystemException | NumberFormatException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
            throw new ApplicationException(e);
        }
    }

    public String getSignatureDetails(String acno) {
        List signature = em.createNativeQuery("select image from cbs_cust_image_detail where newacno='" + acno + "' and auth='N'").getResultList();
        if (!signature.isEmpty()) {
            Vector img = (Vector) signature.get(0);
            String image = img.get(0).toString();
            return image;
        } else {
            return "Signature not found";
        }
    }

    public String getMergSignatureDetails(String acno) {
        List signature = em.createNativeQuery("select image from cbs_cust_image_detail where newacno='" + acno + "'").getResultList();
        if (!signature.isEmpty()) {
            Vector img = (Vector) signature.get(0);
            String image = img.get(0).toString();
            return image;
        } else {
            return "Signature not found";
        }
    }

    public String deleteUnauthSign(String newAccNo, String deletedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insert1 = em.createNativeQuery("insert into cbs_cust_image_detail_delete(NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,DeletedBy,scanned_date) "
                    + "select NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,'" + deletedBy + "',scanned_date from cbs_cust_image_detail where NewAcNo='" + newAccNo + "'");
            int result1 = insert1.executeUpdate();
            if (result1 <= 0) {
                throw new ApplicationException("System Error Occured In Deletion");
            }
            Query insert2 = em.createNativeQuery("delete from cbs_cust_image_detail where NewAcNo='" + newAccNo + "'");
            int result2 = insert2.executeUpdate();
            if (result2 <= 0) {
                throw new ApplicationException("System Error Occured In Deletion");
            }
            ut.commit();
            return "True";
        } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String deleteUnauthCkycImg(String newAccNo, String deletedBy, String imgCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            imgCode = "client_" + imgCode;
            Query insert1 = em.createNativeQuery("insert into cbs_cust_image_detail_delete(NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,DeletedBy,scanned_date) "
                    + "select NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,'" + deletedBy + "',scanned_date from cbs_cust_image_detail where NewAcNo='" + newAccNo + "'");
            int result1 = insert1.executeUpdate();
            if (result1 <= 0) {
                throw new ApplicationException("System Error Occured In Deletion");
            }
            Query insert2 = em.createNativeQuery("delete from cbs_cust_image_detail where NewAcNo='" + newAccNo + "' and image='" + imgCode + "'");
            int result2 = insert2.executeUpdate();
            if (result2 <= 0) {
                throw new ApplicationException("System Error Occured In Deletion");
            }
            ut.commit();
            return "True";
        } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }
    /*
     * End of Signature Authorization
     */

    /*
     * Start of IBCOBC Authorization
     */
    public List ibcObcOnLoad(String billType, String brCode) throws ApplicationException {
        List checkList = null;
        try {
            if (billType.equalsIgnoreCase("OBC")) {
                checkList = em.createNativeQuery("select billtype,bill.acno,instno,instamount,date_format(instdt,'%d/%m/%Y'),enterby,auth,bankname,bankaddr,coll_bankname, coll_bankaddr from bill_obcbooking bill,accountmaster am where (auth='N' or auth is null) and bill.Acno = am.ACNo and am.CurBrCode = '" + brCode + "' order by BillType").getResultList();
            } else if (billType.equalsIgnoreCase("IBC")) {
                checkList = em.createNativeQuery("select billtype,bill.acno,instno,instamount,date_format(instdt,'%d/%m/%Y'),enterby,auth,bankname,bankaddr from bill_ibcbooking bill,accountmaster am where (auth='N' or auth is null) and bill.Acno = am.ACNo and am.CurBrCode = '" + brCode + "' order by BillType").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public List setPartyName(String billType, String acno) throws ApplicationException {

        try {
            List checkList2 = null;
            if (billType.equalsIgnoreCase("OBC")) {
                String acctNat = ftsMethods.getAccountNature(acno);
                if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || (acctNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    checkList2 = em.createNativeQuery("Select CustName from td_accountmaster where acno='" + acno + "'").getResultList();

                } else if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    checkList2 = em.createNativeQuery("Select AcName from GLTable where acno='" + acno + "'").getResultList();

                } else {
                    checkList2 = em.createNativeQuery("Select CustName from accountmaster where acno='" + acno + "'").getResultList();

                }
            } else if (billType.equalsIgnoreCase("IBC")) {
                String acctNat = ftsMethods.getAccountNature(acno);
                if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || (acctNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    checkList2 = em.createNativeQuery("Select CustName from td_accountmaster where acno='" + acno + "'").getResultList();

                } else if (acctNat.equalsIgnoreCase("PO")) {
                    checkList2 = em.createNativeQuery("Select AcName from gltable where acno='" + acno + "'").getResultList();

                } else {
                    checkList2 = em.createNativeQuery("Select CustName from accountmaster where acno='" + acno + "'").getResultList();

                }
            }

            return checkList2;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String ibcObcEntryDelete(String user, String bills, String bilType, String acno, String instno,
            double instamount, String instdt, String enterby, String auth, String bankname,
            String bankaddr, String coll_bankname, String coll_bankaddr, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = null;
            String tempbd = null;
//            if (user.equalsIgnoreCase(enterby)) {
//                ut.rollback();
//                return "You Cannot Delete Your Own Entry";
//            }
            List checkList2 = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + brCode + "'").getResultList();
            if (checkList2.size() == 0) {
                ut.rollback();
                return "Date Does Not Exists !!!.";
            }
            Vector secLst2 = (Vector) checkList2.get(0);
            tempbd = secLst2.get(0).toString();

            if (bills.equalsIgnoreCase("OBC")) {
                Query insertQuery = em.createNativeQuery("insert into deletetrans(acno,dramt,enterby,deletedby,deletedt,details,trantime)"
                        + "values (" + "'" + acno + "'" + "," + instamount + "," + "'" + enterby + "'" + "," + "'" + user + "'" + "," + "'" + tempbd + "'" + "," + "'" + bills + "(" + bilType + ")Deleted'" + ",CURRENT_TIMESTAMP())");
                int var = insertQuery.executeUpdate();
                Query deleteQuery = em.createNativeQuery("delete  bill_obcbooking from bill_obcbooking bill inner join accountmaster am on bill.Acno = am.ACNo and instno='" + instno + "' and coll_bankname='" + coll_bankname + "' and coll_bankaddr='" + coll_bankaddr + "' and am.CurBrCode = '" + brCode + "'");
                int var1 = deleteQuery.executeUpdate();
                if ((var > 0) && (var1 > 0)) {
                    ut.commit();
                    return "Data Deleted Succesfully";
                } else {
                    ut.rollback();
                    return "Data is not inserted in DeleteTrans or not deleted from bill_obcbooking";
                }
            } else if (bills.equalsIgnoreCase("IBC")) {
                Query insertQuery = em.createNativeQuery("insert into deletetrans(acno,dramt,enterby,deletedby,deletedt,details,trantime)"
                        + "values (" + "'" + acno + "'" + "," + instamount + "," + "'" + enterby + "'" + "," + "'" + user + "'" + "," + "'" + tempbd + "'" + "," + "'" + bills + "(" + bilType + ")Deleted'" + ",CURRENT_TIMESTAMP())");
                int var = insertQuery.executeUpdate();
                Query deleteQuery = em.createNativeQuery("delete bill_Ibcbooking from bill_Ibcbooking bill inner join accountmaster am on bill.Acno = am.ACNo and instno='" + instno + "' and instamount='" + instamount + "' and bill.acno='" + acno + "' and am.CurBrCode = '" + brCode + "'");
                int var1 = deleteQuery.executeUpdate();
                if ((var > 0) && (var1 > 0)) {
                    ut.commit();
                    return "Data Deleted Succesfully";
                } else {
                    ut.rollback();
                    return "Data is not inserted in DeleteTrans or not deleted from bill_Ibcbooking";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    public String ibcObcAuth(String BRCODE, String user, String enterby, String bills, String bilType,
            String acno, String instno, double instamount, String Year, String instrDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
//            ut.begin();
//            List checkList = null;
//            String GLHO = "";
//            String GLBPIntRec = "";
//            String GLBCComm = "";
//            String GLBillCol = "";
//            String GLBillPay = "";
//            String GLDraftPay = "";
//            String GLCASEPAY = "";
//            String GLHOPAY = "";
//            String GLBillLodg = "";
//            String GLChequeRet = "";
//            String GLSundry = "";
//            String BnkGuarCr = "";
//            String BnkGuarDr = "";
//            String GLSundryName = "";
//            String GLService = "";
//            String GLLcComm = "";
//            String GLBgComm = "";
//
//            checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
//            if (checkList.isEmpty()) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//                GLDraftPay = BRCODE + GlConstant.GLDraftPay;
//                GLCASEPAY = BRCODE + GlConstant.GLCASEPAY;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } else {
//                checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
//                if (checkList.isEmpty()) {
//                    GLHO = BRCODE + GlConstant.GLHO;
//                    GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//                    GLBCComm = BRCODE + GlConstant.GLBCComm;
//                    GLBillPay = BRCODE + GlConstant.GLBillPay;
//                    GLDraftPay = BRCODE + GlConstant.GLDraftPay;
//                    GLCASEPAY = BRCODE + GlConstant.GLCASEPAY;
//                    GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                    GLBillCol = BRCODE + GlConstant.GLBillCol;
//                    GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//                    GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                    GLSundry = BRCODE + GlConstant.GLSundry;
//                    BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                    BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                    GLService = BRCODE + GlConstant.GLService;
//                    GLLcComm = BRCODE + GlConstant.GLLcComm;
//                    GLBgComm = BRCODE + GlConstant.GLBgComm;
//                } else {
//                    Vector secLst1 = (Vector) checkList.get(0);
//                    GLBPIntRec = secLst1.get(1).toString();
//                    GLBillPay = secLst1.get(3).toString();
//                    GLBillCol = secLst1.get(7).toString();
//                    GLBillLodg = secLst1.get(8).toString();
//                }
//            }
//
//            float SeqNum = 0.0f;
//            if (user.equalsIgnoreCase(enterby)) {
//                ut.rollback();
//                return "You Cannot Authorize Your Own Entry";
//            }
//            if ((bills.equalsIgnoreCase("IBC") && (bilType.equalsIgnoreCase("BP")))) {
//
//                List checkList1 = em.createNativeQuery("SELECT F_YEAR FROM yearend WHERE YEARENDFLAG = 'N' and brncode='" + BRCODE + "'").getResultList();
//                if (checkList1.isEmpty()) {
//                    ut.rollback();
//                    return "YEAR Does Not Exists !!!.";
//                }
//                Vector secLst1 = (Vector) checkList1.get(0);
//                String fyear = secLst1.get(0).toString();
//                List checkList2 = em.createNativeQuery("select coalesce(max(billno),0),(select coalesce(max(billno),0) from bill_ibcprocessed where ifnull(fyear,'" + Year + "')='" + Year + "' and billtype='BP' ) from bill_ibcbooking b,accountmaster am where ifnull(fyear,'" + Year + "')='" + Year + "' and billtype='BP' and b.Acno = am.ACNo and am.CurBrCode = '" + BRCODE + "'").getResultList();
//                if (checkList2.isEmpty()) {
//                    ut.rollback();
//                    return "BillControlNo Does Not Exists !!!.";
//                }
//                Vector secLst2 = (Vector) checkList2.get(0);
//                String billNo = secLst2.get(0).toString();
//                float var1 = Float.parseFloat(billNo);
//                String billNo1 = secLst2.get(1).toString();
//                float var2 = Float.parseFloat(billNo1);
//                if ((var1 - var2) > 0) {
//                    SeqNum = var1;
//                } else {
//                    SeqNum = var2;
//                }
//                SeqNum = SeqNum + 1;
//                Query UpdateQuery = em.createNativeQuery("Update bill_ibcbooking set Billno=" + SeqNum + ",FYear='" + Year + "',auth='Y',authby='" + user + "' where acno='" + acno + "' and Instno= '" + instno + "' and BillType= 'BP' and instAmount=" + instamount + "");
//                int var3 = UpdateQuery.executeUpdate();
//                if (var3 > 0) {
//                    ut.commit();
//                    return "Your IBC No is " + SeqNum + "/" + Year + "   Authorization is Over";
//                } else {
//                    ut.rollback();
//                    return "Authorization is not done";
//                }
//
//            }
//            if ((bills.equalsIgnoreCase("IBC") && (bilType.equalsIgnoreCase("BC")))) {
//
//                float dtrSNo = ftsMethods.getTrsNo();
//
//                List checkList2 = em.createNativeQuery("select coalesce(max(billno),0),(select coalesce(max(billno),0) from bill_ibcprocessed where ifnull(fyear,'" + Year + "')='" + Year + "'  and billtype='BC' ) from bill_ibcbooking b,accountmaster am where ifnull(fyear,'" + Year + "')='" + Year + "' and billtype='BC' and b.Acno = am.ACNo and am.CurBrCode = '" + BRCODE + "'").getResultList();
//                if (checkList2.isEmpty()) {
//                    ut.rollback();
//                    return "BillControlNo Does Not Exists !!!.";
//                }
//                Vector secLst2 = (Vector) checkList2.get(0);
//                String billNo = secLst2.get(0).toString();
//                float var1 = Float.parseFloat(billNo);
//                String billNo1 = secLst2.get(1).toString();
//                float var2 = Float.parseFloat(billNo1);
//                if ((var1 - var2) > 0) {
//                    SeqNum = var1;
//                } else {
//                    SeqNum = var2;
//                }
//                SeqNum = SeqNum + 1;
//                Query UpdateQuery = em.createNativeQuery("Update bill_ibcbooking set Billno=" + SeqNum + ",FYear='" + Year + "',auth='Y',authby='" + user + "' where acno='" + acno + "' and Instno= '" + instno + "' and BillType= 'BC' and instAmount=" + instamount + "");
//                int var3 = UpdateQuery.executeUpdate();
//                String CHK_MSG = null;
//                String MSG = null;
//                float TOKENNO = 0.0f;
//                List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
//                if (bank.isEmpty()) {
//                    ut.rollback();
//                    return "Date Does Not Exists !!!.";
//                }
//                Vector bankLst2 = (Vector) bank.get(0);
//                String tempbd = bankLst2.get(0).toString();
//
//                String Detail = "VCH : Cr. For " + bilType + " No." + SeqNum + "/" + Year;
//                String FTSMsg = "";
//                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillCol, 2, 0, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                    if (FTSMsg.contains("03Account No Does Not Exists !")) {
//                        ut.rollback();
//                        return "GL HEAD DOES NOT EXISTS FOR RESPECTIVE IBC A/C. NO. AUTHORIZATION !!!";
//                    } else {
//                        ut.rollback();
//                        return FTSMsg;
//                    }
//                }
//                Detail = "VCH : Dr. For " + bilType + " No." + SeqNum + "/" + Year;
//                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillLodg, 2, 1, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                    if (FTSMsg.contains("03Account No Does Not Exists !")) {
//                        ut.rollback();
//                        return "GL HEAD DOES NOT EXISTS FOR RESPECTIVE IBC A/C. NO. AUTHORIZATION !!!";
//                    } else {
//                        ut.rollback();
//                        return FTSMsg;
//                    }
//                }
//
//                if ((var3 > 0)) {
//                    ut.commit();
//                    return "Your IBC No is " + SeqNum + "/" + Year + "  And Transaction No is" + dtrSNo + "   Authorization is Over";
//                } else {
//                    ut.rollback();
//                    return "Authorization is not done";
//                }
//            }
//            if ((bills.equalsIgnoreCase("OBC") && (bilType.equalsIgnoreCase("BC")))) {
//
//                float dtrSNo = ftsMethods.getTrsNo();
//
//                List checkList2 = em.createNativeQuery("select coalesce(max(billno),0),(select coalesce(max(billno),0) from bill_obcprocessed where ifnull(fyear,'" + Year + "')='" + Year + "'  and billtype='BC' ) from bill_obcbooking b,accountmaster am where ifnull(fyear,'" + Year + "')='" + Year + "' and billtype='BC' and b.Acno = am.ACNo and am.CurBrCode = '" + BRCODE + "'").getResultList();
//                if (checkList2.isEmpty()) {
//                    ut.rollback();
//                    return "BillControlNo Does Not Exists !!!.";
//                }
//                Vector secLst2 = (Vector) checkList2.get(0);
//                String billNo = secLst2.get(0).toString();
//                float var1 = Float.parseFloat(billNo);
//                String billNo1 = secLst2.get(1).toString();
//                float var2 = Float.parseFloat(billNo1);
//                if ((var1 - var2) > 0) {
//                    SeqNum = var1;
//                } else {
//                    SeqNum = var2;
//                }
//                SeqNum = SeqNum + 1;
//                Query UpdateQuery = em.createNativeQuery("Update bill_obcbooking set Billno=" + SeqNum + ",FYear='" + Year + "',auth='Y',authby='" + user + "' where acno='" + acno + "' and Instno= '" + instno + "' and BillType= 'BC' and instAmount=" + instamount + "");
//                int var3 = UpdateQuery.executeUpdate();
//                String CHK_MSG = null;
//                String MSG = null;
//                float TOKENNO = 0.0f;
//                List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
//                if (bank.isEmpty()) {
//                    ut.rollback();
//                    return "Date Does Not Exists !!!.";
//                }
//                Vector bankLst2 = (Vector) bank.get(0);
//                String tempbd = bankLst2.get(0).toString();
//
//                String Detail = "VCH : Cr. For " + bilType + " No." + SeqNum + "/" + Year;
//                String FTSMsg = "";
//                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillCol, 2, 0, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                    if (FTSMsg.contains("03Account No Does Not Exists !")) {
//                        ut.rollback();
//                        return "GL HEAD DOES NOT EXISTS FOR RESPECTIVE OBC A/C. NO. AUTHORIZATION !!!";
//                    } else {
//                        ut.rollback();
//                        return FTSMsg;
//                    }
//                }
//
//                Detail = "VCH : Dr. For " + bilType + " No." + SeqNum + "/" + Year;
//                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillLodg, 2, 1, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                    if (FTSMsg.contains("03Account No Does Not Exists !")) {
//                        ut.rollback();
//                        return "GL HEAD DOES NOT EXISTS FOR RESPECTIVE OBC A/C. NO. AUTHORIZATION !!!";
//                    } else {
//                        ut.rollback();
//                        return FTSMsg;
//                    }
//                }
//
//                if ((var3 > 0)) {
//                    ut.commit();
//                    return "Your OBC No is " + SeqNum + "/" + Year + "  And Transaction No is" + dtrSNo + "   Authorization is Over";
//                } else {
//                    ut.rollback();
//                    return "Authorization is not done";
//                }
//            }
//            if ((bills.equalsIgnoreCase("OBC") && (bilType.equalsIgnoreCase("BP")))) {
//                float dtrSNo = ftsMethods.getTrsNo();
//
//                List checkList2 = em.createNativeQuery("select coalesce(max(billno),0),(select coalesce(max(billno),0) from bill_obcprocessed where ifnull(fyear,'" + Year + "')='" + Year + "'  and billtype='BP' ) from bill_obcbooking b,accountmaster am where ifnull(fyear,'" + Year + "')='" + Year + "' and billtype='BP' and b.Acno = am.ACNo and am.CurBrCode = '" + BRCODE + "'").getResultList();
//                if (checkList2.isEmpty()) {
//                    ut.rollback();
//                    return "BillControlNo Does Not Exists !!!.";
//                }
//                Vector secLst2 = (Vector) checkList2.get(0);
//                String billNo = secLst2.get(0).toString();
//                float var1 = Float.parseFloat(billNo);
//                String billNo1 = secLst2.get(1).toString();
//                float var2 = Float.parseFloat(billNo1);
//                if ((var1 - var2) > 0) {
//                    SeqNum = var1;
//                } else {
//                    SeqNum = var2;
//                }
//                SeqNum = SeqNum + 1;
//
//                Query UpdateQuery = em.createNativeQuery("Update bill_obcbooking set Billno=" + SeqNum + ",FYear='" + Year + "',auth='Y',authby='" + user + "' where acno='" + acno + "' and Instno= '" + instno + "' and BillType= 'BP' and instAmount=" + instamount + "");
//                int var3 = UpdateQuery.executeUpdate();
//
//                String CHK_MSG = null;
//                String MSG = null;
//                float TOKENNO = 0.0f;
//                List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
//                if (bank.isEmpty()) {
//                    ut.rollback();
//                    return "Date Does Not Exists !!!.";
//                }
//                Vector bankLst2 = (Vector) bank.get(0);
//                String tempbd = bankLst2.get(0).toString();
//
//                List ComPos2 = em.createNativeQuery("select (Comm + Postage) ComPos From bill_obcbooking where acno='" + acno + "' and Instno= '" + instno + "'").getResultList();
//                if (ComPos2.isEmpty()) {
//                    ut.rollback();
//                    return "Comm + Postage Does Not Exists !!!.";
//                }
//                Vector ComPosLst2 = (Vector) ComPos2.get(0);
//                String ComPos1 = ComPosLst2.get(0).toString();
//                double ComPos = Double.parseDouble(ComPos1);
//                String FTSMsg = "";
//
//                if (ComPos != 0) {
//                    String Detail = "VCH : Int. Recvd. For " + bilType + " No." + SeqNum + "/" + Year;
//                    FTSMsg = ftsMethods.ftsPosting43CBS(GLBPIntRec, 2, 0, ComPos, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                        if (FTSMsg.contains("03Account No Does Not Exists !")) {
//                            ut.rollback();
//                            return "GL HEAD DOES NOT EXISTS FOR RESPECTIVE OBC A/C. NO. AUTHORIZATION !!!";
//                        } else {
//                            ut.rollback();
//                            return FTSMsg;
//                        }
//                    }
//
//                }
//
//                String Detail = "VCH : " + bilType + " No." + SeqNum + "/" + Year;
//                double amt = instamount - ComPos;
//                FTSMsg = ftsMethods.ftsPosting43CBS(acno, 2, 0, amt, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                    ut.rollback();
//                    return FTSMsg;
//                }
//
//                Detail = "VCH : " + bilType + " No." + SeqNum + "/" + Year;
//                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillPay, 2, 1, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "Y", user, "A", 3, instno, instrDt, null, null, null, null, null, null, 0.0f, "N", "", "");
//                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
//                    if (FTSMsg.contains("03Account No Does Not Exists !")) {
//                        ut.rollback();
//                        return "GL HEAD DOES NOT EXISTS FOR RESPECTIVE OBC A/C. NO. AUTHORIZATION !!!";
//                    } else {
//                        ut.rollback();
//                        return FTSMsg;
//                    }
//                }
//                if ((var3 > 0)) {
//                    ut.commit();
//                    return "Your OBC No is " + SeqNum + "/" + Year + "  And Transaction No is" + dtrSNo + "   Authorization is Over";
//                } else {
//                    ut.rollback();
//                    return "Authorization is not done";
//                }
//            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    public List chBkTable(String orgnBrCode, String todayDate, String authType) throws ApplicationException {
        List resultlist = null;
        try {
            if (authType.equalsIgnoreCase("0")) {
                resultlist = em.createNativeQuery("select aa.acno,aa.Custname,aa.chbookno,aa.chnofrom,aa.chnoto,aa.issuedby,aa.leafs,"
                        + " aa.auth,aa.ChargeFlag,bb.stateCode, bb.brState  from "
                        + " (select a.acno,b.Custname,a.chbookno,a.chnofrom,a.chnoto,a.issuedby,a.leafs,"
                        + " a.auth,a.ChargeFlag From chbookmaster a ,accountmaster b where substring(a.acno,3,2) in "
                        + "(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.PAY_ORDER + "')) and a.auth ='N' and "
                        + "a.issuedt='" + todayDate + "' and b.CurBrCode = '" + orgnBrCode + "' and a.acno=b.acno) aa,"
                        + "(select ci.Acno as acno, ifnull(cm.mailStateCode,'') as stateCode, ifnull(br.State,'') as brState   "
                        + "from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + "where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + orgnBrCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + orgnBrCode + "')  bb "
                        + "where aa.acno = bb.acno order by aa.acno").getResultList();
            }
            if (authType.equalsIgnoreCase("1")) {
                resultlist = em.createNativeQuery("select aa.acno,aa.Acname,aa.chbookno,aa.chnofrom,aa.chnoto,aa.issuedby,aa.leafs,"
                        + "aa.auth,aa.ChargeFlag,bb.stateCode, bb.brState  from "
                        + "(select a.acno,b.Acname,a.chbookno,a.chnofrom,a.chnoto,a.issuedby,a.leafs,"
                        + "a.auth,a.ChargeFlag From chbookmaster a ,gltable b where substring(a.acno,3,2) in (select acctcode from "
                        + "accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.PAY_ORDER + "')) and a.auth ='N' and a.issuedt='" + todayDate + "' "
                        + "and substring(a.acno,1,2) =  '" + orgnBrCode + "' and a.acno=b.acno) aa, "
                        + "(select ci.Acno as acno, ifnull(cm.mailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                        + "from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + "where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + orgnBrCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + orgnBrCode + "')  bb "
                        + "where aa.acno = bb.acno order by aa.acno").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultlist;
    }

//    public double taxAmount(double amt, String type) throws ApplicationException {
//        try {
//            List resultlist = em.createNativeQuery("select minAmt,maxamt from taxmaster where type='" + type + "' and"
//                    + " ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' "
//                    + "and ApplicableFlag='Y')").getResultList();
//            if (resultlist.size() <= 0) {
//                throw new ApplicationException("No Data in TaxMaster");
//            }
//            Vector resultVect = (Vector) resultlist.get(0);
//            double minAmt = Double.parseDouble(resultVect.get(0).toString());
//            double maxAmt = Double.parseDouble(resultVect.get(1).toString());
//
//            List resultlistTaxMaster = em.createNativeQuery("Select ('" + amt + "'* ROT)/100  from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y')").getResultList();
//            if (resultlistTaxMaster.size() <= 0) {
//                throw new ApplicationException("No Data in TaxMaster");
//            }
//            resultVect = (Vector) resultlistTaxMaster.get(0);
//            double balance = Double.parseDouble(resultVect.get(0).toString());
//
//            if (balance < minAmt) {
//                balance = minAmt;
//            } else if (balance > maxAmt) {
//                balance = maxAmt;
//            }
//            return balance;
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
//    public double findTax(double commAmt) throws ApplicationException {
//        String currentDate = "";
//        Integer roundNo = 0;
//        String appType = "";
//        try {
//            List resultDate = em.createNativeQuery("SELECT date_format(curdate(),'%Y%m%d')").getResultList();
//            if (resultDate.size() <= 0) {
//            } else {
//                Vector resultVect = (Vector) resultDate.get(0);
//                currentDate = resultVect.get(0).toString();
//            }
//            List resultTaxMaster = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= date_format(curdate(),'%Y%m%d') limit 1").getResultList();
//            if (resultTaxMaster.size() <= 0) {
//                throw new ApplicationException("Tax not defined");
//            }
//            Vector resultVect = (Vector) resultTaxMaster.get(0);
//            roundNo = Integer.parseInt(resultVect.get(0).toString());
//
//            List taxApplicableROTList = fnTaxApplicableROT(currentDate);
//            if (taxApplicableROTList.size() <= 0) {
//                throw new ApplicationException("Rate Of Tax not Found");
//            }
//            resultVect = (Vector) taxApplicableROTList.get(0);
//            appType = resultVect.get(0).toString();
//            return CbsUtil.round(taxAmount(commAmt, appType), roundNo);
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
//    public List fnTaxApplicableROT(String appDT) throws ApplicationException {
//        List resultList = null;
//        try {
//            resultList = em.createNativeQuery("select TYPE,ROT,ROTApplyOn,glhead from taxmaster where ApplicableDt<='" + appDT + "' and applicableFlag='Y' and Auth='Y'").getResultList();
//            if (resultList.isEmpty()) {
//                throw new ApplicationException("Data does not exist in TaxMaster");
//            }
//            return resultList;
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//    }
//    public String cbsReconBalanUpdation(String acno, double amount) throws ApplicationException {
//        double balance = 0d;
//        try {
//            if (ftsMethods.getAccountNature(acno).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                List selectQuery = em.createNativeQuery("select acno,dt,balance from ca_reconbalan where acno='" + acno + "'").getResultList();
//                if (selectQuery.isEmpty()) {
//                    Integer caReconBalanList = em.createNativeQuery("insert into ca_reconbalan(Acno,dt,Balance) values ('" + acno + "',CURRENT_TIMESTAMP," + amount + ")").executeUpdate();
//                    if (caReconBalanList <= 0) {
//                        throw new ApplicationException("Data is does get not Inserted in CA_ReconBalan");
//                    }
//                } else {
//                    Vector resultVect = (Vector) selectQuery.get(0);
//                    balance = Double.parseDouble(resultVect.get(2).toString()) + amount;
//
//                    Integer reconBalanList = em.createNativeQuery("UPDATE ca_reconbalan SET dt = CURRENT_TIMESTAMP,Balance =" + balance + " where acno='" + acno + "'").executeUpdate();
//                    if (reconBalanList <= 0) {
//                        throw new ApplicationException("Data is does get not Updated in CA_ReconBalan");
//                    }
//                }
//            } else {
//                List selectQuery = em.createNativeQuery("select acno,dt,balance from reconbalan where acno='" + acno + "'").getResultList();
//                if (selectQuery.isEmpty()) {
//                    Integer reconBalanList = em.createNativeQuery("insert into reconbalan(Acno,dt,Balance)"
//                            + "values ('" + acno + "',CURRENT_TIMESTAMP," + amount + ")").executeUpdate();
//                    if (reconBalanList <= 0) {
//                        throw new ApplicationException("Data is does get not Inserted in ReconBalan");
//                    }
//                } else {
//                    Vector resultVect = (Vector) selectQuery.get(0);
//                    balance = Double.parseDouble(resultVect.get(2).toString()) + amount;
//
//                    Integer reconBalanList = em.createNativeQuery("UPDATE reconbalan SET dt = CURRENT_TIMESTAMP,Balance =" + balance + " where acno='" + acno + "'").executeUpdate();
//                    if (reconBalanList <= 0) {
//                        throw new ApplicationException("Data is does get not Updated in ReconBalan");
//                    }
//                }
//            }
//            return "True";
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
    public String authCheckIssue(ChBookAuthorization item, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            ut.begin();
            double commAmt = 0d;
            float tmpTrsno = 0f;
            int chqNoFrom = item.getChqNoFrom();
            int chqNoTo = item.getChqNoTo();
            String mainDetails = null;
            String brCode = ftsMethods.getCurrentBrnCode(item.getAcno());
            String acNature = ftsMethods.getAccountNature(item.getAcno());
            String custState = item.getCustState();
            String branchState = item.getBranchState();

            List chBookMasterList1 = em.createNativeQuery("SELECT * FROM chbookmaster WHERE acno='" + item.getAcno() + "' AND auth='N' AND "
                    + "chnofrom='" + chqNoFrom + "' AND chnoto='" + chqNoTo + "'  AND issuedt= date_format(curdate(),'%Y%m%d')").getResultList();

            if (chBookMasterList1.isEmpty()) {
                throw new ApplicationException("There is no data for authorization");
            }
            int noOfChq = 0;
            noOfChq = item.getChqNoTo() - item.getChqNoFrom();
            if (noOfChq > 0) {
                noOfChq = noOfChq + 1;
            }
            //ACNATURE (SB,CS,PS)
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                Integer chbookmaster = em.createNativeQuery("UPDATE chbookmaster SET Auth = 'Y',authby='" + userName + "' WHERE "
                        + "acno='" + item.getAcno() + "' AND Auth='N' AND ChNoFrom = '" + chqNoFrom + "' AND"
                        + " ChNoTo = '" + chqNoTo + "'").executeUpdate();
                if (chbookmaster <= 0) {
                    throw new ApplicationException("Data is does get not Inserted in chbookmaster");
                }

                while (chqNoFrom <= chqNoTo) {
                    Integer chBookSB = em.createNativeQuery("INSERT INTO  chbook_sb (acno,chqno,statusflag,"
                            + "issuedt,authby,chqsrno) values('" + item.getAcno() + "','" + chqNoFrom + "','F', date_format(curdate(),'%Y%m%d'),'" + userName + "','" + item.getChqSeries() + "')").executeUpdate();
                    if (chBookSB <= 0) {
                        throw new ApplicationException("Data does not inserted in ChBook_SB");
                    }
                    chqNoFrom = chqNoFrom + 1;
                }
            }
            /**
             * * *********************************** ACNATURE (CA) **
             * ****************************************
             */
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Integer chbookmasterList = em.createNativeQuery("UPDATE chbookmaster SET Auth = 'Y' ,authby='" + userName + "' WHERE "
                        + "acno='" + item.getAcno() + "' AND Auth='N' AND ChNoFrom = '" + chqNoFrom + "' AND"
                        + " ChNoTo = '" + chqNoTo + "'").executeUpdate();
                if (chbookmasterList <= 0) {
                    throw new ApplicationException("Data is does get not Inserted in chbookmaster");
                }
                while (chqNoFrom <= chqNoTo) {
                    Integer ChBookCAList = em.createNativeQuery("INSERT INTO  chbook_ca (acno,chqno,statusflag,"
                            + "issuedt,authby,chqsrno) values('" + item.getAcno() + "','" + chqNoFrom + "','F',date_format(curdate(),'%Y%m%d'),'" + userName + "','" + item.getChqSeries() + "')").executeUpdate();

                    if (ChBookCAList <= 0) {
                        throw new ApplicationException("Data is does get not Inserted in ChBook_CA");
                    }
                    chqNoFrom = chqNoFrom + 1;
                }
            }

            //Commented By Dinesh On 03/01/2015.
//            if (acNature.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
//                Integer chbookmasterList = em.createNativeQuery("UPDATE chbookmaster SET Auth = 'Y' WHERE "
//                        + "acno='" + item.getAcno() + "' AND Auth='N' AND ChNoFrom = '" + chqNoFrom + "' AND"
//                        + " ChNoTo = '" + chqNoTo + "'").executeUpdate();
//                if (chbookmasterList <= 0) {
//                    throw new ApplicationException("Data is does get not Inserted in chbookmaster");
//                }
//                while (chqNoFrom <= chqNoTo) {
//                    Integer ChBookPOList = em.createNativeQuery("INSERT INTO  chbook_po(acno,chqno,statusflag,"
//                            + "issuedt,authby) values('" + item.getAcno() + "','" + chqNoFrom + "','F',date_format(curdate(),'%Y%m%d'),'" + userName + "')").executeUpdate();
//                    if (ChBookPOList <= 0) {
//                        throw new ApplicationException("Data is does get not Inserted in ChBook_PO");
//                    }
//                    chqNoFrom = chqNoFrom + 1;
//                }
//            }
            if (ftsMethods.getCodeForReportName("CHEQUE-BOOK-PRINTING-FILE") == 1) {
//                List txnCodeList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_rec_no='015' and ref_desc='" + acNature + "'").getResultList();
                List txnCodeList = null;
                int code = ftsMethods.getCodeForReportName("CHQ-ISSUE-ON");
                if (code == 1) {
                    txnCodeList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='017' and "
                            + "ref_code='" + acNature + "'").getResultList();
                } else if (code == 0) {
                    txnCodeList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='017' and "
                            + "ref_code='" + item.getAcno().substring(2, 4) + "'").getResultList();
                }

                if (txnCodeList.isEmpty()) {
                    throw new ApplicationException("Please define the Transaction Code");
                }

                Vector txnVect = (Vector) txnCodeList.get(0);
                String txnCode = txnVect.get(0).toString();

                List rsList = em.createNativeQuery("select san,atpar,chbooktype,leafs,chbookno from chbookmaster WHERE acno='" + item.getAcno() + "' AND ChNoFrom = '"
                        + item.getChqNoFrom() + "' AND ChNoTo = '" + item.getChqNoTo() + "'").getResultList();

                if (rsList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }

                Vector rsVect = (Vector) rsList.get(0);

                String san = rsVect.get(0).toString();
                if (ftsMethods.getCodeForReportName("CSN-OR-SAN") == 1) {
                    san = rsVect.get(4).toString();
                }

                String atPar = rsVect.get(1).toString();
                String chBookType = rsVect.get(2).toString();
                int bookSize = Integer.parseInt(rsVect.get(3).toString());
                List booksizelist = em.createNativeQuery("select name,code from cbs_parameterinfo where name='CHQ-BOOK-SIZE'").getResultList();
                Vector vtr = (Vector) booksizelist.get(0);
                String sizecode = vtr.get(1).toString();
                String[] codedata = sizecode.split(",");
                if (!(Arrays.asList(codedata).contains(Integer.toString(bookSize)))) {
                    throw new Exception("Cheque Book size not valid");
                }

//                if (!(bookSize == 15 || bookSize == 30 || bookSize == 45 || bookSize == 60)) {
//                    throw new ApplicationException("Cheque Book size must be 15,30,45,60");
//                }
                List srNoList = em.createNativeQuery("select ifnull(max(srNo),0)+1 from chbook_file_details").getResultList();
                Vector srNoVect = (Vector) srNoList.get(0);
                long srNo = Long.parseLong(srNoVect.get(0).toString());

                String query = "INSERT INTO chbook_file_details (acno, san, txn_code, at_par, chbook_type, book_size, start_chq_no, end_chq_no, "
                        + "entery_by, enter_date, file_gen_by, file_name,srNo) VALUES ('" + item.getAcno() + "', '" + san + "', '" + txnCode
                        + "', '" + atPar + "', '" + chBookType + "'," + bookSize + ", '" + item.getChqNoFrom() + "', '" + item.getChqNoTo() + "', '"
                        + userName + "',now() , '',''," + srNo + ")";

                int rs = em.createNativeQuery(query).executeUpdate();

                if (rs <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
            }
            if (ftsMethods.existInParameterInfoReport("CHEQUE_BOOK_ISSUE_CHARGE")) {
                double stockCommAmt = 0d;
                double chequeCommAmt = 0d;
                // double tax = 0d;
                double taxAmount = 0d;
                double totTaxAmount = 0d;
                if (item.getChargeFlag().equalsIgnoreCase("Y")) {
                    // Check Service Tax module on/off
                    List parameterinfoList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
                    if (parameterinfoList.isEmpty()) {
                        throw new ApplicationException("Data does not exist in parameterinfo_report for tax module.");
                    }
                    Vector sevVector = (Vector) parameterinfoList.get(0);
                    String staxModuleActive = sevVector.get(0).toString();
                    // Check Organization Type(STAFF/OTHER)
                    List staffList = em.createNativeQuery("select  OrgnCode from accountmaster where acno = '" + item.getAcno() + "'").getResultList();
                    Vector staffVector = (Vector) staffList.get(0);
                    String orgType = staffVector.get(0).toString();
                    //Cheque Book Charges Parameter (Cheque Book Charges (PL-06302788) as per document)
                    List parameterList = em.createNativeQuery("SELECT charges,glheadmisc FROM parameterinfo_miscincome WHERE "
                            + "acctCode=SUBSTRING('" + item.getAcno() + "',3,2) AND Purpose='Cheque Book Charges'").getResultList();
                    if (parameterList.isEmpty()) {
                        throw new ApplicationException("There is no GLHead for cheque charge posting");
                    }
                    Vector parameterVect = (Vector) parameterList.get(0);
                    float tmpCharges = 0f;
                    String glHead = "";
                    if (parameterVect.get(0) != null) {
                        tmpCharges = Float.parseFloat(parameterVect.get(0).toString());
                    }
                    if (parameterVect.get(1) != null) {
                        glHead = brCode + parameterVect.get(1).toString() + "01";
                    }

                    //Stock of Cheque Book Charge Parameter
                    List parameterStockList = em.createNativeQuery("SELECT charges,glheadmisc FROM parameterinfo_miscincome WHERE "
                            + "acctCode=SUBSTRING('" + item.getAcno() + "',3,2) AND Purpose='Stock of Cheque Book'").getResultList();
                    if (parameterStockList.isEmpty()) {
                        throw new ApplicationException("There is no GLHead for cheque charge posting");
                    }
                    Vector parameterstVect = (Vector) parameterStockList.get(0);
                    float tmpStCharges = 0f;
                    String glHeadSt = "";
                    if (parameterstVect.get(0) != null) {
                        tmpStCharges = Float.parseFloat(parameterstVect.get(0).toString());
                    }
                    if (parameterstVect.get(1) != null) {
                        glHeadSt = brCode + parameterstVect.get(1).toString() + "01";
                    }

                    int retiy = 1;
                    tmpTrsno = ftsMethods.getTrsNo();
                    if (orgType.equalsIgnoreCase("16")) {
                        //Stock of Cheque Book Charge
                        stockCommAmt = tmpStCharges * noOfChq;
                        if (stockCommAmt > 0) {
                            List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + glHeadSt + "'").getResultList();
                            String glHeadName = "NOT AVAIL";
                            if (glTableList.size() > 0) {
                                Vector findTaxVect = (Vector) glTableList.get(0);
                                glHeadName = findTaxVect.get(0).toString();
                            }
                            float recon = ftsMethods.getRecNo();

                            Integer reconTrf = em.createNativeQuery("INSERT INTO recon_trf_d(Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                    + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode)  VALUES('"
                                    + glHeadSt + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + Math.round(stockCommAmt) + ",'Stock of Cheque Book Charges for " + item.getAcno() + "',2,0," + recon + "," + tmpTrsno + ",1,3,8,'"
                                    + userName + "','" + glHeadName + "','" + brCode + "','" + brCode + "','','')").executeUpdate();

                            if (reconTrf <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }
                        }
                    } else {
                        //Cheque Book Charges
                        chequeCommAmt = tmpCharges * noOfChq;
                        if (chequeCommAmt > 0) {
                            List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + glHead + "'").getResultList();
                            String glHeadName = "NOT AVAIL";
                            if (glTableList.size() > 0) {
                                Vector findTaxVect = (Vector) glTableList.get(0);
                                glHeadName = findTaxVect.get(0).toString();
                            }
                            float recon = ftsMethods.getRecNo();
                            Integer reconTrf = em.createNativeQuery("INSERT INTO recon_trf_d(Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                    + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode)  VALUES('"
                                    + glHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + Math.round(chequeCommAmt) + ",'Cheque Book Charges for " + item.getAcno() + "',2,0," + recon + "," + tmpTrsno + ",1,3,8,'"
                                    + userName + "','" + glHeadName + "','" + brCode + "','" + brCode + "','','')").executeUpdate();

                            if (reconTrf <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }
                        }

                        //Stock of Cheque Book Charge
                        stockCommAmt = tmpStCharges * noOfChq;
                        if (stockCommAmt > 0) {
                            List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + glHeadSt + "'").getResultList();
                            String glHeadName = "NOT AVAIL";
                            if (glTableList.size() > 0) {
                                Vector findTaxVect = (Vector) glTableList.get(0);
                                glHeadName = findTaxVect.get(0).toString();
                            }
                            float recon = ftsMethods.getRecNo();

                            Integer reconTrf = em.createNativeQuery("INSERT INTO recon_trf_d(Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                    + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode)  VALUES('"
                                    + glHeadSt + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + Math.round(stockCommAmt) + ",'Stock of Cheque Book Charges for " + item.getAcno() + "',2,0," + recon + "," + tmpTrsno + ",1,3,8,'"
                                    + userName + "','" + glHeadName + "','" + brCode + "','" + brCode + "','','')").executeUpdate();

                            if (reconTrf <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }
                        }
                    }
                    // For Service Tax Charge
                    if (staxModuleActive.equalsIgnoreCase("1")) {
                        if (!orgType.equalsIgnoreCase("16")) {
                            // For Old code
                            if (custState.equalsIgnoreCase(branchState)) {
                                map = ibRemote.getTaxComponent(new Double(Math.round(chequeCommAmt)), ymd.format(new Date()));
                                Set<Map.Entry<String, Double>> set1 = map.entrySet();
                                Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                                while (it1.hasNext()) {
                                    Map.Entry entry = it1.next();
                                    String[] keyArray = entry.getKey().toString().split(":");
                                    String description = keyArray[0];
                                    String taxHead = brCode + keyArray[1];
                                    List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + taxHead + "'").getResultList();
                                    String glHeadName = "NOT AVAIL";
                                    if (glTableList.size() > 0) {
                                        Vector findTaxVect = (Vector) glTableList.get(0);
                                        glHeadName = findTaxVect.get(0).toString();
                                    }
                                    mainDetails = description.toUpperCase() + " for Cheque Issue Chg. for. " + item.getAcno();
                                    taxAmount = Double.parseDouble(entry.getValue().toString());
                                    totTaxAmount = totTaxAmount + taxAmount;
                                    float recon = ftsMethods.getRecNo();
                                    Integer reconList = em.createNativeQuery("INSERT INTO  recon_trf_d (Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                            + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                            + taxHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                            + taxAmount + " , '" + mainDetails + "' ,2,0," + recon + ","
                                            + tmpTrsno + " ," + retiy + ",3,71,'" + userName + "','" + glHeadName + "','" + brCode + "','"
                                            + brCode + "','','')").executeUpdate();
                                    if (reconList <= 0) {
                                        throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                    }
                                }
                            } else {
                                map = ibRemote.getIgstTaxComponent(new Double(Math.round(chequeCommAmt)), ymd.format(new Date()));
                                Set<Map.Entry<String, Double>> set1 = map.entrySet();
                                Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                                while (it1.hasNext()) {
                                    Map.Entry entry = it1.next();
                                    String[] keyArray = entry.getKey().toString().split(":");
                                    String description = keyArray[0];
                                    String taxHead = brCode + keyArray[1];
                                    List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + taxHead + "'").getResultList();
                                    String glHeadName = "NOT AVAIL";
                                    if (glTableList.size() > 0) {
                                        Vector findTaxVect = (Vector) glTableList.get(0);
                                        glHeadName = findTaxVect.get(0).toString();
                                    }
                                    mainDetails = description.toUpperCase() + " for Cheque Issue Chg. for. " + item.getAcno();
                                    taxAmount = Double.parseDouble(entry.getValue().toString());
                                    totTaxAmount = totTaxAmount + taxAmount;
                                    float recon = ftsMethods.getRecNo();
                                    Integer reconList = em.createNativeQuery("INSERT INTO  recon_trf_d (Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                            + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                            + taxHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                            + taxAmount + " , '" + mainDetails + "' ,2,0," + recon + ","
                                            + tmpTrsno + " ," + retiy + ",3,71,'" + userName + "','" + glHeadName + "','" + brCode + "','"
                                            + brCode + "','','')").executeUpdate();
                                    if (reconList <= 0) {
                                        throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                    }
                                }
                            }
                        }
                    }
                    //double totalCrAmt = Math.round(chequeCommAmt) + Math.round(stockCommAmt) + Math.round(tax);
                    double totalCrAmt = Math.round(chequeCommAmt) + Math.round(stockCommAmt) + totTaxAmount;
                    float recon = ftsMethods.getRecNo();
                    Integer reconTrfList = em.createNativeQuery("INSERT INTO recon_trf_d(acno,dt,valuedt,dramt,details,trantype,ty,"
                            + "recno,trsno,iy,payby,tranDesc,ENTERBY,CUSTNAME,trantime,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                            + item.getAcno() + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                            + totalCrAmt + ",'Cheque Book Charges',2,1," + recon + "," + tmpTrsno + ",1,3,8,'" + userName + "',"
                            + " '" + item.getCustName() + "',CURRENT_TIMESTAMP,'" + brCode + "','" + brCode + "','','')").executeUpdate();
                    if (reconTrfList <= 0) {
                        throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                    }
                    ftsMethods.updateBalance(acNature, item.getAcno(), 0, totalCrAmt, "Y", "Y");
                }
            } else {

                if (item.getChargeFlag().equalsIgnoreCase("Y")) {
                    if (ftsMethods.existInParameterInfoReport("CHEQUE_BOOK_CHARGE_AUTH")) {
                        List parameterList = em.createNativeQuery("SELECT charges,glheadmisc FROM parameterinfo_miscincome WHERE "
                                + "acctCode=SUBSTRING('" + item.getAcno() + "',3,2) AND Purpose='Cheque Book Charges'").getResultList();
                        if (parameterList.isEmpty()) {
                            throw new ApplicationException("There is no GLHead for cheque charge posting");
                        }
                        Vector parameterVect = (Vector) parameterList.get(0);
                        float tmpCharges = 0f;
                        String glHead = "";
                        if (parameterVect.get(0) != null) {
                            tmpCharges = Float.parseFloat(parameterVect.get(0).toString());
                        }
                        if (parameterVect.get(1) != null) {
                            glHead = brCode + parameterVect.get(1).toString() + "01";
                        }
                        List parameterinfoList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
                        if (parameterinfoList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in parameterinfo_report for tax module.");
                        }
                        parameterVect = (Vector) parameterinfoList.get(0);
                        String staxModuleActive = parameterVect.get(0).toString();
                        commAmt = tmpCharges * noOfChq;
                        int retiy = 1;
                        tmpTrsno = ftsMethods.getTrsNo();
                        if (commAmt > 0) {
                            float recon = ftsMethods.getRecNo();
                            String msg = "";
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                msg = ftsMethods.checkBalForOdLimit(item.getAcno(), commAmt, userName);
                                if (msg.equalsIgnoreCase("99")) {
                                    throw new ApplicationException("Limit Exceede for : " + item.getAcno());
                                } else if (!msg.equalsIgnoreCase("1")) {
                                    throw new ApplicationException("Balance Exceeds.");
                                }
                            } else {
                                msg = ftsMethods.checkBalance(item.getAcno(), commAmt, userName);
                                if (!msg.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(item.getAcno() + " Does not have sufficient balance to deduct charges");
                                }
                            }

                            Integer reconTrfList = em.createNativeQuery("INSERT INTO recon_trf_d(acno,dt,valuedt,dramt,details,trantype,ty,"
                                    + "recno,trsno,iy,payby,tranDesc,ENTERBY,CUSTNAME,trantime,org_brnid,dest_brnid,adviceNo,adviceBrnCode,auth,authby) VALUES('"
                                    + item.getAcno() + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + commAmt + ",'Cheque Book Charges',2,1," + recon + "," + tmpTrsno + ",1,3,108,'" + userName + "',"
                                    + " '" + item.getCustName() + "',CURRENT_TIMESTAMP,'" + brCode + "','" + brCode + "','','','Y','" + userName + "')").executeUpdate();
                            if (reconTrfList <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }

                            String ftsMsg = ftsMethods.ftsPosting43CBS(item.getAcno(), 2, 1, commAmt, ymd.format(new Date()), ymd.format(new Date()), userName, brCode, brCode, 108, "Cheque Book Charges",
                                    tmpTrsno, recon, 0, "", "Y", userName, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                            if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException("Error In Transaction " + ftsMsg);
                            }

                            // ftsMethods.updateBalance(acNature, item.getAcno(), 0, commAmt, "Y", "Y");
                            List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + glHead + "'").getResultList();
                            String glHeadName = "NOT AVAIL";
                            if (glTableList.size() > 0) {
                                Vector findTaxVect = (Vector) glTableList.get(0);
                                glHeadName = findTaxVect.get(0).toString();
                            }

                            recon = ftsMethods.getRecNo();

                            Integer reconTrf = em.createNativeQuery("INSERT INTO recon_trf_d(Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                    + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode,auth,authby)  VALUES('"
                                    + glHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + commAmt + ",'Cheque Book Charges for " + item.getAcno() + "',2,0," + recon + "," + tmpTrsno + ",1,3,108,'"
                                    + userName + "','" + glHeadName + "','" + brCode + "','" + brCode + "','','','Y','" + userName + "')").executeUpdate();

                            if (reconTrf <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }

                            ftsMsg = ftsMethods.ftsPosting43CBS(glHead, 2, 0, commAmt, ymd.format(new Date()), ymd.format(new Date()), userName, brCode, brCode, 108, "Cheque Book Charges",
                                    tmpTrsno, recon, 0, "", "Y", userName, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                            if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException("Error In Transaction " + ftsMsg);
                            }

                            if (staxModuleActive.equalsIgnoreCase("1")) {
                                double sTax = 0d;
                                if (custState.equalsIgnoreCase(branchState)) {
                                    map = ibRemote.getTaxComponent(commAmt, ymd.format(new Date()));
                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                    }
                                } else {
                                    map = ibRemote.getIgstTaxComponent(commAmt, ymd.format(new Date()));
                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                    }

                                }

                                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    msg = ftsMethods.checkBalForOdLimit(item.getAcno(), sTax, userName);
                                    if (msg.equalsIgnoreCase("99")) {
                                        throw new ApplicationException("Limit Exceede for : " + item.getAcno());
                                    } else if (!msg.equalsIgnoreCase("1")) {
                                        throw new ApplicationException("Balance Exceeds.");
                                    }
                                } else {
                                    msg = ftsMethods.checkBalance(item.getAcno(), sTax, userName);
                                    if (!msg.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(item.getAcno() + " Does not have sufficient balance to deduct charges");
                                    }
                                }

                                recon = ftsMethods.getRecNo();
                                Integer reconTrfDList = em.createNativeQuery("INSERT INTO  recon_trf_d(acno,dt,valuedt,dramt,details,trantype,ty,"
                                        + "recno,trsno,iy,payby,tranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                        + item.getAcno() + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                        + sTax + ",'GST For Cheque Book Charge',2,1," + " " + recon + "," + tmpTrsno + "," + retiy + ",3,71,'"
                                        + userName + "','" + item.getCustName() + "','" + brCode + "','" + brCode + "','','')").executeUpdate();

                                if (reconTrfDList <= 0) {
                                    throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                }

                                ftsMsg = ftsMethods.ftsPosting43CBS(item.getAcno(), 2, 1, sTax, ymd.format(new Date()), ymd.format(new Date()), userName, brCode, brCode, 71, "GST For Cheque Book Charge",
                                        tmpTrsno, recon, 0, "", "Y", userName, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                                if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                    throw new ApplicationException("Error In Transaction " + ftsMsg);
                                }

                                // ftsMethods.updateBalance(acNature, item.getAcno(), 0, sTax, "Y", "Y");
                                //Service Tax Entry As Per New
                                if (custState.equalsIgnoreCase(branchState)) {
                                    Set<Map.Entry<String, Double>> set1 = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                                    while (it1.hasNext()) {
                                        Map.Entry entry = it1.next();
                                        String[] keyArray = entry.getKey().toString().split(":");
                                        String description = keyArray[0];
                                        String taxHead = brCode + keyArray[1];
                                        glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + taxHead + "'").getResultList();
                                        glHeadName = "NOT AVAIL";
                                        if (glTableList.size() > 0) {
                                            Vector findTaxVect = (Vector) glTableList.get(0);
                                            glHeadName = findTaxVect.get(0).toString();
                                        }
                                        mainDetails = description.toUpperCase() + " for Cheque Issue Chg. for. " + item.getAcno();
                                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                                        recon = ftsMethods.getRecNo();
                                        Integer reconList = em.createNativeQuery("INSERT INTO  recon_trf_d (Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                                + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                                + taxHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                                + taxAmount + " , '" + mainDetails + "' ,2,0," + recon + ","
                                                + tmpTrsno + " ," + retiy + ",3,71,'" + userName + "','" + glHeadName + "','" + brCode + "','"
                                                + brCode + "','','')").executeUpdate();
                                        if (reconList <= 0) {
                                            throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                        }

                                        ftsMsg = ftsMethods.ftsPosting43CBS(taxHead, 2, 0, taxAmount, ymd.format(new Date()), ymd.format(new Date()), userName, brCode, brCode, 71, mainDetails,
                                                tmpTrsno, recon, 0, "", "Y", userName, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                            throw new ApplicationException("Error In Transaction " + ftsMsg);
                                        }
                                    }
                                } else {
                                    Set<Map.Entry<String, Double>> set1 = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                                    while (it1.hasNext()) {
                                        Map.Entry entry = it1.next();
                                        String[] keyArray = entry.getKey().toString().split(":");
                                        String description = keyArray[0];
                                        String taxHead = brCode + keyArray[1];
                                        glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + taxHead + "'").getResultList();
                                        glHeadName = "NOT AVAIL";
                                        if (glTableList.size() > 0) {
                                            Vector findTaxVect = (Vector) glTableList.get(0);
                                            glHeadName = findTaxVect.get(0).toString();
                                        }
                                        mainDetails = description.toUpperCase() + " for Cheque Issue Chg. for. " + item.getAcno();
                                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                                        recon = ftsMethods.getRecNo();
                                        Integer reconList = em.createNativeQuery("INSERT INTO  recon_trf_d (Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                                + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                                + taxHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                                + taxAmount + " , '" + mainDetails + "' ,2,0," + recon + ","
                                                + tmpTrsno + " ," + retiy + ",3,71,'" + userName + "','" + glHeadName + "','" + brCode + "','"
                                                + brCode + "','','')").executeUpdate();
                                        if (reconList <= 0) {
                                            throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                        }

                                        ftsMsg = ftsMethods.ftsPosting43CBS(taxHead, 2, 0, taxAmount, ymd.format(new Date()), ymd.format(new Date()), userName, brCode, brCode, 71, mainDetails,
                                                tmpTrsno, recon, 0, "", "Y", userName, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                            throw new ApplicationException("Error In Transaction " + ftsMsg);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        List parameterList = em.createNativeQuery("SELECT charges,glheadmisc FROM parameterinfo_miscincome WHERE "
                                + "acctCode=SUBSTRING('" + item.getAcno() + "',3,2) AND Purpose='Cheque Book Charges'").getResultList();
                        if (parameterList.isEmpty()) {
                            throw new ApplicationException("There is no GLHead for cheque charge posting");
                        }
                        Vector parameterVect = (Vector) parameterList.get(0);
                        float tmpCharges = 0f;
                        String glHead = "";
                        if (parameterVect.get(0) != null) {
                            tmpCharges = Float.parseFloat(parameterVect.get(0).toString());
                        }
                        if (parameterVect.get(1) != null) {
                            glHead = brCode + parameterVect.get(1).toString() + "01";
                        }
                        List parameterinfoList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
                        if (parameterinfoList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in parameterinfo_report for tax module.");
                        }
                        parameterVect = (Vector) parameterinfoList.get(0);
                        String staxModuleActive = parameterVect.get(0).toString();
                        commAmt = tmpCharges * noOfChq;
                        int retiy = 1;
                        tmpTrsno = ftsMethods.getTrsNo();
                        if (commAmt > 0) {
                            float recon = ftsMethods.getRecNo();

                            String msg = "";
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                msg = ftsMethods.checkBalForOdLimit(item.getAcno(), commAmt, userName);
                                if (msg.equalsIgnoreCase("99")) {
                                    throw new ApplicationException("Limit Exceede for : " + item.getAcno());
                                } else if (!msg.equalsIgnoreCase("1")) {
                                    throw new ApplicationException("Balance Exceeds.");
                                }
                            } else {
                                msg = ftsMethods.checkBalance(item.getAcno(), commAmt, userName);
                                if (!msg.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(item.getAcno() + " Does not have sufficient balance to deduct charges");
                                }
                            }

                            Integer reconTrfList = em.createNativeQuery("INSERT INTO recon_trf_d(acno,dt,valuedt,dramt,details,trantype,ty,"
                                    + "recno,trsno,iy,payby,tranDesc,ENTERBY,CUSTNAME,trantime,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                    + item.getAcno() + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + commAmt + ",'Cheque Book Charges',2,1," + recon + "," + tmpTrsno + ",1,3,108,'" + userName + "',"
                                    + " '" + item.getCustName() + "',CURRENT_TIMESTAMP,'" + brCode + "','" + brCode + "','','')").executeUpdate();
                            if (reconTrfList <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }
                            // cbsReconBalanUpdation(item.getAcno(), -commAmt);
                            ftsMethods.updateBalance(acNature, item.getAcno(), 0, commAmt, "Y", "Y");
                            List glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + glHead + "'").getResultList();
                            String glHeadName = "NOT AVAIL";
                            if (glTableList.size() > 0) {
                                Vector findTaxVect = (Vector) glTableList.get(0);
                                glHeadName = findTaxVect.get(0).toString();
                            }

                            Integer reconTrf = em.createNativeQuery("INSERT INTO recon_trf_d(Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                    + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode)  VALUES('"
                                    + glHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                    + commAmt + ",'Cheque Book Charges for " + item.getAcno() + "',2,0," + recon + "," + tmpTrsno + ",1,3,108,'"
                                    + userName + "','" + glHeadName + "','" + brCode + "','" + brCode + "','','')").executeUpdate();

                            if (reconTrf <= 0) {
                                throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                            }
                            if (staxModuleActive.equalsIgnoreCase("1")) {
                                double sTax = 0d;
                                if (custState.equalsIgnoreCase(branchState)) {
                                    map = ibRemote.getTaxComponent(commAmt, ymd.format(new Date()));
                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                    }
                                } else {
                                    map = ibRemote.getIgstTaxComponent(commAmt, ymd.format(new Date()));
                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                    }
                                }

                                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    msg = ftsMethods.checkBalForOdLimit(item.getAcno(), sTax, userName);
                                    if (msg.equalsIgnoreCase("99")) {
                                        throw new ApplicationException("Limit Exceede for : " + item.getAcno());
                                    } else if (!msg.equalsIgnoreCase("1")) {
                                        throw new ApplicationException("Balance Exceeds.");
                                    }
                                } else {
                                    msg = ftsMethods.checkBalance(item.getAcno(), sTax, userName);
                                    if (!msg.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(item.getAcno() + " Does not have sufficient balance to deduct charges");
                                    }
                                }

                                recon = ftsMethods.getRecNo();
                                Integer reconTrfDList = em.createNativeQuery("INSERT INTO  recon_trf_d(acno,dt,valuedt,dramt,details,trantype,ty,"
                                        + "recno,trsno,iy,payby,tranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                        + item.getAcno() + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                        + sTax + ",'GST For Cheque Book Charge',2,1," + " " + recon + "," + tmpTrsno + "," + retiy + ",3,71,'"
                                        + userName + "','" + item.getCustName() + "','" + brCode + "','" + brCode + "','','')").executeUpdate();

                                if (reconTrfDList <= 0) {
                                    throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                }

                                //double ttlTax = cmrFacade.findTax(commAmt, ymd.format(new Date()));
                                //cbsReconBalanUpdation(item.getAcno(), -ttlTax);
                                ftsMethods.updateBalance(acNature, item.getAcno(), 0, sTax, "Y", "Y");

                                //Service Tax Entry As Per New
                                if (custState.equalsIgnoreCase(branchState)) {
                                    Set<Map.Entry<String, Double>> set1 = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                                    while (it1.hasNext()) {
                                        Map.Entry entry = it1.next();
                                        String[] keyArray = entry.getKey().toString().split(":");
                                        String description = keyArray[0];
                                        String taxHead = brCode + keyArray[1];
                                        glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + taxHead + "'").getResultList();
                                        glHeadName = "NOT AVAIL";
                                        if (glTableList.size() > 0) {
                                            Vector findTaxVect = (Vector) glTableList.get(0);
                                            glHeadName = findTaxVect.get(0).toString();
                                        }
                                        mainDetails = description.toUpperCase() + " for Cheque Issue Chg. for. " + item.getAcno();
                                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                                        recon = ftsMethods.getRecNo();
                                        Integer reconList = em.createNativeQuery("INSERT INTO  recon_trf_d (Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                                + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                                + taxHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                                + taxAmount + " , '" + mainDetails + "' ,2,0," + recon + ","
                                                + tmpTrsno + " ," + retiy + ",3,71,'" + userName + "','" + glHeadName + "','" + brCode + "','"
                                                + brCode + "','','')").executeUpdate();
                                        if (reconList <= 0) {
                                            throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                        }
                                    }
                                } else {
                                    Set<Map.Entry<String, Double>> set1 = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                                    while (it1.hasNext()) {
                                        Map.Entry entry = it1.next();
                                        String[] keyArray = entry.getKey().toString().split(":");
                                        String description = keyArray[0];
                                        String taxHead = brCode + keyArray[1];
                                        glTableList = em.createNativeQuery("SELECT SUBSTRING(ACNAME,1,40) FROM gltable WHERE ACNO='" + taxHead + "'").getResultList();
                                        glHeadName = "NOT AVAIL";
                                        if (glTableList.size() > 0) {
                                            Vector findTaxVect = (Vector) glTableList.get(0);
                                            glHeadName = findTaxVect.get(0).toString();
                                        }
                                        mainDetails = description.toUpperCase() + " for Cheque Issue Chg. for. " + item.getAcno();
                                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                                        recon = ftsMethods.getRecNo();
                                        Integer reconList = em.createNativeQuery("INSERT INTO  recon_trf_d (Acno,Dt,valuedt,Cramt,Details,Trantype,Ty,"
                                                + "Recno,Trsno,iy,payBy,TranDesc,ENTERBY,CUSTNAME,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('"
                                                + taxHead + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'),"
                                                + taxAmount + " , '" + mainDetails + "' ,2,0," + recon + ","
                                                + tmpTrsno + " ," + retiy + ",3,71,'" + userName + "','" + glHeadName + "','" + brCode + "','"
                                                + brCode + "','','')").executeUpdate();
                                        if (reconList <= 0) {
                                            throw new ApplicationException("Data is does get not Inserted in RECON_TRF_D");
                                        }
                                    }
                                }

                                //List fnTax = fnTaxApplicableROT(ymd.format(new Date()));
                                //Vector fnTaxVect = (Vector) fnTax.get(0);
                                //glHead = brCode + fnTaxVect.get(3).toString() + "01";                        
                            }
                        }
                    }
                }
            }
            ut.commit();
            //Sending Sms
            try {
                //Cheque Book Issue
                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                tSmsRequestTo.setMsgType("PAT");
                tSmsRequestTo.setTemplate(SmsType.CHEQUE_BOOK_ISSUE);
                tSmsRequestTo.setAcno(item.getAcno());

                MbSubscriberTabTO subscriberTo = smsFacade.getSubscriberDetails(item.getAcno());
                List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                tSmsRequestTo.setPromoMobile(subscriberTo.getMobileNo());
                tSmsRequestTo.setBankName(templateBankName);

                smsFacade.sendSms(tSmsRequestTo);
                //If Charges.
//                if (item.getChargeFlag().equalsIgnoreCase("Y") && commAmt > 0) {
//                    smsFacade.sendTransactionalSms(SmsType.CHARGE_WITHDRAWAL, item.getAcno(), 9,
//                            1, commAmt, dmy.format(new Date()));
//                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending SMS In Cheque Book Issue.");
            }
            //End here
            if (item.getChargeFlag().equalsIgnoreCase("Y")) {
                return "True" + tmpTrsno;
            } else {
                return "True";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List grid(String BranchCode) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("select Instcode,sno from chbookmaster_amtwise where  brncode = '" + BranchCode + "' AND auth = 'N' and status = 'I' ORDER BY sno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public List getSelectedValue(String instcode, int sno, String BranchCode) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("select InstCode ,SlabCode , AmtFrom ,AmtTo ,date_format(IssueDt,'%d/%m/%Y') AS IssueDt , numfrom , numto ,Leaves ,printlot ,IssueBy  from chbookmaster_amtwise where brncode = '" + BranchCode + "' AND status = 'I' and (ifnull(AUTH,'')='' or auth in ('N')) and instcode='" + instcode + "' and sno=" + sno + " ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public String authDeleteData(String InstCode, int SlabCode, float AmtFrom, float AmtTo,
            String IssueDt, int numfrom, int numto, int Leaves, String printlot, String IssueBy,
            String option, String authUser, int sno, String BranchCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            int var = 0;
            int var1 = 0;
            int var2 = 0;
            int var3 = 0;

            ut.begin();
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BranchCode + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String tempbd = tempCurrent.get(0).toString();
            if (option.equals("AUTHORIZE")) {
                if (IssueBy.equals(authUser)) {
                    ut.rollback();
                    return "You cannot Authorize Book Enter by Yourself";
                }
                Query updateQuery = em.createNativeQuery("UPDATE chbookmaster_amtwise SET AUTH='Y',AUTHBY='" + authUser + "' WHERE brncode = '" + BranchCode + "' AND instcode='" + InstCode + "' and sno=" + sno + " ");
                var = updateQuery.executeUpdate();

                // Added By Rahul for chbook_file_deatils Entry
                if (ftsMethods.getCodeForReportName("PO-BOOK-PRINTING-FILE") == 1) {

                    List txnCodeList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_rec_no='015' and ref_desc='" + InstCode + "'").getResultList();

                    if (txnCodeList.isEmpty()) {
                        throw new ApplicationException("Please define the Transaction Code");
                    }

                    Vector txnVect = (Vector) txnCodeList.get(0);
                    String txnCode = txnVect.get(0).toString();

                    //String san = printlot;
                    List srNoList = em.createNativeQuery("select ifnull(max(srNo),0)+1 from chbook_file_details").getResultList();
                    Vector srNoVect = (Vector) srNoList.get(0);
                    long srNo = Long.parseLong(srNoVect.get(0).toString());

                    String query = "INSERT INTO chbook_file_details (acno, san, txn_code, at_par, chbook_type, book_size, start_chq_no, end_chq_no, "
                            + "entery_by, enter_date, file_gen_by, file_name,srNo) VALUES ('" + BranchCode + "', '" + printlot + "', '" + txnCode
                            + "', 'N', 'Order'," + Leaves + ", " + numfrom + ", " + numto + ", '"
                            + authUser + "',now() , '',''," + srNo + ")";

                    int rs = em.createNativeQuery(query).executeUpdate();

                    if (rs <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }
                }
                //
                while (numfrom <= numto) {
                    Query insertQuery = em.createNativeQuery("INSERT INTO chbook_bill(DDno,Series,statusFlag,EnterBy,EntryDt,TranTime,Inst_Type,SlabCode,brncode) "
                            + "values (" + numfrom + "," + "'" + printlot + "'" + ",'F'," + "'" + authUser + "'" + "," + "'" + IssueDt + "'" + "," + "'" + tempbd + "'" + "," + "'" + InstCode + "'" + "," + SlabCode + "," + "'" + BranchCode + "'" + ")");
                    var1 = insertQuery.executeUpdate();
                    numfrom = numfrom + 1;
                }
            }

            if (option.equals("DELETE")) {
                String DETAILS = SlabCode + ":" + String.valueOf(AmtTo) + ":" + IssueDt + ":" + String.valueOf(numfrom);
                Query insertQuery = em.createNativeQuery("INSERT INTO deletetrans_general(purpose,details,entrydt,entryby,deletedby,brncode)"
                        + "values (" + "'" + InstCode + "'" + "," + "'" + DETAILS + "'" + "," + "'" + IssueDt + "'" + "," + "'" + IssueBy + "'" + "," + "'" + authUser + "'" + "," + "'" + BranchCode + "'" + ")");
                var2 = insertQuery.executeUpdate();
                Query deleteQuery = em.createNativeQuery("DELETE FROM chbookmaster_amtwise WHERE brncode = '" + BranchCode + "' AND instcode='" + InstCode + "' AND sno=" + sno + " AND (AUTH='N' OR ifnull(AUTH,'')='') ");
                var3 = deleteQuery.executeUpdate();
            }

            if (var > 0) {
                ut.commit();
                return "AUTHORIZATION OVER";
            } else if (var > 0 && var1 > 0) {
                ut.commit();
                return "AUTHORIZATION OVER";
            } else if (var2 > 0 && var3 > 0) {
                ut.commit();
                return "DELETION COMPLETED";
            } else {
                ut.rollback();
                return "Some Problem In " + option;
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List gridLoad(String brCode, String curDt) throws ApplicationException {
        List grid = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select aa.Acno,aa.Custname,aa.ChBookNo,aa.ChequeNo,aa.Status,aa.Amount,aa.Favoring,"
                    + " aa.ChequeDt,aa.EnteredBy,aa.EnteryDate,aa.authby,bb.stateCode, bb.brState from "
                    + " (Select a.Acno,b.Custname,a.ChBookNo,a.ChequeNo,a.Status,a.Amount,a.Favoring, "
                    + " date_format(a.ChequeDt,'%d/%m/%Y') as ChequeDt,a.EnteredBy,a.EnteryDate,coalesce(a.authby,'N') as authby From "
                    + " chbookdetail a ,accountmaster b Where a.Status in(1,9) and b.curBrCode = '" + brCode + "' and a.acno=b.acno "
                    + " and a.authBy is null and enterydate='" + curDt + "') aa, "
                    + " (select ci.Acno as acno, ifnull(cm.mailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                    + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                    + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brCode + "' as unsigned) and "
                    + "substring(ci.Acno,1,2) = '" + brCode + "')  bb  where aa.acno = bb.acno order by aa.status ");
            grid = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return grid;
    }

    public String authorizeAction(ChbookDetailPojo obj, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            ut.begin();
            if (obj == null) {
                throw new ApplicationException("Please double click on the auth column to authorize the entry.");
            }
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            String message = "", mainDetails = null;
            int updateResult = em.createNativeQuery("update chbookdetail set authby = '" + enterBy + "' where acno = '" + obj.getAcctNo()
                    + "' and ChBookNo=" + Long.parseLong(obj.getChqNo()) + " and ChequeNo=" + Long.parseLong(obj.getChqNoTo()) + " and status="
                    + obj.getStatus() + "").executeUpdate();
            if ((updateResult <= 0)) {
                throw new ApplicationException("Problem in cheque status updatation.");
            }

            String chequeBookTable = cmrFacade.getChequeBookTable(ftsMethods.getAccountNature(obj.getAcctNo()));
            String busnessDt = ftsBulk.daybeginDate(brcode);
            String status = obj.getStatus() == 9 ? "S" : "F";
            long chqFrNo = Long.parseLong(obj.getChqNo());
            while (chqFrNo <= Long.parseLong(obj.getChqNoTo())) {
                updateResult = em.createNativeQuery("update " + chequeBookTable + " set statusflag = '" + status + "',lastupdateby = '" + enterBy
                        + "',lastupdatedt = '" + busnessDt + "' where acno='" + obj.getAcctNo() + "' and chqno=" + chqFrNo).executeUpdate();

                if (updateResult <= 0) {
                    throw new ApplicationException("Cheque Status updation problem for Cheque Number is " + obj.getChqNo());
                }
                chqFrNo = chqFrNo + 1;
            }

            /**
             * **Charge Deduction case***
             */
            String tmpDetails = "";
            long noOfLeaves = 0;
            if (status.equalsIgnoreCase("S")) {
                List glHeadChkList = cmrFacade.chkGLHead(ftsMethods.getAccountCode(obj.getAcctNo()));
                if (glHeadChkList.isEmpty()) {
                    throw new ApplicationException("There Is No GLHead For Stop Payment Charges , Please Enter GLHead !!!");
                }
                Vector ele = (Vector) glHeadChkList.get(0);
                double charges = Double.parseDouble(ele.get(0).toString());
                double charges1 = Double.parseDouble(ele.get(2).toString());
                String glHead = ele.get(1).toString();

                if (obj.getChqNo().equalsIgnoreCase(obj.getChqNoTo())) {
                    tmpDetails = "Stop Payment Charge For Instno : " + obj.getChqNoTo();
                    noOfLeaves = 1;
                } else {
                    tmpDetails = "Stop Payment Charge For Instno From : " + obj.getChqNoTo() + " To " + obj.getChqNo();
                    noOfLeaves = (Long.parseLong(obj.getChqNoTo()) - Long.parseLong(obj.getChqNo())) + 1;
                }

                List accountList = em.createNativeQuery("select odlimit,adhoclimit,coalesce(adhoctilldt,''), ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                        + " from accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where am.acno = '" + obj.getAcctNo() + "' and am.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + " and br.brncode=cast('" + brcode + "' as unsigned) and substring(ci.Acno,1,2) = '" + brcode + "'").getResultList();
                if (accountList.isEmpty()) {
                    throw new ApplicationException("Account does not exist");
                }
                Vector accountVector = (Vector) accountList.get(0);
                double odLimit = Double.parseDouble(accountVector.get(0).toString());
                double adhocLimit = Double.parseDouble(accountVector.get(1).toString());
                String adhocTillDt = accountVector.get(2).toString();
                String custState = accountVector.get(3).toString();
                String branchState = accountVector.get(4).toString();
                if (adhocTillDt.equals("")) {
                    adhocTillDt = ymdhms.format(ymd.parse(CbsUtil.dateAdd(busnessDt, -2)));
                }

                long dayDiff = CbsUtil.dayDiff(ymdhms.parse(adhocTillDt), ymd.parse(busnessDt));
                double balance = 0;
                if (dayDiff > 1) {
                    balance = odLimit;
                } else {
                    balance = odLimit + adhocLimit;
                }
                List list = cmrFacade.getAcBalance(obj.acctNo);
                double acBal = 0;
                if (!list.isEmpty()) {
                    Vector element = (Vector) list.get(0);
                    acBal = Double.parseDouble(element.get(0).toString());
                }
                double totBal = balance + acBal;
                double commAmt = 0;

                if (noOfLeaves > 1) {
                    int cOpt = 0;
                    List optList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where reportname = 'STOP_CHG_FLAT'").getResultList();
                    if (!optList.isEmpty()) {
                        Vector optVector = (Vector) optList.get(0);
                        cOpt = Integer.parseInt(optVector.get(0).toString());
                    }
                    if (cOpt == 1) {
                        commAmt = charges1;
                    } else {
                        commAmt = noOfLeaves * charges;
                    }
                } else {
                    commAmt = charges;
                }

                List taxModList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'STAXMODULE_ACTIVE'").getResultList();
                if (taxModList.isEmpty()) {
                    throw new ApplicationException("Please define STAXMODULE_ACTIVE in Parameterinfo Report.");
                }
                Vector taxModVector = (Vector) taxModList.get(0);
                Integer code = Integer.parseInt(taxModVector.get(0).toString());
                //String sTaxModActive = "";
                //double totAmt = 0;
                double sTaxAmt = 0;
                if (code == 1) {
                    if (custState.equalsIgnoreCase(branchState)) {
                        map = ibRemote.getTaxComponent(commAmt, busnessDt);
                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            sTaxAmt = sTaxAmt + Double.parseDouble(entry.getValue().toString());
                        }
                    } else {
                        map = ibRemote.getIgstTaxComponent(commAmt, busnessDt);
                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            sTaxAmt = sTaxAmt + Double.parseDouble(entry.getValue().toString());
                        }
                    }
                }

                if ((commAmt + sTaxAmt) > 0) {
                    if (totBal > (commAmt + sTaxAmt)) {
                        float trsNo = ftsMethods.getTrsNo();
                        float recNo = ftsMethods.getRecNo();
                        String ftsMsg = ftsMethods.ftsPosting43CBS(obj.getAcctNo(), 2, 1, commAmt, busnessDt, busnessDt, enterBy, brcode, brcode, 120, tmpDetails,
                                trsNo, recNo, 0, "", "Y", enterBy, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException("Error In Transaction " + ftsMsg);
                        }

                        ftsMsg = ftsMethods.ftsPosting43CBS(brcode + glHead + "01", 2, 0, commAmt, busnessDt, busnessDt, enterBy, brcode, brcode, 120, tmpDetails,
                                trsNo, ftsMethods.getRecNo(), 0, "", "Y", enterBy, "", 3, "", "", "", 0.0f, "", "", "T", "", 0.0f, "N", "", "", "");
                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(ftsMsg);
                        }

//                        if (sTaxModActive.equalsIgnoreCase("Y") && sTaxAmt > 0) {
                        if (code == 1 && sTaxAmt > 0) {
                            //double amt = Double.parseDouble(cmrFacade.taxAmount(commAmt, appType));
                            ftsMsg = ftsMethods.ftsPosting43CBS(obj.getAcctNo(), 2, 1, sTaxAmt, busnessDt, busnessDt, enterBy, brcode, brcode, 71, "GST on " + tmpDetails,
                                    trsNo, ftsMethods.getRecNo(), 0, "", "Y", enterBy, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                            if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(ftsMsg);
                            }

                            //Service Tax Entry As Per New
                            Set<Entry<String, Double>> set = map.entrySet();
                            Iterator<Entry<String, Double>> it = set.iterator();
                            while (it.hasNext()) {
                                Entry entry = it.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                String taxHead = brcode + keyArray[1];
                                mainDetails = description.toUpperCase() + " for Cheque Stop Payment Chg. for. " + obj.getAcctNo();
                                double taxAmount = Double.parseDouble(entry.getValue().toString());
                                ftsMsg = ftsMethods.ftsPosting43CBS(taxHead, 2, 0, taxAmount, busnessDt, busnessDt, enterBy, brcode, brcode, 71, mainDetails,
                                        trsNo, ftsMethods.getRecNo(), 0, "", "Y", enterBy, "", 3, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                                if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                    throw new ApplicationException(ftsMsg);
                                }
                            }

                        }
                        message = "Transaction has been completed successfully. Transfer Batch No is: " + String.valueOf(trsNo);
                    } else {
                        float trsNo = ftsMethods.getTrsNo();
                        float recNo = ftsMethods.getRecNo();
                        int insertResult = em.createNativeQuery("Insert into pendingcharges (acno,ty,dt,Amount,trantype,details,instno,recno,enterby,"
                                + "trsno,charges,trandesc) "
                                + " Values('" + obj.getAcctNo() + "',1,'" + busnessDt + "'," + commAmt + ",2, concat('Insufficient Fund Favof:' ,substring('"
                                + obj.getFavoring() + "',1,20) ,' :ChqDate:' ,'" + obj.getChqDate() + "'),'I'," + recNo + ",'" + enterBy + "'," + trsNo
                                + ",'Stop Payment Charges',120)").executeUpdate();

                        if (insertResult <= 0) {
                            throw new ApplicationException("Data Insertion Problem In PendingCharges");
                        } else {
                            message = "Transaction has been saved in PendingCharges";
                        }
                    }
                }
            }
            ut.commit();
            //Sending Sms For Cheque Stop Payment.
            try {
                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                if (Long.parseLong(obj.getChqNo()) == Long.parseLong(obj.getChqNoTo())) {
                    tSmsRequestTo.setTemplate(obj.getStatus() == 1 ? SmsType.ACTIVE_CHEQUE_PAYMENT
                            : SmsType.STOP_CHEQUE_PAYMENT);
                    tSmsRequestTo.setLastCheque(obj.getChqNo());
                } else {
                    tSmsRequestTo.setTemplate(obj.getStatus() == 1 ? SmsType.ACTIVE_SERIES_CHEQUE_PAYMENT
                            : SmsType.STOP_SERIES_CHEQUE_PAYMENT);
                    tSmsRequestTo.setLastCheque(obj.getChqNoTo());
                }
                tSmsRequestTo.setMsgType("PAT");
                tSmsRequestTo.setAcno(obj.getAcctNo());

                MbSubscriberTabTO subscriberTo = smsFacade.getSubscriberDetails(obj.getAcctNo());
                List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                tSmsRequestTo.setPromoMobile(subscriberTo.getMobileNo());
                tSmsRequestTo.setBankName(templateBankName);
                tSmsRequestTo.setFirstCheque(obj.getChqNo());

                smsFacade.sendSms(tSmsRequestTo);
            } catch (Exception ex) {
                System.out.println("Problem In Sending SMS In Cheque Stop Payment.");
            }
            //End here.
            return "success";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String validateBothAccountNo(String oldAccNo, String newAccNo) throws ApplicationException {
        String result = "TRUE";
        try {
            String chkAccNo = txnAuth.checkForAccountNo(oldAccNo);
            if (!chkAccNo.equals("TRUE")) {
                return chkAccNo;
            }

            String chkAccNo1 = txnAuth.checkForAccountNo(newAccNo);
            if (!chkAccNo1.equals("TRUE")) {
                return chkAccNo1;
            }

            List oldCustId = em.createNativeQuery("select custid FROM customerid WHERE acno='" + oldAccNo + "' ").getResultList();
            Vector tempCurrent = (Vector) oldCustId.get(0);
            String oldCust = tempCurrent.get(0).toString();

            List newCustId = em.createNativeQuery("select custid FROM customerid WHERE acno='" + newAccNo + "' ").getResultList();
            Vector tempCurrent1 = (Vector) newCustId.get(0);
            String newCust = tempCurrent1.get(0).toString();

            if (!oldCust.equalsIgnoreCase(newCust)) {
                return "Both Account is not from same Customer";
            }
        } catch (Exception ex) {
            try {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public String generateChBookPrintingFile(String instType, String fileName, String dt, String orgBrCode, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select aadhar_location, bank_code from mb_sms_sender_bank_detail").getResultList();
            Vector elem = (Vector) list.get(0);
            if (elem.get(0) == null || elem.get(1) == null || elem.get(0).toString().trim().equals("") || elem.get(1).toString().trim().equals("")) {
                throw new ApplicationException("Please define Aadhar Location and Bank Code.");
            }
            String aadharLocation = elem.get(0).toString().trim();
            List fileDataList = null;
            if (instType.equals("1")) {
                fileDataList = em.createNativeQuery("SELECT acno, san, txn_code, at_par, chbook_type, book_size, start_chq_no, end_chq_no, srNo"
                        + " FROM chbook_file_details where date_format(enter_date,'%Y%m%d') <='" + ymd.format(dmy.parse(dt)) + "' and file_name='" + fileName
                        + "'and acno<>'" + orgBrCode + "'").getResultList();
            } else {
                fileDataList = em.createNativeQuery("SELECT acno, san, txn_code, at_par, chbook_type, book_size, start_chq_no, end_chq_no, srNo"
                        + " FROM chbook_file_details where date_format(enter_date,'%Y%m%d') <='" + ymd.format(dmy.parse(dt)) + "' and file_name='" + fileName
                        + "' and acno='" + orgBrCode + "'").getResultList();
            }
            if (fileDataList.isEmpty()) {
                throw new ApplicationException("Data does not exist for file generation");
            }
            String genFileName = fileName;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
            Row row = worksheet.createRow(0);
            Cell cell = row.createCell(0);

            if (genFileName.equals("")) {
                if (elem.get(1).toString().equalsIgnoreCase("INDR")) {
                    genFileName = yyyymmdd.format(new Date()) + ".xls";
                } else {
                    genFileName = yyyymmdd.format(new Date()) + ".txt";
                }
            }
            String chkbookfileName = aadharLocation + "chbook/" + genFileName;
            FileWriter fw = new FileWriter(chkbookfileName);

            //header set
            if (elem.get(1).toString().equalsIgnoreCase("INDR")) {
                cell.setCellValue("MicrCode");
                cell = row.createCell(1);
                cell.setCellValue("ACNo");
                cell = row.createCell(2);
                cell.setCellValue("TranscAcno");
                cell = row.createCell(3);
                cell.setCellValue("TranscationId");
                cell = row.createCell(4);
                cell.setCellValue("AcType");
                cell = row.createCell(5);
                cell.setCellValue("AcName");
                cell = row.createCell(6);
                cell.setCellValue("AcAddressLine1");
                cell = row.createCell(7);
                cell.setCellValue("AcAddressLine2");
                cell = row.createCell(8);
                cell.setCellValue("AcAddressLine3");
                cell = row.createCell(9);
                cell.setCellValue("AcCity");
                cell = row.createCell(10);
                cell.setCellValue("AcPincode");
                cell = row.createCell(11);
                cell.setCellValue("AcState");
                cell = row.createCell(12);
                cell.setCellValue("AcCountry");
                cell = row.createCell(13);
                cell.setCellValue("AcPhoneNo");
                cell = row.createCell(14);
                cell.setCellValue("AcForName");
                cell = row.createCell(15);
                cell.setCellValue("AcAuth1");
                cell = row.createCell(16);
                cell.setCellValue("AcAuth2");
                cell = row.createCell(17);
                cell.setCellValue("ChequePerbook");
                cell = row.createCell(18);
                cell.setCellValue("ChequeBookQuantity");
                cell = row.createCell(19);
                cell.setCellValue("FromChequeNo");
                cell = row.createCell(20);
                cell.setCellValue("FromChequeTo");
            }
            int n = 0;
            for (int i = 0; i < fileDataList.size(); i++) {
                Vector fileVect = (Vector) fileDataList.get(i);
                String acno = fileVect.get(0).toString();
                String san = fileVect.get(1).toString();

                String txnCode = fileVect.get(2).toString();
                String atPar = fileVect.get(3).toString();

                String chBookType = fileVect.get(4).toString();
                int bookSize = Integer.parseInt(fileVect.get(5).toString());

                String startChqNo = fileVect.get(6).toString();
                String endChqno = fileVect.get(7).toString();
                long srNo = Long.parseLong(fileVect.get(8).toString());
                //if (!(acno.equals(orgBrCode))) {
                String query = "";
                if (elem.get(1).toString().equalsIgnoreCase("INDR")) {
                    query = "select cd.customerid,cd.custname,ifnull(cd.middle_name,''),ifnull(cd.last_name,''), date_format(cd.dateofbirth,'%d/%m/%Y'),"
                            + "ifnull(cd.fathername,''),ifnull(cd.mailAddressLine1,''),ifnull(cd.mailaddressline2,''),ifnull(cd.mailBlock,''),ifnull(cd.mailVillage,''),"
                            + "(select ref_desc  from cbs_ref_rec_type where ref_rec_no='302'and ref_code = ifnull(cd.mailCountryCode,'')) as country,ifnull(cd.mailPostalCode,''),"
                            + "(select ref_desc  from cbs_ref_rec_type where ref_rec_no='002'and ref_code = ifnull(cd.mailStateCode,''))as state ,"
                            + "c.acno, ab.micr, ab.micrcode,ab.branchcode, jtname1,jtname2,(select Description from codebook where GroupCode='4' and code=am.opermode) as auth1,"
                            + "att.acctnature,ifnull(cd.mobilenumber,''),am.accttype from customerid c, cbs_customer_master_detail cd,accountmaster am, branchmaster b, bnkadd ab,\n"
                            + " accounttypemaster att  where am.acno=c.acno and c.acno = '" + acno + "' and cast(substring(c.acno,1,2) as unsigned) = b.brncode and\n"
                            + " c.custid = cast(cd.customerid as unsigned) and b.AlphaCode = ab.alphacode and am.accttype = att.acctcode";
                } else {
                    if (acno.equals(orgBrCode)) {
                        query = "select '',ab.bankname,'','','','','','','','','','','',concat('" + acno + "',btm.glhead,'01'), ab.micr, ab.micrcode,ab.branchcode,'','','',"
                                + "btm.instnature from  branchmaster b, bnkadd ab, billtypemaster btm  where b.brncode = " + Integer.parseInt(acno) + " and b.AlphaCode = "
                                + "ab.alphacode and btm.instcode='PO'";
                    } else {
                        query = "select cd.customerid,cd.custname,ifnull(cd.middle_name,''),ifnull(cd.last_name,''),  date_format(cd.dateofbirth,'%d/%m/%Y'), \n"
                                + "ifnull(cd.fathername,''),ifnull(cd.mailAddressLine1,''), ifnull(cd.mailaddressline2,''),ifnull(cd.mailBlock,''),ifnull(cd.mailVillage,''), \n"
                                + "ifnull(cd.mailCountryCode,''),ifnull(cd.mailPostalCode,''),ifnull(cd.mailStateCode,''),c.acno, ab.micr, ab.micrcode,ab.branchcode, \n"
                                + "jtname1,jtname2,opermode, att.acctnature from customerid c, cbs_customer_master_detail cd,accountmaster am, branchmaster b, bnkadd ab, \n"
                                + "accounttypemaster att  where am.acno=c.acno and c.acno = '" + acno + "' and cast(substring(c.acno,1,2) as unsigned) = b.brncode and \n"
                                + "c.custid = cast(cd.customerid as unsigned) and b.AlphaCode = ab.alphacode and am.accttype = att.acctcode";
                    }
                }

                List rsList = em.createNativeQuery(query).getResultList();
                if (rsList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                //int seqNo = i + 1;
                Vector rsVect = (Vector) rsList.get(0);

                String name = rsVect.get(1).toString().trim() + " " + rsVect.get(2).toString().trim() + " " + rsVect.get(3).toString().trim();
                name = name.trim();
                name = name.length() > 50 ? name.substring(0, 50) : name;

                //String address = rsVect.get(6).toString().trim() + rsVect.get(7).toString().trim() + rsVect.get(8).toString().trim();
                String add1 = rsVect.get(6).toString().trim();
                add1 = add1.length() > 50 ? add1.substring(0, 50) : add1;

                String add2 = rsVect.get(7).toString().trim();
                add2 = add2.length() > 50 ? add2.substring(0, 50) : add2;

                String add3 = rsVect.get(8).toString().trim();
                add3 = add3.length() > 50 ? add3.substring(0, 50) : add3;

                String city = rsVect.get(9).toString().trim();
                city = city.length() > 30 ? city.substring(0, 30) : city;

                String pin = rsVect.get(11).toString().trim();
                pin = pin.length() > 6 ? pin.substring(0, 6) : pin;

                String acState = rsVect.get(12).toString().trim();
                acState = acState.length() > 20 ? acState.substring(0, 20) : acState;

                String acCountry = rsVect.get(10).toString().trim();
                acCountry = acCountry.length() > 5 ? acCountry.substring(0, 4) : acCountry;

                String jtNameOne = rsVect.get(17).toString().trim();
                jtNameOne = jtNameOne.length() > 50 ? jtNameOne.substring(0, 50) : jtNameOne;

                String jtNameTwo = rsVect.get(18).toString().trim();
                jtNameTwo = jtNameTwo.length() > 50 ? jtNameTwo.substring(0, 50) : jtNameTwo;

                String acAuth1 = rsVect.get(19).toString().trim();

                String acNature = rsVect.get(20).toString().trim();
                String signAuth = "";
                String newAcNo = rsVect.get(13).toString().trim();
//                    if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                        signAuth = "Authorized Signatory";
//                    }
                String micr = CbsUtil.lPadding(3, Integer.parseInt(rsVect.get(14).toString().trim())) + CbsUtil.lPadding(3, Integer.parseInt(rsVect.get(15).toString().trim()))
                        + CbsUtil.lPadding(3, Integer.parseInt(rsVect.get(16).toString().trim()));

                String individualStr = "";
                String startChequeNo = startChqNo, endChequeNo = endChqno;
                if (elem.get(1).toString().equalsIgnoreCase("INDR")) {
                    String acMobileNo = rsVect.get(21).toString().trim();
                    acMobileNo = acMobileNo.length() > 10 ? acMobileNo.substring(0, 10) : acMobileNo;

                    // String actype = rsVect.get(22).toString().trim();
                    // actype = actype.length() > 2 ? actype.substring(0, 2) : actype;
                    for (int j = startChequeNo.length(); j < 6; j++) {
                        startChequeNo = "0" + startChequeNo;
                    }
                    for (int j = endChequeNo.length(); j < 6; j++) {
                        endChequeNo = "0" + endChequeNo;
                    }

                    row = worksheet.createRow(i + 1);
                    cell = row.createCell(0);
                    String micr1 = CbsUtil.lPadding(3, Integer.parseInt(rsVect.get(14).toString().trim())) + CbsUtil.lPadding(3, Integer.parseInt(rsVect.get(15).toString().trim()))
                            + CbsUtil.lPadding(3, Integer.parseInt(rsVect.get(16).toString().trim()));
                    cell.setCellValue(micr1);

                    cell = row.createCell(1);
                    cell.setCellValue(newAcNo);

                    cell = row.createCell(2);
                    cell.setCellValue(san);

                    cell = row.createCell(3);
                    cell.setCellValue(txnCode);

                    cell = row.createCell(4);
                    cell.setCellValue(acNature);

                    cell = row.createCell(5);
                    cell.setCellValue(name);

                    cell = row.createCell(6);
                    cell.setCellValue(add1);

                    cell = row.createCell(7);
                    cell.setCellValue(add2);

                    cell = row.createCell(8);
                    cell.setCellValue(add3);

                    cell = row.createCell(9);
                    cell.setCellValue(city);

                    cell = row.createCell(10);
                    cell.setCellValue(pin);

                    cell = row.createCell(11);
                    cell.setCellValue(acState);

                    cell = row.createCell(12);
                    cell.setCellValue(acCountry);

                    cell = row.createCell(13);
                    cell.setCellValue(acMobileNo);

                    cell = row.createCell(14);
                    cell.setCellValue(name);

                    cell = row.createCell(15);
                    cell.setCellValue(acAuth1);

                    cell = row.createCell(16);
                    cell.setCellValue("");

                    cell = row.createCell(17);
                    cell.setCellValue(bookSize);

                    cell = row.createCell(18);
                    cell.setCellValue("1");

                    cell = row.createCell(19);
                    cell.setCellValue(startChequeNo);

                    cell = row.createCell(20);
                    cell.setCellValue(endChequeNo);
                } else {
                    individualStr = srNo + "~" + micr.substring(0, 3) + "~" + micr.substring(3, 6) + "~" + micr.substring(6, 9) + "~" + micr
                            + "~" + san + "~" + newAcNo + "~" + txnCode + "~" + name + "~" + jtNameOne + "~" + jtNameTwo + "~" + signAuth + "~~~"
                            + add1 + "~" + add2 + "~" + add3 + "~" + city + "~" + pin + "~1~" + bookSize + "~" + chBookType + "~" + atPar + "~"
                            + startChqNo + "~" + endChqno + "\n";
                    fw.write(individualStr);
                }
                //Updation of flag in table cbs_npci_ecs_input_txn.

                if (elem.get(1).toString().equalsIgnoreCase("INDR")) {
                    //update chbook_file_details set file_name ='file', file_gen_by='vandanna', file_date = now() ,start_chq_no ='000160',end_chq_no='000180' where acno='022004579301' and start_chq_no ='161' and end_chq_no='180' and file_name = ''

                    n = em.createNativeQuery("update chbook_file_details set file_name ='" + genFileName + "',file_gen_by='" + userName + "',file_date = now(),"
                            + "start_chq_no ='" + startChequeNo + "',end_chq_no='" + endChequeNo + "' where acno='" + acno + "' and start_chq_no ='" + startChqNo + "' and end_chq_no='" + endChqno + "' and file_name = '" + fileName + "'").executeUpdate();
                } else {
                    n = em.createNativeQuery("update chbook_file_details set file_name ='" + genFileName + "', file_gen_by='" + userName + "', file_date = now() "
                            + "where acno='" + acno + "' and start_chq_no ='" + startChqNo + "' and end_chq_no='" + endChqno + "' and file_name = '" + fileName + "'").executeUpdate();
                }
                if (n <= 0) {
                    throw new ApplicationException("Updation problem in file generation.");
                }
                // }
            }
            fw.close();
            if (elem.get(1).toString().equalsIgnoreCase("INDR")) { //INDR for Indraprastha
                FileOutputStream fileOut = new FileOutputStream(chkbookfileName);
                workbook.write(fileOut);
                fileOut.close();
            }

            ut.commit();
            return "File has been generated successfully.";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public List<String> getGeneratedFilesName(String instType, String dt, String brCode) throws ApplicationException {
        List<String> dataList = new ArrayList<String>();
        try {
            List list = null;
            if (instType.equals("1")) {
                list = em.createNativeQuery("select distinct file_name from chbook_file_details where date_format(file_date,'%Y%m%d') = '"
                        + ymd.format(dmy.parse(dt)) + "' and file_name <>'' and acno<>'" + brCode + "'").getResultList();
            } else {
                list = em.createNativeQuery("select distinct file_name from chbook_file_details where date_format(file_date,'%Y%m%d') = '"
                        + ymd.format(dmy.parse(dt)) + "' and file_name <>'' and acno='" + brCode + "'").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                dataList.add(ele.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<NpciFileDto> showGeneratedFiles(String instType, String fileName, String dt, String brCode) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = null;
            if (instType.equals("1")) {
                list = em.createNativeQuery("select distinct file_name, date_format(file_date,'%d/%m/%Y'),file_gen_by from chbook_file_details where "
                        + "date_format(file_date,'%Y%m%d')='" + ymd.format(dmy.parse(dt)) + "' and file_name<>'' and acno<>'" + brCode + "'").getResultList();
            } else {
                list = em.createNativeQuery("select distinct file_name, date_format(file_date,'%d/%m/%Y'),file_gen_by from chbook_file_details where "
                        + "date_format(file_date,'%Y%m%d')='" + ymd.format(dmy.parse(dt)) + "' and file_name<>'' and acno='" + brCode + "'").getResultList();
            }

            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                Vector ele = (Vector) list.get(i);

                dto.setFileNo(new BigInteger(String.valueOf(i + 1)));
                dto.setFileGenDt(ele.get(1).toString());
                dto.setFileName(ele.get(0).toString());
                dto.setFileGenBy(ele.get(2).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getCustImgeDetails(String imgType, String custId) throws ApplicationException {
        try {
            if (imgType.equals("S")) {
                return em.createNativeQuery("select NewAcNo,SrNo,EnterBy,image,'Signature' from cbs_cust_image_detail where auth='Y' and NewAcNo='"
                        + custId + "'").getResultList();
            } else {
                return em.createNativeQuery("select NewAcNo,SrNo,EnterBy,substring(image,8,2),REF_DESC from cbs_cust_image_detail ci, cbs_customer_master_detail cm,"
                        + "cbs_ref_rec_type cr where cm.customerid =ci.newacno and ci.auth='Y' and cm.customerid = '" + custId + "' and cr.REF_REC_NO ='362' "
                        + "and REF_CODE = substring(image,8,2)").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
