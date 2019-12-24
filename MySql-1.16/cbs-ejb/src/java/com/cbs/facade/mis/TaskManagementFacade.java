package com.cbs.facade.mis;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.mis.MarketingMisPojo;
import com.cbs.pojo.mis.TaskLeadTargetPojo;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import com.cbs.pojo.mis.TaskManagementPojo;
import com.cbs.pojo.mis.TaskUpdationPojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.transaction.RollbackException;

@Stateless(mappedName = "TaskManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TaskManagementFacade implements TaskManagementRemoteFacade {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @Resource
    SessionContext ctx;
    @EJB
    private CommonReportMethodsRemote commonReport;
    Date dt = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmyy = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dyy = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public List getCustomerDetailsbyAccountno(String accountNo) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select cs.customerid,cs.CustFullName,ifnull(cs.mobilenumber,'') as mobileNo,ci.acno,ifnull(cs.fathername,'') as fathername"
                    + ",ifnull(cs.emailid,'') as emailID,ifnull(cs.PerAddressLine1,'') as PerAddressLine1,ifnull(cs.peraddressline2,'') as peraddressline2,"
                    + "ifnull(cs.PerVillage,'') as PerVillage from  cbs_customer_master_detail cs,customerid ci  where  ci.acno='" + accountNo + "' and ci.custid=cs.customerid").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getUtilityReportDetailsforTaskManagement(String brnCode, String remarks, String frDate, String toDate, String searchBy, String searchByOption) throws ApplicationException {
        try {
            List list = new ArrayList();
            String subQuery = " and (r.details like '%" + remarks + "%' ";
            if (!searchByOption.equalsIgnoreCase("")) {
                if (searchByOption.contains(",")) {
                    String[] splitArray = searchByOption.split(",");
                    for (int i = 0; i < splitArray.length; i++) {
                        String subQuery1 = "  or r.details like '%" + splitArray[i] + "%' ";
                        subQuery = subQuery + subQuery1;

                    }
                } else {
                    String subQuery1 = "  or r.details like '%" + searchByOption + "%' ";
                    subQuery = subQuery + subQuery1;
                }
            }

            list = entityManager.createNativeQuery("select DISTINCT a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,ifnull(c.emailid,'') as emailID,ifnull(c.PerAddressLine1,'') as PerAddressLine1,ifnull(c.peraddressline2,'') as peraddressline2,ifnull(c.PerVillage,'') as PerVillage,ifnull(c.fathername,'') as fathername ,ifnull(c.FatherMiddleName,'') as FatherMiddleName,ifnull(c.FatherLastName,'') as FatherLastName"
                    + " from accountmaster a,cbs_customer_master_detail c,customerid i,recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                    + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "' " + subQuery + " )"
                    + " and substring(a.acno,1,2)='" + brnCode + "'"
                    + " union all "
                    + " select DISTINCT a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,ifnull(c.emailid,'') as emailID,ifnull(c.PerAddressLine1,'') as PerAddressLine1,ifnull(c.peraddressline2,'') as peraddressline2,ifnull(c.PerVillage,'') as PerVillage,ifnull(c.fathername,'') as fathername ,ifnull(c.FatherMiddleName,'') as FatherMiddleName,ifnull(c.FatherLastName,'') as FatherLastName"
                    + " from accountmaster a,cbs_customer_master_detail c,customerid i,ca_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                    + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "' " + subQuery + " )"
                    + " and substring(a.acno,1,2)='" + brnCode + "'"
                    + " union all "
                    + " select DISTINCT a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,ifnull(c.emailid,'') as emailID,ifnull(c.PerAddressLine1,'') as PerAddressLine1,ifnull(c.peraddressline2,'') as peraddressline2,ifnull(c.PerVillage,'') as PerVillage,ifnull(c.fathername,'') as fathername ,ifnull(c.FatherMiddleName,'') as FatherMiddleName,ifnull(c.FatherLastName,'') as FatherLastName"
                    + " from accountmaster a,cbs_customer_master_detail c,customerid i,loan_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                    + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "' " + subQuery + ") "
                    + " and substring(a.acno,1,2)='" + brnCode + "'"
                    + " union all "
                    + " select DISTINCT a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,ifnull(c.emailid,'') as emailID,ifnull(c.PerAddressLine1,'') as PerAddressLine1,ifnull(c.peraddressline2,'') as peraddressline2,ifnull(c.PerVillage,'') as PerVillage,ifnull(c.fathername,'') as fathername ,ifnull(c.FatherMiddleName,'') as FatherMiddleName,ifnull(c.FatherLastName,'') as FatherLastName"
                    + " from accountmaster a,cbs_customer_master_detail c,customerid i,ddstransaction r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                    + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "' " + subQuery + " )"
                    + " and substring(a.acno,1,2)='" + brnCode + "'"
                    + " union all "
                    + " select DISTINCT a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,ifnull(c.emailid,'') as emailID,ifnull(c.PerAddressLine1,'') as PerAddressLine1,ifnull(c.peraddressline2,'') as peraddressline2,ifnull(c.PerVillage,'') as PerVillage,ifnull(c.fathername,'') as fathername ,ifnull(c.FatherMiddleName,'') as FatherMiddleName,ifnull(c.FatherLastName,'') as FatherLastName"
                    + " from accountmaster a,cbs_customer_master_detail c,customerid i,rdrecon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                    + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "' " + subQuery + " )"
                    + " and substring(a.acno,1,2)='" + brnCode + "'"
                    + " union all "
                    + " select DISTINCT a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,ifnull(c.emailid,'') as emailID,ifnull(c.PerAddressLine1,'') as PerAddressLine1,ifnull(c.peraddressline2,'') as peraddressline2,ifnull(c.PerVillage,'') as PerVillage,ifnull(c.fathername,'') as fathername ,ifnull(c.FatherMiddleName,'') as FatherMiddleName,ifnull(c.FatherLastName,'') as FatherLastName"
                    + " from td_accountmaster a,cbs_customer_master_detail c,customerid i,td_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                    + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "' " + subQuery + ") "
                    + " and substring(a.acno,1,2)='" + brnCode + "'").getResultList();
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String insertDataTaskManagement(List<TaskManagementPojo> taskManagement, String sourceOfData, String sourceOfMarketing,
            String origin, String orgnbrncode, String remarks, String from_date, String to_date, String purpose, String purpose_desc,
            String status, String userToBeAssigned, String EnquiryType, String followUpDate, String customerRemark,
            String user, String brncode, String originbyFilter) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String followUptime = "00:00:00";
            ut.begin();
            int n = entityManager.createNativeQuery("insert into cbs_lead_mgmt_id (id,orgn_brncode,origin,origin_Filter,brn_code,source_of_data,"
                    + " source_of_marketing,ramark_of_search,from_dt,to_dt,entry_dt, enter_by,follow_up_dt)"
                    + " VALUES((select ifnull(max(id),0)+1 from cbs_lead_mgmt_id AS ID),'" + orgnbrncode + "','" + origin + "','" + originbyFilter + "','" + brncode + "',"
                    + "'" + sourceOfData + "','" + sourceOfMarketing + "', '" + remarks + "', '" + ymd.format(dmy.parse(from_date)) + "',"
                    + "'" + ymd.format(dmy.parse(to_date)) + "', now(),'" + user + "','" + ymd.format(dmy.parse(followUpDate)) + "')").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in cbs lead mgmt id Insertion");
            }
            for (TaskManagementPojo pojo : taskManagement) {
                int n1 = entityManager.createNativeQuery("insert into cbs_lead_mgmt_details(id,lead_id,acno,cust_name,father_name, "
                        + "address,city,email_id,cotact_no,alternate_contact_no, entry_dt)"
                        + " VALUES((select ifnull(max(id),0)+1 from cbs_lead_mgmt_details AS ID),"
                        + "(select max(id) from cbs_lead_mgmt_id AS ID),'" + pojo.getAccountNo() + "', '" + pojo.getCustomerName() + "',"
                        + "'" + pojo.getFatherName() + "','" + pojo.getAddress() + "','" + pojo.getCity() + "','" + pojo.getEmailId() + "',"
                        + "'" + pojo.getContactNo() + "','" + pojo.getAlternateContactNo() + "',now())").executeUpdate();
                if (n1 <= 0) {
                    throw new Exception("Problem in cbs lead mgmt details insertion");
                }
                int m = entityManager.createNativeQuery("INSERT INTO cbs_lead_follow_ups(lead_detail_id,purpose,purpose_desc, "
                        + "assigned_user,status,cust_remark, enquiry_type,follow_up_dt,follow_time, ref_acno,  entry_dt,enter_by )"
                        + "VALUES((select max(id) from cbs_lead_mgmt_details AS ID),'" + purpose + "','" + purpose_desc + "',"
                        + "'" + userToBeAssigned + "', '" + status + "','" + customerRemark + "','" + EnquiryType + "',"
                        + "'" + ymd.format(dmy.parse(followUpDate)) + "','" + followUptime + "','',now(),"
                        + " '" + user + "' )").executeUpdate();
                if (m <= 0) {
                    throw new Exception("Problem in cbs_lead_follow_ups insertion");
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception ex1) {
                throw new ApplicationException(ex1.getMessage());
            }
        }
        return "true";
    }

    @Override
    public List<TaskManagementPojo> getTaskManagementReport(String dataSource, String sourceOfMarketing, String frmdt, String toDt, String orgnbrnCode, String branch) throws Exception {
        List<TaskManagementPojo> list = new ArrayList<>();
        try {
            int srno = 1;
            String sourceOfdata = "", sourceOfmarketing = "", branchCode = "";
            if (!branch.equalsIgnoreCase("0A")) {
                branchCode = "mgmtId.brn_code='" + branch + "' and ";
            }

            if (!dataSource.equalsIgnoreCase("All")) {
                sourceOfdata = " mgmtId.source_of_data='" + dataSource + "' and ";
            }
            if (!sourceOfMarketing.equalsIgnoreCase("All")) {
                sourceOfmarketing = " mgmtId.source_of_marketing='" + sourceOfMarketing + "' and ";
            }
            String query = "Select (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and "
                    + " brncode=mgmtId.brn_code and userid=followup.assigned_user) as enter_by,"
                    + " date_format(mgmtId.entry_dt,'%d/%m/%Y'),"
                    + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=mgmtId.source_of_data) "
                    + " as source_of_data,(select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and "
                    + " ref_code=mgmtId.source_of_marketing) as source_of_marketing ,details.cust_name,details.cotact_no,"
                    + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followup.purpose) as purpose,"
                    + " followup.cust_remark,(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and "
                    + "  ref_code=followup.status) as status ,followup.follow_time ,"
                    + "  (select BranchName from branchmaster where brncode=mgmtId.brn_code ) as branch"
                    + "  from cbs_lead_mgmt_id mgmtId,"
                    + " cbs_lead_mgmt_details details,cbs_lead_follow_ups followup where  mgmtId.id=details.lead_id and "
                    + "  details.id=followup.lead_detail_id and followup.status='N'  and " + branchCode + sourceOfdata + sourceOfmarketing + " date_format(mgmtId.entry_dt,'%Y%m%d') between '" + frmdt + "'"
                    + "  and '" + toDt + "' order by mgmtId.origin ";

            if (branch.equalsIgnoreCase("0A")) {
                query = query + " ,mgmtId.brn_code";
            }
            if (dataSource.equalsIgnoreCase("All") && (!sourceOfMarketing.equalsIgnoreCase("All"))) {
                query = query + " , mgmtId.source_of_data";
            }
            if (sourceOfMarketing.equalsIgnoreCase("All") && (!dataSource.equalsIgnoreCase("All"))) {
                query = query + " , mgmtId.source_of_marketing";
            }
            if (dataSource.equalsIgnoreCase("All") && sourceOfMarketing.equalsIgnoreCase("All")) {
                query = query + " , mgmtId.source_of_data,mgmtId.source_of_marketing";
            }
//            System.out.println("Query:"+query);
            List dataList = entityManager.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("Data does not exists");
            }
            for (int i = 0; i < dataList.size(); i++) {
                TaskManagementPojo pojo = new TaskManagementPojo();
                Vector v = (Vector) dataList.get(i);
                pojo.setSrNo(srno++);
                pojo.setStaffName(v.get(0) == null ? "" : v.get(0).toString());
                pojo.setDate(v.get(1) == null ? "" : v.get(1).toString());
                pojo.setSourceOfData(v.get(2) == null ? "" : v.get(2).toString());
                pojo.setSourceOfMarketing(v.get(3) == null ? "" : v.get(3).toString());
                pojo.setCustomerName(v.get(4) == null ? "" : v.get(4).toString().trim());
                pojo.setContactNo(v.get(5) == null ? "" : v.get(5).toString());
                pojo.setPurpose(v.get(6) == null ? "" : v.get(6).toString());
                pojo.setRemarks(v.get(7) == null ? "" : v.get(7).toString().trim());
                pojo.setStatus(v.get(8) == null ? "" : v.get(8).toString());
                pojo.setBranch(v.get(10) == null ? "" : v.get(10).toString());
                list.add(pojo);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return list;
    }

    @Override
    public List getDataByUserName(String user, String orgnBrcode, String branch, String userAssigned, String levelId) throws ApplicationException {
        try {
            if ((!orgnBrcode.equalsIgnoreCase("90")) && levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2")) {
                if (user.equalsIgnoreCase(userAssigned)) {
                    return entityManager.createNativeQuery("select detail.id, detail.lead_id, detail.acno, detail.cust_name,"
                            + "ifnull(detail.father_name,''),ifnull(detail.address,''),ifnull(detail.city,''),ifnull(detail.email_id,''),"
                            + "ifnull(detail.cotact_no,''),ifnull(detail.alternate_contact_no,''),"
                            + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and ref_code=a.status) as status, "
                            + " date_format(a.follow_up_dt,'%d/%m/%Y'),date_format(a.follow_time,'%H:%i:%s'),"
                            + "date_format(a.entry_dt,'%d/%m/%Y %H:%i:%s'),a.status from cbs_lead_follow_ups a,"
                            + "(select max(id) as id,lead_detail_id  from cbs_lead_follow_ups group by lead_detail_id) b ,"
                            + " cbs_lead_mgmt_details detail ,cbs_lead_mgmt_id  lead where a.lead_detail_id = b.lead_detail_id "
                            + " and a.id = b.id and (a.status = 'N' or a.status='F') and detail.id=a.lead_detail_id "
                            + " and  lead.id=detail.lead_id  and  a.assigned_user='" + userAssigned + "' and lead.brn_code='" + branch + "' and "
                            + " date_format(a.follow_up_dt,'%Y%m%d') <='" + ymd.format(dt) + "'").getResultList();
                } else {
                    return entityManager.createNativeQuery("select detail.id, detail.lead_id, detail.acno, detail.cust_name,"
                            + "ifnull(detail.father_name,''),ifnull(detail.address,''),ifnull(detail.city,''),ifnull(detail.email_id,''),"
                            + "ifnull(detail.cotact_no,''),ifnull(detail.alternate_contact_no,''),"
                            + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and ref_code=a.status) as status, "
                            + " date_format(a.follow_up_dt,'%d/%m/%Y'),date_format(a.follow_time,'%H:%i:%s'),"
                            + "date_format(a.entry_dt,'%d/%m/%Y %H:%i:%s'),a.status from cbs_lead_follow_ups a,"
                            + "(select max(id) as id,lead_detail_id  from cbs_lead_follow_ups group by lead_detail_id) b ,"
                            + " cbs_lead_mgmt_details detail ,cbs_lead_mgmt_id  lead where a.lead_detail_id = b.lead_detail_id "
                            + " and a.id = b.id and (a.status = 'N' or a.status='F') and detail.id=a.lead_detail_id "
                            + " and  lead.id=detail.lead_id  and  a.assigned_user='" + userAssigned + "' and lead.brn_code='" + branch + "' and "
                            + " date_format(a.follow_up_dt,'%Y%m%d') >='" + ymd.format(dt) + "'").getResultList();
                }
            } else {
                return entityManager.createNativeQuery("select detail.id, detail.lead_id, detail.acno, detail.cust_name,"
                        + "ifnull(detail.father_name,''),ifnull(detail.address,''),ifnull(detail.city,''),ifnull(detail.email_id,''),"
                        + "ifnull(detail.cotact_no,''),ifnull(detail.alternate_contact_no,''),"
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and ref_code=a.status) as status, "
                        + " date_format(a.follow_up_dt,'%d/%m/%Y'),date_format(a.follow_time,'%H:%i:%s'),"
                        + "date_format(a.entry_dt,'%d/%m/%Y %H:%i:%s'),a.status from cbs_lead_follow_ups a,"
                        + "(select max(id) as id,lead_detail_id  from cbs_lead_follow_ups group by lead_detail_id) b ,"
                        + " cbs_lead_mgmt_details detail ,cbs_lead_mgmt_id  lead where a.lead_detail_id = b.lead_detail_id "
                        + " and a.id = b.id and (a.status = 'N' or a.status='F') and detail.id=a.lead_detail_id "
                        + " and  lead.id=detail.lead_id  and  a.assigned_user='" + userAssigned + "' and lead.brn_code='" + branch + "' and "
                        + " date_format(a.follow_up_dt,'%Y%m%d') <='" + ymd.format(dt) + "'").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getTaskInformationByDetail(String detailId) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("SELECT followUp.id, followUp.lead_detail_id, followUp.purpose,"
                    + " followUp.purpose_desc, followUp.status,followUp.cust_remark,followUp.enquiry_type,"
                    + " followUp.assigned_user, date_format(followUp.follow_up_dt,'%d/%m/%Y'), "
                    + " date_format(followUp.follow_time,'%H:%i:%s'),followUp.ref_acno,followUp.entry_dt,"
                    + " followUp.enter_by from cbs_lead_follow_ups followUp ,"
                    + " (select max(id) as id,lead_detail_id  from cbs_lead_follow_ups group by lead_detail_id) b ,"
                    + " cbs_lead_mgmt_details detail ,cbs_lead_mgmt_id  lead"
                    + " where detail.id ='" + detailId + "' and followUp.lead_detail_id = b.lead_detail_id and followUp.id = b.id and"
                    + " detail.id=followUp.lead_detail_id and lead.id=detail.lead_id").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String updateDataTaskUpdation(List<TaskUpdationPojo> dataList, String purpose, String purpose_desc, String status,
            String userToBeAssigned, String EnquiryType, String followupDate, String followUptime, String customerRemarks,
            String user, String referredAccountNo, String accountOpeningDate, String personalVisitStatus, String personalVisitTime, String personalVisitDate, boolean checkBox) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String followDate = "", followTime = "00:00:00", refAcno = "";
            ut.begin();
            for (TaskUpdationPojo pojo : dataList) {
                if (status.equalsIgnoreCase("T")) {
                    if (pojo.getAlternateContactNo() == null) {
                        pojo.setAlternateContactNo("");
                    }
                    int n1 = entityManager.createNativeQuery("insert into cbs_lead_mgmt_details(id,lead_id,acno,cust_name,"
                            + "father_name, address,city,email_id,cotact_no,alternate_contact_no, entry_dt)"
                            + " VALUES((select ifnull(max(id),0)+1 from cbs_lead_mgmt_details AS ID),'" + pojo.getLeadId() + "',"
                            + "'" + pojo.getAccountNo() + "', '" + pojo.getCustomerName() + "','" + pojo.getFatherName() + "',"
                            + "'" + pojo.getAddress() + "','" + pojo.getCity() + "','" + pojo.getEmailId() + "',"
                            + "'" + pojo.getContactNo() + "','" + pojo.getAlternateContactNo() + "',now())").executeUpdate();
                    if (n1 <= 0) {
                        throw new Exception("Problem in cbs lead mgmt details insertion");
                    }
                    int m1 = entityManager.createNativeQuery("INSERT INTO cbs_lead_follow_ups (lead_detail_id,purpose,purpose_desc,assigned_user, "
                            + " status,cust_remark,enquiry_type,follow_up_dt,follow_time,ref_acno,personal_Visit_Status,"
                            + " personal_Visit_Date,  personal_Visit_Time,  entry_dt,  enter_by ) "
                            + " VALUES ((select max(id) from cbs_lead_mgmt_details AS ID),'" + purpose + "','" + purpose_desc + "',"
                            + "'" + userToBeAssigned + "', 'N','" + customerRemarks + "','" + EnquiryType + "',"
                            + "'" + ymd.format(dmy.parse(followupDate)) + "','" + followUptime + "','','',null,"
                            + " null,now(),"
                            + "'" + user + "')").executeUpdate();
                    if (m1 <= 0) {
                        throw new Exception("Problem in cbs_lead_follow_ups insertion");
                    }
                } else {
                    if (status.equalsIgnoreCase("F")) {
                        followDate = ymd.format(dmy.parse(followupDate));
                        followTime = followUptime;
                        if (checkBox) {
                            int m = entityManager.createNativeQuery("INSERT INTO cbs_lead_follow_ups (lead_detail_id,purpose,purpose_desc,assigned_user, "
                                    + " status,cust_remark,enquiry_type,follow_up_dt,follow_time,ref_acno,personal_Visit_Status,"
                                    + " personal_Visit_Date,  personal_Visit_Time,  entry_dt,  enter_by ) "
                                    + " VALUES ('" + pojo.getDetailId() + "','" + purpose + "','" + purpose_desc + "',"
                                    + " '" + userToBeAssigned + "', '" + status + "','" + customerRemarks + "','" + EnquiryType + "',"
                                    + " '" + followDate + "','" + followTime + "','" + refAcno + "', "
                                    + " '" + personalVisitStatus + "','" + ymd.format(dmy.parse(personalVisitDate)) + "','" + personalVisitTime + "' ,now(), '" + user + "' )").executeUpdate();
                            if (m <= 0) {
                                throw new Exception("Problem in cbs_lead_follow_ups insertion");
                            }
                        } else {
                            int m = entityManager.createNativeQuery("INSERT INTO cbs_lead_follow_ups (lead_detail_id,purpose,purpose_desc,assigned_user, "
                                    + " status,cust_remark,enquiry_type,follow_up_dt,follow_time,ref_acno,personal_Visit_Status,"
                                    + " personal_Visit_Date,  personal_Visit_Time,  entry_dt,  enter_by ) "
                                    + " VALUES ('" + pojo.getDetailId() + "','" + purpose + "','" + purpose_desc + "',"
                                    + " '" + userToBeAssigned + "', '" + status + "','" + customerRemarks + "','" + EnquiryType + "',"
                                    + " '" + followDate + "','" + followTime + "','" + refAcno + "', 'N', "
                                    + " null,null,now(), '" + user + "' )").executeUpdate();
                            if (m <= 0) {
                                throw new Exception("Problem in cbs_lead_follow_ups insertion");
                            }
                        }
                    } else if (status.equalsIgnoreCase("B")) {
                        followDate = ymd.format(dt);
                        refAcno = referredAccountNo;
                        int m = entityManager.createNativeQuery("INSERT INTO cbs_lead_follow_ups (lead_detail_id,purpose,purpose_desc,assigned_user, "
                                + " status,cust_remark,enquiry_type,follow_up_dt,follow_time,ref_acno,personal_Visit_Status,"
                                + " personal_Visit_Date,  personal_Visit_Time,  entry_dt,  enter_by ) "
                                + " VALUES ('" + pojo.getDetailId() + "','" + purpose + "','" + purpose_desc + "',"
                                + " '" + userToBeAssigned + "', '" + status + "','" + customerRemarks + "','" + EnquiryType + "',"
                                + " '" + followDate + "','" + followTime + "','" + refAcno + "', '', "
                                + " null,null,now(), '" + user + "' )").executeUpdate();
                        if (m <= 0) {
                            throw new Exception("Problem in cbs_lead_follow_ups insertion");
                        }
                    } else if (status.equalsIgnoreCase("R")) {
                        followDate = ymd.format(dt);
                        int m = entityManager.createNativeQuery("INSERT INTO cbs_lead_follow_ups (lead_detail_id,purpose,purpose_desc,assigned_user, "
                                + " status,cust_remark,enquiry_type,follow_up_dt,follow_time,ref_acno,personal_Visit_Status,"
                                + " personal_Visit_Date,  personal_Visit_Time,  entry_dt,  enter_by ) "
                                + " VALUES ('" + pojo.getDetailId() + "','" + purpose + "','" + purpose_desc + "',"
                                + " '" + userToBeAssigned + "', '" + status + "','" + customerRemarks + "','" + EnquiryType + "',"
                                + " '" + followDate + "','" + followTime + "','" + refAcno + "', '', "
                                + " null,null,now(), '" + user + "' )").executeUpdate();
                        if (m <= 0) {
                            throw new Exception("Problem in cbs_lead_follow_ups insertion");
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "true";
    }

    @Override
    public String insertDataLeadTarget(String orgn_brncode, String branchCode, BigInteger finacialYear, String purpose, String sourceofMkt, String fromDate,
            String todate, BigInteger target, String assignedUser, String entryType, String username) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (orgn_brncode.equalsIgnoreCase("90")) {
                entryType = "Y";
                sourceofMkt = "";
                assignedUser = "";
            }
            if (!orgn_brncode.equalsIgnoreCase("90")) {
                if (entryType.equalsIgnoreCase("D")) {
                    purpose = "";
                }
                if (entryType.equalsIgnoreCase("M")) {
                    sourceofMkt = "";
                }
            }
            int n = entityManager.createNativeQuery("INSERT INTO  cbs_lead_target(orgn_brncode,brn_code,financial_year,entryType, purpose,"
                    + "souce_of_marketing,from_dt,to_dt,assigned_user,target,entry_dt,enter_by)"
                    + "VALUES ('" + orgn_brncode + "','" + branchCode + "','" + finacialYear + "','" + entryType + "',"
                    + "'" + purpose + "','" + sourceofMkt + "','" + ymd.format(dmy.parse(fromDate)) + "' ,"
                    + "'" + ymd.format(dmy.parse(todate)) + "','" + assignedUser + "','" + target + "',now(),'" + username + "')").executeUpdate();
            if (n <= 0) {
                throw new Exception("problem in cbs_lead_target insertion");
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "true";
    }

    @Override
    public List getTargetDetails(String orgnBrncode, String branch, String enterBy) throws ApplicationException {
        List<TaskLeadTargetPojo> list = new ArrayList<>();
        try {
            if (orgnBrncode.equalsIgnoreCase("90") && enterBy.equalsIgnoreCase("Y")) {
                String query = "select  ty.orgn_brncode,ty.brn_code,ty.purposevalue,ty.entrytype,ty.purpose,ty.branchalphacode,"
                        + " targetcount.target,ifnull(targetcount.totalachieved,0),ty.financial_year,ty.fromdt,ty.TODT,ty.id,ty.ENTRYDATE from "
                        + " (select target.purpose,target.target,target.brn_code,achievedtarget.totalachieved,"
                        + " achievedtarget.targetachievedbranch from"
                        + " (select purpose,brn_code,sum(target) as target from cbs_lead_target where entrytype='y' and brn_code='" + branch + "'"
                        + " group by brn_code,purpose) target left join "
                        + " (select f.purpose,count(f.id)as totalachieved, leadid.brn_code as targetAchievedBranch, leadid.orgn_brncode "
                        + " from cbs_lead_follow_ups f, cbs_lead_mgmt_id leadid,"
                        + " cbs_lead_mgmt_details detailid"
                        + " where f.status='B' and (f.ref_acno!=''|| f.ref_acno!=null) and   leadid.id=detailid.lead_id "
                        + " and detailid.id=f.lead_detail_id  group by f.purpose,leadid.brn_code) achievedtarget on "
                        + "  target.purpose =achievedtarget.purpose and target.brn_code=achievedtarget.targetAchievedBranch group by"
                        + "   target.purpose,target.brn_code)as targetcount left join"
                        + " (select id,orgn_brncode,brn_code,purpose,entryType, "
                        + " (select ref_desc from cbs_ref_rec_type "
                        + " where ref_rec_no='423' and  ref_code=t.purpose) as purposevalue,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and "
                        + " brncode='" + branch + "' and userid=enter_by) as enter_byy,date_format(from_dt,'%d/%m/%Y') as fromdt,"
                        + " date_format(to_dt,'%d/%m/%Y') AS TODT,DATE_FORMAT(ENTRY_DT,'%d/%m/%Y') AS ENTRYDATE,ENTER_BY,"
                        + " (SELECT ALPHACODE FROM branchmaster where brncode = brn_code) as branchAlphacode,"
                        + " financial_year"
                        + " from cbs_lead_target t  where entryType ='Y' and brn_code='" + branch + "' group by brn_code, purpose order by "
                        + " branchAlphacode) ty"
                        + " on ty.purpose=targetcount.purpose and  ty.brn_code=targetcount.brn_code "
                        + " group by targetcount.purpose,ty.brn_code order by ty.branchAlphacode,ty.entrydate;"
                        + " ";
                list = entityManager.createNativeQuery(query).getResultList();
            }
            if (orgnBrncode.equalsIgnoreCase("90") && enterBy.equalsIgnoreCase("M")) {
                String query = "select  ty.orgn_brncode,ty.brn_code,ty.purposevalue,ty.entryType,ty.purpose,ty.assignedUser,ty.enter_by,"
                        + " ty.enter_byy,ty.entrydate,ty.branchAlphacode,target.target,ty.financial_year,ty.fromdt,ty.todt,ty.assigned_user,ty.id"
                        + " from  (select purpose,brn_code,assigned_user,"
                        + " sum(target) as target,date_format(entry_dt,'%d/%m/%Y')as entry_dt,enter_by from  cbs_lead_target "
                        + " where entryType='M'and brn_code='" + branch + "' group by"
                        + " brn_code,purpose,enter_by,assigned_user) target left join (select t.id,t.orgn_brncode,t.brn_code,t.purpose,"
                        + " t.entryType,t.assigned_user,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=t.purpose)  as purposevalue,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and "
                        + " brncode=t.brn_code and userid=t.assigned_user) as assignedUser,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and  "
                        + " brncode=t.brn_code and userid=t.enter_by) as enter_byy"
                        + " ,date_format(t.from_dt,'%d/%m/%Y') as fromdt,  date_format(t.to_dt,'%d/%m/%Y') as todt,"
                        + "  date_format(t.entry_dt,'%d/%m/%Y')  as entrydate,enter_by,"
                        + " (select alphacode from branchmaster where brncode = t.brn_code) as branchAlphacode ,financial_year"
                        + " from cbs_lead_target t  where t.entryType ='M' "
                        + " and t.brn_code='" + branch + "' group by t.brn_code,t.enter_by,t.purpose,t.assigned_user order by branchAlphacode) ty "
                        + " on ty.purpose=target.purpose and  ty.entrydate=target.entry_dt"
                        + " and ty.brn_code=target.brn_code and ty.enter_by=target.enter_by and ty.assigned_user=target.assigned_user group by"
                        + "  target.purpose,ty.brn_code,ty.enter_by,ty.assigned_user"
                        + " order by ty.branchAlphacode,ty.entrydate  ";
                list = entityManager.createNativeQuery(query).getResultList();
            }

            if (!(orgnBrncode.equalsIgnoreCase("90")) && enterBy.equalsIgnoreCase("M")) {
                String query = "select  ty.orgn_brncode,ty.brn_code,ty.purposevalue,ty.entryType,ty.purpose,ty.assignedUser,ty.enter_by,"
                        + " ty.enter_byy,ty.entrydate,ty.branchAlphacode,target.target,ty.financial_year,ty.fromdt,ty.todt,ty.assigned_user,ty.id"
                        + " from  (select purpose,brn_code,assigned_user,"
                        + " sum(target) as target,date_format(entry_dt,'%d/%m/%Y')as entry_dt,enter_by from  cbs_lead_target "
                        + " where entryType='M'and brn_code='" + branch + "' group by"
                        + " brn_code,purpose,enter_by,assigned_user) target left join (select t.id,t.orgn_brncode,t.brn_code,t.purpose,"
                        + " t.entryType,t.assigned_user,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=t.purpose)  as purposevalue,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and "
                        + " brncode=t.brn_code and userid=t.assigned_user) as assignedUser,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and  "
                        + " brncode=t.brn_code and userid=t.enter_by) as enter_byy"
                        + " ,date_format(t.from_dt,'%d/%m/%Y') as fromdt,  date_format(t.to_dt,'%d/%m/%Y') as todt,"
                        + "  date_format(t.entry_dt,'%d/%m/%Y')  as entrydate,enter_by,"
                        + " (select alphacode from branchmaster where brncode = t.brn_code) as branchAlphacode ,financial_year"
                        + " from cbs_lead_target t  where t.entryType ='M' "
                        + " and t.brn_code='" + branch + "' group by t.brn_code,t.enter_by,t.purpose,t.assigned_user order by branchAlphacode) ty "
                        + " on ty.purpose=target.purpose and  ty.entrydate=target.entry_dt"
                        + " and ty.brn_code=target.brn_code and ty.enter_by=target.enter_by and ty.assigned_user=target.assigned_user group by"
                        + "  target.purpose,ty.brn_code,ty.enter_by,ty.assigned_user"
                        + " order by ty.branchAlphacode,ty.entrydate  ";
                list = entityManager.createNativeQuery(query).getResultList();

            }
            if (!(orgnBrncode.equalsIgnoreCase("90")) && enterBy.equalsIgnoreCase("D")) {
                String query = "select  ty.orgn_brncode,ty.brn_code,ty.sourcemarketingvalue,ty.entryType,ty.souce_of_marketing,ty.enter_by,"
                        + "ty.enter_byy,ty.entrydate,ty.branchAlphacode,target.target,ty.assigned_user1,ty.financial_year,ty.frommdt,ty.toodt,ty.assigned_user ,ty.id from "
                        + "(select souce_of_marketing,brn_code,sum(target) as target,date_format(entry_dt,'%d/%m/%Y')as entry_dt ,enter_by,assigned_user"
                        + " from cbs_lead_target where entryType='D'and brn_code='" + branch + "' group by brn_code,souce_of_marketing,assigned_user,date_format(entry_dt,'%d/%m/%Y')) target left join"
                        + " (select id,orgn_brncode,brn_code,souce_of_marketing,entryType,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and  ref_code=t.souce_of_marketing) as sourcemarketingvalue,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and"
                        + "  brncode='" + branch + "' and userid=enter_by) as enter_byy,date_format(from_dt,'%d/%m/%Y') as fromdt,"
                        + "  date_format(to_dt,'%d/%m/%Y') as todt,date_format(entry_dt,'%d/%m/%Y') as entrydate,enter_by,"
                        + " (select alphacode from branchmaster where brncode = '" + branch + "') as branchAlphacode,assigned_user,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and"
                        + "  brncode='" + branch + "' and userid=assigned_user) as assigned_user1 ,financial_year,date_format(from_dt,'%d/%m/%Y') as frommdt,"
                        + " date_format(to_dt,'%d/%m/%Y')as toodt"
                        + "  from cbs_lead_target t  where entryType ='D' and brn_code='" + branch + "' group by brn_code,souce_of_marketing,assigned_user,entrydate order by branchAlphacode,entrydate) ty"
                        + " on ty.souce_of_marketing=target.souce_of_marketing and  ty.brn_code=target.brn_code and ty.assigned_user=target.assigned_user and ty.entrydate=target.entry_dt"
                        + " group by target.souce_of_marketing,ty.brn_code,ty.assigned_user,ty.entrydate order by ty.branchAlphacode,ty.entrydate ";

                list = entityManager.createNativeQuery(query).getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    @Override
    public List getTaskUpdationHistoryDetails(String detailId, String user) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select leadDetail.acno,leadDetail.cust_name,"
                    + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and "
                    + "ref_code=follow.status) as status,"
                    + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='425' and ref_code=follow.enquiry_type) "
                    + "as enquirytype,"
                    + "follow.cust_remark,date_format(follow.entry_dt,'%d/%m/%Y %H:%i:%s'),follow.follow_time,"
                    + "date_format(follow.follow_up_dt,'%d/%m/%Y'),"
                    + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=mgmtId.source_of_data)"
                    + "as source_of_data,"
                    + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and "
                    + "ref_code=mgmtId.source_of_marketing) as source_of_marketing ,follow.status "
                    + "from cbs_lead_mgmt_id  mgmtId, cbs_lead_mgmt_details leadDetail,cbs_lead_follow_ups follow "
                    + "where leadDetail.id='" + detailId + "'  and follow.assigned_user='" + user + "'"
                    + "and date_format(follow.follow_up_dt,'%Y%m%d') <='" + ymd.format(dt) + "' and "
                    + "mgmtId.id=leadDetail.lead_id and leadDetail.id=follow.lead_detail_id").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getRefferedAccountNoDetail(String refferedAcno) throws Exception {
        try {
            return entityManager.createNativeQuery("select * from cbs_lead_follow_ups  where ref_acno='" + refferedAcno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<TaskUpdationPojo> getUpdationReportDataList(String frmdt, String todt, String branchCode, String userName, String levelId, String branch, String userassigend) throws Exception {
        List<TaskUpdationPojo> list = new ArrayList<>();
        try {
            String brncode = "", query = "";
            if ((!(branchCode.equalsIgnoreCase("90") || branchCode.equalsIgnoreCase("0A"))) && (!(levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2")))) {
                query = "select detail.id, detail.acno, detail.cust_name,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=lead.source_of_data)"
                        + " as source_of_data, (select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and "
                        + " ref_code=lead.source_of_marketing) as source_of_marketing ,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followUp.purpose) as purpose,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='426' and  ref_code=followUp.purpose_desc) as purposeDesc,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and  ref_code=followUp.status) as status,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='425' and  ref_code=followUp.enquiry_type) as enquirytype,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode= lead.brn_code and"
                        + " userid=followUp.assigned_user) as assigneduser, date_format(followUp.follow_up_dt,'%d/%m/%Y'),"
                        + " followUp.follow_time ,date_format(followUp.entry_dt,'%d/%m/%Y'),followUp.cust_remark,followUp.ref_acno ,"
                        + " (select BranchName from branchmaster where brncode=lead.brn_code ) as branch ,followUp.status"
                        + " from cbs_lead_follow_ups followUp,cbs_lead_mgmt_details detail,cbs_lead_mgmt_id  lead  where "
                        + " detail.id=followUp.lead_detail_id and lead.id=detail.lead_id and lead.brn_code='" + branch + "'"
                        + "  and followUp.assigned_user='" + userassigend + "' and DATE_FORMAT(followUp.entry_dt ,'%Y%m%d') "
                        + " between '" + ymd.format(dmy.parse(frmdt)) + "' and '" + ymd.format(dmy.parse(todt)) + "' order by detail.id,lead.brn_code,followUp.entry_dt";
            }
            if ((!(branchCode.equalsIgnoreCase("90") || branchCode.equalsIgnoreCase("0A"))) && (levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2"))) {
                query = "select detail.id, detail.acno, detail.cust_name,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=lead.source_of_data)"
                        + " as source_of_data, (select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and "
                        + " ref_code=lead.source_of_marketing) as source_of_marketing ,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followUp.purpose) as purpose,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='426' and  ref_code=followUp.purpose_desc) as purposeDesc,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and  ref_code=followUp.status) as status,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='425' and  ref_code=followUp.enquiry_type) as enquirytype,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode=lead.brn_code and"
                        + " userid=followUp.assigned_user) as assigneduser, date_format(followUp.follow_up_dt,'%d/%m/%Y'),"
                        + " followUp.follow_time ,date_format(followUp.entry_dt,'%d/%m/%Y'),followUp.cust_remark,followUp.ref_acno ,"
                        + " (select BranchName from branchmaster where brncode=lead.brn_code ) as branch ,followUp.status"
                        + " from cbs_lead_follow_ups followUp,cbs_lead_mgmt_details detail,cbs_lead_mgmt_id  lead  where "
                        + " detail.id=followUp.lead_detail_id and lead.id=detail.lead_id and lead.brn_code='" + branch + "'"
                        + "  and followUp.assigned_user='" + userassigend + "' and  DATE_FORMAT(followUp.entry_dt ,'%Y%m%d') "
                        + " between '" + ymd.format(dmy.parse(frmdt)) + "' and '" + ymd.format(dmy.parse(todt)) + "' order by detail.id,lead.brn_code,followUp.entry_dt";
            }
            if (branchCode.equalsIgnoreCase("90") || branchCode.equalsIgnoreCase("0A")) {
                if (!(branch.equalsIgnoreCase("0A"))) {
                    brncode = " lead.brn_code='" + branch + "' and ";
                }
                query = " select detail.id, detail.acno, detail.cust_name,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=lead.source_of_data)"
                        + " as source_of_data, (select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and "
                        + " ref_code=lead.source_of_marketing) as source_of_marketing ,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followUp.purpose) as purpose,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='426' and  ref_code=followUp.purpose_desc) as purposeDesc,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and  ref_code=followUp.status) as status,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='425' and  ref_code=followUp.enquiry_type) as enquirytype,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode= lead.brn_code  and"
                        + " userid=followUp.assigned_user) as assigneduser, date_format(followUp.follow_up_dt,'%d/%m/%Y'),"
                        + " followUp.follow_time ,date_format(followUp.entry_dt,'%d/%m/%Y'),followUp.cust_remark,followUp.ref_acno ,"
                        + " (select BranchName from branchmaster where brncode=lead.brn_code ) as branch ,followUp.status"
                        + " from cbs_lead_follow_ups followUp,cbs_lead_mgmt_details detail,cbs_lead_mgmt_id  lead  where "
                        + " detail.id=followUp.lead_detail_id and lead.id=detail.lead_id and " + brncode
                        + " followUp.assigned_user='" + userassigend + "' and  DATE_FORMAT(followUp.entry_dt ,'%Y%m%d') "
                        + " between '" + ymd.format(dmy.parse(frmdt)) + "' and '" + ymd.format(dmy.parse(todt)) + "' order by detail.id,followUp.entry_dt";
                if (branch.equalsIgnoreCase("0A")) {
                    query = query + ", lead.brn_code";
                }
            }


            List list1 = entityManager.createNativeQuery(query).getResultList();
            if (list1.isEmpty()) {
                throw new Exception("Data does not exists");
            }
            int srno = 1;
            for (int i = 0; i < list1.size(); i++) {
                TaskUpdationPojo pojo = new TaskUpdationPojo();
                Vector v = (Vector) list1.get(i);
                pojo.setSrNo(srno++);
                pojo.setDetailId(v.get(0).toString());
                pojo.setAccountNo(v.get(1) == null ? "" : v.get(1).toString());
                pojo.setCustomerName(v.get(2).toString());
                pojo.setPurpose(v.get(5) == null ? "" : v.get(5).toString());
                pojo.setPurposeType(v.get(6) == null ? "" : v.get(6).toString());
                pojo.setStatus(v.get(7) == null ? "" : v.get(7).toString());
                pojo.setEnquiryType(v.get(8) == null ? "" : v.get(8).toString());
                pojo.setEnterBy(v.get(9) == null ? "" : v.get(9).toString());
                String time[] = v.get(11).toString().split(":");
                if (!v.get(16).toString().equalsIgnoreCase("N")) {
                    String followUpTime = "";
                    if (Integer.parseInt(time[0].toString()) == 00) {
                        followUpTime = "12" + ":" + time[1].toString() + " " + "AM";
                    } else {
                        int hour;
                        if (Integer.parseInt(time[0].toString()) < 12) {
                            followUpTime = time[0].toString() + ":" + time[1].toString() + " " + "AM";
                        }
                        if (Integer.parseInt(time[0].toString()) == 12) {
                            followUpTime = "12" + ":" + time[1].toString() + " " + "PM";
                        }
                        if (Integer.parseInt(time[0].toString()) > 12) {
                            hour = Integer.parseInt(time[0]) - 12;
                            followUpTime = String.valueOf(hour) + ":" + time[1].toString() + " " + "PM";
                        }
                    }
                    pojo.setFollowUpDatetime(v.get(10) == null ? "" : v.get(10).toString() + " " + followUpTime);
                } else {
                    pojo.setFollowUpDatetime(v.get(10) == null ? "" : v.get(10).toString());

                }
                pojo.setFollowedDateTime(v.get(12) == null ? "" : v.get(12).toString());
                pojo.setFollowUpRemarks(v.get(13) == null ? "" : v.get(13).toString());
                pojo.setRefferedAcNo(v.get(14) == null ? "" : v.get(14).toString());
                pojo.setBranch(v.get(15) == null ? "" : v.get(15).toString());

                list.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return list;
    }

    @Override
    public List<MarketingMisPojo> getMarketingMisReportData(String branch, String frmdt, String todt, String entryType, String followFrmdt, String followTodt, String reportType, String misType) throws Exception {
        List<MarketingMisPojo> list1 = new ArrayList<>();
        try {
            String brncode = "";
            if (!branch.equalsIgnoreCase("0A")) {
                brncode = " where ss.brn_code= '" + branch + "' ";
            }
            if (reportType.equalsIgnoreCase("N")) { //New Entry
                followFrmdt = frmdt;
                followTodt = todt;
            }
            String newEntryQuery = "Select (select AlphaCode from branchmaster where LPAD(BrnCode,2,'0') = aa.brn_code) as brncode,aa.brn_code,"
                    + " '2.Marketing Activity Done' as WorkType,  aa.entry_dt as follow_up_dt, aa.id, cc.assigned_user, "
                    + " aa.source_of_data,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_desc,"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_order,"
                    + " aa.source_of_marketing,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_desc, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_order, "
                    + " aa.source_of_data as purpose,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as purpose_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as purpose_order,"
                    + " aa.source_of_marketing as purpose_desc,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as purpose_desc_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as purpose_desc_order,"
                    + " cc.status,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_order, "
                    + " count(bb.id)  as noOfLead, '' as ref_acno, '' as entryType"
                    + " from cbs_lead_mgmt_id aa, cbs_lead_mgmt_details bb, cbs_lead_follow_ups cc "
                    + " where date_format(aa.entry_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and aa.id = bb.lead_id "
                    + " and bb.id = cc.lead_detail_id and cc.status = 'N'"
                    + " group by  cc.assigned_user,aa.follow_up_dt,aa.source_of_marketing";

            String followUpQuery = " select(select AlphaCode from branchmaster where LPAD(BrnCode,2,'0') = aa.brn_code) as brncode,aa.brn_code,"
                    + " '2.Marketing Activity Done' as WorkType, aa.follow_up_dt, aa.id, cc.assigned_user,"
                    + " aa.source_of_data,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_desc,"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_order,"
                    + " aa.source_of_marketing,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_desc, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_order, "
                    + " aa.source_of_data as purpose,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as purpose_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as purpose_order,"
                    + " aa.source_of_marketing as purpose_desc,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as purpose_desc_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as purpose_desc_order,"
                    + " cc.status,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_order, "
                    + " count(bb.id)   as noOfLead, '' as ref_acno, '' as entryType  "
                    + " from cbs_lead_mgmt_id aa, cbs_lead_mgmt_details bb, cbs_lead_follow_ups cc "
                    + " where date_format(aa.entry_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' "
                    + " and date_format(cc.follow_up_dt,'%Y%m%d') between '" + followFrmdt + "' and '" + followTodt + "' "
                    + " and aa.id = bb.lead_id and bb.id = cc.lead_detail_id and cc.status not in ('N','B')"
                    + " group by cc.assigned_user, cc.follow_up_dt, aa.source_of_marketing ";

            String secondQuery = "";
            if (reportType.equalsIgnoreCase("N")) { //New Entry
                secondQuery = newEntryQuery;
            } else {
                secondQuery = followUpQuery;
            }
            String query = "Select ss.*,concat(se.UserName) as userName from "
                    + "(select tt.brncode, tt.brn_code, tt.WorkType, tt.follow_up_dt, tt.id, tt.assigned_user, '' as source_of_data,  "
                    + "'' as source_of_data_desc, '0' as source_of_data_order, '' as source_of_marketing, '' as source_of_marketing_desc,  "
                    + "'0' as source_of_marketing_order, '' as purpose, '' as purpose_description, '0' as purpose_order,  "
                    + "if(tt.entryType = 'D', tt.souce_of_marketing, tt.purpose) as purpose_desc,  "
                    + "if(tt.entryType = 'D', tt.souce_of_marketing_desc, tt.purpose_description) as purpose_desc_description,  "
                    + "ifnull(if(tt.entryType = 'D', tt.souce_of_marketing_order, tt.purpose_order),0) as purpose_desc_order, "
                    + " '' as status, '' as status_description, '0' as status_order, tt.noOfLead, '' as ref_acno, tt.entryType  "
                    + "from  "
                    + "(Select (select AlphaCode from branchmaster where LPAD(BrnCode,2,'0') = aa.brn_code) as brncode, aa.brn_code,  "
                    + "'1.Task Given (Morning)' as WorkType,  aa.entry_dt as follow_up_dt,aa.id, aa.assigned_user,  "
                    + "aa.purpose as purpose,  "
                    + "(select ref_desc from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  aa.purpose) as purpose_description,  "
                    + "(select order_by from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  aa.purpose) as purpose_order,  "
                    + "aa.souce_of_marketing,  "
                    + "(select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.souce_of_marketing) as souce_of_marketing_desc,  "
                    + "(select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.souce_of_marketing) as souce_of_marketing_order,  "
                    + "sum(aa.target)  as noOfLead, '' as ref_acno, aa.entryType  "
                    + "from  "
                    + "cbs_lead_target aa  where date_format(aa.entry_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "'  "
                    + "and aa.entryType = '" + entryType + "'   "
                    + "group by  aa.brn_code, aa.assigned_user, if(aa.entryType = 'D', aa.souce_of_marketing,aa.purpose)) tt "
                    + " union all "
                    + secondQuery
                    + " union all"
                    + " select xx.* from "
                    + " (select  yy.brncode, yy.brn_code,"
                    + " '3.Marketing Conversion Done (Evening)' as WorkType ,yy.follow_up_dt,yy.id, yy.assigned_user, yy.source_of_data, "
                    + " yy.source_of_data_desc, yy.source_of_data_order ,"
                    + " yy.source_of_marketing, yy.source_of_marketing_desc, yy.source_of_marketing_order, "
                    + " ifnull(yy.purpose,zz.ref_code) as purpose, "
                    + " ifnull(yy.purpose_description, zz.ref_desc) as purpose_description, "
                    + " ifnull(yy.purpose_order,zz.order_by) as purpose_order,"
                    + " yy.purpose_desc, yy.purpose_desc_description, yy.purpose_desc_order, "
                    + " yy.status, yy.status_description, yy.status_order, ifnull(yy.noOfLead,0), yy.ref_acno, '' as entryType   from "
                    + " (select ref_code,ref_desc, order_by from cbs_ref_rec_type where ref_rec_no = 423 ) zz left join "
                    + " (select  (select AlphaCode from branchmaster where LPAD(BrnCode,2,'0') = aa.brn_code) as brncode, aa.brn_code,"
                    + " '3.Marketing Conversion Done (Evening)' as WorkType,aa.follow_up_dt ,aa.id, cc.assigned_user, "
                    + " aa.source_of_data,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_desc,"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_order,"
                    + " aa.source_of_marketing,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_desc, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_order,"
                    + " cc.purpose,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  cc.purpose) as purpose_description,"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  cc.purpose) as  purpose_order,"
                    + " cc.purpose_desc,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc) as purpose_desc_description,"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc) as  purpose_desc_order,"
                    + " cc.status,"
                    + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_description, "
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_order,"
                    + " count(bb.id)   as noOfLead, GROUP_CONCAT(cc.ref_acno  ORDER BY cc.ref_acno  SEPARATOR ', ' ) AS ref_acno"
                    + " from cbs_lead_mgmt_id aa, cbs_lead_mgmt_details bb, cbs_lead_follow_ups cc "
                    + " where /*date_format(aa.entry_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' "
                    + " and*/ date_format(cc.follow_up_dt,'%Y%m%d') between '" + followFrmdt + "' and '" + followTodt + "' "
                    + " and aa.id = bb.lead_id and bb.id = cc.lead_detail_id and cc.status = 'B'"
                    + " group by  cc.assigned_user,cc.follow_up_dt, cc.purpose, cc.purpose_desc) yy "
                    + " on zz.ref_code = yy.purpose) xx order by WorkType, follow_up_dt, assigned_user, purpose_order,"
                    + " purpose_desc_order, status_order, id) ss "
                    + " left join   \n"
                    + " securityinfo se on se.UserId = ss.assigned_user and ss.brn_code = se.brncode  " + brncode
                    + " order by se.brncode, ss.worktype, userName ";

//            if (branch.equalsIgnoreCase("0A")) {
//                query = query + " order by ss.brn_code ";
//            }
            System.out.println("query:" + query);
            List list = entityManager.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                throw new Exception("Data does not exists");
            }
            System.out.println("Size:" + list.size());
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                MarketingMisPojo pojo = new MarketingMisPojo();
                pojo.setBrncode(v.get(0) == null ? "ZZZZ" : v.get(0).toString());
                pojo.setWorkType(v.get(2) == null ? "" : v.get(2).toString());
                String workType = (v.get(2) == null ? "" : v.get(2).toString());
                pojo.setFollow_up_dt(v.get(3) == null ? dmy.format(ymd.parse(frmdt)) : dmy.format(ymd.parse(v.get(3).toString())));
                pojo.setId(v.get(4) == null ? (Long.valueOf("0")) : Long.parseLong(v.get(4).toString()));
                pojo.setAssigned_user(v.get(24) == null ? (misType.equalsIgnoreCase("U") ? "ZZZZ" : "") : v.get(24).toString());
                pojo.setSource_of_data(v.get(6) == null ? "" : v.get(6).toString());
                pojo.setSource_of_data_desc(v.get(7) == null ? "" : v.get(7).toString());
                pojo.setSource_of_data_order(v.get(8) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(8).toString())));
                pojo.setSource_of_marketing(v.get(9) == null ? "" : v.get(9).toString());
                pojo.setSource_of_marketing_desc(v.get(10) == null ? "" : v.get(10).toString());
                pojo.setSource_of_marketing_order(v.get(11) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(11).toString())));
                pojo.setPurpose(v.get(12) == null ? "" : v.get(12).toString());
                pojo.setPurpose_description(v.get(13) == null ? "" : v.get(13).toString());
                pojo.setPurpose_order(v.get(14) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(14).toString())));
                if (workType.equalsIgnoreCase("3.Marketing Conversion Done (Evening)") && (entryType.equalsIgnoreCase("D") || misType.equalsIgnoreCase("B") || misType.equalsIgnoreCase("U"))) {
                    pojo.setPurpose_desc(v.get(12) == null ? "" : v.get(12).toString());
                    pojo.setPurpose_desc_description(v.get(13) == null ? "" : v.get(13).toString());
                    pojo.setPurpose_desc_order(v.get(14) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(14).toString())));
                } else {
                    pojo.setPurpose_desc(v.get(15) == null ? "" : v.get(15).toString());
                    pojo.setPurpose_desc_description(v.get(16) == null ? "" : v.get(16).toString());
                    pojo.setPurpose_desc_order(v.get(17) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(17).toString())));
                }
                pojo.setStatus(v.get(18) == null ? "" : v.get(18).toString());
                pojo.setStatus_description(v.get(19) == null ? "" : v.get(19).toString());
                pojo.setStatus_order(v.get(20) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(20).toString())));
                pojo.setNoOfLead(v.get(21) == null ? Long.parseLong("0") : Long.parseLong(v.get(21).toString()));
                pojo.setRef_acno(v.get(22) == null ? "" : v.get(22).toString());
                pojo.setEntryType(v.get(23) == null ? "" : v.get(23).toString());
                list1.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return list1;
    }

    @Override
    public List<MarketingMisPojo> getStaffPerformanceMisReport(String branch, String frmdt, String todt, String entryType, String followFrmdt, String followTodt, String reportType, String misType) throws Exception {
        List<MarketingMisPojo> list1 = new ArrayList<>();
        try {
            String brncode = "";
            if (!branch.equalsIgnoreCase("0A")) {
                brncode = "  aa.brn_code= '" + branch + "' ";
            }
            if (reportType.equalsIgnoreCase("N")) { //New Entry
                followFrmdt = frmdt;
                followTodt = todt;
            }

            String query = " select aa.brncode, aa.brn_code, aa.follow_up_dt, ifnull(aa.assigned_user,'ZZZZ') as assigned_user, aa.source_of_marketing, aa.source_of_marketing_desc, aa.source_of_marketing_order, \n"
                    + " aa.acctNature, aa.acctType, sum(aa.noOfLead) as noOfLead, GROUP_CONCAT(aa.refAcNo  ORDER BY aa.refAcNo  SEPARATOR ', ' ) AS ref_acno, sum(amtDisburse) as amtDisburse from \n"
                    + " (select  (select AlphaCode from branchmaster where LPAD(BrnCode,2,'0') = aa.brn_code) as brncode, aa.brn_code, \n"
                    + " '3.Marketing Conversion Done (Evening)' as WorkType,aa.follow_up_dt ,aa.id, \n"
                    + " CONCAT(ss.UserName,' [',cc.assigned_user,']','[',aa.brn_code,']') as assigned_user,   \n"
                    + " aa.source_of_data, (select ref_desc from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_desc, \n"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 421 and ref_code =  aa.source_of_data) as source_of_data_order, \n"
                    + " aa.source_of_marketing, (select ref_desc from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_desc,  \n"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 422 and ref_code =  aa.source_of_marketing) as source_of_marketing_order, \n"
                    + " cc.purpose, (select ref_desc from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  cc.purpose) as purpose_description, (select order_by from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  cc.purpose) as  purpose_order, \n"
                    + " cc.purpose_desc, (select ref_desc from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc) as purpose_desc_description, (select order_by from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc) as  purpose_desc_order, \n"
                    + " cc.status, (select ref_desc from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_description,  \n"
                    + " (select order_by from cbs_ref_rec_type where ref_rec_no = 424 and ref_code =  cc.status) as status_order, \n"
                    + " cc.ref_acno, \n"
                    + " ifnull(atm.acctnature, if(cc.purpose = 'LO',cc.purpose,(select ref_desc from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc))) as acctNature, \n"
                    + " ifnull(atm.acctcode, if(cc.purpose = 'LO',cc.purpose,(select ref_desc from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  cc.purpose_desc))) as acctType1, \n"
                    + " ifnull(atm.AcctDesc, if(cc.purpose = 'LO',(select ref_desc from cbs_ref_rec_type where ref_rec_no = 423 and ref_code =  cc.purpose),(select ref_desc from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc))) as AcctDesc, \n"
                    + " ifnull(atm.AcctType, if(cc.purpose = 'LO',cc.purpose,(select ref_desc from cbs_ref_rec_type where ref_rec_no = 426 and ref_code =  cc.purpose_desc))) as acctType, \n"
                    + " count(Distinct cc.ref_acno)   as noOfLead, \n"
                    + " GROUP_CONCAT(Distinct cc.ref_acno  ORDER BY cc.ref_acno  SEPARATOR ', ' ) AS refAcNo,\n"
                    + " if((atm.acctnature = 'TL' OR atm.acctnature = 'DL'), sum(ld.AMTDISBURSED),0) as amtDisburse\n"
                    + " from cbs_lead_mgmt_id aa, cbs_lead_mgmt_details bb, cbs_lead_follow_ups cc \n"
                    + " left join \n"
                    + " securityinfo ss on ss.UserId = cc.assigned_user \n"
                    + " left join \n"
                    + " accounttypemaster atm on atm.acctcode = substring(cc.ref_acno,3,2) \n"
                    + " left join \n"
                    + " loandisbursement ld on ld.acno = ref_acno and atm.acctcode = substring(cc.ref_acno,3,2) \n"
                    + " where /*date_format(aa.entry_dt,'%Y%m%d') between '20190401' and '20191009'  and*/ "
                    + " date_format(cc.follow_up_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "'  \n"
                    + " and aa.id = bb.lead_id and bb.id = cc.lead_detail_id and cc.status = 'B'  \n" + brncode
                    + " group by  cc.assigned_user,cc.follow_up_dt, cc.purpose, cc.purpose_desc, cc.ref_acno\n"
                    + " ) aa group by brn_code, assigned_user, source_of_marketing_order, purpose_order, acctNature, acctType, aa.refAcNo \n"
                    + " order by brn_code, assigned_user, source_of_marketing_order, purpose_order, acctNature, acctType, aa.refAcNo ";

//            if (branch.equalsIgnoreCase("0A")) {
//                query = query + " order by ss.brn_code ";
//            }
//            System.out.println("query:" + query);
            List list = entityManager.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                throw new Exception("Data does not exists");
            }
            System.out.println("Size:" + list.size());
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                MarketingMisPojo pojo = new MarketingMisPojo();
                pojo.setBrncode(v.get(0) == null ? "ZZZZ" : v.get(0).toString());
                pojo.setFollow_up_dt(v.get(2) == null ? dmy.format(ymd.parse(frmdt)) : dmy.format(ymd.parse(v.get(2).toString())));
                pojo.setAssigned_user(v.get(3) == null ? (misType.equalsIgnoreCase("U") ? "ZZZZ" : "") : v.get(3).toString());
                pojo.setSource_of_marketing(v.get(4) == null ? "" : v.get(4).toString());
                pojo.setSource_of_marketing_desc(v.get(5) == null ? "" : v.get(5).toString());
                pojo.setSource_of_marketing_order(v.get(6) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(6).toString())));
                pojo.setAcctNature(v.get(7) == null ? "" : v.get(7).toString().equalsIgnoreCase("LO") ? "Locker" : v.get(7).toString());
                pojo.setAcctType(v.get(8) == null ? "" : v.get(8).toString().equalsIgnoreCase("LO") ? "Locker" : v.get(8).toString());
                pojo.setNoOfLead(v.get(9) == null ? Long.parseLong("0") : Long.parseLong(v.get(9).toString()));
                pojo.setRef_acno(v.get(10) == null ? "" : v.get(10).toString());
                pojo.setAmtDisburse(v.get(11) == null ? new BigDecimal("0") : new BigDecimal(v.get(11).toString()));
                list1.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return list1;
    }

    @Override
    public List<MarketingMisPojo> getMktDataAsPerRequirement(String branch, String frmdt, String todt, String entryType, String followFrmdt, String followTodt, String reportType) throws Exception {
        /*E = Entry; U= Update*/
        List<MarketingMisPojo> list1 = new ArrayList<>();
        try {
            String brncode = "", query = "";
            if (reportType.equalsIgnoreCase("N")) { //New Entry
                followFrmdt = frmdt;
                followTodt = todt;
            }
            if (entryType.equalsIgnoreCase("E")) {
                if (!branch.equalsIgnoreCase("0A")) {
                    brncode = " and mgmtId.brn_code= '" + branch + "' ";
                }
                query = "select '1.Entry' as point, aa.id, aa.maxId , aa.enter_by, aa.entry_dt, aa.source_of_data, aa.source_of_marketing, aa.cust_name, aa.cotact_no, aa.purpose, aa.cust_remark, "
                        + "aa.source_of_marketing as status, aa.follow_time, aa.branch, aa.status_order as source_of_marketing_order from  "
                        + "(select details.id, followup.id as maxId, (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and  brncode=mgmtId.brn_code and userid=followup.assigned_user) as enter_by,  "
                        + "date_format(mgmtId.entry_dt,'%d/%m/%Y') as entry_dt,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=mgmtId.source_of_data)  as source_of_data, "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and ref_code=mgmtId.source_of_marketing) as source_of_marketing , "
                        + "(select order_by from cbs_ref_rec_type where ref_rec_no ='422' and   ref_code=mgmtId.source_of_marketing) as status_order, "
                        + "details.cust_name,details.cotact_no,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followup.purpose) as purpose,  "
                        + "followup.cust_remark, "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and   ref_code=followup.status) as status , "
                        + "followup.follow_time ,   "
                        + "(select BranchName from branchmaster where brncode=mgmtId.brn_code ) as branch   "
                        + "from cbs_lead_mgmt_id mgmtId, cbs_lead_mgmt_details details,cbs_lead_follow_ups followup  "
                        + "where  mgmtId.id=details.lead_id and   details.id=followup.lead_detail_id and followup.status='N'   "
                        + "and  date_format(mgmtId.entry_dt,'%Y%m%d') between '" + frmdt + "'  and '" + todt + "' " + brncode
                        + " order by mgmtId.origin,  "
                        + "mgmtId.brn_code, mgmtId.source_of_data, mgmtId.source_of_marketing ) aa "
                        + "order by point, status_order,  source_of_data, source_of_marketing, id, maxId";
            } else if (entryType.equalsIgnoreCase("U")) {
                if (!branch.equalsIgnoreCase("0A")) {
                    brncode = " and lead.brn_code= '" + branch + "'";
                }
                query = "select '2.Update' as point, bb.id, bb.maxId,  bb.assigneduser as enter_by, bb.entry_dt, bb.source_of_data, "
                        + "bb.status_desc as source_of_marketing, bb.cust_name, bb.cotact_no, bb.purpose, bb.cust_remark, "
                        + "bb.source_of_marketing as status, bb.follow_time, bb.branch, bb.status_order as source_of_marketing_order from  "
                        + "(select detail.id, followUp.id  as id1, max(followUp.id) as maxId,  detail.acno, detail.cust_name,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=lead.source_of_data) as source_of_data,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and  ref_code=lead.source_of_marketing) as source_of_marketing ,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followUp.purpose) as purpose,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='426' and  ref_code=followUp.purpose_desc) as purposeDesc,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and  ref_code=followUp.status) as status_desc,  "
                        + "(select order_by from cbs_ref_rec_type where ref_rec_no ='424' and  ref_code=followUp.status) as status_order, "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='425' and  ref_code=followUp.enquiry_type) as enquirytype,  "
                        + "(SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode='" + branch + "' and userid=followUp.assigned_user) as assigneduser,  "
                        + "date_format(followUp.follow_up_dt,'%d/%m/%Y') as follow_up_dt, followUp.follow_time ,  "
                        + "date_format(lead.entry_dt,'%d/%m/%Y') as entry_dt,  "
                        + "date_format(followUp.entry_dt,'%d/%m/%Y') as follow_entry_dt, "
                        + "followUp.cust_remark,followUp.ref_acno , (select BranchName from branchmaster where brncode=lead.brn_code ) as branch,  "
                        + "followUp.status, detail.cotact_no from cbs_lead_follow_ups followUp,cbs_lead_mgmt_details detail,cbs_lead_mgmt_id  lead   "
                        + "where  detail.id=followUp.lead_detail_id and lead.id=detail.lead_id /*and followUp.status not in ('N') */ "
                        + "and DATE_FORMAT(lead.entry_dt ,'%Y%m%d')  between '" + frmdt + "'  and '" + todt + "'  "
                        + "and DATE_FORMAT(followUp.entry_dt ,'%Y%m%d')  between '" + frmdt + "'  and '" + todt + "' " + brncode + "   group by detail.id order by detail.id, status_order, lead.brn_code)aa  "
                        + "left join  "
                        + "(select detail.id, followUp.id as id1, followUp.id as maxId,  detail.acno, detail.cust_name,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='421' and ref_code=lead.source_of_data) as source_of_data,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and  ref_code=lead.source_of_marketing) as source_of_marketing ,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='423' and  ref_code=followUp.purpose) as purpose,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='426' and  ref_code=followUp.purpose_desc) as purposeDesc,  "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and  ref_code=followUp.status) as status_desc,  "
                        + "(select order_by from cbs_ref_rec_type where ref_rec_no ='424' and  ref_code=followUp.status) as status_order, "
                        + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='425' and  ref_code=followUp.enquiry_type) as enquirytype,  "
                        + "(SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode='90' and userid=followUp.assigned_user) as assigneduser,  "
                        + "date_format(followUp.follow_up_dt,'%d/%m/%Y') as follow_up_dt, followUp.follow_time ,  "
                        + "date_format(lead.entry_dt,'%d/%m/%Y') as entry_dt,  "
                        + "date_format(followUp.entry_dt,'%d/%m/%Y') as follow_entry_dt, "
                        + "followUp.cust_remark,followUp.ref_acno , (select BranchName from branchmaster where brncode=lead.brn_code ) as branch,  "
                        + "followUp.status, detail.cotact_no from cbs_lead_follow_ups followUp,cbs_lead_mgmt_details detail,cbs_lead_mgmt_id  lead   "
                        + "where  detail.id=followUp.lead_detail_id and lead.id=detail.lead_id /*and followUp.status not in ('N') */ "
                        + "and DATE_FORMAT(lead.entry_dt ,'%Y%m%d')  between '" + frmdt + "'  and '" + todt + "'  "
                        + "and DATE_FORMAT(followUp.entry_dt ,'%Y%m%d')  between '" + followFrmdt + "'  and '" + followTodt + "'" + brncode + "   order by detail.id, status_order, lead.brn_code) bb on aa.id = bb.id and aa.maxid = bb.maxId "
                        + "order by point,  source_of_marketing_order, source_of_data, source_of_marketing, id, maxId";
            }


            System.out.println("query:" + query);
            List list = entityManager.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                throw new Exception("Data does not exists");
            }

            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                MarketingMisPojo pojo = new MarketingMisPojo();
                pojo.setBrncode(v.get(13) == null ? "" : v.get(13).toString());
                pojo.setWorkType(v.get(0) == null ? "" : v.get(0).toString());
                pojo.setFollow_up_dt(v.get(4) == null ? dmy.format(ymd.parse(frmdt)) : v.get(4).toString());
                pojo.setId(v.get(1) == null ? (Long.valueOf("0")) : Long.parseLong(v.get(1).toString()));
                pojo.setAssigned_user(v.get(3) == null ? "" : v.get(3).toString());
                pojo.setSource_of_data(v.get(5) == null ? "" : v.get(5).toString());
                pojo.setSource_of_data_desc(v.get(5) == null ? "" : v.get(5).toString());
//                pojo.setSource_of_data_order(v.get(8) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(8).toString())));
                pojo.setSource_of_marketing(v.get(6) == null ? "" : v.get(6).toString());
                pojo.setSource_of_marketing_desc(v.get(6) == null ? "" : v.get(6).toString());
                pojo.setSource_of_marketing_order(v.get(14) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(14).toString())));
                pojo.setPurpose(v.get(9) == null ? "" : v.get(9).toString());
                pojo.setPurpose_description(v.get(9) == null ? "" : v.get(9).toString());
//                pojo.setPurpose_order(v.get(14) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(14).toString())));
//                pojo.setPurpose_desc(v.get(15) == null ? "" : v.get(15).toString());
//                pojo.setPurpose_desc_description(v.get(16) == null ? "" : v.get(16).toString());
//                pojo.setPurpose_desc_order(v.get(17) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(17).toString())));
                pojo.setStatus(v.get(11) == null ? "" : v.get(11).toString());
                pojo.setStatus_description(v.get(11) == null ? "" : v.get(11).toString());
//                pojo.setStatus_order(v.get(20) == null ? BigDecimal.valueOf(Long.parseLong("0")) : BigDecimal.valueOf(Long.parseLong(v.get(20).toString())));
//                pojo.setNoOfLead(v.get(21) == null ? Long.parseLong("0") : Long.parseLong(v.get(21).toString()));
//                pojo.setRef_acno(v.get(22) == null ? "" : v.get(22).toString());
                pojo.setCust_name(v.get(7) == null ? "" : v.get(7).toString());
                pojo.setCotact_no(v.get(8) == null ? "" : v.get(8).toString());
                pojo.setCust_remark(v.get(10) == null ? "" : v.get(10).toString());
                pojo.setFollow_time(v.get(12) == null ? "" : v.get(12).toString());

                list1.add(pojo);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return list1;
    }

    @Override
    public List getTargetCountbyPurpose(String branch, String frmdt, String todt) throws Exception {
        List list = new ArrayList();
        try {
            String brnCode = "";
            if (!(branch.equalsIgnoreCase("0A") || branch.equalsIgnoreCase("HO"))) {
                brnCode = "and brn_code='" + branch + "'";
            }
            String query = "select  zz.* , yy.* from"
                    + "(select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no = '423') zz left join "
                    + " (select  brn_code, purpose,sum(traget)as target from  cbs_lead_target where date_format(entry_dt,'%Y%m%d') "
                    + " between '" + frmdt + "' and '" + todt + "' " + brnCode + " group by purpose,brn_code) yy "
                    + " on zz.ref_code = yy.purpose  group by zz.ref_code,yy.brn_code,yy.purpose ";
            if (branch.equalsIgnoreCase("0A") || branch.equalsIgnoreCase("HO")) {
                query = query + "order by brn_code";
            }
            list = entityManager.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                throw new Exception("Data does not exists");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return list;
    }

    @Override
    public String getLevelIdOfBranch(String orgnCode, String userId) throws Exception {
        try {
            List list = entityManager.createNativeQuery("select  LevelId  from  securityinfo where  status ='A' and brncode='" + orgnCode + "' and userid='" + userId + "'").getResultList();
            Vector v = (Vector) list.get(0);
            return v.get(0).toString();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List getYearlyTargetHistoryDetails(String origncode) throws Exception {
        try {
            return entityManager.createNativeQuery("select assignedpurpose.purposee,assignedpurpose.brn_code,assignedpurpose.target,ifnull(achievedpurpose.totalachieved,0) from"
                    + " (select (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423'"
                    + " and  ref_code=purpose) as purposee,purpose,sum(target) as target,brn_code from cbs_lead_target "
                    + " where entryType='Y' and brn_code='" + origncode + "' group by brn_code,purpose) assignedpurpose left join  "
                    + " (select f.purpose,count(f.id)as totalachieved, leadid.brn_code as targetAchievedBranch  "
                    + "   from cbs_lead_follow_ups f, cbs_lead_mgmt_id leadid,"
                    + "   cbs_lead_mgmt_details detailid"
                    + "   where f.status='B' and (f.ref_acno!=''|| f.ref_acno!=null) and  leadid.brn_code='" + origncode + "' and leadid.id=detailid.lead_id "
                    + "   and detailid.id=f.lead_detail_id  group by f.purpose,leadid.brn_code) achievedpurpose on "
                    + "    assignedpurpose.purpose=achievedpurpose.purpose and assignedpurpose.brn_code=achievedpurpose.targetAchievedBranch"
                    + "    group by assignedpurpose.purpose,assignedpurpose.brn_code").getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List getCbsLeadMagmtDetails(String contactNo) throws Exception {
        try {
            return entityManager.createNativeQuery("select * from  cbs_lead_mgmt_details where cotact_no='" + contactNo + "'").getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<TaskLeadTargetPojo> getTargetReportData(String branch, String frmdt, String todt, String orignBrcode, String entryType) throws Exception {
        List<TaskLeadTargetPojo> pojoList = new ArrayList<>();
        try {
            String query = "", brncode = "";
            List list = new ArrayList();
            if (entryType.equalsIgnoreCase("Y")) {
                if (!branch.equalsIgnoreCase("0A")) {
                    brncode = " and t.brn_code= '" + branch + "'";
                }
                query = " select (select alphacode from branchmaster where brncode = t.brn_code) as branchAlphacode ,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423'"
                        + " and  ref_code=t.purpose) as purpose ,t.target,date_format(t.entry_dt,'%d/%m/%Y %I:%i:%S'),"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and"
                        + "  brncode=t.orgn_brncode and userid=t.enter_by) as enter_byy from cbs_lead_target t"
                        + " where t.entryType='" + entryType + "'" + brncode
                        + " and date_format(t.entry_dt,'%Y%m%d ') between '" + frmdt + "' and '" + todt + "' "
                        + " order by purpose";
                if (branch.equalsIgnoreCase("0A")) {
                    query = query + ",branchAlphacode";
                }
                list = entityManager.createNativeQuery(query).getResultList();
                if (list.isEmpty()) {
                    throw new Exception("Data does not exists");
                }
                int srno = 1;
                for (int i = 0; i < list.size(); i++) {
                    Vector v = (Vector) list.get(i);
                    TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                    pojo.setSrNo(srno++);
                    pojo.setBranch(v.get(0) == null ? "" : v.get(0).toString());
                    pojo.setPurpose(v.get(1) == null ? "" : v.get(1).toString());
                    pojo.setAssignedTarget(v.get(2) == null ? 0 : Integer.parseInt(v.get(2).toString()));
                    pojo.setEnterBy(v.get(4) == null ? "" : v.get(4).toString());
                    pojo.setEntryDate(v.get(3) == null ? "" : v.get(3).toString());
                    pojo.setSourceOfMarketing("");
                    pojo.setAssignedUser("");
                    pojoList.add(pojo);
                }
            }
            if (entryType.equalsIgnoreCase("M")) {
                if (!branch.equalsIgnoreCase("0A")) {
                    brncode = " and t.brn_code= '" + branch + "'";
                }
                query = " select (select alphacode from branchmaster where brncode = t.brn_code) as branchAlphacode ,"
                        + " (select ref_desc from cbs_ref_rec_type  where ref_rec_no='423'"
                        + " and  ref_code=t.purpose) as purpose ,t.target,date_format(t.entry_dt,'%d/%m/%Y %I:%i:%S'),"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and"
                        + "  brncode=t.brn_code and userid=t.enter_by) as enter_byy from cbs_lead_target t"
                        + " where t.entryType='" + entryType + "'" + brncode
                        + " and date_format(t.entry_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' "
                        + " order by purpose";
                if (branch.equalsIgnoreCase("0A")) {
                    query = query + ",branchAlphacode";
                }
                list = entityManager.createNativeQuery(query).getResultList();
                if (list.isEmpty()) {
                    throw new Exception("Data does not exists");
                }
                int srno = 1;
                for (int i = 0; i < list.size(); i++) {
                    Vector v = (Vector) list.get(i);
                    TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                    pojo.setSrNo(srno++);
                    pojo.setBranch(v.get(0) == null ? "" : v.get(0).toString());
                    pojo.setPurpose(v.get(1) == null ? "" : v.get(1).toString());
                    pojo.setAssignedTarget(v.get(2) == null ? 0 : Integer.parseInt(v.get(2).toString()));
                    pojo.setEnterBy(v.get(4) == null ? "" : v.get(4).toString());
                    pojo.setEntryDate(v.get(3) == null ? "" : v.get(3).toString());
                    pojo.setSourceOfMarketing("");
                    pojo.setAssignedUser("");
                    pojoList.add(pojo);
                }
            }
            if (entryType.equalsIgnoreCase("D")) {
                if (!branch.equalsIgnoreCase("0A")) {
                    brncode = " where securityInfo.brncode = '" + branch + "' ";
                }
                query = "select securityInfo.brncode, (select alphacode from branchmaster where brncode = securityInfo.brncode) as branchAl,securityInfo.uservalue, ifnull(dailytarget.assigneduser,''),"
                        + " ifnull(dailytarget.souce_of_marketing,''),"
                        + " ifnull(dailytarget.target,0),ifnull(dailytarget.enter_byy,'') ,ifnull(dailytarget.entrydate,'') from "
                        + " (select concat(username,'[',userid,']') as uservalue, userid, brncode from  securityinfo  where status='A') securityInfo "
                        + " left join "
                        + " (select (select alphacode from branchmaster where brncode = t.brn_code) as branchAlphacode ,t.brn_code,"
                        + "  (select ref_desc from cbs_ref_rec_type  where ref_rec_no='422' and  ref_code=t.souce_of_marketing) as souce_of_marketing ,t.target,assigned_user, "
                        + " date_format(t.entry_dt,'%d/%m/%Y %I:%i:%S') as entrydate,"
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode=t.brn_code and userid=t.enter_by) as enter_byy,t.enter_by, "
                        + " (SELECT concat(username,'[',userid,']') FROM securityinfo where status='A' and brncode=t.brn_code and userid= t.assigned_user) as assigneduser "
                        + " from cbs_lead_target t where t.entryType='D'  and "
                        + " date_format(t.entry_dt,'%Y%m%d') between '" + frmdt + "' and '" + todt + "'"
                        + " order by branchAlphacode,souce_of_marketing) dailytarget"
                        + " on  dailytarget.assigned_user=securityInfo.userid and securityInfo.brncode = dailytarget.brn_code"
                        + brncode
                        + " order by branchAl, uservalue";

//                System.out.println("Queruy:"+query);
                List list1 = entityManager.createNativeQuery(query).getResultList();
                if (list1.isEmpty()) {
                    throw new Exception("Data does not exists");
                }
                int srno = 1;
                for (int i = 0; i < list1.size(); i++) {
                    Vector v = (Vector) list1.get(i);
                    TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                    pojo.setSrNo(srno++);
                    pojo.setBranch(v.get(1) == null ? "" : v.get(1).toString());
                    pojo.setBranchUserName(v.get(2) == null ? "" : v.get(2).toString());
                    pojo.setSourceOfMarketing(v.get(4) == null ? "" : v.get(4).toString());
                    pojo.setAssignedTarget(v.get(5) == null ? 0 : Integer.parseInt(v.get(5).toString()));
                    pojo.setEnterBy(v.get(6) == null ? "" : v.get(6).toString());
                    pojo.setEntryDate(v.get(7) == null ? "" : v.get(7).toString());
                    pojo.setAssignedUser(v.get(3) == null ? "" : v.get(3).toString());
                    pojo.setPurpose("1");
                    pojoList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return pojoList;
    }

    @Override
    public String updateTaskLeadTarget(TaskLeadTargetPojo pojo, int target) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = entityManager.createNativeQuery("update cbs_lead_target set target='" + target + "' where "
                    + "id ='" + pojo.getId() + "' and date_format(entry_dt,'%Y%m%d')=" + ymd.format(new Date()) + "").executeUpdate();
            if (n < 0) {
                return " Problem in cbs_lead_target Updation";
            }
            ut.commit();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "true";
    }

    @Override
    public String updateFollowUpDateByBranchManager(String followUpId, String detailId, String followUpDate, String followUptime, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String query = "update cbs_lead_follow_ups set follow_up_dt=" + ymd.format(dmy.parse(followUpDate)) + "  , follow_time ='" + followUptime + "' , enter_by='" + userName + "', entry_dt=now() where "
                    + " id ='" + Long.parseLong(followUpId) + "' and lead_detail_id= '" + Long.parseLong(detailId) + "'";
            int n = entityManager.createNativeQuery(query).executeUpdate();
            if (n < 0) {
                return "Problem in cbs_lead_follow_ups updation";
            }
            ut.commit();
        } catch (Exception e) {
            ut.rollback();
            throw new Exception(e.getMessage());
        }
        return "true";
    }

    @Override
    public List getTotalTargetForTaskUpdation(String frmdt, String todt, String userToAssigned, String branch) throws Exception {
        try {
            String query = "select aa.assigned_user, concat(bb.username,'[',bb.userid,']') as username, "
                    + "(select AlphaCode from branchmaster where LPAD(BrnCode,2,'0') = aa.brn_code) as brncode,aa.brn_code,"
                    + " sum(IF(aa.status='N',1,0)) AS 'NEW',  sum(IF(aa.status='F',1,0)) AS 'FOLLOW UP',"
                    + "sum(IF(aa.status='B',1,0)) AS 'BOOKED/ SUCCESSFUL', sum(IF(aa.status='R',1,0)) AS 'REJECTED/ DUMP',"
                    + "sum(IF(aa.status='T',1,0)) AS 'TRANSFER',"
                    + "(sum(IF(aa.status='N',1,0))+sum(IF(aa.status='F',1,0))) AS 'PENDING' from "
                    + "(select a.assigned_user, detail.id, detail.lead_id, detail.acno, detail.cust_name,"
                    + "ifnull(detail.father_name,''),ifnull(detail.address,''),ifnull(detail.city,''),ifnull(detail.email_id,''),"
                    + "ifnull(detail.cotact_no,''),ifnull(detail.alternate_contact_no,''),"
                    + "(select ref_desc from cbs_ref_rec_type  where ref_rec_no='424' and ref_code=a.status) as status_desc, a.status ,"
                    + " date_format(a.follow_up_dt,'%d/%m/%Y'),date_format(a.follow_time,'%H:%i:%s'),"
                    + "date_format(a.entry_dt,'%d/%m/%Y %H:%i:%s'), lead.orgn_brncode as brn_code from cbs_lead_follow_ups a,"
                    + "(select max(id) as id,lead_detail_id  from cbs_lead_follow_ups "
                    + "where date_format(follow_up_dt,'%Y%m%d') between '" + ymd.format(dmy.parse(frmdt)) + "' "
                    + "and '" + ymd.format(dmy.parse(todt)) + "' group by lead_detail_id) b ,"
                    + " cbs_lead_mgmt_details detail ,cbs_lead_mgmt_id  lead where a.lead_detail_id = b.lead_detail_id "
                    + " and a.id = b.id and detail.id=a.lead_detail_id "
                    + " and lead.id=detail.lead_id  and "
                    + " date_format(a.follow_up_dt,'%Y%m%d') between '" + ymd.format(dmy.parse(frmdt)) + "' "
                    + "and '" + ymd.format(dmy.parse(todt)) + "' and a.assigned_user='" + userToAssigned + "' "
                    + " and lead.brn_code='" + branch + "')aa"
                    + " left join securityinfo bb on aa.assigned_user = bb.userid group by aa.assigned_user;";
            return entityManager.createNativeQuery(query).getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
