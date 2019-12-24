/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.FdRdFacadeRemote;
import com.cbs.pojo.VoucherPrintingPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Spellar;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class VoucherPrintingALL extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private String curDate;
    private Date date = new Date();
    private boolean checkBox;
    private boolean gridCheckBoxDisable;
    private boolean checkBoxAllDisable;
    List<VoucherPrintingPojo> gridList;
    private boolean checkBoxAll;
    private boolean allSelected;
    private CommonReportMethodsRemote common;
    private FdRdFacadeRemote remoteFdFacade;
    FtsPostingMgmtFacadeRemote fts;
    private PrintFacadeRemote beanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<VoucherPrintingPojo> reportList = new ArrayList<VoucherPrintingPojo>();
    NumberFormat amtFormatter = new DecimalFormat("0.00");

    public VoucherPrintingALL() {
        try {
            this.setCurDate(dmy.format(date));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFdFacade = (FdRdFacadeRemote) ServiceLocator.getInstance().lookup("FdRdFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup("PrintManagementFacade");
            branchList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
                }
            }

            reportOptionList = new ArrayList<>();
            reportOptionList.add(new SelectItem("0", "--Select--"));
            reportOptionList.add(new SelectItem("1", "System Generate"));
            reportOptionList.add(new SelectItem("2", "Manual Generate"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setAllBoxSelected() {
        try {
            System.out.println("In All Selected CheckBox.");
            if (allSelected) {
                for (VoucherPrintingPojo pojo : gridList) {
                    pojo.setSelected(true);
                }
            } else {
                for (VoucherPrintingPojo pojo : gridList) {
                    pojo.setSelected(false);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

//    public void checkAll() {
//        setMessage("");
//        if (this.checkBoxAll == true) {
//            for (int i = 0; i < gridList.size(); i++) {
//                gridList.get(i).setCheckBox(true);
//            }
//            this.gridCheckBoxDisable = true;
//        } else {
//            for (int i = 0; i < gridList.size(); i++) {
//                gridList.get(i).setCheckBox(false);
//            }
//            this.gridCheckBoxDisable = false;
//        }
//    }

    public void gridLoad() {
        setMessage("");
        try {
            List result = remoteFdFacade.getVoucherPrintinALLgData(branch, reportOption, ymd.format(dmy.parse(curDate)));
            gridList = new ArrayList<>();
            if (!result.isEmpty()) {

                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    VoucherPrintingPojo obj = new VoucherPrintingPojo();
                    obj.setsNo(i + 1);
                    obj.setAcName(vtr.get(0).toString());
                    obj.setAcNo(vtr.get(1).toString());
                    obj.setDate(vtr.get(2).toString());
                    obj.setDebitAmt(amtFormatter.format(Double.parseDouble(vtr.get(3).toString())));
                    obj.setCreditAmt(amtFormatter.format(Double.parseDouble(vtr.get(4).toString())));
                    obj.setTy(vtr.get(5).toString());
                    obj.setTranType(vtr.get(6).toString());
                    obj.setVoucherNo(vtr.get(7).toString());
                    obj.setTrsno(vtr.get(8).toString());
                    obj.setPayby(vtr.get(9).toString());
                    if (vtr.get(10).toString().toUpperCase().contains("PO") && vtr.get(10).toString().contains("~")
                            && (vtr.get(10).toString().toUpperCase().contains("PAID")
                            || vtr.get(10).toString().toUpperCase().contains("ISSUED"))) {
                        obj.setParticular(vtr.get(10).toString().substring(0, vtr.get(10).toString().indexOf("~")));
                    } else {
                        obj.setParticular(vtr.get(10).toString());
                    }
                    obj.setDetails(vtr.get(10).toString());
                    obj.setChequeNo(vtr.get(14).toString());
                    //obj.setCheckBox(false);
                    //obj.setSelected(true);
                    //allSelected = true;
                    gridList.add(obj);
                }
            } else {
                throw new ApplicationException("Data does not exist !");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void printReport() {
        setMessage("");
        try {
            List list;
            String SeqNo = "", CustName = "", InFavourOf = "", PayableAt = "";
            BigDecimal Amount = new BigDecimal("0");
            Spellar splObj = new Spellar();
            for (VoucherPrintingPojo obj : gridList) {
                VoucherPrintingPojo pojo = new VoucherPrintingPojo();
                if (obj.isSelected()) {
                    pojo.setAcName(obj.getAcName());
                    pojo.setAcNo(obj.getAcNo());
                    pojo.setActNature(fts.getAcNatureByCode(obj.getAcNo().substring(2, 4)));
                    pojo.setDate(obj.getDate());
                    pojo.setDebitAmt(obj.getDebitAmt());
                    pojo.setCreditAmt(obj.getCreditAmt());
                    pojo.setTy(obj.getTy());
                    pojo.setTranType(obj.getTranType());
                    pojo.setVoucherNo(obj.getVoucherNo());
                    pojo.setTrsno(obj.getTrsno());
                    pojo.setPayby(obj.getPayby());
                    pojo.setParticular(obj.getParticular());
                    pojo.setDetails(obj.getDetails());
                    pojo.setChequeNo(obj.getChequeNo());
                    pojo.setFavour("");
                    pojo.setPayee("");

                    if (obj.getDetails().toUpperCase().contains("PO") && obj.getDetails().toUpperCase().contains("PAID")) {
                        list = beanRemote.getPOPaidDetail(obj.getChequeNo(), ymd.format(dmy.parse(obj.getDate())), common.getAlphacodeByBrncode(this.branch));
                        Vector element = (Vector) list.get(0);
                        SeqNo = element.get(0).toString();
                        CustName = element.get(1).toString();
                        Amount = new BigDecimal(element.get(2).toString());
                        InFavourOf = element.get(3).toString();
                        PayableAt = element.get(4).toString();
                        pojo.setFavour(InFavourOf);
                        pojo.setPayee(PayableAt);
                    } else if (obj.getDetails().toUpperCase().contains("PO") && obj.getDetails().toUpperCase().contains("ISSUED")) {
//                        list = beanRemote.getPODetail(obj.getChequeNo(), obj.getDate(), this.getOrgnBrCode());
//                        Vector element = (Vector) list.get(0);
//                        SeqNo = element.get(0).toString();
//                        CustName = element.get(1).toString();
//                        Amount = new BigDecimal(element.get(2).toString());
//                        InFavourOf = element.get(3).toString();
//                        PayableAt = element.get(4).toString();
                        pojo.setFavour("");
                        pojo.setPayee("");
                    } else if (obj.getParticular().toUpperCase().contains("LOCKER RENT")) {
                    }
                    // splObj.spellAmount(obj.getTy().equalsIgnoreCase("0") ? obj.getCrAmt() : obj.getDrAmt());
                    pojo.setAmtWord(splObj.spellAmount(obj.getTy().equalsIgnoreCase("0") ? Double.parseDouble(obj.getCreditAmt()): Double.parseDouble(obj.getDebitAmt())).replace("Rupees", ""));
                    reportList.add(pojo);
                }
            }
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(branch);
            fillParams.put("pBankName", brNameAndAddList.get(0));
            fillParams.put("pBankAdd", brNameAndAddList.get(1));

            new ReportBean().openPdf("Voucher Printing_" + common.getAlphacodeByBrncode(branch) + "_" + ymd.format(dmy.parse(getTodayDate())), "VoucherPrintingAll", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", "Voucher Printing ALL");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshPage() {
        setMessage("");
        gridList = new ArrayList<>();
    }

    public String exitPage() {
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

    public String getReportOption() {
        return reportOption;
    }

    public void setReportOption(String reportOption) {
        this.reportOption = reportOption;
    }

    public List<SelectItem> getReportOptionList() {
        return reportOptionList;
    }

    public void setReportOptionList(List<SelectItem> reportOptionList) {
        this.reportOptionList = reportOptionList;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<VoucherPrintingPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<VoucherPrintingPojo> reportList) {
        this.reportList = reportList;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public boolean isGridCheckBoxDisable() {
        return gridCheckBoxDisable;
    }

    public void setGridCheckBoxDisable(boolean gridCheckBoxDisable) {
        this.gridCheckBoxDisable = gridCheckBoxDisable;
    }

    public List<VoucherPrintingPojo> getGridList() {
        return gridList;
    }

    public void setGridList(List<VoucherPrintingPojo> gridList) {
        this.gridList = gridList;
    }

    public boolean isCheckBoxAllDisable() {
        return checkBoxAllDisable;
    }

    public void setCheckBoxAllDisable(boolean checkBoxAllDisable) {
        this.checkBoxAllDisable = checkBoxAllDisable;
    }

    public boolean isCheckBoxAll() {
        return checkBoxAll;
    }

    public void setCheckBoxAll(boolean checkBoxAll) {
        this.checkBoxAll = checkBoxAll;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
}
