/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.ChargesObject;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.InoperativeChargesFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.misc.IncidentalTable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class CentralizedIncidentailCharges extends BaseBean {

    InoperativeChargesFacadeRemote remoteObject;
    private String status;
    private List<SelectItem> acctTypeOption;
    private String acctype;
    private Date fromDate;
    private Date toDate;
    private String charge;
    private String debAcctype;
    private String crdAcctype;
    private List<SelectItem> statusType;
    private String fYear;
    private String tillDate;
    private String message;
    private String amount;
    private String list;
    private List<IncidentalTable> incis;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String crdaccount;
    private boolean flag;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private boolean flag5;
    private boolean flag6;
    private String mode;
    List<ChargesObject> resultList1;
    List<ChargesObject> resultList2;
    private String viewID = "/pages/master/sm/test.jsp";
    private String branch;
    private List<SelectItem> branchList;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
    }

    public boolean isFlag4() {
        return flag4;
    }

    public void setFlag4(boolean flag4) {
        this.flag4 = flag4;
    }

    public boolean isFlag5() {
        return flag5;
    }

    public void setFlag5(boolean flag5) {
        this.flag5 = flag5;
    }

    public boolean isFlag6() {
        return flag6;
    }

    public void setFlag6(boolean flag6) {
        this.flag6 = flag6;
    }

    public List<SelectItem> getStatusType() {
        return statusType;
    }

    public void setStatusType(List<SelectItem> statusType) {
        this.statusType = statusType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getAcctype() {
        return acctype;
    }

    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getDebAcctype() {
        return debAcctype;
    }

    public void setDebAcctype(String debAcctype) {
        this.debAcctype = debAcctype;
    }

    public String getCrdAcctype() {
        return crdAcctype;
    }

    public void setCrdAcctype(String crdAcctype) {
        this.crdAcctype = crdAcctype;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public List<IncidentalTable> getIncis() {
        return incis;
    }

    public void setIncis(List<IncidentalTable> incis) {
        this.incis = incis;
    }

    public String getCrdaccount() {
        return crdaccount;
    }

    public void setCrdaccount(String crdaccount) {
        this.crdaccount = crdaccount;
    }

    public CentralizedIncidentailCharges() {
        try {
            remoteObject = (InoperativeChargesFacadeRemote) ServiceLocator.getInstance().lookup("InoperativeChargesFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            branchList = new ArrayList<>();
            branchList.add(new SelectItem("0A", "ALL"));

            statusType = new ArrayList<SelectItem>();
            statusType.add(new SelectItem("Operative"));
            statusType.add(new SelectItem("InOperative"));
            this.setMode("CAL");
            // dropDownAdd();
            acctTypeOption = new ArrayList<SelectItem>();
            acctTypeOption.add(new SelectItem("ALL"));
            fYearData();
            setToDate(date);
            reSet();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void dropDownAdd() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.accType();
            acctTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctTypeOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void fYearData() {
        String year = getTodayDate().toString().substring(6, 10);
        int yyyy = Integer.parseInt(year);
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            setfYear(remoteObject.fYearData(this.getOrgnBrCode()).toString());
            int fy = Integer.parseInt(fYear);
            if (!fYear.isEmpty()) {
                String date1 = "01" + '/' + "04" + '/' + fy;
                setFromDate(sdf1.parse(date1));
            } else {
                setFromDate(sdf1.parse("01" + '/' + "04" + '/' + yyyy));
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void calculationData() {
        try {
            this.setMode("CAL");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<IncidentalTable>();
            this.setMessage("");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (this.charge == null || this.charge.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Minimum Charge Per Page");
                return;
            }
            if (this.charge.contains(".")) {
                this.setMessage("Please Enter Valid Charges.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\[0-9]+)?)+");
            Matcher codeCheck = p.matcher(this.charge);
            if (!codeCheck.matches()) {
                this.setMessage("Please Enter Valid Charges.");
                return;
            }

            if (Double.parseDouble(this.charge) < 0) {
                this.setMessage("Charge Should Be Positive ");
                return;
            }
            if (this.fromDate == null) {
                this.setMessage("Please Enter The From Date.");
                return;
            }
            if (this.toDate == null) {
                this.setMessage("Please Enter The To Date.");
                return;
            }
            NumberFormat formatter = new DecimalFormat("#0.00");
            String enterDate = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);
            resultList1 = remoteObject.calculateChargesAllBranch(this.acctype, Integer.parseInt(charge), ymd.format(this.fromDate),
                    ymd.format(this.toDate), status, getOrgnBrCode(), getUserName(), enterDate);

            if (resultList1.size() > 0) {
                if (resultList1.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                    double amt = 0f;
                    for (ChargesObject tmpBean : resultList1) {
                        amt = amt + tmpBean.getPenalty();
                        IncidentalTable tab = new IncidentalTable();
                        tab.setAccno(tmpBean.getAcNo());
                        tab.setName(tmpBean.getCustName());
                        tab.setPenlty(tmpBean.getPenalty());
                        tab.setLim(tmpBean.getLimit());

                        tab.setOptst(String.valueOf(tmpBean.getOptStatus()));
                        tab.setStatus1(String.valueOf(tmpBean.getStatus()));
                        tab.setTran(String.valueOf(tmpBean.getTrans()));
                        incis.add(tab);
                    }
                    this.setAmount(formatter.format(amt));
                    setCrdAcctype(amount);
                    setDebAcctype(amount);
                    flag = true;
                    flag1 = true;
                    flag2 = true;
                    flag3 = true;
                    flag4 = true;
                    flag5 = true;
                    flag6 = false;
                    creditAcData();
                    showReport();
                } else {
                    this.setAmount("");
                    setMessage(resultList1.get(0).getMsg());
                }
            } else {
                this.setAmount("");
                setMessage("Data does not exist for Incidental Charges.");
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void creditAcData() {
        try {
            String crdata = remoteObject.crAccountAll(acctype);
            if (crdata.equalsIgnoreCase("") || crdata == null) {
                setCrdaccount("");
            } else {
                setCrdaccount(crdata);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void postData() {
        this.setMessage("");
        try {
            this.setMode("CAL");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String dt = ymd.format(new Date());
            String postvalue = remoteObject.postDataAllBranch(getOrgnBrCode(), getUserName(), dt, crdaccount,
                    acctype, ymd.format(this.fromDate), ymd.format(this.toDate),
                    Integer.parseInt(charge), status);

            this.setMessage(postvalue);

            if (postvalue == null || postvalue.equalsIgnoreCase("")) {
                setMessage("System error occured.");
                return;
            }
            if (postvalue.contains("Problem in posting-->")) {
                setMessage(postvalue);
            }
            String tmpTrsNo = "0";
            if (postvalue.contains("Transaction posted successfully and transfer batch no")) {
                tmpTrsNo = postvalue.substring(postvalue.indexOf("-") + 1);
            }
            setMessage(postvalue);
            this.setMode("POST");
            String v[] = tmpTrsNo.split(",");
            String trsNo1 = "";
            for (int i = 0; i < v.length; i++) {
                trsNo1 = v[i];
                if (!trsNo1.equalsIgnoreCase("")) {
                    resultList2 = remoteObject.reportPrintDetailAllBranch(Float.parseFloat(trsNo1), acctype, getUserName(), dt, getOrgnBrCode());
                }
            }
            
            if (resultList2.get(0).getMsg() == null) {
                this.setMessage(message + " And problem occured in report generation.");
                return;
            }
            if (!resultList2.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                this.setMessage(message + " so " + resultList2.get(0).getMsg());
                return;
            }
            showReport();
            reSet1();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void reSet1() {
        try {
            incis = new ArrayList<IncidentalTable>();
            this.setCrdAcctype("0.00");
            this.setDebAcctype("0.00");
            this.setCrdaccount("");
            setCharge(remoteObject.getMinimumIncidentalCharges());
            flag = false;
            flag1 = false;
            flag2 = false;
            flag3 = false;
            flag4 = false;
            flag5 = false;
            flag6 = true;
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void reSet() {
        try {
            incis = new ArrayList<IncidentalTable>();
            this.setCrdAcctype("0.00");
            this.setDebAcctype("0.00");
            this.setCrdaccount("");
            this.setMessage("");
            setCharge(remoteObject.getMinimumIncidentalCharges());
            flag = false;
            flag1 = false;
            flag2 = false;
            flag3 = false;
            flag4 = false;
            flag5 = false;
            flag6 = true;
            this.setMode("CAL");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            reSet();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }

    public void showReport() {
        try {
            String report = "Incidental Charges Report";
            Map fillParams = new HashMap();
            fillParams.put("pReportDate", sdf.format(this.fromDate) + " To " + sdf.format(this.toDate));
            fillParams.put("operative", status);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            if (mode.equalsIgnoreCase("CAL")) {
                List<ChargesObject> intList = (List<ChargesObject>) resultList1;
                new ReportBean().jasperReport("Incidental_Charges", "text/html", new JRBeanCollectionDataSource(intList), fillParams, report);

            } else {
                List<ChargesObject> postList = (List<ChargesObject>) resultList2;
                new ReportBean().jasperReport("Incidental_Charges_Post", "text/html", new JRBeanCollectionDataSource(postList), fillParams, report);
            }
            this.setViewID("/report/ReportPagePopUp.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
