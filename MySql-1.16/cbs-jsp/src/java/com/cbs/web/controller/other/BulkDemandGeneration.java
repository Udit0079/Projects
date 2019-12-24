/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.DemandReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class BulkDemandGeneration extends BaseBean {

    private String errorMessage;
    private String placeOfWorking;
    private String demandFileName;
    private boolean excelLink;
    private List<SelectItem> placeOfWorkingList;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private String tillDate;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String personalNoRange;
    private List<SelectItem> personalNoRangeList;
    private String fileType;
    private List<SelectItem> fileTypeList;
    private String displayRange;
    private String bnkCode;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiName = "PostalDetailFacade";
    private PostalDetailFacadeRemote remote = null;
    TdReceiptManagementFacadeRemote RemoteCode;
    FtsPostingMgmtFacadeRemote fts;
    DemandReportFacadeRemote dmdRemote;

    public BulkDemandGeneration() {
        try {
            //year = ymd.format(date).substring(0, 4);
            remote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            dmdRemote = (DemandReportFacadeRemote) ServiceLocator.getInstance().lookup("DemandReportFacade");
            bnkCode = fts.getBankCode();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            if (!bnkCode.equalsIgnoreCase("oefb")) {
                if (fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                    getBranchPlaceOfWorking();
                } else {
                    getBranchPlaceOfWorking1();
                }
            }
            monthList = new ArrayList<SelectItem>();
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }
            //For OEF
            if (bnkCode.equalsIgnoreCase("oefb")) {
                this.setDisplayRange("");
                personalNoRangeList = new ArrayList<SelectItem>();
                personalNoRangeList.add(new SelectItem("S", "--Select--"));
                personalNoRangeList.add(new SelectItem("100001 to 104444", "100001 to 104444"));
                personalNoRangeList.add(new SelectItem("104445 to 106666", "104445 to 106666"));
                personalNoRangeList.add(new SelectItem("106667 to 109999", "106667 to 109999"));

                fileTypeList = new ArrayList<SelectItem>();
                fileTypeList.add(new SelectItem("S", "--Select--"));
                fileTypeList.add(new SelectItem("PersonalNo", "PersonalNo Wise"));
                fileTypeList.add(new SelectItem("In cbs", "A/c No. Wise in cbs"));

                getStafOfWorking();

            } else {
                this.setDisplayRange("none");
            }

            btnRefreshAction();

        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void getBranchPlaceOfWorking1() {
        placeOfWorkingList = new ArrayList<SelectItem>();
        try {
            placeOfWorkingList.add(new SelectItem("--Select--"));
            List result = remote.getBranchPlaceOfWorking1(getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    placeOfWorkingList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void getBranchPlaceOfWorking() {
        placeOfWorkingList = new ArrayList<SelectItem>();
        try {
            placeOfWorkingList.add(new SelectItem("--Select--"));
            TreeSet<String> ts = remote.getBranchPlaceOfWorking(getOrgnBrCode());
            if (!ts.isEmpty()) {
                Iterator<String> it = ts.iterator();
                while (it.hasNext()) {
                    placeOfWorkingList.add(new SelectItem(it.next()));
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void getStafOfWorking() {
        placeOfWorkingList = new ArrayList<SelectItem>();
        try {
            placeOfWorkingList.add(new SelectItem("--Select--"));
            placeOfWorkingList.add(new SelectItem("IECP"));
            placeOfWorkingList.add(new SelectItem("STCP"));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }

    }

    public void downloadExcel() {
        HttpServletResponse res = (HttpServletResponse) getHttpResponse();
        String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
        try {
            String dirName = "";
            String osName = System.getProperty("os.name");
            if (bnkCode.equalsIgnoreCase("oefb")) {
                if (fileType.equalsIgnoreCase("PersonalNo")) {
                    if (osName.equalsIgnoreCase("Linux")) {
                        dirName = "/install/PersonalNoWise/";
                    } else {
                        dirName = "C:\\PersonalNoWise\\";
                    }
                } else {
                    if (osName.equalsIgnoreCase("Linux")) {
                        dirName = "/install/POSTAL/";
                    } else {
                        dirName = "C:\\POSTAL\\";
                    }
                }
            } else {
                if (osName.equalsIgnoreCase("Linux")) {
                    dirName = "/install/POSTAL/";
                } else {
                    dirName = "C:\\POSTAL\\";
                }
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + demandFileName);
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (this.placeOfWorking == null || this.placeOfWorking.equals("") || this.placeOfWorking.equals("--Select--")) {
                this.setErrorMessage("Please select place of working.");
                return;
            }

            if (this.month == null || this.month.equals("0")) {
                this.setErrorMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setErrorMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setErrorMessage("Please fill proper Year in numeric");
                return;
            }
            if (bnkCode.equalsIgnoreCase("oefb")) {
                if (personalNoRange == null || personalNoRange.equalsIgnoreCase("S")) {
                    this.setErrorMessage("Please select Personal No. Range.");
                    return;
                }
                if (fileType == null || fileType.equalsIgnoreCase("S")) {
                    this.setErrorMessage("Please select File Type.");
                    return;
                }
            }
            //Get month End Date
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String firstDt = tillDate.substring(6, 10) + tillDate.substring(3, 5) + "01";
            //String monthLastDt = CbsUtil.monthLast(ymd.format(dmy.parse(tillDate)), 0);
            String srNo = remote.getsrNoByArea(this.placeOfWorking);
            String monthLastDt = ymd.format(dmy.parse(tillDate));
            List checkFileGen = remote.fileGenChecking(getOrgnBrCode(), placeOfWorking, firstDt, monthLastDt);
            if (!checkFileGen.isEmpty()) {
                throw new ApplicationException("Already File generated in this month");
            }
            String prvDtEnd = CbsUtil.monthLast(ymd.format(dmy.parse(getTodayDate())), -1);
//            String prvFirstDt = prvDtEnd.substring(0, 4) + prvDtEnd.substring(4, 6) + "01";

            if (remote.getflag(getOrgnBrCode(), placeOfWorking, prvDtEnd).equalsIgnoreCase("A")) {
                throw new ApplicationException("Previous month demand is not recovered !");
            }
            String fileName = "";
            if (bnkCode.equalsIgnoreCase("oefb")) {
                if (fileType.equalsIgnoreCase("PersonalNo")) {
                    fileName = dmdRemote.generateExcelPersonalNoWise(this.placeOfWorking, getOrgnBrCode(), firstDt, this.tillDate, getUserName(), personalNoRange);
                } else {
                    fileName = remote.generateExcelFileBasedOnPlaceOfWorkingOef(this.placeOfWorking, getOrgnBrCode(), firstDt, this.tillDate, getUserName(), personalNoRange);
                }
            } else {
                if (fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                    fileName = remote.generateExcelFileBasedOnPlaceOfWorkingPostal(this.placeOfWorking, getOrgnBrCode(), firstDt, this.tillDate, getUserName());
                } else {
                    fileName = remote.generateExcelFileBasedOnPlaceOfWorking(this.placeOfWorking, getOrgnBrCode(), firstDt, this.tillDate, getUserName());
                }
            }

            if (fileName.equals("")) {
                btnRefreshAction();
                this.setErrorMessage("File were not generated.");
                return;
            }
            this.demandFileName = fileName;
            excelLink = true;
            this.setErrorMessage("Now file is generated, and you can download it by click on given link.");
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setPlaceOfWorking("--Select--");
        this.setExcelLink(false);
        this.setDemandFileName("");
        this.setTillDate("");
        this.setMonth("0");
        this.setPersonalNoRange("");
        this.setFileType("");
        //this.setYear("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /**
     * Getter And Setter
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPlaceOfWorking() {
        return placeOfWorking;
    }

    public void setPlaceOfWorking(String placeOfWorking) {
        this.placeOfWorking = placeOfWorking;
    }

    public String getDemandFileName() {
        return demandFileName;
    }

    public void setDemandFileName(String demandFileName) {
        this.demandFileName = demandFileName;
    }

    public boolean isExcelLink() {
        return excelLink;
    }

    public void setExcelLink(boolean excelLink) {
        this.excelLink = excelLink;
    }

    public List<SelectItem> getPlaceOfWorkingList() {
        return placeOfWorkingList;
    }

    public void setPlaceOfWorkingList(List<SelectItem> placeOfWorkingList) {
        this.placeOfWorkingList = placeOfWorkingList;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getPersonalNoRange() {
        return personalNoRange;
    }

    public void setPersonalNoRange(String personalNoRange) {
        this.personalNoRange = personalNoRange;
    }

    public List<SelectItem> getPersonalNoRangeList() {
        return personalNoRangeList;
    }

    public void setPersonalNoRangeList(List<SelectItem> personalNoRangeList) {
        this.personalNoRangeList = personalNoRangeList;
    }

    public String getDisplayRange() {
        return displayRange;
    }

    public void setDisplayRange(String displayRange) {
        this.displayRange = displayRange;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public String getBnkCode() {
        return bnkCode;
    }

    public void setBnkCode(String bnkCode) {
        this.bnkCode = bnkCode;
    }
}
