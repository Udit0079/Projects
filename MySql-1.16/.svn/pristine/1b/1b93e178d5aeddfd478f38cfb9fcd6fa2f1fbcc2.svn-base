/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.dto.AtmCardMappGrid;
import com.cbs.dto.AtmSecondryAccountDetail;
import com.cbs.dto.CustomerDetail;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.misc.ImpsOutwardDetailPojo;
import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.dto.report.CardFillingReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.H2HMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.utils.ParseFileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Stateless(mappedName = "MiscMgmtFacadeS1")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class MiscMgmtFacadeS1 implements MiscMgmtFacadeS1Remote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    CommonReportMethodsRemote commonReportRemote;
    @EJB
    H2HMgmtFacadeRemote h2hRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dMMMy = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat ddMMyy = new SimpleDateFormat("ddMMyyhhmm");

//    @Override
//    public List<AtmCardMappGrid> getAtmAccountDetail(String accountNo, String cardNo) throws Exception {
//        List<AtmCardMappGrid> dataList = new ArrayList<>();
//        try {
//            List list = em.createNativeQuery("select p.acno,p.card_no,p.min_limit,p.del_flag,p.card_status,p.txn_limit_type,"
//                    + "p.withdrawal_limit_count,p.encoding_name,p.embossing_name,p.card_type,p.txn_limit_card_type,p.card_relationship,"
//                    + "p.service_code,p.spouse_name,p.kcc_emv_type,p.institution_id,ifnull(s.card_no,''),ifnull(s.secondary_acno,''),"
//                    + "ifnull(s.txn_limit_type,''),ifnull(s.trf_limit_amount,0),ifnull(s.trf_limit_count,0) from "
//                    + "atm_card_master p LEFT JOIN atm_secondary_card_master s ON p.acno=s.primary_acno and p.card_no=s.card_no where "
//                    + "p.acno='" + accountNo + "' and p.card_no='" + cardNo + "'").getResultList();
//            if (list.isEmpty()) {
//                throw new Exception("There is no data.");
//            }
//            List<AtmSecondryAccountDetail> secondaryList = new ArrayList<>();
//            AtmCardMappGrid primaryObj = new AtmCardMappGrid();
//            for (int i = 0; i < list.size(); i++) {
//                AtmSecondryAccountDetail secondaryObj = new AtmSecondryAccountDetail();
//                Vector ele = (Vector) list.get(i);
//
//                primaryObj.setAcNo(ele.get(0).toString().trim());
//                primaryObj.setCardNo(ele.get(1).toString().trim());
//                primaryObj.setMinLmt(ele.get(2).toString().trim());
//                primaryObj.setStatus(ele.get(3).toString().trim());
//                primaryObj.setCardStatus(ele.get(4).toString().trim());
//                primaryObj.setTxnLimitType(ele.get(5).toString().trim());
//                primaryObj.setCommonLimitCount(ele.get(6).toString().trim());
//                primaryObj.setEncodingName(ele.get(7).toString().trim());
//                primaryObj.setEmbossingName(ele.get(8).toString().trim());
//                primaryObj.setCardType(ele.get(9).toString().trim());
//                primaryObj.setTxnLimitCardType(ele.get(10).toString().trim());
//                primaryObj.setCardRelationship(ele.get(11).toString().trim());
//                primaryObj.setServiceCode(ele.get(12).toString().trim());
//                primaryObj.setSpouseName(ele.get(13).toString().trim());
//                primaryObj.setKccEmvType(ele.get(14).toString().trim());
//                primaryObj.setInstitutionId(ele.get(15).toString().trim());
//
////                if (!ele.get(16).toString().trim().equals("") && !ele.get(17).toString().trim().equals("")
////                        && !ele.get(18).toString().trim().equals("") && Double.parseDouble(ele.get(19).toString().trim()) != 0
////                        && Integer.parseInt(ele.get(20).toString().trim()) != 0) {
////                    secondaryObj.setPrimaryAccount(ele.get(0).toString().trim());
////                    secondaryObj.setCardNo(ele.get(16).toString().trim());
////                    secondaryObj.setSecondaryAccount(ele.get(17).toString().trim());
////                    secondaryObj.setTxnLimitType(ele.get(18).toString().trim());
////                    secondaryObj.setCommonLimitAmount(new BigDecimal(ele.get(19).toString().trim()));
////                    secondaryObj.setCommonLimitCount(Integer.parseInt(ele.get(20).toString().trim()));
////
////                    secondaryList.add(secondaryObj);
////                }
//
//
//                if (!ele.get(17).toString().trim().equals("") && !ele.get(18).toString().trim().equals("")
//                        && Double.parseDouble(ele.get(19).toString().trim()) != 0) {
//                    secondaryObj.setPrimaryAccount(ele.get(0).toString().trim());
//                    secondaryObj.setCardNo(ele.get(16).toString().trim());
//                    secondaryObj.setSecondaryAccount(ele.get(17).toString().trim());
//                    secondaryObj.setTxnLimitType(ele.get(18).toString().trim());
//                    secondaryObj.setCommonLimitAmount(new BigDecimal(ele.get(19).toString().trim()));
//                    secondaryObj.setCommonLimitCount(Integer.parseInt(ele.get(20).toString().trim()));
//
//                    secondaryList.add(secondaryObj);
//                }
//
//            }
//            //Check for no secondary, 1 secondary, 2 secondary
//            primaryObj.setSecondryAccounts(secondaryList);
//            dataList.add(primaryObj);
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//        return dataList;
//    }
    @Override
    public List<AtmCardMappGrid> getAtmAccountDetail(String accountNo, String cardNo) throws Exception {
        List<AtmCardMappGrid> dataList = new ArrayList<>();
        try {
            List list = em.createNativeQuery("select p.acno,p.card_no,p.min_limit,p.del_flag,p.card_status,p.txn_limit_type,p.withdrawal_limit_amount,"
                    + "p.withdrawal_limit_count,p.purchase_limit_amount,p.purchase_limit_count,p.encoding_name,p.embossing_name,p.card_type,p.txn_limit_card_type,p.card_relationship,"
                    + "p.service_code,p.spouse_name,p.kcc_emv_type,p.institution_id,ifnull(s.card_no,''),ifnull(s.secondary_acno,''),"
                    + "ifnull(s.txn_limit_type,''),ifnull(s.withdrawal_limit_amount,0),ifnull(s.withdrawal_limit_count,0),ifnull(s.purchase_limit_amount,0),ifnull(s.purchase_limit_count,0) from "
                    + "atm_card_master p LEFT JOIN atm_secondary_card_master s ON p.acno=s.primary_acno and p.card_no=s.card_no where "
                    + "p.acno='" + accountNo + "' and p.card_no='" + cardNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data.");
            }
            List<AtmSecondryAccountDetail> secondaryList = new ArrayList<>();
            AtmCardMappGrid primaryObj = new AtmCardMappGrid();
            for (int i = 0; i < list.size(); i++) {
                AtmSecondryAccountDetail secondaryObj = new AtmSecondryAccountDetail();
                Vector ele = (Vector) list.get(i);

                primaryObj.setAcNo(ele.get(0).toString().trim());
                primaryObj.setCardNo(ele.get(1).toString().trim());
                primaryObj.setMinLmt(ele.get(2).toString().trim());
                primaryObj.setStatus(ele.get(3).toString().trim());
                primaryObj.setCardStatus(ele.get(4).toString().trim());
                primaryObj.setTxnLimitType(ele.get(5).toString().trim());
//                primaryObj.setCommonLimitCount(ele.get(6).toString().trim());
                primaryObj.setWithdrawalLimitAmount(new BigDecimal(ele.get(6).toString().trim()));
                primaryObj.setWithdrawalLimitCount(Integer.parseInt(ele.get(7).toString().trim()));
                primaryObj.setPurchaseLimitAmount(new BigDecimal(ele.get(8).toString().trim()));
                primaryObj.setPurchaseLimitCount(Integer.parseInt(ele.get(9).toString().trim()));
                primaryObj.setEncodingName(ele.get(10).toString().trim());
                primaryObj.setEmbossingName(ele.get(11).toString().trim());
                primaryObj.setCardType(ele.get(12).toString().trim());
                primaryObj.setTxnLimitCardType(ele.get(13).toString().trim());
                primaryObj.setCardRelationship(ele.get(14).toString().trim());
                primaryObj.setServiceCode(ele.get(15).toString().trim());
                primaryObj.setSpouseName(ele.get(16).toString().trim());
                primaryObj.setKccEmvType(ele.get(17).toString().trim());
                primaryObj.setInstitutionId(ele.get(18).toString().trim());

//                if (!ele.get(16).toString().trim().equals("") && !ele.get(17).toString().trim().equals("")
//                        && !ele.get(18).toString().trim().equals("") && Double.parseDouble(ele.get(19).toString().trim()) != 0
//                        && Integer.parseInt(ele.get(20).toString().trim()) != 0) {
//                    secondaryObj.setPrimaryAccount(ele.get(0).toString().trim());
//                    secondaryObj.setCardNo(ele.get(16).toString().trim());
//                    secondaryObj.setSecondaryAccount(ele.get(17).toString().trim());
//                    secondaryObj.setTxnLimitType(ele.get(18).toString().trim());
//                    secondaryObj.setCommonLimitAmount(new BigDecimal(ele.get(19).toString().trim()));
//                    secondaryObj.setCommonLimitCount(Integer.parseInt(ele.get(20).toString().trim()));
//
//                    secondaryList.add(secondaryObj);
//                }


                if (!ele.get(20).toString().trim().equals("")) {
                    secondaryObj.setPrimaryAccount(ele.get(0).toString().trim());
                    secondaryObj.setCardNo(ele.get(19).toString().trim());
                    secondaryObj.setSecondaryAccount(ele.get(20).toString().trim());
                    secondaryObj.setTxnLimitType(ele.get(21).toString().trim());
//                    secondaryObj.setCommonLimitAmount(new BigDecimal(ele.get(22).toString().trim()));
                    secondaryObj.setWithdrawalLimitAmount(new BigDecimal(ele.get(22).toString().trim()));
                    secondaryObj.setWithdrawalLimitCount(Integer.parseInt(ele.get(23).toString().trim()));
                    secondaryObj.setPurchaseLimitAmount(new BigDecimal(ele.get(24).toString().trim()));
                    secondaryObj.setPurchaseLimitCount(Integer.parseInt(ele.get(25).toString().trim()));
//                    secondaryObj.setCommonLimitCount(Integer.parseInt(ele.get(23).toString().trim()));

                    secondaryList.add(secondaryObj);
                }

            }
            //Check for no secondary, 1 secondary, 2 secondary
            primaryObj.setSecondryAccounts(secondaryList);
            dataList.add(primaryObj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

//    @Override
//    public List<AtmSecondryAccountDetail> getAtmSecondaryAccountDetail(String accountNo, String cardNo) throws Exception {
//        List<AtmSecondryAccountDetail> secondaryList = new ArrayList<>();
//        try {
//            List list = em.createNativeQuery("select primary_acno,card_no,secondary_acno,txn_limit_type,withdrawal_limit_amount,withdrawal_limit_count,purchase_limit_amount,"
//                    + "purchase_limit_count,trf_limit_amount,trf_limit_count from atm_secondary_card_master where "
//                    + " primary_acno='" + accountNo + "' and card_no='" + cardNo + "'").getResultList();
//            if (list.isEmpty()) {
//                throw new Exception("There is no data.");
//            }
//
//            for (int i = 0; i < list.size(); i++) {
//                AtmSecondryAccountDetail primaryobj = new AtmSecondryAccountDetail();
//                Vector ele = (Vector) list.get(i);
//                primaryobj.setPrimaryAccount(ele.get(0).toString());
//                primaryobj.setCardNo(ele.get(1).toString());
//                primaryobj.setSecondaryAccount(ele.get(2).toString());
//                primaryobj.setTxnLimitType(ele.get(3).toString());
//                primaryobj.setWithdrawalLimitAmount(new BigDecimal(ele.get(4).toString()));
//                primaryobj.setWithdrawalLimitCount(Integer.parseInt(ele.get(5).toString()));
//                primaryobj.setPurchaseLimitAmount(new BigDecimal(ele.get(6).toString()));
//                primaryobj.setPurchaseLimitCount(Integer.parseInt(ele.get(7).toString()));
//                primaryobj.setTrfLimitAmount(new BigDecimal(ele.get(8).toString()));
//                primaryobj.setTrfLimitCount(Integer.parseInt(ele.get(9).toString()));
//
//                secondaryList.add(primaryobj);
//            }
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//        return secondaryList;
//    }
    @Override
    public CustomerDetail getCustomerDetailByAccountNo(String accountNo) throws Exception {
        CustomerDetail obj = new CustomerDetail();
        try {
            List list = em.createNativeQuery("select customerid,ifnull(gender,''),custfullname,date_format(dateofbirth,'%d/%m/%Y'),"
                    + "ifnull(mailaddressline1,''),ifnull(mailaddressline2,''),ifnull(mailvillage,''),ifnull(mailblock,''),"
                    + "ifnull(mailstatecode,''),ifnull(mailpostalcode,''),ifnull(mobilenumber,'') from cbs_customer_master_detail a,"
                    + "customerid i where a.customerid=i.custid and i.acno='" + accountNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no customer data related to this account no." + accountNo);
            }

            Vector ele = (Vector) list.get(0);
            obj.setCustomerId(ele.get(0).toString().trim());
            obj.setGender(commonReportRemote.getRefRecDesc("012", ele.get(1).toString().trim()));
            obj.setCustomerName(ele.get(2).toString().trim());
            obj.setDateOfBirth(ele.get(3).toString().trim());
            obj.setMailAddress(ele.get(4).toString().trim());
            obj.setGenderCode(ele.get(1).toString().trim());
            obj.setMailAddressOne(ele.get(5).toString().trim());
            obj.setMailAddressTwo(ele.get(6).toString().trim());
            obj.setMailAddressThree(ele.get(7).toString().trim());
            obj.setMailAddressFour(ele.get(8).toString().trim() + " " + ele.get(9).toString().trim());
            obj.setMobileNo(ele.get(10).toString().trim());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }

    public String insertPrimaryAtmCardHistory(String primaryAccountNo, String cardNo) throws Exception {
        try {
            List list = em.createNativeQuery("select ifnull(max(sno),0)+1 from atm_card_master_his").getResultList();
            Vector vec = (Vector) list.get(0);
            BigInteger sNo = new BigInteger(vec.get(0).toString());

            int n = em.createNativeQuery("INSERT INTO atm_card_master_his (sno,acno, card_no, issue_dt, min_limit, verify, "
                    + "verify_by, del_flag, enter_by, lastUpdateBy, lastUpdateDate, trantime, file_type, card_status, "
                    + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, purchase_limit_count, "
                    + "trf_limit_amount, trf_limit_count, encoding_name, embossing_name, registration_dt, card_type, "
                    + "txn_limit_card_type, card_relationship, service_code, spouse_name, kcc_emv_type, chn, institution_id) "
                    + "SELECT " + sNo + ",acno, card_no, issue_dt, min_limit, verify,verify_by, del_flag, enter_by, lastUpdateBy, "
                    + "lastUpdateDate, trantime, file_type, card_status,txn_limit_type, withdrawal_limit_amount, "
                    + "withdrawal_limit_count, purchase_limit_amount, purchase_limit_count,trf_limit_amount, trf_limit_count, "
                    + "encoding_name, embossing_name, registration_dt, card_type,txn_limit_card_type, card_relationship, "
                    + "service_code, spouse_name, kcc_emv_type, chn, institution_id FROM atm_card_master WHERE "
                    + "acno='" + primaryAccountNo.trim() + "' and card_no='" + cardNo + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem In Card History Maintenance.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

//    @Override
//    public String processAddAndModifyFirstSeqFile(String fileType, String delFlag, String primaryAccountNo, String txnLimitType,
//            String limitAmount, String limitCount, String encodingName, String embossingName, String cardType,
//            String txnLimitCardType, String cardRelationship, String serviceCode, String kccEmvType,
//            List<AtmSecondryAccountDetail> secondaryTable, String userName) throws Exception {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            List chkList = em.createNativeQuery("select acno from atm_card_master where acno='" + primaryAccountNo + "' and "
//                    + "card_no=''").getResultList();
//            if (delFlag.equalsIgnoreCase("A") && !chkList.isEmpty()) {
//                throw new Exception("This detail is already exists.");
//            } else if (delFlag.equalsIgnoreCase("M") && chkList.isEmpty()) {
//                throw new Exception("This is no such detail to modify.");
//            }
//            if (delFlag.equalsIgnoreCase("A")) {
//                int n = em.createNativeQuery("INSERT INTO atm_card_master (acno, card_no, issue_dt, min_limit, verify, verify_by, "
//                        + "del_flag, enter_by, lastUpdateBy, lastUpdateDate, trantime, file_type, card_status, txn_limit_type, "
//                        + "withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, purchase_limit_count, "
//                        + "trf_limit_amount, trf_limit_count, encoding_name, embossing_name, registration_dt, card_type, "
//                        + "txn_limit_card_type, card_relationship, service_code, spouse_name, kcc_emv_type, chn, institution_id) "
//                        + "VALUES "
//                        + "('" + primaryAccountNo.trim() + "', '', current_timestamp, " + Double.parseDouble(limitAmount) + ", '', '', "
//                        + "'" + delFlag.trim() + "', '" + userName + "', '', current_timestamp, current_timestamp, "
//                        + "'" + fileType.trim() + "', '1', '" + txnLimitType.trim() + "', " + Double.parseDouble(limitAmount) + ", "
//                        + "" + Integer.parseInt(limitCount) + ", " + Double.parseDouble(limitAmount) + ", "
//                        + "" + Integer.parseInt(limitCount) + ", " + Double.parseDouble(limitAmount) + ", "
//                        + "" + Integer.parseInt(limitCount) + ", '" + encodingName.trim() + "', '" + embossingName.trim() + "', "
//                        + "current_timestamp, '" + cardType.trim() + "', '" + txnLimitCardType.trim() + "', "
//                        + "'" + cardRelationship.trim() + "', '" + serviceCode.trim() + "', '', '" + kccEmvType.trim() + "', "
//                        + "'', '')").executeUpdate();
//                if (n <= 0) {
//                    throw new Exception("There is issue in card mapping.");
//                }
//                //Addition of secondary accounts.
//                for (AtmSecondryAccountDetail obj : secondaryTable) {
//                    chkList = em.createNativeQuery("select primary_acno from atm_secondary_card_master where "
//                            + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
//                            + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").getResultList();
//                    if (!chkList.isEmpty()) {
//                        throw new Exception("This secondary detail is already exists.");
//                    }
//                    n = em.createNativeQuery("INSERT INTO atm_secondary_card_master (primary_acno, card_no, secondary_acno, "
//                            + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
//                            + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
//                            + "VALUES "
//                            + "('" + primaryAccountNo.trim() + "', '', '" + obj.getSecondaryAccount().trim() + "', "
//                            + "'" + obj.getTxnLimitType().trim() + "', " + obj.getCommonLimitAmount() + ", "
//                            + "" + obj.getCommonLimitCount() + ", " + obj.getCommonLimitAmount() + ", " + obj.getCommonLimitCount() + ", "
//                            + "" + obj.getCommonLimitAmount() + ", " + obj.getCommonLimitCount() + ")").executeUpdate();
//                    if (n <= 0) {
//                        throw new Exception("There is issue in secondary account mapping.");
//                    }
//                }
//            } else if (delFlag.equalsIgnoreCase("M")) {
//                insertPrimaryAtmCardHistory(primaryAccountNo.trim(), "");
//
//                int n = em.createNativeQuery("UPDATE atm_card_master set min_limit=" + Double.parseDouble(limitAmount) + ","
//                        + "del_flag='" + delFlag + "',lastUpdateBy='" + userName + "',lastUpdateDate=current_timestamp,"
//                        + "txn_limit_type='" + txnLimitType + "',withdrawal_limit_amount=" + new BigDecimal(limitAmount) + ","
//                        + "withdrawal_limit_count=" + Integer.parseInt(limitCount) + ","
//                        + "purchase_limit_amount=" + new BigDecimal(limitAmount) + ","
//                        + "purchase_limit_count=" + Integer.parseInt(limitCount) + ","
//                        + "trf_limit_amount=" + new BigDecimal(limitAmount) + ",trf_limit_count=" + Integer.parseInt(limitCount) + ","
//                        + "encoding_name='" + encodingName.trim() + "',embossing_name='" + embossingName.trim() + "',"
//                        + "card_type='" + cardType.trim() + "',txn_limit_card_type='" + txnLimitCardType.trim() + "',"
//                        + "card_relationship='" + cardRelationship.trim() + "',service_code='" + serviceCode.trim() + "',"
//                        + "kcc_emv_type='" + kccEmvType.trim() + "' WHERE acno='" + primaryAccountNo.trim() + "' and "
//                        + "card_no=''").executeUpdate();
//                if (n <= 0) {
//                    throw new Exception("Problem In Card Detail Updation.");
//                }
//
////                for (AtmSecondryAccountDetail obj : secondaryTable) {
////                    chkList = em.createNativeQuery("select primary_acno from atm_secondary_card_master where "
////                            + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
////                            + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").getResultList();
////                    if (!chkList.isEmpty()) {
////                        n = em.createNativeQuery("INSERT INTO atm_secondary_card_master_his (primary_acno, card_no, secondary_acno, "
////                                + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
////                                + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
////                                + "VALUES "
////                                + "(SELECT primary_acno, card_no, secondary_acno,txn_limit_type, withdrawal_limit_amount, "
////                                + "withdrawal_limit_count, purchase_limit_amount,purchase_limit_count, trf_limit_amount, "
////                                + "trf_limit_count FROM atm_secondary_card_master WHERE primary_acno='" + primaryAccountNo.trim() + "' and "
////                                + "card_no='' and secondary_acno='" + obj.getSecondaryAccount().trim() + "')").executeUpdate();
////                        if (n <= 0) {
////                            throw new Exception("Problem In Secondary History Maintenance.");
////                        }
////                        //Update the record
////                        n = em.createNativeQuery("UPDATE atm_secondary_card_master set "
////                                + "txn_limit_type='" + obj.getTxnLimitType().trim() + "',"
////                                + "withdrawal_limit_amount=" + obj.getCommonLimitAmount() + ","
////                                + "withdrawal_limit_count=" + obj.getCommonLimitCount() + ","
////                                + "purchase_limit_amount=" + obj.getCommonLimitAmount() + ","
////                                + "purchase_limit_count=" + obj.getCommonLimitCount() + ","
////                                + "trf_limit_amount=" + obj.getCommonLimitAmount() + ","
////                                + "trf_limit_count=" + obj.getCommonLimitCount() + " where "
////                                + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
////                                + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").executeUpdate();
////                        if (n <= 0) {
////                            throw new Exception("Problem In Secondary Accounts Updation.");
////                        }
////                    } else {
////                        n = em.createNativeQuery("INSERT INTO atm_secondary_card_master (primary_acno, card_no, secondary_acno, "
////                                + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
////                                + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
////                                + "VALUES "
////                                + "('" + primaryAccountNo.trim() + "', '', '" + obj.getSecondaryAccount().trim() + "', "
////                                + "'" + obj.getTxnLimitType().trim() + "', " + obj.getCommonLimitAmount() + ", "
////                                + "" + obj.getCommonLimitCount() + ", " + obj.getCommonLimitAmount() + ", " + obj.getCommonLimitCount() + ", "
////                                + "" + obj.getCommonLimitAmount() + ", " + obj.getCommonLimitCount() + ")").executeUpdate();
////                        if (n <= 0) {
////                            throw new Exception("There is issue in secondary account mapping.");
////                        }
////                    }
////                }
//            }
//            ut.commit();
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                throw new Exception(ex.getMessage());
//            } catch (Exception e) {
//                throw new Exception(e.getMessage());
//            }
//        }
//        return "true";
//    }
    @Override
    public String processAddAndModifyFirstSeqFile(String fileType, String delFlag, String primaryAccountNo, String txnLimitType,
            String encodingName, String embossingName, String cardType, String txnLimitCardType, String cardRelationship, String serviceCode, String kccEmvType,
            List<AtmSecondryAccountDetail> secondaryTable, String userName, String wdrlLimitAmount, String wdrlLimitCount,
            String purLimitAmount, String purLimitCount, String minLimit, String secondaryacNo) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkList = em.createNativeQuery("select acno from atm_card_master where acno='" + primaryAccountNo + "' and "
                    + "card_no=''").getResultList();
            if (delFlag.equalsIgnoreCase("A") && !chkList.isEmpty()) {
                throw new Exception("This detail is already exists.");
            } else if (delFlag.equalsIgnoreCase("M") && chkList.isEmpty()) {
                throw new Exception("This is no such detail to modify.");
            }
            if (delFlag.equalsIgnoreCase("A")) {
                int n = em.createNativeQuery("INSERT INTO atm_card_master (acno, card_no, issue_dt, min_limit, verify, verify_by, "
                        + "del_flag, enter_by, lastUpdateBy, lastUpdateDate, trantime, file_type, card_status, txn_limit_type, "
                        + "withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, purchase_limit_count, "
                        + "trf_limit_amount, trf_limit_count, encoding_name, embossing_name, registration_dt, card_type, "
                        + "txn_limit_card_type, card_relationship, service_code, spouse_name, kcc_emv_type, chn, institution_id) "
                        + "VALUES "
                        + "('" + primaryAccountNo.trim() + "', '', current_timestamp, " + Double.parseDouble(minLimit) + ", 'N', '', "
                        + "'" + delFlag.trim() + "', '" + userName + "', '" + userName + "', current_timestamp, current_timestamp, "
                        + "'" + fileType.trim() + "', '1', '" + txnLimitType.trim() + "', " + Double.parseDouble(wdrlLimitAmount) + ", "
                        + "" + Integer.parseInt(wdrlLimitCount) + ", " + Double.parseDouble(purLimitAmount) + ", "
                        + "" + Integer.parseInt(purLimitCount) + ",'0', "
                        + " '0', '" + encodingName.trim() + "', '" + embossingName.trim() + "', "
                        + "current_timestamp, '" + cardType.trim() + "', '" + txnLimitCardType.trim() + "', "
                        + "'" + cardRelationship.trim() + "', '" + serviceCode.trim() + "', '', '" + kccEmvType.trim() + "', "
                        + "'', '')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("There is issue in card mapping.");
                }
                //Addition of secondary accounts.
                for (AtmSecondryAccountDetail obj : secondaryTable) {
                    chkList = em.createNativeQuery("select primary_acno from atm_secondary_card_master where "
                            + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
                            + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").getResultList();
                    if (!chkList.isEmpty()) {
                        throw new Exception("This secondary detail is already exists.");
                    }
                    n = em.createNativeQuery("INSERT INTO atm_secondary_card_master (primary_acno, card_no, secondary_acno, "
                            + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
                            + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
                            + "VALUES "
                            + "('" + primaryAccountNo.trim() + "', '', '" + obj.getSecondaryAccount().trim() + "', "
                            + "'" + obj.getTxnLimitType().trim() + "', " + obj.getWithdrawalLimitAmount() + ", "
                            + "" + obj.getWithdrawalLimitCount() + ", " + obj.getPurchaseLimitAmount() + ", " + obj.getPurchaseLimitCount() + ", "
                            + "'0','0')").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("There is issue in secondary account mapping.");
                    }
                }
            } else if (delFlag.equalsIgnoreCase("M")) {
                insertPrimaryAtmCardHistory(primaryAccountNo.trim(), "");

                int n = em.createNativeQuery("UPDATE atm_card_master set min_limit=" + Double.parseDouble(minLimit) + ", verify='N', verify_by='',"
                        + "del_flag='" + delFlag + "',lastUpdateBy='" + userName + "',lastUpdateDate=current_timestamp,"
                        + "txn_limit_type='" + txnLimitType + "',withdrawal_limit_amount=" + new BigDecimal(wdrlLimitAmount) + ","
                        + "withdrawal_limit_count=" + Integer.parseInt(wdrlLimitCount) + ","
                        + "purchase_limit_amount=" + new BigDecimal(purLimitAmount) + ","
                        + "purchase_limit_count=" + Integer.parseInt(purLimitCount) + ","
                        + "trf_limit_amount='0',trf_limit_count='0',"
                        + "encoding_name='" + encodingName.trim() + "',embossing_name='" + embossingName.trim() + "',"
                        + "card_type='" + cardType.trim() + "',txn_limit_card_type='" + txnLimitCardType.trim() + "',"
                        + "card_relationship='" + cardRelationship.trim() + "',service_code='" + serviceCode.trim() + "',"
                        + "kcc_emv_type='" + kccEmvType.trim() + "' WHERE acno='" + primaryAccountNo.trim() + "' and "
                        + "card_no=''").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Card Detail Updation.");
                }
                if (!secondaryTable.isEmpty()) {
                    for (AtmSecondryAccountDetail obj : secondaryTable) {
                        chkList = em.createNativeQuery("select secondary_acno from atm_secondary_card_master where "
                                + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
                                + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").getResultList();
                        if (chkList.equals(obj.getSecondaryAccount())) {
                            n = em.createNativeQuery("INSERT INTO atm_secondary_card_master_his (primary_acno, card_no, secondary_acno, "
                                    + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
                                    + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
                                    + "SELECT primary_acno, card_no, secondary_acno,txn_limit_type, withdrawal_limit_amount, "
                                    + "withdrawal_limit_count, purchase_limit_amount,purchase_limit_count, trf_limit_amount, "
                                    + "trf_limit_count FROM atm_secondary_card_master WHERE primary_acno='" + primaryAccountNo.trim() + "' and "
                                    + "card_no='' and secondary_acno='" + obj.getSecondaryAccount().trim() + "'").executeUpdate();
                            if (n <= 0) {
                                throw new Exception("Problem In Secondary History Maintenance.");
                            }
                            n = em.createNativeQuery("Delete from atm_secondary_card_master where primary_acno='" + primaryAccountNo.trim() + "'"
                                    + " and card_no='' and secondary_acno='" + obj.getSecondaryAccount().trim() + "'").executeUpdate();
                            if (n <= 0) {
                                throw new Exception("Problem In Deletion of Row.");
                            }
                        } else {
                            chkList = em.createNativeQuery("select primary_acno from atm_secondary_card_master where "
                                    + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
                                    + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").getResultList();
                            if (!chkList.isEmpty()) {
                                n = em.createNativeQuery("INSERT INTO atm_secondary_card_master_his (primary_acno, card_no, secondary_acno, "
                                        + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
                                        + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
                                        + "SELECT primary_acno, card_no, secondary_acno,txn_limit_type, withdrawal_limit_amount, "
                                        + "withdrawal_limit_count, purchase_limit_amount,purchase_limit_count, trf_limit_amount, "
                                        + "trf_limit_count FROM atm_secondary_card_master WHERE primary_acno='" + primaryAccountNo.trim() + "' and "
                                        + "card_no='' and secondary_acno='" + obj.getSecondaryAccount().trim() + "'").executeUpdate();
                                if (n <= 0) {
                                    throw new Exception("Problem In Secondary History Maintenance.");
                                }
                                //Update the record
                                n = em.createNativeQuery("UPDATE atm_secondary_card_master set "
                                        + "txn_limit_type='" + obj.getTxnLimitType().trim() + "',"
                                        + "withdrawal_limit_amount=" + obj.getWithdrawalLimitAmount() + ","
                                        + "withdrawal_limit_count=" + obj.getWithdrawalLimitCount() + ","
                                        + "purchase_limit_amount=" + obj.getPurchaseLimitAmount() + ","
                                        + "purchase_limit_count=" + obj.getPurchaseLimitCount() + ","
                                        + "trf_limit_amount='0',"
                                        + "trf_limit_count='0' where "
                                        + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
                                        + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").executeUpdate();
                                if (n <= 0) {
                                    throw new Exception("Problem In Secondary Accounts Updation.");
                                }
                            } else {
                                n = em.createNativeQuery("INSERT INTO atm_secondary_card_master (primary_acno, card_no, secondary_acno, "
                                        + "txn_limit_type, withdrawal_limit_amount, withdrawal_limit_count, purchase_limit_amount, "
                                        + "purchase_limit_count, trf_limit_amount, trf_limit_count) "
                                        + "VALUES "
                                        + "('" + primaryAccountNo.trim() + "', '', '" + obj.getSecondaryAccount().trim() + "', "
                                        + "'" + obj.getTxnLimitType().trim() + "', " + obj.getWithdrawalLimitAmount() + ", "
                                        + "" + obj.getWithdrawalLimitCount() + ", " + obj.getPurchaseLimitAmount() + ", " + obj.getPurchaseLimitCount() + ", "
                                        + " '0', '0')").executeUpdate();
                                if (n <= 0) {
                                    throw new Exception("There is issue in secondary account mapping.");
                                }
                            }
                        }
                    }
                }
            }
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

    @Override
    public String processDeleteFirstSeqFile(String fileType, String delFlag, String primaryAccountNo,
            List<AtmSecondryAccountDetail> secondaryTable, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkList = em.createNativeQuery("select acno from atm_card_master where acno='" + primaryAccountNo + "' and "
                    + "card_no=''").getResultList();
            if (delFlag.equalsIgnoreCase("D") && chkList.isEmpty()) {
                throw new Exception("This is no such detail to delete.");
            }

            insertPrimaryAtmCardHistory(primaryAccountNo.trim(), "");

            int n = em.createNativeQuery("delete from atm_card_master where acno='" + primaryAccountNo + "' and "
                    + "card_no=''").executeUpdate();
            if (n <= 0) {
                throw new Exception("There is problem in deletion of card mapping.");
            }
            for (AtmSecondryAccountDetail obj : secondaryTable) {
                chkList = em.createNativeQuery("select primary_acno from atm_secondary_card_master where "
                        + "primary_acno='" + primaryAccountNo.trim() + "' and card_no='' and "
                        + "secondary_acno='" + obj.getSecondaryAccount().trim() + "'").getResultList();
                if (!chkList.isEmpty()) {
                    n = em.createNativeQuery("delete from atm_secondary_card_master where primary_acno='" + primaryAccountNo.trim() + "' "
                            + "and card_no='' and secondary_acno='" + obj.getSecondaryAccount().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("There is problem in deletion of secondary accounts detail.");
                    }
                }
            }
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

    @Override
    public String changeCardStatus(String fileType, String delFlag, String primaryAccountNo, String chnNo, String cardStatus,
            String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkList = em.createNativeQuery("select file_type,del_flag,card_status from atm_card_master "
                    + "where acno='" + primaryAccountNo + "' and card_no='" + chnNo + "'").getResultList();
            if (chkList.isEmpty()) {
                throw new Exception("There is no data to change the status.");
            }
            Vector ele = (Vector) chkList.get(0);
            String prFileType = ele.get(0).toString().trim();
            String prDelFlag = ele.get(1).toString().trim();
            String prCardStatus = ele.get(2).toString().trim();
            if (prCardStatus.trim().equals(cardStatus)) {
                throw new Exception("Card is in same status.");
            }
            if (prCardStatus.trim().equals("9")) {
                throw new Exception("Card is already closed.");
            }
            if ((prFileType.trim().equalsIgnoreCase("N") && prDelFlag.equalsIgnoreCase("G"))
                    || (prFileType.trim().equalsIgnoreCase("A") && prDelFlag.equalsIgnoreCase("G"))
                    || (prFileType.trim().equalsIgnoreCase("C") && prDelFlag.equalsIgnoreCase("G"))
                    || (prFileType.trim().equalsIgnoreCase("S") && (prDelFlag.equalsIgnoreCase("G") || prDelFlag.equalsIgnoreCase("U")))) {
                //Maintain the history
                insertPrimaryAtmCardHistory(primaryAccountNo.trim(), chnNo);

                int n = em.createNativeQuery("UPDATE atm_card_master set file_type='" + fileType + "',del_flag='" + delFlag + "',verify='N', verify_by='',"
                        + "card_status='" + cardStatus + "',lastUpdateBy='" + userName + "',lastUpdateDate=current_timestamp "
                        + "WHERE acno='" + primaryAccountNo.trim() + "' and card_no='" + chnNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Card Detail Updation.");
                }
            }

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

    @Override
    public String changeNames(String fileType, String delFlag, String primaryAccountNo, String chnNo, String encodingName,
            String embossingName, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkList = em.createNativeQuery("select file_type,del_flag,card_status from atm_card_master "
                    + "where acno='" + primaryAccountNo + "' and card_no='" + chnNo + "'").getResultList();
            if (chkList.isEmpty()) {
                throw new Exception("There is no data to change the names.");
            }
            Vector ele = (Vector) chkList.get(0);
            String prFileType = ele.get(0).toString().trim();
            String prDelFlag = ele.get(1).toString().trim();
            String prCardStatus = ele.get(2).toString().trim();

            if (prCardStatus.trim().equals("9")) {
                throw new Exception("Card is already closed.");
            }
            if ((prFileType.trim().equalsIgnoreCase("N") && prDelFlag.equalsIgnoreCase("G"))
                    || (prFileType.trim().equalsIgnoreCase("S") && prDelFlag.equalsIgnoreCase("G")
                    && (prCardStatus.equals("1") || prCardStatus.equals("3")))
                    || (prFileType.trim().equalsIgnoreCase("A") && prDelFlag.equalsIgnoreCase("G"))
                    || (prFileType.trim().equalsIgnoreCase("C") && (prDelFlag.equalsIgnoreCase("G") || prDelFlag.equalsIgnoreCase("U")))) {
                //Maintain the history
                insertPrimaryAtmCardHistory(primaryAccountNo.trim(), chnNo);

                int n = em.createNativeQuery("UPDATE atm_card_master set file_type='" + fileType + "',del_flag='" + delFlag + "',verify='N', verify_by='',"
                        + "encoding_name='" + encodingName + "',embossing_name='" + embossingName + "',lastUpdateBy='" + userName + "',"
                        + "lastUpdateDate=current_timestamp WHERE acno='" + primaryAccountNo.trim() + "' and "
                        + "card_no='" + chnNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Card Detail Updation.");
                }
            } else {
                throw new Exception("Account is in improper condition to change the names.");
            }
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

    @Override
    public String detailModification(String primaryAccountNo, String chnNo, String oldCardNo, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List chkList = em.createNativeQuery("select file_type,del_flag,card_no from atm_card_master "
                    + "where acno='" + primaryAccountNo + "' and card_no='" + oldCardNo + "'").getResultList();
            if (chkList.isEmpty()) {
                throw new Exception("There is no data to change the detail.");
            }
            Vector ele = (Vector) chkList.get(0);
            String fileType = ele.get(0).toString().trim();
            String delFlag = ele.get(1).toString().trim();

            if (!(fileType.equalsIgnoreCase("N") && (delFlag.equalsIgnoreCase("G")))) {
                throw new Exception("Account is in improper condition to update the details.");
            }

            insertPrimaryAtmCardHistory(primaryAccountNo.trim(), oldCardNo);
            int n = em.createNativeQuery("UPDATE atm_card_master set card_no='" + chnNo + "',lastUpdateBy='" + userName + "',verify='N', verify_by='',"
                    + "lastUpdateDate=current_timestamp WHERE acno='" + primaryAccountNo.trim() + "' and card_no='" + oldCardNo + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem In Card Detail Updation.");
            }

            chkList = em.createNativeQuery("select * from atm_secondary_card_master where primary_acno='" + primaryAccountNo.trim() + "' "
                    + "and card_no=''").getResultList();
            if (!chkList.isEmpty()) {
                n = em.createNativeQuery("UPDATE atm_secondary_card_master set card_no='" + chnNo + "' WHERE "
                        + "primary_acno='" + primaryAccountNo.trim() + "' and card_no=''").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Secondar A/c Detail Updation.");
                }
            }
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

    @Override
    public boolean isPrimaryTypeAccount(String accountNo) throws Exception { //Here we can modify the condition
        try {
            List list = em.createNativeQuery("select am.acno,am.accttype,am.accstatus from accountmaster am,accounttypemaster att "
                    + "where am.accttype=att.acctcode and (att.acctnature in('SB') or (att.acctnature in('CA') and "
                    + "att.accttype='CA')) and am.accstatus=1 and am.acno='" + accountNo + "'").getResultList();
            if (!list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Override
    public String generateNewAddOnCard(String fileType, String dt, String todayDt, String userName, String orgCode,
            String filesLocation) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String fileNo = "", genFileName = "";
        try {
            ut.begin();
            //Required Values Extraction.
            List list = em.createNativeQuery("select ifnull(a.code,'') as BIN,ifnull(b.code,'') as INR_CODE from (select ifnull((select "
                    + "code from cbs_parameterinfo  where name='ATM-SEQ-FILE-BIN'),'') as code) a,(select ifnull((select code from "
                    + "cbs_parameterinfo where name='IND-CUR-CODE'),'') as code) b").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define required values.");
            }
            Vector ele = (Vector) list.get(0);
            String binCode = ele.get(0).toString().trim();
            String inrCode = ele.get(1).toString().trim();
            if (binCode == null || inrCode == null || binCode.equalsIgnoreCase("") || inrCode.equalsIgnoreCase("")
                    || binCode.length() != 6 || inrCode.length() != 3) {
                throw new Exception("Please define required values in proper.");
            }

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files where "
                    + "file_gen_type='" + fileType + "' and file_gen_date='" + todayDt + "'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }

            fileNo = fileNo.length() < 2 ? "0" + fileNo : fileNo;
            genFileName = fileType + todayDt + fileNo + ".txt";

            BigDecimal cardSeqNo;
            //Card sequence no
            list = em.createNativeQuery("select max(ifnull(seq_no,0)) as file_no from cbs_npci_mapper_files where "
                    + "file_gen_type='" + fileType + "'").getResultList();
            ele = (Vector) list.get(0);
            cardSeqNo = new BigDecimal("0");
            if (ele.get(0) != null) {
                cardSeqNo = new BigDecimal(ele.get(0).toString().trim());
                int dotIndex = cardSeqNo.toString().indexOf(".");
                if (dotIndex != -1) {
                    cardSeqNo = new BigDecimal(cardSeqNo.toString().substring(0, dotIndex));
                }
            }

            List dataList = em.createNativeQuery("select am.accttype,am.curbrcode,att.accttype,atm.acno,atm.txn_limit_type,"
                    + "atm.withdrawal_limit_amount,atm.withdrawal_limit_count,atm.encoding_name,atm.embossing_name,"
                    + "date_format(atm.registration_dt,'%d/%m/%Y'),atm.card_type,atm.txn_limit_card_type,atm.card_relationship,"
                    + "atm.service_code,atm.kcc_emv_type,atm.purchase_limit_amount,atm.purchase_limit_count,atm.trf_limit_amount,"
                    + "atm.trf_limit_count from atm_card_master atm,accountmaster am,accounttypemaster att where atm.acno=am.acno "
                    + "and am.accttype=att.acctcode and atm.file_type='N' and atm.del_flag in('A','M') and verify='Y' and "
                    + "date_format(atm.registration_dt,'%Y%m%d')<='" + dt + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to generate the file.");
            }

            //At present we are not sending the secondary accounts
            FileWriter fw = new FileWriter(filesLocation + "/" + genFileName);

            for (int i = 0; i < dataList.size(); i++) {
                cardSeqNo = cardSeqNo.add(new BigDecimal("1"));
                String secondaryAccountDetail = ""; //Total Secondary Account Length Is 425, Individual is 85

                Vector elem = (Vector) dataList.get(i);
                //Secondar Data Evaluation
                List secondaryList = em.createNativeQuery("select am.accttype,am.curbrcode,att.accttype,s.secondary_acno,"
                        + "s.txn_limit_type,s.withdrawal_limit_amount,s.withdrawal_limit_count,s.purchase_limit_amount,"
                        + "s.purchase_limit_count,s.trf_limit_amount,s.trf_limit_count from atm_secondary_card_master s,"
                        + "accountmaster am,accounttypemaster att where s.primary_acno='" + elem.get(3).toString().trim() + "' "
                        + "and s.card_no='' and s.secondary_acno=am.acno and am.accttype=att.acctcode").getResultList();

                for (int j = 0; j < secondaryList.size(); j++) {
                    Vector secVec = (Vector) secondaryList.get(j);

//                    String secondaryProductCode = secVec.get(2).toString().trim().equalsIgnoreCase("CA") ? "01"
//                            : (secVec.get(2).toString().trim().equalsIgnoreCase("OD") ? "03" : "02");

                    String withdrawalAmount = secVec.get(5).toString().trim();
                    int dotIndex = withdrawalAmount.indexOf(".");
                    if (dotIndex != -1) {
                        withdrawalAmount = withdrawalAmount.substring(0, dotIndex);
                    }

                    String purchaseAmount = secVec.get(7).toString().trim();
                    dotIndex = purchaseAmount.indexOf(".");
                    if (dotIndex != -1) {
                        purchaseAmount = purchaseAmount.substring(0, dotIndex);
                    }

                    String trfAmount = secVec.get(9).toString().trim();
                    dotIndex = trfAmount.indexOf(".");
                    if (dotIndex != -1) {
                        trfAmount = trfAmount.substring(0, dotIndex);
                    }

                    secondaryAccountDetail = secondaryAccountDetail
                            + ParseFileUtil.addTrailingZeros(secVec.get(1).toString().trim(), 4)
                            + ParseFileUtil.addTrailingZeros(secVec.get(0).toString().trim(), 5)
                            + ParseFileUtil.addTrailingZeros(secVec.get(3).toString().trim().substring(4, 10), 7)
                            + ParseFileUtil.addTrailingSpaces("01", 2)
                            + ParseFileUtil.addTrailingZeros(inrCode, 5)
                            + secVec.get(4).toString().trim()
                            + ParseFileUtil.addTrailingSpaces(withdrawalAmount, 15) + ParseFileUtil.addTrailingSpaces(secVec.get(6).toString().trim(), 5)
                            + ParseFileUtil.addTrailingSpaces(purchaseAmount, 15) + ParseFileUtil.addTrailingSpaces(secVec.get(8).toString().trim(), 5)
                            + ParseFileUtil.addTrailingSpaces(trfAmount, 15) + ParseFileUtil.addTrailingSpaces(secVec.get(10).toString().trim(), 5);
                }


                String productCode = "", withAmount = "", withLimit = "", purchaseAmount = "", purchaseLimit = "",
                        trfAmount = "", trfLimit = "", mailAddress = "", mailAddressOne = "", mailAddressTwo = "",
                        mailAddressThree = "", mailAddressFour = "", mobileNo = "";

//                productCode = elem.get(2).toString().trim().equalsIgnoreCase("CA") ? "01"
//                        : (elem.get(2).toString().trim().equalsIgnoreCase("OD") ? "03" : "02");

                String txnLimitType = elem.get(4).toString().trim();
                if (txnLimitType.equalsIgnoreCase("AC")) {
                    withAmount = elem.get(5).toString().trim(); //Check for point here
                    withLimit = elem.get(6).toString().trim();
                    purchaseAmount = elem.get(15).toString().trim();
                    purchaseLimit = elem.get(16).toString().trim();
                    trfAmount = elem.get(17).toString().trim();
                    trfLimit = elem.get(18).toString().trim();
                }

                int dotIndex = withAmount.indexOf(".");
                if (dotIndex != -1) {
                    withAmount = withAmount.substring(0, dotIndex);
                }
                dotIndex = purchaseAmount.indexOf(".");
                if (dotIndex != -1) {
                    purchaseAmount = purchaseAmount.substring(0, dotIndex);
                }
                dotIndex = trfAmount.indexOf(".");
                if (dotIndex != -1) {
                    trfAmount = trfAmount.substring(0, dotIndex);
                }
//                if (trfAmount.equalsIgnoreCase("") || trfAmount.equalsIgnoreCase("0")) { //As per finacus - Tejas
//                    trfAmount = "10000";
//                }
//                if (trfLimit.equalsIgnoreCase("") || trfLimit.equalsIgnoreCase("0")) { //As per finacus - Tejas
//                    trfLimit = "99";
//                }


                CustomerDetail obj = getCustomerDetailByAccountNo(elem.get(3).toString().trim());
                mailAddress = obj.getMailAddress().trim();
                mailAddress = (mailAddress.length() > 35) ? mailAddress.substring(0, 35).trim() : mailAddress.trim();

                mailAddressOne = obj.getMailAddressOne().trim();
                mailAddressOne = (mailAddressOne.length() > 35) ? mailAddressOne.substring(0, 35).trim() : mailAddressOne.trim();

                mailAddressTwo = obj.getMailAddressTwo().trim();
                mailAddressTwo = (mailAddressTwo.length() > 35) ? mailAddressTwo.substring(0, 35).trim() : mailAddressTwo.trim();

                mailAddressThree = obj.getMailAddressThree().trim();
                mailAddressThree = (mailAddressThree.length() > 35) ? mailAddressThree.substring(0, 35).trim() : mailAddressThree.trim();

                mailAddressFour = obj.getMailAddressFour().trim();
                mailAddressFour = (mailAddressFour.length() > 35) ? mailAddressFour.substring(0, 35).trim() : mailAddressFour.trim();

                mobileNo = obj.getMobileNo().trim();
                mobileNo = (mobileNo.length() > 10) ? mobileNo.substring(0, 10).trim() : mobileNo.trim();

                //Ask about gender
                String individualActStr = binCode + ParseFileUtil.addTrailingZeros(elem.get(1).toString().trim(), 4)
                        + ParseFileUtil.addTrailingZeros(elem.get(0).toString().trim(), 5)
                        + ParseFileUtil.addTrailingZeros(elem.get(3).toString().trim().substring(4, 10), 7)
                        + "01" + ParseFileUtil.addTrailingZeros(inrCode, 5) + txnLimitType
                        + ParseFileUtil.addTrailingSpaces(withAmount, 15) + ParseFileUtil.addTrailingSpaces(withLimit, 5)
                        + ParseFileUtil.addTrailingSpaces(purchaseAmount, 15) + ParseFileUtil.addTrailingSpaces(purchaseLimit, 5)
                        + ParseFileUtil.addTrailingSpaces(trfAmount, 15) + ParseFileUtil.addTrailingSpaces(trfLimit, 5)
                        + ParseFileUtil.addSuffixSpaces(secondaryAccountDetail, 425)
                        + ParseFileUtil.addSuffixSpaces(elem.get(7).toString().trim(), 25)
                        + ParseFileUtil.addSuffixSpaces(elem.get(8).toString().trim(), 25) + obj.getGenderCode().trim()
                        + ParseFileUtil.addSuffixSpaces(mailAddress, 35) + ParseFileUtil.addSuffixSpaces(mailAddressOne, 35)
                        + ParseFileUtil.addSuffixSpaces(mailAddressTwo, 35) + ParseFileUtil.addSuffixSpaces(mailAddressThree, 35)
                        + ParseFileUtil.addSuffixSpaces(mailAddressFour, 35) + ParseFileUtil.addSuffixSpaces(mobileNo, 16)
                        + ParseFileUtil.addSuffixSpaces("7777777777", 16) + ParseFileUtil.addSuffixSpaces("", 16)
                        + ParseFileUtil.addSuffixSpaces("", 35) + obj.getDateOfBirth()
                        + elem.get(9).toString().trim() + ParseFileUtil.addSuffixSpaces(obj.getCustomerId(), 15)
                        + ParseFileUtil.addSuffixSpaces(elem.get(10).toString().trim(), 2)
                        + ParseFileUtil.addSuffixSpaces(elem.get(11).toString().trim(), 2)
                        + ParseFileUtil.addSuffixSpaces("", 60)
                        + ParseFileUtil.addSuffixSpaces(elem.get(12).toString().trim(), 1)
                        + ParseFileUtil.addSuffixSpaces(elem.get(13).toString().trim(), 2)
                        + ParseFileUtil.addSuffixSpaces("", 1)
                        + ParseFileUtil.addSuffixSpaces("", 25)
                        + ParseFileUtil.addTrailingSpaces(cardSeqNo.toString(), 8)
                        + elem.get(14).toString().trim() + ParseFileUtil.addSuffixSpaces("", 1) + "\r\n";

                fw.write(individualActStr);
                //Updation of del_flag.
                int n = em.createNativeQuery("update atm_card_master set del_flag='G' where acno='" + elem.get(3).toString().trim() + "' and "
                        + "card_no='' and file_type='N' and (del_flag='A' or del_flag='M')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Updation problem in new add on card generation.");
                }
            }

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ","
                    + "'" + todayDt + "','" + genFileName + "','" + userName + "',now(),'" + orgCode + "',"
                    + "'" + fileType + "'," + cardSeqNo + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In First Sequence File Generation.");
            }

            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true";
    }

    @Override
    public String generateBulkCardStatusAndNameChange(String fileType, String dt, String todayDt, String userName,
            String orgCode, String filesLocation) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String fileNo = "", genFileName = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files where "
                    + "file_gen_type='" + fileType + "' and file_gen_date='" + todayDt + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }

            fileNo = fileNo.length() < 2 ? "0" + fileNo : fileNo;
            genFileName = fileType + todayDt + fileNo + ".txt";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ","
                    + "'" + todayDt + "','" + genFileName + "','" + userName + "',now(),'" + orgCode + "',"
                    + "'" + fileType + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Sequence ATM File Bulk Updation.");
            }

            FileWriter fw = new FileWriter(filesLocation + "/" + genFileName);
            List dataList = new ArrayList();
            if (fileType.equalsIgnoreCase("S")) {
                dataList = em.createNativeQuery("select card_no,card_status,acno from atm_card_master where file_type='S' and "
                        + "del_flag='U' and date_format(registration_dt,'%Y%m%d')<='" + dt + "'").getResultList();
            } else if (fileType.equalsIgnoreCase("C")) {
                dataList = em.createNativeQuery("select card_no,encoding_name,embossing_name,acno from atm_card_master "
                        + "where file_type='C' and del_flag='U' and date_format(registration_dt,'%Y%m%d')<='" + dt + "'").getResultList();
            }
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to generate the file.");
            }

            for (int i = 0; i < dataList.size(); i++) {
                Vector elem = (Vector) dataList.get(i);

                String individualActStr = "";
                if (fileType.equalsIgnoreCase("S")) {
                    individualActStr = ParseFileUtil.addTrailingSpaces(elem.get(0).toString().trim(), 19)
                            + elem.get(1).toString().trim() + "\n";
                    fw.write(individualActStr);
                    //Updation of del_flag.
                    n = em.createNativeQuery("update atm_card_master set del_flag='G' where acno='" + elem.get(2).toString().trim() + "' and "
                            + "card_no='" + elem.get(0).toString().trim() + "' and file_type='S' and del_flag='U'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Updation problem in bulk card status change.");
                    }
                } else if (fileType.equalsIgnoreCase("C")) {
                    individualActStr = ParseFileUtil.addTrailingSpaces(elem.get(0).toString().trim(), 19)
                            + ParseFileUtil.addTrailingSpaces(elem.get(1).toString().trim(), 25)
                            + ParseFileUtil.addTrailingSpaces(elem.get(2).toString().trim(), 25) + "\n";
                    fw.write(individualActStr);
                    //Updation of del_flag.
                    n = em.createNativeQuery("update atm_card_master set del_flag='G' where acno='" + elem.get(3).toString().trim() + "' and "
                            + "card_no='" + elem.get(0).toString().trim() + "' and file_type='C' and del_flag='U'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Updation problem in bulk card status change.");
                    }
                }
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

    @Override
    public List<NpciFileDto> showGeneratedFiles(String fileType, String fileShowDt) throws Exception {
        List<NpciFileDto> dataList = new ArrayList<>();
        try {
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + fileShowDt + "' and "
                    + "file_gen_type='" + fileType + "'").getResultList();
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

    @Override
    public List<ExcelReaderPojo> getIdbiNeftReturnData(String txnType, String returnDate) throws Exception {
        List<ExcelReaderPojo> returnData = new ArrayList<>();
        try {
            List list = em.createNativeQuery("SELECT txn_id,ifnull(batch_time,''),ifnull(utr,''),ifnull(related_ref_no,''),"
                    + "sender_ifsc,ifnull(sender_actype,''),ifnull(sender_account,''),ifnull(sender_name,''),"
                    + "ifnull(remittance_originator,''),receiver_ifsc,ifnull(bene_actype,''),bene_account,bene_name,"
                    + "ifnull(bene_add,''),ifnull(remittance_info,''),ifnull(reason,''),amount,date_format(value_dt,'%Y%m%d'),"
                    + "ifnull(sender_address,'') FROM neft_rtgs_status m WHERE status='Unsuccess' AND reason<>'THIS UTR ALREADY PROCESSED.' AND "
                    + "txn_type='" + txnType + "' AND dt='" + returnDate + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);

                ExcelReaderPojo pojo = new ExcelReaderPojo();
                pojo.setTxnId(ele.get(0).toString().trim());
                pojo.setBatchTime(ele.get(1).toString().trim());
                pojo.setUtr(ele.get(2).toString().trim());
                pojo.setRelatedRefNo(ele.get(3).toString().trim());
                pojo.setSenderIfsc(ele.get(4).toString().trim());
                pojo.setSenderAcype(ele.get(5).toString().trim());
                pojo.setSenderAcc(ele.get(6).toString().trim());
                pojo.setSenderName(ele.get(7).toString().trim());
                pojo.setRemittanceOriginator(ele.get(8).toString().trim());
                pojo.setIfsccode(ele.get(9).toString().trim());
                pojo.setBeneficiaryAcType(ele.get(10).toString().trim());
                pojo.setBeneAccount(ele.get(11).toString().trim());
                pojo.setBeneName(ele.get(12).toString().trim());
                pojo.setBeneAdd(ele.get(13).toString().trim());
                pojo.setRemitInfo(ele.get(14).toString().trim());
                pojo.setReason(ele.get(15).toString().trim());
                pojo.setAmount(new BigDecimal(ele.get(16).toString().trim()));
                pojo.setValueDt(ele.get(17).toString().trim());
                pojo.setSenderAddOne(ele.get(18).toString().trim());

                returnData.add(pojo);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnData;
    }

    @Override
    public String generateIdbiIwReturn(String fileType, List<ExcelReaderPojo> returnData, String todayDt, String userName,
            String orgCode, String filesLocation) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String fileNo = "", genFileName = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files where "
                    + "file_gen_type='" + fileType + "' and file_gen_date='" + todayDt + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            fileNo = fileNo.length() < 2 ? "0" + fileNo : fileNo;
            genFileName = fileType + todayDt + fileNo + ".xls";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ","
                    + "'" + todayDt + "','" + genFileName + "','" + userName + "',now(),'" + orgCode + "',"
                    + "'" + fileType + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Neft/Rtgs File Generation.");
            }

            String txnIds = "";
            if (fileType.equalsIgnoreCase("NIRET")) { //Writing NEFT Return File
                txnIds = createIdbiNeftReturn(returnData, todayDt, userName, orgCode, filesLocation, genFileName);
            } else if (fileType.equalsIgnoreCase("OIRET")) { //Writing RTGS Return File
                txnIds = createIdbiRtgsReturn(returnData, todayDt, userName, orgCode, filesLocation, genFileName);
            }
            System.out.println("Txn Is--->" + txnIds);
            n = em.createNativeQuery("update neft_rtgs_status set status='Sent IDBI' where txn_id in(" + txnIds + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem Neft/Rtgs Return Status Updation.");
            }
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

    public String createIdbiNeftReturn(List<ExcelReaderPojo> returnData, String todayDt, String userName,
            String orgCode, String filesLocation, String genFileName) throws Exception {
        String txnIds = "";
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(genFileName);
            //Header
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Batch Time");
            cell = row.createCell(1);
            cell.setCellValue("Transaction Reference Number");
            cell = row.createCell(2);
            cell.setCellValue("Related Reference Number");
            cell = row.createCell(3);
            cell.setCellValue("Sender IFSC");
            cell = row.createCell(4);
            cell.setCellValue("Sender A/C Type");
            cell = row.createCell(5);
            cell.setCellValue("Sender A/C No");
            cell = row.createCell(6);
            cell.setCellValue("Sender Name");
            cell = row.createCell(7);
            cell.setCellValue("Originator Of Remittance");
            cell = row.createCell(8);
            cell.setCellValue("Bene IFSC");
            cell = row.createCell(9);
            cell.setCellValue("Bene A/C Type");
            cell = row.createCell(10);
            cell.setCellValue("Bene A/C No");
            cell = row.createCell(11);
            cell.setCellValue("Bene Name");
            cell = row.createCell(12);
            cell.setCellValue("Beneficiary Customer Address");
            cell = row.createCell(13);
            cell.setCellValue("Sender To Receiver Info");
            cell = row.createCell(14);
            cell.setCellValue("Return Code");
            cell = row.createCell(15);
            cell.setCellValue("Return Reason");
            cell = row.createCell(16);
            cell.setCellValue("Amount");
            cell = row.createCell(17);
            cell.setCellValue("Value Date");

            int rowNum = 1;
            for (int i = 0; i < returnData.size(); i++) {
                ExcelReaderPojo obj = returnData.get(i);
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                cell.setCellValue(obj.getBatchTime());
                cell = row.createCell(1);
                cell.setCellValue(obj.getUtr());
                cell = row.createCell(2);
                cell.setCellValue(obj.getRelatedRefNo());
                cell = row.createCell(3);
                cell.setCellValue(obj.getSenderIfsc());
                cell = row.createCell(4);
                cell.setCellValue(obj.getSenderAcype());
                cell = row.createCell(5);
                cell.setCellValue(obj.getSenderAcc());
                cell = row.createCell(6);
                cell.setCellValue(obj.getSenderName());
                cell = row.createCell(7);
                cell.setCellValue(obj.getRemittanceOriginator());
                cell = row.createCell(8);
                cell.setCellValue(obj.getIfsccode());
                cell = row.createCell(9);
                cell.setCellValue(obj.getBeneficiaryAcType());
                cell = row.createCell(10);
                cell.setCellValue(obj.getBeneAccount());
                cell = row.createCell(11);
                cell.setCellValue(obj.getBeneName());
                cell = row.createCell(12);
                cell.setCellValue(obj.getBeneAdd());
                cell = row.createCell(13);
                cell.setCellValue(obj.getRemitInfo());

                String[] arr = h2hRemote.idbiReturnDescription(obj.getReason());
                cell = row.createCell(14);
                cell.setCellValue(arr[0]);
                cell = row.createCell(15);
                cell.setCellValue(arr[1]);
                cell = row.createCell(16);
                cell.setCellValue(obj.getAmount().doubleValue());
                cell = row.createCell(17);
                cell.setCellValue(dMMMy.format(ymd.parse(todayDt)));

                if (txnIds.equals("")) {
                    txnIds = obj.getTxnId();
                } else {
                    txnIds = txnIds + "," + obj.getTxnId();
                }
            }
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(filesLocation + "/" + genFileName));
            workbook.write(out);
            out.close();
            System.out.println("Idbi neft return file has been generated successfully.");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return txnIds;
    }

    public String createIdbiRtgsReturn(List<ExcelReaderPojo> returnData, String todayDt, String userName,
            String orgCode, String filesLocation, String genFileName) throws Exception {
        String txnIds = "";
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(genFileName);
            //Header
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Other Bank UTR No");
            cell = row.createCell(1);
            cell.setCellValue("Value Date");
            cell = row.createCell(2);
            cell.setCellValue("Amount");
            cell = row.createCell(3);
            cell.setCellValue("Related Reference Number");
            cell = row.createCell(4);
            cell.setCellValue("Sender IFSC");
            cell = row.createCell(5);
            cell.setCellValue("Sender A/C No");
            cell = row.createCell(6);
            cell.setCellValue("Sender Name");
            cell = row.createCell(7);
            cell.setCellValue("Sender Address");
            cell = row.createCell(8);
            cell.setCellValue("Bene IFSC");
            cell = row.createCell(9);
            cell.setCellValue("Bene A/C No");
            cell = row.createCell(10);
            cell.setCellValue("Bene Name");
            cell = row.createCell(11);
            cell.setCellValue("Beneficiary Customer Address");
            cell = row.createCell(12);
            cell.setCellValue("Return Code");
            cell = row.createCell(13);
            cell.setCellValue("Return Reason");

            int rowNum = 1;
            for (int i = 0; i < returnData.size(); i++) {
                ExcelReaderPojo obj = returnData.get(i);
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                cell.setCellValue(obj.getUtr());
                cell = row.createCell(1);
                cell.setCellValue(dMMMy.format(ymd.parse(todayDt)));
                cell = row.createCell(2);
                cell.setCellValue(obj.getAmount().doubleValue());
                cell = row.createCell(3);
                cell.setCellValue(obj.getRelatedRefNo());
                cell = row.createCell(4);
                cell.setCellValue(obj.getSenderIfsc());
                cell = row.createCell(5);
                cell.setCellValue(obj.getSenderAcc());
                cell = row.createCell(6);
                cell.setCellValue(obj.getSenderName());
                cell = row.createCell(7);
                cell.setCellValue(obj.getSenderAddOne());
                cell = row.createCell(8);
                cell.setCellValue(obj.getIfsccode());
                cell = row.createCell(9);
                cell.setCellValue(obj.getBeneAccount());
                cell = row.createCell(10);
                cell.setCellValue(obj.getBeneName());
                cell = row.createCell(11);
                cell.setCellValue(obj.getBeneAdd());

                String[] arr = h2hRemote.idbiReturnDescription(obj.getReason());
                cell = row.createCell(12);
                cell.setCellValue(arr[0]);
                cell = row.createCell(13);
                cell.setCellValue(arr[1]);

                if (txnIds.equals("")) {
                    txnIds = obj.getTxnId();
                } else {
                    txnIds = txnIds + "," + obj.getTxnId();
                }
            }
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(filesLocation + "/" + genFileName));
            workbook.write(out);
            out.close();
            System.out.println("Idbi rtgs return file has been generated successfully.");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return txnIds;
    }

    @Override
    public List<ExcelReaderPojo> getIdbiOutwardData(String paymentType, String txnDt) throws Exception {
        List<ExcelReaderPojo> dataList = new ArrayList<>();
        try {
            List list = em.createNativeQuery("SELECT uniquecustomerrefno,paymentdate,txnamt,outsidebankifsccode,"
                    + "creditaccountno,beneficiaryname,debitaccountno FROM neft_ow_details WHERE status='P' AND "
                    + "auth='Y' and paymenttype='" + paymentType + "' and dt='" + txnDt + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to generate the outward file");
            }
            Map map = getBranchIfscDetailInMap();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);

                ExcelReaderPojo pojo = new ExcelReaderPojo();

                pojo.setUtr(ele.get(0).toString().trim());
                pojo.setValueDt(ele.get(1).toString().trim());
                pojo.setAmount(new BigDecimal(ele.get(2).toString().trim()));
                pojo.setIfsccode(ele.get(3).toString().trim());
                pojo.setBeneAccount(ele.get(4).toString().trim());
                pojo.setBeneName(ele.get(5).toString().trim());
                pojo.setSenderAcc(ele.get(6).toString().trim());

                String drCustName = ftsRemote.ftsGetCustName(pojo.getSenderAcc());
                drCustName = drCustName.replaceAll("[\\W_]", " ");
                if (paymentType.equalsIgnoreCase("N")) {
                    drCustName = drCustName.length() > 50 ? drCustName.substring(0, 50) : drCustName;
                } else if (paymentType.equalsIgnoreCase("R")) {
                    drCustName = drCustName.length() > 35 ? drCustName.substring(0, 35) : drCustName;
                }
                pojo.setSenderName(drCustName);
                pojo.setSenderIfsc(map.get(String.valueOf(Integer.parseInt(pojo.getSenderAcc().substring(0, 2)))).toString());

                dataList.add(pojo);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public Map getBranchIfscDetailInMap() throws Exception {
        Map<String, String> brMap = new HashMap<>();
        List list = em.createNativeQuery("select brncode,ifnull(ifsc_code,'') from branchmaster order by brncode").getResultList();
        if (list.isEmpty()) {
            throw new Exception("There is no data in branchmaster");
        }
        for (int i = 0; i < list.size(); i++) {
            Vector ele = (Vector) list.get(i);
            brMap.put(ele.get(0).toString(), ele.get(1).toString());
        }
        return brMap;
    }

    @Override
    public String generateIdbiOutward(String fileType, List<ExcelReaderPojo> dataList, String todayDt, String userName,
            String orgCode, String filesLocation) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String fileNo = "", genFileName = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files where "
                    + "file_gen_type='" + fileType + "' and file_gen_date='" + todayDt + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            fileNo = fileNo.length() < 2 ? "0" + fileNo : fileNo;

            String txnIds = "";
            if (fileType.equalsIgnoreCase("ONEFT")) { //Writing NEFT Outward File
                genFileName = "NEFT" + ddMMyy.format(new Date()) + fileNo + ".csv";
                txnIds = createIdbiNeftOutward(dataList, todayDt, userName, orgCode, filesLocation, genFileName);
            } else if (fileType.equalsIgnoreCase("ORTGS")) { //Writing RTGS Outward File
                genFileName = "RTGS" + ddMMyy.format(new Date()) + fileNo + ".csv";
                txnIds = createIdbiRtgsOutward(dataList, todayDt, userName, orgCode, filesLocation, genFileName);
            }
            System.out.println("Txn Is--->" + txnIds);

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ","
                    + "'" + todayDt + "','" + genFileName + "','" + userName + "',now(),'" + orgCode + "',"
                    + "'" + fileType + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Neft/Rtgs Outward File Generation.");
            }

            n = em.createNativeQuery("UPDATE neft_ow_details SET status='I',details='Awaiting Response From IDBI Bank.' WHERE "
                    + "uniquecustomerrefno in(" + txnIds + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem Neft/Rtgs Outward Status Updation.");
            }
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

    public String createIdbiNeftOutward(List<ExcelReaderPojo> dataList, String todayDt, String userName,
            String orgCode, String filesLocation, String genFileName) throws Exception {
        String txnIds = "";
        try {
            //Delimiter used in csv file
            String TILD_DELIMITER = "~";
            FileWriter filewriter = new FileWriter(filesLocation + "/" + genFileName);
            for (ExcelReaderPojo obj : dataList) {
                String individualTxn = obj.getUtr() + TILD_DELIMITER + TILD_DELIMITER + obj.getSenderIfsc() + TILD_DELIMITER
                        + TILD_DELIMITER + obj.getSenderAcc() + TILD_DELIMITER + obj.getSenderName() + TILD_DELIMITER
                        + obj.getAmount() + TILD_DELIMITER + todayDt + TILD_DELIMITER + obj.getBeneName() + TILD_DELIMITER
                        + obj.getBeneName() + TILD_DELIMITER + obj.getIfsccode() + TILD_DELIMITER + TILD_DELIMITER
                        + obj.getBeneAccount() + TILD_DELIMITER + TILD_DELIMITER + TILD_DELIMITER + "\n";
                filewriter.write(individualTxn);

                if (txnIds.equals("")) {
                    txnIds = obj.getUtr();
                } else {
                    txnIds = txnIds + "," + obj.getUtr();
                }
            }
            filewriter.flush();
            filewriter.close();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return txnIds;
    }

    //Here we are using the sender name in place of sender address.
    public String createIdbiRtgsOutward(List<ExcelReaderPojo> dataList, String todayDt, String userName,
            String orgCode, String filesLocation, String genFileName) throws Exception {
        String txnIds = "";
        try {
            //Delimiter used in csv file
            String TILD_DELIMITER = "~";
            FileWriter filewriter = new FileWriter(filesLocation + "/" + genFileName);
            for (ExcelReaderPojo obj : dataList) {
                String individualTxn = "R41" + TILD_DELIMITER + obj.getUtr() + TILD_DELIMITER + new SimpleDateFormat("dd-MM-yyyy").
                        format(ymd.parse(todayDt)) + TILD_DELIMITER + obj.getAmount() + TILD_DELIMITER + obj.getIfsccode()
                        + TILD_DELIMITER + obj.getBeneAccount() + TILD_DELIMITER + obj.getBeneName() + TILD_DELIMITER
                        + obj.getSenderAcc() + TILD_DELIMITER + obj.getSenderName() + TILD_DELIMITER + obj.getSenderName()
                        + TILD_DELIMITER + "/URGENT/" + TILD_DELIMITER + "Vendor Payment" + TILD_DELIMITER + TILD_DELIMITER
                        + TILD_DELIMITER + "\n";
                filewriter.write(individualTxn);

                if (txnIds.equals("")) {
                    txnIds = obj.getUtr();
                } else {
                    txnIds = txnIds + "," + obj.getUtr();
                }
            }
            filewriter.flush();
            filewriter.close();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return txnIds;
    }

    @Override
    public List<CbsMandateDetailPojo> getOwMandateFeedReport(String brnCode, String txnType, String option, String frDt, String toDt) throws Exception {
        List<CbsMandateDetailPojo> dataList = new ArrayList<>();
        try {
            String optionQuery = "";
            String txnTypeQuery = "";
            if (txnType.equalsIgnoreCase("ECS")) {
                txnTypeQuery = " and substring(CHI_Umrn,5,1)='9'";
            } else {
                txnTypeQuery = " and substring(CHI_Umrn,5,1)<>'9'";
            }
            if (option.equalsIgnoreCase("E")) {
                optionQuery = " and cast(Mandate_Received_Date as date) between '" + frDt + "' and '" + toDt + "'";
            } else {
                optionQuery = " and Update_Date between '" + frDt + "' and '" + toDt + "'";
            }
            String query = "select Trans_Type,CBS_Umrn,CHI_Umrn,TxnFileType,Proprietary,Category,Amount_Flag,Amount,Frequency,"
                    + "Sequence_Type,Period_Type,From_Date,To_Date,Creditor_Acno,Creditor_Name,Creditor_AcType,Creditor_BankName,"
                    + "Creditor_FinCodeType,Creditor_IFSC,Creditor_Mobile,Creditor_Email,Creditor_Utility_Code,Debtor_Acno,"
                    + "Debtor_Name,Debtor_AcType,Debtor_BankName,Debtor_FinCodeType,Debtor_IFSC,Debtor_Mobile,Debtor_Email,"
                    + "Debtor_Utility_Code,Ref_1,Ref_2,Flag,Mandate_Receiving_Branch,Mandate_Received_By,"
                    + "date_format(Mandate_Received_Date,'%Y%m%d'),Update_By,date_format(Update_Date,'%Y%m%d'),Update_Mode,"
                    + "Response_Code_In_Updation,Response_Detail_In_Updation,Uploaded_Zip_Name,Response_File_Name "
                    + "from cbs_mandate_detail where TxnFileType='ACH'  " + optionQuery + " " + txnTypeQuery + "";

            if (!brnCode.equalsIgnoreCase("0A")) {
                query = query + " and Mandate_Receiving_Branch='" + brnCode + "'";
            }

            List list = em.createNativeQuery(query).getResultList();
            for (int i = 0; i < list.size(); i++) {
                CbsMandateDetailPojo obj = new CbsMandateDetailPojo();
                Vector ele = (Vector) list.get(i);

                obj.setTransType(ele.get(0).toString().trim());
                obj.setcBSUmrn(ele.get(1).toString().trim());
                obj.setcHIUmrn(ele.get(2).toString().trim());
                obj.setTxnFileType(ele.get(3).toString().trim());
                obj.setProprietary(ele.get(4).toString().trim());
                obj.setCategory(ele.get(5).toString().trim());
                obj.setAmountFlag(ele.get(6).toString().trim());
                obj.setAmount(ele.get(7).toString().trim());
                obj.setFrequency(ele.get(8).toString().trim());
                obj.setSequenceType(ele.get(9).toString().trim());
                obj.setPeriodType(ele.get(10).toString().trim());
                obj.setFromDate(ele.get(11).toString().trim().equals("") ? "" : dmy.format(ymd.parse(ele.get(11).toString().trim())));
                obj.setToDate(ele.get(12).toString().trim().equals("") ? "" : dmy.format(ymd.parse(ele.get(12).toString().trim())));
                obj.setCreditorAcno(ele.get(13).toString().trim());
                obj.setCreditorName(ele.get(14).toString().trim());
                obj.setCreditorAcType(ele.get(15).toString().trim());
                obj.setCreditorBankName(ele.get(16).toString().trim());
                obj.setCreditorFinCodeType(ele.get(17).toString().trim());
                obj.setCreditorIFSC(ele.get(18).toString().trim());
                obj.setCreditorMobile(ele.get(19).toString().trim());
                obj.setCreditorEmail(ele.get(20).toString().trim());
                obj.setCreditorUtilityCode(ele.get(21).toString().trim());
                obj.setDebtorAcno(ele.get(22).toString().trim());
                obj.setDebtorName(ele.get(23).toString().trim());
                obj.setDebtorAcType(ele.get(24).toString().trim());
                obj.setDebtorBankName(ele.get(25).toString().trim());
                obj.setDebtorFinCodeType(ele.get(26).toString().trim());
                obj.setDebtorIFSC(ele.get(27).toString().trim());
                obj.setDebtorMobile(ele.get(28).toString().trim());
                obj.setDebtorEmail(ele.get(29).toString().trim());
                obj.setDebtorUtilityCode(ele.get(30).toString().trim());
                obj.setRef1(ele.get(31).toString().trim());
                obj.setRef2(ele.get(32).toString().trim());

                String flagValue = ele.get(33).toString().trim();
                obj.setResponseDetailInUpdation(ele.get(41).toString().trim()); //For Reason
                if (flagValue.equalsIgnoreCase("N")) {
                    obj.setFlag("Feeded");
                } else if (flagValue.equalsIgnoreCase("Y")) {
                    obj.setFlag("File Generated");
                } else if (flagValue.equalsIgnoreCase("A")) {
                    obj.setFlag("Success ACK");
                } else if (flagValue.equalsIgnoreCase("F")) {
                    obj.setFlag("Fail In ACK");
                } else if (flagValue.equalsIgnoreCase("S")) {
                    obj.setFlag("Accepted");
                } else if (flagValue.equalsIgnoreCase("P")) {
                    obj.setFlag("Rejected");
                } else if (flagValue.equalsIgnoreCase("C")) {
                    obj.setFlag("Cancelled");
                    obj.setResponseDetailInUpdation("");
                }

                obj.setMandateReceivingBranch(ele.get(34).toString().trim());
                obj.setMandateReceivedBy(ele.get(35).toString().trim());
                obj.setMandateReceivedDate(dmy.format(ymd.parse(ele.get(36).toString().trim())));
                obj.setUpdateBy(ele.get(37).toString().trim());
                obj.setUpdateDate(dmy.format(ymd.parse(ele.get(38).toString().trim())));
                obj.setUpdateMode(ele.get(39).toString().trim());
                obj.setResponseCodeInUpdation(ele.get(40).toString().trim());
                obj.setUploadedZipName(ele.get(42).toString().trim());
                obj.setResponseFileName(ele.get(43).toString().trim());

                dataList.add(obj);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public List<ImpsOutwardDetailPojo> getImpsOutwardDetailReport(String function, String frDt, String toDt) throws ApplicationException {
        List<ImpsOutwardDetailPojo> finalList = new ArrayList<>();
        String processingCode = "";
        try {

            if (function.equalsIgnoreCase("G")) {
                processingCode = "111001";
            } else if (function.equalsIgnoreCase("C")) {
                processingCode = "111002";
            } else if (function.equalsIgnoreCase("F")) {
                processingCode = "111009";
            }
            List result = em.createNativeQuery("select Remitter_Name,Remitter_Acc_No,Remitter_Mob_No,Bene_Acc_No,Bene_IFSC,Tran_Amount,"
                    + "Remark,Request_Status from cbs_imps_ow_request where Processing_Code='" + processingCode + "' and Dt between '" + frDt + "' and '" + toDt + "'").getResultList();
            if (result.isEmpty()) {
                throw new ApplicationException("There is no data found in between Date.");
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vec = (Vector) result.get(i);
                ImpsOutwardDetailPojo pojo = new ImpsOutwardDetailPojo();
                pojo.setSrno(i + 1);
                pojo.setRemitterName(vec.get(0).toString());
                pojo.setRemitterAcno(vec.get(1).toString());
                pojo.setRemitterMobNo(vec.get(2).toString());
                pojo.setBeneAcno(vec.get(3).toString());
                pojo.setBeneIfsc(vec.get(4).toString());
                pojo.setTranAmt(new BigDecimal(vec.get(5).toString()));
                pojo.setRemark(vec.get(6).toString());
                if (vec.get(7).toString().equalsIgnoreCase("S")) {
                    pojo.setStatus("Success");
                } else {
                    pojo.setStatus("Inter Status");
                }

                finalList.add(pojo);
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return finalList;
    }

    public List<CardFillingReportPojo> getCadFillingReportDetail(String branchCode, String fromDate, String toDate) throws ApplicationException {
        List<CardFillingReportPojo> dataList = new ArrayList();
        String branchQuery = "";
        if (branchCode.equalsIgnoreCase("0A")) {
            branchQuery = "";
        } else {
            branchQuery = "and b.primarybrcode = '" + branchCode + "'";
        }

        Map map = new HashMap();

        try{
        
        
        List accTypeList = em.createNativeQuery("select AcctCode,acctNature from accounttypemaster where acctNature in('sb','ca') and CrDbFlag = 'C'").getResultList();
        if (!accTypeList.isEmpty()) {
            for (int j = 0; j < accTypeList.size(); j++) {
                Vector vec = (Vector) accTypeList.get(j);
                map.put(vec.get(0).toString(), vec.get(1).toString());
            }
        }




        List list = em.createNativeQuery("select card_no,aa.customerid,acno,embossing_name,firstName,ifnull(middle_name,''),last_name,PerAddressLine1,peraddressline2,AddressLine3,city,state,Pincode, "
                + "mobilenumber,primarybrcode,branchName,smsTxnAlert,Dob,PAN_GIRNumber,TanNo,ifnull(AadharNo,'') from "
                + "(select a.card_no,b.customerid,a.acno,a.embossing_name,b.custname as firstName,b.middle_name,b.last_name,b.PerAddressLine1,b.peraddressline2,'' as AddressLine3, "
                + "b.PerDistrict as city,dref.REF_DESC as state,b.PerPostalCode as Pincode,b.mobilenumber,b.primarybrcode,br.BranchName as branchName,'Y' as smsTxnAlert, "
                + "date_format(b.DateOfBirth,'%d/%m/%Y') as Dob,b.PAN_GIRNumber,pa.TAXAcno as TanNo  "
                + "from atm_card_master a,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type dref,branchmaster br,parameterinfo pa "
                + "where a.acno = c.acno and cast(b.customerid as unsigned) = c.custid and b.PerStateCode = dref.REF_CODE and dref.REF_REC_NO = '002' "
                + "and cast(b.primarybrcode as unsigned) = br.BrnCode and cast(b.primarybrcode as unsigned) = pa.BrnCode "
                + "and del_flag <>'I'and date_format(registration_dt,'%Y%m%d') between '" + fromDate + "' and '" + toDate + "' "
                + branchQuery
                + ")aa left join "
                + "(select a.CustomerId, a.IdentityNo as AadharNo from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid  "
                + "union "
                + "select a.CustomerId, a.IdentityNo as AadharNo from cbs_customer_master_detail a where a.legal_document = 'E') bb "
                + "on aa.customerid = bb.customerid ").getResultList();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                CardFillingReportPojo cfrp = new CardFillingReportPojo();
                cfrp.setCardnumber(vec.get(0).toString());
                cfrp.setCardId(vec.get(1).toString());
                if (map.containsKey(vec.get(2).toString().substring(2, 4)) && map.get(vec.get(2).toString().substring(2, 4)).toString().equalsIgnoreCase("CA")) {
                    cfrp.setSavingacno(" ");
                    cfrp.setCurrentacno(vec.get(2).toString());
                    cfrp.setCcacno(" ");
                } else if (map.containsKey(vec.get(2).toString().substring(2, 4)) && map.get(vec.get(2).toString().substring(2, 4)).toString().equalsIgnoreCase("SB")) {
                    cfrp.setSavingacno(vec.get(2).toString());
                    cfrp.setCurrentacno(" ");
                    cfrp.setCcacno(" ");
                } else {
                    cfrp.setSavingacno(" ");
                    cfrp.setCurrentacno(" ");
                    cfrp.setCcacno(vec.get(2).toString());
                }
                if (vec.get(3).toString().length() > 18) {
                    cfrp.setEmbossedname(vec.get(3).toString().substring(0, 18).toUpperCase());
                } else {
                    cfrp.setEmbossedname(vec.get(3).toString().toUpperCase());
                }
                if (vec.get(4).toString().length() > 40) {
                    cfrp.setLastName(vec.get(4).toString().substring(0, 40));
                } else {
                    cfrp.setLastName(vec.get(4).toString());
                }
                if (vec.get(5).toString().length() > 20) {
                    cfrp.setFirstName(vec.get(5).toString().substring(0, 20));
                } else {
                    cfrp.setFirstName(vec.get(5).toString());
                }
                if (vec.get(6).toString().length() > 20) {
                    cfrp.setMiddleName(vec.get(6).toString().substring(0, 20));
                } else {
                    cfrp.setMiddleName(vec.get(6).toString());
                }
                if (vec.get(7).toString().length() > 25) {
                    cfrp.setAddressLine1(vec.get(7).toString().substring(0, 25));
                } else {
                    cfrp.setAddressLine1(vec.get(7).toString());
                }
                if (vec.get(8).toString().length() > 25) {
                    cfrp.setAddressLine2(vec.get(8).toString().substring(0, 25));
                } else {
                    cfrp.setAddressLine2(vec.get(8).toString());
                }
                if (vec.get(9).toString().length() > 25) {
                    cfrp.setAddressLine3(vec.get(9).toString().substring(0, 25));
                } else {
                    cfrp.setAddressLine3(vec.get(9).toString());
                }
                cfrp.setCity(vec.get(10).toString());
                cfrp.setState(vec.get(11).toString());
                if (vec.get(12).toString().length() == 6) {
                    cfrp.setPincode(vec.get(12).toString());
                } else {
                    cfrp.setPincode("");
                }
                cfrp.setAcholdermobileno(vec.get(13).toString());
                cfrp.setBranchid(vec.get(14).toString());
                if (vec.get(15).toString().length() > 20) {
                    cfrp.setBranchname(vec.get(15).toString().substring(0, 20));
                } else {
                    cfrp.setBranchname(vec.get(15).toString());
                }
                if (vec.get(16).toString() != null && !(vec.get(16).toString().equalsIgnoreCase(""))) {
                    cfrp.setSmsalert(vec.get(16).toString());
                } else {
                    cfrp.setSmsalert("N");
                }
                cfrp.setDateOfBirth(vec.get(17).toString());
                if (!vec.get(18).toString().equalsIgnoreCase("")) {
                    if (ParseFileUtil.isAlphabet(vec.get(18).toString().substring(0, 5))
                            && ParseFileUtil.isNumeric(vec.get(18).toString().substring(5, 9))
                            && ParseFileUtil.isAlphabet(vec.get(18).toString().substring(9))) {
                        cfrp.setPan(vec.get(18).toString());
                    } else {
                        cfrp.setPan(" ");
                    }
                } else {
                    cfrp.setPan(" ");
                }
                if (!vec.get(19).toString().equalsIgnoreCase("")) {
                    if (ParseFileUtil.isAlphabet(vec.get(19).toString().substring(0, 4))
                            && ParseFileUtil.isNumeric(vec.get(19).toString().substring(4, 9))
                            && ParseFileUtil.isAlphabet(vec.get(19).toString().substring(9))) {
                        cfrp.setTan(vec.get(19).toString());
                    } else {
                        cfrp.setTan("");
                    }
                } else {
                    cfrp.setTan("");
                }
                if (ParseFileUtil.isNumeric(vec.get(20).toString())) {
                    cfrp.setAadharno(vec.get(20).toString());
                } else {
                    cfrp.setAadharno("");
                }

                dataList.add(cfrp);

            }
        }
        }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }
}
