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
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class RelatedPersonInfo {

    private String relatedCustId;
    private String relatedCustName;
    private String relPersonType;
    private List<SelectItem> relPersonTypeList;
    private String relatedFirstName;
    private String relatedMiddleName;
    private String relatedLastName;
    private String relPanNo;
    private String relUid;
    private String relVoterIdCard;
    private String relNregaJobCard;
    private String relPassportNo;
    private Date relPassportExpDt;
    private String relDrivingLiscNo;
    private Date dlExpiry;
    private String relDin;
    private String relExposed;
    private List<SelectItem> relExposedList;
    private String relAdd1;
    private String relAdd2;
    private String relCity;
    private List<SelectItem> relCityList;
    private String relState;
    private List<SelectItem> relStateList;
    private String relCountry;
    private List<SelectItem> relCountryList;
    private String relPostalCode;
    private RelatedPersonsInfoTable currentItem;
    private List<RelatedPersonsInfoTable> rpiTableList;
    private String relStatus;
    private List<SelectItem> relStatusList;
    private String relIndView = "";
    private String relComView = "";
    private boolean otherFieldDisable = true;
    private boolean fieldDisable = true;
    private boolean selVisible = false;
    private boolean delVisible = false;
    private boolean custIdFocus;
    private CustomerDetail customerDetail;
    private CustomerMasterDelegate masterDelegate;
    private CustomerManagementFacadeRemote customerRemote = null;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
    Pattern alphaNumericWithoutSpace = Pattern.compile("[A-Za-z0-9]*");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public RelatedPersonInfo() {
        try {
            customerRemote = (CustomerManagementFacadeRemote) ServiceLocator.getInstance().lookup("CustomerManagementFacade");
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            masterDelegate = new CustomerMasterDelegate();
            initializeForm();
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void initializeForm() {
        try {
            relPersonTypeList = new ArrayList<SelectItem>();
            relExposedList = new ArrayList<SelectItem>();
            relCityList = new ArrayList<SelectItem>();
            relStateList = new ArrayList<SelectItem>();
            relCountryList = new ArrayList<SelectItem>();
            rpiTableList = new ArrayList<RelatedPersonsInfoTable>();    //Related Persons List
            relStatusList = new ArrayList<SelectItem>();

            List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("308");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                relExposedList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            relCityList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("001");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                relCityList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            relStateList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("002");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                relStateList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            relCountryList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("003");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                relCountryList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            relStatusList.add(new SelectItem("0", "--Select--"));
//            relStatusList.add(new SelectItem("F", "Fresh"));
            relStatusList.add(new SelectItem("M", "Modify"));
            relStatusList.add(new SelectItem("D", "Delete"));

//            this.fieldDisable = true;
//            this.otherFieldDisable = true;
//            if (getCustomerDetail().getFunction().equals("M")) {
//                this.fieldDisable = false;
//                this.otherFieldDisable = false;
//
//                //Retrieve all related Persons
//                rpiTableList = customerMgmtRemote.getAllRelatedPersons(getCustomerDetail().getCustId());
//                this.selVisible = true;
//                this.delVisible = true;
//            }
            this.setRelStatus("F");
            this.custIdFocus = false;
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void getCustDetail() {
        getCustomerDetail().setErrorMsg("");
        this.fieldDisable = false;
        fieldFreshAtAddTime();
        this.relatedCustName = "";
        try {
            this.relatedCustName = customerRemote.getCustomerName(this.relatedCustId);
            this.fieldDisable = true;
            this.custIdFocus = true;
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void onblurStatus() {
        try {
            if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {  //Work in only modify case
                if (this.relPersonType != null && !this.relPersonType.equals("0")
                        && ((this.relatedCustName != null && !this.relatedCustName.equals(""))
                        || (this.relatedFirstName != null && !this.relatedFirstName.equals("")))) {
                    //Both relation based on Related Customer Id and Other(i.e. with First Name) can not be together.
                    if ((this.relatedCustName != null && !this.relatedCustName.equals(""))
                            && (this.relatedFirstName != null && !this.relatedFirstName.equals(""))) {
                        getCustomerDetail().setErrorMsg("Relation can be either based on Related Customer Id in CBS or not.");
                        return;
                    }
                    //Checking of already existence of related person. RelatedPersonsInfoTable
                    RelatedPersonsInfoTable tableObj = null;
                    if (this.relatedCustId != null && !this.relatedCustId.equals("")) { //Relation Based On CBS
                        for (RelatedPersonsInfoTable gridObj : rpiTableList) {
                            if (gridObj.getPersonCustomerid() != null) {
                                if (gridObj.getPersonCustomerid().equalsIgnoreCase(relatedCustId)) {
                                    tableObj = gridObj;
                                }
                            }
                        }
                        if (tableObj != null) { //Modify/Delete Case
                            if (!this.relStatus.equals("0")) {
                                tableObj.setDelFlag(this.relStatus); //In case of modify and delete, Status will be what you will select.
                                tableObj.setActionFlag("M");
                            }
                        } else {    //New Added Case
                            RelatedPersonsInfoTable newPerson = new RelatedPersonsInfoTable();
                            newPerson.setCustomerId(getCustomerDetail().getCustId());
                            newPerson.setRelationshipCode(this.relPersonType);
                            newPerson.setPersonCustomerid(this.relatedCustId);

                            List list = customerRemote.getCustomerAllNames(this.relatedCustId);
                            Vector ele = (Vector) list.get(0);
                            String firstName = (ele.get(0) == null ? "" : ele.get(0).toString());
                            String middleName = (ele.get(1) == null ? "" : ele.get(1).toString());
                            String lastName = (ele.get(2) == null ? "" : ele.get(2).toString());

                            newPerson.setPersonFirstName(firstName);
                            newPerson.setPersonMiddleName(middleName);
                            newPerson.setPersonLastName(lastName);
                            newPerson.setCompleteName(firstName + " " + middleName + " " + lastName);
                            newPerson.setDelFlag("F"); // In case of add
                            newPerson.setActionFlag("A");

                            rpiTableList.add(newPerson);
                        }
                    }

                    if (this.relatedFirstName != null && !this.relatedFirstName.equals("")) { //Relation Except CBS
                        for (RelatedPersonsInfoTable gridObj : rpiTableList) {
                            if (gridObj.getPersonFirstName() != null) {
                                if (gridObj.getPersonFirstName().trim().
                                        equalsIgnoreCase(this.relatedFirstName.trim())) {
                                    tableObj = gridObj;
                                }
                            }
                        }
                        if (tableObj != null) { //Modify Case- First Name can not be modified.
                            if (!this.relStatus.equals("0")) {
                                validateRelatedPerson();

                                tableObj.setPersonMiddleName(this.relatedMiddleName);
                                tableObj.setPersonLastName(this.relatedLastName);
                                tableObj.setPersonPan(this.relPanNo);
                                tableObj.setPersonUid(this.relUid);
                                tableObj.setPersonVoterId(this.relVoterIdCard);
                                tableObj.setPersonNrega(this.relNregaJobCard);
                                tableObj.setPersonDl(this.relDrivingLiscNo);
                                tableObj.setPersonDlExpiry(this.dlExpiry == null ? null : dmy.format(this.dlExpiry));
                                tableObj.setPersonPassportNo(this.relPassportNo);
                                tableObj.setPersonPassportExpiry(this.relPassportExpDt == null ? null : dmy.format(this.relPassportExpDt));
                                tableObj.setPersonDin(this.relDin);
                                tableObj.setPersonPoliticalExposed(this.relExposed);
                                tableObj.setPersonAdd1(this.relAdd1);
                                tableObj.setPersonAdd2(this.relAdd2);
                                tableObj.setPersonCity(this.relCity);
                                tableObj.setPersonState(this.relState);
                                tableObj.setPersonCountry(this.relCountry);
                                tableObj.setPersonPin(this.relPostalCode);

                                tableObj.setDelFlag(this.relStatus);
                                tableObj.setActionFlag("M");
                            }
                        } else {    //New Added Case
                            RelatedPersonsInfoTable newPerson = new RelatedPersonsInfoTable();
                            newPerson.setCustomerId(getCustomerDetail().getCustId());
                            newPerson.setRelationshipCode(this.relPersonType);

                            newPerson.setPersonFirstName(this.relatedFirstName);
                            newPerson.setPersonMiddleName(this.relatedMiddleName);
                            newPerson.setPersonLastName(this.relatedLastName);
                            newPerson.setCompleteName(this.relatedFirstName + " " + this.relatedMiddleName + " " + this.relatedLastName);
                            newPerson.setPersonPan(this.relPanNo);
                            newPerson.setPersonUid(this.relUid);
                            newPerson.setPersonVoterId(this.relVoterIdCard);
                            newPerson.setPersonNrega(this.relNregaJobCard);
                            newPerson.setPersonDl(this.relDrivingLiscNo);
                            newPerson.setPersonDlExpiry(this.dlExpiry == null ? null : dmy.format(this.dlExpiry));
                            newPerson.setPersonPassportNo(this.relPassportNo);
                            newPerson.setPersonPassportExpiry(this.relPassportExpDt == null ? null : dmy.format(this.relPassportExpDt));
                            newPerson.setPersonDin(this.relDin);
                            newPerson.setPersonPoliticalExposed(this.relExposed);
                            newPerson.setPersonAdd1(this.relAdd1);
                            newPerson.setPersonAdd2(this.relAdd2);
                            newPerson.setPersonCity(this.relCity);
                            newPerson.setPersonState(this.relState);
                            newPerson.setPersonCountry(this.relCountry);
                            newPerson.setPersonPin(this.relPostalCode);

                            newPerson.setDelFlag("F");
                            newPerson.setActionFlag("A");

                            rpiTableList.add(newPerson);
                        }
                    }
                    getCustomerDetail().setErrorMsg("Related Persons Information is added / modified.");
                }
            } else {
                getCustomerDetail().setErrorMsg("Related Persons Information tab will work in case of MODIFY");
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void fieldFresh() {
        this.relPersonType = "0";
        this.relatedCustId = "";
        this.relatedCustName = "";
        this.relatedFirstName = "";
        this.relatedMiddleName = "";
        this.relatedLastName = "";
        this.relPanNo = "";
        this.relUid = "";
        this.relVoterIdCard = "";
        this.relNregaJobCard = "";
        this.relPassportNo = "";
        this.relPassportExpDt = null;
        this.relDrivingLiscNo = "";
        this.dlExpiry = null;
        this.relDin = "";
        this.setRelExposed("No");
        this.relAdd1 = "";
        this.relAdd2 = "";
        this.setRelCity("0");
        this.setRelState("0");
        this.setRelCountry("0");
        this.relPostalCode = "";
        this.setRelStatus("0");
        this.custIdFocus = false;
        this.rpiTableList.clear();
    }

    public void fieldFreshAtAddTime() {
//        this.setRelPersonType("0");
        this.relatedFirstName = "";
        this.relatedMiddleName = "";
        this.relatedLastName = "";
        this.relPanNo = "";
        this.relUid = "";
        this.relVoterIdCard = "";
        this.relNregaJobCard = "";
        this.relPassportNo = "";
        this.relPassportExpDt = null;
        this.relDrivingLiscNo = "";
        this.dlExpiry = null;
        this.relDin = "";
        this.setRelExposed("No");
        this.relAdd1 = "";
        this.relAdd2 = "";
        this.setRelCity("0");
        this.setRelState("0");
        this.setRelCountry("IND");
        this.relPostalCode = "";
        this.setRelStatus("F");
        this.custIdFocus = false;
    }

//    public void refreshForm() {
//        this.relatedCustId = "";
//        this.relatedCustName = "";
//        this.setRelPersonType("0");
//        this.relatedFirstName = "";
//        this.relatedMiddleName = "";
//        this.relatedLastName = "";
//        this.relPanNo = "";
//        this.relUid = "";
//        this.relVoterIdCard = "";
//        this.relNregaJobCard = "";
//        this.relPassportNo = "";
//        this.relPassportExpDt = null;
//        this.relDrivingLiscNo = "";
//        this.dlExpiry = null;
//        this.relDin = "";
//        this.setRelExposed("No");
//        this.relAdd1 = "";
//        this.relAdd2 = "";
//        this.setRelCity("0");
//        this.setRelState("0");
//        this.setRelCountry("IND");
//        this.relPostalCode = "";
//        rpiTableList = new ArrayList<RelatedPersonsInfoTable>();
//        this.currentItem = null;
//        this.relIndView = "";
//        this.relComView = "";
//        this.setRelStatus("F");
//    }
    public void setTableToForm() {
        try {
            if (currentItem == null) {
                getCustomerDetail().setErrorMsg("Please select a row from table !");
                return;
            }
            //Data displaying on the page.
            this.setRelPersonType(currentItem.getRelationshipCode());
            this.relatedCustId = currentItem.getPersonCustomerid() == null ? "" : currentItem.getPersonCustomerid();
            if (this.relatedCustId == null || this.relatedCustId.equals("")) {
                this.relatedFirstName = currentItem.getPersonFirstName() == null ? "" : currentItem.getPersonFirstName();
                this.relatedMiddleName = currentItem.getPersonMiddleName() == null ? "" : currentItem.getPersonMiddleName();
                this.relatedLastName = currentItem.getPersonLastName() == null ? "" : currentItem.getPersonLastName();
                this.relatedCustName = "";
            } else {
                this.relatedFirstName = "";
                this.relatedMiddleName = "";
                this.relatedLastName = "";
                this.relatedCustName = customerRemote.getCustomerName(this.relatedCustId);
            }
            this.relPanNo = currentItem.getPersonPan() == null ? "" : currentItem.getPersonPan();
            this.relUid = currentItem.getPersonUid() == null ? "" : currentItem.getPersonUid();
            this.relVoterIdCard = currentItem.getPersonVoterId() == null ? "" : currentItem.getPersonVoterId();
            this.relNregaJobCard = currentItem.getPersonNrega() == null ? "" : currentItem.getPersonNrega();
            this.relPassportNo = currentItem.getPersonPassportNo() == null ? "" : currentItem.getPersonPassportNo();
            this.relPassportExpDt = (currentItem.getPersonPassportExpiry() == null || currentItem.getPersonPassportExpiry().equals("")) ? null : dmy.parse(currentItem.getPersonPassportExpiry());
            this.relDrivingLiscNo = currentItem.getPersonDl() == null ? "" : currentItem.getPersonDl();
            this.dlExpiry = (currentItem.getPersonDlExpiry() == null || currentItem.getPersonDlExpiry().equals("")) ? null : dmy.parse(currentItem.getPersonDlExpiry());
            this.relDin = currentItem.getPersonDin() == null ? "" : currentItem.getPersonDin();
            this.setRelExposed(currentItem.getPersonPoliticalExposed() == null ? "No" : currentItem.getPersonPoliticalExposed());
            this.relAdd1 = currentItem.getPersonAdd1() == null ? "" : currentItem.getPersonAdd1();
            this.relAdd2 = currentItem.getPersonAdd2() == null ? "" : currentItem.getPersonAdd2();
            this.setRelCity(currentItem.getPersonCity() == null ? "0" : currentItem.getPersonCity());
            this.setRelState(currentItem.getPersonState() == null ? "0" : currentItem.getPersonState());
            this.setRelCountry(currentItem.getPersonCountry() == null ? "0" : currentItem.getPersonCountry());
            this.relPostalCode = currentItem.getPersonPin() == null ? "" : currentItem.getPersonPin();
            this.relStatus = (currentItem.getDelFlag() == null) ? "0" : currentItem.getDelFlag();
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void deleteForm() {
        try {
            if (currentItem == null) {
                getCustomerDetail().setErrorMsg("Please select a row from table !");
                return;
            }
            if (currentItem.getDelFlag().equalsIgnoreCase("F")
                    && currentItem.getActionFlag().equalsIgnoreCase("A")) {
                rpiTableList.remove(currentItem);
            } else {
                getCustomerDetail().setErrorMsg("This entry can not be delete !");
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

//    public void fetchCurrentRow2(ActionEvent event) {
//        String customerId = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("custId"));
//        currentRowTodRef = currentRowTodRef + 1;
//        currentRowTodRef = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
//        for (RelatedPersonsInfoTable item : rpiTableList) {
//            if (item.getCustId().equalsIgnoreCase(customerId)) {
//                currentItemRpi = item;
//                break;
//            }
//        }
//    }
//    public void selectRpi() {
//        getCustomerDetail().setErrorMsg("");
//        selectCountTodRef = 1;
//        if (rpiCustId != null && !(this.rpiCustId.equalsIgnoreCase("0") && currentItemRpi.getCustId() != null)) {
//            if (!this.rpiCustId.equalsIgnoreCase("")) {
//                if (this.rpiCustId.equalsIgnoreCase(currentItemRpi.getCustId())) {
//                    count2FTodRef = count1TodRef;
//                    count1TodRef = count1TodRef + 1;
//                    if (count2FTodRef != count1TodRef) {
//                        loadRpiGrid();
//                    }
//                } else {
//                    count1TodRef = 0;
//                }
//            }
//        }
//        sNo = currentItemRpi.getSrNo();
//        this.setRpiCustId(currentItemRpi.getCustId());
//        this.setRpiRelation(currentItemRpi.getCustRelation());
//        this.setRpiname(currentItemRpi.getCustName());
//        this.setDescription(currentItemRpi.getCustDescription());
//        rpiTableList.remove(currentItemRpi);
//        this.setFlag4(false);
//    }
//    public void loadRpiGrid() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher rpiDescCheck = p.matcher(this.description);
//        if (rpiDescCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in RPI Description.");
//            return;
//        } else {
//            getCustomerDetail().setErrorMsg("");
//        }
//        List<RelatedPersonsInfoTable> listRpi = rpiTableList;
//        rpitable = new RelatedPersonsInfoTable();
//        if (getCustomerDetail().getFunction().equalsIgnoreCase("a") || getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
//            if (selectCountTodRef == 0) {
//                for (int i = 0; i < listRpi.size(); i++) {
//                    String rpiCustomerId = listRpi.get(i).getCustId();
//                    if (this.rpiCustId.equalsIgnoreCase(rpiCustomerId)) {
//                        getCustomerDetail().setErrorMsg("Entry Already Exists In The Table");
//                        return;
//                    }
//                }
//            }
//        }
//        getCustomerDetail().setErrorMsg("");
//        RelatedPersonsInfoTable rpiTab = new RelatedPersonsInfoTable();
//        rpiTab.setCustId(rpiCustId);
//        rpiTab.setCustName(rpiname);
//        rpiTab.setCustRelation(rpiRelation);
//        rpiTab.setCustDescription(description);
//        if (getCustomerDetail().getFunction().equalsIgnoreCase("a")) {
//            rpiTab.setSaveUpdateCounter("Save");
//            this.setRpiCustId("");
//            this.setRpiRelation("");
//            this.setRpiname("");
//            this.setDescription("");
//        }
//        rpiTableList.add(rpiTab);
//        this.setFlag4(false);
//        if (getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
//            if (selectCountTodRef != 1) {
//                for (int i = 0; i < listRpi.size(); i++) {
//                    String rpiCusId = listRpi.get(i).getCustId();
//                    if (!rpiCusId.equalsIgnoreCase(this.rpiCustId)) {
//                        rpiTab.setSaveUpdateCounter("Save");
//                        rpiTableList1.add(rpiTab);
//                        this.setRpiCustId("");
//                        this.setRpiRelation("");
//                        this.setRpiname("");
//                        this.setDescription("");
//                        return;
//                    }
//                }
//            }
//            if (currentItemRpi.getCustId() != null) {
//                if (currentItemRpi.getCustId().equalsIgnoreCase(this.rpiCustId)) {
//                    if (!currentItemRpi.getCustDescription().equalsIgnoreCase(this.description) || !currentItemRpi.getCustName().equalsIgnoreCase(this.rpiname) || !currentItemRpi.getCustRelation().equalsIgnoreCase(this.rpiRelation)) {
//                        rpiTab.setSaveUpdateCounter("Update");
//                        rpiTab.setSrNo(sNo);
//                        rpiTableList1.add(rpiTab);
//                        this.setRpiCustId("");
//                        this.setRpiRelation("");
//                        this.setRpiname("");
//                        this.setDescription("");
//                    }
//                    selectCountTodRef = 0;
//                }
//            } else {
//                rpiTab.setSaveUpdateCounter("Save");
//                rpiTableList1.add(rpiTab);
//                this.setRpiCustId("");
//                this.setRpiRelation("");
//                this.setRpiname("");
//                this.setDescription("");
//            }
//        }
//    }
//    public void deleteGridRpi() {
//        rpiTableList.remove(currentItemRpi);
//    }
//    public void onBlurRpiCustid() {
//        try {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher rpiCustIdCheck = p.matcher(this.rpiCustId);
//            if (!rpiCustIdCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in RPI Customer Id.");
//                return;
//            }
//            CBSCustomerMasterDetailTO custDetailsByCustIdTo = masterDelegate.getCustDetailsByCustId(rpiCustId);
//            getCustomerDetail().setErrorMsg("");
//            this.setRpiname(custDetailsByCustIdTo.getCustname());
//
//        } catch (Exception e) {
//            getCustomerDetail().setErrorMsg(e.getMessage());
//        }
//    }
//
//    public void onblurRpiName() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher rpiNameCheck = p.matcher(this.rpiname);
//        if (rpiNameCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in RPI Name.");
//            return;
//        } else if (this.rpiname.contains("0") || this.rpiname.contains("1") || this.rpiname.contains("2") || this.rpiname.contains("3") || this.rpiname.contains("4") || this.rpiname.contains("5")
//                || this.rpiname.contains("6") || this.rpiname.contains("7") || this.rpiname.contains("8") || this.rpiname.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in RPI Name.");
//            return;
//        } else {
//            getCustomerDetail().setErrorMsg("");
//        }
//    }
    public void selectFieldValues() {
//        Date date = new Date();
        System.out.println("In Related Person Detail");
        try {
//            if (rpiTableList != null) {
//                rpiTableList.clear();
//            }
            this.rpiTableList.clear();

            List<CBSRelatedPersonsDetailsTO> tosList = masterDelegate.
                    getRelatedPersonInfoByCustId(getCustomerDetail().getCustId());

            if (!tosList.isEmpty()) {
                for (CBSRelatedPersonsDetailsTO to : tosList) {
                    RelatedPersonsInfoTable gridPojo = new RelatedPersonsInfoTable();

                    gridPojo.setCustomerId(to.getcBSRelatedPersonsDetailsPKTO().getCustomerId());
                    gridPojo.setPersonSrNo(String.valueOf(to.getcBSRelatedPersonsDetailsPKTO().getPersonSrNo()));
                    gridPojo.setPersonCustomerid((to.getPersonCustomerId() == null
                            || to.getPersonCustomerId().equals("")) ? "" : to.getPersonCustomerId());
                    gridPojo.setPersonFirstName((to.getPersonFirstName() == null
                            || to.getPersonFirstName().equals("")) ? "" : to.getPersonFirstName());
                    gridPojo.setPersonMiddleName((to.getPersonMiddleName() == null
                            || to.getPersonMiddleName().equals("")) ? "" : to.getPersonMiddleName());
                    gridPojo.setPersonLastName((to.getPersonLastName() == null
                            || to.getPersonLastName().equals("")) ? "" : to.getPersonLastName());
                    gridPojo.setCompleteName(gridPojo.getPersonFirstName() + " "
                            + gridPojo.getPersonMiddleName() + " " + gridPojo.getPersonLastName());
                    gridPojo.setRelationshipCode((to.getRelationshipCode() == null
                            || to.getRelationshipCode().equals("")) ? "" : to.getRelationshipCode());
                    gridPojo.setPersonPan((to.getPan() == null
                            || to.getPan().equals("")) ? "" : to.getPan());
                    gridPojo.setPersonUid((to.getUid() == null
                            || to.getUid().equals("")) ? "" : to.getUid());
                    gridPojo.setPersonVoterId((to.getVoterId() == null
                            || to.getVoterId().equals("")) ? "" : to.getVoterId());
                    gridPojo.setPersonNrega((to.getNrega() == null
                            || to.getNrega().equals("")) ? "" : to.getNrega());
                    gridPojo.setPersonDl((to.getDl() == null
                            || to.getDl().equals("")) ? "" : to.getDl());
                    gridPojo.setPersonDlExpiry((to.getDlExpiry() == null
                            ? "" : to.getDlExpiry()));
                    gridPojo.setPersonPassportNo((to.getPassportNo() == null
                            || to.getPassportNo().equals("")) ? "" : to.getPassportNo());
                    gridPojo.setPersonPassportExpiry((to.getPassportExpiry() == null
                            ? "" : to.getPassportExpiry()));
                    gridPojo.setPersonDin((to.getDin() == null
                            || to.getDin().equals("")) ? "" : to.getDin());
                    gridPojo.setPersonPoliticalExposed((to.getExposed() == null
                            || to.getExposed().equals("")) ? "" : to.getExposed());
                    gridPojo.setPersonAdd1((to.getAdd1() == null
                            || to.getAdd1().equals("")) ? "" : to.getAdd1());
                    gridPojo.setPersonAdd2((to.getAdd2() == null
                            || to.getAdd2().equals("")) ? "" : to.getAdd2());
                    gridPojo.setPersonCity((to.getCity() == null
                            || to.getCity().equals("")) ? "" : to.getCity());
                    gridPojo.setPersonState((to.getState() == null
                            || to.getState().equals("")) ? "" : to.getState());
                    gridPojo.setPersonCountry((to.getCountry() == null
                            || to.getCountry().equals("")) ? "" : to.getCountry());
                    gridPojo.setPersonPin((to.getPin() == null
                            || to.getPin().equals("")) ? "" : to.getPin());
                    gridPojo.setDelFlag((to.getDelFlag() == null
                            || to.getDelFlag().equals("")) ? "" : to.getDelFlag());
                    gridPojo.setActionFlag("");

                    rpiTableList.add(gridPojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String validateRelatedPerson() {
        getCustomerDetail().setErrorMsg("");

        String valMsg = validateRelFirstName();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelMiddleName();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelLastName();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelPanNo();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelUid();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelNrega();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelPassport();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelVoterId();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelDl();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }

        valMsg = validateRelDin();
        if (!valMsg.equalsIgnoreCase("ok")) {
            return valMsg;
        }
        return "ok";
    }

    public String validateRelFirstName() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relatedFirstName == null || this.relatedFirstName.equals(""))) {
            Matcher matcher = onlyAlphabetWitSpace.matcher(this.relatedFirstName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphabet is allowed for First Name on Related Persons Information tab");
                return "Only alphabet is allowed for First Name on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelMiddleName() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relatedMiddleName == null || this.relatedMiddleName.equals(""))) {
            Matcher matcher = onlyAlphabetWitSpace.matcher(this.relatedMiddleName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphabet is allowed for Middle Name on Related Persons Information tab");
                return "Only alphabet is allowed for Middle Name on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelLastName() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relatedLastName == null || this.relatedLastName.equals(""))) {
            Matcher matcher = onlyAlphabetWitSpace.matcher(this.relatedLastName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphabet is allowed for Last Name on Related Persons Information tab");
                return "Only alphabet is allowed for Last Name on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelPanNo() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relPanNo == null || this.relPanNo.equals(""))) {
            if (this.relPanNo.length() != 10) {
                getCustomerDetail().setErrorMsg("Pan No should be of 10 digit on Related Persons Information tab");
                return "Pan No should be of 10 digit on Related Persons Information tab";
            }
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relPanNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for Pan No on Related Persons Information tab");
                return "Only alphanumeric is allowed for Pan No on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelUid() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relUid == null || this.relUid.equals(""))) {
            if (this.relUid.length() != 12) {
                getCustomerDetail().setErrorMsg("UID should be of 12 digit on Related Persons Information tab");
                return "UID should be of 12 digit on Related Persons Information tab";
            }
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relUid);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for UID on Related Persons Information tab");
                return "Only alphanumeric is allowed for UID on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelNrega() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relNregaJobCard == null || this.relNregaJobCard.equals(""))) {
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relNregaJobCard);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for NREGA Job Card on Related Persons Information tab");
                return "Only alphanumeric is allowed for NREGA Job Card on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelPassport() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relPassportNo == null || this.relPassportNo.equals(""))) {
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relPassportNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for Passport No on Related Persons Information tab");
                return "Only alphanumeric is allowed for Passport No on Related Persons Information tab";
            }
            if (this.relPassportExpDt == null) {
                getCustomerDetail().setErrorMsg("Passport Expiry Date is mandatory if there is Passport No on Related Persons Information tab");
                return "Passport Expiry Date is mandatory if there is Passport No on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelVoterId() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relVoterIdCard == null || this.relVoterIdCard.equals(""))) {
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relVoterIdCard);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for Voter ID Card on Related Persons Information tab");
                return "Only alphanumeric is allowed for Voter ID Card on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelDl() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relDrivingLiscNo == null || this.relDrivingLiscNo.equals(""))) {
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relDrivingLiscNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for Driving License No on Related Persons Information tab");
                return "Only alphanumeric is allowed for Driving License No on Related Persons Information tab";
            }
            if (this.dlExpiry == null) {
                getCustomerDetail().setErrorMsg("Driving License Expiry Date is mandatory if there is Driving License No on Related Persons Information tab");
                return "Driving License Expiry Date is mandatory if there is Driving License No on Related Persons Information tab";
            }
        }
        return "ok";
    }

    public String validateRelDin() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.relDin == null || this.relDin.equals(""))) {
            Matcher matcher = alphaNumericWithoutSpace.matcher(this.relDin);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Only alphanumeric is allowed for DIN on Related Persons Information tab");
                return "Only alphanumeric is allowed for DIN on Related Persons Information tab";
            }
        }
        return "ok";
    }

    //Getter And Setter
    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public List<RelatedPersonsInfoTable> getRpiTableList() {
        return rpiTableList;
    }

    public void setRpiTableList(List<RelatedPersonsInfoTable> rpiTableList) {
        this.rpiTableList = rpiTableList;
    }

    public String getRelatedCustId() {
        return relatedCustId;
    }

    public void setRelatedCustId(String relatedCustId) {
        this.relatedCustId = relatedCustId;
    }

    public String getRelPersonType() {
        return relPersonType;
    }

    public void setRelPersonType(String relPersonType) {
        this.relPersonType = relPersonType;
    }

    public List<SelectItem> getRelPersonTypeList() {
        return relPersonTypeList;
    }

    public void setRelPersonTypeList(List<SelectItem> relPersonTypeList) {
        this.relPersonTypeList = relPersonTypeList;
    }

    public String getRelatedFirstName() {
        return relatedFirstName;
    }

    public void setRelatedFirstName(String relatedFirstName) {
        this.relatedFirstName = relatedFirstName;
    }

    public String getRelatedMiddleName() {
        return relatedMiddleName;
    }

    public void setRelatedMiddleName(String relatedMiddleName) {
        this.relatedMiddleName = relatedMiddleName;
    }

    public String getRelatedLastName() {
        return relatedLastName;
    }

    public void setRelatedLastName(String relatedLastName) {
        this.relatedLastName = relatedLastName;
    }

    public String getRelPanNo() {
        return relPanNo;
    }

    public void setRelPanNo(String relPanNo) {
        this.relPanNo = relPanNo;
    }

    public String getRelUid() {
        return relUid;
    }

    public void setRelUid(String relUid) {
        this.relUid = relUid;
    }

    public String getRelVoterIdCard() {
        return relVoterIdCard;
    }

    public void setRelVoterIdCard(String relVoterIdCard) {
        this.relVoterIdCard = relVoterIdCard;
    }

    public String getRelNregaJobCard() {
        return relNregaJobCard;
    }

    public void setRelNregaJobCard(String relNregaJobCard) {
        this.relNregaJobCard = relNregaJobCard;
    }

    public String getRelPassportNo() {
        return relPassportNo;
    }

    public void setRelPassportNo(String relPassportNo) {
        this.relPassportNo = relPassportNo;
    }

    public Date getRelPassportExpDt() {
        return relPassportExpDt;
    }

    public void setRelPassportExpDt(Date relPassportExpDt) {
        this.relPassportExpDt = relPassportExpDt;
    }

    public String getRelDrivingLiscNo() {
        return relDrivingLiscNo;
    }

    public void setRelDrivingLiscNo(String relDrivingLiscNo) {
        this.relDrivingLiscNo = relDrivingLiscNo;
    }

    public Date getDlExpiry() {
        return dlExpiry;
    }

    public void setDlExpiry(Date dlExpiry) {
        this.dlExpiry = dlExpiry;
    }

    public String getRelDin() {
        return relDin;
    }

    public void setRelDin(String relDin) {
        this.relDin = relDin;
    }

    public String getRelExposed() {
        return relExposed;
    }

    public void setRelExposed(String relExposed) {
        this.relExposed = relExposed;
    }

    public List<SelectItem> getRelExposedList() {
        return relExposedList;
    }

    public void setRelExposedList(List<SelectItem> relExposedList) {
        this.relExposedList = relExposedList;
    }

    public String getRelAdd1() {
        return relAdd1;
    }

    public void setRelAdd1(String relAdd1) {
        this.relAdd1 = relAdd1;
    }

    public String getRelAdd2() {
        return relAdd2;
    }

    public void setRelAdd2(String relAdd2) {
        this.relAdd2 = relAdd2;
    }

    public String getRelCity() {
        return relCity;
    }

    public void setRelCity(String relCity) {
        this.relCity = relCity;
    }

    public String getRelState() {
        return relState;
    }

    public void setRelState(String relState) {
        this.relState = relState;
    }

    public String getRelPostalCode() {
        return relPostalCode;
    }

    public void setRelPostalCode(String relPostalCode) {
        this.relPostalCode = relPostalCode;
    }

    public String getRelCountry() {
        return relCountry;
    }

    public void setRelCountry(String relCountry) {
        this.relCountry = relCountry;
    }

    public String getRelatedCustName() {
        return relatedCustName;
    }

    public void setRelatedCustName(String relatedCustName) {
        this.relatedCustName = relatedCustName;
    }

    public List<SelectItem> getRelCityList() {
        return relCityList;
    }

    public void setRelCityList(List<SelectItem> relCityList) {
        this.relCityList = relCityList;
    }

    public List<SelectItem> getRelStateList() {
        return relStateList;
    }

    public void setRelStateList(List<SelectItem> relStateList) {
        this.relStateList = relStateList;
    }

    public List<SelectItem> getRelCountryList() {
        return relCountryList;
    }

    public void setRelCountryList(List<SelectItem> relCountryList) {
        this.relCountryList = relCountryList;
    }

    public RelatedPersonsInfoTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(RelatedPersonsInfoTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getRelIndView() {
        return relIndView;
    }

    public void setRelIndView(String relIndView) {
        this.relIndView = relIndView;
    }

    public String getRelComView() {
        return relComView;
    }

    public void setRelComView(String relComView) {
        this.relComView = relComView;
    }

    public boolean isFieldDisable() {
        return fieldDisable;
    }

    public void setFieldDisable(boolean fieldDisable) {
        this.fieldDisable = fieldDisable;
    }

    public String getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    public List<SelectItem> getRelStatusList() {
        return relStatusList;
    }

    public void setRelStatusList(List<SelectItem> relStatusList) {
        this.relStatusList = relStatusList;
    }

    public boolean isOtherFieldDisable() {
        return otherFieldDisable;
    }

    public void setOtherFieldDisable(boolean otherFieldDisable) {
        this.otherFieldDisable = otherFieldDisable;
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

    public boolean isCustIdFocus() {
        return custIdFocus;
    }

    public void setCustIdFocus(boolean custIdFocus) {
        this.custIdFocus = custIdFocus;
    }
}
