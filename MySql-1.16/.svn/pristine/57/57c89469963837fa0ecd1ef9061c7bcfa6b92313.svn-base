/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.dto.memberRefMappingPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class MemberRoleMapping extends BaseBean{
    AdvancesInformationTrackingRemote aitr;
    private String message;
    private String name;
    private String designation;
    private String designation1;
    private String function;
    private List<memberRefMappingPojo> gridDetail;
    private String refNo;
    List<SelectItem> designationOption;
    List<SelectItem> functionOption;
    List<SelectItem> designationOption1;
    private memberRefMappingPojo currentItem;
    private boolean selectRender;
    private boolean disabledesignation=false;
    private boolean disabledrefNo=false;
    private boolean deleteflag=true;
    private String displayDesignation2="none";
    Context ctx;
    private HttpServletRequest req;
    ShareTransferFacadeRemote remoteObject;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
     
    public MemberRoleMapping(){
       req = getRequest();
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            setTodayDate(sdf.format(date));
             this.setMessage("");
             
             functionOption = new ArrayList();
             functionOption.add(new SelectItem("0","--Select--"));
             functionOption.add(new SelectItem("A","Add"));
             functionOption.add(new SelectItem("M","Modify"));
             functionOption.add(new SelectItem("D","Delete"));
             
             List listForDesignation = new ArrayList();
            listForDesignation = aitr.refRecCode("416");
            designationOption = new ArrayList<SelectItem>();
            designationOption.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < listForDesignation.size(); i++) {
                Vector element = (Vector) listForDesignation.get(i);
                designationOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
      }
            
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
            
    }
     public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
     
     
     public void onblurfunction(){
         if(function.equalsIgnoreCase("A")){
             this.selectRender = false;
             this.deleteflag = true;
         }else if(function.equalsIgnoreCase("M")){
             this.selectRender = true;
             this.deleteflag = true;
             
         }else if(function.equalsIgnoreCase("D")){
             this.selectRender = true;
             this.deleteflag = false;
         }
     }
     
     public void selectOperation(){
         try{
             if(function.equalsIgnoreCase("M")|| function.equalsIgnoreCase("D")){
                 if(!function.equalsIgnoreCase("D")){
                 this.disabledesignation=true;
                 this.displayDesignation2="";
             }else{
                     this.disabledesignation = true;
                     this.displayDesignation2 = "none";
                 }
               List  listForDesignation = aitr.refRecCode("416");
                 designationOption1 = new ArrayList<SelectItem>();
                 designationOption1.add(new SelectItem("0", "--Select--"));
                 for (int i = 0; i < listForDesignation.size(); i++) {
                Vector element = (Vector) listForDesignation.get(i);
                designationOption1.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
      }
                 this.setDesignation1(currentItem.getDegination());
                 this.setRefNo(currentItem.getRefid());
                 this.setName(currentItem.getName());
                 
                 this.disabledrefNo = true;
             }
             
         }catch(Exception ex){
             this.setMessage(ex.getMessage());
         }
     }
     
     public void onblurrefDesignation(){
          gridDetail = new ArrayList<>();
         try{
        List refnoList = remoteObject.getMaxMemberRefNo(this.designation);
        Vector vec = (Vector) refnoList.get(0);
        int refid =Double.valueOf(vec.get(0).toString().trim()).intValue();
        this.setRefNo(String.valueOf(refid)); 
        
          gridDetail = remoteObject.getAllDetailOfDesignationBases(this.designation,getTodayDate());
         }catch(Exception ex){
             this.setMessage(ex.getMessage());
         }
     }
     
     public void onblurrefDetail(){
         try{
           this.refNo = Double.valueOf(this.refNo).toString();
             String msg = remoteObject.isRefAlreadyUsed(this.designation,String.valueOf(Double.valueOf(this.refNo).intValue()));
             if(!msg.equals("true")){
                 this.setMessage(msg);
                 return;
             }
          }catch(Exception ex){
             this.setMessage(ex.getMessage());
         }
     }
     
     public void onblurFolioDetails() throws ApplicationException {
         gridDetail = new ArrayList<>();
         try{
             setMessage("");
          if (this.designation == null || designation.equalsIgnoreCase("")) {
            this.setMessage("Designation IS BLANK !");
            return;
        }
          if(this.refNo==null || this.refNo.equals("")){
              this.setMessage("ref no. is can not be blank");
              return;
          }
          if(this.name==null || this.name.equalsIgnoreCase("")){
             this.setMessage("Please fill the name.");
             return;
         }
       }catch(Exception e){
     setMessage(e.getMessage());
}}
      

  
    public void clear() {
        setName("");
        setRefNo("");
        setDesignation("0");
        gridDetail = new ArrayList<>();
        this.displayDesignation2="none";
        this.disabledesignation=false;
        this.disabledrefNo=false;
        }
    
    
    public void deleteBtnAction(){
        try{
           String result = remoteObject.deleteMappingDesignation(this.designation,this.refNo);
           if(!result.equalsIgnoreCase("true")){
               this.setMessage("Problem In Deletion of this Member");
               return;
           }
           refresh();
           this.setMessage("This member has been delete successfully.");
         }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
      
    
     public void saveBtnAction() {
        try {
            if(this.function.equalsIgnoreCase("A")){
            if (this.designation == null || designation.equalsIgnoreCase("")) {
            this.setMessage("Designation IS BLANK !");
            return;
        }
          String result = remoteObject.saveDesignation(this.designation,this.name,this.refNo,getUserName(),getTodayDate());
            if (result.equalsIgnoreCase("true") ) {
                this.setMessage("Data has been saved successfully");
            } else {
                this.setMessage("Data already exists...");
            }
            clear();
            }else if(this.function.equalsIgnoreCase("M")){
            if (this.designation1 == null || designation1.equalsIgnoreCase("")) {
            this.setMessage("Designation IS BLANK !");
            return;
        }
          String result = remoteObject.saveDesignation(this.designation1,this.name,this.refNo,getUserName(),getTodayDate());
            if (result.equalsIgnoreCase("true") ) {
                this.setMessage("Data has been modified successfully");
            } else {
                this.setMessage("Data already exists...");
            }
            clear();
            
            
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    public void refresh(){
        this.setDesignation("0");
        this.setMessage("");
        setName("");
        this.setRefNo("");
        gridDetail = new ArrayList<>();
        this.disabledesignation=false;
        this.disabledrefNo=false;
        this.displayDesignation2="none";
        this.setDesignation1("0");
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

   
    public List<SelectItem> getDesignationOption() {
        return designationOption;
    }

    public void setDesignationOption(List<SelectItem> designationOption) {
        this.designationOption = designationOption;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<memberRefMappingPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<memberRefMappingPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionOption() {
        return functionOption;
    }

    public void setFunctionOption(List<SelectItem> functionOption) {
        this.functionOption = functionOption;
    }

    public memberRefMappingPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(memberRefMappingPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isSelectRender() {
        return selectRender;
    }

    public void setSelectRender(boolean selectRender) {
        this.selectRender = selectRender;
    }

    public String getDesignation1() {
        return designation1;
    }

    public void setDesignation1(String designation1) {
        this.designation1 = designation1;
    }

    public List<SelectItem> getDesignationOption1() {
        return designationOption1;
    }

    public void setDesignationOption1(List<SelectItem> designationOption1) {
        this.designationOption1 = designationOption1;
    }

    public boolean isDisabledesignation() {
        return disabledesignation;
    }

    public void setDisabledesignation(boolean disabledesignation) {
        this.disabledesignation = disabledesignation;
    }

    public String getDisplayDesignation2() {
        return displayDesignation2;
    }

    public void setDisplayDesignation2(String displayDesignation2) {
        this.displayDesignation2 = displayDesignation2;
    }

    public boolean isDisabledrefNo() {
        return disabledrefNo;
    }

    public void setDisabledrefNo(boolean disabledrefNo) {
        this.disabledrefNo = disabledrefNo;
    }

    public boolean isDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(boolean deleteflag) {
        this.deleteflag = deleteflag;
    }
    
    
    
}
