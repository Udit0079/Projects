package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.AlmAccountClassificationGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class ALMAccountsClassification extends BaseBean {

    private OtherMasterFacadeRemote otherMasterRemote;
    private String userName = getUserName();
    private String todayDate = getTodayDate();
    private String errorMessage;
    private String message;
    private List<SelectItem> flowList;
    private String flow;
    private String headsOfAccounts;
    private List<SelectItem> headsOfAccountsList;
    private String bucketDesc;
    private List<SelectItem> bucketDescList;
    private String amount;
    private String remarks;
    private String function;
    private List<SelectItem> functionList;
    private boolean flag1;
    private boolean fieldDisFlag;
    private boolean btnDisFlag1;
    private boolean btnDisFlag2;
    private List<AlmAccountClassificationGrid> gridDetail;
    int currentRow;
    private AlmAccountClassificationGrid currentItem = new AlmAccountClassificationGrid();

    public ALMAccountsClassification() {
        try {
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            this.setMessage("");
            this.setFlag1(true);
            this.setFieldDisFlag(true);
            this.setBtnDisFlag1(true);
            this.setBtnDisFlag2(true);
            gridDetail = new ArrayList<AlmAccountClassificationGrid>();
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("--Select--"));
            functionList.add(new SelectItem("ADD"));
            functionList.add(new SelectItem("MODIFY"));
            flowList = new ArrayList<SelectItem>();
            flowList.add(new SelectItem("--Select--"));
            flowList.add(new SelectItem("OUTFLOW"));
            flowList.add(new SelectItem("INFLOW"));
            headsOfAccountsList = new ArrayList<SelectItem>();
            headsOfAccountsList.add(new SelectItem("--Select--"));
            bucketDescriptionCombo();
            gridLoad();
            this.setErrorMessage("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void functionMethod() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Function.");
                this.setBucketDesc("--Select--");
                this.setHeadsOfAccounts("--Select--");
                this.setFlow("--Select--");
                this.setAmount("");
                this.setRemarks("");
                this.setFieldDisFlag(true);
                this.setFlag1(true);
                gridLoad();
                return;
            }
            if (this.function.equalsIgnoreCase("ADD")) {
                gridDetail = new ArrayList<AlmAccountClassificationGrid>();
                this.setFieldDisFlag(false);
                this.setFlag1(true);
                this.setBtnDisFlag1(false);
                this.setBtnDisFlag2(true);
            } else {
                this.setFlag1(false);
                this.setFieldDisFlag(false);
                this.setBtnDisFlag1(true);
                gridLoad();
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            List resultList = new ArrayList();
            this.setFlow(currentItem.getFlow());
            if (currentItem.getFlow().equalsIgnoreCase("OUTFLOW")) {
                resultList = otherMasterRemote.headOfAccountsOutFlow();
                if (resultList.isEmpty()) {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                } else {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        headsOfAccountsList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    }
                }
            } else {
                resultList = otherMasterRemote.headOfAccountsInFlow();
                if (resultList.isEmpty()) {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                } else {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        headsOfAccountsList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    }
                }
            }
            String headOfAc = otherMasterRemote.hoDescriptionFromGrid(currentItem.getFlow(), currentItem.getHeadsOfAccounts());
            this.setHeadsOfAccounts(headOfAc);
            String buckDesc = otherMasterRemote.bucketDescriptionFromGrid(currentItem.getBucketDesc());
            this.setBucketDesc(buckDesc);
            this.setAmount(currentItem.getPercentageAmt());
            this.setRemarks(currentItem.getRemarks());
            this.setBtnDisFlag2(false);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void flowChangeMethod() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.flow.equalsIgnoreCase("--Select--")) {
                headsOfAccountsList = new ArrayList<SelectItem>();
                headsOfAccountsList.add(new SelectItem("--Select--"));
                if (function.equals("ADD")) {
                    this.setErrorMessage("Please Select Flow.");
                }
                return;
            }
            List resultList = new ArrayList();
            if (this.flow.equalsIgnoreCase("OUTFLOW")) {
                resultList = otherMasterRemote.headOfAccountsOutFlow();
                if (resultList.isEmpty()) {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                } else {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        headsOfAccountsList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    }
                }
            } else {
                resultList = otherMasterRemote.headOfAccountsInFlow();
                if (resultList.isEmpty()) {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                } else {
                    headsOfAccountsList = new ArrayList<SelectItem>();
                    headsOfAccountsList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        headsOfAccountsList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void bucketDescriptionCombo() {
        try {
            List resultList = new ArrayList();
            resultList = otherMasterRemote.bucketDescCombo();
            if (resultList.isEmpty()) {
                bucketDescList = new ArrayList<SelectItem>();
                bucketDescList.add(new SelectItem("--Select--"));
                this.setErrorMessage("No Bucket Description/No. Exists");
                return;
            } else {
                bucketDescList = new ArrayList<SelectItem>();
                bucketDescList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    bucketDescList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void loadGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.flow.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Flow.");
                return;
            }
            if (this.headsOfAccounts.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Head Of Accounts.");
                return;
            }
            if (this.bucketDesc.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bucket Description.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.amount == null || this.amount.equalsIgnoreCase("") || this.amount.length() == 0) {
                this.setErrorMessage("Please Enter Percentage Of Amount.");
                return;
            }
            Matcher amountCheck = p.matcher(amount);
            if (!amountCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In Amount.");
                return;
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Numeric Value In Amount.");
                    return;
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Enter Numeric Value In Amount Upto Two Decimal Places.");
                    return;
                }
            }
            if (btnDisFlag2 == true) {
                AlmAccountClassificationGrid bucketBean = createGridDetailObj();
                gridDetail.add(bucketBean);
                this.setFlow("--Select--");
                this.setHeadsOfAccounts("--Select--");
                this.setBucketDesc("--Select--");
                this.setAmount("");
                this.setRemarks("");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private AlmAccountClassificationGrid createGridDetailObj() {
        AlmAccountClassificationGrid bucketBean = new AlmAccountClassificationGrid();
        try {
            bucketBean.setFlow(this.flow);
            if (this.flow.equalsIgnoreCase("OUTFLOW")) {
                String headOfAc = otherMasterRemote.headAcDescForOF(this.headsOfAccounts);
                bucketBean.setHeadsOfAccounts(headOfAc);
            } else {
                String headOfAc = otherMasterRemote.headAcDescForIF(this.headsOfAccounts);
                bucketBean.setHeadsOfAccounts(headOfAc);
            }
            String buckDesc = otherMasterRemote.bucketDescriptionGrid(this.bucketDesc);
            bucketBean.setBucketDesc(buckDesc);
            bucketBean.setPercentageAmt(this.amount);
            bucketBean.setRemarks(this.remarks);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return bucketBean;
    }

    public void saveAlmActClassification() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            List arraylist = new ArrayList();
            if (gridDetail.isEmpty()) {
                this.setErrorMessage("Please Enter Record.");
                return;
            }
            String a[][] = new String[gridDetail.size()][5];
            for (int i = 0; i < gridDetail.size(); i++) {
                a[i][0] = gridDetail.get(i).getFlow().toString();
                a[i][1] = gridDetail.get(i).getHeadsOfAccounts().toString();
                a[i][2] = gridDetail.get(i).getBucketDesc().toString();
                a[i][3] = gridDetail.get(i).getPercentageAmt().toString();
                a[i][4] = gridDetail.get(i).getRemarks().toString();
            }
            for (int i = 0; i < gridDetail.size(); i++) {
                for (int j = 0; j < 5; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            if ((arraylist.isEmpty()) || (arraylist == null)) {
                this.setErrorMessage("Please Enter Record.");
                return;
            }
            String result = otherMasterRemote.saveALMActClassification(arraylist, this.userName, this.todayDate);
            if (result == null) {
                this.setErrorMessage("NOT SAVED !!!");
            } else {
                if (result.equals("true")) {
                    this.setMessage("DATA SAVED SUCCESFULLY.");
                    gridDetail.clear();
                    arraylist.clear();
                    this.setBucketDesc("--Select--");
                    this.setHeadsOfAccounts("--Select--");
                    this.setAmount("");
                    this.setRemarks("");
                    this.setFlow("--Select--");
                    this.setFunction("--Select--");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                    this.setErrorMessage("");
                } else {
                    gridDetail.clear();
                    arraylist.clear();
                    this.setBucketDesc("--Select--");
                    this.setHeadsOfAccounts("--Select--");
                    this.setAmount("");
                    this.setRemarks("");
                    this.setFlow("--Select--");
                    this.setFunction("--Select--");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                    this.setErrorMessage(result);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateAlmActClassification() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.flow.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Flow.");
                return;
            }
            if (this.headsOfAccounts.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Head Of Accounts.");
                return;
            }
            if (this.bucketDesc.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bucket Description.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.amount == null || this.amount.equalsIgnoreCase("") || this.amount.length() == 0) {
                this.setErrorMessage("Please Enter Percentage Of Amount.");
                return;
            }
            Matcher amountCheck = p.matcher(amount);
            if (!amountCheck.matches()) {
                this.setErrorMessage("Please Enter Numeric Value In Amount.");
                return;
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Numeric Value In Amount.");
                    return;
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Enter Numeric Value In Amount Upto Two Decimal Places.");
                    return;
                }
            }
            String result = otherMasterRemote.updateALMActClassification(this.userName, this.todayDate, currentItem.getFlow().substring(0, 1), currentItem.getHeadsOfAccounts(), currentItem.getBucketDesc(), Float.parseFloat(currentItem.getPercentageAmt()), currentItem.getRemarks(), this.flow.substring(0, 1), this.headsOfAccounts, this.bucketDesc, Float.parseFloat(amount), this.remarks);
            if (result == null) {
                this.setErrorMessage("NOT UPDATED !!!");
            } else {
                if (result.equals("true")) {
                    this.setMessage("DATA UPDATED SUCCESFULLY.");
                    this.setBucketDesc("--Select--");
                    this.setHeadsOfAccounts("--Select--");
                    this.setAmount("");
                    this.setRemarks("");
                    this.setFunction("--Select--");
                    this.setFlow("--Select--");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                    this.setErrorMessage("");
                } else {
                    this.setBucketDesc("--Select--");
                    this.setHeadsOfAccounts("--Select--");
                    this.setAmount("");
                    this.setRemarks("");
                    this.setFlow("--Select--");
                    this.setFunction("--Select--");
                    this.setFieldDisFlag(true);
                    this.setFlag1(true);
                    this.setBtnDisFlag1(true);
                    this.setBtnDisFlag2(true);
                    gridLoad();
                    this.setErrorMessage(result);
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
            this.setMessage("");
            this.setBucketDesc("--Select--");
            this.setFunction("--Select--");
            this.setHeadsOfAccounts("--Select--");
            this.setAmount("");
            this.setRemarks("");
            this.setFlow("--Select--");
            this.setFieldDisFlag(true);
            this.setFlag1(true);
            this.setBtnDisFlag1(true);
            this.setBtnDisFlag2(true);
            gridLoad();
            this.setErrorMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<AlmAccountClassificationGrid>();
        try {
            List resultList = new ArrayList();
            resultList = otherMasterRemote.almActClassGridLoad();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    AlmAccountClassificationGrid detail = new AlmAccountClassificationGrid();
                    if (ele.get(0).toString().equalsIgnoreCase("O")) {
                        detail.setFlow("OUTFLOW");
                    } else {
                        detail.setFlow("INFLOW");
                    }
                    try {
                        if (ele.get(0).toString().equalsIgnoreCase("O")) {
                            String headOfAc = otherMasterRemote.headAcDescForOF(ele.get(1).toString());
                            detail.setHeadsOfAccounts(headOfAc);
                        } else {
                            String headOfAc = otherMasterRemote.headAcDescForIF(ele.get(1).toString());
                            detail.setHeadsOfAccounts(headOfAc);
                        }
                        String buckDesc = otherMasterRemote.bucketDescriptionGrid(ele.get(2).toString());
                        detail.setBucketDesc(buckDesc);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    detail.setPercentageAmt(ele.get(3).toString());
                    detail.setRemarks(ele.get(4).toString());
                    gridDetail.add(detail);
                }
                setErrorMessage("Please select a row from table to update.");
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtn() {
        try {
            resetForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getBucketDesc() {
        return bucketDesc;
    }

    public void setBucketDesc(String bucketDesc) {
        this.bucketDesc = bucketDesc;
    }

    public List<SelectItem> getBucketDescList() {
        return bucketDescList;
    }

    public void setBucketDescList(List<SelectItem> bucketDescList) {
        this.bucketDescList = bucketDescList;
    }

    public AlmAccountClassificationGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AlmAccountClassificationGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isFieldDisFlag() {
        return fieldDisFlag;
    }

    public void setFieldDisFlag(boolean fieldDisFlag) {
        this.fieldDisFlag = fieldDisFlag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public List<SelectItem> getFlowList() {
        return flowList;
    }

    public void setFlowList(List<SelectItem> flowList) {
        this.flowList = flowList;
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

    public List<AlmAccountClassificationGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AlmAccountClassificationGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getHeadsOfAccounts() {
        return headsOfAccounts;
    }

    public void setHeadsOfAccounts(String headsOfAccounts) {
        this.headsOfAccounts = headsOfAccounts;
    }

    public List<SelectItem> getHeadsOfAccountsList() {
        return headsOfAccountsList;
    }

    public void setHeadsOfAccountsList(List<SelectItem> headsOfAccountsList) {
        this.headsOfAccountsList = headsOfAccountsList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
