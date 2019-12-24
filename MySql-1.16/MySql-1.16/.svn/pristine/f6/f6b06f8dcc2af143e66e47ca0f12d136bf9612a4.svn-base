/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.txn.PoDetailsItem;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.</p>
 *
 * @version XferAuthorization.java
 * @version Created on 29 Jan 2011
 * @author Dhirendra Singh
 */
public class PoAuthorization extends BaseBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private String acType;
    private String message;
    private String errorMsg;
    private int currentRow;
    private PoDetailsItem poDataItem;
    private List<PoDetailsItem> poDetailsItemList;
    private List<SelectItem> acTypeOption;
    private final String jndiHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public PoDetailsItem getPoDataItem() {
        return poDataItem;
    }

    public void setPoDataItem(PoDetailsItem poDataItem) {
        this.poDataItem = poDataItem;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public List<SelectItem> getAcTypeOption() {
        return acTypeOption;
    }

    public void setAcTypeOption(List<SelectItem> acTypeOption) {
        this.acTypeOption = acTypeOption;
    }

    public List<PoDetailsItem> getPoDetailsItemList() {
        return poDetailsItemList;
    }

    public void setPoDetailsItemList(List<PoDetailsItem> poDetailsItemList) {
        this.poDetailsItemList = poDetailsItemList;
    }

    // </editor-fold>
    /**
     * <p>
     * Construct a new Page bean instance.</p>
     */
    public PoAuthorization() {
        try {
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getAcTypeDetail();

        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getAcTypeDetail() {
        try {
            acTypeOption = new ArrayList<SelectItem>();
            List resultList = txnAuthRemote.getAcType();
            if (resultList.isEmpty()) {
                throw new ApplicationException("No data found in BillTypeMaster table.");
            }
            acTypeOption.add(new SelectItem("0", "--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector element = (Vector) resultList.get(i);
                acTypeOption.add(new SelectItem(element.elementAt(0), element.elementAt(0).toString()));
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getUnAuthPoDetails() {
        try {
            poDetailsItemList = new ArrayList<PoDetailsItem>();
            if (this.getAcType().equals("0") || this.getAcType().equals("")) {
                throw new ApplicationException("Please select Remittance Authorization");
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            List resultList = txnAuthRemote.getUnAuthPoDetails(ymd.format(new Date()), getOrgnBrCode(), this.getAcType());
            if (resultList.isEmpty()) {
                throw new ApplicationException("Voucher does not pending for authorization.");
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector element = (Vector) resultList.get(i);
                PoDetailsItem poDetailsItem = new PoDetailsItem();
                poDetailsItem.setsNo(String.valueOf(i + 1));

                if (element.elementAt(0) != null) {
                    poDetailsItem.setInstNo(element.elementAt(0).toString());
                }
                if (element.elementAt(1) != null) {
                    poDetailsItem.setSeqNo(Float.parseFloat(element.elementAt(1).toString()));
                }
                if (element.elementAt(2) != null) {
                    poDetailsItem.setBillType(element.elementAt(2).toString());
                }
                if (element.elementAt(3) != null) {
                    poDetailsItem.setAcNo(element.elementAt(3).toString());
                }
                if (element.elementAt(4) != null) {
                    poDetailsItem.setCustName(element.elementAt(4).toString());
                }

                if (element.elementAt(5) != null) {
                    poDetailsItem.setPayableAt(element.elementAt(5).toString());
                }
                if (element.elementAt(6) != null) {
                    poDetailsItem.setAmount(Double.parseDouble(element.elementAt(6).toString()));
                }
                if (element.elementAt(7) != null) {
                    poDetailsItem.setInfavourOf(element.elementAt(7).toString());
                }
                if (element.elementAt(8) != null) {
                    poDetailsItem.setStatus(element.elementAt(8).toString());
                }

                if (element.elementAt(9) != null) {
                    poDetailsItem.setEnterBy(element.elementAt(9).toString());
                }
                if (element.elementAt(10) != null) {
                    poDetailsItem.setAuth(element.elementAt(10).toString());
                }
                if (element.elementAt(11) != null) {
                    poDetailsItem.setRecNo(Float.parseFloat(element.elementAt(11).toString()));
                }
                poDetailsItemList.add(poDetailsItem);
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void changeValue() {
        try {
            PoDetailsItem item = poDetailsItemList.get(currentRow);
            if (item.getAuth().equalsIgnoreCase("N")) {
                boolean found = false;
                for (PoDetailsItem item1 : poDetailsItemList) {
                    if (item1.getAuth().equalsIgnoreCase("Y")) {
                        found = true;
                    }
                }
                if (found) {
                    throw new ApplicationException("Only one transction can be authorize at one time.");
                }
                item.setAuth("Y");
                poDetailsItemList.remove(currentRow);
                poDetailsItemList.add(currentRow, item);
            } else if (item.getAuth().equalsIgnoreCase("Y")) {
                item.setAuth("N");
                poDetailsItemList.remove(currentRow);
                poDetailsItemList.add(currentRow, item);
                this.setErrorMsg("");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void authorizePoDetail() {
        PoDetailsItem authItem = new PoDetailsItem();
        for (PoDetailsItem item : poDetailsItemList) {
            if (item.getAuth().equalsIgnoreCase("Y")) {
                authItem = item;
            }
        }
        try {
            this.setErrorMsg("");
            this.setMessage("");
            if (this.getUserName().equalsIgnoreCase(authItem.getEnterBy())) {
                throw new ApplicationException("Please login with the different user.");
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = txnAuthRemote.poAuthorization(authItem.getRecNo(), authItem.getAcNo(), authItem.getSeqNo(),
                    authItem.getBillType(), authItem.getAmount(), authItem.getStatus(), ymd.format(new Date()), getUserName(), getOrgnBrCode());
            this.setMessage(result);
            getUnAuthPoDetails();
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        this.setAcType("0");
        poDetailsItemList = new ArrayList<PoDetailsItem>();
        this.setErrorMsg("");
        this.setMessage("");
    }

    public String btnExitAction() {
        this.setAcType("0");
        getUnAuthPoDetails();
        return "case1";
    }
}