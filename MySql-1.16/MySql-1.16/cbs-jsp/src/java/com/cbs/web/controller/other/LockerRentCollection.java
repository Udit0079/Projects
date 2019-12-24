/*
 * CREATED BY      :   ROHIT KRISHNA GUPTA
 * CREATION DATE   :   22 SEP 2010
 */
package com.cbs.web.controller.other;

import com.cbs.dto.LockerRentCalTable;
import com.cbs.web.pojo.other.LockerRentCollectionGrid;
import com.cbs.dto.LockerRentDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.util.ArrayList;
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
 * @author Admin
 */
public class LockerRentCollection extends BaseBean {

    private String errorMessage;
    private String message;
    private String postOption;
    private String crAcct;
    private String lockerNo;
    private String lblLockerNo;
    private boolean flag;
    private List<SelectItem> postOptionList;
    int currentRow;
    private boolean reportFlag;
    LockerRentCollectionGrid currentItem;
    private List<LockerRentCollectionGrid> gridDetail;
    private boolean allPostflag;
    private final String jndiHomeName = "LockerManagementFacade";
    private LockerManagementFacadeRemote beanRemote = null;
    private String viewID = "/pages/master/sm/test.jsp";

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getPostOptionList() {
        return postOptionList;
    }

    public void setPostOptionList(List<SelectItem> postOptionList) {
        this.postOptionList = postOptionList;
    }

    public String getPostOption() {
        return postOption;
    }

    public void setPostOption(String postOption) {
        this.postOption = postOption;
    }

    public String getCrAcct() {
        return crAcct;
    }

    public void setCrAcct(String crAcct) {
        this.crAcct = crAcct;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getLblLockerNo() {
        return lblLockerNo;
    }

    public void setLblLockerNo(String lblLockerNo) {
        this.lblLockerNo = lblLockerNo;
    }

    public List<LockerRentCollectionGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<LockerRentCollectionGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public LockerRentCollectionGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LockerRentCollectionGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isAllPostflag() {
        return allPostflag;
    }

    public void setAllPostflag(boolean allPostflag) {
        this.allPostflag = allPostflag;
    }

    /**
     * Creates a new instance of LockerRentCollection
     */
    public LockerRentCollection() {
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag(true);
            this.setAllPostflag(false);

            postOptionList = new ArrayList<SelectItem>();
            postOptionList.add(new SelectItem("--Select--"));
            postOptionList.add(new SelectItem("ALL LOCKERS"));
            postOptionList.add(new SelectItem("SINGLE LOCKER"));
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void setGlHead() {
        this.setErrorMessage("");
        this.setMessage("");
        gridDetail = new ArrayList<LockerRentCollectionGrid>();
        if (this.postOption.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please select post option.");
            this.setCrAcct("");
            gridDetail = new ArrayList<LockerRentCollectionGrid>();
            return;
        }
        if (this.postOption.equalsIgnoreCase("SINGLE LOCKER")) {
            this.setLblLockerNo("Enter Locker No. :");
            gridDetail = new ArrayList<LockerRentCollectionGrid>();
            this.setFlag(true);
        } else {
            this.setLblLockerNo("");
            this.setLockerNo("");
            gridDetail = new ArrayList<LockerRentCollectionGrid>();
            try {
                List resultList = new ArrayList();
                resultList = beanRemote.gridLoadForAllAccounts(getOrgnBrCode());
                if (resultList.isEmpty()) {
                    this.setErrorMessage("No rents due for the next fifteen days");
                    gridDetail = new ArrayList<LockerRentCollectionGrid>();
                    this.setFlag(true);
                    return;
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        LockerRentCollectionGrid detail = new LockerRentCollectionGrid();
                        detail.setCabNo(ele.get(0).toString());
                        detail.setLockerTy(ele.get(1).toString());
                        detail.setLockerNo(ele.get(2).toString());

                        detail.setActNo(ele.get(3).toString());
                        detail.setCustName(ele.get(4).toString());
                        detail.setRent(Double.parseDouble(ele.get(5).toString()));

                        detail.setRentDueDt(ele.get(6).toString());
                        detail.setStatus(ele.get(7).toString());
                        detail.setBrCode(ele.get(8).toString());
                        detail.setCustState(ele.get(9).toString());
                        detail.setBrState(ele.get(10).toString());
                        detail.setApply("N");
                        detail.setAdvPayYr(ele.get(11).toString());
                        gridDetail.add(detail);
                        this.setFlag(false);
                    }
                }
            } catch (ApplicationException e) {
                this.setErrorMessage(e.getMessage());
            } catch (Exception e) {
                this.setErrorMessage(e.getLocalizedMessage());
            }
        }
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.glHead(getOrgnBrCode());
            if (resultList.isEmpty()) {
                this.setErrorMessage("Please enter GLHead for locker rent");
                return;
            } else {
                Vector ele = (Vector) resultList.get(0);
                this.setCrAcct(ele.get(1).toString().substring(2, 10) + "-->" + ele.get(0).toString());
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void lockerNoLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            gridDetail = new ArrayList<LockerRentCollectionGrid>();
            if (this.postOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select post option.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                this.setErrorMessage("Please enter locker no.");
                return;
            }
            Matcher lockerNoCheck = p.matcher(lockerNo);
            if (!lockerNoCheck.matches()) {
                this.setErrorMessage("Please enter valid locker no.");
                return;
            }
            String result = beanRemote.lockerNoCheck(getOrgnBrCode(), Float.parseFloat(this.lockerNo));
            if (result == null) {
                gridDetail = new ArrayList<LockerRentCollectionGrid>();
                try {
                    List resultList = new ArrayList();
                    resultList = beanRemote.gridLoadForSingleAccount(getOrgnBrCode(), Float.parseFloat(this.lockerNo));
                    if (resultList.isEmpty()) {
                        this.setErrorMessage("No rents due for the next fifteen days");
                        gridDetail = new ArrayList<LockerRentCollectionGrid>();
                        this.setFlag(true);
                        return;
                    } else {
                        for (int i = 0; i < resultList.size(); i++) {
                            Vector ele = (Vector) resultList.get(i);
                            LockerRentCollectionGrid detail = new LockerRentCollectionGrid();
                            detail.setCabNo(ele.get(0).toString());
                            detail.setLockerTy(ele.get(1).toString());
                            detail.setLockerNo(ele.get(2).toString());

                            detail.setActNo(ele.get(3).toString());
                            detail.setCustName(ele.get(4).toString());
                            detail.setRent(Double.parseDouble(ele.get(5).toString()));

                            detail.setRentDueDt(ele.get(6).toString());
                            detail.setStatus(ele.get(7).toString());
                            detail.setBrCode(ele.get(8).toString());
                            detail.setApply("N");
                            detail.setCustState(ele.get(9).toString());
                            detail.setBrState(ele.get(10).toString());
                            detail.setAdvPayYr(ele.get(11).toString());
                            gridDetail.add(detail);
                            this.setFlag(false);
                        }
                    }
                } catch (ApplicationException e) {
                    this.setErrorMessage(e.getMessage());
                } catch (Exception e) {
                    this.setErrorMessage(e.getLocalizedMessage());
                }
            } else {
                this.setErrorMessage(result);
                gridDetail = new ArrayList<LockerRentCollectionGrid>();
                this.setFlag(true);
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void changeApply() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag(false);
            LockerRentCollectionGrid item = gridDetail.get(currentRow);
            if (item.getApply().equalsIgnoreCase("N")) {
                boolean found1 = false;
                for (LockerRentCollectionGrid item1 : gridDetail) {
                    if (item1.getApply().equalsIgnoreCase("Y")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setErrorMessage("Only one locker rent can be post at one time.");
                } else {
                    item.setApply("Y");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                }
            } else if (item.getApply().equalsIgnoreCase("Y")) {
                item.setApply("N");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    List arraylist = new ArrayList();

    public void postRent() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag(true);
        this.setAllPostflag(false);
        try {
            if (this.postOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select post option.");
                return;
            }
            if (this.postOption.equalsIgnoreCase("SINGLE LOCKER")) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                    this.setErrorMessage("Please enter locker no.");
                    return;
                }
                Matcher lockerNoCheck = p.matcher(lockerNo);
                if (!lockerNoCheck.matches()) {
                    this.setErrorMessage("Please enter valid locker no.");
                    return;
                }
            }
            List<LockerRentDetail> lockerRentDetailList = new ArrayList<LockerRentDetail>();
            if (postOption.equalsIgnoreCase("SINGLE LOCKER")) {
                for (LockerRentCollectionGrid item : gridDetail) {
                    if (item.getApply().equalsIgnoreCase("Y")) {
                        lockerRentDetailList.add(setLocketRentDetail(item));
                    }
                }
                if (lockerRentDetailList.isEmpty()) {
                    this.setErrorMessage("Please select at least one row for posting");
                    return;
                } else {
                    String result = beanRemote.lockerRentPosting(lockerRentDetailList, getOrgnBrCode() + this.crAcct.substring(0, 8) + "01",
                            this.postOption, getUserName(), getOrgnBrCode());
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        this.setErrorMessage(result);
                    } else {
                        this.setMessage("Transaction posted successfully, Transaction Batch No. :-" + result.substring(4));
                    }
                }
            } else {
                for (LockerRentCollectionGrid item : gridDetail) {
                    lockerRentDetailList.add(setLocketRentDetail(item));
                }
                String result = beanRemote.lockerRentPosting(lockerRentDetailList, getOrgnBrCode() + this.crAcct.substring(0, 8) + "01",
                        this.postOption, getUserName(), getOrgnBrCode());
                if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                    this.setErrorMessage(result);
                } else {
                    this.setAllPostflag(true);
                    this.setMessage("Transaction posted successfully, Transaction Batch No. :-" + result.substring(result.indexOf(":") + 1));
                }
            }
            gridDetail = new ArrayList<LockerRentCollectionGrid>();
            arraylist = new ArrayList();
            this.setPostOption("--Select--");
            this.setCrAcct("");
            this.setLblLockerNo("");
            this.setLockerNo("");
            this.setFlag(true);

        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    private LockerRentDetail setLocketRentDetail(LockerRentCollectionGrid item) {
        LockerRentDetail lockerRentDetail = new LockerRentDetail();
        try {
            lockerRentDetail.setAcno(item.getActNo());
            lockerRentDetail.setCustname(item.getCustName());
            lockerRentDetail.setCabno(Float.parseFloat(item.getCabNo()));

            lockerRentDetail.setLockerno(Float.parseFloat(item.getLockerNo()));
            lockerRentDetail.setLockertype(item.getLockerTy());
            lockerRentDetail.setPenalty(item.getRent());
            lockerRentDetail.setRentduedt(item.getRentDueDt());
            lockerRentDetail.setBrCode(item.getBrCode());
            lockerRentDetail.setStatus(item.getStatus());
            lockerRentDetail.setCustState(item.getCustState());
            lockerRentDetail.setBrnchState(item.getBrState());
            lockerRentDetail.setAdvPayYr(item.getAdvPayYr());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
        return lockerRentDetail;
    }

    public void resetForm() {
        try {
            reportFlag = false;
            this.setErrorMessage("");
            this.setMessage("");
            this.setPostOption("--Select--");
            this.setCrAcct("");
            this.setLblLockerNo("");
            this.setLockerNo("");
            gridDetail = new ArrayList<LockerRentCollectionGrid>();
            arraylist = new ArrayList();
            this.setFlag(true);
            this.setAllPostflag(false);
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void showReport() {
        try {
            if (lockerNo.equalsIgnoreCase("")) {
                lockerNo = "0";
            }
            List<LockerRentCalTable> lockerRentCalculationReport = beanRemote.lockerRentCalculationReport(postOption, Float.parseFloat(lockerNo), getOrgnBrCode());
            if (!lockerRentCalculationReport.isEmpty()) {
                reportFlag = true;
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List bankList = common.getBranchNameandAddress(getOrgnBrCode());
                String report = "LOCKER RENT DUE REPORT";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pBankName", bankList.get(0));
                fillParams.put("pBankAdd", bankList.get(1));
                new ReportBean().jasperReport("LOCKER_RENT_REPORT", "text/html", new JRBeanCollectionDataSource(lockerRentCalculationReport), fillParams, report);
                this.setViewID("/report/ReportPagePopUp.jsp");
            } else {
                setMessage("No data exists !");
                reportFlag = false;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }
}
