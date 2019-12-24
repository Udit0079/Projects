package com.cbs.web.controller.ho;

import com.cbs.dto.BatchGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.BatchDeletionRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Navneet Goyal
 */
public class BatchDeletion {

    private HttpServletRequest req;
    private String brCode, userName, todayDate, message, batchNo;
    private boolean disableBtnDelete, flag1;
    private BatchDeletionRemote batchDeletionRemote;
    private Validator validator;
    private List<BatchGrid> batchGrid;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd"),
            dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Integer totalRows;
    private Double crAmt, drAmt;
    private List<BatchData> batchList;
    private BatchData currentBatchData;
    private Date trsnDate;

    public BatchDeletion() {
        try {
            req = getRequest();
            batchDeletionRemote = (BatchDeletionRemote) ServiceLocator.getInstance().lookup("BatchDeletionBean");
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            brCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            setTodayDate(dmyFormat.format(date));
            validator = new Validator();
            onLoad();
            getOnLoadData();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getBatchData() {
        setMessage("");
        setFlag1(true);
        try {
            if (validator.validateInteger(batchNo)) {
                List<List> resultGrid = batchDeletionRemote.getBatchData(Integer.parseInt(batchNo), ymdFormat.format(trsnDate));
                if ((resultGrid == null) || (resultGrid.isEmpty())) {
                    setDisableBtnDelete(true);
                    setMessage("Batch  number does not exists!!!");
                    setFlag1(true);
                } else {
                    batchGrid = new ArrayList<BatchGrid>();
                    setDisableBtnDelete(false);
                    for (int i = 0; i < resultGrid.size(); i++) {
                        Vector tempVector = (Vector) resultGrid.get(i);
                        System.out.println(tempVector);
                        BatchGrid detail = new BatchGrid();
                        detail.setAcno(tempVector.elementAt(0).toString());
//
                        detail.setCustName(tempVector.elementAt(1).toString());
                        detail.setTy(tempVector.elementAt(2).toString());
                        if (detail.getAcno().substring(2, 4).equalsIgnoreCase("FD")) {
                            detail.setBalance(0.0d);
                        } else {
                            detail.setBalance(Double.parseDouble(batchDeletionRemote.getBalanceForAcNature(detail.getAcno())));
                        }
                        detail.setDramt(Double.parseDouble(tempVector.elementAt(4).toString()));
                        detail.setCramt(Double.parseDouble(tempVector.elementAt(5).toString()));
                        detail.setDetails(tempVector.elementAt(6).toString());
                        detail.setIy(tempVector.elementAt(7).toString());
                        if (tempVector.elementAt(8).toString().equalsIgnoreCase("1.0")) {
                            detail.setPayBy("CHEQUE");
                        } else if (tempVector.elementAt(8).toString().equalsIgnoreCase("2.0")) {
                            detail.setPayBy("W/S");
                        } else if (tempVector.elementAt(8).toString().equalsIgnoreCase("3.0")) {
                            detail.setPayBy("VOUCHER");
                        } else if (tempVector.elementAt(8).toString().equalsIgnoreCase("4.0")) {
                            detail.setPayBy("LOOSECHEQUE");
                        } else {
                            detail.setPayBy("VOUCHER");
                        }
                        detail.setInstNo(tempVector.elementAt(9).toString());
                        detail.setTranDesc(tempVector.elementAt(10).toString());
                        detail.setEnterBy(tempVector.elementAt(11).toString());
                        detail.setRecNo(tempVector.elementAt(12).toString());
//                        if (detail.getAcno().substring(2, 4).equalsIgnoreCase("FD")) {
//                            detail.setIntFlag(tempVector.elementAt(12).toString());
//                        } else {
//                            detail.setIntFlag("");
//                        }
//                        detail.setTrsNo(tempVector.elementAt(13).toString());
                        detail.setTableName(tempVector.elementAt(13).toString());
                        setFlag1(false);
                        batchGrid.add(detail);
                    }
                    double cr = 0.0d;
                    double dr = 0.0d;
//                    for (BatchGrid tmpBean : batchGrid) {
//                        cr = cr + tmpBean.getCramt();
//                        dr = dr + tmpBean.getDramt();
//                        setCrAmt(cr);
//                        setDrAmt(dr);
//                    }
//                    if (crAmt == drAmt) {
//                        setDisableBtnDelete(false);
//                    } else {
//                        setDisableBtnDelete(true);
//                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    private void getOnLoadData() {
        try {
            batchList = new ArrayList<BatchData>();
            List resultList = batchDeletionRemote.getBatchNoForDeletion();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector tempVector = (Vector) resultList.get(i);
                    BatchData data = new BatchData();
                    data.setBatchNo(tempVector.elementAt(0).toString());
                    data.setDate(dmyFormat.format(tempVector.elementAt(1)));
                    batchList.add(data);
                }
            } else {
                setMessage("No Batch Data !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setBatchDeletionDetailsRowValues() {
        try {
            batchNo = currentBatchData.getBatchNo();
            trsnDate = dmyFormat.parse(currentBatchData.getDate());
            getBatchData();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
       return "case1";
    }

    public class BatchData {

        String batchNo, date;

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private void onLoad() {
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<BatchGrid> getBatchGrid() {
        return batchGrid;
    }

    public void setBatchGrid(List<BatchGrid> batchGrid) {
        this.batchGrid = batchGrid;
    }

    public Double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(Double crAmt) {
        this.crAmt = crAmt;
    }

    public Double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(Double drAmt) {
        this.drAmt = drAmt;
    }

    public boolean isDisableBtnDelete() {
        return disableBtnDelete;
    }

    public void setDisableBtnDelete(boolean disableBtnDelete) {
        this.disableBtnDelete = disableBtnDelete;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public List<BatchData> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<BatchData> batchList) {
        this.batchList = batchList;
    }

    public BatchData getCurrentBatchData() {
        return currentBatchData;
    }

    public void setCurrentBatchData(BatchData currentBatchData) {
        this.currentBatchData = currentBatchData;
    }
}
