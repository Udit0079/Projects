/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.other.venderMasterPojo;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.other.OtherMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class VenderInvoiceBean extends BaseBean {
    private String message;
    private String function;
    private String gstNo;
    private List<SelectItem> functionList;
    private String venderName;
    private String state;
    private String invoiceNo;
    private String invoiceAmt;
    private String cgst;
    private String sgst;
    private String igst;
    private String stateCode ;
    private String disPlayGrid="none";
    private List<venderMasterPojo> gridDetail;
    private venderMasterPojo currentItem;
    private OtherMgmtFacadeRemote beanRemote;
    CustomerMasterDelegate masterDelegate;
    private CommonReportMethodsRemote commonRemote;
    private InterBranchTxnFacadeRemote interFts;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public VenderInvoiceBean() {
        try{
        masterDelegate = new CustomerMasterDelegate();
        beanRemote = (OtherMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherMgmtFacade");  
        commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        interFts = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
        
        functionList = new ArrayList<>();
        functionList.add(new SelectItem("0","--Select--"));
        functionList.add(new SelectItem("A","Add"));
        functionList.add(new SelectItem("V","Authorize"));
            
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }

   
   public void funtionAction(){
         
       try{
        if(function.equalsIgnoreCase("A")){
            this.disPlayGrid = "none";
               
           }else if(function.equalsIgnoreCase("V")){
            this.disPlayGrid = "";
            List venderList = beanRemote.getVenderInvoiceUnauthorizeDetail();
            if(venderList.isEmpty()){
              this.setMessage("There is no detail to authorize.");
            }else{
           gridDetail = new ArrayList<venderMasterPojo>();
             for(int i =0 ; i<venderList.size() ; i++){
                
                 Vector vc = (Vector) venderList.get(i);
                 venderMasterPojo pojo = new venderMasterPojo();
                 pojo.setGstno(vc.get(0).toString());
                 pojo.setInvoiceNo(vc.get(1).toString());
                 pojo.setInvoiceAmt(new BigDecimal(vc.get(2).toString()));
                 pojo.setCgst(new BigDecimal(vc.get(3).toString()));
                 pojo.setSgst(new BigDecimal(vc.get(4).toString()));
                 pojo.setIgst(new BigDecimal(vc.get(5).toString()));
                 pojo.setName(vc.get(6).toString());
                 pojo.setState(vc.get(7).toString());
          String stateDesc= commonRemote.getRefRecDesc("002",pojo.getState());
                 pojo.setStateDes(stateDesc);
                 pojo.setEnterBy(vc.get(8).toString());
                 
                 gridDetail.add(pojo);
             }   
            }
        }
           
       }catch (Exception ex){
           this.setMessage(ex.getMessage());
       }
   }
    
   
   public void gstAction(){
       this.setMessage("");
       try{
           if(function.equalsIgnoreCase("A")){
            List resultlist = beanRemote.getVenderDetail(this.gstNo);
            if(resultlist.isEmpty()){
                this.setMessage("There is no detail for this gst no.");
                return;
            }
            Vector vec = (Vector) resultlist.get(0);
            this.setVenderName( vec.get(1).toString());
            this.setStateCode(vec.get(2).toString());
            String stateDesc= commonRemote.getRefRecDesc("002",this.stateCode);
            this.setState(stateDesc);
        }
      }catch(Exception ex){
           this.setMessage(ex.getMessage());
       }
   }
    
   public void invoiceAmtAction(){
       if (this.invoiceAmt.toString().equals("") || this.invoiceAmt == null) {
            this.setMessage("Please Fill Invoice Amount");
            return;
        }
        if (!this.invoiceAmt.toString().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            this.setMessage("Only Numeric value is allowed in Invoice Amount");
            return ;
        }
         if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(Double.parseDouble(invoiceAmt))) == 0) {
                this.setMessage("invoice amount field should not be zero.");
                return;
            }
        
       String dt = ymd.format(new Date());
       Double taxAmt = 0.0;
       Map<String, Double> map = new HashMap<String, Double>();
       Map<String, Double> mapIgst = new HashMap<String, Double>();
       try{
            double sPerc = 0, sPercIgst = 0;
                int rUpTo = 0, rUpToIgst = 0;
           if(function.equalsIgnoreCase("A")){
            map = interFts.getTaxComponentSlab(dt);
             Set<Map.Entry<String, Double>> set = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        sPerc = Double.parseDouble(entry.getValue().toString());
                        rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    }
                    
              mapIgst = interFts.getIgstTaxComponentSlab(dt);
                    Set<Map.Entry<String, Double>> setIgst = mapIgst.entrySet();
                    Iterator<Map.Entry<String, Double>> itIgst = setIgst.iterator();
                    while (itIgst.hasNext()) {
                        Map.Entry entry = itIgst.next();
                        sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                        rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    }
           String branchStateCode = commonRemote.getStateCodeFromBranchMaster(getOrgnBrCode());
       if(this.stateCode.equalsIgnoreCase(branchStateCode)){
             taxAmt  = CbsUtil.round(((Double.valueOf(this.invoiceAmt) * sPerc) / 100), rUpTo); 
             this.cgst = taxAmt.toString();
             this.igst="0";
             this.sgst = taxAmt.toString();
            }else{
             taxAmt = CbsUtil.round(((Double.valueOf(this.invoiceAmt) * sPercIgst) / 100), rUpToIgst);
             this.igst = taxAmt.toString();
             this.cgst = "0";
             this.sgst = "0";
            }        
                    
           }
        }catch(Exception ex){
           this.setMessage(ex.getMessage());
       }
   }
   
   
   public void authAction(){
       this.setMessage("");
   try{
       if(function.equalsIgnoreCase("V")){
       if(currentItem.getEnterBy().equalsIgnoreCase(getUserName())){
           this.setMessage("You can not authorize your own entry.");
           return;
       }    
       String result = beanRemote.authorizeDetail(currentItem,getUserName());
       if(!result.equalsIgnoreCase("true")){
           this.setMessage("Detail is not authorize due to some reason.");
       }
       funtionAction();
       this.setMessage("Detail has been authorized successfully.");
      }
   }catch(Exception ex){
       this.setMessage(ex.getMessage());
   }
 }
   
   public void deleteAction(){
       this.setMessage("");
       try{
       String result = beanRemote.deleteDetail(currentItem);
       if(!result.equalsIgnoreCase("true")){
           this.setMessage("This entry can not be delete.");
       }
       funtionAction();
       this.setMessage("This entry has been deleted successfully.");
       }catch(Exception ex){
           this.setAcNoLength(ex.getMessage());
       }
   }
   
   
   public void postDetail(){
   try{
     String result = beanRemote.saveVenderInvoiceDetail(this.gstNo ,this.invoiceNo,this.invoiceAmt,this.cgst,this.sgst,this.igst,
          getUserName(),ymd.format(sdf.parse(getTodayDate())));
     if(result.equalsIgnoreCase("true")){
         resetForm();
         this.setMessage("Invoice Detail saved successfully .");
     }else{
       this.setMessage(result);
     }
    }catch(Exception ex){
       this.setMessage(ex.getMessage());
   }
   }
   
   
   
   public void resetForm(){
       this.setCgst("");
       this.setSgst("");
       this.setIgst("");
       this.setInvoiceAmt("");
       this.setInvoiceNo("");
       this.setFunction("0");
       this.setMessage("");
       this.setState("");
       this.setVenderName("");
       this.gridDetail = null;
       gridDetail= new ArrayList<>();
   }
   
   
   public String exitForm(){
       return "case1";
   }
    
    
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(String invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getDisPlayGrid() {
        return disPlayGrid;
    }

    public void setDisPlayGrid(String disPlayGrid) {
        this.disPlayGrid = disPlayGrid;
    }

    public List<venderMasterPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<venderMasterPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public venderMasterPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(venderMasterPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    
    
    
}
