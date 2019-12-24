package com.cbs.web.controller.report.other;

import com.cbs.dto.report.PayOrderPojo;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class PayOrder extends BaseBean {

    private String message;
    private String calFromDate;
    private String caltoDate;
    OtherReportFacadeRemote beanRemote;
    private String mode;
    private List<SelectItem> modeList;
    private String billType;
    private List<SelectItem> billTypeList;
    private boolean caltoDateDisable;
    private String display = "";
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private TdReceiptManagementFacadeRemote beanFacade;

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
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

    public boolean isCaltoDateDisable() {
        return caltoDateDisable;
    }

    public void setCaltoDateDisable(boolean caltoDateDisable) {
        this.caltoDateDisable = caltoDateDisable;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public PayOrder() {
        try {
            this.setCalFromDate(getTodayDate());
            this.setCaltoDate(getTodayDate());
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            //Todo get Bill tpye from billTypemaster
            List dataList = beanRemote.billTypeLoad();
            
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("0", "--Select--"));
            for(int i=0; i<dataList.size(); i++){
                Vector vector = (Vector) dataList.get(i);
                billTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(1).toString()));
            }
            //billTypeList.add(new SelectItem("DD", "DEMAND DRAFT"));
            //billTypeList.add(new SelectItem("PO", "PAY ORDER"));
            setBillType("0");
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "--Select--"));
            modeList.add(new SelectItem("ISSUED", "ISSUED"));
            modeList.add(new SelectItem("PAID", "PAID"));
            modeList.add(new SelectItem("ISSUEDDATE", "OUTSTANDING BASED ON ISSUE DATE"));
            modeList.add(new SelectItem("SEQNO", "OUTSTANDING BASED ON SEQ NO"));
            modeList.add(new SelectItem("INSTNO", "OUTSTANDING BASED ON INST NO"));
            modeList.add(new SelectItem("STATUS", "STATUS"));

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (billType.equalsIgnoreCase("0")) {
                setMessage("Please select Bill Type.");
                return null;
            }
            if (mode.equalsIgnoreCase("0")) {
                setMessage("Please select mode.");
                return null;
            }
            if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                message = "Please fill from date";
                return null;
            }
            if (!Validator.validateDate(calFromDate)) {
                message = "Please select proper From Date";
                return null;
            }
            if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                message = "Please fill from date";
                return null;
            }
            if (!Validator.validateDate(caltoDate)) {
                message = "Please select proper To Date";
                return null;
            }
            String frmDate = calFromDate.substring(6) + calFromDate.substring(3, 5) + calFromDate.substring(0, 2);
            String toDate = caltoDate.substring(6) + caltoDate.substring(3, 5) + caltoDate.substring(0, 2);
            if (ymdFormat.parse(frmDate).after(ymdFormat.parse(toDate))) {
                setMessage("From date should be less than to date");
                return null;
            }
            String modeSelect = "", dateValue = "", fromdttmp = "", todttmp = "";
            if (mode.equalsIgnoreCase("ISSUED")) {
                modeSelect = mode;
                dateValue = "";
                fromdttmp = frmDate;
                todttmp = toDate;
            } else if (mode.equalsIgnoreCase("PAID")) {
                modeSelect = mode;
                dateValue = "";
                fromdttmp = frmDate;
                todttmp = toDate;
            } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
                modeSelect = mode;
                dateValue = frmDate;
                fromdttmp = "";
                todttmp = "";
            } else if (mode.equalsIgnoreCase("SEQNO")) {
                modeSelect = mode;
                dateValue = frmDate;
                fromdttmp = "";
                todttmp = "";
            } else if (mode.equalsIgnoreCase("INSTNO")) {
                modeSelect = mode;
                dateValue = frmDate;
                fromdttmp = "";
                todttmp = "";
            } else if (mode.equalsIgnoreCase("STATUS")) {
                modeSelect = mode;
                dateValue = "";
                fromdttmp = frmDate;
                todttmp = toDate;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String repName = "Remittance Report Pay Order";
            List<PayOrderPojo> payOrder = beanRemote.getPoDdData(billType, modeSelect, dateValue, fromdttmp, todttmp, branchCode);
            if (!payOrder.isEmpty()) {
                Map fillParams = new HashMap();
                if (mode.equalsIgnoreCase("INSTNO")) {
                    fillParams.put("pPrintedDate", calFromDate + "  TO  " + caltoDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Pay_order_Issue_dt", "text/html", new JRBeanCollectionDataSource(payOrder), fillParams, repName);
                } else if (mode.equalsIgnoreCase("STATUS")) {
                    fillParams.put("pPrintedDate", sdf.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Pay_Order_Status", "text/html", new JRBeanCollectionDataSource(payOrder), fillParams, repName);
                } else if (mode.equalsIgnoreCase("SEQNO")) {
                    fillParams.put("pPrintedDate", calFromDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Pay_order_Issue_dt", "text/html", new JRBeanCollectionDataSource(payOrder), fillParams, repName);
                } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
                    fillParams.put("pPrintedDate", calFromDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Pay_order_Issue_dt", "text/html", new JRBeanCollectionDataSource(payOrder), fillParams, repName);
                } else if (mode.equalsIgnoreCase("ISSUED")) {
                    fillParams.put("pPrintedDate", calFromDate + " to " + caltoDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Pay_Order_Issued", "text/html", new JRBeanCollectionDataSource(payOrder), fillParams, repName);

                } else if (mode.equalsIgnoreCase("PAID")) {
                    fillParams.put("pPrintedDate", calFromDate + " to " + caltoDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Pay_Order_Issued", "text/html", new JRBeanCollectionDataSource(payOrder), fillParams, repName);
                }
                return "report";

            } else {
                setMessage("No data found");
                return null;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (billType.equalsIgnoreCase("0")) {
                setMessage("Please select Bill Type.");
                return null;
            }
            if (mode.equalsIgnoreCase("0")) {
                setMessage("Please select mode.");
                return null;
            }
            if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                message = "Please fill from date";
                return null;
            }
            if (!Validator.validateDate(calFromDate)) {
                message = "Please select proper From Date";
                return null;
            }
            if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                message = "Please fill from date";
                return null;
            }
            if (!Validator.validateDate(caltoDate)) {
                message = "Please select proper To Date";
                return null;
            }
            String frmDate = calFromDate.substring(6) + calFromDate.substring(3, 5) + calFromDate.substring(0, 2);
            String toDate = caltoDate.substring(6) + caltoDate.substring(3, 5) + caltoDate.substring(0, 2);
            if (ymdFormat.parse(frmDate).after(ymdFormat.parse(toDate))) {
                setMessage("From date should be less than to date");
                return null;
            }
            String modeSelect = "", dateValue = "", fromdttmp = "", todttmp = "";
            if (mode.equalsIgnoreCase("ISSUED")) {
                modeSelect = mode;
                dateValue = "";
                fromdttmp = frmDate;
                todttmp = toDate;
            } else if (mode.equalsIgnoreCase("PAID")) {
                modeSelect = mode;
                dateValue = "";
                fromdttmp = frmDate;
                todttmp = toDate;
            } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
                modeSelect = mode;
                dateValue = frmDate;
                fromdttmp = "";
                todttmp = "";
            } else if (mode.equalsIgnoreCase("SEQNO")) {
                modeSelect = mode;
                dateValue = frmDate;
                fromdttmp = "";
                todttmp = "";
            } else if (mode.equalsIgnoreCase("INSTNO")) {
                modeSelect = mode;
                dateValue = frmDate;
                fromdttmp = "";
                todttmp = "";
            } else if (mode.equalsIgnoreCase("STATUS")) {
                modeSelect = mode;
                dateValue = "";
                fromdttmp = frmDate;
                todttmp = toDate;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String repName = "Remittance Report Pay Order";
            List<PayOrderPojo> payOrder = beanRemote.getPoDdData(billType, modeSelect, dateValue, fromdttmp, todttmp, branchCode);
            if (!payOrder.isEmpty()) {
                Map fillParams = new HashMap();
                if (mode.equalsIgnoreCase("INSTNO")) {
                    fillParams.put("pPrintedDate", calFromDate + "  TO  " + caltoDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Remittance Report Pay Order_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Pay_order_Issue_dt", new JRBeanCollectionDataSource(payOrder), fillParams);
                } else if (mode.equalsIgnoreCase("STATUS")) {
                    fillParams.put("pPrintedDate", sdf.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Remittance Report Pay Order_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Pay_Order_Status", new JRBeanCollectionDataSource(payOrder), fillParams);
                } else if (mode.equalsIgnoreCase("SEQNO")) {
                    fillParams.put("pPrintedDate", calFromDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Remittance Report Pay Order_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Pay_order_Issue_dt", new JRBeanCollectionDataSource(payOrder), fillParams);
                } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
                    fillParams.put("pPrintedDate", calFromDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Remittance Report Pay Order_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Pay_order_Issue_dt", new JRBeanCollectionDataSource(payOrder), fillParams);
                } else if (mode.equalsIgnoreCase("ISSUED")) {
                    fillParams.put("pPrintedDate", calFromDate + " to " + caltoDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Remittance Report Pay Order_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Pay_Order_Issued", new JRBeanCollectionDataSource(payOrder), fillParams);

                } else if (mode.equalsIgnoreCase("PAID")) {
                    fillParams.put("pPrintedDate", calFromDate + " to " + caltoDate);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Remittance Report Pay Order_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "Pay_Order_Issued", new JRBeanCollectionDataSource(payOrder), fillParams);
                }
                // ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
                return "report";
            } else {
                setMessage("No data found");
                return null;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void ddModeprocessValueChange() {
        if (mode.equalsIgnoreCase("ISSUED")) {
            caltoDateDisable = false;
            display = "";
        } else if (mode.equalsIgnoreCase("PAID")) {
            caltoDateDisable = false;
            display = "";
        } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
            caltoDateDisable = true;
            display = "none";
        } else if (mode.equalsIgnoreCase("SEQNO")) {
            caltoDateDisable = true;
            display = "none";
        } else if (mode.equalsIgnoreCase("INSTNO")) {
            caltoDateDisable = true;
            display = "none ";
        } else if (mode.equalsIgnoreCase("STATUS")) {
            caltoDateDisable = false;
            display = "";
        }
    }

    public void refreshAction() {
        this.setMessage("");
        this.setCalFromDate(getTodayDate());
        this.setCaltoDate(getTodayDate());
        this.setMode("--Select--");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
