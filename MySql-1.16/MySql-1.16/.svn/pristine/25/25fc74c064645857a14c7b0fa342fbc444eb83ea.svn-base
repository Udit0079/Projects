package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.master.Slab;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.event.ActionEvent;

public final class TdsSlab extends BaseBean {

    TermDepositMasterFacadeRemote tdRemote;
    private String todayDate = getTodayDate();
    Date dates;
    private String custType;
    private List<Slab> slabes;
    private String message;
    int currentRow;
    Slab currentItem;
    private String surcharge;
    private String tdsRate;
    private String tdsAmount;
    private String tdsRatePan;
    private String srctznTdsAmt;
    private String glHead2;
    private int seqNum;
    private String fullGlHead;
    String checkGlHeads;
    private String flag;
    private String flag1;
    private String flag2;
    NumberFormat formatter = new DecimalFormat("#0.00");

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

    public String getCheckGlHeads() {
        return checkGlHeads;
    }

    public void setCheckGlHeads(String checkGlHeads) {
        this.checkGlHeads = checkGlHeads;
    }

    public String getTdsRate() {
        return tdsRate;
    }

    public void setTdsRate(String tdsRate) {
        this.tdsRate = tdsRate;
    }

    public String getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(String tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public String getFullGlHead() {
        return fullGlHead;
    }

    public void setFullGlHead(String fullGlHead) {
        this.fullGlHead = fullGlHead;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public String getGlHead2() {
        return glHead2;
    }

    public void setGlHead2(String glHead2) {
        this.glHead2 = glHead2;
    }

    public Slab getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Slab currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Slab> getSlabes() {
        return slabes;
    }

    public void setSlabes(List<Slab> slabes) {
        this.slabes = slabes;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public String getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }

    public String getTdsRatePan() {
        return tdsRatePan;
    }

    public void setTdsRatePan(String tdsRatePan) {
        this.tdsRatePan = tdsRatePan;
    }

    public String getSrctznTdsAmt() {
        return srctznTdsAmt;
    }

    public void setSrctznTdsAmt(String srctznTdsAmt) {
        this.srctznTdsAmt = srctznTdsAmt;
    }

    public TdsSlab() {
        try {
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            setDates(new Date());
            this.setMessage("");
            slabes = new ArrayList<Slab>();
            flag = "false";
            flag1 = "true";
            flag2 = "true";
            setGlHead2("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getList() {
        try {
            slabes = new ArrayList<Slab>();
            List tableList = new ArrayList();
            tableList = tdRemote.tableDataTdsSlab();
            if (!tableList.isEmpty()) {
                for (int i = 0; i < tableList.size(); i++) {
                    Vector ele = (Vector) tableList.get(i);
                    Slab tdsSlab = new Slab();
                    int abc = Integer.parseInt(ele.get(0).toString());
                    String abcType = null;
                    if (abc == 1) {
                        abcType = "INDIVIDUAL";
                    }
                    if (abc == 2) {
                        abcType = "COMPANY";
                    }
                    if (abc == 3) {
                        abcType = "OTHERS";
                    }
                    tdsSlab.setType(abcType);
                    String applicableRate = ele.get(1).toString();
                    String yy = applicableRate.substring(0, 4);
                    String mm = applicableRate.substring(5, 7);
                    String dd = applicableRate.substring(8, 10);
                    String appli = dd + "/" + mm + "/" + yy;
                    tdsSlab.setApplicableDate(appli);
                    tdsSlab.setTdsAmount(new BigDecimal(Float.parseFloat(ele.get(2).toString())));
                    tdsSlab.setTdsRate(new BigDecimal(Float.parseFloat(ele.get(3).toString())));
                    tdsSlab.setTdsSurcharge(new BigDecimal(Float.parseFloat(ele.get(4).toString())));
                    tdsSlab.setTdsGlHead(ele.get(5).toString());
                    tdsSlab.setEnterBy(ele.get(6).toString());
                    tdsSlab.setLastUpDateDt(ele.get(7).toString());
                    tdsSlab.setsNumber(Integer.parseInt(ele.get(8).toString()));
                    tdsSlab.setTdsRatePan(new BigDecimal(Float.parseFloat(ele.get(9).toString())));
                    tdsSlab.setSrctznTdsAmt(new BigDecimal(Float.parseFloat(ele.get(10).toString())));
                    slabes.add(tdsSlab);
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

    public void setRowValues() {
        try {
            flag = "true";
            flag1 = "false";
            flag2 = "false";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setSurcharge(String.valueOf(currentItem.getTdsSurcharge()));
            setTdsRate(formatter.format(Double.parseDouble(String.valueOf(currentItem.getTdsRate()))));
            setTdsAmount(formatter.format(Double.parseDouble(String.valueOf(currentItem.getTdsAmount()))));
            setDates(sdf.parse(currentItem.getApplicableDate()));
            String custTypes = (currentItem.getType());
            if (custTypes.equals("1")) {
                custTypes = "INDIVIDUAL";
            }
            if (custTypes.equals("2")) {
                custTypes = "COMPANY";
            }
            if (custTypes.equals("3")) {
                custTypes = "OTHERS";
            }
            setCustType(custTypes);
            String glHead = (currentItem.getTdsGlHead());
            String glHeads = glHead.substring(2, 8);
            setGlHead2(glHeads);
            setSeqNum(currentItem.getsNumber());

            setTdsRatePan(formatter.format(Double.parseDouble(String.valueOf(currentItem.getTdsRatePan()))));
            setSrctznTdsAmt(formatter.format(Double.parseDouble(String.valueOf(currentItem.getSrctznTdsAmt()))));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (Slab item : slabes) {
                if (item.getTdsGlHead().equals(accNo)) {
                    currentItem = item;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        try {
            flag = "false";
            flag1 = "true";
            flag2 = "true";
            this.setMessage("");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.custType.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select The Customer Type");
                return;
            }
            if (this.tdsAmount.equalsIgnoreCase("") || this.tdsAmount.length() == 0) {
                this.setMessage("Please Enter tds Amount.");
                return;
            }
            Matcher tdsAmountCheck = p.matcher(tdsAmount);
            if (!tdsAmountCheck.matches()) {
                this.setMessage("Please Enter Valid  tds Amount.");
                return;
            }
            if (Float.parseFloat(tdsAmount) < 0) {
                this.setMessage("Please fill some positive amount.");
                return;
            }
            if (this.tdsAmount.contains(".")) {
                if (this.tdsAmount.substring(this.tdsAmount.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Fill The tds Amount Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.tdsRate.equalsIgnoreCase("") || this.tdsRate.length() == 0) {
                this.setMessage("Please Enter tds Rate.");
                return;
            }
            Matcher tdsRateCheck = p.matcher(tdsRate);
            if (!tdsRateCheck.matches()) {
                this.setMessage("Please Enter Valid  tds Rate.");
                return;
            }
            if (Float.parseFloat(tdsRate) < 0) {
                this.setMessage("Please fill some positive tds Rate.");
                return;
            }
            if (this.tdsRate.contains(".")) {
                if (this.tdsRate.substring(this.tdsRate.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Fill The tds Rate Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.surcharge.contains(".")) {
                if (this.surcharge.substring(this.surcharge.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Fill The surcharge Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.surcharge.equalsIgnoreCase("") || this.surcharge.length() == 0) {
                this.setMessage("Please Enter Surcharge.");
                return;
            }
            Matcher surchargeCheck = p.matcher(surcharge);
            if (!surchargeCheck.matches()) {
                this.setMessage("Please Enter Valid  Surcharge.");
                return;
            }
            if (Float.parseFloat(surcharge) < 0) {
                this.setMessage("Please fill some positive Tds Surcharge.");
                return;
            }

            if (this.tdsRatePan.equalsIgnoreCase("") || this.tdsRatePan.length() == 0) {
                this.setMessage("Please Enter tds Rate Without Pan.");
                return;
            }

            if (this.srctznTdsAmt.equalsIgnoreCase("") || this.srctznTdsAmt.length() == 0) {
                this.setMessage("Please Enter Srctzn Tds Amount.");
                return;
            }

            if (glHead2 == null || this.glHead2.equalsIgnoreCase("") || glHead2.length() < 6) {
                this.setMessage("Please fill TDS-GL Head of 6 digit.");
                return;
            }
            if (dates == null) {
                this.setMessage("Please Enter Applicable Date");
                return;
            }
            String dts = ymd.format(this.dates);
            String s5 = onblurApplicableDate();
            if (!s5.equalsIgnoreCase("true")) {
                this.setMessage(s5);
                return;
            } else {
                this.setMessage("");
            }
            if (checkGlHeads == null) {
                this.setMessage("Please Enter GL Head.");
                return;
            }
            if (checkGlHeads.equals("Enter the Valid GL Head.")) {
                this.setMessage("Enter the Valid GL Head.");
                return;
            }
            fullGlHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glHead2 + "01";
            String saveData = tdRemote.save(fullGlHead, custType, dts, tdsAmount, tdsRate, surcharge, getUserName(), tdsRatePan, srctznTdsAmt);
            this.setMessage(saveData);
            getList();
            setCustType("--SELECT--");
            setTdsRate("");
            setTdsAmount("");
            setSurcharge("");
            setGlHead2("");
            setTdsRatePan("");
            setSrctznTdsAmt("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
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
            String dataDeleted = tdRemote.gridClick(custType, seqNum);
            this.setMessage(dataDeleted);
            getList();
            setCustType("--SELECT--");
            setTdsRate("");
            setTdsAmount("");
            setSurcharge("");
            setGlHead2("");
            setTdsRatePan("");
            setSrctznTdsAmt("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void upDate() {
        try {
            this.setMessage("");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.custType.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select The Customer Type");
                return;
            }
            if (this.tdsAmount.equalsIgnoreCase("") || this.tdsAmount.length() == 0) {
                this.setMessage("Please Enter tds Amount.");
                return;
            }
            Matcher tdsAmountCheck = p.matcher(tdsAmount);
            if (!tdsAmountCheck.matches()) {
                this.setMessage("Please Enter Valid  tds Amount.");
                return;
            }
            if (Float.parseFloat(tdsAmount) < 0) {
                this.setMessage("Please fill some positive amount.");
                return;
            }
            if (this.tdsAmount.contains(".")) {
                if (this.tdsAmount.substring(this.tdsAmount.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Fill The tds Amount Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.tdsRate.equalsIgnoreCase("") || this.tdsRate.length() == 0) {
                this.setMessage("Please Enter tds Rate.");
                return;
            }
            Matcher tdsRateCheck = p.matcher(tdsRate);
            if (!tdsRateCheck.matches()) {
                this.setMessage("Please Enter Valid  tds Rate.");
                return;
            }
            if (Float.parseFloat(tdsRate) < 0) {
                this.setMessage("Please fill some positive tds Rate.");
                return;
            }
            if (this.tdsRate.contains(".")) {
                if (this.tdsRate.substring(this.tdsRate.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Fill The tds Rate Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.surcharge.contains(".")) {
                if (this.surcharge.substring(this.surcharge.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Fill The surcharge Upto Two Decimal Places.");
                    return;
                }
            }
            if (this.surcharge.equalsIgnoreCase("") || this.surcharge.length() == 0) {
                this.setMessage("Please Enter Surcharge.");
                return;
            }
            Matcher surchargeCheck = p.matcher(surcharge);
            if (!surchargeCheck.matches()) {
                this.setMessage("Please Enter Valid  Surcharge.");
                return;
            }
            if (Float.parseFloat(surcharge) < 0) {
                this.setMessage("Please fill some positive Tds Surcharge.");
                return;
            }
            if (glHead2 == null || this.glHead2.equalsIgnoreCase("") || glHead2.length() < 6) {
                this.setMessage("Please Enter TDS GL Head");
                return;
            }
            String dd = todayDate.substring(0, 2);
            String mm = todayDate.substring(3, 5);
            String yy = todayDate.substring(6, 10);
            String currentDt = yy + "" + mm + "" + dd;
            if (dates == null) {
                this.setMessage("Please Enter Applicable Date");
                return;
            }
            String dts = ymd.format(this.dates);
            if (currentDt.equalsIgnoreCase(dts)) {
            } else {
                this.setMessage("Please Enter Correct Applicable Date");
                return;
            }
            if (checkGlHeads == null) {
                this.setMessage("Please Enter GL Head.");
                return;
            }
            if (checkGlHeads.equals("Enter the Valid GL Head.")) {
                this.setMessage("Enter the Valid GL Head.");
                return;
            }
            fullGlHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glHead2 + "01";
            String upDateData = tdRemote.upDate(fullGlHead, custType, dts, tdsAmount, tdsRate, surcharge, seqNum, tdsRatePan, srctznTdsAmt);
            this.setMessage(upDateData);
            getList();
            setCustType("--SELECT--");
            setTdsRate("");
            setTdsAmount("");
            setSurcharge("");
            setGlHead2("");
            setTdsRatePan("");
            setSrctznTdsAmt("");
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

    public void checkGlHead() {
        this.setMessage("");
        try {
            if (glHead2 == null || glHead2.equals("") || glHead2.length() < 6) {
                this.setMessage("Please fill TDS-GL Head of 6 digit.");
                return;
            }
            fullGlHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glHead2 + "01";
            checkGlHeads = tdRemote.checkGlTds(fullGlHead);
            this.setMessage(checkGlHeads);
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
                this.setMessage("Please Enter Applicable Date");
                return "Please Enter Applicable Date";
            }
            long daydiff = CbsUtil.dayDiff(this.dates, new Date());
            if (daydiff < 0) {
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

    public void clearText() {
        try {
            flag = "false";
            flag1 = "true";
            flag2 = "true";
            setCustType("--SELECT--");
            setTdsRate("");
            setTdsAmount("");
            setSurcharge("");
            setMessage("");
            setGlHead2("");
            setTdsRatePan("");
            setSrctznTdsAmt("");
            setDates(new Date());
            slabes = new ArrayList<Slab>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        clearText();
        return "case1";
    }
}
