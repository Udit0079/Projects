/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.other.PmSchemeRegDto;
import com.cbs.dto.other.SortedByStatus;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.SSSFileGeneratorFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

public class RenewSssSchemeBean extends BaseBean {

    private String message;
    private String schemeCode;
    private String vendorCode;
    private boolean reportFlag;
    private boolean calcBtnDisable;
    private boolean postBtnDisable;
    private List<SelectItem> schemeCodeList;
    private List<SelectItem> vendorCodeList;
    private List<PmSchemeRegDto> gridDetail;
    private List<PmSchemeRegDto> reportList;
    private CommonReportMethodsRemote commonReportRemote;
    private SSSFileGeneratorFacadeRemote sssReportRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private String viewID = "/pages/master/sm/test.jsp";

    public RenewSssSchemeBean() {
        try {
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            sssReportRemote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            vendorCodeList = new ArrayList<SelectItem>();
            vendorCodeList.add(new SelectItem("S", "--Select--"));
            getScheme();
            resetForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getScheme() {
        try {
            schemeCodeList = new ArrayList<SelectItem>();
            List list = commonReportRemote.getRefRecList("215");
            schemeCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                schemeCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void getVendor() {
        try {
            vendorCodeList = new ArrayList<SelectItem>();
            List list = sssReportRemote.getVendors(schemeCode);
            vendorCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                vendorCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void calculate() {
        this.reportFlag = false;
        this.postBtnDisable = true;
        try {
            if (this.schemeCode == null || this.schemeCode.equals("S")) {
                this.setMessage("Please select Scheme Name");
                return;
            }
            if (this.vendorCode == null || this.vendorCode.equals("S")) {
                this.setMessage("Please select Vendor Name");
                return;
            }
            List list = sssReportRemote.getSssRenewData(this.schemeCode, this.vendorCode, getOrgnBrCode());
            if (list.isEmpty()) {
                this.setMessage("There is no data to renew");
                return;
            }
            gridDetail = new ArrayList<PmSchemeRegDto>();   //Only active data
            reportList = new ArrayList<PmSchemeRegDto>();   //All report data
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                PmSchemeRegDto dto = new PmSchemeRegDto();

                dto.setAcno(ele.get(2).toString());
                dto.setName(ftsRemote.getCustName(dto.getAcno()));
                dto.setEnrollDate(ele.get(3).toString());

                String classification = ele.get(7).toString().trim();
//                System.out.println("Classification-->" + classification + "\n");
                dto.setClassification(classification);

                if (classification.equalsIgnoreCase("ACTIVE")) {
                    gridDetail.add(dto);
                }
                reportList.add(dto);
            }
            if (!reportList.isEmpty()) {
                String report = "Pradhanmantri Scheme Report";
                List vec = commonReportRemote.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();

                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());

                fillParams.put("pBankName", vec.get(0).toString());
                fillParams.put("pBankAdd", vec.get(1).toString());

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByStatus());
                Collections.sort(reportList, chObj);

                new ReportBean().jasperReport("pmrenewreport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
                this.setViewID("/report/ReportPagePopUp.jsp");
                setReportFlag(true);
            }
            if (!gridDetail.isEmpty()) {
                setPostBtnDisable(false);
                setCalcBtnDisable(true);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            if (this.schemeCode == null || this.schemeCode.equals("S")) {
                this.setMessage("Please select scheme name.");
                return;
            }
            if (this.vendorCode == null || this.vendorCode.equals("S")) {
                this.setMessage("Please select vendor name.");
                return;
            }
            if (gridDetail.isEmpty()) {
                this.setMessage("There is no data to renew.");
                return;
            }

            System.out.println("List Size Is-->" + gridDetail.size());
            for (int j = 0; j < gridDetail.size(); j++) {
                PmSchemeRegDto pojo = gridDetail.get(j);
                System.out.println("Sno Is-->" + (j + 1) + " And Classification Is-->" + pojo.getClassification());
            }

            String msg = sssReportRemote.sssRenewProcess(gridDetail, schemeCode, vendorCode,
                    getOrgnBrCode(), getUserName(), reportList);
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            this.setMessage("Renew process has been done successfully. Generated batch no is:" + msg.substring(4));
            gridDetail = new ArrayList<PmSchemeRegDto>();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setMessage("");
        this.setSchemeCode("S");
        this.setVendorCode("S");
        calcBtnDisable = false;
        postBtnDisable = true;
        reportFlag = false;
        gridDetail = new ArrayList<PmSchemeRegDto>();
    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public List<SelectItem> getVendorCodeList() {
        return vendorCodeList;
    }

    public void setVendorCodeList(List<SelectItem> vendorCodeList) {
        this.vendorCodeList = vendorCodeList;
    }

    public List<PmSchemeRegDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<PmSchemeRegDto> gridDetail) {
        this.gridDetail = gridDetail;
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

    public boolean isCalcBtnDisable() {
        return calcBtnDisable;
    }

    public void setCalcBtnDisable(boolean calcBtnDisable) {
        this.calcBtnDisable = calcBtnDisable;
    }

    public boolean isPostBtnDisable() {
        return postBtnDisable;
    }

    public void setPostBtnDisable(boolean postBtnDisable) {
        this.postBtnDisable = postBtnDisable;
    }

    public List<PmSchemeRegDto> getReportList() {
        return reportList;
    }

    public void setReportList(List<PmSchemeRegDto> reportList) {
        this.reportList = reportList;
    }
}
