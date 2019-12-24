/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.master.GlDescRange;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.TdDueDatePojo;
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
 * @author sipl
 */
public class TdDueReport extends BaseBean {

    private String message;
    private String bankType;
    private List<SelectItem> bankTypeList;
    private String tillDt;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private InvestmentReportMgmtFacadeRemote remObj = null;
    private final String jndiHomeObjName = "InvestmentReportMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");    
    Date dt = new Date();
    
    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public List<SelectItem> getBankTypeList() {
        return bankTypeList;
    }

    public void setBankTypeList(List<SelectItem> bankTypeList) {
        this.bankTypeList = bankTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public TdDueReport() {
        bankTypeList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            remObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeObjName);
            setBankNameList();
            this.setTillDt(dmy.format(dt));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setBankNameList() {
        try {
            List<GlDescRange> entityList = remoteObj.getGlRange("F");
            if (!entityList.isEmpty()) {
                for (GlDescRange entity : entityList) {
                    String fromNo = String.format("%06d", Integer.parseInt(entity.getFromno()));
                    String toNo = String.format("%06d", Integer.parseInt(entity.getTono()));
                    List<String> securityList = remoteObj.getSecurityType(fromNo, toNo);
                    if (!securityList.isEmpty()) {
                        bankTypeList.add(new SelectItem("ALL"));
                        for (String security : securityList) {
                            bankTypeList.add(new SelectItem(security));
                        }
                    } else {
                        this.setMessage("Data is not found for government security in GL DESC RANGE table !");
                        return;
                    }
                }
            } else {
                this.setMessage("Range is not found for government security !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        List<TdDueDatePojo> reportList = new ArrayList<TdDueDatePojo>();
        String repName = "";
        try {
            if (validate()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List brList = common.getBranchNameandAddress(getOrgnBrCode());
                repName = "TD DUE REPORT";
                List<Object[]> dataList = new ArrayList<Object[]>();
                if(this.bankType.equalsIgnoreCase("ALL")){
            
                    dataList = remObj.getDueDateReportActive(dmy.parse(this.tillDt));                 
                }else{
                    dataList = remObj.getDueDateReportActiveSellarName(this.getBankType());                   
                }                
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        TdDueDatePojo pojo = new TdDueDatePojo();
                        Object[] array = dataList.get(i);
                        InvestmentFdrDates dateEntity = (InvestmentFdrDates) array[0];
                        InvestmentFdrDetails detailEntity = (InvestmentFdrDetails) array[1];
                        pojo.setBankName(detailEntity.getSellerName());
                        pojo.setCtrlNo(dateEntity.getCtrlNo());
                        pojo.setFdrNo(detailEntity.getFdrNo());
                        pojo.setFaceValue(detailEntity.getFaceValue());
                        pojo.setMadeDt(dmy.format(dateEntity.getPurchaseDt()));
                        pojo.setRoi(detailEntity.getRoi());
                        pojo.setDueDt(dmy.format(dateEntity.getMatDt()));                        
                        if(dateEntity.getMatDt().compareTo(dmy.parse(this.getTillDt()))>0){
                            pojo.setFlag("TERM DEPOSITS AS ON " + this.getTillDt());
                        }else{
                            pojo.setFlag("TERM DEPOSITS(MATURED) AS ON " + this.getTillDt());
                        }                        
                        pojo.setPrd(dateEntity.getDays().toString()+"D " + dateEntity.getMonths().toString()+"M " + dateEntity.getYears().toString()+"Y");
                        pojo.setIntOpt(detailEntity.getIntOpt());
                        pojo.setMatValue(detailEntity.getBookValue());                       
                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
                String repDate = "";
                repDate = "Till " + this.getTillDt();

                Map fillParams = new HashMap();
                
                fillParams.put("pBankName", brList.get(0));
                fillParams.put("pBranchAddress", brList.get(1));
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", repDate);
                fillParams.put("pPrintedBy", getUserName());

                new ReportBean().jasperReport("dueTDReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "TD DUE REPORT");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validate() {
        this.setMessage("");
        if (this.getTillDt().length() < 10) {
            this.setMessage("please fill correct date !");
            return false;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.getTillDt()) == false) {
            this.setMessage("please fill correct from date !");
            return false;
        }
        return true;
    }

    public void resetAction() {
        this.setMessage("");
        this.setBankType("ALL");
        this.setTillDt(dmy.format(dt));
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