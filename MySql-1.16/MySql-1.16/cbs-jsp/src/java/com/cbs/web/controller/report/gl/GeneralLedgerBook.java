/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.web.pojo.GlbList;
import com.cbs.dto.report.CbsGeneralLedgerBookPojo;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class GeneralLedgerBook extends BaseBean {

    String message;
    Date calDate;
    private final String jndiHomeName = "GLReportFacade";
    private GLReportFacadeRemote glBeanRemote = null;
    List<CbsGeneralLedgerBookPojo> glbDataList = new ArrayList<CbsGeneralLedgerBookPojo>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public GeneralLedgerBook() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setCalDate(new Date());
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        if (this.calDate == null) {
            setMessage("Please Enter Date.");
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date2 = this.calDate;
            String report = "General Ledger Book";
            glbDataList = glBeanRemote.getGeneralLedgerBookData(ymd.format(calDate), getOrgnBrCode());

            List<CbsGeneralLedgerBookPojo> assList = new ArrayList<CbsGeneralLedgerBookPojo>();
            List<CbsGeneralLedgerBookPojo> libList = new ArrayList<CbsGeneralLedgerBookPojo>();
            double grandTotalAssets = 0.0;
            double grandTotalLib = 0.0;

            if (!glbDataList.isEmpty()) {
                for (CbsGeneralLedgerBookPojo cbsPojo : glbDataList) {
                    CbsGeneralLedgerBookPojo pojo1 = new CbsGeneralLedgerBookPojo();
                    CbsGeneralLedgerBookPojo pojo2 = new CbsGeneralLedgerBookPojo();
                    if (cbsPojo.getACTYPE().equalsIgnoreCase("A")) {
                        pojo1.setACNAME(cbsPojo.getACNAME());
                        pojo1.setACTYPE(cbsPojo.getACTYPE());
                        pojo1.setAMOUNT(cbsPojo.getAMOUNT());

                        pojo1.setBankaddress(cbsPojo.getBankaddress());
                        pojo1.setBankname(cbsPojo.getBankname());
                        pojo1.setGNO(cbsPojo.getGNO());
                        grandTotalAssets += Double.parseDouble(pojo1.getAMOUNT().toString());

                        assList.add(pojo1);
                    } else {
                        pojo2.setACNAME(cbsPojo.getACNAME());
                        pojo2.setACTYPE(cbsPojo.getACTYPE());
                        pojo2.setAMOUNT(cbsPojo.getAMOUNT());
                        pojo2.setBankaddress(cbsPojo.getBankaddress());

                        pojo2.setBankname(cbsPojo.getBankname());
                        pojo2.setGNO(cbsPojo.getGNO());
                        grandTotalLib += Double.parseDouble(pojo2.getAMOUNT().toString());
                        libList.add(pojo2);
                    }
                }
            }
            if (!libList.isEmpty() && (!assList.isEmpty())) {
                Map fillParams = new HashMap();
                fillParams.put("dd-MMM-yyyy", sdf2.format(date2));
                fillParams.put("pPrintedDate", sdf2.format(date2));
                fillParams.put("pPrintedBy", getUserName());

                fillParams.put("pReportName", report);
                fillParams.put("pBankName", glbDataList.get(0).getBankname());
                fillParams.put("pBankAdd", glbDataList.get(0).getBankaddress());
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                GlbList myList = new GlbList();

                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                new ReportBean().jasperReport("generalLedgerBook", "text/html",
                        new JRBeanCollectionDataSource(s), fillParams, "General Ledger Book");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            }

        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());

        }
    }

    public void viewPdfReport() {
        
        setMessage("");
        if (this.calDate == null) {
            setMessage("Please Enter Date.");
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date2 = this.calDate;
            String report = "General Ledger Book";
            glbDataList = glBeanRemote.getGeneralLedgerBookData(ymd.format(calDate), getOrgnBrCode());

            List<CbsGeneralLedgerBookPojo> assList = new ArrayList<CbsGeneralLedgerBookPojo>();
            List<CbsGeneralLedgerBookPojo> libList = new ArrayList<CbsGeneralLedgerBookPojo>();
            double grandTotalAssets = 0.0;
            double grandTotalLib = 0.0;

            if (!glbDataList.isEmpty()) {
                for (CbsGeneralLedgerBookPojo cbsPojo : glbDataList) {
                    CbsGeneralLedgerBookPojo pojo1 = new CbsGeneralLedgerBookPojo();
                    CbsGeneralLedgerBookPojo pojo2 = new CbsGeneralLedgerBookPojo();
                    if (cbsPojo.getACTYPE().equalsIgnoreCase("A")) {
                        pojo1.setACNAME(cbsPojo.getACNAME());
                        pojo1.setACTYPE(cbsPojo.getACTYPE());
                        pojo1.setAMOUNT(cbsPojo.getAMOUNT());

                        pojo1.setBankaddress(cbsPojo.getBankaddress());
                        pojo1.setBankname(cbsPojo.getBankname());
                        pojo1.setGNO(cbsPojo.getGNO());
                        grandTotalAssets += Double.parseDouble(pojo1.getAMOUNT().toString());

                        assList.add(pojo1);
                    } else {
                        pojo2.setACNAME(cbsPojo.getACNAME());
                        pojo2.setACTYPE(cbsPojo.getACTYPE());
                        pojo2.setAMOUNT(cbsPojo.getAMOUNT());
                        pojo2.setBankaddress(cbsPojo.getBankaddress());

                        pojo2.setBankname(cbsPojo.getBankname());
                        pojo2.setGNO(cbsPojo.getGNO());
                        grandTotalLib += Double.parseDouble(pojo2.getAMOUNT().toString());
                        libList.add(pojo2);
                    }
                }
            }
            if (!libList.isEmpty() && (!assList.isEmpty())) {
                Map fillParams = new HashMap();
                fillParams.put("dd-MMM-yyyy", sdf2.format(date2));
                fillParams.put("pPrintedDate", sdf2.format(date2));
                fillParams.put("pPrintedBy", getUserName());

                fillParams.put("pReportName", report);
                fillParams.put("pBankName", glbDataList.get(0).getBankname());
                fillParams.put("pBankAdd", glbDataList.get(0).getBankaddress());
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                GlbList myList = new GlbList();

                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                
                new ReportBean().openPdf("generalLedgerBook_"+ymd.format(sdf2.parse(getTodayDate())), "generalLedgerBook",new JRBeanCollectionDataSource(s), fillParams);
              ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

            }
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());

        }
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        this.setMessage("");
        this.setCalDate(new Date());
    }
}
