/*
 * Created By : ROHIT KRISHNA GUPTA   LOCKER AC MASTER
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.web.pojo.other.LockerAccountMasterGrid;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class LockerAccountMaster extends BaseBean {

    private String errorMessage;
    private String message;
    private String cabNo;
    private String lockerType;
    private String lockerNo;
    private String address;
    private String custCat;
    private String rent;
    private Date rentDueDt;
    private String acctNo;
    private String custName;
    private String secDeposite;
    private String keyNo;
    private String mode;
    private String nomination;
    private String adOpr1;
    private String adOpr2;
    private String adOpr3;
    private String adOpr4;
    private String remark;
    private boolean lockNoFlag;
    private boolean gridFlag;
    private boolean custCatFlag;
    private boolean rentDtFlag;
    private int currentRow;
    private LockerAccountMasterGrid currentItem = new LockerAccountMasterGrid();
    private List<SelectItem> cabNoList;
    private List<SelectItem> modeList;
    private List<SelectItem> custCatList;
    private List<SelectItem> lockerTypeList;
    private String noOfYear;
    private List<SelectItem> noOfYearList;
    private List<LockerAccountMasterGrid> gridDetail;
    private LockerManagementFacadeRemote beanRemote = null;
    private final String jndiHomeName = "LockerManagementFacade";
    private String newAcno, acNoLen;
    private NumberFormat formatter = new DecimalFormat("#0.00");
    private boolean disableAcntNo;
    private CommonReportMethodsRemote common;

    public boolean isDisableAcntNo() {
        return disableAcntNo;
    }

    public void setDisableAcntNo(boolean disableAcntNo) {
        this.disableAcntNo = disableAcntNo;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public List<SelectItem> getCabNoList() {
        return cabNoList;
    }

    public void setCabNoList(List<SelectItem> cabNoList) {
        this.cabNoList = cabNoList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustCat() {
        return custCat;
    }

    public void setCustCat(String custCat) {
        this.custCat = custCat;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public List<SelectItem> getLockerTypeList() {
        return lockerTypeList;
    }

    public void setLockerTypeList(List<SelectItem> lockerTypeList) {
        this.lockerTypeList = lockerTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRentDueDt() {
        return rentDueDt;
    }

    public void setRentDueDt(Date rentDueDt) {
        this.rentDueDt = rentDueDt;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getSecDeposite() {
        return secDeposite;
    }

    public void setSecDeposite(String secDeposite) {
        this.secDeposite = secDeposite;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getAdOpr1() {
        return adOpr1;
    }

    public void setAdOpr1(String adOpr1) {
        this.adOpr1 = adOpr1;
    }

    public String getAdOpr2() {
        return adOpr2;
    }

    public void setAdOpr2(String adOpr2) {
        this.adOpr2 = adOpr2;
    }

    public String getAdOpr3() {
        return adOpr3;
    }

    public void setAdOpr3(String adOpr3) {
        this.adOpr3 = adOpr3;
    }

    public String getAdOpr4() {
        return adOpr4;
    }

    public void setAdOpr4(String adOpr4) {
        this.adOpr4 = adOpr4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<LockerAccountMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<LockerAccountMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public List<SelectItem> getCustCatList() {
        return custCatList;
    }

    public void setCustCatList(List<SelectItem> custCatList) {
        this.custCatList = custCatList;
    }

    public LockerAccountMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LockerAccountMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isGridFlag() {
        return gridFlag;
    }

    public void setGridFlag(boolean gridFlag) {
        this.gridFlag = gridFlag;
    }

    public boolean isLockNoFlag() {
        return lockNoFlag;
    }

    public void setLockNoFlag(boolean lockNoFlag) {
        this.lockNoFlag = lockNoFlag;
    }

    public boolean isCustCatFlag() {
        return custCatFlag;
    }

    public void setCustCatFlag(boolean custCatFlag) {
        this.custCatFlag = custCatFlag;
    }

    public boolean isRentDtFlag() {
        return rentDtFlag;
    }

    public void setRentDtFlag(boolean rentDtFlag) {
        this.rentDtFlag = rentDtFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNoOfYear() {
        return noOfYear;
    }

    public void setNoOfYear(String noOfYear) {
        this.noOfYear = noOfYear;
    }

    public List<SelectItem> getNoOfYearList() {
        return noOfYearList;
    }

    public void setNoOfYearList(List<SelectItem> noOfYearList) {
        this.noOfYearList = noOfYearList;
    }

    /**
     * Creates a new instance of LockerAccountMaster
     */
    public LockerAccountMaster() {
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.setRentDueDt(date);
            this.setErrorMessage("");
            this.setMessage("");
            lockNoFlag = false;
            gridFlag = false;
            this.setCustCatFlag(false);
            this.setGridFlag(false);
            this.setRentDtFlag(false);
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem("--Select--"));
            noOfYearList = new ArrayList<>();
            List advPayYearList = common.getTdsDoctype("551");
            noOfYearList.add(new SelectItem("--Select--"));
            for (int i = 0; i < advPayYearList.size(); i++) {
                Vector vtr = (Vector) advPayYearList.get(i);
                noOfYearList.add(new SelectItem(vtr.get(0).toString()));
            }
            custCatList = new ArrayList<SelectItem>();
            custCatList.add(new SelectItem("--Select--"));
            custCatList.add(new SelectItem("CUSTOMER"));
            custCatList.add(new SelectItem("STAFF"));
            custCatList.add(new SelectItem("SPECIAL"));
            cabNoCombo();
            modeCombo();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void cabNoCombo() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.cabNo(getOrgnBrCode());
            cabNoList = new ArrayList<SelectItem>();
            cabNoList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    cabNoList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void modeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.modeCombo(getOrgnBrCode());
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    modeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void lockerTypeComboLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setLockerNo("");
            this.setKeyNo("");
            this.setAcctNo("");
            this.setCustName("");
            this.setAddress("");
            this.setRent("");
            this.setSecDeposite("");
            Date date = new Date();
            this.setRentDueDt(date);
            this.setNomination("");
            this.setAdOpr1("");
            this.setAdOpr2("");
            this.setAdOpr3("");
            this.setAdOpr4("");
            this.setRemark("");
            this.setCustCat("--Select--");
            this.setMode("--Select--");
            this.setRentDtFlag(false);
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no.");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.lockerTypeComboOnChange(getOrgnBrCode(), this.cabNo, this.lockerType);
            if (!resultList.isEmpty() || !resultList.toString().equalsIgnoreCase("[[null]]")) {
                Vector ele = (Vector) resultList.get(0);
                String val = ele.get(0).toString();
                if (val.equalsIgnoreCase("True")) {
                    gridLoad();
                    this.setErrorMessage("This locker type Is already filled up in the selected cabinet");
                    return;
                } else {
                    this.setLockerNo(ele.get(0).toString());
                    gridLoad();
                }
            } else {
                this.setErrorMessage("No locker is available for this account type");
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void lockerTypeCombo() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setLockerNo("");
            this.setKeyNo("");
            this.setAcctNo("");
            this.setCustName("");
            this.setAddress("");
            this.setRent("");
            this.setSecDeposite("");
            Date date = new Date();
            this.setRentDueDt(date);
            this.setNomination("");
            this.setAdOpr1("");
            this.setAdOpr2("");
            this.setAdOpr3("");
            this.setAdOpr4("");
            this.setRemark("");
            this.setCustCat("--Select--");
            this.setMode("--Select--");
            this.setRentDtFlag(false);
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.lockerType(getOrgnBrCode(), this.cabNo);
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    lockerTypeList.add(new SelectItem(ee.toString()));
                }
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
        this.setRentDtFlag(false);
        try {
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no.");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type.");
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
            this.setAcctNo("");
            this.setCustName("");
            this.setAddress("");
            this.setCustCat("--Select--");
            this.setRent("");
            this.setSecDeposite("");
            Date date = new Date();
            this.setRentDueDt(date);
            this.setMode("--Select--");
            this.setNomination("");
            this.setAdOpr1("");
            this.setAdOpr2("");
            this.setAdOpr3("");
            this.setAdOpr4("");
            this.setRemark("");
            List resultList = new ArrayList();

            resultList = beanRemote.lockerNoLostFocus(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType);
            if (!resultList.isEmpty()) {
                if (resultList.toString().contains(",")) {
                    lockNoFlag = false;
                    gridFlag = false;
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector recLst = (Vector) resultList.get(i);
                        this.setRent(String.valueOf(formatter.format(new BigDecimal(recLst.get(11).toString()).doubleValue())));
                        //this.setRent(recLst.get(11).toString());
                        this.setCustName(recLst.get(15).toString());
                        this.setAcctNo(recLst.get(13).toString());
                        this.setNewAcno(recLst.get(13).toString());
                        this.setCustCat(recLst.get(1).toString());
                        this.setMode(recLst.get(2).toString());
                        this.setKeyNo(recLst.get(8).toString());
                        this.setNomination(recLst.get(12).toString());
                        this.setAddress(recLst.get(17).toString() + " Phone No:-" + recLst.get(18).toString());
                        this.setAdOpr1(recLst.get(3).toString());
                        this.setAdOpr2(recLst.get(4).toString());
                        this.setAdOpr3(recLst.get(5).toString());
                        this.setAdOpr4(recLst.get(6).toString());
                        this.setSecDeposite(recLst.get(7).toString());
                        this.setRemark(recLst.get(14).toString());
                        List resultList1 = new ArrayList();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        resultList1 = beanRemote.rentDueDt(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType);
                        if (!resultList1.isEmpty()) {
                            Vector ele = (Vector) resultList1.get(0);
                            Date orgnDt = sdf.parse(ele.get(0).toString());
                            this.setRentDueDt(orgnDt);
                        }
                        //gridFlag=false;
                    }
                    disableAcntNo = true;
                    this.setCustCatFlag(true);
                } else {
                    lockNoFlag = false;
                    gridFlag = false;
                    Vector recLst = (Vector) resultList.get(0);
                    this.setKeyNo(recLst.get(0).toString());
                    this.setCustCatFlag(false);
                }
            } else {
                this.setCustCatFlag(false);
                this.setErrorMessage("The entered locker no does not exist.");
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        this.setRentDtFlag(false);
        gridDetail = new ArrayList<LockerAccountMasterGrid>();
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.gridload(getOrgnBrCode(), this.cabNo, this.lockerType);
            if (!resultList.isEmpty()) {
                System.out.println("Grid Size = " + resultList.size());
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LockerAccountMasterGrid detail = new LockerAccountMasterGrid();
                    detail.setCabNo(ele.get(0).toString());
                    detail.setLockerTy(ele.get(1).toString());
                    detail.setLockerNo(ele.get(2).toString());
                    detail.setKeyNo(ele.get(3).toString());
                    detail.setAcctNo(ele.get(4).toString());
                    detail.setCustCat(ele.get(5).toString());
                    detail.setSecurity(Double.parseDouble(ele.get(6).toString()));
                    detail.setNomination(ele.get(7).toString());
                    detail.setMode(ele.get(8).toString());
                    detail.setAuth(ele.get(9).toString());
                    detail.setRemarks(ele.get(10).toString());
                    detail.setRent(Double.parseDouble(ele.get(11).toString()));
                    detail.setAdvPayYr(ele.get(12).toString());
                    gridDetail.add(detail);
                }
                System.out.println("Grid Size = " + gridDetail.size());
            } else {
                return;
            }

        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void custCatLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        //this.setRentDtFlag(false);
        try {
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type.");
                return;
            }
            if (this.custCat.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select customer type.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.custCatLostFocus(getOrgnBrCode(), this.custCat, this.lockerType);
            if (resultList.isEmpty()) {
                this.setErrorMessage("Please enter associated rent for customer category.");
                this.setRent("");
                this.setCustCatFlag(false);
                return;
            } else {

                Vector ele = (Vector) resultList.get(0);
                this.setRent(String.valueOf(formatter.format(new BigDecimal(ele.get(0).toString()).doubleValue() * Double.parseDouble(noOfYear))));
                //this.setRent(ele.get(0).toString());
                this.setCustCatFlag(true);
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void secDepositeLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setRentDtFlag(false);
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.secDeposite.equalsIgnoreCase("") || this.secDeposite.length() == 0) {
                this.setErrorMessage("Please enter the security deposit amount.");
                return;
            }
            Matcher secDepCheck = p.matcher(secDeposite);
            if (!secDepCheck.matches()) {
                this.setErrorMessage("Please enter valid  security deposit amount.");
                return;
            }
            if (Float.parseFloat(secDeposite) < 0) {
                this.setErrorMessage("Security deposit amount cannot be less than zero.");
                return;
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setRentDtFlag(false);
        newAcno = "";
        setCustName("");
        setAddress("");
        try {
            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumber(acctNo);
            if (result.startsWith("A/c")) {
                resetForm();
                setErrorMessage(result);
                return;
            } else {
                newAcno = result;
            }
            /**
             * ********** added end ************************
             */
            List resultList = new ArrayList();
            resultList = beanRemote.acNoLostFocus(getOrgnBrCode(), newAcno);
            if (resultList.isEmpty()) {
                this.setErrorMessage("Account no. does not exist.");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCustName(ele.get(1).toString());
                    this.setAddress(ele.get(2).toString() + " Phone No.:-" + ele.get(3).toString());
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        lockNoFlag = false;
        gridFlag = false;
        this.setRentDtFlag(false);
        disableAcntNo = true;
        try {
            this.setCabNo(currentItem.getCabNo());
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem(currentItem.getLockerTy()));
            this.setLockerNo(currentItem.getLockerNo());
            this.setKeyNo(currentItem.getKeyNo());
            this.setAcctNo(currentItem.getAcctNo());
            this.setNewAcno(currentItem.getAcctNo());
            //this.setSecDeposite(String.valueOf(currentItem.getSecurity()));
            this.setSecDeposite(formatter.format(currentItem.getSecurity()));
            List resultList = new ArrayList();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            resultList = beanRemote.rentDueDt(getOrgnBrCode(), currentItem.getCabNo(), currentItem.getLockerNo(), currentItem.getLockerTy());
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                Date orgnDt = sdf.parse(ele.get(0).toString());
                this.setRentDueDt(orgnDt);
            }
            List resultList1 = new ArrayList();
            resultList1 = beanRemote.setCustNameAndAdd(getOrgnBrCode(), currentItem.getAcctNo());
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    this.setCustName(ele.get(0).toString());
                    this.setAddress(ele.get(1).toString() + " Phone No:-" + ele.get(2).toString());
                }
            }
            this.setCustCat(currentItem.getCustCat());
            this.setMode(currentItem.getMode());
            this.setNomination(currentItem.getNomination());
            this.setRemark(currentItem.getRemarks());
            this.setNoOfYear(currentItem.getAdvPayYr());

            //if (currentItem.getMode().equalsIgnoreCase("SELF")) {
            List resultList2 = new ArrayList();
            resultList2 = beanRemote.setRent(getOrgnBrCode(), currentItem.getCabNo(), currentItem.getLockerTy(), currentItem.getLockerNo());
            if (!resultList2.isEmpty()) {
                Vector ele = (Vector) resultList2.get(0);
                //this.setRent(ele.get(0).toString());
                this.setRent(String.valueOf(formatter.format(new BigDecimal(ele.get(0).toString()).doubleValue())));
            }
            //} else {
            List resultList3 = new ArrayList();
            resultList3 = beanRemote.setAdOperators(getOrgnBrCode(), currentItem.getCabNo(), currentItem.getLockerTy(), currentItem.getLockerNo());
            if (!resultList3.isEmpty()) {
                for (int i = 0; i < resultList3.size(); i++) {
                    Vector ele = (Vector) resultList3.get(i);
                    //this.setRent(ele.get(0).toString());
                    this.setRent(String.valueOf(formatter.format(new BigDecimal(ele.get(0).toString()).doubleValue())));
                    this.setAdOpr1(ele.get(1).toString());
                    this.setAdOpr2(ele.get(2).toString());
                    this.setAdOpr3(ele.get(3).toString());
                    this.setAdOpr4(ele.get(4).toString());
                }
            }
            // }
            gridFlag = true;
            this.setCustCatFlag(true);
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCabNo("--Select--");
            this.setLockerType("--Select--");
            this.setLockerNo("");
            this.setKeyNo("");
            this.setAcctNo("");
            this.setCustName("");
            this.setAddress("");
            this.setCustCat("--Select--");
            this.setRent("");
            this.setSecDeposite("");
            Date date = new Date();
            this.setRentDueDt(date);
            this.setMode("--Select--");
            this.setNomination("");
            this.setAdOpr1("");
            this.setAdOpr2("");
            this.setAdOpr3("");
            this.setAdOpr4("");
            this.setRemark("");
            this.setNoOfYear("--Select--");
            setNewAcno("");
            lockNoFlag = false;
            gridFlag = false;
            this.setCustCatFlag(false);
            this.setRentDtFlag(false);
            disableAcntNo = false;
            gridDetail = new ArrayList<LockerAccountMasterGrid>();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void saveBtn() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no.");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                this.setErrorMessage("Please enter locker no.");
                return;
            }
            if (this.keyNo.equalsIgnoreCase("") || this.keyNo.length() == 0) {
                this.setErrorMessage("Please enter key no.");
                return;
            }
            Matcher lockerNoCheck = p.matcher(lockerNo);
            if (!lockerNoCheck.matches()) {
                this.setErrorMessage("Please enter valid locker no.");
                return;
            }
            if (this.lockerNo.contains(".")) {
                this.setErrorMessage("Please enter valid locker no.");
                return;
            }
            Matcher keyCheck = p.matcher(keyNo);
            if (!keyCheck.matches()) {
                this.setErrorMessage("Please enter valid key no.");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("")) {
                this.setErrorMessage("Error!!! Account no. cannot be blank");
                return;
            }

            if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("Error!!! Account no. should be proper");
                return;
            }

            if (this.noOfYear.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select Adv.Payment(No Of Year).");
                return;
            }

            if (this.custCat.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select customer type.");
                return;
            }

            if (this.rent.equalsIgnoreCase("") || this.rent.length() == 0) {
                this.setErrorMessage("Please enter the associated rent.");
                return;
            }
            Matcher rentCheck = p.matcher(rent);
            if (!rentCheck.matches()) {
                this.setErrorMessage("Please enter valid associated rent.");
                return;
            }
            if (this.rent.contains(".")) {
                if (this.rent.indexOf(".") != this.rent.lastIndexOf(".")) {
                    this.setErrorMessage("Please enter valid associated rent.");
                    return;
                } else if (this.rent.substring(this.rent.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill the associated rent upto two decimal places.");
                    return;
                }
            }
            if (Float.parseFloat(rent) < 0) {
                this.setErrorMessage("Associated rent cannot be less than zero.");
                return;
            }

            if (this.secDeposite.equalsIgnoreCase("") || this.secDeposite.length() == 0) {
                this.setErrorMessage("Please enter the security deposit amount.");
                return;
            }
            Matcher secDepCheck = p.matcher(secDeposite);
            if (!secDepCheck.matches()) {
                this.setErrorMessage("Please enter valid security deposit amount.");
                return;
            }
            if (this.secDeposite.contains(".")) {
                if (this.secDeposite.indexOf(".") != this.secDeposite.lastIndexOf(".")) {
                    this.setErrorMessage("Please enter valid security deposit amount.");
                    return;
                } else if (this.secDeposite.substring(this.secDeposite.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill the security deposit amount upto two decimal places.");
                    return;
                }
            }
            if (Float.parseFloat(secDeposite) < 0) {
                this.setErrorMessage("Security deposit amount cannot be less than zero.");
                return;
            }
            if (this.mode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select mode.");
                return;
            }

            if (this.mode.equalsIgnoreCase("BOTH OF TWO JOINTLY") || this.mode.equalsIgnoreCase("ANY TWO JOINTLY")) {
                if (this.adOpr1.equalsIgnoreCase("") || this.adOpr1.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 1.");
                    return;
                }
                if (this.adOpr2.equalsIgnoreCase("") || this.adOpr2.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 2.");
                    return;
                }
            }
            if (this.mode.equalsIgnoreCase("ANY FOUR JOINTLY") || this.mode.equalsIgnoreCase("ANY FIVE JOINTLY") || this.mode.equalsIgnoreCase("ALL JOINTLY")) {
                if (this.adOpr1.equalsIgnoreCase("") || this.adOpr1.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 1.");
                    return;
                }
                if (this.adOpr2.equalsIgnoreCase("") || this.adOpr2.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 2.");
                    return;
                }
                if (this.adOpr3.equalsIgnoreCase("") || this.adOpr3.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 3.");
                    return;
                }
                if (this.adOpr4.equalsIgnoreCase("") || this.adOpr4.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 4.");
                    return;
                }
            }
            if (this.mode.equalsIgnoreCase("ANY THREE JOINTLY")) {
                if (this.adOpr1.equalsIgnoreCase("") || this.adOpr1.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 1.");
                    return;
                }
                if (this.adOpr2.equalsIgnoreCase("") || this.adOpr2.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 2.");
                    return;
                }
                if (this.adOpr3.equalsIgnoreCase("") || this.adOpr3.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 3.");
                    return;
                }
            }
            if (this.rentDueDt == null) {
                this.setErrorMessage("Please enter rent due date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = beanRemote.saveBtnIssueLocker(getOrgnBrCode(), Float.parseFloat(this.cabNo), this.lockerType, Float.parseFloat(this.lockerNo),
                    Float.parseFloat(this.keyNo), this.newAcno, Float.parseFloat(this.rent), Float.parseFloat(this.secDeposite), this.custCat, this.mode,
                    this.nomination, this.remark, getUserName(), this.adOpr1, this.adOpr2, this.adOpr3, this.adOpr4, ymd.format(this.rentDueDt), noOfYear);
            System.out.println("THE RESULT IS " + result);
            if (result.equals("Locker Issued Successfully")) {
                this.setMessage("Locker issued successfully.");
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
            this.setCabNo("--Select--");
            this.setLockerType("--Select--");
            this.setLockerNo("");
            this.setKeyNo("");
            this.setAcctNo("");
            this.setCustName("");
            this.setAddress("");
            this.setRent("");
            this.setSecDeposite("");
            Date date = new Date();
            this.setRentDueDt(date);
            this.setNomination("");
            this.setAdOpr1("");
            this.setAdOpr2("");
            this.setAdOpr3("");
            this.setAdOpr4("");
            this.setRemark("");
            setNewAcno("");
            lockNoFlag = false;
            gridFlag = false;
            this.setRentDtFlag(false);
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void updateBtn() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no.");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                this.setErrorMessage("Please enter locker no.");
                return;
            }
            if (this.keyNo.equalsIgnoreCase("") || this.keyNo.length() == 0) {
                this.setErrorMessage("Please enter key no.");
                return;
            }
            Matcher lockerNoCheck = p.matcher(lockerNo);
            if (!lockerNoCheck.matches()) {
                this.setErrorMessage("Please enter valid locker no.");
                return;
            }
            if (this.lockerNo.contains(".")) {
                this.setErrorMessage("Please enter valid locker no.");
                return;
            }
            Matcher keyCheck = p.matcher(keyNo);
            if (!keyCheck.matches()) {
                this.setErrorMessage("Please enter valid key no.");
                return;
            }
            if (this.newAcno.equalsIgnoreCase("")) {
                this.setErrorMessage("Error!!! account no. not entered");
                return;
            }
            if (this.custCat.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select customer type.");
                return;
            }
            if (this.rent.equalsIgnoreCase("") || this.rent.length() == 0) {
                this.setErrorMessage("Please enter the associated rent.");
                return;
            }
            Matcher rentCheck = p.matcher(rent);
            if (!rentCheck.matches()) {
                this.setErrorMessage("Please enter valid associated rent.");
                return;
            }
            if (this.rent.contains(".")) {
                if (this.rent.indexOf(".") != this.rent.lastIndexOf(".")) {
                    this.setErrorMessage("Please enter valid associated rent.");
                    return;
                } else if (this.rent.substring(this.rent.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill the associated rent upto two decimal places.");
                    return;
                }
            }
            if (Float.parseFloat(rent) < 0) {
                this.setErrorMessage("Associated rent cannot be less than zero.");
                return;
            }

            if (this.secDeposite.equalsIgnoreCase("") || this.secDeposite.length() == 0) {
                this.setErrorMessage("Please enter the security deposit amount.");
                return;
            }
            Matcher secDepCheck = p.matcher(secDeposite);
            if (!secDepCheck.matches()) {
                this.setErrorMessage("Please enter valid security deposit amount.");
                return;
            }
            if (this.secDeposite.contains(".")) {
                if (this.secDeposite.indexOf(".") != this.secDeposite.lastIndexOf(".")) {
                    this.setErrorMessage("Please enter valid security deposit amount.");
                    return;
                } else if (this.secDeposite.substring(this.secDeposite.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill the security deposit amount upto two decimal places.");
                    return;
                }
            }
            if (Float.parseFloat(secDeposite) < 0) {
                this.setErrorMessage("Security deposit amount cannot be less than zero.");
                return;
            }
            if (this.mode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select mode.");
                return;
            }
//        if (this.nomination.equalsIgnoreCase("") || this.nomination.length() == 0) {
//            this.setErrorMessage("Please Enter the Nomination.");
//            return;
//        }
            if (this.mode.equalsIgnoreCase("BOTH OF TWO JOINTLY") || this.mode.equalsIgnoreCase("ANY TWO JOINTLY")) {
                if (this.adOpr1.equalsIgnoreCase("") || this.adOpr1.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 1.");
                    return;
                }
                if (this.adOpr2.equalsIgnoreCase("") || this.adOpr2.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 2.");
                    return;
                }
            }
            if (this.mode.equalsIgnoreCase("ANY FOUR JOINTLY") || this.mode.equalsIgnoreCase("ANY FIVE JOINTLY") || this.mode.equalsIgnoreCase("ALL JOINTLY")) {
                if (this.adOpr1.equalsIgnoreCase("") || this.adOpr1.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 1.");
                    return;
                }
                if (this.adOpr2.equalsIgnoreCase("") || this.adOpr2.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 2.");
                    return;
                }
                if (this.adOpr3.equalsIgnoreCase("") || this.adOpr3.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 3.");
                    return;
                }
                if (this.adOpr4.equalsIgnoreCase("") || this.adOpr4.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 4.");
                    return;
                }
            }
            if (this.mode.equalsIgnoreCase("ANY THREE JOINTLY")) {
                if (this.adOpr1.equalsIgnoreCase("") || this.adOpr1.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 1.");
                    return;
                }
                if (this.adOpr2.equalsIgnoreCase("") || this.adOpr2.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 2.");
                    return;
                }
                if (this.adOpr3.equalsIgnoreCase("") || this.adOpr3.length() == 0) {
                    this.setErrorMessage("Please enter Ad operator 3.");
                    return;
                }
            }
            if (this.rentDueDt == null) {
                this.setErrorMessage("Please enter rent due date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = beanRemote.updateLockerInfo(getOrgnBrCode(), Float.parseFloat(this.cabNo), this.lockerType, Float.parseFloat(this.lockerNo),
                    Float.parseFloat(this.keyNo), newAcno, Float.parseFloat(this.rent), Float.parseFloat(this.secDeposite), this.custCat, this.mode,
                    this.nomination, this.remark, getUserName(), this.adOpr1, this.adOpr2, this.adOpr3, this.adOpr4, ymd.format(this.rentDueDt), noOfYear);
            if (result.equals("Record Updated Successfully")) {
                this.setMessage("Record updated successfully.");
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
            this.setCabNo("--Select--");
            this.setLockerType("--Select--");
            this.setLockerNo("");
            this.setKeyNo("");
            this.setAcctNo("");
            this.setCustName("");
            this.setAddress("");
            this.setRent("");
            this.setSecDeposite("");
            Date date = new Date();
            this.setRentDueDt(date);
            this.setNomination("");
            this.setAdOpr1("");
            this.setAdOpr2("");
            this.setAdOpr3("");
            this.setAdOpr4("");
            this.setRemark("");
            lockNoFlag = false;
            gridFlag = false;
            disableAcntNo = false;
            newAcno = "";
            this.setRentDtFlag(false);
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
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
}
