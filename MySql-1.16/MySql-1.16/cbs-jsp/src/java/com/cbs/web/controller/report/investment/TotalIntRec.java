/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.TotalIntRecPojo;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class TotalIntRec extends BaseBean {

    private String message;
    List<TotalIntRecPojo> reportList;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private String selectStatus;
    private List<SelectItem> selectStatusList;
    private String bnkType;
    private List<SelectItem> bnkTypeList;

    public List<TotalIntRecPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<TotalIntRecPojo> reportList) {
        this.reportList = reportList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(String selectStatus) {
        this.selectStatus = selectStatus;
    }

    public List<SelectItem> getSelectStatusList() {
        return selectStatusList;
    }

    public void setSelectStatusList(List<SelectItem> selectStatusList) {
        this.selectStatusList = selectStatusList;
    }

    public String getBnkType() {
        return bnkType;
    }

    public void setBnkType(String bnkType) {
        this.bnkType = bnkType;
    }

    public List<SelectItem> getBnkTypeList() {
        return bnkTypeList;
    }

    public void setBnkTypeList(List<SelectItem> bnkTypeList) {
        this.bnkTypeList = bnkTypeList;
    }
    
    public TotalIntRec() {
        this.setMessage("Please click on Print Report button !");
        reportList = new ArrayList<TotalIntRecPojo>();
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            setStatusList();
            setBankList();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }
    
    public void setBankList() {
        try {
            bnkTypeList = new ArrayList<SelectItem>();
            List<String> resultList = new ArrayList<String>();
            resultList = remoteObj.getBankName();
            bnkTypeList.add(new SelectItem("ALL","ALL"));
            for (String obj : resultList) {
                bnkTypeList.add(new SelectItem(obj));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }        
    }
    
    public void setStatusList() {
        selectStatusList = new ArrayList<SelectItem>();
        selectStatusList.add(new SelectItem("ACTIVE", "ACTIVE"));
        selectStatusList.add(new SelectItem("CLOSED", "CLOSED"));
        selectStatusList.add(new SelectItem("ALL", "ALL"));
    }

    public void printAction() {
        this.setMessage("");
        Double totalAmt = 0.0, totalRecAmt = 0.0;
        reportList = new ArrayList<TotalIntRecPojo>();
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            
            List<Object[]> dataList;
            if(bnkType.equalsIgnoreCase("ALL")){
                if(selectStatus.equalsIgnoreCase("ALL")){
                    dataList = remoteObj.getDatesAndDetailsForInterestRecreport();
                }else{
                    dataList = remoteObj.getDatesAndDetailsForInterestRecreportAll(selectStatus.trim());
                }
            }else{
                if(selectStatus.equalsIgnoreCase("ALL")){
                    dataList = remoteObj.getDatesAndDetailsForInterestRecreportBankAll(bnkType);
                }else{
                    dataList = remoteObj.getDatesAndDetailsForInterestRecreportBankWise(bnkType, selectStatus.trim());
                }
            }
            
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    TotalIntRecPojo pojo = new TotalIntRecPojo();
                        
                    Object[] entityArray = dataList.get(i);
                    InvestmentFdrDates dateEntity = (InvestmentFdrDates) entityArray[0];
                    InvestmentFdrDetails detailsEntity = (InvestmentFdrDetails) entityArray[1];
                        
                    /**
                      * Setting Report List
                    */
                    pojo.setSno(i);
                    pojo.setCtrlNo(dateEntity.getCtrlNo());
                    pojo.setFdrNo(detailsEntity.getFdrNo());
                    pojo.setFaceValue(formatter.format(detailsEntity.getFaceValue()));
                    pojo.setBookValue(formatter.format(detailsEntity.getBookValue()));
                    pojo.setLastRecAmt(formatter.format(dateEntity.getAmtIntTrec()));
                    pojo.setTotalRecAmt(formatter.format(dateEntity.getTotAmtIntRec()));
                    pojo.setLastIntpaidDt(dmy.format(dateEntity.getLastIntPaidDt()));
                    pojo.setSellarName(detailsEntity.getSellerName());
                        
                    reportList.add(pojo);
                    totalRecAmt = totalRecAmt + dateEntity.getTotAmtIntRec();
                    totalAmt = totalAmt + dateEntity.getAmtIntTrec();
                }
                String repName = "Interest Rec. Report";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", brList.get(0));
                fillParams.put("pBranchAddress", brList.get(1));
                fillParams.put("pReportName", repName);
                fillParams.put("totalRecAmt", totalRecAmt.toString());
                fillParams.put("totalAmt", totalAmt.toString());
                new ReportBean().jasperReport("intRecReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                this.setMessage("Please click on Print Report button !");
            } else {
                this.setMessage("Data does not exist !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}