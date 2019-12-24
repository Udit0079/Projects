/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.StandingInstructionManagementRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.SICancellationTxnGrid;
import com.cbs.web.pojo.other.SICancellationGeneralGrid;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.context.ExternalContext;

/**
 *
 * @author root
 */
public class SICancellation extends BaseBean {

    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String oldAcno;
    private String instructionType;
    private int currentRow;
    private SICancellationTxnGrid currentItem;
    private Date dates;
    private String message;
    private List<SICancellationTxnGrid> siTransTables;
    private List<SICancellationGeneralGrid> siTransGeneTbs;
    private SICancellationGeneralGrid currentItem1;
    private String effPeriod;
    private String remarks;
    private float amount;
    private int validityDays;
    private String name;
    private String var;
    private String acCloseFlag;
    private final String jndiHomeName = "StandingInstructionManagementFacade";
    private StandingInstructionManagementRemote beanRemote = null;
    private String newAcno, acNoLen;
    private List l1;

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public List<SICancellationGeneralGrid> getSiTransGeneTbs() {
        return siTransGeneTbs;
    }

    public void setSiTransGeneTbs(List<SICancellationGeneralGrid> siTransGeneTbs) {
        this.siTransGeneTbs = siTransGeneTbs;
    }

    public List<SICancellationTxnGrid> getSiTransTables() {
        return siTransTables;
    }

    public void setSiTransTables(List<SICancellationTxnGrid> siTransTables) {
        this.siTransTables = siTransTables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEffPeriod() {
        return effPeriod;
    }

    public void setEffPeriod(String effPeriod) {
        this.effPeriod = effPeriod;
    }

    public SICancellationGeneralGrid getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(SICancellationGeneralGrid currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public SICancellationTxnGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(SICancellationTxnGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
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

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getAcCloseFlag() {
        return acCloseFlag;
    }

    public void setAcCloseFlag(String acCloseFlag) {
        this.acCloseFlag = acCloseFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public SICancellation() {
        try {
            beanRemote = (StandingInstructionManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setAcNoLen(getAcNoLength());
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            if (req.getParameter("code") == null || req.getParameter("code").toString().equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(req.getParameter("code").toString());
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            setDates(new Date());
            //dropDownAdd();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

//    public void dropDownAdd() {
//        try {
//            reFresh() ;
//            List resultList = new ArrayList();
//            ctx = new InitialContext();
//            instrcancel = (InstructionCancellationRemote) ctx.lookup(InstructionCancellationRemote.class.getName());
//            resultList = instrcancel.accType();
//            acctTypeOption = new ArrayList<SelectItem>();
//            for (int i = 0; i < resultList.size(); i++) {
//                Vector ele = (Vector) resultList.get(i);
//                for (Object ee : ele) {
//                    acctTypeOption.add(new SelectItem(ee.toString()));
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    public void gridData() {
        siTransTables = new ArrayList<SICancellationTxnGrid>();
        siTransGeneTbs = new ArrayList<SICancellationGeneralGrid>();
        try {
            if (instructionType.equals("--SELECT--")) {
                reFresh1();
                setMessage("Please select instruction type");
                return;
            }
            //if (this.oldAcno.length() != 12) {
            if ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen())))) {
                reFresh1();
                setMessage("Please enter proper account no");
                return;
            }
            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumber(oldAcno);
            if (result.startsWith("A/c")) {
                reFresh1();
                setMessage(result);
                return;
            } else {
                newAcno = result;
            }
            List list = new ArrayList();
            list = beanRemote.loadGrdData(newAcno, instructionType);
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                if (instructionType.equals("TRANSACTION")) {
                    SICancellationTxnGrid tab = new SICancellationTxnGrid();
                    tab.setFromAcno(v.get(0).toString());
                    tab.setToAcno(v.get(1).toString());
                    tab.setsNo(v.get(2).toString());
                    tab.setInstrNo(v.get(3).toString());
                    Double d2 = Double.parseDouble(v.get(4).toString());
                    tab.setAmount(new java.text.DecimalFormat("#,############0.00").format(d2));
                    tab.setEffDate(v.get(5).toString());
                    tab.setStatus(v.get(6).toString());
                    tab.setRemarks(v.get(7).toString());
                    tab.setEnterBy(v.get(8).toString());
                    tab.setEntryDate(v.get(9).toString());
                    tab.setChargeFlag(v.get(10).toString());
                    tab.setExpiryDt(v.get(11).toString());
                    siTransTables.add(tab);
                } else if (instructionType.equals("GENERAL")) {
                    SICancellationGeneralGrid tab1 = new SICancellationGeneralGrid();
                    tab1.setAcno(v.get(0).toString());
                    tab1.setsNo(v.get(1).toString());
                    tab1.setInstrNo(v.get(2).toString());
                    tab1.setEffDate(v.get(3).toString());
                    tab1.setStatus(v.get(4).toString());
                    tab1.setRemarks(v.get(5).toString());
                    tab1.setEnterBy(v.get(6).toString());
                    tab1.setEntryDate(v.get(7).toString());
                    siTransGeneTbs.add(tab1);
                }
            }
            String rs = beanRemote.acnoFind(newAcno, instructionType);
            if (rs.equalsIgnoreCase("Account Does Not Exist!!!.") || rs.equalsIgnoreCase("You Must Enter Account Number !!!.") || rs.equalsIgnoreCase("This Account Is Closed !!!.")) {
            }
            if (!rs.isEmpty()) {
                setName(rs);
            }
            if (list.isEmpty()) {
                setMessage("No instruction for this account");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        this.setMessage("");
        try {
            String transdeleteEntry = new String();
            transdeleteEntry = beanRemote.deleteTransData(currentItem.getFromAcno(), Integer.parseInt(orgnBrCode), Float.parseFloat(currentItem.getInstrNo()), userName);
            this.setMessage(transdeleteEntry);
            gridData();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setPanel() {
        this.setVar("Press Yes For Delete All Instruction , Press No For Delete S.No. " + currentItem.getsNo() + " For Instruction No. " + currentItem.getInstrNo() + " And Press Cancel For Cancel Operation.");

    }

    public void deleteTr() {
        this.setVar(currentItem.getsNo());
        this.setMessage("");
        try {
            String transdeleteEntry1 = new String();
            transdeleteEntry1 = beanRemote.deleteTransData1(currentItem.getFromAcno(), Integer.parseInt(orgnBrCode), Float.parseFloat(currentItem.getInstrNo()), userName, Integer.parseInt(currentItem.getsNo()));
            this.setMessage(transdeleteEntry1);
            gridData();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void delete1() {
        this.setMessage("");
        try {
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String dt = ymd.format(date);
            String deleteEntry1 = new String();
            deleteEntry1 = beanRemote.deleteGenData(Float.parseFloat(currentItem1.getInstrNo()), dt, userName, Integer.parseInt(orgnBrCode));
            this.setMessage(deleteEntry1);
            gridData();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void reFresh() {
        try {
            siTransTables = new ArrayList<SICancellationTxnGrid>();
            siTransGeneTbs = new ArrayList<SICancellationGeneralGrid>();
            this.setMessage("");
            setOldAcno("");
            setName("");
            setNewAcno("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void reFresh2() {
        try {
            this.setMessage("");
            setOldAcno("");
            setName("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void reFresh1() {
        try {
            siTransTables = new ArrayList<SICancellationTxnGrid>();
            siTransGeneTbs = new ArrayList<SICancellationGeneralGrid>();
            this.setMessage("");
            setOldAcno("");
            setName("");
            setNewAcno("");
            setInstructionType("--Select--");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            reFresh1();
            if (this.acCloseFlag == null || this.acCloseFlag.equalsIgnoreCase("")) {
                return "case1";
            } else {
                this.setAcCloseFlag(null);
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/AccountClosingRegister.jsp?code=" + 1);
                return "case2";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
