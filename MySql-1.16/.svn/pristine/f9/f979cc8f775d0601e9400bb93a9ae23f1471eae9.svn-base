/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.GoiReportPojo;
import com.cbs.web.pojo.investment.SortedByMarking;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class GoiReport extends BaseBean {

    private String message;
    private String repType;
    private String frDt;
    private String asOnDt;
    private String sellarName;
    private List<SelectItem> repTypeList;
    private List<SelectItem> sellarNameList;
    private String status;
    private List<SelectItem> statusList;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private InvestmentMgmtFacadeRemote obj = null;
    private final String jndiName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sql = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat ymdd = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.####");  
    
    
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

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getSellarName() {
        return sellarName;
    }

    public void setSellarName(String sellarName) {
        this.sellarName = sellarName;
    }

    public List<SelectItem> getSellarNameList() {
        return sellarNameList;
    }

    public void setSellarNameList(List<SelectItem> sellarNameList) {
        this.sellarNameList = sellarNameList;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public GoiReport() {
        repTypeList = new ArrayList<SelectItem>();
        sellarNameList = new ArrayList<SelectItem>();
        statusList = new ArrayList<>();
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            getReportTypeList();
            getSellarList();
            statusList.add(new SelectItem("ALL", "ALL"));
            statusList.add(new SelectItem("P", "Purchased"));
            statusList.add(new SelectItem("C", "Closed"));
            resetAction();
            this.setMessage("Please select report type !");
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

    public void getSellarList() {
        try {
            sellarNameList.add(new SelectItem("All"));
            List<String> resultList = remoteObj.getSellarName();
            if (!resultList.isEmpty()) {
                for (String obj : resultList) {
                    sellarNameList.add(new SelectItem(obj));
                }
            } else {
                this.setMessage("Sellar name does not exist !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
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
        if (this.asOnDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.asOnDt) == false) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        if (this.asOnDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.asOnDt) == false) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        List<GoiReportPojo> reportList = new ArrayList<GoiReportPojo>();
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            Date reportFrDt = dmy.parse(this.frDt);
            Date reportAsOnDt = dmy.parse(this.asOnDt);
            if (this.repType.equals("All")) {
                if (this.sellarName.equals("All")) {
                    List<InvestmentMaster> resultList = remoteObj.getAllReportTypeAllSellarName(reportFrDt, reportAsOnDt, "TERM DEPOSIT", status);
                    if (!resultList.isEmpty()) {
                        for (InvestmentMaster entity : resultList) {
                            GoiReportPojo pojo = new GoiReportPojo();

                            pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                            pojo.setSecDtl(entity.getSecdesc());
                            pojo.setSellarName(entity.getSellername());
                            pojo.setSettleDt(dmy.format(entity.getSettledt()));
                            pojo.setMatDt(dmy.format(entity.getMatdt()));
                            double amortamt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt)));
                            pojo.setFaceValue(entity.getFacevalue());
                            pojo.setBookvalue(entity.getBookvalue() - amortamt);
                            pojo.setYtm(Double.valueOf(formatter.format(entity.getYtm())));
                            pojo.setRecInt(remoteObj.getReceGoiAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt))));
                            
                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                } else {
                    List<InvestmentMaster> resultList = remoteObj.getAllReportTypeIndivisualSellarName(this.sellarName, reportFrDt, reportAsOnDt,status);
                    if (!resultList.isEmpty()) {
                        for (InvestmentMaster entity : resultList) {
                            GoiReportPojo pojo = new GoiReportPojo();

                            pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                            pojo.setSecDtl(entity.getSecdesc());
                            pojo.setSellarName(entity.getSellername());
                            pojo.setSettleDt(dmy.format(entity.getSettledt()));
                            pojo.setMatDt(dmy.format(entity.getMatdt()));
                            pojo.setFaceValue(entity.getFacevalue());
                            double amortamt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt)));
                            pojo.setBookvalue(entity.getBookvalue() - amortamt);
                            pojo.setYtm(Double.valueOf(formatter.format(entity.getYtm())));
                            pojo.setRecInt(remoteObj.getReceGoiAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt))));
                            
                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                }
            } else {
                if (this.sellarName.equals("All")) {
                    List<InvestmentMaster> resultList = remoteObj.getIndivisualReportTypeAllSellarNameGoi(this.repType, reportFrDt,reportAsOnDt,status);
                    if (!resultList.isEmpty()) {
                        for (InvestmentMaster entity : resultList) {
                            GoiReportPojo pojo = new GoiReportPojo();

                            pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                            pojo.setSecDtl(entity.getSecdesc());
                            pojo.setSellarName(entity.getSellername());
                            pojo.setSettleDt(dmy.format(entity.getSettledt()));
                            pojo.setMatDt(dmy.format(entity.getMatdt()));
                            pojo.setFaceValue(entity.getFacevalue());
                            double amortamt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt)));
                            pojo.setBookvalue(entity.getBookvalue() - amortamt);
                            pojo.setYtm(Double.valueOf(formatter.format(entity.getYtm())));
                            String marked = remoteObj.getMarkByControlNo(entity.getInvestmentMasterPK().getControlno(), "GOVERNMENT SECURITIES", ymd.format(dmy.parse(asOnDt)));
                            if (marked.equalsIgnoreCase("")) {
                                marked = entity.getMarking();
                            }
                            if (marked.equalsIgnoreCase("HM")) {
                                pojo.setMarking("1");
                            } else if (marked.equalsIgnoreCase("HT")) {
                                pojo.setMarking("2");
                            } else if (marked.equalsIgnoreCase("AF")) {
                                pojo.setMarking("3");
                            } else {
                                pojo.setMarking("4");
                            }
                            // pojo.setMarking(im.getMarking());
                            pojo.setRecInt(remoteObj.getReceGoiAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt))));
                            List reciDate =remoteObj.getRecivableDate(entity.getInvestmentMasterPK().getControlno());
                            Vector vec = (Vector) reciDate.get(0);
                             String intDate1 = vec.get(0).toString().substring(4, 10);
                            String intDate2 = vec.get(1).toString().substring(4, 10);
                          //  String intDate1 = ymd.format(sql.parse(vec.get(0).toString())).substring(4,8);
                          //  String intDate2 = ymd.format(sql.parse(vec.get(1).toString())).substring(4,8);
                            String veryyear = ymd.format(dmy.parse(asOnDt)).substring(0, 4);
                            Date recivableDate1 = ymdd.parse(veryyear + intDate1 );
                            Date recivableDate2 =  ymdd.parse(veryyear + intDate2 );
                            String dayDiff1 =String.valueOf(CbsUtil.dayDiff(dmy.parse(asOnDt), recivableDate1));
                            String DaysDate1 ="";
                            if (dayDiff1.contains("-")) {
                               DaysDate1 = dayDiff1.substring(dayDiff1.indexOf("-") + 1);
                               dayDiff1 = DaysDate1;
                            }
                            String dayDiff2 =String.valueOf(CbsUtil.dayDiff(dmy.parse(asOnDt),recivableDate2));
                            String DaysDate2 ="";
                             if (dayDiff2.contains("-")) {
                               DaysDate2 = dayDiff2.substring(dayDiff1.indexOf("-") + 1);
                               dayDiff2 = DaysDate2;
                            }
                       if(recivableDate1.after(dmy.parse(asOnDt)) || recivableDate2.after(dmy.parse(asOnDt))){
                           if(recivableDate1.after(dmy.parse(asOnDt)) && recivableDate2.after(dmy.parse(asOnDt))){
                            if(Long.parseLong(dayDiff1) < Long.parseLong(dayDiff2)){
                              pojo.setRecievableDt(dmy.format(recivableDate1));
                           }else{
                                pojo.setRecievableDt(dmy.format(recivableDate2));
                           }  
                           }else if(recivableDate1.after(dmy.parse(asOnDt))){
                               pojo.setRecievableDt(dmy.format(recivableDate1));
                           }else if(recivableDate2.after(dmy.parse(asOnDt))){
                               pojo.setRecievableDt(dmy.format(recivableDate2));
                           }
                         }else{
                           Date minDate = recivableDate1.before(recivableDate2) ? recivableDate1 : recivableDate2;
                           Long yearExtend = Long.parseLong(ymd.format(minDate).substring(0, 4))+1;
                           String extendDate = yearExtend.toString()+ ymd.format(minDate).substring(4, 8);
                           pojo.setRecievableDt(dmy.format(ymd.parse(extendDate)));
                        }
                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                } else {
                    List<InvestmentMaster> resultList = remoteObj.getIndivisualReportTypeReportTypeIndivisualSellarName(this.repType, this.sellarName, reportFrDt, reportAsOnDt,status);
                    if (!resultList.isEmpty()) {
                        for (InvestmentMaster entity : resultList) {
                            GoiReportPojo pojo = new GoiReportPojo();

                            pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                            pojo.setSecDtl(entity.getSecdesc());
                            pojo.setSellarName(entity.getSellername());
                            pojo.setSettleDt(dmy.format(entity.getSettledt()));
                            pojo.setMatDt(dmy.format(entity.getMatdt()));
                            pojo.setFaceValue(entity.getFacevalue());
                            double amortamt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt)));
                            pojo.setBookvalue(entity.getBookvalue() - amortamt);
                            pojo.setYtm(Double.valueOf(formatter.format(entity.getYtm())));
                            String marked = remoteObj.getMarkByControlNo(entity.getInvestmentMasterPK().getControlno(), "GOVERNMENT SECURITIES", ymd.format(dmy.parse(asOnDt)));
                            if (marked.equalsIgnoreCase("")) {
                                marked = entity.getMarking();
                            }
                            if (marked.equalsIgnoreCase("HM")) {
                                pojo.setMarking("1");
                            } else if (marked.equalsIgnoreCase("HT")) {
                                pojo.setMarking("2");
                            } else if (marked.equalsIgnoreCase("AF")) {
                                pojo.setMarking("3");
                            } else {
                                pojo.setMarking("4");
                            }
                            // pojo.setMarking(im.getMarking());
                            pojo.setRecInt(remoteObj.getReceGoiAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt))));
                            List reciDate =remoteObj.getRecivableDate(entity.getInvestmentMasterPK().getControlno());
                            Vector vec = (Vector) reciDate.get(0);
                             String intDate1 = vec.get(0).toString().substring(4, 10);
                            String intDate2 = vec.get(1).toString().substring(4, 10);
                          //  String intDate1 = ymd.format(sql.parse(vec.get(0).toString())).substring(4,8);
                          //  String intDate2 = ymd.format(sql.parse(vec.get(1).toString())).substring(4,8);
                            String veryyear = ymd.format(dmy.parse(asOnDt)).substring(0, 4);
                            Date recivableDate1 = ymdd.parse(veryyear + intDate1 );
                            Date recivableDate2 =  ymdd.parse(veryyear + intDate2 );
                            String dayDiff1 =String.valueOf(CbsUtil.dayDiff(dmy.parse(asOnDt), recivableDate1));
                            String DaysDate1 ="";
                            if (dayDiff1.contains("-")) {
                               DaysDate1 = dayDiff1.substring(dayDiff1.indexOf("-") + 1);
                               dayDiff1 = DaysDate1;
                            }
                            String dayDiff2 =String.valueOf(CbsUtil.dayDiff(dmy.parse(asOnDt),recivableDate2));
                            String DaysDate2 ="";
                             if (dayDiff2.contains("-")) {
                               DaysDate2 = dayDiff2.substring(dayDiff1.indexOf("-") + 1);
                               dayDiff2 = DaysDate2;
                            }
                       if(recivableDate1.after(dmy.parse(asOnDt)) || recivableDate2.after(dmy.parse(asOnDt))){
                           if(recivableDate1.after(dmy.parse(asOnDt)) && recivableDate2.after(dmy.parse(asOnDt))){
                            if(Long.parseLong(dayDiff1) < Long.parseLong(dayDiff2)){
                              pojo.setRecievableDt(dmy.format(recivableDate1));
                           }else{
                                pojo.setRecievableDt(dmy.format(recivableDate2));
                           }  
                           }else if(recivableDate1.after(dmy.parse(asOnDt))){
                               pojo.setRecievableDt(dmy.format(recivableDate1));
                           }else if(recivableDate2.after(dmy.parse(asOnDt))){
                               pojo.setRecievableDt(dmy.format(recivableDate2));
                           }
                         }else{
                           Date minDate = recivableDate1.before(recivableDate2) ? recivableDate1 : recivableDate2;
                           Long yearExtend = Long.parseLong(ymd.format(minDate).substring(0, 4))+1;
                           String extendDate = yearExtend.toString()+ ymd.format(minDate).substring(4, 8);
                           pojo.setRecievableDt(dmy.format(ymd.parse(extendDate)));
                        }
                            reportList.add(pojo);
                        }
                    } else {
                        this.setMessage("Data does not exist !");
                        return;
                    }
                }
            }
            Map fillParams = new HashMap();
            String repName = "Investment GOI Report";
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", brList.get(0));
            fillParams.put("pBranchAddress", brList.get(1));
            fillParams.put("pReportName", repName);
            fillParams.put("pRepType", repType);
            fillParams.put("pReportDate", dmy.format(dmy.parse(this.getAsOnDt())));

            if (repType.equalsIgnoreCase("GOVERNMENT SECURITIES")) {
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByMarking());
                Collections.sort(reportList, chObj);
            }

            new ReportBean().jasperReport("goiReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetAction() {
        this.setMessage("");
        this.setRepType("All");
        this.setSellarName("All");
        this.setStatus("ALL");
        this.setFrDt(dmy.format(new Date()));
        this.setAsOnDt(dmy.format(new Date()));
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