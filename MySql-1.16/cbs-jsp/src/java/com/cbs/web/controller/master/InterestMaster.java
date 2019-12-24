package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.master.IntMasterTable;
import com.cbs.facade.master.InterestMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;

public final class InterestMaster extends BaseBean {

    InterestMasterFacadeRemote interestMasterRemote;
    private String userName;
    private String todayDate;
    private String message;
    private String code;
    private String codeDescription;
    private String interestPercentageCredit;
    private String interestPercentageDebit;
    private String indicatorFlag;
    private String recordStatus;
    private int versionNo;
    private String versionNoDesc;
    private int recordModificationCount;
    private String createdBy;
    private String creationDate;
    private String changedBy;
    private String changedDate;
    private String status;
    private String startDate;
    private String endDate;
    private String currentDate;
    private String sysDate;
    private String rdCreateDate;
    private boolean flag;
    private boolean codeDisable;
    private boolean indFlagDisable;
    private String lastUpdateDate;
    private boolean creditDisable;
    private boolean debitDisable;
    private List<SelectItem> recrdStatus;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private List<IntMasterTable> intMasterData;
    private int currentRow;
    private IntMasterTable currentItem = new IntMasterTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getInterestPercentageCredit() {
        return interestPercentageCredit;
    }

    public void setInterestPercentageCredit(String interestPercentageCredit) {
        this.interestPercentageCredit = interestPercentageCredit;
    }

    public String getInterestPercentageDebit() {
        return interestPercentageDebit;
    }

    public void setInterestPercentageDebit(String interestPercentageDebit) {
        this.interestPercentageDebit = interestPercentageDebit;
    }

    public boolean isCreditDisable() {
        return creditDisable;
    }

    public void setCreditDisable(boolean creditDisable) {
        this.creditDisable = creditDisable;
    }

    public boolean isDebitDisable() {
        return debitDisable;
    }

    public void setDebitDisable(boolean debitDisable) {
        this.debitDisable = debitDisable;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isCodeDisable() {
        return codeDisable;
    }

    public void setCodeDisable(boolean codeDisable) {
        this.codeDisable = codeDisable;
    }

    public boolean isIndFlagDisable() {
        return indFlagDisable;
    }

    public void setIndFlagDisable(boolean indFlagDisable) {
        this.indFlagDisable = indFlagDisable;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRdCreateDate() {
        return rdCreateDate;
    }

    public void setRdCreateDate(String rdCreateDate) {
        this.rdCreateDate = rdCreateDate;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(String changedDate) {
        this.changedDate = changedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getRecordModificationCount() {
        return recordModificationCount;
    }

    public void setRecordModificationCount(int recordModificationCount) {
        this.recordModificationCount = recordModificationCount;
    }

    public IntMasterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(IntMasterTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<IntMasterTable> getIntMasterData() {
        return intMasterData;
    }

    public void setIntMasterData(List<IntMasterTable> intMasterData) {
        this.intMasterData = intMasterData;
    }

    public List<SelectItem> getRecrdStatus() {
        return recrdStatus;
    }

    public void setRecrdStatus(List<SelectItem> recrdStatus) {
        this.recrdStatus = recrdStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public String getIndicatorFlag() {
        return indicatorFlag;
    }

    public void setIndicatorFlag(String indicatorFlag) {
        this.indicatorFlag = indicatorFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getVersionNoDesc() {
        return versionNoDesc;
    }

    public void setVersionNoDesc(String versionNoDesc) {
        this.versionNoDesc = versionNoDesc;
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

    public InterestMaster() {
        try {
            interestMasterRemote = (InterestMasterFacadeRemote) ServiceLocator.getInstance().lookup("InterestMasterFacade");
            clear();
            userName = getUserName();
            todayDate = getTodayDate();
            Date date = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            setSysDate(sdf1.format(date));
            setLastUpdateDate(sdf1.format(date));
            String toDt = "31" + "/" + "12" + "/" + 2099;
            setToDate(sdf.parse(toDt));
            recrdStatus = new ArrayList<SelectItem>();
            recrdStatus.add(new SelectItem("0", "--SELECT--"));
            recrdStatus.add(new SelectItem("A", "Active"));
            recrdStatus.add(new SelectItem("D", "Deleted"));
            setInterestPercentageCredit("0.0");
            setInterestPercentageDebit("0.0");
            setMessage("");
            getTableDetails();
            setFlag(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /************************** Save Button functionality************************/
    public void saveBtnAction() {
        this.setMessage("");
        flag = true;
        if (fromDate == null) {
            this.setMessage("Please fill the Start Date");
            return;
        }
        if (toDate == null) {
            this.setMessage("Please fill the End Date");
            return;
        }
        startDate = ymd.format(this.fromDate);
        endDate = ymd.format(this.toDate);
        try {
            if (code.equals("")) {
                this.setMessage("Please enter the code.");
                return;
            }
            if (codeDescription.equals("")) {
                this.setMessage("Please enter the codeDescription.");
                return;
            }
            if (indicatorFlag.equals("")) {
                this.setMessage("Please enter the indicator Flag.");
                return;
            }
            if (versionNoDesc.equals("")) {
                this.setMessage("Please enter the versionNoDesc.");
                return;
            }
            if (status.equals("0")) {
                this.setMessage("Please select the record status.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher intdebitCheck = p.matcher(interestPercentageDebit);
            Matcher intcreditCheck = p.matcher(interestPercentageCredit);
            if (!intdebitCheck.matches()) {
                this.setMessage("Please fill numeric value in Interest Percentage debit (%)");
                return;
            }
            if (Float.parseFloat(interestPercentageDebit) < 0) {
                this.setMessage("Interest Percentage debit (%) Can't Be Negative");
                return;
            }
            if (!intcreditCheck.matches()) {
                this.setMessage("Please fill numeric value in Interest Percentage Credit (%)");
                return;
            }
            if (Float.parseFloat(interestPercentageCredit) < 0) {
                this.setMessage("Interest Percentage credit (%) can not be negative");
                return;
            }
            String result = interestMasterRemote.saveUpdateInterestMaster("save", code, codeDescription, indicatorFlag, status, startDate, endDate, versionNoDesc,
                    userName, sysDate, userName, Float.parseFloat(interestPercentageDebit), Float.parseFloat(interestPercentageCredit), todayDate,
                    lastUpdateDate, versionNo);
            getTableDetails();
            setMessage(result);
            clear();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /************************** END Save Button functionality************************/
    /************************** Update Button functionality************************/
    public void updateBtnAction() {
        this.setMessage("");
        try {
            if (code.equals("")) {
                this.setMessage("Please enter the Code.");
                return;
            }
            if (codeDescription.equals("")) {
                this.setMessage("Please enter the Code Description.");
                return;
            }
            if (indicatorFlag.equals("")) {
                this.setMessage("Please enter the Indicator Flag.");
                return;
            }
            if (versionNoDesc.equals("")) {
                this.setMessage("Please enter the Version No Desc.");
                return;
            }
            if (status.equals("0")) {
                this.setMessage("Please select the Record Status.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher intdebitCheck = p.matcher(interestPercentageDebit);
            Matcher intcreditCheck = p.matcher(interestPercentageCredit);
            if (!intdebitCheck.matches()) {
                this.setMessage("Please fill numeric value in Interest Percentage debit (%)");
                return;
            }
            if (Float.parseFloat(interestPercentageDebit) < 0) {
                this.setMessage("Interest Percentage debit (%) can not be negative");
                return;
            }
            if (!intcreditCheck.matches()) {
                this.setMessage("Please fill numeric value in Interest Percentage credit (%)");
                return;
            }
            if (Float.parseFloat(interestPercentageCredit) < 0) {
                this.setMessage("Interest Percentage credit (%) can not be negative");
                return;
            }
            if (fromDate == null) {
                this.setMessage("Please fill the Start Date");
                return;
            }
            if (toDate == null) {
                this.setMessage("Please fill the End Date");
                return;
            }
            startDate = ymd.format(this.fromDate);
            endDate = ymd.format(this.toDate);
            String result = interestMasterRemote.saveUpdateInterestMaster("update", code, codeDescription, indicatorFlag, currentItem.getRdStatus(), startDate,
                    endDate, versionNoDesc, currentItem.getCreateBy(), currentItem.getCreatedate(), userName, Float.parseFloat(interestPercentageDebit),
                    Float.parseFloat(interestPercentageCredit), todayDate, lastUpdateDate, currentItem.getVersionNo());
            this.setMessage(result);
            getTableDetails();
            clear();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /************************** END Update Button functionality************************/
    /************************** Table Data functionality************************/
    public void getTableDetails() throws NamingException {
        intMasterData = new ArrayList<IntMasterTable>();
        try {
            List resultLt = new ArrayList();
            resultLt = interestMasterRemote.tableDataInterestMaster();
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    IntMasterTable rd = new IntMasterTable();
                    rd.setCodeValue(ele.get(0).toString());
                    rd.setCodeDes((ele.get(1).toString()));
                    rd.setIndFlag(ele.get(2).toString());
                    rd.setRdStatus(ele.get(3).toString());
                    rd.setStrtDate(ele.get(4).toString());
                    rd.setEndDate(ele.get(5).toString());
                    rd.setVersionNumDescription(ele.get(6).toString());
                    rd.setIntPercent(ele.get(7).toString());
                    rd.setIntPercentCredit(ele.get(8).toString());
                    rd.setModificationCount(Integer.parseInt(ele.get(9).toString()));
                    rd.setVersionNo(Integer.parseInt(ele.get(10).toString()));
                    rd.setCreateBy(ele.get(11).toString());
                    rd.setCreatedate(ele.get(12).toString());
                    if (ele.get(13) == null) {
                        rd.setModifiedby("");
                    } else {
                        rd.setModifiedby(ele.get(13).toString());
                    }
                    if (ele.get(14) == null) {
                        rd.setModifiedDate("");
                    } else {
                        rd.setModifiedDate(ele.get(14).toString());
                    }
                    rd.setCount(Integer.parseInt(ele.get(15).toString()));
                    intMasterData.add(rd);
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

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Code"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (IntMasterTable item : intMasterData) {
                if (item.getCodeValue().equals(accNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /************************** END Table Data functionality************************/
    /************************** Select Button functionality************************/
    public void select() {
        try {
            this.setMessage("");
            setCodeDisable(true);
            setIndFlagDisable(true);
            flag = false;
            if (currentItem.getIntPercent().equalsIgnoreCase("0.0")) {
                setDebitDisable(true);
            }
            if (currentItem.getIntPercentCredit().equalsIgnoreCase("0.0")) {
                setCreditDisable(true);
            }
            setCode(currentItem.getCodeValue());
            setCodeDescription(currentItem.getCodeDes());
            try {
                setFromDate(sdf.parse(currentItem.getStrtDate()));
                setToDate(sdf.parse(currentItem.getEndDate()));
            } catch (ParseException ex) {
                Logger.getLogger(InterestMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
            setInterestPercentageDebit(currentItem.getIntPercent());
            setInterestPercentageCredit(currentItem.getIntPercentCredit());
            setRecordModificationCount(currentItem.getModificationCount());
            setIndicatorFlag(currentItem.getIndFlag());
            setVersionNoDesc(currentItem.getVersionNumDescription());
            setVersionNo(currentItem.getVersionNo());
            setStatus(currentItem.getRdStatus());
            setCreatedBy(currentItem.getCreateBy());
            String year = currentItem.getCreatedate().substring(0, 4);
            String month = currentItem.getCreatedate().substring(5, 7);
            String day = currentItem.getCreatedate().substring(8, 10);
            String time = currentItem.getCreatedate().substring(10);
            String modDate = day + "-" + month + "-" + year + time;
            setCreationDate(modDate);
            if (currentItem.getModifiedby().equalsIgnoreCase("")) {
                setChangedBy("");
            } else {
                setChangedBy(currentItem.getModifiedby());
            }
            if (currentItem.getModifiedDate().equalsIgnoreCase("")) {
                setChangedDate("");
            } else {
                String yearModified = currentItem.getModifiedDate().substring(0, 4);
                String monthModified = currentItem.getModifiedDate().substring(5, 7);
                String dayModified = currentItem.getModifiedDate().substring(8, 10);
                String timeModified = currentItem.getModifiedDate().substring(10);
                String modifiedDate = dayModified + "-" + monthModified + "-" + yearModified + timeModified;
                setChangedDate(modifiedDate);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /************************** END Select Button functionality**********************/
    /************************** Delete Button functionality************************/
    public void delete() {
        this.setMessage("");
        try {
            //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            startDate = ymd.format(sdf.parse(currentItem.getStrtDate()));
            endDate = ymd.format(sdf.parse(currentItem.getEndDate()));
            String deleteEntry = new String();
            deleteEntry = interestMasterRemote.deletionInterestMaster(currentItem.getCodeValue(), currentItem.getCodeDes(), currentItem.getIndFlag(), currentItem.getRdStatus(),
                    startDate, endDate, currentItem.getVersionNumDescription(), currentItem.getCreateBy(), currentItem.getCreatedate(), userName,
                    Float.parseFloat(currentItem.getIntPercent()), Float.parseFloat(currentItem.getIntPercentCredit()), todayDate, lastUpdateDate,
                    currentItem.getVersionNo());
            this.setMessage(deleteEntry);
            getTableDetails();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setDebitDisable() {
        try {
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher intcreditCheck = p.matcher(interestPercentageCredit);
            if (!intcreditCheck.matches()) {
                this.setMessage("Please fill numeric value in Interest Percentage Credit (%)");
                return;
            }
            if (Float.parseFloat(interestPercentageCredit) < 0) {
                this.setMessage("Interest Percentage credit (%) can not be negative");
                return;
            }
            if (interestPercentageCredit != null) {
                if (Float.parseFloat(interestPercentageCredit) > 0) {
                    setInterestPercentageDebit("0.0");
                    setDebitDisable(true);
                }
                if (Float.parseFloat(interestPercentageCredit) < 0 || Float.parseFloat(interestPercentageCredit) == 0) {
                    setDebitDisable(false);
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setCreditDisable() {
        try {
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher intdebitCheck = p.matcher(interestPercentageDebit);
            if (!intdebitCheck.matches()) {
                this.setMessage("Please fill numeric value in Interest Percentage debit (%)");
                return;
            }
            if (Float.parseFloat(interestPercentageDebit) < 0) {
                this.setMessage("Interest Percentage debit (%) can not be negative");
                return;
            }
            if (interestPercentageDebit != null) {
                if (Float.parseFloat(interestPercentageDebit) > 0) {
                    setInterestPercentageCredit("0.0");
                    setCreditDisable(true);
                }
                if (Float.parseFloat(interestPercentageDebit) < 0 || Float.parseFloat(interestPercentageDebit) == 0) {
                    setCreditDisable(false);
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clear() {
        try {
            flag = true;
            setCode("");
            setCodeDescription("");
            setVersionNoDesc("");
            setIndicatorFlag("");
            setInterestPercentageCredit("0.0");
            setInterestPercentageDebit("0.0");
            setCreditDisable(false);
            setDebitDisable(false);
            setCodeDisable(false);
            setIndFlagDisable(false);
            setVersionNo(0);
            setRecordModificationCount(0);
            setCreatedBy("");
            setCreationDate("");
            setChangedBy("");
            setChangedDate("");
            setStatus("0");
            fromDate = new Date();
            setFromDate(fromDate);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetButton() {
        this.setFlag(true);
        this.setMessage("");
    }

    public String exitForm() {
        clear();
        this.setMessage("");
        return "case1";
    }
}
