/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.Form61APojo;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "SFTReportFacade")
public class SFTReportFacade implements SFTReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private RbiReportFacadeRemote rbiReportFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private LoanReportFacadeRemote bal;
    @EJB
    private MiscReportFacadeRemote miscRemote;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd_1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    NumberFormat nft = new DecimalFormat("0.00");
    NumberFormat amtFormatter = new DecimalFormat("0.00");

    public List<Form61APojo> form61AForSBAndCAReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException {
        List<Form61APojo> airAcc = new ArrayList<Form61APojo>();
        try {

            String mainQuery = "", acNatureQuery = "", custIdPre = "", custIdWise = "", govtFlag = "";
            int reportSerialNumber = 0;
            String bnkCode = ftsPosting.getBankCode();
            if (acNature.equalsIgnoreCase("SFT003")) {
                acNatureQuery = "select acctcode from accounttypemaster where acctnature in ('CA') ";
                mainQuery = "select cast(ifnull(e.custid,'') as decimal) as custId, "
                        + " ifnull(d.acno,'') as acno, "
                        + " ifnull(c.acctNature,'') as acNature, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as CustName, "
                        + " ifnull(d.accstatus,'') as accStatus, "
                        + " ifnull(lpad(a.BrnCode,2,0),'') as brnCode, "
                        + " ifnull(a.BranchName,'') as brnName, "
                        + " ifnull(a.Address,'') as brnAdd, "
                        + " ifnull(a.City,'') as brnCity, "
                        + " ifnull(a.Pincode,'') as brnPin, "
                        + " ifnull(a.State,'') as brnState , "
                        + " 'IN' as brnCountry, "
                        + " ifnull(b.BrPhone,'') as brnPhone, "
                        + " ifnull(a.mobileNo,'') as brnMobileNo, "
                        + " ifnull(b.BrFax,'') as brnFaxFaxNo, "
                        + " ifnull(a.email,'') as brnEmail, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + "concat(ifnull(f.fathername,''),"
                        + "if(ifnull(f.FatherMiddleName,'')= '',"
                        + "ifnull(f.FatherMiddleName,''),"
                        + "concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + "if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),"
                        + " concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'), "
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'), "
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01') "
                        + " ), ifnull(f.PAN_GIRNumber,'')) as Pan, "
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'), "
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'), "
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01') "
                        + " ), ifnull(f.AADHAAR_NO,'')) as AadhaarNo, "
                        + " ifnull(f.PAN_GIRNumber,'') as Form60, "
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, "
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'D' and CustEntityType = '01'),'') as DrivingLicenseNo,"
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, "
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card, "
                        + " date_format(ifnull(f.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,  "
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')  as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
                        + " if(ifnull(f.PerPostalCode,'')='','XXXXXX',ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode, "
                        + " if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX', ifnull(f.PerStateCode,'XX'))) as StateCode, "
                        + " 'IN' as countoryCode, "
                        + " ifnull(f.PerPhoneNumber,'') as phnoneNo, "
                        + " ifnull(f.mobilenumber,'') as MobileNo, "
                        + " ifnull(f.PerFaxNumber,'') as faxNo, "
                        + " ifnull(f.EmailID,'') as email, "
                        + " ifnull(d.custid1,''), ifnull(d.custid2,''), ifnull(d.custid3,''), ifnull(d.custid4,'') "
                        + " from  "
                        + " branchmaster a,  "
                        + " parameterinfo b,  "
                        + " accounttypemaster c,  "
                        + " accountmaster d,  "
                        + " customerid e,  "
                        + " cbs_customer_master_detail f,  "
                        + " cbs_cust_misinfo g  "
                        + " where a.BrnCode = b.BrnCode  "
                        + " and lpad(a.BrnCode,2,0) = substring(d.ACNo,1,2)  "
                        + " and substring(d.ACNo,3,2) = c.AcctCode  "
                        + " and d.ACNo = e.Acno  "
                        + " and e.CustId = f.customerid  "
                        + " and f.customerid = g.CustomerId "
                        + " and substring(d.acno,1,2) in (" + brCode + ")  "
                        + " and substring(d.acno,3,2) in (" + acNatureQuery + ") "
                        + " and e.CustId in "
                        + " (select b.CustId from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " /*and substring(a.acno,1,2) in (" + brCode + ")*/ "
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and (( a.ty = 0 and a.trantype = 0) or ( a.ty = 1 and a.trantype = 0) ) "
                        + " group by b.CustId having((sum(a.cramt) between " + fromAmt + " and " + toAmt + ") or (sum(a.dramt) between " + fromAmt + " and " + toAmt + ")) ) "
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " order by e.CustId, d.acno ";
            } else if (acNature.equalsIgnoreCase("SFT004")) {
                acNatureQuery = "select acctcode from accounttypemaster where acctnature not in ('CA','FD','MS','RD','DS') ";
                mainQuery = "select cast(ifnull(e.custid,'') as decimal) as custId, "
                        + " ifnull(d.acno,'') as acno, "
                        + " ifnull(c.acctNature,'') as acNature, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as CustName, "
                        + " ifnull(d.accstatus,'') as accStatus, "
                        + " ifnull(lpad(a.BrnCode,2,0),'') as brnCode, "
                        + " ifnull(a.BranchName,'') as brnName, "
                        + " ifnull(a.Address,'') as brnAdd, "
                        + " ifnull(a.City,'') as brnCity, "
                        + " ifnull(a.Pincode,'') as brnPin, "
                        + " ifnull(a.State,'') as brnState , "
                        + " 'IN' as brnCountry, "
                        + " ifnull(b.BrPhone,'') as brnPhone, "
                        + " ifnull(a.mobileNo,'') as brnMobileNo, "
                        + " ifnull(b.BrFax,'') as brnFaxFaxNo, "
                        + " ifnull(a.email,'') as brnEmail, "
                        + " ((select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and trantype=0 )"
                        + " + "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and trantype=0 )) as AggCrCashAmtDurPd, "
                        + " ((select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and trantype=0)"
                        + " + "
                        + "(select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and trantype=0) ) as AggDrCashAmtDurPd, "
                        + " ((select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0)"
                        + " + "
                        + "((select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0)) ) as CrAmtBeforeDemoFinPd, "
                        + " ((select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0)"
                        + " + "
                        + "((select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0)) ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + "concat(ifnull(f.fathername,''),"
                        + "if(ifnull(f.FatherMiddleName,'')= '',"
                        + "ifnull(f.FatherMiddleName,''),"
                        + "concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + "if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),"
                        + " concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'), "
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'), "
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01') "
                        + " ), ifnull(f.PAN_GIRNumber,'')) as Pan, "
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'), "
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'), "
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01') "
                        + " ), ifnull(f.AADHAAR_NO,'')) as AadhaarNo, "
                        + " ifnull(f.PAN_GIRNumber,'') as Form60, "
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, "
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'D' and CustEntityType = '01'),'') as DrivingLicenseNo,"
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, "
                        + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card, "
                        + " date_format(ifnull(f.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,  "
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')  as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
                        + " if(ifnull(f.PerPostalCode,'')='','XXXXXX',ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode, "
                        + " if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX', ifnull(f.PerStateCode,'XX'))) as StateCode, "
                        + " 'IN' as countoryCode, "
                        + " ifnull(f.PerPhoneNumber,'') as phnoneNo, "
                        + " ifnull(f.mobilenumber,'') as MobileNo, "
                        + " ifnull(f.PerFaxNumber,'') as faxNo, "
                        + " ifnull(f.EmailID,'') as email, "
                        + " ifnull(d.custid1,''), ifnull(d.custid2,''), ifnull(d.custid3,''), ifnull(d.custid4,'') "
                        + " from  "
                        + " branchmaster a,  "
                        + " parameterinfo b,  "
                        + " accounttypemaster c,  "
                        + " accountmaster d,  "
                        + " customerid e,  "
                        + " cbs_customer_master_detail f,  "
                        + " cbs_cust_misinfo g  "
                        + " where a.BrnCode = b.BrnCode  "
                        + " and lpad(a.BrnCode,2,0) = substring(d.ACNo,1,2)  "
                        + " and substring(d.ACNo,3,2) = c.AcctCode  "
                        + " and d.ACNo = e.Acno  "
                        + " and e.CustId = f.customerid  "
                        + " and f.customerid = g.CustomerId "
                        + " and substring(d.acno,1,2) in (" + brCode + ") "
                        + " and substring(d.acno,3,2) in (" + acNatureQuery + ") "
                        + " and e.CustId in ("
                        + " select k.id/*, cast(sum(k.amt) as decimal(15,2))*/ from ("
                        + " select a.acno as ano,c.custid as id, sum(a.cramt) as amt from loan_recon a, customerid c"
                        + " where a.acno = c.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "'"
                        + " and a.ty = 0  and a.trantype='0'  and a.TranType<>'8'"
                        + " group by a.acno,c.custid"
                        + " union all "
                        + " select a.acno as ano,c.custid as id, sum(a.cramt) as amt from recon a, customerid c"
                        + " where a.acno = c.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "'"
                        + " and a.ty = 0 and a.trantype='0' and a.TranType<>'8'"
                        + " group by a.acno,c.custid"
                        + " ) k group by k.id having sum(k.amt) between " + fromAmt + " and " + toAmt + " ) "
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " order by e.CustId, d.acno ";
            }

            //            System.out.println("Query>>>>>:" + mainQuery);
            List custIdWiseList = em.createNativeQuery(mainQuery).getResultList();
            if (!custIdWiseList.isEmpty()) {
                Map<String, BigDecimal> mapCrAmt = new HashMap<String, BigDecimal>();
                Map<String, BigDecimal> mapDrAmt = new HashMap<String, BigDecimal>();
                String acNoPre = "";
                for (int i = 0; i < custIdWiseList.size(); i++) {
                    Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                    String custId = custIdWiseVector.get(0).toString();
                    String acNo = custIdWiseVector.get(1).toString();                                        
                    BigDecimal aggGrossAmountCrCash = new BigDecimal(custIdWiseVector.get(16).toString());
                    BigDecimal aggGrossAmountDrCash = new BigDecimal(custIdWiseVector.get(17).toString());
                    if (mapCrAmt.containsKey(custId)) { //Present Credit Entry
                        if (!acNo.equalsIgnoreCase(acNoPre)) {
                            mapCrAmt.put(custId, new BigDecimal(custIdWiseVector.get(16).toString()).add(mapCrAmt.get(custId)));                            
                        }
                    } else { //Not Present
                        mapCrAmt.put(custId, new BigDecimal(custIdWiseVector.get(16).toString()));
                    } 
                    if (mapDrAmt.containsKey(custId)) { //Present Debit Entry
                        if (!acNo.equalsIgnoreCase(acNoPre)) {
                            mapDrAmt.put(custId, new BigDecimal(custIdWiseVector.get(17).toString()).add(mapDrAmt.get(custId)));
                        }
                    } else { //Not Present
                        mapDrAmt.put(custId, new BigDecimal(custIdWiseVector.get(17).toString()));
                    } 
                    acNoPre = acNo;
                }
                for (int i = 0; i < custIdWiseList.size(); i++) {
                    Form61APojo airPojo = new Form61APojo();
                    Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                    String custId = custIdWiseVector.get(0).toString();

                    if (isIterate(acNature, Double.parseDouble(custIdWiseVector.get(16).toString()),
                            Double.parseDouble(custIdWiseVector.get(17).toString()))) {

                        //}

                        if (custId.equalsIgnoreCase(custIdPre)) {
                            reportSerialNumber = reportSerialNumber;
                        } else {
                            reportSerialNumber = reportSerialNumber + 1;
                        }

                        Integer originalReportSerialNumber = 0;
                        String acNo = custIdWiseVector.get(1).toString();
                        String accountNature = custIdWiseVector.get(2).toString();
                        String accountType = "";
                        if (accountNature.equalsIgnoreCase("CA")) {
                            accountType = "BC"; //Current Account
                        } else if (accountNature.equalsIgnoreCase("SB")) {
                            accountType = "BS"; //Saving Account
                        } else {
                            accountType = "ZZ"; //Other Account
                        }
                        String accountHolderName = custIdWiseVector.get(3).toString();
                        String accountStatus = custIdWiseVector.get(4).toString();
                        if (accountStatus.equalsIgnoreCase("1")) {
                            accountStatus = "A";
                        } else if (accountStatus.equalsIgnoreCase("9")) {
                            accountStatus = "C";
                        } else {
                            accountStatus = "Z";
                        }
                        String branchReferenceNumber = custIdWiseVector.get(5).toString();
                        String branchName = custIdWiseVector.get(6).toString();
                        String branchAddress = custIdWiseVector.get(7).toString();
                        String branchCityTown = custIdWiseVector.get(8).toString();
                        String branchPostalCode = custIdWiseVector.get(9).toString();
                        String branchState = custIdWiseVector.get(10).toString();
                        String branchCountry = custIdWiseVector.get(11).toString();
                        String branchSTDCode = custIdWiseVector.get(12).toString().contains("-") ? custIdWiseVector.get(12).toString().split("-")[0] : "";
                        String branchPhoneNumber = custIdWiseVector.get(12).toString().contains("-") ? custIdWiseVector.get(12).toString().split("-")[1] : "";
                        String branchMobileNumber = custIdWiseVector.get(13).toString().length() != 10 ? "" : custIdWiseVector.get(13).toString();
                        String branchbFaxSTDCode = custIdWiseVector.get(14).toString().contains("-") ? custIdWiseVector.get(14).toString().split("-")[0] : "";;
                        String branchFaxPhoneNo = custIdWiseVector.get(14).toString().contains("-") ? custIdWiseVector.get(14).toString().split("-")[1] : "";;
                        String branchEmail = custIdWiseVector.get(15).toString();
                        String branchRemarks = "";
//                        BigDecimal aggGrossAmountCrCash = new BigDecimal(custIdWiseVector.get(16).toString());
                        BigDecimal aggGrossAmountCrCash = mapCrAmt.get(custId);
//                        BigDecimal aggGrossAmountDrCash = new BigDecimal(custIdWiseVector.get(17).toString());
                        BigDecimal aggGrossAmountDrCash = mapDrAmt.get(custId);
                        BigDecimal amountCrBeforeDemonetization;
                        BigDecimal amountCrAfterDemonetization; 
                        // Discuss with Himant ji & Alokk Sir Demonetization after or befor amt zero
//                        if (bnkCode.equalsIgnoreCase("KHAT")) {
//                            amountCrBeforeDemonetization = new BigDecimal("0");
//                            amountCrAfterDemonetization = new BigDecimal("0");
//                        } else {
//                            amountCrBeforeDemonetization = new BigDecimal(custIdWiseVector.get(18).toString());
//                            amountCrAfterDemonetization = new BigDecimal(custIdWiseVector.get(19).toString());
//                        }
                        
                         amountCrBeforeDemonetization = new BigDecimal("0");
                         amountCrAfterDemonetization = new BigDecimal("0");

                        String accountRemarks = "";
                        /*  In Our System
                         1	SELF                    F
                         2	EITHER OR SURVIVOR      S
                         3	FORMER OR SURVIVOR      S
                         4	ANY ONE OR SURVIVOR     S
                         5	ANY TWO JOINTLY         S
                         6	ANY THREE JOINTLY       T
                         7	UNDER POWER OF ATTOR    A
                         8	PROPRIETOR              A
                         9	AUTHORIZED SIGNATORY    A
                         10	M.D.                    A
                         11	UNDER GUARDIANSHIP      C
                         12	BOTH OF TWO JOINTLY     Z
                         13	MINOR                   C
                         14	ANY FOUR JOINTLY        Z
                         15	ANY FIVE JOINTLY        Z
                         16	ALL JOINTLY             Z   
                         17	JOINTLY                 Z
                         18     KARTA OF HUF            Z
                         * *****************************************
                         *  In FORM-61A report required
                         * *****************************************
                         F -First/Sole Account Holder
                         S - Second Account Holder
                         T - Third Account Holder
                         A - Authorised Signatory
                         C - Controlling Person
                         Z - Others
                         X - Not Categorised
                         */
                        String accountRelationship = custIdWiseVector.get(20).toString();
//                        if (accountNature.equalsIgnoreCase("SB")) {
//                            accountRelationship = "F";
//                        } else 
                        if (accountNature.equalsIgnoreCase("CA")) {
                            accountRelationship = "A";
                        } else if (accountRelationship.equalsIgnoreCase("1")) {
                            accountRelationship = "F";
                        } else if (accountRelationship.equalsIgnoreCase("5") || accountRelationship.equalsIgnoreCase("2")
                                || accountRelationship.equalsIgnoreCase("3") || accountRelationship.equalsIgnoreCase("4")) {
                            accountRelationship = "S";
                        } else if (accountRelationship.equalsIgnoreCase("6")) {
                            accountRelationship = "T";
                        } else if (accountRelationship.equalsIgnoreCase("7") || accountRelationship.equalsIgnoreCase("8")
                                || accountRelationship.equalsIgnoreCase("9") || accountRelationship.equalsIgnoreCase("10")) {
                            accountRelationship = "A";
                        } else if (accountRelationship.equalsIgnoreCase("11") || accountRelationship.equalsIgnoreCase("13")) {
                            accountRelationship = "C";
                        } else {
                            accountRelationship = "Z";
                        }
                        String personName = custIdWiseVector.get(21).toString();
                        /*Person Type                     
                         * ************************
                         AcCategory Code	Description                     FORM 61A	Description
                         * *******************************************************************************************************
                         LB                  LOCAL BODIES                    AO	 Association of persons/Body of individuals
                         AB                  AUTONOMOUS BODIES               AO	 Association of persons/Body of individuals
                         CGD                 CENTRAL GOVT DEPT.              CB	 Public Limited Company 
                         CGP                 CENTRAL GOVT PSUS               CB	 Public Limited Company 
                         SGD                 STATE GOVT. DEPT.               CB	 Public Limited Company 
                         SGP                 STATE GOVT. PSUS                CB	 Public Limited Company 
                         LC                  PUBLIC LIMITED COMPANY          CB	 Public Limited Company 
                         PCS                 PRIVATE/CORPORATE SECTOR        CR	 Private Limited Company
                         PL                  PRIVATE LIMITED COMPANY         CR	 Private Limited Company
                         HUF                 HUF	                     HF	 HUF
                         IND                 INDIVIDUAL                      IN	 Individual
                         JLG                 JOINT LIABILITIES GROUP         LL	Limited Liability Partnership (LLP)
                         COS                 CO-OP. SOCIETY                  SO	 Society
                         RS                  REGISTERED SOCIETIES            SO	 Society
                         TG                  TRUST GROUP                     TR	 Trust
                         UN                  UNCLASSIFIED                    XX	 Not Categorised
                         MI                  MICROFINANCE INST.              ZZ	 Others
                         NGO                 NGOS                            ZZ	 Others
                         SB                  STATUTORY BODIES                ZZ	 Others
                         FI                  FINANCIAL INCLUSION ACCOUNT     ZZ	 Others
                         PC                  PROPRIETARY CONCERNS            SP	 Sole Proprietorship
                         PF                  PROPRIETARY / PARTNERSHIP FIRM  PF	 Partnership Firm

                         * ************************
                         IN - Individual
                         SP - Sole Proprietorship
                         PF - Partnership Firm
                         HF - HUF
                         CR - Private Limited Company 
                         CB - Public Limited Company 
                         SO - Society
                         AO - Association of persons/Body of individuals
                         TR - Trust
                         LI - Liquidator (Not Defined)
                         LL - LLP
                         ZZ - Others
                         XX - Not Categorised
                         */
                        String personType = custIdWiseVector.get(22).toString();
                        if (personType.equalsIgnoreCase("IND")) {
                            personType = "IN";
                        } else if (personType.equalsIgnoreCase("PC")) {
                            personType = "SP";
                        } else if (personType.equalsIgnoreCase("PF")) {
                            personType = "PF";
                        } else if (personType.equalsIgnoreCase("HUF")) {
                            personType = "HF";
                        } else if (personType.equalsIgnoreCase("PCS") || personType.equalsIgnoreCase("PL")) {
                            personType = "CR";
                        } else if (personType.equalsIgnoreCase("CGD") || personType.equalsIgnoreCase("CGP") || personType.equalsIgnoreCase("SGD")
                                || personType.equalsIgnoreCase("SGP") || personType.equalsIgnoreCase("LC")) {
                            personType = "CB";
                        } else if (personType.equalsIgnoreCase("COS") || personType.equalsIgnoreCase("RS")) {
                            personType = "SO";
                        } else if (personType.equalsIgnoreCase("LB") || personType.equalsIgnoreCase("AB")) {
                            personType = "AO";
                        } else if (personType.equalsIgnoreCase("TG")) {
                            personType = "TR";
                        } else if (personType.equalsIgnoreCase("JLG")) {
                            personType = "LL";
                        } else if (personType.equalsIgnoreCase("UN")) {
                            personType = "XX";
                        } else {
                            personType = "ZZ";
                        }
//                    else if (personType.equalsIgnoreCase("")) {
//                        personType = "LI";
//                    }
                        String customerIdentity = custId;
                        /* 
                         M - Male
                         F - Female
                         O – Others
                         N – Not Applicable (for entities) X – Not Categorised
                         */
                        String gender = custIdWiseVector.get(23).toString();
                        if (gender.equalsIgnoreCase("M")) {
                            gender = "M";
                        } else if (gender.equalsIgnoreCase("F")) {
                            gender = "F";
                        } else if (gender.equalsIgnoreCase("O")) {
                            gender = "O";
                        } else {
                            gender = "X";
                        }
                        if (accountNature.equalsIgnoreCase("CA")
                                && (personType.equalsIgnoreCase("CB") || personType.equalsIgnoreCase("CR") || personType.equalsIgnoreCase("HUF")
                                || personType.equalsIgnoreCase("LL") || personType.equalsIgnoreCase("SO") || personType.equalsIgnoreCase("TR")
                                || personType.equalsIgnoreCase("PF") )) {
                            gender = "N";
                        }
                        String fatherName = custIdWiseVector.get(24).toString();
                        String pan = custIdWiseVector.get(25).toString().trim().toUpperCase();
                        String flag = "Y";
                        if (pan.length() == 10) {
                            if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                                flag = "N";
                            }
                            if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                                flag = "N";
                            }
                            if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                                flag = "N";
                            }
                            if (!flag.equalsIgnoreCase("Y")) {
                                pan = "";
                            }
                        } else if (pan.length() > 0) {
                            pan = "";
                        } else {
                            pan = "";
                        }
                        String aadhaarNumber = custIdWiseVector.get(26).toString();
                        String form60Acknowledgement = custIdWiseVector.get(27).toString().trim().equalsIgnoreCase("Form 60") ? "Form 60" : "";//custIdWiseVector.get(27).toString();
                        if ((custIdWiseVector.get(27).toString().trim().length() == 10) && (!custIdWiseVector.get(27).toString().equalsIgnoreCase("Form 60"))) {
                            pan = custIdWiseVector.get(27).toString().trim().toUpperCase();
                            if (pan.length() == 10) {
                                flag = "Y";
                                if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                                    flag = "N";
                                }
                                if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                                    flag = "N";
                                }
                                if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                                    flag = "N";
                                }
                                if (!flag.equalsIgnoreCase("Y")) {
                                    pan = "";
                                }
                            } else if (pan.length() > 0) {
                                pan = "";
                            } else {
                                pan = "";
                            }
                        }

                        /*  **********************
                         *  A - Passport
                         B - Election Id Card
                         C - PAN Card
                         D - ID Card issued by Government/PSU 
                         E - Driving License
                         G - UIDAI Letter / Aadhaar Card
                         H - NREGA job card
                         Z – Others
                         */
                        String identificationType = "", identificationNumber = "";
                        if (!custIdWiseVector.get(28).toString().equalsIgnoreCase("")) {//Voter Id
                            identificationType = "B";
                            identificationNumber = custIdWiseVector.get(28).toString();
                        } else if (!custIdWiseVector.get(29).toString().equalsIgnoreCase("")) {//Driving License
                            identificationType = "E";
                            identificationNumber = custIdWiseVector.get(29).toString();
                        } else if (!custIdWiseVector.get(30).toString().equalsIgnoreCase("")) {//Passport
                            identificationType = "A";
                            identificationNumber = custIdWiseVector.get(30).toString();
                        } else if (!custIdWiseVector.get(31).toString().equalsIgnoreCase("")) {//Narega
                            identificationType = "H";
                            identificationNumber = custIdWiseVector.get(31).toString();
                        } else if (!custIdWiseVector.get(26).toString().equalsIgnoreCase("")) {//Aadhaar
                            identificationType = "G";
                            identificationNumber = custIdWiseVector.get(26).toString();
                        } else if (!pan.equalsIgnoreCase("")) {
                            if (!pan.equalsIgnoreCase("Form 60")) {
                                identificationType = "C";
                                identificationNumber = pan;
                            }
                        }

                        String dobOrIncorporation = custIdWiseVector.get(32).toString();
                        String nationalityOrCountryOfIncorporation = custIdWiseVector.get(34).toString();
                        if (personType.equalsIgnoreCase("IN")) {
                            dobOrIncorporation = dobOrIncorporation;
                            nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                        } else if (personType.equalsIgnoreCase("XX")) {
                            dobOrIncorporation = dobOrIncorporation;
                            nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                        } else if (personType.equalsIgnoreCase("HF")) {
                            if (!dobOrIncorporation.equalsIgnoreCase("")) {
                                dobOrIncorporation = dobOrIncorporation;
                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                            } else if (!custIdWiseVector.get(33).toString().equalsIgnoreCase("")) {
                                dobOrIncorporation = custIdWiseVector.get(33).toString();
                                nationalityOrCountryOfIncorporation = custIdWiseVector.get(35).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(35).toString();
                            } else {
                                dobOrIncorporation = "01-01-0001";
                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                            }
                        } else {
                            dobOrIncorporation = custIdWiseVector.get(33).toString();
                            nationalityOrCountryOfIncorporation = custIdWiseVector.get(35).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(35).toString();
                        }

                        String businessOrOccupation = custIdWiseVector.get(36).toString();
                        String addressType = custIdWiseVector.get(37).toString();
                        String address = custIdWiseVector.get(38).toString();
                        String cityOrTown = custIdWiseVector.get(39).toString();
                        String pinCode = custIdWiseVector.get(40).toString();
                        String state = custIdWiseVector.get(41).toString();
                        String country = custIdWiseVector.get(42).toString();
                        String primaryStdCode = custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-").length > 0 ? custIdWiseVector.get(43).toString().split("-")[0] : "" : "";
                        String primaryTelephoneNumber = custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-").length > 1 ? custIdWiseVector.get(43).toString().split("-")[1] : "" : "";
                        String primaryMobileNumber = custIdWiseVector.get(44).toString().length() != 10 ? "" : custIdWiseVector.get(44).toString();
                        String secondaryStdCode = custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-").length > 0 ? custIdWiseVector.get(45).toString().split("-")[0] : "" : "";
                        String secondaryTelephoneNumber = custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-").length > 1 ? custIdWiseVector.get(45).toString().split("-")[1] : "" : "";
                        String secondaryMobileNumber = "";
                        String email = custIdWiseVector.get(46).toString();
                        String remarks = "";
//                    if (custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 60") || custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 61")) {
//                        airPojo.setForm60Or61("NULL");
//                    }
                        airPojo.setReportSerialNumber(reportSerialNumber);
                        airPojo.setOriginalReportSerialNumber(originalReportSerialNumber);
                        airPojo.setAccountType(accountType);
                        airPojo.setAccountNumber(acNo);
                        airPojo.setAccountHolderName(accountHolderName);
                        airPojo.setAccountStatus(accountStatus);
                        airPojo.setBranchReferenceNumber(branchReferenceNumber);
                        airPojo.setBranchName(branchName);
                        airPojo.setBranchAddress(branchAddress);
                        airPojo.setBranchCityTown(branchCityTown);
                        airPojo.setBranchPostalCode(branchPostalCode);
                        airPojo.setBranchState(branchState);
                        airPojo.setBranchCountry(branchCountry);
                        airPojo.setBranchSTDCode(branchSTDCode);
                        airPojo.setBranchPhoneNumber(branchPhoneNumber);
                        airPojo.setBranchMobileNumber(branchMobileNumber);
                        airPojo.setBranchbFaxSTDCode(branchbFaxSTDCode);
                        airPojo.setBranchFaxPhoneNo(branchFaxPhoneNo);
                        airPojo.setBranchEmail(branchEmail);
                        airPojo.setBranchRemarks(branchRemarks);
                        airPojo.setAggGrossAmountCrCash(aggGrossAmountCrCash);
                        airPojo.setAggGrossAmountDrCash(aggGrossAmountDrCash);
                        airPojo.setAmountCrBeforeDemonetization(amountCrBeforeDemonetization);
                        airPojo.setAmountCrAfterDemonetization(amountCrAfterDemonetization);
                        airPojo.setAccountRemarks(accountRemarks);
                        airPojo.setAccountRelationship(accountRelationship);
                        airPojo.setPersonName(personName);
                        airPojo.setPersonType(personType);
                        airPojo.setCustomerIdentity(customerIdentity);
                        airPojo.setGender(gender);
                        airPojo.setFatherName(fatherName);
                        airPojo.setPan(pan);
                        airPojo.setAadhaarNumber(aadhaarNumber);
                        airPojo.setForm60Acknowledgement(form60Acknowledgement);
                        airPojo.setIdentificationType(identificationType);
                        airPojo.setIdentificationNumber(identificationNumber);
                        airPojo.setDobOrIncorporation(dobOrIncorporation);
                        airPojo.setNationalityOrCountryOfIncorporation(nationalityOrCountryOfIncorporation);
                        airPojo.setBusinessOrOccupation(businessOrOccupation);
                        airPojo.setAddressType(addressType);
                        airPojo.setAddress(address);
                        airPojo.setCityOrTown(cityOrTown);
                        airPojo.setPinCode(pinCode);
                        airPojo.setState(state);
                        airPojo.setCountry(country);
                        airPojo.setPrimaryStdCode(primaryStdCode);
                        airPojo.setPrimaryTelephoneNumber(primaryTelephoneNumber);
                        airPojo.setPrimaryMobileNumber(primaryMobileNumber);
                        airPojo.setSecondaryStdCode(secondaryStdCode);
                        airPojo.setSecondaryTelephoneNumber(secondaryTelephoneNumber);
                        airPojo.setSecondaryMobileNumber(secondaryMobileNumber);
                        airPojo.setEmail(email);
                        airPojo.setRemarks(remarks);
//                    if (aggGrossAmountCrCash.compareTo(new BigDecimal("0")) != 0) {
//                        airAcc.add(airPojo);
                        int jointCount = 0;
                        if (!custIdWiseVector.get(47).toString().equalsIgnoreCase("")) {
                            jointCount = jointCount + 1;
                        }
                        if (!custIdWiseVector.get(48).toString().equalsIgnoreCase("")) {
                            jointCount = jointCount + 1;
                        }
                        if (!custIdWiseVector.get(49).toString().equalsIgnoreCase("")) {
                            jointCount = jointCount + 1;
                        }
                        if (!custIdWiseVector.get(50).toString().equalsIgnoreCase("")) {
                            jointCount = jointCount + 1;
                        }
                        List jointList;   
                        if (jointCount > 0) {
                            jointList = em.createNativeQuery("select  custid  from (select ifnull(custid1,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid2,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid3,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid4,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + ") a where  custid <>''").getResultList();
                            if (!jointList.isEmpty()) {
                                if(accountNature.equalsIgnoreCase("SB") && (acNature.equalsIgnoreCase("SFT004")) && !(accountRelationship.equalsIgnoreCase("F"))){
                                    accountRelationship = "F";
                                    airPojo.setAccountRelationship(accountRelationship);
                                    airAcc.add(airPojo);
                                } else if (accountNature.equalsIgnoreCase("CA") && (acNature.equalsIgnoreCase("SFT003"))) {
                                    if ((personType.equalsIgnoreCase("CB") || personType.equalsIgnoreCase("CR") || personType.equalsIgnoreCase("HUF")
                                            || personType.equalsIgnoreCase("LL") || personType.equalsIgnoreCase("SO") || personType.equalsIgnoreCase("TR")
                                            || personType.equalsIgnoreCase("PF"))) {
                                        accountRelationship = "F";
                                        airPojo.setAccountRelationship(accountRelationship);
                                        airAcc.add(airPojo);
                                    } else if(personType.equalsIgnoreCase("IN") && jointList.size()>0) {
                                        accountRelationship = "F";
                                        airPojo.setAccountRelationship(accountRelationship);
                                        airAcc.add(airPojo);
                                    }
                                }
                                for (int k = 0; k < jointList.size(); k++) {
                                    Vector jointVectList = (Vector) jointList.get(k);
                                    String jointCustId = jointVectList.get(0).toString();
                                    if (!jointCustId.equalsIgnoreCase("")) {
                                        String Query = "select cast(ifnull(f.customerid,'') as decimal) as custId, '' as acno,  '',  concat(ifnull(f.custname,''), "
                                                + " if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), "
                                                + " if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as CustName,  "
                                                + " '' as accStatus,  '' as brnCode,  '' as brnName,  '' as brnAdd,  '' as brnCity,  '' as brnPin,  '' as brnState ,  "
                                                + " '' as brnCountry,  '' as brnPhone,  '' as brnMobileNo,  '' as brnFaxFaxNo,  '' as brnEmail,  "
                                                + " 0 as AggCrCashAmtDurPd,  0 as AggDrCashAmtDurPd,  0 as CrAmtBeforeDemoFinPd,  0 as CrCashAmtDurDemiPd,  "
                                                + " '' as accRelationShip,  "
                                                + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName,  "
                                                + " '' as PersonType,  ifnull(f.gender,'X') as Gender, "
                                                + " concat(ifnull(f.fathername,''),"
                                                + "if(ifnull(f.FatherMiddleName,'')= '',"
                                                + "ifnull(f.FatherMiddleName,''),"
                                                + "concat(' ', ifnull(f.FatherMiddleName,''))),"
                                                + "if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),"
                                                + " concat(' ', ifnull(f.FatherLastName,'')))) as FatherName,  "
                                                + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'), "
                                                + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'), "
                                                + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01') "
                                                + " ), ifnull(f.PAN_GIRNumber,'')) as Pan, "
                                                + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'), "
                                                + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'), "
                                                + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01') "
                                                + " ), ifnull(f.AADHAAR_NO,'')) as AadhaarNo, "
                                                + " ifnull(f.PAN_GIRNumber,'') as Form60, "
                                                + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, "
                                                + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'D' and CustEntityType = '01'),'') as DrivingLicenseNo,"
                                                + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, "
                                                + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card, "
                                                + " date_format(ifnull(f.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,   ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')   as incorPorationDt,   "
                                                + " 'IN' as dobPlace,  'IN' as incorPorationPlace,   "
                                                + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions,  "
                                                + " '1' as addressType,  "
                                                + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),   "
                                                + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),   if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),   "
                                                + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = f.PerCityCode),''))),   "
                                                + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),   "
                                                + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address,  "
                                                + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
                                                + " if(ifnull(f.PerPostalCode,'')='','XXXXXX',ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode,  "
                                                + " if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX', ifnull(f.PerStateCode,'XX'))) as StateCode,  "
                                                + " 'IN' as countoryCode,  ifnull(f.PerPhoneNumber,'') as phnoneNo,  ifnull(f.mobilenumber,'') as MobileNo,  ifnull(f.PerFaxNumber,'') as faxNo, "
                                                + " ifnull(f.EmailID,'') as email  "
                                                + " from    cbs_customer_master_detail f,   cbs_cust_misinfo g   "
                                                + " where  f.customerid = g.CustomerId  "
                                                + " and f.customerid in  (" + jointCustId + ")  group by f.customerid";

                                        List jointListDetails = em.createNativeQuery(Query).getResultList();
                                        if (!jointListDetails.isEmpty()) {
                                            airPojo = new Form61APojo();
                                            custIdWiseVector = (Vector) jointListDetails.get(0);                                            
                                            if (k == 0) {
                                                /*  In Our System
                                                 1	SELF                    F
                                                 2	EITHER OR SURVIVOR      S
                                                 3	FORMER OR SURVIVOR      S
                                                 4	ANY ONE OR SURVIVOR     S
                                                 5	ANY TWO JOINTLY         S
                                                 6	ANY THREE JOINTLY       T
                                                 7	UNDER POWER OF ATTOR    A
                                                 8	PROPRIETOR              A
                                                 9	AUTHORIZED SIGNATORY    A
                                                 10	M.D.                    A
                                                 11	UNDER GUARDIANSHIP      C
                                                 12	BOTH OF TWO JOINTLY     Z
                                                 13	MINOR                   C
                                                 14	ANY FOUR JOINTLY        Z
                                                 15	ANY FIVE JOINTLY        Z
                                                 16	ALL JOINTLY             Z   
                                                 17	JOINTLY                 Z
                                                 18     KARTA OF HUF            Z                 
                                                 * *****************************************
                                                 *  In FORM-61A report required
                                                 * *****************************************
                                                 F -First/Sole Account Holder
                                                 S - Second Account Holder
                                                 T - Third Account Holder
                                                 A - Authorised Signatory
                                                 C - Controlling Person
                                                 Z - Others
                                                 X - Not Categorised
                                                 */

                                                if (accountNature.equalsIgnoreCase("SB")) {
                                                    if (k == 0) {
                                                        accountRelationship = "S";
                                                    } else if (k == 1) {
                                                        accountRelationship = "T";
                                                    } else {
                                                        accountRelationship = "Z";
                                                    }
                                                } else if (accountNature.equalsIgnoreCase("CA")) {
                                                    if (personType.equalsIgnoreCase("CB") || personType.equalsIgnoreCase("CR") || personType.equalsIgnoreCase("HUF")
                                                            || personType.equalsIgnoreCase("LL") || personType.equalsIgnoreCase("SO") || personType.equalsIgnoreCase("TR")
                                                            || personType.equalsIgnoreCase("PF") || (personType.equalsIgnoreCase("IN") && jointList.size()>0)) {
                                                        accountRelationship = "S";
                                                    } else {
                                                        accountRelationship = "F";
                                                    }
                                                } else {
                                                    if (accountRelationship.equalsIgnoreCase("1")) {
                                                        if (k == 0) {
                                                            accountRelationship = "F";
                                                        } else if (k == 1) {
                                                            accountRelationship = "S";
                                                        }
                                                    } else if (accountRelationship.equalsIgnoreCase("5") || accountRelationship.equalsIgnoreCase("2")
                                                            || accountRelationship.equalsIgnoreCase("3") || accountRelationship.equalsIgnoreCase("4")) {
                                                        if (k == 0) {
                                                            accountRelationship = "F";
                                                        } else if (k == 1) {
                                                            accountRelationship = "S";
                                                        } else if (k == 2) {
                                                            accountRelationship = "T";
                                                        } else if (k == 3) {
                                                            accountRelationship = "Z";
                                                        }
                                                    } else if (accountRelationship.equalsIgnoreCase("6")) {
                                                        if (k == 0) {
                                                            accountRelationship = "F";
                                                        } else if (k == 1) {
                                                            accountRelationship = "S";
                                                        } else if (k == 2) {
                                                            accountRelationship = "T";
                                                        }
                                                    } else if (accountRelationship.equalsIgnoreCase("7") || accountRelationship.equalsIgnoreCase("8")
                                                            || accountRelationship.equalsIgnoreCase("9") || accountRelationship.equalsIgnoreCase("10")) {
                                                        accountRelationship = "A";
                                                    } else if (accountRelationship.equalsIgnoreCase("11") || accountRelationship.equalsIgnoreCase("13")) {
                                                        accountRelationship = "C";
                                                    } else {
                                                        accountRelationship = "Z";
                                                    }
                                                }
                                            } else {
                                                if (accountNature.equalsIgnoreCase("SB")) {
                                                    if (k == 1) {
                                                        accountRelationship = "T";
                                                    } else {
                                                        accountRelationship = "Z";
                                                    }
                                                } else if (accountNature.equalsIgnoreCase("CA")) {
                                                    if (personType.equalsIgnoreCase("IN")) {
                                                        accountRelationship = "S";
                                                    } else if (personType.equalsIgnoreCase("CB") || personType.equalsIgnoreCase("CR") || personType.equalsIgnoreCase("HUF")
                                                            || personType.equalsIgnoreCase("LL") || personType.equalsIgnoreCase("SO") || personType.equalsIgnoreCase("TR")
                                                            || personType.equalsIgnoreCase("PF") || (personType.equalsIgnoreCase("IN") && jointList.size()>0)) {
                                                        if (k == 1) {
                                                            accountRelationship = "T";
                                                        } else {
                                                            accountRelationship = "Z";
                                                        }
                                                    } else {
                                                        if (k == 1) {
                                                            accountRelationship = "S";
                                                        }
                                                        if (k == 2) {
                                                            accountRelationship = "T";
                                                        } else {
                                                            accountRelationship = "Z";
                                                        }
                                                    }

                                                } else {
                                                    if (accountRelationship.equalsIgnoreCase("1")) {
                                                        if (k == 0) {
                                                            accountRelationship = "F";
                                                        } else if (k == 1) {
                                                            accountRelationship = "S";
                                                        }
                                                    } else if (accountRelationship.equalsIgnoreCase("5") || accountRelationship.equalsIgnoreCase("2")
                                                            || accountRelationship.equalsIgnoreCase("3") || accountRelationship.equalsIgnoreCase("4")) {
                                                        if (k == 0) {
                                                            accountRelationship = "F";
                                                        } else if (k == 1) {
                                                            accountRelationship = "S";
                                                        } else if (k == 2) {
                                                            accountRelationship = "T";
                                                        } else if (k == 3) {
                                                            accountRelationship = "Z";
                                                        }
                                                    } else if (accountRelationship.equalsIgnoreCase("6")) {
                                                        if (k == 0) {
                                                            accountRelationship = "F";
                                                        } else if (k == 1) {
                                                            accountRelationship = "S";
                                                        } else if (k == 2) {
                                                            accountRelationship = "T";
                                                        }
                                                    } else if (accountRelationship.equalsIgnoreCase("7") || accountRelationship.equalsIgnoreCase("8")
                                                            || accountRelationship.equalsIgnoreCase("9") || accountRelationship.equalsIgnoreCase("10")) {
                                                        accountRelationship = "A";
                                                    } else if (accountRelationship.equalsIgnoreCase("11") || accountRelationship.equalsIgnoreCase("13")) {
                                                        accountRelationship = "C";
                                                    } else {
                                                        accountRelationship = "Z";
                                                    }
                                                }
                                            }
                                            personName = custIdWiseVector.get(21).toString();
                                            /* 
                                             M - Male
                                             F - Female
                                             O – Others
                                             N – Not Applicable (for entities) X – Not Categorised
                                             */
                                            gender = custIdWiseVector.get(23).toString();
                                            if (gender.equalsIgnoreCase("M")) {
                                                gender = "M";
                                            } else if (gender.equalsIgnoreCase("F")) {
                                                gender = "F";
                                            } else if (gender.equalsIgnoreCase("O")) {
                                                gender = "O";
                                            } else {
                                                gender = "X";
                                            }
                                            fatherName = custIdWiseVector.get(24).toString();
                                            pan = custIdWiseVector.get(25).toString().trim().toUpperCase();
                                            flag = "Y";
                                            if (pan.length() == 10) {
                                                if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                                                    flag = "N";
                                                }
                                                if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                                                    flag = "N";
                                                }
                                                if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                                                    flag = "N";
                                                }
                                                if (!flag.equalsIgnoreCase("Y")) {
                                                    pan = "";
                                                }
                                            } else if (pan.length() > 0) {
                                                pan = "";
                                            } else {
                                                pan = "";
                                            }
                                            aadhaarNumber = custIdWiseVector.get(26).toString();
                                            form60Acknowledgement = custIdWiseVector.get(27).toString().trim().equalsIgnoreCase("Form 60") ? "Form 60" : "";//custIdWiseVector.get(27).toString();
                                            if ((custIdWiseVector.get(27).toString().trim().length() == 10) && (!custIdWiseVector.get(27).toString().equalsIgnoreCase("Form 60"))) {
                                                pan = custIdWiseVector.get(27).toString().trim();
                                                if (pan.length() == 10) {
                                                    flag = "Y";
                                                    if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                                                        flag = "N";
                                                    }
                                                    if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                                                        flag = "N";
                                                    }
                                                    if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                                                        flag = "N";
                                                    }
                                                    if (!flag.equalsIgnoreCase("Y")) {
                                                        pan = "";
                                                    }
                                                } else if (pan.length() > 0) {
                                                    pan = "";
                                                } else {
                                                    pan = "";
                                                }
                                            }

                                            /*  **********************
                                             *  A - Passport
                                             B - Election Id Card
                                             C - PAN Card
                                             D - ID Card issued by Government/PSU 
                                             E - Driving License
                                             G - UIDAI Letter / Aadhaar Card
                                             H - NREGA job card
                                             Z – Others
                                             */
                                            identificationType = "";
                                            identificationNumber = "";
                                            if (!custIdWiseVector.get(28).toString().equalsIgnoreCase("")) {//Voter Id
                                                identificationType = "B";
                                                identificationNumber = custIdWiseVector.get(28).toString();
                                            } else if (!custIdWiseVector.get(29).toString().equalsIgnoreCase("")) {//Driving License
                                                identificationType = "E";
                                                identificationNumber = custIdWiseVector.get(29).toString();
                                            } else if (!custIdWiseVector.get(30).toString().equalsIgnoreCase("")) {//Passport
                                                identificationType = "A";
                                                identificationNumber = custIdWiseVector.get(30).toString();
                                            } else if (!custIdWiseVector.get(31).toString().equalsIgnoreCase("")) {//Narega
                                                identificationType = "H";
                                                identificationNumber = custIdWiseVector.get(31).toString();
                                            } else if (!custIdWiseVector.get(26).toString().equalsIgnoreCase("")) {//Aadhaar
                                                identificationType = "G";
                                                identificationNumber = custIdWiseVector.get(26).toString();
                                            } else if (!pan.equalsIgnoreCase("")) {
                                                if (!pan.equalsIgnoreCase("Form 60")) {
                                                    identificationType = "C";
                                                    identificationNumber = pan;
                                                }
                                            }

                                            dobOrIncorporation = custIdWiseVector.get(32).toString();
                                            nationalityOrCountryOfIncorporation = custIdWiseVector.get(34).toString();
                                            if (personType.equalsIgnoreCase("IN")) {
                                                dobOrIncorporation = dobOrIncorporation;
                                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                                            } else if (personType.equalsIgnoreCase("XX")) {
                                                dobOrIncorporation = dobOrIncorporation;
                                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                                            } else if (personType.equalsIgnoreCase("HF")) {
                                                if (!dobOrIncorporation.equalsIgnoreCase("")) {
                                                    dobOrIncorporation = dobOrIncorporation;
                                                    nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                                                } else if (!custIdWiseVector.get(33).toString().equalsIgnoreCase("")) {
                                                    dobOrIncorporation = custIdWiseVector.get(33).toString();
                                                    nationalityOrCountryOfIncorporation = custIdWiseVector.get(35).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(35).toString();
                                                } else {
                                                    dobOrIncorporation = "01-01-0001";
                                                    nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                                                }
                                            } else {
                                                dobOrIncorporation = custIdWiseVector.get(33).toString();
                                                nationalityOrCountryOfIncorporation = custIdWiseVector.get(35).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(35).toString();
                                            }

                                            businessOrOccupation = custIdWiseVector.get(36).toString();
                                            addressType = custIdWiseVector.get(37).toString();
                                            address = custIdWiseVector.get(38).toString();
                                            cityOrTown = custIdWiseVector.get(39).toString();
                                            pinCode = custIdWiseVector.get(40).toString();
                                            state = custIdWiseVector.get(41).toString();
                                            country = custIdWiseVector.get(42).toString();
                                            primaryStdCode = custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-").length > 0 ? custIdWiseVector.get(43).toString().split("-")[0] : "" : "";
                                            primaryTelephoneNumber = custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-").length > 1 ? custIdWiseVector.get(43).toString().split("-")[1] : "" : "";
                                            primaryMobileNumber = custIdWiseVector.get(44).toString().length() != 10 ? "" : custIdWiseVector.get(44).toString();
                                            secondaryStdCode = custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-").length > 0 ? custIdWiseVector.get(45).toString().split("-")[0] : "" : "";
                                            secondaryTelephoneNumber = custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-").length > 1 ? custIdWiseVector.get(45).toString().split("-")[1] : "" : "";
                                            secondaryMobileNumber = "";
                                            email = custIdWiseVector.get(46).toString();
                                            remarks = "";

                                            airPojo.setReportSerialNumber(reportSerialNumber);
                                            airPojo.setOriginalReportSerialNumber(originalReportSerialNumber);
                                            airPojo.setAccountType(accountType);
                                            airPojo.setAccountNumber(acNo);
                                            airPojo.setAccountHolderName(accountHolderName);
                                            airPojo.setAccountStatus(accountStatus);
                                            airPojo.setBranchReferenceNumber(branchReferenceNumber);
                                            airPojo.setBranchName(branchName);
                                            airPojo.setBranchAddress(branchAddress);
                                            airPojo.setBranchCityTown(branchCityTown);
                                            airPojo.setBranchPostalCode(branchPostalCode);
                                            airPojo.setBranchState(branchState);
                                            airPojo.setBranchCountry(branchCountry);
                                            airPojo.setBranchSTDCode(branchSTDCode);
                                            airPojo.setBranchPhoneNumber(branchPhoneNumber);
                                            airPojo.setBranchMobileNumber(branchMobileNumber);
                                            airPojo.setBranchbFaxSTDCode(branchbFaxSTDCode);
                                            airPojo.setBranchFaxPhoneNo(branchFaxPhoneNo);
                                            airPojo.setBranchEmail(branchEmail);
                                            airPojo.setBranchRemarks(branchRemarks);
                                            airPojo.setAggGrossAmountCrCash(aggGrossAmountCrCash);
                                            airPojo.setAggGrossAmountDrCash(aggGrossAmountDrCash);
                                            airPojo.setAmountCrBeforeDemonetization(amountCrBeforeDemonetization);
                                            airPojo.setAmountCrAfterDemonetization(amountCrAfterDemonetization);
                                            airPojo.setAccountRemarks(accountRemarks);
                                            airPojo.setAccountRelationship(accountRelationship);
                                            airPojo.setPersonName(custIdWiseVector.get(21).toString());
                                            airPojo.setPersonType(personType);
                                            airPojo.setCustomerIdentity(jointCustId);
                                            String jointGender = custIdWiseVector.get(23).toString();
                                            if (jointGender.equalsIgnoreCase("M")) {
                                                jointGender = "M";
                                            } else if (jointGender.equalsIgnoreCase("F")) {
                                                jointGender = "F";
                                            } else if (jointGender.equalsIgnoreCase("O")) {
                                                jointGender = "O";
                                            } else {
                                                jointGender = "X";
                                            }
                                            airPojo.setGender(jointGender);
                                            airPojo.setFatherName(custIdWiseVector.get(24).toString());
                                            String jointPan = custIdWiseVector.get(25).toString().trim().toUpperCase();
                                            String jointFlag = "Y";
                                            if (jointPan.length() == 10) {
                                                if (!ParseFileUtil.isAlphabet(jointPan.substring(0, 5))) {
                                                    jointFlag = "N";
                                                }
                                                if (!ParseFileUtil.isNumeric(jointPan.substring(5, 9))) {
                                                    jointFlag = "N";
                                                }
                                                if (!ParseFileUtil.isAlphabet(jointPan.substring(9))) {
                                                    jointFlag = "N";
                                                }
                                                if (!jointFlag.equalsIgnoreCase("Y")) {
                                                    jointPan = "";
                                                }
                                            } else if (jointPan.length() > 0) {
                                                jointPan = "";
                                            } else {
                                                jointPan = "";
                                            }
                                            airPojo.setPan(jointPan);
                                            airPojo.setAadhaarNumber(custIdWiseVector.get(26).toString());
                                            airPojo.setForm60Acknowledgement(custIdWiseVector.get(27).toString().equalsIgnoreCase("Form 60") ? "Form 60" : "");
                                            /*  **********************
                                             *  A - Passport
                                             B - Election Id Card
                                             C - PAN Card
                                             D - ID Card issued by Government/PSU 
                                             E - Driving License
                                             G - UIDAI Letter / Aadhaar Card
                                             H - NREGA job card
                                             Z – Others
                                             */
                                            String jointIdentificationType = "", jointIdentificationNumber = "";
                                            if (!custIdWiseVector.get(28).toString().equalsIgnoreCase("")) {//Voter Id
                                                jointIdentificationType = "B";
                                                jointIdentificationNumber = custIdWiseVector.get(28).toString();
                                            } else if (!custIdWiseVector.get(29).toString().equalsIgnoreCase("")) {//Driving License
                                                jointIdentificationType = "E";
                                                jointIdentificationNumber = custIdWiseVector.get(29).toString();
                                            } else if (!custIdWiseVector.get(30).toString().equalsIgnoreCase("")) {//Passport
                                                jointIdentificationType = "A";
                                                jointIdentificationNumber = custIdWiseVector.get(30).toString();
                                            } else if (!custIdWiseVector.get(31).toString().equalsIgnoreCase("")) {//Narega
                                                jointIdentificationType = "H";
                                                jointIdentificationNumber = custIdWiseVector.get(31).toString();
                                            } else if (!custIdWiseVector.get(26).toString().equalsIgnoreCase("")) {//Aadhaar
                                                jointIdentificationType = "G";
                                                jointIdentificationNumber = custIdWiseVector.get(26).toString();
                                            } else if (!jointPan.equalsIgnoreCase("")) {
                                                if (!jointPan.equalsIgnoreCase("Form 60")) {
                                                    jointIdentificationType = "C";
                                                    jointIdentificationNumber = jointPan;
                                                }
                                            }

                                            airPojo.setIdentificationType(jointIdentificationType);
                                            airPojo.setIdentificationNumber(jointIdentificationNumber);
                                            airPojo.setDobOrIncorporation(custIdWiseVector.get(32).toString());
                                            airPojo.setNationalityOrCountryOfIncorporation(custIdWiseVector.get(34).toString());
                                            airPojo.setBusinessOrOccupation(custIdWiseVector.get(36).toString());
                                            airPojo.setAddressType(custIdWiseVector.get(37).toString());
                                            airPojo.setAddress(custIdWiseVector.get(38).toString());
                                            airPojo.setCityOrTown(custIdWiseVector.get(39).toString());
                                            airPojo.setPinCode(custIdWiseVector.get(40).toString());
                                            airPojo.setState(custIdWiseVector.get(41).toString());
                                            airPojo.setCountry(custIdWiseVector.get(42).toString());
                                            airPojo.setPrimaryStdCode(custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-").length > 0 ? custIdWiseVector.get(43).toString().split("-")[0] : "" : "");
                                            airPojo.setPrimaryTelephoneNumber(custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-").length > 1 ? custIdWiseVector.get(43).toString().split("-")[1] : "" : "");
                                            airPojo.setPrimaryMobileNumber(custIdWiseVector.get(44).toString().length() != 10 ? "" : custIdWiseVector.get(44).toString());
                                            airPojo.setSecondaryStdCode(secondaryStdCode);
                                            airPojo.setSecondaryTelephoneNumber(custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-")[0] : "");
                                            airPojo.setSecondaryMobileNumber(custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-")[1] : "");
                                            airPojo.setEmail(custIdWiseVector.get(46).toString());
                                            airPojo.setRemarks(remarks);
                                            airAcc.add(airPojo);
                                        }
                                    }
                                }
                            }
                        } else {
                            airAcc.add(airPojo);
                        }
//                    }
                        custIdPre = custId;
                    }
                }
            } else {
                Form61APojo airPojo = new Form61APojo();
                airPojo.setReportSerialNumber(1);
                airPojo.setOriginalReportSerialNumber(0);
                airPojo.setAccountType("NIL");
                airPojo.setAccountNumber("NIL");
                airPojo.setAccountHolderName("NIL");
                airPojo.setAccountStatus("NIL");
                airPojo.setBranchReferenceNumber("NIL");
                airPojo.setBranchName("NIL");
                airPojo.setBranchAddress("NIL");
                airPojo.setBranchCityTown("NIL");
                airPojo.setBranchPostalCode("NIL");
                airPojo.setBranchState("NIL");
                airPojo.setBranchCountry("NIL");
                airPojo.setBranchSTDCode("NIL");
                airPojo.setBranchPhoneNumber("NIL");
                airPojo.setBranchMobileNumber("NIL");
                airPojo.setBranchbFaxSTDCode("NIL");
                airPojo.setBranchFaxPhoneNo("NIL");
                airPojo.setBranchEmail("NIL");
                airPojo.setBranchRemarks("NIL");
                airPojo.setAggGrossAmountCrCash(new BigDecimal("0"));
                airPojo.setAggGrossAmountDrCash(new BigDecimal("0"));
                airPojo.setAmountCrBeforeDemonetization(new BigDecimal("0"));
                airPojo.setAmountCrAfterDemonetization(new BigDecimal("0"));
                airPojo.setAccountRemarks("NIL");
                airPojo.setAccountRelationship("NIL");
                airPojo.setPersonName("NIL");
                airPojo.setPersonType("NIL");
                airPojo.setCustomerIdentity("NIL");
                airPojo.setGender("NIL");
                airPojo.setFatherName("NIL");
                airPojo.setPan("NIL");
                airPojo.setAadhaarNumber("NIL");
                airPojo.setForm60Acknowledgement("NIL");
                airPojo.setIdentificationType("NIL");
                airPojo.setIdentificationNumber("NIL");
                airPojo.setDobOrIncorporation("NIL");
                airPojo.setNationalityOrCountryOfIncorporation("NIL");
                airPojo.setBusinessOrOccupation("NIL");
                airPojo.setAddressType("NIL");
                airPojo.setAddress("NIL");
                airPojo.setCityOrTown("NIL");
                airPojo.setPinCode("NIL");
                airPojo.setState("NIL");
                airPojo.setCountry("NIL");
                airPojo.setPrimaryStdCode("NIL");
                airPojo.setPrimaryTelephoneNumber("NIL");
                airPojo.setPrimaryMobileNumber("NIL");
                airPojo.setSecondaryStdCode("NIL");
                airPojo.setSecondaryTelephoneNumber("NIL");
                airPojo.setSecondaryMobileNumber("NIL");
                airPojo.setEmail("NIL");
                airPojo.setRemarks("NIL");
                airAcc.add(airPojo);
            }

            return airAcc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public boolean isIterate(String acNature, double cr, double dr) {
        boolean isflag = true;
        if (acNature.equalsIgnoreCase("SFT003")) {
            isflag = true;
        } else {
            if ((cr != 0 || dr != 0)) {
                isflag = true;
            } else {
                isflag = false;
            }
        }

        return isflag;
    }

    public List<Form61APojo> form61AForTDAndPODDReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException {
        List<Form61APojo> airAcc = new ArrayList<Form61APojo>();
        try {

            String mainQuery = "", acNatureQuery = "", custIdPre = "", custIdWise = "", govtFlag = "";
            int reportSerialNumber = 0;
            List custIdWiseList = null;
            if (acNature.equalsIgnoreCase("SFT001")) {
                mainQuery = "select CUSTNAME,AMOUNT,INFAVOUROF,INSTNO,date_format(DT,'%d-%m-%Y') from bill_po where AMOUNT between " + fromAmt + " and " + toAmt + " and ORIGINDT between '" + fromDt + "' and '" + toDt + "' and  TRANTYPE='0'";
                List poDDDataList = em.createNativeQuery(mainQuery).getResultList();
                if (!poDDDataList.isEmpty()) {
                    for (int i = 0; i < poDDDataList.size(); i++) {
                        Form61APojo airPojo = new Form61APojo();
                        Vector data = (Vector) poDDDataList.get(i);
                        reportSerialNumber = reportSerialNumber + 1;

                        Integer originalReportSerialNumber = 0;
                        airPojo.setReportSerialNumber(reportSerialNumber);
                        airPojo.setOriginalReportSerialNumber(originalReportSerialNumber);
                        airPojo.setAccountRemarks("");
                        airPojo.setPersonName(data.get(0).toString().trim());
                        airPojo.setPersonType("");
                        airPojo.setCustomerIdentity("");
                        airPojo.setGender("");
                        airPojo.setFatherName("");
                        airPojo.setPan("");
                        airPojo.setAadhaarNumber("");
                        airPojo.setForm60Acknowledgement("");
                        airPojo.setIdentificationType("");
                        airPojo.setIdentificationNumber("");
                        airPojo.setDobOrIncorporation("");
                        airPojo.setNationalityOrCountryOfIncorporation("");
                        airPojo.setBusinessOrOccupation("");
                        airPojo.setAddressType("");
                        airPojo.setAddress("");
                        airPojo.setCityOrTown("");
                        airPojo.setPinCode("");
                        airPojo.setState("");
                        airPojo.setCountry("");
                        airPojo.setPrimaryStdCode("");
                        airPojo.setPrimaryTelephoneNumber("");
                        airPojo.setPrimaryMobileNumber("");
                        airPojo.setSecondaryStdCode("");
                        airPojo.setSecondaryTelephoneNumber("");
                        airPojo.setSecondaryMobileNumber("");
                        airPojo.setEmail("");
                        airPojo.setRemarks("");
                        airPojo.setProductType("");
                        airPojo.setAggGrossAmtReciveFromPerInCash(new BigDecimal(data.get(1).toString()));
                        airPojo.setAggGrossAmountReciveFromPer(new BigDecimal(data.get(1).toString()));
                        airPojo.setAggGrossAmtPaidToPer(new BigDecimal(data.get(1).toString()));
                        airPojo.setProductIdentifier(data.get(3).toString());
                        airPojo.setLastDtofTran(data.get(4).toString());

                        airAcc.add(airPojo);
                    }
                }

            } else if (acNature.equalsIgnoreCase("SFT005")) {
                mainQuery = "select custId,acno,PersonName,Gender,FatherName,Pan,AadhaarNo,Form60,VoterIDNo,DrivingLicenseNo,"
                        + " PassportNo,nrega_job_card,dob,incorPorationDt,dobPlace,incorPorationPlace,addressType,address,CityOrTown,PostalCode,StateCode,countoryCode,"
                        + " businessOrOccuptions,phnoneNo,MobileNo,faxNo,email,sum(AggCrAmtDurPd),sum(AggCrCashAmtDurPd),sum(AggDrAmtDurPd),acctnature,acctCategory,cnt from "
                        + "(select cast(ifnull(d.id,'') as decimal) as custId,\n"
                        + "d.ano as acno,\n"
                        + "concat(ifnull(cm.custname,''),\n"
                        + "if(ifnull(cm.middle_name,'')= '',\n"
                        + "ifnull(cm.middle_name,''),\n"
                        + "concat(' ', ifnull(cm.middle_name,''))),if(ifnull(cm.last_name,'')= '', ifnull(cm.last_name,''),\n"
                        + "concat(' ', ifnull(cm.last_name,'')))) as PersonName,\n"
                        + "ifnull(cm.gender,'X') as Gender,\n"
                        + "concat(ifnull(cm.fathername,''),\n"
                        + "if(ifnull(cm.FatherMiddleName,'')= '',\n"
                        + "ifnull(cm.FatherMiddleName,''),\n"
                        + "concat(' ', ifnull(cm.FatherMiddleName,''))),if(ifnull(cm.FatherLastName,'')= '', ifnull(cm.FatherLastName,''), concat(' ', ifnull(cm.FatherLastName,'')))) as FatherName, \n"
                        + "ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cm.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail  where customerid = cm.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = cm.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(cm.PAN_GIRNumber,'')) as Pan,\n"
                        + "ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cm.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from \n"
                        + " cbs_customer_master_detail where customerid = cm.customerid and\n"
                        + "legal_document = 'E' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = cm.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(cm.AADHAAR_NO,'')) as AadhaarNo,\n"
                        + "ifnull(cm.PAN_GIRNumber,'') as Form60,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cm.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cm.customerid and legal_document = 'D'\n"
                        + "and CustEntityType = '01'),'') as DrivingLicenseNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail\n"
                        + "where customerid = cm.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'')\n"
                        + "from  cbs_customer_master_detail where customerid = cm.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card,\n"
                        + "date_format(ifnull(cm.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,\n"
                        + "ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')  as incorPorationDt,\n"
                        + "'IN' as dobPlace,  'IN' as incorPorationPlace,   \n"
                        + "'1' as addressType,\n"
                        + "concat(ifnull(cm.PerAddressLine1,''),\n"
                        + "if(ifnull(cm.PerAddressLine2,'')='', '', concat(', ',ifnull(cm.PerAddressLine2,''))),\n"
                        + "if(ifnull(cm.PerVillage,'')='', '', concat(', ',ifnull(cm.PerVillage,''))),   if(ifnull(cm.PerBlock,'')='', '',\n"
                        + "concat(', ',ifnull(cm.PerBlock,''))),   if(ifnull(cm.PerCityCode,'')='', '',\n"
                        + "concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = cm.PerCityCode),''))),\n"
                        + "if(ifnull(cm.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'\n"
                        + "and REF_CODE = cm.PerStateCode),''))),   if(ifnull(cm.PerPostalCode,'')='', '', concat(', ',ifnull(cm.PerPostalCode,''))), ',INDIA' ) as address,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '011' and REF_CODE = cm.PerCityCode),'') as CityOrTown,\n"
                        + "if(ifnull(cm.PerPostalCode,'')='','XXXXXX',\n"
                        + "ifnull(cm.PerPostalCode,'XXXXXX')) as PostalCode,\n"
                        + "if(ifnull(cm.PerStateCode,'')='','XX',if(ifnull(cm.PerStateCode,'XX')='0','XX',\n"
                        + " ifnull(cm.PerStateCode,'XX'))) as StateCode,\n"
                        + "'IN' as countoryCode,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions,\n"
                        + "ifnull(cm.PerPhoneNumber,'') as phnoneNo,\n"
                        + "ifnull(cm.mobilenumber,'') as MobileNo,\n"
                        + "ifnull(cm.PerFaxNumber,'') as faxNo,  \n"
                        + "ifnull(cm.EmailID,'') as email,\n"
                        + "cast(sum(d.amt) as decimal) as AggCrAmtDurPd ,\n"
                        + "((\n"
                        + "select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from td_recon where acno in(select acno from customerid \n"
                        + "where CustId=d.id) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0  and trantype=0  and CloseFlag is null\n"
                        + " and acno = d.ano)+(\n"
                        + "select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from rdrecon where acno in(select acno from customerid \n"
                        + "where CustId=d.id) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0  and trantype=0  \n"
                        + "  and acno = d.ano)+(\n"
                        + "select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from ddstransaction where acno in(select acno from customerid \n"
                        + "where CustId=d.id) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0  and trantype=0  \n"
                        + "  and acno = d.ano)\n"
                        + ") as AggCrCashAmtDurPd,\n"
                        + "\n"
                        + "((select cast(ifnull(sum(ifnull(DrAmt,0)),0) as decimal) as DrAmt from td_recon where acno in(select acno from customerid \n"
                        + " where CustId=d.id) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and CloseFlag is null and not TranType='27' and details not like '%VCH New Receipt Renewal Entry%' "
                        + " AND intflag is null   and acno = d.ano)\n"
                        + "\n"
                        + "+(select cast(ifnull(sum(ifnull(DrAmt,0)),0) as decimal) as DrAmt from rdrecon where acno in(select acno from customerid \n"
                        + " where CustId=d.id) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1   and acno = d.ano)\n"
                        + "+(select cast(ifnull(sum(ifnull(DrAmt,0)),0) as decimal) as DrAmt from ddstransaction where acno in(select acno from customerid \n"
                        + "where CustId=d.id) and dt between '" + fromDt + "' and '" + toDt + "' and ty = 1   and acno = d.ano)\n"
                        + ") as AggDrAmtDurPd , \n"
                        + "(select acctnature from accounttypemaster where acctcode = substring(d.ano,3,2)) as acctnature,\n"
                        + " ifnull(ifnull(am.acctCategory,ta.acctCategory),'UN') as acctCategory  ,\n"
                        + "count(d.ano) as cnt \n"
                        + " from (\n"
                        + " select dd.id, dd.ano, sum(dd.amt) as amt from  "
                        + " (select bb.id, bb.ano, sum(bb.amt) as amt from  "
                        + "   (select c.custid as id,a.acno as ano, sum(a.cramt) as amt from td_recon a, "
                        + "   (select distinct acno from td_vouchmst where (PrevVoucherNo='' or PrevVoucherNo is null)) b, "
                        + "   customerid c where a.acno = b.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and "
                        + "   a.CloseFlag is null and a.TranType<>'27' and a.TranType<>'8' and not (a.details like '%VCH New Receipt%' or a.details like '%VCH New Receipt Renewal Entry%') "
                        + "   group by c.custid, a.acno"
                        + "   union all "
                        + "   select c.custid as id, a.acno as ano,sum(a.cramt) as amt from rdrecon a, customerid c "
                        + "   where a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and a.TranType<>'8' "
                        + "   group by c.custid, a.acno "
                        + "   union all "
                        + "   select c.custid as id,a.acno as ano, sum(a.cramt) as amt "
                        + "   from ddstransaction a, customerid c where a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                        + "   and a.ty = 0 and a.TranType<>'8' "
                        + "   group by c.custid, a.acno) bb group by bb.id,bb.ano ) dd, "
                        + "  (select aa.id, sum(aa.amt) as amt from "
                        + "    (select c.custid as id,a.acno as ano, sum(a.cramt) as amt from td_recon a, "
                        + "   (select distinct acno from td_vouchmst where (PrevVoucherNo='' or PrevVoucherNo is null)) b, "
                        + "   customerid c where a.acno = b.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and "
                        + "   a.CloseFlag is null and a.TranType<>'27' and a.TranType<>'8' and not (a.details like '%VCH New Receipt%' or a.details like '%VCH New Receipt Renewal Entry%') "
                        + "   group by c.custid, a.acno "
                        + "    union all "
                        + "   select c.custid as id, a.acno as ano,sum(a.cramt) as amt from rdrecon a, customerid c "
                        + "   where a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and a.TranType<>'8' "
                        + "   group by c.custid, a.acno "
                        + "   union all "
                        + "   select c.custid as id,a.acno as ano, sum(a.cramt) as amt "
                        + "   from ddstransaction a, customerid c where a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                        + "   and a.ty = 0 and a.TranType<>'8' "
                        + "   group by c.custid, a.acno) "
                        + "   aa group by aa.id having sum(aa.amt) between " + fromAmt + " and " + toAmt + ") cc where  dd.id = cc.id group by dd.id, dd.ano "
                        + ") as d  left join accountmaster am on am.acno = d.ano left join td_accountmaster ta on ta.acno = d.ano , cbs_customer_master_detail cm, branchmaster br, parameterinfo p, cbs_cust_misinfo g \n"
                        + "where d.id = cm.customerid and cast(cm.PrimaryBrCode as unsigned) = br.brncode and br.brncode = p.brncode\n"
                        + "and cm.customerid = g.customerid group by d.id, d.ano)ff group by ff.custId";

                custIdWiseList = em.createNativeQuery(mainQuery).getResultList();

                if (!custIdWiseList.isEmpty()) {
                    Integer originalReportSerialNumber = 1;
                    for (int i = 0; i < custIdWiseList.size(); i++) {
                        Form61APojo airPojo = new Form61APojo();
                        Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                        String custId = custIdWiseVector.get(0).toString();
                        String acNo = custIdWiseVector.get(1).toString();

                        if (custId.equalsIgnoreCase(custIdPre)) {
                            reportSerialNumber = reportSerialNumber;
                            originalReportSerialNumber = originalReportSerialNumber + 1;;
                        } else {
                            reportSerialNumber = reportSerialNumber + 1;
                            originalReportSerialNumber = 1;
                        }

                        String customerIdentity = custId;

                        String personName = custIdWiseVector.get(2).toString();
                        String accNoForIdentQuery = "select aa.ano,aa.id, "
                                + "(select acctnature from accounttypemaster where acctcode = substring(aa.ano,3,2)) as acctnature,\n"
                                + "ifnull((select acctCategory from td_accountmaster where acno = aa.ano),(select acctCategory from accountmaster where acno = aa.ano))\n"
                                + " as acctCategory"
                                + " from (\n"
                                + "select a.acno as ano,c.custid as id, sum(a.cramt) as amt from td_recon a,\n"
                                + "(select distinct acno from td_vouchmst where (PrevVoucherNo='' or PrevVoucherNo is null)) b,\n"
                                + "customerid c where a.acno = b.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and a.CloseFlag is null\n"
                                + "and a.TranType<>'27'  and a.TranType<>'8' and c.custid = '" + custId + "'\n"
                                + "group by a.acno,c.custid\n"
                                + "union all\n"
                                + "select a.acno as ano,c.custid as id, sum(a.cramt) as amt from rdrecon a, customerid c\n"
                                + "where a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and a.TranType<>'8' and c.custid = '" + custId + "' group by a.acno,c.custid\n"
                                + "union all\n"
                                + "select a.acno as ano,c.custid as id, sum(a.cramt) as amt from ddstransaction a, customerid c\n"
                                + "where a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and a.TranType<>'8' and c.custid = '" + custId + "'\n"
                                + "group by a.acno,c.custid)aa order by aa.ano";
                        String personType = "";
                        String productIdentifier = "";
                        if (Integer.parseInt(custIdWiseVector.get(32).toString()) > 1) {
                            List accList = em.createNativeQuery(accNoForIdentQuery).getResultList();
                            if (!accList.isEmpty()) {
                                productIdentifier = ((Vector) accList.get(0)).get(0).toString();
                                personType = ((Vector) accList.get(0)).get(3).toString();
                            }
                        } else if (Integer.parseInt(custIdWiseVector.get(32).toString()) == 1) {
                            productIdentifier = custIdWiseVector.get(1).toString();
                            personType = custIdWiseVector.get(31).toString();
                        }
                        if (personType.equalsIgnoreCase("IND")) {
                            personType = "IN";
                        } else if (personType.equalsIgnoreCase("PC")) {
                            personType = "SP";
                        } else if (personType.equalsIgnoreCase("PF")) {
                            personType = "PF";
                        } else if (personType.equalsIgnoreCase("HUF")) {
                            personType = "HF";
                        } else if (personType.equalsIgnoreCase("PCS") || personType.equalsIgnoreCase("PL")) {
                            personType = "CR";
                        } else if (personType.equalsIgnoreCase("CGD") || personType.equalsIgnoreCase("CGP") || personType.equalsIgnoreCase("SGD")
                                || personType.equalsIgnoreCase("SGP") || personType.equalsIgnoreCase("LC")) {
                            personType = "CB";
                        } else if (personType.equalsIgnoreCase("COS") || personType.equalsIgnoreCase("RS")) {
                            personType = "SO";
                        } else if (personType.equalsIgnoreCase("LB") || personType.equalsIgnoreCase("AB")) {
                            personType = "AO";
                        } else if (personType.equalsIgnoreCase("TG")) {
                            personType = "TR";
                        } else if (personType.equalsIgnoreCase("JLG")) {
                            personType = "LL";
                        } else if (personType.equalsIgnoreCase("UN")) {
                            personType = "XX";
                        } else {
                            personType = "ZZ";
                        }

//                    String accountStatus = custIdWiseVector.get(4).toString();
//                    if (accountStatus.equalsIgnoreCase("1")) {
//                        accountStatus = "A";
//                    } else if (accountStatus.equalsIgnoreCase("9")) {
//                        accountStatus = "C";
//                    } else {
//                        accountStatus = "Z";
//                    }

                        String gender = custIdWiseVector.get(3).toString();
                        if (gender.equalsIgnoreCase("M")) {
                            gender = "M";
                        } else if (gender.equalsIgnoreCase("F")) {
                            gender = "F";
                        } else if (gender.equalsIgnoreCase("O")) {
                            gender = "O";
                        } else {
                            gender = "X";
                        }

                        String accountRemarks = "";


                        String fatherName = custIdWiseVector.get(4).toString();
                        String pan = custIdWiseVector.get(5).toString().trim().toUpperCase();
                        String flag = "Y";
                        if (pan.length() == 10) {
                            if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                                flag = "N";
                            }
                            if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                                flag = "N";
                            }
                            if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                                flag = "N";
                            }
                            if (!flag.equalsIgnoreCase("Y")) {
                                pan = "";
                            }
                        } else if (pan.length() > 0) {
                            pan = "";
                        } else {
                            pan = "";
                        }
                        String aadhaarNumber = custIdWiseVector.get(6).toString().trim();
                        String form60Acknowledgement = custIdWiseVector.get(7).toString().trim().equalsIgnoreCase("Form 60") ? "Form 60" : "";//custIdWiseVector.get(27).toString();

                        if ((custIdWiseVector.get(7).toString().trim().length() == 10) && (!custIdWiseVector.get(7).toString().equalsIgnoreCase("Form 60")) && (pan.equalsIgnoreCase(""))) {
                            pan = custIdWiseVector.get(7).toString().trim().toUpperCase();
                            if (pan.length() == 10) {
                                flag = "Y";
                                if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                                    flag = "N";
                                }
                                if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                                    flag = "N";
                                }
                                if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                                    flag = "N";
                                }
                                if (!flag.equalsIgnoreCase("Y")) {
                                    pan = "";
                                }
                            } else if (pan.length() > 0) {
                                pan = "";
                            } else {
                                pan = "";
                            }
                        }

                        /*  **********************
                         A - Passport
                         B - Election Id Card
                         C - PAN Card
                         D - ID Card issued by Government/PSU 
                         E - Driving License
                         G - UIDAI Letter / Aadhaar Card
                         H - NREGA job card
                         Z – Others
                         */

                        String identificationType = "", identificationNumber = "";
                        if (!custIdWiseVector.get(8).toString().equalsIgnoreCase("")) {//Voter Id
                            identificationType = "B";
                            identificationNumber = custIdWiseVector.get(8).toString();
                        } else if (!custIdWiseVector.get(9).toString().equalsIgnoreCase("")) {//Driving License
                            identificationType = "E";
                            identificationNumber = custIdWiseVector.get(9).toString();
                        } else if (!custIdWiseVector.get(10).toString().equalsIgnoreCase("")) {//Passport
                            identificationType = "A";
                            identificationNumber = custIdWiseVector.get(10).toString();
                        } else if (!custIdWiseVector.get(11).toString().equalsIgnoreCase("")) {//Narega
                            identificationType = "H";
                            identificationNumber = custIdWiseVector.get(11).toString();
                        } else if (!custIdWiseVector.get(6).toString().equalsIgnoreCase("")) {//Aadhaar
                            identificationType = "G";
                            identificationNumber = custIdWiseVector.get(6).toString();
                        } else if (!pan.equalsIgnoreCase("")) {
                            if (!pan.equalsIgnoreCase("Form 60")) {
                                identificationType = "C";
                                identificationNumber = pan;
                            }
                        }

                        String dobOrIncorporation = custIdWiseVector.get(12).toString();
                        String nationalityOrCountryOfIncorporation = custIdWiseVector.get(15).toString().trim();
                        if (personType.equalsIgnoreCase("IN")) {
                            dobOrIncorporation = dobOrIncorporation;
                            nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                        } else if (personType.equalsIgnoreCase("XX")) {
                            dobOrIncorporation = dobOrIncorporation;
                            nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                        } else if (personType.equalsIgnoreCase("HF")) {
                            if (!dobOrIncorporation.equalsIgnoreCase("")) {
                                dobOrIncorporation = dobOrIncorporation;
                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                            } else if (!custIdWiseVector.get(12).toString().equalsIgnoreCase("")) {
                                dobOrIncorporation = custIdWiseVector.get(12).toString();
                                nationalityOrCountryOfIncorporation = custIdWiseVector.get(15).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(15).toString();
                            } else {
                                dobOrIncorporation = "01-01-0001";
                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                            }
                        } else {
                            dobOrIncorporation = custIdWiseVector.get(12).toString();
                            nationalityOrCountryOfIncorporation = custIdWiseVector.get(15).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(15).toString();
                        }

                        String addressType = custIdWiseVector.get(16).toString();
                        String address = custIdWiseVector.get(17).toString();
                        String cityOrTown = custIdWiseVector.get(18).toString();
                        String pinCode = custIdWiseVector.get(19).toString();
                        String state = custIdWiseVector.get(20).toString();
                        String country = custIdWiseVector.get(21).toString();
                        String businessOrOccupation = custIdWiseVector.get(22).toString();

                        String primaryStdCode = custIdWiseVector.get(23).toString().contains("-") ? custIdWiseVector.get(23).toString().split("-").length > 0 ? custIdWiseVector.get(23).toString().split("-")[0] : "" : "";
                        String primaryTelephoneNumber = custIdWiseVector.get(23).toString().contains("-") ? custIdWiseVector.get(23).toString().split("-").length > 1 ? custIdWiseVector.get(23).toString().split("-")[1] : "" : "";
                        String primaryMobileNumber = custIdWiseVector.get(24).toString().length() != 10 ? "" : custIdWiseVector.get(24).toString();

                        String secondaryStdCode = custIdWiseVector.get(23).toString().contains("-") ? custIdWiseVector.get(23).toString().split("-").length > 0 ? custIdWiseVector.get(23).toString().split("-")[0] : "" : "";
                        String secondaryTelephoneNumber = custIdWiseVector.get(23).toString().contains("-") ? custIdWiseVector.get(23).toString().split("-").length > 1 ? custIdWiseVector.get(23).toString().split("-")[1] : "" : "";
                        String secondaryMobileNumber = custIdWiseVector.get(24).toString().length() != 10 ? "" : custIdWiseVector.get(24).toString();


                        String email = custIdWiseVector.get(26).toString();
                        String remarks = "";
                        // TD - Time Deposit//
                        String productType = "";
                        if (acNature.equalsIgnoreCase("SFT005")) {
                            productType = "TD";
                        }
                        BigDecimal amountRecefrmPer = new BigDecimal(custIdWiseVector.get(27).toString());
                        BigDecimal amountRecefrmPerInCash = new BigDecimal(custIdWiseVector.get(28).toString());
                        BigDecimal amountPaidToPer = new BigDecimal(custIdWiseVector.get(29).toString());
                        String tranRemark = "";

                        String lastTaranDateQuery =
                                /*"select ifnull(date_format(max(tranDate),'%d-%m-%Y'),'') as lastTranDate from(\n"
                                 + "select TranTime as tranDate from td_recon where acno in (select acno from customerid where custId='" + custId + "') and TranType<>'8' TranTime<\n"
                                 + "union all\n"
                                 + "select TranTime as tranDate from rdrecon where acno in (select acno from customerid where custId='" + custId + "') and TranType<>'8'\n"
                                 + "union all\n"
                                 + "select TranTime as tranDate from rdrecon where acno in (select acno from customerid where custId='" + custId + "') and TranType<>'8' \n"
                                 + ") as tranDatetable;";*/
                                "select ifnull(date_format(max(tranDate),'%d-%m-%Y'),'') as lastTranDate from(\n"
                                + "select Dt as tranDate from td_recon where acno in (select acno from customerid where custId='" + custId + "') and TranType<>'8' and TranType<>'27'  and TranType<>'8' \n"
                                + "and not (details = 'VCH New Receipt' or details = 'VCH New Receipt Renewal Entry') and Dt<='" + toDt + "'\n"
                                + "union all\n"
                                + "select Dt as tranDate from rdrecon where acno in (select acno from customerid where custId='" + custId + "') and TranType<>'8' and Dt<='" + toDt + "'\n"
                                + "union all\n"
                                + "select Dt as tranDate from ddstransaction where acno in (select acno from customerid where custId='" + custId + "') and TranType<>'8' and Dt<='" + toDt + "'\n"
                                + ") as tranDatetable;";

                        String LastDtofTrans = "";
                        List tranDate = em.createNativeQuery(lastTaranDateQuery).getResultList();
                        if (!tranDate.isEmpty()) {
                            LastDtofTrans = ((Vector) tranDate.get(0)).get(0).toString();
                        }
                        airPojo.setReportSerialNumber(reportSerialNumber);
                        airPojo.setOriginalReportSerialNumber(originalReportSerialNumber);
                        airPojo.setAccountRemarks(accountRemarks);
                        airPojo.setPersonName(personName.trim());
                        airPojo.setPersonType(personType);
                        airPojo.setCustomerIdentity(customerIdentity);
                        airPojo.setGender(gender);
                        airPojo.setFatherName(fatherName.trim());
                        airPojo.setPan(pan.trim());
                        airPojo.setAadhaarNumber(aadhaarNumber.trim());
                        airPojo.setForm60Acknowledgement(form60Acknowledgement.trim());
                        airPojo.setIdentificationType(identificationType);
                        airPojo.setIdentificationNumber(identificationNumber.trim());
                        airPojo.setDobOrIncorporation(dobOrIncorporation);
                        airPojo.setNationalityOrCountryOfIncorporation(nationalityOrCountryOfIncorporation);
                        airPojo.setBusinessOrOccupation(businessOrOccupation);
                        airPojo.setAddressType(addressType);
                        airPojo.setAddress(address.trim());
                        airPojo.setCityOrTown(cityOrTown.trim());
                        airPojo.setPinCode(pinCode.trim());
                        airPojo.setState(state);
                        airPojo.setCountry(country);
                        airPojo.setPrimaryStdCode(primaryStdCode.trim());
                        airPojo.setPrimaryTelephoneNumber(primaryTelephoneNumber.trim());
                        airPojo.setPrimaryMobileNumber(primaryMobileNumber.trim());
                        airPojo.setSecondaryStdCode(secondaryStdCode.trim());
                        airPojo.setSecondaryTelephoneNumber(secondaryTelephoneNumber.trim());
                        airPojo.setSecondaryMobileNumber(secondaryMobileNumber.trim());
                        airPojo.setEmail(email.trim());
                        airPojo.setRemarks(remarks);
                        airPojo.setProductType(productType);
                        airPojo.setAggGrossAmtReciveFromPerInCash(amountRecefrmPerInCash);
                        airPojo.setAggGrossAmountReciveFromPer(amountRecefrmPer);
                        airPojo.setAggGrossAmtPaidToPer(amountPaidToPer);
                        airPojo.setProductIdentifier(productIdentifier);
                        airPojo.setLastDtofTran(LastDtofTrans);
                        airPojo.setRelatedAccNo(acNo);

                        airAcc.add(airPojo);
                        custIdPre = custId;

                    }
                }
            }
            if (airAcc.isEmpty()) {
                Form61APojo airPojo = new Form61APojo();
                airPojo.setReportSerialNumber(1);
                airPojo.setOriginalReportSerialNumber(0);
                airPojo.setAccountType("NIL");
                airPojo.setAccountNumber("NIL");
                airPojo.setAccountHolderName("NIL");
                airPojo.setAccountStatus("NIL");
                airPojo.setBranchReferenceNumber("NIL");
                airPojo.setBranchName("NIL");
                airPojo.setBranchAddress("NIL");
                airPojo.setBranchCityTown("NIL");
                airPojo.setBranchPostalCode("NIL");
                airPojo.setBranchState("NIL");
                airPojo.setBranchCountry("NIL");
                airPojo.setBranchSTDCode("NIL");
                airPojo.setBranchPhoneNumber("NIL");
                airPojo.setBranchMobileNumber("NIL");
                airPojo.setBranchbFaxSTDCode("NIL");
                airPojo.setBranchFaxPhoneNo("NIL");
                airPojo.setBranchEmail("NIL");
                airPojo.setBranchRemarks("NIL");
                airPojo.setAggGrossAmountCrCash(new BigDecimal("0"));
                airPojo.setAggGrossAmountDrCash(new BigDecimal("0"));
                airPojo.setAmountCrBeforeDemonetization(new BigDecimal("0"));
                airPojo.setAmountCrAfterDemonetization(new BigDecimal("0"));
                airPojo.setAccountRemarks("NIL");
                airPojo.setAccountRelationship("NIL");
                airPojo.setPersonName("NIL");
                airPojo.setPersonType("NIL");
                airPojo.setCustomerIdentity("NIL");
                airPojo.setGender("NIL");
                airPojo.setFatherName("NIL");
                airPojo.setPan("NIL");
                airPojo.setAadhaarNumber("NIL");
                airPojo.setForm60Acknowledgement("NIL");
                airPojo.setIdentificationType("NIL");
                airPojo.setIdentificationNumber("NIL");
                airPojo.setDobOrIncorporation("NIL");
                airPojo.setNationalityOrCountryOfIncorporation("NIL");
                airPojo.setBusinessOrOccupation("NIL");
                airPojo.setAddressType("NIL");
                airPojo.setAddress("NIL");
                airPojo.setCityOrTown("NIL");
                airPojo.setPinCode("NIL");
                airPojo.setState("NIL");
                airPojo.setCountry("NIL");
                airPojo.setPrimaryStdCode("NIL");
                airPojo.setPrimaryTelephoneNumber("NIL");
                airPojo.setPrimaryMobileNumber("NIL");
                airPojo.setSecondaryStdCode("NIL");
                airPojo.setSecondaryTelephoneNumber("NIL");
                airPojo.setSecondaryMobileNumber("NIL");
                airPojo.setEmail("NIL");
                airPojo.setRemarks("NIL");
                airAcc.add(airPojo);
            }
            return airAcc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
}
