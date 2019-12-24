/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.admin.TDLienMarkingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public class AccountTransfer extends BaseBean {
    
    private String msg;
    private String acno;
    private String primaryBranch;
    private String resBranch;
    private boolean saveDisable;
    private String oldAcno;
    Validator validator;
    private String orgnBrCode = getOrgnBrCode();
    private List<SelectItem> resBranchList;
    FtsPostingMgmtFacadeRemote ftsFacadeRemote;
    TDLienMarkingFacadeRemote tdFacadeRemote;
    String primaryBranchCode;

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }
    
    
    public boolean isSaveDisable() {
        return saveDisable;
    }
    
    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }
    
    public String getAcno() {
        return acno;
    }
    
    public void setAcno(String acno) {
        this.acno = acno;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getPrimaryBranch() {
        return primaryBranch;
    }
    
    public void setPrimaryBranch(String primaryBranch) {
        this.primaryBranch = primaryBranch;
    }
    
    public String getResBranch() {
        return resBranch;
    }
    
    public void setResBranch(String resBranch) {
        this.resBranch = resBranch;
    }
    
    public List<SelectItem> getResBranchList() {
        return resBranchList;
    }
    
    public void setResBranchList(List<SelectItem> resBranchList) {
        this.resBranchList = resBranchList;
    }
    
    public AccountTransfer() {
        try {
            ftsFacadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            tdFacadeRemote = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup("TDLienMarkingFacade");
            validator=new Validator();
            setSaveDisable(true);
            resBranchList = new ArrayList<SelectItem>();
            resBranchList.add(new SelectItem("--Select--"));
            List selectList = tdFacadeRemote.getAlphacodes();
            if (!selectList.isEmpty()) {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    resBranchList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());;
        }
    }
    
    public void getPrimaryBranchOnBlur() {
        msg="";
        primaryBranch="";
        if(oldAcno.equalsIgnoreCase("")||oldAcno.length()<12)
        {
            setMsg("Please insert a valid 12 digits account no.");
            return;
        }
        else if(!validator.validateStringAllNoSpace(oldAcno))
        {
            setMsg("Account no. is not valid");
            return;
        }
        try {
            acno=ftsFacadeRemote.getNewAccountNumber(oldAcno);
            if(acno.equalsIgnoreCase("A/c number does not exist"))
            {
                setMsg("A/c number does not exist !");
                acno="";
                return;
            }
            String acNature=ftsFacadeRemote.getAccountNature(acno);
            if(acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))
            {
                setMsg("This account can not be transfer!");
                return;
            }
            primaryBranchCode=ftsFacadeRemote.getCurrentBrnCode(acno);
            if(primaryBranchCode.equalsIgnoreCase("A/C No. does not exist"))
            {
                 setMsg("A/c number does not exist !");
                acno="";
                return;   
            }
            primaryBranch=tdFacadeRemote.getAlphacodeByBranchCode(primaryBranchCode);
            if(!primaryBranchCode.equalsIgnoreCase(getOrgnBrCode()))
            {
                setMsg("this account no. can not be transfer by your branch !");
                return;
            }
            String mesg=tdFacadeRemote.checkAcNoForTransfer(acno);
            if(mesg.equalsIgnoreCase("true"))
            {
                msg="Account no. is already pending for authorization !";
                return;
            }
            setSaveDisable(false);
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());;
        }
    }
    
    public void transferAccountNo() {
        try {
            if(resBranch.equalsIgnoreCase("--Select--"))
            {
                setMsg("Please select Responding Branch !!");
                return;
            }
            msg= tdFacadeRemote.transferAccount(acno, primaryBranchCode, resBranch, getUserName());
            refresh();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
     
    }
    public void refresh()
    {
        acno="";
        oldAcno="";
        primaryBranch="";
        resBranch="--Select--";
        saveDisable=true;
    }
    public void refreshForm() {
        msg="";
        refresh();
    }
    
    public String exitAction() {
        refreshForm();
        return "case1";
    }
}
