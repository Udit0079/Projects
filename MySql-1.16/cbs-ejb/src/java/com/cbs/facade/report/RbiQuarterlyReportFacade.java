/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.AtmMasterGrid;
import com.cbs.dto.DividendTable;
import com.cbs.dto.report.LienReportPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.BankProfilePojo;
import com.cbs.dto.report.ho.OrganisationalPojo;
import com.cbs.dto.report.ho.Oss7BusinessPojo;
import com.cbs.dto.report.ho.Oss8BusinessPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.ho.share.DividendCalculationFacadeRemote;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.pojo.ManagementDetailsPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiQuarterlyReportFacade")
public class RbiQuarterlyReportFacade implements RbiQuarterlyReportFacadeRemote {

    @EJB
    private GLReportFacadeRemote glReport;
    @EJB
    private RbiReportFacadeRemote rbiReportRemote;
    @EJB
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2ReportRemote;
    @EJB
    private CertIssueFacadeRemote certRemote;
    @EJB
    private DividendCalculationFacadeRemote divRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private HoReportFacadeRemote hoReportFacade;
    @EJB
    private RbiOss8ReportFacadeRemote rbiOss8Facade;
    @EJB
    LoanReportFacadeRemote loanRemote;
    @EJB
    private LoanReportFacadeRemote loanReportFacade;
    @EJB
    private NpaReportFacadeRemote npaRemote;
    @EJB
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    @PersistenceContext
    private EntityManager em;
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat d_m_y = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public List<AtmMasterGrid> getAtmProfileData() throws ApplicationException {
        List<AtmMasterGrid> dataList = new ArrayList<AtmMasterGrid>();
        try {
            List list = em.createNativeQuery("select ifnull(atm_name,''),ifnull(atm_location,''),ifnull(atm_state,''),ifnull(atm_city,''),ifnull(location_type,'') ,"
                    + "ifnull(atm_site,'') from atm_master where atm_active_status='A'").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    AtmMasterGrid pojo = new AtmMasterGrid();
                    Vector element = (Vector) list.get(i);
                    pojo.setAtmName(element.get(0).toString());
                    pojo.setAtmAddress(element.get(1).toString());
                    pojo.setState(element.get(2).toString());
                    pojo.setCity(element.get(3).toString());
                    pojo.setLocationType(element.get(4).toString());
                    pojo.setSite(element.get(5).toString());

                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<BankProfilePojo> getBankProfileData(Integer brnCode, BigDecimal repOpt, String reportDt) throws ApplicationException {
        List<BankProfilePojo> dataList = new ArrayList<BankProfilePojo>();
        try {
            List list = em.createNativeQuery("select company_name as bankname,address as regofficeadd,pin as regofficepin ,"
                    + "ho_address,ho_pin,company_reg as status,cstno as bankcategory,rbi_rating,ist_no as subcategory,"
                    + "bank_region,date_format(license_date,'%d/%m/%Y'),lst_no as licenceno,fin_year_from as lastinspectiondt,"
                    + "books_begining_from as agmdt,total_regular_members,total_nominal_members,"
                    + "date_format(last_int_audit_date,'%d/%m/%Y'),major_irregularities_no,minor_irregularities_no,"
                    + "paras_fully_no,paras_outstanding_no,ifnull(bankTier,''),ifnull(population,0),ifnull(extCounter,0)"
                    + " ,ifnull(debitCardNo,0),ifnull(currencyChest,0),ifnull(noFrillAcc,0),ifnull(courtCasePending,0)"
                    + " ,ifnull(crimComplAgstDirector,0),ifnull(crimComplAgstOthr,0) from company_master where "
                    + " company_code = " + brnCode + "  and  EFFECT_DT = (select max(EFFECT_DT) from company_master where company_code ='" + brnCode + "' and EFFECT_DT < '"+reportDt+"') ").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    BankProfilePojo pojo = new BankProfilePojo();
                    Vector element = (Vector) list.get(i);
                    pojo.setBankname(element.get(0).toString());
                    pojo.setRegOfficeAdd(element.get(1).toString());
                    pojo.setRegOfficePin(element.get(2).toString());
                    pojo.setHoOfficeAdd(element.get(3).toString());
                    pojo.setHoOfficePin(element.get(4).toString());
                    pojo.setStatus(element.get(5).toString());
                    pojo.setBankCategory(element.get(6).toString());
                    pojo.setRbiRating(element.get(7).toString());
                    pojo.setBankSubCategory(element.get(8).toString());
                    pojo.setBankRegion(element.get(9).toString());
                    pojo.setLicenceDate(element.get(10).toString());
                    pojo.setLicenceNo(element.get(11).toString());
                    pojo.setLastInsPectionDt(element.get(12).toString());
                    pojo.setLastAgmDate(element.get(13).toString());
                    pojo.setTotRegMembers(Integer.parseInt(element.get(14).toString()));
                    pojo.setTotNomMembers(Integer.parseInt(element.get(15).toString()));
                    pojo.setLastInternalAuditDt(element.get(16).toString());
                    pojo.setMajorIrregularities(new BigDecimal(element.get(17).toString()).divide(repOpt));
                    pojo.setMinorIrregularities(new BigDecimal(element.get(18).toString()).divide(repOpt));
                    pojo.setParasFullyNo(new BigDecimal(element.get(19).toString()).divide(repOpt));
                    pojo.setParasOutStandNo(new BigDecimal(element.get(20).toString()).divide(repOpt));
                    pojo.setBankTier(element.get(21).toString());
                    pojo.setPopulation(element.get(22).toString());
                    pojo.setExtCounter(element.get(23).toString());
                    pojo.setDebitCardNo(element.get(24).toString());
                    pojo.setCurrencyChest(element.get(25).toString());
                    pojo.setNoFrillAcc(element.get(26).toString());
                    pojo.setCourtCasePending(element.get(27).toString());
                    pojo.setCrimComplAgstDirector(element.get(28).toString());
                    pojo.setCrimComplAgstOthr(element.get(29).toString());

                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<OrganisationalPojo> getOrganisationalProfile(String dt, BigDecimal repOpt) throws ApplicationException {
        List<OrganisationalPojo> dataList = new ArrayList<OrganisationalPojo>();
        try {
            List list = em.createNativeQuery("select ifnull(branchname,''),ifnull(address,''),ifnull(pincode,0),ifnull(state,''),"
                    + "ifnull(city,''),ifnull(location_type,'URBAN'),ifnull(computerized_status,'N'),ifnull(brncode,0) from branchmaster "
                    + "order by brncode").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    OrganisationalPojo pojo = new OrganisationalPojo();
                    Vector element = (Vector) list.get(i);
                    pojo.setName(element.get(0).toString());
                    pojo.setAddress(element.get(1).toString());
                    pojo.setPin(element.get(2).toString());
                    pojo.setState(element.get(3).toString());
                    pojo.setDistrict(element.get(4).toString());
                    pojo.setLocationType(element.get(5).toString());
                    pojo.setComputerizedStatus(element.get(6).toString());

                    String brncode = element.get(7).toString();
                    brncode = brncode.length() < 2 ? "0" + brncode : brncode;
                    Double pl = glReport.brWiseIncomeExp(dt, brncode);
                    pojo.setBranchPL(new BigDecimal(pl).divide(repOpt));

                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<ManagementDetailsPojo> getManagementDetails(String reportDt) throws ApplicationException {
        List<ManagementDetailsPojo> dataList = new ArrayList<ManagementDetailsPojo>();
        try {            
            List list = em.createNativeQuery("select ifnull(name,'') as name,ifnull(rem_desg,'') as designation,"
                    + "concat(ifnull(addrline1,''),' ',ifnull(addrline2,''),' ',ifnull(village,''),' ',ifnull(block,''),' ', "
                    + "ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.citycode),''),' ', "
                    + " ifnull(a.statecode,''),' ', ifnull(countrycode,'')) as address, "
                    + "ifnull(postalcode,'') as pin,ifnull(emailid,'') as email,ifnull(joindt,'') as termsince, "
                    + "ifnull(expdt,'') as termupto,ifnull(PhoneNumber,'') from banks_dir_details a where desg='DIR' and ('"+reportDt+"' between JoinDt and expDt ) and status ='A'"
                    + " union all "
                    + "select ifnull(name,'') as name,ifnull(rem_desg,'') as designation,"
                    + "concat(ifnull(addrline1,''),' ',ifnull(addrline2,''),' ',ifnull(village,''),' ',ifnull(block,''),' ', "
                    + "ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.citycode),''),' ', "
                    + "ifnull(a.statecode,'') ,' ',ifnull(countrycode,'')) as address,"
                    + "ifnull(postalcode,'') as pin,ifnull(emailid,'') as email,ifnull(joindt,'') as termsince,"
                    + "ifnull(expdt,'') as termupto,ifnull(PhoneNumber,'') from banks_dir_details_his a where desg='DIR' and ('"+reportDt+"' between JoinDt and expDt )").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    ManagementDetailsPojo pojo = new ManagementDetailsPojo();
                    Vector element = (Vector) list.get(i);
                    pojo.setTitle("");
                    pojo.setName(element.get(0).toString());
                    pojo.setDesg(element.get(1).toString());
                    pojo.setGender("");
                    pojo.setEdu_details("");
                    pojo.setAddrLine1(element.get(2).toString());
                    pojo.setAddrLine2("");
                    pojo.setVillage("");
                    pojo.setBlock("");
                    pojo.setCityCode("");
                    pojo.setStateCode("");
                    pojo.setPostalCode(element.get(3).toString());
                    pojo.setCountryCode("");
                    pojo.setPhoneNumber(element.get(7).toString());
                    pojo.setEmailID(element.get(4).toString());
                    pojo.setJoinDt(d_m_y.format(y_m_d.parse(element.get(5).toString())));
                    pojo.setExpDt(d_m_y.format(y_m_d.parse(element.get(6).toString())));
                    pojo.setStatus("");
                    pojo.setEnterby("");
                    pojo.setSno(0);
                    pojo.setRem_desg("");

                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<Oss8BusinessPojo> getOss8Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, int noOfEmp, double dividend, String reportOption) throws ApplicationException {
        List<Oss8BusinessPojo> dataList = new ArrayList<Oss8BusinessPojo>();
        try {
            /*Calling all the sub report and creating the list*/
            List<AtmMasterGrid> atmProfileData = getAtmProfileData();
            List<BankProfilePojo> bankProfileData = getBankProfileData(Integer.parseInt(orgnBrCode), repOpt, reportDt);
            List<OrganisationalPojo> organisationalProfileData = getOrganisationalProfile(reportDt, repOpt);

            List<ManagementDetailsPojo> managementData = getManagementDetails(reportDt);
            List<String> dates = new ArrayList<String>();
            dates.add(reportDt);
            List<RbiSossPojo> financialDetail = rbiOss8Facade.getOss8Detail(reportName,dates, orgnBrCode, repOpt, noOfEmp, dividend, reportOption);
            int index = getIndex(financialDetail, "Section F");

            List<RbiSossPojo> finOneList = financialDetail.subList(0, index);
            List<RbiSossPojo> finTwoList = financialDetail.subList(index, financialDetail.size());

            /*Creating an object of all sub report list*/

            Oss8BusinessPojo pojo = new Oss8BusinessPojo();
            pojo.setAtmProfile(atmProfileData);
            pojo.setBankProfile(bankProfileData);

            pojo.setOrganisationalProfile(organisationalProfileData);
            pojo.setManagementProfile(managementData);

            pojo.setFinOneProfile(finOneList);
            pojo.setFinTwoProfile(finTwoList);

            /*Addition of object into returning list*/
            dataList.add(pojo);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
    
    public List<Oss8BusinessPojo> getXBRL8Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, int noOfEmp, double dividend, String reportOption) throws ApplicationException {
        List<Oss8BusinessPojo> dataList = new ArrayList<Oss8BusinessPojo>();
        try {
            /*Calling all the sub report and creating the list*/            
            List<BankProfilePojo> bankProfileData = getBankProfileData(Integer.parseInt(orgnBrCode), repOpt,reportDt);
            List<ManagementDetailsPojo> managementData = getManagementDetails(reportDt);
            List<AtmMasterGrid> atmProfileData = getAtmProfileData();
            
            List<OrganisationalPojo> organisationalProfileData = getOrganisationalProfile(reportDt, repOpt);
            
            List<String> dates = new ArrayList<String>();
            dates.add(reportDt);
            List<RbiSossPojo> financialDetail = rbiOss8Facade.getOss8Detail(reportName ,dates, orgnBrCode, repOpt, noOfEmp, dividend, reportOption);
            int index = getIndex(financialDetail, "Ratio Denominators");

            List<RbiSossPojo> finOneList = financialDetail.subList(0, index);
            List<RbiSossPojo> finTwoList = financialDetail.subList(index, financialDetail.size());
            
//            List<AnnualNPAStmtConsolidate> annualNPAList = hoReportFacade.getAnnualNPAStmt(orgnBrCode, reportDt, repOpt,"OSS8_NPA");

            /*Creating an object of all sub report list*/

            Oss8BusinessPojo pojo = new Oss8BusinessPojo();
            pojo.setAtmProfile(atmProfileData);
            pojo.setBankProfile(bankProfileData);

            pojo.setOrganisationalProfile(organisationalProfileData);
            pojo.setManagementProfile(managementData);

            pojo.setFinOneProfile(finOneList);
            pojo.setFinTwoProfile(finTwoList);
//            pojo.setNpaProfile(annualNPAList);
            
//            AnnualNPAStmtConsolidate mainPojo = new AnnualNPAStmtConsolidate();
//            pojo.setMainList(annualNPAList.get(0).getMainList());
//            pojo.setPartBList(annualNPAList.get(0).getPartBList());

            /*Addition of object into returning list*/
            dataList.add(pojo);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
    private int getIndex(List<RbiSossPojo> finList, String desc) throws ApplicationException {
        try {
            for (int i = 0; i < finList.size(); i++) {
                RbiSossPojo pojo = finList.get(i);
                if (pojo.getDescription().trim().equalsIgnoreCase(desc)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<RbiSossPojo> getOss7PartBAndPartCSec2(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, String reportFormat, List osBlancelist) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        try {
            String npaAcDetails = "";
            BigDecimal hundred = new BigDecimal("100");
//            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", reportDt, "", orgnCode.equalsIgnoreCase("90") ? "0A" : orgnCode,"Y");
            List list = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, "
                    + "classification, count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, "
                    + "ifnull(range_from,0), ifnull(range_to,0), formula, f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, "
                    + "npa_classification,REFER_INDEX,REFER_CONTENT from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
            if (list.isEmpty()) {
                RbiSossPojo rbiSossPojo = new RbiSossPojo();
                rbiSossPojo.setsNo(1);
                rbiSossPojo.setReportName("");
                rbiSossPojo.setDescription("");
                rbiSossPojo.setgNo(1);
                rbiSossPojo.setsGNo(0);
                rbiSossPojo.setSsGNo(0);
                rbiSossPojo.setSssGNo(0);
                rbiSossPojo.setSsssGNo(0);
                rbiSossPojo.setClassification("");
                rbiSossPojo.setCountApplicable("");
                rbiSossPojo.setAcNature("");
                rbiSossPojo.setAcType("");
                rbiSossPojo.setNpaClassification("");
                rbiSossPojo.setGlheadFrom("");
                rbiSossPojo.setGlHeadTo("");
                rbiSossPojo.setRangeBaseOn("");
                rbiSossPojo.setRangeFrom("");
                rbiSossPojo.setRangeTo("");
                rbiSossPojo.setFormula("");
                rbiSossPojo.setfGNo("");
                rbiSossPojo.setfSGNo("");
                rbiSossPojo.setfSsGNo("");
                rbiSossPojo.setfSssGNo("");
                rbiSossPojo.setfSsssGNo("");
                rbiSossPojo.setAmt(new BigDecimal("0"));            //Book Value-->Amount
                rbiSossPojo.setSecondAmount(new BigDecimal("0"));   //Margins And Provisions-->Amount
                rbiSossPojo.setThirdAmount(new BigDecimal("0"));    //Book Value Net-->Amount
                rbiSossPojo.setFourthAmount(new BigDecimal("0"));   //Risk Weight Percentage-->Percentage
                rbiSossPojo.setFifthAmount(new BigDecimal("0"));    //Risk Adjusted Value-->Amount

                dataList.add(rbiSossPojo);
            } else {
                String preFormula = "";
                for (int i = 0; i < list.size(); i++) {
                    RbiSossPojo rbiSossPojo = new RbiSossPojo();
                    Vector element = (Vector) list.get(i);
                    Integer sNo = Integer.parseInt(element.get(0).toString());
//                    System.out.println("Part Soss7:Part B>>>"+i+":"+sNo);
                    String reportName = element.get(1).toString();
                    String description = element.get(2).toString();
                    Integer gNo = Integer.parseInt(element.get(3).toString());
                    Integer sGNo = Integer.parseInt(element.get(4).toString());
                    Integer ssGNo = Integer.parseInt(element.get(5).toString());
                    Integer sssGNo = Integer.parseInt(element.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(element.get(7).toString());
                    String classification = element.get(8).toString();
                    String countApplicable = element.get(9).toString();
                    String acNature = element.get(10).toString();
                    String acType = element.get(11).toString();
                    String glHeadFrom = element.get(12).toString();
                    String glHeadTo = element.get(13).toString();
                    String rangeBaseOn = element.get(14).toString();
                    String rangeFrom = element.get(15).toString();
                    String rangeTo = element.get(16).toString();
                    String formula = element.get(17).toString();
                    String fGNo = element.get(18).toString();
                    String fSGNo = element.get(19).toString();
                    String fSsGNo = element.get(20).toString();
                    String fSssGNo = element.get(21).toString();                    //Margin Percentage
                    String fSsssGNo = element.get(22).toString();                   //Risk Weight Percentage
                    String npaClassification = element.get(23).toString();
                    String referIndex = element.get(24).toString();
                    String referContent = element.get(25).toString();
                    /*As it is insertion of heading in the RBI MASTER*/
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setgNo(gNo);
                    rbiSossPojo.setsGNo(sGNo);
                    rbiSossPojo.setSsGNo(ssGNo);
                    rbiSossPojo.setSssGNo(sssGNo);
                    rbiSossPojo.setSsssGNo(ssssGNo);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    rbiSossPojo.setAmt(new BigDecimal("0"));            //Book Value-->Amount
                    rbiSossPojo.setSecondAmount(new BigDecimal("0"));   //Margins And Provisions-->Amount
                    rbiSossPojo.setThirdAmount(new BigDecimal("0"));    //Book Value Net-->Amount
                    rbiSossPojo.setFourthAmount(new BigDecimal("0"));   //Risk Weight Percentage-->Percentage
                    rbiSossPojo.setFifthAmount(new BigDecimal("0"));    //Risk Adjusted Value-->Amount

//                    dataList.add(rbiSossPojo);
                    /*Report Processing*/
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(reportDt);
                    params.setToDate(reportDt);
                    params.setFromRange(rangeFrom);
                    params.setGlFromHead(glHeadFrom);
                    params.setGlToHead(glHeadTo);
                    params.setNature(acNature);
                    params.setOrgBrCode(orgnCode);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setToRange(rangeTo);
                    params.setFlag(fSGNo);
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setgNo(preGNo);
                            rbiSossPojo.setsGNo(preSGNo);
                            rbiSossPojo.setSsGNo(preSsGNo);
                            rbiSossPojo.setSssGNo(preSssGNo);
                            rbiSossPojo.setSsssGNo(preSsssGNo);

                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);

                            dataList.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                            preZ = 0;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);

                        dataList.add(rbiSossPojo);
                    }
//                    params.setNpaAcList(resultList);

                    List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                    AcctBalPojo acctBal = new AcctBalPojo();
                    if (!(acNature == null || acNature.equalsIgnoreCase(""))) {
                        List natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                + "acctnature ='" + params.getNature() + "'").getResultList();
                        for (int n = 0; n < natureList.size(); n++) {
                            Vector vector = (Vector) natureList.get(n);
                            params.setNature("");
                            params.setAcType(vector.get(0).toString());

//                            GlHeadPojo glPojo = new GlHeadPojo();
//                            glPojo.setGlHead(vector.get(0).toString());
//                            glPojo.setGlName(vector.get(1).toString());
                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
//                            acctBal = rbiReportRemote.getAcctsAndBal(params);
                            if (newBalPojo == null) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                } else {
                                    rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                }
                                //                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                            rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                            /*For other amounts*/
                            rbiSossPojo.setSecondAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                            rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                            rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                            rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                            if (referContent.equalsIgnoreCase("PROV")) {
                                rbiSossPojo.setAmt(new BigDecimal("0"));
                                rbiSossPojo.setThirdAmount(new BigDecimal("0").subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                            }
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));

                                        if (preSGNo == 0) {
                                            rbiSossPojo.setsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(preSGNo);
                                            if (preSsGNo == 0 && preSGNo != 0) {
                                                rbiSossPojo.setSsGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(preSsGNo);
                                                if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSssGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(preSssGNo);
                                                    if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(preZ + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));

                                        if (sGNo == 0) {
                                            rbiSossPojo.setsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(sGNo);
                                            if (ssGNo == 0 && sGNo != 0) {
                                                rbiSossPojo.setSsGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(ssGNo);
                                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSssGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(sssGNo);
                                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(n + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = n + 1;
                                    }
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                            }
                            dataList.add(rbiSossPojo);
//                            glPojo.setBalance(acctBal.getBalance());
//                            glHeadList.add(glPojo);
                        }
                    } else if (!(acType == null || acType.equalsIgnoreCase(""))) {
                        List acTypeList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                                + "acctcode ='" + params.getAcType() + "'").getResultList();
                        Vector vector = (Vector) acTypeList.get(0);
                        GlHeadPojo glPojo = new GlHeadPojo();
                        glPojo.setGlHead(vector.get(0).toString());
                        glPojo.setGlName(vector.get(1).toString());
                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
//                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        if (newBalPojo == null) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                        } else {
                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                            } else {
                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                        }
                        rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                        /*For other amounts*/
                        rbiSossPojo.setSecondAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                        rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                        rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                        rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                        if (referContent.equalsIgnoreCase("PROV")) {
                            rbiSossPojo.setAmt(new BigDecimal("0"));
                            rbiSossPojo.setThirdAmount(new BigDecimal("0").subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                        }
                        if (reportFormat.equalsIgnoreCase("N")) {
                            if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                    if (preSGNo == 0) {
                                        rbiSossPojo.setsGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(preSGNo);
                                        if (preSsGNo == 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(preSsGNo);
                                            if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                rbiSossPojo.setSssGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(preSssGNo);
                                                if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(preZ + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                    if (sGNo == 0) {
                                        rbiSossPojo.setsGNo(1);
                                    } else {
                                        rbiSossPojo.setsGNo(sGNo);
                                        if (ssGNo == 0 && sGNo != 0) {
                                            rbiSossPojo.setSsGNo(1);
                                        } else {
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSssGNo(1);
                                            } else {
                                                rbiSossPojo.setSssGNo(sssGNo);
                                                if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = 1;
                                }
                            }
                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                        }
                        dataList.add(rbiSossPojo);
//                        glPojo.setBalance(acctBal.getBalance());
//                        glHeadList.add(glPojo);
                    } else if (!(glHeadFrom == null || glHeadFrom.equalsIgnoreCase(""))
                            && !(glHeadTo == null || glHeadTo.equalsIgnoreCase(""))) {
//                        glHeadList = rbiReportRemote.getGLHeadAndBal(params);
//                        GlHeadPojo glHeadPojo = new GlHeadPojo();
                        List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                                + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                                + "by substring(acno,3,8)").getResultList();
                        for (int n = 0; n < glNameList.size(); n++) {
                            Vector vector = (Vector) glNameList.get(n);
//                        GlHeadPojo glPojo = new GlHeadPojo();
//                       glHeadList = rbiReportRemote.getGLHeadAndBal(params);
                            GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
//                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                            if (newBalPojo == null) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                            } else {
                                if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                                } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                    rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                                } else {
                                    rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                                }
                            }
                            rbiSossPojo.setAmt(rbiSossPojo.getAmt());                            
                            /*For other amounts*/
                            rbiSossPojo.setSecondAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                            rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                            rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                            rbiSossPojo.setFifthAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSsssGNo))).divide(hundred).multiply(new BigDecimal("-1")));;//Risk Adjusted Value-->Amount
                            if(fSGNo.equalsIgnoreCase("PRO")) {
                                rbiSossPojo.setAmt(new BigDecimal("0"));
                                rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                            }
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));

                                        if (preSGNo == 0) {
                                            rbiSossPojo.setsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(preSGNo);
                                            if (preSsGNo == 0 && preSGNo != 0) {
                                                rbiSossPojo.setSsGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(preSsGNo);
                                                if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSssGNo(preZ + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(preSssGNo);
                                                    if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(preZ + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(preSsssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = preZ + 1;
                                    } else {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));

                                        if (sGNo == 0) {
                                            rbiSossPojo.setsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setsGNo(sGNo);
                                            if (ssGNo == 0 && sGNo != 0) {
                                                rbiSossPojo.setSsGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSsGNo(ssGNo);
                                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSssGNo(n + 1);
                                                } else {
                                                    rbiSossPojo.setSssGNo(sssGNo);
                                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                        rbiSossPojo.setSsssGNo(n + 1);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    } else {
                                                        rbiSossPojo.setSsssGNo(ssssGNo);
                                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                    }
                                                }
                                            }
                                        }
                                        preZ = n + 1;
                                    }
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                            }
                            dataList.add(rbiSossPojo);
                        }
                    } else if ( !npaClassification.equalsIgnoreCase("")) {
                        List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
                        resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                "0", "0", "0", "0", npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, "0", "0",
                                referIndex.equalsIgnoreCase("") ? "0": referIndex, referContent.equalsIgnoreCase("") ? "0": referContent, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N","N","0","N","0","N","N", "0", "0", "0", "0");
                        BigDecimal a = new BigDecimal("0");
                        for (int k = 0; k < resultList.size(); k++) {
                            LoanMisCellaniousPojo loanMisCellaniousPojo = new LoanMisCellaniousPojo();
                            LoanMisCellaniousPojo val = resultList.get(k);
//                                a= a.add(val.getOutstanding());
                            if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                a = a.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                            } else {
                                a = a.add(resultList.get(k).getOutstanding());
                            }

                        }
                        rbiSossPojo.setAmt(a.divide(repOpt).multiply(new BigDecimal("-1")));

                        /*For other amounts*/
                        rbiSossPojo.setSecondAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                        rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                        rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                        rbiSossPojo.setFifthAmount((rbiSossPojo.getThirdAmount().multiply(rbiSossPojo.getFourthAmount())).divide(hundred).multiply(new BigDecimal("-1")));//Risk Adjusted Value-->Amount
                    }
                    /*Setting into main list with other amounts*/
//                    BigDecimal hundred = new BigDecimal("100");
                    for (int z = 0; z < glHeadList.size(); z++) {
                        rbiSossPojo = new RbiSossPojo();
                        GlHeadPojo glHeadPo = glHeadList.get(z);
                        rbiSossPojo.setAmt(glHeadPo.getBalance().divide(repOpt));//Book Value-->Amount
                    /*For other amounts*/
                        rbiSossPojo.setSecondAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margins And Provisions-->Amount
                        rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Book Value Net-->Amount
                        rbiSossPojo.setFourthAmount(new BigDecimal(fSsssGNo));//Risk Weight Percentage-->Percentage
                        rbiSossPojo.setFifthAmount((rbiSossPojo.getThirdAmount().multiply(rbiSossPojo.getFourthAmount())).divide(hundred));//Risk Adjusted Value-->Amount
                    /*End of amount settings*/
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                        rbiSossPojo.setRangeFrom(rangeFrom);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        if (ssGNo == 0) {
                            rbiSossPojo.setSsGNo(z + 1);
                        } else {
                            rbiSossPojo.setSsGNo(ssGNo);
                        }
                        if (sssGNo == 0 && ssGNo != 0) {
                            rbiSossPojo.setSssGNo(z + 1);
                        } else {
                            rbiSossPojo.setSssGNo(sssGNo);
                        }
                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
                            rbiSossPojo.setSsssGNo(z + 1);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                        } else {
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                        }
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        dataList.add(rbiSossPojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
//        System.out.println("OSS7 End of Part C2:"+new Date());
        return dataList;
    }

    public List<RbiSossPojo> getOss7PartCSection1(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, List osBlancelist) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        try {
            List list = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, "
                    + "classification, count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, "
                    + "ifnull(range_from,0), ifnull(range_to,0), formula, f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, "
                    + "npa_classification from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There should be data in CBS HO RBI STMT REPORT for:" + repName);
            }
            for (int i = 0; i < list.size(); i++) {
                RbiSossPojo rbiSossPojo = new RbiSossPojo();
                Vector element = (Vector) list.get(i);
                Integer sNo = Integer.parseInt(element.get(0).toString());
                String reportName = element.get(1).toString();
                String description = element.get(2).toString();
                Integer gNo = Integer.parseInt(element.get(3).toString());
                Integer sGNo = Integer.parseInt(element.get(4).toString());
                Integer ssGNo = Integer.parseInt(element.get(5).toString());
                Integer sssGNo = Integer.parseInt(element.get(6).toString());
                Integer ssssGNo = Integer.parseInt(element.get(7).toString());
                String classification = element.get(8).toString();
                String countApplicable = element.get(9).toString();
                String acNature = element.get(10).toString();
                String acType = element.get(11).toString();
                String glHeadFrom = element.get(12).toString();
                String glHeadTo = element.get(13).toString();
                String rangeBaseOn = element.get(14).toString();
                String rangeFrom = element.get(15).toString();
                String rangeTo = element.get(16).toString();
                String formula = element.get(17).toString();
                String fGNo = element.get(18).toString();
                String fSGNo = element.get(19).toString();
                String fSsGNo = element.get(20).toString();                     //CCF for Contingent Percentage                         
                String fSssGNo = element.get(21).toString();                    //Margin Percentage
                String fSsssGNo = element.get(22).toString();                   //RW For Obligent Percentage    
                String npaClassification = element.get(23).toString();
                /*As it is insertion of heading in the RBI MASTER*/
                rbiSossPojo.setsNo(sNo);
                rbiSossPojo.setReportName(reportName);
                rbiSossPojo.setDescription(description);
                rbiSossPojo.setgNo(gNo);
                rbiSossPojo.setsGNo(sGNo);
                rbiSossPojo.setSsGNo(ssGNo);
                rbiSossPojo.setSssGNo(sssGNo);
                rbiSossPojo.setSsssGNo(ssssGNo);
                rbiSossPojo.setClassification(classification);
                rbiSossPojo.setCountApplicable(countApplicable);
                rbiSossPojo.setAcNature(acNature);
                rbiSossPojo.setAcType(acType);
                rbiSossPojo.setNpaClassification(npaClassification);
                rbiSossPojo.setGlheadFrom(glHeadFrom);
                rbiSossPojo.setGlHeadTo(glHeadTo);
                rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                rbiSossPojo.setRangeFrom(rangeFrom);
                rbiSossPojo.setRangeTo(rangeTo);
                rbiSossPojo.setFormula(formula);
                rbiSossPojo.setfGNo(fGNo);
                rbiSossPojo.setfSGNo(fSGNo);
                rbiSossPojo.setfSsGNo(fSsGNo);
                rbiSossPojo.setfSssGNo(fSssGNo);
                rbiSossPojo.setfSsssGNo(fSsssGNo);
                rbiSossPojo.setAmt(new BigDecimal("0"));                        //Gross Block Exposure-->Amount
                rbiSossPojo.setSecondAmount(new BigDecimal("0"));               //Margin And Provisions-->Amount
                rbiSossPojo.setThirdAmount(new BigDecimal("0"));                //Net Exposure-->Amount
                rbiSossPojo.setFourthAmount(new BigDecimal("0"));               //CCF For Contingent-->Percentage
                rbiSossPojo.setFifthAmount(new BigDecimal("0"));                //RW For Obligent-->Percentage
                rbiSossPojo.setSixthAmount(new BigDecimal("0"));                //Risk Adjusted value of Exposure-->Amount

                dataList.add(rbiSossPojo);
                /*Report Processing*/
                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setBrnCode(orgnCode);
                params.setClassification(classification);
                params.setDate(reportDt);
                params.setToDate(reportDt);
                params.setFromRange(rangeFrom);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setNature(acNature);
                params.setOrgBrCode(orgnCode);
                params.setRangeBasedOn(rangeBaseOn);
                params.setToRange(rangeTo);
                params.setFlag(fSGNo);

                List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                if (!(glHeadFrom == null || glHeadFrom.equalsIgnoreCase("")) && !(glHeadTo == null || glHeadTo.equalsIgnoreCase(""))) {
//                    glHeadList = rbiReportRemote.getGLHeadAndBal(params);
                    BigDecimal hundred = new BigDecimal("100");
                    GlHeadPojo glHeadPojo = new GlHeadPojo();
                    List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                            + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                            + "by substring(acno,3,8)").getResultList();
                    for (int n = 0; n < glNameList.size(); n++) {
                        Vector vector = (Vector) glNameList.get(n);
//                        GlHeadPojo glPojo = new GlHeadPojo();
//                       glHeadList = rbiReportRemote.getGLHeadAndBal(params);
                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
//                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        if (newBalPojo == null) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                        } else {
                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                            } else {
                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                        }
                        rbiSossPojo.setAmt(rbiSossPojo.getAmt().divide(repOpt));//Gross Block Exposure-->Amount
                        /*For other amounts*/
                        rbiSossPojo.setSecondAmount((rbiSossPojo.getAmt().multiply(new BigDecimal(fSssGNo))).divide(hundred));//Margin And Provisions-->Amount
                        rbiSossPojo.setThirdAmount(rbiSossPojo.getAmt().subtract(rbiSossPojo.getSecondAmount()));//Net Exposure-->Amount
                        rbiSossPojo.setFourthAmount(new BigDecimal(fSsGNo));//CCF For Contingent-->Percentage
                        rbiSossPojo.setFifthAmount(new BigDecimal(fSsssGNo));//RW For Obligent-->Percentage
                        rbiSossPojo.setSixthAmount((rbiSossPojo.getThirdAmount().multiply(rbiSossPojo.getFifthAmount())).divide(hundred));//Risk Adjusted value of Exposure-->Amount
                        /*End of amount settings*/
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                        rbiSossPojo.setRangeFrom(rangeFrom);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        if (ssGNo == 0) {
                            rbiSossPojo.setSsGNo(n + 1);
                        } else {
                            rbiSossPojo.setSsGNo(ssGNo);
                        }
                        if (sssGNo == 0 && ssGNo != 0) {
                            rbiSossPojo.setSssGNo(n + 1);
                        } else {
                            rbiSossPojo.setSssGNo(sssGNo);
                        }
                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
                            rbiSossPojo.setSsssGNo(n + 1);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(rbiSossPojo.getDescription()));
                        } else {
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(rbiSossPojo.getDescription()));
                        }
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        dataList.add(rbiSossPojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<RbiSossPojo> getOss7PartCSection2i(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, List osBlancelist) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        try {
            List list = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, "
                    + "classification, count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, "
                    + "ifnull(range_from,0), ifnull(range_to,0), formula, f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, "
                    + "npa_classification from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There should be data in CBS HO RBI STMT REPORT for:" + repName);
            }
            for (int i = 0; i < list.size(); i++) {
                RbiSossPojo rbiSossPojo = new RbiSossPojo();
                Vector element = (Vector) list.get(i);
                Integer sNo = Integer.parseInt(element.get(0).toString());
                String reportName = element.get(1).toString();
                String description = element.get(2).toString();
                Integer gNo = Integer.parseInt(element.get(3).toString());
                Integer sGNo = Integer.parseInt(element.get(4).toString());
                Integer ssGNo = Integer.parseInt(element.get(5).toString());
                Integer sssGNo = Integer.parseInt(element.get(6).toString());
                Integer ssssGNo = Integer.parseInt(element.get(7).toString());
                String classification = element.get(8).toString();
                String countApplicable = element.get(9).toString();
                String acNature = element.get(10).toString();
                String acType = element.get(11).toString();
                String glHeadFrom = element.get(12).toString();
                String glHeadTo = element.get(13).toString();
                String rangeBaseOn = element.get(14).toString();
                String rangeFrom = element.get(15).toString();
                String rangeTo = element.get(16).toString();
                String formula = element.get(17).toString();
                String fGNo = element.get(18).toString();
                String fSGNo = element.get(19).toString();
                String fSsGNo = element.get(20).toString();
                String fSssGNo = element.get(21).toString();
                String fSsssGNo = element.get(22).toString();
                String npaClassification = element.get(23).toString();
                /*As it is insertion of heading in the RBI MASTER*/
                rbiSossPojo.setsNo(sNo);
                rbiSossPojo.setReportName(reportName);
                rbiSossPojo.setDescription(description);
                rbiSossPojo.setgNo(gNo);
                rbiSossPojo.setsGNo(sGNo);
                rbiSossPojo.setSsGNo(ssGNo);
                rbiSossPojo.setSssGNo(sssGNo);
                rbiSossPojo.setSsssGNo(ssssGNo);
                rbiSossPojo.setClassification(classification);
                rbiSossPojo.setCountApplicable(countApplicable);
                rbiSossPojo.setAcNature(acNature);
                rbiSossPojo.setAcType(acType);
                rbiSossPojo.setNpaClassification(npaClassification);
                rbiSossPojo.setGlheadFrom(glHeadFrom);
                rbiSossPojo.setGlHeadTo(glHeadTo);
                rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                rbiSossPojo.setRangeFrom(rangeFrom);
                rbiSossPojo.setRangeTo(rangeTo);
                rbiSossPojo.setFormula(formula);
                rbiSossPojo.setfGNo(fGNo);
                rbiSossPojo.setfSGNo(fSGNo);
                rbiSossPojo.setfSsGNo(fSsGNo);
                rbiSossPojo.setfSssGNo(fSssGNo);
                rbiSossPojo.setfSsssGNo(fSsssGNo);
                rbiSossPojo.setAmt(new BigDecimal("0"));                        //Notional Principal Amount-->Amount
                rbiSossPojo.setSecondAmount(new BigDecimal("0"));               //Risk Weight-->Percentage
                rbiSossPojo.setThirdAmount(new BigDecimal("0"));                //Risk Adjusted Value-->Amount

                dataList.add(rbiSossPojo);
                /*Report Processing*/
                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setBrnCode(orgnCode);
                params.setClassification(classification);
                params.setDate(reportDt);
                params.setToDate(reportDt);
                params.setFromRange(rangeFrom);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setNature(acNature);
                params.setOrgBrCode(orgnCode);
                params.setRangeBasedOn(rangeBaseOn);
                params.setToRange(rangeTo);
                params.setFlag(fSGNo);

                List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                if (!(glHeadFrom == null || glHeadFrom.equalsIgnoreCase("")) && !(glHeadTo == null || glHeadTo.equalsIgnoreCase(""))) {
//                    glHeadList = rbiReportRemote.getGLHeadAndBal(params); BigDecimal hundred = new BigDecimal("100");
                    BigDecimal hundred = new BigDecimal("100");
                    GlHeadPojo glHeadPojo = new GlHeadPojo();
                    List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                            + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                            + "by substring(acno,3,8)").getResultList();
                    for (int n = 0; n < glNameList.size(); n++) {
                        Vector vector = (Vector) glNameList.get(n);
//                        GlHeadPojo glPojo = new GlHeadPojo();
//                       glHeadList = rbiReportRemote.getGLHeadAndBal(params);
                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
//                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        if (newBalPojo == null) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                        } else {
                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                            } else {
                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                        }
                        /*Setting into main list with other amounts*/
                        rbiSossPojo.setAmt(rbiSossPojo.getAmt().divide(repOpt));   //Notional Principal Amount-->Amount
                        /*For other amounts*/
                        rbiSossPojo.setSecondAmount(new BigDecimal(fSsssGNo));      //Risk Weight-->Percentage
                        rbiSossPojo.setThirdAmount((rbiSossPojo.getAmt().multiply(rbiSossPojo.getSecondAmount())).divide(hundred));//Risk Adjusted Value-->Amount
                        /*End of amount settings*/
                        rbiSossPojo.setsNo(sNo);
                        rbiSossPojo.setAcNature(acNature);
                        rbiSossPojo.setAcType(acType);
                        rbiSossPojo.setClassification(classification);
                        rbiSossPojo.setCountApplicable(countApplicable);
                        rbiSossPojo.setFormula(formula);
                        rbiSossPojo.setGlHeadTo(glHeadTo);
                        rbiSossPojo.setGlheadFrom(glHeadFrom);
                        rbiSossPojo.setNpaClassification(npaClassification);
                        rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                        rbiSossPojo.setRangeFrom(rangeFrom);
                        rbiSossPojo.setRangeTo(rangeTo);
                        rbiSossPojo.setReportName(reportName);
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        if (ssGNo == 0) {
                            rbiSossPojo.setSsGNo(n + 1);
                        } else {
                            rbiSossPojo.setSsGNo(ssGNo);
                        }
                        if (sssGNo == 0 && ssGNo != 0) {
                            rbiSossPojo.setSssGNo(n + 1);
                        } else {
                            rbiSossPojo.setSssGNo(sssGNo);
                        }
                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
                            rbiSossPojo.setSsssGNo(n + 1);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(rbiSossPojo.getDescription()));
                        } else {
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(rbiSossPojo.getDescription()));
                        }
                        rbiSossPojo.setfGNo(fGNo);
                        rbiSossPojo.setfSGNo(fSGNo);
                        rbiSossPojo.setfSsGNo(fSsGNo);
                        rbiSossPojo.setfSssGNo(fSssGNo);
                        rbiSossPojo.setfSsssGNo(fSsssGNo);
                        dataList.add(rbiSossPojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<RbiSossPojo> getOss7PartA(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, String reportFormat, List osBlancelist,String partBCParam) throws ApplicationException {
        List<RbiSossPojo> dataList = new ArrayList<RbiSossPojo>();
        BigDecimal hundred = new BigDecimal("100");
        try {
            String subQuery ="";
            if (partBCParam.equalsIgnoreCase("N")) {
                subQuery = " and REFER_INDEX <> 'Y'";
            }
            List list = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, "
                    + "classification, count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, "
                    + "ifnull(range_from,0), ifnull(range_to,0), formula, f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, "
                    + "npa_classification,refer_index,refer_content from cbs_ho_rbi_stmt_report where report_name = '" + repName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT "+subQuery+" order by "
                    + "report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), "
                    + "cast(sss_gno as unsigned),cast(ssss_gno as unsigned)").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There should be data in CBS HO RBI STMT REPORT for:" + repName);
            }
            Integer preGNo = 0, preSGNo = 0, preSsGNo = 0, preSssGNo = 0, preSsssGNo = 0, preZ = 0;
            String preFormula = "";
            for (int i = 0; i < list.size(); i++) {
                RbiSossPojo rbiSossPojo = new RbiSossPojo();
                Vector element = (Vector) list.get(i);
                Integer sNo = Integer.parseInt(element.get(0).toString());
//                System.out.println("i>>>>>" + sNo + ":" + new Date());
                String reportName = element.get(1).toString();
                String description = element.get(2).toString();
                Integer gNo = Integer.parseInt(element.get(3).toString());
                Integer sGNo = Integer.parseInt(element.get(4).toString());
                Integer ssGNo = Integer.parseInt(element.get(5).toString());
                Integer sssGNo = Integer.parseInt(element.get(6).toString());
                Integer ssssGNo = Integer.parseInt(element.get(7).toString());
                String classification = element.get(8).toString();
                String countApplicable = element.get(9).toString();
                String acNature = element.get(10).toString();
                String acType = element.get(11).toString();
                String glHeadFrom = element.get(12).toString();
                String glHeadTo = element.get(13).toString();
                String rangeBaseOn = element.get(14).toString();
                String rangeFrom = element.get(15).toString();
                String rangeTo = element.get(16).toString();
                String formula = element.get(17).toString();
                String fGNo = element.get(18).toString();
                String fSGNo = element.get(19).toString();
                String fSsGNo = element.get(20).toString();
                String fSssGNo = element.get(21).toString();
                String fSsssGNo = element.get(22).toString();
                String npaClassification = element.get(23).toString();
                String referIndex = element.get(24).toString();
                String referContent = element.get(25).toString();
                /*As it is insertion of heading in the RBI MASTER*/
                if (!referIndex.equalsIgnoreCase("Y")) {
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setDescription(description);
//                    rbiSossPojo.setgNo(gNo);
//                    rbiSossPojo.setsGNo(sGNo);
//                    rbiSossPojo.setSsGNo(ssGNo);
//                    rbiSossPojo.setSssGNo(sssGNo);
//                    rbiSossPojo.setSsssGNo(ssssGNo);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    rbiSossPojo.setAmt(new BigDecimal("0"));
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setgNo(preGNo);
                            rbiSossPojo.setsGNo(preSGNo);
                            rbiSossPojo.setSsGNo(preSsGNo);
                            rbiSossPojo.setSssGNo(preSssGNo);
                            rbiSossPojo.setSsssGNo(preSsssGNo);

                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);

                            dataList.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                            preZ = 0;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);

                        dataList.add(rbiSossPojo);
                    }


                }
                /*Report Processing*/
                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setBrnCode(orgnCode);
                params.setClassification(classification);
                params.setDate(reportDt);
                params.setToDate(reportDt);
                params.setFromRange(rangeFrom);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);
                params.setNature(acNature);
                params.setAcType(acType);
                params.setOrgBrCode(orgnCode);
                params.setRangeBasedOn(rangeBaseOn);
                params.setToRange(rangeTo);
                params.setFlag(fSGNo);

                List<GlHeadPojo> glHeadList = new ArrayList<GlHeadPojo>();
                if (!(glHeadFrom == null || glHeadFrom.equalsIgnoreCase("")) && !(glHeadTo == null || glHeadTo.equalsIgnoreCase(""))) {
                    GlHeadPojo glHeadPojo = new GlHeadPojo();
                    List glNameList = em.createNativeQuery("select substring(acno,3,8),acname from gltable where "
                            + "substring(acno,3,8) between '" + glHeadFrom + "' and '" + glHeadTo + "' group "
                            + "by substring(acno,3,8)").getResultList();
                    for (int n = 0; n < glNameList.size(); n++) {
                        Vector vector = (Vector) glNameList.get(n);
                        GlHeadPojo glPojo = new GlHeadPojo();
//                       glHeadList = rbiReportRemote.getGLHeadAndBal(params);
                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
//                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        if (newBalPojo == null) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                        } else {
                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                            } else {
                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                            //                                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                        }
                        rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                        if (reportFormat.equalsIgnoreCase("N")) {
                            if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));

                                    if (preSGNo == 0) {
                                        rbiSossPojo.setsGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(preSGNo);
                                        if (preSsGNo == 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(preSsGNo);
                                            if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                rbiSossPojo.setSssGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(preSssGNo);
                                                if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(preZ + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));

                                    if (sGNo == 0) {
                                        rbiSossPojo.setsGNo(n + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(sGNo);
                                        if (ssGNo == 0 && sGNo != 0) {
                                            rbiSossPojo.setSsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSssGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(sssGNo);
                                                if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(n + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = n + 1;
                                }
                            }
                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);
                            rbiSossPojo.setDescription(description);
                        }
                        dataList.add(rbiSossPojo);
                    }
                } else if (!(acNature == null || acNature.equalsIgnoreCase(""))) {
                    List natureList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                            + "acctnature ='" + params.getNature() + "'").getResultList();
                    for (int n = 0; n < natureList.size(); n++) {
                        Vector vector = (Vector) natureList.get(n);
                        params.setNature("");
                        params.setAcType(vector.get(0).toString());
                        GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                        if (newBalPojo == null) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                        } else {
                            if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                            } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                                rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                            } else {
                                rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                            }
                            rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                            /*For other amounts*/
                            if (reportFormat.equalsIgnoreCase("N")) {
                                if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                    }
                                    if (preSGNo == 0) {
                                        rbiSossPojo.setsGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(preSGNo);
                                        if (preSsGNo == 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsGNo(preZ + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(preSsGNo);
                                            if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                                rbiSossPojo.setSssGNo(preZ + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(preSssGNo);
                                                if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(preZ + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(preSsssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = preZ + 1;
                                } else {
                                    rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                    if (sGNo == 0) {
                                        rbiSossPojo.setsGNo(n + 1);
                                    } else {
                                        rbiSossPojo.setsGNo(sGNo);
                                        if (ssGNo == 0 && sGNo != 0) {
                                            rbiSossPojo.setSsGNo(n + 1);
                                        } else {
                                            rbiSossPojo.setSsGNo(ssGNo);
                                            if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                                rbiSossPojo.setSssGNo(n + 1);
                                            } else {
                                                rbiSossPojo.setSssGNo(sssGNo);
                                                if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                                    rbiSossPojo.setSsssGNo(n + 1);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                } else {
                                                    rbiSossPojo.setSsssGNo(ssssGNo);
                                                    rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(n + 1)).concat(". ").concat(vector.get(1).toString()));
                                                }
                                            }
                                        }
                                    }
                                    preZ = n + 1;
                                }
                            } else {
                                rbiSossPojo.setgNo(gNo);
                                rbiSossPojo.setsGNo(sGNo);
                                rbiSossPojo.setSsGNo(ssGNo);
                                rbiSossPojo.setSssGNo(sssGNo);
                                rbiSossPojo.setSsssGNo(ssssGNo);
                                rbiSossPojo.setDescription(description);
                            }
                            dataList.add(rbiSossPojo);
                        }
                    }
                } else if (!(acType == null || acType.equalsIgnoreCase(""))) {
                    List acTypeList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where "
                            + "acctcode ='" + params.getAcType() + "'").getResultList();
                    Vector vector = (Vector) acTypeList.get(0);
                    GlHeadPojo glPojo = new GlHeadPojo();
                    glPojo.setGlHead(vector.get(0).toString());
                    glPojo.setGlName(vector.get(1).toString());
                    GlHeadPojo newBalPojo = monthlyRemote.getOSBalanceWithClassification(osBlancelist, vector.get(0).toString(), classification, reportDt);
                    if (newBalPojo == null) {
                        rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, new BigDecimal(0.00));
                    } else {
                        if (gNo == 1 && classification.equalsIgnoreCase("A")) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt));
                        } else if (gNo == 2 && classification.equalsIgnoreCase("L")) {
                            rbiSossPojo = monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().multiply(new BigDecimal("-1")).divide(repOpt));
                        } else {
                            rbiSossPojo = (fSGNo.equalsIgnoreCase("ACT")) ? monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().divide(repOpt)) : monthlyRemote.setReportAmount(rbiSossPojo, 0, newBalPojo.getBalance().abs().divide(repOpt));
                        }
                    }
                    rbiSossPojo.setAmt(rbiSossPojo.getAmt());
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (rbiSossPojo.getAmt().compareTo(new BigDecimal(0)) != 0) {
                            if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && formula.equalsIgnoreCase(formula)) {
                                rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                            }
                            if (preSGNo == 0) {
                                rbiSossPojo.setsGNo(preZ + 1);
                            } else {
                                rbiSossPojo.setsGNo(preSGNo);
                                if (preSsGNo == 0 && preSGNo != 0) {
                                    rbiSossPojo.setSsGNo(preZ + 1);
                                } else {
                                    rbiSossPojo.setSsGNo(preSsGNo);
                                    if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                        rbiSossPojo.setSssGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setSssGNo(preSssGNo);
                                        if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsssGNo(preZ + 1);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                        } else {
                                            rbiSossPojo.setSsssGNo(preSsssGNo);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(vector.get(1).toString()));
                                        }
                                    }
                                }
                            }
                            preZ = preZ + 1;
                        } else {
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                            if (sGNo == 0) {
                                rbiSossPojo.setsGNo(1);
                            } else {
                                rbiSossPojo.setsGNo(sGNo);
                                if (ssGNo == 0 && sGNo != 0) {
                                    rbiSossPojo.setSsGNo(1);
                                } else {
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                        rbiSossPojo.setSssGNo(1);
                                    } else {
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                            rbiSossPojo.setSsssGNo(1);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                        } else {
                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(1)).concat(". ").concat(vector.get(1).toString()));
                                        }
                                    }
                                }
                            }
                            preZ = 1;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                    }
                    /*For other amounts*/
                    dataList.add(rbiSossPojo);
//                    glPojo.setBalance(acctBal.getBalance());
//                    glHeadList.add(glPojo);
                } else if (referIndex.equalsIgnoreCase("Y") && !(referContent == null || referContent.equals(""))) {
                    BigDecimal referBal = new BigDecimal("0");
                    List<RbiSossPojo> referList = null;
                    if (referContent.equalsIgnoreCase("OSS7-PART-B") || referContent.equalsIgnoreCase("XBRLOSS7-PART-B")) {
                        referList = getOss7PartBAndPartCSec2(referContent, reportDt, orgnCode, repOpt, reportFormat, osBlancelist);
                        for (RbiSossPojo referObj : referList) {
                            referBal = referBal.add(referObj.getFifthAmount());
                        }
                    } else if (referContent.equalsIgnoreCase("OSS7-PART-C-SEC-1")) {
                        referList = getOss7PartCSection1(referContent, reportDt, orgnCode, repOpt, osBlancelist);
                        for (RbiSossPojo referObj : referList) {
                            referBal = referBal.add(referObj.getSixthAmount());
                        }
                    } else if (referContent.equalsIgnoreCase("OSS7-PART-C-SEC-2i")) {
                        referList = getOss7PartCSection2i(referContent, reportDt, orgnCode, repOpt, osBlancelist);
                        for (RbiSossPojo referObj : referList) {
                            referBal = referBal.add(referObj.getThirdAmount());
                        }
                    }
                    /*Addition into main list*/
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setReportName(reportName);
                    rbiSossPojo.setDescription(description);
                    rbiSossPojo.setgNo(gNo);
                    rbiSossPojo.setsGNo(sGNo);
                    rbiSossPojo.setSsGNo(ssGNo);
                    rbiSossPojo.setSssGNo(sssGNo);
                    rbiSossPojo.setSsssGNo(ssssGNo);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    rbiSossPojo.setAmt(referBal);
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setgNo(preGNo);
                            rbiSossPojo.setsGNo(preSGNo);
                            rbiSossPojo.setSsGNo(preSsGNo);
                            rbiSossPojo.setSssGNo(preSssGNo);
                            rbiSossPojo.setSsssGNo(preSsssGNo);

                        } else {
                            rbiSossPojo.setgNo(gNo);
                            rbiSossPojo.setsGNo(sGNo);
                            rbiSossPojo.setSsGNo(ssGNo);
                            rbiSossPojo.setSssGNo(sssGNo);
                            rbiSossPojo.setSsssGNo(ssssGNo);

                            dataList.add(rbiSossPojo);
                            preGNo = gNo;
                            preSGNo = sGNo;
                            preSsGNo = ssGNo;
                            preSssGNo = sssGNo;
                            preSsssGNo = ssssGNo;
                            preFormula = formula;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);

                        dataList.add(rbiSossPojo);
                    }

                }
                /*Setting into main list with other amounts*/
                for (int z = 0; z < glHeadList.size(); z++) {
                    rbiSossPojo = new RbiSossPojo();
                    GlHeadPojo glHeadPo = glHeadList.get(z);
                    rbiSossPojo.setAmt(glHeadPo.getBalance().divide(repOpt));
                    rbiSossPojo.setsNo(sNo);
                    rbiSossPojo.setAcNature(acNature);
                    rbiSossPojo.setAcType(acType);
                    rbiSossPojo.setClassification(classification);
                    rbiSossPojo.setCountApplicable(countApplicable);
                    rbiSossPojo.setFormula(formula);
                    rbiSossPojo.setGlHeadTo(glHeadTo);
                    rbiSossPojo.setGlheadFrom(glHeadFrom);
                    rbiSossPojo.setNpaClassification(npaClassification);
                    rbiSossPojo.setRangeBaseOn(rangeBaseOn);
                    rbiSossPojo.setRangeFrom(rangeFrom);
                    rbiSossPojo.setRangeTo(rangeTo);
                    rbiSossPojo.setReportName(reportName);
//                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
//                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                        rbiSossPojo.setgNo(preGNo);
//                        if (preSGNo == 0) {
//                            rbiSossPojo.setsGNo(preZ + 1);
//                        } else {
//                            rbiSossPojo.setsGNo(preSGNo);
//                            if (preSsGNo == 0 && preSGNo != 0) {
//                                rbiSossPojo.setSsGNo(preZ + 1);
//                            } else {
//                                rbiSossPojo.setSsGNo(preSsGNo);
//                                if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
//                                    rbiSossPojo.setSssGNo(preZ + 1);
//                                } else {
//                                    rbiSossPojo.setSssGNo(preSssGNo);
//                                    if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
//                                        rbiSossPojo.setSsssGNo(preZ + 1);
//                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                                    } else {
//                                        rbiSossPojo.setSsssGNo(preSsssGNo);
//                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                                    }
//                                }
//                            }
//                        }
//                        preZ = preZ + 1;
//                    } else {
//                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                        rbiSossPojo.setgNo(gNo);
//                        if (sGNo == 0) {
//                            rbiSossPojo.setsGNo(z + 1);
//                        } else {
//                            rbiSossPojo.setsGNo(sGNo);
//                            if (ssGNo == 0 && sGNo != 0) {
//                                rbiSossPojo.setSsGNo(z + 1);
//                            } else {
//                                rbiSossPojo.setSsGNo(ssGNo);
//                                if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
//                                    rbiSossPojo.setSssGNo(z + 1);
//                                } else {
//                                    rbiSossPojo.setSssGNo(sssGNo);
//                                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
//                                        rbiSossPojo.setSsssGNo(z + 1);
//                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                                    } else {
//                                        rbiSossPojo.setSsssGNo(ssssGNo);
//                                        rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                                    }
//                                }
//                            }
//                        }
//                        preZ = z + 1;
//                    }
                    rbiSossPojo.setgNo(gNo);
                    if (reportFormat.equalsIgnoreCase("N")) {
                        if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));

                            if (preSGNo == 0) {
                                rbiSossPojo.setsGNo(preZ + 1);
                            } else {
                                rbiSossPojo.setsGNo(preSGNo);
                                if (preSsGNo == 0 && preSGNo != 0) {
                                    rbiSossPojo.setSsGNo(preZ + 1);
                                } else {
                                    rbiSossPojo.setSsGNo(preSsGNo);
                                    if (preSssGNo == 0 && preSsGNo != 0 && preSGNo != 0) {
                                        rbiSossPojo.setSssGNo(preZ + 1);
                                    } else {
                                        rbiSossPojo.setSssGNo(preSssGNo);
                                        if (preSsssGNo == 0 && preSssGNo != 0 && preSsGNo != 0 && preSGNo != 0) {
                                            rbiSossPojo.setSsssGNo(preZ + 1);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                        } else {
                                            rbiSossPojo.setSsssGNo(preSsssGNo);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(preZ + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                        }
                                    }
                                }
                            }
                            preZ = preZ + 1;
                        } else {
                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));

                            if (sGNo == 0) {
                                rbiSossPojo.setsGNo(z + 1);
                            } else {
                                rbiSossPojo.setsGNo(sGNo);
                                if (ssGNo == 0 && sGNo != 0) {
                                    rbiSossPojo.setSsGNo(z + 1);
                                } else {
                                    rbiSossPojo.setSsGNo(ssGNo);
                                    if (sssGNo == 0 && ssGNo != 0 && sGNo != 0) {
                                        rbiSossPojo.setSssGNo(z + 1);
                                    } else {
                                        rbiSossPojo.setSssGNo(sssGNo);
                                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0 && sGNo != 0) {
                                            rbiSossPojo.setSsssGNo(z + 1);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                        } else {
                                            rbiSossPojo.setSsssGNo(ssssGNo);
                                            rbiSossPojo.setDescription(String.valueOf("                          ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
                                        }
                                    }
                                }
                            }
                            preZ = z + 1;
                        }
                    } else {
                        rbiSossPojo.setgNo(gNo);
                        rbiSossPojo.setsGNo(sGNo);
                        rbiSossPojo.setSsGNo(ssGNo);
                        rbiSossPojo.setSssGNo(sssGNo);
                        rbiSossPojo.setSsssGNo(ssssGNo);
                        rbiSossPojo.setDescription(description);
                    }

//                    if (preGNo == gNo && preSGNo == sGNo && preSsGNo == ssGNo && preSssGNo == sssGNo && preSsssGNo == ssssGNo && preFormula.equalsIgnoreCase(formula)) {
//                        rbiSossPojo.setgNo(gNo);
//                        rbiSossPojo.setsGNo(sGNo);
//                        if (ssGNo == 0) {
//                            rbiSossPojo.setSsGNo(z + 1);
//                        } else {
//                            rbiSossPojo.setSsGNo(ssGNo);
//                        }
//                        if (sssGNo == 0 && ssGNo != 0) {
//                            rbiSossPojo.setSssGNo(z + 1);
//                        } else {
//                            rbiSossPojo.setSssGNo(sssGNo);
//                        }
//                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
//                            rbiSossPojo.setSsssGNo(z + 1);
//                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                        } else {
//                            rbiSossPojo.setSsssGNo(ssssGNo);
//                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                        }
//                        preZ = z + 1;
//                    } else {
//                        rbiSossPojo.setgNo(gNo);
//                        rbiSossPojo.setsGNo(sGNo);
//                        if (ssGNo == 0) {
//                            rbiSossPojo.setSsGNo(z + 1);
//                        } else {
//                            rbiSossPojo.setSsGNo(ssGNo);
//                        }
//                        if (sssGNo == 0 && ssGNo != 0) {
//                            rbiSossPojo.setSssGNo(z + 1);
//                        } else {
//                            rbiSossPojo.setSssGNo(sssGNo);
//                        }
//                        if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
//                            rbiSossPojo.setSsssGNo(z + 1);
//                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                        } else {
//                            rbiSossPojo.setSsssGNo(ssssGNo);
//                            rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                        }
//                        preZ = z + 1;
//                    }

//                    rbiSossPojo.setgNo(gNo);
//                    rbiSossPojo.setsGNo(sGNo);
//                    if (ssGNo == 0) {
//                        rbiSossPojo.setSsGNo(z + 1);
//                    } else {
//                        rbiSossPojo.setSsGNo(ssGNo);
//                    }
//                    if (sssGNo == 0 && ssGNo != 0) {
//                        rbiSossPojo.setSssGNo(z + 1);
//                    } else {
//                        rbiSossPojo.setSssGNo(sssGNo);
//                    }
//                    if (ssssGNo == 0 && sssGNo != 0 && ssGNo != 0) {
//                        rbiSossPojo.setSsssGNo(z + 1);
//                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                    } else {
//                        rbiSossPojo.setSsssGNo(ssssGNo);
//                        rbiSossPojo.setDescription(String.valueOf("               ").concat(String.valueOf(z + 1)).concat(". ").concat(glHeadPo.getGlName()));
//                    }
                    rbiSossPojo.setfGNo(fGNo);
                    rbiSossPojo.setfSGNo(fSGNo);
                    rbiSossPojo.setfSsGNo(fSsGNo);
                    rbiSossPojo.setfSssGNo(fSssGNo);
                    rbiSossPojo.setfSsssGNo(fSsssGNo);
                    dataList.add(rbiSossPojo);
                    preGNo = gNo;
                    preSGNo = sGNo;
                    preSsGNo = ssGNo;
                    preSssGNo = sssGNo;
                    preSsssGNo = ssssGNo;
                    preFormula = formula;
                }
            }
            /*Formula Processing*/
            for (int k = 0; k < dataList.size(); k++) {
                RbiSossPojo rbiSossPojo = dataList.get(k);
                if (!(rbiSossPojo.getFormula() == null || rbiSossPojo.getFormula().equals(""))) {
                    BigDecimal bal = new BigDecimal("0.0");
                    if (rbiSossPojo.getFormula().equalsIgnoreCase("+P&L") || rbiSossPojo.getFormula().equalsIgnoreCase("-P&L")) {
                        double balPL = glReport.IncomeExp(reportDt, "0A", "0A");
                        if (rbiSossPojo.getClassification().equalsIgnoreCase("L") && balPL >= 0) {
                            rbiSossPojo.setAmt(new BigDecimal(balPL).divide(repOpt));
                        } else if (rbiSossPojo.getClassification().equalsIgnoreCase("A") && balPL < 0) {
                            rbiSossPojo.setAmt(new BigDecimal(balPL).divide(repOpt));
                        }
                    } else {
                        bal = bal.add(rbiReportRemote.getValueFromFormula(dataList, rbiSossPojo.getFormula()));
                        rbiSossPojo.setAmt(bal);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
//        for (int i = 0; i < dataList.size(); i++) {
//                System.out.println(dataList.get(i).getsNo() + ";" + dataList.get(i).getReportName() + ";" + dataList.get(i).getDescription() + ";" + dataList.get(i).getgNo()
//                        + ";" + dataList.get(i).getsGNo() + ";" + dataList.get(i).getSsGNo() + ";" + dataList.get(i).getSssGNo() + ";" + dataList.get(i).getSsssGNo()
//                        + ";" + dataList.get(i).getClassification() + ";" + dataList.get(i).getCountApplicable() + ";" + dataList.get(i).getAcNature()
//                        + ";" + dataList.get(i).getAcType() + ";" + dataList.get(i).getNpaClassification() + ";" + dataList.get(i).getGlheadFrom()
//                        + ";" + dataList.get(i).getGlHeadTo() + ";" + dataList.get(i).getRangeBaseOn() + ";" + dataList.get(i).getRangeFrom()
//                        + ";" + dataList.get(i).getRangeTo() + ";" + dataList.get(i).getFormula() + ";" + dataList.get(i).getfGNo()
//                        + ";" + dataList.get(i).getfSGNo() + ";" + dataList.get(i).getfSsGNo() + ";" + dataList.get(i).getfSssGNo()
//                        + ";" + dataList.get(i).getfSsssGNo() + ";" + dataList.get(i).getAmt() + ";" + dataList.get(i).getSecondAmount()
//                        + ";" + dataList.get(i).getThirdAmount() + ";" + dataList.get(i).getFourthAmount() + ";" + dataList.get(i).getFifthAmount()
//                        + ";" + dataList.get(i).getSixthAmount() + ";" + dataList.get(i).getSeventhAmount());
//        }
        return dataList;
    }

    public List<Oss7BusinessPojo> getOss7Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, String reportFormat) throws ApplicationException {
        List<Oss7BusinessPojo> dataList = new ArrayList<Oss7BusinessPojo>();
        List<String> dates = new ArrayList<String>();
        dates.add(reportDt);
        try {
            /*Calling all the sub report and creating the list*/
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnBrCode.equalsIgnoreCase("90") ? "0A" : orgnBrCode, dates);
            List<RbiSossPojo> oss7PartAList = getOss7PartA("OSS7-PART-A", reportDt, orgnBrCode, repOpt, reportFormat, osBlancelist,"Y");
            List<RbiSossPojo> oss7PartBList = getOss7PartBAndPartCSec2("OSS7-PART-B", reportDt, orgnBrCode.equalsIgnoreCase("90") ? "0A" : orgnBrCode, repOpt, reportFormat, osBlancelist);
            List<RbiSossPojo> oss7PartCSec1List = getOss7PartCSection1("OSS7-PART-C-SEC-1", reportDt, orgnBrCode, repOpt, osBlancelist);
            List<RbiSossPojo> oss7PartCSec2List = getOss7PartBAndPartCSec2("", reportDt, orgnBrCode, repOpt, reportFormat, osBlancelist);
            List<RbiSossPojo> oss7PartCSec2iList = getOss7PartCSection2i("OSS7-PART-C-SEC-2i", reportDt, orgnBrCode, repOpt, osBlancelist);
            
            /*Creating an object of all sub report list*/
            Oss7BusinessPojo pojo = new Oss7BusinessPojo();
            pojo.setOss7PartAList(oss7PartAList);
            pojo.setOss7PartBList(oss7PartBList);
            pojo.setOss7PartCSec1List(oss7PartCSec1List);
            pojo.setOss7PartCSec2List(oss7PartCSec2List);
            pojo.setOss7PartCSec2iList(oss7PartCSec2iList);

            /*Addition of object into returning list*/
            dataList.add(pojo);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
    public List<Oss7BusinessPojo> getXBRLOss7Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, String reportFormat) throws ApplicationException {
        List<Oss7BusinessPojo> dataList = new ArrayList<Oss7BusinessPojo>();
        List<String> dates = new ArrayList<String>();
        dates.add(reportDt);
        try {
            /*Calling all the sub report and creating the list*/
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(orgnBrCode.equalsIgnoreCase("90") ? "0A" : orgnBrCode, dates);
            List<RbiSossPojo> oss7PartAList = getOss7PartA("XBRLOSS7-PART-A", reportDt, orgnBrCode, repOpt, reportFormat, osBlancelist,"Y");
            List<RbiSossPojo> oss7PartBList = getOss7PartBAndPartCSec2("XBRLOSS7-PART-B", reportDt, orgnBrCode.equalsIgnoreCase("90") ? "0A" : orgnBrCode, repOpt, reportFormat, osBlancelist);
            List<RbiSossPojo> oss7PartCSec1List = getOss7PartCSection1("OSS7-PART-C-SEC-1", reportDt, orgnBrCode, repOpt, osBlancelist);
            List<RbiSossPojo> oss7PartCSec2List = getOss7PartBAndPartCSec2("", reportDt, orgnBrCode, repOpt, reportFormat, osBlancelist);
            List<RbiSossPojo> oss7PartCSec2iList = getOss7PartCSection2i("OSS7-PART-C-SEC-2i", reportDt, orgnBrCode, repOpt, osBlancelist);            
            /*Creating an object of all sub report list*/
            Oss7BusinessPojo pojo = new Oss7BusinessPojo();
            pojo.setOss7PartAList(oss7PartAList);
            pojo.setOss7PartBList(oss7PartBList);
            pojo.setOss7PartCSec1List(oss7PartCSec1List);
            pojo.setOss7PartCSec2List(oss7PartCSec2List);
            pojo.setOss7PartCSec2iList(oss7PartCSec2iList);
            /*Addition of object into returning list*/
            dataList.add(pojo);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<RbiSossPojo> getOss8FinDetails(String repDt, String brCode, BigDecimal repOpt, int noOfEmp, double dividend) throws ApplicationException {
        try {
            List<String> dates = new ArrayList<String>();
            dates.add(repDt);
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            List osBlancelist = monthlyRemote.getAsOnDateBalanceList(brCode.equalsIgnoreCase("90") ? "0A" : brCode, dates);
            List oss1Query = em.createNativeQuery("select sno, report_name, description, gno, s_gno, ss_gno, sss_gno, ssss_gno, classification, "
                    + "count_applicable,ac_nature, ac_type, gl_head_from, gl_head_to, range_base_on, ifnull(range_from,0), ifnull(range_to,0), formula, "
                    + "f_gno, f_s_gno,f_ss_gno, f_sss_gno, f_ssss_gno, npa_classification  from cbs_ho_rbi_stmt_report where report_name = 'OSS8' "
                    + "order by report_name, cast(gno as unsigned),cast(s_gno as unsigned),cast(ss_gno as unsigned), cast(sss_gno as unsigned),"
                    + "cast(ssss_gno as unsigned)").getResultList();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            }
            double shareValue = 0;
            String npaAcDetails = "";
//            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", repDt, "", brCode.equalsIgnoreCase("90") ? "0A" : brCode,"Y");
            for (int i = 0; i < oss1Query.size(); i++) {
                RbiSossPojo sossPojo = new RbiSossPojo();
                Vector oss1Vect = (Vector) oss1Query.get(i);
                Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                String reportName = oss1Vect.get(1).toString();
                String description = oss1Vect.get(2).toString();
                Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());

                Integer sGNo = oss1Vect.get(4).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(4).toString());
                Integer ssGNo = oss1Vect.get(5).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(5).toString());
                Integer sssGNo = oss1Vect.get(6).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(6).toString());
                Integer ssssGNo = oss1Vect.get(7).toString().equals("") ? 0 : Integer.parseInt(oss1Vect.get(7).toString());

                String classification = oss1Vect.get(8).toString();
                String countApplicable = oss1Vect.get(9).toString();
                String acNature = oss1Vect.get(10).toString();
                String acType = oss1Vect.get(11).toString();
                String glHeadFrom = oss1Vect.get(12).toString();

                String glHeadTo = oss1Vect.get(13).toString();
                String rangeBaseOn = oss1Vect.get(14).toString();
                String rangeFrom = oss1Vect.get(15).toString();
                String rangeTo = oss1Vect.get(16).toString();
                String formula = oss1Vect.get(17).toString();

                String fGNo = oss1Vect.get(18).toString();
                String fSGNo = oss1Vect.get(19).toString();
                String fSsGNo = oss1Vect.get(20).toString();
                String fSssGNo = oss1Vect.get(21).toString();
                String fSsssGNo = oss1Vect.get(22).toString();

                String npaClassification = oss1Vect.get(23).toString();

                AdditionalStmtPojo params = new AdditionalStmtPojo();
                params.setAcType(acType);
                params.setBrnCode(brCode);

                params.setClassification(classification);
                params.setDate(repDt);
                params.setToDate(repDt);
                params.setFromRange(rangeFrom);
                params.setGlFromHead(glHeadFrom);
                params.setGlToHead(glHeadTo);

                params.setNature(acNature);
                params.setOrgBrCode(brCode);
                params.setRangeBasedOn(rangeBaseOn);
                params.setToRange(rangeTo);
                params.setFlag(fSGNo);
//                params.setNpaAcList(resultList);

                AcctBalPojo acctBal;
                BigDecimal bal = new BigDecimal(0);

                if (rangeBaseOn.equalsIgnoreCase("Y")) {
                    params.setDate(CbsUtil.yearAdd(repDt, (int) Float.parseFloat(rangeTo)));
                    params.setToDate(CbsUtil.yearAdd(repDt, (int) Float.parseFloat(rangeTo)));
                }

                if (!((acNature == null) || acNature.equalsIgnoreCase(""))) {
                    if (fSGNo.equalsIgnoreCase("AFB")) {
                        String frDt = getMinFinYear(repDt);
                        params.setDate(frDt);
                        params.setToDate(repDt);
                        bal = getFortnightlyAvgBal(params).abs();
                    } else if (fSGNo.equalsIgnoreCase("S") || fSGNo.equalsIgnoreCase("SS") || fSGNo.equalsIgnoreCase("AC") || fSGNo.equalsIgnoreCase("NET")) {
                        bal = getSectorWiseBal(params, fGNo, fSGNo).abs();
                    } else {
                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        bal = acctBal.getBalance().abs();
                    }

                }
                if (!((acType == null) || acType.equalsIgnoreCase(""))) {
                    if (fSGNo.equalsIgnoreCase("AFB")) {
                        String frDt = getMinFinYear(repDt);
                        params.setDate(frDt);
                        params.setToDate(repDt);
                        bal = getFortnightlyAvgBal(params).abs();
                    } else if (fSGNo.equalsIgnoreCase("S") || fSGNo.equalsIgnoreCase("SS") || fSGNo.equalsIgnoreCase("AC") || fSGNo.equalsIgnoreCase("NET")) {
                        bal = getSectorWiseBal(params, fGNo, fSGNo).abs();
                    } else {
                        acctBal = rbiReportRemote.getAcctsAndBal(params);
                        bal = acctBal.getBalance().abs();
                    }
                } else if (!((glHeadFrom == null) || (glHeadFrom.equalsIgnoreCase(""))) && !((glHeadTo == null) || (glHeadTo.equalsIgnoreCase("")))) {
                    if (fSGNo.equalsIgnoreCase("AFB")) {
                        String frDt = getMinFinYear(repDt);
                        params.setDate(frDt);
                        params.setToDate(repDt);
                        bal = getFortnightlyAvgBal(params).abs();
                    } else {
                        bal = getOsBal(params).abs();
                    }
                } else if (fSGNo.equalsIgnoreCase("NOE")) {

                    bal = new BigDecimal(noOfEmp);

                } else if (fSGNo.equalsIgnoreCase("SV")) {

                    shareValue = certRemote.getperShareValue();

                    bal = new BigDecimal(shareValue);

                } else if (fSGNo.equalsIgnoreCase("DVDEC")) {
                    List<DividendTable> divList = divRemote.calculateDividend("All", shareValue, dividend, Integer.parseInt(getFinYear(repDt)), ymd.parse(repDt), "System", brCode,"0");
                    for (DividendTable divObj : divList) {
                        bal = bal.add(new BigDecimal(divObj.getDivamt()));
                    }
                    bal = new BigDecimal(dividend);
                } else if (fSGNo.equalsIgnoreCase("DV")) {
                    bal = new BigDecimal(dividend);
                } else if (fSGNo.equalsIgnoreCase("NDTL")) {
                    bal = hoReportFacade.getNewNdtl(brCode, repDt);
                }

                sossPojo.setsNo(sNo);
                sossPojo.setAcNature(acNature);
                sossPojo.setAcType(acType);
                sossPojo.setClassification(classification);

                sossPojo.setFormula(formula);
                sossPojo.setGlHeadTo(glHeadTo);
                sossPojo.setGlheadFrom(glHeadFrom);

                sossPojo.setReportName(reportName);
                sossPojo.setgNo(gNo);
                sossPojo.setsGNo(sGNo);

                sossPojo.setSsGNo(ssGNo);
                sossPojo.setSssGNo(sssGNo);
                sossPojo.setSsssGNo(ssssGNo);

                sossPojo.setDescription(description);
                sossPojo.setNpaClassification(npaClassification);
                sossPojo.setCountApplicable(countApplicable);

                sossPojo.setfGNo(fGNo);
                sossPojo.setfSGNo(fSGNo);
                sossPojo.setfSsGNo(fSsGNo);

                sossPojo.setfSssGNo(fSssGNo);
                sossPojo.setfSsssGNo(fSsssGNo);
                if (fSGNo.equalsIgnoreCase("DV")) {
                    sossPojo.setAmt(bal);
                } else {
                    sossPojo.setAmt(bal.divide(repOpt));
                }
                rbiPojoTable.add(sossPojo);
            }
            List<RbiSossPojo> oss7PartAList = getOss7PartA("OSS7-PART-A", repDt, brCode, repOpt, "Y", osBlancelist,"Y");
            List<RbiSossPojo> SOSS2 = rbiSoss1And2ReportRemote.getSOSS2("SOSS2", repDt, brCode, repOpt, "Y",osBlancelist,"0");

            for (int k = 0; k < rbiPojoTable.size(); k++) {

                RbiSossPojo rbiSossPojo = rbiPojoTable.get(k);

                if (!rbiSossPojo.getFormula().isEmpty()) {
                    if (rbiSossPojo.getFormula().contains("OSS7")) {
                        String[] strArr = rbiSossPojo.getFormula().split("#");
                        BigDecimal bal = rbiReportRemote.getValueFromFormula(oss7PartAList, strArr[1]);
                        rbiSossPojo.setAmt(bal);
                        rbiSossPojo.setDescription(rbiSossPojo.getDescription().concat(" (").concat(rbiSossPojo.getFormula()).concat(")"));
                    } else if (rbiSossPojo.getFormula().contains("SOSS2")) {
                        String[] strArr = rbiSossPojo.getFormula().split("#");
                        BigDecimal bal = rbiReportRemote.getValueFromFormula(SOSS2, strArr[1]);
                        rbiSossPojo.setAmt(bal);
                        rbiSossPojo.setDescription(rbiSossPojo.getDescription().concat(" (").concat(rbiSossPojo.getFormula()).concat(")"));
                    } else {
                        BigDecimal bal = rbiReportRemote.getValueFromFormula(rbiPojoTable, rbiSossPojo.getFormula());
                        rbiSossPojo.setAmt(bal);
                    }
                }
            }
            return rbiPojoTable;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getOsBal(AdditionalStmtPojo params) throws ApplicationException {
        try {
            int al = 1;
            if (params.getClassification().equals("A")) {
                al = -1;
            }
//            System.out.println("From-Head :" + params.getGlFromHead() + "To-Head :" + params.getGlToHead() + " And Date = " + params.getDate());
            List dataList = new ArrayList();
            if (params.getBrnCode().equals("0A") || params.getBrnCode().equals("90")) {
                if (params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {

                    dataList = em.createNativeQuery("select ifnull(sum(amt)*-1, 0) from cashinhand where ldate ='" + params.getDate() + "'").getResultList();
                } else if (params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue().substring(0, 1))
                        || params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue().substring(0, 1))) {

                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r "
                            + " where dt <='" + params.getDate() + "'  and trandesc <>13 and substring(r.acno,3,8) between '" + params.getGlFromHead()
                            + " ' and '" + params.getGlToHead() + "' having sign(sum(cramt-dramt)) = " + al).getResultList();
                } else {
                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r where dt <='"
                            + params.getDate() + "' and substring(r.acno,3,8) between '" + params.getGlFromHead() + " ' and '" + params.getGlToHead()
                            + "' having sign(sum(cramt-dramt)) = " + al).getResultList();
                }
            } else {
                if (params.getGlFromHead().trim().equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {

                    dataList = em.createNativeQuery("select ifnull(sum(amt)*-1, 0) from cashinhand where ldate ='" + params.getDate() + "'  and brncode = '" + params.getBrnCode() + "'").getResultList();
                } else if (params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue().substring(0, 1))
                        || params.getGlFromHead().trim().startsWith(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue().substring(0, 1))) {

                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r where dt <='" + params.getDate()
                            + "'  and trandesc <>13  and substring(r.acno,3,8) between '" + params.getGlFromHead() + " ' and '" + params.getGlToHead()
                            + "' and substring(r.acno,1,2)='" + params.getBrnCode() + "'  having sign(sum(cramt-dramt)) = " + al).getResultList();
                } else {

                    dataList = em.createNativeQuery("select cast(ifnull(sum(cramt- dramt),0) as decimal(14,2)) from gl_recon r where dt <='" + params.getDate()
                            + "' and substring(r.acno,3,8) between '" + params.getGlFromHead() + "' and '" + params.getGlToHead() + "' and substring(r.acno,1,2)='"
                            + params.getBrnCode() + "' having sign(sum(cramt-dramt)) = " + al).getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                Vector vec = (Vector) dataList.get(0);
                return new BigDecimal(vec.get(0).toString());
            } else {
                return new BigDecimal(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getFinYear(String dt) throws ApplicationException {
        try {
            List yearEndList = em.createNativeQuery("select f_year from cbs_yearend where '" + dt + "' between mindate and maxdate").getResultList();
            Vector vec = (Vector) yearEndList.get(0);
            return vec.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getMinFinYear(String dt) throws ApplicationException {
        try {
            List yearEndList = em.createNativeQuery("select mindate from cbs_yearend where '" + dt + "' between mindate and maxdate").getResultList();
            Vector vec = (Vector) yearEndList.get(0);
            return vec.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public BigDecimal getSectorWiseBal(AdditionalStmtPojo params, String fgno, String fsgno) throws ApplicationException {
        try {
            List dataList;
            int al = 1;
            if (params.getClassification().equals("A")) {
                al = -1;
            }
//            System.out.println("Inside sector wise balance  "+ fsgno);
            String tableName = "", additionalRepQuery = "", acCodeQuery = "", npaQuery = "", bd_query = "";
            String acNature = params.getNature();
            if (!acNature.equals("")) {
                if (acNature.contains(".")) {
                    acNature = acNature.substring(acNature.indexOf(".") + 1);
                }
                tableName = commonReport.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "')";
            } else if (!params.getAcType().equals("")) {
                String acctcode = params.getAcType();
                if (acctcode.contains(".")) {
                    acctcode = acctcode.substring(acctcode.indexOf(".") + 1);
                }
                acNature = commonReport.getAcNatureByAcType(acctcode);
                tableName = commonReport.getTableName(acNature);
                acCodeQuery = " and a.accttype in (select acctcode from accounttypemaster where acctcode = '" + acctcode + "')";
            }
            String npaAcDetails = "";
            if (fsgno.equalsIgnoreCase("NET")) {
//                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", params.getDate(), "", params.getBrnCode().equalsIgnoreCase("90") ? "0A" : params.getBrnCode(),"Y");
//                if (resultList.size() > 0) {
//                    for (int x = 0; x < resultList.size(); x++) {
//                        if (x == 0) {
//                            npaAcDetails = "'" + resultList.get(x).getAcno() + "'";
//                        } else {
//                            npaAcDetails = npaAcDetails + ", '" + resultList.get(x).getAcno() + "'";
//                        }
//                    }
//                }
                npaQuery = " and a.acno not in (select a.acno from accountstatus a,"
                        + " (select acno,max(effdt) as edt from accountstatus where effdt <='" + params.getDate() + "' group by acno) b,"
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='" + params.getDate() + "' group by acno) c ,"
                        + " accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and a.effdt = b.edt "
                        + " and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + params.getDate() + "' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + params.getDate() + "') )";
            }
            if (fsgno.equalsIgnoreCase("S") || fsgno.equalsIgnoreCase("SS") || fsgno.equalsIgnoreCase("AC")) {
                bd_query = ",cbs_loan_borrower_details b";
            }
            if (fsgno.equalsIgnoreCase("S")) {
                additionalRepQuery = "and r.acno = b.acc_no and b.sector='" + fgno + "'";
            }
            if (fsgno.equalsIgnoreCase("SS")) {
                additionalRepQuery = "and r.acno = b.acc_no and b.sub_sector='" + fgno + "'";
            }
            if (fsgno.equalsIgnoreCase("AC")) {
                additionalRepQuery = "and r.acno = b.acc_no and b.applicant_category='" + fgno + "'";
            }

            String executionQuery = "";
            if (params.getBrnCode().equalsIgnoreCase("0A") || params.getBrnCode().equalsIgnoreCase("90")) {
                executionQuery = "select cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                        + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno having sign(sum(cramt-dramt)) = " + al + ") "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and dt <='"
                        + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery;
//                System.out.println(fsgno+";"+fgno+";"+acNature+";"+params.getAcType()+"===>"+executionQuery);
                dataList = em.createNativeQuery(executionQuery).getResultList();
            } else {
                executionQuery = "select cast(IFNULL(sum(cramt- dramt),0) as decimal(14,2)) from " + tableName + " r,accountmaster a" + bd_query + " where "
                        + "r.acno in (select acno from " + tableName + " WHERE DT <= '" + params.getDate() + "' group by acno having sign(sum(cramt-dramt)) = " + al + ") "
                        + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + params.getDate() + "' ) and r.acno = a.acno and a.CurBrCode = '" + params.getBrnCode() + "' and dt <='"
                        + params.getDate() + "'" + acCodeQuery + additionalRepQuery + npaQuery;
                dataList = em.createNativeQuery(executionQuery).getResultList();
            }
            if (dataList.isEmpty()) {
                return new BigDecimal(0);
            } else {
                Vector vec = (Vector) dataList.get(0);
                return new BigDecimal(Double.parseDouble(vec.get(0).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getFortnightlyAvgBal(AdditionalStmtPojo params) throws ApplicationException {
        try {
            List dataList;
            if (params.getAcType().equals("")) {
                dataList = em.createNativeQuery("select ifnull(avg(a.sumbal),0) from (select cast(sum(fb.bal) as decimal(14,2)) as sumBal from ho_reportingfriday rf, "
                        + "fortnight_balance fb where fb.date = date_format(rf.REPFRIDATE,'%Y%m%d') and date_format(rf.REPFRIDATE,'%Y%m%d') between '"
                        + params.getDate() + "' and '" + params.getToDate() + "' and classification = '" + params.getClassification() + "' and ACCODE in "
                        + "(select substring(acno,3,8) from gltable where substring(acno,3,8) between '" + params.getGlFromHead() + "' and '"
                        + params.getGlToHead() + "') group by date_format(rf.REPFRIDATE,'%Y%m%d')) a ").getResultList();
            } else {
                dataList = em.createNativeQuery("select ifnull(avg(a.sumbal),0) from (select cast(sum(fb.bal) as decimal(14,2)) as sumBal from ho_reportingfriday rf, "
                        + "fortnight_balance fb where fb.date = date_format(rf.REPFRIDATE,'%Y%m%d') and date_format(rf.REPFRIDATE,'%Y%m%d') between '" + params.getDate()
                        + "' and '" + params.getToDate() + "' and classification = '" + params.getClassification() + "' and ACCODE ='"+params.getAcType()+"' /* in "
                        + " (select acctcode from accounttypemaster where acctnature = '" + params.getNature() + "')*/ group by date_format(rf.REPFRIDATE,'%Y%m%d') ) a ").getResultList();

            }
            if (dataList.isEmpty()) {
                return new BigDecimal(0);
            } else {
                Vector vec = (Vector) dataList.get(0);
                return new BigDecimal(Double.parseDouble(vec.get(0).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getAcCodeAmount(String accode, String dt) throws ApplicationException {
        BigDecimal amount = new BigDecimal("0"), amt = new BigDecimal("0");;
        try {
            List amountList = null;
            if (accode.contains("SSI")) {
                List accList = em.createNativeQuery("select a.acno from cbs_loan_borrower_details l, accountmaster a where a.acno = l.acc_no and  l.type_of_advance in('" + accode.substring(accode.indexOf(".") + 1) + "') and sub_sector = 'SE' and a.openingdt <='" + dt + "'").getResultList();
                if (accList.size() > 0) {
                    for (int i = 0; i < accList.size(); i++) {
                        Vector accVect = (Vector) accList.get(i);
                        String acNo = accVect.get(0).toString();
                        String acNature = commonReport.getAcNatureByAcNo(acNo);
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from ca_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from loan_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                        }
                        Vector element = (Vector) amountList.get(0);
                        amount = amount.add(new BigDecimal(element.get(0).toString()));
                    }
                }
            } else if (accode.contains("LIEN")) {
                String reportParameter = "";
                List parameterStatus = em.createNativeQuery("select code from cbs_parameterinfo where name = 'LIEN_PREVIOUS'").getResultList();
                List drCrParameter = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name = 'DRCRFORLIEN'").getResultList();
                String drCrLien="";
                if(!drCrParameter.isEmpty()) {
                    Vector vect = (Vector) drCrParameter.get(0);
                    drCrLien = vect.get(0).toString();                    
                }
                /* DRCRFORLIEN ,Y for Both debit and Credit Amount and N For  only Debit Balance */
                if (!parameterStatus.isEmpty()) {
                    Vector paraStatusvect = (Vector) parameterStatus.get(0);
                    reportParameter = paraStatusvect.get(0).toString();
                    if (ymd.parse(reportParameter).after(ymd.parse(dt))) {
                        List accList = em.createNativeQuery("select distinct acno from loansecurity where substring(lienacno,3,2) in ('" + accode.substring(accode.indexOf(".") + 1) + "') and status = 'ACTIVE' and entrydate<='" + dt + "' "
                                + " union all "
                                + " select distinct acno  from loansecurity where substring(lienacno,3,2) in ('" + accode.substring(accode.indexOf(".") + 1) + "') and ExpiryDate >='20141014' and entrydate<='" + dt + "'  order by acno").getResultList();
                        if (accList.size() > 0) {
                            for (int i = 0; i < accList.size(); i++) {
                                Vector accVect = (Vector) accList.get(i);
                                String acNo = accVect.get(0).toString();
                                String acNature = commonReport.getAcNatureByAcNo(acNo);
                                List lienSecAcNoList = null;
                                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from ca_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                                    lienSecAcNoList = em.createNativeQuery("select sum(a.amt) from (select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' and matdate >='" + dt + "' and lienacno is not null"
                                            + " union all "
                                            + " select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and ExpiryDate >='20141014' and entrydate<='" + dt + "' and lienacno is not null ) a"
                                            + "").getResultList();
                                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                    amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from loan_recon where acno = '" + acNo + "' and dt <='" + dt + "'").getResultList();
                                    lienSecAcNoList = em.createNativeQuery("select sum(a.amt) from (select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' and entrydate<='" + dt + "' and lienacno is not null "
                                            + " union all "
                                            + " select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and ExpiryDate >='20141014' and entrydate<='" + dt + "' and lienacno is not null ) a"
                                            + " ").getResultList();
                                }

                                Vector element = (Vector) amountList.get(0);
                                BigDecimal amt1 = new BigDecimal(element.get(0).toString());
                                Vector lienSecAcNoVect = (Vector) lienSecAcNoList.get(0);
                                BigDecimal lienSecAcNoAmt = new BigDecimal(lienSecAcNoVect.get(0).toString());
                                if (lienSecAcNoAmt.compareTo(amt1) == -1) {
                                    amt1 = lienSecAcNoAmt;
                                } else if (lienSecAcNoAmt.compareTo(amt1) == 0) {
                                    amt1 = lienSecAcNoAmt;
                                } else if (lienSecAcNoAmt.compareTo(amt1) == 1) {
                                    amt1 = amt1;
                                }
                                amount = amount.add(amt1);
                            }
                        }
                    } else {
                        List<LienReportPojo> acutalBalList = loanRemote.getLienReportNew(accode.substring(accode.indexOf(".") + 1), dt, "90");
                        for (LienReportPojo pojo : acutalBalList) {
                            if(drCrLien.equalsIgnoreCase("N")){
                                if(new BigDecimal(pojo.getActualValue()).compareTo(new BigDecimal("0")) >0) {
                                    amt= new BigDecimal(pojo.getActualValue());
                                } else {
                                    amt = new BigDecimal("0");
                                }
                            } else {
                                amt = new BigDecimal(pojo.getActualValue());
                            }
                            amount = amount.add(amt);
                            // BigDecimal accbal1 = new BigDecimal(accbal);
                        }
                    }
                } else {
                    List<LienReportPojo> acutalBalList = loanRemote.getLienReportNewOptimized(accode.substring(accode.indexOf(".") + 1), dt, "90");
                    for (LienReportPojo pojo : acutalBalList) {
                        if(drCrLien.equalsIgnoreCase("N")){
                            if(new BigDecimal(pojo.getActualValue()).compareTo(new BigDecimal("0")) >0) {
                                    amt= new BigDecimal(pojo.getActualValue());
                            } else {
                                amt = new BigDecimal("0");
                            }
                        } else {
                            amt = new BigDecimal(pojo.getActualValue());
                        }
                        amount = amount.add(amt);
                        // BigDecimal accbal1 = new BigDecimal(accbal);
                    }

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return amount;
    }
}
