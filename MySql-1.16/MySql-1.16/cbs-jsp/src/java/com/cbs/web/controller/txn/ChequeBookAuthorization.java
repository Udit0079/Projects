/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.ChBookAuthorization;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class ChequeBookAuthorization extends BaseBean{

    private String message;
    private String authType;
    
    private int currentRow;
    private String finalMsg;
    private boolean disableAuth;
    
    private List<ChBookAuthorization> chBookList;
    private List<SelectItem> authTypeList;
    Date date = new Date();
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
   // private ChBookAuthorization currentItem = new ChBookAuthorization();
    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;

    public String getFinalMsg() {
        return finalMsg;
    }

    public void setFinalMsg(String finalMsg) {
        this.finalMsg = finalMsg;
    }

    public boolean isDisableAuth() {
        return disableAuth;
    }

    public void setDisableAuth(boolean disableAuth) {
        this.disableAuth = disableAuth;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ChBookAuthorization> getChBookList() {
        return chBookList;
    }

    public void setChBookList(List<ChBookAuthorization> chBookList) {
        this.chBookList = chBookList;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public List<SelectItem> getAuthTypeList() {
        return authTypeList;
    }

    public void setAuthTypeList(List<SelectItem> authTypeList) {
        this.authTypeList = authTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChequeBookAuthorization() {
        try {
            chBookList = new ArrayList<ChBookAuthorization>();
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            setMessage("");
            setFinalMsg("");
            getTableValues();
            chbkAuthTableValues();
            this.setDisableAuth(true);
            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableValues() {
        try {
            authTypeList = new ArrayList<SelectItem>();
            authTypeList.add(new SelectItem("0", "ALL Values"));
            authTypeList.add(new SelectItem("1", "GL"));
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    }

    public void chbkAuthTableValues() {
        try {
            chBookList = new ArrayList<ChBookAuthorization>();
            List resultList = new ArrayList();
            if (authType == null) {
                resultList = otherAuthRemote.chBkTable(getOrgnBrCode(), ymd.format(sdf.parse(getTodayDate())), "0");
            } else {
                resultList = otherAuthRemote.chBkTable(getOrgnBrCode(), ymd.format(sdf.parse(getTodayDate())), authType);
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector tableVector = (Vector) resultList.get(i);
                ChBookAuthorization mt = new ChBookAuthorization();
                mt.setAcno(tableVector.get(0).toString());
                mt.setCustName(tableVector.get(1).toString());
                
                mt.setChqSeries(Integer.parseInt(tableVector.get(2).toString()));
                mt.setChqNoFrom(Integer.parseInt(tableVector.get(3).toString()));
                mt.setChqNoTo(Integer.parseInt(tableVector.get(4).toString()));
                
                mt.setIssuedBy(tableVector.get(5).toString());
                mt.setNoOfLeaf(Integer.parseInt(tableVector.get(6).toString()));
                mt.setAuth(tableVector.get(7).toString());
                mt.setChargeFlag(tableVector.get(8).toString());
                mt.setCustState(tableVector.get(9).toString());
                mt.setBranchState(tableVector.get(10).toString());
                
                chBookList.add(mt);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeStatus() {
        setFinalMsg("");
        setMessage("");
        try {
            ChBookAuthorization item = chBookList.get(currentRow);
            if (item.getAuth().equalsIgnoreCase("N")) {
                if (item.getIssuedBy().equalsIgnoreCase(getUserName())) {
                    this.setMessage("You cannot authorize your own entry");
                    this.setDisableAuth(true);
                }else{
                    boolean found1 = false;
                    for (ChBookAuthorization item1 : chBookList) {
                        if (item1.getAuth().equalsIgnoreCase("Y")) {
                            found1 = true;
                        }
                    }
                    if (found1) {
                        this.setMessage("Only one transction can be authorize at one time.");
                    } else {
                        item.setAuth("Y");
                        chBookList.remove(currentRow);
                        chBookList.add(currentRow, item);
                        setDisableAuth(false);
                    }
                }
            } else if (item.getAuth().equalsIgnoreCase("Y")) {
                item.setAuth("N");
                chBookList.remove(currentRow);
                chBookList.add(currentRow, item);
                this.setDisableAuth(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    public void saveAction() {
        setMessage("");
        setFinalMsg("");
        try {
            for (ChBookAuthorization item : chBookList) {
                if (item.getAuth().equalsIgnoreCase("Y")) {
                    String msg = otherAuthRemote.authCheckIssue(item, getUserName());
                    if (msg.equalsIgnoreCase("True")) {
                        setFinalMsg("Authorization successfull");
                    } else if(msg.length() > 4 && msg.substring(0,4).equalsIgnoreCase("True")) {
                        setFinalMsg("Authorization successfull and Batch number for Cheque charges is :"+msg.substring(4));
                    }else{
                        setMessage("System errror occurred");
                    }
                }
            }
            this.setDisableAuth(true);
            chbkAuthTableValues();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        setAuthType("0");
        setMessage("");
        setFinalMsg("");
        chbkAuthTableValues();
    }

    public String exitBtnAction() {
        refresh();
        return "case1";
    }
}
