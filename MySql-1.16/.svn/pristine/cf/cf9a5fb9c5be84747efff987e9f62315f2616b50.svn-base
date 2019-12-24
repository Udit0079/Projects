/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  14 JUNE 2011
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.SkillGrid;
import com.hrms.web.pojo.ProgrmDetailsGrid;
import com.hrms.web.pojo.InstituteDepartmentSearchGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrDetailsProgramPKTO;
import com.hrms.common.to.HrDetailsProgramTO;
import com.hrms.common.to.HrMstProgramPKTO;
import com.hrms.common.to.HrMstProgramTO;
import com.hrms.web.delegate.ProgramDetailsDelegate;
import com.hrms.web.utils.WebUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author ROHIT KRISHNA
 */
public class ProgramDetails extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private String errorMessage;
    private String message;
    private String progName;
    private Date progFrom;
    private Date progTo;
    private String totalCost;
    private String training;
    private List<SelectItem> trainingList;
    private String deptName;
    private String facultyName;
    private String compCode = "0";
    private boolean saveFlag;
    private boolean editFlag;
    private boolean delFlag;
    private boolean instFacultyFlag;
    private List<ProgrmDetailsGrid> gridDetail;
    int currentRow;
    private ProgrmDetailsGrid currentItem = new ProgrmDetailsGrid();
    private List<InstituteDepartmentSearchGrid> gridDetail1;
    int currentRow1;
    private InstituteDepartmentSearchGrid currentItem1 = new InstituteDepartmentSearchGrid();
    private boolean deptBrowseFlag;
    private String searchPanelHeaderName;
    private String searchPanelColName;
    private String instDeptCode;
    private String empCode;
    private List<InstituteDepartmentSearchGrid> gridDetail2;
    int currentRow2;
    private InstituteDepartmentSearchGrid currentItem2 = new InstituteDepartmentSearchGrid();
    private String facultyBrowseFlag;
    private String tempInsDepCode;
    private String charatersFind;
    private String skillProgName;
    private List<SelectItem> skillProgNameList;
    private String minSkilSet;
    private List<SelectItem> minSkilSetList;
    private List<SkillGrid> gridDetail3;
    int currentRow3;
    private SkillGrid currentItem3 = new SkillGrid();
    private String skillmessage;
    private String skillErrorMessage;
    private boolean saveSkillFlag;
    private boolean editSkillFlag;
    private boolean delSkillFlag;
    private Integer defaultComp;
    private String operation;
    private List<SelectItem> operationList;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<SelectItem> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SelectItem> operationList) {
        this.operationList = operationList;
    }

    public boolean isDelSkillFlag() {
        return delSkillFlag;
    }

    public void setDelSkillFlag(boolean delSkillFlag) {
        this.delSkillFlag = delSkillFlag;
    }

    public boolean isEditSkillFlag() {
        return editSkillFlag;
    }

    public void setEditSkillFlag(boolean editSkillFlag) {
        this.editSkillFlag = editSkillFlag;
    }

    public boolean isSaveSkillFlag() {
        return saveSkillFlag;
    }

    public void setSaveSkillFlag(boolean saveSkillFlag) {
        this.saveSkillFlag = saveSkillFlag;
    }

    public String getSkillErrorMessage() {
        return skillErrorMessage;
    }

    public void setSkillErrorMessage(String skillErrorMessage) {
        this.skillErrorMessage = skillErrorMessage;
    }

    public String getSkillmessage() {
        return skillmessage;
    }

    public void setSkillmessage(String skillmessage) {
        this.skillmessage = skillmessage;
    }

    public SkillGrid getCurrentItem3() {
        return currentItem3;
    }

    public void setCurrentItem3(SkillGrid currentItem3) {
        this.currentItem3 = currentItem3;
    }

    public int getCurrentRow3() {
        return currentRow3;
    }

    public void setCurrentRow3(int currentRow3) {
        this.currentRow3 = currentRow3;
    }

    public List<SkillGrid> getGridDetail3() {
        return gridDetail3;
    }

    public void setGridDetail3(List<SkillGrid> gridDetail3) {
        this.gridDetail3 = gridDetail3;
    }

    public String getMinSkilSet() {
        return minSkilSet;
    }

    public void setMinSkilSet(String minSkilSet) {
        this.minSkilSet = minSkilSet;
    }

    public List<SelectItem> getMinSkilSetList() {
        return minSkilSetList;
    }

    public void setMinSkilSetList(List<SelectItem> minSkilSetList) {
        this.minSkilSetList = minSkilSetList;
    }

    public String getSkillProgName() {
        return skillProgName;
    }

    public void setSkillProgName(String skillProgName) {
        this.skillProgName = skillProgName;
    }

    public List<SelectItem> getSkillProgNameList() {
        return skillProgNameList;
    }

    public void setSkillProgNameList(List<SelectItem> skillProgNameList) {
        this.skillProgNameList = skillProgNameList;
    }

    public String getCharatersFind() {
        return charatersFind;
    }

    public void setCharatersFind(String charatersFind) {
        this.charatersFind = charatersFind;
    }

    public String getTempInsDepCode() {
        return tempInsDepCode;
    }

    public void setTempInsDepCode(String tempInsDepCode) {
        this.tempInsDepCode = tempInsDepCode;
    }

    public String getFacultyBrowseFlag() {
        return facultyBrowseFlag;
    }

    public void setFacultyBrowseFlag(String facultyBrowseFlag) {
        this.facultyBrowseFlag = facultyBrowseFlag;
    }

    public InstituteDepartmentSearchGrid getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(InstituteDepartmentSearchGrid currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public List<InstituteDepartmentSearchGrid> getGridDetail2() {
        return gridDetail2;
    }

    public void setGridDetail2(List<InstituteDepartmentSearchGrid> gridDetail2) {
        this.gridDetail2 = gridDetail2;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getInstDeptCode() {
        return instDeptCode;
    }

    public void setInstDeptCode(String instDeptCode) {
        this.instDeptCode = instDeptCode;
    }

    public String getSearchPanelColName() {
        return searchPanelColName;
    }

    public void setSearchPanelColName(String searchPanelColName) {
        this.searchPanelColName = searchPanelColName;
    }

    public String getSearchPanelHeaderName() {
        return searchPanelHeaderName;
    }

    public void setSearchPanelHeaderName(String searchPanelHeaderName) {
        this.searchPanelHeaderName = searchPanelHeaderName;
    }

    public boolean isDeptBrowseFlag() {
        return deptBrowseFlag;
    }

    public void setDeptBrowseFlag(boolean deptBrowseFlag) {
        this.deptBrowseFlag = deptBrowseFlag;
    }

    public InstituteDepartmentSearchGrid getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(InstituteDepartmentSearchGrid currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<InstituteDepartmentSearchGrid> getGridDetail1() {
        return gridDetail1;
    }

    public void setGridDetail1(List<InstituteDepartmentSearchGrid> gridDetail1) {
        this.gridDetail1 = gridDetail1;
    }

    public boolean isInstFacultyFlag() {
        return instFacultyFlag;
    }

    public void setInstFacultyFlag(boolean instFacultyFlag) {
        this.instFacultyFlag = instFacultyFlag;
    }

    public ProgrmDetailsGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ProgrmDetailsGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ProgrmDetailsGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ProgrmDetailsGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public List<SelectItem> getTrainingList() {
        return trainingList;
    }

    public void setTrainingList(List<SelectItem> trainingList) {
        this.trainingList = trainingList;
    }

    public Date getProgFrom() {
        return progFrom;
    }

    public void setProgFrom(Date progFrom) {
        this.progFrom = progFrom;
    }

    public Date getProgTo() {
        return progTo;
    }

    public void setProgTo(Date progTo) {
        this.progTo = progTo;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-/][a-zA-Z0-9,]+)*");
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of ProgramDetails */
    public ProgramDetails() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            this.setInstFacultyFlag(true);
            this.setDeptBrowseFlag(true);
            this.setFacultyBrowseFlag("true");
            trainingList = new ArrayList<SelectItem>();
            trainingList.add(new SelectItem("--SELECT--"));
            trainingList.add(new SelectItem("I", "INHOUSE"));
            trainingList.add(new SelectItem("E", "EXTERNAL"));
            //gridLoad();
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            this.setSaveSkillFlag(false);
            this.setEditSkillFlag(true);
            this.setDelSkillFlag(true);
            skillSetAndProgNameLoad();
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Skill Panel Dropdown Method**/
    public void skillSetAndProgNameLoad() {
        try {
            List resultLt = new ArrayList();
            List resultLt1 = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.minSkill(Integer.parseInt(compCode));
            resultLt1 = progDtDel.skilProgName(Integer.parseInt(compCode));
            minSkilSetList = new ArrayList<SelectItem>();
            minSkilSetList.add(new SelectItem("--Select--"));
            skillProgNameList = new ArrayList<SelectItem>();
            skillProgNameList.add(new SelectItem("--Select--"));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    minSkilSetList.add(new SelectItem(result[0].toString(), result[1].toString()));
                }
            }
            if (!resultLt1.isEmpty()) {
                Iterator i1 = resultLt1.iterator();
                while (i1.hasNext()) {
                    Object[] result1 = (Object[]) i1.next();
                    skillProgNameList.add(new SelectItem(result1[0].toString(), result1[1].toString()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**End**/
    public void gridLoad() {
        try {
            if (operation.equalsIgnoreCase("0")) {
                setMessage("Please select an operation!");
                return;
            }
            gridDetail = new ArrayList<ProgrmDetailsGrid>();
            List resultLt = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.progDetailOnload(Integer.parseInt(compCode));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    ProgrmDetailsGrid rd = new ProgrmDetailsGrid();
                    rd.setCompCode(result[0].toString());
                    rd.setProgCode(result[1].toString());
                    rd.setProgName(result[2].toString());
                    rd.setProgFrom(sdf.format(result[3]));
                    rd.setProgTo(sdf.format(result[4]));
                    rd.setTotalCost(formatter.format(result[5]));
                    rd.setInextHouse(result[6].toString());
                    rd.setInstCode(result[7].toString());
                    rd.setFacultyName(result[8].toString());
                    gridDetail.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void skillsGridLoad() {
        try {
            this.setSaveSkillFlag(false);
            this.setEditSkillFlag(true);
            this.setDelSkillFlag(true);
            gridDetail3 = new ArrayList<SkillGrid>();
            List resultLt = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.skilGridDetails(Integer.parseInt(compCode));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    SkillGrid rd = new SkillGrid();
                    rd.setProgCode(result[0].toString());
                    rd.setProgName(result[1].toString());
                    rd.setStructCode(result[2].toString());
                    rd.setDescription(result[3].toString());
                    gridDetail3.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public void gridLoadOnFindButton(){
//        try{
//            gridDetail = new ArrayList<ProgrmDetailsGrid>();
//            List resultLt = new ArrayList();
//            ctx = new InitialContext();
//            progDet = (ProgramDetailsRemote) ctx.lookup(ProgramDetailsRemote.class.getName());
//            resultLt = progDet.programDetailGridForFindBtn(Integer.parseInt(companyCode),this.charatersFind);
//            if (!resultLt.isEmpty()) {
//                Iterator i1 = resultLt.iterator();
//                while (i1.hasNext()) {
//                    Object[] result = (Object[]) i1.next();
//                    ProgrmDetailsGrid rd = new ProgrmDetailsGrid();
//                    rd.setCompCode(result[0].toString());
//                    rd.setProgCode(result[1].toString());
//                    rd.setProgName(result[2].toString());
//                    rd.setProgFrom(sdf.format(result[3]));
//                    rd.setProgTo(sdf.format(result[4]));
//                    rd.setTotalCost(formatter.format(result[5]));
//                    rd.setInextHouse(result[6].toString());
//                    rd.setInstCode(result[7].toString());
//                    rd.setFacultyName(result[8].toString());
//                    gridDetail.add(rd);
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
    public void addProgramDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            Date date = new Date();
            if (this.progName == null || this.progName.equalsIgnoreCase("") || this.progName.length() == 0) {
                this.setErrorMessage("Please enter program name.");
                return;
            }
            Matcher progNameCheck = pm.matcher(progName);
            if (!progNameCheck.matches()) {
                this.setErrorMessage("Program name should not contain special characters.");
                return;
            }
            if (this.progFrom == null) {
                this.setErrorMessage("Please enter program from date.");
                return;
            }
            if (this.progTo == null) {
                this.setErrorMessage("Please enter program to date.");
                return;
            }
            if (this.totalCost == null || this.totalCost.equalsIgnoreCase("") || this.totalCost.length() == 0) {
                this.setErrorMessage("Please enter total cost.");
                return;
            }
            Matcher totalCostCheck = p.matcher(totalCost);
            if (!totalCostCheck.matches()) {
                this.setErrorMessage("Total cost should be numeric.");
                return;
            }
            if (this.totalCost.contains(".")) {
                if (this.totalCost.indexOf(".") != this.totalCost.lastIndexOf(".")) {
                    this.setErrorMessage("Please fill total cost correctly.");
                    return;
                } else if (this.totalCost.substring(totalCost.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill total cost upto two decimal places.");
                    return;
                }
            }
            if (this.training.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select training.");
                return;
            }
            if (this.deptName == null || this.deptName.equalsIgnoreCase("") || this.deptName.length() == 0) {
                this.setErrorMessage("Please enter department/institute name.");
                return;
            }

            String progCode = "";
            HrMstProgramTO hrMstProgTO = new HrMstProgramTO();
            HrMstProgramPKTO hrMstProgPKTO = new HrMstProgramPKTO();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            hrMstProgPKTO.setCompCode(Integer.parseInt(this.compCode));
            hrMstProgTO.setProgName(this.progName);
            /**THIS WILL BE TAKEN FROM POPUP**/
            hrMstProgTO.setInstCode(this.tempInsDepCode);
            hrMstProgTO.setTrngFrom(this.progFrom);
            hrMstProgTO.setTrngTo(this.progTo);
            hrMstProgTO.setInextHouse(this.training.charAt(0));
            hrMstProgTO.setTrngCost(Double.parseDouble(this.totalCost));
            if (this.facultyName == null) {
                this.facultyName = "";
            }
            hrMstProgTO.setFacuName(this.facultyName);
            hrMstProgTO.setDefaultComp(defaultComp);
            hrMstProgTO.setStatFlag("Y");
            hrMstProgTO.setStatUpFlag("N");
            hrMstProgTO.setModDate(date);

            hrMstProgTO.setAuthBy(getUserName());
            hrMstProgTO.setEnteredBy(getUserName());
            if (operation.equalsIgnoreCase("1")) {
                progCode = progDtDel.maxProgramCode(Integer.parseInt(this.compCode));
                if (progCode == null || progCode.equalsIgnoreCase("") || progCode.length() == 0) {
                    this.setErrorMessage("Program code not generated !!!");
                    return;
                }
                int length = progCode.length();
                int addedZero = 3 - length;
                for (int i = 1; i <= addedZero; i++) {
                    progCode = "0" + progCode;
                }
                progCode = "PRG" + progCode;
                if (progCode.length() != 6) {
                    this.setErrorMessage("Proper program code is not generated !!!");
                    return;
                }
                hrMstProgPKTO.setProgCode(progCode);
                hrMstProgTO.setHrMstProgramPKTO(hrMstProgPKTO);
                String result = progDtDel.saveProgramDetail(hrMstProgTO);
                if (result == null || result.equalsIgnoreCase("")) {
                    this.setErrorMessage("System error occured !!!");
                } else if (result.equalsIgnoreCase("false")) {
                    this.setErrorMessage("Data not saved !!!");
                } else {
                    this.setMessage("Data saved succesfully.");
                    this.setDelFlag(true);
                    this.setProgFrom(null);
                    this.setProgName("");
                    this.setProgTo(null);
                    this.setTotalCost("");
                    this.setTraining("--SELECT--");
                    this.setDeptName("");
                    this.setFacultyName("");
                    this.setDeptBrowseFlag(true);
                }
                gridLoad();
            } else if (operation.equalsIgnoreCase("2")) {
                hrMstProgPKTO.setProgCode(currentItem.getProgCode());
                hrMstProgTO.setHrMstProgramPKTO(hrMstProgPKTO);

                String result = progDtDel.updateProgramDetail(hrMstProgTO);
                if (result == null || result.equalsIgnoreCase("")) {
                    this.setErrorMessage("System error occured !!!");
                    return;
                } else if (result.equalsIgnoreCase("false")) {
                    this.setErrorMessage("Record not updated.");
                    return;
                } else {
                    this.setMessage("Record updated succesfully.");
                    gridLoad();
                    this.setDelFlag(true);
                    this.setProgFrom(null);
                    this.setProgName("");
                    this.setProgTo(null);
                    this.setTotalCost("");
                    this.setTraining("--SELECT--");
                    this.setDeptName("");
                    this.setFacultyName("");
                    this.setFacultyBrowseFlag("true");
                    this.setDeptBrowseFlag(true); 
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateProgDetail() {
        try {
            Date date = new Date();
            this.setErrorMessage("");
            this.setMessage("");
            if (this.progName == null || this.progName.equalsIgnoreCase("") || this.progName.length() == 0) {
                this.setErrorMessage("Please enter program name.");
                return;
            }
            Matcher progNameCheck = pm.matcher(progName);
            if (!progNameCheck.matches()) {
                this.setErrorMessage("Program name should not contain special characters.");
                return;
            }
            if (this.progFrom == null) {
                this.setErrorMessage("Please enter program from date.");
                return;
            }
            if (this.progTo == null) {
                this.setErrorMessage("Please enter program to date.");
                return;
            }
            if (this.totalCost == null || this.totalCost.equalsIgnoreCase("") || this.totalCost.length() == 0) {
                this.setErrorMessage("Please enter total cost.");
                return;
            }
            Matcher totalCostCheck = p.matcher(totalCost);
            if (!totalCostCheck.matches()) {
                this.setErrorMessage("Total cost should be numeric.");
                return;
            }
            if (this.totalCost.contains(".")) {
                if (this.totalCost.indexOf(".") != this.totalCost.lastIndexOf(".")) {
                    this.setErrorMessage("Please fill total cost correctly.");
                    return;
                } else if (this.totalCost.substring(totalCost.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill total cost upto two decimal places.");
                    return;
                }
            }
            if (this.training.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select training.");
                return;
            }
            if (this.deptName == null || this.deptName.equalsIgnoreCase("") || this.deptName.length() == 0) {
                this.setErrorMessage("Please enter department/institute name.");
                return;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteProgramRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.progName == null || this.progName.equalsIgnoreCase("") || this.progName.length() == 0) {
                this.setErrorMessage("Please enter program name.");
                return;
            }
            Matcher progNameCheck = pm.matcher(progName);
            if (!progNameCheck.matches()) {
                this.setErrorMessage("Program name should not contain special characters.");
                return;
            }
            if (this.progFrom == null) {
                this.setErrorMessage("Please enter program from date.");
                return;
            }
            if (this.progTo == null) {
                this.setErrorMessage("Please enter program to date.");
                return;
            }
            if (this.totalCost == null || this.totalCost.equalsIgnoreCase("") || this.totalCost.length() == 0) {
                this.setErrorMessage("Please enter total cost.");
                return;
            }
            Matcher totalCostCheck = p.matcher(totalCost);
            if (!totalCostCheck.matches()) {
                this.setErrorMessage("Total cost should be numeric.");
                return;
            }
            if (this.totalCost.contains(".")) {
                if (this.totalCost.indexOf(".") != this.totalCost.lastIndexOf(".")) {
                    this.setErrorMessage("Please fill total cost correctly.");
                    return;
                } else if (this.totalCost.substring(totalCost.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please fill total cost upto two decimal places.");
                    return;
                }
            }
            if (this.training.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select training.");
                return;
            }
            if (this.deptName == null || this.deptName.equalsIgnoreCase("") || this.deptName.length() == 0) {
                this.setErrorMessage("Please enter department/institute name.");
                return;
            }
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            String result = progDtDel.deleteProgramDetail(Integer.parseInt(this.compCode), currentItem.getProgCode());
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setErrorMessage("Record not deleted.");
                return;
            } else {
                this.setMessage("Record deleted succesfully.");
                gridLoad();
                this.setSaveFlag(false);
                this.setDelFlag(true);
                this.setEditFlag(true);
                this.setProgFrom(null);
                this.setProgName("");
                this.setProgTo(null);
                this.setTotalCost("");
                this.setTraining("--SELECT--");
                this.setDeptName("");
                this.setFacultyName("");
                this.setFacultyBrowseFlag("true");
                this.setDeptBrowseFlag(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void gridLoadOfInstitutes() {
        try {
            if (this.training.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select training.");
                return;
            } else if (this.training.equalsIgnoreCase("I")) {
                this.setSearchPanelHeaderName("Department Search");
                this.setSearchPanelColName("Department Name");
            } else if (this.training.equalsIgnoreCase("E")) {
                this.setSearchPanelHeaderName("Institute Search");
                this.setSearchPanelColName("Institute Name");
            }
            gridDetail1 = new ArrayList<InstituteDepartmentSearchGrid>();
            List resultLt = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.instituteSearchGridDetail(Integer.parseInt(compCode), this.training);
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    InstituteDepartmentSearchGrid rd = new InstituteDepartmentSearchGrid();
                    rd.setInstCode(result[0].toString());
                    rd.setInstName(result[1].toString());
                    gridDetail1.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void gridLoadOfFacultyNames() {
        try {
            this.setSearchPanelHeaderName("Faculty Search");
            this.setSearchPanelColName("Faculty Name");
            gridDetail1 = new ArrayList<InstituteDepartmentSearchGrid>();
            List resultLt = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.facultySearchGridDetail(Integer.parseInt(compCode));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    InstituteDepartmentSearchGrid rd = new InstituteDepartmentSearchGrid();
                    rd.setInstCode(result[0].toString());
                    rd.setInstName(result[1].toString());
                    gridDetail1.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void gridLoadOfEmployeeSearch() {
        try {
            if (this.empCode == null || this.empCode.equalsIgnoreCase("") || this.empCode.length() == 0) {
                this.setErrorMessage("Institute/Department code is not found.");
                return;
            }
            gridDetail2 = new ArrayList<InstituteDepartmentSearchGrid>();
            List resultLt = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.employeeSearchGridDetail(Integer.parseInt(compCode), this.empCode);
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    InstituteDepartmentSearchGrid rd = new InstituteDepartmentSearchGrid();
                    rd.setEmpName(result[0].toString());
                    rd.setEmpCode(result[1].toString());
                    rd.setEmpID(result[2].toString());
                    gridDetail2.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void trainingOnBlurMethod() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setDeptName("");
            if (this.training.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select training.");
                this.setDeptBrowseFlag(true);
                return;
            } else {
                this.setDeptBrowseFlag(false);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow1(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("instCode"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (InstituteDepartmentSearchGrid item : gridDetail1) {
            if (item.getInstCode().equalsIgnoreCase(ac)) {
                currentItem1 = item;
            }
        }
    }

    public void fetchCurrentRow2(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("empID"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (InstituteDepartmentSearchGrid item : gridDetail2) {
            if (item.getInstCode().equalsIgnoreCase(ac)) {
                currentItem2 = item;
            }
        }
    }

    public void fillValuesEmpSearch() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFacultyName(currentItem2.getEmpName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fillValuesInstDept() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            //this.setDeptName("");
            this.setTempInsDepCode(currentItem1.getInstCode());
            if (this.searchPanelHeaderName.equalsIgnoreCase("Faculty Search")) {
                this.setFacultyName(currentItem1.getInstName());
            } else {
                this.setDeptName(currentItem1.getInstName());
                this.setInstDeptCode(currentItem1.getInstCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("progCode"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ProgrmDetailsGrid item : gridDetail) {
            if (item.getInstCode().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFacultyBrowseFlag("false");
            this.setProgName(currentItem.getProgName());
            this.setProgFrom(sdf.parse(currentItem.getProgFrom()));
            this.setProgTo(sdf.parse(currentItem.getProgTo()));
            this.setTotalCost(currentItem.getTotalCost());
            this.setTraining(currentItem.getInextHouse());
            this.setFacultyName(currentItem.getFacultyName());
            this.setEmpCode(currentItem.getInstCode());
            this.setTempInsDepCode(currentItem.getInstCode());
            List resultLt = new ArrayList();
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            resultLt = progDtDel.instDeptNameList(currentItem.getInstCode(), this.training);
            String tempInstNam = "";
            if (!resultLt.isEmpty()) {
                tempInstNam = resultLt.get(0).toString();
            }
            this.setDeptName(tempInstNam);
            this.setSaveFlag(true);
            this.setEditFlag(false);
            this.setDelFlag(false);
            this.setDeptBrowseFlag(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void fetchCurrentRow3(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("progCode"));
        currentRow3 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (SkillGrid item : gridDetail3) {
            if (item.getProgCode().equalsIgnoreCase(ac)) {
                currentItem3 = item;
            }
        }
    }

    public void fillValuesSkillGrid() {
        try {
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
            this.setMinSkilSet(currentItem3.getStructCode());
            this.setSkillProgName(currentItem3.getProgCode());
            this.setSaveSkillFlag(true);
            this.setEditSkillFlag(false);
            this.setDelSkillFlag(false);
            setOperation("2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setProgFrom(null);
            this.setProgName("");
            this.setProgTo(null);
            this.setTotalCost("");
            this.setTraining("--SELECT--");
            this.setDeptName("");
            this.setFacultyName("");
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            this.setFacultyBrowseFlag("true");
            this.setDeptBrowseFlag(true);
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
            this.setSaveSkillFlag(false);
            this.setEditSkillFlag(true);
            this.setDelSkillFlag(true);
            this.setSkillProgName("--Select--");
            this.setMinSkilSet("--Select--");
            setOperation("0");
            gridDetail = new ArrayList<ProgrmDetailsGrid>();
            gridDetail1 = new ArrayList<InstituteDepartmentSearchGrid>();
            gridDetail2 = new ArrayList<InstituteDepartmentSearchGrid>();
            gridDetail3 = new ArrayList<SkillGrid>();
            gridLoad();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "case1";
    }

    public void saveSkillDetail() {
        try {
            Date date = new Date();
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
            if (this.skillProgName.equalsIgnoreCase("--Select--")) {
                this.setSkillErrorMessage("Please select program name.");
                return;
            }
            if (this.minSkilSet.equalsIgnoreCase("--Select--")) {
                this.setSkillErrorMessage("Please select minimum skill set.");
                return;
            }

            HrDetailsProgramTO hrDetProgTO = new HrDetailsProgramTO();
            HrDetailsProgramPKTO hrDetProgPKTO = new HrDetailsProgramPKTO();
            hrDetProgPKTO.setCompCode(Integer.parseInt(this.compCode));
            hrDetProgPKTO.setProgCode(this.skillProgName);
            hrDetProgPKTO.setSkillCode(this.minSkilSet);
            hrDetProgTO.setHrDetailsProgramPKTO(hrDetProgPKTO);
            hrDetProgTO.setDefaultComp(defaultComp);
            hrDetProgTO.setStatFlag("Y");
            hrDetProgTO.setStatUpFlag("N");
            hrDetProgTO.setModDate(date);
            hrDetProgTO.setAuthBy(getUserName());
            hrDetProgTO.setEnteredBy(getUserName());

            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            String result = progDtDel.saveSkillDetails(hrDetProgTO);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setSkillErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setSkillErrorMessage("Record not saved.");
                return;
            } else if (result.equalsIgnoreCase("false1")) {
                this.setSkillErrorMessage("Record already exists.");
                return;
            } else {
                this.setSkillmessage("Record saved succesfully.");
                skillsGridLoad();
                this.setSkillProgName("--Select--");
                this.setMinSkilSet("--Select--");
                this.setSaveSkillFlag(false);
                this.setEditSkillFlag(true);
                this.setDelSkillFlag(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateSkillRecord() {
        try {
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
            if (this.skillProgName.equalsIgnoreCase("--Select--")) {
                this.setSkillErrorMessage("Please select program name.");
                return;
            }
            if (this.minSkilSet.equalsIgnoreCase("--Select--")) {
                this.setSkillErrorMessage("Please select minimum skill set.");
                return;
            }
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            String result = progDtDel.updateSkillDetails(Integer.parseInt(this.compCode), this.skillProgName, this.minSkilSet, currentItem3.getProgCode(), currentItem3.getStructCode());
            if (result == null || result.equalsIgnoreCase("")) {
                this.setSkillErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setSkillErrorMessage("Record not updated.");
                return;
            } else if (result.equalsIgnoreCase("false1")) {
                this.setSkillErrorMessage("Record already exists.");
                return;
            } else {
                this.setSkillmessage("Record updated succesfully.");
                skillsGridLoad();
                this.setSkillProgName("--Select--");
                this.setMinSkilSet("--Select--");
                this.setSaveSkillFlag(false);
                this.setEditSkillFlag(true);
                this.setDelSkillFlag(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteSkillsRecord() {
        try {
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
            if (this.skillProgName.equalsIgnoreCase("--Select--")) {
                this.setSkillErrorMessage("Please select program name.");
                return;
            }
            if (this.minSkilSet.equalsIgnoreCase("--Select--")) {
                this.setSkillErrorMessage("Please select minimum skill set.");
                return;
            }
            ProgramDetailsDelegate progDtDel = new ProgramDetailsDelegate();
            String result = progDtDel.deleteSkillDetails(Integer.parseInt(this.compCode), currentItem3.getProgCode(), currentItem3.structCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setSkillErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setSkillErrorMessage("Record not deleted.");
                return;
            } else {
                this.setSkillmessage("Record deleted succesfully.");
                skillsGridLoad();
                this.setSkillProgName("--Select--");
                this.setMinSkilSet("--Select--");
                this.setSaveSkillFlag(false);
                this.setEditSkillFlag(true);
                this.setDelSkillFlag(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetSkillRecord() {
        try {
            this.setSkillErrorMessage("");
            this.setSkillmessage("");
            skillsGridLoad();
            this.setSkillProgName("--Select--");
            this.setMinSkilSet("--Select--");
            this.setSaveSkillFlag(false);
            this.setEditSkillFlag(true);
            this.setDelSkillFlag(true);
            gridDetail3 = new ArrayList<SkillGrid>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
