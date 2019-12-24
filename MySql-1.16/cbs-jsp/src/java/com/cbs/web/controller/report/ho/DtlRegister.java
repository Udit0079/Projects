/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.DtlRegisterPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.cbs.web.pojo.ho.DtlRegisterOfNdtl;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class DtlRegister extends BaseBean{
    private String asOnDt;
    private String message;
    private String repOpt;
    private List<SelectItem> repOptionList;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public String getRepOpt() {
        return repOpt;
    }

    public void setRepOpt(String repOpt) {
        this.repOpt = repOpt;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public DtlRegister() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setMessage("");
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs."));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

      
    public void viewPdf() {
        Map map = new HashMap();
        try {
             if (this.asOnDt == null) {
                setMessage("Please Fill To Date");
                return;
            }
            List repFridayList = hoRemote.maxReportFriday();
            if(repFridayList.isEmpty()) {
                setMessage("Last Reporting Friday Does Not Exist!!");
            }
            Vector vect = (Vector) repFridayList.get(0);
            String[] a = vect.get(0).toString().split(" ");
            String c = a[0].replaceAll("[-+.^:,]","");
            String repFriday= CbsUtil.dateAdd(c,14);
            if (sdf.parse(asOnDt).compareTo(ymd.parse(repFriday)) > 0) {
                setMessage("Date can not be greater than Upcoming Reporting Friday.");
                return;
            }
            if (this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            setMessage("");
            String bankName = "";
            String bankAddress = "";
            String report = "";
            List dataList1;
            dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            List reportList = hoRemote.getCrrSlrPercentage("0A", ymd.format(sdf.parse(asOnDt)));
            Vector ele = (Vector) reportList.get(0);
            
            double crrPer = Double.parseDouble(ele.get(0).toString());
            double slrPer = Double.parseDouble(ele.get(1).toString());
            String nextFriday="N";
            if (sdf.parse(asOnDt).compareTo(new Date()) > 0) {
                nextFriday ="Y";
            }
            
            String amtIn = null;
            if (repOpt.equalsIgnoreCase("T")) {
                amtIn = "Amount (Rs. in Thousand)";
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amount (Rs.)";
            }
            report = "Daily Position of NDTL";
//            map.put("repDate", this.asOnDt);
//            map.put("pPrintedDate", this.dt);
            map.put("pPrintedBy", this.getUserName());
            map.put("pReportDate", this.asOnDt);
            map.put("pReportName", report);
            map.put("pBankName", bankName);
            map.put("pBankAdd", bankAddress);
            map.put("option", amtIn);
            map.put("crr", BigDecimal.valueOf(crrPer));
            map.put("slr", BigDecimal.valueOf(slrPer));
            map.put("nextFriDate", nextFriday);
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");

            List<DtlRegisterPojo> dtlRegList = hoRemote.getDtlRegisterData(getOrgnBrCode(), ymd.format(sdf.parse(asOnDt)), repOpt);
            List<DtlRegisterOfNdtl> dtlTable = new ArrayList<DtlRegisterOfNdtl>();//Actual
            DtlRegisterOfNdtl dtlPojo = new DtlRegisterOfNdtl();
            DtlRegisterPojo classPojo = null;
            for (int i = 0; i < dtlRegList.size(); i++) {
                classPojo = dtlRegList.get(i);
                List<RbiSossPojo> ndtlList  = classPojo.getNdtlList();
                List<RbiSossPojo> crrList        = classPojo.getCrrList();
                List<RbiSossPojo> slrList  = classPojo.getSlrList();
                
                dtlPojo.setNdtlList(new JRBeanCollectionDataSource(ndtlList));
                dtlPojo.setCrrList(new JRBeanCollectionDataSource(crrList));
                dtlPojo.setSlrList(new JRBeanCollectionDataSource(slrList));
                dtlTable.add(dtlPojo);
            }
            new ReportBean().openPdf("NDTL_REGISTER_"+ymd.format(sdf.parse(asOnDt)), "DailyPositionOfCrrSlr", new JRBeanCollectionDataSource(dtlTable), map);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void refresh(){
        setMessage("");
        setRepOpt("");
    }
    
    public String exit() {
        return "case1";
    }
}
