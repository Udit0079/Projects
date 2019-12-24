package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.BranchMasterGridLoad;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;

/**
 *
 * @author Admin
 */
public class BranchMaster extends BaseBean{

    Context ctx;
    BillCommissionFacadeRemote brnMaster;
   // private String orgnBrCode;
    //private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    //private HttpServletRequest req;
    int currentRow;
    private String brCode;
    private String brName;
    private String brAddress;
    private String alphaCode;
    private String city;
    private String pinCode;
    private String regOffice;
    private String state;
    private String ifscCode;
    private String locationType;
     private String mobileNo;
    private String computerStatus;
    private List<SelectItem> locationTypeList;
    private List<SelectItem> computerStatusList;
    private boolean flag;
    private List<BranchMasterGridLoad> gridDetail;
    private BranchMasterGridLoad currentItem = new BranchMasterGridLoad();
    private CertIssueFacadeRemote certRemote;

    public BranchMaster() {
        try {

            Date date = new Date();
            brnMaster = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            certRemote = (CertIssueFacadeRemote) ServiceLocator.getInstance().lookup("CertIssueFacade");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            onLoadData();
            gridLoad();
            this.setFlag(true);
            this.setErrorMessage("");
            this.setMessage("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLoadData() {
        locationTypeList = new ArrayList<SelectItem>();
        computerStatusList = new ArrayList<SelectItem>();
        try {
            /*Setting Computerized Status*/
            computerStatusList.add(new SelectItem("Y", "YES"));
            computerStatusList.add(new SelectItem("N", "NO"));

            /*Setting Location Type*/
            List result = certRemote.getAllRefRecNoData("200");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 200.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                locationTypeList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<BranchMasterGridLoad>();
        try {
            List resultList = new ArrayList();
            resultList = brnMaster.loadGridBranchMaster();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    BranchMasterGridLoad detail = new BranchMasterGridLoad();
                    detail.setBrnCode(ele.get(0).toString());
                    detail.setBrnName(ele.get(1).toString());
                    detail.setAlphaCode(ele.get(2).toString());
                    if (ele.get(3) == null || ele.get(3).toString().equals("")) {
                        detail.setBrnAddress("");
                    } else {
                        detail.setBrnAddress(ele.get(3).toString());
                    }
                    if (ele.get(4) == null || ele.get(4).toString().equals("")) {
                        detail.setCity("");
                    } else {
                        detail.setCity(ele.get(4).toString());
                    }
                    if (ele.get(5) == null || ele.get(5).toString().equals("")) {
                        detail.setState("");
                    } else {
                        detail.setState(ele.get(5).toString());
                    }
                    if (ele.get(6) == null || ele.get(6).toString().equals("")) {
                        detail.setPinCode("");
                    } else {
                        detail.setPinCode(ele.get(6).toString());
                    }
                    detail.setRegOffice(ele.get(7).toString());
                    if (ele.get(8) == null || ele.get(8).toString().equals("")) {
                        detail.setIfscCode("");
                    } else {
                        detail.setIfscCode(ele.get(8).toString());
                    }
                     if (ele.get(9) == null || ele.get(9).toString().equals("")) {
                          detail.setLocationType("");
                     }else{
                          detail.setLocationType(ele.get(9).toString());
                     }
                    if (ele.get(10) == null || ele.get(10).toString().equals("")) {
                          detail.setCompStatus("");
                    }else{
                          detail.setCompStatus(ele.get(10).toString());
                    }
                    if (ele.get(11) == null || ele.get(11).toString().equals("")) {
                          detail.setMobileNo("");
                    }else{
                          detail.setMobileNo(ele.get(11).toString());
                    }
                  
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            //Pattern pm = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Pattern pm = Pattern.compile("[a-zA-z0-9,-/]+([ '-][a-zA-Z0-9,-/]+)*");
            if (this.brCode == null || this.brCode.equalsIgnoreCase("") || this.brCode.length() == 0) {
                this.setErrorMessage("Please enter Branch Code.");
                return;
            }
            Matcher brNo = p.matcher(brCode);
            if (!brNo.matches()) {
                this.setErrorMessage("Please enter valid Branch Code.");
                return;
            }
            if (this.brCode.contains(".")) {
                this.setErrorMessage("Please enter valid Branch Code.");
                return;
            }
            if (this.alphaCode == null || this.alphaCode.equalsIgnoreCase("") || this.alphaCode.length() == 0) {
                this.setErrorMessage("Please enter Alpha Code.");
                return;
            }
            Matcher alphaCodeChk = pm.matcher(alphaCode);
            if (!alphaCodeChk.matches()) {
                this.setErrorMessage("Please enter valid Alpha Code.");
                return;
            }
            if (this.brName == null || this.brName.equalsIgnoreCase("") || this.brName.length() == 0) {
                this.setErrorMessage("Please enter Branch Name.");
                return;
            }
            Matcher brNameChk = pm.matcher(brName);
            if (!brNameChk.matches()) {
                this.setErrorMessage("Please enter valid Branch Name.");
                return;
            }
            if (this.brAddress == null || this.brAddress.equalsIgnoreCase("") || this.brAddress.length() == 0) {
                this.setErrorMessage("Please enter Branch Address.");
                return;
            }
            Matcher brAddressChk = pm.matcher(brAddress);
            if (!brAddressChk.matches()) {
                this.setErrorMessage("Please enter Valid Branch Address.");
                return;
            }
            if (this.city == null || this.city.equalsIgnoreCase("") || this.city.length() == 0) {
                this.setErrorMessage("Please enter City.");
                return;
            }
            Matcher cityChk = pm.matcher(city);
            if (!cityChk.matches()) {
                this.setErrorMessage("Please enter valid City Name.");
                return;
            }
            if (this.state == null || this.state.equalsIgnoreCase("") || this.state.length() == 0) {
                this.setErrorMessage("Please enter State.");
                return;
            }
            Matcher stateChk = pm.matcher(state);
            if (!stateChk.matches()) {
                this.setErrorMessage("Please enter valid State Name.");
                return;
            }
            if (this.pinCode == null || this.pinCode.equalsIgnoreCase("") || this.pinCode.length() == 0) {
                this.setErrorMessage("Please enter Pin Code.");
                return;
            }
            Matcher pinNo = p.matcher(pinCode);
            if (!pinNo.matches()) {
                this.setErrorMessage("Please enter valid Pin Code.");
                return;
            }
            if (this.pinCode.contains(".") || this.pinCode.contains("-")) {
                this.setErrorMessage("Please enter valid Pin Code.");
                return;
            }
            if (this.regOffice.equalsIgnoreCase("") || this.regOffice.length() == 0) {
                this.setErrorMessage("Please enter Regional Office.");
                return;
            }
            Matcher regOfficeChk = pm.matcher(regOffice);
            if (!regOfficeChk.matches()) {
                this.setErrorMessage("Please enter valid Registered Office.");
                return;
            }
            String result = brnMaster.saveDetailBranchMaster(this.brName, this.brAddress, Integer.parseInt(this.brCode),
                    this.alphaCode, this.city, this.state, this.regOffice, Integer.parseInt(this.pinCode), this.ifscCode,
                    this.locationType, this.computerStatus, "", "", "", "19000101","20990131",mobileNo);
            if (result.equals("true")) {
                this.setMessage("Record saved successfully.");
            } else {
                this.setErrorMessage("Record does not save.");
                return;
            }
            flag = true;
            gridLoad();
            resetForm();
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void modifyDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Pattern pm = Pattern.compile("[a-zA-z0-9,-/]+([ '-][a-zA-Z0-9,-/]+)*");
            if (this.brCode == null || this.brCode.equalsIgnoreCase("") || this.brCode.length() == 0) {
                this.setErrorMessage("Please enter Branch Code.");
                return;
            }
            Matcher brNo = p.matcher(brCode);
            if (!brNo.matches()) {
                this.setErrorMessage("Please enter valid Branch Code.");
                return;
            }
            if (this.brCode.contains(".")) {
                this.setErrorMessage("Please enter valid Branch Code.");
                return;
            }
            if (this.alphaCode == null || this.alphaCode.equalsIgnoreCase("") || this.alphaCode.length() == 0) {
                this.setErrorMessage("Please enter Alpha Code.");
                return;
            }
            Matcher alphaCodeChk = pm.matcher(alphaCode);
            if (!alphaCodeChk.matches()) {
                this.setErrorMessage("Please enter valid Alpha Code.");
                return;
            }
            if (this.brName == null || this.brName.equalsIgnoreCase("") || this.brName.length() == 0) {
                this.setErrorMessage("Please enter Branch Name.");
                return;
            }
            Matcher brNameChk = pm.matcher(brName);
            if (!brNameChk.matches()) {
                this.setErrorMessage("Please enter valid Branch Name.");
                return;
            }
            if (this.brAddress == null || this.brAddress.equalsIgnoreCase("") || this.brAddress.length() == 0) {
                this.setErrorMessage("Please enter Branch Address.");
                return;
            }
            Matcher brAddressChk = pm.matcher(brAddress);
            if (!brAddressChk.matches()) {
                this.setErrorMessage("Please enter valid Branch Address.");
                return;
            }
            if (this.city == null || this.city.equalsIgnoreCase("") || this.city.length() == 0) {
                this.setErrorMessage("Please enter City.");
                return;
            }
            Matcher cityChk = pm.matcher(city);
            if (!cityChk.matches()) {
                this.setErrorMessage("Please enter valid City Name.");
                return;
            }
            if (this.state == null || this.state.equalsIgnoreCase("") || this.state.length() == 0) {
                this.setErrorMessage("Please enter State.");
                return;
            }
            Matcher stateChk = pm.matcher(state);
            if (!stateChk.matches()) {
                this.setErrorMessage("Please enter valid State Name.");
                return;
            }
            if (this.pinCode == null || this.pinCode.equalsIgnoreCase("") || this.pinCode.length() == 0) {
                this.setErrorMessage("Please enter Pin Code.");
                return;
            }
            Matcher pinNo = p.matcher(pinCode);
            if (!pinNo.matches()) {
                this.setErrorMessage("Please enter valid Pin Code.");
                return;
            }
            if (this.pinCode.contains(".") || this.pinCode.contains("-")) {
                this.setErrorMessage("Please enter valid Pin Code.");
                return;
            }
            if (this.regOffice.equalsIgnoreCase("") || this.regOffice.length() == 0) {
                this.setErrorMessage("Please enter Regional Office.");
                return;
            }
            Matcher regOfficeChk = pm.matcher(regOffice);
            if (!regOfficeChk.matches()) {
                this.setErrorMessage("Please enter valid Registered Office.");
                return;
            }
            String result = brnMaster.modifyDetailBranchMaster(this.brName, this.brAddress, Integer.parseInt(this.brCode),
                    this.alphaCode, this.city, this.state, this.regOffice, Integer.parseInt(this.pinCode), this.ifscCode,
                    this.locationType, this.computerStatus,this.mobileNo);
            if (result.equals("true")) {
                this.setMessage("Record modified successfully.");
            } else {
                this.setErrorMessage("Record does not modify.");
                return;
            }
            gridLoad();
            resetForm();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fillValuesofGridInFields() {
        flag = false;
        this.setErrorMessage("");
        this.setMessage("");
        this.setBrCode(currentItem.getBrnCode());
        this.setBrName(currentItem.getBrnName());
        this.setBrAddress(currentItem.getBrnAddress());
        this.setAlphaCode(currentItem.getAlphaCode());
        this.setPinCode(currentItem.getPinCode());
        this.setCity(currentItem.getCity());
        this.setRegOffice(currentItem.getRegOffice());
        this.setState(currentItem.getState());
        this.setIfscCode(currentItem.getIfscCode());
        this.setLocationType(currentItem.getLocationType());
        this.setComputerStatus(currentItem.getCompStatus());
        this.setMobileNo(currentItem.getMobileNo());
    }

    public void fetchCurrentRow(ActionEvent event) {
        String branchCode = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("brnCode"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (BranchMasterGridLoad item : gridDetail) {
            if (item.getBrnCode().equalsIgnoreCase(branchCode)) {
                currentItem = item;
            }
        }
    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = brnMaster.deleteRecordBranchMaster(Integer.parseInt(currentItem.getBrnCode()));
            if (result.equals("true")) {
                this.setMessage("Record deleted successfully.");
            } else {
                this.setErrorMessage("Record does not delete.");
                return;
            }
            gridLoad();
            resetForm();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void checkBranchCode() {
        this.setErrorMessage("");
        this.setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.brCode.equalsIgnoreCase("") || this.brCode.length() == 0) {
            this.setErrorMessage("Please enter Branch Code.");
            return;
        }
        Matcher brNo = p.matcher(brCode);
        if (!brNo.matches()) {
            this.setErrorMessage("Please enter valid Branch Code.");
            return;
        }
        if (this.brCode.contains(".")) {
            this.setErrorMessage("Please enter valid Branch Code.");
            return;
        }
        try {
            List result = brnMaster.checkBrnCode(Integer.parseInt(this.brCode));
            if (!result.isEmpty()) {
                this.setErrorMessage("Branch code already exist!");
                this.setBrCode("");
            } else {
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validatePinCode() {
        this.setErrorMessage("");
        this.setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.pinCode.equalsIgnoreCase("") || this.pinCode.length() == 0) {
            this.setErrorMessage("Please enter Pin Code.");
            return;
        }
        Matcher pinNo = p.matcher(pinCode);
        if (!pinNo.matches()) {
            this.setErrorMessage("Please enter valid Pin Code.");
            return;
        }
        if (this.pinCode.contains(".")) {
            this.setErrorMessage("Please enter valid Pin Code.");
            return;
        }
    }

    public void resetForm() {
        this.setBrCode("");
        this.setBrName("");
        this.setBrAddress("");
        this.setAlphaCode("");
        this.setCity("");
        this.setPinCode("");
        this.setRegOffice("");
        this.setState("");
        this.setIfscCode("");
        this.setMobileNo("");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        flag = true;
        this.setErrorMessage("");
        this.setMessage("");
        this.setBrCode("");
        this.setBrName("");
        this.setBrAddress("");
        this.setAlphaCode("");
        this.setCity("");
        this.setPinCode("");
        this.setRegOffice("");
        this.setState("");
        this.setIfscCode("");
        this.setMobileNo("");
        gridLoad();
    }

    /*Getter And Setter*/
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public List<BranchMasterGridLoad> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BranchMasterGridLoad> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public String getBrAddress() {
        return brAddress;
    }

    public void setBrAddress(String brAddress) {
        this.brAddress = brAddress;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getRegOffice() {
        return regOffice;
    }

    public void setRegOffice(String regOffice) {
        this.regOffice = regOffice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BranchMasterGridLoad getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(BranchMasterGridLoad currentItem) {
        this.currentItem = currentItem;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getComputerStatus() {
        return computerStatus;
    }

    public void setComputerStatus(String computerStatus) {
        this.computerStatus = computerStatus;
    }

    public List<SelectItem> getLocationTypeList() {
        return locationTypeList;
    }

    public void setLocationTypeList(List<SelectItem> locationTypeList) {
        this.locationTypeList = locationTypeList;
    }

    public List<SelectItem> getComputerStatusList() {
        return computerStatusList;
    }

    public void setComputerStatusList(List<SelectItem> computerStatusList) {
        this.computerStatusList = computerStatusList;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    
}
