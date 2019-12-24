package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.master.PlMasterGrid;
import com.cbs.facade.master.GlMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public final class PlMaster extends BaseBean {

    GlMasterFacadeRemote glMasterRemote;
    private String errorMessage;
    private String message;
    private String classification;
    private String mode;
    private String codeTy;
    private String grCode;
    private String subGrCode;
    private String code;
   // private String oldCode;
    private String codeDesc;
    private String codeDescTxt;
    private List<SelectItem> classificationList;
    private List<SelectItem> modeList;
    private List<SelectItem> codeList;
    private List<PlMasterGrid> gridDetail;
    int currentRow;
    private PlMasterGrid currentItem = new PlMasterGrid();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private boolean glCodeDesc;
    String acNo;

//    public String getOldCode() {
//        return oldCode;
//    }
//
//    public void setOldCode(String oldCode) {
//        this.oldCode = oldCode;
//    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PlMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(PlMasterGrid currentItem) {
        this.currentItem = currentItem;
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

    public List<PlMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<PlMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isGlCodeDesc() {
        return glCodeDesc;
    }

    public void setGlCodeDesc(boolean glCodeDesc) {
        this.glCodeDesc = glCodeDesc;
    }

    public String getCodeDescTxt() {
        return codeDescTxt;
    }

    public void setCodeDescTxt(String codeDescTxt) {
        this.codeDescTxt = codeDescTxt;
    }

    public PlMaster() {
        try {
            glMasterRemote = (GlMasterFacadeRemote) ServiceLocator.getInstance().lookup("GlMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setErrorMessage("");
            this.setMessage("");
            classificationList = new ArrayList<SelectItem>();
            classificationList.add(new SelectItem("--SELECT--"));
            classificationList.add(new SelectItem("INCOME"));
            classificationList.add(new SelectItem("EXPENDITURE"));
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("--SELECT--"));
            modeList.add(new SelectItem("SINGLE"));
            modeList.add(new SelectItem("CLUBBED"));
//            codeList = new ArrayList<SelectItem>();
//            codeList.add(new SelectItem("--SELECT--"));
//            codeList.add(new SelectItem("GL"));
            codeCombo();
            this.setGlCodeDesc(false);
            resetForm();
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
    
    public void grCodeLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setGlCodeDesc(false);
            this.setCode("");
            this.setSubGrCode("");
            this.setCodeDesc("");
            this.setCodeDescTxt("");
            this.setCodeTy("--SELECT--");
            this.setMode("--SELECT--");
            List grCodeList = new ArrayList();
            
            
            if (this.classification.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Classification");
                return;
            }
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
            if (this.classification.substring(0, 1).equalsIgnoreCase("I")) {
                if (Integer.parseInt(this.grCode) >= 1000) {
                    this.setErrorMessage("Income Group Code Series Should be less than 1000");
                    return;
                }
            } else {
                if (Integer.parseInt(this.grCode) <= 1000) {
                    this.setErrorMessage("Expenditure Group Code Series Should be Greater than 1000");
                    return;
                }
            }
            grCodeList = glMasterRemote.plMasterGrCodeLostFocus(Integer.parseInt(this.grCode), this.classification.substring(0, 1));
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
            subGrCodeList = glMasterRemote.plMasterSubGrCodeLostFocus(Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode));
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
                            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.code.equalsIgnoreCase("") || this.code.length() == 0) {
                this.setErrorMessage("Please Enter Code");
                return;
            }
            Matcher codeCheck = p.matcher(code);
            if (!codeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Code.");
                return;
            }
//            if (this.oldCode == null || this.oldCode.equalsIgnoreCase("") || this.oldCode.length() == 0) {
//                this.setErrorMessage("Please Enter the Account No.");             
//                return;
//            }
//            if (!oldCode.matches("[0-9a-zA-z]*")) {                  
//                    setErrorMessage("Please Enter Valid  Account No.");
//                    return ;
//                }
//            if (oldCode.length() != 8) {
//                setErrorMessage("Account number is not valid.");
//                return;
//            }
//            code = fts.getNewAccountNumber(getOrgnBrCode()+oldCode+"01");
//            if(code.equalsIgnoreCase("A/c number does not exist")){
//                this.setErrorMessage("A/c number does not exist");
//                return;  
//            }else{
//                setCode(code.substring(2,10)); 
//            }
             
            //check in for gl
           if (this.classification.substring(0, 1).equalsIgnoreCase("I")) {
            if (Integer.parseInt(this.code) >= 2500 && Integer.parseInt(this.code) < 3000) {
//                this.setErrorMessage("Income Series out of Range.");
//                return;
            } else {
                this.setErrorMessage("Income Series out of Range.");
                return;
            }
        } else {
            if (Integer.parseInt(this.code) >= 2000 && Integer.parseInt(this.code) < 2500) {
//                this.setErrorMessage("Expenditure Series out of Range.");
//                return;
            } else {
                this.setErrorMessage("Expenditure Series out of Range.");
                return;
            }
        }
              if (Integer.parseInt(this.subGrCode) > 0) {
                int length = code.length();
                int addedZero = 6 - length;
                for (int i = 1; i <= addedZero; i++) {
                    code = "0" + code;
                }
                 acNo = this.codeTy + code;
                List codeNameList = new ArrayList();
                codeNameList = glMasterRemote.plMasterCodeLostFocus(acNo);
                if (!codeNameList.isEmpty()) {
                    Vector recLst = (Vector) codeNameList.get(0);
                    String val = recLst.get(0).toString();
                    this.setCodeDesc(val);
                    if (getCodeDescTxt().equalsIgnoreCase(null) || getCodeDescTxt().equalsIgnoreCase("")) {
                        this.setCodeDescTxt(val);
                    }
                } else {
                    this.setCodeDesc("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<PlMasterGrid>();
        try {
            List resultList = new ArrayList();
            resultList = glMasterRemote.plMasterGridLoad(Integer.parseInt(this.grCode));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    PlMasterGrid detail = new PlMasterGrid();
                    detail.setsNo(Integer.parseInt(ele.get(0).toString()));
                    detail.setGrCode(Integer.parseInt(ele.get(1).toString()));
                    detail.setSubGrCode(Integer.parseInt(ele.get(2).toString()));
                    detail.setCode(ele.get(3).toString());
                    detail.setDescription(ele.get(4).toString());
                    detail.setClassification(ele.get(5).toString());
                    detail.setLastUpdateBy(ele.get(6).toString());
                    detail.setMode(ele.get(7).toString());
                    detail.setTranTime(ele.get(8).toString());
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
            for (PlMasterGrid item : gridDetail) {
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
            this.setCode("");
//            this.setOldCode("");
            this.setSubGrCode("");
            this.setCodeDesc("");
            this.setCodeDescTxt("");
            this.setCodeTy("--SELECT--");
            this.setClassification("--SELECT--");
            this.setMode("--SELECT--");
            this.setGlCodeDesc(false);
            String result = glMasterRemote.plMasterDeleteRecord(currentItem.getsNo(), currentItem.getClassification());
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Record Not Deleted.");
                return;
            }
            gridLoad();
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
            if (this.classification.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Classification");
                return;
            }
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
            if (this.codeTy.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Code Type.");
                return;
            }
            if (this.code.equalsIgnoreCase("") || this.code.length() == 0) {
                this.setErrorMessage("Please Enter Code");
                return;
            }
//            String acnature = fts.getAccountNature(getOrgnBrCode()+code+"01");
//            if(!acnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)){
//                this.setErrorMessage("Account nature does not exist");
//                return;
//            }
            Matcher codeCheck = p.matcher(code);
            if (!codeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Code.");
                return;
            }
            if (this.mode.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please Select Mode.");
                return;
            }
            if (this.codeDesc == null || this.codeDesc.equalsIgnoreCase("") || this.codeDesc.length() == 0) {
                this.setErrorMessage("Code Description Cannot Be Blank.");
                return;
            }
//            if (this.codeDescTxt == null || this.codeDescTxt.equalsIgnoreCase("") || this.codeDescTxt.length() == 0) {
//                this.setErrorMessage("Code Description Cannot Be Blank.");
//                return;
//            }
            if (this.classification.substring(0, 1).equalsIgnoreCase("I")) {
                if (Integer.parseInt(this.grCode) >= 1000) {
                    this.setErrorMessage("Income Group Code Series Should be less than 1000.");
                    return;
                }
            } else {
                if (Integer.parseInt(this.grCode) <= 1000) {
                    this.setErrorMessage("Expenditure Group Code Series Should be Greater than 1000.");
                    return;
                }
            }
            String tmpAcNo;
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                tmpAcNo = "0";
            } else {
                tmpAcNo = this.acNo;
            }
            String result = glMasterRemote.plMasterSaveDetail(Integer.parseInt(this.grCode), Integer.parseInt(this.subGrCode), tmpAcNo, this.codeDesc, this.classification.substring(0, 1), getUserName(), this.mode.substring(0, 1));
            if (result.equals("true")) {
                this.setMessage("Record Saved Successfully.");
                this.setCode("");
//                this.setOldCode("");
                this.setSubGrCode("");
                this.setCodeDesc("");
                this.setCodeDescTxt("");
                this.setCodeTy("--SELECT--");
                this.setClassification("--SELECT--");
                this.setMode("--SELECT--");
                this.setGlCodeDesc(false);
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCode("");
      //  this.setOldCode("");
        this.setGrCode("");
        this.setSubGrCode("");
        this.setCodeDesc("");
        this.setCodeDescTxt("");
        this.setCodeTy("--SELECT--");
        this.setClassification("--SELECT--");
        this.setMode("--SELECT--");
        gridDetail = new ArrayList<PlMasterGrid>();
        this.setGlCodeDesc(false);
    }

    public String exitFrm() {
        resetForm();
        return "case1";
    }
}
