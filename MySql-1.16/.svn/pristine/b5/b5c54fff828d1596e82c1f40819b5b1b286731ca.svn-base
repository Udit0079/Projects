/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.HoGlbMasterGrid;
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
public class HoGLBMaster {

    DailyMasterFacadeRemote glbMast;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private List<SelectItem> typeList;
    private String type;
    private List<SelectItem> codeList;
    private String code;
    private List<SelectItem> acTypeList;
    private String acType;
    private List<SelectItem> acStatusList;
    private String acStatus;
    private String grCode;
    private String subGrCode;
    private String codeAcNo;
    private String codeDesc;
    private String acTypeDesc;
    private HttpServletRequest req;
    private List<HoGlbMasterGrid> gridDetail;
    int currentRow;
    private HoGlbMasterGrid currentItem = new HoGlbMasterGrid();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String newCode;
    private FtsPostingMgmtFacadeRemote ftsBeanRemote;

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
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

    public List<SelectItem> getAcStatusList() {
        return acStatusList;
    }

    public void setAcStatusList(List<SelectItem> acStatusList) {
        this.acStatusList = acStatusList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public List<SelectItem> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<SelectItem> codeList) {
        this.codeList = codeList;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }

    public String getCodeAcNo() {
        return codeAcNo;
    }

    public void setCodeAcNo(String codeAcNo) {
        this.codeAcNo = codeAcNo;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
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

    public HoGlbMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(HoGlbMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<HoGlbMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<HoGlbMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    /** Creates a new instance of Daily_GLB_Master */
    public HoGLBMaster() {
        try {
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            glbMast = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            //setUserName("manager1");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("--SELECT--"));
            typeList.add(new SelectItem("A", "ASSET"));
            typeList.add(new SelectItem("L", "LIABILITIES"));
            acStatusList = new ArrayList<SelectItem>();
            acStatusList.add(new SelectItem("--SELECT--"));
            acStatusList.add(new SelectItem("0", "OPERATIVE"));
            acStatusList.add(new SelectItem("1", "INOPERATIVE"));
            acStatusList.add(new SelectItem("2", "ALL"));
            codeCombo();
            acTypeCombo();
            gridLoad();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void acTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = glbMast.acctTypeCombo();
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acTypeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void codeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = glbMast.codeCombo();
            codeList = new ArrayList<SelectItem>();
            codeList.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    codeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<HoGlbMasterGrid>();
            List resultList = new ArrayList();
            resultList = glbMast.gridDetail(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    HoGlbMasterGrid detail = new HoGlbMasterGrid();
                    detail.setSno(ele.get(0).toString());
                    detail.setGroupCode(ele.get(1).toString());
                    detail.setSubgroupCode(ele.get(2).toString());
                    detail.setCode(ele.get(3).toString());
                    detail.setDescription(ele.get(4).toString());
                    detail.setAcctType(ele.get(5).toString());
                    if (ele.get(6).toString().equalsIgnoreCase("L")) {
                        detail.setGlbacctType("LIABILITIES");
                    } else if (ele.get(6).toString().equalsIgnoreCase("A")) {
                        detail.setGlbacctType("ASSET");
                    } else {
                        detail.setGlbacctType("");
                    }
                    if (ele.get(7).toString().equalsIgnoreCase("0")) {
                        detail.setAcctStatus("OPERATIVE");
                    } else if (ele.get(7).toString().equalsIgnoreCase("1")) {
                        detail.setAcctStatus("INOPERATIVE");
                    } else if (ele.get(7).toString().equalsIgnoreCase("2")) {
                        detail.setAcctStatus("ALL");
                    } else {
                        detail.setAcctStatus("");
                    }
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void subGrCheck() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.type.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Select type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Error!! Enter group code.");
                return;
            }
            Matcher grCodeCheck = p.matcher(grCode);
            if (!grCodeCheck.matches()) {
                this.setErrorMessage("Errro!! Enter valid group code.");
                return;
            }
            if (this.grCode.contains(".")) {
                if (this.grCode.indexOf(".") != this.grCode.lastIndexOf(".")) {
                    this.setErrorMessage("Error!! Invalid group code.Please fill group code correctly.");
                    return;
                } else if (this.grCode.substring(grCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Error!! Fill the group code up to two decimal places.");
                    return;
                }
            }
            if (Integer.parseInt(grCode) < 0) {
                this.setErrorMessage("Error!! Enter valid group code.");
                return;
            }
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Error!! Enter sub group code.");
                return;
            }
            Matcher subGrCodeCheck = p.matcher(subGrCode);
            if (!subGrCodeCheck.matches()) {
                this.setErrorMessage("Error!! Enter valid sub group code.");
                return;
            }
            if (this.subGrCode.contains(".")) {
                if (this.subGrCode.indexOf(".") != this.subGrCode.lastIndexOf(".")) {
                    this.setErrorMessage("Error!! Invalid sub group code.Please fill sub group code correctly.");
                    return;
                } else if (this.subGrCode.substring(subGrCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Error!! Fill the sub group code upto two decimal places.");
                    return;
                }
            }
            if (Integer.parseInt(subGrCode) < 0) {
                this.setErrorMessage("Error!! Enter valid sub group code.");
                return;
            }

            List result = glbMast.subGrCodeChk(this.type, Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode));
            if (!result.isEmpty()) {
                this.setErrorMessage("Error!! Sub group code already exist.");
                this.setSubGrCode("");
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }
//

    public void getAccountDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.codeAcNo.equalsIgnoreCase("") || this.codeAcNo.length() < 8) {
                this.setErrorMessage("Error!! Enter account no should not be less than 8.");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(codeAcNo);
            if (accountNumber.startsWith("A/c")) {
                this.setErrorMessage("Error!!! Entered account number does not exist");
                return;
            } else {
                setErrorMessage("");
                newCode = accountNumber.substring(2, 10);
            }
            List result = glbMast.acNameForBankPurpose(newCode);
            if (result.isEmpty()) {
                this.setErrorMessage("Error!! This GL code does not exists.");
                this.setCodeAcNo("");
                return;
            } else {
                Vector ele = (Vector) result.get(0);
                this.setCodeDesc(ele.get(0).toString());
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void actypeDesc() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.acType.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Select account type.");
                this.setAcTypeDesc("");
                return;
            }
            List result = glbMast.acDesc(this.acType);
            if (result.isEmpty()) {
                this.setErrorMessage("Error!! Select A/C. type correctly.");
                this.setAcTypeDesc("");
                return;
            } else {
                Vector ele = (Vector) result.get(0);
                this.setAcTypeDesc(ele.get(0).toString());
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void delete() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            String result = glbMast.deleteRecord(this.orgnBrCode, currentItem.getSno(), currentItem.getGlbacctType().substring(0, 1));
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Error!! Record not deleted.");
                return;
            }
            gridLoad();
            this.setType("--SELECT--");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            this.setCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
            setNewCode("");
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void saveDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.type.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Select type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Error!! Enter group code.");
                return;
            }
            Matcher grCodeCheck = p.matcher(grCode);
            if (!grCodeCheck.matches()) {
                this.setErrorMessage("Error!! Enter valid group code.");
                return;
            }
            if (this.grCode.contains(".")) {
                if (this.grCode.indexOf(".") != this.grCode.lastIndexOf(".")) {
                    this.setErrorMessage("Error!! Invalid Group code. Please fill Group code correctly.");
                    return;
                } else if (this.grCode.substring(grCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Error!! Fill the Group code up to two decimal places.");
                    return;
                }
            }
            if (Integer.parseInt(grCode) < 0) {
                this.setErrorMessage("Error!! Enter valid Group code.");
                return;
            }
            if (this.type.equalsIgnoreCase("L") && Integer.parseInt(grCode) >= 1000) {
                this.setErrorMessage("Error!! Liabilities Group code series should be less than 1000");
                return;
            } else if (this.type.equalsIgnoreCase("A") && Integer.parseInt(grCode) <= 1000) {
                this.setErrorMessage("Error!! Assests Group code series should be greater than 1000");
                return;
            }
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Error!! Enter sub Group code.");
                return;
            }
            Matcher subGrCodeCheck = p.matcher(subGrCode);
            if (!subGrCodeCheck.matches()) {
                this.setErrorMessage("Error!! Enter valid Sub Group code.");
                return;
            }
            if (this.subGrCode.contains(".")) {
                if (this.subGrCode.indexOf(".") != this.subGrCode.lastIndexOf(".")) {
                    this.setErrorMessage("Error!! Invalid Sub group code.");
                    return;
                } else if (this.subGrCode.substring(subGrCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Error!! Enter Sub Group code up to two decimal places.");
                    return;
                }
            } // 
            if (Integer.parseInt(subGrCode) < 0) {
                this.setErrorMessage("Error!! Enter valid Sub group code.");
                return;
            }
            if (this.newCode.equalsIgnoreCase("") || this.newCode.length() < 8) {
                this.setErrorMessage("Error!! Code length cannot be less than 8");
                return;
            }

            if (this.acStatus.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Select account status");
                return;
            }
            if (this.acType.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Select account type");
                return;
            }

            String result = glbMast.saveRecord(this.orgnBrCode, Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode), newCode, this.codeDesc, this.acType, this.type, Integer.parseInt(this.acStatus), this.userName);
            if (result.equals("true")) {
                this.setMessage("Data Saved Successfully.");
            } else {
                this.setErrorMessage("Error!! Data not saved.");
                return;
            }
            gridLoad();
            this.setType("--SELECT--");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            this.setCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
            setNewCode("");
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setType("--SELECT--");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            this.setCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
            setNewCode("");
            gridLoad();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public void initialReset() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            this.setCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
            setNewCode("");
            gridLoad();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
        }
        return "case1";
    }
}
