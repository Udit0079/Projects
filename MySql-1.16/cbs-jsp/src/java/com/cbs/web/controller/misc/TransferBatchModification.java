/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  29 JUNE 2011
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.TransferBatchDeletionFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.misc.TransferBatchDeletionGrid;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TransferBatchModification {

    Context ctx;
    TransferBatchDeletionFacadeRemote remoteObject;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private String batchNo;
    private String detail;
    private List<TransferBatchDeletionGrid> gridDetail;
    int currentRow;
    private TransferBatchDeletionGrid currentItem = new TransferBatchDeletionGrid();
    private boolean flag1;
    FtsPostingMgmtFacadeRemote facadeRemote;

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public TransferBatchDeletionGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TransferBatchDeletionGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TransferBatchDeletionGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TransferBatchDeletionGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     * Creates a new instance of TransferBatchModification
     */
    public TransferBatchModification() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (TransferBatchDeletionFacadeRemote) ServiceLocator.getInstance().lookup("TransferBatchDeletionFacade");
            facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag1(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    String mesgDetail;

    public void gridLoadAccordingToBatch() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag1(true);
            gridDetail = new ArrayList<TransferBatchDeletionGrid>();
            ctx = new InitialContext();
            if (this.getTodayDate() == null) {
                this.setErrorMessage("DATE IS BLANK !!!");
                return;
            }
            String date = this.getTodayDate();
            String day = date.substring(0, 2);
            String month = date.substring(3, 5);
            String year = date.substring(6);
            date = year + month + day;
            if (this.batchNo == null || this.batchNo.equalsIgnoreCase("") || this.batchNo.length() == 0) {
                this.setErrorMessage("Please enter batch number");
                return;
            }
            Pattern pm = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher lockerNoCheck = pm.matcher(batchNo);
            if (!lockerNoCheck.matches()) {
                this.setErrorMessage("Please enter valid batch no(Numbers only).");
                return;
            }
            if (this.batchNo.contains(".")) {
                if (this.batchNo.indexOf(".") != this.batchNo.lastIndexOf(".")) {
                    this.setErrorMessage("Invalid batch No. Please fill batch Number correctly.");
                    return;
                } else if (this.batchNo.substring(batchNo.indexOf(".") + 1).length() > 1) {
                    this.setErrorMessage("Please fill batch Number correctly.");
                    return;
                }
            }
            List resultGrid = remoteObject.batchDetailGridLoad(date, Float.parseFloat(this.getBatchNo()), orgnBrCode);
            if ((resultGrid == null) || (resultGrid.isEmpty())) {
                this.setErrorMessage("Batch  number does not exists!!!");
                this.setFlag1(true);
                gridDetail = new ArrayList<TransferBatchDeletionGrid>();
            } else {
                for (int i = 0; i < resultGrid.size(); i++) {
                    //List tempList = resultGrid.get(i);
                    // for (int j = 0; j < tempList.size(); j++) {
                    Vector tempVector = (Vector) resultGrid.get(i);
                    TransferBatchDeletionGrid det = new TransferBatchDeletionGrid();
                    det.setAcno(tempVector.elementAt(0).toString());

                    det.setCustName(tempVector.elementAt(1).toString());
                    String acNat = facadeRemote.getAccountNature(det.getAcno());
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        det.setBalance(0.0d);
                    } else {
                        det.setBalance(Double.parseDouble(facadeRemote.ftsGetBal(det.getAcno())));
                    }

                    det.setTy(tempVector.elementAt(2).toString());
                    det.setDramt(Double.parseDouble(tempVector.elementAt(3).toString()));
                    det.setCramt(Double.parseDouble(tempVector.elementAt(4).toString()));
                    // det.setDetails(tempVector.elementAt(5).toString());
                    String viewDetail = tempVector.elementAt(5).toString();
                    if (viewDetail.indexOf("~`") != -1) {
                        String[] arr = viewDetail.split("~`");
                        viewDetail = arr[0];
                        mesgDetail = tempVector.elementAt(5).toString();
                        det.setDetails(viewDetail);
                    } else {
                        det.setDetails(tempVector.elementAt(5).toString());
                    }

                    det.setIy(tempVector.elementAt(6).toString());

                    if (tempVector.elementAt(7).toString().equalsIgnoreCase("1.0")) {
                        det.setPayBy("CHEQUE");
                    } else if (tempVector.elementAt(7).toString().equalsIgnoreCase("2.0")) {
                        det.setPayBy("W/S");
                    } else if (tempVector.elementAt(7).toString().equalsIgnoreCase("3.0")) {
                        det.setPayBy("VOUCHER");
                    } else if (tempVector.elementAt(7).toString().equalsIgnoreCase("4.0")) {
                        det.setPayBy("LOOSECHEQUE");
                    } else {
                        det.setPayBy("VOUCHER");
                    }
                    det.setInstNo(tempVector.elementAt(8).toString());
                    det.setTranDesc(tempVector.elementAt(9).toString());
                    det.setEnterBy(tempVector.elementAt(10).toString());
                    det.setRecNo(tempVector.elementAt(11).toString());

                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        det.setIntFlag(tempVector.elementAt(12).toString());
                    } else {
                        det.setIntFlag("");
                    }
                    det.setTrsNo(tempVector.elementAt(13).toString());
                    this.setFlag1(true);
                    gridDetail.add(det);
                }
                // }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setDetail("");
            this.setDetail(currentItem.getDetails());
            this.setFlag1(false);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.batchNo == null || this.batchNo.equalsIgnoreCase("") || this.batchNo.length() == 0) {
                this.setErrorMessage("Please enter batch number.");
                return;
            }
            if (this.batchNo.contains(".")) {
                this.setErrorMessage("Please enter valid batch number.");
                return;
            }
            Pattern pm = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher lockerNoCheck = pm.matcher(batchNo);
            if (!lockerNoCheck.matches()) {
                this.setErrorMessage("Please enter valid batch no(Numbers only).");
                return;
            }
            if (this.detail == null || this.detail.equalsIgnoreCase("") || this.detail.length() == 0) {
                this.setErrorMessage("Please enter detail to update.");
                return;
            }
            String newdetail = "";
            String oldDetail = "";

            if (currentItem.getCustName().contains("PAY")) {
                //if (!(this.detail.contains("SERVICE") || this.detail.contains("COMM"))) {
                newdetail = detail + mesgDetail;
                detail = newdetail;
                oldDetail = mesgDetail;
            } else {
                oldDetail = currentItem.getDetails();
            }

            String date = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            String result = remoteObject.updateDetailOfBatch(Float.parseFloat(this.batchNo), currentItem.getAcno(), Float.parseFloat(currentItem.getRecNo()), date, oldDetail, this.detail, currentItem.getEnterBy(), this.orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured.");
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                this.setDetail("");
                this.setFlag1(true);
                return;
            } else {
                this.setMessage(result);
            }
            this.setDetail("");
            this.setFlag1(true);
            this.setBatchNo("");
            gridDetail = new ArrayList<TransferBatchDeletionGrid>();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setBatchNo("");
            gridDetail = new ArrayList<TransferBatchDeletionGrid>();
            this.setDetail("");
            this.setFlag1(true);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
