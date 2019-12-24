/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.pojo.ChbookDetailPojo;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

public class InstrumentSearch extends BaseBean {

    AcctEnqueryFacadeRemote remoteObject;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    Context ctx;
    private String todayDate;
    private String userName;
    private String message;
    private String chqno;
    private String acctype;
    private List<SelectItem> acctTypeOption;
    private List<SelectItem> repTypeOption;
    private String accno;
    private String name;
    private String pstatus;
    private String cseries;
    private String orgnBrCode;
    private HttpServletRequest req;
    private String repTp;
    private String acNo, oldAcNo, acNoLen;
    private String chqFlg;
    private String acTFlag;
    private String acNFlag;
    private List<ChbookDetailPojo> chqList;
    
    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getAcctype() {
        return acctype;
    }

    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public String getChqno() {
        return chqno;
    }

    public void setChqno(String chqno) {
        this.chqno = chqno;
    }

    public String getCseries() {
        return cseries;
    }

    public void setCseries(String cseries) {
        this.cseries = cseries;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
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

    public List<SelectItem> getRepTypeOption() {
        return repTypeOption;
    }

    public void setRepTypeOption(List<SelectItem> repTypeOption) {
        this.repTypeOption = repTypeOption;
    }

    public String getRepTp() {
        return repTp;
    }

    public void setRepTp(String repTp) {
        this.repTp = repTp;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getChqFlg() {
        return chqFlg;
    }

    public void setChqFlg(String chqFlg) {
        this.chqFlg = chqFlg;
    }

    public String getAcTFlag() {
        return acTFlag;
    }

    public void setAcTFlag(String acTFlag) {
        this.acTFlag = acTFlag;
    }

    public String getAcNFlag() {
        return acNFlag;
    }

    public void setAcNFlag(String acNFlag) {
        this.acNFlag = acNFlag;
    }

    public List<ChbookDetailPojo> getChqList() {
        return chqList;
    }

    public void setChqList(List<ChbookDetailPojo> chqList) {
        this.chqList = chqList;
    }

    public String getOldAcNo() {
        return oldAcNo;
    }

    public void setOldAcNo(String oldAcNo) {
        this.oldAcNo = oldAcNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    
    public void onBlurGetNewAccount() {
        try{
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                this.setMessage("Please fill Account Number.");
                return;
            }
            
            if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill Account Number.");
                return;
            }
            acNo = ftsPostRemote.getNewAccountNumber(oldAcNo);
        }catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public InstrumentSearch() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            this.setAcNoLen(getAcNoLength());
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            repTypeOption = new ArrayList<SelectItem>();
            repTypeOption.add(new SelectItem("C","Cheque Wise"));
            repTypeOption.add(new SelectItem("A","Account Wise"));
            this.setAcNFlag("true");
            dropDownAdd();
            //setChqno("368493");
            reset();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void setBranchOptions() {
        try {            
            if(this.getRepTp().equalsIgnoreCase("A")){
                this.setChqno("");
                this.setAcNFlag("false");
                this.setAcTFlag("true");
                this.setChqFlg("true");                
            }else{
                chqList = new ArrayList<ChbookDetailPojo>();
                this.setAcNo("");
                this.setAcNFlag("true");
                this.setAcTFlag("false");
                this.setChqFlg("false");
                this.setOldAcNo("");
            }            
            setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void dropDownAdd() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.accType();
            acctTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctTypeOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void searchInfo() {
        try {
            this.setPstatus("");
            this.setAccno("");
            this.setName("");
            this.setMessage("");
            this.setCseries("");            
            
            if(this.getRepTp().equalsIgnoreCase("C")){
                if (this.chqno == null || this.chqno.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Cheque no.");
                    return;
                }
                if (this.chqno.contains(".")) {
                    this.setMessage("Please Enter Valid Cheque no.");
                    return;
                }
                if (this.acctype == null || this.acctype.equalsIgnoreCase("")) {
                    this.setMessage("Please Select Account Type.");
                    return;
                }
                
                String st1 = onblurChequeNo();
                if (st1.equals("true")) {
                    String srchvalue = remoteObject.searchData(this.acctype, this.chqno, this.orgnBrCode);
                    if (srchvalue.contains(":")) {
                        String[] values = null;
                        String spliter = ":";
                        values = srchvalue.split(spliter);
                        this.setPstatus(values[0]);
                        this.setAccno(values[1]);
                        this.setName(values[2]);
                        this.setCseries(values[3]);
                    } else {
                        this.setMessage(srchvalue);
                        return;
                    }
                } else {
                    this.setMessage("Please Enter Valid cheque no.");
                    return;
                }                
            } else {
                if(this.oldAcNo == null || this.oldAcNo.equalsIgnoreCase("")){
                    this.setMessage("Please Enter Account No.");
                    return;
                }
                
                //if(this.acNo.length()!= 12){
                if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setMessage("Please Enter Valid Account No.");
                    return;
                }
                
                acNo = ftsPostRemote.getNewAccountNumber(oldAcNo);
                String result = remoteObject.AccountStatus(acNo);
                if (result != null) {
                    this.setMessage(result);
                    return;
                }               
                
                chqList = new ArrayList<ChbookDetailPojo>();
                chqList = remoteObject.getAllChqDetails(this.getAcNo());
                if(!chqList.isEmpty()){
                    this.setAccno(chqList.get(0).getAcctNo());
                    this.setName(chqList.get(0).getCustName());
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void reset() {
        try {
            setPstatus("");
            setAccno("");
            setName("");
            setMessage("");
            setChqno("");
            setCseries("");
            setAcNo("");
            setRepTp("C");
            setOldAcNo("");
            setBranchOptions();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurChequeNo() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.chqno == null || this.chqno.equalsIgnoreCase("")) {
                this.setMessage("Please Enter cheque no.");
                this.setPstatus("");
                this.setAccno("");
                this.setName("");
                this.setCseries("");
                return "false";
            }
            if (this.chqno.contains(".")) {
                this.setMessage("Please Enter Valid cheque no.");
                this.setPstatus("");
                this.setAccno("");
                this.setName("");
                this.setCseries("");
                return "false";
            }
            Matcher billNoCheck = p.matcher(this.getChqno());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid cheque no.");
                this.setPstatus("");
                this.setAccno("");
                this.setName("");
                this.setCseries("");
                return "false";
            } else {
                this.setMessage("");
                this.setPstatus("");
                this.setAccno("");
                this.setName("");
                this.setCseries("");
                return "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "true";
    }

    public String exitFrm() {
        try {
            reset();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
