/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.ArfAccPojo;
import com.cbs.dto.report.ArfBatPojo;
import com.cbs.dto.report.ArfBrcPojo;
import com.cbs.dto.report.ArfInpPojo;
import com.cbs.dto.report.ArfLpePojo;
import com.cbs.dto.report.ArfRptPojo;
import com.cbs.dto.report.ArfTrnPojo;
import com.cbs.dto.report.CbaAccPojo;
import com.cbs.dto.report.CbaInpPojo;
import com.cbs.dto.report.CbaLpePojo;
import com.cbs.dto.report.CbaTrnPojo;
import com.cbs.dto.report.CtrArfPojo;
import com.cbs.dto.report.CtrPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.AirPojo;
import com.cbs.pojo.Form61APojo;
import com.cbs.pojo.strCommonPanMobilePhoneEmailPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Stateless(mappedName = "CtrStrAirReportFacade")
public class CtrStrAirReportFacade implements CtrStrAirReportFacadeRemote {

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
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public CtrPojo ctrReport(List acNatureList, String acType, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType) throws ApplicationException {
//        List<CtrPojo> ctrPojos = new ArrayList<CtrPojo>();
        CtrPojo ctr = new CtrPojo();
        List<CbaAccPojo> cbaAccPojos = new ArrayList<CbaAccPojo>();
        List<CbaTrnPojo> cbaTrnPojos = new ArrayList<CbaTrnPojo>();
        List<CbaInpPojo> cbaInpPojos = new ArrayList<CbaInpPojo>();
        List<CbaLpePojo> cbaLpePojos = new ArrayList<CbaLpePojo>();

        try {
            Integer cbaAccLine = 0, inpLineNo = 0;;
            Integer year;
            if (toDt.substring(4, 6).equalsIgnoreCase("01") || toDt.substring(4, 6).equalsIgnoreCase("02") || toDt.substring(4, 6).equalsIgnoreCase("03")) {
                year = (Integer.parseInt(toDt.substring(0, 4)) - 1);
            } else {
                year = Integer.parseInt(toDt.substring(0, 4));
            }

            for (int i = 0; i < acNatureList.size(); i++) {
                String acType1 = "";
                Vector acnatureVect = (Vector) acNatureList.get(i);
                String acNature = acnatureVect.get(0).toString();
                List acTypeList = common.getAccType(acnatureVect.get(0).toString());
                if (acTypeList.size() > 0) {
                    for (int z = 0; z < acTypeList.size(); z++) {
                        Vector actypevect = (Vector) acTypeList.get(z);
                        if (z == 0) {
                            acType1 = "'" + actypevect.get(0).toString() + "'";
                        } else {
                            acType1 = acType1.concat(",'" + actypevect.get(0).toString() + "'");
                        }
                    }
                }
                acType = acType1;
                String preAcNo = "0";
                List acTransactionDetailsList;
                if (repType.equalsIgnoreCase("STR")) {
                    acTransactionDetailsList = em.createNativeQuery("select a.acno,a.dt,cast(ifnull(sum(ifnull(r.cramt,0)),0) as decimal(15,2)),cast(ifnull(sum(ifnull(r.dramt,0)),0) as decimal(15,2)),"
                            + "r.trantype, r.ty from cbs_str_detail a," + CbsUtil.getReconTableName(acNature) + " r where a.acno = r.acno and a.flag = 'STR' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.dt=r.dt "
                            + "and substring(a.acno,1,2) in(" + brCode + ")and substring(a.acno,3,2)in(" + acType + ") group by a.acno,a.dt "
                            + "union "
                            + "select r.acno,dt,cast(ifnull(sum(ifnull(r.cramt,0)),0) as decimal(15,2)), cast(ifnull(sum(ifnull(r.dramt,0)),0) as decimal(15,2)),"
                            + "r.trantype,r.ty from " + CbsUtil.getReconTableName(acNature) + " r,(select acno from customerid where custid in(select customerid from cbs_customer_master_detail where strFlag ='WL1.1'))b "
                            + "where r.acno=b.acno and r.dt between '" + fromDt + "' and '" + toDt + "' and substring(b.acno,1,2)in(" + brCode + ")and substring(b.acno,3,2)in(" + acType + ") "
                            + "group by b.acno,r.dt").getResultList();
                } else {
                    acTransactionDetailsList = em.createNativeQuery("select acno,dt,cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(15,2)), "
                            + " cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(15,2)),trantype, ty from " + CbsUtil.getReconTableName(acNature) + " where "
                            + " dt between '" + fromDt + "' and '" + toDt + "' and substring(acno,1,2) in (" + brCode + ")  and substring(acno,3,2) in (" + acType + ") and trantype=0 and "
                            + " (acno in (select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                            + " substring(acno,1,2) in (" + brCode + ")   and substring(acno,3,2) in (" + acType + ") and trantype=0 group by acno having(sum(cramt) between " + fromAmt + " and " + toAmt + ")) "
                            + " or acno in (select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                            + " substring(acno,1,2) in (" + brCode + ")   and substring(acno,3,2) in (" + acType + ") and trantype=0 group by acno having(sum(dramt) between " + fromAmt + " and " + toAmt + "))) "
                            + " group by acno,dt, trantype, ty order by acno,dt, ty, trantype").getResultList();
                }

                for (int j = 0; j < acTransactionDetailsList.size(); j++) {
                    CbaTrnPojo cbaTrn = new CbaTrnPojo();

                    Vector acTranVect = (Vector) acTransactionDetailsList.get(j);
                    String acNo = acTranVect.get(0).toString();
                    String dt = acTranVect.get(1).toString();
                    BigDecimal crAmt = new BigDecimal(acTranVect.get(2).toString());
                    BigDecimal drAmt = new BigDecimal(acTranVect.get(3).toString());
                    String tranType = acTranVect.get(4).toString();
                    String ty = acTranVect.get(5).toString();
                    /**
                     * *************************************
                     * START CBATRN * *************************************
                     */
                    cbaTrn.setRecordType("TRN");
                    cbaTrn.setLineNumber(j + 1);
                    cbaTrn.setBranchReferenceNumber("");
                    cbaTrn.setAcNo(acNo);
                    cbaTrn.setTransactionID("");
                    cbaTrn.setDateOfTransaction(dmy.format(ymd_1.parse(dt)));
                    cbaTrn.setModeOfTransaction("C");
                    if (ty.equalsIgnoreCase("0")) {
                        cbaTrn.setDrCr("C");
                        cbaTrn.setAmount(crAmt);
                    } else {
                        cbaTrn.setDrCr("D");
                        cbaTrn.setAmount(drAmt);
                    }
                    cbaTrn.setCurrencyOfTransaction("INR");
                    cbaTrn.setDispositionOfFunds("X");
                    cbaTrn.setRemarks("");

                    cbaTrnPojos.add(cbaTrn);
                    /**
                     * ********** END OF CBATRN *********
                     */
                    /**
                     * *************************************
                     * START CBAACC/CBALPE *
                     * *************************************
                     */
                    if (!preAcNo.equalsIgnoreCase(acNo)) {
                        cbaAccLine = cbaAccLine + 1;
                        CbaAccPojo cbaAcc = new CbaAccPojo();
                        CbaLpePojo cbaLpe = new CbaLpePojo();
                        /**
                         * ** Customer Information ***
                         */
                        List acNoDetails = em.createNativeQuery("select ifnull(c.CustFullName,''), a.openingdt, a.opermode, ifnull(c.operationalriskrating,''), b.custid, ifnull(c.pan_girnumber,''), "
                                + " ifnull(c.peraddressline1,''), ifnull(c.peraddressline2,''), ifnull(c.pervillage,''), ifnull(c.perblock,''), ifnull(c.percitycode,''), ifnull(c.perstatecode,''),"
                                + " ifnull(c.perpostalcode,''), ifnull(c.percountrycode,''), ifnull(c.perphonenumber,''), ifnull(c.pertelexnumber,''), ifnull(c.perfaxnumber,''), "
                                + " ifnull(c.mailaddressline1,''), ifnull(c.mailaddressline2,''), ifnull(c.mailvillage,''), ifnull(c.mailblock, ''), ifnull(c.mailcitycode, ''),"
                                + " ifnull(c.mailstatecode, ''), ifnull(c.mailpostalcode, ''), ifnull(c.mailcountrycode, ''), ifnull(c.mailphonenumber, ''), ifnull(c.mailtelexnumber, ''),"
                                + " ifnull(c.mailfaxnumber, ''), ifnull(c.mobilenumber,''), ifnull(c.emailid,'')   from accountmaster a, customerid b, cbs_customer_master_detail c "
                                + " where a.acno = b.acno and b.custid = c.customerid and a.acno =  '" + acNo + "'").getResultList();
                        Vector acNoVect = (Vector) acNoDetails.get(0);
                        String custName = acNoVect.get(0).toString();
                        String openingDt = acNoVect.get(1).toString();
                        String operMode = acNoVect.get(2).toString();
                        String riskCategory = acNoVect.get(3).toString();
                        String custId = acNoVect.get(4).toString();
                        String pan = acNoVect.get(5).toString();
                        String perAddressLine1 = acNoVect.get(6).toString();
                        String perAddressLine2 = acNoVect.get(7).toString();
                        String perVillage = acNoVect.get(8).toString();
                        String perBlock = acNoVect.get(9).toString();
                        String perCityCode = acNoVect.get(10).toString();
                        String perStateCode = acNoVect.get(11).toString();
                        String perPostalCode = acNoVect.get(12).toString();
                        String perCountryCode = acNoVect.get(13).toString();
                        String perPhoneNumber = acNoVect.get(14).toString();
                        String perTelexNumber = acNoVect.get(15).toString();
                        String perFaxNumber = acNoVect.get(16).toString();
                        String mailAddressLine1 = acNoVect.get(17).toString();
                        String mailAddressLine2 = acNoVect.get(18).toString();
                        String mailVillage = acNoVect.get(19).toString();
                        String mailBlock = acNoVect.get(20).toString();
                        String mailCityCode = acNoVect.get(21).toString();
                        String mailStateCode = acNoVect.get(22).toString();
                        String mailPostalCode = acNoVect.get(23).toString();
                        String mailCountryCode = acNoVect.get(24).toString();
                        String mailPhoneNumber = acNoVect.get(25).toString();
                        String mailTelexNumber = acNoVect.get(26).toString();
                        String mailFaxNumber = acNoVect.get(27).toString();
                        String mobileNo = acNoVect.get(28).toString();
                        String eMailId = acNoVect.get(29).toString();
                        YearEndDatePojo yDate = new YearEndDatePojo();
                        yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingToDate(acNo.substring(0, 2), toDt);
                        String minFDate = yDate.getMinDate();
                        String maxFDate = yDate.getMaxDate();
                        String fyear = yDate.getfYear();
                        /**
                         * ** Cash Transaction Information ***
                         */
                        List cumCashTranList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt between '" + minFDate + "' and '" + toDt + "' and trantype=0").getResultList();
                        Vector cumCashTranVect = (Vector) cumCashTranList.get(0);
                        BigDecimal cashCrAmt = new BigDecimal(cumCashTranVect.get(0).toString());
                        BigDecimal cashDrAmt = new BigDecimal(cumCashTranVect.get(1).toString());
                        /**
                         * ** All Transaction Information ***
                         */
                        List cumAllTranList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt between '" + minFDate + "' and '" + toDt + "'").getResultList();
                        Vector cumAllTranVect = (Vector) cumAllTranList.get(0);
                        BigDecimal allCrAmt = new BigDecimal(cumAllTranVect.get(0).toString());
                        BigDecimal allDrAmt = new BigDecimal(cumAllTranVect.get(1).toString());

                        /**
                         * Start of CBAACC *
                         */
                        cbaAcc.setRecordType("REC");
                        cbaAcc.setMonthOfReport(toDt.substring(4, 6));
                        cbaAcc.setYearOfReport(year.toString());
                        cbaAcc.setLineNumber(cbaAccLine);
                        cbaAcc.setBranchReferenceNumber("");
                        cbaAcc.setAcNo(acNo);
                        cbaAcc.setName(custName);
                        if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                            cbaAcc.setTypeOfAccount("B");
                        } else if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())) {
                            cbaAcc.setTypeOfAccount("C");
                        } else if (acNo.substring(2, 4).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            cbaAcc.setTypeOfAccount("A");
                        } else {
                            cbaAcc.setTypeOfAccount("Z");
                        }
                        /*  1	SELF
                         2	EITHER OR SURVIVOR
                         3	FORMER OR SURVIVOR
                         4	ANY ONE OR SURVIVOR
                         5	ANY TWO JOINTLY
                         6	ANY THREE JOINTLY
                         7	UNDER POWER OF ATTOR
                         8	PROPRIETOR
                         9	AUTHORIZED SIGNATORY
                         10	M.D.
                         11	UNDER GUARDIANSHIP
                         12	BOTH OF TWO JOINTLY
                         13	MINOR
                         14	ANY FOUR JOINTLY
                         15	ANY FIVE JOINTLY
                         16	ALL JOINTLY
                         17	JOINTLY*/
                        if (operMode.equalsIgnoreCase("1")) {
                            cbaAcc.setTypeOfAccountHolder("A");
                        } else {
                            cbaAcc.setTypeOfAccountHolder("B");
                        }

                        cbaAcc.setDateOfAccountOpening(dmy.format(ymd.parse(openingDt)));

                        /*
                         * In OUR SYSTEM:
                         *  024	C1	LOW RISK CATEGORY       03
                         024	C2	MEDIUM RISK CATEGORY    02
                         024	C3	HIGH RISK CATEGORY      01
                         024	C4	VERY HIGH RISK CATEGORY
                         * 
                         * IN RBI SYSTEM:
                         *  A       LOW RISK
                         *  B       MEDIUM RISK
                         *  C       HIGH RISK
                         */
                        if (riskCategory.equalsIgnoreCase("03")) {
                            cbaAcc.setRiskCategory("A");
                        } else if (riskCategory.equalsIgnoreCase("02")) {
                            cbaAcc.setRiskCategory("B");
                        } else if (riskCategory.equalsIgnoreCase("01")) {
                            cbaAcc.setRiskCategory("C");
                        } else if (riskCategory.equalsIgnoreCase("C4")) {
                            cbaAcc.setRiskCategory("C");
                        } else {
                            cbaAcc.setRiskCategory("C");
                        }

                        cbaAcc.setCumulativeCreditTurnover(allCrAmt);
                        cbaAcc.setCumulativeDebitTurnover(allDrAmt);
                        cbaAcc.setCumulativeCashDepositTurnover(cashCrAmt);
                        cbaAcc.setCumulativeCashWithdrawlTurnover(cashDrAmt);

                        cbaAccPojos.add(cbaAcc);
                        /**
                         * End of CBAACC *
                         */
                        /**
                         * Start of CBALPE *
                         */
                        cbaLpe.setRecordType("LPE");
                        cbaLpe.setMonthOfReport(toDt.substring(4, 6));
                        cbaLpe.setYearOfReport(year.toString());
                        cbaLpe.setLineNumber(cbaAccLine);
                        cbaLpe.setBranchReferenceNumber("");
                        cbaLpe.setAcNo(acNo);
                        cbaLpe.setRelationFlag("A");
                        cbaLpe.setNameOfLegalPerson(custName);
                        cbaLpe.setCustomerID(custId);
                        cbaLpe.setNatureOfBusiness("");
                        cbaLpe.setDateOfIncorporation("");
                        cbaLpe.setTypeOfConstitution("");
                        cbaLpe.setRegistrationNumber("");
                        cbaLpe.setRegisteringAuthority("");
                        cbaLpe.setPlaceOfRegistration("");
                        cbaLpe.setPan(pan);
                        cbaLpe.setCommunicationAddress1(mailAddressLine1);                                                              //No, building
                        cbaLpe.setCommunicationAddress2(mailAddressLine2);                                                              //Street, Road
                        cbaLpe.setCommunicationAddress3(mailBlock);                                                                     //Locality
                        cbaLpe.setCommunicationAddress4(common.getRefRecDesc("001", mailCityCode));                                     //City/Town, District
                        cbaLpe.setCommunicationAddress5(common.getRefRecDesc("002", mailStateCode).concat(common.getRefRecDesc("003", mailCountryCode).equalsIgnoreCase("") ? "" : common.getRefRecDesc("003", mailCountryCode)));            //State, Country
                        cbaLpe.setCommunicationAddressPinCode(mailPostalCode);                                                          //Pin Code
                        cbaLpe.setContactTelephone(mailPhoneNumber.concat(mobileNo.equalsIgnoreCase("") ? mobileNo : ", " + mobileNo));       //Telephone No (With STD Code)
                        cbaLpe.setContactFax(mailFaxNumber);                                                                            //Fax No (With STD Code)
                        cbaLpe.setContactEmail(eMailId);                                                                                //Email Id
                        cbaLpe.setRegisteredAddress1("");
                        cbaLpe.setRegisteredAddress2("");
                        cbaLpe.setRegisteredAddress3("");
                        cbaLpe.setRegisteredAddress4("");
                        cbaLpe.setRegisteredAddress5("");
                        cbaLpe.setRegisteredAddressPinCode("");
                        cbaLpe.setRegisteredOfficeTelephone("");
                        cbaLpe.setRegisteredOfficeFax("");

                        cbaLpePojos.add(cbaLpe);
                        /**
                         * End of CBALPE *
                         */
                        /**
                         * Start of CBAINP *
                         */
                        List jointList;
                        if (operMode.equalsIgnoreCase("1")) {
                            jointList = em.createNativeQuery("select ifnull(cast(custid as char),'') from customerid  where acno = '" + acNo + "'").getResultList();
                        } else {
                            jointList = em.createNativeQuery("select ifnull(custid1,'') from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid2,'') from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid3,'') from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid4,'') from accountmaster where acno='" + acNo + "'").getResultList();
                        }
                        for (int k = 0; k < jointList.size(); k++) {
                            Vector jointVectList = (Vector) jointList.get(k);
                            String jointCustId = jointVectList.get(0).toString();
                            if (!jointCustId.equalsIgnoreCase("")) {
                                inpLineNo = inpLineNo + 1;
                                CbaInpPojo cbaInp = new CbaInpPojo();
                                List jointDetails = em.createNativeQuery("select ifnull(c.CustFullName,''),ifnull(c.gender,''),"
                                        + " concat(ifnull(c.fathername,''),if(ifnull(c.FatherMiddleName,'')= '',ifnull(c.FatherMiddleName,''),concat(' ', ifnull(c.FatherMiddleName,''))),"
                                        + " if(ifnull(c.FatherLastName,'')= '', ifnull(c.FatherLastName,''),concat(' ', ifnull(c.FatherLastName,'')))) as FatherName,ifnull(c.dateofbirth,''),ifnull(c.pan_girnumber,''), "
                                        + " ifnull(c.peraddressline1,''), ifnull(c.peraddressline2,''), ifnull(c.pervillage,''), ifnull(c.perblock,''), ifnull(c.percitycode,''), ifnull(c.perstatecode,''),"
                                        + " ifnull(c.perpostalcode,''), ifnull(c.percountrycode,''), ifnull(c.perphonenumber,''), ifnull(c.pertelexnumber,''), ifnull(c.perfaxnumber,''), "
                                        + " ifnull(c.mailaddressline1,''), ifnull(c.mailaddressline2,''), ifnull(c.mailvillage,''), ifnull(c.mailblock, ''), ifnull(c.mailcitycode, ''),"
                                        + " ifnull(c.mailstatecode, ''), ifnull(c.mailpostalcode, ''), ifnull(c.mailcountrycode, ''), ifnull(c.mailphonenumber, ''), ifnull(c.mailtelexnumber, ''),"
                                        + " ifnull(c.mailfaxnumber, ''), ifnull(c.mobilenumber,''), ifnull(c.emailid,'') from cbs_customer_master_detail c where c.customerid = '" + jointCustId + "'").getResultList();
                                Vector jointVect = (Vector) jointDetails.get(0);
                                String jointCustName = jointVect.get(0).toString();
                                String jointGender = jointVect.get(1).toString();
                                String jointFatherName = jointVect.get(2).toString();
                                String jointDob = jointVect.get(3).toString();
                                String jointPan = jointVect.get(4).toString();
                                String jointPerAddressLine1 = jointVect.get(5).toString();
                                String jointPerAddressLine2 = jointVect.get(6).toString();
                                String jointPerVillage = jointVect.get(7).toString();
                                String jointPerBlock = jointVect.get(8).toString();
                                String jointPerCityCode = jointVect.get(9).toString();
                                String jointPerStateCode = jointVect.get(10).toString();
                                String jointPerPostalCode = jointVect.get(11).toString();
                                String jointPerCountryCode = jointVect.get(12).toString();
                                String jointPerPhoneNumber = jointVect.get(13).toString();
                                String jointPerTelexNumber = jointVect.get(14).toString();
                                String jointPerFaxNumber = jointVect.get(15).toString();
                                String jointMailAddressLine1 = jointVect.get(16).toString();
                                String jointMailAddressLine2 = jointVect.get(17).toString();
                                String jointMailVillage = jointVect.get(18).toString();
                                String jointMailBlock = jointVect.get(19).toString();
                                String jointMailCityCode = jointVect.get(20).toString();
                                String jointMailStateCode = jointVect.get(21).toString();
                                String jointMailPostalCode = jointVect.get(22).toString();
                                String jointMailCountryCode = jointVect.get(23).toString();
                                String jointMailPhoneNumber = jointVect.get(24).toString();
                                String jointMailTelexNumber = jointVect.get(25).toString();
                                String jointMailFaxNumber = jointVect.get(26).toString();
                                String jointMobileNo = jointVect.get(27).toString();
                                String jointEMailId = jointVect.get(28).toString();

                                cbaInp.setRecordType("INP");
                                cbaInp.setMonthOfReport(toDt.substring(4, 6));
                                cbaInp.setYearOfReport(year.toString());
                                cbaInp.setLineNumber(inpLineNo);
                                cbaInp.setBranchReferenceNumber("");
                                cbaInp.setAcNo(acNo);
                                cbaInp.setRelationFlag("C");
                                cbaInp.setName(jointCustName);
                                cbaInp.setCustomerID(jointCustId);
                                cbaInp.setFatherSpouseName(jointFatherName);
                                cbaInp.setOccupation("");
                                cbaInp.setDob(dmy.format(ymd_1.parse(jointDob)).equalsIgnoreCase("01041900") ? "" : dmy.format(ymd_1.parse(jointDob)).equalsIgnoreCase("01011900") ? "" : dmy.format(ymd_1.parse(jointDob)));
                                cbaInp.setSex(jointGender);
                                cbaInp.setNationality("IN");
                                cbaInp.setTypeIOfIdentification("");
                                cbaInp.setIdNo("");
                                cbaInp.setIssuingAuthority("");
                                cbaInp.setPlaceOfIssue("");
                                cbaInp.setPan(jointPan);
                                cbaInp.setCommunicationAddress1(jointMailAddressLine1);                                                              //No, building
                                cbaInp.setCommunicationAddress2(jointMailAddressLine2);                                                              //Street, Road
                                cbaInp.setCommunicationAddress3(jointMailBlock);                                                                     //Locality
                                cbaInp.setCommunicationAddress4(common.getRefRecDesc("001", jointMailCityCode));                                     //City/Town, District
                                cbaInp.setCommunicationAddress5(common.getRefRecDesc("002", jointMailStateCode).concat(common.getRefRecDesc("003", jointMailCountryCode).equalsIgnoreCase("") ? "" : common.getRefRecDesc("003", jointMailCountryCode)));            //State, Country
                                cbaInp.setCommunicationAddressPinCode(jointMailPostalCode);                                                          //Pin Code
                                cbaInp.setContactTelephone(jointMailPhoneNumber);       //Telephone No (With STD Code)
                                cbaInp.setContactMobilenumber(jointMobileNo);
                                cbaInp.setContactEMail(jointEMailId);
                                cbaInp.setPlaceOfWork("");
                                cbaInp.setSecondAddress1(perAddressLine1);                                                                           //No, building
                                cbaInp.setSecondAddress2(perAddressLine2);                                                                           //Street, Road
                                cbaInp.setSecondAddress3(jointPerBlock);                                                                             //Locality
                                cbaInp.setSecondAddress4(common.getRefRecDesc("001", jointPerCityCode));                                             //City/Town, District
                                cbaInp.setSecondAddress5(common.getRefRecDesc("002", jointPerStateCode).concat(common.getRefRecDesc("003", jointPerCountryCode).equalsIgnoreCase("") ? "" : common.getRefRecDesc("003", jointPerCountryCode)));            //State, Country
                                cbaInp.setSecondAddressPinCode(jointPerPostalCode);                                                                  //Pin Code
                                cbaInp.setSecondTelephone(jointPerPhoneNumber);                                                                      //Telephone No (With STD Code)

                                cbaInpPojos.add(cbaInp);
                            }
                        }
                        /**
                         * End of CBAINP *
                         */
                        preAcNo = acNo;
                    }
                    /**
                     * ********** END OF CBAACC/CBALPE *********
                     */
                }
                ctr.setCbaAcc(cbaAccPojos);
                ctr.setCbaInp(cbaInpPojos);
                ctr.setCbaLpe(cbaLpePojos);
                ctr.setCbaTrn(cbaTrnPojos);
            }
            return ctr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public List<ArfBatPojo> getArfBatData(String brCode, String repType, String userName, String frDt, String toDt) throws ApplicationException {
        List<ArfBatPojo> arfBatPojos = new ArrayList<ArfBatPojo>();
        try {
            // ARFBAT
            String arfbat[] = {"Line Number*", "ReportType*", "DataStructureVersion*", "ReportingEntityName*", "ReportingEntityCategory*",
                "RERegistrationNumber", "FIUREID*", "POName*", "PODesignation*", "Address*",
                "City", "StateCode*", "PinCode", "CountryCode*", "Telephone",
                "Mobile", "Fax", "POEmail*", "BatchNumber*", "BatchDate*",
                "MonthOfReport*", "YearOfReport*", "OperationalMode*", "BatchType*", "OriginalBatchId*",
                "ReasonOfRevision*"};

            List result = em.createNativeQuery("select c.bankname,u.UserName,u.designation,b.Address,b.City,b.State as StateCode,b.PinCode,'IN'CountryCode,"
                    + "p.BrPhone,''Mobile,ifnull(p.BrFax,''),ifnull(email,'') as POEmail ,ifnull(fiureid,'BANUCXXXXX') from branchmaster b,parameterinfo p,(select LevelId as designation,UserName "
                    + "from securityinfo where UserId = '" + userName + "' /*and orgbrncode = " + brCode + "*/)u,bnkadd c where b.BrnCode = p.BrnCode and b.BrnCode = " + brCode + " "
                    + "and b.AlphaCode = c.alphacode").getResultList();

            Vector vtr = (Vector) result.get(0);

            for (int j = 0; j < arfbat.length; j++) {
                ArfBatPojo arf1 = new ArfBatPojo();
                if (j == 0) {
                    arf1.setDescription("Line Number*");
                    arf1.setValue("1");
                } else if (j == 1) {
                    arf1.setDescription("ReportType*");
                    arf1.setValue(repType);
                } else if (j == 2) {
                    arf1.setDescription("DataStructureVersion*");
                    arf1.setValue("2");
                } else if (j == 3) {
                    arf1.setDescription("ReportingEntityName*");
                    arf1.setValue(vtr.get(0).toString());
                } else if (j == 4) {
                    arf1.setDescription("ReportingEntityCategory*");
                    arf1.setValue("BANUC");
                } else if (j == 5) {
                    arf1.setDescription("RERegistrationNumber");
                    arf1.setValue(vtr.get(12).toString());
                } else if (j == 6) {
                    arf1.setDescription("FIUREID*");
                    arf1.setValue(vtr.get(12).toString());
                    // ******User Detail******
                } else if (j == 7) {
                    arf1.setDescription("POName*");
                    arf1.setValue(vtr.get(1).toString());
                } else if (j == 8) {
                    arf1.setDescription("PODesignation*");
                    if (vtr.get(2).toString().equalsIgnoreCase("1")) {
                        arf1.setValue("Manager");
                    } else if (vtr.get(2).toString().equalsIgnoreCase("2")) {
                        arf1.setValue("Asst.Manager");
                    } else if (vtr.get(2).toString().equalsIgnoreCase("3")) {
                        arf1.setValue("Officer");
                    } else if (vtr.get(2).toString().equalsIgnoreCase("4")) {
                        arf1.setValue("Clerk");
                    } else if (vtr.get(2).toString().equalsIgnoreCase("5")) {
                        arf1.setValue("DBA");
                    } else if (vtr.get(2).toString().equalsIgnoreCase("6")) {
                        arf1.setValue("System Administrator");
                    } else if (vtr.get(2).toString().equalsIgnoreCase("99")) {
                        arf1.setValue("Auditor");
                    }
                } else if (j == 9) {
                    arf1.setDescription("Address*");
                    arf1.setValue(vtr.get(3).toString());
                } else if (j == 10) {
                    arf1.setDescription("City");
                    arf1.setValue(vtr.get(4).toString());
                } else if (j == 11) {
                    arf1.setDescription("StateCode*");
                    arf1.setValue(vtr.get(5).toString());
                } else if (j == 12) {
                    arf1.setDescription("PinCode");
                    arf1.setValue(vtr.get(6).toString());
                } else if (j == 13) {
                    arf1.setDescription("CountryCode*");
                    arf1.setValue(vtr.get(7).toString());
                } else if (j == 14) {
                    arf1.setDescription("Telephone");
                    arf1.setValue(vtr.get(8).toString());
                } else if (j == 15) {
                    arf1.setDescription("Mobile");
                    arf1.setValue(vtr.get(9).toString());
                } else if (j == 16) {
                    arf1.setDescription("Fax");
                    arf1.setValue(vtr.get(10).toString());
                } else if (j == 17) {
                    arf1.setDescription("POEmail*");
                    arf1.setValue(vtr.get(11).toString());
                    // ****** End User Detail******
                } else if (j == 18) {
                    arf1.setDescription("BatchNumber*");
                    arf1.setValue(toDt);
                } else if (j == 19) {
                    arf1.setDescription("BatchDate*");
                    arf1.setValue(ymd_1.format(ymdFormat.parse(toDt)));
                } else if (j == 20) {
                    arf1.setDescription("MonthOfReport*");
                    if (repType.equalsIgnoreCase("CTR")) {
                        arf1.setValue(toDt.substring(4, 6));
                    } else {
                        arf1.setValue("NA");
                    }
                } else if (j == 21) {
                    arf1.setDescription("YearOfReport*");
                    if (repType.equalsIgnoreCase("CTR")) {
                        arf1.setValue(toDt.substring(0, 4));
                    } else {
                        arf1.setValue("NA");
                    }
                } else if (j == 22) {
                    arf1.setDescription("OperationalMode*");
                    arf1.setValue("P");
                } else if (j == 23) {
                    arf1.setDescription("BatchType*");
                    arf1.setValue("N");
                } else if (j == 24) {
                    arf1.setDescription("OriginalBatchId*");
                    arf1.setValue("0");
                } else if (j == 25) {
                    arf1.setDescription("ReasonOfRevision*");
                    arf1.setValue("N");
                }
                arfBatPojos.add(arf1);
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return arfBatPojos;
    }

    public List<ArfRptPojo> getArfRptData(String brCode, String repType, String userName, String frDt, String toDt, String reportName) throws ApplicationException {
        List<ArfRptPojo> arfRptPojos = new ArrayList<ArfRptPojo>();
        try {
            List result = em.createNativeQuery("select substring(alert_code,1,2),alert_code,REF_DESC,enter_by,UserName from cbs_str_detail a,"
                    + "securityinfo b,cbs_ref_rec_type c where a.auth_status = 'N' and a.flag = '" + reportName + "' and a.dt between '" + frDt + "' and '" + toDt + "' "
                    + "and a.orgnbrncode in(" + brCode + ") and a.orgnbrncode = b.orgbrncode and a.enter_by = b.UserId and c.ref_rec_no = '352' "
                    + "and a.alert_code = c.ref_code group by alert_code,enter_by").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    ArfRptPojo pojo = new ArfRptPojo();
                    String alertType = vtr.get(0).toString();
                    String alertCode = vtr.get(1).toString();
                    String alertDesc = vtr.get(2).toString();
                    //String enterBy = vtr.get(3).toString();
                    String user = vtr.get(4).toString();
                    pojo.setLineNumber(i + 1);
                    pojo.setReportSerialNum(Integer.parseInt(toDt));
                    pojo.setOriginalReportSerialNum(0);
                    pojo.setMainPersonName(user);
                    pojo.setSourceOfAlert(alertType);
                    pojo.setAlertIndicator1(alertCode);
                    pojo.setAlertIndicator2(alertDesc);
                    pojo.setAlertIndicator3(alertDesc);
                    pojo.setSuspicionDueToProceedsOfCrime("N");
                    pojo.setSuspicionDueToComplexTrans("");
                    pojo.setSuspicionDueToNoEcoRationale("");
                    pojo.setSuspicionOfFinancingOfTerrorism("Y");
                    pojo.setAttemptedTransaction("");
                    pojo.setGroundsOfSuspicion(alertDesc);
                    pojo.setDetailsOfInvestigations("");
                    pojo.setlEAInformed("");
                    pojo.setlEADetails("");
                    pojo.setPriorityRating("");
                    pojo.setReportCoverage("");
                    pojo.setAdditionalDocuments("");
                    arfRptPojos.add(pojo);
                }
            } else {
                ArfRptPojo pojo = new ArfRptPojo();
                pojo.setLineNumber(1);
                pojo.setReportSerialNum(Integer.parseInt(toDt));
                pojo.setOriginalReportSerialNum(1);
                pojo.setMainPersonName(userName);
                pojo.setSourceOfAlert("");
                pojo.setAlertIndicator1("");
                pojo.setAlertIndicator2("");
                pojo.setAlertIndicator3("");
                pojo.setSuspicionDueToProceedsOfCrime("N");
                pojo.setSuspicionDueToComplexTrans("");
                pojo.setSuspicionDueToNoEcoRationale("");
                pojo.setSuspicionOfFinancingOfTerrorism("");
                pojo.setAttemptedTransaction("");
                pojo.setGroundsOfSuspicion("");
                pojo.setDetailsOfInvestigations("");
                pojo.setlEAInformed("");
                pojo.setlEADetails("");
                pojo.setPriorityRating("");
                pojo.setReportCoverage("");
                pojo.setAdditionalDocuments("");
                arfRptPojos.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return arfRptPojos;
    }

    public List<ArfBrcPojo> getArfBrcData(String brCode, String repType) throws ApplicationException {
        List<ArfBrcPojo> arfBrcPojos = new ArrayList<ArfBrcPojo>();
        try {
            List result = new ArrayList();
            List parameter = common.getCode("BranchRefNumType");
            if (parameter.get(0).toString().equalsIgnoreCase("1")) {
                result = em.createNativeQuery("select SUBSTRING_INDEX(ifnull(concat('R-',a.brnRefNo), ifnull(concat('M-',c.micr,c.micrcode,c.branchcode),concat('I-',a.ifsc_code))),'-',1) as BranchRefNumType,"
                        + "SUBSTRING_INDEX(ifnull(a.brnRefNo, ifnull(concat('M-',c.micr,c.micrcode,c.branchcode),concat('I-',a.ifsc_code))),'-',-1) as BranchRefNum, a.BranchName, a.Address, a.City,"
                        + "a.State,a.Pincode,'IN'CountryCode,ifnull(b.BrPhone,''),''Mobile,ifnull(b.BrFax,''),ifnull(a.email,'') "
                        + "from branchmaster a,parameterinfo b,bnkadd c where a.BrnCode in(" + brCode + ") and a.BrnCode = b.BrnCode and a.AlphaCode = c.alphacode").getResultList();
            } else {
                result = em.createNativeQuery("select SUBSTRING_INDEX(ifnull(a.brnRefNo, ifnull(concat('M-',c.micr,c.micrcode,c.branchcode),concat('I-',a.ifsc_code))),'-',1) as BranchRefNumType,"
                        + "SUBSTRING_INDEX(ifnull(a.brnRefNo, ifnull(concat('M-',c.micr,c.micrcode,c.branchcode),concat('I-',a.ifsc_code))),'-',-1) as BranchRefNum, a.BranchName, a.Address, a.City,"
                        + "a.State,a.Pincode,'IN'CountryCode,ifnull(b.BrPhone,''),''Mobile,ifnull(b.BrFax,''),ifnull(a.email,'') "
                        + "from branchmaster a,parameterinfo b,bnkadd c where a.BrnCode in(" + brCode + ") and a.BrnCode = b.BrnCode and a.AlphaCode = c.alphacode").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    ArfBrcPojo pojo = new ArfBrcPojo();
                    pojo.setLineNumber(i + 1);
                    pojo.setBranchRefNumType(vtr.get(0).toString());
                    pojo.setBranchRefNum(vtr.get(1).toString());
                    pojo.setBranchName(vtr.get(2).toString());
                    pojo.setAddress(vtr.get(3).toString());
                    pojo.setCity(vtr.get(4).toString());
                    pojo.setStateCode(vtr.get(5).toString());
                    pojo.setPinCode(vtr.get(6).toString());
                    pojo.setCountryCode(vtr.get(7).toString());
                    pojo.setTelephone(vtr.get(8).toString());
                    pojo.setMobile(vtr.get(9).toString());
                    pojo.setFax(vtr.get(10).toString());
                    pojo.setBranchEmail(vtr.get(11).toString());
                    arfBrcPojos.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return arfBrcPojos;
    }

    /*
     * AIR according to following document
     * Document Name : Fwd: TEMPLATE_AIR SFT (13 January 2017 at 17:13)
     */
    public List<AirPojo> airReport(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException {
        List<AirPojo> airAcc = new ArrayList<AirPojo>();
        try {
            String mainQuery = "", acNatureQuery = "", custIdPre = "", custIdWise = "", govtFlag = "";
            int uniqueSrNo = 0;
            if (acNature.equalsIgnoreCase("CA")) {
                acNatureQuery = "select acctcode from accounttypemaster where acctnature in ('CA') ";
                mainQuery = "select bb.CustId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from ca_recon aa, customerid bb, cbs_customer_master_detail cc, accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " (select b.CustId from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " /*and substring(a.acno,1,2) in (" + brCode + ")*/ "
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty = 0 and a.trantype = 0 "
                        + " group by b.CustId having(sum(a.cramt) between " + fromAmt + " and " + toAmt + ") )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " order by bb.CustId, aa.acno, aa.dt, aa.recno ";
            } else {
                acNatureQuery = "select acctcode from accounttypemaster where acctnature not in ('CA') ";
                custIdWise = "select mm.CustId from "
                        + " (select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0  group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " ) mm "
                        + " group by mm.CustId having(sum(mm.cramt) between " + fromAmt + " and " + toAmt + ")";
                mainQuery = " select bb.CustId  as custId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from recon aa, customerid bb, cbs_customer_master_detail cc, accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " ( " + custIdWise + " )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " union all "
                        + " select bb.CustId  as custId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from rdrecon aa, customerid bb, cbs_customer_master_detail cc, accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " ( " + custIdWise + " )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " union all "
                        + " select bb.CustId  as custId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from td_recon aa, customerid bb, cbs_customer_master_detail cc, td_accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " ( " + custIdWise + " )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " union all "
                        + " select bb.CustId  as custId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from loan_recon aa, customerid bb, cbs_customer_master_detail cc, accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " ( " + custIdWise + " )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " union all "
                        + " select bb.CustId  as custId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from ddstransaction aa, customerid bb, cbs_customer_master_detail cc, accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " ( " + custIdWise + " )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " union all "
                        + " select bb.CustId  as custId, aa.acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, "
                        + " ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                        + " ifnull(dd.acctCategory,'') as custCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE =dd.acctCategory),'') as custCategoryDesc, "
                        + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                        + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                        + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                        + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                        + " ifnull(cc.PerPostalCode,'') as pinCode, "
                        + " ifnull(dd.custid1,'') as joint1, ifnull(dd.custid2,'') as joint2, "
                        + " ifnull(dd.custid3,'') as joint3, ifnull(dd.custid4,'') as joint4, "
                        + " date_format(aa.dt,'%d/%m/%Y') as dateOfTransaction, "
                        + " cast(ifnull(aa.cramt,0) as decimal(15,2)) as transactionAmount, "
                        + " aa.trantype as tranCode, (select ifnull(description,'') from codebook where groupcode = 7 and code = aa.trantype and code<>27) as tranDesc, "
                        + " aa.ty, cast(aa.recno as decimal) as recno, dd.CurBrCode , ee.acctNature, dd.OperMode "
                        + " from of_recon aa, customerid bb, cbs_customer_master_detail cc, accountmaster dd, accounttypemaster ee  "
                        + " where bb.CustId = cc.customerid and substring(aa.acno,3,2) = ee.AcctCode and aa.acno = dd.acno and aa.acno = bb.acno and  aa.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and substring(aa.acno,1,2) in (" + brCode + ")  "
                        + " and substring(aa.acno,3,2) in (" + acNatureQuery + ") and aa.ty=0 and aa.trantype=0 "
                        + " and bb.CustId in "
                        + " ( " + custIdWise + " )"
                        + " group by bb.CustId, aa.acno,aa.dt, aa.trantype, aa.ty, aa.recno "
                        + " order by custId, acno, dateOfTransaction, recno ";
            }

            List custIdWiseList = em.createNativeQuery(mainQuery).getResultList();
            if (!custIdWiseList.isEmpty()) {
                for (int i = 0; i < custIdWiseList.size(); i++) {
                    AirPojo airPojo = new AirPojo();
                    Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                    String custId = custIdWiseVector.get(0).toString();
                    String acNo = custIdWiseVector.get(1).toString();
                    if (custId.equalsIgnoreCase(custIdPre)) {
                        airPojo.setUniqueSrNo(uniqueSrNo);
                    } else {
                        uniqueSrNo = uniqueSrNo + 1;
                        airPojo.setUniqueSrNo(uniqueSrNo);
                    }
                    airPojo.setFirstName(custIdWiseVector.get(2).toString());
                    airPojo.setMiddleName(custIdWiseVector.get(3).toString());
                    airPojo.setLastName(custIdWiseVector.get(4).toString());
                    String pan = custIdWiseVector.get(5).toString();
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
                            pan = "INVALIDPAN";
                        }
                    } else if (pan.length() > 0) {
                        pan = "INVALIDPAN";
                    } else {
                        /*Point 1(b)*/
                        pan = "";
                    }

                    if (custIdWiseVector.get(7).toString().contains("GOVT")) {
                        govtFlag = "Government";
                        if (pan.equalsIgnoreCase("INVALIDPAN")) {
                            /*Point 1(c)*/
                            airPojo.setPan(pan);
                            airPojo.setGovernmentNonGovernment("Government");
                        } else if (pan.equalsIgnoreCase("")) {
                            /*Point 2*/
                            airPojo.setPan("INVALIDPAN");
                            airPojo.setGovernmentNonGovernment("Government");
                        } else {
                            airPojo.setPan(pan);
                            airPojo.setGovernmentNonGovernment("Government");
                        }
                        if (custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 60") || custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 61")) {
                            airPojo.setForm60Or61("NULL");
                        }
                    } else {
                        govtFlag = "Non-Government";
                        if (pan.equalsIgnoreCase("INVALIDPAN")) {
                            airPojo.setPan(pan);
                            airPojo.setGovernmentNonGovernment("Government");
                        } else if (pan.equalsIgnoreCase("")) {
                            airPojo.setPan(pan);
                            airPojo.setGovernmentNonGovernment("Non-Government");
                        } else {
                            airPojo.setPan(pan);
                            airPojo.setGovernmentNonGovernment("Non-Government");
                        }
                        if (custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 60") || custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 61")) {
                            airPojo.setForm60Or61("Yes");
                        } else {
                            airPojo.setForm60Or61("No");
                        }
                    }

//                    airPojo.setPan(pan);
//                    airPojo.setGovernmentNonGovernment(custIdWiseVector.get(7).toString().contains("GOVT") ? "Government" : "Non-Government");
//                    airPojo.setForm60Or61(pan.equalsIgnoreCase("INVALIDPAN") ? "" : pan);

                    airPojo.setFlatNoOrHouseNo(custIdWiseVector.get(9).toString());
                    airPojo.setFloorNo(custIdWiseVector.get(10).toString());
                    airPojo.setBuildingName(custIdWiseVector.get(11).toString());
                    airPojo.setBlockOrSector(custIdWiseVector.get(12).toString());
                    airPojo.setRoadOrStreetOrLocalityColony(custIdWiseVector.get(13).toString());
                    airPojo.setCity(custIdWiseVector.get(15).toString());
                    airPojo.setDistrictCode(custIdWiseVector.get(15).toString());
                    airPojo.setStateCode(custIdWiseVector.get(17).toString());
                    airPojo.setPinCode(custIdWiseVector.get(18).toString());

                    airPojo.setDateOfTransaction(custIdWiseVector.get(23).toString());
                    airPojo.setModeOfTransaction(custIdWiseVector.get(26).toString());
                    airPojo.setTransactionAmount(new BigDecimal(custIdWiseVector.get(24).toString()));
                    airPojo.setTransactionCode(custIdWiseVector.get(29).toString().concat(ymdFormat.format(dmyFormat.parse(custIdWiseVector.get(23).toString()))).concat(custIdWiseVector.get(28).toString()));
                    airPojo.setBranchCode(custIdWiseVector.get(29).toString());
                    int jointCount = 0;
                    if (!custIdWiseVector.get(19).toString().equalsIgnoreCase("")) {
                        jointCount = jointCount + 1;
                    }
                    if (!custIdWiseVector.get(20).toString().equalsIgnoreCase("")) {
                        jointCount = jointCount + 1;
                    }
                    if (!custIdWiseVector.get(21).toString().equalsIgnoreCase("")) {
                        jointCount = jointCount + 1;
                    }
                    if (!custIdWiseVector.get(22).toString().equalsIgnoreCase("")) {
                        jointCount = jointCount + 1;
                    }

                    airPojo.setJointTransactionPartyCount(custIdWiseVector.get(31).toString().equalsIgnoreCase("1") ? 1 : jointCount);
                    airAcc.add(airPojo);
                    custIdPre = custId;
                    List jointList = null;
                    if (jointCount > 0) {
                        if (!custIdWiseVector.get(31).toString().equalsIgnoreCase("1")) {
                            jointList = em.createNativeQuery("select  custid  from (select ifnull(custid1,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid2,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid3,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + " union all select ifnull(custid4,'') as custid  from accountmaster where acno='" + acNo + "') a where  custid <>''").getResultList();
                            if (!jointList.isEmpty()) {
                                for (int k = 0; k < jointList.size(); k++) {
                                    Vector jointVectList = (Vector) jointList.get(k);
                                    String jointCustId = jointVectList.get(0).toString();
                                    if (!jointCustId.equalsIgnoreCase("")) {
                                        String Query = "select cc.customerid as custId, '' as acno, ifnull(cc.custname,'') as firstName, ifnull(cc.middle_name,'') as middleName, ifnull(cc.last_name,'') as lastName, ifnull(cc.PAN_GIRNumber,'') as pan, "
                                                + " ifnull('','') as custCategory, ifnull('','') as custCategoryDesc, "
                                                + " ifnull(cc.PAN_GIRNumber,'') as form60or61, "
                                                + " ifnull(cc.PerAddressLine1,'') as flatOrHouseNo, ifnull(cc.peraddressline2,'') as floorNo, ifnull(cc.PerVillage,'') as buildingNo,  ifnull(cc.PerBlock,'') as blockOrSector, '' as Road, "
                                                + " ifnull(cc.PerCityCode,'') as cityCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') as cityName, "
                                                + " ifnull(cc.PerStateCode,'') as stateCode,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') as stateName, "
                                                + " ifnull(cc.PerPostalCode,'') as pinCode, "
                                                + " ifnull('','') as joint1, ifnull('','') as joint2, ifnull('','') as joint3, ifnull('','') as joint4,  "
                                                + " '' as dateOfTransaction, cast(ifnull(0,0) as decimal(15,2)) as transactionAmount, "
                                                + " 0 as tranCode, '' as tranDesc, "
                                                + " 0, cast(0 as decimal) as recno, '', '' "
                                                + " from cbs_customer_master_detail cc"
                                                + " where cc.customerid = " + jointCustId;

                                        List jointListDetails = em.createNativeQuery(Query).getResultList();
                                        if (!jointListDetails.isEmpty()) {
                                            airPojo = new AirPojo();
                                            custIdWiseVector = (Vector) jointListDetails.get(0);
                                            custId = custIdWiseVector.get(0).toString();
                                            airPojo.setUniqueSrNo(uniqueSrNo);
                                            airPojo.setFirstName(custIdWiseVector.get(2).toString());
                                            airPojo.setMiddleName(custIdWiseVector.get(3).toString());
                                            airPojo.setLastName(custIdWiseVector.get(4).toString());
                                            pan = custIdWiseVector.get(5).toString();
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
                                                    pan = "INVALIDPAN";
                                                }
                                            } else if (pan.length() > 0) {
                                                pan = "INVALIDPAN";
                                            } else {
                                                /*Point 1(b)*/
                                                pan = "";
                                            }

                                            if (govtFlag.equalsIgnoreCase("Government")) {
                                                if (pan.equalsIgnoreCase("INVALIDPAN")) {
                                                    /*Point 1(c)*/
                                                    airPojo.setPan(pan);
                                                    airPojo.setGovernmentNonGovernment("Government");
                                                } else if (pan.equalsIgnoreCase("")) {
                                                    /*Point 2*/
                                                    airPojo.setPan("INVALIDPAN");
                                                    airPojo.setGovernmentNonGovernment("Government");
                                                } else {
                                                    airPojo.setPan(pan);
                                                    airPojo.setGovernmentNonGovernment("Government");
                                                }
                                                if (custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 60") || custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 61")) {
                                                    airPojo.setForm60Or61("NULL");
                                                }
                                            } else {
                                                if (pan.equalsIgnoreCase("INVALIDPAN")) {
                                                    airPojo.setPan(pan);
                                                    airPojo.setGovernmentNonGovernment("Government");
                                                } else if (pan.equalsIgnoreCase("")) {
                                                    airPojo.setPan(pan);
                                                    airPojo.setGovernmentNonGovernment("Non-Government");
                                                } else {
                                                    airPojo.setPan(pan);
                                                    airPojo.setGovernmentNonGovernment("Non-Government");
                                                }
                                                if (custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 60") || custIdWiseVector.get(5).toString().equalsIgnoreCase("Form 61")) {
                                                    airPojo.setForm60Or61("Yes");
                                                } else {
                                                    airPojo.setForm60Or61("No");
                                                }
                                            }

                                            //                    airPojo.setPan(pan);
                                            //                    airPojo.setGovernmentNonGovernment(custIdWiseVector.get(7).toString().contains("GOVT") ? "Government" : "Non-Government");
                                            //                    airPojo.setForm60Or61(pan.equalsIgnoreCase("INVALIDPAN") ? "" : pan);

                                            airPojo.setFlatNoOrHouseNo(custIdWiseVector.get(9).toString());
                                            airPojo.setFloorNo(custIdWiseVector.get(10).toString());
                                            airPojo.setBuildingName(custIdWiseVector.get(11).toString());
                                            airPojo.setBlockOrSector(custIdWiseVector.get(12).toString());
                                            airPojo.setRoadOrStreetOrLocalityColony(custIdWiseVector.get(13).toString());
                                            airPojo.setCity(custIdWiseVector.get(15).toString());
                                            airPojo.setDistrictCode(custIdWiseVector.get(15).toString());
                                            airPojo.setStateCode(custIdWiseVector.get(17).toString());
                                            airPojo.setPinCode(custIdWiseVector.get(18).toString());

                                            airPojo.setDateOfTransaction(custIdWiseVector.get(23).toString());
                                            airPojo.setModeOfTransaction(custIdWiseVector.get(26).toString());
                                            airPojo.setTransactionAmount(new BigDecimal(custIdWiseVector.get(24).toString()));
                                            airPojo.setTransactionCode("");
                                            airPojo.setBranchCode(custIdWiseVector.get(29).toString());
                                            airPojo.setJointTransactionPartyCount(0);
                                            airAcc.add(airPojo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                AirPojo airPojo = new AirPojo();
                airPojo.setUniqueSrNo(1);
                airPojo.setFirstName("");
                airPojo.setMiddleName("");
                airPojo.setLastName("");
                airPojo.setPan("");
                airPojo.setGovernmentNonGovernment("");
                airPojo.setForm60Or61("");
                airPojo.setFlatNoOrHouseNo("");
                airPojo.setFloorNo("");
                airPojo.setBuildingName("");
                airPojo.setBlockOrSector("");
                airPojo.setRoadOrStreetOrLocalityColony("");
                airPojo.setCity("");
                airPojo.setDistrictCode("");
                airPojo.setStateCode("");
                airPojo.setPinCode("");
                airPojo.setJointTransactionPartyCount(0);
                airPojo.setDateOfTransaction("");
                airPojo.setModeOfTransaction("");
                airPojo.setTransactionAmount(new BigDecimal("0"));
                airPojo.setTransactionCode("");
                airPojo.setBranchCode("");
                airAcc.add(airPojo);
            }

            return airAcc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
    /*
     * AIR according to following document
     * Document Name : Fwd: TEMPLATE_AIR SFT (13 January 2017 at 17:13)
     */

    public List<Form61APojo> form61AReport(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException {
        List<Form61APojo> airAcc = new ArrayList<Form61APojo>();
        try {
            String mainQuery = "", acNatureQuery = "", custIdPre = "", custIdWise = "", govtFlag = "";
            int reportSerialNumber = 0;
            if (acNature.equalsIgnoreCase("CA")) {
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and a.ty = 0 and a.trantype = 0 "
                        + " group by b.CustId having(sum(a.cramt) between " + fromAmt + " and " + toAmt + ") ) "
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " order by e.CustId, d.acno ";
            } else {
                acNatureQuery = "select acctcode from accounttypemaster where acctnature not in ('CA') ";
                custIdWise = "select mm.CustId from "
                        + " (select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0  group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " union all "
                        + " select b.CustId as CustId, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "'  "
                        + " /*and substring(a.acno,1,2) in ('00','01','02','03','04','05','06','07','08','09','10','11','12''13','99') */"
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty=0 and a.trantype=0 group by b.custid, tab "
                        + " ) mm "
                        + " group by mm.CustId having(sum(mm.cramt) between " + fromAmt + " and " + toAmt + ")";
                mainQuery = " select cast(ifnull(e.custid,'') as decimal) as custId, "
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')   as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and substring(d.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('SB') ) "
                        + " and e.CustId in "
                        + " ( " + custIdWise + " )"
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " union all "
                        + " select cast(ifnull(e.custid,'') as decimal) as custId, "
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from rdrecon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from rdrecon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from rdrecon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from rdrecon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')   as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and substring(d.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('RD') ) "
                        + " and e.CustId in "
                        + " ( " + custIdWise + " )"
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " union all "
                        + " select cast(ifnull(e.custid,'') as decimal) as custId, "
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from loan_recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')   as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and substring(d.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DL','TL') ) "
                        + " and e.CustId in "
                        + " ( " + custIdWise + " )"
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " union all "
                        + " select cast(ifnull(e.custid,'') as decimal) as custId, "
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ddstransaction where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from ddstransaction where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ddstransaction where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ddstransaction where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')   as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and substring(d.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS') ) "
                        + " and e.CustId in "
                        + " ( " + custIdWise + " )"
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " union all "
                        + " select cast(ifnull(e.custid,'') as decimal) as custId, "
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from of_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from of_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from of_recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from of_recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and substring(d.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('OF') ) "
                        + " and e.CustId in "
                        + " ( " + custIdWise + " )"
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " union all "
                        + " select cast(ifnull(e.custid,'') as decimal) as custId, "
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from td_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from td_recon where acno = d.ACNo and dt between '20160401' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from td_recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from td_recon where acno = d.ACNo and dt between '20161109' and '20161230' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
                        + " ifnull(d.OperMode,'') as accRelationShip, "
                        + " concat(ifnull(f.custname,''), if(ifnull(f.middle_name,'')= '', ifnull(f.middle_name,''), concat(' ', ifnull(f.middle_name,''))), if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName, "
                        + " ifnull(d.acctCategory,'') as PersonType, "
                        + " ifnull(f.gender,'X') as Gender, "
                        + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName, "
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
                        + " ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')   as incorPorationDt,  "
                        + " 'IN' as dobPlace, "
                        + " 'IN' as incorPorationPlace,  "
                        + "  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, "
                        + " '1' as addressType, "
                        + " concat(ifnull(f.PerAddressLine1,''), if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))),  "
                        + " if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),  "
                        + " if(ifnull(f.PerBlock,'')='', '', concat(', ',ifnull(f.PerBlock,''))),  "
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " td_accountmaster d,  "
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
                        + " and substring(d.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('TD','MS') ) "
                        + " and e.CustId in "
                        + " ( " + custIdWise + " )"
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " order by custId, acno ";
            }

//            System.out.println("Query>>>>>:" + mainQuery);
            List custIdWiseList = em.createNativeQuery(mainQuery).getResultList();
            if (!custIdWiseList.isEmpty()) {
                for (int i = 0; i < custIdWiseList.size(); i++) {
                    Form61APojo airPojo = new Form61APojo();
                    Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                    String custId = custIdWiseVector.get(0).toString();

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
                    BigDecimal aggGrossAmountCrCash = new BigDecimal(custIdWiseVector.get(16).toString());
                    BigDecimal aggGrossAmountDrCash = new BigDecimal(custIdWiseVector.get(17).toString());
                    BigDecimal amountCrBeforeDemonetization = new BigDecimal(custIdWiseVector.get(18).toString());
                    BigDecimal amountCrAfterDemonetization = new BigDecimal(custIdWiseVector.get(19).toString());
                    String accountRemarks = "";
                    /*  In Our System
                     1	SELF                    F
                     2	EITHER OR SURVIVOR      F
                     3	FORMER OR SURVIVOR      F
                     4	ANY ONE OR SURVIVOR     F
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
                     18      KARTA OF HUF            Z
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
                    if (acNature.equalsIgnoreCase("SB")) {
                        accountRelationship = "F";
                    } else if (acNature.equalsIgnoreCase("CA")) {
                        accountRelationship = "A";
                    } else if (accountRelationship.equalsIgnoreCase("1") || accountRelationship.equalsIgnoreCase("2")
                            || accountRelationship.equalsIgnoreCase("3") || accountRelationship.equalsIgnoreCase("4")) {
                        accountRelationship = "F";
                    } else if (accountRelationship.equalsIgnoreCase("5")) {
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
                     HUF                 HUF	HUF                      HF	 HUF
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
                     PF                  PROPRIETARY / PARTNERSHIP FIRM	PF	 Partnership Firm

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
                    String primaryStdCode = custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-")[0] : "";
                    String primaryTelephoneNumber = custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-")[1] : "";
                    String primaryMobileNumber = custIdWiseVector.get(44).toString().length() != 10 ? "" : custIdWiseVector.get(44).toString();
                    String secondaryStdCode = custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-")[0] : "";
                    String secondaryTelephoneNumber = custIdWiseVector.get(45).toString().contains("-") ? custIdWiseVector.get(45).toString().split("-")[0] : "";
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
                    if (aggGrossAmountCrCash.compareTo(new BigDecimal("0")) != 0) {
//                        airAcc.add(airPojo);
                        int jointCount = 0;
                        if (!custIdWiseVector.get(47).toString().equalsIgnoreCase("")) {
                            jointCount = jointCount + 1;
                        }
//                        if (!custIdWiseVector.get(48).toString().equalsIgnoreCase("")) {
//                            jointCount = jointCount + 1;
//                        }
//                        if (!custIdWiseVector.get(49).toString().equalsIgnoreCase("")) {
//                            jointCount = jointCount + 1;
//                        }
//                        if (!custIdWiseVector.get(50).toString().equalsIgnoreCase("")) {
//                            jointCount = jointCount + 1;
//                        }
                        List jointList;
                        if (jointCount > 0) {
                            jointList = em.createNativeQuery("select  custid  from (select ifnull(custid1,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    //                                    + " union all select ifnull(custid2,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    //                                    + " union all select ifnull(custid3,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    //                                    + " union all select ifnull(custid4,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + ") a where  custid <>''").getResultList();
                            if (!jointList.isEmpty()) {
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
                                                + " '' as PersonType,  ifnull(f.gender,'X') as Gender,  "
                                                + " concat(ifnull(f.fathername,''),if(ifnull(f.FatherMiddleName,'')= '',ifnull(f.FatherMiddleName,''),concat(' ', ifnull(f.FatherMiddleName,''))),"
                                                + " if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),concat(' ', ifnull(f.FatherLastName,'')))) as FatherName,  "
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
                                                + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),   "
                                                + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),   "
                                                + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address,  "
                                                + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
                                                + " if(ifnull(f.PerPostalCode,'')='','XXXXXX',ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode,  "
                                                + " if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX', ifnull(f.PerStateCode,'XX'))) as StateCode,  "
                                                + " 'IN' as countoryCode,  ifnull(f.PerPhoneNumber,'') as phnoneNo,  ifnull(f.mobilenumber,'') as MobileNo,  ifnull(f.PerFaxNumber,'') as faxNo, "
                                                + " ifnull(f.EmailID,'') as email  "
                                                + " from    cbs_customer_master_detail f,   cbs_cust_misinfo g   "
                                                + " where  f.customerid = g.CustomerId  "
                                                + " and f.customerid in  (" + jointCustId + ")  group by f.customerid";

                                        List jointListDetails = em.createNativeQuery(Query).getResultList();
                                        if (!jointListDetails.isEmpty()) {
//                                            airPojo = new Form61APojo();
                                            custIdWiseVector = (Vector) jointListDetails.get(0);
//                                            airPojo.setReportSerialNumber(reportSerialNumber);
//                                            airPojo.setOriginalReportSerialNumber(originalReportSerialNumber);
//                                            airPojo.setAccountType(accountType);
//                                            airPojo.setAccountNumber(acNo);
//                                            airPojo.setAccountHolderName(accountHolderName);
//                                            airPojo.setAccountStatus(accountStatus);
//                                            airPojo.setBranchReferenceNumber(branchReferenceNumber);
//                                            airPojo.setBranchName(branchName);
//                                            airPojo.setBranchAddress(branchAddress);
//                                            airPojo.setBranchCityTown(branchCityTown);
//                                            airPojo.setBranchPostalCode(branchPostalCode);
//                                            airPojo.setBranchState(branchState);
//                                            airPojo.setBranchCountry(branchCountry);
//                                            airPojo.setBranchSTDCode(branchSTDCode);
//                                            airPojo.setBranchPhoneNumber(branchPhoneNumber);
//                                            airPojo.setBranchMobileNumber(branchMobileNumber);
//                                            airPojo.setBranchbFaxSTDCode(branchbFaxSTDCode);
//                                            airPojo.setBranchFaxPhoneNo(branchFaxPhoneNo);
//                                            airPojo.setBranchEmail(branchEmail);
//                                            airPojo.setBranchRemarks(branchRemarks);
//                                            airPojo.setAggGrossAmountCrCash(aggGrossAmountCrCash);
//                                            airPojo.setAggGrossAmountDrCash(aggGrossAmountDrCash);
//                                            airPojo.setAmountCrBeforeDemonetization(amountCrBeforeDemonetization);
//                                            airPojo.setAmountCrAfterDemonetization(amountCrAfterDemonetization);
//                                            airPojo.setAccountRemarks(accountRemarks);
//                                            airPojo.setAccountRelationship(accountRelationship);
                                            airPojo.setPersonName(custIdWiseVector.get(21).toString());
//                                            airPojo.setPersonType(personType);
//                                            airPojo.setCustomerIdentity(jointCustId);                                            
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
                                            airPojo.setPrimaryStdCode(custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-")[0] : "");
                                            airPojo.setPrimaryTelephoneNumber(custIdWiseVector.get(43).toString().contains("-") ? custIdWiseVector.get(43).toString().split("-")[1] : "");
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

                    }

                    custIdPre = custId;

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

    /*
     * ARF according to following document
     * Document Name : Reporting Format Guide
     * Version       : 2.1
     * Year          : 2011
     */
    public CtrArfPojo arfReport(List acNatureList, String acType, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException {
//        List<CtrPojo> ctrPojos = new ArrayList<CtrPojo>();
        CtrArfPojo arf = new CtrArfPojo();
        List<ArfAccPojo> arfAccPojos = new ArrayList<ArfAccPojo>();
        List<ArfTrnPojo> arfTrnPojos = new ArrayList<ArfTrnPojo>();
        List<ArfInpPojo> arfInpPojos = new ArrayList<ArfInpPojo>();
        List<ArfLpePojo> arfLpePojos = new ArrayList<ArfLpePojo>();

        try {
            Integer arfAccLine = 0, inpLineNo = 0, trnLineNo = 0, lpeLineNo = 0, reportSrNo = 0;
            Integer year;
            String ctrParameter, acTypeNot = "''";
            Double ctrLimit = 0d;
            /**
             * *Limit is implemented for CCBL, Because they reported more than
             * 50000 *
             */
            List ctrAmtList = em.createNativeQuery("select ifnull(code,0) from cbs_parameterinfo where name = 'CTR_AMT'").getResultList();
            if (!ctrAmtList.isEmpty()) {
                Vector ctrVect = (Vector) ctrAmtList.get(0);
                ctrLimit = Double.parseDouble(ctrVect.get(0).toString());
            }
            List acTypeNotList = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name = 'CTR_ACCTCODE_NOT'").getResultList();
            if (!acTypeNotList.isEmpty()) {
                Vector acTypeNotVect = (Vector) acTypeNotList.get(0);
                acTypeNot = acTypeNotVect.get(0).toString();
            }
            if (toDt.substring(4, 6).equalsIgnoreCase("01") || toDt.substring(4, 6).equalsIgnoreCase("02") || toDt.substring(4, 6).equalsIgnoreCase("03")) {
                year = (Integer.parseInt(toDt.substring(0, 4)) - 1);
            } else {
                year = Integer.parseInt(toDt.substring(0, 4));
            }

            for (int i = 0; i < acNatureList.size(); i++) {
                String acType1 = "";
                Vector acnatureVect = (Vector) acNatureList.get(i);
                String acNature = acnatureVect.get(0).toString();
                List acTypeList = common.getAccType(acnatureVect.get(0).toString());
                if (acTypeList.size() > 0) {
                    for (int z = 0; z < acTypeList.size(); z++) {
                        Vector actypevect = (Vector) acTypeList.get(z);
                        if (z == 0) {
                            acType1 = "'" + actypevect.get(0).toString() + "'";
                        } else {
                            acType1 = acType1.concat(",'" + actypevect.get(0).toString() + "'");
                        }
                    }
                }
                acType = acType1;
                String preAcNo = "0";

                String acctCategory = "";
                if (repType.equalsIgnoreCase("CTR")) {
                    acctCategory = "and a.acctCategory <> 'NGO'";
                } else {
                    acctCategory = "and a.acctCategory = 'NGO'";
                }

                List acTransactionDetailsList = em.createNativeQuery("select b.acno,dt,b.creditAmt,b.debitAmt,b.trantype,b.ty,b.recno  from " + ftsPosting.getAccountTable(acNature) + " a ,"
                        + "(select acno,dt,cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(15,2)) as creditAmt, "
                        + " cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(15,2)) as debitAmt,trantype, ty, recno from " + CbsUtil.getReconTableName(acNature) + " where "
                        + " dt between '" + fromDt + "' and '" + toDt + "' and substring(acno,1,2) in (" + brCode + ")  and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype=0 and "
                        + " (acno in (select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                        + " substring(acno,1,2) in (" + brCode + ")   and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype=0 group by acno having(sum(cramt) between " + fromAmt + " and " + toAmt + ")) "
                        + " OR acno in ("
                        //                        + " select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                        //                        + " substring(acno,1,2) in (" + brCode + ") and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype=0 group by acno having(sum(dramt) between " + fromAmt + " and " + toAmt + ")"
                        + " select a.acno from ( "
                        + " select acno, dramt, 'CASH' as mode, dt as TRAN_DATE from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                        + " substring(acno,1,2) in (" + brCode + ") and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype=0 "
                        + " union all "
                        + " select FROM_ACCOUNT_NUMBER as acno, AMOUNT as dramt, 'ATM' as mode, TRAN_DATE from atm_normal_transaction_parameter where IN_PROCESS_STATUS='SUCCESS' and "
                        + " SYSTEM_TRACE_NUMBER not in (select ORIGINAL_SYSTEM_TRACE_NUMBER from atm_reversal_transaction_parameter where IN_PROCESS_STATUS='SUCCESS')  "
                        + " and PROCESSING_CODE in ('00','01') and substring(FROM_ACCOUNT_NUMBER,1,2) in (" + brCode + " )"
                        + " and substring(FROM_ACCOUNT_NUMBER,3,2) in (" + acType + " )  and substring(FROM_ACCOUNT_NUMBER,3,2) not in (" + acTypeNot + ") "
                        + " and TRAN_DATE between '" + fromDt + "' and '" + toDt + "') a "
                        + " group by a.acno having(sum(a.dramt) between " + fromAmt + " and " + toAmt + ")"
                        + " )) "
                        + " group by acno,dt, trantype, ty, recno order by acno,dt, ty, trantype) b where a.acno = b.acno " + acctCategory + "").getResultList();


                for (int j = 0; j < acTransactionDetailsList.size(); j++) {
                    ArfTrnPojo arfTrn = new ArfTrnPojo();

                    Vector acTranVect = (Vector) acTransactionDetailsList.get(j);
                    String acNo = acTranVect.get(0).toString();
                    String dt = acTranVect.get(1).toString();
                    BigDecimal crAmt = new BigDecimal(acTranVect.get(2).toString());
                    BigDecimal drAmt = new BigDecimal(acTranVect.get(3).toString());
                    String tranType = acTranVect.get(4).toString();
                    String ty = acTranVect.get(5).toString();
                    float recNo = Float.parseFloat(acTranVect.get(6).toString());
                    /**
                     * *************************************
                     * START ARFTRN * *************************************
                     */
//                    arfTrn.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf((preTrnLineNo!=j?preTrnLineNo+1:j+1)), 6)));
//                    if (!preAcNo.equalsIgnoreCase(acNo)) {
//                        reportSrNo = reportSrNo+1;
//                    } 
                    arfTrn.setReportSerialNum(toDt);
                    List<ArfBrcPojo> arfBrcList = getArfBrcData(acNo.substring(0, 2), "");
                    arfTrn.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
//                    arfTrn.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                    arfTrn.setAccountNumber(acNo);
                    arfTrn.setDateOfTransaction(ymd_1.format(ymd_1.parse(dt)));
                    arfTrn.setTransactionId(ymd.format(ymd_1.parse(dt)).concat(String.valueOf((int) recNo)));

                    /*  TransactionMode
                     A – Cheque (Inter bank transfer)
                     B – Internal Transfer (Transfer within the Institution)
                     C – Cash 
                     D – Demand Draft/Pay Order (Demand Draft, Pay Order)
                     E – Electronic Fund Transfer (Swift, cross border payment platforms, TT, RTGS, NEFT)
                     F – Exchange Based Transaction (Any exchange based transaction)
                     G – Securities transaction (Any Securities based transaction)
                     S – Switching Transaction (Switching transaction (switching of products) like switching of mutual funds)
                     Z – Others (Not listed above)
                     X – Not Categorised.
                     */
                    arfTrn.setTransactionMode("C");

                    /*  DebitCredit
                     D – Debit (Debit transaction in the account(Banks - Withdrawal by customer))
                     C – Credit (Credit transaction in the accoun(Banks - Deposit by customer))
                     X – Not Categorised. 
                     */
                    if (ty.equalsIgnoreCase("0")) {
                        arfTrn.setDebitCredit("C");
                        arfTrn.setAmount(new BigDecimal(new DecimalFormat("#").format(crAmt.doubleValue())));    //The amount should be rounded off to nearest rupee without decimal. If this amount is not in Indian Rupees,then convert to Indian Rupees.
                    } else {
                        arfTrn.setDebitCredit("D");
                        arfTrn.setAmount(new BigDecimal(new DecimalFormat("#").format(drAmt.doubleValue())));
                    }
                    arfTrn.setCurrency("INR");      //INR for Indian Rupees (Mention currency code as per ISO 4127. Refer Annexure G for Currency codes.)

                    /*  Product Type
                     BD – Bonds 
                     ST – Securities 
                     CD – Certificate of Deposit 
                     CP – Commercial Paper 
                     EQ – Equity Shares 
                     FU – Futures 
                     OP – Options 
                     DF – Debt Funds (Mutual Funds)
                     EF – Equity Fund (Mutual Funds)
                     HF – Hybrid Funds (Mutual Funds)
                     LF – Liquid Funds (Mutual Funds)
                     MF – MIP Funds (Mutual Funds)
                     XF – Exchange Traded Funds (Mutual Funds)
                     CO – Commodities 
                     IP – Insurance Products 
                     ZZ – Others (Not listed above)
                     XX – Not Categorised.
                     */
                    arfTrn.setProductType("XX");
                    arfTrn.setIdentifier("");

                    /*  Transaction Type
                     BP – Buy/Purchase       (Credit)
                     SR – Sale/Redemption    (Debit)
                     IA – Annuity payment (Insurance Companies)
                     IP – Pension  (Insurance Companies)
                     IC – Commutation  (Insurance Companies)
                     ID – Death  claim  (Insurance Companies)
                     IM – Maturity  (Insurance Companies)
                     IB – Survival  benefits (Insurance Companies (including money back))
                     IF – Free  look cancellation (Insurance Companies)
                     IW – Withdrawal  (Insurance Companies (including partial withdrawal))
                     IS – Surrender (Insurance Companies)
                     IG – Assignment (Insurance Companies)
                     IE – Decline (Insurance Companies)
                     IX – Excess Refund (Insurance Companies)
                     IR – Premium Payment (Insurance Companies)
                     IL – Loan Repayment (Insurance Companies)
                     DD – Dematerialisation/Conversion of Mutual fund units in demat form (Depositories)
                     DR – Rematerialisation/Repurchase  (Depositories)
                     DO – Off Market trade  (Depositories)
                     DM – Market transfers  (Depositories)
                     DI – Inter Settlement transfers  (Depositories)
                     DP – Pledge and Hypothecation  (Depositories)
                     DC – Corporate action  (Depositories)
                     ZZ – Others 
                     XX – Not Categorised.
                     */
                    arfTrn.setTransactionType("ZZ");
                    arfTrn.setUnits(new BigDecimal("0"));
                    arfTrn.setRate(new BigDecimal("0"));
                    arfTrn.setDispositionOfFunds("X"); //Reserved for later use. Use value X.
                    arfTrn.setRelatedAccountNum("");
                    arfTrn.setRelatedInstitutionName("");
                    arfTrn.setRelatedInstitutionRefNum("");
                    arfTrn.setRemarks("");
                    if (ty.equalsIgnoreCase("0")) {
//                        if (crAmt.doubleValue() >= ctrLimit) {
                        trnLineNo = trnLineNo + 1;
                        arfTrn.setLineNumber(trnLineNo);
                        arfTrnPojos.add(arfTrn);
//                        }
                    } else {
                        if (drAmt.doubleValue() >= ctrLimit) {
                            trnLineNo = trnLineNo + 1;
                            arfTrn.setLineNumber(trnLineNo);
                            arfTrnPojos.add(arfTrn);
                        }
                    }

                    /**
                     * ********** END OF ARFTRN *********
                     */
                    /**
                     * *************************************
                     * START ARFACC/ARFLPE *
                     * *************************************
                     */
                    if (!preAcNo.equalsIgnoreCase(acNo)) {
                        arfAccLine = arfAccLine + 1;
                        ArfAccPojo arfAcc = new ArfAccPojo();
                        ArfLpePojo arfLpe = new ArfLpePojo();
                        /**
                         * ** Customer Information ***
                         */
                        List acNoDetails = em.createNativeQuery("select concat(ifnull(c.custname,''), if(ifnull(c.middle_name,'')= '', ifnull(c.middle_name,''), concat(' ', ifnull(c.middle_name,''))), if(ifnull(c.last_name,'')= '', ifnull(c.last_name,''), concat(' ', ifnull(c.last_name,'')))) as custName,  a.openingdt, "
                                + " a.opermode, ifnull(c.OperationalRiskRating,''), b.custid, ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'C' and CustEntityType = '01'), "
                                + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'C' and CustEntityType = '01'), "
                                + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')"
                                + " ), ifnull(c.PAN_GIRNumber,'')) as Pan, "
                                + " IFNULL(c.PerAddressLine1,''), IFNULL(c.PerAddressLine2,''), IFNULL(c.PerVillage,''), IFNULL(c.PerBlock,''), IFNULL(c.PerCityCode,'0'), IFNULL(c.PerStateCode,''),"
                                + " IFNULL(c.PerPostalCode,''), IFNULL(c.PerCountryCode,''), IFNULL(c.PerPhoneNumber,''), IFNULL(c.PerTelexNumber,''), IFNULL(c.PerFaxNumber,''), "
                                + " IFNULL(c.MailAddressLine1,''), IFNULL(c.MailAddressLine2,''), IFNULL(c.MailVillage,''), IFNULL(c.MailBlock, ''), IFNULL(c.MailCityCode, '0'),"
                                + " IFNULL(c.MailStateCode, ''), IFNULL(c.MailPostalCode, ''), IFNULL(c.MailCountryCode, ''), IFNULL(c.MailPhoneNumber, ''), IFNULL(c.MailTelexNumber, ''),"
                                + " IFNULL(c.MailFaxNumber, ''), IFNULL(c.mobilenumber,''), IFNULL(c.EmailID,''), a.accstatus,  ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'), "
                                + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'), "
                                + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01') "
                                + "), ifnull(c.AADHAAR_NO,'')) as AadhaarNo   from " + CbsUtil.getAccMasterTableName(acNature) + "  a, customerid b, cbs_customer_master_detail c "
                                + " where a.acno = b.acno and b.custid = c.customerid and a.acno =  '" + acNo + "'").getResultList();
                        if (!acNoDetails.isEmpty()) {
                            Vector acNoVect = (Vector) acNoDetails.get(0);
                            String custName = acNoVect.get(0).toString().toUpperCase();
                            String openingDt = acNoVect.get(1).toString();
                            String operMode = acNoVect.get(2).toString();
                            String riskCategory = acNoVect.get(3).toString();
                            String custId = acNoVect.get(4).toString();
                            String pan = acNoVect.get(5).toString().toUpperCase();
                            String perAddressLine1 = acNoVect.get(6).toString();
                            String perAddressLine2 = acNoVect.get(7).toString();
                            String perVillage = acNoVect.get(8).toString();
                            String perBlock = acNoVect.get(9).toString();
                            String perCityCode = acNoVect.get(10).toString();
                            String perStateCode = acNoVect.get(11).toString();
                            String perPostalCode = acNoVect.get(12).toString();
                            String perCountryCode = acNoVect.get(13).toString();
                            String perPhoneNumber = acNoVect.get(14).toString();
                            String perTelexNumber = acNoVect.get(15).toString();
                            String perFaxNumber = acNoVect.get(16).toString();
                            String mailAddressLine1 = acNoVect.get(17).toString();
                            String mailAddressLine2 = acNoVect.get(18).toString();
                            String mailVillage = acNoVect.get(19).toString();
                            String mailBlock = acNoVect.get(20).toString();
                            String mailCityCode = acNoVect.get(21).toString();
                            String mailStateCode = acNoVect.get(22).toString();
                            String mailPostalCode = acNoVect.get(23).toString();
                            String mailCountryCode = acNoVect.get(24).toString();
                            String mailPhoneNumber = acNoVect.get(25).toString();
                            String mailTelexNumber = acNoVect.get(26).toString();
                            String mailFaxNumber = acNoVect.get(27).toString();
                            String mobileNo = acNoVect.get(28).toString();
                            String eMailId = acNoVect.get(29).toString();
                            String accStatus = acNoVect.get(30).toString();
                            String uin = acNoVect.get(31).toString();
                            String acNature1 = common.getAcctNature(acNo.substring(2, 4));

                            YearEndDatePojo yDate = new YearEndDatePojo();
                            yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingToDate(acNo.substring(0, 2), toDt);
                            String minFDate = yDate.getMinDate();
                            String maxFDate = yDate.getMaxDate();
                            String fyear = yDate.getfYear();
                            /**
                             * ** Cash Transaction Information ***
                             */
                            List cumCashTranList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt between '" + minFDate + "' and '" + toDt + "' and trantype=0").getResultList();
                            Vector cumCashTranVect = (Vector) cumCashTranList.get(0);
                            BigDecimal cashCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumCashTranVect.get(0).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(0).toString());
                            BigDecimal cashDrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumCashTranVect.get(1).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(1).toString());
                            /**
                             * ** All Transaction Information ***
                             */
                            List cumAllTranList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt between '" + minFDate + "' and '" + toDt + "'").getResultList();
                            Vector cumAllTranVect = (Vector) cumAllTranList.get(0);
                            BigDecimal allCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumAllTranVect.get(0).toString()).doubleValue()));
                            BigDecimal allDrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumAllTranVect.get(1).toString()).doubleValue()));

                            /**
                             * Start of ARFACC *
                             */
                            arfAcc.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf(arfAccLine), 6)));
                            arfAcc.setReportSerialNum(toDt);
                            arfAcc.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
//                            arfAcc.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                            arfAcc.setAccountNumber(acNo);

                            /*  Account Type
                             BS - Savings Account
                             BC - Current Account
                             BR - Cash Credit/Overdraft Account
                             BD - Credit Card Account
                             BP - Prepaid Card Account
                             BL - Loan Account
                             BT - Term Deposit Account
                             BG – Letter of Credit/Bank Guarantee
                             ZZ - Others
                             XX - Not Categorised
                             */
                            if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                                arfAcc.setAccountType("BC");
                            } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                arfAcc.setAccountType("BR");
                            } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                arfAcc.setAccountType("BS");
                            } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                arfAcc.setAccountType("BL");
                            } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                                arfAcc.setAccountType("BT");
                            } else {
                                arfAcc.setAccountType("ZZ");
                            }

                            arfAcc.setHolderName(custName);

                            /*  In Our System
                             1	SELF
                             2	EITHER OR SURVIVOR
                             3	FORMER OR SURVIVOR
                             4	ANY ONE OR SURVIVOR
                             5	ANY TWO JOINTLY
                             6	ANY THREE JOINTLY
                             7	UNDER POWER OF ATTOR
                             8	PROPRIETOR
                             9	AUTHORIZED SIGNATORY
                             10	M.D.
                             11	UNDER GUARDIANSHIP
                             12	BOTH OF TWO JOINTLY
                             13	MINOR
                             14	ANY FOUR JOINTLY
                             15	ANY FIVE JOINTLY
                             16	ALL JOINTLY
                             17	JOINTLY
                             * *****************************************
                             *  In CTR report required

                             A - Resident Individual
                             B - Legal Person/Entity (excluding C,D,E and F)
                             C - Central/State Government
                             D - Central/State Government owned undertaking
                             E – Reporting Entity (The account belongs to other bank, financial institution or intermediary)
                             F- Non Profit Organisation
                             G- Non-residential individual
                             H - Overseas corporate body/FII
                             Z – Others. (Not listed above)
                             X - Not categorised
                             */
                            if (operMode.equalsIgnoreCase("1")) {
                                arfAcc.setAccountHolderType("A");
                            } else {
                                arfAcc.setAccountHolderType("B");
                            }

                            /*  Account Status
                             A – Active (Account is in regular use/policy inforce)
                             I – Inactive (Account is not in regular use/ policy lapsed)
                             D – Dormant (As defined by regulator (eg. There is no transaction in the account for two years)/ paid up policy lapsed after paying premiums for 3 or more years)
                             S – Suspended (Account/policy risk is temporarily suspended)
                             F – Frozen (Account/policy is frozen (including case of debit freeze))
                             C – Closed (Account is closed/policy foreclosed, surrendered, death or maturity claim paid)
                             Z – Others (Not listed above)
                             X - Not categorised
                             */
                            if (accStatus.equalsIgnoreCase("1")) {
                                arfAcc.setAccountStatus("A");
                            } else if (accStatus.equalsIgnoreCase("2")) {
                                List dormentList = em.createNativeQuery("select ifnull(max(dt),'') from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt<='" + toDt + "'").getResultList();
                                Vector dorVect = (Vector) dormentList.get(0);
                                String dorDt = dorVect.get(0).toString();
                                int yearDiff = CbsUtil.yearDiff(ymd_1.parse(dorDt), ymd_1.parse(ymd_1.format(ymd.parse(toDt))));
                                if (yearDiff >= 2) {
                                    arfAcc.setAccountStatus("D");
                                } else {
                                    List operativeList = em.createNativeQuery("Select c.Code from accountstatus a,codebook c where acno='" + acNo + "' "
                                            + " and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + toDt + "' and acno='" + acNo + "' "
                                            + " and spno=(Select max(Spno) from accountstatus where acno='" + acNo + "' and date_format(EffDt,'%Y%m%d')<='" + toDt + "')) "
                                            + " AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3").getResultList();
                                    if (!operativeList.isEmpty()) {
                                        Vector operVect = (Vector) operativeList.get(0);
                                        String operStatus = operVect.get(0).toString();
                                        if (operStatus.equalsIgnoreCase("1")) {
                                            arfAcc.setAccountStatus("A");
                                        } else {
                                            arfAcc.setAccountStatus("I");
                                        }
                                    } else {
                                        arfAcc.setAccountStatus("A");
                                    }
                                }
                            } else if (accStatus.equalsIgnoreCase("7")) {
                                arfAcc.setAccountStatus("S");
                            } else if (accStatus.equalsIgnoreCase("4")) {
                                arfAcc.setAccountStatus("F");
                            } else if (accStatus.equalsIgnoreCase("9")) {
                                arfAcc.setAccountStatus("C");
                            } else {
                                arfAcc.setAccountStatus("Z");
                            }

                            arfAcc.setDateOfOpening(ymd_1.format(ymd.parse(openingDt)));

                            /*
                             * IN OUR SYSTEM:
                             *  024	C1	LOW RISK CATEGORY       03
                             *  024	C2	MEDIUM RISK CATEGORY    02
                             *  024	C3	HIGH RISK CATEGORY      01
                             *  024	C4	VERY HIGH RISK CATEGORY
                             * 
                             * IN RBI SYSTEM:
                             *  A1 - High Risk Account (Very High or High Risk)
                             *  A2 - Medium Risk Account
                             *  A3 - Low Risk Account
                             *  XX - Not categorised                                                                                                    
                             */
                            if (riskCategory.equalsIgnoreCase("03")) {
                                arfAcc.setRiskRating("A3");
                            } else if (riskCategory.equalsIgnoreCase("02")) {
                                arfAcc.setRiskRating("A2");
                            } else if (riskCategory.equalsIgnoreCase("01")) {
                                arfAcc.setRiskRating("A1");
                            } else if (riskCategory.equalsIgnoreCase("C4")) {
                                arfAcc.setRiskRating("A1");
                            } else {
                                arfAcc.setRiskRating("A1");
                            }

                            arfAcc.setCumulativeCreditTurnover(allCrAmt);
                            arfAcc.setCumulativeDebitTurnover(allDrAmt);
                            arfAcc.setCumulativeCashDepositTurnover(cashCrAmt);
                            arfAcc.setCumulativeCashWithdrawalTurnover(cashDrAmt);
                            arfAcc.setNoTransactionsTobeReported("Y");

                            arfAccPojos.add(arfAcc);
                            /**
                             * End of CBAACC *
                             */
                            /**
                             * Start of CBALPE *
                             */
//                            arfLpe.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf(arfAccLine), 6)));
                            arfLpe.setReportSerialNum(toDt);
                            arfLpe.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
//                            arfLpe.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                            arfLpe.setAccountNumber(acNo);
                            arfLpe.setPersonName(custName);
                            arfLpe.setCustomerId(custId);

                            /*  Realtion Flag
                             *  A - Account Holder
                             B - Authorised Signatory
                             C - Proprietor/Director/Partner/Member of a legal entity
                             D - Introducer
                             E – Guarantor
                             F - Guardian
                             N – Nominee
                             O – Beneficial Owner
                             P – Proposer
                             G - Assignee
                             L - Life Assured
                             J – Beneficiary
                             H – Power of Attorney
                             Z - Others
                             X - Not Categorised.
                             */
                            if (operMode.equalsIgnoreCase("1")) {
                                arfLpe.setRelationFlag("A");
                            } else {
                                arfLpe.setRelationFlag("B");
                            }
                            //                    arfLpe.setRelationFlag("A");
                            arfLpe.setCommunicationAddress((mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).trim().length() > 10 ? (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)) : (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", mailCityCode)));    //No, building//Street, Road//Locality
                            arfLpe.setCity(mailCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", mailCityCode));                                                              //City/Town, District
                            arfLpe.setStateCode(mailStateCode.equalsIgnoreCase("0") ? "" : mailStateCode);
                            arfLpe.setPinCode(mailPostalCode);
                            arfLpe.setCountryCode(mailCountryCode.equalsIgnoreCase("0") ? "IN" : (mailCountryCode.equalsIgnoreCase("") ? "IN" : mailCountryCode.substring(0, 2)));

                            arfLpe.setSecondAddress((perAddressLine1.concat(" ").concat(perAddressLine2).concat(" ").concat(perBlock)).trim().length() > 10 ? (perAddressLine1.concat(" ").concat(perAddressLine2).concat(" ").concat(perBlock)) : (perAddressLine1.concat(" ").concat(perAddressLine2).concat(" ").concat(perBlock)).concat(" ").concat(perCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", perCityCode)));    //No, building//Street, Road//Locality
                            arfLpe.setSecondCity(perCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", perCityCode));
                            arfLpe.setSecondStateCode(perStateCode.equalsIgnoreCase("0") ? "" : perStateCode);
                            arfLpe.setSecondPinCode(perPostalCode);
                            arfLpe.setSecondCountryCode(perCountryCode.equalsIgnoreCase("0") ? "IN" : (perCountryCode.equalsIgnoreCase("") ? "IN" : perCountryCode.substring(0, 2)));

                            arfLpe.setTelephone(mailPhoneNumber.equalsIgnoreCase("") ? (perPhoneNumber.equalsIgnoreCase("") ? "" : (perPhoneNumber.length() <= 10 ? "0".concat(perPhoneNumber) : perPhoneNumber)) : (mailPhoneNumber.length() <= 10 ? "0".concat(mailPhoneNumber) : mailPhoneNumber));
                            arfLpe.setMobile(mobileNo.equalsIgnoreCase("") ? "" : (mobileNo.length() == 10 ? mobileNo : ""));
                            arfLpe.setFax(mailFaxNumber.equalsIgnoreCase("") ? (perFaxNumber.equalsIgnoreCase("") ? "" : (perFaxNumber.length() <= 10 ? "0".concat(perFaxNumber) : perFaxNumber)) : (mailFaxNumber.length() <= 10 ? "0".concat(mailFaxNumber) : mailFaxNumber));
                            arfLpe.setEmail(eMailId);
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
                            } else {
                                pan = "";
                            }
                            arfLpe.setPan(pan);
                            arfLpe.setUin(uin);
                            /*  Constitution Type
                             A – Sole  Proprietorship
                             B – Partnership  Firm
                             C – HUF (Hindu Undivided family)
                             D – Private  Limited Company
                             E – Public  Limited Company
                             F – Society 
                             G – Association 
                             H – Trust 
                             I – Liquidator 
                             J – LLP (Limited Liability Partnership)
                             Z – Others (Not listed above)
                             X – Not Categorised.
                             */
                            arfLpe.setConstitutionType("Z");
                            arfLpe.setRegistrationNumber("");
                            arfLpe.setDateOfIncorporation("");
                            arfLpe.setPlaceOfRegistration("");
                            arfLpe.setRegisteredCountryCode("IN");
                            arfLpe.setNatureOfBusiness("");
                            if (!operMode.equalsIgnoreCase("1")) {
                                lpeLineNo = lpeLineNo + 1;
                                arfLpe.setLineNumber(lpeLineNo);
                                arfLpePojos.add(arfLpe);
                            }
                            /**
                             * End of ARFLPE *
                             */
                            /**
                             * Start of ARFINP *
                             */
                            List jointList;
                            if (operMode.equalsIgnoreCase("1")) {
                                jointList = em.createNativeQuery("select ifnull(cast(custid as char),'') from customerid  where acno = '" + acNo + "'").getResultList();
                            } else {
                                jointList = em.createNativeQuery("select ifnull(custid1,'') from accountmaster where acno='" + acNo + "'"
                                        + " union all select ifnull(custid2,'') from accountmaster where acno='" + acNo + "'"
                                        + " union all select ifnull(custid3,'') from accountmaster where acno='" + acNo + "'"
                                        + " union all select ifnull(custid4,'') from accountmaster where acno='" + acNo + "'").getResultList();
                            }
//                            List jointList = em.createNativeQuery("select ifnull(cast(custid as char),'') from customerid  where acno = '" + acNo + "'" //                            + " union all select isnull(custid1,'') from accountmaster where acno='" + acNo + "'"
//                                    //                            + " union all select isnull(custid2,'') from accountmaster where acno='" + acNo + "'"
//                                    //                            + " union all select isnull(custid3,'') from accountmaster where acno='" + acNo + "'"
//                                    //                            + " union all select isnull(custid4,'') from accountmaster where acno='" + acNo + "'"
//                                    ).getResultList();
                            for (int k = 0; k < jointList.size(); k++) {
                                Vector jointVectList = (Vector) jointList.get(k);
                                String jointCustId = jointVectList.get(0).toString();
                                if (!jointCustId.equalsIgnoreCase("")) {
                                    inpLineNo = inpLineNo + 1;
                                    ArfInpPojo arfInp = new ArfInpPojo();
                                    List jointDetails = em.createNativeQuery("select ifnull(c.custname,''),ifnull(c.gender,'X'),"
                                            + " concat(ifnull(c.fathername,''),if(ifnull(c.FatherMiddleName,'')= '',ifnull(c.FatherMiddleName,''),concat(' ', ifnull(c.FatherMiddleName,''))),"
                                            + " if(ifnull(c.FatherLastName,'')= '', ifnull(c.FatherLastName,''),concat(' ', ifnull(c.FatherLastName,'')))) as FatherName,ifnull(c.DateOfBirth,'1900-04-01'),ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'C' and CustEntityType = '01'),"
                                            + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'C' and CustEntityType = '01'), "
                                            + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')"
                                            + " ), ifnull(c.PAN_GIRNumber,'')) as Pan, "
                                            + " IFNULL(c.PerAddressLine1,''), IFNULL(c.PerAddressLine2,''), IFNULL(c.PerVillage,''), IFNULL(c.PerBlock,''), IFNULL(c.PerCityCode,'0'), IFNULL(c.PerStateCode,''),"
                                            + " IFNULL(c.PerPostalCode,''), IFNULL(c.PerCountryCode,''), IFNULL(c.PerPhoneNumber,''), IFNULL(c.PerTelexNumber,''), IFNULL(c.PerFaxNumber,''), "
                                            + " IFNULL(c.MailAddressLine1,''), IFNULL(c.MailAddressLine2,''), IFNULL(c.MailVillage,''), IFNULL(c.MailBlock, ''), IFNULL(c.MailCityCode, '0'),"
                                            + " IFNULL(c.MailStateCode, ''), IFNULL(c.MailPostalCode, ''), IFNULL(c.MailCountryCode, ''), IFNULL(c.MailPhoneNumber, ''), IFNULL(c.MailTelexNumber, ''),"
                                            + " IFNULL(c.MailFaxNumber, ''), IFNULL(c.mobilenumber,''), IFNULL(c.EmailID,''), "
                                            + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, "
                                            + " IFNULL(c.IssueDate, ''), IFNULL(c.issuingAuthority, ''), IFNULL(c.Expirydate, ''), IFNULL(c.PlaceOfIssue, ''), "
                                            + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, "
                                            + " ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'D' and CustEntityType = '01'),'') as DrivingLicenseNo, "
                                            + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'), "
                                            + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'), "
                                            + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01') "
                                            + "), ifnull(c.AADHAAR_NO,'')) as AadhaarNo,  "
                                            + " ifnull(middle_name,''), ifnull(last_name,'') from cbs_customer_master_detail c where c.customerid = '" + jointCustId + "'").getResultList();
                                    Vector jointVect = (Vector) jointDetails.get(0);
                                    String jointCustName = jointVect.get(0).toString().toUpperCase();
                                    String jointGender = jointVect.get(1).toString();
                                    String jointFatherName = jointVect.get(2).toString().toUpperCase();
                                    String jointDob = jointVect.get(3).toString();
                                    String jointPan = jointVect.get(4).toString().toUpperCase();
                                    String jointPerAddressLine1 = jointVect.get(5).toString();
                                    String jointPerAddressLine2 = jointVect.get(6).toString();
                                    String jointPerVillage = jointVect.get(7).toString();
                                    String jointPerBlock = jointVect.get(8).toString();
                                    String jointPerCityCode = jointVect.get(9).toString();
                                    String jointPerStateCode = jointVect.get(10).toString();
                                    String jointPerPostalCode = jointVect.get(11).toString();
                                    String jointPerCountryCode = jointVect.get(12).toString();
                                    String jointPerPhoneNumber = jointVect.get(13).toString();
                                    String jointPerTelexNumber = jointVect.get(14).toString();
                                    String jointPerFaxNumber = jointVect.get(15).toString();
                                    String jointMailAddressLine1 = jointVect.get(16).toString();
                                    String jointMailAddressLine2 = jointVect.get(17).toString();
                                    String jointMailVillage = jointVect.get(18).toString();
                                    String jointMailBlock = jointVect.get(19).toString();
                                    String jointMailCityCode = jointVect.get(20).toString();
                                    String jointMailStateCode = jointVect.get(21).toString();
                                    String jointMailPostalCode = jointVect.get(22).toString();
                                    String jointMailCountryCode = jointVect.get(23).toString();
                                    String jointMailPhoneNumber = jointVect.get(24).toString();
                                    String jointMailTelexNumber = jointVect.get(25).toString();
                                    String jointMailFaxNumber = jointVect.get(26).toString();
                                    String jointMobileNo = jointVect.get(27).toString();
                                    String jointEMailId = jointVect.get(28).toString();
                                    String passportNo = jointVect.get(29).toString();
                                    String issueDate = jointVect.get(30).toString();
                                    String issuingAuthority = jointVect.get(31).toString();
                                    String placeOfIssue = jointVect.get(33).toString();
                                    String voterIdNo = jointVect.get(34).toString();
                                    String drivingLicenseNo = jointVect.get(35).toString();
                                    String uinNo = jointVect.get(36).toString();
                                    String jointMidName = jointVect.get(37).toString();
                                    String jointLastName = jointVect.get(38).toString();

                                    arfInp.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf(inpLineNo), 6)));
                                    arfInp.setReportSerialNum(toDt);
                                    arfInp.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
//                                    arfInp.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                                    arfInp.setAccountNumber(acNo);
                                    if (!jointMidName.equalsIgnoreCase("")) {
                                        jointCustName = jointCustName.concat(" ").concat(jointMidName);
                                    }
                                    if (!jointLastName.equalsIgnoreCase("")) {
                                        jointCustName = jointCustName.concat(" ").concat(jointLastName);
                                    }
                                    arfInp.setPersonName(jointCustName);
                                    arfInp.setCustomerId(jointCustId);

                                    /*  Realtion Flag
                                     A - Account Holder
                                     B - Authorised Signatory
                                     C - Proprietor/Director/Partner/Member of a legal entity
                                     D - Introducer
                                     E – Guarantor
                                     F - Guardian
                                     N – Nominee
                                     O – Beneficial Owner
                                     P – Proposer
                                     G - Assignee
                                     L - Life Assured
                                     J – Beneficiary
                                     H – Power of Attorney
                                     Z - Others
                                     X - Not Categorised.
                                     */
//                                    if (operMode.equalsIgnoreCase("1")) {
//                                        arfInp.setRelationFlag("A");
//                                    } else {
//                                        arfInp.setRelationFlag("B");
//                                    }                                
                                    arfInp.setRelationFlag("A");
                                    arfInp.setCommunicationAddress((jointMailAddressLine1.concat(" ").concat(jointMailAddressLine2).concat(" ").concat(jointMailBlock)).trim().length() > 10 ? (jointMailAddressLine1.concat(" ").concat(jointMailAddressLine2).concat(" ").concat(jointMailBlock)) : (jointMailAddressLine1.concat(" ").concat(jointMailAddressLine2).concat(" ").concat(jointMailBlock)).concat(" ").concat(jointMailCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", jointMailCityCode)));    //No, building//Street, Road//Locality
                                    arfInp.setCity(jointMailCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", jointMailCityCode));                             //City/Town, District
                                    arfInp.setStateCode(jointMailStateCode.equalsIgnoreCase("0") ? "" : jointMailStateCode);
                                    arfInp.setPinCode(jointMailPostalCode);
                                    arfInp.setCountryCode(jointMailCountryCode.equalsIgnoreCase("0") ? "IN" : (jointMailCountryCode.equalsIgnoreCase("") ? "IN" : jointMailCountryCode.substring(0, 2)));

                                    arfInp.setSecondAddress((jointPerAddressLine1.concat(" ").concat(jointPerAddressLine2).concat(" ").concat(jointPerBlock)).trim().length() > 10 ? (jointPerAddressLine1.concat(" ").concat(jointPerAddressLine2).concat(" ").concat(jointPerBlock)) : (jointPerAddressLine1.concat(" ").concat(jointPerAddressLine2).concat(" ").concat(jointPerBlock)).concat(" ").concat(jointPerCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", jointPerCityCode)));
                                    arfInp.setSecondCity(jointPerCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", jointPerCityCode));
                                    arfInp.setSecondStateCode(jointPerStateCode.equalsIgnoreCase("0") ? "" : jointPerStateCode);
                                    arfInp.setSecondPinCode(jointPerPostalCode);
                                    arfInp.setSecondCountryCode(jointPerCountryCode.equalsIgnoreCase("0") ? "IN" : (jointPerCountryCode.equalsIgnoreCase("") ? "IN" : jointPerCountryCode.substring(0, 2)));

                                    arfInp.setTelephone(jointMailPhoneNumber.equalsIgnoreCase("") ? (jointPerPhoneNumber.equalsIgnoreCase("") ? "" : (jointPerPhoneNumber.length() <= 10 ? "0".concat(jointPerPhoneNumber) : jointPerPhoneNumber)) : (jointMailPhoneNumber.length() <= 10 ? "0".concat(jointMailPhoneNumber) : jointMailPhoneNumber));
                                    arfInp.setMobile(jointMobileNo.equalsIgnoreCase("") ? "" : (jointMobileNo.length() == 10 ? jointMobileNo : ""));
                                    arfInp.setFax(jointMailFaxNumber.equalsIgnoreCase("") ? (jointPerFaxNumber.equalsIgnoreCase("") ? "" : (jointPerFaxNumber.length() <= 10 ? "0".concat(jointPerFaxNumber) : jointPerFaxNumber)) : (jointMailFaxNumber.length() <= 10 ? "0".concat(jointMailFaxNumber) : jointMailFaxNumber));
                                    arfInp.setEmail(jointEMailId);
                                    String jointPanFlag = "Y";
                                    if (jointPan.length() == 10) {
                                        if (!ParseFileUtil.isAlphabet(jointPan.substring(0, 5))) {
                                            jointPanFlag = "N";
                                        }
                                        if (!ParseFileUtil.isNumeric(jointPan.substring(5, 9))) {
                                            jointPanFlag = "N";
                                        }
                                        if (!ParseFileUtil.isAlphabet(jointPan.substring(9))) {
                                            jointPanFlag = "N";
                                        }
                                        if (!jointPanFlag.equalsIgnoreCase("Y")) {
                                            jointPan = "";
                                        }
                                    } else {
                                        jointPan = "";
                                    }
                                    arfInp.setPan(jointPan);
                                    arfInp.setUin(uinNo);

                                    /*  Gender
                                     M- Male
                                     F- Female
                                     X- Not Categorised. 

                                     * IN OUR SYSTEM
                                     0- Select
                                     M- Male
                                     F- Female
                                     C- Company
                                     O- Others
                                     */
                                    if (jointGender.equalsIgnoreCase("M")) {
                                        jointGender = "M";
                                    } else if (jointGender.equalsIgnoreCase("F")) {
                                        jointGender = "F";
                                    } else {
                                        jointGender = "X";
                                    }
                                    arfInp.setGender(jointGender);
                                    arfInp.setDateOfBirth(dmy.format(ymd_1.parse(jointDob)).equalsIgnoreCase("01041900") ? "" : (dmy.format(ymd_1.parse(jointDob)).equalsIgnoreCase("01011900") ? "" : ymd_1.format(ymd_1.parse(jointDob))));

                                    /*  Identification type
                                     A - Passport
                                     B - Election ID Card
                                     C - Pan Card
                                     D - ID Card
                                     E - Driving License
                                     F - Account Introducer
                                     G - UIDAI Letter
                                     H - NREGA job card
                                     Z – Others
                                     */
                                    if (!passportNo.equalsIgnoreCase("")) {
                                        arfInp.setIdentificationType("A");
                                        arfInp.setIdentificationNumber(passportNo);
                                        arfInp.setIssuingAuthority(issuingAuthority);
                                        arfInp.setPlaceOfIssue(placeOfIssue);
                                    } else if (!voterIdNo.equalsIgnoreCase("")) {
                                        arfInp.setIdentificationType("B");
                                        arfInp.setIdentificationNumber(voterIdNo);
                                        arfInp.setIssuingAuthority("");
                                        arfInp.setPlaceOfIssue("");
                                    } else if (!drivingLicenseNo.equalsIgnoreCase("")) {
                                        arfInp.setIdentificationType("E");
                                        arfInp.setIdentificationNumber(drivingLicenseNo);
                                        arfInp.setIssuingAuthority("");
                                        arfInp.setPlaceOfIssue("");
                                    } else {
                                        arfInp.setIdentificationType("Z");
                                        arfInp.setIdentificationNumber("");
                                        arfInp.setIssuingAuthority("");
                                        arfInp.setPlaceOfIssue("");
                                    }

                                    arfInp.setNationality("IN");
                                    arfInp.setPlaceOfWork("");
                                    arfInp.setFatherOrSpouse(jointFatherName);

                                    /* In OUR SYSTEM
                                     0	TYPE OF ORGANIZATION
                                     1	FARMER
                                     2	TRADER
                                     3	SMALL BUSINESSMAN
                                     4	PROFESSIONAL
                                     5	SELF EMPLOYED
                                     6	RETIRED
                                     7	HOUSEWIFE
                                     8	STUDENT
                                     9	OTHERS
                                     10	SERVICE
                                     11	INDUSTRIALIST
                                     12	INDIVIDUAL
                                     13	SHOP KEEPER
                                     14	FORUM
                                     15	INSTITUTIONS
                                     16	STAFF
                                     17	PENSION
                                     18	CENTRAL GOVERNMENT
                                     19	STATE GOVERNMENT
                                     20	BANK
                                     21	PSU
                                     22	DOCTOR
                                     */
                                    arfInp.setOccupation("");

                                    arfInpPojos.add(arfInp);
                                }
                            }
                            /**
                             * End of CBAINP *
                             */
                        }
                        preAcNo = acNo;
                    }
                    /**
                     * ********** END OF CBAACC/CBALPE *********
                     */
                }
                if (arfAccPojos.isEmpty()) {
                    ArfAccPojo accPoJo = new ArfAccPojo();
                    accPoJo.setAccountNumber("Nil");
                    arfAccPojos.add(accPoJo);
                }
                if (arfInpPojos.isEmpty()) {
                    ArfInpPojo inpPoJo = new ArfInpPojo();
                    inpPoJo.setAccountNumber("Nil");
                    arfInpPojos.add(inpPoJo);
                }
                if (arfLpePojos.isEmpty()) {
                    ArfLpePojo lpePoJo = new ArfLpePojo();
                    lpePoJo.setAccountNumber("Nil");
                    arfLpePojos.add(lpePoJo);
                }
                if (arfTrnPojos.isEmpty()) {
                    ArfTrnPojo trnPoJo = new ArfTrnPojo();
                    trnPoJo.setAccountNumber("Nil");
                    arfTrnPojos.add(trnPoJo);
                }
                arf.setArfAcc(arfAccPojos);
                arf.setArfInp(arfInpPojos);
                arf.setArfLpe(arfLpePojos);
                arf.setArfTrn(arfTrnPojos);
            }

            // ARFRPT file
            List<ArfRptPojo> arfRptList = getArfRptData(brCode, repType, userName, fromDt, toDt, "CTR");
            arf.setArfRpt(arfRptList);
            //ARFBRC file
            List<ArfBrcPojo> arfBrcList = getArfBrcData(brCode, repType);
            arf.setArfBrc(arfBrcList);

            if (brCode.length() > 4) {
                brCode = "90";
            } else {
                brCode = brCode;
            }
            //ARFBAT file
            List<ArfBatPojo> arfBatList = getArfBatData(brCode, repType, userName, fromDt, toDt);
            arf.setArfBat(arfBatList);

            return arf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public CtrArfPojo arfReportStr(List acNatureList, String acType, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName, String multiplier) throws ApplicationException {
        //        List<CtrPojo> ctrPojos = new ArrayList<CtrPojo>();
        CtrArfPojo arf = new CtrArfPojo();
        List<ArfAccPojo> arfAccPojos = new ArrayList<ArfAccPojo>();
        List<ArfTrnPojo> arfTrnPojos = new ArrayList<ArfTrnPojo>();
        List<ArfInpPojo> arfInpPojos = new ArrayList<ArfInpPojo>();
        List<ArfLpePojo> arfLpePojos = new ArrayList<ArfLpePojo>();

        try {
            Integer arfAccLine = 0, inpLineNo = 0, trnLineNo = 0, lpeLineNo = 0;
            Double ctrLimit = 0d;
            List<strCommonPanMobilePhoneEmailPojo> commonPanMobileEmailPhone = new ArrayList<strCommonPanMobilePhoneEmailPojo>();
            String panNoQuery = "", mobileNoQuery = "", phoneNoQuery = "", emailIdQuery = "", commonQuery = "";
            commonQuery = "select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " ca_recon a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 "
                    + " union all "
                    + " select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " recon a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 "
                    + " union all "
                    + " select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " loan_recon a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 "
                    + " union all "
                    + " select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " rdrecon a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 "
                    + " union all "
                    + " select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " td_recon a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 "
                    + " union all "
                    + " select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " ddstransaction a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 "
                    + " union all "
                    + " select c.customerid, ifnull(c.PAN_GIRNumber,'') as mpan, ifnull(c.mobilenumber,'') as mobilenumber, ifnull(c.EmailID,'') as EmailID, ifnull(c.PhoneNumber,'') as PhoneNumber, a.acno as macno, a.dt as mdt, sum(a.cramt) as cramt from "
                    + " of_recon a, customerid b, cbs_customer_master_detail c  where a.ACNo = b.Acno and b.CustId = c.customerid "
                    + " and  a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype=0  and ty = 0 "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) group by a.acno ,a.dt having cramt between 100000 and 250000 ";

            panNoQuery = "select mm.mpan, count(mm.macno) ,sum(mm.mdt), sum(mm.amt) as mmamt from "
                    + " (select m.mpan, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where length(mpan)=10 "
                    + " group by m.mpan, m.macno having count(m.mdt)>=1 order by m.mpan) mm "
                    + " group by mm.mpan having count(mm.macno)>1 and sum(mm.mdt)>3 ";
            List panListQuery = em.createNativeQuery(panNoQuery).getResultList();
            String mainPan = "", acNoFromList = "", acNoQuery = "";
            if (!panListQuery.isEmpty()) {
                for (int z = 0; z < panListQuery.size(); z++) {
                    Vector panVect = (Vector) panListQuery.get(z);
                    mainPan = panVect.get(0).toString();
                    acNoQuery = "select m.mpan, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where m.mpan='" + mainPan + "' "
                            + " and substring(m.macno,1,2) in(" + brCode + ") and substring(m.macno,3,2) in (" + acType + ") "
                            + "group by m.mpan, m.macno having count(m.mdt)>=1 order by m.mpan";
                    List acNoWiseList = em.createNativeQuery(acNoQuery).getResultList();
                    if (!acNoWiseList.isEmpty()) {
                        for (int y = 0; y < acNoWiseList.size(); y++) {
                            Vector acNoWiseVect = (Vector) acNoWiseList.get(y);
                            acNoFromList = acNoWiseVect.get(1).toString();
                            strCommonPanMobilePhoneEmailPojo panPojo = new strCommonPanMobilePhoneEmailPojo();
                            panPojo.setAcNo(acNoWiseVect.get(1).toString());
                            panPojo.setCommonNo(acNoWiseVect.get(0).toString());
                            panPojo.setCommonType("PAN");
                            panPojo.setNoOfTrans(Integer.parseInt(acNoWiseVect.get(2).toString()));
                            panPojo.setValueOfTran(new BigDecimal(acNoWiseVect.get(3).toString()));
                            commonPanMobileEmailPhone.add(panPojo);
                        }
                    }
                }
            }
            mobileNoQuery = "select mm.mobilenumber, count(mm.macno) ,sum(mm.mdt), sum(mm.amt) as mmamt from "
                    + " (select m.mobilenumber, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where mobilenumber<>'' "
                    + " group by m.mobilenumber, m.macno having count(m.mdt)>=1 order by m.mobilenumber) mm "
                    + " group by mm.mobilenumber having count(mm.macno)>1 and sum(mm.mdt)>3 ";
            panListQuery = em.createNativeQuery(mobileNoQuery).getResultList();
            mainPan = "";
            acNoFromList = "";
            acNoQuery = "";
            if (!panListQuery.isEmpty()) {
                for (int z = 0; z < panListQuery.size(); z++) {
                    Vector panVect = (Vector) panListQuery.get(z);
                    mainPan = panVect.get(0).toString();
                    acNoQuery = "select m.mobilenumber, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where m.mobilenumber='" + mainPan + "' "
                            + " and substring(m.macno,1,2) in(" + brCode + ") and substring(m.macno,3,2) in (" + acType + ") "
                            + " group by m.mobilenumber, m.macno having count(m.mdt)>=1 order by m.mobilenumber";
                    List acNoWiseList = em.createNativeQuery(acNoQuery).getResultList();
                    if (!acNoWiseList.isEmpty()) {
                        for (int y = 0; y < acNoWiseList.size(); y++) {
                            Vector acNoWiseVect = (Vector) acNoWiseList.get(y);
                            acNoFromList = acNoWiseVect.get(1).toString();
                            strCommonPanMobilePhoneEmailPojo panPojo = new strCommonPanMobilePhoneEmailPojo();
                            panPojo.setAcNo(acNoWiseVect.get(1).toString());
                            panPojo.setCommonNo(acNoWiseVect.get(0).toString());
                            panPojo.setCommonType("MOBILE");
                            panPojo.setNoOfTrans(Integer.parseInt(acNoWiseVect.get(2).toString()));
                            panPojo.setValueOfTran(new BigDecimal(acNoWiseVect.get(3).toString()));
                            commonPanMobileEmailPhone.add(panPojo);
                        }
                    }
                }
            }
            phoneNoQuery = "select mm.PhoneNumber, count(mm.macno) ,sum(mm.mdt), sum(mm.amt) as mmamt from "
                    + " (select m.PhoneNumber, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where PhoneNumber<>'' "
                    + " group by m.PhoneNumber, m.macno having count(m.mdt)>=1 order by m.PhoneNumber) mm "
                    + " group by mm.PhoneNumber having count(mm.macno)>1 and sum(mm.mdt)>3 ";
            panListQuery = em.createNativeQuery(phoneNoQuery).getResultList();
            mainPan = "";
            acNoFromList = "";
            acNoQuery = "";
            if (!panListQuery.isEmpty()) {
                for (int z = 0; z < panListQuery.size(); z++) {
                    Vector panVect = (Vector) panListQuery.get(z);
                    mainPan = panVect.get(0).toString();
                    acNoQuery = "select m.mpan, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where m.mpan='" + mainPan + "' "
                            + " and substring(m.macno,1,2) in(" + brCode + ") and substring(m.macno,3,2) in (" + acType + ") "
                            + "group by m.mpan, m.macno having count(m.mdt)>=1 order by m.mpan";
                    List acNoWiseList = em.createNativeQuery(acNoQuery).getResultList();
                    if (!acNoWiseList.isEmpty()) {
                        for (int y = 0; y < acNoWiseList.size(); y++) {
                            Vector acNoWiseVect = (Vector) acNoWiseList.get(y);
                            acNoFromList = acNoWiseVect.get(1).toString();
                            strCommonPanMobilePhoneEmailPojo panPojo = new strCommonPanMobilePhoneEmailPojo();
                            panPojo.setAcNo(acNoWiseVect.get(1).toString());
                            panPojo.setCommonNo(acNoWiseVect.get(0).toString());
                            panPojo.setCommonType("PHONE");
                            panPojo.setNoOfTrans(Integer.parseInt(acNoWiseVect.get(2).toString()));
                            panPojo.setValueOfTran(new BigDecimal(acNoWiseVect.get(3).toString()));
                            commonPanMobileEmailPhone.add(panPojo);
                        }
                    }
                }
            }
            emailIdQuery = "select mm.EmailID, count(mm.macno) ,sum(mm.mdt), sum(mm.amt) as mmamt from "
                    + " (select m.EmailID, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where EmailID<>'' "
                    + " group by m.EmailID, m.macno having count(m.mdt)>=1 order by m.EmailID) mm "
                    + " group by mm.EmailID having count(mm.macno)>1 and sum(mm.mdt)>3 ";
            panListQuery = em.createNativeQuery(emailIdQuery).getResultList();
            mainPan = "";
            acNoFromList = "";
            acNoQuery = "";
            if (!panListQuery.isEmpty()) {
                for (int z = 0; z < panListQuery.size(); z++) {
                    Vector panVect = (Vector) panListQuery.get(z);
                    mainPan = panVect.get(0).toString();
                    acNoQuery = "select m.mpan, m.macno,count(m.mdt) as mdt, sum(m.cramt) as amt from (" + commonQuery + ") m where m.mpan='" + mainPan + "' "
                            + " and substring(m.macno,1,2) in(" + brCode + ") and substring(m.macno,3,2) in (" + acType + ") "
                            + "group by m.mpan, m.macno having count(m.mdt)>=1 order by m.mpan";
                    List acNoWiseList = em.createNativeQuery(acNoQuery).getResultList();
                    if (!acNoWiseList.isEmpty()) {
                        for (int y = 0; y < acNoWiseList.size(); y++) {
                            Vector acNoWiseVect = (Vector) acNoWiseList.get(y);
                            acNoFromList = acNoWiseVect.get(1).toString();
                            strCommonPanMobilePhoneEmailPojo panPojo = new strCommonPanMobilePhoneEmailPojo();
                            panPojo.setAcNo(acNoWiseVect.get(1).toString());
                            panPojo.setCommonNo(acNoWiseVect.get(0).toString());
                            panPojo.setCommonType("EMAIL");
                            panPojo.setNoOfTrans(Integer.parseInt(acNoWiseVect.get(2).toString()));
                            panPojo.setValueOfTran(new BigDecimal(acNoWiseVect.get(3).toString()));
                            commonPanMobileEmailPhone.add(panPojo);
                        }
                    }
                }
            }

            if (!commonPanMobileEmailPhone.isEmpty()) {
                commonQuery = "";
                for (int w = 0; w < commonPanMobileEmailPhone.size(); w++) {
                    if (w == 0) {
                        commonQuery = commonQuery.concat(" select '").concat(commonPanMobileEmailPhone.get(w).getAcNo()).concat("' as acno ");
                    } else {
                        commonQuery = commonQuery.concat(" union all select '").concat(commonPanMobileEmailPhone.get(w).getAcNo()).concat("'  as acno ");
                    }
                }
            }

            for (int i = 0; i < acNatureList.size(); i++) {
                String acType1 = "";
                Vector acnatureVect = (Vector) acNatureList.get(i);
                String acNature = acnatureVect.get(0).toString();
                List acTypeList = common.getAccType(acnatureVect.get(0).toString());
                if (acTypeList.size() > 0) {
                    for (int z = 0; z < acTypeList.size(); z++) {
                        Vector actypevect = (Vector) acTypeList.get(z);
                        if (z == 0) {
                            acType1 = "'" + actypevect.get(0).toString() + "'";
                        } else {
                            acType1 = acType1.concat(",'" + actypevect.get(0).toString() + "'");
                        }
                    }
                }
                acType = acType1;
                String preAcNo = "0", commTypeQuery = "";
                List acTransactionDetailsList = null;
                if (!commonQuery.equalsIgnoreCase("")) {
                    commTypeQuery = " union all select a.macno as acno from (" + commonQuery + ") a where substring(a.macno,1,2) in (" + brCode + ") and substring(a.macno,3,2) in (" + acType + ") ";
                }
                if (repType.equalsIgnoreCase("STR DEM")) {
                    String dormantQuery = "";

                    if (acNature.equalsIgnoreCase("SB") || acNature.equalsIgnoreCase("CA")) {
                        dormantQuery = "union all select a.acno as acno from accountstatus a, "
                                + "(select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                                + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                                + "group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                                + "(select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                                + "(select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and tranType in(0) group by acno having(sum(cramt)) >= 500000) reconCr, "
                                + "accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                                + "and  ac.acno = reconCr.acno  "
                                + "and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and  "
                                + "substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and  "
                                + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                                + "union all "
                                + " select a.acno as acno from accountstatus a, "
                                + "(select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                                + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                                + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                                + "(select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                                + "(select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and tranType <> 0 group by acno having(sum(cramt)) >= 1000000) reconCr, "
                                + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                                + " and  ac.acno = reconCr.acno  "
                                + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and  "
                                + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and  "
                                + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                                + " ";
                    }

                    acTransactionDetailsList = em.createNativeQuery("select distinct aa.acno from (select a.acno from (select acno "
                            + "from " + CbsUtil.getReconTableName(acNature) + " where  dt between '" + fromDt + "' and '" + toDt + "' and trantype=0 and  substring(acno,1,2) in (" + brCode + ") and substring(acno,3,2) in (" + acType + ") group by acno having(sum(cramt)) >=250000)a, "
                            + "(select acno from accountmaster where curbrcode in (" + brCode + ") and accttype in(" + acType + ")  and acctCategory in('IND','UN') and accstatus <> 9)b "
                            + "where a.acno = b.acno  union all "
                            + "select a.acno from (select acno "
                            + "from " + CbsUtil.getReconTableName(acNature) + " where  dt between '" + fromDt + "' and '" + toDt + "' and trantype=0 and  substring(acno,1,2) in (" + brCode + ") and substring(acno,3,2) in (" + acType + ") group by acno having(sum(cramt)) >=1000000)a, "
                            + "(select acno from accountmaster where curbrcode in (" + brCode + ") and accttype in(" + acType + ") and acctCategory in('HUF','PF') and accstatus <> 9)b "
                            + "where a.acno = b.acno union all "
                            + "select a.acno from (select acno "
                            + "from " + CbsUtil.getReconTableName(acNature) + " where  dt between '" + fromDt + "' and '" + toDt + "' and trantype=0 and  substring(acno,1,2) in (" + brCode + ") and substring(acno,3,2) in (" + acType + ") group by acno having(sum(cramt)) >= 1500000)a,"
                            + "(select acno from accountmaster where curbrcode in (" + brCode + ") and accttype in(" + acType + ") and acctCategory not in('IND','PF','HUF','UN') and accstatus <> 9)b "
                            + "where a.acno = b.acno " + dormantQuery + commTypeQuery + ")aa order by aa.acno").getResultList();

                } else {
                    acTransactionDetailsList = em.createNativeQuery("select a.acno from cbs_str_detail a," + CbsUtil.getReconTableName(acNature) + " r where a.acno = r.acno and a.flag = 'STR' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.dt=r.dt "
                            + " and substring(a.acno,1,2) in (" + brCode + ") and substring(a.acno,3,2) in (" + acType + ") group by a.acno,a.dt "
                            + " union all "
                            + " select r.acno from " + CbsUtil.getReconTableName(acNature) + " r, (select acno from customerid where custid in (select customerid from cbs_customer_master_detail where strFlag ='WL1.1'))b "
                            + " where r.acno=b.acno and r.dt between '" + fromDt + "' and '" + toDt + "' and substring(b.acno,1,2) in (" + brCode + ")and substring(b.acno,3,2) in (" + acType + ")"
                            + " group by b.acno,r.dt "
                            + " union all "
                            + " select distinct aa.acno from "
                            + " (select b.CustId as CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2)) as cramt from " + CbsUtil.getReconTableName(acNature) + " a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,1,2) in (" + brCode + ") and substring(a.acno,3,2) in (" + acType + ") "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 and b.CustId in "
                            + " (select mm.CustId from "
                            + " (select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by a.acno having cramt between 40000 and 49999 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt,  cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 40000 and 49999 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 40000 and 49999 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0  group by  a.acno having cramt between 40000 and 49999 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 40000 and 49999 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 40000 and 49999 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 40000 and 49999 "
                            + " )mm group by mm.CustId having sum(cnt) >(select code from cbs_parameterinfo where name = 'TY1.2_40_49THOUSAND_CNT')) "
                            + " group by a.acno having cramt between 40000 and 49999) aa"
                            + " union all "
                            + " select distinct aa.acno from "
                            + " (select b.CustId as CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2)) as cramt from " + CbsUtil.getReconTableName(acNature) + " a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,1,2) in (" + brCode + ") and substring(a.acno,3,2) in (" + acType + ") "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 and b.CustId in "
                            + " (select mm.CustId from "
                            + " (select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by a.acno having cramt between 900000 and 999999.99 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt,  cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 900000 and 999999.99 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 900000 and 999999.99 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0  group by  a.acno having cramt between 900000 and 999999.99 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 900000 and 999999.99 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 900000 and 999999.99 "
                            + " union all "
                            + " select b.CustId as CustId, a.acno, 1 as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt between 900000 and 999999.99 "
                            + " )mm group by mm.CustId having sum(cnt) >(select code from cbs_parameterinfo where name = 'TY1.1_9_9.99LAC_CNT')) "
                            + " group by a.acno having cramt between 900000 and 999999.99) aa").getResultList();

                }

                String dt = "", tranType = "", ty = "", lastDt = "", strReason = "";
                BigDecimal crAmt = new BigDecimal("0");
                BigDecimal drAmt = new BigDecimal("0");
                float recNo = 0;
                double halfDepositAmt = 0;
                for (int j = 0; j < acTransactionDetailsList.size(); j++) {
                    Vector acTranVect = (Vector) acTransactionDetailsList.get(j);
                    String acNo = acTranVect.get(0).toString();
                    double avgBalance = 0;
                    boolean strFlag = false;
                    List result = new ArrayList();
                    /*1 for individul ('IND','JLG')
                     *8 for Proprietor/ HUF ('HUF','PF')  
                     * <> 0,1,8,18 for Legal entities not in('IND','JLG','PF','HUF','UN')
                     */
                    //List result1 = em.createNativeQuery("select acctCategory from accountmaster where acno = '" + acNo + "'").getResultList();
                    List result1 = em.createNativeQuery("select a.acctCategory,b.ref_desc from accountmaster a,cbs_ref_rec_type b where a.acno = '" + acNo + "' and b.ref_rec_no = '325' and a.acctCategory = b.ref_code "
                            + " union all "
                            + " select a.acctCategory,b.ref_desc from td_accountmaster a,cbs_ref_rec_type b where a.acno = '" + acNo + "' and b.ref_rec_no = '325' and a.acctCategory = b.ref_code").getResultList();
                    Vector opmVector = (Vector) result1.get(0);
                    String opMode = opmVector.get(0).toString();
                    String acDeac = opmVector.get(1).toString();

                    if (repType.equalsIgnoreCase("STR DEM")) {

                        result = em.createNativeQuery("select a.acno,a.dt,a.depositAmount,b.withdrawalAmount,a.trantype,a.ty,a.recno,a.LastTrnDt,a.compAmt from"
                                + "(select acno,dt,cast(sum(cramt) as decimal(25,2)) depositAmount,trantype,ty,recno,DATE_ADD(max(dt), INTERVAL 5 DAY) LastTrnDt,cast(sum(cramt) as decimal(25,2))/2 compAmt "
                                + "from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype=0 and ty=0)a,"
                                + "(select acno,cast(sum(dramt) as decimal(25,2)) withdrawalAmount from " + CbsUtil.getReconTableName(acNature) + "  where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "')b where a.acno = '" + acNo + "'").getResultList();

                        if (result.isEmpty()) {
                            result = em.createNativeQuery("select a.acno,a.dt,a.depositAmount,b.withdrawalAmount,a.trantype,a.ty,a.recno,a.LastTrnDt,a.compAmt from"
                                    + "(select acno,dt,cast(sum(cramt) as decimal(25,2)) depositAmount,trantype,ty,recno,DATE_ADD(max(dt), INTERVAL 5 DAY) LastTrnDt,cast(sum(cramt) as decimal(25,2))/2 compAmt "
                                    + "from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype <> 0 and ty=0)a,"
                                    + "(select acno,cast(sum(dramt) as decimal(25,2)) withdrawalAmount from " + CbsUtil.getReconTableName(acNature) + "  where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "')b where a.acno = '" + acNo + "'").getResultList();
                        }
                        if (!result.isEmpty()) {
                            Vector acVector = (Vector) result.get(0);
                            acNo = acVector.get(0).toString();
                            dt = acVector.get(1).toString();
                            crAmt = new BigDecimal(acVector.get(2).toString());
                            drAmt = new BigDecimal(acVector.get(3).toString());
                            tranType = acVector.get(4).toString();
                            ty = acVector.get(5).toString();
                            recNo = Float.parseFloat(acVector.get(6).toString());
                            lastDt = acVector.get(7).toString();
                            halfDepositAmt = Double.parseDouble(acVector.get(8).toString());

                            //Operation Mode checking
                            // Dormant Account / Inoperative Account
                            List result3 = em.createNativeQuery("select a.acno,npa.npaSpflag from accountstatus a,(select ast.acno as npaAcno,ast.effdt as npaEffDt,"
                                    + "ast.spflag as npaSpflag  from accountstatus ast, (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                    + "(select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                                    + "where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt "
                                    + "and ast.spno = c.sno  and ast.acno = '" + acNo + "' ) npa, "
                                    + "(select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c ,accountmaster ac where  "
                                    + "a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno and  a.spflag in (2)  and a.effdt = npa.npaEffDt "
                                    + "and a.spno = c.sno  and a.effdt <='20161107' and (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107') "
                                    + "and ac.acno = '" + acNo + "'").getResultList();

                            if (!result3.isEmpty()) {
                                Vector dormantVector = (Vector) result3.get(0);
                                String dormantAcno = dormantVector.get(0).toString();
                                String dormantStatus = dormantVector.get(1).toString();
                                List dormantList = em.createNativeQuery("select ifnull(cast(sum(cramt)as decimal(25,2)),0),ifnull(cast(sum(cramt)as decimal(25,2)),0)/2 from recon where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype =  0 and ty = 0").getResultList();
                                Vector ele1 = (Vector) dormantList.get(0);
                                double dorAmt = Double.parseDouble(ele1.get(0).toString());
                                if (dormantStatus.equalsIgnoreCase("2") && dorAmt >= 500000) {
                                    strFlag = true;
                                    strReason = "2. Deposits of Rs.5 lakh and above in case of cash and Rs. 10 lakhs and above in other cases,in single or multiple transactions in dormant accounts.";
                                } else {
                                    strFlag = false;
                                }

                                if (strFlag == false) {
                                    List dormantList2 = em.createNativeQuery("select ifnull(cast(sum(cramt)as decimal(25,2)),0),ifnull(cast(sum(cramt)as decimal(25,2)),0)/2 from recon where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype <>  0 and ty = 0").getResultList();
                                    Vector ele2 = (Vector) dormantList2.get(0);
                                    double dorAmt2 = Double.parseDouble(ele2.get(0).toString());
                                    if (dormantStatus.equalsIgnoreCase("2") && dorAmt2 >= 1000000) {
                                        strFlag = true;
                                        strReason = "2. Deposits of Rs.5 lakh and above in case of cash and Rs. 10 lakhs and above in other cases,in single or multiple transactions in dormant accounts.";
                                    } else {
                                        strFlag = false;
                                    }
                                }
                            }
                        }
                        // Average
                        if ((opMode.equalsIgnoreCase("IND") || opMode.equalsIgnoreCase("UN")) && crAmt.doubleValue() > 250000) {
                            if (tranType.equalsIgnoreCase("0") && crAmt.doubleValue() >= 250000) {
                                List result2 = em.createNativeQuery("select ifnull(cast(avg(ifnull(a.cramt,0)) as decimal(25,2)),0) from (select (sum(cramt)) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + CbsUtil.monthAdd(fromDt, -6) + "' and '" + CbsUtil.dateAdd(fromDt, -1) + "'  and trantype = 0 and ty = 0 group by dt )a").getResultList();
                                Vector avgVector = (Vector) result2.get(0);
                                avgBalance = Double.parseDouble(avgVector.get(0).toString());
                                avgBalance = avgBalance * Double.parseDouble(multiplier); // X=1;
                                List avgCrAmtDuringDemPdList = em.createNativeQuery("select ifnull(cast(avg(ifnull(a.cramt,0)) as decimal(25,2)),0) from (select (sum(cramt)) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + fromDt + "' and '" + toDt + "'  and trantype = 0 and ty = 0 group by dt )a").getResultList();
                                Vector avgCrAmtDuringDemPdVector = (Vector) avgCrAmtDuringDemPdList.get(0);
                                Double avgCrAmtDuringDemPdBalance = Double.parseDouble(avgCrAmtDuringDemPdVector.get(0).toString());
                                if (avgBalance > avgCrAmtDuringDemPdBalance) {
                                    strFlag = false;
                                } else {
                                    strFlag = true;
                                    strReason = "1. Cash deposits during specified period is" + multiplier + "X times higher than cash deposit in earlier period.\n " + opMode + " [" + acDeac + "]";
                                }
                            }
                        } else if ((opMode.equalsIgnoreCase("HUF") || opMode.equalsIgnoreCase("PF")) && crAmt.doubleValue() >= 1000000) {
                            if (tranType.equalsIgnoreCase("0") && crAmt.doubleValue() >= 1000000) {
                                List result2 = em.createNativeQuery("select ifnull(cast(avg(a.cramt) as decimal(25,2)),0) from (select (sum(cramt)) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + CbsUtil.monthAdd(fromDt, -6) + "' and '" + fromDt + "'  and trantype = 0 and ty = 0 group by dt )a").getResultList();
                                Vector avgVector = (Vector) result2.get(0);
                                avgBalance = Double.parseDouble(avgVector.get(0).toString());
                                avgBalance = avgBalance * Integer.parseInt(multiplier); // X=1;
                                List avgCrAmtDuringDemPdList = em.createNativeQuery("select ifnull(cast(avg(ifnull(a.cramt,0)) as decimal(25,2)),0) from (select (sum(cramt)) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + fromDt + "' and '" + toDt + "'  and trantype = 0 and ty = 0 group by dt )a").getResultList();
                                Vector avgCrAmtDuringDemPdVector = (Vector) avgCrAmtDuringDemPdList.get(0);
                                Double avgCrAmtDuringDemPdBalance = Double.parseDouble(avgCrAmtDuringDemPdVector.get(0).toString());
                                if (avgBalance > avgCrAmtDuringDemPdBalance) {
                                    strFlag = false;
                                } else {
                                    strFlag = true;
                                    strReason = "1. Cash deposits during specified period is" + multiplier + "X times higher than cash deposit in earlier period.\n " + opMode + " [" + acDeac + "]";
                                }
                            }

                        } else if ((opMode.equalsIgnoreCase("CGD")
                                || opMode.equalsIgnoreCase("CGP")
                                || opMode.equalsIgnoreCase("COS")
                                || opMode.equalsIgnoreCase("LB")
                                || opMode.equalsIgnoreCase("MI")
                                || opMode.equalsIgnoreCase("NGO")
                                || opMode.equalsIgnoreCase("PCS")
                                || opMode.equalsIgnoreCase("PC")
                                || opMode.equalsIgnoreCase("SGD")
                                || opMode.equalsIgnoreCase("SGP")
                                || opMode.equalsIgnoreCase("SB")
                                || opMode.equalsIgnoreCase("TG")
                                || opMode.equalsIgnoreCase("FI")
                                || opMode.equalsIgnoreCase("PL")
                                || opMode.equalsIgnoreCase("AB")
                                || opMode.equalsIgnoreCase("LC")
                                || opMode.equalsIgnoreCase("RS")
                                || opMode.equalsIgnoreCase("JLG"))
                                && crAmt.doubleValue() > 1500000) {

                            if (tranType.equalsIgnoreCase("0") && crAmt.doubleValue() >= 1500000) {
                                List result2 = em.createNativeQuery("select ifnull(cast(avg(a.cramt) as decimal(25,2)),0) from (select (sum(cramt)) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + CbsUtil.monthAdd(fromDt, -6) + "' and '" + fromDt + "'  and trantype = 0 and ty = 0 group by dt )a").getResultList();
                                Vector avgVector = (Vector) result2.get(0);
                                avgBalance = Double.parseDouble(avgVector.get(0).toString());
                                avgBalance = avgBalance * Integer.parseInt(multiplier); // X=1;
                                List avgCrAmtDuringDemPdList = em.createNativeQuery("select ifnull(cast(avg(ifnull(a.cramt,0)) as decimal(25,2)),0) from (select (sum(cramt)) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + fromDt + "' and '" + toDt + "'  and trantype = 0 and ty = 0 group by dt )a").getResultList();
                                Vector avgCrAmtDuringDemPdVector = (Vector) avgCrAmtDuringDemPdList.get(0);
                                Double avgCrAmtDuringDemPdBalance = Double.parseDouble(avgCrAmtDuringDemPdVector.get(0).toString());
                                if (avgBalance > avgCrAmtDuringDemPdBalance) {
                                    strFlag = false;
                                } else {
                                    strFlag = true;
                                    strReason = "1. Cash deposits during specified period is" + multiplier + "X times higher than cash deposit in earlier period.\n " + opMode + " [" + acDeac + "]";
                                }
                            }
                        }
                        if (strFlag == false) {
                            List result4 = new ArrayList();
                            if ((opMode.equalsIgnoreCase("IND")) && crAmt.doubleValue() > 500000) {
                                result4 = em.createNativeQuery("select ifnull(cast(sum(dramt)as decimal(25,2)),0) from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + fromDt + "' and '" + ymd.format(ymd_1.parse(lastDt)) + "' and trantype<> 0 and ty = 1").getResultList();
                            } else if ((opMode.equalsIgnoreCase("CGD")
                                    || opMode.equalsIgnoreCase("CGP")
                                    || opMode.equalsIgnoreCase("COS")
                                    || opMode.equalsIgnoreCase("LB")
                                    || opMode.equalsIgnoreCase("MI")
                                    || opMode.equalsIgnoreCase("NGO")
                                    || opMode.equalsIgnoreCase("PCS")
                                    || opMode.equalsIgnoreCase("PC")
                                    || opMode.equalsIgnoreCase("SGD")
                                    || opMode.equalsIgnoreCase("SGP")
                                    || opMode.equalsIgnoreCase("SB")
                                    || opMode.equalsIgnoreCase("TG")
                                    || opMode.equalsIgnoreCase("FI")
                                    || opMode.equalsIgnoreCase("PL")
                                    || opMode.equalsIgnoreCase("AB")
                                    || opMode.equalsIgnoreCase("LC")
                                    || opMode.equalsIgnoreCase("RS")
                                    || opMode.equalsIgnoreCase("JLG"))
                                    && crAmt.doubleValue() > 1500000) {

                                result4 = em.createNativeQuery("select ifnull(cast(sum(dramt)as decimal(25,2)),0) from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' "
                                        + "and dt between '" + fromDt + "' and '" + ymd.format(ymd_1.parse(lastDt)) + "' and trantype<> 0 and ty = 1").getResultList();
                            }

                            if (result4.isEmpty()) {
                                strFlag = false;
                            } else {
                                Vector trnVector = (Vector) result4.get(0);
                                double transferAmt = Double.parseDouble(trnVector.get(0).toString());
                                if (transferAmt >= halfDepositAmt) {
                                    strFlag = true;
                                    strReason = "3. Cash deposit exceeding Rs. 5 lakh (for individul) and Rs. 15 lakhs (for legal entities)and the amount is immediately transferred to another account.\n" + opMode + " [" + acDeac + "]";
                                } else {
                                    strFlag = false;
                                }
                            }
                        }
                        if (strFlag == true) {
                            String reason = "";
                            if (!commonPanMobileEmailPhone.isEmpty()) {
                                for (int x = 0; x < commonPanMobileEmailPhone.size(); x++) {
                                    if (acNo.equalsIgnoreCase(commonPanMobileEmailPhone.get(x).getAcNo())) {
                                        reason = "[COMMON " + commonPanMobileEmailPhone.get(x).getCommonType()
                                                + " :" + commonPanMobileEmailPhone.get(x).getCommonNo()
                                                + " - A/c:" + commonPanMobileEmailPhone.get(x).getAcNo()
                                                + " - No. of Tran:" + commonPanMobileEmailPhone.get(x).getNoOfTrans()
                                                + " - Total Value of Tran:" + commonPanMobileEmailPhone.get(x).getValueOfTran() + "]";
                                        strReason = strReason + "\n5. More than 3 deposits each between 1 to 2.5 lakh on each occasion in various accounts of a family/group(common Pan,Mobile,email and telephone number only).\n" + opMode + " [" + acDeac + "]" + reason;
                                        break;
                                    }
                                }
                            }
                        }

                        if (strFlag == false) {
                            String reason = "";
                            if (!commonPanMobileEmailPhone.isEmpty()) {
                                for (int x = 0; x < commonPanMobileEmailPhone.size(); x++) {
                                    if (acNo.equalsIgnoreCase(commonPanMobileEmailPhone.get(x).getAcNo())) {
                                        reason = "[COMMON " + commonPanMobileEmailPhone.get(x).getCommonType()
                                                + " :" + commonPanMobileEmailPhone.get(x).getCommonNo()
                                                + " - A/c:" + commonPanMobileEmailPhone.get(x).getAcNo()
                                                + " - No. of Tran:" + commonPanMobileEmailPhone.get(x).getNoOfTrans()
                                                + " - Total Value of Tran:" + commonPanMobileEmailPhone.get(x).getValueOfTran() + "]";
                                        strFlag = true;
                                        strReason = "5. More than 3 deposits each between 1 to 2.5 lakh on each occasion in various accounts of a family/group(common Pan,Mobile,email and telephone number only).\n" + opMode + " [" + acDeac + "]" + reason;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * *************************************
                     * START ARFTRN * *************************************
                     */
                    if (repType.equalsIgnoreCase("STR DEM") && strFlag == true || repType.equalsIgnoreCase("STR")) {

                        List trnList = new ArrayList();
                        //trnList = em.createNativeQuery("select acno,dt,cast(sum(cramt) as decimal(25,2)) depositAmount,cast(sum(dramt) as decimal(25,2)) withdAmount,trantype,ty,recno from " + CbsUtil.getReconTableName(acNature) + " where  dt between '" + fromDt + "' and '" + toDt + "' and acno = '" + acNo + "' and trantype=0 and ty = 0 group by dt").getResultList();
                        trnList = em.createNativeQuery("select a.acno,a.dt,cast(sum(a.cramt) as decimal(25,2)) depositAmount,cast(sum(a.dramt) as decimal(25,2)) withdAmount,trantype,ty,a.recno \n"
                                + "from " + CbsUtil.getReconTableName(acNature) + " a,cbs_str_detail b where a.acno = '" + acNo + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and trantype=0 and ty = 0 \n"
                                + "and a.recno =  b.recno and a.dt=b.dt and flag = 'STR' group by dt").getResultList();
                        if (trnList.isEmpty()) {
                            trnList = em.createNativeQuery("select acno,dt,cast(sum(cramt) as decimal(25,2)) depositAmount,cast(sum(dramt) as decimal(25,2)) withdAmount,trantype,ty,recno from " + CbsUtil.getReconTableName(acNature) + " where  dt between '" + fromDt + "' and '" + toDt + "' and acno = '" + acNo + "' and trantype <> 0 and ty = 0 group by dt").getResultList();
                        }
                        for (int z = 0; z < trnList.size(); z++) {
                            ArfTrnPojo arfTrn = new ArfTrnPojo();
                            Vector trnVector = (Vector) trnList.get(z);
                            acNo = trnVector.get(0).toString();
                            dt = trnVector.get(1).toString();
                            crAmt = new BigDecimal(trnVector.get(2).toString());
                            drAmt = new BigDecimal(trnVector.get(3).toString());
                            tranType = trnVector.get(4).toString();
                            ty = trnVector.get(5).toString();
                            recNo = Float.parseFloat(trnVector.get(6).toString());

                            arfTrn.setReportSerialNum(toDt);
                            List<ArfBrcPojo> arfBrcList = getArfBrcData(acNo.substring(0, 2), "");
                            arfTrn.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
                            // arfTrn.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                            arfTrn.setAccountNumber(acNo);
                            arfTrn.setDateOfTransaction(ymd_1.format(ymd_1.parse(dt)));
                            arfTrn.setTransactionId(ymd.format(ymd_1.parse(dt)).concat(String.valueOf((int) recNo)));

                            /*  TransactionMode
                             A – Cheque (Inter bank transfer)
                             B – Internal Transfer (Transfer within the Institution)
                             C – Cash 
                             D – Demand Draft/Pay Order (Demand Draft, Pay Order)
                             E – Electronic Fund Transfer (Swift, cross border payment platforms, TT, RTGS, NEFT)
                             F – Exchange Based Transaction (Any exchange based transaction)
                             G – Securities transaction (Any Securities based transaction)
                             S – Switching Transaction (Switching transaction (switching of products) like switching of mutual funds)
                             Z – Others (Not listed above)
                             X – Not Categorised.
                             */
                            arfTrn.setTransactionMode("C");

                            /*  DebitCredit
                             D – Debit (Debit transaction in the account(Banks - Withdrawal by customer))
                             C – Credit (Credit transaction in the accoun(Banks - Deposit by customer))
                             X – Not Categorised. 
                             */
                            if (ty.equalsIgnoreCase("0")) {
                                arfTrn.setDebitCredit("C");
                                arfTrn.setAmount(new BigDecimal(new DecimalFormat("#").format(crAmt.doubleValue())));    //The amount should be rounded off to nearest rupee without decimal. If this amount is not in Indian Rupees,then convert to Indian Rupees.
                            } else {
                                arfTrn.setDebitCredit("D");
                                arfTrn.setAmount(new BigDecimal(new DecimalFormat("#").format(drAmt.doubleValue())));
                            }
                            arfTrn.setCurrency("INR");      //INR for Indian Rupees (Mention currency code as per ISO 4127. Refer Annexure G for Currency codes.)

                            /*  Product Type
                             BD – Bonds 
                             ST – Securities 
                             CD – Certificate of Deposit 
                             CP – Commercial Paper 
                             EQ – Equity Shares 
                             FU – Futures 
                             OP – Options 
                             DF – Debt Funds (Mutual Funds)
                             EF – Equity Fund (Mutual Funds)
                             HF – Hybrid Funds (Mutual Funds)
                             LF – Liquid Funds (Mutual Funds)
                             MF – MIP Funds (Mutual Funds)
                             XF – Exchange Traded Funds (Mutual Funds)
                             CO – Commodities 
                             IP – Insurance Products 
                             ZZ – Others (Not listed above)
                             XX – Not Categorised.
                             */
                            arfTrn.setProductType("XX");
                            arfTrn.setIdentifier("");

                            /*  Transaction Type
                             BP – Buy/Purchase       (Credit)
                             SR – Sale/Redemption    (Debit)
                             IA – Annuity payment (Insurance Companies)
                             IP – Pension  (Insurance Companies)
                             IC – Commutation  (Insurance Companies)
                             ID – Death  claim  (Insurance Companies)
                             IM – Maturity  (Insurance Companies)
                             IB – Survival  benefits (Insurance Companies (including money back))
                             IF – Free  look cancellation (Insurance Companies)
                             IW – Withdrawal  (Insurance Companies (including partial withdrawal))
                             IS – Surrender (Insurance Companies)
                             IG – Assignment (Insurance Companies)
                             IE – Decline (Insurance Companies)
                             IX – Excess Refund (Insurance Companies)
                             IR – Premium Payment (Insurance Companies)
                             IL – Loan Repayment (Insurance Companies)
                             DD – Dematerialisation/Conversion of Mutual fund units in demat form (Depositories)
                             DR – Rematerialisation/Repurchase  (Depositories)
                             DO – Off Market trade  (Depositories)
                             DM – Market transfers  (Depositories)
                             DI – Inter Settlement transfers  (Depositories)
                             DP – Pledge and Hypothecation  (Depositories)
                             DC – Corporate action  (Depositories)
                             ZZ – Others 
                             XX – Not Categorised.
                             */
                            arfTrn.setTransactionType("ZZ");
                            arfTrn.setUnits(new BigDecimal("0"));
                            arfTrn.setRate(new BigDecimal("0"));
                            arfTrn.setDispositionOfFunds("X"); //Reserved for later use. Use value X.
                            arfTrn.setRelatedAccountNum("");
                            arfTrn.setRelatedInstitutionName("");
                            arfTrn.setRelatedInstitutionRefNum("");
                            arfTrn.setRemarks("");
                            if (ty.equalsIgnoreCase("0")) {
//                                if (crAmt.doubleValue() >= ctrLimit) {
                                trnLineNo = trnLineNo + 1;
                                arfTrn.setLineNumber(trnLineNo);
                                arfTrnPojos.add(arfTrn);
//                                }
                            } else {
                                if (drAmt.doubleValue() >= ctrLimit) {
                                    trnLineNo = trnLineNo + 1;
                                    arfTrn.setLineNumber(trnLineNo);
                                    arfTrnPojos.add(arfTrn);
                                }
                            }

                            //}

                            /**
                             * ********** END OF ARFTRN *********
                             */
                            /**
                             * *************************************
                             * START ARFACC/ARFLPE *
                             * *************************************
                             */
                            if (!preAcNo.equalsIgnoreCase(acNo)) {
                                arfAccLine = arfAccLine + 1;
                                ArfAccPojo arfAcc = new ArfAccPojo();
                                ArfLpePojo arfLpe = new ArfLpePojo();
                                /**
                                 * ** Customer Information ***
                                 */
                                List acNoDetails = em.createNativeQuery("select concat(ifnull(c.custname,''), if(ifnull(c.middle_name,'')= '', ifnull(c.middle_name,''), concat(' ', ifnull(c.middle_name,''))), if(ifnull(c.last_name,'')= '', ifnull(c.last_name,''), concat(' ', ifnull(c.last_name,'')))) as custName, "
                                        + " a.openingdt, a.opermode, ifnull(c.OperationalRiskRating,''), b.custid, IFNULL(c.PAN_GIRNumber,''), "
                                        + " IFNULL(c.PerAddressLine1,''), IFNULL(c.PerAddressLine2,''), IFNULL(c.PerVillage,''), IFNULL(c.PerBlock,''), IFNULL(c.PerCityCode,'0'), IFNULL(c.PerStateCode,''),"
                                        + " IFNULL(c.PerPostalCode,''), IFNULL(c.PerCountryCode,''), IFNULL(c.PerPhoneNumber,''), IFNULL(c.PerTelexNumber,''), IFNULL(c.PerFaxNumber,''), "
                                        + " IFNULL(c.MailAddressLine1,''), IFNULL(c.MailAddressLine2,''), IFNULL(c.MailVillage,''), IFNULL(c.MailBlock, ''), IFNULL(c.MailCityCode, '0'),"
                                        + " IFNULL(c.MailStateCode, ''), IFNULL(c.MailPostalCode, ''), IFNULL(c.MailCountryCode, ''), IFNULL(c.MailPhoneNumber, ''), IFNULL(c.MailTelexNumber, ''),"
                                        + " IFNULL(c.MailFaxNumber, ''), IFNULL(c.mobilenumber,''), IFNULL(c.EmailID,''), a.accstatus, IFNULL(c.AADHAAR_NO, ''),a.acctCategory   from " + CbsUtil.getAccMasterTableName(acNature) + "  a, customerid b, cbs_customer_master_detail c "
                                        + " where a.acno = b.acno and b.custid = c.customerid and a.acno =  '" + acNo + "'").getResultList();
                                if (!acNoDetails.isEmpty()) {
                                    Vector acNoVect = (Vector) acNoDetails.get(0);
                                    String custName = acNoVect.get(0).toString().toUpperCase();
                                    String openingDt = acNoVect.get(1).toString();
                                    String operMode = acNoVect.get(2).toString();
                                    String riskCategory = acNoVect.get(3).toString();
                                    String custId = acNoVect.get(4).toString();
                                    String pan = acNoVect.get(5).toString().toUpperCase();
                                    String perAddressLine1 = acNoVect.get(6).toString();
                                    String perAddressLine2 = acNoVect.get(7).toString();
                                    String perVillage = acNoVect.get(8).toString();
                                    String perBlock = acNoVect.get(9).toString();
                                    String perCityCode = acNoVect.get(10).toString();
                                    String perStateCode = acNoVect.get(11).toString();
                                    String perPostalCode = acNoVect.get(12).toString();
                                    String perCountryCode = acNoVect.get(13).toString();
                                    String perPhoneNumber = acNoVect.get(14).toString();
                                    String perTelexNumber = acNoVect.get(15).toString();
                                    String perFaxNumber = acNoVect.get(16).toString();
                                    String mailAddressLine1 = acNoVect.get(17).toString();
                                    String mailAddressLine2 = acNoVect.get(18).toString();
                                    String mailVillage = acNoVect.get(19).toString();
                                    String mailBlock = acNoVect.get(20).toString();
                                    String mailCityCode = acNoVect.get(21).toString();
                                    String mailStateCode = acNoVect.get(22).toString();
                                    String mailPostalCode = acNoVect.get(23).toString();
                                    String mailCountryCode = acNoVect.get(24).toString();
                                    String mailPhoneNumber = acNoVect.get(25).toString();
                                    String mailTelexNumber = acNoVect.get(26).toString();
                                    String mailFaxNumber = acNoVect.get(27).toString();
                                    String mobileNo = acNoVect.get(28).toString();
                                    String eMailId = acNoVect.get(29).toString();
                                    String accStatus = acNoVect.get(30).toString();
                                    String uin = acNoVect.get(31).toString();
                                    String accCategory = acNoVect.get(32).toString();
                                    String acNature1 = common.getAcctNature(acNo.substring(2, 4));

                                    YearEndDatePojo yDate = new YearEndDatePojo();
                                    yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingToDate(acNo.substring(0, 2), toDt);
                                    String minFDate = yDate.getMinDate();
                                    String maxFDate = yDate.getMaxDate();
                                    String fyear = yDate.getfYear();
                                    /**
                                     * ** Cash Transaction Information ***
                                     */
                                    // List cumCashTranList1 = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt between '" + minFDate + "' and '" + toDt + "' and trantype=0").getResultList();
                                    List cumCashTranList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " a,cbs_str_detail b \n"
                                            + "where a.acno='" + acNo + "' and a.dt between '" + minFDate + "' and '" + toDt + "' and trantype=0 and a.recno =  b.recno and a.dt=b.dt and flag = 'STR'").getResultList();
                                    Vector cumCashTranVect = (Vector) cumCashTranList.get(0);
                                    BigDecimal cashCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumCashTranVect.get(0).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(0).toString());
                                    BigDecimal cashDrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumCashTranVect.get(1).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(1).toString());
                                    /**
                                     * ** All Transaction Information ***
                                     */
                                    // List cumAllTranList1 = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt between '" + minFDate + "' and '" + toDt + "'").getResultList();
                                    List cumAllTranList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)),cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) from " + CbsUtil.getReconTableName(acNature) + " a,cbs_str_detail b \n"
                                            + "where a.acno='" + acNo + "' and a.dt between '" + minFDate + "' and '" + toDt + "' and a.recno =  b.recno and a.dt=b.dt and flag = 'STR'").getResultList();
                                    Vector cumAllTranVect = (Vector) cumAllTranList.get(0);
                                    BigDecimal allCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumAllTranVect.get(0).toString()).doubleValue()));
                                    BigDecimal allDrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(cumAllTranVect.get(1).toString()).doubleValue()));

                                    /**
                                     * Start of ARFACC *
                                     */
                                    arfAcc.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf(arfAccLine), 6)));
                                    arfAcc.setReportSerialNum(toDt);

                                    arfAcc.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
                                    // arfAcc.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                                    arfAcc.setAccountNumber(acNo);
                                    arfAcc.setReason(strReason);

                                    /*  Account Type
                                     BS - Savings Account
                                     BC - Current Account
                                     BR - Cash Credit/Overdraft Account
                                     BD - Credit Card Account
                                     BP - Prepaid Card Account
                                     BL - Loan Account
                                     BT - Term Deposit Account
                                     BG – Letter of Credit/Bank Guarantee
                                     ZZ - Others
                                     XX - Not Categorised
                                     */
                                    if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                                        arfAcc.setAccountType("BC");
                                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        arfAcc.setAccountType("BR");
                                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                        arfAcc.setAccountType("BS");
                                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                        arfAcc.setAccountType("BL");
                                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                                        arfAcc.setAccountType("BT");
                                    } else {
                                        arfAcc.setAccountType("ZZ");
                                    }

                                    arfAcc.setHolderName(custName);

                                    /*  In Our System
                                     1	SELF
                                     2	EITHER OR SURVIVOR
                                     3	FORMER OR SURVIVOR
                                     4	ANY ONE OR SURVIVOR
                                     5	ANY TWO JOINTLY
                                     6	ANY THREE JOINTLY
                                     7	UNDER POWER OF ATTOR
                                     8	PROPRIETOR
                                     9	AUTHORIZED SIGNATORY
                                     10	M.D.
                                     11	UNDER GUARDIANSHIP
                                     12	BOTH OF TWO JOINTLY
                                     13	MINOR
                                     14	ANY FOUR JOINTLY
                                     15	ANY FIVE JOINTLY
                                     16	ALL JOINTLY
                                     17	JOINTLY
                                     * *****************************************
                                     *  In CTR report required

                                     A - Resident Individual
                                     B - Legal Person/Entity (excluding C,D,E and F)
                                     C - Central/State Government
                                     D - Central/State Government owned undertaking
                                     E – Reporting Entity (The account belongs to other bank, financial institution or intermediary)
                                     F- Non Profit Organisation
                                     G- Non-residential individual
                                     H - Overseas corporate body/FII
                                     Z – Others. (Not listed above)
                                     X - Not categorised
                                     */
                                    // if (operMode.equalsIgnoreCase("1")) {
                                    if (accCategory.equalsIgnoreCase("IND") || accCategory.equalsIgnoreCase("UN") || accCategory.equalsIgnoreCase("PC")) {

                                        arfAcc.setAccountHolderType("A");
                                    } else {
                                        arfAcc.setAccountHolderType("B");
                                    }

                                    /*  Account Status
                                     A – Active (Account is in regular use/policy inforce)
                                     I – Inactive (Account is not in regular use/ policy lapsed)
                                     D – Dormant (As defined by regulator (eg. There is no transaction in the account for two years)/ paid up policy lapsed after paying premiums for 3 or more years)
                                     S – Suspended (Account/policy risk is temporarily suspended)
                                     F – Frozen (Account/policy is frozen (including case of debit freeze))
                                     C – Closed (Account is closed/policy foreclosed, surrendered, death or maturity claim paid)
                                     Z – Others (Not listed above)
                                     X - Not categorised
                                     */
                                    if (accStatus.equalsIgnoreCase("1")) {
                                        arfAcc.setAccountStatus("A");
                                    } else if (accStatus.equalsIgnoreCase("2")) {
                                        List dormentList = em.createNativeQuery("select ifnull(max(dt),'') from " + CbsUtil.getReconTableName(acNature) + " where acno='" + acNo + "' and dt<='" + toDt + "'").getResultList();
                                        Vector dorVect = (Vector) dormentList.get(0);
                                        String dorDt = dorVect.get(0).toString();
                                        int yearDiff = CbsUtil.yearDiff(ymd_1.parse(dorDt), ymd_1.parse(ymd_1.format(ymd.parse(toDt))));
                                        if (yearDiff >= 2) {
                                            arfAcc.setAccountStatus("D");
                                        } else {
                                            List operativeList = em.createNativeQuery("Select c.Code from accountstatus a,codebook c where acno='" + acNo + "' "
                                                    + " and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + toDt + "' and acno='" + acNo + "' "
                                                    + " and spno=(Select max(Spno) from accountstatus where acno='" + acNo + "' and date_format(EffDt,'%Y%m%d')<='" + toDt + "')) "
                                                    + " AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3").getResultList();
                                            if (!operativeList.isEmpty()) {
                                                Vector operVect = (Vector) operativeList.get(0);
                                                String operStatus = operVect.get(0).toString();
                                                if (operStatus.equalsIgnoreCase("1")) {
                                                    arfAcc.setAccountStatus("A");
                                                } else {
                                                    arfAcc.setAccountStatus("I");
                                                }
                                            } else {
                                                arfAcc.setAccountStatus("A");
                                            }
                                        }
                                    } else if (accStatus.equalsIgnoreCase("7")) {
                                        arfAcc.setAccountStatus("S");
                                    } else if (accStatus.equalsIgnoreCase("4")) {
                                        arfAcc.setAccountStatus("F");
                                    } else if (accStatus.equalsIgnoreCase("9")) {
                                        arfAcc.setAccountStatus("C");
                                    } else {
                                        arfAcc.setAccountStatus("Z");
                                    }

                                    arfAcc.setDateOfOpening(ymd_1.format(ymd.parse(openingDt)));

                                    /*
                                     * IN OUR SYSTEM:
                                     *  024	C1	LOW RISK CATEGORY       03
                                     *  024	C2	MEDIUM RISK CATEGORY    02
                                     *  024	C3	HIGH RISK CATEGORY      01
                                     *  024	C4	VERY HIGH RISK CATEGORY
                                     * 
                                     * IN RBI SYSTEM:
                                     *  A1 - High Risk Account (Very High or High Risk)
                                     *  A2 - Medium Risk Account
                                     *  A3 - Low Risk Account
                                     *  XX - Not categorised                                                                                                    
                                     */
                                    if (riskCategory.equalsIgnoreCase("03")) {
                                        arfAcc.setRiskRating("A3");
                                    } else if (riskCategory.equalsIgnoreCase("02")) {
                                        arfAcc.setRiskRating("A2");
                                    } else if (riskCategory.equalsIgnoreCase("01")) {
                                        arfAcc.setRiskRating("A1");
                                    } else if (riskCategory.equalsIgnoreCase("C4")) {
                                        arfAcc.setRiskRating("A1");
                                    } else {
                                        arfAcc.setRiskRating("A1");
                                    }

                                    arfAcc.setCumulativeCreditTurnover(allCrAmt);
                                    arfAcc.setCumulativeDebitTurnover(allDrAmt);
                                    arfAcc.setCumulativeCashDepositTurnover(cashCrAmt);
                                    arfAcc.setCumulativeCashWithdrawalTurnover(cashDrAmt);
                                    arfAcc.setNoTransactionsTobeReported("Y");

                                    arfAccPojos.add(arfAcc);
                                    /**
                                     * End of CBAACC *
                                     */
                                    /**
                                     * Start of CBALPE *
                                     */
//                            arfLpe.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf(arfAccLine), 6)));
                                    arfLpe.setReportSerialNum(toDt);

                                    arfLpe.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
                                    //arfLpe.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                                    arfLpe.setAccountNumber(acNo);
                                    arfLpe.setPersonName(custName);
                                    arfLpe.setCustomerId(custId);

                                    /*  Realtion Flag
                                     *  A - Account Holder
                                     B - Authorised Signatory
                                     C - Proprietor/Director/Partner/Member of a legal entity
                                     D - Introducer
                                     E – Guarantor
                                     F - Guardian
                                     N – Nominee
                                     O – Beneficial Owner
                                     P – Proposer
                                     G - Assignee
                                     L - Life Assured
                                     J – Beneficiary
                                     H – Power of Attorney
                                     Z - Others
                                     X - Not Categorised.
                                     */
                                    //if (operMode.equalsIgnoreCase("1")) {
                                    if (accCategory.equalsIgnoreCase("IND") || accCategory.equalsIgnoreCase("UN") || accCategory.equalsIgnoreCase("PC")) {
                                        arfLpe.setRelationFlag("A");
                                    } else {
                                        arfLpe.setRelationFlag("B");
                                    }
                                    //                    arfLpe.setRelationFlag("A");
                                    arfLpe.setCommunicationAddress((mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).trim().length() > 10 ? (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)) : (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", mailCityCode)));    //No, building//Street, Road//Locality
                                    arfLpe.setCity(mailCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", mailCityCode));                                                              //City/Town, District
                                    arfLpe.setStateCode(mailStateCode.equalsIgnoreCase("0") ? "" : mailStateCode);
                                    arfLpe.setPinCode(mailPostalCode);
                                    arfLpe.setCountryCode(mailCountryCode.equalsIgnoreCase("0") ? "IN" : (mailCountryCode.equalsIgnoreCase("") ? "IN" : mailCountryCode.substring(0, 2)));

                                    arfLpe.setSecondAddress((perAddressLine1.concat(" ").concat(perAddressLine2).concat(" ").concat(perBlock)).trim().length() > 10 ? (perAddressLine1.concat(" ").concat(perAddressLine2).concat(" ").concat(perBlock)) : (perAddressLine1.concat(" ").concat(perAddressLine2).concat(" ").concat(perBlock)).concat(" ").concat(perCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", perCityCode)));    //No, building//Street, Road//Locality
                                    arfLpe.setSecondCity(perCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", perCityCode));
                                    arfLpe.setSecondStateCode(perStateCode.equalsIgnoreCase("0") ? "" : perStateCode);
                                    arfLpe.setSecondPinCode(perPostalCode);
                                    arfLpe.setSecondCountryCode(perCountryCode.equalsIgnoreCase("0") ? "IN" : (perCountryCode.equalsIgnoreCase("") ? "IN" : perCountryCode.substring(0, 2)));

                                    arfLpe.setTelephone(mailPhoneNumber.equalsIgnoreCase("") ? (perPhoneNumber.equalsIgnoreCase("") ? "" : (perPhoneNumber.length() <= 10 ? "0".concat(perPhoneNumber) : perPhoneNumber)) : (mailPhoneNumber.length() <= 10 ? "0".concat(mailPhoneNumber) : mailPhoneNumber));
                                    arfLpe.setMobile(mobileNo.equalsIgnoreCase("") ? "" : (mobileNo.length() == 10 ? mobileNo : ""));
                                    arfLpe.setFax(mailFaxNumber.equalsIgnoreCase("") ? (perFaxNumber.equalsIgnoreCase("") ? "" : (perFaxNumber.length() <= 10 ? "0".concat(perFaxNumber) : perFaxNumber)) : (mailFaxNumber.length() <= 10 ? "0".concat(mailFaxNumber) : mailFaxNumber));
                                    arfLpe.setEmail(eMailId);
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
                                    } else {
                                        pan = "";
                                    }
                                    arfLpe.setPan(pan);
                                    arfLpe.setUin(uin);
                                    /*  Constitution Type
                                     A – Sole  Proprietorship
                                     B – Partnership  Firm
                                     C – HUF (Hindu Undivided family)
                                     D – Private  Limited Company
                                     E – Public  Limited Company
                                     F – Society 
                                     G – Association 
                                     H – Trust 
                                     I – Liquidator 
                                     J – LLP (Limited Liability Partnership)
                                     Z – Others (Not listed above)
                                     X – Not Categorised.
                                     */
                                    arfLpe.setConstitutionType("Z");
                                    arfLpe.setRegistrationNumber("");
                                    arfLpe.setDateOfIncorporation("");
                                    arfLpe.setPlaceOfRegistration("");
                                    arfLpe.setRegisteredCountryCode("IN");
                                    arfLpe.setNatureOfBusiness("");
//                                    if (operMode.equalsIgnoreCase("7") || operMode.equalsIgnoreCase("8") || operMode.equalsIgnoreCase("9")
//                                            || operMode.equalsIgnoreCase("10") || operMode.equalsIgnoreCase("11")) {
                                    // if (!operMode.equalsIgnoreCase("1")) {
                                    if (!(accCategory.equalsIgnoreCase("IND") || accCategory.equalsIgnoreCase("UN") || accCategory.equalsIgnoreCase("PC"))) {
                                        lpeLineNo = lpeLineNo + 1;
                                        arfLpe.setLineNumber(lpeLineNo);
                                        arfLpePojos.add(arfLpe);
                                    }
                                    /**
                                     * End of ARFLPE *
                                     */
                                    /**
                                     * Start of ARFINP *
                                     */
                                    List jointList;
                                    // if (operMode.equalsIgnoreCase("1")) {
                                    if (accCategory.equalsIgnoreCase("IND") || accCategory.equalsIgnoreCase("UN") || accCategory.equalsIgnoreCase("PC")) {
                                        jointList = em.createNativeQuery("select ifnull(cast(custid as char),'') from customerid  where acno = '" + acNo + "'").getResultList();
                                    } else {
                                        jointList = em.createNativeQuery("select ifnull(custid1,'') from accountmaster where acno='" + acNo + "'"
                                                + " union all select ifnull(custid2,'') from accountmaster where acno='" + acNo + "'"
                                                + " union all select ifnull(custid3,'') from accountmaster where acno='" + acNo + "'"
                                                + " union all select ifnull(custid4,'') from accountmaster where acno='" + acNo + "'").getResultList();
                                    }

                                    for (int k = 0; k < jointList.size(); k++) {
                                        Vector jointVectList = (Vector) jointList.get(k);
                                        String jointCustId = jointVectList.get(0).toString();
                                        if (!jointCustId.equalsIgnoreCase("")) {
                                            inpLineNo = inpLineNo + 1;
                                            ArfInpPojo arfInp = new ArfInpPojo();
                                            List jointDetails = em.createNativeQuery("select ifnull(c.custname,''),ifnull(c.gender,'X'),"
                                                    + " concat(ifnull(c.fathername,''),if(ifnull(c.FatherMiddleName,'')= '',ifnull(c.FatherMiddleName,''),concat(' ', ifnull(c.FatherMiddleName,''))),"
                                                    + " if(ifnull(c.FatherLastName,'')= '', ifnull(c.FatherLastName,''),concat(' ', ifnull(c.FatherLastName,'')))) as FatherName, ifnull(c.DateOfBirth,'1900-04-01'),IFNULL(c.PAN_GIRNumber,''), "
                                                    + " IFNULL(c.PerAddressLine1,''), IFNULL(c.PerAddressLine2,''), IFNULL(c.PerVillage,''), IFNULL(c.PerBlock,''), IFNULL(c.PerCityCode,'0'), IFNULL(c.PerStateCode,''),"
                                                    + " IFNULL(c.PerPostalCode,''), IFNULL(c.PerCountryCode,''), IFNULL(c.PerPhoneNumber,''), IFNULL(c.PerTelexNumber,''), IFNULL(c.PerFaxNumber,''), "
                                                    + " IFNULL(c.MailAddressLine1,''), IFNULL(c.MailAddressLine2,''), IFNULL(c.MailVillage,''), IFNULL(c.MailBlock, ''), IFNULL(c.MailCityCode, '0'),"
                                                    + " IFNULL(c.MailStateCode, ''), IFNULL(c.MailPostalCode, ''), IFNULL(c.MailCountryCode, ''), IFNULL(c.MailPhoneNumber, ''), IFNULL(c.MailTelexNumber, ''),"
                                                    + " IFNULL(c.MailFaxNumber, ''), IFNULL(c.mobilenumber,''), IFNULL(c.EmailID,''), IFNULL(c.PassportNo, ''), IFNULL(c.IssueDate, ''), IFNULL(c.issuingAuthority, ''), "
                                                    + " IFNULL(c.Expirydate, ''), IFNULL(c.PlaceOfIssue, ''), IFNULL(c.VoterIDNo, ''), IFNULL(c.DrivingLicenseNo, ''), IFNULL(c.AADHAAR_NO, ''),  "
                                                    + " ifnull(middle_name,''), ifnull(last_name,'') from cbs_customer_master_detail c where c.customerid = '" + jointCustId + "'").getResultList();
                                            Vector jointVect = (Vector) jointDetails.get(0);
                                            String jointCustName = jointVect.get(0).toString().toUpperCase();
                                            String jointGender = jointVect.get(1).toString();
                                            String jointFatherName = jointVect.get(2).toString().toUpperCase();
                                            String jointDob = jointVect.get(3).toString();
                                            String jointPan = jointVect.get(4).toString().toUpperCase();
                                            String jointPerAddressLine1 = jointVect.get(5).toString();
                                            String jointPerAddressLine2 = jointVect.get(6).toString();
                                            String jointPerVillage = jointVect.get(7).toString();
                                            String jointPerBlock = jointVect.get(8).toString();
                                            String jointPerCityCode = jointVect.get(9).toString();
                                            String jointPerStateCode = jointVect.get(10).toString();
                                            String jointPerPostalCode = jointVect.get(11).toString();
                                            String jointPerCountryCode = jointVect.get(12).toString();
                                            String jointPerPhoneNumber = jointVect.get(13).toString();
                                            String jointPerTelexNumber = jointVect.get(14).toString();
                                            String jointPerFaxNumber = jointVect.get(15).toString();
                                            String jointMailAddressLine1 = jointVect.get(16).toString();
                                            String jointMailAddressLine2 = jointVect.get(17).toString();
                                            String jointMailVillage = jointVect.get(18).toString();
                                            String jointMailBlock = jointVect.get(19).toString();
                                            String jointMailCityCode = jointVect.get(20).toString();
                                            String jointMailStateCode = jointVect.get(21).toString();
                                            String jointMailPostalCode = jointVect.get(22).toString();
                                            String jointMailCountryCode = jointVect.get(23).toString();
                                            String jointMailPhoneNumber = jointVect.get(24).toString();
                                            String jointMailTelexNumber = jointVect.get(25).toString();
                                            String jointMailFaxNumber = jointVect.get(26).toString();
                                            String jointMobileNo = jointVect.get(27).toString();
                                            String jointEMailId = jointVect.get(28).toString();
                                            String passportNo = jointVect.get(29).toString();
                                            String issueDate = jointVect.get(30).toString();
                                            String issuingAuthority = jointVect.get(31).toString();
                                            String placeOfIssue = jointVect.get(33).toString();
                                            String voterIdNo = jointVect.get(34).toString();
                                            String drivingLicenseNo = jointVect.get(35).toString();
                                            String uinNo = jointVect.get(36).toString();
                                            String jointMidName = jointVect.get(37).toString();
                                            String jointLastName = jointVect.get(38).toString();

                                            arfInp.setLineNumber(Integer.parseInt(ParseFileUtil.addTrailingZeros(String.valueOf(inpLineNo), 6)));
                                            arfInp.setReportSerialNum(toDt);
                                            arfInp.setBranchRefNum(arfBrcList.get(0).getBranchRefNum());
                                            // arfInp.setBranchRefNum(common.getBrnRefNoByBrncode(acNo.substring(0, 2)));
                                            arfInp.setAccountNumber(acNo);
                                            if (!jointMidName.equalsIgnoreCase("")) {
                                                jointCustName = jointCustName.concat(" ").concat(jointMidName);
                                            }
                                            if (!jointLastName.equalsIgnoreCase("")) {
                                                jointCustName = jointCustName.concat(" ").concat(jointLastName);
                                            }
                                            arfInp.setPersonName(jointCustName);
                                            arfInp.setCustomerId(jointCustId);

                                            /*  Realtion Flag
                                             A - Account Holder
                                             B - Authorised Signatory
                                             C - Proprietor/Director/Partner/Member of a legal entity
                                             D - Introducer
                                             E – Guarantor
                                             F - Guardian
                                             N – Nominee
                                             O – Beneficial Owner
                                             P – Proposer
                                             G - Assignee
                                             L - Life Assured
                                             J – Beneficiary
                                             H – Power of Attorney
                                             Z - Others
                                             X - Not Categorised.
                                             */
//                                    if (operMode.equalsIgnoreCase("1")) {
//                                        arfInp.setRelationFlag("A");
//                                    } else {
//                                        arfInp.setRelationFlag("B");
//                                    }                                
                                            arfInp.setRelationFlag("A");
                                            arfInp.setCommunicationAddress((jointMailAddressLine1.concat(" ").concat(jointMailAddressLine2).concat(" ").concat(jointMailBlock)).trim().length() > 10 ? (jointMailAddressLine1.concat(" ").concat(jointMailAddressLine2).concat(" ").concat(jointMailBlock)) : (jointMailAddressLine1.concat(" ").concat(jointMailAddressLine2).concat(" ").concat(jointMailBlock)).concat(" ").concat(jointMailCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", jointMailCityCode)));    //No, building//Street, Road//Locality
                                            arfInp.setCity(jointMailCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", jointMailCityCode));                             //City/Town, District
                                            arfInp.setStateCode(jointMailStateCode.equalsIgnoreCase("0") ? "" : jointMailStateCode);
                                            arfInp.setPinCode(jointMailPostalCode);
                                            arfInp.setCountryCode(jointMailCountryCode.equalsIgnoreCase("0") ? "IN" : (jointMailCountryCode.equalsIgnoreCase("") ? "IN" : jointMailCountryCode.substring(0, 2)));

                                            arfInp.setSecondAddress((jointPerAddressLine1.concat(" ").concat(jointPerAddressLine2).concat(" ").concat(jointPerBlock)).trim().length() > 10 ? (jointPerAddressLine1.concat(" ").concat(jointPerAddressLine2).concat(" ").concat(jointPerBlock)) : (jointPerAddressLine1.concat(" ").concat(jointPerAddressLine2).concat(" ").concat(jointPerBlock)).concat(" ").concat(jointPerCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", jointPerCityCode)));
                                            arfInp.setSecondCity(jointPerCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", jointPerCityCode));
                                            arfInp.setSecondStateCode(jointPerStateCode.equalsIgnoreCase("0") ? "" : jointPerStateCode);
                                            arfInp.setSecondPinCode(jointPerPostalCode);
                                            arfInp.setSecondCountryCode(jointPerCountryCode.equalsIgnoreCase("0") ? "IN" : (jointPerCountryCode.equalsIgnoreCase("") ? "IN" : jointPerCountryCode.substring(0, 2)));

                                            arfInp.setTelephone(jointMailPhoneNumber.equalsIgnoreCase("") ? (jointPerPhoneNumber.equalsIgnoreCase("") ? "" : (jointPerPhoneNumber.length() <= 10 ? "0".concat(jointPerPhoneNumber) : jointPerPhoneNumber)) : (jointMailPhoneNumber.length() <= 10 ? "0".concat(jointMailPhoneNumber) : jointMailPhoneNumber));
                                            arfInp.setMobile(jointMobileNo.equalsIgnoreCase("") ? "" : (jointMobileNo.length() == 10 ? jointMobileNo : ""));
                                            arfInp.setFax(jointMailFaxNumber.equalsIgnoreCase("") ? (jointPerFaxNumber.equalsIgnoreCase("") ? "" : (jointPerFaxNumber.length() <= 10 ? "0".concat(jointPerFaxNumber) : jointPerFaxNumber)) : (jointMailFaxNumber.length() <= 10 ? "0".concat(jointMailFaxNumber) : jointMailFaxNumber));
                                            arfInp.setEmail(jointEMailId);
                                            String jointPanFlag = "Y";
                                            if (jointPan.length() == 10) {
                                                if (!ParseFileUtil.isAlphabet(jointPan.substring(0, 5))) {
                                                    jointPanFlag = "N";
                                                }
                                                if (!ParseFileUtil.isNumeric(jointPan.substring(5, 9))) {
                                                    jointPanFlag = "N";
                                                }
                                                if (!ParseFileUtil.isAlphabet(jointPan.substring(9))) {
                                                    jointPanFlag = "N";
                                                }
                                                if (!jointPanFlag.equalsIgnoreCase("Y")) {
                                                    jointPan = "";
                                                }
                                            } else {
                                                jointPan = "";
                                            }
                                            arfInp.setPan(jointPan);
                                            arfInp.setUin(uinNo);

                                            /*  Gender
                                             M- Male
                                             F- Female
                                             X- Not Categorised. 

                                             * IN OUR SYSTEM
                                             0- Select
                                             M- Male
                                             F- Female
                                             C- Company
                                             O- Others
                                             */
                                            if (jointGender.equalsIgnoreCase("M")) {
                                                jointGender = "M";
                                            } else if (jointGender.equalsIgnoreCase("F")) {
                                                jointGender = "F";
                                            } else {
                                                jointGender = "X";
                                            }
                                            arfInp.setGender(jointGender);
                                            arfInp.setDateOfBirth(dmy.format(ymd_1.parse(jointDob)).equalsIgnoreCase("01041900") ? "" : (dmy.format(ymd_1.parse(jointDob)).equalsIgnoreCase("01011900") ? "" : ymd_1.format(ymd_1.parse(jointDob))));

                                            /*  Identification type
                                             A - Passport
                                             B - Election ID Card
                                             C - Pan Card
                                             D - ID Card
                                             E - Driving License
                                             F - Account Introducer
                                             G - UIDAI Letter
                                             H - NREGA job card
                                             Z – Others
                                             */
                                            if (!passportNo.equalsIgnoreCase("")) {
                                                arfInp.setIdentificationType("A");
                                                arfInp.setIdentificationNumber(passportNo);
                                                arfInp.setIssuingAuthority(issuingAuthority);
                                                arfInp.setPlaceOfIssue(placeOfIssue);
                                            } else if (!voterIdNo.equalsIgnoreCase("")) {
                                                arfInp.setIdentificationType("B");
                                                arfInp.setIdentificationNumber(voterIdNo);
                                                arfInp.setIssuingAuthority("");
                                                arfInp.setPlaceOfIssue("");
                                            } else if (!drivingLicenseNo.equalsIgnoreCase("")) {
                                                arfInp.setIdentificationType("E");
                                                arfInp.setIdentificationNumber(drivingLicenseNo);
                                                arfInp.setIssuingAuthority("");
                                                arfInp.setPlaceOfIssue("");
                                            } else {
                                                arfInp.setIdentificationType("Z");
                                                arfInp.setIdentificationNumber("");
                                                arfInp.setIssuingAuthority("");
                                                arfInp.setPlaceOfIssue("");
                                            }

                                            arfInp.setNationality("IN");
                                            arfInp.setPlaceOfWork("");
                                            arfInp.setFatherOrSpouse(jointFatherName);

                                            /* In OUR SYSTEM
                                             0	TYPE OF ORGANIZATION
                                             1	FARMER
                                             2	TRADER
                                             3	SMALL BUSINESSMAN
                                             4	PROFESSIONAL
                                             5	SELF EMPLOYED
                                             6	RETIRED
                                             7	HOUSEWIFE
                                             8	STUDENT
                                             9	OTHERS
                                             10	SERVICE
                                             11	INDUSTRIALIST
                                             12	INDIVIDUAL
                                             13	SHOP KEEPER
                                             14	FORUM
                                             15	INSTITUTIONS
                                             16	STAFF
                                             17	PENSION
                                             18	CENTRAL GOVERNMENT
                                             19	STATE GOVERNMENT
                                             20	BANK
                                             21	PSU
                                             22	DOCTOR
                                             */
                                            arfInp.setOccupation("");

                                            arfInpPojos.add(arfInp);
                                        }
                                    }
                                    /**
                                     * End of CBAINP *
                                     */
                                }
                                preAcNo = acNo;
                            }
                            /**
                             * ********** END OF CBAACC/CBALPE *********
                             */
                        }
                    }
                }
                arf.setArfAcc(arfAccPojos);
                arf.setArfInp(arfInpPojos);
                arf.setArfLpe(arfLpePojos);
                arf.setArfTrn(arfTrnPojos);
            }

            // ARFRPT file
            List<ArfRptPojo> arfRptList = getArfRptData(brCode, repType, userName, fromDt, toDt, "STR");
            arf.setArfRpt(arfRptList);
            //ARFBRC file
            List<ArfBrcPojo> arfBrcList = getArfBrcData(brCode, repType);
            arf.setArfBrc(arfBrcList);

            if (brCode.length() > 4) {
                brCode = "90";
            } else {
                brCode = brCode;
            }
            //ARFBAT file
            List<ArfBatPojo> arfBatList = getArfBatData(brCode, repType, userName, fromDt, toDt);
            arf.setArfBat(arfBatList);

            return arf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
    /*
     * AIR according to following document
     * Document Name : Fwd: SFT SB And CA Report
     */

    public List<Form61APojo> form61AForSBAndCAReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException {
        List<Form61APojo> airAcc = new ArrayList<Form61APojo>();
        try {

            String mainQuery = "", acNatureQuery = "", custIdPre = "", custIdWise = "", govtFlag = "";
            int reportSerialNumber = 0;
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from ca_recon where acno = d.ACNo and dt between '20161231' and '" + toDt + "' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
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
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and a.ty = 0 and a.trantype = 0 "
                        + " group by b.CustId having(sum(a.cramt) between " + fromAmt + " and " + toAmt + ") ) "
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " order by e.CustId, d.acno ";
            } else if (acNature.equalsIgnoreCase("SFT004")) {
                acNatureQuery = "select acctcode from accounttypemaster where acctnature in ('SB') ";
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
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and trantype=0 ) as AggCrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and trantype=0 ) as AggDrCashAmtDurPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20160401' and '20161108' and ty = 0 and trantype=0 ) as CrAmtBeforeDemoFinPd, "
                        + " (select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) from recon where acno = d.ACNo and dt between '20161231' and '" + toDt + "' and ty = 0 and trantype=0 ) as CrCashAmtDurDemiPd, "
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
                        + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),  "
                        + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),  "
                        + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address, "
                        + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
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
                        + " and e.CustId in "
                        + " (select b.CustId from recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
                        + " /*and substring(a.acno,1,2) in (" + brCode + ")*/ "
                        + " and substring(a.acno,3,2) in (" + acNatureQuery + ") "
                        + " and a.ty = 0 and a.trantype = 0 "
                        + " group by b.CustId having(sum(a.cramt) between " + fromAmt + " and " + toAmt + ") ) "
                        + " and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') "
                        + " group by e.CustId, d.acno "
                        + " order by e.CustId, d.acno ";
            }

            //            System.out.println("Query>>>>>:" + mainQuery);
            List custIdWiseList = em.createNativeQuery(mainQuery).getResultList();
            if (!custIdWiseList.isEmpty()) {
                for (int i = 0; i < custIdWiseList.size(); i++) {
                    Form61APojo airPojo = new Form61APojo();
                    Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                    String custId = custIdWiseVector.get(0).toString();

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
                    BigDecimal aggGrossAmountCrCash = new BigDecimal(custIdWiseVector.get(16).toString());
                    BigDecimal aggGrossAmountDrCash = new BigDecimal(custIdWiseVector.get(17).toString());
                    BigDecimal amountCrBeforeDemonetization = new BigDecimal(custIdWiseVector.get(18).toString());
                    BigDecimal amountCrAfterDemonetization = new BigDecimal(custIdWiseVector.get(19).toString());
                    String accountRemarks = "";
                    /*  In Our System
                     1	SELF                    F
                     2	EITHER OR SURVIVOR      F
                     3	FORMER OR SURVIVOR      F
                     4	ANY ONE OR SURVIVOR     F
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
                     18      KARTA OF HUF            Z
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
                    if (acNature.equalsIgnoreCase("SB")) {
                        accountRelationship = "F";
                    } else if (acNature.equalsIgnoreCase("CA")) {
                        accountRelationship = "A";
                    } else if (accountRelationship.equalsIgnoreCase("1") || accountRelationship.equalsIgnoreCase("2")
                            || accountRelationship.equalsIgnoreCase("3") || accountRelationship.equalsIgnoreCase("4")) {
                        accountRelationship = "F";
                    } else if (accountRelationship.equalsIgnoreCase("5")) {
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
                     HUF                 HUF	HUF                      HF	 HUF
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
                     PF                  PROPRIETARY / PARTNERSHIP FIRM	PF	 Partnership Firm

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
                    if (aggGrossAmountCrCash.compareTo(new BigDecimal("0")) != 0) {
//                        airAcc.add(airPojo);
                        int jointCount = 0;
                        if (!custIdWiseVector.get(47).toString().equalsIgnoreCase("")) {
                            jointCount = jointCount + 1;
                        }
//                        if (!custIdWiseVector.get(48).toString().equalsIgnoreCase("")) {
//                            jointCount = jointCount + 1;
//                        }
//                        if (!custIdWiseVector.get(49).toString().equalsIgnoreCase("")) {
//                            jointCount = jointCount + 1;
//                        }
//                        if (!custIdWiseVector.get(50).toString().equalsIgnoreCase("")) {
//                            jointCount = jointCount + 1;
//                        }
                        List jointList;
                        if (jointCount > 0) {
                            jointList = em.createNativeQuery("select  custid  from (select ifnull(custid1,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    //                                    + " union all select ifnull(custid2,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    //                                    + " union all select ifnull(custid3,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    //                                    + " union all select ifnull(custid4,'') as custid  from accountmaster where acno='" + acNo + "'"
                                    + ") a where  custid <>''").getResultList();
                            if (!jointList.isEmpty()) {
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
                                                + "concat(ifnull(f.fathername,''),"
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
                                                + " if(ifnull(f.PerCityCode,'')='', '', concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),   "
                                                + " if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' and REF_CODE = f.PerStateCode),''))),   "
                                                + " if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address,  "
                                                + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, "
                                                + " if(ifnull(f.PerPostalCode,'')='','XXXXXX',ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode,  "
                                                + " if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX', ifnull(f.PerStateCode,'XX'))) as StateCode,  "
                                                + " 'IN' as countoryCode,  ifnull(f.PerPhoneNumber,'') as phnoneNo,  ifnull(f.mobilenumber,'') as MobileNo,  ifnull(f.PerFaxNumber,'') as faxNo, "
                                                + " ifnull(f.EmailID,'') as email  "
                                                + " from    cbs_customer_master_detail f,   cbs_cust_misinfo g   "
                                                + " where  f.customerid = g.CustomerId  "
                                                + " and f.customerid in  (" + jointCustId + ")  group by f.customerid";

                                        List jointListDetails = em.createNativeQuery(Query).getResultList();
                                        if (!jointListDetails.isEmpty()) {
//                                            airPojo = new Form61APojo();
                                            custIdWiseVector = (Vector) jointListDetails.get(0);
//                                            airPojo.setReportSerialNumber(reportSerialNumber);
//                                            airPojo.setOriginalReportSerialNumber(originalReportSerialNumber);
//                                            airPojo.setAccountType(accountType);
//                                            airPojo.setAccountNumber(acNo);
//                                            airPojo.setAccountHolderName(accountHolderName);
//                                            airPojo.setAccountStatus(accountStatus);
//                                            airPojo.setBranchReferenceNumber(branchReferenceNumber);
//                                            airPojo.setBranchName(branchName);
//                                            airPojo.setBranchAddress(branchAddress);
//                                            airPojo.setBranchCityTown(branchCityTown);
//                                            airPojo.setBranchPostalCode(branchPostalCode);
//                                            airPojo.setBranchState(branchState);
//                                            airPojo.setBranchCountry(branchCountry);
//                                            airPojo.setBranchSTDCode(branchSTDCode);
//                                            airPojo.setBranchPhoneNumber(branchPhoneNumber);
//                                            airPojo.setBranchMobileNumber(branchMobileNumber);
//                                            airPojo.setBranchbFaxSTDCode(branchbFaxSTDCode);
//                                            airPojo.setBranchFaxPhoneNo(branchFaxPhoneNo);
//                                            airPojo.setBranchEmail(branchEmail);
//                                            airPojo.setBranchRemarks(branchRemarks);
//                                            airPojo.setAggGrossAmountCrCash(aggGrossAmountCrCash);
//                                            airPojo.setAggGrossAmountDrCash(aggGrossAmountDrCash);
//                                            airPojo.setAmountCrBeforeDemonetization(amountCrBeforeDemonetization);
//                                            airPojo.setAmountCrAfterDemonetization(amountCrAfterDemonetization);
//                                            airPojo.setAccountRemarks(accountRemarks);
//                                            airPojo.setAccountRelationship(accountRelationship);
                                            airPojo.setPersonName(custIdWiseVector.get(21).toString());
//                                            airPojo.setPersonType(personType);
//                                            airPojo.setCustomerIdentity(jointCustId);                                            
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

                    }



                    custIdPre = custId;

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
                acNatureQuery = " select acctcode from accounttypemaster where acctnature in ('FD','MS','RD','DS')  ";
//                custIdWise = "select b.CustId from td_recon a, customerid b,td_vouchmst c where a.ACNo = b.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' "
//                        + "and a.ACNo=c.ACNO and (c.PrevVoucherNo='' or c.PrevVoucherNo is null) and substring(a.acno,3,2) in (" + acNatureQuery + ") "
//                        + "and a.ty = 0 /* and a.trantype = 0*/ and CloseFlag is null and not TranType='27' group by b.CustId having(sum(a.cramt) between " + fromAmt + " and " + toAmt + ")";
                custIdWise = "select d.id from (\n"
                        + "select a.acno as ano,c.custid as id, sum(a.cramt) as amt from td_recon a, \n"
                        + "(select distinct acno from td_vouchmst where (PrevVoucherNo='' or PrevVoucherNo is null)) b,\n"
                        + "customerid c where a.acno = b.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "' and a.ty = 0 and a.CloseFlag is null \n"
                        + "and a.TranType<>'27'  and a.TranType<>'8'\n"
                        + "group by a.acno,c.custid\n"
                        + "union all\n"
                        + "select a.acno as ano,c.custid as id, sum(a.cramt) as amt from rdrecon a, \n"
                        + "customerid c where a.acno = c.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "'\n"
                        + "and a.ty = 0  and a.TranType<>'8'\n"
                        + "group by a.acno,c.custid\n"
                        + "union all\n"
                        + "select a.acno as ano,c.custid as id, sum(a.cramt) as amt from ddstransaction a, \n"
                        + "customerid c where a.acno = c.acno and a.acno = c.acno and a.dt between '" + fromDt + "' and '" + toDt + "'\n"
                        + "and a.ty = 0  and a.TranType<>'8'\n"
                        + "group by a.acno,c.custid\n"
                        + ") d group by d.id having sum(d.amt) between " + fromAmt + " and " + toAmt
                        + "";
                mainQuery = "select cast(ifnull(e.custid,'') as decimal) as custId, \n"
                        + "ifnull(d.acno,'') as acno,\n"
                        + "ifnull(c.acctNature,'') as acNature,\n"
                        + "concat(ifnull(f.custname,''),\n"
                        + "if(ifnull(f.middle_name,'')= '', \n"
                        + "ifnull(f.middle_name,''), \n"
                        + "concat(' ', ifnull(f.middle_name,''))),if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName,  \n"
                        + "ifnull(d.acctCategory,'') as PersonType,\n"
                        + "ifnull(f.gender,'X') as Gender,\n"
                        + "concat(ifnull(f.fathername,''),\n"
                        + "if(ifnull(f.FatherMiddleName,'')= '', \n"
                        + "ifnull(f.FatherMiddleName,''), \n"
                        + "concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + "if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),"
                        + " concat(' ', ifnull(f.FatherLastName,'')))) as FatherName,  \n"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail  where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')  ),\n"
                        + " ifnull(f.PAN_GIRNumber,'')) as Pan, \n"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and\n"
                        + "legal_document = 'E' and CustEntityType = '01'), \n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(f.AADHAAR_NO,'')) as AadhaarNo,\n"
                        + "ifnull(f.PAN_GIRNumber,'') as Form60,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, \n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'D' \n"
                        + "and CustEntityType = '01'),'') as DrivingLicenseNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail \n"
                        + "where customerid = f.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, \n"
                        + " ifnull((select ifnull(IdentityNo,'') \n"
                        + "from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card, \n"
                        + "date_format(ifnull(f.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,\n"
                        + "ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')  as incorPorationDt,\n"
                        + "'IN' as dobPlace,  'IN' as incorPorationPlace,   \n"
                        + "'1' as addressType,\n"
                        + "concat(ifnull(f.PerAddressLine1,''), \n"
                        + "if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))), \n"
                        + "if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),   if(ifnull(f.PerBlock,'')='', '',\n"
                        + "concat(', ',ifnull(f.PerBlock,''))),   if(ifnull(f.PerCityCode,'')='', '',\n"
                        + "concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),\n"
                        + "if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' \n"
                        + "and REF_CODE = f.PerStateCode),''))),   if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, \n"
                        + "if(ifnull(f.PerPostalCode,'')='','XXXXXX',\n"
                        + "ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode, \n"
                        + "if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX',\n"
                        + " ifnull(f.PerStateCode,'XX'))) as StateCode, \n"
                        + "'IN' as countoryCode,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, \n"
                        + "ifnull(f.PerPhoneNumber,'') as phnoneNo,\n"
                        + "ifnull(f.mobilenumber,'') as MobileNo,\n"
                        + "ifnull(f.PerFaxNumber,'') as faxNo,  \n"
                        + "ifnull(f.EmailID,'') as email, \n"
                        + "(select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from td_recon where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and CloseFlag is null and TranType<> '27' and TranType<> '8') as AggCrAmtDurPd , \n"
                        + "(select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from td_recon where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0  and trantype=0  and CloseFlag is null ) as AggCrCashAmtDurPd , \n "
                        + "(select cast(ifnull(sum(ifnull(DrAmt,0)),0) as decimal) as DrAmt from td_recon where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and CloseFlag is null and not TranType='27') as AggDrAmtDurPd , \n"
                        + "(select ifnull(DATE_FORMAT(max(Trantime),'%d-%m-%Y') ,'') from td_recon where acno = d.ACNo  and CloseFlag is null and TranType<> '27' and TranType<> '8' ) as lastTranTime, \n"
                        + "ifnull(d.OperMode,'') as accRelationShip,\n"
                        + "ifnull(d.custid1,''), ifnull(d.custid2,''),\n"
                        + "ifnull(d.custid3,''), ifnull(d.custid4,'')  \n"
                        + "from   branchmaster a,   parameterinfo b, accounttypemaster c,  td_accountmaster d,   customerid e,\n"
                        + "cbs_customer_master_detail f,   cbs_cust_misinfo g   where a.BrnCode = b.BrnCode   and lpad(a.BrnCode,2,0) = substring(d.ACNo,1,2) \n"
                        + "and substring(d.ACNo,3,2) = c.AcctCode   and d.ACNo = e.Acno   and e.CustId = f.customerid   and f.customerid = g.CustomerId \n"
                        + "and substring(d.acno,1,2) in (" + brCode + ")   \n"
                        + "and substring(d.acno,3,2) in (" + acNatureQuery + " )  \n"
                        + "and e.CustId in  ( " + custIdWise + " ) "
                        + "and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') \n"
                        + "group by e.CustId  order by e.CustId "
                        + "union all"
                        + "select cast(ifnull(e.custid,'') as decimal) as custId, \n"
                        + "ifnull(d.acno,'') as acno,\n"
                        + "ifnull(c.acctNature,'') as acNature,\n"
                        + "concat(ifnull(f.custname,''),\n"
                        + "if(ifnull(f.middle_name,'')= '', \n"
                        + "ifnull(f.middle_name,''), \n"
                        + "concat(' ', ifnull(f.middle_name,''))),if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName,  \n"
                        + "ifnull(d.acctCategory,'') as PersonType,\n"
                        + "ifnull(f.gender,'X') as Gender,\n"
                        + "concat(ifnull(f.fathername,''),\n"
                        + "if(ifnull(f.FatherMiddleName,'')= '', \n"
                        + "ifnull(f.FatherMiddleName,''), \n"
                        + "concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + "if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),"
                        + " concat(' ', ifnull(f.FatherLastName,'')))) as FatherName,  \n"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail  where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')  ),\n"
                        + " ifnull(f.PAN_GIRNumber,'')) as Pan, \n"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and\n"
                        + "legal_document = 'E' and CustEntityType = '01'), \n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(f.AADHAAR_NO,'')) as AadhaarNo,\n"
                        + "ifnull(f.PAN_GIRNumber,'') as Form60,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, \n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'D' \n"
                        + "and CustEntityType = '01'),'') as DrivingLicenseNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail \n"
                        + "where customerid = f.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, \n"
                        + " ifnull((select ifnull(IdentityNo,'') \n"
                        + "from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card, \n"
                        + "date_format(ifnull(f.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,\n"
                        + "ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')  as incorPorationDt,\n"
                        + "'IN' as dobPlace,  'IN' as incorPorationPlace,   \n"
                        + "'1' as addressType,\n"
                        + "concat(ifnull(f.PerAddressLine1,''), \n"
                        + "if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))), \n"
                        + "if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),   if(ifnull(f.PerBlock,'')='', '',\n"
                        + "concat(', ',ifnull(f.PerBlock,''))),   if(ifnull(f.PerCityCode,'')='', '',\n"
                        + "concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),\n"
                        + "if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' \n"
                        + "and REF_CODE = f.PerStateCode),''))),   if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, \n"
                        + "if(ifnull(f.PerPostalCode,'')='','XXXXXX',\n"
                        + "ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode, \n"
                        + "if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX',\n"
                        + " ifnull(f.PerStateCode,'XX'))) as StateCode, \n"
                        + "'IN' as countoryCode,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, \n"
                        + "ifnull(f.PerPhoneNumber,'') as phnoneNo,\n"
                        + "ifnull(f.mobilenumber,'') as MobileNo,\n"
                        + "ifnull(f.PerFaxNumber,'') as faxNo,  \n"
                        + "ifnull(f.EmailID,'') as email, \n"
                        + "(select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from rdrecon where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and CloseFlag is null and TranType<> '27' and TranType<> '8') as AggCrAmtDurPd , \n"
                        + "(select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from rdrecon where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0  and trantype=0  and CloseFlag is null ) as AggCrCashAmtDurPd , \n "
                        + "(select cast(ifnull(sum(ifnull(DrAmt,0)),0) as decimal) as DrAmt from rdrecon where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and CloseFlag is null and not TranType='27') as AggDrAmtDurPd , \n"
                        + "(select ifnull(DATE_FORMAT(max(Trantime),'%d-%m-%Y') ,'') from rdrecon where acno = d.ACNo  and CloseFlag is null and TranType<> '27' and TranType<> '8' ) as lastTranTime, \n"
                        + "ifnull(d.OperMode,'') as accRelationShip,\n"
                        + "ifnull(d.custid1,''), ifnull(d.custid2,''),\n"
                        + "ifnull(d.custid3,''), ifnull(d.custid4,'')  \n"
                        + "from   branchmaster a,   parameterinfo b, accounttypemaster c,  accountmaster d,   customerid e,\n"
                        + "cbs_customer_master_detail f,   cbs_cust_misinfo g   where a.BrnCode = b.BrnCode   and lpad(a.BrnCode,2,0) = substring(d.ACNo,1,2) \n"
                        + "and substring(d.ACNo,3,2) = c.AcctCode   and d.ACNo = e.Acno   and e.CustId = f.customerid   and f.customerid = g.CustomerId \n"
                        + "and substring(d.acno,1,2) in (" + brCode + ")   \n"
                        + "and substring(d.acno,3,2) in (" + acNatureQuery + " )  \n"
                        + "and e.CustId in  ( " + custIdWise + " ) "
                        + "and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') \n"
                        + "group by e.CustId  order by e.CustId "
                        + "union all"
                        + "select cast(ifnull(e.custid,'') as decimal) as custId, \n"
                        + "ifnull(d.acno,'') as acno,\n"
                        + "ifnull(c.acctNature,'') as acNature,\n"
                        + "concat(ifnull(f.custname,''),\n"
                        + "if(ifnull(f.middle_name,'')= '', \n"
                        + "ifnull(f.middle_name,''), \n"
                        + "concat(' ', ifnull(f.middle_name,''))),if(ifnull(f.last_name,'')= '', ifnull(f.last_name,''), concat(' ', ifnull(f.last_name,'')))) as PersonName,  \n"
                        + "ifnull(d.acctCategory,'') as PersonType,\n"
                        + "ifnull(f.gender,'X') as Gender,\n"
                        + "concat(ifnull(f.fathername,''),\n"
                        + "if(ifnull(f.FatherMiddleName,'')= '', \n"
                        + "ifnull(f.FatherMiddleName,''), \n"
                        + "concat(' ', ifnull(f.FatherMiddleName,''))),"
                        + "if(ifnull(f.FatherLastName,'')= '', ifnull(f.FatherLastName,''),"
                        + " concat(' ', ifnull(f.FatherLastName,'')))) as FatherName,  \n"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + " (select ifnull(IdentityNo,'') from  cbs_customer_master_detail  where customerid = f.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')  ),\n"
                        + " ifnull(f.PAN_GIRNumber,'')) as Pan, \n"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and\n"
                        + "legal_document = 'E' and CustEntityType = '01'), \n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = f.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(f.AADHAAR_NO,'')) as AadhaarNo,\n"
                        + "ifnull(f.PAN_GIRNumber,'') as Form60,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo, \n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'D' \n"
                        + "and CustEntityType = '01'),'') as DrivingLicenseNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail \n"
                        + "where customerid = f.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, \n"
                        + " ifnull((select ifnull(IdentityNo,'') \n"
                        + "from  cbs_customer_master_detail where customerid = f.customerid and legal_document = 'F' and CustEntityType = '01'),'') as nrega_job_card, \n"
                        + "date_format(ifnull(f.DateOfBirth,'0001-01-01'),'%d-%m-%Y') as dob,\n"
                        + "ifnull(date_format(ifnull(g.Incorporation_Date,'0001-01-01'),'%d-%m-%Y'),'01-01-0001')  as incorPorationDt,\n"
                        + "'IN' as dobPlace,  'IN' as incorPorationPlace,   \n"
                        + "'1' as addressType,\n"
                        + "concat(ifnull(f.PerAddressLine1,''), \n"
                        + "if(ifnull(f.PerAddressLine2,'')='', '', concat(', ',ifnull(f.PerAddressLine2,''))), \n"
                        + "if(ifnull(f.PerVillage,'')='', '', concat(', ',ifnull(f.PerVillage,''))),   if(ifnull(f.PerBlock,'')='', '',\n"
                        + "concat(', ',ifnull(f.PerBlock,''))),   if(ifnull(f.PerCityCode,'')='', '',\n"
                        + "concat(', ', ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),''))),\n"
                        + "if(ifnull(f.PerStateCode,'')='', '', concat(', ',ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002' \n"
                        + "and REF_CODE = f.PerStateCode),''))),   if(ifnull(f.PerPostalCode,'')='', '', concat(', ',ifnull(f.PerPostalCode,''))), ',INDIA' ) as address,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = f.PerCityCode),'') as CityOrTown, \n"
                        + "if(ifnull(f.PerPostalCode,'')='','XXXXXX',\n"
                        + "ifnull(f.PerPostalCode,'XXXXXX')) as PostalCode, \n"
                        + "if(ifnull(f.PerStateCode,'')='','XX',if(ifnull(f.PerStateCode,'XX')='0','XX',\n"
                        + " ifnull(f.PerStateCode,'XX'))) as StateCode, \n"
                        + "'IN' as countoryCode,\n"
                        + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '021' and REF_CODE = g.OccupationCode),'') as businessOrOccuptions, \n"
                        + "ifnull(f.PerPhoneNumber,'') as phnoneNo,\n"
                        + "ifnull(f.mobilenumber,'') as MobileNo,\n"
                        + "ifnull(f.PerFaxNumber,'') as faxNo,  \n"
                        + "ifnull(f.EmailID,'') as email, \n"
                        + "(select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from ddstransaction where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and CloseFlag is null and TranType<> '27' and TranType<> '8') as AggCrAmtDurPd , \n"
                        + "(select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from ddstransaction where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0  and trantype=0  and CloseFlag is null ) as AggCrCashAmtDurPd , \n "
                        + "(select cast(ifnull(sum(ifnull(DrAmt,0)),0) as decimal) as DrAmt from ddstransaction where acno in(select acno from customerid where CustId=e.CustId) and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 1 and CloseFlag is null and not TranType='27') as AggDrAmtDurPd , \n"
                        + "(select ifnull(DATE_FORMAT(max(Trantime),'%d-%m-%Y') ,'') from ddstransaction where acno = d.ACNo  and CloseFlag is null and TranType<> '27' and TranType<> '8' ) as lastTranTime, \n"
                        + "ifnull(d.OperMode,'') as accRelationShip,\n"
                        + "ifnull(d.custid1,''), ifnull(d.custid2,''),\n"
                        + "ifnull(d.custid3,''), ifnull(d.custid4,'')  \n"
                        + "from   branchmaster a,   parameterinfo b, accounttypemaster c,  accountmaster d,   customerid e,\n"
                        + "cbs_customer_master_detail f,   cbs_cust_misinfo g   where a.BrnCode = b.BrnCode   and lpad(a.BrnCode,2,0) = substring(d.ACNo,1,2) \n"
                        + "and substring(d.ACNo,3,2) = c.AcctCode   and d.ACNo = e.Acno   and e.CustId = f.customerid   and f.customerid = g.CustomerId \n"
                        + "and substring(d.acno,1,2) in (" + brCode + ")   \n"
                        + "and substring(d.acno,3,2) in (" + acNatureQuery + " )  \n"
                        + "and e.CustId in  ( " + custIdWise + " ) "
                        + "and (d.ClosingDate is null or d.ClosingDate = '' or d.ClosingDate >'" + fromDt + "') \n"
                        + "group by e.CustId  order by e.CustId ";

                custIdWiseList = em.createNativeQuery(mainQuery).getResultList();
                if (!custIdWiseList.isEmpty()) {
                    for (int i = 0; i < custIdWiseList.size(); i++) {
                        Form61APojo airPojo = new Form61APojo();
                        Vector custIdWiseVector = (Vector) custIdWiseList.get(i);
                        String custId = custIdWiseVector.get(0).toString();

                        if (custId.equalsIgnoreCase(custIdPre)) {
                            reportSerialNumber = reportSerialNumber;
                        } else {
                            reportSerialNumber = reportSerialNumber + 1;
                        }
                        Integer originalReportSerialNumber = 0;
                        String customerIdentity = custId;
                        String accountNature = custIdWiseVector.get(2).toString();
                        String personName = custIdWiseVector.get(3).toString();
                        String personType = custIdWiseVector.get(4).toString();
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

                        String gender = custIdWiseVector.get(5).toString();
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


                        String fatherName = custIdWiseVector.get(6).toString();
                        String pan = custIdWiseVector.get(7).toString().trim().toUpperCase();
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
                        String aadhaarNumber = custIdWiseVector.get(8).toString().trim();
                        String form60Acknowledgement = custIdWiseVector.get(9).toString().trim().equalsIgnoreCase("Form 60") ? "Form 60" : "";//custIdWiseVector.get(27).toString();

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
                        if (!custIdWiseVector.get(10).toString().equalsIgnoreCase("")) {//Voter Id
                            identificationType = "B";
                            identificationNumber = custIdWiseVector.get(11).toString();
                        } else if (!custIdWiseVector.get(11).toString().equalsIgnoreCase("")) {//Driving License
                            identificationType = "E";
                            identificationNumber = custIdWiseVector.get(10).toString();
                        } else if (!custIdWiseVector.get(12).toString().equalsIgnoreCase("")) {//Passport
                            identificationType = "A";
                            identificationNumber = custIdWiseVector.get(12).toString();
                        } else if (!custIdWiseVector.get(13).toString().equalsIgnoreCase("")) {//Narega
                            identificationType = "H";
                            identificationNumber = custIdWiseVector.get(13).toString();
                        } else if (!custIdWiseVector.get(8).toString().equalsIgnoreCase("")) {//Aadhaar
                            identificationType = "G";
                            identificationNumber = custIdWiseVector.get(8).toString();
                        } else if (!pan.equalsIgnoreCase("")) {
                            if (!pan.equalsIgnoreCase("Form 60")) {
                                identificationType = "C";
                                identificationNumber = pan;
                            }
                        }

                        String dobOrIncorporation = custIdWiseVector.get(14).toString();
                        String nationalityOrCountryOfIncorporation = custIdWiseVector.get(17).toString().trim();
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
                            } else if (!custIdWiseVector.get(14).toString().equalsIgnoreCase("")) {
                                dobOrIncorporation = custIdWiseVector.get(14).toString();
                                nationalityOrCountryOfIncorporation = custIdWiseVector.get(17).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(35).toString();
                            } else {
                                dobOrIncorporation = "01-01-0001";
                                nationalityOrCountryOfIncorporation = nationalityOrCountryOfIncorporation;
                            }
                        } else {
                            dobOrIncorporation = custIdWiseVector.get(14).toString();
                            nationalityOrCountryOfIncorporation = custIdWiseVector.get(17).toString().equalsIgnoreCase("0") ? "" : custIdWiseVector.get(35).toString();
                        }

                        String addressType = custIdWiseVector.get(18).toString();
                        String address = custIdWiseVector.get(19).toString();
                        String cityOrTown = custIdWiseVector.get(20).toString();
                        String pinCode = custIdWiseVector.get(21).toString();
                        String state = custIdWiseVector.get(22).toString();
                        String country = custIdWiseVector.get(23).toString();
                        String businessOrOccupation = custIdWiseVector.get(24).toString();

                        String primaryStdCode = custIdWiseVector.get(25).toString().contains("-") ? custIdWiseVector.get(25).toString().split("-").length > 0 ? custIdWiseVector.get(25).toString().split("-")[0] : "" : "";
                        String primaryTelephoneNumber = custIdWiseVector.get(25).toString().contains("-") ? custIdWiseVector.get(25).toString().split("-").length > 1 ? custIdWiseVector.get(25).toString().split("-")[1] : "" : "";
                        String primaryMobileNumber = custIdWiseVector.get(26).toString().length() != 10 ? "" : custIdWiseVector.get(26).toString();

                        String secondaryStdCode = custIdWiseVector.get(25).toString().contains("-") ? custIdWiseVector.get(25).toString().split("-").length > 0 ? custIdWiseVector.get(25).toString().split("-")[0] : "" : "";
                        String secondaryTelephoneNumber = custIdWiseVector.get(25).toString().contains("-") ? custIdWiseVector.get(25).toString().split("-").length > 1 ? custIdWiseVector.get(25).toString().split("-")[1] : "" : "";
                        String secondaryMobileNumber = "";
                        String email = custIdWiseVector.get(28).toString();
                        String remarks = "";
                        // TD - Time Deposit//
                        String productType = "";
                        if (acNature.equalsIgnoreCase("SFT005")) {
                            productType = "TD";
                        }
                        BigDecimal amountRecefrmPer = new BigDecimal(custIdWiseVector.get(29).toString());
                        BigDecimal amountRecefrmPerInCash = new BigDecimal(custIdWiseVector.get(30).toString());
                        BigDecimal amountPaidToPer = new BigDecimal(custIdWiseVector.get(31).toString());
                        String tranRemark = "";
                        String productIdentifier = custIdWiseVector.get(1).toString();
                        String LastDtofTrans = custIdWiseVector.get(32).toString();

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
