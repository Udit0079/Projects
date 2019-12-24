/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeDelinquencyDetailsTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.DeliquencyTable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DD {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;
    String daysPastDue;
    String deliqCycle;
    String delFlagDeliq;
    int currentRowDeliqDetails;
    int count1DeliqDetails = 0;
    int count2DeliqDetails;
    private boolean flagDeliqdias;
    private boolean ddFlag;
    boolean dpFlag;
    private String placeholder;
    private String provisionPercent;
    private int selectCountDeliqDetails = 0;
    private List<DeliquencyTable> deliqDetails;
    private List<DeliquencyTable> deliquencyDetailsTmp;
    private ArrayList<SelectItem> comboDeliqCycle;
    private ArrayList<SelectItem> comboDelFlagDeliq;
    private List<SelectItem> referenceTypeList;
    DeliquencyTable currentItemDeliqDetails = new DeliquencyTable();

    public int getSelectCountDeliqDetails() {
        return selectCountDeliqDetails;
    }

    public void setSelectCountDeliqDetails(int selectCountDeliqDetails) {
        this.selectCountDeliqDetails = selectCountDeliqDetails;
    }

    public List<DeliquencyTable> getDeliqDetails() {
        return deliqDetails;
    }

    public void setDeliqDetails(List<DeliquencyTable> deliqDetails) {
        this.deliqDetails = deliqDetails;
    }

    public boolean isDdFlag() {
        return ddFlag;
    }

    public void setDdFlag(boolean ddFlag) {
        this.ddFlag = ddFlag;
    }

    public ArrayList<SelectItem> getComboDelFlagDeliq() {
        return comboDelFlagDeliq;
    }

    public void setComboDelFlagDeliq(ArrayList<SelectItem> comboDelFlagDeliq) {
        this.comboDelFlagDeliq = comboDelFlagDeliq;
    }

    public ArrayList<SelectItem> getComboDeliqCycle() {
        return comboDeliqCycle;
    }

    public void setComboDeliqCycle(ArrayList<SelectItem> comboDeliqCycle) {
        this.comboDeliqCycle = comboDeliqCycle;
    }

    public int getCurrentRowDeliqDetails() {
        return currentRowDeliqDetails;
    }

    public void setCurrentRowDeliqDetails(int currentRowDeliqDetails) {
        this.currentRowDeliqDetails = currentRowDeliqDetails;
    }

    public String getDaysPastDue() {
        return daysPastDue;
    }

    public void setDaysPastDue(String daysPastDue) {
        this.daysPastDue = daysPastDue;
    }

    public String getDelFlagDeliq() {
        return delFlagDeliq;
    }

    public void setDelFlagDeliq(String delFlagDeliq) {
        this.delFlagDeliq = delFlagDeliq;
    }

    public String getDeliqCycle() {
        return deliqCycle;
    }

    public void setDeliqCycle(String deliqCycle) {
        this.deliqCycle = deliqCycle;
    }

    public boolean isFlagDeliqdias() {
        return flagDeliqdias;
    }

    public void setFlagDeliqdias(boolean flagDeliqdias) {
        this.flagDeliqdias = flagDeliqdias;
    }

    public List<SelectItem> getReferenceTypeList() {
        return referenceTypeList;
    }

    public void setReferenceTypeList(List<SelectItem> referenceTypeList) {
        this.referenceTypeList = referenceTypeList;
    }

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }

    public boolean isDpFlag() {
        return dpFlag;
    }

    public void setDpFlag(boolean dpFlag) {
        this.dpFlag = dpFlag;
    }

    public DeliquencyTable getCurrentItemDeliqDetails() {
        return currentItemDeliqDetails;
    }

    public void setCurrentItemDeliqDetails(DeliquencyTable currentItemDeliqDetails) {
        this.currentItemDeliqDetails = currentItemDeliqDetails;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getProvisionPercent() {
        return provisionPercent;
    }

    public void setProvisionPercent(String provisionPercent) {
        this.provisionPercent = provisionPercent;
    }

    /**
     * Creates a new instance of DD
     */
    public DD() {

        try {
            deliqDetails = new ArrayList<DeliquencyTable>();
            deliquencyDetailsTmp = new ArrayList<DeliquencyTable>();

            comboDelFlagDeliq = new ArrayList<SelectItem>();
            comboDelFlagDeliq.add(new SelectItem("0", ""));
            comboDelFlagDeliq.add(new SelectItem("Y", "YES"));
            comboDelFlagDeliq.add(new SelectItem("N", "NO"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> cbsRefRecTypeTOs = schemeMasterManagementDelegate.getCurrencyCode("207");
            if (cbsRefRecTypeTOs.size() > 0) {
                referenceTypeList = new ArrayList<SelectItem>();
                referenceTypeList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO cbsRefRecTypeTO : cbsRefRecTypeTOs) {
                    referenceTypeList.add(new SelectItem(cbsRefRecTypeTO.getCbsRefRecTypePKTO().getRefCode(), cbsRefRecTypeTO.getRefDesc()));
                }

            } else {
                referenceTypeList = new ArrayList<SelectItem>();
                referenceTypeList.add(new SelectItem("0", ""));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectDeliquencyDetails() {
        this.dpFlag = true;
        schemeMaster.setMessage("");
        selectCountDeliqDetails = 1;
        if ((!deliqCycle.equalsIgnoreCase("0") && currentItemDeliqDetails.getDeliqCycle() != null)) {
            if (!deliqCycle.equalsIgnoreCase("")) {
                if (!deliqCycle.equalsIgnoreCase(currentItemDeliqDetails.getDeliqCycle())) {
                    count2DeliqDetails = count1DeliqDetails;
                    count1DeliqDetails = count1DeliqDetails + 1;
                    if (count2DeliqDetails != count1DeliqDetails) {
                        setValueInDeliquencyDetailsTable();
                    }
                } else {
                    count1DeliqDetails = 0;
                }
            }
        }
        setDeliqCycle(currentItemDeliqDetails.getDeliqCycle());
        setDaysPastDue(currentItemDeliqDetails.getDaysPastDue());
        setPlaceholder(currentItemDeliqDetails.getPlaceInHolder());
        setProvisionPercent(currentItemDeliqDetails.getProvisionInPercent());
        setDelFlagDeliq(currentItemDeliqDetails.getDelFlagDeliq());
        deliqDetails.remove(currentRowDeliqDetails);
    }

    public void setValueInDeliquencyDetailsTable() {
        this.dpFlag = false;
        validateDeliquencyDetails();
        List<DeliquencyTable> deliqDetailsTemp = deliqDetails;
        if (schemeMaster.getFunctionFlag().equalsIgnoreCase("M") || schemeMaster.getFunctionFlag().equalsIgnoreCase("A")) {
            if (selectCountDeliqDetails == 0) {
                for (int m = 0; m < deliqDetailsTemp.size(); m++) {
                    String deliqCycleTmp = deliqDetailsTemp.get(m).getDeliqCycle();
                    if (deliqCycleTmp.equalsIgnoreCase(deliqCycle)) {
                        schemeMaster.setMessage("Please Select different Deliquency Cycle for same Scheme Code.");
                        return;
                    }
                }
            }

        }

        if (validateDeliquencyDetails().equalsIgnoreCase("false")) {
            return;
        }
        DeliquencyTable deliquency = new DeliquencyTable();
        deliquency.setDeliqCycle(getDeliqCycle());
        deliquency.setDaysPastDue(getDaysPastDue());
        deliquency.setPlaceInHolder(this.placeholder);
        deliquency.setProvisionInPercent(this.provisionPercent);
        deliquency.setDelFlagDeliq(getDelFlagDeliq());

        if (schemeMaster.getFunctionFlag().equalsIgnoreCase("A")) {
            setFlagDeliqdias(false);
            deliquency.setCounterSaveUpdate("S");
            refreshDDForm();
        }

        deliqDetails.add(deliquency);
        if (schemeMaster.getFunctionFlag().equalsIgnoreCase("M")) {
            if (selectCountDeliqDetails != 1) {
                for (int i = 0; i < deliqDetailsTemp.size(); i++) {
                    String deliqCycleTmp1 = deliqDetailsTemp.get(i).getDeliqCycle();
                    if (!deliqCycleTmp1.equalsIgnoreCase(deliqCycle)) {
                        deliquency.setCounterSaveUpdate("S");
                        deliquencyDetailsTmp.add(deliquency);
                        refreshDDForm();
                        return;
                    }
                }
            }
            if (currentItemDeliqDetails.getDeliqCycle() == null || currentItemDeliqDetails.getDeliqCycle().equalsIgnoreCase("")) {
                deliquency.setCounterSaveUpdate("S");
                deliquencyDetailsTmp.add(deliquency);
                refreshDDForm();
                return;
            } else if (currentItemDeliqDetails.getDeliqCycle().equalsIgnoreCase(deliqCycle)) {
                if (!currentItemDeliqDetails.getDaysPastDue().equalsIgnoreCase(daysPastDue) || !currentItemDeliqDetails.getDelFlagDeliq().equalsIgnoreCase(delFlagDeliq)) {
                    deliquency.setCounterSaveUpdate("U");
                    refreshDDForm();
                    deliquencyDetailsTmp.add(deliquency);
                }
                selectCountDeliqDetails = 0;
            }
        }
    }

    public void getDDDetails() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeDelinquencyDetailsTO> resultList = schemeMasterManagementDelegate.showDataDeliqDetails(schemeMaster.schemeCode);
            for (CbsSchemeDelinquencyDetailsTO entityList : resultList) {
                DeliquencyTable dt = new DeliquencyTable();
                dt.setDeliqCycle(entityList.getCbsSchemeDelinquencyDetailsPKTO().getDelinqCycle());
                dt.setDaysPastDue(entityList.getDaysPastDue());
                dt.setPlaceInHolder(entityList.getPlaceHolder());
                dt.setProvisionInPercent(entityList.getProvisionPercent());
                dt.setDelFlagDeliq(entityList.getDelFlag());
                dt.setCounterSaveUpdate("G");
                deliqDetails.add(dt);
            }
            refreshDDForm();
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void enableDDForm() {
        this.ddFlag = false;
    }

    public void disableDDForm() {
        this.ddFlag = true;
    }

    public void refreshDDForm() {
        this.setDeliqCycle("0");
        this.setDelFlagDeliq("0");
        this.setDaysPastDue("");
        this.setPlaceholder("");
        this.setProvisionPercent("");
    }

    public String validateDeliquencyDetails() {
        String msg = "";
        if (daysPastDue.equalsIgnoreCase("") || daysPastDue == null) {
            msg = msg + "Please Enter Days Past Due Field. ";
        }
        if (deliqCycle.equalsIgnoreCase("0") || deliqCycle == null) {
            msg = msg + "Please Enter Deliq Cycle. ";
        }
        if (delFlagDeliq.equalsIgnoreCase("0") || delFlagDeliq == null) {
            msg = msg + "Please Enter Delete Flag. ";
        }
        if (placeholder == null || placeholder.equals("")) {
            msg = msg + "Please Fill Place Holder. ";
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(placeholder);
        if (m.matches() != true) {
            msg = msg + "Please Fill Proper Place Holder. ";
        }
        if (this.placeholder.length() < 8) {
            msg = msg + "Please Fill Place Holder of length 8 digits. ";
        }
        if (provisionPercent == null || provisionPercent.equals("")) {
            msg = msg + "Please Fill Provision Percent. ";
        }
        p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        m = p.matcher(provisionPercent);
        if (m.matches() != true) {
            msg = msg + "Please Fill Proper Provision. ";
        }
        if (this.provisionPercent.contains(".")) {
            if (this.provisionPercent.indexOf(".") != this.provisionPercent.lastIndexOf(".")) {
                msg = msg + "Please Fill Provision Percent. ";
            } else if (this.provisionPercent.substring(provisionPercent.indexOf(".") + 1).length() > 2) {
                msg = msg + "Please Fill Provision Percent upto 2 places. ";
            }
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }
}
