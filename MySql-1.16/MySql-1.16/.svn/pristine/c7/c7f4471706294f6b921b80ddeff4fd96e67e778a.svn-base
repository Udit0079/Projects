/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.SundrySuspencePojo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "SundrySuspenceBillBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class SundrySuspenceBillBean implements SundrySuspenceBillRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    @Override
    public List<SundrySuspencePojo> getSundryData(String brncode, int reporttype, String todate, String fromdt) {
        List<SundrySuspencePojo> listPojo = new ArrayList<SundrySuspencePojo>();
        if (reporttype == 0) {
            try {
                Query q = em.createNativeQuery("select bs.fyear,bs.seqno,bs.acno,bs.amount,bs.origindt,bs.status,bs.dt,bs.trantype from bill_sundry bs  where bs.fyear in(select f_year from yearend y where y.yearEndflag='N' and y.brncode='" + brncode + "' and  curDate() between y.mindate and y.maxdate ) and bs.acno in(select acno from gltable where acname='Sundry' and substring(acno,1,2)= '" + brncode + "')and bs.dt between '" + fromdt + "' and '" + todate + "'");
                List sundry = q.getResultList();
                if (!sundry.isEmpty()) {
                    for (int i = 0; i < sundry.size(); i++) {
                        Vector vtr = (Vector) sundry.get(i);
                        Query q1 = em.createNativeQuery("select acno,amount,dt,trantype from bill_sundry_dt where fyear='" + vtr.get(0).toString() + "' and seqno=" + vtr.get(1) + "");
                        List sundrydt = q1.getResultList();
                        if (!sundrydt.isEmpty()) {
                            for (int j = 0; j < sundrydt.size(); j++) {
                                Vector vtr1 = (Vector) sundrydt.get(j);
                                SundrySuspencePojo row = new SundrySuspencePojo();
                                row.setFyear(vtr.get(0).toString());
                                row.setSeqno((Double) vtr.get(1));
                                row.setAcno(vtr.get(2).toString());
                                row.setAmount((Double) vtr.get(3));
                                row.setDt((Date) vtr.get(4));
                                row.setStatus(vtr.get(5).toString());
                                row.setType(vtr.get(6).toString().equalsIgnoreCase("0")?"Cash":"Transfer");
                                row.setAcnodt(vtr1.get(0).toString());
                                row.setAmountdt((Double) vtr1.get(1));
                                row.setDtdt((Date) vtr1.get(2));
                                row.setTypedt(vtr1.get(3).toString().equalsIgnoreCase("0")?"Cash":"Transfer");
                                listPojo.add(row);
                            }

                        } else {
                            SundrySuspencePojo row = new SundrySuspencePojo();
                            row.setFyear(vtr.get(0).toString());
                            row.setSeqno((Double) vtr.get(1));
                            row.setAcno(vtr.get(2).toString());
                            row.setAmount((Double) vtr.get(3));
                            row.setDt((Date) vtr.get(4));
                            row.setStatus(vtr.get(5).toString());
                            row.setType(vtr.get(6).toString().equalsIgnoreCase("0")?"Cash":"Transfer");
                            if (vtr.get(5).toString().equalsIgnoreCase("PAID")) {
                                row.setAcnodt("Cash");
                                row.setAmountdt((Double) vtr.get(3));
                                row.setDtdt((Date) vtr.get(6));
                                row.setTypedt("Cash");
                            }

                            listPojo.add(row);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Query q = em.createNativeQuery("select bs.fyear,bs.seqno,bs.acno,bs.amount,bs.dt,bs.status from bill_suspense bs  where bs.fyear in(select f_year from yearend y where y.yearEndflag='N' and y.brncode='" + brncode + "' and  curDate() between y.mindate and y.maxdate ) and bs.acno in(select acno from gltable where acname='SUSPENSE PAYMENT' and substring(acno,1,2)= '" + brncode + "')and bs.dt between '" + fromdt + "' and '" + todate + "'");
                List sundry = q.getResultList();
                if (!sundry.isEmpty()) {
                    for (int i = 0; i < sundry.size(); i++) {
                        Vector vtr = (Vector) sundry.get(i);
                        Query q1 = em.createNativeQuery("select acno,amount,dt from bill_suspense_dt where fyear='" + vtr.get(0).toString() + "' and seqno=" + vtr.get(1) + "");
                        List sundrydt = q1.getResultList();
                        if (!sundrydt.isEmpty()) {
                            for (int j = 0; j < sundrydt.size(); j++) {
                                Vector vtr1 = (Vector) sundrydt.get(j);
                                SundrySuspencePojo row = new SundrySuspencePojo();
                                row.setFyear(vtr.get(0).toString());
                                row.setSeqno((Double) vtr.get(1));
                                row.setAcno(vtr.get(2).toString());
                                row.setAmount((Double) vtr.get(3));
                                row.setDt((Date) vtr.get(4));
                                row.setStatus(vtr.get(5).toString());
                                row.setAcnodt(vtr1.get(0).toString());
                                row.setAmountdt((Double) vtr1.get(1));
                                row.setDtdt((Date) vtr1.get(2));
                                listPojo.add(row);
                            }

                        } else {
                            SundrySuspencePojo row = new SundrySuspencePojo();
                            row.setFyear(vtr.get(0).toString());
                            row.setSeqno((Double) vtr.get(1));
                            row.setAcno(vtr.get(2).toString());
                            row.setAmount((Double) vtr.get(3));
                            row.setDt((Date) vtr.get(4));
                            row.setStatus(vtr.get(5).toString());
                            row.setAcnodt(null);
                            row.setAmountdt(null);
                            row.setDtdt(null);
                            listPojo.add(row);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listPojo;
    }

    @Override
    public String getBranchNameandAddress(int brncode) {
        Vector ele = null;
        String result = null;
        try {
            Query selectQuery = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and br.brncode=" + brncode);
            List list1 = selectQuery.getResultList();
            if (!list1.isEmpty()) {
                ele = (Vector) list1.get(0);
                result = ele.get(0).toString() + "!" + ele.get(1).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
