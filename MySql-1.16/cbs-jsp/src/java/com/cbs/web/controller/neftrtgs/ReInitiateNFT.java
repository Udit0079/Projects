/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.ReInitiateNftPojo;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.List;
import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
import com.cbs.utils.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author root
 */
public class ReInitiateNFT extends BaseBean {

    private String refno;
    private String errorMsg;
    private String beneficiaryName;
    private String beneficiaryIFSC;
    private String amount;
    private String creditAccountNo;
    private String debitAccountNo;
    private String paymentDate;
    private String txnDate;
    private String enterDate;
    ReInitiateNftPojo tempCurrentItem;
    private Date dt = new Date();
    private List<ReInitiateNftPojo> gridDetail;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public List<ReInitiateNftPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ReInitiateNftPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ReInitiateNftPojo getTempCurrentItem() {
        return tempCurrentItem;
    }

    public void setTempCurrentItem(ReInitiateNftPojo tempCurrentItem) {
        this.tempCurrentItem = tempCurrentItem;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryIFSC() {
        return beneficiaryIFSC;
    }

    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
        this.beneficiaryIFSC = beneficiaryIFSC;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreditAccountNo() {
        return creditAccountNo;
    }

    public void setCreditAccountNo(String creditAccountNo) {
        this.creditAccountNo = creditAccountNo;
    }

    public String getDebitAccountNo() {
        return debitAccountNo;
    }

    public void setDebitAccountNo(String debitAccountNo) {
        this.debitAccountNo = debitAccountNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public ReInitiateNFT() throws ApplicationException {
        setTodayDate(dmy.format(dt));
        setEnterDate(dmy.format(dt));
        try {
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void gridDetails() throws ApplicationException {
        setErrorMsg("");
        try {
                
            if (enterDate == null || enterDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill date !");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(enterDate)) {
                setErrorMsg("Please fill proper date !");
                return;
            }
            gridDetail = new ArrayList<>();
           
            List dataList = remoteInterface.getReInitateNftDetails(ymd.format(dmy.parse(this.enterDate)),getOrgnBrCode());
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector vtr = (Vector) dataList.get(i);
                    ReInitiateNftPojo pojo = new ReInitiateNftPojo();
                    pojo.setRefno(vtr.get(0).toString());
                    pojo.setBeneficiaryName(vtr.get(1).toString());
                    pojo.setBeneficiaryIFSC(vtr.get(2).toString());
                    pojo.setAmount(vtr.get(3).toString());
                    pojo.setCreditAccountNo(vtr.get(4).toString());
                    pojo.setDebitAccountNo(vtr.get(5).toString());
                    pojo.setPaymentDate(vtr.get(6).toString());
                    pojo.setTxnDate(vtr.get(7).toString());
                    pojo.setAuth(vtr.get(8).toString());
                    pojo.setAuthby(vtr.get(9).toString());
                    pojo.setStatus(vtr.get(10).toString());
                    gridDetail.add(pojo);
                }
            } else {
                throw new ApplicationException("Data does not exits.");
            }

        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void update() throws ApplicationException {
        setErrorMsg("");
        try {
            if (tempCurrentItem == null) {
                this.setErrorMsg("Please select row from table !");
                return;
            }
            String result = remoteInterface.getreinitateNeftupdation(tempCurrentItem.getRefno(), getUserName(), getTodayDate());
            if (!result.equalsIgnoreCase("true")) {
                this.setErrorMsg(result);
                return;
            }
            this.setErrorMsg("Transcation has been done successfully !!!");
            gridDetail.remove(tempCurrentItem);
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        setEnterDate(dmy.format(dt));
        gridDetail = new ArrayList<>();
    }

    public String exit() {
        return "case1";
    }
}
