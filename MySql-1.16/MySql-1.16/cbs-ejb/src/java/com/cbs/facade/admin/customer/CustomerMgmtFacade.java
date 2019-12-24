/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin.customer;

import com.cbs.adaptor.ObjectAdaptorCustomer;
import com.cbs.dao.master.CBSCustomerMasterDetailDAO;
import com.cbs.dto.RelatedPersonsInfoTable;
import com.cbs.dto.customer.CBSCustMISInfoHisTO;
import com.cbs.dto.customer.CBSCustMinorInfoHisTO;
import com.cbs.dto.customer.CBSCustNREInfoHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.entity.customer.CBSCustMinorInfoHis;
import com.cbs.entity.customer.CbsCustMisinfoHis;
import com.cbs.entity.customer.CbsCustNreinfoHis;
import com.cbs.entity.customer.CbsCustomerMasterDetailHis;
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

@Stateless(mappedName = "CustomerMgmtFacade")
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerMgmtFacade implements CustomerMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public String getNextRelatedPerson() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(max(person_srno),0)+1 as personSrNo "
                    + "from cbs_related_persons_details").getResultList();
            Vector ele = (Vector) list.get(0);
            return ele.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RelatedPersonsInfoTable> getAllRelatedPersons(String customerId) throws ApplicationException {
        List<RelatedPersonsInfoTable> personList = new ArrayList<RelatedPersonsInfoTable>();
        try {
            List list = em.createNativeQuery("SELECT CustomerId,Person_SrNo,ifnull(Person_CustomerId,''),"
                    + "ifnull(Person_First_Name,''),ifnull(Person_Middle_Name,''),ifnull(Person_Last_Name,''),"
                    + "ifnull(Relationship_Code,''),ifnull(Person_PAN,''),ifnull(Person_UID,''),"
                    + "ifnull(Person_Voter_Id,''),ifnull(Person_Nrega,''),ifnull(Person_DL,''),"
                    + "date_format(ifnull(Person_DL_Expiry,now()),'%d/%m/%Y'),ifnull(Person_Passport_No,''),"
                    + "date_format(ifnull(Person_Passport_Expiry,now()),'%d/%m/%Y'),ifnull(Person_DIN,''),"
                    + "Person_political_exposed,ifnull(Person_Add1,''),ifnull(Person_Add2,''),"
                    + "Person_City,Person_State,Person_Country,ifnull(Person_PIN,''),Del_Flag FROM "
                    + "cbs_related_persons_details where CustomerId='" + customerId + "' order "
                    + "by Person_SrNo").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    RelatedPersonsInfoTable pojo = new RelatedPersonsInfoTable();

                    pojo.setCustomerId(ele.get(0).toString());
                    pojo.setPersonSrNo(ele.get(1).toString());
                    pojo.setPersonCustomerid(ele.get(2).toString());
                    pojo.setPersonFirstName(ele.get(3).toString());
                    pojo.setPersonMiddleName(ele.get(4).toString());
                    pojo.setPersonLastName(ele.get(5).toString());
                    pojo.setCompleteName(ele.get(3).toString() + " " + ele.get(4).toString() + " " + ele.get(5).toString());
                    pojo.setRelationshipCode(ele.get(6).toString());
                    pojo.setPersonPan(ele.get(7).toString());
                    pojo.setPersonUid(ele.get(8).toString());
                    pojo.setPersonVoterId(ele.get(9).toString());
                    pojo.setPersonNrega(ele.get(10).toString());
                    pojo.setPersonDl(ele.get(11).toString());
                    pojo.setPersonDlExpiry(ele.get(12).toString());
                    pojo.setPersonPassportNo(ele.get(13).toString());
                    pojo.setPersonPassportExpiry(ele.get(14).toString());
                    pojo.setPersonDin(ele.get(15).toString());
                    pojo.setPersonPoliticalExposed(ele.get(16).toString());
                    pojo.setPersonAdd1(ele.get(17).toString());
                    pojo.setPersonAdd2(ele.get(18).toString());
                    pojo.setPersonCity(ele.get(19).toString());
                    pojo.setPersonState(ele.get(20).toString());
                    pojo.setPersonCountry(ele.get(21).toString());
                    pojo.setPersonPin(ele.get(22).toString());
                    pojo.setDelFlag(ele.get(23).toString());

                    personList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return personList;
    }

    public String getBankCode() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define bank code.");
            }
            Vector ele = (Vector) list.get(0);
            return ele.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String isCustIdExistBasedOnPan(String function, String custId, String panNo) throws ApplicationException {
        try {
            List list;
            if (function.equalsIgnoreCase("A")) {
                list = em.createNativeQuery("select customerid from cbs_customer_master_detail "
                        + "where pan_girnumber='" + panNo + "'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return "This Pan is already exist for customer:" + ele.get(0).toString().trim();
                }
            } else if (function.equalsIgnoreCase("M")) {
                list = em.createNativeQuery("select customerid from cbs_customer_master_detail "
                        + "where pan_girnumber='" + panNo + "' and customerid <> '" + custId + "'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return "This Pan is exist for customer:" + ele.get(0).toString().trim();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "ok";
    }

    public String isCustIdExistBasedOnAadhar(String function, String custId, String aadharNo) throws ApplicationException {
        try {
            List list;
            if (function.equalsIgnoreCase("A")) {
                list = em.createNativeQuery("select customerid from cbs_customer_master_detail "
                        + "where aadhaar_no='" + aadharNo + "'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return "This Aadhar is already exist for customer:" + ele.get(0).toString().trim();
                }
            } else if (function.equalsIgnoreCase("M")) {
                list = em.createNativeQuery("select customerid from cbs_customer_master_detail "
                        + "where aadhaar_no='" + aadharNo + "' and customerid <> '" + custId + "'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return "This Aadhar is exist for customer:" + ele.get(0).toString().trim();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "ok";
    }

    public String isCustIdExistBasedOnNameFatherAndDob(String function, String custId, String custName,
            String fatherName, String dob) throws ApplicationException {
        try {
            List list;
            if (function.equalsIgnoreCase("A")) {
                list = em.createNativeQuery("select customerid from cbs_customer_master_detail where "
                        + "trim(concat(trim(concat(trim(ifnull(custname,'')),' ',trim(ifnull(middle_name,'')))),' ',"
                        + "trim(ifnull(last_name,'')))) = '" + custName + "' and fathername='" + fatherName + "' and "
                        + "date_format(dateofbirth,'%Y%m%d')='" + dob + "'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return "Customer for this name, fathername and dob already exists:" + ele.get(0).toString().trim();
                }
            } else if (function.equalsIgnoreCase("M")) {
                list = em.createNativeQuery("select customerid from cbs_customer_master_detail where "
                        + "trim(concat(trim(concat(trim(ifnull(custname,'')),' ',trim(ifnull(middle_name,'')))),' ',"
                        + "trim(ifnull(last_name,'')))) = '" + custName + "' and fathername='" + fatherName + "' and "
                        + "date_format(dateofbirth,'%Y%m%d')='" + dob + "' and customerid <> '" + custId + "'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return "Customer for this name, fathername and dob exists:" + ele.get(0).toString().trim();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "ok";
    }

    public String retrieveCurrentCustomerStatus(String custId) throws ApplicationException {
        String status = "";
        try {
            List list = em.createNativeQuery("select ifnull(Suspensionflg,'') as suspensionflag from "
                    + "cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                status = ele.get(0).toString().trim();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return status;
    }

    public boolean isActiveAcOnCustomer(String custId) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select am.acno from cbs_customer_master_detail cust,customerid id, accountmaster am "
                    + "where cust.customerid=id.custid and id.acno=am.acno and am.accstatus<>9 and "
                    + "cust.customerid='" + custId + "' "
                    + "union "
                    + "select am.acno from cbs_customer_master_detail cust,customerid id, td_accountmaster am "
                    + "where cust.customerid=id.custid and id.acno=am.acno and am.accstatus<>9 and "
                    + "cust.customerid='" + custId + "'").getResultList();
            if (!list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return false;
    }

    public CBSCustomerMasterDetailHisTO getCustomerLastChangeDetail(String customerid) throws ApplicationException {
        CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(em);
        CBSCustomerMasterDetailHisTO hisTo = null;
        try {
            List list = em.createNativeQuery("select max(h1.txnid) as txnid from cbs_customer_master_detail_his h1,"
                    + "cbs_customer_master_detail_his h2 where h1.customerid=h2.customerid and "
                    + "h1.customerid='" + customerid + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                Long txnId = Long.parseLong(ele.get(0).toString());

                CbsCustomerMasterDetailHis hisObj = masterDetailDAO.getCustomerLastDetail(customerid, txnId);
                if (hisObj != null) {
                    hisTo = ObjectAdaptorCustomer.adaptToCBSCustomerMasterDtlHisTO(hisObj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return hisTo;
    }

    public CBSCustMinorInfoHisTO getCustomerLastChangeDetailForMinor(String customerid) throws ApplicationException {
        CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(em);
        CBSCustMinorInfoHisTO hisTo = null;
        try {
            List list = em.createNativeQuery("select max(h1.txnid) as txnid from cbs_cust_minorinfo_his h1,"
                    + "cbs_cust_minorinfo_his h2 where h1.customerid=h2.customerid and "
                    + "h1.customerid='" + customerid + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                Long txnId = Long.parseLong(ele.get(0).toString());

                CBSCustMinorInfoHis hisObj = masterDetailDAO.getCustomerLastDetailForMinor(customerid, txnId);
                if (hisObj != null) {
                    hisTo = ObjectAdaptorCustomer.adaptToCBSCustMinorInfoHisTO(hisObj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return hisTo;
    }

    public CBSCustMISInfoHisTO getCustomerLastChangeDetailForMis(String customerid) throws ApplicationException {
        CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(em);
        CBSCustMISInfoHisTO hisTo = null;
        try {
            List list = em.createNativeQuery("select max(h1.txnid) as txnid from cbs_cust_misinfo_his h1,"
                    + "cbs_cust_misinfo_his h2 where h1.customerid=h2.customerid and "
                    + "h1.customerid='" + customerid + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                Long txnId = Long.parseLong(ele.get(0).toString());

                CbsCustMisinfoHis hisObj = masterDetailDAO.getCustomerLastDetailForMis(customerid, txnId);
                if (hisObj != null) {
                    hisTo = ObjectAdaptorCustomer.adaptToCBSCustMISInfoHisTO(hisObj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return hisTo;
    }

    public CBSCustNREInfoHisTO getCustomerLastChangeDetailForNre(String customerid) throws ApplicationException {
        CBSCustomerMasterDetailDAO masterDetailDAO = new CBSCustomerMasterDetailDAO(em);
        CBSCustNREInfoHisTO hisTo = null;
        try {
            List list = em.createNativeQuery("select max(h1.txnid) as txnid from cbs_cust_nreinfo_his h1,"
                    + "cbs_cust_nreinfo_his h2 where h1.customerid=h2.customerid and "
                    + "h1.customerid='" + customerid + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                Long txnId = Long.parseLong(ele.get(0).toString());

                CbsCustNreinfoHis hisObj = masterDetailDAO.getCustomerLastDetailForNre(customerid, txnId);
                if (hisObj != null) {
                    hisTo = ObjectAdaptorCustomer.adaptToCBSCustNREInfoHisTO(hisObj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return hisTo;
    }
}
