
package com.cbs.web.controller.intcal;

import com.cbs.dto.HoIntObj;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.HoInterestCalcFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class HoInterestCal extends BaseBean {

    private String message;

    private String branch;

    private String debitAcc;

    private String creditAcc;

    private String totalInt;

    private float intRate;

    private String quarter;

    private boolean disablePost;

    private boolean disableCal;

    private boolean reportFlag;

    private String panelMsg;

    private List<SelectItem> branchOptionList;

    private List<HoIntObj> intCalc;

    private List<SelectItem> quarterEndList;

    private CommonReportMethodsRemote common = null;
    private HoInterestCalcFacadeRemote hoIntFacade = null;

    private String viewID = "/pages/master/sm/test.jsp";

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

    public String getDebitAcc() {
        return debitAcc;
    }

    public void setDebitAcc(String debitAcc) {
        this.debitAcc = debitAcc;
    }

    public String getCreditAcc() {
        return creditAcc;
    }

    public void setCreditAcc(String creditAcc) {
        this.creditAcc = creditAcc;
    }

    public String getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(String totalInt) {
        this.totalInt = totalInt;
    }

    public float getIntRate() {
        return intRate;
    }

    public void setIntRate(float intRate) {
        this.intRate = intRate;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public boolean isDisableCal() {
        return disableCal;
    }

    public void setDisableCal(boolean disableCal) {
        this.disableCal = disableCal;
    }

    public List<SelectItem> getQuarterEndList() {
        return quarterEndList;
    }

    public void setQuarterEndList(List<SelectItem> quarterEndList) {
        this.quarterEndList = quarterEndList;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getPanelMsg() {
        return panelMsg;
    }

    public void setPanelMsg(String panelMsg) {
        this.panelMsg = panelMsg;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public HoInterestCal() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            hoIntFacade = (HoInterestCalcFacadeRemote) ServiceLocator.getInstance().lookup("HoInterestCalcFacade");
            quarterEndList = new ArrayList<SelectItem>();
            quarterEndList.add(new SelectItem("", "--Select--"));
            quarterEndList.add(new SelectItem("3", "MARCH"));
            quarterEndList.add(new SelectItem("6", "JUNE"));

            quarterEndList.add(new SelectItem("9", "SEPTEMBER"));
            quarterEndList.add(new SelectItem("12", "DECEMBER"));
            setQuarter("");
            branchOptionList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchOptionList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
                }
            }
            setIntRate(0);
            setReportFlag(false);
            setDisableCal(false);
            setDisablePost(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void calculate() {
        setMessage("");
        setReportFlag(false);
        NumberFormat formatter = new DecimalFormat("#0.00");
        intCalc = new ArrayList<HoIntObj>();
        try {
            if (quarter.equals("")) {
                throw new ApplicationException("Please select the Quarter.");
            }

            if (intRate <= 0) {
                throw new ApplicationException("Please fill the correct interest Rate");
            }

            Map<String, List<HoIntObj>> dataMap = hoIntFacade.hoIntCalculation(branch, quarter, intRate);

            List<HoIntObj> glHeadList = dataMap.get("H");
            HoIntObj hoHeadObj = glHeadList.get(0);
            HoIntObj brHeadObj = glHeadList.get(1);
            for (HoIntObj intObj : glHeadList) {
                System.out.println(intObj.getsNo() + " : " + intObj.getDesc());
            }
            List<HoIntObj> totIntList = dataMap.get("I");

            HoIntObj intObj = totIntList.get(0);
            BigDecimal totInt = intObj.getAmount();
            setTotalInt(formatter.format(intObj.getAmount()));

            System.out.println(intObj.getsNo() + " : " + totInt);
            if (totInt.doubleValue() > 0) {
                setDebitAcc(hoHeadObj.getDesc());
                setCreditAcc(brHeadObj.getDesc());
            } else {
                setDebitAcc(brHeadObj.getDesc());
                setCreditAcc(hoHeadObj.getDesc());
            }

            intCalc = dataMap.get("L");
            for (HoIntObj intObjNew : intCalc) {
                System.out.println(intObjNew.getsNo() + " : " + intObjNew.getDesc() + " : " + intObjNew.getAmount());
            }
            setDisablePost(false);
            setDisableCal(true);
            String repName = "Ho Interest Calculation";
            String report = "Ho Interest Calculation";
            List vec = common.getBranchNameandAddress(getOrgnBrCode());
            List vecBr = common.getBranchNameandAddress(branch);
            
            Map fillParams = new HashMap();

            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pBnkName", vec.get(0).toString());
            fillParams.put("pBnkAdd", vec.get(1).toString());
            //fillParams.put("pBranchName", vecBr.get(0).toString());
            fillParams.put("pBranchAdd", vecBr.get(1).toString());
            
            new ReportBean().jasperReport("Ho_Interst", "text/html", new JRBeanCollectionDataSource(intCalc), fillParams, report);
            this.setViewID("/report/ReportPagePopUp.jsp");
            setReportFlag(true);
            this.setMessage("Interest has been calculated successfully. Now you can post your interest !");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void Post() {
        try {
            if(debitAcc == null || debitAcc.equals("")){
                throw new ApplicationException("Please fill the Debit Account.");
            }
            
            if(creditAcc == null || creditAcc.equals("")){
                throw new ApplicationException("Please fill the Credit Account.");
            }
            if(totalInt == null || totalInt.equals("")){
                throw new ApplicationException("Interest amount could not blank.");
            }
            String msg = hoIntFacade.hoIntPosting(getOrgnBrCode(), branch, debitAcc, creditAcc, Math.abs(Double.parseDouble(totalInt)), getUserName(),quarter);
            setReportFlag(false);
            setMessage(msg);
            
            setDebitAcc("");
            setCreditAcc("");
            setTotalInt("");
            setIntRate(0);
            setDisableCal(false);
            setDisablePost(true);
            if (intCalc != null) {
                intCalc.clear();;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setReportFlag(false);
            setMessage("");

            setDebitAcc("");
            setCreditAcc("");
            setQuarter("");
            setIntRate(0);
            setTotalInt("");
            if (intCalc != null) {
                intCalc.clear();
            }
            setDisableCal(false);
            setDisablePost(true);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        this.setMessage("");
        refresh();
        return "case1";
    }
}
