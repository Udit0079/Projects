/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.admin.TdLienMarkingGrid;
import com.cbs.facade.admin.TDLienMarkingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class TermDepositeLienMarking  extends BaseBean{

    TDLienMarkingFacadeRemote lienMark;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String errorMessage;
    private String message;
    private String acctType;
    private String acctNo;
    private String oldAcctNo;
    private String custName;
    private String jtName;
    private String oprMode;
    private String type;
    private List<SelectItem> typeList;
    private String lienMarkOption;
    private List<SelectItem> lienMarkOptionList;
    private List<SelectItem> acctTypeList;
    int currentRow;
    private List<TdLienMarkingGrid> gridDetail;
    private TdLienMarkingGrid currentItem = new TdLienMarkingGrid();
    private HttpServletRequest req;
    private String recieptNo;
    private String controlNo;
    private String prinAmount;
    private String detail;
    private String status;
    private String auth, acNoLen;
    private boolean flag1;

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

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOprMode() {
        return oprMode;
    }

    public void setOprMode(String oprMode) {
        this.oprMode = oprMode;
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

    public String getLienMarkOption() {
        return lienMarkOption;
    }

    public void setLienMarkOption(String lienMarkOption) {
        this.lienMarkOption = lienMarkOption;
    }

    public List<SelectItem> getLienMarkOptionList() {
        return lienMarkOptionList;
    }

    public void setLienMarkOptionList(List<SelectItem> lienMarkOptionList) {
        this.lienMarkOptionList = lienMarkOptionList;
    }

    public TdLienMarkingGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdLienMarkingGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TdLienMarkingGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TdLienMarkingGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getPrinAmount() {
        return prinAmount;
    }

    public void setPrinAmount(String prinAmount) {
        this.prinAmount = prinAmount;
    }

    public String getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(String recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Date date = new Date();

    /** Creates a new instance of TermDepositeLienMarking */
    public TermDepositeLienMarking() {
        try {
            lienMark = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup("TDLienMarkingFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("--Select--"));
            typeList.add(new SelectItem("0", "COLLATERAL"));
            typeList.add(new SelectItem("1", "PRIMARY"));
            lienMarkOptionList = new ArrayList<SelectItem>();
            lienMarkOptionList.add(new SelectItem("--Select--"));
            lienMarkOptionList.add(new SelectItem("Yes"));
            lienMarkOptionList.add(new SelectItem("No"));
            this.setFlag1(true);
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void customerDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCustName("");
        this.setOprMode("");
        this.setJtName("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatus("");
        this.setFlag1(true);
        gridDetail = new ArrayList<TdLienMarkingGrid>();
        try {
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            //if (this.oldAcctNo.length() < 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("Please Enter Account No Carefully.");
                return;
            }
             acctNo=ftsPostRemote.getNewAccountNumber(oldAcctNo);
             String actNature=ftsPostRemote.getAccountNature(acctNo);
             if (!(actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                this.setErrorMessage("Please Enter Account No. Of 'FD' Nature.");
                return;
            }
            
            List resultList = lienMark.customerDetail(this.acctNo);
            List resultList1 = lienMark.gridDetailLoad(this.acctNo, actNature);
            String authResult = lienMark.auth(this.getUserName(), this.getOrgnBrCode());
            this.setAuth(authResult);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCustName(ele.get(0).toString());
                    this.setOprMode(ele.get(1).toString());
                    this.setJtName(ele.get(2).toString());
                }
            } else {
                this.setErrorMessage("Account No. Does Not Exists !!!");
                return;
            }
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    TdLienMarkingGrid dt = new TdLienMarkingGrid();
                    dt.setAcNo(ele.get(0).toString());
                    dt.setVoucherNo(ele.get(1).toString());
                    dt.setReciept(ele.get(2).toString());
                    dt.setPrintAmt(ele.get(3).toString());
                    dt.setFddt(ele.get(4).toString());
                    dt.setMatDt(ele.get(5).toString());
                    dt.setTdmatDt(ele.get(6).toString());
                    dt.setIntOpt(ele.get(7).toString());
                    dt.setRoi(ele.get(8).toString());
                    dt.setStatus(ele.get(9).toString());
                    dt.setSeqNo(ele.get(10).toString());
                    if (ele.get(11).toString().equalsIgnoreCase("Y")) {
                        dt.setLien("Yes");
                    } else {
                        dt.setLien("No");
                    }

                    gridDetail.add(dt);
                }
            } else {
                this.setErrorMessage("No Voucher Exists in This Account No.");
                return;
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatus("");
        this.setDetail("");
        this.setLienMarkOption("--Select--");
        try {
            this.setControlNo(currentItem.getSeqNo());
            this.setRecieptNo(currentItem.getReciept());
            this.setStatus(currentItem.getLien());
            if (currentItem.getLien().equalsIgnoreCase("Yes")) {
                this.setLienMarkOption("No");
            } else {
                this.setLienMarkOption("Yes");
            }

            String result = lienMark.tdLienPresentAmount(this.acctNo, Float.parseFloat(currentItem.getVoucherNo()), Float.parseFloat(currentItem.getPrintAmt()));
            if (result == null) {
                this.setErrorMessage("PROBLEM IN GETTING PRESENT AMOUNT !!!");
                this.setFlag1(true);
                return;
            } else {
                int n = result.indexOf("*");
                this.setDetail(result.substring(0, n));
                this.setPrinAmount(result.substring(n + 1));
                this.setFlag1(false);
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }
    String Details = "";
    String DLAccOpen_Lien = "";
    String BillLcBg_Lien = "";
    String loanLienCall = "";

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        String lAcNO = "";
        try {
            String tmpSecType = "";
            if (this.type.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Type.");
                return;
            }

            if (this.type.equalsIgnoreCase("1")) {
                tmpSecType = "P";
            } else if (this.type.equalsIgnoreCase("0")) {
                tmpSecType = "C";
            }
            if (this.lienMarkOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Lien Marking.");
                return;
            }
            
            if (this.lienMarkOption.equalsIgnoreCase("Yes")) {
                List list = ftsPostRemote.autoPayLienStatus(currentItem.getAcNo(), Double.parseDouble(this.recieptNo));
                if(!list.isEmpty()){
                    Vector autoVec = (Vector) list.get(0);
                    String autoPay = autoVec.get(1).toString();
                    if(autoPay.equalsIgnoreCase("Y")){
                        setErrorMessage("This Receipt is Marked for Auto Payment, So Can not be Lien.");
                        return;
                    }
                }
            }
            
            List resultList1 = new ArrayList();
            gridDetail = new ArrayList<TdLienMarkingGrid>();
            
            String acctCode=ftsPostRemote.getAccountCode(currentItem.getAcNo());
//            if (!ftsPostRemote.getCurrentBrnCode(currentItem.getAcNo()).equalsIgnoreCase(getOrgnBrCode())) {
//                setErrorMessage("This is not your branch's account no.");
//                return;
//            }
            String result = lienMark.saveLienMarkingDetail(Float.parseFloat(this.recieptNo), Float.parseFloat(currentItem.getVoucherNo()), acctCode, currentItem.getAcNo(), lAcNO, this.lienMarkOption, this.auth, this.getUserName(), this.detail, this.loanLienCall, tmpSecType, DLAccOpen_Lien, BillLcBg_Lien, getOrgnBrCode());
            String acCode=ftsPostRemote.getAccountCode(acctNo);
            resultList1 = lienMark.gridDetailLoad(this.acctNo, acCode);
            if (result == null) {
                this.setErrorMessage("PROBLEM IN SAVE THE RECORD !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                } else {
                    this.setMessage(result);
                }
            }
            this.setFlag1(true);
            this.setRecieptNo("");
            this.setControlNo("");
            this.setPrinAmount("");
            this.setStatus("");
            this.setLienMarkOption("--Select--");
            this.setDetail("");
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    TdLienMarkingGrid dt = new TdLienMarkingGrid();
                    dt.setAcNo(ele.get(0).toString());
                    dt.setVoucherNo(ele.get(1).toString());
                    dt.setReciept(ele.get(2).toString());
                    dt.setPrintAmt(ele.get(3).toString());
                    dt.setFddt(ele.get(4).toString());
                    dt.setMatDt(ele.get(5).toString());
                    dt.setTdmatDt(ele.get(6).toString());
                    dt.setIntOpt(ele.get(7).toString());
                    dt.setRoi(ele.get(8).toString());
                    dt.setStatus(ele.get(9).toString());
                    dt.setSeqNo(ele.get(10).toString());
                    if (ele.get(11).toString().equalsIgnoreCase("Y")) {
                        dt.setLien("Yes");
                    } else {
                        dt.setLien("No");
                    }

                    gridDetail.add(dt);
                }
            } else {
                this.setErrorMessage("No Voucher Exists in This Account No.");
                return;
            }

        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCustName("");
        this.setAcctNo("");
        oldAcctNo="";
        this.setJtName("");
        this.setOprMode("");
        this.setType("--Select--");
        this.setRecieptNo("");
        this.setControlNo("");
        this.setPrinAmount("");
        this.setStatus("");
        this.setLienMarkOption("--Select--");
        this.setDetail("");
        this.setFlag1(true);
        gridDetail = new ArrayList<TdLienMarkingGrid>();
    }


    public String exitBtnAction(){
        resetForm();
        return "case1";
    }
}
