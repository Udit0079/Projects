/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.other.MinimumBalSmsTo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "OtherMiscReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OtherMiscReportFacade implements OtherMiscReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List<MinimumBalSmsTo> getMinimumBalSmsReport(String brncode, String frDt, String toDt) throws ApplicationException {
        List<MinimumBalSmsTo> dataList = new ArrayList<MinimumBalSmsTo>();
        try {
            String reportQuery = "select acno,mobile,date_format(dt,'%Y%m%d') as dt from "
                    + "cbs_min_bal_sms_detail where dt between '" + frDt + "'  and '" + toDt + "'";
            if (!brncode.equalsIgnoreCase("ALL")) {
                reportQuery = reportQuery + " and substring(acno,1,2) = '" + brncode + "'";
            }
            List list = em.createNativeQuery(reportQuery).getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String acno = ele.get(0).toString().trim();
                String mobile = ele.get(1).toString().trim();
                String dt = ele.get(2).toString().trim();

                List minList = em.createNativeQuery("select min_bal from cbs_min_bal_acno_detail "
                        + "where min_bal_dt='" + dt + "' and acno='" + acno + "' "
                        + "union "
                        + "select min_bal from cbs_min_bal_acno_detail_his where "
                        + "min_bal_dt='" + dt + "' and acno='" + acno + "'").getResultList();
                if (!minList.isEmpty()) {
                    Vector minVec = (Vector) minList.get(0);
                    BigDecimal minBal = new BigDecimal(minVec.get(0).toString());

                    MinimumBalSmsTo to = new MinimumBalSmsTo();
                    to.setAcno(acno);
                    to.setMinBal(minBal);
                    to.setMobile(mobile);
                    to.setMinBalDt(dmy.format(ymd.parse(dt)));
                    to.setSmsFlag(mobile.trim().length() == 13 ? "Y" : "N");

                    dataList.add(to);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
