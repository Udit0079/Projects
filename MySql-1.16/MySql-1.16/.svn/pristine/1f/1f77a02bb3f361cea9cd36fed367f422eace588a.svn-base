/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  11 NOV 2010
 */
package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.pojo.BucketParameterGrid;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public final class BucketParameter extends BaseBean {

    private int currentRow;
    private String errorMessage;
    private String message;
    private String bucketNo;
    private String bucketDesc;
    private String startDay;
    private String endDay;
    private String function;
    private String parameter;
    private boolean flag1;
    private boolean fieldDisFlag;
    private boolean btnDisFlag1;
    private boolean btnDisFlag2;
    private List<SelectItem> functionList;
    private List<SelectItem> parameterList;
    private List<BucketParameterGrid> gridDetail;
    private BucketParameterGrid currentItem = new BucketParameterGrid();
    private GeneralMasterFacadeRemote glMasterRemote;
    private OtherMasterFacadeRemote otherMasterRemote;

    public String getBucketDesc() {
        return bucketDesc;
    }

    public void setBucketDesc(String bucketDesc) {
        this.bucketDesc = bucketDesc;
    }

    public String getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(String bucketNo) {
        this.bucketNo = bucketNo;
    }

    public BucketParameterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(BucketParameterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<BucketParameterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BucketParameterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public boolean isFieldDisFlag() {
        return fieldDisFlag;
    }

    public void setFieldDisFlag(boolean fieldDisFlag) {
        this.fieldDisFlag = fieldDisFlag;
    }

    public boolean isBtnDisFlag1() {
        return btnDisFlag1;
    }

    public void setBtnDisFlag1(boolean btnDisFlag1) {
        this.btnDisFlag1 = btnDisFlag1;
    }

    public boolean isBtnDisFlag2() {
        return btnDisFlag2;
    }

    public void setBtnDisFlag2(boolean btnDisFlag2) {
        this.btnDisFlag2 = btnDisFlag2;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public List<SelectItem> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<SelectItem> parameterList) {
        this.parameterList = parameterList;
    }

    public BucketParameter() {
        try {
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            glMasterRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            this.setErrorMessage("");
            this.setMessage("");
            gridDetail = new ArrayList<BucketParameterGrid>();
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("--Select--"));
            functionList.add(new SelectItem("ADD"));
            functionList.add(new SelectItem("MODIFY"));
            this.setFlag1(true);
            this.setFieldDisFlag(true);
            this.setBtnDisFlag1(true);
            this.setBtnDisFlag2(true);
            loadProfileParameter();
            this.setParameter(parameter);
            gridLoad();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void loadProfileParameter() {
        parameterList = new ArrayList<SelectItem>();
        try {
            List list = glMasterRemote.getRefCodeAndDescByNo("006");
            if (list.isEmpty()) {
                setErrorMessage("Please fill profile parameter in CBS REF REC TYPE for 006.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                parameterList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bucketNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (BucketParameterGrid item : gridDetail) {
                if (item.getBucketNo().equalsIgnoreCase(ac)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setBucketNo(currentItem.getBucketNo());
            this.setBucketDesc(currentItem.getBucketDesc());
            this.setStartDay(currentItem.getStartDay());
            this.setEndDay(currentItem.getEndDay());
            this.setParameter(currentItem.getProfileParameter());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<BucketParameterGrid>();
            List resultList = new ArrayList();
            resultList = otherMasterRemote.bucketGridLoad();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    BucketParameterGrid detail = new BucketParameterGrid();
                    detail.setBucketNo(ele.get(0).toString());
                    detail.setBucketDesc(ele.get(1).toString());
                    detail.setStartDay(ele.get(2).toString());
                    detail.setEndDay(ele.get(3).toString());
                    detail.setProfileParameter(ele.get(4).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void loadGrid() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.bucketNo == null || this.bucketNo.equalsIgnoreCase("") || this.bucketNo.length() == 0) {
                this.setErrorMessage("Please Enter Bucket No.");
                return;
            }
            Matcher bucketNoCheck = p.matcher(bucketNo);
            if (!bucketNoCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In Bucket No.");
                return;
            }
            if (this.bucketNo.contains(".")) {
                this.setErrorMessage("Please Enter Numeric Value In Bucket No.");
                return;
            }
            if (this.startDay == null || this.startDay.equalsIgnoreCase("") || this.startDay.length() == 0) {
                this.setErrorMessage("Please Enter Start Day.");
                return;
            }
            Matcher startDayCheck = p.matcher(startDay);
            if (!startDayCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In Start Day.");
                return;
            }
            if (this.startDay.contains(".")) {
                this.setErrorMessage("Please Enter Numeric Value In Start Day.");
                return;
            }
            if (this.endDay == null || this.endDay.equalsIgnoreCase("") || this.endDay.length() == 0) {
                this.setErrorMessage("Please Enter Start Day.");
                return;
            }
            Matcher endDayCheck = p.matcher(endDay);
            if (!endDayCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In End Day.");
                return;
            }
            if (this.endDay.contains(".")) {
                this.setErrorMessage("Please Enter Numeric Value In End Day.");
                return;
            }
            if (Integer.parseInt(this.endDay) < Integer.parseInt(this.startDay)) {
                this.setErrorMessage("End Day Cannot Be Less Than Start Day.");
                return;
            }
            if (btnDisFlag2 == true) {
                BucketParameterGrid bucketBean = createGridDetailObj();
                gridDetail.add(bucketBean);
                this.setBucketNo("");
                this.setBucketDesc("");
                this.setStartDay("");
                this.setEndDay("");
                this.setParameter(parameter);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private BucketParameterGrid createGridDetailObj() {
        BucketParameterGrid bucketBean = new BucketParameterGrid();
        try {
            bucketBean.setBucketNo(bucketNo);
            bucketBean.setBucketDesc(bucketDesc);
            bucketBean.setStartDay(startDay);
            bucketBean.setEndDay(endDay);
            bucketBean.setProfileParameter(parameter);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return bucketBean;
    }

    public void functionMethod() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Function.");
                this.setBucketDesc("");
                this.setBucketNo("");
                this.setStartDay("");
                this.setEndDay("");
                this.setFieldDisFlag(true);
                this.setFlag1(true);
                gridLoad();
                return;
            }
            if (this.function.equalsIgnoreCase("ADD")) {
                gridDetail = new ArrayList<BucketParameterGrid>();
                this.setFieldDisFlag(false);
                this.setFlag1(true);
                this.setBtnDisFlag1(false);
                this.setBtnDisFlag2(true);
            } else {
                this.setFlag1(false);
                this.setFieldDisFlag(false);
                this.setBtnDisFlag2(false);
                this.setBtnDisFlag1(true);
                gridLoad();
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setErrorMessage("Please Enter Record.");
                return;
            }
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = otherMasterRemote.saveBucketParameter(gridDetail, ymd.format(dmy.parse(getTodayDate())), getUserName());
            if (result == null) {
                this.setErrorMessage("NOT SAVED !!!");
            } else {
                if (result.equals("true")) {
                    this.setMessage("DATA SAVED SUCCESFULLY.");
                    gridDetail.clear();
                    this.setBucketDesc("");
                    this.setBucketNo("");
                    this.setStartDay("");
                    this.setEndDay("");
                    this.setFunction("--Select--");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    gridDetail.clear();
                    this.setBucketDesc("");
                    this.setBucketNo("");
                    this.setStartDay("");
                    this.setEndDay("");
                    this.setFunction("--Select--");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void updateBucketParameter() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.bucketNo.equalsIgnoreCase("") || this.bucketNo.length() == 0) {
                this.setErrorMessage("Please Enter Bucket No.");
                return;
            }
            Matcher bucketNoCheck = p.matcher(bucketNo);
            if (!bucketNoCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In Bucket No.");
                return;
            }
            if (this.startDay.equalsIgnoreCase("") || this.startDay.length() == 0) {
                this.setErrorMessage("Please Enter Start Day.");
                return;
            }
            Matcher startDayCheck = p.matcher(startDay);
            if (!startDayCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In Start Day.");
                return;
            }
            if (this.endDay.equalsIgnoreCase("") || this.endDay.length() == 0) {
                this.setErrorMessage("Please Enter Start Day.");
                return;
            }
            Matcher endDayCheck = p.matcher(endDay);
            if (!endDayCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In End Day.");
                return;
            }
            String result = otherMasterRemote.updateBucketParameter(getTodayDate(), getUserName(), Integer.parseInt(currentItem.getBucketNo()), Integer.parseInt(this.bucketNo), this.bucketDesc, Integer.parseInt(this.startDay), Integer.parseInt(this.endDay), this.parameter);
            if (result == null) {
                this.setErrorMessage("NOT UPDATED !!!");
            } else {
                if (result.equals("true")) {
                    this.setMessage("DATA UPDATED SUCCESFULLY.");
                    this.setBucketDesc("");
                    this.setBucketNo("");
                    this.setStartDay("");
                    this.setFunction("--Select--");
                    this.setEndDay("");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    this.setBucketDesc("");
                    this.setBucketNo("");
                    this.setFunction("--Select--");
                    this.setStartDay("");
                    this.setEndDay("");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFunction("--Select--");
            this.setBucketDesc("");
            this.setBucketNo("");
            this.setStartDay("");
            this.setEndDay("");
            this.setFieldDisFlag(true);
            this.setFlag1(true);
            this.setBtnDisFlag1(true);
            this.setBtnDisFlag2(true);
            gridLoad();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitBtn() {
        resetForm();
        return "case1";
    }
}
