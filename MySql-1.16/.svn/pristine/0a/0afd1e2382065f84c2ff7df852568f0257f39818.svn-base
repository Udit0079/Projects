package com.cbs.web.controller.bill;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class RevalidationOfRemittance extends BaseBean {

    private String remType;
    private String instNo;
    private String seqNo;
    private String instNo1;
    private String remtType;
    private String timeLimit;
    private String custName;
    private String payAt;
    private String amount;
    private Date originalDt;
    private String inFavOf;
    private Date newRemDate;
    private String message;
    private List<SelectItem> remittanceType;
    private BillCommissionFacadeRemote revalidateEjb;

    public List<SelectItem> getRemittanceType() {
        return remittanceType;
    }

    public void setRemittanceType(List<SelectItem> remittanceType) {
        this.remittanceType = remittanceType;
    }

    public String getRemType() {
        return remType;
    }

    public void setRemType(String remType) {
        this.remType = remType;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getInFavOf() {
        return inFavOf;
    }

    public void setInFavOf(String inFavOf) {
        this.inFavOf = inFavOf;
    }

    public String getInstNo1() {
        return instNo1;
    }

    public void setInstNo1(String instNo1) {
        this.instNo1 = instNo1;
    }

    public Date getNewRemDate() {
        return newRemDate;
    }

    public void setNewRemDate(Date newRemDate) {
        this.newRemDate = newRemDate;
    }

    public Date getOriginalDt() {
        return originalDt;
    }

    public void setOriginalDt(Date originalDt) {
        this.originalDt = originalDt;
    }

    public String getPayAt() {
        return payAt;
    }

    public void setPayAt(String payAt) {
        this.payAt = payAt;
    }

    public String getRemtType() {
        return remtType;
    }

    public void setRemtType(String remtType) {
        this.remtType = remtType;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RevalidationOfRemittance() {
        try {
            revalidateEjb = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            remittanceType = new ArrayList<SelectItem>();
            remittanceType.add(new SelectItem("--Select--"));
            List result = revalidateEjb.billTypeLoad();
            for(int i=0; i<result.size(); i++){
                Vector vect = (Vector) result.get(i);
                remittanceType.add(new SelectItem(vect.get(0).toString()));
            }
            //remittanceType.add(new SelectItem("PO"));
            //remittanceType.add(new SelectItem("DD"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void instDetail() {
        try {
            refreshForm();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            if (this.remType.equalsIgnoreCase("--Select--")) {
                this.setMessage("You Must Select Remittance Type.");
                return;
            }
            if (this.instNo == null || this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
                this.setMessage("Please Enter Instrument Number.");
                return;
            }
            List result = revalidateEjb.instFind(this.remType, this.instNo, getOrgnBrCode());
            Vector a = (Vector) result.get(0);
            String originDt = ymd.format(dmy.parse(a.get(7).toString()));
            Long longChqDt = Long.parseLong(CbsUtil.monthAdd(originDt, 3));

            if (longChqDt.compareTo(Long.parseLong(ymd.format(new Date()))) >= 0) {
                throw new ApplicationException("Instrument Date must be more than three months old.");
                
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.setSeqNo(a.get(0).toString());
            this.setInstNo1(a.get(1).toString());
            this.setRemtType(a.get(2).toString());
            this.setTimeLimit(a.get(3).toString());
            
            this.setCustName(a.get(4).toString());
            this.setPayAt(a.get(5).toString());
            this.setAmount(a.get(6).toString());
            
            Date orgnDt = sdf.parse(a.get(7).toString());
            this.setOriginalDt(orgnDt);
            this.setInFavOf(a.get(8).toString());
            
            Date newDt = sdf.parse(a.get(9).toString());
            this.setNewRemDate(newDt);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveRevalidationInfo() throws NamingException {
        this.setMessage("");
        if (this.remType.equals("--Select--")) {
            this.setMessage("You Must Select Remittance Type.");
            return;
        }
        if (this.instNo.equals("") || (this.instNo.length() == 0)) {
            this.setMessage("You Must Enter Instrument No.");
            return;
        }
        if (this.remtType.equals("") || (this.remtType.length() == 0)) {
            this.setMessage("Cannot Update Please Check Instrument No.");
            return;
        }
        if (this.originalDt == null) {
            this.setMessage("Original Date Is Blank !!!");
            return;
        }
        if (this.newRemDate == null) {
            this.setMessage("Please Enter New Remmitance Date.");
            return;
        }
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String saveResult = revalidateEjb.revalidateSave(this.remtType, this.instNo, ymd.format(this.originalDt), ymd.format(this.newRemDate), getUserName(), getOrgnBrCode());
            if (saveResult == null) {
                this.setMessage("There is problem in updation !");
                return;
            } else {
                if (saveResult.contains("!")) {
                    this.setMessage(saveResult);
                } else {
                    this.setMessage(saveResult);
                    this.setInstNo1("");
                    this.setSeqNo("");
                    this.setRemtType("");
                    this.setTimeLimit("");
                    this.setCustName("");
                    this.setPayAt("");
                    this.setAmount("");
                    this.setInFavOf("");
                    this.setNewRemDate(null);
                    this.setOriginalDt(null);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setInstNo1("");
        this.setSeqNo("");
        this.setRemtType("");
        this.setTimeLimit("");
        this.setCustName("");
        this.setPayAt("");
        this.setAmount("");
        this.setMessage("");
        this.setInFavOf("");
        this.setNewRemDate(null);
        this.setOriginalDt(null);
    }

    public void resetForm() {
        this.setRemType("--Select--");
        this.setInstNo("");
        this.setInstNo1("");
        this.setSeqNo("");
        this.setRemtType("");
        this.setTimeLimit("");
        this.setCustName("");
        this.setPayAt("");
        this.setAmount("");
        this.setMessage("");
        this.setInFavOf("");
        this.setNewRemDate(null);
        this.setOriginalDt(null);
    }

    public String exitFrm() {
        resetForm();
        return "case1";
    }
}
