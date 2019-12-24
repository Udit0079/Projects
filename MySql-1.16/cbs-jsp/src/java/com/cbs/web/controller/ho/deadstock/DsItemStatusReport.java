package com.cbs.web.controller.ho.deadstock;

import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.dto.ItemStatusReportTable;
import com.cbs.exception.ApplicationException;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ankit Verma
 */
public final class DsItemStatusReport extends BaseBean implements Comparator<Date> {

    private DeadstockFacadeRemote deadstockFacadeRemote;
    private String msg;
    private String selectBranch;
    InitialContext ctx;
    private String fromDate;
    private String toDate;
    List<SelectItem> selectBranchList = new ArrayList<SelectItem>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DsItemStatusReport() {
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSelectBranch() {
        return selectBranch;
    }

    public void setSelectBranch(String selectBranch) {
        this.selectBranch = selectBranch;
    }

    public List<SelectItem> getSelectBranchList() {
        return selectBranchList;
    }

    public void setSelectBranchList(List<SelectItem> selectBranchList) {
        this.selectBranchList = selectBranchList;
    }

    public void viewReport() {
        try {
            deadstockFacadeRemote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            String brncode = getOrgnBrCode();
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                String dt1[] = fromDate.split("/");
                String frmDt = dt1[2] + dt1[1] + dt1[0];
                String dt2[] = toDate.split("/");
                String toDt = dt2[2] + dt2[1] + dt2[0];
                Vector vec = deadstockFacadeRemote.getBranchNameandAddress(brncode);
                Map fillParams = new HashMap();
                if (vec.size() > 0) {
                    fillParams.put("pReportName", "Item Status Report");
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBranchAddress", vec.get(1).toString());
                    fillParams.put("pFromDate", fromDate);
                    fillParams.put("pToDate", toDate);
                }
                List<ItemStatusReportTable> reportTable = deadstockFacadeRemote.getDataAccordingToBrnCode(brncode, frmDt, toDt);
                new ReportBean().jasperReport("Ds_Item_Status_Report", "text/html", new JRBeanCollectionDataSource(reportTable), fillParams, "Item Status Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        fromDate = "";
        toDate = "";
    }

    public String exitAction() {
        return "case1";
    }

    public String validation() {
        try {
            if (fromDate == null || fromDate.equalsIgnoreCase("")) {
                return "Please Insert From Date";
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                return "Please Insert To Date";
            }
            SimpleDateFormat ymd = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = ymd.parse(fromDate);
            Date d2 = ymd.parse(toDate);
            int intFlag = compare(d1, d2);
            if (intFlag > 0) {
                return "To Date must be greater than From Date";
            }
            return "ok";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public int compare(Date frmDate, Date toDate) {
        int iFlag = frmDate.compareTo(toDate);
        return iFlag;
    }
}
