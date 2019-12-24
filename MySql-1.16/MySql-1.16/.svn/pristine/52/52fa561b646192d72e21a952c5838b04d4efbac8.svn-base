/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho;

import com.cbs.dto.report.FdMaturityPojo;
import javax.ejb.Stateless;
import java.util.List;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless
public class HoReportBean implements HoReportBeanRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    
    public List<FdMaturityPojo> getFdMaturityData(String brncode, double amt, int fyear, int toyear, String asonDt) throws ApplicationException {
        List<FdMaturityPojo> returnList = new ArrayList<FdMaturityPojo>();
        try {
            int varfryr = fyear * 365;
            int vartoyr = toyear * 365;
            if (brncode.equalsIgnoreCase("0A")) {
                List value = em.createNativeQuery("select ifnull(sum(ifnull(cumuprinamt,0)),0),count(acno) from td_vouchmst where status = 'A' and  matdt <= "
                        + " DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + varfryr + " DAY),'%Y%m%d') ").getResultList();
                Vector valListVec = (Vector) value.get(0);

                String amtDesc = "Deposits maturing within " + fyear + " year";
                BigDecimal pAmt = new BigDecimal(valListVec.get(0).toString());
                int pCnt = Integer.parseInt(valListVec.get(1).toString());

                List value1 = em.createNativeQuery("select ifnull(sum(ifnull(cumuprinamt,0)),0),count(acno) from td_vouchmst where status = 'A' and matdt >  DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + varfryr + " DAY),'%Y%m%d')"
                        + " and matdt<= DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + vartoyr + " DAY),'%Y%m%d')").getResultList();
                Vector valListVec1 = (Vector) value1.get(0);

                String amtDesc1 = "Deposits maturing within " + fyear + " - " + toyear + " years";
                BigDecimal amt1to3 = new BigDecimal(valListVec1.get(0).toString());
                int varcnt1 = Integer.parseInt(valListVec1.get(1).toString());

                List value2 = em.createNativeQuery("select ifnull(sum(ifnull(cumuprinamt,0)),0),count(acno) from td_vouchmst where status = 'A' and matdt > DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + vartoyr + " DAY),'%Y%m%d')").getResultList();
                Vector valListVec2 = (Vector) value2.get(0);

                String amtDesc2 = "Deposits maturing beyond " + toyear + " years";
                BigDecimal amtabove3 = new BigDecimal(valListVec2.get(0).toString());
                int varcnt2 = Integer.parseInt(valListVec2.get(1).toString());

                String amtDesc3 = "Total";
                int totCnt = pCnt + varcnt1 + varcnt2;
                BigDecimal totAmt = (pAmt.add(amt1to3)).add(amtabove3);

                int varcnt = 0;
                double var_sumamt = 0;
                double var_interest = 0;
                double var_prinamt = 0;
                List value3 = em.createNativeQuery("select acno,coalesce(prinamt,0),voucherno from td_vouchmst where intopt = 'C' and prinamt <= "+ amt +" and fddt < '" + asonDt + "' and status = 'A'").getResultList();
                if (value3.size() > 0) {
                    for (int j = 0; j < value3.size(); j++) {
                        Vector record = (Vector) value3.get(j);
                        String var_acno = record.get(0).toString();
                        String var_voucherno = record.get(2).toString();
                        var_prinamt = Double.parseDouble(record.get(1).toString());

                        varcnt = varcnt + 1;
                        List intDtl = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where intopt = 'C' and acno = '" + var_acno + "' and voucherno = "+ var_voucherno +"").getResultList();
                        Vector intVec = (Vector) intDtl.get(0);
                        var_interest = Double.parseDouble(intVec.get(0).toString());

                        var_sumamt = var_sumamt + var_prinamt + var_interest;
                    }
                }

                String amtDesc4 = "Deposit with Balance (Including accrued interest) up to Rs. " + amt / 100000 + "Lakh";

                double var_int = 0;
                double var_pri = 0;
                double amtabove1lac = 0;
                int varcnt4 = 0;

                List value4 = em.createNativeQuery("select acno,coalesce(prinamt,0),voucherno from td_vouchmst where  intopt = 'C' and prinamt > "+ amt +"  and fddt < '" + asonDt + "' and status = 'A'").getResultList();
                if (value4.size() > 0) {
                    for (int k = 0; k < value4.size(); k++) {
                        Vector record = (Vector) value4.get(k);
                        String var_acno = record.get(0).toString();
                        String var_voucherno = record.get(2).toString();
                        var_pri = Double.parseDouble(record.get(1).toString());

                        varcnt4 = varcnt4 + 1;
                        List intDtl = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where intopt = 'C' and acno = '" + var_acno + "' and voucherno = "+ var_voucherno +"").getResultList();
                        Vector intVec = (Vector) intDtl.get(0);
                        var_int = Double.parseDouble(intVec.get(0).toString());

                        amtabove1lac = amtabove1lac + var_pri + var_int;
                    }
                }

                String amtDesc5 = "Deposits with Balances (Including accrued interes) above Rs. " + amt / 100000 + "Lakh";

                String amtDesc6 = "Total";
                int toCnt1 = varcnt + varcnt4;
                double toAmt1 = amtabove1lac + var_sumamt;

                FdMaturityPojo pojo = new FdMaturityPojo();
                pojo.setAcDesc(amtDesc);
                pojo.setCnt(pCnt);
                pojo.setAmt(pAmt);
                returnList.add(pojo);
                
                FdMaturityPojo pojo1 = new FdMaturityPojo();
                pojo1.setAcDesc(amtDesc1);
                pojo1.setCnt(varcnt1);
                pojo1.setAmt(amt1to3);
                returnList.add(pojo1);
                
                FdMaturityPojo pojo2 = new FdMaturityPojo();
                pojo2.setAcDesc(amtDesc2);
                pojo2.setCnt(varcnt2);
                pojo2.setAmt(amtabove3);
                returnList.add(pojo2);
                
                FdMaturityPojo pojo3 = new FdMaturityPojo();
                pojo3.setAcDesc(amtDesc3);
                pojo3.setCnt(totCnt);
                pojo3.setAmt(totAmt);
                returnList.add(pojo3);
                
                FdMaturityPojo pojo4 = new FdMaturityPojo();
                pojo4.setAcDesc(amtDesc4);
                pojo4.setCnt(varcnt);
                pojo4.setAmt(new BigDecimal(var_sumamt));
                returnList.add(pojo4);
                
                FdMaturityPojo pojo5 = new FdMaturityPojo();
                pojo5.setAcDesc(amtDesc5);
                pojo5.setCnt(varcnt4);
                pojo5.setAmt(new BigDecimal(amtabove1lac));
                returnList.add(pojo5);
                
                FdMaturityPojo pojo6 = new FdMaturityPojo();
                pojo6.setAcDesc(amtDesc6);
                pojo6.setCnt(toCnt1);
                pojo6.setAmt(new BigDecimal(toAmt1));
                returnList.add(pojo6);
                
            } else {
                List value = em.createNativeQuery("select ifnull(sum(ifnull(cumuprinamt,0)),0),count(acno) from td_vouchmst where status = 'A' and  matdt <= "
                        + " DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + varfryr + " DAY),'%Y%m%d') and substring(acno,1,2)='" + brncode + "'").getResultList();
                Vector valListVec = (Vector) value.get(0);

                String amtDesc = "Deposits maturing within " + fyear + " year";
                BigDecimal pAmt = new BigDecimal(valListVec.get(0).toString());
                int pCnt = Integer.parseInt(valListVec.get(1).toString());

                List value1 = em.createNativeQuery("select ifnull(sum(ifnull(cumuprinamt,0)),0),count(acno) from td_vouchmst where status = 'A' and matdt > DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + varfryr + " DAY),'%Y%m%d')"
                        + " and matdt<= DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + vartoyr + " DAY),'%Y%m%d') and substring(acno,1,2)='" + brncode + "'").getResultList();
                Vector valListVec1 = (Vector) value1.get(0);

                String amtDesc1 = "Deposits maturing within " + fyear + " - " + toyear + " years";
                BigDecimal amt1to3 = new BigDecimal(valListVec1.get(0).toString());
                int varcnt1 = Integer.parseInt(valListVec1.get(1).toString());

                List value2 = em.createNativeQuery("select ifnull(sum(ifnull(cumuprinamt,0)),0),count(acno) from td_vouchmst where status = 'A' and matdt > DATE_FORMAT(DATE_ADD('" + asonDt + "', INTERVAL " + vartoyr + " DAY),'%Y%m%d') and substring(acno,1,2)='" + brncode + "'").getResultList();
                Vector valListVec2 = (Vector) value2.get(0);

                String amtDesc2 = "Deposits maturing beyond " + toyear + " years";
                BigDecimal amtabove3 = new BigDecimal(valListVec2.get(0).toString());
                int varcnt2 = Integer.parseInt(valListVec2.get(1).toString());

                String amtDesc3 = "Total";
                int totCnt = pCnt + varcnt1 + varcnt2;
                BigDecimal totAmt = (pAmt.add(amt1to3)).add(amtabove3);

                int varcnt = 0;
                double var_sumamt = 0;
                double var_interest = 0;
                double var_prinamt = 0;
                List value3 = em.createNativeQuery("select acno,coalesce(prinamt,0),voucherno from td_vouchmst where intopt = 'C' and prinamt <= "+ amt +"  and fddt < '" + asonDt + "' and status = 'A' and substring(acno,1,2)='" + brncode + "'").getResultList();
                if (value3.size() > 0) {
                    for (int j = 0; j < value3.size(); j++) {
                        Vector record = (Vector) value3.get(j);
                        String var_acno = record.get(0).toString();
                        String var_voucherno = record.get(2).toString();
                        var_prinamt = Double.parseDouble(record.get(1).toString());

                        varcnt = varcnt + 1;
                        List intDtl = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where intopt = 'C' and acno = '" + var_acno + "' and voucherno = "+ var_voucherno +"").getResultList();
                        Vector intVec = (Vector) intDtl.get(0);
                        var_interest = Double.parseDouble(intVec.get(0).toString());

                        var_sumamt = var_sumamt + var_prinamt + var_interest;
                    }
                }

                String amtDesc4 = "Deposit with Balance (Including accrued interest) up to Rs. " + amt / 100000 + "Lakh";

                double var_int = 0;
                double var_pri = 0;
                double amtabove1lac = 0;
                int varcnt4 = 0;

                List value4 = em.createNativeQuery("select acno,coalesce(prinamt,0),voucherno from td_vouchmst where  intopt = 'C' and prinamt > "+ amt +"  and fddt < '" + asonDt + "' and status = 'A' and substring(acno,1,2)='" + brncode + "'").getResultList();
                if (value4.size() > 0) {
                    for (int k = 0; k < value4.size(); k++) {
                        Vector record = (Vector) value4.get(k);
                        String var_acno = record.get(0).toString();
                        String var_voucherno = record.get(2).toString();
                        var_pri = Double.parseDouble(record.get(1).toString());

                        varcnt4 = varcnt4 + 1;
                        List intDtl = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where intopt = 'C' and acno = '" + var_acno + "' and voucherno = "+ var_voucherno +"").getResultList();
                        Vector intVec = (Vector) intDtl.get(0);
                        var_int = Double.parseDouble(intVec.get(0).toString());

                        amtabove1lac = amtabove1lac + var_pri + var_int;

                    }
                }

                String amtDesc5 = "Deposits with Balances (Including accrued interes) above Rs. " + amt / 100000 + "Lakh";

                String amtDesc6 = "Total";
                int toCnt1 = varcnt + varcnt4;
                double toAmt1 = amtabove1lac + var_sumamt;

                FdMaturityPojo pojo = new FdMaturityPojo();
                pojo.setAcDesc(amtDesc);
                pojo.setCnt(pCnt);
                pojo.setAmt(pAmt);
                returnList.add(pojo);
                
                FdMaturityPojo pojo1 = new FdMaturityPojo();
                pojo1.setAcDesc(amtDesc1);
                pojo1.setCnt(varcnt1);
                pojo1.setAmt(amt1to3);
                returnList.add(pojo1);
                
                FdMaturityPojo pojo2 = new FdMaturityPojo();
                pojo2.setAcDesc(amtDesc2);
                pojo2.setCnt(varcnt2);
                pojo2.setAmt(amtabove3);
                returnList.add(pojo2);
                
                FdMaturityPojo pojo3 = new FdMaturityPojo();
                pojo3.setAcDesc(amtDesc3);
                pojo3.setCnt(totCnt);
                pojo3.setAmt(totAmt);
                returnList.add(pojo3);
                
                FdMaturityPojo pojo4 = new FdMaturityPojo();
                pojo4.setAcDesc(amtDesc4);
                pojo4.setCnt(varcnt);
                pojo4.setAmt(new BigDecimal(var_sumamt));
                returnList.add(pojo4);
                
                FdMaturityPojo pojo5 = new FdMaturityPojo();
                pojo5.setAcDesc(amtDesc5);
                pojo5.setCnt(varcnt4);
                pojo5.setAmt(new BigDecimal(amtabove1lac));
                returnList.add(pojo5);
                
                FdMaturityPojo pojo6 = new FdMaturityPojo();
                pojo6.setAcDesc(amtDesc6);
                pojo6.setCnt(toCnt1);
                pojo6.setAmt(new BigDecimal(toAmt1));
                
                returnList.add(pojo6);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }
}