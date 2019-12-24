/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ckycr;

import com.cbs.constant.CkycrEnum;
import com.cbs.dto.ckycr.CKYCRDownloadDetail30;
import com.cbs.dto.ckycr.CKYCRDownloadDetail60;
import com.cbs.dto.ckycr.CKYCRFailurePojo;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.dto.ckycr.CkycrRealTimeRequestPojo;
import com.cbs.dto.ckycr.CkycrRealTimeSearchDetailsPojo;
import com.cbs.dto.customer.CBSCustIdentityDetailsTo;
import com.cbs.dto.customer.CBSCustMISInfoTO;
import com.cbs.dto.customer.CBSCustMinorInfoTO;
import com.cbs.dto.customer.CBSCustNREInfoTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsTO;
import com.cbs.dto.customer.CbsCustAdditionalAddressDetailsTo;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.CKYCRDownloadPojo;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.ckyc.helper.DwndDataCreater;
import com.cbs.web.ckyc.helper.SearchDataCreator;
import com.cbs.web.ckyc.httpclient.CkycDownloadClient;
import com.cbs.web.ckyc.httpclient.CkycSearchClient;
import com.cbs.web.ckyc.pojo.CkycDwndReq;
import com.cbs.web.ckyc.pojo.CkycDwndRes;
import com.cbs.web.ckyc.pojo.DwndReqPidData;
import com.cbs.web.ckyc.pojo.DwndResPidData;
import com.cbs.web.ckyc.pojo.DwndResPidData.IDENTITYDETAILS.IDENTITY;
import com.cbs.web.ckyc.pojo.DwndResPidData.IMAGEDETAILS.IMAGE;
import com.cbs.web.ckyc.pojo.DwndResPidData.LOCALADDRESSDETAILS.LOCALADDRESS;
import com.cbs.web.ckyc.pojo.DwndResPidData.PERSONALDETAILS;
import com.cbs.web.ckyc.pojo.Header;
import com.cbs.web.ckyc.pojo.ReqPidData;
////////////////import com.cbs.web.ckyc.pojo.ReqPidData;
import com.cbs.web.ckyc.pojo.ReqRoot;
import com.cbs.web.ckyc.pojo.ResPidData;
import com.cbs.web.ckyc.sec.DataDecryptor;
import com.cbs.web.ckyc.sec.DigitalSigner;
import com.cbs.web.ckyc.sec.Encrypter;
import com.cbs.web.ckyc.utils.XMLUtilities;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
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
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "CkycrViewMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CkycrViewMgmtFacade implements CkycrViewMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private CkycrViewMgmtFacadeRemote ckycrViewMgmtRemote;
    @EJB
    private CkycrCommonMgmtFacadeRemote ckycrCommonMgmtRemote;
    @EJB
    private CkycrProcessMgmtFacadeRemote ckycrProcessMgmtRemote;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    @EJB
    private InterBranchTxnFacadeRemote txnRemote;
    private Properties props = null;
    SimpleDateFormat ymdWihtoutSeparator = new SimpleDateFormat("yyyyMMdd");
    DateFormat ymdTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmyHyphen = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private CustomerManagementFacadeRemote beanRemote = null;

    @PostConstruct
    private void loadConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The WSLOCATION Properties File. " + ex.getMessage());
        }
    }

    public List<CKYCRRequestPojo> getCbsCustomerMasterDetailIndividual(String customerIdOrCKYCRNo, String branchCode) throws ApplicationException {
        List<CKYCRRequestPojo> customerList = new ArrayList<CKYCRRequestPojo>();
        try {
            List list = em.createNativeQuery("select * from ckycr_request_detail where "
                    + "CustomerId_CKYCR_No = '" + customerIdOrCKYCRNo + "'").getResultList();
            if (list.isEmpty()) {
                List selectList = em.createNativeQuery("select c.customerid, ifnull(custfullname,'') as custname,"
                        + "date_format(c.dateofbirth,'%d/%m/%Y') as dob,ifnull(c.fathername,''),b.alphacode "
                        + "from cbs_customer_master_detail c,branchmaster b where c.primarybrcode ='" + branchCode + "' and "
                        + "c.customerid='" + customerIdOrCKYCRNo + "' and c.auth = 'Y' and "
                        + "date_format(c.creationtime,'%Y%m%d') < date_format(curdate(),'%Y%m%d') and "
                        + "suspensionflg <> 'Y' and cast(c.primarybrcode as unsigned)=b.brncode").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("Either this customer id does not exist or it is related to other branch. or Authorization is pending.");
                } else {
                    Vector vec = (Vector) selectList.get(0);

                    CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                    ckycrPojo.setCustomerIdOrCKYCRNo(vec.get(0).toString());
                    ckycrPojo.setCustName(vec.get(1).toString());
                    ckycrPojo.setDateOfBirth(vec.get(2).toString());
                    ckycrPojo.setFatherName(vec.get(3).toString());
                    ckycrPojo.setPrimaryBrCode(branchCode);
                    ckycrPojo.setPrimaryBrName(vec.get(4).toString());
                    ckycrPojo.setCheckBox(true);
                    customerList.add(ckycrPojo);
                }
            } else {
                throw new ApplicationException("Request for this customer id is already provided.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return customerList;
    }

    public List<CKYCRRequestPojo> getCbsCustomerMasterDetailBulk(String branchCode) throws ApplicationException {
        List<CKYCRRequestPojo> customerList = new ArrayList<CKYCRRequestPojo>();
        try {
            int limitValue = ckycrCommonMgmtRemote.getCkycrLimit();

            List list = em.createNativeQuery("select c.customerid, ifnull(custfullname,'') as custname,"
                    + "date_format(c.dateofbirth,'%d/%m/%Y') as dob,ifnull(c.fathername,''),b.alphacode "
                    + "from cbs_customer_master_detail c,branchmaster b where c.primarybrcode ='" + branchCode + "' and "
                    + "c.auth = 'Y' and date_format(c.creationtime,'%Y%m%d') < date_format(curdate(),'%Y%m%d') and "
                    + "suspensionflg <> 'Y' and cast(c.primarybrcode as unsigned)=b.brncode and c.customerid "
                    + "not in(select customerid_ckycr_no from ckycr_request_detail where branch_code='" + branchCode + "') "
                    + "order by c.creationtime limit " + limitValue + "").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no customer to upload.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                ckycrPojo.setCustomerIdOrCKYCRNo(element.get(0).toString());
                ckycrPojo.setCustName(element.get(1).toString());
                ckycrPojo.setDateOfBirth(element.get(2).toString());
                ckycrPojo.setFatherName(element.get(3).toString());
                ckycrPojo.setPrimaryBrCode(branchCode);
                ckycrPojo.setPrimaryBrName(element.get(4).toString());
                ckycrPojo.setCheckBox(true);
                customerList.add(ckycrPojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return customerList;
    }

    public int insertCKYCRRequestDetails(List<CKYCRRequestPojo> customerList, String mode, String type,
            String requestBy, boolean checkAll, String fetchMode) throws Exception {
        int count = 0, unCheckedCount = 0;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String status = "ENTERED";
            String reason = "";
            String reasonCode = "";
            String batchNo = "";
            String responseUpdateTime = null;
            Date requestDate = new Date();
            if (checkAll == true) {
                System.out.println("----------------All Checked------------------");
                for (CKYCRRequestPojo obj : customerList) {
                    String dob = ymd.format(dmy.parse(obj.getDateOfBirth()));
                    Query insertCKYCRRequest = em.createNativeQuery("insert into ckycr_request_detail(Mode, Type, CustomerId_CKYCR_No, DOB, Branch_code, Status, Reason, Reason_Code, Request_By, Request_Date, Request_TranTime, Response_Update_Time, Fetch_Mode, Batch_No) values('" + mode + "','" + type + "','" + obj.getCustomerIdOrCKYCRNo() + "','" + dob + "','" + obj.getPrimaryBrCode() + "','" + status + "','" + reason + "','" + reasonCode + "','" + requestBy + "','" + ymd.format(requestDate) + "',now()," + responseUpdateTime + ",'" + fetchMode + "','" + batchNo + "')");
                    int result = insertCKYCRRequest.executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Problem Occured In CKYCR Reautest Insertion !");
                    } else {
                        count++;
                    }
                }
            } else {
                System.out.println("----------------Not All Checked------------------");
                for (CKYCRRequestPojo obj : customerList) {
                    if (obj.isCheckBox() == true) {
                        String dob = ymd.format(dmy.parse(obj.getDateOfBirth()));
                        Query insertCKYCRRequest = em.createNativeQuery("insert into ckycr_request_detail(Mode, Type, CustomerId_CKYCR_No, DOB, Branch_code, Status, Reason, Reason_Code, Request_By, Request_Date, Request_TranTime, Response_Update_Time, Fetch_Mode, Batch_No) values('" + mode + "','" + type + "','" + obj.getCustomerIdOrCKYCRNo() + "','" + dob + "','" + obj.getPrimaryBrCode() + "','" + status + "','" + reason + "','" + reasonCode + "','" + requestBy + "','" + ymd.format(requestDate) + "',now()," + responseUpdateTime + ",'" + fetchMode + "','" + batchNo + "')");
                        int result = insertCKYCRRequest.executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem Occured In CKYCR Reautest Insertion !");
                        } else {
                            count++;
                        }
                    } else {
                        System.out.println("----------------Not Checked------------------");
                        System.out.println(obj.getSno() + " " + obj.isCheckBox());
                        unCheckedCount++;
                    }
                }
            }
            if (unCheckedCount == customerList.size()) {
                throw new ApplicationException("You have not selected any row !");
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
        return count;// returning no of customer info inserted
    }

    public int downloadCbsCustomerMasterDetail(String mode, String type, String customerIdOrCKYCRNo, String dateOfBirth,
            String orgnBrCode, String requestBy, String fetchMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        int res = 0;
        try {
            ut.begin();
            String status = "ENTERED";
            String reason = "";
            String reasonCode = "";
            String batchNo = "";
            String responseUpdateTime = null;
            Date requestDate = new Date();
            // inserting in to ckycr_request_detail_his from ckycr_request_detail on custId or CKYCRNo
            int flag = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
                    + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                    + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks) select * from "
                    + "ckycr_request_detail where customerid_ckycr_no = '" + customerIdOrCKYCRNo + "'").executeUpdate();
            if (flag == 0) { // if does not exist this customer in ckycr_request_detail then insert as new to Downloading  
                Query insertCKYCRRequest = em.createNativeQuery("insert into ckycr_request_detail(Mode, Type, CustomerId_CKYCR_No, DOB, Branch_code, Status, Reason, Reason_Code, Request_By, Request_Date, Request_TranTime, Response_Update_Time, Fetch_Mode, Batch_No) values('" + mode + "','" + type + "','" + customerIdOrCKYCRNo + "','" + dateOfBirth + "','" + orgnBrCode + "','" + status + "','" + reason + "','" + reasonCode + "','" + requestBy + "','" + ymd.format(requestDate) + "',now()," + responseUpdateTime + ",'" + fetchMode + "','" + batchNo + "')");
                int result = insertCKYCRRequest.executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem Occured In CKYCR Requtest Insertion !");
                } else {
                    res = result;
                    ut.commit();
                }
            } else if (flag > 0) { // if exist this customer in ckycr_request_detail
                Query insertCKYCRRequest = em.createNativeQuery("update ckycr_request_detail set Mode ='" + mode + "', Type='" + type + "', CustomerId_CKYCR_No='" + customerIdOrCKYCRNo + "', DOB='" + dateOfBirth + "', Branch_code='" + orgnBrCode + "', Status='" + status + "', Reason='" + reason + "', Reason_Code='" + reasonCode + "', Request_By='" + requestBy + "', Request_Date='" + ymd.format(requestDate) + "', Request_TranTime=now(), Batch_No='" + batchNo + "' where CustomerId_CKYCR_No='" + customerIdOrCKYCRNo + "'");
                int result = insertCKYCRRequest.executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem Occured In CKYCR Reautest Updation !");
                } else {
                    res = result;
                    ut.commit();
                }
            } else {
                throw new ApplicationException("Problem Occured In CKYCR Reautest History Insertion !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return res;
    }

    public CKYCRRequestPojo getCKYCRDetailByCustId(String customerId) throws ApplicationException {
        CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
        try {
            List selectList = em.createNativeQuery("select Mode,Type,CustomerId_CKYCR_No,DOB,Branch_Code,Status,Reason,Reason_Code,"
                    + "Request_By,Request_Date,Batch_No from ckycr_request_detail where CustomerId_CKYCR_No='" + customerId + "'").getResultList();
            if (selectList.size() > 0) {
                Vector vec = (Vector) selectList.get(0);
                ckycrPojo.setMode(vec.get(0).toString());
                ckycrPojo.setType(vec.get(1).toString());
                ckycrPojo.setCustomerId(vec.get(2).toString());
                ckycrPojo.setDateOfBirth(vec.get(3) == null ? "" : vec.get(3).toString());
                ckycrPojo.setPrimaryBrCode(vec.get(4).toString());
                ckycrPojo.setStatus(vec.get(5).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return ckycrPojo;
    }

    public int checkPrameterInfoReport(String parameter) throws ApplicationException {
        int result = 0;
        try {
            List selectList = em.createNativeQuery("select COALESCE(Code,'0') from parameterinfo_report where ReportName in('" + parameter + "')").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("Parameter does not exist in parameterinfo_report table !");
            } else {
                Vector vec = (Vector) selectList.get(0);
                result = Integer.parseInt(vec.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String updateCKYCRDetailByCustId(String customerId, String updateMode, String requestBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            Date date = new Date();
            String subQuery = "";
            String condition = "";
            //--Checking parameterinfo_report (CKYCR-MANUAL)
            int parameter = checkPrameterInfoReport("CKYCR-MANUAL");
            if (parameter == 1) {
                if (updateMode.equalsIgnoreCase("UPDATE")) {
                    subQuery = "Mode='UPDATE',Status='UPDATE',DOB=null, Fetch_Mode='Manual'";
                    condition = "Status in('03','01')";
                } else if (updateMode.equalsIgnoreCase("RESEND")) {
//                    subQuery = "Mode='UPLOAD',Status='RESEND',Batch_No='', Fetch_Mode='Manual'";
                    subQuery = "Status='RESEND',Batch_No='', Fetch_Mode='Manual'";
                    condition = "Status In('FAILURE','02')";
                }
            } else {
                if (updateMode.equalsIgnoreCase("UPDATE")) {
                    subQuery = "Mode='UPDATE',Status='UPDATE',DOB=null";
                    condition = "Status in('03','01')";
                } else if (updateMode.equalsIgnoreCase("RESEND")) {
//                    subQuery = "Mode='UPLOAD',Status='RESEND',Batch_No=''";
                    subQuery = "Status='RESEND',Batch_No=''";
                    condition = "Status In('FAILURE','02')";
                }
            }

            ut.begin();

            List historyList = em.createNativeQuery("Select customerid from cbs_customer_master_detail_his where customerid = " + customerId + "").getResultList();
            if (historyList.isEmpty()) {
                throw new ApplicationException("There is no previous updation for the customer id " + customerId + " .");
            }

            int result = em.createNativeQuery("INSERT INTO ckycr_request_detail_his(Mode, Type, CustomerId_CKYCR_No, DOB, Branch_Code, Status, Reason, Reason_Code,Request_By, "
                    + "Request_Date, Request_TranTime,Response_Update_Time,Fetch_Mode, Batch_No) SELECT Mode, Type, CustomerId_CKYCR_No, DOB, Branch_Code, Status, Reason, Reason_Code,Request_By, Request_Date,"
                    + " Request_TranTime,Response_Update_Time,Fetch_Mode, Batch_No FROM ckycr_request_detail where CustomerId_CKYCR_No = '" + customerId + "'").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Data insert issue in domain request history.");
            }

            result = em.createNativeQuery("UPDATE ckycr_request_detail SET  " + subQuery + ",Reason='',Reason_Code='',Request_By='" + requestBy
                    + "',Request_Date='" + ymd.format(date) + "',Request_TranTime='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "' WHERE " + condition + " AND CustomerId_CKYCR_No='" + customerId + "'").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Data upadte issue in domain request.");
            }
            msg = "Customer Id " + customerId + " Successfully Updated.";

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

    public List getUploadUpdateRequestFromBranches() throws Exception {
        try {
            return em.createNativeQuery("select mode,customerid_ckycr_no,branch_code,status,request_by,"
                    + "date_format(request_date,'%Y%m%d') as Request_Date from ckycr_request_detail where "
                    + "mode in('UPLOAD','UPDATE') and status in('ENTERED','RESEND','UPDATE') and fetch_mode<>'MANUAL' and "
                    + "request_date<='" + ymd.format(new Date()) + "' order by request_trantime").getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List getUploadRequestFromScheduler(int limit) throws Exception {
        try {
            return em.createNativeQuery("select customerid,primarybrcode,date_format(creationtime,'%Y%m%d') as "
                    + "creationtime from cbs_customer_master_detail where customerid not in(select "
                    + "customerid_ckycr_no from ckycr_request_detail)  and auth='Y' and suspensionflg<>'Y' and "
                    + "date_format(creationtime,'%Y%m%d')<'" + ymd.format(new Date()) + "' order "
                    + "by creationtime limit " + limit + "").getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String getNextBatchNo(String mode) throws Exception {
        String batchNo = "";
        try {
            List list = em.createNativeQuery("select ifnull(max(ifnull(cast(batch_no as unsigned),0)),0)+1 as "
                    + "batch_no from ckycr_file_detail where mode='" + mode + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            batchNo = ftsRemote.lPading(ele.get(0).toString(), 5, "0");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return batchNo;
    }

    public boolean isValidPostalCode(String pinCode, String stateCode) throws ApplicationException {
        List selectList = em.createNativeQuery("select distinct pincode from pin_directory where stateCode='"
                + stateCode + "' and pincode='" + pinCode + "'").getResultList();
        return selectList.isEmpty() ? false : selectList.size() == 1 ? true : false;

    }

    public List getBranchList() throws ApplicationException {
        List branchList = new ArrayList();
        try {
            //System.out.println("select distinct crd.Branch_Code, bm.BranchName from ckycr_request_detail crd, branchmaster bm where crd.Branch_Code = bm.BrnCode");
            //branchList = em.createNativeQuery("select distinct crd.Branch_Code, bm.BranchName from ckycr_request_detail crd, branchmaster bm where crd.Branch_Code = bm.BrnCode").getResultList();
            branchList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode, case  BrnCode when '90' then 'All' else BranchName end as BranchName from branchmaster order by BranchName").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return branchList;
    }

    public List<CKYCRRequestPojo> getCKYCRFailureRequestReport(String branchCode) throws ApplicationException {
        List<CKYCRRequestPojo> ckycrPojoList = new ArrayList<CKYCRRequestPojo>();
        try {
            System.err.println("select Mode, Type, CustomerId_CKYCR_No, Reason, Reason_code, Request_By from ckycr_request_detail where Status = 'FAILURE and Branch_Code = '" + branchCode + "'");
            List selectList = em.createNativeQuery("select Mode, Type, CustomerId_CKYCR_No, Reason, Reason_code, Request_By from ckycr_request_detail where Status = 'FAILURE' and Branch_Code = '" + branchCode + "'").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("CKYCR Failure Request does not exist !");
            } else {
                if (selectList.size() > 0) {
                    for (int i = 0; i < selectList.size(); i++) {
                        Vector vec = (Vector) selectList.get(i);
                        CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                        ckycrPojo.setMode(vec.get(0).toString().toUpperCase());
                        ckycrPojo.setType(vec.get(1).toString().toUpperCase());
                        ckycrPojo.setCustomerIdOrCKYCRNo(vec.get(2).toString());
                        ckycrPojo.setReason(vec.get(3).toString().toUpperCase());
                        ckycrPojo.setReasonCode(vec.get(4).toString().toUpperCase());
                        ckycrPojo.setRequestBy(vec.get(5).toString().toUpperCase());
                        ckycrPojoList.add(ckycrPojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return ckycrPojoList;
    }

    public List<CKYCRFailurePojo> getFailureCustomerDetail(String branchCode, String mode, String fromDate, String toDate) throws ApplicationException {
        List<CKYCRFailurePojo> failureList = new ArrayList<CKYCRFailurePojo>();
        try {
            List selectList = new ArrayList();
            if (branchCode.equals("90")) {
                selectList = em.createNativeQuery("select  crd.CustomerId_CKYCR_No, ccmd.custname, bm.BranchName, crd.Branch_Code,crd.Mode, crd.Request_By, DATE_FORMAT(crd.Request_Date,'%d-%m-%Y'), crd.Status, bm.Address from ckycr_request_detail crd, branchmaster bm, cbs_customer_master_detail ccmd  \n"
                        + "where crd.Request_Date >= '" + fromDate + "' and crd.Request_Date <= '" + toDate + "' AND crd.Status IN('FAILURE','02') \n"
                        + "and crd.Mode='" + mode + "' and crd.Branch_Code =  bm.BrnCode and ccmd.customerid = crd.CustomerId_CKYCR_No order by bm.BranchName").getResultList();
            } else {
                selectList = em.createNativeQuery("select  crd.CustomerId_CKYCR_No, ccmd.custname, bm.BranchName, crd.Branch_Code,crd.Mode, crd.Request_By, DATE_FORMAT(crd.Request_Date,'%d-%m-%Y'), crd.Status, bm.Address from ckycr_request_detail crd, branchmaster bm, cbs_customer_master_detail ccmd  \n"
                        + "where crd.Request_Date >= '" + fromDate + "' and crd.Request_Date <= '" + toDate + "' AND crd.Branch_Code ='" + branchCode + "'AND crd.Status IN('FAILURE','02') \n"
                        + "and crd.Mode='" + mode + "' and crd.Branch_Code =  bm.BrnCode and ccmd.customerid = crd.CustomerId_CKYCR_No ").getResultList();

            }

            if (selectList.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            } else {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    CKYCRFailurePojo ckycrPojo = new CKYCRFailurePojo();
                    ckycrPojo.setCustomerId(vec.get(0).toString());
                    ckycrPojo.setCustomerName(vec.get(1).toString().toUpperCase());
                    ckycrPojo.setBranchName(vec.get(2).toString().toUpperCase());
                    ckycrPojo.setBranchCode(vec.get(3).toString());
                    ckycrPojo.setMode(vec.get(4).toString().toUpperCase());
                    ckycrPojo.setRequestby(vec.get(5).toString().toUpperCase());
                    ckycrPojo.setRequestDate(vec.get(6).toString());
                    ckycrPojo.setStatus(vec.get(7).toString().toUpperCase());
                    ckycrPojo.setBranchAddress(vec.get(8).toString().toUpperCase());
                    ckycrPojo.setSno(i + 1);
                    ckycrPojo.setCheckBox(false);
                    failureList.add(ckycrPojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return failureList;
    }

    public List<CKYCRFailurePojo> getInfoForPrintFailureReport(String customerId, String branchCode, String mode) throws ApplicationException {
        List<CKYCRFailurePojo> failureList = new ArrayList<CKYCRFailurePojo>();
        try {
            //List selectList = em.createNativeQuery("select Reason_Code from ckycr_request_detail where CustomerId_CKYCR_No = '" + customerId + "' and Branch_Code ='" + branchCode + "' and Status = 'FAILURE' and Mode = '" + mode + "'").getResultList();
            List selectList = em.createNativeQuery("select Status, Reason_Code, Reason from ckycr_request_detail where CustomerId_CKYCR_No = '" + customerId + "' and Branch_Code ='" + branchCode + "' and Status IN('FAILURE','02') and Mode = '" + mode + "'").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("Reason code not found !");
            } else {
                Vector vec = (Vector) selectList.get(0);
                if (vec.get(0).toString().equalsIgnoreCase("FAILURE")) {
                    String reasonCode = vec.get(1).toString();
                    System.out.println("Reason Code " + reasonCode);
                    String code = "";
                    for (int i = 0; i < reasonCode.length(); i++) {

                        if (reasonCode.charAt(i) != '|') {
                            code += "" + reasonCode.charAt(i);
                        } else {
                            CKYCRFailurePojo ckycrPojo = new CKYCRFailurePojo();
                            ckycrPojo.setReasonCode(code);
                            ckycrPojo.setReasonCodeDescription(CkycrEnum.getValue(code));
                            failureList.add(ckycrPojo);
                            code = "";
                        }
                    }
                    CKYCRFailurePojo ckycrPojo = new CKYCRFailurePojo();
                    ckycrPojo.setReasonCode(code);
                    ckycrPojo.setReasonCodeDescription(CkycrEnum.getValue(code));
                    failureList.add(ckycrPojo);
                }
                if (vec.get(0).toString().equalsIgnoreCase("02")) {
                    CKYCRFailurePojo ckycrPojo = new CKYCRFailurePojo();
                    ckycrPojo.setReasonCode(vec.get(1).toString());
                    ckycrPojo.setReasonCodeDescription(vec.get(2).toString().toUpperCase());
                    failureList.add(ckycrPojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return failureList;
    }

    public CKYCRDownloadPojo setCKYCRDownload(String[] data) throws Exception {
        CKYCRDownloadPojo pojo = new CKYCRDownloadPojo();
        try {
            pojo.setRecordType(data[0]);
            pojo.setLineNo(data[1]);
            pojo.setApplicationType(data[2]);
            pojo.setBranchCode(data[3]);
            pojo.setApplicantNameUpdateFlag(data[4]);
            pojo.setPersonalEntityDetailsUpdateFlag(data[5]);
            pojo.setAddressDetailsUpdateFlag(data[6]);
            pojo.setContactDetailsUpdateFlag(data[7]);
            pojo.setRemarksUpdateFlag(data[8]);
            pojo.setkYCVerificationUpdateFlag(data[9]);
            pojo.setIdentityDetailsUpdateFlag(data[10]);
            pojo.setRelatedPersonDetailsFlag(data[11]);
            pojo.setControllingPersonDetailsFlag(data[12]);
            pojo.setImageDetailsUpdateFlag(data[13]);
            pojo.setConstitutionType(data[14]);
            pojo.setAccHolderTypeFlag(data[15]);
            pojo.setAccHolderType(data[16]);
            pojo.setAccType(data[17]);
            pojo.setcKYCno(data[18]);
            pojo.setCustNamePrefix(data[19]);
            pojo.setCustFirstName(data[20]);
            pojo.setCustMiddleName(data[21]);
            pojo.setCustLastName(data[22]);
            pojo.setCustFullName(data[23]);
            pojo.setMaidenNamePrefix(data[24]);
            pojo.setMaidenFirstName(data[25]);
            pojo.setMaidenMiddleName(data[26]);
            pojo.setMaidenLastName(data[27]);
            pojo.setMaidenFullName(data[28]);
            pojo.setFather_spouse_flag(data[29]);
            pojo.setFatherSpouseNamePrefix(data[30]);
            pojo.setFatherSpouseFirstName(data[31]);
            pojo.setFatherSpouseMiddleName(data[32]);
            pojo.setFatherSpouseLastName(data[33]);
            pojo.setFatherSpouseFullName(data[34]);
            pojo.setMotherNamePrefix(data[35]);
            pojo.setMotherFirstName(data[36]);
            pojo.setMotherMiddleName(data[37]);
            pojo.setMotherLastName(data[38]);
            pojo.setMotherFullName(data[39]);
            pojo.setGender(data[40]);
            pojo.setMaritalStatus(data[41]);
            pojo.setNationality(data[42]);
            pojo.setOccupationType(data[43]);
            pojo.setDateOfBirth(data[44].equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(data[44])));
            pojo.setPlaceOfIncorporation(data[45]);
            pojo.setDateOfCommBusiness(data[46].equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(data[46])));
            pojo.setCountryOfIncorporation(data[47]);
            pojo.setResidenceCountryTaxLaw(data[48]);
            pojo.setIdentificationType(data[49]);
            pojo.settINNo(data[50]);
            pojo.settINIssuingCountry(data[51]);
            pojo.setpAN(data[52]);
            pojo.setResidentialStatus(data[53]);
            pojo.setFlagCustResiForTaxJuriOutsideIN(data[54]);
            pojo.setJuriOfResidence(data[55]);
            pojo.setJuriTaxIdentificationNo(data[56]);
            pojo.setCountryOfBirth(data[57]);
            pojo.setPlaceOfBirth(data[58]);
            pojo.setPerAddType(data[59]);
            pojo.setPerAddressLine1(data[60]);
            pojo.setPeraddressline2(data[61]);
            pojo.setPeraddressline3(data[62]);
            pojo.setPerCityVillage(data[63]);
            pojo.setPerDistrict(data[64]);
            pojo.setPerState(data[65]);
            pojo.setPerCountryCode(data[66]);
            pojo.setPerPostalCode(data[67]);
            pojo.setPerPOA(data[68]);
            pojo.setPerOtherPOA(data[69]);
            pojo.setPerMailAddSameFlagIndicate(data[70]);
            pojo.setMailAddType(data[71]);
            pojo.setMailAddressLine1(data[72]);
            pojo.setMailAddressLine2(data[73]);
            pojo.setMailAddressLine3(data[74]);
            pojo.setMailCityVillage(data[75]);
            pojo.setMailDistrict(data[76]);
            pojo.setMailState(data[77]);
            pojo.setMailCountry(data[78]);
            pojo.setMailPostalCode(data[79]);
            pojo.setMailPOA(data[80]);
            pojo.setJuriAddBasedOnFlag(data[81]);
            pojo.setJuriAddType(data[82]);
            pojo.setJuriAddressLine1(data[83]);
            pojo.setJuriAddressLine2(data[84]);
            pojo.setJuriAddressLine3(data[85]);
            pojo.setJuriCityVillage(data[86]);
            pojo.setJuriState(data[87]);
            pojo.setJuriCountry(data[88]);
            pojo.setJuriPostCode(data[89]);
            pojo.setJuriPOA(data[90]);
            pojo.setResidenceTelSTDCode(data[91]);
            pojo.setResidenceTelNo(data[92]);
            pojo.setOfficeTeleSTDCode(data[93]);
            pojo.setOfficeTelNo(data[94]);
            pojo.setiSDCode(data[95]);
            pojo.setMobileNo(data[96]);
            pojo.setFaxSTDCode(data[97]);
            pojo.setFaxNo(data[98]);
            pojo.setEmailID(data[99]);
            pojo.setRemarks(data[100]);
            pojo.setDateOfDeclaration(data[101].equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(data[101])));
            pojo.setPlaceOfDeclaration(data[102]);
            pojo.setkYCVerificationDate(data[103].equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(data[103])));
            pojo.setTypeOfDocSubmitted(data[104]);
            pojo.setkYCVerificationName(data[105]);
            pojo.setkYCVerificDesignation(data[106]);
            pojo.setkYCVerificBranch(data[107]);
            pojo.setkYCVerificEMPCode(data[108]);
            pojo.setOrganisationName(data[109]);
            pojo.setOrganisationCode(data[110]);
            pojo.setNoOfIdentityDetails(data[111]);
            pojo.setNoOfrelatedpeople(data[112]);
            pojo.setNoOfControllingPersonResiOutsideIN(data[113]);
            pojo.setNoOfLocalAddDetails(data[114]);
            pojo.setNoOfImages(data[115]);
            pojo.setErrorCode(data[116]);
            pojo.setFiller1(data[117]);
            pojo.setFiller2(data[118]);
            pojo.setFiller3(data[119]);
            pojo.setFiller4(data[120]);
        } catch (ParseException ex) {
            throw new Exception(ex.getMessage());
        }
        return pojo;
    }

    public CKYCRDownloadDetail30 setCKYCRDownloadDetail30(String[] data) throws Exception {
        CKYCRDownloadDetail30 pojo = new CKYCRDownloadDetail30();
        pojo.setRecordType(data[0]);
        pojo.setLineNumber(data[1]);
        pojo.setIdentificationtype(data[2]);
        pojo.setIdentityNumber(data[3]);
        pojo.setExpiryDate(data[4].equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(data[4])));
        pojo.setIdProofSubmitted(data[5]);
        pojo.setIdVerificationStatus(data[6]);
        pojo.setFiller1(data[7]);
        pojo.setFiller2(data[8]);
        pojo.setFiller3(data[9]);
        pojo.setFiller4(data[10]);
        return pojo;
    }

    public CKYCRDownloadDetail60 setCKYCRDownloadDetail60(String[] data) throws Exception {
        CKYCRDownloadDetail60 pojo = new CKYCRDownloadDetail60();
        pojo.setSeqNo("");
        pojo.setRecordType(data[0]);
        pojo.setLineNumber(data[1]);
        pojo.setBranchCode(data[2]);
        pojo.setAddressType(data[3]);
        pojo.setLocalAddressLine1(data[4]);
        pojo.setLocalAddressLine2(data[5]);
        pojo.setLocalAddressLine3(data[6]);
        pojo.setLocalAddressCityVillage(data[7]);
        pojo.setLocalAddressDistrict(data[8]);
        pojo.setLocalAddressPINCode(data[9]);
        pojo.setLocalAddressState(data[10]);
        pojo.setLocalAddressCountry(data[11]);
        pojo.setProofofAdd(data[12]);
        pojo.setResiTelSTDCode(data[13]);
        pojo.setResiTelNo(data[14]);
        pojo.setOfficeTelSTDCode(data[15]);
        pojo.setOfficeTelNo(data[16]);
        pojo.setMobISDCode(data[17]);
        pojo.setMobileNo(data[18]);
        pojo.setFaxSTDCode(data[19]);
        pojo.setFaxNo(data[20]);
        pojo.setEmailID(data[21]);
        pojo.setDateofDeclaration(data[22].equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(data[22])));
        pojo.setPlaceofDeclaration(data[23]);
        pojo.setFiller1(data[24]);
        pojo.setFiller2(data[25]);
        pojo.setFiller3(data[26]);
        pojo.setFiller4(data[27]);
        return pojo;
    }

    public List<CKYCRRequestPojo> getInfoForPrintCKYCRReport(String branchCode, String mode, String reportType, String fromDate, String toDate) throws ApplicationException {
        List<CKYCRRequestPojo> ckycrReportPojoList = new ArrayList<CKYCRRequestPojo>();
        List selectList = new ArrayList();
        try {
            if (reportType.equalsIgnoreCase("SUCCESS")) {
                if (branchCode.equalsIgnoreCase("90")) {
                    selectList = em.createNativeQuery("select t.tmode, t.ttype, t.custId,t.treqDt,t.treqBy,t.tBr,t.tst,t.tckycrNo,custfullName,PAN_GIRNumber from (\n"
                            + "select crd.Mode as tmode, crd.Type as ttype, crd.CustomerId_CKYCR_No as custId,\n"
                            + "DATE_FORMAT(crd.Request_Date,'%d-%m-%Y') as treqDt, crd.Request_By as treqBy, bm.BranchName as tBr, crd.Status as tst,\n"
                            + "cm.CKYCNo tckycrNo,custfullName,PAN_GIRNumber\n"
                            + "from ckycr_request_detail crd, branchmaster bm, cbs_customer_master_detail\n"
                            + "cm where crd.Mode='" + mode + "' and crd.Status in('01','03') and crd.Request_date >= '" + fromDate + "'\n"
                            + "and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode and cm.customerid = crd.CustomerId_CKYCR_No) t").getResultList();
                } else {
                    selectList = em.createNativeQuery("select t.tmode, t.ttype, t.custId,t.treqDt,t.treqBy,t.tBr,t.tst,t.tckycrNo,custfullName,PAN_GIRNumber from (\n"
                            + "select crd.Mode as tmode, crd.Type as ttype, crd.CustomerId_CKYCR_No as custId,\n"
                            + "DATE_FORMAT(crd.Request_Date,'%d-%m-%Y') as treqDt, crd.Request_By as treqBy, bm.BranchName as tBr, crd.Status as tst,\n"
                            + "cm.CKYCNo tckycrNo,custfullName,PAN_GIRNumber\n"
                            + "from ckycr_request_detail crd, branchmaster bm, cbs_customer_master_detail\n"
                            + "cm where crd.Branch_Code ='" + branchCode + "' and crd.Mode='" + mode + "' and crd.Status in('01','03') and crd.Request_date >= '" + fromDate + "'\n"
                            + "and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode and cm.customerid = crd.CustomerId_CKYCR_No) t").getResultList();
                }


                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no data to show.");
                } else {
                    if (selectList.size() > 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            Vector vec = (Vector) selectList.get(i);
                            CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                            ckycrPojo.setMode(vec.get(0).toString().toUpperCase());
                            ckycrPojo.setType(vec.get(1).toString().toUpperCase());
                            ckycrPojo.setCustomerId(vec.get(2).toString());
                            ckycrPojo.setCustomerIdOrCKYCRNo(vec.get(7).toString());
                            ckycrPojo.setRequestDate(vec.get(3).toString());
                            ckycrPojo.setRequestBy(vec.get(4).toString().toUpperCase());
                            ckycrPojo.setPrimaryBrName(vec.get(5).toString().toUpperCase());
                            ckycrPojo.setCustName(vec.get(8).toString().toUpperCase());
                            ckycrPojo.setPanGirNumber(vec.get(9).toString().toUpperCase());
                            ckycrReportPojoList.add(ckycrPojo);
                        }
                    }
                }

            } else if (reportType.equalsIgnoreCase("ENTERED")) {
                if (branchCode.equalsIgnoreCase("90")) {
                    selectList = em.createNativeQuery("select crd.Mode, crd.Type, crd.CustomerId_CKYCR_No, DATE_FORMAT(crd.Request_Date,'%d-%m-%Y') as requestDate, crd.Request_By, bm.BranchName from ckycr_request_detail crd, branchmaster bm where crd.Mode='" + mode + "' and crd.Status = '" + reportType + "' and crd.Request_date >= '" + fromDate + "' and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode").getResultList();
                } else {
                    selectList = em.createNativeQuery("select crd.Mode, crd.Type, crd.CustomerId_CKYCR_No, DATE_FORMAT(crd.Request_Date,'%d-%m-%Y') as requestDate, crd.Request_By, bm.BranchName from ckycr_request_detail crd, branchmaster bm where crd.Branch_Code ='" + branchCode + "' and crd.Mode='" + mode + "' and crd.Status = '" + reportType + "' and crd.Request_date >= '" + fromDate + "' and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode").getResultList();
                }
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no data to show.");
                } else {
                    if (selectList.size() > 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            Vector vec = (Vector) selectList.get(i);
                            CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                            ckycrPojo.setMode(vec.get(0).toString().toUpperCase());
                            ckycrPojo.setType(vec.get(1).toString().toUpperCase());
                            ckycrPojo.setCustomerId(vec.get(2).toString());
                            ckycrPojo.setRequestDate(vec.get(3).toString());
                            ckycrPojo.setRequestBy(vec.get(4).toString().toUpperCase());
                            ckycrPojo.setPrimaryBrName(vec.get(5).toString().toUpperCase());
                            ckycrReportPojoList.add(ckycrPojo);
                        }
                    }
                }
            } else if (mode.equalsIgnoreCase("UPLOAD") && reportType.equalsIgnoreCase("ENTERED-RESEND")) {
                if (branchCode.equalsIgnoreCase("90")) {
                    selectList = em.createNativeQuery("select crd.Mode, crd.Type, crd.CustomerId_CKYCR_No, DATE_FORMAT(crd.Request_Date,'%d-%m-%Y') as requestDate, crd.Request_By, bm.BranchName,cmd.custfullName,cmd.PAN_GIRNumber "
                            + "from ckycr_request_detail crd, branchmaster bm,cbs_customer_master_detail cmd where crd.Mode='" + mode + "' and crd.Status in('ENTERED','RESEND') "
                            + "and crd.Request_date >= '" + fromDate + "' and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode and cmd.customerid = crd.CustomerId_CKYCR_No").getResultList();
                } else {
                    selectList = em.createNativeQuery("select crd.Mode, crd.Type, crd.CustomerId_CKYCR_No, DATE_FORMAT(crd.Request_Date,'%d-%m-%Y') as requestDate, crd.Request_By, bm.BranchName,cmd.custfullName,cmd.PAN_GIRNumber "
                            + "from ckycr_request_detail crd, branchmaster bm,cbs_customer_master_detail cmd where crd.Branch_Code ='" + branchCode + "' and crd.Mode='" + mode + "' and crd.Status in('ENTERED','RESEND') "
                            + "and crd.Request_date >= '" + fromDate + "' and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode and cmd.customerid = crd.CustomerId_CKYCR_No").getResultList();
                }
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no data to show.");
                } else {
                    if (selectList.size() > 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            Vector vec = (Vector) selectList.get(i);
                            CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                            ckycrPojo.setMode(vec.get(0).toString().toUpperCase());
                            ckycrPojo.setType(vec.get(1).toString().toUpperCase());
                            ckycrPojo.setCustomerId(vec.get(2).toString());
                            ckycrPojo.setRequestDate(vec.get(3).toString());
                            ckycrPojo.setRequestBy(vec.get(4).toString().toUpperCase());
                            ckycrPojo.setPrimaryBrName(vec.get(5).toString().toUpperCase());
                            ckycrPojo.setCustName(vec.get(6).toString().toUpperCase());
                            ckycrPojo.setPanGirNumber(vec.get(7).toString().toUpperCase());
                            ckycrReportPojoList.add(ckycrPojo);
                        }
                    }
                }
            } else {//UPLOADED
                if (branchCode.equalsIgnoreCase("90")) {
                    selectList = em.createNativeQuery("select crd.Mode, crd.Type, crd.CustomerId_CKYCR_No, DATE_FORMAT(crd.Request_Date,'%d/%m/%Y') as requestDate, crd.Request_By, bm.BranchName,cmd.custfullName,cmd.PAN_GIRNumber "
                            + "from ckycr_request_detail crd, branchmaster bm,cbs_customer_master_detail cmd where crd.Mode='" + mode + "' and crd.Status = '" + reportType + "' "
                            + "and crd.Request_date >= '" + fromDate + "' and crd.Request_date <='" + toDate + "' and crd.Branch_Code = bm.BrnCode and cmd.customerid = crd.CustomerId_CKYCR_No").getResultList();
                } else {
                    selectList = em.createNativeQuery("select crd.Mode, crd.Type, crd.CustomerId_CKYCR_No, DATE_FORMAT(crd.Request_Date,'%d/%m/%Y') as requestDate, crd.Request_By, bm.BranchName,cmd.custfullName,cmd.PAN_GIRNumber "
                            + "from ckycr_request_detail crd, branchmaster bm,cbs_customer_master_detail cmd where crd.Branch_Code ='" + branchCode + "' and crd.Mode='" + mode + "' "
                            + "and crd.Status = '" + reportType + "' and crd.Request_date >= '" + fromDate + "' and crd.Request_date <='" + toDate + "' "
                            + "and crd.Branch_Code = bm.BrnCode and cmd.customerid = crd.CustomerId_CKYCR_No").getResultList();
                }
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no data to show.");
                } else {
                    if (selectList.size() > 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            Vector vec = (Vector) selectList.get(i);
                            CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                            ckycrPojo.setMode(vec.get(0).toString().toUpperCase());
                            ckycrPojo.setType(vec.get(1).toString().toUpperCase());
                            ckycrPojo.setCustomerId(vec.get(2).toString());
                            ckycrPojo.setRequestDate(vec.get(3).toString());
                            ckycrPojo.setRequestBy(vec.get(4).toString().toUpperCase());
                            ckycrPojo.setPrimaryBrName(vec.get(5).toString().toUpperCase());
                            ckycrPojo.setCustName(vec.get(6).toString().toUpperCase());
                            ckycrPojo.setPanGirNumber(vec.get(7).toString().toUpperCase());
                            ckycrReportPojoList.add(ckycrPojo);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return ckycrReportPojoList;
    }

    public CKYCRDownloadPojo getCKYCRClientDetails(String ckycrNo, String dob) throws ApplicationException {

        CKYCRDownloadPojo pojo = new CKYCRDownloadPojo();
        try {

            String condition = "";
            String dateOfBirth;
            if (dob.equalsIgnoreCase("") && !(ckycrNo.equalsIgnoreCase(""))) {
                condition = "(CKYCno='" + ckycrNo + "' )";
            } else if (!(dob.equalsIgnoreCase("") && ckycrNo.equalsIgnoreCase(""))) {
                dateOfBirth = ymdWihtoutSeparator.format(dmyFormat.parse(dob));
                condition = "(CKYCno='" + ckycrNo + "' and DateOfBirth='" + dateOfBirth + "')";
            }

//            String DateOfBirth = dob.equals("") ? "19000101" : ymdWihtoutSeparator.format(dmyFormat.parse(dob));
            String insertquery = "select ConstitutionType, AccHolderTypeFlag, AccHolderType, AccType, CKYCno, CustNamePrefix,"
                    + " CustFirstName, CustMiddleName, CustLastName, CustFullName, MaidenFullName, father_spouse_flag, "
                    + "FatherSpouseFullName, MotherFullName, Gender, MaritalStatus, "
                    + "Nationality, OccupationType, DateOfBirth, PlaceOfIncorporation, DateOfCommBusiness, "
                    + "CountryOfIncorporation, ResidenceCountryTaxLaw, IdentificationType, TINNo, TINIssuingCountry, "
                    + "PAN, ResidentialStatus, FlagCustResiForTaxJuriOutsideIN, JuriOfResidence, JuriTaxIdentificationNo,"
                    + " CountryOfBirth, PlaceOfBirth, PerAddType, PerAddressLine1, peraddressline2, peraddressline3, "
                    + "PerCityVillage, PerDistrict, PerState, PerCountryCode, PerPostalCode, PerPOA, PerOtherPOA, "
                    + "PerMailAddSameFlagIndicate, MailAddType, MailAddressLine1, MailAddressLine2, MailAddressLine3,"
                    + " MailCityVillage, MailDistrict, MailState, MailCountry, MailPostalCode, MailPOA,"
                    + " JuriAddBasedOnFlag, JuriAddType, JuriAddressLine1, JuriAddressLine2, JuriAddressLine3, "
                    + "JuriCityVillage, JuriState, JuriCountry, JuriPostCode, JuriPOA, ResidenceTelSTDCode,"
                    + " ResidenceTelNo, OfficeTeleSTDCode, OfficeTelNo, ISDCode, MobileNo, FaxSTDCode, FaxNo,"
                    + " EmailID, Remarks from ckycr_download where  " + condition + "  and TxnNo=(select "
                    + "COALESCE( max(TxnNo) ,'') from ckycr_download where CKYCno='" + ckycrNo + "')";

            List selectList = em.createNativeQuery(insertquery).getResultList();

            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    String constType = ParseFileUtil.addTrailingZeros(vec.get(0).toString(), 2);
                    pojo.setDataExist(true);
                    pojo.setConstitutionType(getRefDiscription(constType, "044"));
                    pojo.setConstitutionCode(constType);
                    pojo.setcKYCno(vec.get(4).toString());

                    if (constType.equalsIgnoreCase("01")) {
                        pojo.setCustNamePrefix(getRefDiscription(vec.get(5).toString(), "045"));
                        pojo.setCustFirstName(vec.get(6).toString().toUpperCase());
                        pojo.setCustMiddleName(vec.get(7).toString().toUpperCase());
                        pojo.setCustLastName(vec.get(8).toString().toUpperCase());
                        pojo.setMaidenFullName(vec.get(10).toString().toUpperCase());
                        pojo.setFather_spouse_flag(vec.get(11).toString().toUpperCase());
                        pojo.setFatherSpouseFullName(vec.get(12).toString().toUpperCase());
                        pojo.setMotherFullName(vec.get(13).toString().toUpperCase());
                        pojo.setMaritalStatus(getRefDiscription(vec.get(15).toString(), "152"));
                        pojo.setNationality(getRefDiscription(vec.get(16).toString(), "302"));
                        pojo.setOccupationType(getRefDiscription(vec.get(17).toString(), "363"));
                        pojo.setIdentificationType(getRefDiscription(vec.get(23).toString(), "311"));
                        pojo.setAccType(getRefDiscription(vec.get(3).toString(), "361"));
                        pojo.setCustFullName(pojo.getCustNamePrefix() + " " + pojo.getCustFirstName() + " "
                                + pojo.getCustMiddleName() + " " + pojo.getCustLastName().toUpperCase());
                    } else {
                        pojo.setAccHolderTypeFlag(getRefDiscription(vec.get(1).toString(), "358"));
                        pojo.setAccHolderType(getRefDiscription(vec.get(2).toString(), "359"));
                        pojo.setCustFullName(vec.get(9).toString().toUpperCase());
                        pojo.setPlaceOfIncorporation(vec.get(19).toString());
                        pojo.setDateOfCommBusiness(vec.get(20).toString().equals("") ? "" : dmyFormat.format(ymdWihtoutSeparator.parse(vec.get(20).toString())));
                        pojo.setCountryOfIncorporation(getRefDiscription(vec.get(21).toString(), "302"));
                        pojo.setResidenceCountryTaxLaw(getRefDiscription(vec.get(22).toString(), "302"));
                        pojo.setIdentificationType(getRefDiscription(vec.get(23).toString(), "304"));
                    }

                    pojo.setGender(getRefDiscription(vec.get(14).toString(), "012"));
                    pojo.setDateOfBirth(vec.get(18).toString().equals("") ? "" : dmyFormat.format(ymdWihtoutSeparator.parse(vec.get(18).toString())));

                    pojo.settINNo(vec.get(24).toString());
                    pojo.settINIssuingCountry(getRefDiscription(vec.get(25).toString(), "302"));

                    pojo.setpAN(vec.get(26).toString());
                    pojo.setResidentialStatus(getRefDiscription(vec.get(27).toString(), "303"));
                    pojo.setFlagCustResiForTaxJuriOutsideIN(vec.get(28).toString());

                    pojo.setJuriOfResidence(getRefDiscription(vec.get(29).toString(), "302"));
                    pojo.setJuriTaxIdentificationNo(vec.get(30).toString());
                    pojo.setCountryOfBirth(getRefDiscription(vec.get(31).toString(), "302"));
                    pojo.setPlaceOfBirth(vec.get(32).toString());

                    pojo.setPerAddType(getRefDiscription(vec.get(33).toString(), "354"));
                    pojo.setPerAddressLine1(vec.get(34).toString());
                    pojo.setPeraddressline2(vec.get(35).toString());
                    pojo.setPeraddressline3(vec.get(36).toString());
                    pojo.setPerCityVillage(vec.get(37).toString());
                    pojo.setPerDistrict(vec.get(38).toString());
                    pojo.setPerState(getRefDiscription(vec.get(39).toString(), "002"));
                    pojo.setPerCountryCode(getRefDiscription(vec.get(40).toString(), "302"));
                    pojo.setPerPostalCode(vec.get(41).toString());
                    pojo.setPerPOA(getRefDiscription(vec.get(42).toString(), "312"));
                    pojo.setPerOtherPOA(vec.get(43).toString());

                    pojo.setPerMailAddSameFlagIndicate(vec.get(44).toString());
                    pojo.setMailAddType(getRefDiscription(vec.get(45).toString(), "354"));
                    pojo.setMailAddressLine1(vec.get(46).toString());
                    pojo.setMailAddressLine2(vec.get(47).toString());
                    pojo.setMailAddressLine3(vec.get(48).toString());
                    pojo.setMailCityVillage(vec.get(49).toString());
                    pojo.setMailDistrict(vec.get(50).toString());
                    pojo.setMailState(getRefDiscription(vec.get(51).toString(), "002"));
                    pojo.setMailCountry(getRefDiscription(vec.get(52).toString(), "302"));
                    pojo.setMailPostalCode(vec.get(53).toString());
                    pojo.setMailPOA(getRefDiscription(vec.get(54).toString(), "312"));

                    pojo.setJuriAddBasedOnFlag(getRefDiscription(vec.get(55).toString(), "355"));
                    pojo.setJuriAddType(getRefDiscription(vec.get(56).toString(), "354"));
                    pojo.setJuriAddressLine1(vec.get(57).toString());
                    pojo.setJuriAddressLine2(vec.get(58).toString());
                    pojo.setJuriAddressLine3(vec.get(59).toString());
                    pojo.setJuriCityVillage(vec.get(60).toString());
                    pojo.setJuriState(getRefDiscription(vec.get(61).toString(), "002"));
                    pojo.setJuriCountry(getRefDiscription(vec.get(62).toString(), "302"));
                    pojo.setJuriPostCode(vec.get(63).toString());
                    pojo.setJuriPOA(getRefDiscription(vec.get(64).toString(), "312"));

                    pojo.setResidenceTelSTDCode(vec.get(65).toString());
                    pojo.setResidenceTelNo(vec.get(66).toString());
                    pojo.setOfficeTeleSTDCode(vec.get(67).toString());
                    pojo.setOfficeTelNo(vec.get(68).toString());
                    pojo.setiSDCode(vec.get(69).toString());
                    pojo.setMobileNo(vec.get(70).toString());
                    pojo.setFaxSTDCode(vec.get(71).toString());
                    pojo.setFaxNo(vec.get(72).toString());
                    pojo.setEmailID(vec.get(73).toString());
                }
            } else {
                pojo.setDataExist(false);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    public String getRefDiscription(String refCode, String refRecNo) {
        List selectList = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where REF_CODE='" + refCode + "' and REF_REC_NO='" + refRecNo + "'").getResultList();
        String description = "";
        if (selectList.size() > 0) {
            Vector vec = (Vector) selectList.get(0);
            description = vec.get(0).toString();
        }
        return description;
    }

    public List<CKYCRRequestPojo> getDownloadCKYCRReport(String branchCode, String mode, String reportType, String fromDate, String toDate) throws ApplicationException {
        List<CKYCRRequestPojo> ckycrReportPojoList = new ArrayList<CKYCRRequestPojo>();
        String query = "";
        if (branchCode.equalsIgnoreCase("90")) {
            query = "select Mode,Type,CustomerId_CKYCR_No,DATE_FORMAT(Request_Date,'%d-%m-%Y') as requestDate,Request_By,bm.BranchName from "
                    + "ckycr_request_detail crd, branchmaster bm where Mode='" + mode + "'and crd.Branch_Code = bm.BrnCode and crd.Request_date >= '" + fromDate + "'"
                    + " and crd.Request_date <='" + toDate + "' and crd.Status='" + reportType + "'";
        } else {
            query = "select Mode,Type,CustomerId_CKYCR_No,DATE_FORMAT(Request_Date,'%d-%m-%Y') as requestDate,Request_By,bm.BranchName from "
                    + "ckycr_request_detail crd, branchmaster bm where Mode='" + mode + "'and crd.Branch_Code = bm.BrnCode and crd.Request_date >= '" + fromDate + "'"
                    + " and crd.Request_date <='" + toDate + "' and crd.Status='" + reportType + "' and crd.Branch_Code='" + branchCode + "'";
        }
        List selectList = em.createNativeQuery(query).getResultList();
        if (selectList.isEmpty()) {
            throw new ApplicationException("There is no data to show.");
        } else {
            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    CKYCRRequestPojo ckycrPojo = new CKYCRRequestPojo();
                    ckycrPojo.setMode(vec.get(0).toString().toUpperCase());
                    ckycrPojo.setType(vec.get(1).toString().toUpperCase());
                    ckycrPojo.setCustomerIdOrCKYCRNo(vec.get(2).toString());
                    ckycrPojo.setRequestDate(vec.get(3).toString());
                    ckycrPojo.setRequestBy(vec.get(4).toString().toUpperCase());
                    ckycrPojo.setPrimaryBrName(vec.get(5).toString().toUpperCase());
                    ckycrReportPojoList.add(ckycrPojo);
                }
            }
        }
        return ckycrReportPojoList;
    }

    private CKYCRDownloadPojo getCKYCRData20(String ckycrNo, String dob) throws ApplicationException {
        CKYCRDownloadPojo pojo = new CKYCRDownloadPojo();
        try {
            String condition = "";
            String dateOfBirth = "";
            if (dob.equalsIgnoreCase("") && !(ckycrNo.equalsIgnoreCase(""))) {
                condition = "(CKYCno='" + ckycrNo + "' )";
            } else if (!(dob.equalsIgnoreCase("") && ckycrNo.equalsIgnoreCase(""))) {
                dateOfBirth = ymdWihtoutSeparator.format(dmyFormat.parse(dob));
                condition = "(CKYCno='" + ckycrNo + "' and DateOfBirth='" + dateOfBirth + "')";
            }
            String ckycrDetaialQuery = "select ConstitutionType,AccHolderTypeFlag,AccHolderType,AccType,CKYCno,CustNamePrefix,"
                    + "CustFirstName,CustMiddleName,CustLastName,CustFullName,MaidenNamePrefix,MaidenFirstName,"
                    + "MaidenMiddleName,MaidenLastName,MaidenFullName,father_spouse_flag,FatherSpouseNamePrefix,"
                    + "FatherSpouseFirstName,FatherSpouseMiddleName,FatherSpouseLastName,FatherSpouseFullName,"
                    + "MotherNamePrefix,MotherFirstName,MotherMiddleName,MotherLastName,MotherFullName,Gender,"
                    + "MaritalStatus,Nationality,OccupationType,DateOfBirth,PlaceOfIncorporation,DateOfCommBusiness,"
                    + "CountryOfIncorporation,ResidenceCountryTaxLaw,IdentificationType,TINNo,TINIssuingCountry,"
                    + "PAN,ResidentialStatus,FlagCustResiForTaxJuriOutsideIN,JuriOfResidence,JuriTaxIdentificationNo,"
                    + "CountryOfBirth,PlaceOfBirth,PerAddType,PerAddressLine1,peraddressline2,peraddressline3,"
                    + "PerCityVillage,PerDistrict,PerState,PerCountryCode,PerPostalCode,PerPOA,PerOtherPOA,"
                    + "PerMailAddSameFlagIndicate,MailAddType,MailAddressLine1,MailAddressLine2,MailAddressLine3,"
                    + "MailCityVillage,MailDistrict,MailState,MailCountry,MailPostalCode,MailPOA,JuriAddBasedOnFlag,"
                    + "JuriAddType,JuriAddressLine1,JuriAddressLine2,JuriAddressLine3,JuriCityVillage,JuriState,"
                    + "JuriCountry,JuriPostCode,JuriPOA,ResidenceTelSTDCode,ResidenceTelNo,OfficeTeleSTDCode,"
                    + "OfficeTelNo,ISDCode,MobileNo,FaxSTDCode,FaxNo,EmailID,Remarks,DateOfDeclaration,PlaceOfDeclaration,"
                    + "KYCVerificationDate,TypeOfDocSubmitted,KYCVerificationName,KYCVerificDesignation,KYCVerificBranch,"
                    + "KYCVerificEMPCode,OrganisationName,OrganisationCode,NoOfIdentityDetails,NoOfrelatedpeople,"
                    + "NoOfControllingPersonResiOutsideIN,NoOfLocalAddDetails,NoOfImages,ErrorCode from ckycr_download "
                    + "where " + condition + " and "
                    + "(TxnNo=(select COALESCE(max(TxnNo) ,'') from ckycr_download where " + condition + "))";
            List selectList = em.createNativeQuery(ckycrDetaialQuery).getResultList();

            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {

                    Vector vec = (Vector) selectList.get(i);
                    pojo.setDataExist(true);
                    String constType = ParseFileUtil.addTrailingZeros(vec.get(0).toString(), 2);
                    pojo.setConstitutionCode(constType);
                    pojo.setAccHolderTypeFlag(vec.get(1).toString());
                    pojo.setAccHolderType(vec.get(2).toString());
                    pojo.setAccType(vec.get(3).toString());
                    pojo.setcKYCno(vec.get(4).toString());
                    pojo.setCustNamePrefix(vec.get(5).toString());
                    pojo.setCustFirstName(vec.get(6).toString());
                    pojo.setCustMiddleName(vec.get(7).toString());
                    pojo.setCustLastName(vec.get(8).toString());
                    pojo.setCustFullName(vec.get(9).toString());
                    pojo.setMaidenNamePrefix(vec.get(10).toString());
                    pojo.setMaidenFirstName(vec.get(11).toString());
                    pojo.setMaidenMiddleName(vec.get(12).toString());
                    pojo.setMaidenLastName(vec.get(13).toString());
                    pojo.setMaidenFullName(vec.get(14).toString());
                    pojo.setFather_spouse_flag(vec.get(15).toString());
                    pojo.setFatherSpouseNamePrefix(vec.get(16).toString());
                    pojo.setFatherSpouseFirstName(vec.get(17).toString());
                    pojo.setFatherSpouseMiddleName(vec.get(18).toString());
                    pojo.setFatherSpouseLastName(vec.get(19).toString());
                    pojo.setFatherSpouseFullName(vec.get(20).toString());
                    pojo.setMotherNamePrefix(vec.get(21).toString());
                    pojo.setMotherFirstName(vec.get(22).toString());
                    pojo.setMotherMiddleName(vec.get(23).toString());
                    pojo.setMotherLastName(vec.get(24).toString());
                    pojo.setMotherFullName(vec.get(25).toString());
                    pojo.setGender(vec.get(26).toString());
                    pojo.setMaritalStatus(vec.get(27).toString());
                    pojo.setNationality(vec.get(28).toString());
                    pojo.setOccupationType(vec.get(29).toString());
                    pojo.setDateOfBirth(vec.get(30).toString());
                    pojo.setPlaceOfIncorporation(vec.get(31).toString());
                    pojo.setDateOfCommBusiness(vec.get(32).toString());
                    pojo.setCountryOfIncorporation(vec.get(33).toString());
                    pojo.setResidenceCountryTaxLaw(vec.get(34).toString());
                    pojo.setIdentificationType(vec.get(35).toString());
                    pojo.settINNo(vec.get(36).toString());
                    pojo.settINIssuingCountry(vec.get(37).toString());
                    pojo.setpAN(vec.get(38).toString());
                    pojo.setResidentialStatus(vec.get(39).toString());
                    pojo.setFlagCustResiForTaxJuriOutsideIN(vec.get(40).toString());
                    pojo.setJuriOfResidence(vec.get(41).toString());
                    pojo.setJuriTaxIdentificationNo(vec.get(42).toString());
                    pojo.setCountryOfBirth(vec.get(43).toString());
                    pojo.setPlaceOfBirth(vec.get(44).toString());
                    pojo.setPerAddType(vec.get(45).toString());
                    pojo.setPerAddressLine1(vec.get(46).toString());
                    pojo.setPeraddressline2(vec.get(47).toString());
                    pojo.setPeraddressline3(vec.get(48).toString());
                    pojo.setPerCityVillage(vec.get(49).toString());
                    pojo.setPerDistrict(vec.get(50).toString());
                    pojo.setPerState(vec.get(51).toString());
                    pojo.setPerCountryCode(vec.get(52).toString());
                    pojo.setPerPostalCode(vec.get(53).toString());
                    pojo.setPerPOA(vec.get(54).toString());
                    pojo.setPerOtherPOA(vec.get(55).toString());
                    pojo.setPerMailAddSameFlagIndicate(vec.get(56).toString());
                    pojo.setMailAddType(vec.get(57).toString());
                    pojo.setMailAddressLine1(vec.get(58).toString());
                    pojo.setMailAddressLine2(vec.get(59).toString());
                    pojo.setMailAddressLine3(vec.get(60).toString());
                    pojo.setMailCityVillage(vec.get(61).toString());
                    pojo.setMailDistrict(vec.get(62).toString());
                    pojo.setMailState(vec.get(63).toString());
                    pojo.setMailCountry(vec.get(64).toString());
                    pojo.setMailPostalCode(vec.get(65).toString());
                    pojo.setMailPOA(vec.get(66).toString());
                    pojo.setJuriAddBasedOnFlag(vec.get(67).toString());
                    pojo.setJuriAddType(vec.get(68).toString());
                    pojo.setJuriAddressLine1(vec.get(69).toString());
                    pojo.setJuriAddressLine2(vec.get(70).toString());
                    pojo.setJuriAddressLine3(vec.get(71).toString());
                    pojo.setJuriCityVillage(vec.get(72).toString());
                    pojo.setJuriState(vec.get(73).toString());
                    pojo.setJuriCountry(vec.get(74).toString());
                    pojo.setJuriPostCode(vec.get(75).toString());
                    pojo.setJuriPOA(vec.get(76).toString());
                    pojo.setResidenceTelSTDCode(vec.get(77).toString());
                    pojo.setResidenceTelNo(vec.get(78).toString());
                    pojo.setOfficeTeleSTDCode(vec.get(79).toString());
                    pojo.setOfficeTelNo(vec.get(80).toString());
                    pojo.setiSDCode(vec.get(81).toString());
                    pojo.setMobileNo(vec.get(82).toString());
                    pojo.setFaxSTDCode(vec.get(83).toString());
                    pojo.setFaxNo(vec.get(84).toString());
                    pojo.setEmailID(vec.get(85).toString());
                    pojo.setRemarks(vec.get(86).toString());
                    pojo.setDateOfDeclaration(vec.get(87).toString());
                    pojo.setPlaceOfDeclaration(vec.get(88).toString());
//                    pojo.setKYCVerificationDate(vec.get(89).toString());
                    pojo.setTypeOfDocSubmitted(vec.get(90).toString());
//                    pojo.setKYCVerificationName(vec.get(91).toString());
//                    pojo.setKYCVerificDesignation(vec.get(92).toString());
//                    pojo.setKYCVerificBranch(vec.get(93).toString());
//                    pojo.setKYCVerificEMPCode(vec.get(94).toString());
                    pojo.setOrganisationName(vec.get(95).toString());
                    pojo.setOrganisationCode(vec.get(96).toString());
                    pojo.setNoOfIdentityDetails(vec.get(97).toString());
                    pojo.setNoOfrelatedpeople(vec.get(98).toString());
                    pojo.setNoOfControllingPersonResiOutsideIN(vec.get(99).toString());
                    pojo.setNoOfLocalAddDetails(vec.get(100).toString());
                    pojo.setNoOfImages(vec.get(101).toString());
                    pojo.setErrorCode(vec.get(102).toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    private List<CKYCRDownloadDetail30> getCKYCRData30(String ckycrNo) throws ApplicationException {
        List<CKYCRDownloadDetail30> detail30List = new ArrayList<CKYCRDownloadDetail30>();

        try {
            String ckycrDetaial30Query = "select CKYCno,RecordType,LineNumber,Identificationtype,IdentityNumber,"
                    + " ExpiryDate,IdProofSubmitted,IdVerificationStatus from ckycr_download_detail_30 "
                    + " where CKYCno='" + ckycrNo + "' and groupTxnId=(select CAST(coalesce(max(groupTxnId),'0') AS UNSIGNED) "
                    + " from ckycr_download_detail_30 where CKYCno='" + ckycrNo + "')";
            List selectListDetaial30 = em.createNativeQuery(ckycrDetaial30Query).getResultList();
            if (selectListDetaial30.size() > 0) {

                for (int i = 0; i < selectListDetaial30.size(); i++) {
                    CKYCRDownloadDetail30 pojoDetail30 = new CKYCRDownloadDetail30();
                    Vector vec = (Vector) selectListDetaial30.get(i);

                    pojoDetail30.setcKYCno(vec.get(0).toString());
                    pojoDetail30.setRecordType(vec.get(1).toString());
                    pojoDetail30.setLineNumber(vec.get(2).toString());
                    pojoDetail30.setIdentificationtype(vec.get(3).toString());
                    pojoDetail30.setIdentityNumber(vec.get(4).toString());
                    pojoDetail30.setExpiryDate(vec.get(5).toString());
                    pojoDetail30.setIdProofSubmitted(vec.get(6).toString());
                    pojoDetail30.setIdVerificationStatus(vec.get(7).toString());
                    detail30List.add(pojoDetail30);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return detail30List;
    }

    private List<CKYCRDownloadDetail60> getCKYCRData60(String ckycrNo) throws ApplicationException {
        List<CKYCRDownloadDetail60> Detail60List = new ArrayList<CKYCRDownloadDetail60>();
        try {
            String ckycrDetaial60Query = "SELECT CKYCno,RecordType,LineNumber,AddressType,LocalAddressLine1,LocalAddressLine2,"
                    + "LocalAddressLine3,LocalAddressCityVillage,LocalAddressDistrict,LocalAddressPINCode,LocalAddressState,"
                    + "LocalAddressCountry,ProofofAdd,ResiTelSTDCode,ResiTelNo,OfficeTelSTDCode,OfficeTelNo,MobISDCode,"
                    + "MobileNo,FaxSTDCode,FaxNo,EmailID,DateofDeclaration,PlaceofDeclaration,SeqNo FROM ckycr_download_detail_60 "
                    + "where CKYCno='" + ckycrNo + "' and groupTxnId=(select CAST(coalesce(max(groupTxnId),'0') AS UNSIGNED) "
                    + " from ckycr_download_detail_60 where CKYCno='" + ckycrNo + "')";
            List selectListDetaial60 = em.createNativeQuery(ckycrDetaial60Query).getResultList();
            if (selectListDetaial60.size() > 0) {

                for (int i = 0; i < selectListDetaial60.size(); i++) {
                    CKYCRDownloadDetail60 pojoDetail60 = new CKYCRDownloadDetail60();
                    Vector vec = (Vector) selectListDetaial60.get(i);

                    pojoDetail60.setcKYCno(vec.get(0).toString());
                    pojoDetail60.setRecordType(vec.get(1).toString());
                    pojoDetail60.setLineNumber(vec.get(2).toString());
                    pojoDetail60.setAddressType(vec.get(3).toString());
                    pojoDetail60.setLocalAddressLine1(vec.get(4).toString());
                    pojoDetail60.setLocalAddressLine2(vec.get(5).toString());
                    pojoDetail60.setLocalAddressCityVillage(vec.get(7).toString());
                    pojoDetail60.setLocalAddressDistrict(vec.get(8).toString());

                    pojoDetail60.setLocalAddressPINCode(vec.get(9).toString());
                    pojoDetail60.setLocalAddressState(vec.get(10).toString());
                    pojoDetail60.setLocalAddressCountry(vec.get(11).toString());
                    pojoDetail60.setProofofAdd(vec.get(12).toString());
                    pojoDetail60.setResiTelSTDCode(vec.get(13).toString());
                    pojoDetail60.setResiTelNo(vec.get(14).toString());
                    pojoDetail60.setOfficeTelSTDCode(vec.get(15).toString());
                    pojoDetail60.setOfficeTelNo(vec.get(16).toString());

                    pojoDetail60.setMobISDCode(vec.get(17).toString());
                    pojoDetail60.setMobileNo(vec.get(18).toString());
                    pojoDetail60.setFaxSTDCode(vec.get(19).toString());
                    pojoDetail60.setFaxNo(vec.get(20).toString());
                    pojoDetail60.setEmailID(vec.get(21).toString());
                    pojoDetail60.setDateofDeclaration(vec.get(22).toString());
                    pojoDetail60.setPlaceofDeclaration(vec.get(23).toString());
                    pojoDetail60.setSeqNo(vec.get(24).toString());

                    Detail60List.add(pojoDetail60);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return Detail60List;
    }

    public String createCustomerIdByCKYCR(String ckycrNo, String dob, String userName, String brncode) throws ApplicationException {
        String msg = "";
        try {
            if (!isCKYCRCustomerIdExist(ckycrNo)) {

                CKYCRDownloadPojo pojo = getCKYCRData20(ckycrNo, dob);


                if (pojo.isDataExist()) {
                    CBSCustomerMasterDetailTO customerMasterDetailTO = new CBSCustomerMasterDetailTO();
                    CBSCustMinorInfoTO minorInfoTO = new CBSCustMinorInfoTO();
                    CBSCustNREInfoTO nREInfoTO = new CBSCustNREInfoTO();
                    CBSCustMISInfoTO mISInfoTO = new CBSCustMISInfoTO();

                    customerMasterDetailTO.setTitle(pojo.getCustNamePrefix());
                    customerMasterDetailTO.setCustname(pojo.getCustFirstName());
                    customerMasterDetailTO.setMiddleName(pojo.getCustMiddleName());
                    customerMasterDetailTO.setLastName(pojo.getCustLastName());
                    customerMasterDetailTO.setGender(pojo.getGender());
                    customerMasterDetailTO.setGstIdentificationNumber("");


                    //Handdle it by father spouse flag//
                    customerMasterDetailTO.setFatherSpouseFlag(pojo.getFather_spouse_flag().equalsIgnoreCase("01") ? "N" : pojo.getFather_spouse_flag().equalsIgnoreCase("02") ? "Y" : "");
                    if (customerMasterDetailTO.getFatherSpouseFlag().equals("Y")) {
                        customerMasterDetailTO.setFathername("NA");
                        customerMasterDetailTO.setFatherMiddleName("NA");
                        customerMasterDetailTO.setFatherLastName("NA");

                        customerMasterDetailTO.setSpouseName(pojo.getFatherSpouseFirstName());
                        customerMasterDetailTO.setSpouseMiddleName(pojo.getFatherSpouseMiddleName());
                        customerMasterDetailTO.setSpouseLastName(pojo.getFatherSpouseLastName());
                    } else if (customerMasterDetailTO.getFatherSpouseFlag().equals("N")) {
                        customerMasterDetailTO.setFathername(pojo.getFatherSpouseFirstName());
                        customerMasterDetailTO.setFatherMiddleName(pojo.getFatherSpouseMiddleName());
                        customerMasterDetailTO.setFatherLastName(pojo.getFatherSpouseLastName());

                        customerMasterDetailTO.setSpouseName("NA");
                        customerMasterDetailTO.setSpouseMiddleName("NA");
                        customerMasterDetailTO.setSpouseLastName("NA");
                    }

                    customerMasterDetailTO.setMothername(pojo.getMotherFirstName());
                    customerMasterDetailTO.setMotherMiddleName(pojo.getMotherMiddleName());
                    customerMasterDetailTO.setMotherLastName(pojo.getMotherLastName());

                    customerMasterDetailTO.setDateOfBirth(ymdWihtoutSeparator.parse(pojo.getDateOfBirth()));
                    customerMasterDetailTO.setMaritalstatus(pojo.getMaritalStatus());
                    customerMasterDetailTO.setMaidenName(pojo.getMaidenFullName());

                    customerMasterDetailTO.setNationality(pojo.getNationality());
                    customerMasterDetailTO.setNriflag(pojo.getResidentialStatus());

                    customerMasterDetailTO.setIsdCode(pojo.getiSDCode());
                    customerMasterDetailTO.setMobilenumber(pojo.getMobileNo());
                    customerMasterDetailTO.setCommunicationPreference(pojo.getPerMailAddSameFlagIndicate().equalsIgnoreCase("Y") ? "P" : "C");

                    customerMasterDetailTO.setpANGIRNumber(pojo.getpAN());

                    beanRemote = (CustomerManagementFacadeRemote) ServiceLocator.getInstance().lookup("CustomerManagementFacade");
                    ParameterinfoReportTO to = beanRemote.getAgeLimit("MINOR-AGE-LIMIT");
                    int minorAgeLimit = to.getCode();
                    int strDateDiff = CbsUtil.yearDiff(ymdWihtoutSeparator.parse(pojo.getDateOfBirth()), new Date());
                    customerMasterDetailTO.setMinorflag(strDateDiff <= minorAgeLimit ? "Y" : "N");

                    if (customerMasterDetailTO.getMinorflag().trim().equalsIgnoreCase("Y")) {
                        minorInfoTO.setGuardianCode("");
                        minorInfoTO.setMajorityDate(new Date());
                    } else {
                        minorInfoTO.setGuardianCode("");
                        minorInfoTO.setMajorityDate(new Date());
                    }

                    customerMasterDetailTO.setCustEntityType(pojo.getConstitutionCode().equalsIgnoreCase("01") ? "01" : "02");
                    customerMasterDetailTO.setAcHolderTypeFlag(pojo.getAccHolderTypeFlag());
                    customerMasterDetailTO.setAcHolderType(pojo.getAccHolderType());
                    customerMasterDetailTO.setAcType(pojo.getAccType());
                    customerMasterDetailTO.setcKYCNo(pojo.getcKYCno());

                    //AADHAAR NO ADDITION
                    customerMasterDetailTO.setRegType("0");
                    customerMasterDetailTO.setAdhaarNo("");
                    customerMasterDetailTO.setLpgId("");
                    customerMasterDetailTO.setAdhaarLpgAcno("");
                    customerMasterDetailTO.setMandateFlag("");
                    customerMasterDetailTO.setMandateDt("");

//                    if (!pojo.getConstitutionCode().equalsIgnoreCase("01")) {
//                        customerMasterDetailTO.setLegalDocument(pojo.getIdentificationType());
//                    }

//                    customerMasterDetailTO.setIncomeRange(custGeneralInfo.getIncomeRange());
//                    customerMasterDetailTO.setNetworth((custGeneralInfo.getNetworth() == null || custGeneralInfo.getNetworth().equals("")) ? Double.parseDouble("0")
//                            : Double.parseDouble(custGeneralInfo.getNetworth()));
//                    customerMasterDetailTO.setNetworthAsOn((custGeneralInfo.getNetworthAsOn() == null || custGeneralInfo.getNetworthAsOn().equals("")) ? "" : dmy.format(custGeneralInfo.getNetworthAsOn()));
//                    customerMasterDetailTO.setQualification(custGeneralInfo.getQualification());
//                    customerMasterDetailTO.setPoliticalExposed(custGeneralInfo.getExposed());
//                    customerMasterDetailTO.setSalary((custGeneralInfo.getSalary() == null
//                            || custGeneralInfo.getSalary().equals("") ? Double.parseDouble("0") : Double.parseDouble(custGeneralInfo.getSalary())));


                    //Identification Detail if Constitution typr  as legal entity.
                    if (!pojo.getConstitutionCode().equalsIgnoreCase("01")) {
                        customerMasterDetailTO.setLegalDocument(pojo.getIdentificationType());
                        customerMasterDetailTO.setIdentityNo(pojo.gettINNo());
                        customerMasterDetailTO.setTinIssuingCountry(pojo.gettINIssuingCountry());
                    }

                    customerMasterDetailTO.setPerOtherPOA("");
                    customerMasterDetailTO.setMailOtherPOA("");
                    customerMasterDetailTO.setJuriOtherPOA("");

                    customerMasterDetailTO.setPoa(pojo.getPerPOA());
                    customerMasterDetailTO.setPerAddType(pojo.getPerAddType());
                    customerMasterDetailTO.setPerAddressLine1(pojo.getPerAddressLine1());
                    customerMasterDetailTO.setPerAddressLine2(pojo.getPeraddressline2());
                    customerMasterDetailTO.setPerVillage(pojo.getPerCityVillage());
                    customerMasterDetailTO.setPerBlock(pojo.getPeraddressline3());
                    customerMasterDetailTO.setPerCountryCode(pojo.getPerCountryCode());

                    customerMasterDetailTO.setPerStateCode(pojo.getPerState());
                    customerMasterDetailTO.setPerDistrict(pojo.getPerDistrict());

                    customerMasterDetailTO.setPerPostalCode(pojo.getPerPostalCode());
                    customerMasterDetailTO.setPerPhoneNumber(pojo.getResidenceTelNo());
                    customerMasterDetailTO.setPerFaxNumber(pojo.getFaxSTDCode() + pojo.getFaxNo());
                    customerMasterDetailTO.setPerEmail(pojo.getEmailID());

                    customerMasterDetailTO.setPerMailAddSameFlagIndicate(pojo.getPerMailAddSameFlagIndicate());
                    customerMasterDetailTO.setMailPoa(pojo.getMailPOA());
                    customerMasterDetailTO.setMailAddType(pojo.getMailAddType());
                    customerMasterDetailTO.setMailAddressLine1(pojo.getMailAddressLine1());
                    customerMasterDetailTO.setMailAddressLine2(pojo.getMailAddressLine2());
                    customerMasterDetailTO.setMailVillage(pojo.getMailCityVillage());
                    customerMasterDetailTO.setMailBlock(pojo.getMailAddressLine3());
                    customerMasterDetailTO.setMailCountryCode(pojo.getMailCountry());
                    customerMasterDetailTO.setMailStateCode(pojo.getMailState());

                    customerMasterDetailTO.setMailDistrict(pojo.getMailDistrict());
                    customerMasterDetailTO.setMailPostalCode(pojo.getMailPostalCode());

                    customerMasterDetailTO.setMailPhoneNumber(pojo.getResidenceTelNo());
                    customerMasterDetailTO.setMailFaxNumber(pojo.getFaxSTDCode() + pojo.getFaxNo());
                    customerMasterDetailTO.setMailEmail(pojo.getEmailID());

                    customerMasterDetailTO.setEmployerid("");
                    customerMasterDetailTO.setEmployeeNo("");
                    customerMasterDetailTO.setEmpAddressLine1("");
                    customerMasterDetailTO.setEmpAddressLine2("");
                    customerMasterDetailTO.setEmpVillage("");
                    customerMasterDetailTO.setEmpBlock("");
                    customerMasterDetailTO.setEmpCityCode("");
                    customerMasterDetailTO.setEmpCountryCode("");
                    if ("IN".equalsIgnoreCase("IN")) {
                        customerMasterDetailTO.setEmpStateCode("");
                        customerMasterDetailTO.setEmpDistrict("");
                    } else {
                        customerMasterDetailTO.setEmpStateCode("");
                        customerMasterDetailTO.setEmpDistrict("");
                    }
                    customerMasterDetailTO.setEmpPostalCode("");
                    customerMasterDetailTO.setEmpPhoneNumber("");
                    customerMasterDetailTO.setEmpTelexNumber("");
                    customerMasterDetailTO.setEmpFaxNumber("");
                    customerMasterDetailTO.setEmailID("");

                    customerMasterDetailTO.setJuriAddBasedOnFlag(pojo.getJuriAddBasedOnFlag());
                    customerMasterDetailTO.setJuriPoa(pojo.getJuriPOA());
                    customerMasterDetailTO.setJuriAddType(pojo.getJuriAddType());
                    customerMasterDetailTO.setJuriAdd1(pojo.getJuriAddressLine1());
                    customerMasterDetailTO.setJuriAdd2(pojo.getJuriAddressLine2());
                    customerMasterDetailTO.setJuriCountry(pojo.getJuriCountry());

                    customerMasterDetailTO.setJuriState(pojo.getJuriState());
                    customerMasterDetailTO.setJuriDistrict("");

                    customerMasterDetailTO.setJuriPostal(pojo.getJuriPostCode());

                    File imageDir = new File(props.getProperty("cbsDownloadImageLocation").trim() + File.separatorChar + ckycrNo + File.separatorChar);
                    File[] files = imageDir.listFiles();
                    if (files.length > 0) {
                        String custImageCode = "";
                        int i = 0;
                        for (File file : files) {
                            String tempFileName = file.getName().split("\\.")[0];
                            tempFileName = tempFileName.split("_")[1];
                            custImageCode = custImageCode.concat(tempFileName);
                            if (i != files.length - 1) {
                                custImageCode = custImageCode.concat("|");
                            }
                            i++;
                        }
                        customerMasterDetailTO.setCustImage(custImageCode);
                    }
                    //--To map ckycr occupation to the local cbs occupation--//
                    mISInfoTO.setOccupationCode(getLocalOccupation(pojo.getOccupationType()));
                    //----------------//
                    if (customerMasterDetailTO.getCustEntityType().equalsIgnoreCase("02")) {
                        mISInfoTO.setIncorpDt(pojo.getDateOfBirth());
                    } else {
                        mISInfoTO.setIncorpDt("");
                    }
                    mISInfoTO.setCountryOfIncorp("");
                    mISInfoTO.setStateOfIncorp("");
                    mISInfoTO.setIncorpPlace(pojo.getPlaceOfIncorporation());

                    mISInfoTO.setCommDt(pojo.getDateOfCommBusiness());
                    mISInfoTO.setConstitutionCode(pojo.getConstitutionCode());
                    mISInfoTO.setMisJuriResidence(pojo.getJuriOfResidence());
                    if (pojo.getJuriOfResidence().equalsIgnoreCase("IN")) {
                        mISInfoTO.setMisTin("");
                        mISInfoTO.setMisCity("");
                        mISInfoTO.setMisBirthCountry("");
                    } else {
                        mISInfoTO.setMisTin(pojo.getJuriTaxIdentificationNo());
                        mISInfoTO.setMisCity(pojo.getPlaceOfBirth());
                        mISInfoTO.setMisBirthCountry(pojo.getCountryOfBirth());
                    }

                    mISInfoTO.setType("");
                    mISInfoTO.setGroupID("");
                    customerMasterDetailTO.setOperationalRiskRating("02");
                    customerMasterDetailTO.setRatingAsOn(new Date());
                    customerMasterDetailTO.setCustAcquistionDate(new Date());
                    customerMasterDetailTO.setThresoldTransactionLimit(Double.parseDouble("0.0"));
                    customerMasterDetailTO.setThresoldLimitUpdationDate(new Date());
                    customerMasterDetailTO.setCustUpdationDate(new Date());
                    mISInfoTO.setTypeOfDocSubmit(pojo.getTypeOfDocSubmitted());

                    mISInfoTO.setResidenceCountryTaxLaw(pojo.getResidenceCountryTaxLaw());

                    //For NRI INFO TAB
                    nREInfoTO.setNonResidentDate(new Date());
                    nREInfoTO.setCountryCode("IN");
                    nREInfoTO.setLocalContPersonCode("");
                    nREInfoTO.setLocalAddress1("");
                    nREInfoTO.setLocalAddress2("");
                    nREInfoTO.setVillage("");
                    nREInfoTO.setCityCode("");
                    nREInfoTO.setStateCode("");
                    nREInfoTO.setPostalCode("");
                    nREInfoTO.setPhoneNumber("");
                    nREInfoTO.setMobileNumber("");
                    nREInfoTO.setNreEmial("");
                    nREInfoTO.setNonResidentEndDate(new Date());

                    List<CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos = new ArrayList<CBSRelatedPersonsDetailsTO>();

                    List<CKYCRDownloadDetail30> pojoDetail30List = getCKYCRData30(ckycrNo);
                    List<CBSCustIdentityDetailsTo> idTypeList = new ArrayList<CBSCustIdentityDetailsTo>();
                    if (!pojoDetail30List.isEmpty()) {
                        for (int i = 0; i < pojoDetail30List.size(); i++) {
                            CKYCRDownloadDetail30 pojoDetail30 = pojoDetail30List.get(i);

                            if (customerMasterDetailTO.getCustEntityType().equalsIgnoreCase("01")) {
                                if (pojoDetail30.getIdentificationtype().equalsIgnoreCase("C")) {
                                    customerMasterDetailTO.setpANGIRNumber(pojoDetail30.getIdentityNumber());
                                }
                                if (i == 0) {
                                    customerMasterDetailTO.setLegalDocument(pojoDetail30.getIdentificationtype());
                                    customerMasterDetailTO.setOtherIdentity("");
                                    customerMasterDetailTO.setIdentityNo(pojoDetail30.getIdentityNumber());
                                    customerMasterDetailTO.setIdExpiryDate(pojoDetail30.getExpiryDate().equalsIgnoreCase("") ? "" : dmy.format(ymdWihtoutSeparator.parse(pojoDetail30.getExpiryDate())));
                                    customerMasterDetailTO.setTinIssuingCountry("");
                                } else {
                                    CBSCustIdentityDetailsTo idType = new CBSCustIdentityDetailsTo();
                                    idType.setIdentificationType(pojoDetail30.getIdentificationtype());
                                    idType.setIdentityNo(pojoDetail30.getIdentityNumber());
                                    idType.setIdentityExpiryDate(pojoDetail30.getExpiryDate().equalsIgnoreCase("") ? "" : dmy.format(ymdWihtoutSeparator.parse(pojoDetail30.getExpiryDate())));
                                    idType.setOtherIdentificationType("");
                                    idType.setTinIssuingCountry("");
                                    idTypeList.add(idType);
                                }
                            } else {
                                CBSCustIdentityDetailsTo idType = new CBSCustIdentityDetailsTo();
                                idType.setIdentificationType(pojoDetail30.getIdentificationtype());
                                idType.setIdentityNo(pojoDetail30.getIdentityNumber());
                                idType.setIdentityExpiryDate(pojoDetail30.getExpiryDate().equalsIgnoreCase("") ? "" : dmy.format(ymdWihtoutSeparator.parse(pojoDetail30.getExpiryDate())));
                                idType.setOtherIdentificationType("");
                                idType.setTinIssuingCountry("");
                                idTypeList.add(idType);
                            }
                        }
                    }


                    List<CKYCRDownloadDetail60> pojoDetail60List = getCKYCRData60(ckycrNo);
                    List<CbsCustAdditionalAddressDetailsTo> addressDetailsList = new ArrayList<CbsCustAdditionalAddressDetailsTo>();
                    if (!pojoDetail60List.isEmpty()) {
//                        addressDetailsList = new ArrayList<CbsCustAdditionalAddressDetailsTo>();
                        for (CKYCRDownloadDetail60 pojoDetail60 : pojoDetail60List) {
                            CbsCustAdditionalAddressDetailsTo addressDetail = new CbsCustAdditionalAddressDetailsTo();

                            addressDetail.setAddressType(pojoDetail60.getAddressType());
                            addressDetail.setsNo(pojoDetail60.getSeqNo());

                            addressDetail.setLocalAddressLine1(pojoDetail60.getLocalAddressLine1());
                            addressDetail.setLocalAddressLine2(pojoDetail60.getLocalAddressLine2());
                            addressDetail.setLocalAddressCityVillage(pojoDetail60.getLocalAddressCityVillage());
                            addressDetail.setLocalAddressDistrict(pojoDetail60.getLocalAddressDistrict());
                            addressDetail.setLocalAddressPINCode(pojoDetail60.getLocalAddressPINCode());
                            addressDetail.setLocalAddressState(pojoDetail60.getLocalAddressState());
                            addressDetail.setLocalAddressCountry(pojoDetail60.getLocalAddressCountry());
                            addressDetail.setProofofAdd(pojoDetail60.getProofofAdd());
                            addressDetail.setResiTelSTDCode(pojoDetail60.getResiTelSTDCode());
                            addressDetail.setResiTelNo(pojoDetail60.getResiTelNo());
                            addressDetail.setOfficeTelSTDCode(pojoDetail60.getOfficeTelSTDCode());
                            addressDetail.setOfficeTelNo(pojoDetail60.getOfficeTelNo());
                            addressDetail.setMobISDCode(pojoDetail60.getMobISDCode());
                            addressDetail.setMobileNo(pojoDetail60.getMobileNo());
                            addressDetail.setFaxSTDCode(pojoDetail60.getFaxSTDCode());
                            addressDetail.setFaxNo(pojoDetail60.getFaxNo());
                            addressDetail.setEmailID(pojoDetail60.getEmailID());
                            addressDetail.setDateofDeclaration(pojoDetail60.getDateofDeclaration());
                            addressDetail.setPlaceofDeclaration(pojoDetail60.getPlaceofDeclaration());

                            addressDetailsList.add(addressDetail);
                        }
                    }

                    msg = beanRemote.saveUpdateCustomer(customerMasterDetailTO, minorInfoTO, nREInfoTO, mISInfoTO,
                            reletedPersonDetailsTos, idTypeList, addressDetailsList, "A", userName, brncode);

                    if (!customerMasterDetailTO.getCustImage().equals("")) {
                        if (copyCKYCRImageToCustomerImage((msg.split("is")[1]).trim(), ckycrNo).equalsIgnoreCase("success")) {
                            msg = msg.concat(" , Image successfully downloaded.");
                        }
                    }

                } else {
                    msg = "No CKYCR Data Exist for Generate Customer Id !";
                }
            } else {
                msg = "Customer Id Exist for Corresponding CKYCR No. !";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public boolean isCKYCRCustomerIdExist(String ckycrNo) throws ApplicationException {
        try {
            String ckycrDetaialQuery = "select * from cbs_customer_master_detail where CKYCNo='" + ckycrNo + "'";
            List selectList = em.createNativeQuery(ckycrDetaialQuery).getResultList();
            if (selectList.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

    }

    private String getLocalOccupation(String occpCode) throws ApplicationException {
        String ckycrOccup = "";
        try {
            String ckycrDetaialQuery = "select SS_GNO from cbs_ref_rec_mapping where GNO='007' and S_GNO='" + occpCode + "'";
            List selectList = em.createNativeQuery(ckycrDetaialQuery).getResultList();
            if (selectList.size() > 0) {
                ckycrOccup = ((Vector) selectList.get(0)).get(0).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return ckycrOccup;
    }

    public List<CKYCRRequestPojo> generateDownloadFile(String mode, String fromDate, String toDate, String branchCode) throws ApplicationException {

        List<CKYCRRequestPojo> pojoList = new ArrayList<CKYCRRequestPojo>();
        try {
            List selectList = em.createNativeQuery("select uploaded_file_name,date_format(uploaded_gen_date,'%d/%m/%Y') "
                    + "from ckycr_file_detail where mode='" + mode + "' and date_format(uploaded_gen_date,'%Y%m%d') "
                    + "between '" + fromDate + "' and '" + toDate + "' and uploaded_branch='" + branchCode + "' order "
                    + "by uploaded_gen_date").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("There is no file to show.");
            }
            if (mode.equalsIgnoreCase("upload")) {
                for (int i = 0; i < selectList.size(); i++) {
                    CKYCRRequestPojo pojo = new CKYCRRequestPojo();
                    Vector vec = (Vector) selectList.get(i);
                    pojo.setSno(i + 1);
                    pojo.setUploadedFileName(vec.get(0).toString() + ".zip");
                    pojo.setUploadedGenDate(vec.get(1).toString());
                    pojoList.add(pojo);

                    pojo = new CKYCRRequestPojo();
                    pojo.setSno(i + 1);
                    pojo.setUploadedFileName(vec.get(0).toString() + ".trg");
                    pojo.setUploadedGenDate(vec.get(1).toString());
                    pojoList.add(pojo);
                }
            } else if (mode.equalsIgnoreCase("download")) {
                for (int i = 0; i < selectList.size(); i++) {
                    CKYCRRequestPojo pojo = new CKYCRRequestPojo();
                    Vector vec = (Vector) selectList.get(i);
                    pojo.setSno(i + 1);
                    pojo.setUploadedFileName(vec.get(0).toString() + ".txt");
                    pojo.setUploadedGenDate(vec.get(1).toString());
                    pojoList.add(pojo);

                    pojo = new CKYCRRequestPojo();
                    pojo.setSno(i + 1);
                    pojo.setUploadedFileName(vec.get(0).toString() + ".trg");
                    pojo.setUploadedGenDate(vec.get(1).toString());
                    pojoList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojoList;
    }

    private String copyCKYCRImageToCustomerImage(String custId, String ckycrNo) {
        String msg = "";
        Boolean flag = false;
        if (custId == null || custId.equalsIgnoreCase("")) {
            msg = "Please enter Customer Id !";
            return msg;
        }
        try {
            File dir = null;
            String osName = System.getProperty("os.name");
            if (osName.equals("Linux")) {
                dir = new File("/" + ftsPostRemote.getBankCode() + "/CI/" + custId);
            } else {
                dir = new File("C:/" + ftsPostRemote.getBankCode() + "/CI/" + custId);
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File imageDir = new File(props.getProperty("cbsDownloadImageLocation").trim() + File.separatorChar + ckycrNo + File.separatorChar);
            File[] files = imageDir.listFiles();
            for (File file : files) {

                FileInputStream fis = new FileInputStream(file);
                byte[] bArray = new byte[(int) file.length()];
                fis.read(bArray);
                fis.close();

                String encBytes = Base64.encodeBytes(bArray);
                String tempFileName = file.getName().split("\\.")[0];
                tempFileName = tempFileName.split("_")[1];

                String filePath = dir.getCanonicalPath() + "/" + tempFileName;
                flag = CbsUtil.generateXML(filePath, "Img", encBytes);
            }
            if (flag == true) {
                msg = "success";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            msg = ex.getMessage();
        }
        return msg;
    }

    public CkycrRealTimeSearchDetailsPojo getCkycrSearchResponse(CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException {
        int flag = 0;
        CkycrRealTimeSearchDetailsPojo searchResponseData;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List selectList = em.createNativeQuery("SELECT LPAD(COALESCE(MAX(CAST(Request_Id AS UNSIGNED INTEGER)+1),'0'),5,'0') "
                    + "FROM ckycr_real_time_request").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("There is problem to generate request id.!");
            } else {
                ckycrRequest.setRequestId(((Vector) selectList.get(0)).get(0).toString());
            }
            selectList = em.createNativeQuery("select code from cbs_parameterinfo where NAME='CKYCR-FI-CODE'").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("There is problem to geting FI Code.!");
            } else {
                ckycrRequest.setFiCode(((Vector) selectList.get(0)).get(0).toString());
            }
            ckycrRequest.setRequestDate(new java.sql.Date(new Date().getTime()).toString());
            flag = em.createNativeQuery("INSERT INTO ckycr_real_time_request (Request_Type,Search_Id_Type,"
                    + "Search_Download_No,Download_DOB,Enter_By,Enter_Date,Enter_Time,"
                    + "Request_Id) VALUES ( '" + ckycrRequest.getRequestType() + "', '" + ckycrRequest.getSearchIdType() + "',"
                    + "'" + ckycrRequest.getSearchDownloadNo() + "','" + ckycrRequest.getDownloadDOB() + "',"
                    + "'" + ckycrRequest.getEnterBy() + "','" + ckycrRequest.getRequestDate() + "', '"
                    + new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(new Date()) + "','" + ckycrRequest.getRequestId() + "')").executeUpdate();
            ckycrRequest.setVersion("1.0");
            ResPidData ckycrResponse = callSearchRequest(ckycrRequest);//un comment if it goes to live

//
//            //comment or remove this code if it goes to live//
//            ckycrResponse.setCKYCNO("test");
//            ckycrResponse.setNAME("test");
//            ckycrResponse.setFATHERSNAME("test");
//            ckycrResponse.setAGE("36");
//            ckycrResponse.setIMAGETYPE("test");
//            ckycrResponse.setKYCDATE("2017-04-22");
//            ckycrResponse.setUPDATEDDATE("2017-04-22");
//            ckycrResponse.setREMARKS("test");
//            ckycrResponse.setPHOTO("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");
//            //---------------------------------------//
//            

            String flagmsg = insertCkycrSearchResponse(ckycrResponse, ckycrRequest);
            searchResponseData = getSearchResponseCkycrDetails(ckycrRequest.getRequestDate(), ckycrRequest.getRequestId());
            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return searchResponseData;
    }

    public ResPidData callSearchRequest(CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException {

        Encrypter enc = new Encrypter("/home/dsg-key/cersai.cer");
        ResPidData resPid = null;
        try {
            SearchDataCreator dataCreator = new SearchDataCreator(enc);
            ReqPidData pid = dataCreator.prepareSearchPidData(ckycrRequest.getSearchIdType(), ckycrRequest.getSearchDownloadNo());
            Header header = dataCreator.prepareSearchHeader(ckycrRequest.getFiCode(), ckycrRequest.getRequestId(), ckycrRequest.getVersion());

            ReqRoot kycReq = dataCreator.prepareSearchReq(header, pid);

            System.out.println(XMLUtilities.getXML(ReqRoot.class, kycReq, true, "REQ_ROOT"));

            DigitalSigner signer = new DigitalSigner("/home/dsg-key/dsgkey.pfx", "password!9".toCharArray(), "");

            DataDecryptor decrypter = new DataDecryptor("/home/dsg-key/dsgkey.pfx", "password!9".toCharArray(), "/home/dsg-key/cersai.cer");

            CkycSearchClient ckycClient = new CkycSearchClient(new URI("https://testbed.ckycindia.in/Search/ckycverificationservice/verify"));

            ckycClient.setDigitalSignator(signer);
            ckycClient.setDataDecryptor(decrypter);

            ReqRoot searchRes = ckycClient.searchCkycData(kycReq);

            Header resHeader = searchRes.getHEADER();
            if (searchRes.getCKYCINQ().getERROR() != null || !searchRes.getCKYCINQ().getERROR().equals("")) {
                System.out.println(searchRes.getCKYCINQ().getERROR());
            } else {
                String resPidData = searchRes.getCKYCINQ().getPID();
                resPid = (ResPidData) XMLUtilities.parseXML(ResPidData.class, resPidData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());

        }
        return resPid;
    }

    public String insertCkycrSearchResponse(ResPidData ckycrResponse, CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException {
        int flag = 0;
        try {
            flag = em.createNativeQuery("INSERT INTO  ckycr_real_time_search_response(Request_Date,Request_Id,"
                    + "FI_Code,Version,CKYC_No,Name,Father_Name,Age,Image_Type,Kyc_Date,"
                    + "Updated_Date,Reason_Code,Reason) values('" + ckycrRequest.getRequestDate() + "','" + ckycrRequest.getRequestId()
                    + "','" + ckycrRequest.getFiCode() + "','" + ckycrRequest.getVersion() + "','" + ckycrResponse.getCKYCNO() + "','" + ckycrResponse.getNAME() + "',"
                    + "'" + ckycrResponse.getFATHERSNAME() + "','" + ckycrResponse.getAGE() + "','" + ckycrResponse.getIMAGETYPE() + "',"
                    + "'" + ymdhms.format(dmyHyphen.parse(ckycrResponse.getKYCDATE())) + "','" + ymdhms.format(dmyHyphen.parse(ckycrResponse.getUPDATEDDATE())) + "','" + "" + "','" + ckycrResponse.getREMARKS() + "')").executeUpdate();

            flag = em.createNativeQuery("INSERT INTO ckycr_real_time_downloaded_images(Request_Date,Request_Id,Image) VALUES"
                    + " ('" + ckycrRequest.getRequestDate() + "','" + ckycrRequest.getRequestId() + "','" + ckycrResponse.getPHOTO() + "')").executeUpdate();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return flag > 0 ? "Success" : "Unsuccess";
    }

    public CkycrRealTimeSearchDetailsPojo getSearchResponseCkycrDetails(String requestDate, String requestId) throws ApplicationException {
        CkycrRealTimeSearchDetailsPojo detailsPojo = new CkycrRealTimeSearchDetailsPojo();
        try {
            List selectList = em.createNativeQuery("select Request_Date,Request_Id,CKYC_No,Name,Father_Name,Age,Kyc_Date,"
                    + "Updated_Date,Reason from ckycr_real_time_search_response where "
                    + "Request_Date='" + requestDate + "' and Request_Id='" + requestId + "'").getResultList();
            if (!selectList.isEmpty()) {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    detailsPojo.setRequestDate(vec.get(0).toString());
                    detailsPojo.setRequestId(vec.get(1).toString());
                    detailsPojo.setcKYCNo(vec.get(2).toString());
                    detailsPojo.setName(vec.get(3).toString());
                    detailsPojo.setFatherName(vec.get(4).toString());
                    detailsPojo.setAge(vec.get(5).toString());
                    detailsPojo.setKycDate(vec.get(6).toString());
                    detailsPojo.setUpdatedDate(vec.get(7).toString());
                    detailsPojo.setReason(vec.get(8).toString());
                }
            }

            selectList = em.createNativeQuery("select Image from ckycr_real_time_downloaded_images"
                    + " where Request_Date='" + requestDate + "' and Request_Id='" + requestId + "'").getResultList();
            if (!selectList.isEmpty()) {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    detailsPojo.setPhoto((byte[]) vec.get(0));
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return detailsPojo;
    }

    public String getCkycrDownloadResponse(CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException {
        String msg = "";
        try {
            List selectList = em.createNativeQuery("SELECT LPAD(COALESCE(MAX(CAST(Request_Id AS UNSIGNED INTEGER)+1),'0'),5,'0') "
                    + "FROM ckycr_real_time_request").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("There is problem to generate request id.!");
            } else {
                ckycrRequest.setRequestId(((Vector) selectList.get(0)).get(0).toString());
            }
            selectList = em.createNativeQuery("select code from cbs_parameterinfo where NAME='CKYCR-FI-CODE'").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("There is problem to geting FI Code.!");
            } else {
                ckycrRequest.setFiCode(((Vector) selectList.get(0)).get(0).toString());
            }

            DwndResPidData ckycrResponse = callDownloadRequest(ckycrRequest);//--Un comment this when it goes to live
//            DwndResPidData ckycrResponse = new DwndResPidData();//--Remove this when it goes to live
            msg = insertCkycrDownloadResponse(ckycrResponse, ckycrRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String insertCkycrDownloadResponse(DwndResPidData ckycrResponse, CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException {
        int flag = 0;
        try {
            PERSONALDETAILS personalDetail = ckycrResponse.getPERSONALDETAILS();
            List<LOCALADDRESS> localAddList = ckycrResponse.getLOCALADDRESSDETAILS().getLOCALADDRESS();
            List<IDENTITY> identityList = ckycrResponse.getIDENTITYDETAILS().getIDENTITY();
            List<IMAGE> imageList = ckycrResponse.getIMAGEDETAILS().getIMAGE();

//
//            PERSONALDETAILS personalDetail = new PERSONALDETAILS();
//            List<LOCALADDRESS> localAddList = new ArrayList<LOCALADDRESS>();
////            = ckycrResponse.getLOCALADDRESSDETAILS().getLOCALADDRESS();
//            List<IDENTITY> identityList = new ArrayList<IDENTITY>();
////            = ckycrResponse.getIDENTITYDETAILS().getIDENTITY();
//            List<IMAGE> imageList = new ArrayList<IMAGE>();
////            = ckycrResponse.getIMAGEDETAILS().getIMAGE();

//            personalDetail.setCONSTITYPE("01");
//            personalDetail.setACCTYPE("01");
//            personalDetail.setCKYCNO(ckycrRequest.getSearchDownloadNo());
//            personalDetail.setPREFIX("MR");
//            personalDetail.setFNAME("RAKESH");
//            personalDetail.setMNAME("KUMAR");
//            personalDetail.setLNAME("PRASAD");
//            personalDetail.setFULLNAME("MR RAKESH KUMAR PRASAD");
//            personalDetail.setMAIDENPREFIX("");
//            personalDetail.setMAIDENFNAME("");
//            personalDetail.setMAIDENMNAME("");
//            personalDetail.setMAIDENLNAME("");
//            personalDetail.setMAIDENFULLNAME("");
//            personalDetail.setFATHERSPOUSEFLAG("01");
//            personalDetail.setFATHERPREFIX("MR");
//            personalDetail.setFATHERFNAME("HARISH");
//            personalDetail.setFATHERMNAME("PARASAD");
//            personalDetail.setFATHERLNAME("YADAV");
//            personalDetail.setFATHERFULLNAME("MR HARISH PRASAD YADAV");
//            personalDetail.setMOTHERPREFIX("MRS");
//            personalDetail.setMOTHERFNAME("SITA");
//            personalDetail.setMOTHERMNAME("");
//            personalDetail.setMOTHERLNAME("DEVI");
//            personalDetail.setMOTHERFULLNAME("MRS SITA DEVI");
//            personalDetail.setGENDER("M");
//            personalDetail.setMARITALSTATUS("01");
//            personalDetail.setNATIONALITY("IN");
//            personalDetail.setOCCUPATION("B-01");
//            personalDetail.setDOB("12-12-1976");
//            personalDetail.setRESISTATUS("04");
//            personalDetail.setJURIFLAG("02");
//            personalDetail.setJURIRESI("IN");
//            personalDetail.setTAXNUM("");
//            personalDetail.setBIRTHCOUNTRY("IN");
//            personalDetail.setBIRTHPLACE("NOIDA");
//            personalDetail.setPERMTYPE("02");
//            personalDetail.setPERMLINE1("C-25 NOIDA SEC-62");
//            personalDetail.setPERMLINE2("NOIDA");
//            personalDetail.setPERMLINE3("GAUTAMBUDH NAGAR");
//            personalDetail.setPERMCITY("GAUTAM BUDH NAGAR");
//            personalDetail.setPERMDIST("GAUTAM BUDH NAGAR");
//            personalDetail.setPERMSTATE("UP");
//            personalDetail.setPERMCOUNTRY("IN");
//            personalDetail.setPERMPIN("203155");
//            personalDetail.setPERMPOA("04");
//            personalDetail.setPERMPOAOTHERS("");
//            personalDetail.setPERMCORRESSAMEFLAG("N");
//            personalDetail.setCORRESLINE1("C-25 NOIDA SEC-62");
//            personalDetail.setCORRESLINE2("GAUTAMBUDH NAGAR");
//            personalDetail.setCORRESLINE3("GAUTAMBUDH NAGAR");
//            personalDetail.setCORRESCITY("GAUTAMBUDH NAGAR");
//            personalDetail.setCORRESDIST("GAUTAMBUDH NAGAR");
//            personalDetail.setCORRESSTATE("UP");
//            personalDetail.setCORRESCOUNTRY("IN");
//            personalDetail.setCORRESPIN("203155");
//            personalDetail.setJURISAMEFLAG("03");
//            personalDetail.setJURILINE1("C-25 NOIDA SEC-62");
//            personalDetail.setJURILINE2("GAUTAMBUDH NAGAR");
//            personalDetail.setJURILINE3("GAUTAMBUDH NAGAR");
//            personalDetail.setJURICITY("GAUTAMBUDH NAGAR");
//            personalDetail.setJURISTATE("UP");
//            personalDetail.setJURICOUNTRY("IN");
//            personalDetail.setJURIPIN("203155");
//            personalDetail.setRESISTDCODE("0124");
//            personalDetail.setRESITELNUM("24565254");
//            personalDetail.setOFFSTDCODE("0124");
//            personalDetail.setOFFTELNUM("54632585");
//            personalDetail.setMOBCODE("91");
//            personalDetail.setMOBNUM("9899797994");
//            personalDetail.setFAXCODE("0124");
//            personalDetail.setFAXNO("654645989");
//            personalDetail.setEMAIL("marketing.comp@gemail.com");
//            personalDetail.setREMARKS("");
//            personalDetail.setDECDATE("12-12-2014");
//            personalDetail.setDECPLACE("NOIDA");
//            personalDetail.setKYCDATE("12-12-2014");
//            personalDetail.setDOCSUB("01");
//            personalDetail.setKYCNAME("RAJIV SUKLA");
//            personalDetail.setKYCDESIGNATION("MANAGER");
//            personalDetail.setKYCBRANCH("IN652");
//            personalDetail.setKYCEMPCODE("KJ65234");
//            personalDetail.setORGNAME("ORG");
//            personalDetail.setORGCODE("ORG452");
//            personalDetail.setNUMIDENTITY("2");
//            personalDetail.setNUMRELATED("0");
//            personalDetail.setNUMLOCALADDRESS("1");
//            personalDetail.setNUMIMAGES("3");
//            personalDetail.setNAMEUPDATEFLAG("02");
//            personalDetail.setPERSONALUPDATEFLAG("02");
//            personalDetail.setADDRESSUPDATEFLAG("02");
//            personalDetail.setCONTACTUPDATEFLAG("02");
//            personalDetail.setREMARKSUPDATEFLAG("02");
//            personalDetail.setKYCUPDATEFLAG("02");
//            personalDetail.setIDENTITYUPDATEFLAG("02");
//            personalDetail.setRELPERSONUPDATEFLAG("02");
//            personalDetail.setIMAGEUPDATEFLAG("02");
//
//            for (int i = 1; i < 3; i++) {
//                IDENTITY identity = new IDENTITY();
//                identity.setIDENTTYPE("B" + i);
//                identity.setIDENTNUM("654646649");
//                identity.setIDEXPIRYDATE("12-12-2020");
//                identity.setIDPROOFSUBMITTED("01");
//                identity.setIDVERSTATUS("01");
//                identityList.add(identity);
//            }
//
//            for (int i = 1; i < 3; i++) {
//                LOCALADDRESS localAdd = new LOCALADDRESS();
//                localAdd.setSEQUENCENO("0" + i);
//                localAdd.setBRANCHCODE("INV65");
//                localAdd.setADDRLINE1("C-25 NOIDA SEC-62");
//                localAdd.setADDRLINE2("GAUTAM BUDH NAGAR");
//                localAdd.setADDRLINE3("GAUTAM BUDH NAGAR");
//                localAdd.setADDRCITY("GAUTAM BUDH NAGAR");
//                localAdd.setADDRDIST("GAUTAM BUDH NAGAR");
//                localAdd.setADDRPIN("203155");
//                localAdd.setADDRSTATE("UP");
//                localAdd.setADDRCOUNTRY("IN");
//                localAdd.setRESISTDCODE("0124");
//                localAdd.setRESITELNUM("65656545");
//                localAdd.setOFFSTDCODE("0124");
//                localAdd.setOFFTELNUM("65454525");
//                localAdd.setMOBCODE("91");
//                localAdd.setMOBNUM("9856565456");
//                localAdd.setFAXCODE("0124");
//                localAdd.setFAXNO("6564565656");
//                localAdd.setEMAIL("MARKET.CS@GMAIL.COM");
//                localAdd.setDECDATE("12-12-2014");
//                localAdd.setDECPLACE("GAUTAM BUDH NAGAR");
//                localAddList.add(localAdd);
//            }
//
//            for (int i = 1; i < 3; i++) {
//                IMAGE imgs = new IMAGE();
//                imgs.setSEQUENCENO("01");
//                imgs.setIMAGETYPE("jpg");
//                imgs.setIMAGECODE("0" + i);
//                imgs.setIMAGEDATA("/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkLDRYPDQwMDRsUFRAWIB0iIiAdHx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys/RD84QzQ5OjcBCgoKDQwNGg8PGjclHyU3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3N//AABEIAIMAqQMBIgACEQEDEQH/xAAcAAACAQUBAAAAAAAAAAAAAAAAAQIDBAUGBwj/xAA5EAABAwIDBQUFBgcBAAAAAAABAAIDBBEFITEGEhNBUQciYXGRFDJCgaEWIySxwdEVUmJykuHwM//EABkBAQEBAQEBAAAAAAAAAAAAAAABAwIEBf/EACERAQACAgICAgMAAAAAAAAAAAABEQIDITESQSIyBBND/9oADAMBAAIRAxEAPwDr7WqVk7IWbQITQgSaSLoAqBek9ysMTxKjwulfVV9RHBCwXLnutfw8UF7v55ID8lxzH+2GTiyx4HRMMPuxzVFw6/8ANb9PqtMn7RNrpZHOfjksd/gjiYAPLupEObel9/NTDgvLbtstopXb0uN17j4TFv5LI4V2gbS4fK1zMWqJmDWOotI0+ov9VaPJ6WvkhaTsRt9RbQxNgqzHS4jpwrndk/tJ5+Gq3QOuoqaEBCATSQgaEIRQhCFUIIKEKKSSaSAUXKSoVMzYYnyPcGtaCSToAgsMZxejwijkqa2UMYxpdYakf9+a4H2g7X/amtjEVMYqeB3cLn3d8hoPzVz2i7SyY5i00UT7UcDrNH8xHM/Ww8VpEweCDmb8khxMqLjY5JA/JMt3kt0hdIqRlwGVvRVQXgZtBHgqLARmb/JXEd79eiCtTz8Nwc1+7Zdk7NNvzWSR4Pjcv4jSnqHn3/6XHr0PPTVcZa1riMrO5jqhpdEQATuX7rh8JSSHrhpUlqXZxtH9otnIZpnh1ZT/AHNTnq4aO+Yz9Vto0XLsITSQNCAhFCEIVCQUIKgiUJpIEVqXaTiQwzZx77j72Rsfe0Op+trLbStA7aWh2x7SRpVx+lnXRJcHc90kji472Zc89Te5P1VWhpZMRrWwR3vJa56BUH/dsI5kWctl7Pqfi4k6Qi4aFM5rGzXj5ZxDK4hsXGMOZ7OLysH+Y6fstGqKUxSFrr3BI7wsRbqvQVNSsmjs4C1ui1PbPY51RG6ro22maM8sneaw17Z6l6NumO8XKGR2yI8lV4e5rex5qczZKeZ0E0bmSA6OCTLvaWWuCvTdvJXpTdZgzzB6JMkG/Zxu0oJIb3hpldW57py15IOj9i2JGh2qfQvf3ayIxkE5OcwFzT523l3lq8xbDVAg2twadztwCqYxx6XNv1t816dCjqE0JJopJhJNAIQhFJCEIESkmkgR0Wl9rMIl2Kqri5jlieP8wPyJW6Fa3t/Tmp2SxKMZnhX9M0SXmWV13u+eS3bY1s1PRukgiL5XvNgtLkYRU7viuwbJUHBwmGQNu7duAOay3ZVi1/HxvK1SkxPaCmvfDmyN8Dp5rK4ftW2d5psRopKR9snHNjvIrEYhVbSGKofQMZDwyNyIRhzpBfO18r2UsIhxepbE7F4Gl8xcQ3h2dEAcg/lcjosfVt65pb7VbMUmKNMsLQ2UZsc3K/gSubYhQT4bVARkvBNrEZjwIXeIMPZ7M4b2gtZajtDh8jd91LSskqHNcWbzcjbl+yuvZMcGzXGUW5gIaidjtymJa/MObmAVYyRPaSHtLSNQVvmHwYgyndVTUgiBkDTBwy1zss3bvn5XUdr8Fb7FJXRN3SA3eyW0bPlTCdPxtqWEuEdTG4m1pGknpYjNesLgk2zC8pYRRurq6CjYHE1EwjG7qPEL1VH7jfJaSxhVCaTU0UIQhAIQhFJCEIESkUEpIBWmIU7aqllp5BdkjCx3kVdqL23CDzDj2EOwzHp6OS/cqCG/2l5t9F2nCKeOOkgZGBuNjaB6LC9qezbXt/jcA70RaZxbUAjP0BWXwCYS4VSSA3Domm/XJefd1DfR3LLila7O6OHHCMgAeZVWOUbt1Z4hIIwJJb8PUlY+nqiOVzBu8N9jqVFkUT27rmgjxWOocbp5IjuMewE6SRlrvQqvh1fFiDOJTskbYneEjd0ix6FRZiVw6giGdvFa/tlSx/ZyvdYdyEu9M/0WyyT7osdVre3Et9mapgveYtiaAMyXOAVj7OM/q5x2a0kkm3GB7zLx8SR56XbE4/svRTVzDs7wJn8flrmh3Dw8GKO4yLnN3XfRo9V09q9r56o0pqIUlVCEIQCEkIgQhCKiQo2splJEJBQhFWWIUsVXTSwTtDo5Glrgellp+E0MuGUYoJQfw5LGE/Ey/dPp+S3mXQrFYlG10LpCBdgvfwWWzG4d68qyYyN9tTkqb8Wo3gt9oh7ps4F4yKInXdloUVOFwzgPZEwPH9Oq8sPdjV8pQ1GHPDX8WIkcw1SGJUkIcY5obcwDYlY04RvSW/hcR5lwAA9NFkKDCaeku/gRCQj4WgWVtpnjhXEqj3cbOxF1isWoajEsQw+hpgQ1j/aJZDowt/8AO/XO5+SyxFpPBZylYxsTHNAuW6rvVjc28u7L40jhWH0+F0bKWlbZjfU5WuVfNUAqjF63kSCkkmgEIQgEk0IhIQhFBUCpXSKIihCRKKhIcisXikMs9HI2AnfFngD4rG5b8wLLISG6iwZLmeXWPbTjKYncSMbzTyWRpa6OQBzXaajmFdV+CufO6oo3C783xO0J6g8j/wBksFU0T2Sbrmvjl6HI/wC148sZxl7sZxlnvbY7e+Fb1NdExuTxvHQcyteNJUueLyPt5rIUlGGuaN0ve7IDUlczk6pewh8pBDS57jZrOpWw0kLqemiie7fc1ubupVLDqAUw4kucrhaw0aOgV7ZenVhOPMvNsny4IKo0qmFILZ5/GVUJqmCpgqoaEIQCEIRCRdCR1QCRKCVAooJUDc5BS8yEi7oFLdxjaBaLotllyTLch1UWOaH7pyJUaREJDJRqIoJYj7Q1pY0XJfo3xvyVQrXduKatxPAqjDMOlEUtS3dkffRnMfPTyulLU+mk4xt3RQVErcJpJquFriGyPka1rvEam3mjZztOoYK8DGcONNHJZvtLJeJw/MWFm+S57UUlXhFe6gxKN0b/AISRk4dQVk9itnjtLtE2me29LTjiVHiOQ+Z+gKka8Y5pl+zOZ8ZeiI5I5mNkie18bgC1zTcOB5gpnJYbDsMq8LZuU07X040gc3JvkeSycVQHd2Rro38w/wDdWGs410kLl5tyU+ale1/FIBVyaN5PqogZElHNWqByaoG4OSqMJ5o5nFNF1ElK6rlJIoQgRUHaIQosdoN7wucypBJCjZLkFGwuDbMHJCEEnaFWsLGlpkt33HN3MoQix0492xsb9pcKYQC3gONj13/9LN9ix/E46LDuuhtlpk5CF1LD+jqXNRkY1zXbwBSQo2SaABkpBCEQne6mPdCEIIt95S5IQgZSQhGUv//Z");
//                imgs.setGLOBALFLAG("01");
//                imgs.setBRANCHCODE("INV65");
//                imageList.add(imgs);
//            }
//            

            UserTransaction ut = context.getUserTransaction();
            ut.begin();

            ckycrRequest.setRequestDate(new java.sql.Date(new Date().getTime()).toString());
            ckycrRequest.setVersion("1.0");
            flag = em.createNativeQuery("INSERT INTO ckycr_real_time_request (Request_Type,Search_Id_Type,"
                    + "Search_Download_No,Download_DOB,Enter_By,Enter_Date,Enter_Time,"
                    + "Request_Id) VALUES ( '" + ckycrRequest.getRequestType() + "', '',"
                    + "'" + ckycrRequest.getSearchDownloadNo() + "','" + ymd.format(dmyFormat.parse(ckycrRequest.getDownloadDOB())) + "',"
                    + "'" + ckycrRequest.getEnterBy() + "','" + ckycrRequest.getRequestDate() + "', '"
                    + ymdTimeFormat.format(new Date()) + "','" + ckycrRequest.getRequestId() + "')").executeUpdate();

            if (flag <= 0) {
                throw new ApplicationException("Problem in ckycr_real_time_request log maintainance.");
            }
            if (personalDetail != null) {
                CKYCRDownloadPojo pojo = ckycrViewMgmtRemote.setCKYCRDownload20FromRealResponse(personalDetail);
                pojo.setZipFileName("");
                pojo.setRequestType("DOWNLOAD");
                pojo.setMode("REAL");
                pojo.setRequestId(ckycrRequest.getRequestId());
                pojo.setRequestDate(ckycrRequest.getRequestDate());
                txnRemote.insertCKYCRDownload(pojo);
            }
            if (!identityList.isEmpty()) {
                for (IDENTITY identity : identityList) {
                    CKYCRDownloadDetail30 pojo = ckycrViewMgmtRemote.setCKYCRDownloadDetail30FromRealResponse(identity);
                    pojo.setZipFileName("");
                    pojo.setRequestType("DOWNLOAD");
                    pojo.setMode("REAL");
                    pojo.setRequestId(ckycrRequest.getRequestId());
                    pojo.setRequestDate(ckycrRequest.getRequestDate());
                    pojo.setcKYCno(personalDetail.getCKYCNO());
                    txnRemote.insertCKYCRDownloadDetail30(pojo);
                }
            }
            if (!localAddList.isEmpty()) {
                for (LOCALADDRESS localAdd : localAddList) {
                    CKYCRDownloadDetail60 pojo = ckycrViewMgmtRemote.setCKYCRDownloadDetail60FromRealResponse(localAdd);
                    pojo.setZipFileName("");
                    pojo.setRequestType("DOWNLOAD");
                    pojo.setMode("REAL");
                    pojo.setRequestId(ckycrRequest.getRequestId());
                    pojo.setcKYCno(personalDetail.getCKYCNO());
                    pojo.setRequestDate(ckycrRequest.getRequestDate());
                    txnRemote.insertCKYCRDownloadDetail60(pojo);
                }
            }



            String ckycrImageLocation = props.getProperty("cbsDownloadImageLocation").trim();
            File ckycrImageDirectory = new File(ckycrImageLocation + File.separator + personalDetail.getCKYCNO() + File.separator);
            if (!ckycrImageDirectory.exists()) {
                ckycrImageDirectory.mkdirs();
            } else {
                //---Clean the folder first---//
                if (ckycrImageDirectory.isDirectory()) {
                    for (File file : ckycrImageDirectory.listFiles()) {
                        if (!file.isDirectory()) {
                            file.delete();
                        }
                    }
                }
            }

            if (!imageList.isEmpty()) {
                for (IMAGE img : imageList) {
                    flag = em.createNativeQuery("INSERT INTO ckycr_real_time_downloaded_images(Request_Date,Request_Id,Image) VALUES"
                            + " ('" + ckycrRequest.getRequestDate() + "','" + ckycrRequest.getRequestId() + "','" + img.getIMAGEDATA() + "')").executeUpdate();
                    if (flag <= 0) {
                        throw new ApplicationException("Problem in save image data in database");
                    }
                    byte[] bytearray = Base64.decode(img.getIMAGEDATA());
                    String ckycImageName = personalDetail.getCKYCNO() + "_" + img.getIMAGECODE() + "_" + new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
                    BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytearray));
                    ImageIO.write(imag, img.getIMAGETYPE(), new File(ckycrImageDirectory, ckycImageName + "." + img.getIMAGETYPE()));
                }
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return flag > 0 ? "Success" : "Unsuccess";
    }

    public DwndResPidData callDownloadRequest(CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException {
        DwndResPidData resPid = null;
        try {
            Encrypter enc = new Encrypter("/home/dsg-key/cersai.cer");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            DwndDataCreater dataCreator = new DwndDataCreater(enc);
            DwndReqPidData pid = dataCreator.prepareDwndPidData(ckycrRequest.getSearchDownloadNo(), sdf.parse(ckycrRequest.getDownloadDOB()));
            Header header = dataCreator.prepareDwndHeader(ckycrRequest.getFiCode(), ckycrRequest.getRequestId(), ckycrRequest.getVersion());

            CkycDwndReq kycReq = dataCreator.prepareDwndReq(header, pid);

            System.out.println(XMLUtilities.getXML(CkycDwndReq.class, kycReq, true, "CKYC_DOWNLOAD_REQUEST"));

            DigitalSigner signer = new DigitalSigner("/home/dsg-key/dsgkey.pfx", "password!9".toCharArray(), "");

            DataDecryptor decrypter = new DataDecryptor("/home/dsg-key/dsgkey.pfx", "password!9".toCharArray(), "/home/dsg-key/cersai.cer");

            CkycDownloadClient ckycClient = new CkycDownloadClient(new URI("https://testbed.ckycindia.in/Search/ckycverificationservice/download"));

            ckycClient.setDigitalSignator(signer);
            ckycClient.setDataDecryptor(decrypter);

            CkycDwndRes dwndRes = ckycClient.downloadCkycData(kycReq);

            Header resHeader = dwndRes.getHEADER();
            if (dwndRes.getCKYCINQ().getERROR() != null || !dwndRes.getCKYCINQ().getERROR().equals("")) {
                System.out.println(dwndRes.getCKYCINQ().getERROR());
            } else {
                String resPidData = dwndRes.getCKYCINQ().getPID();
                resPid = (DwndResPidData) XMLUtilities.parseXML(DwndResPidData.class, resPidData);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resPid;
    }

    public CKYCRDownloadPojo setCKYCRDownload20FromRealResponse(PERSONALDETAILS personalDetail) throws Exception {
        CKYCRDownloadPojo pojo = new CKYCRDownloadPojo();

        try {
            pojo.setRecordType("");
            pojo.setLineNo("");
            pojo.setApplicationType("");
            pojo.setBranchCode("");

            pojo.setApplicantNameUpdateFlag(personalDetail.getNAMEUPDATEFLAG());
            pojo.setPersonalEntityDetailsUpdateFlag(personalDetail.getPERSONALUPDATEFLAG());
            pojo.setAddressDetailsUpdateFlag(personalDetail.getADDRESSUPDATEFLAG());
            pojo.setContactDetailsUpdateFlag(personalDetail.getCONTACTUPDATEFLAG());
            pojo.setRemarksUpdateFlag(personalDetail.getREMARKSUPDATEFLAG());
            pojo.setkYCVerificationUpdateFlag(personalDetail.getKYCUPDATEFLAG());
            pojo.setIdentityDetailsUpdateFlag(personalDetail.getIDENTITYUPDATEFLAG());
            pojo.setRelatedPersonDetailsFlag(personalDetail.getRELPERSONUPDATEFLAG());

            pojo.setControllingPersonDetailsFlag("");

            pojo.setImageDetailsUpdateFlag(personalDetail.getIMAGEUPDATEFLAG());

            pojo.setConstitutionType(personalDetail.getCONSTITYPE());


            pojo.setAccHolderTypeFlag("");
            pojo.setAccHolderType("");



            pojo.setAccType(personalDetail.getACCTYPE());
            pojo.setcKYCno(personalDetail.getCKYCNO());
            pojo.setCustNamePrefix(personalDetail.getPREFIX());
            pojo.setCustFirstName(personalDetail.getFNAME());
            pojo.setCustMiddleName(personalDetail.getMNAME());
            pojo.setCustLastName(personalDetail.getLNAME());
            pojo.setCustFullName(personalDetail.getFULLNAME());
            pojo.setMaidenNamePrefix(personalDetail.getMAIDENPREFIX());
            pojo.setMaidenFirstName(personalDetail.getMAIDENFNAME());
            pojo.setMaidenMiddleName(personalDetail.getMAIDENMNAME());
            pojo.setMaidenLastName(personalDetail.getMAIDENLNAME());
            pojo.setMaidenFullName(personalDetail.getMAIDENFULLNAME());
            pojo.setFather_spouse_flag(personalDetail.getFATHERSPOUSEFLAG());
            pojo.setFatherSpouseNamePrefix(personalDetail.getFATHERPREFIX());
            pojo.setFatherSpouseFirstName(personalDetail.getFATHERFNAME());
            pojo.setFatherSpouseMiddleName(personalDetail.getFATHERMNAME());
            pojo.setFatherSpouseLastName(personalDetail.getFATHERLNAME());
            pojo.setFatherSpouseFullName(personalDetail.getFATHERFULLNAME());
            pojo.setMotherNamePrefix(personalDetail.getMOTHERPREFIX());
            pojo.setMotherFirstName(personalDetail.getMOTHERFNAME());
            pojo.setMotherMiddleName(personalDetail.getMOTHERMNAME());
            pojo.setMotherLastName(personalDetail.getMOTHERLNAME());
            pojo.setMotherFullName(personalDetail.getMOTHERFULLNAME());
            pojo.setGender(personalDetail.getGENDER());
            pojo.setMaritalStatus(personalDetail.getMARITALSTATUS());
            pojo.setNationality(personalDetail.getNATIONALITY());
            pojo.setOccupationType(personalDetail.getOCCUPATION());
            pojo.setDateOfBirth(personalDetail.getDOB().equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(personalDetail.getDOB())));

            pojo.setPlaceOfIncorporation("");
            pojo.setDateOfCommBusiness("".equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse("01-01-1900")));
            pojo.setCountryOfIncorporation("");
            pojo.setResidenceCountryTaxLaw("");
            pojo.setIdentificationType("");
            pojo.settINNo("");
            pojo.settINIssuingCountry("");
            pojo.setpAN("");

            pojo.setResidentialStatus(personalDetail.getRESISTATUS());
            pojo.setFlagCustResiForTaxJuriOutsideIN(personalDetail.getJURIFLAG());


            pojo.setJuriOfResidence(personalDetail.getJURIRESI());
            pojo.setJuriTaxIdentificationNo(personalDetail.getTAXNUM());

            pojo.setCountryOfBirth(personalDetail.getBIRTHCOUNTRY());
            pojo.setPlaceOfBirth(personalDetail.getBIRTHPLACE());

            pojo.setPerAddType(personalDetail.getPERMTYPE());
            pojo.setPerAddressLine1(personalDetail.getPERMLINE1());
            pojo.setPeraddressline2(personalDetail.getPERMLINE2());
            pojo.setPeraddressline3(personalDetail.getPERMLINE3());
            pojo.setPerCityVillage(personalDetail.getPERMCITY());
            pojo.setPerDistrict(personalDetail.getPERMDIST());
            pojo.setPerState(personalDetail.getPERMSTATE());
            pojo.setPerCountryCode(personalDetail.getPERMCOUNTRY());
            pojo.setPerPostalCode(personalDetail.getPERMPIN());
            pojo.setPerPOA(personalDetail.getPERMPOA());
            pojo.setPerOtherPOA(personalDetail.getPERMPOAOTHERS());
            pojo.setPerMailAddSameFlagIndicate(personalDetail.getPERMCORRESSAMEFLAG());


            pojo.setMailAddType("");


            pojo.setMailAddressLine1(personalDetail.getCORRESLINE1());
            pojo.setMailAddressLine2(personalDetail.getCORRESLINE2());
            pojo.setMailAddressLine3(personalDetail.getCORRESLINE3());
            pojo.setMailCityVillage(personalDetail.getCORRESCITY());
            pojo.setMailDistrict(personalDetail.getCORRESDIST());
            pojo.setMailState(personalDetail.getCORRESSTATE());
            pojo.setMailCountry(personalDetail.getCORRESCOUNTRY());
            pojo.setMailPostalCode(personalDetail.getCORRESPIN());


            pojo.setMailPOA("");

            pojo.setJuriAddType("");

            pojo.setJuriAddBasedOnFlag(personalDetail.getJURISAMEFLAG());
            pojo.setJuriAddressLine1(personalDetail.getJURILINE1());
            pojo.setJuriAddressLine2(personalDetail.getJURILINE2());
            pojo.setJuriAddressLine3(personalDetail.getJURILINE3());
            pojo.setJuriCityVillage(personalDetail.getJURICITY());
            pojo.setJuriState(personalDetail.getJURISTATE());
            pojo.setJuriCountry(personalDetail.getJURICOUNTRY());
            pojo.setJuriPostCode(personalDetail.getJURIPIN());

            pojo.setJuriPOA("");

            pojo.setResidenceTelSTDCode(personalDetail.getRESISTDCODE());
            pojo.setResidenceTelNo(personalDetail.getRESITELNUM());
            pojo.setOfficeTeleSTDCode(personalDetail.getOFFSTDCODE());
            pojo.setOfficeTelNo(personalDetail.getOFFTELNUM());
            pojo.setiSDCode(personalDetail.getMOBCODE());
            pojo.setMobileNo(personalDetail.getMOBNUM());
            pojo.setFaxSTDCode(personalDetail.getFAXCODE());
            pojo.setFaxNo(personalDetail.getFAXNO());
            pojo.setEmailID(personalDetail.getEMAIL());
            pojo.setRemarks(personalDetail.getREMARKS());
            pojo.setDateOfDeclaration(personalDetail.getDECDATE().equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(personalDetail.getDECDATE())));
            pojo.setPlaceOfDeclaration(personalDetail.getDECPLACE());
            pojo.setkYCVerificationDate(personalDetail.getKYCDATE().equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(personalDetail.getKYCDATE())));
            pojo.setTypeOfDocSubmitted(personalDetail.getDOCSUB());
            pojo.setkYCVerificationName(personalDetail.getKYCNAME());
            pojo.setkYCVerificDesignation(personalDetail.getKYCDESIGNATION());
            pojo.setkYCVerificBranch(personalDetail.getKYCBRANCH());
            pojo.setkYCVerificEMPCode(personalDetail.getKYCEMPCODE());
            pojo.setOrganisationName(personalDetail.getORGNAME());
            pojo.setOrganisationCode(personalDetail.getORGCODE());
            pojo.setNoOfIdentityDetails(personalDetail.getNUMIDENTITY());
            pojo.setNoOfrelatedpeople(personalDetail.getNUMRELATED());

            pojo.setNoOfControllingPersonResiOutsideIN("");

            pojo.setNoOfLocalAddDetails(personalDetail.getNUMLOCALADDRESS());
            pojo.setNoOfImages(personalDetail.getNUMIMAGES());

            pojo.setErrorCode("");
            pojo.setFiller1("");
            pojo.setFiller2("");
            pojo.setFiller3("");
            pojo.setFiller4("");
        } catch (ParseException ex) {
            throw new Exception(ex.getMessage());
        }
        return pojo;
    }

    public CKYCRDownloadDetail30 setCKYCRDownloadDetail30FromRealResponse(IDENTITY identity) throws Exception {
        CKYCRDownloadDetail30 pojo = new CKYCRDownloadDetail30();
        pojo.setRecordType("");
        pojo.setLineNumber("");

        pojo.setIdentificationtype(identity.getIDENTTYPE());
        pojo.setIdentityNumber(identity.getIDENTNUM());
        pojo.setExpiryDate(identity.getIDEXPIRYDATE().equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(identity.getIDEXPIRYDATE())));
        pojo.setIdProofSubmitted(identity.getIDPROOFSUBMITTED());
        pojo.setIdVerificationStatus(identity.getIDVERSTATUS());
        pojo.setFiller1("");
        pojo.setFiller2("");
        pojo.setFiller3("");
        pojo.setFiller4("");
        return pojo;
    }

    public CKYCRDownloadDetail60 setCKYCRDownloadDetail60FromRealResponse(LOCALADDRESS localAdd) throws Exception {
        CKYCRDownloadDetail60 pojo = new CKYCRDownloadDetail60();

        pojo.setRecordType("");
        pojo.setLineNumber("");
        pojo.setBranchCode(localAdd.getBRANCHCODE());

        pojo.setAddressType("");
        pojo.setSeqNo(localAdd.getSEQUENCENO());
        pojo.setLocalAddressLine1(localAdd.getADDRLINE1());
        pojo.setLocalAddressLine2(localAdd.getADDRLINE2());
        pojo.setLocalAddressLine3(localAdd.getADDRLINE3());
        pojo.setLocalAddressCityVillage(localAdd.getADDRCITY());
        pojo.setLocalAddressDistrict(localAdd.getADDRDIST());
        pojo.setLocalAddressPINCode(localAdd.getADDRPIN());
        pojo.setLocalAddressState(localAdd.getADDRSTATE());
        pojo.setLocalAddressCountry(localAdd.getADDRCOUNTRY());

        pojo.setProofofAdd("");

        pojo.setResiTelSTDCode(localAdd.getRESISTDCODE());
        pojo.setResiTelNo(localAdd.getRESITELNUM());
        pojo.setOfficeTelSTDCode(localAdd.getOFFSTDCODE());
        pojo.setOfficeTelNo(localAdd.getOFFTELNUM());
        pojo.setMobISDCode(localAdd.getMOBCODE());
        pojo.setMobileNo(localAdd.getMOBNUM());
        pojo.setFaxSTDCode(localAdd.getFAXCODE());
        pojo.setFaxNo(localAdd.getFAXNO());
        pojo.setEmailID(localAdd.getEMAIL());
        pojo.setDateofDeclaration(localAdd.getDECDATE().equals("") ? "" : ymdWihtoutSeparator.format(dmyHyphen.parse(localAdd.getDECDATE())));
        pojo.setPlaceofDeclaration(localAdd.getDECPLACE());

        pojo.setFiller1("");
        pojo.setFiller2("");
        pojo.setFiller3("");
        pojo.setFiller4("");
        return pojo;
    }

    public boolean isCKYCRDataExist(String ckycrNo) throws ApplicationException {
        try {
            String ckycrDetaialQuery = "select distinct CKYCno from ckycr_download where CKYCno='" + ckycrNo + "'";
            List selectList = em.createNativeQuery(ckycrDetaialQuery).getResultList();
            if (selectList.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

    }
}
