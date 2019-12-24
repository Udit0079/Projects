/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.BalanceMastGrid;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.DailyGlbMasterGrid;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class DailyGLBMaster {

    private HttpServletRequest request;
    private String newDate;
    private String userName;
    private String accDescType;
    private String balType;
    private String orgBrnCode;
    private String type;
    private String groupCode;
    private int currentRow;
    private String subGroupCode;
    private String subSubGroupCode;
    private String description;
    private String code;
    private String subCode;
    private String acType;
    private String error;
    private String acStatus;
    private List<SelectItem> typeList;
    private List<SelectItem> acctDescOption;
    private List<SelectItem> acStatusList;
    private String subCodeText;
    private String subCodeDesc;
    private String subSubCodeText;
    private String subSubCodeDesc;
    private DailyMasterFacadeRemote rfr;
    List<DailyGlbMasterGrid> dglbmasList = new ArrayList<DailyGlbMasterGrid>();
    private DailyGlbMasterGrid dailyGlbMast;
    private int serialNo;
    private boolean disableSubCode;
    private boolean disableSubSubCode;
    private boolean disableSubSubSubCode;
    private String subSubSubCodeType;
    private String subSubSubCodeAcc;
    private String subSubSubCodeDesc;
    private String subSubSubGroupCode;
    FtsPostingMgmtFacadeRemote ftsBeanRemote;
    private String newSubCode;
    private String newSubSubCode;
    private String newSubSubSubCode;
    private String saveMessage;

    public String getSubSubCodeDesc() {
        return subSubCodeDesc;
    }

    public void setSubSubCodeDesc(String subSubCodeDesc) {
        this.subSubCodeDesc = subSubCodeDesc;
    }

    public String getSubSubCodeText() {
        return subSubCodeText;
    }

    public void setSubSubCodeText(String subSubCodeText) {
        this.subSubCodeText = subSubCodeText;
    }

    public String getSubCodeDesc() {
        return subCodeDesc;
    }

    public void setSubCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
    }

    public String getSubCodeText() {
        return subCodeText;
    }

    public void setSubCodeText(String subCodeText) {
        this.subCodeText = subCodeText;
    }

    public boolean isDisableSubSubSubCode() {
        return disableSubSubSubCode;
    }

    public void setDisableSubSubSubCode(boolean disableSubSubSubCode) {
        this.disableSubSubSubCode = disableSubSubSubCode;
    }

    public boolean isDisableSubSubCode() {
        return disableSubSubCode;
    }

    public void setDisableSubSubCode(boolean disableSubSubCode) {
        this.disableSubSubCode = disableSubSubCode;
    }

    public boolean isDisableSubCode() {
        return disableSubCode;
    }

    public void setDisableSubCode(boolean disableSubCode) {
        this.disableSubCode = disableSubCode;
    }

    public String getNewSubCode() {
        return newSubCode;
    }

    public void setNewSubCode(String newSubCode) {
        this.newSubCode = newSubCode;
    }

    public String getNewSubSubCode() {
        return newSubSubCode;
    }

    public void setNewSubSubCode(String newSubSubCode) {
        this.newSubSubCode = newSubSubCode;
    }

    public String getNewSubSubSubCode() {
        return newSubSubSubCode;
    }

    public void setNewSubSubSubCode(String newSubSubSubCode) {
        this.newSubSubSubCode = newSubSubSubCode;
    }

    public String getSubSubGroupCode() {
        return subSubGroupCode;
    }

    public void setSubSubGroupCode(String subSubGroupCode) {
        this.subSubGroupCode = subSubGroupCode;
    }

    public List<SelectItem> getAcStatusList() {
        return acStatusList;
    }

    public void setAcStatusList(List<SelectItem> acStatusList) {
        this.acStatusList = acStatusList;
    }

    public List<SelectItem> getAcctDescOption() {
        return acctDescOption;
    }

    public void setAcctDescOption(List<SelectItem> acctDescOption) {
        this.acctDescOption = acctDescOption;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubGroupCode() {
        return subGroupCode;
    }

    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    public String getAccDescType() {
        return accDescType;
    }

    public void setAccDescType(String accDescType) {
        this.accDescType = accDescType;
    }

    public String getBalType() {
        return balType;
    }

    public void setBalType(String balType) {
        this.balType = balType;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DailyGlbMasterGrid getDailyGlbMast() {
        return dailyGlbMast;
    }

    public void setDailyGlbMast(DailyGlbMasterGrid dailyGlbMast) {
        this.dailyGlbMast = dailyGlbMast;
    }

    public List<DailyGlbMasterGrid> getDglbmasList() {
        return dglbmasList;
    }

    public void setDglbmasList(List<DailyGlbMasterGrid> dglbmasList) {
        this.dglbmasList = dglbmasList;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getSubSubSubCodeType() {
        return subSubSubCodeType;
    }

    public void setSubSubSubCodeType(String subSubSubCodeType) {
        this.subSubSubCodeType = subSubSubCodeType;
    }

    public String getSubSubSubCodeAcc() {
        return subSubSubCodeAcc;
    }

    public void setSubSubSubCodeAcc(String subSubSubCodeAcc) {
        this.subSubSubCodeAcc = subSubSubCodeAcc;
    }

    public String getSubSubSubCodeDesc() {
        return subSubSubCodeDesc;
    }

    public void setSubSubSubCodeDesc(String subSubSubCodeDesc) {
        this.subSubSubCodeDesc = subSubSubCodeDesc;
    }

    public String getSubSubSubGroupCode() {
        return subSubSubGroupCode;
    }

    public void setSubSubSubGroupCode(String subSubSubGroupCode) {
        this.subSubSubGroupCode = subSubSubGroupCode;
    }

    public DailyGLBMaster() {
        try {
            request = getRquest();
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            rfr = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setNewDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            getAcDesc();
            getDetailsForTable();
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void getAcDesc() {
        try {
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("L", "LIABLITIES"));
            typeList.add(new SelectItem("A", "ASSETS"));
            acStatusList = new ArrayList<SelectItem>();
            acStatusList.add(new SelectItem("1", "OPERATVE"));
            acStatusList.add(new SelectItem("2", "INOPERATVE"));
            acStatusList.add(new SelectItem("3", "ALL"));
            List listForAcDesc = rfr.getTableDetails();
            acctDescOption = new ArrayList<SelectItem>();
            for (int i = 0; i < listForAcDesc.size(); i++) {
                Vector element1 = (Vector) listForAcDesc.get(i);
                String con = element1.get(0).toString();
                acctDescOption.add(new SelectItem(con));
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void gCode() {
        try {
            this.setError("");
            if (this.groupCode.equalsIgnoreCase("")) {
                setError("Please Fill group code");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getGroupCode());
            if (!billNoCheck.matches()) {
                this.setError("Enter the valid numeric value for text field...");
                this.setGroupCode("");
                return;
            } else {
                if (this.type.substring(0, 1).equalsIgnoreCase("L")) {
                    if (Integer.parseInt(this.groupCode) >= 1000) {
                        this.setError("Liabilities group code series should be less than 1000");
                        this.setGroupCode("");
                        return;
                    }
                } else {
                    if (Integer.parseInt(this.groupCode) <= 1000) {
                        this.setError("Assests group code series should be greater than 1000");
                        return;
                    }
                }
            }
            setError("");
            List listSubGrpCode = rfr.subGroupCode(groupCode, type);
            Vector vecSubGrpCod = (Vector) listSubGrpCode.get(0);
            if (Integer.parseInt(vecSubGrpCod.get(0).toString()) == 0) {
                this.setSubGroupCode(vecSubGrpCod.get(0).toString());
                this.setSubSubGroupCode(vecSubGrpCod.get(0).toString());
                this.setSubSubSubGroupCode(vecSubGrpCod.get(0).toString());
                this.setCode("");
                this.setSubCode("");
                this.setSubSubSubCodeType("");
                disableSubCode = true;
                disableSubSubCode = true;
                disableSubSubSubCode = true;
                List list3 = rfr.selectdescription(groupCode, vecSubGrpCod.get(0).toString(), vecSubGrpCod.get(0).toString());
                if (!list3.isEmpty()) {
                    Vector vecdesc = (Vector) list3.get(0);
                    this.setDescription(vecdesc.get(0).toString());
                } else {
                    this.setDescription("");
                }
            } else if (Integer.parseInt(vecSubGrpCod.get(0).toString()) > 0) {
                this.setSubGroupCode(vecSubGrpCod.get(0).toString());
                List list2 = rfr.subSubGroupCode(groupCode, vecSubGrpCod.get(0).toString(), this.type.substring(0, 1));
                Vector vecSubSubGrpCode = (Vector) list2.get(0);
                if (Integer.parseInt(vecSubSubGrpCode.get(0).toString()) == 0) {
                    this.setSubSubGroupCode(vecSubSubGrpCode.get(0).toString());
                    this.setSubSubSubGroupCode(vecSubSubGrpCode.get(0).toString());
                    disableSubCode = false;
                    disableSubSubCode = true;
                    disableSubSubSubCode = true;
                } else {
                    this.setSubSubGroupCode(vecSubSubGrpCode.get(0).toString());
                    List list4 = rfr.subSubSubGroupCode(groupCode, vecSubGrpCod.get(0).toString(), this.type.substring(0, 1), vecSubSubGrpCode.get(0).toString());
                    Vector vecSubSubSubGroupCode = (Vector) list4.get(0);
                    if (Integer.parseInt(vecSubSubSubGroupCode.get(0).toString()) == 0) {
                        this.setSubSubSubGroupCode(vecSubSubSubGroupCode.get(0).toString());
                        disableSubCode = true;
                        disableSubSubCode = false;
                        disableSubSubSubCode = true;
                    } else {
                        this.setSubSubSubGroupCode(vecSubSubSubGroupCode.get(0).toString());
                        disableSubCode = true;
                        disableSubSubCode = true;
                        disableSubSubSubCode = false;
                    }
                }
                this.setSubCode("");
                List list3 = rfr.selectdescription(groupCode, vecSubGrpCod.get(0).toString(), vecSubSubGrpCode.get(0).toString());
                if (!list3.isEmpty()) {
                    Vector vecdesc = (Vector) list3.get(0);
                    this.setDescription(vecdesc.get(0).toString());
                } else {
                    this.setDescription("");
                }
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void getGlSubCodeDesc() {
        try {
            this.setError("");
            if (getSubCodeText().length() < 8) {
                setError("Entered account number length cannot be less than 8");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(getSubCodeText());
            if (accountNumber.startsWith("Account Number does not exist")) {
                this.setError("Entered Sub code account number does not exist");
                return;
            } else {
                setError("");
                newSubCode = accountNumber.substring(2, 10);
            }
            List list2 = rfr.acNameForBankPurpose(newSubCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setError("");
                this.setSubCodeDesc(acname1);
            } else {
                this.setSubCodeDesc("");
                this.setError("Invalid account number.");
                return;
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void getGlSubSubCodeDesc() {
        try {
            this.setError("");
            if (getSubSubCodeText().length() < 8) {
                setError("Entered Sub Sub Sub code length cannot be less than 8");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(getSubSubCodeText());
            if (accountNumber.startsWith("Account Number does not exist")) {
                this.setError("Entered account number does not exist");
                return;
            } else {
                setError("");
                newSubSubCode = accountNumber.substring(2, 10);
            }
            List list2 = rfr.acNameForBankPurpose(newSubSubCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setError("");
                this.setSubSubCodeDesc(acname1);
            } else {
                this.setSubSubCodeDesc("");
                this.setError("Invalid Account Number.");
                return;
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void getGlSubSubSubCodeDesc() {
        try {
            this.setError("");
            if (getSubSubSubCodeAcc().length() < 8) {
                setError("Entered Sub Sub Sub code account length cannot be less than 8");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(getSubSubSubCodeAcc());
            if (accountNumber.startsWith("Account Number does not exist")) {
                this.setError("Entered Sub Sub Sub account number does not exist");
                return;
            } else {
                setError("");
                newSubSubSubCode = accountNumber.substring(2, 10);
            }
            List list2 = rfr.acNameForBankPurpose(newSubSubSubCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setError("");
                this.setSubSubSubCodeDesc(acname1);
            } else {
                this.setSubSubSubCodeDesc("");
                this.setError("Invalid account number.");
                return;
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public String validations() {
        try {
            this.setError("");
            if (this.getGroupCode().equals("")) {
                return "Group code field cannot be empty";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher codeMatch = p.matcher(this.getGroupCode());
            if (!codeMatch.matches()) {
                return "Enter the valid numeric value for group code...";
            } else {
                if (this.type.equalsIgnoreCase("L")) {
                    if (Integer.parseInt(this.groupCode) >= 1000) {
                        return "Liabilities group code series should be less than 1000";
                    }
                } else {
                    if (Integer.parseInt(this.groupCode) <= 1000) {
                        return "Assests group code series should be greater than 1000";
                    }
                }
            }
            if (this.getSubGroupCode().equals("")) {
                return "Sub Group code field cannot be empty";
            }
            if (this.getSubSubGroupCode().equals("")) {
                return "Sub Sub Group code field cannot be empty,";
            }
            if (this.getSubSubSubGroupCode().equals("")) {
                return "Sub Sub Sub Group code field cannot be empty";
            }
            if (this.getDescription().equals("")) {
                return "Description field cannot be empty";
            }
            codeMatch = p.matcher(this.getSubGroupCode());
            if (!codeMatch.matches()) {
                this.setSubGroupCode("");
                return "Enter the valid numeric value for Sub Group Code...";
            }
            codeMatch = p.matcher(this.getSubSubGroupCode());
            if (!codeMatch.matches()) {
                this.setSubSubGroupCode("");
                return "Enter the valid numeric value for Sub Sub Group Code...";
            }
            codeMatch = p.matcher(this.getSubSubSubGroupCode());
            if (!codeMatch.matches()) {
                this.setSubSubSubGroupCode("");
                return "Enter the valid numeric value for Sub Sub Group Code...";
            }
            Pattern p1 = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck1 = p.matcher(this.getDescription());
            if (disableSubCode == false) {
                if ((newSubCode == null)||(newSubCode.equalsIgnoreCase(""))) {
                    return "Sub code not present";
                }
                if (newSubCode.length() < 8) {
                    return "Sub code length cannot be less than 8";
                }
                billNoCheck1 = p1.matcher(this.getSubCodeDesc());
                if (!billNoCheck1.matches()) {
                    this.setSubCodeDesc("");
                    return "Enter the valid charcacter value for sub code description ..";
                }
            }
            if (disableSubSubCode == false) {
                if ((newSubSubCode == null)||(newSubSubCode.equalsIgnoreCase(""))) {
                    return "Sub code not present";
                }
                if (newSubSubCode.length() < 8) {
                    return "Sub code length cannot be less than 8";
                }
                billNoCheck1 = p1.matcher(this.getSubSubCodeDesc());
                if (!billNoCheck1.matches()) {
                    setSubSubCodeDesc("");
                    return "Enter the valid character value for Sub Sub Code description ..";
                }
            }
            if (disableSubSubSubCode == false) {
                if ((newSubSubSubCode == null)||(newSubSubSubCode.equalsIgnoreCase(""))) {
                    return "Sub code not present";
                }
                if (newSubSubSubCode.length() < 8) {
                    return "Sub code length cannot be less than 8";
                }
                billNoCheck1 = p1.matcher(this.getSubSubSubCodeDesc());
                if (!billNoCheck1.matches()) {
                    this.setSubSubSubCodeDesc("");
                    return "Enter the valid  charcacter value for Sub Sub Code Description ..";
                }
            }
        } catch (Exception e) {
            return "Exception occurred during validations";
        }
        return "true";
    }

    public void getDetailsForTable() {
        try {
            this.setError("");
            dglbmasList = new ArrayList<DailyGlbMasterGrid>();
            List resultSet = rfr.getBalanceMast();
            if (!resultSet.isEmpty()) {
                for (int i = 0; i < resultSet.size(); i++) {
                    Vector vec = (Vector) resultSet.get(i);
                    dailyGlbMast = new DailyGlbMasterGrid();

                    dailyGlbMast.setsNo(String.valueOf(dglbmasList.size() + 1));
                    dailyGlbMast.setActStatus(vec.get(14).toString());
                    dailyGlbMast.setActType(vec.get(12).toString());
                    dailyGlbMast.setDesc(vec.get(5).toString());
                    dailyGlbMast.setGlbActType(vec.get(13).toString());
                    dailyGlbMast.setGrpCode(vec.get(1).toString());
                    dailyGlbMast.setSubGrpCode(vec.get(2).toString());
                    dailyGlbMast.setSubSubSubGrpCode(vec.get(4).toString());
                    dailyGlbMast.setSubSubGrpCode(vec.get(3).toString());
                    dailyGlbMast.setsCode(vec.get(6).toString());
                    dailyGlbMast.setsCodeDesc(vec.get(7).toString());
                    dailyGlbMast.setSsCodeDesc(vec.get(9).toString());
                    dailyGlbMast.setSsCode(vec.get(8).toString());
                    dailyGlbMast.setSssCode(vec.get(10).toString());
                    dailyGlbMast.setSssCodeDesc(vec.get(11).toString());
                    dglbmasList.add(dailyGlbMast);
                }
            }
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void deleteCurrentData() {
        try {
            setError("");
            String result = rfr.deleteBalanceMast(Integer.parseInt(dailyGlbMast.getGrpCode()), Integer.parseInt(dailyGlbMast.getSubGrpCode()), Integer.parseInt(dailyGlbMast.getSubSubGrpCode()), Integer.parseInt(dailyGlbMast.getSubSubSubGrpCode()));
            refreshForm();
            setError(result);
            getDetailsForTable();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void saveGriddata() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setError(valResult);
                return;
            }
            BalanceMastGrid grid = new BalanceMastGrid();
            grid.setAcType(acType);
            grid.setActStatus(acStatus);
            grid.setDescription(description);
            grid.setGlbActype(type);
            grid.setGroupCode(groupCode);
            grid.setLastupdatedBy(userName);
            grid.setSubDescription(subCodeDesc);
            grid.setSubGLCode(newSubCode);
            grid.setSubGroupCode(subGroupCode);
            grid.setSubSubDescription(subSubCodeDesc);
            grid.setSubSubGLCode(newSubSubCode);
            grid.setSubSubGroupCode(subSubGroupCode);
            grid.setSubSubSubDescription(subSubSubCodeDesc);
            grid.setSubSubSubGLCode(newSubSubSubCode);
            grid.setSubSubSubGroupCode(subSubSubGroupCode);
            String saveresult = rfr.saveData(grid, userName);
            getDetailsForTable();
            if (saveresult.equals("Data has been saved.")) {
                this.setError("Data has been saved");
                this.setSubCode("");
                this.setSubGroupCode("");
                this.setSubSubGroupCode("");
                this.setSubSubSubGroupCode("");
                this.setDescription("");
                this.setNewSubCode("");
                this.setNewSubSubCode("");
                this.setNewSubSubSubCode("");
                if (disableSubCode == false) {
                    this.setSubCodeText("");
                    this.setSubCodeDesc("");
                }
                if (disableSubSubCode == false) {
                    this.setSubSubCodeText("");
                    setSubSubCodeDesc("");
                }
                if (disableSubSubSubCode == false) {
                    this.setSubSubSubCodeAcc("");
                    this.setSubSubSubCodeDesc("");
                }
            } else {
                setError(saveresult);
            }
            this.setError(saveresult);
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void onChange() {
        this.setError("");
    }

    public void descriptionFunc() {
        try {
            this.setError("");
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck = p.matcher(this.getDescription());
            if (!billNoCheck.matches()) {
                this.setError("Enter valid character for description...");
                this.setDescription("");
                return;
            }
        } catch (Exception e) {
            setError(e.getMessage());
        }
    }

    public void subGroupCodeFunc() {
        try {
            this.setError("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getSubGroupCode());
            if (!billNoCheck.matches()) {
                this.setError("Enter the valid numeric value for sub group code Text field...");
                this.setSubGroupCode("");
                return;
            }
            if (Integer.parseInt(this.subGroupCode) > 0 && Integer.parseInt(this.subSubGroupCode) <= 0) {
                disableSubCode = false;
                disableSubSubCode = true;
                this.setSubSubCodeText("");
                setSubSubCodeDesc("");
                this.setSubCode("");
                disableSubSubSubCode = true;
                this.setSubSubSubCodeType("");
                this.setSubSubSubCodeAcc("");
                this.setSubSubSubCodeDesc("");
            } else if (Integer.parseInt(this.subGroupCode) > 0 && Integer.parseInt(this.subSubGroupCode) > 0) {
                if (Integer.parseInt(this.subSubSubGroupCode) > 0) {
                    disableSubCode = true;
                    this.setCode("");
                    this.setSubCodeText("");
                    this.setSubCodeDesc("");
                    disableSubSubCode = true;
                    this.setSubSubCodeText("");
                    setSubSubCodeDesc("");
                    this.setSubCode("");
                    disableSubSubSubCode = false;
                } else {
                    disableSubCode = true;
                    this.setCode("");
                    this.setSubCodeText("");
                    this.setSubCodeDesc("");
                    disableSubSubCode = false;
                    disableSubSubSubCode = true;
                    this.setSubSubSubCodeType("");
                    this.setSubSubSubCodeAcc("");
                    this.setSubSubSubCodeDesc("");
                }
            }
        } catch (Exception e) {
            setError(e.getMessage());
        }
    }

    public void subSubGroupCodeFunc() {
        try {
            this.setError("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getSubSubGroupCode());
            if (!billNoCheck.matches()) {
                this.setError("Enter the valid  numeric value for Sub Group Code Text field...");
                this.setSubSubGroupCode("");
                return;
            }
            if (Integer.parseInt(this.subSubGroupCode) > 0 && Integer.parseInt(this.subSubSubGroupCode) <= 0) {
                disableSubCode = true;
                this.setCode("");
                this.setSubCodeText("");
                this.setSubCodeDesc("");
                disableSubSubCode = false;
                disableSubSubSubCode = true;
                this.setSubSubSubCodeType("");
                this.setSubSubSubCodeAcc("");
                this.setSubSubSubCodeDesc("");
            } else if (Integer.parseInt(this.subSubGroupCode) > 0 && Integer.parseInt(this.subSubSubGroupCode) > 0) {
                disableSubCode = true;
                this.setCode("");
                this.setSubCodeText("");
                this.setSubCodeDesc("");
                disableSubSubCode = true;
                this.setSubSubCodeText("");
                setSubSubCodeDesc("");
                this.setSubCode("");
                disableSubSubSubCode = false;
            }
        } catch (Exception e) {
            setError(e.getMessage());
        }
    }

    public void subSubSubGroupCodeFunc() {
        try {
            this.setError("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getSubSubSubGroupCode());
            if (!billNoCheck.matches()) {
                this.setError("Enter  the  valid  numeric  value for Sub Group Code Text field...");
                this.setSubSubSubGroupCode("");
                return;
            }
            if (Integer.parseInt(this.subSubSubGroupCode) > 0) {
                disableSubCode = true;
                this.setCode("");
                this.setSubCodeText("");
                this.setSubCodeDesc("");
                disableSubSubCode = true;
                this.setSubSubCodeText("");
                setSubSubCodeDesc("");
                this.setSubCode("");
                disableSubSubSubCode = false;
            } else {
                disableSubSubSubCode = true;
                this.setSubSubSubCodeType("");
                this.setSubSubSubCodeAcc("");
                this.setSubSubSubCodeDesc("");
                if (Integer.parseInt(this.subSubGroupCode) > 0) {
                    disableSubCode = true;
                    this.setCode("");
                    this.setSubCodeText("");
                    this.setSubCodeDesc("");
                    disableSubSubCode = false;
                } else {
                    if (Integer.parseInt(this.subGroupCode) > 0) {
                        disableSubSubCode = true;
                        this.setSubSubCodeText("");
                        setSubSubCodeDesc("");
                        this.setSubCode("");
                        disableSubCode = false;
                    } else {
                        disableSubCode = true;
                        this.setCode("");
                        this.setSubCodeText("");
                        this.setSubCodeDesc("");
                        disableSubSubCode = true;
                        this.setSubSubCodeText("");
                        setSubSubCodeDesc("");
                        this.setSubCode("");
                        disableSubSubSubCode = true;
                        this.setSubSubSubCodeType("");
                        this.setSubSubSubCodeAcc("");
                        this.setSubSubSubCodeDesc("");
                    }
                }
            }
        } catch (Exception e) {
            setError(e.getMessage());
        }
    }

    public void refreshForm() {
        try {
            this.setType("");
            this.setGroupCode("");
            this.setSubGroupCode("");
            this.setSubSubGroupCode("");
            this.setDescription("");
            this.setCode("");
            this.setSubCodeText("");
            this.setSubCodeDesc("");
            this.setSubSubCodeText("");
            setSubSubCodeDesc("");
            this.setSubCode("");
            this.setAcType("");
            this.setAcStatus("");
            this.setSubSubSubGroupCode("");
            this.setSubSubSubCodeType("");
            this.setSubSubSubCodeAcc("");
            this.setSubSubSubCodeDesc("");
            this.setNewSubCode("");
            this.setNewSubSubCode("");
            this.setNewSubSubSubCode("");
            this.setError("");
        } catch (Exception e) {
            setError(e.getLocalizedMessage());
        }
    }

    public String btnExit_action() {
        try {
            refreshForm();
        } catch (Exception e) {
            setError(e.getMessage());
        }
        return "case1";
    }
}
