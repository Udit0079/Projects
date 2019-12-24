package com.cbs.web.controller.report.other;

import com.cbs.dto.report.ExcessTransactionPojo;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
public final class ExcessTransactionReport extends BaseBean {

    private String message;
    Date calDate = new Date();
    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ExcessTransactionReport() {
        try {

            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            OtherReportFacadeRemote beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            if (calDate == null) {
                message = "Please Fill Transaction Date";
                return null;
            }
            if (amount == null || amount.equalsIgnoreCase("")) {
                message = "Please Fill Amount";
                return null;
            }
            if (!amount.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Amount");
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<ExcessTransactionPojo> excessTransaction = beanRemote.excessTransaction(Double.parseDouble(amount), ymdFormat.format(calDate), getOrgnBrCode());
            if (!excessTransaction.isEmpty()) {
                String repName = "Excess Transaction Report";
                Map fillParams = new HashMap();
                fillParams.put("repName", repName);
                fillParams.put("printBy", getUserName());
                fillParams.put("datinvolve", sdf.format(calDate));
                fillParams.put("pAmount", Double.parseDouble(amount));
                new ReportBean().jasperReport("excessTransactionReport", "text/html", new JRBeanCollectionDataSource(excessTransaction), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAmount("");
            calDate = new Date();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
