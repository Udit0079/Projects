/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.SSSRejectDto;
import com.cbs.dto.other.PmSchemeRegDto;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.SSSRegistrationPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author root
 */
@Stateless(mappedName = "SSSFileGeneratorFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class SSSFileGeneratorFacade implements SSSFileGeneratorFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingFacade;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private CommonReportMethodsRemote reportCommon;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @Resource
    EJBContext context;
    Date curDt = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat mdy = new SimpleDateFormat("MMddyyyy");
    SimpleDateFormat dmyOne = new SimpleDateFormat("ddMMyy");
    DecimalFormat nf = new DecimalFormat("#.##");

    public List getPmbsData() throws ApplicationException {
        try {
            return em.createNativeQuery("select txnid,scheme_code, vendor_code, premium_amt, insured_amt, scheme_gl, gl_amt, scheme_pl, pl_amt, "
                    + "agent_amt, date_format(effective_date,'%d/%m/%Y'), enter_by, date_format(enter_date,'%d/%m/%Y'),"
                    + "policy_ac_no,auto_debit_freq from pm_scheme_details").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getVendors(String schemeCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select pm.vendor_code, rf.REF_DESC from pm_scheme_details pm, cbs_ref_rec_type rf where "
                    + "scheme_code='" + schemeCode + "' and rf.ref_rec_no='216' and pm.vendor_code = rf.REF_CODE and pm.effective_date  >= "
                    + "(select max(effective_date) from pm_scheme_details where scheme_code='" + schemeCode + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCustDetailByAcno(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select custfullname,peraddressline1,ifnull(aadhaar_no,''),date_format(dateofbirth,'%d/%m/%Y'),mobilenumber,emailid,"
                    + "maritalstatus,minorflag,PerPostalCode,PerDistrict,PerStateCode,gender,PerAddressLine2,MailDistrict,MailPostalCode,MailAddressLine1 from cbs_customer_master_detail where customerid "
                    + "in(select CustId from customerid where acno = '" + acno + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getPmRegDetail(String funcFlag, String schemeCode, String vendorCode, String brCode, String acNo) throws ApplicationException {
        try {
            if (funcFlag.equalsIgnoreCase("3") || funcFlag.equalsIgnoreCase("1") || funcFlag.equalsIgnoreCase("4")) {
                return em.createNativeQuery("SELECT acno, nom_name, date_format(nom_dob,'%d/%m/%Y'), nom_add, nom_relationship, nom_aadhar, guardian_name, guardian_add, "
                        + "spouse_name, spouse_aadhar,scheme_code, disability, disability_details,"
                        + "date_format(auto_debit_date,'%d/%m/%Y'), enrol_flag,date_format(enrol_date,'%d/%m/%Y'),ENROLL_BY,"
                        + "AUTH,VENDOR_CODE,MINOR_FLAG,SWAVALAMBAN_SUB,PRAN,INCOME_TAX_PAYER,APY_PENSION_AMT,APY_CONTRIBUTION_AMT,APY_STATE_CODE FROM pm_scheme_reg_details where TXN_BR_CODE = '" + brCode + "' and scheme_code = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and auth = 'N'").getResultList();
            } else {
                return em.createNativeQuery("SELECT acno, nom_name, date_format(nom_dob,'%d/%m/%Y'), nom_add, nom_relationship, nom_aadhar, guardian_name, guardian_add, "
                        + "spouse_name, spouse_aadhar,scheme_code, disability, disability_details, "
                        + "date_format(auto_debit_date,'%d/%m/%Y'), enrol_flag,date_format(enrol_date,'%d/%m/%Y'),ENROLL_BY,"
                        + "AUTH,VENDOR_CODE,MINOR_FLAG,SWAVALAMBAN_SUB,PRAN,INCOME_TAX_PAYER,APY_PENSION_AMT,APY_CONTRIBUTION_AMT,APY_STATE_CODE FROM pm_scheme_reg_details where scheme_code = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and acno = '" + acNo + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String pmbsDetailSave(String schemeCode, String vendorCode, String premiumAmt, String insuredAamt, String schemeGl, String glAmt, String schemePl, String plAmt,
            String agentAmt, String effectiveDate, String enterBy, String enterDate, String policyNo, String autoFreq) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Query query = em.createNativeQuery("INSERT INTO pm_scheme_details (scheme_code, vendor_code, premium_amt, insured_amt, scheme_gl, "
                    + "gl_amt, scheme_pl, pl_amt, agent_amt, effective_date, enter_by, enter_date,policy_ac_no,auto_debit_freq)"
                    + " VALUES "
                    + "('" + schemeCode + "', '" + vendorCode + "', " + Double.parseDouble(premiumAmt) + ", " + Double.parseDouble(insuredAamt) + ", "
                    + "'" + schemeGl + "', " + Double.parseDouble(glAmt) + ", '" + schemePl + "', " + Double.parseDouble(plAmt) + ", " + Double.parseDouble(agentAmt) + ", "
                    + "'" + effectiveDate + "', '" + enterBy + "', '" + enterDate + "','" + policyNo + "','" + autoFreq + "')");

            int rowAffected = query.executeUpdate();
            if (rowAffected <= 0) {
                throw new ApplicationException("Problem in data insertion");
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

    public String pmbsDetailUpdate(String txnId, String schemeCode, String vendorCode, String premiumAmt, String insuredAamt, String schemeGl, String glAmt, String schemePl, String plAmt, String agentAmt, String effectiveDate, String enterBy, String enterDate, String policyNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query query = em.createNativeQuery("update pm_scheme_details set scheme_code = '" + schemeCode + "', vendor_code='" + vendorCode + "', "
                    + "premium_amt= " + Double.parseDouble(premiumAmt) + ", insured_amt=" + Double.parseDouble(insuredAamt) + ", "
                    + "scheme_gl='" + schemeGl + "', gl_amt=" + Double.parseDouble(glAmt) + ", scheme_pl='" + schemePl + "',"
                    + "pl_amt=" + Double.parseDouble(plAmt) + ", agent_amt=" + Double.parseDouble(agentAmt) + ", effective_date='" + effectiveDate + "', "
                    + "enter_by='" + enterBy + "', enter_date='" + enterDate + "',policy_ac_no = '" + policyNo + "',update_by = '" + enterBy + "',update_date = now() where txnid = '" + txnId + "'");

            int rowAffected = query.executeUpdate();
            if (rowAffected <= 0) {
                throw new ApplicationException("Problem in data insertion");
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

    public String pmRegistrationDetailSaveUpdateVeryfy(String bntButton, String acNo, String nomName, String nomDob,
            String nomAdd, String nomRelationship, String nomAadhar, String guardianName, String guardianAdd,
            String spouseName, String spouseAadhar, String schemeCode, String disability, String disabilityDetails,
            String enrolFlag, String enrolDate, String cancelDate, String enterBy, String enterDate, String auth,
            String authBy, String brnCode, String minorFlag, String vendorCode, String pran, String pensionAmt,
            String contributionAmt, String incomeTax, String swalban, String apyState) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            float trsNo = 0f;
            ut.begin();
            if (bntButton.equalsIgnoreCase("Save")) {
                int insertResult = em.createNativeQuery("INSERT INTO pm_scheme_reg_details (ACNO, NOM_NAME, NOM_DOB, NOM_ADD, NOM_RELATIONSHIP, NOM_AADHAR, "
                        + "MINOR_FLAG, GUARDIAN_NAME, GUARDIAN_ADD, SPOUSE_NAME, SPOUSE_AADHAR, SCHEME_CODE, VENDOR_CODE,DISABILITY, DISABILITY_DETAILS, "
                        + "AUTO_DEBIT_DATE, TXN_BR_CODE, SWAVALAMBAN_SUB, PRAN, INCOME_TAX_PAYER, APY_PENSION_AMT, APY_CONTRIBUTION_AMT, ENROL_FLAG, "
                        + "ENROLL_BY,ENROL_DATE, CANCEL_DATE, AUTH, AUTH_BY,APY_STATE_CODE) "
                        + "VALUES "
                        + "('" + acNo + "', '" + nomName + "', '" + nomDob + "', '" + nomAdd + "', '" + nomRelationship + "', '" + nomAadhar + "', "
                        + "'" + minorFlag + "', '" + guardianName + "', '" + guardianAdd + "', '" + spouseName + "', '" + spouseAadhar + "', '" + schemeCode + "', "
                        + "'" + vendorCode + "', '" + disability + "', '" + disabilityDetails + "', curdate(), '" + brnCode + "', '" + swalban + "', '" + pran + "', '" + incomeTax + "', "
                        + "" + Double.parseDouble(pensionAmt) + ", " + Double.parseDouble(contributionAmt) + ", '" + enrolFlag + "', '" + enterBy + "',"
                        + " now(), " + cancelDate + ", '" + auth + "', '" + authBy + "','" + apyState + "')").executeUpdate();

                if (insertResult <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }

            } else if (bntButton.equalsIgnoreCase("Delete")) {
                int deleteResult = em.createNativeQuery("DELETE FROM pm_scheme_reg_details WHERE ACNO = '" + acNo + "' and SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "'").executeUpdate();
                if (deleteResult <= 0) {
                    throw new ApplicationException("Problem in data delation");
                }
            } else if (bntButton.equalsIgnoreCase("Update")) {
                if (schemeCode.equalsIgnoreCase("PMSBY") && vendorCode.equalsIgnoreCase("UIC")) {
                    if (enrolFlag.equalsIgnoreCase("R") && auth.equalsIgnoreCase("Y")) {
                        enrolFlag = "J";
                        cancelDate = ymd.format(new Date());
                    }
                }

                int updateResult = 0;
                if (enrolFlag.equalsIgnoreCase("C")) {
                    updateResult = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag = '" + enrolFlag + "', "
                            + "cancel_date = " + cancelDate + ",cancel_by = '" + authBy + "' where acno = '" + acNo + "' "
                            + "and scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "'").executeUpdate();
                } else {
                    updateResult = em.createNativeQuery("UPDATE pm_scheme_reg_details SET ACNO = '" + acNo + "', NOM_NAME = '" + nomName + "', NOM_DOB = '" + nomDob + "', NOM_ADD = '" + nomAdd + "', "
                            + "NOM_RELATIONSHIP = '" + nomRelationship + "', NOM_AADHAR = '" + nomAadhar + "', GUARDIAN_NAME = '" + guardianName + "', GUARDIAN_ADD = '" + guardianAdd + "',"
                            + " SPOUSE_NAME = '" + spouseName + "', SPOUSE_AADHAR = '" + spouseAadhar + "', DISABILITY = '" + disability + "',"
                            + " DISABILITY_DETAILS = '" + disabilityDetails + "', "
                            + "ENROL_FLAG = '" + enrolFlag + "', CANCEL_DATE = " + cancelDate + ",UPDATE_BY = '" + authBy + "',UPDATE_DATE=NOW() WHERE ACNO = '" + acNo + "' and SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "'").executeUpdate();
                }
                if (updateResult <= 0) {
                    throw new ApplicationException("Update problem in pm_scheme_reg_details.");
                }
            } else if (bntButton.equalsIgnoreCase("Verify")) {
                String agentAcno = "";
//                if (!agentCode.equalsIgnoreCase("")) {
//                    List list = em.createNativeQuery("select acno from ddsagent where agentcode='" + agentCode + "' and brncode='" + brnCode + "'").getResultList();
//                    if (list.isEmpty()) {
//                        throw new ApplicationException("Please fill the Account No of the Agent from Agent Master form");
//                    }
//                    Vector ele = (Vector) list.get(0);
//                    agentAcno = ele.get(0).toString();
//                }

                if (schemeCode.equals("APY")) {
                    trsNo = apySchemeTxn(brnCode, schemeCode, vendorCode, acNo, enrolFlag, contributionAmt,
                            ymd.format(curDt), enterBy);
                } else {
                    trsNo = pmSchemeTxn(brnCode, schemeCode, vendorCode, acNo, ymd.format(curDt), agentAcno, enterBy);
                }
                int verifyResult = em.createNativeQuery("update pm_scheme_reg_details set auth = 'Y',"
                        + "auth_by = '" + authBy + "' where acno = '" + acNo + "' and "
                        + "scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "'").executeUpdate();
                if (verifyResult <= 0) {
                    throw new ApplicationException("Verify problem in pm_scheme_reg_details.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                if (bntButton.equalsIgnoreCase("Verify")) {
                    List schemeDetailsList = em.createNativeQuery("select premium_amt from pm_scheme_details where effective_date  <= '" + ymd.format(curDt)
                            + "' and scheme_code='" + schemeCode + "' and vendor_code = '" + vendorCode + "' and effective_date  >= "
                            + "(select max(effective_date) from pm_scheme_details where scheme_code='" + schemeCode + "' and vendor_code = '" + vendorCode + "')").getResultList();
                    if (schemeDetailsList.isEmpty()) {
                        throw new ApplicationException("Please fille the Scheme Detail");
                    }
                    Vector schemeDetailsVec = (Vector) schemeDetailsList.get(0);
                    BigDecimal premiumAmt = new BigDecimal(schemeDetailsVec.get(0).toString());

                    smsFacade.sendTransactionalSms(SmsType.TRANSFER_WITHDRAWAL, acNo, 2, 1, premiumAmt.doubleValue(), dmy.format(curDt), "");
                }
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In Transfer Authorization." + e.getMessage());
            }
            //End here.
            return "true" + trsNo;
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String generateSSSFiles(String schemeCode, String vendor, String fileGenDt, String todayDt,
            String orgnBrCode, String enterBy, String premium, String utrNo, String utrDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List schemeDetailsList = em.createNativeQuery("select gl_amt,insured_amt,policy_ac_no,scheme_gl from pm_scheme_details "
                    + "where scheme_code='" + schemeCode + "' and vendor_code = '" + vendor + "' and effective_date  <= '" + ymd.format(dmy.parse(fileGenDt))
                    + "' and effective_date  >= (select max(effective_date) from pm_scheme_details where scheme_code='" + schemeCode
                    + "' and vendor_code = '" + vendor + "')").getResultList();
            if (schemeDetailsList.isEmpty()) {
                throw new ApplicationException("Please fill the Scheme Detail");
            }

            Vector schemeDetailsVec = (Vector) schemeDetailsList.get(0);
            String premiumAmt = schemeDetailsVec.get(0).toString();
            String insuredAmt = schemeDetailsVec.get(1).toString();
            String policyAcno = schemeDetailsVec.get(2).toString();
            String schemeCrAcno = schemeDetailsVec.get(2).toString();

            List list = em.createNativeQuery("select bank_code,aadhar_location,ifnull(apy_back_off_ref_no,'') "
                    + "as apy_back_off_ref_no,ifnull(apy_nlao_reg_no,'') as apy_nlao_reg_no,"
                    + "ifnull(apy_nlcc_reg_no,'') as apy_nlcc_reg_no from mb_sms_sender_bank_detail").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(0).toString().trim().equals("") || ele.get(1) == null
                    || ele.get(1).toString().trim().equals("")) {
                throw new ApplicationException("Please Aadhar Location and Bank Code.");
            }
            String bankCode = ele.get(0).toString().trim();
            String aadharLocation = ele.get(1).toString().trim();

            String apyBackOffRefNo = "", apyNlaoRegNo = "", apyNlccRegNo = "";
            if (schemeCode.equals("APY")) {
                apyBackOffRefNo = ele.get(2).toString().trim();
                apyNlaoRegNo = ele.get(3).toString().trim();
                if (!apyNlaoRegNo.equals("") && apyNlaoRegNo.length() != 7) {
                    throw new ApplicationException("There should be proper NLAO Reg No.");
                }
                apyNlccRegNo = ele.get(4).toString().trim();
                if (apyNlccRegNo.equals("") || apyNlccRegNo.length() != 10) {
                    throw new ApplicationException("There should be proper NLCC Reg No.");
                }
            }

            list = em.createNativeQuery("select bankname from bnkadd where alphacode = 'HO'").getResultList();
            ele = (Vector) list.get(0);
            String bankName = ele.get(0).toString().trim(); //In APY
            bankName = bankName.length() > 30 ? bankName.substring(0, 30) : bankName;

            list = em.createNativeQuery("select max(file_no)+1 as file_no from "
                    + "cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and "
                    + "file_gen_type='" + schemeCode + "'").getResultList();
            ele = (Vector) list.get(0);

            Integer fileNo = 101;
            String apyFileNo = "1";
            if (ele.get(0) != null) {
                fileNo = Integer.parseInt(ele.get(0).toString().trim());
                apyFileNo = ele.get(0).toString().trim();
            }

            if (schemeCode.equals("PMJJBY") && vendor.equals("SBI")) {
                List dataList = em.createNativeQuery("select ac.acno,ifnull(cm.aadhaar_no,''),cm.customerid,ac.curbrcode, pms.txn_br_code,"
                        + "date_format(pms.enrol_date,'%d/%m/%Y'),cm.custfullname,date_format(cm.dateofbirth,'%d/%m/%Y'),cm.gender,substring(cm.mobilenumber,1,12),"
                        + "cm.emailid,cb.description,cm.peraddressline1,cm.peraddressline2,cm.PerDistrict,rr2.ref_desc,cm.perpostalcode,pms.nom_name,"
                        + "date_format(pms.nom_dob,'%d/%m/%Y'),rr3.ref_desc,pms.nom_add,pms.guardian_name,pms.guardian_add,pms.agent_code,"
                        + "act.glhead from accountmaster ac, cbs_customer_master_detail cm, customerid ci,pm_scheme_reg_details pms,codebook cb,"
                        + "cbs_ref_rec_type rr2,accounttypemaster act,customermaster ct,cbs_ref_rec_type rr3 where "
                        + "ac.acno = ci.acno and ci.custid = cm.customerid and pms.acno = ac.acno and pms.enrol_flag = 'L' and pms.auth='Y' "
                        + "and cb.groupcode=6 and cb.code=ct.occupation and "
                        + "rr2.ref_rec_no='002' and rr2.ref_code=cm.perstatecode and ac.accttype=act.acctcode and substring(ac.acno,5,6) = ct.custno "
                        + "and ac.accttype=ct.actype and ac.curbrcode = ct.brncode and rr3.ref_rec_no='217' and rr3.ref_code=pms.nom_relationship "
                        + "and pms.SCHEME_CODE = '" + schemeCode + "' and pms.VENDOR_CODE = '" + vendor + "' and "
                        + "pms.AUTO_DEBIT_DATE <='" + ymd.format(dmy.parse(todayDt)) + "'").getResultList();
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the files.");
                }

                BigDecimal totPremium = new BigDecimal(premiumAmt).multiply(new BigDecimal(dataList.size()));

                if (totPremium.compareTo(new BigDecimal(premium)) != 0) {
                    throw new ApplicationException("Calculated and paid Premium amount does not same");
                }

                String genFileName = schemeCode.toUpperCase() + "_" + bankCode.toUpperCase() + "_" + ymdOne.format(curDt) + ".txt";
                int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type) values(" + fileNo + ",'"
                        + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','" + schemeCode + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in Files Insertion.");
                }

                FileWriter fw = new FileWriter(aadharLocation + genFileName);

                String header = "TYPE-OF-POLICY|FROM-ACCT-NO|To-ACCT-NO|AADHAR NO|BANK CUSTOMER ID|Account Home Branch|"
                        + "TXN-BANK CODE|TXN-BRANCH CODE|TRAN-DATE|JOURNAL-NO|APPLICANT-NAME|APPLICANT-DOB|Member Gender|"
                        + "INSURED-AMOUNT|PREMIUM AMOUNT|MOBILE NO|EMAIL-ADDRESS|Member Occupation|APPLICANT-ADDRESS1|"
                        + "APPLICANT-ADDRESS2|APPLICANT-ADDRESS3|APPLICANT-ADDRESS4|CITY|STATE|POST-CODE|Nominee Name|"
                        + "Nominee DOB|Relationship With Nominee|Nominee Address|Appointee Name|Appointee DOB|"
                        + "Appointee Address|KYC_Compliant|Bank_BC_Code" + "\n";
                fw.write(header);
                String details = "";
                for (int i = 0; i < dataList.size(); i++) {
                    Vector dataVect = (Vector) dataList.get(i);

                    details = bankCode + schemeCode + "|" + dataVect.get(0).toString().trim() + "|" + schemeCrAcno
                            + "|" + dataVect.get(1).toString().trim() + "|" + dataVect.get(2).toString().trim()
                            + "|" + dataVect.get(3).toString().trim() + "|" + bankCode + "|" + dataVect.get(4).toString().trim()
                            + "|" + dataVect.get(5).toString().trim() + "|" + dataVect.get(24).toString().trim()
                            + "|" + dataVect.get(6).toString().trim() + "|" + dataVect.get(7).toString().trim()
                            + "|" + dataVect.get(8).toString().trim() + "|" + insuredAmt + "|" + premiumAmt
                            + "|" + dataVect.get(9).toString().trim() + "|" + dataVect.get(10).toString().trim()
                            + "|" + dataVect.get(11).toString().trim() + "|" + dataVect.get(12).toString().trim()
                            + "|" + dataVect.get(13).toString().trim() + "|||" + dataVect.get(14).toString().trim()
                            + "|" + dataVect.get(15).toString().trim() + "|" + dataVect.get(16).toString().trim()
                            + "|" + dataVect.get(17).toString().trim() + "|" + dataVect.get(18).toString().trim()
                            + "|" + dataVect.get(19).toString().trim() + "|" + dataVect.get(20).toString().trim()
                            + "|" + dataVect.get(21).toString().trim() + "||" + dataVect.get(22).toString().trim()
                            + "|Y|" + dataVect.get(23).toString().trim() + "\n";

                    n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where acno='" + dataVect.get(0).toString().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem In Status Updation.");
                    }
                    fw.write(details);
                }
                fw.close();
            } else if (schemeCode.equals("PMJJBY") && vendor.equals("LIC")) {

                List dataList = em.createNativeQuery("select substring(bm.ifsc_code,1,11),'Urban',substring(cm.custfullname,1,50),cm.gender,date_format(cm.dateofbirth,'%Y%m%d'),"
                        + "ac.acno,cm.customerid,ifnull(cm.AADHAAR_NO,''), cm.PAN_GIRNumber,cm.PerPostalCode,substring(cm.mobilenumber,1,12),substring(cm.EmailID,1,50),"
                        + "ac.OpeningDt,date_format(pms.AUTO_DEBIT_DATE,'%Y%m%d'),substring(pms.NOM_NAME,1,50),pms.NOM_RELATIONSHIP,TIMESTAMPDIFF(YEAR, pms.NOM_DOB, NOW()),"
                        + "substring(pms.GUARDIAN_NAME,1,50) from branchmaster bm,accountmaster ac, cbs_customer_master_detail cm, customerid ci,"
                        + "pm_scheme_reg_details pms where ac.acno = ci.acno and ci.custid = cm.customerid and pms.acno = ac.acno and "
                        + "pms.enrol_flag = 'L' and pms.auth='Y' and cast(ac.CurBrCode as unsigned) = bm.brncode and pms.SCHEME_CODE = '"
                        + schemeCode + "' and pms.VENDOR_CODE = '" + vendor + "' and pms.AUTO_DEBIT_DATE <='" + ymd.format(dmy.parse(todayDt)) + "'").getResultList();

                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the files.");
                }

                BigDecimal totPremium = new BigDecimal(premiumAmt).multiply(new BigDecimal(dataList.size()));

                if (totPremium.compareTo(new BigDecimal(premium)) != 0) {
                    throw new ApplicationException("Calculated and paid Premium amount does not same");
                }
                Map<Integer, List> dataMap = new HashMap<Integer, List>();
                Integer lastFileNo = fileNo;
                if (dataList.size() > 25000) {
                    for (int i = 0; i < dataList.size(); i = i + 25000) {
                        dataMap.put(lastFileNo, dataList.subList(i, i + 25000));
                        lastFileNo = lastFileNo + 1;
                    }
                } else {
                    dataMap.put(fileNo, dataList);
                }
                utrNo = ParseFileUtil.addTrailingZeros(utrNo, 22);
                String ctlGenFileName = (utrNo + "-" + fileNo + "-" + lastFileNo + "-" + ymd.format(curDt) + ".ctl").toUpperCase();
                Integer ctlFileNo = lastFileNo + 1;
                Iterator iterator = dataMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, List> mapEntry = (Map.Entry<Integer, List>) iterator.next();

                    Integer tmpFileNo = mapEntry.getKey();

                    List tmpDataList = mapEntry.getValue();

                    String genFileName = (utrNo + "-" + tmpFileNo + "-" + ymd.format(curDt) + ".csv").toUpperCase();

                    int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                            + "file_gen_time,file_gen_brncode,file_gen_type) values(" + tmpFileNo + ",'"
                            + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                            + orgnBrCode + "','" + schemeCode + "')").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in SSS Files Insertion.");
                    }

                    FileWriter fw = new FileWriter(aadharLocation + genFileName);
                    String details = "";

                    for (int i = 0; i < tmpDataList.size(); i++) {
                        Vector dataVect = (Vector) tmpDataList.get(i);
                        if (dataVect.get(0).toString().trim().length() != 11) {
                            throw new ApplicationException("Please fill the appropriate IFSC Code.");
                        }
                        String panNo = "";

                        if (dataVect.get(8).toString().trim().length() == 10) {
                            panNo = dataVect.get(8).toString().trim();
                        }

                        details = dataVect.get(0).toString().trim() + "," + dataVect.get(1).toString().trim()
                                + "," + dataVect.get(2).toString().trim() + "," + dataVect.get(3).toString().trim()
                                + "," + dataVect.get(4).toString().trim() + "," + dataVect.get(5).toString().trim()
                                + "," + dataVect.get(6).toString().trim() + "," + dataVect.get(7).toString().trim()
                                + "," + panNo + "," + dataVect.get(9).toString().trim()
                                + "," + dataVect.get(10).toString().trim() + "," + dataVect.get(11).toString().trim()
                                + "," + dataVect.get(12).toString().trim() + "," + dataVect.get(13).toString().trim()
                                + "," + dataVect.get(14).toString().trim() + "," + dataVect.get(15).toString().trim()
                                + "," + dataVect.get(16).toString().trim() + "," + dataVect.get(17).toString().trim() + "\n";

                        n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where acno='" + dataVect.get(5).toString().trim()
                                + "' and scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In Status Updation.");
                        }
                        fw.write(details);
                    }
                    fw.close();

                    File file = new File(aadharLocation + genFileName);
                    long fileSize = 0;
                    if (file.exists()) {
                        fileSize = file.length();
                    }

                    FileWriter fw1 = new FileWriter(aadharLocation + ctlGenFileName);

                    String ctlDetails = ParseFileUtil.getParticularLengthData(policyAcno, 10) + "," + genFileName + "," + utrNo + ","
                            + ymd.format(dmy.parse(utrDate)) + "," + tmpFileNo + "," + ymd.format(curDt) + "," + tmpDataList.size() + "," + fileSize;

                    fw1.write(ctlDetails);
                    fw1.close();
                }

                int n1 = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type) values(" + ctlFileNo + ",'"
                        + ymd.format(dmy.parse(todayDt)) + "','" + ctlGenFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','" + schemeCode + "')").executeUpdate();
                if (n1 <= 0) {
                    throw new ApplicationException("Problem in SSS Files Insertion.");
                }

            } else if (schemeCode.equals("PMSBY") && vendor.equals("OIC")) {
                List nameList = em.createNativeQuery("select code from cbs_parameterinfo where name='PMSBY-OIC'").getResultList();
                if (nameList.isEmpty()) {
                    throw new ApplicationException("Please fill the File Name.");
                }
                Vector ele1 = (Vector) nameList.get(0);
                String fileNamePrefix = ele1.get(0).toString().trim();

                List dataList = em.createNativeQuery("select substring(bm.ifsc_code,1,11),substring(cm.custfullname,1,50),substring(cm.fathername,1,50),"
                        + "cm.gender,date_format(cm.dateofbirth,'%d%m%Y'),date_format(ac.openingdt,'%d%m%Y'),ac.acno,ifnull(cm.AADHAAR_NO,''),"
                        + "cm.PerPostalCode,date_format(pms.AUTO_DEBIT_DATE,'%d%m%Y'), cm.PAN_GIRNumber,substring(cm.EmailID,1,50),substring(cm.mobilenumber,1,12),"
                        + "pms.NOM_NAME,ifnull(pms.agent_code,''),substring(rr3.ref_desc,1,30),st.txn_id from branchmaster bm,accountmaster ac, "
                        + "cbs_customer_master_detail cm, customerid ci,pm_scheme_reg_details pms,sss_txn_detail st,cbs_ref_rec_type rr3 "
                        + "where ac.acno = ci.acno and ci.custid = cm.customerid and pms.acno = ac.acno and pms.enrol_flag = 'L' and "
                        + "pms.auth='Y' and cast(ac.CurBrCode as unsigned) = bm.brncode and pms.SCHEME_CODE = '" + schemeCode + "' and "
                        + "pms.VENDOR_CODE = '" + vendor + "' and pms.AUTO_DEBIT_DATE <='" + ymd.format(dmy.parse(todayDt)) + "' and "
                        + "pms.SCHEME_CODE = st.scheme_code and pms.VENDOR_CODE = st.vendor_code and pms.acno=st.acno and "
                        + "pms.auto_debit_date = st.txn_date and rr3.ref_rec_no='217' and rr3.ref_code=pms.nom_relationship").getResultList();

                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the files.");
                }

                BigDecimal totPremium = new BigDecimal(premiumAmt).multiply(new BigDecimal(dataList.size()));

                if (totPremium.compareTo(new BigDecimal(premium)) != 0) {
                    throw new ApplicationException("Calculated and paid Premium amount does not same");
                }

                String genFileName = fileNamePrefix + "_" + ymdOne.format(curDt) + "_" + fileNo + ".txt";
                int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,file_gen_time,"
                        + "file_gen_brncode,file_gen_type) values(" + fileNo + ",'" + ymd.format(dmy.parse(todayDt)) + "','" + genFileName
                        + "','" + enterBy + "',now(),'" + orgnBrCode + "','" + schemeCode + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in Files Insertion.");
                }

                FileWriter fw = new FileWriter(aadharLocation + genFileName);
                String details = "";

                for (int i = 0; i < dataList.size(); i++) {
                    Vector dataVect = (Vector) dataList.get(i);
                    if (dataVect.get(0).toString().trim().length() != 11) {
                        throw new ApplicationException("Please fill the appropriate IFSC Code.");
                    }
                    String panNo = "";

                    if (dataVect.get(8).toString().trim().length() == 10) {
                        panNo = dataVect.get(10).toString().trim();
                    }

                    details = dataVect.get(0).toString().trim() + "|" + fileNo + "|" + ymdOne.format(curDt)
                            + "|" + dataVect.get(1).toString().trim() + "|" + dataVect.get(2).toString().trim()
                            + "|" + dataVect.get(3).toString().trim() + "|" + dataVect.get(4).toString().trim()
                            + "|" + dataVect.get(5).toString().trim() + "|" + dataVect.get(6).toString().trim()
                            + "|" + dataVect.get(7).toString().trim() + "|" + dataVect.get(8).toString().trim()
                            + "|" + dataVect.get(9).toString().trim() + "|" + panNo
                            + "|" + dataVect.get(11).toString().trim() + "|" + dataVect.get(12).toString().trim()
                            + "|" + dataVect.get(13).toString().trim() + "|" + dataVect.get(14).toString().trim()
                            + "|" + dataVect.get(15).toString().trim() + "|" + dataVect.get(16).toString().trim() + "|\n";

                    n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where acno='" + dataVect.get(6).toString().trim()
                            + "' and scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem In Status Updation.");
                    }
                    fw.write(details);
                }
                fw.close();
            } else if (schemeCode.equalsIgnoreCase("APY") && vendor.equalsIgnoreCase("NSDL")) {
                List dataList = em.createNativeQuery("select pms.acno,pms.pran,pms.swavalamban_sub,pms.income_tax_payer,"
                        + "pms.apy_pension_amt,pms.apy_contribution_amt,date_format(pms.auto_debit_date,'%Y%m%d') as "
                        + "autodebitdt,date_format(pms.enrol_date,'%Y%m%d') as enrolldt,pms.nom_name,"
                        + "date_format(pms.nom_dob,'%Y%m%d') as nomdob,pms.nom_aadhar,ref1.ref_desc,"
                        + "pms.minor_flag,pms.guardian_name,pms.apy_state_code,cm.title,cm.custfullname,"
                        + "ifnull(cm.fathername,'') as fname,cm.gender,date_format(cm.dateofbirth,'%Y%m%d') as "
                        + "subdob,ifnull(cm.pan_girnumber,'') as pan,ifnull(cm.mailaddressline1,'') as coradd,"
                        + "cm.MailDistrict,cm.mailpostalcode,ifnull(cm.mobilenumber,'') as mob,cm.maritalstatus,"
                        + "pms.spouse_name,pms.spouse_aadhar,pms.txn_br_code from cbs_customer_master_detail cm,"
                        + "pm_scheme_reg_details pms,customerid ci,cbs_ref_rec_type ref1 where "
                        + "pms.scheme_code = '" + schemeCode + "' and pms.vendor_code = '" + vendor + "' and "
                        + "pms.auto_debit_date<='" + ymd.format(dmy.parse(fileGenDt)) + "' and pms.enrol_flag = 'E' and "
                        + "pms.auth = 'Y' and pms.acno = ci.acno and ci.custid = cm.customerid  and "
                        + "pms.nom_relationship = ref1.ref_code and ref1.ref_rec_no='217'").getResultList();
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the file.");
                }
                String apyRegFileName = "Apy_Reg_" + mdy.format(curDt) + "_" + ParseFileUtil.addTrailingZeros(apyFileNo, 3) + ".txt";

                int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,"
                        + "file_gen_by,file_gen_time,file_gen_brncode,file_gen_type) "
                        + "values(" + Integer.parseInt(apyFileNo) + ",'" + ymd.format(dmy.parse(todayDt)) + "','"
                        + apyRegFileName + "','" + enterBy + "',now(),'" + orgnBrCode + "','"
                        + schemeCode + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in Files Insertion.");
                }

                String totalPran = String.valueOf(dataList.size());
                //Header Preparation
                FileWriter fw = new FileWriter(aadharLocation + apyRegFileName);
                String header = "000001^FH^PRAN^A^" + mdy.format(curDt) + "^"
                        + ParseFileUtil.addTrailingZeros(apyFileNo, 3) + "^" + ParseFileUtil.addTrailingZeros(totalPran, 6) + "\n";
                fw.write(header);
                //Data preparation
                Integer srNo = 1;
                for (int i = 0; i < dataList.size(); i++) {
                    Vector dataVec = (Vector) dataList.get(i);
                    String title = dataVec.get(15).toString();
                    if (title.toUpperCase().contains("MASTER") || title.toUpperCase().contains("MR")) {
                        title = "shri";
                    } else if (title.toUpperCase().contains("MS") || title.toUpperCase().contains("BABBY")) {
                        title = "kumari";
                    } else if (title.toUpperCase().contains("MRS")) {
                        title = "Smt";
                    }
                    String subName = dataVec.get(16).toString().trim();
                    subName = subName.replaceAll("[\\W_]", " ");
                    subName = subName.length() > 90 ? subName.substring(0, 90) : subName;
                    String fatherName = dataVec.get(17).toString().trim();
                    fatherName = fatherName.replaceAll("[\\W_]", " ");
                    fatherName = fatherName.length() > 90 ? fatherName.substring(0, 90) : fatherName;
                    String gender = dataVec.get(18).toString().trim();
                    if (!(gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"))) {
                        gender = "T";
                    }
                    String pan = dataVec.get(20).toString().trim();
                    if (pan.equals("") || pan.length() != 10
                            || !ParseFileUtil.isAlphabet(pan.substring(0, 5))
                            || !ParseFileUtil.isNumeric(pan.substring(5, 9))
                            || !ParseFileUtil.isAlphabet(pan.substring(9))) {
                        pan = "";
                    }
                    String corAdd = dataVec.get(21).toString().trim();
                    corAdd = corAdd.length() > 90 ? corAdd.substring(0, 90) : corAdd;
                    String city = dataVec.get(22).toString().trim();
                    city = city.length() > 30 ? city.substring(0, 30) : city;

                    srNo = srNo + 1;
                    //Subscriber details. Bank MICR Code,Bank IFSC, Subscriber Aadhar is blank.
                    String subscriberLine = ParseFileUtil.addTrailingZeros(String.valueOf(srNo), 6) + "^FD^"
                            + ParseFileUtil.addTrailingZeros(String.valueOf(i + 1), 6) + "^"
                            + dataVec.get(1).toString() + "^^" + title + "^" + subName + "^"
                            + fatherName + "^" + apyNlaoRegNo + "^" + apyNlccRegNo + "^"
                            + gender + "^" + mdy.format(ymd.parse(dataVec.get(19).toString().trim())) + "^"
                            + pan + "^" + corAdd + "^" + city + "^" + dataVec.get(14).toString().trim() + "^IN^"
                            + dataVec.get(23).toString().trim() + "^^^^" + dataVec.get(24).toString().trim() + "^^N^"
                            + dataVec.get(25).toString().trim() + "^"
                            + dataVec.get(26).toString().trim() + "^" + dataVec.get(27).toString().trim() + "^Savings^"
                            + dataVec.get(0).toString().trim() + "^" + bankName + "^"
                            + reportCommon.getAlphacodeByBrncode(dataVec.get(28).toString().trim()) + "^^^^"
                            + dataVec.get(2).toString().trim() + "^N^" + dataVec.get(4).toString().trim() + "^"
                            + dataVec.get(5).toString().trim() + "^"
                            + String.valueOf(Integer.parseInt(dataVec.get(6).toString().trim().substring(6))) + "^"
                            + mdy.format(ymd.parse(dataVec.get(7).toString().trim())) + "^"
                            + reportCommon.getAlphacodeByBrncode(dataVec.get(28).toString().trim()) + "^"
                            + dataVec.get(3).toString().trim() + "\n";

                    n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where "
                            + "acno='" + dataVec.get(0).toString().trim() + "' and "
                            + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem In Status Updation.");
                    }

                    fw.write(subscriberLine);

                    srNo = srNo + 1;
                    String nomRelation = dataVec.get(11).toString().trim();
                    nomRelation = nomRelation.length() > 30 ? nomRelation.substring(0, 30) : nomRelation;
                    //Nominee details
                    String nomineeLine = ParseFileUtil.addTrailingZeros(String.valueOf(srNo), 6) + "^ND^1^1^"
                            + dataVec.get(8).toString().trim() + "^" + mdy.format(ymd.parse(dataVec.get(9).toString().trim()))
                            + "^" + dataVec.get(10).toString().trim() + "^" + nomRelation + "^"
                            + dataVec.get(12).toString().trim() + "^" + dataVec.get(13).toString().trim() + "\n";
                    fw.write(nomineeLine);
                }
                fw.close();
            } else if (schemeCode.equalsIgnoreCase("PMSBY") && vendor.equalsIgnoreCase("UIC")) {
                //Location category is hardcode. It can be softcode from OSS8 impl. Where location_type(In BranchMaster)
                //will use for atm profile in OSS8. Currently location category is U
                List nameList = em.createNativeQuery("select code from cbs_parameterinfo where "
                        + "name='PMSBY-UIC'").getResultList();
                if (nameList.isEmpty()) {
                    throw new ApplicationException("Please fill Bank Id in Cbs Parameterinfo.");
                }
                Vector ele1 = (Vector) nameList.get(0);
                String bankId = ele1.get(0).toString().trim();
                if (bankId.trim().equals("") || bankId.length() != 4) {
                    throw new ApplicationException("Bank Id in Cbs Parameterinfo should be of 4 digit.");
                }

                nameList = em.createNativeQuery("select ifnull(ifsc_code,'') as ifsc from branchmaster "
                        + "where alphacode='HO'").getResultList();
                if (nameList.isEmpty()) {
                    throw new ApplicationException("Please fill IFSC Code For HO in Branchmaster.");
                }
                ele1 = (Vector) nameList.get(0);
                String hoAlphaCode = ele1.get(0).toString().trim();
                if (hoAlphaCode.trim().equals("") || hoAlphaCode.length() != 11) {
                    throw new ApplicationException("Ho IFSC Code should be of 11 digit.");
                }

                List dataList = em.createNativeQuery("select pms.acno,bm.alphacode,"
                        + "cm.customerid,date_format(pms.renew_date,'%Y%m%d') as enroll_date,cm.custfullname,"
                        + "ifnull(cm.mailaddressline1,'') as coradd,cm.MailDistrict,ref2.ref_desc,cm.mailpostalcode,"
                        + "date_format(cm.dateofbirth,'%Y%m%d') as dob,cm.gender,pms.disability,pms.nom_name,"
                        + "ref3.ref_desc,pms.minor_flag,ifnull(pms.guardian_name,''),ifnull(cm.aadhaar_no,'') as "
                        + "aadhar_no,ifnull(cm.pan_girnumber,'') as pan,ifnull(cm.mobilenumber,'') as mob from "
                        + "pm_scheme_reg_details pms,branchmaster bm,cbs_customer_master_detail cm,customerid ci, "
                        + "cbs_ref_rec_type ref2, cbs_ref_rec_type ref3 where pms.acno = ci.acno "
                        + "and ci.custid = cm.customerid and cast(pms.txn_br_code as unsigned)=bm.brncode and cm.mailstatecode = ref2.ref_code "
                        + "and ref2.ref_rec_no='002' and pms.nom_relationship = ref3.ref_code and ref3.ref_rec_no='218'  and "
                        + "pms.auto_debit_date <= '" + ymd.format(dmy.parse(fileGenDt)) + "' and pms.enrol_flag = 'L' and "
                        + "pms.auth='Y'  and pms.scheme_code='" + schemeCode + "' and "
                        + "pms.vendor_code='" + vendor + "'").getResultList();
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the file.");
                }

                BigDecimal totPremium = new BigDecimal(premiumAmt).multiply(new BigDecimal(dataList.size()));

                if (totPremium.compareTo(new BigDecimal(premium)) != 0) {
                    throw new ApplicationException("Calculated and paid Premium amount does not same");
                }

                String totalRecords = String.valueOf(dataList.size());
                String uicDataFileName = "INWDATD" + bankId.trim().toUpperCase() + dmyOne.format(new Date()) + ParseFileUtil.addTrailingZeros(apyFileNo, 2) + "01.CSV";

                //Data Member File Writing.
                FileWriter fw = new FileWriter(aadharLocation + uicDataFileName);
                for (int i = 0; i < dataList.size(); i++) {
                    ele1 = (Vector) dataList.get(i);
                    String custname = ele1.get(4).toString().trim();
                    custname = custname.length() > 50 ? custname.substring(0, 50) : custname;
                    String corAdd = ele1.get(5).toString().trim();
                    corAdd = corAdd.length() > 160 ? corAdd.substring(0, 160) : corAdd;
                    String city = ele1.get(6).toString().trim();
                    city = city.length() > 40 ? city.substring(0, 40) : city;
                    String state = ele1.get(7).toString().trim();
                    state = state.length() > 35 ? state.substring(0, 35) : state;
                    String pin = ele1.get(8).toString().trim();
                    pin = pin.length() > 8 ? pin.substring(0, 8) : pin;
                    String dob = dmy.format(ymd.parse(ele1.get(9).toString().trim()));
                    String gender = ele1.get(10).toString().trim();
                    if (!(gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"))) {
                        gender = "T";
                    }
                    String disability = ele1.get(11).toString().trim();
                    disability = (disability.equals("") || disability.equalsIgnoreCase("N")) ? "No" : "Yes";
                    String nomName = ele1.get(12).toString().trim();
                    nomName = nomName.length() > 50 ? nomName.substring(0, 50) : nomName;
                    String grdName = ele1.get(15).toString().trim();
                    grdName = grdName.length() > 50 ? grdName.substring(0, 50) : grdName;
                    String pan = ele1.get(17).toString().trim();
                    pan = pan.length() > 10 ? pan.substring(0, 10) : pan;
                    String mobile = ele1.get(18).toString().trim();
                    mobile = mobile.length() > 10 ? mobile.substring(0, 10) : mobile;

                    //Here Agency Code/ BC Code is bankId at last field.
                    String row = ele1.get(0).toString().trim() + "~~" + bankId.trim().toUpperCase() + "~~"
                            + ele1.get(1).toString().trim() + "~~" + hoAlphaCode + "~~" + ele1.get(2).toString().trim()
                            + "~~" + dmy.format(ymd.parse(ele1.get(3).toString().trim())) + "~~" + custname + "~~"
                            + corAdd + "~~" + city + "~~" + state + "~~" + pin + "~~" + "U~~" + dob + "~~" + gender
                            + "~~" + disability + "~~" + nomName + "~~" + ele1.get(13).toString().trim() + "~~"
                            + grdName + "~~" + ele1.get(16).toString().trim() + "~~" + pan + "~~" + "" + "~~"
                            + mobile + "~~" + bankId + "\n";

                    int n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where "
                            + "acno='" + ele1.get(0).toString().trim() + "' and "
                            + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem In Status Updation.");
                    }

                    fw.write(row);
                }
                fw.close();

                String uicControllFile = "INWCTLD" + bankId.trim().toUpperCase() + dmyOne.format(new Date())
                        + ParseFileUtil.addTrailingZeros(apyFileNo, 2) + "01.CSV";
                //Controll File Generation
                int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,"
                        + "file_gen_by,file_gen_time,file_gen_brncode,file_gen_type,premium_amount,utr,utr_date) "
                        + "values(" + Integer.parseInt(apyFileNo) + ",'" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "'" + uicDataFileName + "|" + uicControllFile + "','" + enterBy + "',now(),'" + orgnBrCode + "',"
                        + "'" + schemeCode + "'," + premium + ",'" + utrNo + "',"
                        + "'" + ymd.format(dmy.parse(utrDate)) + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in Files Insertion.");
                }
                fw = new FileWriter(aadharLocation + uicControllFile);
                String row = uicDataFileName.substring(0, uicDataFileName.indexOf(".")).trim() + "~~"
                        + bankId.trim().toUpperCase() + "~~" + utrNo + "~~" + utrDate + "~~" + totalRecords + "~~"
                        + premium + "~~" + hoAlphaCode;
                fw.write(row);
                fw.close();

            } else if (schemeCode.equalsIgnoreCase("PMSBY") && vendor.equalsIgnoreCase("NIA")) {

                List nameList = em.createNativeQuery("select ifnull(ifsc_code,'') as ifsc from branchmaster "
                        + "where alphacode='HO'").getResultList();
                if (nameList.isEmpty()) {
                    throw new ApplicationException("Please fill IFSC Code For HO in Branchmaster.");
                }

                Vector ele1 = (Vector) nameList.get(0);
                String hoAlphaCode = ele1.get(0).toString().trim();
                if (hoAlphaCode.trim().equals("") || hoAlphaCode.length() != 11) {
                    throw new ApplicationException("Ho IFSC Code should be of 11 digit.");
                }

                List dataList = em.createNativeQuery("select pms.txn_br_code,cm.customerid,pms.acno,"
                        + "cm.custfullname,ifnull(cm.pan_girnumber,'') as pan,ifnull(cm.aadhaar_no,'') as aadhar_no,date_format(cm.dateofbirth,'%Y%m%d') as dob,"
                        + "cm.gender,substring(cm.mobilenumber,1,12)as mobile_no,cm.emailid,cm.peraddressline1,cm.peraddressline2,cm.MailDistrict,"
                        + "ref2.ref_desc as stste,cm.perpostalcode,cm.PerCountryCode,date_format(pms.enrol_date,'%Y%m%d') as enroll_date "
                        + "from pm_scheme_reg_details pms,branchmaster bm,cbs_customer_master_detail cm,customerid ci, cbs_ref_rec_type ref2, cbs_ref_rec_type ref3  "
                        + "where pms.acno = ci.acno and ci.custid = cm.customerid and cast(pms.txn_br_code as unsigned)=bm.brncode "
                        + "and cm.mailstatecode = ref2.ref_code and ref2.ref_rec_no='002' "
                        + "and pms.nom_relationship = ref3.ref_code and ref3.ref_rec_no='217'  and pms.auto_debit_date <= '" + ymd.format(dmy.parse(fileGenDt)) + "' "
                        + "and pms.enrol_flag = 'L' and pms.auth='Y'  and pms.scheme_code='" + schemeCode + "' and pms.vendor_code='" + vendor + "'").getResultList();
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the file.");
                }

                BigDecimal totPremium = new BigDecimal(premiumAmt).multiply(new BigDecimal(dataList.size()));

                if (totPremium.compareTo(new BigDecimal(premium)) != 0) {
                    throw new ApplicationException("Calculated and paid Premium amount does not same");
                }
                String totalRecords = String.valueOf(dataList.size());
                String genFileName = schemeCode.toUpperCase() + "_" + "0" + bankCode.toUpperCase() + "_" + ymdOne.format(curDt) + ".xlsx";

                int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type) values(" + fileNo + ",'"
                        + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','" + schemeCode + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in Files Insertion.");
                }

                XSSFWorkbook workbook = new XSSFWorkbook();
                //************Header Line*****************
                XSSFSheet sheet = workbook.createSheet("NEW INDIA ASSURENCE DATA");
                DataFormat fmt = workbook.createDataFormat();
                CellStyle textStyle = workbook.createCellStyle();
                textStyle.setDataFormat(fmt.getFormat("@"));
                Row row = sheet.createRow(0);

                sheet.setDefaultColumnStyle(0, textStyle);
                Cell cell = row.createCell(0);
                cell.setCellStyle(textStyle);
                cell.setCellValue("BankCode");
                sheet.autoSizeColumn(0);

                sheet.setDefaultColumnStyle(1, textStyle);
                cell = row.createCell(1);
                cell.setCellStyle(textStyle);
                cell.setCellValue("BrCode");
                sheet.autoSizeColumn(1);

                sheet.setDefaultColumnStyle(2, textStyle);
                cell = row.createCell(2);
                cell.setCellStyle(textStyle);
                cell.setCellValue("RefNumber");
                sheet.autoSizeColumn(2);

                sheet.setDefaultColumnStyle(3, textStyle);
                cell = row.createCell(3);
                cell.setCellStyle(textStyle);
                cell.setCellValue("CustomerID");
                sheet.autoSizeColumn(3);

                sheet.setDefaultColumnStyle(4, textStyle);
                cell = row.createCell(4);
                cell.setCellStyle(textStyle);
                cell.setCellValue("ACNumber");
                sheet.autoSizeColumn(4);

                sheet.setDefaultColumnStyle(5, textStyle);
                cell = row.createCell(5);
                cell.setCellStyle(textStyle);
                cell.setCellValue("SchemeType");
                sheet.autoSizeColumn(5);

                sheet.setDefaultColumnStyle(6, textStyle);
                cell = row.createCell(6);
                cell.setCellStyle(textStyle);
                cell.setCellValue("ACName");
                sheet.autoSizeColumn(6);

                sheet.setDefaultColumnStyle(7, textStyle);
                cell = row.createCell(7);
                cell.setCellStyle(textStyle);
                cell.setCellValue("PANNo");
                sheet.autoSizeColumn(7);

                sheet.setDefaultColumnStyle(8, textStyle);
                cell = row.createCell(8);
                cell.setCellStyle(textStyle);
                cell.setCellValue("AadharNo");
                sheet.autoSizeColumn(8);

                sheet.setDefaultColumnStyle(9, textStyle);
                cell = row.createCell(9);
                cell.setCellStyle(textStyle);
                cell.setCellValue("DOB");
                sheet.autoSizeColumn(9);

                sheet.setDefaultColumnStyle(10, textStyle);
                cell = row.createCell(10);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Gender");
                sheet.autoSizeColumn(10);

                sheet.setDefaultColumnStyle(11, textStyle);
                cell = row.createCell(11);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Mobile");
                sheet.autoSizeColumn(11);

                sheet.setDefaultColumnStyle(12, textStyle);
                cell = row.createCell(12);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Email");
                sheet.autoSizeColumn(12);

                sheet.setDefaultColumnStyle(13, textStyle);
                cell = row.createCell(13);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Addr1");
                sheet.autoSizeColumn(13);

                sheet.setDefaultColumnStyle(14, textStyle);
                cell = row.createCell(14);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Addr2");
                sheet.autoSizeColumn(14);

                sheet.setDefaultColumnStyle(15, textStyle);
                cell = row.createCell(15);
                cell.setCellStyle(textStyle);
                cell.setCellValue("City");

                sheet.autoSizeColumn(15);

                sheet.setDefaultColumnStyle(16, textStyle);
                cell = row.createCell(16);
                cell.setCellStyle(textStyle);
                cell.setCellValue("State");
                sheet.autoSizeColumn(16);

                sheet.setDefaultColumnStyle(17, textStyle);
                cell = row.createCell(17);
                cell.setCellStyle(textStyle);
                cell.setCellValue("PinCode");
                sheet.autoSizeColumn(17);

                sheet.setDefaultColumnStyle(18, textStyle);
                cell = row.createCell(18);
                cell.setCellStyle(textStyle);
                cell.setCellValue("CountryCode");
                sheet.autoSizeColumn(18);

                sheet.setDefaultColumnStyle(19, textStyle);
                cell = row.createCell(19);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Premium");
                sheet.autoSizeColumn(19);

                sheet.setDefaultColumnStyle(20, textStyle);
                cell = row.createCell(20);
                cell.setCellStyle(textStyle);
                cell.setCellValue("ServiceTax");
                sheet.autoSizeColumn(20);

                sheet.setDefaultColumnStyle(21, textStyle);
                cell = row.createCell(21);
                cell.setCellStyle(textStyle);
                cell.setCellValue("Cess");
                sheet.autoSizeColumn(21);

                sheet.setDefaultColumnStyle(22, textStyle);
                cell = row.createCell(22);
                cell.setCellStyle(textStyle);
                cell.setCellValue("TotalPremium");
                sheet.autoSizeColumn(22);

                sheet.setDefaultColumnStyle(23, textStyle);
                cell = row.createCell(23);
                cell.setCellStyle(textStyle);
                cell.setCellValue("TranID");
                sheet.autoSizeColumn(23);

                sheet.setDefaultColumnStyle(24, textStyle);
                cell = row.createCell(24);
                cell.setCellStyle(textStyle);
                cell.setCellValue("TranDate");
                sheet.autoSizeColumn(24);

                sheet.setDefaultColumnStyle(25, textStyle);
                cell = row.createCell(25);
                cell.setCellStyle(textStyle);
                cell.setCellValue("DGHFlag");
                sheet.autoSizeColumn(25);

                sheet.setDefaultColumnStyle(26, textStyle);
                cell = row.createCell(26);
                cell.setCellStyle(textStyle);
                cell.setCellValue("VendorCode");
                sheet.autoSizeColumn(26);

                int rownum = 1;
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);

                    String brCode = element.get(0).toString().trim();
                    String custId = element.get(1).toString();
                    String acNo = element.get(2).toString();
                    String custname = element.get(3).toString().trim();
                    String panNo = element.get(4).toString().trim();
                    if (panNo.equalsIgnoreCase("")) {
                        panNo = "0";
                    }
                    String aadhar = element.get(5).toString().trim();
                    if (aadhar.equalsIgnoreCase("")) {
                        aadhar = "0";
                    }
                    String dob = dmy.format(ymd.parse(element.get(6).toString().trim()));
                    String gender = element.get(7).toString().trim();

                    if (!(gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"))) {
                        gender = "T";
                    }

                    if (gender.equalsIgnoreCase("M")) {
                        gender = "Male";
                    } else if (gender.equalsIgnoreCase("F")) {
                        gender = "Female";
                    }

                    String mobileNo = element.get(8).toString().trim();
                    mobileNo = mobileNo.length() > 10 ? mobileNo.substring(0, 10) : mobileNo;
                    String email = element.get(9).toString().trim();
                    String Add1 = element.get(10).toString().trim();
                    String Add2 = element.get(11).toString().trim();
                    String city = element.get(12).toString().trim();
                    String state = element.get(13).toString().trim();
                    String pinCode = element.get(14).toString().trim();
                    String countryCode = element.get(15).toString().trim();
                    // String tranDate = element.get(16).toString().trim();

                    List txnList = em.createNativeQuery("select txn_id,date_format(txn_date,'%d/%m/%Y') from sss_txn_detail where acno = '" + acNo + "'").getResultList();
                    Vector txnv = (Vector) txnList.get(0);

                    String refrenceNo = "NIA" + hoAlphaCode + acNo;
                    String dghFlag = "N";
                    String vendorCode = "0";
                    String cess = "0";
                    String servicesTax = "0";
                    String tranId = txnv.get(0).toString();
                    String tranDate = txnv.get(1).toString();
                    BigDecimal totalPremiumAmt = new BigDecimal("0");

                    totalPremiumAmt = totalPremiumAmt.add(new BigDecimal(premiumAmt)).add(new BigDecimal(servicesTax).add(new BigDecimal(cess)));

                    row = sheet.createRow(rownum++);

                    cell = row.createCell(0);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(bankCode);
                    sheet.autoSizeColumn(0);

                    cell = row.createCell(1);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(brCode);
                    sheet.autoSizeColumn(1);

                    cell = row.createCell(2);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(refrenceNo);
                    sheet.autoSizeColumn(2);

                    cell = row.createCell(3);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(custId);
                    sheet.autoSizeColumn(3);

                    cell = row.createCell(4);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(acNo);
                    sheet.autoSizeColumn(4);

                    cell = row.createCell(5);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(schemeCode);
                    sheet.autoSizeColumn(5);

                    cell = row.createCell(6);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(custname);
                    sheet.autoSizeColumn(6);

                    cell = row.createCell(7);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(panNo);
                    sheet.autoSizeColumn(7);

                    cell = row.createCell(8);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(aadhar);
                    sheet.autoSizeColumn(8);

                    cell = row.createCell(9);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(dob);
                    sheet.autoSizeColumn(9);

                    cell = row.createCell(10);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(gender);
                    sheet.autoSizeColumn(10);

                    cell = row.createCell(11);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(mobileNo);
                    sheet.autoSizeColumn(11);

                    cell = row.createCell(12);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(email);
                    sheet.autoSizeColumn(12);

                    cell = row.createCell(13);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(Add1);
                    sheet.autoSizeColumn(13);

                    cell = row.createCell(14);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(Add2);
                    sheet.autoSizeColumn(14);

                    cell = row.createCell(15);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(city);
                    sheet.autoSizeColumn(15);

                    cell = row.createCell(16);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(state);
                    sheet.autoSizeColumn(16);

                    cell = row.createCell(17);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(pinCode);
                    sheet.autoSizeColumn(17);

                    cell = row.createCell(18);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(countryCode);
                    sheet.autoSizeColumn(18);

                    cell = row.createCell(19);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(premiumAmt);
                    sheet.autoSizeColumn(19);

                    cell = row.createCell(20);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(servicesTax);
                    sheet.autoSizeColumn(20);

                    cell = row.createCell(21);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(cess);
                    sheet.autoSizeColumn(21);

                    cell = row.createCell(22);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(totalPremiumAmt.toString());
                    sheet.autoSizeColumn(22);

                    cell = row.createCell(23);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(tranId);
                    sheet.autoSizeColumn(23);

                    cell = row.createCell(24);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(tranDate);
                    sheet.autoSizeColumn(24);

                    cell = row.createCell(25);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(dghFlag);
                    sheet.autoSizeColumn(25);

                    cell = row.createCell(26);
                    cell.setCellStyle(textStyle);
                    cell.setCellValue(vendorCode);
                    sheet.autoSizeColumn(26);

                    int u = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where "
                            + "acno='" + acNo + "' and "
                            + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                    if (u <= 0) {
                        throw new ApplicationException("Problem In Status Updation.");
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(aadharLocation + genFileName));
                workbook.write(out);
                out.close();
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "true";
    }

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fileShowDt) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and"
                    + " file_gen_type='" + fileType + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                Vector ele = (Vector) list.get(i);

                String fileName = ele.get(2).toString().trim();
                String[] splitArray = fileName.split("\\|");

                if (splitArray.length == 1) { //Others
                    dto.setFileNo(new BigInteger(ele.get(0).toString()));
                    dto.setFileGenDt(ele.get(1).toString());
                    dto.setFileName(ele.get(2).toString());
                    dto.setFileGenBy(ele.get(3).toString());

                    dataList.add(dto);
                } else if (splitArray.length == 2) { //PMSBY and UIC case
                    dto.setFileNo(new BigInteger(ele.get(0).toString()));
                    dto.setFileGenDt(ele.get(1).toString());
                    dto.setFileName(splitArray[0].trim());
                    dto.setFileGenBy(ele.get(3).toString());

                    dataList.add(dto);

                    dto = new NpciFileDto();
                    dto.setFileNo(new BigInteger(ele.get(0).toString()));
                    dto.setFileGenDt(ele.get(1).toString());
                    dto.setFileName(splitArray[1].trim());
                    dto.setFileGenBy(ele.get(3).toString());

                    dataList.add(dto);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String calculatePremium(String schemeCode, String vendor, String fileGenDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List preAmtList = em.createNativeQuery("select gl_amt from pm_scheme_details "
                    + "where effective_date  <= '" + ymd.format(dmy.parse(fileGenDt)) + "' and scheme_code='" + schemeCode + "' and vendor_code = '" + vendor
                    + "' and effective_date  >= (select max(effective_date) from pm_scheme_details where scheme_code='" + schemeCode
                    + "' and vendor_code = '" + vendor + "')").getResultList();
            if (preAmtList.isEmpty()) {
                throw new ApplicationException("Please fille the Scheme Detail");
            }

            Vector premiumVec = (Vector) preAmtList.get(0);
            String premiumAmt = premiumVec.get(0).toString();

            List totalAcnoList = em.createNativeQuery("select acno from pm_scheme_reg_details where scheme_code='" + schemeCode
                    + "' and vendor_code = '" + vendor + "' and enrol_flag='E' and auth='Y' and auto_debit_date<='" + ymd.format(dmy.parse(fileGenDt)) + "'").getResultList();
            if (totalAcnoList.isEmpty()) {
                throw new ApplicationException("There is no pending registration for payment.");
            }
            BigDecimal totalPremium = new BigDecimal(0);
            int record = 0;
            for (int i = 0; i < totalAcnoList.size(); i++) {
                Vector acnoVec = (Vector) totalAcnoList.get(i);
                String acno = acnoVec.get(0).toString().trim();
                totalPremium = totalPremium.add(new BigDecimal(Double.parseDouble(premiumAmt)));
                record = record + 1;
                int n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='L' where acno='" + acno
                        + "' and scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Status Updation.");
                }
            }
            ut.commit();
            return nf.format(totalPremium.doubleValue()) + "@#" + record;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List getMaxEffectDate() throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(date_format(max(effective_date),'%d/%m/%Y'),'') from pm_scheme_details").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private float pmSchemeTxn(String orgnBrCode, String schemeCode, String vendorCode, String acno,
            String fileGenDt, String agentAcNo, String enterBy) throws ApplicationException {
        try {
            List schemeDetailsList = em.createNativeQuery("select premium_amt,scheme_gl,gl_amt,scheme_pl,"
                    + "pl_amt,agent_amt from pm_scheme_details where effective_date  <= '" + fileGenDt + "' and "
                    + "scheme_code='" + schemeCode + "' and vendor_code = '" + vendorCode + "' and "
                    + "effective_date  >= (select max(effective_date) from pm_scheme_details where "
                    + "scheme_code='" + schemeCode + "' and vendor_code = '" + vendorCode + "')").getResultList();
            if (schemeDetailsList.isEmpty()) {
                throw new ApplicationException("Please fille the Scheme Detail");
            }
            Vector schemeDetailsVec = (Vector) schemeDetailsList.get(0);
            BigDecimal premiumAmt = new BigDecimal(schemeDetailsVec.get(0).toString());

            String insuranceHead = schemeDetailsVec.get(1).toString();
            BigDecimal insuranceAmt = new BigDecimal(schemeDetailsVec.get(2).toString());

            String plHead = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + schemeDetailsVec.get(3).toString() + "01";
            BigDecimal plAmt = new BigDecimal(schemeDetailsVec.get(4).toString());

            BigDecimal agentAmt = new BigDecimal(schemeDetailsVec.get(5).toString());

            if (agentAcNo.equals("")) {
                List list = em.createNativeQuery("select ifnull(code,1) from parameterinfo_report where reportname='PMSSS-PL-HEAD'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define PL head in parameterinfo_report");
                }
                String query = "";
                Vector codeVec = (Vector) list.get(0);
                if (Integer.parseInt(codeVec.get(0).toString()) == 1) {
                    query = "select acno from abb_parameter_info where purpose='PMSSS-PL-HEAD' and flag='A'";
                } else {
                    query = "select acno from abb_parameter_info where purpose='" + schemeCode + "-PL-HEAD' and flag='A'";
                }
                list = em.createNativeQuery(query).getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define PL head in abb_parameter_info");
                }
                Vector headVec = (Vector) list.get(0);
                agentAcNo = orgnBrCode + headVec.get(0).toString();
            }
            String msg = "True";
            float trsNo = ftsPostingFacade.getTrsNo();

            int insertResult = em.createNativeQuery("INSERT INTO sss_txn_detail (txn_id, scheme_code, vendor_code, acno, status, amount, "
                    + "enter_by, txn_date, tran_time, trsn_no) VALUES ('" + ymdms.format(new Date()) + "', '" + schemeCode + "', '"
                    + vendorCode + "', '" + acno + "', 'E'," + premiumAmt + ", '" + enterBy + "', '" + fileGenDt + "', now(), "
                    + trsNo + ");").executeUpdate();

            if (insertResult <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }

            if (insuranceHead.substring(0, 2).equals(orgnBrCode)) {
                String details = schemeCode + " Premium deduction";

                float recNo = ftsPostingFacade.getRecNo();

                msg = ftsPostingFacade.ftsPosting43CBS(acno, 2, 1, premiumAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
                        acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
                recNo = ftsPostingFacade.getRecNo();
                details = schemeCode + " Insurance Premium for " + acno;

                msg = ftsPostingFacade.ftsPosting43CBS(insuranceHead, 2, 0, insuranceAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
                        acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
                //if amount does not zero
                if (plAmt.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " Administrative expenses for " + acno;

                    msg = ftsPostingFacade.ftsPosting43CBS(plHead, 2, 0, plAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
                            acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                    if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                }
                //if amount does not zero
                if (agentAmt.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " for " + acno;

                    msg = ftsPostingFacade.ftsPosting43CBS(agentAcNo, 2, 0, agentAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
                            acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                    if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                }
            } else {
                String details = schemeCode + " Premium deduction";
                float recNo = ftsPostingFacade.getRecNo();

                msg = interBranchFacade.cbsPostingCx(acno, 1, fileGenDt, premiumAmt.doubleValue(), 0f, 2, details, 0f, "A", "",
                        "", 3, 0f, recNo, 68, acno.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAccountNature(acno), acno, 0, premiumAmt.doubleValue(), "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                recNo = ftsPostingFacade.getRecNo();
                details = schemeCode + " Insurance Premium for " + acno;

                msg = interBranchFacade.cbsPostingSx(insuranceHead, 0, fileGenDt, insuranceAmt.doubleValue(), 0f, 2, details,
                        0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                //if amount does not zero
                if (plAmt.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " Administrative expenses for " + acno;

                    msg = interBranchFacade.cbsPostingCx(plHead, 0, fileGenDt, plAmt.doubleValue(), 0f, 2, details,
                            0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
                //if amount does not zero
                if (agentAmt.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " for " + acno;

                    msg = interBranchFacade.cbsPostingCx(agentAcNo, 0, fileGenDt, agentAmt.doubleValue(), 0f, 2, details,
                            0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
            }
            ftsPostingFacade.lastTxnDateUpdation(acno);
            return trsNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private float apySchemeTxn(String orgnBrCode, String schemeCode, String vendorCode,
            String acNo, String enrollFlag, String contributionAmt, String regDt,
            String enterBy) throws ApplicationException {
        try {
            List schemeDetailsList = em.createNativeQuery("select scheme_gl from pm_scheme_details where "
                    + "effective_date  <= '" + regDt + "' and scheme_code='" + schemeCode + "' and "
                    + "vendor_code = '" + vendorCode + "' and effective_date  >= (select max(effective_date) "
                    + "from pm_scheme_details where scheme_code='" + schemeCode + "' and "
                    + "vendor_code = '" + vendorCode + "')").getResultList();
            if (schemeDetailsList.isEmpty()) {
                throw new ApplicationException("Please fill the Scheme Detail");
            }
            Vector schemeDetailsVec = (Vector) schemeDetailsList.get(0);
            String apyHead = schemeDetailsVec.get(0).toString();

            String msg = "True";
            float trsNo = ftsPostingFacade.getTrsNo();

            int insertResult = em.createNativeQuery("INSERT INTO sss_txn_detail (txn_id, scheme_code, "
                    + "vendor_code, acno, status, amount, enter_by, txn_date, tran_time, trsn_no) "
                    + "VALUES ('" + ymdms.format(new Date()) + "', '" + schemeCode + "', "
                    + "'" + vendorCode + "', '" + acNo + "', '" + enrollFlag + "'," + new BigDecimal(contributionAmt) + ","
                    + " '" + enterBy + "', '" + regDt + "', now(), " + trsNo + ");").executeUpdate();

            if (insertResult <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            if (apyHead.substring(0, 2).equals(orgnBrCode)) {
                String details = schemeCode + " Scheme deduction";

                float recNo = ftsPostingFacade.getRecNo();

                msg = ftsPostingFacade.ftsPosting43CBS(acNo, 2, 1, Double.parseDouble(contributionAmt), regDt,
                        regDt, enterBy, orgnBrCode, acNo.substring(0, 2), 68, details, trsNo, recNo, 0,
                        "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
                recNo = ftsPostingFacade.getRecNo();
                details = schemeCode + " Scheme deduction for " + acNo;

                msg = ftsPostingFacade.ftsPosting43CBS(apyHead, 2, 0, Double.parseDouble(contributionAmt),
                        regDt, regDt, enterBy, orgnBrCode, acNo.substring(0, 2), 68, details, trsNo,
                        recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
            } else {
                String details = schemeCode + " Scheme deduction";
                float recNo = ftsPostingFacade.getRecNo();

                msg = interBranchFacade.cbsPostingCx(acNo, 1, regDt, Double.parseDouble(contributionAmt), 0f, 2,
                        details, 0f, "A", "", "", 3, 0f, recNo, 68, acNo.substring(0, 2), orgnBrCode,
                        enterBy, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAccountNature(acNo), acNo,
                        0, Double.parseDouble(contributionAmt), "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                recNo = ftsPostingFacade.getRecNo();
                details = schemeCode + " Scheme deduction for " + acNo;

                msg = interBranchFacade.cbsPostingSx(apyHead, 0, regDt, Double.parseDouble(contributionAmt), 0f, 2,
                        details, 0f, "A", "", "", 3, 0f, recNo, 68, apyHead.substring(0, 2), orgnBrCode,
                        enterBy, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }
            ftsPostingFacade.lastTxnDateUpdation(acNo);
            return trsNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List chkSSSAcReg(String schemeCode, String vendorCode, String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from pm_scheme_reg_details where SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "'and acno ='" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public double getPremiumAmt(String schemeCode, String vendorCode) throws ApplicationException {
        try {
            List balList = em.createNativeQuery("select ifnull(premium_amt,0) from pm_scheme_details  where scheme_code = '" + schemeCode + "'and vendor_code = '" + vendorCode + "'").getResultList();
            if (!balList.isEmpty()) {
                Vector vtr = (Vector) balList.get(0);
                return new Double(vtr.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return 0.0;
    }

    public List<SSSRegistrationPojo> getSSSReportData(String brCode, String schemeCode, String vendorCode, String frDt, String toDt, String status, String genType, String repType) throws ApplicationException {
        List<SSSRegistrationPojo> dataList = new ArrayList<SSSRegistrationPojo>();
        List result = new ArrayList();
        try {
            String dateComp = "";
            if (status.equalsIgnoreCase("Re")) {
                dateComp = "and date_format(p.renew_date,'%Y%m%d')";
                status = "R";
            } else {
                dateComp = "and date_format(p.enrol_date,'%Y%m%d')";
            }

            if (brCode.equalsIgnoreCase("0A")) {
                if (status.equalsIgnoreCase("A")) {
                    if (genType.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where p.scheme_code = '" + schemeCode + "' "
                                + "and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "'"
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where p.scheme_code = '" + schemeCode + "' "
                                + "and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid and b.gender = '" + genType + "' "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "'"
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    }
//                    result = em.createNativeQuery("select ACNO,NOM_NAME,SCHEME_CODE,VENDOR_CODE,ENROL_FLAG,date_format(ENROL_DATE,'%d/%m/%Y') from pm_scheme_reg_details "
//                            + "where SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "'order by enrol_flag").getResultList();
                } else {
                    if (genType.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where p.scheme_code = '" + schemeCode + "' "
                                + "and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "' and enrol_flag = '" + status + "' "
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where p.scheme_code = '" + schemeCode + "' "
                                + "and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid and b.gender = '" + genType + "' "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "' and enrol_flag = '" + status + "' "
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    }
//                    result = em.createNativeQuery("select ACNO,NOM_NAME,SCHEME_CODE,VENDOR_CODE,ENROL_FLAG,date_format(ENROL_DATE,'%d/%m/%Y') from pm_scheme_reg_details "
//                            + "where SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "' and ENROL_FLAG = '" + status + "'order by enrol_flag").getResultList();
                }
            } else {
                if (status.equalsIgnoreCase("A")) {
                    if (genType.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where p.txn_br_code = '" + brCode + "' "
                                + "and p.scheme_code = '" + schemeCode + "' and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "'"
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where p.txn_br_code = '" + brCode + "' "
                                + "and p.scheme_code = '" + schemeCode + "' and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid "
                                + "and b.gender = '" + genType + "' " + dateComp + " between  '" + frDt + "' and '" + toDt + "'"
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    }
//                    result = em.createNativeQuery("select ACNO,NOM_NAME,SCHEME_CODE,VENDOR_CODE,ENROL_FLAG,date_format(ENROL_DATE,'%d/%m/%Y') from pm_scheme_reg_details "
//                            + "where TXN_BR_CODE = '" + brCode + "' and SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "'order by enrol_flag").getResultList();
                } else {
                    if (genType.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where txn_br_code = '" + brCode + "' and p.scheme_code = '" + schemeCode + "' "
                                + "and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "' and enrol_flag = '" + status + "' "
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select p.acno,b.custfullname,b.fathername,date_format(b.DateOfBirth,'%d/%m/%Y'),b.PerAddressLine1,"
                                + "b.gender,p.nom_name,p.scheme_code,p.vendor_code,p.enrol_flag,date_format(p.enrol_date,'%d/%m/%Y'),ifnull(date_format(p.renew_date,'%d/%m/%Y'),'') "
                                + "from pm_scheme_reg_details p,cbs_customer_master_detail b,customerid c where txn_br_code = '" + brCode + "' and p.scheme_code = '" + schemeCode + "' "
                                + "and p.vendor_code = '" + vendorCode + "' and p.acno = c.acno and cast(b.customerid as unsigned) = c.custid and b.gender = '" + genType + "' "
                                + " " + dateComp + " between  '" + frDt + "' and '" + toDt + "' and enrol_flag = '" + status + "' "
                                + "order by p.enrol_flag,b.gender,p.acno").getResultList();
                    }

//                    result = em.createNativeQuery("select ACNO,NOM_NAME,SCHEME_CODE,VENDOR_CODE,ENROL_FLAG,date_format(ENROL_DATE,'%d/%m/%Y') from pm_scheme_reg_details "
//                            + "where TXN_BR_CODE = '" + brCode + "' and SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "' and ENROL_FLAG = '" + status + "'order by enrol_flag").getResultList();

                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    SSSRegistrationPojo pojo = new SSSRegistrationPojo();
                    String acno = vtr.get(0).toString();
                    pojo.setAcNo(acno);
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setFatherName(vtr.get(2).toString());
                    pojo.setDob(vtr.get(3).toString());
                    pojo.setAddr(vtr.get(4).toString());
                    pojo.setGender(vtr.get(5).toString().equalsIgnoreCase("F") ? "Female" : vtr.get(5).toString().equalsIgnoreCase("M") ? "Male" : vtr.get(5).toString().equalsIgnoreCase("O") ? "Other" : "");
                    pojo.setNomName(vtr.get(6).toString());
                    pojo.setScheme(vtr.get(7).toString());
                    pojo.setVendor(vtr.get(8).toString());
                    pojo.setEnrollFlag(vtr.get(9).toString().equalsIgnoreCase("E") ? "Pending" : vtr.get(9).toString().equalsIgnoreCase("L") ? "Premium Calculate" : vtr.get(9).toString().equalsIgnoreCase("R") ? "Registered" : "");
                    pojo.setEnrollDt(vtr.get(10).toString());
                    pojo.setRenewDt(vtr.get(11).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }

    public List getSSSDetail(String schemeCode, String vendorCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select txnid,scheme_code, vendor_code, premium_amt, insured_amt, scheme_gl, gl_amt, scheme_pl, pl_amt,agent_amt, "
                    + "date_format(effective_date,'%d/%m/%Y'), enter_by, date_format(enter_date,'%d/%m/%Y'),policy_ac_no,auto_debit_freq "
                    + "from pm_scheme_details where scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public double getContributationAmount(int age, String pensionAmount) throws ApplicationException {
        try {
            List balList = em.createNativeQuery("select contribution_amount from apy_pension_slab where age = " + age + " and pension_amt = '" + pensionAmount + "'").getResultList();
            Vector vtr = (Vector) balList.get(0);
            return new Double(vtr.get(0).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<SSSRegistrationPojo> getSSSReportSummaryData(String brCode, String schemeCode, String vendorCode, String frDt, String toDt, String status) throws ApplicationException {
        List<SSSRegistrationPojo> dataList = new ArrayList<SSSRegistrationPojo>();
        List result = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                if (status.equalsIgnoreCase("A")) {
                    result = em.createNativeQuery("select count(*),enrol_flag from pm_scheme_reg_details where SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "' group by enrol_flag").getResultList();
                } else {
                    result = em.createNativeQuery("select count(*),enrol_flag from pm_scheme_reg_details where SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "' and ENROL_FLAG = '" + status + "' group by enrol_flag").getResultList();
                }
            } else {
                if (status.equalsIgnoreCase("A")) {
                    result = em.createNativeQuery("select count(*),enrol_flag from pm_scheme_reg_details where TXN_BR_CODE = '" + brCode + "' and SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "' group by enrol_flag").getResultList();
                } else {
                    result = em.createNativeQuery("select count(*),enrol_flag from pm_scheme_reg_details where TXN_BR_CODE = '" + brCode + "' and SCHEME_CODE = '" + schemeCode + "' and VENDOR_CODE = '" + vendorCode + "' and date_format(ENROL_DATE,'%Y%m%d') between  '" + frDt + "' and '" + toDt + "' and ENROL_FLAG = '" + status + "' group by enrol_flag").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    SSSRegistrationPojo pojo = new SSSRegistrationPojo();
                    pojo.setScheme(schemeCode.equalsIgnoreCase("PMJJBY") ? "JEEVAN JYOTI BIMA YOJANA" : schemeCode.equalsIgnoreCase("PMSBY") ? "SURAKSHA BIMA YOJANA" : schemeCode.equalsIgnoreCase("APY") ? "ATAL PENSION YOJNA" : "");
                    pojo.setVendor(vendorCode.equalsIgnoreCase("LIC") ? "LIC OF INDIA" : vendorCode.equalsIgnoreCase("NSDL") ? "NSDL" : vendorCode.equalsIgnoreCase("OIC") ? "ORIENTAL INSURANCE" : "");
                    pojo.setRecord(Integer.parseInt(vtr.get(0).toString()));
                    pojo.setEnrollFlag(vtr.get(1).toString().equalsIgnoreCase("E") ? "Pending" : vtr.get(1).toString().equalsIgnoreCase("L") ? "Premium Calculate" : "Registered");
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String uploadPmSBYRejection(String schemeCode, String vendor, String ctrlFileId, String upldFileId,
            List<SSSRejectDto> inputList, String uploadingUserName, String todayDt) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            if (inputList.isEmpty()) {
                throw new Exception("There is no data to upload");
            }
            ut.begin();
            List list = em.createNativeQuery("select scheme_code from pm_scheme_rejection_details where "
                    + "uploaded_file_name='" + upldFileId + "'").getResultList();
            if (!list.isEmpty()) {
                throw new Exception("This file has been already uploaded");
            }
            for (int i = 0; i < inputList.size(); i++) {
                SSSRejectDto dto = inputList.get(i);
                int n = em.createNativeQuery("INSERT INTO pm_scheme_rejection_details(scheme_code,vendor_code,acno,"
                        + "bank_br_name,bank_br_ifsc,cust_id,enroll_date,custname,mail_address,city,state,postal_code,"
                        + "location_category,dob,gender,disability,nom_name,nom_relation,guardian_name,aadhar_no,pan_no,"
                        + "account_email,account_mobile,agency_code,uploaded_file_name,actual_file_name,file_status,"
                        + "reason_code,reason,update_by,update_date,update_time) VALUES('" + schemeCode.toUpperCase() + "',"
                        + "'" + vendor.toUpperCase() + "','" + dto.getAcno() + "','" + dto.getBrName() + "',"
                        + "'" + dto.getBrIfsc() + "','" + dto.getCustId() + "','" + ymd.format(dmy.parse(dto.getEnrollDt())) + "',"
                        + "'" + dto.getName() + "','" + dto.getMailAdd() + "','" + dto.getCity() + "',"
                        + "'" + dto.getState() + "','" + dto.getPin() + "','" + dto.getLocCategory() + "',"
                        + "'" + ymd.format(dmy.parse(dto.getDob())) + "','" + dto.getGender() + "','" + dto.getDisability() + "',"
                        + "'" + dto.getNomName() + "','" + dto.getNomRel() + "','" + dto.getGuardianName() + "',"
                        + "'" + dto.getAadharNo() + "','" + dto.getPan() + "','" + dto.getEmail() + "',"
                        + "'" + dto.getMobile() + "','" + dto.getAgencyCode() + "','" + upldFileId + "',"
                        + "'" + ctrlFileId + "','U','','" + dto.getErrorDesc() + "','" + uploadingUserName + "',"
                        + "'" + ymd.format(dmy.parse(todayDt)) + "',now())").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Uploading");
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

    public String generateSSSReturnFiles(String schemeCode, String vendor, String fileGenDt,
            String ctrlFile, String todayDt, String enterBy, String uploadBrCode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (schemeCode.equalsIgnoreCase("PMSBY") && vendor.equalsIgnoreCase("UIC")) {
                List dataList = em.createNativeQuery("select acno from pm_scheme_rejection_details where "
                        + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "' and "
                        + "actual_file_name='" + ctrlFile + "' and update_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                        + "file_status='U'").getResultList();
                if (dataList.isEmpty()) {
                    throw new Exception("There is no data to generate the return file.");
                }
                //Updation Checking.
                for (int i = 0; i < dataList.size(); i++) {
                    Vector ele = (Vector) dataList.get(i);
                    String acno = ele.get(0).toString().trim();

                    List list = em.createNativeQuery("select acno from pm_scheme_reg_details where "
                            + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "' and "
                            + "acno='" + acno + "' and enrol_flag='J' and auth='Y'").getResultList();
                    if (list.isEmpty()) {
                        throw new Exception("A/c number: " + acno + " was not updated.");
                    }
                }
                //Required Fields Value.
                List nameList = em.createNativeQuery("select code from cbs_parameterinfo where "
                        + "name='PMSBY-UIC'").getResultList();
                if (nameList.isEmpty()) {
                    throw new ApplicationException("Please fill Bank Id in Cbs Parameterinfo.");
                }
                Vector ele1 = (Vector) nameList.get(0);
                String bankId = ele1.get(0).toString().trim();
                if (bankId.trim().equals("") || bankId.length() != 4) {
                    throw new ApplicationException("Bank Id in Cbs Parameterinfo should be of 4 digit.");
                }

                nameList = em.createNativeQuery("select ifnull(ifsc_code,'') as ifsc from branchmaster "
                        + "where alphacode='HO'").getResultList();
                if (nameList.isEmpty()) {
                    throw new ApplicationException("Please fill IFSC Code For HO in Branchmaster.");
                }
                ele1 = (Vector) nameList.get(0);
                String hoAlphaCode = ele1.get(0).toString().trim();
                if (hoAlphaCode.trim().equals("") || hoAlphaCode.length() != 11) {
                    throw new ApplicationException("Ho IFSC Code should be of 11 digit.");
                }

                nameList = em.createNativeQuery("select aadhar_location from mb_sms_sender_bank_detail").getResultList();
                ele1 = (Vector) nameList.get(0);
                if (ele1.get(0) == null || ele1.get(0).toString().trim().equals("")) {
                    throw new ApplicationException("Please Aadhar Location.");
                }
                String aadharLocation = ele1.get(0).toString().trim();

                dataList = em.createNativeQuery("select pms.acno,bm.alphacode,cm.customerid,"
                        + "date_format(pms.enrol_date,'%Y%m%d') as enroll_date,cm.custfullname,"
                        + "ifnull(cm.mailaddressline1,'') as coradd,cm.MailDistrict,ref2.ref_desc,"
                        + "cm.mailpostalcode,date_format(cm.dateofbirth,'%Y%m%d') as dob,"
                        + "cm.gender,pms.disability,pms.nom_name,ref3.ref_desc,pms.minor_flag,"
                        + "ifnull(pms.guardian_name,''),ifnull(cm.aadhaar_no,'') as aadhar_no,"
                        + "ifnull(cm.pan_girnumber,'') as pan,ifnull(cm.mobilenumber,'') as mob,"
                        + "pms.enrol_date,pmr.actual_file_name,pmr.update_time from pm_scheme_reg_details pms,"
                        + "branchmaster bm,cbs_customer_master_detail cm,customerid ci , "
                        + "cbs_ref_rec_type ref2, cbs_ref_rec_type ref3, pm_scheme_rejection_details pmr "
                        + "where pms.acno = ci.acno and ci.custid = cm.customerid and "
                        + "cast(pms.txn_br_code as unsigned)=bm.brncode  "
                        + "and cm.mailstatecode = ref2.ref_code and ref2.ref_rec_no='002' and "
                        + "pms.nom_relationship = ref3.ref_code and ref3.ref_rec_no='218' and pms.enrol_flag = 'J' and "
                        + "pms.auth='Y'  and pms.scheme_code='" + schemeCode + "' and pms.vendor_code='" + vendor + "' and "
                        + "pmr.actual_file_name='" + ctrlFile + "' and pmr.file_status='U' and "
                        + "pmr.update_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and pmr.scheme_code=pms.scheme_code "
                        + "and pmr.vendor_code=pms.vendor_code and pmr.acno=pms.acno").getResultList();
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no data to generate the file.");
                }

                String totalRecords = String.valueOf(dataList.size());
                String fileVersion = ParseFileUtil.addTrailingZeros(String.valueOf(Integer.parseInt(ctrlFile.substring(19).trim()) + 1), 2);
                String uicDataFileName = ctrlFile.substring(0, 19) + fileVersion + ".CSV";

                //Data Member File Writing.
                FileWriter fw = new FileWriter(aadharLocation + uicDataFileName);
                for (int i = 0; i < dataList.size(); i++) {
                    ele1 = (Vector) dataList.get(i);
                    String custname = ele1.get(4).toString().trim();
                    custname = custname.length() > 50 ? custname.substring(0, 50) : custname;
                    String corAdd = ele1.get(5).toString().trim();
                    corAdd = corAdd.length() > 160 ? corAdd.substring(0, 160) : corAdd;
                    String city = ele1.get(6).toString().trim();
                    city = city.length() > 40 ? city.substring(0, 40) : city;
                    String state = ele1.get(7).toString().trim();
                    state = state.length() > 35 ? state.substring(0, 35) : state;
                    String pin = ele1.get(8).toString().trim();
                    pin = pin.length() > 8 ? pin.substring(0, 8) : pin;
                    String dob = dmy.format(ymd.parse(ele1.get(9).toString().trim()));
                    String gender = ele1.get(10).toString().trim();
                    if (!(gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"))) {
                        gender = "T";
                    }
                    String disability = ele1.get(11).toString().trim();
                    disability = (disability.equals("") || disability.equalsIgnoreCase("N")) ? "No" : "Yes";
                    String nomName = ele1.get(12).toString().trim();
                    nomName = nomName.length() > 50 ? nomName.substring(0, 50) : nomName;
                    String grdName = ele1.get(15).toString().trim();
                    grdName = grdName.length() > 50 ? grdName.substring(0, 50) : grdName;
                    String pan = ele1.get(17).toString().trim();
                    pan = pan.length() > 10 ? pan.substring(0, 10) : pan;
                    String mobile = ele1.get(18).toString().trim();
                    mobile = mobile.length() > 10 ? mobile.substring(0, 10) : mobile;

                    //Here Agency Code/ BC Code is bankId at last field.
                    String row = ele1.get(0).toString().trim() + "~~" + bankId.trim().toUpperCase() + "~~"
                            + ele1.get(1).toString().trim() + "~~" + hoAlphaCode + "~~" + ele1.get(2).toString().trim()
                            + "~~" + dmy.format(ymd.parse(ele1.get(3).toString().trim())) + "~~" + custname + "~~"
                            + corAdd + "~~" + city + "~~" + state + "~~" + pin + "~~" + "U~~" + dob + "~~" + gender
                            + "~~" + disability + "~~" + nomName + "~~" + ele1.get(13).toString().trim() + "~~"
                            + grdName + "~~" + ele1.get(16).toString().trim() + "~~" + pan + "~~" + "" + "~~"
                            + mobile + "~~" + bankId + "\n";

                    int n = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='R' where "
                            + "acno='" + ele1.get(0).toString().trim() + "' and "
                            + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem In Status Updation In PM Scheme Reg Details");
                    }

                    n = em.createNativeQuery("update pm_scheme_rejection_details set file_status='S' where "
                            + "scheme_code='" + schemeCode + "' and vendor_code='" + vendor + "' and "
                            + "acno='" + ele1.get(0).toString().trim() + "' and actual_file_name='" + ctrlFile + "' and "
                            + "update_date='" + ymd.format(dmy.parse(fileGenDt)) + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem In Status Updation In PM Scheme Rejection Details.");
                    }
                    fw.write(row);
                }
                fw.close();

                nameList = em.createNativeQuery("select max(file_no)+1 as file_no from "
                        + "cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and "
                        + "file_gen_type='PMSBYR'").getResultList();
                ele1 = (Vector) nameList.get(0);

                String apyFileNo = "1";
                if (ele1.get(0) != null) {
                    apyFileNo = ele1.get(0).toString().trim();
                }

                String uicControllFile = uicDataFileName.substring(0, 3) + "CTLD" + uicDataFileName.substring(7);
                //Controll File Generation
                int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,"
                        + "file_gen_by,file_gen_time,file_gen_brncode,file_gen_type) "
                        + "values(" + Integer.parseInt(apyFileNo) + ",'" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "'" + uicDataFileName + "|" + uicControllFile + "','" + enterBy + "',now(),"
                        + "'" + uploadBrCode + "','PMSBYR')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in Files Insertion.");
                }
                //Fetching Utr details
                nameList = em.createNativeQuery("select premium_amount,utr,date_format(utr_date,'%d/%m/%Y') as UTR_DATE from "
                        + "cbs_npci_mapper_files where file_gen_type='" + schemeCode + "' and "
                        + "substring(file_name,1,21)='" + ctrlFile + "'").getResultList();
                if (nameList.isEmpty()) {
                    throw new Exception("There should be utr data.");
                }
                ele1 = (Vector) nameList.get(0);
                if (ele1.get(0) == null || ele1.get(1) == null || ele1.get(2) == null
                        || ele1.get(0).toString().equals("") || ele1.get(1).toString().equals("")
                        || ele1.get(2).toString().equals("")) {
                    throw new Exception("Utr data should not be blank.");
                }

                fw = new FileWriter(aadharLocation + uicControllFile);
                String row = uicDataFileName.substring(0, uicDataFileName.indexOf(".")).trim() + "~~"
                        + bankId.trim().toUpperCase() + "~~" + ele1.get(1).toString().trim() + "~~"
                        + ele1.get(2).toString().trim() + "~~" + totalRecords + "~~"
                        + new BigDecimal(ele1.get(0).toString()) + "~~" + hoAlphaCode;
                fw.write(row);
                fw.close();
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return "true";
    }

    public List getActualFile(String schemeCode, String vendorCode, String fDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(actual_file_name) from pm_scheme_rejection_details "
                    + "where scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "' and "
                    + "file_status ='U' and update_date = '" + fDt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<SSSRegistrationPojo> getRejectedFileDetail(String brCode, String schemeCode, String vendorCode, String fileName, String fDt) throws ApplicationException {
        List<SSSRegistrationPojo> dataList = new ArrayList<SSSRegistrationPojo>();
        List result = new ArrayList();

        try {
            if (brCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select acno,cust_id,custname,bank_br_name,reason,uploaded_file_name from pm_scheme_rejection_details where scheme_code = '" + schemeCode + "' "
                        + "and vendor_code = '" + vendorCode + "' and update_date = '" + fDt + "' and actual_file_name = '" + fileName + "' and file_status = 'U'").getResultList();
            } else {
                result = em.createNativeQuery("select acno,cust_id,custname,bank_br_name,reason,uploaded_file_name from pm_scheme_rejection_details where substring(acno,1,2)='" + brCode + "' "
                        + "and scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "' and update_date = '" + fDt + "' and actual_file_name = '" + fileName + "' "
                        + "and file_status = 'U'").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    SSSRegistrationPojo pojo = new SSSRegistrationPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustId(vtr.get(1).toString());
                    pojo.setCustName(vtr.get(2).toString());
                    pojo.setBranch(vtr.get(3).toString());
                    pojo.setReason(vtr.get(4).toString());
                    pojo.setActualFile(vtr.get(5).toString());
                    dataList.add(pojo);
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getSssRenewData(String schemeCode, String vendorCode, String orgnBrCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select premium_amt from pm_scheme_details "
                    + "where scheme_code='" + schemeCode + "' and vendor_code='" + vendorCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define premium amount for "
                        + "scheme:" + schemeCode + " and vendor:" + vendorCode);
            }
            Vector amtVec = (Vector) list.get(0);
            double premiumAmount = Double.parseDouble(amtVec.get(0).toString());

            return em.createNativeQuery("select a.scheme_code,a.vendor_code,a.acno,a.enroll_date,a.balance,a.accstatus,"
                    + "a.temp_value,"
                    + "case "
                    + "when temp_value<>'ACTIVE' then temp_value "
                    + "when balance<" + premiumAmount + " then 'IF' "
                    + "when balance>=" + premiumAmount + " then 'ACTIVE' "
                    + "end as classification from (select pm.scheme_code,pm.vendor_code,pm.acno,"
                    + "date_format(pm.enrol_date,'%d/%m/%Y') as enroll_date,cast(r.balance as decimal(14,2)) as balance,"
                    + "a.accstatus,"
                    + "case accstatus "
                    + "when 9 then 'CLOSED' "
                    + "when 2 then 'INOPERATIVE' "
                    + "when 7 then 'WITHDRAWAL STOPPED' "
                    + "when 8 then 'OPERATION STOPPED' "
                    + "when 15 then 'DEAF' "
                    + "else 'ACTIVE' end as temp_value "
                    + "from pm_scheme_reg_details pm,reconbalan r,accountmaster a where pm.acno=r.acno and a.acno=pm.acno "
                    + "and pm.enrol_flag='R' and auth='Y' and pm.scheme_code='" + schemeCode + "' and "
                    + "pm.vendor_code='" + vendorCode + "' and a.curbrcode='" + orgnBrCode + "' "
                    + "and pm.acno not in(select acno from sss_txn_detail where "
                    + "scheme_code='" + schemeCode + "' and vendor_code='" + vendorCode + "' and "
                    + "substring(acno,1,2)='" + orgnBrCode + "' and "
                    + "extract(year from txn_date)=" + ymd.format(curDt).substring(0, 4) + ")) a").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String sssRenewProcess(List<PmSchemeRegDto> gridDetail, String schemeCode, String vendorCode,
            String orgnBrCode, String userName, List<PmSchemeRegDto> reportList) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            float trsNo = 0f, recNo = 0f;
            String msg = "", details = "";
            String curDate = ymd.format(curDt);
            ut.begin();

            List schemeDetailsList = em.createNativeQuery("select premium_amt,scheme_gl,gl_amt,scheme_pl,"
                    + "pl_amt,agent_amt from pm_scheme_details where effective_date  <= '" + curDate + "' and "
                    + "scheme_code='" + schemeCode + "' and vendor_code = '" + vendorCode + "' and "
                    + "effective_date  >= (select max(effective_date) from pm_scheme_details where "
                    + "scheme_code='" + schemeCode + "' and vendor_code = '" + vendorCode + "')").getResultList();
            if (schemeDetailsList.isEmpty()) {
                throw new ApplicationException("Please fille the Scheme Detail");
            }
            Vector schemeDetailsVec = (Vector) schemeDetailsList.get(0);
            BigDecimal premiumAmt = new BigDecimal(schemeDetailsVec.get(0).toString());

            String insuranceHead = schemeDetailsVec.get(1).toString();
            BigDecimal insuranceAmt = new BigDecimal(schemeDetailsVec.get(2).toString());

            String plHead = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + schemeDetailsVec.get(3).toString() + "01";
            BigDecimal plAmt = new BigDecimal(schemeDetailsVec.get(4).toString());

            BigDecimal agentAmt = new BigDecimal(schemeDetailsVec.get(5).toString());

            trsNo = ftsPostingFacade.getTrsNo();

            BigDecimal totalInsuredAmount = new BigDecimal("0");
            BigDecimal totalPlAmount = new BigDecimal("0");
            BigDecimal totalAgentAmount = new BigDecimal("0");
            for (PmSchemeRegDto obj : gridDetail) {
                System.out.println("A/c No Is-->" + obj.getAcno());
                msg = renewPmSchemeTxn(orgnBrCode, schemeCode, vendorCode, obj.getAcno(), curDate, "", userName, premiumAmt,
                        insuranceHead, insuranceAmt, plHead, plAmt, agentAmt, trsNo);
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem in transaction insertion for A/c-->" + obj.getAcno());
                }

                int verifyResult = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='E', auth = 'Y',"
                        + "renew_by = '" + userName + "',renew_date='" + curDate + "' where acno = '" + obj.getAcno() + "' and "
                        + "scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "'").executeUpdate();
                if (verifyResult <= 0) {
                    throw new ApplicationException("Updation problem in pm_scheme_reg_details.");
                }

                totalInsuredAmount = totalInsuredAmount.add(insuranceAmt);
                totalPlAmount = totalPlAmount.add(plAmt);
                totalAgentAmount = totalAgentAmount.add(agentAmt);
            }
            //Gl Entries
            List list = em.createNativeQuery("select ifnull(code,1) from parameterinfo_report where "
                    + "reportname='PMSSS-PL-HEAD'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define PL head in parameterinfo_report");
            }
            String query = "";
            Vector codeVec = (Vector) list.get(0);
            if (Integer.parseInt(codeVec.get(0).toString()) == 1) {
                query = "select acno from abb_parameter_info where purpose='PMSSS-PL-HEAD' and flag='A'";
            } else {
                query = "select acno from abb_parameter_info where purpose='" + schemeCode + "-PL-HEAD' and flag='A'";
            }
            list = em.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define PL head in abb_parameter_info");
            }
            Vector headVec = (Vector) list.get(0);
            String agentAcNo = orgnBrCode + headVec.get(0).toString();

            if (insuranceHead.substring(0, 2).equals(orgnBrCode)) { //Local Case
                recNo = ftsPostingFacade.getRecNo();
                details = schemeCode + " Insurance Premium";

                msg = ftsPostingFacade.ftsPosting43CBS(insuranceHead, 2, 0, totalInsuredAmount.doubleValue(),
                        curDate, curDate, userName, orgnBrCode, orgnBrCode, 68, details, trsNo, recNo, 0, "",
                        "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
                //if amount does not zero
                if (totalPlAmount.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " Administrative expenses";

                    msg = ftsPostingFacade.ftsPosting43CBS(plHead, 2, 0, totalPlAmount.doubleValue(), curDate,
                            curDate, userName, orgnBrCode, orgnBrCode, 68, details, trsNo, recNo, 0, "", "Y",
                            "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                    if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                }
                //if amount does not zero
                if (totalAgentAmount.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " Agent commission";

                    msg = ftsPostingFacade.ftsPosting43CBS(agentAcNo, 2, 0, totalAgentAmount.doubleValue(),
                            curDate, curDate, userName, orgnBrCode, orgnBrCode, 68, details, trsNo, recNo, 0, "",
                            "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                    if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                }
            } else {    //Remote Case-Transaction from branch to HO
                recNo = ftsPostingFacade.getRecNo();
                details = schemeCode + " Insurance Premium";

                msg = interBranchFacade.cbsPostingSx(insuranceHead, 0, curDate, totalInsuredAmount.doubleValue(),
                        0f, 2, details, 0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2),
                        orgnBrCode, userName, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                //if amount does not zero
                if (totalPlAmount.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " Administrative expenses";

                    msg = interBranchFacade.cbsPostingCx(plHead, 0, curDate, totalPlAmount.doubleValue(), 0f, 2, details,
                            0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode,
                            userName, "SYSTEM", trsNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
                //if amount does not zero
                if (totalAgentAmount.doubleValue() > 0) {
                    recNo = ftsPostingFacade.getRecNo();
                    details = schemeCode + " Agent commission";

                    msg = interBranchFacade.cbsPostingCx(agentAcNo, 0, curDate, totalAgentAmount.doubleValue(), 0f, 2,
                            details, 0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2),
                            orgnBrCode, userName, "SYSTEM", trsNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
            }

            //Status updation of closed a/c
            for (PmSchemeRegDto pojo : reportList) {
                if (pojo.getClassification().equalsIgnoreCase("Closed")) {
                    int verifyResult = em.createNativeQuery("update pm_scheme_reg_details set enrol_flag='C', auth = 'Y',"
                            + "cancel_by = '" + userName + "',cancel_date='" + curDate + "' where acno = '" + pojo.getAcno() + "' and "
                            + "scheme_code = '" + schemeCode + "' and vendor_code = '" + vendorCode + "'").executeUpdate();
                    if (verifyResult <= 0) {
                        throw new ApplicationException("Updation problem in pm_scheme_reg_details.");
                    }
                }
            }

            ut.commit();
            return "true" + trsNo;
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    private String renewPmSchemeTxn(String orgnBrCode, String schemeCode, String vendorCode, String acno,
            String fileGenDt, String agentAcNo, String enterBy, BigDecimal premiumAmt, String insuranceHead,
            BigDecimal insuranceAmt, String plHead, BigDecimal plAmt, BigDecimal agentAmt, float trsNo) throws ApplicationException {
        try {
//            if (agentAcNo.equals("")) {
//                List list = em.createNativeQuery("select ifnull(code,1) from parameterinfo_report where "
//                        + "reportname='PMSSS-PL-HEAD'").getResultList();
//                if (list.isEmpty()) {
//                    throw new ApplicationException("Please define PL head in parameterinfo_report");
//                }
//                String query = "";
//                Vector codeVec = (Vector) list.get(0);
//                if (Integer.parseInt(codeVec.get(0).toString()) == 1) {
//                    query = "select acno from abb_parameter_info where purpose='PMSSS-PL-HEAD' and flag='A'";
//                } else {
//                    query = "select acno from abb_parameter_info where purpose='" + schemeCode + "-PL-HEAD' and flag='A'";
//                }
//                list = em.createNativeQuery(query).getResultList();
//                if (list.isEmpty()) {
//                    throw new ApplicationException("Please define PL head in abb_parameter_info");
//                }
//                Vector headVec = (Vector) list.get(0);
//                agentAcNo = orgnBrCode + headVec.get(0).toString();
//            }
            String msg = "";
            int insertResult = em.createNativeQuery("INSERT INTO sss_txn_detail (txn_id, scheme_code, vendor_code, acno, status, amount, "
                    + "enter_by, txn_date, tran_time, trsn_no) VALUES ('" + ymdms.format(new Date()) + "', '" + schemeCode + "', '"
                    + vendorCode + "', '" + acno + "', 'E'," + premiumAmt + ", '" + enterBy + "', '" + fileGenDt + "', now(), "
                    + trsNo + ");").executeUpdate();

            if (insertResult <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }

            if (insuranceHead.substring(0, 2).equals(orgnBrCode)) {
                String details = schemeCode + " Premium deduction";

                float recNo = ftsPostingFacade.getRecNo();

                msg = ftsPostingFacade.ftsPosting43CBS(acno, 2, 1, premiumAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
                        acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg + ":A/c No-->" + acno);
                }
//                recNo = ftsPostingFacade.getRecNo();
//                details = schemeCode + " Insurance Premium for " + acno;
//
//                msg = ftsPostingFacade.ftsPosting43CBS(insuranceHead, 2, 0, insuranceAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
//                        acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "");
//
//                if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
//                    throw new ApplicationException(msg);
//                }
//                //if amount does not zero
//                if (plAmt.doubleValue() > 0) {
//                    recNo = ftsPostingFacade.getRecNo();
//                    details = schemeCode + " Administrative expenses for " + acno;
//
//                    msg = ftsPostingFacade.ftsPosting43CBS(plHead, 2, 0, plAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
//                            acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "");
//
//                    if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
//                        throw new ApplicationException(msg);
//                    }
//                }
//                //if amount does not zero
//                if (agentAmt.doubleValue() > 0) {
//                    recNo = ftsPostingFacade.getRecNo();
//                    details = schemeCode + " for " + acno;
//
//                    msg = ftsPostingFacade.ftsPosting43CBS(agentAcNo, 2, 0, agentAmt.doubleValue(), fileGenDt, fileGenDt, enterBy, orgnBrCode,
//                            acno.substring(0, 2), 68, details, trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "");
//
//                    if (!msg.substring(0, 4).equalsIgnoreCase("True")) {
//                        throw new ApplicationException(msg);
//                    }
//                }
            } else {
                String details = schemeCode + " Premium deduction";
                float recNo = ftsPostingFacade.getRecNo();

                msg = interBranchFacade.cbsPostingCx(acno, 1, fileGenDt, premiumAmt.doubleValue(), 0f, 2, details, 0f, "A", "",
                        "", 3, 0f, recNo, 68, acno.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg + ":A/c No-->" + acno);
                }
                msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAccountNature(acno), acno, 0, premiumAmt.doubleValue(), "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
//                recNo = ftsPostingFacade.getRecNo();
//                details = schemeCode + " Insurance Premium for " + acno;
//
//                msg = interBranchFacade.cbsPostingSx(insuranceHead, 0, fileGenDt, insuranceAmt.doubleValue(), 0f, 2, details,
//                        0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
//                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                    throw new ApplicationException(msg);
//                }
                //if amount does not zero
//                if (plAmt.doubleValue() > 0) {
//                    recNo = ftsPostingFacade.getRecNo();
//                    details = schemeCode + " Administrative expenses for " + acno;
//
//                    msg = interBranchFacade.cbsPostingCx(plHead, 0, fileGenDt, plAmt.doubleValue(), 0f, 2, details,
//                            0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
//                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                        throw new ApplicationException(msg);
//                    }
//                }
//                //if amount does not zero
//                if (agentAmt.doubleValue() > 0) {
//                    recNo = ftsPostingFacade.getRecNo();
//                    details = schemeCode + " for " + acno;
//
//                    msg = interBranchFacade.cbsPostingCx(agentAcNo, 0, fileGenDt, agentAmt.doubleValue(), 0f, 2, details,
//                            0f, "A", "", "", 3, 0f, recNo, 68, insuranceHead.substring(0, 2), orgnBrCode, enterBy, "SYSTEM", trsNo, "", "");
//                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                        throw new ApplicationException(msg);
//                    }
//                }
            }
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
