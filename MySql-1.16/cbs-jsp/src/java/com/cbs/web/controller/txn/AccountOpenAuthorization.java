/*
 * CREATED BY        :  ROHIT KRISHNA GUPTA
 * CREATION DATE     :  28 OCT 2010
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.txn.AccountOpenAuthorizationGrid;
import com.cbs.web.pojo.txn.AcctOpenPendingList;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class AccountOpenAuthorization {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String enterby;
    private boolean btnFlag;
    private HttpServletRequest req;
    private List<AcctOpenPendingList> pendingAcctList;
    private List<AccountOpenAuthorizationGrid> acDetailList;
    int currentRow;
    AcctOpenPendingList currentItem;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final String jndiHomeName = "AccountAuthorizationManagementFacade";
    private AccountAuthorizationManagementFacadeRemote accountAuthRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameAc = "AccountOpeningFacade";
    private AccountOpeningFacadeRemote acOpRemote = null;
    private CommonReportMethodsRemote reportMethodsRemote = null;

    public List<AccountOpenAuthorizationGrid> getAcDetailList() {
        return acDetailList;
    }

    public void setAcDetailList(List<AccountOpenAuthorizationGrid> acDetailList) {
        this.acDetailList = acDetailList;
    }

    public AcctOpenPendingList getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AcctOpenPendingList currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public List<AcctOpenPendingList> getPendingAcctList() {
        return pendingAcctList;
    }

    public void setPendingAcctList(List<AcctOpenPendingList> pendingAcctList) {
        this.pendingAcctList = pendingAcctList;
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

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    /** Creates a new instance of AccountOpenAuthorization */
    public AccountOpenAuthorization() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("nishant");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            accountAuthRemote = (AccountAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            acOpRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameAc);
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            pendingAcctGridLoad();
            this.setBtnFlag(true);
            this.setErrorMessage("");
            this.setMessage("");
            
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

    public void pendingAcctGridLoad() {
        pendingAcctList = new ArrayList<AcctOpenPendingList>();
        try {
            List resultList = new ArrayList();
            resultList = accountAuthRemote.accountNoOpenList(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    AcctOpenPendingList acOpen = new AcctOpenPendingList();
                    acOpen.setAcno(ele.get(0).toString());
                    pendingAcctList.add(acOpen);
                }
            } else {
                this.setErrorMessage("No Accounts Exists For Authorization !!!");
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String acNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acno"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));

        for (AcctOpenPendingList item : pendingAcctList) {
            if (item.getAcno().equalsIgnoreCase(acNo)) {
                currentItem = item;
            }
        }
    }

    public void loadAcDetailGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        acDetailList = new ArrayList<AccountOpenAuthorizationGrid>();
        try {
            List acDet = new ArrayList();
            acDet = accountAuthRemote.loadGrid(currentItem.getAcno(), this.orgnBrCode);
            String acNat = ftsPostRemote.getAccountNature(currentItem.getAcno());
            if (!acDet.isEmpty()) {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) 
                        || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    for (int i = 0; i < acDet.size(); i++) {
                        Vector ele = (Vector) acDet.get(i);
                        AccountOpenAuthorizationGrid detail = new AccountOpenAuthorizationGrid();
                        detail.setAcctNo(ele.get(0).toString());
                        detail.setCustName(ele.get(3).toString());
                        detail.setFatherName(ele.get(15).toString());
                        detail.setPrAddress(ele.get(8).toString());
                        detail.setCrAddress(ele.get(9).toString());
                        detail.setPhoneNo(ele.get(10).toString());
                        detail.setDob(ele.get(13).toString());
                        detail.setOccupation(ele.get(14).toString());
                        detail.setOperMode(ele.get(16).toString());
                        detail.setNominee(ele.get(17).toString());
                        detail.setGrName(ele.get(7).toString());
                        detail.setRelationship(ele.get(12).toString());
                        detail.setPanNo(ele.get(11).toString());
                        //detail.setRoi(ele.get(10).toString());
                        detail.setIntroAcctNo(ele.get(1).toString());
                        detail.setIntroName(ele.get(2).toString());
                        //detail.setLimit(ele.get(10).toString());
                        detail.setJtName1(ele.get(4).toString());
                        detail.setJtName2(ele.get(5).toString());
                        detail.setEnterBy(ele.get(6).toString());
                        enterby = ele.get(6).toString();
                        //detail.setAuthBy(ele.get(11).toString());
                        detail.setInstAmt("");
                        detail.setPrd("");
                        detail.setMatDt("");
                        detail.setMatAmt("");
                        
                        String cuType = "";
                        if(ele.get(18) != null){
                            String cType = ele.get(18).toString();
                            String dsgTypeDesc = reportMethodsRemote.getRefRecDesc("235", cType);
                            cuType = dsgTypeDesc;
//                            if(cType.equalsIgnoreCase("SC")){
//                                cuType ="Senior Citizen";
//                            }else if(cType.equalsIgnoreCase("ST")){
//                                cuType ="Staff";
//                            }else if(cType.equalsIgnoreCase("OT")){
//                                cuType ="Others";
//                            }
                        }
                        detail.setCustTp(cuType);
                        acDetailList.add(detail);
                        this.setBtnFlag(false);
                    }
                } else {
                    for (int i = 0; i < acDet.size(); i++) {
                        Vector ele = (Vector) acDet.get(i);
                        AccountOpenAuthorizationGrid detail = new AccountOpenAuthorizationGrid();
                        detail.setAcctNo(ele.get(0).toString());
                        detail.setCustName(ele.get(3).toString());
                        detail.setFatherName(ele.get(17).toString());
                        detail.setPrAddress(ele.get(10).toString());
                        detail.setCrAddress(ele.get(11).toString());
                        detail.setPhoneNo(ele.get(12).toString());
                        detail.setDob(ele.get(15).toString());
                        detail.setOccupation(ele.get(16).toString());
                        detail.setOperMode(ele.get(19).toString());
                        detail.setNominee(ele.get(9).toString());
                        detail.setGrName(ele.get(8).toString());
                        detail.setRelationship(ele.get(14).toString());
                        detail.setPanNo(ele.get(13).toString());
                        detail.setRoi(ele.get(18).toString());
                        detail.setIntroAcctNo(ele.get(1).toString());
                        detail.setIntroName(ele.get(2).toString());
                        detail.setLimit(Double.parseDouble(ele.get(4).toString()));
                        detail.setJtName1(ele.get(5).toString());
                        detail.setJtName2(ele.get(6).toString());
                        detail.setEnterBy(ele.get(7).toString());
                        enterby = ele.get(7).toString();
                        
                        String matAmts = "0";
                        if (acNat.equalsIgnoreCase("RD")) {
                            matAmts = acOpRemote.cbsRdCalculation(Float.parseFloat(ele.get(21).toString()), Integer.parseInt(ele.get(20).toString()), Float.parseFloat(ele.get(18).toString()));
                            detail.setInstAmt(ele.get(21).toString());
                            detail.setPrd(ele.get(20).toString());
                            detail.setMatDt(ele.get(22).toString());
                            detail.setMatAmt(matAmts);
                        }else {
                            detail.setInstAmt("");
                            detail.setPrd("");
                            detail.setMatDt("");
                            detail.setMatAmt("");
                        }
                        detail.setCustTp("");
                        acDetailList.add(detail);
                        this.setBtnFlag(false);
//                        detail.setAuthBy(ele.get(11).toString());
                    }
                }
            } else {
                this.setBtnFlag(true);
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    public void accountAuthorization() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.enterby.equalsIgnoreCase("") || this.enterby.length() == 0) {
                this.setErrorMessage("Enter By Name Is Not Present So This A/C. Cannot Be Authorized.");
                return;
            }
            if (enterby.equalsIgnoreCase(this.userName)) {
                this.setErrorMessage("SORRY,YOU CAN NOT PASS YOUR OWN ENTRY !!!");
                return;
            }
            String result = accountAuthRemote.authorizeAcOpen(currentItem.getAcno(), this.enterby, this.userName);
            if (result == null) {
                this.setErrorMessage("AUTHORIZATION NOT DONE.");
                return;
            } else {
                if (result.equalsIgnoreCase("AUTHORIZATION SUCCESSFUL")) {
                    this.setMessage(result);
                    acDetailList = new ArrayList<AccountOpenAuthorizationGrid>();
                } else {
                    this.setErrorMessage(result);
                }
            }
            this.setBtnFlag(true);
            pendingAcctGridLoad();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExitAction() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            acDetailList = new ArrayList<AccountOpenAuthorizationGrid>();
            pendingAcctGridLoad();
            this.setBtnFlag(true);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
