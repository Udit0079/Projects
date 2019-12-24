/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentSecurityAuthDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.investment.SecurityAuthPojo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class SecurityAuth extends BaseBean {
    private String message;
    private String message1;
    private String ctrlNo;
    private String flag3 = "none";
    private String alertMsg;
    private List<SelectItem> pendingCtrlNoList;
    private List<SecurityAuthPojo> secFirst;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(String ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public List<SelectItem> getPendingCtrlNoList() {
        return pendingCtrlNoList;
    }

    public void setPendingCtrlNoList(List<SelectItem> pendingCtrlNoList) {
        this.pendingCtrlNoList = pendingCtrlNoList;
    }

    public List<SecurityAuthPojo> getSecFirst() {
        return secFirst;
    }

    public void setSecFirst(List<SecurityAuthPojo> secFirst) {
        this.secFirst = secFirst;
    }        

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public String getAlertMsg() {
        return alertMsg;
    }

    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }
    
    public SecurityAuth() {
        try {
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getUnAuthCtrlNo();
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

    public void getUnAuthCtrlNo() {
        try {
            this.setMessage1("");
            pendingCtrlNoList = new ArrayList<SelectItem>();
            List<InvestmentSecurityAuthDetail> dataList = remoteInterface.getUnAuthSecCtrlNo(getOrgnBrCode());
            if (!dataList.isEmpty()) {
                for (InvestmentSecurityAuthDetail entity : dataList) {
                    pendingCtrlNoList.add(new SelectItem(entity.getMaxSec()));
                }
            }
        } catch (Exception e) {
            setMessage1(e.getMessage());
        }
    }

    public void getList() {
        this.setMessage("");
        this.setFlag3("none");
        this.setAlertMsg(""); 
        secFirst = new ArrayList<SecurityAuthPojo>();
        if (ctrlNo.equals("--SELECT--")) {
            this.setMessage("Please Select The Control Number");
            return;
        }
        try {
            List<InvestmentSecurityAuthDetail> dataList1 = remoteInterface.getUnAuthCtrlNoDtl(Integer.parseInt(this.ctrlNo));
            if (!dataList1.isEmpty()) {
                for (InvestmentSecurityAuthDetail entity1 : dataList1) {
                    SecurityAuthPojo secFst = new SecurityAuthPojo();
                    secFst.setCtrlNo(entity1.getMaxSec().toString());
                    secFst.setSecTp(entity1.getSecTpDd());
                    secFst.setMatDt(sdf.format(entity1.getMatDate()));
                    secFst.setSellerName(entity1.getSellerNmVal());
                    secFst.setCodeNo(entity1.getCodeNoVal());
                    secFst.setIssDt(entity1.getPrnIssueDate());
                    secFst.setPurDT(entity1.getPurchaseDate());
                    secFst.setTrnDt(sdf.format(entity1.getTransactionDate()));
                    secFst.setSettleDt(sdf.format(entity1.getSettlementDate()));
                    secFst.setfIntPayDt(entity1.getFInttPayDate());
                    secFst.setsIntPayDt(entity1.getSinttPaydate());
                    secFst.setRoi(Double.toString(entity1.getRoiVal()));
                    
                    secFst.setNoOfShare(entity1.getNoOfShrVal().toString());
                    secFst.setConAcNo(entity1.getConAcNoVal());
                    secFst.setIssueUnit(entity1.getIssuePrVal().toString());
                    secFst.setIssuePrice(entity1.getPriceVal().toString());
                    secFst.setFaceValue(entity1.getFaceValue().toString());
                    secFst.setBookValue(entity1.getBookValue().toString());
                    secFst.setYtm(entity1.getYtm().toString());
                    secFst.setAccrInt(entity1.getAccrIntVal().toString());
                    secFst.setConSglAc(entity1.getConSgAccountDd());
                    secFst.setBrokerAc(entity1.getBrokerAcVal());
                    secFst.setBrokerageAmt(entity1.getBrokerageAmtVal().toString());
                    secFst.setCrGlHead(entity1.getCrGlVal());
                    secFst.setCrBranch(entity1.getCrBranchDd());
                    secFst.setSecDetail(entity1.getSecDtl());
                    secFst.setSetFlg(entity1.getSetFlag());
                    secFst.setEnterBy(entity1.getEnterBy());
                    secFst.setDetail(entity1.getDetail());
                    if(entity1.getMarking()== null){
                        secFst.setMarking("");
                    } else if(entity1.getMarking().equalsIgnoreCase("HT")){
                        secFst.setMarking("Held for Trading");
                    }else if(entity1.getMarking().equalsIgnoreCase("HM")){
                        secFst.setMarking("Held For Maturity");
                    }else if(entity1.getMarking().equalsIgnoreCase("AF")){
                        secFst.setMarking("Available for Sale");
                    }else{
                        secFst.setMarking("");
                    }
                    secFirst.add(secFst);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public int recNoGenerateSec() {
        int code = 0;
        try {
            code = remoteInterface.getMaxCtrlNoByInvestmentMasterSec();
            if (code == 0) {
                code = 1;
            } else {
                code = code + 1;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return code;
    }
    
    public void ctrlAuth() {
        String msg = "";
        if (ctrlNo.equals("--SELECT--")) {
            this.setMessage("Please Select The Control Number");
            return;
        }
        try {
            
            int maxSec = recNoGenerateSec();
            String markVar = "";
            if(secFirst.get(0).getMarking().equalsIgnoreCase("Held For Maturity")){
                markVar = "HM";
            } else if(secFirst.get(0).getMarking().equalsIgnoreCase("Held for Trading")){
                markVar = "HT";
            } else if(secFirst.get(0).getMarking().equalsIgnoreCase("Available for Sale")){
                markVar = "AF";
            }
            
            msg = remoteInterface.saveGoiSecurity(maxSec, secFirst.get(0).getCrGlHead(), secFirst.get(0).getSecTp(), secFirst.get(0).getFaceValue(), getOrgnBrCode(), secFirst.get(0).getCrBranch(),
                    getUserName(), secFirst.get(0).getBookValue(), secFirst.get(0).getAccrInt(), secFirst.get(0).getSecDetail(), secFirst.get(0).getCodeNo(), Double.valueOf(secFirst.get(0).getRoi()),
                    sdf.parse(secFirst.get(0).getTrnDt()), secFirst.get(0).getSetFlg(), sdf.parse(secFirst.get(0).getSettleDt()), secFirst.get(0).getSellerName() , secFirst.get(0).getConAcNo(), secFirst.get(0).getBrokerAc(),
                    Double.valueOf(secFirst.get(0).getBrokerageAmt()), Integer.parseInt(secFirst.get(0).getNoOfShare()), sdf.parse(secFirst.get(0).getMatDt()), secFirst.get(0).getConSglAc(),
                    Double.valueOf(secFirst.get(0).getIssueUnit()), secFirst.get(0).getPurDT(), secFirst.get(0).getIssDt(), secFirst.get(0).getfIntPayDt(), secFirst.get(0).getsIntPayDt(),
                    secFirst.get(0).getIssuePrice(),secFirst.get(0).getDetail(),Double.valueOf(secFirst.get(0).getYtm()),secFirst.get(0).getEnterBy(),Integer.parseInt(secFirst.get(0).getCtrlNo()), markVar);

            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                if(Double.parseDouble(secFirst.get(0).getBookValue())> Double.parseDouble(secFirst.get(0).getFaceValue())){
                    if(!secFirst.get(0).getSecTp().equalsIgnoreCase("TREASURY BILLS")){
                        this.setFlag3("");
                        this.setAlertMsg("Create Amort Schedule For this Control");
                    }                                        
                }
                if(msg.contains("GOIPURCHASE")){
                    this.setMessage("Control No. is  " + maxSec + "Transaction Made Successfully");
                }else{                    
                    String tr = msg.substring(4);
                    this.setMessage("Control No. is  " + maxSec + " Batch No. - " + tr + "Transaction Made Successfully");
                }
                secFirst = new ArrayList<SecurityAuthPojo>();
                getUnAuthCtrlNo();
                setCtrlNo("--SELECT--");                
            } else {
                this.setMessage(msg);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void ctrlDelete() {
        this.setMessage("");
        if (ctrlNo.equals("--SELECT--")) {
            this.setMessage("Please Select The Control Number");
            return;
        }
        try {
            if (this.ctrlNo == null || this.ctrlNo.equalsIgnoreCase("") || this.ctrlNo.equals("--SELECT--") || this.ctrlNo.equalsIgnoreCase("0")) {
                this.setMessage("Please Select The Control Number");
                return;
            }
            
            String msg = remoteInterface.deleteInvestmentSecurityAuth(Integer.parseInt(this.ctrlNo));
                if (msg.equalsIgnoreCase("true")) {
                    this.setMessage("Entry has been deleted successfully !");
                    getUnAuthCtrlNo();                    
                    return;
                } else {
                    this.setMessage(msg);
                }
            
        } catch (ApplicationException e) {
            if (e.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("Please Select The Control Number");
            } else {
                this.setMessage(e.getMessage());
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void clearText() {
        setCtrlNo("--SELECT--");
        this.setMessage("");
        this.setMessage1("");
        this.setFlag3("none");
        this.setAlertMsg("");
        secFirst = new ArrayList<SecurityAuthPojo>();        
    }

    public String exitFrm() {
        setCtrlNo("--SELECT--");
        this.setMessage("");
        this.setMessage1("");
        this.setFlag3("none");
        this.setAlertMsg("");
        secFirst = new ArrayList<SecurityAuthPojo>();        
        return "case1";
    }
}