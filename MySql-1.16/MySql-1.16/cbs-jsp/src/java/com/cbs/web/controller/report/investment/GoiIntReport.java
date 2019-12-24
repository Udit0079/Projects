/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.pojo.GoiIntReportPojo;
import com.cbs.utils.Validator;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class GoiIntReport extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;
    private String seller;
    private List<SelectItem> sellerList;
    private String month;
    private List<SelectItem> monthList;
    private String tillDt;
    private String frDt;
    private String toDt;
    private String status;
    private List<SelectItem> statusList;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private InvestmentMgmtFacadeRemote obj = null;
    private final String jndiName = "InvestmentMgmtFacade";
    private TdReceiptManagementFacadeRemote tdobj = null;
    private final String tdJndiName = "TdReceiptManagementFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<SelectItem> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<SelectItem> sellerList) {
        this.sellerList = sellerList;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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
    

    public GoiIntReport() {
        this.setMessage("");
        this.setFrDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
        repTypeList = new ArrayList<SelectItem>();
        monthList = new ArrayList<SelectItem>();
        statusList = new ArrayList<>();
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            tdobj = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(tdJndiName);
            statusList.add(new SelectItem("ALL", "ALL"));
            statusList.add(new SelectItem("P", "Purchased"));
            statusList.add(new SelectItem("C", "Closed"));
            getReportTypeList();
            loadSName();
            fillMonthList();
            resetAction();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getReportTypeList() {
        try {
            repTypeList.add(new SelectItem("--Select--"));
            repTypeList.add(new SelectItem("All"));
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = obj.getGlRange("G");
            for (int i = 0; i < resultList.size(); i++) {
                GlDescRange entity = resultList.get(i);
                repTypeList.add(new SelectItem(entity.getGlname()));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void loadSName() {
        sellerList = new ArrayList<SelectItem>();
        sellerList.add(new SelectItem("All"));
        try {
            List<String> resultList;
            if (this.repType.equalsIgnoreCase("All")) {
                resultList = remoteObj.getSellarName();
            } else {
                resultList = remoteObj.getSName(this.repType);
            }

            if (!resultList.isEmpty()) {
                for (String obj : resultList) {
                    sellerList.add(new SelectItem(obj));
                }
            } else {
                this.setMessage("There is no any seller name !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillMonthList() {
        monthList.add(new SelectItem("03", "March"));
        monthList.add(new SelectItem("06", "June"));
        monthList.add(new SelectItem("09", "Sep"));
        monthList.add(new SelectItem("12", "Dec"));
    }

    public void onBlurRepType() {
        this.setMessage("");
        if (this.repType.equals("--Select--")) {
            this.setMessage("Please select report type !");
            return;
        }
        loadSName();
    }

    public void onBlurTillDt() {
        this.setMessage("");
        validateTillDate();

    }

    public void validateTillDate() {
        if (this.tillDt == null || this.tillDt.equals("")) {
            this.setMessage("Please fill till date !");
            return;
        }

        if (this.tillDt.length() < 4) {
            this.setMessage("Please fill proper till year !");
            return;
        }

        Integer tillDate = Integer.parseInt(this.getTillDt());

        if (tillDate < 2012 || tillDate > 2099) {
            this.setMessage("Please fill proper till year !");
            return;
        }
    }

    public Date getMaxDate() {
        Date maxDt = null;
        try {
            maxDt = Util.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.tillDt));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return maxDt;
    }

    public void onBlurFrDt() {
        this.setMessage("");
        if (this.frDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.frDt) == false) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
    }

    public void onBlurAsOnDt() {
        this.setMessage("");
        if (this.toDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        if (this.repType.equals("--Select--")) {
            this.setMessage("Please select report type !");
            return;
        }
        validateTillDate();
        /**
         * *Calculate the max days ***
         */
        List<GoiIntReportPojo> reportList = new ArrayList<GoiIntReportPojo>();
        try {
            Date reportFrDt = dmy.parse(this.frDt);
            Date reportToDt = dmy.parse(this.toDt);
            //Date maxDt = getMaxDate();
            Date maxDt = reportToDt;
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!this.seller.equals("All")) {
                if (this.repType.equalsIgnoreCase("All")) {
                    List<Object[]> resultList = remoteObj.getInvestMastSecAll(this.seller,reportFrDt, reportToDt,status);
                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            String intopt = "S";
                            Object[] obj = resultList.get(i);
                            InvestmentMaster im = (InvestmentMaster) obj[0];
                            InvestmentGoidates ism = (InvestmentGoidates) obj[1];
                            GoiIntReportPojo pojo = new GoiIntReportPojo();
                            //String fddt1 = getFdDt1(ism.getIntPayableFirstDate(), ism.getIntPayableSecondDate());
                            String fddt1 = getFdDt1(ism.getPurchasedt(), ism.getLastintpaiddt(), ism.getLastIntPaidDtAccr());
                            //long period = CbsUtil.dayDiff(dmy.parse(fddt1), maxDt)+1;
                            long period;
//                            if(period > 180){
//                                period = 180;
//                            }else{
                            int mon = CbsUtil.monthDiff(dmy.parse(fddt1), maxDt);
                            int d1 = Integer.parseInt(fddt1.substring(0, 2));
                            int d2 = Integer.parseInt(dmy.format(maxDt).substring(0, 2));
                            if (d1 > d2) {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), (mon - 1));
                                period = ((mon - 1) * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            } else if (d1 == d2) {
                                period = (mon * 30);
                            } else {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), mon);
                                period = (mon * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            }
//                            }
                            String dayPr = period + "Days";
                            String val = tdobj.orgFDInterestGoi(intopt, (float) im.getRoi(), ymd.format(dmy.parse(fddt1)), ymd.format(maxDt), im.getFacevalue(), dayPr, this.getOrgnBrCode());

                            pojo.setCtrlNo(im.getInvestmentMasterPK().getControlno());
                            pojo.setParticulars(im.getSecdesc());
                            pojo.setBookVal(new BigDecimal(im.getBookvalue()).toString());
                            pojo.setSettleDt(dmy.format(im.getSettledt()));
                            pojo.setIpDate(fddt1);
                            pojo.setFaceVal(new BigDecimal(im.getFacevalue()).toString());
                            pojo.setRoi(im.getRoi());
                            pojo.setPeriod(period);
                            pojo.setIntAmt(val);
                            pojo.setYtm(im.getYtm());

                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                } else {
                    List<Object[]> resultList = remoteObj.getInvestmentMasterSecurityMaster(this.repType, this.seller,reportFrDt, maxDt,status);
                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            String intopt = "S";
                            Object[] obj = resultList.get(i);
                            InvestmentMaster im = (InvestmentMaster) obj[0];
                            InvestmentGoidates ism = (InvestmentGoidates) obj[1];
                            GoiIntReportPojo pojo = new GoiIntReportPojo();
                            //String fddt1 = getFdDt1(ism.getIntPayableFirstDate(), ism.getIntPayableSecondDate());
                            String fddt1 = getFdDt1(ism.getPurchasedt(), ism.getLastintpaiddt(), ism.getLastIntPaidDtAccr());
                            //long period = CbsUtil.dayDiff(dmy.parse(fddt1), maxDt)+1;
                            long period;
//                            if(period > 180){
//                                period = 180;
//                            }else{
                            int mon = CbsUtil.monthDiff(dmy.parse(fddt1), maxDt);
                            int d1 = Integer.parseInt(fddt1.substring(0, 2));
                            int d2 = Integer.parseInt(dmy.format(maxDt).substring(0, 2));
                            if (d1 > d2) {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), (mon - 1));
                                period = ((mon - 1) * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            } else if (d1 == d2) {
                                period = (mon * 30);
                            } else {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), mon);
                                period = (mon * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            }
//                            }
                            String dayPr = period + "Days";
                            String val = tdobj.orgFDInterestGoi(intopt, (float) im.getRoi(), ymd.format(dmy.parse(fddt1)), ymd.format(maxDt), im.getFacevalue(), dayPr, this.getOrgnBrCode());

                            pojo.setCtrlNo(im.getInvestmentMasterPK().getControlno());
                            pojo.setParticulars(im.getSecdesc());
                            pojo.setBookVal(new BigDecimal(im.getBookvalue()).toString());
                            pojo.setSettleDt(dmy.format(im.getSettledt()));
                            pojo.setIpDate(fddt1);
                            pojo.setFaceVal(new BigDecimal(im.getFacevalue()).toString());
                            pojo.setRoi(im.getRoi());
                            pojo.setPeriod(period);
                            pojo.setIntAmt(val);
                            pojo.setYtm(im.getYtm());

                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                }
            } else {
                if (this.repType.equalsIgnoreCase("All")) {
                    List<Object[]> resultList = remoteObj.getAllInvestMastSec(reportFrDt,maxDt,status);
                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            String intopt = "S";
                            Object[] obj = resultList.get(i);
                            InvestmentMaster im = (InvestmentMaster) obj[0];
                            InvestmentGoidates ism = (InvestmentGoidates) obj[1];
                            GoiIntReportPojo pojo = new GoiIntReportPojo();
                            //String fddt1 = getFdDt1(ism.getIntPayableFirstDate(), ism.getIntPayableSecondDate());
                            String fddt1 = getFdDt1(ism.getPurchasedt(), ism.getLastintpaiddt(), ism.getLastIntPaidDtAccr());
                            //long period = CbsUtil.dayDiff(dmy.parse(fddt1), maxDt)+1;
                            long period;
//                            if(period > 180){
//                                period = 180;
//                            }else{
                            int mon = CbsUtil.monthDiff(dmy.parse(fddt1), maxDt);
                            int d1 = Integer.parseInt(fddt1.substring(0, 2));
                            int d2 = Integer.parseInt(dmy.format(maxDt).substring(0, 2));
                            if (d1 > d2) {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), (mon - 1));
                                period = ((mon - 1) * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            } else if (d1 == d2) {
                                period = (mon * 30);
                            } else {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), mon);
                                period = (mon * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            }
//                            }
                            String dayPr = period + "Days";
                            String val = tdobj.orgFDInterestGoi(intopt, (float) im.getRoi(), ymd.format(dmy.parse(fddt1)), ymd.format(maxDt), im.getFacevalue(), dayPr, this.getOrgnBrCode());

                            pojo.setCtrlNo(im.getInvestmentMasterPK().getControlno());
                            pojo.setParticulars(im.getSecdesc());
                            pojo.setBookVal(new BigDecimal(im.getBookvalue()).toString());
                            pojo.setSettleDt(dmy.format(im.getSettledt()));
                            pojo.setIpDate(fddt1);
                            pojo.setFaceVal(new BigDecimal(im.getFacevalue()).toString());
                            pojo.setRoi(im.getRoi());
                            pojo.setPeriod(period);
                            pojo.setIntAmt(val);
                            pojo.setYtm(im.getYtm());

                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                } else {
                    List<Object[]> resultList = remoteObj.getAllInvestmentMasterSecurityMasterGoiInt(this.repType,reportFrDt, maxDt,status);
                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            String intopt = "S";
                            Object[] obj = resultList.get(i);
                            InvestmentMaster im = (InvestmentMaster) obj[0];
                            InvestmentGoidates ism = (InvestmentGoidates) obj[1];
                            GoiIntReportPojo pojo = new GoiIntReportPojo();
                            //String fddt1 = getFdDt1(ism.getIntPayableFirstDate(), ism.getIntPayableSecondDate());
                            String fddt1 = getFdDt1(ism.getPurchasedt(), ism.getLastintpaiddt(), ism.getLastIntPaidDtAccr());
//                            long period = CbsUtil.dayDiff(dmy.parse(fddt1), maxDt)+1;
                            long period;
//                            if(period > 180){
//                                period = 180;
//                            }else{
                            int mon = CbsUtil.monthDiff(dmy.parse(fddt1), maxDt);
                            int d1 = Integer.parseInt(fddt1.substring(0, 2));
                            int d2 = Integer.parseInt(dmy.format(maxDt).substring(0, 2));
                            if (d1 > d2) {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), (mon - 1));
                                period = ((mon - 1) * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            } else if (d1 == d2) {
                                period = (mon * 30);
                            } else {
                                String newDt = CbsUtil.monthAdd(ymd.format(dmy.parse(fddt1)), mon);
                                period = (mon * 30) + (CbsUtil.dayDiff(ymd.parse(newDt), maxDt));
                            }
//                            }
                            String dayPr = period + "Days";
                            String val = tdobj.orgFDInterestGoi(intopt, (float) im.getRoi(), ymd.format(dmy.parse(fddt1)), ymd.format(maxDt), im.getFacevalue(), dayPr, this.getOrgnBrCode());

                            pojo.setCtrlNo(im.getInvestmentMasterPK().getControlno());
                            pojo.setParticulars(im.getSecdesc());
                            pojo.setBookVal(new BigDecimal(im.getBookvalue()).toString());
                            pojo.setSettleDt(dmy.format(im.getSettledt()));
                            pojo.setIpDate(fddt1);
                            pojo.setFaceVal(new BigDecimal(im.getFacevalue()).toString());
                            pojo.setRoi(im.getRoi());
                            pojo.setPeriod(period);
                            pojo.setIntAmt(val);
                            pojo.setYtm(im.getYtm());

                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                }
            }

            Map fillParams = new HashMap();
            String repName = "GOI Interest Report";
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", brList.get(0));
            fillParams.put("pBranchAddress", brList.get(1));
            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", dmy.format(maxDt));

            new ReportBean().jasperReport("goiIntReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

//    public String getFdDt1(String dt1, String dt2) {
//        String fddt1 = "";
//        int fdt1 = Integer.parseInt(dt1.substring(3));
//        int sdt1 = Integer.parseInt(dt2.substring(3));
//        if (this.month.equals("09")) {
//            if (fdt1 >= 4 && fdt1 <= 9) {
//                fddt1 = dt1 + "/" + dmy.format(getMaxDate()).substring(6);
//            } else if (sdt1 >= 4 && sdt1 <= 9) {
//                fddt1 = dt2 + "/" + dmy.format(getMaxDate()).substring(6);
//            }
//        } else if (this.month.equals("03")) {
//            String tillDate = CbsUtil.yearAdd(ymd.format(getMaxDate()), -1);
//            if (fdt1 >= 1 && fdt1 <= 3) {
//                fddt1 = dt1 + "/" + dmy.format(getMaxDate()).substring(6);
//            } else if (sdt1 >= 1 && sdt1 <= 3) {
//                fddt1 = dt2 + "/" + dmy.format(getMaxDate()).substring(6);
//            } else if (fdt1 >= 10 && fdt1 <= 12) {
//                fddt1 = dt1 + "/" + tillDate.substring(0, 4);
//            } else if (sdt1 >= 10 && sdt1 <= 12) {
//                fddt1 = dt2 + "/" + tillDate.substring(0, 4);
//            }
//        } else if (this.month.equals("06")) {
//            if (fdt1 >= 1 && fdt1 <= 6) {
//                fddt1 = dt1 + "/" + dmy.format(getMaxDate()).substring(6);
//            } else if (sdt1 >= 1 && sdt1 <= 6) {
//                fddt1 = dt2 + "/" + dmy.format(getMaxDate()).substring(6);
//            }
//        } else if (this.month.equals("12")) {
//            if (fdt1 >= 7 && fdt1 <= 12) {
//                fddt1 = dt1 + "/" + dmy.format(getMaxDate()).substring(6);
//            } else if (sdt1 >= 7 && sdt1 <= 12) {
//                fddt1 = dt2 + "/" + dmy.format(getMaxDate()).substring(6);
//            }
//        }
//        return fddt1;
//    }
    public String getFdDt1(java.util.Date dt1, java.util.Date dt2, java.util.Date dt3) {
        String fddt1 = "";
        if ((dt1.compareTo(dt2) == 0) && (dt2.compareTo(dt3) == 0)) {
            fddt1 = dmy.format(dt1);
        } else {
            if (dt2.compareTo(dt3) < 0) {
                fddt1 = CbsUtil.dateAdd(dmy.format(dt3), 1);
            } else {
                fddt1 = CbsUtil.dateAdd(dmy.format(dt2), 1);
            }
        }
        return fddt1;
    }

    public void resetAction() {
        this.setMessage("");
        this.setRepType("--Select--");
        this.setSeller("All");
        this.setMonth("03");
        this.setTillDt(dmy.format(dt).substring(6));
    }

    public String exitAction() {
        try {
            resetAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}