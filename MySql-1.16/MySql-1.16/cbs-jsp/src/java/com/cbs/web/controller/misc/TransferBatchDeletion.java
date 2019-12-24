    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class TransferBatchDeletion {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private String batchNo;
    String creditAmt;
    String debitAmt;
    boolean deleteBtn;
    Context ctx;
    TransferBatchDeletionFacadeRemote remoteObject;
    ArrayList<TransferBatchDeletionGrid> gridData;
    TransferBatchDeletionGrid rd;
    private TransferBatchDeletionGrid element;
    private Iterator<TransferBatchDeletionGrid> i1;
    private String list;
    private String crAmt;
    private String drAmt;
    private boolean flag1;
    FtsPostingMgmtFacadeRemote facadeRemote;

    public boolean isDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(boolean deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public TransferBatchDeletionGrid getElement() {
        return element;
    }

    public void setElement(TransferBatchDeletionGrid element) {
        this.element = element;
    }

    public Iterator<TransferBatchDeletionGrid> getI1() {
        return i1;
    }

    public void setI1(Iterator<TransferBatchDeletionGrid> i1) {
        this.i1 = i1;
    }

    public ArrayList<TransferBatchDeletionGrid> getGridData() {
        return gridData;
    }

    public void setGridData(ArrayList<TransferBatchDeletionGrid> gridData) {
        this.gridData = gridData;
    }

    public TransferBatchDeletionGrid getRd() {
        return rd;
    }

    public void setRd(TransferBatchDeletionGrid rd) {
        this.rd = rd;
    }

    public String getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(String debitAmt) {
        this.debitAmt = debitAmt;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
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

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    /**
     * Creates a new instance of TransferBatchDel
     */
    public TransferBatchDeletion() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (TransferBatchDeletionFacadeRemote) ServiceLocator.getInstance().lookup("TransferBatchDeletionFacade");
            facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            this.crAmt = "0.0";
            this.drAmt = "0.0";
            this.setDeleteBtn(true);
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

    public void gridLoad() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag1(true);
        try {
            this.setErrorMessage("");
            gridData = new ArrayList<TransferBatchDeletionGrid>();
            this.crAmt = "0.0";
            this.drAmt = "0.0";
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
            if (resultGrid.isEmpty()) {
                this.setDeleteBtn(true);
                this.setErrorMessage("Batch  number does not exists!!!");
                this.setFlag1(true);
                gridData = new ArrayList<TransferBatchDeletionGrid>();
            } else {
                this.setDeleteBtn(false);
                for (int i = 0; i < resultGrid.size(); i++) {
                    Vector tempVector = (Vector) resultGrid.get(i);

                    TransferBatchDeletionGrid detail = new TransferBatchDeletionGrid();
                    detail.setAcno(tempVector.elementAt(0).toString());

                    detail.setCustName(tempVector.elementAt(1).toString());
                    String acNat = facadeRemote.getAccountNature(detail.getAcno());
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        detail.setBalance(0.0d);
                    } else {
                        detail.setBalance(Double.parseDouble(facadeRemote.ftsGetBal(detail.getAcno())));
                    }

                    detail.setTy(tempVector.elementAt(2).toString());
                    detail.setDramt(Double.parseDouble(tempVector.elementAt(3).toString()));
                    detail.setCramt(Double.parseDouble(tempVector.elementAt(4).toString()));
                    detail.setDetails(tempVector.elementAt(5).toString());
                    detail.setIy(tempVector.elementAt(6).toString());

                    if (tempVector.elementAt(7).toString().equalsIgnoreCase("1.0")) {
                        detail.setPayBy("CHEQUE");
                    } else if (tempVector.elementAt(7).toString().equalsIgnoreCase("2.0")) {
                        detail.setPayBy("W/S");
                    } else if (tempVector.elementAt(7).toString().equalsIgnoreCase("3.0")) {
                        detail.setPayBy("VOUCHER");
                    } else if (tempVector.elementAt(7).toString().equalsIgnoreCase("4.0")) {
                        detail.setPayBy("LOOSECHEQUE");
                    } else {
                        detail.setPayBy("VOUCHER");
                    }
                    detail.setInstNo(tempVector.elementAt(8).toString());
                    detail.setTranDesc(tempVector.elementAt(9).toString());
                    detail.setEnterBy(tempVector.elementAt(10).toString());
                    detail.setRecNo(tempVector.elementAt(11).toString());

                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        detail.setIntFlag(tempVector.elementAt(12).toString());
                    } else {
                        detail.setIntFlag("");
                    }
                    detail.setTrsNo(tempVector.elementAt(13).toString());
                    this.setFlag1(false);
                    gridData.add(detail);
                }
                double cr = 0.0d;
                double dr = 0.0d;
                NumberFormat formatter = new DecimalFormat("#0.00");
                for (TransferBatchDeletionGrid tmpBean : gridData) {
                    cr = cr + tmpBean.getCramt();
                    dr = dr + tmpBean.getDramt();

                    this.setCrAmt(String.valueOf(formatter.format(cr)));
                    this.setDrAmt(String.valueOf(formatter.format(dr)));
                }
                if (this.crAmt.equalsIgnoreCase(this.drAmt)) {
                    this.setDeleteBtn(false);
                } else {
                    this.setDeleteBtn(true);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
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

            String date = this.getTodayDate();
            String day = date.substring(0, 2);
            String month = date.substring(3, 5);
            String year = date.substring(6);
            date = year + month + day;

            String result = remoteObject.deleteBatchNoProc(date, Float.parseFloat(batchNo), this.orgnBrCode, this.userName);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("Sorry batch could not be deleted !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                } else if (result.equalsIgnoreCase("true")) {
                    this.setMessage("Batch deleted successfully.");
                    this.setCrAmt("0.0");
                    this.setDrAmt("0.0");
                }
                gridData = new ArrayList<TransferBatchDeletionGrid>();
            }
            this.setDeleteBtn(true);
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
            gridData = new ArrayList<TransferBatchDeletionGrid>();
            this.setCrAmt("0.0");
            this.setDrAmt("0.0");
            this.setDeleteBtn(true);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            this.setBatchNo("");
            resetForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
