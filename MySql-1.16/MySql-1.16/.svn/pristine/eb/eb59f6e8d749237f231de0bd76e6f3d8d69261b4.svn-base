/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.loan.RepaymentShedulePojo;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ankit Verma
 */
public class RepaymentShedule extends BaseBean {

    private String acno, acNoLen;
    private String selectPeriodicity;
    private String amount;
    private String msg;
    private String period;
    private Date emiDate;
    LoanGenralFacadeRemote facadeRemote;
    RepaymentShedulePojo pojo;
    List<RepaymentShedulePojo> sheduleList;

    /** Creates a new instance of RepaymentShedule */
    public RepaymentShedule() {
        try {
            facadeRemote = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            this.setAcNoLen(getAcNoLength());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }

    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSelectPeriodicity() {
        return selectPeriodicity;
    }

    public Date getEmiDate() {
        return emiDate;
    }

    public void setEmiDate(Date emiDate) {
        this.emiDate = emiDate;
    }

    public void setSelectPeriodicity(String selectPeriodicity) {
        this.selectPeriodicity = selectPeriodicity;
    }

    public List<RepaymentShedulePojo> getSheduleList() {
        return sheduleList;
    }

    public void setSheduleList(List<RepaymentShedulePojo> sheduleList) {
        this.sheduleList = sheduleList;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public void saveData() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            pojo = new RepaymentShedulePojo();
            pojo.setAcno(acno);
            pojo.setAmount(Double.parseDouble(amount));
            pojo.setPeriod(period);
            pojo.setSelectPeriodicity(selectPeriodicity);
            pojo.setEmiDate(emiDate);
            try {
                msg = facadeRemote.saveLoanRepaymentDetails(pojo);
                viewDetails();
                refresh();
            } catch (Exception e) {
                setMsg(e.getMessage());
            }
        }
    }

    public void refresh() {
        acno = "";
        amount = "0.0";
        period = "0";
        selectPeriodicity = "0";
        emiDate = null;
    }

    public void refreshForm() {
        refresh();
        if (sheduleList != null) {
            sheduleList.clear();
        }
        msg = "";
    }

    public String validation() {
        Validator validator = new Validator();
        if (acno.equalsIgnoreCase("")) {
            return "Please insert Account no.";
        }
        if (!checkAcno()) {
            return "Please insert valid Account no.";
        }
        
        if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            return "Please insert valid Account no.";
        }
        
        if (amount.equalsIgnoreCase("")) {
            return "Please insert valid Installment Amount.";
        }
        if (!validator.validateDoubleAll(amount)) {
            return "Please insert valid Installment Amount.";
        }
        if (selectPeriodicity.equalsIgnoreCase("0")) {
            return "Please select periodicity.";
        }
        if (period.equalsIgnoreCase("")) {
            return "Please insert No. of Installments.";
        }
        if (!validator.validateInteger(period)) {
            return "Please insert numeric No. of Installments.";
        }
        if (emiDate == null || !validator.validateDate(new SimpleDateFormat("dd/MM/yyyy").format(emiDate))) {
            return "Please insert valid date";
        }
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (sdf.parse(sdf.format(date)).after(emiDate)) {
                return "EMI Date should be equal or greater than current date.";
            }
        } catch (Exception e) {
        }

        return "ok";
    }

    public boolean checkAcno() {
        try {
            return facadeRemote.checkAcno(acno, getOrgnBrCode());
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
        return false;
    }

    public void viewDetails() {
        try {
            sheduleList = facadeRemote.getSheduleList(acno);
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }
}
