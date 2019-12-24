/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.neftrtgs;

import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.pojo.neftrtgs.NeftRtgsReportPojo;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class UtrStatus extends BaseBean {

    private String message;
    private String reportType;
    private String status;
    private String processType;
    private List<SelectItem> processTypeList;
    private String neftBank;
    private List<SelectItem> neftBankList;
    private String npciStatus;
    private String frdt;
    private String todt;
    private String enableNeft = "none";
    private String enableNpci = "none";
    private String enableNeftBank = "none";
    private List<SelectItem> reportTypeList;
    private List<SelectItem> statusList;
    private List<SelectItem> npciStatusList;
    private List<NeftRtgsReportPojo> resultList;
    private UploadNeftRtgsMgmtFacadeRemote remote;
    private NpciMgmtFacadeRemote remoteBene;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String dateGridDisplay = "none";
    private String enableTrsNoGrid = "none";
    private String dt;
    private String trsNo;
    private String alphaCode;
    private List<SelectItem> trsNoList;

    public UtrStatus() {
        this.setMessage("");
        try {
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            remoteBene = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            resultList = new ArrayList<>();

            reportTypeList = new ArrayList<>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("NEFT", "NEFT"));
//            reportTypeList.add(new SelectItem("NPCI APBS", "NPCI APBS"));  //Do not delete these
//            reportTypeList.add(new SelectItem("NPCI ACH", "NPCI ACH"));
//            reportTypeList.add(new SelectItem("NPCI ACH PERIOD", "ACH PERIOD"));
//            reportTypeList.add(new SelectItem("NPCI ECS", "NPCI ECS"));
//            reportTypeList.add(new SelectItem("ECS-DR", "NPCI ECS DR"));
//            reportTypeList.add(new SelectItem("ACH-DR", "NPCI ACH DR"));

            statusList = new ArrayList<>();
            statusList.add(new SelectItem("S", "--Select--"));
            String alphaCode = common.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                statusList.add(new SelectItem("ALL", "ALL"));
                statusList.add(new SelectItem("Success", "Success"));
                statusList.add(new SelectItem("Unsuccess", "Unsuccess"));
                statusList.add(new SelectItem("Mismatch", "Mismatch"));
                statusList.add(new SelectItem("Sent IDBI", "Sent To IDBI"));
                statusList.add(new SelectItem("Sponsor", "Sponsor Bank"));
                statusList.add(new SelectItem("This Utr Already Processed.", "Utr Already Processed"));
            } else {
                statusList.add(new SelectItem("Success", "Success"));
                statusList.add(new SelectItem("Unsuccess", "Unsuccess"));
            }



            npciStatusList = new ArrayList<>();
            npciStatusList.add(new SelectItem("S", "--Select--"));
            npciStatusList.add(new SelectItem("ALL", "ALL"));
            npciStatusList.add(new SelectItem("S", "Success"));
            npciStatusList.add(new SelectItem("U", "Unsuccess"));

            processTypeList = new ArrayList<>();
            processTypeList.add(new SelectItem("--Select--"));
            processTypeList.add(new SelectItem("AUTO"));
            processTypeList.add(new SelectItem("MANUAL"));

            trsNoList = new ArrayList<>();
            trsNoList.add(new SelectItem("--Select--"));

            this.setFrdt(getTodayDate());
            this.setTodt(getTodayDate());
            this.setDt(getTodayDate());

            this.enableNeft = "none";
            this.enableNpci = "none";
            this.enableNeftBank = "none";
            this.dateGridDisplay = "";
            this.enableTrsNoGrid = "none";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processFunction() {
        this.setMessage("");
        this.enableNeft = "none";
        this.enableNpci = "none";
        this.enableNeftBank = "none";
        this.dateGridDisplay = "none";
        this.enableTrsNoGrid = "none";
        if (reportType.equalsIgnoreCase("NEFT")) {
            this.enableNeft = "";
            this.enableNpci = "none";
            this.enableNeftBank = "";
            this.dateGridDisplay = "";
            this.enableTrsNoGrid = "none";
        } else if (reportType.equalsIgnoreCase("NPCI APBS")
                || reportType.equalsIgnoreCase("NPCI ECS")
                || reportType.equalsIgnoreCase("ECS-DR")
                || reportType.equalsIgnoreCase("ACH-DR")
                || reportType.equalsIgnoreCase("NPCI ACH PERIOD")) {
            this.enableNeft = "none";
            this.enableNpci = "";
            this.dateGridDisplay = "";
            this.enableTrsNoGrid = "none";
        } else if (reportType.equalsIgnoreCase("NPCI ACH")) {
            this.enableNeft = "none";
            this.enableNpci = "";
            this.dateGridDisplay = "none";
            this.enableTrsNoGrid = "";
        }
    }

    public void getAllTrsNo() {
        this.setMessage("");
        trsNoList = new ArrayList<SelectItem>();
        trsNoList.add(new SelectItem("--Select--"));
        try {
            List list = remoteBene.getAllAchTrsNo("ACH", ymd.format(dmy.parse(this.dt)));
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                trsNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processTypeAction() {
        this.setMessage("");
        try {
            if (this.processType == null || this.processType.equals("--Select--")) {
                this.setMessage("Please select process type.");
                return;
            }
            neftBankList = new ArrayList<SelectItem>();
            neftBankList.add(new SelectItem("--Select--"));
//            List<CbsAutoNeftDetails> list = remote.getAutoNeftDetailsByProcess(this.processType);
            List<CbsAutoNeftDetails> list = remote.getNeftMisMatchBankName(this.processType);
            if (list.isEmpty()) {
                this.setMessage("There is no banks for manual inward uploading.");
                return;
            }
            for (CbsAutoNeftDetails obj : list) {
                neftBankList.add(new SelectItem(obj.getNeftBankName()));
            }
            this.setMessage("Please select neft bank.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equals("S")) {
                this.setMessage("Please select Report Type !");
                return;
            }
            if (this.reportType.equalsIgnoreCase("NEFT")) {
                if (this.status == null || this.status.equals("S")) {
                    this.setMessage("Please select Status !");
                    return;
                }

                if (this.processType.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select Process Type !");
                    return;
                }

                if (this.neftBank.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select Neft Bank !");
                    return;
                }
            }
            if (this.reportType.equalsIgnoreCase("NPCI")) {
                if (this.npciStatus == null || this.npciStatus.equals("S")) {
                    this.setMessage("Please select Status !");
                    return;
                }
            }

            if (!reportType.equalsIgnoreCase("NPCI ACH")) {
                if (this.frdt == null) {
                    this.setMessage("Please select from date !");
                    return;
                }
                if (this.todt == null) {
                    this.setMessage("Please select to date !");
                    return;
                }
                this.dt = dmy.format(new Date());
                this.trsNo = "0";
            } else {
                if (this.dt == null || this.dt.equalsIgnoreCase("")) {
                    this.setMessage("Please fill date !");
                    return;
                }
                if (this.trsNo == null || this.trsNo.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select Trs No !");
                    return;
                }
            }

            if (reportType.equalsIgnoreCase("NEFT")) {
//                if (!reportOpenAtPosition(getOrgnBrCode(), this.reportType, this.status).equalsIgnoreCase("open")) {
//                    this.setMessage("This report can not be open at this branch.");
//                    return;
//                }
                resultList = remote.getNeftRtgsReportStatus(dmy.parse(frdt), dmy.parse(todt), this.status,
                        getOrgnBrCode(), this.processType, this.neftBank);
            } else {
//                if (!reportOpenAtPosition(getOrgnBrCode(), this.reportType, this.npciStatus).equalsIgnoreCase("open")) {
//                    this.setMessage("This report can not be open at this branch.");
//                    return;
//                }
                resultList = remoteBene.getNpciReportData(ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)),
                        this.npciStatus, getOrgnBrCode(), reportType, ymd.format(dmy.parse(this.dt)), Double.parseDouble(this.trsNo));
            }

            String hType = "", headerColumn = "", h1Column = "", h2Column = "";
            String repType = "", fileName = "";

            if (reportType.equalsIgnoreCase("NPCI APBS")) {
                hType = "AAdhar No.";
                repType = "NPCI APBS";
            } else if (reportType.equalsIgnoreCase("NPCI ACH") || reportType.equalsIgnoreCase("NPCI ACH PERIOD")) {
                hType = "Account No.";
                repType = "NPCI ACH";
            } else if (reportType.equalsIgnoreCase("NPCI ECS")) {
                hType = "Account No.";
                repType = "NPCI ECS";
                headerColumn = "-------------------------------------";
                h1Column = "Sender Bank Code";
                h2Column = "Sender Name";
            } else if (reportType.equalsIgnoreCase("ECS-DR")) {
                hType = "Status";
                repType = "ECS-DR";
                h1Column = "Reason";
            } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                hType = "Status";
                repType = "ACH-DR";
                h1Column = "Reason";
            }

            if (!resultList.isEmpty()) {
                String reportName = "Neft-Rtgs Status Report";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

                if (!reportType.equalsIgnoreCase("NEFT")) {
                    fileName = resultList.get(0).getFileName();
                }


                Map fillParams = new HashMap();
                fillParams.put("printedBy", getUserName());
                if (reportType.equalsIgnoreCase("ECS-DR")) {
                    fillParams.put("reportName", "ECS-DR Status Report");
                } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                    fillParams.put("reportName", "ACH-DR Status Report");
                } else if (reportType.equalsIgnoreCase("NEFT")) {
                    fillParams.put("reportName", reportName);
                } else {
                    fillParams.put("reportName", "NPCI Status Report");
                }

                if (reportType.equalsIgnoreCase("ECS-DR") || reportType.equalsIgnoreCase("ACH-DR")) {
                    if (npciStatus.equalsIgnoreCase("U")) {
                        npciStatus = "Unsuccess";
                    } else if (npciStatus.equalsIgnoreCase("S")) {
                        npciStatus = "Success";
                    } else {
                        npciStatus = "ALL";
                    }
                }
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("frDt", getFrdt());
                fillParams.put("toDt", getTodt());
                fillParams.put("status", getStatus());
                fillParams.put("pHeader", hType);
                fillParams.put("pNpaStatus", npciStatus);
                fillParams.put("pRepType", repType);
                fillParams.put("pH1Col", h1Column);
                fillParams.put("pH2Col", h2Column);
                fillParams.put("pHeaderCol", headerColumn);
                if (reportType.equalsIgnoreCase("NPCI ACH")) {
                    fillParams.put("fileName", "File Name : " + fileName);
                }

                if (getStatus().equalsIgnoreCase("All") || getStatus().equalsIgnoreCase("Mismatch")) {
                    fillParams.put("showColumn", "Y");
                } else {
                    fillParams.put("showColumn", "N");
                }
                if (reportType.equalsIgnoreCase("NEFT")) {
                    new ReportBean().jasperReport("utrStatusReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                } else if (reportType.equalsIgnoreCase("ECS-DR") || reportType.equalsIgnoreCase("ACH-DR")) {
                    new ReportBean().jasperReport("NpciEcsDr", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                } else {
                    new ReportBean().jasperReport("NpciStatus", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                }

                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equals("S")) {
                this.setMessage("Please select Report Type !");
                return;
            }
            if (this.reportType.equalsIgnoreCase("NEFT")) {
                if (this.status == null || this.status.equals("S")) {
                    this.setMessage("Please select Status !");
                    return;
                }

                if (this.processType.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select Process Type !");
                    return;
                }

                if (this.neftBank.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select Neft Bank !");
                    return;
                }
            }
            if (this.reportType.equalsIgnoreCase("NPCI")) {
                if (this.npciStatus == null || this.npciStatus.equals("S")) {
                    this.setMessage("Please select Status !");
                    return;
                }
            }
            if (!reportType.equalsIgnoreCase("NPCI ACH")) {
                if (this.frdt == null) {
                    this.setMessage("Please select from date !");
                    return;
                }
                if (this.todt == null) {
                    this.setMessage("Please select to date !");
                    return;
                }
                this.dt = dmy.format(new Date());
                this.trsNo = "0";
            } else {
                if (this.dt == null || this.dt.equalsIgnoreCase("")) {
                    this.setMessage("Please fill date !");
                    return;
                }
                if (this.trsNo == null || this.trsNo.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select Trs No !");
                    return;
                }
            }
            if (reportType.equalsIgnoreCase("NEFT")) {
//                if (!reportOpenAtPosition(getOrgnBrCode(), this.reportType, this.status).equalsIgnoreCase("open")) {
//                    this.setMessage("This report can not be open at this branch.");
//                    return;
//                }
                resultList = remote.getNeftRtgsReportStatus(dmy.parse(frdt), dmy.parse(todt), this.status, getOrgnBrCode(), this.processType, this.neftBank);
            } else {
//                if (!reportOpenAtPosition(getOrgnBrCode(), this.reportType, this.npciStatus).equalsIgnoreCase("open")) {
//                    this.setMessage("This report can not be open at this branch.");
//                    return;
//                }
                resultList = remoteBene.getNpciReportData(ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)),
                        this.npciStatus, getOrgnBrCode(), reportType, ymd.format(dmy.parse(this.dt)),
                        Double.parseDouble(this.trsNo));
            }

            String hType = "", headerColumn = "", h1Column = "", h2Column = "";
            String repType = "", fileName = "";

            if (reportType.equalsIgnoreCase("NPCI APBS")) {
                hType = "AAdhar No.";
                repType = "NPCI APBS";
            } else if (reportType.equalsIgnoreCase("NPCI ACH") || reportType.equalsIgnoreCase("NPCI ACH PERIOD")) {
                hType = "Account No.";
                repType = "NPCI ACH";
            } else if (reportType.equalsIgnoreCase("NPCI ECS")) {
                hType = "Account No.";
                repType = "NPCI ECS";
                headerColumn = "-----------------------------------------------------------------";
                h1Column = "Sender Bank Code";
                h2Column = "Sender Name";
            } else if (reportType.equalsIgnoreCase("ECS-DR")) {
                hType = "Status";
                repType = "ECS-DR";
                h1Column = "Reason";
            } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                hType = "Status";
                repType = "ACH-DR";
                h1Column = "Reason";
            }

            if (!resultList.isEmpty()) {
                String reportName = "Neft-Rtgs Status Report";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

                if (!reportType.equalsIgnoreCase("NEFT")) {
                    fileName = resultList.get(0).getFileName();
                }
                Map fillParams = new HashMap();
                fillParams.put("printedBy", getUserName());
                if (reportType.equalsIgnoreCase("ECS-DR")) {
                    fillParams.put("reportName", "ECS-DR Status Report");
                } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                    fillParams.put("reportName", "ACH-DR Status Report");
                } else if (reportType.equalsIgnoreCase("NEFT")) {
                    fillParams.put("reportName", reportName);
                } else {
                    fillParams.put("reportName", "NPCI Status Report");
                }
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("frDt", getFrdt());
                fillParams.put("toDt", getTodt());
                fillParams.put("status", getStatus());
                fillParams.put("pHeader", hType);
                fillParams.put("pNpaStatus", npciStatus);
                fillParams.put("pRepType", repType);
                fillParams.put("pH1Col", h1Column);
                fillParams.put("pH2Col", h2Column);
                fillParams.put("pHeaderCol", headerColumn);
                if (reportType.equalsIgnoreCase("NPCI ACH")) {
                    fillParams.put("fileName", "File Name : " + fileName);
                }
                if (getStatus().equalsIgnoreCase("All") || getStatus().equalsIgnoreCase("Mismatch")) {
                    fillParams.put("showColumn", "Y");
                } else {
                    fillParams.put("showColumn", "N");
                }
                if (reportType.equalsIgnoreCase("NEFT")) {
                    new ReportBean().openPdf("utrStatusReport", "utrStatusReport", new JRBeanCollectionDataSource(resultList), fillParams);
                } else if (reportType.equalsIgnoreCase("ECS-DR") || reportType.equalsIgnoreCase("ACH-DR")) {
                    new ReportBean().openPdf("NpciEcsDr", "NpciEcsDr", new JRBeanCollectionDataSource(resultList), fillParams);
                } else {
                    new ReportBean().openPdf("NpciStatus", "NpciStatus", new JRBeanCollectionDataSource(resultList), fillParams);
                }

                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

//    public String reportOpenAtPosition(String orgnBrCode, String reportType, String status) {
//        try {
//            String alphaCode = common.getAlphacodeByBrncode(orgnBrCode);
//            if (!alphaCode.equalsIgnoreCase("HO")) {
//                if (reportType.equalsIgnoreCase("NPCI APBS")) {
//                    return "This report can not be open at this branch.";
//                }
//                if (reportType.equalsIgnoreCase("NEFT") && !status.equalsIgnoreCase("Success")) {
//                    return "This report can not be open at this branch.";
//                }
//            }
//        } catch (Exception ex) {
//            this.setMessage(ex.getMessage());
//            return "This report can not be open at this branch.";
//        }
//        return "open";
//    }
    public void btnRefreshAction() {
        this.setMessage("");
        this.setFrdt(getTodayDate());
        this.setTodt(getTodayDate());
        this.setDt(getTodayDate());
        this.setReportType("S");
        this.setStatus("S");
        this.setNpciStatus("S");
        this.enableNeft = "none";
        this.enableNpci = "none";
        this.enableNeftBank = "none";
        this.dateGridDisplay = "";
        this.enableTrsNoGrid = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public String getNpciStatus() {
        return npciStatus;
    }

    public void setNpciStatus(String npciStatus) {
        this.npciStatus = npciStatus;
    }

    public String getEnableNeft() {
        return enableNeft;
    }

    public void setEnableNeft(String enableNeft) {
        this.enableNeft = enableNeft;
    }

    public String getEnableNpci() {
        return enableNpci;
    }

    public void setEnableNpci(String enableNpci) {
        this.enableNpci = enableNpci;
    }

    public List<SelectItem> getNpciStatusList() {
        return npciStatusList;
    }

    public void setNpciStatusList(List<SelectItem> npciStatusList) {
        this.npciStatusList = npciStatusList;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public List<SelectItem> getProcessTypeList() {
        return processTypeList;
    }

    public void setProcessTypeList(List<SelectItem> processTypeList) {
        this.processTypeList = processTypeList;
    }

    public String getNeftBank() {
        return neftBank;
    }

    public void setNeftBank(String neftBank) {
        this.neftBank = neftBank;
    }

    public List<SelectItem> getNeftBankList() {
        return neftBankList;
    }

    public void setNeftBankList(List<SelectItem> neftBankList) {
        this.neftBankList = neftBankList;
    }

    public String getEnableNeftBank() {
        return enableNeftBank;
    }

    public void setEnableNeftBank(String enableNeftBank) {
        this.enableNeftBank = enableNeftBank;
    }

    public String getDateGridDisplay() {
        return dateGridDisplay;
    }

    public void setDateGridDisplay(String dateGridDisplay) {
        this.dateGridDisplay = dateGridDisplay;
    }

    public String getEnableTrsNoGrid() {
        return enableTrsNoGrid;
    }

    public void setEnableTrsNoGrid(String enableTrsNoGrid) {
        this.enableTrsNoGrid = enableTrsNoGrid;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(String trsNo) {
        this.trsNo = trsNo;
    }

    public List<SelectItem> getTrsNoList() {
        return trsNoList;
    }

    public void setTrsNoList(List<SelectItem> trsNoList) {
        this.trsNoList = trsNoList;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }
}
