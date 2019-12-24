package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.InterestMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.loan.InterestCodeHelp;
import com.cbs.dto.InterestCodeMasterTable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class InterestCodeMaster extends BaseBean {

    String orgnBrCode;
    private String msg;
    //private String todayDate;
    private String userName;
    private String loanPeriodMonths;
    private String loanPeriodDays;
    private String bgSlabAmt;
    private String endSlabAmt;
    private String recordStatus;
    private String intSlabDcFlag;
    private String normalIntInd;
    private String peenalIntPer;
    private String peenalIntInd;
    private String cleanIntPer;// = "0";//intialize by manish kumar(18-05-17)
    private String cleanPortionInd;// = "N";//intialize by manish kumar
    private String qisIntPer;// = "0";//intialize by manish kumar
    private String qisPortionInd;//="N";////intialize by manish kumar
    private String additionalInt;// = "0";//intialize by manish kumar
    private String additionalInd;// = "N";//intialize by manish kumar
    private String normalIntPer;
    private boolean saveDisable;
    private String basePerCredit;
    private String basePerDebit;
    private String intTableCode;
    private String intTableDesc;
    private String currencyCode;
    private String recordStatusMs;
    private Date startDate;
    private Date endDate;
    private String intRateDes;
    private String intVerRemark;
    private String baseindFlag;
    private String baseIntTableCode;
    private int currentRow;
    private int modificationCounter;
    private int modificationMasterCounter;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private String createdBy;
    private String createdDate;
    private int intVerNo;
    private String lastUpdatedBySlab;
    private String lastUpdatedDateSlab;
    private String createdBySlab;
    private String createdDateSlab;
    private int intVerNoSlab;
    private String intSlabDes;
    private String intSlabVerRemark;
    private String tableIntCode;
    private String flag = "";
    int select;
    int count1 = 0;
    int count2 = 0;
    int i = 0;
    private List<InterestCodeHelp> tabSearch;
    private List<InterestCodeMasterTable> intCodeMaster;
    private List<InterestCodeMasterTable> intCodeMasterTmp = new ArrayList<InterestCodeMasterTable>();
    private List<SelectItem> recordStatusList;
    private List<SelectItem> baseindFlagList;
    private List<SelectItem> intFlagCrDeFlgList;
    private List<SelectItem> normalIntIndList;
    private List<SelectItem> peenalIntIndList;
    private List<SelectItem> qisPortionList;
    private List<SelectItem> addPortionList;
    private List<SelectItem> cleanPortionIndList;
    private List<SelectItem> currencyCodeList;
    private boolean intTableCodeDisable;
    private boolean baseIndFlabDisable;
    private boolean loanMonthDisable;
    private boolean loanDaysDisable;
    private boolean bgSlabAmtDisable;
    private boolean endSlabAmtDisable;
    private boolean creditDisable;
    private boolean debitDisable;
    private boolean updateDisable;
    private boolean newSlabDisable;
    private boolean currencyCodeDisable;
    //Added by manish kumar
    private boolean normalIntIndicatorDisable;
    private boolean peenalIntIndicatorDisable;
    private boolean cleanPortionIndicatorDisable;
    private boolean qisPortionIndicatorDisable;
    private boolean additionalInterestIndicatorDisable;
    private boolean intSlabDcFlagDisable;
    private boolean interestSlabDescDisable;
    private boolean interestSlabVesionRemarkDisable;
    private boolean recordStatusDisable;
    private boolean deleteDisable;
    //---            
    private List<SelectItem> baseIntTableCodeList;
    List baseIntTableCodeResultList = new ArrayList();
    InterestMasterFacadeRemote interestMasterRemote;
    Date date = new Date();
    private InterestCodeMasterTable currentItem = new InterestCodeMasterTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    double prevEndSlabAmt = 0;

    public boolean isCurrencyCodeDisable() {
        return currencyCodeDisable;
    }

    public void setCurrencyCodeDisable(boolean currencyCodeDisable) {
        this.currencyCodeDisable = currencyCodeDisable;
    }

    public List<InterestCodeHelp> getTabSearch() {
        return tabSearch;
    }

    public void setTabSearch(List<InterestCodeHelp> tabSearch) {
        this.tabSearch = tabSearch;
    }

    public boolean isNewSlabDisable() {
        return newSlabDisable;
    }

    public void setNewSlabDisable(boolean newSlabDisable) {
        this.newSlabDisable = newSlabDisable;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateSlab() {
        return createdDateSlab;
    }

    public void setCreatedDateSlab(String createdDateSlab) {
        this.createdDateSlab = createdDateSlab;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedDateSlab() {
        return lastUpdatedDateSlab;
    }

    public void setLastUpdatedDateSlab(String lastUpdatedDateSlab) {
        this.lastUpdatedDateSlab = lastUpdatedDateSlab;
    }

    public boolean isBgSlabAmtDisable() {
        return bgSlabAmtDisable;
    }

    public void setBgSlabAmtDisable(boolean bgSlabAmtDisable) {
        this.bgSlabAmtDisable = bgSlabAmtDisable;
    }

    public boolean isEndSlabAmtDisable() {
        return endSlabAmtDisable;
    }

    public void setEndSlabAmtDisable(boolean endSlabAmtDisable) {
        this.endSlabAmtDisable = endSlabAmtDisable;
    }

    public boolean isLoanDaysDisable() {
        return loanDaysDisable;
    }

    public void setLoanDaysDisable(boolean loanDaysDisable) {
        this.loanDaysDisable = loanDaysDisable;
    }

    public boolean isLoanMonthDisable() {
        return loanMonthDisable;
    }

    public void setLoanMonthDisable(boolean loanMonthDisable) {
        this.loanMonthDisable = loanMonthDisable;
    }

    public boolean isBaseIndFlabDisable() {
        return baseIndFlabDisable;
    }

    public void setBaseIndFlabDisable(boolean baseIndFlabDisable) {
        this.baseIndFlabDisable = baseIndFlabDisable;
    }

    public boolean isIntTableCodeDisable() {
        return intTableCodeDisable;
    }

    public void setIntTableCodeDisable(boolean intTableCodeDisable) {
        this.intTableCodeDisable = intTableCodeDisable;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIntSlabDes() {
        return intSlabDes;
    }

    public void setIntSlabDes(String intSlabDes) {
        this.intSlabDes = intSlabDes;
    }

    public String getIntSlabVerRemark() {
        return intSlabVerRemark;
    }

    public void setIntSlabVerRemark(String intSlabVerRemark) {
        this.intSlabVerRemark = intSlabVerRemark;
    }

    public String getCreatedBySlab() {
        return createdBySlab;
    }

    public void setCreatedBySlab(String createdBySlab) {
        this.createdBySlab = createdBySlab;
    }

    public Integer getIntVerNoSlab() {
        return intVerNoSlab;
    }

    public void setIntVerNoSlab(Integer intVerNoSlab) {
        this.intVerNoSlab = intVerNoSlab;
    }

    public String getLastUpdatedBySlab() {
        return lastUpdatedBySlab;
    }

    public void setLastUpdatedBySlab(String lastUpdatedBySlab) {
        this.lastUpdatedBySlab = lastUpdatedBySlab;
    }

    public int getModificationMasterCounter() {
        return modificationMasterCounter;
    }

    public void setModificationMasterCounter(int modificationMasterCounter) {
        this.modificationMasterCounter = modificationMasterCounter;
    }

    public Integer getIntVerNo() {
        return intVerNo;
    }

    public void setIntVerNo(Integer intVerNo) {
        this.intVerNo = intVerNo;
    }

    public List<SelectItem> getBaseIntTableCodeList() {
        return baseIntTableCodeList;
    }

    public void setBaseIntTableCodeList(List<SelectItem> baseIntTableCodeList) {
        this.baseIntTableCodeList = baseIntTableCodeList;
    }

    public List<SelectItem> getCurrencyCodeList() {
        return currencyCodeList;
    }

    public void setCurrencyCodeList(List<SelectItem> currencyCodeList) {
        this.currencyCodeList = currencyCodeList;
    }

    public List<SelectItem> getAddPortionList() {
        return addPortionList;
    }

    public void setAddPortionList(List<SelectItem> addPortionList) {
        this.addPortionList = addPortionList;
    }

    public List<SelectItem> getCleanPortionIndList() {
        return cleanPortionIndList;
    }

    public void setCleanPortionIndList(List<SelectItem> cleanPortionIndList) {
        this.cleanPortionIndList = cleanPortionIndList;
    }

    public List<SelectItem> getNormalIntIndList() {
        return normalIntIndList;
    }

    public void setNormalIntIndList(List<SelectItem> normalIntIndList) {
        this.normalIntIndList = normalIntIndList;
    }

    public List<SelectItem> getPeenalIntIndList() {
        return peenalIntIndList;
    }

    public void setPeenalIntIndList(List<SelectItem> peenalIntIndList) {
        this.peenalIntIndList = peenalIntIndList;
    }

    public List<SelectItem> getQisPortionList() {
        return qisPortionList;
    }

    public void setQisPortionList(List<SelectItem> qisPortionList) {
        this.qisPortionList = qisPortionList;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String LastUpdatedBy) {
        this.lastUpdatedBy = LastUpdatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public List<SelectItem> getIntFlagCrDeFlgList() {
        return intFlagCrDeFlgList;
    }

    public void setIntFlagCrDeFlgList(List<SelectItem> intFlagCrDeFlgList) {
        this.intFlagCrDeFlgList = intFlagCrDeFlgList;
    }

    public List<SelectItem> getBaseindFlagList() {
        return baseindFlagList;
    }

    public void setBaseindFlagList(List<SelectItem> baseindFlagList) {
        this.baseindFlagList = baseindFlagList;
    }

    public List<SelectItem> getRecordStatusList() {
        return recordStatusList;
    }

    public void setRecordStatusList(List<SelectItem> recordStatusList) {
        this.recordStatusList = recordStatusList;
    }

    public int getModificationCounter() {
        return modificationCounter;
    }

    public void setModificationCounter(int modificationCounter) {
        this.modificationCounter = modificationCounter;
    }

    public InterestCodeMasterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InterestCodeMasterTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getIntTableCode() {
        return intTableCode;
    }

    public void setIntTableCode(String intTableCode) {
        this.intTableCode = intTableCode;
    }

    public String getIntTableDesc() {
        return intTableDesc;
    }

    public void setIntTableDesc(String intTableDesc) {
        this.intTableDesc = intTableDesc;
    }

    public String getBaseIntTableCode() {
        return baseIntTableCode;
    }

    public void setBaseIntTableCode(String baseIntTableCode) {
        this.baseIntTableCode = baseIntTableCode;
    }

    public String getBaseindFlag() {
        return baseindFlag;
    }

    public void setBaseindFlag(String baseindFlag) {
        this.baseindFlag = baseindFlag;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIntRateDes() {
        return intRateDes;
    }

    public void setIntRateDes(String intRateDes) {
        this.intRateDes = intRateDes;
    }

    public String getIntVerRemark() {
        return intVerRemark;
    }

    public void setIntVerRemark(String intVerRemark) {
        this.intVerRemark = intVerRemark;
    }

    public String getRecordStatusMs() {
        return recordStatusMs;
    }

    public void setRecordStatusMs(String recordStatusMs) {
        this.recordStatusMs = recordStatusMs;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getBasePerCredit() {
        return basePerCredit;
    }

    public void setBasePerCredit(String basePerCredit) {
        this.basePerCredit = basePerCredit;
    }

    public boolean isCreditDisable() {
        return creditDisable;
    }

    public void setCreditDisable(boolean creditDisable) {
        this.creditDisable = creditDisable;
    }

    public String getBasePerDebit() {
        return basePerDebit;
    }

    public void setBasePerDebit(String basePerDebit) {
        this.basePerDebit = basePerDebit;
    }

    public boolean isDebitDisable() {
        return debitDisable;
    }

    public void setDebitDisable(boolean debitDisable) {
        this.debitDisable = debitDisable;
    }

    public List<InterestCodeMasterTable> getIntCodeMaster() {
        return intCodeMaster;
    }

    public void setIntCodeMaster(List<InterestCodeMasterTable> intCodeMaster) {
        this.intCodeMaster = intCodeMaster;
    }

    public String getBgSlabAmt() {
        return bgSlabAmt;
    }

    public void setBgSlabAmt(String bgSlabAmt) {
        this.bgSlabAmt = bgSlabAmt;
    }

    public String getEndSlabAmt() {
        return endSlabAmt;
    }

    public void setEndSlabAmt(String endSlabAmt) {
        this.endSlabAmt = endSlabAmt;
    }

    public String getIntSlabDcFlag() {
        return intSlabDcFlag;
    }

    public void setIntSlabDcFlag(String intSlabDcFlag) {
        this.intSlabDcFlag = intSlabDcFlag;
    }

    public String getLoanPeriodDays() {
        return loanPeriodDays;
    }

    public void setLoanPeriodDays(String loanPeriodDays) {
        this.loanPeriodDays = loanPeriodDays;
    }

    public String getLoanPeriodMonths() {
        return loanPeriodMonths;
    }

    public void setLoanPeriodMonths(String loanPeriodMonths) {
        this.loanPeriodMonths = loanPeriodMonths;
    }

    public String getNormalIntInd() {
        return normalIntInd;
    }

    public void setNormalIntInd(String normalIntInd) {
        this.normalIntInd = normalIntInd;
    }

    public String getAdditionalInd() {
        return additionalInd;
    }

    public void setAdditionalInd(String additionalInd) {
        this.additionalInd = additionalInd;
    }

    public String getCleanPortionInd() {
        return cleanPortionInd;
    }

    public void setCleanPortionInd(String cleanPortionInd) {
        this.cleanPortionInd = cleanPortionInd;
    }

    public String getPeenalIntInd() {
        return peenalIntInd;
    }

    public void setPeenalIntInd(String peenalIntInd) {
        this.peenalIntInd = peenalIntInd;
    }

    public String getCleanIntPer() {
        return cleanIntPer;
    }

    public void setCleanIntPer(String cleanIntPer) {
        this.cleanIntPer = cleanIntPer;
    }

    public String getPeenalIntPer() {
        return peenalIntPer;
    }

    public void setPeenalIntPer(String peenalIntPer) {
        this.peenalIntPer = peenalIntPer;
    }

    public String getAdditionalInt() {
        return additionalInt;
    }

    public void setAdditionalInt(String additionalInt) {
        this.additionalInt = additionalInt;
    }

    public String getNormalIntPer() {
        return normalIntPer;
    }

    public void setNormalIntPer(String normalIntPer) {
        this.normalIntPer = normalIntPer;
    }

    public String getQisIntPer() {
        return qisIntPer;
    }

    public void setQisIntPer(String qisIntPer) {
        this.qisIntPer = qisIntPer;
    }

    public String getQisPortionInd() {
        return qisPortionInd;
    }

    public void setQisPortionInd(String qisPortionInd) {
        this.qisPortionInd = qisPortionInd;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public boolean isNormalIntIndicatorDisable() {
        return normalIntIndicatorDisable;
    }

    public void setNormalIntIndicatorDisable(boolean normalIntIndicatorDisable) {
        this.normalIntIndicatorDisable = normalIntIndicatorDisable;
    }

    public boolean isPeenalIntIndicatorDisable() {
        return peenalIntIndicatorDisable;
    }

    public void setPeenalIntIndicatorDisable(boolean peenalIntIndicatorDisable) {
        this.peenalIntIndicatorDisable = peenalIntIndicatorDisable;
    }

    public boolean isCleanPortionIndicatorDisable() {
        return cleanPortionIndicatorDisable;
    }

    public void setCleanPortionIndicatorDisable(boolean cleanPortionIndicatorDisable) {
        this.cleanPortionIndicatorDisable = cleanPortionIndicatorDisable;
    }

    public boolean isQisPortionIndicatorDisable() {
        return qisPortionIndicatorDisable;
    }

    public void setQisPortionIndicatorDisable(boolean qisPortionIndicatorDisable) {
        this.qisPortionIndicatorDisable = qisPortionIndicatorDisable;
    }

    public boolean isAdditionalInterestIndicatorDisable() {
        return additionalInterestIndicatorDisable;
    }

    public void setAdditionalInterestIndicatorDisable(boolean additionalInterestIndicatorDisable) {
        this.additionalInterestIndicatorDisable = additionalInterestIndicatorDisable;
    }

    public boolean isIntSlabDcFlagDisable() {
        return intSlabDcFlagDisable;
    }

    public void setIntSlabDcFlagDisable(boolean intSlabDcFlagDisable) {
        this.intSlabDcFlagDisable = intSlabDcFlagDisable;
    }

    public boolean isInterestSlabDescDisable() {
        return interestSlabDescDisable;
    }

    public void setInterestSlabDescDisable(boolean interestSlabDescDisable) {
        this.interestSlabDescDisable = interestSlabDescDisable;
    }

    public boolean isInterestSlabVesionRemarkDisable() {
        return interestSlabVesionRemarkDisable;
    }

    public void setInterestSlabVesionRemarkDisable(boolean interestSlabVesionRemarkDisable) {
        this.interestSlabVesionRemarkDisable = interestSlabVesionRemarkDisable;
    }

    public boolean isRecordStatusDisable() {
        return recordStatusDisable;
    }

    public void setRecordStatusDisable(boolean recordStatusDisable) {
        this.recordStatusDisable = recordStatusDisable;
    }

    public boolean isDeleteDisable() {
        return deleteDisable;
    }

    public void setDeleteDisable(boolean deleteDisable) {
        this.deleteDisable = deleteDisable;
    }

    public InterestCodeMaster() {
        try {
            interestMasterRemote = (InterestMasterFacadeRemote) ServiceLocator.getInstance().lookup("InterestMasterFacade");
            intCodeMaster = new ArrayList<InterestCodeMasterTable>();
            orgnBrCode = getOrgnBrCode();
            String setEndDate = "31/12/2099";
            setUserName(getUserName());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int min = cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);
            int hour = cal.get(Calendar.HOUR);
            refreshForm();
            //setTodayDate(sdf.format(date));
            setStartDate(date);
            setCreatedDate(sdf.format(date) + " " + hour + ":" + min + ":" + sec);
            setCreatedDateSlab(sdf.format(date) + " " + hour + ":" + min + ":" + sec);
            setCreatedBy(getUserName());
            setCreatedBySlab(getUserName());
            setEndDate(sdf.parse(setEndDate));
            setSlabAllValues();
            setIntTableCodeDisable(false);
            setBaseIndFlabDisable(false);
            setLoanDaysDisable(false);
            setLoanMonthDisable(false);
            setBgSlabAmtDisable(false);
            setEndSlabAmtDisable(false);
            setDebitDisable(false);
            setCreditDisable(false);
            setSaveDisable(true);
            setUpdateDisable(true);
            setNewSlabDisable(true);
//            setMsg("");
//            //manish kumar
//            cleanIntPer = "0";//intialize by manish kumar(18-05-17)
//            cleanPortionInd = "N";//intialize by manish kumar
//            qisIntPer = "0";//intialize by manish kumar
//            qisPortionInd = "N";////intialize by manish kumar
//            additionalInt = "0";//intialize by manish kumar
//            additionalInd = "N";
//            //
            onLoadDropDown();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * *************************** SET THE VALUES IN TABLE ON CHANGE OF RECORD
     * STATUS ***********************
     */
    public void setTable() {
        try {
            setMsg("");
            if (!interestMasterRemote.isIntrestTableCodeExit(intTableCode)) {
                this.setCleanPortionInd("N");
                this.setCleanIntPer("0");
                this.setQisPortionInd("N");
                this.setQisIntPer("0");
                this.setAdditionalInd("N");
                this.setAdditionalInt("0");
                String msg1 = validationOfSlab();
                if (msg1.equalsIgnoreCase("false")) {
                    return;
                }
            }

            InterestCodeMasterTable mt = new InterestCodeMasterTable();
            //Added by manish kumar

            int loanPdMonths = Integer.parseInt(getLoanPeriodMonths());
            int loanPdDays = Integer.parseInt(getLoanPeriodDays());
            int row = -1;
            if (interestMasterRemote.isIntrestTableCodeExit(intTableCode)) {
                if (!flag.equalsIgnoreCase("New Slab")) {
                    row = currentRow - 1;
                } else {
                    this.setCleanPortionInd("N");
                    this.setCleanIntPer("0");
                    this.setQisPortionInd("N");
                    this.setQisIntPer("0");
                    this.setAdditionalInd("N");
                    this.setAdditionalInt("0");
                    String msg1 = validationOfSlab();
                    if (msg1.equalsIgnoreCase("false")) {
                        return;
                    }
                    if (!intCodeMaster.isEmpty()) {
                        if (loanPdDays == 0) {
                            for (int i = 0; i < intCodeMaster.size(); i++) {
                                if (intCodeMaster.get(i).getLoanPeriodMonths() == loanPdMonths) {
                                    row = i;
                                }
                            }
                        }
                        if (loanPdMonths == 0) {
                            for (int i = 0; i < intCodeMaster.size(); i++) {
                                if (intCodeMaster.get(i).getLoanPeriodDays() == loanPdDays) {
                                    row = i;
                                }
                            }
                        }
                    }

                }
            } else {
                if (!intCodeMaster.isEmpty()) {
                    if (loanPdDays == 0) {
                        for (int i = 0; i < intCodeMaster.size(); i++) {
                            if (intCodeMaster.get(i).getLoanPeriodMonths() == loanPdMonths) {
                                row = i;
                            }
                        }
                    }
                    if (loanPdMonths == 0) {
                        for (int i = 0; i < intCodeMaster.size(); i++) {
                            if (intCodeMaster.get(i).getLoanPeriodDays() == loanPdDays) {
                                row = i;
                            }
                        }
                    }
                }

                if (getBgSlabAmt().indexOf(".") != -1) {
                    if (getBgSlabAmt().substring(getBgSlabAmt().indexOf(".") + 1, getBgSlabAmt().length()).length() != 2) {
                        setMsg("Begin slab amount must have two digit after point !");
                        return;
                    }
                }
                if (getEndSlabAmt().indexOf(".") != -1) {
                    if (getEndSlabAmt().substring(getEndSlabAmt().indexOf(".") + 1, getEndSlabAmt().length()).length() != 2) {
                        setMsg("End slab amount must have two digit after point !");
                        return;
                    }
                }

                double beginSlabAmt = Double.parseDouble(getBgSlabAmt());
                double endSlabAmt = Double.parseDouble(getEndSlabAmt());
                if (intCodeMaster.isEmpty()) {
                    if (beginSlabAmt > 0) {
                        setMsg("Begin slab amount must be zero at first adding slab!");
                        return;
                    }
                } else {
                    List list1 = new ArrayList();
                    List list2 = new ArrayList();
                    for (int i = 0; i < intCodeMaster.size(); i++) {
                        list1.add(intCodeMaster.get(i).getLoanPeriodMonths());
                        list2.add(intCodeMaster.get(i).getLoanPeriodDays());
                    }
                    Collections.sort(list1);
                    int lowestPeriodMonth = Integer.parseInt(list1.get(0).toString());
                    Collections.sort(list2);
                    int lowestPeriodDays = Integer.parseInt(list2.get(0).toString());
                    System.out.println("Value :-" + list1.get(0));
                    if (loanPdMonths < lowestPeriodMonth) {
                        setMsg("Loan Period can not be less than loan period added in grid !");
                        return;
                    }
                    if (loanPdDays < lowestPeriodDays) {
                        setMsg("Loan Period can not be less than loan period added in grid !");
                        return;
                    }
                    if (beginSlabAmt > 0) {
                        int c1 = 0;
                        for (int i = 0; i < intCodeMaster.size(); i++) {
                            if (intCodeMaster.get(i).getLoanPeriodMonths() == loanPdMonths && loanPdDays == 0) {
                                c1++;
                                break;
                            } else if (intCodeMaster.get(i).getLoanPeriodDays() == loanPdDays && loanPdMonths == 0) {
                                c1++;
                                break;
                            }
                        }
                        if (c1 == 0) {
                            setMsg("Begin slab amount must be zero at first adding slab, if loan period is different from added slab in grid!");
                            return;
                        }

                    }
                }
                if (!intCodeMaster.isEmpty()) {
                    for (int i = 0; i < intCodeMaster.size(); i++) {
                        if (Double.parseDouble(intCodeMaster.get(i).getEndSlabAmt()) >= beginSlabAmt && intCodeMaster.get(i).getLoanPeriodMonths() == loanPdMonths && loanPdDays == 0) {
                            setMsg("Begin slab amount can not less or equal to End slab amount of added row in grid !");
                            return;
                        } else if (Double.parseDouble(intCodeMaster.get(i).getEndSlabAmt()) >= beginSlabAmt && intCodeMaster.get(i).getLoanPeriodDays() == loanPdDays && loanPdMonths == 0) {
                            setMsg("Begin slab amount can not less or equal to End slab amount of added row in grid !");
                            return;
                        }
                    }
                }
            }

            //prevEndSlabAmt = endSlabAmt;
            mt.setLoanPeriodMonths(loanPdMonths);
            mt.setLoanPeriodDays(loanPdDays);
            mt.setAdditionalInt(Float.parseFloat(getAdditionalInt()));
            mt.setAdditionalInd(getAdditionalInd());
            //modify by manish kumar
            if (getBgSlabAmt().indexOf(".") == -1) {
                mt.setBgSlabAmt(getBgSlabAmt() + ".00");
            } else {
                mt.setBgSlabAmt(getBgSlabAmt());
            }
            mt.setCleanIntPer(Float.parseFloat(getCleanIntPer()));
            mt.setCleanPortionInd(getCleanPortionInd());
            //modify by manish kumar
            if (getEndSlabAmt().indexOf(".") == -1) {
                mt.setEndSlabAmt(getEndSlabAmt() + ".00");
            } else {
                mt.setEndSlabAmt(getEndSlabAmt());
            }
            mt.setIntSlabDcFlag(getIntSlabDcFlag());
            mt.setNormalIntInd(getNormalIntInd());
            mt.setNormalIntPer(Float.parseFloat(getNormalIntPer()));
            mt.setPeenalIntInd(getPeenalIntInd());
            mt.setPeenalIntPer(Float.parseFloat(getPeenalIntPer()));
            mt.setQisIntPer(Float.parseFloat(getQisIntPer()));
            mt.setQisPortionInd(getQisPortionInd());
            mt.setRecordStatus(getRecordStatus());
            mt.setModificationCount(getModificationCounter() + 1);
            mt.setSno(i + 1);
            mt.setIntSlabVerRemark(getIntSlabVerRemark());
            mt.setIntSlabDes(getIntSlabDes());
            mt.setIntSlabVerNo(getIntVerNoSlab());
            mt.setIntTableCode(getIntTableCode());
            mt.setCurrencyCode(getCurrencyCode());
            mt.setIntVerNo(getIntVerNo());
            if (row == -1) {
                intCodeMaster.add(0, mt);
            } else //if(row != 0)
            {
                intCodeMaster.add(row + 1, mt);
            }
//            else
//                intCodeMaster.add(row,mt);
            //intCodeMaster.add(mt);
            String finalAmt = getEndSlabAmt();
            resetSlab();
            if (finalAmt.indexOf(".") == -1) {
                setBgSlabAmt(finalAmt + ".01");
            } else {

                int temp = Integer.parseInt(finalAmt.substring(finalAmt.indexOf(".") + 1, finalAmt.length()));
                ++temp;
                finalAmt = finalAmt.substring(0, finalAmt.indexOf("."));
                if (temp > 9) {
                    finalAmt += "." + temp;
                } else {
                    finalAmt += ".0" + temp;
                }
                setBgSlabAmt(finalAmt);//+""+temp
            }
            if (loanPdMonths > 0) {
                setLoanDaysDisable(true);
                this.loanPeriodDays = loanPdDays + "";
                this.loanPeriodMonths = loanPdMonths + "";
            }
            if (loanPdDays > 0) {
                setLoanMonthDisable(true);
                this.loanPeriodMonths = loanPdMonths + "";
                this.loanPeriodDays = loanPdDays + "";
            }

        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void setDebitDisable() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (basePerCredit != null) {
                Matcher perDebit = p.matcher(basePerCredit);
                if (!perDebit.matches()) {
                    msg = "Enter numeric Value for Base Percentage Credit.";
                    setMsg(msg);
                    return;
                } else {
                    if (basePerCredit != null) {
                        if (Float.parseFloat(basePerCredit) > 0) {
                            setBasePerDebit("0");
                            setDebitDisable(true);
                        }
                        if (Float.parseFloat(basePerCredit) < 0 || Float.parseFloat(basePerCredit) == 0) {
                            setDebitDisable(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void setCreditDisable() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (basePerDebit != null) {
                Matcher perDebit = p.matcher(basePerDebit);
                if (!perDebit.matches()) {
                    msg = "Enter numeric Value for Base Percentage Debit.";
                    setMsg(msg);
                    return;
                } else {
                    if (basePerDebit != null) {
                        if (Float.parseFloat(basePerDebit) > 0) {
                            setBasePerCredit("0");
                            setCreditDisable(true);
                        }
                        if (Float.parseFloat(basePerDebit) < 0 || Float.parseFloat(basePerDebit) == 0) {
                            setCreditDisable(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void checkSelectedRow() {
        try {
            if (!currentItem.getAdditionalInd().equalsIgnoreCase(additionalInd) || !currentItem.getPeenalIntInd().equalsIgnoreCase(peenalIntInd)
                    || !currentItem.getCleanPortionInd().equalsIgnoreCase(cleanPortionInd) || !currentItem.getNormalIntInd().equalsIgnoreCase(normalIntInd)
                    || !currentItem.getQisPortionInd().equalsIgnoreCase(qisPortionInd) || currentItem.getRecordStatus().equals(recordStatus)) {
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************ SET THE VALUES IN THE PAGE FIELD ON THE
     * SELECTION OF THE CURRENT ROW **********************
     */
    public void select() throws ParseException {
        try {
            currencyCodeDisable = true;
            select = 1;
            slabDisable(false);
            if (currentItem.getIntTableCode() == null) {
                return;
            }
            tableIntCode = currentItem.getIntTableCode();
            int days = currentItem.getLoanPeriodDays();
            if (intTableCode != null && currentItem.getIntTableCode() != null) {
                if (intTableCode.equalsIgnoreCase(currentItem.getIntTableCode())) {
                    count2 = count1;
                    count1 = count1 + 1;
                    if (count2 != count1) {
                        if (loanPeriodDays != null) {
                            setTable();
                        }
                    }
                } else {
                    count1 = 0;
                }
            }
            if (intTableCode == null || intTableCode.equalsIgnoreCase("")) {
                setDataInField();
                resetSlab();
                for (int j = 0; j < intCodeMaster.size(); j++) {
                    if (currentItem.getBgSlabAmt().toString().equalsIgnoreCase(intCodeMaster.get(j).getBgSlabAmt().toString())) {
                        currentRow = j;
                        intCodeMaster.remove(currentRow);
                    }
                }
            } else {
                intCodeMaster.remove(currentRow);
            }
            setLoanPeriodDays(currentItem.getLoanPeriodDays() + "");
            setLoanPeriodMonths(currentItem.getLoanPeriodMonths() + "");
            setBgSlabAmt(currentItem.getBgSlabAmt().toString());
            setEndSlabAmt(currentItem.getEndSlabAmt().toString());
            setNormalIntInd(currentItem.getNormalIntInd());
            setNormalIntPer(currentItem.getNormalIntPer().toString());
            setPeenalIntInd(currentItem.getPeenalIntInd());
            setPeenalIntPer(currentItem.getPeenalIntPer().toString());
            setCleanIntPer(currentItem.getCleanIntPer().toString());
            setCleanPortionInd(currentItem.getCleanPortionInd());
            setQisIntPer(currentItem.getQisIntPer().toString());
            setQisPortionInd(currentItem.getQisPortionInd());
            setAdditionalInd(currentItem.getAdditionalInd());
            setAdditionalInt(currentItem.getAdditionalInt().toString());
            setIntSlabDcFlag(currentItem.getIntSlabDcFlag());
            setRecordStatus(currentItem.getRecordStatus());
            setModificationCounter(currentItem.getModificationCount());
            setIntVerNoSlab(Integer.parseInt(currentItem.getIntSlabVerNo().toString()));
            setIntVerRemark(currentItem.getIntSlabVerRemark());
            setIntSlabDes(currentItem.getIntSlabDes());
            if (currentItem.getCreatedBy() == null) {
            } else {
                setCreatedBySlab(currentItem.getCreatedBy());
            }
            if (currentItem.getCreatedDate() == null) {
            } else {
                String yearModified = currentItem.getCreatedDate().substring(0, 4);
                String monthModified = currentItem.getCreatedDate().substring(5, 7);
                String dayModified = currentItem.getCreatedDate().substring(8, 10);
                String timeModified = currentItem.getCreatedDate().substring(10);
                String modifiedDate = dayModified + "/" + monthModified + "/" + yearModified + timeModified;
                setCreatedDateSlab(modifiedDate);
            }
            if (currentItem.getUpdatedBy() == null) {
            } else {
                setLastUpdatedBySlab(currentItem.getUpdatedBy());
            }
            if (currentItem.getUpdatedDate() == null) {
            } else {
                String yearModified = currentItem.getUpdatedDate().substring(0, 4);
                String monthModified = currentItem.getUpdatedDate().substring(5, 7);
                String dayModified = currentItem.getUpdatedDate().substring(8, 10);
                String timeModified = currentItem.getUpdatedDate().substring(10);
                String modifiedDate = dayModified + "/" + monthModified + "/" + yearModified + timeModified;
                setLastUpdatedDateSlab(modifiedDate);
            }
            //check here insert table code is existing in database or not
            if (interestMasterRemote.isIntrestTableCodeExit(intTableCode)) {
                //if true
                setLoanDaysDisable(true);
                setLoanMonthDisable(true);
                setBgSlabAmtDisable(true);
                setEndSlabAmtDisable(true);
                setUpdateDisable(false);
            } else {
//                setLoanDaysDisable(false);
//                setLoanMonthDisable(false);
                setBgSlabAmtDisable(false);
                setEndSlabAmtDisable(false);
                setUpdateDisable(true);
            }

        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************* SET THE EXISTING DATA IN THE FIELD AND TABLE
     * CORRESPONDING TO ENTERED TABLE CODE ****************
     */
    public void setDataInField() {
        try {
            setMsg("");
            resetSlab();
            //Added by Manish kumar
            this.flag = "";
            intCodeMasterTmp = new ArrayList<InterestCodeMasterTable>();//------
            List intTableCodeList = new ArrayList();
            List intSlabTableCodeResultList = new ArrayList();
            if (intTableCode == null || intTableCode.equalsIgnoreCase("")) {
                if (tableIntCode == null || tableIntCode.equalsIgnoreCase("")) {
                    setMsg("Please Enter the Interest Table Code.");
                    return;
                }
                String check = interestMasterRemote.checkRefCodeInterestCodeMaster("201", tableIntCode);
                if (check.equalsIgnoreCase("false")) {
                    setMsg("Please insert " + tableIntCode + " Code in the Reference Table.");
                    String tempIntCode = intTableCode;
                    resetMaster();
                    setIntTableCode(tempIntCode);
                    intCodeMaster = new ArrayList<InterestCodeMasterTable>();
                    setSaveDisable(false);
                    return;
                }
                intTableCodeList = interestMasterRemote.getDataInterestCodeMaster(tableIntCode);
            } else {
                String check = interestMasterRemote.checkRefCodeInterestCodeMaster("201", intTableCode);
                if (check.equalsIgnoreCase("false")) {
                    setMsg("Please insert " + intTableCode + " Code in the Reference Table.");
                    String tempIntCode = intTableCode;
                    resetMaster();
                    setIntTableCode(tempIntCode);
                    intCodeMaster = new ArrayList<InterestCodeMasterTable>();
                    setSaveDisable(false);
                    return;
                }
                intTableCodeList = interestMasterRemote.getDataInterestCodeMaster(intTableCode);
            }
            if (intTableCodeList.isEmpty()) {
                setMsg("No Master Exists Corresponding to " + intTableCode + ".Please fill up the appropiate values.");
                String tempIntCode = intTableCode;
                resetMaster();
                setIntTableCode(tempIntCode);
                intCodeMaster = new ArrayList<InterestCodeMasterTable>();
                setSaveDisable(false);
                slabDisable(false);
                //Added by Manish kumar
                this.newSlabDisable = true;
                this.updateDisable = true;
                this.deleteDisable = false;
                //--
                return;
            } else {
                setCurrencyCodeDisable(true);
                setNewSlabDisable(false);
                setIntTableCodeDisable(true);
                Vector ele = (Vector) intTableCodeList.get(0);
                setIntTableCode(ele.get(0).toString());
                setIntRateDes(ele.get(1).toString());
                setIntVerNo(Integer.parseInt(ele.get(2).toString()));
                setCurrencyCode(ele.get(4).toString());
                setRecordStatusMs(ele.get(5).toString());
                SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
                setStartDate(yyyyMMdd.parse(ele.get(6).toString()));
                setEndDate(yyyyMMdd.parse(ele.get(7).toString()));
                setBasePerCredit(ele.get(13).toString());
                setBasePerDebit(ele.get(12).toString());
                setIntTableDesc(ele.get(3).toString());
                setIntVerRemark(ele.get(9).toString());
                setModificationMasterCounter(Integer.parseInt(ele.get(11).toString()));
                setBaseIntTableCode(ele.get(10).toString());
                setBaseindFlag(ele.get(8).toString());
                setCreatedBy(ele.get(14).toString());
                String yearModified = ele.get(15).toString().substring(0, 4);
                String monthModified = ele.get(15).toString().substring(5, 7);
                String dayModified = ele.get(15).toString().substring(8, 10);
                String timeModified = ele.get(15).toString().substring(10);
                String modifiedDate = dayModified + "/" + monthModified + "/" + yearModified + timeModified;
                setCreatedDate(modifiedDate);
                if (ele.get(16) == null || ele.get(16).equals("")) {
                    setLastUpdatedBy("");
                } else {
                    setLastUpdatedBy(ele.get(16).toString());
                }
                if (ele.get(17) == null || ele.get(17).equals("")) {
                } else {
                    String lmodifiedDate = dayModified + "/" + monthModified + "/" + yearModified + timeModified;
                    setLastUpdatedDate(lmodifiedDate);
                }
                //Added by manish kumar
                slabDisable(true);
                this.saveDisable = true;
                this.deleteDisable = true;
                //---------
            }
            if (intTableCode == null) {
                intSlabTableCodeResultList = interestMasterRemote.getDataIntSlabInterestCodeMaster(currentItem.getIntTableCode(), currentItem.getIntVerNo(), currentItem.getLoanPeriodDays(),
                        currentItem.getLoanPeriodMonths(), Float.parseFloat(currentItem.getBgSlabAmt()), Float.parseFloat(currentItem.getEndSlabAmt()), currencyCode);
                setIntTableCode(tableIntCode);
            } else {
                if (loanPeriodDays == null || loanPeriodDays.equals("")) {
                    loanPeriodDays = "0";
                }
                if (loanPeriodMonths == null || loanPeriodMonths.equals("")) {
                    loanPeriodMonths = "0";
                }
                if (bgSlabAmt == null || bgSlabAmt.equals("")) {
                    bgSlabAmt = "0";
                }
                if (endSlabAmt == null || endSlabAmt.equals("")) {
                    endSlabAmt = "0";
                }
                intSlabTableCodeResultList = interestMasterRemote.getDataIntSlabInterestCodeMaster(intTableCode, intVerNo, Integer.parseInt(loanPeriodDays),
                        Integer.parseInt(loanPeriodMonths), Float.parseFloat(bgSlabAmt), Float.parseFloat(endSlabAmt), currencyCode);
                //Added by manish kumar
                loanPeriodDays = "";
                loanPeriodMonths = "";
                bgSlabAmt = "";
                endSlabAmt = "";//----
            }
            intCodeMaster = new ArrayList<InterestCodeMasterTable>();
            if (intSlabTableCodeResultList.isEmpty()) {
                setMsg("No Slab Exists Corresponding to " + intTableCode);
            } else {
                for (int j = 0; j < intSlabTableCodeResultList.size(); j++) {
                    Vector slabVector = (Vector) intSlabTableCodeResultList.get(j);
                    InterestCodeMasterTable mt = new InterestCodeMasterTable();
                    mt.setIntVerNo(Integer.parseInt(slabVector.get(1).toString()));
                    mt.setCurrencyCode(slabVector.get(2).toString());
                    mt.setIntSlabDcFlag(slabVector.get(3).toString());
                    mt.setIntSlabVerNo(Integer.parseInt(slabVector.get(4).toString()));
                    mt.setIntSlabDes(slabVector.get(5).toString());
                    mt.setLoanPeriodMonths(Integer.parseInt(slabVector.get(6).toString()));
                    mt.setLoanPeriodDays(Integer.parseInt(slabVector.get(7).toString()));
                    mt.setRecordStatus(slabVector.get(8).toString());
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    mt.setBgSlabAmt(formatter.format(new Double(slabVector.get(9).toString())));
                    mt.setEndSlabAmt(formatter.format(new Double(slabVector.get(10).toString())));
                    mt.setNormalIntInd(slabVector.get(11).toString());
                    mt.setNormalIntPer(Float.parseFloat(slabVector.get(12).toString()));
                    mt.setPeenalIntInd(slabVector.get(13).toString());
                    mt.setPeenalIntPer(Float.parseFloat(slabVector.get(14).toString()));
                    mt.setCleanPortionInd(slabVector.get(15).toString());
                    mt.setCleanIntPer(Float.parseFloat(slabVector.get(16).toString()));
                    mt.setQisPortionInd(slabVector.get(17).toString());
                    mt.setQisIntPer(Float.parseFloat(slabVector.get(18).toString()));
                    mt.setAdditionalInd(slabVector.get(19).toString());
                    mt.setAdditionalInt(Float.parseFloat(slabVector.get(20).toString()));
                    mt.setModificationCount(Integer.parseInt(slabVector.get(21).toString()));
                    mt.setIntSlabVerRemark(slabVector.get(22).toString());
                    mt.setSno(j + 1);
                    mt.setIntTableCode(slabVector.get(0).toString());
                    mt.setCreatedBy(slabVector.get(23).toString());
                    mt.setCreatedDate(slabVector.get(24).toString());
                    if (slabVector.get(25) == null) {
                    } else {
                        mt.setUpdatedBy(slabVector.get(25).toString());
                    }
                    if (slabVector.get(26) == null) {
                    } else {
                        mt.setUpdatedDate(slabVector.get(26).toString());
                    }
                    intCodeMaster.add(mt);
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************** SET THE DROPDOWN VALUES
     * ***********************************
     */
    public void onLoadDropDown() {
        try {
            List currencyCodeResultList = new ArrayList();
            baseIntTableCodeResultList = interestMasterRemote.getBaseIntTableCodeInterestCodeMaster();
            baseIntTableCodeList = new ArrayList<SelectItem>();
            baseIntTableCodeList.add(new SelectItem("--SELECT--"));
            for (int j = 0; j < baseIntTableCodeResultList.size(); j++) {
                Vector ele = (Vector) baseIntTableCodeResultList.get(j);
                baseIntTableCodeList.add(new SelectItem(ele.get(0).toString()));
            }
            currencyCodeResultList = interestMasterRemote.getCurrencyCodeInterestCodeMaster(176);
            currencyCodeList = new ArrayList<SelectItem>();
            currencyCodeList.add(new SelectItem("--SELECT--"));
            for (int j = 0; j < currencyCodeResultList.size(); j++) {
                Vector ele = (Vector) currencyCodeResultList.get(j);
                for (Object ee : ele) {
                    currencyCodeList.add(new SelectItem(ee.toString()));
                }
            }
            /**
             * ************************** hard Corded Values
             * ****************************
             */
            setRecordStatusList(new ArrayList<SelectItem>());
            recordStatusList.add(new SelectItem("0", "--SELECT--"));
            recordStatusList.add(new SelectItem("A", "Active"));
            recordStatusList.add(new SelectItem("D", "Deleted"));
            setIntFlagCrDeFlgList(new ArrayList<SelectItem>());
            intFlagCrDeFlgList.add(new SelectItem("0", "--SELECT--"));
            intFlagCrDeFlgList.add(new SelectItem("Cr", "Credit"));
            intFlagCrDeFlgList.add(new SelectItem("Dr", "Debit"));
            setAddPortionList(new ArrayList<SelectItem>());
            addPortionList.add(new SelectItem("0", "--SELECT--"));
            addPortionList.add(new SelectItem("F", "F"));
            addPortionList.add(new SelectItem("D", "D"));
            addPortionList.add(new SelectItem("N", "N"));
            setPeenalIntIndList(new ArrayList<SelectItem>());
            peenalIntIndList.add(new SelectItem("0", "--SELECT--"));
            peenalIntIndList.add(new SelectItem("F", "F"));
            peenalIntIndList.add(new SelectItem("D", "D"));
            peenalIntIndList.add(new SelectItem("N", "N"));
            setQisPortionList(new ArrayList<SelectItem>());
            qisPortionList.add(new SelectItem("0", "--SELECT--"));
            qisPortionList.add(new SelectItem("F", "F"));
            qisPortionList.add(new SelectItem("D", "D"));
            qisPortionList.add(new SelectItem("N", "N"));
            setCleanPortionIndList(new ArrayList<SelectItem>());
            cleanPortionIndList.add(new SelectItem("0", "--SELECT--"));
            cleanPortionIndList.add(new SelectItem("F", "F"));
            cleanPortionIndList.add(new SelectItem("D", "D"));
            cleanPortionIndList.add(new SelectItem("N", "N"));
            setNormalIntIndList(new ArrayList<SelectItem>());
            normalIntIndList.add(new SelectItem("0", "--SELECT--"));
            normalIntIndList.add(new SelectItem("F", "F"));
            normalIntIndList.add(new SelectItem("D", "D"));
            normalIntIndList.add(new SelectItem("N", "N"));
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * *************************** FUCTION TO SAVE THE VALUE
     * ****************************************
     */
    public void saveAction() {
        try {
            setMsg("");
            String msg1 = "";
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (intTableCode == null) {
                msg1 = "\nPlease Enter the Interest Table Code.  ";
                setMsg(msg1);
                return;
            }
            String check = interestMasterRemote.checkRefCodeInterestCodeMaster("201", intTableCode);
            if (check.equalsIgnoreCase("false")) {
                setMsg("Interest Table Code" + intTableCode + " not found in the Reference Table ");
                String tempIntCode = intTableCode;
                resetMaster();
                setIntTableCode(tempIntCode);
                intCodeMaster = new ArrayList<InterestCodeMasterTable>();
                return;
            }
            if (intRateDes.equalsIgnoreCase("")) {
                msg1 = msg1 + "\nPlease Enter the Interest Rate Description.  ";
                setMsg(msg1);
                return;
            }
            if (currencyCode.equalsIgnoreCase("--SELECT--")) {
                msg1 = msg1 + "\nPlease Select  the Currency Code.";
                setMsg(msg1);
                return;
            }
            if (recordStatusMs.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Enter the Record Status. ";
                setMsg(msg1);
                return;
            }
            if (startDate == null) {
                msg1 = msg1 + "\nPlease Enter the Start Date.";
                setMsg(msg1);
                return;
            }
            if (endDate == null) {
                msg1 = msg1 + "\nPlease Enter the End Date.  ";
                setMsg(msg1);
                return;
            }
            // Added by manish

            if (startDate.compareTo(endDate) > 0) {
                this.setMsg("Start Date can not be greater than End Date.");
                return;
            }//-----
            if (basePerCredit == null || basePerDebit == null) {
                msg1 = msg1 + "\nPlease Enter Either Base Percentage Cebit or Base Percentage Debit  ";
                setMsg(msg1);
                return;
            } else {
                if (basePerCredit != null) {
                    Matcher perCredit = p.matcher(basePerCredit);
                    if (!perCredit.matches()) {
                        msg1 = msg1 + "Base Percentage Credit Should be numeric";
                        setMsg(msg1);
                        return;
                    }
                }
                if (basePerDebit != null) {
                    Matcher perDebit = p.matcher(basePerDebit);
                    if (!perDebit.matches()) {
                        msg1 = msg1 + "Base Percentage Debit Should be numeric";
                        setMsg(msg1);
                        return;
                    }
                }
            }
            if (baseIntTableCode.equalsIgnoreCase("--SELECT--")) {
                msg1 = msg1 + "\nPlease Enter Select the Base Interest Table Code  ";
                setMsg(msg1);
                return;
            }
            if (msg1.equalsIgnoreCase("")) {
            } else {
                setMsg(msg1);
                return;
            }
            if (flag.equalsIgnoreCase("New Slab")) {
                msg1 = interestMasterRemote.AddNewSlabInterestCodeMaster(intCodeMaster, intTableCode, intRateDes, currencyCode, recordStatusMs,
                        ymd.format(startDate), ymd.format(endDate), Float.parseFloat(basePerCredit), Float.parseFloat(basePerDebit), intTableDesc, intVerRemark,
                        baseIntTableCode, baseindFlag, this.getUserName(), ymd.format(sdf.parse(getTodayDate())));
                if (msg1.equalsIgnoreCase("true")) {
                    msg1 = "Data Saved Successfully";
                    //Added by manish kumar
                    intCodeMasterTmp = null;
                    //--
                } else if (msg1.equalsIgnoreCase("Check the today date you have passed")) {
                    msg1 = "Check the today date you have passed";
                } else {
                    msg1 = "Data is not Saved";
                }
            } else {
                msg1 = interestMasterRemote.saveInterestCodeMaster(intCodeMaster, intTableCode, intRateDes, currencyCode, recordStatusMs,
                        ymd.format(startDate), ymd.format(endDate), Float.parseFloat(basePerCredit), Float.parseFloat(basePerDebit), intTableDesc, intVerRemark,
                        baseIntTableCode, baseindFlag, this.getUserName(), ymd.format(sdf.parse(getTodayDate())));
                if (msg1.equalsIgnoreCase("true")) {
                    msg1 = "Data Saved Successfully";
                    //Added by manish kumar
                    intCodeMasterTmp = null;
                    //--
                } else if (msg1.equalsIgnoreCase("Check the today date you have passed")) {
                    msg1 = "Check the today date you have passed";
                } else {
                    msg1 = "Data is not Saved";
                }
            }
            setDebitDisable(false);
            setCreditDisable(false);
            setIntTableCodeDisable(false);
            intCodeMaster = new ArrayList<InterestCodeMasterTable>();
            setDataInField();
            resetMaster();
            resetSlab();
            setMsg(msg1);
            setSaveDisable(true);
            setIntTableCodeDisable(false);
        } catch (ApplicationException e) {
            setMsg(e.getMessage());
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void delete() {
        try {
            // Added by manish kumar
            if (interestMasterRemote.isIntrestTableCodeExit(intTableCode) && !flag.equalsIgnoreCase("New Slab")) {
                intCodeMaster.remove(currentRow);
            } else {
                System.out.println("Size " + intCodeMaster.size());
                int row = 0;
                System.out.println("Period :" + intCodeMaster.get(currentRow).getLoanPeriodMonths());
                for (int i = 0; i < intCodeMaster.size(); i++) {
                    if (intCodeMaster.get(currentRow).getLoanPeriodMonths() == intCodeMaster.get(i).getLoanPeriodMonths()) {
                        row++;
                    }
                }
                if (intCodeMaster.get(currentRow).getLoanPeriodMonths() > 0) {
                    if (row != 1) {
                        if (intCodeMaster.get(currentRow).getLoanPeriodMonths() == intCodeMaster.get(currentRow + 1).getLoanPeriodMonths()) {
                            intCodeMaster.get(currentRow + 1).setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                            if (currentRow != 0) {
                                setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                            }
                        } else {
                            setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                        }
                    } else {
                        setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                    }
                    this.loanPeriodMonths = "" + intCodeMaster.get(currentRow).getLoanPeriodMonths();
                    intCodeMaster.remove(currentRow);
                    validation1();
                    return;
                }
                if (intCodeMaster.get(currentRow).getLoanPeriodDays() > 0) {
                    if (row != 1) {
                        if (intCodeMaster.get(currentRow).getLoanPeriodDays() == intCodeMaster.get(currentRow + 1).getLoanPeriodDays()) {
                            intCodeMaster.get(currentRow + 1).setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                            setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                        } else {
                            setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                        }
                    } else {
                        setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
                    }
                    this.loanPeriodDays = "" + intCodeMaster.get(currentRow).getLoanPeriodDays();
                    intCodeMaster.remove(currentRow);
                    validation2();
                }
            }

//            if(intCodeMaster.size() >= 2 && currentRow !=intCodeMaster.size()-1){
//                intCodeMaster.get(currentRow+1).setBgSlabAmt(intCodeMaster.get(currentRow).getBgSlabAmt());
//            }
//            intCodeMaster.remove(currentRow);
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void resetMaster() {
        try {
            setIntTableCode("");
            setIntRateDes("");
            setIntTableCode("");
            setBasePerCredit("");
            setBasePerDebit("");
            setIntVerNo(0);
            setIntRateDes("");
            setIntVerRemark("");
            setModificationMasterCounter(0);
            setBaseindFlag("");
            setCreatedBy("");
            setLastUpdatedBy("");
            setIntTableDesc("");
            setRecordStatusMs("0");
            setCurrencyCode("");
            setBaseIntTableCode("");
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************* FUCTION TO SET THE Base Indicator Flag ON THE
     * SELECTION OF THE Base Interest Table Code ***************************
     */
    public void setBaseIndFlag() {
        try {
            setMsg("");
            for (int j = 0; j < baseIntTableCodeResultList.size(); j++) {
                Vector ele = (Vector) baseIntTableCodeResultList.get(j);
                if (ele.get(0).toString().equals(baseIntTableCode)) {
                    setBaseindFlag(ele.get(1).toString());
                    setBasePerDebit(ele.get(2).toString());
                    setBasePerCredit(ele.get(3).toString());
                    setBaseIndFlabDisable(true);
                }
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************* SET ALL THE VALUES IN THE TABLE WHICH EXIST IN
     * THE DATA BASE *******************************
     */
    public void setSlabAllValues() {
        try {
            intCodeMaster = new ArrayList<InterestCodeMasterTable>();
            setMsg("");
            List slabAllValuesList = new ArrayList();
            slabAllValuesList = interestMasterRemote.getSlabInterestCodeMaster();
            if (slabAllValuesList.isEmpty()) {
                setMsg("No Data Exist in CBS_LOAN_INTEREST_SLAB_MASTER");
            } else {
                for (int j = 0; j < slabAllValuesList.size(); j++) {
                    Vector slabVector = (Vector) slabAllValuesList.get(j);
                    InterestCodeMasterTable mt = new InterestCodeMasterTable();
                    mt.setIntVerNo(Integer.parseInt(slabVector.get(1).toString()));
                    mt.setCurrencyCode(slabVector.get(2).toString());
                    mt.setIntSlabDcFlag(slabVector.get(3).toString());
                    mt.setIntSlabVerNo(Integer.parseInt(slabVector.get(4).toString()));
                    mt.setIntSlabDes(slabVector.get(5).toString());
                    mt.setLoanPeriodMonths(Integer.parseInt(slabVector.get(6).toString()));
                    mt.setLoanPeriodDays(Integer.parseInt(slabVector.get(7).toString()));
                    mt.setRecordStatus(slabVector.get(8).toString());
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    mt.setBgSlabAmt(formatter.format(new Double(slabVector.get(9).toString())));
                    mt.setEndSlabAmt(formatter.format(new Double(slabVector.get(10).toString())));
                    mt.setNormalIntInd(slabVector.get(11).toString());
                    mt.setNormalIntPer(Float.parseFloat(slabVector.get(12).toString()));
                    mt.setPeenalIntInd(slabVector.get(13).toString());
                    mt.setPeenalIntPer(Float.parseFloat(slabVector.get(14).toString()));
                    mt.setCleanPortionInd(slabVector.get(15).toString());
                    mt.setCleanIntPer(Float.parseFloat(slabVector.get(16).toString()));
                    mt.setQisPortionInd(slabVector.get(17).toString());
                    mt.setQisIntPer(Float.parseFloat(slabVector.get(18).toString()));
                    mt.setAdditionalInd(slabVector.get(19).toString());
                    mt.setAdditionalInt(Float.parseFloat(slabVector.get(20).toString()));
                    mt.setModificationCount(Integer.parseInt(slabVector.get(21).toString()));
                    mt.setIntSlabVerRemark(slabVector.get(22).toString());
                    mt.setSno(j + 1);
                    mt.setIntTableCode(slabVector.get(0).toString());
                    intCodeMaster.add(mt);
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ********************** TO UPDATE THE EXISTIN MASTER TABLE AND THE SLAB
     * *****************************
     */
    public void updateAction() {
        try {
            String msg1 = "";
            setMsg("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            //Added by manish kumar
            if (!this.endSlabAmt.equalsIgnoreCase("")) {
                setMsg("Please Select record status from Slab panal !");
                return;
            }//---

            if (intTableCode == null) {
                msg1 = "\nPlease Enter the Interest Table Code.  ";
                setMsg(msg1);
                return;
            }
            String check = interestMasterRemote.checkRefCodeInterestCodeMaster("201", intTableCode);
            if (check.equalsIgnoreCase("false")) {
                setMsg("Please insert " + intTableCode + " Code in the Reference Table.");
                String tempIntCode = intTableCode;
                resetMaster();
                setIntTableCode(tempIntCode);
                intCodeMaster = new ArrayList<InterestCodeMasterTable>();
                return;
            }
            if (intRateDes.equalsIgnoreCase("")) {
                msg1 = msg1 + "\nPlease Enter the Interest Rate Description.  ";
                setMsg(msg1);
                return;
            }
            if (currencyCode.equalsIgnoreCase("--SELECT--")) {
                msg1 = msg1 + "\nPlease Select  the Currency Code.";
                setMsg(msg1);
                return;
            }
            if (recordStatusMs.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Enter the Record Status. ";
                setMsg(msg1);
                return;
            }
            if (startDate == null) {
                msg1 = msg1 + "\nPlease Enter the Start Date.";
                setMsg(msg1);
                return;
            }
            if (endDate == null) {
                msg1 = msg1 + "\nPlease Enter the End Date.  ";
                setMsg(msg1);
                return;
            }
            if (basePerCredit == null || basePerDebit == null) {
                msg1 = msg1 + "\nPlease Enter Either Base Percentage Cebit or Base Percentage Debit  ";
                setMsg(msg1);
                return;
            } else {
                if (basePerCredit != null) {
                    Matcher perCredit = p.matcher(basePerCredit);
                    if (!perCredit.matches()) {
                        msg1 = "Enter numeric Value for Base Percentage Credit.";
                        setMsg(msg1);
                        return;
                    }
                }
                if (basePerDebit != null) {
                    Matcher perDebit = p.matcher(basePerDebit);
                    if (!perDebit.matches()) {
                        msg1 = "Enter numeric Value for Base Percentage Debit.";
                        setMsg(msg1);
                        return;
                    }
                }
            }
            if (baseIntTableCode.equalsIgnoreCase("--SELECT--")) {
                msg1 = msg1 + "\nPlease Enter Select the Base Interest Table Code  ";
                setMsg(msg1);
                return;
            }
            /**
             * ************************************************************************************
             */
            List intTableCodeList = new ArrayList();
            intTableCodeList = interestMasterRemote.getDataInterestCodeMaster(intTableCode);
            Vector ele = (Vector) intTableCodeList.get(0);
            String varIntRateDes = ele.get(1).toString();
            String varRecordStatusMs = ele.get(5).toString();
            String varBasePerCredit = ele.get(13).toString();
            String varBasePerDebit = ele.get(12).toString();
            String varIntTableDesc = ele.get(3).toString().toUpperCase();
            String varIntVerRemark = ele.get(9).toString();
            String varBaseIntTableCode = ele.get(10).toString();
            String varBaseindFlag = ele.get(8).toString();
            if (!varIntRateDes.equals(intRateDes) || !varRecordStatusMs.equals(recordStatusMs)
                    || Double.parseDouble(varBasePerCredit) != Double.parseDouble(basePerCredit)
                    || Double.parseDouble(varBasePerDebit) != Double.parseDouble(basePerDebit)
                    || !varIntTableDesc.equals(intTableDesc) || !varIntVerRemark.equals(intVerRemark)
                    || !varBaseIntTableCode.equals(baseIntTableCode) || !varBaseindFlag.equals(baseindFlag)) {
                SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
                String varStartDate = ele.get(6).toString().substring(0, 4) + ele.get(6).toString().substring(5, 7) + ele.get(6).toString().substring(8, 10);
                String strtDt = yyyyMMdd.format(startDate);
                Long dt1 = Long.parseLong(varStartDate);
                Long dt2 = Long.parseLong(strtDt);
                if (dt1.compareTo(dt2) == 0) {
                    msg1 = msg1 + "\nPlease Change the Starting Date";
                    setMsg(msg1);
                    return;
                }
            }
            /**
             * **********************************************************************************888888**8888
             */
            if (msg1.equalsIgnoreCase("")) {
            } else {
                setMsg(msg1);
                return;
            }
            String message = interestMasterRemote.updateMasterSlabInterestCodeMaster(intCodeMaster, intTableCode, intRateDes, currencyCode, recordStatusMs, ymd.format(startDate),
                    ymd.format(endDate), Float.parseFloat(basePerCredit), Float.parseFloat(basePerDebit), intTableDesc, intVerRemark, baseIntTableCode,
                    baseindFlag, this.getUserName(), ymd.format(sdf.parse(getTodayDate())));
            if (message.equalsIgnoreCase("true")) {
                setMsg("Data Updated Successfully");
            } else if (message.equalsIgnoreCase("Check the today date you have passed")) {
                setMsg("Check the today date you have passed");
            } else {
                setMsg("Data is not Updated");
            }
            setDebitDisable(false);
            setCreditDisable(false);
            setIntTableCodeDisable(false);
            setBaseIndFlabDisable(false);
            setCurrencyCodeDisable(false);
            resetMaster();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ***************************************** VALIDATION FUNCTION CALL IN
     * THE setTable FUNCTION *************************
     */
    public String validationOfSlab() {
        try {
            setMsg("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            String msg1 = "";
            if ((loanPeriodDays == null || loanPeriodDays.equals("")) && (loanPeriodMonths == null || loanPeriodMonths.equals(""))) {
                msg1 = msg1 + "\nPlease Enter Either the Loan Period Months or Loan Period Days.";
                setMsg(msg1);
                return "false";
            } else {
                if (loanPeriodMonths != null) {
                    Matcher loanMonths = p.matcher(loanPeriodMonths);
                    if (!loanMonths.matches()) {
                        msg1 = msg1 + "Enter numeric Value for Loan Period Months.";
                        setMsg(msg1);
                        return "false";
                    }
                }
                if (loanPeriodDays != null) {
                    Matcher loanDays = p.matcher(loanPeriodDays);
                    if (!loanDays.matches()) {
                        msg1 = msg1 + "Enter numeric Value for Loan Period Days.";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            if (bgSlabAmt == null) {
                msg1 = msg1 + "\nPlease Enter the Begin Slab Amt. ";
                setMsg(msg1);
                return "false";
            } else {
                Matcher bgSlab = p.matcher(bgSlabAmt);
                if (!bgSlab.matches()) {
                    msg1 = msg1 + "Enter numeric Value for Begin Slab Amount.";
                    setMsg(msg1);
                    return "false";
                }
            }
            /**
             * ****** commented by shipra Bcoz Data base is taking value in
             * exponential form **********
             */
            if (endSlabAmt == null || endSlabAmt.equals("")) {
                msg1 = msg1 + "\nPlease Enter the End Slab Amt. ";
                setMsg(msg1);
                return "false";
            } else {
                Matcher endSlab = p.matcher(endSlabAmt);
                if (!endSlab.matches()) {
                    msg1 = msg1 + "Enter numeric Value for End Slab Amount.";
                    setMsg(msg1);
                    return "false";
                }
            }
            if (bgSlabAmt != null && endSlabAmt != null) {
                Matcher bgSlab = p.matcher(bgSlabAmt);
                Matcher endSlab = p.matcher(endSlabAmt);
                if (endSlab.matches() && bgSlab.matches()) {
                    if (Float.parseFloat(bgSlabAmt) > Float.parseFloat(endSlabAmt)) {
                        msg1 = msg1 + "\nBegin Slab Amt can not be greater than End Slab Amt. ";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            /**
             * ************** comment end here **************
             */
            if (normalIntInd.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Select the Normal Int Indicator.  ";
                setMsg(msg1);
                return "false";
            }
            if (peenalIntInd.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Select the Peenal Int Indicator. ";
                setMsg(msg1);
                return "false";
            }//
            if (cleanPortionInd.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Select the Clean Portion Indicator.  ";
                setMsg(msg1);
                return "false";
            }
            if (qisPortionInd.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Select the QIS Portion Indicator.  ";
                setMsg(msg1);
                return "false";
            }
            if (additionalInd.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Select the Additional Interest Indicator. ";
                setMsg(msg1);
                return "false";
            }
            //
            if (normalIntPer == null || normalIntPer.equals("")) {
                normalIntPer = "0";
            } else {
                Matcher normalInt = p.matcher(normalIntPer);
                if (!normalInt.matches()) {
                    msg1 = msg1 + "Normal Interest percentage(%) Should be numeric.";
                    setMsg(msg1);
                    return "false";
                } else {
                    if (Float.parseFloat(normalIntPer) < 0 || Float.parseFloat(normalIntPer) > 100) {
                        msg1 = msg1 + "Normal Interest percentage(%) Should be b/w 1 to 100.";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            if (peenalIntPer == null || peenalIntPer.equals("")) {
                peenalIntPer = "0";
            } else {
                Matcher peenalInt = p.matcher(peenalIntPer);
                if (!peenalInt.matches()) {
                    msg1 = msg1 + " Enter numeric Value for Peenal Int percentage(%).";
                    setMsg(msg1);
                    return "false";
                } else {
                    if (Float.parseFloat(peenalIntPer) < 0 || Float.parseFloat(peenalIntPer) > 100) {
                        msg1 = msg1 + "Peenal Int percentage(%) Should be b/w 1 to 100.";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            if (cleanIntPer == null || cleanIntPer.equals("")) {
                cleanIntPer = "0";
            } else {
                Matcher cleanInt = p.matcher(cleanIntPer);
                if (!cleanInt.matches()) {
                    msg1 = msg1 + "Clean Portion percentage(%) Should be numeric.";
                    setMsg(msg1);
                    return "false";
                } else {
                    if (Float.parseFloat(cleanIntPer) < 0 || Float.parseFloat(cleanIntPer) > 100) {
                        msg1 = msg1 + "Clean Portion percentage(%) Should be b/w 1 to 100.";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            if (qisIntPer == null || qisIntPer.equals("")) {
                qisIntPer = "0";
            } else {
                Matcher qisInt = p.matcher(qisIntPer);
                if (!qisInt.matches()) {
                    msg1 = msg1 + "QIS Portion  percentage(%) Should be numeric.";
                    setMsg(msg1);
                    return "false";
                } else {
                    if (Float.parseFloat(qisIntPer) < 0 || Float.parseFloat(qisIntPer) > 100) {
                        msg1 = msg1 + "QIS Portion percentage(%) Should be b/w 1 to 100.";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            if (additionalInt == null || additionalInt.equals("")) {
                additionalInt = "0";
            } else {
                Matcher additional = p.matcher(additionalInt);
                if (!additional.matches()) {
                    msg1 = msg1 + "Enter numeric Value for Additional Interest percentage(%).";
                    setMsg(msg1);
                    return "false";
                } else {
                    if (Float.parseFloat(additionalInt) < 0 || Float.parseFloat(additionalInt) > 100) {
                        msg1 = msg1 + "Additional Interest percentage(%) Should be b/w 1 to 100.";
                        setMsg(msg1);
                        return "false";
                    }
                }
            }
            if (intSlabDcFlag.equalsIgnoreCase("0")) {
                msg1 = msg1 + "\nPlease Select Int Slab Dr/Cr. Flag. ";
                setMsg(msg1);
                return "false";
            }
            if (recordStatus.equalsIgnoreCase("0")) {
                this.setMsg("Please Select the Record Status.");
                return "false";
            }
            if (msg1.equalsIgnoreCase("")) {
            } else {
                setMsg(msg1);
                return "false";
            }
            return "true";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public void addNewEntry() {
        try {

            setMsg("");
            setDataInField();
            slabDisable(false);
            intCodeMasterTmp = intCodeMaster;
            intCodeMaster = new ArrayList<InterestCodeMasterTable>();
            resetSlab();
            setNewSlabDisable(true);
            setUpdateDisable(true);
            setSaveDisable(false);
            setLoanDaysDisable(false);
            setLoanMonthDisable(false);
            setBgSlabAmtDisable(false);
            setEndSlabAmtDisable(false);
            flag = "New Slab";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void resetSlab() {
        try {
            setLoanPeriodDays("");
            setLoanPeriodMonths("");
            setBgSlabAmt("");
            setEndSlabAmt("");
            setNormalIntInd("0");
            setNormalIntPer("");
            setPeenalIntInd("0");
            setPeenalIntPer("");
            setCleanPortionInd("0");
            setCleanIntPer("");
            setQisPortionInd("0");
            setQisIntPer("");
            setAdditionalInd("0");
            setAdditionalInt("");
            setIntSlabDcFlag("0");
            setIntSlabDes("");
            setRecordStatus("0");
            setCleanPortionInd("0");
            setIntSlabVerRemark("");
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        try {
            resetSlab();
            resetMaster();
            setLoanDaysDisable(false);
            setLoanMonthDisable(false);
            setBgSlabAmtDisable(false);
            setEndSlabAmtDisable(false);
            setModificationCounter(0);
            intCodeMaster = new ArrayList<InterestCodeMasterTable>();
            setSlabAllValues();
            setIntTableCodeDisable(false);
            setCurrencyCodeDisable(false);
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        refreshForm();
        setIntTableCodeDisable(false);
        return "case1";
    }

    public void getTableDetails() {
        try {
            this.setMsg("");
            tabSearch = new ArrayList<InterestCodeHelp>();
            List resultLt = new ArrayList();
            resultLt = interestMasterRemote.getDataHelpInterestCodeMaster("201");
            if (!resultLt.isEmpty()) {
                for (int j = 0; j < resultLt.size(); j++) {
                    Vector ele = (Vector) resultLt.get(j);
                    InterestCodeHelp rd = new InterestCodeHelp();
                    rd.setCode(ele.get(0).toString());
                    rd.setDesc((ele.get(1).toString()));
                    tabSearch.add(rd);
                }
            } else {
                this.setMsg("Records does not Exist. ");
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public String checkSlab() {
        try {
            setMsg("");
            /* added By Zeeshan */
            if (loanPeriodDays == null || loanPeriodDays.equalsIgnoreCase("")) {
                setMsg("Please Fill Loan Period Days");
                return "false";
            }
            if (loanPeriodMonths == null || loanPeriodMonths.equalsIgnoreCase("")) {
                setMsg("Please Fill Loan Period Months");
                return "false";
            }
            if (bgSlabAmt == null || bgSlabAmt.equalsIgnoreCase("")) {
                setMsg("Please Fill Begin Slab Amount");
                return "false";
            }
            if (endSlabAmt == null || endSlabAmt.equalsIgnoreCase("")) {
                setMsg("Please Fill End Slab Amount");
                return "false";
            }
            /* END */
            List<InterestCodeMasterTable> tmpIntTable = intCodeMasterTmp;
            for (int j = 0; j < tmpIntTable.size(); j++) {
                float tmpDays = tmpIntTable.get(j).getLoanPeriodDays();
                float tmpMonths = tmpIntTable.get(j).getLoanPeriodMonths();
                float tmpEndAmt = Float.parseFloat(tmpIntTable.get(j).getEndSlabAmt());
                float tmpBgAmt = Float.parseFloat(tmpIntTable.get(j).getBgSlabAmt());
                if (tmpDays == Float.parseFloat(loanPeriodDays) && tmpMonths == Float.parseFloat(loanPeriodMonths) && tmpEndAmt == Float.parseFloat(endSlabAmt) && tmpBgAmt == Float.parseFloat(bgSlabAmt)) {
                    setMsg("Entry Already Exists In The Table");
                    return "false";
                }
            }
            return "true";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public void validation1() {
        boolean bgSlabFlag = true;
        if (this.loanPeriodMonths.indexOf(".") > -1) {
            setBgSlabAmt("0");
            setMsg("Sorry, Only numeric is value allowed !");
            return;
        } else {
            setMsg("");
        }
        if (Integer.parseInt(this.loanPeriodMonths) > 0) {
            this.loanPeriodDays = "0";
        }
        if (intCodeMasterTmp.isEmpty()) {
            setBgSlabAmt("0");
            if (!intCodeMaster.isEmpty()) {
                List list1 = new ArrayList();
                Map<Integer, String> unsortMap = new HashMap<Integer, String>();
                for (int i = 0; i < intCodeMaster.size(); i++) {
                    list1.add(intCodeMaster.get(i).getLoanPeriodMonths());
                    String str = intCodeMaster.get(i).getBgSlabAmt();
                    str = str.substring(str.indexOf("."), str.length());
                    String str1 = intCodeMaster.get(i).getEndSlabAmt();
                    str1 = str1.substring(0, str1.indexOf("."));
                    if (str.equals(".00")) {
                        str1 += ".01";
                    } else {
                        str1 += str;
                    }
                    unsortMap.put(intCodeMaster.get(i).getLoanPeriodMonths(), str1);//intCodeMaster.get(i).getEndSlabAmt());
                }
                Collections.sort(list1);
                int lowestPeriod = Integer.parseInt(list1.get(0).toString());
                System.out.println("Value :-" + list1.get(0));
                if (Integer.parseInt(loanPeriodMonths) < lowestPeriod) {
                    setBgSlabAmt("0");
                    setMsg("Loan Period can not be less than loan period added in grid !");
                } else {
                    Map<Integer, String> treeMap = new TreeMap<Integer, String>(unsortMap);
                    for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                        if (entry.getKey() == Integer.parseInt(loanPeriodMonths)) {
                            setBgSlabAmt(entry.getValue());
                            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                            break;
                        } else {
                            setBgSlabAmt("0");
                        }
                    }
                }
            }
        } else {
            if (!intCodeMasterTmp.isEmpty()) {
                List list1 = new ArrayList();
                Map<Integer, String> unsortMap = new HashMap<Integer, String>();
                for (int i = 0; i < intCodeMasterTmp.size(); i++) {
                    list1.add(intCodeMasterTmp.get(i).getLoanPeriodMonths());
                    String str = intCodeMasterTmp.get(i).getBgSlabAmt();
                    str = str.substring(str.indexOf("."), str.length());
                    String str1 = intCodeMasterTmp.get(i).getEndSlabAmt();
                    str1 = str1.substring(0, str1.indexOf("."));
                    if (str.equals(".00")) {
                        str1 += ".01";
                    } else {
                        str1 += str;
                    }
                    unsortMap.put(intCodeMasterTmp.get(i).getLoanPeriodMonths(), str1);//intCodeMaster.get(i).getEndSlabAmt());
                }
                Collections.sort(list1);
                int lowestPeriod = Integer.parseInt(list1.get(0).toString());
                System.out.println("Value :-" + list1.get(0));
                if (Integer.parseInt(loanPeriodMonths) < lowestPeriod) {
                    setBgSlabAmt("0");
                    setMsg("Loan Period can not be less than loan period added in grid !");
                } else {
                    Map<Integer, String> treeMap = new TreeMap<Integer, String>(unsortMap);
                    for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                        if (entry.getKey() == Integer.parseInt(loanPeriodMonths)) {
                            setBgSlabAmt(entry.getValue());
                            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                            break;
                        } else {
                            setBgSlabAmt("0");
                            bgSlabFlag = false;
                        }
                    }
                }
                setBgSlabAmtDisable(bgSlabFlag);
            }
        }
    }

    public void validation2() {
        boolean bgSlabFlag = true;
        if (this.loanPeriodDays.indexOf(".") > -1) {
            setMsg("Sorry, Only numeric is value allowed !");
            return;
        } else {
            setMsg("");
        }
        if (Integer.parseInt(this.loanPeriodDays) > 0) {
            this.loanPeriodMonths = "0";
        }
        if (intCodeMasterTmp.isEmpty()) {
            setBgSlabAmt("0");
            if (!intCodeMaster.isEmpty()) {
                List list1 = new ArrayList();
                Map<Integer, String> unsortMap = new HashMap<Integer, String>();
                for (int i = 0; i < intCodeMaster.size(); i++) {
                    list1.add(intCodeMaster.get(i).getLoanPeriodDays());
                    String str = intCodeMaster.get(i).getBgSlabAmt();
                    str = str.substring(str.indexOf("."), str.length());
                    String str1 = intCodeMaster.get(i).getEndSlabAmt();
                    str1 = str1.substring(0, str1.indexOf("."));
                    if (str.equals(".00")) {
                        str1 += ".01";
                    } else {
                        str1 += str;
                    }
                    unsortMap.put(intCodeMaster.get(i).getLoanPeriodDays(), str1);
                }
                Collections.sort(list1);
                int lowestPeriod = Integer.parseInt(list1.get(0).toString());
                System.out.println("Value :-" + list1.get(0));
                if (Integer.parseInt(loanPeriodDays) < lowestPeriod) {
                    setBgSlabAmt("0");
                    setMsg("Loan Period can not be less than loan period added in grid !");
                } else {
                    Map<Integer, String> treeMap = new TreeMap<Integer, String>(unsortMap);
                    for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                        if (entry.getKey() == Integer.parseInt(loanPeriodDays)) {
                            setBgSlabAmt(entry.getValue());
                            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                            break;
                        } else {
                            setBgSlabAmt("0");
                            bgSlabFlag = false;
                        }
                    }
                }
                setBgSlabAmtDisable(bgSlabFlag);
            }
        } else {
            if (!intCodeMasterTmp.isEmpty()) {
                List list1 = new ArrayList();
                Map<Integer, String> unsortMap = new HashMap<Integer, String>();
                for (int i = 0; i < intCodeMasterTmp.size(); i++) {
                    list1.add(intCodeMasterTmp.get(i).getLoanPeriodDays());
                    String str = intCodeMasterTmp.get(i).getBgSlabAmt();
                    str = str.substring(str.indexOf("."), str.length());
                    String str1 = intCodeMasterTmp.get(i).getEndSlabAmt();
                    str1 = str1.substring(0, str1.indexOf("."));
                    if (str.equals(".00")) {
                        str1 += ".01";
                    } else {
                        str1 += str;
                    }
                    unsortMap.put(intCodeMasterTmp.get(i).getLoanPeriodDays(), str1);
                }
                Collections.sort(list1);
                int lowestPeriod = Integer.parseInt(list1.get(0).toString());
                System.out.println("Value :-" + list1.get(0));
                if (Integer.parseInt(loanPeriodDays) < lowestPeriod) {
                    setBgSlabAmt("0");
                    setMsg("Loan Period can not be less than loan period added in grid !");
                } else {
                    Map<Integer, String> treeMap = new TreeMap<Integer, String>(unsortMap);
                    for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                        if (entry.getKey() == Integer.parseInt(loanPeriodDays)) {
                            setBgSlabAmt(entry.getValue());
                            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                            break;
                        } else {
                            setBgSlabAmt("0");
                            bgSlabFlag = false;
                        }
                    }
                }
                setBgSlabAmtDisable(bgSlabFlag);
            }
        }

    }

    public void slabDisable(boolean flag) {
        this.normalIntIndicatorDisable = flag;
        this.peenalIntIndicatorDisable = flag;
        this.cleanPortionIndicatorDisable = flag;
        this.qisPortionIndicatorDisable = flag;
        this.additionalInterestIndicatorDisable = flag;
        this.intSlabDcFlagDisable = flag;
        this.interestSlabDescDisable = flag;
        this.interestSlabVesionRemarkDisable = flag;
        this.recordStatusDisable = flag;
        setLoanDaysDisable(flag);
        setLoanMonthDisable(flag);
        setBgSlabAmtDisable(flag);
        setEndSlabAmtDisable(flag);
    }

    public void validation3() {
    }
}
