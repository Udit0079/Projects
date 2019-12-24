
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import javax.faces.model.SelectItem;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class Poddcharges {

    Date date;
    SimpleDateFormat sdf;
    InitialContext ctx;
    BillCommissionFacadeRemote poddchargesremote;
    HttpServletRequest req;
    private String user;
    private String datetoday;
    private String amt;
    private String comm;
    private String sercharge;
    private String msg;
    private String paytype;
    private String mode;
    int modeint;
    double surcharge;
    String commflag;
    private List<SelectItem> modeListOption;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private InterBranchTxnFacadeRemote ibrFacade = null;
    private final String jndiIbrName = "InterBranchTxnFacade";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public List<SelectItem> getModeListOption() {
        return modeListOption;
    }

    public void setModeListOption(List<SelectItem> modeListOption) {
        this.modeListOption = modeListOption;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSercharge() {
        return sercharge;
    }

    public void setSercharge(String sercharge) {
        this.sercharge = sercharge;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDatetoday() {
        return datetoday;
    }

    public void setDatetoday(String datetoday) {
        this.datetoday = datetoday;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * Creates a new instance of Poddcharges
     */
    public Poddcharges() {
        try {
            req = getRequest();
            poddchargesremote = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ibrFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup(jndiIbrName);
            date = new Date();
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            setUser(req.getRemoteUser());
            setDatetoday(sdf.format(date));
            msg = "";
            gettype();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void gettype() {
        try {
            modeListOption = new ArrayList<SelectItem>();
            List resultList = new ArrayList();
            resultList = poddchargesremote.dataList();
            if (resultList.size() > 0) {
                modeListOption.add(new SelectItem("--Select--", "--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(i);
                    modeListOption.add(new SelectItem(element.elementAt(0), element.elementAt(0).toString()));
                }
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void calcul() {
        this.setComm("0.0");
        this.setSercharge("0.0");
        this.setMsg("");
        try {
            if (this.paytype == null || this.paytype.equalsIgnoreCase("--Select--")) {
                this.setMsg("Please select Remittence Type");
                return;
            }
            if (this.mode == null || this.mode.equalsIgnoreCase("--SELECT--")) {
                this.setMsg("Please Select Mode");
                return;
            }
            if (this.amt == null || this.amt.equalsIgnoreCase("")) {
                this.setMsg("Please Enter the Amount");
                return;
            }
            double amt1 = Double.parseDouble(amt);
            if (amt1 < 0) {
                this.setMsg("Please Enter the Valid Amount");
                return;
            }
            Double.parseDouble(amt);
        } catch (Exception e) {
            this.setMsg("Plese Enter Numeric value");
            return;
        }
        try {
            if (this.getMode().equalsIgnoreCase("Cash")) {
                modeint = 0;
            } else if (this.getMode().equalsIgnoreCase("Transfer")) {
                modeint = 1;
            }

            date = new Date();
            sdf = new SimpleDateFormat("yyyy-MM-dd");

            /**
             * change for merging by himanshu
             */
            setComm(String.valueOf(txnRemote.getCommission(ymd.format(date), paytype, modeint, Double.parseDouble(amt.toString()))));
//            resultList = txnRemote.getCommission(date1, type1, modeint);
//            if (!resultList.isEmpty()) {
//                for (int i = 0; i < resultList.size(); i++) {
//                    Vector v = (Vector) resultList.get(i);
//                    if (Double.parseDouble(v.get(1).toString()) != 0) {
//                        if (Double.parseDouble(amt.toString()) >= Double.parseDouble(v.get(0).toString()) && Double.parseDouble(amt.toString()) <= Double.parseDouble(v.get(1).toString())) {
//                            if (v.get(5).toString().equals("N")) {
//                                this.setComm(String.valueOf(Math.round(Double.parseDouble(v.get(2).toString()))));
//                            } else if (v.get(5).toString().equals("Y")) {
//                                double bg = (Double.parseDouble(v.get(2).toString()) / 1000.0) * (amt1);
//                                this.setComm(String.valueOf(Math.round(bg)));
//                                if (Double.parseDouble(getComm().toString()) < Double.parseDouble(v.get(4).toString())) {
//                                    setComm(String.valueOf(Math.round(Double.parseDouble(v.get(4).toString()))));
//                                }
//                                surcharge = Double.parseDouble(v.get(3).toString());
//                                setComm(String.valueOf(Math.round(Double.parseDouble(getComm()) + surcharge)));
//                            }
//                        }
//                    } else if (Double.parseDouble(v.get(1).toString()) == 0) {
//                        if (Double.parseDouble(amt.toString()) >= Double.parseDouble(v.get(0).toString()) && v.get(5).toString().equals("Y")) {
//                            double bg = (Double.parseDouble(v.get(2).toString()) / 1000.0) * (amt1);
//                            this.setComm(String.valueOf(Math.round(bg)));
//                            if (Double.parseDouble(getComm().toString()) < Double.parseDouble(v.get(4).toString())) {
//                                setComm(String.valueOf(Math.round(Double.parseDouble(v.get(4).toString()))));
//                            }
//                            surcharge = Double.parseDouble(v.get(3).toString());
//                            setComm(String.valueOf(Math.round(Double.parseDouble(getComm()) + surcharge)));
//                        }
//                    }
//                }
//            }else{
//                this.setMsg("Please Fill Slab For "+ type1);
//                return;
//            }

            boolean staxModuleActive = false;
            List list8 = txnRemote.selectBulkReportsFromParameterInfoReport();
            if (!list8.isEmpty()) {
                for (int i = 0; i < list8.size(); i++) {
                    Vector v8 = (Vector) list8.get(i);
                    if (v8.get(1).toString().equalsIgnoreCase("STAXMODULE_ACTIVE")) {
                        if (Integer.parseInt(v8.get(0).toString()) == 0) {
                            staxModuleActive = false;
                        } else {
                            staxModuleActive = true;
                        }
                    }
                }
            }
            if (staxModuleActive == true) {
                Map<String, Double> taxMap = ibrFacade.getTaxComponent(Double.parseDouble(getComm()), ymd.format(date));
                double sTax = 0;
                Set<Map.Entry<String, Double>> set = taxMap.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    sTax = sTax + Double.parseDouble(entry.getValue().toString());
                }
                this.setSercharge(String.valueOf(sTax));
            } else {
                this.setSercharge("0.0");
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }

    }

    public String btn_exit() {
        reset();
        return "case1";
    }

    public void reset() {
        msg = "";
        this.setPaytype("--Select--");
        this.setMode("--Select--");
        this.setAmt("");
        this.setComm("");
        this.setSercharge("");
    }
}
