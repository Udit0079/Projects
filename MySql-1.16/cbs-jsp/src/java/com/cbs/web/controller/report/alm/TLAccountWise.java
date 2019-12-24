package com.cbs.web.controller.report.alm;

import com.cbs.dto.report.TlAccountWisePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ALMReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author Zeeshan Waris
 */
public final class TLAccountWise extends BaseBean {

    private String message;
    Date calDate = new Date();
    ALMReportFacadeRemote beanRemote;

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TLAccountWise() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String printAction() {
        try {
            if (calDate == null) {
                this.setMessage("Please Fill As On Date");
                return null;
            }
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            beanRemote = (ALMReportFacadeRemote) ServiceLocator.getInstance().lookup("ALMReportFacade");
            List<TlAccountWisePojo> tlAcWise = beanRemote.tlAcWise(ymdFormat.format(calDate), getOrgnBrCode());
            if (tlAcWise == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!tlAcWise.isEmpty()) {
                String repName = "ALM TL A/C Wise";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("asOnDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                fillParams.put("pPrintedBy", getUserName());                
                new ReportBean().jasperReport("almtlacwise", "text/html", new JRBeanCollectionDataSource(tlAcWise), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String exitAction() {
        return "case1";
    }

    public void refreshAction() {
        try {
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
