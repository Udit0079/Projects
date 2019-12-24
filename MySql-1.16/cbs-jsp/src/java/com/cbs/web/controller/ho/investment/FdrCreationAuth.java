/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.pojo.InvestmentFDRInterestPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
public class FdrCreationAuth extends BaseBean {
    
    private String message;
    private String seqNo;
    private List<SelectItem> pendingSeqNoList;
    private List<InvestmentFDRInterestPojo> secFirst;
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String pEnterBy;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public List<SelectItem> getPendingSeqNoList() {
        return pendingSeqNoList;
    }

    public void setPendingSeqNoList(List<SelectItem> pendingSeqNoList) {
        this.pendingSeqNoList = pendingSeqNoList;
    }

    public List<InvestmentFDRInterestPojo> getSecFirst() {
        return secFirst;
    }

    public void setSecFirst(List<InvestmentFDRInterestPojo> secFirst) {
        this.secFirst = secFirst;
    }

    public String getpEnterBy() {
        return pEnterBy;
    }

    public void setpEnterBy(String pEnterBy) {
        this.pEnterBy = pEnterBy;
    }
    
    public FdrCreationAuth() {
        try {
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getPedingSeqNo();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getPedingSeqNo(){
        try {
            pendingSeqNoList = new ArrayList<SelectItem>();
            List list = remoteInterface.getUnAuthFDRSeqNo();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                pendingSeqNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void getList(){
        this.setMessage("");
        pEnterBy = "";
        secFirst = new ArrayList<InvestmentFDRInterestPojo>();
        if (seqNo.equals("--SELECT--")) {
            this.setMessage("Please Select The Control Number");
            return;
        }
        try {
            List<InvestmentFDRInterestPojo> dataList1 = remoteInterface.getUnAuthSeqNoFDRList(Integer.parseInt(this.seqNo));
            if (!dataList1.isEmpty()) {
                for (InvestmentFDRInterestPojo entity1 : dataList1) {
                    InvestmentFDRInterestPojo invFdrPojo = new InvestmentFDRInterestPojo();
                    
                    invFdrPojo.setCtrlNo(entity1.getCtrlNo());
                    invFdrPojo.setSecType(entity1.getSecType());
                    invFdrPojo.setSecDesc(entity1.getSecDesc());
                    invFdrPojo.setFdrNo(entity1.getFdrNo());
                    invFdrPojo.setSellerName(entity1.getSellerName());
                    invFdrPojo.setBranch1(entity1.getBranch1());
                    invFdrPojo.setPurchaseDt(entity1.getPurchaseDt());
                    invFdrPojo.setDays(entity1.getDays());
                    invFdrPojo.setMonths(entity1.getMonths());
                    invFdrPojo.setYears(entity1.getYears());
                    invFdrPojo.setMatDt(entity1.getMatDt());
                    invFdrPojo.setRoi(entity1.getRoi());
                    invFdrPojo.setIntOpt(entity1.getIntOpt());
                    invFdrPojo.setFaceValue(entity1.getFaceValue());
                    invFdrPojo.setAmtIntRec(entity1.getAmtIntRec());
                    invFdrPojo.setTotAmtIntRec(entity1.getTotAmtIntRec());
                    invFdrPojo.setBranch2(entity1.getBranch2());
                    invFdrPojo.setCrHead(entity1.getCrHead());
                    Gltable entityCr = remoteInterface.getGltableByAcno(entity1.getCrHead().toString());
                    if (entityCr != null) {
                        invFdrPojo.setCrHeadName(entityCr.getAcName());
                    }else{
                        invFdrPojo.setCrHeadName("");
                    }
                    invFdrPojo.setDrHead(entity1.getDrHead());
                    Gltable entityDr = remoteInterface.getGltableByAcno(entity1.getDrHead().toString());
                    if (entityDr != null) {
                        invFdrPojo.setDrHeadName(entityDr.getAcName());
                    }else{
                        invFdrPojo.setDrHeadName("");
                    }
                    
                    pEnterBy = entity1.getEnterBy().toString();
                    secFirst.add(invFdrPojo);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void ctrlAuth(){
         try {
            String msg = "";
            if (pEnterBy.equalsIgnoreCase(getUserName())) {
                this.setMessage("You Can't Authorize Your Own Entry.");
                return;
            } else {
                Integer maxCtrlNo = remoteInterface.getMaxCtrlNoFromInvestmentFdrDetails();
                
                msg = remoteInterface.fdrSaveProcess(maxCtrlNo, sdf.parse(secFirst.get(0).getPurchaseDt()), sdf.parse(secFirst.get(0).getMatDt()),
                        secFirst.get(0).getIntOpt(), secFirst.get(0).getDays(), secFirst.get(0).getMonths(), secFirst.get(0).getYears(), secFirst.get(0).getSecDesc(), secFirst.get(0).getRoi().toString(), secFirst.get(0).getSellerName(),
                        Double.parseDouble(secFirst.get(0).getFaceValue().toString()), Double.parseDouble(secFirst.get(0).getTotAmtIntRec().toString()), secFirst.get(0).getFdrNo(), secFirst.get(0).getBranch1(),
                        getUserName(), secFirst.get(0).getCrHead(), getOrgnBrCode(),secFirst.get(0).getSecType(), secFirst.get(0).getCtrlNo());
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    clearText();
                    this.setMessage("Transaction has been made successfully, Controll No: " + maxCtrlNo.toString() + " And " + "Batch No: " + msg.substring(4));
                    getPedingSeqNo();
                    return;
                }
            }
         } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void ctrlDelete(){
        try {
            if (seqNo == null || (seqNo.equalsIgnoreCase("")) || seqNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Select The Sequence Number");
                return;
            }
            
            if (seqNo.equals("--SELECT--")) {
                this.setMessage("Please Select The Sequence Number");
                return;
            }
            
            if(secFirst.isEmpty()){
                this.setMessage("No Data To Delete");
                return;
            }

            String msg = remoteInterface.deleteFdrCreateAuth(secFirst.get(0).getCtrlNo());
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                clearText();
                getPedingSeqNo();
                String msg1 = "Data has been Deleted successfully";
                this.setMessage(msg1);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void clearText(){
        this.setMessage("");
        pendingSeqNoList = new ArrayList<SelectItem>();
        secFirst = new ArrayList<InvestmentFDRInterestPojo>();
        pEnterBy = "";        
    }
    
    public String exitFrm(){
        try {
            clearText();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
    
}
