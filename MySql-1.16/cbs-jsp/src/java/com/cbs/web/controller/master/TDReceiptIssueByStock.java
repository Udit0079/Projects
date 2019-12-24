package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.TDReceipt;
import com.cbs.web.pojo.master.Receipt;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.event.ActionEvent;

public final class TDReceiptIssueByStock extends BaseBean {

    OtherMasterFacadeRemote otherMasterRemote;
    private String message;
    private List<SelectItem> schemeTypeOption;
    private List<TDReceipt> allReceiptEntries;
    private List<Receipt> backEntries;
    private String scheme;
    private String showTableData;
    private String tdlevel8;
    int currentRow;
    //TDReceipt currentItem;
    Receipt currentItem;
    TDReceipt currentItem1;
    private String bookNo;
    private String receiptNoFrom;
    private String receiptNoTo;
    private String noOfLeaves;
    private String series;
    Date issueDt;
    int currentRow1;
    private Float seqNumber;
    private String flag;
    private String flag1;
    private String orgnBrCode = getOrgnBrCode();
    private String userName = getUserName();
   // private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
   // private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
   // private FtsPostingMgmtFacadeRemote fts = null;

    public TDReceipt getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(TDReceipt currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Float getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Float seqNumber) {
        this.seqNumber = seqNumber;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public Date getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(Date issueDt) {
        this.issueDt = issueDt;
    }

    public String getNoOfLeaves() {
        return noOfLeaves;
    }

    public void setNoOfLeaves(String noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    public String getReceiptNoFrom() {
        return receiptNoFrom;
    }

    public void setReceiptNoFrom(String receiptNoFrom) {
        this.receiptNoFrom = receiptNoFrom;
    }

    public String getReceiptNoTo() {
        return receiptNoTo;
    }

    public void setReceiptNoTo(String receiptNoTo) {
        this.receiptNoTo = receiptNoTo;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public Receipt getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Receipt currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getTdlevel8() {
        return tdlevel8;
    }

    public void setTdlevel8(String tdlevel8) {
        this.tdlevel8 = tdlevel8;
    }

    public String getShowTableData() {
        return showTableData;
    }

    public void setShowTableData(String showTableData) {
        this.showTableData = showTableData;
    }

    public List<Receipt> getBackEntries() {
        return backEntries;
    }

    public void setBackEntries(List<Receipt> backEntries) {
        this.backEntries = backEntries;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public List<TDReceipt> getAllReceiptEntries() {
        return allReceiptEntries;
    }

    public void setAllReceiptEntries(List<TDReceipt> allReceiptEntries) {
        this.allReceiptEntries = allReceiptEntries;
    }

    public List<SelectItem> getSchemeTypeOption() {
        return schemeTypeOption;
    }

    public void setSchemeTypeOption(List<SelectItem> schemeTypeOption) {
        this.schemeTypeOption = schemeTypeOption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TDReceiptIssueByStock() {
        try {
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
           // fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            setIssueDt(new Date());
            getdata();
            this.setMessage("");
            flag = "true";
            flag1 = "false";
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        this.setMessage("");
        try {
            String deleteEntry = new String();
            deleteEntry = otherMasterRemote.deleteData(Float.parseFloat(currentItem.getSeqNum()), orgnBrCode);
            this.setMessage(deleteEntry);
            backEntries.remove(currentRow);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String acNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (Receipt item : backEntries) {
                if (item.getBookNo().equalsIgnoreCase(acNo)) {
                    currentItem = item;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValues() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            flag = "false";
            flag1 = "true";
            setBookNo(currentItem.getBookNo());
           // String code = otherMasterRemote.getCode(currentItem.getScheme());
            setScheme(currentItem.getScheme());
            String frm = (String.valueOf(currentItem.getRecFrom()));
            setReceiptNoFrom(frm);
            String to = (String.valueOf(currentItem.getRecTo()));
            setReceiptNoTo(to);
            int leave = (currentItem.getLeaf());
            String leaves = String.valueOf(leave);
            setNoOfLeaves(leaves);
            setSeries(currentItem.getSeries());
            setSeqNumber(Float.parseFloat(currentItem.getSeqNum()));
            setIssueDt(sdf.parse(currentItem.getIssueDt()));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void changeValue() {
        try {
            flag = "false";
            flag1 = "true";
              TDReceipt item = allReceiptEntries.get(currentRow1);

            if (item.getStatus().equalsIgnoreCase("F")) {
                item.setStatus("D");
                allReceiptEntries.remove(currentRow1);
                allReceiptEntries.add(currentRow1, item);
            } 
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getdata() {
        try {
            List acnoDataList = new ArrayList();
            acnoDataList = otherMasterRemote.getData();
            schemeTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    schemeTypeOption.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableDetails() {
        try {
            this.setMessage("");
            allReceiptEntries = new ArrayList<TDReceipt>();
            backEntries = new ArrayList<Receipt>();
            List allList = new ArrayList();
            List backList = new ArrayList();
            String acNature = scheme;
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                setTdlevel8("Term Deposit");
            } else if (acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                setTdlevel8("Cumulative Term Deposit");
            } else if (acNature.equals("--SELECT--")) {
                setTdlevel8("");
                this.setMessage("Please Select The Scheme");
                return;
            } else {
                setTdlevel8("");
            }
            allList = otherMasterRemote.allReceiptTableData(acNature, orgnBrCode);
            backList = otherMasterRemote.backTableData(acNature, orgnBrCode);
            if (!backList.isEmpty()) {
                for (int i = 0; i < backList.size(); i++) {
                    Vector ele = (Vector) backList.get(i);
                   // TDReceipt all = new TDReceipt();
                    Receipt back = new Receipt();
                    back.setScheme(ele.get(0).toString());
                    back.setBookNo(ele.get(1).toString());
                    back.setSeries(ele.get(2).toString());
                    String s = String.valueOf(Float.parseFloat(ele.get(3).toString()));
                    s = s.substring(0, s.indexOf("."));
                    back.setRecFrom(s);
                    String s1 = String.valueOf(Float.parseFloat(ele.get(4).toString()));
                    s1 = s1.substring(0, s1.indexOf("."));
                    back.setRecTo(s1);
                    back.setLeaf(Integer.parseInt(ele.get(5).toString()));
                    String s2 = String.valueOf(Float.parseFloat(ele.get(6).toString()));
                    s2 = s2.substring(0, s2.indexOf("."));
                    back.setSeqNum(s2);
                    back.setIssueDt(ele.get(7).toString());
                    backEntries.add(back);
                }
            }
            if (!allList.isEmpty()) {
                for (int j = 0; j < allList.size(); j++) {
                    Vector ele1 = (Vector) allList.get(j);
                   // Receipt back = new Receipt();
                    TDReceipt all = new TDReceipt();
                    all.setSeqNum1(Float.parseFloat(ele1.get(0).toString()));
                    all.setScheme(ele1.get(1).toString());
                    all.setBookNo(ele1.get(2).toString());
                    all.setSeries(ele1.get(3).toString());
                    all.setReceiptNo(Float.parseFloat(ele1.get(4).toString()));
                    all.setEntryDate(ele1.get(5).toString());
                    all.setStatus(ele1.get(6).toString());
                    allReceiptEntries.add(all);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getNoOfLeave() {
        try {
            this.setMessage("");
            if (receiptNoFrom.contains(".")) {
                this.setMessage("Please Enter Receipt Number From in Integer.");
                return;
            }
            if (receiptNoTo.contains(".")) {
                this.setMessage("Please Enter Receipt Number To in Integer.");
                return;
            }
            if ((receiptNoFrom == null) || (receiptNoFrom.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Receipt Number From.");
                return;
            }
            if ((receiptNoTo == null) || (receiptNoTo.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Receipt Number To.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher receiptNoToCheck = p.matcher(receiptNoTo);
            Matcher receiptNoFromCheck = p.matcher(receiptNoFrom);
            if (!receiptNoFromCheck.matches()) {
                this.setMessage("Please Enter Valid receipt Number From.");
                setNoOfLeaves("");
                return;
            }
            if (!receiptNoToCheck.matches()) {
                this.setMessage("Please Enter Valid receipt Number To .");
                setNoOfLeaves("");
                return;
            }
            int lev;
            int to = Integer.parseInt(receiptNoTo);
            int from = Integer.parseInt(receiptNoFrom);
            if (to < from) {
                this.setMessage("Please Enter Valid Series No.");
                return;
            } else {
                lev = to - from + 1;
            }
            if (lev > 100) {
                this.setMessage("No. of Leaves can not be greater then 100");
                return;
            }
            String leavess = String.valueOf(lev);
            String abc = String.valueOf(leavess);
            setNoOfLeaves(abc);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurIssueDate() {
        String str = "";
        try {
            this.setMessage("");
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
            if (issueDt == null) {
                this.setMessage("Please Enter Issue Date.");
                return "Please Enter Issue Date.";
            }
            List dateDif = otherMasterRemote.dateDiffStatementFreqDate(sd.format(this.issueDt));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessage("You can't select the date after the current date.");
                return str = "You can't select the date after the current date.";
            } else {
                this.setMessage("");
                return str = "true";
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return str;
    }

    public void saveData() {
        try {
            flag = "true";
            flag1 = "false";
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            this.setMessage("");
            String tmpFlag = "S";
            Float s = null;
            if (this.scheme.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select scheme.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (bookNo.contains("-")) {
                this.setMessage("Please Enter Valid book Number.");
                return;
            }
            if (this.bookNo.equalsIgnoreCase("") || this.bookNo.length() == 0) {
                this.setMessage("Please Enter book Number.");
                return;
            }
            if (this.receiptNoFrom.equalsIgnoreCase("") || this.receiptNoFrom.length() == 0) {
                this.setMessage("Please Enter receipt No From.");
                return;
            }
            Matcher receiptNoFromCheck = p.matcher(receiptNoFrom);
            if (!receiptNoFromCheck.matches()) {
                this.setMessage("Please Enter Valid receipt Number From.");
                return;
            }
            if (Integer.parseInt(receiptNoFrom) < 0) {
                this.setMessage("Please Enter Valid receipt Number From.");
                return;
            }
            if (this.receiptNoTo.equalsIgnoreCase("") || this.receiptNoTo.length() == 0) {
                this.setMessage("Please Enter receipt No To.");
                return;
            }
            Matcher receiptNoToCheck = p.matcher(receiptNoTo);
            if (!receiptNoToCheck.matches()) {
                this.setMessage("Please Enter Valid receipt Number To .");
                return;
            }
            if (Integer.parseInt(receiptNoTo) < 0) {
                this.setMessage("Please Enter Valid receipt Number To .");
                return;
            }
            if (Integer.parseInt(receiptNoFrom) > Integer.parseInt(receiptNoTo)) {
                this.setMessage("Receipt No To must not be less than the Receipt No From");
                return;
            }
            if (this.noOfLeaves.equalsIgnoreCase("") || this.noOfLeaves.length() == 0 || this.noOfLeaves == null) {
                this.setMessage("Receipt No To must not be less than the Receipt No From");
                return;
            }
            if (series.contains("-")) {
                this.setMessage("Please Enter Valid Series.");
                return;
            }
            if (this.series.equalsIgnoreCase("") || this.series.length() == 0) {
                this.setMessage("Please Enter Series.");
                return;
            }
            if (issueDt == null) {
                this.setMessage("Please Enter Issue Date.");
                return;
            }
            List dateDif = otherMasterRemote.dateDiffStatementFreqDate(ymd.format(this.issueDt));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessage("You can't select the date after the current date.");
                return;
            } else {
                this.setMessage("");
            }
            List resultList = new ArrayList();
            String date = ymd.format(this.issueDt);
            String saveResult = otherMasterRemote.saveData(resultList, scheme, bookNo, receiptNoFrom, receiptNoTo, showTableData, tmpFlag, noOfLeaves, series, date, userName, s, orgnBrCode);
            getTableDetails();
            this.setMessage(saveResult);
            setScheme("--SELECT--");
            setBookNo("");
            setReceiptNoFrom("");
            setReceiptNoTo("");
            setNoOfLeaves("");
            setSeries("");
            setTdlevel8("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void upDateData() {
        try {
            flag = "true";
            flag1 = "false";
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            this.setMessage("");
            String tmpFlag = "U";
            onblurIssueDate();
            List resultList = new ArrayList();
            String a[][] = new String[allReceiptEntries.size()][7];
            for (int i = 0; i < allReceiptEntries.size(); i++) {
                a[i][0] = allReceiptEntries.get(i).getSeqNum1().toString();
                a[i][1] = allReceiptEntries.get(i).getScheme().toString();
                a[i][2] = allReceiptEntries.get(i).getBookNo().toString();
                a[i][3] = allReceiptEntries.get(i).getSeries().toString();
                a[i][4] = allReceiptEntries.get(i).getReceiptNo().toString();
                a[i][5] = allReceiptEntries.get(i).getEntryDate().toString();
                a[i][6] = allReceiptEntries.get(i).getStatus().toString();
            }
            for (int i = 0; i < allReceiptEntries.size(); i++) {
                for (int j = 0; j < 7; j++) {
                    Object combinedArray = a[i][j];
                    resultList.add(combinedArray);
                }
            }

            if (issueDt == null) {
                this.setMessage("Please Enter Issue Date.");
                return;
            }
            String date = ymd.format(this.issueDt);
            String saveResult = otherMasterRemote.saveData(resultList, scheme, bookNo, receiptNoFrom, receiptNoTo, showTableData, tmpFlag, noOfLeaves, series, date, userName, seqNumber, orgnBrCode);
            getTableDetails();
            this.setMessage(saveResult);
            setBookNo("");
            setReceiptNoFrom("");
            setReceiptNoTo("");
            setNoOfLeaves("");
            setSeries("");
            setTdlevel8("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clearText() {
        try {
            flag = "true";
            flag1 = "false";
            setScheme("--SELECT--");
            this.setMessage("");
            setBookNo("");
            setReceiptNoFrom("");
            setReceiptNoTo("");
            setNoOfLeaves("");
            setSeries("");
            setTdlevel8("");
            setIssueDt(new Date());
            allReceiptEntries = new ArrayList<TDReceipt>();
            backEntries = new ArrayList<Receipt>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        clearText();
        return "case1";
    }
}
