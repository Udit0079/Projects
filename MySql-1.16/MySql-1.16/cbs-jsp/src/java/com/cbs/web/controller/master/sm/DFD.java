/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeDepositFlowDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDepositFlowDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.DepositFlow;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class DFD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for dfd.jsp file
    private String flowCode;
    private String flowFreqMonths;
    private String flowFreqDays;
    private String flowPeriodBegin;
    private String flowPeriodEnd;
    private String delFlagFlow;
    private int currentRow4;
    private int schemeflow = 0;
    boolean disableFlowCode;
    private boolean dfdFlag;
    private int countflow1;
    private int countflow2;
    private DepositFlow currentItem4 = new DepositFlow();
    private List<DepositFlow> depFlow;
    private List<DepositFlow> flowtmp;
    private List<SelectItem> ddturnoverFreqType;
    private List<SelectItem> ddDfdTrnRefNo;
    
    //Getter-Setter for dfd.jsp file
    public List<DepositFlow> getFlowtmp() {
        return flowtmp;
    }

    public void setFlowtmp(List<DepositFlow> flowtmp) {
        this.flowtmp = flowtmp;
    }

    public int getCurrentRow4() {
        return currentRow4;
    }

    public void setCurrentRow4(int currentRow4) {
        this.currentRow4 = currentRow4;
    }

    public int getCountflow1() {
        return countflow1;
    }

    public void setCountflow1(int countflow1) {
        this.countflow1 = countflow1;
    }

    public int getCountflow2() {
        return countflow2;
    }

    public void setCountflow2(int countflow2) {
        this.countflow2 = countflow2;
    }

    public List<SelectItem> getDdDfdTrnRefNo() {
        return ddDfdTrnRefNo;
    }

    public void setDdDfdTrnRefNo(List<SelectItem> ddDfdTrnRefNo) {
        this.ddDfdTrnRefNo = ddDfdTrnRefNo;
    }

    public List<SelectItem> getDdturnoverFreqType() {
        return ddturnoverFreqType;
    }

    public void setDdturnoverFreqType(List<SelectItem> ddturnoverFreqType) {
        this.ddturnoverFreqType = ddturnoverFreqType;
    }

    public DepositFlow getCurrentItem4() {
        return currentItem4;
    }

    public void setCurrentItem4(DepositFlow currentItem4) {
        this.currentItem4 = currentItem4;
    }

    public String getDelFlagFlow() {
        return delFlagFlow;
    }

    public void setDelFlagFlow(String delFlagFlow) {
        this.delFlagFlow = delFlagFlow;
    }

    public List<DepositFlow> getDepFlow() {
        return depFlow;
    }

    public void setDepFlow(List<DepositFlow> depFlow) {
        this.depFlow = depFlow;
    }

    public boolean isDisableFlowCode() {
        return disableFlowCode;
    }

    public void setDisableFlowCode(boolean disableFlowCode) {
        this.disableFlowCode = disableFlowCode;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getFlowFreqDays() {
        return flowFreqDays;
    }

    public void setFlowFreqDays(String flowFreqDays) {
        this.flowFreqDays = flowFreqDays;
    }

    public String getFlowFreqMonths() {
        return flowFreqMonths;
    }

    public void setFlowFreqMonths(String flowFreqMonths) {
        this.flowFreqMonths = flowFreqMonths;
    }

    public String getFlowPeriodBegin() {
        return flowPeriodBegin;
    }

    public void setFlowPeriodBegin(String flowPeriodBegin) {
        this.flowPeriodBegin = flowPeriodBegin;
    }

    public String getFlowPeriodEnd() {
        return flowPeriodEnd;
    }

    public void setFlowPeriodEnd(String flowPeriodEnd) {
        this.flowPeriodEnd = flowPeriodEnd;
    }

    public int getSchemeflow() {
        return schemeflow;
    }

    public void setSchemeflow(int schemeflow) {
        this.schemeflow = schemeflow;
    }

    public boolean isDfdFlag() {
        return dfdFlag;
    }

    public void setDfdFlag(boolean dfdFlag) {
        this.dfdFlag = dfdFlag;
    }

    /** Creates a new instance of DFD */
    public DFD() {
       
        try {
            depFlow = new ArrayList<DepositFlow>();
            flowtmp = new ArrayList<DepositFlow>();

            ddturnoverFreqType = new ArrayList<SelectItem>();
            ddturnoverFreqType.add(new SelectItem("0", ""));
            ddturnoverFreqType.add(new SelectItem("D", "Daily"));
            ddturnoverFreqType.add(new SelectItem("W", "Weekly"));
            ddturnoverFreqType.add(new SelectItem("M", "Monthly"));
            ddturnoverFreqType.add(new SelectItem("Q", "Quarterly"));
            ddturnoverFreqType.add(new SelectItem("H", "HalfYearly"));
            ddturnoverFreqType.add(new SelectItem("Y", "Yearly"));

            ddDfdTrnRefNo = new ArrayList<SelectItem>();
            ddDfdTrnRefNo.add(new SelectItem("0", ""));
            ddDfdTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddDfdTrnRefNo.add(new SelectItem("N", "No"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for dfd.jsp file
    public void selectDFDDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeDepositFlowDetailsTO> cbsSchemeDepositFlowDetailsTOs = schemeMasterManagementDelegate.getDepositFlowDetails(schemeMaster.getSchemeCode());
            if (cbsSchemeDepositFlowDetailsTOs.size() > 0) {
                for (CbsSchemeDepositFlowDetailsTO tolist : cbsSchemeDepositFlowDetailsTOs) {
                    DepositFlow df = new DepositFlow();
                    CbsSchemeDepositFlowDetailsPKTO cbsSchemeDepositFlowDetailsPKTO = tolist.getCbsSchemeDepositFlowDetailsPKTO();
                    if (cbsSchemeDepositFlowDetailsPKTO.getFlowCode() == null || cbsSchemeDepositFlowDetailsPKTO.getFlowCode().equalsIgnoreCase("")) {
                        df.setTblFlowCode("");
                    } else {
                        df.setTblFlowCode(cbsSchemeDepositFlowDetailsPKTO.getFlowCode());
                    }
                    if (tolist.getFlowFreqMonths() == null || tolist.getFlowFreqMonths().equalsIgnoreCase("")) {
                        df.setTblFlowFreqMonths("");
                    } else {
                        df.setTblFlowFreqMonths(tolist.getFlowFreqMonths());
                    }
                    if (tolist.getFlowFreqDays() == null || tolist.getFlowFreqDays().equalsIgnoreCase("")) {
                        df.setTblFlowFreqDays("");
                    } else {
                        df.setTblFlowFreqDays(tolist.getFlowFreqDays());
                    }
                    if (tolist.getFlowPeriodBegin() == null || tolist.getFlowPeriodBegin().equalsIgnoreCase("")) {
                        df.setTblFlowPeriodBegin("0");
                    } else {
                        df.setTblFlowPeriodBegin(tolist.getFlowPeriodBegin());
                    }
                    if (tolist.getFlowPeriodEnd() == null || tolist.getFlowPeriodEnd().equalsIgnoreCase("")) {
                        df.setTblFlowPeriodEnd("0");
                    } else {
                        df.setTblFlowPeriodEnd(tolist.getFlowPeriodEnd());
                    }
                    if (tolist.getDelFlag() == null || tolist.getDelFlag().equalsIgnoreCase("")) {
                        df.setTblDelFlagFlow("0");
                    } else {
                        df.setTblDelFlagFlow(tolist.getDelFlag());
                    }
                    df.setCounterSaveUpdateFlow("G");
                    depFlow.add(df);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void setDepositFlowTable() {
        schemeMaster.setMessage("");
        this.disableFlowCode = false;
        List<DepositFlow> depositTemp = depFlow;

        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (schemeflow == 0) {
                for (int i = 0; i < depositTemp.size(); i++) {
                    String tmpCompare = depositTemp.get(i).getTblFlowCode();
                    if (tmpCompare.equalsIgnoreCase(flowCode)) {
                        schemeMaster.setMessage("Flow Code Already Exists In The Table. Please Select other Flow Code.");
                        return;
                    }
                }
            }
        }

        DepositFlow stk3 = new DepositFlow();
        stk3.setTblFlowCode(flowCode);
        stk3.setTblFlowFreqMonths(flowFreqMonths);
        stk3.setTblFlowFreqDays(flowFreqDays);
        stk3.setTblFlowPeriodBegin(flowPeriodBegin);
        stk3.setTblFlowPeriodEnd(flowPeriodEnd);
        stk3.setTblDelFlagFlow(delFlagFlow);

        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            stk3.setCounterSaveUpdateFlow("S");
            refreshDFDForm();
        }

        depFlow.add(stk3);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (schemeflow != 1) {
                for (int i = 0; i < depositTemp.size(); i++) {
                    String referenceTypetmp = depositTemp.get(i).getTblFlowCode();
                    if (!referenceTypetmp.equalsIgnoreCase(flowCode)) {
                        stk3.setCounterSaveUpdateFlow("S");
                        flowtmp.add(stk3);
                        refreshDFDForm();
                        return;
                    }
                }
            }

            if (currentItem4.getTblFlowCode() == null || currentItem4.getTblFlowCode().equalsIgnoreCase("")) {
                stk3.setCounterSaveUpdateFlow("S");
                flowtmp.add(stk3);
                refreshDFDForm();
                return;
            } else if (currentItem4.getTblFlowCode().equalsIgnoreCase(flowCode)) {

                if (!currentItem4.getTblFlowCode().equalsIgnoreCase(flowCode)
                        || !currentItem4.getTblFlowFreqMonths().equalsIgnoreCase(flowFreqMonths)
                        || !currentItem4.getTblFlowFreqDays().equalsIgnoreCase(flowFreqDays)
                        || !currentItem4.getTblFlowPeriodBegin().equalsIgnoreCase(flowPeriodBegin)
                        || !currentItem4.getTblFlowPeriodEnd().equalsIgnoreCase(flowPeriodEnd)
                        || !currentItem4.getTblDelFlagFlow().equalsIgnoreCase(delFlagFlow)) {
                    stk3.setCounterSaveUpdateFlow("U");
                    refreshDFDForm();
                    flowtmp.add(stk3);
                }
                schemeflow = 0;
            }
        }
    }

    public void selectDepositFlow() {
        this.disableFlowCode = true;
        schemeMaster.setMessage("");
        schemeflow = 1;
        if (!(flowCode.equalsIgnoreCase("0") && currentItem4.getTblFlowCode() != null)) {
            if (!flowCode.equalsIgnoreCase("")) {
                if (!flowCode.equalsIgnoreCase(currentItem4.getTblFlowCode())) {
                    countflow2 = countflow1;
                    countflow1 = countflow1 + 1;
                    if (countflow2 != countflow1) {
                        //setDepositInterestTable();
                        setDepositFlowTable();
                    }
                } else {
                    countflow1 = 0;
                }
            }
        }

        setFlowCode(currentItem4.getTblFlowCode());
        setFlowFreqMonths(currentItem4.getTblFlowFreqMonths());
        setFlowFreqDays(currentItem4.getTblFlowFreqDays());
        setFlowPeriodBegin(currentItem4.getTblFlowPeriodBegin());
        setFlowPeriodEnd(currentItem4.getTblFlowPeriodEnd());
        setDelFlagFlow(currentItem4.getTblDelFlagFlow());
        depFlow.remove(currentRow4);
    }

    public void refreshDFDForm() {
        this.setFlowCode("");
        this.setFlowFreqMonths("");
        this.setFlowFreqDays("");
        this.setFlowPeriodBegin("0");
        this.setFlowPeriodEnd("0");
        this.setDelFlagFlow("0");
        //depFlow.clear();
    }

    public void enableDFDForm() {
        this.dfdFlag = false;
    }

    public void disableDFDForm() {
        this.dfdFlag = true;
    }
}
