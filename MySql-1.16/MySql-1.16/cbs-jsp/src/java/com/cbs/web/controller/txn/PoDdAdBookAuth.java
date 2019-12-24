/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.AuthorizeTable;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class PoDdAdBookAuth {

    private String todayDate;
    private String userName;
    private HttpServletRequest req;
    private String orgnBrCode;
    private String message;
    private List<AuthorizeTable> incis;
    int currentRow;
    AuthorizeTable currentItem;
    private String instcode;
    private String stabcode;
    private String amtform;
    private String amtto;
    private String issuedate;
    private String issuedatte;
    private Date issuedattes;
    private String numfrom;
    private String numto;
    private String leaves;
    private String printlot;
    private String issueby;
    private String instcode1;
    private String dabbkissue;
    private String option;
    private String flag;
    private String flag1;
    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public Date getIssuedattes() {
        return issuedattes;
    }

    public void setIssuedattes(Date issuedattes) {
        this.issuedattes = issuedattes;
    }

    public String getIssuedatte() {
        return issuedatte;
    }

    public void setIssuedatte(String issuedatte) {
        this.issuedatte = issuedatte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<AuthorizeTable> getIncis() {
        return incis;
    }

    public void setIncis(List<AuthorizeTable> incis) {
        this.incis = incis;
    }

    public AuthorizeTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AuthorizeTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getAmtform() {
        return amtform;
    }

    public void setAmtform(String amtform) {
        this.amtform = amtform;
    }

    public String getAmtto() {
        return amtto;
    }

    public void setAmtto(String amtto) {
        this.amtto = amtto;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

    public String getIssueby() {
        return issueby;
    }

    public void setIssueby(String issueby) {
        this.issueby = issueby;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    public String getNumfrom() {
        return numfrom;
    }

    public void setNumfrom(String numfrom) {
        this.numfrom = numfrom;
    }

    public String getNumto() {
        return numto;
    }

    public void setNumto(String numto) {
        this.numto = numto;
    }

    public String getPrintlot() {
        return printlot;
    }

    public void setPrintlot(String printlot) {
        this.printlot = printlot;
    }

    public String getStabcode() {
        return stabcode;
    }

    public void setStabcode(String stabcode) {
        this.stabcode = stabcode;
    }

    public String getInstcode1() {
        return instcode1;
    }

    public void setInstcode1(String instcode1) {
        this.instcode1 = instcode1;
    }

    public String getDabbkissue() {
        return dabbkissue;
    }

    public void setDabbkissue(String dabbkissue) {
        this.dabbkissue = dabbkissue;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public PoDdAdBookAuth() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager1");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            griddata();
            reFresh();
            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void griddata() {
        incis = new ArrayList<AuthorizeTable>();
        try {
            List list = new ArrayList();
            list = otherAuthRemote.grid(orgnBrCode);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector v = (Vector) list.get(i);
                    AuthorizeTable tab = new AuthorizeTable();
                    if (v.get(0).toString().equalsIgnoreCase("") || v.get(0).toString() == null) {
                        tab.setInstcode("");
                    } else {
                        tab.setInstcode(v.get(0).toString());
                    }
                    if (v.get(1).toString().equalsIgnoreCase("") || v.get(1).toString() == null) {
                        tab.setSno("0");
                    } else {
                        tab.setSno(v.get(1).toString());
                    }
                    incis.add(tab);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        instcode1 = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("instcode1"));
        String sno = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sno"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (AuthorizeTable item : incis) {
            if (item.getInstcode().equals(instcode1) && item.getSno().equals(sno)) {
                currentItem = item;
                break;
            }
        }
    }

    public void selectValueInstcode() {
        try {
            if (!(currentItem.getInstcode()).isEmpty()) {
                setDabbkissue(currentItem.getSno());
            } else {
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValues() {
        try {
            selectValueInstcode();
            flag1 = "false";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (((currentItem.getInstcode()).equals("")) || (currentItem.getInstcode() == null)) {
                this.setMessage("");
                return;
            }
            if (Integer.parseInt(currentItem.getSno()) == 0) {
                this.setMessage("");
                return;
            }
            List selectvalue = new ArrayList();
            selectvalue = otherAuthRemote.getSelectedValue(currentItem.getInstcode(), Integer.parseInt(currentItem.getSno()), orgnBrCode);
            if (!selectvalue.isEmpty()) {
                for (int i = 0; i < selectvalue.size(); i++) {
                    Vector v = (Vector) selectvalue.get(i);
                    if (v.get(0).toString().equalsIgnoreCase("") || v.get(0).toString() == null) {
                        setInstcode("");
                    } else {
                        setInstcode(v.get(0).toString());
                    }
                    if (v.get(1).toString().equalsIgnoreCase("") || v.get(1).toString() == null) {
                        setStabcode("0");
                    } else {
                        setStabcode(v.get(1).toString());
                    }
                    if (v.get(2).toString().equalsIgnoreCase("") || v.get(2).toString() == null) {
                        setAmtform("0");
                    } else {
                        int amfr = Math.round(Float.parseFloat(v.get(2).toString()));
                        setAmtform(String.valueOf(amfr));
                    }
                    if (v.get(3).toString().equalsIgnoreCase("") || v.get(3).toString() == null) {
                        setAmtto("0");
                    } else {
                        int amto = Math.round(Float.parseFloat(v.get(3).toString()));
                        setAmtto(String.valueOf(amto));
                    }
                    if (v.get(4).toString().equalsIgnoreCase("") || v.get(4).toString() == null) {
                        setIssuedate("");
                    } else {
                        setIssuedate(v.get(4).toString());
                        setIssuedattes(sdf.parse(issuedate));
                        setIssuedatte(ymd.format(issuedattes));
                    }
                    if (v.get(5).toString().equalsIgnoreCase("") || v.get(5).toString() == null) {
                        setNumfrom("0");
                    } else {
                        setNumfrom(v.get(5).toString());
                    }
                    if (v.get(6).toString().equalsIgnoreCase("") || v.get(6).toString() == null) {
                        setNumto("0");
                    } else {
                        setNumto(v.get(6).toString());
                    }
                    if (v.get(7).toString().equalsIgnoreCase("") || v.get(7).toString() == null) {
                        setLeaves("0");
                    } else {
                        setLeaves(v.get(7).toString());
                    }
                    if (v.get(8).toString().equalsIgnoreCase("") || v.get(8).toString() == null) {
                        setPrintlot("0");
                    } else {
                        setPrintlot(v.get(8).toString());
                    }
                    if (v.get(9).toString().equalsIgnoreCase("") || v.get(9).toString() == null || v.get(9).toString().equalsIgnoreCase("null")) {
                        setIssueby("");
                    } else {
                        setIssueby(v.get(9).toString());
                    }

                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void dleteOpeartion() {
        try {
            option = "DELETE";
            String selectvalue1 = otherAuthRemote.authDeleteData(instcode, Integer.parseInt(stabcode), Float.parseFloat(amtform), Float.parseFloat(amtto), issuedatte, Integer.parseInt(numfrom), Integer.parseInt(numto), Integer.parseInt(leaves), printlot, issueby, option, userName, Integer.parseInt(currentItem.getSno()), orgnBrCode);
            if (!selectvalue1.isEmpty()) {
                incis = new ArrayList<AuthorizeTable>();
                setMessage(selectvalue1);
                setInstcode("");
                setStabcode("");
                setAmtform("");
                setAmtto("");
                setIssuedate("");
                setNumfrom("");
                setNumto("");
                setLeaves("");
                setPrintlot("");
                setIssueby("");
                setDabbkissue("");
                griddata();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void authOpeartion() {
        try {
            option = "AUTHORIZE";
            String selectvalue1 = otherAuthRemote.authDeleteData(instcode, Integer.parseInt(stabcode), Float.parseFloat(amtform), Float.parseFloat(amtto), issuedatte, Integer.parseInt(numfrom), Integer.parseInt(numto), Integer.parseInt(leaves), printlot, issueby, option, userName, Integer.parseInt(currentItem.getSno()), orgnBrCode);
            if (!selectvalue1.isEmpty()) {
                incis = new ArrayList<AuthorizeTable>();
                setMessage(selectvalue1);
                setInstcode("");
                setStabcode("");
                setAmtform("");
                setAmtto("");
                setIssuedate("");
                setNumfrom("");
                setNumto("");
                setLeaves("");
                setPrintlot("");
                setIssueby("");
                setDabbkissue("");
                griddata();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void reFresh() {
        try {
            flag1 = "true";
            setMessage("");
            setInstcode("");
            setStabcode("");
            setAmtform("");
            setAmtto("");
            setIssuedate("");
            setNumfrom("");
            setNumto("");
            setLeaves("");
            setPrintlot("");
            setIssueby("");
            setDabbkissue("");
            griddata();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String exitFrm() {
        return "case1";
    }
}
