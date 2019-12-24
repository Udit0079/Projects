/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.common;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.ChargesObject;
import com.cbs.dto.LockerRentDetail;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Shipra Gupta Modify By Dhirendra Singh
 */
@Stateless(mappedName = "FtsBulkPostingFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class FtsBulkPostingFacade implements FtsBulkPostingFacadeRemote {

    @EJB
    private FtsPostingMgmtFacadeRemote fts;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat mdy = new SimpleDateFormat("MMM dd yyyy");

    public FtsBulkPostingFacade() {
    }

    public String ftsPostBulkDrCr(List<ChargesObject> chargesList, String glAcc, String user, String acType, String desc,
            Integer tranDesc, Integer tyFlag, String taxFlag, String exceedBalFlag, String dt, String enterBy,
            String orgnBrCode) throws ApplicationException {
        String msg = "", mainDetails = null;
        try {
            String acNat = fts.getAcNatureByCode(acType);
            Map<String, Double> map = new HashMap<String, Double>();
            if (glAcc == null) {
                return msg = "Glacc cannot be blank";
            }
            if (user == null) {
                return msg = "User cannot be blank";
            }
            if (acNat == null) {
                return msg = "Account nature cannot be blank";
            }
            if (desc == null) {
                return msg = "Desc cannot be blank";
            }
            if (tranDesc == null) {
                return msg = "Trandesc cannot be blank";
            }
            if (tyFlag == null) {
                return msg = "Tyflag cannot be blank";
            }
            if (taxFlag == null) {
                return msg = "Taxflag cannot be blank";
            }
            if (exceedBalFlag == null) {
                return msg = "Exceedbalflag cannot be blank";
            }
            if (enterBy == null) {
                return msg = "Enterby field cannot be blank";
            }
            // comment for Centralize Mimimum balance charge Software Bug #36135
//            String datevalidate = fts.ftsDateValidate(dt, orgnBrCode);
//            if (!(datevalidate.equalsIgnoreCase("True"))) {
//                return msg = "Please check the date you have passed";
//            }
            
            String userValidateMsg = fts.ftsUserValidate(user, orgnBrCode);
            if (!(userValidateMsg.equalsIgnoreCase("True"))) {
                return userValidateMsg;
            }

            List glHeadExistList = em.createNativeQuery("SELECT * FROM gltable WHERE ACNO ='" + glAcc + "' AND POSTFLAG NOT IN(99,1,2)").getResultList();
            if (glHeadExistList.size() <= 0) {
                return msg = "GL HEAD NOT DEFINED";
            }
            double totalTaxAmt = 0;
            boolean isNpaChargesPost = false;
            List npaChargesPostingList = em.createNativeQuery("SELECT ifnull(code,0) FROM parameterinfo_report WHERE reportname='NPA CHARGES POSTING'").getResultList();
            if (!npaChargesPostingList.isEmpty()) {
                Vector npaChargesPostVect = (Vector) npaChargesPostingList.get(0);
                if (npaChargesPostVect.get(0).toString().equals("1")) {
                    isNpaChargesPost = true;
                } else {
                    isNpaChargesPost = false;
                }
            }
            Float trSNo = fts.getTrsNo();
            //String tranTime = daybeginDate(orgnBrCode);

            double penalty1 = 0d;
            double amt = 0d;
            double ttlTaxAmt = 0d;
            double ttlTaxAmtIgst = 0d;

            double penalty = 0d;
            //String taxGlAcNo = "";
//            double taxAmt = 0d;
            String taxName = "";
            int ty = 0;
            int iy = 1;
            ty = 0;
            double sPerc = 0;
            int rUpTo = 0;
            double sPercIgst = 0;
            int rUpToIgst = 0;
            if (taxFlag.equalsIgnoreCase("Y")) {
                map = interFts.getTaxComponentSlab(dt);
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                    rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
                map = interFts.getIgstTaxComponentSlab(dt);
                Set<Entry<String, Double>> set1 = map.entrySet();
                Iterator<Entry<String, Double>> it1 = set1.iterator();
                while (it1.hasNext()) {
                    Entry entry = it1.next();
                    sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                    rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
            }

            for (ChargesObject chargesObj : chargesList) { // for
                String acNo = chargesObj.getAcNo();
//                System.out.println("acNo" + acNo);
                penalty = chargesObj.getPenalty();
                String custState = chargesObj.getCustState();
                String branchState = chargesObj.getBrState();
//                if(custState.equalsIgnoreCase("0")||custState.equalsIgnoreCase("")){
//                    custState = branchState;
//                }
                int optStatus = chargesObj.getOptStatus();

                int trans = chargesObj.getTrans();

                int minBal = chargesObj.getMinbal();
                String custName = chargesObj.getCustName();
                int stat = Integer.parseInt(chargesObj.getStatus());
                String acNature = acNature(fts.getAccountCode(chargesObj.getAcNo()));

                double tPenalty = penalty;
                float recNo = 0f;
                double taxAmt = 0d;
                double taxAmtIgst = 0d;
                if (penalty > 0) {
                    if (stat != 11 && stat != 12 && stat != 13) {
                        if (tranDesc == 25 && minBal == 2) {
                            msg = ftsInsertPending(acNo, "NOT APPLICABLE", penalty, trans, optStatus, custName, recNo,
                                    trSNo, enterBy, user, tranDesc, desc, orgnBrCode, dt);
                            if (!msg.equalsIgnoreCase("true")) {
                                msg = "Date Couldn't be inserted in NPA_ENTRY";
                            }
                        }
                        if (taxFlag.equalsIgnoreCase("Y")) {

                            if (custState.equalsIgnoreCase(branchState)) {
                                taxAmt = CbsUtil.round(((penalty * sPerc) / 100), rUpTo);
                                ttlTaxAmt = ttlTaxAmt + taxAmt;
                                tPenalty = penalty + taxAmt;
                            } else {
                                taxAmtIgst = CbsUtil.round(((penalty * sPercIgst) / 100), rUpToIgst);
                                ttlTaxAmtIgst = ttlTaxAmtIgst + taxAmtIgst;
                                tPenalty = penalty + taxAmtIgst;
                                System.out.println("acNo:" + acNo + "; Igst:" + taxAmtIgst + "; Total IGST" + ttlTaxAmtIgst);
                            }
                        }
                        if (tyFlag == 1) {
                            msg = fts.checkBalance(acNo, tPenalty, enterBy);
                        }

                        if (msg.equalsIgnoreCase("True")) { // In case of sufficient fund.
                            if (taxFlag.equalsIgnoreCase("Y")) {
                                recNo = fts.getRecNo();
                                String taxDetail = desc + ":" + (custState.equalsIgnoreCase(branchState) ? "CGST:SGST" : "IGST");
                                msg = fts.insertRecons(acNature, acNo, tyFlag, (custState.equalsIgnoreCase(branchState) ? taxAmt : taxAmtIgst), dt, dt, 2, taxDetail, enterBy, trSNo, null,
                                        recNo, "Y", user, 71, 3, null, null, (float) 0, null, "A", iy, null, null, null, null,
                                        orgnBrCode, orgnBrCode, 0, null, "", "");
                                if (!msg.equalsIgnoreCase("True")) {
                                    return "False";
                                }
                            }
                            recNo = fts.getRecNo();

                            msg = fts.insertRecons(acNature, acNo, tyFlag, penalty, dt, dt, 2, desc, enterBy, trSNo, null, recNo, "Y", user, tranDesc,
                                    3, null, null, (float) 0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }
                            penalty1 = penalty1 + penalty;
                            double crAmt = 0d, drAmt = 0d;
                            if (tyFlag == 0) {
                                crAmt = tPenalty;
                                drAmt = (float) 0;
                            } else {
                                crAmt = (float) 0;
                                drAmt = tPenalty;
                            }
                            msg = fts.updateBalance(acNature, acNo, crAmt, drAmt, "Y", "Y");
                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }
                        } else { // In case of insufficient fund.
                            //recNo = fts.getRecNo(acNo.substring(0, 2));
                            recNo = fts.getRecNo();

                            msg = ftsInsertPending(acNo, "INSUFFICIENT FUND", penalty, trans, optStatus, custName, recNo, trSNo, enterBy, user, tranDesc, desc, orgnBrCode, dt);

                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }
                            if (taxFlag.equalsIgnoreCase("Y")) {
                                ttlTaxAmt = ttlTaxAmt - taxAmt;
                                if (ttlTaxAmtIgst != 0 && !(custState.equalsIgnoreCase(branchState))) {
                                    ttlTaxAmtIgst = ttlTaxAmtIgst - taxAmtIgst;
                                }
                            }
                        }
                    } else {
                        if (isNpaChargesPost) {
                            recNo = fts.getRecNo();

                            msg = ftsNpaEntry(acNo, tyFlag, penalty, desc, enterBy, user, optStatus, trSNo, recNo,
                                    tranDesc, trans, custName, orgnBrCode);
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Date Couldn't be inserted in NPA_ENTRY");
                            }
                            totalTaxAmt = totalTaxAmt + penalty;
                        }
                    }
                }// End pently >= 0 condition
                amt = penalty1;
            } // End for loop
            // }//
            if (totalTaxAmt > 0) {
                contraEntryUriOirHead(acType, totalTaxAmt, orgnBrCode, desc, dt, tranDesc, trSNo, user);
            }
            if (penalty1 > 0) {
                float recNo = 0f;
                String acNature = "";
                String taxDesc = taxName + desc;
                double crAmt = 0d, drAmt = 0d;
                double totalGstAmt = 0;

                if (taxFlag.equalsIgnoreCase("Y")) {
                    double sTaxAmt = CbsUtil.round(((ttlTaxAmt * 100) / sPerc), rUpTo);
                    map = interFts.getTaxComponent(sTaxAmt, dt);
                    Set<Entry<String, Double>> set1 = map.entrySet();

                    Iterator<Entry<String, Double>> it1 = set1.iterator();
                    String taxHead = "";
                    while (it1.hasNext()) {
                        Entry entry = it1.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        String description = keyArray[0];
                        taxHead = orgnBrCode + keyArray[1];
                        
                        mainDetails = description.toUpperCase() + " " + desc;
                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                        totalGstAmt = totalGstAmt + taxAmount;
                        acNature = acNature(fts.getAccountCode(taxHead));
                        //taxDesc = taxName + desc;
                        recNo = fts.getRecNo();

                        msg = fts.insertRecons(acNature, taxHead, 0, taxAmount, dt, dt, 2, mainDetails, enterBy, trSNo, null, recNo, "Y", user,
                                71, 3, null, null, (float) 0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                        if (!msg.equalsIgnoreCase("True")) {
                            return "False";
                        }

                        crAmt = taxAmount;
                        drAmt = (float) 0;
                        msg = fts.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                        if (!msg.equalsIgnoreCase("True")) {
                            return "False";
                        }
                    }
                    ttlTaxAmt = CbsUtil.round(ttlTaxAmt, rUpTo);
                    if (ttlTaxAmt != totalGstAmt) {
                        double serAmount = ttlTaxAmt - totalGstAmt;
                        serAmount = CbsUtil.round(serAmount, rUpTo);
                        int drCr = 0;
                        if (serAmount < 0) {
                            drCr = 1;
                            serAmount = Math.abs(serAmount);
                        }
                        recNo = recNo + 1;
                        msg = fts.insertRecons("PO", taxHead, drCr, serAmount, dt, dt, 2, "Difference amount for Charge batch", enterBy, trSNo, null, recNo, "Y",
                                user, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
                                "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }

                        msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }

                    if (ttlTaxAmtIgst != 0) {
                        double sTaxAmtIgst = CbsUtil.round(((ttlTaxAmtIgst * 100) / sPercIgst), rUpToIgst);
                        map = interFts.getIgstTaxComponent(sTaxAmtIgst, dt);
                        Set<Entry<String, Double>> setIgst = map.entrySet();
                        Iterator<Entry<String, Double>> itIgst = setIgst.iterator();
                        while (itIgst.hasNext()) {
                            Entry entry = itIgst.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            taxHead = orgnBrCode + keyArray[1];
                            mainDetails = description.toUpperCase() + " " + desc;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            acNature = acNature(fts.getAccountCode(taxHead));
                            //taxDesc = taxName + desc;
                            recNo = fts.getRecNo();

                            msg = fts.insertRecons(acNature, taxHead, 0, taxAmount, dt, dt, 2, mainDetails, enterBy, trSNo, null, recNo, "Y", user,
                                    71, 3, null, null, (float) 0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }

                            crAmt = taxAmount;
                            drAmt = (float) 0;
                            msg = fts.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }
                        }
                    }
                }

                //acNature = acNature(glAcc.substring(2, 4));
                acNature = acNature(fts.getAccountCode(glAcc));
                msg = fts.insertRecons(acNature, glAcc, ty, amt, dt, dt, 2, taxDesc, enterBy, trSNo, null, recNo, "Y", user,
                        tranDesc, 3, null, null, (float) 0.0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                if (!msg.equalsIgnoreCase("True")) {
                    return "False";
                }
                if (ty == 0) {
                    crAmt = amt;
                    drAmt = (float) 0;
                } else {
                    crAmt = (float) 0;
                    drAmt = amt;
                }
                msg = fts.updateBalance(acNature, glAcc, crAmt, drAmt, "Y", "Y");
                if (!msg.equalsIgnoreCase("True")) {
                    return "False";
                }
            }
            return msg + ":" + trSNo;
        } catch (ApplicationException | NumberFormatException e) {
            e.printStackTrace();
            throw new ApplicationException("Transaction is not saved " + e.getMessage());
        }
    }

    public String ftsInsertPending(String Acno, String status, Double penalty, float trans, int optStatus, String Custname,
            Float recNo, Float trsNo, String enterBy, String user, Integer transDecs, String description, String orgnBrCode, String entryDate) {
        try {
            if (Acno.equals("")) {
                return "Acno Can Not Be Null";
            }
            if (status.equals("")) {
                return "Status Can Not Be Null";
            }
            if (status.equalsIgnoreCase("INSUFFICIENT FUND")) {
                if (penalty == null && penalty != 0) {
                    return "Penalty Can Not Be Null";
                }
                if (recNo == null && recNo != 0) {
                    return "Recno Can Not Be Null";
                }
                if (trsNo == null && trsNo != 0) {
                    return "Trsno Can Not Be Null";
                }
                if (enterBy.equals("")) {
                    return "EnterBy Can Not Be Null";
                }
                if (transDecs == null && transDecs != 0) {
                    return "TranDesc Can Not Be Null";
                }
                Query insertBillLost = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)"
                        + " values('" + Acno + "',DATE_FORMAT(curdate(),'%Y%m%d')," + penalty + ",'INSUFFICIENT FUND','1','2',"
                        + "" + recNo + "," + trsNo + ",'" + enterBy + "','Y','" + user + "','" + transDecs + "','" + description + "')");
                Integer insertBillLostVarient = insertBillLost.executeUpdate();
                if (insertBillLostVarient <= 0) {
                    return "25_Unable To Insert In Table PendingCharges";
                }
            }
            return "true";
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String ftsNpaEntry(String acno, Integer ty, double penalty, String desc, String enterBy, String user, Integer optStatus,
            Float trsno, Float recno, Integer tranDesc, int trans, String custName, String orgnBrCode) throws ApplicationException {
        try {
            Integer varinsertNapReconList = em.createNativeQuery("INSERT into npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,"
                    + "ENTERBY,AuthBy,AUTH,iy,Payby,trsno,RecNo,tranDesc,intamt,org_brnid,dest_brnid)values ('" + acno + "'," + ty + ",DATE_FORMAT(curdate(),'%Y%m%d'),DATE_FORMAT(curdate(),'%Y%m%d'),"
                    + "" + penalty + ",2,'" + desc + "','" + enterBy + "','" + user + "','Y'," + optStatus + ",3," + trsno + "," + recno + "," + tranDesc + ",0,'"
                    + orgnBrCode + "','" + fts.getCurrentBrnCode(acno) + "')").executeUpdate();

            if (varinsertNapReconList <= 0) {
                throw new ApplicationException();
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
            if (dateList.size() <= 0) {
                throw new ApplicationException("Check the Day Begin/Day End");
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String acNature(String acType) throws ApplicationException {
        try {
            List acNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = (String) acNatureVect.get(0);
            return acNature;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//    public String taxCalculation(Double Amt, String Dt, String orgnBrCode) {
//        try {
//            Float taxChg = 0.0f;
//            Float taxAmt = 0.0f;
//            String taxDes = "";
//            String taxGlAcno = "";
//
//            List resultList = fnTaxApplicableROT(Dt);
//            if (resultList.size() > 0) {
//                Vector resultListV = (Vector) resultList.get(0);
//                String taxType = resultListV.get(0).toString();
//                taxGlAcno = resultListV.get(3).toString();
//                String taxApplyOn = resultListV.get(2).toString();
//
//                List chk1 = em.createNativeQuery("select minAmt,maxamt from taxmaster where type='" + taxType + "' and ApplicableFlag='Y'"
//                        + " and ApplicableDt in (select max(ApplicableDt) from taxmaster "
//                        + " where type='" + taxType + "' and ApplicableFlag='Y')").getResultList();
//                Float amtMin = 0.0f;
//                Float amtMax = 0.0f;
//                if (!chk1.isEmpty()) {
//                    Vector chk1V = (Vector) chk1.get(0);
//                    amtMin = Float.parseFloat(chk1V.get(0).toString());
//                    amtMax = Float.parseFloat(chk1V.get(1).toString());
//                }
//                if (taxApplyOn.equalsIgnoreCase("C")) {
//                    List chk2 = em.createNativeQuery("Select (" + Amt + " * ROT)/100  from taxmaster where type='" + taxType + "' and ApplicableFlag='Y'"
//                            + " and ApplicableDt in (select max(ApplicableDt) from taxmaster "
//                            + " where type='" + taxType + "' and ApplicableFlag='Y')").getResultList();
//                    if (!chk2.isEmpty()) {
//                        Vector chk2V = (Vector) chk2.get(0);
//                        taxChg = Float.parseFloat(chk2V.get(0).toString());
//                    }
//                }
//                if (taxApplyOn.equalsIgnoreCase("T")) {
//                    List chk2 = em.createNativeQuery("Select (" + taxAmt + " * ROT)/100  from taxmaster where type='" + taxType + "' and ApplicableFlag='Y'"
//                            + " and ApplicableDt in (select max(ApplicableDt) from taxmaster "
//                            + " where type='" + taxType + "' and ApplicableFlag='Y')").getResultList();
//                    if (!chk2.isEmpty()) {
//                        Vector chk2V = (Vector) chk2.get(0);
//                        taxChg = Float.parseFloat(chk2V.get(0).toString());
//                    }
//                }
//                if (taxChg < amtMin) {
//                    taxChg = amtMin;
//                } else if (taxChg > amtMax) {
//                    taxChg = amtMax;
//                }
//                taxAmt = taxChg;
//                List chk3 = em.createNativeQuery("select ACNAME,ACNO from gltable where substring(ACNO,3,8)='"
//                        + taxGlAcno + "' and substring(ACNO,1,2) ='" + orgnBrCode + "'").getResultList();
//
//                if (chk3.size() > 0) {
//                    Vector chk3V = (Vector) chk3.get(0);
//                    taxDes = chk3V.get(0).toString();
//                    taxGlAcno = chk3V.get(1).toString();
//                }
//            }
//            return taxGlAcno + "~`~" + taxChg.toString() + "~`~" + taxDes.toString();
//        } catch (Exception ex) {
//            throw new EJBException("Transaction failed: " + ex.getMessage());
//        }
//    }
//    public List fnTaxApplicableROT(String appDT) {
//        List resultList = null;
//        try {
//            resultList = em.createNativeQuery("select TYPE,ROT,ROTApplyOn,glhead from taxmaster where ApplicableDt<='"
//                    + appDT + "' and applicableFlag='Y' and Auth='Y'").getResultList();
//            return resultList;
//        } catch (Exception ex) {
//            throw new EJBException("Transaction failed: " + ex.getMessage());
//        }
//    }
    public String postInciCharges(List<ChargesObject> chargesList, String glAcc, String fromDt, String toDt, String user, String acType, String desc,
            Integer tranDesc, String brCode, String enterBy, String entryDate) {
        try {
            double penalty = 0d;
            double penalty1 = 0d;
            Float recno = 0f;
            float trsno = fts.getTrsNo();
            double totalChargesAmt = 0;
            boolean isNpaChargesPost = false;
            List npaChargesPostingList = em.createNativeQuery("SELECT ifnull(code,0) FROM parameterinfo_report WHERE reportname='NPA CHARGES POSTING'").getResultList();
            if (!npaChargesPostingList.isEmpty()) {
                Vector npaChargesPostVect = (Vector) npaChargesPostingList.get(0);
                if (npaChargesPostVect.get(0).toString().equals("1")) {
                    isNpaChargesPost = true;
                } else {
                    isNpaChargesPost = false;
                }
            }
            String acNat = fts.getAcNatureByCode(acType);

            if (chargesList.size() > 0) {
                for (ChargesObject chargesObj : chargesList) {
                    String acno = chargesObj.getAcNo();
                    penalty = chargesObj.getPenalty();
                    float limit = chargesObj.getLimit();

                    int optStatus = chargesObj.getOptStatus();
                    int trans = chargesObj.getTrans();
                    String status = chargesObj.getStatus();
                    int minbal = chargesObj.getMinbal();
                    String custname = chargesObj.getCustName();

                    if (Integer.parseInt(status) != 11 && Integer.parseInt(status) != 12 && Integer.parseInt(status) != 13) {
                        if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            double net = 0d;
                            List balanceList = em.createNativeQuery("select ifnull(balance,0) from reconbalan where acno='" + acno + "'").getResultList();
                            if (balanceList.size() > 0) {
                                Vector balanceVect = (Vector) balanceList.get(0);
                                net = Float.parseFloat(balanceVect.get(0).toString());
                            }
                            if (penalty >= 0) {
                                if (tranDesc == 25 && minbal == 2) {
                                    Integer MinchargeStatus1 = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,"
                                            + "Trans,OptStatus,custname) values('" + acno + "','Not Applicable'," + penalty + "," + trans + ","
                                            + "" + optStatus + ",'" + custname + "')").executeUpdate();
                                    if (MinchargeStatus1 <= 0) {
                                        return "Data is not inserted into MinchargeStatus";
                                    }
                                } else if (Math.signum(net - penalty) <= 0) {
                                    //recno = fts.getRecNo(acno.substring(0, 2));
                                    recno = fts.getRecNo();
                                    Integer MinchargeStatus2 = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,"
                                            + "OptStatus,custname) values('" + acno + "','INSUFFICIENT FUND'," + penalty + "," + trans + ","
                                            + "" + optStatus + ",'" + custname + "')").executeUpdate();
                                    if (MinchargeStatus2 <= 0) {
                                        return "Data is not inserted into MinchargeStatus";
                                    }
                                    Integer PendingCharges = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,"
                                            + "trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)values('" + acno + "',DATE_FORMAT(curdate(),'%Y%m%d')," + penalty + ",'INSUFFICIENT FUND',1,2," + recno + "," + trsno + ",'" + enterBy + "','Y'," + "'" + user + "'," + tranDesc + ",'" + desc + "')").executeUpdate();
                                    if (PendingCharges <= 0) {
                                        return "Data is not inserted into PendingCharges";
                                    }
                                } else if (Math.signum(net - penalty) == 1) {
                                    //recno = fts.getRecNo(acno.substring(0, 2));
                                    recno = fts.getRecNo();
                                    Integer PendingCharges = em.createNativeQuery("INSERT into recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,"
                                            + "ENTERBY,AuthBy,AUTH,iy,Payby,TrsNo,RecNo,TranDesc,org_brnid,dest_brnid)Values ('" + acno + "',1,DATE_FORMAT(curdate(),'%Y%m%d'),DATE_FORMAT(curdate(),'%Y%m%d'),"
                                            + penalty + ",2,'" + desc + "','" + enterBy + "','" + user + "','Y',1,3,"
                                            + "" + trsno + "," + recno + "," + tranDesc + ",'" + brCode + "','" + brCode + "')").executeUpdate();
                                    if (PendingCharges <= 0) {
                                        return "Data is not inserted into PendingCharges";
                                    }
                                    penalty1 = penalty1 + penalty;
                                    List balance1List = em.createNativeQuery("SELECT BALANCE FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                                    if (!balance1List.isEmpty()) {
                                        int reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=ifnull(BALANCE,0)-"
                                                + "ifnull(" + penalty + ",0),DT=DATE_FORMAT(curdate(),'%Y%m%d') WHERE ACNO='" + acno + "'").executeUpdate();
                                        if (reconBalan <= 0) {
                                            return "Data is not RECONBALAN into RECON BALAN";
                                        }
                                    } else {
                                        int reconBalan = em.createNativeQuery("Insert reconbalan (acno,Dt,balance) values('" + acno + "',DATE_FORMAT(curdate(),'%Y%m%d'),-" + penalty + ")").executeUpdate();
                                        if (reconBalan <= 0) {
                                            return "Data is not inserted into RECON BALAN";
                                        }
                                    }
                                    int reconBalan = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,"
                                            + "OptStatus,custname) values('" + acno + "','POSTED'," + penalty + "," + trans + "," + optStatus + ","
                                            + "'" + custname + "')").executeUpdate();
                                    if (reconBalan <= 0) {
                                        return "Data is not inserted into RECON BALAN";
                                    }
                                }
                            }
                        }
                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            if (penalty >= 0) {
                                double bal = 0d;
                                List balanceList = em.createNativeQuery("select Balance from ca_reconbalan where acno='" + acno + "'").getResultList();
                                if (!balanceList.isEmpty()) {
                                    Vector balanceVect = (Vector) balanceList.get(0);
                                    bal = Float.parseFloat(balanceVect.get(0).toString());
                                }
                                if (tranDesc == 25 && minbal == 2) {
                                    int reconBalan = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,"
                                            + "OptStatus,custname) values('" + acno + "','POSTED'," + penalty + "," + trans + "," + optStatus + ","
                                            + "'" + custname + "')").executeUpdate();
                                    if (reconBalan <= 0) {
                                        return "Data is not inserted into RECON BALAN";
                                    }
                                } else if (bal - penalty > -(limit) || Math.signum(bal) == -1) {
                                    //recno = fts.getRecNo(acno.substring(0, 2));
                                    recno = fts.getRecNo();

                                    penalty1 = penalty1 + penalty;
                                    int caRecon = em.createNativeQuery("INSERT into ca_recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,"
                                            + "ENTERBY,AuthBy,AUTH,iy,Payby,trsno,RecNo,tranDesc,org_brnid,dest_brnid)values ('" + acno + "',1,DATE_FORMAT(curdate(),'%Y%m%d'),DATE_FORMAT(curdate(),'%Y%m%d'),"
                                            + penalty + ",2,'" + desc + "','" + enterBy + "','" + user + "','Y'," + optStatus + ","
                                            + "3," + trsno + "," + recno + "," + tranDesc + ",'" + brCode + "','" + brCode + "')").executeUpdate();
                                    if (caRecon <= 0) {
                                        return "Data is not inserted into CA_RECON";
                                    }
                                    int minCharge = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,"
                                            + "OptStatus,custname) values('" + acno + "','POSTED'," + penalty + "," + trans + "," + optStatus + ","
                                            + "'" + custname + "')").executeUpdate();
                                    if (minCharge <= 0) {
                                        return "Data is not inserted into minCharge_Status";
                                    }
                                    List balanceList3 = em.createNativeQuery("SELECT BALANCE FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                                    if (!balanceList3.isEmpty()) {
                                        int caReconBalan = em.createNativeQuery("UPDATE ca_reconbalan SET BALANCE=ifnull(BALANCE,0)-"
                                                + " ifnull(" + penalty + ",0), DT=DATE_FORMAT(curdate(),'%Y%m%d') WHERE ACNO='" + acno + "'").executeUpdate();
                                        if (caReconBalan <= 0) {
                                            return "Data is not UPDATED into CA_RECONBALAN.";
                                        }
                                    } else {
                                        int caReconBalan = em.createNativeQuery("Insert reconbalan (acno,Dt,balance) "
                                                + "values('" + acno + "',DATE_FORMAT(curdate(),'%Y%m%d'),-" + penalty + ")").executeUpdate();

                                        if (caReconBalan <= 0) {
                                            return "Data is not inserted into CA_RECONBALAN.";
                                        }
                                    }
                                } else {
                                    //recno = fts.getRecNo(acno.substring(0, 2));
                                    recno = fts.getRecNo();
                                    int minCharge1 = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,"
                                            + "OptStatus,custname) values('" + acno + "','INSUFFICIENT FUND'," + penalty + "," + penalty + ","
                                            + "" + optStatus + ",'" + custname + "')").executeUpdate();
                                    if (minCharge1 <= 0) {
                                        return "Data is not inserted into minCharge_Status.";
                                    }
                                    int pendingCharges = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,"
                                            + "trantype,recno,trsno,enterby,auth,authby,Trandesc,charges) values('" + acno + "',DATE_FORMAT(curdate(),'%Y%m%d')," + penalty + ",'INSUFFICIENT FUND',1,2," + recno + "," + trsno + ",'" + enterBy + "','Y','" + user + "'," + tranDesc + ",'" + desc + "')").executeUpdate();
                                    if (pendingCharges <= 0) {
                                        return "Data is not inserted into PendingCharges.";
                                    }
                                }
                            }
                        } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            if (penalty > 0) {
                                //recno = fts.getRecNo(acno.substring(0, 2));
                                recno = fts.getRecNo();
                                int pendingCharges = em.createNativeQuery("INSERT into loan_recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,"
                                        + "ENTERBY,AuthBy,AUTH,iy,Payby,trsno,RecNo,tranDesc,org_brnid,dest_brnid)values ('" + acno + "',1,DATE_FORMAT(curdate(),'%Y%m%d'),DATE_FORMAT(curdate(),'%Y%m%d'),"
                                        + penalty + ",2,'" + desc + "','" + enterBy + "','" + user + "','Y'," + optStatus + ",3,"
                                        + "" + trsno + "," + recno + "," + tranDesc + ",'" + brCode + "','" + brCode + "')").executeUpdate();
                                if (pendingCharges <= 0) {
                                    return "Data is not inserted into PendingCharges.";
                                }
                                int minCharge3 = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,OptStatus,"
                                        + "custname) values('" + acno + "','POSTED'," + penalty + "," + trans + "," + optStatus + ",'" + custname + "')").executeUpdate();

                                if (minCharge3 <= 0) {
                                    return "Data is not inserted into minCharge_Status.";
                                }
                                penalty1 = penalty1 + penalty;
                            }
                        }
                    } else {
                        if (isNpaChargesPost) {
                            recno = fts.getRecNo();
                            int npaRecon = em.createNativeQuery("INSERT into npa_recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,ENTERBY,AuthBy,"
                                    + "AUTH,iy,Payby,trsno,RecNo,tranDesc,intamt,org_brnid,dest_brnid)values ('" + acno + "',1,DATE_FORMAT(curdate(),'%Y%m%d'),DATE_FORMAT(curdate(),'%Y%m%d')," + penalty + ",2,'" + desc + "',"
                                    + "'" + enterBy + "','" + user + "','Y'," + optStatus + ",3," + trsno + "," + recno + "," + tranDesc + ",0,'" + brCode + "','" + brCode + "')").executeUpdate();
                            if (npaRecon <= 0) {
                                return "Data is not inserted into Npa recon.";
                            }
                            totalChargesAmt = totalChargesAmt + penalty;
                            int minCharge3 = em.createNativeQuery("insert into mincharge_status(acno,status,penalty,Trans,OptStatus,"
                                    + "custname) values('" + acno + "','NPA Account'," + penalty + "," + trsno + "," + optStatus + ",'" + custname + "')").executeUpdate();
                            if (minCharge3 <= 0) {
                                return "Data is not inserted into minCharge_Status.";
                            }
                        }
                    }
                }
            }
            if (totalChargesAmt > 0) {
                String details = "Charges From " + mdy.format(ymd.parse(fromDt)) + " to " + mdy.format(ymd.parse(toDt)) + " on NPA of " + acType;
                contraEntryUriOirHead(acType, totalChargesAmt, brCode, details, toDt, tranDesc, trsno, user);
            }
            if (penalty1 >= 0) {
                recno = fts.getRecNo();
                int npaRecon = em.createNativeQuery("INSERT gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,TRANTYPE,DETAILS,ENTERBY,AUTH,Authby,iy,Payby,"
                        + "TrsNo,RecNo,TranDesc,org_brnid,dest_brnid)VALUES('" + glAcc + "',0,DATE_FORMAT(curdate(),'%Y%m%d'),DATE_FORMAT(curdate(),'%Y%m%d')," + penalty1 + ",2,'" + desc + "',"
                        + "'" + enterBy + "','Y','" + user + "',1,3," + trsno + "," + "" + recno + "," + tranDesc + ",'" + brCode + "','"
                        + brCode + "')").executeUpdate();
                if (npaRecon <= 0) {
                    return "Data is not inserted into NpaRECON.";
                }
                List balanceList = em.createNativeQuery("SELECT BALANCE FROM reconbalan WHERE ACNO='" + glAcc + "'").getResultList();
                if (!balanceList.isEmpty()) {
                    int reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=ifnull(BALANCE,0) + ifnull(" + penalty1 + ",0),"
                            + "DT=DATE_FORMAT(curdate(),'%Y%m%d') WHERE ACNO='" + glAcc + "'").executeUpdate();
                    if (reconBalan <= 0) {
                        return "Data is not UPDATED into RECONBALAN.";
                    }
                } else {
                    int reconBalan = em.createNativeQuery("Insert reconbalan (acno,Dt,balance) values('" + glAcc + "',DATE_FORMAT(curdate(),'%Y%m%d')," + penalty1 + ")").executeUpdate();
                    if (reconBalan <= 0) {
                        return "Data is not inserted into RECONBALAN.";
                    }
                }
            }
            return "true:" + trsno;
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String contraEntryUriOirHead(String acType, double chargesAmt, String brnCode, String details, String toDt, int tranDesc, float trsNo, String authBy) throws ApplicationException {
        try {
            String uriGl = "";
            String oirHead = "";
            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for " + acType);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                //    glAcNo = brnCode + glHeadVect.get(0).toString() + "01";
                uriGl = glHeadVect.get(1).toString();
                oirHead = glHeadVect.get(2).toString();
            }

            if (!oirHead.equals("") && !uriGl.equals("")) {
                uriGl = brnCode + uriGl + "01";
                oirHead = brnCode + oirHead + "01";
                float recNo = fts.getRecNo();
                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                        + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "'," + chargesAmt + ",0,2," + tranDesc + ",3,'"
                        + details + "','system','Y','" + authBy + "'," + trsNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                if (insertReconBalan == 0) {
                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                }
                recNo = fts.getRecNo();
                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                        + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0," + chargesAmt + ",2," + tranDesc + ",3,'"
                        + details + "','system','Y','" + authBy + "'," + trsNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                insertReconBalan = insertReconBalanQuery.executeUpdate();
                if (insertReconBalan == 0) {
                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                }

                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + chargesAmt + " WHERE ACNO= '" + oirHead + "'");
                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                if (updateReconBalan == 0) {
                    throw new ApplicationException("Value doesn't updated in reconbalan");
                }

                Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + chargesAmt + " WHERE ACNO= '" + uriGl + "'");
                Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                if (updateReconBalanUri == 0) {
                    throw new ApplicationException("Value doesn't updated in reconbalan");
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<List> postLockerRent(List<LockerRentDetail> chargesList, String glAcc, String user, String desc,
            Integer tranDesc, String taxFlag, String exceedBalFlag, String orgnBrCode) {
        String msg = "true";
        List<List> returnList = new ArrayList<List>();
        List<String> strList = new ArrayList<String>();
        List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            List glHeadExistList = em.createNativeQuery("SELECT * FROM gltable WHERE ACNO ='" + glAcc + "' AND POSTFLAG "
                    + "NOT IN(99,1,2)").getResultList();
            if (glHeadExistList.size() <= 0) {
                strList.add("Gl Acc No. Do Not Exist In GLTable");
                returnList.add(strList);
                returnList.add(smsList);
                return returnList;
//                return "Gl Acc No. Do Not Exist In GLTable";
            }
            Float trSNo = fts.getTrsNo();

            Date dt1 = new Date();
            String dt = ymd.format(dt1);
            double penalty1 = 0d;
            double amt = 0d;
            double ttlTaxAmt = 0d, ttlTaxAmtIgst = 0d;
            //String taxGlAcNo = "";
            double taxAmt = 0d, taxAmtIgst = 0d;
            String taxName = "";
            //int taxRoundUpTo = 0;

//            List checkList = em.createNativeQuery("SELECT ROUNDUPTO FROM taxmaster WHERE  UPPER(APPLICABLEFLAG)='Y' AND UPPER(AUTH)='Y' "
//                    + "AND APPLICABLEDT<=DATE_FORMAT(curdate(),'%Y%m%d') limit 1").getResultList();
//            if (checkList.size() > 0) {
//                Vector checkListVector = (Vector) checkList.get(0);
//                String abc = checkListVector.get(0).toString();
//                taxRoundUpTo = Integer.parseInt(abc);
//            }
            double sPerc = 0, sPercIgst = 0;
            int rUpTo = 0, rUpToIgst = 0;
            if (taxFlag.equalsIgnoreCase("Y")) {
                map = interFts.getTaxComponentSlab(dt);
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                    rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
                map = interFts.getIgstTaxComponentSlab(dt);
                Set<Entry<String, Double>> set1 = map.entrySet();
                Iterator<Entry<String, Double>> it1 = set1.iterator();
                while (it1.hasNext()) {
                    Entry entry = it1.next();
                    sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                    rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
            }

            String result = cbsDestEntryIdentification(chargesList, orgnBrCode);
            if (result.equalsIgnoreCase("true")) {  //Destination Branch Case.
                for (LockerRentDetail chargesObj : chargesList) { // for
                    String acNo = chargesObj.getAcno();
                    System.out.println("A/c No Is-->" + acNo);
                    double penalty = chargesObj.getPenalty();
                    String custName = chargesObj.getCustname();
                    String custState = chargesObj.getCustState();
                    String branchState = chargesObj.getBrnchState();
                    desc = "Locker Rent for Locker No = " + chargesObj.getLockerno() + " from account No = " + acNo;

                    double tPenalty = penalty;
                    if (taxFlag.equalsIgnoreCase("Y")) {
//                        String taxCalList = taxCalculation(penalty, dt, orgnBrCode);
//                        String[] values = null;
//                        String spliter = "~`~";
//
//                        values = taxCalList.split(spliter);
//                        taxGlAcNo = values[0];
//                        taxAmt = Double.parseDouble(values[1]);
//                        taxName = values[2];
//                        taxAmt = CbsUtil.round(taxAmt, taxRoundUpTo);
                        if (custState.equalsIgnoreCase(branchState)) {
                            taxAmt = CbsUtil.round(((penalty * sPerc) / 100), rUpTo);
                            tPenalty = penalty + taxAmt;
                        } else {
                            taxAmtIgst = CbsUtil.round(((penalty * sPercIgst) / 100), rUpToIgst);
                            tPenalty = penalty + taxAmtIgst;
                        }
                    }
                    float recNo = 0f;
                    if (penalty >= 0) {
                        msg = fts.checkBalance(acNo, tPenalty, user);
                        if (msg.equalsIgnoreCase("True")) { // In case of sufficient fund.
                            if (taxFlag.equalsIgnoreCase("Y")) {
                                String taxDetail = "";
                                if (custState.equalsIgnoreCase(branchState)) {
                                    ttlTaxAmt = ttlTaxAmt + taxAmt;
                                    taxDetail = desc + ":" + "GST";
                                } else {
                                    ttlTaxAmtIgst = ttlTaxAmtIgst + taxAmtIgst;
                                    taxDetail = desc + ":" + "IGST";
                                    taxAmt = taxAmtIgst;
                                }
                                if (!orgnBrCode.equals(chargesObj.getBrCode())) {
                                    recNo = fts.getRecNo();
                                    msg = interFts.cbsPostingSx(acNo, 1, dt, taxAmt, 0d, 2, taxDetail, 0f, "A", "", "", 3, 0f, recNo, tranDesc,
                                            fts.getCurrentBrnCode(acNo), orgnBrCode, user, user, trSNo, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        strList.add(msg);
                                        returnList.add(strList);
                                        returnList.add(smsList);
                                        return returnList;
//                                        return msg;
                                    }
                                } else {
                                    recNo = fts.getRecNo();
                                    msg = interFts.cbsPostingCx(acNo, 1, dt, taxAmt, 0d, 2, taxDetail, 0f, "A", "", "", 3, 0f, recNo, tranDesc,
                                            fts.getCurrentBrnCode(acNo), orgnBrCode, user, user, trSNo, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        strList.add(msg);
                                        returnList.add(strList);
                                        returnList.add(smsList);
                                        return returnList;
//                                        return msg;
                                    }
                                }
                            }
                            if (!orgnBrCode.equals(chargesObj.getBrCode())) {
                                recNo = fts.getRecNo();
                                msg = interFts.cbsPostingSx(acNo, 1, dt, penalty, 0d, 2, desc, 0f, "A", "", "", 3, 0f, recNo, tranDesc,
                                        fts.getCurrentBrnCode(acNo), orgnBrCode, user, user, trSNo, "", "");
                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    strList.add(msg);
                                    returnList.add(strList);
                                    returnList.add(smsList);
                                    return returnList;
                                    // return msg;
                                }
                            } else {
                                recNo = fts.getRecNo();
                                msg = interFts.cbsPostingCx(acNo, 1, dt, penalty, 0d, 2, desc, 0f, "A", "", "", 3, 0f, recNo, tranDesc,
                                        fts.getCurrentBrnCode(acNo), orgnBrCode, user, user, trSNo, "", "");
                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    strList.add(msg);
                                    returnList.add(strList);
                                    returnList.add(smsList);
                                    return returnList;
                                }
                            }
                            msg = fts.updateBalance(fts.getAccountNature(acNo), acNo, 0, tPenalty, "Y", "Y");
                            if (!msg.equalsIgnoreCase("true")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
                            }
                            msg = updateLockerDetails(user, chargesObj, orgnBrCode);
                            if (!msg.equalsIgnoreCase("true")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
                            }
                            penalty1 = penalty1 + penalty;
                            chargesObj.setStatus("Success");

                            //last transaction updation
                            fts.lastTxnDateUpdation(acNo);
                            //Create Sms List
                            try {
                                SmsToBatchTo to = new SmsToBatchTo();
                                to.setAcNo(acNo);
                                to.setCrAmt(0d);
                                to.setDrAmt(tPenalty);
                                to.setTranType(2);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(new Date()));
                                to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
                                smsList.add(to);
                            } catch (Exception ex) {
                                System.out.println("Error In Sms List Creation." + ex.getMessage());
                            }
                        } else { // In case of insufficient fund.
                            // Discussion with team regarding the pending charges
//                            recNo = fts.getRecNo();
//                            msg = ftsInsertPending(acNo, "INSUFFICIENT FUND", penalty, 0, 1, custName, recNo, trSNo, user, user, tranDesc, desc, orgnBrCode, dt);
//                            if (!msg.equalsIgnoreCase("True")) {
//                                strList.add(msg);
//                                returnList.add(strList);
//                                returnList.add(smsList);
//                                return returnList;
////                                return "False";
//                            }
                            chargesObj.setStatus("Insufficient Fund");
                        }
                    }// End pently >= 0 condition
                    amt = penalty1;
                } // End for loop
                msg = "true";
                if (penalty1 > 0) {
                    float recNo = 0f;
                    if (taxFlag.equalsIgnoreCase("Y")) {
//                        recNo = fts.getRecNo();
//                        msg = interFts.cbsPostingCx(taxGlAcNo, 0, dt, ttlTaxAmt, 0d, 2, taxName + desc, 0f, "A", "", "", 3, 0f, recNo, tranDesc,
//                                fts.getCurrentBrnCode(taxGlAcNo), orgnBrCode, user, user, trSNo, "", "");
//                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                            strList.add(msg);
//                            returnList.add(strList);
//                            returnList.add(smsList);
//                            return returnList;
////                            return msg;
//                        }
                        desc = "VCH. Of Locker Rent";
                        if(ttlTaxAmt!=0){
                        double sTaxAmt = CbsUtil.round(((ttlTaxAmt * 100) / sPerc), rUpTo);
                        map = interFts.getTaxComponent(sTaxAmt, dt);
                        Set<Entry<String, Double>> set1 = map.entrySet();
                        Iterator<Entry<String, Double>> it1 = set1.iterator();
                        while (it1.hasNext()) {
                            Entry entry = it1.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = orgnBrCode + keyArray[1];
                            String mainDetails = description.toUpperCase() + " " + desc;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            //taxDesc = taxName + desc;
                            recNo = fts.getRecNo();
                            msg = interFts.cbsPostingCx(taxHead, 0, dt, taxAmount, 0d, 2, mainDetails, 0f, "A", "", "", 3, 0f, recNo, 71,
                                    fts.getCurrentBrnCode(taxHead), orgnBrCode, user, user, trSNo, "", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
                                //                            return msg;
                            }
                        }
                    }
                        if (ttlTaxAmtIgst != 0) {
                            double sTaxAmtIgst = CbsUtil.round(((ttlTaxAmtIgst * 100) / sPercIgst), rUpToIgst);
                            map = interFts.getIgstTaxComponent(sTaxAmtIgst, dt);
                            Set<Entry<String, Double>> setIgst = map.entrySet();
                            Iterator<Entry<String, Double>> itIgst = setIgst.iterator();
                            while (itIgst.hasNext()) {
                                Entry entry = itIgst.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                String taxHead = orgnBrCode + keyArray[1];
                                String mainDetails = description.toUpperCase() + " " + desc;
                                double taxAmount = Double.parseDouble(entry.getValue().toString());

                                //taxDesc = taxName + desc;
                                recNo = fts.getRecNo();
                                msg = interFts.cbsPostingCx(taxHead, 0, dt, taxAmount, 0d, 2, mainDetails, 0f, "A", "", "", 3, 0f, recNo, 71,
                                        fts.getCurrentBrnCode(taxHead), orgnBrCode, user, user, trSNo, "", "");
                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    strList.add(msg);
                                    returnList.add(strList);
                                    returnList.add(smsList);
                                    return returnList;
                                    //                            return msg;
                                }
                            }
                        }
                    }
                    recNo = fts.getRecNo();
                    msg = interFts.cbsPostingCx(glAcc, 0, dt, amt, 0d, 2, taxName + desc, 0f, "A", "", "", 3, 0f, recNo, tranDesc,
                            fts.getCurrentBrnCode(glAcc), orgnBrCode, user, user, trSNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        strList.add(msg);
                        returnList.add(strList);
                        returnList.add(smsList);
                        return returnList;
//                        return msg;
                    }
                }
            } else {
                for (LockerRentDetail chargesObj : chargesList) {// for
                    String acNo = chargesObj.getAcno();
                    System.out.println("A/c No Is-->" + acNo);
                    double penalty = chargesObj.getPenalty();
                    String custName = chargesObj.getCustname();
                    String acNature = fts.getAccountNature(acNo);
                    String custState = chargesObj.getCustState();
                    String branchState = chargesObj.getBrnchState();
                    desc = "Locker Rent for Locker No = " + chargesObj.getLockerno() + " from account No = " + acNo;
                    double tPenalty = penalty;
                    float recNo = 0f;
                    if (penalty >= 0) {
                        msg = fts.ftsAcnoValidateAuto(acNo, 1);
                        //msg = fts.ftsAcnoValidate(acNo, 1);                        

                        if (!msg.equals("true")) {
                            chargesObj.setStatus("Failed");
                            break;
                        }
                        if (taxFlag.equalsIgnoreCase("Y")) {
//                            String taxCalList = taxCalculation(penalty, dt, orgnBrCode);
//                            String[] values = null;
//                            String spliter = "~`~";
//
//                            values = taxCalList.split(spliter);
//                            taxGlAcNo = values[0];
//                            taxAmt = Double.parseDouble(values[1]);
//                            taxName = values[2];
//
//                            taxAmt = CbsUtil.round(taxAmt, taxRoundUpTo);
                            if (custState.equalsIgnoreCase(branchState)) {
                                taxAmt = CbsUtil.round(((penalty * sPerc) / 100), rUpTo);
                                tPenalty = penalty + taxAmt;
                            } else {
                                taxAmtIgst = CbsUtil.round(((penalty * sPercIgst) / 100), rUpToIgst);
                                tPenalty = penalty + taxAmtIgst;
                            }
                        }
                        msg = fts.checkBalance(acNo, tPenalty, user);
                        if (msg.equalsIgnoreCase("True")) { // In case of sufficient fund.
                            if (taxFlag.equalsIgnoreCase("Y")) {
                                if (custState.equalsIgnoreCase(branchState)) {
                                    ttlTaxAmt = ttlTaxAmt + taxAmt;
                                } else {
                                    ttlTaxAmtIgst = ttlTaxAmtIgst + taxAmtIgst;
                                    taxAmt = taxAmtIgst;
                                }
                                recNo = fts.getRecNo();
                                String taxDetail = desc + ":" + taxName;
                                msg = fts.insertRecons(acNature, acNo, 1, taxAmt, dt, dt, 2, taxDetail, user, trSNo, null, recNo, "Y", user, tranDesc, 3, null,
                                        null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, fts.getCurrentBrnCode(acNo), 0, null, "", "");

                                if (!msg.equalsIgnoreCase("True")) {
                                    strList.add(msg);
                                    returnList.add(strList);
                                    returnList.add(smsList);
                                    return returnList;
//                                    return "False";
                                }
                            }

                            recNo = fts.getRecNo();
                            msg = fts.insertRecons(acNature, acNo, 1, penalty, dt, dt, 2, desc, user, trSNo, null, recNo, "Y", user, tranDesc, 3, null,
                                    null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, fts.getCurrentBrnCode(acNo), 0, null, "", "");
                            if (!msg.equalsIgnoreCase("True")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
//                                return "False";
                            }

                            msg = fts.updateBalance(acNature, acNo, 0, tPenalty, "Y", "Y");
                            if (!msg.equalsIgnoreCase("True")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
//                                return "False";
                            }

                            penalty1 = penalty1 + penalty;
                            msg = updateLockerDetails(user, chargesObj, orgnBrCode);
                            if (!msg.equalsIgnoreCase("true")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
//                                return msg;
                            }
                            chargesObj.setStatus("Success");
                            //last transaction updation
                            fts.lastTxnDateUpdation(acNo);
                            //Create Sms List
                            try {
                                SmsToBatchTo to = new SmsToBatchTo();
                                to.setAcNo(acNo);
                                to.setCrAmt(0d);
                                to.setDrAmt(tPenalty);
                                to.setTranType(2);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(new Date()));
                                to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
                                smsList.add(to);
                            } catch (Exception ex) {
                                System.out.println("Error In Sms List Creation." + ex.getMessage());
                            }
                        } else { // In case of insufficient fund.
//                            recNo = fts.getRecNo();
//                            msg = ftsInsertPending(acNo, "INSUFFICIENT FUND", penalty, 0, 1, custName, recNo, trSNo, user, user, tranDesc, desc, orgnBrCode, dt);
//
//                            if (!msg.equalsIgnoreCase("True")) {
//                                strList.add(msg);
//                                returnList.add(strList);
//                                returnList.add(smsList);
//                                return returnList;
////                                return "False";
//                            }
                            chargesObj.setStatus("Insufficient Fund");
                        }
                    }// End pently >= 0 condition
                    amt = penalty1;
                } // End for loop
                msg = "true";
                if (penalty1 > 0) {
                    float recNo = 0f;
                    String acNature = "";
                    desc = "VCH. Of Locker Rent";
                    String taxDesc = taxName + desc;
                    if (taxFlag.equalsIgnoreCase("Y")) {
//                        acNature = acNature(fts.getAccountCode(taxGlAcNo));
//                        taxDesc = taxName + desc;
//                        recNo = fts.getRecNo();
//
//                        msg = fts.insertRecons(acNature, taxGlAcNo, 0, ttlTaxAmt, dt, dt, 2, taxDesc, user, trSNo, null, recNo, "Y", user,
//                                tranDesc, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
//                        if (!msg.equalsIgnoreCase("True")) {
//                            strList.add(msg);
//                            returnList.add(strList);
//                            returnList.add(smsList);
//                            return returnList;
////                            return "False";
//                        }
//                        msg = fts.updateBalance(acNature, taxGlAcNo, taxAmt, 0, "Y", "Y");
//                        if (!msg.equalsIgnoreCase("True")) {
//                            strList.add(msg);
//                            returnList.add(strList);
//                            returnList.add(smsList);
//                            return returnList;
////                            return "False";
//                        }
                        double sTaxAmt = CbsUtil.round(((ttlTaxAmt * 100) / sPerc), rUpTo);
                        map = interFts.getTaxComponent(sTaxAmt, dt);
                        Set<Entry<String, Double>> set1 = map.entrySet();
                        Iterator<Entry<String, Double>> it1 = set1.iterator();
                        while (it1.hasNext()) {
                            Entry entry = it1.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = orgnBrCode + keyArray[1];
                            String mainDetails = description.toUpperCase() + " " + desc;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            acNature = acNature(fts.getAccountCode(taxHead));
                            //taxDesc = taxName + desc;
                            recNo = fts.getRecNo();

                            msg = fts.insertRecons(acNature, taxHead, 0, taxAmount, dt, dt, 2, mainDetails, user, trSNo, null, recNo, "Y", user,
                                    71, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                            if (!msg.equalsIgnoreCase("True")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
//                                return "False";
                            }

                            msg = fts.updateBalance(acNature, taxHead, taxAmount, 0, "Y", "Y");
                            if (!msg.equalsIgnoreCase("True")) {
                                strList.add(msg);
                                returnList.add(strList);
                                returnList.add(smsList);
                                return returnList;
                                //                            return "False";
                            }
                        }
                        if (ttlTaxAmtIgst != 0) {
                            double sTaxAmtIgst = CbsUtil.round(((ttlTaxAmtIgst * 100) / sPercIgst), rUpToIgst);
                            map = interFts.getIgstTaxComponent(sTaxAmtIgst, dt);
                            Set<Entry<String, Double>> setIgst = map.entrySet();
                            Iterator<Entry<String, Double>> itIgst = setIgst.iterator();
                            while (itIgst.hasNext()) {
                                Entry entry = itIgst.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                String taxHead = orgnBrCode + keyArray[1];
                                String mainDetails = description.toUpperCase() + " " + desc;
                                double taxAmount = Double.parseDouble(entry.getValue().toString());

                                acNature = acNature(fts.getAccountCode(taxHead));
                                //taxDesc = taxName + desc;
                                recNo = fts.getRecNo();

                                msg = fts.insertRecons(acNature, taxHead, 0, taxAmount, dt, dt, 2, mainDetails, user, trSNo, null, recNo, "Y", user,
                                        71, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                                if (!msg.equalsIgnoreCase("True")) {
                                    strList.add(msg);
                                    returnList.add(strList);
                                    returnList.add(smsList);
                                    return returnList;
                                    //                                return "False";
                                }

                                msg = fts.updateBalance(acNature, taxHead, taxAmount, 0, "Y", "Y");
                                if (!msg.equalsIgnoreCase("True")) {
                                    strList.add(msg);
                                    returnList.add(strList);
                                    returnList.add(smsList);
                                    return returnList;
                                    //                            return "False";
                                }
                            }
                        }
                    }
                    recNo = fts.getRecNo();
                    acNature = acNature(fts.getAccountCode(glAcc));
                    msg = fts.insertRecons(acNature, glAcc, 0, amt, dt, dt, 2, taxDesc, user, trSNo, null, recNo, "Y", user,
                            tranDesc, 3, null, null, (float) 0.0, null, "A", 1, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                    if (!msg.equalsIgnoreCase("True")) {
                        strList.add(msg);
                        returnList.add(strList);
                        returnList.add(smsList);
                        return returnList;
//                        return "False";
                    }
                    msg = fts.updateBalance(acNature, glAcc, amt, 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("True")) {
                        strList.add(msg);
                        returnList.add(strList);
                        returnList.add(smsList);
                        return returnList;
//                        return "False";
                    }
                }
            }
            //  }
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                strList.add(msg + ":");
            } else {
                strList.add(msg + ":" + trSNo);
            }
            returnList.add(strList);
            returnList.add(smsList);
            return returnList;
//            return msg + ":" + trSNo;
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    private String cbsDestEntryIdentification(List<LockerRentDetail> rentList, String orgnBrCode) throws ApplicationException {
        String msg = "FALSE";
        for (LockerRentDetail rentObj : rentList) {
            String destBrnId = rentObj.getBrCode();
            if (!orgnBrCode.equalsIgnoreCase(destBrnId)) {
                msg = "TRUE";
                return msg;
            }
        }
        return msg;
    }

    private String updateLockerDetails(String user, LockerRentDetail chargesObj, String orgnBrCode) throws ApplicationException {
        try {
            float cabNo = chargesObj.getCabno();
            String lockerType = chargesObj.getLockertype();
            float lockerno = chargesObj.getLockerno();

            String rentdueDt = chargesObj.getRentduedt();
            SimpleDateFormat ddmmyyy = new SimpleDateFormat("dd/MM/yyyy");
            rentdueDt = ymd.format(ddmmyyy.parse(rentdueDt));

            Query insertQ = em.createNativeQuery("Insert Into lockerrecon (CabNo ,LockerType ,LockerNo ,Acno ,Rent,paydt,enterby)"
                    + " select cabno,lockertype,lockerno,acno,rent,CURRENT_TIMESTAMP,'" + user + "' from lockerrent "
                    + " Where  status='U' and CabNo= " + cabNo + "  And LockerType ='" + lockerType + "' And LockerNo =" + lockerno + "");
            int var = insertQ.executeUpdate();
            if (var <= 0) {
                return "False";
            }
            Query updateLockerRent = em.createNativeQuery("Update lockerrent Set Status='P',Txndate=CURRENT_TIMESTAMP Where status='U' "
                    + " and CabNo= " + cabNo + " And LockerType = '" + lockerType + "' And LockerNo =" + lockerno + " And RentDueDt='"
                    + rentdueDt + "' and  brnCode = '" + orgnBrCode + "'");
            int var2 = updateLockerRent.executeUpdate();
            if (var2 <= 0) {
                return "False";
            }
            Query insertQuery1 = em.createNativeQuery("Insert Into lockerrent(cabno,lockerType,lockerNo,Acno,Rent,issueDt,enterBy,RentDueDt,"
                    + "TxnDate,status,brncode) select cabno,lockerType,lockerNo,Acno,Rent,issueDt,'" + user + "','" + CbsUtil.yearAdd(rentdueDt, Integer.parseInt(chargesObj.getAdvPayYr()))
                    + "', TxnDate,'U',brncode from lockerrent where CabNo= " + cabNo + " And LockerType = '" + lockerType + "' And LockerNo = "
                    + lockerno + " And RentDueDt='" + rentdueDt + "' and brnCode = '" + orgnBrCode + "'");
            var = insertQuery1.executeUpdate();
            if (var <= 0) {
                return "False";
            }
            return "True";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
