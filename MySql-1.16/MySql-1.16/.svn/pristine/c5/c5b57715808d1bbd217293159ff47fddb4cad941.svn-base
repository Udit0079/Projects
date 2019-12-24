/*
 * Created By: ROHIT KRISHNA GUPTA
 * Creation Date: 04 Oct 2010
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.txn.IBCOBCAuthGrid;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class IBCOBCAuthorization {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String billType;
    private String list;
    private boolean flag;
    private List<SelectItem> billTypeList;
    private HttpServletRequest req;
    int currentRow;
    IBCOBCAuthGrid currentItem;
    private List<IBCOBCAuthGrid> gridDetail;
    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;

    public IBCOBCAuthGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(IBCOBCAuthGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<IBCOBCAuthGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<IBCOBCAuthGrid> gridDetail) {
        this.gridDetail = gridDetail;
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /** Creates a new instance of IBCOBCAuthorization */
    public IBCOBCAuthorization() {
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
            this.setFlag(true);
            this.setErrorMessage("");
            this.setMessage("");
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("OBC"));
            billTypeList.add(new SelectItem("IBC"));
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
//            billTypeList.add(new SelectItem("Bank Guarantee"));
//            billTypeList.add(new SelectItem("Letter of Credit"));
//            billTypeList.add(new SelectItem("Local Bill Purchase"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void gridLoad() {
        this.setFlag(true);
//        this.setErrorMessage("");
//        this.setMessage("");
        if (this.billType.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please Select Bill Type.");
            gridDetail = new ArrayList<IBCOBCAuthGrid>();
            return;
        }

        gridDetail = new ArrayList<IBCOBCAuthGrid>();
        try {
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            resultList = otherAuthRemote.ibcObcOnLoad(this.billType, this.orgnBrCode);
            if (!resultList.isEmpty()) {
                if (this.billType.equalsIgnoreCase("OBC")) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        IBCOBCAuthGrid detail = new IBCOBCAuthGrid();
                        detail.setsNo(i + 1);
                        detail.setBillType(ele.get(0).toString());
                        detail.setAcNo(ele.get(1).toString());
                        resultList1 = otherAuthRemote.setPartyName(this.billType, ele.get(1).toString());
                        Vector secLst2 = (Vector) resultList1.get(0);
                        detail.setPartyName(secLst2.get(0).toString());
                        detail.setInstNo(ele.get(2).toString());
                        detail.setInstAmt(ele.get(3).toString());
                        detail.setInstDt(ele.get(4).toString());
                        detail.setEnterBy(ele.get(5).toString());
                        detail.setAuth(ele.get(6).toString());
                        detail.setBankName(ele.get(7).toString());
                        detail.setBankAdd(ele.get(8).toString());
                        detail.setColBankname(ele.get(9).toString());
                        detail.setColBankAdd(ele.get(10).toString());
                        gridDetail.add(detail);
                    }
                } else if (this.billType.equalsIgnoreCase("IBC")) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        IBCOBCAuthGrid detail = new IBCOBCAuthGrid();
                        detail.setsNo(i + 1);
                        detail.setBillType(ele.get(0).toString());
                        detail.setAcNo(ele.get(1).toString());
                        resultList1 = otherAuthRemote.setPartyName(this.billType, ele.get(1).toString());
                        Vector secLst2 = (Vector) resultList1.get(0);
                        detail.setPartyName(secLst2.get(0).toString());
                        detail.setInstNo(ele.get(2).toString());
                        detail.setInstAmt(ele.get(3).toString());
                        detail.setInstDt(ele.get(4).toString());
                        detail.setEnterBy(ele.get(5).toString());
                        detail.setAuth(ele.get(6).toString());
                        detail.setBankName(ele.get(7).toString());
                        detail.setBankAdd(ele.get(8).toString());
                        detail.setColBankname("");
                        detail.setColBankAdd("");
                        gridDetail.add(detail);
                    }
                }

            } else {
                this.setErrorMessage("No Data Exists For Authorization!!!");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String userId = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (IBCOBCAuthGrid item : gridDetail) {
            if (item.getAcNo().equalsIgnoreCase(userId)) {
                currentItem = item;
            }
        }
    }

    public void refreshForm() {
        this.setBillType("--Select--");
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag(true);
        gridDetail = new ArrayList<IBCOBCAuthGrid>();
    }
    String acno;
    String instNo;
    String enterby;
    String billTy;
    Double instAm;
    String instrDt;

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag(false);
        try {
            this.setErrorMessage("You Have Selected Row No.: " + currentItem.getsNo() + " For Account No. : " + currentItem.getAcNo() + " And Intrument No. : " + currentItem.getInstNo());
            acno = currentItem.getAcNo();
            instNo = currentItem.getInstNo();
            enterby = currentItem.getEnterBy();
            billTy = currentItem.getBillType();
            instAm = Double.parseDouble(currentItem.getInstAmt());
            instrDt = currentItem.getInstDt().substring(6, 10) + currentItem.getInstDt().substring(3, 5) + currentItem.getInstDt().substring(0, 2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void accountAuthorization() {
        this.setErrorMessage("");
        this.setMessage("");
        String saveResult = null;
        try {
            //System.out.println("INPUTS:======="+orgnBrCode+"-"+userName+"-"+enterby+"-"+billType+"-"+billTy+"-"+acno+"-"+instNo+"-"+instAm+"-"+this.todayDate.substring(6));
            saveResult = otherAuthRemote.ibcObcAuth(this.orgnBrCode, this.userName, enterby, this.billType, billTy, acno, instNo, instAm, this.todayDate.substring(6), instrDt);
            if (saveResult == null) {
                this.setErrorMessage("AUTHORIZATION NOT DONE !!!");
                this.setFlag(true);
                gridLoad();

            } else {
                if (saveResult.substring(0, 4).equalsIgnoreCase("Your")) {
                    this.setMessage(saveResult);
                } else {
                    this.setErrorMessage(saveResult);
                }
                this.setFlag(true);
                gridLoad();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        String deleteResult = null;
        try {
            deleteResult = otherAuthRemote.ibcObcEntryDelete(this.userName, this.billType, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), Double.parseDouble(currentItem.getInstAmt()), currentItem.getInstDt(), currentItem.getEnterBy(), currentItem.getAuth(), currentItem.getBankName(), currentItem.getBankAdd(), currentItem.getColBankname(), currentItem.getColBankAdd(), this.orgnBrCode);
            if (deleteResult.equalsIgnoreCase("Data Deleted Succesfully")) {
                this.setMessage(deleteResult);
                this.setFlag(true);
                gridLoad();
            } else {
                this.setErrorMessage(deleteResult);
                this.setFlag(true);
                gridLoad();
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
