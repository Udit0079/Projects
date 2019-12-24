/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TdPeriodReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class TdPeriodReport extends BaseBean {

    private String message;
    private String type;
    private String asOnDt;
    private Integer sNo;
    private List<SelectItem> typeList;
    private String fromDays;
    private String toDays;
    private Date date = new Date();
    private boolean flag;
    boolean disableFrDaysToDays;
    boolean disableDate;
    private String focusId;
    private String tyButton;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherReportFacade;
    private OtherMasterFacadeRemote otherMasterRemote;
    private List<TdPeriodReportPojo> gridDetail;
    List<TdPeriodReportPojo> reportList = new ArrayList<TdPeriodReportPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public boolean isDisableDate() {
        return disableDate;
    }

    public void setDisableDate(boolean disableDate) {
        this.disableDate = disableDate;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public String getTyButton() {
        return tyButton;
    }

    public void setTyButton(String tyButton) {
        this.tyButton = tyButton;
    }

    public boolean isDisableFrDaysToDays() {
        return disableFrDaysToDays;
    }

    public void setDisableFrDaysToDays(boolean disableFrDaysToDays) {
        this.disableFrDaysToDays = disableFrDaysToDays;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<TdPeriodReportPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TdPeriodReportPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFromDays() {
        return fromDays;
    }

    public void setFromDays(String fromDays) {
        this.fromDays = fromDays;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToDays() {
        return toDays;
    }

    public void setToDays(String toDays) {
        this.toDays = toDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public TdPeriodReport() {
        try {
            //setAsOnDt(dmy.format(date));
            asOnDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherReportFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "--Select--"));
            typeList.add(new SelectItem("R", "Report"));
            typeList.add(new SelectItem("M", "Master"));
            this.setTyButton("Save");
            gridLoad();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewButoon() {
        try {
            if (type.equalsIgnoreCase("R")) {
                this.setFocusId("asOnDt");
                this.setTyButton("View Report");
                setDisableFrDaysToDays(true);
                setDisableDate(false);
            }
            if (type.equalsIgnoreCase("M")) {
                this.setFocusId("txtFrDay");
                this.setTyButton("Save");
                setDisableFrDaysToDays(false);
                setDisableDate(true);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<TdPeriodReportPojo>();
            List resultList = new ArrayList();
            resultList = otherMasterRemote.loadTdPeriod();
            int sNo = 0;
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    sNo = sNo + 1;
                    TdPeriodReportPojo pojo = new TdPeriodReportPojo();
                    pojo.setFromDays(ele.get(0).toString());
                    pojo.setToDays(ele.get(1).toString());
                    pojo.setSrNo(sNo);
                    gridDetail.add(pojo);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnAction() {
        if (type.equals("R")) {
            viewReport();
        } else {
            saveBtnAction();
        }
    }

    public void saveBtnAction() {
        setMessage("");
        try {

            if (type == null || type.equalsIgnoreCase("0")) {
                setMessage("Please select Status!!!");
                return;
            }
            if (fromDays == null) {
                setMessage("Please fill From Days!!!");
                return;
            }
            if (toDays == null) {
                setMessage("Please fill From Days!!!");
                return;
            }

            String result = otherMasterRemote.saveTdPeriodData(fromDays, toDays, getUserName());
            gridLoad();
            setMessage(result);
            clear();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "";
        String address = "";
        String report = "Term Deposit Period Report";
        try {
            if (type == null || type.equalsIgnoreCase("0")) {
                setMessage("Please select Status!!!");
                return;
            }
            
             if (asOnDt == null || asOnDt.equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return ;
            }
            if (!Validator.validateDate(asOnDt)) {
                setMessage("Please select Proper from date ");
                return ;
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDate", this.asOnDt);

            String asOnDate = ymd.format(dmy.parse(asOnDt));

            reportList = otherReportFacade.getTdPeriodReportData(getOrgnBrCode(), asOnDate);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }

            new ReportBean().jasperReport("Td_Period_Report", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Term Deposit Period Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void clear() {
        // setMessage("");
        try {
            //this.setType("0");
            this.setFromDays("0");
            this.setToDays("0");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        this.setType("0");
        this.setAsOnDt(dmy.format(date));
    }

    public String exitAction() {
        return "case1";
    }
}
