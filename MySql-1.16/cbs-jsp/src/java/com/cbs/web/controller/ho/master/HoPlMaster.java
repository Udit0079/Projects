/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.HoGridPlMaster;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sudhir Kr Bisht
 */
@ViewScoped
public class HoPlMaster {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String classification;
    private String mode;
    private String codeTy;
    private String grCode;
    private String subGrCode;
    private String code;
    private String codeDesc;
    private HttpServletRequest req;
    private List<SelectItem> classificationList;
    private List<SelectItem> modeList;
    private List<SelectItem> codeList;
    private CrrDailyEntryFacadeRemote plmast;
    private int currentRow;
    private Vector Vector1;
    private String acno;
    private String acname;
    private List<HoGridPlMaster> gridData;
    private HoGridPlMaster plgrid;
    private String groupDesc;
    private HoGridPlMaster authorized;
    private Iterator<HoGridPlMaster> Iterator1;
    private boolean save;
    private boolean disableCode;
    private boolean disableGroupDesc;
    private String acno2;
    private String msg = "";
    private String newCode;
    private DailyMasterFacadeRemote glbMast;

    public boolean isDisableGroupDesc() {
        return disableGroupDesc;
    }

    public void setDisableGroupDesc(boolean disableGroupDesc) {
        this.disableGroupDesc = disableGroupDesc;
    }

    public boolean isDisableCode() {
        return disableCode;
    }

    public void setDisableCode(boolean disableCode) {
        this.disableCode = disableCode;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public String getGrCode() {
        return grCode;
    }

    public void setGrCode(String grCode) {
        this.grCode = grCode;
    }

    public String getSubGrCode() {
        return subGrCode;
    }

    public void setSubGrCode(String subGrCode) {
        this.subGrCode = subGrCode;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public Vector getVector1() {
        return Vector1;
    }

    public void setVector1(Vector Vector1) {
        this.Vector1 = Vector1;
    }

    public HoGridPlMaster getAuthorized() {
        return authorized;
    }

    public void setAuthorized(HoGridPlMaster authorized) {
        this.authorized = authorized;
    }

    public Iterator<HoGridPlMaster> getIterator1() {
        return Iterator1;
    }

    public void setIterator1(Iterator<HoGridPlMaster> Iterator1) {
        this.Iterator1 = Iterator1;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public HoGridPlMaster getPlgrid() {
        return plgrid;
    }

    public void setPlgrid(HoGridPlMaster plgrid) {
        this.plgrid = plgrid;
    }

    public String getAcname() {
        return acname;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SelectItem> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<SelectItem> classificationList) {
        this.classificationList = classificationList;
    }

    public List<SelectItem> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<SelectItem> codeList) {
        this.codeList = codeList;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCodeTy() {
        return codeTy;
    }

    public void setCodeTy(String codeTy) {
        this.codeTy = codeTy;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public List<HoGridPlMaster> getGridData() {
        return gridData;
    }

    public void setGridData(List<HoGridPlMaster> gridData) {
        this.gridData = gridData;
    }

   

    /** Creates a new instance of PlMaster */
    //
    public HoPlMaster() {
        req = getRequest();
        try {
            gridData = new ArrayList<HoGridPlMaster>();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            plmast = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");

            classificationList = new ArrayList<SelectItem>();
            classificationList.add(new SelectItem("--SELECT--"));
            classificationList.add(new SelectItem("I", "INCOME"));
            classificationList.add(new SelectItem("E", "EXPENDITURE"));
            modeList = new ArrayList<SelectItem>();

            modeList.add(new SelectItem("--SELECT--"));
            modeList.add(new SelectItem("S", "SINGLE"));
            modeList.add(new SelectItem("C", "CLUBBED"));
            codeList = new ArrayList<SelectItem>();

            getTableData();
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void SubCode() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getGrCode());
            if (!billNoCheck.matches()) {
                this.setErrorMessage("Error!! Enter the valid value for group code...");
                this.setGrCode("");
                this.setSubGrCode("");
                return;
            }
            String classify = this.classification;
            if (String.valueOf(this.grCode).equalsIgnoreCase("") || String.valueOf(this.grCode).equalsIgnoreCase(null)) {
                this.setErrorMessage("Error!! Enter group code.");
                return;
            }
            if (classify.equalsIgnoreCase("I")) {
                if (Double.parseDouble(this.grCode) >= 1000 || Double.parseDouble(this.grCode) <= 0) {
                    this.setErrorMessage("Error!! Income should be between 1 to 999");
                    return;
                }
            }
            if (classify.equalsIgnoreCase("E")) {
                if (Double.parseDouble(this.grCode) <= 1000 || Double.parseDouble(this.grCode) >= 2000) {
                    this.setErrorMessage("Error!! Expenditure should be  between 1000 to 2000");
                    return;
                }
            }
            this.setErrorMessage("");

            List list1 = null;

            list1 = plmast.getSubGroupCode(classify, Integer.parseInt(this.getGrCode()));
            Vector vec1 = (Vector) list1.get(0);
            if (Integer.parseInt(vec1.get(0).toString()) == 999999) {
                this.setSubGrCode(String.valueOf(1));
                this.setGroupDesc("");
                this.setCode("");
                this.setAcname("");
                this.setCode("");
                disableCode = true;
                disableGroupDesc = false;
            } else {
                disableCode = false;
                setCode("");
                this.setGroupDesc("");
                int subcode = Integer.parseInt(vec1.get(0).toString());
                subcode = subcode + 1;
                disableGroupDesc = true;
                this.setSubGrCode(String.valueOf(subcode));
                String grpdesc = plmast.getGroupDescription(Integer.parseInt(this.getGrCode()), Integer.parseInt(this.getSubGrCode()));
                this.setGroupDesc(grpdesc);
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public void getAcNo() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getCode());
            this.setErrorMessage("");
            String acode = this.getCode();

            String classify = this.classification;
            if (classify.equalsIgnoreCase("I")) {
                if (Long.parseLong(acode.substring(4, 8)) < 2501 || Long.parseLong(acode.substring(4, 8)) > 3000) {
                    this.setErrorMessage("Error!! Income code should be between 2500 to 3001");
                    return;
                }
            }
            if (classify.equalsIgnoreCase("E")) {
                if (Long.parseLong(acode.substring(4, 8)) <= 2000 || Long.parseLong(acode.substring(4, 8)) >= 2501) {
                    this.setErrorMessage("Error!! Expenditure code Should be  between 2000 to 2501");
                    return;
                }
            }
            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(acode);
            if (accountNumber.contains("Account Number does not exist")) {
                this.setErrorMessage("Error!!! Entered account number does not exist");
                this.setCode("");
                this.setNewCode("");
                this.setAcname("");
                return;
            } else {
                setErrorMessage("");
                newCode = accountNumber.substring(2, 10);
            }
            glbMast = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            List list2 = glbMast.acNameForBankPurpose(newCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setErrorMessage("");
                this.setAcname(acname1);
            } else {
                this.setErrorMessage("Error!! Invalid account number.");
                this.setCode("");
                this.setAcname("");
                return;
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public String Validations() {
        try {
            if (classification.equals("--SELECT--")) {
                return "Error!! Select the classification option..";
            }
            if (mode.equals("--SELECT--")) {
                return "Error!! Select the mode option..";
            }
            if (getGrCode().equals("")) {
                return "Error!! Group code cannot be empty..";
            }
            if (getSubGrCode().equals("")) {
                return "Error!! Sub group code cannot be empty...";
            }
            if (disableGroupDesc == false) {
                Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
                if (groupDesc.equalsIgnoreCase("")) {
                    return "Error!! Description field cannot be empty";
                }
                Matcher billNoCheck = p.matcher(this.getGroupDesc());
                if (!billNoCheck.matches()) {
                    return "Error!! Enter the valid text for decsription..";
                } else {
                    this.setErrorMessage("");
                }
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getGrCode());
            if (!billNoCheck.matches()) {
                return "Error!! Enter the valid value for Group Code..";
            }
            Matcher billNoCheck1 = p.matcher(this.getSubGrCode());
            if (!billNoCheck1.matches()) {
                return "Error!! Enter the valid value for Sub Group Code..";
            }
            Matcher billNoCheck3 = p.matcher(this.getCode());
            if (!billNoCheck.matches()) {
                return "Error!! Enter the valid numeric value for account code..";
            }

            Pattern p1 = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            if (disableCode == false) {
                if (newCode.equalsIgnoreCase("")) {
                    return "Error!! Sub code is not present..";
                }
                if (newCode.length() < 8) {
                    return "Error!! Entered account no code length cannot be less tan 8";
                }
                if (getAcname().equals("")) {
                    return "Error!! Account name text field could not be empty..";
                }
                Matcher billNoCheck2 = p1.matcher(this.getAcname());
                if (!billNoCheck1.matches()) {
                    return "Error!! Enter valid characters for the Account name..";
                }
            }
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
            return errorMessage;
        }
        return "true";
    }

    public void onMode() {
        setErrorMessage("");
    }

    public void getTableData() {
        try {
            gridData = new ArrayList<HoGridPlMaster>();
            List resultset = plmast.selectFincompMast();
            if (!resultset.isEmpty()) {
                for (int i = 0; i < resultset.size(); i++) {
                    plgrid = new HoGridPlMaster();
                    Vector vec = (Vector) resultset.get(i);
                    plgrid.setGroupcode(Integer.parseInt(vec.get(1).toString()));
                    plgrid.setSubgroupcode(Integer.parseInt(vec.get(2).toString()));
                    plgrid.setGroupDescriptions(vec.get(3).toString());
                    plgrid.setCode(vec.get(4).toString());
                    plgrid.setSubGrpDesc(vec.get(5).toString());
                    plgrid.setClassfication(vec.get(6).toString());
                    plgrid.setMode(vec.get(7).toString());
                    plgrid.setLastUpdatedBy(vec.get(8).toString());
                    plgrid.setLastUpdatedBy(this.userName);
                    gridData.add(plgrid);
                }
            }
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public void setRowValues() {
        try {
            String result = plmast.deleteFincompPlMast(authorized.getGroupcode(), authorized.getSubgroupcode());
            refresh();
            getTableData();
            setErrorMessage(result);
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public void save() {
        try {
            String valResult = Validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setErrorMessage(valResult);
                return;
            } 
            if (classification.equalsIgnoreCase("I")) {
                if (Double.parseDouble(this.grCode) >= 1000 || Double.parseDouble(this.grCode) <= 0) {
                    this.setErrorMessage("Error!! Income group should be between 1 to 999");
                    return;
                }
            }
            if (classification.equalsIgnoreCase("E")) {
                if (Double.parseDouble(this.grCode) <= 1000 || Double.parseDouble(this.grCode) >= 2000) {
                    this.setErrorMessage("Error!! Expenditure group should be between 1000 to 2000");
                    return;
                }
            }
            if (disableCode == false) {
                if (classification.equalsIgnoreCase("I")) {
                    if (Long.parseLong(getCode().substring(4, 8)) < 2501 || Long.parseLong(getCode().substring(4, 8)) > 3000) {
                        this.setErrorMessage("Error!! Income code should be between 2500 to 3001");
                        return;
                    }
                }
                if (classification.equalsIgnoreCase("E")) {
                    if (Long.parseLong(getCode().substring(4, 8)) <= 2000 || Long.parseLong(getCode().substring(4, 8)) >= 2501) {
                        this.setErrorMessage("Error!! Expenditure code Should be  between 2000 to 2501");
                        return;
                    }
                }
            }
            this.setErrorMessage("");
            String result = null;

            result = plmast.save(classification, Integer.parseInt(getGrCode()), Integer.parseInt(subGrCode), getNewCode(), getGroupDesc(), this.userName, mode, getAcname());
            refresh();
            setErrorMessage(result);
            getTableData();
        } catch (ApplicationException ex) {
            errorMessage = ex.getMessage();
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public void refresh() {
        try {
            this.setErrorMessage("");
            setClassification("--SELECT--");
            setMode("--SELECT--");
            setAcname("");
            setGrCode("");
            setSubGrCode("");
            setGroupDesc("");
            setCodeDesc("");
            setCode("");
            setErrorMessage("");
            setNewCode("");
            getTableData();
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
    }

    public String exitForm() {
        try {
            refresh();
            gridData = new ArrayList<HoGridPlMaster>();
        } catch (Exception e) {
            errorMessage = e.getLocalizedMessage();
        }
        return "case1";
    }
}
