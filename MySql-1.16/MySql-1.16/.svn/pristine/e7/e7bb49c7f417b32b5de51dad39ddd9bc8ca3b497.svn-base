/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.td;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.other.ReceiptDetail;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class TdReceiptSearch {

    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private List<SelectItem> acctTypeOption;
    private String acType;
    private List<ReceiptDetail> receiptDetail;
    private String receiptNo;
    private String message;
    private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public List<ReceiptDetail> getReceiptDetail() {
        return receiptDetail;
    }

    public void setReceiptDetail(List<ReceiptDetail> receiptDetail) {
        this.receiptDetail = receiptDetail;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TdReceiptSearch() {
        try {
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);

            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            getdata();
            
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getdata() {

        try {
            List resultList = new ArrayList();
            resultList = tdRcptMgmtRemote.getTdReceiptSearchAcType();
            acctTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                    acctTypeOption.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTableDetails() throws NamingException {
        this.setMessage("");
        receiptDetail = new ArrayList<ReceiptDetail>();
        if (acType.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Td Account Type. ");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.receiptNo.equalsIgnoreCase("") || this.receiptNo.length() == 0) {
            this.setMessage("Please Enter Receipt Number.");
            return;
        }
        Matcher billNoCheck = p.matcher(receiptNo);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Receipt Number.");
            return;
        }
        try {
            List resultLt = new ArrayList();
            resultLt = tdRcptMgmtRemote.tableData(Float.parseFloat(receiptNo), acType);
            if (!resultLt.isEmpty()) {
                this.setMessage("");
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    ReceiptDetail rd = new ReceiptDetail();

                    rd.setSeqNo(Float.parseFloat(ele.get(0).toString()));
                    rd.setVoucherNo(Float.parseFloat(ele.get(1).toString()));
                    rd.setAcno(ele.get(2).toString());

                    rd.setPrinAmt(Float.parseFloat(ele.get(3).toString()));
                    rd.setRoi(Float.parseFloat(ele.get(4).toString()));

                    rd.setTdMadeDt(ele.get(5).toString());
                    rd.setFdDt(ele.get(6).toString());
                    rd.setMatDt(ele.get(7).toString());
                    rd.setIntOpt(ele.get(8).toString());
                    rd.setIntToAcno(ele.get(9).toString());
                    rd.setPeriod(ele.get(10).toString());
                    receiptDetail.add(rd);
                }
            } else {
                this.setMessage("Records does not Exist. ");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetAllValue() {
        setAcType("--SELECT--");
        setReceiptNo("");
        setMessage("");
        receiptDetail = new ArrayList<ReceiptDetail>();
    }

    public String exitFrm() {
        resetAllValue();
        return "case1";
    }
}