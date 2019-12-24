/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.other.venderMasterPojo;
import com.cbs.facade.other.OtherMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class VenderDetailBean extends BaseBean {
    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String name;
    private String gstIn;
    private String gstNo;
    private String state;
    private String disGst="none" ;
    private String displayPanel = "";
    private boolean selectRender;
    private boolean deleteRender;
    private boolean gstFlag= false;
    private String btnValue="Save";
    private List<SelectItem> stateOption;
    private List<venderMasterPojo> gridDetail;
    private venderMasterPojo currentItem;
    private OtherMgmtFacadeRemote beanRemote;
    CustomerMasterDelegate masterDelegate;
    private CommonReportMethodsRemote commonRemote;

    public VenderDetailBean() {
        try{
        masterDelegate = new CustomerMasterDelegate();
        beanRemote = (OtherMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherMgmtFacade");  
        commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        
        functionList = new ArrayList<>();
        functionList.add(new SelectItem("0","--Select--"));
        functionList.add(new SelectItem("A","Add"));
        functionList.add(new SelectItem("M","Modify"));
        
       List<CbsRefRecTypeTO> stateOptionList = masterDelegate.getFunctionValues("002");
        stateOption = new ArrayList<SelectItem>();
        stateOption.add(new SelectItem("0", "--Select--"));
        for (CbsRefRecTypeTO recTypeTO : stateOptionList) {
                stateOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
           }
      }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }

    public void funtionAction(){
        try{
            if(function.equalsIgnoreCase("A")){
                this.disGst = "none";
                this.displayPanel = "";
                this.selectRender = false;
                this.deleteRender = true;
                this.setBtnValue("Save");
            }else if(function.equalsIgnoreCase("M")){
                this.disGst = "";
                this.displayPanel = "none";
                this.selectRender = true;
                this.deleteRender = false;
                this.setBtnValue("Update");
            }
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void stateSelectAction(){
        this.setMessage("");
        try{
            if(function.equalsIgnoreCase("A")){
          if(this.name.equalsIgnoreCase("")|| this.name.equals(null)){
              this.setMessage("Please fill the Name .");
              return;
          }
          if(this.gstIn.equalsIgnoreCase("") || this.gstIn.equalsIgnoreCase(null)){
              this.setMessage("Please fill the Gst No.");
          }
          Pattern p = Pattern.compile("^([0][1-9]|[1-2][0-9]|[3][0-7])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$");
          Matcher gstCheck = p.matcher(this.gstIn);
          if(!gstCheck.matches()){
              this.setMessage("Invalid GST No. "+"Please fill the proper GST No.");
              return;
          }
          if(this.state.equalsIgnoreCase("0")){
              this.setMessage("Please select the state.");
              return;
          }
          List list  = beanRemote.getVenderDetail(this.gstIn);
          if(!list.isEmpty()){
              this.setMessage("This gst no already exits.");
              return;
          }
          gridDetail = new ArrayList<>();
          venderMasterPojo pojo = new venderMasterPojo();
          pojo.setGstno(this.gstIn);
          pojo.setName(this.name);
          pojo.setState(this.state);
          String stateDesc = commonRemote.getRefRecDesc("002",this.state);
          pojo.setStateDes(stateDesc);
          
          gridDetail.add(pojo);
            }
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void gridLoadAction(){
        gridDetail = new ArrayList<>();
        try{
           if(function.equalsIgnoreCase("M")){
          List resultList = beanRemote.getVenderDetail(this.gstNo);
          if(resultList.isEmpty()){
              this.setMessage("There is no detail for this gstNo.");
              return;
          }
          for(int i=0 ; i<resultList.size(); i++){
              Vector vec = (Vector) resultList.get(i);
              venderMasterPojo pojo = new venderMasterPojo();
              pojo.setGstno(vec.get(0).toString());
              pojo.setName(vec.get(1).toString());
              pojo.setState(vec.get(2).toString());
              String stateDesc = commonRemote.getRefRecDesc("002",pojo.getState());
              pojo.setStateDes(stateDesc);
              
              gridDetail.add(pojo);
          }
           }  
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void setRowValues(){
        this.displayPanel = "";
        this.disGst ="none";
        try{
            this.setGstIn(currentItem.getGstno().toString());
            this.setName(currentItem.getName().toString());
            List<CbsRefRecTypeTO> stateOptionList = masterDelegate.getFunctionValues("002");
           stateOption = new ArrayList<SelectItem>();
           stateOption.add(new SelectItem("0", "--Select--"));
        for (CbsRefRecTypeTO recTypeTO : stateOptionList) {
                stateOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
           }
            this.setState(currentItem.getState().toString());
            this.gstFlag =false;
            
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void deleteAction(){
        try{
          if(!(currentItem == null)){
              gridDetail.remove(currentItem);
          }  
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void isgstexits(){
        try{
         List list = beanRemote.isgstexits(currentItem.getGstno().toString());
         if(!list.isEmpty()){
             this.setMessage("This gst no already in use. so you can not modify this gst no.");
             this.setGstIn(currentItem.getGstno().toString());
             this.gstFlag = true;
             return;
         }
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void postDetail(){
        try{
           if(function.equalsIgnoreCase("A")){
          List list  = beanRemote.getVenderDetail(this.gstIn);
          if(!list.isEmpty()){
              this.setMessage("This gst no already exits.");
              return;
          }
          if(!(gridDetail == null)){
           String result = beanRemote.saveVenderMasterDetail(gridDetail,getUserName(),getTodayDate(),this.function);
           if(result.equalsIgnoreCase("true")){
               resetForm();
             this.setMessage("Vender master detail save sucessfully.");
           }
          }
           }else if(function.equalsIgnoreCase("M")){
            String result = beanRemote.updateVenderMasterDetail(this.gstIn,this.name,this.state,getUserName());
           if(result.equalsIgnoreCase("true")){
               resetForm();
               this.setMessage("vender master detail update successfully.");
           }
           } 
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void resetForm(){
        this.setMessage("");
        this.setDisGst("none");
        this.setDisplayPanel("");
        this.setGstIn("");
        this.setGstNo("");
        this.setState("0");
        this.setFunction("0");
        this.name ="";
        this.gridDetail = null;
        gridDetail = new ArrayList<venderMasterPojo> ();
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

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<SelectItem> getStateOption() {
        return stateOption;
    }

    public void setStateOption(List<SelectItem> stateOption) {
        this.stateOption = stateOption;
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

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getDisGst() {
        return disGst;
    }

    public void setDisGst(String disGst) {
        this.disGst = disGst;
    }

    public String getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(String displayPanel) {
        this.displayPanel = displayPanel;
    }

    public boolean isSelectRender() {
        return selectRender;
    }

    public void setSelectRender(boolean selectRender) {
        this.selectRender = selectRender;
    }

    public boolean isDeleteRender() {
        return deleteRender;
    }

    public void setDeleteRender(boolean deleteRender) {
        this.deleteRender = deleteRender;
    }

    public boolean isGstFlag() {
        return gstFlag;
    }

    public void setGstFlag(boolean gstFlag) {
        this.gstFlag = gstFlag;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }
    
    
    
 }
