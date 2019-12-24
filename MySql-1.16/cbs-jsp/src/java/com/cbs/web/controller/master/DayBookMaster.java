package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.GlMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.DayBookMasterGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public final class DayBookMaster extends BaseBean {

    private GlMasterFacadeRemote glMasterRemote;
    private String errorMessage;
    // private String oldCode;
    private String message;
    private String acType;
    private String acctType;
    private String acctStatus;
    private String grCode;
    private String subGrCode;
    private String code;
    //private String codeDesc;
    private String codeDescTxt;
    private String frGlHead;
    private String toGlHead;
    private String acTypeDesc;
    private List<SelectItem> acTypeList;
    private List<SelectItem> acctTypeList;
    private List<SelectItem> acctStatusList;
    private List<DayBookMasterGrid> gridDetail;
    private int currentRow;
    private DayBookMasterGrid currentItem = new DayBookMasterGrid();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private boolean glCodeDesc;
    private String acNo;
    private int optStatus;

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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public List<SelectItem> getAcctStatusList() {
        return acctStatusList;
    }

    public void setAcctStatusList(List<SelectItem> acctStatusList) {
        this.acctStatusList = acctStatusList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDescTxt() {
        return codeDescTxt;
    }

    public void setCodeDescTxt(String codeDescTxt) {
        this.codeDescTxt = codeDescTxt;
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

    public String getFrGlHead() {
        return frGlHead;
    }

    public void setFrGlHead(String frGlHead) {
        this.frGlHead = frGlHead;
    }

    public String getToGlHead() {
        return toGlHead;
    }

    public void setToGlHead(String toGlHead) {
        this.toGlHead = toGlHead;
    }

    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }

    public List<DayBookMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<DayBookMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public DayBookMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(DayBookMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isGlCodeDesc() {
        return glCodeDesc;
    }

    public void setGlCodeDesc(boolean glCodeDesc) {
        this.glCodeDesc = glCodeDesc;
    }

    public DayBookMaster() {
        try {
            glMasterRemote = (GlMasterFacadeRemote) ServiceLocator.getInstance().lookup("GlMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setErrorMessage("");
            this.setMessage("");
            getAcctTypeValues();
            codeCombo();
            acctStatusList = new ArrayList<SelectItem>();
            acctStatusList.add(new SelectItem("--Select--"));
            acctStatusList.add(new SelectItem("Operative"));
            acctStatusList.add(new SelectItem("InOperative"));
            acctStatusList.add(new SelectItem("All"));
            this.setGlCodeDesc(false);
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
            acTypeList = new ArrayList<SelectItem>();
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

    public void getAcctTypeValues() {
        try {
            List resultList = new ArrayList();
            resultList = glMasterRemote.acctTypeDayBook();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem(" "));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctTypeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void grCodeLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            List grCodeList = new ArrayList();
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Please Enter Group Code");
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
            this.setGlCodeDesc(false);
            grCodeList = glMasterRemote.grCodeLostFocusDayBook(Integer.parseInt(this.grCode));
            if (!grCodeList.isEmpty()) {
                Vector recLst = (Vector) grCodeList.get(0);
                String val = recLst.get(0).toString();
                Integer val1 = Integer.parseInt(val) + 1;
                this.setSubGrCode(val1.toString());
                gridLoad();
            } else {
                this.setSubGrCode("1");
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void subGrCodeLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Please Enter Group Code");
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
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Please Enter Sub Group Code");
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
            List subGrCodeList = new ArrayList();
            subGrCodeList = glMasterRemote.subGrCodeLostFocusDayBook(Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode));
            if (!subGrCodeList.isEmpty()) {
                Vector recLst = (Vector) subGrCodeList.get(0);
                String val = recLst.get(0).toString();
                this.setGlCodeDesc(true);
                this.setCodeDescTxt(val);
            } else {
                this.setCodeDescTxt("");
                this.setGlCodeDesc(false);
                return;
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void codeLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please First Select Account Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.code.equalsIgnoreCase("")) {
                this.setErrorMessage("Please Enter Code");
                return;
            }
            Matcher codeCheck = p.matcher(code);
            if (!codeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Code.");
                return;
            }
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Please Enter Sub Group Code");
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
            if (Integer.parseInt(this.subGrCode) > 0 && this.code.equalsIgnoreCase("")) {
                this.setErrorMessage("Please Enter Code");
                return;
            }
            if (Integer.parseInt(this.subGrCode) > 0) {
                if (Integer.parseInt(this.code) > 0) {
                    int length = code.length();
                    int addedZero = 6 - length;
                    for (int i = 1; i <= addedZero; i++) {
                        code = "0" + code;
                    }
                    acNo = this.acType + code;
                    List codeNameList = new ArrayList();
                    codeNameList = glMasterRemote.codeLostFocusDayBook(acNo);
                    if (!codeNameList.isEmpty()) {
                        Vector recLst = (Vector) codeNameList.get(0);
                        String val = recLst.get(0).toString();
//                        this.setCodeDesc(val);
                        if (getCodeDescTxt().equalsIgnoreCase(null) || getCodeDescTxt().equalsIgnoreCase("")) {
                            this.setCodeDescTxt(val);
                        }
                    } else {
                        this.setErrorMessage("Account No. Does Not Exist.");
                        this.setCode("");
                        this.setCodeDescTxt("");
                        return;
                    }
                } else {
                    acNo = "0";
                }
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void frGlHeadLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.frGlHead.equalsIgnoreCase("") || this.frGlHead.length() == 0) {
                this.setErrorMessage("Please Enter GL Head");
                return;
            }
            Matcher frCodeCheck = p.matcher(frGlHead);
            if (!frCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid From GL Head.");
                return;
            }
            int length = frGlHead.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                frGlHead = "0" + frGlHead;
            }
            List frHeadList = glMasterRemote.frGlHeadLostFocusDayBook(frGlHead);
            if (frHeadList.isEmpty()) {
                this.setErrorMessage("GL HEAD DOES NOT EXISTS.");
                this.setFrGlHead("");
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void toGlHeadLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.frGlHead.equalsIgnoreCase("") || this.frGlHead.length() == 0) {
                this.setErrorMessage("Please Enter GL Head");
                return;
            }
            Matcher frCodeCheck = p.matcher(frGlHead);
            if (!frCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid From GL Head.");
                return;
            }
            if (this.toGlHead.equalsIgnoreCase("") || this.toGlHead.length() == 0) {
                this.setErrorMessage("Please Enter GL Head");
                return;
            }
            Matcher toCodeCheck = p.matcher(toGlHead);
            if (!toCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid To GL Head.");
                return;
            }
            if (Integer.parseInt(frGlHead) > Integer.parseInt(toGlHead)) {
                this.setErrorMessage("Please Enter Valid GlHead Range.");
                return;
            }
            int length = toGlHead.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                toGlHead = "0" + toGlHead;
            }
            List frHeadList = glMasterRemote.toGlHeadLostFocusDayBook(toGlHead);
            if (frHeadList.isEmpty()) {
                this.setErrorMessage("GL HEAD DOES NOT EXISTS.");
                this.setToGlHead("");
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void acctTypeLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type.");
                return;
            }
            List acList = glMasterRemote.acTypeLostFocusDayBook(acctType);
            if (!acList.isEmpty()) {
                Vector recLst = (Vector) acList.get(0);
                String val = recLst.get(0).toString();
                this.setAcTypeDesc(val);
            } else {
                this.setAcTypeDesc("");
                return;
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<DayBookMasterGrid>();
            List resultList = new ArrayList();
            resultList = glMasterRemote.gridLoadDayBook(Integer.parseInt(this.grCode));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    DayBookMasterGrid detail = new DayBookMasterGrid();
                    detail.setsNo(Integer.parseInt(ele.get(0).toString()));
                    detail.setGrCode(Integer.parseInt(ele.get(1).toString()));
                    detail.setSubGrCode(Integer.parseInt(ele.get(2).toString()));
                    detail.setCode(ele.get(3).toString());
                    detail.setDescription(ele.get(4).toString());
                    detail.setFrCode(ele.get(5).toString());
                    detail.setToCode(ele.get(6).toString());
                    detail.setAcType(ele.get(7).toString());
                    detail.setAcStatus(ele.get(8).toString());
                    detail.setLastUpdateBy(ele.get(9).toString());
                    detail.setTranTime(ele.get(10).toString());
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

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String sno = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (DayBookMasterGrid item : gridDetail) {
                if (item.getsNo() == Integer.parseInt(sno)) {
                    currentItem = item;
                }
            }
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            String result = glMasterRemote.deleteRecordDayBook(currentItem.getAcType(), currentItem.getDescription(), currentItem.getCode());
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Record Not Deleted.");
                return;
            }
            this.setCode("");
            this.setCodeDescTxt("");
            this.setFrGlHead("");
            this.setToGlHead("");
            this.setAcTypeDesc("");
            this.setAcType("--Select--");
            this.setAcctType(" ");
            this.setAcctStatus("--Select--");
            gridLoad();
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String tmpAcNo;
            String tmpFrAcNo;
            String tmpToAcNo;
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.grCode.equalsIgnoreCase("") || this.grCode.length() == 0) {
                this.setErrorMessage("Please Enter Group Code");
                return;
            }
            Matcher grCodeCheck = p.matcher(grCode);
            if (!grCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Group Code.");
                return;
            }
            if (this.subGrCode.equalsIgnoreCase("") || this.subGrCode.length() == 0) {
                this.setErrorMessage("Please Enter Sub Group Code");
                return;
            }
            Matcher subGrCodeCheck = p.matcher(subGrCode);
            if (!subGrCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Sub Group Code.");
                return;
            }
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type.");
                return;
            }
            if (this.code.equalsIgnoreCase("")) {
                this.setErrorMessage("Please Enter Code");
                return;
            }
            Matcher codeCheck = p.matcher(code);
            if (!codeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Code.");
                return;
            }
            if (this.frGlHead.equalsIgnoreCase("") || this.frGlHead.length() == 0) {
                this.setErrorMessage("Please Enter GL Head");
                return;
            }
            Matcher frCodeCheck = p.matcher(frGlHead);
            if (!frCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid From GL Head.");
                return;
            }
            if (this.toGlHead.equalsIgnoreCase("") || this.toGlHead.length() == 0) {
                this.setErrorMessage("Please Enter GL Head");
                return;
            }
            Matcher toCodeCheck = p.matcher(toGlHead);
            if (!toCodeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid To GL Head.");
                return;
            }
            if (Integer.parseInt(frGlHead) > Integer.parseInt(toGlHead)) {
                this.setErrorMessage("Please Enter Valid GlHead Range.");
                return;
            }
            if (this.acctStatus.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Status.");
                return;
            }
            if (this.acctStatus.substring(0, 1).equalsIgnoreCase("I")) {
                optStatus = 2;
            } else if (this.acctStatus.substring(0, 1).equalsIgnoreCase("O")) {
                optStatus = 1;
            } else {
                optStatus = 0;
            }
            int length = frGlHead.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                frGlHead = "0" + frGlHead;
            }
            int length1 = toGlHead.length();
            int addedZero1 = 6 - length1;
            for (int i = 1; i <= addedZero1; i++) {
                toGlHead = "0" + toGlHead;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                tmpAcNo = "0";
                optStatus = 0;
            } else {
                tmpAcNo = this.acNo;
            }
            String frAcNo = this.acType + this.frGlHead;
            String toAcNo = this.acType + this.toGlHead;
            if (frAcNo.equalsIgnoreCase("") || frAcNo.length() == 0 || toAcNo.equalsIgnoreCase("") || toAcNo.length() == 0) {
                tmpFrAcNo = tmpAcNo;
                tmpToAcNo = tmpAcNo;
            } else {
                tmpFrAcNo = frAcNo;
                tmpToAcNo = toAcNo;
            }
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                optStatus = 0;
            }
            if (Integer.parseInt(this.grCode) < 0) {
                this.setErrorMessage("Invalid Group Code");
                return;
            }
            if (Integer.parseInt(this.subGrCode) < 0) {
                this.setErrorMessage("Invalid Sub Group Code");
                return;
            }
            if (this.codeDescTxt == null || this.codeDescTxt.equalsIgnoreCase("") || this.codeDescTxt.length() == 0) {
                this.setErrorMessage("Account Type Description Cannot Be Blank.");
                return;
            }
//            if (this.codeDesc == null || this.codeDesc.equalsIgnoreCase("") || this.codeDesc.length() == 0) {
//                this.setErrorMessage("Account Type Description Cannot Be Empty.");
//                return;
//            }
            String result = glMasterRemote.saveDetailDayBook(Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode), tmpAcNo, this.codeDescTxt, this.acctType, this.optStatus, tmpFrAcNo, tmpToAcNo, getUserName(), this.codeDescTxt);
            if (result.equals("true")) {
                this.setMessage("Record Saved Successfully.");
                this.setCode("");
//                this.setOldCode("");
//                this.setCodeDesc("");
                this.setCodeDescTxt("");
                this.setFrGlHead("");
                this.setToGlHead("");
                this.setAcTypeDesc("");
                this.setAcType("--Select--");
                this.setAcctType(" ");
                this.setAcctStatus("--Select--");
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setGrCode("");
            this.setSubGrCode("");
            this.setCode("");
            // this.setOldCode("");
//            this.setCodeDesc("");
            this.setCodeDescTxt("");
            this.setFrGlHead("");
            this.setToGlHead("");
            this.setAcTypeDesc("");
            this.setAcType("--Select--");
            this.setAcctType("--Select--");
            this.setAcctStatus("--Select--");
            gridDetail = new ArrayList<DayBookMasterGrid>();
            this.setGlCodeDesc(false);
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        resetForm();
        return "case1";
    }
}
