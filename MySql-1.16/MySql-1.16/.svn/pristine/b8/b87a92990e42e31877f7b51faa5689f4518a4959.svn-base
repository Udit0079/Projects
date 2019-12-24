package com.cbs.web.controller.misc;

import com.cbs.dto.Table;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.InoperativeChargesFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class InoperativeMarking extends BaseBean{

    InoperativeChargesFacadeRemote remoteObject;
    private Date tillDate;
    private List<SelectItem> daysList;
    private List<SelectItem> acctNo;
    String acType;
    private List<Table> inoperativeMark;
    String days;
    private String message;
    private boolean isdisable;
    private String flag;
    String globalDate;
    private String viewID="/pages/master/sm/test.jsp";

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }
    
    
    public String getGlobalDate() {
        return globalDate;
    }

    public void setGlobalDate(String globalDate) {
        this.globalDate = globalDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<SelectItem> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<SelectItem> daysList) {
        this.daysList = daysList;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public List<Table> getInoperativeMark() {
        return inoperativeMark;
    }

    public void setInoperativeMark(List<Table> inoperativeMark) {
        this.inoperativeMark = inoperativeMark;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(List<SelectItem> acctNo) {
        this.acctNo = acctNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsdisable() {
        return isdisable;
    }

    public void setIsdisable(boolean isdisable) {
        this.isdisable = isdisable;
    }

    public InoperativeMarking() {
        try {
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            remoteObject = (InoperativeChargesFacadeRemote) ServiceLocator.getInstance().lookup("InoperativeChargesFacade");
            Date date = new Date();
            setTodayDate(dmy.format(date));
            setTillDate(date);
            getDropDownList();
            setIsdisable(true);
            flag = "false";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    public void getDropDownList() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.getDropdownListInoperative();
            acctNo = new ArrayList<SelectItem>();
            acctNo.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                acctNo.add(new SelectItem(ele.get(0)));
            }
            daysList = new ArrayList<SelectItem>();
            daysList.add(new SelectItem("--Select--"));
            daysList.add(new SelectItem("180"));
            daysList.add(new SelectItem("365"));
            daysList.add(new SelectItem("730"));
            daysList.add(new SelectItem("1095"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onchangeAcno() {

        this.setMessage("");
    }

    public void onChangeNumber() {
        this.setMessage("");
    }

    public String getTableDetails() throws NamingException {
        this.setMessage("");
        setIsdisable(true);
        flag = "false";
        inoperativeMark = new ArrayList<Table>();
        try {
            if (this.acType.equals("--Select--")) {
                this.setMessage("Please Select The Account Type.");
                return "Please Select The Account Type.";
            } else {
                this.setMessage("");
            }

            if (this.days.equals("--Select--")) {
                this.setMessage("Please select the in opt day");
                return "Please select the in opt day";
            } else {
                this.setMessage("");
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String tilldate1 = formatter.format(tillDate);
            globalDate = tilldate1;
            List<Table> resultLt = remoteObject.getInoperativeAccounts(getOrgnBrCode(), acType, Integer.parseInt(days), tilldate1);
            if (!resultLt.isEmpty()) {
                setIsdisable(false);
                for (int i = 0; i < resultLt.size(); i++) {
                    Table rd = new Table();
                    rd.setsNo(resultLt.get(i).getsNo());
                    rd.setAcNo(resultLt.get(i).getAcNo());
                    rd.setCustomerName(resultLt.get(i).getCustomerName());
                    rd.setLastTrctionDt(resultLt.get(i).getLastTrctionDt());
                    rd.setDayDiff(resultLt.get(i).getDayDiff());
                    rd.setBalance(resultLt.get(i).getBalance());
                    inoperativeMark.add(rd);
                    
                }
            String date=globalDate;
            String yy=date.substring(0,4);
            String mm=date.substring(4,6);
            String dd=date.substring(6,8);
            String fullDate=dd +"/"+ mm + "/"+yy;
            CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List vec = beanLocal.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", "Inoperative");
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDate", fullDate);
            fillParams.put("pBankName", vec.get(0).toString());
            fillParams.put("pBankAdd", vec.get(1).toString());
            
            new ReportBean().jasperReport("InOperative", "text/html", new JRBeanCollectionDataSource(inoperativeMark), fillParams, "Inoperative");
            } else {
                this.setMessage("There does not exist Any data..");
                setIsdisable(true);
                return "There does not exist Any data..";
            }
            flag = "true";
            this.setViewID("/report/ReportPagePopUp.jsp"); 
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return flag;
    }

    public void markingClick() throws NamingException {
        try {
            if (inoperativeMark.size() <= 0) {
                this.setMessage("Data does not Exist");
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String result = remoteObject.inoperativeMark(getOrgnBrCode(), acType, inoperativeMark, getUserName(), formatter.format(tillDate));
            this.setMessage(result);
            inoperativeMark = new ArrayList<Table>();
            setAcType("--Select--");
            setDays("--Select--");
            
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String refreshForm() {
        inoperativeMark = new ArrayList<Table>();
        setAcType("--Select--");
        setDays("--Select--");
        this.setMessage("");
        return "";
    }

    public String exitForm() {
        inoperativeMark = new ArrayList<Table>();
        setAcType("--Select--");
        setDays("--Select--");
        this.setMessage("");
        return "case1";

    }
}
