/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.master.TrialBalance;
import com.cbs.facade.master.GlMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.faces.event.ActionEvent;

public final class TrialBalanceMaster extends BaseBean {

    GlMasterFacadeRemote glMasterRemote;
    private List<SelectItem> acctTypeOption;
    private List<SelectItem> acTypeOption;
    private List<TrialBalance> trialBalanceMs;
    private String gCode;
    private String message;
    int currentRow;
    TrialBalance currentItem;
    private int seqNumber;
    private String type;
    private String subGroupCode;
    private String accountStatus;
    private String accountType;
    private String codeDescri;
    private String acType;
    private String accountNo;
    private String accountNo1;
    private String description;
    boolean flag;
    boolean flag1;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public String getSubGroupCode() {
        return subGroupCode;
    }

    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    public boolean isFlag() {
        return flag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountNo1() {
        return accountNo1;
    }

    public void setAccountNo1(String accountNo1) {
        this.accountNo1 = accountNo1;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCodeDescri() {
        return codeDescri;
    }

    public void setCodeDescri(String codeDescri) {
        this.codeDescri = codeDescri;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public TrialBalance getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TrialBalance currentItem) {
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

    public List<TrialBalance> getTrialBalanceMs() {
        return trialBalanceMs;
    }

    public void setTrialBalanceMs(List<TrialBalance> trialBalanceMs) {
        this.trialBalanceMs = trialBalanceMs;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public List<SelectItem> getAcTypeOption() {
        return acTypeOption;
    }

    public void setAcTypeOption(List<SelectItem> acTypeOption) {
        this.acTypeOption = acTypeOption;
    }    

    public TrialBalanceMaster() {
        try {
            glMasterRemote = (GlMasterFacadeRemote) ServiceLocator.getInstance().lookup("GlMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            acTypeOption = new ArrayList<SelectItem>();
            acTypeOption.add(new SelectItem(CbsAcCodeConstant.GL_ACCNO.getAcctCode(),CbsAcCodeConstant.GL_ACCNO.getAcctCode()));
            accountType();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void accountType() {
        try {
            List acnoDataList = new ArrayList();
            acnoDataList = glMasterRemote.accountTypeTrialBalance();
            acctTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    acctTypeOption.add(new SelectItem(ee.toString(), ee.toString()));
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
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (TrialBalance item : trialBalanceMs) {
                if (item.getAccType().equals(accNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            this.setMessage("");
            String deleteEntry = new String();
            deleteEntry = glMasterRemote.deleteDataTrialBalance(currentItem.getSeqNo(), currentItem.getType());
            this.setMessage(deleteEntry);
            setTable(gCode);
            clrText();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        try {
            if (this.type.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select Type.");
                return;
            }
            if (this.gCode.equalsIgnoreCase("") || gCode == null) {
                this.setMessage("Please Enter Group Code");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher gCodecheck = p.matcher(gCode);
            if (!gCodecheck.matches()) {
                this.setMessage("Please Enter Valid Group Code In Integers.");
                return;
            }
            if (Integer.parseInt(this.gCode) < 0) {
                this.setMessage("Negative Group Code is not accepted.");
                return;
            }
            if (this.subGroupCode.equalsIgnoreCase("") || subGroupCode == null) {
                this.setMessage("Please Enter Sub Group Code");
                return;
            }
            Matcher subGroupCodecheck = p.matcher(subGroupCode);
            if (!subGroupCodecheck.matches()) {
                this.setMessage("Please Enter Valid Sub Group Code In Integers.");
                return;
            }
            if (Integer.parseInt(this.subGroupCode) < 0) {
                this.setMessage("Negative Sub Group Code is not accepted.");
                return;
            }
            if (this.accountNo.equalsIgnoreCase("") || accountNo == null) {
                this.setMessage("Please Enter Code");
                return;
            }
            Matcher accountNocheck = p.matcher(accountNo);
            if (!accountNocheck.matches()) {
                this.setMessage("Please Enter Valid Code In Integers.");
                return;
            }
            if (Integer.parseInt(this.accountNo) < 0) {
                this.setMessage("Negative Code is not accepted.");
                return;
            }
            if (this.accountStatus.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select Account Status");
                return;
            }
            if (this.description.equalsIgnoreCase("") || this.description == null) {
                this.setMessage("Please Fill Description Code");
                return;
            }
//            if (this.accountType.equalsIgnoreCase("--SELECT--")) {
//                this.setMessage("Please Select Account Type");
//                return;
//            }
            if (this.codeDescri == null) {
                setCodeDescri("");
            }
             String accType = this.acType;
             //String code = this.accountNo.substring(2);
            String code = this.accountNo;
            int length = code.length();
            int addZero = 6 - length;
            for (int i = 1; i <= addZero; i++) {
                code = "0" + code;
            }
            String fullAccNo = accType + code;
             //String fullAccNo = this.accountNo;
            if (type.equals("LIABILITIES")) {
                type = "L";
            } else {
                type = "A";
            }
            if (accountStatus.equals("INOPERATIVE")) {
                accountStatus = "I";
            }
            if (accountStatus.equals("OPERATIVE")) {
                accountStatus = "O";
            }
            if (accountStatus.equals("ALL")) {
                accountStatus = "A";
            }
            String save = glMasterRemote.saveDataTrialBalance(type, Integer.parseInt(gCode), Integer.parseInt(subGroupCode), accountStatus, fullAccNo,accountType, description, getUserName());
            this.setMessage(save);
            setTable(gCode);
            clrText();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void codeDescriptionss() {
        try {
            if (this.accountNo.equalsIgnoreCase("") || accountNo == null) {
                this.setMessage("Please Enter Code");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher accountNocheck = p.matcher(accountNo);
            if (!accountNocheck.matches()) {
                this.setMessage("Please Enter Valid Code In Integers.");
                return;
            }
            if (Integer.parseInt(this.accountNo) < 0) {
                this.setMessage("Negative Code is not accepted.");
                return;
            } else {
                this.setMessage("");
            }
            String descriptions = glMasterRemote.codeDescriptionTrialBalance(accountType);
            if (descriptions == null) {
                this.setCodeDescri("");
            } else {
                this.setCodeDescri(descriptions);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void types() {
        try {
            if (type.equals("--SELECT--")) {
                this.setMessage("Type Cann't Blank.");
            } else {
                this.setgCode("");
                this.setMessage("");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void groupCodess() {
        try {
            if (this.gCode.equalsIgnoreCase("") || gCode == null) {
                this.setMessage("Please Enter Group Code");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher gCodecheck = p.matcher(gCode);
            if (!gCodecheck.matches()) {
                this.setMessage("Please Enter Valid Group Code In Integers.");
                return;
            }
            if (Integer.parseInt(this.gCode) < 0) {
                this.setMessage("Negative Group Code is not accepted.");
                return;
            } else {
                this.setMessage("");
            }
            if (type.equals("LIABILITIES")) {
                type = "L";
            } else {
                type = "A";
            }
            String groupCodes = glMasterRemote.groupCodeTrialBalance(type, Integer.parseInt(gCode));
            if (groupCodes.equalsIgnoreCase("Liabilities Group Code Series Should be less than 1000")) {
                flag = false;
                this.setMessage(groupCodes);
            } else if (groupCodes.equalsIgnoreCase("Assests Group Code Series Should be Greater than 1000")) {
                this.setMessage(groupCodes);
                flag = false;
            } else {
                flag = true;
                this.setMessage("");
                this.setSubGroupCode(groupCodes);
                if (Integer.parseInt(gCode) <= 0) {
                    gCode = "0";
                }
                setTable(gCode);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void accountDescription() {
        try {
            this.setMessage("");
            String code = this.accountNo;
            int length = code.length();
            int addZero = 6 - length;
            for (int i = 1; i <= addZero; i++) {
                code = "0" + code;
            }
            if (this.accountNo.equalsIgnoreCase("") || accountNo == null) {
                this.setMessage("Please Enter Code");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher accountNocheck = p.matcher(accountNo);
            if (!accountNocheck.matches()) {
                this.setMessage("Please Enter Valid Code.");
                return;
            }
            if (Integer.parseInt(this.accountNo) < 0) {
                this.setMessage("Negative Code is not accepted.");
                return;
            } else {
                this.setMessage("");
            }
            
            String desc = glMasterRemote.codeTrialBalance(code, Integer.parseInt(subGroupCode));
            if (desc.equals("Account No. Does Not Exist") || desc.equals("Does Not Allow Profit and Loss Series.")) {
                this.setMessage(desc);
                flag1 = false;
            } else {
                if (flag1 == true) {
                } else {
                    this.setAccountNo1(desc);
                }
                this.setDescription(desc);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void subGroupCodess() {
        try {
            if (this.subGroupCode.equalsIgnoreCase("") || subGroupCode == null) {
                this.setMessage("Please Enter Sub Group Code");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher subGroupCodecheck = p.matcher(subGroupCode);
            if (!subGroupCodecheck.matches()) {
                this.setMessage("Please Enter Valid Sub Group Code In Integers.");
                return;
            }
            if (Integer.parseInt(this.subGroupCode) < 0) {
                this.setMessage("Negative Sub Group Code is not accepted.");
                return;
            } else {
                this.setMessage("");
            }
            String acDesc = glMasterRemote.subGroupCodeTrialBalance(Integer.parseInt(gCode), Integer.parseInt(subGroupCode));
            if (acDesc.equalsIgnoreCase("Data Doesn't exist.")) {
                this.setAccountNo1("");
                this.setMessage("");
                flag1 = false;
            } else {
                this.setAccountNo1(acDesc);
                flag1 = true;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clearText() {
        trialBalanceMs = new ArrayList<TrialBalance>();
        flag1 = false;
        setMessage("");
        setCodeDescri("");
        setgCode("");
        setDescription("");
        setSubGroupCode("");
        setAccountNo1("");
        setAccountNo("");
        //setOldAccountNo("");
        setAccountStatus("--SELECT--");
        setAccountType("--SELECT--");
         setAcType("--SELECT--");
    }

    public void clrText() {
        flag1 = false;
        setMessage("");
        setCodeDescri("");
        setgCode("");
        setDescription("");
        setSubGroupCode("");
        setAccountNo1("");
        setAccountNo("");      
         //setOldAccountNo("");
        setAccountStatus("--SELECT--");
        setAccountType("--SELECT--");
        setAcType("--SELECT--");
    }

    public String exitFrm() {
        clearText();
        return "case1";
    }

    public void setTable(String gCode) {
        try {
            trialBalanceMs = new ArrayList<TrialBalance>();
            List resultLt = new ArrayList();
            resultLt = glMasterRemote.tableDataTrialBalance(Integer.parseInt(gCode));
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    TrialBalance balance = new TrialBalance();
                    balance.setGroupCode(Integer.parseInt(ele.get(0).toString()));
                    balance.setSubGroupCode(Integer.parseInt(ele.get(1).toString()));
                    balance.setCode(ele.get(2).toString());
                    balance.setDescription(ele.get(3).toString());
                    balance.setAccType(ele.get(4).toString());
                    balance.setType(ele.get(5).toString());
                    balance.setAcctStatus(ele.get(6).toString());
                    balance.setLastUpdatedBy(ele.get(7).toString());
                    balance.setTranTime(ele.get(8).toString());
                    balance.setSeqNo(Integer.parseInt(ele.get(9).toString()));
                    trialBalanceMs.add(balance);
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
}
