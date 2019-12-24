package com.cbs.web.controller.master;

import com.cbs.dto.ReferenceCodeMasterTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public final class ReferenceCodeMaster extends BaseBean {

    String refRecNo;
    String refCode;
    String refDesc;
    String createByUserid;
    String creationDate;
    String lastUpdatedBy;
    String lastUpdateDate;
    int modifications;
    String functionFlag;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    boolean disable;
    String status = "I";
    int i = 0;
    String message;
    BankAndLoanMasterFacadeRemote testEJB;
    String show;
    boolean flag1;
    boolean flag2;
    boolean flag3;
    private List<SelectItem> functionOption;
    private List<ReferenceCodeMasterTable> codeMasterList;
    private List<ReferenceCodeMasterTable> codeMasterLstTmp = new ArrayList<ReferenceCodeMasterTable>();
    int selectCountCodeMaster = 0;
    int currentRowCodeMaster;
    ReferenceCodeMasterTable currentItemCodeMaster = new ReferenceCodeMasterTable();
    int count1CodeMaster = 0;
    int count2CodeMaster;
    String preRefRecNo = "";

    public List<SelectItem> getFunctionOption() {
        return functionOption;
    }

    public void setFunctionOption(List<SelectItem> functionOption) {
        this.functionOption = functionOption;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
    }

    public List<ReferenceCodeMasterTable> getCodeMasterList() {
        return codeMasterList;
    }

    public void setCodeMasterList(List<ReferenceCodeMasterTable> codeMasterList) {
        this.codeMasterList = codeMasterList;
    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateByUserid() {
        return createByUserid;
    }

    public void setCreateByUserid(String createByUserid) {
        this.createByUserid = createByUserid;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getModifications() {
        return modifications;
    }

    public void setModifications(int modifications) {
        this.modifications = modifications;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefDesc() {
        return refDesc;
    }

    public void setRefDesc(String refDesc) {
        this.refDesc = refDesc;
    }

    public String getRefRecNo() {
        return refRecNo;
    }

    public void setRefRecNo(String refRecNo) {
        this.refRecNo = refRecNo;
    }

    public ReferenceCodeMasterTable getCurrentItemCodeMaster() {
        return currentItemCodeMaster;
    }

    public void setCurrentItemCodeMaster(ReferenceCodeMasterTable currentItemCodeMaster) {
        this.currentItemCodeMaster = currentItemCodeMaster;
    }

    public int getCurrentRowCodeMaster() {
        return currentRowCodeMaster;
    }

    public void setCurrentRowCodeMaster(int currentRowCodeMaster) {
        this.currentRowCodeMaster = currentRowCodeMaster;
    }

    public ReferenceCodeMaster() {
        try {
            testEJB = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            codeMasterList = new ArrayList<ReferenceCodeMasterTable>();
            functionOption = new ArrayList<SelectItem>();
            functionOption.add(new SelectItem("A", "ADD"));
            functionOption.add(new SelectItem("M", "MODIFY"));
            functionOption.add(new SelectItem("I", "INQUIRY"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRowCodeMaster(ActionEvent event) {
        try {
            String SNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Ref Rec No"));
            currentRowCodeMaster = currentRowCodeMaster + 1;
            currentRowCodeMaster = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (ReferenceCodeMasterTable dataItem : codeMasterList) {
                if (dataItem.getRefRecNoTab().equals(SNo)) {
                    currentItemCodeMaster = dataItem;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setDataInCodeMasterTable() {
        try {
            List<ReferenceCodeMasterTable> codeMasterTemp = codeMasterList;
            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("A")) {
                if (selectCountCodeMaster == 0) {
                    if (!refRecNo.equalsIgnoreCase("") && (!refCode.equalsIgnoreCase(""))) {
                        for (int m = 0; m < codeMasterTemp.size(); m++) {
                            String refCodeTmp = codeMasterTemp.get(m).getRefCodeTab();
                            if (refCodeTmp.equalsIgnoreCase(refCode)) {
                                setMessage("You can't insert same code in two different Reference No");
                                return;
                            }
                        }
                    } else {
                        setMessage("PleaseInsert Ref Rec No and Ref Code");
                        return;
                    }
                }
            }
            ReferenceCodeMasterTable codeMaster = new ReferenceCodeMasterTable();
            codeMaster.setRefRecNoTab(getRefRecNo());
            codeMaster.setRefCodeTab(getRefCode());
            codeMaster.setRefDescTab(getRefDesc());
            codeMaster.setCreateByUseridTab(getUserName());
            codeMaster.setCreationDateTab(getTodayDate());
            codeMaster.setLastUpdatedByTab(getUserName());
            codeMaster.setLastUpdateDateTab(getTodayDate());
            codeMaster.setModificationsTab(getModifications());
            if (functionFlag.equalsIgnoreCase("A")) {
                codeMaster.setCounterSaveUpdate("Save");
                clearFieldCodeMaster();
            }
            codeMasterList.add(codeMaster);
            if (functionFlag.equalsIgnoreCase("M")) {
                if (selectCountCodeMaster != 1) {
                    for (int j = 0; j < codeMasterTemp.size(); j++) {
                        String refCodeTmp1 = codeMasterTemp.get(j).getRefCodeTab();
                        if (!refCodeTmp1.equalsIgnoreCase(refCode)) {
                            codeMaster.setCounterSaveUpdate("save");
                            codeMasterLstTmp.add(codeMaster);
                            clearFieldCodeMaster();
                            return;
                        }
                    }
                }
                if (currentItemCodeMaster.getRefCodeTab().equalsIgnoreCase(refCode)) {
                    if (!currentItemCodeMaster.getRefDescTab().equalsIgnoreCase(refDesc)) {
                        codeMaster.setCounterSaveUpdate("Update");
                        clearFieldCodeMaster();
                        codeMasterLstTmp.add(codeMaster);
                    }
                    selectCountCodeMaster = 0;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void selectFromCodeMasterTable() {
        try {
            setMessage("");
            selectCountCodeMaster = 1;
            if ((!refCode.equalsIgnoreCase("0") && currentItemCodeMaster.getRefCodeTab() != null)) {
                if (!refCode.equalsIgnoreCase("")) {
                    if (!refCode.equalsIgnoreCase(currentItemCodeMaster.getRefCodeTab())) {
                        count2CodeMaster = count1CodeMaster;
                        count1CodeMaster = count1CodeMaster + 1;
                        if (count2CodeMaster != count1CodeMaster) {
                            setDataInCodeMasterTable();
                        }
                    } else {
                        count1CodeMaster = 0;
                    }
                }
            }
            setRefRecNo(currentItemCodeMaster.getRefRecNoTab());
            setRefCode(currentItemCodeMaster.getRefCodeTab());
            setRefDesc(currentItemCodeMaster.getRefDescTab());
            codeMasterList.remove(currentRowCodeMaster);
            preRefRecNo = currentItemCodeMaster.getRefRecNoTab();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getDataInCodeMaster() {
        try {
            codeMasterList = new ArrayList<ReferenceCodeMasterTable>();
            List resultList = new ArrayList();
            resultList = testEJB.showDataCodeMasterTable(refRecNo);
            if (functionFlag.equalsIgnoreCase("I")) {
                setMessage("");
            }
            if (functionFlag.equalsIgnoreCase("M")) {
                setMessage("");
                clearFieldCodeMaster();
            }
            if (functionFlag.equalsIgnoreCase("A")) {
                setMessage("");
                clearFieldCodeMaster();
            }
            if (resultList.size() > 0) {
                for (int j = 0; j < resultList.size(); j++) {
                    Vector resultVec = (Vector) resultList.get(j);
                    ReferenceCodeMasterTable cm = new ReferenceCodeMasterTable();
                    cm.setRefRecNoTab(resultVec.get(0).toString());
                    cm.setRefCodeTab(resultVec.get(1).toString());
                    cm.setRefDescTab(resultVec.get(2).toString());
                    codeMasterList.add(cm);
                }
            } else {
                this.setMessage("There does not exist Any data");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setDataInCodeMaster() {
        try {
            List refMasterList = new ArrayList();
            setMessage("");
            if (functionFlag.equalsIgnoreCase("M")) {
                codeMasterList = new ArrayList<ReferenceCodeMasterTable>();
            }
            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I")) {
                refMasterList = testEJB.showDataCodeMasterTable(refRecNo);
                for (int j = 0; j < refMasterList.size(); j++) {
                    ReferenceCodeMasterTable rc = new ReferenceCodeMasterTable();
                    Vector refMasterVec = (Vector) refMasterList.get(j);
                    rc.setRefRecNoTab(refMasterVec.get(0).toString());
                    rc.setRefCodeTab(refMasterVec.get(1).toString());
                    rc.setRefDescTab(refMasterVec.get(2).toString());
                    rc.setCreateByUseridTab(refMasterVec.get(3).toString());
                    rc.setCreationDateTab(refMasterVec.get(4)== null ? "" : refMasterVec.get(4).toString());
                    rc.setLastUpdatedByTab(refMasterVec.get(5)== null ? "" : refMasterVec.get(5).toString());
                    rc.setLastUpdateDateTab(refMasterVec.get(6)== null ? "" : refMasterVec.get(6).toString());
                    rc.setModificationsTab(Integer.parseInt(refMasterVec.get(7)== null ? "0" : refMasterVec.get(7).toString()));
                    codeMasterList.add(rc);
                }
                clearFieldCodeMaster();
            }
            if (functionFlag.equalsIgnoreCase("M")) {
                clearFieldCodeMaster();
                setFlag1(true);
                setFlag2(true);
            } else if (functionFlag.equalsIgnoreCase("I")) {
                setFlag1(true);
                setFlag2(true);
                setFlag3(true);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveReferenceCodeMaster() {
        try {
            setMessage("");
            String msg = "";
            if (functionFlag.equalsIgnoreCase("A")) {
                msg = testEJB.saveReferenceCodeMaster(codeMasterList, ymd.format(sdf.parse(getTodayDate())), preRefRecNo);
                setMessage(msg);
            }
            if (functionFlag.equalsIgnoreCase("M")) {
                msg = testEJB.saveReferenceCodeMaster(codeMasterLstTmp, ymd.format(sdf.parse(getTodayDate())), preRefRecNo);
                setMessage(msg);
                codeMasterLstTmp.clear();
            }
            clearFieldCodeMaster();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clearFieldCodeMaster() {
        try {
            this.setRefRecNo("");
            this.setRefCode("");
            this.setRefDesc("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clearfun() {
        try {
            if (functionFlag.equalsIgnoreCase("I")) {
                setMessage("");
                setFlag1(false);
                codeMasterList.clear();
                clearFieldCodeMaster();
            }
            if (functionFlag.equalsIgnoreCase("M")) {
                setMessage("");
                setFlag1(false);
                codeMasterList.clear();
                clearFieldCodeMaster();
            }
            if (functionFlag.equalsIgnoreCase("A")) {
                setMessage("");
                setFlag1(false);
                setFlag2(false);
                setFlag3(false);
                codeMasterList.clear();
                clearFieldCodeMaster();
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            setFunctionFlag("A");
            setMessage("");
            codeMasterList = new ArrayList<>();
            setRefRecNo("");
            setRefCode("");
            setRefDesc("");
            flag1 = false;
            flag2 = false;
            flag3 = false;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        refresh();
        return "case1";
    }
}
