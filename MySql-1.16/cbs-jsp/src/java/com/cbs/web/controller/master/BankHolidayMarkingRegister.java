package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author ROHIT KRISHNA
 */
public final class BankHolidayMarkingRegister extends BaseBean {

    BankAndLoanMasterFacadeRemote holidayReg;
    private CommonReportMethodsRemote remoteCode;
    private GeneralMasterFacadeRemote testEJB;
    private String errorMessage;
    private String message;
    private String choice;
    private List<SelectItem> choiceOption;
    private String festible;
    private Date hoidayDate;
    private String branch;
    private List<SelectItem> branchOption;
    private List<HolidayRegisterPojo> hoidayList;
    int currentRow;
    private HolidayRegisterPojo currentItem = new HolidayRegisterPojo();
    private boolean markFlag;
    private boolean updateFlag;
    private String saveUpdateFlag;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getSaveUpdateFlag() {
        return saveUpdateFlag;
    }

    public void setSaveUpdateFlag(String saveUpdateFlag) {
        this.saveUpdateFlag = saveUpdateFlag;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public boolean isMarkFlag() {
        return markFlag;
    }

    public void setMarkFlag(boolean markFlag) {
        this.markFlag = markFlag;
    }

    public HolidayRegisterPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(HolidayRegisterPojo currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public List<SelectItem> getChoiceOption() {
        return choiceOption;
    }

    public void setChoiceOption(List<SelectItem> choiceOption) {
        this.choiceOption = choiceOption;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFestible() {
        return festible;
    }

    public void setFestible(String festible) {
        this.festible = festible;
    }

    public Date getHoidayDate() {
        return hoidayDate;
    }

    public void setHoidayDate(Date hoidayDate) {
        this.hoidayDate = hoidayDate;
    }

    public List<HolidayRegisterPojo> getHoidayList() {
        return hoidayList;
    }

    public void setHoidayList(List<HolidayRegisterPojo> hoidayList) {
        this.hoidayList = hoidayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(List<SelectItem> branchOption) {
        this.branchOption = branchOption;
    }
    

    public BankHolidayMarkingRegister() {
        try {
            holidayReg = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            remoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            testEJB = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            this.setHoidayDate(new Date());
            this.setErrorMessage("");
            this.setMessage("");
            this.setMarkFlag(true);
            this.setUpdateFlag(false);
            this.setSaveUpdateFlag("u");
            choiceOption = new ArrayList<SelectItem>();
            choiceOption.add(new SelectItem("--Select--"));
            choiceOption.add(new SelectItem("Mark Holiday"));
            choiceOption.add(new SelectItem("Unmark Holiday"));
            
           List brncode = remoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchOption = new ArrayList<>();
            branchOption.add(new SelectItem("00","--Select--"));
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchOption.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getTableDetails();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void choiceOnblur() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFestible("");
            Date date = new Date();
            this.setHoidayDate(date);
            this.setMarkFlag(true);
            this.setUpdateFlag(false);
            if (this.choice == null || this.choice.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select your action.");
                return;
            }
            if (this.choice.equalsIgnoreCase("Mark Holiday")) {
                this.setMarkFlag(true);
                this.setUpdateFlag(false);
            } else {
                this.setMarkFlag(false);
                this.setUpdateFlag(true);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableDetails() {
        hoidayList = new ArrayList<HolidayRegisterPojo>();
        try {
            List tableList = new ArrayList();
            tableList = holidayReg.gridDetailBankHolidayMarkingRegister();
            if (!tableList.isEmpty()) {
                for (int i = 0; i < tableList.size(); i++) {
                    Vector ele = (Vector) tableList.get(i);
                    HolidayRegisterPojo res = new HolidayRegisterPojo();
                    res.setHolidayDate(ele.get(0).toString());
                    res.setHolidayDescription(ele.get(1).toString());
                    hoidayList.add(res);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("holidayDate"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (HolidayRegisterPojo item : hoidayList) {
                if (item.getHolidayDate().equalsIgnoreCase(ac)) {
                    currentItem = item;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFestible(currentItem.getHolidayDescription());
            this.setHoidayDate(sdf.parse(currentItem.getHolidayDate()));
            this.setChoice("Unmark Holiday");
            this.setMarkFlag(false);
            this.setUpdateFlag(true);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void markHoliday() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.choice.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select action to be performed.");
                return;
            }
            if(this.branch.equals("00") || this.branch == null){
                this.setErrorMessage("Please select the branch option.");
                return;
            }
            if (this.festible == null || this.festible.equalsIgnoreCase("") || this.festible.length() == 0) {
                this.setErrorMessage("Please enter holiday description.");
                return;
            }
            if (this.hoidayDate == null) {
                this.setErrorMessage("Please enter holiday date.");
                return;
            }
            String result = "";
            if(branch.equalsIgnoreCase("0A")){
             result = holidayReg.saveRecordBankHolidayMarkingRegister(ymd.format(hoidayDate), this.festible);   
            }else{
             result = testEJB.saveHolidayMarkingData(ymd.format(hoidayDate), this.festible,this.branch);  
            }
           if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("Record not saved !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("false")) {
                    this.setErrorMessage("Record not saved !!!");
                    return;
                } else if (result.contains("!")) {
                    this.setErrorMessage(result);
                    return;
                } else {
                    this.setMessage(result);
                    getTableDetails();
                    this.setChoice("--Select--");
                    this.setFestible("");
                    Date date = new Date();
                    this.setHoidayDate(date);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveUpdateFlag("u");
            if (this.choice.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select action to be performed.");
                return;
            }
            if(branch == null || this.branch.equalsIgnoreCase("00") || this.branch.length()==0){
                this.setErrorMessage("Please Select the branch option");
                return;
            }
            if (this.festible == null || this.festible.equalsIgnoreCase("") || this.festible.length() == 0) {
                this.setErrorMessage("Please enter holiday description.");
                return;
            }
            if (this.hoidayDate == null) {
                this.setErrorMessage("Please enter holiday date.");
                return;
            }
            String result="";
            if(branch.equalsIgnoreCase("0A")){
            result = holidayReg.deleteDataBankHolidayMarkingRegister(ymd.format(hoidayDate), this.festible, saveUpdateFlag);     
            }else{
            result = testEJB.deleteHolidayMarkingData(ymd.format(hoidayDate), this.festible, saveUpdateFlag,this.branch);
            }
           
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("falseDelete")) {
                    this.setErrorMessage("Record not deleted !!!");
                    return;
                } else if (result.equalsIgnoreCase("falseUpdate")) {
                    this.setErrorMessage("Record not updated !!!");
                    return;
                } else if (result.contains("!")) {
                    this.setErrorMessage(result);
                    getTableDetails();
                    return;
                } else {
                    this.setMessage(result);
                    this.setChoice("--Select--");
                    this.setFestible("");
                    Date date = new Date();
                    this.setHoidayDate(date);
                    getTableDetails();
                    this.setMarkFlag(true);
                    this.setUpdateFlag(false);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void deleteRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveUpdateFlag("d");
            if (this.choice.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select action to be performed.");
                return;
            }
           if(branch == null || this.branch.equalsIgnoreCase("") || this.branch.length()==0){
                this.setErrorMessage("Please Select the branch option");
                return;
            }
            if (this.festible == null || this.festible.equalsIgnoreCase("") || this.festible.length() == 0) {
                this.setErrorMessage("Please enter holiday description.");
                return;
            }
            if (this.hoidayDate == null) {
                this.setErrorMessage("Please enter holiday date.");
                return;
            }
            String result="";
            if(branch.equalsIgnoreCase("0A")){
            result = holidayReg.deleteDataBankHolidayMarkingRegister(ymd.format(hoidayDate), this.festible, saveUpdateFlag);     
            }else{
            result = testEJB.deleteHolidayMarkingData(ymd.format(hoidayDate), this.festible, saveUpdateFlag,this.branch);
            }
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("falseDelete")) {
                    this.setErrorMessage("Record not deleted !!!");
                    return;
                } else if (result.equalsIgnoreCase("falseUpdate")) {
                    this.setErrorMessage("Record not updated !!!");
                    return;
                } else if (result.contains("!")) {
                    this.setErrorMessage(result);
                    getTableDetails();
                    return;
                } else {
                    this.setMessage(result);
                    this.setChoice("--Select--");
                    this.setFestible("");
                    Date date = new Date();
                    this.setHoidayDate(date);
                    getTableDetails();
                    this.setMarkFlag(true);
                    this.setUpdateFlag(false);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setChoice("--Select--");
            this.setFestible("");
            Date date = new Date();
            this.setHoidayDate(date);
            this.setMarkFlag(true);
            this.setUpdateFlag(false);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }
}
