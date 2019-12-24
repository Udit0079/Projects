/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.txn.NPATransactionPojo;
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
 * @author Athar Reza
 */
public class NPATransactions extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String oldAcno;
    private String acNo;
    private String opDt;
    private String acName;
    private String joiName;
    private String custid;
    private String trantype;
    private List<SelectItem> trantypeList;
    private String type;
    private List<SelectItem> typeList;
    private String tranby;
    private List<SelectItem> tranbyList;
    private String relatedto;
    private List<SelectItem> relatedtoList;
    private String date;
    private Date dt = new Date();
    private String details;
    boolean disableVerify;
    boolean disableSave;
    private double amount;
    private String btnLabel;
    private String status, acNoLen;
    private NPATransactionPojo currentItem;
    private List<NPATransactionPojo> tableDataList;
    int currentRow;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableVerify() {
        return disableVerify;
    }

    public void setDisableVerify(boolean disableVerify) {
        this.disableVerify = disableVerify;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public NPATransactionPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NPATransactionPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<NPATransactionPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<NPATransactionPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
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

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getJoiName() {
        return joiName;
    }

    public void setJoiName(String joiName) {
        this.joiName = joiName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getOpDt() {
        return opDt;
    }

    public void setOpDt(String opDt) {
        this.opDt = opDt;
    }

    public String getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(String relatedto) {
        this.relatedto = relatedto;
    }

    public List<SelectItem> getRelatedtoList() {
        return relatedtoList;
    }

    public void setRelatedtoList(List<SelectItem> relatedtoList) {
        this.relatedtoList = relatedtoList;
    }

    public String getTranby() {
        return tranby;
    }

    public void setTranby(String tranby) {
        this.tranby = tranby;
    }

    public List<SelectItem> getTranbyList() {
        return tranbyList;
    }

    public void setTranbyList(List<SelectItem> tranbyList) {
        this.tranbyList = tranbyList;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public List<SelectItem> getTrantypeList() {
        return trantypeList;
    }

    public void setTrantypeList(List<SelectItem> trantypeList) {
        this.trantypeList = trantypeList;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public NPATransactions() {
        try {

            date = dmy.format(dt);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setAcNoLen(getAcNoLength());

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("A", "Add"));

            trantypeList = new ArrayList<SelectItem>();
            trantypeList.add(new SelectItem("2", "Transfer"));

            typeList = new ArrayList<SelectItem>();
            //typeList.add(new SelectItem("0", "Cr"));
            typeList.add(new SelectItem("1", "Dr"));

            tranbyList = new ArrayList<SelectItem>();
            tranbyList.add(new SelectItem("0", "Voucher"));

//            relatedtoList = new ArrayList<SelectItem>();
//            relatedtoList.add(new SelectItem("8", "Interest"));

            relatedtoList = new ArrayList<SelectItem>();
            relatedtoList.add(new SelectItem("S", "--Select--"));
            List l3 = txnRemote.getDropdownDataOnLoad("42");
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                if (v3.get(0).toString().equalsIgnoreCase("3") || v3.get(0).toString().equalsIgnoreCase("8")) {
                    relatedtoList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
                }
            }

            setDisableVerify(true);
            gridLoad();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void verified() {
        setMessage("");
        try {
            if (!tableDataList.isEmpty()) {
                for (int i = 0; i < tableDataList.size(); i++) {
                    NPATransactionPojo pojo = tableDataList.get(i);
                    if (pojo.getAuth().equalsIgnoreCase("Y")) {
                        String result = txnRemote.UpdateNpaAuthStatus(pojo.getAcNo(), getUserName());
                        setMessage(result);
                    } else {
                        setMessage("Please select a row from table to verfy.");
                    }
                    gridLoad();
                }
            } else {
                setMessage("There is no data to verify.");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void gridLoad() {
        try {
            tableDataList = new ArrayList<NPATransactionPojo>();
            List resultList = new ArrayList();
            String ymddt1 = ymd.format(dmy.parse(date));
            resultList = txnRemote.getNpaUnauthData(getOrgnBrCode(), ymddt1);
            int sNo = 0;
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    sNo = sNo + 1;

                    NPATransactionPojo pojo = new NPATransactionPojo();
                    pojo.setsNo(sNo);
                    pojo.setAcNo(ele.get(0).toString());
                    pojo.setTransactionType(ele.get(1).toString());
                    pojo.setType(ele.get(2).toString());
                    double cramt = Double.parseDouble(ele.get(3).toString());
                    double dramt = Double.parseDouble(ele.get(4).toString());
                    if (cramt == 0.0) {
                        pojo.setAmt(dramt);
                    } else {
                        pojo.setAmt(cramt);
                    }
                    // pojo.setAmt(Double.parseDouble(ele.get(3).toString()));
                    pojo.setTxnDate(ele.get(5).toString().substring(8, 10) + "/" + ele.get(5).toString().substring(5, 7) + "/" + ele.get(5).toString().substring(0, 4));
                    pojo.setRecNo(ele.get(7).toString());
                    pojo.setEnterBy(ele.get(8).toString());
                    pojo.setDetail(ele.get(6).toString());
                    pojo.setAuth(ele.get(9).toString());
                    tableDataList.add(pojo);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onBlurAcno() {
        try {
            List custDetailList = null;
            List acnochkList = null;
            setMessage("");
            if (oldAcno == null || oldAcno.equalsIgnoreCase("") || ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                throw new ApplicationException("Please enter proper account number.");
            }
            oldAcno = ftsPostRemote.getNewAccountNumber(oldAcno);
            this.acNo = oldAcno;
            if (!acNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
                throw new ApplicationException("Entry allow only from base branch.");
            }
            String acNature = ftsPostRemote.getAccountNature(oldAcno);
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                acnochkList = txnRemote.getNpaAcnoChechByAcno(acNo);
                if (acnochkList.isEmpty()) {
                    throw new ApplicationException("This Account number does not NPA.");
                }
            } else {
                throw new ApplicationException("Account No not in TL,DL and CA Nature!");
            }
            custDetailList = txnRemote.getCustomerDetailByAcno(acNo);
            if (custDetailList.isEmpty()) {
                throw new ApplicationException("Account does not exits!");
            }
            Vector ele = (Vector) custDetailList.get(0);
            acName = ele.get(1).toString();
            joiName = ele.get(2).toString();
            opDt = ele.get(3).toString().substring(6, 8) + "/" + ele.get(3).toString().substring(4, 6) + "/" + ele.get(3).toString().substring(0, 4);
            status = ele.get(5).toString();

            List<String> custIdList = txnRemote.getCustIdByAccount(acNo);
            if (!custIdList.isEmpty()) {
                this.setCustid(custIdList.get(0));
            } else {
                this.setCustid("");
            }
//            List acNoCck = txnRemote.getAcnoCheckByAcno(acNo);
//            if (!acNoCck.isEmpty()) {
//                throw new ApplicationException("Interest Entry already exist in Memorandam. Please choose another Account.");
//            }
            double npaBal = txnRemote.getNpaBalCheck(acNo, ymd.format(dmy.parse(date)));
            if (npaBal > 0) {
                throw new ApplicationException("Interest Entry already exist in Memorandam. Please choose another Account.");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onBlurAmt() {
        try {
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveNPATransaction() {
        setMessage("");
        try {
            if (!acNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
                throw new ApplicationException("Entry allow only from base branch.");
            }
            if (date.equalsIgnoreCase("")) {
                setMessage("Please fill date!");
                return;
            }

            if (!Validator.validateDate(date)) {
                setMessage("Please select Proper from date ");
                return;
            }

            if (dmy.parse(date).after(getDt())) {
                setMessage("Please date should be less than Login date ");
                return;
            }
            if (relatedto == null || relatedto.equalsIgnoreCase("S")) {
                setMessage("Please select Related To !");
                return;
            }

            if (amount == 0) {
                setMessage("Please fill the Amount!");
                return;
            }
            String ymddt = ymd.format(dmy.parse(date));

            String result = txnRemote.saveNpaTransactionData(acNo, trantype, type, tranby, relatedto, ymddt, amount, details, getUserName(), getOrgnBrCode(), this.status);
            setMessage(result);
            gridLoad();
            clear();

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void changeStatus() {
        setMessage("");
        try {
            setDisableVerify(false);
            setDisableSave(true);
            NPATransactionPojo item = tableDataList.get(currentRow);
            acNo = currentItem.getAcNo();
            if (item.getAuth().equalsIgnoreCase("N")) {
                if (ftsPostRemote.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    if (item.getEnterBy().equals(this.getUserName())) {
                        throw new ApplicationException("You can not verify your own entry");
                    } else {
                        boolean found = false;
                        for (NPATransactionPojo item1 : tableDataList) {
                            if (item1.getAuth().equalsIgnoreCase("Y")) {
                                found = true;
                            }
                        }
                        if (found) {
                            this.setMessage("Only one transction can be verify at one time.");
                        } else {
                            item.setAuth("Y");
                            tableDataList.remove(currentRow);
                            tableDataList.add(currentRow, item);
                        }
                    }
                } else {
                    this.setMessage("You are not authorized person for verifing this detail.");
                }
            } else if (item.getAuth().equalsIgnoreCase("Y")) {
                item.setAuth("N");
                tableDataList.remove(currentRow);
                tableDataList.add(currentRow, item);
            }
            clear();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void deleteUnAuth() {
        setMessage("");
        try {
            String result = txnRemote.deleteNpaDetail(currentItem.getAcNo());
            setMessage(result);
            gridLoad();
            clear();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        this.setAcName("");
        this.setAmount(0.00);
        this.setCustid("");
        this.setDetails("");
        this.setJoiName("");
        this.setOldAcno("");
        this.setAcNo("");
        this.setOpDt("");
        this.setRelatedto("S");
        this.setDate(dmy.format(dt));
        setDisableVerify(true);
        setDisableSave(false);

    }

    public void clear() {
        // setMessage("");
        this.setAcName("");
        this.setAmount(0.00);
        this.setCustid("");
        this.setDetails("");
        this.setJoiName("");
        this.setOldAcno("");
        this.setAcNo("");
        this.setOpDt("");
        this.setRelatedto("S");
        this.setDate(dmy.format(dt));
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
