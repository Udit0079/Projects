package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.web.pojo.master.GlbMasterGrid;
import com.cbs.facade.master.GlMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class DailyGlbMaster extends BaseBean {
    
    GlMasterFacadeRemote glMasterRemote;
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
    private String oldCodeAcNo;
    private String grCode;
    private String subGrCode;
    private String newAcno;
    private String codeDesc;
    private String acTypeDesc;
    private List<GlbMasterGrid> gridDetail;
    private GlbMasterGrid currentItem = new GlbMasterGrid();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private DailyMasterFacadeRemote dailyFacade;
    
    public String getNewAcno() {
        return newAcno;
    }
    
    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }
    
    public String getOldCodeAcNo() {
        return oldCodeAcNo;
    }
    
    public void setOldCodeAcNo(String oldCodeAcNo) {
        this.oldCodeAcNo = oldCodeAcNo;
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
    
    public GlbMasterGrid getCurrentItem() {
        return currentItem;
    }
    
    public void setCurrentItem(GlbMasterGrid currentItem) {
        this.currentItem = currentItem;
    }
    
    public List<GlbMasterGrid> getGridDetail() {
        return gridDetail;
    }
    
    public void setGridDetail(List<GlbMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }
    
    public DailyGlbMaster() {
        try {
            glMasterRemote = (GlMasterFacadeRemote) ServiceLocator.getInstance().lookup("GlMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
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
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void acTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = glMasterRemote.glbMasterAcctTypeCombo();
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acTypeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void codeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = glMasterRemote.glbMasterCodeCombo();
            codeList = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    codeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void gridLoad() {
        try {
            gridDetail = new ArrayList<GlbMasterGrid>();
            List resultList = new ArrayList();
            resultList = glMasterRemote.glbMasterGridDetail();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    GlbMasterGrid detail = new GlbMasterGrid();
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
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void subGrCheck() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.type.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Type not selected");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Error!! Group Code not entered");
                return;
            }
            Matcher grCodeCheck = p.matcher(grCode);
            if (!grCodeCheck.matches()) {
                this.setErrorMessage("Error!! Enter Valid Group Code.");
                return;
            }
            if (this.grCode.contains(".")) {
                if (this.grCode.indexOf(".") != this.grCode.lastIndexOf(".")) {
                    this.setErrorMessage("Error!! Invalid Group Code.Fill Group Code correctly.");
                    return;
                } else if (this.grCode.substring(grCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Group Code Upto Two Decimal Places.");
                    return;
                }
            }
            if (Integer.parseInt(grCode) < 0) {
                this.setErrorMessage("Please Enter Valid Group Code.");
                return;
            }
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Please Enter Sub Group Code.");
                return;
            }
            Matcher subGrCodeCheck = p.matcher(subGrCode);
            if (!subGrCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Sub Group Code.");
                return;
            }
            if (this.subGrCode.contains(".")) {
                if (this.subGrCode.indexOf(".") != this.subGrCode.lastIndexOf(".")) {
                    this.setErrorMessage("Invalid Sub Group Code.Please Fill Sub Group Code Correctly.");
                    return;
                } else if (this.subGrCode.substring(subGrCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Sub Group Code Upto Two Decimal Places.");
                    return;
                }
            }
            if (Integer.parseInt(subGrCode) < 0) {
                this.setErrorMessage("Please Enter Valid Sub Group Code.");
                return;
            }
            List result = glMasterRemote.glbMasterSubGrCodeChk(this.type, Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode));
            if (!result.isEmpty()) {
                this.setErrorMessage("Sub Group Code Already Exist.");
                this.setSubGrCode("");
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void getAccountDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            
            if (this.oldCodeAcNo == null || this.oldCodeAcNo.equalsIgnoreCase("") || this.oldCodeAcNo.length() == 0) {
                this.setErrorMessage("Error!! Enter GL Code no.");
                return;
            }
            if (!oldCodeAcNo.matches("[0-9a-zA-z]*")) {
                setErrorMessage("Error!! Enter valid GL Code no.");
                return;
            }
            if (oldCodeAcNo.length() != 8) {
                setErrorMessage("Error!! GL Code number should be of 8 digit");
                return;
            }
            String actNumber = newAcno = fts.getNewAccountNumberForHo(oldCodeAcNo);
            if (actNumber.startsWith("A/c")) {
                this.setErrorMessage("Error!!! Entered GL Code does not exist");
                setNewAcno("");
                return;
            } else {
                setErrorMessage("");
                newAcno = actNumber.substring(2, 10);
            }
            
            String acnature = fts.getActNatureFor8DigitGLCode(newAcno);
            if (!acnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                setErrorMessage("Error!!! Entered GL Code does not exist");
                return;
            }
            dailyFacade = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            List result = dailyFacade.acNameForBankPurpose(newAcno);
            if (result.isEmpty()) {
                this.setErrorMessage("This GL Code Does Not Exists.");
                this.setNewAcno("");
                this.setOldCodeAcNo("");
                return;
            } else {
                Vector ele = (Vector) result.get(0);
                this.setCodeDesc(ele.get(0).toString());
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void actypeDesc() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.acType.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Error!! Select Account type.");
                this.setAcTypeDesc("");
                return;
            }
            List result = glMasterRemote.glbMasterAcDesc(this.acType);
            if (result.isEmpty()) {
                this.setErrorMessage("Error!! Select A/C. type correctly.");
                this.setAcTypeDesc("");
                return;
            } else {
                Vector ele = (Vector) result.get(0);
                this.setAcTypeDesc(ele.get(0).toString());
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void delete() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            String result = glMasterRemote.glbMasterDeleteRecord(currentItem.getSno(), currentItem.getGlbacctType().substring(0, 1));
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Error!! Record Not Deleted.");
                return;
            }
            gridLoad();
            this.setType("--SELECT--");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            this.setNewAcno("");
            this.setOldCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void saveDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.type.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Please Enter Group Code.");
                return;
            }
            Matcher grCodeCheck = p.matcher(grCode);
            if (!grCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Group Code.");
                return;
            }
            if (this.grCode.contains(".")) {
                if (this.grCode.indexOf(".") != this.grCode.lastIndexOf(".")) {
                    this.setErrorMessage("Invalid Group Code.Please Fill Group Code Correctly.");
                    return;
                } else if (this.grCode.substring(grCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Group Code Upto Two Decimal Places.");
                    return;
                }
            }
            if (Integer.parseInt(grCode) < 0) {
                this.setErrorMessage("Please Enter Valid Group Code.");
                return;
            }
            if (this.type.equalsIgnoreCase("L") && Integer.parseInt(grCode) >= 1000) {
                this.setErrorMessage("Liabilities Group Code Series Should be less than 1000");
                return;
            } else if (this.type.equalsIgnoreCase("A") && Integer.parseInt(grCode) <= 1000) {
                this.setErrorMessage("Assests Group Code Series Should be Greater than 1000");
                return;
            }
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Please Enter Sub Group Code.");
                return;
            }
            Matcher subGrCodeCheck = p.matcher(subGrCode);
            if (!subGrCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Sub Group Code.");
                return;
            }
            if (this.subGrCode.contains(".")) {
                if (this.subGrCode.indexOf(".") != this.subGrCode.lastIndexOf(".")) {
                    this.setErrorMessage("Invalid Sub Group Code.Please Fill Sub Group Code Correctly.");
                    return;
                } else if (this.subGrCode.substring(subGrCode.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Sub Group Code Upto Two Decimal Places.");
                    return;
                }
            }
            if (Integer.parseInt(subGrCode) < 0) {
                this.setErrorMessage("Please Enter Valid Sub Group Code.");
                return;
            }
            if (this.newAcno.equalsIgnoreCase("") || this.newAcno.length() == 0) {
                this.setErrorMessage("Please Enter Code No.");
                return;
            }
            if (this.newAcno.equalsIgnoreCase("") || this.newAcno.length() == 0) {
                this.setErrorMessage("Please Enter the Code No.");
                return;
            }
            Matcher codeAcNoCheck = p.matcher(newAcno);
            if (!codeAcNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Code No.");
                return;
            }
            
            String actNo = newAcno;
            if (this.codeDesc.equalsIgnoreCase("") || this.codeDesc.length() == 0) {
                this.setErrorMessage("Code Description Could Not Be Null");
                return;
            }
            if (this.acStatus.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Account Status");
                return;
            }
            if (this.acType.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Account Type");
                return;
            }
            String result = glMasterRemote.glbMasterSaveRecord(Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode), actNo, this.codeDesc, this.acType, this.type, Integer.parseInt(this.acStatus), getUserName(), getOrgnBrCode());
            if (result.equals("true")) {
                this.setMessage("Data Saved Successfully.");
            } else {
                this.setErrorMessage("Error!! Data Not Saved.");
                return;
            }
            gridLoad();
            this.setType("--SELECT--");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            setNewAcno("");
            this.setOldCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setType("--SELECT--");
            this.setAcType("--SELECT--");
            this.setAcStatus("--SELECT--");
            setNewAcno("");
            this.setOldCodeAcNo("");
            this.setCodeDesc("");
            this.setAcTypeDesc("");
            this.setGrCode("");
            this.setSubGrCode("");
            gridLoad();
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
