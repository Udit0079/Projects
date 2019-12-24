/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.StandingInstructionManagementRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.SIPendingGrid;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SIPending extends BaseBean {

    private String message;
    private List<SIPendingGrid> instPending;
    private List<SIPendingGrid> reportInstPending;
    private String errMessage;
    private final String jndiHomeName = "StandingInstructionManagementFacade";
    private StandingInstructionManagementRemote beanRemote = null;

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public List<SIPendingGrid> getReportInstPending() {
        return reportInstPending;
    }

    public void setReportInstPending(List<SIPendingGrid> reportInstPending) {
        this.reportInstPending = reportInstPending;
    }

    public List<SIPendingGrid> getInstPending() {
        return instPending;
    }

    public void setInstPending(List<SIPendingGrid> instPending) {
        this.instPending = instPending;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a new instance of SIPending
     */
    public SIPending() {
        try {
            beanRemote = (StandingInstructionManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            instPending = new ArrayList<SIPendingGrid>();
            getTableDetails();
            reportInstPending = new ArrayList<SIPendingGrid>();
        } catch (ApplicationException e) {
            this.setErrMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getTableDetails() throws NamingException {
        setErrMessage("");
        instPending = new ArrayList<SIPendingGrid>();
        try {
            List resultLt = new ArrayList();
            resultLt = beanRemote.loadGridPending(getOrgnBrCode());
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    SIPendingGrid pending = new SIPendingGrid();

                    pending.setInstrNo(Float.parseFloat(ele.get(0).toString()));
                    pending.setsNo(Integer.parseInt(ele.get(1).toString()));
                    pending.setFromAcno(ele.get(2).toString());
                    pending.setFromCustName(beanRemote.getCustomerName(ele.get(2).toString()));
                    pending.setToAcno(ele.get(3).toString());
                    pending.setToCustName(beanRemote.getCustomerName(ele.get(3).toString()));
                    pending.setAmount(Float.parseFloat(ele.get(4).toString()));
                    pending.setEffDate(ele.get(5).toString());
                    pending.setRemarks(ele.get(6).toString());
                    pending.setExpiryDt(ele.get(7).toString());

                    instPending.add(pending);
                }
            } else {
                this.setErrMessage("No instructions pending for today");
                return;
            }
        } catch (ApplicationException e) {
            this.setErrMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrMessage(e.getLocalizedMessage());
        }
    }

    public void postingData() throws NamingException {
        reportInstPending = new ArrayList<SIPendingGrid>();
        this.setMessage("");
        try {
            String postingResult = beanRemote.postButton(getUserName(), getOrgnBrCode());
            String[] values = null;
            String spliter = "@ ";
            values = postingResult.split(spliter);
            String msg = (values[0]);
            String tableListValue = (values[1]);
            this.setMessage(msg);
            if (tableListValue.contains("[")) {
                String[] values1 = null;
                tableListValue = tableListValue.replace("]", "");
                tableListValue = tableListValue.replace("[", "");
                String spliter1 = ", ";
                values1 = tableListValue.split(spliter1);
                for (int i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, p = 7; p <= (values1.length); i = i + 8, j = j + 8, k = k + 8, l = l + 8, m = m + 8, n = n + 8, o = o + 8, p = p + 8) {
                    SIPendingGrid pendingReport = new SIPendingGrid();

                    pendingReport.setInstrNo(Float.parseFloat(values1[i]));
                    pendingReport.setsNo(Integer.parseInt(values1[j]));
                    pendingReport.setFromAcno(values1[k]);
                    pendingReport.setFromCustName(beanRemote.getCustomerName(values1[k]));
                    pendingReport.setToAcno(values1[l]);
                    pendingReport.setToCustName(beanRemote.getCustomerName(values1[l]));
                    pendingReport.setAmount(Float.parseFloat(values1[m]));
                    pendingReport.setEffDate(values1[n]);
                    pendingReport.setRemarks(values1[o]);
                    pendingReport.setExpiryDt(values1[p]);

                    reportInstPending.add(pendingReport);
                }
            }
            getTableDetails();
        } catch (ApplicationException e) {
            this.setErrMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrMessage(e.getLocalizedMessage());
        }
    }

    public String exit() {
        this.setMessage("");
        try {
            getTableDetails();
        } catch (Exception e) {
            this.setErrMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
