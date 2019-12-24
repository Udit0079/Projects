/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class IdChange extends BaseBean {
    
    private String message;
    private String accNo;
    private String custName;
    private String opMode;
    private String jtId;
    private String jtCustName;
    private String remark;
    private String globalOpMode;
    private TransactionManagementFacadeRemote remoteObject;    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }

    public String getJtCustName() {
        return jtCustName;
    }

    public void setJtCustName(String jtCustName) {
        this.jtCustName = jtCustName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGlobalOpMode() {
        return globalOpMode;
    }

    public void setGlobalOpMode(String globalOpMode) {
        this.globalOpMode = globalOpMode;
    }
    
    public IdChange() {
        try {
            remoteObject = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void getAccDetails () {
        this.setMessage("");
        try {
            if((this.accNo == null) || (this.accNo.equalsIgnoreCase(""))){
                this.setMessage("Account No. can't be blank");
                return;
            }
            
            if(!this.accNo.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Account No. must be of your branch");
                return;
            }
            
            List listForAcc = remoteObject.getAccDetailForChange(this.getAccNo());
            if(!listForAcc.isEmpty()){
                Vector v1 = (Vector) listForAcc.get(0);
                this.custName = v1.get(1).toString();
                opMode = v1.get(5).toString();
                this.setJtId(v1.get(2).toString());
                this.setJtCustName(v1.get(3).toString());
                globalOpMode = v1.get(4).toString();
            }else{
                this.setMessage("Details Not Exist For This Account");
                return;
            }            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    
    public void refreshForm() {
        this.setMessage("");
        this.setAccNo("");
        this.setCustName("");
        this.setJtId("");
        this.setJtCustName("");
        this.setOpMode("");
        this.setRemark("");
    }
    
    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
    
    public void saveDetails(){
        try{
            if((this.accNo == null) || (this.accNo.equalsIgnoreCase(""))){
                this.setMessage("Account No. can't be blank");
                return;
            }
            
            if((this.remark == null) || (this.remark.equalsIgnoreCase(""))){
                this.setMessage("Remarks is necessary to change customer id");
                return;
            }
            
            if(!this.remark.toUpperCase().contains("DEATH")){
                this.setMessage("Not a valid remark to change customer id");
                return;
            }
            
            if(!globalOpMode.equalsIgnoreCase("2")){
                this.setMessage("Not a valid Operation to change customer id");
                return;
            }
            
            String result = remoteObject.changeCustomerIdData(this.getAccNo(), this.jtId, this.remark, this.getOrgnBrCode(), this.getUserName());
            
            if(result.equalsIgnoreCase("true")){
                this.setMessage("Id Change Successfully");
                this.setAccNo("");
                this.setCustName("");
                this.setJtId("");
                this.setJtCustName("");
                this.setOpMode("");
                this.setRemark("");
            }
        }catch (Exception e) {
            setMessage(e.getMessage());
        }
    }    
}
