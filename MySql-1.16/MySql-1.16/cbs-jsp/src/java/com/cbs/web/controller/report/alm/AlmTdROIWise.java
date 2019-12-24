package com.cbs.web.controller.report.alm;

import com.cbs.dto.report.AlmTdRoiWiseReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ALMReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class AlmTdROIWise extends BaseBean {

    private String message;
    String calDate;

    public AlmTdROIWise() {
    }

    public String printAction() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
                    dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
            if (calDate == null || calDate.equals("")) {
                setMessage("Please fill the As on Date");
                return null;
            }
            if(!Validator.validateDate(calDate)){
                setMessage("Please fill the valid As on Date");
                return null;
            }
            String dt = ymdFormatter.format(dmyformatter.parse(calDate));
            ALMReportFacadeRemote beanRemote = (ALMReportFacadeRemote) ServiceLocator.getInstance().lookup("ALMReportFacade");
            List<AlmTdRoiWiseReportPojo> almTdRoiWiseReport = beanRemote.getAlmTdRoiWiseReport(dt, getOrgnBrCode());
            if (almTdRoiWiseReport == null) {
                setMessage("Data does not exist");
                return null;
            }
            if (!almTdRoiWiseReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "ALM Term Deposit ROI Wise Report";
                Map fillParams = new HashMap();
                fillParams.put("asOnDate", calDate);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);                
                new ReportBean().jasperReport("almtdroiwise", "text/html", new JRBeanCollectionDataSource(almTdRoiWiseReport), fillParams, repName);
                return "report";
            } else {
                setMessage("Data does not exist");
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return null;
    }

    public String exitAction() {
        return "case1";
    }

    public void refresh(){
        setMessage("");
        setCalDate("");
    }
    
    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
