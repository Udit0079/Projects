/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.CustomerEnquiryPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class CustomerEnquiry extends BaseBean {

    private String errorMsg;
    private String customerId;
    private String gender;
    private String homeType;
    private List<SelectItem> ddList = new ArrayList<SelectItem>();
    private List<SelectItem> mmList = new ArrayList<SelectItem>();
    private List<SelectItem> ocupationList = new ArrayList<SelectItem>();
    private List<SelectItem> genderlist = new ArrayList<SelectItem>();
    private List<SelectItem> hometypeList = new ArrayList<SelectItem>();
    private List<SelectItem> branchList = new ArrayList<SelectItem>();
    private List<SelectItem> custStatusList = new ArrayList<SelectItem>();
    private OtherReportFacadeRemote remote;
    private CommonReportMethodsRemote common;
    private String ocupation;
    private String custStatus;
    private String branch = "0";
    private double balance;
    private double avgBalance;
    private String dd;
    private String mm;
    private String area;
    private double income;
    private int fromAge;
    private int toAge;
    boolean fielddisable;
    private String acType;
    private List<SelectItem> acTypeList = new ArrayList<SelectItem>();
    private double balFrom, balTo;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates a new instance of CustomerEnquiry
     */
    public CustomerEnquiry() {
        try {
            remote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        } catch (ServiceLocatorException ex) {
            Logger.getLogger(CustomerEnquiry.class.getName()).log(Level.SEVERE, null, ex);
        }
        ddList = new ArrayList<SelectItem>();
        // ddList.clear();
        mmList = new ArrayList<SelectItem>();
        ocupationList = new ArrayList<SelectItem>();
        genderlist = new ArrayList<SelectItem>();
        hometypeList = new ArrayList<SelectItem>();
        branchList = new ArrayList<SelectItem>();
        initData();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public double getBalFrom() {
        return balFrom;
    }

    public void setBalFrom(double balFrom) {
        this.balFrom = balFrom;
    }

    public double getBalTo() {
        return balTo;
    }

    public void setBalTo(double balTo) {
        this.balTo = balTo;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public List<SelectItem> getDdList() {
        return ddList;
    }

    public void setDdList(List<SelectItem> ddList) {
        this.ddList = ddList;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public List<SelectItem> getMmList() {
        return mmList;
    }

    public void setMmList(List<SelectItem> mmList) {
        this.mmList = mmList;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public int getFromAge() {
        return fromAge;
    }

    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAvgBalance() {
        return avgBalance;
    }

    public void setAvgBalance(double avgBalance) {
        this.avgBalance = avgBalance;
    }

    public String getOcupation() {
        return ocupation;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public List<SelectItem> getOcupationList() {
        return ocupationList;
    }

    public void setOcupationList(List<SelectItem> ocupationList) {
        this.ocupationList = ocupationList;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public List<SelectItem> getCustStatusList() {
        return custStatusList;
    }

    public void setCustStatusList(List<SelectItem> custStatusList) {
        this.custStatusList = custStatusList;
    }

    public List<SelectItem> getGenderlist() {
        return genderlist;
    }

    public void setGenderlist(List<SelectItem> genderlist) {
        this.genderlist = genderlist;
    }

    public List<SelectItem> getHometypeList() {
        return hometypeList;
    }

    public void setHometypeList(List<SelectItem> hometypeList) {
        this.hometypeList = hometypeList;
    }

    public boolean isFielddisable() {
        return fielddisable;
    }

    public void setFielddisable(boolean fielddisable) {
        this.fielddisable = fielddisable;
    }

    private void initData() {
        try {
            List l = remote.getRefcodeDesc("152");
            List brnList = remote.getBranchCode(Integer.parseInt(getOrgnBrCode()));
            Vector vtr;
            ocupationList.add(new SelectItem("--Select--", "--Select--"));
            l = remote.getRefcodeDesc("21");
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                ocupationList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
            acTypeList.add(new SelectItem("ALL", "ALL"));
            l = remote.getAcctTypeList();
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                acTypeList.add(new SelectItem(vtr.get(0).toString()));
            }
            custStatusList.add(new SelectItem("ALL", "ALL"));
            l = remote.getRefcodeDesc("025");
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                custStatusList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
            genderlist.add(new SelectItem("--Select--", "--Select--"));
            genderlist.add(new SelectItem("M", "Male"));
            genderlist.add(new SelectItem("F", "Female"));

            mmList.add(new SelectItem("MM", "MM"));
            ddList.add(new SelectItem("DD", "DD"));
            for (int i = 1; i < 13; i++) {
                mmList.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
                ddList.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
            }
            for (int i = 13; i < 32; i++) {
                ddList.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
            }
            branchList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < brnList.size(); i++) {
                vtr = (Vector) brnList.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }
            hometypeList.add(new SelectItem("--Select--", "--Select--"));
            hometypeList.add(new SelectItem("Own", "Own"));
            hometypeList.add(new SelectItem("Rent", "Rent"));
            setFielddisable(false);
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String viewReport() throws ParseException {
        errorMsg = "";
        if (branch.equals("0")) {
            setErrorMsg("Please select branch");
            return null;
        }
        if (!validateData()) {
            return null;
        }
        String sb = "SELECT CB.CUSTOMERID,CI.ACNO,CB.CUSTNAME,"
                + "CASE CB.GENDER WHEN 'M' THEN 'MALE' WHEN 'F' THEN 'FEMALE' WHEN 'C' THEN 'COMPANY' WHEN '0' THEN ''    WHEN 'NULL' THEN '' END AS GENDER,"
                + "CASE CB.MARITALSTATUS WHEN 'UM' THEN 'UNMARRIED' WHEN 'U' THEN 'UNMARRIED' WHEN 'w' THEN 'WIDOW' WHEN 'M' THEN 'MARRIED'"
                + "WHEN '0' THEN '' WHEN 'WR' THEN '' WHEN 'NULL' THEN '' END AS MARITALSTATUS,CB.FATHERNAME,CB.DATEOFBIRTH,CB.PERADDRESSLINE1,"
                + "CM.OCCUPATIONCODE,CM.COMMUNITYCODE FROM cbs_customer_master_detail CB "
                + "LEFT OUTER JOIN customerid CI ON CB.CUSTOMERID = CI.CUSTID "
                + "LEFT OUTER JOIN cbs_cust_misinfo CM ON CB.CUSTOMERID = CM.CUSTOMERID ";
        String sb1 = "";

        if (customerId == null || customerId.equalsIgnoreCase("")) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CB.customerid ='" + customerId + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " CB.customerid ='" + customerId + "' ";
                } else {
                    sb = sb + " where CB.customerid ='" + customerId + "' ";
                }
            }
            setFielddisable(true);
        }

        if ((gender.equalsIgnoreCase("--Select--"))) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CB.gender ='" + gender + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " CB.gender ='" + gender + "' ";
                } else {
                    sb = sb + " where CB.gender ='" + gender + "' ";
                }
            }
        }


        if ((dd.equalsIgnoreCase("DD") || mm.equalsIgnoreCase("MM"))) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and (day(CB.dateofbirth) ='" + dd + "') and (month(CB.dateofbirth) ='" + mm + "') ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and (day(CB.dateofbirth) ='" + dd + "') and (month(CB.dateofbirth) ='" + mm + "') ";
                } else {
                    sb = sb + " where (day(CB.dateofbirth) ='" + dd + "') and (month(CB.dateofbirth) ='" + mm + "') ";
                }
            }
        }

        if (area == null || area.trim().equalsIgnoreCase("")) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and (CB.perAddressLine1 like '%" + area + "%' or CB.mailAddressLine1 like '%+area+%' ) ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and (CB.perAddressLine1 like '%" + area + "%' or CB.mailAddressLine1 like '%+area+%' ) ";
                } else {
                    sb = sb + " where (CB.perAddressLine1 like '%" + area + "%' or CB.mailAddressLine1 like '%+area+%' ) ";
                }
            }
        }


        if (income == 0.0 || income == 0) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CB.custsalary ='" + income + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and CB.custsalary ='" + income + "' ";
                } else {
                    sb = sb + " where CB.custsalary ='" + income + "' ";
                }
            }
        }



        if ((ocupation.equalsIgnoreCase("--Select--"))) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CM.OCCUPATIONCODE ='" + ocupation + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and CM.OCCUPATIONCODE ='" + ocupation + "' ";
                } else {
                    sb = sb + " where CM.OCCUPATIONCODE ='" + ocupation + "' ";
                }
            }
        }
        if (sb.contains("and")) {
            sb = sb + " and substring(CI.ACNO,1,2) = '" + branch + "'";
        } else {
            if (sb.contains("where")) {
                sb = sb + " and substring(CI.ACNO,1,2) = '" + branch + "'";
            } else {
                sb = sb + " where substring(CI.ACNO,1,2) = '" + branch + "'";
            }
        }
        /**
         * ***** Addition of AcType    ******
         */
        if (sb.contains("and")) {
            if (!acType.equalsIgnoreCase("all")) {
                sb = sb + " and substring(CI.ACNO,3,2) = '" + acType + "' ";
            }
        } else {
            if (sb.contains("where")) {
                if (!acType.equalsIgnoreCase("all")) {
                    sb = sb + " and substring(CI.ACNO,3,2) = '" + acType + "' ";
                }
            } else {
                if (!acType.equalsIgnoreCase("all")) {
                    sb = sb + " where substring(CI.ACNO,3,2) = '" + acType + "' ";
                }
            }
        }
        /**
         * ***** Addition of AcType    ******
         */
        /**
         * ***** Addition of Customer Status    ******
         */
        if (sb.contains("and")) {
            if (!custStatus.equalsIgnoreCase("all")) {
                sb = sb + " and CB.CustStatus = '" + custStatus + "' ";
            }
        } else {
            if (sb.contains("where")) {
                if (!custStatus.equalsIgnoreCase("all")) {
                    sb = sb + " and CB.CustStatus = '" + custStatus + "' ";
                }
            } else {
                if (!custStatus.equalsIgnoreCase("all")) {
                    sb = sb + " where CB.CustStatus = '" + custStatus + "' ";
                }
            }
        }
        /**
         * ***** Addition of Customer Status    ******
         */
        sb = sb + " order by cast(CB.CUSTOMERID as unsigned)";

//        if ((balance == 0.0 || balance == 0)) {
//            sb1 = sb1 + " and balance >=" + balance + "";
//        }

        if (balFrom == 0.0) {
            if (balTo == 0.0) {
                sb1 = sb1 /*+ " and balance >=" + balance*/;
            } else {
                sb1 = sb1 + " and balance >= " + balFrom + " and  balance <= " + balTo;
            }
        } else {
            sb1 = sb1 + " and balance >= " + balFrom + " and  balance <= " + balTo;
        }

        try {
            List<CustomerEnquiryPojo> result = remote.getCustomerEnquiry(sb, sb1, balance, fromAge, toAge, ymd.parse(ymd.format(new Date())), avgBalance);
            if (!result.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Customer Enquiry Report");
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                new ReportBean().jasperReport("CustomerEnquiry", "text/html", new JRBeanCollectionDataSource(result), fillParams, "Customer Enquiry Report");
                return "report";

            } else {
                errorMsg = "Data does not exist.";
            }
            setFielddisable(false);
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        }
        return null;
    }

    public String pdfDownLoad() throws ParseException, IOException {
        errorMsg = "";
        if (branch.equals("0")) {
            setErrorMsg("Please select branch");
            return null;
        }
        if (!validateData()) {
            return null;
        }
        String sb = "SELECT CB.CUSTOMERID,CI.ACNO,CB.CUSTNAME,"
                + "CASE CB.GENDER WHEN 'M' THEN 'MALE' WHEN 'F' THEN 'FEMALE' WHEN 'C' THEN 'COMPANY' WHEN '0' THEN ''    WHEN 'NULL' THEN '' END AS GENDER,"
                + "CASE CB.MARITALSTATUS WHEN 'UM' THEN 'UNMARRIED' WHEN 'U' THEN 'UNMARRIED' WHEN 'w' THEN 'WIDOW' WHEN 'M' THEN 'MARRIED'"
                + "WHEN '0' THEN '' WHEN 'WR' THEN '' WHEN 'NULL' THEN '' END AS MARITALSTATUS,CB.FATHERNAME,CB.DATEOFBIRTH,CB.PERADDRESSLINE1,"
                + "CM.OCCUPATIONCODE,CM.COMMUNITYCODE FROM cbs_customer_master_detail CB "
                + "LEFT OUTER JOIN customerid CI ON CB.CUSTOMERID = CI.CUSTID "
                + "LEFT OUTER JOIN cbs_cust_misinfo CM ON CB.CUSTOMERID = CM.CUSTOMERID ";
        String sb1 = "";

        if (customerId == null || customerId.equalsIgnoreCase("")) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CB.customerid ='" + customerId + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " CB.customerid ='" + customerId + "' ";
                } else {
                    sb = sb + " where CB.customerid ='" + customerId + "' ";
                }
            }
            setFielddisable(true);
        }

        if ((gender.equalsIgnoreCase("--Select--"))) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CB.gender ='" + gender + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " CB.gender ='" + gender + "' ";
                } else {
                    sb = sb + " where CB.gender ='" + gender + "' ";
                }
            }
        }


        if ((dd.equalsIgnoreCase("DD") || mm.equalsIgnoreCase("MM"))) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and (day(CB.dateofbirth) ='" + dd + "') and (month(CB.dateofbirth) ='" + mm + "') ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and (day(CB.dateofbirth) ='" + dd + "') and (month(CB.dateofbirth) ='" + mm + "') ";
                } else {
                    sb = sb + " where (day(CB.dateofbirth) ='" + dd + "') and (month(CB.dateofbirth) ='" + mm + "') ";
                }
            }
        }

        if (area == null || area.trim().equalsIgnoreCase("")) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and (CB.perAddressLine1 like '%" + area + "%' or CB.mailAddressLine1 like '%+area+%' ) ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and (CB.perAddressLine1 like '%" + area + "%' or CB.mailAddressLine1 like '%+area+%' ) ";
                } else {
                    sb = sb + " where (CB.perAddressLine1 like '%" + area + "%' or CB.mailAddressLine1 like '%+area+%' ) ";
                }
            }
        }


        if (income == 0.0 || income == 0) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CB.custsalary ='" + income + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and CB.custsalary ='" + income + "' ";
                } else {
                    sb = sb + " where CB.custsalary ='" + income + "' ";
                }
            }
        }



        if ((ocupation.equalsIgnoreCase("--Select--"))) {
        } else {
            if (sb.contains("and")) {
                sb = sb + " and CM.OCCUPATIONCODE ='" + ocupation + "' ";
            } else {
                if (sb.contains("where")) {
                    sb = sb + " and CM.OCCUPATIONCODE ='" + ocupation + "' ";
                } else {
                    sb = sb + " where CM.OCCUPATIONCODE ='" + ocupation + "' ";
                }
            }
        }
        if (sb.contains("and")) {
            sb = sb + " and substring(CI.ACNO,1,2) = '" + branch + "'";
        } else {
            if (sb.contains("where")) {
                sb = sb + " and substring(CI.ACNO,1,2) = '" + branch + "'";
            } else {
                sb = sb + " where substring(CI.ACNO,1,2) = '" + branch + "'";
            }
        }
        /**
         * ***** Addition of AcType    ******
         */
        if (sb.contains("and")) {
            if (!acType.equalsIgnoreCase("all")) {
                sb = sb + " and substring(CI.ACNO,3,2) = '" + acType + "' ";
            }
        } else {
            if (sb.contains("where")) {
                if (!acType.equalsIgnoreCase("all")) {
                    sb = sb + " and substring(CI.ACNO,3,2) = '" + acType + "' ";
                }
            } else {
                if (!acType.equalsIgnoreCase("all")) {
                    sb = sb + " where substring(CI.ACNO,3,2) = '" + acType + "' ";
                }
            }
        }
        /**
         * ***** Addition of AcType    ******
         */
        /**
         * ***** Addition of Customer Status    ******
         */
        if (sb.contains("and")) {
            if (!custStatus.equalsIgnoreCase("all")) {
                sb = sb + " and CB.CustStatus = '" + custStatus + "' ";
            }
        } else {
            if (sb.contains("where")) {
                if (!custStatus.equalsIgnoreCase("all")) {
                    sb = sb + " and CB.CustStatus = '" + custStatus + "' ";
                }
            } else {
                if (!custStatus.equalsIgnoreCase("all")) {
                    sb = sb + " where CB.CustStatus = '" + custStatus + "' ";
                }
            }
        }
        /**
         * ***** Addition of Customer Status    ******
         */
        sb = sb + " order by cast(CB.CUSTOMERID as unsigned)";

//        if ((balance == 0.0 || balance == 0)) {
//            sb1 = sb1 + " and balance >=" + balance + "";
//        }

        if (balFrom == 0.0) {
            if (balTo == 0.0) {
                sb1 = sb1 /*+ " and balance >=" + balance*/;
            } else {
                sb1 = sb1 + " and balance >= " + balFrom + " and  balance <= " + balTo;
            }
        } else {
            sb1 = sb1 + " and balance >= " + balFrom + " and  balance <= " + balTo;
        }

        try {
            List<CustomerEnquiryPojo> result = remote.getCustomerEnquiry(sb, sb1, balance, fromAge, toAge, ymd.parse(ymd.format(new Date())), avgBalance);
            if (!result.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Customer Enquiry Report");
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                new ReportBean().openPdf("Customer Enquiry Report_"+ymd.format(sdf.parse(getTodayDate())), "CustomerEnquiry", new JRBeanCollectionDataSource(result), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "Customer Enquiry Report");

            } else {
                errorMsg = "Data does not exist.";
            }
            setFielddisable(false);
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        }
        return null;
    }

    public boolean validateData() {
        if (!dd.equalsIgnoreCase("DD")) {
            if (mm.equalsIgnoreCase("MM")) {
                errorMsg = "Please select Month";
                return false;
            }
        }
        if (!mm.equalsIgnoreCase("MM")) {
            if (dd.equalsIgnoreCase("DD")) {
                errorMsg = "Please select Days";
                return false;
            }
        }
        if (fromAge != 0.0) {
            if (toAge == 0.0) {
                errorMsg = "Please enter To Age";
                return false;
            }
        }
        if (toAge != 0.0) {
            if (fromAge == 0.0) {
                errorMsg = "Please enter From Age";
                return false;
            }
        }
        return true;
    }

    public void refresh() {
        setErrorMsg("");
        setBranch("0");
        setGender("--Select--");
        setIncome(0.0);
        setAvgBalance(0.0);
        setFromAge(0);
        setToAge(0);
        setCustomerId("");
        setArea("");
        setBalance(0.0);
        setDd("DD");
        setMm("MM");
        setOcupation("--Select--");
        setFielddisable(false);

    }

    public void disabledfields() {
        if (customerId == null || customerId.equalsIgnoreCase("")) {
        } else {
            setFielddisable(true);
        }



    }
}
