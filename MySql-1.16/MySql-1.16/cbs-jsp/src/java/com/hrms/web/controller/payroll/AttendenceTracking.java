/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.AttendenceCallenderGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrAttendanceMaintenancePKTO;
import com.hrms.common.to.HrAttendanceMaintenanceTO;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import java.util.Iterator;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class AttendenceTracking extends BaseBean {

    private static final Logger logger = Logger.getLogger(AttendenceTracking.class);
    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private String compCode;
    private String cmpname;
    private String user;
    private String password;
    private String enteredby;
    private String authby;
    private int defaultComp;
    private List l;
    Date d2;
    private List<AttendenceCallenderGrid> resultgrid = new ArrayList<AttendenceCallenderGrid>();
    private AttendenceCallenderGrid currentitem;
    private String err;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /** Creates a new instance of AttendenceTracking */
    public AttendenceTracking() {
        try{
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            initData();
        } catch (Exception e) {
            logger.error("Exception occured while executing method AttendenceTracking()", e);
            logger.error("AttendenceTracking()", e);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthby() {
        return authby;
    }

    public void setAuthby(String authby) {
        this.authby = authby;
    }

    public String getEnteredby() {
        return enteredby;
    }

    public void setEnteredby(String enteredby) {
        this.enteredby = enteredby;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<AttendenceCallenderGrid> getResultgrid() {
        return resultgrid;
    }

    public void setResultgrid(List<AttendenceCallenderGrid> resultgrid) {
        this.resultgrid = resultgrid;
    }

    public AttendenceCallenderGrid getCurrentitem() {
        return currentitem;
    }

    public void setCurrentitem(AttendenceCallenderGrid currentitem) {
        this.currentitem = currentitem;
    }

    public String getCmpname() {
        return cmpname;
    }

    public void setCmpname(String cmpname) {
        this.cmpname = cmpname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List getL() {
        return l;
    }

    public void setL(List l) {
        this.l = l;
    }

    public void initData() {
        try {
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            this.setCmpname(obj.getCompanyName(Integer.parseInt(compCode)));
            List l = obj.getcalander(Integer.parseInt(compCode));
            Iterator itr = l.iterator();
            Object[] o = (Object[]) itr.next();
            Date d = (Date) o[0];
            Date d1 = (Date) o[1];
            d2 = new java.util.Date();
            int i = d2.getMonth();
            if (i >= 3) {
                d.setMonth(i);
                d2 = d;
            } else {
                d1.setMonth(i);
                d2 = d1;
            }
        } catch (Exception e) {
        }
    }

//Load The Data On View Attendance
    public void getdatagriditem() {
        try {
            if (this.getUser().equals("") || this.getUser() == null) {
                this.setErr("Enter the User Name");
                return;
            }
            if (this.getPassword().equals("") || this.getPassword() == null) {
                this.setErr("Enter the Password");
                return;
            }
            l = new ArrayList();
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            l = obj.viewdata(2, this.getUser(), this.getPassword(), d2);
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy ");
            if (l.size() != 0) {
                Iterator itr = l.iterator();
                while (itr.hasNext()) {
                    AttendenceCallenderGrid grid = new AttendenceCallenderGrid();
                    Object[] o = (Object[]) itr.next();
                    grid.setDate(formatter1.format(((Date) o[2])));
                    grid.setMonth(getMonthString(Integer.parseInt(formatter1.format(((Date) o[2])).substring(3, 5))));
                    grid.setTimein(formatter.format(((Date) o[0])));
                    grid.setTimeout(formatter.format(((Date) o[1])));
                    resultgrid.add(grid);
                }
            } else {
                this.setErr("No Data Found");
            }

            this.setUser("");
            this.setPassword("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMonthString(int m) {
        String monthString;
        switch (m) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    public void savedataAttend() {

        try {
            if (this.getUser().equals("") || this.getUser() == null) {
                this.setErr("Enter the User Name");
                return;
            }
            if (this.getPassword().equals("") || this.getPassword() == null) {
                this.setErr("Enter the Password");
                return;
            }
            HrAttendanceMaintenanceTO hrAttendanceMaintenance = new HrAttendanceMaintenanceTO();
            HrAttendanceMaintenancePKTO hrAttendanceMaintenancePK = new HrAttendanceMaintenancePKTO();
            hrAttendanceMaintenancePK.setCompCode(Integer.parseInt(compCode));
            //Date d = new java.util.Date();
            Date d = new Date();
            d.setDate(1);
            d.setMonth(0);
            d.setYear(0);
            hrAttendanceMaintenancePK.setAttenDate(Date.class.newInstance());
            hrAttendanceMaintenance.setTimeIn(Date.class.newInstance());
            hrAttendanceMaintenance.setTimeOut(d);
            hrAttendanceMaintenance.setAttenStatusFixed('N');
            hrAttendanceMaintenance.setDefaultComp(defaultComp);
            hrAttendanceMaintenance.setAuthBy(getUserName());
            hrAttendanceMaintenance.setEnteredBy(getUserName());
            hrAttendanceMaintenance.setModDate(Date.class.newInstance());
            hrAttendanceMaintenance.setStatFlag("Y");
            hrAttendanceMaintenance.setStatUpFlag("Y");
            hrAttendanceMaintenance.setHrAttendanceMaintenancePKTO(hrAttendanceMaintenancePK);
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            String s = obj.insert(this.getUser(), this.getPassword(), hrAttendanceMaintenance);
            setErr(s);
            this.setUser("");
            this.setPassword("");
        } catch (Exception e) {
        }
    }

    public void reset() {
        this.setUser("");
        this.setPassword("");
        this.setErr("");
        AttendenceCallenderGrid grid = new AttendenceCallenderGrid();
        grid.setDate("");
        grid.setMonth("");
        grid.setTimein("");
        grid.setTimeout("");
        resultgrid.clear();
    }

    public String btnExitAction() {
        reset();
        return "case1";
    }
}
