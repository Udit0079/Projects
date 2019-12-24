/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.neftrtgs;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AdharRegistrationDetailPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class AAdharRegistrationGenerate extends BaseBean {

    private String message;
    private String branch;
    private String adhartype;
    private String filter;
    private String fromdate;
    private String todate;
    private String displayDate = "none";
    private String fileDownloadPanel = "none";
    private boolean btnflag;
    private NpciFileDto currentItem;
    private List<NpciFileDto> gridDetail;
    private List<SelectItem> branchList;
    private List<SelectItem> adharList;
    private List<SelectItem> filterList;
    private String function;
    private List<SelectItem> functionList;
    private String btnValue;
    private String show;
    private String dateButton;
    private String dateButton1;
    boolean disblefrDt;
    List<AdharRegistrationDetailPojo> reportList = new ArrayList<AdharRegistrationDetailPojo>();
    private CommonReportMethodsRemote common;
    private NpciMgmtFacadeRemote npciFacade;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public AAdharRegistrationGenerate() {
        try {
            fromdate = dmy.format(date);
            todate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            List brnLst = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(1).toString().length() < 2 ? "0" + ele7.get(1).toString() : ele7.get(1).toString(), ele7.get(0).toString()));
                }
            }
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("GF", "Generate File"));
            functionList.add(new SelectItem("SF", "Show File"));

            filterList = new ArrayList<SelectItem>();
            filterList.add(new SelectItem("S", "--Select--"));
            filterList.add(new SelectItem("All", "All Date"));
            filterList.add(new SelectItem("Dt", "Date Wise"));
            this.setBtnValue("Generate File");
            this.setFunction("--Select--");
            btnRefreshAction();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setMessage("");
        try {
            if (this.function == null || this.function.equals("")) {
                this.setMessage("Please select function.");
                return;
            }
            if (this.function.equals("GF")) {
                this.setBtnValue("Generate File");
            } else if (this.function.equals("SF")) {
                this.setBtnValue("Show File");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void hideDate() {
        try {
            if (function.equalsIgnoreCase("GF")) {
                if (filter.equalsIgnoreCase("All")) {
                    this.displayDate = "none";
                } else {
                    this.displayDate = "";
                    this.show = "";
                }
            } else {
                if (filter.equalsIgnoreCase("All")) {
                    this.displayDate = "none";
                } else {
                    this.displayDate = "";
                    this.show = "none";
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        this.setMessage("");
        try {
            if (this.adhartype == null || this.getAdhartype().equalsIgnoreCase("S")) {
                setMessage("Please select Aadhaar Type !");
                return false;
            }

            if (this.filter == null || this.getFilter().equalsIgnoreCase("S")) {
                setMessage("Please select Filter Type !");
                return false;
            }

            if (this.fromdate == null || this.getFromdate().equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return false;
            }

            if (!Validator.validateDate(fromdate)) {
                setMessage("Please select Proper from date");
                return false;
            }
            if (this.todate == null || this.getTodate().equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return false;
            }
            if (!Validator.validateDate(todate)) {
                setMessage("Please select Proper to Date");
                return false;
            }
            if (dmy.parse(fromdate).after(date)) {
                setMessage("From date should be less than current date !");
                return false;
            }

            if (dmy.parse(todate).after(date)) {
                setMessage("To date should be less than current date !");
                return false;
            }

            if (dmy.parse(fromdate).after(dmy.parse(todate))) {
                setMessage("From Date should be less than To date !");
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public void genAadharFile() {
        try {
            if (!getOrgnBrCode().equals("90")) {
                this.setMessage("File generation is not allowed from branch.");
                return;
            }
//            if (this.adhartype == null || this.adhartype.equals("") || this.adhartype.equals("S")) {
//                this.setMessage("Adhar type can not blank.");
//                return;
//            }
            if (this.filter == null || this.filter.equals("") || this.filter.equals("S")) {
                this.setMessage("Filter type can not blank.");
                return;
            }
            if (function.equalsIgnoreCase("GF")) {
                if (!this.branch.equals("90") && !this.adhartype.equals("NA")) {
                    List list = npciFacade.getBankDetails();
                    Vector ele = (Vector) list.get(0);
                    if (ele.get(8) == null || ele.get(9) == null
                            || ele.get(8).toString().trim().equals("")
                            || ele.get(9).toString().trim().equals("")) {
                        this.setMessage("Please define Aadhar Location / NPCI Bank Code in bank detail.");
                        return;
                    }
                    String dirName = ele.get(8).toString().trim();
                    String npciBankCode = ele.get(9).toString().trim();
                    //File generation for aadhar lookup.
                    String fileName = npciFacade.aadharLookUpFileGeneration(this.branch, this.adhartype, this.filter,
                            ymd.format(dmy.parse(fromdate)), ymd.format(dmy.parse(todate)), dirName, npciBankCode,
                            getOrgnBrCode(), getUserName());
                    if (fileName == null || fileName.equals("")) {
                        this.setMessage("There is problem in file generation.");
                        return;
                    } else {
                        this.setMessage("File generation Successfully.");
                    }
                    //Loading all current files.
//                    gridDetail = npciFacade.showAadharLookUpFiles("AL", ymd.format(date));
//                    this.fileDownloadPanel = "";
                } else {
                    this.setMessage("File can only be generated for branch and aadhar type.");
                    return;
                }
            } else {
                //Loading all current files.
                gridDetail = npciFacade.showAadharLookUpFiles("AL", ymd.format(dmy.parse(fromdate)));
                this.fileDownloadPanel = "";
            }
            refreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            if (currentItem == null) {
                this.setMessage("Please download one file from table.");
                return;
            }
            List list = npciFacade.getBankDetails();
            Vector ele = (Vector) list.get(0);
            if (ele.get(8) == null) {
                this.setMessage("Please define Aadhar Location in bank detail.");
                return;
            }
            String dirName = ele.get(8).toString().trim();

            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAdhartype("S");
        this.setFilter("S");
        this.setFunction("S");
        this.displayDate = "none";
        this.fileDownloadPanel = "none";
    }

    public void refreshAction() {
        this.setAdhartype("S");
        this.setFilter("S");
        this.setFunction("S");
        this.displayDate = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

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

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getAdhartype() {
        return adhartype;
    }

    public void setAdhartype(String adhartype) {
        this.adhartype = adhartype;
    }

    public List<SelectItem> getAdharList() {
        return adharList;
    }

    public void setAdharList(List<SelectItem> adharList) {
        this.adharList = adharList;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<SelectItem> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<SelectItem> filterList) {
        this.filterList = filterList;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isBtnflag() {
        return btnflag;
    }

    public void setBtnflag(boolean btnflag) {
        this.btnflag = btnflag;
    }

    public String getFileDownloadPanel() {
        return fileDownloadPanel;
    }

    public void setFileDownloadPanel(String fileDownloadPanel) {
        this.fileDownloadPanel = fileDownloadPanel;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NpciFileDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciFileDto currentItem) {
        this.currentItem = currentItem;
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

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getDateButton() {
        return dateButton;
    }

    public void setDateButton(String dateButton) {
        this.dateButton = dateButton;
    }

    public String getDateButton1() {
        return dateButton1;
    }

    public void setDateButton1(String dateButton1) {
        this.dateButton1 = dateButton1;
    }

    public boolean isDisblefrDt() {
        return disblefrDt;
    }

    public void setDisblefrDt(boolean disblefrDt) {
        this.disblefrDt = disblefrDt;
    }
}
