package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.master.Condition;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.event.ActionEvent;

public final class TdCondition extends BaseBean {

    TermDepositMasterFacadeRemote tdRemote;
    private String userName = getUserName();
    Date dates;
    private List<Condition> condits;
    private String custType;
    private String tdAmounts;
    private String tdMonthly;
    private String tdCumulative;
    private String seqNumber;
    int currentRow;
    Condition currentItem;
    String message;
    private String flag;
    private String flag1;
    private String flag2;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(String seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getTdCumulative() {
        return tdCumulative;
    }

    public void setTdCumulative(String tdCumulative) {
        this.tdCumulative = tdCumulative;
    }

    public String getTdMonthly() {
        return tdMonthly;
    }

    public void setTdMonthly(String tdMonthly) {
        this.tdMonthly = tdMonthly;
    }

    public String getTdAmounts() {
        return tdAmounts;
    }

    public void setTdAmounts(String tdAmounts) {
        this.tdAmounts = tdAmounts;
    }

    public Condition getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Condition currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public List<Condition> getCondits() {
        return condits;
    }

    public void setCondits(List<Condition> condits) {
        this.condits = condits;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TdCondition() {
        try {
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            setDates(new Date());
            flag = "false";
            flag1 = "true";
            flag2 = "true";
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getList() {
        try {
            condits = new ArrayList<Condition>();
            List tableList = new ArrayList();
            tableList = tdRemote.tableDataTdCondition();
            if (!tableList.isEmpty()) {
                for (int i = 0; i < tableList.size(); i++) {
                    Vector ele = (Vector) tableList.get(i);
                    Condition conditions = new Condition();
                    String s = String.valueOf(Float.parseFloat(ele.get(0).toString()));
                    s = s.substring(0, s.indexOf("."));
                    conditions.setsNumber(s);
                    String status = ele.get(1).toString();
                    if (status.equals("N")) {
                        conditions.setStatus("NEW");
                    } else if (status.equals("R")) {
                        conditions.setStatus("RENEWAL");
                    }
                    conditions.setApplicableDate(ele.get(2).toString());
                    conditions.setTdAmount(Integer.parseInt(ele.get(3).toString()));
                    conditions.setTdDayMonthly(Integer.parseInt(ele.get(4).toString()));
                    conditions.setTdDayCumulat(Integer.parseInt(ele.get(5).toString()));
                    conditions.setEnterBy(ele.get(6).toString());
                    conditions.setLastUpDateDt(ele.get(7).toString());
                    condits.add(conditions);
                }
            } else {
                this.setMessage("Records does not Exist. ");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurApplicableDate() {
        String str = "";
        try {
            this.setMessage("");
            if (dates == null) {
                this.setMessage("Please Enter Applicable Date.");
                return str = "Please Enter Applicable Date.";
            }
            long dateDif = CbsUtil.dayDiff(this.dates, new Date());
            if (dateDif < 0) {
                this.setMessage("You can't select the date after the current date.");
                return str = "You can't select the date after the current date.";
            } else {
                this.setMessage("");
                return str = "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return str;
    }

    public void setRowValues() {
        try {
            flag = "true";
            flag1 = "false";
            flag2 = "false";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTdAmounts(String.valueOf(currentItem.getTdAmount()));
            setTdMonthly(String.valueOf(currentItem.getTdDayMonthly()));
            setTdCumulative(String.valueOf(currentItem.getTdDayCumulat()));
            setDates(sdf.parse(currentItem.getApplicableDate()));
            setSeqNumber(currentItem.getsNumber());
            setCustType(currentItem.getStatus());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (Condition item : condits) {
                if (item.getStatus().equals(accNo)) {
                    currentItem = item;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            flag = "false";
            flag1 = "true";
            flag2 = "true";
            this.setMessage("");
            String custTp = custType;
            if (custTp.equals("NEW")) {
                custTp = "N";
            }
            if (custTp.equals("RENEWAL")) {
                custTp = "R";
            }
            String dataDeleted = tdRemote.gridClick(custTp, Float.parseFloat(seqNumber));
            clearText();
            this.setMessage(dataDeleted);
            getList();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void upDate() {
        try {
            this.setMessage("");
            if (this.custType.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select Customer Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.tdAmounts.equalsIgnoreCase("") || this.tdAmounts.length() == 0) {
                this.setMessage("Please Enter Tds Amount.");
                return;
            }
            Matcher tdAmountsCheck = p.matcher(tdAmounts);
            if (!tdAmountsCheck.matches()) {
                this.setMessage("Please Enter Valid  Tds Amount.");
                return;
            }
            if (this.tdMonthly.equalsIgnoreCase("") || this.tdMonthly.length() == 0) {
                this.setMessage("Please Enter Monthly(In Days).");
                return;
            }
            Matcher tdMonthlyCheck = p.matcher(tdMonthly);
            if (!tdMonthlyCheck.matches()) {
                this.setMessage("Please Enter Valid  Monthly(In Days).");
                return;
            }
            if (this.tdCumulative.equalsIgnoreCase("") || this.tdCumulative.length() == 0) {
                this.setMessage("Please Enter Cumulative(In Days).");
                return;
            }
            Matcher tdCumulativeCheck = p.matcher(tdCumulative);
            if (!tdCumulativeCheck.matches()) {
                this.setMessage("Please Enter Valid  Cumulative(In Days).");
                return;
            }
            if (dates == null) {
                this.setMessage("Please Enter Applicable Date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String dts = ymd.format(this.dates);
            String s5 = onblurApplicableDate();
            if (!s5.equalsIgnoreCase("true")) {
                this.setMessage(s5);
                return;
            } else {
                this.setMessage("");
            }
            String upDateData = tdRemote.upDate(custType, dts, tdAmounts, tdMonthly, tdCumulative, Float.parseFloat(seqNumber), userName);
            clearText();
            this.setMessage(upDateData);
            getList();
            setCustType("--SELECT--");
            setTdAmounts("");
            setTdMonthly("");
            setTdCumulative("");
            upDateData = upDateData.toString().trim();
            if (upDateData.equals("Data Update Successfully")) {
                flag = "false";
                flag1 = "true";
                flag2 = "true";
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurTdsAmount() {
        try {
            this.setMessage("");
            if (tdAmounts.contains(".")) {
                this.setMessage("Please Enter Valid Tds Amount.");
                return "Please Enter Valid Tds Amount.";
            }
            if (this.tdAmounts.equalsIgnoreCase("") || this.tdAmounts.length() == 0) {
                this.setMessage("Please Enter Tds Amount.");
                return "Please Enter Tds Amount.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tdAmountsCheck = p.matcher(tdAmounts);
            if (!tdAmountsCheck.matches()) {
                this.setMessage("Please Enter Valid  Tds Amount.");
                return "Please Enter Valid  Tds Amount.";
            }
            if (Integer.parseInt(tdAmounts) < 0) {
                this.setMessage("Please Enter Valid  Tds Amount.");
                return "Please Enter Valid  Tds Amount.";
            } else {
                this.setMessage("");
                return "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurmonthlyInDays() {
        try {
            this.setMessage("");
            if (this.tdMonthly.equalsIgnoreCase("") || this.tdMonthly.length() == 0) {
                this.setMessage("Please Enter Monthly(In Days).");
                return "Please Enter Monthly(In Days).";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tdMonthlyCheck = p.matcher(tdMonthly);
            if (!tdMonthlyCheck.matches()) {
                this.setMessage("Please Enter Valid  Monthly(In Days).");
                return "Please Enter Valid  Monthly(In Days).";
            }
            if (tdMonthly.contains(".")) {
                this.setMessage("Please Enter Valid Monthly in Days.");
                return "Please Enter Valid Monthly in Days.";
            }
            if (Integer.parseInt(tdMonthly) < 0) {
                this.setMessage("Please Enter Valid Monthly in Days.");
                return "Please Enter Valid Monthly in Days.";
            } else {
                this.setMessage("");
                return "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurCumulativeInDays() {
        try {
            this.setMessage("");
            if (this.tdCumulative.equalsIgnoreCase("") || this.tdCumulative.length() == 0) {
                this.setMessage("Please Enter Cumulative(In Days).");
                return "Please Enter Cumulative(In Days).";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tdCumulativeCheck = p.matcher(tdCumulative);
            if (!tdCumulativeCheck.matches()) {
                this.setMessage("Please Enter Valid  Cumulative(In Days).");
                return "Please Enter Valid  Cumulative(In Days).";
            }
            if (tdCumulative.contains(".")) {
                this.setMessage("Please Enter Valid  Cumulative(In Days).");
                return "Please Enter Valid  Cumulative(In Days).";
            }
            if (Integer.parseInt(tdCumulative) < 0) {
                this.setMessage("Please Enter Valid  Cumulative(In Days).");
                return "Please Enter Valid  Cumulative(In Days).";
            } else {
                this.setMessage("");
                return "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public void save() {
        try {
            flag = "false";
            flag1 = "true";
            flag2 = "true";
            this.setMessage("");
            if (this.custType.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select Customer Type.");
                return;
            }
            String ss = onblurTdsAmount();
            if (!ss.equals("true")) {
                this.setMessage(ss);
                return;
            }
            String ss1 = onblurmonthlyInDays();
            if (!ss1.equals("true")) {
                this.setMessage(ss1);
                return;
            }
            String ss2 = onblurCumulativeInDays();
            if (!ss2.equals("true")) {
                this.setMessage(ss2);
                return;
            }
            if (dates == null) {
                this.setMessage("Please Enter Applicable Date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String dts = ymd.format(this.dates);
            String s5 = onblurApplicableDate();
            if (!s5.equalsIgnoreCase("true")) {
                this.setMessage(s5);
                return;
            } else {
                this.setMessage("");
            }
            String saveData = tdRemote.save(custType, dts, tdAmounts, tdMonthly, tdCumulative, userName);
            clearText();
            this.setMessage(saveData);
            getList();
            setCustType("--SELECT--");
            setTdAmounts("");
            setTdMonthly("");
            setTdCumulative("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clearText() {
        try {
            flag = "false";
            flag1 = "true";
            flag2 = "true";
            setCustType("--SELECT--");
            setTdAmounts("");
            setTdMonthly("");
            setTdCumulative("");
            setMessage("");
            setDates(new Date());
            condits = new ArrayList<Condition>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        clearText();
        return "case1";
    }
}