/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class BulkDeafMarking extends BaseBean {

    private String errorMsg;
    private String acNature;
    private String acType;
    private String month;
    private String year;
    private String savingRoi;
    private String roiVisible= "none";
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean disableBtn = true;
    private boolean fieldDisable = false;
    private List<SelectItem> acNatureList;
    private List<SelectItem> acTypeList;
    private List<SelectItem> monthList;
    private List<UnclaimedAccountStatementPojo> deafDataList;
    private DDSReportFacadeRemote ddsRemote;
    private MiscReportFacadeRemote remoteFacade;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote commonRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    String intCalcDt = "";
    String finalDeafEffDt = "";

    public BulkDeafMarking() {
        try {
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");


            acNatureList = new ArrayList<SelectItem>();
            acTypeList = new ArrayList<SelectItem>();
            monthList = new ArrayList<SelectItem>();

            //Retrieve Nature
            acNatureList.add(new SelectItem("0", "--Select--"));
            List list = ddsRemote.getAccountNatureClassification("'C','B'");
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }

            refreshForm();
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            if(this.acNature.equals(CbsConstant.DEPOSIT_SC)) this.roiVisible="inline";
            
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("0", "--Select--"));
//            acTypeList.add(new SelectItem("ALL", "ALL"));
            List actCodeList = ddsRemote.getAcctCodeByNature(this.acNature);
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void retrieveDeafData() {
        this.setErrorMsg("");
        try {
            if (validateField()) {
                deafDataList = new ArrayList<UnclaimedAccountStatementPojo>();
                this.disableBtn = true;
                this.fieldDisable = false;
                intCalcDt = "";
                finalDeafEffDt = "";
                //this.roiVisible = "none";
                //this.savingRoi;
                //Get Month Deaf Effective Date
                String deafEffDate = ddsRemote.getDeafEfectiveDate();
                if (deafEffDate.equals("") || deafEffDate.equals("")) {
                    this.setErrorMsg("Please define Deaf Effective Date");
                    return;
                }
                //Get Final Interest Calculation Date
//                intCalcDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
//                        Integer.parseInt(this.year)));
                
                intCalcDt = dmy.format(ymd.parse(CbsUtil.dateAdd(ymd.format(CbsUtil.
                        calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year))), -1)));

                String prevMonthDt = CbsUtil.monthAdd(ymd.format(dmy.parse(intCalcDt)), -1);

                //Final Deaf Effective Date
                finalDeafEffDt = prevMonthDt.substring(0, 6) + deafEffDate;
                if(!this.acNature.equals(CbsConstant.DEPOSIT_SC)) savingRoi = "0";
                
                deafDataList = ddsRemote.unClaimedMarking(getOrgnBrCode(), this.acNature,
                        this.acType, finalDeafEffDt, intCalcDt, getTodayDate(),Double.parseDouble(savingRoi));//savingRoi parameter added by manish
                if (deafDataList.isEmpty()) {
                    refreshForm();
                    this.setErrorMsg("There is no data for deaf marking.");
                } else {
                    if (ftsRemote.existInParameterInfoReport("UNCLAIMED-POSTING")) {
                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                           // this.roiVisible = "";
                            this.savingRoi = "";
                        }
                        this.disableBtn = false;
                        this.fieldDisable = true;
                        this.setErrorMsg("Now you can mark deaf.");

                        printReport(deafDataList, dmy.format(ymd.parse(finalDeafEffDt)), intCalcDt);
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void printReport(List<UnclaimedAccountStatementPojo> resultList, String deafAsOn, String intAsOn) {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankDetailsList.size() > 0) {
                String reportName = "Deaf Report";
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddress", bankAddress);
                fillParams.put("pReportName", reportName);
                fillParams.put("pReportDt", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("deafAsOn", deafAsOn);
                fillParams.put("intAsOn", intAsOn);

                new ReportBean().jasperReport("DeafAccountInfo", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMsg("");
        try {
            if (ftsRemote.existInParameterInfoReport("UNCLAIMED-POSTING")) {
                if (deafDataList.isEmpty()) {
                    this.setErrorMsg("There is no data to mark deaf.");
                    return;
                }
//                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
//                        || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                    String result = validateRoi();
//                    if (!result.equals("true")) {
//                        this.setErrorMsg(result);
////                        return;
//                    }
//                }
                //Unclaimed Posting.
                String msg = ddsRemote.insertDeafTransaction(getOrgnBrCode(), this.acNature, deafDataList,
                        intCalcDt, getUserName(), "R", this.acType, this.savingRoi, finalDeafEffDt, getTodayDate());
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                refreshForm();
                this.setErrorMsg("Deaf marking has been done successfully.");
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public String validateRoi() {
        String result = "true";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            Matcher m = p.matcher(this.savingRoi);
            if (this.savingRoi == null || this.savingRoi.equals("")) {
                return result = "Saving roi can not be blank.";
            } else if (m.matches() != true) {
                return result = "Please fill proper Saving Roi.";
            } else if (new BigDecimal(this.savingRoi).compareTo(new BigDecimal("0")) == -1) {
                return result = "Saving roi can not be negative.";
            }
//            else if (this.savingRoi.equalsIgnoreCase("0") || this.savingRoi.equalsIgnoreCase("0.0")) {
//                return result = "Saving roi can not be zero.";
//            } 

            if (this.savingRoi.contains(".")) {
                if (this.savingRoi.indexOf(".") != this.savingRoi.lastIndexOf(".")) {
                    return result = "Invalid Saving Roi. Please fill the Saving Roi correctly.";
                } else if (this.savingRoi.substring(savingRoi.indexOf(".") + 1).length() > 2) {
                    return result = "Please fill the Saving Roi upto two decimal places.";
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public boolean validateField() {
        try {
            if (this.acNature == null || this.acNature.equals("0")) {
                this.setErrorMsg("Please select A/c Nature.");
                return false;
            }
            if (this.acType == null || this.acType.equals("0")) {
                this.setErrorMsg("Please select A/c Type.");
                return false;
            }
            if (this.month == null || this.month.equals("0")) {
                this.setErrorMsg("Please select Month.");
                return false;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setErrorMsg("Please fill proper Year.");
                return false;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setErrorMsg("Please fill proper Year in numeric");
                return false;
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
            return false;
        }
        return true;
    }

    public void refreshForm() {
        this.setErrorMsg("");
        this.setAcNature("0");
        this.setAcType("0");
        this.setMonth("0");
        this.setYear("");
        this.setDisableBtn(true);
        this.setFieldDisable(false);
        deafDataList = new ArrayList<UnclaimedAccountStatementPojo>();
        intCalcDt = "";
        finalDeafEffDt = "";
        this.roiVisible = "none";
        this.savingRoi = "";
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public List<UnclaimedAccountStatementPojo> getDeafDataList() {
        return deafDataList;
    }

    public void setDeafDataList(List<UnclaimedAccountStatementPojo> deafDataList) {
        this.deafDataList = deafDataList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isFieldDisable() {
        return fieldDisable;
    }

    public void setFieldDisable(boolean fieldDisable) {
        this.fieldDisable = fieldDisable;
    }

    public String getSavingRoi() {
        return savingRoi;
    }

    public void setSavingRoi(String savingRoi) {
        this.savingRoi = savingRoi;
    }

    public String getRoiVisible() {
        return roiVisible;
    }

    public void setRoiVisible(String roiVisible) {
        this.roiVisible = roiVisible;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }
}
