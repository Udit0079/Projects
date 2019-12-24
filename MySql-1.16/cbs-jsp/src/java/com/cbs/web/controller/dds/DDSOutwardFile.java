/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class DDSOutwardFile extends BaseBean {

    private String message;
    private String agentCode;
    private String agentName;
    private String accountCode;
    private String daysFlag;
    private String days;
    private String bankCode = "";
    private int pratiNidhiNew = 0;
    private List<SelectItem> accountCodeList;
    private final String jndiNameDds = "DDSManagementFacade";
    private DDSManagementFacadeRemote ddsRemote = null;
    public static int PORT = 2001;
    private Socket socketTCP;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private UploadNeftRtgsMgmtFacadeRemote remote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDaysFlag() {
        return daysFlag;
    }

    public void setDaysFlag(String daysFlag) {
        this.daysFlag = daysFlag;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public List<SelectItem> getAccountCodeList() {
        return accountCodeList;
    }

    public void setAccountCodeList(List<SelectItem> accountCodeList) {
        this.accountCodeList = accountCodeList;
    }

    public int getPratiNidhiNew() {
        return pratiNidhiNew;
    }

    public void setPratiNidhiNew(int pratiNidhiNew) {
        this.pratiNidhiNew = pratiNidhiNew;
    }

    public DDSOutwardFile() {
        try {
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiNameDds);
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            refreshForm();
            fillAccountCode();
            enableDaysFlag();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void enableDaysFlag() {
        this.setMessage("");
        try {
            List<MbSmsSenderBankDetail> bankList = remote.getBankCode();
            if (bankList.isEmpty()) {
                this.setMessage("Please define bank code.");
                return;
            }

            MbSmsSenderBankDetail bankEntity = bankList.get(0);
            bankCode = bankEntity.getBankCode();
            if (bankCode.equalsIgnoreCase("eucb")
                    || bankCode.equalsIgnoreCase("sric")
                    || bankCode.equalsIgnoreCase("nabu")
                    || bankCode.equalsIgnoreCase("hisr")) {
                this.daysFlag = "true";     //Balaji(Jamshedpur Case)
            } else {
                this.daysFlag = "false";    //Prati Nidhi(Pune)    
                this.pratiNidhiNew = ftsRemote.getCodeForReportName("PRATI-NIDHI-NEW");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillAccountCode() {
        this.setMessage("");
        accountCodeList = new ArrayList<SelectItem>();
        try {
            accountCodeList.add(new SelectItem("0", "--Select--"));
            List acTypeList = ddsRemote.getAcctType();
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector element = (Vector) acTypeList.get(i);
                    accountCodeList.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAccountCode() {
        this.setMessage("");
        try {
            if (this.accountCode.equals("0")) {
                this.setMessage("Please select account code.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAgentCode() {
        this.setMessage("");
        try {
            if (this.agentCode == null || this.agentCode.equals("") || this.agentCode.length() < 2) {
                this.setMessage("Please fill agent code in 2 digit.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher agentCheck = p.matcher(this.agentCode);
            if (!agentCheck.matches()) {
                this.setMessage("Invalid Agent Code Entry.");
                return;
            }

            String name = ddsRemote.getAgentName(this.agentCode, getOrgnBrCode());
            if (name.equals("")) {
                this.setMessage("Agent does not exist.");
                return;
            } else {
                this.setAgentName(name);
                if (daysFlag.equalsIgnoreCase("true")) {
                    this.setMessage("Now click on generate file button to create the file.");
                } else {
                    this.setMessage("");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validatePwdDays() {
        this.setMessage("");
        try {
            if (this.pratiNidhiNew == 1 || this.pratiNidhiNew == 2) { //Prati Nidhi New Machine Type
                if (this.days == null || this.days.equals("")) {
                    this.setMessage("Please fill Password Expiry Days. Values can be 1-5");
                    return;
                }
                if (Integer.parseInt(this.days) < 1 || Integer.parseInt(this.days) > 5) {
                    this.setMessage("Please fill Password Expiry Days. Values can be 1-5");
                    return;
                }
            } else {  //Prati Nidhi Old Machine Type
                if (this.days == null || this.days.equals("")) {
                    this.setMessage("Please fill Password Expiry Days. Values can be 1-7");
                    return;
                }
                if (Integer.parseInt(this.days) < 1 || Integer.parseInt(this.days) > 7) {
                    this.setMessage("Please fill Password Expiry Days. Values can be 1-7");
                    return;
                }
            }
            this.setMessage("Now click on generate file button to create the file.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void generateFile() {
        this.setMessage("");
        try {
            if (this.accountCode.equals("0")) {
                this.setMessage("Please select account code.");
                return;
            }
            if (this.agentCode == null || this.agentCode.equals("") || this.agentCode.length() < 2) {
                this.setMessage("Please fill agent code in 2 digit.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher agentCheck = p.matcher(this.agentCode);
            if (!agentCheck.matches()) {
                this.setMessage("Invalid Agent Code Entry.");
                return;
            }

            String password = "";
            if (this.daysFlag.equalsIgnoreCase("false")) {
                if (this.pratiNidhiNew == 1 || this.pratiNidhiNew == 2) {//Prati Nidhi New Machine Type
                    if (this.days == null || this.days.equals("")) {
                        this.setMessage("Please fill Password Expiry Days. Values can be 1-5");
                        return;
                    }
                    if (Integer.parseInt(this.days) < 1 || Integer.parseInt(this.days) > 5) {
                        this.setMessage("Please fill Password Expiry Days. Values can be 1-5");
                        return;
                    }
                } else {
                    if (this.days == null || this.days.equals("")) {
                        this.setMessage("Please fill Password Expiry Days. Values can be 1-7");
                        return;
                    }
                    if (Integer.parseInt(this.days) < 1 || Integer.parseInt(this.days) > 7) {
                        this.setMessage("Please fill Password Expiry Days. Values can be 1-7");
                        return;
                    }
                }
                password = ddsRemote.getAgentPassword(this.agentCode, getOrgnBrCode());
                if (password.equalsIgnoreCase("") || password.trim().length() != 8) {
                    this.setMessage("Password should be 8 digit numeric.");
                    return;
                }
            }
            List<String> dataList;
            if (this.daysFlag.equalsIgnoreCase("false") && this.pratiNidhiNew == 1) { //Only For Prati Nidhi New Machine Type
                dataList = ddsRemote.pratiNidhiNewDDSOutwardFile(this.accountCode, getOrgnBrCode(),
                        this.agentCode, this.days, bankCode, password);
            } else if (this.daysFlag.equalsIgnoreCase("false") && this.pratiNidhiNew == 2) { //Limit Base Prati Nidhi New Machine Type
                dataList = ddsRemote.pratiNidhiNewDDSOutwardFileForMode2(this.accountCode, getOrgnBrCode(),
                        this.agentCode, this.days, bankCode, password);
            } else {
                dataList = ddsRemote.generateDDSOutwardFile(this.accountCode, getOrgnBrCode(),
                        this.agentCode, this.days, bankCode, password);
            }
            if (!dataList.isEmpty()) {
                //String remoteHost = getHttpRequest().getRemoteHost();
                String remoteHost = WebUtil.getClientIP(getHttpRequest());
                socketTCP = new Socket(remoteHost, PORT);
                objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());
                objectInputStream = new ObjectInputStream(socketTCP.getInputStream());
                writeObject(dataList);
                Vector result = (Vector) readObject();
                System.out.println("result = " + result);
                if (result.elementAt(0).equals(true)) {
                    if (daysFlag.equalsIgnoreCase("true")) {
                        this.setMessage("DDS outward file is successfully generated. Now you can send data to Balaji Machine.");
                    } else {
                        this.setMessage("DDS outward file is successfully generated. Now you can send data to Machine. "
                                + "And Password is :: " + password);
                    }
                } else {
                    this.setMessage("System error occurred");
                }
            } else {
                this.setMessage("There is no data to generate the file.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean writeObject(List<String> dataList) {
        try {
            Vector writeVector = new Vector();
            if (daysFlag.equalsIgnoreCase("true")) { //Balaji(Jamshedpur)
                writeVector.add("DDS");
            } else {    //Prati Nidhi
                writeVector.add("DDS-SIPL");
            }
            writeVector.add(dataList);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Object readObject() {
        try {
            Object readObject = objectInputStream.readObject();
            return readObject;
        } catch (Exception e) {
            e.printStackTrace();
            if (!this.socketTCP.isClosed()) {
                System.out.println("SOCKET CLOSED FROM READ OBJECT");
            }
            return null;
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setAccountCode("0");
        this.setAgentCode("");
        this.setAgentName("");
        this.setDays("");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
