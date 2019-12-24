/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.LoanAuthorizationManagementFacadeRemote;
//import com.cbs.facade.loan.LoanAuthMgmtFacadeRemote;
//import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
//import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Administrator
 */
public class LoanMisDetailsAuth {

    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private String acno;
    private List<SelectItem> accountTypeOption;
    private List<LoanMisCellaniousPojo> loamMisFirst;
    private List<LoanMisCellaniousPojo> loamMisSecond;
    private List<LoanMisCellaniousPojo> loamMisThird;
    private List<LoanMisCellaniousPojo> loamMisFourth;
    private String enterBy;
    private LoanAuthorizationManagementFacadeRemote loanAuthRemote ;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public List<LoanMisCellaniousPojo> getLoamMisFourth() {
        return loamMisFourth;
    }

    public void setLoamMisFourth(List<LoanMisCellaniousPojo> loamMisFourth) {
        this.loamMisFourth = loamMisFourth;
    }

    public List<LoanMisCellaniousPojo> getLoamMisThird() {
        return loamMisThird;
    }

    public void setLoamMisThird(List<LoanMisCellaniousPojo> loamMisThird) {
        this.loamMisThird = loamMisThird;
    }

    public List<LoanMisCellaniousPojo> getLoamMisSecond() {
        return loamMisSecond;
    }

    public void setLoamMisSecond(List<LoanMisCellaniousPojo> loamMisSecond) {
        this.loamMisSecond = loamMisSecond;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public List<LoanMisCellaniousPojo> getLoamMisFirst() {
        return loamMisFirst;
    }

    public void setLoamMisFirst(List<LoanMisCellaniousPojo> loamMisFirst) {
        this.loamMisFirst = loamMisFirst;
    }

    public List<SelectItem> getAccountTypeOption() {
        return accountTypeOption;
    }

    public void setAccountTypeOption(List<SelectItem> accountTypeOption) {
        this.accountTypeOption = accountTypeOption;
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

    /** Creates a new instance of LoanMisDetailsAuth */
    public LoanMisDetailsAuth() {
        try {
            loanAuthRemote = (LoanAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("LoanAuthorizationManagementFacade");
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setMessage("");            
            getAccountNumber();
            
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

    public void getAccountNumber() {
        try {
            List acnoDataList = new ArrayList();
            acnoDataList = loanAuthRemote.getAccountNoInformation(orgnBrCode);
            accountTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    accountTypeOption.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    //List getAccountNoAuthData(String acno)
    public void getList() {
                this.setMessage("");
        if (acno.equals("--SELECT--")) {
            this.setMessage("Please Select The Account Number");
            loamMisFirst = new ArrayList<LoanMisCellaniousPojo>();
            loamMisSecond = new ArrayList<LoanMisCellaniousPojo>();
            loamMisThird = new ArrayList<LoanMisCellaniousPojo>();
            loamMisFourth = new ArrayList<LoanMisCellaniousPojo>();
            return;
        }
        loamMisFirst = new ArrayList<LoanMisCellaniousPojo>();
        loamMisSecond = new ArrayList<LoanMisCellaniousPojo>();
        loamMisThird = new ArrayList<LoanMisCellaniousPojo>();
        loamMisFourth = new ArrayList<LoanMisCellaniousPojo>();
        try {
            List <LoanMisCellaniousPojo> tableList = new ArrayList<LoanMisCellaniousPojo>();
            tableList = loanAuthRemote.getAccountNoAuthData(acno,ymd.format(dmy.parse(getTodayDate())));
            
            LoanMisCellaniousPojo loanMis1 = new LoanMisCellaniousPojo();
            loanMis1.setCustId(tableList.get(0).getCustId());
            loanMis1.setCustName(tableList.get(0).getCustName());
            loanMis1.setSectorDesc(tableList.get(0).getSectorDesc());
            loanMis1.setSubSectorDesc(tableList.get(0).getSubSectorDesc());
            loanMis1.setModeOfAdvanceDesc(tableList.get(0).getModeOfAdvanceDesc());
            loanMis1.setSecuredDesc(tableList.get(0).getSecuredDesc());//Type Of Advances
            loanMis1.setTypeOfAdvanceDesc(tableList.get(0).getTypeOfAdvanceDesc());//TERM OF Advances
            loanMis1.setPurposeOfAdvanceDesc(tableList.get(0).getPurposeOfAdvanceDesc());//Sub Sector Category (Q)
            loanMis1.setGuarnteeCoverDesc(tableList.get(0).getGuarnteeCoverDesc());//Sub Sector Category (Y)
            loanMis1.setPurOfAdvDesc(tableList.get(0).getPurOfAdvDesc());//Sickness
            loanMis1.setExposureDesc(tableList.get(0).getExposureDesc());
            loanMis1.setExposureCategoryDesc(tableList.get(0).getExposureCategoryDesc());
            loanMis1.setExposureCategoryPurposeDesc(tableList.get(0).getExposureCategoryPurposeDesc());
            loamMisFirst.add(loanMis1);

            LoanMisCellaniousPojo loanMis2 = new LoanMisCellaniousPojo();            
            loanMis2.setSchemeCodeDesc(tableList.get(0).getSchemeCodeDesc());
            loanMis2.setIntTableDesc(tableList.get(0).getIntTableDesc());
            loanMis2.setIntTypeDesc(tableList.get(0).getIntTypeDesc());
            loanMis2.setApplicantCategoryDesc(tableList.get(0).getApplicantCategoryDesc());
            loanMis2.setCategoryOptDesc(tableList.get(0).getCategoryOptDesc());
            loanMis2.setMinorCommunityDesc(tableList.get(0).getMinorCommunityDesc());
            loanMis2.setRelationDesc(tableList.get(0).getRelationDesc());//Director/ Staff
            loanMis2.setRelOwnerDesc(tableList.get(0).getRelOwnerDesc());//Director Relation
            loanMis2.setDrawingPwrIndDesc(tableList.get(0).getDrawingPwrIndDesc());
            loanMis2.setPopuGroupDesc(tableList.get(0).getPopuGroupDesc());
            loanMis2.setSanctionLevelDesc(tableList.get(0).getSanctionLevelDesc());
            loanMis2.setSanctionAuthDesc(tableList.get(0).getSanctionAuthDesc());
            loamMisSecond.add(loanMis2);

            LoanMisCellaniousPojo loanMis3 = new LoanMisCellaniousPojo();
            loanMis3.setCreatedUsrId(tableList.get(0).getCreatedUsrId());
            setEnterBy(tableList.get(0).getCreatedUsrId());
            loanMis3.setCreationDt(dmy.format(y_m_d.parse(tableList.get(0).getCreationDt())));
            loanMis3.setLastUpdtUsrId(tableList.get(0).getLastUpdtUsrId());
            loanMis3.setLastUpdtDt(tableList.get(0).getLastUpdtDt());
            loanMis3.setTotalModification(tableList.get(0).getTotalModification());
            loanMis3.setNetWorth(tableList.get(0).getNetWorth());
            loanMis3.setMarginMoney(tableList.get(0).getMarginMoney());
            loanMis3.setDocumentDt(dmy.format(y_m_d.parse(tableList.get(0).getDocumentDt())));
            loanMis3.setDocumentExpDt(dmy.format(y_m_d.parse(tableList.get(0).getDocumentExpDt())));
            loanMis3.setRenewalDt(dmy.format(y_m_d.parse(tableList.get(0).getRenewalDt())));
            loanMis3.setMonthlyIncome(tableList.get(0).getMonthlyIncome());
            loanMis3.setRemarks(tableList.get(0).getRemarks());

            LoanMisCellaniousPojo loanMis4 = new LoanMisCellaniousPojo();
            loanMis4.setNpaClassDesc(tableList.get(0).getNpaClassDesc());
            loanMis4.setAssetClassReasonDesc(tableList.get(0).getAssetClassReasonDesc());
            loanMis4.setCourtsDesc(tableList.get(0).getCourtsDesc());
            loanMis4.setModeOfSettleDesc(tableList.get(0).getModeOfSettleDesc());
            loanMis4.setDebtWaiverReasonDesc(tableList.get(0).getDebtWaiverReasonDesc());
            loanMis4.setRestructuringDesc(tableList.get(0).getRestructuringDesc());
            loanMis4.setSanctionAmt(tableList.get(0).getSanctionAmt());            
            loamMisFourth.add(loanMis4);

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void accountAuthorization() {
        this.setMessage("");
        if (acno.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("ACCOUNT NUMBER DOES NOT EXIST FOR AUTHORIZATION !!!");
            return;
        }
        if (this.enterBy.equalsIgnoreCase("") || this.enterBy.length() == 0) {
            this.setMessage("Enter By Name Is Not Present So This A/C. Cannot Be Authorized.");
            return;
        }
        if (enterBy.equalsIgnoreCase(this.userName)) {
            this.setMessage("SORRY,YOU CAN NOT PASS YOUR OWN ENTRY !!!");
            return;
        }
        try {
             String result = loanAuthRemote.authorizeAction(acno, enterBy, userName);
            if (result.equalsIgnoreCase("AUTHORIZATION SUCCESSFUL")) {
                this.setMessage(result);
                loamMisFirst = new ArrayList<LoanMisCellaniousPojo>();
                loamMisSecond = new ArrayList<LoanMisCellaniousPojo>();
                loamMisThird = new ArrayList<LoanMisCellaniousPojo>();
                loamMisFourth = new ArrayList<LoanMisCellaniousPojo>();
            } else {
                this.setMessage(result);
            }
            setAcno("--SELECT--");
            List acnoDataList = loanAuthRemote.getAccountNoInformation(orgnBrCode);
            accountTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    accountTypeOption.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void clearText() {
        setAcno("--SELECT--");
        this.setMessage("");
        loamMisFirst = new ArrayList<LoanMisCellaniousPojo>();
        loamMisSecond = new ArrayList<LoanMisCellaniousPojo>();
        loamMisThird = new ArrayList<LoanMisCellaniousPojo>();
        loamMisFourth = new ArrayList<LoanMisCellaniousPojo>();
    }

    public String exitFrm() {
        setAcno("--SELECT--");
        this.setMessage("");
        loamMisFirst = new ArrayList<LoanMisCellaniousPojo>();
        loamMisSecond = new ArrayList<LoanMisCellaniousPojo>();
        loamMisThird = new ArrayList<LoanMisCellaniousPojo>();
        loamMisFourth = new ArrayList<LoanMisCellaniousPojo>();
        return "case1";
    }
}
