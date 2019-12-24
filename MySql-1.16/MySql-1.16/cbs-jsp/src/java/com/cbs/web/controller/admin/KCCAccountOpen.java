/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.FdDdsAccountOpeningFacadeRemote;
import com.cbs.web.pojo.admin.KccImovableAssets;
import com.cbs.web.pojo.admin.KccMovableAssets;
import com.cbs.web.pojo.admin.KccParticularHoldingTable;
import com.cbs.web.pojo.admin.KccLandHoldingTable;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
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
public class KCCAccountOpen  extends BaseBean{
    private String message;
    private List<SelectItem> acctTypeOption;
    private List<SelectItem> occupationOption;
    private List<SelectItem> operationModeOption;
    private List<SelectItem> securityPraposalOption;
    private List<SelectItem> villageOption;
    private List<KccLandHoldingTable> landHoldingDetail;
    private String villages;
    private String blockNumber;
    private String ownedLands;
    private String leaseds;
    private String croppers;
    private String areaAcress;
    private String irrigateds;
    private String sourceOfIrrigations;
    private String incumbrances;
    private int currentRow;
    public List<KccParticularHoldingTable> libilitiesDetails;
    private String institutions;
    private String purposes;
    private String osAmounts;
    private String overDues;
    private String securitys;
    private String installAmts;
    public List<KccMovableAssets> movableDetail;
    public List<KccImovableAssets> imovableDetails;
    private String movabless;
    private String number;
    private String presentValue;
    private String imovabless;
    private String inumbers;
    private String ipresentValues;
    private String remarks;
    private String acctType;
    private String occupation;
    private String nameTital;
    private String txtName;
    private String txtAddress;
    private String ddVillage;
    private String txtState;
    private String phoneNo;
    Date calDob;
    private String fathersName;
    private String roi;
    private String sancAmount;
    Date sancDt;
    Date acOpenDt;
    private String education;
    private String txtRabiLimit;
    private String txtKharifLimit;
    private String txtAgriInc;
    private String txtOthInc;
    private String cmbFarmer;
    private String cmbPriSec1;
    private String cmbPriSec2;
    private String cmbColSec1;
    private String cmbColSec2;
    private List<SelectItem> educationListOption;
    private List<SelectItem> titleOption;
    private List<SelectItem> nameOfInsOption;
    private List<SelectItem> movableOption;
    private List<SelectItem> imovableOption;
    private List<SelectItem> farmerOption;
    FdDdsAccountOpeningFacadeRemote fdDdsAccountOpeningFacadeRemote;
    AccountOpeningFacadeRemote acOpenFacadeRemote;

    public List<SelectItem> getFarmerOption() {
        return farmerOption;
    }

    public void setFarmerOption(List<SelectItem> farmerOption) {
        this.farmerOption = farmerOption;
    }

    public List<SelectItem> getImovableOption() {
        return imovableOption;
    }

    public void setImovableOption(List<SelectItem> imovableOption) {
        this.imovableOption = imovableOption;
    }

    public List<SelectItem> getMovableOption() {
        return movableOption;
    }

    public void setMovableOption(List<SelectItem> movableOption) {
        this.movableOption = movableOption;
    }

    public List<SelectItem> getNameOfInsOption() {
        return nameOfInsOption;
    }

    public void setNameOfInsOption(List<SelectItem> nameOfInsOption) {
        this.nameOfInsOption = nameOfInsOption;
    }

    public List<SelectItem> getTitleOption() {
        return titleOption;
    }

    public void setTitleOption(List<SelectItem> titleOption) {
        this.titleOption = titleOption;
    }

    public List<SelectItem> getEducationListOption() {
        return educationListOption;
    }

    public void setEducationListOption(List<SelectItem> educationListOption) {
        this.educationListOption = educationListOption;
    }

    public String getCmbColSec1() {
        return cmbColSec1;
    }

    public void setCmbColSec1(String cmbColSec1) {
        this.cmbColSec1 = cmbColSec1;
    }

    public String getCmbColSec2() {
        return cmbColSec2;
    }

    public void setCmbColSec2(String cmbColSec2) {
        this.cmbColSec2 = cmbColSec2;
    }

    public String getCmbPriSec1() {
        return cmbPriSec1;
    }

    public void setCmbPriSec1(String cmbPriSec1) {
        this.cmbPriSec1 = cmbPriSec1;
    }

    public String getCmbPriSec2() {
        return cmbPriSec2;
    }

    public void setCmbPriSec2(String cmbPriSec2) {
        this.cmbPriSec2 = cmbPriSec2;
    }

    public String getCmbFarmer() {
        return cmbFarmer;
    }

    public void setCmbFarmer(String cmbFarmer) {
        this.cmbFarmer = cmbFarmer;
    }

    public String getTxtOthInc() {
        return txtOthInc;
    }

    public void setTxtOthInc(String txtOthInc) {
        this.txtOthInc = txtOthInc;
    }

    public String getTxtAgriInc() {
        return txtAgriInc;
    }

    public void setTxtAgriInc(String txtAgriInc) {
        this.txtAgriInc = txtAgriInc;
    }

    public String getTxtKharifLimit() {
        return txtKharifLimit;
    }

    public void setTxtKharifLimit(String txtKharifLimit) {
        this.txtKharifLimit = txtKharifLimit;
    }

    public String getTxtRabiLimit() {
        return txtRabiLimit;
    }

    public void setTxtRabiLimit(String txtRabiLimit) {
        this.txtRabiLimit = txtRabiLimit;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getAcOpenDt() {
        return acOpenDt;
    }

    public void setAcOpenDt(Date acOpenDt) {
        this.acOpenDt = acOpenDt;
    }

    public Date getSancDt() {
        return sancDt;
    }

    public void setSancDt(Date sancDt) {
        this.sancDt = sancDt;
    }

    public String getSancAmount() {
        return sancAmount;
    }

    public void setSancAmount(String sancAmount) {
        this.sancAmount = sancAmount;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public Date getCalDob() {
        return calDob;
    }

    public void setCalDob(Date calDob) {
        this.calDob = calDob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTxtState() {
        return txtState;
    }

    public void setTxtState(String txtState) {
        this.txtState = txtState;
    }

    public String getDdVillage() {
        return ddVillage;
    }

    public void setDdVillage(String ddVillage) {
        this.ddVillage = ddVillage;
    }

    public String getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(String txtAddress) {
        this.txtAddress = txtAddress;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getNameTital() {
        return nameTital;
    }

    public void setNameTital(String nameTital) {
        this.nameTital = nameTital;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImovabless() {
        return imovabless;
    }

    public void setImovabless(String imovabless) {
        this.imovabless = imovabless;
    }

    public String getInumbers() {
        return inumbers;
    }

    public void setInumbers(String inumbers) {
        this.inumbers = inumbers;
    }

    public String getIpresentValues() {
        return ipresentValues;
    }

    public void setIpresentValues(String ipresentValues) {
        this.ipresentValues = ipresentValues;
    }

    public String getMovabless() {
        return movabless;
    }

    public void setMovabless(String movabless) {
        this.movabless = movabless;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPresentValue() {
        return presentValue;
    }

    public void setPresentValue(String presentValue) {
        this.presentValue = presentValue;
    }

    public List<KccImovableAssets> getImovableDetails() {
        return imovableDetails;
    }

    public void setImovableDetails(List<KccImovableAssets> imovableDetails) {
        this.imovableDetails = imovableDetails;
    }

    public List<KccMovableAssets> getMovableDetail() {
        return movableDetail;
    }

    public void setMovableDetail(List<KccMovableAssets> movableDetail) {
        this.movableDetail = movableDetail;
    }

    public List<KccParticularHoldingTable> getLibilitiesDetails() {
        return libilitiesDetails;
    }

    public void setLibilitiesDetails(List<KccParticularHoldingTable> libilitiesDetails) {
        this.libilitiesDetails = libilitiesDetails;
    }

    public String getInstallAmts() {
        return installAmts;
    }

    public void setInstallAmts(String installAmts) {
        this.installAmts = installAmts;
    }

    public String getInstitutions() {
        return institutions;
    }

    public void setInstitutions(String institutions) {
        this.institutions = institutions;
    }

    public String getOsAmounts() {
        return osAmounts;
    }

    public void setOsAmounts(String osAmounts) {
        this.osAmounts = osAmounts;
    }

    public String getOverDues() {
        return overDues;
    }

    public void setOverDues(String overDues) {
        this.overDues = overDues;
    }

    public String getPurposes() {
        return purposes;
    }

    public void setPurposes(String purposes) {
        this.purposes = purposes;
    }

    public String getSecuritys() {
        return securitys;
    }

    public void setSecuritys(String securitys) {
        this.securitys = securitys;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getAreaAcress() {
        return areaAcress;
    }

    public void setAreaAcress(String areaAcress) {
        this.areaAcress = areaAcress;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getCroppers() {
        return croppers;
    }

    public void setCroppers(String croppers) {
        this.croppers = croppers;
    }

    
    public String getIncumbrances() {
        return incumbrances;
    }

    public void setIncumbrances(String incumbrances) {
        this.incumbrances = incumbrances;
    }

    public String getIrrigateds() {
        return irrigateds;
    }

    public void setIrrigateds(String irrigateds) {
        this.irrigateds = irrigateds;
    }

    public String getLeaseds() {
        return leaseds;
    }

    public void setLeaseds(String leaseds) {
        this.leaseds = leaseds;
    }

    public String getOwnedLands() {
        return ownedLands;
    }

    public void setOwnedLands(String ownedLands) {
        this.ownedLands = ownedLands;
    }

    public String getSourceOfIrrigations() {
        return sourceOfIrrigations;
    }

    public void setSourceOfIrrigations(String sourceOfIrrigations) {
        this.sourceOfIrrigations = sourceOfIrrigations;
    }

    public String getVillages() {
        return villages;
    }

    public void setVillages(String villages) {
        this.villages = villages;
    }

    public List<KccLandHoldingTable> getLandHoldingDetail() {
        return landHoldingDetail;
    }

    public void setLandHoldingDetail(List<KccLandHoldingTable> landHoldingDetail) {
        this.landHoldingDetail = landHoldingDetail;
    }

    public List<SelectItem> getVillageOption() {
        return villageOption;
    }

    public void setVillageOption(List<SelectItem> villageOption) {
        this.villageOption = villageOption;
    }

    public List<SelectItem> getSecurityPraposalOption() {
        return securityPraposalOption;
    }

    public void setSecurityPraposalOption(List<SelectItem> securityPraposalOption) {
        this.securityPraposalOption = securityPraposalOption;
    }

    public List<SelectItem> getOperationModeOption() {
        return operationModeOption;
    }

    public void setOperationModeOption(List<SelectItem> operationModeOption) {
        this.operationModeOption = operationModeOption;
    }

    public List<SelectItem> getOccupationOption() {
        return occupationOption;
    }

    public void setOccupationOption(List<SelectItem> occupationOption) {
        this.occupationOption = occupationOption;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** Creates a new instance of KCCAccountOpen */
    public KCCAccountOpen() {
        try {
            fdDdsAccountOpeningFacadeRemote=(FdDdsAccountOpeningFacadeRemote)ServiceLocator.getInstance().lookup("FdDdsAccountOpeningFacade");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setMessage("");
            setAcOpenDt(new Date());
            setSancDt(new Date());
            setCalDob(new Date());
            educationListOption = new ArrayList<SelectItem>();
            educationListOption.add(new SelectItem("0", "UP TO HSC"));
            educationListOption.add(new SelectItem("1", "GRADUATE"));
            educationListOption.add(new SelectItem("2", "POST-GRADUATE"));
            educationListOption.add(new SelectItem("3", "PROFESSIONAL"));
            educationListOption.add(new SelectItem("4", "OTHERS"));
            educationListOption.add(new SelectItem("5", "N/A"));
            titleOption = new ArrayList<SelectItem>();
            titleOption.add(new SelectItem("0", "Mr."));
            titleOption.add(new SelectItem("1", "Mrs."));
            titleOption.add(new SelectItem("2", "Master"));
            titleOption.add(new SelectItem("3", "Kumari"));
            titleOption.add(new SelectItem("4", "M/s"));
            titleOption.add(new SelectItem("5", "Miss"));
            nameOfInsOption = new ArrayList<SelectItem>();
            nameOfInsOption.add(new SelectItem("0", "Our Bank"));
            nameOfInsOption.add(new SelectItem("1", "Other Banks"));
            nameOfInsOption.add(new SelectItem("2", "Co-Op Credit Soc."));
            nameOfInsOption.add(new SelectItem("3", "Land Dev.Bank"));
            nameOfInsOption.add(new SelectItem("4", "Other Creditors"));
            nameOfInsOption.add(new SelectItem("5", "N/A"));
            movableOption = new ArrayList<SelectItem>();
            movableOption.add(new SelectItem("0", "Plough Cattle"));
            movableOption.add(new SelectItem("1", "Milch Cattle"));
            movableOption.add(new SelectItem("2", "Poultry Birds"));
            movableOption.add(new SelectItem("3", "Oil Engine / Ele.Motor /Pumpsets"));
            movableOption.add(new SelectItem("4", "Power Tiller"));
            movableOption.add(new SelectItem("5", "Tractors"));
            movableOption.add(new SelectItem("6", "Transport Vechiles"));
            movableOption.add(new SelectItem("7", "Other Implement"));
            movableOption.add(new SelectItem("8", "N/A"));
            imovableOption = new ArrayList<SelectItem>();
            imovableOption.add(new SelectItem("0", "Particulars of Property"));
            imovableOption.add(new SelectItem("1", "House/Bulding"));
            imovableOption.add(new SelectItem("2", "Tractor Shed/Farm Shed"));
            imovableOption.add(new SelectItem("3", "Fishing Ponds/Tank"));
            imovableOption.add(new SelectItem("4", "N/A"));
            farmerOption = new ArrayList<SelectItem>();
            farmerOption.add(new SelectItem("0", "Medium"));
            farmerOption.add(new SelectItem("1", "Small"));
            farmerOption.add(new SelectItem("2", "Large"));
            movableDetail = new ArrayList<KccMovableAssets>();
            imovableDetails = new ArrayList<KccImovableAssets>();
            libilitiesDetails = new ArrayList<KccParticularHoldingTable>();
            landHoldingDetail = new ArrayList<KccLandHoldingTable>();
            acctTypeOption = new ArrayList<SelectItem>();
            occupationOption = new ArrayList<SelectItem>();
            operationModeOption = new ArrayList<SelectItem>();
            securityPraposalOption = new ArrayList<SelectItem>();
            villageOption = new ArrayList<SelectItem>();
            getAccountTypeData();

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

    public void getAccountTypeData() {
        try {
            List acnoDataList = new ArrayList();
            List occupationList = new ArrayList();
            List operationModeList = new ArrayList();
            List securityList = new ArrayList();
            List villageList = new ArrayList();
            acnoDataList = fdDdsAccountOpeningFacadeRemote.getAccountTypeNatureByKC();
            occupationList = fdDdsAccountOpeningFacadeRemote.getOccupationDetails();
            operationModeList = fdDdsAccountOpeningFacadeRemote.getOperationMode();
            securityList = fdDdsAccountOpeningFacadeRemote.getSecurity();
            villageList = fdDdsAccountOpeningFacadeRemote.getbsrVillage();

            if (!acnoDataList.isEmpty()) {
                for (int i = 0; i < acnoDataList.size(); i++) {
                    Vector ele = (Vector) acnoDataList.get(i);
                    for (Object ee : ele) {
                        acctTypeOption.add(new SelectItem(ee.toString(), ee.toString()));
                    }
                }
            }
            if (!occupationList.isEmpty()) {
                for (int j = 0; j < occupationList.size(); j++) {
                    Vector ele1 = (Vector) occupationList.get(j);
                    for (Object ee1 : ele1) {
                        occupationOption.add(new SelectItem(ee1.toString(), ee1.toString()));
                    }
                }
            } else {
                this.setMessage("No Record Exist In CodeBook");
                return;
            }
            if (!operationModeList.isEmpty()) {
                for (int k = 0; k < operationModeList.size(); k++) {
                    Vector ele2 = (Vector) operationModeList.get(k);
                    for (Object ee2 : ele2) {
                        operationModeOption.add(new SelectItem(ee2.toString(), ee2.toString()));
                    }
                }
            } else {
                this.setMessage("No Record Exist In CodeBook");
                return;
            }
            if (!securityList.isEmpty()) {
                for (int l = 0; l < securityList.size(); l++) {
                    Vector ele3 = (Vector) securityList.get(l);
                    for (Object ee3 : ele3) {
                        securityPraposalOption.add(new SelectItem(ee3.toString(), ee3.toString()));
                    }
                }
            } else {
                this.setMessage("No Record Exist In Loan CodeBook");
                return;
            }
            if (!villageList.isEmpty()) {
                for (int m = 0; m < villageList.size(); m++) {
                    Vector ele4 = (Vector) villageList.get(m);
                    for (Object ee4 : ele4) {
                        villageOption.add(new SelectItem(ee4.toString(), ee4.toString()));
                    }
                }
            } else {
                this.setMessage("Please Fill BSR Details");
                return;
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void loadGrid() {
        try {
            this.setMessage("");
            Pattern match = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.areaAcress.equalsIgnoreCase("") || this.areaAcress.length() == 0 || (areaAcress == null) || (areaAcress.equals("0"))) {
                this.setMessage("Please Fill Area In Acres and It Cannot Be Zero");
                return;
            }
            Matcher areaAcressCheck = match.matcher(areaAcress);
            if (!areaAcressCheck.matches()) {
                this.setMessage("Please Enter Valid Area In Acres");
                return;
            }
            if (this.ownedLands.equalsIgnoreCase("") || this.ownedLands.length() == 0 || (ownedLands == null) || (ownedLands.equals("0"))) {
                this.setMessage("Please Fill Owned Land Area and It Cannot Be Zero");
                return;
            }
            Matcher ownedLandsCheck = match.matcher(ownedLands);
            if (!ownedLandsCheck.matches()) {
                this.setMessage("Please Enter Valid Owned Land Area");
                return;
            }
            if (this.leaseds.equalsIgnoreCase("") || this.leaseds.length() == 0 || (leaseds == null) || (leaseds.equals("0"))) {
                this.setMessage("Please Fill Leased");
                return;
            }
            Matcher leasedsCheck = match.matcher(leaseds);
            if (!leasedsCheck.matches()) {
                this.setMessage("Please Enter Valid Leased");
                return;
            }
            if (this.croppers.equalsIgnoreCase("") || this.croppers.length() == 0 || (croppers == null) || (croppers.equals("0"))) {
                this.setMessage("Please Fill Cropper");
                return;
            }
            Matcher cropperCheck = match.matcher(croppers);
            if (!cropperCheck.matches()) {
                this.setMessage("Please Enter Valid Cropper");
                return;
            }
            if (this.irrigateds.equalsIgnoreCase("") || this.irrigateds.length() == 0 || (irrigateds == null) || (irrigateds.equals("0"))) {
                this.setMessage("Please Fill Irrigated Land Area and It Cannot Be Zero");
                return;
            }
            Matcher irrigatedsCheck = match.matcher(irrigateds);
            if (!irrigatedsCheck.matches()) {
                this.setMessage("Please Enter Valid Irrigated Land Area");
                return;
            }
            if ((Float.parseFloat(irrigateds)) > (Float.parseFloat(areaAcress))) {
                this.setMessage("Irrigated Area Cannot Be Greater Than Area In Acres");
                return;
            }
            KccLandHoldingTable txnBean = createTableObj();
            landHoldingDetail.add(txnBean);
            this.setVillages("");
            this.setAreaAcress("");
            this.setBlockNumber("");
            this.setOwnedLands("");
            this.setLeaseds("");
            this.setCroppers("");
            this.setIrrigateds("");
            this.setSourceOfIrrigations("");
            this.setIncumbrances("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    private KccLandHoldingTable createTableObj() {
        KccLandHoldingTable holding = new KccLandHoldingTable();
        try {
            int as = landHoldingDetail.size();
            as = as + 1;
            holding.setSeqNo(String.valueOf(as));
            holding.setVillage(villages);
            holding.setAreaAcres(areaAcress);
            holding.setBlockNo(blockNumber);
            holding.setOwnedLand(ownedLands);
            holding.setLeased(leaseds);
            holding.setCropper(croppers);
            holding.setIrrigated(irrigateds);
            holding.setSourceOfIrrigation(sourceOfIrrigations);
            holding.setIncumbrance(incumbrances);
            return holding;
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return holding;
    }

    public void delete() {
        this.setMessage("");
        try {
            landHoldingDetail.remove(currentRow);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void loadGridExistingLibilities() {
        try {
            this.setMessage("");
            Pattern match = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.purposes.equalsIgnoreCase("") || this.purposes.length() == 0 || (purposes == null)) {
                this.setMessage("Please Fill The Purpose Of Loan");
                return;
            }
            if (this.osAmounts.equalsIgnoreCase("") || this.osAmounts.length() == 0 || (osAmounts == null) || (osAmounts.equals("0"))) {
                this.setMessage("Please Fill The OS Amount");
                return;
            }
            Matcher osAmountsCheck = match.matcher(osAmounts);
            if (!osAmountsCheck.matches()) {
                this.setMessage("Please Enter Valid OS Amount");
                return;
            }
            KccParticularHoldingTable txnBean = createLibilitiesTableObj();
            libilitiesDetails.add(txnBean);
            this.setInstitutions("");
            this.setPurposes("");
            this.setOsAmounts("");
            this.setOverDues("");
            this.setSecuritys("");
            this.setInstallAmts("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    private KccParticularHoldingTable createLibilitiesTableObj() {
        KccParticularHoldingTable libilitie = new KccParticularHoldingTable();
        try {

            int libi = libilitiesDetails.size();
            libi = libi + 1;
            libilitie.setSequenceNo(String.valueOf(libi));
            if (institutions.equals("0")) {
                institutions = "Our Bank";
            } else if (institutions.equals("1")) {
                institutions = "Other Banks";
            } else if (institutions.equals("2")) {
                institutions = "Co-Op Credit Soc.";
            } else if (institutions.equals("3")) {
                institutions = "Land Dev.Bank";
            } else if (institutions.equals("4")) {
                institutions = "Other Creditors";
            } else if (institutions.equals("5")) {
                institutions = "N/A";
            }
            libilitie.setInstitution(institutions);
            libilitie.setPurpose(purposes);
            libilitie.setOsAmount(osAmounts);
            libilitie.setOverDue(overDues);
            libilitie.setSecurity(securitys);
            libilitie.setInstallAmt(installAmts);
            return libilitie;
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return libilitie;
    }

    public void libilitiesDelete() {
        this.setMessage("");
        try {
            libilitiesDetails.remove(currentRow);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void loadGridMovable() {
        try {
            this.setMessage("");
            Pattern match = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.number.equalsIgnoreCase("") || this.number.length() == 0 || (number == null) || (number.equals("0"))) {
                this.setMessage("Please Fill The No. Of Movable Assets");
                return;
            }
            Matcher numberCheck = match.matcher(number);
            if (!numberCheck.matches()) {
                this.setMessage("Please Enter Valid No. Of Movable Assets");
                return;
            }
            if (this.presentValue.equalsIgnoreCase("") || this.presentValue.length() == 0 || (presentValue == null) || (presentValue.equals("0"))) {
                this.setMessage("Please Fill The Present Value");
                return;
            }
            Matcher presentValueCheck = match.matcher(presentValue);
            if (!presentValueCheck.matches()) {
                this.setMessage("Please Enter Valid Present Value");
                return;
            }
            KccMovableAssets movableBean = createMovableTableObj();
            movableDetail.add(movableBean);
            this.setNumber("");
            this.setPresentValue("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    private KccMovableAssets createMovableTableObj() {
        KccMovableAssets movables = new KccMovableAssets();
        try {

            int mov = movableDetail.size();
            mov = mov + 1;
            movables.setSeqNo(String.valueOf(mov));
            if (movabless.equals("0")) {
                movabless = "Plough Cattle";
            } else if (movabless.equals("1")) {
                movabless = "Milch Cattle";
            } else if (movabless.equals("2")) {
                movabless = "Poultry Birds";
            } else if (movabless.equals("3")) {
                movabless = "Oil Engine / Ele.Motor /Pumpsets";
            } else if (movabless.equals("4")) {
                movabless = "Power Tiller";
            } else if (movabless.equals("5")) {
                movabless = "Tractors";
            } else if (movabless.equals("6")) {
                movabless = "Transport Vechiles";
            } else if (movabless.equals("7")) {
                movabless = "Other Implement";
            } else if (movabless.equals("8")) {
                movabless = "N/A";
            }
            movables.setParticulars(movabless);
            movables.setNumber(number);
            movables.setPresentValue(presentValue);
            return movables;
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return movables;
    }

    public void movableDelete() {
        this.setMessage("");
        try {
            movableDetail.remove(currentRow);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void loadGridImovable() {
        try {
            this.setMessage("");
            Pattern match = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.inumbers.equalsIgnoreCase("") || this.inumbers.length() == 0 || (inumbers == null) || (inumbers.equals("0"))) {
                this.setMessage("Please Fill The No. Of IMMovable Assets");
                return;
            }
            Matcher inumbersCheck = match.matcher(inumbers);
            if (!inumbersCheck.matches()) {
                this.setMessage("Please Enter Valid No. Of IMMovable Assets");
                return;
            }
            if (this.ipresentValues.equalsIgnoreCase("") || this.ipresentValues.length() == 0 || (ipresentValues == null) || (ipresentValues.equals("0"))) {
                this.setMessage("Please Fill The Present Value");
                return;
            }
            Matcher ipresentValuesCheck = match.matcher(ipresentValues);
            if (!ipresentValuesCheck.matches()) {
                this.setMessage("Please Enter Valid Present Value");
                return;
            }
            KccImovableAssets imovableBean = createImovableTableObj();
            imovableDetails.add(imovableBean);
            this.setInumbers("");
            this.setIpresentValues("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    private KccImovableAssets createImovableTableObj() {
        KccImovableAssets imovables = new KccImovableAssets();
        try {

            int imov = imovableDetails.size();
            imov = imov + 1;
            imovables.setImSeqNo(String.valueOf(imov));
            if (imovabless.equals("0")) {
                imovabless = "Particulars of Property";
            } else if (imovabless.equals("1")) {
                imovabless = "House/Bulding";
            } else if (imovabless.equals("2")) {
                imovabless = "Tractor Shed/Farm Shed";
            } else if (imovabless.equals("3")) {
                imovabless = "Fishing Ponds/Tank";
            } else if (imovabless.equals("4")) {
                imovabless = "N/A";
            }
            imovables.setImParticulars(imovabless);
            imovables.setImNumber(inumbers);
            imovables.setImPresentValue(ipresentValues);
            return imovables;
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return imovables;
    }

    public void imovableDelete() {
        this.setMessage("");
        try {
            imovableDetails.remove(currentRow);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public String onblurSancDate() {
        this.setMessage("");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        String str = "";
        try {
            
            List dateDif = acOpenFacadeRemote.dateDiffStatementFreqDate(sd.format(this.sancDt));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessage("Sanction Date CanNot Be Greater Than Current Date");
                return str = "Sanction Date CanNot Be Greater Than Current Date";
            } else {
                this.setMessage("");
                return str = "true";
            }

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return str;
    }

    public String onblurDateOfBirth() {
        this.setMessage("");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        String str = "";
        try {
            List dateDif = acOpenFacadeRemote.dateDiffStatementFreqDate(sd.format(this.calDob));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) <= 0) {
                this.setMessage("Date OF Birth CanNot Be Greater Than or Equal To Current Date");
                return str = "Date OF Birth CanNot Be Greater Than or Equal To Current Date";
            } else {
                this.setMessage("");
                return str = "true";
            }

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return str;
    }

    public void saveData() {
        try {
            this.setMessage("");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String dateOfBirth = ymd.format(this.calDob);
            String sancDob = ymd.format(this.sancDt);
            String accountOpenDt = ymd.format(this.acOpenDt);
            Pattern match = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if ((txtName == null) || (txtName.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Customer Name");
                return;
            }
            if ((fathersName == null) || (fathersName.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Father Name");
                return;
            }
            if ((txtAddress == null) || (txtAddress.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Address Name");
                return;
            }
            if ((txtState == null) || (txtState.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter State");
                return;
            }
            if (calDob == null) {
                this.setMessage("Please Enter Date Of Birth");
                return;
            }
            String dateOfBirths = onblurDateOfBirth();
            if (!dateOfBirths.equalsIgnoreCase("true")) {
                this.setMessage(dateOfBirths);
                return;
            } else {
                this.setMessage("");
            }
            Matcher phoneNoCheck = match.matcher(phoneNo);
            if (!phoneNoCheck.matches()) {
                this.setMessage("Please Enter Valid Phone No");
                return;
            }
            if (this.sancAmount.equalsIgnoreCase("") || this.sancAmount.length() == 0) {
                this.setMessage("Please Enter Sanction Amount");
                return;
            }
            Matcher sancAmountCheck = match.matcher(sancAmount);
            if (!sancAmountCheck.matches()) {
                this.setMessage("Please Enter Valid Sanction Amount");
                return;
            }
            if (sancDt == null) {
                this.setMessage("Please Enter Sanc Date");
                return;
            }
            String sanDt = onblurSancDate();
            if (!sanDt.equalsIgnoreCase("true")) {
                this.setMessage(sanDt);
                return;
            } else {
                this.setMessage("");
            }
            if (this.txtRabiLimit.equalsIgnoreCase("") || this.txtRabiLimit.length() == 0) {
                this.setMessage("Please Enter Rabi Limit");
                return;
            }
            Matcher txtRabiLimitCheck = match.matcher(txtRabiLimit);
            if (!txtRabiLimitCheck.matches()) {
                this.setMessage("Please Enter Valid Rabi Limit");
                return;
            }
            if (this.txtKharifLimit.equalsIgnoreCase("") || this.txtKharifLimit.length() == 0) {
                this.setMessage("Please Enter Kharif Limit");
                return;
            }
            Matcher txtKharifLimitCheck = match.matcher(txtKharifLimit);
            if (!txtKharifLimitCheck.matches()) {
                this.setMessage("Please Enter Valid Kharif Limit");
                return;
            }
            if (this.roi.equalsIgnoreCase("") || this.roi.length() == 0) {
                this.setMessage("Please Enter ROI");
                return;
            }
            Matcher roiCheck = match.matcher(roi);
            if (!roiCheck.matches()) {
                this.setMessage("Please Enter Valid ROI");
                return;
            }
            if (this.txtAgriInc.equalsIgnoreCase("") || this.txtAgriInc.length() == 0) {
                this.setMessage("Please Enter Agriculture Income");
                return;
            }
            Pattern match1 = Pattern.compile("((-|\\.+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher txtAgriIncCheck = match1.matcher(txtAgriInc);
            if (!txtAgriIncCheck.matches()) {
                this.setMessage("Please Enter Valid Agriculture Income");
                return;
            }
            if (this.txtOthInc.equalsIgnoreCase("") || this.txtOthInc.length() == 0) {
                this.setMessage("Please Enter Other Income");
                return;
            }
            Matcher txtOthIncCheck = match1.matcher(txtAgriInc);
            if (!txtOthIncCheck.matches()) {
                this.setMessage("Please Enter Valid Agriculture Income");
                return;
            }
            if (Float.parseFloat(sancAmount) < (Float.parseFloat(txtRabiLimit) + (Float.parseFloat(txtKharifLimit)))) {
                this.setMessage("Sanction Limit CanNot Be Less Than Sum OF Rabi And Kharif Limit");
                return;
            }

            List holdingResultList = new ArrayList();
            String a[][] = new String[landHoldingDetail.size()][10];
            for (int i = 0; i < landHoldingDetail.size(); i++) {
                a[i][0] = landHoldingDetail.get(i).getSeqNo().toString();
                a[i][1] = landHoldingDetail.get(i).getVillage().toString();
                a[i][2] = landHoldingDetail.get(i).getAreaAcres().toString();
                a[i][3] = landHoldingDetail.get(i).getBlockNo().toString();
                a[i][4] = landHoldingDetail.get(i).getOwnedLand().toString();
                a[i][5] = landHoldingDetail.get(i).getLeased().toString();
                a[i][6] = landHoldingDetail.get(i).getCropper().toString();
                a[i][7] = landHoldingDetail.get(i).getIrrigated().toString();
                a[i][8] = landHoldingDetail.get(i).getSourceOfIrrigation().toString();
                a[i][9] = landHoldingDetail.get(i).getIncumbrance().toString();
            }
            for (int i = 0; i < landHoldingDetail.size(); i++) {
                for (int j = 0; j < 10; j++) {
                    Object landHoldingArray = a[i][j];
                    holdingResultList.add(landHoldingArray);
                }
            }
            if (holdingResultList.size() > 20) {
            } else {
                this.setMessage("Please Fill The Land Details");
                return;
            }
            if (this.remarks.equalsIgnoreCase("") || this.remarks.length() == 0) {
                this.setMessage("Please Enter Remarks In Liabilities Information");
                return;
            }
            List mhLiabresultList = new ArrayList();
            String b[][] = new String[libilitiesDetails.size()][7];
            for (int k = 0; k < libilitiesDetails.size(); k++) {
                b[k][0] = libilitiesDetails.get(k).getSequenceNo().toString();
                b[k][1] = libilitiesDetails.get(k).getInstitution().toString();
                b[k][2] = libilitiesDetails.get(k).getPurpose().toString();
                b[k][3] = libilitiesDetails.get(k).getOsAmount().toString();
                b[k][4] = libilitiesDetails.get(k).getOverDue().toString();
                b[k][5] = libilitiesDetails.get(k).getSecurity().toString();
                b[k][6] = libilitiesDetails.get(k).getInstallAmt().toString();
            }
            for (int k = 0; k < libilitiesDetails.size(); k++) {
                for (int z = 0; z < 7; z++) {
                    Object libilitiesArray = b[k][z];
                    mhLiabresultList.add(libilitiesArray);
                }
            }
            List movableResultList = new ArrayList();
            String c[][] = new String[movableDetail.size()][4];
            for (int m = 0; m < movableDetail.size(); m++) {
                c[m][0] = movableDetail.get(m).getSeqNo().toString();
                c[m][1] = movableDetail.get(m).getParticulars().toString();
                c[m][2] = movableDetail.get(m).getNumber().toString();
                c[m][3] = movableDetail.get(m).getPresentValue().toString();
            }
            for (int m = 0; m < movableDetail.size(); m++) {
                for (int n = 0; n < 4; n++) {
                    Object movableArray = c[m][n];
                    movableResultList.add(movableArray);
                }
            }
            List imovablesResultList = new ArrayList();
            String d[][] = new String[imovableDetails.size()][4];
            for (int p = 0; p < imovableDetails.size(); p++) {
                d[p][0] = imovableDetails.get(p).getImSeqNo().toString();
                d[p][1] = imovableDetails.get(p).getImParticulars().toString();
                d[p][2] = imovableDetails.get(p).getImNumber().toString();
                d[p][3] = imovableDetails.get(p).getImPresentValue().toString();
            }
            for (int p = 0; p < imovableDetails.size(); p++) {
                for (int q = 0; q < 4; q++) {
                    Object imovableArray = d[p][q];
                    imovablesResultList.add(imovableArray);
                }
            }
           
            String acResult = fdDdsAccountOpeningFacadeRemote.saveData(remarks, acctType, occupation, nameTital, txtName, txtAddress, ddVillage, txtState, phoneNo, dateOfBirth, fathersName, roi, sancAmount, sancDob, accountOpenDt, education, txtRabiLimit, txtKharifLimit, txtAgriInc, txtOthInc, cmbFarmer, cmbPriSec1, cmbPriSec2, cmbColSec1, cmbColSec2, holdingResultList, mhLiabresultList, movableResultList, imovablesResultList, getUserName(), getOrgnBrCode());
            this.setMessage(acResult);
            resetAll();
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());

        }
    }

    public void resetAll() {
        try {
            Date date = new Date();
            this.setTxtName("");
            this.setFathersName("");
            this.setTxtAddress("");
            this.setTxtState("");
            this.setPhoneNo("");
            this.setSancAmount("");
            this.setTxtRabiLimit("");
            this.setTxtKharifLimit("");
            this.setRoi("");
            this.setTxtAgriInc("");
            this.setTxtOthInc("");
            this.setSancDt(date);
            this.setCalDob(date);

            this.setAreaAcress("");
            this.setBlockNumber("");
            this.setOwnedLands("");
            this.setLeaseds("");
            this.setCroppers("");
            this.setIrrigateds("");
            this.setSourceOfIrrigations("");
            this.setIncumbrances("");
            this.setPurposes("");
            this.setOsAmounts("");
            this.setOverDues("");
            this.setSecuritys("");
            this.setInstallAmts("");
            this.setNumber("");
            this.setPresentValue("");
            this.setInumbers("");
            this.setIpresentValues("");
            movableDetail = new ArrayList<KccMovableAssets>();
            imovableDetails = new ArrayList<KccImovableAssets>();
            libilitiesDetails = new ArrayList<KccParticularHoldingTable>();
            landHoldingDetail = new ArrayList<KccLandHoldingTable>();
            this.setRemarks("");

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void refreshAll() {
        try {
            this.setMessage("");
            resetAll();
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public String exitForm() {
        resetAll();
        this.setMessage("");
        return "case1";
    }
}
