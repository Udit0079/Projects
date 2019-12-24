/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class InvestmentCommonOperation {

    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";

    public InvestmentCommonOperation() {
        try {
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printReport(List resultList, String reportName, String jrxmlName, String orgnCode, String todayDate, String userName, Map param) throws ApplicationException {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(orgnCode);
            if (bankDetailsList.size() > 0) {
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", reportName);
                fillParams.put("pReportDate", todayDate);
                fillParams.put("pPrintedBy", userName);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);

                new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
