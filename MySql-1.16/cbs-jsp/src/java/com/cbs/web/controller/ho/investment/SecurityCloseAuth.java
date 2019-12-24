/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.pojo.SecurityAuthClosePojo;
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
public class SecurityCloseAuth extends BaseBean  {
    private String message;
    private String securityType;
    private List<SelectItem> securityTypeList;
    private String ctrlNo;
    private List<SelectItem> pendingCtrlNoList;
    private List<SecurityAuthClosePojo> secFirst;
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String pEnterBy;
    private int pTxnId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public List<SelectItem> getSecurityTypeList() {
        return securityTypeList;
    }

    public void setSecurityTypeList(List<SelectItem> securityTypeList) {
        this.securityTypeList = securityTypeList;
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

    public List<SecurityAuthClosePojo> getSecFirst() {
        return secFirst;
    }

    public void setSecFirst(List<SecurityAuthClosePojo> secFirst) {
        this.secFirst = secFirst;
    }    

    public String getpEnterBy() {
        return pEnterBy;
    }

    public void setpEnterBy(String pEnterBy) {
        this.pEnterBy = pEnterBy;
    }

    public int getpTxnId() {
        return pTxnId;
    }

    public void setpTxnId(int pTxnId) {
        this.pTxnId = pTxnId;
    }
    
    public SecurityCloseAuth() {
        try {
            securityTypeList = new ArrayList<SelectItem>();
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getSecurityTypeData();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getSecurityTypeData() {
        try {
            List<GlDescRange> entityList = remoteInterface.getGlRange("G");
            if (!entityList.isEmpty()) {
                for (GlDescRange entity : entityList) {
                    securityTypeList.add(new SelectItem(entity.getGlname()));                  
                }
            } else {
                this.setMessage("Range is not found for government security !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getPedingControl(){
        try {
            pendingCtrlNoList = new ArrayList<SelectItem>();
            List list = remoteInterface.getUnAuthSecCloseList(this.getSecurityType());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                pendingCtrlNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void getList(){
        this.setMessage("");
        pEnterBy = "";
        pTxnId = 0;
        secFirst = new ArrayList<SecurityAuthClosePojo>();
        if (ctrlNo.equals("--SELECT--")) {
            this.setMessage("Please Select The Control Number");
            return;
        }
        try {
            List<SecurityAuthClosePojo> dataList1 = remoteInterface.getUnAuthCtrlNoSecClose(Integer.parseInt(this.ctrlNo));
            if (!dataList1.isEmpty()) {
                for (SecurityAuthClosePojo entity1 : dataList1) {
                    SecurityAuthClosePojo secFst = new SecurityAuthClosePojo();
                    
                    secFst.setSecTp(entity1.getSecTp().toString());
                    secFst.setSecDetail(entity1.getSecDetail().toString());
                    secFst.setCtrlNo(entity1.getCtrlNo().toString());
                    secFst.setSellerName(entity1.getSellerName().toString());
                    secFst.setFaceValue(entity1.getFaceValue().toString());
                    secFst.setBookValue(entity1.getBookValue().toString());
                    secFst.setMatDt(entity1.getMatDt().toString());
                    secFst.setAccrInt(entity1.getAccrInt().toString());
                    secFst.setAmortVal(entity1.getAmortVal().toString());
                    secFst.setBalInt(entity1.getBalInt().toString());
                    secFst.setIssuePrice(entity1.getIssuePrice().toString());
                    secFst.setSelingStatus(entity1.getSelingStatus().toString());
                    secFst.setSelingAmt(entity1.getSelingAmt().toString());
                    secFst.setSelingDt(entity1.getSelingDt().toString());
                    secFst.setDrGlHead(entity1.getDrGlHead().toString());
                    secFst.setBranch(entity1.getBranch().toString());
                    Gltable entity = remoteInterface.getGltableByAcno(entity1.getDrGlHead().toString());
                    if (entity != null) {
                        secFst.setDrGlName(entity.getAcName());
                    }else{
                        secFst.setDrGlName("");
                    }
                    pEnterBy = entity1.getEnterBy().toString();
                    pTxnId = entity1.getTxnid();
                    secFirst.add(secFst);
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
                if(secFirst.get(0).getSecTp().equalsIgnoreCase("TREASURY BILLS")){                
//                    if(Double.parseDouble(secFirst.get(0).getFaceValue()) != (Double.parseDouble(secFirst.get(0).getBalInt()) + Double.parseDouble(secFirst.get(0).getAccrInt()) + Double.parseDouble(secFirst.get(0).getBookValue()))){
//                        this.setMessage("Face Value not equal to Book value and interest");
//                        return;    
//                    }
                
                    msg = remoteInterface.saveTrBillValue(sdf.parse(getTodayDate()), secFirst.get(0).getCtrlNo(), secFirst.get(0).getCtrlNo(), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getFaceValue()), Double.parseDouble(secFirst.get(0).getBookValue()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()));
                    if (msg.substring(0,4).equalsIgnoreCase("true")) {
                        getPedingControl();
                        secFirst = new ArrayList<SecurityAuthClosePojo>();
                        this.setMessage("Data has been saved successfully And Posted Batch " + msg.substring(4));
                        return;
                    }
                }else{
                    if(secFirst.get(0).getSelingStatus().equalsIgnoreCase("Full")){
                        if (Double.parseDouble(secFirst.get(0).getFaceValue()) == Double.parseDouble(secFirst.get(0).getBookValue())) {
                            msg = remoteInterface.saveFaceEqualSellAmtPart(sdf.parse(getTodayDate()), Integer.parseInt(secFirst.get(0).getCtrlNo()), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getFaceValue()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()));
                        }
                        if (Double.parseDouble(secFirst.get(0).getFaceValue()) > Double.parseDouble(secFirst.get(0).getBookValue())) {
                            msg = remoteInterface.saveFaceGreaterSellAmtPart(sdf.parse(getTodayDate()), secFirst.get(0).getCtrlNo(), secFirst.get(0).getCtrlNo(), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getFaceValue()), Double.parseDouble(secFirst.get(0).getBookValue()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()));
                        }
                        if (Double.parseDouble(secFirst.get(0).getFaceValue()) < Double.parseDouble(secFirst.get(0).getBookValue())) {
                            msg = remoteInterface.saveFaceLessSellAmtPart(sdf.parse(getTodayDate()), secFirst.get(0).getCtrlNo(), secFirst.get(0).getCtrlNo(), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getFaceValue()), Double.parseDouble(secFirst.get(0).getBookValue()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()), Double.parseDouble(secFirst.get(0).getAmortVal()));
                        }
                    }else{
                        if (Double.parseDouble(secFirst.get(0).getSelingAmt()) == Double.parseDouble(secFirst.get(0).getBookValue())) {
                            msg = remoteInterface.saveSellAmtEqualBookAmt(sdf.parse(getTodayDate()), Integer.parseInt(secFirst.get(0).getCtrlNo()), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getSelingAmt()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()));
                        }
                        if (Double.parseDouble(secFirst.get(0).getSelingAmt()) > Double.parseDouble(secFirst.get(0).getBookValue())) {
                            msg = remoteInterface.saveSellAmtGreaterBookAmt(sdf.parse(getTodayDate()), secFirst.get(0).getCtrlNo(), secFirst.get(0).getCtrlNo(), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getSelingAmt()), Double.parseDouble(secFirst.get(0).getBookValue()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()));
                        }
                        if (Double.parseDouble(secFirst.get(0).getSelingAmt()) < Double.parseDouble(secFirst.get(0).getBookValue())) {
                            msg = remoteInterface.saveSellAmtLessBookAmt(sdf.parse(getTodayDate()), secFirst.get(0).getCtrlNo(), secFirst.get(0).getCtrlNo(), secFirst.get(0).getSecTp(), Double.parseDouble(secFirst.get(0).getSelingAmt()), Double.parseDouble(secFirst.get(0).getBookValue()), getOrgnBrCode(), getOrgnBrCode(), getUserName(), Double.parseDouble(secFirst.get(0).getAccrInt()), secFirst.get(0).getDrGlHead(), secFirst.get(0).getBranch(),Double.parseDouble(secFirst.get(0).getBalInt()), Double.parseDouble(secFirst.get(0).getAmortVal()));
                        }                        
                    }
                    
                    if (msg.substring(0,4).equalsIgnoreCase("true")) {
                        getPedingControl();
                        secFirst = new ArrayList<SecurityAuthClosePojo>();
                        this.setMessage("Data has been saved successfully And Posted Batch " + msg.substring(4));
                        return;
                    }
                }
            }
         } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void ctrlDelete(){
        try {
            if (securityType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Function");
                return;
            }

            if (ctrlNo == null || (ctrlNo.equalsIgnoreCase("")) || ctrlNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Select The Control Number");
                return;
            }
            
            if (ctrlNo.equals("--SELECT--")) {
                this.setMessage("Please Select The Control Number");
                return;
            }
            
            if(secFirst.isEmpty()){
                this.setMessage("No Data To Delete");
                return;
            }

            String msg = remoteInterface.deleteSecCloseAuth(pTxnId, secFirst.get(0).getCtrlNo().toString());
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                clearText();
                getPedingControl();
                String msg1 = "Data has been Deleted successfully";
                this.setMessage(msg1);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void clearText(){
        this.setMessage("");
        pendingCtrlNoList = new ArrayList<SelectItem>();
        secFirst = new ArrayList<SecurityAuthClosePojo>();
        pEnterBy = "";
        pTxnId = 0;
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
