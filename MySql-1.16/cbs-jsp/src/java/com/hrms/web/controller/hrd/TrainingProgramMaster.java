/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  20 JUNE 2011
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.TrainingProgMstGrid;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrMstTrngProgramPKTO;
import com.hrms.common.to.HrMstTrngProgramTO;
import com.hrms.web.delegate.TrainingProgramMasterDelegate;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.regex.Pattern;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingProgramMaster extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(TrainingProgramMaster.class);
    private String errorMessage;
    private String message;
    private String compCode="0";
    private String trainingName;
    private List<SelectItem> trainingNameList;
    private String progName;
    private List<SelectItem> progNameList;
    private boolean saveFlag;
    private boolean editFlag;
    private boolean delFlag;
    private List<TrainingProgMstGrid> gridDetail;
    int currentRow;
    private TrainingProgMstGrid currentItem = new TrainingProgMstGrid();
    private Integer defaultComp;

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public TrainingProgMstGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TrainingProgMstGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<TrainingProgMstGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TrainingProgMstGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getCompanyCode() {
        return compCode;
    }

    public void setCompanyCode(String compCode) {
        this.compCode = compCode;
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

    
    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public List<SelectItem> getProgNameList() {
        return progNameList;
    }

    public void setProgNameList(List<SelectItem> progNameList) {
        this.progNameList = progNameList;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public List<SelectItem> getTrainingNameList() {
        return trainingNameList;
    }

    public void setTrainingNameList(List<SelectItem> trainingNameList) {
        this.trainingNameList = trainingNameList;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-/][a-zA-Z0-9,]+)*");
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of TrainingProgramMaster */
    public TrainingProgramMaster() {

        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            comboOnLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void comboOnLoad() {
        try {
            List resultLt = new ArrayList();
            List resultLt1 = new ArrayList();
            TrainingProgramMasterDelegate trngProgMstDel = new TrainingProgramMasterDelegate();
            resultLt = trngProgMstDel.trainingNameList(Integer.parseInt(compCode));
            resultLt1 = trngProgMstDel.progNameList(Integer.parseInt(compCode));
            trainingNameList = new ArrayList<SelectItem>();
            trainingNameList.add(new SelectItem("0","--Select--"));
            progNameList = new ArrayList<SelectItem>();
            progNameList.add(new SelectItem("0","--Select--"));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    trainingNameList.add(new SelectItem(result[0].toString(), result[1].toString()));
                }
            }
            if (!resultLt1.isEmpty()) {
                Iterator i1 = resultLt1.iterator();
                while (i1.hasNext()) {
                    Object[] result1 = (Object[]) i1.next();
                    progNameList.add(new SelectItem(result1[0].toString(), result1[1].toString()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method comboOnLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method comboOnLoad()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method comboOnLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.trainingName.equalsIgnoreCase("0")) {
                this.setErrorMessage("Please select training name.");
                return;
            }
            if (this.progName.equalsIgnoreCase("0")) {
                this.setErrorMessage("Please select program name.");
                return;
            }
            Date date = new Date();
            HrMstTrngProgramTO trngProgObj = new HrMstTrngProgramTO();
            HrMstTrngProgramPKTO trngProgObjPK = new HrMstTrngProgramPKTO();
            trngProgObjPK.setCompCode(Integer.parseInt(this.compCode));
            trngProgObjPK.setProgCode(this.progName);
            trngProgObjPK.setTrngCode(this.trainingName);
            trngProgObj.setHrMstTrngProgramPKTO(trngProgObjPK);
            trngProgObj.setDefaultComp(defaultComp);
            trngProgObj.setStatFlag("N");
            trngProgObj.setStatUpFlag("Y");
            trngProgObj.setModDate(date);
            trngProgObj.setAuthBy("");
            trngProgObj.setEnteredBy(getUserName());
            TrainingProgramMasterDelegate trngProgMstDel = new TrainingProgramMasterDelegate();
            String result = trngProgMstDel.saveTrainingDetail(trngProgObj);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setErrorMessage("Record not saved.");
                return;
            } else if (result.equalsIgnoreCase("false1")) {
                this.setErrorMessage("Record already exists.");
                return;
            } else {
                this.setMessage("Record saved succesfully.");
                this.setTrainingName("0");
                this.setProgName("0");
                this.setSaveFlag(false);
                this.setEditFlag(true);
                this.setDelFlag(true);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveRecord()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveRecord()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveRecord()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void trainingMasterGridLoad() {
        try {
            if (this.trainingName.equalsIgnoreCase("0")) {
                this.setErrorMessage("Please select training name.");
                return;
            }
            gridDetail = new ArrayList<TrainingProgMstGrid>();
            List resultLt = new ArrayList();
            TrainingProgramMasterDelegate trngProgMstDel = new TrainingProgramMasterDelegate();
            resultLt = trngProgMstDel.trainingProgramGridDetail(Integer.parseInt(compCode), this.trainingName);
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TrainingProgMstGrid rd = new TrainingProgMstGrid();
                    rd.setProgCode(result[0].toString());
                    rd.setProgname(result[1].toString());
                    rd.setTrainingCode(result[2].toString());
                    gridDetail.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method trainingMasterGridLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method trainingMasterGridLoad()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingMasterGridLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("progCode"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (TrainingProgMstGrid item : gridDetail) {
            if (item.getProgCode().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setSaveFlag(true);
            this.setEditFlag(false);
            this.setDelFlag(false);
            this.setErrorMessage("");
            this.setMessage("");
            this.setProgName(currentItem.getProgCode());
            this.setTrainingName(currentItem.getTrainingCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.trainingName.equalsIgnoreCase("0")) {
                this.setErrorMessage("Please select training name.");
                return;
            }
            if (this.progName.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select program name.");
                return;
            }
            HrMstTrngProgramPKTO trngProgObjPK = new HrMstTrngProgramPKTO();
            trngProgObjPK.setCompCode(Integer.parseInt(this.compCode));
            trngProgObjPK.setProgCode(this.progName);
            trngProgObjPK.setTrngCode(this.trainingName);
            TrainingProgramMasterDelegate trngProgMstDel = new TrainingProgramMasterDelegate();
            String result = trngProgMstDel.deleteTrainingDetail(trngProgObjPK);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setErrorMessage("Record not deleted.");
                return;
            } else {
                this.setMessage("Record deleted succesfully.");
                this.setTrainingName("0");
                this.setProgName("0");
                this.setSaveFlag(false);
                this.setEditFlag(true);
                this.setDelFlag(true);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteRecord()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteRecord()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteRecord()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setTrainingName("0");
            this.setProgName("0");
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            gridDetail = new ArrayList<TrainingProgMstGrid>();
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
}
