/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.AccStatusEnum;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.txn.CashCr;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class SBNCashDepositDeclaration extends BaseBean {

    public SBNCashDepositDeclaration() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            getListValues();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getListValues() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("A", "Add"));
           // functionList.add(new SelectItem("M", "Modify"));
            functionList.add(new SelectItem("D", "Delete"));
            functionList.add(new SelectItem("V", "Verify"));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void funcAction() {
        setAcNo("");
        setMessage("");
        refreshObj();
        if (this.getFunction().equalsIgnoreCase("A")) {
            this.setBtnLbl("Save");
            this.setBtnPopUp("Are You Sure To Save The Detail");
            
            this.setGridStyle("none");
            setFocusId("txtacNo");
            setReadable(false);
        } else if (this.getFunction().equalsIgnoreCase("D")) {
            this.setBtnLbl("Delete");
            this.setBtnPopUp("Are You Sure To Delete The Detail");
          
            this.setGridStyle("block");
            getUnauthDetails();
            
            setReadable(true);
            setFocusId("btnLabel");
        } else if (this.getFunction().equalsIgnoreCase("V")) {
            this.setBtnLbl("Verify");
            this.setBtnPopUp("Are You Sure To Verify The Detail");
            this.setGridStyle("block");
            getUnauthDetails();
            setReadable(true);
            setFocusId("btnLabel");
        }
    }

    public void accNoLostFocus() {
        try {
            if(function.equals("A")){
                refreshObj();
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
                if (this.acNo == null || this.acNo.equals("") || this.acNo.trim().length() != 12) {
                    throw new ApplicationException("Please fill A/c No.");
                }
                Matcher acNoCheck = p3.matcher(this.acNo);
                if (!acNoCheck.matches()) {
                    throw new ApplicationException("Please fill A/c No.");
                }
                newAcno = ftsRemote.getNewAccountNumber(acNo);

                acNature = ftsRemote.getAccountNature(this.newAcno);

                if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    throw new ApplicationException("GL Entry is not allowed");
                }
                String msg = ftsRemote.ftsDateValidate(ymd.format(new Date()), getOrgnBrCode());
                if (!msg.equalsIgnoreCase("TRUE")) {
                    throw new ApplicationException(msg);
                }
                List rsList = txnRemote.getAccountDetails(acNo, acNature);
                Vector vect = (Vector) rsList.get(0);
                int st = Integer.parseInt(vect.get(8).toString());
                if (st == 9) {
                    throw new ApplicationException(AccStatusEnum.getStatusMsg(st));
                }

                this.setCustId(vect.get(9).toString());
                this.setAcName(vect.get(1).toString());

                this.setJtName(vect.get(2).toString() + " " + vect.get(3).toString() + " " + vect.get(4).toString() + " " + vect.get(5).toString());
                this.setOperMode(vect.get(6).toString());
                this.setOpeningDt(vect.get(7).toString());

                this.setStatus(AccStatusEnum.getStatusMsg(st));
            }
        } catch (Exception e) {
            setNewAcno("");
            setAcNo("");
            setMessage(e.getMessage());
        }
    }

    public void getUnauthDetails() {
        try {
            refreshObj();
            List rsList = txnRemote.getUnauthDetails(getOrgnBrCode());
            itemList = new ArrayList<CashCr>();
            CashCr item;
            for (int i = 0; i < rsList.size(); i++) {
                Vector vect = (Vector) rsList.get(i);
                item = new CashCr();
                item.setsNo(vect.get(0).toString());
                item.setAcNo(vect.get(1).toString());
                
                item.setCustName(vect.get(2).toString());
                item.setInstAmt(Double.parseDouble(vect.get(3).toString()));
                
                item.setDetails(vect.get(4).toString());
                item.setEnterBy(vect.get(5).toString());

                item.setOrgn_br_code(vect.get(6).toString());
                item.setDest_br_code(vect.get(7).toString());
                itemList.add(item);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void processData() {
        try {
            setAcNo(currentItem.getAcNo());
            setNewAcno(currentItem.getAcNo());
            acNature = ftsRemote.getAccountNature(this.newAcno);
            List rsList = txnRemote.getAccountDetails(acNo, acNature);
            Vector vect = (Vector) rsList.get(0);
            int st = Integer.parseInt(vect.get(8).toString());
            if (st == 9) {
                throw new ApplicationException(AccStatusEnum.getStatusMsg(st));
            }

            this.setCustId(vect.get(9).toString());
            this.setAcName(vect.get(1).toString());

            this.setJtName(vect.get(2).toString() + " " + vect.get(3).toString() + " " + vect.get(4).toString() + " " + vect.get(5).toString());
            this.setOperMode(vect.get(6).toString());
            this.setOpeningDt(vect.get(7).toString());

            this.setStatus(AccStatusEnum.getStatusMsg(st));
            setAmt(currentItem.getInstAmt());
            setDetails(currentItem.getDetails());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void saveDetails() {
        try {
            String result = validateDetail();
            if (!result.equals("true")) {
                throw new ApplicationException(result);
            }
            result = "";
            if (function.equals("A")) {
                result = txnRemote.processSBNCashDepostDecl(function, newAcno, details, getUserName(), amt, getOrgnBrCode(), "", 0);
            } else {
                result = txnRemote.processSBNCashDepostDecl(function, currentItem.getAcNo(), currentItem.getDetails(), currentItem.getEnterBy(),
                        currentItem.getInstAmt(), currentItem.getOrgn_br_code(), getUserName(), Long.parseLong(currentItem.getsNo()));
            }
            if (result.equalsIgnoreCase("true")) {
                String resMsg = "";
                if (this.getFunction().equalsIgnoreCase("A")) {
                    resMsg = "Data Inserted Successfully";
                } else if (this.getFunction().equalsIgnoreCase("V")) {
                    resMsg = "Data Verified Successfully";
                } else if (this.getFunction().equalsIgnoreCase("D")) {
                    resMsg = "Data Deleted Successfully";
                }
                refresh();
                this.setMessage(resMsg);
            }
            
        } catch (Exception e) {
            //refresh();
            setMessage(e.getMessage());
        }

    }

    public String validateDetail() {
        Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
        Pattern p5 = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        String result = "true";

        try {
            //Validate Account No Field
            if (this.newAcno == null || this.newAcno.equals("") || this.newAcno.trim().length() != 12) {
                return "Please fill A/c No.";
            }
            Matcher acNoCheck = p3.matcher(this.newAcno);
            if (!acNoCheck.matches()) {
                return "Invalid A/c No";
            }
            /**
             * ******** * Validate Amount Field ********
             */
            if (this.amt <= 0 ) {
                return "Amount must be greater than zero";
            }

            if (details.equals("")) {
                return "Please fill the proper Declaration";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }

        return result;
    }

    public void refreshObj() {
        this.setNewAcno("");
        this.setCustId("");
        this.setAcName("");
        this.setJtName("");
        this.setOperMode("");
        this.setOpeningDt("");
        this.setDetails("");
        this.setAmt(0);
    }

    public void refresh() {
        this.setFunction("A");
        this.setAcNo("");
        this.setNewAcno("");
        this.setCustId("");
        this.setAcName("");
        this.setJtName("");
        this.setOperMode("");
        this.setOpeningDt("");
        this.setDetails("");
        this.setAmt(0);
        this.setBtnLbl("Save");
        this.setBtnPopUp("");
        this.setMessage("");
        this.setGridStyle("none");
        itemList = new ArrayList<CashCr>();
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String acNo;
    private String newAcno;
    private String custId;
    private String acName;
    private String jtName;
    private String operMode;
    private String openingDt;
    private String status;
    private double amt;
    private String focusId;
    private boolean readable;
    private String details;
    private String acNature;
    private String btnLbl = "Save";
    private String btnPopUp;
    private String gridStyle;
    private CashCr currentItem;
    private List<CashCr> itemList;

    NumberFormat formatter = new DecimalFormat("#0.00");
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private TransactionManagementFacadeRemote txnRemote;

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }
    
    public String getGridStyle() {
        return gridStyle;
    }

    public void setGridStyle(String gridStyle) {
        this.gridStyle = gridStyle;
    }

    public CashCr getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CashCr currentItem) {
        this.currentItem = currentItem;
    }

    public List<CashCr> getItemList() {
        return itemList;
    }

    public void setItemList(List<CashCr> itemList) {
        this.itemList = itemList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getBtnLbl() {
        return btnLbl;
    }

    public void setBtnLbl(String btnLbl) {
        this.btnLbl = btnLbl;
    }

    public String getBtnPopUp() {
        return btnPopUp;
    }

    public void setBtnPopUp(String btnPopUp) {
        this.btnPopUp = btnPopUp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
