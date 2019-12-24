/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Vector;
import com.cbs.dto.Stock;
import com.cbs.dto.Issue;
import com.cbs.dto.master.RemittanceTypeMaster;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "BillCommissionFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class BillCommissionFacade implements BillCommissionFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingFacade;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List loadGridBillCommission() throws ApplicationException {
        List gridValues = new ArrayList();
        try {
            gridValues = em.createNativeQuery("SELECT COLLECTTYPE,AMOUNTFROM,AMOUNTTO,COMMCHARGE,COALESCE(POSTAGE,0),PLACETYPE,COALESCE(MINCHARGE,0),COALESCE(SURCHARGE,0),PAYBY,MAXCANCHARGE,date_format(SLABDATE,'%d/%m/%Y'),COMMFLAG FROM bill_commission order by collecttype, amountfrom").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridValues;
    }

    public List billTypeLoad() throws ApplicationException {
        List billType = new ArrayList();
        try {
            billType = em.createNativeQuery("select INSTCODE,INSTNATURE from billtypemaster where instnature in ('PO','DD','TPO') ORDER BY INSTNATURE").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return billType;
    }

    public String saveDetailBillCommission(String collectType, Double amtFrom, Double amtTo, Float minChg, Float comChg, Float postage, String slabDate, String placeType, Float surCharge, Integer payBY, String comFlag, String enterBy, Float maxCanChg, boolean eFlag, String oldBillType, double oldAmtFrom, double OldAmtTo, Float oldComChg) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (collectType == null || collectType.equalsIgnoreCase("") || collectType.length() == 0) {
                ut.rollback();
                return "Bill Type Is Blank !!!";
            }
            if (amtFrom == null) {
                ut.rollback();
                return "Amount From Is Blank !!!";
            }
            if (amtTo == null) {
                ut.rollback();
                return "Amount To Is Blank !!!";
            }
            if (minChg == null) {
                ut.rollback();
                return "Minimum Charge Is Blank !!!";
            }
            if (comChg == null) {
                ut.rollback();
                return "Commission Charge Is Blank !!!";
            }
            if (postage == null) {
                ut.rollback();
                return "Postage Charge Is Blank !!!";
            }
            if (slabDate == null) {
                ut.rollback();
                return "With Effect From Date Is Not Selected !!!";
            }
            if (placeType == null || placeType.equalsIgnoreCase("")) {
                ut.rollback();
                return "Place Type Is Blank !!!";
            }
            if (surCharge == null) {
                ut.rollback();
                return "Surcharge Is Blank !!!";
            }
            if (payBY == null) {
                ut.rollback();
                return "Pay By Is Blank !!!";
            }
            if (enterBy == null || enterBy.equalsIgnoreCase("") || enterBy.length() == 0) {
                ut.rollback();
                return "Enter By Is Blank !!!";
            }
            if (maxCanChg == null) {
                ut.rollback();
                return "Cancellation Charge Is Blank !!!";
            }
            if (eFlag == false) {
                Query insertQuery = em.createNativeQuery("insert bill_commission (collectType, AmountFrom, AmountTo,minCharge, CommCharge,postage, slabDate,PlaceType,SurCharge,payby, CommFlag,enterby,maxcancharge,TRANTIME) values" + " ('" + collectType + "'," + amtFrom + ", " + amtTo + ", " + minChg + "," + " " + comChg + "," + postage + ",'" + slabDate + "','" + placeType + "'," + surCharge + "," + payBY + ",'" + comFlag + "','" + enterBy + "'," + maxCanChg + ",CURRENT_TIMESTAMP)");
                var = insertQuery.executeUpdate();
            } else {
                Query insertQuery1 = em.createNativeQuery("update bill_commission  set AmountFrom=" + amtFrom + ",AmountTo= " + amtTo + ", mincharge=" + minChg + "," + " commcharge=" + comChg + ", postage = " + postage + ", maxcancharge=" + maxCanChg + ",slabdate='" + slabDate + "'," + " placetype='" + placeType + "', surcharge=" + surCharge + ", payby=" + payBY + ",enterby='" + enterBy + "',CommFlag='" + comFlag + "' where collectType='" + oldBillType + "' " + " and amountfrom=" + oldAmtFrom + " and amountto =" + OldAmtTo + " and commcharge=" + oldComChg + "");
                var = insertQuery1.executeUpdate();
            }
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record could not be saved , Please Fill Again Valid Values In All Fields !!!";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String deleteRecordBillCommission(String collectType, double amtFrom, float comChg) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            Query insertQuery1 = em.createNativeQuery("delete from bill_commission  where collectType='" + collectType + "' and amountfrom=" + amtFrom + " and commcharge=" + comChg + "");
            var = insertQuery1.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List loadGridBranchMaster() throws ApplicationException {
        List gridValues = new ArrayList();
        try {
            gridValues = em.createNativeQuery("select brncode,branchname,alphacode,address,city,state,pincode,roffice,"
                    + "ifsc_code,location_type,computerized_status,mobileNo from branchmaster order by brncode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridValues;
    }

    public String modifyDetailBranchMaster(String brName, String brAddress, int brCode, String alphaCode, String city,
            String state, String regOffice, int pinCode, String ifscCode, String locationType,
            String compStatus, String mobileNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertResult = em.createNativeQuery("update branchmaster set branchname='" + brName + "',"
                    + "address='" + brAddress + "',brncode='" + brCode + "',alphacode='" + alphaCode + "',"
                    + "city='" + city + "',state='" + state + "',roffice='" + regOffice + "',pincode='" + pinCode + "',"
                    + "ifsc_code='" + ifscCode + "',location_type='" + locationType + "',"
                    + "computerized_status='" + compStatus + "' ,mobileNo = '" + mobileNo + "' where brncode='" + brCode + "'").executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Problem In BranchMaster Updation.");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveDetailBranchMaster(String brName, String brAddress, int brCode, String alphaCode, String city,
            String state, String regOffice, int pinCode, String ifscCode, String locationType,
            String compStatus, String block, String taluk, String office_type, String opendate, String closedate, String mobileNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertResult = em.createNativeQuery("insert into branchmaster(branchname,address,brncode,alphacode,city,"
                    + "state,roffice,pincode,enterby,ex_counter,ifsc_code,location_type,computerized_status,block,taluk,office_type,opendate,closedate,mobileNo) "
                    + "values('" + brName + "','" + brAddress + "'," + brCode + ",'" + alphaCode + "','" + city + "',"
                    + "'" + state + "','" + regOffice + "'," + pinCode + ",'SYSTEM','N','" + ifscCode + "',"
                    + "'" + locationType + "','" + compStatus + "','" + block + "','" + taluk + "','" + office_type + "','" + opendate + "','" + closedate + "','" + mobileNo + "')").executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Insertion Problem In BranchMaster.");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String deleteRecordBranchMaster(int brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List checkResult = em.createNativeQuery("select * from branchmaster where brncode=" + brCode + "").getResultList();
            if (!checkResult.isEmpty()) {

                Query insertQuery1 = em.createNativeQuery("delete from branchmaster where brncode=" + brCode + "");
                var = insertQuery1.executeUpdate();

            }
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List checkBrnCode(int brCode) throws ApplicationException {
        List brnName = new ArrayList();
        try {
            brnName = em.createNativeQuery("select branchname from branchmaster where brncode=" + brCode + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brnName;
    }
    /*
     * end BranchMasterBean
     * start PoDdChargesBean
     * 
     */

    public List dataList() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct instcode from billtypemaster where instnature in ('DD','PO')").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List listCharge(String type, String date, int pay, double amt) throws ApplicationException {
        try {
            return em.createNativeQuery("select amountfrom,amountto,commcharge,surcharge,mincharge,commflag from bill_commission where slabdate = "
                    + "(select max(slabdate) from bill_commission where slabdate <= '" + date + "' and collecttype='" + type + "') "
                    + "and collecttype='" + type + "' and payby=" + pay + " and amountto>=" + amt + " and amountfrom<=" + amt + " order by amountfrom ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }


    }

//    public List rot(String date) throws ApplicationException {
//        try {
//            return em.createNativeQuery("select ROT from taxmaster where ApplicableDt<='" + date + "' and applicableFlag='Y' and Auth='Y'").getResultList();
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    /*End of tthe code of PoddChargesBean
     * Start code StockBookIssueRegisterBean
     */
    public List instTypeStock() throws ApplicationException {
        List instCode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct instcode from billtypemaster");
            instCode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return instCode;
    }

    public List setSlabStockBook(String instcode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select DISTINCT SlabCode from chbookmaster_amtslab where instCode ='" + instcode + "'");
            tableResult = selectQuery.getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public List setAmtSlabStockBook(String instcode, int slabcode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select AmtFROM,AmtTO from chbookmaster_amtslab where instCode ='" + instcode + "' and SlabCode=" + slabcode + " and Flag ='F'");
            tableResult = selectQuery.getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public List showDataStockBook(String instcode, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select sno,Instcode,Slabcode,Numfrom,numTo,Amtfrom,amtto,Leaves,printlot,status,EnterBy from chbookmaster_amtwise where instcode='" + instcode + "' and brncode='" + brCode + "' and Auth='N' ORDER BY InstCode,slabcode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveStockBook(List<Stock> table, String dt, String orgnBrCode) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String result = null;
        try {

            ut.begin();

            List<Stock> slabTable = table;
            /**
             * **************CHECKING THE DAYBEGIN & DATE ACCORDING TO ISSUED
             * BRANCH CODE*****************
             */
            String tempBd = daybeginDate(orgnBrCode);

            /**
             * **************COMPAIR THE DAYBEGIN DATE AND TODAY
             * DATE*************************************
             */
            if (ymd.parse(tempBd).after(ymd.parse(dt)) || ymd.parse(tempBd).before(ymd.parse(dt))) {
                ut.rollback();
                return "Check the today date you have passed";
            }

            int cOpt = 0;
            List optList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where reportname = 'PO_SERIES'").getResultList();
            if (!optList.isEmpty()) {
                Vector optVector = (Vector) optList.get(0);
                cOpt = Integer.parseInt(optVector.get(0).toString());
            }

            for (int i = 0; i < slabTable.size(); i++) {
                String instCode = slabTable.get(i).getInstCode();
                int slabCode = slabTable.get(i).getCode();
                double Amtfrom = slabTable.get(i).getAmtFrom();
                BigDecimal AmtTo = slabTable.get(i).getAmtto();
                String NumFrom = slabTable.get(i).getNumFrom();
                String NumTo = slabTable.get(i).getNumTo();
                String Leaves = slabTable.get(i).getLeaves();
                String PrintLot = slabTable.get(i).getPrintlot();
                String stats = slabTable.get(i).getStatus();
                String Enterby = slabTable.get(i).getEnterBy();

                List list = em.createNativeQuery("select ifnull(InstNature,'') from billtypemaster where instcode = '" + instCode + "'").getResultList();
                Vector vBillNat = (Vector) list.get(0);
                String billNature = vBillNat.get(0).toString();

                if ((cOpt != 0) && (billNature.equalsIgnoreCase("PO"))) {
                    PrintLot = "000000";
                }

                Integer entryList = em.createNativeQuery("insert into chbookmaster_amtwise(InstCode,SlabCode,Amtfrom,AmtTo,NumFrom,NumTo, Leaves,PrintLot,Dt,Enterby,status,issuedt,auth,ISSUEBY,brncode)"
                        + "values ('" + instCode + "'," + slabCode + "," + Amtfrom + "," + AmtTo + "," + Integer.parseInt(NumFrom) + "," + Integer.parseInt(NumTo) + "," + Integer.parseInt(Leaves) + ",'" + PrintLot + "','" + dt + "','" + Enterby + "','F','" + dt + "','N','" + Enterby + "','" + orgnBrCode + "')").executeUpdate();
                if (entryList > 0) {
                    ut.commit();
                    result = "Stock Entry Inserted Successfully";
                } else {
                    ut.rollback();
                    result = "Data is not inserted chBookMaster_Amtwise";
                }

            }
            return result;

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List setSlabIssue(String instcode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct SlabCode from chbookmaster_amtslab where instCode ='" + instcode + "'");
            tableResult = selectQuery.getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List setAmtSlabIssueStockBook(String instcode, int slabcode, String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct AmtFrom,AmtTo from chbookmaster_amtwise where instCode ='" + instcode + "' and brncode='" + brCode + "' and SlabCode=" + slabcode + " and status in('I','F')");
            tableResult = selectQuery.getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List setCodeTableIssue(String instcode, int slabcode, String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select sno,numFrom,numTo,Leaves,printlot from chbookmaster_amtwise  where instCode ='" + instcode + "' and brncode='" + brCode + "' and SlabCode=" + slabcode + " and status='F' and Auth='N' order by numfrom");
            tableResult = selectQuery.getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List setCodeTableIssueBook(String instcode, int slabcode, String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select sno,numFrom,numTo,Leaves from chbookmaster_amtwise  where instCode ='" + instcode + "' and brncode='" + brCode + "' and SlabCode=" + slabcode + " and status='F' and Auth='N' order by numfrom");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String numberFromData(String from, String brCode) throws ApplicationException {
        String numFrom = "";
        try {

            List dateList = em.createNativeQuery("select numFrom from chbookmaster_amtwise where sno=" + from + " and brncode='" + brCode + "'").getResultList();
            Vector dateVect = (Vector) dateList.get(0);
            numFrom = dateVect.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return numFrom;
    }

    public List setNumberFromData(String instcode, int slabcode, String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select sno,numFrom,numTo,Leaves,printlot from chbookmaster_amtwise  where sno ='" + instcode + "' and brncode='" + brCode + "' and SlabCode=" + slabcode + " and status='F' and Auth='N' order by numfrom");
            tableResult = selectQuery.getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String saveBookIssue(List<Issue> table, String dt, String orgnBrCode, String userName) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String result = "";
        String tempBd = "";
        try {

            ut.begin();

            List<Issue> slabTable = table;
            /**
             * **************CHECKING THE DAYBEGIN & DATE ACCORDING TO ISSUED
             * BRANCH CODE*****************
             */
            tempBd = daybeginDate(orgnBrCode);

            /**
             * **************COMPAIR THE DAYBEGIN DATE AND TODAY
             * DATE*************************************
             */
            if (ymd.parse(tempBd).after(ymd.parse(dt)) || ymd.parse(tempBd).before(ymd.parse(dt))) {
                ut.rollback();
                return "Check the today date you have passed";
            }
            for (int i = 0; i < slabTable.size(); i++) {
                String instCode = slabTable.get(i).getInsttype();
                int slabCode = slabTable.get(i).getCode();
                double Amtfrom = slabTable.get(i).getAmtFrom();
                String NumFrom = slabTable.get(i).getNumFrom();
                String NumTo = slabTable.get(i).getNumTo();
                String Leaves = slabTable.get(i).getLeaves();
                String PrintLot = slabTable.get(i).getPrintlot();
                Integer entryList = em.createNativeQuery("Update chbookmaster_amtwise set status='I',issuedt='" + tempBd + "',issueby='" + userName + "' where instcode='" + instCode + "' and Slabcode=" + slabCode + " and numfrom= " + Integer.parseInt(NumFrom) + " and numto=" + Integer.parseInt(NumTo) + " and printlot='" + PrintLot + "' and status='F' AND DT<='" + tempBd + "' and brncode='" + orgnBrCode + "'").executeUpdate();
                if (entryList <= 0) {
                    result = " No Data For SAVE!!!";
                    ut.rollback();
                } else {
                    result = "Book Issued Successfully";
                }
            }
            ut.commit();
            return result;

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String chkSeriesStock(String instcode, Integer slabcode, String printlot, Integer numFrom, Integer numTo, String brCode) throws ApplicationException {
        List chkSeriesStock;
        String message = "";
        Integer tmpNumFrom;
        Integer tmpNumTo;
        try {
            Query selectQuery = em.createNativeQuery("select numfrom,numto from chbookmaster_amtwise where instcode = '" + instcode + "' and brncode='" + brCode + "' and slabcode=" + slabcode + " and printlot='" + printlot + "'");
            chkSeriesStock = selectQuery.getResultList();
            if (chkSeriesStock.isEmpty()) {
                return " ";
            }
            for (int i = 0; i < chkSeriesStock.size(); i++) {
                Vector dateVect = (Vector) chkSeriesStock.get(i);
                tmpNumFrom = Integer.parseInt(dateVect.get(0).toString());
                tmpNumTo = Integer.parseInt(dateVect.get(1).toString());
                if ((tmpNumFrom >= numFrom && tmpNumFrom <= numTo) || (tmpNumTo >= numFrom && tmpNumTo <= numTo)) {
                    return "Cheque Book Series Already Issued";
                } else if (tmpNumFrom <= numFrom && tmpNumTo >= numTo) {
                    return "Cheque Book Series Already Issued";
                }
            }
            return message;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String bookIssueGridDoubleClick(String dt, String orgnBrCode, String userName, String instCode, int slabcode, float sCode, String NumFrom) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        String tempBd = "";
        try {
            ut.begin();
            /**
             * **************CHECKING THE DAYBEGIN & DATE ACCORDING TO ISSUED
             * BRANCH CODE*****************
             */
            tempBd = daybeginDate(orgnBrCode);
            /**
             * **************COMPAIR THE DAYBEGIN DATE AND TODAY
             * DATE*************************************
             */
            if (ymd.parse(tempBd).after(ymd.parse(dt)) || ymd.parse(tempBd).before(ymd.parse(dt))) {
                ut.rollback();
                return "Check the today date you have passed";
            }
            Integer entryList = em.createNativeQuery("Update chbook_bill set statusFlag='D',lastUpdateDt='" + tempBd + "',LastUpdateBy='" + userName + "' where statusFlag='F' and Inst_Type='" + instCode + "' and Slabcode= " + slabcode + " and DDno=" + sCode + " and brncode='" + orgnBrCode + "' and series='" + NumFrom + "'").executeUpdate();
            if (entryList == 0) {
                result = " Leaf Not marked as Damaged!!!";
                ut.rollback();
                return result;
            } else {
                result = "Leaf marked as Damaged Successfully!!!";
            }
            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String showAllDataIssue(String instcode, Integer slabcode, String brCode) throws ApplicationException {
        List chkList;
        List a = null;
        try {
            Query selectQuery = em.createNativeQuery("select Amtfrom,floor(AmtTo) from chbookmaster_amtslab  where Instcode='" + instcode + "' and slabcode=" + slabcode + "");
            chkList = selectQuery.getResultList();
            if (chkList.isEmpty()) {
                return "No Data Resulted";
            }
            Vector dateVect = (Vector) chkList.get(0);
            float tmpNumFrom = Float.parseFloat(dateVect.get(0).toString());
            float tmpNumTo = Float.parseFloat(dateVect.get(1).toString());
            List chbookbill = em.createNativeQuery("select Inst_type,DDno,series,StatusFlag," + tmpNumFrom + "," + tmpNumTo + " from chbook_bill where statusFlag='F' and Inst_Type='" + instcode + "' and brncode='" + brCode + "' and slabcode='" + slabcode + "' order by Inst_Type,DDno").getResultList();
            if (chbookbill.isEmpty()) {
                return "No Data Resulted";
            }
            return chbookbill.toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    /*
     * End of Stock book issue register bean
     * Start RevalidateBean
     */

    public List instFind(String remType, String instNum, String brcode) throws ApplicationException {
        try {
            List checkList;
            if ((remType == null) || (instNum == null)) {
                throw new ApplicationException("You Must Enter Inst. Number And Remittance Type!!!.");
            }
            if (instNum.equalsIgnoreCase("")) {
                throw new ApplicationException("You Must Enter Inst. Number!!!.");
            }
            if (remType.equalsIgnoreCase("PO")) {
                checkList = em.createNativeQuery("select SEQNO,INSTNO,BILLTYPE,TIMELIMIT,CUSTNAME,PAYABLEAT,AMOUNT,coalesce(date_format(ORIGINDT,'%d/%m/%Y'),''),"
                        + "coalesce(INFAVOUROF,''),coalesce(date_format(VALIDATIONDT,'%d/%m/%Y'),'') from bill_po where instno='" + instNum + "' and billtype='"
                        + remType + "' and status='Issued' and substring(acno,1,2)='" + brcode + "'").getResultList();
            } else {
                checkList = em.createNativeQuery("select SEQNO,INSTNO,BILLTYPE,TIMELIMIT,CUSTNAME,PAYABLEAT,AMOUNT,coalesce(date_format(ORIGINDT,'%d/%m/%Y'),''),"
                        + "coalesce(INFAVOUROF,'') from bill_dd where instno='" + instNum + "' and billtype='" + remType + "' and status='Issued' "
                        + "and substring(acno,1,2)='" + brcode + "'").getResultList();
            }
            if (checkList.isEmpty()) {
                throw new ApplicationException("No Record Exists For This Instrument No.!!!.");
            }
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String revalidateSave(String remType, String instNum, String orgndt, String validDT, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var;
            int var1;
            if ((remType == null) || (instNum == null) || (orgndt == null) || (validDT == null) || (enterBy == null) || (brcode == null)) {
                throw new ApplicationException("Data Not Found!!!.");
            }
            long secnum = CbsUtil.dayDiff(ymd.parse(ymd.format(new Date())), ymd.parse(validDT));
            if (secnum != 0) {
                throw new ApplicationException("New remittance date should be current date !");
            }
            if (remType.equalsIgnoreCase("PO")) {
                Query insertQuery = em.createNativeQuery("INSERT INTO bill_revalidhis(FYEAR,SEQNO,INSTNO,BILLTYPE,ACNO,CUSTNAME,PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,TIMELIMIT,"
                        + "COMM,TRANTYPE,TY,INFAVOUROF,PLACE,ENTERBY,LASTUPDATEBY,AUTH,AUTHBY,TRANTIME,RECNO,PRINTFLAG,VALIDATIONDT)  SELECT FYEAR,SEQNO,INSTNO,BILLTYPE,"
                        + "ACNO,CUSTNAME,PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,TIMELIMIT,COMM,TRANTYPE,TY,INFAVOUROF,PLACE,ENTERBY,LASTUPDATEBY,AUTH,AUTHBY,TRANTIME,RECNO,"
                        + "PRINTFLAG,VALIDATIONDT FROM bill_po where billtype='" + remType + "' and instno='" + instNum + "' and origindt='" + orgndt + "' and status='ISSUED'"
                        + " and substring(acno,1,2)='" + brcode + "'");
                var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Update!!!");
                }
                Query updateQuery = em.createNativeQuery("UPDATE bill_po set VALIDATIONDT='" + validDT + "',lastupdateby='" + enterBy + "' where billtype='" + remType
                        + "' and instno='" + instNum + "' and origindt='" + orgndt + "' and status='ISSUED' and substring(acno,1,2)='" + brcode + "'");
                var1 = updateQuery.executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Data could not be Update!!!");
                }
            } else {
                Query insertQuery = em.createNativeQuery("insert into bill_revalidhis (FYEAR,SEQNO,INSTNO,BILLTYPE,ACNO,CUSTNAME,PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,"
                        + "TIMELIMIT,COMM,TRANTYPE,TY,INFAVOUROF,PLACE,ENTERBY,LASTUPDATEBY,AUTH,AUTHBY,TRANTIME,RECNO,PRINTFLAG,VALIDATIONDT) SELECT FYEAR,SEQNO,INSTNO,"
                        + "BILLTYPE,ACNO,CUSTNAME,PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,TIMELIMIT,COMM,TRANTYPE,TY,INFAVOUROF,PLACE,ENTERBY,LASTUPDATEBY,AUTH,AUTHBY,"
                        + "TRANTIME,RECNO,PRINTFLAG,ORIGINDT from bill_dd where billtype='" + remType + "' and instno='" + instNum + "' and origindt='" + orgndt
                        + "' and status='ISSUED' and substring(acno,1,2)='" + brcode + "'");
                var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Update!!!");
                }
                Query updateQuery = em.createNativeQuery("UPDATE bill_dd set originDT='" + validDT + "',lastupdateby='" + enterBy + "' where billtype='"
                        + remType + "' and instno='" + instNum + "' and origindt='" + orgndt + "' and status='ISSUED' and substring(acno,1,2)='" + brcode + "'");
                var1 = updateQuery.executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Data could not be Update!!!");
                }
            }
            ut.commit();
            return "Update Successfully.";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List getInstrumentCodeData() throws ApplicationException {
        List instCode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct instcode from billtypemaster");
            instCode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return instCode;
    }

    public List setFromRemaittance(String instcode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select ifnull(max(slabcode),0)+1,cast(ifnull(max(amtTo),0)+1 as unsigned) from chbookmaster_amtslab where instcode='" + instcode.toUpperCase() + "' and flag='F'");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public List amountSlabTable(String instcode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select sno,instcode,slabcode,amtfrom,amtto ,date_format(effdt,'%d/%m/%Y'),flag from chbookmaster_amtslab where flag='F'and instcode='" + instcode + "' order by AmtTo");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public String daybeginDate() throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date_format(date,'%Y%m%d') from cbs_bankdays where DATE=date_format(curdate(),'%Y%m%d')").getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveSlabMasterRemaittance(RemittanceTypeMaster obj, String enterBy, String dt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        try {
            ut.begin();
            String tempBd = daybeginDate();
            if (ymd.parse(tempBd).after(ymd.parse(dt)) || ymd.parse(tempBd).before(ymd.parse(dt))) {
                ut.rollback();
                return "Check the today date you have passed";
            }
            Query insertBillLostHist = em.createNativeQuery("insert into chbookmaster_amtslab(instcode,slabcode,amtfrom,amtto,effdt,flag,dt,enterby) "
                    + "values('" + obj.getInstCode() + "'," + obj.getSlabCode() + "," + obj.getAmtFrom() + "," + obj.getAmtTo() + ",'"
                    + ymd.format(dmy.parse(obj.getEffDt())) + "','F','" + tempBd + "','" + enterBy + "')");
            Integer varinsertBillLostHist = insertBillLostHist.executeUpdate();
            if (varinsertBillLostHist <= 0) {
                throw new ApplicationException("Data Not Saved");
            }
            ut.commit();
            return "Data Saved Successfully";

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String amountSalbDeleteRemaittance(String instrumentNo, int slab, int sno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query updateBillLost = em.createNativeQuery("update chbookmaster_amtslab set flag='D' where instcode = '" + instrumentNo + "' and slabcode = " + slab + " and sno=" + sno + "");
            Integer varUpdateBillLost = updateBillLost.executeUpdate();
            if (varUpdateBillLost <= 0) {
                throw new ApplicationException("Instrument Code is not Deleted");
            }

            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    /**
     * End of the code RemittanceAmountSlabMasterBean
     *
     */
    public String deleteSeries(double fromDDNo, double toDDNo, String instrumentType, String slabCode, String series, String userName, String orgBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String lastUpdateDt = ymd.format(new Date());
            double ddNo = fromDDNo;
            double ddNoDiff = toDDNo - fromDDNo;
            for (int i = 0; i < ddNoDiff + 1; i++) {
                ddNo = ddNo + i;
                List resultList = em.createNativeQuery("select * from chbook_bill where statusFlag='F' and Inst_Type='" + instrumentType + "' and Slabcode= " + Integer.parseInt(slabCode) + " and DDno=" + ddNo + " and brncode='" + orgBrCode + "' and series='" + series + "'").getResultList();
                if (!resultList.isEmpty()) {
                    Query updateQuery = em.createNativeQuery("Update chbook_bill set statusFlag='D',lastUpdateDt='" + lastUpdateDt + "',LastUpdateBy='" + userName + "' where statusFlag='F' and Inst_Type='" + instrumentType + "' and Slabcode= " + Integer.parseInt(slabCode) + " and DDno=" + ddNo + " and brncode='" + orgBrCode + "' and series='" + series + "'");
                    int updateResult = updateQuery.executeUpdate();
                    if (updateResult <= 0) {
                        throw new ApplicationException("Series has not been damaged successfully !");
                    }
                } else {
                    throw new ApplicationException("DD Number " + ddNo + " does not exist !");
                }
            }
            ut.commit();
            return "Series has been damaged successfully !";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String chequePurchaseSave(String bntButton, String branch, String seqNo, String bcBillNo, String activityType, String billNo, String accountNo, String acHolder,
            String trantType, String accType, String groupName, double totalLimit, double avgLimit, String bankCode, String bankName,
            String baranch, String schemeCode, String schemeCodeDesc, String docType, String docNo, String docSerise, String docDate, String billDate,
            double amount, int usanceDays, int gracePeriod, String maturityDate, double collectionChr, double postageAmt, double pocketExp,
            double margin, double interestRate, double interestAmt, String status, double realisationAmt, double commPaidToBankers,
            String auth, String authBy, String enterBy, String updateBy, String updateDt) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            float trsNo = 0f;
            String bcBillNoAuto = null;
            if (bntButton.equalsIgnoreCase("1")) {

                List checkList = em.createNativeQuery("select * from cheque_purchase where AccountNo = '" + accountNo + "' and DocNo = '" + docNo + "' and date_format(DT,'%Y%m%d') = '" + ymd.format(new Date()) + "'").getResultList();
                if (!checkList.isEmpty()) {
                    throw new ApplicationException("Data already exist in this AccountNo and Instrument !");
                }

                int insertChequePurchase = em.createNativeQuery("INSERT INTO cheque_purchase (BcBillNo, ActivityType, BillNo, AccountNo, "
                        + "AccountHolder, TrantType, AccType, GroupName, TotalLimit, AvgLimit, BankCode, BankName, BaranchName, schemeCode,schemeCodeDesc, "
                        + "DocType, DocNo, DocSerise, DocDate, BillDate, Amount, usanceDays, gracePeriod, maturityDate, CollectionChr, "
                        + "PostageAmt, PocketExp, margin, InterestRate, InterestAmt, Status, RealisationAmt, CommPaidToBankers, DT, "
                        + "AUTH, EnterBy) VALUES "
                        + "('" + bcBillNo + "', '" + activityType + "', '" + billNo + "', '" + accountNo + "', '" + acHolder + "', '" + trantType + "',"
                        + " '" + accType + "', '" + groupName + "', " + totalLimit + ", " + avgLimit + ", '" + bankCode + "', '" + bankName + "', '" + baranch + "',"
                        + " '" + schemeCode + "','" + schemeCodeDesc + "', '" + docType + "', '" + docNo + "', '" + docSerise + "', " + ymd.format(dmy.parse(docDate)) + ", " + ymd.format(dmy.parse(billDate)) + ","
                        + " " + amount + ", " + usanceDays + ", " + gracePeriod + ", " + ymd.format(dmy.parse(maturityDate)) + ", " + collectionChr + ", " + postageAmt + ", " + pocketExp + ", "
                        + "" + margin + ", " + interestRate + ", " + interestAmt + ", '" + status + "', " + realisationAmt + ", " + commPaidToBankers + ", "
                        + "now(), 'N', '" + enterBy + "')").executeUpdate();

                if (insertChequePurchase <= 0) {
                    throw new ApplicationException("Data insertion problem !");
                }

            } else if (bntButton.equalsIgnoreCase("2")) {

                int insertHisData = em.createNativeQuery("INSERT INTO cheque_purchase_his (BcBillNo, ActivityType, BillNo, AccountNo, AccountHolder, TrantType, AccType, GroupName, TotalLimit, AvgLimit, BankCode, BankName, BaranchName, schemeCode,schemeCodeDesc, DocType, DocNo, DocSerise, DocDate, BillDate, Amount, usanceDays, gracePeriod, maturityDate, CollectionChr, PostageAmt, PocketExp, margin, InterestRate, InterestAmt, Status, RealisationAmt, CommPaidToBankers, DT, AUTH, AuthBy, EnterBy, UpdatedBy, UpdatedT) "
                        + "SELECT BcBillNo, ActivityType, BillNo, AccountNo, AccountHolder, TrantType, AccType, GroupName, TotalLimit, AvgLimit, BankCode, BankName, BaranchName, schemeCode,schemeCodeDesc,DocType, DocNo, DocSerise, DocDate, BillDate, Amount, usanceDays, gracePeriod, maturityDate, CollectionChr, PostageAmt, PocketExp, margin, InterestRate, InterestAmt, Status, RealisationAmt, CommPaidToBankers, DT, AUTH, AuthBy, EnterBy, UpdatedBy, UpdatedT FROM cheque_purchase where SNo = " + seqNo + " and AUTH = 'N'").executeUpdate();
                if (insertHisData <= 0) {
                    throw new ApplicationException("Data insertion problem !");
                }
                int deleteChequeData = em.createNativeQuery("DELETE FROM cheque_purchase WHERE SNo = " + seqNo + " ").executeUpdate();
                if (deleteChequeData <= 0) {
                    throw new ApplicationException("Data deletion problem !");
                }
            } else if (bntButton.equalsIgnoreCase("3")) {

                List yearList = em.createNativeQuery("select distinct F_YEAR from yearend where YEARENDFLAG = 'N'").getResultList();
                Vector vtr = (Vector) yearList.get(0);
                String fYear = vtr.get(0).toString();
                List seqNoList = em.createNativeQuery("select max(cast(substring(BcBillNo,4,4) as unsigned))+1 from cheque_purchase").getResultList();
                vtr = (Vector) seqNoList.get(0);
                String sequnceNo = vtr.get(0).toString();
                bcBillNoAuto = "BPC" + CbsUtil.lPadding(4, Integer.parseInt(sequnceNo)) + "/" + fYear;

                trsNo = chequePurchaseTransation(bcBillNoAuto, seqNo, branch, accountNo, amount, collectionChr, ymd.format(new Date()), authBy);
                int updateChequeData = em.createNativeQuery("UPDATE cheque_purchase SET BcBillNo = '" + bcBillNoAuto + "',RealisationFlag='N',AUTH = 'Y', AuthBy = '" + authBy + "' WHERE SNo = " + seqNo + " and AccountNo = '" + accountNo + "'").executeUpdate();
                if (updateChequeData <= 0) {
                    throw new ApplicationException("Data updation problem !");
                }
            }

            ut.commit();
            return "true" + bcBillNoAuto + trsNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    private float chequePurchaseTransation(String bcBillNo, String seqNo, String branch, String acNo, double chequeAmt, double chequeChgAmt, String todayDt, String enterBy) throws ApplicationException {

        try {

            List list1 = em.createNativeQuery("select purpose,acno from abb_parameter_info where purpose='CHEQUE-PURCHASE-HEAD'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Please fill cheque purchase glhead");
            }
            Vector vtr1 = (Vector) list1.get(0);
            String ChequePurchaseHead = branch + vtr1.get(1).toString();

            list1 = em.createNativeQuery("select purpose,acno from abb_parameter_info where purpose='CHEQUE-PURCHASE-CHARGE-HEAD'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Please fill cheque purchase charge glhead");
            }
            vtr1 = (Vector) list1.get(0);
            String ChequePurchaseChargeHead = branch + vtr1.get(1).toString();
            String msg = "True";
            float trsNo = ftsPostingFacade.getTrsNo();

            msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(ChequePurchaseHead.substring(2, 4)), ChequePurchaseHead, 1, chequeAmt, todayDt, todayDt, 2,
                    "Cheque Purchase for Bill No. " + bcBillNo, enterBy, trsNo,
                    "", ftsPostingFacade.getRecNo(), "Y", enterBy, 0, 3, "", todayDt, 0f, "", "", 0, "",
                    0f, "", null, branch, branch, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(ChequePurchaseHead.substring(2, 4)), ChequePurchaseHead, 0, chequeAmt, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(acNo.substring(2, 4)), acNo, 0, chequeAmt,
                    todayDt, todayDt, 2, "Cheque purchase for Bill No. " + bcBillNo,
                    enterBy, trsNo, "", ftsPostingFacade.getRecNo(), "Y", enterBy, 0, 3, "", todayDt,
                    0f, "", "", 0, "", 0f, "", null, branch, branch, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(acNo.substring(2, 4)), acNo, chequeAmt, 0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            // For Chaege Amount
            if (chequeChgAmt != 0) {
                msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(ChequePurchaseChargeHead.substring(2, 4)), ChequePurchaseChargeHead, 0, chequeChgAmt, todayDt, todayDt, 2,
                        "Cheque Purchase Charge for Bill No. " + bcBillNo, enterBy, trsNo,
                        "", ftsPostingFacade.getRecNo(), "Y", enterBy, 8, 3, "", todayDt, 0f, "", "", 0, "",
                        0f, "", null, branch, branch, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(ChequePurchaseChargeHead.substring(2, 4)), ChequePurchaseChargeHead, chequeChgAmt, 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(acNo.substring(2, 4)), acNo, 1, chequeChgAmt,
                        todayDt, todayDt, 2, "Cheque purchase Charge for Bill No. " + bcBillNo,
                        enterBy, trsNo, "", ftsPostingFacade.getRecNo(), "Y", enterBy, 8, 3, "", todayDt,
                        0f, "", "", 0, "", 0f, "", null, branch, branch, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(acNo.substring(2, 4)), acNo, chequeChgAmt, 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            // For Services Tax
            Map<String, Double> map = new HashMap<String, Double>();
            int servTaxApplyCode = ftsPostingFacade.getCodeForReportName("STAXMODULE_ACTIVE");
            if (servTaxApplyCode == 1 && chequeChgAmt != 0) {
                double sPerc = 0;
                int rUpTo = 0;
                double sPercIgst = 0;
                int rUpToIgst = 0;
                List stateList = em.createNativeQuery("select aa.BranchState,bb.MailStateCode from ( "
                        + "(select State BranchState from branchmaster where brncode = " + Integer.parseInt(branch) + ")aa, "
                        + "(select MailStateCode from cbs_customer_master_detail a,customerid b where cast(a.customerid as unsigned) = b.custid and b.acno ='" + acNo + "')bb "
                        + ")").getResultList();

                Vector vtr = (Vector) stateList.get(0);
                String branchState = vtr.get(0).toString();
                String custState = vtr.get(1).toString();
                map = interFts.getTaxComponentSlab(todayDt);
                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                    rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
                map = interFts.getIgstTaxComponentSlab(todayDt);
                Set<Map.Entry<String, Double>> set1 = map.entrySet();
                Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                while (it1.hasNext()) {
                    Map.Entry entry = it1.next();
                    sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                    rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
                double penalty = 0;
                float recNo = 0f;
                double taxAmt = 0d;
                double taxAmtIgst = 0d;
                if (custState.equalsIgnoreCase(branchState)) {
                    taxAmt = CbsUtil.round(((chequeChgAmt * sPerc) / 100), rUpTo);
                } else {
                    taxAmtIgst = CbsUtil.round(((chequeChgAmt * sPercIgst) / 100), rUpToIgst);
                }
                //For Party A/c Transaction
                String taxDetail = "Cheque purchase for bill No. " + bcBillNo + (custState.equalsIgnoreCase(branchState) ? "CGST:SGST" : "IGST");
                msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(acNo.substring(2, 4)), acNo, 1, (custState.equalsIgnoreCase(branchState) ? taxAmt : taxAmtIgst),
                        todayDt, todayDt, 2, taxDetail, enterBy, trsNo, null,
                        recNo, "Y", enterBy, 71, 3, null, null, (float) 0, null, "A", 0, null, null, null, null,
                        branch, branch, 0, null, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(acNo.substring(2, 4)), acNo, (custState.equalsIgnoreCase(branchState) ? taxAmt : taxAmtIgst), 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                //ENd For Party A/c Transaction
                // Service Tax Head Transaction
                double sTaxAmt = CbsUtil.round((((custState.equalsIgnoreCase(branchState) ? taxAmt : taxAmtIgst) * 100) / (custState.equalsIgnoreCase(branchState) ? sPerc : sPercIgst)), rUpTo);
                map = interFts.getTaxComponent(sTaxAmt, todayDt);
                Set<Map.Entry<String, Double>> set11 = map.entrySet();
                Iterator<Map.Entry<String, Double>> it11 = set11.iterator();
                while (it11.hasNext()) {
                    Map.Entry entry = it11.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    String description = keyArray[0];
                    String taxHead = branch + keyArray[1];
                    String mainDetails = description.toUpperCase() + " Cheque purchase for Bill No. "+bcBillNo;
                    double taxAmount = Double.parseDouble(entry.getValue().toString());
                    String acNature = ftsPostingFacade.getAcNatureByCode(taxHead.substring(2, 4));
                    recNo = ftsPostingFacade.getRecNo();
                    msg = ftsPostingFacade.insertRecons(acNature, taxHead, 0, taxAmount, todayDt, todayDt, 2, mainDetails, enterBy, trsNo, null, recNo, "Y", enterBy,
                            71, 3, null, null, (float) 0, null, "A", 0, null, null, null, null, branch, branch, 0, null, "", "");
                    if (!msg.equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                    double crAmt = taxAmount;
                    double drAmt = (float) 0;
                    msg = ftsPostingFacade.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                    if (!msg.equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                }
                // END Service Tax Head Transaction
            }
            // END For Services Tax
            // END For Chaege Amount
            return trsNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getcustInfoByAcno(String acno) throws ApplicationException {
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select CustFullName,AcctCode,AcctDesc,e.SCHEME_CODE,f.SCHEME_DESCRIPTION,ifnull(CUST_TYPE,'') from accountmaster a,customerid b,cbs_customer_master_detail c, "
                    + "accounttypemaster d,cbs_loan_acc_mast_sec e,cbs_scheme_general_scheme_parameter_master f "
                    + "where a.acno = '" + acno + "' and a.acno = b.acno and a.acno = e.acno and b.custid = c.customerid "
                    + "and substring(a.acno,3,2) = d.acctcode and substring(a.acno,3,2) = f.scheme_type and e.SCHEME_CODE = f.SCHEME_CODE").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List getChargeDays() throws ApplicationException {

        try {
            return em.createNativeQuery("select charge,days from ( "
                    + "(select code as charge  from cbs_parameterinfo WHERE NAME =('CHEQUE-PURCHASE-CHG'))a, "
                    + "(select code as days from cbs_parameterinfo WHERE NAME =('USANCE-DAYS'))b "
                    + ")").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getSerialNo() throws ApplicationException {
        try {
            return em.createNativeQuery("select SNo from cheque_purchase where auth = 'N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getChequePurchaseDataBySeqNo(String seqNo) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT SNo,BcBillNo, ActivityType, BillNo, AccountNo, AccountHolder, TrantType, AccType, GroupName, TotalLimit, AvgLimit, BankCode, BankName, BaranchName, schemeCode, DocType, DocNo, DocSerise, date_format(DocDate,'%d/%m/%Y'), date_format(BillDate,'%d/%m/%Y'), Amount, usanceDays, gracePeriod, date_format(maturityDate,'%d/%m/%Y'), CollectionChr, PostageAmt, PocketExp, margin, InterestRate, InterestAmt, Status, RealisationAmt, CommPaidToBankers,EnterBy,schemeCodeDesc FROM cheque_purchase where SNo = " + Integer.parseInt(seqNo)).getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getChequePurchaseDataByBcBillNo(String bcBillNO) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT SNo,BcBillNo, ActivityType, BillNo, AccountNo, AccountHolder, TrantType, AccType, GroupName, TotalLimit, AvgLimit, BankCode, BankName, BaranchName, schemeCode, DocType, DocNo, DocSerise, date_format(DocDate,'%d/%m/%Y'), date_format(BillDate,'%d/%m/%Y'), Amount, usanceDays, gracePeriod, date_format(maturityDate,'%d/%m/%Y'), CollectionChr, PostageAmt, PocketExp, margin, InterestRate, InterestAmt, Status, RealisationAmt, CommPaidToBankers,EnterBy FROM cheque_purchase where BcBillNo = '" + bcBillNO + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String chequePurchaseRealisation(String bcBillNo, String acNo, double amount, String enterBy, String branch, String todayDt) throws ApplicationException {
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            List list1 = em.createNativeQuery("select purpose,acno from abb_parameter_info where purpose='CHEQUE-PURCHASE-HEAD'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Please fill cheque purchase glhead");
            }
            Vector vtr1 = (Vector) list1.get(0);
            String ChequePurchaseHead = branch + vtr1.get(1).toString();

            list1 = em.createNativeQuery("select purpose,acno from abb_parameter_info where purpose='OUTWARD-CHEQUE-FOR-COLLECTION'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Please fill cheque purchase charge glhead");
            }
            vtr1 = (Vector) list1.get(0);
            String outwardChequeCollectionHead = branch + vtr1.get(1).toString();
            String msg = "True";
            float trsNo = ftsPostingFacade.getTrsNo();

            msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(ChequePurchaseHead.substring(2, 4)), ChequePurchaseHead, 0, amount, todayDt, todayDt, 2,
                    "Cheque Purchase Realisation for Bill No." + bcBillNo, enterBy, trsNo,
                    "", ftsPostingFacade.getRecNo(), "Y", enterBy, 0, 3, "", todayDt, 0f, "", "", 0, "",
                    0f, "", null, branch, branch, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(ChequePurchaseHead.substring(2, 4)), ChequePurchaseHead, amount, 0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            msg = ftsPostingFacade.insertRecons(ftsPostingFacade.getAcNatureByCode(outwardChequeCollectionHead.substring(2, 4)), outwardChequeCollectionHead, 1, amount, todayDt, todayDt, 2,
                    "Cheque Purchase Realisation for Bill No." + bcBillNo, enterBy, trsNo,
                    "", ftsPostingFacade.getRecNo(), "Y", enterBy, 0, 3, "", todayDt, 0f, "", "", 0, "",
                    0f, "", null, branch, branch, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsPostingFacade.updateBalance(ftsPostingFacade.getAcNatureByCode(outwardChequeCollectionHead.substring(2, 4)), outwardChequeCollectionHead, 0, amount, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            int updateChequeData = em.createNativeQuery("UPDATE cheque_purchase SET RealisationFlag='Y',RealisationDt=now(),RealisationBy='" + enterBy + "' WHERE BcBillNo = '" + bcBillNo + "'").executeUpdate();
            if (updateChequeData <= 0) {
                throw new ApplicationException("Data updation problem !");
            }

            ut.commit();
            return "true" + trsNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getBcBillNoRealisationN(String branch) throws ApplicationException {
        try {
            return em.createNativeQuery("select BcBillNo from cheque_purchase where RealisationFlag = 'N' and substring(AccountNo,1,2)='" + branch + "' order by BcBillNo").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}