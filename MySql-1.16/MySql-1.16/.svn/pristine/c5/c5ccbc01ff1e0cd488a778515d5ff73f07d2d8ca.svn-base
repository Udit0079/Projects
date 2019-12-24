/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.StandingInstructionManagementRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.SI;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class SIEnquiry extends BaseBean{

   private String message;
    private String status;
    private List<SelectItem> statusType;
    private String oldAcno;
    private Integer code1;
    private List<SI> incis;
    private String effdate;
    private Date effdate1;
    private String entrydt;
    private Date entrydt1;
    private final String jndiHomeName = "StandingInstructionManagementFacade";
    private StandingInstructionManagementRemote beanRemote = null;
    private String newAcno, acNoLen;

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

    public Integer getCode1() {
        return code1;
    }

    public void setCode1(Integer code1) {
        this.code1 = code1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusType() {
        return statusType;
    }

    public void setStatusType(List<SelectItem> statusType) {
        this.statusType = statusType;
    }

    public List<SI> getIncis() {
        return incis;
    }

    public void setIncis(List<SI> incis) {
        this.incis = incis;
    }

    public String getEffdate() {
        return effdate;
    }

    public void setEffdate(String effdate) {
        this.effdate = effdate;
    }

    public Date getEffdate1() {
        return effdate1;
    }

    public void setEffdate1(Date effdate1) {
        this.effdate1 = effdate1;
    }

    public String getEntrydt() {
        return entrydt;
    }

    public void setEntrydt(String entrydt) {
        this.entrydt = entrydt;
    }

    public Date getEntrydt1() {
        return entrydt1;
    }

    public void setEntrydt1(Date entrydt1) {
        this.entrydt1 = entrydt1;
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

    public SIEnquiry() {
        try {
            beanRemote = (StandingInstructionManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setAcNoLen(getAcNoLength());
            reFresh();
            statusType = new ArrayList<SelectItem>();
            statusType.add(new SelectItem("---Select---"));
            statusType.add(new SelectItem("Account To Be Debited"));
            statusType.add(new SelectItem("Account To Be Credited"));
            statusType.add(new SelectItem("Instruction No."));
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void griddata() {
        incis = new ArrayList<SI>();
        this.setMessage("");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        try {
            if (status.equals("---Select---")) {
                this.setMessage("Please select standing instruction enquiry");
                return;
            }
            
            if (this.status.equalsIgnoreCase("Account To Be Credited")) {
                code1 = 1;
            } else if (this.status.equalsIgnoreCase("Account To Be Debited")) {
                code1 = 0;
            } else if (this.status.equalsIgnoreCase("Instruction No.")) {
                code1 = 2;
            }
            
            if(code1==2){
                newAcno = oldAcno;
            }else{
                if ((oldAcno == null) || (oldAcno.equalsIgnoreCase(""))) {
                    this.setMessage("Please enter account no");
                    return;
                }
                //if ((oldAcno.length() < 12)) {
                if (!this.oldAcno.equalsIgnoreCase("") && ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setMessage("Error!!! Entered account should be proper");
                    return;
                }
                
                FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                String result = ftsBeanRemote.getNewAccountNumber(oldAcno);
                if (result.startsWith("A/c")) {
                    setMessage(result);
                    return;
                } else {
                    newAcno = result;
                }
            }
            
            list = beanRemote.accNoPass(newAcno, code1);
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                SI tab = new SI();
                if (code1 == 1 || code1 == 2) {
                    tab.setDraccount(v.get(6).toString());
                } 
                if (code1 == 0 || code1 == 2) {
                    tab.setCraccount(v.get(7).toString());
                }
                tab.setInstrnno(v.get(0).toString());
                setEffdate(v.get(1).toString());
                setEffdate1(ymd.parse(effdate));
                tab.setEffdate(sdf.format(effdate1));
                Double d2 = Double.parseDouble(v.get(2).toString());
                tab.setAmount(new java.text.DecimalFormat("#,############0.00").format(d2));
                tab.setStatus(v.get(3).toString());
                setEntrydt(v.get(4).toString());
                setEntrydt1(ymd.parse(entrydt));
                tab.setEntrydate(sdf.format(entrydt1));
                tab.setEnteryby(v.get(5).toString());
                incis.add(tab);
            }
            if (list.isEmpty()) {
                this.setMessage("No data exist");
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void reFresh() {
        try {
            incis = new ArrayList<SI>();
            this.setMessage("");
            setOldAcno("");
            setNewAcno("");
            setStatus("---Select---");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            reFresh();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
