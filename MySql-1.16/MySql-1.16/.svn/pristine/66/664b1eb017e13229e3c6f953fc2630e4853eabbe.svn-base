/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.email;

import com.cbs.dto.CustomerDetail;
import com.cbs.utils.CbsUtil;
import java.util.Calendar;
import java.util.Date;
import com.cbs.dto.report.AccontDetailList;
import com.cbs.dto.report.CustIdWiseAcStmt;
import com.cbs.dto.report.MailingDetailsPojo;
import com.cbs.dto.report.ServiceDetailPojo;
import com.cbs.email.service.EmailConstant;
import com.cbs.email.service.EmailType;
import com.cbs.email.service.MailCustomerInfo;
import com.cbs.email.service.MailRequestTO;
import com.cbs.email.service.MailService;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Stateless(mappedName = "EmailMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class EmailMgmtFacade implements EmailMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private MiscMgmtFacadeS1Remote miscReportFacadeS1;
    @EJB
    private MiscReportFacadeRemote miscReportFacade;
    @Resource(lookup = "mail/MailSession")
    private Session mailSession;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void emailProcess() {
        System.out.println("In emailProcess method()");
        try {
            /**
             * It will generate the account statement for customers having
             * frequency on current date and send email to customers.
             */
            sendStatementEmail();
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("Problem in emailProcess() method......" + ex.getMessage());
        }
    }

    private void sendStatementEmail() throws Exception {
        try {
            List<MailCustomerInfo> mailCustomers = allCustomersToMail();
            if (mailCustomers.isEmpty()) {
                throw new Exception("There is no customer to send account statement.");
            }
            //Generate account statement
            for (MailCustomerInfo mail : mailCustomers) {
                System.out.println("Customer Id-->" + mail.getCustomerId() + ":Customer Email-->" + mail.getEmail()
                        + ":Statement Period-->" + mail.getStatementPeriod() + ":Customer Name-->" + mail.getCustName()
                        + ":Period From Date-->" + mail.getPeriodFrDt() + ":Period To Date-->" + mail.getPeriodToDt() + "\n");

                downloadPdfCustId(mail.getCustomerId(), mail.getPeriodFrDt(), mail.getFrequency().equalsIgnoreCase("D")
                        ? mail.getPeriodFrDt() : mail.getPeriodToDt(), mail.getCustomerDob());
            }
            //Sending email to customers
            for (MailCustomerInfo mail : mailCustomers) {
                MailRequestTO mailRequestTO = new MailRequestTO();
                mailRequestTO.setServiceType(mail.getServiceType());
                mailRequestTO.setCustId(mail.getCustomerId());
                mailRequestTO.setCustName(mail.getCustName());
                mailRequestTO.setCustEmail(mail.getEmail());
                mailRequestTO.setStatementDuration(mail.getStatementPeriod());
                mailRequestTO.setPeriodFromDt(mail.getPeriodFrDt());
                mailRequestTO.setStmtDueDt(mail.getStmtDueDt());
                mailRequestTO.setPeriodToDt(mail.getFrequency().equalsIgnoreCase("D") ? mail.getPeriodFrDt() : mail.getPeriodToDt());
                mailRequestTO.setSubject("Your " + mailSession.getProperty("mail.from.name") + " e-statement for "
                        + mail.getStatementPeriod() + " for Customer id xxxxxxxxx" + mail.getCustomerId().substring(mail.getCustomerId().length() - 1));
                mailRequestTO.setBankName(mailSession.getProperty("mail.from.name"));
                mailRequestTO.setBankEmail(mailSession.getProperty("mail.from"));
                mailRequestTO.setBankPhone("");
                mailRequestTO.setDobText(mail.getCustEntityType().equals("01") ? "your Date of Birth (DOB)" : "Date of Incorporation of the Company");

                new MailService().postMail(EmailType.SEND_ACCT_STMAT, mailRequestTO);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<MailCustomerInfo> allCustomersToMail() throws Exception {
        List<MailCustomerInfo> mailCustomers = new ArrayList<>();
        try {
            List emailDataList = em.createNativeQuery("select s.customer_id,s.email,s.frequency,"
                    + "date_format(s.start_date,'%Y%m%d'),s.start_flag,c.custfullname,"
                    + "date_format(c.dateofbirth,'%d%m%Y'),c.custentitytype from cbs_service_detail s,"
                    + "cbs_customer_master_detail c where s.service_type='S' and "
                    + "s.customer_id=c.customerid group by s.customer_id").getResultList();
            if (emailDataList.isEmpty()) {
                throw new Exception("There is no data to send statement email.");
            }
            for (int i = 0; i < emailDataList.size(); i++) {
                Vector ele = (Vector) emailDataList.get(i);
                String frequecny = ele.get(2).toString();
                String startDate = ele.get(3).toString();

                List list = em.createNativeQuery("select ifnull(max(date_format(stmt_due_dt,'%Y%m%d')),'') as "
                        + "max_date from cbs_email where customer_id='" + ele.get(0).toString() + "'").getResultList(); //Check empty case
                Vector dtVec = (Vector) list.get(0);
                String maxDt = dtVec.get(0).toString().trim();

                String nextDateToMail = "";
                if (maxDt.equals("")) {
                    nextDateToMail = getNextDateToMail(startDate, frequecny, ele.get(4).toString().trim());
                } else {
                    nextDateToMail = getNextDateToMail(maxDt, frequecny, "");
                }
                System.out.println("nextDateToMail-->" + nextDateToMail);

                list = em.createNativeQuery("select customer_id from cbs_email where customer_id='" + ele.get(0).toString() + "' and "
                        + "stmt_due_dt='" + nextDateToMail + "'").getResultList();

//                if (list.isEmpty() && ((ymd.parse(ymd.format(new Date()))).compareTo(ymd.parse(nextDateToMail))) >= 0) {
                if (list.isEmpty() && ((ymd.parse(ymd.format(new Date()))).compareTo(ymd.parse(nextDateToMail))) > 0) {
                    MailCustomerInfo mailCustomer = new MailCustomerInfo();
                    mailCustomer.setServiceType("S");
                    mailCustomer.setCustomerId(ele.get(0).toString());
                    mailCustomer.setEmail(ele.get(1).toString());
                    mailCustomer.setStatementPeriod(customerStatementPeriod(nextDateToMail, frequecny));
                    mailCustomer.setCustName(ele.get(5).toString());
                    mailCustomer.setCustomerDob(ele.get(6).toString());
                    mailCustomer.setCustEntityType(ele.get(7).toString());
                    String[] arr = getStmtPeriodDates(mailCustomer.getStatementPeriod().trim());
                    mailCustomer.setPeriodFrDt(arr[0]);
                    mailCustomer.setPeriodToDt(arr[1]);
                    mailCustomer.setFrequency(frequecny);
                    mailCustomer.setStmtDueDt(nextDateToMail);
                    mailCustomers.add(mailCustomer);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return mailCustomers;
    }

    private String[] getStmtPeriodDates(String statementPeriod) throws Exception {
        String[] arr = new String[2];
        String frDt = "", toDt = "";
        try {
            if (statementPeriod.toUpperCase().contains("TO")) {
                frDt = ymd.format(dmy.parse(statementPeriod.toUpperCase().split("TO")[0].trim()));
                toDt = ymd.format(dmy.parse(statementPeriod.toUpperCase().split("TO")[1].trim()));
            } else {
                frDt = ymd.format(dmy.parse(statementPeriod.trim()));
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        arr[0] = frDt;
        arr[1] = toDt;
        return arr;
    }

    private String getNextDateToMail(String prevGenDt, String frequency, String startFlag) throws Exception {
        String nextDtToSend = "";
        try {
            if (startFlag.equalsIgnoreCase("S")) {
                nextDtToSend = CbsUtil.dateAdd(prevGenDt, 0);
            } else {
                if (frequency.equalsIgnoreCase("Y")) {
                    nextDtToSend = CbsUtil.yearAdd(prevGenDt, 1);
                } else if (frequency.equalsIgnoreCase("H")) {
                    nextDtToSend = CbsUtil.monthAdd(prevGenDt, 6);
                } else if (frequency.equalsIgnoreCase("Q")) {
                    nextDtToSend = CbsUtil.monthAdd(prevGenDt, 3);
                } else if (frequency.equalsIgnoreCase("M")) {
                    nextDtToSend = CbsUtil.monthAdd(prevGenDt, 1);
                } else if (frequency.equalsIgnoreCase("W")) {
                    nextDtToSend = CbsUtil.dateAdd(prevGenDt, 6);
                } else if (frequency.equalsIgnoreCase("D")) {
                    nextDtToSend = CbsUtil.dateAdd(prevGenDt, 1);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return nextDtToSend;
    }

    private String customerStatementPeriod(String statementGenDt, String frequecny) throws Exception {
        String settlementPeriod = "";
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(ymd.parse(statementGenDt));
            if (frequecny.equalsIgnoreCase("Y")) {
                settlementPeriod = dmy.format(ymd.parse(CbsUtil.yearAdd(statementGenDt, -1)))
                        + " To " + dmy.format(ymd.parse(statementGenDt));
            } else if (frequecny.equalsIgnoreCase("H")) {
                settlementPeriod = dmy.format(ymd.parse(CbsUtil.monthAdd(statementGenDt, -6)))
                        + " To " + dmy.format(ymd.parse(statementGenDt));
            } else if (frequecny.equalsIgnoreCase("Q")) {
                settlementPeriod = dmy.format(ymd.parse(CbsUtil.monthAdd(statementGenDt, -3)))
                        + " To " + dmy.format(ymd.parse(statementGenDt));
            } else if (frequecny.equalsIgnoreCase("M")) {
                settlementPeriod = dmy.format(ymd.parse(CbsUtil.monthAdd(statementGenDt, -1)))
                        + " To " + dmy.format(ymd.parse(statementGenDt));
            } else if (frequecny.equalsIgnoreCase("W")) {
                settlementPeriod = dmy.format(ymd.parse(CbsUtil.dateAdd(statementGenDt, -6)))
                        + " To " + dmy.format(ymd.parse(statementGenDt));
            } else if (frequecny.equalsIgnoreCase("D")) {
                settlementPeriod = dmy.format(ymd.parse(statementGenDt));
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return settlementPeriod;
    }

    public void downloadPdfCustId(String custId, String fromDt, String toDt, String dob) throws Exception {
        try {
            List<CustIdWiseAcStmt> accountStatementReport = miscReportFacade.getCustIdAccountStatementReport(custId, fromDt, toDt);
//            if (accountStatementReport == null || accountStatementReport.isEmpty()) {
//                throw new Exception("There is no data to send e-mail for customer-->" + custId);
//            }
            JRBeanCollectionDataSource jrxmlds = new JRBeanCollectionDataSource(accountStatementReport);
            HashMap hm = new HashMap();
            hm.put("image", "/opt/images/logo-with-name.png");
            hm.put("pdFromDt", dmy.format(ymd.parse(fromDt)));
            hm.put("pdToDt", dmy.format(ymd.parse(toDt)));

            InputStream is = this.getClass().getResourceAsStream(EmailConstant.EMAIL_TEMPLATES_PATH + "custIdWiseAcStmt.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(is);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, jrxmlds);
            JRExporter exporter = new JRPdfExporter();

            String attachmentFileName = "STMT_" + "XXXXXXXXX" + custId.substring(custId.length() - 1)
                    + "_" + fromDt + "_" + toDt;

            String initialPdfName = EmailConstant.STMT_LOCATION + attachmentFileName + "_INI.pdf";
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, initialPdfName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.exportReport();
            System.out.println("Pdf File Created:Customer-->" + custId + "::File Name-->" + initialPdfName);
            //Making pdf password protected
            String encryptedPdfName = EmailConstant.STMT_LOCATION + attachmentFileName + ".pdf";
            encryptPdf(initialPdfName, encryptedPdfName, dob.getBytes(), dob.getBytes());
            System.out.println("Encrypted Pdf has been created:Customer-->" + custId + "::File Name-->" + encryptedPdfName);
            //Removing the initialPdfFile
            File custStmtFile = new File(initialPdfName);
            if (custStmtFile.exists()) {
                custStmtFile.delete();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void encryptPdf(String initialPdfName, String encryptedPdfName, byte[] userPassword,
            byte[] ownerPassword) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(initialPdfName);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(encryptedPdfName));
        stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_PRINTING,
                PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
        stamper.close();
        reader.close();
    }

    @Override
    public MailCustomerInfo getAccForEnableServices(String function, String serviceType, String custId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        MailCustomerInfo pojo = new MailCustomerInfo();
        List<AccontDetailList> accList = new ArrayList<>();
        try {

            List list = em.createNativeQuery("select DateOfBirth from cbs_customer_master_detail where customerid='" + custId + "' and "
                    + " not ((DateOfBirth IS NULL)or(date(DateOfBirth)='1900-01-01')) and FLOOR(DATEDIFF(now(),date(DateOfBirth))/365)<=150").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Improper Date of Birth of Corresponding Customer Id ");
            }
            if (function.equalsIgnoreCase("ADD")) {
                List custIdList = em.createNativeQuery("SELECT distinct customer_id FROM cbs_service_detail WHERE customer_id='" + custId + "' "
                        + " and service_type='" + serviceType + "'").getResultList();
                if (!custIdList.isEmpty()) {
                    throw new Exception("Service on this customer id already enabled.");
                }

                List aList = em.createNativeQuery("select ACNo,custname from ("
                        + " select ACNo,custname from accountmaster where AccStatus<>'9'"
                        + " union all"
                        + " select ACNo,custname from td_accountmaster where AccStatus<>'9') as t"
                        + " where ACNo in(select  Acno from customerid where custId='" + custId + "')").getResultList();
                if (aList.isEmpty()) {
                    throw new Exception("There is no Any Active Account No. ");
                } else {
                    for (int i = 0; i < aList.size(); i++) {
                        AccontDetailList accListArr = new AccontDetailList();
                        accListArr.setAcno(((Vector) aList.get(i)).get(0).toString());
                        accListArr.setCustName(((Vector) aList.get(i)).get(1).toString());
                        accListArr.setAccType(commonReport.getAcNatureByAcType(commonReport.getAcTypeByAcNo(accListArr.getAcno().trim())));
                        accListArr.setCheckBox(true);
                        accList.add(accListArr);
                    }
                    pojo.setAccList(accList);
                }
            } else if (function.equalsIgnoreCase("MODIFY")) {

                List modAccList = em.createNativeQuery("select ACNo,custname from ("
                        + " select ACNo,custname from accountmaster where AccStatus<>'9'"
                        + " union all"
                        + " select ACNo,custname from td_accountmaster where AccStatus<>'9') as t"
                        + " where ACNo in(select  Acno from customerid where custId='" + custId + "')").getResultList();
                if (modAccList.isEmpty()) {
                    throw new Exception("There is no Any Active Account of this customer id. ");
                } else {
                    for (int i = 0; i < modAccList.size(); i++) {
                        AccontDetailList accListArr = new AccontDetailList();
                        accListArr.setAcno(((Vector) modAccList.get(i)).get(0).toString());
                        accListArr.setCustName(((Vector) modAccList.get(i)).get(1).toString());
                        accListArr.setAccType(commonReport.getAcNatureByAcType(commonReport.getAcTypeByAcNo(accListArr.getAcno().trim())));
                        accListArr.setCheckBox(true);
                        accList.add(accListArr);
                    }
                    List custIdData = em.createNativeQuery("SELECT distinct service_type, customer_id,email, frequency, date_format(start_date,'%d/%m/%Y'), start_flag "
                            + " FROM cbs_service_detail WHERE customer_id='" + custId + "' and service_type='" + serviceType + "' and verify = 'Y'").getResultList();
                    if (custIdData.isEmpty()) {
                        throw new Exception("There is no Any customer id to modify.");
                    } else {
                        pojo.setServiceType(((Vector) custIdData.get(0)).get(0).toString());
                        pojo.setCustomerId(((Vector) custIdData.get(0)).get(1).toString());
                        pojo.setEmail(((Vector) custIdData.get(0)).get(2).toString());
                        pojo.setFrequency(((Vector) custIdData.get(0)).get(3).toString());
                        pojo.setStartDate(((Vector) custIdData.get(0)).get(4).toString());
                        pojo.setStartFlag(((Vector) custIdData.get(0)).get(5).toString());
                        pojo.setAccList(accList);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    @Override
    public List gridDetailForEnableService(String Date) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select distinct acno,customer_id  from cbs_service_detail  "
                    + " where verify = 'N' and verify_by='' and   cast(last_update_date as date)='" + Date + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }

    @Override
    public List gridDetailForVerifyEnableService(String function, String acNo, String custId) throws ApplicationException {
        List detailList = new ArrayList();
        try {
            if (function.equalsIgnoreCase("VERIFY")) {
                detailList = em.createNativeQuery("SELECT distinct service_type,acno,customer_id,email, frequency, "
                        + " date_format(start_date,'%d/%m/%Y'), start_flag  FROM cbs_service_detail WHERE  "
                        + " acno='" + acNo + "' and customer_id='" + custId + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return detailList;
    }

    @Override
    public String enableModifyAccountServeces(MailCustomerInfo data, String function, String enterBy, String currentDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            List<AccontDetailList> accChkdList = new ArrayList<>();
            for (AccontDetailList accData : data.getAccList()) {
                if (accData.isCheckBox()) {
                    accChkdList.add(accData);
                }
            }
            if (accChkdList.isEmpty()) {
                throw new ApplicationException("No Account Selected to Enable service.");
            }
            ut.begin();
            if (function.equalsIgnoreCase("ADD")) {
                for (AccontDetailList accData : accChkdList) {
                    int n = em.createNativeQuery("INSERT INTO cbs_service_detail (service_type, customer_id, acno, email, "
                            + " frequency, start_date, start_flag, enter_by, enter_time, last_update_by,last_update_date,verify,verify_by) "
                            + " VALUES ('" + data.getServiceType() + "', '" + data.getCustomerId() + "', '" + accData.getAcno() + "', "
                            + "'" + data.getEmail() + "', '" + data.getFrequency() + "', '" + data.getStartDate() + "', '" + data.getStartFlag() + "', "
                            + "'" + enterBy + "', now(),'" + enterBy + "',current_timestamp,'N','')").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in entry save");
                    }
                }
            } else if (function.equalsIgnoreCase("MODIFY")) {
                int n = em.createNativeQuery("INSERT INTO cbs_service_detail_his (service_type, customer_id, acno, email, frequency, start_date,"
                        + " start_flag, enter_by, enter_time,last_update_by,last_update_date,update_by,update_time,verify,verify_by,verify_date) "
                        + " SELECT service_type, customer_id, acno, email, frequency, start_date, start_flag, enter_by, enter_time,'" + enterBy + "' as update_by ,now() as update_time,"
                        + " last_update_by,last_update_date, verify,verify_by,verify_date "
                        + " FROM cbs_service_detail WHERE customer_id='" + data.getCustomerId() + "' and service_type='" + data.getServiceType() + "'; ").executeUpdate();
                n = em.createNativeQuery("DELETE FROM cbs_service_detail WHERE customer_id='" + data.getCustomerId() + "' and service_type='" + data.getServiceType() + "';").executeUpdate();
                for (AccontDetailList accData : accChkdList) {
                    n = em.createNativeQuery("INSERT INTO cbs_service_detail (service_type, customer_id, acno, email, "
                            + " frequency, start_date, start_flag, enter_by, enter_time,last_update_by,last_update_date,verify,verify_by) "
                            + " VALUES ('" + data.getServiceType() + "', '" + data.getCustomerId() + "', '" + accData.getAcno() + "', "
                            + "'" + data.getEmail() + "', '" + data.getFrequency() + "', '" + data.getStartDate() + "', '" + data.getStartFlag() + "', "
                            + "'" + enterBy + "', now(),'" + enterBy + "',now(),'N','')").executeUpdate();
                }
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public String verifyEnableServicesDetail(AccontDetailList obj, String user, String Date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List exitList = em.createNativeQuery("select last_update_by from cbs_service_detail where acno='" + obj.getAcno() + "' and customer_id = '" + obj.getCustId() + "'").getResultList();
            Vector ele = (Vector) exitList.get(0);
            String lastUser = ele.get(0).toString();
            if (lastUser.equals(user)) {
                ut.rollback();
                return "You can not verify your own entry.";
            } else {
                int n = em.createNativeQuery("Update cbs_service_detail set verify = 'Y',verify_by='" + user + "',verify_date=now(),last_update_by='" + user + "' , "
                        + "last_update_date=now() where acno = '" + obj.getAcno() + "' and customer_id = '" + obj.getCustId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In enable service Updation.");
                }
                ut.commit();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public List<ServiceDetailPojo> getServiceRegistrationData(String brnCode, String frdt, String todt,
            String serviceType, String mode) throws ApplicationException {
        List<ServiceDetailPojo> repList = new ArrayList<>();
        List sertempList = null;
        try {
            if (brnCode.equalsIgnoreCase("A")) {
                sertempList = em.createNativeQuery("select service_type,customer_id,acno,email,frequency,last_update_by,"
                        + "last_update_date,verify,verify_by,start_flag from cbs_service_detail where  cast(last_update_date as date)  between '" + frdt + "' and '" + todt + "'  "
                        + " and  service_type = '" + serviceType + "' ").getResultList();
            } else {
                sertempList = em.createNativeQuery("select service_type,customer_id,acno,email,frequency,last_update_by,"
                        + "last_update_date,verify,verify_by,start_flag from cbs_service_detail where  cast(last_update_date as date)  between '" + frdt + "' and '" + todt + "'  "
                        + " and substring(acno,1,2)='" + brnCode + "' and  service_type = '" + serviceType + "' ").getResultList();
            }
            int n = 1;
            if (!sertempList.isEmpty()) {
                for (int i = 0; i < sertempList.size(); i++) {
                    ServiceDetailPojo pojo = new ServiceDetailPojo();
                    Vector ele = (Vector) sertempList.get(i);
                    int srno = n++;
                    String servicetype = ele.get(0).toString().trim();
                    List Servicename = commonReport.getfrequencydescription("371", servicetype);
                    String ServiceDes = Servicename.get(0).toString().trim();
                    String custid = ele.get(1).toString().trim();
                    String acno = ele.get(2).toString().trim();
                    String email = ele.get(3).toString().trim();
                    String freq = ele.get(4).toString().trim();
                    List freDescription = commonReport.getfrequencydescription("370", freq);
                    String freDes = freDescription.get(0).toString();
                    String lastUpdtBy = ele.get(5).toString().trim();
                    String lastUpdtDt = ele.get(6).toString().trim();
                    String verify = ele.get(7).toString().trim();
                    String verifyDes = "";
                    if (verify.equalsIgnoreCase("Y")) {
                        verifyDes = "verify";
                    } else {
                        verifyDes = "UnVerify";
                    }
                    CustomerDetail customerObj = miscReportFacadeS1.getCustomerDetailByAccountNo(acno);
                    String custName = customerObj.getCustomerName();
                    String verifyBy = ele.get(8).toString().trim();
                    String start = ele.get(9).toString().trim();
                    List startDescription = commonReport.getfrequencydescription("372", start);
                    String StartDes = startDescription.get(0).toString();

                    pojo.setServiceType(ServiceDes);
                    pojo.setCustid(custid);
                    pojo.setAcNo(acno);
                    pojo.setFrequency(freDes);
                    pojo.setStartFlag(StartDes);
                    pojo.setVerify(verifyDes);
                    pojo.setLastupdateBy(lastUpdtBy);
                    pojo.setLastupdateDatel(lastUpdtDt);
                    pojo.setVerifyBy(verifyBy);
                    pojo.setEmail(email);
                    pojo.setCustName(custName);
                    pojo.setSrNo(srno);

                    repList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }

    @Override
    public List<ServiceDetailPojo> getIndividualServiceDetail(String brnCode, String acno, String custid, String serviceType) throws ApplicationException {
        List<ServiceDetailPojo> repList = new ArrayList<>();
        List sertempList = null;
        try {
            if (brnCode.equalsIgnoreCase("A")) {
                if (!acno.equalsIgnoreCase("")) {
                    sertempList = em.createNativeQuery("select service_type,customer_id,acno,email,frequency,last_update_by,"
                            + "last_update_date,verify,verify_by,start_flag from cbs_service_detail where acno = '" + acno + "' and  "
                            + " service_type = '" + serviceType + "'").getResultList();
                } else {
                    sertempList = em.createNativeQuery("select service_type,customer_id,acno,email,frequency,last_update_by,"
                            + "last_update_date,verify,verify_by,start_flag from cbs_service_detail where customer_id = '" + custid + "' and "
                            + "  service_type = '" + serviceType + "'").getResultList();
                }
            } else {
                if (!acno.equalsIgnoreCase("")) {
                    sertempList = em.createNativeQuery("select service_type,customer_id,acno,email,frequency,last_update_by,"
                            + "last_update_date,verify,verify_by,start_flag from cbs_service_detail where acno = '" + acno + "' and  "
                            + " substring(acno,1,2)='" + brnCode + "' and service_type = '" + serviceType + "'").getResultList();
                } else {
                    sertempList = em.createNativeQuery("select service_type,customer_id,acno,email,frequency,last_update_by,"
                            + "last_update_date,verify,verify_by,start_flag from cbs_service_detail where customer_id = '" + custid + "' and "
                            + " substring(acno,1,2)='" + brnCode + "' and service_type = '" + serviceType + "' ").getResultList();
                }
            }
            int n = 1;
            if (!sertempList.isEmpty()) {
                for (int i = 0; i < sertempList.size(); i++) {
                    ServiceDetailPojo pojo = new ServiceDetailPojo();
                    Vector ele = (Vector) sertempList.get(i);
                    int srno = n++;
                    String servicetype = ele.get(0).toString().trim();
                    List Servicename = commonReport.getfrequencydescription("371", servicetype);
                    String ServiceDes = Servicename.get(0).toString().trim();
                    String custid1 = ele.get(1).toString().trim();
                    String acno1 = ele.get(2).toString().trim();
                    String email = ele.get(3).toString().trim();
                    String freq = ele.get(4).toString().trim();
                    List freDescription = commonReport.getfrequencydescription("370", freq);
                    String freDes = freDescription.get(0).toString();
                    String lastUpdtBy = ele.get(5).toString().trim();
                    String lastUpdtDt = ele.get(6).toString().trim();
                    String verify = ele.get(7).toString().trim();
                    String verifyDes = "";
                    if (verify.equalsIgnoreCase("Y")) {
                        verifyDes = "verify";
                    } else {
                        verifyDes = "UnVerify";
                    }
                    CustomerDetail customerObj = miscReportFacadeS1.getCustomerDetailByAccountNo(acno1);
                    String custName = customerObj.getCustomerName();
                    String verifyBy = ele.get(8).toString().trim();
                    String start = ele.get(9).toString().trim();
                    List startDescription = commonReport.getfrequencydescription("372", start);
                    String StartDes = startDescription.get(0).toString();

                    pojo.setServiceType(ServiceDes);
                    pojo.setCustid(custid1);
                    pojo.setAcNo(acno1);
                    pojo.setFrequency(freDes);
                    pojo.setStartFlag(StartDes);
                    pojo.setVerify(verifyDes);
                    pojo.setLastupdateBy(lastUpdtBy);
                    pojo.setLastupdateDatel(lastUpdtDt);
                    pojo.setVerifyBy(verifyBy);
                    pojo.setEmail(email);
                    pojo.setCustName(custName);
                    pojo.setSrNo(srno);

                    repList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }

    @Override
    public List<MailingDetailsPojo> getIndividualMailingDetail(String brnCode, String custid, String serviceType, String frDt, String toDt, String mode) throws ApplicationException {
        List<MailingDetailsPojo> repList = new ArrayList<>();
        List mailtempList = null;
        try {
            if (mode.equalsIgnoreCase("IND")) {
                if (brnCode.equalsIgnoreCase("A")) {
                    mailtempList = em.createNativeQuery("select a.service_type,a.customer_id,a.stmt_time,a.stmt_due_dt,b.Acno "
                            + "from cbs_email a ,customerid b where b.CustId =a.customer_id and (cast(stmt_time as date) between '" + frDt + "' and '" + toDt + "') "
                            + "and a.customer_id = '" + custid + "' and service_type='" + serviceType + "' ").getResultList();
                } else {
                    mailtempList = em.createNativeQuery("select a.service_type,a.customer_id,a.stmt_time,a.stmt_due_dt,b.Acno "
                            + "from cbs_email a ,customerid b where b.CustId =a.customer_id and (cast(stmt_time as date) between '" + frDt + "' and '" + toDt + "') "
                            + "and substring(b.Acno,1,2)='" + brnCode + "' and a.customer_id = '" + custid + "' and service_type='" + serviceType + "' ").getResultList();
                }
            } else if (mode.equalsIgnoreCase("ALL")) {
                if (brnCode.equalsIgnoreCase("A")) {
                    mailtempList = em.createNativeQuery("select a.service_type,a.customer_id,a.stmt_time,a.stmt_due_dt,b.Acno "
                            + "from cbs_email a ,customerid b where b.CustId =a.customer_id and (cast(stmt_time as date) between '" + frDt + "' and '" + toDt + "') "
                            + "and service_type='" + serviceType + "' ").getResultList();
                } else {
                    mailtempList = em.createNativeQuery("select a.service_type,a.customer_id,a.stmt_time,a.stmt_due_dt,b.Acno "
                            + "from cbs_email a ,customerid b where b.CustId =a.customer_id and (cast(stmt_time as date) between '" + frDt + "' and '" + toDt + "') "
                            + "and substring(b.Acno,1,2)='" + brnCode + "' and service_type='" + serviceType + "' ").getResultList();
                }
            }
            int n = 1;
            if (!mailtempList.isEmpty()) {
                for (int i = 0; i < mailtempList.size(); i++) {
                    MailingDetailsPojo pojo = new MailingDetailsPojo();
                    Vector ele = (Vector) mailtempList.get(i);
                    int srno = n++;
                    String servicetype = ele.get(0).toString().trim();
                    List Servicename = commonReport.getfrequencydescription("371", servicetype);
                    String ServiceDes = Servicename.get(0).toString().trim();
                    String custId = ele.get(1).toString().trim();
                    String stmtTime = ele.get(2).toString().trim();
                    String dueDate = ele.get(3).toString().trim();
                    String Acno = ele.get(4).toString().trim();
                    CustomerDetail customerObj = miscReportFacadeS1.getCustomerDetailByAccountNo(Acno);
                    String custName = customerObj.getCustomerName();

                    pojo.setServiceType(ServiceDes);
                    pojo.setSrNo(srno);
                    pojo.setStmtTime(stmtTime);
                    pojo.setCustId(custId);
                    pojo.setCustName(custName);
                    pojo.setStmtDueDate(dueDate);
                    pojo.setAcNo(Acno);

                    repList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }
}
