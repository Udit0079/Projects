/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.adaptor.ObjectAdaptorCustomer;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.NpciReturnConstant;
import com.cbs.dao.master.AbbParameterInfoDAO;
import com.cbs.dao.master.CBSCustomerMasterDetailDAO;
import com.cbs.dao.master.ParameterinfoReportDAO;
import com.cbs.dao.neftrtgs.CbsNpciInwardDAO;
import com.cbs.dao.neftrtgs.CbsNpciInwardNonAadhaarDAO;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.NpciInwardDto;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.report.AccwiesECSACHReportPojo;
import com.cbs.dto.report.AchEcsResponseStatusReportPojo;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.master.AbbParameterInfo;
import com.cbs.entity.neftrtgs.CbsNpciInward;
import com.cbs.entity.neftrtgs.CbsNpciInwardNonAadhaar;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.npci.h2h.H2HNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.AadharLpgStatusPojo;
import com.cbs.pojo.AdharRegistrationDetailPojo;
import com.cbs.pojo.NpciInputPojo;
import com.cbs.pojo.neftrtgs.NeftRtgsReportPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.Validator;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "NpciMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class NpciMgmtFacade implements NpciMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private InterBranchTxnFacadeRemote ibtRemote;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private CommonReportMethodsRemote reportRemote;
    @EJB
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    @EJB
    private CustomerManagementFacadeRemote customerRemote;
    @EJB
    private H2HNpciMgmtFacadeRemote h2hNpciRemote;
    NumberFormat formatter = new DecimalFormat("#.##");
    Date curDt = new Date();
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymdSql = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @PostConstruct
    private void loadWebServicesConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "WSLOCATION Properties File In NpciMgmtFacade" + ex.getMessage());
        }
    }

    public String npciRegistration(String custId, String mappingStatus, String enterBy,
            String regType) throws ApplicationException {
        //Mode--> F-First Time Registration, U-Updation, D-Deactivation
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (custId == null || custId.equals("")) {
                throw new ApplicationException("There is no proper customer id-->" + custId);
            }
            if (!(mappingStatus.equals("") || mappingStatus.equalsIgnoreCase("I"))) {    //It should be "" or "I"(In case of de-activation).
                throw new ApplicationException("There should be proper mapping status.");
            }
            if (enterBy == null || enterBy.equals("")) {
                throw new ApplicationException("There should be proper userid-->" + enterBy);
            }
            if (!(regType.equalsIgnoreCase("AD") || regType.equalsIgnoreCase("NA"))) {
                throw new ApplicationException("There should be proper registration type-->" + regType);
            }
            if (regType.equalsIgnoreCase("AD")) {   //Aadhaar Registration.
                aadhaarRegistration(custId, mappingStatus, enterBy, regType);
            } else if (regType.equalsIgnoreCase("NA")) {    //Non-Aadhaar Registration.
                nonAadhaarRegistration(custId, enterBy, regType);
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

    //Before Moved Out Code
//    public String aadhaarRegistration(String custId, String mappingStatus, String enterBy,
//            String regType) throws ApplicationException {
//        try {
//            List dataList = em.createNativeQuery("select ifnull(aadhaar_no,'') as aadharNo,ifnull(primarybrcode,'') "
//                    + "as brcode,ifnull(mandate_flag,'') as mandate_flag,ifnull(mandate_date,'') as mandate_date,"
//                    + "ifnull(aadhaar_lpg_acno,'') from cbs_customer_master_detail where "
//                    + "customerid='" + custId + "'").getResultList();
//            if (dataList == null || dataList.isEmpty()) {
//                throw new ApplicationException("There is no detail in cbs customer master detail "
//                        + "for customer id-->" + custId);
//            }
//            Vector element = (Vector) dataList.get(0);
//            String aadharNo = element.get(0).toString();
//            String primaryBrCode = element.get(1).toString();
//            String mandateFlag = element.get(2).toString();
//            String mandateDate = element.get(3).toString();
//            String aadharAcno = element.get(4).toString();
//
//            if (aadharNo.equals("")) {
//                throw new ApplicationException("Aadhar No UnAvailable For Customer Id-->" + custId);
//            }
//            if (primaryBrCode.equals("") || primaryBrCode.length() != 2) {
//                throw new ApplicationException("There is no proper primary br code for customer id-->" + custId);
//            }
//            if (aadharAcno.equals("") || aadharAcno.length() != 12) {
//                throw new ApplicationException("There is no proper mapped a/c for customer id-->" + custId);
//            }
//            if (mandateFlag.equalsIgnoreCase("Y")) {
//                if (mandateDate == null || mandateDate.equals("") || mandateDate.length() != 10
//                        || (new Validator().validateDate_dd_mm_yyyy(mandateDate) == false)) {
//                    throw new ApplicationException("Mandate date is not proper for customer id-->" + custId);
//                }
//            } else {
//                mandateFlag = "N";
//                mandateDate = "";
//            }
//            //Evaluate the OD flag and OD date on a/c basis. If OD flag is y then mandate flag should be Y.
//            dataList = em.createNativeQuery("select aadhar_no,mandate_flag,reg_type,status,mapping_status "
//                    + "from cbs_aadhar_registration where cust_id='" + custId + "'").getResultList();
//            if (dataList.isEmpty()) {
//                int n = em.createNativeQuery("insert into cbs_aadhar_registration(cust_id,aadhar_no,status,"
//                        + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
//                        + "enter_by,dt,tran_time,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,reg_type,"
//                        + "reject_reason,res_update_by,res_update_dt,res_file_name) values('" + custId + "',"
//                        + "'" + aadharNo + "','F','" + ymdms.format(new Date()) + "','" + mandateFlag + "',"
//                        + "'" + mandateDate + "','N','','" + mappingStatus + "','" + primaryBrCode + "',"
//                        + "'" + enterBy + "','" + ymd.format(curDt) + "',now(),'','','','','" + regType + "',"
//                        + "'','','" + ymd.format(curDt) + "','')").executeUpdate();
//                if (n <= 0) {
//                    throw new ApplicationException("Problem In CBS Aadhar Registration Insertion.");
//                }
//            } else {
//                element = (Vector) dataList.get(0);
//                String mapAadharNo = element.get(0).toString();
//                String mapFlagInRegistration = element.get(1).toString();
//                String mapRegType = element.get(2).toString();
//                String mapStatus = element.get(3).toString();
//                String mapMappingStatus = element.get(4).toString();
//
//                if (mapRegType.equalsIgnoreCase("NA")) {
//                    updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
//                            mappingStatus, regType, "", "", "", "");
//                } else if (mapRegType.equalsIgnoreCase("AD")) {
//                    if (mandateFlag.equalsIgnoreCase("Y") && (new Validator().validateDate_dd_mm_yyyy(mandateDate) == true)
//                            && (!((mapStatus.equalsIgnoreCase("E") || mapStatus.equalsIgnoreCase("D")) && mapMappingStatus.equalsIgnoreCase("I")))) {
//                        if (mapFlagInRegistration.equalsIgnoreCase("N")
//                                || (mapFlagInRegistration.equalsIgnoreCase("Y") && (!aadharNo.equals(mapAadharNo)))) {
//                            updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
//                                    mappingStatus, regType, "", "", "", "");
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return "true";
//    }
    // new change by rahul
    public String aadhaarRegistration(String custId, String mappingStatus, String enterBy,
            String regType) throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("select ifnull(aadhaar_no,'') as aadharNo,ifnull(primarybrcode,'') "
                    + "as brcode,ifnull(mandate_flag,'') as mandate_flag,ifnull(mandate_date,'') as mandate_date,"
                    + "ifnull(aadhaar_lpg_acno,''),ifnull(aadhaar_bank_iin,'') as aadhaarBankIin from cbs_customer_master_detail where "
                    + "customerid='" + custId + "'").getResultList();
            if (dataList == null || dataList.isEmpty()) {
                throw new ApplicationException("There is no detail in cbs customer master detail "
                        + "for customer id-->" + custId);
            }
            Vector element = (Vector) dataList.get(0);
            String aadharNo = element.get(0).toString();
            String primaryBrCode = element.get(1).toString();
            String mandateFlag = element.get(2).toString();
            String mandateDate = element.get(3).toString();
            String aadharAcno = element.get(4).toString();
            String aadhaarBankIin = element.get(5).toString();

            if (aadharNo.equals("")) {
                throw new ApplicationException("Aadhar No UnAvailable For Customer Id-->" + custId);
            }
            if (primaryBrCode.equals("") || primaryBrCode.length() != 2) {
                throw new ApplicationException("There is no proper primary br code for customer id-->" + custId);
            }
            if (aadharAcno.equals("") || aadharAcno.length() != 12) {
                throw new ApplicationException("There is no proper mapped a/c for customer id-->" + custId);
            }
            if (mandateFlag.equalsIgnoreCase("Y")) {
                if (mandateDate == null || mandateDate.equals("") || mandateDate.length() != 10
                        || (new Validator().validateDate_dd_mm_yyyy(mandateDate) == false)) {
                    throw new ApplicationException("Mandate date is not proper for customer id-->" + custId);
                }
            } else {
                mandateFlag = "N";
                mandateDate = "";
            }
            //Evaluate the OD flag and OD date on a/c basis. If OD flag is y then mandate flag should be Y.
            dataList = em.createNativeQuery("select aadhar_no,mandate_flag,reg_type,status,mapping_status,aadhaar_bank_iin,ifnull(reason_code,'') "
                    + "from cbs_aadhar_registration where cust_id='" + custId + "'").getResultList();
            if (dataList.isEmpty()) {
                int n = em.createNativeQuery("insert into cbs_aadhar_registration(cust_id,aadhar_no,status,"
                        + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                        + "enter_by,dt,tran_time,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,reg_type,"
                        + "reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) values('" + custId + "',"
                        + "'" + aadharNo + "','F','" + ymdms.format(new Date()) + "','" + mandateFlag + "',"
                        + "'" + mandateDate + "','N','','" + mappingStatus + "','" + primaryBrCode + "',"
                        + "'" + enterBy + "','" + ymd.format(curDt) + "',now(),'','','','','" + regType + "',"
                        + "'','','" + ymd.format(curDt) + "','','" + aadhaarBankIin + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In CBS Aadhar Registration Insertion.");
                }
            } else {
                element = (Vector) dataList.get(0);
                String mapAadharNo = element.get(0).toString();
                String mapFlagInRegistration = element.get(1).toString();
                String mapRegType = element.get(2).toString();
                String mapStatus = element.get(3).toString();
                String mapMappingStatus = element.get(4).toString();
                String mapaadhaarBankIin = element.get(5).toString();
                String reasonCode = element.get(6).toString();

                if (mapRegType.equalsIgnoreCase("NA")) {
                    updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
                            mappingStatus, regType, "", "", "", "", aadhaarBankIin);
                } else if (mapRegType.equalsIgnoreCase("AD")) {
                    if (mapStatus.equalsIgnoreCase("D") && (mapMappingStatus.equalsIgnoreCase("M"))) {
                        int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
                                + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
                                + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
                                + "reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) select cust_id,aadhar_no,"
                                + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                                + "enter_by,dt,tran_time,'" + enterBy + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
                                + "dest_bank_acno,reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin from "
                                + "cbs_aadhar_registration where cust_id='" + custId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In CBS Aadhar Registration History Insertion In Moved Out.");
                        }
                        n = em.createNativeQuery("update cbs_aadhar_registration set aadhar_no='" + aadharNo + "',status='F',"
                                + "rrn='" + ymdms.format(new Date()) + "',mandate_flag='" + mandateFlag + "',"
                                + "mandate_date='" + mandateDate + "',"
                                + "mapping_status='" + mappingStatus + "',enter_by='" + enterBy + "',dt='" + ymd.format(curDt) + "',"
                                + "tran_time=now(),reg_type='" + regType + "',reject_reason='',res_update_by='',"
                                + "res_update_dt='" + ymd.format(curDt) + "',res_file_name='',custid_brncode='" + primaryBrCode + "',aadhaar_bank_iin='" + aadhaarBankIin + "' where "
                                + "cust_id='" + custId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In CBS Aadhar Registration Updation.");
                        }

//                       }else if(mapStatus.equalsIgnoreCase("I")&& reasonCode.equalsIgnoreCase("26")){
                    } else if ((mapStatus.equalsIgnoreCase("I") && mapMappingStatus.trim().equalsIgnoreCase("") && !reasonCode.equalsIgnoreCase("26"))
                            || (mapStatus.equalsIgnoreCase("F") && mapMappingStatus.trim().equalsIgnoreCase(""))
                            || (mapStatus.equalsIgnoreCase("U") && mapMappingStatus.trim().equalsIgnoreCase(""))) { //Update Condition
                        updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
                                mappingStatus, regType, "", "", "", "", aadhaarBankIin);
                    }

//                    } else if ((mapStatus.equalsIgnoreCase("I") && mapMappingStatus.trim().equalsIgnoreCase(""))
//                            || (mapStatus.equalsIgnoreCase("F") && mapMappingStatus.trim().equalsIgnoreCase(""))
//                            || (mapStatus.equalsIgnoreCase("U") && mapMappingStatus.trim().equalsIgnoreCase(""))) { //Update Condition
//                        updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
//                                mappingStatus, regType, "", "", "", "", aadhaarBankIin);
//                    }else if(mapStatus.equalsIgnoreCase("I")&& reasonCode.equalsIgnoreCase("26")){
//                        
//                    }
//                    else if (mandateFlag.equalsIgnoreCase("Y") && (new Validator().validateDate_dd_mm_yyyy(mandateDate) == true)
//                            && (!((mapStatus.equalsIgnoreCase("E") || mapStatus.equalsIgnoreCase("D")) && mapMappingStatus.equalsIgnoreCase("I")))) {
//                        if (mapFlagInRegistration.equalsIgnoreCase("N")
//                                || (mapFlagInRegistration.equalsIgnoreCase("Y") && (!aadharNo.equals(mapAadharNo)))) {
//                            updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
//                                    mappingStatus, regType, "", "", "", "", aadhaarBankIin);
//                        }
//                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

//     public String aadhaarRegistration(String custId, String mappingStatus, String enterBy,
//            String regType) throws ApplicationException {
//        try {
//            List dataList = em.createNativeQuery("select ifnull(aadhaar_no,'') as aadharNo,ifnull(primarybrcode,'') "
//                    + "as brcode,ifnull(mandate_flag,'') as mandate_flag,ifnull(mandate_date,'') as mandate_date,"
//                    + "ifnull(aadhaar_lpg_acno,'') from cbs_customer_master_detail where "
//                    + "customerid='" + custId + "'").getResultList();
//            if (dataList == null || dataList.isEmpty()) {
//                throw new ApplicationException("There is no detail in cbs customer master detail "
//                        + "for customer id-->" + custId);
//            }
//            Vector element = (Vector) dataList.get(0);
//            String aadharNo = element.get(0).toString();
//            String primaryBrCode = element.get(1).toString();
//            String mandateFlag = element.get(2).toString();
//            String mandateDate = element.get(3).toString();
//            String aadharAcno = element.get(4).toString();
//
//            if (aadharNo.equals("")) {
//                throw new ApplicationException("Aadhar No UnAvailable For Customer Id-->" + custId);
//            }
//            if (primaryBrCode.equals("") || primaryBrCode.length() != 2) {
//                throw new ApplicationException("There is no proper primary br code for customer id-->" + custId);
//            }
//            if (aadharAcno.equals("") || aadharAcno.length() != 12) {
//                throw new ApplicationException("There is no proper mapped a/c for customer id-->" + custId);
//            }
//            if (mandateFlag.equalsIgnoreCase("Y")) {
//                if (mandateDate == null || mandateDate.equals("") || mandateDate.length() != 10
//                        || (new Validator().validateDate_dd_mm_yyyy(mandateDate) == false)) {
//                    throw new ApplicationException("Mandate date is not proper for customer id-->" + custId);
//                }
//            } else {
//                mandateFlag = "N";
//                mandateDate = "";
//            }
//            //Evaluate the OD flag and OD date on a/c basis. If OD flag is y then mandate flag should be Y.
//            dataList = em.createNativeQuery("select aadhar_no,mandate_flag,reg_type,status,mapping_status "
//                    + "from cbs_aadhar_registration where cust_id='" + custId + "'").getResultList();
//            if (dataList.isEmpty()) {
//                int n = em.createNativeQuery("insert into cbs_aadhar_registration(cust_id,aadhar_no,status,"
//                        + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
//                        + "enter_by,dt,tran_time,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,reg_type,"
//                        + "reject_reason,res_update_by,res_update_dt,res_file_name) values('" + custId + "',"
//                        + "'" + aadharNo + "','F','" + ymdms.format(new Date()) + "','" + mandateFlag + "',"
//                        + "'" + mandateDate + "','N','','" + mappingStatus + "','" + primaryBrCode + "',"
//                        + "'" + enterBy + "','" + ymd.format(curDt) + "',now(),'','','','','" + regType + "',"
//                        + "'','','" + ymd.format(curDt) + "','')").executeUpdate();
//                if (n <= 0) {
//                    throw new ApplicationException("Problem In CBS Aadhar Registration Insertion.");
//                }
//            } else {
//                element = (Vector) dataList.get(0);
//                String mapAadharNo = element.get(0).toString();
//                String mapFlagInRegistration = element.get(1).toString();
//                String mapRegType = element.get(2).toString();
//                String mapStatus = element.get(3).toString();
//                String mapMappingStatus = element.get(4).toString();
//
//                if (mapRegType.equalsIgnoreCase("NA")) {
//                    updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
//                            mappingStatus, regType, "", "", "", "");
//                } else if (mapRegType.equalsIgnoreCase("AD")) {
//                    if (mapStatus.equalsIgnoreCase("D") && (mapMappingStatus.equalsIgnoreCase("M"))) {
//                        int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
//                                + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
//                                + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
//                                + "reg_type,reject_reason,res_update_by,res_update_dt,res_file_name) select cust_id,aadhar_no,"
//                                + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
//                                + "enter_by,dt,tran_time,'" + enterBy + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
//                                + "dest_bank_acno,reg_type,reject_reason,res_update_by,res_update_dt,res_file_name from "
//                                + "cbs_aadhar_registration where cust_id='" + custId + "'").executeUpdate();
//                        if (n <= 0) {
//                            throw new ApplicationException("Problem In CBS Aadhar Registration History Insertion In Moved Out.");
//                        }
//                        n = em.createNativeQuery("update cbs_aadhar_registration set aadhar_no='" + aadharNo + "',status='F',"
//                                + "rrn='" + ymdms.format(new Date()) + "',mandate_flag='" + mandateFlag + "',"
//                                + "mandate_date='" + mandateDate + "',"
//                                + "mapping_status='" + mappingStatus + "',enter_by='" + enterBy + "',dt='" + ymd.format(curDt) + "',"
//                                + "tran_time=now(),reg_type='" + regType + "',reject_reason='',res_update_by='',"
//                                + "res_update_dt='" + ymd.format(curDt) + "',res_file_name='',custid_brncode='" + primaryBrCode + "' where "
//                                + "cust_id='" + custId + "'").executeUpdate();
//                        if (n <= 0) {
//                            throw new ApplicationException("Problem In CBS Aadhar Registration Updation.");
//                        }
//
//                    } else if (mandateFlag.equalsIgnoreCase("Y") && (new Validator().validateDate_dd_mm_yyyy(mandateDate) == true)
//                            && (!((mapStatus.equalsIgnoreCase("E") || mapStatus.equalsIgnoreCase("D")) && mapMappingStatus.equalsIgnoreCase("I")))) {
//                        if (mapFlagInRegistration.equalsIgnoreCase("N")
//                                || (mapFlagInRegistration.equalsIgnoreCase("Y") && (!aadharNo.equals(mapAadharNo)))) {
//                            updateAadhaarRegistration(enterBy, custId, aadharNo, mandateFlag, mandateDate,
//                                    mappingStatus, regType, "", "", "", "");
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return "true";
//    }
    public String updateAadhaarRegistration(String enterBy, String custId, String aadharNo,
            String mandateFlag, String mandateDate, String mappingStatus, String regType, String lpgId,
            String responderCode, String destbankIfsc, String destBankAcno, String aadhaarBankIin) throws ApplicationException {
        try {
            int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
                    + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
                    + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
                    + "reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) select cust_id,aadhar_no,"
                    + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                    + "enter_by,dt,tran_time,'" + enterBy + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
                    + "dest_bank_acno,reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin from "
                    + "cbs_aadhar_registration where cust_id='" + custId + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In CBS Aadhar Registration History Insertion.");
            }
            String odFlag = "N", odDt = "";
            if (regType.equals("NA")) {
                odFlag = "";
            }
            n = em.createNativeQuery("update cbs_aadhar_registration set aadhar_no='" + aadharNo + "',status='U',"
                    + "rrn='" + ymdms.format(new Date()) + "',mandate_flag='" + mandateFlag + "',"
                    + "mandate_date='" + mandateDate + "',od_flag='" + odFlag + "',od_date='" + odDt + "',"
                    + "mapping_status='" + mappingStatus + "',enter_by='" + enterBy + "',dt='" + ymd.format(curDt) + "',"
                    + "tran_time=now(),lpg_id='" + lpgId + "',responder_code='" + responderCode + "',"
                    + "dest_bank_ifsc='" + destbankIfsc + "',dest_bank_acno='" + destBankAcno + "',"
                    + "reg_type='" + regType + "',reject_reason='',res_update_by='',"
                    + "res_update_dt='" + ymd.format(curDt) + "',res_file_name='',aadhaar_bank_iin='" + aadhaarBankIin + "' where "
                    + "cust_id='" + custId + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In CBS Aadhar Registration Updation.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String nonAadhaarRegistration(String custId, String enterBy, String regType) throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("select ifnull(aadhaar_no,'') as aadharNo,ifnull(primarybrcode,'') as "
                    + "brcode,ifnull(aadhaar_lpg_acno,'') as acno,ifnull(lpg_id,'') as lpg_id from "
                    + "cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
            if (dataList == null || dataList.isEmpty()) {
                throw new ApplicationException("There is no detail in cbs customer master detail "
                        + "for customer id-->" + custId);
            }
            Vector element = (Vector) dataList.get(0);
            String aadharNo = element.get(0).toString();
            String primaryBrCode = element.get(1).toString();
            String aadharAcno = element.get(2).toString();
            String lpgId = element.get(3).toString();
            if (primaryBrCode.equals("") || primaryBrCode.length() != 2) {
                throw new ApplicationException("There is no proper primary br code for customer id-->" + custId);
            }
            if (aadharAcno.equals("") || aadharAcno.length() != 12) {
                throw new ApplicationException("There is no proper mapped a/c for customer id-->" + custId);
            }
            if (lpgId.equals("") || lpgId.length() != 17) {
                throw new ApplicationException("Lpg Id UnAvailable For Customer Id-->" + custId);
            }
            dataList = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no='008' "
                    + "and ref_code='" + lpgId.substring(0, 1) + "'").getResultList();
            if (dataList == null || dataList.isEmpty()) {
                throw new ApplicationException("Please define data in Cbs Ref Rec Type For 008 or your Lpg Id is not correct.");
            }
            element = (Vector) dataList.get(0);
            String responderCode = element.get(0).toString().trim();
            if (responderCode.equals("")) {
                throw new ApplicationException("Please define proper data in Cbs Ref Rec Type For 008.");
            }

            dataList = em.createNativeQuery("select ifnull(ifsc_code,'') from  branchmaster where "
                    + "brncode = " + Integer.parseInt(primaryBrCode) + "").getResultList();
            if (dataList == null || dataList.isEmpty()) {
                throw new ApplicationException("Please define ifsc code in branchmaster for::" + primaryBrCode);
            }
            element = (Vector) dataList.get(0);
            String destBankIfsc = element.get(0).toString().trim();
            if (destBankIfsc.equals("") || destBankIfsc.length() != 11) {
                throw new ApplicationException("Ifsc code in branchmaster for "
                        + "branchcode " + primaryBrCode + " is not proper.");
            }

            dataList = em.createNativeQuery("select aadhar_no,lpg_id,reg_type from cbs_aadhar_registration "
                    + "where cust_id='" + custId + "'").getResultList();
            if (dataList.isEmpty()) {
                int n = em.createNativeQuery("insert into cbs_aadhar_registration(cust_id,aadhar_no,status,rrn,"
                        + "mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,dt,"
                        + "tran_time,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,reg_type,reject_reason,"
                        + "res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) values('" + custId + "','" + aadharNo + "',"
                        + "'F','" + ymdms.format(new Date()) + "','','','','','','" + primaryBrCode + "',"
                        + "'" + enterBy + "','" + ymd.format(curDt) + "',now(),'" + lpgId + "','" + responderCode + "',"
                        + "'" + destBankIfsc + "','" + aadharAcno + "','" + regType + "','','',"
                        + "'" + ymd.format(curDt) + "','','')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In CBS Aadhar Registration Insertion.");
                }
            } else {
                updateAadhaarRegistration(enterBy, custId, aadharNo, "", "", "", "", regType, lpgId,
                        responderCode, destBankIfsc, aadharAcno);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    //Mapper File Generation.
    public String generateNpciFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String rrn = "", aadharNo = "", mandateFlag = "", mandateDt = "";
        String mappingStatus = "", odFlag = "", odDt = "", headerId = "";
        String detailId = "", uploadingOrMappedIIN = "", aadharLocation = "";
        String fileNo = "", totalRecordNo = "", bankCode = "", genFileName = "", custId = "", iin = "";
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = elem.get(0).toString().trim();

            List dataList = em.createNativeQuery("select rrn,aadhar_no,mandate_flag,mandate_date,"
                    + "mapping_status,od_flag,od_date,cust_id,aadhaar_bank_iin from cbs_aadhar_registration "
                    + "where dt='" + ymd.format(dmy.parse(fileGenDt)) + "' and status "
                    + "in('F','U') and reg_type='AD'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("NPCI-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NPCI-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("NPCI-DRI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NPCI-DRI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            List list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, Aadhar Location and Bank Code.");
            }
            uploadingOrMappedIIN = ele.get(0).toString().trim();    //Make to 9 digit.
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim();    //As it is

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and file_gen_type='MF'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();  //Make to 5 digit.
            }
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
                genFileName = "ACH-CM-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-" + ymdOne.format(curDt) + "-"
                        + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-MAP.txt";
            } else {
                genFileName = "ACH-CM-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-" + ymdOne.format(curDt) + "-"
                        + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-MAP.txt";
            }
            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + orgnBrCode + "','MF')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }

            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to 5 digit.
            //Header Preparation.
            FileWriter fw = null;
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
            }
//            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = headerId + ParseFileUtil.addTrailingZeros(uploadingOrMappedIIN, 9)
                    + ParseFileUtil.addSuffixSpaces(npciUserName, 30) + ymdOne.format(curDt)
                    + ParseFileUtil.addTrailingZeros(fileNo, 5) + ParseFileUtil.addTrailingZeros(totalRecordNo, 5)
                    + ParseFileUtil.addSuffixSpaces("", 301) + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                rrn = element.get(0).toString().trim();
                aadharNo = element.get(1).toString().trim();
                mandateFlag = element.get(2).toString().trim();
                mandateDt = element.get(3).toString().trim();
                mappingStatus = element.get(4).toString().trim();
                odFlag = element.get(5).toString().trim();
                odDt = element.get(6).toString().trim();
                custId = element.get(7).toString().trim();
                iin = element.get(8).toString().trim();


                if (mandateFlag.equalsIgnoreCase("N")) {
                    mandateDt = ParseFileUtil.addSuffixSpaces("", 8);
                } else if (mandateFlag.equalsIgnoreCase("Y")) {
                    mandateDt = ymdOne.format(dmy.parse(mandateDt));
                }
                if (!mappingStatus.equals("I")) {
                    mappingStatus = ParseFileUtil.addSuffixSpaces("", 1);
                } else {
                    iin = "";
                }
                if (iin.equalsIgnoreCase("")) {
                    iin = ParseFileUtil.addTrailingSpaces("", 9);
                } else {
                    iin = ParseFileUtil.addTrailingZeros(iin, 9);
                }

                if (odFlag.equalsIgnoreCase("N")) {
                    odDt = ParseFileUtil.addSuffixSpaces("", 8);
                } else if (odFlag.equalsIgnoreCase("Y")) {
                    odDt = ymdOne.format(dmy.parse(odDt));
                }

//                String individualStr = detailId + rrn + ParseFileUtil.addTrailingZeros(aadharNo, 15)
//                        + ParseFileUtil.addTrailingZeros(uploadingOrMappedIIN, 9) + mandateFlag + mandateDt
//                        + mappingStatus + odFlag + odDt + ParseFileUtil.addSuffixSpaces("", 300) + "\n";

                String individualStr = detailId + rrn + ParseFileUtil.addTrailingZeros(aadharNo, 15)
                        + ParseFileUtil.addTrailingZeros(uploadingOrMappedIIN, 9) + mandateFlag + mandateDt
                        + mappingStatus + odFlag + odDt + iin
                        + ParseFileUtil.addSuffixSpaces("", 291) + "\n";

                fw.write(individualStr);
                //Status Updation.
                String updateStatus = "R";
                if (mappingStatus.equalsIgnoreCase("I")) {
                    updateStatus = "E";
                }

                n = em.createNativeQuery("update cbs_aadhar_registration set "
                        + "status='" + updateStatus + "' where cust_id='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Status Updation.");
                }
            }
            fw.close();
            //In case of H2H NPCI Only
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
            }
            //End Only
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

    //Generate APB Return Files.(APB)
    public String generateNpciReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, Double seqNo, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String headerId = "", detailReturnId = "", uploadingOrMappedIIN = "", aadharLocation = "";
        String fileNo = "", totalRecordNo = "", bankCode = "", genFileName = "";
        try {
            ut.begin();

            if (processingMode.equalsIgnoreCase("H2H")) {
                List list = em.createNativeQuery("select * from cbs_npci_mapper_files where "
                        + "FILE_GEN_TYPE='RF' AND DATE_OF_FILE_GEN='" + ymdSql.format(dmy.parse(fileGenDt)) + "' AND FILE_GEN_SEQN='" + seqNo + "'").getResultList();
                if (list.size() >= 1) {
                    throw new ApplicationException("ACH Return File already Generated...");
                }
            }
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

            List dataList = em.createNativeQuery("select apbs_tran_code,dest_bank_iin,dest_actype,ledger_no,"
                    + "bene_aadhaar_no,bene_name,sponsor_bank_iin,user_name_narration,user_credit_reference,"
                    + "amount,reserved_one,reserved_two,reserved_three,dest_bank_acno,return_reason_code,status,"
                    + "reason,user_no from cbs_npci_inward where settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "trs_no=" + seqNo + " and iw_type='APB'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("RETURN-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::RETURN-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("RETURN-DRI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::RETURN-DRI");
            }
            detailReturnId = paramEntity.getCode().toString().trim(); //As it is

            list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, Aadhar Location and Bank Code.");
            }
            uploadingOrMappedIIN = ele.get(0).toString().trim();    //Make to desired digit.
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim();    //As it is

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and file_gen_type='RF'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();  //Make to 5 digit.
            }
            //userName changes
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
//                genFileName = "APB-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(fileGenDt)) + "-"
//                        + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";

                genFileName = "APB-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-" + ymdOne.format(new Date()) + "-"
                        + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";
            } else {
                genFileName = "APB-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(fileGenDt)) + "-"
                        + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";
            }

            String insertnpciMapperQuery = "";
            int fileGenFlag = 0;
            if (processingMode.equalsIgnoreCase("H2H")) {
                insertnpciMapperQuery = "insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no,DATE_OF_FILE_GEN,FILE_GEN_SEQN) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','RF'," + seqNo + ",'" + ymdSql.format(dmy.parse(fileGenDt)) + "','" + seqNo + "')";
                String updateFileGenFlagQuery = "UPDATE cbs_npci_inward SET file_gen_flag = 'Y' where TRS_NO='" + seqNo + "' "
                        + "and IW_TYPE='APB' and SETTLEMENT_DATE='" + ymd.format(dmy.parse(fileGenDt)) + "'";
                fileGenFlag = em.createNativeQuery(updateFileGenFlagQuery).executeUpdate();
                if (fileGenFlag <= 0) {
                    throw new ApplicationException("problem in file_gen_flag updation...!");
                }
            } else {
                insertnpciMapperQuery = "insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','RF'," + seqNo + ")";
            }
            int n = em.createNativeQuery(insertnpciMapperQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }

            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to desired digit.
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (int i = 0; i < dataList.size(); i++) {
                ele = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(ele.get(9).toString().trim()).divide(new BigDecimal(100));
                totalSubAmt = totalSubAmt.add(individualAmt);
            }
            String amtInPaisa = "";
            totalSubAmt = totalSubAmt.multiply(new BigDecimal(100));
            int dotIndex = totalSubAmt.toString().indexOf(".");
            if (dotIndex == -1) {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().trim(), 13);
            } else {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().substring(0, dotIndex).trim(), 13);
            }
            //Header Preparation.
//            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            FileWriter fw = null;
            String headerDt = "";
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
                headerDt = ymdOne.format(new Date());
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
                headerDt = ymdOne.format(dmy.parse(fileGenDt));
            }

            String header = headerId + ParseFileUtil.addSuffixZeros("", 7) + ParseFileUtil.addSuffixSpaces("", 87)
                    + ParseFileUtil.addTrailingZeros(uploadingOrMappedIIN, 7) + ParseFileUtil.addTrailingZeros(totalRecordNo, 9)
                    + amtInPaisa + headerDt + ParseFileUtil.addSuffixSpaces("", 43) + "." + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String apbsTranCode = element.get(0).toString();
                String destBankIIN = element.get(1).toString();
                String aadhaarNo = element.get(4).toString();
                String sponserBankIIN = element.get(6).toString();
                String userCreditRef = element.get(8).toString();
                String amount = element.get(9).toString();
                String reservedOne = element.get(10).toString();
                String reservedTwo = element.get(11).toString();
                String status = element.get(15).toString();
                String reason = element.get(16).toString();
                String reasonCode = "", firstName = "", middleName = "", lastName = "", benAcno = "";
                if (status.equalsIgnoreCase("U")) {
                    reasonCode = getNpciReturnReasonCode(reason);
                }
                //Commented on 24/09/2019
//                else if (status.equalsIgnoreCase("S")) {
//                    List benList = em.createNativeQuery("select aa.cust_id,ifnull(cu.custname,'') as benName,"
//                            + "ifnull(cu.aadhaar_lpg_acno,'') as benAcno,ifnull(cu.middle_name,'') as middle_name,"
//                            + "ifnull(cu.last_name,'') as lastName from cbs_customer_master_detail cu,cbs_aadhar_registration "
//                            + "aa where cu.customerid = aa.cust_id and "
//                            + "aa.aadhar_no = '" + aadhaarNo.substring(3) + "' and (status<>'D' and mapping_status<>'M')").getResultList();
//                    if (benList.isEmpty() || benList.size() > 1) {
//                        throw new ApplicationException("Aadhar no " + aadhaarNo.substring(3) + " is mapped "
//                                + "with multiple customerid");
//                    }
//                    Vector benVec = (Vector) benList.get(0);
//                    firstName = benVec.get(1).toString();
//                    benAcno = benVec.get(2).toString();
//                    middleName = benVec.get(3).toString();
//                    lastName = benVec.get(4).toString();
//
//                    firstName = firstName.trim() + " " + middleName.trim();
//                    firstName = firstName.trim() + " " + lastName.trim();
//
//                    firstName = firstName.replaceAll("[\\W_]", " ");
//                    firstName = firstName.length() > 40 ? firstName.substring(0, 40) : firstName;
//                }

                if (reasonCode.equalsIgnoreCase("") || reasonCode.equalsIgnoreCase("01") || reasonCode.equalsIgnoreCase("53")
                        || reasonCode.equalsIgnoreCase("68") || reasonCode.equalsIgnoreCase("71")) { //All return code in which name/account_no is required(1,51-58,60,62,68,69,70,71), we are handling as per the cbs
                    List benList = em.createNativeQuery("select aa.cust_id,ifnull(cu.custname,'') as benName,"
                            + "ifnull(cu.aadhaar_lpg_acno,'') as benAcno,ifnull(cu.middle_name,'') as middle_name,"
                            + "ifnull(cu.last_name,'') as lastName from cbs_customer_master_detail cu,cbs_aadhar_registration "
                            + "aa where cu.customerid = aa.cust_id and "
                            + "aa.aadhar_no = '" + aadhaarNo.substring(3) + "' and (status<>'D' and mapping_status<>'M')").getResultList();
                    if (benList.isEmpty() || benList.size() > 1) {
                        throw new ApplicationException("Aadhar no " + aadhaarNo.substring(3) + " is mapped "
                                + "with multiple customerid");
                    }
                    Vector benVec = (Vector) benList.get(0);
                    firstName = benVec.get(1).toString();
                    benAcno = benVec.get(2).toString();
                    middleName = benVec.get(3).toString();
                    lastName = benVec.get(4).toString();

                    firstName = firstName.trim() + " " + middleName.trim();
                    firstName = firstName.trim() + " " + lastName.trim();

                    firstName = firstName.replaceAll("[\\W_]", " ");
                    firstName = firstName.length() > 40 ? firstName.substring(0, 40) : firstName;
                }

                String userNo = element.get(17).toString();
                String userNameAndNarration = element.get(7).toString();
                String individualStr = apbsTranCode + destBankIIN + ParseFileUtil.addSuffixZeros("", 2)
                        + ParseFileUtil.addSuffixSpaces("", 3) + aadhaarNo + ParseFileUtil.addSuffixSpaces(firstName, 40)
                        + sponserBankIIN + userNo + ParseFileUtil.addSuffixSpaces(userNameAndNarration, 20)
                        + ParseFileUtil.addSuffixSpaces(userCreditRef, 13) + amount
                        + ParseFileUtil.addSuffixSpaces(reservedOne, 10) + ParseFileUtil.addSuffixSpaces(reservedTwo, 10)
                        + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces(benAcno, 20)
                        + ParseFileUtil.addSuffixSpaces(reasonCode, 2) + "\n";

                fw.write(individualStr);
            }
            fw.close();
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
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

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fileShowDt, Double seqNo) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = null;
            if (fileType.equalsIgnoreCase("RF") || fileType.equalsIgnoreCase("CHI") || fileType.equalsIgnoreCase("EHI")) {  //RF-APB Inw Return, CHI-ACH Inw Return, EHI-ECS Inw Return
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                        + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + fileType + "' and seq_no=" + seqNo + "").getResultList();
            } else if (fileType.equalsIgnoreCase("MF")) {   //Mapper File
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                        + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + fileType + "'").getResultList();
            }
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

    //APBS Inward File Uploading.
//    public String npciInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
//            String uploadingUserName, String todayDt) throws ApplicationException {
//        String message = "";
//        BigDecimal totalAmount = new BigDecimal("0");
//        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
//        CbsNpciInwardDAO cbsNpciInwardDAO = new CbsNpciInwardDAO(em);
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            List userList = em.createNativeQuery("select npciusername from securityinfo where "
//                    + "userid='" + uploadingUserName + "'").getResultList();
//            Vector elem = (Vector) userList.get(0);
//            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
//                throw new ApplicationException("You are not authorized person to upload the files.");
//            }
//            if (inputList.isEmpty()) {
//                throw new ApplicationException("There is no data found to upload !");
//            }
//            Float trsno = ftsRemote.getTrsNo();
//            for (int i = 0; i < inputList.size(); i++) {
//                message = "unsuccess";
//                NpciInwardDto dto = inputList.get(i);
//                List<CbsNpciInward> isEntity = cbsNpciInwardDAO.findByUserCreditReferenceAndIwType(dto.getUserCreditRef().trim(), "APB");
//                if (!isEntity.isEmpty()) {
//                    message = "duplicate";
//                }
//                CbsNpciInward object = new CbsNpciInward();
//
//                object.setApbsTranCode(dto.getApbsTranCode().trim());
//                object.setDestBankIin(dto.getDestBankIIN().trim());
//                object.setDestActype(dto.getDestAcType().trim());
//                object.setLedgerNo(dto.getLedgerNo().trim());
//                object.setBeneAadhaarNo(dto.getBeneAadhaarNo().trim());
//
//                object.setBeneName(dto.getBeneName().trim());
//                object.setSponsorBankIin(dto.getSponsorBankIIN().trim());
//                object.setUserNo(dto.getUserNumber().trim());
//                object.setUserNameNarration(dto.getUserName().trim());
//                object.setUserCreditReference(dto.getUserCreditRef().trim());
//
//                object.setAmount(dto.getAmount().trim());
//                object.setReservedOne(dto.getReservedOne().trim());
//                object.setReservedTwo(dto.getReservedTwo().trim());
//                object.setReservedThree(dto.getReservedThree().trim());
//                object.setDestBankAcno(dto.getDestBankAcno().trim());
//
//                object.setReturnReasonCode(dto.getRetReasonCode().trim());
//                Date txnDt = dmy.parse(todayDt);
//                object.setTranDate(txnDt);
//                object.setValueDate(txnDt);
//                object.setTranTime(new Date());
//                object.setEnterBy(uploadingUserName.trim());
//                object.setAuthBy(uploadingUserName.trim());
//                object.setTrsNo(trsno.doubleValue());
//                object.setSettlementDate(ymd.parse(dto.getSettlementDt()));
//                object.setIwType("APB");
//                object.setAchItemSeqNo("");
//                object.setAchChecksum("");
//                object.setAchProductType("");
//                object.setAchHeaderDestIin("");
//
//                if (!message.equals("duplicate")) {
//                    try {
//                        List list = em.createNativeQuery("select ifnull(aadhaar_lpg_acno,''),customerid from "
//                                + "cbs_customer_master_detail where aadhaar_no='" + dto.getBeneAadhaarNo().
//                                trim().substring(3) + "'").getResultList();
//                        if (list.isEmpty() || list.size() > 1) {
//                            object.setStatus("U");
//                            if (list.isEmpty()) {
//                                object.setReason("No such Aadhaar No");
//                            } else if (list.size() > 1) {
//                                object.setReason("Multiple Subsidy A/c Mapped");
//                            }
//                        } else {
//                            Vector element = (Vector) list.get(0);
//                            if (element.get(0) == null || element.get(0).equals("")) {
//                                object.setStatus("U");
//                                object.setReason("No such Account");
//                            } else {
//                                String subsidyAc = element.get(0).toString().trim();
//                                String custId = element.get(1).toString().trim();
//
//                                list = em.createNativeQuery("select status,mapping_status from cbs_aadhar_registration "
//                                        + "where cust_id='" + custId + "' and aadhar_no='" + dto.getBeneAadhaarNo().
//                                        trim().substring(3) + "'").getResultList();
//                                if (list.isEmpty()) {
//                                    object.setStatus("U");
//                                    object.setReason("No such Aadhaar No");
//                                } else {
//                                    element = (Vector) list.get(0);
//                                    String mapStatus = element.get(0).toString();
//                                    String mappingStatus = element.get(1).toString();
//                                    if (((mapStatus.equalsIgnoreCase("E") || mapStatus.equalsIgnoreCase("D")) && mappingStatus.equalsIgnoreCase("I"))
//                                            || (mapStatus.equalsIgnoreCase("D") && mappingStatus.equalsIgnoreCase("M"))) {
//                                        object.setStatus("U");
//                                        object.setReason("Aadhar is deactivated.");
//                                    } else {
//                                        Double subsidyAmt = Double.parseDouble(formatter.format(new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal("100")).doubleValue()));
//                                        //Subsidy Processing
//                                        message = ibtRemote.cbsPostingSx(subsidyAc, 0, ymd.format(txnDt),
//                                                subsidyAmt, 0f, 2, "Aaadhar Credit By NPCI", 0f, "A", "", "", 3, 0f,
//                                                ftsRemote.getRecNo(), 66, subsidyAc.substring(0, 2), uploadBrCode,
//                                                uploadingUserName, uploadingUserName, trsno, "", "");
//                                        if (message.substring(0, 4).equalsIgnoreCase("true")) {
//                                            object.setStatus("S");
//                                            object.setReason("");
//                                            totalAmount = totalAmount.add(new BigDecimal(subsidyAmt));
//                                            //Adding Object For Sms
//                                            SmsToBatchTo to = new SmsToBatchTo();
//                                            to.setAcNo(subsidyAc);
//                                            to.setCrAmt(subsidyAmt);
//                                            to.setDrAmt(0d);
//                                            to.setTranType(2);
//                                            to.setTy(0);
//                                            to.setTxnDt(todayDt);
//                                            to.setTemplate(SmsType.TRANSFER_DEPOSIT);
//                                            smsBatchList.add(to);
//                                            //End
//                                        } else {
//                                            object.setStatus("U");
//                                            object.setReason(message);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Exception ex) {
//                        object.setStatus("U");
//                        object.setReason(ex.getMessage());
//                    }
//                } else {
//                    object.setStatus("U");
//                    object.setReason("This User Credit Reference Is Already Processed.");
//                }
//                cbsNpciInwardDAO.save(object);
//            }
//            //Performing General Ledger Transaction of Debit
//            String glAccount = "";
//            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(em);
//            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NPCI-INW-CR");
//            if (!abbParameterInfoList.isEmpty()) {
//                for (AbbParameterInfo abbPojo : abbParameterInfoList) {
//                    glAccount = uploadBrCode + abbPojo.getAcno();
//                }
//            }
//            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
//                message = ibtRemote.cbsPostingCx(glAccount, 1, ymd.format(dmy.parse(todayDt)), totalAmount.doubleValue(), 0f, 2,
//                        todayDt + " NPCI Subsidy Entry", 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, uploadBrCode,
//                        uploadBrCode, uploadingUserName, uploadingUserName, trsno, "", "");
//                if (!message.substring(0, 4).equalsIgnoreCase("true")) {
//                    throw new ApplicationException("Problem In Npci Inward Head Entry");
//                }
//                message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0,
//                        totalAmount.doubleValue(), "Y", "Y");
//                if (!message.equalsIgnoreCase("true")) {
//                    throw new ApplicationException("Problem In Npci Inward Head Balance Updation.");
//                }
//            }
//            ut.commit();
//            //Sending Sms
//            try {
//                smsFacade.sendSmsToBatch(smsBatchList);
//            } catch (Exception e) {
//                System.out.println("Problem In SMS Sending To Batch In "
//                        + "Npci Inward Credit." + e.getMessage());
//            }
//            //End here
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(ex.getMessage());
//            } catch (Exception e) {
//                throw new ApplicationException(e.getMessage());
//            }
//        }
//        return "true";
//    }
    //APBS Inward File Uploading.
    public String npciInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String fileName, String processingMode) throws ApplicationException {
        String message = "";
        BigDecimal totalAmount = new BigDecimal("0");
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        CbsNpciInwardDAO cbsNpciInwardDAO = new CbsNpciInwardDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }

            userList = em.createNativeQuery("select ifnull(file_name,'') from cbs_npci_inward where "
                    + "file_name='" + fileName + "'").getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }

            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }

            //Addition on 25/0/92019
            List paramList = em.createNativeQuery("select code from cbs_parameterinfo where "
                    + "name='NPCI-APB-NOT-ALLOWED-CC-OD-ACCT-TYPE'").getResultList();
            if (paramList.isEmpty()) {
                throw new ApplicationException("Please define NPCI-APB-NOT-ALLOWED-CC-OD-ACCT-TYPE");
            }
            Vector paramVec = (Vector) paramList.get(0);
            String paramCodes = paramVec.get(0).toString().toLowerCase();


            Float trsno = ftsRemote.getTrsNo();
            for (int i = 0; i < inputList.size(); i++) {
//                message = "unsuccess";
                NpciInwardDto dto = inputList.get(i);
//                List<CbsNpciInward> isEntity = cbsNpciInwardDAO.findByUserCreditReferenceAndIwType(dto.getUserCreditRef().trim(), "APB");
//                if (!isEntity.isEmpty()) {
//                    message = "duplicate";
//                }
                CbsNpciInward object = new CbsNpciInward();

                object.setApbsTranCode(dto.getApbsTranCode().trim());
                object.setDestBankIin(dto.getDestBankIIN().trim());
                object.setDestActype(dto.getDestAcType().trim());
                object.setLedgerNo(dto.getLedgerNo().trim());
                object.setBeneAadhaarNo(dto.getBeneAadhaarNo().trim());

                object.setBeneName(dto.getBeneName().trim());
                object.setSponsorBankIin(dto.getSponsorBankIIN().trim());
                object.setUserNo(dto.getUserNumber().trim());
                object.setUserNameNarration(dto.getUserName().trim());
                object.setUserCreditReference(dto.getUserCreditRef().trim());

                object.setAmount(dto.getAmount().trim());
                object.setReservedOne(dto.getReservedOne().trim());
                object.setReservedTwo(dto.getReservedTwo().trim());
                object.setReservedThree(dto.getReservedThree().trim());
                object.setDestBankAcno(dto.getDestBankAcno().trim());

                object.setReturnReasonCode(dto.getRetReasonCode().trim());
                Date txnDt = dmy.parse(todayDt);
                object.setTranDate(txnDt);
                object.setValueDate(txnDt);
                object.setTranTime(new Date());
                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());
                object.setTrsNo(trsno.doubleValue());
                object.setSettlementDate(ymd.parse(dto.getSettlementDt()));
                object.setIwType("APB");
                object.setAchItemSeqNo("");
                object.setAchChecksum("");
                object.setAchProductType("");
                object.setAchHeaderDestIin("");
                object.setCbsAcno("");
                object.setCbsAcname("");
                object.setFileName(fileName);
                if (processingMode.equalsIgnoreCase("H2H")) {
                    object.setFileGenFlag("N");
                }
//                if (!message.equals("duplicate")) {
                try {
                    List list = em.createNativeQuery("select ifnull(aadhaar_lpg_acno,''),customerid from "
                            + "cbs_customer_master_detail where aadhaar_no='" + dto.getBeneAadhaarNo().
                            trim().substring(3) + "'").getResultList();
                    if (list.isEmpty() || list.size() > 1) {
                        object.setStatus("U");
                        if (list.isEmpty()) {
                            object.setReason("No such Aadhaar No");
                        } else if (list.size() > 1) {
                            object.setReason("Multiple Subsidy A/c Mapped"); //No such Aadhaar No
                        }
                    } else {
                        Vector element = (Vector) list.get(0);
                        if (element.get(0) == null || element.get(0).equals("")) {
                            object.setStatus("U");
                            object.setReason("No such Account"); //No such Aadhaar No
                        } else {
                            String subsidyAc = element.get(0).toString().trim();
                            String custId = element.get(1).toString().trim();

                            list = em.createNativeQuery("select status,mapping_status from cbs_aadhar_registration "
                                    + "where cust_id='" + custId + "' and aadhar_no='" + dto.getBeneAadhaarNo().
                                    trim().substring(3) + "'").getResultList();
                            if (list.isEmpty()) {
                                object.setStatus("U");
                                object.setReason("No such Aadhaar No");
                            } else {
                                element = (Vector) list.get(0);
                                String mapStatus = element.get(0).toString();
                                String mappingStatus = element.get(1).toString();
                                if (((mapStatus.equalsIgnoreCase("E") || mapStatus.equalsIgnoreCase("D")) && mappingStatus.equalsIgnoreCase("I"))
                                        || (mapStatus.equalsIgnoreCase("D") && mappingStatus.equalsIgnoreCase("M")) || (mapStatus.equalsIgnoreCase("D"))) {
                                    object.setStatus("U");
                                    object.setReason("Aadhar is deactivated"); //No such Aadhaar No
                                } else {
                                    Double subsidyAmt = Double.parseDouble(formatter.format(new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal("100")).doubleValue()));
                                    //A/c validation
                                    list = em.createNativeQuery("select ifnull(at.acctnature,'') as acctnature,at.accttype,"
                                            + "am.accstatus from accounttypemaster at,accountmaster am where "
                                            + "at.acctcode=am.accttype and am.acno='" + subsidyAc + "'").getResultList();
                                    if (list.isEmpty()) {
                                        object.setStatus("U");
                                        object.setReason("No such Account"); //Invalid Account
                                    } else {
                                        element = (Vector) list.get(0);
                                        String actNature = element.get(0).toString();
                                        String acctType = element.get(1).toString().toLowerCase();
                                        String accstatus = element.get(2).toString();
                                        if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                                                || ((actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                                                //&& (acctType.equalsIgnoreCase("CC") || acctType.equalsIgnoreCase("OD"))))) { //Commented on 25/09/2019
                                                && paramCodes.contains(acctType)))) {
                                            object.setStatus("U");
                                            object.setReason("Invalid Account"); //Invalid Account
                                        } else {
                                            if (accstatus.equals("9")) {
                                                object.setStatus("U");
                                                object.setReason("Account Closed");
                                            } else if (accstatus.equals("4")) {
                                                object.setStatus("U");
                                                object.setReason("Frozen");
                                            } else if (accstatus.equals("2")) {
                                                object.setStatus("U");
                                                object.setReason("Inoperative");
                                            } else if (accstatus.equals("8")) {
                                                object.setStatus("U");
                                                object.setReason("Operation Stopped");
                                            } else if (accstatus.equals("15")) {
                                                object.setStatus("U");
                                                object.setReason("Deaf");
                                            } else if (accstatus.equals("3") || accstatus.equals("5") || accstatus.equals("6")
                                                    || accstatus.equals("10") || accstatus.equals("11") || accstatus.equals("12")
                                                    || accstatus.equals("13") || accstatus.equals("14")) {
                                                object.setStatus("U");
                                                object.setReason("Invalid Account Status"); //Invalid Account Type
                                            } else {
                                                String txnDetails = "By NPCI " + object.getUserNameNarration() + " " + object.getUserCreditReference(); //Aadhar Credit By NPCI
                                                //Subsidy Processing
                                                if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                                    Integer varinsertReconList = em.createNativeQuery("insert into recon(acno,"
                                                            + "ty,dt,valuedt,dramt,cramt,balance,trantype,details,iy,instno,"
                                                            + "instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,"
                                                            + "tokenpaidby,subtokenno,trantime,org_brnid,dest_brnid,tran_id,"
                                                            + "term_id) values('" + subsidyAc + "',0,'" + ymd.format(txnDt) + "',"
                                                            + "'" + ymd.format(txnDt) + "',0," + subsidyAmt + ", 0,2,"
                                                            + "'" + txnDetails + "',9999,'','19000101',"
                                                            + "'" + uploadingUserName + "','Y'," + ftsRemote.getRecNo() + ","
                                                            + "3,'" + uploadingUserName + "'," + trsno + ",66,0,'','',"
                                                            + "current_timestamp(),'" + uploadBrCode + "',"
                                                            + "'" + subsidyAc.substring(0, 2) + "',0,'' )").executeUpdate();
                                                    if (varinsertReconList <= 0) {
                                                        throw new ApplicationException("Insertion Problem in Recons for A/c No :- " + subsidyAc);
                                                    }
                                                } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    Integer varinsertCaReconList = em.createNativeQuery("insert into "
                                                            + "ca_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,"
                                                            + "details,iy,instno,instdt,enterby,auth,recno,payby,authby,"
                                                            + "trsno,trandesc,tokenno,tokenpaidby,subtokenno,trantime,"
                                                            + "org_brnid,dest_brnid,tran_id,term_id) values('" + subsidyAc + "',"
                                                            + "0,'" + ymd.format(txnDt) + "','" + ymd.format(txnDt) + "',0,"
                                                            + "" + subsidyAmt + ", 0,2,'" + txnDetails + "',9999,'',"
                                                            + "'19000101','" + uploadingUserName + "','Y',"
                                                            + "" + ftsRemote.getRecNo() + ",3,'" + uploadingUserName + "',"
                                                            + "" + trsno + ",66,0,'','',current_timestamp(),"
                                                            + "'" + uploadBrCode + "','" + subsidyAc.substring(0, 2) + "',"
                                                            + "0,'' )").executeUpdate();
                                                    if (varinsertCaReconList <= 0) {
                                                        throw new ApplicationException("Insertion Problem in Recons for A/c No :- " + subsidyAc);
                                                    }
                                                }
                                                String resultLastTrxn = ftsRemote.lastTxnDateUpdation(subsidyAc);
                                                message = ftsRemote.updateBalance(actNature, subsidyAc, subsidyAmt, 0, "Y", "Y");
                                                if (!message.equalsIgnoreCase("true")) {
                                                    throw new ApplicationException("Problem in balance updation for A/c-->" + subsidyAc);
                                                }

                                                //ISO Head Transactions
                                                list = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'INTERSOLE ACCOUNT'").getResultList();
                                                if (list.isEmpty()) {
                                                    throw new ApplicationException("Please define intersole account.");
                                                }
                                                element = (Vector) list.get(0);
                                                String isoHead = element.get(0).toString();
                                                if (isoHead.trim().length() != 10) {
                                                    throw new ApplicationException("Please define 10 digit intersole account in abb parameter info.");
                                                }
                                                //Brach ISO Head Transaction
                                                String isoAccount = subsidyAc.substring(0, 2) + isoHead;

                                                Integer varinsertGlReconList = em.createNativeQuery("insert into gl_recon(acno,"
                                                        + "ty,dt,valuedt,dramt,cramt,balance,trantype,details,iy,instno,instdt,"
                                                        + "enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,tokenpaidby,"
                                                        + "subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                                                        + "advicebrncode) values('" + isoAccount + "',1,"
                                                        + "'" + ymd.format(txnDt) + "','" + ymd.format(txnDt) + "',"
                                                        + "" + subsidyAmt + ",0, 0,2,'Aaadhar Credit By NPCI',9999,'',"
                                                        + "'19000101','" + uploadingUserName + "','Y',"
                                                        + "" + ftsRemote.getRecNo() + ",3,'" + uploadingUserName + "',"
                                                        + "" + trsno + ",66,0,'','',current_timestamp(),"
                                                        + "'" + uploadBrCode + "','" + subsidyAc.substring(0, 2) + "',0,"
                                                        + "'','','' )").executeUpdate();
                                                if (varinsertGlReconList <= 0) {
                                                    throw new ApplicationException("Insertion Problem in Recons for A/c No :- " + isoAccount);
                                                }

                                                isoAccount = uploadBrCode + isoHead;
                                                varinsertGlReconList = em.createNativeQuery("insert into gl_recon(acno,"
                                                        + "ty,dt,valuedt,dramt,cramt,balance,trantype,details,iy,instno,instdt,"
                                                        + "enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,tokenpaidby,"
                                                        + "subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                                                        + "advicebrncode) values('" + isoAccount + "',0,"
                                                        + "'" + ymd.format(txnDt) + "','" + ymd.format(txnDt) + "',"
                                                        + "0," + subsidyAmt + ", 0,2,'Aaadhar Credit By NPCI',8888,'',"
                                                        + "'19000101','" + uploadingUserName + "','Y',"
                                                        + "" + ftsRemote.getRecNo() + ",3,'" + uploadingUserName + "',"
                                                        + "" + trsno + ",66,0,'','',current_timestamp(),"
                                                        + "'" + uploadBrCode + "','" + subsidyAc.substring(0, 2) + "',0,"
                                                        + "'','','' )").executeUpdate();
                                                if (varinsertGlReconList <= 0) {
                                                    throw new ApplicationException("Insertion Problem in Recons for A/c No :- " + isoAccount);
                                                }

                                                object.setStatus("S");
                                                object.setReason("");
                                                totalAmount = totalAmount.add(new BigDecimal(subsidyAmt));
                                                //Adding Object For Sms
                                                SmsToBatchTo to = new SmsToBatchTo();
                                                to.setAcNo(subsidyAc);
                                                to.setCrAmt(subsidyAmt);
                                                to.setDrAmt(0d);
                                                to.setTranType(2);
                                                to.setTy(0);
                                                to.setTxnDt(todayDt);
                                                to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                                                smsBatchList.add(to);
                                                //End
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new ApplicationException(ex.getMessage());
                }
                cbsNpciInwardDAO.save(object);
            }
            //Performing General Ledger Transaction of Debit
            String glAccount = "";
            ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(em);

            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("APB-ACH-HEAD");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 1) {
                throw new ApplicationException("Please define code for::APB-ACH-HEAD");
            }
            if (!(paramEntity.getCode() == 0 || paramEntity.getCode() == 1)) {
                throw new ApplicationException("Please define proper code for APB-ACH-HEAD");
            }
            String apbAchHead = paramEntity.getCode().toString().trim();

            List<AbbParameterInfo> abbParameterInfoList = null;
            if (apbAchHead.equals("1")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NPCI-INW-CR");
            } else if (apbAchHead.equals("0")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ACH-INW-CR");
            }
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("Please define proper head for NPCI.");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                glAccount = uploadBrCode + abbPojo.getAcno();
            }
            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                String details = todayDt + " NPCI Subsidy Entry";
                int varinsertGlReconList = em.createNativeQuery("insert into gl_recon(acno,"
                        + "ty,dt,valuedt,dramt,cramt,balance,trantype,details,iy,instno,instdt,"
                        + "enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,tokenpaidby,"
                        + "subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                        + "advicebrncode) values('" + glAccount + "',1,"
                        + "'" + ymd.format(dmy.parse(todayDt)) + "','" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "" + totalAmount.doubleValue() + ",0,0,2,'" + details + "',8888,'',"
                        + "'19000101','" + uploadingUserName + "','Y',"
                        + "" + ftsRemote.getRecNo() + ",3,'" + uploadingUserName + "',"
                        + "" + trsno + ",66,0,'','',current_timestamp(),"
                        + "'" + uploadBrCode + "','" + uploadBrCode + "',0,"
                        + "'','','' )").executeUpdate();
                if (varinsertGlReconList <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No :- " + glAccount);
                }

                message = ftsRemote.updateBalance("PO", glAccount, 0, totalAmount.doubleValue(), "Y", "Y");
                if (!message.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Npci Inward Head Balance Updation.");
                }
            }
            //In case of h2h
            if (processingMode.equalsIgnoreCase("H2H")) {
                fileName = fileName + ".txt";
                int zn = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + fileName + "','IW')").executeUpdate();
                if (zn <= 0) {
                    throw new ApplicationException("Problem in cbs_npci_h2h_file_detail insertion.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Npci Inward Credit." + e.getMessage());
            }
            //End here
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

    public List getBankDetails() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select * from mb_sms_sender_bank_detail").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please fill bank details");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getNpciBranch(String orgnBrCode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String alphaCode = reportRemote.getAlphacodeByBrncode(orgnBrCode);
            if (alphaCode.equalsIgnoreCase("HO")) {
                dataList = em.createNativeQuery("select 'A','ALL' union select brncode,alphacode from branchmaster where "
                        + "alphacode not in('CELL')").getResultList();
            } else {
                dataList = em.createNativeQuery("select brncode,alphacode from branchmaster where "
                        + "brncode = cast('" + orgnBrCode + "' as unsigned)").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

//    public String getNpciReturnReasonCode(String cbsMessage) throws ApplicationException {
//        String reasonCode = "";
//        try {
//            cbsMessage = cbsMessage.trim().toLowerCase();
//            if (cbsMessage.contains("account closed")) {
//                reasonCode = "01";
//            } else if (cbsMessage.contains("no such aadhaar no")
//                    || cbsMessage.contains("multiple subsidy a/c mapped")
//                    || cbsMessage.contains("no such account")
//                    || cbsMessage.contains("aadhar is deactivated")) {
//                reasonCode = "64";
//            } else if (cbsMessage.contains("frozen")) {
//                reasonCode = "68";
//            } else if (cbsMessage.contains("invalid account status")) {
//                reasonCode = "70";
//            } else if (cbsMessage.contains("invalid account")) {
//                reasonCode = "71";
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return reasonCode;
//    }
    public String getNpciReturnReasonCode(String cbsMessage) throws ApplicationException {
        String reasonCode = "";
        try {
            cbsMessage = cbsMessage.trim().toLowerCase();
            if (cbsMessage.contains("account closed")) {
                reasonCode = "01";
            } else if (cbsMessage.contains("no such aadhaar no")
                    || cbsMessage.contains("multiple subsidy a/c mapped")
                    || cbsMessage.contains("no such account")
                    || cbsMessage.contains("aadhar is deactivated")) {
                reasonCode = "64";
            } else if (cbsMessage.contains("frozen")
                    || cbsMessage.contains("operation stopped")) {
                reasonCode = "68";
            } else if (cbsMessage.contains("inoperative")) {
                reasonCode = "53";
            } else if (cbsMessage.contains("deaf")) {
                reasonCode = "10";
            } else if (cbsMessage.contains("invalid account status")
                    || cbsMessage.contains("invalid account")) {
                reasonCode = "71";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return reasonCode;
    }

    @Override
    public List getSeqNoForSettlementDate(String settlementDt, String iwType) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct trs_no from cbs_npci_inward where "
                    + "settlement_date='" + ymd.format(dmy.parse(settlementDt)) + "' and "
                    + "iw_type='" + iwType + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this settlement date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getH2HSeqNoForSettlementDate(String settlementDt, String iwType) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct trs_no from cbs_npci_inward where "
                    + "settlement_date='" + ymd.format(dmy.parse(settlementDt)) + "' and "
                    + "iw_type='" + iwType + "' and file_gen_flag='N'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this settlement date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<NeftRtgsReportPojo> getNpciReportData(String frDt, String toDt, String status,
            String brCode, String repType, String dt, double trsNo) throws ApplicationException {
        List<NeftRtgsReportPojo> dataList = new ArrayList<NeftRtgsReportPojo>();
        List result = new ArrayList();
        try {
            if (repType.equalsIgnoreCase("NPCI APBS")) {
                if (status.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select bene_aadhaar_no,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d') from cbs_npci_inward where "
                            + "date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and iw_type = 'apb'").getResultList();
                } else {
                    result = em.createNativeQuery("select bene_aadhaar_no,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d') from cbs_npci_inward "
                            + "where date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'and "
                            + "status = '" + status + "' and iw_type = 'apb'").getResultList();
                }
            } else if (repType.equalsIgnoreCase("NPCI ACH")) {
                if (status.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,'') from cbs_npci_inward where "
                            + "date_format(tran_date,'%Y%m%d') ='" + dt + "' and iw_type = 'ach' and trs_no=" + trsNo + "").getResultList();
                } else {
                    result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,'') from cbs_npci_inward "
                            + "where date_format(tran_date,'%Y%m%d') ='" + dt + "' and "
                            + "status = '" + status + "' and iw_type = 'ach' and trs_no=" + trsNo + "").getResultList();
                }
            } else if (repType.equalsIgnoreCase("NPCI ACH PERIOD")) {
                if (status.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,'') from cbs_npci_inward where "
                            + "date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and iw_type = 'ach'").getResultList();
                } else {
                    result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,'') from cbs_npci_inward "
                            + "where date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and "
                            + "status = '" + status + "' and iw_type = 'ach'").getResultList();
                }
            } else if (repType.equalsIgnoreCase("NPCI ECS")) {
                if (status.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),dest_bank_iin,sponsor_bank_iin,user_name_narration from cbs_npci_inward where "
                            + "date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and iw_type = 'ECS'").getResultList();
                } else {
                    result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                            + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),dest_bank_iin,sponsor_bank_iin,user_name_narration from cbs_npci_inward "
                            + "where date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'and "
                            + "status = '" + status + "' and iw_type = 'ECS'").getResultList();
                }
            } else if (repType.equalsIgnoreCase("ACH-DR") || repType.equalsIgnoreCase("ECS-DR")) {
                if (status.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as amount,"
                            + "micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,return_code,user_name "
                            + "from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') "
                            + "between '" + frDt + "' and '" + toDt + "' and iw_type='" + repType + "' order "
                            + "by file_seq_no").getResultList();
                } else {
                    status = status.equalsIgnoreCase("S") ? "P" : "R";
                    result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as "
                            + "amount,micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,"
                            + "return_code,user_name from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') "
                            + "between '" + frDt + "' and '" + toDt + "' and iw_type='" + repType + "' and "
                            + "ac_val_flag='" + status + "' order by file_seq_no").getResultList();
                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    NeftRtgsReportPojo pojo = new NeftRtgsReportPojo();
                    if (repType.equalsIgnoreCase("ECS-DR") || repType.equalsIgnoreCase("ACH-DR")) {
                        pojo.setBeneAccount(vtr.get(0).toString().trim());
                        pojo.setAcctcode(vtr.get(1).toString().trim());
                        pojo.setBeneName(vtr.get(2).toString().trim());

                        BigDecimal amt = new BigDecimal(vtr.get(3).toString().trim()).divide(new BigDecimal("100"));
                        pojo.setAmount(new BigDecimal(formatter.format(amt.doubleValue())));
                        pojo.setMicr(vtr.get(4).toString().trim());
                        pojo.setAchItemSeqNo(vtr.get(5).toString().trim());
                        pojo.setFileSeqNo(vtr.get(6).toString().trim());
                        pojo.setTranRef(vtr.get(7).toString().trim());

                        String acValFlag = vtr.get(8).toString().trim();
                        if (acValFlag.equalsIgnoreCase("P")) {
                            pojo.setAcValFlag("PASS");
                        } else if (acValFlag.equalsIgnoreCase("R")) {
                            pojo.setAcValFlag("RETURN");
                        } else {
                            pojo.setAcValFlag("NOT VERIFIED");
                        }
                        pojo.setReturnReason("");
                        String reasonCode = vtr.get(9).toString().trim();
                        if (!reasonCode.equals("")) {
                            if (repType.equalsIgnoreCase("ECS-DR")) {
                                pojo.setReturnReason(reportRemote.getRefRecDesc("315", reasonCode));
                            } else if (repType.equalsIgnoreCase("ACH-DR")) {
                                pojo.setReturnReason(reportRemote.getRefRecDesc("319", reasonCode));
                            }
                        }
                        pojo.setSenderName(vtr.get(10) == null ? "" : vtr.get(10).toString().trim());
                        dataList.add(pojo);
                    } else {
                        if (repType.equalsIgnoreCase("NPCI APBS")) {
                            pojo.setAadharNo(vtr.get(0).toString());
                        } else {
                            pojo.setBeneAccount(vtr.get(0).toString());
                        }
                        List aadharAcnoList;
                        if (repType.equalsIgnoreCase("NPCI APBS")) {
                            aadharAcnoList = em.createNativeQuery("select ifnull(aadhaar_lpg_acno,'') from cbs_customer_master_detail where customerid in(select cust_id from cbs_aadhar_registration where aadhar_no = '" + vtr.get(0).toString().substring(3) + "')").getResultList();
                        } else {
                            aadharAcnoList = em.createNativeQuery("select ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward  where DEST_BANK_ACNO = '" + vtr.get(0).toString() + "' and iw_type = 'ach'").getResultList();
                        }
                        if (!aadharAcnoList.isEmpty()) {
                            Vector acnov = (Vector) aadharAcnoList.get(0);
                            if (repType.equalsIgnoreCase("NPCI APBS")) {
                                pojo.setBeneAccount(acnov.get(0).toString());
                            } else {
                                pojo.setAadharNo(acnov.get(0).toString());
                            }
                        }
                        if (repType.equalsIgnoreCase("NPCI ECS")) {
                            pojo.setAadharNo(vtr.get(9).toString());
                            pojo.setSenderAccount(vtr.get(10).toString());
                            pojo.setSenderName(vtr.get(11).toString());
                        }
                        pojo.setBeneName(vtr.get(1).toString());
                        pojo.setAmount(new BigDecimal(vtr.get(2).toString()));
                        pojo.setSettleDt(vtr.get(3).toString());
                        pojo.setUtrNo(vtr.get(4).toString());
                        pojo.setTrSno(vtr.get(5).toString());
                        if (vtr.get(6).toString().equalsIgnoreCase("S")) {
                            pojo.setDetails("Success");
                        } else {
                            pojo.setDetails("Unsuccess");
                        }
                        pojo.setNarration(vtr.get(7).toString());
                        pojo.setTranDt(ymd.parse(vtr.get(8).toString()));
                        pojo.setFileName("");
                        if (repType.equalsIgnoreCase("NPCI ACH")) {
                            pojo.setFileName(vtr.get(9).toString());
                        }
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<NeftRtgsReportPojo> getNpciACHReportData(String frDt, String toDt, String status,
            String brCode, String repType, String reportMode, String dt, String trsNo) throws ApplicationException {
        List<NeftRtgsReportPojo> dataList = new ArrayList<NeftRtgsReportPojo>();
        List result = new ArrayList();
        try {
            String condition = "";
            if (!brCode.equalsIgnoreCase("ALL")) {
                String arr[] = otherNpciRemote.getIfscAndMicrCodeByBrnCode(brCode);
                if (repType.equalsIgnoreCase("ECS-CR")) { //ECS-CR And ACH-CR-ECS
                    condition = " and (dest_bank_iin='" + arr[0] + "' or dest_bank_iin='" + arr[1] + "')";
                } else if (repType.equalsIgnoreCase("ECS-DR")) {
                    condition = " and (micr='" + arr[0] + "' or micr='" + arr[1] + "')";
                } else if (repType.equalsIgnoreCase("ACH-CR")) {
                    condition = " and (dest_bank_iin='" + arr[0] + "' or dest_bank_iin='" + arr[1] + "')";
                } else if (repType.equalsIgnoreCase("ACH-DR")) {
                    condition = " and (micr='" + arr[0] + "' or micr='" + arr[1] + "')";
                } else if (repType.equalsIgnoreCase("NPCI APBS")) {
                    condition = " and (a.dest_bank_iin='" + arr[0] + "' or a.dest_bank_iin='" + arr[1] + "')";
                }
            }

            if (repType.equalsIgnoreCase("NPCI APBS")) {
                if (reportMode.equalsIgnoreCase("SQW")) {
                    if (status.equalsIgnoreCase("All")) {
                        if (brCode.equalsIgnoreCase("All")) {
                            result = em.createNativeQuery("select bene_aadhaar_no,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                    + "trs_no,status,reason,date_format(tran_date,'%Y%m%d') ,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward where "
                                    + "date_format(tran_date,'%Y%m%d') ='" + dt + "' and iw_type = 'APB' and"
                                    + " trs_no='" + Double.parseDouble(trsNo) + "' order by tran_date,trs_no").getResultList();
                        } else {
                            result = em.createNativeQuery("select Distinct a.bene_aadhaar_no,a.bene_name, a.amount/100, date_format(a.settlement_date,'%d/%m/%Y'), a.user_credit_reference,"
                                    + "a.trs_no,a.status,a.reason,date_format(a.tran_date,'%Y%m%d') ,ifnull(a.file_name,''),ifnull(a.BENE_AADHAAR_NO,'') from cbs_npci_inward a,cbs_aadhar_registration b, branchmaster c where substring(a.bene_aadhaar_no,4,12)=b.AADHAR_NO"
                                    + " and (substring(b.CUSTID_BRNCODE,2,2) = '" + brCode + "') and date_format(a.tran_date,'%Y%m%d') ='" + dt + "' and a.iw_type = 'APB' and a.trs_no='" + Double.parseDouble(trsNo) + "' order by a.tran_date,a.trs_no ").getResultList();
                        }
                    } else {
                        if (brCode.equalsIgnoreCase("All")) {
                            result = em.createNativeQuery("select bene_aadhaar_no,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                    + " trs_no,status,reason,date_format(tran_date,'%Y%m%d') ,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward where "
                                    + " date_format(tran_date,'%Y%m%d') ='" + dt + "' and status = '" + status + "' and iw_type = 'APB' and"
                                    + " trs_no= '" + Double.parseDouble(trsNo) + "' order by tran_date,trs_no").getResultList();
                        } else {
                            result = em.createNativeQuery("select Distinct a.bene_aadhaar_no,a.bene_name, a.amount/100, date_format(a.settlement_date,'%d/%m/%Y'), a.user_credit_reference,"
                                    + "a.trs_no,a.status,a.reason,date_format(a.tran_date,'%Y%m%d') ,ifnull(a.file_name,''),ifnull(a.BENE_AADHAAR_NO,'') from cbs_npci_inward a,cbs_aadhar_registration b, branchmaster c where substring(a.bene_aadhaar_no,4,12)=b.AADHAR_NO"
                                    + " and (substring(b.CUSTID_BRNCODE,2,2) = '" + brCode + "') and a.status = '" + status + "' and date_format(a.tran_date,'%Y%m%d') ='" + dt + "' and a.iw_type = 'APB' and a.trs_no='" + Double.parseDouble(trsNo) + "' order by a.tran_date,a.trs_no ").getResultList();
                        }
                    }
                } else if (reportMode.equalsIgnoreCase("DTW")) {
                    if (status.equalsIgnoreCase("All")) {
                        if (brCode.equalsIgnoreCase("All")) {
                            result = em.createNativeQuery("select bene_aadhaar_no,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                    + "trs_no,status,reason,date_format(tran_date,'%Y%m%d') ,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward where "
                                    + " date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and iw_type = 'APB'  order by tran_date,trs_no").getResultList();
                        } else {
                            result = em.createNativeQuery("select Distinct a.bene_aadhaar_no,a.bene_name, a.amount/100, date_format(a.settlement_date,'%d/%m/%Y'), a.user_credit_reference,"
                                    + " a.trs_no,a.status,a.reason,date_format(a.tran_date,'%Y%m%d') ,ifnull(a.file_name,''),ifnull(a.BENE_AADHAAR_NO,'') from cbs_npci_inward a,cbs_aadhar_registration b, branchmaster c where substring(a.bene_aadhaar_no,4,12)=b.AADHAR_NO"
                                    + "  and (substring(b.CUSTID_BRNCODE,2,2) = '" + brCode + "')  and date_format(a.tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' "
                                    + " and a.iw_type = 'APB' order by a.tran_date,a.trs_no").getResultList();
                        }
                    } else {
                        if (brCode.equalsIgnoreCase("All")) {
                            result = em.createNativeQuery("select bene_aadhaar_no,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                    + " trs_no,status,reason,date_format(tran_date,'%Y%m%d') ,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward where "
                                    + " date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'  and status = '" + status + "' and iw_type = 'APB' "
                                    + " order by tran_date,trs_no").getResultList();
                        } else {
                            result = em.createNativeQuery("select Distinct a.bene_aadhaar_no,a.bene_name, a.amount/100, date_format(a.settlement_date,'%d/%m/%Y'), a.user_credit_reference,"
                                    + " a.trs_no,a.status,a.reason,date_format(a.tran_date,'%Y%m%d') ,ifnull(a.file_name,''),ifnull(a.BENE_AADHAAR_NO,'') from cbs_npci_inward a,cbs_aadhar_registration b, branchmaster c where substring(a.bene_aadhaar_no,4,12)=b.AADHAR_NO"
                                    + "  and (substring(b.CUSTID_BRNCODE,2,2) = '" + brCode + "') and a.status = '" + status + "' and date_format(a.tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' "
                                    + " and a.iw_type = 'APB'  order by a.tran_date,a.trs_no").getResultList();

                        }

                    }
                }
            } else if (repType.equalsIgnoreCase("ACH-CR")) {
                if (reportMode.equalsIgnoreCase("SQW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward where "
                                + "date_format(tran_date,'%Y%m%d') ='" + dt + "' and iw_type = 'ach' and trs_no=" + Double.parseDouble(trsNo)
                                + " " + condition + " order by tran_date,trs_no").getResultList();
                    } else {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward "
                                + "where date_format(tran_date,'%Y%m%d') ='" + dt + "' and status = '" + status
                                + "' and iw_type = 'ach' and trs_no=" + Double.parseDouble(trsNo) + " " + condition + " order by tran_date,trs_no").getResultList();
                    }
                } else if (reportMode.equalsIgnoreCase("DTW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward where "
                                + " iw_type = 'ach' and date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt
                                + "'" + condition + " order by tran_date,trs_no").getResultList();
                    } else {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'') from cbs_npci_inward "
                                + "where date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and "
                                + "status = '" + status + "' and iw_type = 'ach' " + condition + " order by tran_date,trs_no").getResultList();
                    }
                }
            } else if (repType.equalsIgnoreCase("ACH-DR")) {
                if (reportMode.equalsIgnoreCase("DTW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as amount,"
                                + "micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,return_code,user_name,ifnull(UMRN,'') as umrn "
                                + "from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') "
                                + "between '" + frDt + "' and '" + toDt + "' and iw_type='" + repType + "' " + condition + " order "
                                + "by file_coming_dt,file_seq_no").getResultList();
                    } else {
                        status = status.equalsIgnoreCase("S") ? "P" : "R";
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as "
                                + "amount,micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,"
                                + "return_code,user_name,ifnull(UMRN,'') as umrn from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') "
                                + "between '" + frDt + "' and '" + toDt + "' and iw_type='" + repType + "' and "
                                + "ac_val_flag='" + status + "' " + condition + " order by file_coming_dt,file_seq_no").getResultList();
                    }
                } else if (reportMode.equalsIgnoreCase("SQW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as amount,"
                                + "micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,return_code,user_name,ifnull(UMRN,'') as umrn "
                                + "from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') ='" + dt + "'"
                                + " and file_seq_no='" + trsNo + "' and iw_type='" + repType
                                + "' " + condition + " order by file_coming_dt,file_seq_no").getResultList();
                    } else {
                        status = status.equalsIgnoreCase("S") ? "P" : "R";
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as "
                                + "amount,micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,"
                                + "return_code,user_name,ifnull(UMRN,'') as umrn from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d')='" + dt + "'"
                                + " and file_seq_no='" + trsNo + "' and ac_val_flag='" + status + "'"
                                + " and iw_type='" + repType + "' " + condition + " order by file_coming_dt,file_seq_no").getResultList();
                    }
                }

            } else if (repType.equalsIgnoreCase("ECS-CR")) { //ECS-CR and ACH-CR-ECS
                if (reportMode.equalsIgnoreCase("SQW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),dest_bank_iin,sponsor_bank_iin,user_name_narration,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'')  from cbs_npci_inward where "
                                + "date_format(tran_date,'%Y%m%d') ='" + dt + "' and iw_type = 'ECS' and ach_item_seq_no='" + trsNo + "' " + condition + " order by tran_date,trs_no").getResultList();
                    } else {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),dest_bank_iin,sponsor_bank_iin,user_name_narration,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'')  from cbs_npci_inward "
                                + "where date_format(tran_date,'%Y%m%d') ='" + dt + "' and status = '" + status + "' "
                                + " and iw_type = 'ECS' and ach_item_seq_no='" + trsNo + "' " + condition + " order by tran_date,trs_no").getResultList();
                    }
                } else if (reportMode.equalsIgnoreCase("DTW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),dest_bank_iin,sponsor_bank_iin,user_name_narration,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'')  from cbs_npci_inward where "
                                + " iw_type = 'ECS' and date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt
                                + "'" + condition + " order by tran_date,trs_no").getResultList();
                    } else {
                        result = em.createNativeQuery("select dest_bank_acno,bene_name,amount/100,date_format(settlement_date,'%d/%m/%Y'),user_credit_reference,"
                                + "trs_no,status,reason,date_format(tran_date,'%Y%m%d'),dest_bank_iin,sponsor_bank_iin,user_name_narration,ifnull(file_name,''),ifnull(BENE_AADHAAR_NO,'')  from cbs_npci_inward "
                                + "where date_format(tran_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and "
                                + "status = '" + status + "' and iw_type = 'ECS' " + condition + " order by tran_date,trs_no").getResultList();
                    }
                }
            } else if (repType.equalsIgnoreCase("ECS-DR")) {
                if (reportMode.equalsIgnoreCase("DTW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as amount,"
                                + "micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,return_code,user_name,ifnull(UMRN,'') as umrn "
                                + "from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') "
                                + "between '" + frDt + "' and '" + toDt + "' and iw_type='" + repType + "' " + condition + " order "
                                + "by file_coming_dt,file_seq_no").getResultList();
                    } else {
                        status = status.equalsIgnoreCase("S") ? "P" : "R";
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as "
                                + "amount,micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,"
                                + "return_code,user_name,ifnull(UMRN,'') as umrn from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') "
                                + "between '" + frDt + "' and '" + toDt + "' and iw_type='" + repType + "' and "
                                + "ac_val_flag='" + status + "' " + condition + " order by file_coming_dt,file_seq_no").getResultList();
                    }
                } else if (reportMode.equalsIgnoreCase("SQW")) {
                    if (status.equalsIgnoreCase("All")) {
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as amount,"
                                + "micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,return_code,user_name,ifnull(UMRN,'') as umrn "
                                + "from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d') ='" + dt + "'"
                                + " and file_seq_no='" + trsNo + "' and iw_type='" + repType
                                + "' " + condition + " order by file_coming_dt,file_seq_no").getResultList();
                    } else {
                        status = status.equalsIgnoreCase("S") ? "P" : "R";
                        result = em.createNativeQuery("select old_acno,ac_type,old_acname,ifnull(amount,0) as "
                                + "amount,micr,ifnull(ach_item_seq_no,'') as ach,file_seq_no,tran_ref,ac_val_flag,"
                                + "return_code,user_name,ifnull(UMRN,'') as umrn from cbs_npci_oac_detail where date_format(file_coming_dt,'%Y%m%d')='" + dt + "'"
                                + " and file_seq_no='" + trsNo + "' and ac_val_flag='" + status + "'"
                                + " and iw_type='" + repType + "' " + condition + " order by file_coming_dt,file_seq_no").getResultList();
                    }
                }
            }

            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                NeftRtgsReportPojo pojo = new NeftRtgsReportPojo();
                if (repType.equalsIgnoreCase("ACH-DR") || repType.equalsIgnoreCase("ECS-DR")) {
                    pojo.setBeneAccount(vtr.get(0).toString().trim());
                    pojo.setAcctcode(vtr.get(1).toString().trim());
                    pojo.setBeneName(vtr.get(2).toString().trim());

                    BigDecimal amt = new BigDecimal(vtr.get(3).toString().trim()).divide(new BigDecimal("100"));
                    pojo.setAmount(new BigDecimal(formatter.format(amt.doubleValue())));
                    pojo.setMicr(vtr.get(4).toString().trim());
                    pojo.setAchItemSeqNo(vtr.get(5).toString().trim());
                    pojo.setFileSeqNo(vtr.get(6).toString().trim());
                    pojo.setTranRef(vtr.get(7).toString().trim());

                    String acValFlag = vtr.get(8).toString().trim();
                    if (acValFlag.equalsIgnoreCase("P")) {
                        pojo.setAcValFlag("PASS");
                    } else if (acValFlag.equalsIgnoreCase("R")) {
                        pojo.setAcValFlag("RETURN");
                    } else {
                        pojo.setAcValFlag("NOT VERIFIED");
                    }
                    pojo.setReturnReason("");
                    String reasonCode = vtr.get(9).toString().trim();
                    if (!reasonCode.equals("")) {
                        if (repType.equalsIgnoreCase("ACH-DR")) {
                            pojo.setReturnReason(reportRemote.getRefRecDesc("319", reasonCode));
                        } else if (repType.equalsIgnoreCase("ECS-DR")) {
                            pojo.setReturnReason(reportRemote.getRefRecDesc("315", reasonCode));
                        }
                    }
                    pojo.setSenderName(vtr.get(10) == null ? "" : vtr.get(10).toString().trim());
                    pojo.setUmrn(vtr.get(11) == null ? "" : vtr.get(11).toString().trim());
                    dataList.add(pojo);
                } else if (repType.equalsIgnoreCase("ACH-CR") || repType.equalsIgnoreCase("ECS-CR")) {
                    pojo.setBeneAccount(vtr.get(0).toString());
//                    List aadharAcnoList;
//                    aadharAcnoList = em.createNativeQuery("select ifnull(BENE_AADHAAR_NO,'') from "
//                            + "cbs_npci_inward  where DEST_BANK_ACNO = '" + vtr.get(0).toString() + "' and "
//                            + "iw_type = 'ach'").getResultList();
//                    if (!aadharAcnoList.isEmpty()) {
//                        Vector acnov = (Vector) aadharAcnoList.get(0);
//                        pojo.setAadharNo(acnov.get(0).toString());
//                    }

                    pojo.setBeneName(vtr.get(1).toString());
                    pojo.setAmount(new BigDecimal(vtr.get(2).toString()));
                    pojo.setSettleDt(vtr.get(3).toString());
                    pojo.setUtrNo(vtr.get(4).toString());
                    pojo.setTrSno(vtr.get(5).toString());
                    if (vtr.get(6).toString().equalsIgnoreCase("S")) {
                        pojo.setDetails("Success");
                    } else {
                        pojo.setDetails("Unsuccess");
                    }
                    pojo.setNarration(vtr.get(7).toString());
                    pojo.setTranDt(ymd.parse(vtr.get(8).toString()));
                    if (repType.equalsIgnoreCase("ECS-CR")) {
                        pojo.setAadharNo(vtr.get(9).toString());
                        pojo.setSenderAccount(vtr.get(10).toString());
                        pojo.setSenderName(vtr.get(11).toString());
                        pojo.setFileName(vtr.get(12).toString());
                        pojo.setAadharNo(vtr.get(13).toString());
                    } else {
                        pojo.setFileName(vtr.get(9).toString());
                        pojo.setAadharNo(vtr.get(10).toString());
                    }
                    dataList.add(pojo);
                } else if (repType.equalsIgnoreCase("NPCI APBS")) {
                    pojo.setAadharNo(vtr.get(0).toString());
                    List aadharAcnoList;
                    aadharAcnoList = em.createNativeQuery("select ifnull(aadhaar_lpg_acno,''),ifnull(CustFullName,'') from cbs_customer_master_detail where customerid in(select cust_id from cbs_aadhar_registration where aadhar_no = '" + vtr.get(0).toString().substring(3) + "')").getResultList();
                    if (!aadharAcnoList.isEmpty()) {
                        Vector acnov = (Vector) aadharAcnoList.get(0);
                        pojo.setBeneAccount(acnov.get(0).toString());
                        if (vtr.get(6).toString().equalsIgnoreCase("S")) {
                            pojo.setBeneName(acnov.get(1).toString());
                        }
                    }
                    pojo.setAmount(new BigDecimal(vtr.get(2).toString()));
                    pojo.setSettleDt(vtr.get(3).toString());
                    pojo.setUtrNo(vtr.get(4).toString());
                    pojo.setTrSno(vtr.get(5).toString());
                    if (vtr.get(6).toString().equalsIgnoreCase("S")) {
                        pojo.setDetails("Success");
                    } else {
                        pojo.setDetails("Unsuccess");
                    }
                    pojo.setNarration(vtr.get(7).toString());
                    pojo.setTranDt(ymd.parse(vtr.get(8).toString()));
                    pojo.setFileName("");
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    //Non-Aadhar A/c Verification Inward Upload
    public String npciNonAadhaarInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String processingMode, String fileName) throws ApplicationException {
        String message = "";
        CbsNpciInwardNonAadhaarDAO nonAadhaarDao = new CbsNpciInwardNonAadhaarDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }

            for (int i = 0; i < inputList.size(); i++) {
                message = "unsuccess";
                NpciInwardDto dto = inputList.get(i);
                List<CbsNpciInwardNonAadhaar> isEntity = nonAadhaarDao.findByRrn(dto.getUserCreditRef().trim());
                if (!isEntity.isEmpty()) {
                    message = "duplicate";
                }
                CbsNpciInwardNonAadhaar object = new CbsNpciInwardNonAadhaar();

                object.setOriginatorCode(dto.getOriginatorCode().trim());
                object.setResponderCode(dto.getResponderCode().trim());
                object.setFileComingDt(ymd.parse(dto.getSettlementDt()));
                object.setFileSeqNo(dto.getFileSeqNo().trim());
                object.setRecordIdentifier(dto.getApbsTranCode().trim());
                object.setRrn(dto.getUserCreditRef().trim());
                object.setDestBankIfsc(dto.getDestBankIIN().trim());
                object.setDestBankAcno(dto.getDestBankAcno().trim());
                object.setBenNameOrgn(dto.getBeneName().trim());
                object.setLpgId(dto.getLpgId().trim());
                object.setAcValidFlag(dto.getAcValidFlag().trim());
                object.setBenNameMatchFlag(dto.getBenNameMatchFlag().trim());
                object.setBenNameResponder(dto.getBenNameResponder().trim());
                object.setAadhaarNo(dto.getBeneAadhaarNo().trim());
                object.setMobileNo(dto.getReservedOne().trim());
                object.setEmailId(dto.getReservedTwo().trim());
                object.setReturnCode("");
                Date txnDt = dmy.parse(todayDt);
                object.setTranDate(txnDt);
                object.setTranTime(new Date());
                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());
                object.setFileRefNo(dto.getFileRefNo().trim());
                object.setFiller(dto.getFiller().trim());
                if (processingMode.equalsIgnoreCase("H2H")) {
                    object.setFileGenFlag("N");
                }

                if (i == 0) {
                    List<CbsNpciInwardNonAadhaar> isBatchList = nonAadhaarDao.duplicateSeqOnDate(dto.getOriginatorCode().trim(),
                            ymd.parse(dto.getSettlementDt()), dto.getFileSeqNo().trim());
                    if (!isBatchList.isEmpty()) {
                        throw new ApplicationException("This file has been already uploaded.");
                    }
                    if (processingMode.equalsIgnoreCase("H2H")) {
                        int zn = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                                + "values('" + ymd.format(new Date()) + "','" + fileName + "','IW')").executeUpdate();
                        if (zn <= 0) {
                            throw new ApplicationException("Problem in cbs_npci_h2h_file_detail insertion.");
                        }
                    }
                }

                if (!message.equals("duplicate")) {
                    try {
                        //Account No Validation.
                        List list = em.createNativeQuery("select accstatus,custname,ifnull(jtname1,'') as jtname1,"
                                + "ifnull(jtname2,'') as jtname2,ifnull(jtname3,'') as jtname3,ifnull(jtname4,'') as "
                                + "jtname4 from accountmaster where "
                                + "acno='" + dto.getDestBankAcno().trim() + "'").getResultList();
                        if (list == null || list.isEmpty()) {
                            object.setStatus("U");
                            object.setReason("No Such Account");
                        } else {
                            Vector ele = (Vector) list.get(0);
                            String accStatus = ele.get(0).toString();
                            String custName = ele.get(1).toString().trim();
                            String jtName1 = ele.get(2).toString().trim();
                            String jtName2 = ele.get(3).toString().trim();
                            String jtName3 = ele.get(4).toString().trim();
                            String jtName4 = ele.get(5).toString().trim();

                            //Ifsc Code,IIN and Micr Validation.
                            list = em.createNativeQuery("select ifnull(ifsc_code,'') from  branchmaster "
                                    + "where brncode = " + Integer.parseInt(dto.getDestBankAcno().
                                    trim().substring(0, 2)) + "").getResultList();
                            ele = (Vector) list.get(0);
                            String ifscCode = ele.get(0).toString().trim();

                            list = em.createNativeQuery("select ifnull(iin,'') as iin from "
                                    + "mb_sms_sender_bank_detail").getResultList();
                            ele = (Vector) list.get(0);
                            String iin = ele.get(0).toString().trim();

                            String micrNo = "";
//                            list = em.createNativeQuery("select ifnull(concat(micr,micrcode,branchcode),'') as micrno "
//                                    + "from bnkadd where alphacode in(select alphacode from branchmaster "
//                                    + "where brncode=" + Integer.parseInt(dto.getDestBankAcno().trim().substring(0, 2)) + ")").getResultList();
//                            if (!list.isEmpty()) {
//                                ele = (Vector) list.get(0);
//                                micrNo = ele.get(0).toString().trim();
//                            }

                            list = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                                    + "bank_code,ifnull(b.branchcode,'') as brach_code from bnkadd b,branchmaster m where "
                                    + "b.alphacode=m.alphacode and "
                                    + "m.brncode=" + Integer.parseInt(dto.getDestBankAcno().trim().substring(0, 2)) + "").getResultList();
                            if (!list.isEmpty()) {
                                ele = (Vector) list.get(0);
                                String cityCode = ele.get(0).toString().trim();
                                String bankCode = ele.get(1).toString().trim();
                                String branchCode = ele.get(2).toString().trim();

                                micrNo = ParseFileUtil.addTrailingZeros(cityCode, 3) + ParseFileUtil.addTrailingZeros(bankCode, 3) + ParseFileUtil.addTrailingZeros(branchCode, 3);
                            }

                            if (!(dto.getDestBankIIN().trim().equalsIgnoreCase(ifscCode)
                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(iin)
                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(micrNo))) {
                                object.setStatus("U");
                                object.setReason("Ifsc Code MisMatch");
                            } else {
                                //Account Status Checking.
                                if (!accStatus.equals("1")) {
                                    if (accStatus.equals("9")) {
                                        object.setStatus("U");
                                        object.setReason("Account Closed or Transferred");
                                    } else {
                                        object.setStatus("U");
                                        object.setReason("A/c Blocked or Frozen");
                                    }
                                } else {
                                    //Name And Jtname Validation.
                                    String benNameOrgn = dto.getBeneName().trim();
                                    if (!(benNameOrgn.equalsIgnoreCase(custName)
                                            || benNameOrgn.equalsIgnoreCase(jtName1)
                                            || benNameOrgn.equalsIgnoreCase(jtName2)
                                            || benNameOrgn.equalsIgnoreCase(jtName3)
                                            || benNameOrgn.equalsIgnoreCase(jtName4))) {
                                        object.setStatus("U");
                                        object.setReason("Account Holder Name Invalid");
                                    } else {
                                        object.setStatus("S");
                                        object.setReason("");
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        object.setStatus("U");
                        object.setReason(ex.getMessage());
                    }
                } else {
                    object.setStatus("U");
                    object.setReason("This Record Reference Number Is Already Processed");
                }
                nonAadhaarDao.save(object);
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

    @Override
    public String npciCbdtInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt) throws ApplicationException {
        String message = "";
        CbsNpciInwardNonAadhaarDAO nonAadhaarDao = new CbsNpciInwardNonAadhaarDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);
                //Duplicate file upload checking
                if (i == 0) {
                    List<CbsNpciInwardNonAadhaar> isBatchList = nonAadhaarDao.duplicateSeqOnDate(dto.getOriginatorCode().trim(),
                            ymd.parse(dto.getSettlementDt()), dto.getFileSeqNo().trim());
                    if (!isBatchList.isEmpty()) {
                        throw new ApplicationException("This file has been already uploaded.");
                    }
                }

                message = "unsuccess";

                List<CbsNpciInwardNonAadhaar> isEntity = nonAadhaarDao.findByRrn(dto.getUserCreditRef().trim());
                if (!isEntity.isEmpty()) {
                    message = "duplicate";
                }
                CbsNpciInwardNonAadhaar object = new CbsNpciInwardNonAadhaar();

                object.setOriginatorCode(dto.getOriginatorCode().trim());
                object.setResponderCode(dto.getResponderCode().trim());
                object.setFileComingDt(ymd.parse(dto.getSettlementDt()));
                object.setFileSeqNo(dto.getFileSeqNo().trim());
                object.setRecordIdentifier(dto.getApbsTranCode().trim());
                object.setRrn(dto.getUserCreditRef().trim());
                object.setDestBankIfsc(dto.getDestBankIIN().trim());
                object.setDestBankAcno(dto.getDestBankAcno().trim());
                object.setFileRefNo(dto.getFileRefNo().trim());
                object.setBenNameOrgn("");
                object.setLpgId("");
                object.setAcValidFlag("");
                object.setBenNameMatchFlag("");
                object.setBenNameResponder("");
                object.setAadhaarNo("");
                object.setMobileNo("");
                object.setEmailId("");
                object.setReturnCode("");
                object.setFiller("");
                Date txnDt = dmy.parse(todayDt);
                object.setTranDate(txnDt);
                object.setTranTime(new Date());
                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());

                if (!message.equals("duplicate")) {
                    try {
                        if (!getAllBankIfsc().contains(object.getDestBankIfsc().toUpperCase())) {
                            object.setStatus("U");
                            object.setReason("Ifsc Code MisMatch");
                        } else {
                            List list = em.createNativeQuery("select accstatus,custname,ifnull(jtname1,'') as jtname1,"
                                    + "ifnull(jtname2,'') as jtname2,ifnull(jtname3,'') as jtname3,ifnull(jtname4,'') as "
                                    + "jtname4 from accountmaster where "
                                    + "acno='" + dto.getDestBankAcno().trim() + "'").getResultList();
                            if (list == null || list.isEmpty()) {
                                object.setStatus("U");
                                object.setReason("No Such Account");
                            } else {
                                Vector ele = (Vector) list.get(0);
                                String accStatus = ele.get(0).toString();
                                if (!accStatus.equals("1")) {
                                    if (accStatus.equals("9")) {
                                        object.setStatus("U");
                                        object.setReason("Account Closed or Transferred");
                                    } else {
                                        object.setStatus("U");
                                        object.setReason("A/c Blocked or Frozen");
                                    }
                                } else {
                                    object.setStatus("S");
                                    object.setReason("");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        object.setStatus("U");
                        object.setReason(ex.getMessage());
                    }
                } else {
                    object.setStatus("U");
                    object.setReason("This Record Reference Number Is Already Processed");
                }
                nonAadhaarDao.save(object);
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

    //NON-AADHAAR INPUT FILE GENERATION.
    public String generateNonAadhaarInput(String lpgType, String fileGenDt,
            String orgnBrCode, String enterBy, String todayDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String rrn = "", aadharNo = "", destBankIfsc = "", destBankAcno = "";
        String lpgId = "", headerId = "", detailId = "", aadharLocation = "";
        String fileNo = "", totalRecordNo = "", originatorCode = "", genFileName = "", custId = "";
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = elem.get(0).toString().trim();

            userList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where "
                    + "ref_rec_no='008' and ref_code='" + lpgType.trim() + "'").getResultList();
            if (userList.isEmpty()) {
                throw new ApplicationException("Please define data into Cbs Ref Rec Type for 008.");
            }
            elem = (Vector) userList.get(0);
            String responderCode = elem.get(0).toString().trim();   //Make it to 11 digit.

            List dataList = em.createNativeQuery("select cust_id,rrn,dest_bank_ifsc,dest_bank_acno,lpg_id,aadhar_no "
                    + "from cbs_aadhar_registration where dt='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "responder_code ='" + responderCode + "' and status in('F','U') and reg_type='NA'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("NA-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NA-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("NA-DRI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NA-DRI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            userList = em.createNativeQuery("select aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            elem = (Vector) userList.get(0);
            if (elem.get(0) == null || elem.get(1) == null
                    || elem.get(0).toString().trim().equals("")
                    || elem.get(1).toString().trim().equals("")
                    || elem.get(1).toString().trim().length() != 4) {
                throw new ApplicationException("Please define Aadhar Location and Bank Code.");
            }
            aadharLocation = elem.get(0).toString().trim();
            originatorCode = elem.get(1).toString().trim();    //Make it to 11 digits.

            userList = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and "
                    + "file_gen_type = '" + responderCode.substring(0, 2) + "I" + "'").getResultList();
            elem = (Vector) userList.get(0);
            fileNo = "1";
            if (elem.get(0) != null) {
                fileNo = elem.get(0).toString().trim();  //Make to 6 digit.
            }

            genFileName = "AV-" + originatorCode.toUpperCase() + "-" + responderCode.toUpperCase() + "-"
                    + npciUserName.toUpperCase() + "-" + ymdOne.format(curDt) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + orgnBrCode + "','" + responderCode.substring(0, 2) + "I" + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }

            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to 6 digit.
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = headerId + ParseFileUtil.addSuffixSpaces(originatorCode, 11)
                    + ParseFileUtil.addSuffixSpaces(responderCode, 11) + ymdOne.format(curDt)
                    + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addTrailingZeros(totalRecordNo, 6)
                    + ParseFileUtil.addSuffixSpaces("", 452) + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                custId = element.get(0).toString().trim();
                rrn = element.get(1).toString().trim();
                destBankIfsc = element.get(2).toString().trim();
                destBankAcno = element.get(3).toString().trim();
                lpgId = element.get(4).toString().trim();
                aadharNo = element.get(5).toString().trim();

                String benNameOrg = ftsRemote.ftsGetCustName(destBankAcno);
                benNameOrg = benNameOrg.replaceAll("[\\W_]", " ");
                benNameOrg = benNameOrg.length() > 100 ? benNameOrg.substring(0, 100) : benNameOrg;

                String individualStr = detailId + ParseFileUtil.addSuffixSpaces(rrn, 15)
                        + ParseFileUtil.addSuffixSpaces(destBankIfsc, 11) + ParseFileUtil.addSuffixSpaces(destBankAcno, 35)
                        + ParseFileUtil.addSuffixSpaces(benNameOrg, 100) + lpgId + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces("", 2)
                        + ParseFileUtil.addSuffixSpaces("", 200) + ParseFileUtil.addSuffixSpaces(aadharNo, 15)
                        + ParseFileUtil.addTrailingZeros("", 15) + ParseFileUtil.addSuffixSpaces("", 70)
                        + ParseFileUtil.addSuffixSpaces("", 16) + "\n";
                fw.write(individualStr);
                //Status Updation.
                n = em.createNativeQuery("update cbs_aadhar_registration set status='R' where "
                        + "cust_id='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Status Updation.");
                }
            }
            fw.close();
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

    //A/c Validation Return File
    public String generateNonAadhaarReturn(String lpgType, String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo, String processingMode, String H2HLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String headerId = "", detailId = "", aadharLocation = "";
        String fileNo = "", totalRecordNo = "", genFileName = "";
        String originatorCode = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();
            if (!processingMode.equalsIgnoreCase("H2H")) {
                list = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where "
                        + "ref_rec_no='008' and ref_code='" + lpgType.trim() + "'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define data into Cbs Ref Rec Type for 008.");
                }
                ele = (Vector) list.get(0);
                originatorCode = ele.get(0).toString().trim();   //Make it to 11 digit.
            } else {
                originatorCode = lpgType.trim();
            }
            List dataList = em.createNativeQuery("select responder_code,file_ref_no,filler,rrn,dest_bank_ifsc,"
                    + "dest_bank_acno,ben_name_orgn,lpg_id,aadhaar_no,mobile_no,email_id,status,reason from "
                    + "cbs_npci_inward_non_aadhaar where originator_code = '" + originatorCode + "' and "
                    + "file_seq_no='" + seqNo + "' and "
                    + "file_coming_dt ='" + ymd.format(dmy.parse(fileGenDt)) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("NA-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NA-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("NA-DRI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NA-DRI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            list = em.createNativeQuery("select aadhar_location from mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(0).toString().trim().equals("")) {
                throw new ApplicationException("Please define Aadhar Location.");
            }
            aadharLocation = ele.get(0).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "file_gen_type='" + originatorCode.substring(0, 2) + "R" + "'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            //Extracting Header Details
            ele = (Vector) dataList.get(0);
            String responderCode = ele.get(0).toString().trim();    //Make it to 11 digit
            String fileRefNo = ele.get(1).toString().trim();    //Make it to 10 digit
            String filler = ele.get(2).toString().trim();   //Make it to 452 digit
            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to 6 digit.

            // username changes
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
                genFileName = "AV-" + originatorCode.toUpperCase() + "-" + responderCode.toUpperCase() + "-"
                        + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(fileGenDt)) + "-"
                        + ParseFileUtil.addTrailingZeros(seqNo, 6) + "-RES.txt";
            } else {
                genFileName = "AV-" + originatorCode.toUpperCase() + "-" + responderCode.toUpperCase() + "-"
                        + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(fileGenDt)) + "-"
                        + ParseFileUtil.addTrailingZeros(seqNo, 6) + "-RES.txt";
            }

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + orgnBrCode + "','" + originatorCode.substring(0, 2) + "R" + "'," + Double.parseDouble(seqNo) + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }
            //Header Preparation.
            FileWriter fw = null;
            if (processingMode.equalsIgnoreCase("H2H")) {
                fw = new FileWriter(H2HLocation + genFileName);
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
            }
            String header = headerId + ParseFileUtil.addSuffixSpaces(originatorCode, 11)
                    + ParseFileUtil.addSuffixSpaces(responderCode, 11) + ymdOne.format(new Date())
                    + ParseFileUtil.addSuffixSpaces(fileRefNo, 10) + ParseFileUtil.addTrailingZeros(totalRecordNo, 6)
                    + ParseFileUtil.addSuffixSpaces(filler, 452) + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String rrn = element.get(3).toString();
                String destBankIfsc = element.get(4).toString();
                String destBankAcno = element.get(5).toString();
                String benNameOrgn = element.get(6).toString();
                String lpgId = element.get(7).toString();
                String aadhaarNo = element.get(8).toString();
                String mobileNo = element.get(9).toString();
                String emailId = element.get(10).toString();
                String status = element.get(11).toString();
                String reason = element.get(12).toString();
                String[] arr = getNonAadhaarReturnDetails(status, reason, destBankAcno);

                String individualStr = detailId + ParseFileUtil.addSuffixSpaces(rrn, 15)
                        + ParseFileUtil.addSuffixSpaces(destBankIfsc, 11) + ParseFileUtil.addSuffixSpaces(destBankAcno, 35)
                        + ParseFileUtil.addSuffixSpaces(benNameOrgn, 100) + lpgId + arr[0] + arr[1] + arr[2]
                        + ParseFileUtil.addSuffixSpaces(aadhaarNo, 15) + ParseFileUtil.addTrailingZeros(mobileNo, 15)
                        + ParseFileUtil.addSuffixSpaces(emailId, 70) + ParseFileUtil.addSuffixSpaces("", 16) + "\n";

                fw.write(individualStr);
            }
            if (processingMode.equalsIgnoreCase("H2H")) {
                int u = em.createNativeQuery("update cbs_npci_inward_non_aadhaar set FILE_GEN_FLAG ='Y' "
                        + "where FILE_SEQ_NO='" + seqNo + "' and FILE_COMING_DT = '" + ymd.format(dmy.parse(fileGenDt)) + "'").executeUpdate();
                if (u <= 0) {
                    throw new ApplicationException("Problem In Cbs Npci inward non aadhar flag updation.");
                }
            }
            fw.close();
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
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
    //Added by Rahul for CBDT Files Generation

    public String generateNonAadhaarCBDT(String lpgType, String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String rrn = "", destBankIfsc = "", destBankAcno = "", PrimaryPanno = "", SecondaryPanno = "";
        String headerId = "", detailId = "", aadharLocation = "", jointName = "";
        String fileNo = "", totalRecordNo = "", genFileName = "", custId = "", recordIdentifier = "";
        String acvalidflag = "";
        String jointacflag = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

            list = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where "
                    + "ref_rec_no='008' and ref_code='" + lpgType.trim() + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define data into Cbs Ref Rec Type for 008.");
            }
            ele = (Vector) list.get(0);
            String originatorCode = ele.get(0).toString().trim();

            List dataList = em.createNativeQuery("select responder_code,file_ref_no,filler,rrn,dest_bank_ifsc,"
                    + "dest_bank_acno,record_identifier,status,reason from "
                    + "cbs_npci_inward_non_aadhaar where originator_code = '" + originatorCode + "' and "
                    + "file_seq_no='" + seqNo + "' and "
                    + "file_coming_dt ='" + ymd.format(dmy.parse(fileGenDt)) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("NA-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NA-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("NA-DRI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::NA-DRI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            list = em.createNativeQuery("select aadhar_location from mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(0).toString().trim().equals("")) {
                throw new ApplicationException("Please define Aadhar Location.");
            }
            aadharLocation = ele.get(0).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "file_gen_type='" + originatorCode.substring(0, 2) + "R" + "'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            // Extracting Header Detail 
            ele = (Vector) dataList.get(0);
            String responderCode = ele.get(0).toString().trim();    //Make it to 11 digit
            String fileRefNo = ele.get(1).toString().trim();    //Make it to 10 digit
            String filler = ele.get(2).toString().trim();   //Make it to 452 digit
            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to 6 digit.

            genFileName = "AV-" + originatorCode.toUpperCase() + "-" + responderCode.toUpperCase() + "-"
                    + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(fileGenDt)) + "-"
                    + ParseFileUtil.addTrailingZeros(seqNo, 6) + "-RES.txt";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + orgnBrCode + "','" + originatorCode.substring(0, 2) + "R" + "'," + Double.parseDouble(seqNo) + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = headerId + ParseFileUtil.addSuffixSpaces(originatorCode, 11)
                    + ParseFileUtil.addSuffixSpaces(responderCode, 11) + ymdOne.format(new Date())
                    + ParseFileUtil.addSuffixSpaces(fileRefNo, 10) + ParseFileUtil.addTrailingZeros(totalRecordNo, 6)
                    + ParseFileUtil.addSuffixSpaces(filler, 452) + "\n";
            fw.write(header);
            // Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                rrn = element.get(3).toString();
                destBankIfsc = element.get(4).toString();
                destBankAcno = element.get(5).toString();
                recordIdentifier = element.get(6).toString();
                String status = element.get(7).toString();
                String reason = element.get(8).toString();
                String[] arr = getNonAadhaarReturnDetails(status, reason, destBankAcno);
                acvalidflag = "00"; // for Successs Status  

                if (status.equalsIgnoreCase("U")) { // FOR UNSUCCESS STATUS
                    acvalidflag = "01";
                }

                PrimaryPanno = ftsRemote.ftsgetPanNo(destBankAcno);
                SecondaryPanno = ftsRemote.ftsgetjointDetail(destBankAcno);
                if (!(PrimaryPanno.equals("") || PrimaryPanno.equals(null))) {
                    jointName = ftsRemote.ftsgetJointname(destBankAcno);
                } else {
                    jointName = "";
                }

                if (acvalidflag.equalsIgnoreCase("00")) {
                    if (!(jointName.equalsIgnoreCase(null) || jointName.equalsIgnoreCase(""))) {
                        jointacflag = "00";
                    } else {
                        jointacflag = "01";
                    }
                } else if (acvalidflag.equalsIgnoreCase("01")) {
                    jointacflag = ParseFileUtil.addSuffixSpaces("", 2);
                }
                // PrimaryPanno validation
                if (PrimaryPanno.equals(null) || PrimaryPanno.equals("")) {
                    PrimaryPanno = ParseFileUtil.addSuffixSpaces("", 10);
                } else {
                    PrimaryPanno = ParseFileUtil.addSuffixSpaces(PrimaryPanno, 10);
                }
                //SecondaryPanno validation
                if (jointName.equals("") || jointName.equals(null)) {
                    SecondaryPanno = ParseFileUtil.addSuffixSpaces("", 10);
                } else {
                    SecondaryPanno = ParseFileUtil.addSuffixSpaces(SecondaryPanno, 10);
                }

                String individualStr = detailId + ParseFileUtil.addSuffixSpaces(rrn, 15)
                        + ParseFileUtil.addSuffixSpaces(destBankIfsc, 11) + ParseFileUtil.addSuffixSpaces(destBankAcno, 35)
                        + acvalidflag + jointacflag + PrimaryPanno + SecondaryPanno + ParseFileUtil.addSuffixSpaces("", 413) + "\n";
                fw.write(individualStr);
            }
            fw.close();
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

    //It will according to the given document from NPCI.
    public String[] getNonAadhaarReturnDetails(String status, String reason, String destBankAcno) throws ApplicationException {
        String[] arr = new String[3];
        String acValidFlag = "", benNameMatchFlag = "", benNameResponder = "";
        try {
            if (status.equalsIgnoreCase("S")) {
                acValidFlag = "00";
                benNameMatchFlag = "00";
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("No Such Account")) {
                acValidFlag = "02";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("Ifsc Code Not Found")) {
                acValidFlag = "03";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("Ifsc Code MisMatch")) {
                acValidFlag = "03";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("Account Closed or Transferred")) {
                acValidFlag = "01";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("A/c Blocked or Frozen")) {
                acValidFlag = "68";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("Account Holder Name Invalid")) {
                acValidFlag = "00";
                benNameMatchFlag = "01";

                String benNameResp = ftsRemote.ftsGetCustName(destBankAcno);
                benNameResp = benNameResp.replaceAll("[\\W_]", " ");
                benNameResp = benNameResp.length() > 200 ? benNameResp.substring(0, 200) : benNameResp;
                benNameResponder = ParseFileUtil.addSuffixSpaces(benNameResp, 200);
            } else if (status.equalsIgnoreCase("U") && reason.equalsIgnoreCase("This Record Reference Number Is Already Processed")) {
                acValidFlag = "04";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            } else {
                acValidFlag = "04";
                benNameMatchFlag = ParseFileUtil.addSuffixSpaces("", 2);
                benNameResponder = ParseFileUtil.addSuffixSpaces("", 200);
            }
            arr[0] = acValidFlag;
            arr[1] = benNameMatchFlag;
            arr[2] = benNameResponder;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }

    //NON-AADHAAR SHOW FILES.
    public List<NpciFileDto> showNonAadhaarGeneratedFiles(String fileType, String lpgType, String fileShowDt,
            Double seqNo) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List fetchList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where "
                    + "ref_rec_no='008' and ref_code='" + lpgType.trim() + "'").getResultList();
            if (fetchList.isEmpty()) {
                throw new ApplicationException("Please define data into Cbs Ref Rec Type for 008.");
            }
            Vector elem = (Vector) fetchList.get(0);
            String lpgName = elem.get(0).toString().trim();

            List list = null;
            if (fileType.equalsIgnoreCase("SR")) {  //Return Files
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,file_gen_by "
                        + "from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + lpgName.substring(0, 2) + "R" + "' and seq_no=" + seqNo + "").getResultList();
            } else if (fileType.equalsIgnoreCase("SI")) {   //Input Files
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,file_gen_by "
                        + "from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + lpgName.substring(0, 2) + "I" + "'").getResultList();
            } else if (fileType.equalsIgnoreCase("SC")) {  // CBDT Files
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,file_gen_by "
                        + "from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + lpgName.substring(0, 2) + "R" + "'").getResultList();
            }
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

    public List getLPGTypeList(String refRecNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type "
                    + "where ref_rec_no='" + refRecNo.trim() + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getSeqNoForNonAadhaar(String lpgType, String inComingDt) throws ApplicationException {
        try {
            List fetchList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where "
                    + "ref_rec_no='008' and ref_code='" + lpgType.trim() + "'").getResultList();
            if (fetchList.isEmpty()) {
                throw new ApplicationException("Please define data into Cbs Ref Rec Type for 008.");
            }
            Vector elem = (Vector) fetchList.get(0);
            String lpgName = elem.get(0).toString().trim();

            List list = em.createNativeQuery("select distinct file_seq_no from "
                    + "cbs_npci_inward_non_aadhaar where file_coming_dt='" + ymd.format(dmy.parse(inComingDt)) + "' and "
                    + "originator_code='" + lpgName + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this settlement date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private boolean isUserCreditRefExist(List userCreditRefDetail, String userCreditRef) throws ApplicationException {
        try {
            for (int i = 0; i < userCreditRefDetail.size(); i++) {
                Vector vec = (Vector) userCreditRefDetail.get(i);
                if (vec.get(0).toString().equals(userCreditRef)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private int getAccountIndex(List acctDetails, String acno) throws ApplicationException {
        try {
            int index = -1;
            for (int i = 0; i < acctDetails.size(); i++) {
                Vector vec = (Vector) acctDetails.get(i);
//                if (vec.get(6).toString().equals(acno)) {
                if (vec.get(6).toString().equals(acno) || vec.get(8).toString().equals(acno)) {
                    index = i;
                    break;
                }
            }
            return index;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private int getBrIndex(List brList, int brCode) throws ApplicationException {
        try {
            int index = -1;
            for (int i = 0; i < brList.size(); i++) {
                Vector vec = (Vector) brList.get(i);
                if (Integer.parseInt(vec.get(4).toString()) == brCode) {
                    index = i;
                    break;
                }
            }
            return index;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isBankIfsc(List brList, String destBankIIN) {
        boolean result = false;
        try {
            for (int i = 0; i < brList.size(); i++) {
                Vector vec = (Vector) brList.get(i);
                if (destBankIIN.equalsIgnoreCase(vec.get(3).toString().trim())) {
                    result = true;
                    break;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return result;
    }

    public boolean isBankMicr(List brList, String destBankIIN) {
        boolean result = false;
        try {
            for (int i = 0; i < brList.size(); i++) {
                Vector vec = (Vector) brList.get(i);
                String micrNo = ParseFileUtil.addTrailingZeros(vec.get(0).toString().trim(), 3)
                        + ParseFileUtil.addTrailingZeros(vec.get(1).toString().trim(), 3)
                        + ParseFileUtil.addTrailingZeros(vec.get(2).toString().trim(), 3);
                if (destBankIIN.equalsIgnoreCase(micrNo)) {
                    result = true;
                    break;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return result;
    }

    //Uploading Of Ach Inward.
    public String newNpciAchInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String fileName, String processingMode) throws ApplicationException {
        String message = "";
        BigDecimal totalAmount = new BigDecimal("0");
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        CbsNpciInwardDAO cbsNpciInwardDAO = new CbsNpciInwardDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            Date txnDt = dmy.parse(todayDt);
            String businessDt = ymd.format(txnDt);
            List dayBeginList = em.createNativeQuery("select date from cbs_bankdays where date='" + businessDt + "' and daybeginflag = 'Y' "
                    + "and dayendflag = 'N'").getResultList();
            if (dayBeginList.isEmpty()) {
                throw new ApplicationException("Bank has not start the day.");
            }

            List userList = em.createNativeQuery("select npciusername from securityinfo where userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }

            userList = em.createNativeQuery("select ifnull(file_name,'') from cbs_npci_inward where "
                    + "file_name='" + fileName + "'").getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }

            userList = em.createNativeQuery("select ifnull(iin,'') as iin from mb_sms_sender_bank_detail").getResultList();
            Vector iinEle = (Vector) userList.get(0);
            String iin = iinEle.get(0).toString().trim();

            String acctDetail = "", userCreditRefDetail = "", fileRefNo = "";

            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);

                String userCreditRef = dto.getUserCreditRef().trim();
                String destBankAcno = dto.getDestBankAcno().trim();

                if ((acctDetail.equals("") && userCreditRefDetail.equals(""))) {
                    userCreditRefDetail = "\'" + userCreditRef + "\'";
                    acctDetail = "\'" + destBankAcno + "\'";
                } else {
                    userCreditRefDetail = userCreditRefDetail + ",\'" + userCreditRef + "\'";
                    acctDetail = acctDetail + ",\'" + destBankAcno + "\'";
                }

                //For Details Only
                if (i == 0) {
                    fileRefNo = dto.getFileRefNo();
                }
            }

            //Taking Account From Mapping Table
            String mappingAccountDetail = "";

            List cbsAcnoMappingList = em.createNativeQuery("select new_ac_no from cbs_acno_mapping where "
                    + "old_ac_no in(" + acctDetail + ") or new_ac_no in(" + acctDetail + ")").getResultList();
            if (cbsAcnoMappingList.isEmpty()) {
                mappingAccountDetail = "999999999999";
            } else {
                for (int x = 0; x < cbsAcnoMappingList.size(); x++) {
                    Vector mappingVec = (Vector) cbsAcnoMappingList.get(x);
                    if (mappingAccountDetail.equals("")) {
                        mappingAccountDetail = "\'" + mappingVec.get(0).toString() + "\'";
                    } else {
                        mappingAccountDetail = mappingAccountDetail + ",\'" + mappingVec.get(0).toString() + "\'";
                    }
                }
            }

            //End Here

//            List acNoList = em.createNativeQuery("select accstatus,custname,ifnull(jtname1,'') as jtname1, ifnull(jtname2,'') as jtname2, "
//                    + "ifnull(jtname3,'') as jtname3,ifnull(jtname4,'') as jtname4, acno,acctnature from accountmaster a, accounttypemaster att  "
//                    + "where att.AcctCode = a.Accttype and acno in (" + acctDetail + ")").getResultList();

            List acNoList = em.createNativeQuery("select accstatus,custname,ifnull(jtname1,'') as jtname1, ifnull(jtname2,'') as jtname2, "
                    + "ifnull(jtname3,'') as jtname3,ifnull(jtname4,'') as jtname4, acno,acctnature,map.old_ac_no from accountmaster a, accounttypemaster att,cbs_acno_mapping map  "
                    + "where att.AcctCode = a.Accttype and acno in (" + mappingAccountDetail + ") and map.new_ac_no=a.acno").getResultList();

            List userCreditRefList = em.createNativeQuery("select user_credit_reference from cbs_npci_inward where iw_type='ACH' and "
                    + "user_credit_reference in (" + userCreditRefDetail + ")").getResultList();

            List brList = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as bank_code,ifnull(b.branchcode,'') as "
                    + "brach_code,ifnull(ifsc_code,''), m.brncode from bnkadd b,branchmaster m where b.alphacode=m.alphacode ").getResultList();

            List dataList = em.createNativeQuery("select alphacode from branchmaster where "
                    + "brncode = '" + Integer.parseInt(uploadBrCode) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define alphacode for-->" + uploadBrCode);
            }
            Vector dataVec = (Vector) dataList.get(0);
            String orgnAlphaCode = dataVec.get(0).toString();

            //Details Preparation
//            if (fileRefNo.toLowerCase().contains("dbl")) {
//                narration = "AT " + orgnAlphaCode + ": ACH LPG Subsidy";
//            } else if (fileRefNo.toLowerCase().contains("pfm")) {
//                narration = "AT " + orgnAlphaCode + ": ACH Pension/Scholarship Subsidy";
//            } else {
//                narration = "AT " + orgnAlphaCode + ": ACH Subsidy";
//            }


            System.out.println("Start processing -->>>>>>>>>>>>>>>>" + new Date());

            Float trsno = ftsRemote.getTrsNo();

            String accStatus = "", partyNature = "", micrNo = "", ifscCode = "", cityCode = "", bankCode = "", branchCode = "",
                    subsidyAc = "";
            Double subsidyAmt = 0d;
            float recNo = ftsRemote.getRecNo();
            for (int i = 0; i < inputList.size(); i++) {
//                message = "unsuccess";
                NpciInwardDto dto = inputList.get(i);
//                if (isUserCreditRefExist(userCreditRefList, dto.getUserCreditRef().trim())) {
//                    message = "duplicate";
//                }

                CbsNpciInward object = new CbsNpciInward();

                object.setApbsTranCode(dto.getApbsTranCode().trim());
                object.setDestBankIin(dto.getDestBankIIN().trim());
                object.setDestActype(dto.getDestAcType().trim());
                object.setLedgerNo(dto.getLedgerNo().trim());
                object.setBeneAadhaarNo(dto.getBeneAadhaarNo().trim());

                object.setBeneName(dto.getBeneName().trim());
                object.setSponsorBankIin(dto.getSponsorBankIIN().trim());
                object.setUserNo(dto.getUserNumber().trim());
                object.setUserNameNarration(dto.getUserName().trim());
                object.setUserCreditReference(dto.getUserCreditRef().trim());

                object.setAmount(dto.getAmount().trim());
                object.setReservedOne("");
                object.setReservedTwo("");
                object.setReservedThree("");
                object.setDestBankAcno(dto.getDestBankAcno().trim());

                object.setReturnReasonCode("");
                object.setTranDate(txnDt);
                object.setTranTime(new Date());
                object.setValueDate(txnDt);

                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());
                object.setTrsNo(trsno.doubleValue());
                object.setSettlementDate(ymd.parse(dto.getSettlementDt()));
                object.setIwType("ACH");

                object.setAchSettlementCycle(dto.getSettlementCycle().trim());
                object.setAchControl2nd(dto.getControll2nd().trim());
                object.setAchControl5th(dto.getControll5th().trim());
                object.setAchControl7th(dto.getControll7th().trim());
                object.setAchControl8th(dto.getControll8th().trim());

                object.setAchControl10th(dto.getControll10th().trim());
                object.setAchItemSeqNo(dto.getItemSeqNo().trim());
                object.setAchChecksum(dto.getCheckSum().trim());
                object.setAchFiller(dto.getAchFiller().trim());
                object.setAchProductType(dto.getProductType().trim());
                object.setAchUmrn(dto.getUmrn().trim());
                object.setAchReservedFlag(dto.getReservedFlag().trim());
                object.setAchReservedReason(dto.getReservedReason().trim());
                object.setAchHeaderDestIin(dto.getHeaderDestIIN().trim());
                object.setCbsAcno("");
                object.setCbsAcname("");
                object.setFileName(fileName);
                if (processingMode.equalsIgnoreCase("H2H")) {
                    object.setFileGenFlag("N");
                }

//                if (!message.equals("duplicate")) {
                try {
                    //Account No Validation.
                    int index = getAccountIndex(acNoList, dto.getDestBankAcno().trim());
                    if (index == -1) {
                        object.setStatus("U");
                        object.setReason("No Such Account");
                    } else {
                        Vector ele = (Vector) acNoList.get(index);
                        accStatus = ele.get(0).toString();
                        partyNature = ele.get(7).toString();
                        //Ifsc Code,IIN and Micr Validation.

//                            int brIndex = getBrIndex(brList, Integer.parseInt(dto.getDestBankAcno().trim().substring(0, 2)));
//                            if (brIndex > -1) {
//                                ele = (Vector) brList.get(brIndex);
//                                cityCode = ele.get(0).toString().trim();
//                                bankCode = ele.get(1).toString().trim();
//                                branchCode = ele.get(2).toString().trim();
//
//                                micrNo = ParseFileUtil.addTrailingZeros(cityCode, 3) + ParseFileUtil.addTrailingZeros(bankCode, 3) + ParseFileUtil.addTrailingZeros(branchCode, 3);
//                                ifscCode = ele.get(3).toString().trim();
//                            }

//                            if (!(dto.getDestBankIIN().trim().equalsIgnoreCase(ifscCode)
//                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(iin)
//                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(micrNo))) {
//                                object.setStatus("U");
//                                object.setReason("Ifsc Code Not Found");
//                            } 
                        if (!(isBankIfsc(brList, dto.getDestBankIIN().trim())
                                || dto.getDestBankIIN().trim().equalsIgnoreCase(iin)
                                || isBankMicr(brList, dto.getDestBankIIN().trim()))) {
                            object.setStatus("U");
                            object.setReason("Ifsc Code Not Found");
                        } else {
                            if (accStatus.equals("9")) {
                                object.setStatus("U");
                                object.setReason("Account Closed or Transferred");
                            } else if (accStatus.equals("4")) {
                                object.setStatus("U");
                                object.setReason("Frozen");
                            } else if (accStatus.equals("2")) {
                                object.setStatus("U");
                                object.setReason("Inoperative");
                            } else if (accStatus.equals("8")) {
                                object.setStatus("U");
                                object.setReason("Operation Stopped");
                            } else if (accStatus.equals("15")) {
                                object.setStatus("U");
                                object.setReason("Deaf");
                            } else if (accStatus.equals("3") || accStatus.equals("5") || accStatus.equals("6")
                                    || accStatus.equals("10") || accStatus.equals("11") || accStatus.equals("12")
                                    || accStatus.equals("13") || accStatus.equals("14")) {
                                object.setStatus("U");
                                object.setReason("Invalid Account Status"); //71 - Invalid Account Type
                            } else {
//                                subsidyAc = dto.getDestBankAcno().trim();

                                subsidyAc = ftsRemote.getNewAccountNumber(dto.getDestBankAcno().trim());

                                subsidyAmt = Double.parseDouble(formatter.format(new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal("100")).doubleValue()));
                                //Subsidy Processing
                                String narration = "By NPCI " + object.getUserNameNarration() + " " + object.getUserCreditReference(); //dto.getUserName()

                                if (fileName.toUpperCase().contains("LON")) {
                                    Integer insertRs = em.createNativeQuery("insert into " + CbsUtil.getReconTableName(partyNature) + "( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                            + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                                            + "org_brnid,dest_brnid,tran_id,term_id) values('" + subsidyAc + "',0,'" + businessDt + "','" + businessDt + "',0," + subsidyAmt + ", 0,2,"
                                            + "'" + narration + "',51,'','19000101','" + uploadingUserName + "','Y'," + recNo + ",3,'System'," + trsno + ",80,"
                                            + 0 + ",'','A',now(),'" + uploadBrCode + "','" + subsidyAc.substring(0, 2) + "',0,'')").executeUpdate();
                                    if (insertRs <= 0) {
                                        throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + subsidyAc);
                                    }

                                } else {
                                    Integer insertRs = em.createNativeQuery("insert into " + CbsUtil.getReconTableName(partyNature) + "( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                            + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                                            + "org_brnid,dest_brnid,tran_id,term_id) values('" + subsidyAc + "',0,'" + businessDt + "','" + businessDt + "',0," + subsidyAmt + ", 0,2,"
                                            + "'" + narration + "',51,'','19000101','" + uploadingUserName + "','Y'," + recNo + ",3,'System'," + trsno + ",66,"
                                            + 0 + ",'','A',now(),'" + uploadBrCode + "','" + subsidyAc.substring(0, 2) + "',0,'')").executeUpdate();
                                    if (insertRs <= 0) {
                                        throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + subsidyAc);
                                    }
                                }

                                Integer varupdateReconBalanList = em.createNativeQuery("Update " + CbsUtil.getReconBalanTableName(partyNature)
                                        + " set balance=ifnull(balance,0) + " + subsidyAmt + " ,dt=CURRENT_TIMESTAMP where acno='" + subsidyAc
                                        + "'").executeUpdate();
                                if (varupdateReconBalanList <= 0) {
                                    throw new ApplicationException("ReconBalan is not updated");
                                }

                                String resultLastUpdation = ftsRemote.lastTxnDateUpdation(subsidyAc);

                                recNo = recNo + 1;
                                object.setStatus("S");
                                object.setReason("");
                                totalAmount = totalAmount.add(new BigDecimal(subsidyAmt));

                                //Adding Object For Sms
                                SmsToBatchTo to = new SmsToBatchTo();
                                to.setAcNo(subsidyAc);
                                to.setCrAmt(subsidyAmt);
                                to.setDrAmt(0d);
                                to.setTranType(2);
                                to.setTy(0);
                                to.setTxnDt(todayDt);
                                to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                                smsBatchList.add(to);
                                //End
                            }
                        }
                    }
                } catch (Exception ex) {
                    object.setStatus("U");
                    object.setReason(ex.getMessage());
                }
//                } else {
//                    object.setStatus("U");
//                    object.setReason("This User Credit Reference Is Already Processed");
//                }
                cbsNpciInwardDAO.save(object);
            }

            String glAccount = "";
            ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(em);

            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("APB-ACH-HEAD");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 1) {
                throw new ApplicationException("Please define code for::APB-ACH-HEAD");
            }
            if (!(paramEntity.getCode() == 0 || paramEntity.getCode() == 1)) {
                throw new ApplicationException("Please define proper code for APB-ACH-HEAD");
            }
            String apbAchHead = paramEntity.getCode().toString().trim();

            String repName = "";
            if (apbAchHead.equals("1")) {
                repName = "NPCI-INW-CR";
            } else if (apbAchHead.equals("0")) {
                repName = "ACH-INW-CR";
            }
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose(repName);
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("Please define proper head for NPCI.");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                glAccount = uploadBrCode + abbPojo.getAcno();
            }

            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                if (fileName.toUpperCase().contains("LON")) {
                    int glRs = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,details, iy, instno, "
                            + "instdt, enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,"
                            + "tran_id,term_id,adviceno,advicebrncode) values('" + glAccount + "',1,'" + businessDt + "','" + businessDt + "',"
                            + totalAmount.doubleValue() + ",0, 0,2,'ACH Subsidy GL Entry',51,'','19000101','" + uploadingUserName + "','Y'," + recNo
                            + ",3,'System'," + trsno + ",80,0,'','',now(),'" + uploadBrCode + "','" + uploadBrCode + "',0,'','','')").executeUpdate();
                    if (glRs <= 0) {
                        throw new ApplicationException("Problem In Npci Inward Head Entry");
                    }
                    message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0, totalAmount.doubleValue(), "Y", "Y");
                    if (!message.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Problem In Npci Inward Head Balance Updation.");
                    }
                } else {
                    int glRs = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,details, iy, instno, "
                            + "instdt, enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,"
                            + "tran_id,term_id,adviceno,advicebrncode) values('" + glAccount + "',1,'" + businessDt + "','" + businessDt + "',"
                            + totalAmount.doubleValue() + ",0, 0,2,'ACH Subsidy GL Entry',51,'','19000101','" + uploadingUserName + "','Y'," + recNo
                            + ",3,'System'," + trsno + ",66,0,'','',now(),'" + uploadBrCode + "','" + uploadBrCode + "',0,'','','')").executeUpdate();
                    if (glRs <= 0) {
                        throw new ApplicationException("Problem In Npci Inward Head Entry");
                    }
                    message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0, totalAmount.doubleValue(), "Y", "Y");
                    if (!message.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Problem In Npci Inward Head Balance Updation.");
                    }
                }

            }
            int n = em.createNativeQuery("UPDATE reconvmast SET recno = " + recNo).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in updation in recno");
            }
            System.out.println("End processing -->>>>>>>>>>>>>>>>" + new Date());
            //In case of h2h
            if (processingMode.equalsIgnoreCase("H2H")) {
                fileName = fileName + ".txt";
                n = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + fileName + "','IW')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in updation in recno");
                }
            }
            ut.commit();

            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Npci Inward Credit." + e.getMessage());
            }
            //End here
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

    public String npciAchInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt) throws ApplicationException {
        String message = "";
        BigDecimal totalAmount = new BigDecimal("0");
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        CbsNpciInwardDAO cbsNpciInwardDAO = new CbsNpciInwardDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            Float trsno = ftsRemote.getTrsNo();
            for (int i = 0; i < inputList.size(); i++) {
                message = "unsuccess";
                NpciInwardDto dto = inputList.get(i);
                List<CbsNpciInward> isEntity = cbsNpciInwardDAO.findByUserCreditReferenceAndIwType(dto.getUserCreditRef().trim(),
                        "ACH");
                if (!isEntity.isEmpty()) {
                    message = "duplicate";
                }
                CbsNpciInward object = new CbsNpciInward();

                object.setApbsTranCode(dto.getApbsTranCode().trim());
                object.setDestBankIin(dto.getDestBankIIN().trim());
                object.setDestActype(dto.getDestAcType().trim());
                object.setLedgerNo(dto.getLedgerNo().trim());
                object.setBeneAadhaarNo(dto.getBeneAadhaarNo().trim());

                object.setBeneName(dto.getBeneName().trim());
                object.setSponsorBankIin(dto.getSponsorBankIIN().trim());
                object.setUserNo(dto.getUserNumber().trim());
                object.setUserNameNarration(dto.getUserName().trim());
                object.setUserCreditReference(dto.getUserCreditRef().trim());

                object.setAmount(dto.getAmount().trim());
                object.setReservedOne("");
                object.setReservedTwo("");
                object.setReservedThree("");
                object.setDestBankAcno(dto.getDestBankAcno().trim());

                object.setReturnReasonCode("");
                Date txnDt = dmy.parse(todayDt);
                object.setTranDate(txnDt);
                object.setTranTime(new Date());
                object.setValueDate(txnDt);

                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());
                object.setTrsNo(trsno.doubleValue());
                object.setSettlementDate(ymd.parse(dto.getSettlementDt()));
                object.setIwType("ACH");

                object.setAchSettlementCycle(dto.getSettlementCycle().trim());
                object.setAchControl2nd(dto.getControll2nd().trim());
                object.setAchControl5th(dto.getControll5th().trim());
                object.setAchControl7th(dto.getControll7th().trim());
                object.setAchControl8th(dto.getControll8th().trim());

                object.setAchControl10th(dto.getControll10th().trim());
                object.setAchItemSeqNo(dto.getItemSeqNo().trim());
                object.setAchChecksum(dto.getCheckSum().trim());
                object.setAchFiller(dto.getAchFiller().trim());
                object.setAchProductType(dto.getProductType().trim());
                object.setAchUmrn(dto.getUmrn().trim());
                object.setAchReservedFlag(dto.getReservedFlag().trim());
                object.setAchReservedReason(dto.getReservedReason().trim());
                object.setAchHeaderDestIin(dto.getHeaderDestIIN().trim());
                object.setCbsAcno("");
                object.setCbsAcname("");

                if (!message.equals("duplicate")) {
                    try {
                        //Account No Validation.
                        List list = em.createNativeQuery("select accstatus,custname,ifnull(jtname1,'') as jtname1,"
                                + "ifnull(jtname2,'') as jtname2,ifnull(jtname3,'') as jtname3,ifnull(jtname4,'') as "
                                + "jtname4 from accountmaster where "
                                + "acno='" + dto.getDestBankAcno().trim() + "'").getResultList();
                        if (list == null || list.isEmpty()) {
                            object.setStatus("U");
                            object.setReason("No Such Account");
                        } else {
                            Vector ele = (Vector) list.get(0);
                            String accStatus = ele.get(0).toString();
                            //Ifsc Code,IIN and Micr Validation.
                            list = em.createNativeQuery("select ifnull(ifsc_code,'') from  branchmaster "
                                    + "where brncode = " + Integer.parseInt(dto.getDestBankAcno().
                                    trim().substring(0, 2)) + "").getResultList();
                            ele = (Vector) list.get(0);
                            String ifscCode = ele.get(0).toString().trim();

                            list = em.createNativeQuery("select ifnull(iin,'') as iin from "
                                    + "mb_sms_sender_bank_detail").getResultList();
                            ele = (Vector) list.get(0);
                            String iin = ele.get(0).toString().trim();

                            String micrNo = "";

                            list = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                                    + "bank_code,ifnull(b.branchcode,'') as brach_code from bnkadd b,branchmaster m where "
                                    + "b.alphacode=m.alphacode and "
                                    + "m.brncode=" + Integer.parseInt(dto.getDestBankAcno().trim().substring(0, 2)) + "").getResultList();
                            if (!list.isEmpty()) {
                                ele = (Vector) list.get(0);
                                String cityCode = ele.get(0).toString().trim();
                                String bankCode = ele.get(1).toString().trim();
                                String branchCode = ele.get(2).toString().trim();

                                micrNo = ParseFileUtil.addTrailingZeros(cityCode, 3) + ParseFileUtil.addTrailingZeros(bankCode, 3) + ParseFileUtil.addTrailingZeros(branchCode, 3);
                            }

                            if (!(dto.getDestBankIIN().trim().equalsIgnoreCase(ifscCode)
                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(iin)
                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(micrNo))) {
                                object.setStatus("U");
                                object.setReason("Ifsc Code Not Found");
                            } else {
                                if (accStatus.equals("9")) {
                                    object.setStatus("U");
                                    object.setReason("Account Closed or Transferred");
                                } else {
                                    String subsidyAc = dto.getDestBankAcno().trim();
                                    Double subsidyAmt = Double.parseDouble(formatter.format(new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal("100")).doubleValue()));
                                    //Subsidy Processing
                                    message = ibtRemote.cbsPostingSx(subsidyAc, 0, ymd.format(txnDt),
                                            subsidyAmt, 0f, 2, "ACH Subsidy Entry", 0f, "A", "", "", 3, 0f,
                                            ftsRemote.getRecNo(), 66, subsidyAc.substring(0, 2), uploadBrCode,
                                            uploadingUserName, uploadingUserName, trsno, "", "");
                                    if (message.substring(0, 4).equalsIgnoreCase("true")) {
                                        object.setStatus("S");
                                        object.setReason("");

                                        totalAmount = totalAmount.add(new BigDecimal(subsidyAmt));
                                        //Adding Object For Sms
                                        SmsToBatchTo to = new SmsToBatchTo();
                                        to.setAcNo(subsidyAc);
                                        to.setCrAmt(subsidyAmt);
                                        to.setDrAmt(0d);
                                        to.setTranType(2);
                                        to.setTy(0);
                                        to.setTxnDt(todayDt);
                                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                                        smsBatchList.add(to);
                                        //End
                                    } else {
                                        object.setStatus("U");
                                        object.setReason(message);
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        object.setStatus("U");
                        object.setReason(ex.getMessage());
                    }
                } else {
                    object.setStatus("U");
                    object.setReason("This User Credit Reference Is Already Processed");
                }
                cbsNpciInwardDAO.save(object);
            }
            //Performing General Ledger Transaction of Debit
            String glAccount = "";
            ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(em);

            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("APB-ACH-HEAD");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 1) {
                throw new ApplicationException("Please define code for::APB-ACH-HEAD");
            }
            if (!(paramEntity.getCode() == 0 || paramEntity.getCode() == 1)) {
                throw new ApplicationException("Please define proper code for APB-ACH-HEAD");
            }
            String apbAchHead = paramEntity.getCode().toString().trim();

            List<AbbParameterInfo> abbParameterInfoList = null;
            if (apbAchHead.equals("1")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NPCI-INW-CR");
            } else if (apbAchHead.equals("0")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ACH-INW-CR");
            }
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("Please define proper head for NPCI.");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                glAccount = uploadBrCode + abbPojo.getAcno();
            }

            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                message = ibtRemote.cbsPostingCx(glAccount, 1, ymd.format(dmy.parse(todayDt)), totalAmount.doubleValue(), 0f, 2,
                        todayDt + " ACH Subsidy GL Entry", 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, uploadBrCode,
                        uploadBrCode, uploadingUserName, uploadingUserName, trsno, "", "");
                if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Npci Inward Head Entry");
                }
                message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0,
                        totalAmount.doubleValue(), "Y", "Y");
                if (!message.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Npci Inward Head Balance Updation.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Npci Inward Credit." + e.getMessage());
            }
            //End here
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

    //Generation of ACH Inward Cr Return Files.
    //ALPHA NUM--> Suffix Spaces, Num--> Trailing Zeroes.
    //Exception is AadhaarNo-will be treated as spaces.
    public String generateAchReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, Double seqNo, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String headerId = "", detailId = "", headerDestIIN = "", aadharLocation = "";
        String fileNo = "", totalRecordNo = "", bankCode = "", genFileName = "";
        String settlementCycle = "";
        try {
            ut.begin();
            if (processingMode.equalsIgnoreCase("H2H")) {
                List list = em.createNativeQuery("select * from cbs_npci_mapper_files where "
                        + "FILE_GEN_TYPE='CHI' AND DATE_OF_FILE_GEN='" + ymdSql.format(dmy.parse(fileGenDt)) + "' AND FILE_GEN_SEQN='" + seqNo + "'").getResultList();
//                Vector ele = (Vector) list.get(0);
                if (list.size() >= 1) {
                    throw new ApplicationException("ACH Return File already Generated...");
                }
            }

            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

            List dataList = em.createNativeQuery("select apbs_tran_code,bene_name,amount,ach_item_seq_no,"
                    + "ach_checksum,dest_bank_iin,dest_bank_acno,sponsor_bank_iin,user_no,user_credit_reference,"
                    + "ach_product_type,ifnull(ach_settlement_cycle,''),status,reason,ach_header_dest_iin from cbs_npci_inward "
                    + "where settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "trs_no=" + seqNo + " and iw_type='ACH'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Header Values.
            for (int h = 0; h < 1; h++) { //Only For First Iteration.
                Vector hVec = (Vector) dataList.get(0);
                headerDestIIN = hVec.get(14).toString();        //Make to desired length
                settlementCycle = hVec.get(11).toString();      //Make to desired length
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("ACH-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::ACH-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("ACH-DI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::ACH-DI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, Aadhar Location and Bank Code.");
            }
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim();    //As it is

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and file_gen_type='CHI'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();  //Make to 5 digit.
            }
            //userName changes
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
//                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                        + ymdOne.format(dmy.parse(fileGenDt)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";

                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";
            } else {
                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(dmy.parse(fileGenDt)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";
            }


            String insertnpciMapperQuery = "";
            int fileGenFlag = 0;
            if (processingMode.equalsIgnoreCase("H2H")) {
                insertnpciMapperQuery = "insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no,DATE_OF_FILE_GEN,FILE_GEN_SEQN) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','CHI'," + seqNo + ",'" + ymdSql.format(dmy.parse(fileGenDt)) + "','" + seqNo + "')";
                String updateFileGenFlagQuery = "UPDATE cbs_npci_inward SET file_gen_flag = 'Y' where TRS_NO='" + seqNo + "' "
                        + "and IW_TYPE='ACH' and SETTLEMENT_DATE='" + ymd.format(dmy.parse(fileGenDt)) + "'";
                fileGenFlag = em.createNativeQuery(updateFileGenFlagQuery).executeUpdate();
                if (fileGenFlag <= 0) {
                    throw new ApplicationException("Problem In Cbs Npci inward file_gen_flag updation Files Insertion.");
                }
            } else {
                insertnpciMapperQuery = "insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','CHI'," + seqNo + ")";
            }
            int n = em.createNativeQuery(insertnpciMapperQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }
            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to desired digit.
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (int i = 0; i < dataList.size(); i++) {
                ele = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(ele.get(2).toString().trim()).divide(new BigDecimal(100));
                totalSubAmt = totalSubAmt.add(individualAmt);
            }
            String amtInPaisa = "";
            totalSubAmt = totalSubAmt.multiply(new BigDecimal(100));
            int dotIndex = totalSubAmt.toString().indexOf(".");
            if (dotIndex == -1) {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().trim(), 13);
            } else {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().substring(0, dotIndex).trim(), 13);
            }
            //Header Preparation.
            FileWriter fw = null;
            String headerDt = "";
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
                headerDt = ymdOne.format(new Date());
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
                headerDt = ymdOne.format(dmy.parse(fileGenDt));
            }
            String header = headerId + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces("", 87)
                    + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addTrailingZeros(totalRecordNo, 9)
                    + amtInPaisa + headerDt + ParseFileUtil.addSuffixSpaces("", 27)
                    + ParseFileUtil.addSuffixSpaces(headerDestIIN, 11) + ParseFileUtil.addTrailingZeros(settlementCycle, 2)
                    + ParseFileUtil.addSuffixSpaces("", 132) + "." + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String apbsTranCodeDetail = element.get(0).toString();
                String benName = element.get(1).toString();
                String detailAmt = element.get(2).toString();
                String itemSeqNo = element.get(3).toString();
                String checkSum = element.get(4).toString();
                String destBankIfsc = element.get(5).toString();
                String benAcno = element.get(6).toString();
                String sponserBankIIN = element.get(7).toString();
                String userNumber = element.get(8).toString();
                String trfRefNo = element.get(9).toString();
                String productType = element.get(10).toString();
                String status = element.get(12).toString();
                String reason = element.get(13).toString();
                String reservedFlag = "1";  //For Success
                String reasonCode = "00";
                String customReasonDescOne = "";
                String customReasonDescTwo = "";

                if (status.equalsIgnoreCase("U")) {
                    reservedFlag = "0";     //For Un-Success
                    if (productType.equalsIgnoreCase("LPG") || productType.equalsIgnoreCase("PFM")
                            || productType.equalsIgnoreCase("DBT") || productType.equalsIgnoreCase("SYM")
                            || productType.equalsIgnoreCase("DBL") || productType.equalsIgnoreCase("KSN")) {
                        reasonCode = getAchReturnReasonCodeWitoutMiscellenious(reason);
                    } else {
                        reasonCode = getAchReturnReasonCode(reason);
                        if (reasonCode.equalsIgnoreCase("04")) {
                            String[] arr = makeAchMiscellaneousReturnDescription(reason);
                            customReasonDescOne = arr[0];
                            customReasonDescTwo = arr[1];
                        }
                    }
                }
                String individualStr = apbsTranCodeDetail + ParseFileUtil.addSuffixSpaces("", 9)
                        + ParseFileUtil.addTrailingZeros("", 2) + ParseFileUtil.addSuffixSpaces("", 3)
                        + ParseFileUtil.addSuffixSpaces(customReasonDescOne, 15) + ParseFileUtil.addSuffixSpaces(benName, 40)
                        + ParseFileUtil.addSuffixSpaces("", 8) + ParseFileUtil.addSuffixSpaces(customReasonDescTwo, 8)
                        + ParseFileUtil.addSuffixSpaces("", 20) + ParseFileUtil.addSuffixSpaces("", 13)
                        + detailAmt + ParseFileUtil.addTrailingZeros(itemSeqNo, 10)
                        + ParseFileUtil.addTrailingZeros(checkSum, 10) + ParseFileUtil.addSuffixSpaces("", 7)
                        + ParseFileUtil.addSuffixSpaces(destBankIfsc, 11) + ParseFileUtil.addSuffixSpaces(benAcno, 35)
                        + ParseFileUtil.addSuffixSpaces(sponserBankIIN, 11) + ParseFileUtil.addSuffixSpaces(userNumber, 18)
                        + ParseFileUtil.addSuffixSpaces(trfRefNo, 30) + ParseFileUtil.addSuffixSpaces(productType, 3)
                        + ParseFileUtil.addTrailingZeros("", 15) + ParseFileUtil.addSuffixSpaces("", 20) + reservedFlag
                        + reasonCode + "\n";

                fw.write(individualStr);
            }
            fw.close();
            //In case of H2H
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
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

    public String[] makeAchMiscellaneousReturnDescription(String reason) throws ApplicationException {
        String descOne = "";
        String descTwo = "";
        String[] arr = new String[2];
        try {
            reason = reason.replaceAll("[\\W_]", "").trim();
            if (reason.length() < 10) {
                reason = reason + " " + "Not Honour";
                reason = reason.trim();
            }
            //System.out.println("Reason Is-->" + reason);
            int reasonLength = reason.length();
            if (reasonLength < 15 || reasonLength == 15) {
                descOne = reason.trim();
                descTwo = "";
            } else if (reasonLength > 15 && reasonLength <= 23) {
                descOne = reason.substring(0, 15).trim();
                descTwo = reason.substring(15).trim();
            } else if (reasonLength > 23) {
                descOne = reason.substring(0, 15).trim();
                descTwo = reason.substring(15, 23).trim();
            }
            arr[0] = descOne;
            arr[1] = descTwo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }

//    public String getAchReturnReasonCode(String cbsMessage) throws ApplicationException {
//        String reasonCode = "";
//        try {
//            cbsMessage = cbsMessage.trim().toLowerCase();
//            if (cbsMessage.contains("account is closed")
//                    || cbsMessage.contains("account closed or transferred")) {
//                reasonCode = "01";
//            } else if (cbsMessage.contains("no such account")
//                    || cbsMessage.contains("account no does not exist")
//                    || cbsMessage.contains("account no. can not be blank or should be in proper state")) {
//                reasonCode = "02";
//            } else if (cbsMessage.contains("account holder name invalid")) {
//                reasonCode = "65";
//            } else if (cbsMessage.contains("a/c blocked or frozen")
//                    || cbsMessage.contains("frozen")) {
//                reasonCode = "68";
//            } else if (cbsMessage.contains("invalid account status")
//                    || cbsMessage.contains("operation stopped for this account")
//                    || cbsMessage.contains("transaction is not allowed for this type of account")) {
//                reasonCode = "70";
//            } else {
//                reasonCode = "04";
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return reasonCode;
//    }
    @Override
    public String getAchReturnReasonCode(String cbsMessage) throws ApplicationException {
        String reasonCode = "";
        try {
            cbsMessage = cbsMessage.trim().toLowerCase();
            if (cbsMessage.contains("account is closed")
                    || cbsMessage.contains("account closed or transferred")) {
                reasonCode = "01";
            } else if (cbsMessage.contains("no such account")
                    || cbsMessage.contains("account no does not exist")
                    || cbsMessage.contains("account no. can not be blank or should be in proper state")) {
                reasonCode = "02";
            } else if (cbsMessage.contains("inoperative")) {
                reasonCode = "53";
            } else if (cbsMessage.contains("account holder name invalid")) {
                reasonCode = "65";
            } else if (cbsMessage.contains("a/c blocked or frozen")
                    || cbsMessage.contains("frozen")
                    || cbsMessage.contains("operation stopped for this account")
                    || cbsMessage.contains("operation stopped")) {
                reasonCode = "68";
            } else if (cbsMessage.contains("invalid account status")
                    || cbsMessage.contains("transaction is not allowed for this type of account")) {
                reasonCode = "71";
            } else if (cbsMessage.contains("deaf")) {
                reasonCode = "10";
            } else {
                reasonCode = "04";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return reasonCode;
    }

//    public String getAchReturnReasonCodeWitoutMiscellenious(String cbsMessage) throws ApplicationException {
//        String reasonCode = "";
//        try {
//            cbsMessage = cbsMessage.trim().toLowerCase();
//            if (cbsMessage.contains("account is closed")
//                    || cbsMessage.contains("account closed or transferred")) {
//                reasonCode = "01";
//            } else if (cbsMessage.contains("no such account")
//                    || cbsMessage.contains("account no does not exist")
//                    || cbsMessage.contains("account no. can not be blank or should be in proper state")) {
//                reasonCode = "02";
//            } else if (cbsMessage.contains("ifsc code not found")
//                    || cbsMessage.contains("invalid ifsc/micr code")) {
//                reasonCode = "11";
//            } else if (cbsMessage.contains("account holder name invalid")) {
//                reasonCode = "65";
//            } else if (cbsMessage.contains("a/c blocked or frozen")
//                    || cbsMessage.contains("frozen")
//                    || cbsMessage.contains("account has been frozen")) {
//                reasonCode = "68";
//            } else if (cbsMessage.contains("invalid account status")
//                    || cbsMessage.contains("operation stopped for this account")
//                    || cbsMessage.contains("transaction is not allowed for this type of account")) {
//                reasonCode = "70";
//            } else {
//                reasonCode = "70";
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return reasonCode;
//    }
//    public String getAchReturnReasonCodeWitoutMiscellenious(String cbsMessage) throws ApplicationException {
//        String reasonCode = "";
//        try {
//            cbsMessage = cbsMessage.trim().toLowerCase();
//            if (cbsMessage.contains("account is closed")
//                    || cbsMessage.contains("account closed or transferred")) {
//                reasonCode = "01";
//            } else if (cbsMessage.contains("no such account")
//                    || cbsMessage.contains("account no does not exist")
//                    || cbsMessage.contains("account no. can not be blank or should be in proper state")) {
//                reasonCode = "02";
//            } else if (cbsMessage.contains("ifsc code not found")
//                    || cbsMessage.contains("invalid ifsc/micr code")) {
//                reasonCode = "11";
//            } else if (cbsMessage.contains("inoperative")) {
//                reasonCode = "53";
//            } else if (cbsMessage.contains("account holder name invalid")) {
//                reasonCode = "65";
//            } else if (cbsMessage.contains("a/c blocked or frozen")
//                    || cbsMessage.contains("account has been frozen")
//                    || cbsMessage.contains("frozen")
//                    || cbsMessage.contains("operation stopped for this account")
//                    || cbsMessage.contains("operation stopped")) {
//                reasonCode = "68";
//            } else if (cbsMessage.contains("invalid account status")
//                    || cbsMessage.contains("transaction is not allowed for this type of account")) {
//                reasonCode = "71";
//            } else if (cbsMessage.contains("deaf")) {
//                reasonCode = "10";
//            } else {
//                reasonCode = "71";
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return reasonCode;
//    }
    public String getAchReturnReasonCodeWitoutMiscellenious(String cbsMessage) throws ApplicationException {
        String reasonCode = "";
        try {
            cbsMessage = cbsMessage.trim().toLowerCase();
            if (cbsMessage.contains("account is closed")
                    || cbsMessage.contains("account closed or transferred")) {
                reasonCode = "01";
            } else if (cbsMessage.contains("no such account")
                    || cbsMessage.contains("account no does not exist")
                    || cbsMessage.contains("account no. can not be blank or should be in proper state")) {
                reasonCode = "02";
            } else if (cbsMessage.contains("inoperative")) {
                reasonCode = "53";
            } else if (cbsMessage.contains("account holder name invalid")) {
                reasonCode = "65";
            } else if (cbsMessage.contains("a/c blocked or frozen")
                    || cbsMessage.contains("account has been frozen")
                    || cbsMessage.contains("frozen")
                    || cbsMessage.contains("operation stopped for this account")
                    || cbsMessage.contains("operation stopped")) {
                reasonCode = "68";
            } else if (cbsMessage.contains("invalid account status")
                    || cbsMessage.contains("transaction is not allowed for this type of account")) {
                reasonCode = "71";
            } else if (cbsMessage.contains("deaf")) {
                reasonCode = "10";
            } else {
                reasonCode = "71";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return reasonCode;
    }

    public List<AdharRegistrationDetailPojo> getAdharRegistraionDetail(String brCode, String Adhar, String filter, String fromdate, String todate) throws ApplicationException {
        List dataList = new ArrayList<AdharRegistrationDetailPojo>();
        List resultList;
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                if (filter.equalsIgnoreCase("All")) {
                    resultList = em.createNativeQuery("SELECT A.CUST_ID,B.CustFullName,AADHAR_NO,A.LPG_ID,B.AADHAAR_LPG_ACNO,A.MANDATE_DATE,A.OD_DATE,A.ENTER_BY,date_format(A.DT,'%d/%m/%Y'),A.STATUS,A.REJECT_REASON,MAPPING_STATUS,CUSTID_BRNCODE FROM cbs_aadhar_registration A,cbs_customer_master_detail B "
                            + "WHERE A.CUST_ID=B.CUSTOMERID AND A.REG_TYPE='" + Adhar + "' /*AND A.CUST_ID IN "
                            + "(SELECT CUSTOMERID FROM cbs_customer_master_detail)GROUP BY B.AADHAAR_LPG_ACNO*/").getResultList();
                } else {
                    resultList = em.createNativeQuery("SELECT A.CUST_ID,B.CustFullName,AADHAR_NO,A.LPG_ID,B.AADHAAR_LPG_ACNO,A.MANDATE_DATE,A.OD_DATE,A.ENTER_BY,date_format(A.DT,'%d/%m/%Y'),A.STATUS,A.REJECT_REASON,MAPPING_STATUS,CUSTID_BRNCODE FROM cbs_aadhar_registration A,cbs_customer_master_detail B "
                            + "WHERE A.CUST_ID=B.CUSTOMERID AND A.REG_TYPE='" + Adhar + "' AND A.DT BETWEEN '" + fromdate + "' AND '" + todate + "' /*AND A.CUST_ID IN "
                            + "(SELECT CUSTOMERID FROM cbs_customer_master_detail)GROUP BY B.AADHAAR_LPG_ACNO*/").getResultList();
                }
            } else {
                if (filter.equalsIgnoreCase("All")) {
                    resultList = em.createNativeQuery("SELECT A.CUST_ID,B.CustFullName,AADHAR_NO,A.LPG_ID,B.AADHAAR_LPG_ACNO,A.MANDATE_DATE,A.OD_DATE,A.ENTER_BY,date_format(A.DT,'%d/%m/%Y'),A.STATUS,A.REJECT_REASON,MAPPING_STATUS,CUSTID_BRNCODE FROM cbs_aadhar_registration A,cbs_customer_master_detail B "
                            + "WHERE A.CUST_ID=B.CUSTOMERID AND A.CUSTID_BRNCODE='" + brCode + "' AND A.REG_TYPE='" + Adhar + "' /*AND A.CUST_ID IN "
                            + "(SELECT CUSTOMERID FROM cbs_customer_master_detail)GROUP BY B.AADHAAR_LPG_ACNO*/").getResultList();
                } else {
                    resultList = em.createNativeQuery("SELECT A.CUST_ID,B.CustFullName,AADHAR_NO,A.LPG_ID,B.AADHAAR_LPG_ACNO,A.MANDATE_DATE,A.OD_DATE,A.ENTER_BY,date_format(A.DT,'%d/%m/%Y'),A.STATUS,A.REJECT_REASON,MAPPING_STATUS,CUSTID_BRNCODE FROM cbs_aadhar_registration A,cbs_customer_master_detail B "
                            + "WHERE A.CUST_ID=B.CUSTOMERID AND A.CUSTID_BRNCODE='" + brCode + "' AND A.REG_TYPE='" + Adhar + "' AND A.DT BETWEEN '" + fromdate + "' AND '" + todate + "' /*AND A.CUST_ID IN "
                            + "(SELECT CUSTOMERID FROM cbs_customer_master_detail)GROUP BY B.AADHAAR_LPG_ACNO*/").getResultList();
                }
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    AdharRegistrationDetailPojo pojo = new AdharRegistrationDetailPojo();
                    Vector aadharVect = (Vector) resultList.get(i);
                    pojo.setCustId(aadharVect.get(0).toString());
                    pojo.setCustName(aadharVect.get(1).toString());
//                    pojo.setAadharNo(aadharVect.get(2).toString());
//                    pojo.setLpgId(aadharVect.get(3).toString());
                    if (Adhar.equalsIgnoreCase("AD")) {
                        pojo.setAadharNo(aadharVect.get(2).toString());
                    } else {
                        pojo.setAadharNo(aadharVect.get(3).toString());
                    }
                    pojo.setAcno(aadharVect.get(4).toString());
                    pojo.setManDate(aadharVect.get(5).toString());
                    pojo.setOdDate(aadharVect.get(6).toString());
                    pojo.setEnterBy(aadharVect.get(7).toString());
                    pojo.setDt(aadharVect.get(8).toString());

                    if (aadharVect.get(9).toString().equalsIgnoreCase("R")) {
                        pojo.setStatus("Sent To NPCI");
                    } else if (aadharVect.get(9).toString().equalsIgnoreCase("F")) {
                        pojo.setStatus("Entered");
                    } else if (aadharVect.get(9).toString().equalsIgnoreCase("I")) {
                        pojo.setStatus("Rejected");
                    } else if (aadharVect.get(9).toString().equalsIgnoreCase("E") && aadharVect.get(11).toString().equalsIgnoreCase("I")) {
                        pojo.setStatus("CBS Level Deactivated");
                    } else if (aadharVect.get(9).toString().equalsIgnoreCase("D") && aadharVect.get(11).toString().equalsIgnoreCase("I")) {
                        pojo.setStatus("NPCI Level Deactivated");
                    } else if (aadharVect.get(9).toString().equalsIgnoreCase("D") && aadharVect.get(11).toString().equalsIgnoreCase("M")) {
                        pojo.setStatus("Moved Out");
                    } else if (aadharVect.get(9).toString().equalsIgnoreCase("U") && aadharVect.get(11).toString().equalsIgnoreCase("I")) {
                        pojo.setStatus("AAdhar is update because A/c No. is Closed.");
                    } else {
                        pojo.setStatus("Updated");
                    }
                    if (aadharVect.get(9).toString().equalsIgnoreCase("I")) {
                        pojo.setReason(aadharVect.get(10).toString());
                    } else {
                        pojo.setReason("");
                    }
                    pojo.setBranch(reportRemote.getAlphacodeByBrncode(aadharVect.get(12).toString()));
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<AadharLpgStatusPojo> getAadharLpgStatus(String status, String aadharNo) throws ApplicationException {
        List<AadharLpgStatusPojo> dataList = new ArrayList<AadharLpgStatusPojo>();
        List result = new ArrayList();
        try {
            if (status.equalsIgnoreCase("C")) {
                result = em.createNativeQuery("select a.cust_id,b.custname,b.aadhaar_lpg_acno,a.custid_brncode,a.mandate_flag,a.mandate_date,a.reg_type,a.status,a.enter_by,date_format(a.dt,'%d/%m/%Y'),a.REJECT_REASON,MAPPING_STATUS "
                        + "from cbs_aadhar_registration a,cbs_customer_master_detail b where a.cust_id=b.customerid  "
                        + "and (a.aadhar_no='" + aadharNo + "'or a.lpg_id='" + aadharNo + "')").getResultList();
            } else {
                result = em.createNativeQuery("select a.cust_id,b.custname,b.aadhaar_lpg_acno,a.custid_brncode,a.mandate_flag,a.mandate_date,a.reg_type,a.status,a.enter_by,date_format(a.dt,'%d/%m/%Y'),a.REJECT_REASON,MAPPING_STATUS,a.update_by,date_format(a.update_dt,'%d/%m/%Y') "
                        + "from cbs_aadhar_registration_his a,cbs_customer_master_detail b where a.cust_id=b.customerid  "
                        + "and (a.aadhar_no='" + aadharNo + "'or a.lpg_id='" + aadharNo + "')").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    AadharLpgStatusPojo alsPojo = new AadharLpgStatusPojo();
                    Vector adharLpgVect = (Vector) result.get(i);
                    alsPojo.setCustId(adharLpgVect.get(0).toString());
                    alsPojo.setName(adharLpgVect.get(1).toString());
                    alsPojo.setAcNo(adharLpgVect.get(2).toString());
                    alsPojo.setBranch(reportRemote.getAlphacodeByBrncode(adharLpgVect.get(3).toString()));
                    alsPojo.setManDateFlg(adharLpgVect.get(4).toString());
                    alsPojo.setManDate(adharLpgVect.get(5).toString());
                    if (adharLpgVect.get(6).toString().equalsIgnoreCase("AD")) {
                        alsPojo.setRegType("Aadhar");
                    } else {
                        alsPojo.setRegType("Non Aadhar");
                    }
                    if (adharLpgVect.get(7).toString().equalsIgnoreCase("R")) {
                        alsPojo.setStatus("Sent To NPCI");
                    } else if (adharLpgVect.get(7).toString().equalsIgnoreCase("F")) {
                        alsPojo.setStatus("Entered");
                    } else if (adharLpgVect.get(7).toString().equalsIgnoreCase("I")) {
                        alsPojo.setStatus("Rejected");
                    } else if (adharLpgVect.get(7).toString().equalsIgnoreCase("E") && adharLpgVect.get(11).toString().equalsIgnoreCase("I")) {
                        alsPojo.setStatus("CBS Level Deactivated");
                    } else if (adharLpgVect.get(7).toString().equalsIgnoreCase("D") && adharLpgVect.get(11).toString().equalsIgnoreCase("I")) {
                        alsPojo.setStatus("NPCI Level Deactivated");
                    } else if (adharLpgVect.get(7).toString().equalsIgnoreCase("D") && adharLpgVect.get(11).toString().equalsIgnoreCase("M")) {
                        alsPojo.setStatus("Moved Out");
                    } else if (adharLpgVect.get(7).toString().equalsIgnoreCase("U") && adharLpgVect.get(11).toString().equalsIgnoreCase("I")) {
                        alsPojo.setStatus("AAdhar is update because A/c No. is Closed.");
                    } else {
                        alsPojo.setStatus("Updated");
                    }
                    alsPojo.setRegBy(adharLpgVect.get(8).toString());
                    alsPojo.setRegDate(adharLpgVect.get(9).toString());

                    if (status.equalsIgnoreCase("H")) {
                        alsPojo.setUpdateBy(adharLpgVect.get(12).toString());
                        alsPojo.setUpdateDt(adharLpgVect.get(13).toString());
                        if (adharLpgVect.get(7).toString().equalsIgnoreCase("I")) {
                            alsPojo.setReason(adharLpgVect.get(10).toString());
                        } else {
                            alsPojo.setReason("");
                        }
                    } else {
                        if (adharLpgVect.get(7).toString().equalsIgnoreCase("I")) {
                            alsPojo.setReason(adharLpgVect.get(10).toString());
                        } else {
                            alsPojo.setReason("");
                        }
                    }
                    dataList.add(alsPojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<AadharLpgStatusPojo> getAadharAndLpgCbsRegistration(String type, String idNo) throws ApplicationException {
        List<AadharLpgStatusPojo> dataList = new ArrayList<AadharLpgStatusPojo>();
        try {
            String typeQuery = "";
            if (type.equals("AD")) {
                typeQuery = " where aadhaar_no='" + idNo + "'";
            } else {
                typeQuery = " where lpg_id='" + idNo + "'";
            }

            List list = em.createNativeQuery("select customerid,ifnull(custname,'') as custname,"
                    + "ifnull(aadhaar_lpg_acno,'') mapAcno,ifnull(primarybrcode,'90') as brCode,"
                    + "ifnull(mandate_flag,'') as mandateFlag,ifnull(mandate_date,'') as mandate,"
                    + "ifnull(reg_type,'') as regtype,ifnull(lastchangeuserid,'') as lastUser,"
                    + "date_format(ifnull(lastchangetime,now()),'%d/%m/%Y') as lastChangeTime "
                    + "from cbs_customer_master_detail" + typeQuery).getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    AadharLpgStatusPojo pojo = new AadharLpgStatusPojo();
                    Vector ele = (Vector) list.get(i);
                    pojo.setCustId(ele.get(0).toString());
                    pojo.setName(ele.get(1).toString());
                    pojo.setAcNo(ele.get(2).toString());
                    pojo.setBranch(reportRemote.getAlphacodeByBrncode(ele.get(3).toString()));
                    pojo.setManDateFlg(ele.get(4).toString());
                    pojo.setManDate(ele.get(5).toString());
                    if (ele.get(6).toString().equalsIgnoreCase("AD")) {
                        pojo.setRegType("Aadhar");
                    } else if (ele.get(6).toString().equalsIgnoreCase("NA")) {
                        pojo.setRegType("Non Aadhar");
                    } else {
                        pojo.setRegType("");
                    }
                    pojo.setUpdateBy(ele.get(7).toString().toUpperCase());
                    pojo.setUpdateDt(ele.get(8).toString());

                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    //Uploading of Aadhar Registration Response.
    public String npciAadharResponseUpload(List<NpciInwardDto> inputList, String uploadingUserName,
            String todayDt, String uploadedFileName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);
                String aadharNo = dto.getBeneAadhaarNo();
                String reason = dto.getRetReasonCode();
                String accepted = dto.getTranRef();
                String reasonCode = dto.getReasonCode();

                if (!accepted.equalsIgnoreCase("true")) {
                    List list = em.createNativeQuery("select status,cust_id from cbs_aadhar_registration "
                            + "where aadhar_no='" + aadharNo + "' and reg_type='AD' and STATUS='R'").getResultList();
                    if (!list.isEmpty() && list.size() == 1) {
                        Vector ele = (Vector) list.get(0);
                        String custId = ele.get(1).toString();

                        int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
                                + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
                                + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
                                + "reg_type,reason_code,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) select cust_id,aadhar_no,"
                                + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                                + "enter_by,dt,tran_time,'" + uploadingUserName + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
                                + "dest_bank_acno,reg_type,reason_code,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin from "
                                + "cbs_aadhar_registration where cust_id='" + custId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In CBS Aadhar Registration History Insertion.");
                        }

                        n = em.createNativeQuery("update cbs_aadhar_registration set status='I',"
                                + "reject_reason='" + reason + "',res_update_by='" + uploadingUserName + "',"
                                + "res_update_dt='" + ymd.format(dmy.parse(todayDt)) + "',"
                                + "res_file_name='" + uploadedFileName + "',reason_code='" + reasonCode + "' where aadhar_no='" + aadharNo + "' and "
                                + "reg_type='AD'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("There is updation issue in aadhar response.");
                        }
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

    public String npciNonAadharResponseUpload(List<NpciInwardDto> inputList, String uploadingUserName,
            String todayDt, String uploadedFileName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);
                String lpgId = dto.getLpgId();
                System.out.println("LpgId Is-->" + lpgId);
                String acValidFlag = dto.getAcValidFlag();
                System.out.println("AcValidFlag-->" + acValidFlag);

                List list = em.createNativeQuery("select status,cust_id from cbs_aadhar_registration where "
                        + "lpg_id='" + lpgId + "' and reg_type='NA'").getResultList();
                if (!list.isEmpty() && list.size() == 1) {
                    if (!acValidFlag.equalsIgnoreCase("00")) {  //'00' is for success.
                        String reason = NpciReturnConstant.getValue(acValidFlag) == null ? ""
                                : NpciReturnConstant.getValue(acValidFlag);

                        Vector ele = (Vector) list.get(0);
                        String custId = ele.get(1).toString();

                        int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
                                + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
                                + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
                                + "reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) select cust_id,aadhar_no,"
                                + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                                + "enter_by,dt,tran_time,'" + uploadingUserName + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
                                + "dest_bank_acno,reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin from "
                                + "cbs_aadhar_registration where cust_id='" + custId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In CBS Aadhar Registration History Insertion.");
                        }

                        n = em.createNativeQuery("update cbs_aadhar_registration set status='I',"
                                + "reject_reason='" + reason + "',res_update_by='" + uploadingUserName + "',"
                                + "res_update_dt='" + ymd.format(dmy.parse(todayDt)) + "',"
                                + "res_file_name='" + uploadedFileName + "' where lpg_id='" + lpgId + "' and "
                                + "reg_type='NA'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("There is updation issue in aadhar response.");
                        }
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

    public String getParameterInfoReportValue(String reportName) throws ApplicationException {
        String value = "";
        try {
            ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName(reportName);
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for-->" + reportName);
            }
            value = paramEntity.getCode().toString().trim();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return value;
    }

    //A/c Closed And Aadhar Moved Out Case.
    @Override
    public String aadharDeactivation(String custId, String aadharNo, String deActivationType,
            String mappingStatus, String regType, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(em);
        try {
            ut.begin();
            //DO NOT DELETE THIS COMMENT
            //deActivationType- mo(Moved Out), ca-(Closed A/c), ur-(Response Updation), mr(Make registered)
            //deActivationType- mu(make in update mode so that mapper can be generated)
            int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
                    + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
                    + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
                    + "reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) select cust_id,aadhar_no,"
                    + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                    + "enter_by,dt,tran_time,'" + enterBy + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
                    + "dest_bank_acno,reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin from "
                    + "cbs_aadhar_registration where cust_id='" + custId + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Aadhar History Creation In aadharDeactivation() Method.");
            }
            if (deActivationType.equalsIgnoreCase("mo")) {
                List list1 = em.createNativeQuery("select * from cbs_aadhar_registration where "
                        + "aadhar_no='" + aadharNo + "'and status='D'and mapping_status='M'").getResultList();
                if (!list1.isEmpty()) {
                    throw new ApplicationException("This aadhar is already moved out");
                }
                //Moved Out- That means aadhar will be De-Activate at only CBS level.
                n = em.createNativeQuery("update cbs_aadhar_registration set status='D',"
                        + "mapping_status='" + mappingStatus + "',enter_by='" + enterBy + "',"
                        + "dt='" + ymd.format(curDt) + "',tran_time=now() where "
                        + "cust_id='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Moved Out Updation.");
                }

                n = em.createNativeQuery("insert into cbs_customer_master_detail_his(customerid,title,custname,shortname,gender,maritalstatus,"
                        + "fathername,mothername,staffflag,staffid,minorflag,nriflag,UINCardNo,communicationPreference,employerid,employeeNo,"
                        + "mobilenumber,CustStatus,issuingAuthority,Expirydate,PlaceOfissue,PreferredLanguage,chgTurnOver,PurgeAllowed,"
                        + "AccountManager,AllowSweeps,TradeFinanceFlag,SwiftCodeStatus,SwiftCode,BCBFID,CombinedStmtFlag,StmtFreqType,StmtFreqWeekNo,StmtFreqWeekDay,"
                        + "StmtFreqStartDate,StmtFreqNP,IntroCustomerId,CustTitle,name,AddressLine1,AddressLine2,village,block,CityCode,StateCode,PostalCode,CountryCode,"
                        + "PhoneNumber,TelexNumber,FaxNumber,salary,ChargeStatus,ChargeLevelCode,ABBChargeCode,PaperRemittance,DeliveryChannelChargeCode,"
                        + "AccountLevelCharges,CustLevelCharges,TaxSlab,TDSCode,TDSCustomerId,TDSExemptionReferenceNo,ExemptionRemarks,"
                        + "ITFileNo,TDSFloorLimit,CustFinancialDetails,FinancialYearAndMonth,CurrencyCodeType,PropertyAssets,BusinessAssets,Investments,NetWorth,Deposits,"
                        + "OtherBankCode,LimitsWithOtherBank,FundBasedLimit,NonFundBasedLimit,OfflineCustDebitLimit,CustSalary,CustFinancialDate,PAN_GIRNumber,TINNumber,"
                        + "SalesTaxNo,ExciseNo,VoterIDNo,DrivingLicenseNo,CreditCard,CardNumber,CardIssuer,Bankname,AcctId,BranchName,PerAddressLine1,PerAddressLine2,"
                        + "PerVillage,PerBlock,PerCityCode,PerStateCode,PerPostalCode,PerCountryCode,PerPhoneNumber,PerTelexNumber,PerFaxNumber,MailAddressLine1,MailAddressLine2,"
                        + "MailVillage,MailBlock,MailCityCode,MailStateCode,MailPostalCode,MAilCountryCode,MAilPhoneNumber,MailTelexNumber,MailfaxNumber,EmpAddressLine1,EmpAddressLine2,"
                        + "EmpVillage,EmpBlock,EmpCityCode,EmpStateCode,EmpPostalCode,EmpCountryCode,EmpPhoneNumber,EmpTelexNumber,EmpFaxNumber,EmailID,OperationalRiskRating,"
                        + "CreditRiskRatingInternal,ExternalRatingShortTerm,ExternalRatingLongTerm,"
                        + "ThresoldTransactionLimit,SuspensionFlg,PrimaryBrCode,LastUpdatedBr,FirstAccountDate,Auth,LastChangeUserID,"
                        + "LastChangeTime,RecordCreaterID,AADHAAR_NO,LPG_ID,AADHAAR_LPG_ACNO,MANDATE_FLAG,MANDATE_DATE,REG_TYPE,middle_name,last_name,Spouse_name,"
                        + "maiden_name,nrega_job_card,dl_expiry,legal_document,income_range,networth_as_on,qualification,Political_exposed,juri_add1,juri_add2,juri_city,"
                        + "juri_state,juri_postal,juri_country,tan,cin,per_email,mail_email,nationality,other_identity,poa,AcHolderTypeFlag,AcHolderType,AcType,CKYCNo,FatherMiddleName,"
                        + "FatherLastName,SpouseMiddleName,SpouseLastName,MotherMiddleName,MotherLastName, TinIssuingCountry,CustEntityType,IdentityNo,IdExpiryDate,PerAddType,PerMailAddSameFlagIndicate,MailAddType,MailPoa,"
                        + "JuriAddBasedOnFlag,JuriAddType,JuriPoa,PerDistrict,MailDistrict,EmpDistrict,JuriDistrict,PerOtherPOA,MailOtherPOA,JuriOtherPOA,father_spouse_flag,"
                        + "isd_code,CustImage,CustFullName,gstIdentificationNumber,aadhaar_mode,aadhaar_bank_iin)"
                        + "select customerid,title,custname,shortname,gender,maritalstatus,"
                        + "fathername,mothername,staffflag,staffid,minorflag,nriflag,UINCardNo,communicationPreference,employerid,employeeNo,"
                        + "mobilenumber,CustStatus,issuingAuthority,date_format(ifnull(Expirydate,'1900-01-01'),'%d/%m/%Y'),PlaceOfissue,PreferredLanguage,chgTurnOver,PurgeAllowed,"
                        + "AccountManager,AllowSweeps,TradeFinanceFlag,SwiftCodeStatus,SwiftCode,BCBFID,CombinedStmtFlag,StmtFreqType,StmtFreqWeekNo,StmtFreqWeekDay,"
                        + "StmtFreqStartDate,StmtFreqNP,IntroCustomerId,CustTitle,name,AddressLine1,AddressLine2,village,block,CityCode,StateCode,PostalCode,CountryCode,"
                        + "PhoneNumber,TelexNumber,FaxNumber,salary,ChargeStatus,ChargeLevelCode,ABBChargeCode,PaperRemittance,DeliveryChannelChargeCode,"
                        + "AccountLevelCharges,CustLevelCharges,TaxSlab,TDSCode,TDSCustomerId,TDSExemptionReferenceNo,ExemptionRemarks,"
                        + "ITFileNo,TDSFloorLimit,CustFinancialDetails,FinancialYearAndMonth,CurrencyCodeType,PropertyAssets,BusinessAssets,Investments,NetWorth,Deposits,"
                        + "OtherBankCode,LimitsWithOtherBank,FundBasedLimit,NonFundBasedLimit,OfflineCustDebitLimit,CustSalary,CustFinancialDate,PAN_GIRNumber,TINNumber,"
                        + "SalesTaxNo,ExciseNo,VoterIDNo,DrivingLicenseNo,CreditCard,CardNumber,CardIssuer,Bankname,AcctId,BranchName,PerAddressLine1,PerAddressLine2,"
                        + "PerVillage,PerBlock,PerCityCode,PerStateCode,PerPostalCode,PerCountryCode,PerPhoneNumber,PerTelexNumber,PerFaxNumber,MailAddressLine1,MailAddressLine2,"
                        + "MailVillage,MailBlock,MailCityCode,MailStateCode,MailPostalCode,MAilCountryCode,MAilPhoneNumber,MailTelexNumber,MailfaxNumber,EmpAddressLine1,EmpAddressLine2,"
                        + "EmpVillage,EmpBlock,EmpCityCode,EmpStateCode,EmpPostalCode,EmpCountryCode,EmpPhoneNumber,EmpTelexNumber,EmpFaxNumber,EmailID,OperationalRiskRating,"
                        + "CreditRiskRatingInternal,ExternalRatingShortTerm,ExternalRatingLongTerm,"
                        + "ThresoldTransactionLimit,SuspensionFlg,PrimaryBrCode,LastUpdatedBr,FirstAccountDate,Auth,LastChangeUserID,"
                        + "LastChangeTime,RecordCreaterID,AADHAAR_NO,LPG_ID,AADHAAR_LPG_ACNO,MANDATE_FLAG,MANDATE_DATE,REG_TYPE,middle_name,last_name,Spouse_name,"
                        + "maiden_name,nrega_job_card,dl_expiry,legal_document,income_range,networth_as_on,qualification,Political_exposed,juri_add1,juri_add2,juri_city,"
                        + "juri_state,juri_postal,juri_country,tan,cin,per_email,mail_email,nationality,other_identity,poa,AcHolderTypeFlag,AcHolderType,AcType,CKYCNo,FatherMiddleName,"
                        + "FatherLastName,SpouseMiddleName,SpouseLastName,MotherMiddleName,MotherLastName, TinIssuingCountry,CustEntityType,IdentityNo,IdExpiryDate,PerAddType,PerMailAddSameFlagIndicate,MailAddType,MailPoa,"
                        + "JuriAddBasedOnFlag,JuriAddType,JuriPoa,PerDistrict,MailDistrict,EmpDistrict,JuriDistrict,PerOtherPOA,MailOtherPOA,JuriOtherPOA,father_spouse_flag,"
                        + "isd_code,CustImage,CustFullName,gstIdentificationNumber,aadhaar_mode,aadhaar_bank_iin from cbs_customer_master_detail  where customerid='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("problem ib customerDetail history creation .");
                }

//                n = em.createNativeQuery("Update cbs_customer_master_detail set AADHAAR_NO='',LPG_ID='',AADHAAR_LPG_ACNO='',"
//                        + "MANDATE_FLAG='',REG_TYPE='',MANDATE_DATE='',aadhaar_mode='',aadhaar_bank_iin='',LastChangeUserID='" + enterBy + "',LastChangeTime=now() "
//                        + "where customerid='" + custId + "'").executeUpdate();
//                if (n <= 0) {
//                    throw new ApplicationException("problem in custmor master detail updation");
//                }

                CBSCustomerMasterDetailTO to = customerRemote.getCustDetailsByCustId(custId);
                to.setAdhaarNo("");
                to.setLpgId("");
                to.setAdhaarLpgAcno("");
                to.setMandateFlag("");
                to.setRegType("0");
                to.setMandateDt("");
                to.setAadhaarMode("");
                to.setAadhaarBankIin("");
                masterDetailDAO.merge(ObjectAdaptorCustomer.adaptToCBSCustomerMasterDtlEntity(to));
            } else if (deActivationType.equalsIgnoreCase("ca") || deActivationType.equalsIgnoreCase("mi")) {
                n = em.createNativeQuery("update cbs_aadhar_registration set status='U',"
                        + "rrn='" + ymdms.format(new Date()) + "',mapping_status='" + mappingStatus + "',"
                        + "enter_by='" + enterBy + "',dt='" + ymd.format(curDt) + "',tran_time=now() "
                        + "where cust_id='" + custId + "' and aadhar_no='" + aadharNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Closed A/c Updation.");
                }
            } else if (deActivationType.equalsIgnoreCase("ur")) {
                n = em.createNativeQuery("update cbs_aadhar_registration set status='D',"
                        + "enter_by='" + enterBy + "',dt='" + ymd.format(curDt) + "',"
                        + "tran_time=now() where cust_id='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Moved Out Updation.");
                }
            } else if (deActivationType.equalsIgnoreCase("mr")) {
                n = em.createNativeQuery("update cbs_aadhar_registration set status='R',"
                        + "mapping_status='',enter_by='" + enterBy + "',"
                        + "dt='" + ymd.format(curDt) + "',tran_time=now() where "
                        + "cust_id='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Make Registration.");
                }
            } else if (deActivationType.equalsIgnoreCase("mu")) {
                n = em.createNativeQuery("update cbs_aadhar_registration set status='U',"
                        + "mapping_status='',enter_by='" + enterBy + "',"
                        + "dt='" + ymd.format(curDt) + "',tran_time=now() where "
                        + "cust_id='" + custId + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Make Registration.");
                }
            }

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List getNpciusername(String userName) throws ApplicationException {
        List userList = new ArrayList();
        try {
            userList = em.createNativeQuery("select npciusername from securityinfo where userid='" + userName + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return userList;
    }

    public String aadharLookUpFileGeneration(String brCode, String type, String filter,
            String frDt, String toDt, String dirName, String npciBankCode, String genBrCode,
            String genUser) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String genFileName = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + genUser + "'").getResultList();
            Vector elem = (Vector) list.get(0);
            if (list.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = elem.get(0).toString().trim();

            List<AdharRegistrationDetailPojo> dataList = getAdharRegistraionDetail(brCode, "AD", filter, frDt, toDt);
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist for file generation.");
            }

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(new Date()) + "' and file_gen_type='AL'").getResultList();
            elem = (Vector) list.get(0);
            String fileNo = "1";
            if (elem.get(0) != null) {
                fileNo = elem.get(0).toString().trim();
            }

            genFileName = "UID-ST-" + npciBankCode + "-" + npciUserName + "-"
                    + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(new Date()) + "','" + genFileName + "','" + genUser + "',now(),'"
                    + genBrCode + "','AL')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }
            FileWriter fw = new FileWriter(dirName + genFileName);
            for (int i = 0; i < dataList.size(); i++) {
                AdharRegistrationDetailPojo ele = dataList.get(i);
                String aadharNo = ele.getAadharNo().trim();
                fw.write(aadharNo + "\n");
            }
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return genFileName;
    }

    public List<NpciFileDto> showAadharLookUpFiles(String fileType, String fileGenDt) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + fileGenDt + "' and "
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

    //Old One Code
    public String npciEcsCreditInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode, String todayDt,
            String uploadingUserName) throws ApplicationException {
        String message = "";
        BigDecimal totalAmount = new BigDecimal("0");
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        CbsNpciInwardDAO cbsNpciInwardDAO = new CbsNpciInwardDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            Float trsno = ftsRemote.getTrsNo();
            for (int i = 0; i < inputList.size(); i++) {
                message = "unsuccess";
                NpciInwardDto dto = inputList.get(i);
                List<CbsNpciInward> isEntity = cbsNpciInwardDAO.findByUserCreditReferenceAndIwType(dto.getUserCreditRef().trim(),
                        "ECS");
                if (!isEntity.isEmpty()) {
                    message = "duplicate";
                }
                CbsNpciInward object = new CbsNpciInward();

                object.setApbsTranCode(dto.getApbsTranCode().trim());
                object.setDestBankIin(dto.getDestBankIIN().trim());
                object.setDestActype(dto.getDestAcType().trim());
                object.setLedgerNo(dto.getLedgerNo().trim());
                object.setBeneAadhaarNo("");

                object.setBeneName(dto.getBeneName().trim());
                object.setSponsorBankIin(dto.getSponsorBankIIN().trim());
                object.setUserNo(dto.getUserNumber().trim());
                object.setUserNameNarration(dto.getUserName().trim());
                object.setUserCreditReference(dto.getUserCreditRef().trim());

                object.setAmount(dto.getAmount().trim());
                object.setReservedOne(dto.getReservedOne());
                object.setReservedTwo(dto.getReservedTwo());
                object.setReservedThree(dto.getReservedThree());
                object.setDestBankAcno(dto.getDestBankAcno().trim());

                object.setReturnReasonCode("");
                Date txnDt = dmy.parse(todayDt);
                object.setTranDate(txnDt);
                object.setTranTime(new Date());
                object.setValueDate(txnDt);
                object.setAchItemSeqNo(dto.getItemSeqNo().trim());

                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());
                object.setTrsNo(trsno.doubleValue());
                object.setSettlementDate(ymd.parse(dto.getSettlementDt()));
                object.setIwType("ECS");
                object.setAchChecksum("");
                object.setAchProductType("");
                object.setAchHeaderDestIin(dto.getHeaderDestIIN().trim());
                object.setCbsAcno("");
                object.setCbsAcname("");

                if (!message.equals("duplicate")) {
                    try {
                        //Account No Validation.
                        List list = em.createNativeQuery("select accstatus,custname,ifnull(jtname1,'') as jtname1,"
                                + "ifnull(jtname2,'') as jtname2,ifnull(jtname3,'') as jtname3,ifnull(jtname4,'') as "
                                + "jtname4 from accountmaster where "
                                + "acno='" + dto.getDestBankAcno().trim() + "'").getResultList();
                        if (list == null || list.isEmpty()) {
                            object.setStatus("U");
                            object.setReason("No Such Account");
                        } else {
                            Vector ele = (Vector) list.get(0);
                            String accStatus = ele.get(0).toString();
                            //Ifsc Code,IIN and Micr Validation.
                            list = em.createNativeQuery("select ifnull(ifsc_code,'') from  branchmaster "
                                    + "where brncode = " + Integer.parseInt(dto.getDestBankAcno().
                                    trim().substring(0, 2)) + "").getResultList();
                            ele = (Vector) list.get(0);
                            String ifscCode = ele.get(0).toString().trim();

                            list = em.createNativeQuery("select ifnull(iin,'') as iin from "
                                    + "mb_sms_sender_bank_detail").getResultList();
                            ele = (Vector) list.get(0);
                            String iin = ele.get(0).toString().trim();

                            String micrNo = "";
//                            list = em.createNativeQuery("select ifnull(concat(micr,micrcode,branchcode),'') as micrno "
//                                    + "from bnkadd where alphacode in(select alphacode from branchmaster "
//                                    + "where brncode=" + Integer.parseInt(dto.getDestBankAcno().trim().substring(0, 2)) + ")").getResultList();
//                            if (!list.isEmpty()) {
//                                ele = (Vector) list.get(0);
//                                micrNo = ele.get(0).toString().trim();
//                            }

                            list = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                                    + "bank_code,ifnull(b.branchcode,'') as brach_code from bnkadd b,branchmaster m where "
                                    + "b.alphacode=m.alphacode and "
                                    + "m.brncode=" + Integer.parseInt(dto.getDestBankAcno().trim().substring(0, 2)) + "").getResultList();
                            if (!list.isEmpty()) {
                                ele = (Vector) list.get(0);
                                String cityCode = ele.get(0).toString().trim();
                                String bankCode = ele.get(1).toString().trim();
                                String branchCode = ele.get(2).toString().trim();

                                micrNo = ParseFileUtil.addTrailingZeros(cityCode, 3) + ParseFileUtil.addTrailingZeros(bankCode, 3) + ParseFileUtil.addTrailingZeros(branchCode, 3);
                            }

                            if (!(dto.getDestBankIIN().trim().equalsIgnoreCase(ifscCode)
                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(iin)
                                    || dto.getDestBankIIN().trim().equalsIgnoreCase(micrNo))) {
                                object.setStatus("U");
                                object.setReason("Ifsc Code Not Found");
                            } else {
                                if (accStatus.equals("9")) {
                                    object.setStatus("U");
                                    object.setReason("Account Closed or Transferred");
                                } else {
                                    String subsidyAc = dto.getDestBankAcno().trim();
                                    Double subsidyAmt = Double.parseDouble(formatter.format(new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal("100")).doubleValue()));
                                    //Subsidy Processing
                                    message = ibtRemote.cbsPostingSx(subsidyAc, 0, ymd.format(txnDt),
                                            subsidyAmt, 0f, 2, "NPCI-ECS-Cr_" + dmy.format(txnDt) + "_Entry", 0f, "A", "", "", 3, 0f,
                                            ftsRemote.getRecNo(), 66, subsidyAc.substring(0, 2), uploadBrCode,
                                            uploadingUserName, uploadingUserName, trsno, "", "");
                                    if (message.substring(0, 4).equalsIgnoreCase("true")) {
                                        object.setStatus("S");
                                        object.setReason("");

                                        totalAmount = totalAmount.add(new BigDecimal(subsidyAmt));
                                        //Adding Object For Sms
                                        SmsToBatchTo to = new SmsToBatchTo();
                                        to.setAcNo(subsidyAc);
                                        to.setCrAmt(subsidyAmt);
                                        to.setDrAmt(0d);
                                        to.setTranType(2);
                                        to.setTy(0);
                                        to.setTxnDt(todayDt);
                                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                                        smsBatchList.add(to);
                                        //End
                                    } else {
                                        object.setStatus("U");
                                        object.setReason(message);
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        object.setStatus("U");
                        object.setReason(ex.getMessage());
                    }
                } else {
                    object.setStatus("U");
                    object.setReason("This User Credit Reference Is Already Processed");
                }
                cbsNpciInwardDAO.save(object);
            }
            //Performing General Ledger Transaction of Debit
            String glAccount = "";
            ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(em);

            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("APB-ACH-HEAD");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 1) {
                throw new ApplicationException("Please define code for::APB-ACH-HEAD");
            }
            if (!(paramEntity.getCode() == 0 || paramEntity.getCode() == 1)) {
                throw new ApplicationException("Please define proper code for APB-ACH-HEAD");
            }
            String apbAchHead = paramEntity.getCode().toString().trim();

            List<AbbParameterInfo> abbParameterInfoList = null;
            if (apbAchHead.equals("1")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NPCI-INW-CR");
            } else if (apbAchHead.equals("0")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ECS-INW-CR");
            }
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("Please define proper head for NPCI.");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                glAccount = uploadBrCode + abbPojo.getAcno();
            }

            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                message = ibtRemote.cbsPostingCx(glAccount, 1, ymd.format(dmy.parse(todayDt)), totalAmount.doubleValue(), 0f, 2,
                        "NPCI-ECS-Cr_" + todayDt + "_Entry", 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, uploadBrCode,
                        uploadBrCode, uploadingUserName, uploadingUserName, trsno, "", "");
                if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Npci Inward Head Entry");
                }
                message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0,
                        totalAmount.doubleValue(), "Y", "Y");
                if (!message.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Npci Inward Head Balance Updation.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Npci Inward Credit." + e.getMessage());
            }
            //End here
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

    public String npciCECSCreditInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode, String todayDt,
            String uploadingUserName, String processingMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) list.get(0);
            if (list.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            NpciInwardDto dto = inputList.get(0);
            String settlementDt = dto.getSettlementDt().trim();
            String achItemSeqNo = dto.getItemSeqNo().trim();

            list = em.createNativeQuery("select dest_bank_acno from cbs_npci_inward "
                    + "where settlement_date='" + settlementDt + "' and "
                    + "ach_item_seq_no='" + achItemSeqNo + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            for (int i = 0; i < inputList.size(); i++) {
                dto = inputList.get(i);
                String mappedAccNo = "";
                if (dto.getDestBankAcno().trim().length() == 15) {
                    list = em.createNativeQuery("select coalesce(NEW_AC_NO,'') from cbs_acno_mapping where OLD_AC_NO='" + dto.getDestBankAcno().trim() + "'").getResultList();
                    if (list.isEmpty()) {
                        System.out.println("Account mapping not exist.");
                    } else {
                        mappedAccNo = ((Vector) list.get(0)).get(0).toString();
                    }
                }
                String query = "";
                if (processingMode.equalsIgnoreCase("H2H")) {
                    query = "insert into cbs_npci_inward(apbs_tran_code,dest_bank_iin,"
                            + "dest_actype,ledger_no,bene_aadhaar_no,bene_name,sponsor_bank_iin,user_no,"
                            + "user_name_narration,user_credit_reference,amount,reserved_one,reserved_two,"
                            + "reserved_three,dest_bank_acno,return_reason_code,status,reason,tran_date,"
                            + "tran_time,value_date,enter_by,auth_by,trs_no,settlement_date,iw_type,"
                            + "ach_item_seq_no,ach_header_dest_iin,cbs_acno,cbs_acname,ach_checksum,ach_product_type,"
                            + "ach_settlement_cycle,ach_control_2nd,ach_control_5th,ach_control_7th,ach_control_8th,"
                            + "ach_control_10th,ach_filler,ach_umrn,ach_reserved_flag,ach_reserved_reason,MAPPED_CBS_ACNO,"
                            + "FILE_GEN_FLAG) "
                            + "values('" + dto.getApbsTranCode().trim() + "','" + dto.getDestBankIIN().trim() + "',"
                            + "'" + dto.getDestAcType().trim() + "','" + dto.getLedgerNo().trim() + "','',"
                            + "'" + dto.getBeneName().trim() + "','" + dto.getSponsorBankIIN().trim() + "',"
                            + "'" + dto.getUserNumber().trim() + "','" + dto.getUserName().trim() + "',"
                            + "'" + dto.getUserCreditRef().trim() + "','" + dto.getAmount().trim() + "',"
                            + "'" + dto.getReservedOne() + "','" + dto.getReservedTwo() + "',"
                            + "'" + dto.getReservedThree() + "','" + dto.getDestBankAcno().trim() + "',"
                            + "'','','','" + ymd.format(dmy.parse(todayDt)) + "',now(),"
                            + "'" + ymd.format(dmy.parse(todayDt)) + "','" + uploadingUserName.trim() + "',"
                            + "'" + uploadingUserName.trim() + "',0,'" + dto.getSettlementDt() + "','ECS',"
                            + "'" + dto.getItemSeqNo().trim() + "',"
                            + "'" + dto.getHeaderDestIIN().trim() + "','','','" + dto.getCheckSum().trim() + "',"
                            + "'" + dto.getProductType().trim() + "','" + dto.getSettlementCycle().trim() + "',"
                            + "'" + dto.getControll2nd().trim() + "','" + dto.getControll5th().trim() + "',"
                            + "'" + dto.getControll7th().trim() + "','" + dto.getControll8th().trim() + "',"
                            + "'" + dto.getControll10th().trim() + "','" + dto.getAchFiller().trim() + "',"
                            + "'" + dto.getUmrn().trim() + "','" + dto.getReservedFlag().trim() + "',"
                            + "'" + dto.getReservedReason().trim() + "','" + mappedAccNo + "','N')";
                } else {
                    query = "insert into cbs_npci_inward(apbs_tran_code,dest_bank_iin,"
                            + "dest_actype,ledger_no,bene_aadhaar_no,bene_name,sponsor_bank_iin,user_no,"
                            + "user_name_narration,user_credit_reference,amount,reserved_one,reserved_two,"
                            + "reserved_three,dest_bank_acno,return_reason_code,status,reason,tran_date,"
                            + "tran_time,value_date,enter_by,auth_by,trs_no,settlement_date,iw_type,"
                            + "ach_item_seq_no,ach_header_dest_iin,cbs_acno,cbs_acname,ach_checksum,ach_product_type,"
                            + "ach_settlement_cycle,ach_control_2nd,ach_control_5th,ach_control_7th,ach_control_8th,"
                            + "ach_control_10th,ach_filler,ach_umrn,ach_reserved_flag,ach_reserved_reason,MAPPED_CBS_ACNO) "
                            + "values('" + dto.getApbsTranCode().trim() + "','" + dto.getDestBankIIN().trim() + "',"
                            + "'" + dto.getDestAcType().trim() + "','" + dto.getLedgerNo().trim() + "','',"
                            + "'" + dto.getBeneName().trim() + "','" + dto.getSponsorBankIIN().trim() + "',"
                            + "'" + dto.getUserNumber().trim() + "','" + dto.getUserName().trim() + "',"
                            + "'" + dto.getUserCreditRef().trim() + "','" + dto.getAmount().trim() + "',"
                            + "'" + dto.getReservedOne() + "','" + dto.getReservedTwo() + "',"
                            + "'" + dto.getReservedThree() + "','" + dto.getDestBankAcno().trim() + "',"
                            + "'','','','" + ymd.format(dmy.parse(todayDt)) + "',now(),"
                            + "'" + ymd.format(dmy.parse(todayDt)) + "','" + uploadingUserName.trim() + "',"
                            + "'" + uploadingUserName.trim() + "',0,'" + dto.getSettlementDt() + "','ECS',"
                            + "'" + dto.getItemSeqNo().trim() + "',"
                            + "'" + dto.getHeaderDestIIN().trim() + "','','','" + dto.getCheckSum().trim() + "',"
                            + "'" + dto.getProductType().trim() + "','" + dto.getSettlementCycle().trim() + "',"
                            + "'" + dto.getControll2nd().trim() + "','" + dto.getControll5th().trim() + "',"
                            + "'" + dto.getControll7th().trim() + "','" + dto.getControll8th().trim() + "',"
                            + "'" + dto.getControll10th().trim() + "','" + dto.getAchFiller().trim() + "',"
                            + "'" + dto.getUmrn().trim() + "','" + dto.getReservedFlag().trim() + "',"
                            + "'" + dto.getReservedReason().trim() + "','" + mappedAccNo + "')";
                }
                int n = em.createNativeQuery(query).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in file uploading !");
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

    //CECS credit inward return.
    public String generateECSReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String headerId = "", detailId = "", aadharLocation = "", headerDestIIN = "";
        String fileNo = "", totalRecordNo = "", bankCode = "", genFileName = "", settlementCycle = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

            list = em.createNativeQuery("select dest_bank_iin from cbs_npci_inward where iw_type='ECS' and "
                    + "settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and ach_item_seq_no='" + seqNo + "' and status=''").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("Total entries were not verified for: "
                        + "Settlement Date->" + fileGenDt + " And Seq No-->" + seqNo);
            }

//            List dataList = em.createNativeQuery("select apbs_tran_code,dest_bank_iin,dest_actype,ledger_no,dest_bank_acno,bene_name,"
//                    + "sponsor_bank_iin,user_no,user_name_narration,user_credit_reference,amount,reserved_one,reserved_two,reserved_three,"
//                    + "ach_header_dest_iin,settlement_date,status,reason,ach_item_seq_no from cbs_npci_inward "
//                    + "where settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
//                    + "trs_no=" + seqNo + " and iw_type='ECS'").getResultList();
            List dataList = em.createNativeQuery("select apbs_tran_code,dest_bank_iin,dest_actype,ledger_no,dest_bank_acno,bene_name,"
                    + "sponsor_bank_iin,user_no,user_name_narration,user_credit_reference,amount,reserved_one,reserved_two,reserved_three,"
                    + "ach_header_dest_iin,settlement_date,status,reason,ach_item_seq_no from cbs_npci_inward "
                    + "where settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "ach_item_seq_no=" + seqNo + " and iw_type='ECS'").getResultList();

            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Header Values.
            for (int h = 0; h < 1; h++) { //Only For First Iteration.
                Vector hVec = (Vector) dataList.get(0);
                headerDestIIN = hVec.get(14).toString();
                settlementCycle = hVec.get(18).toString();
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("ECS-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::ECS-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("ECS-DI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::ECS-DI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, ECS Location and Bank Code.");
            }
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and file_gen_type='EHI'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            genFileName = "ECS-CR-" + bankCode.toUpperCase() + "-" + npciUserName + "-"
                    + ymdOne.format(dmy.parse(fileGenDt)) + "-" + "NEW" + ParseFileUtil.addTrailingZeros(settlementCycle, 6) + "-RTN.txt";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + orgnBrCode + "','EHI'," + seqNo + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs ECS Return Files Insertion.");
            }
            totalRecordNo = String.valueOf(dataList.size()).trim();
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (int i = 0; i < dataList.size(); i++) {
                ele = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(ele.get(10).toString().trim()).divide(new BigDecimal(100));
                totalSubAmt = totalSubAmt.add(individualAmt);
            }
            String amtInPaisa = "";
            totalSubAmt = totalSubAmt.multiply(new BigDecimal(100));
            int dotIndex = totalSubAmt.toString().indexOf(".");
            if (dotIndex == -1) {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().trim(), 13);
            } else {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().substring(0, dotIndex).trim(), 13);
            }
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = headerId + ParseFileUtil.addTrailingZeros("", 7) + ParseFileUtil.addSuffixSpaces("", 84)
                    + headerDestIIN + ParseFileUtil.addTrailingZeros(totalRecordNo, 9)
                    + amtInPaisa + ymdOne.format(dmy.parse(fileGenDt)) + ParseFileUtil.addSuffixSpaces("", 26) + "." + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String destBankIIN = element.get(1).toString();
                String destAcType = element.get(2).toString();
                String ledgerFolioNo = element.get(3).toString();
                String destBankAcno = element.get(4).toString();
                String benName = element.get(5).toString();
                String sponserBankIIN = element.get(6).toString();
                String userNumber = element.get(7).toString();
                String userName = element.get(8).toString();
                String transReference = element.get(9).toString();
                String amount = element.get(10).toString();
                String reservedACHItemSeqNo = element.get(11).toString();
                String reservedChecksum = element.get(12).toString();
                String status = element.get(16).toString();
                String reason = element.get(17).toString();
                String reservedFlag = "1";  //For Success
                String reasonCode = "00";
                if (status.equalsIgnoreCase("U")) {
                    reservedFlag = "0";     //For Un-Success
                    reasonCode = getAchReturnReasonCode(reason);
                }
                String individualStr = detailId + ParseFileUtil.addSuffixSpaces(destBankIIN, 9)
                        + ParseFileUtil.addSuffixSpaces(destAcType, 2) + ParseFileUtil.addSuffixSpaces(ledgerFolioNo, 3)
                        + ParseFileUtil.addSuffixSpaces(destBankAcno, 15) + ParseFileUtil.addSuffixSpaces(benName, 40)
                        + ParseFileUtil.addSuffixSpaces(sponserBankIIN, 9) + ParseFileUtil.addSuffixSpaces(userNumber, 7)
                        + ParseFileUtil.addSuffixSpaces(userName, 20) + ParseFileUtil.addSuffixSpaces(transReference, 13)
                        + ParseFileUtil.addTrailingZeros(amount, 13) + ParseFileUtil.addSuffixSpaces(reservedACHItemSeqNo, 10)
                        + ParseFileUtil.addSuffixSpaces(reservedChecksum, 10) + reservedFlag
                        + ParseFileUtil.addSuffixSpaces("", 4) + reasonCode + "\n";
                fw.write(individualStr);
            }
            fw.close();
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

    public String npciOacUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String iwType, String processingMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            //Dupliacte Upload Checking
            NpciInwardDto dto = inputList.get(0);
            userList = em.createNativeQuery("select header_identifier from cbs_npci_oac_detail "
                    + "where file_coming_dt='" + dto.getSettlementDt() + "' and "
                    + "file_seq_no='" + dto.getFileSeqNo() + "' and iw_type='" + iwType + "'").getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            for (int i = 0; i < inputList.size(); i++) {
                dto = inputList.get(i);
                String query = "";
                if (processingMode.equalsIgnoreCase("H2H")) {
                    query = "insert into cbs_npci_oac_detail(header_identifier,originator_code,"
                            + "responder_code,file_coming_dt,file_ref_no,file_seq_no,record_identifier,rrn,micr,"
                            + "ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,"
                            + "cbs_name,pan_no,header_filler,record_filler,enter_by,enter_date,enter_time,"
                            + "return_code,amount,ach_item_seq_no,iw_type,FILE_GEN_FLAG) "
                            + "values('" + dto.getHeaderId().trim() + "','" + dto.getOriginatorCode().trim() + "',"
                            + "'" + dto.getResponderCode().trim() + "','" + dto.getSettlementDt().trim() + "',"
                            + "'" + dto.getFileRefNo().trim() + "','" + dto.getFileSeqNo().trim() + "',"
                            + "'" + dto.getRecordId().trim() + "','" + dto.getUserCreditRef().trim() + "',"
                            + "'" + dto.getDestBankIIN().trim() + "','" + dto.getDestAcType().trim() + "',"
                            + "'" + dto.getOldAcno().trim() + "','" + dto.getOldAcName().trim() + "',"
                            + "'" + dto.getUserNumber().trim() + "','" + dto.getUserName().trim() + "',"
                            + "'" + dto.getTranRef().trim() + "','','" + dto.getCbsAcno().trim() + "',"
                            + "'" + dto.getCbsAcName().trim() + "','','','','" + uploadingUserName + "',"
                            + "'" + ymd.format(new Date()) + "',now(),'','0','','" + iwType + "','N')";
                } else {
                    query = "insert into cbs_npci_oac_detail(header_identifier,originator_code,"
                            + "responder_code,file_coming_dt,file_ref_no,file_seq_no,record_identifier,rrn,micr,"
                            + "ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,"
                            + "cbs_name,pan_no,header_filler,record_filler,enter_by,enter_date,enter_time,"
                            + "return_code,amount,ach_item_seq_no,iw_type) "
                            + "values('" + dto.getHeaderId().trim() + "','" + dto.getOriginatorCode().trim() + "',"
                            + "'" + dto.getResponderCode().trim() + "','" + dto.getSettlementDt().trim() + "',"
                            + "'" + dto.getFileRefNo().trim() + "','" + dto.getFileSeqNo().trim() + "',"
                            + "'" + dto.getRecordId().trim() + "','" + dto.getUserCreditRef().trim() + "',"
                            + "'" + dto.getDestBankIIN().trim() + "','" + dto.getDestAcType().trim() + "',"
                            + "'" + dto.getOldAcno().trim() + "','" + dto.getOldAcName().trim() + "',"
                            + "'" + dto.getUserNumber().trim() + "','" + dto.getUserName().trim() + "',"
                            + "'" + dto.getTranRef().trim() + "','','" + dto.getCbsAcno().trim() + "',"
                            + "'" + dto.getCbsAcName().trim() + "','','','','" + uploadingUserName + "',"
                            + "'" + ymd.format(new Date()) + "',now(),'','0','','" + iwType + "')";
                }
                int n = em.createNativeQuery(query).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Uploading of OAC.");
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

    public String npciEcsDrUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String iwType, String processingMode, String uploadedFileName)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            //Dupliacte Upload Checking
            NpciInwardDto dto = inputList.get(0);
            userList = em.createNativeQuery("select header_identifier from cbs_npci_oac_detail "
                    + "where file_coming_dt='" + dto.getSettlementDt() + "' and "
                    + "file_seq_no='" + dto.getFileSeqNo() + "' and iw_type='" + iwType + "'").getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            for (int i = 0; i < inputList.size(); i++) {
                dto = inputList.get(i);
                String query = "";

//                int autoFillFlag = reportRemote.getCodeByReportName("AUTO-RETURN-ECS-ACH-DR");
//                String successFlag = "", resonCode = "";
//                if (autoFillFlag == 1) {
//                    Double amt = (new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal(100))).doubleValue();
//                    List returnChqList = em.createNativeQuery("select MndtId from mms_detail where MndtId='" + dto.getUmrn().trim() + "' and "
//                            + " Mandate_Status<>'S' and ( MaxAmt='" + amt + "' or ColltnAmt='" + amt + "')").getResultList();
//                    if (!returnChqList.isEmpty()) {
//                        successFlag = "R";
//                        resonCode = "70";
//                    }
//                }

                if (processingMode.equalsIgnoreCase("H2H")) {
                    query = " insert into cbs_npci_oac_detail(header_identifier,originator_code,"
                            + "responder_code,file_coming_dt,file_ref_no,file_seq_no,record_identifier,rrn,micr,ac_type,"
                            + "old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,cbs_name,pan_no,"
                            + "header_filler,record_filler,enter_by,enter_date,enter_time,return_code,amount,"
                            + "ach_item_seq_no,iw_type,file_name_date,FILE_GEN_FLAG) values('" + dto.getHeaderId().trim() + "',"
                            + "'" + dto.getOriginatorCode().trim() + "','" + dto.getResponderCode().trim() + "',"
                            + "'" + dto.getSettlementDt().trim() + "','" + dto.getFileRefNo().trim() + "',"
                            + "'" + dto.getFileSeqNo().trim() + "','" + dto.getRecordId().trim() + "',"
                            + "'" + dto.getUserCreditRef().trim() + "','" + dto.getDestBankIIN().trim() + "',"
                            + "'" + dto.getDestAcType().trim() + "','" + dto.getOldAcno().trim() + "',"
                            + "'" + dto.getOldAcName().trim() + "','" + dto.getUserNumber().trim() + "',"
                            + "'" + dto.getUserName().trim() + "','" + dto.getTranRef().trim() + "','',"
                            + "'','','','','','" + uploadingUserName + "','" + ymd.format(new Date()) + "',now(),"
                            + "'','" + dto.getAmount().trim() + "','" + dto.getAchFiller().trim() + "',"
                            + "'" + iwType + "','" + dto.getFileNameDt() + "','N')";
                } else {
                    query = " insert into cbs_npci_oac_detail(header_identifier,originator_code,"
                            + "responder_code,file_coming_dt,file_ref_no,file_seq_no,record_identifier,rrn,micr,ac_type,"
                            + "old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,cbs_name,pan_no,"
                            + "header_filler,record_filler,enter_by,enter_date,enter_time,return_code,amount,"
                            + "ach_item_seq_no,iw_type,file_name_date) values('" + dto.getHeaderId().trim() + "',"
                            + "'" + dto.getOriginatorCode().trim() + "','" + dto.getResponderCode().trim() + "',"
                            + "'" + dto.getSettlementDt().trim() + "','" + dto.getFileRefNo().trim() + "',"
                            + "'" + dto.getFileSeqNo().trim() + "','" + dto.getRecordId().trim() + "',"
                            + "'" + dto.getUserCreditRef().trim() + "','" + dto.getDestBankIIN().trim() + "',"
                            + "'" + dto.getDestAcType().trim() + "','" + dto.getOldAcno().trim() + "',"
                            + "'" + dto.getOldAcName().trim() + "','" + dto.getUserNumber().trim() + "',"
                            + "'" + dto.getUserName().trim() + "','" + dto.getTranRef().trim() + "','',"
                            + "'','','','','','" + uploadingUserName + "','" + ymd.format(new Date()) + "',now(),"
                            + "'','" + dto.getAmount().trim() + "','" + dto.getAchFiller().trim() + "',"
                            + "'" + iwType + "','" + dto.getFileNameDt() + "')";
                }
                int n = em.createNativeQuery(query).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Uploading ECS DR File.");
                }
            }
            //In case of H2H IW
            if (processingMode.equalsIgnoreCase("H2H")) {
                int zn = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + uploadedFileName + "','IW')").executeUpdate();
                if (zn <= 0) {
                    throw new ApplicationException("Problem in cbs_npci_h2h_file_detail insertion.");
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

    public String npciInputSave(String func, String oldUref, String micr, String acType, String acno,
            String name, String amount, String type, String enterBy, String ownBankAcno)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (func.equalsIgnoreCase("A")) {
                int n = em.createNativeQuery("insert into cbs_npci_ecs_input_detail(unique_ref_no,type,micr,ac_type,acno,name,amount,"
                        + "ledge_folio_no,ach_item_seq_no,checksum,flag,reason_code,entry_by,enter_time,own_bank_acno) "
                        + " values('" + ymdms.format(new Date()).substring(0, 13) + "','" + type + "','" + micr + "','" + acType + "','" + acno + "',"
                        + "'" + name + "','" + amount + "','','','','','','" + enterBy + "',now(),'" + ownBankAcno + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In NPCI ECS Input Insertion.");
                }
            } else if (func.equalsIgnoreCase("M")) {
                int n = em.createNativeQuery("Insert Into cbs_npci_ecs_input_detail_his(unique_ref_no,type,micr,ac_type,acno,name,amount,"
                        + "ledge_folio_no,ach_item_seq_no,checksum,flag,reason_code,entry_by,enter_time,update_by,update_time,own_bank_acno) "
                        + "select unique_ref_no,type,micr,ac_type,acno,name,amount,ledge_folio_no,ach_item_seq_no,checksum,flag,reason_code,"
                        + "entry_by,enter_time,'" + enterBy + "',now(),own_bank_acno from cbs_npci_ecs_input_detail where unique_ref_no = '" + oldUref + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In NPCI ECS History Insertion.");
                }

                int m = em.createNativeQuery("Update cbs_npci_ecs_input_detail Set micr='" + micr + "',ac_type='" + acType + "',acno='" + acno + "',"
                        + "name='" + name + "',amount='" + amount + "',type='" + type + "', entry_by='" + enterBy + "',enter_time=now(),own_bank_acno='" + ownBankAcno + "' "
                        + " Where unique_ref_no ='" + oldUref + "'").executeUpdate();
                if (m <= 0) {
                    throw new ApplicationException("Problem In NPCI ECS Input Updation.");
                }
            } else if (func.equalsIgnoreCase("D")) {
                int n = em.createNativeQuery("Insert Into cbs_npci_ecs_input_detail_his(unique_ref_no,type,micr,ac_type,acno,name,amount,"
                        + "ledge_folio_no,ach_item_seq_no,checksum,flag,reason_code,entry_by,enter_time,update_by,update_time,own_bank_acno) "
                        + "select unique_ref_no,type,micr,ac_type,acno,name,amount,ledge_folio_no,ach_item_seq_no,checksum,flag,reason_code,"
                        + "entry_by,enter_time,'" + enterBy + "',now(),own_bank_acno from cbs_npci_ecs_input_detail where unique_ref_no = '" + oldUref + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In NPCI ECS Input Insertion.");
                }

                int m = em.createNativeQuery("delete from cbs_npci_ecs_input_detail Where unique_ref_no ='" + oldUref + "'").executeUpdate();
                if (m <= 0) {
                    throw new ApplicationException("Problem In NPCI ECS Deletion.");
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

    public List<NpciInputPojo> getNpciInputDetails(String ectType) throws ApplicationException {
        List<NpciInputPojo> npciPojoList = new ArrayList<NpciInputPojo>();
        try {
            List result = new ArrayList();
            result = em.createNativeQuery("select unique_ref_no,type,micr,ac_type,acno,name,amount,entry_by,own_bank_acno from "
                    + "cbs_npci_ecs_input_detail where type = '" + ectType + "'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    NpciInputPojo pDtl = new NpciInputPojo();
                    pDtl.setuRefNo(record.get(0).toString());
                    pDtl.setType(record.get(1).toString());
                    pDtl.setMicr(record.get(2).toString());
                    pDtl.setAcType(record.get(3).toString());
                    pDtl.setAcNo(record.get(4).toString());
                    pDtl.setName(record.get(5).toString());
                    pDtl.setAmount(record.get(6).toString());
                    pDtl.setEntry_by(record.get(7).toString());
                    pDtl.setOwnAcno(record.get(8).toString());
                    npciPojoList.add(pDtl);
                }
            }
            return npciPojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String npciAchDr306Upload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String iwType, String processingMode,
            String uploadedFileName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            //Dupliacte Upload Checking
            NpciInwardDto dto = inputList.get(0);
            userList = em.createNativeQuery("select header_identifier from cbs_npci_oac_detail "
                    + "where file_coming_dt='" + dto.getSettlementDt() + "' and "
                    + "file_seq_no='" + dto.getItemSeqNo() + "' and iw_type='" + iwType + "'").getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            for (int i = 0; i < inputList.size(); i++) {
                dto = inputList.get(i);
                String query = "";

                int autoFillFlag = reportRemote.getCodeByReportName("AUTO-RETURN-ACH-DR");
                String returnFlag = "", returnCode = "";
                if (autoFillFlag == 1 && !dto.getProductType().trim().equalsIgnoreCase("LEG")) {
                    Double amt = (new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal(100))).doubleValue();
                    List returnChqList = em.createNativeQuery("select MndtId from mms_detail where Mandate_Status<>'S' and "
                            + "MndtId='" + dto.getUmrn().trim() + "' and (MaxAmt>=" + amt + " or "
                            + "ColltnAmt=" + amt + ")").getResultList();
                    if (returnChqList.isEmpty()) {
                        returnFlag = "R";
//                        returnCode = "70"; //Coomented on 25/09/2019
                        returnCode = "71";
                    }
                }
                if (processingMode.equalsIgnoreCase("H2H")) {
                    query = "insert into cbs_npci_oac_detail(header_identifier,originator_code,"
                            + "responder_code,file_coming_dt,file_ref_no,file_seq_no,record_identifier,rrn,micr,"
                            + "ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,"
                            + "cbs_name,return_code,pan_no,header_filler,record_filler,enter_by,enter_date,"
                            + "enter_time,amount,ach_item_seq_no,iw_type,header_settlement_cycle,header_dest_iin,"
                            + "ledger_folio,checksum,sponsor_iin,product_type,umrn,success_flag,"
                            + "reason_code,file_gen_flag) values('" + dto.getHeaderId() + "','','','" + dto.getSettlementDt().trim() + "','',"
                            + "'" + dto.getItemSeqNo().trim() + "','" + dto.getRecordId() + "','',"
                            + "'" + dto.getDestBankIIN().trim() + "','" + dto.getDestAcType().trim() + "',"
                            + "'" + dto.getDestBankAcno().trim() + "','" + dto.getBeneName().trim() + "',"
                            + "'" + dto.getUserNumber().trim() + "','" + dto.getUserName().trim() + "',"
                            + "'" + dto.getUserCreditRef().trim() + "','" + returnFlag + "','','','" + returnCode + "','','','','" + uploadingUserName + "',"
                            + "'" + ymd.format(new Date()) + "',now(),'" + dto.getAmount().trim() + "',"
                            + "'" + dto.getReservedOne().trim() + "','" + iwType + "','" + dto.getSettlementCycle().trim() + "',"
                            + "'" + dto.getHeaderDestIIN().trim() + "','" + dto.getLedgerNo().trim() + "',"
                            + "'" + dto.getCheckSum().trim() + "','" + dto.getSponsorBankIIN().trim() + "',"
                            + "'" + dto.getProductType().trim() + "','" + dto.getUmrn().trim() + "','','','N')";
                } else {
                    query = "insert into cbs_npci_oac_detail(header_identifier,originator_code,"
                            + "responder_code,file_coming_dt,file_ref_no,file_seq_no,record_identifier,rrn,micr,"
                            + "ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,"
                            + "cbs_name,return_code,pan_no,header_filler,record_filler,enter_by,enter_date,"
                            + "enter_time,amount,ach_item_seq_no,iw_type,header_settlement_cycle,header_dest_iin,"
                            + "ledger_folio,checksum,sponsor_iin,product_type,umrn,success_flag,"
                            + "reason_code) values('" + dto.getHeaderId() + "','','','" + dto.getSettlementDt().trim() + "','',"
                            + "'" + dto.getItemSeqNo().trim() + "','" + dto.getRecordId() + "','',"
                            + "'" + dto.getDestBankIIN().trim() + "','" + dto.getDestAcType().trim() + "',"
                            + "'" + dto.getDestBankAcno().trim() + "','" + dto.getBeneName().trim() + "',"
                            + "'" + dto.getUserNumber().trim() + "','" + dto.getUserName().trim() + "',"
                            + "'" + dto.getUserCreditRef().trim() + "','" + returnFlag + "','','','" + returnCode + "','','','','" + uploadingUserName + "',"
                            + "'" + ymd.format(new Date()) + "',now(),'" + dto.getAmount().trim() + "',"
                            + "'" + dto.getReservedOne().trim() + "','" + iwType + "','" + dto.getSettlementCycle().trim() + "',"
                            + "'" + dto.getHeaderDestIIN().trim() + "','" + dto.getLedgerNo().trim() + "',"
                            + "'" + dto.getCheckSum().trim() + "','" + dto.getSponsorBankIIN().trim() + "',"
                            + "'" + dto.getProductType().trim() + "','" + dto.getUmrn().trim() + "','','')";
                }
                int n = em.createNativeQuery(query).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Uploading ECS DR File.");
                }
            }
            //In case of H2H IW
            if (processingMode.equalsIgnoreCase("H2H")) {
                int zn = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + uploadedFileName + "','IW')").executeUpdate();
                if (zn <= 0) {
                    throw new ApplicationException("Problem in cbs_npci_h2h_file_detail insertion.");
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

    public List getAllAchTrsNo(String iwType, String dt) throws ApplicationException {
        List list = null;
        try {
            if (iwType.equalsIgnoreCase("ACH")) {
                list = em.createNativeQuery("select distinct trs_no from cbs_npci_inward "
                        + "where iw_type='" + iwType + "' and tran_date='" + dt + "'").getResultList();
            } else if (iwType.equalsIgnoreCase("ECS")) {
                list = em.createNativeQuery("select distinct ach_item_seq_no from cbs_npci_inward "
                        + "where iw_type='" + iwType + "' and tran_date='" + dt + "'").getResultList();
            } else if (iwType.equalsIgnoreCase("APB")) {
                list = em.createNativeQuery("select distinct trs_no from cbs_npci_inward "
                        + "where iw_type='" + iwType + "' and tran_date='" + dt + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List getAllAchDRFileSqnNo(String iwType, String dt) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct FILE_SEQ_NO from cbs_npci_oac_detail where iw_type='" + iwType + "' and FILE_COMING_DT='" + dt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getSettlementDtForUnverifiedEntriesNPCIInward(String fileType) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct date_format(settlement_date,'%d/%m/%Y') from cbs_npci_inward where  iw_type='" + fileType + "' and  file_gen_flag='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getFileComingDtForUnverifiedEntriesNpciNonAadharInward() throws ApplicationException {
        try {
//            return em.createNativeQuery("select distinct date_format(FILE_COMING_DT,'%d/%m/%Y'),FILE_SEQ_NO from cbs_npci_inward_non_aadhaar where  ORIGINATOR_CODE in('IOCL' ,'BPCL','HPCL') and  FILE_GEN_FLAG='N'").getResultList();
            return em.createNativeQuery("select distinct date_format(FILE_COMING_DT,'%d/%m/%Y'),FILE_SEQ_NO from cbs_npci_inward_non_aadhaar where  ORIGINATOR_CODE in(select ref_desc from cbs_ref_rec_type  where ref_rec_no in('008')) and  FILE_GEN_FLAG='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getLpgTypeForH2hAvFileGeneration(String Date, String seqNo) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select ORIGINATOR_CODE from cbs_npci_inward_non_aadhaar where FILE_COMING_DT='" + Date + "' and FILE_SEQ_NO ='" + seqNo + "' and FILE_GEN_FLAG='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getSettlementDtForUnverifiedEntriesNPCIOAC(String fileType) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct date_format(FILE_COMING_DT,'%d/%m/%Y') from cbs_npci_oac_detail where  iw_type='" + fileType + "' and  FILE_GEN_FLAG='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getUploadDateForUnverifiedEntriesForReturnMMs() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct date_format(Upload_Date,'%d/%m/%Y'),Mandate_Type,File_No,Mandate_Mode from mms_upload_xml_detail where Mandate_Status ='V' and FILE_GEN_FLAG ='N' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String npciAchDrResponse306Upload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String fileLocation, String fileName, String processingMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            //Dupliacte Upload Checking
            userList = em.createNativeQuery("select CBS_Umrn from cbs_sponsor_txn_detail where Response_File_Name='" + fileName + "' " /* + "and Txn_Date='"+ymdSql.format(ymdOne.parse(entryGenDate))+"'"*/).getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);
                String query = "", umrnNo = "", /*entryGenDate = "",*/ tranrefNo = "", entryGenDate = "";
                tranrefNo = dto.getUserCreditRef();
                umrnNo = tranrefNo.substring(0, 17).trim();
                entryGenDate = tranrefNo.substring(22).trim();
                int entrySeqNo = Integer.parseInt(tranrefNo.substring(17, 22));

                List entryChqList = em.createNativeQuery("select ifnull(success_flag,'') from cbs_sponsor_txn_detail "
                        + "where cbs_umrn='" + umrnNo + "' and entry_seqno=" + entrySeqNo + " and "
                        + "date_format(entry_date,'%Y%m%d')='" + ymd.format(ymdOne.parse(entryGenDate)) + "'").getResultList();
                if (entryChqList.isEmpty()) {
                    continue;
                } else {
                    Vector eleChq = (Vector) entryChqList.get(0);
                    if (eleChq.get(0).toString().equalsIgnoreCase("1")
                            || eleChq.get(0).toString().equalsIgnoreCase("0")) { //1-Pass, 0-Return
                        System.out.println("Ignore Umrn Is-->" + umrnNo);
                        continue;
                    }
                }

                String returnCode = String.valueOf(Integer.parseInt(dto.getReservedReason()));
                if (processingMode.equalsIgnoreCase("H2H")) {
                    query = "";
                } else {
                    query = "UPDATE cbs_sponsor_txn_detail SET Success_Flag='" + dto.getReservedFlag() + "', Return_Code='" + returnCode + "',"
                            + " Response_File_Name='" + fileName + "', Update_By='" + uploadingUserName + "', Update_Date='" + ymdSql.format(dmy.parse(todayDt)) + "'"
                            + " WHERE CBS_Umrn='" + umrnNo + "' and "
                            + "date_format(Entry_Date,'%Y%m%d')='" + ymd.format(ymdOne.parse(entryGenDate)) + "' and "
                            + "Entry_SeqNo='" + entrySeqNo + "'";
                }
                int n = em.createNativeQuery("INSERT INTO cbs_sponsor_txn_detail_his (CBS_Umrn, TxnFileType, Txn_Date, Entry_Date, Success_Flag,"
                        + " Return_Code, Response_File_Name, Update_By, Update_Date, Txn_File_Name,Entry_SeqNo) "
                        + " select CBS_Umrn, TxnFileType, Txn_Date, Entry_Date, Success_Flag, Return_Code, Response_File_Name,"
                        + " Update_By, Update_Date, Txn_File_Name,Entry_SeqNo from cbs_sponsor_txn_detail WHERE"
                        + " CBS_Umrn='" + umrnNo + "' and  date_format(Entry_Date,'%Y%m%d')='" + ymd.format(ymdOne.parse(entryGenDate)) + "' and Entry_SeqNo='" + entrySeqNo + "' ").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Updating History ACH DR Response File.");
                }
                n = em.createNativeQuery(query).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Uploading ACH DR Response File.");
                }


                float trsno = ftsRemote.getTrsNo().floatValue();
                String accNoReturn = "", umrnNoReturn = "";
                String successFlag = dto.getReservedFlag();

                int achDrChgApply = reportRemote.getCodeByReportName("ACH-INP-RET-CHG");
                if (achDrChgApply == 1) {
                    //*** Charge Deduction for for return code 4 and 5 from ref rec 386
                    List returnReasonList = em.createNativeQuery("select * from cbs_ref_rec_type where REF_REC_NO='386' and REF_CODE='" + returnCode + "'").getResultList();
                    List returnAccDtlList = em.createNativeQuery("select Creditor_Acno from cbs_mandate_detail where CBS_Umrn='" + umrnNo + "'").getResultList();
                    if ((!returnAccDtlList.isEmpty()) && (!returnReasonList.isEmpty()) && (!successFlag.equalsIgnoreCase("1"))) {
                        accNoReturn = ((Vector) returnAccDtlList.get(0)).get(0).toString();
                        String result = ibtRemote.postACHDRreturnChargesAndTax(accNoReturn, uploadingUserName, trsno, uploadBrCode, ymd.format(dmy.parse(todayDt)));
                        if (!(result.equalsIgnoreCase("true") || result.equalsIgnoreCase("pending"))) {

                            throw new ApplicationException("Problem In Charge Deduction.");
                        }
                    }
                    //***end 
                }

                //*** Amount posting  for successfull response
                String accNoSuccess = "";
                List successAccDtlList = em.createNativeQuery("select Creditor_Acno from cbs_mandate_detail where CBS_Umrn='" + umrnNo + "'").getResultList();
                if ((!successAccDtlList.isEmpty()) && (successFlag.equalsIgnoreCase("1"))) {
                    accNoSuccess = ((Vector) successAccDtlList.get(0)).get(0).toString();
                    BigDecimal amt = new BigDecimal(dto.getAmount()).divide(new BigDecimal(100));
                    String result = ibtRemote.postACHDRSuccessEntry(accNoSuccess, Double.parseDouble(amt.toString()), uploadingUserName, trsno);
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Problem In clg amoount posting.");
                    }
                }
                //****End
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

    @Override
    public String npciECSDrResponse156Upload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String fileLocation, String fileName, String processingMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userList = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) userList.get(0);
            if (userList.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }
            //Dupliacte Upload Checking
            userList = em.createNativeQuery("select CBS_Umrn from cbs_sponsor_txn_detail where Response_File_Name='" + fileName + "' " /* + "and Txn_Date='"+ymdSql.format(ymdOne.parse(entryGenDate))+"'"*/).getResultList();
            if (!userList.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
            for (NpciInwardDto dto : inputList) {
                String query = "", umrnNo = "", fileGenDate = "";
                System.out.println("Detail Code-->" + dto.getRecordId() + "::UserRefNo-->" + dto.getUserCreditRef()
                        + "::Success Flag-->" + dto.getReservedFlag() + "::Reject Reason-->" + dto.getReservedReason());

                umrnNo = dto.getUserCreditRef().substring(0, 10);
                int entrySeqNo = Integer.parseInt(dto.getUserCreditRef().substring(10));
                fileGenDate = dto.getFileRefNo().substring(6);

                List entryChqList = em.createNativeQuery("select ifnull(success_flag,'') from cbs_sponsor_txn_detail "
                        + "where cbs_umrn='" + umrnNo + "' and entry_seqno=" + entrySeqNo + " and "
                        + "date_format(entry_date,'%Y%m%d')='" + fileGenDate + "'").getResultList();
                if (entryChqList.isEmpty()) {
                    continue;
                } else {
                    Vector eleChq = (Vector) entryChqList.get(0);
                    if (eleChq.get(0).toString().equalsIgnoreCase("1")
                            || eleChq.get(0).toString().equalsIgnoreCase("0")) { //1-Pass, 0-Return
                        continue;
                    }
                }

                if (processingMode.equalsIgnoreCase("H2H")) {
                    query = "";
                } else {
                    query = "UPDATE cbs_sponsor_txn_detail SET Success_Flag='" + dto.getReservedFlag() + "', Return_Code='" + Integer.parseInt(dto.getReservedReason()) + "',"
                            + " Response_File_Name='" + fileName + "', Update_By='" + uploadingUserName + "', Update_Date='" + ymdSql.format(dmy.parse(todayDt)) + "'"
                            + " WHERE CBS_Umrn='" + umrnNo + "' and date_format(Entry_Date,'%Y%m%d')='" + fileGenDate + "' and Entry_SeqNo=" + entrySeqNo;
                }
                int n = em.createNativeQuery("INSERT INTO cbs_sponsor_txn_detail_his (CBS_Umrn, TxnFileType, Txn_Date, Entry_Date, Success_Flag,"
                        + " Return_Code, Response_File_Name, Update_By, Update_Date, Txn_File_Name,Entry_SeqNo) "
                        + " select CBS_Umrn, TxnFileType, Txn_Date, Entry_Date, Success_Flag, Return_Code, Response_File_Name,"
                        + " Update_By, Update_Date, Txn_File_Name,Entry_SeqNo from cbs_sponsor_txn_detail WHERE"
                        + " CBS_Umrn='" + umrnNo + "' and date_format(Entry_Date,'%Y%m%d')='" + fileGenDate + "' and Entry_SeqNo=" + entrySeqNo).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Updating History ECS DR Response File.");
                }
                n = em.createNativeQuery(query).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Uploading ECS DR Response File.");
                }

                float trsno = ftsRemote.getTrsNo().floatValue();
                String accNoReturn = "", umrnNoReturn = "";
                String successFlag = dto.getReservedFlag();

                int ecsDrChgApply = reportRemote.getCodeByReportName("ECS-INP-RET-CHG");
                if (ecsDrChgApply == 1) {
                    //*** Charge Deduction for for return code 4 and 5 from ref rec 385
                    List returnReasonList = em.createNativeQuery("select * from cbs_ref_rec_type where REF_REC_NO='385' and REF_CODE='" + Integer.parseInt(dto.getReservedReason().trim()) + "'").getResultList();
                    List returnAccDtlList = em.createNativeQuery("select Creditor_Acno from cbs_mandate_detail where CBS_Umrn='" + umrnNo + "'").getResultList();
                    if ((!returnAccDtlList.isEmpty()) && (!returnReasonList.isEmpty()) && (!successFlag.equalsIgnoreCase("1"))) {
                        accNoReturn = ((Vector) returnAccDtlList.get(0)).get(0).toString();
                        String result = ibtRemote.postECSChargesAndTax(umrnNo, accNoReturn, uploadingUserName, trsno, uploadBrCode, ymd.format(dmy.parse(todayDt)));
                        if (!(result.equalsIgnoreCase("true") || result.equalsIgnoreCase("pending"))) {
                            throw new ApplicationException("Problem In Charge Deduction.");
                        }

                    }
                    //***end 
                }

                //*** Amount posting  for successfull response
                String accNoSuccess = "";
                List successAccDtlList = em.createNativeQuery("select Creditor_Acno from cbs_mandate_detail where CBS_Umrn='" + umrnNo + "'").getResultList();
                if ((!successAccDtlList.isEmpty()) && (successFlag.equalsIgnoreCase("1"))) {
                    accNoSuccess = ((Vector) successAccDtlList.get(0)).get(0).toString();
                    BigDecimal amt = new BigDecimal(dto.getAmount()).divide(new BigDecimal(100));
                    String result = ibtRemote.postECSDRSuccessEntry(umrnNo, accNoSuccess, Double.parseDouble(amt.toString()), uploadingUserName, trsno);
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Problem In clg amoount posting.");
                    }
                }
                //****End
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

    @Override
    public List<AccwiesECSACHReportPojo> getAccwiesACHECSReportData(String fileType, String sponsorType, String txnType,
            String frDt, String toDt, String accountNo) throws ApplicationException {
        List<AccwiesECSACHReportPojo> reportData = new ArrayList<>();
        try {
            List result = em.createNativeQuery("select cm.CBS_Umrn,cm.Creditor_Acno,Creditor_BankName,cm.Debtor_Acno,"
                    + "cm.Debtor_BankName,cm.Amount,cm.Frequency,date_format(cs.Txn_Date,'%d/%m/%Y'),date_format(Entry_Date,'%d/%m/%Y'),cs.Success_Flag,cs.Return_Code "
                    + "from cbs_mandate_detail cm,cbs_sponsor_txn_detail cs where cs.CBS_Umrn=cm.CBS_Umrn and cm.TxnFileType='" + fileType + "' and "
                    + "cm.Proprietary='" + txnType + "' and cs.txnfiletype='" + sponsorType + "' and (Debtor_Acno='" + accountNo + "' or Creditor_Acno='" + accountNo + "') and "
                    + "(Entry_Date BETWEEN '" + frDt + "' AND '" + toDt + "')").getResultList();

            if (result.isEmpty()) {
                throw new ApplicationException("There is no data to print.");
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                AccwiesECSACHReportPojo pojo = new AccwiesECSACHReportPojo();
                pojo.setUmrn(vtr.get(0).toString().trim());

                if (txnType.equalsIgnoreCase("DEBIT")) {
                    pojo.setDestAccNo(vtr.get(3).toString().trim());
                    pojo.setDestBankName(vtr.get(4).toString().trim());
                } else if (txnType.equalsIgnoreCase("CREDIT")) {
                    pojo.setDestAccNo(vtr.get(1).toString().trim());
                    pojo.setDestBankName(vtr.get(2).toString().trim());
                }

                BigDecimal amt = new BigDecimal(vtr.get(5).toString().trim());
                pojo.setAmount(new BigDecimal(amt.doubleValue()));
                pojo.setFrequency(reportRemote.getRefRecDesc("365", vtr.get(6).toString().trim()));
                pojo.setTxnDate(vtr.get(7).toString().trim());
                pojo.setTxnFileGenDate(vtr.get(8).toString().trim());
                String successFlag = vtr.get(9).toString().trim();
                String reasonCode = vtr.get(10).toString().trim();
                if (successFlag.equalsIgnoreCase("1")) { //ACH-ECS
                    pojo.setStatus("Success");
                    pojo.setReason("");
                } else if (successFlag.equalsIgnoreCase("0")) { //ACH-ECS 
                    pojo.setStatus("Return");
                    if (txnType.equalsIgnoreCase("ACH")) {
                        pojo.setReason(reportRemote.getRefRecDesc("319", reasonCode));
                    } else if (txnType.equalsIgnoreCase("ECS")) {
                        pojo.setReason(reportRemote.getRefRecDesc("315", reasonCode));
                    }
                } else if (successFlag.equalsIgnoreCase("2")) { //Only For ECS
                    pojo.setStatus("Return");
                    pojo.setReason("Rejection at NACH");
                } else { //ACH-ECS
                    pojo.setStatus("Initiated");
                    pojo.setReason("");
                }
                reportData.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return reportData;
    }

    @Override
    public List<AchEcsResponseStatusReportPojo> getAchEcsResponseStatusReportData(String txnType, String sponsorType,
            String proprietary, String fromDate, String toDate, String status) throws ApplicationException {
        List<AchEcsResponseStatusReportPojo> resultData = new ArrayList<>();
        try {
            String query = "select m.cbs_umrn,m.creditor_acno,m.creditor_name,m.amount,m.debtor_acno,m.debtor_name,"
                    + "date_format(s.entry_date,'%Y%m%d') as entry_date,s.success_flag,s.return_code,"
                    + "date_format(s.update_date,'%Y%m%d') as response_date from cbs_mandate_detail m,"
                    + "cbs_sponsor_txn_detail s where m.cbs_umrn=s.cbs_umrn and m.txnfiletype='" + txnType + "' and "
                    + "s.txnfiletype='" + sponsorType + "' and m.proprietary='" + proprietary + "' and "
                    + "date_format(s.entry_date,'%Y%m%d') between '" + fromDate + "' and '" + toDate + "' and ifnull(s.Cancel_By,'')=''";
            if (!status.equalsIgnoreCase("All")) {
                if (status.equalsIgnoreCase("In")) {
                    status = "''";
                }
                query = query + " and s.success_flag in(" + status + ")";
            }
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to show the report.");
            }
            for (int i = 0; i < dataList.size(); i++) {
                AchEcsResponseStatusReportPojo pojo = new AchEcsResponseStatusReportPojo();
                Vector ele = (Vector) dataList.get(i);
                pojo.setCbsUmrn(ele.get(0).toString().trim());
                pojo.setCreditorAccount(ele.get(1).toString().trim());
                pojo.setCreditorName(ele.get(2).toString().trim());
                pojo.setAmount(new BigDecimal(ele.get(3).toString().trim()));
                pojo.setDebitorAccount(ele.get(4).toString().trim());
                pojo.setDebitorName(ele.get(5).toString().trim());
                pojo.setEntryDt(ele.get(6).toString().trim());
                pojo.setSuccessFlag(ele.get(7).toString().trim());
                pojo.setReturnCode(ele.get(8).toString().trim());
                pojo.setResponseDt(ele.get(9).toString().trim());
                if (pojo.getSuccessFlag().equalsIgnoreCase("1")) { //ACH-ECS
                    pojo.setStatus("Success");
                    pojo.setReasonDesc("");
                } else if (pojo.getSuccessFlag().equalsIgnoreCase("0")) { //ACH-ECS 
                    pojo.setStatus("Return");
                    if (txnType.equalsIgnoreCase("ACH")) {
                        pojo.setReasonDesc(reportRemote.getRefRecDesc("319", pojo.getReturnCode()));
                    } else if (txnType.equalsIgnoreCase("ECS")) {
                        pojo.setReasonDesc(reportRemote.getRefRecDesc("315", pojo.getReturnCode()));
                    }
                } else if (pojo.getSuccessFlag().equalsIgnoreCase("2")) { //Only For ECS
                    pojo.setStatus("Return");
                    pojo.setReasonDesc("Rejection at NACH");
                } else if (pojo.getSuccessFlag().equalsIgnoreCase("3")) { //ACH-ECS
                    pojo.setStatus("");
                    pojo.setReasonDesc("Extension");
                } else if (pojo.getSuccessFlag().equalsIgnoreCase("7")) { //ACH-ECS
                    pojo.setStatus("");
                    pojo.setReasonDesc("Deemed Accepted.");
                } else { //ACH-ECS
                    pojo.setStatus("Initiated");
                    pojo.setReasonDesc("");
                }
                resultData.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultData;
    }

    @Override
    public List<String> getAllBankIfsc() throws ApplicationException {
        List<String> allBankIfsc = new ArrayList<>();
        try {
            List dataList = em.createNativeQuery("select distinct upper(ifsc_code) from branchmaster "
                    + "where ifsc_code is not null").getResultList();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                allBankIfsc.add(ele.get(0).toString().trim());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return allBankIfsc;
    }

    public String updateAtmCardNoViaFile(String cardno, String acno, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = em.createNativeQuery("update atm_card_master  set card_no = '" + cardno + "',lastUpdateBy='" + user + "',lastUpdateDate=now()  where acno = '" + acno + "' and card_no = ''").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In updation of Card no.");
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());

        }
        return "true";
    }

    public List getAcNoInAtmCardMaster(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno from atm_card_master where acno ='" + acno + "' and card_no=''").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }
}
