package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.TableBankDirectory;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public final class BankDirectory extends BaseBean {

    private String orgnBrCode = getOrgnBrCode();
    private String message;
    private String micr;
    private String bankCode;
    private String branchNo;
    private String branchName;
    private String branch;
    private String phoneNo;
    private String faxNo;
    private String weekly;
    private boolean saveDisable;
    private boolean updateDisable;
    private List<SelectItem> weeklyList;
    private List<TableBankDirectory> bankDir;
    BankAndLoanMasterFacadeRemote testEJB;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private int currentRow;
    boolean flag;

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public TableBankDirectory getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TableBankDirectory currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
    private TableBankDirectory currentItem = new TableBankDirectory();

    public List<TableBankDirectory> getBankDir() {
        return bankDir;
    }

    public void setBankDir(List<TableBankDirectory> bankDir) {
        this.bankDir = bankDir;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public List<SelectItem> getWeeklyList() {
        return weeklyList;
    }

    public void setWeeklyList(List<SelectItem> weeklyList) {
        this.weeklyList = weeklyList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public BankDirectory() {
        try {
            testEJB = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            refresh();
            getTableValues();
            setMicr("110");
            setUpdateDisable(true);
            bankDir = new ArrayList<TableBankDirectory>();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableValues() {
        try {
            weeklyList = new ArrayList<SelectItem>();
            weeklyList.add(new SelectItem("0", "--Select--"));
            weeklyList.add(new SelectItem("Sunday", "Sunday"));
            weeklyList.add(new SelectItem("Monday", "Monday"));
            weeklyList.add(new SelectItem("Tuesday", "Tuesday"));
            weeklyList.add(new SelectItem("Wednesday", "Wednesday"));
            weeklyList.add(new SelectItem("Thuresday", "Thuresday"));
            weeklyList.add(new SelectItem("Friday", "Friday"));
            weeklyList.add(new SelectItem("Saturday", "Saturday"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void securityDetailsTableValues() {
        try {
            this.setMessage("");
            bankDir = new ArrayList<TableBankDirectory>();
            if (bankCode == null || bankCode.equalsIgnoreCase("")) {
                return;
            } else {
                if (!bankCode.matches("[0-9]*")) {
                    setMessage("Please enter numeric value for Bank Code.");
                    setBankCode("");
                    return;
                }
            }
            int length1 = bankCode.length();
            int addedZero1 = 3 - length1;
            for (int i = 1; i <= addedZero1; i++) {
                bankCode = "0" + bankCode;
            }
            List resultList = testEJB.getBankDirectoryTable(micr, bankCode);
            if (resultList.size() <= 0) {
                setSaveDisable(false);
                setBranch("");
                setBranchName("");
                setBranchNo("");
                setPhoneNo("");
                setFaxNo("");
                setWeekly("0");
                bankDir = new ArrayList<TableBankDirectory>();
            } else {
                setSaveDisable(true);
                setBranch("");
                setBranchName("");
                setBranchNo("");
                setPhoneNo("");
                setFaxNo("");
                setWeekly("0");
                for (int i = 0; i < resultList.size(); i++) {
                    Vector tableVector = (Vector) resultList.get(i);
                    TableBankDirectory mt = new TableBankDirectory();
                    mt.setBankCode(tableVector.get(0).toString());
                    mt.setBranchNo(tableVector.get(1).toString());
                    mt.setBankName(tableVector.get(2).toString());
                    mt.setBranch(tableVector.get(3).toString());
                    mt.setPhone(tableVector.get(4).toString());
                    mt.setFax(tableVector.get(5).toString());
                    mt.setWeekly(tableVector.get(6).toString());
                    bankDir.add(mt);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String sNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (TableBankDirectory item : bankDir) {
                if (item.getBankCode().equals(sNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void select() {
        try {
            setMessage("");
            setBankCode(currentItem.getBankCode());
            setBranch(currentItem.getBranch());
            setBranchNo(currentItem.getBranchNo());
            setFaxNo(currentItem.getFax());
            setPhoneNo(currentItem.getPhone());
            setWeekly(currentItem.getWeekly());
            setBranchName(currentItem.getBankName());
            setSaveDisable(true);
            setUpdateDisable(false);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void update() {
        try {
            String msg;
            if (validation().equalsIgnoreCase("False")) {
                return;
            }
            msg = testEJB.updateBankDirectory(bankCode, branchNo, branchName, branch, phoneNo, faxNo, weekly);
            setUpdateDisable(true);
            setBranch("");
            setBranchName("");
            setBranchNo("");
            setPhoneNo("");
            setFaxNo("");
            setWeekly("0");
            securityDetailsTableValues();
            setMessage(msg);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void weekly() {
        try {
            setSaveDisable(false);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        if (validation().equalsIgnoreCase("False")) {
            return;
        }
        String msg;
        if (Integer.parseInt(bankCode) < 0) {
            setMessage("Please fill correct Bank Code !!.");
            return;
        }
        if (Integer.parseInt(branchNo) < 0) {
            setMessage("Please fill correct Branch No !!.");
            return;
        }
        try {
            msg = testEJB.saveBankDirectory(micr, bankCode, branchNo, branchName, branch, phoneNo, faxNo, weekly);
            if (msg.equalsIgnoreCase("Data saved successfully")) {
                setSaveDisable(true);
                setBranch("");
                setBranchName("");
                setBranchNo("");
                setPhoneNo("");
                setFaxNo("");
                setWeekly("0");
                securityDetailsTableValues();
                setMessage(msg);
            } else {
                setMessage(msg);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String validation() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (bankCode.equalsIgnoreCase("")) {
                this.setMessage("Please enter Bank Code.");
                return "false";
            } else {
                Matcher bankCodeCheck = p.matcher(bankCode);
                if (!bankCodeCheck.matches()) {
                    setMessage("Please enter numeric value for Bank Code.");
                    setBankCode("");
                    return "false";
                }
            }
            if (branchNo.equalsIgnoreCase("")) {
                setMessage("Please nnter Branch No.");
                return "false";
            } else {
                Matcher branchNoCheck = p.matcher(branchNo);
                if (!branchNoCheck.matches()) {
                    setMessage("Please enter numeric value for Branch No.");
                    setBranchNo("");
                    return "false";
                }
                if (!branchNo.matches("[0-9]*")) {
                    setMessage("Please enter numeric value for Branch No.");
                    setBranchNo("");
                    return "false";
                }
            }
            if (branchName.equalsIgnoreCase("")) {
                setMessage("Please enter Bank Name.");
                return "false";
            } else {
                if (!branchName.matches("[a-zA-z.]+([ '-][a-zA-Z]+)*")) {
                    setMessage("Please enter valid value for Bank Name.");
                    setBranchName("");
                    return "false";
                }
            }
            if (branch.equals("")) {
                setMessage("Please enter Branch.");
                return "false";
            } else {
                if (branch.substring(0, 1).equalsIgnoreCase("-") || branch.substring(0, 1).equalsIgnoreCase(".")
                        || branch.substring(0, 1).equalsIgnoreCase("&") || branch.substring(0, 1).equalsIgnoreCase(",")
                        || branch.substring(0, 1).equalsIgnoreCase(":") || branch.substring(0, 1).equalsIgnoreCase("/")) {
                    setMessage("Please enter valid value for Branch.");
                    setBranch("");
                    return "false";
                }
            }
            if (phoneNo == null || phoneNo.equals("")) {
                setMessage("Please enter the Phone No.");
                return "false";
            } else {
                Matcher phoneNoCheck = p.matcher(phoneNo);
                if (!phoneNoCheck.matches()) {
                    setMessage("Please enter numeric value for Phone No.");
                    setPhoneNo("");
                    return "false";
                }
                if (!phoneNo.matches("[0-9+,-]*")) {
                    setPhoneNo("");
                    setMessage("Please enter numeric value for Phone No.");
                    return "false";
                }
            }
            if (faxNo == null || faxNo.equals("")) {
                setMessage("Please Enter Fax No.");
                return "false";
            } else {
                Matcher faxNoCheck = p.matcher(faxNo);
                if (!faxNoCheck.matches()) {
                    setMessage("Please enter numeric value for Fax No.");
                    setFaxNo("");
                    return "false";
                }
                if (!faxNo.matches("[0-9+,-]*")) {
                    setFaxNo("");
                    setMessage("Please enter numeric value for Fax No.");
                    return "false";
                }
            }
            if (weekly.equals("0")) {
                setMessage("Please select Weekly Off.");
                return "false";
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public void refresh() {
        setMessage("");
        setBankCode("");
        setBranch("");
        setBranchName("");
        setBranchNo("");
        setPhoneNo("");
        setFaxNo("");
        setWeekly("0");
        setMicr("110");
        bankDir = new ArrayList<TableBankDirectory>();
    }

    public String exitBtnAction() {
        refresh();
        return "case1";
    }
}
