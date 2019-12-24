package com.cbs.web.controller.dds;

import com.cbs.constant.AccStatusEnum;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.cbs.dto.DdsTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.util.Vector;

public class DDSAgentMaster extends BaseBean {

    private String name;
    private String address;
    private String phone;
    private String agentCode;
    private String remarks;
    private DDSManagementFacadeRemote remote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private TransactionManagementFacadeRemote txnRemote;
    private List<DdsTable> datagrid;
    private DdsTable currentData;
    private String status = "A";
    private String msg;
    private String code11;
    private String msgBox;
    private boolean disableSave;
    private boolean update = true;
    private boolean flag;
    private String password;
    private int ddsManualCode;
    private String acno;
    private String newAcNo;
    private String custname;
    private String jointName;
    private String openDate;
    private int txnMode; // either cash or transafer
    private String displayAcct = "";
    private String displayBlank = "none";

    public DDSAgentMaster() {
        try {
            remote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            txnMode = ftsRemote.getCodeForReportName("DDS-ONLINE-TXN-MODE"); //Either cash or transfer, 1 for transfer or 0 for cash
            if (txnMode == 1 || txnMode == 2) {
                displayAcct = "";
                displayBlank = "none";
            } else {
                newAcNo = "";
                displayAcct = "none";
                displayBlank = "";
            }
            viewData();
        } catch (ApplicationException e) {
            setMsg(e.getMessage());
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void accountDetails() {
        try {
            setMsg("");
            if (this.acno.equalsIgnoreCase("") || this.acno == null || this.acno.equalsIgnoreCase("null")) {
                throw new ApplicationException("Please Enter 12 Digit Account Number.");

            }
            if (!this.acno.equalsIgnoreCase("") && this.acno.length() < 12) {
                throw new ApplicationException("Please Enter 12 Digit Account Number.");

            }
            newAcNo = ftsRemote.getNewAccountNumber(acno);
            String curBrnCode = ftsRemote.getCurrentBrnCode(newAcNo);

            if (!curBrnCode.equals(getOrgnBrCode())) {
                throw new ApplicationException("Account number must be from the same Branch");
            }
            String chkAccNo = txnRemote.checkForAccountNo(newAcNo);
            if (!chkAccNo.equals("TRUE")) {
                throw new ApplicationException(chkAccNo);
            }
            if (txnMode == 1) {
                getAcctDetails();
            } else if (txnMode == 2) {
                getGLdetails();
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
            setNewAcNo("");
            setAcno("");
            setCustname("");
            setOpenDate("");
            setJointName("");
        }
    }

    private void getGLdetails() throws ApplicationException {
        List selectGLTable = txnRemote.selectFromGLTable(newAcNo);
        if (selectGLTable.isEmpty()) {
            throw new ApplicationException("Account No does not exist.");
        }
        Vector vecGLTable = (Vector) selectGLTable.get(0);
        int postFlag = Integer.parseInt(vecGLTable.get(2).toString());
        int msgFlag = Integer.parseInt(vecGLTable.get(3).toString());

        if (postFlag == 0 || postFlag == 99) {
            throw new ApplicationException("Entry for this Account No is not allowed.");
        }
        if (msgFlag == 4) {
            throw new ApplicationException("Entry for this Account No is not alowed.");
        }
        if (msgFlag == 50) {
            throw new ApplicationException("Entry in Share Capital Head is not allowed from this Screen");
        }
        custname = vecGLTable.get(0).toString();
        jointName = "";
        openDate = "";
    }

    private void getAcctDetails() throws ApplicationException {

        List acctDetails = txnRemote.selectFromAcctMaster(newAcNo);
        if (acctDetails.isEmpty()) {
            throw new ApplicationException("Account number doe not exist");
        }
        Vector v1 = (Vector) acctDetails.get(0);

        String acctOpenDate = v1.get(9).toString();
        int acStatus = Integer.parseInt(v1.get(10).toString());

        if (acStatus != 1) {
            throw new ApplicationException(AccStatusEnum.getStatusMsg(acStatus));
        }

        custname = v1.get(1).toString();
        jointName = v1.get(2).toString() + " " + v1.get(3).toString() + " " + v1.get(4).toString() + " " + v1.get(5).toString();

        openDate = acctOpenDate.substring(6) + "/" + acctOpenDate.substring(4, 6) + "/" + acctOpenDate.substring(0, 4);
    }

    public void saveData() {
        try {
            flag = false;
            msg = validation();
            if (!msg.equalsIgnoreCase("ok")) {
                throw new ApplicationException(msg);
            }
            msg = "";
            disableSave = true;
            update = true;
            code11 = "01";
            String code = remote.getMaxCode(getOrgnBrCode());

            int a = Integer.parseInt(code) + 1;
            code11 = CbsUtil.lPadding(2, a);

            setAgentCode(code11);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String opdate = sdf.format(new Date());

            msg = remote.saveData(agentCode, name, address, phone, opdate, "A", remarks, getOrgnBrCode(), getUserName(), this.password, newAcNo);
            flag = true;
            refresh();
            msgBox = "Success : Genrated Agent code is " + code11;
            viewData();

        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void viewData() {
        try {
            datagrid = remote.viewData(getOrgnBrCode());
            if (datagrid.isEmpty()) {
                setMsg("No Agent records exist !!");
            }
            ddsManualCode = ftsRemote.getCodeForReportName("DDS-MANUAL"); //DDS is running manualy.
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void updateData() {
        try {
            msg = "";
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                String result = remote.updateData(currentData.getAgentCode(), name, address, phone, remarks, status, getUserName(), getOrgnBrCode(),
                        this.password, newAcNo);
                refresh();
                viewData();
                msg = result;
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public String exitAction() {
        try {
            refresh();
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
        return "case1";
    }

    public void selectData() {
        try {
            update = false;
            disableSave = true;
            msg = "";
            setAgentCode(code11);
            setAgentCode(currentData.getAgentCode());
            setPhone(currentData.getPhone());
            setAddress(currentData.getAddress());
            setRemarks(currentData.getRemarks());
            setName(currentData.getName());
            if (currentData.getStatus() != null && currentData.getStatus().length() > 1) {
                setStatus(currentData.getStatus().substring(0, 1));
            }
            this.setPassword(currentData.getPassword());
            this.setAcno(currentData.getAgAcno());
            setNewAcNo(currentData.getAgAcno());
            
            if (txnMode == 1) {
                getAcctDetails();
            } else if (txnMode == 2) {
                getGLdetails();
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void refresh() {
        try {
            update = true;
            disableSave = false;
            setAgentCode("");
            setPhone("");
            setAddress("");
            setRemarks("");
            setName("");
            setStatus("A");
            this.setPassword("");
            setAcno("");
            setNewAcNo("");
            setCustname("");
            setOpenDate("");
            setJointName("");
            viewData();
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void btnRefresh() {
        try {
            msg = "";
            refresh();
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public String validation() {
        try {
            Validator validator = new Validator();
            if (name.equalsIgnoreCase("")) {
                return "Field 'Name' is blank !!";
            }
            if (!validator.validateStringOnlyChar(name)) {
                return "Field 'Name' contains invalid characters !!";
            }
            if (address.equalsIgnoreCase("")) {
                return "Field 'Address' is blank !!";
            }
            if (!validator.validateString(remarks)) {
                return "Field 'Remarks' is blank !!";
            }
            if (status == null || status.equalsIgnoreCase("0")) {
                return "Please select Field 'Status' !!";
            }
            if (!validator.validateMobile(phone)) {
                return "Field 'Phone' contains invalid characters !!";
            }
            if (ddsManualCode != 1) { // 1 is for manual dds operation.
                if (this.password == null || this.password.equals("") || password.trim().length() != 8) {
                    return "Please fill 8 digit numeric password.";
                }
                if (!validator.validateInteger(password)) {
                    return "Please fill 8 digit numeric password.";
                }
            } else {
                this.password = "";
            }
            if (txnMode == 1 && newAcNo.equals("")) {
                return "Please fill the proper Account";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "ok";
    }

    public String getDisplayBlank() {
        return displayBlank;
    }

    public void setDisplayBlank(String displayBlank) {
        this.displayBlank = displayBlank;
    }

    public String getDisplayAcct() {
        return displayAcct;
    }

    public void setDisplayAcct(String displayAcct) {
        this.displayAcct = displayAcct;
    }

    public String getJointName() {
        return jointName;
    }

    public void setJointName(String jointName) {
        this.jointName = jointName;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(String msgBox) {
        this.msgBox = msgBox;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DdsTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<DdsTable> datagrid) {
        this.datagrid = datagrid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public DdsTable getCurrentData() {
        return currentData;
    }

    public void setCurrentData(DdsTable currentData) {
        this.currentData = currentData;
    }

    public void getTableData() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDdsManualCode() {
        return ddsManualCode;
    }

    public void setDdsManualCode(int ddsManualCode) {
        this.ddsManualCode = ddsManualCode;
    }

    public boolean validateString(String value) {
        try {
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == '#' || value.charAt(i) == '~') {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
