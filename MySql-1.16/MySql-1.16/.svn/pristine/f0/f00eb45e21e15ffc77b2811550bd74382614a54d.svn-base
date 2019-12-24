/*
 * CREATED BY       :  ROHIT KRISHNA GUPTA
 * CREATION DATE    :  29 OCT 2010
 */
package com.cbs.web.controller.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.AcctEditAuthOtherAcNatGrid;
import com.cbs.web.pojo.txn.AcctEditAuthPendingList;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
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
public class AccountEditAuthorization {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String enterby;
    private boolean gridFlag;
    private boolean btnFlag;
    private HttpServletRequest req;
    private List<AcctEditAuthOtherAcNatGrid> otherAcNatListOld;
    private List<AcctEditAuthOtherAcNatGrid> otherAcNatListNew;
    private List<AcctEditAuthOtherAcNatGrid> FDAcNatListOld;
    private List<AcctEditAuthOtherAcNatGrid> FDAcNatListNew;
    private List<AcctEditAuthPendingList> pendingAcctList;
    int currentRow;
    AcctEditAuthPendingList currentItem;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final String jndiHomeName = "AccountAuthorizationManagementFacade";
    private AccountAuthorizationManagementFacadeRemote accountAuthRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;

    public List<AcctEditAuthOtherAcNatGrid> getFDAcNatListNew() {
        return FDAcNatListNew;
    }

    public void setFDAcNatListNew(List<AcctEditAuthOtherAcNatGrid> FDAcNatListNew) {
        this.FDAcNatListNew = FDAcNatListNew;
    }

    public List<AcctEditAuthOtherAcNatGrid> getFDAcNatListOld() {
        return FDAcNatListOld;
    }

    public void setFDAcNatListOld(List<AcctEditAuthOtherAcNatGrid> FDAcNatListOld) {
        this.FDAcNatListOld = FDAcNatListOld;
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

    public List<AcctEditAuthOtherAcNatGrid> getOtherAcNatListNew() {
        return otherAcNatListNew;
    }

    public void setOtherAcNatListNew(List<AcctEditAuthOtherAcNatGrid> otherAcNatListNew) {
        this.otherAcNatListNew = otherAcNatListNew;
    }

    public List<AcctEditAuthOtherAcNatGrid> getOtherAcNatListOld() {
        return otherAcNatListOld;
    }

    public void setOtherAcNatListOld(List<AcctEditAuthOtherAcNatGrid> otherAcNatListOld) {
        this.otherAcNatListOld = otherAcNatListOld;
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

    public AcctEditAuthPendingList getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AcctEditAuthPendingList currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<AcctEditAuthPendingList> getPendingAcctList() {
        return pendingAcctList;
    }

    public void setPendingAcctList(List<AcctEditAuthPendingList> pendingAcctList) {
        this.pendingAcctList = pendingAcctList;
    }

    public boolean isGridFlag() {
        return gridFlag;
    }

    public void setGridFlag(boolean gridFlag) {
        this.gridFlag = gridFlag;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    /** Creates a new instance of AccountEditAuthorization */
    public AccountEditAuthorization() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager2");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            accountAuthRemote = (AccountAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            pendingAcctGridLoad();
            this.setGridFlag(true);
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
        pendingAcctList = new ArrayList<AcctEditAuthPendingList>();
        try {
            List resultList = new ArrayList();
            resultList = accountAuthRemote.accountNoList(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    AcctEditAuthPendingList acEdit = new AcctEditAuthPendingList();
                    acEdit.setAcno(ele.get(0).toString());
                    acEdit.setTxnId(ele.get(1).toString());
                    pendingAcctList.add(acEdit);
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
        for (AcctEditAuthPendingList item : pendingAcctList) {
            if (item.getAcno().equalsIgnoreCase(acNo)) {
                currentItem = item;
            }
        }
    }

    public void loadAcDetailGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        otherAcNatListOld = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        otherAcNatListNew = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        FDAcNatListOld = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        FDAcNatListNew = new ArrayList<AcctEditAuthOtherAcNatGrid>();

        try {
            List acDet = new ArrayList();
            //List acNatList = accountAuthRemote.accountNature(currentItem.getAcno().substring(2, 4));
            acDet = accountAuthRemote.loadGrid(currentItem.getAcno(), currentItem.getTxnId(), this.orgnBrCode);
//            if (!acNatList.isEmpty()) {
//                Vector recLst = (Vector) acNatList.get(0);
//                acNat = recLst.get(0).toString();
//            }
            String acNat = ftsPostRemote.getAccountNature(currentItem.getAcno());
            if (!acDet.isEmpty()) {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    this.setGridFlag(false);
                    this.setBtnFlag(false);
                    for (int i = 0; i < acDet.size(); i++) {
                        Vector ele = (Vector) acDet.get(i);
                        AcctEditAuthOtherAcNatGrid detail = new AcctEditAuthOtherAcNatGrid();
                        AcctEditAuthOtherAcNatGrid detail1 = new AcctEditAuthOtherAcNatGrid();

                        /**FOR OLD DETAIL**/
                        detail.setCustName(ele.get(0).toString());
                        detail.setOperMode(ele.get(2).toString());
                        detail.setOccupation(ele.get(4).toString());
                        detail.setIntroAcctNo(ele.get(6).toString());
                        detail.setFatherName(ele.get(8).toString());
                        detail.setCrAddress(ele.get(10).toString());
                        detail.setPrAddress(ele.get(12).toString());
                        detail.setPhoneNo(ele.get(14).toString());
                        detail.setPanNo(ele.get(16).toString());
                        detail.setApplyTds(ele.get(18).toString());
                        detail.setTdsDocuments(ele.get(20).toString());
                        detail.setNominee(ele.get(22).toString());
                        detail.setRelationship(ele.get(24).toString());
                        detail.setJtName1(ele.get(26).toString());
                        detail.setJtName2(ele.get(28).toString());
                        detail.setJtName3(ele.get(30).toString());
                        detail.setGrName(ele.get(32).toString());
                        detail.setInstructions(ele.get(34).toString());
                        detail.setEnterBy(ele.get(36).toString());
                        
                        String cuType = "";
                        if(ele.get(39) != null){
                            String cType = ele.get(39).toString();
                            if(cType.equalsIgnoreCase("SC")){
                                cuType ="Senior Citizen";
                            }else if(cType.equalsIgnoreCase("ST")){
                                cuType ="Staff";
                            }else if(cType.equalsIgnoreCase("OT")){
                                cuType ="Others";
                            }
                        }                        
                        detail.setCust_type(cuType);
                        /**FOR NEW DETAIL**/
                        detail1.setCustNameNew(ele.get(1).toString());
                        detail1.setOperModeNew(ele.get(3).toString());
                        detail1.setOccupationNew(ele.get(5).toString());
                        detail1.setIntroAcctNoNew(ele.get(7).toString());
                        detail1.setFatherNameNew(ele.get(9).toString());
                        detail1.setCrAddressNew(ele.get(11).toString());
                        detail1.setPrAddressNew(ele.get(13).toString());
                        detail1.setPhoneNoNew(ele.get(15).toString());
                        detail1.setPanNoNew(ele.get(17).toString());
                        detail1.setApplyTdsNew(ele.get(19).toString());
                        detail1.setTdsDocumentsNew(ele.get(21).toString());
                        detail1.setNomineeNew(ele.get(23).toString());
                        detail1.setRelationshipNew(ele.get(25).toString());
                        detail1.setJtName1New(ele.get(27).toString());
                        detail1.setJtName2New(ele.get(29).toString());
                        detail1.setJtName3New(ele.get(31).toString());
                        detail1.setGrNameNew(ele.get(33).toString());
                        detail1.setInstructionsNew(ele.get(35).toString());
                        detail1.setEnterByNew(ele.get(37).toString());
                        
                        String cuTypeN = "";
                        if(ele.get(38) != null){
                            String cTypeN = ele.get(38).toString();
                            if(cTypeN.equalsIgnoreCase("SC")){
                                cuTypeN ="Senior Citizen";
                            }else if(cTypeN.equalsIgnoreCase("ST")){
                                cuTypeN ="Staff";
                            }else if(cTypeN.equalsIgnoreCase("OT")){
                                cuTypeN ="Others";
                            }
                        }
                        detail1.setCust_typeNew(cuTypeN);
                        enterby = ele.get(37).toString();
                        FDAcNatListOld.add(detail);
                        FDAcNatListNew.add(detail1);

                    }
                } else {
                    this.setGridFlag(true);
                    this.setBtnFlag(false);
                    for (int i = 0; i < acDet.size(); i++) {
                        Vector ele = (Vector) acDet.get(i);
                        AcctEditAuthOtherAcNatGrid detail = new AcctEditAuthOtherAcNatGrid();
                        AcctEditAuthOtherAcNatGrid detail1 = new AcctEditAuthOtherAcNatGrid();

                        /**FOR OLD DETAIL**/
                        detail.setCustName(ele.get(0).toString());
                        detail.setOperMode(ele.get(2).toString());
                        detail.setOccupation(ele.get(4).toString());
                        detail.setIntroAcctNo(ele.get(6).toString());
                        detail.setFatherName(ele.get(8).toString());
                        detail.setCrAddress(ele.get(10).toString());
                        detail.setPrAddress(ele.get(12).toString());
                        detail.setPhoneNo(ele.get(14).toString());
                        detail.setPanNo(ele.get(16).toString());
                        detail.setNominee(ele.get(18).toString());
                        detail.setRelationship(ele.get(20).toString());
                        detail.setJtName1(ele.get(22).toString());
                        detail.setJtName2(ele.get(24).toString());
                        detail.setJtName3(ele.get(26).toString());
                        detail.setMinBalCharges(ele.get(28).toString());
                        detail.setGrName(ele.get(30).toString());
                        detail.setInstructions(ele.get(32).toString());
                        detail.setEnterBy(ele.get(34).toString());                        

                        /**FOR NEW DETAIL**/
                        detail1.setCustNameNew(ele.get(1).toString());
                        detail1.setOperModeNew(ele.get(3).toString());
                        detail1.setOccupationNew(ele.get(5).toString());
                        detail1.setIntroAcctNoNew(ele.get(7).toString());
                        detail1.setFatherNameNew(ele.get(9).toString());
                        detail1.setCrAddressNew(ele.get(11).toString());
                        detail1.setPrAddressNew(ele.get(13).toString());
                        detail1.setPhoneNoNew(ele.get(15).toString());
                        detail1.setPanNoNew(ele.get(17).toString());
                        detail1.setNomineeNew(ele.get(19).toString());
                        detail1.setRelationshipNew(ele.get(21).toString());
                        detail1.setJtName1New(ele.get(23).toString());
                        detail1.setJtName2New(ele.get(25).toString());
                        detail1.setJtName3New(ele.get(27).toString());
                        detail1.setMinBalChargesNew(ele.get(29).toString());
                        detail1.setGrNameNew(ele.get(31).toString());
                        detail1.setInstructionsNew(ele.get(33).toString());
                        detail1.setEnterByNew(ele.get(35).toString());                        
                        enterby = ele.get(35).toString();
                        otherAcNatListOld.add(detail);
                        otherAcNatListNew.add(detail1);
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
        if (this.enterby.equalsIgnoreCase("") || this.enterby.length() == 0) {
            this.setErrorMessage("Enter By Name Is Not Present So This A/C. Cannot Be Authorized.");
            return;
        }
        if (this.enterby.equalsIgnoreCase(this.userName)) {
            this.setErrorMessage("SORRY,YOU CAN NOT PASS YOUR OWN ENTRY !!!");
            return;
        }
        try {
            String result = accountAuthRemote.authorizeAcEdit(currentItem.getAcno(), currentItem.getTxnId(), this.enterby, this.userName);
            if (result == null) {
                this.setErrorMessage("AUTHORIZATION NOT DONE.");
                return;
            } else {
                if (result.equalsIgnoreCase("AUTHORIZATION SUCCESSFUL")) {
                    this.setMessage(result);
                    otherAcNatListOld = new ArrayList<AcctEditAuthOtherAcNatGrid>();
                    otherAcNatListNew = new ArrayList<AcctEditAuthOtherAcNatGrid>();
                    FDAcNatListOld = new ArrayList<AcctEditAuthOtherAcNatGrid>();
                    FDAcNatListNew = new ArrayList<AcctEditAuthOtherAcNatGrid>();
                    this.setBtnFlag(true);
                } else {
                    this.setErrorMessage(result);
                }
            }
            pendingAcctGridLoad();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        otherAcNatListOld = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        otherAcNatListNew = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        FDAcNatListOld = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        FDAcNatListNew = new ArrayList<AcctEditAuthOtherAcNatGrid>();
        this.setErrorMessage("");
        this.setMessage("");
        pendingAcctGridLoad();
        this.setBtnFlag(true);
        return "case1";
    }
}
