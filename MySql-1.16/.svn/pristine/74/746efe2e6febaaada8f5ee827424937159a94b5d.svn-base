package com.cbs.web.controller.ho.share;

import com.cbs.dto.DividendTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.DividendCalculationFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Navneet Goyal
 */
public class DividendCalculation extends BaseBean {

    private String alphaCode, postDate, financialYear, dividend, message, currentDate;
    private boolean disableBtnCalculate, disableBtnPost, disableBtnReport, visibleDividendTable, readOnlyAlphaCode, readOnlyDividend, readOnlyFinYear;
    private Double shareAmount;
    private boolean reportFlag;
    DividendCalculationFacadeRemote dividendCalculationRemote;
    private List<SelectItem> alphaCodeList;
    private List<SelectItem> finYearList;
    private List<SelectItem> custTypeList;
    private String custType;
    private Validator validator;
    private List<DividendTable> dividendTable;
    private String viewID = "/pages/master/sm/test.jsp";
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Integer totalRows;

    public DividendCalculation() {
        try {
            Date date = new Date();
            setPostDate(dmyFormat.format(date));
            dividendCalculationRemote = (DividendCalculationFacadeRemote) ServiceLocator.getInstance().lookup("DividendCalculationFacade");
            validator = new Validator();
            onLoad();
            getOnLoadData();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void calculateAction() {
        try {
            Date date = new Date();
            currentDate = ymdFormat.format(date);
            dividendTable = dividendCalculationRemote.calculateDividend(alphaCode, shareAmount, Double.parseDouble(dividend), Integer.parseInt(financialYear), dmyFormat.parse(postDate), getUserName(), getOrgnBrCode(), custType);
            if (!dividendTable.isEmpty()) {
                Collections.sort(dividendTable);
                totalRows = dividendTable.size();
                setMessage("Data Generated !!");
                setDisableBtnReport(false);
                if (alphaCode.equalsIgnoreCase("All")) {
                    setDisableBtnPost(false);
                } else {
                    setDisableBtnPost(true);
                }

                disableFields();
                Vector ele = dividendCalculationRemote.getBranchNameandAddress(getOrgnBrCode());

                Map fillParams = new HashMap();
                String repName = "Share Dividend Calculation Report";
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pAlphaCode", alphaCode);
                fillParams.put("pBankname", ele.get(0).toString());
                fillParams.put("pBranchAddress", ele.get(1).toString());
                new ReportBean().jasperReport("DividendCalculationReport", "text/html", new JRBeanCollectionDataSource(dividendTable), fillParams, repName);
                this.setViewID("/report/ReportPagePopUp.jsp");
            } else {
                setMessage("No Data Exists !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private void disableFields() {
        setDisableBtnCalculate(true);
        setVisibleDividendTable(true);
        setReadOnlyAlphaCode(true);
        setReadOnlyDividend(true);
        setReadOnlyFinYear(true);
    }

    private void enableFields() {
        setDisableBtnCalculate(false);
        setDisableBtnPost(true);
        setVisibleDividendTable(false);
        setReadOnlyAlphaCode(false);
        setReadOnlyDividend(false);
        setReadOnlyFinYear(false);
    }

    public void postAction() {
        try {
            if (dividendTable != null && !dividendTable.isEmpty()) {
                String resS = dividendCalculationRemote.postDividend(dividendTable, alphaCode, Integer.parseInt(financialYear), dmyFormat.parse(postDate), getUserName(), getOrgnBrCode(),custType);
                cancelAction();
                onLoad();
                setMessage(resS);
            } else {
                setMessage("Please first calculate the dividend !!");
            }
        } catch (Exception e) {
            cancelAction();
            onLoad();
            setMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancelAction() {
        try {
            dividendTable = null;
            setMessage("");
            setAlphaCode("--Select--");
            setDividend("");
            setCustType("0");
            enableFields();
            onLoad();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        try {
            cancelAction();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void onBlurFinancialYear() {
        setMessage("");
        if (validate()) {
            setDisableBtnCalculate(false);
        } else {
            setDisableBtnCalculate(true);
        }
    }

    private boolean validate() {
        if (alphaCode.equalsIgnoreCase("--Select--")) {
            setMessage("Please Select Alpha Code !!");
            return false;
        }
        if (!validator.validateDoublePositive(dividend)) {
            setMessage("Please Fill Correct Dividend (in %) !!");
            return false;
        }
        if (Double.parseDouble(dividend) > 25) {
            setMessage("Dividend (in %) Cannot Be Greater Than 25 !!");
            return false;
        }
        if (!validator.validateInteger(financialYear)) {
            setMessage("Year Can Have Numeric Value Only !!");
            return false;
        }
        if (financialYear.length() != 4 || Integer.parseInt(financialYear) > 2200) {
            setMessage("Please select valid financial year");
            return false;
        }
        return true;
    }

    private void getOnLoadData() {
        try {
            shareAmount = dividendCalculationRemote.getShareValue();
            List alphaList = dividendCalculationRemote.getAlphaCodeList();
            finYearList = new ArrayList<SelectItem>();
            List list = dividendCalculationRemote.getFinancialYear();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                finYearList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            alphaCodeList = new ArrayList<SelectItem>();
            alphaCodeList.add(new SelectItem("--Select--"));
            alphaCodeList.add(new SelectItem("All"));
            if (!alphaList.isEmpty()) {
                for (int i = 0; i < alphaList.size(); i++) {
                    Vector ele = (Vector) alphaList.get(i);
                    alphaCodeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
            custTypeList = new ArrayList<SelectItem>();
            custTypeList.add(new SelectItem("00","All"));
            custTypeList.add(new SelectItem("01", "Indivisual"));
            custTypeList.add(new SelectItem("02", "Associate"));
            setCustType("0");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    private void onLoad() {
        setDisableBtnReport(true);
        setDisableBtnCalculate(true);
        setDisableBtnPost(true);
    }

    public List<SelectItem> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<SelectItem> finYearList) {
        this.finYearList = finYearList;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public List<SelectItem> getAlphaCodeList() {
        return alphaCodeList;
    }

    public void setAlphaCodeList(List<SelectItem> alphaCodeList) {
        this.alphaCodeList = alphaCodeList;
    }

    public boolean isDisableBtnCalculate() {
        return disableBtnCalculate;
    }

    public void setDisableBtnCalculate(boolean disableBtnCalculate) {
        this.disableBtnCalculate = disableBtnCalculate;
    }

    public boolean isDisableBtnPost() {
        return disableBtnPost;
    }

    public void setDisableBtnPost(boolean disableBtnPost) {
        this.disableBtnPost = disableBtnPost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDividend() {
        return dividend;
    }

    public void setDividend(String dividend) {
        this.dividend = dividend;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Double getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(Double shareAmount) {
        this.shareAmount = shareAmount;
    }

    public List<DividendTable> getDividendTable() {
        return dividendTable;
    }

    public void setDividendTable(List<DividendTable> dividendTable) {
        this.dividendTable = dividendTable;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public boolean isVisibleDividendTable() {
        return visibleDividendTable;
    }

    public void setVisibleDividendTable(boolean visibleDividendTable) {
        this.visibleDividendTable = visibleDividendTable;
    }

    public boolean isReadOnlyAlphaCode() {
        return readOnlyAlphaCode;
    }

    public void setReadOnlyAlphaCode(boolean readOnlyAlphaCode) {
        this.readOnlyAlphaCode = readOnlyAlphaCode;
    }

    public boolean isReadOnlyDividend() {
        return readOnlyDividend;
    }

    public void setReadOnlyDividend(boolean readOnlyDividend) {
        this.readOnlyDividend = readOnlyDividend;
    }

    public boolean isReadOnlyFinYear() {
        return readOnlyFinYear;
    }

    public void setReadOnlyFinYear(boolean readOnlyFinYear) {
        this.readOnlyFinYear = readOnlyFinYear;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public boolean isDisableBtnReport() {
        return disableBtnReport;
    }

    public void setDisableBtnReport(boolean disableBtnReport) {
        this.disableBtnReport = disableBtnReport;
    }

    public List<SelectItem> getCustTypeList() {
        return custTypeList;
    }

    public void setCustTypeList(List<SelectItem> custTypeList) {
        this.custTypeList = custTypeList;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }
    
}
