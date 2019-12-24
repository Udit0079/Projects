/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.RelatedPersonsInfoTable;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class RelatedPerson  {
    private String relPersonType;
    private List<SelectItem> relPersonTypeList;
    private String relatedCustId;
    private String relatedCustName;
    private String flagType;
    private List<SelectItem> flagTypeList; 
    private boolean otherFieldDisable = false;
    private CustomerDetail customerDetail;
    private CustomerMasterDelegate masterDelegate;
    private RelatedPersonsInfoTable currentItem;
    private boolean selVisible = false;
    private boolean delVisible = false;
    private List<RelatedPersonsInfoTable> rpiTableList;
    private CustomerManagementFacadeRemote customerRemote ;
    private CustomerMgmtFacadeRemote customerMgmtRemote ;
    Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
    Pattern alphaNumericWithoutSpace = Pattern.compile("[A-Za-z0-9]*");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

   public RelatedPerson(){
       try{
       customerRemote = (CustomerManagementFacadeRemote) ServiceLocator.getInstance().lookup("CustomerManagementFacade");
       customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
       masterDelegate = new CustomerMasterDelegate();
       initForm();
       fieldFresh();
       }catch(Exception ex){
           getCustomerDetail().setErrorMsg(ex.getMessage());
       }
  }
    
   public void initForm(){
       try{
         rpiTableList = new ArrayList<RelatedPersonsInfoTable>();
         flagTypeList = new ArrayList<SelectItem>();
         flagTypeList.add(new SelectItem("0","--Select--"));
         flagTypeList.add(new SelectItem("F","Add"));
         flagTypeList.add(new SelectItem("D","Delete"));
         
         
         List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("309");
         relPersonTypeList= new ArrayList<SelectItem>();
         relPersonTypeList.add(new SelectItem("0","--Select--"));
         functionList = masterDelegate.getFunctionValues("309");
         for(CbsRefRecTypeTO recTypeTO : functionList){
             relPersonTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                     recTypeTO.getRefDesc()));
             
         this.setFlagType("0");
         }
    }catch(Exception e){
           getCustomerDetail().setErrorMsg(e.getMessage());
       }
    }
   
   public void deleteForm(){
       try{
           if(currentItem==null){
               getCustomerDetail().setErrorMsg("Please select the row from the table.");
           }
//           this.setCurrentItem(null);
//           this.rpiTableList=null;
       rpiTableList.remove(currentItem);
        this.setFlagType("");
        this.setRelPersonType("0");
        this.setRelatedCustId("");
        this.setRelatedCustName("");
       }catch(Exception ex){
           getCustomerDetail().setErrorMsg(ex.getMessage());
       }
   }
   
    public void setTableToForm(){
        try{
             if (currentItem == null) {
                getCustomerDetail().setErrorMsg("Please select a row from table !");
                return;
            }
             this.relPersonType= currentItem.getRelationshipCode()==null ? "" : currentItem.getRelationshipCode();
             this.relatedCustId= currentItem.getPersonCustomerid()==null ? "" : currentItem.getPersonCustomerid();
             this.flagType = currentItem.getDelFlag()==null ? "" : currentItem.getDelFlag();
            
        }catch(Exception ex){
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }
    
    public void getCustDetail(){
        getCustomerDetail().setErrorMsg("");
        //fieldFreshAtAddTime();
        this.relatedCustName = "";
        try{
            this.relatedCustName= customerRemote.getCustomerName(this.relatedCustId);
        }catch(Exception ex){
            getCustomerDetail().setErrorMsg(ex.getMessage());
                    }
    }
    
    public void selectFieldValues() {
        this.selVisible=true;
        this.delVisible=true;
        System.out.println("In Related Person");
        try{
             // this.rpiTableList.clear();
             List<CBSRelatedPersonsDetailsTO> tosList = masterDelegate.
                    getRelatedPersonInfoByCustId(getCustomerDetail().getCustId());
             if(!tosList.isEmpty()){
                  for (CBSRelatedPersonsDetailsTO to : tosList) {
                       RelatedPersonsInfoTable fldvalue = new RelatedPersonsInfoTable();
                       fldvalue.setCustomerId(to.getcBSRelatedPersonsDetailsPKTO().getCustomerId());
                       fldvalue.setPersonCustomerid(to.getPersonCustomerId()==null ||
                               to.getPersonCustomerId().equals("") ?"" :to.getPersonCustomerId());
                       fldvalue.setRelationshipCode(to.getRelationshipCode()== null || 
                               to.getRelationshipCode().equals("")? "" : to.getRelationshipCode());
                       fldvalue.setDelFlag(to.getDelFlag()==null || to.getDelFlag().equals("")? "" : to.getDelFlag());
                       fldvalue.setCompleteName(to.getPersonFirstName() + "" + to.getPersonMiddleName() 
                               + "" + to.getPersonLastName());

                      rpiTableList.add(fldvalue);
             }
          }
       }catch(Exception ex){
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }
    
    public void onblurflagType(){
        this.selVisible=true;
        this.delVisible=true;
        try{
            if(getCustomerDetail().getFunction().equalsIgnoreCase("0")
                    ||getCustomerDetail().getFunction().equalsIgnoreCase(null)){
                getCustomerDetail().setErrorMsg("Please Select the Function.");
            }else if(getCustomerDetail().getFunction().equalsIgnoreCase("A")){
                getCustomerDetail().setErrorMsg("Related person Tab Works only in Modify case, "
                        + "Please Release this tab for Add case.");
            }else if(getCustomerDetail().getFunction().equalsIgnoreCase("M")){
                RelatedPersonsInfoTable tableObj = null;
                if (this.relatedCustId != null && !this.relatedCustId.equals("")){
                     for (RelatedPersonsInfoTable gridObj : rpiTableList){
                          if (gridObj.getPersonCustomerid() != null) {
                                if (gridObj.getPersonCustomerid().equalsIgnoreCase(relatedCustId)) {
                                    tableObj = gridObj;
                                }
                          }
                  if (tableObj != null) { //Modify/Delete Case
                            if (!this.flagType.equals("0")) {
                                tableObj.setDelFlag(this.flagType); //In case of add and delete, falgType will be what you will select.
                                tableObj.setActionFlag("M");
                            }
                  }else{
                       RelatedPersonsInfoTable newPerson = new RelatedPersonsInfoTable();
                       newPerson.setCustomerId(getCustomerDetail().getCustId());
                       newPerson.setPersonCustomerid(this.relatedCustId);
                        List list = customerRemote.getCustomerAllNames(this.relatedCustId);
                            Vector ele = (Vector) list.get(0);
                            String firstName = (ele.get(0) == null ? "" : ele.get(0).toString());
                            String middleName = (ele.get(1) == null ? "" : ele.get(1).toString());
                            String lastName = (ele.get(2) == null ? "" : ele.get(2).toString());
                       newPerson.setPersonFirstName(firstName);
                       newPerson.setPersonMiddleName(middleName);
                       newPerson.setPersonLastName(lastName);
                       newPerson.setCompleteName(customerRemote.getCustomerName(this.relatedCustId));
                       newPerson.setRelationshipCode(this.relPersonType);
                       newPerson.setDelFlag("F");// In case of add
                       newPerson.setActionFlag("A");
                        rpiTableList.add(newPerson);
                  }
                }
            }
                if(this.rpiTableList!=null){
                    RelatedPersonsInfoTable newPerson = new RelatedPersonsInfoTable();
                    newPerson.setPersonCustomerid(this.relatedCustId);
                    newPerson.setCustomerId(getCustomerDetail().getCustId());
                    List list = customerRemote.getCustomerAllNames(this.relatedCustId);
                            Vector ele = (Vector) list.get(0);
                            String firstName = (ele.get(0) == null ? "" : ele.get(0).toString());
                            String middleName = (ele.get(1) == null ? "" : ele.get(1).toString());
                            String lastName = (ele.get(2) == null ? "" : ele.get(2).toString());
                    newPerson.setPersonFirstName(firstName);
                    newPerson.setPersonMiddleName(middleName);
                    newPerson.setPersonLastName(lastName);
                    newPerson.setCompleteName(customerRemote.getCustomerName(this.relatedCustId));
                    newPerson.setRelationshipCode(this.relPersonType);
                    newPerson.setDelFlag(this.flagType);
                    newPerson.setActionFlag("M");
                     
                    rpiTableList.add(newPerson);
                 getCustomerDetail().setErrorMsg("Related Persons Information is added / modified.");
                
//                }else if((this.rpiTableList!=null  && this.getCustomerDetail().getFunction().equals("A"))){
//                    RelatedPersonsInfoTable newPerson = new RelatedPersonsInfoTable();
//                    newPerson.setPersonCustomerid(this.relatedCustId);
//                    newPerson.setCustomerId(getCustomerDetail().getCustId());
//                    List list = customerRemote.getCustomerAllNames(this.relatedCustId);
//                            Vector ele = (Vector) list.get(0);
//                            String firstName = (ele.get(0) == null ? "" : ele.get(0).toString());
//                            String middleName = (ele.get(1) == null ? "" : ele.get(1).toString());
//                            String lastName = (ele.get(2) == null ? "" : ele.get(2).toString());
//                    newPerson.setPersonFirstName(firstName);
//                    newPerson.setPersonMiddleName(middleName);
//                    newPerson.setPersonLastName(lastName);
//                    newPerson.setCompleteName(customerRemote.getCustomerName(this.relatedCustId));
//                    newPerson.setRelationshipCode(this.relPersonType);
//                    newPerson.setDelFlag(this.flagType);
//                    newPerson.setActionFlag("A");
//                     
//                    rpiTableList.add(newPerson);
//                 getCustomerDetail().setErrorMsg("Related Persons Information is added / modified.");
//                
//                 
                 }
            } 
        }catch(Exception ex){
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        
    }
        
    
    
    public void fieldFresh(){
        this.otherFieldDisable=false;
        this.setFlagType("");
        this.setRelPersonType("0");
        this.setRelatedCustId("");
        this.setRelatedCustName("");
        rpiTableList= new ArrayList<RelatedPersonsInfoTable>();
        this.currentItem= null;
        rpiTableList.clear();
       
   }
    
   public void fieldFreshAtAddTime(){
       this.otherFieldDisable=false;
       this.flagType="";
       this.relatedCustId="";
       this.relPersonType="0";
       this.setRelatedCustName("");
    }
    
    public String getRelPersonType() {
        return relPersonType;
    }

    public void setRelPersonType(String relPersonType) {
        this.relPersonType = relPersonType;
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType;
    }

    public List<SelectItem> getFlagTypeList() {
        return flagTypeList;
    }

    public void setFlagTypeList(List<SelectItem> flagTypeList) {
        this.flagTypeList = flagTypeList;
    }
    public List<SelectItem> getRelPersonTypeList() {
        return relPersonTypeList;
    }

    public void setRelPersonTypeList(List<SelectItem> relPersonTypeList) {
        this.relPersonTypeList = relPersonTypeList;
    }

    public String getRelatedCustId() {
        return relatedCustId;
    }

    public void setRelatedCustId(String relatedCustId) {
        this.relatedCustId = relatedCustId;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public CustomerMgmtFacadeRemote getCustomerMgmtRemote() {
        return customerMgmtRemote;
    }

    public void setCustomerMgmtRemote(CustomerMgmtFacadeRemote customerMgmtRemote) {
        this.customerMgmtRemote = customerMgmtRemote;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public CustomerManagementFacadeRemote getCustomerRemote() {
        return customerRemote;
    }

    public void setCustomerRemote(CustomerManagementFacadeRemote customerRemote) {
        this.customerRemote = customerRemote;
    }

    public boolean isOtherFieldDisable() {
        return otherFieldDisable;
    }

    public void setOtherFieldDisable(boolean otherFieldDisable) {
        this.otherFieldDisable = otherFieldDisable;
    }

    public List<RelatedPersonsInfoTable> getRpiTableList() {
        return rpiTableList;
    }

    public void setRpiTableList(List<RelatedPersonsInfoTable> rpiTableList) {
        this.rpiTableList = rpiTableList;
    }

    public RelatedPersonsInfoTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(RelatedPersonsInfoTable currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isSelVisible() {
        return selVisible;
    }

    public void setSelVisible(boolean selVisible) {
        this.selVisible = selVisible;
    }

    public boolean isDelVisible() {
        return delVisible;
    }

    public void setDelVisible(boolean delVisible) {
        this.delVisible = delVisible;
    }

    public String getRelatedCustName() {
        return relatedCustName;
    }

    public void setRelatedCustName(String relatedCustName) {
        this.relatedCustName = relatedCustName;
    }
}
