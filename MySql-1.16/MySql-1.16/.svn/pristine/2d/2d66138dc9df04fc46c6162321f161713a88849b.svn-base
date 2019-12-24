/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.neftrtgs;

import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.neftrtgs.NeftRtgsReportPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AchReport extends BaseBean {

    private String message;
    private String reportType;
    private String date;
    private String seqNo;
    private String fromDate;
    private String todate;
    private String reportMode;
    private String status;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> seqNoList;
    private List<SelectItem> reportModeList;
    private List<SelectItem> statusList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private NpciMgmtFacadeRemote remoteBene;
    private CommonReportMethodsRemote common;
    private UploadNeftRtgsMgmtFacadeRemote remote;
    private List<NeftRtgsReportPojo> resultList;
    private String dateGridDisplay = "none";
    private String enableSeqNoGrid = "none";
    private List<SelectItem> branchList;
    private String brnCode;

    public AchReport() {
        this.message = "";
        try {
             remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            remoteBene = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            resultList = new ArrayList<NeftRtgsReportPojo>();

            branchList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeExcludingHo();
            if (this.getOrgnBrCode().equalsIgnoreCase("90")) {
                branchList.add(new SelectItem("ALL", "ALL BRANCH"));
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    branchList.add(new SelectItem(Integer.parseInt(vtr.get(0).toString()), vtr.get(1).toString()));
                }
            } else {
                list = common.getAlphacodeBasedOnBranch(this.getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    branchList.add(new SelectItem(Integer.parseInt(vtr.get(1).toString()), vtr.get(0).toString()));
                }
            }

            this.reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("ACH-CR", "ACH CR"));
            reportTypeList.add(new SelectItem("ACH-DR", "ACH DR"));
            reportTypeList.add(new SelectItem("ECS-CR", "ECS CR"));
            reportTypeList.add(new SelectItem("ECS-DR", "ECS DR"));
            reportTypeList.add(new SelectItem("NPCI APBS", "APBS"));

            this.reportModeList = new ArrayList<SelectItem>();
            reportModeList.add(new SelectItem("0", "--Select--"));
            reportModeList.add(new SelectItem("DTW", "Date Wise"));
            reportModeList.add(new SelectItem("SQW", "Sequence No. wise"));

            this.statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("ALL", "ALL"));
            statusList.add(new SelectItem("S", "Success"));
            statusList.add(new SelectItem("U", "Unsuccess"));

            this.seqNoList = new ArrayList<SelectItem>();

            this.fromDate = getTodayDate();
            this.todate = getTodayDate();
            this.date = getTodayDate();
        } catch (Exception ex) {
            this.message = ex.getMessage();
        }
    }

    public void processMode() {
//        if (reportType.equalsIgnoreCase("ECS-CR")) {
//            this.reportModeList = new ArrayList<SelectItem>();
//            reportModeList.add(new SelectItem("0", "--Select--"));
//            reportModeList.add(new SelectItem("DTW", "Date Wise"));
//        } else {
//            this.reportModeList = new ArrayList<SelectItem>();
//            reportModeList.add(new SelectItem("0", "--Select--"));
//            reportModeList.add(new SelectItem("DTW", "Date Wise"));
//            reportModeList.add(new SelectItem("SQW", "Sequence No. wise"));
//        }
    }

    public void processFunction() {
        this.setMessage("");
        this.dateGridDisplay = "none";
        this.enableSeqNoGrid = "none";
        if (reportType.equalsIgnoreCase("ACH-CR") || reportType.equalsIgnoreCase("ACH-DR")
                || reportType.equalsIgnoreCase("ECS-CR") || reportType.equalsIgnoreCase("ECS-DR") || reportType.equalsIgnoreCase("NPCI APBS")) {
            if (reportMode.equalsIgnoreCase("SQW")) {
                this.dateGridDisplay = "none";
                this.enableSeqNoGrid = "";
            } else if (reportMode.equalsIgnoreCase("DTW")) {
                this.dateGridDisplay = "";
                this.enableSeqNoGrid = "none";
            }
        }
    }

    public void getAllSeqNo() {
        this.setMessage("");
        seqNoList = new ArrayList<SelectItem>();
        seqNoList.add(new SelectItem("0", "--Select--"));
        try {
            List list = null;
            String iwType = "";
            if (reportType.equalsIgnoreCase("ACH-CR") || reportType.equalsIgnoreCase("ECS-CR") ||reportType.equalsIgnoreCase("NPCI APBS")) {
                if (reportType.equalsIgnoreCase("ACH-CR")) {
                    iwType = "ACH";
                } else if  (reportType.equalsIgnoreCase("NPCI APBS")){
                    iwType="APB";
                }else {
                    iwType = "ECS";
                }
                list = remoteBene.getAllAchTrsNo(iwType, ymd.format(dmy.parse(this.date)));
            } else if (reportType.equalsIgnoreCase("ACH-DR") || reportType.equalsIgnoreCase("ECS-DR")) {
                list = remoteBene.getAllAchDRFileSqnNo(reportType, ymd.format(dmy.parse(this.date)));
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                seqNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setFromDate(getTodayDate());
        this.setTodate(getTodayDate());
        this.setDate(getTodayDate());
        this.setReportType("0");
        this.setReportMode("0");
        this.setStatus("0");
        this.dateGridDisplay = "none";
        this.enableSeqNoGrid = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
                this.setMessage("Please select Report Type !");
                return;
            }

            if (this.status == null || this.status.equals("0")) {
                this.setMessage("Please select Status !");
                return;
            }
            if (this.reportMode == null || this.reportMode.equals("0")) {
                this.setMessage("Please select Mode Type !");
                return;
            } else if (this.reportMode.equalsIgnoreCase("DTW")) {
                date = "01/01/1990";
                if (this.fromDate == null || this.fromDate.equals("")) {
                    this.setMessage("Please Fill From date !");
                    return;
                }
                if (this.todate == null || this.todate.equals("")) {
                    this.setMessage("Please Fill To Date !");
                    return;
                }
            } else if (this.reportMode.equalsIgnoreCase("SQW")) {
                fromDate = "01/01/1990";
                todate = "01/01/1990";
                if (this.seqNo == null || this.seqNo.equals("S")) {
                    this.setMessage("Please select Sequence No. !");
                    return;
                }
            }
           /* if (reportType.equalsIgnoreCase("NPCI APBS") && (!this.getOrgnBrCode().equalsIgnoreCase("90")))
            {
              this.setMessage("This report can not be open at this branch.");
             return;
            }*/
            resultList = remoteBene.getNpciACHReportData(ymd.format(dmy.parse(fromDate)), ymd.format(dmy.parse(todate)),
                    this.status, this.brnCode, reportType, reportMode, ymd.format(dmy.parse(this.date)), this.seqNo);
   
            String hType = "", headerColumn = "", h1Column = "", h2Column = "";
            String repType = "", fileName = "";

            if (reportType.equalsIgnoreCase("NPCI APBS")) {
                hType = "AAdhar No.";
                repType = "NPCI APBS";
            }
             else if (reportType.equalsIgnoreCase("ACH-CR")) {
                hType = "Account No.";
                repType = "NPCI ACH ";
            } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                hType = "Status";
                repType = "ACH-DR";
                h1Column = "Reason";
            } else if (reportType.equalsIgnoreCase("ECS-CR")) {
                hType = "Account No.";
                repType = "NPCI ECS ";
            } else if (reportType.equalsIgnoreCase("ECS-DR")) {
                hType = "Status";
                repType = "ECS-DR";
                h1Column = "Reason";
            }
            if (!resultList.isEmpty()) {
                String reportName = "ACH Report";
//                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

                List brNameAndAddList;
                if (!this.brnCode.equalsIgnoreCase("ALL")) {
                    brNameAndAddList = common.getBranchNameandAddress(this.brnCode);
                } else {
                    brNameAndAddList = new ArrayList();
                    brNameAndAddList.add(common.getBankName());
                    brNameAndAddList.add("ALL BRANCH");
                }
                Map fillParams = new HashMap();
                if (reportType.equalsIgnoreCase("ACH-CR")) {
                    fillParams.put("reportName", "ACH-CR Status Report");
                } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                    fillParams.put("reportName", "ACH-DR Status Report");
                } else if (reportType.equalsIgnoreCase("ECS-CR")) {
                    fillParams.put("reportName", "ECS-CR Status Report");
                } else if (reportType.equalsIgnoreCase("ECS-DR")) {
                    fillParams.put("reportName", "ECS-DR Status Report");
                } else if (reportType.equalsIgnoreCase("NPCI APBS")){
                    fillParams.put("reportName", "NPCI APBS Status Report");
                }
                fillParams.put("printedBy", getUserName());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("status", getStatus());
                fillParams.put("pHeader", hType);
                if (reportMode.equalsIgnoreCase("SQW")) {
                    fillParams.put("frDt", getDate());
                    fillParams.put("toDt", getDate());
                } else if (reportMode.equalsIgnoreCase("DTW")) {
                    fillParams.put("frDt", getFromDate());
                    fillParams.put("toDt", getTodate());
                }
                if (reportType.equalsIgnoreCase("ACH-DR") || reportType.equalsIgnoreCase("ECS-DR")
                        || reportType.equalsIgnoreCase("NPCI APBS")) {
                    if (reportMode.equalsIgnoreCase("SQW")) {
                        if (status.equalsIgnoreCase("U")) {
                            fillParams.put("pNpaStatus", "Unsuccess Sequence No:" + seqNo);
                        } else if (status.equalsIgnoreCase("S")) {
                            fillParams.put("pNpaStatus", "Success Sequence No:" + seqNo);
                        } else {
                            fillParams.put("pNpaStatus", "ALL Sequence No:" + seqNo);
                        }
                    } else if (reportMode.equalsIgnoreCase("DTW")) {
                        if (status.equalsIgnoreCase("U")) {
                            fillParams.put("pNpaStatus", "Unsuccess");
                        } else if (status.equalsIgnoreCase("S")) {
                            fillParams.put("pNpaStatus", "Success");
                        } else {
                            fillParams.put("pNpaStatus", "ALL");
                        }
                    }
                } else if (reportType.equalsIgnoreCase("ACH-CR") || reportType.equalsIgnoreCase("ECS-CR")) {
                    fillParams.put("pNpaStatus", getStatus());
                }
                if (reportType.equalsIgnoreCase("ECS-CR")) {
                    headerColumn = "-------------------------------------";
                    h1Column = "Sender Bank Code";
                    h2Column = "Sender Name";
                }
                fillParams.put("pRepType", repType);
                fillParams.put("pH1Col", h1Column);
                fillParams.put("pH2Col", h2Column);
                fillParams.put("pHeaderCol", headerColumn);

                if (reportType.equalsIgnoreCase("ACH-CR") || reportType.equalsIgnoreCase("ECS-CR")) {
                    fileName = resultList.get(0).getFileName();
                    fillParams.put("fileName", "File Name : " + fileName);
                }

                if (getStatus().equalsIgnoreCase("All")) {
                    fillParams.put("showColumn", "Y");
                } else {
                    fillParams.put("showColumn", "N");
                }

                if (reportType.equalsIgnoreCase("ACH-DR") || reportType.equalsIgnoreCase("ECS-DR")) {
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
            if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
                this.setMessage("Please select Report Type !");
                return;
            }

            if (this.status == null || this.status.equals("0")) {
                this.setMessage("Please select Status !");
                return;
            }
            if (this.reportMode == null || this.reportMode.equals("0")) {
                this.setMessage("Please select Mode Type !");
                return;
            } else if (this.reportMode.equalsIgnoreCase("DTW")) {
                date = "01/01/1990";
                if (this.fromDate == null || this.fromDate.equals("")) {
                    this.setMessage("Please Fill From date !");
                    return;
                }
                if (this.todate == null || this.todate.equals("")) {
                    this.setMessage("Please Fill To Date !");
                    return;
                }
            } else if (this.reportMode.equalsIgnoreCase("SQW")) {
                fromDate = "01/01/1990";
                todate = "01/01/1990";
                if (this.seqNo == null || this.seqNo.equals("S")) {
                    this.setMessage("Please select Sequence No. !");
                    return;
                }
            }
            if (reportType.equalsIgnoreCase("NPCI APBS") && (!this.getOrgnBrCode().equalsIgnoreCase("90")))
            {
              this.setMessage("This report can not be open at this branch.");
             return;
            }
            resultList = remoteBene.getNpciACHReportData(ymd.format(dmy.parse(fromDate)), ymd.format(dmy.parse(todate)),
                    this.status, this.brnCode, reportType, reportMode, ymd.format(dmy.parse(this.date)), this.seqNo);
   
            String hType = "", headerColumn = "", h1Column = "", h2Column = "";
            String repType = "", fileName = "";

            if (reportType.equalsIgnoreCase("NPCI APBS")) {
                hType = "AAdhar No.";
                repType = "NPCI APBS";
            }
             else if (reportType.equalsIgnoreCase("ACH-CR")) {
                hType = "Account No.";
                repType = "NPCI ACH ";
            } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                hType = "Status";
                repType = "ACH-DR";
                h1Column = "Reason";
            } else if (reportType.equalsIgnoreCase("ECS-CR")) {
                hType = "Account No.";
                repType = "NPCI ECS ";
            } else if (reportType.equalsIgnoreCase("ECS-DR")) {
                hType = "Status";
                repType = "ECS-DR";
                h1Column = "Reason";
            }
            if (!resultList.isEmpty()) {
                String reportName = "ACH Report";
//                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

                List brNameAndAddList;
                if (!this.brnCode.equalsIgnoreCase("ALL")) {
                    brNameAndAddList = common.getBranchNameandAddress(this.brnCode);
                } else {
                    brNameAndAddList = new ArrayList();
                    brNameAndAddList.add(common.getBankName());
                    brNameAndAddList.add("ALL BRANCH");
                }
                Map fillParams = new HashMap();
                if (reportType.equalsIgnoreCase("ACH-CR")) {
                    fillParams.put("reportName", "ACH-CR Status Report");
                } else if (reportType.equalsIgnoreCase("ACH-DR")) {
                    fillParams.put("reportName", "ACH-DR Status Report");
                } else if (reportType.equalsIgnoreCase("ECS-CR")) {
                    fillParams.put("reportName", "ECS-CR Status Report");
                } else if (reportType.equalsIgnoreCase("ECS-DR")) {
                    fillParams.put("reportName", "ECS-DR Status Report");
                } else if (reportType.equalsIgnoreCase("NPCI APBS")){
                    fillParams.put("reportName", "NPCI APBS Status Report");
                }
                fillParams.put("printedBy", getUserName());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("status", getStatus());
                fillParams.put("pHeader", hType);
                if (reportMode.equalsIgnoreCase("SQW")) {
                    fillParams.put("frDt", getDate());
                    fillParams.put("toDt", getDate());
                } else if (reportMode.equalsIgnoreCase("DTW")) {
                    fillParams.put("frDt", getFromDate());
                    fillParams.put("toDt", getTodate());
                }
                if (reportType.equalsIgnoreCase("ACH-DR") || reportType.equalsIgnoreCase("ECS-DR")
                        || reportType.equalsIgnoreCase("NPCI APBS")) {
                    if (reportMode.equalsIgnoreCase("SQW")) {
                        if (status.equalsIgnoreCase("U")) {
                            fillParams.put("pNpaStatus", "Unsuccess Sequence No:" + seqNo);
                        } else if (status.equalsIgnoreCase("S")) {
                            fillParams.put("pNpaStatus", "Success Sequence No:" + seqNo);
                        } else {
                            fillParams.put("pNpaStatus", "ALL Sequence No:" + seqNo);
                        }
                    } else if (reportMode.equalsIgnoreCase("DTW")) {
                        if (status.equalsIgnoreCase("U")) {
                            fillParams.put("pNpaStatus", "Unsuccess");
                        } else if (status.equalsIgnoreCase("S")) {
                            fillParams.put("pNpaStatus", "Success");
                        } else {
                            fillParams.put("pNpaStatus", "ALL");
                        }
                    }
                } else if (reportType.equalsIgnoreCase("ACH-CR") || reportType.equalsIgnoreCase("ECS-CR")) {
                    fillParams.put("pNpaStatus", getStatus());
                }
                if (reportType.equalsIgnoreCase("ECS-CR")) {
                    headerColumn = "-------------------------------------";
                    h1Column = "Sender Bank Code";
                    h2Column = "Sender Name";
                }
                fillParams.put("pRepType", repType);
                fillParams.put("pH1Col", h1Column);
                fillParams.put("pH2Col", h2Column);
                fillParams.put("pHeaderCol", headerColumn);

                if (reportType.equalsIgnoreCase("ACH-CR") || reportType.equalsIgnoreCase("ECS-CR")) {
                    fileName = resultList.get(0).getFileName();
                    fillParams.put("fileName", "File Name : " + fileName);
                }

        
                if (getStatus().equalsIgnoreCase("All")) {
                    fillParams.put("showColumn", "Y");
                } else {
                    fillParams.put("showColumn", "N");
                }

                if (reportType.equalsIgnoreCase("ACH-DR") || reportType.equalsIgnoreCase("ECS-DR")) {
                    new ReportBean().openPdf("NpciEcsDr", "NpciEcsDr", new JRBeanCollectionDataSource(resultList), fillParams);
                }else {
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

    public List<SelectItem> getReportModeList() {
        return reportModeList;
    }

    public void setReportModeList(List<SelectItem> reportModeList) {
        this.reportModeList = reportModeList;
    }

    public String getReportMode() {
        return reportMode;
    }

    public void setReportMode(String reportMode) {
        this.reportMode = reportMode;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public NpciMgmtFacadeRemote getRemoteBene() {
        return remoteBene;
    }

    public void setRemoteBene(NpciMgmtFacadeRemote remoteBene) {
        this.remoteBene = remoteBene;
    }

    public CommonReportMethodsRemote getCommon() {
        return common;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public List<NeftRtgsReportPojo> getResultList() {
        return resultList;
    }

    public void setResultList(List<NeftRtgsReportPojo> resultList) {
        this.resultList = resultList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }

    public String getDateGridDisplay() {
        return dateGridDisplay;
    }

    public void setDateGridDisplay(String dateGridDisplay) {
        this.dateGridDisplay = dateGridDisplay;
    }

    public String getEnableSeqNoGrid() {
        return enableSeqNoGrid;
    }

    public void setEnableSeqNoGrid(String enableSeqNoGrid) {
        this.enableSeqNoGrid = enableSeqNoGrid;
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

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }
}
